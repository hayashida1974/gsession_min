<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>

<!DOCTYPE html>
<%
String maxLengthBiko = String.valueOf(jp.groupsession.v2.rng.RngConst.MAX_LENGTH_COMMENT);
int tCmdAdd = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_ADD;
int tCmdEdit = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_EDIT;
int tmodeShare = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_SHARE;
int tmodePrivate = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_PRIVATE;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>

  <script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/usrgrpselect.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../ringi/js/rng140.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../ringi/css/ringi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <ringi:rng140_message/>
  <title>GROUPSESSION <gsmsg:write key="cmn.category.entry" /></title>
</head>

<body onload="initLoad();">
<bean:define id="rngTemplateMode" name="rng140Form" property="rngTemplateMode" />
<% int rtMode = ((Integer) rngTemplateMode).intValue(); %>
<html:form action="/ringi/rng140">
<input type="hidden" name="CMD" value="ok">
<%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
<html:hidden property="rng140SeniFlg" />
<html:hidden property="rng140CatSid" />
<html:hidden property="rng140ProcMode" />
<html:hidden property="rngTemplateMode" />
<html:hidden property="backScreen" />
<html:hidden property="rng010TransitionFlg" />
<html:hidden property="rng060SelectCat" />
<input type="hidden" name="helpPrm" value="<bean:write name="rng140Form" property="rngTemplateMode" />">
<input type="hidden" name="helpPrm" value="<bean:write name="rng140Form" property="rng140ProcMode" />">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <% if (rtMode == tmodeShare) { %>
      <li>
        <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
      </li>
      <li>
        <gsmsg:write key="cmn.admin.setting" />
      </li>
      <li class="pageTitle_subFont">
        <logic:equal name="rng140Form" property="rng140ProcMode" value="<%= String.valueOf(tCmdAdd) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.23" />
        </logic:equal>
        <logic:equal name="rng140Form" property="rng140ProcMode" value="<%= String.valueOf(tCmdEdit) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.118" />
        </logic:equal>
       </li>
    <% } else if (rtMode == tmodePrivate) { %>
      <li>
        <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
       </li>
      <li>
        <gsmsg:write key="cmn.preferences2" />
      </li>
      <li class="pageTitle_subFont">
          <logic:equal name="rng140Form" property="rng140ProcMode" value="<%= String.valueOf(tCmdAdd) %>">
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.33" />
          </logic:equal>
          <logic:equal name="rng140Form" property="rng140ProcMode" value="<%= String.valueOf(tCmdEdit) %>">
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.119" />
          </logic:equal>
       </li>
    <% } %>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="rng140Form" property="rng140ProcMode" value="<%= String.valueOf(tCmdEdit) %>">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('rng140back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left w100">
    <!-- カテゴリ名 -->
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.category.name" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <html:text maxlength="20" property="rng140CatName" styleClass="wp190" />
      </td>
    </tr>
    <!-- カテゴリ管理者 -->
    <logic:equal name="rng140Form" property="rngTemplateMode" value="1">
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.category" /><gsmsg:write key="cmn.admin" />
        </th>
        <td class="w75">
          <ui:usrgrpselector name="rng140Form" property="rng140UserAdmSelector" styleClass="hp160"/>
        </td>
      </tr>
      <!-- カテゴリ使用制限 -->
      <tr>
        <th class="w25">
          <gsmsg:write key="rng.05"/>
        </th>
        <td class="w75">
          <span class="verAlignMid">
            <html:radio styleId="noUseLimit" name="rng140Form" property="rng140UserLimit" value="0" onclick="hideOrDsp();"/>
            <label for="noUseLimit"><gsmsg:write key="cmn.not.limit"/></label>
            <html:radio styleClass="ml10" styleId="useLimit" name="rng140Form" property="rng140UserLimit" value="1" onclick="hideOrDsp();"/>
            <label for="useLimit"><gsmsg:write key="cmn.do.limit"/></label>
          </span>
        </td>
      </tr>
      <!-- カテゴリ使用制限対象 -->
      <tr id="limitType">
        <th class="w25">
          <gsmsg:write key="schedule.sch240.05"/><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </th>
        <td class="td_wt w80">
          <span class="verAlignMid">
            <html:radio styleId="nonPermitted" name="rng140Form" property="rng140UserLimitType" value="0" onclick="changeDsp('limitList', 'rng.rng140.useLimit');"/>
            <label for="nonPermitted" ><gsmsg:write key="cmn.access.permit" /></label>
            <html:radio styleClass="ml10" styleId="permitted" name="rng140Form" property="rng140UserLimitType" value="1"  onclick="changeDsp('limitList', 'rng.rng140.useAccept');"/>
            <label for="permitted" ><gsmsg:write key="cmn.access.limit" /></label>
          </span>
          <div class="mt5"></div>
          <ui:usrgrpselector name="rng140Form" property="rng140UserLimitSelector" styleClass="hp160"/>
        </td>
      </tr>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="rng140Form" property="rng140ProcMode" value="<%= String.valueOf(tCmdEdit) %>">
      <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('rng140back');">
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