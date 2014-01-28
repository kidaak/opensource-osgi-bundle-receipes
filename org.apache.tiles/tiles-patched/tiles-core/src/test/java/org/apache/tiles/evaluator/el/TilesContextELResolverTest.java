/*
 * $Id: TilesContextELResolverTest.java 654850 2008-05-09 15:15:42Z apetrelli $
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.ELContext;

import junit.framework.TestCase;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContext;
import org.easymock.EasyMock;

/**
 * Tests {@link TilesContextELResolver}.
 *
 * @version $Rev: 654850 $ $Date: 2008-05-09 17:15:42 +0200 (Fri, 09 May 2008) $
 */
public class TilesContextELResolverTest extends TestCase {

    /**
     * The resolver to test.
     */
    private TilesContextELResolver resolver;

    /** {@inheritDoc} */
    protected void setUp() throws Exception {
        super.setUp();
        resolver = new TilesContextELResolver();
    }

    /**
     * Test method for
     * {@link TilesContextELResolver#getCommonPropertyType(javax.el.ELContext, java.lang.Object)}.
     */
    public void testGetCommonPropertyTypeELContextObject() {
        Class<?> clazz = resolver.getCommonPropertyType(null, null);
        assertEquals("The class is not correct", String.class, clazz);
        clazz = resolver.getCommonPropertyType(null, "Base object");
        assertNull("The class for non root objects must be null", clazz);
    }

    /**
     * Test method for
     * {@link TilesContextELResolver#getFeatureDescriptors(javax.el.ELContext, java.lang.Object)}.
     */
    public void testGetFeatureDescriptorsELContextObject() {
        List<FeatureDescriptor> expected = new ArrayList<FeatureDescriptor>();
        Set<String> properties = new HashSet<String>();
        resolver.collectBeanInfo(TilesRequestContext.class, expected,
                properties);
        resolver.collectBeanInfo(TilesApplicationContext.class, expected,
                properties);
        Iterator<FeatureDescriptor> featureIt = resolver.getFeatureDescriptors(
                null, null);
        Iterator<FeatureDescriptor> expectedIt = expected.iterator();
        while (featureIt.hasNext() && expectedIt.hasNext()) {
            assertEquals("The feature is not the same", expectedIt.next(),
                    featureIt.next());
        }
        assertTrue("The feature descriptors are not of the same size",
                !featureIt.hasNext() && !expectedIt.hasNext());
    }

    /**
     * Test method for
     * {@link TilesContextELResolver#getType(javax.el.ELContext, java.lang.Object, java.lang.Object)}.
     */
    public void testGetType() {
        TilesRequestContext request = EasyMock
                .createMock(TilesRequestContext.class);
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        ELContext context = new ELContextImpl(resolver);
        EasyMock.replay(request, applicationContext);
        context.putContext(TilesRequestContext.class, request);
        context.putContext(TilesApplicationContext.class, applicationContext);
        assertEquals("The requestScope object is not a map.", Map.class,
                resolver.getType(context, null, "requestScope"));
        assertEquals("The sessionScope object is not a map.", Map.class,
                resolver.getType(context, null, "sessionScope"));
        assertEquals("The applicationScope object is not a map.", Map.class,
                resolver.getType(context, null, "applicationScope"));
    }

    /**
     * Test method for
     * {@link TilesContextELResolver#getValue(javax.el.ELContext, java.lang.Object, java.lang.Object)}.
     */
    public void testGetValue() {
        Map<String, Object> requestScope = new HashMap<String, Object>();
        requestScope.put("objectKey", "objectValue");
        Map<String, Object> sessionScope = new HashMap<String, Object>();
        sessionScope.put("sessionObjectKey", "sessionObjectValue");
        Map<String, Object> applicationScope = new HashMap<String, Object>();
        applicationScope.put("applicationObjectKey", "applicationObjectValue");
        TilesRequestContext request = EasyMock
                .createMock(TilesRequestContext.class);
        EasyMock.expect(request.getRequestScope()).andReturn(requestScope);
        EasyMock.expect(request.getSessionScope()).andReturn(sessionScope);
        TilesApplicationContext applicationContext = EasyMock
                .createMock(TilesApplicationContext.class);
        EasyMock.expect(applicationContext.getApplicationScope()).andReturn(
                applicationScope);
        ELContext context = new ELContextImpl(resolver);
        EasyMock.replay(request, applicationContext);
        context.putContext(TilesRequestContext.class, request);
        context.putContext(TilesApplicationContext.class, applicationContext);
        assertEquals("The requestScope map does not correspond", requestScope,
                resolver.getValue(context, null, "requestScope"));
        assertEquals("The sessionScope map does not correspond", sessionScope,
                resolver.getValue(context, null, "sessionScope"));
        assertEquals("The applicationScope map does not correspond",
                applicationScope, resolver.getValue(context, null,
                        "applicationScope"));
    }

    /**
     * Test method for
     * {@link TilesContextELResolver#isReadOnly(javax.el.ELContext, java.lang.Object, java.lang.Object)}.
     */
    public void testIsReadOnly() {
        ELContext context = new ELContextImpl(resolver);
        assertTrue("The value is not read only", resolver.isReadOnly(context,
                null, null));
    }
}
