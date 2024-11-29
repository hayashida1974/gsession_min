<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.wml.wml190kn.Wml190knForm" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstWebmail" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.97" /></title>
</head>

<body class="body_03">

<html:form action="/webmail/wml190kn">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden property="wmlCmdMode" />
<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlAccountMode" />
<html:hidden property="wmlAccountSid" />
<html:hidden property="wml190initFlg" />
<html:hidden property="wml190sign" />
<html:hidden property="wml190signAuto" />
<html:hidden property="wml190name" />
<html:hidden property="wml190address" />
<html:hidden property="wml190receiveServer" />
<html:hidden property="wml190receiveServerPort" />
<html:hidden property="wml190receiveServerEncrypt" />
<html:hidden property="wml190receiveServerType" />
<html:hidden property="wml190receiveServerUser" />
<html:hidden property="wml190receiveServerPassword" />
<html:hidden property="wml190sendServer" />
<html:hidden property="wml190sendServerPort" />
<html:hidden property="wml190sendServerEncrypt" />
<html:hidden property="wml190smtpAuth" />
<html:hidden property="wml190sendServerUser" />
<html:hidden property="wml190sendServerPassword" />
<html:hidden property="wml190autoTo" />
<html:hidden property="wml190autoCc" />
<html:hidden property="wml190autoBcc" />
<html:hidden property="wml190theme" />
<html:hidden property="wml190quotes" />
<html:hidden property="wml190autoSaveMin"/>

<html:hidden property="wml190authMethod" />
<html:hidden property="wml190provider" />
<html:hidden property="wml190authAccount" />
<html:hidden property="wml190cotSid" />

<logic:notEmpty name="wml190knForm" property="wml190proxyUser">
<logic:iterate id="proxyUser" name="wml190knForm" property="wml190proxyUser">
  <input type="hidden" name="wml190proxyUser" value="<bean:write name="proxyUser" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="wml100sortAccount" />


<bean:define id="acctMode" name="wml190knForm" property="wmlAccountMode" type="java.lang.Integer" />
<bean:define id="wCmdMode" name="wml190knForm" property="wmlCmdMode" type="java.lang.Integer" />
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.97" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
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
        <bean:write name="wml190knForm" property="wml190name" />
      </td>
    </tr>

<logic:equal name="wml190knForm" property="wml190settingServer" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_SETTING_SERVER_YES) %>">
<logic:notEqual name="wml190knForm" property="wml190authMethod" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
    <tr>
      <th class="no_w"><gsmsg:write key="wml.81" /></th>
      <td>
        <bean:write name="wml190knForm" property="wml190receiveServer" />&nbsp;<gsmsg:write key="cmn.port.number" />:<bean:write name="wml190knForm" property="wml190receiveServerPort" />
        <br><gsmsg:write key="cmn.ango" />:
        <bean:write name="wml190knForm" property="wml190knReciveEncrypt" />
      </td>
    </tr>
    <tr>
      <th class="no_w"><gsmsg:write key="wml.43" /></th>
      <td>
        <bean:write name="wml190knForm" property="wml190receiveServerUser" />
      </td>
    </tr>
    <tr>
      <th class="no_w"><gsmsg:write key="wml.44" /></th>
      <td>*****</td>
    </tr>
    <tr>
      <th class="no_w"><gsmsg:write key="wml.80" /></th>
      <td>
        <bean:write name="wml190knForm" property="wml190sendServer" />&nbsp;<gsmsg:write key="cmn.port.number" />:<bean:write name="wml190knForm" property="wml190sendServerPort" />
        <br><gsmsg:write key="cmn.ango" />:
        <bean:write name="wml190knForm" property="wml190knSendEncrypt" />
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.106" /></th>
      <td>
        <logic:equal name="wml190knForm" property="wml190smtpAuth" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAC_SMTPAUTH_YES) %>">
          <gsmsg:write key="wml.07" />
        </logic:equal>
        <logic:notEqual name="wml190knForm" property="wml190smtpAuth" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAC_SMTPAUTH_YES) %>">
          <gsmsg:write key="wml.08" />
        </logic:notEqual>
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.78" /></th>
      <td>
        <bean:write name="wml190knForm" property="wml190sendServerUser" />
      </td>
    </tr>
    <tr>
      <th class="no_w"><gsmsg:write key="wml.79" /></td>
      <td>
        <logic:notEmpty name="wml190knForm" property="wml190sendServerPassword">
          *****
        </logic:notEmpty>
      </td>
    </tr>
</logic:notEqual>
<logic:equal name="wml190knForm" property="wml190authMethod" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
    <tr>
      <th>
        <gsmsg:write key="wml.311" />
      </th>
      <td>
        <bean:define id="oauthFlg" name="wml190knForm" property="wml190oauthCompFlg" type="java.lang.Boolean" />
        <%  if (oauthFlg) { %><gsmsg:write key="wml.314"/>
        <%  } else { %><gsmsg:write key="wml.315"/><% } %>
      </td>
    </tr>
</logic:equal>
</logic:equal>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.34" /></th>
      <td>
        <logic:notEmpty name="wml190knForm" property="wml190signList">
          <table class="table-left w99 mt5">
            <logic:iterate id="signData" name="wml190knForm" property="wml190signList" indexId="signIdx" type="org.apache.struts.util.LabelValueBean">
              <tr>
                <td class="w98">
                  <span class="ml5 fs_14"><bean:write name="signData" property="label" /></span>
                </td>
              </tr>
            </logic:iterate>
          </table>
        </logic:notEmpty>
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.sign.auto" /></th>
      <td>
        <logic:equal name="wml190knForm" property="wml190signAuto" value="1">
          <gsmsg:write key="wml.sign.auto.no" />
        </logic:equal>
        <logic:notEqual name="wml190knForm" property="wml190signAuto" value="1">
          <gsmsg:write key="wml.sign.auto.insert" />
        </logic:notEqual>
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.52" /></th>
      <td>
        <bean:write name="wml190knForm" property="wml190autoTo" />
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.53" /></th>
      <td>
        <bean:write name="wml190knForm" property="wml190autoCc" />
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="wml.54" /></th>
      <td>
        <bean:write name="wml190knForm" property="wml190autoBcc" />
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="cmn.theme" /></th>
      <td>
        <bean:write name="wml190knForm" property="wml190knTheme" />
    </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="cmn.quotes" /></th>
      <td>
        <bean:write name="wml190knForm" property="wml190knQuotes" />
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.302" /></th>
      <td>
        <logic:equal name="wml190knForm" property="wml190autoSaveMin" value="0">
          <gsmsg:write key="wml.304" />
        </logic:equal>
        <logic:notEqual name="wml190knForm" property="wml190autoSaveMin" value="0">
          <bean:write name="wml190knForm" property="wml190autoSaveMin" /><gsmsg:write key="cmn.minute" />
        </logic:notEqual>
      </td>
    </tr>

<logic:equal name="wml190knForm" property="wml190proxyUserFlg" value="true">
    <tr>
      <th class="no_w"><gsmsg:write key="cmn.proxyuser" /></th>
      <td>
        <logic:notEmpty name="wml190knForm" property="proxyUserSelectCombo">
          <ul>
            <logic:iterate id="proxyUserKbnLabel" name="wml190knForm" property="proxyUserSelectCombo">
              <li><bean:write name="proxyUserKbnLabel" property="label" /></li>
            </logic:iterate>
          </ul>
        </logic:notEmpty>
      </td>
    </tr>
</logic:equal>

  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
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