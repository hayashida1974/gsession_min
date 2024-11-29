<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="address.adr110kn.1" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr110kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/address/adr110kn">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>
<logic:notEmpty name="adr110knForm" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr110knForm" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr110knForm" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr110knForm" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr110knForm" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr110knForm" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr110knForm" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr110knForm" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr110knForm" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr110knForm" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

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

<logic:notEmpty name="adr110knForm" property="adr020tantoList">
<logic:iterate id="tantoList" name="adr110knForm" property="adr020tantoList">
  <input type="hidden" name="adr020tantoList" value="<bean:write name="tantoList" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr110knForm" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr110knForm" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr110knForm" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr110knForm" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020tantoGroup" />

<logic:notEmpty name="adr110knForm" property="adr020label">
<logic:iterate id="label" name="adr110knForm" property="adr020label">
  <input type="hidden" name="adr020label" value="<bean:write name="label" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitView" />

<logic:notEmpty name="adr110knForm" property="adr020permitViewGroup">
<logic:iterate id="permitViewGroup" name="adr110knForm" property="adr020permitViewGroup">
  <input type="hidden" name="adr020permitViewGroup" value="<bean:write name="permitViewGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr110knForm" property="adr020permitViewUser">
<logic:iterate id="permitViewUser" name="adr110knForm" property="adr020permitViewUser">
  <input type="hidden" name="adr020permitViewUser" value="<bean:write name="permitViewUser" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitViewUserGroup" />
<html:hidden property="adr020permitEdit" />

<logic:notEmpty name="adr110knForm" property="adr020permitEditGroup">
<logic:iterate id="permitEditGroup" name="adr110knForm" property="adr020permitEditGroup">
  <input type="hidden" name="adr020permitEditGroup" value="<bean:write name="permitEditGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr110knForm" property="adr020permitEditUser">
<logic:iterate id="permitEditUser" name="adr110knForm" property="adr020permitEditUser">
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

<html:hidden property="adr110coCode" />
<html:hidden property="adr110coName" />
<html:hidden property="adr110coNameKn" />
<html:hidden property="adr110coPostno1" />
<html:hidden property="adr110coPostno2" />
<html:hidden property="adr110coTdfk" />
<html:hidden property="adr110coAddress1" />
<html:hidden property="adr110coAddress2" />

<logic:notEmpty name="adr110knForm" property="adr110atiList">
<logic:iterate id="atiSid" name="adr110knForm" property="adr110atiList">
<input type="hidden" name="adr110atiList" value="<bean:write name="atiSid" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr110url" />
<html:hidden property="adr110biko" />
<html:hidden property="adr110abaType" />
<html:hidden property="adr110abaName" />
<html:hidden property="adr110abaPostno1" />
<html:hidden property="adr110abaPostno2" />
<html:hidden property="adr110abaTdfk" />
<html:hidden property="adr110abaAddress1" />
<html:hidden property="adr110abaAddress2" />
<html:hidden property="adr110abaBiko" />

<logic:notEmpty name="adr110knForm" property="abaListToList">
<logic:iterate id="abaData" name="adr110knForm" property="abaListToList" indexId="idx">
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

<html:hidden name="adr110knForm" property="adr110ProcMode" />
<html:hidden name="adr110knForm" property="adr110editAcoSid" />
<html:hidden name="adr110knForm" property="adr110BackId" />
<jsp:include page="/WEB-INF/plugin/address/jsp/adr330_hiddenParams.jsp" >
<jsp:param value="adr110knForm" name="thisFormName"/>
</jsp:include>

