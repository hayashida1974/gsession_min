<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.man.GSConstMain" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<% String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY; %>
<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title><gsmsg:write key="main.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%=GSConst.VERSION_PARAM%>">
<!--  日本語化 -->
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%=GSConst.VERSION_PARAM%>">
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/components/file-preview/index.js?<%=GSConst.VERSION_PARAM%>"></script>

<script src="../main/js/man001.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../portal/js/ptlmain.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jtooltip_main.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../main/css/main.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<logic:notEmpty name="man001Form" property="screenInfoList">
  <logic:iterate id="screenMdl" name="man001Form" property="screenInfoList">
    <logic:notEmpty name="screenMdl" property="scriptPath">
      <script src="<bean:write name="screenMdl" property="scriptPath" />?<%= GSConst.VERSION_PARAM %>"></script>
    </logic:notEmpty>
    <logic:notEmpty name="screenMdl" property="stylePath">
      <link rel=stylesheet href='<bean:write name="screenMdl" property="stylePath" />?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    </logic:notEmpty>
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="man001Form" property="screenInfoList">
<script type="text/javascript">
<!--
$(function() {
<logic:iterate id="screenMdl" name="man001Form" property="screenInfoList" indexId="idx">
    var screenId<bean:write name="idx" /> = '';
    var url<bean:write name="idx" /> = "";
    screenId<bean:write name="idx" /> = '<bean:write name="screenMdl" property="pluginId" />' + '_' + '<bean:write name="screenMdl" property="id" />';
    url<bean:write name="idx" /> = '<bean:write name="screenMdl" property="screenUrl" />';
    if (url<bean:write name="idx" /> != '') {
        $.ajaxSetup({async:true});
        $.post(url<bean:write name="idx" />, function(data){
            if ($('#' + screenId<bean:write name="idx" />)[0] != null) {
<logic:equal name="screenMdl" property="loadScript" value="true">
                $('#' + screenId<bean:write name="idx" />).html(data);
</logic:equal>
<logic:notEqual name="screenMdl" property="loadScript" value="true">
                $('#' + screenId<bean:write name="idx" />)[0].innerHTML = data;
</logic:notEqual>
            }
        });
    }
</logic:iterate>
});

<logic:notEqual name="man001Form" property="man001Reload" value="0">
    var reloadinterval = <bean:write name="man001Form" property="man001Reload" />;
    setTimeout("buttonPush('reload')",reloadinterval);
</logic:notEqual>
-->
</script>
</logic:notEmpty>

</head>

<logic:empty name="man001Form" property="screenInfoList">
  <body onunload="windowClose();">
</logic:empty>

<logic:notEmpty name="man001Form" property="screenInfoList">
  <body onload="initArea();" onunload="windowClose();">
</logic:notEmpty>

<jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp"  />

