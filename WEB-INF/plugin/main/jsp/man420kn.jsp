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
<title>GROUPSESSION <gsmsg:write key="main.man040.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-1.6.4.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/main/man420kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="man420UsrImpFlg"/>
<html:hidden property="man420UsrFrHour"/>
<html:hidden property="man420InitFlg"/>
<html:hidden property="man420UsrImpTimeSelect"/>
<html:hidden property="man420ImportFolder"/>
<html:hidden property="man420ImpSuccessFolder"/>
<html:hidden property="man420ImpFailedFolder"/>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man420kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doSetting');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
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
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
<table class="table-left">
    <tr>
      <th class="w15 no_w">
        <gsmsg:write key="main.man420.1" />
      </th>
      <td class="w85">
        <logic:equal name="man420knForm" property="man420UsrImpFlg" value="0">
          <gsmsg:write key="main.man420.7" />
        </logic:equal>
        <logic:equal name="man420knForm" property="man420UsrImpFlg" value="1">
          <gsmsg:write key="main.man420.8" />
        </logic:equal>
      </td>
    </tr>
  <logic:equal name="man420knForm" property="man420UsrImpFlg" value="1">
  <logic:equal name="man420knForm" property="man420UsrImpTimeSelect" value="0">
    <tr id="usrStartTimeSelect">
      <th class="no_w">
        <gsmsg:write key="cmn.import" /><gsmsg:write key="main.man080.1" />
      </th>
      <td>
        <gsmsg:write key="main.man420.4" />
      </td>
    </tr>
  </logic:equal>
  <logic:equal name="man420knForm" property="man420UsrImpTimeSelect" value="1">
    <tr id="usrStartTimeSelect">
      <th class="no_w">
        <gsmsg:write key="cmn.import" /><gsmsg:write key="main.man080.1" />
      </th>
      <td>
        <gsmsg:write key="main.man420.5" />
      </td>
    </tr>
  </logic:equal>
  <logic:equal name="man420knForm" property="man420UsrImpTimeSelect" value="2">
    <tr id="usrStartTimeSelect">
      <th class="no_w">
        <gsmsg:write key="cmn.import" /><gsmsg:write key="main.man080.1" />
      </th>
      <td>
        <gsmsg:write key="main.man420.6" />
      </td>
    </tr>
    <tr id="usrStartTime">
      <th class="no_w">
        <gsmsg:write key="cmn.starttime" />
      </th>
      <td>
        <bean:write name="man420knForm" property="man420UsrFrHour"/><gsmsg:write key="cmn.hour.input" />
      </td>
    </tr>
  </logic:equal>
      <tr id="usrImportFolder">
      <th class="no_w">
        <gsmsg:write key="cmn.import" /><gsmsg:write key="cmn.folder" />
      </th>
      <td>
        <bean:write name="man420knForm" property="man420ImportFolder" filter="true"/>
      </td>
    </tr>
  </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doSetting');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
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