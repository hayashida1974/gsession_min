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
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <link rel=stylesheet href="../common/js/jplot/css/jquery.jqplot.min.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
  <link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
  <link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>


  <gsjsmsg:js filename="gsjsmsg.js"/>
  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/jplot/jquery.jqplot.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/jplot/jqplot.barRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/jplot/jqplot.pieRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/jplot/jqplot.highlighter.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/jplot/jqplot.categoryAxisRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/jplot/jqplot.canvasAxisTickRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>

  <script src="../common/js/jplot/jqplot.canvasTextRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>

  <script src="../common/js/jplot/jqplot.pointLabels.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/jplot/jqplot.cursor.min.js?<%= GSConst.VERSION_PARAM %>"></script>

  <script src='../webmail/js/wml300.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/jquery.infieldlabel.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/jquery.exfixed.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml010.25" /></title>
</head>

<body class="body_03">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/webmail/wml300">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<input type="hidden" name="wmlCmdMode" value="0">
<html:hidden property="wmlViewAccount" />
<html:hidden property="wml300NowPage"/>
<html:hidden property="wml300GraphItem"/>
<html:hidden property="wml300GsAdminFlg"/>
<html:hidden property="wml300CtrlFlgSml"/>
<html:hidden property="wml300CtrlFlgCir"/>
<html:hidden property="wml300CtrlFlgFil"/>
<html:hidden property="wml300CtrlFlgBbs"/>
<html:hidden property="wml300CtrlFlgCht"/>
<html:hidden property="logCountBack"/>

<html:hidden property="jsonDateData"/>
<html:hidden property="jsonJmailData"/>
<html:hidden property="jsonSmailData"/>

<logic:notEqual name="wml300Form" property="wml300DateUnit" value="2">
  <html:hidden property="wml300DateMonthlyFrYear"/>
  <html:hidden property="wml300DateMonthlyFrMonth"/>
  <html:hidden property="wml300DateMonthlyToYear"/>
  <html:hidden property="wml300DateMonthlyToMonth"/>
</logic:notEqual>

