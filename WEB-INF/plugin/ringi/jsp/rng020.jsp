<%@page import="jp.groupsession.v2.rng.RngConst"%>
<%@page import="jp.groupsession.v2.rng.rng060.IRng060PersonalParam"%>
<%@page import="jp.groupsession.v2.rng.rng060.Rng060PersonalParamImpl"%>
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

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% int tmodeAll = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_ALL; %>
<% String notAcceptHanyo = String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_HANYO_FLG_NO); %>
<% String maxLengthContent = String.valueOf(jp.groupsession.v2.rng.RngConst.MAX_LENGTH_CONTENT); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">


<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../ringi/css/ringi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>



<script src="../common/js/glayer.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js" ></script>

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../ringi/js/rng020.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

<cmn:loadscript src="../common/js/formbuilder/usrgrpselect.js" />

<script>
 msglist_rng020 = new Array();
 msglist_rng020['cmn.cancel'] = '<gsmsg:write key="cmn.cancel"/>';
</script>

<title>GROUPSESSION <gsmsg:write key="rng.rng020.04" /></title>
</head>

<body onunload="windowClose();" onload="">

<html:form action="ringi/rng020">
<%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
<input type="hidden" name="CMD" value="">

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
<html:hidden property="svRng130searchSubject3" />
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

<html:hidden property="rng020requestUserId" />

<input type="hidden" name="loadRctSid" />
<input type="hidden" name="scrollY" />


<bean:define id="rng060params" name="rng020Form" property="rng060params" type="Rng060PersonalParamImpl" ></bean:define>
<gsform:writehidden bean="<%=rng060params%>" beanClass="<%=IRng060PersonalParam.class %>"></gsform:writehidden>

<input type="hidden" name="rng020TemplateFileId" value="">
<logic:equal value="1" name="rng020Form" property="useKeiroTemplateFlg" >
  <input type="hidden" name="helpPrm" value="0" >
</logic:equal>
<logic:notEqual value="1" name="rng020Form" property="useKeiroTemplateFlg" >
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
      <gsmsg:write key="rng.46" />
    </li>
    <li>
      <div>
        <logic:notEqual value="false" name="rng020Form" property="rng020ButtonDsp" >
          <logic:notEqual value="false" name="rng020Form" property="rng020input.haveAutoCalc" >
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.recalc" />" onClick="buttonPush('reload');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png">
              <gsmsg:write key="cmn.recalc" />
            </button>
          </logic:notEqual>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.save.draft" />" onClick="buttonPush('draft');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_save_soukou.png">
            <img class="btn_originalImg-display" src="../common/images/original/icon_save_soukou.png">
            <gsmsg:write key="cmn.save.draft" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('approval');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png">
            <gsmsg:write key="cmn.ok" />
          </button>
        </logic:notEqual>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rng010');">
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
  <logic:equal value="<%=notAcceptHanyo %>" name="rng020Form" property="useTemplateFlg" >
    <div class="textError"><gsmsg:write key="rng.rng020.error.use.accept" /></div>
  </logic:equal>
  <ringi:rng020_form name="rng020Form"/>
  <div id="keiroupdatebox" class="display_n txt_l mt10">
    <input type="button" class="baseBtn" onclick="document.forms[0].scrollY.value=$(window).scrollTop(); buttonPush('reload');" value="<gsmsg:write key="cmn.update"/>"/>
    <gsmsg:write key="rng.rng020.08" />
  </div>

  <div class="mt10 mb10 txt_l">
    <logic:equal value="1" name="rng020Form" property="useKeiroTemplateFlg" >
      <button type="button" class="baseBtn js_load_rct" name="loadKeiroButton" class="baseBtn" value="<gsmsg:write key="cmn.use.template" />">
        <gsmsg:write key="cmn.use.template" />
      </button>
      <ringi:ring020_keirotemplateDialog hiddenShare="hidden"/>
    </logic:equal>
  </div>

  <ringi:rng020_keiro name="rng020Form" ></ringi:rng020_keiro>
  <div class="footerBtn_block mt20">
    <logic:notEqual value="false" name="rng020Form" property="rng020ButtonDsp" >
      <logic:notEqual value="false" name="rng020Form" property="rng020input.haveAutoCalc" >
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.recalc" />" onClick="buttonPush('reload');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png">
          <gsmsg:write key="cmn.recalc" />
        </button>
      </logic:notEqual>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.save.draft" />" onClick="buttonPush('draft');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_save_soukou.png">
        <img class="btn_originalImg-display" src="../common/images/original/icon_save_soukou.png">
        <gsmsg:write key="cmn.save.draft" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('approval');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png">
        <gsmsg:write key="cmn.ok" />
      </button>
    </logic:notEqual>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rng010');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<logic:equal name="rng020Form" property="keiroAutoChanged" value="1">
  <div id="warning_keiro_autochange" title="" class="display_n">
    <table class="w100 h100">
      <tr>
        <td class="w10">
          <img src="../common/images/classic/icon_info.png" class="wp30hp30 btn_classicImg-display">
          <img src="../common/images/original/icon_info.png" class="wp30hp30 btn_originalImg-display">
        </td>
        <td class="w90">
          <gsmsg:write key="rng.rng020.07"/>
        </td>
      </tr>
    </table>
 </div>
</logic:equal>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>
