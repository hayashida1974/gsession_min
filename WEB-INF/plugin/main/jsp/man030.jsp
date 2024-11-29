<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<% String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY; %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.preferences2" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../main/css/main.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body class="body_03" onload="stopCloseWindow();parent.menu.location='../common/cmn003.do';">
<html:form action="/main/man030">
<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<bean:define id="kusr" name="<%= key %>" scope="session" />
<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.preferences2" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper_2column">
  <div class="main_listbox-l">
    <logic:notEmpty name="man030Form" property="pluginMenuList">
      <div class="settingList">
        <dl class="w100">
          <dt class="settingList_title main_listbox-dt">
            <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="cmn.plugin" />"><gsmsg:write key="cmn.plugin" />
          </dt>
          <dd>
            <ul class="pluginList pt0 pl0 pb10">
              <logic:iterate id="plugin" name="man030Form" property="pluginMenuList">
                <bean:define id="pluginName" name='plugin' property='name' type="java.lang.String" />
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
  </div>
  <div class="main_listbox-r">
    <div class="settingList">
      <dl class="w100">
        <dt class="settingList_title main_listbox-dt">
          <img src="../main/images/classic/icon_gs.gif" class="btn_classicImg-display mr10" alt="<gsmsg:write key="cmn.preferences" />"><gsmsg:write key="cmn.preferences" />
        </dt>
        <dd>
          <div class="settingList_text">
            <% String space = ""; %>
            <logic:equal name="man030Form" property="changePassword" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CHANGEPASSWORD_PARMIT) %>">
              <logic:equal name="man030Form" property="manPasswordKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PASSWORD_EDIT_USER) %>">
                <div class="<%= space %>">
                  <a href="#" onClick="return buttonPush('passwordEdit');">
                    <img src="../main/images/classic/icon_passward.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.change.password" />">
                    <img src="../common/images/original/icon_key_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.change.password" />">
                    <gsmsg:write key="cmn.change.password" />
                  </a>
                  <br>
                  <div class="ml50">・・・<gsmsg:write key="man.change.password.configure" /></div>
                </div>
                <% space = "mt20";%>
              </logic:equal>
            </logic:equal>
            <logic:equal name="man030Form" property="useableOtp" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USE) %>">
              <div class="<%= space %>">
                <a href="#" onClick="return buttonPush('otp_sendto_address');">
                  <img src="../main/images/classic/icon_passward.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="user.usr060.1" />">
                  <img src="../common/images/original/icon_key_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="user.usr060.1" />">
                  <gsmsg:write key="user.usr060.1" />
                </a>
                <br>
                <div class="ml50">・・・<gsmsg:write key="user.usr060.2" /></div>
              </div>
              <% space = "mt20";%>
            </logic:equal>
            <logic:equal name="man030Form" property="mainPconfEditKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PCONF_EDIT_USER) %>">
              <div class="<%= space %>">
                <a href="#" onClick="return buttonPush('userEdit');">
                  <img src="../common/images/classic/icon_user.png" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.modify.personalinfo" />">
                  <img src="../common/images/original/icon_user_25.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.modify.personalinfo" />">
                  <gsmsg:write key="cmn.modify.personalinfo" />
                </a>
                <br>
                <div class="ml50">・・・<gsmsg:write key="main.man030.4" /></div>
              </div>
              <% space = "mt20";%>
            </logic:equal>
            <logic:equal name="man030Form" property="pluginSetting" value="0">
              <div class="<%= space %>">
                <a href="#" onClick="return buttonPush('menuEdit');">
                  <img src="../main/images/classic/icon_menu.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man030.5" />">
                  <img src="../main/images/original/icon_menu.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man030.5" />">
                  <gsmsg:write key="main.man030.5" />
                </a>
                <br>
                <div class="ml50">・・・<gsmsg:write key="main.man030.6" /></div>
              </div>
              <% space = "mt20";%>
            </logic:equal>
            <div class="<%= space %>">
              <a href="#" onClick="return buttonPush('maindsp');">
                <img src="../main/images/classic/icon_main.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.setting.main.view2" />">
                <img src="../main/images/original/menu_icon_single.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.setting.main.view2" />">
                <gsmsg:write key="cmn.setting.main.view2" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man030.7" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('mygroup');">
                <img src="../main/images/classic/icon_group.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man030.8" />">
                <img src="../main/images/original/icon_mygroup.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man030.8" />">
                <gsmsg:write key="main.man030.8" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="man.set.destination.maigurupu" /></div>
            </div>
            <div class="mt20">
              <a href="#" onClick="return buttonPush('theme');">
                <img src="../main/images/classic/icon_theme.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man030.10" />">
                <img src="../main/images/original/icon_theme.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man030.10" />">
                <gsmsg:write key="main.man030.10" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man030.11" /></div>
            </div>
            <logic:equal name="man030Form" property="portalUseOk" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
              <div class="mt20">
                <a href="#" onClick="return buttonPush('portal');">
                  <img src="../portal/images/classic/menu_icon_single.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="ptl.7" />">
                  <img src="../portal/images/original/menu_icon_single.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="ptl.7" />">
                  <gsmsg:write key="ptl.7" />
                </a>
                <br>
                <logic:equal name="man030Form" property="ptlSortPow" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.EDIT_KBN_PUBLIC) %>">
                  <logic:equal name="man030Form" property="ptlDefPow" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.EDIT_KBN_PUBLIC) %>">
                    <div class="ml50">・・・<gsmsg:write key="ptl.22" /></div>
                  </logic:equal>
                  <logic:equal name="man030Form" property="ptlDefPow" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.EDIT_KBN_ADM) %>">
                    <div class="ml50">・・・<gsmsg:write key="ptl.ptl140.2" /></div>
                  </logic:equal>
                </logic:equal>
                <logic:equal name="man030Form" property="ptlSortPow" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.EDIT_KBN_ADM) %>">
                  <logic:equal name="man030Form" property="ptlDefPow" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstPortal.EDIT_KBN_PUBLIC) %>">
                    <div class="ml50">・・・<gsmsg:write key="ptl.ptl020.3" /></div>
                  </logic:equal>
                </logic:equal>
              </div>
            </logic:equal>
            <logic:equal name="man030Form" property="mainLayoutKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.MANSCREEN_LAYOUTKBN_USER) %>">
              <div class="mt20">
                <a href="#" onClick="return buttonPush('mainLayout');">
                  <img src="../portal/images/classic/menu_icon_single.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.layout.setting" />">
                  <img src="../portal/images/original/menu_icon_single.png" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.layout.setting" />">
                  <gsmsg:write key="main.layout.setting" />
                </a>
                <br>
                <div class="ml50">・・・<gsmsg:write key="main.man002.73" /></div>
              </div>
            </logic:equal>
            <logic:equal name="man030Form" property="smlUseOk" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
            <logic:equal name="man030Form" property="smlNoticeFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
              <div class="mt20">
                <a href="#" onClick="return buttonPush('smlPconf');">
                  <img src="../smail/images/classic/menu_icon_single.gif" class="btn_classicImg-display main_listboxImg_border" alt="ショートメール">
                  <img src="../smail/images/original/menu_icon_single.png" class="btn_originalImg-display mr10" alt="ショートメール">
                  <gsmsg:write key="cmn.sml.notification.setting" />
                </a>
                <br>
                <div class="ml50">・・・<gsmsg:write key="main.man002.112" /></div>
              </div>
            </logic:equal>
            </logic:equal>
