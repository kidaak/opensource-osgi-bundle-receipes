/*
 * $Id: PutAttributeTagParent.java 637434 2008-03-15 15:48:38Z apetrelli $
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

/**
 * Tag classes implementing this interface can contain nested {@link PutAttributeTag}.
 * This interface defines a method called by nested tags.
 *
 * @version $Rev: 637434 $ $Date: 2008-03-15 16:48:38 +0100 (Sat, 15 Mar 2008) $
 */
public interface PutAttributeTagParent {
    /**
     * Process the nested tag.
     *
     * @param nestedTag Nested tag to process.
     * @throws TilesJspException If something goes wrong during processing.
     */
    void processNestedTag(PutAttributeTag nestedTag) throws TilesJspException;

}
