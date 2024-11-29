<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>



<% boolean newTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>
<% if (!newTheme) { %>
   <jsp:include page="/WEB-INF/plugin/common/jsp/cmn003_classic.jsp">
       <jsp:param value="cmn003Form" name="thisFormName"/>
   </jsp:include>
<% } else {%>
   <jsp:include page="/WEB-INF/plugin/common/jsp/cmn003_origin.jsp">
       <jsp:param value="cmn003Form" name="thisFormName"/>
   </jsp:include>
<% }%>
