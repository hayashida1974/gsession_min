<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src="../bulletin/js/bbs150.js?<%=GSConst.VERSION_PARAM%>"></script>
<html:form action="/bulletin/bbs150">

<input type="hidden" name="plgId" value="bbs">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_bbsErrorArea"></div>
  <div class="txt_r">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="setBbsMessage('bulletin', this);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </div>
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.manual.delete2" />
      </th>
      <td class="w80">
       <gsmsg:write key="bbs.bbs150.1" />
       <div class="mt5">
         <logic:notEmpty name="bbs150Form" property="bbs150YearLabelList">
         <html:select property="bbs150Year" styleClass="bbs150Year">
         <html:optionsCollection name="bbs150Form" property="bbs150YearLabelList" value="value" label="label" />
         </html:select>
         </logic:notEmpty>
         <logic:notEmpty name="bbs150Form" property="bbs150MonthLabelList">
         <html:select property="bbs150Month" styleClass="bbs150Month ml5 mr5">
         <html:optionsCollection name="bbs150Form" property="bbs150MonthLabelList" value="value" label="label" />
         </html:select>
         </logic:notEmpty>
         <gsmsg:write key="cmn.after.data" />
       </div>
      </td>
    </tr>
  </table>
</div>
</html:form>