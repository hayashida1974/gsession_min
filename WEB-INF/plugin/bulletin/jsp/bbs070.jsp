<%@page import="jp.groupsession.v2.cmn.GSConstCommon"%>
<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.bbs.GSConstBulletin"%>

<!DOCTYPE html>

<%
  String maxLengthValue = String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.MAX_LENGTH_THREVALUE);
  String limitNo = String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_LIMIT_NO);
  String limitYes = String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_LIMIT_YES);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<logic:notEqual name="bbs070Form" property="bbs070cmdMode" value="<%=String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT)%>">
  <title>GROUPSESSION <gsmsg:write key="bbs.bbs070.1" /></title>
</logic:notEqual>
<logic:equal name="bbs070Form" property="bbs070cmdMode" value="<%=String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT)%>">
  <title>GROUPSESSION <gsmsg:write key="bbs.bbs070.2" /></title>
</logic:equal>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />

<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jquery.selection-min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/tinymce-5.10.3/tinymce.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bulletin/js/bbs_tinymce.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bulletin/js/bbs070.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>  

<link rel=stylesheet href='../bulletin/css/bulletin.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<theme:css filename="theme.css" />
</head>

<body onunload="windowClose();" onload="showLengthId($('#input_area_plain')[0], <%=maxLengthValue%>, 'inputlength');">

  <html:form styleId="bbsForm" action="/bulletin/bbs070">

    <input type="hidden" name="CMD" value="">
    <html:hidden name="bbs070Form" property="s_key" />
    <html:hidden name="bbs070Form" property="tempDirId" />
    <html:hidden name="bbs070Form" property="bbs010page1" />
    <html:hidden name="bbs070Form" property="bbs010forumSid" />
    <html:hidden name="bbs070Form" property="bbs060page1" />
    <html:hidden name="bbs070Form" property="threadSid" />
    <html:hidden name="bbs070Form" property="soukouSid" />
    <html:hidden name="bbs070Form" property="searchDspID" />
    <html:hidden name="bbs070Form" property="bbs040forumSid" />
    <html:hidden name="bbs070Form" property="bbs040keyKbn" />
    <html:hidden name="bbs070Form" property="bbs040taisyouThread" />
    <html:hidden name="bbs070Form" property="bbs040taisyouNaiyou" />
    <html:hidden name="bbs070Form" property="bbs040userName" />
    <html:hidden name="bbs070Form" property="bbs040readKbn" />
    <html:hidden name="bbs070Form" property="bbs040publicStatusOngoing" />
    <html:hidden name="bbs070Form" property="bbs040publicStatusScheduled" />
    <html:hidden name="bbs070Form" property="bbs040publicStatusOver" />
    <html:hidden name="bbs070Form" property="bbs040dateNoKbn" />
    <html:hidden name="bbs070Form" property="bbs040fromYear" />
    <html:hidden name="bbs070Form" property="bbs040fromMonth" />
    <html:hidden name="bbs070Form" property="bbs040fromDay" />
    <html:hidden name="bbs070Form" property="bbs040toYear" />
    <html:hidden name="bbs070Form" property="bbs040toMonth" />
    <html:hidden name="bbs070Form" property="bbs040toDay" />
    <html:hidden name="bbs070Form" property="bbs041page1" />
    <html:hidden name="bbs070Form" property="bbs060postPage1" />
    <html:hidden name="bbs070Form" property="bbs060postSid" />
    <html:hidden name="bbs070Form" property="bbs060postOrderKey" />
    <html:hidden name="bbs070Form" property="bbs070limitDisable" />
    <html:hidden name="bbs070Form" property="bbs070limitException" />
    <html:hidden name="bbs070Form" property="bbs070changeDateFlg" />
    <html:hidden name="bbs070Form" property="bbs170backForumSid" />
    <html:hidden name="bbs070Form" property="bbs170allForumFlg" />

    <html:hidden name="bbs070Form" property="bbs070cmdMode" />
    <html:hidden name="bbs070Form" property="bbs070contributorEditKbn" />
    <%-- <html:hidden name="bbs070Form" property="bbs170writeSid" /> --%>
    <html:hidden name="bbs070Form" property="bbs070BackID" />
    <html:hidden name="bbs070Form" property="bbs070valueType" />
    <input type="hidden" name="bbs070limitFrYear" value="" />
    <input type="hidden" name="bbs070limitFrMonth" value="" />
    <input type="hidden" name="bbs070limitFrDay" value="" />
    <input type="hidden" name="bbs070limitYear" value="" />
    <input type="hidden" name="bbs070limitMonth" value="" />
    <input type="hidden" name="bbs070limitDay" value="" />
    <input type="hidden" name="bbs070limitFrHour" value="" />
    <input type="hidden" name="bbs070limitFrMinute" value="" />
    <input type="hidden" name="bbs070limitHour" value="" />
    <input type="hidden" name="bbs070limitMinute" value="" />
    <input type="hidden" name="yearRangeMinFr" value="<bean:write name="bbs070Form" property="bbs070oldYear" />" />
    <input type="hidden" name="yearRangeMaxFr" value="5" />
    <input type="hidden" name="yearRangeMinTo" value="0" />
    <input type="hidden" name="yearRangeMaxTo" value="5" />
    <input type="hidden" name="hourDivision" value="<bean:write name="bbs070Form" property="bbs070hourDivision" />" />
    

    <html:hidden property="bbs220SortKey" />
    <html:hidden property="bbs220OrderKey" />
    <html:hidden property="bbs220BackDsp" />
    <html:hidden property="bbs220BackThreadSid" />
    <html:hidden property="bbs220BackForumSid" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <!-- BODY -->
    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../bulletin/images/classic/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
          <img class="header_pluginImg" src="../bulletin/images/original/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
        </li>
        <li>
          <gsmsg:write key="cmn.bulletin" />
        </li>
        <logic:notEqual name="bbs070Form" property="bbs070cmdMode" value="<%=String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT)%>">
          <li class="pageTitle_subFont">
            <gsmsg:write key="bbs.bbs070.1" />
          </li>
        </logic:notEqual>
        <logic:equal name="bbs070Form" property="bbs070cmdMode" value="<%=String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT)%>">
          <li class="pageTitle_subFont">
            <gsmsg:write key="bbs.bbs070.2" />
          </li>
        </logic:equal>
        <li>
          <div>
            <logic:notEqual name="bbs070Form" property="bbs070cmdMode" value="<%=String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT)%>">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.save.draft" />" onClick="buttonPush('draft');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
                <gsmsg:write key="cmn.save.draft" />
              </button>
            </logic:notEqual>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="threadDateCheck();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backList');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <logic:notEqual name="bbs070Form" property="bbs070limitDisable" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_ENABLE)%>">
        <logic:equal name="bbs070Form" property="bbs070limitException" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_EXCEPTION)%>">
          <div>

            <gsmsg:write key="bbs.bbs070.7" />
          </div>
        </logic:equal>
      </logic:notEqual>

      <table class="table-left w100">
        <tr>
          <th class="w25 no_w txt_l">
            <gsmsg:write key="cmn.contributor" />
          </th>
          <td class="w75">
            <logic:equal name="bbs070Form" property="bbs070contributorEditKbn" value="0">
              <logic:iterate id="labelMdl" name="bbs070Form" property="bbs070contributorList">
                <html:hidden name="bbs070Form" property="bbs070contributor" />
                <logic:equal name="bbs070Form" property="bbs070contributorJKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                  <del>
                    <bean:write name="labelMdl" property="label" />
                  </del>
                </logic:equal>
                <logic:notEqual name="bbs070Form" property="bbs070contributorJKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                  <bean:write name="labelMdl" property="label" />
                </logic:notEqual>
              </logic:iterate>
            </logic:equal>
            <logic:equal name="bbs070Form" property="bbs070contributorEditKbn" value="1">

              <%-- 投稿者項目の設定  --%>
              <html:select property="bbs070contributor" styleClass="wp200">
                <logic:iterate name="bbs070Form" property="bbs070contributorList" id="contributor" type="UsrLabelValueBean">
                  <html:option styleClass="<%=contributor.getCSSClassNameOption()%>" value="<%=contributor.getValue()%>">
                    <bean:write name="contributor" property="label" />
                  </html:option>
                </logic:iterate>
              </html:select>
            </logic:equal>

          </td>
        </tr>

        <tr>
          <th class="w20 no_w txt_l">
            <gsmsg:write key="bbs.3" />
          </th>
          <td>
            <bean:write name="bbs070Form" property="bbs070forumName" />
          </td>
        </tr>

        <tr>
          <th class="no_w txt_l">
            <gsmsg:write key="cmn.title" />
            <span class="cl_fontWarn">※</span>
          </th>
          <td>
            <html:text name="bbs070Form" property="bbs070title" maxlength="70" styleClass="wp650" />
          </td>
        </tr>

        <!-- 重要度 -->
        <tr>
          <th class="no_w txt_l">
            <gsmsg:write key="project.prj050.4" />
          </th>
          <td>
            <div id="imgClick" class="verAlignMid">
              <html:checkbox name="bbs070Form" styleId="bbs070Importance" property="bbs070Importance" value="1" />
              <label for="bbs070Importance">
                <img class="classic-display pl5" src="../common/images/classic/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                <img class="original-display pl5" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                <gsmsg:write key="sml.61" />
              </label>
            </div>
          </td>
        </tr>

        <!-- 本文 -->
        <bean:define id="tempDirId" name="bbs070Form" property="tempDirId" type="java.lang.String"/>
        <tr>
          <th class="no_w txt_l">
            <gsmsg:write key="cmn.content" />
            <span class="cl_fontWarn">※</span>
          </td>
          <td>
            <div class="w100 txt_r">
              <span class="cl_linkDef cursor_p" id="value_content_type_switch"></span>
            </div>

            <div id="input_area_html" class="w100">
              <span id="bbsComposeBodyContent">
              </span>

              <span id="attachmentFileErrorArea2"></span>

              <div class="m0 p0" id="attachment_FormArea2">
                <textarea name="bbs070valueHtml" id="inputstr_tinymce"><bean:write name="bbs070Form" property="bbs070valueHtml" /></textarea>
              </div>
              <input type="file" id="attachmentAreaBtn2" class="display_none" onchange="attachFileSelect(this, '2');" multiple="">
            </div>
            <textarea name="bbs070value" class="w100" rows="10" id="input_area_plain" onkeyup="showLengthStr(value, <%=maxLengthValue%>, 'inputlength');"><bean:write name="bbs070Form" property="bbs070value" /></textarea>
            <div id="plain_text_count" class="fs_13">
              <gsmsg:write key="cmn.current.characters" />:<span class="formCounter" id="inputlength">0</span>&nbsp;/&nbsp;<%=maxLengthValue%>
              <gsmsg:write key="cmn.character" />
            </div>
          </td>
        </tr>

        <tr>
          <th class="no_w txt_l">
            <gsmsg:write key="cmn.attached" />
          </th>
          <td>
            <attachmentFile:filearea
              mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
              pluginId="<%= GSConstBulletin.PLUGIN_ID_BULLETIN %>"
              tempDirId="<%= tempDirId %>"
              formId="1" />
          </td>
        </tr>


        <logic:equal name="bbs070Form" property="bbs070limitDisable" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_ENABLE)%>">
          <tr>
            <th class="no_w txt_l">
              <gsmsg:write key="bbs.12" />
            </th>
            <td>
              <div id="unlimited" class="display_n">
                <bean:write name="bbs070Form" property="bbs070Unlimited" />
              </div>
              <div class="verAlignMid" id="limit_radio_area">
                <html:radio styleId="limit0" name="bbs070Form" property="bbs070limit" value="<%=limitNo%>" />
                <label for="limit0">
                  <gsmsg:write key="cmn.unlimited" />
                </label>
                <html:radio styleClass="ml10" styleId="limit1" name="bbs070Form" property="bbs070limit" value="<%=limitYes%>" />
                <label for="limit1">
                  <gsmsg:write key="bbs.bbs070.4" />
                </label>
              </div>
              <div id="limit_date_area" class="mt10">
                <div class="verAlignMid">
                  <gsmsg:write key="bbs.bbs070.5" />:
                  <html:text name="bbs070Form" property="bbs070limitFrDate" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker ml5"/>
                  <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                  <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
                    <html:text name="bbs070Form" property="bbs070limitFrTime" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
                    <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
                  </span>
                  <span class="ml10">
                    <a href="#!" id="limit_fr_today" class="cursor_p mr10">
                      <gsmsg:write key="cmn.today" />
                    </a>
                    <a href="#!" id="limit_fr_1week" class="cursor_p mr10">1
                      <gsmsg:write key="cmn.weeks" />
                    </a>
                    <a href="#!" id="limit_fr_2week" class="cursor_p mr10">2
                      <gsmsg:write key="cmn.weeks" />
                    </a>
                    <a href="#!" id="limit_fr_month" class="cursor_p mr10">
                      <gsmsg:write key="cmn.months" arg0="1" />
                    </a>
                  </span>
                </div>
                <div class="verAlignMid mt5">
                  <gsmsg:write key="bbs.bbs070.6" />:
                  <html:text name="bbs070Form" property="bbs070limitToDate" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker ml5"/>
                  <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                  <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
                    <html:text name="bbs070Form" property="bbs070limitToTime" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
                    <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
                  </span>
                  <span class="ml10">
                    <a href="#!" id="limit_to_today" class="cursor_p mr10">
                      <gsmsg:write key="cmn.today" />
                    </a>
                    <a href="#!" id="limit_to_1week" class="cursor_p mr10">1
                      <gsmsg:write key="cmn.weeks" />
                    </a>
                    <a href="#!" id="limit_to_2week" class="cursor_p mr10">2
                      <gsmsg:write key="cmn.weeks" />
                    </a>
                    <a href="#!" id="limit_to_month" class="cursor_p mr10">
                      <gsmsg:write key="cmn.months" arg0="1" />
                    </a>
                  </span>
                </div>
                <div class="mt5">
                  ※<gsmsg:write key="bbs.bbs070.3" />
                </div>
              </div>
            </td>
          </tr>
        </logic:equal>
      </table>
      <div class="footerBtn_block">
        <logic:notEqual name="bbs070Form" property="bbs070cmdMode" value="<%=String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT)%>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.save.draft" />" onClick="buttonPush('draft');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
            <gsmsg:write key="cmn.save.draft" />
          </button>
        </logic:notEqual>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="threadDateCheck();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backList');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
      </div>
    </div>

    <div id="threadKeikoku" title="<gsmsg:write key="cmn.warning" />" class="display_n">
      <table class="w100 h100">
        <tr>
          <td width="w15">
            <img class="classic-display" src="../common/images/classic/icon_warn_2.gif">
            <img class="original-display" src="../common/images/original/icon_warn_63.png">
          </td>
          <td id="keikokuMsgArea" class="w85">
            <gsmsg:write key="bbs.bbs070.8" />
            <br>
            <span class="cl_fontWarn fw_b">
              <gsmsg:write key="bbs.bbs070.9" />
              <br>
            </span>
            <gsmsg:write key="bbs.bbs070.10" />
          </td>
        </tr>
      </table>
    </div>
  </html:form>


  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>
