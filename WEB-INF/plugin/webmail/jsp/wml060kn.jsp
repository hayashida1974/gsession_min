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
<title>GROUPSESSION <gsmsg:write key="cmn.manual.delete.kn" /></title>
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

<html:form action="/webmail/wml060kn">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden name="wml060knForm" property="wmlViewAccount" />
<html:hidden name="wml060knForm" property="wml060delKbn1" />
<html:hidden name="wml060knForm" property="wml060delYear1" />
<html:hidden name="wml060knForm" property="wml060delMonth1" />
<html:hidden name="wml060knForm" property="wml060delDay1" />
<html:hidden name="wml060knForm" property="wml060delKbn2" />
<html:hidden name="wml060knForm" property="wml060delYear2" />
<html:hidden name="wml060knForm" property="wml060delMonth2" />
<html:hidden name="wml060knForm" property="wml060delDay2" />
<html:hidden name="wml060knForm" property="wml060delKbn3" />
<html:hidden name="wml060knForm" property="wml060delYear3" />
<html:hidden name="wml060knForm" property="wml060delMonth3" />
<html:hidden name="wml060knForm" property="wml060delDay3" />
<html:hidden name="wml060knForm" property="wml060delKbn4" />
<html:hidden name="wml060knForm" property="wml060delYear4" />
<html:hidden name="wml060knForm" property="wml060delMonth4" />
<html:hidden name="wml060knForm" property="wml060delDay4" />
<html:hidden name="wml060knForm" property="wml060delKbn5" />
<html:hidden name="wml060knForm" property="wml060delYear5" />
<html:hidden name="wml060knForm" property="wml060delMonth5" />
<html:hidden name="wml060knForm" property="wml060delDay5" />


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="cmn.manual.delete" /><gsmsg:write key="cmn.check" />
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
       <logic:equal name="wml060knForm" property="wml060delKbn1" value="0">
            <bean:write name="wml060knForm" property="manuDustDelSetUp" />
          </logic:equal>
          <logic:equal name="wml060knForm" property="wml060delKbn1" value="1">
            <span class="del"><bean:write name="wml060knForm" property="manuDustDelSetUp" /></span>
            <span class="ml10">
              <bean:define id="dyear" name="wml060knForm" property="manuDustDelSetUpYear" type="java.lang.String" />
              <gsmsg:write key="cmn.year" arg0="<%= dyear %>" />
              <bean:define id="dmonth" name="wml060knForm" property="manuDustDelSetUpMonth" type="java.lang.String" />
              <gsmsg:write key="cmn.months" arg0="<%= dmonth %>" />
              <bean:write name="wml060knForm" property="manuDustDelSetUpDay" /><gsmsg:write key="cmn.day" />
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
        <logic:equal name="wml060knForm" property="wml060delKbn2" value="0">
            <bean:write name="wml060knForm" property="manuSendDelSetUp" />
          </logic:equal>
          <logic:equal name="wml060knForm" property="wml060delKbn2" value="1">
            <span class="del"><bean:write name="wml060knForm" property="manuSendDelSetUp" /></span>
            <span class="ml10">
              <bean:define id="syear" name="wml060knForm" property="manuSendDelSetUpYear" type="java.lang.String" />
              <gsmsg:write key="cmn.year" arg0="<%= syear %>" />
              <bean:define id="smonth" name="wml060knForm" property="manuSendDelSetUpMonth" type="java.lang.String" />
              <gsmsg:write key="cmn.months" arg0="<%= smonth %>" />
              <bean:write name="wml060knForm" property="manuSendDelSetUpDay" /><gsmsg:write key="cmn.day" />
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
      <logic:equal name="wml060knForm" property="wml060delKbn3" value="0">
            <bean:write name="wml060knForm" property="manuDraftDelSetUp" />
          </logic:equal>
          <logic:equal name="wml060knForm" property="wml060delKbn3" value="1">
            <span class="del"><bean:write name="wml060knForm" property="manuDraftDelSetUp" /></span>
            <span class="ml10">
              <bean:define id="ddyear" name="wml060knForm" property="manuDraftDelSetUpYear" type="java.lang.String" />
              <gsmsg:write key="cmn.year" arg0="<%= ddyear %>" />
              <bean:define id="ddmonth" name="wml060knForm" property="manuDraftDelSetUpMonth" type="java.lang.String" />
              <gsmsg:write key="cmn.months" arg0="<%= ddmonth %>" />
              <bean:write name="wml060knForm" property="manuDraftDelSetUpDay" /><gsmsg:write key="cmn.day" />
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
      <logic:equal name="wml060knForm" property="wml060delKbn4" value="0">
            <bean:write name="wml060knForm" property="manuResvDelSetUp" />
          </logic:equal>
          <logic:equal name="wml060knForm" property="wml060delKbn4" value="1">
            <span class="del"><bean:write name="wml060knForm" property="manuResvDelSetUp" /></span>
            <span class="ml10">
              <bean:define id="ryear" name="wml060knForm" property="manuResvDelSetUpYear" type="java.lang.String" />
              <gsmsg:write key="cmn.year" arg0="<%= ryear %>" />
              <bean:define id="rmonth" name="wml060knForm" property="manuResvDelSetUpMonth" type="java.lang.String" />
              <gsmsg:write key="cmn.months" arg0="<%= rmonth %>" />
              <bean:write name="wml060knForm" property="manuResvDelSetUpDay" /><gsmsg:write key="cmn.day" />
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
       <logic:equal name="wml060knForm" property="wml060delKbn5" value="0">
            <bean:write name="wml060knForm" property="manuKeepDelSetUp" />
          </logic:equal>
          <logic:equal name="wml060knForm" property="wml060delKbn5" value="1">
            <span class="del"><bean:write name="wml060knForm" property="manuKeepDelSetUp" /></span>
            <span class="ml10">
              <bean:define id="kyear" name="wml060knForm" property="manuKeepDelSetUpYear" type="java.lang.String" />
              <gsmsg:write key="cmn.year" arg0="<%= kyear %>" />
              <bean:define id="kmonth" name="wml060knForm" property="manuKeepDelSetUpMonth" type="java.lang.String" />
              <gsmsg:write key="cmn.months" arg0="<%= kmonth %>" />
              <bean:write name="wml060knForm" property="manuKeepDelSetUpDay" /><gsmsg:write key="cmn.day" />
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