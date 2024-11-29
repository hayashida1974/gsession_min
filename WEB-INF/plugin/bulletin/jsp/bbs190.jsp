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
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bulletin/js/bbs190.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../bulletin/css/bulletin.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body class="m0" onload="checkedForum(); setParentForumDisabled(); doScrollForumFrame();" >
<html:form action="/bulletin/bbs190">

<html:hidden property="parentName" />

<!-- BODY 通常時 -->
<div class="w100 bgC_body iframe_hp">

  <div class="pl10">
    <input id="parentForumSid0" type=radio name="forumSid" value="0" onchange="onParentForumSubmit('0')">
    <label for="parentForumSid0">
      <strong class="cl_fontWarn fs_13">※<gsmsg:write key="user.145" /></strong>
    </label>
  </div>

  <logic:notEmpty name="bbs190Form" property="forumList">
  <logic:iterate id="fList" name="bbs190Form" property="forumList">

  <bean:define id="cLevel" name="fList" property="classLevel" />
  <% int intLevel = ((Integer) cLevel).intValue(); %>
  <% int dep = 27 * intLevel - 17; %>
  <div class="bgC_body" id="imgClick" style="padding-left:<%= dep %>px;">

    <input id="parentForumSid<bean:write name="fList" property="forumSid"/>" type="radio" name="forumSid" value="<bean:write name="fList" property="forumSid"/>" onchange="onParentForumSubmit(<bean:write name="fList" property="forumSid"/>)">

    <label for="parentForumSid<bean:write name="fList" property="forumSid"/>">

    <span>

    <%-- フォーラム画像default --%>
    <logic:equal name="fList" property="binSid" value="0">
      <img class="classic-display wp20hp20 ml4 mb5" src="../bulletin/images/classic/icon_forum.gif" alt="<gsmsg:write key="bbs.3" />">
      <img class="original-display wp20hp20 ml4 mb5" src="../bulletin/images/original/icon_forum_32.png" alt="<gsmsg:write key="bbs.3" />">
    </logic:equal>

    <%-- フォーラム画像original --%>
    <logic:notEqual name="fList" property="binSid" value="0">
      <img class="wp20hp20 ml4 mb5" alt="<gsmsg:write key="bbs.3" />" src="../bulletin/bbs010.do?CMD=getImageFile&bbs010BinSid=<bean:write name="fList" property="binSid" />&bbs010ForSid=<bean:write name="fList" property="forumSid" />">
    </logic:notEqual>

    <span id="parentForumSid<bean:write name="fList" property="forumSid"/>">
    <bean:write name="fList" property="forumName"/>
    </span>

    </span>

    </label>

  </div>
  </logic:iterate>
  </logic:notEmpty>

</div>

</html:form>
</body>

</html:html>