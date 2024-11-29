<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel"%>
<!-- 再描画用リクエストパラメータ -->
<bean:define id="usrgrpsel" name="UsrGrpSelDialogForm" property="usrgrpsel" type="UserGroupSelectModel" />
<gsform:usrgrpsel_dialogparams bean="<%=usrgrpsel %>" inDialog="in"></gsform:usrgrpsel_dialogparams>
<div>
  <logic:notEqual value="<%=String.valueOf(UserGroupSelectModel.FLG_MULTI_OFF) %>"  name="usrgrpsel" property="multiFlg">
    <gsform:usrgrpselect name="UsrGrpSelDialogForm" property="usrgrpsel" size="15" onchange="$(this).parent().parent().usrpicker();" ></gsform:usrgrpselect>
  </logic:notEqual>
  <logic:equal value="<%=String.valueOf(UserGroupSelectModel.FLG_MULTI_OFF) %>"  name="usrgrpsel" property="multiFlg">
    <gsform:grpselect name="UsrGrpSelDialogForm" property="usrgrpsel.group" onchange="$(this).parent().parent().usrpicker();"></gsform:grpselect>
    <input type="hidden" name="<%="usrgrpsel.selected(" + UserGroupSelectModel.KEY_DEFAULT + ")" %>" value="<%=usrgrpsel.getSelectedSingle() %>" />
    <table class="table-top w100" cellpadding="5" cellspacing="0">
      <logic:notEmpty name="usrgrpsel" property="allList">
        <% String checked = ""; %>
        <logic:iterate id="user" name="usrgrpsel" property="allList" indexId="idx" type="UsrLabelValueBean">
          <tr class="js_listHover cursor_p">
            <%
              checked = "";
              if (StringUtils.equals(usrgrpsel.getSelectedSingle(), user.getValue())) {
                checked = "bgC_select " ;
              }
              String selectEv = "";
              selectEv += " $(this).parent().parent().parent().prev().val($(this).attr('value'));";
              selectEv += " $( this ).parents('.usrgrpsel_dialog').siblings('.ui-dialog-buttonpane').find('button:eq(0)').click();";
            %>
            <td class="txt_l w100 <%=checked %>" onclick="<%=selectEv%>" value="<%=user.getValue()%>">
              <% if (user.getCSSClassNameOption() != "") { %>
                <span class="<%=user.getCSSClassNameOption() %>">
                  <bean:write name="user" property="label" />
                </span>
              <% } else { %>
                <span class="cl_linkDef">
                  <bean:write name="user" property="label" />
                </span>
              <% } %>
            </td>
          </tr>
        </logic:iterate>
      </logic:notEmpty>
    </table>
  </logic:equal>
</div>
