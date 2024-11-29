<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/common/" prefix="common" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cht.01" /> <gsmsg:write key="cmn.statistical.info" /></title>

<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<jsp:include page="/WEB-INF/plugin/common/jsp/cmn_message.jsp" />

<script src="../common/js/cmn290.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/toastDisplay.js?<%=GSConst.VERSION_PARAM%>"></script>

</head>
<body>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

<html:form action="/common/cmn290">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="pluginId" value="">
<input type="hidden" name="helpPrm" value="">

<common:toast toastId="cmn290">
  <gsmsg:write key="cmn.cmn280.01" />
</common:toast>

<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.sml.notification.setting" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="settingSml();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cmn290back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
</html:form>
<div class="txt_l js_errorArea"></div>
<div class="wrapper mrl_auto">
  <div class="display_flex bor1 bgC_body bgI_none">
    <table class="w20 js_listTable">
      <%-- スケジュール --%>
      <logic:equal name="cmn290Form" property="cmn290schDsp" value="1">
      <tr id="statsSch" class="js_tab_tr txt_l cmnSetting_sideMenu borC_light">
        <td class="js_tab_img cmnSetting_menuIcon w5">
          <img class="classic-display mr5" src="../schedule/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="schedule.108" />">
          <img class="original-display mr5" src="../schedule/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="schedule.108" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="schedule.108" /></span>
        </td>
      </tr>
      </logic:equal>

      <%-- 掲示板 --%>
      <logic:equal name="cmn290Form" property="cmn290bbsDsp" value="1">
      <tr id="statsBbs" class="js_tab_tr txt_l cmnSetting_sideMenu borC_light">
        <td class="js_tab_img cmnSetting_menuIcon w5">
          <img class="classic-display mr5" src="../bulletin/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="cmn.bulletin" />">
          <img class="original-display mr5" src="../bulletin/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="cmn.bulletin" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="cmn.bulletin" /></span>
        </td>
      </tr>
      </logic:equal>
      <logic:equal name="cmn290Form" property="cmn290rsvDsp" value="1">
      <%-- 施設予約 --%>
      <tr id="statsRsv" class="js_tab_tr txt_l cmnSetting_sideMenu borC_light">
        <td class="js_tab_img cmnSetting_menuIcon w5" >
          <img class="classic-display mr5" src="../reserve/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="cmn.reserve" />">
          <img class="original-display mr5" src="../reserve/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="cmn.reserve" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="cmn.reserve" /></span>
        </td>
      </tr>
      </logic:equal>

      <logic:equal name="cmn290Form" property="cmn290ntpDsp" value="1">
      <%-- 日報 --%>
      <tr id="statsNtp" class="js_tab_tr txt_l cmnSetting_sideMenu borC_light">
        <td class="js_tab_img cmnSetting_menuIcon w5" >
          <img class="classic-display mr5" src="../nippou/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="ntp.1" />">
          <img class="original-display mr5" src="../nippou/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="ntp.1" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="ntp.1" /></span>
        </td>
      </tr>
      </logic:equal>
      <logic:equal name="cmn290Form" property="cmn290cirDsp" value="1">
      <%-- 回覧板 --%>
      <tr id="statsCir" class="js_tab_tr txt_l cmnSetting_sideMenu borC_light">
        <td class="js_tab_img cmnSetting_menuIcon w5" >
            <img class="classic-display mr5" src="../circular/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="cir.5" />">
            <img class="original-display mr5" src="../circular/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="cir.5" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="cir.5" /></span>
        </td>
      </tr>
      </logic:equal>
      <logic:equal name="cmn290Form" property="cmn290filDsp" value="1">
      <%-- ファイル管理 --%>
      <tr id="statsFil" class="js_tab_tr txt_l cmnSetting_sideMenu borC_light">
        <td class="js_tab_img cmnSetting_menuIcon w5" >
            <img class="classic-display mr5" src="../file/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="cmn.filekanri" />">
            <img class="original-display mr5" src="../file/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="cmn.filekanri" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="cmn.filekanri" /></span>
        </td>
      </tr>
      </logic:equal>
      <logic:equal name="cmn290Form" property="cmn290rngDsp" value="1">
      <%-- 稟議 --%>
      <tr id="statsRng" class="js_tab_tr txt_l cmnSetting_sideMenu borC_light">
        <td class="js_tab_img cmnSetting_menuIcon w5" >
            <img class="classic-display mr5" src="../ringi/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="rng.62" />">
            <img class="original-display mr5" src="../ringi/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="rng.62" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="rng.62" /></span>
        </td>
      </tr>
      </logic:equal>
      <tr class="h100 cmnSetting_sideMenuArea bor_r1 borC_light">
        <td class="cmnSetting_menuArea h100">&nbsp;</td>
        <td class="cmnSetting_menuArea bor_r1 borC_light h100 txt_m">&nbsp;</td>
      </tr>
    </table>
    <div class="w80 h100 p5 js_dispArea">
      <logic:equal name="cmn290Form" property="cmn290schDsp" value="1">
        <div class="display_n js_sch js_panel">
          <jsp:include page="/WEB-INF/plugin/schedule/jsp/sch095.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn290Form" property="cmn290bbsDsp" value="1">
        <div class="display_n js_bbs js_panel">
          <jsp:include page="/WEB-INF/plugin/bulletin/jsp/bbs052.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn290Form" property="cmn290rsvDsp" value="1">
        <div class="display_n js_rsv js_panel">
          <jsp:include page="/WEB-INF/plugin/reserve/jsp/rsv300.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn290Form" property="cmn290ntpDsp" value="1">
        <div class="display_n js_ntp js_panel">
          <jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp095.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn290Form" property="cmn290cirDsp" value="1">
        <div class="display_n js_cir js_panel">
          <jsp:include page="/WEB-INF/plugin/circular/jsp/cir250.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn290Form" property="cmn290filDsp" value="1">
        <div class="display_n js_fil js_panel">
          <jsp:include page="/WEB-INF/plugin/file/jsp/fil130.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn290Form" property="cmn290rngDsp" value="1">
        <div class="display_n js_rng js_panel">
          <jsp:include page="/WEB-INF/plugin/ringi/jsp/rng250.jsp" />
        </div>
      </logic:equal>
    </div>
  </div>
</div>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>