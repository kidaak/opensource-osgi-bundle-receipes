/*
 * $Id: IncludingServlet.java 600658 2007-12-03 20:13:55Z apetrelli $
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
package org.apache.tiles.test.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.servlet.context.ServletUtil;

/**
 * Sample servlet that includes a page specified by the <code>include</code>
 * init parameter.
 *
 * @version $Rev: 600658 $ $Date: 2007-12-03 21:13:55 +0100 (Mon, 03 Dec 2007) $
 */
public class IncludingServlet extends HttpServlet {

    /**
     * Init parameter value, that indicates the path to include.
     */
    private String include;

    /**
     * Init parameter value, that indicates the path to include in case of
     * error.
     */
    private String errorInclude;

    /**
     * Initializes the servlet, reading the <code>include</code> init
     * parameter.
     *
     * @param config The servlet configuration object to use.
     * @throws ServletException Thrown by
     * {@link HttpServlet#init(ServletConfig)}
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        include = config.getInitParameter("include");
        errorInclude = config.getInitParameter("errorInclude");
    }

    /**
     * Processes the request, including the specified page.
     *
     * @param request The request object.
     * @param response The response object.
     * @throws ServletException Thrown by the {@link #include} method.
     * @throws IOException Thrown by the {@link #include} method.
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (ServletUtil.isForceInclude(request)) {
            request.getRequestDispatcher(include).include(request, response);
        } else {
            request.getRequestDispatcher(errorInclude).include(request, response);
        }
    }
}
