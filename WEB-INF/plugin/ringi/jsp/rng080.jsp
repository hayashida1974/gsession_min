<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% int tmodeAll = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_ALL; %>
<% int tmodeShare = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_SHARE; %>
<% int tmodePrivate = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_PRIVATE; %>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../ringi/css/ringi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
<title>GROUPSESSION <gsmsg:write key="rng.62" /> <gsmsg:write key="cmn.preferences2" /></title>
</head>

<body>

<html:form action="ringi/rng080">
<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
<html:hidden property="backScreen" />
<html:hidden property="rngTemplateMode" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--　BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="rng.62" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rng010');">
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
      <dt onClick="buttonPush('rng120');">
        <span class="settingList_title"><gsmsg:write key="cmn.preferences" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="rng.rng080.03" /></div>
      </dd>
    </dl>

    <dl>
      <dt onClick="buttonPush('rng270');">
        <span class="settingList_title"><gsmsg:write key="rng.rng080.05" /></span>
      </dt>
      <dd>
        <div class="settingList_text"><gsmsg:write key="rng.rng080.06" /></div>
      </dd>
    </dl>

    <logic:equal name="rng080Form" property="rng080TemplatePersonalFlg" value="1">
      <dl>
        <dt onClick="template(<%= tmodePrivate %>, 'rng060');">
          <span class="settingList_title"><gsmsg:write key="rng.rng080.02" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="rng.rng080.04" /></div>
        </dd>
      </dl>
    </logic:equal>

    <logic:equal name="rng080Form" property="rng080KeiroPersonalFlg" value="1">
      <dl>
        <dt onClick="template(<%= tmodePrivate %>, 'rng100');">
          <span class="settingList_title"><gsmsg:write key="rng.rng080.01" /></span>
        </dt>
        <dd>
          <div class="settingList_text"><gsmsg:write key="rng.16" /></div>
        </dd>
      </dl>
    </logic:equal>

  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>