<!-- BODY -->

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../main/images/classic/header_main.png">
      <img class="header_pluginImg" src="../main/images/original/header_main.png">
    </li>
    <li><gsmsg:write key="cmn.main" /></li>
    <%if (originalTheme) { %>
      <li class="ml20">
    <% } else { %>
      <li>
    <% } %>
      <% boolean smailUseFlg = false; %>
      <logic:equal name="man001Form" property="smailUseOk" value="<%= String.valueOf(GSConstMain.PLUGIN_USE) %>">
        <% smailUseFlg = true; %>
      </logic:equal>
      <% if (smailUseFlg) { %>
        <%if (originalTheme) { %>
          <button class="baseBtn wp110" value="<gsmsg:write key="cmn.send.shortmail" />" onclick="location.href='../smail/sml010.do?sml010scriptFlg=1&sml010scriptKbn=2'">
            <img class="btn_originalImg-display" src="../smail/images/original/icon_ms.png" alt="<gsmsg:write key="cmn.send.shortmail" />">
            <gsmsg:write key="cmn.send.shortmail" />
          </button>
        <% } else { %>
          <a href="../smail/sml010.do?sml010scriptFlg=1&sml010scriptKbn=2" class="ml20 fs_11">
            <img alt="<gsmsg:write key="cmn.send.shortmail" />" src="../smail/images/classic/icon_ms.gif">
            <gsmsg:write key="cmn.send.shortmail" />
          </a>
        <% } %>
      <% } %>

      <% boolean projectUseFlg = false; %>
      <logic:equal name="man001Form" property="projectUseOk" value="<%= String.valueOf(GSConstMain.PLUGIN_USE) %>">
        <% projectUseFlg = true; %>
      </logic:equal>
      <% if (projectUseFlg) { %>
        <%if (originalTheme) { %>
          <button class="baseBtn" value="<gsmsg:write key="main.6" />" onclick="location.href='../project/prj050.do?CMD=addTodo'">
            <img class="btn_originalImg-display" src="../project/images/original/icon_add_todo_main.png" alt="<gsmsg:write key="main.6" />">
            <gsmsg:write key="main.6" />
          </button>
        <% } else { %>
          <a href="../project/prj050.do?CMD=addTodo" class="ml20 fs_11">
            <img alt="<gsmsg:write key="main.6" />" src="../project/images/classic/icon_add_todo.gif">
            <gsmsg:write key="main.6" />
          </a>
        <% } %>
      <% } %>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.reload" />" onClick="buttonPush2('reload');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
          <gsmsg:write key="cmn.reload" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w100">
  <logic:notEmpty name="man001Form" property="portalList">
    <div class="portalTabList">
      <ul>
        <logic:iterate id="ptlMdl" name="man001Form" property="portalList">
          <logic:equal name="ptlMdl" property="ptlSid" value="0">
            <li class="portalTabList_tab-forcus cl_fontOutline">
              <span>
                <bean:write name="ptlMdl" property="ptlName" />
              </span>
            </li>
          </logic:equal>
          <logic:notEqual name="ptlMdl" property="ptlSid" value="0">
            <li class="portalTabList_tab-nonforcus cl_fontOutline">
              <a href="#!" class="ul-hov" onclick="return pushPortalTab('portal', <bean:write name="ptlMdl" property="ptlSid" />)">
                <bean:write name="ptlMdl" property="ptlName" />
              </a>
            </li>
          </logic:notEqual>
        </logic:iterate>
      </ul>
    </div>
  </logic:notEmpty>
  <html:form action="/main/man001">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="cmd" />
    <html:hidden property="sch010SelectUsrSid" />
    <html:hidden property="sch010SelectUsrKbn" />
    <html:hidden property="ptlMainSid" />
    <input type="hidden" name="ptlBackPage" value="">
    <html:hidden property="man001mainStatus" />
    <html:hidden property="man001layoutDefFlg" />
    <html:hidden property="man001areaLeft" />
    <html:hidden property="man001areaRight" />
    <html:hidden property="man001areaTop" />
    <html:hidden property="man001areaBottom" />
    <html:hidden property="man001areaCenter" />
  </html:form>
  <logic:equal name="man001Form" property="man001layoutDefFlg" value="true">
    <table class="w100">
      <tr>
        <!-- 左 -->
        <td id="mainScreenListLeft" class="js_column pr10 w75 txt_t">
          <logic:notEmpty name="man001Form" property="screenInfoListLeft">
            <logic:iterate id="screenMdl" name="man001Form" property="screenInfoListLeft">
              <bean:define id="pluginId" name="screenMdl" property="pluginId" />
              <bean:define id="id" name="screenMdl" property="id" />
              <% String positionId = id.toString(); %>
              <div id="<%= positionId %>">
                <% if (id.equals(GSConstMain.MAINSCREENID_INFORMATION)) { %>
                  <jsp:include page="/WEB-INF/plugin/main/jsp/man001_information.jsp" />
                <% } else if (id.equals(GSConstMain.MAINSCREENID_DAYTIME)) { %>
                  <jsp:include page="/WEB-INF/plugin/common/jsp/daytime.jsp" />
                <% } else if (id.equals(GSConstMain.MAINSCREENID_LASTLOGIN)) { %>
                  <jsp:include page="/WEB-INF/plugin/common/jsp/lastlogin.jsp" />
                <% } else { %>
                  <% String screenId = pluginId + "_" + id; %>
                  <span id="<%= screenId %>"><bean:write name="screenMdl" property="pluginName" /><gsmsg:write key="cmn.reload" />...</span>
                <% } %>
              </div>
            </logic:iterate>
          </logic:notEmpty>
        </td>
        <!-- 右 -->
        <td id="mainScreenListRight" class="js_column2 pl10 w25 txt_t">
          <logic:notEmpty name="man001Form" property="screenInfoListRight">
            <logic:iterate id="screenMdl" name="man001Form" property="screenInfoListRight">
              <bean:define id="pluginId" name="screenMdl" property="pluginId" />
              <bean:define id="id" name="screenMdl" property="id" type="java.lang.String" />
              <% String positionId = id.toString(); %>
              <div id="<%= positionId %>">
                <% if (id.equals(GSConstMain.MAINSCREENID_INFORMATION)) { %>
                  <jsp:include page="/WEB-INF/plugin/main/jsp/man001_information.jsp" />
                <% } else if (id.equals(GSConstMain.MAINSCREENID_DAYTIME)) { %>
                  <jsp:include page="/WEB-INF/plugin/common/jsp/daytime.jsp" />
                <% } else if (id.equals(GSConstMain.MAINSCREENID_LASTLOGIN)) { %>
                  <jsp:include page="/WEB-INF/plugin/common/jsp/lastlogin.jsp" />
                <% } else { %>
                  <% String screenId = pluginId + "_" + id; %>
                  <span id="<%= screenId %>"><bean:write name="screenMdl" property="pluginName" /><gsmsg:write key="cmn.reload" /></span>
                <% } %>
              </div>
            </logic:iterate>
          </logic:notEmpty>
        </td>
      </tr>
    </table>
  </logic:equal>
  <logic:notEqual name="man001Form" property="man001layoutDefFlg" value="true">
    <table class="w100">
      <!-- 上 -->
      <logic:equal name="man001Form" property="man001areaTop" value="0">
        <tr>
          <td id="mainScreenListTop" class="js_column3 w100">
            <logic:notEmpty name="man001Form" property="screenInfoListTop">
              <logic:iterate id="screenMdl" name="man001Form" property="screenInfoListTop">
                <bean:define id="pluginId" name="screenMdl" property="pluginId" />
                <bean:define id="id" name="screenMdl" property="id" type="java.lang.String" />
                <% String positionId = id.toString(); %>
                <div id="<%= positionId %>">
                  <% if (id.equals(GSConstMain.MAINSCREENID_INFORMATION)) { %>
                    <jsp:include page="/WEB-INF/plugin/main/jsp/man001_information.jsp" />
                  <% } else if (id.equals(GSConstMain.MAINSCREENID_DAYTIME)) { %>
                    <jsp:include page="/WEB-INF/plugin/common/jsp/daytime.jsp" />
                  <% } else if (id.equals(GSConstMain.MAINSCREENID_LASTLOGIN)) { %>
                    <jsp:include page="/WEB-INF/plugin/common/jsp/lastlogin.jsp" />
                  <% } else { %>
                    <% String screenId = pluginId + "_" + id; %><span id="<%= screenId %>">
                    <bean:write name="screenMdl" property="pluginName" /><gsmsg:write key="cmn.reload" /></span>
                  <% } %>
                </div>
              </logic:iterate>
            </logic:notEmpty>
            <logic:empty name="man001Form" property="screenInfoListTop">&nbsp;</logic:empty>
          </td>
        </tr>
      </logic:equal>
      <tr>
        <td>
          <table class="w100">
            <tr>
              <!-- 左-->
              <logic:equal name="man001Form" property="man001areaLeft" value="0">
                <td id="mainScreenListLeft" class="js_column3 txt_t pr10 mwp100">
                  <logic:notEmpty name="man001Form" property="screenInfoListLeft">
                    <logic:iterate id="screenMdl" name="man001Form" property="screenInfoListLeft">
                      <bean:define id="pluginId" name="screenMdl" property="pluginId" />
                      <bean:define id="id" name="screenMdl" property="id" type="java.lang.String" />
                      <% String positionId = id.toString(); %>
                      <div id="<%= positionId %>">
                        <% if (id.equals(GSConstMain.MAINSCREENID_INFORMATION)) { %>
                          <jsp:include page="/WEB-INF/plugin/main/jsp/man001_information.jsp" />
                        <% } else if (id.equals(GSConstMain.MAINSCREENID_DAYTIME)) { %>
                          <jsp:include page="/WEB-INF/plugin/common/jsp/daytime.jsp" />
                        <% } else if (id.equals(GSConstMain.MAINSCREENID_LASTLOGIN)) { %>
                          <jsp:include page="/WEB-INF/plugin/common/jsp/lastlogin.jsp" />
                        <% } else { %><% String screenId = pluginId + "_" + id; %>
                          <span id="<%= screenId %>"><bean:write name="screenMdl" property="pluginName" /><gsmsg:write key="cmn.reload" /></span>
                        <% } %>
                      </div>
                    </logic:iterate>
                  </logic:notEmpty>
                  <logic:empty name="man001Form" property="screenInfoListLeft">&nbsp;</logic:empty>
                </td>
              </logic:equal>
              <!-- 中 -->
              <logic:equal name="man001Form" property="man001areaCenter" value="0">
                <td id="mainScreenListCenter" class="js_column3 txt_t plt_center mwp100">
                  <logic:notEmpty name="man001Form" property="screenInfoListCenter">
                    <logic:iterate id="screenMdl" name="man001Form" property="screenInfoListCenter">
                      <bean:define id="pluginId" name="screenMdl" property="pluginId" />
                      <bean:define id="id" name="screenMdl" property="id" type="java.lang.String" />
                      <% String positionId = id.toString(); %>
                      <div id="<%= positionId %>">
                        <% if (id.equals(GSConstMain.MAINSCREENID_INFORMATION)) { %>
                          <jsp:include page="/WEB-INF/plugin/main/jsp/man001_information.jsp" />
                        <% } else if (id.equals(GSConstMain.MAINSCREENID_DAYTIME)) { %>
                          <jsp:include page="/WEB-INF/plugin/common/jsp/daytime.jsp" />
                        <% } else if (id.equals(GSConstMain.MAINSCREENID_LASTLOGIN)) { %>
                          <jsp:include page="/WEB-INF/plugin/common/jsp/lastlogin.jsp" />
                        <% } else { %>
                          <% String screenId = pluginId + "_" + id; %>
                          <span id="<%= screenId %>"><bean:write name="screenMdl" property="pluginName" /><gsmsg:write key="cmn.reload" /></span>
                        <% } %>
                      </div>
                    </logic:iterate>
                  </logic:notEmpty>
                  <logic:empty name="man001Form" property="screenInfoListCenter">&nbsp;</logic:empty>
                </td>
              </logic:equal>
              <!-- 右 -->
              <logic:equal name="man001Form" property="man001areaRight" value="0">
                <td id="mainScreenListRight" class="js_column3 txt_t pl10 mwp100">
                  <logic:notEmpty name="man001Form" property="screenInfoListRight">
                    <logic:iterate id="screenMdl" name="man001Form" property="screenInfoListRight">
                      <bean:define id="pluginId" name="screenMdl" property="pluginId" />
                      <bean:define id="id" name="screenMdl" property="id" type="java.lang.String" />
                      <% String positionId = id.toString(); %>
                      <div id="<%= positionId %>">
                        <% if (id.equals(GSConstMain.MAINSCREENID_INFORMATION)) { %>
                          <jsp:include page="/WEB-INF/plugin/main/jsp/man001_information.jsp" />
                        <% } else if (id.equals(GSConstMain.MAINSCREENID_DAYTIME)) { %>
                          <jsp:include page="/WEB-INF/plugin/common/jsp/daytime.jsp" />
                        <% } else if (id.equals(GSConstMain.MAINSCREENID_LASTLOGIN)) { %>
                          <jsp:include page="/WEB-INF/plugin/common/jsp/lastlogin.jsp" />
                        <% } else { %><% String screenId = pluginId + "_" + id; %>
                          <span id="<%= screenId %>"><bean:write name="screenMdl" property="pluginName" /><gsmsg:write key="cmn.reload" /></span>
                        <% } %>
                      </div>
                    </logic:iterate>
                  </logic:notEmpty>
                  <logic:empty name="man001Form" property="screenInfoListRight">&nbsp;</logic:empty>
                </td>
              </logic:equal>
              <logic:equal name="man001Form" property="man001areaRight" value="1">
                <td></td>
              </logic:equal>
            </tr>
          </table>
        </td>
      </tr>
      <!-- 下 -->
      <logic:equal name="man001Form" property="man001areaBottom" value="0">
        <tr>
          <td id="mainScreenListBottom" class="js_column3 txt_c txt_t w100">
            <logic:notEmpty name="man001Form" property="screenInfoListBottom">
              <logic:iterate id="screenMdl" name="man001Form" property="screenInfoListBottom">
                <bean:define id="pluginId" name="screenMdl" property="pluginId" />
                <bean:define id="id" name="screenMdl" property="id" type="java.lang.String" />
                <% String positionId = id.toString(); %>
                <div id="<%= positionId %>">
                  <% if (id.equals(GSConstMain.MAINSCREENID_INFORMATION)) { %>
                    <jsp:include page="/WEB-INF/plugin/main/jsp/man001_information.jsp" />
                  <% } else if (id.equals(GSConstMain.MAINSCREENID_DAYTIME)) { %>
                    <jsp:include page="/WEB-INF/plugin/common/jsp/daytime.jsp" />
                  <% } else if (id.equals(GSConstMain.MAINSCREENID_LASTLOGIN)) { %>
                    <jsp:include page="/WEB-INF/plugin/common/jsp/lastlogin.jsp" />
                  <% } else { %>
                    <% String screenId = pluginId + "_" + id; %><span id="<%= screenId %>">
                    <bean:write name="screenMdl" property="pluginName" /><gsmsg:write key="cmn.reload" /></span>
                  <% } %>
                </div>
              </logic:iterate>
            </logic:notEmpty>
            <logic:empty name="man001Form" property="screenInfoListBottom">&nbsp;</logic:empty>
          </td>
        </tr>
      </logic:equal>
    </table>
  </logic:notEqual>
  <logic:equal name="man001Form" property="man001ptlAdminFlg" value="true">
    <div class="mt20 txt_r">
      <button class="baseBtn" type="button" value="<gsmsg:write key="ptl.7" />" onClick="movePortalSetting();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_portal.png" alt="<gsmsg:write key="ptl.7" />">
        <img class="btn_originalImg-display" src="../portal/images/original/icon_portlet.png" alt="<gsmsg:write key="ptl.7" />">
        <gsmsg:write key="ptl.7" />
      </button>
    </div>
  </logic:equal>
</div>

<div class="txt_r mt20 p5 bor_t1 bor_b1">
  <form name="lockForm" method="post" action="#">
    <div class="verAlignMid">
      <gsmsg:write key="cmn.display.position" />：
      <input type="radio" name="lockFlg" value="1" checked="checked" id="lockFlg_01" onclick="destroySortable();"><label for="lockFlg_01"><gsmsg:write key="cmn.fixed3" /></label>
      <input class="ml10" type="radio" name="lockFlg" value="0" id="lockFlg_02" onclick="createSortable();"><label for="lockFlg_02"><gsmsg:write key="main.man001.3" />(<gsmsg:write key="main.man001.4" />)</label>
    </div>
  </form>
</div>
<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
</body>
</html:html>