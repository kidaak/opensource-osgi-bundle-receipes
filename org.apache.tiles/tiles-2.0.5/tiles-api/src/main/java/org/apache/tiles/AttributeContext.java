/*
 * $Id: AttributeContext.java 527536 2007-04-11 15:44:51Z apetrelli $
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
package org.apache.tiles;

import java.util.Map;
import java.util.Iterator;

/**
 * Encapsulation of the current state of execution.
 *
 * @since Tiles 2.0
 * @version $Rev: 527536 $ $Date: 2007-04-11 16:44:51 +0100 (Wed, 11 Apr 2007) $
 */
public interface AttributeContext {

    /**
     * Add all attributes to the context.
     *
     * @param newAttributes the attributes to be added.
     */
    void addAll(Map<String, Attribute> newAttributes);

    /**
     * Add all attributes to the context.
     *
     * @param defaultAttributes attributes which should be present.
     */
    void addMissing(Map<String, Attribute> defaultAttributes);

    /**
     * Retrieve the named attribute.
     *
     * @param name key name for the attribute.
     * @return Attribute associated with the given name.
     */
    Attribute getAttribute(String name);

    /**
     * Iterator of all attribute names.
     *
     * @return iterator of all names.
     */
    Iterator<String> getAttributeNames();

    /**
     * Add the specified attribute.
     *
     * @param name name of the attribute
     * @param value value of the attribute
     */
    void putAttribute(String name, Attribute value);

    /**
     * Clear the attributes.
     */
    void clear();
}
