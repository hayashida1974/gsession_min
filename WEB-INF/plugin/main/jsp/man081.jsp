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
<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/jquery-ui-1.8.16.custom.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man081.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript">
<!--
var msglist_man081 = (function () {
  //使用するメッセージキーの配列を作成
   var ret = new Array();
   ret['cmn.close'] = '<gsmsg:write key="cmn.close"/>';
   ret['cmn.cancel'] = '<gsmsg:write key="cmn.cancel"/>';
   ret['cmn.ok'] = '<gsmsg:write key="cmn.ok"/>';
   ret['main.man081.2'] = '<gsmsg:write key="main.man081.2"/>';
   ret['main.man081.3'] = '<gsmsg:write key="main.man081.3"/>';
   return ret;
})();
-->
</script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.manual.backup" /></title>
</head>
<body class="body_03">
<html:form action="/main/man081">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="man081backupFile" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w90 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.manual.backup" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.reload" />" onclick="buttonPush('reload');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
          <gsmsg:write key="cmn.reload" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.create.new" />" onClick="createBackup();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.create.new" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.create.new" />">
          <gsmsg:write key="cmn.create.new" />
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
  <table class="table-top w100">
    <tr>
      <th class="w45 table_title-color no_w">
        <gsmsg:write key="cmn.backupfile" />
      </th>
      <th class="w30 table_title-color no_w">
        <gsmsg:write key="man.creation.date" />
      </th>
      <th class="w20 table_title-color no_w">
        <gsmsg:write key="main.man080.7" />
      </th>
      <th class="w5 table_title-color">
      </th>
    </tr>
    <logic:notEmpty name="man081Form" property="fileDataList">
      <logic:iterate id="fileData" name="man081Form" property="fileDataList" indexId="idx">
        <tr>
          <td class="txt_l">
            <a href="#!" onclick="return buttonPushWithFileName('download', '<bean:write name="fileData" property="hashFileName" />')"><bean:write name="fileData" property="fileName" /></a>
          </td>
          <td class="txt_c">
            <bean:write name="fileData" property="strMakeDate" />
          </td>
          <td class="txt_r">
            <bean:write name="fileData" property="fileSize" />
          </td>
          <td class="txt_c">
            <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPushWithFileName('delete', '<bean:write name="fileData" property="hashFileName" />');">
              <gsmsg:write key="cmn.delete" />
            </button>
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.reload" />" onclick="buttonPush('reload');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
      <gsmsg:write key="cmn.reload" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.create.new" />" onClick="createBackup();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.create.new" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.create.new" />">
      <gsmsg:write key="cmn.create.new" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backadmconf');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
</html:form>
<div class="display_none">
  <div id="confimationPop" title="">
    <ul class="p0 verAlignMid  w100 pt10">
      <li class="" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class="pl10 dialog_msgbody word_b-all">
        <bean:define id="actionName" type="String"><gsmsg:write key="cmn.backupfile"/></bean:define>
        <bean:message key="create.kakunin.once" arg0="<%=actionName %>" ></bean:message>
      </li>
    </ul>
  </div>
  <div id="creatingPop" title="" >
    <table class="w100 h100">
      <tr>
        <td class="txt_m txt_c">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
          <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
        </td>
        <td class="txt_m txt_c">
          <gsmsg:write key="main.man081.2"/>
        </td>
      </tr>
    </table>
  </div>

</div>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>