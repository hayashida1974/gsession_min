<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<html:hidden property="usr040SearchKana" />

<table class="w100" cellpadding="0">
  <tr class="txt_t">
    <td class="w100 txt_c">
      <bean:define id="nrow" value="0" type="java.lang.String" />
      <table class="bgC_tableCell display_inline">
        <logic:iterate id="existskn" name="usr040Form" property="usr040ekanas" scope="request">
          <bean:define id="row" name="existskn" property="row" type="java.lang.String" />
          <logic:notEqual name="row" value="<%= nrow %>">
            <logic:notEqual name="<%= nrow %>" value="0">
              </tr>
            </logic:notEqual>
            <% nrow=row; %>
            <tr class="fs_15">
          </logic:notEqual>
          <logic:equal name="row" value="<%= nrow %>">
            <logic:equal name="existskn" property="exists" value="true">
              <td class="bor1 wp70 hp40 js_listUsrClick cursor_p " data-kana="<bean:write name="existskn" property="kana" />">
                <div class="cl_linkDef fw_b td-hoverChange wp70 hp40 display_tbl_c txt_c txt_m">
                  <bean:write name="existskn" property="kana" />
                </div>
              </td>
            </logic:equal>
            <logic:notEqual name="existskn" property="exists" value="true">
              <td class="bor1 wp70 hp40">
                <span class="cl_fontBody">
                  <bean:write name="existskn" property="kana" />
                </span>
              </td>
            </logic:notEqual>
          </logic:equal>
        </logic:iterate>
        </tr>
      </table>
    </td>
  </tr>
</table>

<table class="table-left w100">
  <tr>
    <th class="w10 fs_13">
      <gsmsg:write key="cmn.sort.order" />
    </th>
    <td class="w90 fs_13">
      <gsmsg:write key="cmn.first.key" />
      <span class="verAlignMid">
        <logic:notEqual name="usr040Form" property="sortKeyLabelList" value="">
          <html:select property="usr040sortKey" onchange="changeSortCombo();">
            <html:optionsCollection name="usr040Form" property="sortKeyLabelList" value="value" label="label" />
          </html:select>
        </logic:notEqual>
        <html:radio name="usr040Form" styleClass="ml5" property="usr040orderKey" onclick="return changeSortCombo();" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" styleId="sort1_up" />
        <label for="sort1_up">
          <gsmsg:write key="cmn.order.asc" />
        </label>
        <html:radio name="usr040Form" styleClass="ml10" property="usr040orderKey" onclick="return changeSortCombo();" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" styleId="sort1_dw" />
        <label for="sort1_dw">
          <gsmsg:write key="cmn.order.desc" />
        </label>
      </span>
      <span class="ml20">
        <gsmsg:write key="cmn.second.key" />
      </span>
      <span class="verAlignMid">
        <logic:notEqual name="usr040Form" property="sortKeyLabelList" value="">
          <html:select property="usr040sortKey2" onchange="changeSortCombo();">
            <html:optionsCollection name="usr040Form" property="sortKeyLabelList" value="value" label="label" />
          </html:select>
        </logic:notEqual>
        <html:radio name="usr040Form" styleClass="ml5" property="usr040orderKey2" onclick="return changeSortCombo();" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" styleId="sort2_up" />
        <label for="sort2_up">
          <gsmsg:write key="cmn.order.asc" />
        </label>
        <html:radio name="usr040Form" styleClass="ml10" property="usr040orderKey2" onclick="return changeSortCombo();" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" styleId="sort2_dw" />
        <label for="sort2_dw">
          <gsmsg:write key="cmn.order.desc" />
        </label>
      </span>
    </td>
  </tr>
</table>

