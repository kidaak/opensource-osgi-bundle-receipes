/*
 * $Id: AddListAttributeTag.java 734389 2009-01-14 13:38:06Z apetrelli $
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

package org.apache.tiles.jsp.taglib;

import org.apache.tiles.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * AddListAttribute tag implementation.
 *
 * @since Tiles 1.0
 * @version $Rev: 734389 $ $Date: 2009-01-14 14:38:06 +0100 (Wed, 14 Jan 2009) $
 */
public class AddListAttributeTag extends AddAttributeTag
    implements AddAttributeTagParent {

    /**
     * Get list defined in tag.
     *
     * @return The list of attributes.
     */
    @SuppressWarnings("unchecked")
    public List<Attribute> getAttributes() {
        return (List<Attribute>) super.getValue();
    }

    /** {@inheritDoc} */
    @Override
    public void setValue(Object object) {
        throw new IllegalStateException("The value of the PutListAttributeTag must be the originally defined list.");
    }

    /** {@inheritDoc} */
    public int doStartTag() {
        super.setValue(new ArrayList<Attribute>());
        return EVAL_BODY_BUFFERED;
    }

    /**
     * PutListAttributeTag may not have any body, except for PutAttribute tags.
     *
     * @return <code>SKIP_BODY</code>.
     */
    public int doAfterBody() {
        return (SKIP_BODY);
    }

    /**
     * Process nested &lg;addAttribute&gt; tag.
     * <p/>
     * Places the value of the nested tag within the
     * {@link org.apache.tiles.AttributeContext}.It is the responsibility
     * of the descendent to check security.  Security will be managed by called
     * tags.
     *
     * @param nestedTag the put tag desciendent.
     */
    public void processNestedTag(AddAttributeTag nestedTag) {
        Attribute attribute = new Attribute(nestedTag.getValue(), null, nestedTag
                        .getRole(), nestedTag.getType());

        this.addValue(attribute);
    }

    /**
     * Adds a value in this list.
     *
     * @param attribute The attribute to add.
     */
    private void addValue(Attribute attribute) {
        this.getAttributes().add(attribute);
    }
}
