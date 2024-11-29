<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.adr.GSConstAddress" %>
<%@ page import="jp.groupsession.v2.adr.adr010.Adr010Const" %>
<% Integer cmdMode = Integer.parseInt(request.getParameter("cmdMode"));%>
<bean:define id="sortKey" name="adr010Form" property="adr010sortKey" />
<bean:define id="orderKey" name="adr010Form" property="adr010orderKey" />
<% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(request);%>
<table id="demoTable" class="table-top demoTable mb0">
  <tr>
    <th class="js_tableTopCheck js_tableTopCheck-header js_mailFlg wp25">
      <input type="checkbox" name="adr010webAllCheck" onClick="chgCheckAll('adr010webAllCheck', 'adr010selectSid');">
    </th>
    <%--   プロジェクト --%>
    <% if (cmdMode.intValue() == Adr010Const.CMDMODE_PROJECT) {%>
      <th class="wp150 no_w">
        <%-- 氏名 --%>
        <a href="#" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.name" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.name" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
            <gsmsg:write key="cmn.name" />
          </logic:notEqual>
        </a>
      </th>
      <%-- 会社名 --%>
      <th class="wp150">
        <a href="#" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
            <gsmsg:write key="cmn.company.name" />
          </logic:notEqual>
        </a>
      </th>
      <%--電話番号--%>
      <th class="wp150">
        <gsmsg:write key="cmn.tel" />
      </th>
      <%--E-MAIL--%>
      <th>
        <gsmsg:write key="address.96" />
      </th>
    <% } else {%>
      <th class="wp150 no_w">
        <%--氏名--%>
        <a href="#" class="cl_fontOutlineLink" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.name" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.name" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
            <gsmsg:write key="cmn.name" />
          </logic:notEqual>
        </a>
        ／
        <%--役職--%>
        <a href="#" class="cl_fontOutlineLink" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_POSITION) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_POSITION) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.post" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.post" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.post" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.post" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_POSITION) %>">
            <gsmsg:write key="cmn.post" />
          </logic:notEqual>
        </a>
      </th>
      <th class="wp150">
        <%--会社名--%>
        <a href="#" class="cl_fontOutlineLink" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
            <gsmsg:write key="cmn.company.name" />
          </logic:notEqual>
        </a>
        ／
        <%--拠点--%>
        <a href="#" class="cl_fontOutlineLink" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="address.10" />▲</span>
              <span class="original-display"><gsmsg:write key="address.10" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="address.10" />▼</span>
              <span class="original-display"><gsmsg:write key="address.10" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>">
            <gsmsg:write key="address.10" />
          </logic:notEqual>
        </a>
      </th>
      <%--電話番号--%>
      <th class="wp150">
        <gsmsg:write key="cmn.tel" />
      </th>
      <%--E-MAIL--%>
      <th>
        <gsmsg:write key="address.96" />
      </th>
    <% }%>
  </tr>
