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
<script src="../main/js/man360kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="main.man350kn.1" /></title>
</head>
<!-- body -->
<body onload="initChgArea();">
<html:form action="/main/man360kn">
<input type="hidden" name="CMD" value="init">
<html:hidden property="man360init" />
<html:hidden property="man360area1" />
<html:hidden property="man360area2" />
<html:hidden property="man360area3" />
<html:hidden property="man360area4" />
<html:hidden property="man360area5" />
<html:hidden property="man360layout" />
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />">
      <img class="header_pluginImg" src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man350kn.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush2('man360knOk');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('man360knBack');">
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
      <th class="w20" id="layoutKbn1">
        <gsmsg:write key="ptl.5" />
      </th>
      <td class="w80" id="layoutKbn2">
        <logic:equal name="man360Form" property="man360layout" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.MANSCREEN_LAYOUT_DEFAULT) %>" >
          <gsmsg:write key="cmn.default" />
        </logic:equal>
        <logic:equal name="man360Form" property="man360layout" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.MANSCREEN_LAYOUT_CUSTOM) %>" >
          <gsmsg:write key="cmn.customize" />
        </logic:equal>
      </td>
    </tr>
  </table>
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