<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.man.GSConstMain" %>
<!DOCTYPE html>

<% String modeExport = String.valueOf(jp.groupsession.v2.man.GSConstMain.MODE_EXPORT); %>
<% String modeImport = String.valueOf(jp.groupsession.v2.man.GSConstMain.MODE_IMPORT); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man330.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="main.memberships.conf" /></title>
</head>
<body onunload="windowClose();">
<!--BODY -->
<html:form action="/main/man330">
<input type="hidden" name="CMD" value="">
<html:hidden property="man330cmdMode" />
<input type="hidden" name="helpPrm" value="<bean:write name="man330Form" property="man330cmdMode" />">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <logic:equal name="man330Form" property="man330cmdMode" value="<%= modeExport %>">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="main.memberships.conf" /></span><gsmsg:write key="cmn.export" />
      </logic:equal>
      <logic:notEqual name="man330Form" property="man330cmdMode" value="<%= modeExport %>">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="main.memberships.conf" /></span><gsmsg:write key="cmn.import" />
      </logic:notEqual>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ktool');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="txt_l mb5">
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <logic:equal name="man330Form" property="man330cmdMode" value="<%= modeExport %>">
    <ul id="normal_tab" class="tabHeader w100">
      <li id="export" class="tabHeader_tab-on bgC_lightGray pr10 pl10 bgI_none border_bottom_none">
        <gsmsg:write key="cmn.export" />
      </li>
      <li id="import" class="js_accTabHeader_tab tabHeader_tab-off pr10 pl10">
        <gsmsg:write key="cmn.import" />
      </li>
      <li class="tabHeader_space">
      </li>
    </ul>
  </logic:equal>
  <logic:equal name="man330Form" property="man330cmdMode" value="<%= modeImport %>">
    <ul class="tabHeader w100">
      <li id="export" class="js_accTabHeader_tab tabHeader_tab-off pr10 pl10">
        <gsmsg:write key="cmn.export" />
      </li>
      <li id="import" class="tabHeader_tab-on bgC_lightGray pr10 pl10 bgI_none border_bottom_none">
        <gsmsg:write key="cmn.import" />
      </li>
      <li class="tabHeader_space">
      </li>
    </ul>
  </logic:equal>
  <logic:equal name="man330Form" property="man330cmdMode" value="<%= modeExport %>">
    <%@ include file="/WEB-INF/plugin/main/jsp/man330_sub01.jsp" %>
  </logic:equal>
  <logic:equal name="man330Form" property="man330cmdMode" value="<%= modeImport %>">
    <%@ include file="/WEB-INF/plugin/main/jsp/man330_sub02.jsp" %>
  </logic:equal>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>