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
<title>GROUPSESSION <gsmsg:write key="cmn.preferences2" /></title>
</head>

<body>

<html:form action="/webmail/wml090">
<input type="hidden" name="CMD" value="init">
<html:hidden property="backScreen" />
<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlAccountMode" />
<html:hidden property="wmlAccountSid" />


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
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
      <dt onClick="buttonPush('accountList');">
        <span class="settingList_title"><gsmsg:write key="wml.100" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml090.04" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="buttonPush('labelList');">
        <span class="settingList_title"><gsmsg:write key="cmn.label.management" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml090.02" /></div>
      </dd>
    </dl>
    <dl>
      <dt onClick="buttonPush('filterList');">
        <span class="settingList_title"><gsmsg:write key="wml.86" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml090.03" /></div>
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
      <dt onClick="return buttonPush('deleteMailList');">
        <span class="settingList_title"><gsmsg:write key="wml.wml090.05" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="wml.wml090.06" /></div>
      </dd>
    </dl>
    <!-- *** add(s) システムプロステージ *** -->
    <dl>
      <dt onClick="return buttonPush('importEmlFiles');">
        <span class="settingList_title">メールインポート</span>
      </dt>
      <dd>
        <div class="settingList_text">emlファイルをインポートします。</div>
      </dd>
    </dl>
    <!-- *** add(e) システムプロステージ *** -->
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>