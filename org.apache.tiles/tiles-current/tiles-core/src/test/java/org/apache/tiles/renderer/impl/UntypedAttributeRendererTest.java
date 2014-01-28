/*
 * $Id: UntypedAttributeRendererTest.java 736275 2009-01-21 09:58:20Z apetrelli $
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
package org.apache.tiles.renderer.impl;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.tiles.Attribute;
import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.evaluator.impl.DirectAttributeEvaluator;
import org.easymock.EasyMock;

import junit.framework.TestCase;

/**
 * Tests {@link UntypedAttributeRenderer}.
 *
 * @version $Rev: 736275 $ $Date: 2009-01-21 10:58:20 +0100 (Wed, 21 Jan 2009) $
 */
public class UntypedAttributeRendererTest extends TestCase {

    /**
     * The renderer.
     */
    private UntypedAttributeRenderer renderer;

    /** {@inheritDoc} */
    @Override
    protected void setUp() throws Exception {
        renderer = new UntypedAttributeRenderer();
        renderer.setEvaluator(new DirectAttributeEvaluator());
    }

    /**
     * Tests
     * {@link StringAttributeRenderer#write(Object, Attribute, TilesRequestContext)}
     * writing a Definition.
     *
     * @throws IOException If something goes wrong during rendition.
     */
    public void testWriteDefinition() throws IOException {
        StringWriter writer = new StringWriter();
        Attribute attribute = new Attribute("my.definition", null, null, "definition");
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        TilesRequestContextFactory contextFactory = EasyMock
                .createMock(TilesRequestContextFactory.class);
        TilesContainer container = EasyMock.createMock(TilesContainer.class);
        TilesRequestContext requestContext = EasyMock
                .createMock(TilesRequestContext.class);
        EasyMock.expect(contextFactory.createRequestContext(applicationContext))
                .andReturn(requestContext);
        Object[] requestObjects = new Object[0];
        EasyMock.expect(requestContext.getRequestObjects()).andReturn(requestObjects);
        EasyMock.expect(container.isValidDefinition("my.definition"))
                .andReturn(Boolean.TRUE);
        container.render("my.definition");
        EasyMock.replay(applicationContext, contextFactory, requestContext,
                container);
        renderer.setApplicationContext(applicationContext);
        renderer.setRequestContextFactory(contextFactory);
        renderer.setContainer(container);
        renderer.render(attribute, requestContext);
        writer.close();
    }

    /**
     * Tests
     * {@link StringAttributeRenderer#write(Object, Attribute, TilesRequestContext)}
     * writing a string.
     *
     * @throws IOException If something goes wrong during rendition.
     */
    public void testWriteString() throws IOException {
        StringWriter writer = new StringWriter();
        Attribute attribute = new Attribute("Result", null, null, "string");
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        TilesRequestContextFactory contextFactory = EasyMock
                .createMock(TilesRequestContextFactory.class);
        TilesContainer container = EasyMock.createMock(TilesContainer.class);
        TilesRequestContext requestContext = EasyMock
                .createMock(TilesRequestContext.class);
        Object[] requestObjects = new Object[0];
        EasyMock.expect(requestContext.getRequestObjects()).andReturn(requestObjects);
        EasyMock.expect(contextFactory.createRequestContext(applicationContext))
                .andReturn(requestContext);
        EasyMock.expect(container.isValidDefinition("my.definition"))
                .andReturn(Boolean.TRUE);
        EasyMock.expect(requestContext.getWriter()).andReturn(writer);
        EasyMock.replay(applicationContext, contextFactory, requestContext);
        renderer.setApplicationContext(applicationContext);
        renderer.setRequestContextFactory(contextFactory);
        renderer.setContainer(container);
        renderer.render(attribute, requestContext);
        writer.close();
        assertEquals("Not written 'Result'", "Result", writer.toString());
    }

    /**
     * Tests
     * {@link StringAttributeRenderer#write(Object, Attribute, TilesRequestContext)}
     * writing a template.
     *
     * @throws IOException If something goes wrong during rendition.
     */
    public void testWriteTemplate() throws IOException {
        StringWriter writer = new StringWriter();
        Attribute attribute = new Attribute("/myTemplate.jsp", null, null, "template");
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        TilesRequestContextFactory contextFactory = EasyMock
                .createMock(TilesRequestContextFactory.class);
        TilesContainer container = EasyMock.createMock(TilesContainer.class);
        TilesRequestContext requestContext = EasyMock
                .createMock(TilesRequestContext.class);
        EasyMock.expect(contextFactory.createRequestContext(applicationContext))
                .andReturn(requestContext);
        Object[] requestObjects = new Object[0];
        EasyMock.expect(requestContext.getRequestObjects()).andReturn(requestObjects);
        requestContext.dispatch("/myTemplate.jsp");
        EasyMock.expect(container.isValidDefinition("my.definition"))
                .andReturn(Boolean.TRUE);
        EasyMock.replay(applicationContext, contextFactory, requestContext);
        renderer.setApplicationContext(applicationContext);
        renderer.setRequestContextFactory(contextFactory);
        renderer.setContainer(container);
        renderer.render(attribute, requestContext);
        writer.close();
    }
}
