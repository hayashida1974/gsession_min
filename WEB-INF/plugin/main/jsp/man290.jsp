<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.man.GSConstMain" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<!DOCTYPE html>

<% String maxLengthContent = String.valueOf(jp.groupsession.v2.man.GSConstMain.MAX_LENGTH_VALUE); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man290.1" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man290.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>

<% String selectionEvent = ""; %>
<% boolean selectionFlg = false; %>
<% String valueFocusEvent = ""; %>
<logic:equal name="man290Form" property="webSearchUse" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
  <script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/selectionSearchText.js?<%= GSConst.VERSION_PARAM %>"></script>
  <% selectionFlg = true; %>
</logic:equal>

<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<% String closeScript = "windowClose();"; %>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onunload="<%= closeScript %>" onload="moreRe();setDisabled();changeWeekCombo();showLengthId($('#inputstr2')[0], <%= maxLengthContent %>, 'inputlength');">


<html:form action="/main/man290">

<input type="hidden" name="hourDivision" value="10" />
<input type="hidden" name="man290FrYear" value="" />
<input type="hidden" name="man290FrMonth" value="" />
<input type="hidden" name="man290FrDay" value="" />
<input type="hidden" name="man290FrHour" value="" />
<input type="hidden" name="man290FrMin" value="" />
<input type="hidden" name="man290ToYear" value="" />
<input type="hidden" name="man290ToMonth" value="" />
<input type="hidden" name="man290ToDay" value="" />
<input type="hidden" name="man290ToHour" value="" />
<input type="hidden" name="man290ToMin" value="" />
<input type="hidden" name="yearRangeMinFr" value="<bean:write name="man290Form" property="man290YearRangeMin" />" />
<input type="hidden" name="yearRangeMaxFr" value="<bean:write name="man290Form" property="man290YearRangeMax" />" />
<input type="hidden" name="yearRangeMinTo" value="<bean:write name="man290Form" property="man290YearRangeMin" />" />
<input type="hidden" name="yearRangeMaxTo" value="<bean:write name="man290Form" property="man290YearRangeMax" />" />

