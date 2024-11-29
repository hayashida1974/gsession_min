<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<%
pageContext.setAttribute("thisForm", request.getAttribute(request.getParameter("thisFormName")));
String beanName = request.getParameter("searchParamName");
String nameStr = request.getParameter("thisFormName") + "." + beanName;
%>

    <%-- ソート項目 --%>
<html:hidden property="<%=beanName + \".sortKey\" %>" />
    <%-- 並び順 --%>
<html:hidden property="<%=beanName + \".orderKey\" %>" />
    <%-- ページ --%>
<html:hidden property="<%=beanName + \".page\" %>" />
    <%--選択グループ--%>
<html:hidden property="<%=beanName + \".groupSidStr\" %>" />
    <%-- 会社名 --%>
<html:hidden property="<%=beanName + \".coName\" %>" />
    <%-- 業種 --%>
<html:hidden property="<%=beanName + \".atiSid\" %>" />
    <%-- 担当者 --%>
<html:hidden property="<%=beanName + \".user\" %>" />
    <%-- 氏名 姓 --%>
<html:hidden property="<%=beanName + \".unameSei\" %>" />
    <%-- 氏名 名 --%>
<html:hidden property="<%=beanName + \".unameMei\" %>" />
    <%-- 氏名カナ 姓 --%>
<html:hidden property="<%=beanName + \".unameSeiKn\" %>" />
    <%-- 氏名カナ 名 --%>
<html:hidden property="<%=beanName + \".unameMeiKn\" %>" />
    <%-- 所属 --%>
<html:hidden property="<%=beanName + \".syozoku\" %>" />
    <%-- 役職 --%>
<html:hidden property="<%=beanName + \".position\" %>" />
    <%-- E-MAIL --%>
<html:hidden property="<%=beanName + \".mail\" %>" />
