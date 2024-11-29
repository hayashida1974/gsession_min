<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstWebmail" %>
<%@ page import="jp.groupsession.v2.wml.wml040.Wml040Form" %>
<%-- 定数値 --%>
<%
  String  acModeNormal    = String.valueOf(GSConstWebmail.ACCOUNTMODE_NORMAL);
  String  acModePsn       = String.valueOf(GSConstWebmail.ACCOUNTMODE_PSNLSETTING);
  String  acModeCommon    = String.valueOf(GSConstWebmail.ACCOUNTMODE_COMMON);
  String  cmdModeAdd      = String.valueOf(GSConstWebmail.CMDMODE_ADD);
  String  cmdModeEdit     = String.valueOf(GSConstWebmail.CMDMODE_EDIT);
%>

<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">

  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml040.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml041.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmnOAuth.js?<%= GSConst.VERSION_PARAM %>"></script>

  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <title>GROUPSESSION <gsmsg:write key="wml.wml040.05" /></title>
</head>

<bean:define id="webmailAdmin" name="wml040Form" property="wml040webmailAdmin" type="java.lang.Boolean" />
<% boolean adminUserFlg = webmailAdmin.booleanValue(); %>

<bean:define id="wmlPermitKbn" name="wml040Form" property="wml040PermitKbn" type="java.lang.Integer" />
<% int permitKbn = wmlPermitKbn.intValue(); %>
<% if (permitKbn != 0) { %>
  <body onunload="windowClose();wml041Close();" onload="changeInputDiskSize(<bean:write name="wml040Form" property="wml040diskSize" />);change(<bean:write name="wml040Form" property="wml040userKbn" />, <bean:write name="wml040Form" property="wmlAccountMode" />);initDiskArea(<%= adminUserFlg %>);changeSendServerAuth(<bean:write name="wml040Form" property="wml040smtpAuth" />);changeAutoRsvTime(<bean:write name="wml040Form" property="wml040autoResv" />);changeCompressFile(<bean:write name="wml040Form" property="wml040compressFile" />);changeTimesent(<bean:write name="wml040Form" property="wml040timeSent" />);changeAuthMethod();">
<% } else { %>
  <body onunload="windowClose();wml041Close();" onload="change(<bean:write name="wml040Form" property="wml040userKbn" />, <bean:write name="wml040Form" property="wmlAccountMode" />);initDiskArea(<%= adminUserFlg %>);changeSendServerAuth(<bean:write name="wml040Form" property="wml040smtpAuth" />);changeCompressFile(<bean:write name="wml040Form" property="wml040compressFile" />);changeTimesent(<bean:write name="wml040Form" property="wml040timeSent" />);changeAuthMethod();">
<% } %>

<html:form action="/webmail/wml040">
<logic:notEqual name="wml040Form" property="wmlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="wml040Form" property="wmlCmdMode" value="<%= cmdModeAdd %>">
  <input type="hidden" name="helpPrm" value="0">
 </logic:equal>
</logic:notEqual>

<logic:equal name="wml040Form" property="wmlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="wml040Form" property="wmlCmdMode" value="<%= cmdModeAdd %>">
  <input type="hidden" name="helpPrm" value="1">
 </logic:equal>
</logic:equal>

<logic:notEqual name="wml040Form" property="wmlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="wml040Form" property="wmlCmdMode" value="<%= cmdModeEdit %>">
  <input type="hidden" name="helpPrm" value="2">
 </logic:equal>
</logic:notEqual>

<logic:equal name="wml040Form" property="wmlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="wml040Form" property="wmlCmdMode" value="<%= cmdModeEdit %>">
  <input type="hidden" name="helpPrm" value="3">
 </logic:equal>
