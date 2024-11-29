<%@ page import="java.util.Calendar" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.usr.GSConstUser"%>
<!DOCTYPE html>

<%
  String infoOpen = String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN);
%>
<%
  String infoClose = String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE);
%>

<%
  String reservUser = String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_RESERV_SID);
%>

<%
  String maxLengthBiko = String.valueOf(jp.groupsession.v2.usr.GSConstUser.MAX_LENGTH_USERCOMMENT);
  String labelSetOk = String.valueOf(jp.groupsession.v2.usr.GSConstUser.LABEL_SET_OK);
%>

<% String tempMode = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_TANITU_USR031); %>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man002.24" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script language="JavaScript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../user/js/group.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../user/js/usr031.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../user/js/uidHisPopUp.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>

<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<logic:equal name="usr031Form" property="labelSetPow" value="<%=labelSetOk%>">
  <script src="../user/js/usrLabel.js?<%=GSConst.VERSION_PARAM%>"></script>
</logic:equal>

<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../user/css/user.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<logic:equal name="usr031Form" property="adminFlg" value="true">
  <logic:equal name="usr031Form" property="usr031UsiMblUse" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE)%>">
    <body onload="defaultGroup();changeUidElementStatus();showLengthId($('#inputstr')[0], <%=maxLengthBiko%>, 'inputlength');dspChangeUsrUko();" onunload="windowClose();">
  </logic:equal>
  <logic:notEqual name="usr031Form" property="usr031UsiMblUse" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE)%>">
    <body onload="defaultGroup();showLengthId($('#inputstr')[0], <%=maxLengthBiko%>, 'inputlength');dspChangeUsrUko();" onunload="windowClose();">
  </logic:notEqual>
</logic:equal>
<logic:notEqual name="usr031Form" property="adminFlg" value="true">
  <body onload="showLengthId($('#inputstr')[0], <%=maxLengthBiko%>, 'inputlength');" onunload="windowClose();">
</logic:notEqual>

