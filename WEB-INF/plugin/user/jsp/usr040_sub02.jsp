<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<html:hidden property="selectgsid" />
<html:hidden property="scrollPosition" />
<table class="w100">
  <tr class="txt_t">
    <td class="w50">
      <input type="hidden" name="usr040_02_init" value="0">
      <div class="bor1 hp152">
      <iframe src="../user/usr022.do" class="h100 w100" name="ctgFrame" frameborder="0">
        <gsmsg:write key="user.32" />
      </iframe>
      </div>
    </td>
    <td class="w5"></td>
    <td class="w45 txt_m txt_l">
      <table class="table-left">
        <tr>
          <th class="w10 no_w">
            <gsmsg:write key="cmn.group.id" />
          </th>
          <td>
            <html:text name="usr040Form" property="usr040GrpSearchGId" styleClass="w100" />
          </td>
        </tr>
        <tr>
          <th class="w10 no_w">
            <gsmsg:write key="cmn.group.name" />
          </th>
          <td>
            <html:text name="usr040Form" property="usr040GrpSearchGName" styleClass="w100" />
          </td>
        </tr>
      </table>
      <div class="w100 txt_c">
        <button type="button" name="grpSearch" class="baseBtn" value="絞込み" onClick="groupShiborikomi(1);">絞込み</button>
      </div>
    </td>
  </tr>
</table>

<table class="table-left">
  <tr>
    <th class="w10 no_w">
      <gsmsg:write key="cmn.sort.order" />
    </th>
    <td>
      <span>
        <gsmsg:write key="cmn.first.key" />
      </span>
      <span class="verAlignMid">
        <logic:notEqual name="usr040Form" property="sortKeyLabelList" value="">
          <html:select property="usr040sortKey" onchange="changeSortCombo();">
            <html:optionsCollection name="usr040Form" property="sortKeyLabelList" value="value" label="label" />
          </html:select>
        </logic:notEqual>
        <span class="verAlignMid">
          <html:radio name="usr040Form" property="usr040orderKey" styleClass="ml5" onclick="return changeSortCombo();" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" styleId="sort1_up" />
          <label for="sort1_up">
            <gsmsg:write key="cmn.order.asc" />
          </label>
          <html:radio name="usr040Form" property="usr040orderKey" styleClass="ml10" onclick="return changeSortCombo();" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" styleId="sort1_dw" />
          <label for="sort1_dw">
            <gsmsg:write key="cmn.order.desc" />
          </label>
        </span>
      </span>
      <span>
        <gsmsg:write key="cmn.second.key" />
      </span>
      <span class="verAlignMid">
        <logic:notEqual name="usr040Form" property="sortKeyLabelList" value="">
          <html:select property="usr040sortKey2" onchange="changeSortCombo();">
            <html:optionsCollection name="usr040Form" property="sortKeyLabelList" value="value" label="label" />
          </html:select>
        </logic:notEqual>
        <span class="verAlignMid">
          <html:radio name="usr040Form" property="usr040orderKey2" styleClass="ml5" onclick="return changeSortCombo();" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" styleId="sort2_up" />
          <label for="sort2_up">
            <gsmsg:write key="cmn.order.asc" />
          </label>
          <html:radio name="usr040Form" property="usr040orderKey2" styleClass="ml10" onclick="return changeSortCombo();" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" styleId="sort2_dw" />
          <label for="sort2_dw">
            <gsmsg:write key="cmn.order.desc" />
          </label>
        </span>
      </span>
    </td>
  </tr>
</table>
