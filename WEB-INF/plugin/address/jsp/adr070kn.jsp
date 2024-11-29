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
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="address.adr070kn.1" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/address/adr070kn">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<input type="hidden" name="helpPrm" value="<bean:write name="adr070knForm" property="adr070cmdMode" />">

<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>

<html:hidden property="adr070init" name="adr070Form"/>

<logic:notEmpty name="adr070knForm" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr070knForm" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr070knForm" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr070knForm" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr070knForm" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr070knForm" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr070knForm" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr070knForm" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr070selectCompany" />
<html:hidden property="adr070selectCompanyBase" />
<html:hidden property="adr070permitView" />
<html:hidden property="adr070tantoGroup" />

<logic:notEmpty name="adr070knForm" property="adr070permitViewGroup">
<logic:iterate id="permitViewGroup" name="adr070knForm" property="adr070permitViewGroup">
  <input type="hidden" name="adr070permitViewGroup" value="<bean:write name="permitViewGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr070knForm" property="adr070permitViewUser">
<logic:iterate id="permitViewUser" name="adr070knForm" property="adr070permitViewUser">
  <input type="hidden" name="adr070permitViewUser" value="<bean:write name="permitViewUser" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr070permitViewUserGroup" />
<html:hidden property="adr070permitEdit" />


<logic:notEmpty name="adr070knForm" property="adr070permitEditGroup">
<logic:iterate id="permitEditGroup" name="adr070knForm" property="adr070permitEditGroup">
  <input type="hidden" name="adr070permitEditGroup" value="<bean:write name="permitEditGroup" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr070knForm" property="adr070permitEditUser">
<logic:iterate id="permitEditUser" name="adr070knForm" property="adr070permitEditUser">
  <input type="hidden" name="adr070permitEditUser" value="<bean:write name="permitEditUser" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr070knForm" property="adr070tantoList">
<logic:iterate id="tanto" name="adr070knForm" property="adr070tantoList">
  <input type="hidden" name="adr070tantoList" value="<bean:write name="tanto" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr070knForm" property="projectCmbList">
<logic:iterate id="project" name="adr070knForm" property="projectCmbList">
  <input type="hidden" name="projectCmbList" value="<bean:write name="project" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr070knForm" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr070knForm" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="adr070permitEditUserGroup" />
<html:hidden property="adr070cmdMode" />
<html:hidden property="adr070updateFlg" />


<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li>
      <gsmsg:write key="cmn.addressbook" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.adr070kn.1" />
    </li>
    <li>
      <div>
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
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">

