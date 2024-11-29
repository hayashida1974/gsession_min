<%@page import="java.util.Arrays"%>
<%@page import="jp.groupsession.v2.rng.rng030.Rng030Action"%>
<%@page import="jp.groupsession.v2.rng.rng020.Rng020KeiroBlock"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="jp.groupsession.v2.rng.rng030.Rng030ButtonDispParam"%>
<%@page import="jp.groupsession.v2.rng.rng030.Rng030KeiroParam"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/formbuilder" prefix="fb" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform" %>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="cmn" %>

<%@ page import="jp.groupsession.v2.rng.RngConst" %>
<%@ page import="jp.groupsession.v2.rng.rng030.Rng030Form" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% String maxLengthContent = String.valueOf(RngConst.MAX_LENGTH_CONTENT); %>
<% String maxLengthCmt = String.valueOf(RngConst.MAX_LENGTH_COMMENT); %>
<% int cmdmode_view = Rng030Form.CMDMODE_VIEW; %>
<% int cmdmode_appr = Rng030Form.CMDMODE_APPR; %>
<% int cmdmode_confirm = Rng030Form.CMDMODE_CONFIRM; %>
<% int cmdmode_adminappr = Rng030Form.CMDMODE_ADMINAPPR; %>
<% String apprMode_appl = String.valueOf(RngConst.RNG_APPRMODE_APPL); %>
<% int mode0 = jp.groupsession.v2.rng.RngConst.RNG_MODE_JYUSIN; %>
<% int mode1 = jp.groupsession.v2.rng.RngConst.RNG_MODE_SINSEI; %>
<% int mode2 = jp.groupsession.v2.rng.RngConst.RNG_MODE_KANRYO; %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>" type="text/css">

<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../ringi/css/ringi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<script src="../common/js/glayer.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/clockpiker/clockpiker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js" ></script>


<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../ringi/js/rng030.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/selectionSearch.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

<cmn:loadscript src="../common/js/formbuilder/usrgrpselect.js" />

<% String onloadEvent_appl = ""; %>
<% String onloadEvent_view = ""; %>
<% boolean checkFlg= false; %>

<title>GROUPSESSION <gsmsg:write key="rng.62" /></title>
</head>
<logic:equal name="rng030Form" property="rngApprMode" value="<%= apprMode_appl %>">
  <body onunload="windowClose();" onload="<%= onloadEvent_appl %>">
  <% checkFlg = true; %>
</logic:equal>
<logic:notEqual name="rng030Form" property="rng030CmdMode" value="<%= String.valueOf(cmdmode_view) %>">
  <body onunload="windowClose();" onload="<%= onloadEvent_view %>">
  <% checkFlg = true; %>
</logic:notEqual>

<% if (!checkFlg) { %>
  <body>
<% } %>

<html:form action="/ringi/rng030">
<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>

<html:hidden property="backScreen" />
<html:hidden property="rngSid" />
<html:hidden property="rngProcMode" />
<html:hidden property="rngApprMode" />
<html:hidden property="rng010pageTop" />
<html:hidden property="rng010pageBottom" />
<html:hidden property="rng010sortKey" />
<html:hidden property="rng010orderKey" />
<html:hidden property="rngAdminKeyword" />
<html:hidden property="rngAdminGroupSid" />
<html:hidden property="rngAdminUserSid" />
<html:hidden property="rngAdminApplYearFr" />
<html:hidden property="rngAdminApplMonthFr" />
<html:hidden property="rngAdminApplDayFr" />
<html:hidden property="rngAdminApplYearTo" />
<html:hidden property="rngAdminApplMonthTo" />
<html:hidden property="rngAdminApplDayTo" />
<html:hidden property="rngAdminLastManageYearFr" />
<html:hidden property="rngAdminLastManageMonthFr" />
<html:hidden property="rngAdminLastManageDayFr" />
<html:hidden property="rngAdminLastManageYearTo" />
<html:hidden property="rngAdminLastManageMonthTo" />
<html:hidden property="rngAdminLastManageDayTo" />
<html:hidden property="rngAdminSortKey" />
<html:hidden property="rngAdminOrderKey" />
<html:hidden property="rngAdminPageTop" />
<html:hidden property="rngAdminPageBottom" />
<html:hidden property="rngAdminSearchFlg" />
<html:hidden property="rng070Kekka" />
<html:hidden property="rngInputKeyword" />
<html:hidden property="sltGroupSid" />
<html:hidden property="sltUserSid" />
<html:hidden property="sltApplYearFr" />
<html:hidden property="sltApplMonthFr" />
<html:hidden property="sltApplDayFr" />
<html:hidden property="sltApplYearTo" />
<html:hidden property="sltApplMonthTo" />
<html:hidden property="sltApplDayTo" />
<html:hidden property="sltLastManageYearFr" />
<html:hidden property="sltLastManageMonthFr" />
<html:hidden property="sltLastManageDayFr" />
<html:hidden property="sltLastManageYearTo" />
<html:hidden property="sltLastManageMonthTo" />
<html:hidden property="sltLastManageDayTo" />
<html:hidden property="rngDspMode" />
<html:hidden property="rng010ViewAccount" />
<html:hidden property="rng010SearchCategory" />
<html:hidden property="rng010SelectCategoryUser" />
<html:hidden property="rng010SelectCategory" />
<html:hidden property="rng010DairiFlg" />

