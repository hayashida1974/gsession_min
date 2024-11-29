<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<!DOCTYPE html>
<logic:notEmpty name="adr241Form" property="addressList">
  <logic:iterate id="addressModel" name="adr241Form" property="addressList" indexId="idx">
    <tr class="js_listHover cursor_p">
      <td class="txt_c w5 js_tantoArea js_tableTopCheck">
        <input type="checkbox" name="adr240Address" value="<bean:write name="addressModel" property="addressSid" />">
      </td>
      <td class="w95 js_tantoArea" onclick="clickAddressName('<bean:write name="addressModel" property="addressSid" />');">
        <input type="hidden" id="adrName_<bean:write name="addressModel" property="addressSid" />" value="<bean:write name="addressModel" property="addressName" />">
        <bean:write name="addressModel" property="addressName" />
      </td>
    </tr>
  </logic:iterate>
</logic:notEmpty>
