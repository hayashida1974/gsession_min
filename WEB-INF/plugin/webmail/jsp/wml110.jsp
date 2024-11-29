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
  <script src="../webmail/js/wml110.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml110.01" /></title>
</head>

<body>

<html:form action="/webmail/wml110">

<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden name="wml110Form" property="wmlViewAccount" />
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="wmlLabelCmdMode" />
<html:hidden property="wmlEditLabelId" />
<html:hidden property="dspCount" />

<logic:notEmpty name="wml110Form" property="lbnList" scope="request">
  <logic:iterate id="sort" name="wml110Form" property="lbnList" scope="request">
    <input type="hidden" name="wml110sortLabel" value="<bean:write name="sort" property="lbValue" />">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="cmn.label.management" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="addLabel();">
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

<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>

  <table class="table-left mt0">
    <tr>
    <th class="w0 no_w"><gsmsg:write key="wml.28" /></th>
    <td class="w100 txt_l">
    <logic:notEmpty name="wml110Form" property="acntList">
      <html:select property="wml110accountSid" size="1" styleClass="w100" onchange="changeAccountCombo();">
        <html:optionsCollection name="wml110Form" property="acntList" value="value" label="label" />
      </html:select>
    </logic:notEmpty>
    </td>
    </tr>
  </table>

  <div class="txt_l">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upLabelData');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downLabelData');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>


  <table class="table-top mt0">
    <tr>
      <th class="w0 no_w">&nbsp;</th>
      <th class="w100 no_w"><gsmsg:write key="wml.74" /></th>
      <th class="w0 no_w"><gsmsg:write key="cmn.fixed" /></th>
      <th class="w0 no_w"><gsmsg:write key="cmn.delete" /></th>
    </tr>

<logic:notEqual name="wml110Form" property="wml110accountSid" value="-1">
  <logic:iterate id="lbnData" name="wml110Form" property="lbnList" indexId="idx">
    <bean:define id="lbValue" name="lbnData" property="lbValue" />
    <% String labelCheckId = "chkLabel" + String.valueOf(idx.intValue()); %>

    <tr>
      <td class="txt_c js_tableTopCheck cursor_p no_w">
        <html:radio property="wml110SortRadio" value="<%= String.valueOf(lbValue) %>" styleId="<%= labelCheckId %>" />
      </td>
      <td>
        <bean:write name="lbnData" property="labelName" />
      </td>
      <td>
        <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.fixed" />" onClick="editLabel('<bean:write name="lbnData" property="labelSid" />');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.fixed" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.fixed" />">
          <gsmsg:write key="cmn.fixed" />
        </button>
      </td>
      <td>
        <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.delete" />" onClick="deleteLabel('<bean:write name="lbnData" property="labelSid" />');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </td>
    </tr>
  </logic:iterate>
</logic:notEqual>

  </table>

</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>