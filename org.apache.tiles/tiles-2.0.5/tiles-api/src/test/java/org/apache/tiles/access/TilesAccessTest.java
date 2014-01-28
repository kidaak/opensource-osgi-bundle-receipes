/*
 * $Id: TilesAccessTest.java 527536 2007-04-11 15:44:51Z apetrelli $
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
package org.apache.tiles.access;

import org.easymock.EasyMock;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.TilesException;

import javax.servlet.ServletContext;

import junit.framework.TestCase;

/**
 * @version $Rev: 527536 $ $Date: 2007-04-11 16:44:51 +0100 (Wed, 11 Apr 2007) $
 */
public class TilesAccessTest extends TestCase {

    /**
     * The servlet context to use.
     */
    private ServletContext context;

    /** {@inheritDoc} */
    public void setUp() {
        context = EasyMock.createMock(ServletContext.class);
    }

    /**
     * Tests the setting of the context.
     *
     * @throws TilesException If something goes wrong.
     */
    public void testSetContext() throws TilesException {
        TilesContainer container = EasyMock.createMock(TilesContainer.class);
        context.setAttribute(TilesAccess.CONTAINER_ATTRIBUTE, container);
        EasyMock.replay(context);
        TilesAccess.setContainer(context, container);
        EasyMock.verify(context);
    }

    /**
     * Tests the getting of the context.
     *
     * @throws TilesException If something goes wrong.
     */
    public void testGetContext() throws TilesException {
        TilesContainer container = EasyMock.createMock(TilesContainer.class);
        EasyMock.expect(context.getAttribute(TilesAccess.CONTAINER_ATTRIBUTE)).andReturn(container);
        EasyMock.replay(context);
        assertEquals(container, TilesAccess.getContainer(context));
        EasyMock.verify(context);
    }

}
