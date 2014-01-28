/*
 * $Id: JspTilesRequestContext.java 531864 2007-04-24 10:24:30Z apetrelli $
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
package org.apache.tiles.jsp.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.servlet.context.ServletTilesRequestContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Context implementation used for executing tiles within a
 * jsp tag library.
 *
 * @version $Rev: 531864 $ $Date: 2007-04-24 11:24:30 +0100 (Tue, 24 Apr 2007) $
 */
public class JspTilesRequestContext extends ServletTilesRequestContext
    implements TilesRequestContext {

    /**
     * The logging object.
     */
    private static final Log LOG =
        LogFactory.getLog(JspTilesRequestContext.class);


    /**
     * The current page context.
     */
    private PageContext pageContext;

    /**
     * The writer response to use.
     */
    private JspWriterResponse response;

    /**
     * Constructor.
     *
     * @param context The servlet context to use.
     * @param pageContext The page context to use.
     */
    public JspTilesRequestContext(ServletContext context, PageContext pageContext) {
        super(context,
            (HttpServletRequest) pageContext.getRequest(),
            (HttpServletResponse) pageContext.getResponse());
        this.pageContext = pageContext;
    }

    /**
     * Dispatches a path. In fact it "includes" it!
     *
     * @param path The path to dispatch to.
     * @throws IOException If something goes wrong during dispatching.
     * @see org.apache.tiles.servlet.context.ServletTilesRequestContext#dispatch(java.lang.String)
     */
    public void dispatch(String path) throws IOException {
        include(path);
    }

    /** {@inheritDoc} */
    public void include(String path) throws IOException {
        try {
            JspUtil.doInclude(pageContext, path, false);
        } catch (JspException e) {
            LOG.error("JSPException while including path '" + path + "'. ", e);
            throw new IOException("JSPException while including path '" + path
                    + "'. " + e.getMessage());
        }
    }

    /** {@inheritDoc} */
    public HttpServletResponse getResponse() {
        if (response == null) {
            response = new JspWriterResponse(pageContext);
        }
        return response;
    }

}