<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.adr110kn.1" />
    </li>
    <li>
      <div>
        <logic:equal name="adr110knForm" property="adr100backFlg" value="3">
          <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decisionCompany');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <gsmsg:write key="cmn.final" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="backInput('backInputCompany');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>

      <logic:notEqual name="adr110knForm" property="adr100backFlg" value="3">
        <logic:notEqual name="adr110knForm" property="adr100backFlg" value="2">
          <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decisionCompany');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <gsmsg:write key="cmn.final" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInputCompany');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:notEqual>

        <logic:equal name="adr110knForm" property="adr100backFlg" value="2">
          <button type="button" value="<gsmsg:write key="cmn.edit" />" class="baseBtn" onClick="return editCompany('<bean:write name="adr110knForm" property="adr110editAcoSid" />', '<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT) %>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_1.png" alt="<gsmsg:write key="cmn.edit" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
            <gsmsg:write key="cmn.edit" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdrList');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>
      </logic:notEqual>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">

<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>


<table class="w100">
  <tr>
    <td class="w50 txt_t">
      <table class="table-left">
        <!--  <gsmsg:write key="address.7" /> -->
        <tr>
          <th class="w25">
            <gsmsg:write key="address.7" />
          </th>
          <td class="w75" colspan="3">
            <bean:write name="adr110knForm" property="adr110coCode" />
          </td>
        </tr>
        <!--  <gsmsg:write key="cmn.company.name" /> -->
        <tr>
          <th>
            <gsmsg:write key="cmn.company.name" />
          </th>
          <td colspan="3">
            <bean:write name="adr110knForm" property="adr110coName" />
          </td>
        </tr>
        <!--  <gsmsg:write key="cmn.company.name" /><gsmsg:write key="cmn.kana.2" /> -->
        <tr>
          <th>
            <gsmsg:write key="cmn.company.name" />(<gsmsg:write key="cmn.kana" />)
          </th>
          <td colspan="3">
            <bean:write name="adr110knForm" property="adr110coNameKn" />
          </td>
        </tr>
        <!--  <gsmsg:write key="address.103" /> -->
        <tr>
          <th>
            <gsmsg:write key="address.103" />
          </th>
          <td colspan="3">
            <logic:notEmpty name="adr110knForm" property="adr110knViewAtiList">
              <logic:iterate id="atiName" name="adr110knForm" property="adr110knViewAtiList">
                <bean:write name="atiName" /><br>
              </logic:iterate>
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.postalcode" />
          </th>
          <td class="w30">
            <logic:notEmpty name="adr110knForm" property="adr110coPostno1">
              〒<bean:write name="adr110knForm" property="adr110coPostno1" />-<bean:write name="adr110knForm" property="adr110coPostno2" />
            </logic:notEmpty>
          </td>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.prefectures" />
          </th>
          <td class="w30">
            <bean:write name="adr110knForm" property="adr110knViewCoTdfkName" />
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="cmn.address1" />
          </th>
          <td colspan="3">
            <bean:write name="adr110knForm" property="adr110coAddress1" />
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="cmn.address2" />
          </th>
          <td colspan="3">
            <bean:write name="adr110knForm" property="adr110coAddress2" />
          </td>
        </tr>
        <!-- URL -->
        <tr>
          <th>
            <gsmsg:write key="cmn.url" />
          </th>
          <td colspan="3">
            <a href="<bean:write name="adr110knForm" property="adr110url" />" target="__blank"><bean:write name="adr110knForm" property="adr110url" /></a>
          </td>
        </tr>
        <!--  <gsmsg:write key="cmn.memo" /> -->
        <tr>
          <th>
            <gsmsg:write key="cmn.memo" />
          </th>
          <td colspan="3">
            <bean:write name="adr110knForm" property="adr110knViewBiko" filter="false" />
          </td>
        </tr>
      </table>
      <logic:notEmpty name="adr110knForm" property="abaListToList">
        <table class="table-left">
          <logic:iterate id="abaData" name="adr110knForm" property="abaListToList">
            <tr>
              <td class="bgC_lightGray fw_b" colspan="4">
                <img class="classic-display mr5" src="../address/images/classic/icon_parson.gif" alt="<gsmsg:write key="address.106" />">
                <img class="original-display mr5" src="../common/images/original/icon_company.png" alt="<gsmsg:write key="address.106" />">
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
            </tr>
            <tr>
              <th class="w20">
                <gsmsg:write key="cmn.postalcode" />
              </th>
              <td class="w30">
                <logic:notEmpty name="abaData" property="adr110abaPostno1">
                  〒<bean:write name="abaData" property="adr110abaPostno1" />-<bean:write name="abaData" property="adr110abaPostno2" />
                </logic:notEmpty>
              </td>
              <th class="w20">
                <gsmsg:write key="cmn.prefectures" />
              </th>
              <td class="w30">
                <bean:write name="abaData" property="adr110abaTdfkName" />
              </td>
            </tr>
            <tr>
              <th>
                <gsmsg:write key="cmn.address1" />
              </th>
              <td colspan="3">
                <bean:write name="abaData" property="adr110abaAddress1" />
              </td>
            </tr>
            <tr>
              <th>
                <gsmsg:write key="cmn.address2" />
              </th>
              <td colspan="3">
                <bean:write name="abaData" property="adr110abaAddress2" />
              </td>
            </tr>
            <tr>
              <th>
                <gsmsg:write key="cmn.memo" />
              </th>
              <td colspan="3">
                <bean:write name="abaData" property="adr110abaViewBiko" filter="false" />
              </td>
            </tr>
          </logic:iterate>
        </table>
      </logic:notEmpty>
    </td>
    <logic:equal name="adr110knForm" property="adr100backFlg" value="2">
      <td class="txt_t">
        <table class="table-left">
          <tr>
            <td colspan="3" class="bgC_lightGray fw_b">
              <img src="../address/images/classic/icon_parson.gif" alt="<gsmsg:write key="address.106" />" class="img_bottom classic-display">
              <img src="../common/images/original/icon_company.png" alt="<gsmsg:write key="address.106" />" class="img_bottom original-display">
              <gsmsg:write key="address.adr110kn.3" /><span class="ml5">（<bean:write name="adr110knForm" property="adr110knAdrCount" /><gsmsg:write key="cmn.number" />）</span>
            </td>
          </tr>
          <logic:notEmpty name="adr110knForm" property="adr110knAdrInfList" >
            <tr>
              <th class="w40 txt_c">
                <gsmsg:write key="cmn.name" />
              </th>
              <th class="w40 txt_c">
                <gsmsg:write key="address.10" />
              </th>
              <th class="w20 txt_c">
                <gsmsg:write key="cmn.post" />
              </th>
            </tr>
            <logic:iterate id="adrInfo" name="adr110knForm" property="adr110knAdrInfList" indexId="idx">
              <tr>
                <td>
                  <a href="#" onClick="return editAddress('<bean:write name="adrInfo" property="adrSid" />');">
                  <bean:write name="adrInfo" property="userName" /></a>
                </td>
                <td>
                  <bean:write name="adrInfo" property="companyBaseName" />
                </td>
                <td>
                  <bean:write name="adrInfo" property="positionName" />
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </table>
      </td>
    </logic:equal>
  </tr>
</table>

<div class="footerBtn_block">

  <logic:equal name="adr110knForm" property="adr100backFlg" value="3">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decisionCompany');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="backInput('backInputCompany');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </logic:equal>

  <logic:notEqual name="adr110knForm" property="adr100backFlg" value="3">
  <logic:notEqual name="adr110knForm" property="adr100backFlg" value="2">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decisionCompany');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInputCompany');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </logic:notEqual>

  <logic:equal name="adr110knForm" property="adr100backFlg" value="2">
    <button type="button" value="<gsmsg:write key="cmn.fixed" />" class="baseBtn" onClick="return editCompany('<bean:write name="adr110knForm" property="adr110editAcoSid" />', '<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT) %>');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_1.png" alt="<gsmsg:write key="cmn.fixed" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.fixed" />">
      <gsmsg:write key="cmn.fixed" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdrList');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </logic:equal>
  </logic:notEqual>

  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>