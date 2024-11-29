<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.wml.wml190.Wml190Form" %>
<%@ page import="jp.groupsession.v2.wml.wml190kn.Wml190knForm" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstWebmail" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml041.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml190.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmnOAuth.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.98" /></title>
</head>

<body onload="changeSendServerAuth(<bean:write name="wml190Form" property="wml190smtpAuth" />);" onunload="wml041Close();">

<html:form action="/webmail/wml190">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden property="wmlCmdMode" />
<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlAccountMode" />
<html:hidden property="wmlAccountSid" />
<html:hidden property="wml190name" />
<html:hidden property="wml190address" />
<html:hidden property="wml190initFlg" />

<html:hidden property="wml190authMethod" />
<html:hidden property="wml190provider" />
<html:hidden property="wml190authAccount" />
<html:hidden property="wml190cotSid" />

<bean:define id="acctMode" name="wml190Form" property="wmlAccountMode" type="java.lang.Integer" />
<bean:define id="wCmdMode" name="wml190Form" property="wmlCmdMode" type="java.lang.Integer" />
<% int accountMode = acctMode.intValue(); %>
<% int cmdMode = wCmdMode.intValue(); %>


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w90 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.98" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <table class="table-left mt0">
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.96" /></th>
      <td class="w75 txt_l">
        <bean:write name="wml190Form" property="wml190name" />
      </td>
    </tr>

<logic:equal name="wml190Form" property="wml190settingServer" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_SETTING_SERVER_YES) %>">
<logic:equal name="wml190Form" property="wml190authMethod" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
    <tr>
      <th>
        <gsmsg:write key="wml.311" />
      </th>
      <td>
        <span class="verAlignMid">
          <button type="button" class="baseBtn mr20" onclick="doOAuth('wml190provider', 'wml190address', 'wml190cotSid');"><gsmsg:write key="wml.313" /></button>
          <span class="ml10">
          <bean:define id="oauthFlg" name="wml190Form" property="wml190oauthCompFlg" type="java.lang.Boolean" />
          <gsmsg:write key="wml.316"/>:
          <%  if (oauthFlg) { %><gsmsg:write key="wml.314"/>
          <%  } else { %><gsmsg:write key="wml.315"/><% } %>
          </span>
        </span>
      </td>
    </tr>
</logic:equal>
<logic:notEqual name="wml190Form" property="wml190authMethod" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
    <tr>
      <th class="no_w"><gsmsg:write key="wml.81" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
      <td class="txt_l">
        <html:text name="wml190Form" property="wml190receiveServer" styleClass="w60" size="40" maxlength="100" />
        <gsmsg:write key="cmn.port.number" />
        <html:text name="wml190Form" property="wml190receiveServerPort" size="6" maxlength="5" />
        <div class="p0 mt5">
          <span class="ml5"><gsmsg:write key="cmn.ango" />:</span>
          <html:select name="wml190Form" property="wml190receiveServerEncrypt" styleClass="hp24">
            <html:optionsCollection name="wml190Form" property="wml190AngoProtocolCombo" value="value" label="label" />
          </html:select>
        </div>
      </td>
    </tr>
    <tr>
      <th class="no_w"><gsmsg:write key="wml.43" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
      <td class="txt_l">
        <html:text name="wml190Form" property="wml190receiveServerUser" styleClass="w60" size="40" maxlength="256" />
      </td>
    </tr>
    <tr>
      <th class="no_w"><gsmsg:write key="wml.44" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
      <td class="txt_l">
        <html:password name="wml190Form" property="wml190receiveServerPassword" styleClass="w60" size="40" maxlength="100" />
      </td>
    </tr>
    <tr>
      <th class="no_w"><gsmsg:write key="wml.80" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
      <td class="txt_l">
        <html:text name="wml190Form" property="wml190sendServer" styleClass="w60" size="40" maxlength="100" />
        <gsmsg:write key="cmn.port.number" />
        <html:text name="wml190Form" property="wml190sendServerPort" size="6" maxlength="5" />
        <div class="p0 mt5">
          <span class="ml5"><gsmsg:write key="cmn.ango" />:</span>
          <html:select name="wml190Form" property="wml190sendServerEncrypt" styleClass="hp24">
            <html:optionsCollection name="wml190Form" property="wml190AngoProtocolCombo" value="value" label="label" />
          </html:select>
        </div>
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.106" /></th>
      <td class="txt_l">
        <span class="verAlignMid">
          <html:radio name="wml190Form" property="wml190smtpAuth" styleId="smtpAuth1" value="1" onclick="changeSendServerAuth(1);" />
          <label for="smtpAuth1" class="mr10"><gsmsg:write key="wml.07" /></label>
          <html:radio name="wml190Form" property="wml190smtpAuth" styleId="smtpAuth2" value="0" onclick="changeSendServerAuth(0);" />
          <label for="smtpAuth2"><gsmsg:write key="wml.08" /></label>
        </span>
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.78" /></th>
      <td class="txt_l">
        <html:text name="wml190Form" property="wml190sendServerUser" styleClass="w60" size="40" maxlength="256" styleId="wml190sendServerUser" />
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.79" /></th>
      <td class="txt_l">
        <html:password name="wml190Form" property="wml190sendServerPassword" styleClass="w60" size="40" maxlength="100" styleId="wml190sendServerPassword" />
      </td>
    </tr>
