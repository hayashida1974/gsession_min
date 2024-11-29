<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<% int tmodeAll = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_ALL; %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="Content-Script-Type" content="text/javascript">
  <script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../ringi/js/rng130.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../ringi/js/csvUtil.js?<%= GSConst.VERSION_PARAM %>"></script>
  
  <gsjsmsg:js filename="gsjsmsg.js"/>
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
  <link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../ringi/css/ringi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <title>GROUPSESSION <gsmsg:write key="rng.rng130.09" /></title>
</head>

<body onload="controlInput();" onunload="windowClose();">

<html:form styleId="rmgForm" action="ringi/rng130">
<input type="hidden" name="CMD" value="search">
<input type="hidden" name="sltApplYearFr" value="">
<input type="hidden" name="sltApplMonthFr" value="">
<input type="hidden" name="sltApplDayFr" value="">
<input type="hidden" name="sltApplYearTo" value="">
<input type="hidden" name="sltApplMonthTo" value="">
<input type="hidden" name="sltApplDayTo" value="">
<input type="hidden" name="sltLastManageYearFr" value="">
<input type="hidden" name="sltLastManageMonthFr" value="">
<input type="hidden" name="sltLastManageDayFr" value="">
<input type="hidden" name="sltLastManageYearTo" value="">
<input type="hidden" name="sltLastManageMonthTo" value="">
<input type="hidden" name="sltLastManageDayTo" value="">

<html:hidden property="rngSid" />
<input type="hidden" name="rngCmdMode" value="0">
<input type="hidden" name="rngApprMode" value="0">
<html:hidden property="rngProcMode" />
<html:hidden property="rngTemplateMode" />
<html:hidden property="rng010orderKey" />
<html:hidden property="rng010sortKey" />
<html:hidden property="rng010pageTop" />
<html:hidden property="rng010pageBottom" />
<html:hidden property="rng010SelectCategoryUser" />
<html:hidden property="rng010SelectCategory" />
<html:hidden property="svRngViewAccount" />
<html:hidden property="svRng130Category" />
<html:hidden property="svRngKeyword" />
<html:hidden property="svRng130Type" />
<html:hidden property="svGroupSid" />
<html:hidden property="svUserSid" />
<html:hidden property="svRng130keyKbn" />
<html:hidden property="svRng130searchSubject1" />
<html:hidden property="svRng130searchSubject2" />
<html:hidden property="svRng130searchSubject3" />
<html:hidden property="svSortKey1" />
<html:hidden property="svRng130orderKey1" />
<html:hidden property="svSortKey2" />
<html:hidden property="svRng130orderKey2" />
<html:hidden property="svApplYearFr" />
<html:hidden property="svApplMonthFr" />
<html:hidden property="svApplDayFr" />
<html:hidden property="svApplYearTo" />
<html:hidden property="svApplMonthTo" />
<html:hidden property="svApplDayTo" />
<html:hidden property="svRng130Status" />
<html:hidden property="svLastManageYearFr" />
<html:hidden property="svLastManageMonthFr" />
<html:hidden property="svLastManageDayFr" />
<html:hidden property="svLastManageYearTo" />
<html:hidden property="svLastManageMonthTo" />
<html:hidden property="svLastManageDayTo" />
<html:hidden property="rng130outPutDirHash" />
<html:hidden property="rng130searchFlg" />
<html:hidden property="rng130rngNowDate" />
<input type="hidden" name="yearRangeMinFr" value="<bean:write name="rng130Form" property="rng130MinYear" />" />
<input type="hidden" name="yearRangeMaxFr" value="0" />
<input type="hidden" name="yearRangeMinTo" value="<bean:write name="rng130Form" property="rng130MinYear" />" />
<input type="hidden" name="yearRangeMaxTo" value="0" />
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<script>
 msglist_rng130 = new Array();
 msglist_rng130['cmn.number'] = '<gsmsg:write key="cmn.number"/>';
 msglist_rng130['cmn.pdf'] = '<gsmsg:write key="cmn.pdf"/>';
