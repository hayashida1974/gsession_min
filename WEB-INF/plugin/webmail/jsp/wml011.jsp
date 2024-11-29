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

  <logic:notEmpty name="wml011Form" property="wml010theme" scope="request">
    <bean:define id="selectThemePath" name="wml011Form" property="wml010theme" type="String"/>
    <theme:css filename="theme.css" selectthemepath="<%= selectThemePath %>" />
  </logic:notEmpty>
  <logic:empty name="wml011Form" property="wml010theme" scope="request">
    <theme:css filename="theme.css"/>
  </logic:empty>


  <title>GROUPSESSION <gsmsg:write key="wml.header.info" /></title>
</head>

<body>

<html:form action="/webmail/wml011">

<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>

<div class="wrapper mrl_auto">
  <table class="table-left">
    <tr>
      <th class="fs_12">
        <gsmsg:write key="wml.header.info" />
      </th>
    </tr>

<logic:equal name="wml011Form" property="wml011viewFlg" value="true">
  <logic:notEmpty name="wml011Form" property="wml011MailHeaderData">
    <tr>
      <td class="fs_12">
        <logic:iterate id="headerData" name="wml011Form" property="wml011MailHeaderData">
          <bean:write name="headerData" property="wmhType" />: <bean:write name="headerData" property="wmhContent" /><br>
        </logic:iterate>
      </td>
    </tr>
  </logic:notEmpty>
  <logic:notEmpty name="wml011Form" property="wml011EntryDate">
    <tr>
      <td class="fs_12">
        <gsmsg:write key="schedule.src.84" />: <bean:write name="wml011Form" property="wml011EntryDate" />
      </td>
    </tr>
  </logic:notEmpty>
</logic:equal>

  </table>

  <div class="footerBtn_block txt_c">
    <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onClick="window.close();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>

</div>

</html:form>
</body>
</html:html>