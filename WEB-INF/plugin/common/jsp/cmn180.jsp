<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<html:form action="/common/cmn180">
<input type="hidden" name="CMD" value="setting">
<input type="hidden" name="cmn150backPage" value="<%= String.valueOf(jp.groupsession.v2.cmn.cmn150.Cmn150Form.BACKPAGE_MAIN) %>">
<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>
<% String tableSpace = " ";%>
<logic:notEmpty name="cmn180Form" property="areaList">
  <logic:iterate id="weatherData" name="cmn180Form" property="areaList">
    <table class="table-top w100 mb0 <%= tableSpace %>">
      <tr>
        <th class="table_title-color txt_l w100">
          <img src="../common/images/classic/icon_menu_tenki.gif" class="mainPlugin_icon">
          <a href="#" onclick="window.open('<bean:write name="cmn180Form" property="cmn180WeekUrl" /><bean:write name="weatherData" property="areaId" />.html?date=<bean:write name="cmn180Form" property="cmn180Date" />');return false;">
            <bean:write name="weatherData" property="areaName" />
          </a>
          <span class="flo_r">
            <%if (originalTheme) { %>
              <button class="mainConfigBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="document.forms['cmn180Form'].submit();return false;">
                <gsmsg:write key="cmn.setting" />
              </button>
            <% } else { %>
                <input type="button" onclick="document.forms['cmn180Form'].submit();return false;" class="mainConfigBtn cursor_p" value="<gsmsg:write key="cmn.setting" />">
            <% } %>
          </span>
        </th>
      </tr>
      <tr>
        <td class="pb0">
          <iframe hspace="0" vspace="0" class="main_iflameWeather main_iframeColor" frameborder="no" src="<bean:write name="cmn180Form" property="cmn180Url" /><bean:write name="weatherData" property="areaId" />.html?date=<bean:write name="cmn180Form" property="cmn180Date" />&theme=<bean:write name="cmn180Form" property="cmn180themeSid" />"></iframe>
        </td>
      </tr>
    </table>
    <% tableSpace = "mt10"; %>
  </logic:iterate>
</logic:notEmpty>
</html:form>