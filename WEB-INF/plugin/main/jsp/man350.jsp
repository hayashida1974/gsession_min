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
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man350.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="main.man350.1" /></title>
</head>
<!-- body -->
<body onload="initChgArea();">
<html:form action="/main/man350">
<input type="hidden" name="CMD" value="init">
<html:hidden property="man350init" />
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man350.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush2('man350edit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('man350back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20">
        <gsmsg:write key="main.man350.2" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio styleId="kbn_0" name="man350Form" property="man350kbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.MANSCREEN_LAYOUTKBN_ADMIN) %>" onclick="chgDefaultArea();" />
          <label for="kbn_0"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio styleId="kbn_1" styleClass="ml10" name="man350Form" property="man350kbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.MANSCREEN_LAYOUTKBN_USER) %>" onclick="hideAll();" />
          <label for="kbn_1"><gsmsg:write key="cmn.set.eachuser" /></label>
        </div>
      </td>
    </tr>
    <tr class="js_eventTr border_none">
      <th class="w20" id="layoutKbn1">
        <gsmsg:write key="ptl.5" />
      </th>
      <td class="w80" id="layoutKbn2">
        <div class="verAlignMid">
          <html:radio styleId="layout_0" name="man350Form" property="man350layout" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.MANSCREEN_LAYOUT_DEFAULT) %>" onclick="chgDefaultArea();" />
          <label for="layout_0"><gsmsg:write key="cmn.default" /></label>
          <html:radio styleId="layout_1" styleClass="ml10" name="man350Form" property="man350layout" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.MANSCREEN_LAYOUT_CUSTOM) %>" onclick="chgDefaultArea();" />
          <label for="layout_1"><gsmsg:write key="cmn.customize" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div id="checkarea">
    <div class="flo_l verAlignMid">
      <html:checkbox styleId="topId" name="man350Form" property="man350area3" value="1" onclick="chgArea('topId', 'Top');" />
      <label for="topId"><gsmsg:write key="ptl.ptl060.2" /></label>
      <html:checkbox styleId="leftId" styleClass="ml10" name="man350Form" property="man350area1" value="1" onclick="chgArea('leftId', 'Left');" />
      <label for="leftId"><gsmsg:write key="cmn.left" /></label>
      <html:checkbox styleId="centerId" styleClass="ml10" name="man350Form" property="man350area5" value="1" onclick="chgArea('centerId', 'Center');" />
      <label for="centerId"><gsmsg:write key="ptl.ptl060.3" /></label>
      <html:checkbox styleId="rightId" styleClass="ml10" name="man350Form" property="man350area2" value="1" onclick="chgArea('rightId', 'Right');" />
      <label for="rightId"><gsmsg:write key="cmn.right" /></label>
      <html:checkbox styleId="bottomId" styleClass="ml10" name="man350Form" property="man350area4" value="1" onclick="chgArea('bottomId', 'Bottom');" />
      <label for="bottomId"><gsmsg:write key="ptl.ptl060.4" /></label>
    </div>
  </div>
  <div id="defaultAreaList">
    <table class="w100">
      <tr>
        <td class="w75 hp300 bor3">
        </td>
        <td class="pr10">
        </td>
        <td class="w25 hp300 bor3">
        </td>
      </tr>
    </table>
  </div>
  <div id="areaList">
    <table class="w100">
      <tr>
        <td class="w100 hp90 p0" id="mainScreenListTop">
          <div class="w100 hp90 bor3">
          </div>
        </td>
      </tr>
      <tr>
        <td class="pt10" id="top_space">
        </td>
      </tr>
      <tr>
        <td class="p0">
          <table class="w100 m0" id="middleAreaAll">
            <tr>
              <td class="hp150 bor3 w33">
              </td>
              <td class="pl10" id="left_space">
              </td>
              <td class="hp150 bor3 w33">
              </td>
              <td class="pr10" id="right_space">
              </td>
              <td class="hp150 bor3 w33">
              </td>
            </tr>
          </table>
          <table class="w100 m0" id="middleAreaLR">
            <tr>
              <td class="hp150 bor3 w50">
              </td>
              <td class="pr10">
              </td>
              <td class="hp150 bor3 w50">
              </td>
            </tr>
          </table>
          <table class="w100 m0" id="middleAreaLC">
            <tr>
              <td class="hp150 bor3 w30">
              </td>
              <td class="pr10">
              </td>
              <td class="hp150 bor3 w70">
              </td>
            </tr>
          </table>
          <table class="w100 m0" id="middleAreaCR">
            <tr>
              <td class="hp150 bor3 w70">
              </td>
              <td class="pr10">
              </td>
              <td class="hp150 bor3 w30">
              </td>
            </tr>
          </table>
          <table class="w100 m0" id="middleArea">
            <tr>
              <td class="hp150 w100 bor3">
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td class="pt10" id="bottom_space">
        </td>
      </tr>
      <tr>
        <td class="w100 hp90 p0" id="mainScreenListBottom">
          <div class="w100 hp90 bor3">
          </div>
        </td>
      </tr>
    </table>
  </div>
</div>

</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>

</html:html>