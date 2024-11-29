<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/formbuilder" prefix="fb" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src="../common/js/formdialog_prefarence.js?<%= GSConst.VERSION_PARAM %>"></script>
<script>
$(function() {
  <logic:equal parameter="CMD" value="dialogSubmit">
    <logic:messagesNotPresent message="false">
      $('#form_dialog').data('submitClose')();
    </logic:messagesNotPresent>
  </logic:equal>
});
</script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<logic:messagesPresent message="false">
  <div class="mb5"><html:errors /></div>
</logic:messagesPresent>
<fb:form_prefarence name="FormCellDialogForm" property="cell"></fb:form_prefarence>
