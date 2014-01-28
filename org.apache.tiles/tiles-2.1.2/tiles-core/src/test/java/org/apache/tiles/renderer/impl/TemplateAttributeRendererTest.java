/*
 * $Id: TemplateAttributeRendererTest.java 736275 2009-01-21 09:58:20Z apetrelli $
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
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.evaluator.impl.DirectAttributeEvaluator;
import org.easymock.EasyMock;

import junit.framework.TestCase;

/**
 * Tests {@link TemplateAttributeRenderer}.
 *
 * @version $Rev: 736275 $ $Date: 2009-01-21 10:58:20 +0100 (Wed, 21 Jan 2009) $
 */
public class TemplateAttributeRendererTest extends TestCase {

    /**
     * The renderer.
     */
    private TemplateAttributeRenderer renderer;

    /** {@inheritDoc} */
    @Override
    protected void setUp() throws Exception {
        renderer = new TemplateAttributeRenderer();
        renderer.setEvaluator(new DirectAttributeEvaluator());
    }

    /**
     * Tests
     * {@link StringAttributeRenderer#write(Object, Attribute, TilesRequestContext)}.
     *
     * @throws IOException If something goes wrong during rendition.
     */
    public void testWrite() throws IOException {
        StringWriter writer = new StringWriter();
        Attribute attribute = new Attribute("/myTemplate.jsp", null, null, "template");
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        TilesRequestContextFactory contextFactory = EasyMock
                .createMock(TilesRequestContextFactory.class);
        TilesRequestContext requestContext = EasyMock
                .createMock(TilesRequestContext.class);
        EasyMock.expect(contextFactory.createRequestContext(applicationContext))
                .andReturn(requestContext);
        requestContext.dispatch("/myTemplate.jsp");
        EasyMock.replay(applicationContext, contextFactory, requestContext);
        renderer.setApplicationContext(applicationContext);
        renderer.setRequestContextFactory(contextFactory);
        renderer.render(attribute, requestContext);
        writer.close();
    }
}
