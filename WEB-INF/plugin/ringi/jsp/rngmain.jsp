<%@ page import="jp.groupsession.v2.cmn.dao.BaseUserModel"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<!DOCTYPE html>
<% String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY; %>
<html:html>
<head>
<meta name="format-detection" content="telephone=no">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="Content-Script-Type" content="text/javascript">
  <script type="text/javascript" src="../ringi/js/rngmain.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
  <title>GROUPSESSION <gsmsg:write key="rng.13" /></title>
</head>
<body>
<html:form action="ringi/rngmain">
<logic:notEmpty name="rngmainForm" property="rngDataList">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="rngCmdMode" value="0">
<input type="hidden" name="rngDspMode" value="1">
<input type="hidden" name="rngApprMode" value="0">
<html:hidden property="rngSid" />
<html:hidden property="rngProcMode" />
<html:hidden property="rng010sortKey" />
<html:hidden property="rng010orderKey" />
<html:hidden property="rng010ViewAccount" />
<bean:define id="procMode" name="rngmainForm" property="rngProcMode" />
<table class="table-top table_col-even w100 mb0">
  <tr>
    <th class="txt_l table_title-color" colspan="4">
      <img class="mainPlugin_icon" src="../ringi/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="rng.rngmain.03" />">
      <a class="main_pluginTitle" href="<bean:write name="rngmainForm" property="rngTopUrl" />">
        <gsmsg:write key="rng.rngmain.03" />
      </a>
    </th>
  </tr>
  <tr>
    <th class="w50 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="cmn.title" />
    </th>
    <th class="w20 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="rng.47" />
    </th>
    <th class="w15 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="rng.rngmain.02" />
    </th>
    <th class="w15 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="rng.rngmain.01" />
    </th>
  </tr>
  <bean:define id="kusr" name="<%= key %>" scope="session" />
  <logic:iterate id="rngData" name="rngmainForm" property="rngDataList" indexId="idx" scope="request">
    <% String apprMode = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_APPRMODE_APPR); %>
    <logic:equal name="rngData" property="rncType" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_RNCTYPE_APPL) %>">
      <% apprMode = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_APPRMODE_APPL); %>
    </logic:equal>
    <% String apprUserClass = "";%>
    <logic:equal name="rngData" property="apprUserDelFlg" value="true">
      <% apprUserClass = "text_appruser_del"; %>
    </logic:equal>
    <tr class="js_listHover cursor_p">
      <input type="hidden" name="select_usrSid" value="<bean:write name="kusr" property="usrsid" />">
      <input type="hidden" name="select_rngSid" value="<bean:write name="rngData" property="rngSid" />">
      <input type="hidden" name="select_apprMode" value="<%= apprMode %>">
      <td class="txt_l js_listClick cl_linkDef">
        <bean:write name="rngData" property="rngTitle" />
      </td>
      <td class="txt_l <%= apprUserClass %> js_listClick">
        <bean:write name="rngData" property="apprUser" />
      </td>
      <td class="txt_c js_listClick">
        <bean:write name="rngData" property="strRngAppldate" />
      </td>
      <td class="txt_c js_listClick">
        <bean:write name="rngData" property="strRcvDate" />
      </td>
    </tr>
  </logic:iterate>
</table>
</logic:notEmpty>
</html:form>
</body>
</html:html>
