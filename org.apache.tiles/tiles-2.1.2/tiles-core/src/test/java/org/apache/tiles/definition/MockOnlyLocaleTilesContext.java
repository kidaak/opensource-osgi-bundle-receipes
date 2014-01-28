/*
 * $Id: MockOnlyLocaleTilesContext.java 736275 2009-01-21 09:58:20Z apetrelli $
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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContext;

/**
 * Creates a TilesApplicationContext that contains only a Locale.
 *
 * @version $Rev: 736275 $ $Date: 2009-01-21 10:58:20 +0100 (Wed, 21 Jan 2009) $
 */
public class MockOnlyLocaleTilesContext implements TilesRequestContext {

    /**
     * The locale object.
     */
    private Locale locale;

    /** Creates a new instance of MockOnlyLocaleTilesContext.
     *
     * @param locale The locale object to use.
     */
    public MockOnlyLocaleTilesContext(Locale locale) {
        this.locale = locale;
    }

    /**
     * Returns the locale specified in the constructor.
     *
     * @return The locale of the request.
     * @see org.apache.tiles.context.TilesRequestContext#getRequestLocale()
     */
    public Locale getRequestLocale() {
        return locale;
    }

    // The rest of the implemented methods has a "dummy" behaviour, doing
    // nothing or returning null, because they are not needed at all in tests
    // that use this class.

    /** {@inheritDoc} */
    public void dispatch(String path) throws IOException {
    }

    /** {@inheritDoc} */
    public Map<String, String> getHeader() {
        return null;
    }

    /** {@inheritDoc} */
    public OutputStream getOutputStream() {
        return null;
    }

    /** {@inheritDoc} */
    public Writer getWriter() {
        return null;
    }

    /** {@inheritDoc} */
    public PrintWriter getPrintWriter() throws IOException {
        return null;
    }

    /** {@inheritDoc} */
    public Map<String, String[]> getHeaderValues() {
        return null;
    }

    /** {@inheritDoc} */
    public Map<String, String> getParam() {
        return null;
    }

    /** {@inheritDoc} */
    public Map<String, String[]> getParamValues() {
        return null;
    }

    /** {@inheritDoc} */
    public Map<String, Object> getRequestScope() {
        return null;
    }

    /** {@inheritDoc} */
    public Map<String, Object> getSessionScope() {
        return null;
    }

    /** {@inheritDoc} */
    public TilesApplicationContext getApplicationContext() {
        return null;
    }

    /** {@inheritDoc} */
    public void include(String path) throws IOException {
    }

    /** {@inheritDoc} */
    public boolean isUserInRole(String role) {
        return false;
    }

    /** {@inheritDoc} */
    public Object[] getRequestObjects() {
        return null;
    }

    /** {@inheritDoc} */
    public Object getRequest() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /** {@inheritDoc} */
    public Object getResponse() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
