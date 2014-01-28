/*
 * $Id: ELAttributeEvaluator.java 734389 2009-01-14 13:38:06Z apetrelli $
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

import java.util.Map;

import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.ResourceBundleELResolver;
import javax.el.ValueExpression;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.awareness.TilesApplicationContextAware;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.evaluator.AbstractAttributeEvaluator;
import org.apache.tiles.reflect.ClassUtil;

/**
 * Evaluates string expression with typical EL syntax.<br>
 * You can use normal EL syntax, knowing that the root objects are
 * {@link TilesRequestContext}, {@link TilesApplicationContext} and beans
 * contained in request, session and application scope.
 *
 * @version $Rev: 734389 $ $Date: 2009-01-14 14:38:06 +0100 (Wed, 14 Jan 2009) $
 * @since 2.1.0
 */
public class ELAttributeEvaluator extends AbstractAttributeEvaluator implements
        TilesApplicationContextAware {

    /**
     * Initialization parameter to decide the implementation of
     * {@link ExpressionFactoryFactory}.
     *
     * @since 2.1.0
     */
    public static final String EXPRESSION_FACTORY_FACTORY_INIT_PARAM =
        "org.apache.tiles.evaluator.el.ExpressionFactoryFactory";

    /**
     * The Tiles application context.
     *
     * @since 2.1.0
     */
    protected TilesApplicationContext applicationContext;

    /**
     * The EL expression factory.
     *
     * @since 2.1.0
     */
    protected ExpressionFactory expressionFactory;

    /**
     * The EL resolver to use.
     *
     * @since 2.1.0
     */
    protected ELResolver resolver;

    /**
     * Constructor.
     *
     * @since 2.1.0
     */
    public ELAttributeEvaluator() {
    }

    /** {@inheritDoc} */
    public void init(Map<String, String> initParameters) {
        String expressionFactoryClassName = initParameters
                .get(EXPRESSION_FACTORY_FACTORY_INIT_PARAM);
        ExpressionFactoryFactory efFactory;
        if (expressionFactoryClassName != null) {
            efFactory = (ExpressionFactoryFactory) ClassUtil
                    .instantiate(expressionFactoryClassName);
        } else {
            efFactory = (ExpressionFactoryFactory) ClassUtil
                    .instantiate(
                    "org.apache.tiles.evaluator.el.JspExpressionFactoryFactory", true);
            if (efFactory == null) {
                efFactory = new TomcatExpressionFactoryFactory();
            }
        }
        if (efFactory instanceof TilesApplicationContextAware) {
            ((TilesApplicationContextAware) efFactory)
                    .setApplicationContext(applicationContext);
        }
        expressionFactory = efFactory.getExpressionFactory();
        resolver = new CompositeELResolver() {
            {
                add(new TilesContextELResolver());
                add(new TilesContextBeanELResolver());
                add(new ArrayELResolver(false));
                add(new ListELResolver(false));
                add(new MapELResolver(false));
                add(new ResourceBundleELResolver());
                add(new BeanELResolver(false));
            }
        };
    }

    /** {@inheritDoc} */
    public void setApplicationContext(TilesApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Sets the expression factory to use.
     *
     * @param expressionFactory The expression factory.
     * @since 2.1.0
     */
    public void setExpressionFactory(ExpressionFactory expressionFactory) {
        this.expressionFactory = expressionFactory;
    }

    /**
     * Sets the EL resolver to use.
     *
     * @param resolver The EL resolver.
     * @since 2.1.0
     */
    public void setResolver(ELResolver resolver) {
        this.resolver = resolver;
    }

    /** {@inheritDoc} */
    public Object evaluate(String expression, TilesRequestContext request) {
        ELContextImpl context = new ELContextImpl(resolver);
        context.putContext(TilesRequestContext.class, request);
        context.putContext(TilesApplicationContext.class,
                applicationContext);
        ValueExpression valueExpression = expressionFactory
                .createValueExpression(context, expression, Object.class);

        return valueExpression.getValue(context);
    }
}
