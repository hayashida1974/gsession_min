<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<% String edit = String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT); %>

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
  imgTextMap.put(markOther, msgOther);

  imgTextMap.put("none", "&nbsp;");
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="address.6" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr161.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/components/file-preview/index.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body class="body_03">
<html:form action="/address/adr161">

<input type="hidden" name="CMD" value="">
<input type="hidden" name="adr161TmpFileId" value="">
<html:hidden property="seniFlg" />
<html:hidden property="adr160ProcMode" />
<html:hidden property="adr160EditSid" />
<html:hidden property="adr160pageNum1" />
<html:hidden property="adr160pageNum2" />


<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--　BODY -->
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../ringi/images/classic/header_ringi.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.6" />
    </li>
    <li>
      <div>
        <logic:equal name="adr161Form" property="adr161EditFlg" value="0">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onClick="buttonPush('edit');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
            <gsmsg:write key="cmn.edit" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('adr161_back');">
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
        <logic:notEmpty name="adr161Form" property="adr161ContactUserComName">
          <span class="mr10"><bean:write name="adr161Form" property="adr161ContactUserComName" /></span>
        </logic:notEmpty>
        <bean:write name="adr161Form" property="adr161ContactUserName" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.title" />
      </th>
      <td class="w75">
        <bean:write name="adr161Form" property="adr161title" />
      </td>
    </tr>
    <bean:define id="imgMark"><bean:write name="adr161Form" property="adr161Mark" /></bean:define>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.type" />
      </th>
      <td class="w75">
        <% java.lang.String key = "none";
           if (imgMapClassic.containsKey(imgMark)) {
             key = imgMark;
           }
        %>
        <span class="classic-display"><%= (java.lang.String) imgMapClassic.get(key) %></span>
        <span class="original-display"><%= (java.lang.String) imgMapOriginal.get(key) %></span>
        <% java.lang.String txtkey = "none";
           if (imgTextMap.containsKey(imgMark)) {
             txtkey = imgMark;
           }
        %>
        <%= (java.lang.String) imgTextMap.get(txtkey) %>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="address.114" />
      </th>
      <td class="w75">
        <bean:write name="adr161Form" property="adr161ContactFrom" />
        <span class="ml5 mr5"><gsmsg:write key="tcd.153" /></span>
        <bean:write name="adr161Form" property="adr161ContactTo" />
      </td>
    </tr>
    <logic:equal name="adr161Form" property="projectPluginKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_USE) %>">
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.project" />
        </th>
        <td class="w75">
          <logic:notEmpty name="adr161Form" property="adr161ProjectList">
            <logic:iterate id="prjData" name="adr161Form" property="adr161ProjectList">
              <div><bean:write name="prjData" property="label" /></div>
            </logic:iterate>
          </logic:notEmpty>
        </td>
      </tr>
    </logic:equal>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w75">
        <bean:write name="adr161Form" property="adr161biko" filter="false" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="schedule.117" />
      </th>
      <td class="w75">
        <logic:notEmpty name="adr161Form" property="adr161DoujiUser">
          <logic:iterate id="doziUser" name="adr161Form" property="adr161DoujiUser" scope="request">
            <div>
              <bean:write name="doziUser" property="adrSei" />
              <span class="ml10"><bean:write name="doziUser" property="adrMei" /></span>
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.attached" />
      </th>
      <td class="w75">
        <logic:notEmpty name="adr161Form" property="tmpFileList" scope="request">
          <logic:iterate id="fileMdl" name="adr161Form" property="tmpFileList" scope="request">
            <file-preview
              url="../address/adr161.do?CMD=fileDownload&adr010EditAdrSid=<bean:write name="adr161Form" property="adr010EditAdrSid"/>&adr160EditSid=<bean:write name="adr161Form" property="adr160EditSid"/>&adr161TmpFileId=<bean:write name="fileMdl" property="binSid"/>"
              filename="<bean:write name="fileMdl" property="binFileName"/>">
              <span class="classic-display"><img src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.file"/>"></span>
              <span class="original-display"><img src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.file"/>"></span>
              <a href="#!" onClick="return fileLinkClick('<bean:write name="fileMdl" property="binSid"/>');">
                <bean:write name="fileMdl" property="binFileName"/>
                <bean:write name="fileMdl" property="binFileSizeDsp"/>
              </a>
              <br>
            </file-preview>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <logic:equal name="adr161Form" property="adr161EditFlg" value="0">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onClick="buttonPush('edit');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
        <gsmsg:write key="cmn.edit" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('adr161_back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>
<iframe type="hidden" src="../common/html/damy.html" style="display: none" name="navframe"></iframe>
</body>
</html:html>