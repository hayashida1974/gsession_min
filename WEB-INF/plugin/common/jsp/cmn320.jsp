<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.display.settings" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn320.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="changeEnableDisable();">
  <html:form action="/common/cmn320">

    <input type="hidden" name="CMD" value="">
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="admin_icon" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="admin_icon" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.admin.setting" /></li>
        <li class="pageTitle_subFont"><gsmsg:write key="cmn.display.settings" /></li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="back" onClick="buttonPush('update');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="back" onClick="buttonPush('cmn320Back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="back">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="back">
              <gsmsg:write key="cmn.back" />
            </button>
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
          <th class="w25"><gsmsg:write key="cmn.cmn320.01" /></th>
          <td class="w75">
            <div><gsmsg:write key="cmn.cmn320.02" /></div>
            <span class="verAlignMid">
              <html:radio name="cmn320Form" styleId="cmn320RokuyoDspKbn_01" property="cmn320RokuyoDspKbn" value="<%= String.valueOf(GSConst.SETTING_ADM) %>" onclick="changeEnableDisable();" />
              <label for="cmn320RokuyoDspKbn_01"><gsmsg:write key="cmn.set.the.admin" /></label>
              <html:radio name="cmn320Form" styleId="cmn320RokuyoDspKbn_02" styleClass="ml10" property="cmn320RokuyoDspKbn" value="<%= String.valueOf(GSConst.SETTING_USR) %>" onclick="changeEnableDisable();" />
              <label for="cmn320RokuyoDspKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
            </span>
            <div class="settingForm_separator" id="rokuyouEditArea">
              <span class="verAlignMid">
                <html:radio styleId="cmn320RokuyoDsp_01" name="cmn320Form" property="cmn320RokuyoDsp" value="<%= String.valueOf(GSConst.DSP_NOT) %>" />
                <label for="cmn320RokuyoDsp_01"><gsmsg:write key="cmn.dont.show" /></label>
                <html:radio styleId="cmn320RokuyoDsp_02" name="cmn320Form" property="cmn320RokuyoDsp" value="<%= String.valueOf(GSConst.DSP_OK) %>" styleClass="ml10" />
                <label for="cmn320RokuyoDsp_02"><gsmsg:write key="cmn.display.ok"/></label>
              </span>
            </div>
          </td>
        </tr>
      </table>
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('update');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cmn320Back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>
</body>
</html:html>