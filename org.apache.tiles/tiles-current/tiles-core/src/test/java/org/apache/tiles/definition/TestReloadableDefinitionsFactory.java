/*
 * $Id: TestReloadableDefinitionsFactory.java 736275 2009-01-21 09:58:20Z apetrelli $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.tiles.definition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.tiles.Definition;
import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.awareness.TilesApplicationContextAware;
import org.apache.tiles.context.TilesRequestContext;
import org.easymock.EasyMock;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the reloadable definitions impl.
 *
 * @version $Rev: 736275 $ $Date: 2009-01-21 10:58:20 +0100 (Wed, 21 Jan 2009) $
 */
public class TestReloadableDefinitionsFactory extends TestCase {

    /**
     * The time (in milliseconds) to wait to be sure that the system updates the
     * modify date of a file.
     */
    private static final int SLEEP_MILLIS = 30000;

    /**
     * Creates a new instance of TestReloadableDefinitionsFactory.
     *
     * @param name The name of the test.
     */
    public TestReloadableDefinitionsFactory(String name) {
        super(name);
    }

    /**
     * Start the tests.
     *
     * @param theArgs the arguments. Not used
     */
    public static void main(String[] theArgs) {
        junit.textui.TestRunner.main(
                new String[]{TestReloadableDefinitionsFactory.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite() {
        return new TestSuite(TestReloadableDefinitionsFactory.class);
    }

    /**
     * Tests reloading definitions impl.
     *
     * @throws Exception If something goes wrong.
     */
    public void testReloadableDefinitionsFactory() throws Exception {
        DefinitionsFactory factory = new UrlDefinitionsFactory();

        // Set up multiple data sources.
        URL url = this.getClass().getClassLoader().getResource(
                "org/apache/tiles/config/temp-defs.xml");

        URI uri = null;
        String urlPath = null;

        // The following madness is necessary b/c of the way Windows hanndles URLs.
        // We must add a slash to the protocol if Windows does not.  But we cannot
        // add a slash to Unix paths b/c they already have one.
        if (url.getPath().startsWith("/")) {
            urlPath = "file:" + url.getPath();
        } else {
            urlPath = "file:/" + url.getPath();
        }

        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        Set<URL> urlSet = new HashSet<URL>();
        urlSet.add(url);
        EasyMock.expect(applicationContext.getResources(urlPath)).andReturn(
                urlSet);
        EasyMock.replay(applicationContext);
        ((TilesApplicationContextAware) factory)
                .setApplicationContext(applicationContext);

        // The following second madness is necessary b/c sometimes spaces
        // are encoded as '%20', sometimes they are not. For example in
        // Windows 2000 under Eclipse they are encoded, under the prompt of
        // Windows 2000 they are not.
        // It seems to be in the different behaviour of
        // sun.misc.Launcher$AppClassLoader (called under Eclipse) and
        // java.net.URLClassLoader (under maven).
        // And an URL accepts spaces while URIs need '%20'.
        try {
            uri = new URI(urlPath);
        } catch (URISyntaxException e) {
            uri = new URI(urlPath.replaceAll(" ", "%20"));
        }

        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n"
            + "<!DOCTYPE tiles-definitions PUBLIC "
            + "\"-//Apache Software Foundation//DTD Tiles Configuration 2.0//EN\" "
            + "\"http://tiles.apache.org/dtds/tiles-config_2_0.dtd\">\n\n"
            + "<tiles-definitions>"
            + "<definition name=\"rewrite.test\" template=\"/test.jsp\">"
            + "<put-attribute name=\"testparm\" value=\"testval\"/>"
            + "</definition>"
            + "</tiles-definitions>";

        File file = new File(uri);
        FileOutputStream fileOut = new FileOutputStream(file);
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(fileOut));
        writer.write(xml);
        writer.close();

        Map<String, String> params = new HashMap<String, String>();
        params.put(DefinitionsFactory.DEFINITIONS_CONFIG, urlPath);
        factory.init(params);
        TilesRequestContext context = EasyMock.createMock(TilesRequestContext.class);
        EasyMock.expect(context.getSessionScope()).andReturn(
                new HashMap<String, Object>()).anyTimes();
        EasyMock.expect(context.getRequestLocale()).andReturn(null).anyTimes();
        EasyMock.replay(context);

        Definition definition = factory.getDefinition("rewrite.test", context);
        assertNotNull("rewrite.test definition not found.", definition);
        assertEquals("Incorrect initial template value", "/test.jsp",
                definition.getTemplateAttribute().getValue());

        Refreshable reloadable = (Refreshable) factory;
        assertEquals("Factory should be fresh.", false,
                reloadable.refreshRequired());

        // Make sure the system actually updates the timestamp.
        Thread.sleep(SLEEP_MILLIS);

        // Set up multiple data sources.
        xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n"
            + "<!DOCTYPE tiles-definitions PUBLIC "
            + "\"-//Apache Software Foundation//DTD Tiles Configuration 2.0//EN\" "
            + "\"http://tiles.apache.org/dtds/tiles-config_2_0.dtd\">\n\n"
            + "<tiles-definitions>"
            + "<definition name=\"rewrite.test\" template=\"/newtest.jsp\">"
            + "<put-attribute name=\"testparm\" value=\"testval\"/>"
            + "</definition>"
            + "</tiles-definitions>";

        file = new File(uri);
        fileOut = new FileOutputStream(file);
        writer = new BufferedWriter(new OutputStreamWriter(fileOut));
        writer.write(xml);
        writer.close();


        assertEquals("Factory should be stale.", true,
                reloadable.refreshRequired());
        reloadable.refresh();
        definition = factory.getDefinition("rewrite.test", context);
        assertNotNull("rewrite.test definition not found.", definition);
        assertEquals("Incorrect initial template value", "/newtest.jsp",
                definition.getTemplateAttribute().getValue());
    }
}
