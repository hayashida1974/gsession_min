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
<title>GROUPSESSION <gsmsg:write key="main.man002.19" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man340.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../main/css/main.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body class="">
<html:form action="/main/man340">
<input type="hidden" name="CMD" value="">
<html:hidden property="man340initFlg" />
<html:hidden property="man120pluginId" />
<html:hidden property="man340cmdMode" />
<html:hidden property="man340file" />
<html:hidden property="man340SaveFile" />
<html:hidden property="man340funcId" />
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.plugin" /><gsmsg:write key="cmn.add" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('man340kn');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="man340Form" property="man340cmdMode" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('man340_del');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man120');">
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
        <gsmsg:write key="cmn.plugin" /><gsmsg:write key="cmn.name3" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text maxlength="10" property="man340title" styleClass="wp300" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        URL<span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text styleClass="w100" maxlength="1000" property="man340url" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man340.5" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="man340Form" property="man340paramKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PARAM_KBN_NO) %>" styleId="man340paramKbn0"/>
          <label for="man340paramKbn0"><gsmsg:write key="cmn.noset" /></label>
          <html:radio name="man340Form" styleClass="ml10" property="man340paramKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PARAM_KBN_YES) %>" styleId="man340paramKbn1"/>
          <label for="man340paramKbn1"><gsmsg:write key="cmn.setting.do" /></label>
        </div>
        <div id="paramSetArea">
          <div class="settingForm_separator">
            <div class="verAlignMid">
              <span class="mr10 fw_b"><gsmsg:write key="main.man340.6" />：</span>
              <html:radio name="man340Form" styleId="sendKbn_01" property="man340sendKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.SEND_KBN_POST) %>" />
              <label for="sendKbn_01"><gsmsg:write key="main.man340.7" /></label>
              <html:radio name="man340Form" styleClass="ml10" styleId="sendKbn_02" property="man340sendKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.SEND_KBN_GET) %>" />
              <label for="sendKbn_02"><gsmsg:write key="main.man340.8" /></label>
            </div>
          </div>
          <table class="mt10">
            <tr class="border_none">
              <th class="wp200 bgC_header2 txt_c bor1">
                <gsmsg:write key="main.man340.9" />
              </th>
              <td class="wp100 border_none"></td>
              <th class="wp200 bgC_header2 txt_c bor1">
                <gsmsg:write key="main.man340.10" />
              </th>
            </tr>
            <logic:notEmpty name="man340Form" property="man340urlParamList">
              <% String[] lineParamName = {"paramName", "paramValue"}; %>
              <logic:iterate name="man340Form" property="man340urlParamList" id="urlParamMdl" indexId="idx">
                <% String lineNo = String.valueOf(idx.intValue()); %>
                <% String lineFrmName = "man340urlParamList[" + lineNo + "]."; %>
               <tr class="border_none">
                  <td class="txt_c bor_b1 bor_l1 bor_r1">
                    <html:text maxlength="100" property="<%= lineFrmName + lineParamName[0] %>" styleClass="w100" />
                  </td>
                  <td class="txt_c border_none">=</td>
                  <td class="txt_c bor_b1 bor_l1 bor_r1">
                    <html:text maxlength="1000" property="<%= lineFrmName + lineParamName[1] %>" styleClass="w100" />
                  </td>
                </tr>
              </logic:iterate>
            </logic:notEmpty>
          </table>
          <div class="mt10">
            <gsmsg:write key="main.man340.11" />
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man340.1" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="man340Form" property="man340openKbn" value="0" styleId="man340opnKbn0"/>
          <label for="man340opnKbn0"><gsmsg:write key="main.man340.2" /></label>
          <html:radio name="man340Form" styleClass="ml10" property="man340openKbn" value="1" styleId="man340opnKbn1"/>
          <label for="man340opnKbn1"><gsmsg:write key="main.man340.3" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.icon" />
      </th>
      <td class="w80">
        <div class="pluginSetting_iconSize">
          <logic:equal name="man340Form" property="man340file" value="">
            <img src="../common/images/pluginImg/classic/menu_icon_plugin_default_50.png" name="pitctImage" class="btn_classicImg-display" alt="<gsmsg:write key="cmn.icon" />">
            <img src="../common/images/pluginImg/original/menu_icon_plugin_default_50.png" name="pitctImage" class="btn_originalImg-display" alt="<gsmsg:write key="cmn.icon" />">
          </logic:equal>
          <logic:notEqual name="man340Form" property="man340file" value="">
            <img src="../main/man340.do?CMD=getImageFile" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" onload="initImageView('pitctImage');">
          </logic:notEqual>
        </div>
        <div>
          (<gsmsg:write key="main.man340.4" />)
        </div>
        <attachmentFile:filearea
          mode="<%= String.valueOf(GSConstCommon.CMN110MODE_GAZOU) %>"
          pluginId="<%= GSConstMain.PLUGIN_ID_MAIN %>"
          tempDirId="man340"
          delBtn="true"
          delBtnAction="buttonPush('delete');"
          fileList="false" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('man340kn');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="man340Form" property="man340cmdMode" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('man340_del');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man120');">
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