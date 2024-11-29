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
<title>GROUPSESSION <gsmsg:write key="cmn.import.cybozu" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-1.6.4.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body>
<html:form action="/main/man440">
<input type="hidden" name="CMD" value="">
<html:hidden property="man440InitFlg"/>
<!--BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.import.cybozu" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('440_back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </div>
  <div class="p10 bor1 bgC_cybozuAlert">
    <table class="w100">
      <tr>
        <td class="w100 fw_b pb20 fs_17" colspan="2">
          <gsmsg:write key="main.man440.1" />
        </td>
      </tr>
      <tr>
        <td class="txt_t">●</td>
        <td class="w100 txt_l"><gsmsg:write key="main.man440.2" /></td>
      </tr>
      <tr>
        <td class="txt_t">●</td>
        <td class="w100 txt_l"><gsmsg:write key="main.man440.3" /></td>
      </tr>
      <tr>
        <td class="txt_t">●</td>
        <td class="w100 txt_l"><gsmsg:write key="main.man440.4" /></td>
      </tr>
    </table>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man440.5" />1
      </th>
      <td class="w80">
        <div class="txt_l">
          <gsmsg:write key="main.man440.6" />
        </div>
        <div class="txt_l mt10">
          <a href="#" onclick="return buttonPush('grouplist');"><gsmsg:write key="user.44" /></a>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man440.5" />2
      </th>
      <td class="w80">
        <div class="txt_l">
          <gsmsg:write key="main.man440.7" />
        </div>
        <div class="txt_l mt10">
          <gsmsg:write key="main.man440.8" />
          <html:select property="man440GrpSid" styleClass="wp200">
            <html:optionsCollection name="man440Form" property="grpLabelList" value="value" label="label" />
          </html:select>
        </div>
        <table class="w100 table-noBorder mt10">
          <tr>
            <td class="no_w">1.</td>
            <td class="no_w">
              <a href="#" onclick="return buttonPush('memberImport');"><gsmsg:write key="main.man440.9" /></a>
            </td>
            <td class="w100 no_w">
              <gsmsg:write key="rng.rng130.19" /><gsmsg:write key="main.man440.10" />
            </td>
          </tr>
          <tr>
            <td class="no_w">2.</td>
            <td class="no_w">
              <a href="#" onclick="return buttonPush('eventImport');"><gsmsg:write key="main.man440.11" /></a>
            </td>
            <td class="w100 no_w">
              <gsmsg:write key="rng.rng130.19" /><gsmsg:write key="main.man440.12" />
            </td>
          </tr>
          <tr>
            <td class="no_w">3.</td>
            <td class="no_w">
              <a href="#" onclick="return buttonPush('boardImport');"><gsmsg:write key="cmn.bulletin" /></a>
            </td>
            <td class="w100 no_w">
              <gsmsg:write key="rng.rng130.19" /><gsmsg:write key="main.man440.13" />
            </td>
          </tr>
          <tr>
            <td class="no_w">4.</td>
            <td class="no_w">
              <a href="#" onclick="return buttonPush('todoImport');"><gsmsg:write key="main.man440.14" /></a>
            </td>
            <td class="w100 no_w">
              <gsmsg:write key="rng.rng130.19" /><gsmsg:write key="main.man440.15" />
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man440.5" />3
      </th>
      <td class="w80">
        <div class="txt_l">
          <gsmsg:write key="main.man440.16" />
        </div>
        <div class="txt_l mt10">
          <a href="#" onclick="return buttonPush('sisetuSettei');"><gsmsg:write key="reserve.rsv070.1" /></a>
          <gsmsg:write key="rng.rng130.19" /><gsmsg:write key="main.man440.17" />
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man440.5" />4
      </th>
      <td class="w80">
        <div class="txt_l">
          <gsmsg:write key="main.man440.18" />
        </div>
        <div class="txt_l mt10">
          <a href="#" onclick="return buttonPush('fileManager');"><gsmsg:write key="cmn.filekanri" /></a>
          <gsmsg:write key="rng.rng130.19" /><gsmsg:write key="main.man440.19" />
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>