<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<% String help = jp.groupsession.v2.cmn.GSConst.HELPURL; %>
<% boolean newTheme = jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>
<!-- HEADER -->
<logic:notEmpty name="<%= help %>">
  <%if (!newTheme) { %>
  <div class="txt_r">
  <!--a href="<bean:write name="<%= help %>" />" target="_blank"><img src="../common/images/q.gif" alt="<gsmsg:write key="cmn.help" />" border="0"></a></div -->
  <button class="baseBtn" value="<gsmsg:write key="cmn.help" />" onclick="return openHelp();"><img src="../common/images/classic/btn_help.png" alt="<gsmsg:write key="cmn.help" />"><i class="fas fa-question-circle"></i><gsmsg:write key="cmn.help" /></button>
  </div>
  <%} %>

  <%if (newTheme) { %>
  <!--a href="<bean:write name="<%= help %>" />" target="_blank"><img src="../common/images/q.gif" alt="<gsmsg:write key="cmn.help" />" border="0"></a></div -->
  <button class="baseBtn" value="<gsmsg:write key="cmn.help" />" onclick="return openHelp();"><img src="../common/images/classic/btn_help.png" alt="<gsmsg:write key="cmn.help" />"><i class="fas fa-question-circle"></i><gsmsg:write key="cmn.help" /></button>
  <%} %>

<script language="JavaScript">
<!--
function openHelp(){

  var urlStr = '<bean:write name="<%= help %>" filter="false" />';
  helpPrm = document.getElementsByName("helpPrm");

  if (helpPrm.length > 0) {
    for (i = 0; i < helpPrm.length; i++) {
      urlStr = urlStr + helpPrm[i].value;
    }
  }

  window.open(urlStr, 'help', '');
  return false;
}

// -->
</script>


</logic:notEmpty>