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
<title>GROUPSESSION <gsmsg:write key="address.118" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../address/css/address.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body class="body_03">

<html:form action="/address/adr250">

<input type="hidden" name="CMD" value="">
<html:hidden property="adr250AcoSid" />


<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.118" />
    </li>
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
<div class="wrapper w80 mrl_auto">
  <table class="table-left">
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="address.7" />
      </th>
      <td class="w75" colspan="3">
        <bean:write name="adr250Form" property="adr250coCode" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.company.name" />
      </th>
      <td class="w75" colspan="3">
        <bean:write name="adr250Form" property="adr250coName" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.company.name" />(<gsmsg:write key="cmn.kana" />)
      </th>
      <td colspan="3">
        <bean:write name="adr250Form" property="adr250coNameKn" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="address.103" />
      </th>
      <td colspan="3">
        <logic:notEmpty name="adr250Form" property="adr250ViewAtiList">
          <logic:iterate id="atiName" name="adr250Form" property="adr250ViewAtiList">
            <div>
              <bean:write name="atiName" />
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.postalcode" />
      </th>
      <td class="w30">
        <logic:notEmpty name="adr250Form" property="adr250coPostno1">
          〒<bean:write name="adr250Form" property="adr250coPostno1" />-<bean:write name="adr250Form" property="adr250coPostno2" />
        </logic:notEmpty>
      </td>
      <th class="w15 no_w">
        <gsmsg:write key="cmn.prefectures" />
      </th>
      <td class="w30">
        <bean:write name="adr250Form" property="adr250coTdfkName" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.address1" />
      </th>
      <td class="w75" colspan="3">
        <bean:write name="adr250Form" property="adr250coAddress1" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.address2" />
      </th>
      <td class="w75" colspan="3">
        <bean:write name="adr250Form" property="adr250coAddress2" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.url" />
      </th>
      <td colspan="3">
        <a href="<bean:write name="adr250Form" property="adr250url" />" target="__blank">
          <bean:write name="adr250Form" property="adr250url" />
        </a>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w75" colspan="3">
        <bean:write name="adr250Form" property="adr250ViewBiko" filter="false"/>
      </td>
    </tr>
  </table>

  <logic:notEmpty name="adr250Form" property="abaList">
    <logic:iterate id="abaData" name="adr250Form" property="abaList">
      <table class="table-left">
        <tr class="bgC_lightGray">
          <td colspan="4" class="txt_l">
            <span class="classic-display"><img src="../address/images/classic/icon_parson.gif" alt="<gsmsg:write key="address.106" />"></span>
            <span class="original-display"><img src="../common/images/original/icon_company.png" alt="<gsmsg:write key="address.106" />"></span>
            <logic:equal name="abaData" property="adr250abaTypeNameDetail" value="address.122">
              <gsmsg:write key="address.122" />
            </logic:equal>
            <logic:equal name="abaData" property="adr250abaTypeNameDetail" value="address.123">
              <gsmsg:write key="address.123" />
            </logic:equal>
            <logic:equal name="abaData" property="adr250abaTypeNameDetail" value="address.124">
              <gsmsg:write key="address.124" />
            </logic:equal>
            <span class="p10"><bean:write name="abaData" property="adr250abaName" /></span>
          </td>
        </tr>
        <tr>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.postalcode" />
          </th>
          <td class="w35">
            <logic:notEmpty name="abaData" property="adr250abaPostno1">
              〒<bean:write name="abaData" property="adr250abaPostno1" />-<bean:write name="abaData" property="adr250abaPostno2" />
            </logic:notEmpty>
          </td>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.prefectures" />
          </th>
          <td class="w35">
            <bean:write name="abaData" property="adr250abaTdfkName" />
          </td>
        </tr>
        <tr>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.address1" />
          </th>
          <td colspan="3" class="w85">
            <bean:write name="abaData" property="adr250abaAddress1" />
          </td>
        </tr>
        <tr>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.address2" />
          </th>
          <td colspan="3" class="w85">
            <bean:write name="abaData" property="adr250abaAddress2" />
          </td>
        </tr>
        <tr>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.memo" />
          </th>
          <td colspan="3" class="w85">
            <bean:write name="abaData" property="adr250abaViewBiko" filter="false"/>
          </td>
        </tr>
      </table>
    </logic:iterate>
  </logic:notEmpty>
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