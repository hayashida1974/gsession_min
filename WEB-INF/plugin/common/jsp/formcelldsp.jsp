<%@page import="jp.groupsession.v2.cmn.formbuilder.FormCell"%>
<%@page import="jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/formbuilder" prefix="fb" %>
<%@ taglib tagdir="/WEB-INF/tags/dandd" prefix="dandd" %>

<%
   FormCellDialogForm form = (FormCellDialogForm) request.getAttribute("FormCellDialogForm");
   FormCell bean = form.getCell();
%>

<fb:dspContent  bean="<%=bean%>"/>
<dandd:droparea />
