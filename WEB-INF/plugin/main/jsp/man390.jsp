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
<title><gsmsg:write key="main.login.history" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
<!--[if IE]><script type="text/javascript" src="../common/js/graph_circle_1_0_2/excanvas/excanvas.js"></script><![endif]-->
<script src="../common/js/jplot/jquery.jqplot.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.barRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.pieRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.highlighter.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.categoryAxisRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.canvasAxisTickRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/jplot/jqplot.canvasTextRenderer.min.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/jplot/jqplot.pointLabels.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jplot/jqplot.cursor.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../nippou/js/ntp.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.infieldlabel.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.exfixed.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src='../main/js/man390.js?<%= GSConst.VERSION_PARAM %>'></script>

<link rel=stylesheet href="../common/js/jplot/css/jquery.jqplot.min.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href="../common/js/jquery-ui-1.8.16/development-bundle/themes/base/jquery.ui.all.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/main/man390">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="man390NowPage"/>
<html:hidden property="man390GraphItem"/>
<html:hidden property="logCountBack"/>

<html:hidden property="jsonDateData"/>
<html:hidden property="jsonLoginRate"/>

<logic:notEqual name="man390Form" property="man390DateUnit" value="2">
  <html:hidden property="man390DateMonthlyFrYear"/>
  <html:hidden property="man390DateMonthlyFrMonth"/>
  <html:hidden property="man390DateMonthlyToYear"/>
  <html:hidden property="man390DateMonthlyToMonth"/>
</logic:notEqual>

<!-- BODY -->

