/*
 * $Id: AttributeTagSupport.java 734996 2009-01-16 13:27:28Z apetrelli $
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.TilesException;
import org.apache.tiles.jsp.context.JspUtil;

/**
 * Support for Scoped tags.
 *
 * @version $Rev: 734996 $ $Date: 2009-01-16 14:27:28 +0100 (Fri, 16 Jan 2009) $
 */
public abstract class AttributeTagSupport extends TilesTag {

    /**
     * The logging object.
     */
    private final Log log = LogFactory.getLog(AttributeTagSupport.class);

    /**
     * Maps scope names to their constants.
     */
    private static final Map<String, Integer> SCOPES =
        new HashMap<String, Integer>();

    static {
        SCOPES.put("page", PageContext.PAGE_SCOPE);
        SCOPES.put("request", PageContext.REQUEST_SCOPE);
        SCOPES.put("session", PageContext.SESSION_SCOPE);
        SCOPES.put("application", PageContext.APPLICATION_SCOPE);
    }


    /**
     * The scope name.
     */
    protected String scopeName = null;

    /**
     * The scope.
     */
    protected int scope = PageContext.PAGE_SCOPE;

    /**
     * The name of the attribute.
     */
    protected String name = null;

    /**
     * Flag that, if <code>true</code>, ignores exceptions.
     */
    protected boolean ignore = false;


    /**
     * The Tiles container to use.
     */
    protected TilesContainer container;

    /**
     * The current attribute context.
     */
    protected AttributeContext attributeContext;

    /**
     * The found attribute.
     */
    protected Attribute attribute;

    /**
     * The attribute value.
     *
     * @since 2.1.0
     */
    protected Object attributeValue;

    /**
     * Set the scope.
     *
     * @param scope Scope.
     */
    public void setScope(String scope) {
        this.scopeName = scope;
    }

    /**
     * Get scope.
     *
     * @return Scope.
     */
    public String getScope() {
        return scopeName;
    }

    /** {@inheritDoc} */
    @Override
    protected void reset() {
        super.reset();
        scopeName = null;
        scope = PageContext.PAGE_SCOPE;
        ignore = false;
        attribute = null;
        attributeValue = null;
        attributeContext = null;
    }

    /** {@inheritDoc} */
    public int doStartTag() throws TilesJspException {
        container = JspUtil.getCurrentContainer(pageContext);
        attributeContext = container.getAttributeContext(pageContext);
        scope = getScopeId();

        // Some tags allow for unspecified attribues.  This
        // implies that the tag should use all of the attributes.
        if (name != null) {
            attribute = attributeContext.getAttribute(name);
            if ((attribute == null || attribute.getValue() == null) && ignore) {
                return SKIP_BODY;
            }

            if (attribute == null) {
                throw new TilesJspException("Attribute with name '" + name
                        + "' not found");
            }

            try {
                attributeValue = container.evaluate(attribute, pageContext);
            } catch (TilesException e) {
                if (!ignore) {
                    throw e;
                } else if (log.isDebugEnabled()) {
                    log.debug("Ignoring Tiles Exception", e);
                }
            }

            if (attributeValue == null) {
                throw new TilesJspException("Attribute with name '" + name
                        + "' has a null value.");
            }
        }

        try {
            execute();
        } catch (IOException e) {
            throw new TilesJspException("io error while executing tag '"
                    + getClass().getName() + "'.", e);
        }

        return SKIP_BODY;
    }

    /**
     * Execute this tag. It is called inside {@link #doEndTag()}.
     *
     * @throws TilesJspException If something goes wrong during rendering.
     * @throws IOException If something goes wrong during writing content.
     */
    public abstract void execute() throws TilesJspException, IOException;

    /** {@inheritDoc} */
    public int doEndTag() {
        return EVAL_PAGE;
    }

    /**
     * Get scope value from string value.
     *
     * @return Scope as an <code>int</code>, or <code>defaultValue</code> if scope is <code>null</code>.
     * @throws TilesJspException Scope name is not recognized as a valid scope.
     */
    public int getScopeId() throws TilesJspException {
        if (scopeName == null) {
            return PageContext.PAGE_SCOPE;
        }

        return getScope(scopeName);
    }

    /**
     * Converts the scope name into its corresponding PageContext constant value.
     *
     * @param scopeName Can be "page", "request", "session", or "application" in any
     *                  case.
     * @return The constant representing the scope (ie. PageContext.REQUEST_SCOPE).
     * @throws TilesJspException if the scopeName is not a valid name.
     */
    public static int getScope(String scopeName) throws TilesJspException {
        Integer scope = SCOPES.get(scopeName.toLowerCase());

        if (scope == null) {
            throw new TilesJspException("Unable to retrieve the scope "
                    + scopeName);
        }

        return scope;
    }

    /**
     * Get the name.
     *
     * @return Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set ignore flag.
     *
     * @param ignore default: <code>false</code>: Exception is thrown when attribute is not found, set to <code>
     *               true</code> to ignore missing attributes silently
     */
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    /**
     * Get ignore flag.
     *
     * @return default: <code>false</code>: Exception is thrown when attribute is not found, set to <code>
     *         true</code> to ignore missing attributes silently
     */
    public boolean getIgnore() {
        return ignore;
    }
}
