/*
 * $Id: UrlPreparerTest.java 709151 2008-10-30 12:36:29Z apetrelli $
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

package org.apache.tiles.compat.preparer;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.AttributeContext;
import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.servlet.context.ServletTilesApplicationContext;
import org.apache.tiles.servlet.context.ServletTilesRequestContext;
import org.apache.tiles.servlet.context.ServletUtil;
import org.easymock.EasyMock;

import junit.framework.TestCase;

/**
 * Tests {@link UrlPreparer}.
 *
 * @version $Rev: 709151 $ $Date: 2008-10-30 13:36:29 +0100 (Thu, 30 Oct 2008) $
 */
public class UrlPreparerTest extends TestCase {

    /**
     * The preparer to test.
     */
    private UrlPreparer preparer;

    /** {@inheritDoc} */
    @Override
    protected void setUp() throws Exception {
        preparer = new UrlPreparer("/my/url.do");
    }

    /**
     * Test method for
     * {@link org.apache.tiles.compat.preparer.UrlPreparer#execute(
     * org.apache.tiles.context.TilesRequestContext, org.apache.tiles.AttributeContext)}.
     * @throws IOException If something goes wrong.
     * @throws ServletException If something goes wrong.
     */
    public void testExecute() throws ServletException, IOException {
        HttpServletRequest request = EasyMock
                .createMock(HttpServletRequest.class);
        request.setAttribute(ServletUtil.FORCE_INCLUDE_ATTRIBUTE_NAME, true);
        HttpServletResponse response = EasyMock
                .createMock(HttpServletResponse.class);
        ServletContext servletContext = EasyMock
                .createMock(ServletContext.class);
        RequestDispatcher rd = EasyMock.createMock(RequestDispatcher.class);
        TilesApplicationContext applicationContext = new ServletTilesApplicationContext(
                servletContext);
        TilesRequestContext requestContext = new ServletTilesRequestContext(
                applicationContext, request, response);
        AttributeContext attributeContext = EasyMock
                .createMock(AttributeContext.class);

        EasyMock.expect(request.getRequestDispatcher("/my/url.do"))
                .andReturn(rd);
        rd.include(request, response);
        EasyMock
                .replay(request, response, servletContext, attributeContext, rd);
        preparer.execute(requestContext, attributeContext);
        EasyMock.verify(rd);
    }
}
