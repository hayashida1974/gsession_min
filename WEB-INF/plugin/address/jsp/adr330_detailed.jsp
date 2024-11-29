<%@page import="jp.groupsession.v2.usr.GSConstUser"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.adr.GSConstAddress" %>

<%
String beanName = request.getParameter("searchParamName");
%>
  <div class="bgC_lightGray p10 bor1">
    <table class="table-left">
      <tr>
        <th class="w10">
          <gsmsg:write key="cmn.name" />
        </th>
        <td class="w40">
          <gsmsg:write key="cmn.lastname" />
          <html:text property="<%=beanName + \".unameSei\" %>" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_SEI) %>" styleClass="wp150 ml5 mr10" />
          <gsmsg:write key="cmn.name3" />
          <html:text property="<%=beanName + \".unameMei\" %>" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_MEI) %>" styleClass="wp150 ml5" />
        </td>
        <th class="w10">
          <gsmsg:write key="address.96" />
        </th>
        <td class="w40">
          <html:text property="<%=beanName + \".mail\" %>" maxlength="<%=String.valueOf(GSConstUser.MAX_LENGTH_MAIL) %>" styleClass="wp300" />
        </td>
      </tr>
      <tr>
        <th class="w10">
          <gsmsg:write key="cmn.name.kana" />
        </th>
        <td class="w40">
          <gsmsg:write key="cmn.lastname" />
          <html:text property="<%=beanName + \".unameSeiKn\" %>" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_SEI_KN) %>" styleClass="wp150 ml5 mr10" />
          <gsmsg:write key="cmn.name3" />
          <html:text property="<%=beanName + \".unameMeiKn\" %>" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_MEI_KN) %>" styleClass="wp150 ml5" />
        </td>
        <th>
          <gsmsg:write key="cmn.staff" />
        </th>
        <td>
          <html:select property="<%=beanName + \".groupSidStr\" %>" onchange="buttonPush('init');" styleClass="wp300">
            <logic:notEmpty name="adr330Form" property="groupCmbList" scope="request">
              <logic:iterate id="gpBean" name="adr330Form" property="groupCmbList" scope="request">
                <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                <% boolean mygrpFlg = gpValue.indexOf("M") == -1; %>
                <% if (mygrpFlg) { %>
                  <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                <% } else { %>
                  <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                <% } %>
              </logic:iterate>
            </logic:notEmpty>
          </html:select>
          <button class="iconBtn-border mr10" type="button" id="adr010DetailedGroupBtn" value="&nbsp;&nbsp;" onclick="openGroupWindow(this.form.elements.namedItem('<%=beanName + ".groupSidStr" %>').item(0), 'elements.namedItem(\'<%=beanName + ".groupSidStr" %>\')', '0', 'init')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
            <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
          </button>
          <html:select  property="<%=beanName + \".user\" %>" styleClass="wp300">
            <html:optionsCollection name="adr330Form" property="userCmbList" value="value" label="label" />
          </html:select>
        </td>
      </tr>
      <tr>
        <th>
          <gsmsg:write key="cmn.company.name" />
        </th>
        <td>
          <html:text property="<%=beanName + \".coName\" %>" maxlength="50" styleClass="wp300" />
        </td>
        <th>
          <gsmsg:write key="address.11" />
        </th>
        <td>
          <html:select  property="<%=beanName + \".atiSid\" %>" styleClass="wp300">
            <html:optionsCollection name="adr330Form" property="atiCmbList" value="value" label="label" />
          </html:select>
        </td>
      </tr>
      <tr>
        <th>
          <gsmsg:write key="cmn.affiliation" />
        </th>
        <td colspan="3">
          <html:text property="<%=beanName + \".syozoku\" %>" maxlength="60" styleClass="wp300" />
        </td>
      </tr>
      <tr>
        <th>
          <gsmsg:write key="cmn.post" />
        </th>
        <td colspan="3">
          <html:select property="<%=beanName + \".position\" %>" styleClass="wp300">
            <html:optionsCollection property="positionCmbList" value="value" label="label" />
          </html:select>
        </td>
      </tr>
    </table>
    <div>
      <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');">
        <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_classicImg-display">
        <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_originalImg-display">
        <gsmsg:write key="cmn.search" />
      </button>
    </div>
  </div>