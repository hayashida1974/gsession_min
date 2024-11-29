<%@page import="java.util.Arrays"%>
<%@page import="org.apache.struts.util.LabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>
<%@ taglib tagdir="/WEB-INF/tags/dandd" prefix="dandd" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
  <head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
    <title>GROUPSESSION <gsmsg:write key="rng.rng120.08" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
    <script src="../ringi/js/rng260.js?<%= GSConst.VERSION_PARAM %>"></script>
    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
    <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
    <link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../ringi/css/ringi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
  </head>
  <body>
    <html:form action="/ringi/rng260">
      <input type="hidden" name="CMD" value="">
      <%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
      <html:hidden property="backScreen" />
      <html:hidden property="rngTemplateMode" />
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
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.rng080.05" />
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.setting" />" class="baseBtn" onClick="buttonPush('confirm');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_ktool.png" alt="<gsmsg:write key="cmn.setting" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_ktool.png" alt="<gsmsg:write key="cmn.setting" />">
                <gsmsg:write key="cmn.setting" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backMenu');">
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
        <table class="table-left">
          <!-- ユーザ選択 -->
          <tr class="w100">
            <th class="w25">
              <gsmsg:write key="cmn.form.user" />
            </th>
            <td class="w75">
              <html:select property="rng260GrpSid" onchange="changeGroup('chggroup');">
                <logic:notEmpty name="rng260Form" property="rng260GroupCombo" scope="request">
                  <logic:iterate id="gpBean" name="rng260Form" property="rng260GroupCombo" scope="request">
                    <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                    <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:iterate>
                </logic:notEmpty>
              </html:select>
              <logic:notEmpty name="rng260Form" property="rng260UserCombo">
                <html:select property="rng260User" onchange="changeUser('chguser');">
                  <logic:iterate id="nowSelectUserLabel" name="rng260Form" property="rng260UserCombo">
                    <%-- 初期検索対象ユーザ設定のためのデータを取得  --%>
                    <bean:define id="selectUser" name="rng260Form" property="rng260User"/>
                    <bean:define id="nowSelectUser" name="nowSelectUserLabel" property="value"/>
                    <%-- 検索対象ユーザの初期設定(無効) --%>
                    <logic:equal name="nowSelectUserLabel" property="usrUkoFlg" value="1">
                      <% if (String.valueOf(selectUser).equals(String.valueOf(nowSelectUser))) { %>
                        <option value='<bean:write name="nowSelectUserLabel" property="value"/>' class="mukoUserOption" selected>
                      <% } else { %>
                        <option value='<bean:write name="nowSelectUserLabel" property="value"/>' class="mukoUserOption">
                      <% } %>
                      <bean:write name="nowSelectUserLabel" property="label"/></option>
                    </logic:equal>
                    <%-- 検索対象ユーザの初期設定(有効) --%>
                    <logic:notEqual name="nowSelectUserLabel" property="usrUkoFlg" value="1">
                      <% if (String.valueOf(selectUser).equals(String.valueOf(nowSelectUser))) { %>
                        <option value='<bean:write name="nowSelectUserLabel" property="value"/>' selected>
                      <% } else { %>
                        <option value='<bean:write name="nowSelectUserLabel" property="value"/>'>
                      <% } %>
                      <bean:write name="nowSelectUserLabel" property="label"/></option>
                    </logic:notEqual>
                  </logic:iterate>
                </html:select>
              </logic:notEmpty>
            </td>
          </tr>
          <logic:greaterEqual name="rng260Form" property="rng260GrpSid" value="0">
            <logic:greaterThan name="rng260Form" property="rng260User" value="0">
              <!-- 代理人設定 -->
              <tr class="w100">
                <th class="w25">
                  <gsmsg:write key="rng.rng120.10" />
                </th>
                <td class="w75" id="dairiSettingArea">

                  <ui:usrgrpselector name="rng260Form" property="usrgroupselector" styleClass="hp160"/>

                </td>
              </tr>
              <!-- 代理人期間 -->
              <tr class="w100">
                <th class="w25">
                  <gsmsg:write key="rng.rng120.11" />
                </th>
                <td class="w75" id="dairiKikanArea">
                  <span class="verAlignMid">
                    <html:radio name="rng260Form" styleId="dairiKikan_01" property="rng260DairiLast" onchange="radiocheck('on')" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_DAIRI_KIKAN) %>" />
                    <label for="dairiKikan_01">
                      <gsmsg:write key="rng.rng120.12" />
                    </label>
                    <html:radio styleClass="ml10" name="rng260Form" styleId="dairiKikan_02" property="rng260DairiLast" onchange="radiocheck('off')" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_DAIRI_NOT_KIKAN) %>" />
                    <label for="dairiKikan_02">
                      <gsmsg:write key="rng.rng120.13" />
                    </label>
                  </span>
                  <div class="pt5">
                    <gsmsg:write key="cmn.start" /><gsmsg:write key="cmn.colon" />
                    <span class="verAlignMid">
                      <html:text name="rng260Form" property="rng260DairiStart" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="dairiKikanStart" />
                      <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                    </span>
                    <span id="finish" class="ml20">
                      <gsmsg:write key="cmn.end" /><gsmsg:write key="cmn.colon" />
                      <span class="verAlignMid">
                        <html:text name="rng260Form" property="rng260DairiFinish" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="dairiKikanFinish" />
                        <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
                      </span>
                    </span>
                  </div>
                </td>
              </tr>
            </logic:greaterThan>
          </logic:greaterEqual>
        </table>
        <div class="footerBtn_block">
          <button type="button" value="<gsmsg:write key="cmn.setting" />" class="baseBtn" onClick="buttonPush('confirm');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ktool.png" alt="<gsmsg:write key="cmn.setting" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_ktool.png" alt="<gsmsg:write key="cmn.setting" />">
            <gsmsg:write key="cmn.setting" />
          </button>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backMenu');">
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