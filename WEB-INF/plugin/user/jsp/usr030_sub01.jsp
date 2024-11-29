<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<html:hidden property="usr030SearchKana" />
<bean:define id="nrow" value="0" type="java.lang.String" />
<table class="w100 bgC_lightGray borC_light border_top_none">
  <tr class="txt_c">
    <td class="w100 p5">
      <table class="bgC_tableCell display_inline">
        <logic:iterate id="existskn" name="usr030Form" property="usr030ekanas" scope="request">
          <bean:define id="row" name="existskn" property="row" type="java.lang.String" />
          <logic:notEqual name="row" value="<%= nrow %>">
            <logic:notEqual name="<%= nrow %>" value="0">
              </tr>
            </logic:notEqual>
            <% nrow=row; %>
            <tr class="fs_15">
          </logic:notEqual>
          <logic:equal name="row" value="<%= nrow %>">
            <logic:equal name="existskn" property="exists" value="true">
              <td class="bor1 wp80 hp40 js_listUsrClick cursor_p" data-kana="<bean:write name="existskn" property="kana" />">
                <div class="cl_linkDef fw_b td-hoverChange display_tbl_c wp80 hp40 txt_c txt_m">
                  <bean:write name="existskn" property="kana" />
                </div>
              </td>
            </logic:equal>
            <logic:notEqual name="existskn" property="exists" value="true">
              <td class="bor1 wp80 hp40">
                <span class="cl_fontBody">
                  <bean:write name="existskn" property="kana" />
                </span>
              </td>
            </logic:notEqual>
          </logic:equal>
        </logic:iterate>
        </tr>
      </table>
    </td>
  </tr>
</table>

