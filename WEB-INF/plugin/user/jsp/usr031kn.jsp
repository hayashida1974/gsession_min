<%@page import="jp.groupsession.v2.usr.usr031kn.Usr031knForm"%>
<%@ page import="java.util.Calendar" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

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
<title>GROUPSESSION <gsmsg:write key="main.man002.24" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../user/js/usr031kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="dspChangeUsrUko()">

<html:form action="/user/usr031kn">

<input type="hidden" name="CMD" value="">
<bean:define id="thisForm" name="usr031knForm" scope="request" type="Usr031knForm"/>
<html:hidden property="processMode" />
<html:hidden property="usr030cmdMode" />
<html:hidden property="usr030SearchFlg" />

<html:hidden property="selectgsid" />
<html:hidden property="usr030userId" />
<html:hidden property="usr030usrUkoFlg" />
<html:hidden property="usr030shainno" />
<html:hidden property="usr030sei" />
<html:hidden property="usr030mei" />
<html:hidden property="usr030seikn" />
<html:hidden property="usr030meikn" />
<html:hidden property="usr030agefrom" />
<html:hidden property="usr030ageto" />
<html:hidden property="usr030yakushoku" />
<html:hidden property="usr030mail" />
<html:hidden property="usr030tdfkCd" />
<html:hidden property="usr030seibetu" />
<html:hidden property="usr030entranceYearFr" />
<html:hidden property="usr030entranceMonthFr" />
<html:hidden property="usr030entranceDayFr" />
<html:hidden property="usr030entranceYearTo" />
<html:hidden property="usr030entranceMonthTo" />
<html:hidden property="usr030entranceDayTo" />
<html:hidden property="selectgsidSave" />
<html:hidden property="usr030userIdSave" />
<html:hidden property="usr030usrUkoFlgSave" />
<html:hidden property="usr030shainnoSave" />
<html:hidden property="usr030seiSave" />
<html:hidden property="usr030meiSave" />
<html:hidden property="usr030seiknSave" />
<html:hidden property="usr030meiknSave" />
<html:hidden property="usr030agefromSave" />
<html:hidden property="usr030agetoSave" />
<html:hidden property="usr030yakushokuSave" />
<html:hidden property="usr030mailSave" />
<html:hidden property="usr030tdfkCdSave" />
<html:hidden property="usr030seibetuSave" />
<html:hidden property="usr030entranceYearFrSave" />
<html:hidden property="usr030entranceMonthFrSave" />
<html:hidden property="usr030entranceDayFrSave" />
<html:hidden property="usr030entranceYearToSave" />
<html:hidden property="usr030entranceMonthToSave" />
<html:hidden property="usr030entranceDayToSave" />

