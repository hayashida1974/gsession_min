<%@tag import="jp.co.sjts.util.NullDefault"%>
<%@ tag pageEncoding="utf-8"  %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib tagdir="/WEB-INF/tags/dandd" prefix="dandd" %>
<%@ attribute description="コンテンツ識別名" name="contentname" type="java.lang.String" rtexprvalue="true" required="true"%>
<%@ attribute description="クラス" name="styleClass" type="java.lang.String" rtexprvalue="true" %>
<%@ attribute description="配置形式 0：上下左右 1:上下 2：左右" name="arrange" type="java.lang.Integer" rtexprvalue="true" %>
<%
  String arrangeName = "";
  switch (arrange) {
  case 1:
    arrangeName =  "addibleV";
    break;
  case 2:
    arrangeName =  "addibleH";
    break;
  case 0:
  default:
    arrangeName = "";
  }
%>

<div name="<%=contentname%>" class="dandd_droptable <%=styleClass%> <%=arrangeName%>">
  <jsp:doBody />
</div>
