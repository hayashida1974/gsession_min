<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%
  String okClick         = jp.groupsession.v2.man.man240.Man240Action.CMD_OK;
  String backClick       = jp.groupsession.v2.man.man240.Man240Action.CMD_BACK;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man002.51" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man240.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/main/man240">
<input type="hidden" name="CMD" value="">
<bean:define id="pluginId" value="" />
<bean:define id="pluginName" value="" />
<logic:notEmpty name="man240Form" property="man240LogConfList">
  <logic:iterate id="man240LogConf" name="man240Form" property="man240LogConfList" indexId="indx">
    <% pluginId = "man240LogConf[" + String.valueOf(indx.intValue()) + "].lgcPlugin"; %>
    <% pluginName = "man240LogConf[" + String.valueOf(indx.intValue()) + "].pluginName"; %>
    <input type="hidden" name="<%= pluginId %>" value="<bean:write name='man240Form' property='<%= pluginId %>' />" >
    <input type="hidden" name="<%= pluginName %>" value="<bean:write name='man240Form' property='<%= pluginName %>' />" >
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man002.51" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('<%= okClick %>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= backClick %>');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <div class="txt_l">
    <gsmsg:write key="main.man240.1" />
  </div>
  <table class="table-left w100">
    <logic:notEmpty name="man240Form" property="man240LogConfList">
      <logic:iterate name="man240Form" property="man240LogConfList" id="man240LogConf" indexId="idx" >
        <tr>
          <th class="w25 no_w">
            <bean:write name="man240LogConf" property="pluginName" />
          </th>
          <td class="no_w border_right_none">
            <div class="verAlignMid">
              <html:checkbox name="man240LogConf" property="lgcLevelError" indexed="true" value="1" />
              <a href="#!" class="cl_fontBody" onclick="logLevelCheck('man240LogConf', 'lgcLevelError', '<%= String.valueOf(idx.intValue()) %>');" >
                <img class="btn_classicImg-display ml5 mr5" src="../main/images/classic/icon_log_error.gif" alt="<gsmsg:write key="man.error" />"><img class="btn_originalImg-display ml5 mr5" src="../main/images/original/icon_error.png" alt="<gsmsg:write key="man.error" />"><gsmsg:write key="man.error" />
              </a>
            </div>
          </td>
          <td class="no_w border_right_none">
            <div class="verAlignMid">
              <html:checkbox name="man240LogConf" styleClass="ml10" property="lgcLevelWarn" indexed="true" value="1"/>
              <a href="#!" class="cl_fontBody" onclick="logLevelCheck('man240LogConf', 'lgcLevelWarn', '<%= String.valueOf(idx.intValue()) %>');">
                <img class="btn_classicImg-display ml5 mr5" src="../main/images/classic/icon_log_warn.gif" alt="<gsmsg:write key="cmn.warning" />"><img class="btn_originalImg-display ml5 mr5" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />"><gsmsg:write key="cmn.warning" />
              </a>
            </div>
          </td>
          <td class="no_w border_right_none">
            <div class="verAlignMid">
              <html:checkbox name="man240LogConf" styleClass="ml10" property="lgcLevelInfo" indexed="true" value="1"/>
              <a href="#!" class="cl_fontBody" onclick="logLevelCheck('man240LogConf', 'lgcLevelInfo', '<%= String.valueOf(idx.intValue()) %>');">
                <img class="btn_classicImg-display ml5 mr5" src="../main/images/classic/icon_log_info.gif" alt="<gsmsg:write key="main.man240.2" />"><img class="btn_originalImg-display ml5 mr5" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="main.man240.2" />"><gsmsg:write key="main.man240.2" />
              </a>
            </div>
          </td>
          <td class="no_w w75">
            <div class="verAlignMid">
              <html:checkbox name="man240LogConf" styleClass="ml10" property="lgcLevelTrace" indexed="true" value="1"/>
              <a href="#!" class="cl_fontBody" onclick="logLevelCheck('man240LogConf', 'lgcLevelTrace', '<%= String.valueOf(idx.intValue()) %>');">
                <img class="btn_classicImg-display ml5 mr5" src="../main/images/classic/icon_log_trace.gif" alt="<gsmsg:write key="main.man240.3" />"><img class="btn_originalImg-display ml5 mr5" src="../common/images/original/icon_info.png" alt="<gsmsg:write key="main.man240.3" />"><gsmsg:write key="main.man240.3" />
              </a>
            </div>
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('<%= okClick %>');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%= backClick %>');">
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