<div id="FreezePane">

  <html:form action="/user/usr031">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="winname" value="posbox">
    <input type="hidden" name="childframe" value="grpFrame">

    <html:hidden property="processMode" />
    <html:hidden property="usr030SearchKana" />
    <html:hidden property="usr030selectuser" />
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
    <html:hidden property="usr030usrUkoFlgSave" />
    <html:hidden property="usr030userIdSave" />
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

    <html:hidden property="selectgroup" />
    <html:hidden property="usr031initFlg" />
    <html:hidden property="usr031BinSid" />
    <html:hidden property="usr031ImageName" />
    <html:hidden property="usr031ImageSaveName" />
    <html:hidden property="labelSetPow" />
    <html:hidden property="adminFlg" />
    <html:hidden property="usr031CoeKbn" />
    <html:hidden property="usr031UppercaseKbn" />
    <html:hidden property="usr031Digit" />
    <html:hidden property="usr031UidPswdKbn" />
    <html:hidden property="usr031warnUserLimit" />

    <span id="usr031labelArea">
      <logic:notEmpty name="usr031Form" property="usrLabel">
        <logic:iterate id="label" name="usr031Form" property="usrLabel">
          <input type="hidden" name="usrLabel" value="<bean:write name="label" />">
        </logic:iterate>
      </logic:notEmpty>
    </span>

    <logic:notEmpty name="usr031Form" property="groupList">
      <logic:iterate id="grpData" name="usr031Form" property="groupList">
        <input type="hidden" name="defGrpId" value="<bean:write name="grpData" property="groupSid" />">
        <input type="hidden" name="defGrpNm" value="<bean:write name="grpData" property="groupName" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="usr031Form" property="usr030selectusers" scope="request">
      <logic:iterate id="users" name="usr031Form" property="usr030selectusers" indexId="idx" scope="request">
        <bean:define id="userSid" name="users" type="java.lang.String" />
        <html:hidden property="usr030selectusers" value="<%=userSid%>" />
      </logic:iterate>
    </logic:notEmpty>

    <logic:equal name="usr031Form" property="processMode" value="kojn_edit">
      <input type="hidden" name="helpPrm" value="<bean:write name="usr031Form" property="processMode" />">
    </logic:equal>
    <logic:notEqual name="usr031Form" property="processMode" value="kojn_edit">
      <input type="hidden" name="helpPrm" value="add">
    </logic:notEqual>

    <input type="hidden" name="delLabel" value="">


    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w90 mrl_auto">
      <ul>
        <li>
          <bean:define id="pageTitle"><gsmsg:write key="cmn.admin.setting" /></bean:define>
          <logic:notEqual name="usr031Form" property="processMode" value="kojn_edit">
            <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
            <bean:define id="pageTitle"><gsmsg:write key="cmn.admin.setting" /></bean:define>
          </logic:notEqual>
          <logic:equal name="usr031Form" property="processMode" value="kojn_edit">
            <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
            <bean:define id="pageTitle"><gsmsg:write key="cmn.preferences2" /></bean:define>
          </logic:equal>
        </li>
        <li>
          <bean:write name="pageTitle" />
        </li>
        <li class="pageTitle_subFont">
        <gsmsg:write key="cmn.shain.info" />
          <span class="pageTitle_subFont-plugin">
            <span class="settei_ttl"></span>
            <logic:equal name="usr031Form" property="processMode" scope="request" value="add">
              <gsmsg:write key="user.usr031.3" />
            </logic:equal>
            <logic:equal name="usr031Form" property="processMode" scope="request" value="edit">
              <gsmsg:write key="user.usr031.4" />
            </logic:equal>
            <logic:equal name="usr031Form" property="processMode" scope="request" value="kojn_edit">
              <gsmsg:write key="cmn.modify.personalinfo" />
            </logic:equal>
          </span>
        </li>
        <li>
          <div>
            <logic:equal name="usr031Form" property="adminFlg" value="true">
              <logic:equal name="usr031Form" property="usr031UsiMblUse" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE)%>">
                <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="submitStyleChange();setShowGroup();getSelectGroup();return buttonPushUsr('usr031_kakunin');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                  <gsmsg:write key="cmn.ok" />
                </button>
              </logic:equal>
              <logic:notEqual name="usr031Form" property="usr031UsiMblUse" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE)%>">
                <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="setShowGroup();getSelectGroup();return buttonPushUsr('usr031_kakunin');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                  <gsmsg:write key="cmn.ok" />
                </button>
              </logic:notEqual>
            </logic:equal>
            <logic:notEqual name="usr031Form" property="adminFlg" value="true">
              <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPushUsr('usr031_kakunin');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                <gsmsg:write key="cmn.ok" />
              </button>
            </logic:notEqual>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPushUsr('Usr031_Back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>


    <div class="wrapper w90 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <logic:equal name="usr031Form" property="usr031warnUserLimit" value="1">
        <div class="txt_l mt10">
          <logic:equal name="usr031Form" property="processMode" value="edit">
            <gsmsg:write key="user.usr031.24" />
          </logic:equal>
          <logic:notEqual name="usr031Form" property="processMode" value="edit">
            <gsmsg:write key="user.usr031.23" />
          </logic:notEqual>
        </div>
      </logic:equal>

      <table class="table-left" id="usr031FormTable" >

        <logic:equal name="usr031Form" property="adminFlg" value="true">

          <!-- ユーザID-->
          <tr>
            <th class="txt_m txt_l no_w" colspan="2">
              <span>
                <gsmsg:write key="cmn.user.id" />
              </span>
              <span class="cl_fontWarn">※</span>
            </th>
            <td class="txt_m txt_l no_w border_right_none ukoCheck" colspan="1">
              <html:text property="usr031userid" styleClass="wp300" maxlength="256" />
              <br>
              <%
                String userMinIdCnt = "2";
              %>
              <span>*<gsmsg:write key="user.usr031.10" arg0="<%=userMinIdCnt%>" /></span>
              <br><span>*<gsmsg:write key="user.usr031.8" /><br />&nbsp;&nbsp;- ! # $ % & . / = @ | _ * ' + ? ^ ` { } ~</span>
              <logic:notEqual name="usr031Form" property="processMode" value="kojn_edit">
                <div id="usr031UsrUkoFlg_warn" class="display_n">
                  <span class="cl_fontWarn">※
                    <gsmsg:write key="user.usr031kn.12" />
                  </span>
                </div>
              </logic:notEqual>
            </td>
            <td class="txt_t txt_c no_w border_left_none wp200 ukoCheck">
              <logic:notEqual name="usr031Form" property="processMode" value="kojn_edit">
                <table class="table-left">
                  <tr>
                    <th class="txt_m txt_c">
                      <span>
                        <gsmsg:write key="cmn.status" />
                      </span>
                    </th>
                  </tr>
                  <tr>
                    <td class="txt_m txt_c">
                      <div>
                      <span class="verAlignMid">
                        <html:checkbox styleId="usr031UsrUkoFlg" name="usr031Form" property="usr031UsrUkoFlg" value="1" onclick="dspChangeUsrUko();" />
                        <label for="usr031UsrUkoFlg">
                          <gsmsg:write key="user.usr031.21" />
                        </label>
                        </span>
                      </div>
                    </td>
                  </tr>
                </table>
              </logic:notEqual>
              <logic:equal name="usr031Form" property="processMode" value="kojn_edit">
                <html:hidden name="usr031Form" property="usr031UsrUkoFlg" />
              </logic:equal>
            </td>
          </tr>

          <logic:equal name="usr031Form" property="changePassword" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.CHANGEPASSWORD_PARMIT)%>">
            <!-- パスワード -->
            <tr>
              <th class="txt_m txt_l no_w" colspan="2">
                <span>
                  <gsmsg:write key="user.117" />
                </span>
                <span class="cl_fontWarn">※</span>
              </th>
              <td class="txt_l txt_m ukoCheck" colspan="2">
              <div class="mb5">
                <html:password property="usr031password" styleClass="wp300" maxlength="256" />
                </div>
                <div>
                <html:password property="usr031passwordkn" styleClass="wp300" maxlength="256" />
                <span>
                  &nbsp;
                  <gsmsg:write key="user.19" />
                </span>
                </div>
                <br>
                <span>
                  <html:checkbox styleId="pswd_kbn" name="usr031Form" property="usr031PswdKbn" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.PSWD_UPDATE_ON)%>" />
                  <label for="pswd_kbn">
                    <gsmsg:write key="user.usr031.9" />
                  </label>
                </span>
                <br>
                <bean:define id="digitStr" name="usr031Form" property="usr031Digit" type="java.lang.Integer" />
                <span>*<gsmsg:write key="user.usr031.10" arg0="<%=String.valueOf(digitStr.intValue())%>" /></span>
                <br>
                <logic:equal name="usr031Form" property="usr031CoeKbn" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_COEKBN_ON_EN)%>">
                  <span>*<gsmsg:write key="user.usr031.12" /></span>
                  <br>
                </logic:equal>
                <logic:equal name="usr031Form" property="usr031CoeKbn" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_COEKBN_ON_ENS)%>">
                  <span>*<gsmsg:write key="user.usr031.19" /></span>
                  <br>
                </logic:equal>
                <logic:equal name="usr031Form" property="usr031UppercaseKbn" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UPPERCASE_ON)%>">
                  <span>*<gsmsg:write key="user.usr031.25" /></span>
                  <br>
                </logic:equal>
                <span>*<gsmsg:write key="user.usr031.11" /></span>
                <br>
                <logic:equal name="usr031Form" property="usr031UidPswdKbn" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UIDPSWDKBN_ON)%>">
                  <span>*<gsmsg:write key="user.usr031.13" /></span>
                </logic:equal>
              </td>
            </tr>
          </logic:equal>
          <logic:equal name="usr031Form" property="changeOtpToAddress" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USE)%>">
            <!-- ワンタイムパスワード通知先メールアドレス -->
            <tr>
              <th class="txt_m txt_l no_w" colspan="2">
                <span>
                  <gsmsg:write key="user.usr031.22" />
                </span>
              </th>
              <td class="txt_m txt_l ukoCheck" colspan="2">
                <html:text property="usr031OtpToAddress" maxlength="256" styleClass="input_mail" />
              </td>
            </tr>
          </logic:equal>

        </logic:equal>

        <!-- 職員番号 -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.employee.staff.number" />
            </span>
          </th>
          <td class="txt_m txt_l ukoCheck" colspan="2">
            <html:text property="usr031shainno" styleClass="wp250" maxlength="20" />
          </td>
        </tr>

        <!-- 写真 -->
        <tr>
          <th class="txt_m txt_l" class="txt_m txt_l no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.photo" />
            </span>
          </th>
          <td class="txt_l txt_m ukoCheck" colspan="2">
           <logic:equal name="usr031Form" property="usr031ImageName" value="">
             <img class="classic-display" src="../common/images/classic/icon_photo.gif" name="pitctImage" alt="<gsmsg:write key="cmn.photo" />" border="1">
             <img class="original-display" src="../common/images/original/photo.png  " name="pitctImage" alt="<gsmsg:write key="cmn.photo" />" border="1">
           </logic:equal>
           <logic:notEqual name="usr031Form" property="usr031ImageName" value="">
             <img src="../user/usr031.do?CMD=getImageFile&" name="pitctImage" alt="<gsmsg:write key="cmn.photo" />" onload="initImageView130('pitctImage');">
           </logic:notEqual>
           <span class="verAlignMid ml10">
             <html:radio styleId="usr031UsiPicgKf_0" name="usr031Form" property="usr031UsiPicgKf" value="<%=infoOpen%>" />
             <label for="usr031UsiPicgKf_0">
               <gsmsg:write key="cmn.publish" />
             </label>
             <html:radio styleClass="ml10" styleId="usr031UsiPicgKf_1" name="usr031Form" property="usr031UsiPicgKf" value="<%=infoClose%>" />
             <label for="usr031UsiPicgKf_1">
               <gsmsg:write key="cmn.not.publish" />
             </label>
           </span>

           <div class="mt5">
             (<gsmsg:write key="user.usr031.14" />)
           </div>
           <div>
           <span class="cl_fontWarn">
             <gsmsg:write key="user.usr031.20" />
           </span>
           <br>
           <logic:notEqual name="usr031Form" property="adminFlg" value="true">
             <attachmentFile:filearea
             mode="<%= tempMode %>"
             pluginId="<%=GSConstUser.PLUGIN_ID_USER %>"
             tempDirId="usr031"
             delBtn="true"
             delBtnAction="buttonPushUsr('pictDelete');"
             fileList="false" />
           </logic:notEqual>
           <logic:equal name="usr031Form" property="adminFlg" value="true">
             <attachmentFile:filearea
             mode="<%= tempMode %>"
             pluginId="<%=GSConstUser.PLUGIN_ID_USER %>"
             tempDirId="usr031"
             delBtn="true"
             delBtnAction="buttonPushUsr('pictDelete');"
             fileList="false" />
           </logic:equal>
           </div>
          </td>
        </tr>

        <!-- 氏名 -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.name" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_m txt_l no_w ukoCheck" colspan="2">
            <gsmsg:write key="cmn.lastname" /><html:text property="usr031sei" styleClass="wp150 ml5 mr10" maxlength="<%=String.valueOf(GSConstUser.MAX_LENGTH_USER_NAME_SEI)%>" />
            <gsmsg:write key="cmn.name3" /><html:text property="usr031mei" styleClass="wp150 ml5" maxlength="<%=String.valueOf(GSConstUser.MAX_LENGTH_USER_NAME_MEI)%>" />
          </td>
        </tr>

        <!-- 氏名かな -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <span>
              <gsmsg:write key="user.119" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_m txt_l no_w ukoCheck" colspan="2">
            <gsmsg:write key="cmn.lastname" /><html:text property="usr031seikn" styleClass="wp150 ml5 mr10" maxlength="<%=String.valueOf(GSConstUser.MAX_LENGTH_USER_NAME_SEI_KN)%>" />
            <gsmsg:write key="cmn.name3" /><html:text property="usr031meikn" styleClass="wp150 ml5" maxlength="<%=String.valueOf(GSConstUser.MAX_LENGTH_USER_NAME_MEI_KN)%>" />
            <br>
            *<gsmsg:write key="cmn.enter.kana.zenkaku" />
            </span>
            <br>
          </td>
        </tr>

        <!-- 所属 -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font><gsmsg:write key="cmn.affiliation" /></font>
          </th>
          <td class="txt_m txt_l no_w ukoCheck" colspan="2">
            <html:text property="usr031syozoku" styleClass="wp500" maxlength="60" />
          </td>
        </tr>

        <!-- 役職 -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.post" />
            </span>
          </th>
          <td class="txt_m txt_l ukoCheck" colspan="2">
            <div class="vereAlignMid">
              <html:select property="usr031yakushoku">
                <html:optionsCollection name="usr031Form" property="posLabelList" value="value" label="label" />
              </html:select>

              <logic:equal name="usr031Form" property="adminFlg" value="true">
                <a href="#" onClick="getSelectGroup();buttonDisabled();return openpos();">
                  <img class="classic-display" src="../common/images/classic/icon_add_2.gif" alt="<gsmsg:write key="cmn.add" />">
                  <img class="original-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                </a>
              </logic:equal>
            </div>
          </td>
        </tr>

        <!-- 性別 -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <span>
              <gsmsg:write key="user.123" />
            </span>
          </th>
          <td class="txt_m txt_l ukoCheck" colspan="2">
          <span class="verAlignMid">
            <logic:notEmpty name="usr031Form" property="seibetuLabelList">
              <bean:define id="seibetuVal" name="usr031Form" property="usr031seibetu" type="java.lang.String" />
              <logic:iterate id="seibetuMdl" name="usr031Form" property="seibetuLabelList" indexId="idxSeibetu">
                <logic:equal name="seibetuMdl" property="value" value="<%=seibetuVal%>">
                  <input type="radio" name="usr031seibetu" id="usr031seibetu_<%=idxSeibetu%>" value="<bean:write name="seibetuMdl" property="value" />" checked />
                  <label class="mr10" for="usr031seibetu_<%=idxSeibetu%>">
                    <bean:write name="seibetuMdl" property="label" />
                  </label>
                </logic:equal>
                <logic:notEqual name="seibetuMdl" property="value" value="<%=seibetuVal%>">
                  <input type="radio" name="usr031seibetu" id="usr031seibetu_<%=idxSeibetu%>" value="<bean:write name="seibetuMdl" property="value" />" />
                  <label class="mr10" for="usr031seibetu_<%=idxSeibetu%>">
                    <bean:write name="seibetuMdl" property="label" />
                  </label>
                </logic:notEqual>
              </logic:iterate>
            </logic:notEmpty>
          </span>
          </td>
        </tr>

        <!-- 入社年月日 -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font><gsmsg:write key="user.122" /></font>
          </th>
          <td class="txt_m txt_l ukoCheck" colspan="2">
            <html:text property="usr031entranceYear" styleClass="wp50" maxlength="4" />
            <span>
              <gsmsg:write key="cmn.year2" />
            </span>
            <html:text property="usr031entranceMonth" styleClass="wp50 ml5" maxlength="2" />
            <span>
              <gsmsg:write key="cmn.month" />
            </span>
            <html:text property="usr031entranceDay" styleClass="wp50 ml5" maxlength="2" />
            <span>
              <gsmsg:write key="cmn.day" />
            </span>
          </td>
        </tr>
        <!-- ヘッダ 全公開・全非公開ボタン -->
        <tr>
          <td class="txt_m txt_l no_w bgC_lightGray border_right_none" colspan="3"></td>
          <td class="txt_m txt_r no_w bgC_lightGray">
            <span>
              <a href="javascript:allPublish();">
                <gsmsg:write key="cmn.all" /><gsmsg:write key="cmn.publish" />
              </a>
              <a href="javascript:allDisPublish();">
                <gsmsg:write key="cmn.all" /><gsmsg:write key="cmn.not.publish" />
              </a>
            </span>
          </td>
        </tr>
        <!-- 生年月日 -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font><gsmsg:write key="user.120" /></font>
          </th>
          <td class="txt_m txt_l border_right_none ukoCheck">
            <html:text property="usr031birthYear" styleClass="wp50" maxlength="4" />
            <span>
              <gsmsg:write key="cmn.year2" />
            </span>
            <html:text property="usr031birthMonth" styleClass="wp50 ml5" maxlength="2" />
            <span>
              <gsmsg:write key="cmn.month" />
            </span>
            <html:text property="usr031birthDay" styleClass="wp50 ml5" maxlength="2" />
            <span>
              <gsmsg:write key="cmn.day" />
            </span>
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiBdateKf_0" name="usr031Form" property="usr031UsiBdateKf" value="<%=infoOpen%>" />
              <label for="usr031UsiBdateKf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiBdateKf_1" name="usr031Form" property="usr031UsiBdateKf" value="<%=infoClose%>" />
              <label for="usr031UsiBdateKf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>

        <!-- メールアドレス -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font><gsmsg:write key="cmn.mailaddress1" /></font>
          </th>
          <td class="txt_m txt_l border_right_none ukoCheck">
            <html:text property="usr031mail1" styleClass="wp300 mr10" maxlength="256" />
            <gsmsg:write key="cmn.comment" />：<html:text property="usr031mailCmt1" maxlength="10" styleClass="wp100 ml5" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiMail1Kf_0" name="usr031Form" property="usr031UsiMail1Kf" value="<%=infoOpen%>" />
              <label for="usr031UsiMail1Kf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiMail1Kf_1" name="usr031Form" property="usr031UsiMail1Kf" value="<%=infoClose%>" />
              <label for="usr031UsiMail1Kf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font><gsmsg:write key="cmn.mailaddress2" /></font>
          </th>
          <td class="txt_m txt_l border_right_none ukoCheck">
            <html:text property="usr031mail2" styleClass="wp300 mr10" maxlength="256" />
              <gsmsg:write key="cmn.comment" />：<html:text property="usr031mailCmt2" maxlength="10" styleClass="wp100 ml5" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiMail2Kf_0" name="usr031Form" property="usr031UsiMail2Kf" value="<%=infoOpen%>" />
              <label for="usr031UsiMail2Kf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiMail2Kf_1" name="usr031Form" property="usr031UsiMail2Kf" value="<%=infoClose%>" />
              <label for="usr031UsiMail2Kf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font><gsmsg:write key="cmn.mailaddress3" /></font>
          </th>
          <td class="txt_m txt_l border_right_none ukoCheck">
            <html:text property="usr031mail3" styleClass="wp300 mr10" maxlength="256" />
            <gsmsg:write key="cmn.comment" />：<html:text property="usr031mailCmt3" maxlength="10" styleClass="wp100 ml5" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiMail3Kf_0" name="usr031Form" property="usr031UsiMail3Kf" value="<%=infoOpen%>" />
              <label for="usr031UsiMail3Kf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiMail3Kf_1" name="usr031Form" property="usr031UsiMail3Kf" value="<%=infoClose%>" />
              <label for="usr031UsiMail3Kf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>

        <!-- 郵便番号 -->
        <tr>
          <th class="txt_m txt_l no_w" rowspan="4">
            <font><gsmsg:write key="cmn.address" /></font>
          </th>
          <th class="txt_m txt_l no_w">
            <font><gsmsg:write key="cmn.postalcode" /></font>
          </th>
          <td class="txt_m txt_l border_right_none ukoCheck">
            <html:text property="usr031post1" styleClass="wp50" maxlength="3" />
            <font>-</font>
            <html:text property="usr031post2" style="wp50" maxlength="4" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiZipKf_0" name="usr031Form" property="usr031UsiZipKf" value="<%=infoOpen%>" />
              <label for="usr031UsiZipKf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiZipKf_1" name="usr031Form" property="usr031UsiZipKf" value="<%=infoClose%>" />
              <label for="usr031UsiZipKf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>

        <!-- 都道府県 -->
        <tr>
          <th class="txt_m txt_l no_w">
            <font><gsmsg:write key="cmn.prefectures" /></font>
          </th>
          <td class="txt_m txt_l border_right_none ukoCheck">
            <html:select property="usr031tdfkCd">
              <html:optionsCollection name="usr031Form" property="tdfkLabelList" value="value" label="label" />
            </html:select>
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiTdfKf_0" name="usr031Form" property="usr031UsiTdfKf" value="<%=infoOpen%>" />
              <label for="usr031UsiTdfKf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiTdfKf_1" name="usr031Form" property="usr031UsiTdfKf" value="<%=infoClose%>" />
              <label for="usr031UsiTdfKf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>

        <!-- 住所1 -->
        <tr>
          <th class="txt_m txt_l no_w">
            <font><gsmsg:write key="cmn.address" />１</font>
          </th>
          <td class="txt_m txt_l border_right_none ukoCheck">
            <html:text property="usr031address1" styleClass="wp450" maxlength="100" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiAddr1Kf_0" name="usr031Form" property="usr031UsiAddr1Kf" value="<%=infoOpen%>" />
              <label for="usr031UsiAddr1Kf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiAddr1Kf_1" name="usr031Form" property="usr031UsiAddr1Kf" value="<%=infoClose%>" />
              <label for="usr031UsiAddr1Kf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
          </td>
        </tr>

        <!-- 住所2 -->
        <tr>
          <th class="txt_m txt_l no_w">
            <font><gsmsg:write key="cmn.address" />２</font>
          </th>
          <td class="txt_m txt_l border_right_none ukoCheck">
            <html:text property="usr031address2" styleClass="wp450" maxlength="100" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiAddr2Kf_0" name="usr031Form" property="usr031UsiAddr2Kf" value="<%=infoOpen%>" />
              <label for="usr031UsiAddr2Kf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiAddr2Kf_1" name="usr031Form" property="usr031UsiAddr2Kf" value="<%=infoClose%>" />
              <label for="usr031UsiAddr2Kf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>

        <!-- 電話番号 -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font><gsmsg:write key="cmn.tel1" /></font>
          </th>
          <td class="txt_m txt_l border_right_none no_w ukoCheck">
            <html:text property="usr031tel1" styleClass="wp150 mr10" maxlength="20" />
            <gsmsg:write key="user.136" />：<html:text property="usr031telNai1" maxlength="15" styleClass="wp100 ml5 mr10" />
            <gsmsg:write key="cmn.comment" />：<html:text property="usr031telCmt1" maxlength="10" styleClass="wp100 ml5" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiTel1Kf_0" name="usr031Form" property="usr031UsiTel1Kf" value="<%=infoOpen%>" />
              <label for="usr031UsiTel1Kf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiTel1Kf_1" name="usr031Form" property="usr031UsiTel1Kf" value="<%=infoClose%>" />
              <label for="usr031UsiTel1Kf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font><gsmsg:write key="cmn.tel2" /></font>
          </th>

          <td class="txt_m txt_l border_right_none no_w ukoCheck">
            <html:text property="usr031tel2" styleClass="wp150 mr10" maxlength="20" />
            <gsmsg:write key="user.136" />：<html:text property="usr031telNai2" maxlength="15" styleClass="wp100 ml5 mr10" />
            <gsmsg:write key="cmn.comment" />：<html:text property="usr031telCmt2" maxlength="10" styleClass="wp100 ml5" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiTel2Kf_0" name="usr031Form" property="usr031UsiTel2Kf" value="<%=infoOpen%>" />
              <label for="usr031UsiTel2Kf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiTel2Kf_1" name="usr031Form" property="usr031UsiTel2Kf" value="<%=infoClose%>" />
              <label for="usr031UsiTel2Kf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font><gsmsg:write key="cmn.tel3" /></font>
          </th>

          <td class="txt_m txt_l border_right_none no_w ukoCheck">
            <html:text property="usr031tel3" styleClass="wp150 mr10" maxlength="20" />
            <gsmsg:write key="user.136" />：<html:text property="usr031telNai3" maxlength="15" styleClass="wp100 ml5 mr10" />
            <gsmsg:write key="cmn.comment" />：<html:text property="usr031telCmt3" maxlength="10" styleClass="wp100 ml5" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiTel3Kf_0" name="usr031Form" property="usr031UsiTel3Kf" value="<%=infoOpen%>" />
              <label for="usr031UsiTel3Kf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiTel3Kf_1" name="usr031Form" property="usr031UsiTel3Kf" value="<%=infoClose%>" />
              <label for="usr031UsiTel3Kf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>

        <!-- FAX -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font><gsmsg:write key="user.143" /></font>
          </th>
          <td class="txt_m txt_l border_right_none ukoCheck">
            <html:text property="usr031fax1" styleClass="wp150 mr10" maxlength="20" />
            <gsmsg:write key="cmn.comment" />：<html:text property="usr031faxCmt1" maxlength="10" styleClass="wp150 ml5" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiFax1Kf_0" name="usr031Form" property="usr031UsiFax1Kf" value="<%=infoOpen%>" />
              <label for="usr031UsiFax1Kf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiFax1Kf_1" name="usr031Form" property="usr031UsiFax1Kf" value="<%=infoClose%>" />
              <label for="usr031UsiFax1Kf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font>ＦＡＸ２</font>
          </th>
          <td class="txt_m txt_l border_right_none ukoCheck">
            <html:text property="usr031fax2" styleClass="wp150 mr10" maxlength="20" />
            <gsmsg:write key="cmn.comment" />：<html:text property="usr031faxCmt2" maxlength="10" styleClass="wp150 ml5" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiFax2Kf_0" name="usr031Form" property="usr031UsiFax2Kf" value="<%=infoOpen%>" />
              <label for="usr031UsiFax2Kf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiFax2Kf_1" name="usr031Form" property="usr031UsiFax2Kf" value="<%=infoClose%>" />
              <label for="usr031UsiFax2Kf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font>ＦＡＸ３</font>
          </th>
          <td class="txt_m txt_l border_right_none ukoCheck">
            <html:text property="usr031fax3" styleClass="wp150 mr10" maxlength="20" />
            <gsmsg:write key="cmn.comment" />：<html:text property="usr031faxCmt3" maxlength="10" styleClass="wp150 ml5" />
          </td>
          <td class="txt_m txt_r no_w ukoCheck">
            <span class="verAlignMid">
              <html:radio styleId="usr031UsiFax3Kf_0" name="usr031Form" property="usr031UsiFax3Kf" value="<%=infoOpen%>" />
              <label for="usr031UsiFax3Kf_0">
                <gsmsg:write key="cmn.publish" />
              </label>
              <html:radio styleClass="ml10" styleId="usr031UsiFax3Kf_1" name="usr031Form" property="usr031UsiFax3Kf" value="<%=infoClose%>" />
              <label for="usr031UsiFax3Kf_1">
                <gsmsg:write key="cmn.not.publish" />
              </label>
            </span>
          </td>
        </tr>

        <logic:equal name="usr031Form" property="adminFlg" value="true">
          <logic:equal name="usr031Form" property="usr031UsiMblUse" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE)%>">

            <tr>
              <th class="txt_m txt_l no_w" colspan="2">
                <font><gsmsg:write key="cmn.mobile.use" /></font>
              </th>
              <td class="txt_m txt_l ukoCheck" colspan="2">
                <div>
                  <span class="verAlignMid">
                    <html:radio styleId="usr031UsiMblUse_0" name="usr031Form" property="usr031UsiMblUseKbn" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MBL_USE_OK)%>" onclick="return changeUidElementStatus();" />
                    <label for="usr031UsiMblUse_0">
                      <gsmsg:write key="cmn.accepted" />
                    </label>
                    <html:radio styleClass="ml10" styleId="usr031UsiMblUse_1" name="usr031Form" property="usr031UsiMblUseKbn" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MBL_USE_NG)%>" onclick="return changeUidElementStatus();" />
                    <label for="usr031UsiMblUse_1">
                      <gsmsg:write key="cmn.not" />
                    </label>
                  </span>
                </div>

                <div class="verAlignMid">
                  <html:checkbox name="usr031Form" property="usr031NumCont" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.UID_CONTROL)%>" styleId="num_seigyo" onclick="return changeUidElementStatus();" />
                  <label for="num_seigyo">
                    <gsmsg:write key="cmn.login.control.identification.number" />
                  </label>
                </div>
                <div class="cl_fontWarn js_mobileAutoPush">
                  <gsmsg:write key="main.man210.7" />
                </div>
                <div class="ml15">
                  <span class="verAlignMid">
                    <html:checkbox name="usr031Form" property="usr031NumAutAdd" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.UID_AUTO_REG_OK)%>" styleId="autoreg" onclick="return changeUidElementStatus();" />
                    <label for="autoreg">
                      <gsmsg:write key="user.99" />
                    </label>
                  </span>
                  <br>
                  <span class="cl_fontWarn">
                    <span class="cl_fontWarn ml5">*
                      <gsmsg:write key="user.100" />
                    </span>
                    <br>
                    <span class="cl_fontWarn">
                      <span class="cl_fontWarn ml5">*
                        <gsmsg:write key="user.102" />
                      </span>
                    </span>
                  </span>
                </div>
                <div class="ml35 mt5">
                  <gsmsg:write key="user.105" />：<html:text name="usr031Form" property="usr031CmuUid1" styleClass="wp300 ml5" maxlength="50" />
                  <button type="button" class="baseBtn" name="hisBtn1" id="hisBtn1" value="<gsmsg:write key="user.usr031.18" />" onclick="return openUidHisWindow(<bean:write name="usr031Form" property="usr030selectuser" />, 1);">
                    <gsmsg:write key="user.usr031.18" />
                  </button>
                </div>
                <div class="ml35 mt5">
                  <gsmsg:write key="user.106" />：<html:text name="usr031Form" property="usr031CmuUid2" styleClass="wp300 ml5" maxlength="50" />
                  <button type="button" class="baseBtn" name="hisBtn2" id="hisBtn2" value="<gsmsg:write key="user.usr031.18" />" onclick="return openUidHisWindow(<bean:write name="usr031Form" property="usr030selectuser" />, 2);">
                    <gsmsg:write key="user.usr031.18" />
                  </button>
                </div>
                <div class="ml35 mt5">
                  <gsmsg:write key="user.107" />：<html:text name="usr031Form" property="usr031CmuUid3" styleClass="wp300 ml5" maxlength="50" />
                  <button type="button" class="baseBtn" name="hisBtn3" id="hisBtn3" value="<gsmsg:write key="user.usr031.18" />" onclick="return openUidHisWindow(<bean:write name="usr031Form" property="usr030selectuser" />, 3);">
                    <gsmsg:write key="user.usr031.18" />
                  </button>
                </div>
              </td>
            </tr>

          </logic:equal>
        </logic:equal>

        <!-- ラベル -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.label" />
            </span>
          </th>
          <td class="txt_m txt_l no_w ukoCheck" colspan="2">
            <logic:equal name="usr031Form" property="labelSetPow" value="<%=labelSetOk%>">
              <table cellpadding="0" cellspacing="0">
                <tr>
                  <td class="border_none p0">
                    <input type="button" value="<gsmsg:write key="cmn.select.label" />" class="m0 baseBtn" onClick="buttonDisabled();return openlabel();">
                  </td>
                </tr>

              </table>
            </logic:equal>

            <logic:notEmpty name="usr031Form" property="selectLabelList">
              <table class="table-top w40">

                <logic:iterate id="labelData" name="usr031Form" property="selectLabelList" indexId="idx">
                  <tr>
                    <td class="w100">
                      <logic:equal name="usr031Form" property="labelSetPow" value="<%=labelSetOk%>">
                        <img class="classic-display mr5 cursor_p" src="../common/images/classic/icon_delete_2.gif" alt="<gsmsg:write key="cmn.delete" />" onclick="deleteLabel('<bean:write name="labelData" property="labSid" />');">
                        <img class="original-display mr5 cursor_p" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />" onclick="deleteLabel('<bean:write name="labelData" property="labSid" />');">
                      </logic:equal>
                      <bean:write name="labelData" property="labName" />
                    </td>
                  </tr>
                </logic:iterate>
              </table>
            </logic:notEmpty>
          </td>
        </tr>

        <!-- ソートキー1,2 -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <font><gsmsg:write key="cmn.sortkey" /></font>
          </th>
          <td class="txt_m txt_l ukoCheck" colspan="2">
            1：<html:text property="usr031sortkey1" styleClass="wp100 mr10" maxlength="10" />
            2：<html:text property="usr031sortkey2" styleClass="wp100" maxlength="10" />
          </td>
        </tr>

        <!-- 備考 -->
        <tr>
          <th class="txt_m txt_l no_w" colspan="2">
            <span>
              <gsmsg:write key="cmn.memo" />
            </span>
          </th>
          <td class="txt_m txt_l ukoCheck" colspan="2">
            <textarea class="wp550" name="usr031bikou" rows="5" wrap="hard" onkeyup="showLengthStr(value, <%=maxLengthBiko%>, 'inputlength');" id="inputstr"><bean:write name="usr031Form" property="usr031bikou" /></textarea>
            <br>
            <span class="formCounter">
              <gsmsg:write key="cmn.current.characters" />:
            </span>
            <span id="inputlength" class="formCounter">0</span>
            <span class="formCounter_max">/<%=maxLengthBiko%><gsmsg:write key="cmn.character" />
            </span>
          </td>
        </tr>

        <logic:equal name="usr031Form" property="adminFlg" value="true">
          <!-- グループ設定ヘッダ -->
          <tr>
            <td class="txt_m txt_l no_w bgC_lightGray ukoCheck" colspan="4">
              <span>
                <gsmsg:write key="user.86" />
              </span>
              <span class="cl_fontWarn"></span>
            </td>
          </tr>

          <!--  グループ設定 -->
          <tr>
            <th class="txt_m txt_l no_w" colspan="2">
              <span>
                <gsmsg:write key="cmn.affiliation.group" />
              </span>
              <span class="cl_fontWarn">※</span>
            </th>
            <td class="txt_m txt_l ukoCheck" colspan="2">
              <div class="verAlignMid w100">
                <div class="bor1 hp302 w80">
                  <iframe name="grpFrame" src="../user/usr021.do?parentName=usr031" class="w100 hp300" frameborder="0">
                    <gsmsg:write key="user.32" />
                  </iframe>
                </div>
                <div class="w20 p5 txt_c">
                  <div>
                  <button type="button" name="all_off" class="baseBtn" onclick="javascript:onAllUnCheck();defaultGroup();" value="<gsmsg:write key="user.31" />">
                    <gsmsg:write key="user.31" />
                  </button>
                  </div>
                  <div class="mt10">
                  <button type="button" name="all_off" class="baseBtn" onclick="javascript:onParentChecked();defaultGroup();" value="<gsmsg:write key="user.33" />">
                    <gsmsg:write key="user.33" />
                  </button>
                  </div>
                  <div class="mt10">
                  <button type="button" name="all_off" class="baseBtn" onclick="javascript:onChildChecked();;defaultGroup();" value="<gsmsg:write key="user.34" />">
                    <gsmsg:write key="user.34" />
                  </button>
                  </div>
                </div>
              </div>
            </td>
          </tr>

          <!-- デフォルトグループ -->
          <tr>
            <th class="txt_m txt_l no_w" colspan="2">
              <logic:lessEqual name="usr031Form" property="usr030selectuser" value="<%=reservUser%>">
                <span>
                  <gsmsg:write key="user.35" />
                </span>
                <span class="cl_fontWarn">※</span>
              </logic:lessEqual>
              <logic:greaterThan name="usr031Form" property="usr030selectuser" value="<%=reservUser%>">
                <span>
                  <gsmsg:write key="user.35" />
                </span>
                <span class="cl_fontWarn">※</span>
              </logic:greaterThan>
            </th>
            <td class="txt_m txt_l ukoCheck" colspan="2">
              <logic:empty name="usr031Form" property="groupList">
                <html:select property="usr031defgroup" styleClass="wp550">
                  <option value="-1"><gsmsg:write key="cmn.select.plz" /></option>
                </html:select>
              </logic:empty>
              <logic:notEmpty name="usr031Form" property="groupList">
                <html:select property="usr031defgroup" styleClass="wp550">
                  <option value="-1"><gsmsg:write key="cmn.select.plz" /></option>
                  <html:optionsCollection name="usr031Form" property="groupList" value="groupSid" label="groupName" />
                </html:select>
              </logic:notEmpty>
            </td>
          </tr>
        </logic:equal>
      </table>

      <div class="footerBtn_block">
        <logic:equal name="usr031Form" property="adminFlg" value="true">
          <logic:equal name="usr031Form" property="usr031UsiMblUse" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE)%>">
            <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="submitStyleChange();setShowGroup();getSelectGroup();return buttonPushUsr('usr031_kakunin');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
          </logic:equal>
          <logic:notEqual name="usr031Form" property="usr031UsiMblUse" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PLUGIN_USE)%>">
            <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="setShowGroup();getSelectGroup();return buttonPushUsr('usr031_kakunin');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
          </logic:notEqual>
        </logic:equal>
        <logic:notEqual name="usr031Form" property="adminFlg" value="true">
          <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPushUsr('usr031_kakunin');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>
        </logic:notEqual>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPushUsr('Usr031_Back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</div>

<logic:equal name="usr031Form" property="labelSetPow" value="<%=labelSetOk%>">
  <div id="labelPanel" class="display_n txt_c p0">
    <div class="bd">
      <iframe src="../common/html/damy.html" name="lab" class="m0 p0 w100 hp450" frameborder="no"></iframe>
    </div>
  </div>
</logic:equal>

<div id="subPanel" class="display_n txt_c p0">
  <div class="bd">
    <iframe src="../common/html/damy.html" name="pos" class="m0 p0 w100 h100" frameborder="no"></iframe>
  </div>
</div>

</body>
</html:html>