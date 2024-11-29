<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%-- 手動削除区分 --%>
<%
  String manuDelNo        = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.MANU_DEL_NO);
  String manuDelOk        = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.MANU_DEL_OK);
%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script type="text/javascript" src="../webmail/js/wml060.js?<%= GSConst.VERSION_PARAM %>"></script>

<html:form action="/webmail/wml060">

<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden name="wml060Form" property="wmlViewAccount" />
<input type="hidden" name="plgId" value="wml0">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_wml060ErrorArea"></div>
  <div class="txt_r">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="setWmlMessage('webmail', this);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </div>
  <table class="table-left" id="wml_settings">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.trash" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="wml060Form" property="wml060delKbn1" value="<%= manuDelNo %>" styleId="manuDelNo1" onclick="wmlChangeDelKbn(1)" />
          <label for="manuDelNo1" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="wml060Form" property="wml060delKbn1" value="<%= manuDelOk %>" styleId="manuDelOk1" onclick="wmlChangeDelKbn(1)" />
          <label for="manuDelOk1"><gsmsg:write key="wml.60" /></label>

          <logic:notEmpty name="wml060Form" property="yearLabelList">
            <html:select property="wml060delYear1" styleId="delYear1" styleClass="ml20">
              <html:optionsCollection name="wml060Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="wml060Form" property="monthLabelList">
            <html:select property="wml060delMonth1" styleId="delMonth1" styleClass="ml5">
              <html:optionsCollection name="wml060Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="wml060Form" property="dayLabelList">
            <html:select property="wml060delDay1" styleId="delDay1" styleClass="ml5 mr5">
              <html:optionsCollection name="wml060Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.19" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="wml060Form" property="wml060delKbn2" value="<%= manuDelNo %>" styleId="manuDelNo2" onclick="wmlChangeDelKbn(2)" />
          <label for="manuDelNo2" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="wml060Form" property="wml060delKbn2" value="<%= manuDelOk %>" styleId="manuDelOk2" onclick="wmlChangeDelKbn(2)" />
          <label for="manuDelOk2"><gsmsg:write key="wml.60" /></label>

          <logic:notEmpty name="wml060Form" property="yearLabelList">
            <html:select property="wml060delYear2" styleId="delYear2" styleClass="ml20">
              <html:optionsCollection name="wml060Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="wml060Form" property="monthLabelList">
            <html:select property="wml060delMonth2" styleId="delMonth2" styleClass="ml5">
              <html:optionsCollection name="wml060Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="wml060Form" property="dayLabelList">
            <html:select property="wml060delDay2" styleId="delDay2" styleClass="ml5 mr5">
              <html:optionsCollection name="wml060Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.draft" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="wml060Form" property="wml060delKbn3" value="<%= manuDelNo %>" styleId="manuDelNo3" onclick="wmlChangeDelKbn(3)" />
          <label for="manuDelNo3" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="wml060Form" property="wml060delKbn3" value="<%= manuDelOk %>" styleId="manuDelOk3" onclick="wmlChangeDelKbn(3)" />
          <label for="manuDelOk3"><gsmsg:write key="wml.60" /></label>

          <logic:notEmpty name="wml060Form" property="yearLabelList">
            <html:select property="wml060delYear3" styleId="delYear3" styleClass="ml20">
              <html:optionsCollection name="wml060Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="wml060Form" property="monthLabelList">
            <html:select property="wml060delMonth3" styleId="delMonth3" styleClass="ml5">
              <html:optionsCollection name="wml060Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="wml060Form" property="dayLabelList">
            <html:select property="wml060delDay3" styleId="delDay3" styleClass="ml5 mr5">
              <html:optionsCollection name="wml060Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="wml.37" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="wml060Form" property="wml060delKbn4" value="<%= manuDelNo %>" styleId="manuDelNo4" onclick="wmlChangeDelKbn(4)" />
          <label for="manuDelNo4" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="wml060Form" property="wml060delKbn4" value="<%= manuDelOk %>" styleId="manuDelOk4" onclick="wmlChangeDelKbn(4)" />
          <label for="manuDelOk4"><gsmsg:write key="wml.60" /></label>

          <logic:notEmpty name="wml060Form" property="yearLabelList">
            <html:select property="wml060delYear4" styleId="delYear4" styleClass="ml20">
              <html:optionsCollection name="wml060Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="wml060Form" property="monthLabelList">
            <html:select property="wml060delMonth4" styleId="delMonth4" styleClass="ml5">
              <html:optionsCollection name="wml060Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="wml060Form" property="dayLabelList">
            <html:select property="wml060delDay4" styleId="delDay4" styleClass="ml5 mr5">
              <html:optionsCollection name="wml060Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.strage" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="wml060Form" property="wml060delKbn5" value="<%= manuDelNo %>" styleId="manuDelNo5" onclick="wmlChangeDelKbn(5)" />
          <label for="manuDelNo5" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="wml060Form" property="wml060delKbn5" value="<%= manuDelOk %>" styleId="manuDelOk5" onclick="wmlChangeDelKbn(5)" />
          <label for="manuDelOk5"><gsmsg:write key="wml.60" /></label>

          <logic:notEmpty name="wml060Form" property="yearLabelList">
            <html:select property="wml060delYear5" styleId="delYear5" styleClass="ml20">
              <html:optionsCollection name="wml060Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="wml060Form" property="monthLabelList">
            <html:select property="wml060delMonth5" styleId="delMonth5" styleClass="ml5">
              <html:optionsCollection name="wml060Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="wml060Form" property="dayLabelList">
            <html:select property="wml060delDay5" styleId="delDay5" styleClass="ml5 mr5">
              <html:optionsCollection name="wml060Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>