<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%-- 手動削除区分 --%>
<%
  String manuDelNo        = String.valueOf(jp.groupsession.v2.rng.RngConst.MANU_DEL_NO);
  String manuDelOk        = String.valueOf(jp.groupsession.v2.rng.RngConst.MANU_DEL_OK);
%>

<script type="text/javascript" src="../ringi/js/rng170.js?<%= GSConst.VERSION_PARAM %>"></script>

<html:form action="/ringi/rng170">
<input type="hidden" name="plgId" value="rng">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_rngErrorArea"></div>
  <div class="txt_r">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="setRngMessage('ringi', this);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </div>
  <table class="table-left w100">
    <!-- 申請中 -->
    <tr>
      <th class="w20">
        <gsmsg:write key="rng.48" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="rng170Form" property="rng170pendingKbn" value="<%= manuDelNo %>" styleId="rngManuDelNo1" onclick="rngChangeDelKbn(1)" />
          <label for="rngManuDelNo1"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio styleClass="ml10" name="rng170Form" property="rng170pendingKbn" value="<%= manuDelOk %>" styleId="rngManuDelOk1" onclick="rngChangeDelKbn(1)" />
          <label for="rngManuDelOk1"><gsmsg:write key="wml.60" /></label>
          <logic:notEmpty name="rng170Form" property="yearLabelList">
            <html:select property="rng170pendingYear" styleId="rngDelYear1" styleClass="ml20">
              <html:optionsCollection name="rng170Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="rng170Form" property="monthLabelList">
            <html:select property="rng170pendingMonth" styleId="rngDelMonth1" styleClass="ml5">
              <html:optionsCollection name="rng170Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="rng170Form" property="dayLabelList">
            <html:select property="rng170pendingDay" styleId="rngDelDay1" styleClass="ml5 mr5">
              <html:optionsCollection name="rng170Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
    <!-- 完了 -->
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.complete" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="rng170Form" property="rng170completeKbn" value="<%= manuDelNo %>" styleId="rngManuDelNo2" onclick="rngChangeDelKbn(2)" />
          <label for="rngManuDelNo2"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio styleClass="ml10" name="rng170Form" property="rng170completeKbn" value="<%= manuDelOk %>" styleId="rngManuDelOk2" onclick="rngChangeDelKbn(2)" />
          <label for="rngManuDelOk2"><gsmsg:write key="wml.60" /></label>
          <logic:notEmpty name="rng170Form" property="yearLabelList">
            <html:select property="rng170completeYear" styleId="rngDelYear2" styleClass="ml20">
              <html:optionsCollection name="rng170Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="rng170Form" property="monthLabelList">
            <html:select property="rng170completeMonth" styleId="rngDelMonth2" styleClass="ml5">
              <html:optionsCollection name="rng170Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="rng170Form" property="dayLabelList">
            <html:select property="rng170completeDay" styleId="rngDelDay2" styleClass="ml5 mr5">
              <html:optionsCollection name="rng170Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
    <!-- 草稿 -->
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.draft" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="rng170Form" property="rng170draftKbn" value="<%= manuDelNo %>" styleId="rngManuDelNo3" onclick="rngChangeDelKbn(3)" />
          <label for="rngManuDelNo3"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio styleClass="ml10" name="rng170Form" property="rng170draftKbn" value="<%= manuDelOk %>" styleId="rngManuDelOk3" onclick="rngChangeDelKbn(3)" />
          <label for="rngManuDelOk3"><gsmsg:write key="wml.60" /></label>
          <logic:notEmpty name="rng170Form" property="yearLabelList">
            <html:select property="rng170draftYear" styleId="rngDelYear3" styleClass="ml20">
              <html:optionsCollection name="rng170Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="rng170Form" property="monthLabelList">
            <html:select property="rng170draftMonth" styleId="rngDelMonth3" styleClass="ml5">
              <html:optionsCollection name="rng170Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="rng170Form" property="dayLabelList">
            <html:select property="rng170draftDay" styleId="rngDelDay3" styleClass="ml5 mr5">
              <html:optionsCollection name="rng170Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>