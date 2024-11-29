<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<% int sort_title = jp.groupsession.v2.rng.RngConst.RNG_SORT_TITLE; %>
<% int sort_name = jp.groupsession.v2.rng.RngConst.RNG_SORT_NAME; %>
<% int sort_appl = jp.groupsession.v2.rng.RngConst.RNG_SORT_DATE; %>
<% int sort_jyusin = jp.groupsession.v2.rng.RngConst.RNG_SORT_JYUSIN; %>
<% int sort_kakunin = jp.groupsession.v2.rng.RngConst.RNG_SORT_KAKUNIN; %>
<% int sort_touroku = jp.groupsession.v2.rng.RngConst.RNG_SORT_TOUROKU; %>
<% int order_asc = jp.groupsession.v2.rng.RngConst.RNG_ORDER_ASC; %>
<% int order_desc = jp.groupsession.v2.rng.RngConst.RNG_ORDER_DESC; %>

<bean:define id="rngSortKey" name="rng010Form" property="rng010sortKey" type="java.lang.Integer" />
<bean:define id="rngOrder" name="rng010Form" property="rng010orderKey" type="java.lang.Integer" />

<!-- 一覧 -->
<table class="table-top">
  <tbody>
    <tr>
      <th class="js_tableTopCheck js_tableTopCheck-header cursor_p">
        <input type="checkbox" name="allChk"/>
      </th>
      <%
        String sortSign = "";
        String nextOrder = "";
        jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
        String[] widthList = {"w85", "w15"};
        String[] titleList = {gsMsg.getMessage(request, "cmn.title"), gsMsg.getMessage(request, "rng.37")};
        int[] sortKeyList = {1, 5};
        for (int titleIdx = 0; titleIdx < titleList.length; titleIdx++) {
          if (rngSortKey.intValue() == sortKeyList[titleIdx]) {
            if (rngOrder.intValue() == 1) {
              sortSign="▼"; nextOrder = "0";
            } else {
              sortSign="▲"; nextOrder = "1";
            }
          } else {
            nextOrder = "0"; sortSign = "";
          }
      %>
      <th class="cursor_p <%= widthList[titleIdx] %>">
        <a class="table_headerSort-top" href="#" onClick="clickTitle(<%= String.valueOf(sortKeyList[titleIdx]) %>, <%= nextOrder %>);">
          <%= titleList[titleIdx] %><span class="classic-display"><%= sortSign %></span>
          <span class="original-display txt_m">
            <% if (sortSign.equals("▼")) {%>
              <i class="icon-sort_down"></i>
            <%} else if (sortSign.equals("▲")) { %>
              <i class="icon-sort_up"></i>
            <% } %>
          </span>
        </a>
      </th>
      <% } %>
    </tr>
    <logic:notEmpty name="rng010Form" property="rngDataList">
      <% String apprMode = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_APPRMODE_APPR); %>
        <logic:iterate id="rngData" name="rng010Form" property="rngDataList" indexId="idx">
          <tr class="js_listHover cursor_p" id="<bean:write name="rngData" property="rngSid" />" name="<%= apprMode %>">
          <!-- 削除チェック -->
          <td class="txt_c js_tableTopCheck">
            <html:multibox name="rng010Form" property="rng010DelSidList">
              <bean:write name="rngData" property="rngSid" />
            </html:multibox>
          </td>
          <!-- タイトル -->
          <td class="js_listClick_draft cl_linkDef">
            <bean:write name="rngData" property="rngTitle" />
          </td>
          <!-- 申請日 -->
          <td class="txt_c no_w js_listClick_draft">
            <bean:write name="rngData" property="strMakeDate" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </tbody>
</table>