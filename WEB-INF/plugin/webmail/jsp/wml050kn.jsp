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
<title>GROUPSESSION <gsmsg:write key="wml.wml010.25" /> <gsmsg:write key="cmn.autodelete.setting" /><gsmsg:write key="cmn.check" /></title>
  <meta name="format-detection" content="telephone=no">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>

<html:form action="/webmail/wml050kn">

<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>

<html:hidden property="backScreen" />
<html:hidden name="wml050knForm" property="wmlCmdMode" />
<html:hidden name="wml050knForm" property="wmlViewAccount" />
<html:hidden name="wml050knForm" property="wmlAccountMode" />
<html:hidden name="wml050knForm" property="wmlAccountSid" />
<html:hidden name="wml050knForm" property="wml050dustKbn" />
<html:hidden name="wml050knForm" property="wml050dustYear" />
<html:hidden name="wml050knForm" property="wml050dustMonth" />
<html:hidden name="wml050knForm" property="wml050dustDay" />
<html:hidden name="wml050knForm" property="wml050sendKbn" />
<html:hidden name="wml050knForm" property="wml050sendYear" />
<html:hidden name="wml050knForm" property="wml050sendMonth" />
<html:hidden name="wml050knForm" property="wml050sendDay" />
<html:hidden name="wml050knForm" property="wml050draftKbn" />
<html:hidden name="wml050knForm" property="wml050draftYear" />
<html:hidden name="wml050knForm" property="wml050draftMonth" />
<html:hidden name="wml050knForm" property="wml050draftDay" />
<html:hidden name="wml050knForm" property="wml050resvKbn" />
<html:hidden name="wml050knForm" property="wml050resvYear" />
<html:hidden name="wml050knForm" property="wml050resvMonth" />
<html:hidden name="wml050knForm" property="wml050resvDay" />
<html:hidden name="wml050knForm" property="wml050keepKbn" />
<html:hidden name="wml050knForm" property="wml050keepYear" />
<html:hidden name="wml050knForm" property="wml050keepMonth" />
<html:hidden name="wml050knForm" property="wml050keepDay" />

<html:hidden name="wml050knForm" property="wml050initFlg" />

<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="cmn.autodelete.setting" /><gsmsg:write key="cmn.check" />
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
  <table class="table-left" id="wml_settings">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.trash" />
      </th>
      <td class="w75">
        <bean:write name="wml050knForm" property="dustDelSetUp" />
        <logic:equal name="wml050knForm" property="wml050dustKbn" value="2">
          <span class="ml10">
            <bean:define id="dyear" name="wml050knForm" property="dustDelSetUpYear" type="java.lang.String" />
            <gsmsg:write key="cmn.year" arg0="<%= dyear %>" />
            <bean:define id="dmonth" name="wml050knForm" property="dustDelSetUpMonth" type="java.lang.String" />
            <gsmsg:write key="cmn.months" arg0="<%= dmonth %>" />
            <bean:write name="wml050knForm" property="dustDelSetUpDay" /><gsmsg:write key="cmn.day" />
            <gsmsg:write key="wml.73" />
          </span>
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.19" />
      </th>
      <td>
        <bean:write name="wml050knForm" property="sendDelSetUp" />
        <logic:equal name="wml050knForm" property="wml050sendKbn" value="2">
          <span class="ml10">
            <bean:define id="syear" name="wml050knForm" property="sendDelSetUpYear" type="java.lang.String" />
            <gsmsg:write key="cmn.year" arg0="<%= syear %>" />
            <bean:define id="smonth" name="wml050knForm" property="sendDelSetUpMonth" type="java.lang.String" />
            <gsmsg:write key="cmn.months" arg0="<%= smonth %>" />
            <bean:write name="wml050knForm" property="sendDelSetUpDay" /><gsmsg:write key="cmn.day" />
            <gsmsg:write key="wml.73" />
          </span>
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.draft" />
      </th>
      <td>
        <bean:write name="wml050knForm" property="draftDelSetUp" />
        <logic:equal name="wml050knForm" property="wml050draftKbn" value="2">
          <span class="ml10">
            <bean:define id="ddyear" name="wml050knForm" property="draftDelSetUpYear" type="java.lang.String" />
            <gsmsg:write key="cmn.year" arg0="<%=ddyear %>" />
            <bean:define id="ddmonth" name="wml050knForm" property="draftDelSetUpMonth" type="java.lang.String" />
            <gsmsg:write key="cmn.months" arg0="<%= ddmonth %>" />
            <bean:write name="wml050knForm" property="draftDelSetUpDay" /><gsmsg:write key="cmn.day" />
            <gsmsg:write key="wml.73" />
          </span>
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.37" />
      </th>
      <td>
        <bean:write name="wml050knForm" property="resvDelSetUp" />
          <logic:equal name="wml050knForm" property="wml050resvKbn" value="2">
            <span class="ml10">
              <bean:define id="ryear" name="wml050knForm" property="resvDelSetUpYear" type="java.lang.String" />
              <gsmsg:write key="cmn.year" arg0="<%=ryear %>" />
              <bean:define id="rmonth" name="wml050knForm" property="resvDelSetUpMonth" type="java.lang.String" />
              <gsmsg:write key="cmn.months" arg0="<%= rmonth %>" />
              <bean:write name="wml050knForm" property="resvDelSetUpDay" /><gsmsg:write key="cmn.day" />
              <gsmsg:write key="wml.73" />
            </span>
          </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.strage" />
      </th>
      <td>
        <bean:write name="wml050knForm" property="keepDelSetUp" />
          <logic:equal name="wml050knForm" property="wml050keepKbn" value="2">
            <span class="ml10">
              <bean:define id="kyear" name="wml050knForm" property="keepDelSetUpYear" type="java.lang.String" />
              <gsmsg:write key="cmn.year" arg0="<%= kyear %>" />
              <bean:define id="kmonth" name="wml050knForm" property="keepDelSetUpMonth" type="java.lang.String" />
              <gsmsg:write key="cmn.months" arg0="<%= kmonth %>" />
              <bean:write name="wml050knForm" property="keepDelSetUpDay" /><gsmsg:write key="cmn.day" />
              <gsmsg:write key="wml.73" />
            </span>
          </logic:equal>
      </td>
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