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
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml100.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION<gsmsg:write key="wml.wml100.02" /></title>
</head>

<body>

<html:form action="/webmail/wml100">

<input type="hidden" name="CMD" value="">

<html:hidden property="wmlCmdMode" />
<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlAccountMode" />
<html:hidden property="wmlAccountSid" />
<html:hidden property="backScreen" />

<logic:notEmpty name="wml100Form" property="accountList" scope="request">
  <logic:iterate id="sort" name="wml100Form" property="accountList" scope="request">
    <input type="hidden" name="wml100sortLabel" value="<bean:write name="sort" property="acValue" />">
  </logic:iterate>
</logic:notEmpty>

<input type="hidden" name="wmlMailTemplateKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.MAILTEMPLATE_NORMAL) %>">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.100" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="accountConf(0, 0);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('psnTool');">
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
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upFilterData');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downFilterData');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>


  <table class="table-top">
    <tr>
      <th class="w0 no_w">&nbsp;</th>
      <th class="w5 no_w"><gsmsg:write key="wml.281" /></th>
      <th class="w25 no_w"><gsmsg:write key="wml.96" /></th>
      <th class="w30 no_w"><gsmsg:write key="cmn.mailaddress" /></th>
      <th class="w10 no_w"><gsmsg:write key="cmn.received.date" /></th>
      <th class="w20 no_w"><gsmsg:write key="cmn.memo" /></th>
      <th class="w5 no_w"><gsmsg:write key="cmn.edit" /></th>
      <th class="w5 no_w"><gsmsg:write key="cmn.template" /></th>
    </tr>

  <logic:iterate id="acuntData" name="wml100Form" property="accountList">
    <bean:define id="acValue" name="acuntData" property="acValue" />
    <tr>
      <td class="txt_c js_tableTopCheck cursor_p no_w"><html:radio property="wml100sortAccount" value="<%= String.valueOf(acValue) %>" /></td>
      <td><bean:write name="acuntData" property="accountUid" /></td>
      <td><bean:write name="acuntData" property="accountName" /></td>
      <td><bean:write name="acuntData" property="accountAddress" /></td>
      <td class="txt_c"><bean:write name="acuntData" property="receiveDate" /></td>
      <td><bean:write name="acuntData" property="accountBiko" /></td>
      <td>
        <logic:equal name="acuntData" property="accountUseUser" value="true">
          <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.fixed" />" onClick="return accountEdit(1, <bean:write name="acuntData" property="accountSid" />);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.fixed" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.fixed" />">
            <gsmsg:write key="cmn.fixed" />
          </button>
        </logic:equal>
      </td>
      <td>
        <button class="baseBtn wp100 no_w" value="<gsmsg:write key="cmn.template" />" onclick="return mailTemplate(<bean:write name="acuntData" property="accountSid" />);">
          <gsmsg:write key="cmn.template" />
        </button>
      </td>
    </tr>
  </logic:iterate>

  </table>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>