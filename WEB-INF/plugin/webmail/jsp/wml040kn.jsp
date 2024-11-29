<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstWebmail" %>
<%@ page import="jp.groupsession.v2.wml.wml040kn.Wml040knForm" %>
<%-- 定数値 --%>
<%
  String  acModeNormal    = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.ACCOUNTMODE_NORMAL);
  String  acModePsn       = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.ACCOUNTMODE_PSNLSETTING);
  String  acModeCommon    = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.ACCOUNTMODE_COMMON);
  String  cmdModeAdd      = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.CMDMODE_ADD);
  String  cmdModeEdit     = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.CMDMODE_EDIT);
%>

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
  <script src="../webmail/js/wml040.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml040kn.05" /></title>
</head>

<bean:define id="webmailAdmin" name="wml040knForm" property="wml040webmailAdmin" type=" java.lang.Boolean" />
<% boolean adminUserFlg = webmailAdmin.booleanValue(); %>

<body onload="changeAutoRsvTime(<bean:write name="wml040knForm" property="wml040autoResv" />);">

<html:form action="/webmail/wml040kn">

<logic:notEqual name="wml040knForm" property="wmlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="wml040knForm" property="wmlCmdMode" value="<%= cmdModeAdd %>">
  <input type="hidden" name="helpPrm" value="0">
 </logic:equal>
</logic:notEqual>

<logic:equal name="wml040knForm" property="wmlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="wml040knForm" property="wmlCmdMode" value="<%= cmdModeAdd %>">
  <input type="hidden" name="helpPrm" value="1">
 </logic:equal>
</logic:equal>

<logic:notEqual name="wml040knForm" property="wmlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="wml040knForm" property="wmlCmdMode" value="<%= cmdModeEdit %>">
  <input type="hidden" name="helpPrm" value="2">
 </logic:equal>
</logic:notEqual>

<logic:equal name="wml040knForm" property="wmlAccountMode" value="<%= acModeCommon %>">
 <logic:equal name="wml040knForm" property="wmlCmdMode" value="<%= cmdModeEdit %>">
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
<html:hidden property="wml030keyword" />
<html:hidden property="wml030group" />
<html:hidden property="wml030user" />
<html:hidden property="wml030pageTop" />
<html:hidden property="wml030pageBottom" />

<html:hidden property="wml040initFlg" />
<html:hidden property="wml040elementKbn" />

<html:hidden property="wml040accountId" />
<html:hidden property="wml040name" />
<html:hidden property="wml040mailAddress" />

<html:hidden property="wml040authMethod" />
<html:hidden property="wml040provider" />
<html:hidden property="wml040authAccount" />
<html:hidden property="wml040cotSid" />

<html:hidden property="wml040receiveServer" />
<html:hidden property="wml040receiveServerPort" />
<html:hidden property="wml040receiveServerEncrypt" />
<html:hidden property="wml040receiveServerType" />
<html:hidden property="wml040receiveServerUser" />
<html:hidden property="wml040receiveServerPassword" />
<html:hidden property="wml040receiveServerEncrypt" />
<html:hidden property="wml040sendServer" />
<html:hidden property="wml040sendServerPort" />
<html:hidden property="wml040sendServerEncrypt" />
<html:hidden property="wml040sendServerUser" />
<html:hidden property="wml040sendServerPassword" />
<html:hidden property="wml040sendServerEncrypt" />
<html:hidden property="wml040diskSize" />
<html:hidden property="wml040diskSizeLimit" />
<html:hidden property="wml040diskSizeComp" />
<html:hidden property="wml040diskSps" />
<html:hidden property="wml040biko" />
<html:hidden property="wml040userKbn" />
<html:hidden property="wml040organization" />
<html:hidden property="wml040sign" />
<html:hidden property="wml040signNo" />
<html:hidden property="wml040signAuto" />
<html:hidden property="wml040signPoint" />
<html:hidden property="wml040autoTo" />
<html:hidden property="wml040autoCc" />
<html:hidden property="wml040autoBcc" />
<html:hidden property="wml040delReceive" />
<html:hidden property="wml040reReceive" />
<html:hidden property="wml040apop" />
<html:hidden property="wml040topCmd" />
<html:hidden property="wml040smtpAuth" />
<html:hidden property="wml040popBSmtp" />
<html:hidden property="wml040encodeSend" />
<html:hidden property="wml040autoResv" />
<html:hidden property="wml040sendType" />
<html:hidden property="wml040receiveDsp" />
<html:hidden property="wml040AutoReceiveTime" />
<html:hidden property="wml040PermitKbn" />

