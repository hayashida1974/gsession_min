<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<!DOCTYPE html>
<html:html>
  <head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <gsjsmsg:js filename="gsjsmsg.js"/>
    <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../ringi/js/rng200.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
    <title>GROUPSESSION <gsmsg:write key="rng.62" /> <gsmsg:write key="cmn.preferences" /></title>
  </head>
  <body>
    <html:form action="/ringi/rng200">
      <input type="hidden" name="CMD" value="">
      <%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
      <html:hidden property="backScreen"/>
      <input type="hidden" name="rng200Sid" value="">
      <!-- BODY -->
      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
      <div class="kanriPageTitle w80 mrl_auto">
        <ul>
          <li>
            <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
          </li>
          <li>
            <gsmsg:write key="cmn.admin.setting" />
          </li>
          <li class="pageTitle_subFont">
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.rng040.10" />
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonPush('add');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <gsmsg:write key="cmn.add" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backMenu');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.back" />
              </button>
            </div>
          </li>
        </ul>
      </div>
      <div class="wrapper w80 mrl_auto">
        <logic:messagesPresent message="false">
          <html:errors/>
        </logic:messagesPresent>
        <table class="table-top">
          <tr>
            <th class="w40 table_title-color">
              <gsmsg:write key="cmn.title" />
            </th>
            <th class="w30 table_title-color">
              <gsmsg:write key="rng.rng210.13" />
            </th>
            <th class="w30 table_title-color">
              <gsmsg:write key="rng.rng210.01" />
            </th>
          </tr>
          <logic:notEmpty name="rng200Form" property="rng200List">
            <logic:iterate id="rngMdl" name="rng200Form" property="rng200List" scope="request" indexId="idx">
              <%
                String lineNo = String.valueOf(idx.intValue());
                String dispFormat = "rng200DispList[" + lineNo + "]";
                String pattern = "rng200Pattern[" + lineNo + "]";
                String class_td ="";
                String class_td_title ="";
                String class_tr ="";
              %>
              <logic:notEqual name="rngMdl" property="rngSid" value = "0">
                <%
                  class_td ="js_listClick";
                  class_td_title = "cl_linkDef";
                  class_tr ="js_listHover cursor_p";
                %>
              </logic:notEqual>
              <tr class="<%= class_tr %>" id="<bean:write name="rngMdl" property="rngSid" />">
                <td class="<%= class_td %> <%= class_td_title %>">
                  <bean:write name="rngMdl" property="rngTitle" />
                </td>
                <td class="<%= class_td %>">
                  <bean:write name="rng200Form" property="<%= pattern %>" />
                </td>
                <td class="<%= class_td %>">
                  <bean:write name="rng200Form" property="<%= dispFormat %>" />
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </table>
        <div class="footerBtn_block">
          <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonPush('add');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backMenu');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </div>
      <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
    </html:form>
  </body>
</html:html>