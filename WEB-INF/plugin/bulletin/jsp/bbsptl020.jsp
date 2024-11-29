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
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bulletin/js/bbsptl020.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="bbs.1" /></title>
</head>

<!-- BODY -->
<body onload="closeWindow();">

<html:form action="/bulletin/bbsptl020">

<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortalSid" />

<html:hidden property="bbsptl020forumSid" />
<html:hidden property="bbsptl020selectFlg" />
<logic:notEmpty name="bbsptl020Form" property="bbsPtl020createdForumList">
<logic:iterate id="fsid" name="bbsptl020Form" property="bbsPtl020createdForumList">
  <input type="hidden" name="bbsPtl020createdForum" value="<bean:write name="fsid" />">
</logic:iterate>
</logic:notEmpty>

<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../bulletin/images/classic/header_bulletin.png">
      <img class="header_pluginImg" src="../bulletin/images/original/header_bulletin.png">
    </li>
    <li>
      <gsmsg:write key="cmn.bulletin" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="bbs.9" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
  <div class="txt_l">
    <gsmsg:write key="bbs.ptl020.2" />
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="ptl.21" />
      </th>
      <td class="w80">
        <logic:notEmpty name="bbsptl020Form" property="portletTypeCombo">
          <html:select property="ptl080PluginPortlet" onchange="buttonPush('bbsChangeCombo');">
            <html:optionsCollection property="portletTypeCombo" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <table class="table-top w100">
    <tr>
      <th class="no_w table_title-color">
        <gsmsg:write key="bbs.3" />
      </th>
    </tr>
    <logic:notEmpty name="bbsptl020Form" property="forumList">
      <logic:iterate id="forInfModel" name="bbsptl020Form" property="forumList" indexId="idx">
        <tr class="js_listHover" data-sid="<bean:write name="forInfModel" property="bfiSid" />">
          <bean:define id="fLevel" name="forInfModel" property="bfiLevel" />
          <% int intLevel = ((Integer) fLevel).intValue(); %>
          <% int dep = 5 + (36 * intLevel - 36); %>
          <td class="js_listClick cursor_p" style="padding-left:<%= dep %>px;">
            <!-- フォーラム画像default -->
            <logic:equal name="forInfModel" property="binSid" value="0">
              <img src="../bulletin/images/classic/icon_forum.gif" class="btn_classicImg-display" alt="<gsmsg:write key="bbs.3" />">
              <img src="../bulletin/images/original/icon_forum_32.png" class="btn_originalImg-display" alt="<gsmsg:write key="bbs.3" />">
            </logic:equal>
            <!-- フォーラム画像original -->
            <logic:notEqual name="forInfModel" property="binSid" value="0">
              <img class="wp30hp30" src="../bulletin/bbsptl020.do?CMD=getImageFile&bbsptl020binSid=<bean:write name="forInfModel" property="binSid" />&bbsptl020forumSid=<bean:write name="forInfModel" property="bfiSid" />" alt="<gsmsg:write key="bbs.3" />">
            </logic:notEqual>
            <span class="cl_linkDef">
              <bean:write name="forInfModel" property="bfiName" />
            </span>
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
</div>
</body>
</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:html>