<bean:define id="wmlPermitKbn" name="wml040knForm" property="wml040PermitKbn" type="java.lang.Integer" />
<% int permitKbn = wmlPermitKbn.intValue(); %>

<logic:equal name="wml040knForm" property="wml040userKbn" value="1">
  <html:hidden property="wml040userKbnUserGroup" />
</logic:equal>

<logic:notEmpty name="wml040knForm" property="wml040userKbnGroup">
<logic:iterate id="permitId" name="wml040knForm" property="wml040userKbnGroup">
  <input type="hidden" name="wml040userKbnGroup" value="<bean:write name="permitId" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="wml040knForm" property="wml040userKbnUser">
<logic:iterate id="permitId" name="wml040knForm" property="wml040userKbnUser">
  <input type="hidden" name="wml040userKbnUser" value="<bean:write name="permitId" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="wml040knForm" property="wml040proxyUser">
<logic:iterate id="proxyUser" name="wml040knForm" property="wml040proxyUser">
  <input type="hidden" name="wml040proxyUser" value="<bean:write name="proxyUser" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="wml040theme" />
<html:hidden property="wml040checkAddress" />
<html:hidden property="wml040checkFile" />
<html:hidden property="wml040compressFile" />
<html:hidden property="wml040compressFileDef" />
<html:hidden property="wml040timeSent" />
<html:hidden property="wml040timeSentDef" />
<html:hidden property="wml040quotes" />

<html:hidden property="wml100sortAccount" />
<html:hidden property="wml030keyword" />
<html:hidden property="wml030group" />
<html:hidden property="wml030user" />
<html:hidden property="wml030svKeyword" />
<html:hidden property="wml030svGroup" />
<html:hidden property="wml030svUser" />
<html:hidden property="wml030sortKey" />
<html:hidden property="wml030order" />
<html:hidden property="wml030searchFlg" />
<html:hidden property="wml040autoSaveMin"/>

