/*
 * $Id: UseAttributeTag.java 727707 2008-12-18 12:35:57Z apetrelli $
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

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;


/**
 * Exposes am attribute as a scripting variable within the page.
 *
 * @since Tiles 1.0
 * @version $Rev: 727707 $ $Date: 2008-12-18 13:35:57 +0100 (Thu, 18 Dec 2008) $
 */
public class UseAttributeTag extends AttributeTagSupport {

    /**
     * Class name of object.
     */
    private String classname = null;

    /**
     * Get class name.
     *
     * @return class name
     */
    public String getClassname() {
        return classname;

    }

    /**
     * Set the class name.
     *
     * @param name The new class name.
     */
    public void setClassname(String name) {
        this.classname = name;
    }

    /** {@inheritDoc} */
    protected void reset() {
        super.reset();
        classname = null;
        id = null;
    }

    /**
     * Expose the requested attribute from attribute context.
     */
    public void execute() {
        pageContext.setAttribute(getScriptingVariable(), attributeValue, scope);
    }

    /**
     * Returns the scripting variable to use.
     *
     * @return The scripting variable.
     */
    public String getScriptingVariable() {
        return id == null ? getName() : id;
    }


    /**
     * Implementation of <code>TagExtraInfo</code> which identifies
     * the scripting object(s) to be made visible.
     */
    public static class Tei extends TagExtraInfo {

        /** {@inheritDoc} */
        public VariableInfo[] getVariableInfo(TagData data) {
            String classname = data.getAttributeString("classname");
            if (classname == null) {
                classname = "java.lang.Object";
            }

            String id = data.getAttributeString("id");
            if (id == null) {
                id = data.getAttributeString("name");
            }

            return new VariableInfo[] {
                new VariableInfo(id, classname, true, VariableInfo.AT_END)
            };

        }
    }
}
