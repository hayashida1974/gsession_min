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
<title>GROUPSESSION <gsmsg:write key="main.man002.19" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man121.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body class="body_03" onload="parent.menu.location='../common/cmn003.do'">
<html:form action="/main/man121">
<input type="hidden" name="CMD" value="conf">
<html:hidden property="man120pluginId" />
<html:hidden property="man280backId" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--BODY -->
<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man002.19" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdminMenu');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <div class="wrapperContent-top">
    <div class="txt_l">
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
    </div>
  </div>
  <div class="wrapperContent-2column">
    <div class="side_multi-left bgC_none">
      <table class="table-top">
        <tr>
          <th class="w100 txt_l table_title-color">
            <gsmsg:write key="man.menu" />
          </th>
        </tr>
        <tr class="js_listHover cursor_p" data-cmd="plugin">
          <td class="w100 txt_l js_listMenuClick bgC_tableCell">
            <span class="cl_linkDef"><gsmsg:write key="cmn.plugin" /></span>
          </td>
        </tr>
        <tr class="js_listHover cursor_p">
          <td class="w100 txt_l bgC_tableCell">
            <span class="cl_linkDef"><gsmsg:write key="main.man120.1" /></span>
          </td>
        </tr>
        <tr class="js_listHover cursor_p" data-cmd="dspSettei">
          <td class="w100 txt_l js_listMenuClick bgC_tableCell">
            <span class="cl_linkDef"><gsmsg:write key="main.man120.2" /></span>
          </td>
        </tr>
      </table>
    </div>
    <div class="main">
      <table class="table-top table_col-even w100">
        <tr>
          <th class="w100 table_title-color txt_l" colspan="5">
            <gsmsg:write key="main.man121.1" />
          </th>
        </tr>
        <tr>
          <th class="w20 bgC_header2 no_w cl_fontBody">
            <gsmsg:write key="cmn.plugin" />
          </th>
          <th class="w30 bgC_header2 no_w cl_fontBody">
            <gsmsg:write key="cmn.admin" />
          </th>
          <th class="w15 bgC_header2 no_w cl_fontBody">
            <gsmsg:write key="cmn.howto.limit" />
          </th>
          <th class="w30 bgC_header2 no_w cl_fontBody">
            <gsmsg:write key="cmn.target" />
          </th>
          <th class="w5 bgC_header2 no_w">
          </th>
        </tr>
        <% boolean admGroupFlg = false; %>
        <logic:notEmpty name="man121Form" property="man121dspList" >
          <logic:iterate id="dspModel" name="man121Form" property="man121dspList" indexId="idx">
            <tr>
            <td>
              <logic:equal name="dspModel" property="pluginKbn" value="0">
                <img src="<bean:write name="dspModel" property="iconClassic" />" class="wp25hp25 btn_classicImg-display main_listboxImg_border">
                <img src="<bean:write name="dspModel" property="icon" />" class="wp25hp25 btn_originalImg-display">
              </logic:equal>
              <logic:notEqual name="dspModel" property="pluginKbn" value="0">
                <logic:equal name="dspModel" property="pluginBinSid" value="0">
                  <img src="../common/images/pluginImg/classic/menu_icon_plugin_default_25.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_classicImg-display main_listboxImg_border">
                  <img src="../common/images/pluginImg/original/menu_icon_plugin_default_50.png	" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_originalImg-display">
                </logic:equal>
                <logic:notEqual name="dspModel" property="pluginBinSid" value="0">
                  <img src="../main/man121.do?CMD=getImageFile&man120imgPluginId=<bean:write name="dspModel" property="pluginId" />" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_classicImg-display main_listboxImg_border">
                  <img src="../main/man121.do?CMD=getImageFile&man120imgPluginId=<bean:write name="dspModel" property="pluginId" />" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_originalImg-display">
                </logic:notEqual>
              </logic:notEqual>
              <bean:write name="dspModel" property="pluginName" />
            </td>
            <td>
              <% admGroupFlg = false; %>
              <logic:notEmpty name="dspModel" property="admGroupNameList">
                <% admGroupFlg = true; %>
                <gsmsg:write key="cmn.group" />:
                <logic:iterate id="adminData" name="dspModel" property="admGroupNameList">
                  <br>　　<bean:write name="adminData" property="label" />
                </logic:iterate>
              </logic:notEmpty>
              <logic:notEmpty name="dspModel" property="admUserNameList">
                <% if (admGroupFlg) { %><br><% } %>
                <gsmsg:write key="cmn.user" />:
                <logic:iterate id="adminData" name="dspModel" property="admUserNameList">
                  <br>　　<span class = "<logic:equal name="adminData" property="usrUkoFlg" value="1">mukoUser</logic:equal>"><bean:write name="adminData" property="label" /></span>
                </logic:iterate>
              </logic:notEmpty>
            </td>
            <td>
              <logic:equal name="dspModel" property="pctType" value="0">
                <gsmsg:write key="main.man121.3" /><br><gsmsg:write key="main.man121.4" />
              </logic:equal>
              <logic:equal name="dspModel" property="pctType" value="1">
                <gsmsg:write key="main.man121.5" /><br><gsmsg:write key="main.man121.6" />
              </logic:equal>
              <logic:equal name="dspModel" property="pctType" value="2">
                <gsmsg:write key="man.no.limit" />
              </logic:equal>
            </td>
            <td class="txt_l">
               <bean:write name="dspModel" property="taisyo" />
            </td>
            <td class="txt_c">
              <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.change" />" onClick="editClick('<bean:write name="dspModel" property="pluginId" />');">
                <img src="../common/images/classic/icon_edit_2.png" class="btn_classicImg-display">
                <img src="../common/images/original/icon_edit.png" class="btn_originalImg-display">
                <gsmsg:write key="cmn.change" />
              </button>
            </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>
      <gsmsg:write key="main.man121.7" />
    </div>
  </div>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdminMenu');">
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