<html:hidden property="cmd" />
<html:hidden property="man320OrderKey" />
<html:hidden property="man320SortKey" />
<html:hidden property="man320FormAdminConfBtn" />
<html:hidden property="man320SltPage1" />
<html:hidden property="man320SltPage2" />
<html:hidden property="man320PageNum" />
<html:hidden property="man320SelectedSid" />
<html:hidden property="man290elementKbn" />
<html:hidden property="man290helpMode" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<input type="hidden" name="helpPrm" value="<bean:write name="man290Form" property="man290helpMode" />">
<!--BODY -->
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="<gsmsg:write key="cmn.information" />">
      <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="<gsmsg:write key="cmn.information" />">
    </li>
    <li><gsmsg:write key="cmn.information" /></li>
    <li class="pageTitle_subFont">
      <logic:equal name="man290Form" property="cmd" value="add">
        <gsmsg:write key="cmn.entry" />
      </logic:equal>
      <logic:equal name="man290Form" property="cmd" value="edit">
        <gsmsg:write key="cmn.change" />
      </logic:equal>
    </li>
    <li>
      <div>
        <logic:equal name="man290Form" property="cmd" value="add">
          <input type="hidden" name="CMD" value="290_ok">
          <button type="submit" class="baseBtn" value="<gsmsg:write key="cmn.entry" />" onclick="setParams();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
            <gsmsg:write key="cmn.entry" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('290_back');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>
        <logic:equal name="man290Form" property="cmd" value="edit">
          <input type="hidden" name="CMD" value="290_ok">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.register.copy2" />" onclick="buttonPush2('290_copy', 'add');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
            <gsmsg:write key="cmn.register.copy2" />
          </button>
          <button type="submit" class="baseBtn" value="<gsmsg:write key="cmn.edit" />"  onclick="setParams();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_1.png" alt="<gsmsg:write key="cmn.edit" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
            <gsmsg:write key="cmn.edit" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('290_del');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('290_back');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="txt_l">
    <a href="#" onClick="more()"><span class="textLink" id="more">＞＞<gsmsg:write key="man.advanced.display" /></span></a>
  </div>
  <div class="txt_l mt5">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </div>
  <table class="table-left">
    <tr id="moreSettings" class="display_n">
      <th class="w20 no_w" colspan="2">
        <gsmsg:write key="main.man290.2" />
      </th>
      <td class="w80">
        <div class="display_inline txt_t">
          <div class="verAlignMid">
            <html:radio name="man290Form" property="man290ExtKbn" value="1" styleId="man290ExtKbn0" onclick="setDisabled();"/>
            <label for="man290ExtKbn0"><gsmsg:write key="cmn.everyday" /></label>
          </div>
          <div class="verAlignMid ml10" >
            <html:radio name="man290Form" property="man290ExtKbn" value="2" styleId="man290ExtKbn1" onclick="setDisabled();"/>
            <label for="man290ExtKbn1"><gsmsg:write key="cmn.weekly2" />
          </div>
          <div class="verAlignMid ml10" >
            <html:radio name="man290Form" property="man290ExtKbn" value="3" styleId="man290ExtKbn2" onclick="setDisabled();"/>
            <label for="man290ExtKbn2"><gsmsg:write key="cmn.monthly.2" /></label>
          </div>
        </div>
        <div class="mt5">
          <div class="verAlignMid">
            <gsmsg:write key="cmn.week" />：
            <html:select property="man290Week" onchange="changeWeekCombo();">
              <html:optionsCollection name="man290Form" property="man290WeekLabel" value="value" label="label" />
            </html:select>
          </div>
          <div class="verAlignMid ml5">
            <gsmsg:write key="cmn.day" />：
            <html:select property="man290Day">
              <html:optionsCollection name="man290Form" property="man290ExDayLabel" value="value" label="label" />
            </html:select>
          </div>
        </div>
        <div class="mt5">
          <div>
            <div class="bor_l1 bor_t1 display_inline">
              <div class="wp30hp30 bgC_calSunday verAlignMid bor_r1">
                <span class="cl_fontSunday mrl_auto">
                  <gsmsg:write key="cmn.sunday" />
                </span>
              </div>
              <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                <span class="mrl_auto">
                  <gsmsg:write key="cmn.Monday" />
                </span>
              </div>
              <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                <span class="mrl_auto">
                  <gsmsg:write key="cmn.tuesday" />
                </span>
              </div>
              <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                <span class="mrl_auto">
                  <gsmsg:write key="cmn.wednesday" />
                </span>
              </div>
              <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                <span class="mrl_auto">
                  <gsmsg:write key="cmn.thursday" />
                </span>
              </div>
              <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1">
                <span class="mrl_auto">
                  <gsmsg:write key="cmn.friday" />
                </span>
              </div>
              <div class="wp30hp30 bgC_calSaturday verAlignMid bor_r1">
                <span class="cl_fontSaturday mrl_auto">
                  <gsmsg:write key="cmn.saturday" />
                </span>
              </div>
            </div>
          </div>
          <div>
            <div class="bor_l1 bor_t1 bor_b1  display_inline">
              <div class="wp30hp30 bgC_calSunday verAlignMid bor_r1 js_tableTopCheck cursor_p js_dayCheck">
                <span class="cl_fontSunday mrl_auto verAlignMid">
                  <html:multibox property="man290Dweek" value="1" styleClass="m0" />
                </span>
              </div>
              <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck cursor_p js_dayCheck">
                <span class="mrl_auto verAlignMid">
                  <html:multibox property="man290Dweek" value="2" styleClass="m0" />
                </span>
              </div>
              <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck cursor_p js_dayCheck">
                <span class="mrl_auto verAlignMid">
                  <html:multibox property="man290Dweek" value="3" styleClass="m0" />
                </span>
              </div>
              <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck cursor_p js_dayCheck">
                <span class="mrl_auto verAlignMid">
                  <html:multibox property="man290Dweek" value="4" styleClass="m0" />
                </span>
              </div>
              <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck cursor_p js_dayCheck">
                <span class="mrl_auto verAlignMid">
                  <html:multibox property="man290Dweek" value="5" styleClass="m0" />
                </span>
              </div>
              <div class="wp30hp30 bgC_tableCell verAlignMid bor_r1 js_tableTopCheck cursor_p js_dayCheck">
                <span class="mrl_auto verAlignMid">
                  <html:multibox property="man290Dweek" value="6" styleClass="m0"/>
                </span>
              </div>
              <div class="wp30hp30 bgC_calSaturday verAlignMid bor_r1 js_tableTopCheck cursor_p js_dayCheck">
                <span class="cl_fontSaturday mrl_auto verAlignMid">
                  <html:multibox property="man290Dweek" value="7" styleClass="m0" />
                </span>
              </div>
            </div>
          </div>
        </div>
        <div class="mt5">
          <span class="cl_fontWarn fs_13">><gsmsg:write key="main.man290.3" /></span><br>
          <div class="verAlignMid" >
            <html:radio name="man290Form" property="man290HolKbn" value="0" styleId="man290HolidayKbn0"/>
            <label for="man290HolidayKbn0"><gsmsg:write key="cmn.displayed.as" /></label>
          </div><br>
          <div class="verAlignMid" >
            <html:radio name="man290Form" property="man290HolKbn" value="1" styleId="man290HolidayKbn1"/>
            <label for="man290HolidayKbn1"><gsmsg:write key="cmn.dont.show" /></label>
          </div><br>
          <div class="verAlignMid" >
            <html:radio name="man290Form" property="man290HolKbn" value="2" styleId="man290HolidayKbn2"/>
            <label for="man290HolidayKbn2"><gsmsg:write key="main.man290.6" /></label>
          </div><br>
          <div class="verAlignMid" >
            <html:radio name="man290Form" property="man290HolKbn" value="3" styleId="man290HolidayKbn3"/>
            <label for="man290HolidayKbn3"><gsmsg:write key="main.man290.7" /></label>
          </div>
        </div>
      </td>
    </tr>
    <tr class="js_eventTr border_top_none">
      <th class="w15 no_w" rowspan="2">
        <gsmsg:write key="cmn.period" />
      </th>
      <th class="w5 no_w">
        <gsmsg:write key="cmn.start" />
      </th>
      <td class="w80">
        <div>
          <span class="verAlignMid">
            <html:text name="man290Form" property="man290FrDate" maxlength="10" styleId="frDate" styleClass="txt_c wp95 datepicker js_frDatePicker" />
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            <button type="button" class="webIconBtn ml5" onClick="return moveDay($('#frDate')[0], 1);" id="man290FrCalBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#frDate')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </button>
            <span class="original-display">
              <a class="fw_b todayBtn " onClick="return moveDay($('#frDate')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </a>
            </span>
            <button type="button" class="webIconBtn" onClick="return moveDay($('#frDate')[0], 3);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
            <div class="mrl_auto"></div>
          </span>
          <span class="verAlignMid">
            <html:text name="man290Form" property="man290FrTime" styleId="fr_clock" maxlength="5" styleClass="ml5 clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w5 no_w">
        <gsmsg:write key="cmn.end" />
      </th>
      <td class="w80">
        <div>
          <span class="verAlignMid">
            <html:text name="man290Form" property="man290ToDate" maxlength="10" styleId="toDate" styleClass="txt_c wp95 datepicker js_toDatePicker" />
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            <button type="button" class="webIconBtn ml5" onClick="return moveDay($('#toDate')[0], 1);" id="man290FrCalBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#toDate')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </button>
            <span class="original-display">
              <a class="fw_b todayBtn " onClick="return moveDay($('#toDate')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </a>
            </span>
            <button type="button" class="webIconBtn" onClick="return moveDay($('#toDate')[0], 3);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
            <div class="mrl_auto"></div>
          </span>
          <span class="verAlignMid">
            <html:text name="man290Form" property="man290ToTime" styleId="to_clock" maxlength="5" styleClass="ml5 clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w" colspan="2">
        <gsmsg:write key="cmn.message" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text name="man290Form" styleClass="w100" maxlength="150" property="man290Msg" styleId="selectionSearchArea" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w" colspan="2">
        <gsmsg:write key="cmn.content2" />
      </th>
      <td class="w80">
        <textarea name="man290Value" rows="10" class="w100" onkeyup="showLengthStr(value, <%= maxLengthContent %>, 'inputlength');" id="inputstr2" <%= valueFocusEvent %>><bean:write name="man290Form" property="man290Value" /></textarea>
        <div>
          <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthContent %>&nbsp;<gsmsg:write key="cmn.character" /></span>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w" colspan="2">
        <gsmsg:write key="main.exposed" />
      </th>
      <td class="w80">
        <ui:usrgrpselector name="man290Form" property="man290memberSidUI" styleClass="hp215" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w" colspan="2">
        <gsmsg:write key="cmn.attached" />
      </th>
      <td class="w80">
        <attachmentFile:filearea
          mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
          pluginId="<%= GSConstMain.PLUGIN_ID_MAIN %>"
          tempDirId="man290" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <logic:equal name="man290Form" property="cmd" value="add">
      <button type="submit" class="baseBtn" value="<gsmsg:write key="cmn.entry" />" onclick="setParams();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
        <gsmsg:write key="cmn.entry" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('290_back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:equal>
    <logic:equal name="man290Form" property="cmd" value="edit">
      <button type="submit" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="setParams();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_1.png" alt="<gsmsg:write key="cmn.edit" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
        <gsmsg:write key="cmn.edit" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('290_del');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('290_back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:equal>
  </div>
</div>

</html:form>

<% if (selectionFlg) { %>
<span id="tooltip_search" class="tooltip_search"></span>
<span id="damy"></span>
<% } %>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>