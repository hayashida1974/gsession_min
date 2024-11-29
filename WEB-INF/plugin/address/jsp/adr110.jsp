<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<%
    String maxLengthBiko = String.valueOf(jp.groupsession.v2.adr.GSConstAddress.MAX_LENGTH_ADR_BIKO);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="address.adr110.1" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adrComPopUp.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body class="body_03" onunload="windowClose();" onload="showLengthId($('#inputstr1')[0], <%= maxLengthBiko %>, 'inputlength');showLengthId($('#inputstr2')[0], <%= maxLengthBiko %>, 'inputlength2');">

<html:form action="/address/adr110">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<logic:notEmpty name="adr110Form" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr110Form" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr110Form" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr110Form" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr110Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr110Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr110Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr110Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<input type="hidden" name="CMD" value="">
<input type="hidden" name="helpPrm" value="<bean:write name="adr110Form" property="adr110ProcMode" />">
<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>
<jsp:include page="/WEB-INF/plugin/address/jsp/adr330_hiddenParams.jsp" >
<jsp:param value="adr110Form" name="thisFormName"/>
</jsp:include>

<html:hidden property="adr020webmail" />

<html:hidden property="adr020init" />
<html:hidden property="adr020ProcMode" />
<html:hidden property="adr020unameSei" />
<html:hidden property="adr020unameMei" />
<html:hidden property="adr020unameSeiKn" />
<html:hidden property="adr020unameMeiKn" />
<html:hidden property="adr020selectCompany" />
<html:hidden property="adr020selectCompanyBase" />
<html:hidden property="adr020syozoku" />
<html:hidden property="adr020position" />
<html:hidden property="adr020mail1" />
<html:hidden property="adr020mail1Comment" />
<html:hidden property="adr020mail2" />
<html:hidden property="adr020mail2Comment" />
<html:hidden property="adr020mail3" />
<html:hidden property="adr020mail3Comment" />
<html:hidden property="adr020tel1" />
<html:hidden property="adr020tel1Nai" />
<html:hidden property="adr020tel1Comment" />
<html:hidden property="adr020tel2" />
<html:hidden property="adr020tel2Nai" />
<html:hidden property="adr020tel2Comment" />
<html:hidden property="adr020tel3" />
<html:hidden property="adr020tel3Nai" />
<html:hidden property="adr020tel3Comment" />
<html:hidden property="adr020fax1" />
<html:hidden property="adr020fax1Comment" />
<html:hidden property="adr020fax2" />
<html:hidden property="adr020fax2Comment" />
<html:hidden property="adr020fax3" />
<html:hidden property="adr020fax3Comment" />
<html:hidden property="adr020postno1" />
<html:hidden property="adr020postno2" />
<html:hidden property="adr020tdfk" />
<html:hidden property="adr020address1" />
<html:hidden property="adr020address2" />
<html:hidden property="adr020biko" />

<logic:notEmpty name="adr110Form" property="adr020tantoList">
<logic:iterate id="tantoList" name="adr110Form" property="adr020tantoList">
  <input type="hidden" name="adr020tantoList" value="<bean:write name="tantoList" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr110Form" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr110Form" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr110Form" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr110Form" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr110Form" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr110Form" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020tantoGroup" />

<logic:notEmpty name="adr110Form" property="adr020label">
<logic:iterate id="label" name="adr110Form" property="adr020label">
  <input type="hidden" name="adr020label" value="<bean:write name="label" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitView" />

<logic:notEmpty name="adr110Form" property="adr020permitViewGroup">
<logic:iterate id="permitViewGroup" name="adr110Form" property="adr020permitViewGroup">
  <input type="hidden" name="adr020permitViewGroup" value="<bean:write name="permitViewGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr110Form" property="adr020permitViewUser">
<logic:iterate id="permitViewUser" name="adr110Form" property="adr020permitViewUser">
  <input type="hidden" name="adr020permitViewUser" value="<bean:write name="permitViewUser" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitViewUserGroup" />
<html:hidden property="adr020permitEdit" />

