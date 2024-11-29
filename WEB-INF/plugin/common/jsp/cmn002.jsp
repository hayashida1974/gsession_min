<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<% String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY; %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.cmn999.Cmn999Form" %>
<%@ page import="jp.groupsession.v2.cmn.dao.BaseUserModel" %>
<!DOCTYPE html>
<% boolean newTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>

<html class="w100 h100">
<head>
  <title>GROUPSESSION</title>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
  <link rel="stylesheet" href="../common/css/layout.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
  <link rel="stylesheet" href="../common/css/all.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
  <theme:css filename="theme.css"/>
  <script type="text/javascript" src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel="stylesheet" href="../common/css/common.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
  <script type="text/javascript"  src="../common/js/websocket.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript"  src="../common/js/push.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript"  src="../common/js/notice.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript"  src="../common/js/cmn002.js?<%= GSConst.VERSION_PARAM %>"></script>

<script type="text/javascript">
  <!--
    var msglist_cmn002 = (function() {
      //使用するメッセージキーの配列を作成
      var ret = new Array();
      ret['cmn.admin.setting'] = '<gsmsg:write key="cmn.admin.setting"/>';
      ret['cmn.preferences2'] = '<gsmsg:write key="cmn.preferences2"/>';
      ret['cht.cht010.80'] = '<gsmsg:write key="cht.cht010.80"/>';
      return ret;
    })();
    -->
  </script>
</head>

<body class="body_bg cmn002 cmn002-hidden" onload="websocket('<bean:write name="cmn002Form" property="url" />');" >
  <iframe id="menuFrame" loading="lazy" class="cmn002_menu border_none"  src="../common/cmn003.do?menuPage=<bean:write name="cmn002Form" property="menuPage" />" name="menu"></iframe>
  <iframe id="bodyFrame" loading="lazy" class="cmn002_body js_body border_none" src="<bean:write name="cmn002Form" property="url" />" name="body" ></iframe>
  <% Cmn999Form cmn999Form = (Cmn999Form) request.getAttribute("cmn999Form"); %>
  <% if (cmn999Form != null && cmn999Form.isInfoToast()) { %>
    <div id="cmn002InfoToast" class="display_n mrl_auto">
      <span class='js_toastMessage'><%= cmn999Form.getMessage() %></span>
    </div>
  <% } %>
  <div class="js_setteiLink topMenu_confList fw_n txt_l no_w display_n wp150 pos_abs z_idx100">
    <div class="js_setteiArea settei_area bt0"></div>
    <div class="bor1">
      <div class='pl5 pr5 pt5 bgC_body lh100'>
      <div class='verAlignMid'>
        <img class='btn_originalImg-display mr5' src='../main/images/original/menu_icon_single.png'>
        <img class='btn_classicImg-display mr5' src='../main/images/classic/menu_icon_single.gif'>
        <gsmsg:write key="cmn.main" />
      </div>
    </div>
    <div class="js_setteiMainArea"></div>
    <% BaseUserModel baseMdl = (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY); %>
      <% if (baseMdl.getAdminFlg()) { %>
    <div onClick='clickMenuTarConf(1, 0, 0, "main");' class='settei_content bgC_body content-hoverChange'>
        <gsmsg:write key="cmn.admin.setting" />
      </div>
      <% } %>
      <% if (baseMdl.getUsrsid() != GSConst.SYSTEM_USER_ADMIN) { %>
    <div onClick='clickMenuTarConf(0, 0, 0, "main");' class='settei_content bgC_body content-hoverChange'>
        <gsmsg:write key="cmn.preferences2" />
      </div>
      <div onClick='clickMenuTarConf(2, 0, 0, "main");' class='settei_content bgC_body content-hoverChange'>
        <gsmsg:write key="cmn.notice.browser" />
      </div>
    <% } %>
    <div class="js_setteiPluginpArea">
    </div>
    </div>
  </div>
  <div class="pos_abs display_n js_settingResetArea w100 h100"></div>
</body>
<noframes>
  <gsmsg:write key="cmn.cmn002.1" />
</noframes>
</html>
