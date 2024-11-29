<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="user.usr032.3" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../user/js/group.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../user/js/usr032.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../user/css/user.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<html:form action="/user/usr032">

  <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NORMAL)%>">
    <body onload="defaultGroup();lmtEnableDisable();" onunload="windowClose();">
  </logic:equal>

  <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP_ALL)%>">
    <body onload="lmtEnableDisable();" onunload="windowClose();">
  </logic:equal>

  <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_MULTIPLE_GROUP)%>">
    <body onload="lmtEnableDisable();" onunload="windowClose();">
  </logic:equal>

  <input type="hidden" name="CMD" value="">
  <html:hidden property="processMode" />
  <html:hidden property="csvOut" />
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
  <html:hidden property="usr032initFlg" />
  <html:hidden property="usr032cmdMode" />


  <html:hidden property="usr030SearchKana" />
  <html:hidden property="selectgroup" />

  <logic:notEmpty name="usr032Form" property="groupList">
    <logic:iterate id="grpData" name="usr032Form" property="groupList">
      <input type="hidden" name="defGrpId" value="<bean:write name="grpData" property="groupSid" />">
      <input type="hidden" name="defGrpNm" value="<bean:write name="grpData" property="groupName" />">
    </logic:iterate>
  </logic:notEmpty>

  <logic:notEmpty name="usr032Form" property="usr030selectusers">
    <logic:iterate id="usrSid" name="usr032Form" property="usr030selectusers">
      <input type="hidden" name="usr030selectusers" value="<bean:write name="usrSid" />">
    </logic:iterate>
  </logic:notEmpty>

<% String tempMode = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_FILE_TANITU); %>
  <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

  <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NORMAL)%>">
    <input type="hidden" name="helpPrm" value="0">
  </logic:equal>

  <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP_ALL)%>">
    <input type="hidden" name="helpPrm" value="1">
  </logic:equal>

  <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_MULTIPLE_GROUP)%>">
    <input type="hidden" name="helpPrm" value="2">
  </logic:equal>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shain.info" /></span><gsmsg:write key="user.usr032.3" />
    </li>
      <li>
        <div>
          <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NORMAL)%>">
            <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="getSelectGroup();buttonSubmit('Usr032_userImp');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <gsmsg:write key="cmn.import" />
            </button>
          </logic:equal>
          <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP_ALL)%>">
            <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonSubmit('Usr032_userImp');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <gsmsg:write key="cmn.import" />
            </button>
          </logic:equal>
          <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_MULTIPLE_GROUP)%>">
            <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonSubmit('Usr032_userImp');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <gsmsg:write key="cmn.import" />
            </button>
          </logic:equal>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonSubmit('Usr032_Back');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </li>
    </ul>