<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.statistical.info" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('admTool');">
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
        <tr class="" onClick="buttonPush('statsMain');" >
          <td class="bor_b1  p0">
            <div class="pl5 toukei_plugin-select bgC_body bgI_none fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../main/images/classic/icon_menu_login_history.gif" alt="<gsmsg:write key="main.login.history" />">
              <img class="original-display mr5" src="../main/images/original/icon_login_history.png" alt="<gsmsg:write key="main.login.history" />">
              <span class="timeline_menu"><gsmsg:write key="main.login.history" /></span>
            </div>
          </td>
        </tr>

        <%-- WEBメール --%>
        <logic:equal name="man390Form" property="man390CtrlFlgWml" value="true">
        <tr class="" onClick="buttonPush('webmail');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../webmail/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="wml.wml010.25" />">
              <img class="original-display mr5" src="../webmail/images/original/menu_icon_single.png" alt="<gsmsg:write key="wml.wml010.25" />">
              <span class="timeline_menu"><gsmsg:write key="wml.wml010.25" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>
        <%-- ショートメール --%>
        <logic:equal name="man390Form" property="man390CtrlFlgSml" value="true">
        <tr class="" onClick="buttonPush('smail');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../smail/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.shortmail" />">
              <img class="original-display mr5" src="../smail/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.shortmail" />">
              <span class="timeline_menu"><gsmsg:write key="cmn.shortmail" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>
        <%-- 回覧板 --%>
        <logic:equal name="man390Form" property="man390CtrlFlgCir" value="true">
        <tr class="" onClick="buttonPush('circular');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../circular/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cir.5" />">
              <img class="original-display mr5" src="../circular/images/original/menu_icon_single.png" alt="<gsmsg:write key="cir.5" />">
              <span class="timeline_menu"><gsmsg:write key="cir.5" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- ファイル管理 --%>
        <logic:equal name="man390Form" property="man390CtrlFlgFil" value="true">
        <tr class="" onClick="buttonPush('file');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../file/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.filekanri" />">
              <img class="original-display mr5" src="../file/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.filekanri" />">
              <span class="timeline_menu"><gsmsg:write key="cmn.filekanri" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- 掲示板 --%>
        <logic:equal name="man390Form" property="man390CtrlFlgBbs" value="true">
        <tr class="" onClick="buttonPush('bulletin');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../bulletin/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.bulletin" />">
              <img class="original-display mr5" src="../bulletin/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.bulletin" />">
              <span class="timeline_menu"><gsmsg:write key="cmn.bulletin" /></span>
            </div>
          </td>
        </tr>
        </logic:equal>

        <%-- チャット --%>
        <logic:equal name="man390Form" property="man390CtrlFlgCht" value="true">
        <tr class="" onClick="buttonPush('chat');" >
          <td class="bor_r1 p0">
            <div class="pl5 toukei_plugin fs_16 no_w verAlignMid border_none" >
              <img class="classic-display mr5" src="../chat/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cht.01" />">
              <img class="original-display mr5" src="../chat/images/original/menu_icon_single.png" alt="<gsmsg:write key="cht.01" />">
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
    <div class="w100 p5">
      <div class="display_flex">
        <div class="fw_b mr5 txt_l no_w">
          <%-- 月単位表示年月指定 --%>
          <logic:equal name="man390Form" property="man390DateUnit" value="2">
              <gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" />
              <html:select name="man390Form" property="man390DateMonthlyFrYear" onchange="changeYearMonthCombo('from');">
                <html:optionsCollection name="man390Form" property="man390DspYearLabel" value="value" label="label" />
              </html:select>
              <html:select name="man390Form" property="man390DateMonthlyFrMonth" onchange="changeYearMonthCombo('from');">
                <html:optionsCollection name="man390Form" property="man390DspMonthLabel" value="value" label="label" />
              </html:select>
              <gsmsg:write key="tcd.153" />
              <html:select name="man390Form" property="man390DateMonthlyToYear" onchange="changeYearMonthCombo('to');">
                <html:optionsCollection name="man390Form" property="man390DspYearLabel" value="value" label="label" />
              </html:select>
              <html:select name="man390Form" property="man390DateMonthlyToMonth" onchange="changeYearMonthCombo('to');">
                <html:optionsCollection name="man390Form" property="man390DspMonthLabel" value="value" label="label" />
              </html:select>
          </logic:equal>
          <%-- 週単位表示 --%>
          <logic:equal name="man390Form" property="man390DateUnit" value="1">
            <div id="jquery-ui-datepicker-wrap" class="display_flex">
              <gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" />
              <html:text name="man390Form" property="man390DateWeeklyFrStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <label for="jquery-ui-datepicker-from" class="ml5"><gsmsg:write key="tcd.153" /></label>
              <html:text name="man390Form" property="man390DateWeeklyToStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_toDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            </div>
          </logic:equal>
          <%-- 日付範囲指定 --%>
          <logic:equal name="man390Form" property="man390DateUnit" value="0">
            <div id="jquery-ui-datepicker-wrap" class="display_flex">
              <gsmsg:write key="ntp.132" /><gsmsg:write key="wml.215" />
              <html:text name="man390Form" property="man390DateDailyFrStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <label for="jquery-ui-datepicker-from" class="ml5"><gsmsg:write key="tcd.153" /></label>
              <html:text name="man390Form" property="man390DateDailyToStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            </div>
          </logic:equal>
        </div>
        <div class="ml_auto">
        &nbsp;
        </div>
        <div class="fw_b mr10 ">
          <div class="txt_l">
            <div class="category_sel_area">
              <gsmsg:write key="cmn.number.display" /><gsmsg:write key="wml.215" />
              <html:select name="man390Form" property="man390DspNum" styleClass="wp60" onchange="changeDspNumCombo();">
                <logic:notEmpty name="man390Form" property="man390DspNumLabel">
                  <html:optionsCollection name="man390Form" property="man390DspNumLabel" value="value" label="label" />
                </logic:notEmpty>
              </html:select>
            </div>
          </div>
        </div>
        <div class="">
          <div class="category_sel_area">
            <span class="verAlignMid">
              <html:radio name="man390Form" property="man390DateUnit" value="2" styleId="radio_month" onclick="buttonPush('dateUnitChange');"/>
              <label for="radio_month" class="no_w mr10"><gsmsg:write key="cmn.monthly.3" /></label>
              <html:radio name="man390Form" property="man390DateUnit" value="1" styleId="radio_week" onclick="buttonPush('dateUnitChange');"/>
              <label for="radio_week" class="no_w mr10"><gsmsg:write key="cmn.weekly3" /></label>
              <html:radio name="man390Form" property="man390DateUnit" value="0" styleId="radio_day" onclick="buttonPush('dateUnitChange');"/>
              <label for="radio_day" class="no_w "><gsmsg:write key="cmn.daily" /></label>
            </span>
          </div>
        </div>
      </div>
      <table class="w100 p0 m0">
        <tr class="hp300">
          <%-- グラフ --%>
          <td class=" txt_t p0 m0">
            <table class="w100 p0 m0">

              <%-- グラフ --%>
              <tr>
                <td class="pl5  border_none">
                  <div id="manCntGraph" class="hp260"></div>
                </td>
              </tr>

              <!-- ページング -->
              <tr>
                <td class="w100 txt_r no_w">
                  <bean:size id="pageCount" name="man390Form" property="man390PageLabel" scope="request" />
                  <logic:greaterThan name="pageCount" value="1">
                    <div class="paging">
                      <button type="button" class="webIconBtn" onClick="buttonPush('pageLast');">
                        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                        <i class="icon-paging_left"></i>
                      </button>
                      <logic:notEmpty name="man390Form" property="man390PageLabel" scope="request">
                        <html:select property="man390DspPage1" onchange="changePage(this);" styleClass="text_i">
                          <html:optionsCollection name="man390Form" property="man390PageLabel" value="value" label="label" />
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
              <tr id="anken_detail_area" class="border_none">
                <td class="w100">
                  <table id="anken_val_table" class="table-top m0 w100">
                    <tr class="anken_val_title" id="anken_val_title">
                      <logic:equal name="man390Form" property="man390DateUnit" value="0">
                        <th class="w40 txt_c"><gsmsg:write key="cmn.date3" /></th>
                        <th class="w20 txt_c"><gsmsg:write key="main.man390.3" /></th>
                        <th class="w20 txt_c"><gsmsg:write key="main.man390.1" /></th>
                        <th class="w20 txt_c"><gsmsg:write key="main.man390.2" /></th>
                      </logic:equal>
                      <logic:notEqual name="man390Form" property="man390DateUnit" value="0">
                        <th class="w40 txt_c"><gsmsg:write key="cmn.date3" /></th>
                        <th class="w30 txt_c"><gsmsg:write key="main.man390.3" /></th>
                        <th class="w30 txt_c"><gsmsg:write key="main.man390.2" /></th>
                      </logic:notEqual>
                    </tr>

                    <logic:notEmpty name="man390Form" property="man390LogCountList">
                      <logic:iterate name="man390Form" property="man390LogCountList" id="dayData">
                        <tr class="anken_val">
                          <td class="txt_c"><bean:write name="dayData" property="dspDate" /></td>
                          <td class="txt_r"><bean:write name="dayData" property="dspLoginNum" /></td>
                          <logic:equal name="man390Form" property="man390DateUnit" value="0">
                            <td class="txt_r"><bean:write name="dayData" property="dspLoginUserNum" /></td>
                          </logic:equal>
                          <td class="txt_r"><bean:write name="dayData" property="dspLoginRate" /></td>
                        </tr>
                      </logic:iterate>
                      <tr>
                        <td class="txt_c fw_b"><gsmsg:write key="cmn.sum" /></td>
                        <td class="txt_r fw_b"><bean:write name="man390Form" property="man390SumLoginNum" /></td>
                        <logic:equal name="man390Form" property="man390DateUnit" value="0">
                          <td class="txt_r fw_b"><bean:write name="man390Form" property="man390SumLoginData" /></td>
                        </logic:equal>
                        <td class="txt_r fw_b"></td>
                      </tr>
                    </logic:notEmpty>
                  </table>
                </td>
              </tr>

              <!-- ページング -->
              <tr>
                <td class="w100 txt_r pt4 no_w">
                  <bean:size id="pageCount2" name="man390Form" property="man390PageLabel" scope="request" />
                  <logic:greaterThan name="pageCount2" value="1">
                    <div class="paging">
                      <button type="button" class="webIconBtn" onClick="buttonPush('pageLast');">
                        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                        <i class="icon-paging_left"></i>
                      </button>
                      <logic:notEmpty name="man390Form" property="man390PageLabel" scope="request">
                        <html:select styleClass="paging_combo"  property="man390DspPage2" onchange="changePage(this);">
                          <html:optionsCollection name="man390Form" property="man390PageLabel" value="value" label="label" />
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
            <logic:equal name="man390Form" property="man390DateUnit" value="0">
              <div class="txt_l">
                <gsmsg:write key="cmn.comments" /><gsmsg:write key="main.man390.4" />
              </div>
              <div class="txt_l">
                <gsmsg:write key="cmn.comments" /><gsmsg:write key="main.man390.5" />
              </div>
            </logic:equal>
            <logic:notEqual name="man390Form" property="man390DateUnit" value="0">
              <div class="txt_l">
                <gsmsg:write key="cmn.comments" /><gsmsg:write key="main.man390.6" />
              </div>
              <div class="txt_l">
                <gsmsg:write key="cmn.comments" /><gsmsg:write key="main.man390.7" />
              </div>
            </logic:notEqual>
          </td>
        </tr>
      </table>
    </div>
  </div>
</div>






<table width="100%" cellpadding="5" cellspacing="0">
<tr>
<td width="100%" align="center">

<span id="tooltip_area"></span>

<div id="searchBtnResultPop" style="height:550px;overflow-y:hidden;display:none;" title="<gsmsg:write key="bbs.bbs041.2" />">
  <p>
    <div id="searchBtnResultArea" style="height:450px;">
    </div>
  </p>
</div>

</td>
</tr>
</table>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>