<logic:notEmpty name="adr110Form" property="adr020permitEditGroup">
<logic:iterate id="permitEditGroup" name="adr110Form" property="adr020permitEditGroup">
  <input type="hidden" name="adr020permitEditGroup" value="<bean:write name="permitEditGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr110Form" property="adr020permitEditUser">
<logic:iterate id="permitEditUser" name="adr110Form" property="adr020permitEditUser">
  <input type="hidden" name="adr020permitEditUser" value="<bean:write name="permitEditUser" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitEditUserGroup" />
<html:hidden property="adr100backFlg" />

<html:hidden property="adr110ProcMode" />
<html:hidden property="adr110editAcoSid" />
<html:hidden property="adr110initFlg" />
<html:hidden property="adr100searchFlg" />
<html:hidden property="adr100page" />
<html:hidden property="adr100pageTop" />
<html:hidden property="adr100pageBottom" />
<html:hidden property="adr100code" />
<html:hidden property="adr100coName" />
<html:hidden property="adr100coNameKn" />
<html:hidden property="adr100coBaseName" />
<html:hidden property="adr100atiSid" />
<html:hidden property="adr100tdfk" />
<html:hidden property="adr100biko" />
<html:hidden property="adr100svCode" />
<html:hidden property="adr100svCoName" />
<html:hidden property="adr100svCoNameKn" />
<html:hidden property="adr100svCoBaseName" />
<html:hidden property="adr100svAtiSid" />
<html:hidden property="adr100svTdfk" />
<html:hidden property="adr100svBiko" />
<html:hidden property="adr100SortKey" />
<html:hidden property="adr100OrderKey" />
<html:hidden property="adr100SearchKana" />
<html:hidden property="adr100mode" />

<logic:notEmpty name="adr110Form" property="abaListToList">
<logic:iterate id="abaData" name="adr110Form" property="abaListToList" indexId="idx">
<input type="hidden" name="abaList[<%= String.valueOf(idx.intValue()) %>].adr110abaIndex" value="<bean:write name="abaData" property="adr110abaIndex" />">
<input type="hidden" name="abaList[<%= String.valueOf(idx.intValue()) %>].adr110abaSidDetail" value="<bean:write name="abaData" property="adr110abaSidDetail" />">
<input type="hidden" name="abaList[<%= String.valueOf(idx.intValue()) %>].adr110abaTypeDetail" value="<bean:write name="abaData" property="adr110abaTypeDetail" />">
<input type="hidden" name="abaList[<%= String.valueOf(idx.intValue()) %>].adr110abaName" value="<bean:write name="abaData" property="adr110abaName" />">
<input type="hidden" name="abaList[<%= String.valueOf(idx.intValue()) %>].adr110abaPostno1" value="<bean:write name="abaData" property="adr110abaPostno1" />">
<input type="hidden" name="abaList[<%= String.valueOf(idx.intValue()) %>].adr110abaPostno2" value="<bean:write name="abaData" property="adr110abaPostno2" />">
<input type="hidden" name="abaList[<%= String.valueOf(idx.intValue()) %>].adr110abaTdfk" value="<bean:write name="abaData" property="adr110abaTdfk" />">
<input type="hidden" name="abaList[<%= String.valueOf(idx.intValue()) %>].adr110abaAddress1" value="<bean:write name="abaData" property="adr110abaAddress1" />">
<input type="hidden" name="abaList[<%= String.valueOf(idx.intValue()) %>].adr110abaAddress2" value="<bean:write name="abaData" property="adr110abaAddress2" />">
<input type="hidden" name="abaList[<%= String.valueOf(idx.intValue()) %>].adr110abaBiko" value="<bean:write name="abaData" property="adr110abaBiko" />">
</logic:iterate>
</logic:notEmpty>

<input type="hidden" name="adr110deleteCompanyBaseIndex" value="">
<input type="hidden" name="adr111editCompanyBaseIndex" value="">
<html:hidden name="adr110Form" property="adr110BackId" />





