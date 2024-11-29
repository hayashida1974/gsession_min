<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.shain.info" /> <gsmsg:write key="cmn.default" /><gsmsg:write key="cmn.display.settings" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../user/js/usr082.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="lmtEnableDisable();">
<html:form action="/user/usr082">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="usr040cmdMode" />
<html:hidden property="usr040orderKey" />
<html:hidden property="usr040sortKey" />
<html:hidden property="usr040orderKey2" />
<html:hidden property="usr040sortKey2" />
<html:hidden property="usr040pageNum1" />
<html:hidden property="usr040pageNum2" />

<html:hidden property="usr040SearchKana" />
<html:hidden property="selectgsid" />

<html:hidden property="usr040Keyword" />
<html:hidden property="usr040KeyKbnShainno" />
<html:hidden property="usr040KeyKbnName" />
<html:hidden property="usr040KeyKbnNameKn" />
<html:hidden property="usr040KeyKbnMail" />
<html:hidden property="usr040KeyKbnTel" />
<html:hidden property="usr040agefrom" />
<html:hidden property="usr040ageto" />
<html:hidden property="usr040yakushoku" />
<html:hidden property="usr040tdfkCd" />
<html:hidden property="usr040entranceYearFr" />
<html:hidden property="usr040entranceMonthFr" />
<html:hidden property="usr040entranceDayFr" />
<html:hidden property="usr040entranceYearTo" />
<html:hidden property="usr040entranceMonthTo" />
<html:hidden property="usr040entranceDayTo" />
<html:hidden property="usr040seibetu" />

<logic:notEmpty name="usr082Form" property="usr040labSid">
  <logic:iterate id="labSidArray" name="usr082Form" property="usr040labSid" indexId="idx">
    <input type="hidden" name="usr040labSid" value="<bean:write name="labSidArray" />">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="selectgsidSave" />
<html:hidden property="usr040SearchKanaSave" />

<html:hidden property="usr040KeywordSave" />
<html:hidden property="usr040KeyKbnShainnoSave" />
<html:hidden property="usr040KeyKbnNameSave" />
<html:hidden property="usr040KeyKbnNameKnSave" />
<html:hidden property="usr040KeyKbnMailSave" />
<html:hidden property="usr040KeyKbnTelSave" />
<html:hidden property="usr040agefromSave" />
<html:hidden property="usr040agetoSave" />
<html:hidden property="usr040yakushokuSave" />
<html:hidden property="usr040tdfkCdSave" />
<html:hidden property="usr040entranceYearFrSave" />
<html:hidden property="usr040entranceMonthFrSave" />
<html:hidden property="usr040entranceDayFrSave" />
<html:hidden property="usr040entranceYearToSave" />
<html:hidden property="usr040entranceMonthToSave" />
<html:hidden property="usr040entranceDayToSave" />
<html:hidden property="usr040seibetuSave" />

<logic:notEmpty name="usr082Form" property="usr040labSidSave">
  <logic:iterate id="labSidArraySave" name="usr082Form" property="usr040labSidSave" indexId="idx">
    <input type="hidden" name="usr040labSidSave" value="<bean:write name="labSidArraySave" />">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="usr040SearchFlg" />
<html:hidden property="usr040DspFlg" />

<html:hidden property="usr082initKbn" />

<html:hidden property="usr040CategorySetInitFlg" />

<logic:notEmpty name="usr082Form" property="usr040CategoryOpenFlg">
<logic:iterate id="openFlg" name="usr082Form" property="usr040CategoryOpenFlg">
  <bean:define id="flg" name="openFlg" type="java.lang.String" />
  <html:hidden property="usr040CategoryOpenFlg" value="<%= flg %>" />
</logic:iterate>
</logic:notEmpty>

<html:hidden property="usr040GrpSearchGId"/>
<html:hidden property="usr040GrpSearchGName"/>
<html:hidden property="usr040GrpSearchGIdSave"/>
<html:hidden property="usr040GrpSearchGNameSave"/>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shain.info" /></span><gsmsg:write key="cmn.display.settings" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('usr082kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('usr082back');">
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
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="user.usr082.2" />
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio name="usr082Form" styleId="usr082DefoDspKbn_01" property="usr082DefoDspKbn" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.DEFO_DSP_ADM) %>" onclick="lmtEnableDisable();" /><label for="usr082DefoDspKbn_01" class="mr5"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="usr082Form" styleClass="ml10" styleId="usr082DefoDspKbn_02" property="usr082DefoDspKbn" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.DEFO_DSP_USR) %>" onclick="lmtEnableDisable();" /><label for="usr082DefoDspKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </div>
        <br>
        <span id="js_lmtinput">
          ※<gsmsg:write key="cmn.view.user.defaultset" /><br>
        </span>
        <div class="verAlignMid mt5">
          <gsmsg:write key="cmn.first.key" />：
          <!-- キー1 -->
          <html:select property="usr082AdSortKey1" styleClass="mr5" onchange="checkValue(1);">
          <html:optionsCollection name="usr082Form" property="usr082SortKeyLabel" value="value" label="label" />
          </html:select>
          <html:radio name="usr082Form" styleClass="ml10" property="usr082AdSortOrder1" styleId="usr082AdSortOrder10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="usr082AdSortOrder10" class="mr5"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="usr082Form" styleClass="ml10" property="usr082AdSortOrder1" styleId="usr082AdSortOrder11" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="usr082AdSortOrder11"><gsmsg:write key="cmn.order.desc" /></label>
        </div><br>
        <div class="verAlignMid mt5">
          <gsmsg:write key="cmn.second.key" />：
          <!-- キー2 -->
          <html:select property="usr082AdSortKey2" styleClass="mr5" onchange="checkValue(2);">
          <html:optionsCollection name="usr082Form" property="usr082SortKeyLabel" value="value" label="label" />
          </html:select>
          <html:radio name="usr082Form" styleClass="ml10" property="usr082AdSortOrder2" styleId="usr082AdSortOrder20" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="usr082AdSortOrder20" class="mr5"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="usr082Form" styleClass="ml10" property="usr082AdSortOrder2" styleId="usr082AdSortOrder21" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="usr082AdSortOrder21"><gsmsg:write key="cmn.order.desc" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.cmn100.4" /><gsmsg:write key="cmn.display.settings" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio styleId="dispEditOn" name="usr082Form" property="usr082QrDisp" value="1"/><label for="dispEditOn" class="mr5"><gsmsg:write key="cmn.display.ok" /></label>
          <html:radio styleId="dispEditOff" styleClass="ml10" name="usr082Form" property="usr082QrDisp" value="0"/><label for="dispEditOff"><gsmsg:write key="cmn.dont.show" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('usr082kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('usr082back');">
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
