<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<gsmsg:define id="pageTitle" msgkey="main.man021.1" type="java.lang.String" />
<gsmsg:define id="confirmBtnValue" msgkey="user.59" type="java.lang.String" />
<logic:equal name="man021Form" property="man021CmdMode" value="2">
  <gsmsg:define id="pageTitle2" msgkey="main.man021.2" type="java.lang.String" />
  <gsmsg:define id="confirmBtnValue2" msgkey="schedule.43" type="java.lang.String" />
  <% pageTitle = pageTitle2.toString(); %>
  <% confirmBtnValue = confirmBtnValue2.toString(); %>
</logic:equal>


<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <%= pageTitle %></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man021.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/main/man021">
<input type="hidden" name="CMD" value="confirm">
<html:hidden property="man020DspYear" />
<html:hidden property="editHolDate" />
<html:hidden property="man021CmdMode" />
<html:hidden property="man021HolMonthOld" />
<html:hidden property="man021HolDayOld" />

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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="main.holiday.setting" /></span><gsmsg:write key="cmn.add" />
    </li>
    <li>
      <div>
        <logic:equal name="man021Form" property="man021CmdMode" value="1">
          <button type="submit" class="baseBtn" value="<%= confirmBtnValue %>">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
        </logic:equal>
        <logic:equal name="man021Form" property="man021CmdMode" value="2">
          <button type="submit" class="baseBtn" value="<%= confirmBtnValue %>">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
            <gsmsg:write key="cmn.change" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('2');">
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
  <table class="table-left w100">
    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.date2" />
      </th>
      <td class="w70">
        <html:select name="man021Form" property="man021HolMonth">
          <html:optionsCollection name="man021Form" property="man021MonthLabel" value="value" label="label" />
        </html:select>
        <html:select name="man021Form" property="man021HolDay">
          <html:optionsCollection name="man021Form" property="man021DayLabel" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.holiday.name" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w70">
        <html:text name="man021Form" styleClass="wp300" maxlength="20" property="man021HolName" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <logic:equal name="man021Form" property="man021CmdMode" value="1">
      <button type="submit" class="baseBtn" value="<%= confirmBtnValue %>">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <gsmsg:write key="cmn.add" />
      </button>
    </logic:equal>
    <logic:equal name="man021Form" property="man021CmdMode" value="2">
      <button type="submit" class="baseBtn" value="<%= confirmBtnValue %>">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.change" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.change" />">
        <gsmsg:write key="cmn.change" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('2');">
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