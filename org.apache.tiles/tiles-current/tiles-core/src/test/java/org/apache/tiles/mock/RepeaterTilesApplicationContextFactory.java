/*
 * $Id: RepeaterTilesApplicationContextFactory.java 722062 2008-12-01 13:09:09Z apetrelli $
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
package org.apache.tiles.mock;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.AbstractTilesApplicationContextFactory;

/**
 * "Repeats" (i.e. returns back) the context as a
 * {@link TilesApplicationContext}.
 *
 * @version $Rev: 722062 $ $Date: 2008-12-01 14:09:09 +0100 (Mon, 01 Dec 2008) $
 */
public class RepeaterTilesApplicationContextFactory extends
        AbstractTilesApplicationContextFactory {

    /**
     * The application context.
     */
    private TilesApplicationContext applicationContext;

    /**
     * Constructor.
     */
    public RepeaterTilesApplicationContextFactory() {
        applicationContext = new MockTilesApplicationContext();
    }

    /**
     * Constructor.
     *
     * @param applicationContext The application context to use.
     */
    public RepeaterTilesApplicationContextFactory(
            TilesApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /** {@inheritDoc} */
    public TilesApplicationContext createApplicationContext(Object context) {
        if (context instanceof TilesApplicationContext) {
            return (TilesApplicationContext) context;
        } else {
            return applicationContext;
        }
    }

    /** {@inheritDoc} */
    public void init(Map<String, String> configurationParameters) {
        // Do nothing
    }

    /**
     * Empty application context that does nothing.
     */
    public class MockTilesApplicationContext implements TilesApplicationContext {

        /** {@inheritDoc} */
        public Map<String, Object> getApplicationScope() {
            return null;
        }

        /** {@inheritDoc} */
        public Object getContext() {
            return null;
        }

        /** {@inheritDoc} */
        public Map<String, String> getInitParams() {
            return null;
        }

        /** {@inheritDoc} */
        public URL getResource(String path) throws IOException {
            return null;
        }

        /** {@inheritDoc} */
        public Set<URL> getResources(String path) throws IOException {
            return null;
        }

    }
}
