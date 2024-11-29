<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man460.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/main/man480kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="man440GrpSid"/>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man480kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('Man480kn_Import');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('Man480kn_Back');">
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
  <html:errors/>
</logic:messagesPresent>
</div>
<div class="txt_l cl_fontWarn"><gsmsg:write key="cmn.capture.file.sure" /></div>
<!-- <gsmsg:write key="cmn.capture.file" /> -->
  <table class="table-left">
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.capture.file" />
      </th>
      <td class="w75">
        <logic:notEmpty name="man480knForm" property="man480FileLabelList">
        <logic:iterate name="man480knForm" property="man480FileLabelList" id="file">
          <bean:write name="file" property="label"/><br />
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.capture.item.count" />
      </th>
      <td>
        <bean:write name="man480knForm" property="man480impCount" /><gsmsg:write key="cmn.number" />
      </td>
    </tr>
  <%--登録対象プロジェクト --%>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="main.man480.2" />
      </th>
      <td class="w75">
        <html:hidden name="man480knForm" property="man480mode"/>
        <!-- 新規プロジェクト作成 -->
        <logic:equal name="man480knForm" property="man480mode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_ADD)%>">
          <gsmsg:write key="main.man480.3" />
          <div class="ml10">
            <div class="fw_b"><gsmsg:write key="project.31" />&nbsp;:&nbsp;<bean:write name="man480knForm" property="man480projectID"/></div>
            <html:hidden name="man480knForm" property="man480projectID"  />
            <div class="fw_b mt5"><gsmsg:write key="project.40" />&nbsp;:&nbsp;<bean:write name="man480knForm" property="man480projectName"/></div>
            <html:hidden name="man480knForm" property="man480projectName" />
            <div class="fw_b mt5"><gsmsg:write key="project.41" />&nbsp;:&nbsp;<bean:write name="man480knForm" property="man480projectShortName"/></div>
            <html:hidden name="man480knForm" property="man480projectShortName" />
            <div class="fw_b mt5"><gsmsg:write key="main.man480.5" /></div>
            ・<bean:write name="man480knForm" property="man480grpName" />
          </div>
        </logic:equal>
        <!-- 登録済みプロジェクト -->
        <logic:equal name="man480knForm" property="man480mode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_EDIT) %>">
          <gsmsg:write key="main.man480.4" />
          <div class="ml10">
            <!-- プロジェクト選択 -->
            <bean:define name="man480knForm" property="man480selectProject" id="selectPrj" />
            <logic:notEmpty name="man480knForm" property="man480projectLabelList">
            <logic:iterate name="man480knForm" property="man480projectLabelList" id="label">
              <logic:equal value="<%=String.valueOf(selectPrj) %>" name="label" property="value">
                <html:hidden name="man480knForm" property="man480selectProject"/>
                <bean:write name="label" property="label"/>
              </logic:equal>
            </logic:iterate>
            </logic:notEmpty>
            <div>
              ※<gsmsg:write key="main.man480.7" />
            </div>
          </div>
        </logic:equal>
      </td>
    </tr>
  <%--進捗設定 --%>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="main.man480.6" />
      </th>
      <td class="w75">
        ※<gsmsg:write key="main.man480.8" />
        <table class="table-noBorder">
          <tr>
            <td class="w10 txt_r">未着手</td>
            <td class="w5 txt_r">0%</td>
            <td class="w85"></td>
          </tr>
          <logic:notEmpty name="man480knForm" property="man480svStatusLabelList">
            <logic:iterate name="man480knForm" property="man480svStatusLabelList" id="statusLabel" indexId="idx">
              <tr>
                <td class="txt_r"><bean:write name="statusLabel" property="label"/></td>
                <td>
                  <bean:write name="statusLabel" property="value"/>%
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
          <logic:notEmpty name="man480knForm" property="man480statusNames">
            <logic:iterate name="man480knForm" property="man480statusNames" id="statusName" indexId="idx">
              <tr>
                <td class="w10 txt_r"><bean:write name="statusName" /></td>
                <td class="w5 txt_r">
                  <html:hidden name="man480knForm" property="<%=\"man480statusValue(\" + idx + \")\" %>" />
                  <bean:write name="man480knForm" property="<%=\"man480statusValue(\" + idx + \")\" %>" />%
                </td>
                <td class="w85"></td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
          <tr>
            <td class="w10 txt_r"><gsmsg:write key="cmn.complete" /></td>
            <td class="w5 txt_r">100%</td>
            <td class="w85"></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('Man480kn_Import');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('Man480kn_Back');">
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