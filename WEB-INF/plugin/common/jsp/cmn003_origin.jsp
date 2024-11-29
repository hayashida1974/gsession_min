<%@page import="jp.groupsession.v2.cmn.cmn003.MenuInfo"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%pageContext.setAttribute("cmn003Form", request.getAttribute(request.getParameter("thisFormName")));%>
<% String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY; %>
<% String gsurl = "../main/man001.do"; %>
<logic:equal name="cmn003Form" property="cmn003SysAdminFlg" value="true">
  <% gsurl = "../main/man002.do"; %>
</logic:equal>

<!DOCTYPE html>

<html class="m0 p0 ofy_h">
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.cmn003.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/css/common.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<theme:css filename="theme.css"/>
<logic:notEqual name="cmn003Form" property="cmn003LogoBinSid" value="0">
  <style><!--
    .header_logo-origin {
       background-image:url(../common/cmn003.do?CMD=getLogoImageFile&cmn003LogoBinSid=<bean:write name="cmn003Form" property="cmn003LogoBinSid" />);
    }
  --></style>
</logic:notEqual>
<script type="text/javascript" src='../common/js/jquery-3.3.1.min.js?'></script>
<script type="text/javascript" src='../common/js/jquery.cookie.js?'></script>
<script type="text/javascript" src="../common/js/cmn003.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/cmn003_origin.js?<%= GSConst.VERSION_PARAM %>"></script>
<!--
var msglist_cmn003 = (function () {
  //使用するメッセージキーの配列を作成
   var ret = new Array();
   ret['cmn.cmn003.5'] = '<gsmsg:write key="cmn.cmn003.5"/>';

  return ret;
})();

-->
</script>

</head>

