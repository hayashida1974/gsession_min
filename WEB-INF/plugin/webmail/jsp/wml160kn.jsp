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

  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml160kn.03" /></title>
</head>

<body>

<html:form action="/webmail/wml160kn">

<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
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
<html:hidden property="wml160initFlg" />
<html:hidden property="wml160updateFlg" />

<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlAccountMode" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.wml160kn.03" />
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

  <div class="txt_l">
    <span class="cl_fontWarn"><gsmsg:write key="main.man028kn.3" /></span>
  </div>

  <table class="table-left w100">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.capture.file.name" />
      </th>
      <td class="w75">
        <bean:write name="wml160knForm" property="wml160knFileName" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.overwrite" />
      </th>
      <td class="w75">
        <logic:equal name="wml160knForm" property="wml160updateFlg" value="1">
          <gsmsg:write key="wml.wml160.06" />
        </logic:equal>
      </td>
    </tr>
  </table>

  <table class="table-top w100 mt20">
    <tr>
      <th class="w50 no_w">
        <gsmsg:write key="wml.96" />
      </th>
      <th class="w50 no_w">
        <gsmsg:write key="cmn.employer" />
      </th>
    </tr>
  <logic:iterate id="useUserData" name="wml160knForm" property="wml160knUseUserList">
    <tr>
      <td class="w50">
        <bean:write name="useUserData" property="accountName" />
      </td>
      <td class="w50">
        <bean:define id="useUserKbn" name="useUserData" property="userKbn" type="java.lang.Integer" />
        <% boolean grpFlg = (useUserKbn == 1); %>
        <% if (grpFlg) { %>
          <gsmsg:write key="cmn.group" />:
        <% } else { %>
          <gsmsg:write key="cmn.user" />:
        <% } %>
        <div class="mt10 ml15">
          <logic:iterate id="useUserName" name="useUserData" property="userNameList">
            <% if (grpFlg) { %><bean:write name="useUserName" property="grpName" />
            <% } else { %><bean:write name="useUserName" property="usiSei" />  <bean:write name="useUserName" property="usiMei" />
            <% } %><br>
          </logic:iterate>
        </div>
      <logic:notEmpty name="useUserData" property="proxyUserNameList">
        <span class="mt10">
          <gsmsg:write key="cmn.proxyuser" />:
          <div class="mt10 ml15">
            <logic:iterate id="proxyUserName" name="useUserData" property="proxyUserNameList">
            <bean:write name="proxyUserName" property="usiSei" />  <bean:write name="proxyUserName" property="usiMei" /><br>
            </logic:iterate>
          </div>
        </span>
      </logic:notEmpty>
      </td>
    </tr>
  </logic:iterate>
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