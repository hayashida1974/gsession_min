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
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="main.man200kn.1" /></title>
</head>

<body>

<!--　BODY -->
<html:form action="/main/man200kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="man200CoeKbn" />
<html:hidden property="man200UppercaseKbn" />
<html:hidden property="man200LimitKbn" />
<html:hidden property="man200UidPswdKbn" />
<html:hidden property="man200OldPswdKbn" />
<html:hidden property="man200LimitDay" />
<html:hidden property="man200Digit" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man200kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('setting');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_pswdConf');">
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
        <gsmsg:write key="main.man200.19" />
      </th>
      <td class="w75">
        <div class="fw_bold"><gsmsg:write key="main.man200.2" /></div>
        <bean:define id="digitMsg" name="man200knForm" property="man200Digit" type="java.lang.Integer" />
        <div class="ml20"><gsmsg:write key="cmn.digit.more" arg0="<%= String.valueOf(digitMsg.intValue()) %>" /></div>
        <div class="fw_bold mt10"><gsmsg:write key="main.man200.4" /></div>
        <div class="ml20">
          <logic:equal name="man200knForm" property="man200CoeKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_COEKBN_OFF) %>"><gsmsg:write key="cmn.no" /></logic:equal>
          <logic:equal name="man200knForm" property="man200CoeKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_COEKBN_ON_EN) %>"><gsmsg:write key="main.man200.17" /></logic:equal>
          <logic:equal name="man200knForm" property="man200CoeKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_COEKBN_ON_ENS) %>"><gsmsg:write key="main.man200.18" /></logic:equal>
        </div>
        <div class="fw_bold mt10"><gsmsg:write key="main.man200.20" /></div>
        <div class="ml20">
          <logic:equal name="man200knForm" property="man200UppercaseKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UPPERCASE_OFF) %>"><gsmsg:write key="cmn.no" /></logic:equal>
          <logic:equal name="man200knForm" property="man200UppercaseKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UPPERCASE_ON) %>"><gsmsg:write key="cmn.required" /></logic:equal>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="man.expiration.date" />
      </th>
      <td>
        <logic:equal name="man200knForm" property="man200LimitKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_LIMITKBN_OFF) %>"><gsmsg:write key="cmn.unlimited" /></logic:equal>
        <logic:equal name="man200knForm" property="man200LimitKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_LIMITKBN_ON) %>"><gsmsg:write key="main.man200.11" /><bean:write name="man200knForm" property="man200LimitDay"/><gsmsg:write key="main.man200.12" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="main.man200.13" />
      </th>
      <td>
        <logic:equal name="man200knForm" property="man200UidPswdKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UIDPSWDKBN_OFF) %>"><gsmsg:write key="cmn.permit" /></logic:equal>
        <logic:equal name="man200knForm" property="man200UidPswdKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UIDPSWDKBN_ON) %>"><gsmsg:write key="cmn.not.permit" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="main.man200.15" />
      </th>
      <td>
        <logic:equal name="man200knForm" property="man200OldPswdKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_OLDPSWDKBN_OFF) %>"><gsmsg:write key="cmn.permit" /></logic:equal>
        <logic:equal name="man200knForm" property="man200OldPswdKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_OLDPSWDKBN_ON) %>"><gsmsg:write key="cmn.not.permit" /></logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('setting');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_pswdConf');">
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