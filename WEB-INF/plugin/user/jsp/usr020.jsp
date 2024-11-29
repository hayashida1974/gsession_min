<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-groupTree.tld" prefix="groupTree" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.grouplist" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../user/js/usr020.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../user/js/group2.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../user/css/user.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<bean:define id="type" name="usr020Form" property="listType" scope="request"/>
<bean:define id="level" name="usr020Form" property="selectLevel" scope="request"/>
<bean:define id="root" name="usr020Form" property="dspRoot" scope="request"/>

<body class="m0" onload="checkedGroup();">
<html:form styleClass="bgC_body hp300" action="/user/usr020">

<html:hidden property="parentName" />

<!-- BODY 通常時 -->
<table class="w100 bgC_body">
<tr>
<td class="wp500">

<groupTree:gtreewrite name="usr020Form" property="groupList" root="<%= root.toString() %>" level="<%= level.toString() %>" type="<%= type.toString() %>"/>
</td>
</tr>

</table>

</html:form>
</body>

</html:html>