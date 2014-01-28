/*
 * $Id: RenderTag.java 734996 2009-01-16 13:27:28Z apetrelli $
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

import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.ListAttribute;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.jsp.context.JspUtil;

/**
 * <p>
 * Abstracts class for all tags which render (an attribute, a template, or
 * definition).
 * </p>
 * <p>
 * Properly invokes the defined preparer and invokes the abstract render method
 * upon completion.
 * </p>
 * This tag takes special care to ensure that the attribute context is reset to
 * it's original state after the execution of the tag is complete. This ensures
 * that all all included attributes in subsequent tiles are scoped properly and
 * do not bleed outside their intended scope.
 *
 * @since 2.1.1
 * @version $Rev: 734996 $ $Date: 2009-01-16 14:27:28 +0100 (Fri, 16 Jan 2009) $
 */
public abstract class RenderTag extends TilesBodyTag implements
        PutAttributeTagParent, PutListAttributeTagParent {

    /**
     * The log instance for this tag.
     */
    private final Log log = LogFactory.getLog(RenderTag.class);

    /**
     * The role to check. If the user is in the specified role, the tag is taken
     * into account; otherwise, the tag is ignored (skipped).
     *
     * @since 2.1.1
     */
    protected String role;

    /**
     * The view preparer to use before the rendering.
     *
     * @since 2.1.1
     */
    protected String preparer;

    /**
     * This flag, if <code>true</code>, flushes the content before rendering.
     *
     * @since 2.1.1
     */
    protected boolean flush;

    /**
     * This flag, if <code>true</code>, ignores exception thrown by preparers
     * and those caused by problems with definitions.
     *
     * @since 2.1.1
     */
    protected boolean ignore;

    /**
     * The Tiles container that can be used inside the tag.
     *
     * @since 2.1.1
     */
    protected TilesContainer container;

    /**
     * The attribute context to use to store and read attribute values.
     *
     * @since 2.1.1
     */
    protected AttributeContext attributeContext;

    /**
     * Returns the role to check. If the user is in the specified role, the tag is
     * taken into account; otherwise, the tag is ignored (skipped).
     *
     * @return The role to check.
     * @since 2.1.1
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role to check. If the user is in the specified role, the tag is
     * taken into account; otherwise, the tag is ignored (skipped).
     *
     * @param role The role to check.
     * @since 2.1.1
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns the preparer name.
     *
     * @return The preparer name.
     * @since 2.1.1
     */
    public String getPreparer() {
        return preparer;
    }

    /**
     * Sets the preparer name.
     *
     * @param preparer The preparer name.
     * @since 2.1.1
     */
    public void setPreparer(String preparer) {
        this.preparer = preparer;
    }

    /**
     * Returns the flush flag. If <code>true</code>, current page out stream
     * is flushed before insertion.
     *
     * @return The flush flag.
     * @since 2.1.1
     */
    public boolean isFlush() {
        return flush;
    }

    /**
     * Sets the flush flag. If <code>true</code>, current page out stream
     * is flushed before insertion.
     *
     * @param flush The flush flag.
     * @since 2.1.1
     */
    public void setFlush(boolean flush) {
        this.flush = flush;
    }

    /**
     * Returns the ignore flag. If it is set to true, and the attribute
     * specified by the name does not exist, simply return without writing
     * anything. The default value is false, which will cause a runtime
     * exception to be thrown.
     *
     * @return The ignore flag.
     * @since 2.1.1
     */
    public boolean isIgnore() {
        return ignore;
    }

    /**
     * Sets the ignore flag. If this attribute is set to true, and the attribute
     * specified by the name does not exist, simply return without writing
     * anything. The default value is false, which will cause a runtime
     * exception to be thrown.
     *
     * @param ignore The ignore flag.
     * @since 2.1.1
     */
    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    /** {@inheritDoc} */
    @Override
    protected void reset() {
        super.reset();
        preparer = null;
        flush = false;
        ignore = false;
        container = null;
        attributeContext = null;
        role = null;
    }

    /** {@inheritDoc} */
    public int doStartTag() throws TilesJspException {
        container = JspUtil.getCurrentContainer(pageContext);
        if (container != null) {
            startContext(pageContext);
            return EVAL_BODY_BUFFERED;
        } else {
            throw new TilesJspException("TilesContainer not initialized");
        }
    }

    /** {@inheritDoc} */
    public int doEndTag() throws TilesJspException {
        try {
            render();
            if (flush) {
                pageContext.getOut().flush();
            }

            return EVAL_PAGE;
        } catch (IOException io) {
            String message = "IO Error executing tag: " + io.getMessage();
            log.error(message, io);
            throw new TilesJspException(message, io);
        } finally {
            endContext(pageContext);
        }
    }

    /**
     * Render the specified content.
     *
     * @throws TilesJspException if a jsp exception occurs.
     * @throws IOException if an io exception occurs.
     */
    protected abstract void render() throws TilesJspException, IOException;

    /**
     * Starts the context when entering the tag.
     *
     * @param context The page context to use.
     */
    protected void startContext(PageContext context) {
        if (container != null) {
            attributeContext = container.startContext(pageContext);
        }
    }

    /**
     * Ends the context when exiting the tag.
     *
     * @param context The page context to use.
     */
    protected void endContext(PageContext context) {
        if (attributeContext != null && container != null) {
            container.endContext(pageContext);
        }
    }

    /**
     * <p>
     * Process nested &lg;put&gt; tag.
     * <p/>
     * <p>
     * Places the value of the nested tag within the
     * {@link org.apache.tiles.AttributeContext}.It is the responsibility
     * of the descendent to check security. Security will be managed by
     * called tags.
     * </p>
     *
     * @param nestedTag the put tag desciendent.
     */
    public void processNestedTag(PutAttributeTag nestedTag) {
        Attribute attribute = new Attribute(
            nestedTag.getValue(), null,
            nestedTag.getRole(), nestedTag.getType());

        attributeContext.putAttribute(nestedTag.getName(), attribute, nestedTag
                .isCascade());
    }

    /** {@inheritDoc} */
    public void processNestedTag(PutListAttributeTag nestedTag) {
        ListAttribute attribute = new ListAttribute(nestedTag.getAttributes());
        attribute.setRole(nestedTag.getRole());
        attribute.setInherit(nestedTag.getInherit());

        attributeContext.putAttribute(nestedTag.getName(), attribute, nestedTag
                .isCascade());
    }
}