<body class="body_bg origin m0 p0 ofy_h">
  <div class="menu_header js_menu_header">
    <%-- GSロゴ --%>
    <a href="<%= gsurl %>" target="body" class="menu_logo">
      <logic:equal name="cmn003Form" property="cmn003LogoBinSid" value="0">
        <span class="gsLogo origin"   />
      </logic:equal>
      <logic:notEqual name="cmn003Form" property="cmn003LogoBinSid" value="0">
        <div class="header_logo-origin"></div>
      </logic:notEqual>
    </a>
    <span class="mrl_auto"></span>
    <!-- メモ -->
    <logic:notEqual value="true" name="cmn003Form" property="cmn003SysAdminFlg">
      <logic:equal value="1" name="cmn003Form" property="cmn003MemoUse">
        <a class="cursor_p cl_fontOutlineLink menu_headerText" href="#!" onClick="openMemoWindow()">
          <i class="icon-memo"></i>
          <gsmsg:write key="memo.01" />
        </a>
      </logic:equal>
    </logic:notEqual>
    
    <%-- ヘルプ --%>
    <a class="ml10 cursor_p cl_fontOutlineLink menu_headerText" href="#!" onClick="help()">
      <i class="icon-help"></i>
      <gsmsg:write key="cmn.help" />
    </a>
    
    <%-- 設定 --%>
    <div class="js_setteiArea ml10 cursor_p cl_fontOutlineLink menu_headerText cl_linkHoverChange topMenu_setting link" href="#!">
      <i class="icon-setting"></i>
      <gsmsg:write key="cmn.setting" />
    </div>
    <%-- ユーザ名 --%>
    <span class="ml10 cl_fontOutline menu_headerText">
      <i class="icon-user"></i>
      <logic:notEmpty name="<%= key %>" scope="session">
        <bean:write name="<%= key %>" scope="session" property="usisei" />&nbsp;<bean:write name="<%= key %>" scope="session" property="usimei" />
        <%-- ログアウトボタン --%>
        <a class="ml10 baseBtn menu_logout" href="./cmn001.do?CMD=logout" target="_top">
          <gsmsg:write key="mobile.11" />
        </a>
      </logic:notEmpty>
    </span>
  </div>
  <%-- プラグインメニュー --%>
  <div class="js_menu_base menu_base w100">
  <html:form action="/common/cmn002" target="_parent">
    <input type="hidden" id="windowCloseFlag" value="">
    <input type="hidden" name="url" value="">
    <input type="hidden" name="backPlugin" value="">
    <input type="hidden" id="menuCloseFlg" value="1">
    <input type="hidden" id="menuOpenFlg" value="1">
    <html:hidden name="cmn003Form" property="menuPage" />
    <bean:define id="menuInfoList" name="cmn003Form" property="menuInfoList" />
    <bean:define id="kusr" name="<%= key %>" scope="session" />
    <ul class="menu_list w100 bgC_menu">
      <%-- 一般ユーザ --%>
      <logic:notEqual name="kusr" property="usrsid" value="0">
        <logic:iterate id="menuInfo" name="cmn003Form" property="menuInfoList" type="MenuInfo">
            <% String pluginId = menuInfo.getPluginId(); %>
            <% String name = menuInfo.getName(); %>
            <% String menuUrl = menuInfo.getUrl(); %>
            <% Integer menuTarget = menuInfo.getTarget(); %>
            <% int pluginKbn = menuInfo.getPluginKbn(); %>
            <% Long binSid = menuInfo.getBinSid(); %>
            <% int paramKbn = menuInfo.getParamKbn(); %>
            <% int sendKbn = menuInfo.getSendKbn(); %>
            <% int strLen = name.length(); %>
            <% if (strLen == 7) { %>
            <%    name = name.substring(0, 4) + "<br>" + name.substring(4); %>
            <% } else if (strLen >= 8) { %>
            <%    strLen = 8; %>
            <%    name = name.substring(0, 5) + "<br>" + name.substring(5); %>
            <% } %>

            <% String imgUrl;
               if (pluginKbn != 0) {
                 if (binSid != 0) {
                     imgUrl = "../common/cmn003.do?CMD=getImageFile&cmn003PluginId=" + pluginId + "&cmn003BinSid=" + binSid;
                 } else {
                     imgUrl ="../common/images/pluginImg/original/menu_icon_plugin_default_50.png";
                 }
               } else if (menuInfo.getIcon() != null && menuInfo.getIcon().length() > 0) {
                   imgUrl = menuInfo.getIcon();
               } else {
                   imgUrl = "../common/images/pluginImg/original/menu_icon_" + pluginId +"_50.png";
               }
            %>
            <li class="js_menu_btn">
              <% if (menuTarget == 0) { %>
                  <% if (pluginKbn != 0) { %>
                <a id="<%= pluginId %>" class="menu_btn user_plugin_link" href="../common/cmn002.do?url=<%=menuUrl%>" target="_parent" onclick="return clickMenuTarFrame('<%= pluginId %>','<%= paramKbn %>','<%= sendKbn %>');">
                  <% } else { %>
                <a class="menu_btn" href="../common/cmn002.do?url=<%=menuUrl%>" target="_parent" onclick="return clickMenuGs('<%= menuUrl %>');">
                  <% } %>
              <% } else { %>
                  <a id="<%= pluginId %>" class="menu_btn user_plugin_link" href="<%=menuUrl%>" target="_blank" onclick="clickMenuTarWindow('<%= pluginId %>','<%= paramKbn %>','<%= sendKbn %>'); return false;">
              <% } %>
                <div class="menu_ico_base js_menuIcon"><img src="<%=imgUrl%>" class="" alt="アイコン"></div><div class="menu_name js_menuName"><%=name%></div>
              </a>
            </li>
        </logic:iterate>
      </logic:notEqual>
      <%-- adminユーザ --%>
      <logic:equal name="kusr" property="usrsid" value="0">
        <li class="js_menu_btn">
          <a class="menu_btn" href="../common/cmn002.do?url=../main/man001.do" target="_parent" onclick="return clickMenuGs('../main/man001.do');">
            <div class="menu_ico_base js_menuIcon">
              <img src="../common/images/pluginImg/original/menu_icon_main_50.png" alt="アイコン">
            </div>
            <div class="menu_name js_menuName">
              <gsmsg:write key="cmn.main" />
            </div>
          </a>
        </li>
      </logic:equal>
    </ul>
    <a class="menu_lockBtn js_menu_lockBtn js_menu_lockBtn-arrow" href="#!">
      <i class="icon-menu_arrow2 verAlignMid"></i>
    </a>
    <a class="menu_lockBtn js_menu_lockBtn js_menu_lockBtn-pin display_none" href="#!">
      <i class="icon-menu_pin verAlignMid"></i>
    </a>
  </html:form>
  </div>
  <div class="js_menu_close_base menu_close_base w100 txt_c p0">
  </div>
</body>
</html>
