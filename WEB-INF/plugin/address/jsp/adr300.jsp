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
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr300.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="addressbook" /> <gsmsg:write key="cmn.default.setting" /></title>
</head>

<body onload="lmtEnableDisable();lmtEnableDisable2(<bean:write name="adr300Form" property="adr300PermitKbn" />);">
<html:form action="/address/adr300">
<input type="hidden" name="CMD" value="">
<html:hidden name="adr300Form" property="backScreen" />

<logic:notEmpty name="adr300Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr300Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr300Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr300Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr300Form" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr300Form" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="addressbook" /></span><gsmsg:write key="cmn.default.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('adr300kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('adr300back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
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
      <th class="w25">
        <gsmsg:write key="address.120" />
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio name="adr300Form" styleId="adr300MemDspKbn_01" property="adr300MemDspKbn" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.MEM_DSP_ADM) %>" onclick="lmtEnableDisable();" /><label for="adr300MemDspKbn_01" class="mr10"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="adr300Form" styleId="adr300MemDspKbn_02" property="adr300MemDspKbn" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.MEM_DSP_USR) %>" onclick="lmtEnableDisable();" /><label for="adr300MemDspKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </div>
        <span id="lmtinput">
          <gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.user.defaultset" />
        </span>
        <div class="mt5">
          <gsmsg:write key="cmn.reading" />：
          <html:select styleClass="mb5" property="adr300PermitKbn" onchange="lmtEnableDisable2(this[this.selectedIndex].value);">
            <html:optionsCollection name="adr300Form" property="adr300PermitKbnLabel" value="value" label="label" />
          </html:select>
        </div>
        <div>
          <gsmsg:write key="cmn.edit" />：
          <span id="lmtinput1"><gsmsg:write key="address.62" /></span>
          <span id="lmtinput2"><gsmsg:write key="group.designation" /></span>
          <span id="lmtinput3"><gsmsg:write key="cmn.user.specified" /></span>
          <span id="lmtinput4">
            <html:select property="adr300EditKbn">
              <html:optionsCollection name="adr300Form" property="adr300EditKbnLabel" value="value" label="label" />
            </html:select>
          </span>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('adr300kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('adr300back');">
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