<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.adr.GSConstAddress" %>
<!DOCTYPE html>
<%
    String maxLengthBiko = String.valueOf(GSConstAddress.MAX_LENGTH_ADR_BIKO);
%>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="address.adr020.1" /></title>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../address/css/freeze.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href="../address/css/address.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<theme:css filename="theme.css"/>

  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr020.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr240.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<% String closeScript = "companyWindowClose();"; %>
</head>

<body class="body_03" onload="viewchange();editchange();showLengthId($('#inputstr')[0], <%= maxLengthBiko %>, 'inputlength');" onunload="<%= closeScript %>">

<div id="FreezePane">

<html:form action="/address/adr020">

<input type="hidden" name="helpPrm" value="<bean:write name="adr020Form" property="adr020ProcMode" />">

<input type="hidden" name="CMD" value="">
<input type="hidden" name="winname" value="subbox">
<logic:notEmpty name="adr020Form" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr020Form" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<jsp:include page="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" />

<logic:notEmpty name="adr020Form" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr020Form" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr020Form" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr020Form" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020init" />
<html:hidden property="adr020ProcMode" />
<html:hidden property="adr020BackId" />
<html:hidden property="adr160pageNum1" />
<html:hidden property="adr160pageNum2" />
<html:hidden property="sortKey" />
<html:hidden property="orderKey" />
<html:hidden property="adrPosition" />

<html:hidden property="adrCopyFlg" />
<html:hidden property="adr020webmail" />

<html:hidden property="adr020selectCompany" />
<html:hidden property="adr020selectCompanyBase" />
<input type="hidden" name="adr020deleteLabel" value="">
<input type="hidden" name="adr100backFlg" value="1">
<input type="hidden" name="adr110editAcoSid" value="">
<input type="hidden" name="adr110ProcMode" value="0">

<logic:notEmpty name="adr020Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr020Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr020Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr020Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<span id="adr020labelArea">
<logic:notEmpty name="adr020Form" property="adr020label">
<logic:iterate id="label" name="adr020Form" property="adr020label">
  <input type="hidden" name="adr020label" value="<bean:write name="label" />">
</logic:iterate>
</logic:notEmpty>
</span>

<div id="adr020CompanyIdArea">
<logic:notEmpty name="adr020Form" property="adr020CompanySid">
  <logic:iterate id="coId" name="adr020Form" property="adr020CompanySid">
    <input type="hidden" name="adr020CompanySid" value="<bean:write name="coId"/>">
  </logic:iterate>
</logic:notEmpty>
</div>

<div id="adr020CompanyBaseIdArea">
<logic:notEmpty name="adr020Form" property="adr020CompanyBaseSid">
  <logic:iterate id="coId" name="adr020Form" property="adr020CompanyBaseSid">
    <input type="hidden" name="adr020CompanyBaseSid" value="<bean:write name="coId"/>">
  </logic:iterate>
</logic:notEmpty>
</div>

<logic:notEmpty name="adr020Form" property="projectCmbList">
<logic:iterate id="project" name="adr020Form" property="projectCmbList">
  <input type="hidden" name="projectCmbList" value="<bean:write name="project" />">
</logic:iterate>
</logic:notEmpty>
<jsp:include page="/WEB-INF/plugin/address/jsp/adr330_hiddenParams.jsp" >
<jsp:param value="adr020Form" name="thisFormName"/>
</jsp:include>

<% boolean callWebmail = true; %>
<logic:notEqual name="adr020Form" property="adr020webmail" value="1">
<jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
<% callWebmail = false; %>
</logic:notEqual>


