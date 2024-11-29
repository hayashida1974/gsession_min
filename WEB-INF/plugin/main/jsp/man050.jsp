<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% String list = String.valueOf(jp.groupsession.v2.man.GSConstMain.MODE_LIST); %>
<% String search = String.valueOf(jp.groupsession.v2.man.GSConstMain.MODE_SEARCH); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src="../main/js/man050.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="user.usr090.2" /></title>
</head>
<logic:equal name="man050Form" property="man050cmdMode" value="<%= list %>">
  <body class="body_03" onunload="windowClose();">
</logic:equal>
<logic:equal name="man050Form" property="man050adminFlg" value="true">
  <logic:equal name="man050Form" property="man050cmdMode" value="<%= search %>">
    <body class="body_03" onunload="windowClose();" onload="tarminalChangeInit();">
  </logic:equal>
</logic:equal>
<!-- BODY -->

<html:form action="/main/man050">

<input type="hidden" name="CMD" value="">
<input type="hidden" name="yearRangeMinFr" value="10">
<input type="hidden" name="yearRangeMaxFr" value="0">
<input type="hidden" name="yearRangeMinTo" value="10">
<input type="hidden" name="yearRangeMaxTo" value="0">

<html:hidden property="man050SortKey" />
<html:hidden property="man050OrderKey" />
<html:hidden property="man050Backurl" />
<html:hidden property="man050SelectedUsrSid" />
<html:hidden property="man050cmdMode" />
<html:hidden property="man050SearchFlg" />

<html:hidden property="cmd" />
<html:hidden property="sch010SelectUsrSid" />
<html:hidden property="sch010SelectUsrKbn" />

<logic:equal name="man050Form" property="man050adminFlg" value="true">
<input type="hidden" name="helpPrm" value="<bean:write name="man050Form" property="man050cmdMode" />">
</logic:equal>

<logic:equal name="man050Form" property="man050adminFlg" value="false">
<input type="hidden" name="helpPrm" value="2">
</logic:equal>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../main/images/classic/header_main.png" alt="<gsmsg:write key="cmn.main" />">
      <img class="header_pluginImg" src="../main/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.main" />">
    </li>
    <li><gsmsg:write key="cmn.main" /></li>
    <li class="pageTitle_subFont">
      <logic:equal name="man050Form" property="man050cmdMode" value="<%= list %>">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="user.usr090.2" /></span><gsmsg:write key="cmn.list" />
      </logic:equal>
      <logic:notEqual name="man050Form" property="man050cmdMode" value="<%= list %>">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="user.usr090.2" /></span><gsmsg:write key="cmn.advanced.search" />
      </logic:notEqual>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('main');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
   <logic:equal name="man050Form" property="man050adminFlg" value="true">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <logic:equal name="man050Form" property="man050cmdMode" value="<%= list %>">
    <ul id="normal_tab" class="tabHeader w100">
      <li id="lastLogin" class="js_accTabHeader_tab bgI_none bgC_lightGray tabHeader_tab-on pr10 pl10">
        <gsmsg:write key="main.man050.9" />
      </li>
      <li id="search" class="js_accTabHeader_tab tabHeader_tab-off  pr10 pl10 bor_b1">
        <gsmsg:write key="cmn.advanced.search" />
      </li>
      <li class="tabHeader_space bor_b1">
      </li>
    </ul>
  </logic:equal>
  <logic:equal name="man050Form" property="man050cmdMode" value="<%= search %>">
    <ul class="tabHeader w100">
      <li id="lastLogin" class="js_accTabHeader_tab tabHeader_tab-off pr10 pl10 bor_b1">
        <gsmsg:write key="main.man050.9" />
      </li>
      <li id="search" class="js_accTabHeader_tab bgI_none bgC_lightGray tabHeader_tab-on  pr10 pl10">
        <gsmsg:write key="cmn.advanced.search" />
      </li>
      <li class="tabHeader_space bor_b1">
      </li>
    </ul>
    </logic:equal>
  </logic:equal>
  <logic:equal name="man050Form" property="man050cmdMode" value="<%= list %>">
    <%@ include file="/WEB-INF/plugin/main/jsp/man050_sub01.jsp" %>
  </logic:equal>
  <logic:equal name="man050Form" property="man050adminFlg" value="true">
    <logic:equal name="man050Form" property="man050cmdMode" value="<%= search %>">
      <%@ include file="/WEB-INF/plugin/main/jsp/man050_sub02.jsp" %>
    </logic:equal>
  </logic:equal>
</div>

</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>