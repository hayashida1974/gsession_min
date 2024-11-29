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
<title>GROUPSESSION <gsmsg:write key="main.man002.19" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man122.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body class="body_03" onload="parent.menu.location='../common/cmn003.do';">
<html:form action="/main/man122">
<input type="hidden" name="CMD" value="conf">
<html:hidden property="man120pluginId" />
<html:hidden property="menuEditOk" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--BODY -->
<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man002.19" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdminMenu');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <div class="wrapperContent-top">
    <div class="txt_l">
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
    </div>
  </div>
  <div class="wrapperContent-2column">
    <div class="side_multi-left bgC_none">
      <table class="table-top">
        <tr>
          <th class="w100 txt_l table_title-color">
            <gsmsg:write key="man.menu" />
          </th>
        </tr>
        <tr class="js_listHover cursor_p" data-cmd="plugin">
          <td class="w100 txt_l js_listMenuClick bgC_tableCell">
            <span class="cl_linkDef"><gsmsg:write key="cmn.plugin" /></span>
          </td>
        </tr>
        <tr class="js_listHover cursor_p" data-cmd="seigenSettei">
          <td class="w100 txt_l js_listMenuClick bgC_tableCell">
            <span class="cl_linkDef"><gsmsg:write key="main.man120.1" /></span>
          </td>
        </tr>
        <tr class="js_listHover cursor_p bgC_tableCell">
          <td class="w100 txt_l">
            <span class="cl_linkDef"><gsmsg:write key="main.man120.2" /></span>
          </td>
        </tr>
      </table>
    </div>
    <div class="main">
      <table class="table-left w100">
        <tr>
          <th class="w25">
            <gsmsg:write key="main.man122.1" />
          </th>
          <td class="w75">
            <div>
              <span class="verAlignMid">
                <html:radio name="man122Form" styleId="limit" property="menuEditOk" value="1" onclick="buttonPush('man122edit');"/>
                <label for="limit">
                  <logic:equal name="man122Form" property="menuEditOk" value="1">
                    <span class="fw_b"><gsmsg:write key="main.man122.2" /></span>
                  </logic:equal>
                  <logic:equal name="man122Form" property="menuEditOk" value="0">
                    <gsmsg:write key="main.man122.2" />
                  </logic:equal>
                </label>
              </span>
            </div>
            <div>
              <span class="verAlignMid">
                <html:radio name="man122Form" styleId="permit" property="menuEditOk" value="0" onclick="buttonPush('man122edit');"/>
                <label for="permit">
                  <logic:equal name="man122Form" property="menuEditOk" value="0">
                  <span class="fw_b"><gsmsg:write key="main.man122.5" /></span>
                  </logic:equal>
                  <logic:equal name="man122Form" property="menuEditOk" value="1">
                  <gsmsg:write key="main.man122.5" />
                  </logic:equal>
                </label>
              </span>
            </div>
            <div class="mt10">
              <gsmsg:write key="main.man122.7" />
            </div>
          </td>
        </tr>
      </table>
    </div>
  </div>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdminMenu');">
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