<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<table class="table-top tl0 w100 mt_10" cellpadding="5">
  <tr>
    <th align="center" class="w40 table_title-color">
      <gsmsg:write key="cmn.title" />
    </th>
    <th align="center" class="w25 table_title-color">
      <gsmsg:write key="rng.rng210.13" />
    </th>
    <th align="center" class="w25 table_title-color">
      <gsmsg:write key="rng.rng210.01" />
    </th>
  </tr>
  <logic:notEmpty name="rng200Form" property="rng200List">
    <logic:iterate id="rngMdl" name="rng200Form" property="rng200List" scope="request" indexId="idx">
      <% String lineNo = String.valueOf(idx.intValue()); %>
      <% String dispFormat = "rng200DispList[" + lineNo + "]"; %>
      <% String pattern = "rng200Pattern[" + lineNo + "]"; %>
      <bean:define id="mod" value="0" />
      <bean:define id="sid" name="rngMdl" property="rngSid" />
      <tr class="borderBlock-white js_listHover cursor_p" name="<%= sid %>">
        <td class="txt_l js_listClick">
          <span class="cl_linkDef cursor_p">
            <bean:write name="rngMdl" property="rngTitle" />
          </span>
        </td>
        <td class="txt_l js_listClick">
          <bean:write name="rng200Form" property="<%= pattern %>" />
        </td>
        <td class="txt_l js_listClick">
          <bean:write name="rng200Form" property="<%= dispFormat %>" />
        </td>
      </tr>
    </logic:iterate>
  </logic:notEmpty>
</table>
