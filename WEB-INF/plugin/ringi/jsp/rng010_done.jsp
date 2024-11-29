<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.rng.RngConst" %>

<% int sort_title = jp.groupsession.v2.rng.RngConst.RNG_SORT_TITLE; %>
<% int sort_name = jp.groupsession.v2.rng.RngConst.RNG_SORT_NAME; %>
<% int sort_appl = jp.groupsession.v2.rng.RngConst.RNG_SORT_DATE; %>
<% int sort_jyusin = jp.groupsession.v2.rng.RngConst.RNG_SORT_JYUSIN; %>
<% int sort_kakunin = jp.groupsession.v2.rng.RngConst.RNG_SORT_KAKUNIN; %>
<% int sort_touroku = jp.groupsession.v2.rng.RngConst.RNG_SORT_TOUROKU; %>
<% int order_asc = jp.groupsession.v2.rng.RngConst.RNG_ORDER_ASC; %>
<% int order_desc = jp.groupsession.v2.rng.RngConst.RNG_ORDER_DESC; %>

<% int status_settled = jp.groupsession.v2.rng.RngConst.RNG_STATUS_SETTLED; %>
<% int status_reject = jp.groupsession.v2.rng.RngConst.RNG_STATUS_REJECT; %>
<% int status_done = jp.groupsession.v2.rng.RngConst.RNG_STATUS_DONE; %>
<% int status_torisage = jp.groupsession.v2.rng.RngConst.RNG_STATUS_TORISAGE; %>

<bean:define id="rngSortKey" name="rng010Form" property="rng010sortKey" type="java.lang.Integer" />
<bean:define id="rngOrder" name="rng010Form" property="rng010orderKey" type="java.lang.Integer" />

<!-- 一覧 -->
<table class="table-top">
  <tbody>
    <tr>
      <logic:equal name="rng010Form" property="rng010delAuth" value="<%= String.valueOf(RngConst.RNG_DEL_OK) %>">
        <th class="js_tableTopCheck js_tableTopCheck-header cursor_p"> <input type="checkbox" name="allChk"/></th>
        <%
        String sortSign = "";
        String nextOrder = "";
        jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
        String[] widthList = {"","w45", "", "", ""};
        String[] titleList = {gsMsg.getMessage(request, "cmn.status"), gsMsg.getMessage(request, "cmn.title"), gsMsg.getMessage(request, "rng.47"), gsMsg.getMessage(request, "rng.application.date"), gsMsg.getMessage(request, "rng.105")};
        int[] sortKeyList = {6,  0,  1,  2, 4};
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
      </logic:equal>
      <logic:notEqual name="rng010Form" property="rng010delAuth" value="<%= String.valueOf(RngConst.RNG_DEL_OK) %>">
        <%
          String sortSign = "";
          String nextOrder = "";
          jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
          String[] widthList = {"","w55", "", "w15", "w15"};
          String[] titleList = {gsMsg.getMessage(request, "cmn.status"), gsMsg.getMessage(request, "cmn.title"), gsMsg.getMessage(request, "rng.47"), gsMsg.getMessage(request, "rng.application.date"), gsMsg.getMessage(request, "rng.105")};
          int[] sortKeyList = {6,  0,  1,  2, 4};
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
        <th class="<%= widthList[titleIdx] %>">
          <a class="table_headerSort-top" href="#" onClick="clickTitle(<%= String.valueOf(sortKeyList[titleIdx]) %>, <%= nextOrder %>);"><%= titleList[titleIdx] %><%= sortSign %></a>
        </th>
        <% } %>
      </logic:notEqual>
    </tr>

    <logic:notEmpty name="rng010Form" property="rngDataList">
      <% String apprMode = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_APPRMODE_APPR); %>
      <logic:iterate id="rngData" name="rng010Form" property="rngDataList" indexId="idx">
        <tr class="js_listHover cursor_p" id="<bean:write name="rngData" property="rngSid" />" name="<%= apprMode %>">
          <!-- 削除チェック -->
          <logic:equal name="rng010Form" property="rng010delAuth" value="<%= String.valueOf(RngConst.RNG_DEL_OK) %>">
          <td class="txt_c no_w js_tableTopCheck">
            <logic:equal name="rngData" property="delFlg" value="<%= String.valueOf(RngConst.RNG_DEL_OK) %>" >
              <html:multibox name="rng010Form" property="rng010DelSidList">
                <bean:write name="rngData" property="rngSid" />
              </html:multibox>
            </logic:equal>
          </td>
          </logic:equal>
          <!-- 状態 -->
          <bean:define id="rncstatus" name="rngData" property="rngStatus" />
          <% int status = ((Integer) rncstatus).intValue(); %>
          <td class="txt_c no_w js_listClick">
            <% if (status == status_settled) { %>
              <gsmsg:write key="rng.64" />
            <%} else if(status == status_reject){ %>
              <gsmsg:write key="rng.65" />
            <%} else if(status == status_done){ %>
              <gsmsg:write key="rng.rng030.06" />
            <%} else if(status == status_torisage){ %>
              <gsmsg:write key="rng.rng030.15" />
            <%} else {%>
              <gsmsg:write key="rng.48" />
            <%} %>
          </td>
          <!-- タイトル -->
          <td class="td_link cl_linkDef js_listClick">
            <bean:write name="rngData" property="rngTitle" />
          </td>
          <!-- 申請者 -->
          <td class="txt_c no_w js_listClick">
            <% String className = ""; %>
            <logic:equal name="rngData" property="usrUkoFlg" value="1">
              <% className = "mukoUserOption"; %>
            </logic:equal>
            <span class="<%= className %>">
              <logic:equal name="rngData" property="apprUserDelFlg" value="true">
                <del>
                  <bean:write name="rngData" property="apprUser" />
                </del>
              </logic:equal>
              <logic:notEqual name="rngData" property="apprUserDelFlg" value="true">
                <bean:write name="rngData" property="apprUser" />
              </logic:notEqual>
            </span>
          </td>
          <!-- 申請日 -->
          <td class="txt_c no_w js_listClick">
            <bean:write name="rngData" property="strRngAppldate" />
          </td>
          <!-- 最終確認日時 -->
          <td class="txt_c no_w js_listClick">
            <bean:write name="rngData" property="strLastManageDate" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </tbody>
</table>