<html:hidden property="rngKeyword" />
<html:hidden property="rng010SearchCategory" />
<html:hidden property="rng130Type" />
<html:hidden property="rng130keyKbn" />
<html:hidden property="rng130Status" />
<html:hidden property="rng130searchSubject1" />
<html:hidden property="rng130searchSubject2" />
<html:hidden property="rng130searchSubject3" />
<html:hidden property="sltSortKey1" />
<html:hidden property="rng130orderKey1" />
<html:hidden property="sltSortKey2" />
<html:hidden property="rng130orderKey2" />
<html:hidden property="rng130pageTop" />
<html:hidden property="rng130pageBottom" />

<html:hidden property="svRngViewAccount" />
<html:hidden property="svRng130Category" />
<html:hidden property="svRngKeyword" />
<html:hidden property="svRng130Type" />
<html:hidden property="svGroupSid" />
<html:hidden property="svUserSid" />
<html:hidden property="svRng130keyKbn" />
<html:hidden property="svRng130Status" />
<html:hidden property="svRng130searchSubject1" />
<html:hidden property="svRng130searchSubject2" />
<html:hidden property="svRng130searchSubject3" />
<html:hidden property="svSortKey1" />
<html:hidden property="svRng130orderKey1" />
<html:hidden property="svSortKey2" />
<html:hidden property="svRng130orderKey2" />
<html:hidden property="svApplYearFr" />
<html:hidden property="svApplMonthFr" />
<html:hidden property="svApplDayFr" />
<html:hidden property="svApplYearTo" />
<html:hidden property="svApplMonthTo" />
<html:hidden property="svApplDayTo" />
<html:hidden property="svLastManageYearFr" />
<html:hidden property="svLastManageMonthFr" />
<html:hidden property="svLastManageDayFr" />
<html:hidden property="svLastManageYearTo" />
<html:hidden property="svLastManageMonthTo" />
<html:hidden property="svLastManageDayTo" />

<html:hidden property="rng130searchFlg" />
<html:hidden property="rng030fileId" />
<html:hidden property="rng030RksSid" />
<html:hidden property="rng030KoetuFlg" />
<html:hidden property="rng030SasiNo"/>
<html:hidden property="rng030koetuNo"/>
<html:hidden property="rng030InitFlg"/>

