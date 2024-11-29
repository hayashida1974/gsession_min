<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%
  String delTypeNo = String.valueOf(jp.groupsession.v2.rng.RngConst.RAD_KBN_NO);
  String delTypeDelete = String.valueOf(jp.groupsession.v2.rng.RngConst.RAD_KBN_DELETE);
%>

<script src="../ringi/js/rng160.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/ringi/rng160" styleClass="js_delForm">
<html:hidden name="rng160Form" property="rng160initFlg" />
<input type="hidden" name="plgId" value="rng">

<div class="wrapper w100 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left">
    <!-- 申請中 -->
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="rng.48" />
      </th>
      <td class="w80">
        <div>
          <div class="verAlignMid">
            <html:radio name="rng160Form" property="rng160pendingKbn" value="<%= delTypeNo %>" styleId="radioNo1" onclick="radChangeDelKbn(1);" />
            <label for="radioNo1"><gsmsg:write key="cmn.noset" /></label>
            <html:radio styleClass="ml10" name="rng160Form" property="rng160pendingKbn" value="<%= delTypeDelete %>" styleId="radioDel1" onclick="radChangeDelKbn(1);" />
            <label for="radioDel1"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <logic:notEmpty name="rng160Form" property="yearLabelList">
              <html:select property="rng160pendingYear" styleId="rng160DelYear1">
                <html:optionsCollection name="rng160Form" property="yearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="rng160Form" property="monthLabelList">
              <html:select styleClass="ml5" property="rng160pendingMonth" styleId="rng160DelMonth1">
                <html:optionsCollection name="rng160Form" property="monthLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="rng160Form" property="dayLabelList">
              <html:select styleClass="ml5 mr5" property="rng160pendingDay" styleId="rng160DelDay1">
                <html:optionsCollection name="rng160Form" property="dayLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <gsmsg:write key="cmn.after.data" />
          </div>
        </div>
      </td>
    </tr>
    <!-- 完了 -->
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="cmn.complete" />
      </th>
      <td class="w80">
        <div>
          <div class="verAlignMid">
            <html:radio name="rng160Form" property="rng160completeKbn" value="<%= delTypeNo %>" styleId="radioNo2" onclick="radChangeDelKbn(2);" />
            <label for="radioNo2"><gsmsg:write key="cmn.noset" /></label>
            <html:radio styleClass="ml10" name="rng160Form" property="rng160completeKbn" value="<%= delTypeDelete %>" styleId="radioDel2" onclick="radChangeDelKbn(2);" />
            <label for="radioDel2"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <logic:notEmpty name="rng160Form" property="yearLabelList">
              <html:select property="rng160completeYear" styleId="rng160DelYear2">
                <html:optionsCollection name="rng160Form" property="yearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="rng160Form" property="monthLabelList">
              <html:select styleClass="ml5" property="rng160completeMonth" styleId="rng160DelMonth2">
                <html:optionsCollection name="rng160Form" property="monthLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="rng160Form" property="dayLabelList">
              <html:select styleClass="ml5 mr5" property="rng160completeDay" styleId="rng160DelDay2">
                <html:optionsCollection name="rng160Form" property="dayLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <gsmsg:write key="cmn.after.data" />
          </div>
        </div>
      </td>
    </tr>
    <!-- 草稿 -->
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="cmn.draft" />
      </th>
      <td class="w80">
        <div>
          <div class="verAlignMid">
            <html:radio name="rng160Form" property="rng160draftKbn" value="<%= delTypeNo %>" styleId="radioNo3" onclick="radChangeDelKbn(3);" />
            <label for="radioNo3"><gsmsg:write key="cmn.noset" /></label>
            <html:radio styleClass="ml10" name="rng160Form" property="rng160draftKbn" value="<%= delTypeDelete %>" styleId="radioDel3" onclick="radChangeDelKbn(3);" />
            <label for="radioDel3"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <logic:notEmpty name="rng160Form" property="yearLabelList">
              <html:select property="rng160draftYear" styleId="rng160DelYear3">
                <html:optionsCollection name="rng160Form" property="yearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="rng160Form" property="monthLabelList">
              <html:select styleClass="ml5" property="rng160draftMonth" styleId="rng160DelMonth3">
                <html:optionsCollection name="rng160Form" property="monthLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="rng160Form" property="dayLabelList">
              <html:select styleClass="ml5 mr5" property="rng160draftDay" styleId="rng160DelDay3">
                <html:optionsCollection name="rng160Form" property="dayLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <gsmsg:write key="cmn.after.data" />
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>
