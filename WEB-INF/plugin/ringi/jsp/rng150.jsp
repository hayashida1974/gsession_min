<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<!DOCTYPE html>
<%
  int tmodeShare = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_SHARE;
  int tmodePrivate = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_PRIVATE;
  int tCmdAdd = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_ADD;
  int tCmdEdit = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_EDIT;
%>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="Content-Script-Type" content="text/javascript">
  <script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script type="text/javascript" src="../ringi/js/rng150.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <title>GROUPSESSION <gsmsg:write key="rng.rng150.01" /></title>
</head>
<body>
<html:form action="ringi/rng150">
<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
<html:hidden property="rngTemplateMode" />
<html:hidden property="rng060SelectCat" />
<html:hidden property="rng060SelectCatUsr" />
<input type="hidden" name="rng140ProcMode" value="">
<input type="hidden" name="rng140CatSid" value="">
<input type="hidden" name="rng140SeniFlg" value="">
<html:hidden property="backScreen" />
<html:hidden property="rng010TransitionFlg" />
<input type="hidden" name="helpPrm" value="<bean:write name="rng150Form" property="rngTemplateMode" />">
<input type="hidden" name="helpPrm" value="0">
<bean:define id="rngTemplateMode" name="rng150Form" property="rngTemplateMode" />
<% int rtcMode = ((Integer) rngTemplateMode).intValue(); %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--　BODY -->
<div  class="kanriPageTitle w80 mrl_auto">
  <ul>
    <% if (rtcMode == tmodeShare) { %>
      <li>
        <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
      </li>
      <li>
        <gsmsg:write key="cmn.admin.setting" />
      </li>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.rng150.03" />
      </li>
    <% } else { %>
      <li>
        <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
       </li>
      <li>
        <gsmsg:write key="cmn.preferences2" />
      </li>
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.rng150.04" />
      </li>
    <% } %>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="addEditCategoryWithFlg('-1', <%= tCmdAdd %>, 'addeditcategory');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('rng150back');">
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
  <div class="txt_l">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="return sortUp(<%= String.valueOf(rtcMode) %>);">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="return sortDown(<%= String.valueOf(rtcMode) %>);">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>
  <table class="table-top w100">
    <tr>
      <th class="w5 table_title-color"></th>
      <th class="w95 table_title-color">
        <gsmsg:write key="cmn.category.name" />
      </th>
    </tr>
    <logic:iterate id="tempCatMdl" name="rng150Form" property="rng150CatList" indexId="idx">
      <bean:define id="rtcSid" name="tempCatMdl" property="rtcSid" type="java.lang.Integer" />
      <% String strRtpSid = String.valueOf(rtcSid); %>
      <tr class="js_listHover cursor_p" id="<%= strRtpSid %>">
        <td class="w5 txt_c js_sord_radio">
          <html:radio property="rng150SortRadio" value="<%= strRtpSid %>" />
        </td>
        <td class="w95 js_listClick cl_linkDef">
          <bean:write name="tempCatMdl" property="rtcName" />
        </td>
      </tr>
    </logic:iterate>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="addEditCategoryWithFlg('-1', <%= tCmdAdd %>, 'addeditcategory');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('rng150back');">
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