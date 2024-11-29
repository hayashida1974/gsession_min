<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY; %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../main/css/main.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="stopCloseWindow();parent.menu.location='../common/cmn003.do';">
<html:form action="/main/man002">
<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--BODY -->

<bean:define id="kusr" name="<%= key %>" scope="session" />
<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.admin.setting" />
    </li>
    <li>
      <logic:notEqual name="kusr" property="usrsid" value="0">
        <div>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man002back');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </logic:notEqual>
      <logic:equal name="kusr" property="usrsid" value="0">
        <div class="original-display main_headerSpace">
      </logic:equal>
    </li>
  </ul>
</div>
<div class="wrapper_2column">

  <bean:define id="autoDelFlg" name="man002Form" property="autoDelFlg" />
  <bean:define id="smlUseOk" name="man002Form" property="smlUseOk" />
  <bean:define id="smlNoticeFlg" name="man002Form" property="smlNoticeFlg" />
   <%
      String layout = "w98 mrl_auto main_listbox-l";
      String width = "w14";
      boolean rDspFlg = false;
   %>

  <logic:equal name="man002Form" property="man002AdminFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.ADMKBN_PLG_ADM) %>">
    <logic:notEmpty name="man002Form" property="pluginMenuList">
      <%
         if (String.valueOf(autoDelFlg).equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE))
             || (String.valueOf(smlUseOk).equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE))
                  && String.valueOf(smlNoticeFlg).equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE)))) {
             layout = "main_listbox-l";
             width = "w25";
             rDspFlg = true;
         }
      %>
      <div class="<%= layout %>">
        <div class="settingList">
          <dl class="w100">
            <dt class="ml0 settingList_title main_listbox-dt">
              <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="cmn.plugin" />"><gsmsg:write key="cmn.plugin" />
            </dt>
            <dd>
              <ul class="w100 pluginList pt0 pl0 pb10">
                <logic:iterate id="plugin" name="man002Form" property="pluginMenuList">
                  <bean:define id="plname" name="plugin" property="name" type="java.lang.String" />
                  <bean:define id="plgId" name="plugin" property="id" type="java.lang.String" />
                  <li class="<%= width %> mt20">
                    <a href="<bean:write name='plugin' property='url' />">
                      <div class="main_pluginList">
                        <img src="<bean:write name='plugin' property='iconClassic' />" class="btn_classicImg-display main_listboxImg_border" alt="<bean:write name='plugin' property='name' />">
                        <img src="<bean:write name='plugin' property='icon' />" class="btn_originalImg-display" alt="<bean:write name='plugin' property='name' />">
                      </div>
                      <div class="txt_c"><bean:write name='plugin' property='name' /></div>
                    </a>
                  </li>
                </logic:iterate>
              </ul>
            </dd>
          </dl>
        </div>
      </div>
      <% if (rDspFlg) { %>
      <div class="main_listbox-r">
        <% if (String.valueOf(autoDelFlg).equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE))) { %>
        <div class="settingList">
          <dl class="w100">
            <dt class="settingList_title main_listbox-dt">
              <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.13" />"><gsmsg:write key="main.man002.13" />
            </dt>
            <dd>
              <div class="settingList_text">
                <div>
                  <a href="#" onClick="return buttonPush('autoDel');">
                    <img src="../common/images/classic/icon_trash.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.autodelete.setting" />">
                    <img src="../common/images/original/icon_trash_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.autodelete.setting" />">
                    <gsmsg:write key="cmn.autodelete.setting" />
                  </a>
                  <br>
                  <div class="ml50">・・・<gsmsg:write key="cmn.cmn300.01" /></div>
                </div>
                <logic:equal name="man002Form" property="manualDelFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
                <div class="mt20">
                  <a href="#" onClick="return buttonPush('manualDel');">
                    <img src="../common/images/classic/icon_trash.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.manual.delete2" />">
                    <img src="../common/images/original/icon_trash_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.manual.delete2" />">
                    <gsmsg:write key="cmn.manual.delete2" />
                  </a>
                  <br>
                  <div class="ml50">・・・<gsmsg:write key="cmn.cmn310.01" /></div>
                </div>
                </logic:equal>
              </div>
            </dd>
          </dl>
        </div>
        <% } %>
        <% if (String.valueOf(smlUseOk).equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE))
               && String.valueOf(smlNoticeFlg).equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE))) { %>
        <div class="settingList">
          <dl class="w100">
            <dt class="settingList_title main_listbox-dt">
              <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.63" />"><gsmsg:write key="main.man002.63" />
            </dt>
            <dd>
              <div class="settingList_text">
                <%  String marginTop = "mt0"; %>
                <logic:equal name="man002Form" property="smlUseOk" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
                <logic:equal name="man002Form" property="smlNoticeFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
                  <%  marginTop = "mt20"; %>
                  <div>
                    <a href="#" onClick="return buttonPush('smlConf');">
                      <img src="../smail/images/classic/menu_icon_single.gif" class="btn_classicImg-display main_listboxImg_border" alt="ショートメール">
                      <img src="../smail/images/original/menu_icon_single.png" class="btn_originalImg-display mr10" alt="ショートメール">
                      <gsmsg:write key="cmn.sml.notification.setting" />
                    </a>
                    <br>
                    <div class="ml50">・・・<gsmsg:write key="main.man002.112" /></div>
                  </div>
                </logic:equal>
                </logic:equal>
              </div>
            </dd>
          </dl>
        </div>
        <% } %>
      </div>
      <% } %>
    </logic:notEmpty>
    <logic:empty name="man002Form" property="pluginMenuList">
      <div class="<%= layout %>">
        <% if (String.valueOf(autoDelFlg).equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE))) { %>
        <div class="settingList">
          <dl class="w100">
            <dt class="settingList_title main_listbox-dt">
              <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.13" />"><gsmsg:write key="main.man002.13" />
            </dt>
            <dd>
              <div class="settingList_text">
                <div>
                  <a href="#" onClick="return buttonPush('autoDel');">
                    <img src="../common/images/classic/icon_trash.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.autodelete.setting" />">
                    <img src="../common/images/original/icon_trash_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.autodelete.setting" />">
                    <gsmsg:write key="cmn.autodelete.setting" />
                  </a>
                  <br>
                  <div class="ml50">・・・<gsmsg:write key="cmn.cmn300.01" /></div>
                </div>
                <logic:equal name="man002Form" property="manualDelFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
                <div class="mt20">
                  <a href="#" onClick="return buttonPush('manualDel');">
                    <img src="../common/images/classic/icon_trash.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.manual.delete2" />">
                    <img src="../common/images/original/icon_trash_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.manual.delete2" />">
                    <gsmsg:write key="cmn.manual.delete2" />
                  </a>
                  <br>
                  <div class="ml50">・・・<gsmsg:write key="cmn.cmn310.01" /></div>
                </div>
                </logic:equal>
              </div>
            </dd>
          </dl>
        </div>
        <% } %>
        <% if (String.valueOf(smlUseOk).equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE))
               && String.valueOf(smlNoticeFlg).equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE))) { %>
        <div class="settingList">
          <dl class="w100">
            <dt class="settingList_title main_listbox-dt">
              <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.63" />"><gsmsg:write key="main.man002.63" />
            </dt>
            <dd>
              <div class="settingList_text">
                <%  String marginTop = "mt0"; %>
                <logic:equal name="man002Form" property="smlUseOk" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
                <logic:equal name="man002Form" property="smlNoticeFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
                  <%  marginTop = "mt20"; %>
                  <div>
                    <a href="#" onClick="return buttonPush('smlConf');">
                      <img src="../smail/images/classic/menu_icon_single.gif" class="btn_classicImg-display main_listboxImg_border" alt="ショートメール">
                      <img src="../smail/images/original/menu_icon_single.png" class="btn_originalImg-display mr10" alt="ショートメール">
                      <gsmsg:write key="cmn.sml.notification.setting" />
                    </a>
                    <br>
                    <div class="ml50">・・・<gsmsg:write key="main.man002.112" /></div>
                  </div>
                </logic:equal>
                </logic:equal>
              </div>
            </dd>
          </dl>
        </div>
        <% } %>
      </div>
    </logic:empty>
  </logic:equal>


  <logic:equal name="man002Form" property="man002AdminFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.ADMKBN_ADM) %>">
  <div class="main_listbox-l">
    <div class="settingList">
      <dl class="w100">
        <dt class="settingList_title main_listbox-dt">
          <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="cmn.license.info" />"><gsmsg:write key="cmn.license.info" />
        </dt>
        <dd>
          <div class="settingList_text">
            <table class="w100">
              <tr>
                <td class="w25 txt_r" nowrap>
                  <span class=fw_b><gsmsg:write key="main.man002.2" /></span>
                </td>
                <td class="w75 bor_b1">
                  <logic:empty name="man002Form" property="gsUid">&nbsp;</logic:empty>
                  <logic:notEmpty name="man002Form" property="gsUid">
                    <span class=""><bean:write name="man002Form" property="gsUid" /></span>
                  </logic:notEmpty>
                </td>
              </tr>
              <tr>
                <td class="w25 txt_r" nowrap>
                  <span class="fw_b"><gsmsg:write key="main.man002.3" /></span>
                </td>
                <td class="w75 bor_b1">
                  <logic:empty name="man002Form" property="licenseId">&nbsp;</logic:empty>
                  <logic:notEmpty name="man002Form" property="licenseId">
                    <span class=""><bean:write name="man002Form" property="licenseId" /></span>
                  </logic:notEmpty>
                </td>
              </tr>
              <tr>
                <td class="w25 txt_r no_w">
                  <span class="fw_b"><gsmsg:write key="cmn.company.name" />：</span>
                </td>
                <td class="w75 bor_b1">
                  <logic:empty name="man002Form" property="licenseCom">&nbsp;</logic:empty>
                  <logic:notEmpty name="man002Form" property="licenseCom">
                    <span class=""><bean:write name="man002Form" property="licenseCom" /></span>
                  </logic:notEmpty>
                </td>
              </tr>
              <logic:notEmpty name="man002Form" property="licenseEntry">
                <tr>
                  <td class="w25 txt_r" nowrap>
                    <span class="fw_b"><gsmsg:write key="main.man002.101" />：</span>
                  </td>
                  <td class="w75 bor_b1">
                    <logic:empty name="man002Form" property="licenseId">&nbsp;</logic:empty>
                    <span class=""><bean:write name="man002Form" property="licenseEntry" /></span>
                  </td>
                </tr>
              </logic:notEmpty>
              <logic:notEmpty name="man002Form" property="pluginList">
                <logic:iterate id="plugin" name="man002Form" property="pluginList" scope="request" indexId="cnt">
                  <tr>
                    <td class="w25 txt_r" nowrap>
                      <span class="fw_b"><bean:write name="plugin" property="pluginName" />：</span>
                    </td>
                    <td class="w75 bor_b1">
                      <span class=""><gsmsg:write key="cmn.period2" />&nbsp;</span>
                      <logic:equal name="plugin" property="licenseOverFlag" value="1">
                        <span>
                          <bean:write name="plugin" property="licenseLimit" />
                        </span>
                      </logic:equal>
                      <logic:equal name="plugin" property="licenseOverFlag" value="0">
                        <span class="cl_fontWarn">
                          <bean:write name="plugin" property="licenseLimit" />
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_warn.png" alt="<gsmsg:write key="cmn.back" />">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.back" />">
                        </span>
                      </logic:equal>
                      <logic:equal name="plugin" property="licenseOverFlag" value="-1">
                        <span class=" bgC_lightGray">
                          <bean:write name="plugin" property="licenseLimit" />
                          <gsmsg:write key="cmn.asterisk" /><gsmsg:write key="main.man002.100" />
                        </span>
                      </logic:equal>
                    </td>
                  </tr>
                </logic:iterate>
              </logic:notEmpty>
              <logic:equal name="man002Form" property="sysKbn" value="1">
                <bean:define id="usrCnt" name="man002Form" property="userCount" type="java.lang.String" />
                <bean:define id="maxCnt" name="man002Form" property="licenseCount" type="java.lang.String" />
                <tr>
                  <td class="w25 txt_r" nowrap>
                    <span class="fw_b">ByCloud：</span>
                  </td>
                  <td class="w75 bor_b1">
                    <logic:empty name="man002Form" property="licenseId">&nbsp;</logic:empty>
                    <logic:notEmpty name="man002Form" property="licenseId">
                      <span class=""><%= usrCnt %>/<gsmsg:write key="bmk.23" arg0="<%= maxCnt %>" /></span>
                    </logic:notEmpty>
                  </td>
                </tr>
                <tr>
                  <td class="w25 txt_r" nowrap>
                    <span class="fw_b"><gsmsg:write key="cmn.data.usage" />：</span>
                  </td>
                  <td class="w75 bor_b1">
                    <span class=""><bean:write name="man002Form" property="dbUse" />/<bean:write name="man002Form" property="dbCanUse" />MB</span>
                  </td>
                </tr>
              </logic:equal>
              <tr>
                <td colspan="2" class="txt_c pt5">
                  <logic:notEqual name="man002Form" property="sysKbn" value="1">
                    <%-- ライセンス設定ボタン --- --%>
                    <button class="baseBtn" value="<gsmsg:write key="cmn.admin.setting" />" onClick="return buttonPush('licenseReg');">
                      <gsmsg:write key="main.man002.7" />
                    </button>
                    <%-- --- ライセンス設定ボタン --%>
                  </logic:notEqual>
                </td>
              </tr>
              <tr>
                <td colspan="2" class="txt_r">
                  <bean:define id="manCnt" name="man002Form" property="userCount" type="java.lang.String" />
                  <span class=""><gsmsg:write key="main.man002.8" />&nbsp;</span><span class=""><gsmsg:write key="bmk.23" arg0="<%= manCnt %>" /></span>
                </td>
              </tr>
              <logic:notEqual name="man002Form" property="limitUserCount" value="0">
                <tr>
                  <td colspan="2" class="txt_r">
                    <bean:define id="manLimCnt" name="man002Form" property="limitUserCount" type="java.lang.String" />
                    <span class=""><gsmsg:write key="main.man002.9" />&nbsp;</span><span class=""><gsmsg:write key="bmk.23" arg0="<%= manLimCnt %>" /></span>
                  </td>
                </tr>
              </logic:notEqual>

              <%-- ライセンス取得リンク  --- --%>
              <tr>
                <td colspan="2" class="txt_r">
                  <logic:notEmpty name="man002Form" property="licenseFreeUrl">
                    <logic:notEmpty name="man002Form" property="gsUid">
                      <logic:equal name="man002Form" property="man002FreeLinkFlg" value="1">
                        <a href="<bean:write name="man002Form" property="licenseFreeUrl" />?ksSN=<bean:write name="man002Form" property="gsUid" />" target="_blank"><span class="sc_link_b"><gsmsg:write key="main.man002.102" /></span></a>
                      </logic:equal>
                    </logic:notEmpty>
                    <logic:empty name="man002Form" property="gsUid">
                      <a href="<bean:write name="man002Form" property="licenseFreeUrl" />" target="_blank"><span class="sc_link_b"><gsmsg:write key="main.man002.102" /></span></a>
                    </logic:empty>
                  </logic:notEmpty>
                </td>
              </tr>
              <tr>
                <td colspan="2" class="txt_r">
                  <logic:notEmpty name="man002Form" property="licensePageUrl">
                    <logic:notEqual name="man002Form" property="sysKbn" value="1">
                      <logic:notEmpty name="man002Form" property="gsUid">
                        <a href="<bean:write name="man002Form" property="licensePageUrl" />?ksSN=<bean:write name="man002Form" property="gsUid" />" target="_blank"><gsmsg:write key="main.man002.10" /></a>
                      </logic:notEmpty>
                      <logic:empty name="man002Form" property="gsUid">
                        <a href="<bean:write name="man002Form" property="licensePageUrl" />" target="_blank"><gsmsg:write key="main.man002.10" /></a>
                      </logic:empty>
                    </logic:notEqual>
                    <logic:equal name="man002Form" property="sysKbn" value="1">
                      <a href="<bean:write name="man002Form" property="licensePageUrl" />&byCloudId=<bean:write name="man002Form" property="domain" />" target="_blank">ライセンスの更新・ユーザ数追加はこちらから</a>
                    </logic:equal>
                  </logic:notEmpty>
                </td>
              </tr>
              <%-- --- ライセンス取得リンク --%>
            </table>
          </div>
        </dd>
      </dl>
    </div>

    <logic:notEmpty name="man002Form" property="pluginMenuList">
      <div class="settingList">
        <dl class="w100">
          <dt class="settingList_title main_listbox-dt">
            <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="cmn.plugin" />"><gsmsg:write key="cmn.plugin" />
          </dt>
          <dd>
            <ul class="pluginList pt0 pl0 pb10">
              <li class="w25 mt20">
                <a href="../main/man300.do?CMD=admconf&backScreen=1">
                  <div class="main_pluginList">
                    <img src="../main/images/classic/menu_icon_single_info.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.information" />">
                    <img src="../common/images/pluginImg/original/menu_icon_info_50.png" class="btn_originalImg-display" alt="<gsmsg:write key="cmn.information" />">
                  </div>
                  <div class="txt_c fs_13 no_w"><gsmsg:write key="cmn.information" /></div>
                </a>
              </li>
              <logic:equal name="man002Form" property="portalUseOk" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
              <li class="w25 mt20">
                <a href="../portal/ptl020.do">
                  <div class="main_pluginList">
                    <img src="../portal/images/classic/menu_icon_single.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="portal.portal" />">
                    <img src="../portal/images/original/menu_icon_single_50.png" class="btn_originalImg-display" alt="<gsmsg:write key="portal.portal" />">
                  </div>
                  <div class="txt_c"><gsmsg:write key="portal.portal" /></div>
                </a>
              </li>
              </logic:equal>
              <logic:iterate id="plugin" name="man002Form" property="pluginMenuList">
                <bean:define id="plname" name="plugin" property="name" type="java.lang.String" />
                <bean:define id="plgId" name="plugin" property="id" type="java.lang.String" />
                <li class="w25 mt20">
                  <a href="<bean:write name='plugin' property='url' />">
                    <div class="main_pluginList">
                      <img src="<bean:write name='plugin' property='iconClassic' />" class="btn_classicImg-display main_listboxImg_border" alt="<bean:write name='plugin' property='name' />">
                      <img src="<bean:write name='plugin' property='icon' />" class="btn_originalImg-display" alt="<bean:write name='plugin' property='name' />">
                    </div>
                    <div class="txt_c"><bean:write name='plugin' property='name' /></div>
                  </a>
                </li>
              </logic:iterate>
            </ul>
          </dd>
        </dl>
      </div>
    </logic:notEmpty>

    <div class="settingList">
      <dl class="w100">
        <dt class="settingList_title main_listbox-dt">
          <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.1" />"><gsmsg:write key="main.man002.1" />
        </dt>
        <dd>
          <div class="settingList_text">
            <a href="#" onClick="return buttonPush('mblUse');">
              <img src="../mobile/images/classic/menu_icon_single.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.mobile.use.massconfig" />">
              <img src="../mobile/images/original/menu_icon_single.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.mobile.use.massconfig" />">
              <gsmsg:write key="cmn.mobile.use.massconfig" />
            </a>
            <br>
            <div class="ml50">・・・<gsmsg:write key="main.man002.14" /></div>
          </div>
        </dd>
      </dl>
    </div>

    <logic:equal name="man002Form" property="autoDelFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
    <div class="settingList">
      <dl class="w100">
        <dt class="settingList_title main_listbox-dt">
          <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.13" />"><gsmsg:write key="main.man002.13" />
        </dt>
        <dd>
          <div class="settingList_text">
            <div>
              <a href="#" onClick="return buttonPush('autoDel');">
                <img src="../common/images/classic/icon_trash.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.autodelete.setting" />">
                <img src="../common/images/original/icon_trash_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.autodelete.setting" />">
                <gsmsg:write key="cmn.autodelete.setting" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="cmn.cmn300.01" /></div>
            </div>
            <logic:equal name="man002Form" property="manualDelFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
            <div class="mt20">
              <a href="#" onClick="return buttonPush('manualDel');">
                <img src="../common/images/classic/icon_trash.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.manual.delete2" />">
                <img src="../common/images/original/icon_trash_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.manual.delete2" />">
                <gsmsg:write key="cmn.manual.delete2" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="cmn.cmn310.01" /></div>
            </div>
            </logic:equal>
          </div>
        </dd>
      </dl>
    </div>
    </logic:equal>

    <bean:define id="dbKbn" name="man002Form" property="dbKbn" />
    <% if ((String.valueOf(smlUseOk).equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE))
            && String.valueOf(smlNoticeFlg).equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE)))
            || dbKbn.equals(String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE))) { %>
    <div class="settingList">
      <dl class="w100">
        <dt class="settingList_title main_listbox-dt">
          <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.63" />"><gsmsg:write key="main.man002.63" />
        </dt>
        <dd>
          <div class="settingList_text">
            <%  String marginTop = "mt0"; %>
            <logic:equal name="man002Form" property="smlUseOk" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
            <logic:equal name="man002Form" property="smlNoticeFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
              <%  marginTop = "mt20"; %>
              <div>
                <a href="#" onClick="return buttonPush('smlConf');">
                  <img src="../smail/images/classic/menu_icon_single.gif" class="btn_classicImg-display main_listboxImg_border" alt="ショートメール">
                  <img src="../smail/images/original/menu_icon_single.png" class="btn_originalImg-display mr10" alt="ショートメール">
                  <gsmsg:write key="cmn.sml.notification.setting" />
                </a>
                <br>
                <div class="ml50">・・・<gsmsg:write key="main.man002.112" /></div>
              </div>
            </logic:equal>
            </logic:equal>
            <logic:equal name="man002Form" property="dbKbn" value="0">
              <div class="<%= marginTop %>">
                <a href="#" onClick="return buttonPush('diskconf');">
                  <img src="../main/images/classic/icon_disc.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.64" />">
                  <img src="../main/images/original/icon_disc_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.64" />">
                  <gsmsg:write key="main.man002.64" />
                </a>
                <br>
                <div class="ml50">・・・<gsmsg:write key="main.man002.65" /></div>
              </div>
            </logic:equal>
          </div>
        </dd>
      </dl>
    </div>
    <% } %>

    <div class="settingList">
      <dl class="w100">
        <dt class="settingList_title main_listbox-dt">
          <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.50" />"><gsmsg:write key="main.man002.50" />
        </dt>
        <dd>
          <div class="settingList_text">
            <div>
              <a href="#" onClick="return buttonPush('oplogConf');">
                <img src="../main/images/classic/icon_log.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.51" />">
                <img src="../common/images/original/icon_log.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.51" />">
                <gsmsg:write key="main.man002.51" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.52" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('oplogSearch');">
                <img src="../main/images/classic/icon_log.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.53" />">
                <img src="../common/images/original/icon_log.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.53" />">
                <gsmsg:write key="main.man002.53" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.54" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('oplogAutoDel');">
                <img src="../main/images/classic/icon_login01.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="man.autodelete.opelog" />">
                <img src="../main/images/original/icon_login_history_auto_del.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="man.autodelete.opelog" />">
                <gsmsg:write key="man.autodelete.opelog" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.56" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('oplogSyudoDel');">
                <img src="../main/images/classic/icon_login02.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.manualdelete.operation.log" />">
                <img src="../main/images/original/icon_login_history_manual_del.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.manualdelete.operation.log" />">
                <gsmsg:write key="main.manualdelete.operation.log" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.74" /></div>
            </div>
            <logic:equal name="man002Form" property="dbKbn" value="0">
             <div class="mt20">
                <a href="#" onClick="return buttonPush('loglist');">
                <img src="../main/images/classic/icon_log.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.57" />">
                <img src="../common/images/original/icon_log.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.57" />">
                  <gsmsg:write key="main.man002.57" />
                </a>
                <br>
                <div class="ml50">・・・<gsmsg:write key="main.man002.58" /></div>
              </div>
            </logic:equal>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('beLogTime');">
                <img src="../main/images/classic/icon_login02.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="user.usr090.2" />">
                <img src="../main/images/original/icon_login_history_manual_del.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="user.usr090.2" />">
                <gsmsg:write key="user.usr090.2" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.dsp.last.login.time" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('lhisAutoDel');">
                <img src="../main/images/classic/icon_login01.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.autodelete.login.history" />">
                <img src="../main/images/original/icon_login_history_auto_del.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.autodelete.login.history" />">
                <gsmsg:write key="main.autodelete.login.history" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.60" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('lhisSyudoDel');">
                <img src="../main/images/classic/icon_login02.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.manualdelete.login.history" />">
                <img src="../main/images/original/icon_login_history_manual_del.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.manualdelete.login.history" />">
                <gsmsg:write key="main.manualdelete.login.history" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.62" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('manLogCount');">
                <img src="../main/images/classic/icon_login_history.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.statistical.info" />">
                <img src="../main/images/original/icon_login_history.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.statistical.info" />">
                <gsmsg:write key="cmn.statistical.info" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.75" /></div>
            </div>
          </div>
        </dd>
      </dl>
    </div>
  </div>

  <div class="main_listbox-r">
    <div class="settingList">
      <dl class="w100">
        <dt class="settingList_title main_listbox-dt">
          <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="cmn.preferences" />"><gsmsg:write key="cmn.preferences" />
        </dt>
        <dd>
          <div class="settingList_text">
            <div>
              <a href="#" onClick="return buttonPush('pluginlist');">
                <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.preferences" />">
                <img src="../common/images/original/icon_gs.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.preferences" />">
                <gsmsg:write key="main.man002.19" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.20" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('enterpriseiinfo');">
                <img src="../main/images/classic/icon_enterprise.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.21" />">
                <img src="../common/images/original/icon_company_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.21" />">
                <gsmsg:write key="main.man002.21" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.22" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('grouplist');">
                <img src="../common/images/classic/icon_user.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="user.44" />">
                <img src="../common/images/original/icon_group_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="user.44" />">
                <gsmsg:write key="user.44" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.23" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('userlist');">
                <img src="../common/images/classic/icon_user.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.24" />">
                <img src="../common/images/original/icon_user_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.24" />">
                <gsmsg:write key="main.man002.24" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.25" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('memshipconf');">
                <img src="../common/images/classic/icon_user.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.memberships.conf" />">
                <img src="../common/images/original/icon_user_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.memberships.conf" />">
                <gsmsg:write key="main.memberships.conf" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.expimp.memberships" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('poslist');">
                <img src="../common/images/classic/icon_user.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.26" />">
                <img src="../common/images/original/icon_user_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.26" />">
                <gsmsg:write key="main.man002.26" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.27" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('holidaylist');">
                <img src="../main/images/classic/icon_holiday.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.holiday.setting" />">
                <img src="../main/images/original/icon_holiday.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.holiday.setting" />">
                <gsmsg:write key="main.holiday.setting" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.29" /></div>
            </div>
            <logic:notEqual name="man002Form" property="sysKbn" value="1">
            <div class="mt20">
              <a href="#" onClick="return buttonPush('pxyconf');">
                <img src="../main/images/classic/icon_proxy.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.30" />">
                <img src="../main/images/original/icon_system.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.30" />">
                <gsmsg:write key="main.man002.30" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.31" /></div>
            </div>
            </logic:notEqual>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('fileConf');">
                <img src="../main/images/classic/icon_temp.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.32" />">
                <img src="../common/images/original/icon_attach_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.32" />">
                <gsmsg:write key="main.man002.32" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.33" /></div>
            </div>
            <logic:notEqual name="man002Form" property="sysKbn" value="1">
            <div class="mt20">
              <a href="#" onClick="return buttonPush('jobschedule');">
                <img src="../main/images/classic/icon_batch_01.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.34" />">
                <img src="../common/images/original/icon_clock_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.34" />">
                <gsmsg:write key="main.man002.34" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.35" /></div>
            </div>
            </logic:notEqual>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('pconfedit');">
                <img src="../common/images/classic/icon_user.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man500.2" />">
                <img src="../common/images/original/icon_user_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man500.2" />">
                <gsmsg:write key="main.man500.2"/>
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.76"/></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('sortConfig');">
                <img src="../common/images/classic/icon_user.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.grp.usr.sort.setting" />">
                <img src="../common/images/original/icon_user_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.grp.usr.sort.setting" />">
                <gsmsg:write key="main.grp.usr.sort.setting" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.37" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('layoutConfig');">
                <img src="../portal/images/classic/menu_icon_single.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.layout.setting" />">
                <img src="../portal/images/original/menu_icon_single.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.layout.setting" />">
                <gsmsg:write key="main.layout.setting" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.73" /></div>
            </div>
            <logic:notEqual name="man002Form" property="sysKbn" value="1">
            <div class="mt20">
              <a href="#" onClick="return buttonPush('linkTime');">
                <img src="../common/images/classic/icon_user.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man420.2" />">
                <img src="../common/images/original/icon_user_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man420.2" />">
                <gsmsg:write key="main.man420.2" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.77" /></div>
            </div>
            </logic:notEqual>

            <div class="mt20">
              <a href="#" onClick="return buttonPush('oauthManager');">
                <img src="../common/images/classic/icon_user.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.cmn250.01" />">
                <img src="../common/images/original/icon_user_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.cmn250.01" />">
                <gsmsg:write key="cmn.cmn250.01" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.78" /></div>
            </div>

          </div>
        </dd>
      </dl>
    </div>
    <div class="settingList">
      <dl class="w100">
        <dt class="settingList_title main_listbox-dt">
          <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="cmn.display.settings" />"><gsmsg:write key="cmn.display.settings" />
        </dt>
        <dd>
          <div class="settingList_text">
            <div>
              <a href="#" onClick="return buttonPush('dspConf');">
                <img src="../main/images/classic/icon_menu.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.display.settings" />">
                <img src="../main/images/original/icon_menu.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.display.settings" />">
                <gsmsg:write key="cmn.display.settings" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="cmn.cmn320.03" /></div>
            </div>
          </div>
        </dd>
      </dl>
    </div>
    <div class="settingList">
      <dl class="w100">
        <dt class="settingList_title main_listbox-dt">
          <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.38" />"><gsmsg:write key="main.man002.38" />
        </dt>
        <dd>
          <div class="settingList_text">
            <%-- GSファイアウォール設定 --- --%>
            <div class="mb20">
              <a href="#" onClick="return buttonPush('firewallConf');">
                <img src="../main/images/original/icon_firewall.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.cmn390.01" />">
                <img src="../main/images/original/icon_firewall.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.cmn390.01" />">
                <gsmsg:write key="cmn.cmn390.01" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="cmn.cmn390.02" /></div>
            </div>
            <%-- --- GSファイアウォール設定 --%>
            <div>

            <logic:equal name="man002Form" property="changePassword" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CHANGEPASSWORD_PARMIT) %>">
              <div>
                <a href="#" onClick="return buttonPush('pswdConf');">
                  <img src="../main/images/classic/icon_passward.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.39" />">
                  <img src="../common/images/original/icon_key_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.39" />">
                  <gsmsg:write key="main.man002.39" />
                </a>
                <br>
                <div class="ml50">・・・<gsmsg:write key="main.man002.40" /></div>
              </div>
              <div class="mt20">
            </logic:equal>
            <div>
              <a href="#" onClick="return buttonPush('sessiontime');">
                <img src="../main/images/classic/icon_session.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.41" />">
                <img src="../main/images/original/icon_login_timer.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.41" />">
                <gsmsg:write key="main.man002.41" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.42" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('loginConf');">
                <img src="../main/images/classic/icon_loginconf.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.login.setting" />">
                <img src="../main/images/original/icon_login.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.login.setting" />">
                <gsmsg:write key="main.login.setting" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.44" /></div>
            </div>
            <logic:equal name="man002Form" property="useableOtp" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USE) %>">
              <%-- ワンタイムパスワード設定 --- --%>
              <div class="mt20">
                <a href="#" onClick="return buttonPush('otpConf');">
                  <img src="../main/images/classic/icon_loginconf.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man510.1" />">
                  <img src="../main/images/original/icon_login.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man510.1" />">
                  <gsmsg:write key="main.man510.1" />
                </a>
                <br>
                <div class="ml50">・・・<gsmsg:write key="main.man510.2" /></div>
              </div>
              <%-- --- ワンタイムパスワード設定 --%>
            </logic:equal>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('externalPage');">
                <img src="../main/images/classic/icon_passward.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.externalpage" />">
                <img src="../common/images/original/icon_key_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.externalpage" />">
                <gsmsg:write key="main.externalpage" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.110" /></div>
            </div>
          </div>
        </dd>
      </dl>
    </div>
    <logic:equal name="man002Form" property="dbKbn" value="0">
      <logic:equal name="man002Form" property="tempFileHozonKbn" value="0">
        <div class="settingList">
          <dl class="w100">
            <dt class="settingList_title main_listbox-dt">
              <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.45" />"><gsmsg:write key="main.man002.45" />
            </dt>
            <dd>
              <div class="settingList_text">
                <div>
                  <a href="#" onClick="return buttonPush('backupconf');">
                    <img src="../main/images/classic/icon_backup01.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.autobackup.setting" />">
                    <img src="../main/images/original/icon_backup_manual.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.autobackup.setting" />">
                    <gsmsg:write key="cmn.autobackup.setting" />
                  </a>
                  <br>
                  <div class="ml50">・・・<gsmsg:write key="main.man002.47" /></div>
                </div>
                <div class="mt20">
                  <a href="#" onClick="return buttonPush('manualbackup');">
                    <img src="../main/images/classic/icon_backup02.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.manual.backup" />">
                    <img src="../main/images/original/icon_backup_auto.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.manual.backup" />">
                    <gsmsg:write key="cmn.manual.backup" />
                  </a>
                  <br>
                  <div class="ml50">・・・<gsmsg:write key="main.man002.49" /></div>
                </div>
              </div>
            </dd>
          </dl>
        </div>
      </logic:equal>
    </logic:equal>

    <div class="settingList">
      <dl class="w100">
        <dt class="settingList_title main_listbox-dt">
          <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.66" />"><gsmsg:write key="main.man002.66" />
        </dt>
        <dd>
          <div class="settingList_text">
            <logic:notEqual name="man002Form" property="sysKbn" value="1">
            <logic:equal name="man002Form" property="man002SysInfDspFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.SYS_INF_DSP_YES) %>">
            <div class="mb20">
              <a href="#" onClick="return buttonPush('system');">
                <img src="../main/images/classic/icon_proxy.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man002.67" />">
                <img src="../main/images/original/icon_system.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man002.67" />">
                <gsmsg:write key="main.man002.67" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.68" /></div>
            </div>
            </logic:equal>
            </logic:notEqual>
            <div>
              <a href="#" onClick="return buttonPush('useddata');">
                <img src="../main/images/classic/icon_proxy.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man520.1" />">
                <img src="../main/images/original/icon_system.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man520.1" />">
                <gsmsg:write key="main.man520.1" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man520.2" /></div>
            </div>
          </div>
        </dd>
      </dl>
    </div>
    <%-- adminユーザ設定 --- --%>
    <div class="settingList">
      <dl class="w100">
        <dt class="settingList_title main_listbox-dt">
          <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="main.man002.111" />"><gsmsg:write key="main.man002.111" />
        </dt>
        <dd>
          <div class="settingList_text">
            <div>
              <a href="../user/usr050.do?backScreen=1">
                <img src="../main/images/classic/icon_passward.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.change.password" />">
                <img src="../common/images/original/icon_key_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.change.password" />">
                <gsmsg:write key="cmn.change.password" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man002.70" /></div>
            </div>
            <logic:equal name="man002Form" property="useableOtp" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USE) %>">
              <div class="mt20">
                <a href="#" onClick="return buttonPush('otp_sendto_address');">
                  <img src="../main/images/classic/icon_passward.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="user.usr060.1" />">
                  <img src="../common/images/original/icon_key_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="user.usr060.1" />">
                  <gsmsg:write key="user.usr060.1" />
                </a>
                <br>
                <div class="ml50">・・・<gsmsg:write key="user.usr061.2" /></div>
              </div>
            </logic:equal>
          </div>
        </dd>
      </dl>
    </div>
    <%-- --- adminユーザ設定 --%>
  </div>
  </logic:equal>
</div>
</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>