<bean:define id="acctMode" name="wml040knForm" property="wmlAccountMode" type="java.lang.Integer" />
<bean:define id="wCmdMode" name="wml040knForm" property="wmlCmdMode" type="java.lang.Integer" />
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><% if (cmdMode == 1) { %><gsmsg:write key="wml.97" /><% } else if (cmdMode != 1) { %><gsmsg:write key="wml.wml040kn.05" /><% } %>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="wml.wml040kn.04" />" onClick="buttonPush('connectTest');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_connect.png" alt="<gsmsg:write key="wml.wml040kn.04" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_connect.png" alt="<gsmsg:write key="wml.wml040kn.04" />">
          <gsmsg:write key="wml.wml040kn.04" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
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
        <gsmsg:write key="wml.281" />
      </th>
      <td class="w75">
        <bean:write name="wml040knForm" property="wml040accountId" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.96" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040name" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.mailaddress" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040mailAddress" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.313" />
      </th>
      <td>
        <logic:notEqual name="wml040knForm" property="wml040authMethod" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
          <gsmsg:write key="wml.309" />
        </logic:notEqual>
        <logic:equal name="wml040knForm" property="wml040authMethod" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
          <gsmsg:write key="wml.310" />
        </logic:equal>
      </td>
    </tr>

  <logic:notEqual name="wml040knForm" property="wml040authMethod" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
    <tr>
      <th>
        <gsmsg:write key="wml.81" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040receiveServer" />
        <span class="ml5"><gsmsg:write key="cmn.port.number" /></span>:<bean:write name="wml040knForm" property="wml040receiveServerPort" />
        <br><gsmsg:write key="cmn.ango" />:
        <bean:write name="wml040knForm" property="wml040knReciveEncrypt" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.43" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040receiveServerUser" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.44" />
      </th>
      <td>
        *****
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.80" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040sendServer" />
        <span class="ml5"><gsmsg:write key="cmn.port.number" /></span>:<bean:write name="wml040knForm" property="wml040sendServerPort" />
        <br><gsmsg:write key="cmn.ango" />:
        <bean:write name="wml040knForm" property="wml040knSendEncrypt" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.106" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040smtpAuth" value="1">
          <gsmsg:write key="wml.07" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040smtpAuth" value="1">
          <gsmsg:write key="wml.08" />
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.78" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040sendServerUser" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.79" />
      </th>
      <td>
        *****
      </td>
    </tr>
  </logic:notEqual>

  <logic:equal name="wml040knForm" property="wml040authMethod" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
    <tr>
      <th>
        <gsmsg:write key="wml.308" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040knProvider" />
        <bean:define id="oauthFlg" name="wml040knForm" property="wml040oauthCompFlg" type="java.lang.Boolean" />
        <span class="ml10">
          <gsmsg:write key="wml.316"/>:
          <%  if (oauthFlg) { %><gsmsg:write key="wml.314"/>
          <%  } else { %><span class="cl_fontWarn"><gsmsg:write key="wml.315"/></span><% } %>
        </span>
      </td>
    </tr>
  </logic:equal>

    <tr>
      <th>
        <gsmsg:write key="wml.87" />
      </th>
      <td>
        <bean:define id="diskSizeComp" name="wml040knForm" property="wml040diskSizeComp" type="java.lang.Integer" />
        <bean:define id="diskSps" name="wml040knForm" property="wml040diskSps" type="java.lang.Integer" />
        <% if ((permitKbn == 0 || diskSizeComp == 1) && diskSps == 0) { %>
          <logic:equal name="wml040knForm" property="wml040admDisk" value="0">
            <gsmsg:write key="wml.31" />
          </logic:equal>
          <logic:notEqual name="wml040knForm" property="wml040admDisk" value="0">
            <span id="inputDiskSize"><gsmsg:write key="wml.32" /><span class="ml5"><bean:write name="wml040knForm" property="wml040admDiskSize" />MB</span></span>
          </logic:notEqual>
        <% } else { %>
          <logic:equal name="wml040knForm" property="wml040diskSize" value="1">
            <gsmsg:write key="wml.32" />&nbsp;<bean:write name="wml040knForm" property="wml040diskSizeLimit" />MB
          </logic:equal>
          <logic:notEqual name="wml040knForm" property="wml040diskSize" value="1">
            <gsmsg:write key="wml.31" />
          </logic:notEqual>
        <% } %>
        <% if (adminUserFlg && (permitKbn == 0 || diskSizeComp == 1) && diskSps == 1) { %>
          <span class="spsetting_span" align="left">
            <gsmsg:write key="cmn.spsetting" />
          </span>
        <% } %>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040knBiko" filter="false" />
      </td>
    </tr>
    <logic:equal name="wml040knForm" property="wmlAccountMode" value="2">
    <tr>
      <th>
        <gsmsg:write key="cmn.employer" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040userKbn" value="<%= String.valueOf(jp.groupsession.v2.wml.wml040kn.Wml040knForm.USERKBN_GROUP) %>">
          <div>
            <gsmsg:write key="wml.94" />
          </div>
          <logic:notEmpty name="wml040knForm" property="userKbnGroupSelectCombo">
            <div class="settingForm_separator">
              <ul>
                <logic:iterate id="userKbnLabel" name="wml040knForm" property="userKbnGroupSelectCombo">
                <li><bean:write name="userKbnLabel" property="label" /></li>
                </logic:iterate>
              </ul>
            </div>
          </logic:notEmpty>
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040userKbn" value="<%= String.valueOf(jp.groupsession.v2.wml.wml040kn.Wml040knForm.USERKBN_GROUP) %>">
          <div>
            <gsmsg:write key="wml.77" />
          </div>
          <logic:notEmpty name="wml040knForm" property="userKbnUserSelectCombo">
            <div class="settingForm_separator">
              <logic:iterate id="user" name="wml040knForm" property="userKbnUserSelectCombo">
                <bean:define id="mukoUserClass" value="" />
                <logic:equal name="user" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUser" /></logic:equal>
                <div><span class="<%=mukoUserClass%>"><bean:write name="user" property="label" /></span></div>
              </logic:iterate>
            </div>
          </logic:notEmpty>
        </logic:notEqual>
      </td>
    </tr>
    </logic:equal>
    <logic:equal name="wml040knForm" property="wml040proxyUserFlg" value="true">
    <tr>
      <th>
        <gsmsg:write key="cmn.proxyuser" /></span>
      </th>
      <td>
        <logic:notEmpty name="wml040knForm" property="proxyUserSelectCombo">
          <logic:iterate id="user" name="wml040knForm" property="proxyUserSelectCombo">
            <bean:define id="mukoUserClass" value="" />
            <logic:equal name="user" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUser" /></logic:equal>
            <div><span class="<%=mukoUserClass%>"><bean:write name="user" property="label" /></span></div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    </logic:equal>
  </table>

  <table id="tab2_table" class="table-left w100 mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.25" />
      </th>
      <td class="w75">
        <bean:write name="wml040knForm" property="wml040organization" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.34" />
      </th>
      <td>
        <logic:notEmpty name="wml040knForm" property="wml040signList">
          <div id="right_label">
            <logic:iterate id="signData" name="wml040knForm" property="wml040signList" indexId="signIdx" type="org.apache.struts.util.LabelValueBean">
              <div><bean:write name="signData" property="label" /></div>
            </logic:iterate>
          </div>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.sign.auto" />
      </th>
      <td>
        <logic:notEqual name="wml040knForm" property="wml040signAuto" value="1">
          <gsmsg:write key="wml.sign.auto.insert" />
        </logic:notEqual>
        <logic:equal name="wml040knForm" property="wml040signAuto" value="1">
          <gsmsg:write key="wml.sign.auto.no" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.sign.point" />
      </th>
      <td>
        <logic:notEqual name="wml040knForm" property="wml040signPoint" value="1">
          <gsmsg:write key="wml.sign.top" />
        </logic:notEqual>
        <logic:equal name="wml040knForm" property="wml040signPoint" value="1">
          <gsmsg:write key="wml.sign.bottom" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.receive.sign" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040receiveDsp" value="1">
          <gsmsg:write key="cmn.display.ok" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040receiveDsp" value="1">
          <gsmsg:write key="cmn.dont.show" />
        </logic:notEqual>
      </td>
    </tr>
  </table>

  <table id="tab3_table" class="table-left w100 mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.52" />
      </th>
      <td class="w75">
        <bean:write name="wml040knForm" property="wml040autoTo" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.53" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040autoCc" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.54" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040autoBcc" />
      </td>
    </tr>

    <% if (permitKbn == 1) { %>
    <tr>
      <th>
        <gsmsg:write key="wml.36" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040delReceive" value="1">
          <gsmsg:write key="wml.60" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040delReceive" value="1">
          <gsmsg:write key="cmn.dont.delete" />
        </logic:notEqual>
      </td>
    </tr>
    <% } %>

    <tr>
      <th>
        <gsmsg:write key="wml.39" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040reReceive" value="1">
          <gsmsg:write key="wml.41" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040reReceive" value="1">
          <gsmsg:write key="wml.42" />
        </logic:notEqual>
      </td>
    </tr>
  <logic:notEqual name="wml040knForm" property="wml040authMethod" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
    <tr>
      <th>
        <gsmsg:write key="wml.111" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040apop" value="1">
          <gsmsg:write key="wml.112" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040apop" value="1">
          <gsmsg:write key="wml.113" />
        </logic:notEqual>
      </td>
    </tr>
  </logic:notEqual>
    <tr>
      <th>
        <gsmsg:write key="wml.278" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040topCmd" value="0">
          <gsmsg:write key="wml.279" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040topCmd" value="0">
          <gsmsg:write key="wml.280" />
        </logic:notEqual>
      </td>
    </tr>
  <logic:notEqual name="wml040knForm" property="wml040authMethod" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
    <tr>
      <th>
        <gsmsg:write key="wml.17" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040popBSmtp" value="1">
          <gsmsg:write key="wml.07" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040popBSmtp" value="1">
          <gsmsg:write key="wml.08" />
        </logic:notEqual>
      </td>
    </tr>
  </logic:notEqual>
    <tr>
      <th>
        <gsmsg:write key="wml.wml040kn.01" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040encodeSend" value="0">
          <gsmsg:write key="wml.108" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040encodeSend" value="0">
          <gsmsg:write key="wml.103" />
        </logic:notEqual>
      </td>
    </tr>

    <% if (permitKbn == 1) { %>
    <tr>
      <th>
        <gsmsg:write key="wml.50" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040autoResv" value="1">
          <gsmsg:write key="wml.48" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040autoResv" value="1">
          <gsmsg:write key="wml.49" />
        </logic:notEqual>
      </td>
    </tr>
    <tr id="autoRsvTime">
      <th>
        <gsmsg:write key="wml.auto.receive.time" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040AutoReceiveTime" /><gsmsg:write key="cmn.minute" />
       </td>
    </tr>
    <% } %>

    <tr>
      <th>
        <gsmsg:write key="cmn.format." />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040sendType" value="0">
          <gsmsg:write key="cmn.standard" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040sendType" value="0">
          <gsmsg:write key="wml.110" />
        </logic:notEqual>
      </td>
    </tr>

    <% if (permitKbn == 1) { %>
    <tr id="checkAddress">
      <th>
        <gsmsg:write key="wml.238" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040checkAddress" value="1">
          <gsmsg:write key="cmn.check.2" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040checkAddress" value="1">
          <gsmsg:write key="cmn.notcheck" />
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.239" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040checkFile" value="1">
          <gsmsg:write key="cmn.check.2" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040checkFile" value="1">
          <gsmsg:write key="cmn.notcheck" />
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.240" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040compressFile" value="1">
          <gsmsg:write key="cmn.compress" />
        </logic:equal>
        <logic:equal name="wml040knForm" property="wml040compressFile" value="0">
          <gsmsg:write key="cmn.not.compress" />
        </logic:equal>
        <logic:equal name="wml040knForm" property="wml040compressFile" value="2">
          <gsmsg:write key="cmn.setting.from.screen" />
        </logic:equal>
      </td>
    </tr>

    <logic:equal name="wml040knForm" property="wml040compressFile" value="2">
      <tr id="compressDefArea">
        <th>
          <gsmsg:write key="wml.240" />&nbsp;<gsmsg:write key="ntp.10"/>
        </th>
        <td>
          <logic:equal name="wml040knForm" property="wml040compressFileDef" value="1">
           <gsmsg:write key="cmn.compress" />
          </logic:equal>
          <logic:notEqual name="wml040knForm" property="wml040compressFileDef" value="1">
            <gsmsg:write key="cmn.not.compress" />
          </logic:notEqual>
        </td>
      </tr>
    </logic:equal>

    <tr>
      <th>
        <gsmsg:write key="wml.241" />
      </th>
      <td>
        <logic:equal name="wml040knForm" property="wml040timeSent" value="0">
          <gsmsg:write key="cmn.invalid" />
        </logic:equal>
        <logic:equal name="wml040knForm" property="wml040timeSent" value="1">
          <gsmsg:write key="cmn.effective" />
        </logic:equal>
        <logic:equal name="wml040knForm" property="wml040timeSent" value="2">
          <gsmsg:write key="cmn.setting.from.screen" />
        </logic:equal>
      </td>
    </tr>

    <logic:equal name="wml040knForm" property="wml040timeSent" value="2">
      <tr>
        <th>
          <gsmsg:write key="wml.241" />&nbsp;<gsmsg:write key="ntp.10"/>
        </th>
        <td>
          <logic:equal name="wml040knForm" property="wml040timeSentDef" value="1">
            <gsmsg:write key="wml.241" />
          </logic:equal>
          <logic:notEqual name="wml040knForm" property="wml040timeSentDef" value="1">
            <gsmsg:write key="wml.276" />
          </logic:notEqual>
        </td>
      </tr>
    </logic:equal>
    <% } %>

  </table>

  <table id="tab4_table" class="table-left w100 mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.theme" />
      </th>
      <td class="w75">
        <bean:write name="wml040knForm" property="wml040knTheme" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.quotes" />
      </th>
      <td>
        <bean:write name="wml040knForm" property="wml040knQuotes" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.302" /></th>
      <td>
        <logic:equal name="wml040knForm" property="wml040autoSaveMin" value="0">
          <gsmsg:write key="wml.304" />
        </logic:equal>
        <logic:notEqual name="wml040knForm" property="wml040autoSaveMin" value="0">
          <bean:write name="wml040knForm" property="wml040autoSaveMin" /><gsmsg:write key="cmn.minute" />
        </logic:notEqual>
      </td>
    </tr>

  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="wml.wml040kn.04" />" onClick="buttonPush('connectTest');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_connect.png" alt="<gsmsg:write key="wml.wml040kn.04" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_connect.png" alt="<gsmsg:write key="wml.wml040kn.04" />">
      <gsmsg:write key="wml.wml040kn.04" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
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