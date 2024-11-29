<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<%
  int add = jp.groupsession.v2.adr.GSConstAddress.PROCMODE_ADD;
  int edit = jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT;
%>

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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="address.adr160.1" /></title>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr160.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body class="body_03">
<html:form action="/address/adr160">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<input type="hidden" name="CMD" value="">
<input type="hidden" name="adr160EditSid">
<input type="hidden" name="adr160ProcMode">

<html:hidden name="adr160Form" property="adr010EditAdrSid" />
<html:hidden name="adr160Form" property="adr020ProcMode" />
<html:hidden name="adr160Form" property="adr020BackId" />
<html:hidden name="adr160Form" property="sortKey" />
<html:hidden name="adr160Form" property="orderKey" />

<logic:notEmpty name="adr160Form" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr160Form" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr160Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr160Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr160Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr160Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.adr160.1" />
    </li>
    <li>
      <div>

        <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="return buttonSubmit('adr160add', '<%= add %>', '-1');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <logic:notEqual name="adr160Form" property="adr160dataExist" value="0" >
          <logic:notEqual name="adr160Form" property="adr160exportPower" value="0" >
            <button type="button" value="<gsmsg:write key="cmn.export" />" class="baseBtn" onClick="buttonPush('csv');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
              <gsmsg:write key="cmn.export" />
            </button>
          </logic:notEqual>
        </logic:notEqual>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('adr160back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <div class="wrapperContent-top txt_l font-bold">
    <bean:write name="adr160Form" property="adr160kaisya" />
    <a href="#" class="ml10" onClick="return editAddress('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT) %>', '<bean:write name="adr160Form" property="adr010EditAdrSid" />')">
      <bean:write name="adr160Form" property="adr160simei" />
    </a>
  </div>
  <div class="wrapperContent-2column w100">
    <div class="w60">
      <bean:size id="count1" name="adr160Form" property="adr160PageLabel" scope="request" />
      <logic:greaterThan name="count1" value="1">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo"  property="adr160pageNum1" onchange="changePage(1);">
            <html:optionsCollection name="adr160Form" property="adr160PageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('next');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>
      <logic:equal name="adr160Form" property="adr160dataExist" value="0" >
        <span class="cl_fontWarn"><gsmsg:write key="address.adr160.2" /></span>
      </logic:equal>

      <table class="table-top">
        <logic:notEqual name="adr160Form" property="adr160dataExist" value="0" >
          <tr>
            <th class="w25">
              <logic:equal name="adr160Form" property="sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_DATE) %>">
                <logic:equal name="adr160Form" property="orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
                  <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_DATE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>');">
                    <span class="classic-display"><gsmsg:write key="cmn.date" />▲</span>
                    <span class="original-display"><gsmsg:write key="cmn.date" /><i class="icon-sort_up"></i></span>
                  </a>
                </logic:equal>
                <logic:equal name="adr160Form" property="orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
                  <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_DATE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>');">
                    <span class="classic-display"><gsmsg:write key="cmn.date" />▼</span>
                    <span class="original-display"><gsmsg:write key="cmn.date" /><i class="icon-sort_down"></i></span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="adr160Form" property="sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_DATE) %>">
                <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_DATE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>');">
                  <gsmsg:write key="cmn.date" />
                </a>
              </logic:notEqual>
            </th>
            <th class="w15">
              <logic:equal name="adr160Form" property="sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_SYUBETU) %>">
                <logic:equal name="adr160Form" property="orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
                  <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_SYUBETU) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>');">
                    <span class="classic-display"><gsmsg:write key="cmn.type" />▲</span>
                    <span class="original-display"><gsmsg:write key="cmn.type" /><i class="icon-sort_up"></i></span>
                  </a>
                </logic:equal>
                <logic:equal name="adr160Form" property="orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
                  <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_SYUBETU) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>');">
                    <span class="classic-display"><gsmsg:write key="cmn.type" />▼</span>
                    <span class="original-display"><gsmsg:write key="cmn.type" /><i class="icon-sort_down"></i></span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="adr160Form" property="sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_SYUBETU) %>">
                <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_SYUBETU) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>');">
                  <gsmsg:write key="cmn.type" />
                </a>
              </logic:notEqual>
            </th>
            <th class="w45">
              <logic:equal name="adr160Form" property="sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_TITLE) %>">
                <logic:equal name="adr160Form" property="orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
                  <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_TITLE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>');">
                    <span class="classic-display"><gsmsg:write key="cmn.title" />▲</span>
                    <span class="original-display"><gsmsg:write key="cmn.title" /><i class="icon-sort_up"></i></span>
                  </a>
                </logic:equal>
                <logic:equal name="adr160Form" property="orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
                  <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_TITLE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>');">
                    <span class="classic-display"><gsmsg:write key="cmn.title" />▼</span>
                    <span class="original-display"><gsmsg:write key="cmn.title" /><i class="icon-sort_down"></i></span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="adr160Form" property="sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_TITLE) %>">
                <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_TITLE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>');">
                  <gsmsg:write key="cmn.title" />
                </a>
              </logic:notEqual>
            </th>
            <th class="w15">
              <logic:equal name="adr160Form" property="sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_ADDUSER) %>">
                <logic:equal name="adr160Form" property="orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
                  <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_ADDUSER) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>');">
                    <span class="classic-display"><gsmsg:write key="cmn.registant" />▲</span>
                    <span class="original-display"><gsmsg:write key="cmn.registant" /><i class="icon-sort_up"></i></span>
                  </a>
                </logic:equal>
                <logic:equal name="adr160Form" property="orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
                  <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_ADDUSER) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>');">
                    <span class="classic-display"><gsmsg:write key="cmn.registant" />▼</span>
                    <span class="original-display"><gsmsg:write key="cmn.registant" /><i class="icon-sort_down"></i></span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="adr160Form" property="sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_ADDUSER) %>">
                <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.CONTACT_SORT_ADDUSER) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>');">
                  <gsmsg:write key="cmn.registant" />
                </a>
              </logic:notEqual>
            </th>
          </tr>
        </logic:notEqual>
        <logic:notEmpty name="adr160Form" property="adr160contactList">
          <logic:iterate id="conMdl" name="adr160Form" property="adr160contactList" indexId="idx">
            <tr class="js_listHover cursor_p" id="<bean:write name="conMdl" property="adcSid" />">
              <td class="txt_c">
                <bean:write name="conMdl" property="adcCttimeDsp" />
              </td>
              <td class="txt_c">
                <bean:define id="imgMark"><bean:write name="conMdl" property="adcType" /></bean:define>
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
              <td class="cl_linkDef">
                <bean:write name="conMdl" property="adcTitle" />
              </td>
              <td>
                <logic:notEqual name="conMdl" property="adcAddusrJkbn" value="0">
                  <del><bean:write name="conMdl" property="adcAdduserDspSei" /><span class="ml5"><bean:write name="conMdl" property="adcAdduserDspMei" /></span></del>
                </logic:notEqual>
                <logic:equal name="conMdl" property="adcAddusrJkbn" value="0">
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal name="conMdl" property="adcAddUsrUkoFlg" value="1">
                    <bean:define id="mukoUserClass" value="mukoUser" />
                  </logic:equal>
                  <span class="<%=mukoUserClass%>"><bean:write name="conMdl" property="adcAdduserDspSei" /><span class="ml5"><bean:write name="conMdl" property="adcAdduserDspMei" /></span></span>
                </logic:equal>
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>
      <logic:greaterThan name="count1" value="1">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo"  property="adr160pageNum2" onchange="changePage(2);">
            <html:optionsCollection name="adr160Form" property="adr160PageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('next');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>
    </div>
    <div class="w40 ml10">
      <table class="table-left">
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="cmn.name" />
          </th>
          <td class="w75">
            <a href="#" onClick="return editAddress('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT) %>', '<bean:write name="adr160Form" property="adr010EditAdrSid" />')">
              <bean:write name="adr160Form" property="adr160simei" />
            </a>
          </td>
        </tr>
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="user.119" />
          </th>
          <td>
            <bean:write name="adr160Form" property="adr160simeikana" />
          </td>
        </tr>
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="address.139" />
          </th>
          <td>
            <bean:write name="adr160Form" property="adr160kaisya" />
            <div>
              <bean:write name="adr160Form" property="adr160kaisyakyoten" />
            </div>
          </td>
        </tr>
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="cmn.affiliation" />
          </th>
          <td>
            <bean:write name="adr160Form" property="adr160syozoku" />
          </td>
        </tr>
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="cmn.post" />
          </th>
          <td>
            <bean:write name="adr160Form" property="adr160yakusyoku" />
          </td>
        </tr>
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="cmn.mailaddress1" />
          </th>
          <td>
            <a href="mailto:<bean:write name="adr160Form" property="adr160MailAddress1" />"><bean:write name="adr160Form" property="adr160MailAddress1" /></a>
            <logic:notEmpty name="adr160Form" property="adr160MailComment1"><br>
              <gsmsg:write key="cmn.comment" />：<bean:write name="adr160Form" property="adr160MailComment1" />
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="cmn.mailaddress2" />
          </th>
          <td>
            <a href="mailto:<bean:write name="adr160Form" property="adr160MailAddress2" />"><bean:write name="adr160Form" property="adr160MailAddress2" /></a>
            <logic:notEmpty name="adr160Form" property="adr160MailComment2"><br>
             <gsmsg:write key="cmn.comment" />：<bean:write name="adr160Form" property="adr160MailComment2" />
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="cmn.mailaddress3" />
          </th>
          <td>
            <a href="mailto:<bean:write name="adr160Form" property="adr160MailAddress3" />"><bean:write name="adr160Form" property="adr160MailAddress3" /></a>
              <logic:notEmpty name="adr160Form" property="adr160MailComment3"><br>
                <gsmsg:write key="cmn.comment" />：<bean:write name="adr160Form" property="adr160MailComment3" />
              </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="cmn.tel1" />
          </th>
          <td>
            <bean:write name="adr160Form" property="adr160Tel1" />
            <logic:notEmpty name="adr160Form" property="adr160TelNaisen1"><br>
              <gsmsg:write key="address.58" />：<bean:write name="adr160Form" property="adr160TelNaisen1" />
            </logic:notEmpty>
            <logic:notEmpty name="adr160Form" property="adr160TelComment1"><br>
              <gsmsg:write key="cmn.comment" />：<bean:write name="adr160Form" property="adr160TelComment1" />
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="cmn.tel2" />
          </th>
          <td>
            <bean:write name="adr160Form" property="adr160Tel2" />
            <logic:notEmpty name="adr160Form" property="adr160TelNaisen2"><br>
              <gsmsg:write key="address.58" />：<bean:write name="adr160Form" property="adr160TelNaisen2" />
            </logic:notEmpty>
            <logic:notEmpty name="adr160Form" property="adr160TelComment2"><br>
              <gsmsg:write key="cmn.comment" />：<bean:write name="adr160Form" property="adr160TelComment2" />
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="cmn.tel3" />
          </th>
          <td>
            <bean:write name="adr160Form" property="adr160Tel3" />
            <logic:notEmpty name="adr160Form" property="adr160TelNaisen3"><br>
              <gsmsg:write key="address.58" />：<bean:write name="adr160Form" property="adr160TelNaisen3" />
            </logic:notEmpty>
            <logic:notEmpty name="adr160Form" property="adr160TelComment3"><br>
              <gsmsg:write key="cmn.comment" />：<bean:write name="adr160Form" property="adr160TelComment3" />
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="address.adr020.10" />
          </th>
          <td>
            <bean:write name="adr160Form" property="adr160Fax1" />
            <logic:notEmpty name="adr160Form" property="adr160FaxComment1"><br>
              <gsmsg:write key="cmn.comment" />：<bean:write name="adr160Form" property="adr160FaxComment1" />
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="address.adr020.11" />
          </th>
          <td>
            <bean:write name="adr160Form" property="adr160Fax2" />
            <logic:notEmpty name="adr160Form" property="adr160FaxComment2"><br>
              <gsmsg:write key="cmn.comment" />：<bean:write name="adr160Form" property="adr160FaxComment2" />
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="address.adr020.12" />
          </th>
          <td>
            <bean:write name="adr160Form" property="adr160Fax3" />
            <logic:notEmpty name="adr160Form" property="adr160FaxComment3"><br>
              <gsmsg:write key="cmn.comment" />：<bean:write name="adr160Form" property="adr160FaxComment3" />
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="cmn.address" />
          </th>
          <td>
            <logic:notEmpty name="adr160Form" property="adr160PostNo">
              <div>
                〒<bean:write name="adr160Form" property="adr160PostNo" />
              </div>
            </logic:notEmpty>
            <logic:notEmpty name="adr160Form" property="adr160TdfName">
              <bean:write name="adr160Form" property="adr160TdfName" />
            </logic:notEmpty>
            <logic:notEmpty name="adr160Form" property="adr160Address1">
              <bean:write name="adr160Form" property="adr160Address1" />
            </logic:notEmpty>
            <logic:notEmpty name="adr160Form" property="adr160Address2">
              <bean:write name="adr160Form" property="adr160Address2" />
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="cmn.memo" />
          </th>
          <td>
            <bean:write name="adr160Form" property="adr160Biko" filter="false" />
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="cmn.staff" />
          </th>
          <td>
            <logic:notEmpty name="adr160Form" property="adr160TantoUserName">
              <logic:iterate id="tanto" name="adr160Form" property="adr160TantoUserName" indexId="idx">
                <bean:define id="mukoUserClass" value=""/>
                <logic:equal value="1" name="tanto" property="usrUkoFlg"><bean:define id="mukoUserClass" value="mukoUser"/></logic:equal>
                <span class="<%=mukoUserClass%>"><bean:write name="tanto" property="label" /></span><br>
              </logic:iterate>
            </logic:notEmpty>
          </td>
        </tr>
      </table>
      <logic:notEqual name="adr160Form" property="adr160labelExist" value="0" >
        <table class="table-top">
          <tr>
            <th class="w100">
              <gsmsg:write key="cmn.label" />
            </th>
          </tr>
          <logic:notEmpty name="adr160Form" property="adr160labelList">
            <logic:iterate id="labelMdl" name="adr160Form" property="adr160labelList" indexId="idx">
              <tr>
                <td>
                  <bean:write name="labelMdl" property="albName" />
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </table>
      </logic:notEqual>
    </div>
  </div>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>