/*
 * $Id: TestUrlDefinitionsFactory.java 676326 2008-07-13 14:16:07Z apetrelli $
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

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContext;
import org.easymock.EasyMock;

/**
 * Tests the UrlDefinitionsFactory.
 *
 * @version $Rev: 676326 $ $Date: 2008-07-13 16:16:07 +0200 (Sun, 13 Jul 2008) $
 */
public class TestUrlDefinitionsFactory extends TestCase {

    /**
     * The definitions factory.
     */
    private UrlDefinitionsFactory factory;

    /** {@inheritDoc} */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        factory = new UrlDefinitionsFactory();
    }

    /**
     * Creates a new instance of TestUrlDefinitionsFactory.
     *
     * @param name The name of the test.
     */
    public TestUrlDefinitionsFactory(String name) {
        super(name);
    }

    /**
     * Start the tests.
     *
     * @param theArgs the arguments. Not used
     */
    public static void main(String[] theArgs) {
        junit.textui.TestRunner.main(
                new String[]{TestUrlDefinitionsFactory.class.getName()});
    }

    /**
     * @return a test suite (<code>TestSuite</code>) that includes all methods
     *         starting with "test"
     */
    public static Test suite() {
        return new TestSuite(TestUrlDefinitionsFactory.class);
    }

    /**
     * Tests the readDefinitions method under normal conditions.
     *
     * @throws Exception If something goes wrong.
     */
    public void testReadDefinitions() throws Exception {
        // Set up multiple data sources.
        URL url1 = this.getClass().getClassLoader().getResource(
                "org/apache/tiles/config/defs1.xml");
        assertNotNull("Could not load defs1 file.", url1);
        URL url2 = this.getClass().getClassLoader().getResource(
                "org/apache/tiles/config/defs2.xml");
        assertNotNull("Could not load defs2 file.", url2);
        URL url3 = this.getClass().getClassLoader().getResource(
                "org/apache/tiles/config/defs3.xml");
        assertNotNull("Could not load defs3 file.", url3);

        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        Set<URL> urlSet = new HashSet<URL>();
        urlSet.add(url1);
        EasyMock.expect(
                applicationContext
                        .getResources("org/apache/tiles/config/defs1.xml"))
                .andReturn(urlSet);
        urlSet = new HashSet<URL>();
        urlSet.add(url2);
        EasyMock.expect(
                applicationContext
                        .getResources("org/apache/tiles/config/defs2.xml"))
                .andReturn(urlSet);
        urlSet = new HashSet<URL>();
        urlSet.add(url3);
        EasyMock.expect(
                applicationContext
                        .getResources("org/apache/tiles/config/defs3.xml"))
                .andReturn(urlSet);
        EasyMock.replay(applicationContext);
        factory.setApplicationContext(applicationContext);
        TilesRequestContext emptyContext = new MockOnlyLocaleTilesContext(null);

        Map<String, String> params = new HashMap<String, String>();
        params.put(DefinitionsFactory.DEFINITIONS_CONFIG,
                "org/apache/tiles/config/defs1.xml,org/apache/tiles/config/defs2.xml,"
                + "org/apache/tiles/config/defs3.xml");
        factory.init(params);

        assertNotNull("test.def1 definition not found.", factory.getDefinition(
                "test.def1", emptyContext));
        assertNotNull("test.def2 definition not found.", factory.getDefinition(
                "test.def2", emptyContext));
        assertNotNull("test.def3 definition not found.", factory.getDefinition(
                "test.def3", emptyContext));
    }

    /**
     * Tests the getDefinition method.
     *
     * @throws Exception If something goes wrong.
     */
    public void testGetDefinition() throws Exception {
        // Set up multiple data sources.
        URL url1 = this.getClass().getClassLoader().getResource(
                "org/apache/tiles/config/defs1.xml");
        assertNotNull("Could not load defs1 file.", url1);
        URL url2 = this.getClass().getClassLoader().getResource(
                "org/apache/tiles/config/defs2.xml");
        assertNotNull("Could not load defs2 file.", url2);
        URL url3 = this.getClass().getClassLoader().getResource(
                "org/apache/tiles/config/defs3.xml");
        assertNotNull("Could not load defs3 file.", url3);

        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        Set<URL> urlSet = new HashSet<URL>();
        urlSet.add(url1);
        EasyMock.expect(
                applicationContext
                        .getResources("org/apache/tiles/config/defs1.xml"))
                .andReturn(urlSet);
        urlSet = new HashSet<URL>();
        urlSet.add(url2);
        EasyMock.expect(
                applicationContext
                        .getResources("org/apache/tiles/config/defs2.xml"))
                .andReturn(urlSet);
        urlSet = new HashSet<URL>();
        urlSet.add(url3);
        EasyMock.expect(
                applicationContext
                        .getResources("org/apache/tiles/config/defs3.xml"))
                .andReturn(urlSet);
        EasyMock.replay(applicationContext);
        factory.setApplicationContext(applicationContext);

        Map<String, String> params = new HashMap<String, String>();
        params.put(DefinitionsFactory.DEFINITIONS_CONFIG,
                "org/apache/tiles/config/defs1.xml,org/apache/tiles/config/defs2.xml,"
                + "org/apache/tiles/config/defs3.xml");
        factory.init(params);

        TilesRequestContext emptyContext = new MockOnlyLocaleTilesContext(null);
        TilesRequestContext usContext = new MockOnlyLocaleTilesContext(Locale.US);
        TilesRequestContext frenchContext = new MockOnlyLocaleTilesContext(Locale.FRENCH);
        TilesRequestContext chinaContext = new MockOnlyLocaleTilesContext(Locale.CHINA);
        TilesRequestContext canadaFrenchContext = new MockOnlyLocaleTilesContext(Locale.CANADA_FRENCH);

        assertNotNull("test.def1 definition not found.", factory.getDefinition("test.def1", emptyContext));
        assertNotNull("test.def2 definition not found.", factory.getDefinition("test.def2", emptyContext));
        assertNotNull("test.def3 definition not found.", factory.getDefinition("test.def3", emptyContext));
        assertNotNull("test.common definition not found.", factory.getDefinition("test.common", emptyContext));
        assertNotNull("test.common definition in US locale not found.", factory
                .getDefinition("test.common", usContext));
        assertNotNull("test.common definition in FRENCH locale not found.",
                factory.getDefinition("test.common", frenchContext));
        assertNotNull("test.common definition in CHINA locale not found.",
                factory.getDefinition("test.common", chinaContext));
        assertNotNull(
                "test.common.french definition in FRENCH locale not found.",
                factory.getDefinition("test.common.french", frenchContext));
        assertNotNull(
                "test.common.french definition in CANADA_FRENCH locale not found.",
                factory
                        .getDefinition("test.common.french",
                                canadaFrenchContext));
        assertNotNull("test.def.toextend definition not found.", factory
                .getDefinition("test.def.toextend", emptyContext));
        assertNotNull("test.def.overridden definition not found.", factory
                .getDefinition("test.def.overridden", emptyContext));
        assertNotNull(
                "test.def.overridden definition in FRENCH locale not found.",
                factory.getDefinition("test.def.overridden", frenchContext));

        assertEquals("Incorrect default country value", "default", factory
                .getDefinition("test.def1", emptyContext).getAttribute(
                        "country").getValue());
        assertEquals("Incorrect US country value", "US", factory.getDefinition(
                "test.def1", usContext).getAttribute("country").getValue());
        assertEquals("Incorrect France country value", "France", factory
                .getDefinition("test.def1", frenchContext).getAttribute(
                        "country").getValue());
        assertEquals("Incorrect Chinese country value (should be default)",
                "default", factory.getDefinition("test.def1", chinaContext)
                        .getAttribute("country").getValue());
        assertEquals("Incorrect default country value", "default", factory
                .getDefinition("test.def.overridden", emptyContext)
                .getAttribute("country").getValue());
        assertEquals("Incorrect default title value",
                "Definition to be overridden", factory.getDefinition(
                        "test.def.overridden", emptyContext).getAttribute(
                        "title").getValue());
        assertEquals("Incorrect France country value", "France", factory
                .getDefinition("test.def.overridden", frenchContext)
                .getAttribute("country").getValue());
        assertEquals("Incorrect France title value",
                "Definition to be extended", factory.getDefinition(
                        "test.def.overridden", frenchContext).getAttribute(
                        "title").getValue());
    }

    /**
     * Tests the reader init param.
     *
     * @throws Exception If something goes wrong.
     */
    public void testReaderParam() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put(DefinitionsFactory.READER_IMPL_PROPERTY,
                "org.apache.tiles.definition.MockDefinitionsReader");

        int instanceCount = MockDefinitionsReader.getInstanceCount();

        UrlDefinitionsFactory factory = new UrlDefinitionsFactory();

        // Set up multiple data sources.
        URL url1 = this.getClass().getClassLoader().getResource(
                "org/apache/tiles/config/defs1.xml");
        assertNotNull("Could not load defs1 file.", url1);

        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        Set<URL> urlSet = new HashSet<URL>();
        urlSet.add(url1);
        EasyMock.expect(
                applicationContext
                        .getResources("org/apache/tiles/config/defs1.xml"))
                .andReturn(urlSet);
        EasyMock.replay(applicationContext);
        factory.setApplicationContext(applicationContext);

        params.put(DefinitionsFactory.DEFINITIONS_CONFIG,
                "org/apache/tiles/config/defs1.xml");
        factory.init(params);

        assertEquals("MockDefinitionsReader not used.",
                instanceCount + 1,
                MockDefinitionsReader.getInstanceCount());
    }
}
