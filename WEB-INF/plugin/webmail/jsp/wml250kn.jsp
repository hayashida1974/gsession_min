<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<bean:define id="templateCmdMode" name="wml250knForm" property="wmlTemplateCmdMode" type="java.lang.Integer" />
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

  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml250kn.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/components/file-preview/index.js?<%=GSConst.VERSION_PARAM%>"></script>

<% if (editFlg) { %>
  <title>GROUPSESSION <gsmsg:write key="wml.wml250kn.02" /></title>
<% } else { %>
  <title>GROUPSESSION <gsmsg:write key="wml.wml250kn.01" /></title>
<% } %>
</head>

<body>

<html:form action="/webmail/wml250kn">

<bean:define id="templateKbn" name="wml250knForm" property="wmlMailTemplateKbn" type="java.lang.Integer" />
<bean:define id="accountMode" name="wml250knForm" property="wmlAccountMode" type="java.lang.Integer" />
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

<html:hidden property="wmlTemplateCmdMode" />
<html:hidden property="wmlEditTemplateId" />

<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlAccountSid" />
<html:hidden property="wml240SortRadio" />
<html:hidden property="dspCount" />
<html:hidden property="wmlMailTemplateKbn" />

<html:hidden property="wml250initKbn" />
<html:hidden property="wml250TemplateName" />
<html:hidden property="wml250Title" />
<html:hidden property="wml250Content" />
<input type="hidden" name="wml250knFileId" value="">

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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><% if (editFlg) { %><gsmsg:write key="wml.wml250kn.02" /><% } else { %><gsmsg:write key="wml.wml250kn.01" /><% } %>
    </li>

    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backInput');">
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
        <bean:define id="templateKbn" name="wml250knForm" property="wmlMailTemplateKbn" type="java.lang.Integer" />
      <% if (templateKbn.intValue() == jp.groupsession.v2.cmn.GSConstWebmail.MAILTEMPLATE_COMMON) { %>
        <gsmsg:write key="cmn.common" />
      <% } else { %>
        <bean:write name="wml250knForm" property="wml240accountName" />
      <% } %>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="anp.anp100.02" />
      </th>
      <td class="w75">
        <bean:write name="wml250knForm" property="wml250TemplateName" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.subject" />
      </th>
      <td class="w75">
        <bean:write name="wml250knForm" property="wml250Title" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.body" />
      </th>
      <td class="w75">
        <bean:write name="wml250knForm" property="wml250knViewContent" filter="false" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.attached" />
      </th>
      <td class="w75">
      <logic:notEmpty name="wml250knForm" property="fileList">
      <logic:iterate id="fileMdl" name="wml250knForm" property="fileList">
        <file-preview
          url="../webmail/wml250kn.do?CMD=fileDownload&wmlMailTemplateKbn=<bean:write name="wml250knForm" property="wmlMailTemplateKbn"/>&wml250knFileId=<bean:write name="fileMdl" property="value"/>&wmlAccountMode=<bean:write name="wml250knForm" property="wmlAccountMode"/>&wmlAccountSid=<bean:write name="wml250knForm" property="wmlAccountSid"/>"
          filename="<bean:write name="fileMdl" property="label"/>">
          <div>
            <span class="verAlignMid">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file.gif" alt="<gsmsg:write key="cmn.file" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.file" />">
              <a href="#!" onClick="return fileLinkClick('<bean:write name="fileMdl" property="value" />');"><span class="textLink ml5"><bean:write name="fileMdl" property="label" /></span></a>
            </span>
          </div>
        </file-preview>
      </logic:iterate>
      </logic:notEmpty>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backInput');">
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