<html:hidden property="usr031userid" />
<html:hidden property="usr031password" />
<html:hidden property="usr031passwordkn" />
<html:hidden property="usr031sei" />
<html:hidden property="usr031mei" />
<html:hidden property="usr031seikn" />
<html:hidden property="usr031meikn" />
<html:hidden property="usr031shainno" />
<html:hidden property="usr031yakushoku" />
<html:hidden property="usr031seibetu" />
<html:hidden property="usr031sortkey1" />
<html:hidden property="usr031sortkey2" />
<html:hidden property="usr031bikou" />
<html:hidden property="usr031bikouHtml" />
<html:hidden property="usr031defgroup" />
<html:hidden property="usr031birthYear" />
<html:hidden property="usr031birthMonth" />
<html:hidden property="usr031birthDay" />
<html:hidden property="usr031entranceYear" />
<html:hidden property="usr031entranceMonth" />
<html:hidden property="usr031entranceDay" />
<html:hidden property="usr031mail1" />
<html:hidden property="usr031mailCmt1" />
<html:hidden property="usr031mail2" />
<html:hidden property="usr031mailCmt2" />
<html:hidden property="usr031mail3" />
<html:hidden property="usr031mailCmt3" />
<html:hidden property="usr031tel1" />
<html:hidden property="usr031telNai1" />
<html:hidden property="usr031telCmt1" />
<html:hidden property="usr031tel2" />
<html:hidden property="usr031telNai2" />
<html:hidden property="usr031telCmt2" />
<html:hidden property="usr031tel3" />
<html:hidden property="usr031telNai3" />
<html:hidden property="usr031telCmt3" />
<html:hidden property="usr031fax1" />
<html:hidden property="usr031faxCmt1" />
<html:hidden property="usr031fax2" />
<html:hidden property="usr031faxCmt2" />
<html:hidden property="usr031fax3" />
<html:hidden property="usr031faxCmt3" />
<html:hidden property="usr031post1" />
<html:hidden property="usr031post2" />
<html:hidden property="usr031tdfkCd" />
<html:hidden property="usr031address1" />
<html:hidden property="usr031address2" />
<html:hidden property="usr031syozoku" />
<html:hidden property="usr031initFlg" />
<html:hidden property="usr031BinSid" />
<html:hidden property="usr031ImageName" />
<html:hidden property="usr031ImageSaveName" />
<html:hidden property="usr031UsiPicgKf" />
<html:hidden property="usr031UsiBdateKf" />
<html:hidden property="usr031UsiMail1Kf" />
<html:hidden property="usr031UsiMail2Kf" />
<html:hidden property="usr031UsiMail3Kf" />
<html:hidden property="usr031UsiZipKf" />
<html:hidden property="usr031UsiTdfKf" />
<html:hidden property="usr031UsiAddr1Kf" />
<html:hidden property="usr031UsiAddr2Kf" />
<html:hidden property="usr031UsiTel1Kf" />
<html:hidden property="usr031UsiTel2Kf" />
<html:hidden property="usr031UsiTel3Kf" />
<html:hidden property="usr031UsiFax1Kf" />
<html:hidden property="usr031UsiFax2Kf" />
<html:hidden property="usr031UsiFax3Kf" />
<html:hidden property="usr030SearchKana" />
<html:hidden property="usr030selectuser" />
<html:hidden property="selectgroup" />
<html:hidden property="adminFlg" />
<html:hidden property="usr031UsiMblUseKbn" />

<html:hidden property="usr031Digit" />
<html:hidden property="usr031CoeKbn" />
<html:hidden property="usr031UppercaseKbn" />
<html:hidden property="usr031PswdKbn" />
<html:hidden property="usr031UidPswdKbn" />
<html:hidden property="usr031warnUserLimit" />

<html:hidden property="usr031UsrUkoFlg" />

<logic:equal name="usr031knForm" property="processMode" value="edit">
<input type="hidden" name="helpPrm" value="add">
</logic:equal>
<logic:notEqual name="usr031knForm" property="processMode" value="edit">
<input type="hidden" name="helpPrm" value="<bean:write name="usr031knForm" property="processMode" />">
</logic:notEqual>

<html:hidden property="usr031NumCont" />
<html:hidden property="usr031NumAutAdd" />
<html:hidden property="usr031CmuUid1" />
<html:hidden property="usr031CmuUid2" />
<html:hidden property="usr031CmuUid3" />
<html:hidden property="usr031delPluralKbn" />

