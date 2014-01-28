/*
 * $Id: TilesContextBeanELResolverTest.java 654850 2008-05-09 15:15:42Z apetrelli $
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
package org.apache.tiles.evaluator.el;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContext;
import org.easymock.EasyMock;

import junit.framework.TestCase;

/**
 * Tests {@link TilesContextBeanELResolver}.
 *
 * @version $Rev: 654850 $ $Date: 2008-05-09 17:15:42 +0200 (Fri, 09 May 2008) $
 */
public class TilesContextBeanELResolverTest extends TestCase {

    /**
     * The resolver to test.
     */
    private TilesContextBeanELResolver resolver;

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        resolver = new TilesContextBeanELResolver();
    }

    /**
     * Test method for
     * {@link TilesContextBeanELResolver#getCommonPropertyType(javax.el.ELContext, java.lang.Object)}.
     */
    public void testGetCommonPropertyType() {
        Class<?> clazz = resolver.getCommonPropertyType(null, null);
        assertEquals("The class is not correct", String.class, clazz);
        clazz = resolver.getCommonPropertyType(null, "Base object");
        assertNull("The class for non root objects must be null", clazz);
    }

    /**
     * Test method for
     * {@link TilesContextBeanELResolver#getFeatureDescriptors(javax.el.ELContext, java.lang.Object)}.
     */
    public void testGetFeatureDescriptors() {
        Map<String, Object> requestScope = new HashMap<String, Object>();
        Map<String, Object> sessionScope = new HashMap<String, Object>();
        Map<String, Object> applicationScope = new HashMap<String, Object>();
        requestScope.put("object1", "value");
        sessionScope.put("object2", new Integer(1));
        applicationScope.put("object3", new Float(2.0));
        TilesRequestContext request = EasyMock
                .createMock(TilesRequestContext.class);
        EasyMock.expect(request.getRequestScope()).andReturn(requestScope)
                .anyTimes();
        EasyMock.expect(request.getSessionScope()).andReturn(sessionScope)
                .anyTimes();
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        EasyMock.expect(applicationContext.getApplicationScope()).andReturn(
                applicationScope).anyTimes();
        EasyMock.replay(request, applicationContext);

        ELContext context = new ELContextImpl(resolver);
        context.putContext(TilesRequestContext.class, request);
        context.putContext(TilesApplicationContext.class, applicationContext);

        List<FeatureDescriptor> expected = new ArrayList<FeatureDescriptor>();
        resolver.collectBeanInfo(requestScope, expected);
        resolver.collectBeanInfo(sessionScope, expected);
        resolver.collectBeanInfo(applicationScope, expected);
        Iterator<FeatureDescriptor> featureIt = resolver.getFeatureDescriptors(
                context, null);
        Iterator<FeatureDescriptor> expectedIt = expected.iterator();
        while (featureIt.hasNext() && expectedIt.hasNext()) {
            FeatureDescriptor expectedDescriptor = expectedIt.next();
            FeatureDescriptor descriptor = featureIt.next();
            assertEquals("The feature is not the same", expectedDescriptor
                    .getDisplayName(), descriptor.getDisplayName());
            assertEquals("The feature is not the same", expectedDescriptor
                    .getName(), descriptor.getName());
            assertEquals("The feature is not the same", expectedDescriptor
                    .getShortDescription(), descriptor.getShortDescription());
            assertEquals("The feature is not the same", expectedDescriptor
                    .getValue("type"), descriptor.getValue("type"));
            assertEquals("The feature is not the same", expectedDescriptor
                    .getValue("resolvableAtDesignTime"), descriptor
                    .getValue("resolvableAtDesignTime"));
            assertEquals("The feature is not the same", expectedDescriptor
                    .isExpert(), descriptor.isExpert());
            assertEquals("The feature is not the same", expectedDescriptor
                    .isHidden(), descriptor.isHidden());
            assertEquals("The feature is not the same", expectedDescriptor
                    .isPreferred(), descriptor.isPreferred());
        }
        assertTrue("The feature descriptors are not of the same size",
                !featureIt.hasNext() && !expectedIt.hasNext());
    }

    /**
     * Test method for
     * {@link TilesContextBeanELResolver#getType(javax.el.ELContext, java.lang.Object, java.lang.Object)}.
     */
    public void testGetType() {
        Map<String, Object> requestScope = new HashMap<String, Object>();
        Map<String, Object> sessionScope = new HashMap<String, Object>();
        Map<String, Object> applicationScope = new HashMap<String, Object>();
        requestScope.put("object1", "value");
        sessionScope.put("object2", new Integer(1));
        applicationScope.put("object3", new Float(2.0));
        TilesRequestContext request = EasyMock
                .createMock(TilesRequestContext.class);
        EasyMock.expect(request.getRequestScope()).andReturn(requestScope)
                .anyTimes();
        EasyMock.expect(request.getSessionScope()).andReturn(sessionScope)
                .anyTimes();
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        EasyMock.expect(applicationContext.getApplicationScope()).andReturn(
                applicationScope).anyTimes();
        EasyMock.replay(request, applicationContext);

        ELContext context = new ELContextImpl(resolver);
        context.putContext(TilesRequestContext.class, request);
        context.putContext(TilesApplicationContext.class, applicationContext);

        assertEquals("The type is not correct", String.class, resolver.getType(
                context, null, "object1"));
        assertEquals("The type is not correct", Integer.class, resolver.getType(
                context, null, "object2"));
        assertEquals("The type is not correct", Float.class, resolver.getType(
                context, null, "object3"));
    }

    /**
     * Test method for
     * {@link TilesContextBeanELResolver#getValue(javax.el.ELContext, java.lang.Object, java.lang.Object)}.
     */
    public void testGetValue() {
        Map<String, Object> requestScope = new HashMap<String, Object>();
        Map<String, Object> sessionScope = new HashMap<String, Object>();
        Map<String, Object> applicationScope = new HashMap<String, Object>();
        requestScope.put("object1", "value");
        sessionScope.put("object2", new Integer(1));
        applicationScope.put("object3", new Float(2.0));
        TilesRequestContext request = EasyMock
                .createMock(TilesRequestContext.class);
        EasyMock.expect(request.getRequestScope()).andReturn(requestScope)
                .anyTimes();
        EasyMock.expect(request.getSessionScope()).andReturn(sessionScope)
                .anyTimes();
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        EasyMock.expect(applicationContext.getApplicationScope()).andReturn(
                applicationScope).anyTimes();
        EasyMock.replay(request, applicationContext);

        ELContext context = new ELContextImpl(resolver);
        context.putContext(TilesRequestContext.class, request);
        context.putContext(TilesApplicationContext.class, applicationContext);

        assertEquals("The value is not correct", "value", resolver.getValue(
                context, null, "object1"));
        assertEquals("The value is not correct", new Integer(1), resolver
                .getValue(context, null, "object2"));
        assertEquals("The value is not correct", new Float(2.0), resolver
                .getValue(context, null, "object3"));
    }

    /**
     * Test method for
     * {@link TilesContextBeanELResolver#isReadOnly(javax.el.ELContext, java.lang.Object, java.lang.Object)}.
     */
    public void testIsReadOnlyELContextObjectObject() {
        ELContext context = new ELContextImpl(resolver);
        assertTrue("The value is not read only", resolver.isReadOnly(context,
                null, null));
    }

    /**
     * Test method for
     * {@link TilesContextBeanELResolver#findObjectByProperty(javax.el.ELContext, java.lang.Object)}.
     */
    public void testFindObjectByProperty() {
        Map<String, Object> requestScope = new HashMap<String, Object>();
        Map<String, Object> sessionScope = new HashMap<String, Object>();
        Map<String, Object> applicationScope = new HashMap<String, Object>();
        requestScope.put("object1", "value");
        sessionScope.put("object2", new Integer(1));
        applicationScope.put("object3", new Float(2.0));
        TilesRequestContext request = EasyMock
                .createMock(TilesRequestContext.class);
        EasyMock.expect(request.getRequestScope()).andReturn(requestScope)
                .anyTimes();
        EasyMock.expect(request.getSessionScope()).andReturn(sessionScope)
                .anyTimes();
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        EasyMock.expect(applicationContext.getApplicationScope()).andReturn(
                applicationScope).anyTimes();
        EasyMock.replay(request, applicationContext);

        ELContext context = new ELContextImpl(resolver);
        context.putContext(TilesRequestContext.class, request);
        context.putContext(TilesApplicationContext.class, applicationContext);

        assertEquals("The value is not correct", "value", resolver
                .findObjectByProperty(context, "object1"));
        assertEquals("The value is not correct", new Integer(1), resolver
                .findObjectByProperty(context, "object2"));
        assertEquals("The value is not correct", new Float(2.0), resolver
                .findObjectByProperty(context, "object3"));
    }

    /**
     * Test method for
     * {@link org.apache.tiles.evaluator.el.TilesContextBeanELResolver#getObject(java.util.Map, java.lang.String)}.
     */
    public void testGetObject() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("object1", "value");
        assertEquals("The value is not correct", "value", resolver.getObject(
                map, "object1"));
        assertNull("The value is not null", resolver.getObject(map, "object2"));
        assertNull("The value is not null", resolver.getObject(null, "object1"));
    }

}