<html:hidden property="rng020copyApply" />
<html:hidden property="rng030ViewTitle" />
<jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
<!--　BODY -->

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../ringi/images/classic/header_ringi.png" alt="<gsmsg:write key="rng.62" />">
      <img class="header_pluginImg" src="../ringi/images/original/header_ringi.png" alt="<gsmsg:write key="rng.62" />">
    </li>
    <li><gsmsg:write key="rng.62" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="sml.sml030.08" />
    </li>
    <li>
      <div>
        <bean:define id="btn" name="rng030Form" property="rng030BtnDisp" />
        <logic:equal name="btn" property="btnRecalcDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.recalc" />" onClick="buttonPush('reload');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.recalc" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.recalc" />">
            <gsmsg:write key="cmn.recalc" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnApprovalDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.41" />" onClick="buttonPush('approval');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_syonin.png" alt="<gsmsg:write key="rng.41" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_syonin.png" alt="<gsmsg:write key="rng.22" />">
            <gsmsg:write key="rng.41" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnDismissalDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.22" />" onClick="buttonPush('reject');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_kyakka.png" alt="<gsmsg:write key="rng.22" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kyakka.png" alt="<gsmsg:write key="rng.22" />">
            <gsmsg:write key="rng.22" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnConfDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.check" />" onClick="buttonPush('confirmation');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="rng.22" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="rng.22" />">
            <gsmsg:write key="cmn.check" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnCompDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.complete" />" onClick="buttonPush('complete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_complete.png" alt="<gsmsg:write key="rng.22" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_complete.png" alt="<gsmsg:write key="rng.22" />">
            <gsmsg:write key="cmn.complete" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnForcedCompDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.06" />" onClick="buttonPush('compelcomplete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_complete.png" alt="<gsmsg:write key="rng.22" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_complete.png" alt="<gsmsg:write key="rng.22" />">
            <gsmsg:write key="rng.rng030.06" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnForcedDelDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.07" />" onClick="buttonPush('compeldelete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="rng.rng030.07" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="rng.rng030.07" />">
            <gsmsg:write key="rng.rng030.07" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnSkipDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.03" />" onClick="buttonPush('skip');">
            <img class="btn_classicImg-display" src="../ringi/images/classic/icon_skip.png" alt="<gsmsg:write key="rng.22" />">
            <img class="btn_originalImg-display" src="../ringi/images/original/icon_skip.png" alt="<gsmsg:write key="rng.22" />">
            <gsmsg:write key="rng.rng030.03" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnRemandDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.08" />" onClick="sasimodosi();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_sashimodoshi.png" alt="<gsmsg:write key="rng.rng030.08" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_sashimodoshi.png" alt="<gsmsg:write key="rng.rng030.08" />">
            <gsmsg:write key="rng.rng030.08" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnTorisageDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.15" />" onClick="buttonPush('torisage');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_kyakka.png" alt="<gsmsg:write key="rng.rng030.15" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kyakka.png" alt="<gsmsg:write key="rng.rng030.15" />">
            <gsmsg:write key="rng.rng030.15" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnReapplyDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.09" />" onClick="buttonPush('applicate');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei.png" alt="<gsmsg:write key="rng.rng030.09" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_sinsei.png" alt="<gsmsg:write key="rng.rng030.09" />">
            <gsmsg:write key="rng.rng030.09" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnPathAddDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng020.02" />" onClick="loadAddKeiroPopup();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="rng.rng020.02" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="rng.rng020.02" />">
            <gsmsg:write key="rng.rng020.02" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnKoetuDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.18" />" onClick="koetu();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_syonin.png" alt="<gsmsg:write key="rng.rng030.18" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_syonin.png" alt="<gsmsg:write key="rng.rng030.18" />">
            <gsmsg:write key="rng.rng030.18" />
          </button>
        </logic:equal>
        <logic:equal name="btn" property="btnReproductionDispFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.13" />" onClick="copyApply();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="rng.rng030.13" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="rng.rng030.13" />">
            <gsmsg:write key="rng.rng030.13" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backList');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="txt_r verAlignMid w100">
  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.pdf" />" onClick="buttonPush2('pdf');">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
    <gsmsg:write key="cmn.pdf" />
  </button>
</div>