<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="addressbook" />">
    </li>
    <li><gsmsg:write key="addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.adr020.1" />
    </li>
    <li>
      <div>
        <logic:equal name="adr020Form" property="adr020ProcMode" value="<%= String.valueOf(GSConstAddress.PROCMODE_EDIT) %>">
          <button type="button" value="<gsmsg:write key="cmn.register.copy" />" class="baseBtn" onClick="buttonCopy('<%= String.valueOf(GSConstAddress.PROCMODE_ADD) %>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy" />">
            <gsmsg:write key="cmn.register.copy" />
          </button>
        </logic:equal>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('registConfirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="adr020Form" property="adr020ProcMode" value="<%= String.valueOf(GSConstAddress.PROCMODE_EDIT) %>">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <% if (callWebmail) { %>
          <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onClick="window.parent.webmailEntrySubWindowClose();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
            <gsmsg:write key="cmn.close" />
          </button>
        <% } else { %>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backAdrList');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        <% } %>
      </div>
    </li>
  </ul>
</div>
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
<div class="wrapper">
  <table class="table-left">
    <tr>
      <th colspan="2" class="w25 no_w">
        <gsmsg:write key="cmn.name" /><span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
      </th>
      <td class="w75">
        <gsmsg:write key="cmn.lastname" /><html:text property="adr020unameSei" styleClass="ml5 wp300" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_SEI) %>" />
        <span class="ml10"><gsmsg:write key="cmn.name3" /></span><html:text styleClass="ml5 wp300" property="adr020unameMei" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_MEI) %>" />
      </td>
    </tr>
    <tr>
      <th colspan="2" class="w25 no_w">
        <gsmsg:write key="user.119" /><span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
      </th>
      <td class="w75">
        <gsmsg:write key="cmn.lastname" /><html:text property="adr020unameSeiKn" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_SEI_KN) %>" styleClass="ml5 wp300"/>
        <span class="ml10"><gsmsg:write key="cmn.name3" /></span><html:text property="adr020unameMeiKn" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_MEI_KN) %>" styleClass="ml5 wp300"/>
        <div>
          <gsmsg:write key="cmn.cmn160.2" />
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.select.company" />
      </th>
      <td class="w75">
        <gsmsg:write key="address.7" /> ： <bean:write name="adr020Form" property="adr020companyCode" />
        <div>
          <logic:notEmpty name="adr020Form" property="adr020companyName">
            <a href="#" onClick="return editCompany();" ><bean:write name="adr020Form" property="adr020companyName" /><span class="ml5"><bean:write name="adr020Form" property="adr020companyBaseName" /></span></a>
          </logic:notEmpty>
          <button type="button" value="<gsmsg:write key="cmn.search" />" class="baseBtn" onclick="return openCompanyWindow('adr020')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
          <logic:equal name="adr020Form" property="adr020addCompanyBtnFlg" value="1">
            <a href="#" onClick="buttonPush('addCompany');">
              <img class="classic-display ml10" src="../common/images/classic/icon_add_2.gif" alt="<gsmsg:write key="address.adr020.5" />">
              <img class="original-display ml10" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="address.adr020.5" />">
            </a>
          </logic:equal>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.affiliation" />
      </th>
      <td class="w75">
        <html:text property="adr020syozoku" maxlength="60" styleClass="wp650" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.post" />
      </th>
      <td class="w75">
        <html:select name="adr020Form" property="adr020position">
          <html:optionsCollection name="adr020Form" property="positionCmbList" value="value" label="label" />
        </html:select>
        <logic:equal name="adr020Form" property="adr020addPositionBtnFlg" value="1">
          <a href="#" onClick="return openpos();">
            <img class="classic-display ml10" src="../common/images/classic/icon_add_2.gif" alt="<gsmsg:write key="address.adr020.6" />">
            <img class="original-display ml10" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="address.adr020.6" />">
          </a>
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.mailaddress1" />
      </th>
      <td class="w75">
        <html:text property="adr020mail1" maxlength="256" styleClass="wp400" />
        <span class="ml10"><gsmsg:write key="cmn.comment" />：<html:text property="adr020mail1Comment" maxlength="10" styleClass="wp150" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.mailaddress2" />
      </th>
      <td class="w75">
        <html:text property="adr020mail2" maxlength="256" styleClass="wp400" />
        <span class="ml10"><gsmsg:write key="cmn.comment" />：<html:text property="adr020mail2Comment" maxlength="10" styleClass="wp150" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.mailaddress3" />
      </th>
      <td class="w75">
        <html:text property="adr020mail3" maxlength="256" styleClass="wp400" />
        <span class="ml10"><gsmsg:write key="cmn.comment" />：<html:text property="adr020mail3Comment" maxlength="10" styleClass="wp150" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.tel1" />
      </th>
      <td class="w40 no_w">
        <html:text property="adr020tel1" maxlength="20" styleClass="wp300" />
        <span class="ml10"><gsmsg:write key="address.58" />：<html:text property="adr020tel1Nai" maxlength="10" styleClass="wp150" /></span>
        <span class="ml10"><gsmsg:write key="cmn.comment" />：<html:text property="adr020tel1Comment" maxlength="10" styleClass="wp150" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.tel2" />
      </th>
      <td class="w75">
        <html:text property="adr020tel2" maxlength="20" styleClass="wp300" />
        <span class="ml10"><gsmsg:write key="address.58" />：<html:text property="adr020tel2Nai" maxlength="10" styleClass="wp150" /></span>
        <span class="ml10"><gsmsg:write key="cmn.comment" />：<html:text property="adr020tel2Comment" maxlength="10" styleClass="wp150" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.tel3" />
      </th>
      <td class="w75">
        <html:text property="adr020tel3" maxlength="20" styleClass="wp300" />
        <span class="ml10"><gsmsg:write key="address.58" />：<html:text property="adr020tel3Nai" maxlength="10" styleClass="wp150" /></span>
        <span class="ml10"><gsmsg:write key="cmn.comment" />：<html:text property="adr020tel3Comment" maxlength="10" styleClass="wp150" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="address.adr020.10" />
      </th>
      <td class="w75">
        <html:text property="adr020fax1" maxlength="20" styleClass="wp300" />
        <span class="ml10"><gsmsg:write key="cmn.comment" />：<html:text property="adr020fax1Comment" maxlength="10" styleClass="wp150" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="address.adr020.11" />
      </th>
      <td class="w75">
        <html:text property="adr020fax2" maxlength="20" styleClass="wp300" />
        <span class="ml10"><gsmsg:write key="cmn.comment" />：<html:text property="adr020fax2Comment" maxlength="10" styleClass="wp150" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="address.adr020.12" />
      </th>
      <td class="w75">
        <html:text property="adr020fax3" maxlength="20" styleClass="wp300" />
        <span class="ml10"><gsmsg:write key="cmn.comment" />：<html:text property="adr020fax3Comment" maxlength="10" styleClass="wp150" /></span>
      </td>
    </tr>
    <tr>
      <th class="w10 no_w" rowspan="4">
        <gsmsg:write key="cmn.address" />
      </th>
      <th class="w15 no_w">
        <gsmsg:write key="cmn.postalcode" />
      </th>
      <td class="w75">
        <html:text property="adr020postno1" maxlength="3" styleClass="wp60" />
        -
        <html:text property="adr020postno2" maxlength="4" styleClass="wp70" />
      </td>
    </tr>
    <tr>
      <th class="w15 no_w">
        <gsmsg:write key="cmn.prefectures" />
      </th>
      <td class="w75">
        <html:select name="adr020Form" property="adr020tdfk">
          <html:optionsCollection name="adr020Form" property="tdfkCmbList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w15 no_w">
        <gsmsg:write key="cmn.address1" />
      </th>
      <td class="w75"">
        <html:text property="adr020address1" maxlength="100" styleClass="wp650" />
      </td>
    </tr>
    <tr>
      <th class="w15 no_w">
        <gsmsg:write key="cmn.address2" />
      </th>
      <td class="w75">
        <html:text property="adr020address2" maxlength="100" tabindex="40" styleClass="wp650" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <textarea name="adr020biko" rows="8" class="wp650" onkeyup="showLengthStr(value, <%= maxLengthBiko %>, 'inputlength');" id="inputstr"><bean:write name="adr020Form" property="adr020biko" /></textarea>
        <div>
          <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>&nbsp;/&nbsp;<span class="formCounter_max"><%= maxLengthBiko %>&nbsp;<gsmsg:write key="cmn.character" /></span>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.label" />
      </th>
      <td class="w75" colspan="2">
        <button type="button" value="<gsmsg:write key="cmn.select.label" />" class="baseBtn" onClick="openlabel();">
          <gsmsg:write key="cmn.select.label" />
        </button>
        <logic:equal name="adr020Form" property="adr020addLabelBtnFlg" value="1">
          <a href="#" onClick="return openlabeladd();">
            <img class="classic-display ml10" src="../common/images/classic/icon_add_2.gif" alt="<gsmsg:write key="cmn.add.label2" />">
            <img class="original-display ml10" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add.label2" />">
          </a>
        </logic:equal>
        <logic:notEmpty name="adr020Form" property="selectLabelList">
            <logic:iterate id="labelData" name="adr020Form" property="selectLabelList" indexId="idx">
              <div>
                <span class="verAlignMid">
                  <img class="classic-display mr5 cursor_p" src="../common/images/classic/icon_delete_2.gif" alt="<gsmsg:write key="cmn.delete" />" onClick="deleteLabel('<bean:write name="labelData" property="albSid" />');">
                  <img class="original-display mr5 cursor_p" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />" onClick="deleteLabel('<bean:write name="labelData" property="albSid" />');">
                  <bean:write name="labelData" property="albName" />
                </span>
              </div>
            </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th colspan="2" class="w25 no_w">
        <gsmsg:write key="address.46" />
      </th>
      <td colspan="2" class="w75">
        <ui:usrgrpselector name="adr020Form" property="adr020tantoListUI" styleClass="hp215" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="address.61" /><span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
      </th>
      <td class="w75" colspan="2">
        <div class="verAlignMid">
          <html:radio property="adr020permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_OWN) %>" styleId="view0" onclick="viewchange();"  /><label for="view0"><gsmsg:write key="address.62" /></label>
          <html:radio styleClass="ml10" property="adr020permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_GROUP) %>" styleId="view1" onclick="viewchange();" /><label for="view1"><gsmsg:write key="group.designation" /></label>
          <html:radio styleClass="ml10" property="adr020permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_USER) %>" styleId="view2" onclick="viewchange();"  /><label for="view2"><gsmsg:write key="cmn.user.specified" /></label>
          <html:radio styleClass="ml10" property="adr020permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_NORESTRICTION) %>" styleId="view3" onclick="viewchange();"  /><label for="view3"><gsmsg:write key="cmn.no.setting.permission" /></label>
        </div>
        <div id="viewgroup">
          <ui:usrgrpselector name="adr020Form" property="adr020permitViewGroupUI" styleClass="hp215" />
        </div>

        <div id="viewuser">
          <ui:usrgrpselector name="adr020Form" property="adr020permitViewUserUI" styleClass="hp215" />
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.edit.permissions" /><span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
      </th>
      <td class="W75" colspan="2">
        <div id="editselect" class="verAlignMid">
          <html:radio property="adr020permitEdit" value="<%= String.valueOf(GSConstAddress.EDITPERMIT_OWN) %>" styleId="edit0" onclick="editchange();" tabindex="62" /><label for="edit0"><gsmsg:write key="address.62" /></label>
          <html:radio styleClass="ml10" property="adr020permitEdit" value="<%= String.valueOf(GSConstAddress.EDITPERMIT_GROUP) %>" styleId="edit1" onclick="editchange();" tabindex="63" /><label for="edit1"><gsmsg:write key="group.designation" /></label>
          <html:radio styleClass="ml10" property="adr020permitEdit" value="<%= String.valueOf(GSConstAddress.EDITPERMIT_USER) %>" styleId="edit2" onclick="editchange();" tabindex="64" /><label for="edit2"><gsmsg:write key="cmn.user.specified" /></label>
          <html:radio styleClass="ml10" property="adr020permitEdit" value="<%= String.valueOf(GSConstAddress.EDITPERMIT_NORESTRICTION) %>" styleId="edit3" onclick="editchange();" tabindex="65" /><label for="edit3"><gsmsg:write key="cmn.no.setting.permission" /></label>
        </div>
        <div id="editselectstr">
        </div>
        <div id="editgroup">
          <ui:usrgrpselector name="adr020Form" property="adr020permitEditGroupUI" styleClass="hp215" />
        </div>
        <div id="edituser">
          <ui:usrgrpselector name="adr020Form" property="adr020permitEditUserUI" styleClass="hp215" />
        </div>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <logic:equal name="adr020Form" property="adr020ProcMode" value="<%= String.valueOf(GSConstAddress.PROCMODE_EDIT) %>">
      <button type="button" value="<gsmsg:write key="cmn.register.copy" />" class="baseBtn" onClick="buttonCopy('<%= String.valueOf(GSConstAddress.PROCMODE_ADD) %>');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy" />">
        <gsmsg:write key="cmn.register.copy" />
      </button>
    </logic:equal>
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('registConfirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="adr020Form" property="adr020ProcMode" value="<%= String.valueOf(GSConstAddress.PROCMODE_EDIT) %>">
      <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <% if (callWebmail) { %>
      <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onClick="window.parent.webmailEntrySubWindowClose();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <gsmsg:write key="cmn.close" />
      </button>
    <% } else { %>
      <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backAdrList');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    <% } %>
  </div>
</div>
<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
</html:form>


</div>

<div id="subPanel" class="display_n p0 txt_c">
  <div class="bd"><iframe src="../common/html/damy.html" class="wp400 border_none hp120 mt10" id="posDialog" name="pos"></iframe></div>
</div>

<div id="labelAddPanel" class="display_n p0 txt_c"">
  <div class="bd"><iframe src="../common/html/damy.html" name="labadd" class="wp550 border_none hp200 mt10"></iframe></div>
</div>

<div id="labelPanel" class="display_n p0 txt_c"">
  <div class="bd"><iframe src="../common/html/damy.html" name="lab" class="wp500 hp300 border_none mt10"></iframe></div>
</div>

</body>
</html:html>