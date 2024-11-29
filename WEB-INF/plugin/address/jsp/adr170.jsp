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
<%@ page import="jp.groupsession.v2.adr.GSConstAddress" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<!DOCTYPE html>
<% String edit = String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT);%>
<% String maxLengthBiko = String.valueOf(jp.groupsession.v2.adr.GSConstAddress.MAX_LENGTH_ADR3_BIKO);%>

<% String markOther    = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_OTHER); %>
<% String markTel      = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_TEL); %>
<% String markMail     = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_MAIL); %>
<% String markWeb      = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_WEB);  %>
<% String markMeeting  = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_MEETING);   %>

<%-- <gsmsg:write key="adr.mark.image" /> --%>
<%
  java.util.HashMap imgMapClassic = new java.util.HashMap();
  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
  String msgTel = gsMsg.getMessage(request, "cmn.phone");
  String msgMail = gsMsg.getMessage(request, "cmn.mail");
  String msgMeeting = gsMsg.getMessage(request, "address.28");
  String msgOther = gsMsg.getMessage(request, "cmn.other");

  imgMapClassic.put(markTel, "<img src=\"../common/images/classic/icon_call.png\" alt=" + "\"" + msgTel + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put(markMail, "<img src=\"../address/images/classic/icon_mail.gif\" alt=" + "\"" + msgMail + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put(markWeb, "<img src=\"../address/images/classic/icon_web.gif\" alt=\"Web\" border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put(markMeeting, "<img src=\"../common/images/classic/icon_syorui.gif\" alt=" + "\"" + msgMeeting + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put("none", "");

  java.util.HashMap imgMapOriginal = new java.util.HashMap();
  imgMapOriginal.put(markTel, "<img src=\"../common/images/original/icon_call.png\" alt=" + "\"" + msgTel + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put(markMail, "<img src=\"../common/images/original/icon_mail.png\" alt=" + "\"" + msgMail + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put(markWeb, "<img src=\"../address/images/original/icon_web.png\" alt=\"Web\" border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put(markMeeting, "<img src=\"../common/images/original/icon_siryo.png\" alt=" + "\"" + msgMeeting + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put("none", "");
%>

<%
  java.util.HashMap imgTextMap = new java.util.HashMap();
  imgTextMap.put(markTel, msgTel);
  imgTextMap.put(markMail, msgMail);
  imgTextMap.put(markWeb, "Web");
  imgTextMap.put(markMeeting, msgMeeting);

  imgTextMap.put("none", "&nbsp;");
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="address.112" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">

<theme:css filename="theme.css"/>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr170.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>  
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>

<% String closeScript = "windowClose();"; %>
<logic:equal name="adr170Form" property="projectPluginKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_USE) %>">
<% closeScript += "projectWindowClose();"; %>
</logic:equal>

</head>

<body class="body_03" onunload="<%= closeScript %>" onload="showLengthId($('#inputstr')[0], <%= maxLengthBiko %>, 'inputlength');">

<html:form action="/address/adr170">

<input type="hidden" name="CMD" value="ok">
<input type="hidden" name="helpPrm" value="<bean:write name="adr170Form" property="adr160ProcMode" />">

<html:hidden property="seniFlg" />

<html:hidden property="adr160ProcMode" />
<html:hidden property="adr160EditSid" />
<html:hidden property="adr160pageNum1" />
<html:hidden property="adr160pageNum2" />
<html:hidden property="sortKey" />
<html:hidden property="orderKey" />
<html:hidden property="adr170InitFlg" />
<html:hidden property="adr170delProjectSid" />
<html:hidden property="projectPluginKbn" />
<input type="hidden" name="hourDivision" value="5" />

<logic:notEmpty name="adr170Form" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr170Form" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr170Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr170Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr170Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr170Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<div id="adr170ProjectIdArea">
<logic:notEmpty name="adr170Form" property="adr170ProjectSid">
  <logic:iterate id="pjSid" name="adr170Form" property="adr170ProjectSid">
    <input type="hidden" name="adr170ProjectSid" value="<bean:write name="pjSid"/>">
  </logic:iterate>
</logic:notEmpty>
</div>

<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--　BODY -->

<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.112" />
    </li>
    <li>
      <div>
        <button type="submit" class="baseBtn" value="<gsmsg:write key="cmn.ok" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="adr170Form" property="adr160ProcMode" value="<%= edit %>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('list_back');">
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
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.target" />
      </th>
      <td class="w75">
        <logic:notEmpty name="adr170Form" property="adr170ContactUserComName">
          <span class="mr10"><bean:write name="adr170Form" property="adr170ContactUserComName" /></span>
        </logic:notEmpty>
        <bean:write name="adr170Form" property="adr170ContactUserName" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.title" /><span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
      </th>
      <td class="w75">
        <html:text maxlength="100" property="adr170title" styleClass="wp600" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.type" />
      </th>
      <td class="w75">
        <span class="verAlignMid">
          <html:radio styleId="adr170Mark_1" name="adr170Form" property="adr170Mark" value="<%= markTel %>" />
          <label for="adr170Mark_1">
            <span class="classic-display"><%= (java.lang.String) imgMapClassic.get(markTel) %></span>
            <span class="original-display"><%= (java.lang.String) imgMapOriginal.get(markTel) %></span>
            <%= (String) imgTextMap.get(markTel) %>
          </label>
          <html:radio styleClass="ml10" styleId="adr170Mark_2" name="adr170Form" property="adr170Mark" value="<%= markMail %>" />
          <label for="adr170Mark_2">
            <span class="classic-display"><%= (java.lang.String) imgMapClassic.get(markMail) %></span>
            <span class="original-display"><%= (java.lang.String) imgMapOriginal.get(markMail) %></span>
            <%= (String) imgTextMap.get(markMail) %>
          </label>
          <html:radio styleClass="ml10" styleId="adr170Mark_3" name="adr170Form" property="adr170Mark" value="<%= markWeb %>" />
          <label for="adr170Mark_3">
            <span class="classic-display"><%= (java.lang.String) imgMapClassic.get(markWeb) %></span>
            <span class="original-display"><%= (java.lang.String) imgMapOriginal.get(markWeb) %></span>
            <%= (String) imgTextMap.get(markWeb) %>
          </label>
          <html:radio styleClass="ml10" styleId="adr170Mark_4" name="adr170Form" property="adr170Mark" value="<%= markMeeting %>" />
          <label for="adr170Mark_4">
            <span class="classic-display"><%= (java.lang.String) imgMapClassic.get(markMeeting) %></span>
            <span class="original-display"><%= (java.lang.String) imgMapOriginal.get(markMeeting) %></span>
            <%= (String) imgTextMap.get(markMeeting) %>
          </label>
          <html:radio styleClass="ml10" styleId="adr170Mark_0" name="adr170Form" property="adr170Mark" value="<%= markOther %>" />
          <label for="adr170Mark_0">
            <gsmsg:write key="cmn.other" />
          </label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="address.114" />
      </th>
      <td>
        <div class="verAlignMid">
          <gsmsg:write key="cmn.start" />：
          <html:text name="adr170Form" property="adr170enterContactDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker"/>
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
            <html:text name="adr170Form" property="adr170enterContactTimeFr" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
        <div class="verAlignMid ml10">
          <gsmsg:write key="cmn.end" />：
          <html:text name="adr170Form" property="adr170enterContactDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          <span class="clockpicker_to ml5 pos_rel display_flex input-group">
            <html:text name="adr170Form" property="adr170enterContactTimeTo" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
        </div>
      </td>
    </tr>
    <logic:equal name="adr170Form" property="projectPluginKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_USE) %>">
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.project" />
        </th>
        <td class="w75">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.project" />" onclick="return openProjectWindow('adr170')">
            <gsmsg:write key="cmn.project" />
          </button>
          <logic:notEmpty name="adr170Form" property="adr170ProjectList">
            <logic:iterate id="prjData" name="adr170Form" property="adr170ProjectList">
              <div class="varAlignMid">
                <a href="#" onClick="deleteProject(<bean:write name="prjData" property="value" />);">
                  <span class="classic-display">
                    <img src="../common/images/classic/icon_delete_2.gif" alt="<gsmsg:write key="address.adr170.6" />">
                  </span>
                  <span class="original-display">
                    <img src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="address.adr170.6" />">
                  </span>
                </a>
                <bean:write name="prjData" property="label" />
              </div>
            </logic:iterate>
          </logic:notEmpty>
        </td>
      </tr>
    </logic:equal>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <textarea name="adr170biko" class="wp600"  rows="10" onkeyup="showLengthStr(value, <%= maxLengthBiko %>, 'inputlength');" id="inputstr"><bean:write name="adr170Form" property="adr170biko" /></textarea><br>
        <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthBiko %>&nbsp;<gsmsg:write key="cmn.character" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="schedule.117" />
      </th>
      <td class="w75">
        <ui:multiselector name="adr170Form" property="saveUserUI" styleClass="hp215" />
      </td>
    </tr>

    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.attached" />
      </th>
      <td>
        <% String tempDirId = "adr170"; %>
        <attachmentFile:filearea
            mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
            pluginId="<%= GSConstAddress.PLUGIN_ID_ADDRESS %>"
            tempDirId="<%= tempDirId %>" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="submit" class="baseBtn" value="<gsmsg:write key="cmn.ok" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="adr170Form" property="adr160ProcMode" value="<%= edit %>">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('delete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('list_back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>