<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION</title>
</head>

<body onunload="windowClose();">

<script>
function importEml() {
    var thisForm = document.forms['wml1010Form'];

    thisForm.CMD.value = 'importEml';
    thisForm.submit();

    return false;
}

function changeAccountCombo(){
    document.forms[0].CMD.value='';
    document.forms[0].submit();
    return false;
}
</script>

<html:form action="/webmail/wml1010">

<input type="hidden" name="CMD" value="">
<html:hidden name="wml1010Form" property="wmlViewAccount" />
<html:hidden property="wml1010initFlg" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span>メールインポート
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="importEml();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('psnTool');">
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

  <table class="table-left mt0">
    <tr>
      <th class="w0 no_w">インポートするアカウント</th>
      <td class="w100 txt_l">
      <logic:notEmpty name="wml1010Form" property="acntList">
        <html:select property="wml1010accountSid" size="1" styleClass="w100" onchange="changeAccountCombo();">
          <html:optionsCollection name="wml1010Form" property="acntList" value="value" label="label" />
        </html:select>
      </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w0 no_w">インポートするフォルダ</th>
      <td class="w100 txt_l">
        <% String dirReceiveVal = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.DIR_TYPE_RECEIVE); %>
        <html:radio name="wml1010Form" property="wml1010saveWdrSid" value="<%= dirReceiveVal %>" styleId="dirReceive"/><label for="dirReceive"><gsmsg:write key="cmn.receive" /></label>
        <% String dirStorageVal = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.DIR_TYPE_STORAGE); %>
        <html:radio styleClass="ml10" name="wml1010Form" property="wml1010saveWdrSid" value="<%= dirStorageVal %>" styleId="dirStrage"/><label for="dirStrage"><gsmsg:write key="cmn.strage" /></label>
        <% String dirSendedVal = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.DIR_TYPE_SENDED); %>
        <html:radio styleClass="ml10" name="wml1010Form" property="wml1010saveWdrSid" value="<%= dirSendedVal %>" styleId="dirSended"/><label for="dirSended"><gsmsg:write key="wml.19" /></label>
      </td>
    </tr>
    <tr>
      <th class="w0 no_w">追加するラベル</th>
      <td class="w100 txt_l">
      <logic:notEmpty name="wml1010Form" property="lbnList">
        <html:select property="wml1010labelSid" size="1" styleClass="w100">
          <html:optionsCollection name="wml1010Form" property="lbnList" value="value" label="label" />
        </html:select>
      </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.capture.file" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <div>
          <span class="fs_13">*<gsmsg:write key="cmn.plz.specify2" arg0="emlファイル" /></span>
        </div>

        <% String tempMode = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_FILE); %>
        <attachmentFile:filearea
          mode="<%= tempMode %>"
          pluginId="<%= jp.groupsession.v2.cmn.GSConstWebmail.PLUGIN_ID_WEBMAIL %>"
          tempDirId="wml1010" />
      </td>
    </tr>
  </table>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>