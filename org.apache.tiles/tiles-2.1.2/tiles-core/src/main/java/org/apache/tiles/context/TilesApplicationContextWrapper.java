/*
 * $Id: TilesApplicationContextWrapper.java 709140 2008-10-30 11:07:50Z apetrelli $
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
package org.apache.tiles.context;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.apache.tiles.TilesApplicationContext;

/**
 * Delegate for ease of customization.
 *
 * @since Tiles 2.1.1
 * @version $Rev: 709140 $ $Date: 2008-10-30 12:07:50 +0100 (Thu, 30 Oct 2008) $
 */
public class TilesApplicationContextWrapper implements TilesApplicationContext {

    /**
     * The original context.
     */
    private TilesApplicationContext context;

    /**
     * Constructor.
     *
     * @param context The original context.
     */
    public TilesApplicationContextWrapper(TilesApplicationContext context) {
        this.context = context;
    }

    /**
     * Returns the wrapped application context.
     *
     * @return The wrapped application context.
     */
    public TilesApplicationContext getWrappedApplicationContext() {
        return context;
    }

    /** {@inheritDoc} */
    public Map<String, Object> getApplicationScope() {
        return context.getApplicationScope();
    }

    /** {@inheritDoc} */
    public Object getContext() {
        return context.getContext();
    }

    /** {@inheritDoc} */
    public Map<String, String> getInitParams() {
        return context.getInitParams();
    }

    /** {@inheritDoc} */
    public URL getResource(String path) throws IOException {
        return getResource(path);
    }

    /** {@inheritDoc} */
    public Set<URL> getResources(String path) throws IOException {
        return getResources(path);
    }
}
