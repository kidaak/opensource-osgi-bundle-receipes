/*
 * $Id: ImportAttributeTag.java 734996 2009-01-16 13:27:28Z apetrelli $
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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tiles.Attribute;
import org.apache.tiles.TilesException;

import java.util.Collection;


/**
 * Import attribute(s) into the specified scope.
 * If not explicitly named, all attributes are imported.
 * If the scope is not specified, page scope is assumed.
 *
 * @since Tiles 1.0
 * @version $Rev: 734996 $ $Date: 2009-01-16 14:27:28 +0100 (Fri, 16 Jan 2009) $
 */
public class ImportAttributeTag extends AttributeTagSupport {

    /**
     * The logging object.
     */
    private final Log log = LogFactory.getLog(ImportAttributeTag.class);

    /**
     * The destination attribute name.
     */
    private String toName;

    /**
     * <p>
     * Returns the name of the destination attribute. If not specified, the name
     * will be the same as specified in <code>name</code> attribute
     * </p>
     *
     * @return The destination attribute name.
     */
    public String getToName() {
        return toName;
    }

    /**
     * <p>
     * Sets the name of the destination attribute. If not specified, the name
     * will be the same as specified in <code>name</code> attribute
     * </p>
     *
     * @param toName The destination attribute name.
     */
    public void setToName(String toName) {
        this.toName = toName;
    }

    /** {@inheritDoc} */
    @Override
    protected void reset() {
        super.reset();
        this.toName = null;
    }

    /**
     * Expose the requested property from attribute context.
     *
     * @throws TilesJspException On errors processing tag.
     */
    public void execute() throws TilesJspException {
        if (attributeValue != null) {
            pageContext.setAttribute(toName != null ? toName : name,
                    attributeValue, scope);
        } else {
            importAttributes(attributeContext.getCascadedAttributeNames());
            importAttributes(attributeContext.getLocalAttributeNames());
        }
    }

    /**
     * Imports an attribute set.
     *
     * @param names The names of the attributes to be imported.
     * @throws TilesJspException If something goes wrong during the import.
     */
    private void importAttributes(Collection<String> names)
            throws TilesJspException {
        if (names == null || names.isEmpty()) {
            return;
        }

        for (String name : names) {
            if (name == null && !ignore) {
                throw new TilesJspException("Error importing attributes. "
                        + "Attribute with null key found.");
            } else if (name == null) {
                continue;
            }

            Attribute attr = attributeContext.getAttribute(name);

            if (attr != null) {
                try {
                    Object attributeValue = container.evaluate(attr, pageContext);
                    if (attributeValue == null) {
                        throw new TilesJspException(
                                "Error importing attributes. " + "Attribute '"
                                        + name + "' has a null value ");
                    }
                    pageContext.setAttribute(name, attributeValue, scope);
                } catch (TilesException e) {
                    if (!ignore) {
                        throw e;
                    } else if (log.isDebugEnabled()) {
                        log.debug("Ignoring Tiles Exception", e);
                    }
                }
            } else if (!ignore) {
                throw new TilesJspException("Error importing attributes. "
                        + "Attribute '" + name + "' is null");
            }

        }
    }
}
