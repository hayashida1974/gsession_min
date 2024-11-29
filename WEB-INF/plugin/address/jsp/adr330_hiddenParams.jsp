<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%
String thisFormName = request.getParameter("thisFormName");
pageContext.setAttribute("thisFormName", thisFormName);
%>

<html:hidden property="adr330searchFlg" />
<html:hidden property="adr330back" />

<%--表示用検索パラメータ --%>
<jsp:include page="/WEB-INF/plugin/address/jsp/adr330_searchHiddenParams.jsp" >
<jsp:param value="<%=thisFormName %>" name="thisFormName"/>
<jsp:param value="adr330searchBean" name="searchParamName"/>
</jsp:include>
<%--前回検索パラメータ --%>
<jsp:include page="/WEB-INF/plugin/address/jsp/adr330_searchHiddenParams.jsp" >
<jsp:param value="<%=thisFormName %>" name="thisFormName"/>
<jsp:param value="adr330searchSVBean" name="searchParamName"/>
</jsp:include>
