<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%-- ゴミ箱自動削除区分 --%>
<%
  String wadDustNo        = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_DUST_NO);
  String wadDustLogout    = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_DUST_LOGOUT);
  String wadDustAuto      = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_DUST_AUTO);
%>
<%-- 送信済み自動削除区分 --%>
<%
  String wadSendNo        = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_SEND_NO);
  String wadSendLogout    = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_SEND_LOGOUT);
  String wadSendAuto      = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_SEND_AUTO);
%>
<%-- 草稿自動削除区分 --%>
<%
  String wadDraftNo        = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_DRAFT_NO);
  String wadDraftLogout    = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_DRAFT_LOGOUT);
  String wadDraftAuto      = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_DRAFT_AUTO);
%>
<%-- 受信箱自動削除区分 --%>
<%
  String wadResvNo        = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_RESV_NO);
  String wadResvLogout    = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_RESV_LOGOUT);
  String wadResvAuto      = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_RESV_AUTO);
%>
<%-- 保管自動削除区分 --%>
<%
  String wadKeepNo        = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_KEEP_NO);
  String wadKeepLogout    = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_KEEP_LOGOUT);
  String wadKeepAuto      = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.WAD_KEEP_AUTO);
%>

<script src="../webmail/js/wml050.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/webmail/wml050" styleClass="js_delForm">
<input type="hidden" name="plgId" value="wml0">