<div class="wrapper mt10 txt_l">
  <logic:notEqual parameter="CMD" value="addKeiro">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </logic:notEqual>

  <% String kakuninMode = "kakunin"; %>
  <logic:equal name="rng030Form" property="rngApprMode" value="<%= apprMode_appl %>">
    <%-- 代理人でない場合 --%>
    <logic:equal name="rng030Form" property="rng010DairiFlg" value="0">
      <% kakuninMode = ""; %>
    </logic:equal>
  </logic:equal>
  <% String dspMode = "rng030"; %>
  <!-- フォーム -->
  <ringi:rng030_form name="rng030Form" kakuninMode="<%=kakuninMode %>" dspMode="<%=dspMode %>"/>

  <div class="mt5"/>
  <% String[] keiroParam = {"keiroName","keiroCount","keiroMessage","keiroStatus","keiroSingi","keiroSort","singiCount","keiroKoetuMei","keiroStepSid","keiroLimit", "groupDelFlg"}; %>
  <% String[] singiParam = {"singiName","singiDairi","singiComment","singiStatus","singiDate","singiDairiFlg","singiCheckFlg","singiCommentFlg",
        "tmpFileList","singiKoetu","singiTime","singiPosition","userSid","dairiSid"}; %>
  <% int keiroCount = 0; %>
  <% int conf = 0; %>


  <div class="fixed_header bgC_body keiroKakuninList display_n" id="js_fixed_header">
    <dl class="keiroKakuninList_header bgC_header1 cl_fontOutline">
      <li class="wp50"></li>
      <li class="w100 bor_l1">
        <dl class="keiroKakuninList_keiroStep">
          <li class="wp50 fw_b"><gsmsg:write key="cmn.status" /></li>
          <li class="">
            <dl>
              <li class="w20 fw_b"><gsmsg:write key="cmn.user.name" /></li>
              <li class="ml5 bor_r1 fw_b"></li>
              <li class="mrl_auto fw_b"><gsmsg:write key="cmn.comment" />/<gsmsg:write key="rng.rng030.05" /></li>
              <li class="bor_l1 wp130 fw_b"><gsmsg:write key="rng.rng030.04" /></li>
            </dl>
          </li>
        </dl>
      </li>
    </dl>
  </div>
  <div id="js_header_move" class="bgC_body keiroKakuninList">
    <dl class="keiroKakuninList_header bgC_header1 cl_fontOutline">
      <li class="wp50"></li>
      <li class="w100 bor_l1">
        <dl class="keiroKakuninList_keiroStep">
          <li class="wp50 fw_b"><gsmsg:write key="cmn.status" /></li>
          <li class="">
            <dl>
              <li class="w20 fw_b"><gsmsg:write key="cmn.user.name" /></li>
              <li class="ml5 bor_r1 fw_b"></li>
              <li class="mrl_auto fw_b"><gsmsg:write key="cmn.comment" />/<gsmsg:write key="rng.rng030.05" /></li>
              <li class="bor_l1 wp130 fw_b"><gsmsg:write key="rng.rng030.04" /></li>
            </dl>
          </li>
        </dl>
      </li>
    </dl>
    <% keiroCount = 0; %>
    <bean:define id="channelListCount" name="rng030Form" property="channelListCount" />
    <bean:define id="confirmListCount" name="rng030Form" property="confirmChannelListCount" />
    <logic:notEmpty name="rng030Form" property="channelList">
      <dl class="keiroKakuninList_syounin m0 pos_rel">
        <li class="wp50 txt_c">
          <div class="keiro_arrowBlock pos_abs verAlignMid wp50">
            <img src="../common/images/classic/icon_arrow_south.gif" class="classic-display" alt="<gsmsg:write key="rng.41" />" class="">
            <img src="../common/images/original/icon_arrow_south.png" class="original-display" alt="<gsmsg:write key="rng.41" />" class="">
          </div>
        </li>
        <li>
          <logic:iterate id="keiroGroup" name="rng030Form" property="rng030keiroList" indexId="Idx" type="Rng030KeiroParam" offset="0" length="<%=String.valueOf(channelListCount) %>">
            <% keiroCount += 1; %>
            <jsp:include page="/WEB-INF/plugin/ringi/jsp/rng030_keiro.jsp" >
              <jsp:param value="<%=Idx %>" name="Idx"/>
            </jsp:include>
            <%--経路書き込み処理 --%>
          </logic:iterate>
        </li>
      </dl>
    </logic:notEmpty>
    <logic:notEmpty name="rng030Form" property="confirmChannelList">
      <dl class="keiroKakuninList_kakunin m0">
        <li  class="wp50 txt_c"><gsmsg:write key="cmn.check" /></li>
        <li class="w100 bor_l1">
          <logic:iterate id="keiroGroup" name="rng030Form" property="rng030keiroList" indexId="Idx" type="Rng030KeiroParam" offset="<%=String.valueOf(channelListCount)%>" length="<%=String.valueOf(channelListCount) %>">
            <% keiroCount += 1; %>
            <jsp:include page="/WEB-INF/plugin/ringi/jsp/rng030_keiro.jsp" >
              <jsp:param value="<%=Idx %>" name="Idx"/>
            </jsp:include>
          </logic:iterate>
        </li>
      </dl>
    </logic:notEmpty>
    <div id="keiroCount" class="display_none"><%= keiroCount%></div>
  </div>

  <div class="footerBtn_block mt10">
    <bean:define id="btn" name="rng030Form" property="rng030BtnDisp" />
    <logic:equal name="btn" property="btnRecalcDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.recalc" />" onClick="buttonPush('reload');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.recalc" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.recalc" />">
        <gsmsg:write key="cmn.recalc" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnApprovalDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.41" />" onClick="buttonPush('approval');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_syonin.png" alt="<gsmsg:write key="rng.41" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_syonin.png" alt="<gsmsg:write key="rng.22" />">
        <gsmsg:write key="rng.41" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnDismissalDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.22" />" onClick="buttonPush('reject');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_kyakka.png" alt="<gsmsg:write key="rng.22" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_kyakka.png" alt="<gsmsg:write key="rng.22" />">
        <gsmsg:write key="rng.22" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnConfDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.check" />" onClick="buttonPush('confirmation');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="rng.rng020.02" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="rng.rng020.02" />">
        <gsmsg:write key="cmn.check" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnCompDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.complete" />" onClick="buttonPush('complete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_complete.png" alt="<gsmsg:write key="rng.rng020.02" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_complete.png" alt="<gsmsg:write key="rng.rng020.02" />">
        <gsmsg:write key="cmn.complete" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnForcedCompDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.06" />" onClick="buttonPush('compelcomplete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_complete.png" alt="<gsmsg:write key="rng.rng020.02" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_complete.png" alt="<gsmsg:write key="rng.rng020.02" />">
        <gsmsg:write key="rng.rng030.06" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnForcedDelDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.07" />" onClick="buttonPush('compeldelete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="rng.rng030.07" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="rng.rng030.07" />">
        <gsmsg:write key="rng.rng030.07" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnSkipDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.03" />" onClick="buttonPush('skip');">
        <img class="btn_classicImg-display" src="../ringi/images/classic/icon_skip.png" alt="<gsmsg:write key="rng.rng020.02" />">
        <img class="btn_originalImg-display" src="../ringi/images/original/icon_skip.png" alt="<gsmsg:write key="rng.rng020.02" />">
        <gsmsg:write key="rng.rng030.03" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnRemandDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.08" />" onClick="sasimodosi();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_sashimodoshi.png" alt="<gsmsg:write key="rng.rng030.08" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_sashimodoshi.png" alt="<gsmsg:write key="rng.rng030.08" />">
        <gsmsg:write key="rng.rng030.08" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnTorisageDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.15" />" onClick="buttonPush('torisage');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_kyakka.png" alt="<gsmsg:write key="rng.rng030.15" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_kyakka.png" alt="<gsmsg:write key="rng.rng030.15" />">
        <gsmsg:write key="rng.rng030.15" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnReapplyDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.09" />" onClick="buttonPush('applicate');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei.png" alt="<gsmsg:write key="rng.rng030.09" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_sinsei.png" alt="<gsmsg:write key="rng.rng030.09" />">
        <gsmsg:write key="rng.rng030.09" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnPathAddDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng020.02" />" onClick="loadAddKeiroPopup();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="rng.rng020.02" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="rng.rng020.02" />">
        <gsmsg:write key="rng.rng020.02" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnKoetuDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.18" />" onClick="koetu();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_syonin.png" alt="<gsmsg:write key="rng.rng030.18" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_syonin.png" alt="<gsmsg:write key="rng.rng030.18" />">
        <gsmsg:write key="rng.rng030.18" />
      </button>
    </logic:equal>
    <logic:equal name="btn" property="btnReproductionDispFlg" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng030.13" />" onClick="copyApply();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="rng.rng030.13" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="rng.rng030.13" />">
        <gsmsg:write key="rng.rng030.13" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backList');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />

