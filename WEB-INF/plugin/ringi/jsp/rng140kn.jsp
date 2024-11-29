<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page import="jp.groupsession.v2.usr.UserUtil"%>
<%@ page import="jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel"%>
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
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <title>GROUPSESSION <gsmsg:write key="rng.62" /> <gsmsg:write key="cmn.category.entry.kn" /></title>
</head>
<body>
<html:form action="/ringi/rng140kn">
<input type="hidden" name="CMD" value="ok">
<%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
<html:hidden property="rngTemplateMode" />
<html:hidden property="backScreen" />
<html:hidden property="rng010TransitionFlg" />
<html:hidden property="rng060SelectCat" />
<html:hidden property="rng140CatSid" />
<html:hidden property="rng140CatName" />
<html:hidden property="rng140ProcMode" />
<html:hidden property="rng140SeniFlg" />
<html:hidden property="rng140UserLimit"/>
<html:hidden property="rng140UserLimitType"/>
<input type="hidden" name="helpPrm" value="<bean:write name="rng140Form" property="rngTemplateMode" />">
<input type="hidden" name="helpPrm" value="<bean:write name="rng140Form" property="rng140ProcMode" />">
<bean:define id="rngTemplateMode" name="rng140knForm" property="rngTemplateMode" />
<% int rtMode = ((Integer) rngTemplateMode).intValue(); %>
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
        <logic:equal name="rng140knForm" property="rng140ProcMode" value="<%= String.valueOf(tCmdAdd) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.114" />
        </logic:equal>
        <logic:equal name="rng140knForm" property="rng140ProcMode" value="<%= String.valueOf(tCmdEdit) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.115" />
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
          <logic:equal name="rng140knForm" property="rng140ProcMode" value="<%= String.valueOf(tCmdAdd) %>">
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.116" />
          </logic:equal>
          <logic:equal name="rng140knForm" property="rng140ProcMode" value="<%= String.valueOf(tCmdEdit) %>">
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.117" />
          </logic:equal>
       </li>
    <% } %>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('kakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('input_back');">
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
    <tr>
      <!-- カテゴリ名 -->
      <th class="w25">
        <gsmsg:write key="cmn.category.name" />
      </th>
      <td class="w75">
        <bean:write name="rng140knForm" property="rng140CatName" />
      </td>
    </tr>
    <logic:equal name="rng140Form" property="rngTemplateMode" value="1">
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.category" /><gsmsg:write key="cmn.admin" />
        </th>
        <td class="w75">
          <logic:iterate id="user" name="rng140knForm" property="rng140UserAdmList.selectedList(adminList)" type="UsrLabelValueBean">
            <div class="<%=user.getCSSClassNameNormal()%>">
              <bean:write name="user" property="label" />
            </div>
            <html:hidden property="rng140UserAdmList.selected(adminList)" value="<%=user.getValue() %>" />
          </logic:iterate>
        </td>
      </tr>
      <!-- カテゴリ使用制限 -->
      <tr>
        <th class="w25">
          <gsmsg:write key="rng.05"/>
        </td>
        <td class="w75">
          <logic:equal name="rng140knForm" property="rng140UserLimit" value="0">
            <gsmsg:write key="cmn.not.limit"/>
          </logic:equal>
          <logic:equal name="rng140knForm" property="rng140UserLimit" value="1">
            <gsmsg:write key="cmn.do.limit"/>
          </logic:equal>
        </th>
      </tr>
      <!-- カテゴリ使用権限 -->
      <logic:equal name="rng140knForm" property="rng140UserLimit" value="1">
        <tr>
          <th class="w25">
            <gsmsg:write key="schedule.sch240.05"/>
          </th>
          <td class="w75">
            <div class="fw_b">
              <gsmsg:write key="cmn.howto.limit"/>
            </div>
            <logic:equal name="rng140knForm" property="rng140UserLimitType" value="0">
              <gsmsg:write key="cmn.access.permit" /><br>
            </logic:equal>
            <logic:equal name="rng140knForm" property="rng140UserLimitType" value="1">
              <gsmsg:write key="cmn.access.limit" /><br>
            </logic:equal>
            <div class="settingForm_separator"></div>
            <div class="fw_b">
              <gsmsg:write key="schedule.sch240.05"/>
            </div>
            <logic:iterate id="user" name="rng140knForm" property="rng140UserLimitList.selectedList(limitList)" type="UsrLabelValueBean">
              <div class="<%=user.getCSSClassNameNormal()%>">
                <bean:write name="user" property="label" />
              </div>
              <html:hidden property="rng140UserLimitList.selected(limitList)" value="<%=user.getValue() %>" />
            </logic:iterate>
          </td>
        </tr>
      </logic:equal>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('kakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('input_back');">
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