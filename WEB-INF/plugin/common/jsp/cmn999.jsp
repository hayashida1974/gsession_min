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
<link rel=stylesheet href='../common/css/common.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<title>GROUPSESSION <gsmsg:write key="cmn.message" /></title>
<script language="JavaScript">
<!--
  //ボタン
  function onForward(buttonType){

      document.forms[0].target=document.forms[0].wtarget.value;

      var forwardUrl;
      if (buttonType == 0) {
          forwardUrl = "<bean:write name="cmn999Form" property="urlOK" filter="false" />";
          document.forms[0].directURL.value = forwardUrl;
      } else if (buttonType == 1) {
          forwardUrl = "<bean:write name="cmn999Form" property="urlCancel"/>";
          document.forms[0].directURL.value = forwardUrl;
      }

      document.forms[0].submit();
      return onControlSubmit();
  }

  var subWindow;

  function openErrReportWindow() {
    var winWidth = 500;
    var winHeight = 300;
    var winx = getCenterX(winWidth);
    var winy = getCenterY(winHeight);

    var newWinOpt = "width=" + winWidth + ", height=" + winHeight + ", toolbar=no ,scrollbars=yes, resizable=yes, left=" + winx + ", top=" + winy;

    if (!flagSubWindow || (flagSubWindow && subWindow.closed)) {
        subWindow = window.open('', 'exceptionWindow', newWinOpt);
        flagSubWindow = true;
    } else {
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

<html:form action="/common/cmn999" target="_self">

<html:hidden property="CMD" value="" />
<html:hidden property="directURL" value="" />
<html:hidden property="wtarget" />

<logic:notEmpty name="cmn999Form" property="hiddenList">
  <logic:iterate id="item" name="cmn999Form" property="hiddenList" scope="request">
    <input type="hidden" name="<bean:write name="item" property="key"/>" value="<bean:write name="item" property="value"/>">
  </logic:iterate>
</logic:notEmpty>

<div id="contair">
  <div class="information_body">
    <div class="information_top">
      <logic:equal name="cmn999Form" property="icon" value="0">
        <gsmsg:write key="cmn.warning" />
      </logic:equal>
      <logic:equal name="cmn999Form" property="icon" value="1">
        <gsmsg:write key="cmn.information" />
      </logic:equal>
    </div>
    <div class="information_middle bgC_body">
      <table class="tl0">
        <tr>
          <td class="pl20">
            <logic:equal name="cmn999Form" property="icon" value="0">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_warn_2.gif" alt="<gsmsg:write key="cmn.warning" />">
              <img class="header_pluginImg" src="../common/images/original/icon_warn_63.png" alt="<gsmsg:write key="cmn.warning" />">
            </logic:equal>
            <logic:equal name="cmn999Form" property="icon" value="1">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_info_2.gif" alt="<gsmsg:write key="cmn.information" />">
              <img class="header_pluginImg" src="../common/images/original/icon_info_63.png" alt="<gsmsg:write key="cmn.information" />">
            </logic:equal>
          </td>
          <td class="information_messageArea">
            <span class="fs_16">
              <logic:notEqual name="cmn999Form" property="freeLicenseMessage" value="1">
                <logic:notEmpty name="cmn999Form" property="messageList">
                  <logic:iterate id="msg" name="cmn999Form" property="messageList">
                    <bean:write name="msg" /><br>
                  </logic:iterate>
                </logic:notEmpty>
              </logic:notEqual>
              <logic:equal name="cmn999Form" property="freeLicenseMessage" value="1">
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
      <!-- エラーログ -->
      <logic:notEmpty name="cmn999Form" property="errorLog">
        <div id="information_errorlog pt10">
          <div class="ml40 txt_l">
            <gsmsg:write key="cmn.cmn999.2" />
          </div>
          <textarea name="elog" id="exceptionLog" class="errMsg_textArea ml40" rows="5" readonly wrap="off"><bean:write name="cmn999Form" property="errorLogOnly"/></textarea>
        </div>
      </logic:notEmpty>
      <!-- 動作環境 -->
      <logic:notEmpty name="cmn999Form" property="detailInfo">
        <div class="pt10">
          <div class="ml40 txt_l">
            <gsmsg:write key="cmn.cmn999.3" />
          </div>
          <textarea name="minfo" id="exceptionLog" class="errMsg_textArea ml40" rows="2" readonly wrap="off"><bean:write name="cmn999Form" property="detailInfo" /></textarea>
        </div>
      </logic:notEmpty>
      <!-- ボタン -->
      <logic:equal name="cmn999Form" property="type_popup" value="0">
        <div class="txt_c">
          <logic:equal name="cmn999Form" property="type" value="0">
            <button type="button" class="baseBtn" value="<bean:write name="cmn999Form" property="okBtnValue"/>" onClick="return onForward(0);">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_ok.png" alt="<bean:write name="cmn999Form" property="okBtnValue"/>">
              <img class="header_pluginImg" src="../common/images/original/icon_check.png" alt="<bean:write name="cmn999Form" property="okBtnValue"/>">
              <bean:write name="cmn999Form" property="okBtnValue"/>
            </button>
          </logic:equal>
          <logic:equal name="cmn999Form" property="type" value="1">
            <button type="button" class="baseBtn wp115" value="<bean:write name="cmn999Form" property="okBtnValue"/>" onClick="return onForward(0);">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_ok.png" alt="<bean:write name="cmn999Form" property="okBtnValue"/>">
              <img class="header_pluginImg" src="../common/images/original/icon_check.png" alt="<bean:write name="cmn999Form" property="okBtnValue"/>">
              <bean:write name="cmn999Form" property="okBtnValue"/>
            </button>
            <button type="button" class="baseBtn wp115" value="<bean:write name="cmn999Form" property="cancelBtnValue"/>" onClick="return onForward(1);">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_delete.png" alt="<bean:write name="cmn999Form" property="cancelBtnValue"/>">
              <img class="header_pluginImg" src="../common/images/original/icon_delete.png" alt="<bean:write name="cmn999Form" property="cancelBtnValue"/>">
              <bean:write name="cmn999Form" property="cancelBtnValue"/>
            </button>
          </logic:equal>
          <logic:equal name="cmn999Form" property="type" value="2">
            <button type="button" class="baseBtn " value="<bean:write name="cmn999Form" property="okBtnValue"/>" onClick="return onForward(0);">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_ok.png" alt="<bean:write name="cmn999Form" property="okBtnValue"/>">
              <img class="header_pluginImg" src="../common/images/original/icon_check.png" alt="<bean:write name="cmn999Form" property="okBtnValue"/>">
              <bean:write name="cmn999Form" property="okBtnValue"/>
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return onForward(1);">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="header_pluginImg" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </logic:equal>
        </div>
      </logic:equal>

      <logic:equal name="cmn999Form" property="type_popup" value="1">
        <div class="txt_c">
          <logic:empty name="cmn999Form" property="closeScript">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.open('about:blank','_self').close()">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.close" />">
              <img class="header_pluginImg" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.close" />">
              <gsmsg:write key="cmn.close" />
            </button>
          </logic:empty>
          <logic:notEmpty name="cmn999Form" property="closeScript">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="<bean:write name="cmn999Form" property="closeScript" />">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.close" />">
              <img class="header_pluginImg" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.close" />">
              <gsmsg:write key="cmn.close" />
            </button>
          </logic:notEmpty>
        </div>
      </logic:equal>

      <logic:equal name="cmn999Form" property="outOfMemory" value="1">
        <div class="pl20 pr20">
          <div class="txt_l textError"><gsmsg:write key="cmn.outofmemory" /></div>
          <div class="txt_l"><gsmsg:write key="cmn.cmn999.4" />:
            <a href="<%=ConfigBundle.getValue("GS_SETTING_DOC_URL")%>" target="_blank"><gsmsg:write key="cmn.cmn999.5" /></a>
          </div>
        </div>
      </logic:equal>

      <logic:equal name="cmn999Form" property="type" value="3">
        <div class="txt_c">
          <logic:notEmpty name="cmn999Form" property="errorLog">
            <div id="error_report">
              <div class="txt_l ml40 mt10">
                <gsmsg:write key="cmn.cmn999.6" />
              </div>
              <div class="ml40 mr40 txt_c bor1">
                <gsmsg:write key="cmn.cmn999.7" />
                <br>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.reports" />" onClick="openErrReportWindow()">
                  <gsmsg:write key="cmn.reports" />
                </button>
              </div>
              <br>
              <button type="button" class="baseBtn" value="<bean:write name="cmn999Form" property="okBtnValue"/>" onClick="return onForward(0);">
                <img class="header_pluginImg-classic" src="../common/images/classic/icon_ok.png" alt="<bean:write name="cmn999Form" property="okBtnValue"/>">
                <img class="header_pluginImg" src="../common/images/original/icon_check.png" alt="<bean:write name="cmn999Form" property="okBtnValue"/>">
                <bean:write name="cmn999Form" property="okBtnValue"/>
              </button>
            </div>
          </logic:notEmpty>
          <logic:empty name="cmn999Form" property="errorLog">
            <button type="button" class="baseBtn" value="<bean:write name="cmn999Form" property="okBtnValue"/>" onClick="return onForward(0);">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_ok.png" alt="<bean:write name="cmn999Form" property="okBtnValue"/>">
              <img class="header_pluginImg" src="../common/images/original/icon_check.png" alt="<bean:write name="cmn999Form" property="okBtnValue"/>">
              <bean:write name="cmn999Form" property="okBtnValue"/>
            </button>
          </logic:empty>
        </div>
      </logic:equal>
    </div>
    <div class="information_bottom bgC_body"></div>
  </div>
</div>
</html:form>
<form action="<%=ConfigBundle.getValue("ERROR_REPORT_URL")%>" method="post" name="errLogForm" target="exceptionWindow" class="display_none">
  <input type="hidden" name="exception" value="<bean:write name="cmn999Form" property="errorLog"/>">
  <input type="hidden" name="version" value="<bean:write name="cmn999Form" property="gsVersion"/>">
</form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>