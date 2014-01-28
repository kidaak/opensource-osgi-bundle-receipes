<%@ page session="false" %>
<!--
/*
 * $Id: index.jsp 734389 2009-01-14 13:38:06Z apetrelli $
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
 *
 */
-->
<html>
<head>
    <title>Tiles 2 Test Application</title>
</head>

<body>
    <h1>Tiles 2 Test Application</h1>
    
    <h2>Features in Tiles 2.0.x</h2>

    <h3>Standard Render/Attribute Tests</h3>
    <a href="testinsertdefinition.jsp">Test Insert Configured Definition</a><br/>
    <a href="testinsertdefinition_ignore.jsp">Test Insert Configured Definition with Ignore</a><br/>
    <a href="testinsertdefinition_flush.jsp">Test Insert Configured Definition with Flush</a><br/>
    <a href="testinsertdefinition_preparer.jsp">Test Insert Configured Definition with Preparer</a><br/>
    <a href="testinsertdefinition_preparer_configured.jsp">Test Insert Configured Definition with Preparer configured in the definition itself</a><br/>
    <a href="testinsertdefinition_classpath.jsp">Test Insert Configured Classpath Definition</a><br/>
    <a href="testinsertdefinition_override.jsp">Test Insert Configured Definition with an overridden content</a><br/>
    <a href="testinsertdefinition_override_and_not.jsp">Test Insert Configured Definition with an overridden content and one with original content</a><br/>
    <a href="testinsertdefinition_inline.jsp">Test Insert Configured Definition with an inline content</a><br/>
    <a href="testinsertdefinition_composite.jsp">Test Insert Configured Definition that contains another definition inside</a><br/>
    <a href="testinsertdefinition_exception.jsp">Test Insert Configured Definition with an exception in an attribute page</a><br/>
    <a href="testinsertdefinition_freemarker.jsp">Test Insert Configured Definition with FreeMarker</a><br/>
    <a href="testinsertdefinition_openbody.jsp">Test Insert Configured Definition with Open Body</a><br/>
    <a href="testput.jsp">Test Put Tag</a><br/>
    <a href="testput_flush.jsp">Test Put Tag with Flush</a><br/>
    <a href="testput_el.jsp">Test Put Tag using EL</a><br/>
    <a href="testput_servlet.jsp">Test Put Tag using a servlet mapping as a template</a><br/>
    <a href="testputlist.jsp">Test Put List Tag</a><br/>
    <a href="testimportattribute.jsp">Test importAttribute Tag</a><br/>
    <a href="testimportattribute_all.jsp">Test importAttribute Tag with no name</a><br/>
    <a href="testdecorationfilter.jsp">Test Tiles Definition Filter</a><br/>
    <a href="testdispatchservlet.tiles">Test Tiles Dispatch Servlet</a><br/>
    <a href="selectlocale.jsp">Test Localization</a><br/>

    <h3>Mutable Container Tests</h3>
    <a href="testinitcontainer.jsp">Test Initialize Container</a><br/>
    <a href="testdef.jsp">Test Definition Tag</a><br/>
    <a href="testdef_extend.jsp">Test Definition Tag extending configured and custom definitions</a><br/>
    <a href="testdef_preparer.jsp">Test Definition Tag with Preparer</a><br/>
    <a href="testinsertdefinition_composite_tags_includes_configured.jsp">Test Insert Definition that contains another definition inside (configured via tiles-defs.xml) using JSP tags</a><br/>
    <a href="testinsertdefinition_composite_tags.jsp">Test Insert Definition that contains another definition inside using JSP tags</a><br/>
    <a href="testinsertdefinition_composite_tags_includes_configured_notype.jsp">Test Insert Definition that contains another definition inside (configured via tiles-defs.xml) using JSP tags without types</a><br/>
    <a href="testinsertdefinition_composite_tags_notype.jsp">Test Insert Definition that contains another definition inside using JSP tags without types</a><br/></body>
    
    <h3>Roles Verification tests</h3>
    <a href="testinsertdefinition_role.jsp">Test Insert Configured Definition with Specified Role</a><br/>
    <a href="testinsertdefinition_role_tag.jsp">Test Insert Configured Definition with Specified Role in Tag</a><br/>
    <a href="testinsertdefinition_attribute_roles.jsp">Test Insert Configured Definition with Attribute that have Roles</a><br/>

    <h2>Features in Tiles 2.1.x</h2>

    <h3>Standard Render/Attribute Tests</h3>
    <a href="testinsertdefinition_override_template.jsp">Test Insert Configured Definition with an overridden template</a><br/>
    <a href="testinsertdefinition_old.jsp">Test Insert Configured Definition in Old Format</a><br/>
    <a href="testinsertdefinition_cascaded.jsp">Test Insert Configured Cascaded Definition</a><br/>
    <a href="testinsertdefinition_cascaded_overridden.jsp">Test Insert Configured Cascaded Definition with Override</a><br/>
    <a href="testinsertdefinition_cascaded_template.jsp">Test Insert Configured Cascaded Definition with Template</a><br/>
    <a href="testinsertdefinition_cascaded_list.jsp">Test Insert Configured Cascaded Definition with List</a><br/>
    <a href="testinsertdefinition_reversed.jsp">Test Insert Configured Definition with Reversed Attribute</a><br/>
    <a href="testinsertdefinition_reversed_explicit.jsp">Test Insert Configured Definition with Reversed Explicit Attribute</a><br/>
    <a href="testinsertdefinition_attribute_preparer.jsp">Test Insert Configured Definition with Attribute Preparer</a><br/>
    <a href="testinsertnesteddefinition.jsp">Test Insert Nested Definition</a><br/>
    <a href="testinsertnesteddefinition_tags.jsp">Test Insert Nested Definition only using JSP tags</a><br/>
    <a href="testinsertnestedlistdefinition.jsp">Test Insert Nested List Definition</a><br/>
    <a href="testinsertnestedlistdefinition_tags.jsp">Test Insert Nested List Definition only using JSP tags</a><br/>
    <a href="testinsertdefinition_el.jsp">Test Insert Configured Definition with EL</a><br/>
    <a href="testinsertdefinition_el_singleeval.jsp">Test Insert Configured Definition with EL to test Single Evaluation</a><br/>
    <a href="testinsertdefinition_wildcard.jsp">Test Insert Configured Definition with Wildcards</a><br/>
    <a href="testinsertdefinition_defaultvalues.jsp">Test Insert Configured Definition with Default Values</a><br/>
    <a href="testput_cascaded.jsp">Test Put Tag with Cascaded Attributes</a><br/>
    <a href="testput_cascaded_overridden.jsp">Test Put Tag with Overridden Cascaded Attributes</a><br/>
    <a href="testput_cascaded_template.jsp">Test Put Tag with Cascaded Attributes and Template</a><br/>
    <a href="testput_el_singleeval.jsp">Test Put Tag using EL to test Single Evaluation</a><br/>
    <a href="testput_reversed.jsp">Test Put Tag with Reversed Attribute</a><br/>
    <a href="testput_reversed_explicit.jsp">Test Put Tag with Reversed Explicit Attribute</a><br/>
    <a href="testputlist_cascaded.jsp">Test Put List Cascaded Tag</a><br/>
    <a href="testputlist_inherit.jsp">Test Put List Tag with Inherit</a><br/>
    <a href="testimportattribute_inherit.jsp">Test importAttribute Tag with List Inherit</a><br/>
    <a href="testsetcurrentcontainer.jsp">Test setCurrentContainer Tag</a><br/>

    <h3>Mutable Container Tests</h3>
    <a href="testdef_list_inherit.jsp">Test Definition Tag with a List Inherit</a><br/>

    <h3>Database Verification tests</h3>
    <a href="testinsertdefinition_db.jsp">Test Insert Configured Definition from DB</a><br/>
    <a href="testinsertdefinition_extended_db.jsp">Test Insert Extended Configured Definition from DB</a><br/>
    <a href="selectlocale_db.jsp">Test Localization from DB</a><br/>

</html>