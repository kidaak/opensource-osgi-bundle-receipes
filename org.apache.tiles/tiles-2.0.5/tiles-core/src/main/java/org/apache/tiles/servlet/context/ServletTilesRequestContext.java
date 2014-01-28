/*
 * $Id: ServletTilesRequestContext.java 573651 2007-09-07 18:09:41Z apetrelli $
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
package org.apache.tiles.servlet.context;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tiles.context.TilesRequestContext;

/**
 * Servlet-bsed implementation of the TilesApplicationContext interface.
 *
 * @version $Rev: 573651 $ $Date: 2007-09-07 19:09:41 +0100 (Fri, 07 Sep 2007) $
 */
public class ServletTilesRequestContext extends ServletTilesApplicationContext implements TilesRequestContext {

    /**
     * The logging object.
     */
    private static final Log LOG = LogFactory.getLog(ServletTilesRequestContext.class);

    /**
     * The request object to use.
     */
    private HttpServletRequest request;

    /**
     * The response object to use.
     */
    private HttpServletResponse response;


    /**
     * <p>The lazily instantiated <code>Map</code> of header name-value
     * combinations (immutable).</p>
     */
    private Map<String, String> header = null;


    /**
     * <p>The lazily instantitated <code>Map</code> of header name-values
     * combinations (immutable).</p>
     */
    private Map<String, String[]> headerValues = null;


    /**
     * <p>The lazily instantiated <code>Map</code> of request
     * parameter name-value.</p>
     */
    private Map<String, String> param = null;


    /**
     * <p>The lazily instantiated <code>Map</code> of request
     * parameter name-values.</p>
     */
    private Map<String, String[]> paramValues = null;

    /**
     * <p>The lazily instantiated <code>Map</code> of request scope
     * attributes.</p>
     */
    private Map<String, Object> requestScope = null;

    /**
     * <p>The lazily instantiated <code>Map</code> of session scope
     * attributes.</p>
     */
    private Map<String, Object> sessionScope = null;


    /**
     * Creates a new instance of ServletTilesRequestContext.
     *
     * @param servletContext The servlet context.
     * @param request The request object.
     * @param response The response object.
     */
    public ServletTilesRequestContext(ServletContext servletContext,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        super(servletContext);
        initialize(request, response);
    }


    /** {@inheritDoc} */
    public Map<String, String> getHeader() {

        if ((header == null) && (request != null)) {
            header = new ServletHeaderMap(request);
        }
        return (header);

    }


    /** {@inheritDoc} */
    public Map<String, String[]> getHeaderValues() {

        if ((headerValues == null) && (request != null)) {
            headerValues = new ServletHeaderValuesMap(request);
        }
        return (headerValues);

    }


    /** {@inheritDoc} */
    public Map<String, String> getParam() {

        if ((param == null) && (request != null)) {
            param = new ServletParamMap(request);
        }
        return (param);

    }


    /** {@inheritDoc} */
    public Map<String, String[]> getParamValues() {

        if ((paramValues == null) && (request != null)) {
            paramValues = new ServletParamValuesMap(request);
        }
        return (paramValues);

    }


    /** {@inheritDoc} */
    public Map<String, Object> getRequestScope() {

        if ((requestScope == null) && (request != null)) {
            requestScope = new ServletRequestScopeMap(request);
        }
        return (requestScope);

    }


    /** {@inheritDoc} */
    public Map<String, Object> getSessionScope() {

        if ((sessionScope == null) && (request != null)) {
            sessionScope = new ServletSessionScopeMap(request);
        }
        return (sessionScope);

    }

    /** {@inheritDoc} */
    public void dispatch(String path) throws IOException {
        if (response.isCommitted()) {
            include(path);
        } else {
            forward(path);
        }
    }

    /**
     * Forwards to a path.
     *
     * @param path The path to forward to.
     * @throws IOException If something goes wrong during the operation.
     */
    private void forward(String path) throws IOException {
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            rd.forward(request, response);
        } catch (ServletException ex) {
            LOG.error("Servlet Exception while including path", ex);
            throw new IOException("Error including path '" + path + "'. "
                    + ex.getMessage());
        }
    }


    /** {@inheritDoc} */
    public void include(String path) throws IOException {
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            rd.include(request, response);
        } catch (ServletException ex) {
            LOG.error("Servlet Exception while including path", ex);
            throw new IOException("Error including path '" + path + "'. "
                    + ex.getMessage());
        }
    }

    /** {@inheritDoc} */
    public Locale getRequestLocale() {
        return request.getLocale();
    }

    /** {@inheritDoc} */
    public HttpServletRequest getRequest() {
        return request;
    }

    /** {@inheritDoc} */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * <p>Initialize (or reinitialize) this {@link ServletTilesRequestContext} instance
     * for the specified Servlet API objects.</p>
     *
     * @param request  The <code>HttpServletRequest</code> for this request
     * @param response The <code>HttpServletResponse</code> for this request
     */
    public void initialize(HttpServletRequest request,
                           HttpServletResponse response) {

        // Save the specified Servlet API object references
        this.request = request;
        this.response = response;
        // Perform other setup as needed
    }


    /**
     * <p>Release references to allocated resources acquired in
     * <code>initialize()</code> of via subsequent processing.  After this
     * method is called, subsequent calls to any other method than
     * <code>initialize()</code> will return undefined results.</p>
     */
    public void release() {
        // Release references to allocated collections
        header = null;
        headerValues = null;
        param = null;
        paramValues = null;
        requestScope = null;
        sessionScope = null;

        // Release references to Servlet API objects
        request = null;
        response = null;
        super.release();

    }


    /** {@inheritDoc} */
    public boolean isUserInRole(String role) {
        return request.isUserInRole(role);
    }
}
