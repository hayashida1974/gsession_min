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
  <theme:css filename="theme.css"/>

  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml041.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml041.01" /></title>
</head>

<body onload="wml041Init();" onunload="wml041Close();">

<html:form action="webmail/wml041">

<input type="hidden" name="wml041CMD">
<html:hidden property="wml041mode" />
<html:hidden property="wml041No" />
<html:hidden property="wml041initFlg" />
<html:hidden property="wml041endFlg" />
<html:hidden property="wml041parentId" />

<div class="wrapper w80 mrl_auto">
  <table class="table-left wp550 mt20 mb20">
    <tr>
      <th>
        <gsmsg:write key="wml.wml041.01" />
      </th>
    </tr>
  </table>

  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <table class="table-left wp550 mt0">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.title" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text name="wml041Form" property="wml041title" maxlength="100" styleClass="wp400" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="wml.34" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:textarea name="wml041Form" property="wml041sign" rows="10" styleClass="wp400" />
      </td>
    </tr>
  </table>

  <div class="footerBtn_block wp550">
    <logic:equal name="wml041Form" property="wml041mode" value="1">
      <button type="button" value="<gsmsg:write key="cmn.edit" />" class="baseBtn" onClick="wml041Entry();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_1.png" alt="<gsmsg:write key="cmn.edit" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
        <gsmsg:write key="cmn.edit" />
      </button>
    </logic:equal>
    <logic:notEqual name="wml041Form" property="wml041mode" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="wml041Entry();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="rng.rng010.02" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="rng.rng010.02" />">
        <gsmsg:write key="cmn.add" />
      </button>
    </logic:notEqual>

    <button type="button" value="<gsmsg:write key="cmn.cancel" />" class="baseBtn" onClick="wml041thisClose();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.cancel" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.cancel" />">
      <gsmsg:write key="cmn.cancel" />
    </button>
  </div>

</div>

</html:form>
</div>
</body>
</html:html>