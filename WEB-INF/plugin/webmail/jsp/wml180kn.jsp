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
<title>GROUPSESSION <gsmsg:write key="cmn.shortmail" /> <gsmsg:write key="wml.21" /><gsmsg:write key="cmn.check" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../webmail/js/wml180kn.js?<%= GSConst.VERSION_PARAM %>"></script>

</head>

<body onload="wml180knChangeDelKbn();">

<html:form action="/webmail/wml180kn">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden name="wml180knForm" property="wmlViewAccount" />

<html:hidden name="wml180knForm" property="wml180delKbn" />
<html:hidden name="wml180knForm" property="wml180delYear" />
<html:hidden name="wml180knForm" property="wml180delMonth" />
<html:hidden name="wml180knForm" property="wml180delDay" />
<html:hidden name="wml180knForm" property="wml180delYearFr" />
<html:hidden name="wml180knForm" property="wml180delMonthFr" />
<html:hidden name="wml180knForm" property="wml180delDayFr" />
<html:hidden name="wml180knForm" property="wml180delYearTo" />
<html:hidden name="wml180knForm" property="wml180delMonthTo" />
<html:hidden name="wml180knForm" property="wml180delDayTo" />


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.21" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
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
<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
  <table class="table-left" id="wml_settings">
    <tr>
      <th class="w25">
        <gsmsg:write key="wml.59" />
      </th>
      <td class="w75" id="dateElement">
       <bean:define id="yr" name="wml180knForm" property="manuDelSetUpYear" type="java.lang.String" />
        <gsmsg:write key="cmn.year" arg0="<%= yr %>" />
        <bean:define id="month" name="wml180knForm" property="manuDelSetUpMonth" type="java.lang.String" />
        <gsmsg:write key="cmn.months" arg0="<%= month %>" />
        <bean:write name="wml180knForm" property="manuDelSetUpDay" /><gsmsg:write key="cmn.day" />
        <gsmsg:write key="wml.73" />
      </td>
      <td class="w75" id="dateAreaElement">
        <div id="dateAreaElement2"><gsmsg:write key="wml.05" /></div>
        <bean:define id="yr2" name="wml180knForm" property="wml180delYearFr" type="java.lang.Integer" />
        <gsmsg:write key="cmn.year" arg0="<%= String.valueOf(yr2) %>" />
        <bean:write name="wml180knForm" property="wml180delMonthFr" /><gsmsg:write key="cmn.month" />
        <bean:write name="wml180knForm" property="wml180delDayFr" /><gsmsg:write key="cmn.day" />
        <span class="ml5 mr5"><gsmsg:write key="tcd.153" /></span>
        <bean:define id="yr3" name="wml180knForm" property="wml180delYearTo" type="java.lang.Integer" />
        <gsmsg:write key="cmn.year" arg0="<%= String.valueOf(yr3) %>" />
        <bean:write name="wml180knForm" property="wml180delMonthTo" /><gsmsg:write key="cmn.month" />
        <bean:write name="wml180knForm" property="wml180delDayTo" /><gsmsg:write key="cmn.day" />
      </td>
      <td id="allDelElement"><gsmsg:write key="cmn.delete.all" /></td>
      </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
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