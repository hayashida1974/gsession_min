<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
pageContext.setAttribute("thisForm", request.getAttribute(request.getParameter("thisFormName")));
%>

<logic:notEmpty name="thisForm" property="adr010SearchTargetContact">
  <logic:iterate id="target" name="thisForm" property="adr010SearchTargetContact">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="thisForm" property="adr010svSearchTargetContact" >
  <logic:iterate id="svTarget" name="thisForm" property="adr010svSearchTargetContact" >
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="thisForm" property="adr010selectSid">
<logic:iterate id="adrSid" name="thisForm" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>
