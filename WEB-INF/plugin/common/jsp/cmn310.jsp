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
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.common" /> <gsmsg:write key="cmn.manual.delete2" /></title>

<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmn310.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/toastDisplay.js?<%=GSConst.VERSION_PARAM%>"></script>
<jsp:include page="/WEB-INF/plugin/common/jsp/cmn_message.jsp" />
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>
<body>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

<html:form action="/common/cmn310">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="helpPrm" value="">

<common:toast toastId="cmn310">
  <gsmsg:write key="schedule.108" /><gsmsg:write key="cmn.cmn310.02" />
</common:toast>

<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.manual.delete2" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cmn310back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
</html:form>

<div class="display_none">
  <div id="manualDelPop" title="">
    <ul class="p0 verAlignMid w100 pt10">
      <li class="">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="<gsmsg:write key="cmn.information" />">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="<gsmsg:write key="cmn.information" />">
      </li>
      <li class="pl10 dialog_msgbody word_b-all">
        <div id="delMsgArea"></div>
      </li>
    </ul>
  </div>
</div>

<div class="wrapper mrl_auto">
  <div class="display_flex bor1 bgC_body bgI_none">
    <table class="w20 js_listTable">
      <%-- スケジュール --%>
      <logic:equal name="cmn310Form" property="cmn310schDsp" value="1">
      <tr id="statsSch" class="js_tab_tr txt_l cmnSetting_sideMenu-select bgC_Body borC_body borC_body borBottomC_light borTopC_light border_top_none">
        <td class="js_tab_img cmnSetting_menuIcon-select w5">
          <img class="classic-display mr5" src="../schedule/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="schedule.108" />">
          <img class="original-display mr5" src="../schedule/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="schedule.108" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="schedule.108" /></span>
        </td>
      </tr>
      </logic:equal>
      <%-- 掲示板 --%>
      <logic:equal name="cmn310Form" property="cmn310bbsDsp" value="1">
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
      <%-- ショートメール --%>
      <logic:equal name="cmn310Form" property="cmn310smlDsp" value="1">
      <tr id="statsSml" class="js_tab_tr txt_l cmnSetting_sideMenu borC_light">
        <td class="js_tab_img cmnSetting_menuIcon w5">
          <img class="classic-display mr5" src="../smail/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="cmn.shortmail" />">
          <img class="original-display mr5" src="../smail/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="cmn.shortmail" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="cmn.shortmail" /></span>
        </td>
      </tr>
      </logic:equal>
      <%-- 施設予約 --%>
      <logic:equal name="cmn310Form" property="cmn310rsvDsp" value="1">
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
      <%-- 日報 --%>
      <logic:equal name="cmn310Form" property="cmn310ntpDsp" value="1">
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
      <%-- 回覧板 --%>
      <logic:equal name="cmn310Form" property="cmn310cirDsp" value="1">
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
      <%-- WEBメール --%>
      <logic:equal name="cmn310Form" property="cmn310wmlDsp" value="1">
      <tr id="statsWml" class="js_tab_tr txt_l cmnSetting_sideMenu borC_light">
        <td class="js_tab_img cmnSetting_menuIcon w5">
          <img class="classic-display mr5" src="../webmail/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="wml.wml010.25" />">
          <img class="original-display mr5" src="../webmail/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="wml.wml010.25" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="wml.wml010.25" /></span>
        </td>
      </tr>
      </logic:equal>
      <%-- 稟議 --%>
      <logic:equal name="cmn310Form" property="cmn310rngDsp" value="1">
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
      <%-- アンケート --%>
      <logic:equal name="cmn310Form" property="cmn310enqDsp" value="1">
      <tr id="statsEnq" class="js_tab_tr txt_l cmnSetting_sideMenu borC_light">
        <td class="js_tab_img cmnSetting_menuIcon w5">
          <img class="classic-display mr5" src="../enquete/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="enq.plugin" />">
          <img class="original-display mr5" src="../enquete/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="enq.plugin" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="enq.plugin" /></span>
        </td>
      </tr>
      </logic:equal>
      <%-- チャット --%>
      <logic:equal name="cmn310Form" property="cmn310chtDsp" value="1">
      <tr id="statsCht" class="js_tab_tr txt_l cmnSetting_sideMenu borC_light">
        <td class="js_tab_img cmnSetting_menuIcon w5">
          <img class="classic-display mr5" src="../chat/images/classic/menu_icon_single_24.png" alt="<gsmsg:write key="cht.01" />">
          <img class="original-display mr5" src="../chat/images/original/menu_icon_single_24.png" alt="<gsmsg:write key="cht.01" />">
        </td>
        <td class=" fs_16 no_w w95 lh_normal">
          <span class="timeline_menu ml5"><gsmsg:write key="cht.01" /></span>
        </td>
      </tr>
      </logic:equal>
      <tr class="h100 cmnSetting_sideMenuArea bor_r1 borC_light">
        <td class="cmnSetting_menuArea h100">&nbsp;</td>
        <td class="cmnSetting_menuArea bor_r1 borC_light h100 txt_m">&nbsp;</td>
      </tr>
    </table>
    <div class="w80 h100 p5 js_dispArea">
      <logic:equal name="cmn310Form" property="cmn310schDsp" value="1">
        <div class="display_n js_sch js_panel">
          <jsp:include page="/WEB-INF/plugin/schedule/jsp/sch083.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn310Form" property="cmn310bbsDsp" value="1">
        <div class="display_n js_bbs js_panel">
          <jsp:include page="/WEB-INF/plugin/bulletin/jsp/bbs150.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn310Form" property="cmn310smlDsp" value="1">
        <div class="display_n js_sml js_panel">
          <jsp:include page="/WEB-INF/plugin/smail/jsp/sml160.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn310Form" property="cmn310rsvDsp" value="1">
        <div class="display_n js_rsv js_panel">
          <jsp:include page="/WEB-INF/plugin/reserve/jsp/rsv130.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn310Form" property="cmn310ntpDsp" value="1">
        <div class="display_n js_ntp js_panel">
          <jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp083.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn310Form" property="cmn310cirDsp" value="1">
        <div class="display_n js_cir js_panel">
          <jsp:include page="/WEB-INF/plugin/circular/jsp/cir120.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn310Form" property="cmn310wmlDsp" value="1">
        <div class="display_n js_wml js_panel p10">
          <ul class="tabHeader w100">
            <li class="tabHeader_tab-on mwp100 pl10 pr10 js_wmlTab1 border_bottom_none" onClick="changeWmlTab('1');">
              <gsmsg:write key="cmn.mail" />
            </li>
            <li class="tabHeader_tab-off mwp100 pl10 pr10 js_wmlTab2" onClick="changeWmlTab('2');">
              <gsmsg:write key="wml.wml170kn.02" />
            </li>
            <li class="tabHeader_space"></li>
          </ul>
          <div id="tab1_table" class="table-left w100 mt0 p10 js_wmlContent1 border_top_none">
            <jsp:include page="/WEB-INF/plugin/webmail/jsp/wml060.jsp" />
          </div>
          <div id="tab2_table" class="table-left display_none w100 mt0 p10 js_wmlContent2 border_top_none">
            <jsp:include page="/WEB-INF/plugin/webmail/jsp/wml180.jsp" />
          </div>
        </div>
      </logic:equal>
      <logic:equal name="cmn310Form" property="cmn310rngDsp" value="1">
        <div class="display_n js_rng js_panel">
          <jsp:include page="/WEB-INF/plugin/ringi/jsp/rng170.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn310Form" property="cmn310enqDsp" value="1">
        <div class="display_n js_enq js_panel">
          <jsp:include page="/WEB-INF/plugin/enquete/jsp/enq950.jsp" />
        </div>
      </logic:equal>
      <logic:equal name="cmn310Form" property="cmn310chtDsp" value="1">
        <div class="display_n js_cht js_panel">
          <jsp:include page="/WEB-INF/plugin/chat/jsp/cht060.jsp" />
        </div>
      </logic:equal>
    </div>
  </div>
</div>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>