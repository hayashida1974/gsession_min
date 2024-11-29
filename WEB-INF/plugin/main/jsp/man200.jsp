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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man200.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="main.man002.39" /></title>
</head>

<body onload="initEnableDisable();">

<!--BODY -->
<html:form action="/main/man200">
<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man002.39" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('rule_settei_kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKtool');">
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
      <html:errors />
    </logic:messagesPresent>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="main.man200.19" />
      </th>
      <td class="w75">
        <div class="fw_bold">
          <gsmsg:write key="main.man200.2" />
        </div>
        <html:select property="man200Digit" styleClass="wp90 ml20">
          <html:optionsCollection name="man200Form" property="man200DigitLabelList" value="value" label="label" />
        </html:select>
        <div class="mt10 fw_bold">
          <gsmsg:write key="main.man200.4" />
        </div>
        <span class="verAlignMid ml20">
          <html:radio name="man200Form" property="man200CoeKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_COEKBN_OFF) %>" styleId="man200CoeKbn_0" />
          <label for="man200CoeKbn_0"><gsmsg:write key="cmn.no" /></label>
          <html:radio name="man200Form" styleClass="ml10" property="man200CoeKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_COEKBN_ON_EN) %>" styleId="man200CoeKbn_1" />
          <label for="man200CoeKbn_1"><gsmsg:write key="main.man200.17" /></label>
          <html:radio name="man200Form" styleClass="ml10" property="man200CoeKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_COEKBN_ON_ENS) %>" styleId="man200CoeKbn_2" />
          <label for="man200CoeKbn_2"><gsmsg:write key="main.man200.18" /></label>
        </span>
        <div class="mt10 fw_bold">
          <gsmsg:write key="main.man200.20" />
        </div>
        <span class="verAlignMid ml20">
          <html:radio name="man200Form" property="man200UppercaseKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UPPERCASE_OFF) %>" styleId="man200UppercaseKbn_0" />
          <label for="man200UppercaseKbn_0"><gsmsg:write key="cmn.no" /></label>
          <html:radio name="man200Form" styleClass="ml10" property="man200UppercaseKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UPPERCASE_ON) %>" styleId="man200UppercaseKbn_1" />
          <label for="man200UppercaseKbn_1"><gsmsg:write key="cmn.required" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="man.expiration.date" />
      </th>
      <td class="w75">
        <div class="cl_fontWarn fs_13">
          <gsmsg:write key="main.man200.8" />
        </div>
        <div>
          <span class="verAlignMid">
        <logic:equal name="man200Form" property="manPasswordKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PASSWORD_EDIT_ADM) %>">
          <html:hidden property="manPasswordKbn" />
          <html:radio name="man200Form" property="man200LimitKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_LIMITKBN_OFF) %>" disabled="true" styleId="man200LimitKbn_0" onclick="changeLimitKbn(0);" />
          <label for="man200LimitKbn_0"><gsmsg:write key="main.man200.9" /></label>
          <html:radio name="man200Form" styleClass="ml10" property="man200LimitKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_LIMITKBN_ON) %>" disabled="true" styleId="man200LimitKbn_1" onclick="changeLimitKbn(1);" />
          <label for="man200LimitKbn_1"><gsmsg:write key="main.man200.10" /></label>
          <div class="cl_fontWarn">
            <gsmsg:write key="man.passkbn.admin"/>
          </div>
        </logic:equal>
        <logic:equal name="man200Form" property="manPasswordKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PASSWORD_EDIT_USER) %>">
          <html:hidden property="manPasswordKbn" />
          <html:radio name="man200Form" property="man200LimitKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_LIMITKBN_OFF) %>" styleId="man200LimitKbn_0" onclick="changeLimitKbn(0);" />
          <label for="man200LimitKbn_0"><gsmsg:write key="main.man200.9" /></label>
          <html:radio name="man200Form" styleClass="ml10" property="man200LimitKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_LIMITKBN_ON) %>" styleId="man200LimitKbn_1" onclick="changeLimitKbn(1);" />
          <label for="man200LimitKbn_1"><gsmsg:write key="main.man200.10" /></label>
        </logic:equal>
          </span>
        </div>
        <div id="passLimitDay">
          <gsmsg:write key="main.man200.11" /><html:text name="man200Form" property="man200LimitDay" maxlength="3" styleClass="wp50 txt_r ml5 mr5" /><gsmsg:write key="main.man200.12" />
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="main.man200.13" />
      </th>
      <td class="w75">
        <div>
          <gsmsg:write key="main.man200.14" />
        </div>
        <div>
          <span class="verAlignMid">
            <html:radio name="man200Form" property="man200UidPswdKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UIDPSWDKBN_ON) %>" styleId="man200UidPswdKbn_1" />
            <label for="man200UidPswdKbn_1"><gsmsg:write key="cmn.not.permit" /></label>
            <html:radio name="man200Form" styleClass="ml10" property="man200UidPswdKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UIDPSWDKBN_OFF) %>" styleId="man200UidPswdKbn_0" />
            <label for="man200UidPswdKbn_0"><gsmsg:write key="cmn.permit" /></label>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="main.man200.15" />
      </th>
      <td class="w75">
        <div>
          <gsmsg:write key="main.man200.16" />
        </div>
        <div>
          <span class="verAlignMid">
            <html:radio name="man200Form" property="man200OldPswdKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_OLDPSWDKBN_ON) %>" styleId="man200OldPswdKbn_1" onclick="" />
            <label for="man200OldPswdKbn_1"><gsmsg:write key="cmn.not.permit" /></label>
            <html:radio name="man200Form" styleClass="ml10" property="man200OldPswdKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_OLDPSWDKBN_OFF) %>" styleId="man200OldPswdKbn_0" onclick="" />
            <label for="man200OldPswdKbn_0"><gsmsg:write key="cmn.permit" /></label>
          </span>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('rule_settei_kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKtool');">
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