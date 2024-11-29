<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html:hidden property="adr010cmdMode" />
<html:hidden property="adr010searchFlg" />
<html:hidden property="adr010EditAdrSid" />
<html:hidden property="adr010orderKey" />
<html:hidden property="adr010sortKey" />
<html:hidden property="adr010page" />
<html:hidden property="adr010pageTop" />
<html:hidden property="adr010pageBottom" />
<html:hidden property="adr010code" />
<html:hidden property="adr010coName" />
<html:hidden property="adr010coNameKn" />
<html:hidden property="adr010coBaseName" />
<html:hidden property="adr010atiSid" />
<html:hidden property="adr010tdfk" />
<html:hidden property="adr010biko" />
<html:hidden property="adr010svCode" />
<html:hidden property="adr010svCoName" />
<html:hidden property="adr010svCoNameKn" />
<html:hidden property="adr010svCoBaseName" />
<html:hidden property="adr010svAtiSid" />
<html:hidden property="adr010svTdfk" />
<html:hidden property="adr010svBiko" />
<html:hidden property="adr010SearchComKana" />
<html:hidden property="adr010svSearchComKana" />
<html:hidden property="adr010SearchKana" />
<html:hidden property="adr010svSearchKana" />
<html:hidden property="adr010tantoGroup" />
<html:hidden property="adr010tantoUser" />
<html:hidden property="adr010svTantoGroup" />
<html:hidden property="adr010svTantoUser" />
<html:hidden property="adr010unameSei" />
<html:hidden property="adr010unameMei" />
<html:hidden property="adr010unameSeiKn" />
<html:hidden property="adr010unameMeiKn" />
<html:hidden property="adr010detailCoName" />
<html:hidden property="adr010syozoku" />
<html:hidden property="adr010position" />
<html:hidden property="adr010mail" />
<html:hidden property="adr010detailTantoGroup" />
<html:hidden property="adr010detailTantoUser" />
<html:hidden property="adr010detailAtiSid" />
<html:hidden property="adr010svUnameSei" />
<html:hidden property="adr010svUnameMei" />
<html:hidden property="adr010svUnameSeiKn" />
<html:hidden property="adr010svUnameMeiKn" />
<html:hidden property="adr010svDetailCoName" />
<html:hidden property="adr010svSyozoku" />
<html:hidden property="adr010svPosition" />
<html:hidden property="adr010svMail" />
<html:hidden property="adr010svDetailTantoGroup" />
<html:hidden property="adr010svDetailTantoUser" />
<html:hidden property="adr010svDetailAtiSid" />
<html:hidden property="adr010tantoGroupContact" />
<html:hidden property="adr010tantoUserContact" />
<html:hidden property="adr010unameSeiContact" />
<html:hidden property="adr010unameMeiContact" />
<html:hidden property="adr010CoNameContact" />
<html:hidden property="adr010CoBaseNameContact" />
<html:hidden property="adr010ProjectContact" />
<html:hidden property="adr010TempFilekbnContact" />
<html:hidden property="adr010SltYearFrContact" />
<html:hidden property="adr010SltMonthFrContact" />
<html:hidden property="adr010SltDayFrContact" />
<html:hidden property="adr010SltYearToContact" />
<html:hidden property="adr010SltMonthToContact" />
<html:hidden property="adr010SltDayToContact" />
<html:hidden property="adr010SyubetsuContact" />
<html:hidden property="adr010SearchWordContact" />
<html:hidden property="adr010KeyWordkbnContact" />
<html:hidden property="adr010dateNoKbn" />
<html:hidden property="adr010svTantoGroupContact" />
<html:hidden property="adr010svTantoUserContact" />
<html:hidden property="adr010svUnameSeiContact" />
<html:hidden property="adr010svUnameMeiContact" />
<html:hidden property="adr010svCoNameContact" />
<html:hidden property="adr010svCoBaseNameContact" />
<html:hidden property="adr010svProjectContact" />
<html:hidden property="adr010SvTempFilekbnContact" />
<html:hidden property="adr010svSltYearFrContact" />
<html:hidden property="adr010svSltMonthFrContact" />
<html:hidden property="adr010svSltDayFrContact" />
<html:hidden property="adr010svSltYearToContact" />
<html:hidden property="adr010svSltMonthToContact" />
<html:hidden property="adr010svSltDayToContact" />
<html:hidden property="adr010svSyubetsuContact" />
<html:hidden property="adr010svSearchWordContact" />
<html:hidden property="adr010SvKeyWordkbnContact" />
<html:hidden property="adr010svdateNoKbn" />
<html:hidden property="adr010InitDspContactFlg" />
<html:hidden property="projectKbnSv" />
<html:hidden property="statusKbnSv" />
<html:hidden property="selectingProjectSv" />
<html:hidden property="projectKbn" />
<html:hidden property="statusKbn" />
<html:hidden property="selectingProject" />
<html:hidden property="adr010selectCategory" />

<html:hidden property="adr010CategorySetInitFlg" />

<%@ page import="jp.co.sjts.util.StringUtilHtml" %>
<%
  String listParamName = "adr010CategoryOpenFlg";
  String[] paramList = request.getParameterValues(listParamName);
  if (paramList != null) {
    for (int idx = 0; idx < paramList.length; idx++) {
%>
<input type="hidden" name="<%= listParamName %>" value="<%= StringUtilHtml.transToHTml(paramList[idx]) %>">
<%
     }
   }
%>