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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn170.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/common.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="main.man030.10" /></title>
</head>

<body>
<html:form action="/common/cmn170">
<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->

<div class="kanriPageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />">
      <img class="header_pluginImg" src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />">
    </li>
    <li>
      <gsmsg:write key="cmn.preferences2" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man030.10" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return backButton();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
  <div class="mt20 mb20">
    <table class="w100 table-noBorder">
      <% int originalThemeLength = 0; %>
      <logic:iterate id="theme" name="cmn170Form" property="originalThemeList" indexId="idx1">
      <% if (idx1 % 3 == 0) { %>
        <tr>
      <% } %>
      <% String StyleId = String.valueOf(idx1); %>
      <bean:define id="ctmid" name="theme" property="ctmSid" type="java.lang.Integer" />

      <% if (idx1 % 3 == 0) { %>
        <td class="themeSelect-left">
      <% } %>
      <% if (idx1 % 3 == 1) { %>
        <td class="themeSelect-center">
      <% } %>
      <% if (idx1 % 3 == 2) { %>
        <td class="themeSelect-right">
      <% } %>
      <label class="themeSelect_theme" for="<%= StyleId %>" onclick="return clickFormLabel(this);">
        <div class="pr10 txt_r bor1 bgC_lightGray">
          <span class="flo_l pl10">
            <bean:define id="perCnt" name="theme" property="ctmPerCount" type="java.lang.Integer" />
            <html:radio name="cmn170Form" styleId="<%= StyleId %>" property="cmn170Dsp1" value="<%= String.valueOf(ctmid.intValue()) %>" />
            <span class="text_theme"><bean:write name="theme" property="ctmName" /></span>
          </span>
          <span class="theme-count">
            <gsmsg:write key="bmk.22" arg0="<%= String.valueOf(perCnt.intValue()) %>" />
          </span>
        </div>
        <div class="themeSelect_img bgC_body">
          <span class="theme_image">
            <img class="cursor_p wp250 hp90" src="../<bean:write name="theme" property="ctmPathImg" />" alt="<bean:write name="theme" property="ctmName" />">
          </span>
        </div>
      </label>
      </td>
      <% if (idx1 % 3 == 2) { %>
        </tr>
      <% } %>
      <% originalThemeLength = idx1; %>
      </logic:iterate>
      <% originalThemeLength++; %>
      <logic:iterate id="theme" name="cmn170Form" property="classicThemeList" indexId="idx1">
      <% if ((idx1 + originalThemeLength) % 3 == 0) { %>
        <tr>
      <% } %>
      <% String StyleId = String.valueOf(idx1 + originalThemeLength); %>
      <bean:define id="ctmid" name="theme" property="ctmSid" type="java.lang.Integer" />

      <% if ((idx1 + originalThemeLength) % 3 == 0) { %>
        <td class="themeSelect-left">
      <% } %>
      <% if ((idx1 + originalThemeLength) % 3 == 1) { %>
        <td class="themeSelect-center">
      <% } %>
      <% if ((idx1 + originalThemeLength) % 3 == 2) { %>
        <td class="themeSelect-right">
      <% } %>
      <label class="themeSelect_theme" for="<%= StyleId %>" onclick="return clickFormLabel(this);">
        <div class="pr10 txt_r bor1 bgC_lightGray">
          <span class="flo_l pl10">
            <bean:define id="perCnt" name="theme" property="ctmPerCount" type="java.lang.Integer" />
            <html:radio name="cmn170Form" styleId="<%= StyleId %>" property="cmn170Dsp1" value="<%= String.valueOf(ctmid.intValue()) %>" />
            <span class="text_theme"><bean:write name="theme" property="ctmName" /></span>
          </span>
          <span class="theme-count">
            <gsmsg:write key="bmk.22" arg0="<%= String.valueOf(perCnt.intValue()) %>" />
          </span>
        </div>
        <div class="themeSelect_img bgC_body">
          <span class="theme_image">
            <img class="cursor_p wp250 hp90" src="../<bean:write name="theme" property="ctmPathImg" />" alt="<bean:write name="theme" property="ctmName" />">
          </span>
        </div>
      </label>
      </td>
      <% if ((idx1 + originalThemeLength) % 3 == 2) { %>
        </tr>
      <% } %>
      </logic:iterate>
    </table>
  </div>

  <div class="footerBtn_block mt20">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('cmn170back');">
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