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
<title>GROUPSESSION <gsmsg:write key="cmn.bulletin" /> <gsmsg:write key="bbs.bbs200.1" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../bulletin/js/bbs200.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/bulletin/bbs200">

<input type="hidden" name="CMD" value="">
<html:hidden name="bbs200Form" property="backScreen" />
<html:hidden name="bbs200Form" property="s_key" />
<html:hidden name="bbs200Form" property="bbs010page1" />
<html:hidden name="bbs200Form" property="bbs200Init" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.bulletin" /></span><gsmsg:write key="cmn.default.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('bbs200Decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdmMenu');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <table class="table-left">
    <tr>
      <th class="w15">
        <gsmsg:write key="bbs.37" />
      </th>
      <td class="w85">
        <span class="verAlignMid">
          <html:radio name="bbs200Form" styleId="iniPostTypeKbn_01" property="bbs200IniPostTypeKbn" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BAC_INI_POST_TYPE_KBN_ADM) %>" /><label for="iniPostTypeKbn_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio styleClass="ml10" name="bbs200Form" styleId="iniPostTypeKbn_02" property="bbs200IniPostTypeKbn" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BAC_INI_POST_TYPE_KBN_USER) %>" /><label for="iniPostTypeKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </span>
        <div id="js_iniPostTypeArea" class="settingForm_separator">
          <span class="verAlignMid">
            <html:radio name="bbs200Form" styleId="iniPostType_01" property="bbs200IniPostType" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) %>" /><label for="iniPostType_01"><gsmsg:write key="wml.js.12" /></label>
            <html:radio styleClass="ml10" name="bbs200Form" styleId="iniPostType_02" property="bbs200IniPostType" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.CONTENT_TYPE_TEXT_HTML) %>" /><label for="iniPostType_02"><gsmsg:write key="wml.109" /></label>
          </span>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('bbs200Decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdmMenu');">
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
