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
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adrcommon.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr150.js?<%= GSConst.VERSION_PARAM %>"></script>
<theme:css filename="theme.css"/>
<link rel=stylesheet href="../common/css/default.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel=stylesheet href="../address/css/address.css?<%= GSConst.VERSION_PARAM %>" type="text/css">

</head>

<body class="body_03">

<html:form action="/address/adr150">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>
<logic:notEmpty name="adr150Form" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr150Form" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr150Form" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr150Form" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr150Form" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr150Form" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr150Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr150Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr150Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr150Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020init" />
<html:hidden property="adr020ProcMode" />
<html:hidden property="adr020BackId" />
<html:hidden property="adr160pageNum1" />
<html:hidden property="adr160pageNum2" />
<html:hidden property="sortKey" />
<html:hidden property="orderKey" />
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

<logic:notEmpty name="adr150Form" property="adr020tantoList">
<logic:iterate id="tantoList" name="adr150Form" property="adr020tantoList">
  <input type="hidden" name="adr020tantoList" value="<bean:write name="tantoList" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr150Form" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr150Form" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr150Form" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr150Form" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020tantoGroup" />

<logic:notEmpty name="adr150Form" property="adr020label">
<logic:iterate id="label" name="adr150Form" property="adr020label">
  <input type="hidden" name="adr020label" value="<bean:write name="label" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitView" />

<logic:notEmpty name="adr150Form" property="adr020permitViewGroup">
<logic:iterate id="permitViewGroup" name="adr150Form" property="adr020permitViewGroup">
  <input type="hidden" name="adr020permitViewGroup" value="<bean:write name="permitViewGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr150Form" property="adr020permitViewUser">
<logic:iterate id="permitViewUser" name="adr150Form" property="adr020permitViewUser">
  <input type="hidden" name="adr020permitViewUser" value="<bean:write name="permitViewUser" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitViewUserGroup" />
<html:hidden property="adr020permitEdit" />

<logic:notEmpty name="adr150Form" property="adr020permitEditGroup">
<logic:iterate id="permitEditGroup" name="adr150Form" property="adr020permitEditGroup">
  <input type="hidden" name="adr020permitEditGroup" value="<bean:write name="permitEditGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr150Form" property="adr020permitEditUser">
<logic:iterate id="permitEditUser" name="adr150Form" property="adr020permitEditUser">
  <input type="hidden" name="adr020permitEditUser" value="<bean:write name="permitEditUser" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitEditUserGroup" />

<html:hidden property="adr150searchFlg" />
<html:hidden property="adr150page" />
<html:hidden property="adr150svCode" />
<html:hidden property="adr150svCoName" />
<html:hidden property="adr150svCoNameKn" />
<html:hidden property="adr150svCoBaseName" />
<html:hidden property="adr150svAtiSid" />
<html:hidden property="adr150svTdfk" />
<html:hidden property="adr150svBiko" />
<html:hidden property="adr150SortKey" />
<html:hidden property="adr150OrderKey" />

<html:hidden property="adr150ReturnPage" />

