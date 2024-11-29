<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="bbs.9" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>
<html:form action="/bulletin/bbsmain">
<input type="hidden" name="CMD" value="">

<!--掲示板-->
<logic:notEmpty name="bbsmainForm" property="threadList">
<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>
<table class="table-top table_col-even w100 mb0">
  <tr>
    <th class="txt_l table_title-color" colspan="4">
      <img class="mainPlugin_icon" src="../bulletin/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="bbs.bbsMain.5" />">
      <a class="main_pluginTitle" href="<bean:write name="bbsmainForm" property="bbsTopUrl" />">
        <gsmsg:write key="cmn.bulletin" />
      </a>
    </th>
  </tr>
  <tr>
    <th class="w30 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col"><gsmsg:write key="bbs.3" /></th>
    <th class="w30 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col"><gsmsg:write key="bbs.bbsMain.4" /></th>
    <th class="w25 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col"><gsmsg:write key="bbs.bbsMain.2" /></th>
    <th class="w15 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col"><gsmsg:write key="bbs.bbsMain.3" /></th>
  </tr>
  <logic:iterate id="thdMdl" name="bbsmainForm" property="threadList" indexId="index">
    <bean:define id="userJtkbn" name="thdMdl" property="userJkbn" />
    <tr>
      <td>
        <logic:equal name="thdMdl" property="newFlg" value="1">
          <%if (originalTheme) { %>
            <span class="labelNew"><gsmsg:write key="bbs.bbsMain.6" /></span>
          <% } else { %>
            <img class="classic-display" src="../bulletin/images/classic/icon_new.gif" alt="<gsmsg:write key="bbs.bbsMain.6" />">
          <% } %>
        </logic:equal>
        <% String titleClass = "cl_linkDef"; %>
        <logic:equal name="thdMdl" property="readFlg" value="1">
          <% titleClass = "cl_linkVisit"; %>
        </logic:equal>
        <a href="../bulletin/bbs060.do?bbs010forumSid=<bean:write name="thdMdl" property="bfiSid" />&bbsmainFlg=1"><span class="<%= titleClass %> cl_linkHoverChange"><bean:write name="thdMdl" property="bfiName" /></span></a>
      </td>
      <td>
        <a href="../bulletin/bbs060.do?bbs010forumSid=<bean:write name="thdMdl" property="bfiSid" />&threadSid=<bean:write name="thdMdl" property="btiSid" />&bbsmainFlg=1">
        <logic:equal name="thdMdl" property="bfiThreImportance" value="1">
          <!-- 重要 -->
          <img class="classic-display" src="../common/images/classic/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
          <img class="original-display" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
        </logic:equal>
        <logic:equal name="thdMdl" property="btsTempflg" value="1">
          <!-- 添付ファイル -->
          <img class="classic-display" src="../common/images/classic/icon_temp_file.gif" alt="<gsmsg:write key="cmn.attach.file" />">
          <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attach.file" />">
        </logic:equal>
        <span class="<%= titleClass %> cl_linkHoverChange"><bean:write name="thdMdl" property="btiTitle" /></a></span>
      </td>
      <td class="txt_c"><bean:write name="thdMdl" property="strWriteDate" /></td>
      <td>
        <bean:define id="cbGrpSid" name="thdMdl" property="grpSid" type="java.lang.Integer" />
        <% if (cbGrpSid.intValue() > 0) { %>
          <logic:equal name="thdMdl" property="grpJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
            <s><bean:write name="thdMdl" property="grpName" /></s>
          </logic:equal>
          <logic:notEqual name="thdMdl" property="grpJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
            <bean:write name="thdMdl" property="grpName" />
          </logic:notEqual>
        <% } else { %>
          <logic:equal name="thdMdl" property="userJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
            <s><bean:write name="thdMdl" property="userName" /></s>
          </logic:equal>
          <logic:notEqual name="thdMdl" property="userJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
            <logic:equal value="1" name="thdMdl" property="userYukoKbn">
              <span class="mukoUser"><bean:write name="thdMdl" property="userName" /></span>
            </logic:equal>
            <logic:notEqual value="1" name="thdMdl" property="userYukoKbn">
              <bean:write name="thdMdl" property="userName" />
            </logic:notEqual>
          </logic:notEqual>
        <% } %>
      </td>
    </tr>
  </logic:iterate>
</table>
</logic:notEmpty>
</html:form>

</body>
</html:html>