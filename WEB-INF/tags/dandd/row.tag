<%@ tag pageEncoding="utf-8" %>
<%@ taglib tagdir="/WEB-INF/tags/dandd" prefix="dandd" %>
<%@ attribute description="クラス" name="styleClass" type="java.lang.String" rtexprvalue="true" %>

<div class="contents row <%=styleClass %>">
  <jsp:doBody />
</div>
