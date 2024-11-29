<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/formbuilder" prefix="fb" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% int tmodeAll = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_ALL; %>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../ringi/css/ringi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<script src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/components/file-preview/index.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../ringi/js/rng020.js?<%= GSConst.VERSION_PARAM %>"></script>
<title>GROUPSESSION <gsmsg:write key="rng.rng020kn.02" /></title>
</head>

<body onunload="windowClose();">

<html:form action="ringi/rng020kn">
<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
<html:hidden property="rngSid" />
<html:hidden property="rngCmdMode" />
<html:hidden property="rngApprMode" />
<html:hidden property="rngTemplateMode" />

<html:hidden property="rng130Type" />
<html:hidden property="rng130Status" />
<html:hidden property="svRng130Status" />
<html:hidden property="sltGroupSid" />
<html:hidden property="sltUserSid" />
<html:hidden property="rng130keyKbn" />
<html:hidden property="rng130searchSubject1" />
<html:hidden property="rng130searchSubject2" />
<html:hidden property="rng130searchSubject3" />
<html:hidden property="sltSortKey1" />
<html:hidden property="rng130orderKey1" />
<html:hidden property="sltSortKey2" />
<html:hidden property="rng130orderKey2" />
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
<html:hidden property="rng130pageTop" />
<html:hidden property="rng130pageBottom" />

<html:hidden property="svRngViewAccount" />
<html:hidden property="svRng130Category" />
<html:hidden property="svRngKeyword" />
<html:hidden property="svRng130Type" />
<html:hidden property="svGroupSid" />
<html:hidden property="svUserSid" />
<html:hidden property="svRng130keyKbn" />
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

<html:hidden property="rng020copyApply" />
<html:hidden property="rng020ScrollFlg" />
<html:hidden property="rng020rtpSid" />
<html:hidden property="rng020rtpVer" />
<html:hidden property="rng020rtpKeiroVersion" />
<html:hidden property="rng020prevForward" />
<html:hidden property="rng020IdPrefManual" />
<html:hidden property="rng020Title" />
<html:hidden property="rng020requestUserId" />
<html:hidden property="rng020ID" />

<input type="hidden" name="rng020BinSid">

<logic:equal value="1" name="rng020knForm" property="useKeiroTemplateFlg" >
<input type="hidden" name="helpPrm" value="0" >
</logic:equal>
<logic:notEqual value="1" name="rng020knForm" property="useKeiroTemplateFlg" >
<input type="hidden" name="helpPrm" value="1" >
</logic:notEqual>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- body -->

<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../ringi/images/classic/header_ringi.png" alt="<gsmsg:write key="rng.62" />">
      <img class="header_pluginImg" src="../ringi/images/original/header_ringi.png" alt="<gsmsg:write key="rng.62" />">
    </li>
    <li>
      <gsmsg:write key="rng.62" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="rng.rng020kn.01" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="rng.46" />" onClick="buttonPush('rng020knSinsei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei.png" alt="<gsmsg:write key="rng.46" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_sinsei.png" alt="<gsmsg:write key="rng.46" />">
          <gsmsg:write key="rng.46" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rng020knback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w90 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <ringi:rng020_form name="rng020knForm" kakuninMode="kakunin"/>
  <div class="mt10"></div>
  <ringi:rng020_keiro name="rng020knForm" kakuninMode="kakunin" ></ringi:rng020_keiro>
  <div class="footerBtn_block mt20">
    <button type="button" class="baseBtn" value="<gsmsg:write key="rng.46" />" onClick="buttonPush('rng020knSinsei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei.png" alt="<gsmsg:write key="rng.46" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_sinsei.png" alt="<gsmsg:write key="rng.46" />">
      <gsmsg:write key="rng.46" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rng020knback');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>