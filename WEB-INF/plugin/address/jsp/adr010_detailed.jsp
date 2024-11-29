<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.adr.GSConstAddress" %>
<%@ page import="jp.groupsession.v2.usr.GSConstUser"%>
<%@ page import="jp.groupsession.v2.adr.adr010.Adr010Const" %>
<table class="main_searchArea w100 bgC_lightGray mt0 borC_light">
  <tr>
    <td class="w50 p5 txt_t">
      <table class="table-left">
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.name" />
          </th>
          <td class="w75">
            <span>
              <gsmsg:write key="cmn.lastname" />
              <html:text property="adr010unameSei" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_SEI) %>" styleClass="w30 ml5" />
            </span>
            <span class="ml10">
              <gsmsg:write key="cmn.name3" />
              <html:text property="adr010unameMei" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_MEI) %>" styleClass="w30 ml5" />
            </span>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.name.kana" />
          </th>
          <td class="w75">
            <span>
              <gsmsg:write key="cmn.lastname" />
              <html:text property="adr010unameSeiKn" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_SEI_KN) %>" styleClass="w30 ml5" />
            </span>
            <span class="ml10">
              <gsmsg:write key="cmn.name3" />
              <html:text property="adr010unameMeiKn" maxlength="<%= String.valueOf(GSConstAddress.MAX_LENGTH_NAME_MEI_KN) %>" styleClass="w30 ml5" />
            </span>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.company.name" />
          </th>
          <td class="w75">
            <html:text property="adr010detailCoName" maxlength="50" styleClass="w100" />
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.affiliation" />
          </th>
          <td class="w75">
            <html:text property="adr010syozoku" maxlength="60" styleClass="w100" />
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.post" />
          </th>
          <td class="w75">
            <html:select name="adr010Form" property="adr010position" styleClass="w100">
              <html:optionsCollection name="adr010Form" property="positionCmbList" value="value" label="label" />
            </html:select>
          </td>
        </tr>
      </table>
    </td>
    <td class="w50 p5 txt_t">
      <table class="table-left">
        <tr>
          <th class="w25">
            <gsmsg:write key="address.96" />
          </th>
          <td class="w75">
            <html:text property="adr010mail" maxlength="<%=String.valueOf(GSConstUser.MAX_LENGTH_MAIL) %>" styleClass="w100" />
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.staff" />
          </th>
          <td class="w75">
            <html:select name="adr010Form" property="adr010detailTantoGroup" onchange="buttonPush('init');" styleClass="w80">
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
              <button type="button" class="iconBtn-border ml5" value="" onclick="openGroupWindow(this.form.adr010detailTantoGroup, 'adr010detailTantoGroup', '0', 'init')" id="adr010DetailedGroupBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
              </button>
            </logic:notEqual>
            <div class="mt5">
              <html:select name="adr010Form" property="adr010detailTantoUser" styleClass="w100">
                <logic:iterate id="user" name="adr010Form" property="userCmbList"   type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
                  <html:option styleClass="<%= user.getCSSClassNameOption() %>" value="<%=user.getValue() %>"><bean:write name="user" property="label" /></html:option>
                </logic:iterate>
              </html:select>
            </div>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="address.11" />
          </th>
          <td class="w75">
            <html:select name="adr010Form" property="adr010detailAtiSid" styleClass="w100">
              <html:optionsCollection name="adr010Form" property="atiCmbList" value="value" label="label" />
            </html:select>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td colspan="2" class="txt_c pb10">
      <button type="button" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');" class="baseBtn">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
        <gsmsg:write key="cmn.search" />
      </button>
    </td>
  </tr>
</table>