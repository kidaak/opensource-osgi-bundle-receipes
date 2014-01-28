/*
 * $Id: AttributeTest.java 736275 2009-01-21 09:58:20Z apetrelli $
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

import junit.framework.TestCase;

/**
 * Tests {@link Attribute}.
 *
 * @version $Rev: 736275 $ $Date: 2009-01-21 10:58:20 +0100 (Wed, 21 Jan 2009) $
 */
public class AttributeTest extends TestCase {

    /**
     * Test method for {@link org.apache.tiles.Attribute#inherit(org.apache.tiles.Attribute)}.
     */
    public void testInherit() {
        Attribute attribute = new Attribute(null, null, null, (String) null);
        Attribute parentAttribute = new Attribute("value", "expression", "role", "renderer");
        attribute.inherit(parentAttribute);
        assertEquals("value", attribute.getValue());
        assertEquals("expression", attribute.getExpression());
        assertEquals("role", attribute.getRole());
        assertEquals("renderer", attribute.getRenderer());

        attribute = new Attribute("myvalue", "myexpression", "myrole", (String) "myrenderer");
        assertEquals("myvalue", attribute.getValue());
        assertEquals("myexpression", attribute.getExpression());
        assertEquals("myrole", attribute.getRole());
        assertEquals("myrenderer", attribute.getRenderer());
    }
}
