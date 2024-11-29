<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
  <head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Content-Script-Type" content="text/javascript">
    <gsjsmsg:js filename="gsjsmsg.js"/>
    <script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
    <script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/selectionSearch.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script type="text/javascript" src="../ringi/js/rng070.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script type="text/javascript" src="../ringi/js/csvUtil.js?<%= GSConst.VERSION_PARAM %>"></script>
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
    <title>GROUPSESSION <gsmsg:write key="rng.62" /> <gsmsg:write key="rng.19" /></title>
  </head>
  <body>
    <html:form styleId="rngForm" action="ringi/rng070">
      <input type="hidden" name="CMD" value="">
      <%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
      <input type="hidden" name="rngSid" value="">
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

      <html:hidden property="backScreen" />
      <html:hidden property="rngAdminGroupSid" />
      <html:hidden property="rngAdminUserSid" />
      <html:hidden property="rngAdminKeyword" />
      <html:hidden property="rngAdminApplYearFr" />
      <html:hidden property="rngAdminApplMonthFr" />
      <html:hidden property="rngAdminApplDayFr" />
      <html:hidden property="rngAdminApplYearTo" />
      <html:hidden property="rngAdminApplMonthTo" />
      <html:hidden property="rngAdminApplDayTo" />
      <html:hidden property="rngAdminLastManageYearFr" />
      <html:hidden property="rngAdminLastManageMonthFr" />
      <html:hidden property="rngAdminLastManageDayFr" />
      <html:hidden property="rngAdminLastManageYearTo" />
      <html:hidden property="rngAdminLastManageMonthTo" />
      <html:hidden property="rngAdminLastManageDayTo" />
      <html:hidden property="rngAdminSortKey" />
      <html:hidden property="rngAdminOrderKey" />
      <html:hidden property="rngAdminSearchFlg" />
      <html:hidden property="rng070rngNowDate" />
      <html:hidden property="rng070outPutDirHash" />
      <input type="hidden" name="yearRangeMinFr" value="<bean:write name="rng070Form" property="rng070MinYear" />" />
      <input type="hidden" name="yearRangeMaxFr" value="0" />
      <input type="hidden" name="yearRangeMinTo" value="<bean:write name="rng070Form" property="rng070MinYear" />" />
      <input type="hidden" name="yearRangeMaxTo" value="0" />
      <input type="hidden" name="rngApprMode" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_APPRMODE_COMPLETE) %>">
      <bean:define id="iorderKey" name="rng070Form" property="rngAdminSortKey" />
      <bean:define id="isortKbn" name="rng070Form" property="rngAdminOrderKey" />
      <script>
       msglist_rng070 = new Array();
       msglist_rng070['cmn.number'] = '<gsmsg:write key="cmn.number"/>';
       msglist_rng070['cmn.pdf'] = '<gsmsg:write key="cmn.pdf"/>';
      </script>
      <!-- BODY -->
      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
      <div class="kanriPageTitle mrl_auto">
        <ul>
          <li>
            <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
          </li>
          <li>
            <gsmsg:write key="cmn.admin.setting" />
          </li>
          <li class="pageTitle_subFont">
             <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.19" />
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="setDateParam();buttonPush('rng070del');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.delete" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('rng040');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.back" />
              </button>
            </div>
          </li>
        </ul>
      </div>
      <div class="wrapper mrl_auto">
        <logic:messagesPresent message="false">
          <html:errors/>
        </logic:messagesPresent>
        <div id="pdfErrorArea" class="display_n"><p id="pdfErrorText" class="textError"></p></div>
        <!-- 検索 -->
        <table class="table-left w100">
          <tr>
            <td colspan="4" class="w100 table_title-color ">
              <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="mb5 table_header_icon_search classic-display">
              <span class="table_title-color"><gsmsg:write key="rng.19" /></span>
            </td>
          </tr>
          <tr>
            <th class="w25">
              <gsmsg:write key="cmn.keyword" />
            </th>
            <td class="w75">
              <html:text name="rng070Form" property="rngInputKeyword" styleClass="wp400" maxlength="100" />
            </td>
          </tr>
          <tr>
            <th class="w25">
              <gsmsg:write key="cmn.group" />
            </th>
            <td class="w75">
              <html:select property="sltGroupSid" onchange="changeGroupCombo();">
                <html:optionsCollection name="rng070Form" property="rng070groupList" value="value" label="label" />
              </html:select>
              <button class="iconBtn-border" type="button" id="rng070groupList" value="&nbsp;&nbsp;" onClick="setDateParam();openGroupWindow(this.form.sltGroupSid, 'sltGroupSid', '0', '');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
              </button>
            </td>
          </tr>
          <tr>
            <th class="w25">
              <gsmsg:write key="rng.47" />
            </th>
            <td class="w75">
              <html:select property="sltUserSid">
                <logic:iterate id="user" name="rng070Form" property="rng070userList"  >
                  <bean:define id="userValue" name="user" property="value" />
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal name="user" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUserOption" /></logic:equal>
                  <html:option styleClass="<%=mukoUserClass %>" value="<%=String.valueOf(userValue) %>"><bean:write name="user" property="label" /></html:option>
                </logic:iterate>
              </html:select>
            </td>
          </tr>
          <tr>
            <th class="w25">
              <gsmsg:write key="rng.application.date" />
            </th>
            <td class="w75">
              <div class="verAlignMid mr5">
                <html:text name="rng070Form" property="rng070ApplDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDateaf" />
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              </div>
              <span class="mr5"><gsmsg:write key="tcd.153" /></span>
              <div class="verAlignMid">
                <html:text name="rng070Form" property="rng070ApplDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateat" />
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              </div>
            </td>
          </tr>
          <tr>
            <th class="w25">
              <gsmsg:write key="rng.105" />
            </th>
            <td class="w75">
              <div class="verAlignMid mr5">
                <html:text name="rng070Form" property="rng070LastManageDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatelf" />
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              </div>
              <span class="mr5"><gsmsg:write key="tcd.153" /></span>
              <div class="verAlignMid">
                <html:text name="rng070Form" property="rng070LastManageDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDatelt" />
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              </div>
            </td>
          </tr>
          <tr>
            <th class="w25">
              <gsmsg:write key="cmn.results" />
            </th>
            <td class="w75">
              <div class="verAlignMid">
                <html:radio name="rng070Form" property="rng070Kekka" styleId="status0" value="-1"  />
                <label for="status0"><gsmsg:write key="cmn.all" /></label>
                <html:radio styleClass="ml10" name="rng070Form" property="rng070Kekka" styleId="status1" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_SETTLED) %>"  />
                <label for="status1"><gsmsg:write key="rng.29" /></label>
                <html:radio styleClass="ml10" name="rng070Form" property="rng070Kekka" styleId="status2" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_REJECT) %>" />
                <label for="status2"><gsmsg:write key="rng.22" /></label>
                <html:radio styleClass="ml10" name="rng070Form" property="rng070Kekka" styleId="status3" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_DONE) %>"  />
                <label for="status3"><gsmsg:write key="rng.rng030.06" /></label>
                <html:radio styleClass="ml10" name="rng070Form" property="rng070Kekka" styleId="status4" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_TORISAGE) %>" />
                <label for="status4"><gsmsg:write key="rng.rng030.15" /></label>
              </div>
            </td>
          </tr>
        </table>
        <div>
          <button class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');">
            <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_classicImg-display">
            <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_originalImg-display">
            <gsmsg:write key="cmn.search" />
          </button>
        </div>
        <!-- 出力ボタン -->
        <div class="txt_r mb5">
          <logic:notEmpty name="rng070Form" property="rng070dataList">
            <button type="submit" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.pdf" />" onClick="return pdfOutputButton();">
              <img src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />" class="btn_classicImg-display">
              <img src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />" class="btn_originalImg-display">
              <gsmsg:write key="cmn.pdf" />
            </button>
            <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.export" />" onClick="rngCsvExportButton('rng070', 'export');">
              <img src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_classicImg-display">
              <img src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_originalImg-display">
              <gsmsg:write key="cmn.export" />
            </button>
          </logic:notEmpty>
        </div>
        <!-- 経路状態 -->
        <div class="txt_r keiro_status_ex">
          <img src="../ringi/images/classic/icon_keiro_mi.png" alt="<gsmsg:write key="rng.60" />" class="classic-display"><img src="../ringi/images/original/keiro.png" alt="<gsmsg:write key="rng.60" />" class="original-display"><gsmsg:write key="wml.215" /><gsmsg:write key="rng.60" />
          <img src="../ringi/images/classic/icon_keiro_ok.png" alt="<gsmsg:write key="rng.43" />" class="classic-display"><img src="../ringi/images/original/keiro_ok.png" alt="<gsmsg:write key="rng.43" />" class="original-display"><gsmsg:write key="wml.215" /><gsmsg:write key="rng.43" />
          <img src="../ringi/images/classic/icon_keiro_now.png" alt="<gsmsg:write key="rng.48" />" class="classic-display"><img src="../ringi/images/original/keiro_now.png" alt="<gsmsg:write key="rng.48" />" class="original-display"><gsmsg:write key="wml.215" /><gsmsg:write key="rng.48" />
          <img src="../ringi/images/classic/icon_keiro_ng.png" alt="<gsmsg:write key="rng.22" />" class="classic-display"><img src="../ringi/images/original/keiro_ng.png" alt="<gsmsg:write key="rng.22" />" class="original-display"><gsmsg:write key="wml.215" /><gsmsg:write key="rng.22" />
        </div>
        <!-- ページング -->
        <div class="txt_r">
          <logic:notEmpty name="rng070Form" property="rngAdminPageList">
            <bean:size id="pageTopCount" name="rng070Form" property="rngAdminPageList" scope="request" />
            <logic:greaterThan name="pageTopCount" value="1">
              <span class="paging">
                <button type="button" class="webIconBtn" onClick="setDateParam();return buttonPush('pageleft');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select property="rngAdminPageTop" onchange="selectPage(0);" styleClass="paging_combo">
                  <html:optionsCollection name="rng070Form" property="rngAdminPageList" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onClick="setDateParam();return buttonPush('pageright');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </span>
            </logic:greaterThan>
          </logic:notEmpty>

        </div>
      <!-- 検索結果一覧 -->
        <bean:define id="sortKey" name="rng070Form" property="rngAdminSortKey" />
        <bean:define id="orderKey" name="rng070Form" property="rngAdminOrderKey" />
        <% int iSortKey = ((Integer) sortKey).intValue();
           int iOrderKey = ((Integer) orderKey).intValue();
           int[] sortKeyList = new int[] { jp.groupsession.v2.rng.RngConst.RNG_SORT_KEKKA,
                                           jp.groupsession.v2.rng.RngConst.RNG_SORT_TITLE,
                                           jp.groupsession.v2.rng.RngConst.RNG_SORT_DATE,
                                           jp.groupsession.v2.rng.RngConst.RNG_SORT_KAKUNIN,
                                           jp.groupsession.v2.rng.RngConst.RNG_SORT_NAME
                                         };
           String[] title_width = new String[] {"w5", "w15", "w10","w10", "w50"};
           jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
           String result = gsMsg.getMessage(request, "cmn.results");
           String subject = gsMsg.getMessage(request, "cmn.subject");
           String date = gsMsg.getMessage(request, "rng.application.date");
           String lastDate = gsMsg.getMessage(request, "rng.105");
           String keiro = gsMsg.getMessage(request, "rng.channel.status");
           String[] titleList = new String[] {result, subject, date, lastDate, keiro};
        %>
        <table class="table-top w100" id="selectionSearchArea">
          <tr>
            <th class="w3 table_title-color"></th>
            <% String down = gsMsg.getMessage(request, "tcd.tcd040.23");
               String up = gsMsg.getMessage(request, "tcd.tcd040.22");
               String sortSign = "";
               int order_asc = jp.groupsession.v2.rng.RngConst.RNG_ORDER_ASC;
               int order_desc = jp.groupsession.v2.rng.RngConst.RNG_ORDER_DESC;
               for (int i = 0; i < sortKeyList.length; i++) {
                 String title = titleList[i];
                 String skey = String.valueOf(sortKeyList[i]);
                 String order = String.valueOf(order_asc);
                 if (iSortKey == sortKeyList[i]) {
                   if (iOrderKey == order_desc) {
                     sortSign = down;
                   } else {
                     sortSign = up;
                     order = String.valueOf(order_desc);
                   }
                 } else {
                     sortSign = "";
                   }
            %>
            <th class="<%= title_width[i] %> table_title-color cursor_p" onClick="return sorton(<%= skey %>, <%= order %>);" nowrap>
                <a class="table_headerSort-top" href="">
                  <%=title%><span class="classic-display"><%= sortSign %></span>
                  <span class="original-display txt_m">
                    <% if (sortSign.equals(down)) {%>
                      <i class="icon-sort_down"></i>
                    <%} else if (sortSign.equals(up)) { %>
                      <i class="icon-sort_up"></i>
                    <% } %>
                  </span>
                </a>
            </th>
            <% } %>
          </tr>
            <%
               String error = gsMsg.getMessage(request, "wml.js.68");
               String soukou = gsMsg.getMessage(request, "cmn.draft");
               String sinsei = gsMsg.getMessage(request, "rng.48");
               String kessai = gsMsg.getMessage(request, "rng.29");
               String kyakka = gsMsg.getMessage(request, "rng.22");
               String done = gsMsg.getMessage(request, "rng.rng030.06");
               String torisage = gsMsg.getMessage(request, "rng.rng030.15");
               int st_soukou = jp.groupsession.v2.rng.RngConst.RNG_STATUS_DRAFT;
               int st_sinsei = jp.groupsession.v2.rng.RngConst.RNG_STATUS_REQUEST;
               int st_kessai = jp.groupsession.v2.rng.RngConst.RNG_STATUS_SETTLED;
               int st_kyakka = jp.groupsession.v2.rng.RngConst.RNG_STATUS_REJECT;
               int st_done = jp.groupsession.v2.rng.RngConst.RNG_STATUS_DONE;
               int st_torisage = jp.groupsession.v2.rng.RngConst.RNG_STATUS_TORISAGE;
            %>
            <logic:notEmpty name="rng070Form" property="rng070dataList">
              <logic:iterate id="rngkn" name="rng070Form" property="rng070dataList" indexId="idx" scope="request">
                <bean:define id="rngstatus" name="rngkn" property="rngStatus" />
                <%
                   int status = ((Integer) rngstatus).intValue();
                   String kekka = error;
                   if (status == st_soukou) {
                     kekka = soukou;
                   } else if (status == st_sinsei) {
                     kekka = sinsei;
                   } else if (status == st_kessai) {
                     kekka = kessai;
                   } else if (status == st_kyakka) {
                     kekka = kyakka;
                   } else if (status == st_done){
                     kekka = done;
                   } else if (status == st_torisage){
                     kekka = torisage;
                   }
                %>
                <tr class="js_listHover cursor_p" id="<bean:write name="rngkn" property="rngSid" />">
                  <td class="txt_c txt_m js_tableTopCheck">
                    <html:multibox name="rng070Form" property="rng070dellist">
                      <bean:write name="rngkn" property="rngSid" />
                    </html:multibox>
                  </td>
                  <td class="js_listClick"><%= kekka %></td>
                  <td class="cl_linkDef js_listClick">
                    <bean:write name="rngkn" property="rngTitle" />
                  </td>
                  <td class="js_listClick">
                    <bean:write name="rngkn" property="strRngAppldate" />
                  </td>
                  <td class="js_listClick">
                    <bean:write name="rngkn" property="strLastManageDate" />
                  </td>
                  <td class="keiro_status js_listClick">
                    <% String apprUserClass = "";%>
                    <logic:equal name="rngkn" property="apprUserDelFlg" value="true">
                      <% apprUserClass = "delete_border"; %>
                    </logic:equal>
                    <logic:notEqual name="rngkn" property="apprUserDelFlg" value="true">
                      <logic:equal name="rngkn" property="usrUkoFlg" value="1">
                        <% apprUserClass = "mukoUser"; %>
                      </logic:equal>
                    </logic:notEqual>
                    <span class="<%= apprUserClass %>"><bean:write name="rngkn" property="apprUser" /></span>
                    <logic:notEmpty name="rngkn" property="keiroNameList">
                     <% int beforeStatus = jp.groupsession.v2.rng.RngConst.RNG_RNCSTATUS_APPR; %>
                      <logic:iterate id="channelData" name="rngkn" property="keiroNameList" indexId="channelIdx">
                        <bean:define id="rncStatus" name="channelData" property="rksStatus" />
                        <% int intRncStatus = ((Integer) rncStatus).intValue(); %>
                      <% if (intRncStatus == jp.groupsession.v2.rng.RngConst.RNG_RNCSTATUS_CONFIRM) { %>
                            <img src="../ringi/images/classic/icon_keiro_now.png" alt="<gsmsg:write key="rng.48" />" class="classic-display">
                            <img src="../ringi/images/original/keiro_now.png" alt="<gsmsg:write key="rng.48" />" class="original-display">
                        <% } else if (intRncStatus == jp.groupsession.v2.rng.RngConst.RNG_RNCSTATUS_APPR) { %>
                            <img src="../ringi/images/classic/icon_keiro_ok.png" alt="<gsmsg:write key="rng.43" />" class="classic-display">
                            <img src="../ringi/images/original/keiro_ok.png" alt="<gsmsg:write key="rng.43" />" class="original-display">
                        <% } else if (intRncStatus == jp.groupsession.v2.rng.RngConst.RNG_RNCSTATUS_NOSET) { %>
                            <img src="../ringi/images/classic/icon_keiro_mi.png" alt="<gsmsg:write key="rng.60" />" class="classic-display">
                            <img src="../ringi/images/original/keiro.png" alt="<gsmsg:write key="rng.60" />" class="original-display">
                        <% } else if (intRncStatus == jp.groupsession.v2.rng.RngConst.RNG_RNCSTATUS_DENIAL) { %>
                            <img src="../ringi/images/classic/icon_keiro_ng.png" alt="<gsmsg:write key="rng.22" />" class="classic-display">
                            <img src="../ringi/images/original/keiro_ng.png" alt="<gsmsg:write key="rng.22" />" class="original-display">
                        <% } %>
                        <bean:define id="dispUserGroup" value="0"/>
                        <logic:equal name="channelData" property="delUser" value="true">
                          <bean:define id="dispUserGroup" value="1"/>
                        </logic:equal>
                        <logic:equal name="channelData" property="groupDelFlg" value="9">
                          <bean:define id="dispUserGroup" value="2"/>
                        </logic:equal>
                        <logic:notEqual name="dispUserGroup" value="0">
                          <strike><bean:write name="channelData" property="usrgrpName" /></strike>
                        </logic:notEqual>
                        <logic:equal name="dispUserGroup" value="0">
                          <bean:define id="mukoUserClass" value="" />
                          <logic:equal name="channelData" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUser" /></logic:equal>
                          <span class="<%=mukoUserClass%>"><bean:write name="channelData" property="usrgrpName" /></span>
                        </logic:equal>
                        <% beforeStatus = intRncStatus; %>
                      </logic:iterate>
                    </logic:notEmpty>
                  </td>
                </tr>
              </logic:iterate>
            </logic:notEmpty>
         </table>
        <!-- ページング -->
        <div class="txt_r">
          <logic:notEmpty name="rng070Form" property="rngAdminPageList">
            <bean:size id="pageBottomCount" name="rng070Form" property="rngAdminPageList" scope="request" />
            <logic:greaterThan name="pageBottomCount" value="1">
              <span class="paging">
                <button type="button" class="webIconBtn" onClick="setDateParam();return buttonPush('pageleft');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select property="rngAdminPageBottom" onchange="selectPage(1);" styleClass="paging_combo">
                  <html:optionsCollection name="rng070Form" property="rngAdminPageList" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onClick="setDateParam();return buttonPush('pageright');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </span>
            </logic:greaterThan>
          </logic:notEmpty>
        </div>
        <%-- PDF出力シーケンスポップアップ --%>
        <div id="loading_pop" title="" class="display_n">
          <table class="w100 h100">
            <tr>
              <td id="pdfOutput" class="loading_area w100 txt_m">
                <p><gsmsg:write key="rng.rngPdfDlg.01"/></p>
                <p id="outputCount" class="txt_c">
                   <logic:greaterThan value="0" name="rng070Form" property="rng070pdfOutputMax">
                      <bean:write name="rng070Form" property="rng070pdfOutputCnt"/><gsmsg:write key="cmn.number"/>&nbsp;
                      /&nbsp;<bean:write name="rng070Form" property="rng070pdfOutputMax"/><gsmsg:write key="cmn.number"/>
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
      <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
    </html:form>
  </body>
</html:html>