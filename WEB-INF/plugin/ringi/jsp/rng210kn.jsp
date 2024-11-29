<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.rng.RngConst" %>
<!DOCTYPE html>
<html:html>
  <head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
    <title>GROUPSESSION <gsmsg:write key="rng.62" /> <gsmsg:write key="cmn.preferences.kn" /></title>
  </head>
  <body>
    <html:form action="/ringi/rng210kn">
      <input type="hidden" name="CMD" value="">
      <%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
      <html:hidden property="backScreen"/>
      <html:hidden property="rng210Format"/>
      <html:hidden property="rng210DispFormat"/>
      <html:hidden property="rng210Init"/>
      <html:hidden property="rng210Manual"/>
      <html:hidden property="rng210Cmd"/>
      <html:hidden property="rng210Date"/>
      <html:hidden property="rng210Reset"/>
      <html:hidden property="rng210Zeroume"/>
      <html:hidden property="rng210Title"/>
      <html:hidden property="rng200Sid"/>
      <input type="hidden" name="helpPrm" value="<bean:write name="rng210Form" property="rng210Cmd" />">
      <!-- BODY -->
      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
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
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><!--
            --><logic:equal name="rng210Form" property="rng200Sid" value="0"><gsmsg:write key="rng.rng210kn.01" /></logic:equal><!--
            --><logic:notEqual name="rng210Form" property="rng200Sid" value="0"><gsmsg:write key="rng.rng210kn.02" /></logic:notEqual>
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                <gsmsg:write key="cmn.final" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backList');">
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
          <html:errors />
        </logic:messagesPresent>
        <table class="table-left w100">
          <!-- タイトル -->
          <tr>
            <th class="w25" id="cirEditArea1">
              <gsmsg:write key="cmn.title" />
            </th>
            <td class="w75">
              <bean:write name="rng210Form" property="rng210Title" />
            </td>
          </tr>
          <!-- フォーマット -->
          <tr>
            <th class="w25" id="cirEditArea1">
              <gsmsg:write key="rng.12" />
            </th>
            <td class="w75">
              <bean:write name="rng210Form" property="rng210DispFormat" />
            </td>
          </tr>
          <!-- 0埋め値 -->
          <tr>
            <th class="w25" id="cirEditArea1">
              <gsmsg:write key="rng.rng210.10" />
            </th>
            <td>
              <bean:write name="rng210Form" property="rng210Zeroume" />
            </td>
          </tr>
          <!-- 採番初期値 -->
          <tr>
            <th class="w25" id="cirEditArea1">
              <gsmsg:write key="rng.rng210.01" />
            </th>
            <td class="w75">
              <bean:write name="rng210Form" property="rng210Init" />
            </td>
          </tr>
          <!-- リセット期間 -->
          <tr>
            <th class="w25" id="cirEditArea1">
              <gsmsg:write key="rng.rng210.07" />
            </th>
            <td class="w75">
              <logic:equal name="rng210knForm" property="rng210Reset" value="<%= String.valueOf(RngConst.RAR_RESET_NONE) %>">
                <gsmsg:write key="rng.rng210.08" />
              </logic:equal>
              <logic:equal name="rng210knForm" property="rng210Reset" value="<%= String.valueOf(RngConst.RAR_RESET_YEAR) %>">
                <gsmsg:write key="cmn.year2" />
              </logic:equal>
              <logic:equal name="rng210knForm" property="rng210Reset" value="<%= String.valueOf(RngConst.RAR_RESET_MONTH) %>">
                <gsmsg:write key="cmn.Monday" />
              </logic:equal>
              <logic:equal name="rng210knForm" property="rng210Reset" value="<%= String.valueOf(RngConst.RAR_RESET_DAY) %>">
                <gsmsg:write key="cmn.day" />
              </logic:equal>
            </td>
          </tr>
          <!-- 手動設定 -->
          <tr>
            <th class="w25" id="cirEditArea1">
              <gsmsg:write key="rng.rng210.04" />
            </th>
            <td class="w75">
              <logic:equal name="rng210knForm" property="rng210Manual" value="<%= String.valueOf(RngConst.RAR_SINSEI_MANUAL_TEMPLATE) %>">
                <gsmsg:write key="rng.rng180.02" />
              </logic:equal>
              <logic:equal name="rng210knForm" property="rng210Manual" value="<%= String.valueOf(RngConst.RAR_SINSEI_MANUAL_KYOKA) %>">
                <gsmsg:write key="cmn.permit" />
              </logic:equal>
              <logic:equal name="rng210knForm" property="rng210Manual" value="<%= String.valueOf(RngConst.RAR_SINSEI_MANUAL_NOT_KYOKA) %>">
                <gsmsg:write key="cmn.not.permit" />
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
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backList');">
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