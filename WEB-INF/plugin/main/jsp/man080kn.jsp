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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="main.man080kn.1" /></title>
</head>
<body class="body_03">
<!--BODY -->
<% String interval_noset = String.valueOf(jp.groupsession.v2.man.GSConstMain.BUCCONF_INTERVAL_NOSET); %>
<% String interval_daily = String.valueOf(jp.groupsession.v2.man.GSConstMain.BUCCONF_INTERVAL_DAILY); %>
<% String interval_weekly = String.valueOf(jp.groupsession.v2.man.GSConstMain.BUCCONF_INTERVAL_WEEKLY); %>
<% String interval_monthly = String.valueOf(jp.groupsession.v2.man.GSConstMain.BUCCONF_INTERVAL_MONTHLY); %>
<html:form action="/main/man080kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="man080Interval" />
<html:hidden property="man080dow" />
<html:hidden property="man080weekmonth" />
<html:hidden property="man080monthdow" />
<html:hidden property="man080generation" />
<html:hidden property="man080zipOutputKbn" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w90 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man080kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('entry');">
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
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man080.1" />
      </th>
      <td class="w80">
        <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
        <% String[] dowList = {gsMsg.getMessage(request, "cmn.every.sunday"), gsMsg.getMessage(request, "cmn.every.monday"), gsMsg.getMessage(request, "cmn.every.tuesday"), gsMsg.getMessage(request, "cmn.every.wednesday"), gsMsg.getMessage(request, "cmn.every.thursday"), gsMsg.getMessage(request, "cmn.every.friday"), gsMsg.getMessage(request, "cmn.every.saturday")}; %>
        <% String[] dayOfWeekNameList = {gsMsg.getMessage(request, "cmn.sunday3"), gsMsg.getMessage(request, "cmn.monday3"), gsMsg.getMessage(request, "cmn.tuesday3"), gsMsg.getMessage(request, "cmn.wednesday3"), gsMsg.getMessage(request, "cmn.thursday3"), gsMsg.getMessage(request, "main.src.man080.7"), gsMsg.getMessage(request, "cmn.saturday3")}; %>
        <logic:equal name="man080knForm" property="man080Interval" value="<%= interval_noset %>">
          <gsmsg:write key="main.man080kn.2" />
        </logic:equal>
        <logic:equal name="man080knForm" property="man080Interval" value="<%= interval_daily %>">
          <gsmsg:write key="main.man080kn.3" />
        </logic:equal>
        <logic:equal name="man080knForm" property="man080Interval" value="<%= interval_weekly %>">
          <bean:define id="dow" name="man080knForm" property="man080dow" />
          <gsmsg:write key="main.man080kn.4" arg0="<%= dowList[((Integer) dow).intValue() - 1] %>" />
        </logic:equal>
        <logic:equal name="man080knForm" property="man080Interval" value="<%= interval_monthly %>">
          <% String[] weekMonthList = {gsMsg.getMessage(request, "cmn.no.1"), gsMsg.getMessage(request, "cmn.no.2"), gsMsg.getMessage(request, "cmn.no.3"), gsMsg.getMessage(request, "cmn.no.4"), gsMsg.getMessage(request, "cmn.no.5")}; %>
          <bean:define id="dow" name="man080knForm" property="man080monthdow" />
          <bean:define id="weekmonth" name="man080knForm" property="man080weekmonth" />
          <gsmsg:write key="cmn.monthly.2" /> <gsmsg:write key="main.man080kn.4" arg0="<%= weekMonthList[((Integer) weekmonth).intValue() - 1] + dayOfWeekNameList[((Integer) dow).intValue() - 1] %>" />
        </logic:equal>
      </td>
    </tr>
    <bean:define id="generation" name="man080knForm" property="man080generation" />
    <% String[] generationList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}; %>
    <logic:notEqual name="man080knForm" property="man080Interval" value="<%= interval_noset %>">
      <tr>
        <th class="w20 no_w">
          <gsmsg:write key="man.number.generations" />
        </th>
        <td class="w80">
          <gsmsg:write key="main.man080kn.20" arg0="<%= generationList[((Integer) generation).intValue() - 1] %>" />
        </td>
      </tr>
    </logic:notEqual>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.output" />
      </th>
      <td class="w80">
        <logic:equal name="man080knForm" property="man080zipOutputKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.ZIP_BACKUP_FLG_OFF) %>">
          <gsmsg:write key="main.not.compress" />
        </logic:equal>
        <logic:equal name="man080knForm" property="man080zipOutputKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.ZIP_BACKUP_FLG_ON) %>">
          <gsmsg:write key="main.zip.format.output" />
        </logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('entry');">
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