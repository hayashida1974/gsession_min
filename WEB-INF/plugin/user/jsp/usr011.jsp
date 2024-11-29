<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<%
  String maxLengthCmt = String.valueOf(jp.groupsession.v2.usr.GSConstUser.MAX_LENGTH_GROUPCOMMENT);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="user.44" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../user/js/usr011.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/toastDisplay.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../user/css/user.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body class="dialog_overlay" onload="showLengthId($('#inputstr')[0], <%=maxLengthCmt%>, 'inputlength');">

  <html:form action="/user/usr011">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="usr010grpmode" />
    <html:hidden property="usr010grpSid" />
    <html:hidden property="usr011grpsid" />
    <html:hidden property="usr011grpOnce" />
    <html:hidden property="usr011DelButton" />
    <html:hidden property="usr011DelKbn" />
    <html:hidden property="selectgroup" />
    <html:hidden property="disabledGroups" />
    <logic:equal name="usr011Form" property="usr010grpmode" scope="request" value="add">
      <input type="hidden" name="helpPrm" value="0">
    </logic:equal>
    <logic:equal name="usr011Form" property="usr010grpmode" scope="request" value="edit">
      <input type="hidden" name="helpPrm" value="1">
    </logic:equal>

    <%-- デフォルトグループ指定されているユーザSIDリスト --%>
    <logic:notEmpty name="usr011Form" property="defGroupUserSidList" scope="request">
      <logic:iterate id="defGroupUserSid" name="usr011Form" property="defGroupUserSidList" scope="request">
        <script><!--
          usr011DefGroupUserSidList.push(<bean:write name="defGroupUserSid" />);
            --></script>
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin">
            <gsmsg:write key="cmn.shain.info" />
          </span>
          <logic:equal name="usr011Form" property="usr010grpmode" scope="request" value="add">
            <gsmsg:write key="user.usr011.1" />
          </logic:equal>
          <logic:equal name="usr011Form" property="usr010grpmode" scope="request" value="edit">
            <gsmsg:write key="user.usr011.2" />
          </logic:equal>
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="getChgctg(), buttonPush('kakunin');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <logic:equal name="usr011Form" property="usr010grpmode" scope="request" value="edit">
              <logic:equal name="usr011Form" property="usr011DelButton" value="enable">
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="pushDell('del'), getChgctg(), buttonPush('del');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
              </logic:equal>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
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

      <table class="table-left" cellpadding="5" cellspacing="0" border="0">
        <!-- グループID -->
        <tr>
          <th class="txt_m txt_l no_w">
            <gsmsg:write key="cmn.group.id" /><span class="cl_fontWarn">※</span>
          </th>
          <td class="w100 txt_m txt_l">
            <html:text property="usr011gpId" styleClass="wp500" maxlength="50" />
          </td>
        </tr>

        <!-- グループ名 -->
        <tr>
          <th class="txt_m txt_l no_w">
            <gsmsg:write key="cmn.group.name" /><span class="cl_fontWarn">※</span>
          </th>
          <td class="w100 txt_m txt_l">
            <html:text property="usr011gpname" styleClass="wp500" maxlength="50" />
          </td>
        </tr>

        <!-- グループ名カナ -->
        <tr>
          <th class="txt_m txt_l no_w">
            <gsmsg:write key="user.14" />
          </th>
          <td class="w100 txt_m txt_l">
            <html:text property="usr011gpnameKana" styleClass="wp500" maxlength="75" />
          </td>
        </tr>

        <!-- コメント -->
        <tr>
          <th class="txt_m txt_l no_w">
            <gsmsg:write key="cmn.comment" />
          </th>
          <td class="w100 txt_m txt_l">
            <textarea class="wp500" name="usr011com" rows="5" wrap="hard" onkeyup="showLengthStr(value, <%=maxLengthCmt%>, 'inputlength');" id="inputstr"><bean:write name="usr011Form" property="usr011com" /></textarea>
            <br>
            <span class="formCounter">
              <gsmsg:write key="cmn.current.characters" />:
            </span>
            <span id="inputlength" class="formCounter">0</span>
            <span class="formCounter_max">/<%=maxLengthCmt%><gsmsg:write key="cmn.character" />
            </span>
          </td>
        </tr>

        <!-- 利用者設定 -->
        <tr>
          <th class="txt_m txt_l no_w">
            <gsmsg:write key="user.75" />
          </th>
          <td class="w100 txt_m txt_l">
            <ui:multiselector name="usr011Form" property="usr011GroupUserUI" styleClass="hp200" />
          </td>
        </tr>

        <!-- グループ管理者設定 -->
        <tr>
          <th class="txt_m txt_l no_w">
            <span>
              <gsmsg:write key="cmn.group.admin" />
            </span>
          </th>
          <td>
            <ui:multiselector name="usr011Form" property="usr011GroupAdminUI" styleClass="hp200"/>
          </td>
        </tr>

        <!-- グループ設定 -->
        <tr>
          <th class="txt_m txt_l no_w">
            <gsmsg:write key="user.21" />
          </th>
          <td class="w100 txt_m txt_l">
            <span class="text_base">
              <br>
              <gsmsg:write key="user.usr011.3" />
            </span>
            <br>
            <span class="cl_fontWarn">
              <gsmsg:write key="user.usr011.4" />
            </span>
            <div class="bor1 hp302">
            <iframe src="../user/usr020.do?dspRoot=1&selectLevel=<bean:write name="usr011Form" property="selectLevel" scope="request"/>&checkGroup=<bean:write name="usr011Form" property="selectgroup" scope="request"/>" class="hp300 w100" name="ctgFrame" frameborder="0" root="1">
              <gsmsg:write key="user.32" />
            </iframe>
            </div>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="getChgctg(), buttonPush('kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="usr011Form" property="usr010grpmode" scope="request" value="edit">
          <logic:equal name="usr011Form" property="usr011DelButton" value="enable">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="pushDell('del'), getChgctg(), buttonPush('del');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:equal>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>


  </html:form>
  <common:toast toastId="toastUserSelDelError">
    <bean:message  key="error.input.default.group" />
  </common:toast>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>