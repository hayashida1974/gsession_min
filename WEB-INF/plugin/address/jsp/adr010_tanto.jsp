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
<table class="main_searchArea w100 bgC_lightGray mt0 borC_light">
  <tr>
    <td class="p5">
      <table class="table-left">
        <tr>
          <th class="w25 no_w">
            <gsmsg:write key="cmn.staff" />
          </th>
          <td class="w75">
            <html:select name="adr010Form" property="adr010tantoGroup" onchange="buttonPush('grpChange');" styleClass="w70">
              <logic:notEmpty name="adr010Form" property="groupCmbList" scope="request">
                <logic:iterate id="gpBean" name="adr010Form" property="groupCmbList" scope="request">
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
            <logic:notEqual name="adr010Form" property="adr010webmail" value="1">
              <button type="button" class="iconBtn-border ml5" value="" onClick="openGroupWindow(this.form.adr010tantoGroup, 'adr010tantoGroup', '0', 'grpChange')" id="adr010TantoGroupBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
              </button>
            </logic:notEqual>
            <div class="mt5">
              <html:select name="adr010Form" property="adr010tantoUser" styleClass="w70">
                <logic:iterate id="user" name="adr010Form" property="userCmbList"   type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
                  <html:option styleClass="<%=user.getCSSClassNameOption()%>" value="<%=user.getValue() %>"><bean:write name="user" property="label" /></html:option>
                </logic:iterate>
              </html:select>
            </div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td class="txt_c pb10">
      <button type="button" value="<gsmsg:write key="cmn.search" />" onClick='buttonPush("search");' class="baseBtn">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <gsmsg:write key="cmn.search" />
      </button>
    </td>
  </tr>
</table>