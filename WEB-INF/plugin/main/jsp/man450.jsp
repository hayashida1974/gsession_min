<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.man.GSConstMain" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man450.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body onunload="windowClose();">
<html:form action="/main/man450">
<input type="hidden" name="CMD" value="">
<html:hidden property="man440GrpSid"/>
<!-- BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man450.1" />
    </li>
    <li>
      <button type="button" value="<gsmsg:write key="cmn.import" />" class="baseBtn" onClick="buttonPush('Man450_Import');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <gsmsg:write key="cmn.import" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('Man450_Back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </div>
  <div class="p10 bor1 bgC_cybozuAlert">
    <table class="w100">
      <tr>
        <td class="w100 fw_b pb20 fs_17" colspan="2">
          <gsmsg:write key="main.man440.1" />
        </td>
      </tr>
      <tr>
        <td class="txt_t">●</td>
        <td class="w100 txt_l"><gsmsg:write key="main.man450.7" /></td>
      </tr>
      <tr>
        <td class="txt_t">●</td>
        <td class="w100 txt_l"><gsmsg:write key="main.man450.8" /></td>
      </tr>
    </table>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man450.2" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <div>
          <button type="button" class="baseBtn ml0" value="<gsmsg:write key="cmn.attached" />" onClick="opnTemp('man450selectFiles', '<%=GSConstCommon.CMN110MODE_FILE_TANITU %>', '<%=GSConstMain.PLUGIN_ID_MAIN %>', 'man450');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
            <gsmsg:write key="cmn.attached" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_trash.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_trash.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </div>
        <html:select property="man450selectFiles" styleClass="wp200 ml0" size="1">
          <html:optionsCollection name="man450Form" property="man450FileLabelList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man450.3" />
      </th>
      <td class="w80">
        <bean:write name="man450Form" property="man450grpName" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man450.4" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <div class="tx_l">
          <gsmsg:write key="main.man450.5" />
        </div>
        <div class="txt_l no_w">
          <html:password property="man450password" styleClass="wp200" maxlength="256" />
        </div>
        <div class="txt_l mt10 verAlignMid">
          <html:checkbox styleClass="mr5" styleId="pswd_kbn" name="man450Form" property="man450passUpdateFlg" value="1" />
          <label for="pswd_kbn"><gsmsg:write key="main.man450.6" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.import" />" class="baseBtn" onClick="buttonPush('Man450_Import');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <gsmsg:write key="cmn.import" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('Man450_Back');">
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