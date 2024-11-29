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
<title>GROUPSESSION <gsmsg:write key="cmn.entry.label" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/readOnly.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/freeze.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr200.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body class="body_03" onload="freezeScreenParent('', false);parentReadOnly();parentReload(<bean:write name="adr200Form" property="adr200closeFlg" />);">
<html:form action="/address/adr200">

<input type="hidden" name="CMD" value="registLabel">

<div class="wrapper">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <logic:equal name="adr200Form" property="adr200Admin" value="1">
    <table class="table-left">
      <tr>
        <th class="w25 no_w">
          <gsmsg:write key="cmn.category" />
        </th>
        <td class="w75">
          <html:select property="adr200selectCategory">
            <html:optionsCollection name="adr200Form" property="adr200category" value="value" label="label" />
          </html:select>
        </td>
      </tr>
      <tr>
        <th class="w25 no_w">
          <gsmsg:write key="cmn.label" />
        </th>
        <td>
          <html:text property="adr200labelName" styleClass="wp320" />
          <div class="mt5">
            <gsmsg:write key="address.adr200.1" />
          </div>
        </td>
      </tr>
    </table>
  </logic:equal>

  <div class="txt_r">
    <logic:equal name="adr200Form" property="adr200Admin" value="1">
      <button type="submit" value="<gsmsg:write key="cmn.add" />" class="baseBtn">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <gsmsg:write key="cmn.add" />
      </button>
    </logic:equal>
    <button type="button" value="<gsmsg:write key="cmn.cancel" />" class="baseBtn" onClick="labelAddClose(false);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.cancel" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.cancel" />">
      <gsmsg:write key="cmn.cancel" />
    </button>
  </div>
</div>

</html:form>
</body>
</html:html>