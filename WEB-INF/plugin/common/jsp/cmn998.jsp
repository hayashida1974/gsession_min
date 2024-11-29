<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.ConfigBundle" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/submit.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.message" /></title>
<script language="JavaScript">
<!--

  var subWindow;

  function openErrReportWindow(url) {
    var winWidth = 500;
    var winHeight = 300;
    var winx = getCenterX(winWidth);
    var winy = getCenterY(winHeight);

    var newWinOpt = "width=" + winWidth + ", height=" + winHeight + ", toolbar=no ,scrollbars=yes, resizable=yes, left=" + winx + ", top=" + winy;

    if (!flagSubWindow || (flagSubWindow && subWindow.closed)) {
        subWindow = window.open(url, 'exceptionWindow', newWinOpt);
        flagSubWindow = true;
    } else {
        subWindow.location.href=url;
        subWindow.focus();
    }

    document.forms['errLogForm'].submit();
    return false;
  }

  function getCenterX(winWidth) {
    var x = (screen.width - winWidth) / 2;
    return x;
  }

  function getCenterY(winHeight) {
    var y = (screen.height - winHeight) / 2;
    return y;
  }
// -->
</script>
</head>
<body>
<bean:define id="formTarget" name="cmn998Form" property="wtarget" type="java.lang.String" />
<% if (formTarget == null || formTarget.length() <= 0) { formTarget = "_self"; } %>
<html:form action="/common/cmn998" target="<%= formTarget %>">
<html:hidden property="CMD" value="cmn998ok" />
<html:hidden property="directURL" />
<html:hidden property="wtarget" />
<logic:notEmpty name="cmn998Form" property="hiddenList">
  <logic:iterate id="item" name="cmn998Form" property="hiddenList" scope="request">
    <input type="hidden" name="<bean:write name="item" property="key"/>" value="<bean:write name="item" property="value"/>">
  </logic:iterate>
</logic:notEmpty>

<div id="contair">
  <div class="information_body">
    <div class="information_top">
      <gsmsg:write key="cmn.warning" />
    </div>
    <div class="information_middle bgC_body">
      <table class="tl0">
        <tr>
          <td class="pl20">
            <img class="header_pluginImg-classic" src="../common/images/classic/icon_warn_2.gif" alt="<gsmsg:write key="cmn.warning" />">
            <img class="header_pluginImg" src="../common/images/original/icon_warn_63.png" alt="<gsmsg:write key="cmn.warning" />">
          </td>
          <td class="information_messageArea">
            <span class="fs_16">
              <logic:equal name="cmn998Form" property="freeLicenseMessage" value="0">
                <logic:notEmpty name="cmn998Form" property="messageList">
                  <logic:iterate id="msg" name="cmn998Form" property="messageList">
                    <bean:write name="msg" /><br>
                  </logic:iterate>
                </logic:notEmpty>
              </logic:equal>
              <logic:equal name="cmn998Form" property="freeLicenseMessage" value="1">
                <gsmsg:write key="cmn.cmn999.13" /><br>
                <gsmsg:write key="cmn.cmn999.14" />
                <a href=<%= ConfigBundle.getValue("LICENSE_PAGE_FOR_FREE") %> target=_blank>
                <gsmsg:write key="cmn.cmn999.15" /></a><gsmsg:write key="cmn.cmn999.16" />
              </logic:equal>
            </span>
          </td>
        </tr>
      </table>
    </div>
    <div class="information_middle bgC_body">
      <div class="txt_c">
        <button type="submit" class="baseBtn" value="<bean:write name="cmn998Form" property="okBtnValue"/>">
          <img class="header_pluginImg-classic" src="../common/images/classic/icon_ok.png" alt="<bean:write name="cmn998Form" property="okBtnValue"/>">
          <img class="header_pluginImg" src="../common/images/original/icon_check.png" alt="<bean:write name="cmn998Form" property="okBtnValue"/>">
          <bean:write name="cmn998Form" property="okBtnValue"/>
        </button>
      </div>
    </div>
    <div class="information_bottom bgC_body"></div>
  </div>
</div>
</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>