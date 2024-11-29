<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="bbs.bbs040.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bulletin/js/bbs040.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body onload="bbs040DateKbn();">

  <html:form action="/bulletin/bbs040">
    <input type="hidden" name="CMD" value="">
    <html:hidden name="bbs040Form" property="bbs010page1" />
    <html:hidden name="bbs040Form" property="bbs010forumSid" />
    <html:hidden name="bbs040Form" property="bbs060page1" />
    <html:hidden name="bbs040Form" property="searchDspID" />
    <input type="hidden" name="hourDivision" value="5" />
    <input type="hidden" name="yearRangeMinFr" value="<bean:write name="bbs040Form" property="bbs040oldYear" />" />
    <input type="hidden" name="yearRangeMaxFr" value="0" />
    <input type="hidden" name="yearRangeMinTo" value="<bean:write name="bbs040Form" property="bbs040oldYear" />" />
    <input type="hidden" name="yearRangeMaxTo" value="0" />
    <input type="hidden" name="bbs040fromYear" value="" />
    <input type="hidden" name="bbs040fromMonth" value="" />
    <input type="hidden" name="bbs040fromDay" value="" />
    <input type="hidden" name="bbs040toYear" value="" />
    <input type="hidden" name="bbs040toMonth" value="" />
    <input type="hidden" name="bbs040toDay" value="" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <!-- BODY -->
    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../bulletin/images/classic/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
          <img class="header_pluginImg" src="../bulletin/images/original/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
        </li>
        <li>
          <gsmsg:write key="cmn.bulletin" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="cmn.advanced.search" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToList');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left w100">

        <tr>
          <th class="w20 txt_c">
            <gsmsg:write key="bbs.3" />
          </th>
          <td>
            <html:select property="bbs040forumSid" styleClass="select_forum">
              <logic:notEmpty name="bbs040Form" property="bbs040forumList">
                <html:optionsCollection name="bbs040Form" property="bbs040forumList" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
          </td>
        </tr>

        <tr>
         <th class="w20 txt_c">
            <gsmsg:write key="cmn.keyword" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
          </th>
          <td>
            <div>
            <html:text name="bbs040Form" property="s_key" maxlength="50" styleClass="wp300" />
            </div>
            <span class="verAlignMid">
              <html:radio name="bbs040Form" styleId="bbs040keyKbn_01" property="bbs040keyKbn" value="0" />
              <label for="bbs040keyKbn_01">
                <gsmsg:write key="cmn.contains.all" />(AND)
              </label>
              <html:radio styleClass="ml10" name="bbs040Form" styleId="bbs040keyKbn_02" property="bbs040keyKbn" value="1" />
              <label for="bbs040keyKbn_02">
                <gsmsg:write key="cmn.containing.either" />(OR)
              </label>
            </span>
          </td>
        </tr>

        <tr>
         <th class="w20 txt_c">
            <gsmsg:write key="cmn.search2" />
          </th>
          <td>
            <span class="verAlignMid">
              <html:checkbox name="bbs040Form" styleId="bbs040taisyouThread" property="bbs040taisyouThread" value="1" />
              <label for="bbs040taisyouThread">
                <gsmsg:write key="bbs.bbs040.2" />
              </label>
              <html:checkbox styleClass="ml10" name="bbs040Form" styleId="bbs040taisyouNaiyou" property="bbs040taisyouNaiyou" value="1" />
              <label for="bbs040taisyouNaiyou">
                <gsmsg:write key="cmn.content" />
              </label>
            </span>
          </td>
        </tr>

        <tr>
         <th class="w20 txt_c">
            <gsmsg:write key="cmn.contributor.name" />
          </th>
          <td>
            <span>
              <html:text name="bbs040Form" property="bbs040userName" maxlength="20" styleClass="wp300" />
            </span>
          </td>
        </tr>

        <tr>
         <th class="w20 txt_c">
            <gsmsg:write key="bbs.8" />
          </th>
          <td>
            <span class="verAlignMid">
              <html:radio name="bbs040Form" styleId="bbs040readKbn_01" property="bbs040readKbn" value="0" />
              <label for="bbs040readKbn_01">
                <gsmsg:write key="cmn.without.specifying" />
              </label>
              <html:radio styleClass="ml10" name="bbs040Form" styleId="bbs040readKbn_02" property="bbs040readKbn" value="1" />
              <label for="bbs040readKbn_02">
                <gsmsg:write key="cmn.read.yet" />
              </label>
              <html:radio styleClass="ml10" name="bbs040Form" styleId="bbs040readKbn_03" property="bbs040readKbn" value="2" />
              <label for="bbs040readKbn_03">
                <gsmsg:write key="cmn.read.already" />
              </label>
            </span>
          </td>
        </tr>

        <tr>
         <th class="w20 txt_c">
            <gsmsg:write key="cmn.status" />
          </th>
          </td>
          <td>
            <span class="verAlignMid">
              <html:checkbox name="bbs040Form" styleId="bbs040publicStatus_01" property="bbs040publicStatusOngoing" value="1" />
              <label for="bbs040publicStatus_01">
                <gsmsg:write key="bbs.bbs040.3" />
              </label>
              <html:checkbox styleClass="ml10" name="bbs040Form" styleId="bbs040publicStatus_02" property="bbs040publicStatusScheduled" value="1" />
              <label for="bbs040publicStatus_02">
                <gsmsg:write key="bbs.bbs010.4" />
              </label>
              <html:checkbox styleClass="ml10" name="bbs040Form" styleId="bbs040publicStatus_03" property="bbs040publicStatusOver" value="1" />
              <label for="bbs040publicStatus_03">
                <gsmsg:write key="bbs.bbs040.4" />
              </label>
            </span>
          </td>
        </tr>

        <tr>
         <th class="w20 txt_c">
            <gsmsg:write key="cmn.posted" />
            </span>
          </th>
          <td>

            <span class="verAlignMid">
              <html:checkbox name="bbs040Form" styleId="bbs040dateNoKbn" property="bbs040dateNoKbn" value="1" onclick="bbs040DateKbn();" />
              <label for="bbs040dateNoKbn">
                <gsmsg:write key="cmn.without.specifying" />
              </label>
              <html:text name="bbs040Form" property="bbs040fromDate" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart mr5"></span>
              ～
              <html:text name="bbs040Form" property="bbs040toDate" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_toDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            </span>
          </td>
        </tr>
      </table>

      <div class="txt_c">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="setParams();buttonPush('dtlSearch');">
          <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_classicImg-display">
          <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_originalImg-display">
          <gsmsg:write key="cmn.search" />
        </button>
      </div>
    </div>
  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>
