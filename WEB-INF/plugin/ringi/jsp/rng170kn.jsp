<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%-- 手動削除区分 --%>
<%
  String manuDelNo        = String.valueOf(jp.groupsession.v2.rng.RngConst.MANU_DEL_NO);
  String manuDelOk        = String.valueOf(jp.groupsession.v2.rng.RngConst.MANU_DEL_OK);
%>
<!DOCTYPE html>
<html:html>
  <head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
    <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
    <title>GROUPSESSION <gsmsg:write key="cmn.manual.delete.kn" /></title>
  </head>
  <body>
    <html:form action="/ringi/rng170kn">
      <input type="hidden" name="CMD" value="">
      <%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
      <html:hidden property="backScreen" />
      <html:hidden property="rngTemplateMode" />
      <html:hidden name="rng170knForm" property="rng170pendingKbn" />
      <html:hidden name="rng170knForm" property="rng170pendingYear" />
      <html:hidden name="rng170knForm" property="rng170pendingMonth" />
      <html:hidden name="rng170knForm" property="rng170pendingDay" />
      <html:hidden name="rng170knForm" property="rng170completeKbn" />
      <html:hidden name="rng170knForm" property="rng170completeYear" />
      <html:hidden name="rng170knForm" property="rng170completeMonth" />
      <html:hidden name="rng170knForm" property="rng170completeDay" />
      <html:hidden name="rng170knForm" property="rng170draftKbn" />
      <html:hidden name="rng170knForm" property="rng170draftYear" />
      <html:hidden name="rng170knForm" property="rng170draftMonth" />
      <html:hidden name="rng170knForm" property="rng170draftDay" />
      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
      <!-- BODY -->
      <div class="kanriPageTitle w80 mrl_auto">
        <ul>
          <li>
            <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
          </li>
          <li>
            <gsmsg:write key="cmn.admin.setting" />
          </li>
          <li class="pageTitle_subFont">
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.manual.delete.kn" />
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
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
        <table class="table-left w100">
          <!-- 申請中 -->
          <tr>
            <th class="w25">
              <gsmsg:write key="rng.48" />
            </th>
            <td class="w75">
              <logic:equal name="rng170knForm" property="rng170pendingKbn" value="<%= manuDelNo %>" >
                <gsmsg:write key="cmn.dont.delete" />
              </logic:equal>
              <logic:equal name="rng170knForm" property="rng170pendingKbn" value="<%= manuDelOk %>" >
                <bean:define id="year1" name="rng170knForm" property="rng170pendingYear" type="java.lang.Integer" />
                &nbsp;<gsmsg:write key="cmn.year" arg0="<%= String.valueOf(year1.intValue()) %>" />
                <bean:define id="month1" name="rng170knForm" property="rng170pendingMonth" type="java.lang.Integer" />
                <gsmsg:write key="cmn.months" arg0="<%= String.valueOf(month1.intValue()) %>" />
                <bean:write name="rng170knForm" property="rng170pendingDay" /><gsmsg:write key="cmn.day" />
                <gsmsg:write key="cmn.comp.oe" /><gsmsg:write key="cmn.after.data" />
              </logic:equal>
            </td>
          </tr>
          <!-- 完了 -->
          <tr>
            <th class="w25">
              <gsmsg:write key="cmn.complete" />
            </th>
            <td class="w75">
              <logic:equal name="rng170knForm" property="rng170completeKbn" value="<%= manuDelNo %>" >
                <gsmsg:write key="cmn.dont.delete" />
              </logic:equal>
              <logic:equal name="rng170knForm" property="rng170completeKbn" value="<%= manuDelOk %>" >
                <bean:define id="year2" name="rng170knForm" property="rng170completeYear" type="java.lang.Integer" />
                &nbsp;<gsmsg:write key="cmn.year" arg0="<%= String.valueOf(year2.intValue()) %>" />
                <bean:define id="month2" name="rng170knForm" property="rng170completeMonth" type="java.lang.Integer" />
                <gsmsg:write key="cmn.months" arg0="<%= String.valueOf(month2.intValue()) %>" />
                <bean:write name="rng170knForm" property="rng170completeDay" /><gsmsg:write key="cmn.day" />
                <gsmsg:write key="cmn.comp.oe" /><gsmsg:write key="cmn.after.data" />
              </logic:equal>
            </td>
          </tr>
          <!-- 草稿 -->
          <tr>
            <th class="w25">
              <gsmsg:write key="cmn.draft" />
            </th>
            <td class="w75">
              <logic:equal name="rng170knForm" property="rng170draftKbn" value="<%= manuDelNo %>" >
                <gsmsg:write key="cmn.dont.delete" />
              </logic:equal>
              <logic:equal name="rng170knForm" property="rng170draftKbn" value="<%= manuDelOk %>" >
                <bean:define id="year3" name="rng170knForm" property="rng170draftYear" type="java.lang.Integer" />
                &nbsp;<gsmsg:write key="cmn.year" arg0="<%= String.valueOf(year3.intValue()) %>" />
                <bean:define id="month3" name="rng170knForm" property="rng170draftMonth" type="java.lang.Integer" />
                <gsmsg:write key="cmn.months" arg0="<%= String.valueOf(month3.intValue()) %>" />
                <bean:write name="rng170knForm" property="rng170draftDay" /><gsmsg:write key="cmn.day" />
                <gsmsg:write key="cmn.comp.oe" /><gsmsg:write key="cmn.after.data" />
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
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backInput');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </div>
      <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
    </html:form>
  </body>
</html:html>