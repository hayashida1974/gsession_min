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

<title>GROUPSESSION <gsmsg:write key="cmn.category.setting" /> </title>

<script src="../common/js/jquery-1.5.2.min.js?500"></script>
<script src="../user/js/usr043.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/user/usr043">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="usr043ProcMode" value="">
<input type="hidden" name="usr043EditSid" value="">

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

<logic:notEmpty name="usr043Form" property="usr040labSid">
  <logic:iterate id="labSidArray" name="usr043Form" property="usr040labSid" indexId="idx">
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

<logic:notEmpty name="usr043Form" property="usr040labSidSave">
  <logic:iterate id="labSidArraySave" name="usr043Form" property="usr040labSidSave" indexId="idx">
    <input type="hidden" name="usr040labSidSave" value="<bean:write name="labSidArraySave" />">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="usr040SearchFlg" />
<html:hidden property="usr040DspFlg" />

<html:hidden property="usr040CategorySetInitFlg" />
<logic:notEmpty name="usr043Form" property="usr040CategoryOpenFlg">
<logic:iterate id="openFlg" name="usr043Form" property="usr040CategoryOpenFlg">
  <bean:define id="flg" name="openFlg" type="java.lang.String" />
  <html:hidden property="usr040CategoryOpenFlg" value="<%= flg %>" />
</logic:iterate>
</logic:notEmpty>

<html:hidden property="usr040GrpSearchGId"/>
<html:hidden property="usr040GrpSearchGName"/>
<html:hidden property="usr040GrpSearchGIdSave"/>
<html:hidden property="usr040GrpSearchGNameSave"/>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<logic:notEmpty name="usr043Form" property="catList" scope="request">
  <logic:iterate id="sort" name="usr043Form" property="catList" scope="request">
    <input type="hidden" name="usr043KeyList" value="<bean:write name="sort" property="ulcValue" />">
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
      <gsmsg:write key="cmn.category.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonSubmit('addCategory', 'add' , '-1');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('usr041back');">
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
    <button type="button" class="baseBtn"  value="<gsmsg:write key="cmn.up" />" name="btn_upper" onClick="return buttonPush('usr043up');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" name="btn_downer" onClick="return buttonPush('usr043down');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>
    <table class="table-top">
      <tr>
        <th class="w5">&nbsp;</th>
        <th class="w25"><span class="text_tlw"><gsmsg:write key="cmn.category.name" /></span></th>
        <th class="w50"><span class="text_tlw"><gsmsg:write key="cmn.memo" /></span></th>
        <th class="w20" colspan="2"><gsmsg:write key="cmn.edit" /></th>
      </tr>
      <logic:notEmpty name="usr043Form" property="catList">
        <logic:iterate id="catMdl" name="usr043Form" property="catList" indexId="idx">
          <bean:define id="ulcValue" name="catMdl" property="ulcValue" />
          <tr>
            <!-- ラジオ -->
            <td class="txt_c js_tableTopCheck cursor_p">
              <html:radio property="usr043SortRadio" value="<%= String.valueOf(ulcValue) %>" />
            </td>
            <!-- カテゴリ名 -->
            <td>
              <bean:write name="catMdl" property="lucName" />
            </td>
            <!-- 備考 -->
            <td>
              <bean:write name="catMdl" property="lucBiko" filter="false" />
            </td>
            <td class="txt_c">
              <logic:notEqual name="catMdl" property="lucSid" value="0">
                <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.category" />" onclick="return buttonSubmit('categoryEdit', 'edit', '<bean:write name="catMdl" property="lucSid" />')">
                  <gsmsg:write key="cmn.category" />
                </button>
              </logic:notEqual>
            </td>
            <td class="txt_c">
              <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.label" />" onclick="return buttonSubmit('usr043edit','', '<bean:write name="catMdl" property="lucSid" />');">
                <gsmsg:write key="cmn.label" />
              </button>
            </td>
          </tr>
        </logic:iterate>
      </logic:notEmpty>
    </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonSubmit('addCategory', 'add' , '-1');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('usr041back');">
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