<logic:notEmpty name="usr031knForm" property="usr030selectusers" scope="request">
<logic:iterate id="users" name="usr031knForm" property="usr030selectusers" indexId="idx" scope="request">
  <bean:define id="userSid" name="users" type="java.lang.String" />
  <html:hidden property="usr030selectusers" value="<%= userSid %>" />
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="usr031knForm" property="usrLabel">
<logic:iterate id="label" name="usr031knForm" property="usrLabel">
  <input type="hidden" name="usrLabel" value="<bean:write name="label" />">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w90 mrl_auto">
  <ul>
    <bean:define id="pageTitle"><gsmsg:write key="cmn.admin.setting" /></bean:define>
    <logic:notEqual name="usr031knForm" property="processMode" value="kojn_edit">
      <li>
        <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
      </li>
      <bean:define id="pageTitle"><gsmsg:write key="cmn.admin.setting" /></bean:define>
    </logic:notEqual>
    <logic:equal name="usr031knForm" property="processMode" value="kojn_edit">
      <li>
        <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
      </li>
      <bean:define id="pageTitle"><gsmsg:write key="cmn.preferences2" /></bean:define>
    </logic:equal>
    <li><bean:write name="pageTitle" /></li>

    <li class="pageTitle_subFont">
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="add"><span class="pageTitle_subFont-plugin"><gsmsg:write key="user.src.60" /></span><gsmsg:write key="user.usr031kn.1" /></logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="edit"><span class="pageTitle_subFont-plugin"><gsmsg:write key="user.src.60" /></span><gsmsg:write key="user.usr031kn.2" /></logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="kojn_edit"><span class="pageTitle_subFont-plugin"><gsmsg:write key="user.src.60" /></span><gsmsg:write key="user.usr031kn.3" /></logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="del"><span class="pageTitle_subFont-plugin"><gsmsg:write key="user.src.60" /></span><gsmsg:write key="user.usr031kn.4" /></logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="yuko"><span class="pageTitle_subFont-plugin"><gsmsg:write key="user.src.60" /></span><gsmsg:write key="user.usr031kn.8" /></logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="muko"><span class="pageTitle_subFont-plugin"><gsmsg:write key="user.src.60" /></span><gsmsg:write key="user.usr031kn.9" /></logic:equal>
    </li>

    <li>
      <div>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="add">
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
      </logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="edit">
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('edit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
      </logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="kojn_edit">
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('edit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
      </logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="del">
        <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="return buttonPush('usr031kn_del');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="yuko">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('usr031kn_yuko');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
      </logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="muko">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('usr031kn_muko');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('Usr031kn_Back');">
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
  <html:errors/>
</logic:messagesPresent>
</div>

<div class="txt_l">
  <logic:equal name="usr031knForm" property="processMode" scope="request" value="add"><gsmsg:write key="user.usr011kn.4" /></logic:equal>
  <logic:equal name="usr031knForm" property="processMode" scope="request" value="edit"><gsmsg:write key="user.usr031kn.6" /></logic:equal>
  <logic:equal name="usr031knForm" property="processMode" scope="request" value="kojn_edit"><gsmsg:write key="user.usr031kn.6" /></logic:equal>
  <logic:equal name="usr031knForm" property="processMode" scope="request" value="del">
    <logic:equal name="usr031knForm" property="usr031knDelSelfFlg" value="false">
      <gsmsg:write key="user.usr031kn.7" />
    </logic:equal>
    <logic:equal name="usr031knForm" property="usr031knDelSelfFlg" value="true">
      <gsmsg:write key="user.usr031kn.7" />
      <div class="cl_fontWarn fw_b">
        <gsmsg:write key="user.usr031kn.13" /><br>
        &nbsp;&nbsp;&nbsp;<gsmsg:write key="user.usr031kn.14" />
      </div>
    </logic:equal>
  </logic:equal>
  <logic:equal name="usr031knForm" property="processMode" scope="request" value="yuko"><gsmsg:write key="user.usr031kn.10" /></logic:equal>
  <logic:equal name="usr031knForm" property="processMode" scope="request" value="muko">
    <gsmsg:write key="user.usr031kn.11" /><br />
    <gsmsg:write key="user.usr031kn.12" />
  </logic:equal>
</div>

  <logic:equal name="usr031knForm" property="usr031delPluralKbn" value="0">
  <table class="table-left" id="usr031FormTable">
  <logic:equal name="usr031knForm" property="adminFlg" value="true">
  <!-- ユーザID -->
    <tr>
      <th colspan="2" class="w25">
        <gsmsg:write key="cmn.user.id" />
      </th>
      <td class="w75 border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031userid" />
        <logic:notEqual name="usr031knForm" property="processMode" value="kojn_edit">
        <logic:equal name="usr031knForm" property="usr031UsrUkoFlg" value="1">
          <br />
          <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="user.usr031kn.12" /></span>
        </logic:equal>
        </logic:notEqual>
      </td>
      <td class="ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsrUkoFlg" value="1">
          <span class="cl_fontWarn no_w"><gsmsg:write key="cmn.status" />:<gsmsg:write key="user.usr031.21" /></span>
        </logic:equal>
      </td>
    </tr>
  <!--  パスワード -->
  <logic:equal name="usr031knForm" property="changePassword" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CHANGEPASSWORD_PARMIT) %>">
    <tr>
      <th colspan="2">
        <gsmsg:write key="user.117" />
      </th>
      <td class="ukoCheck" colspan="2">
        <bean:write name="usr031knForm" property="passworddamy" />
      </td>
    </tr>
  </logic:equal>
  <!-- ワンタイムパスワード通知先メールアドレス -->
  <logic:equal name="usr031knForm" property="changeOtpToAddress" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USE) %>">
    <tr>
      <th colspan="2">
        <gsmsg:write key="user.usr031.22" />
      </th>
      <td class="ukoCheck" colspan="2">
        <html:hidden name="usr031knForm" property="usr031OtpToAddress" />
        <bean:write name="usr031knForm" property="usr031OtpToAddress" />
      </td>
    </tr>
  </logic:equal>
  </logic:equal>
  <!-- 職員番号 -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.employee.staff.number" />
      </th>
      <td class="ukoCheck" colspan="2">
        <bean:write name="usr031knForm" property="usr031shainno" />
      </td>
    </tr>
  <!-- 写真 -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.photo" />
      </th>
      <td class="ukoCheck" colspan="2">
        <logic:equal name="usr031knForm" property="usr031ImageName" value="">
          <img class="classic-display wp130 hp150" src="../common/images/classic/icon_photo.gif" name="pitctImage" alt="<gsmsg:write key="cmn.photo" />">
          <img class="original-display wp130 hp150" src="../common/images/original/photo.png" name="pitctImage" alt="<gsmsg:write key="cmn.photo" />">
        </logic:equal>
        <logic:notEqual name="usr031knForm" property="usr031ImageName" value="">
          <img src="../user/usr031kn.do?CMD=getImageFile&" name="pitctImage" alt="<gsmsg:write key="cmn.photo" />" onload="initImageView130('pitctImage');" class="bor1 borC_light">
        </logic:notEqual>
        <logic:equal name="usr031knForm" property="usr031UsiPicgKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiPicgKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
  <!--  氏名 -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.name" />
      </th>
      <td class="ukoCheck" colspan="2">
        <bean:write name="usr031knForm" property="usr031sei" />&nbsp;&nbsp;<bean:write name="usr031knForm" property="usr031mei" />
      </td>
    </tr>
  <!--  氏名かな -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="user.119" />
      </th>
      <td class="ukoCheck" colspan="2">
        <bean:write name="usr031knForm" property="usr031seikn" />&nbsp;&nbsp;<bean:write name="usr031knForm" property="usr031meikn" />
      </td>
    </tr>
  <!-- 所属 -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.affiliation" />
      </th>
      <td class="ukoCheck" colspan="2">
        <bean:write name="usr031knForm" property="usr031syozoku" />
      </td>
    </tr>
  <!-- 役職 -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.post" />
      </th>
      <td class="ukoCheck" colspan="2">
        <bean:write name="usr031knForm" property="usr031knposName" />
      </td>
    </tr>
  <!-- 性別 -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="user.123" />
      </th>
      <td class="ukoCheck" colspan="2">
        <bean:write name="usr031knForm" property="usr031seibetuName" />
      </td>
    </tr>
  <!-- 入社年月日 -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="user.122" />
      </th>
      <td class="ukoCheck" colspan="2">
        <logic:notEmpty name="usr031knForm" property="usr031entranceYear" scope="request">
        <bean:define id="yr" type="java.lang.String" ><gsmsg:write key="cmn.year" arg0="<%= thisForm.getUsr031entranceYear() %>" /></bean:define>
        <bean:write name="yr"  />&nbsp;
        <bean:write name="usr031knForm" property="usr031entranceMonth" /><gsmsg:write key="cmn.month" />&nbsp;
        <bean:write name="usr031knForm" property="usr031entranceDay" /><gsmsg:write key="cmn.day" />
        </logic:notEmpty>
      </td>
    </tr>
  <!-- 生年月日 -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="user.120" />
      </th>
      <td class="border_right_none ukoCheck">
        <logic:notEmpty name="usr031knForm" property="usr031birthYear" scope="request">
        <bean:define id="yr" type="java.lang.String" ><gsmsg:write key="cmn.year" arg0="<%= thisForm.getUsr031birthYear() %>" /></bean:define>
        <bean:write name="yr"  />&nbsp;
        <bean:write name="usr031knForm" property="usr031birthMonth" /><gsmsg:write key="cmn.month" />&nbsp;
        <bean:write name="usr031knForm" property="usr031birthDay" /><gsmsg:write key="cmn.day" />
        </logic:notEmpty>
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiBdateKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiBdateKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
  <!-- メールアドレス -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.mailaddress1" />
      </th>
      <td class="border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031knMail1" filter="false" />
        <logic:notEmpty name="usr031knForm" property="usr031mailCmt1">
          &nbsp;&nbsp;<gsmsg:write key="cmn.comment" />：&nbsp;<bean:write name="usr031knForm" property="usr031mailCmt1" />
        </logic:notEmpty>
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiMail1Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiMail1Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.mailaddress2" />
      </th>
      <td class="border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031knMail2" filter="false" />
        <logic:notEmpty name="usr031knForm" property="usr031mailCmt2">
        <gsmsg:write key="cmn.comment" />：&nbsp;<bean:write name="usr031knForm" property="usr031mailCmt2" />
        </logic:notEmpty>
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiMail2Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiMail2Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.mailaddress3" />
      </th>
      <td class="border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031knMail3" filter="false" />
        <logic:notEmpty name="usr031knForm" property="usr031mailCmt3">
        &nbsp;&nbsp;<gsmsg:write key="cmn.comment" />：&nbsp;<bean:write name="usr031knForm" property="usr031mailCmt3" />
        </logic:notEmpty>
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiMail3Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiMail3Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
  <!-- 郵便番号 -->
    <tr>
      <th rowspan="4">
        <gsmsg:write key="cmn.address" />
      </th>
      <th>
        <gsmsg:write key="cmn.postalcode" />
      </th>
      <td class="border_right_none ukoCheck">
        <logic:notEmpty name="usr031knForm" property="usr031post1" scope="request">
        <bean:write name="usr031knForm" property="usr031post1" />-<bean:write name="usr031knForm" property="usr031post2" />
        </logic:notEmpty>
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiZipKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiZipKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
  <!-- 都道府県 -->
    <tr>
      <th>
        <gsmsg:write key="cmn.prefectures" />
      </th>
      <td class="border_right_none ukoCheck">
        <logic:notEmpty name="usr031knForm" property="usr031kntdfkName" scope="request">
          <bean:write name="usr031knForm" property="usr031kntdfkName" />
        </logic:notEmpty>
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiTdfKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiTdfKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
  <!-- 住所1 -->
    <tr>
      <th>
        <gsmsg:write key="cmn.address" />１
      </th>
      <td class="border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031address1" />
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiAddr1Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiAddr1Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>

  <!-- 住所2 -->
    <tr>
      <th>
        <gsmsg:write key="cmn.address" />２
      </th>
      <td class="border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031address2" />
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiAddr2Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiAddr2Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
  <!-- 電話番号 -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.tel1" />
      </th>
      <td class="border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031tel1" />
        <logic:notEmpty name="usr031knForm" property="usr031telNai1">
          &nbsp;&nbsp;<gsmsg:write key="user.136" />：&nbsp;<bean:write name="usr031knForm" property="usr031telNai1" />
        </logic:notEmpty>
        <logic:notEmpty name="usr031knForm" property="usr031telCmt1">
         &nbsp;&nbsp;<gsmsg:write key="cmn.comment" />：&nbsp;<bean:write name="usr031knForm" property="usr031telCmt1" />
        </logic:notEmpty>
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiTel1Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiTel1Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.tel2" />
      </th>
      <td class="border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031tel2" />
                <logic:notEmpty name="usr031knForm" property="usr031telNai2">
          &nbsp;&nbsp;<gsmsg:write key="user.136" />：&nbsp;<bean:write name="usr031knForm" property="usr031telNai2" />
        </logic:notEmpty>
        <logic:notEmpty name="usr031knForm" property="usr031telCmt2">
          &nbsp;&nbsp;<gsmsg:write key="cmn.comment" />：&nbsp;<bean:write name="usr031knForm" property="usr031telCmt2" />
        </logic:notEmpty>
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiTel2Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiTel2Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>

    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.tel3" />
      </th>
      <td class="border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031tel3" />
        <logic:notEmpty name="usr031knForm" property="usr031telNai3">
          &nbsp;&nbsp;<gsmsg:write key="user.136" />：&nbsp;<bean:write name="usr031knForm" property="usr031telNai3" />
        </logic:notEmpty>
        <logic:notEmpty name="usr031knForm" property="usr031telCmt3">
          &nbsp;&nbsp;<gsmsg:write key="cmn.comment" />：&nbsp;<bean:write name="usr031knForm" property="usr031telCmt3" />
        </logic:notEmpty>
      </td>
      <td class="ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiTel3Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiTel3Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>

  <!-- FAX -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="user.143" />
      </th>
      <td class="border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031fax1" />
        <logic:notEmpty name="usr031knForm" property="usr031faxCmt1">
          &nbsp;&nbsp;<gsmsg:write key="cmn.comment" />：&nbsp;<bean:write name="usr031knForm" property="usr031faxCmt1" />
        </logic:notEmpty>
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiFax1Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiFax1Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.cmn100.2" />
      </th>
      <td class="border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031fax2" />
        <logic:notEmpty name="usr031knForm" property="usr031faxCmt2">
          &nbsp;&nbsp;<gsmsg:write key="cmn.comment" />：&nbsp;<bean:write name="usr031knForm" property="usr031faxCmt2" />
        </logic:notEmpty>
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiFax2Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiFax2Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.cmn100.3" />
      </th>
      <td class="border_right_none ukoCheck">
        <bean:write name="usr031knForm" property="usr031fax3" />
        <logic:notEmpty name="usr031knForm" property="usr031faxCmt3">
        &nbsp;&nbsp;<gsmsg:write key="cmn.comment" />：&nbsp;<bean:write name="usr031knForm" property="usr031faxCmt3" />
        </logic:notEmpty>
      </td>
      <td class="no_w ukoCheck">
        <logic:equal name="usr031knForm" property="usr031UsiFax3Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.publish" /></span></logic:equal>
        <logic:equal name="usr031knForm" property="usr031UsiFax3Kf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>"><gsmsg:write key="cmn.not.publish" /></logic:equal>
      </td>
    </tr>
  <logic:equal name="usr031knForm" property="adminFlg" value="true">
  <logic:equal name="usr031knForm" property="usr031UsiMblUse" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE) %>">
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.mobile.use" />
      </th>
      <td class="ukoCheck" colspan="2">
  <!-- モバイル使用可否 -->
        <div>
          <logic:equal name="usr031knForm" property="usr031UsiMblUseKbn" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.MBL_USE_OK) %>"><gsmsg:write key="cmn.accepted" /></logic:equal>
          <logic:equal name="usr031knForm" property="usr031UsiMblUseKbn" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.MBL_USE_NG) %>"><gsmsg:write key="cmn.not" /></logic:equal>
        </div>

        <logic:equal name="usr031knForm" property="usr031UsiMblUseKbn" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.MBL_USE_OK) %>">
        <logic:equal name="usr031knForm" property="usr031NumCont" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.UID_CONTROL) %>">
        <div>
          <gsmsg:write key="cmn.login.control.identification.number" />
        </div>

        <logic:equal name="usr031knForm" property="usr031NumAutAdd" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.UID_AUTO_REG_OK) %>">
          <div class="ml10">
            <gsmsg:write key="user.99" />
          </div>
        </logic:equal>
        <div class="ml35 mt5">
          <gsmsg:write key="user.105" />：&nbsp;<bean:write name="usr031knForm" property="usr031CmuUid1" />
        </div>
        <div class="ml35">
          <gsmsg:write key="user.106" />：&nbsp;<bean:write name="usr031knForm" property="usr031CmuUid2" />
        </div>
        <div class="ml35">
          <gsmsg:write key="user.107" />：&nbsp;<bean:write name="usr031knForm" property="usr031CmuUid3" />
        </div>
        </logic:equal>
        </logic:equal>

      </td>
    </tr>
  </logic:equal>
  </logic:equal>
  <!-- ラベル -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.label" />
      </th>
      <td class="ukoCheck" colspan="2">
      <logic:notEmpty name="usr031knForm" property="selectLabelList">
      <table class="table-left w40">
      <logic:iterate id="labelData" name="usr031knForm" property="selectLabelList" indexId="idx">
      <tr>
      <td><bean:write name="labelData" property="labName" /></td>
      </tr>
      </logic:iterate>
      </table>
      </logic:notEmpty>
      </td>
    </tr>
  <!-- ソートキー1,2-->
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.sortkey" />
      </th>
      <td class="ukoCheck" colspan="2">
        <logic:notEqual name="usr031knForm" property="usr031sortkey1" value="">
          1：<bean:write name="usr031knForm" property="usr031sortkey1" />
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </logic:notEqual>
        <logic:notEqual name="usr031knForm" property="usr031sortkey2" value="">
          2：<bean:write name="usr031knForm" property="usr031sortkey2" />
        </logic:notEqual>
      </td>
    </tr>
  <!-- 備考 -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="ukoCheck" colspan="2">
        <bean:write name="usr031knForm" property="usr031bikouHtml" filter="false" />
      </td>
    </tr>
    <logic:equal name="usr031knForm" property="adminFlg" value="true">
    <tr>
      <td colspan="4" class="bgC_lightGray fw_b ukoCheck">
        <gsmsg:write key="user.86" />
      </td>
    </tr>
  <!--  グループ設定 -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="cmn.affiliation.group" />
      </th>
      <td class="ukoCheck" colspan="2">
        <logic:notEmpty name="usr031knForm" property="usr031knSltgps" scope="request">
        <logic:iterate id="sgrp" name="usr031knForm" property="usr031knSltgps" scope="request">
          <bean:write name="sgrp" property="grpName" /><br>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  <!-- デフォルトグループ -->
    <tr>
      <th colspan="2">
        <gsmsg:write key="user.35" />
      </th>
      <td class="ukoCheck" colspan="2">
        <logic:notEmpty name="usr031knForm" property="usr031knDefgp">
        <bean:define id="defgrp" name="usr031knForm" property="usr031knDefgp" />
         <bean:write name="defgrp" property="grpName" />
        </logic:notEmpty>
      </td>
    </tr>
    </logic:equal>
  </table>
