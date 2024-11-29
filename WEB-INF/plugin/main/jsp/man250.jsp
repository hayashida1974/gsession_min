<%@page import="org.apache.struts.util.LabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%
String orderAsc  = String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC);
String orderDesc = String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC);
%>

<%
String sortDate  = String.valueOf(jp.groupsession.v2.man.man250.Man250Biz.SORT_KEY_DATE);
String sortPulugin  = String.valueOf(jp.groupsession.v2.man.man250.Man250Biz.SORT_KEY_PLUGIN);
String sortLevel  = String.valueOf(jp.groupsession.v2.man.man250.Man250Biz.SORT_KEY_LOG_LEVEL);
String sortUsrName  = String.valueOf(jp.groupsession.v2.man.man250.Man250Biz.SORT_KEY_USR_NAME);
String sortPgName  = String.valueOf(jp.groupsession.v2.man.man250.Man250Biz.SORT_KEY_PG_NAME);
String sortOpCode  = String.valueOf(jp.groupsession.v2.man.man250.Man250Biz.SORT_KEY_OP_CODE);
String sortValue  = String.valueOf(jp.groupsession.v2.man.man250.Man250Biz.SORT_KEY_VALUE);
String sortLogIp  = String.valueOf(jp.groupsession.v2.man.man250.Man250Biz.SORT_KEY_LOG_IP);

String logLevelError  = gsMsg.getMessage(request, "man.error");
String logLevelWarn  = gsMsg.getMessage(request, "cmn.warning");
String logLevelInfo  = gsMsg.getMessage(request, "main.man240.2");
String logLevelTrace  = gsMsg.getMessage(request, "main.man240.3");

%>

<html:html>
<head>

<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">

<title>GROUPSESSION <gsmsg:write key="main.man002.53" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man250.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<theme:css filename="theme.css"/>
</head>
<body class="body_03">
<html:form action="/main/man250">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="yearRangeMaxFr" value="0">
<input type="hidden" name="yearRangeMaxTo" value="0">
<input type="hidden" name="hourDivision" value="1" />
<html:hidden property="man250PageNum" />
<html:hidden property="man250SvSltGroup" />
<html:hidden property="man250SvSltUser" />
<html:hidden property="man250SvSltYearFr" />
<html:hidden property="man250SvSltMonthFr" />
<html:hidden property="man250SvSltDayFr" />
<html:hidden property="man250SvSltHourFr" />
<html:hidden property="man250SvSltMinFr" />
<html:hidden property="man250SvSltYearTo" />
<html:hidden property="man250SvSltMonthTo" />
<html:hidden property="man250SvSltDayTo" />
<html:hidden property="man250SvSltHourTo" />
<html:hidden property="man250SvSltMinTo" />
<html:hidden property="man250SvSltPlugin" />
<html:hidden property="man250SvSltLogError" />
<html:hidden property="man250SvSltLogWarn" />
<html:hidden property="man250SvSltLogInfo" />
<html:hidden property="man250SvSltLogTrace" />
<html:hidden property="man250SvOrderKey1" />
<html:hidden property="man250SvSortKey1" />
<html:hidden property="man250SvOrderKey2" />
<html:hidden property="man250SvSortKey2" />

<logic:notEmpty name="man250Form" property="man250SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="man250Form" property="man250SvSearchTarget" scope="request">
    <input type="hidden" name="man250SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
  <html:hidden property="man250SvKeyWord" />
  <html:hidden property="man250SvKeyWordKbn" />
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--BODY -->

