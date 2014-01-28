/*
 * $Id: MutableTilesContainer.java 537196 2007-05-11 14:07:35Z apetrelli $
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
package org.apache.tiles.mgmt;

import org.apache.tiles.Definition;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.TilesException;

/**
 * Defines a mutable version of the TilesContainer.
 *
 * @since Tiles 2.0
 * @version $Rev: 537196 $ $Date: 2007-05-11 15:07:35 +0100 (Fri, 11 May 2007) $
 */
public interface MutableTilesContainer extends TilesContainer {

    /**
     * Register a new definition with the container.
     *
     * @param definition The definition to register.
     * @param requestItems the current request objects.
     * @throws TilesException If something goes wrong during registration.
     */
    void register(Definition definition, Object... requestItems)
            throws TilesException;
}
