<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.wml.wml180.Wml180Form" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%-- 手動削除区分 --%>
<%
  String manuDelNo        = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.MANU_DEL_NO);
  String manuDelOk        = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.MANU_DEL_OK);
%>


<script src="../webmail/js/wml180.js?<%= GSConst.VERSION_PARAM %>"></script>
<body onload="wml180ChangeDelKbn();">

<html:form action="/webmail/wml180">
<input type="hidden" name="plgId" value="wml1">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_wml180ErrorArea"></div>
  <div class="txt_r">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="setWmlLogMessage('webmail', this);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </div>
  <table class="table-left" id="wml_settings">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.manual.delete2" />
      </th>
      <td class="w75">
      <div class="verAlignMid">
        <html:radio styleId="wml180_delKbn0" name="wml180Form" property="wml180delKbn" value="<%= String.valueOf(Wml180Form.DEL_KBN_DATE) %>" onclick="wml180ChangeDelKbn();" /><label for="wml180_delKbn0" class="mr5"><gsmsg:write key="wml.wml180.01" /></label>
        <html:radio styleClass="ml10" styleId="wml180_delKbn1" name="wml180Form" property="wml180delKbn" value="<%= String.valueOf(Wml180Form.DEL_KBN_DATEAREA) %>" onclick="wml180ChangeDelKbn();" /><label for="wml180_delKbn1" class="mr5"><gsmsg:write key="wml.05" /></label>
        <html:radio styleClass="ml10" styleId="wml180_delKbn2" name="wml180Form" property="wml180delKbn" value="<%= String.valueOf(Wml180Form.DEL_KBN_ALL) %>" onclick="wml180ChangeDelKbn();" /><label for="wml180_delKbn2"><gsmsg:write key="cmn.delete.all" /></label>
      </div>
      <div class="mt10" id="dateElement">
        <logic:notEmpty name="wml180Form" property="yearLabelList">
          <html:select property="wml180delYear" styleId="delYear">
            <html:optionsCollection name="wml180Form" property="yearLabelList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>

        <logic:notEmpty name="wml180Form" property="monthLabelList">
          <html:select property="wml180delMonth" styleId="delMonth">
            <html:optionsCollection name="wml180Form" property="monthLabelList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>

        <logic:notEmpty name="wml180Form" property="dayLabelList">
          <html:select property="wml180delDay" styleId="delDay">
            <html:optionsCollection name="wml180Form" property="dayLabelList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
        <gsmsg:write key="wml.73" />
        </div>

        <div class="mt10" id="dateAreaElement">
          <html:select property="wml180delYearFr" styleClass="mr5" styleId="wmlYearFr">
            <html:optionsCollection name="wml180Form" property="dateAreaYearLabelList" value="value" label="label" />
          </html:select><gsmsg:write key="cmn.year2" />

          <html:select property="wml180delMonthFr" styleClass="mr5" styleId="wmlMonthFr">
            <html:optionsCollection name="wml180Form" property="dateAreaMonthLabelList" value="value" label="label" />
          </html:select><gsmsg:write key="cmn.month" />

          <html:select property="wml180delDayFr" styleClass="mr5" styleId="wmlDayFr">
            <html:optionsCollection name="wml180Form" property="dateAreaDayLabelList" value="value" label="label" />
          </html:select><gsmsg:write key="cmn.day" />

          <span class="ml5 mr5"><gsmsg:write key="tcd.153" /></span>

          <html:select property="wml180delYearTo" styleClass="mr5" styleId="wmlYearTo">
            <html:optionsCollection name="wml180Form" property="dateAreaYearLabelList" value="value" label="label" />
          </html:select><gsmsg:write key="cmn.year2" />

          <html:select property="wml180delMonthTo" styleClass="mr5" styleId="wmlMonthTo">
            <html:optionsCollection name="wml180Form" property="dateAreaMonthLabelList" value="value" label="label" />
          </html:select><gsmsg:write key="cmn.month" />

          <html:select property="wml180delDayTo" styleClass="mr5" styleId="wmlDayTo">
            <html:optionsCollection name="wml180Form" property="dateAreaDayLabelList" value="value" label="label" />
          </html:select><gsmsg:write key="cmn.day" />
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>
</body>