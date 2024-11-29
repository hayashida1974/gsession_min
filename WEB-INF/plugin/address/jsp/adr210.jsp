<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<%
  int add = jp.groupsession.v2.adr.GSConstAddress.PROCMODE_ADD;
  int edit = jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="address.adr210.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../address/js/adr210.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
</head>

<body class="body_03">

<html:form action="/address/adr210">
<input type="hidden" name="CMD" value="">
<html:hidden property="adr210ProcMode" />
<html:hidden property="adr210EditPosSid" />
<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>

<logic:notEmpty name="adr210Form" property="posList" scope="request">
  <logic:iterate id="sort" name="adr210Form" property="posList" scope="request">
    <input type="hidden" name="adr210KeyList" value="<bean:write name="sort" property="posValue" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr210Form" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr210Form" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr210Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr210Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr210Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr210Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>


<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.adr210.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonSubmit('adr210add', '<%= add %>', '-1');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('adr210back');">
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
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="return buttonPush('up');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="return buttonPush('down');">
      <gsmsg:write key="cmn.down" />
    </button>
    <gsmsg:write key="address.adr210.2" />
  </div>

  <table class="table-top">
  <tr>
    <th class="w5">
    </th>
    <th class="w25">
      <gsmsg:write key="cmn.job.title" />
    </th>
    <th class="w70">
      <gsmsg:write key="cmn.memo" />
    </th>
  </tr>
  <logic:notEmpty name="adr210Form" property="posList" scope="request">
    <logic:iterate id="posMdl" name="adr210Form" property="posList" scope="request" indexId="idx">
      <bean:define id="posValue" name="posMdl" property="posValue" />
      <tr class="js_listHover cursor_p" id="<bean:write name="posMdl" property="apsSid" />">
        <td class="txt_c js_tableTopCheck">
          <html:radio property="adr210SortRadio" value="<%= String.valueOf(posValue) %>" />
        </td>
        <td class="js_listClick">
          <span class="cl_linkDef">
            <bean:write name="posMdl" property="apsName" />
          </span>
        </td>
        <td class="js_listClick">
          <bean:write name="posMdl" property="apsBiko" filter="false" />
        </td>
      </tr>
    </logic:iterate>
  </logic:notEmpty>

</table>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>