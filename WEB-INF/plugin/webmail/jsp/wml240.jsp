<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

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
  <script src="../webmail/js/wml240.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="anp.anp070.02" /></title>
</head>

<body>

<html:form action="/webmail/wml240">

<bean:define id="templateKbn" name="wml240Form" property="wmlMailTemplateKbn" type="java.lang.Integer" />
<bean:define id="accountMode" name="wml240Form" property="wmlAccountMode" type="java.lang.Integer" />
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
<input type="hidden" name="helpPrm" value="<%= helpParam %>">


<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml030_hiddenParams.jsp" %>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden name="wml240Form" property="wmlViewAccount" />
<html:hidden property="wmlAccountSid" />
<html:hidden property="wmlTemplateCmdMode" />
<html:hidden property="wmlEditTemplateId" />
<html:hidden property="dspCount" />
<html:hidden property="wmlMailTemplateKbn" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
    <% if (accountMode == 2) { %>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <% } else { %>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <% } %>

    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="anp.anp070.02" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="addTemplate();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('wml240Back');">
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
    <span class="txt_b"><html:errors/></span>
  </logic:messagesPresent>

  <table class="table-left w100">
    <tr>
      <th class="w25 txt_l">
        <gsmsg:write key="wml.28" />
      </th>
      <td class="w75">
        <bean:define id="templateKbn" name="wml240Form" property="wmlMailTemplateKbn" type="java.lang.Integer" />
        <% if (templateKbn.intValue() == jp.groupsession.v2.cmn.GSConstWebmail.MAILTEMPLATE_COMMON) { %>
          <gsmsg:write key="cmn.common" />
        <% } else { %>
          <bean:write name="wml240Form" property="wml240accountName" />
        <% } %>
      </td>
    </tr>
  </table>

  <div class="txt_l">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upTemplateData');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downTemplateData');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>

  <table class="table-top w100">
    <tr>
      <th class="w5 txt_c">&nbsp;</th>
      <th class="w85 txt_c">
        <gsmsg:write key="anp.anp100.02" />
      </th>
      <th class="w5 txt_c no_w">
        <gsmsg:write key="cmn.fixed" />
      </th>
      <th class="w5 txt_c no_w">
        <gsmsg:write key="cmn.delete" />
      </th>
    </tr>

  <logic:iterate id="templateData" name="wml240Form" property="templateList" indexId="idx">
    <bean:define id="templateId" name="templateData" property="wtpSid" />
    <% String templateCheckId = "chkTemplate" + String.valueOf(idx.intValue()); %>
    <tr>
      <td class="txt_c js_tableTopCheck cursor_p"><html:radio property="wml240SortRadio" value="<%= String.valueOf(templateId) %>" styleId="<%= templateCheckId %>" /></td>
      <td class="txt_l">
        <bean:write name="templateData" property="wtpName" />
      </td>
      <td class="txt_c no_w">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.fixed" />" onClick="editTemplate('<bean:write name="templateData" property="wtpSid" />');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.fixed" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.fixed" />">
          <gsmsg:write key="cmn.fixed" />
        </button>
      </td>
      <td class="txt_c no_w">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="deleteTemplate('<bean:write name="templateData" property="wtpSid" />');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </td>
    </tr>
  </logic:iterate>

  </table>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>