</logic:notEqual>
</logic:equal>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.34" /></th>
      <td class="txt_l">
      <logic:empty name="wml190Form" property="wml190signList">
        <button type="button" id="signAddBtn" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="openSignWindow(0, 0, 1);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
      </logic:empty>

      <logic:notEmpty name="wml190Form" property="wml190signList">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upSign');">
          <gsmsg:write key="cmn.up" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downSign');">
          <gsmsg:write key="cmn.down" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="openSignWindow(0, 0, 1);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteSign');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>

        <table class="table-left w99 mt5">
          <logic:iterate id="signData" name="wml190Form" property="wml190signList" indexId="signIdx" type="org.apache.struts.util.LabelValueBean">
            <tr>
              <td class="w98">
                <span class="verAlignMid fs_14">
                  <html:radio name="wml190Form" property="wml190sign" value="<%= signData.getValue() %>"/>
                  <a href="#" class="ml5" onClick="openSignWindowEdit(<%= signData.getValue() %>, 1);"><bean:write name="signData" property="label" /></a>
                </span>
              </td>
            </tr>
          </logic:iterate>
        </table>
      </logic:notEmpty>
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.sign.auto" /></th>
      <td class="txt_l">
        <span class="verAlignMid">
          <html:radio name="wml190Form" property="wml190signAuto" styleId="signAuto1" value="0" /><label for="signAuto1"><gsmsg:write key="wml.sign.auto.insert" /></label>
          <html:radio name="wml190Form" property="wml190signAuto" styleId="signAuto2" styleClass="ml10" value="1" /><label for="signAuto2"><gsmsg:write key="wml.sign.auto.no" /></label>
        </span>
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.52" /></th>
      <td class="txt_l">
        <html:text name="wml190Form" property="wml190autoTo" styleClass="w60" size="40" maxlength="256" />
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.53" /></th>
      <td class="txt_l">
        <html:text name="wml190Form" property="wml190autoCc" styleClass="w60" size="40" maxlength="256" />
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.54" /></th>
      <td class="txt_l">
        <html:text name="wml190Form" property="wml190autoBcc" styleClass="w60" size="40" maxlength="256" />
      </td>
    </tr>

    <tr>
      <th><gsmsg:write key="cmn.theme" /></th>
      <td class="txt_l">
        <html:select name="wml190Form" property="wml190theme">
          <logic:notEmpty name="wml190Form" property="wml190themeList">
            <html:optionsCollection name="wml190Form" property="wml190themeList" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    <tr>
      <th><gsmsg:write key="cmn.quotes" /></th>
      <td class="txt_l">
        <html:select name="wml190Form" property="wml190quotes">
          <logic:notEmpty name="wml190Form" property="wml190quotesList">
            <html:optionsCollection name="wml190Form" property="wml190quotesList" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="no_w"><gsmsg:write key="wml.302" /></th>
      <td>
        <html:select property="wml190autoSaveMin">
          <html:optionsCollection name="wml190Form" property="wml190autoSaveList" value="value" label="label" />
        </html:select>
      </td>
    </tr>

    <logic:equal name="wml190Form" property="wml190proxyUserFlg" value="true">
    <tr>
      <th class="no_w"><gsmsg:write key="cmn.proxyuser" /></th>
      <td class="txt_l">
        <ui:usrgrpselector name="wml190Form" property="wml190proxyUserUI" styleClass="hp215" />
      </td>
    </tr>
    </logic:equal>

  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
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