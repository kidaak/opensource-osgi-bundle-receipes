/*
 * $Id: DestroyContainerTag.java 734110 2009-01-13 11:49:19Z apetrelli $
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
package org.apache.tiles.jsp.taglib.definition;

import org.apache.tiles.jsp.context.JspUtil;
import org.apache.tiles.jsp.taglib.TilesTag;

/**
 * Destroys the accessible container.
 *
 * @version $Rev: 734110 $ $Date: 2009-01-13 12:49:19 +0100 (Tue, 13 Jan 2009) $
 */
public class DestroyContainerTag extends TilesTag {

    /**
     * The key of the container to destroy.
     */
    private String containerKey;

    /**
     * Returns the key of the container to destroy.
     *
     * @return the containerKey The container key.
     * @since 2.1.0
     */
    public String getContainerKey() {
        return containerKey;
    }

    /**
     * Sets the key of the container to destroy.
     *
     * @param containerKey the containerKey The container key.
     * @since 2.1.0
     */
    public void setContainerKey(String containerKey) {
        this.containerKey = containerKey;
    }

    /** {@inheritDoc} */
    @Override
    protected void reset() {
        super.reset();
        this.containerKey = null;
    }

    /** {@inheritDoc} */
    @Override
    public int doEndTag() {
        JspUtil.setContainer(pageContext, null, containerKey);
        return EVAL_PAGE;
    }
}
