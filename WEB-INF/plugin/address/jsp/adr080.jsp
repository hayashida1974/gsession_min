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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="address.adr080.1" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../address/js/adr080.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
</head>

<body class="body_03">
<html:form action="/address/adr080">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="adr080EditAtiSid">
<input type="hidden" name="adr080ProcMode">
<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>

<logic:notEmpty name="adr080Form" property="adr080GyosyuList" scope="request">
  <logic:iterate id="sort" name="adr080Form" property="adr080GyosyuList" scope="request">
    <input type="hidden" name="adr080KeyList" value="<bean:write name="sort" property="atiValue" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr080Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr080Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr080Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr080Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr080Form" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr080Form" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<%-- BODY --%>


<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.adr080.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="return buttonSubmit('adr080add', '<%= add %>', '-1');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('adr080back');">
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
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="return buttonPush('adr080up');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="return buttonPush('adr080down');">
      <gsmsg:write key="cmn.down" />
    </button>
    <gsmsg:write key="address.adr080.2" />
  </div>

  <table class="table-top">
    <tr>
      <th class="w5">
      </th>
      <th class="w25">
        <gsmsg:write key="address.101" />
      </th>
      <th class="w70">
        <gsmsg:write key="cmn.memo" />
      </th>
    </tr>
    <logic:notEmpty name="adr080Form" property="adr080GyosyuList">
      <bean:define id="tdColor" value="" />
      <% String[] tdColors = new String[] {"borderBlock-white", "td_type_usr"}; %>
      <logic:iterate id="gyoMdl" name="adr080Form" property="adr080GyosyuList" indexId="idx">
        <% tdColor = tdColors[(idx.intValue() % 2)]; %>
        <bean:define id="atiValue" name="gyoMdl" property="atiValue" />
        <tr class="js_listHover cursor_p" id="<bean:write name="gyoMdl" property="atiSid" />">
          <td class="txt_c js_tableTopCheck">
            <html:radio property="adr080SortRadio" value="<%= String.valueOf(atiValue) %>" />
          </td>
          <td class="js_listClick">
            <span class="cl_linkDef">
              <bean:write name="gyoMdl" property="atiName" />
            </span>
          </td>
          <td class="js_listClick">
            <bean:write name="gyoMdl" property="atiBiko" filter="false" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>
