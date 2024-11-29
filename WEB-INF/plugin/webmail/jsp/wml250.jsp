<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<bean:define id="templateCmdMode" name="wml250Form" property="wmlTemplateCmdMode" type="java.lang.Integer" />
<% boolean editFlg = (templateCmdMode == 1); %>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>

<% if (editFlg) { %>
  <title>GROUPSESSION <gsmsg:write key="wml.wml250.02" /></title>
<% } else { %>
  <title>GROUPSESSION <gsmsg:write key="wml.wml250.01" /></title>
<% } %>

  <script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
</head>

<bean:define id="contentLimit" name="wml250Form" property="contentMaxLen" type="java.lang.Integer" />
<% String strContentLimit = Integer.toString(contentLimit.intValue()); %>
<% if (contentLimit.intValue() > 0) { %>
<body onload="showLengthId($('#inputstr')[0], <%= contentLimit %>, 'inputlength');">
<% } else { %>
<body>
<% } %>

<html:form action="/webmail/wml250">

<bean:define id="templateKbn" name="wml250Form" property="wmlMailTemplateKbn" type="java.lang.Integer" />
<bean:define id="accountMode" name="wml250Form" property="wmlAccountMode" type="java.lang.Integer" />
<%
  String helpParam = "0";
  if (templateKbn.intValue() != jp.groupsession.v2.cmn.GSConstWebmail.MAILTEMPLATE_COMMON) {
    if (accountMode.intValue() == jp.groupsession.v2.cmn.GSConstWebmail.WAC_TYPE_USER) {
      helpParam = "1";
    } else {
      helpParam = "2";
    }
  }
%>
<% String tempMode = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_FILE); %>
<input type="hidden" name="helpPrm" value="<%= helpParam %>">

<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml030_hiddenParams.jsp" %>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlTemplateCmdMode" />
<html:hidden property="wmlEditTemplateId" />
<html:hidden property="wmlAccountSid" />
<html:hidden property="wml250initKbn" />
<html:hidden property="wml240SortRadio" />
<html:hidden property="dspCount" />
<html:hidden property="wmlMailTemplateKbn" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>

  <% if (accountMode.intValue() == 2) { %>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
  <% } else { %>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
  <% } %>

    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><% if (editFlg) { %><gsmsg:write key="wml.wml250.02" /><% } else { %><gsmsg:write key="wml.wml250.01" /><% } %>
    </li>

    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('wml250back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">

  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.102" />
      </th>
      <td class="w75">
        <bean:define id="templateKbn" name="wml250Form" property="wmlMailTemplateKbn" type="java.lang.Integer" />
        <% if (templateKbn.intValue() == jp.groupsession.v2.cmn.GSConstWebmail.MAILTEMPLATE_COMMON) { %>
          <gsmsg:write key="cmn.common" />
        <% } else { %>
          <bean:write name="wml250Form" property="wml240accountName" />
        <% } %>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="anp.anp100.02" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <html:text name="wml250Form" property="wml250TemplateName" maxlength="100" styleClass="wp550" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.subject" />
      </th>
      <td class="w75">
        <html:text name="wml250Form" property="wml250Title" maxlength="100" styleClass="wp550" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.body" />
      </th>
      <td class="w75">
      <% if (contentLimit.intValue() > 0) { %>
        <% String keyupEvent = "showLengthStr(value, " + strContentLimit + ", \'inputlength\');"; %>
        <html:textarea name="wml250Form" property="wml250Content" styleClass="wp550" rows="5" onkeyup="<%= keyupEvent %>" styleId="inputstr" />
        <br>
        <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>&nbsp;/&nbsp;<span class="formCounter"><%= contentLimit %>&nbsp;<gsmsg:write key="cmn.character" /></span>
      <% } else { %>
        <html:textarea name="wml250Form" property="wml250Content" styleClass="wp550" rows="5" />
      <% } %>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.attached" />
      </th>
      <td class="w75">
        <attachmentFile:filearea
        mode="<%= tempMode %>"
        pluginId="<%= jp.groupsession.v2.cmn.GSConstWebmail.PLUGIN_ID_WEBMAIL %>"
        tempDirId="wml250" />
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('wml250back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>