<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%
  String setteiClick         = jp.groupsession.v2.man.man240kn.Man240knAction.CMD_SETTEI_CLICK;
  String backClick           = jp.groupsession.v2.man.man240kn.Man240knAction.CMD_BACK_CLICK;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man240kn.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/main/man240kn">
<input type="hidden" name="CMD" value="">

<bean:define id="pluginId" value="" />
<bean:define id="pluginName" value="" />
<bean:define id="checkError" value="" />
<bean:define id="checkWarn" value="" />
<bean:define id="checkInfo" value="" />
<bean:define id="checkTrace" value="" />


<logic:notEmpty name="man240knForm" property="man240LogConfList">
<logic:iterate id="man240LogConf" name="man240knForm" property="man240LogConfList" indexId="indx">

<% pluginId = "man240LogConf[" + String.valueOf(indx.intValue()) + "].lgcPlugin"; %>
<% pluginName = "man240LogConf[" + String.valueOf(indx.intValue()) + "].pluginName"; %>
<% checkError = "man240LogConf[" + String.valueOf(indx.intValue()) + "].lgcLevelError"; %>
<% checkWarn = "man240LogConf[" + String.valueOf(indx.intValue()) + "].lgcLevelWarn"; %>
<% checkInfo = "man240LogConf[" + String.valueOf(indx.intValue()) + "].lgcLevelInfo"; %>
<% checkTrace = "man240LogConf[" + String.valueOf(indx.intValue()) + "].lgcLevelTrace"; %>

<input type="hidden" name="<%= pluginId %>" value="<bean:write name='man240knForm' property='<%= pluginId %>' />" >
<input type="hidden" name="<%= pluginName %>" value="<bean:write name='man240knForm' property='<%= pluginName %>' />" >

<logic:notEmpty name="man240knForm" property="<%= checkError %>">
  <bean:define id="checkErrorValue" name="man240knForm" property="<%= checkError %>" type="java.lang.String" />
  <input type="hidden" name="<%= checkError %>" value="<%= checkErrorValue %>" >
</logic:notEmpty>
<logic:notEmpty name="man240knForm" property="<%= checkWarn %>">
  <bean:define id="checkWarnValue" name="man240knForm" property="<%= checkWarn %>" type="java.lang.String" />
  <input type="hidden" name="<%= checkWarn %>" value="<%= checkWarnValue %>" >
</logic:notEmpty>
<logic:notEmpty name="man240knForm" property="<%= checkInfo %>">
  <bean:define id="checkInfoValue" name="man240knForm" property="<%= checkInfo %>" type="java.lang.String" />
  <input type="hidden" name="<%= checkInfo %>" value="<%= checkInfoValue %>" >
</logic:notEmpty>
<logic:notEmpty name="man240knForm" property="<%= checkTrace %>">
  <bean:define id="checkTraceValue" name="man240knForm" property="<%= checkTrace %>" type="java.lang.String" />
  <input type="hidden" name="<%= checkTrace %>" value="<%= checkTraceValue %>" >
</logic:notEmpty>

</logic:iterate>
</logic:notEmpty>




<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--　BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man240kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('<%= setteiClick %>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= backClick %>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
<table class="table-left">
  <logic:notEmpty name="man240Form" property="man240LogConfList">
  <logic:iterate name="man240Form" property="man240LogConfList" id="man240LogConf" indexId="idx" >
    <tr>
      <th class="w25 no_w">
        <bean:write name="man240LogConf" property="pluginName" />
      </th>
      <td class="no_w border_right_none">
        <div class="verAlignMid">
          <logic:equal name="man240LogConf" property="lgcLevelError" value="1"><img class="classic-display mr5" src="../main/images/classic/icon_log_error.gif" alt="<gsmsg:write key="man.error" />"><img class="original-display mr5" src="../main/images/original/icon_error.png" alt="<gsmsg:write key="man.error" />"><span class="mr20"><gsmsg:write key="man.error" /></span></logic:equal>
        </div>
      </td>
      <td class="no_w border_right_none">
        <div class="verAlignMid">
          <logic:equal name="man240LogConf" property="lgcLevelWarn" value="1"><img class="classic-display mr5" src="../main/images/classic/icon_log_warn.gif" alt="<gsmsg:write key="cmn.warning" />"><img class="original-display mr5" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />"><span class="mr20"><gsmsg:write key="cmn.warning" /></span></logic:equal>
        </div>
      </td>
      <td class="no_w border_right_none">
        <div class="verAlignMid">
          <logic:equal name="man240LogConf" property="lgcLevelInfo" value="1"><img class="classic-display mr5" src="../main/images/classic/icon_log_info.gif" alt="<gsmsg:write key="main.man240.2" />"><img class="original-display mr5" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="main.man240.2" />"><span class="mr20"><gsmsg:write key="main.man240.2" /></span></logic:equal>
        </div>
      </td>
      <td class="no_w w75">
        <div class="verAlignMid">
          <logic:equal name="man240LogConf" property="lgcLevelTrace" value="1"><img class="classic-display mr5" src="../main/images/classic/icon_log_trace.gif" alt="<gsmsg:write key="main.man240.3" />"><img class="original-display mr5" src="../common/images/original/icon_info.png" alt="<gsmsg:write key="main.man240.3" />"><span class="mr20"><gsmsg:write key="main.man240.3" /></span></logic:equal>
        </div>
      </td>
    </tr>
  </logic:iterate>
  </logic:notEmpty>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('<%= setteiClick %>');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= backClick %>');">
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