<table class="table-left">
  <%-- 取り込みファイル --%>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.capture.file" />
      </th>
      <td class="w75">
        <bean:write name="adr070knForm" property="adr070knFileName" />
      </td>
    </tr>
  <%-- 取り込みアドレス氏名 --%>
    <tr>
      <th>
        <gsmsg:write key="address.adr070kn.2" />
      </th>
      <td>
        <logic:notEmpty name="adr070knForm" property="adr070knNameList">
          <logic:iterate id="userName" name="adr070knForm" property="adr070knNameList">
            <bean:write name="userName" /><br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  <%-- 会社 --%>
  <logic:notEqual name="adr070knForm" property="adr070cmdMode" value="1">
    <tr>
      <th>
        <gsmsg:write key="address.139" />
      </th>
      <td>
        <div>
          <gsmsg:write key="address.139" />&nbsp;:&nbsp;
          <bean:write name="adr070knForm" property="adr070knCompanyName" />
        </div>
        <div>
          <gsmsg:write key="address.10" />&nbsp;:&nbsp;
          <bean:write name="adr070knForm" property="adr070knCompanyBaseName" />
        </div>
      </td>
    </tr>
  </logic:notEqual>
  <%-- 取り込み会社名 --%>
  <logic:equal name="adr070knForm" property="adr070cmdMode" value="1">
    <tr>
      <th>
        <gsmsg:write key="address.adr120kn.2" />
      </th>
      <td>
        <logic:notEmpty name="adr070knForm" property="adr070knComList">
        <logic:iterate id="companyName" name="adr070knForm" property="adr070knComList">
          <bean:write name="companyName" /><br>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  </logic:equal>
  <%-- 役職 --%>
  <logic:notEmpty name="adr070knForm" property="adr070knPositionList">
    <tr>
      <th>
        <gsmsg:write key="cmn.post" />
      </th>
      <td>
        <span class="cl_fontWarn fs_12">
          <gsmsg:write key="address.adr070kn.3" /><br>
          <gsmsg:write key="address.adr070kn.4" /><br>
        </span>
        <logic:iterate id="posName" name="adr070knForm" property="adr070knPositionList">
        <bean:write name="posName"/><br>
        </logic:iterate>
      </td>
    </tr>
  </logic:notEmpty>
  <%-- 上書き --%>
  <logic:equal name="adr070knForm" property="adr070cmdMode" value="1">
    <tr>
      <th>
        <gsmsg:write key="cmn.overwrite" />
      </th>
      <td>
        <logic:equal name="adr070knForm" property="adr070updateFlg" value="1" >
          <gsmsg:write key="address.109" />
        </logic:equal>
      </td>
    </tr>
  </logic:equal>
  <%-- 担当者 --%>
    <tr>
      <th>
        <gsmsg:write key="cmn.staff" />
      </th>
      <td>
        <logic:notEmpty name="adr070knForm" property="selectTantoCombo">
          <logic:iterate id="tanto" name="adr070knForm" property="selectTantoCombo" indexId="idx">
          <bean:define id="mukoUserClass" value="" />
          <logic:equal name="tanto" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUserOption" /></logic:equal>
          <span class="<%=mukoUserClass%>" ><bean:write name="tanto" property="label" /></span><br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  <%-- 閲覧権限 --%>
    <tr>
      <th>
        <gsmsg:write key="address.61" />
      </th>
      <td>
        <bean:define id="permitView" name="adr070knForm" property="adr070permitView" type="java.lang.Integer"/>
          <% if (permitView.intValue() == jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_NORESTRICTION) { %>
            <gsmsg:write key="cmn.no.setting.permission" />
          <% } else if (permitView.intValue() == jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_GROUP) { %>
            <gsmsg:write key="group.designation" />：
            <br>
            <logic:iterate id="tanto" name="adr070knForm" property="selectPermitViewGroup" indexId="idx">
              <bean:write name="tanto" property="label" /><br>
            </logic:iterate>
          <% } else if (permitView.intValue() == jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_USER) { %>
            <gsmsg:write key="cmn.user.specified" />：
            <br>
              <logic:iterate id="user" name="adr070knForm" property="selectPermitViewUser" indexId="idx">
                <bean:define id="mukoUserClass" value="" />
                <logic:equal name="user" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUserOption" /></logic:equal>
               <span class="<%=mukoUserClass%>" ><bean:write name="user" property="label" /></span><br>
             </logic:iterate>
         <% } else { %>
           <gsmsg:write key="address.62" />
         <% } %>
      </td>
    </tr>
  <%-- 編集権限 --%>
    <tr>
      <th>
        <gsmsg:write key="cmn.edit.permissions" />
      </th>
      <td>
        <bean:define id="permitEdit" name="adr070knForm" property="adr070permitEdit" type="java.lang.Integer" />
          <% if (permitEdit.intValue() == jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_NORESTRICTION) { %>
            <span><gsmsg:write key="cmn.no.setting.permission" /></span>
          <% } else if (permitEdit.intValue() == jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_GROUP) { %>
            <gsmsg:write key="group.designation" />：
            <br>
            <logic:iterate id="tanto" name="adr070knForm" property="selectPermitEditGroup" indexId="idx">
              <bean:write name="tanto" property="label" /><br>
            </logic:iterate>
          <% } else if (permitEdit.intValue() == jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_USER) { %>
            <gsmsg:write key="cmn.user.specified" />：
            <br>
            <logic:iterate id="user" name="adr070knForm" property="selectPermitEditUser" indexId="idx">
              <bean:define id="mukoUserClass" value="" />
              <logic:equal name="user" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUserOption" /></logic:equal>
              <span class="<%=mukoUserClass%>" ><bean:write name="user" property="label" /></span><br>
            </logic:iterate>
         <% } else { %>
           <gsmsg:write key="address.62" />
         <% } %>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
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
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>
