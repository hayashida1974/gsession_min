<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%-- 自動削除区分 --%>
<%
  String walKbnNoset     = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAL_KBN_NOSET);
  String walKbnAuto      = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAL_KBN_AUTODELETE);
%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<script src="../webmail/js/wml170.js?<%= GSConst.VERSION_PARAM %>"></script>
<body onload="changeEnableDisable();">
<html:form action="/webmail/wml170" styleClass="js_delForm">
<input type="hidden" name="plgId" value="wml1">

<div class="wrapper w100 mrl_auto">
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="wml.wml170kn.02" />
      </th>
      <td class="w75">
        <div class="mt5">
          <div class="verAlignMid">
            <html:radio name="wml170Form" property="wml170delKbn" value="<%= walKbnNoset %>" styleId="walDelKbnNo" onclick="changeEnableDisable()" />
            <label for="walDelKbnNo" class="mr10"><gsmsg:write key="cmn.noset" /></label>
            <html:radio name="wml170Form" property="wml170delKbn" value="<%= walKbnAuto %>" styleId="walDelKbnAuto" onclick="changeEnableDisable()" />
            <label for="walDelKbnAuto"><gsmsg:write key="cmn.autodelete" /></label>
          </div>
          <div class="ml20">
            <html:select property="wml170delYear" styleClass="js_wml170Year">
              <html:optionsCollection name="wml170Form" property="yearLabelList" value="value" label="label" />
            </html:select>
            <html:select property="wml170delMonth" styleClass="ml5 js_wml170Month">
              <html:optionsCollection name="wml170Form" property="monthLabelList" value="value" label="label" />
            </html:select>
            <html:select property="wml170delDay" styleClass="ml5 mr5 js_wml170Day">
              <html:optionsCollection name="wml170Form" property="dayLabelList" value="value" label="label" />
            </html:select>
            <gsmsg:write key="wml.73" />
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>
</body>