</logic:equal>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden property="wmlCmdMode" />
<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlAccountMode" />
<html:hidden property="wmlAccountSid" />
<html:hidden property="wml010adminUser" />
<html:hidden property="wml040initFlg" />
<html:hidden property="wml040elementKbn" />
<html:hidden property="wml040PermitKbn" />
<html:hidden property="wml040diskSizeComp" />
<html:hidden property="wml100sortAccount" />
<html:hidden property="wml030keyword" />
<html:hidden property="wml030group" />
<html:hidden property="wml030user" />
<html:hidden property="wml030pageTop" />
<html:hidden property="wml030pageBottom" />
<html:hidden property="wml030svKeyword" />
<html:hidden property="wml030svGroup" />
<html:hidden property="wml030svUser" />
<html:hidden property="wml030sortKey" />
<html:hidden property="wml030order" />
<html:hidden property="wml030searchFlg" />

<html:hidden property="wml040authAccount" />
<html:hidden property="wml040cotSid" />
<html:hidden property="wml040PermitKbn" />

<% if (permitKbn == 0) { %>
  <html:hidden property="wml040AutoReceiveTime" />
  <html:hidden property="wml040delReceive" />
  <html:hidden property="wml040autoResv" />
<% } %>

<bean:define id="acctMode" name="wml040Form" property="wmlAccountMode" type="java.lang.Integer" />
<bean:define id="wCmdMode" name="wml040Form" property="wmlCmdMode" type="java.lang.Integer" />
<% int accountMode = acctMode.intValue(); %>
<% int cmdMode = wCmdMode.intValue(); %>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w90 mrl_auto">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><% if (cmdMode == 1) { %><gsmsg:write key="wml.98" /><% } else if (cmdMode != 1) { %><gsmsg:write key="wml.wml040.05" /><% } %>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="wml040Form" property="wmlCmdMode" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteAccount');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w90 mrl_auto">

  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <ul class="tabHeader w100">
    <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none" id="tab1">
      <gsmsg:write key="cmn.preferences" />
    </li>
    <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab2">
      <gsmsg:write key="wml.sign.setting" />
    </li>
    <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab3">
      <gsmsg:write key="wml.receive.setting" />
    </li>
    <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab4">
      <gsmsg:write key="cmn.other" />
    </li>
    <li class="tabHeader_space border_bottom_none"></li>
  </ul>

  <table id="tab1_table" class="table-left w100 mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.281" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <html:text name="wml040Form" property="wml040accountId" styleClass="wp300" maxlength="<%= String.valueOf(GSConstWebmail.MAXLEN_ACCOUNT_ID) %>" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.96" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <html:text name="wml040Form" property="wml040name" styleClass="wp300" maxlength="<%= String.valueOf(GSConstWebmail.MAXLEN_ACCOUNT) %>" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.mailaddress" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <html:text name="wml040Form" property="wml040mailAddress" styleClass="wp300" maxlength="256" />
      </td>
    </tr>



    <bean:size id="providerListSize" name="wml040Form" property="wml040providerList"/>
    <tr>
      <th>
        <gsmsg:write key="wml.313" />
      </th>
      <td>
        <logic:equal name="providerListSize" value="1">
          <gsmsg:write key="wml.309" />
        </logic:equal>
        <logic:notEqual name="providerListSize" value="1">
          <span class="verAlignMid">
            <html:radio name="wml040Form" property="wml040authMethod" styleId="authMethod1" value="0" onclick="changeAuthMethod();" /><label for="authMethod1"><gsmsg:write key="wml.309" /></label>
            <html:radio name="wml040Form" property="wml040authMethod" styleId="authMethod2" value="1" onclick="changeAuthMethod();" styleClass="ml10" /><label for="authMethod2"><gsmsg:write key="wml.310" /></label>
          </span>
        </logic:notEqual>
      </td>
    </tr>

    <tr class="js_BaseAuth">
      <th>
        <gsmsg:write key="wml.81" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <html:text name="wml040Form" property="wml040receiveServer" styleClass="wp300 mr5" maxlength="100" />
        <gsmsg:write key="cmn.port.number" />
        <html:text name="wml040Form" property="wml040receiveServerPort" styleClass="wp50" maxlength="5" />
        <div class="mt5">
          <gsmsg:write key="cmn.ango" />:
          <html:select name="wml040Form" property="wml040receiveServerEncrypt" styleClass="hp24">
            <html:optionsCollection name="wml040Form" property="wml040AngoProtocolCombo" value="value" label="label" />
          </html:select>
        </div>
      </td>
    </tr>
    <tr class="js_BaseAuth">
      <th>
        <gsmsg:write key="wml.43" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <html:text name="wml040Form" property="wml040receiveServerUser" styleClass="wp300" maxlength="256" />
      </td>
    </tr>
    <tr class="js_BaseAuth">
      <th>
        <gsmsg:write key="wml.44" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <html:password name="wml040Form" property="wml040receiveServerPassword" styleClass="wp300 mr5" maxlength="100" />
      </td>
    </tr>
    <tr class="js_BaseAuth">
      <th>
        <gsmsg:write key="wml.80" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <html:text name="wml040Form" property="wml040sendServer" styleClass="wp300 mr5" maxlength="100" />
        <gsmsg:write key="cmn.port.number" />
        <html:text name="wml040Form" property="wml040sendServerPort" styleClass="wp50" maxlength="5" />
        <div class="mt5">
          <gsmsg:write key="cmn.ango" />:
          <html:select name="wml040Form" property="wml040sendServerEncrypt" styleClass="hp24">
            <html:optionsCollection name="wml040Form" property="wml040AngoProtocolCombo" value="value" label="label" />
          </html:select>
        </div>
      </td>
    </tr>
    <tr class="js_BaseAuth">
      <th>
        <gsmsg:write key="wml.106" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040smtpAuth" styleId="smtpAuth1" value="1" onclick="changeSendServerAuth(1);" /><label for="smtpAuth1"><gsmsg:write key="wml.07" /></label>
          <html:radio name="wml040Form" property="wml040smtpAuth" styleId="smtpAuth2" value="0" onclick="changeSendServerAuth(0);" styleClass="ml10" /><label for="smtpAuth2"><gsmsg:write key="wml.08" /></label>
        </span>
      </td>
    </tr>
    <tr class="js_BaseAuth">
      <th>
        <gsmsg:write key="wml.78" />
      </th>
      <td>
        <html:text name="wml040Form" property="wml040sendServerUser" styleClass="wp300" maxlength="256" styleId="wml040sendServerUser" />
      </td>
    </tr>
    <tr class="js_BaseAuth">
      <th>
        <gsmsg:write key="wml.79" />
      </th>
      <td>
        <html:password name="wml040Form" property="wml040sendServerPassword" styleClass="wp300" maxlength="100" styleId="wml040sendServerPassword" />
      </td>
    </tr>

    <tr class="js_OAuth display_none">
      <th>
        <gsmsg:write key="wml.308" />
      </th>
      <td>
        <html:select name="wml040Form" property="wml040provider">
          <html:optionsCollection name="wml040Form" property="wml040providerList" value="value" label="label" />
        </html:select>
        <span class="verAlignMid">
          <button type="button" class="baseBtn mr20" onclick="doOAuth('wml040provider', 'wml040mailAddress', 'wml040cotSid');"><gsmsg:write key="wml.313" /></button>
          <span class="ml10">
          <bean:define id="oauthFlg" name="wml040Form" property="wml040oauthCompFlg" type="java.lang.Boolean" />
          <gsmsg:write key="wml.316"/>:
          <%  if (oauthFlg) { %><gsmsg:write key="wml.314"/>
          <%  } else { %><span class="cl_fontWarn"><gsmsg:write key="wml.315"/></span><% } %>
          </span>
        </span>
      </td>
    </tr>

    <tr>
      <th>
        <gsmsg:write key="wml.87" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <span id="diskSizeInputArea">
          <span class="verAlignMid">
            <html:radio name="wml040Form" property="wml040diskSize" styleId="disk1" value="0" onclick="changeInputDiskSize(0);" /><label for="disk1"><gsmsg:write key="wml.31" /></label>
            <html:radio name="wml040Form" property="wml040diskSize" styleId="disk2" value="1" onclick="changeInputDiskSize(1);" styleClass="ml10" /><label for="disk2"><gsmsg:write key="wml.32" /></label>
            <span id="inputDiskSize"><html:text name="wml040Form" property="wml040diskSizeLimit" styleClass="wp80 ml10 mr5" maxlength="6" />MB</span>
          </span>
        </span>
        <span id="diskSizeViewArea">
          <html:hidden property="wml040diskSize" />
          <html:hidden property="wml040diskSizeLimit" />
          <bean:define id="diskSizeComp" name="wml040Form" property="wml040diskSizeComp" type="java.lang.Integer" />
          <bean:define id="diskSps" name="wml040Form" property="wml040diskSps" type="java.lang.Integer" />
        <% if ((permitKbn == 0 || diskSizeComp == 1) && (adminUserFlg || (!adminUserFlg && diskSps == 0))) { %>
          <logic:equal name="wml040Form" property="wml040admDisk" value="0">
            <gsmsg:write key="wml.31" />
          </logic:equal>
          <logic:notEqual name="wml040Form" property="wml040admDisk" value="0">
            <span id="inputDiskSize"><gsmsg:write key="wml.32" /><span class="ml5"><bean:write name="wml040Form" property="wml040admDiskSize" /></span>MB</span>
          </logic:notEqual>
        <% } else { %>
          <logic:equal name="wml040Form" property="wml040diskSize" value="0">
            <gsmsg:write key="wml.31" />
          </logic:equal>
          <logic:notEqual name="wml040Form" property="wml040diskSize" value="0">
            <span id="inputDiskSize"><gsmsg:write key="wml.32" /><span class="ml5"><bean:write name="wml040Form" property="wml040diskSizeLimit" /></span>MB</span>
          </logic:notEqual>
        <% } %>
        </span>
        <% if (adminUserFlg && (permitKbn == 0 || diskSizeComp == 1)) { %>
        <span class="w99 ml30 txt_l verAlignMid">
          <html:checkbox styleId="diskSps" name="wml040Form" property="wml040diskSps" value="1" onclick="changeDiskSps();" /><label for="diskSps"><gsmsg:write key="cmn.spsetting" /></label>
        </span>
        <% } %>
        <% if (!adminUserFlg) { %>
          <html:hidden name="wml040Form" property="wml040diskSps" />
        <% } %>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <html:textarea name="wml040Form" property="wml040biko" rows="5" styleClass="wp350" />
      </td>
    </tr>

    <logic:equal name="wml040Form" property="wmlAccountMode" value="2">
    <tr>
      <th>
        <gsmsg:write key="cmn.employer" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <div>
          <span class="verAlignMid">
            <html:radio name="wml040Form" property="wml040userKbn" styleId="usrKbn1" value="0" onclick="change(0, 2);" /><label for="usrKbn1"><gsmsg:write key="wml.94" /></label>
            <html:radio name="wml040Form" property="wml040userKbn" styleId="usrKbn2" value="1" onclick="change(1, 2);" styleClass="ml10" /><label for="usrKbn2"><gsmsg:write key="wml.77" /></label>
          </span>
        </div>

        <div class="settingForm_separator">
          <span id="permissionGroup">
            <ui:usrgrpselector name="wml040Form" property="wml040userKbnGroupUI" styleClass="hp215" />
          </span>

          <span id="permissionUser">
            <ui:usrgrpselector name="wml040Form" property="wml040userKbnUserUI" styleClass="hp215" />
          </span>
        </div>
      </td>
    </tr>
    </logic:equal>
    <logic:equal name="wml040Form" property="wml040proxyUserFlg" value="true">
    <tr>
      <th>
        <gsmsg:write key="cmn.proxyuser" /></span>
      </th>
      <td>
        <ui:usrgrpselector name="wml040Form" property="wml040proxyUserUI" styleClass="hp215" />
      </td>
    </tr>
    </logic:equal>
  </table>

  <table id="tab2_table" class="table-left display_none w100 mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.25" />
      </th>
      <td class="w75">
        <html:text name="wml040Form" property="wml040organization" styleClass="wp300" maxlength="<%= String.valueOf(GSConstWebmail.MAXLEN_ACCOUNT_ORGANIZE) %>" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.34" />
      </th>
      <td>
        <logic:empty name="wml040Form" property="wml040signList">
          <button type="button" id="signAddBtn" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="openSignWindow(0, 0, 0);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
        </logic:empty>

        <logic:notEmpty name="wml040Form" property="wml040signList">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upSign');">
            <gsmsg:write key="cmn.up" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downSign');">
            <gsmsg:write key="cmn.down" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="openSignWindow(0, 0, 0);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteSign');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>

          <table class="table-left w99 mt5">
            <logic:iterate id="signData" name="wml040Form" property="wml040signList" indexId="signIdx" type="org.apache.struts.util.LabelValueBean">
              <tr>
                <td class="w98">
                  <span class="verAlignMid">
                    <html:radio name="wml040Form" property="wml040signNo" value="<%= signData.getValue() %>"/>
                    <a href="#" class="ml5" onClick="openSignWindowEdit(<%= signData.getValue() %>, 0);"><bean:write name="signData" property="label" /></a>
                  </span>
                </td>
              </tr>
            </logic:iterate>
          </table>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.sign.auto" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040signAuto" styleId="signAuto" value="0" /><label for="signAuto"><gsmsg:write key="wml.sign.auto.insert" /></label>
          <html:radio name="wml040Form" property="wml040signAuto" styleId="signAuto2" value="1" styleClass="ml10" /><label for="signAuto2"><gsmsg:write key="wml.sign.auto.no" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.sign.point" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040signPoint" styleId="signPlace" value="0" /><label for="signPlace"><gsmsg:write key="wml.sign.top" /></label>
          <html:radio name="wml040Form" property="wml040signPoint" styleId="signPlace2" value="1" styleClass="ml10" /><label for="signPlace2"><gsmsg:write key="wml.sign.bottom" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.receive.sign" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040receiveDsp" styleId="receiveDsp" value="1" /><label for="receiveDsp"><gsmsg:write key="cmn.display.ok" /></label>
          <html:radio name="wml040Form" property="wml040receiveDsp" styleId="receiveDsp2" value="0" styleClass="ml10" /><label for="receiveDsp2"><gsmsg:write key="cmn.dont.show" /></label>
        </span>
      </td>
    </tr>
  </table>

  <table id="tab3_table" class="table-left display_none w100 mt0">
    <tr>
      <th clsss="w25">
        <gsmsg:write key="wml.52" />
      </th>
      <td class="w75">
        <html:text name="wml040Form" property="wml040autoTo" styleClass="wp300" maxlength="256" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.53" />
      </th>
      <td>
        <html:text name="wml040Form" property="wml040autoCc" styleClass="wp300" maxlength="256" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.54" />
      </th>
      <td>
        <html:text name="wml040Form" property="wml040autoBcc" styleClass="wp300" maxlength="256" />
      </td>
    </tr>
  <% if (permitKbn == 1) { %>
    <tr>
      <th>
        <gsmsg:write key="wml.36" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040delReceive" styleId="delReceive1" value="1" /><label for="delReceive1"><gsmsg:write key="wml.60" /></label>
          <html:radio name="wml040Form" property="wml040delReceive" styleId="delReceive2" value="0" styleClass="ml10" /><label for="delReceive2"><gsmsg:write key="cmn.dont.delete" /></label>
        </span>
      </td>
    </tr>
  <% } %>
    <tr>
      <th>
        <gsmsg:write key="wml.39" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040reReceive" styleId="reReceive1" value="1" /><label for="reReceive1"><gsmsg:write key="wml.41" /></label>
          <html:radio name="wml040Form" property="wml040reReceive" styleId="reReceive2" value="0" styleClass="ml10" /><label for="reReceive2"><gsmsg:write key="wml.42" /></label>
        </span>
      </td>
    </tr>
    <tr class="js_BaseAuth">
      <th>
        <gsmsg:write key="wml.111" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040apop" styleId="apop1" value="1" /><label for="apop1"><gsmsg:write key="wml.112" /></label>
          <html:radio name="wml040Form" property="wml040apop" styleId="apop2" value="0" styleClass="ml10" /><label for="apop2"><gsmsg:write key="wml.113" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.278" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040topCmd" styleId="topCmd1" value="0" /><label for="topCmd1"><gsmsg:write key="wml.279" /></label>
          <html:radio name="wml040Form" property="wml040topCmd" styleId="topCmd2" value="1" styleClass="ml10" /><label for="topCmd2"><gsmsg:write key="wml.280" /></label>
        </span>
      </td>
    </tr>
    <tr class="js_BaseAuth">
      <th>
        <gsmsg:write key="wml.17" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040popBSmtp" styleId="popBSmtp1" value="1" /><label for="popBSmtp1"><gsmsg:write key="wml.07" /></label>
          <html:radio name="wml040Form" property="wml040popBSmtp" styleId="popBSmtp2" value="0" styleClass="ml10" /><label for="popBSmtp2"><gsmsg:write key="wml.08" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.wml040kn.01" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040encodeSend" styleId="encodeSend1" value="0" /><label for="encodeSend1"><gsmsg:write key="wml.108" /></label>
          <html:radio name="wml040Form" property="wml040encodeSend" styleId="encodeSend2" value="1" styleClass="ml10" /><label for="encodeSend2"><gsmsg:write key="wml.103" /></label>
        </span>
      </td>
    </tr>
  <% if (permitKbn == 1) { %>
    <tr>
      <th>
        <gsmsg:write key="wml.50" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040autoResv" styleId="autoResv1" value="1" onclick="changeAutoRsvTime(1);" /><label for="autoResv1"><gsmsg:write key="wml.48" /></label>
          <html:radio name="wml040Form" property="wml040autoResv" styleId="autoResv2" value="0" onclick="changeAutoRsvTime(0);" styleClass="ml10" /><label for="autoResv2"><gsmsg:write key="wml.49" /></label>
        </span>
      </td>
    </tr>
    <tr id="autoRsvTime">
      <th>
        <gsmsg:write key="wml.auto.receive.time" />
      </th>
      <td>
        <html:select property="wml040AutoReceiveTime">
          <html:optionsCollection name="wml040Form" property="wml040AutoRsvKeyLabel" value="value" label="label" />
        </html:select>
      </td>
    </tr>
  <% } %>
    <tr>
      <th>
        <gsmsg:write key="cmn.format." />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040sendType" styleId="sendType1" value="0" /><label for="sendType1"><gsmsg:write key="cmn.standard" /></label>
          <html:radio name="wml040Form" property="wml040sendType" styleId="sendType2" value="1" styleClass="ml10" /><label for="sendType2"><gsmsg:write key="wml.110" /></label>
        </span>
      </td>
    </tr>
  <% if (permitKbn == 1) { %>
    <tr>
      <th>
        <gsmsg:write key="wml.238" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040checkAddress" styleId="checkAddress1" value="1" /><label for="checkAddress1"><gsmsg:write key="cmn.check.2" /></label>
          <html:radio name="wml040Form" property="wml040checkAddress" styleId="checkAddress2" value="0" styleClass="ml10" /><label for="checkAddress2"><gsmsg:write key="cmn.notcheck" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.239" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040checkFile" styleId="checkFile1" value="1" /><label for="checkFile1"><gsmsg:write key="cmn.check.2" /></label>
          <html:radio name="wml040Form" property="wml040checkFile" styleId="checkFile2" value="0" styleClass="ml10" /><label for="checkFile2"><gsmsg:write key="cmn.notcheck" /></label>
        </span>
      </td>
    </tr>
    <tr class="table_folding-border">
      <th>
        <gsmsg:write key="wml.240" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040compressFile" styleId="compressFile1" value="1" onclick="changeCompressFile(1);" /><label for="compressFile1"><gsmsg:write key="cmn.compress" /></label>
          <html:radio name="wml040Form" property="wml040compressFile" styleId="compressFile2" value="0" onclick="changeCompressFile(0);" styleClass="ml10" /><label for="compressFile2"><gsmsg:write key="cmn.not.compress" /></label>
          <html:radio name="wml040Form" property="wml040compressFile" styleId="compressFile3" value="2" onclick="changeCompressFile(2);" styleClass="ml10" /><label for="compressFile3"><gsmsg:write key="cmn.setting.from.screen" /></label>
        </span>
      </td>
    </tr>
    <tr id="compressDefArea">
      <th>
        <gsmsg:write key="wml.240" /><span class="ml5"><gsmsg:write key="ntp.10"/></span>
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040compressFileDef" styleId="compressFileDef1" value="1" /><label for="compressFileDef1"><gsmsg:write key="cmn.compress" /></label>
          <html:radio name="wml040Form" property="wml040compressFileDef" styleId="compressFileDef2" value="0" styleClass="ml10" /><label for="compressFileDef2"><gsmsg:write key="cmn.not.compress" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th><gsmsg:write key="wml.241" /></span></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040timeSent" styleId="timeSent1" value="1" onclick="changeTimesent(1);" /><label for="timeSent1"><gsmsg:write key="cmn.effective" /></label>
          <html:radio name="wml040Form" property="wml040timeSent" styleId="timeSent2" value="0" onclick="changeTimesent(0);" styleClass="ml10" /><label for="timeSent2"><gsmsg:write key="cmn.invalid" /></label>
          <html:radio name="wml040Form" property="wml040timeSent" styleId="timeSent3" value="2" onclick="changeTimesent(2);" styleClass="ml10" /><label for="timeSent3"><gsmsg:write key="cmn.setting.from.screen" /></label>
        </span>
      </td>
    </tr>
    <tr id="timeSentDefArea">
      <th>
        <gsmsg:write key="wml.241" />&nbsp;<gsmsg:write key="ntp.10"/>
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml040Form" property="wml040timeSentDef" styleId="timeSentDef1" value="1" /><label for="timeSentDef1"><gsmsg:write key="wml.241" /></label>
          <html:radio name="wml040Form" property="wml040timeSentDef" styleId="timeSentDef2" value="0" styleClass="ml10" /><label for="timeSentDef2"><gsmsg:write key="wml.276" /></label>
        </span>
      </td>
    </tr>
  <% } %>
  </table>

  <table id="tab4_table" class="table-left display_none w100 mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.theme" />
      </th>
      <td class="w75">
        <html:select name="wml040Form" property="wml040theme">
          <logic:notEmpty name="wml040Form" property="wml040themeList">
            <html:optionsCollection name="wml040Form" property="wml040themeList" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.quotes" />
      </th>
      <td>
        <html:select name="wml040Form" property="wml040quotes">
          <logic:notEmpty name="wml040Form" property="wml040quotesList">
            <html:optionsCollection name="wml040Form" property="wml040quotesList" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.302" /></th>
      <td>
        <html:select property="wml040autoSaveMin">
          <html:optionsCollection name="wml040Form" property="wml040autoSaveList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="wml040Form" property="wmlCmdMode" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteAccount');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
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