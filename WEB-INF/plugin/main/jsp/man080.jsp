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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man080.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.autobackup.setting" /></title>
</head>
<body onload="setDisabled();">
<html:form action="/main/man080">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="man080backupFile" value="">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w90 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.autobackup.setting" />
    </li>
    <li>
      <div>
        <button type="type" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backadmconf');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man080.1" />
      </th>
      <td class="w80">
        <div>
          <span class="cl_fontWarn"><gsmsg:write key="main.man080.2" /></span>
        </div>
        <div>
          <bean:define id="man080batTime" name="man080Form" property="man080batchHour" type="java.lang.String" />
          <gsmsg:write key="main.man080.3" arg0="<%= man080batTime %>" />
        </div>
        <div class="mt10">
          <span class="verAlignMid">
          <html:radio styleId="noset" name="man080Form" property="man080Interval" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.BUCCONF_INTERVAL_NOSET) %>" onclick="setDisabled();" /><label for="noset"><gsmsg:write key="cmn.noset" /></label>
          </span>
        </div>
        <div class="mt5">
          <span class="verAlignMid">
          <html:radio styleId="day" name="man080Form" property="man080Interval" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.BUCCONF_INTERVAL_DAILY) %>" onclick="setDisabled();" /><label for="day"><gsmsg:write key="cmn.everyday" /></label>
          </span>
        </div>
        <div class="mt5">
          <span class="verAlignMid">
          <html:radio styleId="week" name="man080Form" property="man080Interval" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.BUCCONF_INTERVAL_WEEKLY) %>" onclick="setDisabled();" /><label for="week"><gsmsg:write key="cmn.weekly2" /></label>
          <html:select name="man080Form" property="man080dow" styleClass="ml20">
            <html:optionsCollection name="man080Form" property="dowList" />
          </html:select>
          </span>
        </div>
        <div class="mt5">
          <span class="verAlignMid">
          <html:radio styleId="month" name="man080Form" property="man080Interval" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.BUCCONF_INTERVAL_MONTHLY) %>" onclick="setDisabled();" /><label for="month"><gsmsg:write key="cmn.monthly.2" /></label>
          <html:select name="man080Form" property="man080weekmonth" styleClass="ml20 mr5">
            <html:optionsCollection name="man080Form" property="weekmonthList" />
          </html:select>
          <html:select name="man080Form" property="man080monthdow">
            <html:optionsCollection name="man080Form" property="dowList" />
          </html:select>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="man.number.generations" />
      </th>
      <td>
        <div class="cl_fontWarn">
          <gsmsg:write key="main.man080.5" />
        </div>
        <div class="mt10">
          <html:select name="man080Form" property="man080generation">
            <html:optionsCollection name="man080Form" property="generationList" />
          </html:select>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="main.output" />
      </th>
      <td class="w80">
        <span class="verAlignMid">
          <html:radio name="man080Form" property="man080zipOutputKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.ZIP_BACKUP_FLG_OFF) %>" styleId="man080notCompress" />
          <label for="man080notCompress"><gsmsg:write key="main.not.compress" /></label>
          <html:radio name="man080Form" property="man080zipOutputKbn" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.ZIP_BACKUP_FLG_ON) %>" styleId="man080zipOutput" />
          <label for="man080zipOutput"><gsmsg:write key="main.zip.format.output" /></label>
        </span>
      </td>
    </tr>
  </table>
  <table class="table-top" w100>
    <tr>
      <th class="w50 table_title-color">
        <gsmsg:write key="cmn.backupfile" />
      </th>
      <th class="w30 table_title-color">
        <gsmsg:write key="man.creation.date" />
      </th>
      <th class="w20 table_title-color">
        <gsmsg:write key="main.man080.7" />
      </th>
    </tr>
    <logic:iterate id="fileData" name="man080Form" property="fileDataList" indexId="idx">
      <logic:equal name="fileData" property="zipOutput" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.ZIP_BACKUP_FLG_ON) %>" >
        <tr class="js_listHover cursor_p" data-name="<bean:write name="fileData" property="hashFileName" />">
          <td class="txt_l js_listClick">
            <span class="cl_linkDef"><bean:write name="fileData" property="fileName" /></span>
          </td>
          <td class="txt_c no_w js_listClick">
            <bean:write name="fileData" property="strMakeDate" />
          </td>
          <td class="txt_r no_w js_listClick">
            <bean:write name="fileData" property="fileSize" />
          </td>
        </tr>
      </logic:equal>
      <logic:equal name="fileData" property="zipOutput" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.ZIP_BACKUP_FLG_OFF) %>" >
        <tr>
          <td class="txt_l">
            <bean:write name="fileData" property="fileName" />
          </td>
          <td class="txt_c no_w">
            <bean:write name="fileData" property="strMakeDate" />
          </td>
          <td class="txt_r no_w">
            <bean:write name="fileData" property="fileSize" />
          </td>
        </tr>
      </logic:equal>
    </logic:iterate>
  </table>
  <div class="footerBtn_block">
    <button type="type" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backadmconf');">
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