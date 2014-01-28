/*
 * $Id: WildcardPortletTilesApplicationContextFactory.java 711885 2008-11-06 16:06:38Z apetrelli $
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

package org.apache.tiles.portlet.context.wildcard;

import javax.portlet.PortletContext;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.portlet.context.PortletTilesApplicationContextFactory;

/**
 * In the {@link #createApplicationContext(Object)} method creates an instance
 * of {@link WildcardPortletTilesApplicationContext}.
 *
 * @version $Rev: 711885 $ $Date: 2008-11-06 17:06:38 +0100 (Thu, 06 Nov 2008) $
 * @since 2.1.1
 */
public class WildcardPortletTilesApplicationContextFactory extends
        PortletTilesApplicationContextFactory {

    /** {@inheritDoc} */
    @Override
    public TilesApplicationContext createApplicationContext(Object context) {
        if (context instanceof PortletContext) {
            PortletContext portletContext = (PortletContext) context;
            return new WildcardPortletTilesApplicationContext(portletContext);
        }
        return null;
    }
}
