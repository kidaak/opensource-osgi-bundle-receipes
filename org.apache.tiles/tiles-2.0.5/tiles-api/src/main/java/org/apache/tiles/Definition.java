/*
 * $Id: Definition.java 537196 2007-05-11 14:07:35Z apetrelli $
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
import java.util.HashMap;

import org.apache.tiles.Attribute.AttributeType;

/**
 * A definition, i.e. a template with (completely or not) filled attributes.
 * Attributes of a template can be defined with the help of this class.<br>
 * It can be used as a data transfer object used for registering new
 * definitions with the Container.
 *
 * @since Tiles 2.0
 * @version $Rev: 537196 $ $Date: 2007-05-11 15:07:35 +0100 (Fri, 11 May 2007) $
 */
public class Definition {
    /**
     * Extends attribute value.
     */
    protected String inherit;
    /**
     * Definition name.
     */
    protected String name = null;
    /**
     * Template path.
     */
    protected String template = null;
    /**
     * Attributes defined for the definition.
     */
    protected Map<String, Attribute> attributes = null;
    /**
     * Role associated to definition.
     */
    protected String role = null;
    /**
     * Associated ViewPreparer URL or classname, if defined.
     */
    protected String preparer = null;


    /**
     * Constructor.
     */
    public Definition() {
        attributes = new HashMap<String, Attribute>();
    }

    /**
     * Copy Constructor.
     * Create a new definition initialized with parent definition.
     * Do a shallow copy : attributes are shared between copies, but not the Map
     * containing attributes.
     *
     * @param definition The definition to copy.
     */
    public Definition(Definition definition) {
        attributes = new HashMap<String, Attribute>(
            definition.getAttributes());
        this.name = definition.getName();
        this.template = definition.getTemplate();
        this.role = definition.getRole();
        this.preparer = definition.getPreparer();
        this.inherit = definition.getExtends();
    }

    /**
     * Constructor.
     * @param name The name of the definition.
     * @param template The template of the definition.
     * @param attributes The attribute map of the definition.
     */
    public Definition(String name, String template,
                               Map<String, Attribute> attributes) {
        this.name = name;
        this.template = template;
        this.attributes = attributes;
    }

    /**
     * Access method for the name property.
     *
     * @return the current value of the name property
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param aName the new value of the name property
     */
    public void setName(String aName) {
        name = aName;
    }

    /**
     * Access method for the template property.
     *
     * @return the current value of the template property
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Sets the value of the template property.
     *
     * @param template the new value of the path property
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Access method for the role property.
     *
     * @return the current value of the role property
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     *
     * @param role the new value of the path property
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Access method for the attributes property.
     * If there is no attributes, return an empty map.
     *
     * @return the current value of the attributes property
     */
    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

    /**
     * Returns the attribute for the given name, or null if no attribute of the
     * given name exists.
     *
     * @param key name of the attribute
     * @return requested attribute or null if not found
     */
    public Attribute getAttribute(String key) {
        return attributes.get(key);
    }

    /**
     * Put a new attribute in this definition.
     *
     * @param key   String key for attribute
     * @param value Attibute value.
     */
    public void putAttribute(String key, Attribute value) {
        attributes.put(key, value);
    }

    /**
     * Add an attribute to this definition.
     * <p/>
     * This method is used by Digester to load definitions.
     *
     * @param attribute Attribute to add.
     */
    public void addAttribute(Attribute attribute) {
        putAttribute(attribute.getName(), attribute);
    }

    /**
     * Checks whether the <code>key</code> attribute has been set.
     *
     * @param key The attribute key to check.
     * @return <code>true</code> if the attribute has a value.
     */
    public boolean hasAttributeValue(String key) {
        return attributes.containsKey(key);
    }

    /**
     * Put an attribute in template definition.
     * Attribute can be used as content for tag get.
     *
     * @param name    Attribute name
     * @param content Attribute value
     */
    public void put(String name, Object content) {
        put(name, content, null);
    }

    /**
     * Put an attribute in template definition.
     * Attribute can be used as content for tag get.
     *
     * @param name    Attribute name
     * @param content Attribute value
     * @param role    Determine if content is used by get tag. If user is in role, content is used.
     */
    public void put(String name, Object content, String role) {
        put(name, content, null, role);
    }

    /**
     * Put an attribute in template definition.
     * Attribute can be used as content for tag get.
     *
     * @param name    Attribute name
     * @param content Attribute value
     * @param type    attribute type: template, string, definition
     * @param role    Determine if content is used by get tag. If user is in role, content is used.
     */
    public void put(String name, Object content, AttributeType type, String role) {
        // Is there a type set ?
        // First check direct attribute, and translate it to a valueType.
        // Then, evaluate valueType, and create requested typed attribute.
        Attribute attribute = new Attribute(content, role, type);
        putAttribute(name, attribute);
    }

    /**
     * Get associated preparerInstance.
     *
     * @return The preparer name.
     */
    public String getPreparer() {
        return preparer;
    }

    /**
     * Set associated preparerInstance URL.
     *
     * @param url Url called locally
     */
    public void setPreparer(String url) {
        this.preparer = url;
    }

    /**
     * Set extends.
     *
     * @param name Name of the extended definition.
     */
    public void setExtends(String name) {
        inherit = name;
    }

    /**
     * Get extends.
     *
     * @return Name of the extended definition.
     */
    public String getExtends() {
        return inherit;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    /**
     * Get extends flag.
     *
     * @return <code>true</code> if this definition extends another.
     */
    public boolean isExtending() {
        return inherit != null;
    }

    /**
     * Returns a description of the attributes.
     *
     * @return A string representation of the content of this definition.
     */
    public String toString() {
        return "{name="
            + name
            + ", template="
            + template
            + ", role="
            + role
            + ", preparerInstance="
            + preparer
            + ", attributes="
            + attributes
            + "}\n";
    }
}
