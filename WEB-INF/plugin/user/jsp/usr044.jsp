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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="../common/js/jquery-1.5.2.min.js?500"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.label.settings" /> </title>
<script src="../user/js/usr044.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>

<html:form action="/user/usr044">
<input type="hidden" name="CMD" value="">
<html:hidden property="usr043EditSid" />
<html:hidden property="usr044ProcMode" />
<html:hidden property="labelEditSid" />


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

<logic:notEmpty name="usr044Form" property="usr040labSid">
  <logic:iterate id="labSidArray" name="usr044Form" property="usr040labSid" indexId="idx">
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

<logic:notEmpty name="usr044Form" property="usr040labSidSave">
  <logic:iterate id="labSidArraySave" name="usr044Form" property="usr040labSidSave" indexId="idx">
    <input type="hidden" name="usr040labSidSave" value="<bean:write name="labSidArraySave" />">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="usr040SearchFlg" />
<html:hidden property="usr040DspFlg" />

<html:hidden property="usr040CategorySetInitFlg" />
<logic:notEmpty name="usr044Form" property="usr040CategoryOpenFlg">
<logic:iterate id="openFlg" name="usr044Form" property="usr040CategoryOpenFlg">
  <bean:define id="flg" name="openFlg" type="java.lang.String" />
  <html:hidden property="usr040CategoryOpenFlg" value="<%= flg %>" />
</logic:iterate>
</logic:notEmpty>

<html:hidden property="usr040GrpSearchGId"/>
<html:hidden property="usr040GrpSearchGName"/>
<html:hidden property="usr040GrpSearchGIdSave"/>
<html:hidden property="usr040GrpSearchGNameSave"/>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<logic:notEmpty name="usr044Form" property="labelList" scope="request">
  <logic:iterate id="sort" name="usr044Form" property="labelList" scope="request">
    <input type="hidden" name="usr044KeyList" value="<bean:write name="sort" property="lauValue" />">
  </logic:iterate>
</logic:notEmpty>



<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../user/images/classic/header_user.png" alt="<gsmsg:write key="cmn.shain.info" />">
      <img class="header_pluginImg" src="../user/images/original/header_user.png" alt="<gsmsg:write key="cmn.shain.info" />">
    </li>
    <li><gsmsg:write key="cmn.shain.info" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.label.settings" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonPush('addLabel');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('usr043back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">

  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.category" />
      </th>
      <td class="w80">
        <bean:write name="usr044Form" property="usr044CatName" />
      </td>
    </tr>
  </table>

  <div class="txt_l mt10">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" name="btn_upper" onClick="return buttonPush('usr044up');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" name="btn_downer" onClick="return buttonPush('usr044down');">
      <gsmsg:write key="cmn.down" />
    </button>
    <span class="fs_13">
      <gsmsg:write key="cmn.edit.label.click.name" />
    </span>
  </div>

  <table class="table-top">
    <tr>
      <th class="w5">
      </th>
      <th class="w35">
        <gsmsg:write key="wml.74" />
      </th>
      <th class="w65">
        <gsmsg:write key="cmn.memo" />
      </th>
    </tr>
  <logic:notEmpty name="usr044Form" property="labelList">
  <logic:iterate id="labMdl" name="usr044Form" property="labelList" indexId="idx">
    <bean:define id="lauValue" name="labMdl" property="lauValue" />
    <tr class="js_listHover cursor_p" id="<bean:write name="labMdl" property="labSid" />">
      <td class="txt_c js_tableTopCheck">
        <html:radio property="usr044SortRadio" value="<%= String.valueOf(lauValue) %>" />
      </td>
      <td class="js_listClick">
        <bean:write name="labMdl" property="labName" />
      </td>
      <td class="js_listClick">
        <bean:write name="labMdl" property="labBiko" filter="false" />
      </td>
    </tr>
    </logic:iterate>
  </logic:notEmpty>
  </table>

  <div class="footerBtn_block">
   <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonPush('addLabel');">
     <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
     <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
     <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('usr043back');">
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