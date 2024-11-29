<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="man.restricteduse.plugin" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../main/js/man280.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body onload="selectUseKbn(); selectLimitType();">
<html:form action="/main/man280">
<input type="hidden" name="CMD" value="">
<html:hidden name="man280Form" property="menuEditOk" />
<html:hidden name="man280Form" property="man120pluginId" />
<html:hidden name="man280Form" property="man280initFlg" />
<html:hidden name="man280Form" property="man280backId" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.plugin.usage.restrictions" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backPluginList');">
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
  <div class="flo_l fw_b mt10 mb10 verAlignMid">
    <logic:equal name="man280Form" property="man280pluginKbn" value="0">
      <img src="../<bean:write name="man280Form" property="man120pluginId" />/images/classic/menu_icon_single.gif" id="img<bean:write name="man280Form" property="man120pluginId" />" class="wp25hp25 mr5 btn_classicImg-display main_listboxImg_border">
      <img src="../<bean:write name="man280Form" property="man120pluginId" />/images/original/menu_icon_single.png" id="img<bean:write name="man280Form" property="man120pluginId" />" class="wp25hp25 mr5 btn_originalImg-display">
    </logic:equal>
    <logic:notEqual name="man280Form" property="man280pluginKbn" value="0">
      <logic:equal name="man280Form" property="man280BinSid" value="0">
        <img src="../common/images/pluginImg/classic/menu_icon_plugin_default_25.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 mr5 btn_classicImg-display main_listboxImg_border">
        <img src="../common/images/pluginImg/original/menu_icon_plugin_default_50.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 mr5 btn_originalImg-display">
      </logic:equal>
      <logic:notEqual name="man280Form" property="man280BinSid" value="0">
        <img src="../main/man280.do?CMD=getImageFile&man120imgPluginId=<bean:write name="man280Form" property="man120pluginId" />" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 mr5 btn_classicImg-display main_listboxImg_border">
        <img src="../main/man280.do?CMD=getImageFile&man120imgPluginId=<bean:write name="man280Form" property="man120pluginId" />" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 mr5 btn_originalImg-display">
      </logic:notEqual>
    </logic:notEqual>
    <span class="fs_17 fw_b lh_normal">
      <bean:write name="man280Form" property="man280pluginName" />
    </span>
  </div>
  <logic:notEqual name="man280Form" property="man120pluginId" value="main">
    <table class="table-left w100">
      <tr>
        <th class="w100 bgC_header1 cl_fontOutline" colspan="2">
          <gsmsg:write key="main.man280.2" />
        </th>
      </tr>
      <tr>
        <th class="w20 no_w">
          <gsmsg:write key="cmn.admin" />
        </th>
        <td class="w80">
          <div>
            <gsmsg:write key="main.man280.3" />
          </div>
          <ui:usrgrpselector name="man280Form" property="man280AdminSidUI" styleClass="hp215" />
        </td>
      </tr>
    </table>
  </logic:notEqual>
  <table class="table-left w100">
    <tr>
      <th class="w100 bgC_header1 cl_fontOutline" colspan="2">
        <gsmsg:write key="main.plugin.usage.restrictions" />
      </th>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.plugin.usage.restrictions" />
      </th>
      <td class="w80">
        <div>
          <span class="verAlignMid">
            <html:radio name="man280Form" property="man280useKbn" styleId="useKbn0" value="0" onclick="selectUseKbn();" />
            <label for="useKbn0"><gsmsg:write key="main.man280.4" /></label>
          </span>
        </div>
        <div>
          <span class="verAlignMid">
            <html:radio name="man280Form" property="man280useKbn" styleId="useKbn1" value="1" onclick="selectUseKbn();" />
            <label for="useKbn1"><gsmsg:write key="main.man280.5" /></label>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <th id="pluginUseMember">
        <gsmsg:write key="cmn.howto.limit" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td id="pluginUseMember2">
        <div>
          <span class="verAlignMid">
            <html:radio name="man280Form" property="man280limitType" styleId="limitType0" value="0" onclick="selectLimitType();" />
            <label for="limitType0"><gsmsg:write key="main.man280.7" /></label>
          </span>
        </div>
        <div>
          <span class="verAlignMid">
            <html:radio name="man280Form" property="man280limitType" styleId="limitType1" value="1" onclick="selectLimitType();" />
            <label for="limitType1"><gsmsg:write key="main.man280.8" /></label>
          </span>
        </div>
        <div id="limit" class="mt10">
          <gsmsg:write key="main.man280.9" />
        </div>
        <div id="permit" class="mt10">
          <gsmsg:write key="main.man280.10" />
        </div>
        <ui:usrgrpselector name="man280Form" property="man280memberSidUI" styleClass="hp215" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backPluginList');">
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