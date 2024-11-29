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
<title>GROUPSESSION <gsmsg:write key="cmn.categorylist" /></title>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../address/js/adr280.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
</head>

<body class="body_03">

<html:form action="/address/adr280">

<input type="hidden" name="CMD" value="">
<input type="hidden" name="adr280EditSid">
<input type="hidden" name="adr280ProcMode">

<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>

<logic:notEmpty name="adr280Form" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr280Form" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr280Form" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr280Form" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr280Form" property="catList" scope="request">
  <logic:iterate id="sort" name="adr280Form" property="catList" scope="request">
    <input type="hidden" name="adr280KeyList" value="<bean:write name="sort" property="alcValue" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr280Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr280Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr280Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr280Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.category.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonSubmit('addCategory', 'add' , '-1');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('adr280back');">
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
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" name="btn_upper" onClick="return buttonPush('adr280up');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" name="btn_downer" onClick="return buttonPush('adr280down');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>
  <table class="table-top">
    <tr>
      <th class="table_title-color w5">
      </th>
      <th class="table_title-color w30">
        <gsmsg:write key="cmn.category.name" />
      </th>
      <th class="table_title-color w40">
        <gsmsg:write key="cmn.memo" />
      </th>
      <th class="table_title-color w25" colspan="2">
        <gsmsg:write key="cmn.edit" />
      </th>
    </tr>
    <logic:notEmpty name="adr280Form" property="catList">
      <logic:iterate id="catMdl" name="adr280Form" property="catList" indexId="idx">
        <bean:define id="alcValue" name="catMdl" property="alcValue" />
        <tr>
          <td class="txt_c js_tableTopCheck">
            <html:radio property="adr280SortRadio" value="<%= String.valueOf(alcValue) %>" />
          </td>
          <td>
            <bean:write name="catMdl" property="alcName" />
          </td>
          <td >
            <bean:write name="catMdl" property="alcBiko" filter="false" />
          </td>
          <td class="txt_c">
            <logic:notEqual name="catMdl" property="alcSid" value="1">
              <button type="button" value="<gsmsg:write key="cmn.category" />" class="baseBtn" onclick="return buttonSubmit('categoryEdit', 'edit', '<bean:write name="catMdl" property="alcSid" />')">
                <gsmsg:write key="cmn.category" />
              </button>
            </logic:notEqual>
          </td>
          <td class="txt_c">
            <button name="btn_prjadd" type="button" value="<gsmsg:write key="cmn.label" />" onClick="return buttonSubmit('adr280edit','', '<bean:write name="catMdl" property="alcSid" />');" class="baseBtn">
              <gsmsg:write key="cmn.label" />
            </button>
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonSubmit('addCategory', 'add' , '-1');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('adr280back');">
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