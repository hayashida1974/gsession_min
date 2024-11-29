<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting" /></title>
</head>

<body>
<html:form action="/webmail/wml020">
<input type="hidden" name="CMD" value="init">
<html:hidden property="wmlViewAccount" />
<input type="hidden" name="wmlAccountMode" value="2">
<html:hidden property="backScreen" />

<input type="hidden" name="wmlMailTemplateKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.MAILTEMPLATE_COMMON) %>">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="wml.wml010.25" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('mailList');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="settingList">
    <dl>
      <dt onClick="return buttonPush('accountManager');">
        <span class="settingList_title"><gsmsg:write key="wml.wml020.08" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml020.09" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('confAccount');">
        <span class="settingList_title"><gsmsg:write key="cmn.preferences" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml020.10" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('confMailTemplate');">
        <span class="settingList_title"><gsmsg:write key="anp.anp070.02" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml020.13" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('confSendList');">
        <span class="settingList_title"><gsmsg:write key="wml.wml020.14" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml020.15" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('timesentManager');">
        <span class="settingList_title"><gsmsg:write key="wml.259" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml020.12" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="return buttonPush('mailLog');">
        <span class="settingList_title"><gsmsg:write key="wml.wml070.03" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml020.06" /></div>
      </dd>
    </dl>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>