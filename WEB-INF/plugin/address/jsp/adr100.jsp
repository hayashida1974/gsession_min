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
<title>GROUPSESSION <gsmsg:write key="address.102" /></title>
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adrcommon.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr100.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body class="body_03">

<html:form action="/address/adr100">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>
<logic:notEmpty name="adr100Form" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr100Form" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr100Form" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr100Form" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr100Form" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr100Form" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr100Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr100Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr100Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr100Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

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

<logic:notEmpty name="adr100Form" property="adr020tantoList">
<logic:iterate id="tantoList" name="adr100Form" property="adr020tantoList">
  <input type="hidden" name="adr020tantoList" value="<bean:write name="tantoList" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr100Form" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr100Form" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr100Form" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr100Form" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020tantoGroup" />

<logic:notEmpty name="adr100Form" property="adr020label">
<logic:iterate id="label" name="adr100Form" property="adr020label">
  <input type="hidden" name="adr020label" value="<bean:write name="label" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitView" />

<logic:notEmpty name="adr100Form" property="adr020permitViewGroup">
<logic:iterate id="permitViewGroup" name="adr100Form" property="adr020permitViewGroup">
  <input type="hidden" name="adr020permitViewGroup" value="<bean:write name="permitViewGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr100Form" property="adr020permitViewUser">
<logic:iterate id="permitViewUser" name="adr100Form" property="adr020permitViewUser">
  <input type="hidden" name="adr020permitViewUser" value="<bean:write name="permitViewUser" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitViewUserGroup" />
<html:hidden property="adr020permitEdit" />

<logic:notEmpty name="adr100Form" property="adr020permitEditGroup">
<logic:iterate id="permitEditGroup" name="adr100Form" property="adr020permitEditGroup">
  <input type="hidden" name="adr020permitEditGroup" value="<bean:write name="permitEditGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr100Form" property="adr020permitEditUser">
<logic:iterate id="permitEditUser" name="adr100Form" property="adr020permitEditUser">
  <input type="hidden" name="adr020permitEditUser" value="<bean:write name="permitEditUser" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitEditUserGroup" />
<html:hidden property="adr100backFlg" />