<%--
            <div class="mt20">
              <a href="#" onClick="return buttonPush('cybozu');">
                <img src="../main/images/classic/icon_import_cyb.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="cmn.import.cybozu" />">
                <img src="../main/images/classic/icon_import_cyb.gif" class="btn_originalImg-display mr10" alt="<gsmsg:write key="cmn.import.cybozu" />">
                <gsmsg:write key="cmn.import.cybozu" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man490.2" /><br><span class="fs_13"><gsmsg:write key="main.man440.21" /></span></div>
            </div>


            <div class="mt20">
              <a href="#" onClick="return buttonPush('languageEdit');">
                <img src="../main/images/classic/icon_import_cyb.gif" class="btn_classicImg-display main_listboxImg_border" alt="<gsmsg:write key="main.man030.13" />">
                <img src="../main/images/classic/icon_import_cyb.gif" class="btn_originalImg-display mr10" alt="<gsmsg:write key="main.man030.13" />">
                <gsmsg:write key="main.man030.13" />
              </a>
              <br>
              <div class="ml50">・・・<gsmsg:write key="main.man030.12" /></div>
            </div>
--%>
          </div>
        </dd>
      </dl>
    </div>
    <logic:equal name="man030Form" property="dspSettingKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.SETTING_USR) %>">
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
    </logic:equal>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>