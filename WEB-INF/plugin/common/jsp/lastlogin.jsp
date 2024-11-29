<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<!--前回ログイン時間-->

<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>

<table class="table-top w100 mb0">
  <tr>
    <th class="table_title-color txt_l w100">
      <img src="../main/images/classic/icon_menu_single_login.gif" class="mainPlugin_icon">
      <a href="../main/man050.do?man050Backurl=1">
        <gsmsg:write key="cmn.last.1" />
      </a>
    </th>
  </tr>
  <tr>
    <td class="txt_c">
      <logic:notEmpty name="man001Form" property="man001lstLogintime">
        <span class="fw_b"><bean:write name="man001Form" property="man001lstLogintime" /></span><br>
      </logic:notEmpty>
      <button class="baseBtn" type="button" value="<gsmsg:write key="cmn.last.2" />" onClick="location.href='../main/man050.do?man050Backurl=1';">
        <gsmsg:write key="cmn.last.2" />
      </button>
    </td>
  </tr>
</table>