<table align="center"  cellpadding="5" cellspacing="0" border="0" width="100%">
<tr>
<td width="100%" align="center">

  <table cellpadding="0" cellspacing="0" border="0" width="90%">
  <tr>
  <td>

    <table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
    <td width="0%"><img src="../address/images/header_address_01.gif" border="0" alt="<gsmsg:write key="cmn.addressbook" />"></td>
    <td width="0%" class="header_white_bg_text" align="right" valign="middle" nowrap><span class="plugin_ttl"><gsmsg:write key="cmn.addressbook" /></span></td>
    <td width="100%" class="header_white_bg_text">[ <gsmsg:write key="address.102" /> ]</td>
    <td width="0%"><img src="../common/images/header_white_end.gif" border="0" alt="<gsmsg:write key="cmn.addressbook" />"></td>
    </tr>
    </table>

    <table cellpadding="5" cellspacing="0" width="100%">
    <tr>
    <td align="right">
      <input type="button" name="btn_facilities_mnt" class="btn_back_n1" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInputAddress');">
    </td>
    </tr>
    </table>
    <IMG SRC="../common/images/spacer.gif" width="1px" height="10px" border="0">
  </td>
  </tr>

  <logic:messagesPresent message="false">
  <tr>
  <td>
    <html:errors />
    <IMG SRC="../common/images/spacer.gif" width="1px" height="10px" border="0">
  </td>
  </tr>
  </logic:messagesPresent>

  <tr>
  <td>

    <table width="100%" class="tl0 add_tbl_base">
    <tr>
    <td colspan="4" class="table_bg_7D91BD" align="left">
      <img src="../address/images/add_company_search.gif" class="img_bottom" alt="<gsmsg:write key="address.31" />">
    </td>
    </tr>

    <tr>
    <th width="10%" class="td_gray text_bb2" nowrap><gsmsg:write key="address.7" /></th>
    <td class="borderBlock-white">
      <html:text property="adr150code" maxlength="20" size="40" />
    </td>
    <th width="10%" class="td_gray text_bb2" nowrap><gsmsg:write key="address.11" /></th>
    <td width="40%" class="borderBlock-white">
      <html:select name="adr150Form" property="adr150atiSid">
        <html:optionsCollection name="adr150Form" property="atiCmbList" value="value" label="label" />
      </html:select>
    </td>
    </tr>

    <tr>
    <th width="10%" class="td_gray text_bb2" nowrap><gsmsg:write key="cmn.company.name" /></th>
    <td width="40%" class="borderBlock-white">
      <html:text property="adr150coName" maxlength="50" size="40" />
    </td>
    <th width="10%" class="td_gray text_bb2" nowrap><gsmsg:write key="cmn.prefectures" /></th>
    <td width="40%" class="borderBlock-white">
      <html:select name="adr150Form" property="adr150tdfk">
        <html:optionsCollection name="adr150Form" property="tdfkCmbList" value="value" label="label" />
      </html:select>
    </td>
    </tr>

    <tr>
    <th width="10%" class="td_gray text_bb2" nowrap><gsmsg:write key="address.9" /></th>
    <td class="borderBlock-white"><html:text property="adr150coNameKn" maxlength="100" size="40" /></td>
    <th width="10%" class="td_gray text_bb2" nowrap><gsmsg:write key="cmn.memo" /></th>
    <td class="borderBlock-white"><html:text property="adr150biko" maxlength="1000" size="40" /></td>
    </tr>

    <tr>
    <th width="10%" class="td_gray text_bb2" nowrap><gsmsg:write key="address.10" /></th>
    <td class="borderBlock-white" colspan="3"><html:text property="adr150coBaseName" maxlength="50" size="40" /></td>
    </tr>
    </table>
  </td>
  </tr>

  <tr>
  <td width="100%">
    <table cellpadding="5" width="100%">
    <tr>
    <td align="center" width="100%">
      <span align="center"><input type="button" value="<gsmsg:write key="cmn.search" />" class="btn_search_n1" onClick="buttonPush('search');"></span>
    </td>
    </tr>
    </table>
  </td>
  </tr>

  <logic:notEmpty name="adr150Form" property="companyList">
  <tr>
  <td>
    <logic:notEmpty  name="adr150Form" property="pageCmbList">
    <table width="100%" cellpadding="5" cellspacing="0">
    <td align="right">
      <img src="../common/images/arrow2_l.gif" alt="<gsmsg:write key="cmn.previous.page" />"class="img_bottom" width="20" height="20" onClick="buttonPush('prevPage');" style="cursor:pointer;">
      <html:select name="adr150Form" property="adr150pageTop" styleClass="text_i" onchange="changePage('adr150pageTop');">
        <html:optionsCollection name="adr150Form" property="pageCmbList" value="value" label="label" />
      </html:select>
      <img src="../common/images/arrow2_r.gif" alt="<gsmsg:write key="cmn.next.page" />"class="img_bottom" width="20" height="20" onClick="buttonPush('nextPage');" style="cursor:pointer;">
    </td>
    </tr>
    </table>
    </logic:notEmpty>

    <table class="tl0 table_td_border" width="100%" cellpadding="0" cellspacing="0">
    <tr class="table_bg_7D91BD_search">

    <!-- <gsmsg:write key="cmn.company.name" /> -->
    <logic:equal name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>">

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th align="center" class="table_bg_7D91BD" width="30%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.company.name" />▲</span></a></th>
      </logic:equal>

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th align="center" class="table_bg_7D91BD" width="30%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.company.name" />▼</span></a></th>
      </logic:equal>

    </logic:equal>

    <logic:notEqual name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>">
      <th align="center" class="table_bg_7D91BD" width="30%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.company.name" /></span></a></th>
    </logic:notEqual>

    <!-- <gsmsg:write key="address.adr150.1" /> -->
    <logic:equal name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ABA_TYPE) %>">

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th align="center" class="table_bg_7D91BD" width="5%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ABA_TYPE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')"><span class="text_tlw"><gsmsg:write key="address.adr150.1" />▲</span></a></th>
      </logic:equal>

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th align="center" class="table_bg_7D91BD" width="5%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ABA_TYPE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="address.adr150.1" />▼</span></a></th>
      </logic:equal>

    </logic:equal>

    <logic:notEqual name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ABA_TYPE) %>">
      <th align="center" class="table_bg_7D91BD" width="5%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ABA_TYPE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="address.adr150.1" /></span></a></th>
    </logic:notEqual>

    <!-- <gsmsg:write key="address.10" /> -->
    <logic:equal name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ABA_NAME) %>">

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th align="center" class="table_bg_7D91BD" width="5%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ABA_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')"><span class="text_tlw"><gsmsg:write key="address.10" />▲</span></a></th>
      </logic:equal>

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th align="center" class="table_bg_7D91BD" width="5%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ABA_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="address.10" />▼</span></a></th>
      </logic:equal>

    </logic:equal>

    <logic:notEqual name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ABA_NAME) %>">
      <th align="center" class="table_bg_7D91BD" width="5%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ABA_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="address.10" /></span></a></th>
    </logic:notEqual>

    <!-- <gsmsg:write key="cmn.prefectures" /> -->
    <logic:equal name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_TDFK) %>">

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th align="center" class="table_bg_7D91BD" width="5%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_TDFK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.prefectures" />▲</span></a></th>
      </logic:equal>

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th align="center" class="table_bg_7D91BD" width="5%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_TDFK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.prefectures" />▼</span></a></th>
      </logic:equal>

    </logic:equal>

    <logic:notEqual name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_TDFK) %>">
      <th align="center" class="table_bg_7D91BD" width="5%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_TDFK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.prefectures" /></span></a></th>
    </logic:notEqual>

    <!-- <gsmsg:write key="cmn.address1" /> -->
    <logic:equal name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ADDR1) %>">

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th align="center" class="table_bg_7D91BD" width="30%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ADDR1) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.address1" />▲</span></a></th>
      </logic:equal>

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th align="center" class="table_bg_7D91BD" width="30%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ADDR1) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.address1" />▼</span></a></th>
      </logic:equal>

    </logic:equal>

    <logic:notEqual name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ADDR1) %>">
      <th align="center" class="table_bg_7D91BD" width="30%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ADDR1) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.address1" /></span></a></th>
    </logic:notEqual>

    <!-- <gsmsg:write key="cmn.address2" /> -->
    <logic:equal name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ADDR2) %>">

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th align="center" class="table_bg_7D91BD" width="30%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ADDR2) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.address2" />▲</span></a></th>
      </logic:equal>

      <logic:equal name="adr150Form" property="adr150OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th align="center" class="table_bg_7D91BD" width="30%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ADDR2) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.address2" />▼</span></a></th>
      </logic:equal>

    </logic:equal>

    <logic:notEqual name="adr150Form" property="adr150SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ADDR2) %>">
      <th align="center" class="table_bg_7D91BD" width="30%" nowrap><a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_ADDR2) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span class="text_tlw"><gsmsg:write key="cmn.address2" /></span></a></th>
    </logic:notEqual>

    </tr>

    <logic:iterate id="companyData" name="adr150Form" property="companyList" indexId="idx">
    <tr class="td_line_color1">
    <td align="left" nowrap><a href="#" onClick="return selectCompany(<bean:write name="companyData" property="acoSid" />, <bean:write name="companyData" property="abaSid" />);"><span class="textLink"><bean:write name="companyData" property="companyName" /></span></a></td>
    <td align="left"><bean:write name="companyData" property="companyBaseTypeName" /></td>
    <td align="left"><bean:write name="companyData" property="companyBaseName" /></td>
    <td align="left"><bean:write name="companyData" property="tdfkName" /></td>
    <td align="left"><bean:write name="companyData" property="companyBaseAddress1" /></td>
    <td nowrap align="left"><bean:write name="companyData" property="companyBaseAddress2" /></td>
    </tr>
    </logic:iterate>

    </table>


    <logic:notEmpty  name="adr150Form" property="pageCmbList">
    <table width="100%" cellpadding="5" cellspacing="0">
    <td align="right">
      <img src="../common/images/arrow2_l.gif" alt="<gsmsg:write key="cmn.previous.page" />" class="img_bottom" width="20" height="20" onClick="buttonPush('prevPage');" style="cursor:pointer;">
      <html:select name="adr150Form" property="adr150pageBottom" styleClass="text_i" onchange="changePage('adr150pageBottom');">
        <html:optionsCollection name="adr150Form" property="pageCmbList" value="value" label="label" />
      </html:select>
      <img src="../common/images/arrow2_r.gif" alt="<gsmsg:write key="cmn.next.page" />" class="img_bottom" width="20" height="20" onClick="buttonPush('nextPage');" style="cursor:pointer;">
    </td>
    </tr>
    </table>
    </logic:notEmpty>

  </td>
  </tr>
  </logic:notEmpty>

  </table>

</td>
</tr>
</table>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>