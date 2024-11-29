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
<meta name="format-detection" content="telephone=no">
<logic:equal name="adr020knForm" property="adr020viewFlg" value="1">
<title>GROUPSESSION <gsmsg:write key="address.src.2" /></title>
</logic:equal>
<logic:notEqual name="adr020knForm" property="adr020viewFlg" value="1">
<title>GROUPSESSION <gsmsg:write key="address.adr020kn.1" /></title>
</logic:notEqual>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
</head>

<body>

<html:form action="/address/adr020kn">

<logic:notEqual name="adr020knForm" property="adr020webmail" value="1">
<jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
</logic:notEqual>

<input type="hidden" name="CMD" value="">

<jsp:include page="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" />

<html:hidden property="adr020viewFlg" />
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

<html:hidden property="adr020webmail" />

<logic:notEmpty name="adr020knForm" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr020knForm" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr020knForm" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr020knForm" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr020knForm" property="adr020tantoList">
<logic:iterate id="tantoList" name="adr020knForm" property="adr020tantoList">
  <input type="hidden" name="adr020tantoList" value="<bean:write name="tantoList" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr020knForm" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr020knForm" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr020knForm" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr020knForm" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr020knForm" property="projectCmbList">
<logic:iterate id="project" name="adr020knForm" property="projectCmbList">
  <input type="hidden" name="projectCmbList" value="<bean:write name="project" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020tantoGroup" />

<logic:notEmpty name="adr020knForm" property="adr020label">
<logic:iterate id="label" name="adr020knForm" property="adr020label">
  <input type="hidden" name="adr020label" value="<bean:write name="label" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitView" />

<logic:notEmpty name="adr020knForm" property="adr020permitViewGroup">
<logic:iterate id="permitViewGroup" name="adr020knForm" property="adr020permitViewGroup">
  <input type="hidden" name="adr020permitViewGroup" value="<bean:write name="permitViewGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr020knForm" property="adr020permitViewUser">
<logic:iterate id="permitViewUser" name="adr020knForm" property="adr020permitViewUser">
  <input type="hidden" name="adr020permitViewUser" value="<bean:write name="permitViewUser" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr020permitViewUserGroup" />
<html:hidden property="adr020permitEdit" />

<logic:notEmpty name="adr020knForm" property="adr020permitEditGroup">
<logic:iterate id="permitEditGroup" name="adr020knForm" property="adr020permitEditGroup">
  <input type="hidden" name="adr020permitEditGroup" value="<bean:write name="permitEditGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr020knForm" property="adr020permitEditUser">
<logic:iterate id="permitEditUser" name="adr020knForm" property="adr020permitEditUser">
  <input type="hidden" name="adr020permitEditUser" value="<bean:write name="permitEditUser" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr020knForm" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr020knForm" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr110editAcoSid" />
<html:hidden property="adr110BackId" />

<html:hidden property="adr020permitEditUserGroup" />

<jsp:include page="/WEB-INF/plugin/address/jsp/adr330_hiddenParams.jsp" >
<jsp:param value="adr020knForm" name="thisFormName"/>
</jsp:include>

