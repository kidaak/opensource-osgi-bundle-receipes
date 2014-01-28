/*
 * $Id: InsertTemplateTag.java 527536 2007-04-11 15:44:51Z apetrelli $
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

import org.apache.tiles.jsp.context.JspUtil;

import javax.servlet.jsp.JspException;

/**
 * This is the tag handler for &lt;tiles:insertTemplate&gt;, which includes a
 * template ready to be filled.
 *
 * @version $Rev: 527536 $ $Date: 2007-04-11 16:44:51 +0100 (Wed, 11 Apr 2007) $
 */
public class InsertTemplateTag extends RenderTagSupport
    implements PutAttributeTagParent {

    /**
     * A string representing the URI of a template (for example, a JSP page).
     */
    private String template;

    /**
     * Returns a string representing the URI of a template (for example, a JSP
     * page).
     *
     * @return The template URI.
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Sets a string representing the URI of a template (for example, a JSP
     * page).
     *
     * @param template The template URI.
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /** {@inheritDoc} */
    protected void render() throws JspException {
        JspUtil.doInclude(pageContext, template, flush);
    }
}