<!-- BODY -->
<div class="kanriPageTitle mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="cmn.statistical.info" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('admTool');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper mrl_auto">
  <div class="display_flex bor1 bgC_body bgI_none">
    <div class=" ">
      <table class="wp180 h100 ">

        <%-- ログイン履歴 --%>
        <logic:equal name="wml300Form" property="wml300GsAdminFlg" value="true">
          <tr class="" onClick="buttonPush('statsMain');" >
            <td class="bor_r1 p0">
              <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
                <img class="classic-display mr5" src="../main/images/classic/icon_menu_login_history.gif" alt="<gsmsg:write key="main.login.history" />">
                <img class="original-display mr5" src="../main/images/original/icon_login_history.png" alt="<gsmsg:write key="main.login.history" />">
                <span class="timeline_menu"><gsmsg:write key="main.login.history" /></span>
              </div>
            </td>
          </tr>
        </logic:equal>

        <%-- WEBメール --%>
        <tr class=""  >
          <td class="bor_b1 bor_t1 p0">
            <div class="pl5 toukei_plugin-select bgC_body bgI_none fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../webmail/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="wml.wml010.25" />">
              <img class="original-display mr5" src="../webmail/images/original/menu_icon_single.png" alt="<gsmsg:write key="wml.wml010.25" />">
              <span class="timeline_menu"><gsmsg:write key="wml.wml010.25" /></span>
            </div>
          </td>
        </tr>

        <%-- ショートメール --%>
        <logic:equal name="wml300Form" property="wml300CtrlFlgSml" value="true">
          <tr class="" onClick="buttonPush('smail');" >
            <td class="bor_r1 p0">
              <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
                <img class="classic-display mr5" src="../webmail/images/classic/smail_icon_single.gif" alt="<gsmsg:write key="cmn.shortmail" />">
                <img class="original-display mr5" src="../smail/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.shortmail" />">
                <span class="timeline_menu"><gsmsg:write key="cmn.shortmail" /></span>
              </div>
            </td>
          </tr>
        </logic:equal>

        <%-- 回覧板 --%>
        <logic:equal name="wml300Form" property="wml300CtrlFlgCir" value="true">
          <tr class="" onClick="buttonPush('circular');" >
            <td class="bor_r1 p0">
              <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
                <img class="classic-display mr5" src="../webmail/images/classic/circular_icon_single.gif" alt="<gsmsg:write key="cir.5" />">
                <img class="original-display mr5" src="../webmail/images/original/circular_icon_single.png" alt="<gsmsg:write key="cir.5" />">
                <span class="timeline_menu"><gsmsg:write key="cir.5" /></span>
              </div>
            </td>
          </tr>
        </logic:equal>

        <%-- ファイル管理 --%>
        <logic:equal name="wml300Form" property="wml300CtrlFlgFil" value="true">
          <tr class="" onClick="buttonPush('file');" >
            <td class="bor_r1 p0">
              <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
                <img class="classic-display mr5" src="../webmail/images/classic/file_icon_single.gif" alt="<gsmsg:write key="cmn.filekanri" />">
                <img class="original-display mr5" src="../webmail/images/original/file_icon_single.png" alt="<gsmsg:write key="cmn.filekanri" />">
                <span class="timeline_menu"><gsmsg:write key="cmn.filekanri" /></span>
              </div>
            </td>
          </tr>
        </logic:equal>

        <%-- 掲示板 --%>
        <logic:equal name="wml300Form" property="wml300CtrlFlgBbs" value="true">
          <tr class="" onClick="buttonPush('bulletin');" >
            <td class="bor_r1 p0">
              <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
                <img class="classic-display mr5" src="../webmail/images/classic/bulletin_icon_single.gif" alt="<gsmsg:write key="cmn.bulletin" />">
                <img class="original-display mr5" src="../webmail/images/original/bulletin_icon_single.png" alt="<gsmsg:write key="cmn.bulletin" />">
                <span class="timeline_menu"><gsmsg:write key="cmn.bulletin" /></span>
              </div>
            </td>
          </tr>
        </logic:equal>

        <%-- チャット --%>
        <logic:equal name="wml300Form" property="wml300CtrlFlgCht" value="true">
          <tr class="" onClick="buttonPush('chat');" >
            <td class="bor_r1 p0">
              <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
                <img class="classic-display mr5" src="../webmail/images/classic/chat_icon_single.gif" alt="<gsmsg:write key="cht.01" />">
                <img class="original-display mr5" src="../webmail/images/original/chat_icon_single.png" alt="<gsmsg:write key="cht.01" />">
                <span class="timeline_menu"><gsmsg:write key="cht.01" /></span>
              </div>
            </td>
          </tr>
        </logic:equal>

        <tr class=" ">
          <td class="h100 toukei_pluginArea bor_r1"></td>
        </tr>

      </table>
    </div>
    <div class="w100 p5 ">
      <div class="display_flex">
        <%-- 日付指定 --%>
        <div class="fw_b mr5 txt_l no_w">
          <%-- 月単位表示年月指定 --%>
          <logic:equal name="wml300Form" property="wml300DateUnit" value="2">
              <span class=" "><gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" /></span>
              <html:select name="wml300Form" property="wml300DateMonthlyFrYear" onchange="changeYearMonthCombo('from');">
                <html:optionsCollection name="wml300Form" property="wml300DspYearLabel" value="value" label="label" />
              </html:select>
              <html:select name="wml300Form" property="wml300DateMonthlyFrMonth" onchange="changeYearMonthCombo('from');">
                <html:optionsCollection name="wml300Form" property="wml300DspMonthLabel" value="value" label="label" />
              </html:select>
              <gsmsg:write key="tcd.153" />
              <html:select name="wml300Form" property="wml300DateMonthlyToYear" onchange="changeYearMonthCombo('to');">
                <html:optionsCollection name="wml300Form" property="wml300DspYearLabel" value="value" label="label" />
              </html:select>
              <html:select name="wml300Form" property="wml300DateMonthlyToMonth" onchange="changeYearMonthCombo('to');">
                <html:optionsCollection name="wml300Form" property="wml300DspMonthLabel" value="value" label="label" />
              </html:select>
          </logic:equal>

          <%-- 週単位表示 --%>
          <logic:equal name="wml300Form" property="wml300DateUnit" value="1">
              <div class="verAlignMid">
                <gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" />
                <html:text name="wml300Form" property="wml300DateWeeklyFrStr" readonly="true" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                <span class="ml5"><gsmsg:write key="tcd.153" /></span>
                <html:text name="wml300Form" property="wml300DateWeeklyToStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_toDatePicker"/>
                <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              </div>
          </logic:equal>

          <%-- 日付範囲指定 --%>
          <logic:equal name="wml300Form" property="wml300DateUnit" value="0">
            <div class="verAlignMid">
              <gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" />
              <html:text name="wml300Form" property="wml300DateDailyFrStr" readonly="true" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
              <span class="ml5"><gsmsg:write key="tcd.153" /></span>
              <html:text name="wml300Form" property="wml300DateDailyToStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_toDatePicker" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
            </div>
          </logic:equal>
        </div>
        <div class="ml_auto">
        &nbsp;
        </div>
        <%-- 表示件数 --%>
        <div class="category_sel_area fw_b mr10">
          <span class=" "><gsmsg:write key="cmn.number.display" /><gsmsg:write key="wml.215" /></span>

          <html:select name="wml300Form" property="wml300DspNum" styleClass="wp60" onchange="changeDspNumCombo();">
            <logic:notEmpty name="wml300Form" property="wml300DspNumLabel">
              <html:optionsCollection name="wml300Form" property="wml300DspNumLabel" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
        </div>
        <div class="">
                <%-- 月週日切り替えラジオ--%>
          <div class="category_sel_area no_w">
            <span class="verAlignMid ">
              <html:radio name="wml300Form" property="wml300DateUnit" value="2" styleId="radio_month" onclick="buttonPush('dateUnitChange');"/>
              <label for="radio_month" class=" mr10"><gsmsg:write key="cmn.monthly.3" /></label>
              <html:radio name="wml300Form" property="wml300DateUnit" value="1" styleId="radio_week" onclick="buttonPush('dateUnitChange');"/>
              <label for="radio_week" class=" mr10"><gsmsg:write key="cmn.weekly3" /></label>
              <html:radio name="wml300Form" property="wml300DateUnit" value="0" styleId="radio_day" onclick="buttonPush('dateUnitChange');"/>
              <label for="radio_day" class=" "><gsmsg:write key="cmn.daily" /></label>
            </span>
          </div>
        </div>
      </div>


      <%-- コンテンツ--%>
      <table class="w100 p0 mt10">
        <tr class="hp300">
          <%-- メニュー--%>
          <td id="sel_menu_wrapper" class="wp180 bor1 txt_t p0 toukei_pluginArea">
            <% String jmailClass=" toukei_option-select", smailClass=""; %>
            <logic:equal name="wml300Form" property="wml300GraphItem" value="wml_graph_smail">
            <% jmailClass=""; smailClass=" toukei_option-select"; %>
            </logic:equal>
            <table class="w100 mb5">
              <tr>
                <td class="toukei_option txt_l no_w<%= jmailClass %>" id="wml_graph_jmail" onclick="changeDspItem('wml_graph_jmail');">
                  <gsmsg:write key="sml.sml370.1" />
                </td>
              </tr>
              <tr>
                <td class="toukei_option txt_l no_w<%= smailClass %>" id="wml_graph_smail" onclick="changeDspItem('wml_graph_smail');">
                  <gsmsg:write key="sml.sml370.2" />
                </td>
              </tr>
            </table>
          </td>

          <%-- メニュー格納用縦線 --%>
          <td id="menu_length_area" class="toukei_optionClose borC_light wp5 p0 m0 "></td>

          <%-- グラフ --%>
          <td class=" txt_t p0 m0">
            <table class="ml5 w100 p0 m0">

              <%-- グラフ --%>
              <tr>
                <td class=" pb10 border_none">
                  <div id="wmlCntGraph" class="hp260"></div>
                </td>
              </tr>

              <!-- ページング -->
              <tr>
                <td class="w100 txt_r no_w">
                  <bean:size id="pageCount" name="wml300Form" property="wml300PageLabel" scope="request" />
                  <logic:greaterThan name="pageCount" value="1">
                    <div class="paging">
                      <button type="button" class="webIconBtn" onClick="buttonPush('pageLast');">
                        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                        <i class="icon-paging_left"></i>
                      </button>
                      <logic:notEmpty name="wml300Form" property="wml300PageLabel" scope="request">
                        <html:select styleClass="paging_combo"  property="wml300DspPage1" onchange="changePage(this);">
                          <html:optionsCollection name="wml300Form" property="wml300PageLabel" value="value" label="label" />
                        </html:select>
                      </logic:notEmpty>
                      <button type="button" class="webIconBtn" onClick="buttonPush('pageNext');">
                        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                        <i class="icon-paging_right"></i>
                      </button>
                    </div>
                  </logic:greaterThan>
                </td>
              </tr>

              <%-- 集計一覧 --%>
              <tr id="anken_detail_area border_none">
                <td class="w100 pr5">
                  <table id="anken_val_table" class="table-top m0 w100">
                    <tr class="anken_val_title" id="anken_val_title">
                      <th class="w40 txt_c"><gsmsg:write key="cmn.date3" /></th>
                      <th class="w30 txt_c"><gsmsg:write key="wml.wml300.1" /></th>
                      <th class="w30 txt_c"><gsmsg:write key="wml.wml300.2" /></th>
                    </tr>

                    <logic:notEmpty name="wml300Form" property="wml300LogCountList">
                      <logic:iterate name="wml300Form" property="wml300LogCountList" id="dayData">
                        <tr class="anken_val">
                          <td class="txt_c"><bean:write name="dayData" property="dspDate" /></td>
                          <td class="txt_r"><bean:write name="dayData" property="strJmailNum" /></td>
                          <td class="txt_r"><bean:write name="dayData" property="strSmailNum" /></td>
                        </tr>
                      </logic:iterate>

                      <tr>
                        <td class="txt_c fw_b"><gsmsg:write key="cmn.average" /></td>
                        <td class="txt_r fw_b"><bean:write name="wml300Form" property="wml300AveJmailNum" /></td>
                        <td class="txt_r fw_b"><bean:write name="wml300Form" property="wml300AveSmailNum" /></td>
                      </tr>
                      <tr>
                        <td class="txt_c fw_b"><gsmsg:write key="cmn.sum" /></td>
                        <td class="txt_r fw_b"><bean:write name="wml300Form" property="wml300SumJmailNum" /></td>
                        <td class="txt_r fw_b"><bean:write name="wml300Form" property="wml300SumSmailNum" /></td>
                      </tr>
                    </logic:notEmpty>
                  </table>
                </td>
              </tr>

              <!-- ページング -->
              <tr>
                <td class="w100 txt_r pt4 no_w">
                  <bean:size id="pageCount2" name="wml300Form" property="wml300PageLabel" scope="request" />
                  <logic:greaterThan name="pageCount2" value="1">
                    <div class="paging">
                      <button type="button" class="webIconBtn" onClick="buttonPush('pageLast');">
                        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                        <i class="icon-paging_left"></i>
                      </button>
                      <logic:notEmpty name="wml300Form" property="wml300PageLabel" scope="request">
                        <html:select styleClass="paging_combo"  property="wml300DspPage2" onchange="changePage(this);">
                          <html:optionsCollection name="wml300Form" property="wml300PageLabel" value="value" label="label" />
                        </html:select>
                      </logic:notEmpty>
                      <button type="button" class="webIconBtn" onClick="buttonPush('pageNext');">
                        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                        <i class="icon-paging_right"></i>
                      </button>
                    </div>
                  </logic:greaterThan>
                </td>
              </tr>

            </table>
          </td>
        </tr>
      </table>
    </div>
  </div>
  <span id="tooltip_area"></span>
  <div id="searchBtnResultPop" class="hp550 display_none ofy_h"  title="<gsmsg:write key="bbs.bbs041.2" />">
    <p>
      <div id="searchBtnResultArea" class="hp450">
      </div>
    </p>
  </div>

</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>