</logic:equal>

<logic:equal name="usr031knForm" property="usr031delPluralKbn" value="1">
  <table class="table-top">
  <logic:notEmpty name="usr031knForm" property="usr031delUsrList" >
    <tr>
    <th class="w25">
      <gsmsg:write key="cmn.employee.staff.number" />
    </td>
    <th class="w75">
      <gsmsg:write key="cmn.name" />
    </th>
    </tr>
    <logic:iterate id="uinfMdl" name="usr031knForm" property="usr031delUsrList" >
    <tr>
    <!-- ユーザID -->
    <td class="ukoCheck">
      <bean:write name="uinfMdl" property="usiSyainNo" />
    </td>
    <td class="ukoCheck">
      <bean:write name="uinfMdl" property="usiSei" />&nbsp;<bean:write name="uinfMdl" property="usiMei" />
    </td>
    </tr>
    </logic:iterate>
  </table>
  </logic:notEmpty>
</logic:equal>

  <div class="footerBtn_block">
   <logic:equal name="usr031knForm" property="processMode"  scope="request" value="add">
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
      </logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="edit">
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('edit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
      </logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="kojn_edit">
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('edit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
      </logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="del">
        <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="return buttonPush('usr031kn_del');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="yuko">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('usr031kn_yuko');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
      </logic:equal>
      <logic:equal name="usr031knForm" property="processMode"  scope="request" value="muko">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('usr031kn_muko');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('Usr031kn_Back');">
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
