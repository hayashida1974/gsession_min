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

<%
  String notSbt   = String.valueOf(jp.groupsession.v2.adr.GSConstAddress.NOT_SYUBETU);
%>
<%
  String targetTitle   = String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_TARGET_TITLE);
  String targetBiko    = String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_TARGET_BIKO);
%>
<%
  String keyWordAnd    = String.valueOf(jp.groupsession.v2.adr.GSConstAddress.KEY_WORD_KBN_AND);
  String keyWordOr     = String.valueOf(jp.groupsession.v2.adr.GSConstAddress.KEY_WORD_KBN_OR);
  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
  String markOther    = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_OTHER);
  String markTel      = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_TEL);
  String markMail     = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_MAIL);
  String markWeb      = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_WEB);
  String markMeeting  = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_MEETING);
  java.util.HashMap imgMapClassic = new java.util.HashMap();
  String msgTel = gsMsg.getMessage(request, "cmn.phone");
  String msgMail = gsMsg.getMessage(request, "cmn.mail");
  String msgMeeting = gsMsg.getMessage(request, "address.28");
  String msgOther = gsMsg.getMessage(request, "cmn.other");

  imgMapClassic.put(markTel, "<img src=\"../common/images/classic/icon_call.png\" alt=" + "\"" + msgTel + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put(markMail, "<img src=\"../address/images/classic/icon_mail.gif\" alt=" + "\"" + msgMail + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put(markWeb, "<img src=\"../address/images/classic/icon_web.gif\" alt=\"Web\" border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put(markMeeting, "<img src=\"../common/images/classic/icon_syorui.gif\" alt=" + "\"" + msgMeeting + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put("none", "");

  java.util.HashMap imgMapOriginal = new java.util.HashMap();
  imgMapOriginal.put(markTel, "<img src=\"../common/images/original/icon_call.png\" alt=" + "\"" + msgTel + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put(markMail, "<img src=\"../common/images/original/icon_mail.png\" alt=" + "\"" + msgMail + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put(markWeb, "<img src=\"../address/images/original/icon_web.png\" alt=\"Web\" border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put(markMeeting, "<img src=\"../common/images/original/icon_siryo.png\" alt=" + "\"" + msgMeeting + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put("none", "");

  java.util.HashMap imgTextMap = new java.util.HashMap();
  imgTextMap.put(markTel, msgTel);
  imgTextMap.put(markMail, msgMail);
  imgTextMap.put(markWeb, "Web");
  imgTextMap.put(markMeeting, msgMeeting);
  imgTextMap.put(markOther, msgOther);
  imgTextMap.put("none", "&nbsp;");

%>
<table class="main_searchArea w100 bgC_lightGray mt0 borC_light">
  <tr>
    <td class="w50 pl5 pr5 pt5 txt_t">
      <table class="table-left">
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.name" />
          </th>
          <td class="w75">
            <span>
              <gsmsg:write key="cmn.lastname" />
              <html:text property="adr010unameSeiContact" maxlength="10" styleClass="w30 ml5" />
            </span>
            <span class="ml10">
              <gsmsg:write key="cmn.name3" />
              <html:text property="adr010unameMeiContact" maxlength="10" styleClass="w30 ml5" />
            </span>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.company.name" />
          </th>
          <td class="w75">
            <html:text property="adr010CoNameContact" maxlength="50" styleClass="w100" />
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="address.10" />
          </th>
          <td class="w75">
            <html:text property="adr010CoBaseNameContact" maxlength="50" styleClass="w100" />
          </td>
        </tr>
      </table>
    </td>
    <td class="w50 pl5 pr5 pt5 txt_t">
      <table class="table-left">
        <tr>
          <th class="w30">
            <gsmsg:write key="cmn.staff" />
          </th>
          <td class="w70">
            <html:select name="adr010Form" property="adr010tantoGroupContact" onchange="buttonPush('grpChange');" styleClass="w80">
              <logic:iterate id="gpBean" name="adr010Form" property="groupCmbList" scope="request">
                <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                <% boolean mygrpFlg = gpValue.indexOf("M") == -1; %>
                <% if (mygrpFlg) { %>
                  <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                <% } else { %>
                  <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                <% } %>
              </logic:iterate>
            </html:select>
            <button type="button" class="iconBtn-border ml5" value="" onclick="openGroupWindow(this.form.adr010tantoGroupContact, 'adr010tantoGroupContact', '0', 'grpChange')" id="adr010ContactGroupBtn2">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
            </button>
            <div class="mt5">
              <html:select name="adr010Form" property="adr010tantoUserContact" styleClass="w100">
              <logic:iterate id="user" name="adr010Form" property="userCmbList"   type="jp.groupsession.v2.usr.model.UsrLabelValueBean">
                <html:option styleClass="<%= user.getCSSClassNameOption() %>" value="<%=user.getValue() %>"><bean:write name="user" property="label" /></html:option>
              </logic:iterate>
            </html:select>
            </div>
          </td>
        </tr>
        <tr>
          <th class="w30">
            <gsmsg:write key="cmn.project" />
          </th>
          <td class="w70">
            <html:select name="adr010Form" styleClass="w100" property="adr010ProjectContact">
              <html:optionsCollection name="adr010Form" property="projectCmbList" value="value" label="label" />
            </html:select>
          </td>
        </tr>
        <tr>
          <th class="w30">
            <gsmsg:write key="cmn.attached" />
          </th>
          <td class="w70">
            <span class="verAlignMid">
              <html:radio name="adr010Form" property="adr010TempFilekbnContact" value="0" styleId="tempFreeCont"/><label for="tempFreeCont"><gsmsg:write key="cmn.specified.no" /></label>
              <html:radio styleClass="ml10" name="adr010Form" property="adr010TempFilekbnContact" value="1" styleId="tempExistCont"/><label for="tempExistCont"><gsmsg:write key="address.adr010.contact.5" /></label>
              <html:radio styleClass="ml10" name="adr010Form" property="adr010TempFilekbnContact" value="2" styleId="tempNotExistCont"/><label for="tempNotExistCont"><gsmsg:write key="cmn.no" /></label>
            </span>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td colspan="2" class="pl5 pr5">
      <table class="table-left">
        <tr>
          <th class="w15">
            <gsmsg:write key="cmn.date" />
          </th>
          <td class="w85" colspan="3">
            <span class="verAlignMid">
              <html:checkbox name="adr010Form" styleId="adr010dateNoKbn" property="adr010dateNoKbn" value="1" onclick="adr010DateKbn();" /><label for="adr010dateNoKbn"><gsmsg:write key="cmn.without.specifying" /></label>
              <html:text name="adr010Form" property="adr010SltFrContact" maxlength="10" styleClass="ml5 txt_c wp95 datepicker js_frDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <gsmsg:write key="tcd.142" />
              <html:text name="adr010Form" property="adr010SltToContact" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker"/>
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            </span>
          </td>
        </tr>
        <tr>
          <th class="w15">
            <gsmsg:write key="cmn.type" />
          </th>
          <td class="w85" colspan="3">
            <span class="verAlignMid">
              <html:radio styleId="adr010Mark_m1" name="adr010Form" property="adr010SyubetsuContact" value="<%= notSbt %>" /><label for="adr010Mark_m1"><gsmsg:write key="cmn.without.specifying" /></label>
              <html:radio styleClass="ml10 classic-display" styleId="adr010Mark_1c" name="adr010Form" property="adr010SyubetsuContact" value="<%= markTel %>" /><label for="adr010Mark_1c" class="classic-display verAlignMid"><%= (String) imgMapClassic.get(markTel) %><%= (String) imgTextMap.get(markTel) %></label>
              <html:radio styleClass="ml10 original-display" styleId="adr010Mark_1o" name="adr010Form" property="adr010SyubetsuContact" value="<%= markTel %>" /><label for="adr010Mark_1o" class="original-display verAlignMid"><%= (String) imgMapOriginal.get(markTel) %><%= (String) imgTextMap.get(markTel) %></label>

              <html:radio styleClass="ml10 classic-display" styleId="adr010Mark_2c" name="adr010Form" property="adr010SyubetsuContact" value="<%= markMail %>" /><label for="adr010Mark_2c" class="classic-display verAlignMid"><%= (String) imgMapClassic.get(markMail) %><%= (String) imgTextMap.get(markMail) %></label>
              <html:radio styleClass="ml10 original-display" styleId="adr010Mark_2o" name="adr010Form" property="adr010SyubetsuContact" value="<%= markMail %>" /><label for="adr010Mark_2o" class="original-display verAlignMid"><%= (String) imgMapOriginal.get(markMail) %><%= (String) imgTextMap.get(markMail) %></label>

              <html:radio styleClass="ml10 classic-display" styleId="adr010Mark_3c" name="adr010Form" property="adr010SyubetsuContact" value="<%= markWeb %>" /><label for="adr010Mark_3c" class="classic-display verAlignMid"><%= (String) imgMapClassic.get(markWeb) %><%= (String) imgTextMap.get(markWeb) %></label>
              <html:radio styleClass="ml10 original-display" styleId="adr010Mark_3o" name="adr010Form" property="adr010SyubetsuContact" value="<%= markWeb %>" /><label for="adr010Mark_3o" class="original-display verAlignMid"><%= (String) imgMapOriginal.get(markWeb) %><%= (String) imgTextMap.get(markWeb) %></label>

              <html:radio styleClass="ml10 classic-display" styleId="adr010Mark_4c" name="adr010Form" property="adr010SyubetsuContact" value="<%= markMeeting %>" /><label for="adr010Mark_4c" class="classic-display verAlignMid"><%= (String) imgMapClassic.get(markMeeting) %><%= (String) imgTextMap.get(markMeeting) %></label>
              <html:radio styleClass="ml10 original-display" styleId="adr010Mark_4o" name="adr010Form" property="adr010SyubetsuContact" value="<%= markMeeting %>" /><label for="adr010Mark_4o" class="original-display verAlignMid"><%= (String) imgMapOriginal.get(markMeeting) %><%= (String) imgTextMap.get(markMeeting) %></label>
              <html:radio styleClass="ml10" styleId="adr010Mark_0c" name="adr010Form" property="adr010SyubetsuContact" value="<%= markOther %>" /><label for="adr010Mark_0c"><gsmsg:write key="cmn.other" /></label>
            </span>
          </td>
        </tr>
        <tr>
          <th class="w15">
            <gsmsg:write key="cmn.keyword" />
          </th>
          <td class="w45">
            <html:text name="adr010Form" maxlength="50" property="adr010SearchWordContact" styleClass="w100"/>
            <div class="verAlignMid mt5">
              <html:radio name="adr010Form" property="adr010KeyWordkbnContact" value="<%= keyWordAnd %>" styleId="keyKbn_01" /><label for="keyKbn_01"><gsmsg:write key="cmn.contains.all.and" /></label>
              <html:radio styleClass="ml10" name="adr010Form" property="adr010KeyWordkbnContact" value="<%= keyWordOr %>" styleId="keyKbn_02" /><label for="keyKbn_02"><gsmsg:write key="cmn.orcondition" /></label>
            </div>
          </td>
          <th class="w15">
            <gsmsg:write key="cmn.search2" />
          </th>
          <td class="w25">
            <span class="verAlignMid">
              <html:multibox styleId="search_scope_01" name="adr010Form" property="adr010SearchTargetContact" value="<%= targetTitle %>" /><label for="search_scope_01"><gsmsg:write key="cmn.title" /></label>
              <html:multibox styleClass="ml10" styleId="search_scope_02" name="adr010Form" property="adr010SearchTargetContact" value="<%= targetBiko %>" /><label for="search_scope_02"><gsmsg:write key="cmn.memo" /></label>
            </span>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td class="txt_c pb10" colspan ="2">
      <button type="button" value="<gsmsg:write key="cmn.search" />" onClick='buttonPush("search");' class="baseBtn">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
        <gsmsg:write key="cmn.search" />
      </button>
    </td>
  </tr>
</table>