<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
    <logic:equal name="adr020knForm" property="adr020viewFlg" value="1">
      <gsmsg:write key="address.src.2" />
    </logic:equal>
    <logic:notEqual name="adr020knForm" property="adr020viewFlg" value="1">
      <gsmsg:write key="address.adr020kn.1" />
    </logic:notEqual>
    </li>
    <li>
      <div>
      <logic:equal name="adr020knForm" property="adr020viewFlg" value="1">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAddressList');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </logic:equal>
      <logic:notEqual name="adr020knForm" property="adr020viewFlg" value="1">
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backRegist');">
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
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>

    <table class="table-left">
    <tr>
    <th class="w15" colspan="2">
      <gsmsg:write key="cmn.name" />
    </th>
    <td class="w45">
      <bean:write name="adr020knForm" property="adr020unameSei" /> <bean:write name="adr020knForm" property="adr020unameMei" />
    </td>
    </tr>

    <tr>
    <th colspan="2">
      <gsmsg:write key="user.119" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020unameSeiKn" /> <bean:write name="adr020knForm" property="adr020unameMeiKn" />
    </td>
    </tr>

    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.select.company" />
    </th>
    <td>
      <gsmsg:write key="address.7" />：
      <bean:write name="adr020knForm" property="adr020companyCode" /><br>
      <bean:write name="adr020knForm" property="adr020companyName" />&nbsp;&nbsp;&nbsp;
      <bean:write name="adr020knForm" property="adr020companyBaseName" /><br>
    </td>
    </tr>

    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.affiliation" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020syozoku" />
    </td>
    </tr>
    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.post" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020knPositionName" />
    </td>
    </tr>

    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.mailaddress1" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020mail1" />&nbsp;
      <gsmsg:write key="cmn.comment" />：
      <bean:write name="adr020knForm" property="adr020mail1Comment" />
    </td>
    </tr>

    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.mailaddress2" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020mail2" />&nbsp;
        <gsmsg:write key="cmn.comment" />：
      <bean:write name="adr020knForm" property="adr020mail2Comment" />
    </td>
    </tr>
    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.mailaddress3" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020mail3" />&nbsp;
      <gsmsg:write key="cmn.comment" />：
      <bean:write name="adr020knForm" property="adr020mail3Comment" />
    </td>
    </tr>

    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.tel1" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020tel1" />&nbsp;
      <logic:notEmpty name="adr020knForm" property="adr020tel1Nai">
        <gsmsg:write key="address.58" />：
      <bean:write name="adr020knForm" property="adr020tel1Nai" />
      </logic:notEmpty>
      <logic:notEmpty name="adr020knForm" property="adr020tel1Comment">
        <gsmsg:write key="cmn.comment" />：
      <bean:write name="adr020knForm" property="adr020tel1Comment" />
      </logic:notEmpty>
    </td>
    </tr>

    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.tel2" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020tel2" />&nbsp;
      <logic:notEmpty name="adr020knForm" property="adr020tel2Nai">
        <gsmsg:write key="address.58" />：
      <bean:write name="adr020knForm" property="adr020tel2Nai" />
      </logic:notEmpty>
      <logic:notEmpty name="adr020knForm" property="adr020tel2Comment">
        <gsmsg:write key="cmn.comment" />：
      <bean:write name="adr020knForm" property="adr020tel2Comment" />
      </logic:notEmpty>
    </td>
    </tr>
    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.tel3" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020tel3" />&nbsp;
      <logic:notEmpty name="adr020knForm" property="adr020tel3Nai">
        <gsmsg:write key="address.58" />：
      <bean:write name="adr020knForm" property="adr020tel3Nai" />
      </logic:notEmpty>
      <logic:notEmpty name="adr020knForm" property="adr020tel3Comment">
        <gsmsg:write key="cmn.comment" />：
      <bean:write name="adr020knForm" property="adr020tel3Comment" />
      </logic:notEmpty>
    </td>
    </tr>

    <tr>
    <th colspan="2">
      <gsmsg:write key="user.143" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020fax1" />
      <logic:notEmpty name="adr020knForm" property="adr020fax1Comment">&nbsp;
        <gsmsg:write key="cmn.comment" />：
      <bean:write name="adr020knForm" property="adr020fax1Comment" />
      </logic:notEmpty>
    </td>
    </tr>

    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.cmn100.2" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020fax2" />
      <logic:notEmpty name="adr020knForm" property="adr020fax2Comment">&nbsp;
        <gsmsg:write key="cmn.comment" />：
      <bean:write name="adr020knForm" property="adr020fax2Comment" />
      </logic:notEmpty>
    </td>
    </tr>

    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.cmn100.3" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020fax3" />
      <logic:notEmpty name="adr020knForm" property="adr020fax3Comment">&nbsp;
        <gsmsg:write key="cmn.comment" />：
        <bean:write name="adr020knForm" property="adr020fax3Comment" />
      </logic:notEmpty>
    </td>
    </tr>

    <tr>
    <th rowspan="4">
      <gsmsg:write key="cmn.address" />
    </th>
    <th>
      <gsmsg:write key="cmn.postalcode" />
    </th>
    <td>
      <logic:notEmpty name="adr020knForm" property="adr020postno1">
      <logic:notEmpty name="adr020knForm" property="adr020postno2">
        <bean:write name="adr020knForm" property="adr020postno1" />-<bean:write name="adr020knForm" property="adr020postno2" />
      </logic:notEmpty>
      </logic:notEmpty>
    </td>
    </tr>

    <tr>
    <th>
      <gsmsg:write key="cmn.prefectures" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020knTdfkName" />
    </td>
    </tr>

    <tr>
    <th>
      <gsmsg:write key="cmn.address1" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020address1" />
    </td>
    </tr>
    <tr>
    <th>
      <gsmsg:write key="cmn.address2" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020address2" />
    </td>
    </tr>

    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.memo" />
    </th>
    <td>
      <bean:write name="adr020knForm" property="adr020knViewBiko" filter="false" />
    </td>
    </tr>

    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.label" />
      </th>
      <td>
        <logic:notEmpty name="adr020knForm" property="selectLabelList">
          <logic:iterate id="labelData" name="adr020knForm" property="selectLabelList" indexId="idx">
            <div>
              <bean:write name="labelData" property="albName" />
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>

    <tr>
      <th colspan="2">
        <gsmsg:write key="address.46" />
      </th>
      <td>
        <logic:notEmpty name="adr020knForm" property="selectTantoCombo">
          <logic:iterate id="user" name="adr020knForm" property="selectTantoCombo"  >
            <bean:define id="userValue" name="user" property="value" />
            <bean:define id="mukoUserClass" value="" />
            <logic:equal name="user" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUser" /></logic:equal>
            <span class="<%=mukoUserClass%>"><bean:write name="user" property="label" /></span><br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
    <th colspan="2">
      <gsmsg:write key="address.61" />
    </th>
    <td>
      <bean:define id="permitView" name="adr020knForm" property="adr020permitView" type="java.lang.Integer"/>
      <% if (permitView.intValue() == jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_NORESTRICTION) { %>
      <gsmsg:write key="cmn.no.setting.permission" />
      <% } else if (permitView.intValue() == jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_GROUP) { %>
      <gsmsg:write key="group.designation" />：
      <br>
      <% } else if (permitView.intValue() == jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_USER) { %>
      <gsmsg:write key="cmn.user.specified" />：
      <br>
      <% } else { %>
      <gsmsg:write key="address.62" />
      <% } %>
      <logic:notEmpty name="adr020knForm" property="selectPermitViewUser">
      <logic:iterate id="user" name="adr020knForm" property="selectPermitViewUser"  >
        <bean:define id="mukoUserClass" value="" />
        <logic:equal name="user" property="usrUkoFlg" value="1">
        <bean:define id="mukoUserClass" value="mukoUser" />
        </logic:equal>
         <span class="<%=mukoUserClass%>"><bean:write name="user" property="label" /></span><br>
      </logic:iterate>
      </logic:notEmpty>

      <logic:equal name="adr020knForm" property="adr020permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_GROUP) %>">
        <logic:notEmpty name="adr020knForm" property="adr020knPermitViewList">
          <logic:iterate id="permitViewName" name="adr020knForm" property="adr020knPermitViewList">
            <bean:write name="permitViewName" /><br>
          </logic:iterate>
        </logic:notEmpty>
      </logic:equal>
    </td>
    </tr>
    <tr>
    <th colspan="2">
      <gsmsg:write key="cmn.edit.permissions" />
    </th>
    <td>
      <bean:define id="permitEdit" name="adr020knForm" property="adr020permitEdit" type="java.lang.Integer" />
      <% if (permitEdit.intValue() == jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_NORESTRICTION) { %>
      <gsmsg:write key="cmn.no.setting.permission" />
      <% } else if (permitEdit.intValue() == jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_GROUP) { %>
      <gsmsg:write key="group.designation" />：
      <br>
      <% } else if (permitEdit.intValue() == jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_USER) { %>
      <gsmsg:write key="cmn.user.specified" />：
      <br>
      <% } else { %>
      <gsmsg:write key="address.62" />
      <% } %>
      <logic:notEmpty name="adr020knForm" property="selectPermitEditUser">

      <logic:iterate id="user" name="adr020knForm" property="selectPermitEditUser"  >
        <bean:define id="mukoUserClass" value="" />
        <logic:equal name="user" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUser" /></logic:equal>
        <span class="<%=mukoUserClass%>"><bean:write name="user" property="label" /></span><br>
      </logic:iterate>
      </logic:notEmpty>

      <logic:equal name="adr020knForm" property="adr020permitEdit" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_GROUP) %>">
        <logic:notEmpty name="adr020knForm" property="adr020knPermitEditList">
          <logic:iterate id="permitEditName" name="adr020knForm" property="adr020knPermitEditList">
            <bean:write name="permitEditName" /><br>
          </logic:iterate>
        </logic:notEmpty>
      </logic:equal>

    </td>
    </tr>
    </table>

  <div class="footerBtn_block">
    <logic:equal name="adr020knForm" property="adr020viewFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAddressList');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:equal>
    <logic:notEqual name="adr020knForm" property="adr020viewFlg" value="1">
      <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
        <gsmsg:write key="cmn.final" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backRegist');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:notEqual>
  </div>
</div>

</html:form>

<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />

</body>
</html:html>