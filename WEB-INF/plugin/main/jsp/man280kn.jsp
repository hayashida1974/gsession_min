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
<title>GROUPSESSION <gsmsg:write key="man.restricteduse.plugin.kn" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../main/js/man280kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="initPluginUseConfirm();">

<html:form action="/main/man280kn">
<input type="hidden" name="CMD" value="">
<html:hidden name="man280knForm" property="man120pluginId" />
<html:hidden name="man280knForm" property="man280initFlg" />
<html:hidden name="man280knForm" property="man280useKbn" />
<html:hidden name="man280knForm" property="man280limitType" />
<html:hidden name="man280knForm" property="man280backId" />
<logic:notEmpty name="man280knForm" property="man280memberSid">
  <logic:iterate id="usid" name="man280knForm" property="man280memberSid">
    <input type="hidden" name="man280memberSid" value="<bean:write name="usid" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="man280knForm" property="man280AdminSid">
  <logic:iterate id="usid" name="man280knForm" property="man280AdminSid">
    <input type="hidden" name="man280AdminSid" value="<bean:write name="usid" />">
  </logic:iterate>
</logic:notEmpty>

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
      <gsmsg:write key="main.man280kn.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToInput');">
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
  <div class="flo_l txt_l fw_b mt10 mb10 verAlignMid">
    <logic:equal name="man280knForm" property="man280pluginKbn" value="0">
      <img src="../<bean:write name="man280knForm" property="man120pluginId" />/images/classic/menu_icon_single.gif" id="img<bean:write name="man280knForm" property="man120pluginId" />" class="wp25hp25 mr5 btn_classicImg-display main_listboxImg_border">
      <img src="../<bean:write name="man280knForm" property="man120pluginId" />/images/original/menu_icon_single.png" id="img<bean:write name="man280knForm" property="man120pluginId" />" class="wp25hp25 mr5 btn_originalImg-display">
    </logic:equal>
    <logic:notEqual name="man280knForm" property="man280pluginKbn" value="0">
      <logic:equal name="man280knForm" property="man280BinSid" value="0">
        <img src="../common/images/pluginImg/classic/menu_icon_plugin_default_25.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_classicImg-display main_listboxImg_border">
        <img src="../common/images/pluginImg/original/menu_icon_plugin_default_50.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_originalImg-display">
      </logic:equal>
      <logic:notEqual name="man280knForm" property="man280BinSid" value="0">
        <img src="../main/man280.do?CMD=getImageFile&man120imgPluginId=<bean:write name="man280knForm" property="man120pluginId" />" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_classicImg-display main_listboxImg_border">
        <img src="../main/man280.do?CMD=getImageFile&man120imgPluginId=<bean:write name="man280knForm" property="man120pluginId" />" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_originalImg-display">
      </logic:notEqual>
    </logic:notEqual>
    <span class="fs_17 fw_b lh_normal">
      <bean:write name="man280knForm" property="man280pluginName" />
    </span>
  </div>
  <logic:notEqual name="man280knForm" property="man120pluginId" value="main">
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
        <% boolean admGroupFlg = false; %>
        <logic:notEmpty name="man280knForm" property="man280knAdmGroupNameList">
          <% admGroupFlg = true; %>
          <span class="fw_b">
            <gsmsg:write key="cmn.group" />
          </span>
          <logic:iterate id="adminData" name="man280knForm" property="man280knAdmGroupNameList">
            <div class="ml10">
              <bean:write name="adminData" property="label" />
            </div>
          </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="man280knForm" property="man280knAdmUserNameList">
           <% if (admGroupFlg) { %><div class="mt10"></div><% } %>
          <span class="fw_b">
            <gsmsg:write key="cmn.user" />
          </span>
          <logic:iterate id="adminData" name="man280knForm" property="man280knAdmUserNameList">
            <div class="ml10">
              <span class="<logic:equal name="adminData" property="usrUkoFlg" value="1">mukoUser</logic:equal>"><bean:write name="adminData" property="label" /></span>
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
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
        <logic:equal name="man280knForm" property="man280useKbn" value="0">
          <gsmsg:write key="main.man280.4" />
        </logic:equal>
        <logic:equal name="man280knForm" property="man280useKbn" value="1">
         <gsmsg:write key="main.man280.5" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th id="pluginUseMember" class="w20 no_w">
        <gsmsg:write key="cmn.howto.limit" />
      </th>
      <td id="pluginUseMember2" class="w80">
        <logic:equal name="man280knForm" property="man280limitType" value="0">
          <div class="mb10">
            <gsmsg:write key="main.man280.7" />
          </div>
        </logic:equal>
        <logic:equal name="man280knForm" property="man280limitType" value="1">
          <div class="mb10">
            <gsmsg:write key="main.man280.8" />
          </div>
        </logic:equal>
        <% boolean groupFlg = false; %>
        <logic:notEmpty name="man280knForm" property="man280knMemGroupNameList">
          <% groupFlg = true; %>
          <span class="fw_b">
            <gsmsg:write key="cmn.group" />
          </span>
          <logic:iterate id="memberData" name="man280knForm" property="man280knMemGroupNameList">
            <div class="ml10">
              <bean:write name="memberData" property="label" />
            </div>
          </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="man280knForm" property="man280knMemUserNameList">
          <% if (groupFlg) { %><div class="mt10"></div><% } %>
          <span class="fw_b">
            <gsmsg:write key="cmn.user" />
          </span>
          <logic:iterate id="memberData" name="man280knForm" property="man280knMemUserNameList">
            <div class="ml10">
              <span class="<logic:equal name="memberData" property="usrUkoFlg" value="1">mukoUser</logic:equal>"><bean:write name="memberData" property="label" /></span>
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToInput');">
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