</div>
  <div class="wrapper w80 mrl_auto">

    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>

    <ul class="tabHeader w100">

      <%-- グループ指定 --%>
      <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NORMAL)%>">
        <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none" onclick="changeTab('tujou');">
          <gsmsg:write key="group.designation" />
        </li>
        <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" onclick="changeTab('groupikatu');">
          <gsmsg:write key="user.usr032.5" />
        </li>
        <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" onclick="changeTab('multipleGroup')">
          <gsmsg:write key="user.usr032.6" />
        </li>
      </logic:equal>

      <%-- ユーザ・グループ同時 --%>
      <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP_ALL)%>">
        <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" onclick="changeTab('tujou');">
          <gsmsg:write key="group.designation" />
        </li>
        <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none" onclick="changeTab('groupikatu');">
          <gsmsg:write key="user.usr032.5" />
        </li>
        <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" onclick="changeTab('multipleGroup')">
          <gsmsg:write key="user.usr032.6" />
        </li>
      </logic:equal>

      <%-- ユーザ・複数グループ同時 --%>
      <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_MULTIPLE_GROUP)%>">
        <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" onclick="changeTab('tujou');">
          <gsmsg:write key="group.designation" />
        </li>
        <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" onclick="changeTab('groupikatu');">
          <gsmsg:write key="user.usr032.5" />
        </li>
        <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none" onclick="changeTab('multipleGroup')">
          <gsmsg:write key="user.usr032.6" />
        </li>
      </logic:equal>

      <li class="tabHeader_space border_bottom_none"></li>
    </ul>

    <!-- 取込みファイル -->
    <table class="table-left m0" cellpadding="5" cellspacing="0" border="0">
      <tr>
        <th class="txt_m txt_l no_w">
          <span>
            <gsmsg:write key="cmn.capture.file" />
          </span>
          <span class="cl_fontWarn">※</span>
        </th>
        <td class="txt_m txt_l border_right_none">
          <%
            jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
            String csvFileMsg = "";
          %>
          <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NORMAL)%>">
            <%
              csvFileMsg = "<a href=\"../user/usr032.do?CMD=Usr032_sample01&sample=1\">" + gsMsg.getMessage(request, "user.usr032.8") + "</a>";
            %>
            <div>
              <span class="fs_13">*<gsmsg:write key="cmn.plz.specify2" arg0="<%=csvFileMsg%>" /></span>
            </div>
            <div>
              <span class="fs_13">*<gsmsg:write key="user.91" />⇒<a href="../user/usr032.do?CMD=Usr032_sample01&sample=1">【<gsmsg:write key="cmn.download" />】</a></span>
            </div>
          </logic:equal>
          <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP_ALL)%>">
            <%
              csvFileMsg = "<a href=\"../user/usr032.do?CMD=Usr032_sample02&sample=1\">" + gsMsg.getMessage(request, "user.usr032.8") + "</a>";
            %>
            <div>
              <span class="fs_13">*<gsmsg:write key="cmn.plz.specify2" arg0="<%=csvFileMsg%>" /></span>
            </div>
            <div>
              <span class="fs_13">*<gsmsg:write key="user.91" />⇒<a href="../user/usr032.do?CMD=Usr032_sample02&sample=1">【<gsmsg:write key="cmn.download" />】</a></span>
            </div>
          </logic:equal>
          <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_MULTIPLE_GROUP)%>">
            <%
              csvFileMsg = "<a href=\"../user/usr032.do?CMD=Usr032_sample03&sample=1\">" + gsMsg.getMessage(request, "user.usr032.8") + "</a>";
            %>
            <div>
              <span class="fs_13">*<gsmsg:write key="cmn.plz.specify2" arg0="<%=csvFileMsg%>" /></span>
            </div>
            <div>
              <span class="fs_13">*<gsmsg:write key="user.91" />⇒<a href="../user/usr032.do?CMD=Usr032_sample03&sample=1">【<gsmsg:write key="cmn.download" />】</a></span>
            </div>
          </logic:equal>
          <attachmentFile:filearea
          mode="<%= tempMode %>"
          pluginId="<%= jp.groupsession.v2.usr.GSConstUser.PLUGIN_ID_USER %>"
          tempDirId="usr032" />
        </td>
      </tr>

      <!-- 所属グループ,デフォルトグループ　-->
      <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NORMAL)%>">
        <%@ include file="/WEB-INF/plugin/user/jsp/usr032_sub01.jsp"%>
      </logic:equal>

      <logic:equal name="usr032Form" property="changePassword" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.CHANGEPASSWORD_PARMIT)%>">
        <!--  パスワード変更 -->
        <tr>
          <th class="txt_m txt_l no_w">
            <span>
              <gsmsg:write key="cmn.change.password" />
            </span>
          </th>
          <td class="txt_m txt_l">
          <span class="verAlignMid">
            <html:checkbox name="usr032Form" property="usr032PswdKbn" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.PSWD_UPDATE_ON)%>" styleId="pswd_kbn" />
            <label for="pswd_kbn">
              <gsmsg:write key="user.usr031.9" />
            </label>
            </span>
          </td>
        </tr>
      </logic:equal>

      <!-- 上書き -->
      <tr>
        <th class="txt_m txt_l no_w">
          <span>
            <gsmsg:write key="cmn.overwrite" />
          </span>
        </th>
        <td class="txt_m txt_l">
        <span class="verAlignMid">
          <html:checkbox name="usr032Form" property="usr032updateFlg" value="1" styleId="updateFlg" onclick="lmtEnableDisable();" />
          <label for="updateFlg">
            <gsmsg:write key="user.1" />
          </label>
          </span>
          <div id="lmtinput">
          <span class="verAlignMid">
            <html:multibox name="usr032Form" property="usr032updatePassFlg" value="1" styleId="passUp" />
            <label for="passUp">
              <gsmsg:write key="user.no.pass.override" />
            </label>
            </span>
          </div>
        </td>
      </tr>

      <!-- グループ作成 -->
      <logic:equal name="usr032Form" property="usr032cmdMode" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP_ALL) %>">
        <%@ include file="/WEB-INF/plugin/user/jsp/usr032_sub02.jsp"%>
      </logic:equal>
    </table>

    <div class="footerBtn_block mt5">
      <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NORMAL)%>">
        <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="getSelectGroup();buttonSubmit('Usr032_userImp');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
      </logic:equal>
      <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP_ALL)%>">
        <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonSubmit('Usr032_userImp');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
      </logic:equal>
      <logic:equal name="usr032Form" property="usr032cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_MULTIPLE_GROUP)%>">
        <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonSubmit('Usr032_userImp');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
      </logic:equal>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonSubmit('Usr032_Back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>
  </div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

</body>
</html:html>