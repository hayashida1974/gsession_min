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
<title>GROUPSESSION Database Administrator</title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body class="body_03">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<html:form action="/address/adr030">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<logic:notEmpty name="adr030Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr030Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr030Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr030Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr030Form" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr030Form" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>

<!--　BODY -->

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.addressbook" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="settingList">
    <dl>
      <dt onClick="return buttonPush('address');">
        <span class="settingList_title"><gsmsg:write key="address.adr330.1" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="address.adr330.2" /></div>
      </dd>
    </dl>

    <dl>
      <dt onClick="return buttonPush('iniset');">
        <span class="settingList_title"><gsmsg:write key="cmn.default.setting" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="address.83" /></div>
      </dd>
    </dl>

    <dl>
      <dt onClick="return buttonPush('powedit');">
        <span class="settingList_title"><gsmsg:write key="cmn.setting.permissions" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="address.adr030.1" /></div>
      </dd>
    </dl>

    
    <dl>
      <dt onClick="return buttonPush('arestset');">
        <span class="settingList_title"><gsmsg:write key="address.adr320.1" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="address.adr320.2" /></div>
      </dd>
    </dl>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>
