<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notEmpty name="dba001Form" property="resultMessage" scope="request">
    <!-- Messages -->
    <div id="dbaResultMsg"><bean:write name="dba001Form" property="resultMessage" /></div>
</logic:notEmpty>

<logic:notEmpty name="dba001Form" property="dba001ResultHeaderName" scope="request">

  <logic:notEmpty name="dba001Form" property="sqlExeTime" scope="request">
    <div id="dbaResultTime"><bean:write name="dba001Form" property="sqlExeTime" /></div>
  </logic:notEmpty>

  <!-- Header Name -->
  <table class="table-top">
    <tr>
      <logic:iterate id="rheadName" name="dba001Form" property="dba001ResultHeaderName" scope="request">
        <th class="fw_b no_w"><bean:write name="rheadName" /></th>
      </logic:iterate>
    </tr>
    <!-- Data Table -->
    <logic:iterate id="row" name="dba001Form" property="dba001ResultData" scope="request" indexId="idx">
      <tr>
        <logic:iterate id="data" name="row">
          <td><bean:write name="data" /></td>
        </logic:iterate>
      </tr>
    </logic:iterate>
  </table>

</logic:notEmpty>