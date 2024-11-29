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
<script src="../main/js/man120.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="stopCloseWindow();parent.menu.location='../common/cmn003.do'">
<html:form action="/main/man120">
<input type="hidden" name="CMD" value="conf">
<html:hidden property="man120pluginId" />
<logic:notEmpty name="man120Form" property="man120notViewMenuList" scope="request">
  <logic:iterate id="ulnotBean" name="man120Form" property="man120notViewMenuList" scope="request">
    <input type="hidden" name="man120notViewMenuList" value="<bean:write name="ulnotBean" />">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="man120Form" property="man120viewMenuLabel">
  <logic:iterate id="viewMenu" name="man120Form" property="man120viewMenuLabel">
    <input type="hidden" name="man120viewMenuList" value="<bean:write name="viewMenu" property="pluginId" />">
  </logic:iterate>
</logic:notEmpty>

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
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="addUplgClick();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
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
  <div class="wrapperContent-top txt_r">
    <logic:equal name="man120Form" property="menuEditOk" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('up');">
        <gsmsg:write key="cmn.up" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('down');">
        <gsmsg:write key="cmn.down" />
      </button>
    </logic:equal>
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
        <tr class="js_listHover cursor_p">
          <td class="w100 txt_l bgC_tableCell">
            <span class="cl_linkDef"><gsmsg:write key="cmn.plugin" /></span>
          </td>
        </tr>
        <tr class="js_listHover cursor_p" data-cmd="seigenSettei">
          <td class="w100 txt_l js_listMenuClick bgC_tableCell">
            <span class="cl_linkDef"><gsmsg:write key="main.man120.1" /></span>
          </td>
        </tr>
        <tr class="js_listHover cursor_p" data-cmd="dspSettei">
          <td class="w100 txt_l js_listMenuClick bgC_tableCell">
            <span class="cl_linkDef"><gsmsg:write key="main.man120.2" /></span>
          </td>
        </tr>
      </table>
      <table class="table-top">
        <tr>
          <th class="table_title-color txt_l">
            <gsmsg:write key="main.man120.3" />
          </th>
        </tr>
        <logic:notEmpty name="man120Form" property="man120notViewMenuLabel" >
          <logic:iterate id="notUsePlugin" name="man120Form" property="man120notViewMenuLabel" >
            <tr>
              <td class="bgC_tableCell">
                <logic:equal name="notUsePlugin" property="pluginKbn" value="0">
                  <img src="<bean:write name="notUsePlugin" property="iconClassic" />" property="pluginId" class="wp25hp25 btn_classicImg-display main_listboxImg_border">
                  <img src="<bean:write name="notUsePlugin" property="icon" />" property="pluginId" class="wp25hp25 btn_originalImg-display">
                </logic:equal>
                <logic:notEqual name="notUsePlugin" property="pluginKbn" value="0">
                  <logic:equal name="notUsePlugin" property="pluginBinSid" value="0">
                    <img src="../common/images/pluginImg/classic/menu_icon_plugin_default_25.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_classicImg-display main_listboxImg_border">
                    <img src="../common/images/pluginImg/original/menu_icon_plugin_default_50.png	" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_originalImg-display">
                  </logic:equal>
                  <logic:notEqual name="notUsePlugin" property="pluginBinSid" value="0">
                    <img src="../main/man120.do?CMD=getImageFile&man120imgPluginId=<bean:write name="notUsePlugin" property="pluginId" />" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_classicImg-display main_listboxImg_border">
                    <img src="../main/man120.do?CMD=getImageFile&man120imgPluginId=<bean:write name="notUsePlugin" property="pluginId" />" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_originalImg-display">
                  </logic:notEqual>
                </logic:notEqual>
                <bean:write name="notUsePlugin" property="pluginName" />&nbsp;[<a href="#!" onclick="return addUseKbn('<bean:write name="notUsePlugin" property="pluginId" />');"><gsmsg:write key="cmn.use" /></a>]
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>
    </div>
    <div class="main">
      <table class="table-top w100">
        <tr>
          <th class="table_title-color txt_l" colspan="5">
            <gsmsg:write key="main.man120.5" />
          </th>
        </tr>
        <logic:notEmpty name="man120Form" property="man120viewMenuLabel">
          <logic:iterate id="viewMenuValue" name="man120Form" property="man120viewMenuLabel" indexId="idx">
            <bean:define id="pluginid" name="viewMenuValue" property="pluginId" />
            <tr>
              <logic:equal name="viewMenuValue" property="pluginId" value="mobile">
                <td class="w5 bgC_tableCell border_right_none" rowspan="2">
              </logic:equal>
              <logic:equal name="viewMenuValue" property="pluginId" value="api">
                <td class="w5 bgC_tableCell border_right_none" rowspan="2">
              </logic:equal>
              <logic:notEqual name="viewMenuValue" property="pluginId" value="mobile">
                <logic:notEqual name="viewMenuValue" property="pluginId" value="api">
                  <td class="w5 bgC_tableCell border_right_none">
                </logic:notEqual>
              </logic:notEqual>
                <logic:equal name="man120Form" property="menuEditOk" value="1">
                  <html:radio property="man120sort" value="<%= String.valueOf(pluginid) %>" styleId="<%= String.valueOf(pluginid) %>"/>
                </logic:equal>
              </td>
              <logic:equal name="viewMenuValue" property="pluginId" value="mobile">
                <td class="bgC_tableCell border_none">
              </logic:equal>
              <logic:equal name="viewMenuValue" property="pluginId" value="api">
                <td class="bgC_tableCell border_none">
              </logic:equal>
              <logic:notEqual name="viewMenuValue" property="pluginId" value="mobile">
                <logic:notEqual name="viewMenuValue" property="pluginId" value="api">
                  <td class="bgC_tableCell border_left_none border_right_none">
                </logic:notEqual>
              </logic:notEqual>
                <label for="<%= String.valueOf(pluginid) %>">
                  <logic:equal name="viewMenuValue" property="pluginKbn" value="0">
                    <img src="<bean:write name="viewMenuValue" property="iconClassic" />" property="pluginId" class="wp25hp25 btn_classicImg-display main_listboxImg_border">
                    <img src="<bean:write name="viewMenuValue" property="icon" />" property="pluginId" class="wp25hp25 btn_originalImg-display">
                  </logic:equal>
                  <logic:notEqual name="viewMenuValue" property="pluginKbn" value="0">
                    <logic:equal name="viewMenuValue" property="pluginBinSid" value="0">
                      <img src="../common/images/pluginImg/classic/menu_icon_plugin_default_25.png" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_classicImg-display main_listboxImg_border">
                      <img src="../common/images/pluginImg/original/menu_icon_plugin_default_50.png	" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_originalImg-display">
                    </logic:equal>
                    <logic:notEqual name="viewMenuValue" property="pluginBinSid" value="0">
                      <img src="../main/man120.do?CMD=getImageFile&man120imgPluginId=<bean:write name="viewMenuValue" property="pluginId" />" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_classicImg-display main_listboxImg_border">
                      <img src="../main/man120.do?CMD=getImageFile&man120imgPluginId=<bean:write name="viewMenuValue" property="pluginId" />" alt="<gsmsg:write key="cmn.icon" />" class="wp25hp25 btn_originalImg-display">
                    </logic:notEqual>
                  </logic:notEqual>
                </label>
              </td>
              <logic:equal name="viewMenuValue" property="pluginId" value="mobile">
                <td class="w35 bgC_tableCell border_none">
              </logic:equal>
              <logic:equal name="viewMenuValue" property="pluginId" value="api">
                <td class="w35 bgC_tableCell border_none">
              </logic:equal>
              <logic:notEqual name="viewMenuValue" property="pluginId" value="mobile">
                <logic:notEqual name="viewMenuValue" property="pluginId" value="api">
                  <td class="w35 bgC_tableCell border_left_none border_right_none">
                </logic:notEqual>
              </logic:notEqual>
                <label for="<%= String.valueOf(pluginid) %>">
                  <span class="fs_17 fw_b lh_normal"><bean:write name="viewMenuValue" property="pluginName" /></span>
                </label>
              </td>
              <logic:equal name="viewMenuValue" property="pluginId" value="mobile">
                <td class="w20 bgC_tableCell border_none">
              </logic:equal>
              <logic:equal name="viewMenuValue" property="pluginId" value="api">
                <td class="w20 bgC_tableCell border_none">
              </logic:equal>
              <logic:notEqual name="viewMenuValue" property="pluginId" value="mobile">
                <logic:notEqual name="viewMenuValue" property="pluginId" value="api">
                  <td class="w15 bgC_tableCell border_left_none border_right_none">
                </logic:notEqual>
              </logic:notEqual>
                <logic:notEqual name="viewMenuValue" property="pluginId" value="main">
                  <div>
                    <span class="verAlignMid no_w fs_14">
                    <input type="radio" name="man120useKbn<bean:write name="viewMenuValue" property="pluginId" />" value="0" checked="checked">
                    <gsmsg:write key="cmn.use" />
                    <input class="ml10" type="radio" name="man120useKbn<bean:write name="viewMenuValue" property="pluginId" />" value="1" onclick="delUseKbn('<bean:write name="viewMenuValue" property="pluginId" />');" id="useKbn<bean:write name="viewMenuValue" property="pluginId" />">
                    <label for="useKbn<bean:write name="viewMenuValue" property="pluginId" />"><gsmsg:write key="cmn.unused" /></label>
                    </span>
                  </div>
                </logic:notEqual>
              </td>
              <logic:equal name="viewMenuValue" property="pluginId" value="mobile">
                <td class="w40 bgC_tableCell border_left_none border_bottom_none txt_r">
              </logic:equal>
              <logic:equal name="viewMenuValue" property="pluginId" value="api">
                <td class="w40 bgC_tableCell border_left_none border_bottom_none txt_r">
              </logic:equal>
              <logic:notEqual name="viewMenuValue" property="pluginId" value="mobile">
                <logic:notEqual name="viewMenuValue" property="pluginId" value="api">
                  <td class="w40 bgC_tableCell border_left_none txt_r">
                </logic:notEqual>
              </logic:notEqual>
                <logic:notEqual name="viewMenuValue" property="pluginId" value="main">
                  <logic:equal name="viewMenuValue" property="pluginKbn" value="1">
                    <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.edit" />" onClick="editUplgClick('<bean:write name="viewMenuValue" property="pluginId" />');">
                      <img src="../common/images/classic/icon_edit_2.png" class="btn_classicImg-display">
                      <img src="../common/images/original/icon_edit.png" class="btn_originalImg-display">
                      <gsmsg:write key="cmn.edit" />
                    </button>
                  </logic:equal>
                  <button type="button" class="baseBtn no_w" value="<gsmsg:write key="main.man120.6" />" onClick="editClick('<bean:write name="viewMenuValue" property="pluginId" />');">
                    <img src="../common/images/classic/icon_edit_2.png" class="btn_classicImg-display">
                    <img src="../common/images/original/icon_edit.png" class="btn_originalImg-display">
                    <gsmsg:write key="main.man120.6" />
                  </button>
                </logic:notEqual>
              </td>
            </tr>
            <logic:equal name="viewMenuValue" property="pluginId" value="mobile">
              <td class="bgC_tableCell border_left_none border_right_none" colspan="4">
                <span class="cl_fontWarn fs_12"><gsmsg:write key="main.man120.7" /></span>
              </td>
            </logic:equal>
            <logic:equal name="viewMenuValue" property="pluginId" value="api">
              <td class="bgC_tableCell border_left_none border_right_none" colspan="4">
                <span class="cl_fontWarn fs_12"><gsmsg:write key="main.man120.8" /></span>
              </td>
            </logic:equal>
          </logic:iterate>
        </logic:notEmpty>
      </table>
    </div>
  </div>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="addUplgClick();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
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