</html:form>

<jsp:include page="/WEB-INF/plugin/ringi/jsp/rng030_koetu.jsp" />
<jsp:include page="/WEB-INF/plugin/ringi/jsp/rng030_Popup.jsp" />
<logic:equal name="rng030Form" property="rng030AddKeiroMode" value="0">
  <div id="addKeiroPop" title=経路追加 class="display_n"/>
</logic:equal>
<logic:notEqual name="rng030Form" property="rng030AddKeiroMode" value="0">
  <div id="addKeiroPop" title=経路追加 class="display_n">
    <form>
    <%--経路追加確認画面からの戻り時にダイアログ表示 --%>
    <html:hidden property="rng030AddKeiroMode" name="rng030Form" />
    <logic:iterate id="addKeiroEntry" name="rng030Form" property="rng030addKeiroMap" type="Entry">
       <bean:define id="addRksSid" name="addKeiroEntry" property="key" />
       <ringi:rng030_keiroAddibleRow name="rng030Form"  property="<%=\"rng030addKeiro(\" + addRksSid + \")\" %>" block="<%=(Rng020KeiroBlock) addKeiroEntry.getValue() %>"/>
    </logic:iterate>
    <script>$(function (){ loadAddKeiroPopup(); });</script>
    </form>
  </div>
</logic:notEqual>

</body>
</html:html>