<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man002.53" />
    </li>
    <li>
      <div>
        <logic:notEmpty name="man250Form" property="man250DspList">
          <button type="button" class="baseBtn" value="<gsmsg:write key="man.purge" />" onClick="buttonPush('man250delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="man.purge" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="man.purge" />">
            <gsmsg:write key="man.purge" />
          </button>
        </logic:notEmpty>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKtool');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="bgC_header1" colspan="4">&nbsp;</th>
    </tr>
    <tr>
      <th class="w15">
        <gsmsg:write key="cmn.loglevel" />
      </th>
      <td class="w85" colspan="3">
        <div class="verAlignMid">
          <html:checkbox styleId="search_scope_01" property="man250SltLogError" value="1" />
          <label for="search_scope_01"><img class="btn_classicImg-display ml5 mr5" src="../main/images/classic/icon_log_error.gif" alt="<gsmsg:write key="man.error" />"><img class="btn_originalImg-display ml5 mr5" src="../main/images/original/icon_error.png" alt="<gsmsg:write key="man.error" />"><gsmsg:write key="man.error" /></label>
          <html:checkbox styleId="search_scope_02" styleClass="ml20" property="man250SltLogWarn" value="1" />
          <label for="search_scope_02"><img class="btn_classicImg-display ml5 mr5" src="../main/images/classic/icon_log_warn.gif" alt="<gsmsg:write key="cmn.warning" />"><img class="btn_originalImg-display ml5 mr5" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />"><gsmsg:write key="cmn.warning" /></label>
          <html:checkbox styleId="search_scope_03" styleClass="ml20" property="man250SltLogInfo" value="1" />
          <label for="search_scope_03"><img class="btn_classicImg-display ml5 mr5" src="../main/images/classic/icon_log_info.gif" alt="<gsmsg:write key="main.man240.2" />"><img class="btn_originalImg-display ml5 mr5" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="main.man240.2" />"><gsmsg:write key="main.man240.2" /></label>
          <html:checkbox styleId="search_scope_04" styleClass="ml20" property="man250SltLogTrace" value="1" />
          <label for="search_scope_04"><img class="btn_classicImg-display ml5 mr5" src="../main/images/classic/icon_log_trace.gif" alt="<gsmsg:write key="main.man240.3" />"><img class="btn_originalImg-display ml5 mr5" src="../common/images/original/icon_info.png" alt="<gsmsg:write key="main.man240.3" />"><gsmsg:write key="main.man240.3" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w15 no_w">
        <gsmsg:write key="main.man250.2" />
      </th>
      <td class="w85" colspan="3">
        <div class="verAlignMid">
          <html:text name="man250Form" property="man250SltDateFr" maxlength="10" styleId="frDate" styleClass="txt_c wp95 datepicker js_frDatePicker" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          <button type="button" class="webIconBtn ml5" onClick="moveDay($('#frDate')[0], 1);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
            <i class="icon-paging_left "></i>
          </button>
          <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="moveDay($('#frDate')[0], 2);">
            <gsmsg:write key="cmn.today" />
          </button>
          <span>
            <a class="fw_b todayBtn original-display" onClick="moveDay($('#frDate')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </a>
          </span>
          <button type="button" class="webIconBtn" onClick="moveDay($('#frDate')[0], 3);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
            <i class="icon-paging_right "></i>
          </button>
          <html:text name="man250Form" property="man250SltTimeFr" styleId="fr_clock" maxlength="5" styleClass="ml5 clockpicker js_clockpicker txt_c wp60"/>
          <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w15 no_w">
        <gsmsg:write key="main.man250.3" />
      </th>
      <td class="w85" colspan="3">
        <div class="verAlignMid">
          <html:text name="man250Form" property="man250SltDateTo" maxlength="10" styleId="toDate" styleClass="txt_c wp95 datepicker js_toDatePicker" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          <button type="button" class="webIconBtn ml5" onClick="moveDay($('#toDate')[0], 1);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
            <i class="icon-paging_left "></i>
          </button>
          <button type="button" class="baseBtn classic-display" onClick="moveDay($('#toDate')[0], 2);">
            <gsmsg:write key="cmn.today" />
          </button>
          <span>
            <a class="fw_b todayBtn original-display" onClick="moveDay($('#toDate')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </a>
          </span>
          <button type="button" class="webIconBtn" onClick="moveDay($('#toDate')[0], 3);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
            <i class="icon-paging_right "></i>
          </button>
          <html:text name="man250Form" property="man250SltTimeTo" styleId="to_clock" maxlength="5" styleClass="ml5 clockpicker js_clockpicker txt_c wp60"/>
          <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w15 no_w">
        <gsmsg:write key="cmn.plugin" />
      </th>
      <td class="w85" colspan="3">
        <logic:notEmpty name="man250Form" property="plgLabel">
          <html:select property="man250SltPlugin" styleClass="wp150">
            <html:optionsCollection name="man250Form" property="plgLabel" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w15 no_w">
        <gsmsg:write key="man.run.user" />
      </th>
      <td class="w85" colspan="3">
        <logic:notEmpty name="man250Form" property="grpLabel">
          <html:select property="man250SltGroup" styleClass="wp200" onchange="changeGroupCombo();">
            <html:optionsCollection name="man250Form" property="grpLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="iconBtn-border ml5 mr5" value="Cal" onClick="openGroupWindowForMan250(this.form.man250SltGroup, 'man250SltGroup', '0', 'research');" id="man250GroupBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="Cal">
            <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="Cal">
          </button>
        </logic:notEmpty>
        <logic:notEmpty name="man250Form" property="usrLabel">
          <html:select property="man250SltUser" styleClass="wp200">
            <logic:iterate id="user" name="man250Form" property="usrLabel" type="LabelValueBean">
               <bean:define id="selected" value="" />
               <logic:equal name="man250Form" property="man250SltUser" value="<%=user.getValue() %>">
                 <bean:define id="selected" value="selected" />
               </logic:equal>
               <option class="<logic:equal name="user" property="usrUkoFlg" value="1">mukoUser-color</logic:equal>" value="<bean:write name="user" property="value"/>" <bean:write name="selected"/>><bean:write name="user" property="label"/></option>
            </logic:iterate>
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w15 no_w">
        <gsmsg:write key="cmn.keyword" />
      </th>
      <td class="w35">
        <div>
          <html:text name="man250Form" maxlength="50" property="man250KeyWord" styleClass="w100" />
        </div>
        <div>
          <span class="verAlignMid">
            <html:radio name="man250Form" property="man250KeyWordKbn" value="0" styleId="keyKbn_01" />
            <label for="keyKbn_01"><gsmsg:write key="cmn.contains.all.and" /></label>
            <html:radio name="man250Form" styleClass="ml10" property="man250KeyWordKbn" value="1" styleId="keyKbn_02" />
            <label for="keyKbn_02"><gsmsg:write key="cmn.orcondition" /></label>
          </span>
        </div>
      </td>
      <th class="w15 no_w">
        <gsmsg:write key="cmn.search2" />
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <html:multibox styleId="search_target_01" name="man250Form" property="man250SearchTarget" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.SEARCH_TARGET_FUNC) %>" />
          <label for="search_target_01"><gsmsg:write key="main.scr.feature.name" /></label>
          <html:multibox styleId="search_target_02" styleClass="ml10" name="man250Form" property="man250SearchTarget" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.SEARCH_TARGET_OPERATION) %>" />
          <label for="search_target_02"><gsmsg:write key="cmn.operations" /></label>
          <html:multibox styleId="search_target_03" styleClass="ml10" name="man250Form" property="man250SearchTarget" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.SEARCH_TARGET_CONTENT) %>" />
          <label for="search_target_03"><gsmsg:write key="cmn.content" /></label>
          <html:multibox styleId="search_target_04" styleClass="ml10" name="man250Form" property="man250SearchTarget" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.SEARCH_TARGET_IPADDRESS) %>" />
          <label for="search_target_04"><gsmsg:write key="cmn.ipaddress" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w15 no_w">
        <gsmsg:write key="cmn.sort.order" />
      </th>
      <td class="w85" colspan="3">
        <div class="verAlignMid">
          <span class="fw_b"><gsmsg:write key="cmn.first.key" /></span>
          <html:select property="man250SortKey1" styleClass="wp200 ml5">
            <html:optionsCollection name="man250Form" property="sortLabel" value="value" label="label" />
          </html:select>
          <html:radio name="man250Form" styleClass="ml10" property="man250OrderKey1" styleId="sort1_up" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" />
          <label for="sort1_up"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="man250Form" styleClass="ml10" property="man250OrderKey1" styleId="sort1_dw" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" />
          <label for="sort1_dw"><gsmsg:write key="cmn.order.desc" /></label>
          <span class="fw_b ml20"><gsmsg:write key="cmn.second.key" /></span>
          <html:select property="man250SortKey2" styleClass="wp200 ml5">
            <html:optionsCollection name="man250Form" property="sortLabel" value="value" label="label" />
          </html:select>
          <html:radio name="man250Form" styleClass="ml10" property="man250OrderKey2" styleId="sort2_up" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" />
          <label for="sort2_up"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="man250Form" styleClass="ml10" property="man250OrderKey2" styleId="sort2_dw" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" />
          <label for="sort2_dw"><gsmsg:write key="cmn.order.desc" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="txt_c">
    <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onclick="buttonPush('man250search');">
      <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>
  <div class="txt_r">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onclick="buttonPush('man250export');">
      <img src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.export" />
    </button>
  </div>
  <logic:notEmpty name="man250Form" property="man250DspList">
    <div class="paging mt10">
      <bean:size id="count1" name="man250Form" property="man250PageLabel" scope="request" />
      <logic:greaterThan name="count1" value="1">
      <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_left');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
        <i class="icon-paging_left"></i>
      </button>
      <logic:notEmpty name="man250Form" property="man250PageLabel">
        <html:select property="man250SltPage1" onchange="changePage1();">
          <html:optionsCollection name="man250Form" property="man250PageLabel" value="value" label="label" />
        </html:select>
      </logic:notEmpty>
      <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_right');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
        <i class="icon-paging_right"></i>
      </button>
      </logic:greaterThan>
    </div>
    <table class="table-top w100">
      <tr>
        <th class="w10 no_w cursor_p table_header-evt js_table_header-evt">
          <a href="#!" onClick="clickSortTitle(<%= sortLevel %>);"><gsmsg:write key="cmn.loglevel" /></a>
        </th>
        <th class="w10 no_w cursor_p table_header-evt js_table_header-evt">
          <a href="#!" onClick="clickSortTitle(<%= sortDate %>);"><gsmsg:write key="man.run.time" /></a>
        </th>
        <th class="w10 no_w cursor_p table_header-evt js_table_header-evt">
          <a href="#!" onClick="clickSortTitle(<%= sortPulugin %>);"><gsmsg:write key="cmn.plugin" /></a>
        </th>
        <th class="w10 no_w cursor_p table_header-evt js_table_header-evt">
          <a href="#!" onClick="clickSortTitle(<%= sortUsrName %>);"><gsmsg:write key="man.run.user" /></a>
        </th>
        <th class="w10 no_w cursor_p table_header-evt js_table_header-evt">
          <a href="#!" onClick="clickSortTitle(<%= sortPgName %>);"><gsmsg:write key="main.scr.feature.name" /></a>
        </th>
        <th class="w15 no_w cursor_p table_header-evt js_table_header-evt">
          <a href="#!" onClick="clickSortTitle(<%= sortOpCode %>);"><gsmsg:write key="cmn.operations" /></a>
        </th>
        <th class="w25 no_w cursor_p table_header-evt js_table_header-evt">
          <a href="#!" onClick="clickSortTitle(<%= sortValue %>);"><gsmsg:write key="cmn.content" /></a>
        </th>
        <th class="w10 no_w cursor_p table_header-evt js_table_header-evt">
          <a href="#!" onClick="clickSortTitle(<%= sortLogIp %>);"><gsmsg:write key="cmn.ipaddress" /></a>
        </th>
      </tr>
      <logic:iterate id="dspMdl" name="man250Form" property="man250DspList" indexId="idx">
        <tr>
          <td class="txt_l no_w">
            <logic:equal name="dspMdl" property="logLevel" value="<%= logLevelError %>">
              <img class="btn_classicImg-display" src="../main/images/classic/icon_log_error.gif" alt="<gsmsg:write key="man.error" />"><img class="btn_originalImg-display" src="../main/images/original/icon_error.png" alt="<gsmsg:write key="man.error" />">
            </logic:equal>
            <logic:equal name="dspMdl" property="logLevel" value="<%= logLevelWarn %>">
              <img class="btn_classicImg-display" src="../main/images/classic/icon_log_warn.gif" alt="<gsmsg:write key="cmn.warning" />"><img class="btn_originalImg-display" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
            </logic:equal>
            <logic:equal name="dspMdl" property="logLevel" value="<%= logLevelInfo %>">
              <img class="btn_classicImg-display" src="../main/images/classic/icon_log_info.gif" alt="<gsmsg:write key="main.man240.2" />"><img class="btn_originalImg-display" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="main.man240.2" />">
            </logic:equal>
            <logic:equal name="dspMdl" property="logLevel" value="<%= logLevelTrace %>">
              <img class="btn_classicImg-display" src="../main/images/classic/icon_log_trace.gif" alt="<gsmsg:write key="main.man240.3" />"><img class="btn_originalImg-display" src="../common/images/original/icon_info.png" alt="<gsmsg:write key="main.man240.3" />">
            </logic:equal>
            <bean:write name="dspMdl" property="logLevel" />
          </td>
          <td class="txt_c no_w">
            <bean:write name="dspMdl" property="logDate" />
          </td>
          <td class="txt_l no_w">
            <bean:write name="dspMdl" property="pluginName" />
          </td>
          <td class="txt_l no_w">
            <span class = "<logic:equal name="dspMdl" property="usrUkoFlg" value="1">mukoUser</logic:equal>">
              <bean:write name="dspMdl" property="usrNameSei" />&nbsp;<bean:write name="dspMdl" property="usrNameMei" />
            </span>
          </td>
          <td class="txt_l">
            <bean:write name="dspMdl" property="pgName" filter="false" />
          </td>
          <td class="txt_l no_w">
            <bean:write name="dspMdl" property="opCode"  />
          </td>
          <td class="txt_l">
            <bean:write name="dspMdl" property="value" filter="false"  />
          </td>
          <td class="txt_l no_w">
            <bean:write name="dspMdl" property="logIp"  />
          </td>
        </tr>
      </logic:iterate>
    </table>
    <div class="paging mt10">
      <bean:size id="count2" name="man250Form" property="man250PageLabel" scope="request" />
      <logic:greaterThan name="count2" value="1">
      <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_left');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
        <i class="icon-paging_left"></i>
      </button>
      <logic:notEmpty name="man250Form" property="man250PageLabel">
        <html:select property="man250SltPage2" onchange="changePage2();" styleClass="text_i">
          <html:optionsCollection name="man250Form" property="man250PageLabel" value="value" label="label" />
        </html:select>
      </logic:notEmpty>
      <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_right');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
        <i class="icon-paging_right"></i>
      </button>
      </logic:greaterThan>
    </div>
  </logic:notEmpty>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>