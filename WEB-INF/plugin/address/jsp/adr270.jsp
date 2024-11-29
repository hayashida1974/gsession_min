<%@ page import="java.util.Calendar" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<% String close = String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn100.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr270.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="cmn.addressbook" /></title>
</head>

<body class="body_03">

<html:form action="/address/adr270">

<html:hidden property="adr270address1" />
<html:hidden property="adr270address2" />

<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>

    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />"  onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
  <table class="table-left">
    <tr>
      <th class="w40" colspan="2">
        <gsmsg:write key="cmn.name" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270unameSei" /><span class="ml10"><bean:write name="adr270Form" property="adr270unameMei" /></span>
      </td>
    </tr>
    <tr>
      <th class="w40" colspan="2">
        <gsmsg:write key="user.119" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270unameSeiKn" /><span class="ml10"><bean:write name="adr270Form" property="adr270unameMeiKn" /></span>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w" rowspan="3">
        <gsmsg:write key="address.118" />
      </th>
      <th class="w20 no_w">
        <gsmsg:write key="address.7" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270companyCode" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.company.name" />
      </th>
      <td class="w60 no_w">
        <bean:write name="adr270Form" property="adr270companyName" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="address.10" />
      </th>
      <td>
        <bean:write name="adr270Form" property="adr270companyBaseName" />
      </td>
    </tr>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="cmn.affiliation" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270syozoku" />
      </td>
    </tr>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="cmn.post" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270positionName" />
      </td>
    </tr>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="cmn.mailaddress1" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270mail1" />
        <logic:notEmpty name="adr270Form" property="adr270mail1Comment">
          <div>
            <gsmsg:write key="cmn.comment" />：<span class="ml5"><bean:write name="adr270Form" property="adr270mail1Comment" /></span>
          </div>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="cmn.mailaddress2" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270mail2" />
        <logic:notEmpty name="adr270Form" property="adr270mail1Comment">
          <div>
            <gsmsg:write key="cmn.comment" />：<span class="ml5"><bean:write name="adr270Form" property="adr270mail2Comment" /></span>
          </div>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
　　  <th class="w40 no_w" colspan="2">
        <gsmsg:write key="cmn.mailaddress3" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270mail3" />
        <logic:notEmpty name="adr270Form" property="adr270mail1Comment">
          <div>
            <gsmsg:write key="cmn.comment" />：<span class="ml5"><bean:write name="adr270Form" property="adr270mail3Comment" /></span>
          </div>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="cmn.tel1" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270tel1" />
        <logic:notEmpty name="adr270Form" property="adr270tel1Nai">
          <div>
            <gsmsg:write key="address.58" />：<span class="ml5"><bean:write name="adr270Form" property="adr270tel1Nai" /></span>
          </div>
        </logic:notEmpty>
        <logic:notEmpty name="adr270Form" property="adr270tel1Comment">
          <div>
            <gsmsg:write key="cmn.comment" />：<span class="ml5"><bean:write name="adr270Form" property="adr270tel1Comment" /></span>
          </div>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="cmn.tel2" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270tel2" />
        <logic:notEmpty name="adr270Form" property="adr270tel2Nai">
          <div>
            <gsmsg:write key="address.58" />：<span class="ml5"><bean:write name="adr270Form" property="adr270tel2Nai" /></span>
          </div>
        </logic:notEmpty>
        <logic:notEmpty name="adr270Form" property="adr270tel2Comment">
          <div>
            <gsmsg:write key="cmn.comment" />：<span class="ml5"><bean:write name="adr270Form" property="adr270tel2Comment" /></span>
          </div>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="cmn.tel3" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270tel3" />
        <logic:notEmpty name="adr270Form" property="adr270tel3Nai">
          <div>
            <gsmsg:write key="address.58" />：<span class="ml5"><bean:write name="adr270Form" property="adr270tel3Nai" /></span>
          </div>
        </logic:notEmpty>
        <logic:notEmpty name="adr270Form" property="adr270tel3Comment">
          <div>
            <gsmsg:write key="cmn.comment" />：<span class="ml5"><bean:write name="adr270Form" property="adr270tel3Comment" /></span>
          </div>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="address.adr020.10" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270fax1" />
        <logic:notEmpty name="adr270Form" property="adr270fax1Comment">
          <div>
            <gsmsg:write key="cmn.comment" />：<span class="ml5"><bean:write name="adr270Form" property="adr270fax1Comment" /></span>
          </div>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="address.adr020.11" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270fax2" />
        <logic:notEmpty name="adr270Form" property="adr270fax2Comment">
          <div>
            <gsmsg:write key="cmn.comment" />：<span class="ml5"><bean:write name="adr270Form" property="adr270fax2Comment" /></span>
          </div>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="address.adr020.12" />
      </th>
      <td valign="middle" align="left" class="borderBlock-white" nowrap>
        <bean:write name="adr270Form" property="adr270fax3" />
        <logic:notEmpty name="adr270Form" property="adr270fax3Comment">
          <div>
            <gsmsg:write key="cmn.comment" />：<span class="ml5"><bean:write name="adr270Form" property="adr270fax3Comment" /></span>
          </div>
        </logic:notEmpty>
      </td>
    </tr>
    <% String addressRow = "5"; %>
    <logic:equal name="adr270Form" property="adr270searchUse" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_NOT_USE) %>">
      <% addressRow = "4"; %>
    </logic:equal>
    <tr>
      <th class="w20" rowspan="<%= addressRow %>">
        <gsmsg:write key="cmn.address" />
      </th>
      <th class="w20">
        <gsmsg:write key="cmn.postalcode" />
      </th>
      <td valign="middle" align="left" class="borderBlock-white">
        <bean:write name="adr270Form" property="adr270ViewPostno" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.prefectures" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270tdfkName" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.address1" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270address1" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.address2" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270address2" />
      </td>
    </tr>
    <% if (addressRow.equals("5")) { %>
      <tr>
        <th class="w20">
          <gsmsg:write key="address.76" />
        </th>
        <td class="w60">
          <a href="#" onClick="return addressSearch();">
            <gsmsg:write key="cmn.search.map" />
          </a>
        </td>
      </tr>
    <% } %>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w60">
        <bean:write name="adr270Form" property="adr270biko" filter="false" />
      </td>
    </tr>
    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="cmn.staff" />
      </th>
      <td class="w60">
        <logic:notEmpty name="adr270Form" property="selectTantoCombo">
          <% boolean tantoLineFlg = false; %>
          <logic:iterate id="tantoLabel" name="adr270Form" property="selectTantoCombo" type="UsrLabelValueBean">
            <% if (tantoLineFlg) { %><br><% } else { tantoLineFlg = true; } %>
            <span class="<%= UserUtil.getCSSClassNameNormal(tantoLabel.getUsrUkoFlg()) %>"><bean:write name="tantoLabel" property="label" /></span>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>

    <tr>
      <th class="w40 no_w" colspan="2">
        <gsmsg:write key="cmn.label" />
      </th>
      <td class="w60">
        <logic:notEmpty name="adr270Form" property="selectLabelList">
          <logic:iterate id="labelData" name="adr270Form" property="selectLabelList" indexId="idx">
            <div>
              <bean:write name="labelData" property="albName" />
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />"  onClick="window.close();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>
</div>
</html:form>
</body>
</html:html>