<%@page import="jp.groupsession.v2.cmn.login.otp.OtpSendResult"%>
<%@page import="jp.groupsession.v2.cmn.cmn004.Cmn004Form"%>
<%@page import="jp.groupsession.v2.cmn.GSConstCommon"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/submit.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.message" /></title>
<script language="JavaScript">
</script>
</head>

<body>

<html:form action="/common/cmn004" target="_self">
<bean:define id="thisForm" name="cmn004Form" type="Cmn004Form" />
<bean:define id="otpRes" name="cmn004Form" property="cmn004OtpSendResult" type="OtpSendResult" />

<html:hidden property="url" />

<html:hidden property="CMD" value="cmn004OK" />
<html:hidden property="cmn004Token" />

<div id="contair">
  <div class="information_body" class="marginTop30">

    <div class="information_top">
      <span>
      <gsmsg:write key="cmn.cmn004.1"/>
      </span>
    </div>
    <div class="information_middle">
      <div class="information_messageArea">
        <div class="text_base" >
            <gsmsg:write key="cmn.cmn004.2" arg0="<%=otpRes.getOtpSendMailTo() %>"/>
        </div>
        <div class="text_base ml5 mt5" >
          <div>
            <ul class="display_inline m0 p0">
              <li class="wp60"><gsmsg:write key="cmn.sender"/></li>
              <li>：<bean:write name="otpRes" property="otpSendMailFrom" /></li>
            </ul>
          </div>
          <div>
            <ul class="display_inline m0 p0">
              <li class="wp60"><gsmsg:write key="cmn.title"/></li>
              <li>：<bean:write name="otpRes" property="otpSendMailSubject" /></li>
            </ul>
          </div>
          <div>
            <ul class="display_inline m0 p0">
              <li class="wp60"><gsmsg:write key="cmn.send.date"/></li>
              <li>：<bean:write name="otpRes" property="otpSendDateStr" /></li>
            </ul>
          </div>
        </div>
        <div class="text_base  mt5" >
            <gsmsg:write key="cmn.cmn004.3" />
        </div>
        <div class="text_base  mt5">
          <div id="error_report">
             <logic:messagesPresent message="false">
             <html:errors />
             </logic:messagesPresent>
          </div>
          <input name="cmn004OtPass" type="text" maxlength="<%= String.valueOf(GSConstCommon.OTP_LENGTH) %>">
          <button type="submit" value="OK" class="baseBtn" >
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.login" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.login" />">
            <gsmsg:write key="cmn.login" />
          </button>
        </div>
      </div>
    </div>
    <div class="information_bottom"></div>
  </div>
</div>
</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>