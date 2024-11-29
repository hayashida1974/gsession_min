<%@page import="jp.co.sjts.util.NullDefault"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!-- errMessages -->
    <logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
<bean:define id="CMD" value="<%=NullDefault.getString(request.getParameter(\"CMD\"), \"\") %>" />
<logic:equal name="CMD" value="chkMovePopup">
<logic:messagesNotPresent message="false">
    <%-- 移動チェックエラーなし時 ダイアログOpen --%>
        <script>

        $(function() {
            openMovePopup();
        });
        </script>
</logic:messagesNotPresent>
</logic:equal>