<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.adr110.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="okPush('confirmCompany');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="adr110Form" property="adr110ProcMode" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT) %>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteCompany');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <logic:equal name="adr110Form" property="adr100backFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInputAddress');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>
        <logic:notEqual name="adr110Form" property="adr100backFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backCompanyList');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:notEqual>
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
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="address.7" /><span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
      </th>
      <td class="w75">
        <html:text property="adr110coCode" maxlength="20" styleClass="wp300" />
        <button type="button" class="baseBtn" value="<gsmsg:write key="address.adr110.2" />" onclick="return openComWindow();">
          <gsmsg:write key="address.adr110.2" />
        </button>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.company.name" /><span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
      </th>
      <td class="w75">
        <html:text property="adr110coName" maxlength="50" styleClass="wp500" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.company.name" />(<gsmsg:write key="cmn.kana" />)<span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
      </th>
      <td class="w75">
        <html:text property="adr110coNameKn" maxlength="100" styleClass="wp500" />
        <div>
          *<gsmsg:write key="cmn.enter.kana.zenkaku" />
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="address.103" />
      </th>
      <td>
        <ui:multiselector name="adr110Form" property="adr110atiListUI" styleClass="hp215" />
      </td>
    </tr>

    <tr>
      <th rowspan="4" class="no_w w10">
        <gsmsg:write key="cmn.address" />
      </th>
      <th class="no_w w15">
        <gsmsg:write key="cmn.postalcode" />
      </th>
      <td>
        <html:text property="adr110coPostno1" maxlength="3" styleClass="wp50" />
        -
        <html:text property="adr110coPostno2" maxlength="4" styleClass="wp60" />
      </td>
    </tr>
    <tr>
      <th class="no_w">
        <gsmsg:write key="cmn.prefectures" />
      </th>
      <td>
        <logic:notEmpty  name="adr110Form" property="tdfkCmbList">
          <html:select name="adr110Form" property="adr110coTdfk">
            <html:optionsCollection name="adr110Form" property="tdfkCmbList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="no_w">
        <gsmsg:write key="cmn.address1" />
      </th>
      <td>
        <html:text property="adr110coAddress1" maxlength="100" styleClass="wp500" />
      </td>
    </tr>
    <tr>
      <th class="no_w">
        <gsmsg:write key="cmn.address2" />
      </th>
      <td>
        <html:text property="adr110coAddress2" maxlength="100" styleClass="wp500" />
      </td>
    </tr>


    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.url" />
      </th>
      <td>
        <html:text property="adr110url" maxlength="100" styleClass="wp600" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w" colspan="2">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <textarea name="adr110biko" rows="5" class="wp500" onkeyup="showLengthStr(value, <%= maxLengthBiko %>, 'inputlength');" id="inputstr1" /><bean:write name="adr110Form" property="adr110biko" /></textarea>
        <div>
          <span class="formCounter">
            <gsmsg:write key="cmn.current.characters" />:
          </span>
          <span id="inputlength" class="formCounter">
            0
          </span>&nbsp;
          <span class="formCounter_max">
            /&nbsp;<%= maxLengthBiko %>&nbsp;<gsmsg:write key="cmn.character" />
          </span>
        </div>
      </td>
    </tr>
  </table>

  <logic:notEmpty name="adr110Form" property="abaListToList">
    <logic:iterate id="abaData" name="adr110Form" property="abaListToList">
      <table class="table-left mt0">
        <tr class="bgC_lightGray">
          <td class="w50 border_right_none" colspan="2">
            <span class="classic-display"><img src="../address/images/classic/icon_parson.gif" alt="<gsmsg:write key="address.106" />"></span>
            <span class="original-display"><img src="../common/images/original/icon_company.png" alt="<gsmsg:write key="address.106" />"></span>
            <logic:equal name="abaData" property="adr110abaTypeNameDetail" value="address.122">
              <gsmsg:write key="address.122" />
            </logic:equal>
            <logic:equal name="abaData" property="adr110abaTypeNameDetail" value="address.123">
              <gsmsg:write key="address.123" />
            </logic:equal>
            <logic:equal name="abaData" property="adr110abaTypeNameDetail" value="address.124">
              <gsmsg:write key="address.124" />
            </logic:equal>
            <span class="ml10"><bean:write name="abaData" property="adr110abaName" /></span>
          </td>
          <td class="txt_r w50" colspan="2">
            <button class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onClick="editCompanyBase('<bean:write name="abaData" property="adr110abaIndex" />');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.edit" />
            </button>
            <button class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="deleteCompanyBase('<bean:write name="abaData" property="adr110abaIndex" />');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </td>
        </tr>
        <tr>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.postalcode" />
          </th>
          <td class="w35">
            <logic:notEmpty name="abaData" property="adr110abaPostno1">
              〒<bean:write name="abaData" property="adr110abaPostno1" />-<bean:write name="abaData" property="adr110abaPostno2" />
            </logic:notEmpty>
          </td>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.prefectures" />
          </th>
          <td class="w35">
            <bean:write name="abaData" property="adr110abaTdfkName" />
          </td>
        </tr>
        <tr>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.address1" />
          </th>
          <td class="w75" colspan="3">
            <bean:write name="abaData" property="adr110abaAddress1" />
          </td>
        </tr>
        <tr>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.address2" />
          </th>
          <td class="w75" colspan="3">
            <bean:write name="abaData" property="adr110abaAddress2" />
          </td>
        </tr>
        <tr>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.memo" />
          </th>
          <td class="w75" colspan="3">
            <bean:write name="abaData" property="adr110abaViewBiko" filter="false" />
          </td>
        </tr>
      </table>
    </logic:iterate>
  </logic:notEmpty>

  <table class="table-left">
    <tr class="bgC_lightGray">
      <td class="border_right_none" colspan="2">
        <gsmsg:write key="address.adr110.3" />
      </td>
      <td class="txt_r">
        <button class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('addCompanyBase');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
      </td>
    </tr>
    <tr>
      <th class="w25" colspan="2">
        <gsmsg:write key="address.10" /><span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
      </th>
      <td class="w65">
        <html:select name="adr110Form" property="adr110abaType">
          <html:optionsCollection name="adr110Form" property="abaTypeList" value="value" label="label" />
        </html:select>
        <html:text property="adr110abaName" maxlength="50" styleClass="wp500" />
      </td>
    </tr>
    <tr>
      <th rowspan="4" class="no_w w10">
        <gsmsg:write key="cmn.address" />
      </th>
      <th class="no_w w10">
        <gsmsg:write key="cmn.postalcode" />
      </th>
      <td>
        <html:text property="adr110abaPostno1" maxlength="3" styleClass="wp50" />
        -
        <html:text property="adr110abaPostno2" maxlength="4" styleClass="wp60" />
      </td>
    </tr>
    <tr>
      <th class="no_w">
        <gsmsg:write key="cmn.prefectures" />
      </th>
      <td>
        <logic:notEmpty  name="adr110Form" property="tdfkCmbList">
          <html:select name="adr110Form" property="adr110abaTdfk">
            <html:optionsCollection name="adr110Form" property="tdfkCmbList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="no_w">
        <gsmsg:write key="cmn.address1" />
      </th>
      <td>
        <html:text property="adr110abaAddress1" maxlength="100" styleClass="wp500" />
      </td>
    </tr>
    <tr>
      <th class="no_w">
        <gsmsg:write key="cmn.address2" />
      </th>
      <td>
        <html:text property="adr110abaAddress2" maxlength="100" styleClass="wp500" />
      </td>
    </tr>
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td>
        <textarea name="adr110abaBiko" class="wp500" rows="5" onkeyup="showLengthStr(value, <%= maxLengthBiko %>, 'inputlength2');" id="inputstr2" /><bean:write name="adr110Form" property="adr110abaBiko" /></textarea>
        <br>
        <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength2" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthBiko %>&nbsp;<gsmsg:write key="cmn.character" /></span>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="okPush('confirmCompany');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="adr110Form" property="adr110ProcMode" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT) %>">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteCompany');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <logic:equal name="adr110Form" property="adr100backFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInputAddress');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:equal>
    <logic:notEqual name="adr110Form" property="adr100backFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backCompanyList');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:notEqual>
  </div>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>