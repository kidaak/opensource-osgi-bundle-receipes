/*
 * $Id: TestAlternateTilesContainerFactory.java 722062 2008-12-01 13:09:09Z apetrelli $
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

package org.apache.tiles.test.factory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.definition.DefinitionsFactoryException;
import org.apache.tiles.definition.LocaleDefinitionsFactory;
import org.apache.tiles.definition.dao.BaseLocaleUrlDefinitionDAO;
import org.apache.tiles.definition.dao.CachingLocaleUrlDefinitionDAO;
import org.apache.tiles.locale.LocaleResolver;

/**
 * Test alternate Tiles container factory to customize Tiles behaviour.
 *
 * @version $Rev: 722062 $ $Date: 2008-12-01 14:09:09 +0100 (Mon, 01 Dec 2008) $
 */
public class TestAlternateTilesContainerFactory extends TestTilesContainerFactory {

    /**
     * The number of URLs to load..
     */
    private static final int URL_COUNT = 1;

    /** {@inheritDoc} */
    @Override
    protected List<URL> getSourceURLs(TilesApplicationContext applicationContext,
            TilesRequestContextFactory contextFactory) {
        List<URL> urls = new ArrayList<URL>(URL_COUNT);
        try {
            urls.add(applicationContext.getResource("/WEB-INF/tiles-alt-defs.xml"));
        } catch (IOException e) {
            throw new DefinitionsFactoryException(
                    "Cannot load definition URLs", e);
        }
        return urls;
    }

    /** {@inheritDoc} */
    @Override
    protected LocaleDefinitionsFactory instantiateDefinitionsFactory(
            TilesApplicationContext applicationContext,
            TilesRequestContextFactory contextFactory, LocaleResolver resolver) {
        return new LocaleDefinitionsFactory();
   }

    /** {@inheritDoc} */
    @Override
    protected BaseLocaleUrlDefinitionDAO instantiateLocaleDefinitionDao(
            TilesApplicationContext applicationContext,
            TilesRequestContextFactory contextFactory, LocaleResolver resolver) {
        return new CachingLocaleUrlDefinitionDAO();
    }
}