<input type="hidden" name="adr110ProcMode" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_ADD) %>">
<input type="hidden" name="adr110editAcoSid" value="">
<html:hidden property="adr100searchFlg" />
<html:hidden property="adr100page" />
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
<bean:define id="procMode" name="adr100Form" property="adr100mode" />
<% int sMode = ((Integer) procMode).intValue(); %>

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.102" />
    </li>
    <li>
      <div>
        <logic:notEqual name="adr100Form" property="adr100backFlg" value="1">
          <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="addCompany('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_ADD) %>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
          <button type="button" value="<gsmsg:write key="cmn.import" />" class="baseBtn" onClick="buttonPush('importCompany');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
            <gsmsg:write key="cmn.import" />
          </button>
          <logic:notEmpty name="adr100Form" property="companyList">
            <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('deleteCompanies');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:notEmpty>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backAddressList');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:notEqual>
        <logic:equal name="adr100Form" property="adr100backFlg" value="1">
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backInputAddress');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <% if (sMode == 0) { %>
    <ul class="tabHeader w100">
      <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none bgI_none bgC_lightGray" id="tab1" onClick="changeTab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_50) %>');">
        <gsmsg:write key="address.adr100.1" />
      </li>
      <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab2" onClick="changeTab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) %>');">
        <gsmsg:write key="cmn.advanced.search" />
      </li>
      <li class="tabHeader_space border_bottom_none"></li>
    </ul>
    <table class="bgC_lightGray bor1 w100 mt0">
      <tr>
        <td class="txt_c w50 p5">
          <% java.util.List rowList = new java.util.ArrayList(); %>
          <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
          <% rowList.add(new String[]{gsMsg.getMessage(request, "cmn.kana.a"), gsMsg.getMessage(request, "cmn.kana.ka"), gsMsg.getMessage(request, "cmn.kana.sa"), gsMsg.getMessage(request, "cmn.kana.ta"), gsMsg.getMessage(request, "cmn.kana.na"), gsMsg.getMessage(request, "cmn.kana.ha"), gsMsg.getMessage(request, "cmn.kana.ma"), gsMsg.getMessage(request, "cmn.kana.ya"), gsMsg.getMessage(request, "cmn.kana.ra"), gsMsg.getMessage(request, "cmn.kana.wa")}); %>
          <% rowList.add(new String[]{gsMsg.getMessage(request, "cmn.kana.i"), gsMsg.getMessage(request, "cmn.kana.ki"), gsMsg.getMessage(request, "cmn.kana.shi"), gsMsg.getMessage(request, "cmn.kana.chi"), gsMsg.getMessage(request, "cmn.kana.ni"), gsMsg.getMessage(request, "cmn.kana.hi"), gsMsg.getMessage(request, "cmn.kana.mi"), "", gsMsg.getMessage(request, "cmn.kana.ri"), gsMsg.getMessage(request, "cmn.kana.wo")}); %>
          <% rowList.add(new String[]{gsMsg.getMessage(request, "cmn.kana.u"), gsMsg.getMessage(request, "cmn.kana.ku"), gsMsg.getMessage(request, "cmn.kana.su"), gsMsg.getMessage(request, "cmn.kana.tsu"), gsMsg.getMessage(request, "cmn.kana.nu"), gsMsg.getMessage(request, "cmn.kana.fu"), gsMsg.getMessage(request, "cmn.kana.mu"), gsMsg.getMessage(request, "cmn.kana.yu"), gsMsg.getMessage(request, "cmn.kana.ru"), gsMsg.getMessage(request, "cmn.kana.n")}); %>
          <% rowList.add(new String[]{gsMsg.getMessage(request, "cmn.kana.e"), gsMsg.getMessage(request, "cmn.kana.ke"), gsMsg.getMessage(request, "cmn.kana.se"), gsMsg.getMessage(request, "cmn.kana.te"), gsMsg.getMessage(request, "cmn.kana.ne"), gsMsg.getMessage(request, "cmn.kana.he"), gsMsg.getMessage(request, "cmn.kana.me"), "", gsMsg.getMessage(request, "cmn.kana.re"), ""}); %>
          <% rowList.add(new String[]{gsMsg.getMessage(request, "cmn.kana.o"), gsMsg.getMessage(request, "cmn.kana.ko"), gsMsg.getMessage(request, "cmn.kana.so"), gsMsg.getMessage(request, "cmn.kana.to"), gsMsg.getMessage(request, "cmn.kana.no"), gsMsg.getMessage(request, "cmn.kana.ho"), gsMsg.getMessage(request, "cmn.kana.mo"), gsMsg.getMessage(request, "cmn.kana.yo"), gsMsg.getMessage(request, "cmn.kana.ro"), ""}); %>
          <bean:define id="existKanaList" name="adr100Form" property="adr100comNameKanaList" type="java.util.List" />
          <table class="bgC_tableCell w100">
            <% for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) { %>
              <% String[] kanaArray = (String[]) rowList.get(rowIndex); %>
              <tr align="center">
                <% for (int kanaIndex = 0; kanaIndex < kanaArray.length; kanaIndex++) { %>
                  <% String kana = kanaArray[kanaIndex]; %>
                  <% if (existKanaList.contains(kana)) { %>
                    <td class="bor1 wp30 hp20 td-hoverChange cursor_p" onClick="return searchToKana('<%= kana %>');">
                      <span class="cl_linkDef font-bold"><%= kana %></span>
                    </td>
                  <% } else { %>
                    <td class="bor1 wp30 hp20">
                      <%= kana %>
                    </td>
                  <% } %>
                <% } %>
              </tr>
            <% } %>
          </table>
        </td>
      </tr>
    </table>
  <% } else if (sMode == 1) { %>
    <ul class="tabHeader w100">
      <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab1" onClick="changeTab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_50) %>');">
        <gsmsg:write key="address.adr100.1" />
      </li>
      <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none bgI_none bgC_lightGray" id="tab2" onClick="changeTab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) %>');">
        <gsmsg:write key="cmn.advanced.search" />
      </li>
      <li class="tabHeader_space border_bottom_none"></li>
    </ul>
    <table class="table-left w100 mt0">
      <tr>
        <th class="w10 no_w">
          <gsmsg:write key="address.7" />
        </th>
        <td class="w40">
          <html:text property="adr100code" maxlength="20" styleClass="wp300" />
        </td>
        <th class="w10 no_w">
          <gsmsg:write key="address.11" />
        </th>
        <td class="w40">
          <html:select name="adr100Form" property="adr100atiSid">
            <html:optionsCollection name="adr100Form" property="atiCmbList" value="value" label="label" />
          </html:select>
        </td>
      </tr>
      <tr>
        <th class="w10 no_w">
          <gsmsg:write key="cmn.company.name" />
        </th>
        <td class="w40">
          <html:text property="adr100coName" maxlength="50" styleClass="wp350" />
        </td>
        <th class="w10 no_w">
          <gsmsg:write key="cmn.prefectures" />
        </th>
        <td class="w40">
          <html:select name="adr100Form" property="adr100tdfk">
            <html:optionsCollection name="adr100Form" property="tdfkCmbList" value="value" label="label" />
          </html:select>
        </td>
      </tr>
      <tr>
        <th class="w10 no_w">
          <gsmsg:write key="address.9" />
        </th>
        <td class="w40">
          <html:text property="adr100coNameKn" maxlength="100" styleClass="wp350" />
        </td>
        <th class="w10 no_w">
          <gsmsg:write key="cmn.memo" />
        </th>
        <td class="w40">
          <html:text property="adr100biko" maxlength="1000" styleClass="wp350" />
        </td>
      </tr>
      <tr>
        <th class="w10 no_w">
          <gsmsg:write key="address.10" />
        </th>
        <td class="w90" colspan="3">
          <html:text property="adr100coBaseName" maxlength="50" styleClass="wp350" />
        </td>
      </tr>
    </table>

    <div class="w100 txt_c mt10">
      <button type="button" value="<gsmsg:write key="cmn.search" />" class="baseBtn" onClick="buttonPush('search');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <gsmsg:write key="cmn.search" />
      </button>
    </div>
  <% } %>

  <logic:notEmpty name="adr100Form" property="companyList">
    <div class="w100 txt_r mt10">
      <button type="button" value="<gsmsg:write key="cmn.export" />" class="baseBtn" onClick="buttonPush('exportCompany');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
        <gsmsg:write key="cmn.export" />
      </button>
    </div>
    <logic:notEmpty  name="adr100Form" property="pageCmbList">
      <div class="paging mt10">
        <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  name="adr100Form" property="adr100pageTop" onchange="changePage('adr100pageTop');">
          <html:optionsCollection name="adr100Form" property="pageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:notEmpty>
    <table class="table-top">
      <tr>
        <th class="w5 js_tableTopCheck js_tableTopCheck-header">
          <input type="checkbox" name="all_Check"></input>
        </th>
        <logic:equal name="adr100Form" property="adr100SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_CODE) %>">
          <logic:equal name="adr100Form" property="adr100OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
            <th class="w20 no_w">
              <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_CODE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
                <span class="classic-display"><gsmsg:write key="address.7" />▲</span>
                <span class="original-display txt_m"><gsmsg:write key="address.7" /><i class="icon-sort_up"></i></span>
              </a>
            </th>
          </logic:equal>
          <logic:equal name="adr100Form" property="adr100OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
            <th class="w20 no_w">
              <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_CODE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
                <span class="classic-display"><gsmsg:write key="address.7" />▼</span>
                <span class="original-display txt_m"><gsmsg:write key="address.7" /><i class="icon-sort_down"></i></span>
              </a>
            </th>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="adr100Form" property="adr100SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_CODE) %>">
          <th class="w20 no_w">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_CODE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
              <gsmsg:write key="address.7" />
            </a>
          </th>
        </logic:notEqual>
        <logic:equal name="adr100Form" property="adr100SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>">
          <logic:equal name="adr100Form" property="adr100OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
            <th class="w25 no_w">
              <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
                <span class="classic-display"><gsmsg:write key="cmn.company.name" />▲</span>
                <span class="original-display txt_m"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_up"></i></span>
              </a>
            </th>
          </logic:equal>
          <logic:equal name="adr100Form" property="adr100OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
            <th class="w25 no_w">
              <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
                <span class="classic-display"><gsmsg:write key="cmn.company.name" />▼</span>
                <span class="original-display txt_m"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_down"></i></span>
              </a>
            </th>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="adr100Form" property="adr100SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>">
          <th class="w25 no_w">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
              <gsmsg:write key="cmn.company.name" />
            </a>
          </th>
        </logic:notEqual>
        <th class="w25">
          <gsmsg:write key="address.11" />
        </th>
        <logic:equal name="adr100Form" property="adr100SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_BIKO) %>">
          <logic:equal name="adr100Form" property="adr100OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
            <th class="w25 no_w">
              <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_BIKO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
                <span class="classic-display"><gsmsg:write key="cmn.memo" />▲</span>
                <span class="original-display txt_m"><gsmsg:write key="cmn.memo" /><i class="icon-sort_up"></i></span>
              </a>
            </th>
          </logic:equal>
          <logic:equal name="adr100Form" property="adr100OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
            <th class="w25 no_w">
              <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_BIKO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
                <span class="classic-display"><gsmsg:write key="cmn.memo" />▼</span>
                <span class="original-display txt_m"><gsmsg:write key="cmn.memo" /><i class="icon-sort_down"></i></span>
              </a>
            </th>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="adr100Form" property="adr100SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_BIKO) %>">
          <th class="w25 no_w">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_BIKO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
              <gsmsg:write key="cmn.memo" />
            </a>
          </th>
        </logic:notEqual>
      </tr>
      <logic:iterate id="companyData" name="adr100Form" property="companyList" indexId="idx">
        <tr class="js_listHover cursor_p" id="<bean:write name="companyData" property="acoSid" />">
          <td class="txt_c js_tableTopCheck">
            <html:multibox name="adr100Form" property="adr100SelectCom"><bean:write name="companyData" property="acoSid" /></html:multibox>
          </td>
          <logic:equal name="adr100Form" property="adr100backFlg" value="1">
            <td class="js_listClick">
              <bean:write name="companyData" property="companyCode" />
            </td>
            <td class="js_listClick">
              <span class="cl_linkDef">
                <bean:write name="companyData" property="companyName" />
              </span>
            </td>
            <td class="js_listClick">
              <bean:write name="companyData" property="industryName" />
            </td>
            <td class="js_listClick">
              <bean:write name="companyData" property="companyBiko" />
            </td>
          </logic:equal>
          <logic:notEqual name="adr100Form" property="adr100backFlg" value="1">
            <td class="js_listClick2">
              <bean:write name="companyData" property="companyCode" />
            </td>
            <td class="js_listClick2">
              <span class="cl_linkDef">
                <bean:write name="companyData" property="companyName" />
              </span>
            </td>
            <td class="js_listClick2">
              <bean:write name="companyData" property="industryName" />
            </td>
            <td class="js_listClick2">
              <bean:write name="companyData" property="companyBiko" />
            </td>
          </logic:notEqual>
        </tr>
      </logic:iterate>
    </table>
  </logic:notEmpty>
  <logic:notEmpty  name="adr100Form" property="pageCmbList">
    <div class="paging mt10">
      <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select styleClass="paging_combo"  name="adr100Form" property="adr100pageBottom" onchange="changePage('adr100pageBottom');">
        <html:optionsCollection name="adr100Form" property="pageCmbList" value="value" label="label" />
      </html:select>
      <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:notEmpty>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>