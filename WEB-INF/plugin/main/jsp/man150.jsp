<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.man.GSConstMain" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man150.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onunload="windowClose();">
<html:form action="/main/man150">
<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man150.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('import');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKanriMenu');">
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
      <html:errors />
    </logic:messagesPresent>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man150.2" />
      </th>
      <td class="w80">
        <table class="table-noBorder w100">
          <tr>
            <td class="w20 txt_r fw_b">
              <gsmsg:write key="main.man002.3" />
            </td>
            <td class="w80 txt_l">
              <logic:empty name="man150Form" property="man150LicenseId">&nbsp;</logic:empty>
              <logic:notEmpty name="man150Form" property="man150LicenseId">
                <bean:write name="man150Form" property="man150LicenseId" />
              </logic:notEmpty>
            </td>
          </tr>
          <tr>
            <td class="w20 txt_r fw_b">
              <gsmsg:write key="main.man002.4" />
            </td>
            <td class="w80 txt_l">
              <logic:empty name="man150Form" property="man150LicenseCom">&nbsp;</logic:empty>
              <logic:notEmpty name="man150Form" property="man150LicenseCom">
                <bean:write name="man150Form" property="man150LicenseCom" />
              </logic:notEmpty>
            </td>
          </tr>
          <logic:notEmpty name="man150Form" property="man150PluginList">
            <logic:iterate id="plugin" name="man150Form" property="man150PluginList" scope="request" indexId="cnt">
              <tr>
                <td class="w20 txt_r fw_b">
                  <bean:write name="plugin" property="pluginName" />：
                </td>
                <td class="w80 txt_l">
                  <gsmsg:write key="cmn.period2" />&nbsp;<bean:write name="plugin" property="licenseLimit" />
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </table>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.src.26" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <span class="fs_13">
          <gsmsg:write key="main.man150.5" />
        </span>
        <attachmentFile:filearea
          mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
          pluginId="<%= GSConstMain.PLUGIN_ID_MAIN %>"
          tempDirId="man150" />
      </td>
    </tr>
  </table>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>