<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:form action="/common/cmn240">
<input type="hidden" name="CMD" value="setting">
<input type="hidden" name="cmn150backPage" value="<%= String.valueOf(jp.groupsession.v2.cmn.cmn150.Cmn150Form.BACKPAGE_MAIN) %>">
<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>

<table class="table-top w100 mb0">
  <tr>
    <th class="table_title-color txt_l w100">
      <img src="../common/images/classic/icon_menu_news.gif" class="mainPlugin_icon">
      <a href="#" onclick="window.open('<bean:write name="cmn240Form" property="cmn240newsUrl" />index.html?time=<bean:write name="cmn240Form" property="cmn240nowTime" />');return false;">
        <gsmsg:write key="cmn.news" />
      </a>
      <span class="flo_r">
        <%if (originalTheme) { %>
          <button class="mainConfigBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="document.forms['cmn240Form'].submit();return false;">
            <gsmsg:write key="cmn.setting" />
          </button>
        <% } else { %>
            <input type="button" onclick="document.forms['cmn240Form'].submit();return false;" class="mainConfigBtn cursor_p" value="<gsmsg:write key="cmn.setting" />">
        <% } %>
      </span>
    </th>
  </tr>
  <tr>
    <td class="pb0">
      <iframe hspace="0" vspace="0" class="main_iflameNews main_iframeColor" frameborder="no" src="<bean:write name="cmn240Form" property="cmn240newsUrl" />gadget/index.html?time=<bean:write name="cmn240Form" property="cmn240nowTime" />&theme=<bean:write name="cmn240Form" property="cmn240themeSid" />"></iframe>
    </td>
  </tr>
</table>

</html:form>