</script>

<!-- body -->
<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../ringi/images/classic/header_ringi.png" alt="<gsmsg:write key="rng.62" />">
      <img class="header_pluginImg" src="../ringi/images/original/header_ringi.png" alt="<gsmsg:write key="rng.62" />">
    </li>
    <li>
      <gsmsg:write key="rng.62" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.advanced.search" />
    </li>
    <li>
    <div>
      <button  type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backRngList')">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <div id="pdfErrorArea" class="display_n"><p id="pdfErrorText" class="textError"></p></div>
  <% String sort_title = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SORT_TITLE); %>
  <% String sort_name = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SORT_NAME); %>
  <% String sort_status = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SORT_KEKKA); %>
  <% String sort_date = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SORT_DATE); %>
  <% String sort_jyusin = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SORT_JYUSIN); %>
  <% String sort_touroku = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SORT_TOUROKU); %>
  <% String sort_kakunin = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SORT_KAKUNIN); %>
  <!-- 検索 -->
  <table class="table-left w100">
    <tr>
      <td colspan="4" class="w100 table_title-color">
        <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="mb5 table_header_icon_search classic-display">
        <span class="table_title-color"><gsmsg:write key="cmn.advanced.search" /></span>
      </td>
    </tr>
    <tr>
      <th class="w10 txt_c">
        <gsmsg:write key="cmn.account" />
      </th>
      <td class="w40">
        <html:select  property="rng010ViewAccount" styleId="account_comb_box">
          <logic:notEmpty name="rng130Form" property="rng010AccountList">
            <logic:iterate id="accountMdl" name="rng130Form" property="rng010AccountList">
              <bean:define id="accoutVal" name="accountMdl" property="accountSid" type="java.lang.Integer" />
              <bean:define id="optClass" value="" />
              <logic:equal name="accountMdl" property="usrUkoFlg" value="1">
                <bean:define id="optClass" value="mukoUserOption" />
              </logic:equal>
              <option value="<%= String.valueOf(accoutVal) %>" class="<bean:write name="optClass" />"  <logic:equal name="rng130Form" property="rng010ViewAccount" value="<%= String.valueOf(accoutVal) %>">selected</logic:equal>><bean:write name="accountMdl" property="accountName" /></option>
            </logic:iterate>
          </logic:notEmpty>
        </html:select>
      </td>
      <th class="w10 no_w txt_c">
       <gsmsg:write key="cmn.category" />
      </th>
      <td class="w40">
        <html:select property="rng010SearchCategory">
          <html:optionsCollection property="rng010CategoryList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w10 no_w txt_c">
        <gsmsg:write key="cmn.type" />
      </th>
      <td class="w40" >
        <div id="sessionSid" class="display_n"><bean:write name="rng130Form" property="rng130sessionSid" /></div>
        <span class="verAlignMid">
          <html:radio name="rng130Form" property="rng130Type" styleId="type1" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_MODE_JYUSIN) %>" onclick="changeProcType();" />
          <label for="type1"><gsmsg:write key="cmn.receive" /></label>
          <html:radio styleClass="ml10" name="rng130Form" property="rng130Type" styleId="type2" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_MODE_SINSEI) %>" onclick="changeProcType();" />
          <label for="type2"><gsmsg:write key="rng.application.ongoing" /></label>
          <html:radio styleClass="ml10" name="rng130Form" property="rng130Type" styleId="type3" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_MODE_KANRYO) %>" onclick="changeProcType();" />
          <label for="type3"><gsmsg:write key="cmn.complete" /></label>
          <html:radio styleClass="ml10" name="rng130Form" property="rng130Type" styleId="type4" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_MODE_SOUKOU) %>" onclick="changeProcType();" />
          <label id="type4mes" for="type4"><gsmsg:write key="cmn.draft" /></label>
          <html:radio styleClass="ml10" name="rng130Form" property="rng130Type" styleId="type5" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_MODE_KOETU) %>" onclick="changeProcType();" />
          <label for="type5"><gsmsg:write key="rng.109" /></label>
        </span>
      </td>
      <th class="w10 no_w txt_c">
       <gsmsg:write key="cmn.status" />
      </th>
      <td class="w40">
        <div class="verAlignMid">
          <html:radio name="rng130Form" property="rng130Status" styleId="status0" value="-1"  />
          <label for="status0"><gsmsg:write key="cmn.all" /></label>
          <html:radio styleClass="ml10" name="rng130Form" property="rng130Status" styleId="status1" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_SETTLED) %>"  />
          <label for="status1"><gsmsg:write key="rng.29" /></label>
          <html:radio styleClass="ml10" name="rng130Form" property="rng130Status" styleId="status2" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_REJECT) %>" />
          <label for="status2"><gsmsg:write key="rng.22" /></label>
          <html:radio styleClass="ml10" name="rng130Form" property="rng130Status" styleId="status3" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_DONE) %>"  />
          <label for="status3"><gsmsg:write key="rng.rng030.06" /></label>
          <html:radio styleClass="ml10" name="rng130Form" property="rng130Status" styleId="status4" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_TORISAGE) %>" />
          <label for="status4"><gsmsg:write key="rng.rng030.15" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w10 no_w txt_c">
        <gsmsg:write key="cmn.keyword" />
      </th>
      <td class="w40">
        <html:text styleClass="wp320 js_searchArea" name="rng130Form" property="rngKeyword" maxlength="100" />
        <div>
          <span class="verAlignMid">
            <html:radio name="rng130Form" property="rng130keyKbn" value="<%= String.valueOf( jp.groupsession.v2.rng.RngConst.RNG_SEARCHTYPE_AND) %>" styleId="rng130keyKbn_01" />
            <label for="rng130keyKbn_01"><gsmsg:write key="cmn.contains.all.and" /></label>
            <html:radio styleClass="ml10" name="rng130Form" property="rng130keyKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SEARCHTYPE_OR) %>" styleId="rng130keyKbn_02" />
            <label for="rng130keyKbn_02"><gsmsg:write key="cmn.orcondition" /></label>
          </span>
        </div>
      </td>
      <th class="w10 no_w txt_c">
        <gsmsg:write key="cmn.search2" />
      </th>
      <td class="w40">
        <div class="verAlignMid">
          <html:checkbox name="rng130Form" property="rng130searchSubject1" value="1" styleId="search_scope_01" />
          <label for="search_scope_01"><gsmsg:write key="cmn.subject" /></label>
          <html:checkbox styleClass="ml10" name="rng130Form" property="rng130searchSubject2" value="1" styleId="search_scope_02" />
          <label for="search_scope_02"><gsmsg:write key="cmn.content" /></label>
        </div>
        <div class="verAlignMid ml5" id="search_rngid">
          <html:checkbox name="rng130Form" property="rng130searchSubject3" value="1" styleId="search_scope_03" />
          <label for="search_scope_03"><gsmsg:write key="rng.rng180.04" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w10 no_w txt_c">
        <gsmsg:write key="rng.47" />
      </th>
      <td class="w90" colspan="3">
        <html:select property="sltGroupSid"  styleId="sltGroupSid" onchange="changeGroupCombo();">
          <html:optionsCollection name="rng130Form" property="rng130groupList" value="value" label="label" />
        </html:select>
        <button class="iconBtn-border mr10" type="button" id="rng130GroupBtn" value="&nbsp;&nbsp;" onClick="setDateParam();openGroupWindow(this.form.sltGroupSid, 'sltGroupSid', '0');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
        </button>
        <html:select property="sltUserSid"  styleId="sltUserSid" >
          <logic:iterate id="user" name="rng130Form" property="rng130userList"  >
            <bean:define id="userValue" name="user" property="value" />
            <bean:define id="mukoUserClass" value="" />
            <logic:equal name="user" property="usrUkoFlg" value="1">
              <bean:define id="mukoUserClass" value="mukoUserOption" />
            </logic:equal>
            <html:option styleClass="<%= mukoUserClass %>" value="<%= String.valueOf(userValue) %>">
              <bean:write name="user" property="label" />
            </html:option>
          </logic:iterate>
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w10 no_w txt_c">
        <gsmsg:write key="rng.application.date" />
      </th>
      <td class="w90" colspan="3">
        <div class="verAlignMid mr5">
          <html:text name="rng130Form" property="rng130ApplDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDateaf" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
        </div>
        <span class="mr5"><gsmsg:write key="tcd.153" /></span>
        <div class="verAlignMid mr5">
          <html:text name="rng130Form" property="rng130ApplDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateat" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w10 no_w txt_c">
        <gsmsg:write key="rng.36" />
      </th>
      <td class="w90" colspan="3">
        <div class="verAlignMid mr5">
          <html:text name="rng130Form" property="rng130LastManageDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatelf" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
        </div>
        <span class="mr5"><gsmsg:write key="tcd.153" /></span>
        <div class="verAlignMid">
          <html:text name="rng130Form" property="rng130LastManageDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDatelt" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
        </div>
      </td>
    </tr>
      <tr>
        <th class="w10 no_w txt_c" >
          <gsmsg:write key="cmn.sort.order" />
        </th>
        <td class="w90" colspan="3">
          <div class="display_inline">
            <gsmsg:write key="cmn.first.key" />&nbsp;
            <html:select property="sltSortKey1" >
              <html:optionsCollection name="rng130Form" property="sortKeyList" value="value" label="label" />
            </html:select>
          </div>
          <div class="display_inline txt_b">
           <span class="verAlignMid mr20">
            <html:radio name="rng130Form" property="rng130orderKey1" styleId="sort1_up" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_ORDER_ASC) %>" />
            <label for="sort1_up"><gsmsg:write key="cmn.order.asc" /></label>
            <html:radio styleClass="ml10" name="rng130Form" property="rng130orderKey1" styleId="sort1_dw" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_ORDER_DESC) %>" />
            <label for="sort1_dw"><gsmsg:write key="cmn.order.desc" /></label>
          </span>
          </div>
          <div class="display_inline">
            <gsmsg:write key="cmn.second.key" />&nbsp;
            <html:select property="sltSortKey2" >
              <html:optionsCollection name="rng130Form" property="sortKeyList" value="value" label="label" />
            </html:select>
          </div>
          <div class="display_inline txt_b">
           <span class="verAlignMid mr20">
            <html:radio name="rng130Form" property="rng130orderKey2" styleId="sort2_up" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_ORDER_ASC) %>" />
            <label for="sort2_up"><gsmsg:write key="cmn.order.asc" /></label>
            <html:radio styleClass="ml10" name="rng130Form" property="rng130orderKey2" styleId="sort2_dw" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_ORDER_DESC) %>" />
            <label for="sort2_dw"><gsmsg:write key="cmn.order.desc" /></label>
          </span>
          </div>
        </td>
      </tr>
  </table>
  <div>
    <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onClick="searchpush();">
      <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>

  <logic:notEmpty name="rng130Form" property="rng130rngDataList">
    <!-- 出力ボタン -->
    <div class="txt_r">
      <logic:notEqual name="rng130Form" property="svRng130Type" value="3">
        <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.pdf" />" onClick="return pdfOutputButton();">
          <img src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />" class="btn_classicImg-display">
          <img src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />" class="btn_originalImg-display">
          <gsmsg:write key="cmn.pdf" />
        </button>
      </logic:notEqual>
        <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.export" />" onClick="rngCsvExportButton('rng130', 'rng130export');">
          <img src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_classicImg-display">
          <img src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_originalImg-display">
          <gsmsg:write key="cmn.export" />
        </button>
    </div>
    <!-- ページング -->
    <div class="txt_r mt10">
      <logic:notEmpty name="rng130Form" property="pageList">
        <span class="paging">
           <button type="button" class="webIconBtn" onClick="setDateParam();return buttonPush('prevPage');">
             <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
             <i class="icon-paging_left"></i>
           </button>
            <html:select property="rng130pageTop" onchange="selectPage(0);" styleClass="paging_combo">
              <html:optionsCollection name="rng130Form" property="pageList" value="value" label="label" />
            </html:select>
           <button type="button" class="webIconBtn" onClick="setDateParam();return buttonPush('nextPage');">
             <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
             <i class="icon-paging_right"></i>
           </button>
        </span>
      </logic:notEmpty>
    </div>
    <!-- 検索結果一覧 -->
      <table class="table-top">
        <bean:define id="rngType" name="rng130Form" property="rng130Type" type="Integer"/>
        <bean:define id="procMode" name="rng130Form" property="svRng130Type" type="Integer"/>
        <%
          int intRngType = rngType.intValue();
          int sMode = procMode.intValue();
          int mode0 = jp.groupsession.v2.rng.RngConst.RNG_MODE_JYUSIN;
          int mode1 = jp.groupsession.v2.rng.RngConst.RNG_MODE_SINSEI;
          int mode2 = jp.groupsession.v2.rng.RngConst.RNG_MODE_KANRYO;
          int mode3 = jp.groupsession.v2.rng.RngConst.RNG_MODE_SOUKOU;
          int mode4 = jp.groupsession.v2.rng.RngConst.RNG_MODE_KOETU;
          String rngstatus_settlet = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_SETTLED);
          String rngstatus_reject = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_REJECT);
          String rngstatus_done = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_DONE);
          String rngstatus_torisage = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_TORISAGE);
        %>

        <tr>
          <% if (sMode == mode0) { %>
            <th class="w55 table_title-color cursor_p">
              <a href="#" onClick="clickSortTitle(<%= sort_title %>);">
                  <gsmsg:write key="cmn.title" />
              </a>
            </th>
            <% if (intRngType != mode3) { %>
              <th class="w15 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_name %>);">
                    <gsmsg:write key="rng.47" />
                </a>
              </th>
              <th class="w15 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_date %>);">
                    <gsmsg:write key="rng.application.date" />
                </a>
              </th>
            <% } else { %>
              <th class="w15 table_title-color cursor_p">
                  <gsmsg:write key="rng.47" />
              </th>
              <th class="w15 table_title-color cursor_p">
                  <gsmsg:write key="rng.application.date" />
              </th>
            <% } %>
            <% if (intRngType == mode0) { %>
              <th class="w15 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_jyusin %>);">
                    <gsmsg:write key="cmn.received.date" />
                </a>
              </th>
            <% } else { %>
              <th class="w15 table_title-color cursor_p">
                  <gsmsg:write key="cmn.received.date" />
              </th>
            <% } %>
          <% } else if (sMode == mode1) { %>
            <th class="w55 table_title-color cursor_p">
              <a href="#" onClick="clickSortTitle(<%= sort_title %>);">
                <gsmsg:write key="cmn.title" />
              </a>
            </th>
            <% if (intRngType != mode3) { %>
              <th class="w15 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_name %>);">
                  <gsmsg:write key="rng.47" />
                </a>
              </th>
              <th class="w15 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_date %>);">
                  <gsmsg:write key="rng.application.date" />
                </a>
              </th>
            <% } else { %>
              <th class="w15 table_title-color cursor_p">
                <gsmsg:write key="rng.47" />
              </th>
              <th class="w15 table_title-color cursor_p">
                <gsmsg:write key="rng.application.date" />
              </th>
            <% } %>
            <% if (intRngType == mode1 || intRngType == mode2) { %>
              <th class="w15 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_kakunin %>);">
                    <gsmsg:write key="rng.105" />
                </a>
              </th>
            <% } else { %>
              <th class="w15 table_title-color cursor_p">
                  <gsmsg:write key="rng.105" />
              </th>
            <% } %>
          <% } else if (sMode == mode2) { %>
              <th class="w45 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_title %>);">
                  <gsmsg:write key="cmn.title" />
                </a>
              </th>
              <th class="w10 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_status %>);">
                  <gsmsg:write key="cmn.status" />
                </a>
              </th>
            <% if (intRngType != mode3) { %>
              <th class="w15 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_name %>);">
                  <gsmsg:write key="rng.47" />
                </a>
              </th>
              <th class="w15 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_date %>);">
                  <gsmsg:write key="rng.application.date" />
                </a>
              </th>
            <% } else { %>
              <th class="w15 table_title-color cursor_p">
                <gsmsg:write key="rng.47" />
              </th>
              <th class="w15 table_title-color cursor_p">
                <gsmsg:write key="rng.application.date" />
              </th>
            <% } %>
            <% if (intRngType == mode1 || intRngType == mode2) { %>
              <th class="w15 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_kakunin %>);">
                  <gsmsg:write key="rng.105" />
                </a>
              </th>
            <% } else { %>
              <th class="w15 table_title-color cursor_p">
                <gsmsg:write key="rng.105" />
              </th>
            <% } %>
          <% } else if (sMode == mode3) { %>
              <th class="w85 table_title-color cursor_p">
              <a href="#" onClick="clickSortTitle(<%= sort_title %>);">
                <gsmsg:write key="cmn.title" />
              </a>
            </th>
            <% if (intRngType == mode3) { %>
              <th class="w15 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_touroku %>);">
                  <gsmsg:write key="rng.37" />
                </a>
              </th class="w15 table_title-color cursor_p">
            <% } else { %>
              <th class="w15 table_title-color cursor_p">
                <gsmsg:write key="rng.37" />
              </th>
            <% } %>
          <% } else if (sMode == mode4) { %>
              <th class="w60 table_title-color cursor_p">
              <a href="#" onClick="clickSortTitle(<%= sort_title %>);">
                <gsmsg:write key="cmn.title" />
              </a>
            </th>
            <% if (intRngType != mode3) { %>
              <th class="w20 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_name %>);">
                  <gsmsg:write key="rng.47" />
                </a>
              </th>
              <th class="w20 table_title-color cursor_p">
                <a href="#" onClick="clickSortTitle(<%= sort_date %>);">
                  <gsmsg:write key="rng.application.date" />
                </a>
              </th>
            <% } else { %>
              <th class="w20 table_title-color cursor_p">
                <gsmsg:write key="rng.47" />
              </th>
              <th class="w20 table_title-color cursor_p">
                <gsmsg:write key="rng.application.date" />
              </th>
            <% } %>
          <% } %>
        </tr>
        <logic:iterate id="rngData" name="rng130Form" property="rng130rngDataList" indexId="idx" scope="request">
          <tr class="js_listHover  cursor_p">
            <% String apprUserClass = "";%>
            <logic:equal name="rngData" property="apprUserDelFlg" value="true">
              <% apprUserClass = "delete_border"; %>
            </logic:equal>
            <logic:notEqual name="rngData" property="apprUserDelFlg" value="true">
              <logic:equal name="rngData" property="usrUkoFlg" value="1">
                <% apprUserClass = "mukoUser"; %>
              </logic:equal>
            </logic:notEqual>
            <input type="hidden" name="rngData_sid" value="<bean:write name="rngData" property="rngSid" />">
            <input type="hidden" name="rng_mode" value="<%= sMode %>">
            <% if (sMode == mode0) { %>
              <% String apprMode = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_APPRMODE_APPR); %>
              <logic:equal name="rngData" property="rncType" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_RNCTYPE_APPL) %>">
                <% apprMode = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_APPRMODE_APPL); %>
              </logic:equal>
              <input type="hidden" name="rngData_ApprMode" value="<%= apprMode %>">
              <td class="txt_l cl_linkDef js_listClick">
                <bean:write name="rngData" property="rngTitle" />
              </td>
              <td class="<%= apprUserClass %> txt_l js_listClick">
                <bean:write name="rngData" property="apprUser" />
              </td>
              <td class="txt_c js_listClick">
                <bean:write name="rngData" property="strRngAppldate" />
              </td>
              <td class="txt_c js_listClick">
                <bean:write name="rngData" property="strRcvDate" />
              </td>
            <% } else if (sMode == mode1) { %>
              <td class="txt_l cl_linkDef js_listClick">
                <bean:write name="rngData" property="rngTitle" />
              </td>
              <td class="<%= apprUserClass %> txt_l js_listClick">
                <bean:write name="rngData" property="apprUser" />
              </td>
              <td class="txt_c js_listClick">
                <bean:write name="rngData" property="strRngAppldate" />
              </td>
              <td class="txt_c js_listClick">
                <bean:write name="rngData" property="strLastManageDate" />
              </td>
            <% } else if (sMode == mode2) { %>
              <td class="txt_l cl_linkDef js_listClick w45">
                <bean:write name="rngData" property="rngTitle" />
              </td>
              <% String rngStatus = "&nbsp;"; %>
              <logic:equal name="rngData" property="rngStatus" value="<%= rngstatus_settlet %>">
                <%
                jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
                String kessai = gsMsg.getMessage(request, "rng.64");
                String kyakka = gsMsg.getMessage(request, "rng.65");
                %>
                <% rngStatus = kessai; %>
              </logic:equal>
              <logic:equal name="rngData" property="rngStatus" value="<%= rngstatus_reject %>">
                <%
                jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
                String kessai = gsMsg.getMessage(request, "rng.64");
                String kyakka = gsMsg.getMessage(request, "rng.65");
                rngStatus = kyakka;
                %>
              </logic:equal>
              <logic:equal name="rngData" property="rngStatus" value="<%= rngstatus_done %>">
                <%
                  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
                  String kessai = gsMsg.getMessage(request, "rng.64");
                  String kyakka = gsMsg.getMessage(request, "rng.65");
                  String done   = gsMsg.getMessage(request, "rng.rng030.06");
                %>
                <% rngStatus = done; %>
              </logic:equal>
              <logic:equal name="rngData" property="rngStatus" value="<%= rngstatus_torisage %>">
                <%
                jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
                String torisage = gsMsg.getMessage(request, "rng.66");
                %>
                <% rngStatus = torisage; %>
              </logic:equal>
              <td class="txt_c js_listClick w10 no_w">
                <%= rngStatus %>
              </td>
              <td class="<%= apprUserClass %> txt_l js_listClick">
                <bean:write name="rngData" property="apprUser" />
              </td>
              <td class="txt_c js_listClick">
                <bean:write name="rngData" property="strRngAppldate" />
              </td>
              <td class="txt_c js_listClick">
                <bean:write name="rngData" property="strLastManageDate" />
              </td>
            <% } else if (sMode == mode3) { %>
              <td class="txt_l cl_linkDef js_listClick w80">
                  <bean:write name="rngData" property="rngTitle" />
              </td>
              <td class="txt_c js_listClick">
                <bean:write name="rngData" property="strMakeDate" />
              </td>
            <% } else if (sMode == mode4) { %>
              <% String apprMode = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_APPRMODE_APPR); %>
              <logic:equal name="rngData" property="rncType" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_RNCTYPE_APPL) %>">
                <% apprMode = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_APPRMODE_APPL); %>
              </logic:equal>
              <input type="hidden" name="rngData_ApprMode" value="<%= apprMode %>">
              <td class="txt_l cl_linkDef js_listClick">
                  <bean:write name="rngData" property="rngTitle" />
              </td>
              <td class="<%= apprUserClass %> txt_l js_listClick">
                <bean:write name="rngData" property="apprUser" />
              </td>
              <td class="txt_c js_listClick">
                <bean:write name="rngData" property="strRngAppldate" />
              </td>
            <% } %>
          </tr>
        </logic:iterate>
      </table>
      <!-- ページング -->
      <div class="txt_r mt10">
        <logic:notEmpty name="rng130Form" property="pageList">
          <span class="paging">
             <button type="button" class="webIconBtn" onClick="setDateParam();return buttonPush('prevPage');">
               <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
               <i class="icon-paging_left"></i>
             </button>
              <html:select property="rng130pageBottom" onchange="selectPage(1);" styleClass="paging_combo">
                <html:optionsCollection name="rng130Form" property="pageList" value="value" label="label" />
              </html:select>
             <button type="button" class="webIconBtn" onClick="setDateParam();return buttonPush('nextPage');">
               <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
               <i class="icon-paging_right"></i>
             </button>
          </span>
        </logic:notEmpty>
      </div>
  </logic:notEmpty>
  <!--  PDF出力シーケンスポップアップ -->
        <div id="loading_pop" title="" class="display_n">
          <table class="w100 h100">
            <tr>
              <td id="pdfOutput" class="loading_area w100 txt_m">
                <p><gsmsg:write key="rng.rngPdfDlg.01"/></p>
                <p id="outputCount" class="txt_c">
                   <logic:greaterThan value="0" name="rng130Form" property="rng130pdfOutputMax">
                      <bean:write name="rng130Form" property="rng130pdfOutputCnt"/><gsmsg:write key="cmn.number"/>&nbsp;
                      /&nbsp;<bean:write name="rng130Form" property="rng130pdfOutputMax"/><gsmsg:write key="cmn.number"/>
                   </logic:greaterThan>
                </p>
                <p class="txt_c">
                  <button type="button" name="btn_usrimp" class="baseBtn" value="<gsmsg:write key="cmn.cancel"/>" onclick="pdfCancel();">
                    <gsmsg:write key="cmn.cancel"/>
                  </button>
                </p>
              </td>
              <td id="pdfZip" class="loading_area w100 txt_m display_n">
                <p><gsmsg:write key="rng.rngPdfDlg.02"/></p>
              </td>
              <td id="pdfDownload" class="loading_area w100 txt_m display_n">
                <p><gsmsg:write key="rng.rngPdfDlg.03"/></p>
                <p id="zipName"></p>
                <p class="txt_c">
                  <button type="button" name="btn_usrimp" class="baseBtn" value="<gsmsg:write key="cmn.download"/>" onclick="pdfDownload();">
                    <gsmsg:write key="cmn.download"/>
                  </button>&nbsp;&nbsp;
                  <button type="button" name="btn_usrimp" class="baseBtn" value="<gsmsg:write key="cmn.close"/>" onclick="pdfDialogClose(true);">
                    <gsmsg:write key="cmn.close"/>
                  </button>
                </p>
              </td>
              <td id="pdfError" class="loading_area w100 txt_m display_n">
                <p><gsmsg:write key="rng.rngPdfDlg.04"/></p>
                <p class="txt_c"><button type="button" name="btn_usrimp" class="btn_base0" value="<gsmsg:write key="cmn.close"/>" onclick="pdfDialogClose(false);"><gsmsg:write key="cmn.close"/></button></p>
              </td>
            </tr>
          </table>
        </div>
        <!-- CSVエクスポート 読み込み中ダイアログ -->
        <div id="loading_csv_pop" title="" class="display_n">
          <table class="w100 h100">
            <tr>
              <td class="txt_m txt_c">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
                <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
              </td>
            </tr>
          </table>
        </div>
</div>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>
</body>
</html:html>