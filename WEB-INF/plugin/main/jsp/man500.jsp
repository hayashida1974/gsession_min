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
<title>GROUPSESSION <gsmsg:write key="man.personal.edit" />
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../schedule/js/sch086.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/main/man500">

<input type="hidden" name="CMD" value="">
<html:hidden property="man500init" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man500.2" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('man500Ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man500Back');">
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
        <gsmsg:write key="main.man500.3" />
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio name="man500Form" styleId="manEditKbn_01" property="man500EditKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PCONF_EDIT_ADM) %>" /><label for="manEditKbn_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="man500Form" styleClass="ml10" styleId="manEditKbn_02" property="man500EditKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PCONF_EDIT_USER) %>" /><label for="manEditKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="main.man500.4" />
      </th>
      <td>
        <logic:equal name="man500Form" property="manPasswordLimitFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_LIMITKBN_ON) %>">
          <div class="verAlignMid">
            <html:hidden property="manPasswordKbn" />
            <html:radio name="man500Form" styleId="manPasswordKbn_01" property="man500PasswordKbn" disabled="true" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PASSWORD_EDIT_ADM) %>" /><label for="manPasswordKbn_01"><gsmsg:write key="cmn.set.the.admin" /></label>
            <html:radio name="man500Form" styleClass="ml10" styleId="manPasswordKbn_02" property="man500PasswordKbn" disabled="true" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PASSWORD_EDIT_USER) %>" /><label for="manPasswordKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
          </div>
          <div>
            <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="main.man500.6"/></span>
          </div>
        </logic:equal>
        <logic:notEqual name="man500Form" property="manPasswordLimitFlg" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_LIMITKBN_ON) %>">
          <div class="verAlignMid">
            <html:radio name="man500Form" styleId="manPasswordKbn_01" property="man500PasswordKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PASSWORD_EDIT_ADM) %>" /><label for="manPasswordKbn_01"><gsmsg:write key="cmn.set.the.admin" /></label>
            <html:radio name="man500Form" styleClass="ml10" styleId="manPasswordKbn_02" property="man500PasswordKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PASSWORD_EDIT_USER) %>" /><label for="manPasswordKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
          </div>
        </logic:notEqual>
      </td>
    </tr>
    <%-- ワンタイムパスワード通知先メールアドレス --%>
    <tr>
      <th>
        <gsmsg:write key="user.usr060.4" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="man500Form" styleId="manOtpAddressKbn_01" property="man500OtpAddressKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTPTOADDRES_EDIT_ADM) %>" /><label for="manOtpAddressKbn_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="man500Form" styleClass="ml10" styleId="manOtpAddressKbn_02" property="man500OtpAddressKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTPTOADDRES_EDIT_USER) %>" /><label for="manOtpAddressKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('man500Ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man500Back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
  </html:form>
  </body>
  </html:html>