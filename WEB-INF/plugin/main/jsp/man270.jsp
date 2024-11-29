<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="main.login.setting" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../main/js/man270.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="setDisp();">

<html:form action="/main/man270">
<html:hidden property="man270InitFlg" />
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<input type="hidden" name="CMD" value="">

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.login.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('confirmLoginConf');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdmMenu');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">

<table class="table-left">
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="main.man270.1" />
      </th>
      <td class="w75">
        <div class="verAlignMid">
        <html:radio property="man270lockKbn" value="<%= String.valueOf(GSConstCommon.LOGIN_LOCKKBN_NOSET) %>" styleId="lockKbn1" onclick="setDisp();" /><label for="lockKbn1" class="mr10"><gsmsg:write key="cmn.noset" /></label>
        <html:radio property="man270lockKbn" value="<%= String.valueOf(GSConstCommon.LOGIN_LOCKKBN_LOCKOUT) %>" styleId="lockKbn2" onclick="setDisp();" /><label for="lockKbn2"><gsmsg:write key="main.man270.2" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.number.failure" />
      </th>
      <td>
        <html:select name="man270Form" property="man270failCount">
          <html:optionsCollection name="man270Form" property="man270failCountList" value="value" label="label" />
        </html:select>&nbsp;
        <br>
        <gsmsg:write key="main.man270.5" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="main.lockout.time" />
      </th>
      <td>
        <html:select name="man270Form" property="man270lockTime">
          <html:optionsCollection name="man270Form" property="man270lockTimeList" value="value" label="label" />
        </html:select>&nbsp;<gsmsg:write key="cmn.minute" />
        <br>
        <gsmsg:write key="main.man270.7" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.reset.number.failure" />
      </th>
      <td>
        <html:select name="man270Form" property="man270failTime">
          <html:optionsCollection name="man270Form" property="man270failTimeList" value="value" label="label" />
        </html:select>&nbsp;<gsmsg:write key="main.man270.9" />
        <br>
        <gsmsg:write key="main.man270.10" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('confirmLoginConf');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdmMenu');">
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