</table>
    <table  class="table-top mt0 border_top_none">
      <logic:iterate id="detailMdl" name="adr010Form" property="detailList">
        <tr class="borderBlock-white border_top_none">
        <td class="js_tableTopCheck cursor_p wp25 txt_c p3">
          <% boolean existMail = false; %>
          <logic:notEmpty name="detailMdl" property="mail1"><% existMail = true; %></logic:notEmpty>
          <logic:notEmpty name="detailMdl" property="mail2"><% existMail = true; %></logic:notEmpty>
          <logic:notEmpty name="detailMdl" property="mail3"><% existMail = true; %></logic:notEmpty>
          <% if (existMail) { %>
            <html:multibox name="adr010Form" property="adr010selectSid"><bean:write name="detailMdl"  property="adrSid" /></html:multibox>
          <% } %>
        </td>
        <td class="wp150 fs_12 p3 lh140">
          <% if (cmdMode.intValue() != Adr010Const.CMDMODE_PROJECT) { %>
            <logic:notEmpty name="detailMdl" property="positionName" >
              <bean:write name="detailMdl" property="positionName" />
            </logic:notEmpty>
          <% } %>
          <%
            int dspCnt = 0;
            String mail = null;
            try {
              for(int i = 1; i <= 3; i++) {
                mail = "mail" + i;
                if (dspCnt == 0) {
          %>
                  <logic:notEmpty name="detailMdl" property="<%=mail%>">
                    <a href="#!" onClick="addAddress(0, <bean:write name="detailMdl"  property="adrSid" />, 'addUsrAtesaki'); return false;"><bean:write name="detailMdl" property="userName" /></a>
                    <% dspCnt++;%>
                  </logic:notEmpty>
          <%
                }
              }
            } catch(Exception e) {
            }
            if (dspCnt == 0) {
          %>
              <bean:write name="detailMdl" property="userName" />
          <%
            }
          %>
          <logic:notEmpty name="detailMdl" property="viewLabelName">
            <br>
            <span class="baseLabel ml0"><bean:write name="detailMdl" property="viewLabelName" /></span>
          </logic:notEmpty>
        </td>
        <%--会社名／拠点--%>
        <td class="wp150 fs_12 p3 lh140">
          <bean:write name="detailMdl" property="companyName" />
          <% if (cmdMode.intValue() != Adr010Const.CMDMODE_PROJECT) { %>
            <logic:notEmpty name="detailMdl" property="companyBaseName">
              <div>
                <bean:write name="detailMdl" property="companyBaseName" />
              </div>
            </logic:notEmpty>
          <% } %>
        </td>
        <td class="wp150 fs_12 p3 lh140">
          <logic:notEmpty name="detailMdl" property="tel1">
            <div>
              <bean:write name="detailMdl" property="tel1" />
              <logic:notEmpty name="detailMdl" property="telCmt1">
                <span class="ml5">(<bean:write name="detailMdl" property="telCmt1" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
          <logic:notEmpty name="detailMdl" property="tel2">
            <div>
              <bean:write name="detailMdl" property="tel2" />
              <logic:notEmpty name="detailMdl" property="telCmt2">
                <span class="ml5">(<bean:write name="detailMdl" property="telCmt2" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
          <logic:notEmpty name="detailMdl" property="tel3">
            <div>
              <bean:write name="detailMdl" property="tel3" />
              <logic:notEmpty name="detailMdl" property="telCmt3">
                <span class="ml5">(<bean:write name="detailMdl" property="telCmt3" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
        </td>
        <td class="fs_12 p3 lh140">
          <logic:notEmpty name="detailMdl" property="mail1">
            <div>
              <a href="#!" onClick="addAddressExt(0, <bean:write name="detailMdl" property="adrSid" />, 'addUsrAtesaki', 1); return false;"><bean:write name="detailMdl" property="mail1" /></a>
              <logic:notEmpty name="detailMdl" property="mailCmt1">
                <span class="ml5">(<bean:write name="detailMdl" property="mailCmt1" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
          <logic:notEmpty name="detailMdl" property="mail2">
            <div>
              <a href=""!" onClick="addAddressExt(0, <bean:write name="detailMdl" property="adrSid" />, 'addUsrAtesaki', 2); return false;"><bean:write name="detailMdl" property="mail2" /></a>
              <logic:notEmpty name="detailMdl" property="mailCmt2">
                <span class="ml5">(<bean:write name="detailMdl" property="mailCmt2" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
          <logic:notEmpty name="detailMdl" property="mail3">
            <div>
              <a href="#!" onClick="addAddressExt(0, <bean:write name="detailMdl" property="adrSid" />, 'addUsrAtesaki', 3); return false;"><bean:write name="detailMdl" property="mail3" /></a>
              <logic:notEmpty name="detailMdl" property="mailCmt3">
                <span class="ml5">(<bean:write name="detailMdl" property="mailCmt3" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
        </td>
      </tr>
    </logic:iterate>
    </table>
