/*
 * $Id: TestAlternateTilesListener.java 733753 2009-01-12 13:52:54Z apetrelli $
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
package org.apache.tiles.test.listener;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.startup.BasicTilesInitializer;
import org.apache.tiles.startup.TilesInitializer;
import org.apache.tiles.test.factory.TestAlternateTilesContainerFactory;
import org.apache.tiles.web.startup.TilesListener;

/**
 * Test Tiles listener for Tiles initialization of the alternate container.
 *
 * @version $Rev: 733753 $ $Date: 2009-01-12 14:52:54 +0100 (Mon, 12 Jan 2009) $
 */
public class TestAlternateTilesListener extends TilesListener {

    /** {@inheritDoc} */
    @Override
    protected TilesInitializer createTilesInitializer() {
        return new TestAlternateTilesInitializer();
    }

    /**
     * Test Tiles initializer for Tiles initialization of the alternate container.
     */
    private static class TestAlternateTilesInitializer extends BasicTilesInitializer {

        /** {@inheritDoc} */
        @Override
        protected AbstractTilesContainerFactory createContainerFactory(
                TilesApplicationContext context) {
            return new TestAlternateTilesContainerFactory();
        }

        /** {@inheritDoc} */
        @Override
        protected String getContainerKey(
                TilesApplicationContext applicationContext) {
            return "alternate";
        }
    }
}