<div class="wrapper w100 mrl_auto">
  <table class="table-left">
    <tr>
      <th>
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="cmn.receive" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="wml050Form" property="wml050resvKbn" value="<%= wadResvNo %>" styleId="wadResvNo" onclick="wmlDispState(this.form.wml050resvKbn, this.form.wml050resvYear, this.form.wml050resvMonth, this.form.wml050resvDay)" />
          <label for="wadResvNo"><gsmsg:write key="cmn.noset" /></label>
          <html:radio name="wml050Form" property="wml050resvKbn" styleClass="ml10" value="<%= wadResvLogout %>" styleId="wadResvLogout" onclick="wmlDispState(this.form.wml050resvKbn, this.form.wml050resvYear, this.form.wml050resvMonth, this.form.wml050resvDay)" />
          <label for="wadResvLogout"><gsmsg:write key="wml.wml050.02" /></label>
          <html:radio name="wml050Form" property="wml050resvKbn" styleClass="ml10" value="<%= wadResvAuto %>" styleId="wadResvAuto" onclick="wmlDispState(this.form.wml050resvKbn, this.form.wml050resvYear, this.form.wml050resvMonth, this.form.wml050resvDay)" />
          <label for="wadResvAuto"><gsmsg:write key="cmn.autodelete" /></label>
        </div>
        <div>
          <logic:notEmpty name="wml050Form" property="yearLabelList">
            <html:select styleClass="wml050resvYear ml20" property="wml050resvYear">
              <html:optionsCollection name="wml050Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="wml050Form" property="monthLabelList">
            <html:select styleClass="wml050resvMonth ml5" property="wml050resvMonth">
              <html:optionsCollection name="wml050Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="wml050Form" property="dayLabelList">
            <html:select styleClass="wml050resvDay ml5 mr5" property="wml050resvDay">
              <html:optionsCollection name="wml050Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="cmn.sent" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="wml050Form" property="wml050sendKbn" value="<%= wadSendNo %>" styleId="wadSendNo" onclick="wmlDispState(this.form.wml050sendKbn, this.form.wml050sendYear, this.form.wml050sendMonth, this.form.wml050sendDay)" />
          <label for="wadSendNo"><gsmsg:write key="cmn.noset" /></label><br>
          <html:radio name="wml050Form" property="wml050sendKbn" styleClass="ml10" value="<%= wadSendLogout %>" styleId="wadSendLogout" onclick="wmlDispState(this.form.wml050sendKbn, this.form.wml050sendYear, this.form.wml050sendMonth, this.form.wml050sendDay)" />
          <label for="wadSendLogout"><gsmsg:write key="wml.wml050.02" /></label><br>
          <html:radio name="wml050Form" property="wml050sendKbn" styleClass="ml10" value="<%= wadSendAuto %>" styleId="wadSendAuto" onclick="wmlDispState(this.form.wml050sendKbn, this.form.wml050sendYear, this.form.wml050sendMonth, this.form.wml050sendDay)" />
          <label for="wadSendAuto"><gsmsg:write key="cmn.autodelete" /></label>
        </div>
        <div>
          <logic:notEmpty name="wml050Form" property="yearLabelList">
            <html:select styleClass="wml050sendYear ml20" property="wml050sendYear">
              <html:optionsCollection name="wml050Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="wml050Form" property="monthLabelList">
            <html:select styleClass="wml050sendMonth ml5" property="wml050sendMonth">
              <html:optionsCollection name="wml050Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="wml050Form" property="dayLabelList">
            <html:select styleClass="wml050sendDay ml5 mr5" property="wml050sendDay">
              <html:optionsCollection name="wml050Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="cmn.draft" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="wml050Form" property="wml050draftKbn" value="<%= wadDraftNo %>" styleId="wadDraftNo" onclick="wmlDispState(this.form.wml050draftKbn, this.form.wml050draftYear, this.form.wml050draftMonth, this.form.wml050draftDay)" />
          <label for="wadDraftNo"><gsmsg:write key="cmn.noset" /></label>
          <html:radio name="wml050Form" property="wml050draftKbn" styleClass="ml10" value="<%= wadDraftLogout %>" styleId="wadDraftLogout" onclick="wmlDispState(this.form.wml050draftKbn, this.form.wml050draftYear, this.form.wml050draftMonth, this.form.wml050draftDay)" />
          <label for="wadDraftLogout"><gsmsg:write key="wml.wml050.02" /></label>
          <html:radio name="wml050Form" property="wml050draftKbn" styleClass="ml10" value="<%= wadDraftAuto %>" styleId="wadDraftAuto" onclick="wmlDispState(this.form.wml050draftKbn, this.form.wml050draftYear, this.form.wml050draftMonth, this.form.wml050draftDay)" />
          <label for="wadDraftAuto"><gsmsg:write key="cmn.autodelete" /></label>
        </div>
        <div>
          <logic:notEmpty name="wml050Form" property="yearLabelList">
            <html:select styleClass="wml050draftYear ml20" property="wml050draftYear">
              <html:optionsCollection name="wml050Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="wml050Form" property="monthLabelList">
            <html:select styleClass="wml050draftMonth ml5" property="wml050draftMonth">
              <html:optionsCollection name="wml050Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="wml050Form" property="dayLabelList">
            <html:select styleClass="wml050draftDay ml5 mr5" property="wml050draftDay">
              <html:optionsCollection name="wml050Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="cmn.trash" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="wml050Form" property="wml050dustKbn" value="<%= wadDustNo %>" styleId="wadDustNo" onclick="wmlDispState(this.form.wml050dustKbn, this.form.wml050dustYear, this.form.wml050dustMonth, this.form.wml050dustDay)" />
          <label for="wadDustNo"><gsmsg:write key="cmn.noset" /></label>
          <html:radio name="wml050Form" property="wml050dustKbn" styleClass="ml10" value="<%= wadDustLogout %>" styleId="wadDustLogout" onclick="wmlDispState(this.form.wml050dustKbn, this.form.wml050dustYear, this.form.wml050dustMonth, this.form.wml050dustDay)" />
          <label for="wadDustLogout"><gsmsg:write key="wml.wml050.02" /></label>
          <html:radio name="wml050Form" property="wml050dustKbn" styleClass="ml10" value="<%= wadDustAuto %>" styleId="wadDustAuto" onclick="wmlDispState(this.form.wml050dustKbn, this.form.wml050dustYear, this.form.wml050dustMonth, this.form.wml050dustDay)" />
          <label for="wadDustAuto"><gsmsg:write key="cmn.autodelete" /></label>
        </div>
        <div>
          <logic:notEmpty name="wml050Form" property="yearLabelList">
            <html:select styleClass="wml050dustYear ml20" property="wml050dustYear">
              <html:optionsCollection name="wml050Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="wml050Form" property="monthLabelList">
            <html:select styleClass="wml050dustMonth ml5" property="wml050dustMonth">
              <html:optionsCollection name="wml050Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="wml050Form" property="dayLabelList">
            <html:select styleClass="wml050dustDay ml5 mr5" property="wml050dustDay">
              <html:optionsCollection name="wml050Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="cmn.strage" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="wml050Form" property="wml050keepKbn" value="<%= wadKeepNo %>" styleId="wadKeepNo" onclick="wmlDispState(this.form.wml050keepKbn, this.form.wml050keepYear, this.form.wml050keepMonth, this.form.wml050keepDay)" />
          <label for="wadKeepNo"><gsmsg:write key="cmn.noset" /></label>
          <html:radio name="wml050Form" property="wml050keepKbn" styleClass="ml10" value="<%= wadKeepLogout %>" styleId="wadKeepLogout" onclick="wmlDispState(this.form.wml050keepKbn, this.form.wml050keepYear, this.form.wml050keepMonth, this.form.wml050keepDay)" />
          <label for="wadKeepLogout"><gsmsg:write key="wml.wml050.02" /></label>
          <html:radio name="wml050Form" property="wml050keepKbn" styleClass="ml10" value="<%= wadKeepAuto %>" styleId="wadKeepAuto" onclick="wmlDispState(this.form.wml050keepKbn, this.form.wml050keepYear, this.form.wml050keepMonth, this.form.wml050keepDay)" />
          <label for="wadKeepAuto"><gsmsg:write key="cmn.autodelete" /></label>
        </div>
        <div>
          <logic:notEmpty name="wml050Form" property="yearLabelList">
            <html:select styleClass="wml050keepYear ml20" property="wml050keepYear">
              <html:optionsCollection name="wml050Form" property="yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="wml050Form" property="monthLabelList">
            <html:select styleClass="wml050keepMonth ml5" property="wml050keepMonth">
              <html:optionsCollection name="wml050Form" property="monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="wml050Form" property="dayLabelList">
            <html:select styleClass="wml050keepDay ml5 mr5" property="wml050keepDay">
              <html:optionsCollection name="wml050Form" property="dayLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>