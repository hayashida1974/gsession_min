<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<% String gsurl = jp.groupsession.v2.cmn.GSConst.GS_HOMEPAGE_URL; %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <title>GROUPSESSION <gsmsg:write key="mobile.17" /></title>
  <meta name="robots" content="noindex">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/common.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

  <script language="JavaScript" src="../common/js/cmn001.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>
<logic:equal name="cmn001Form" property="cmn001LoginCtrl" value="0">
  <body class="login" onLoad="document.getElementsByName('cmn001Userid')[0].focus()">
</logic:equal>
<logic:notEqual name="cmn001Form" property="cmn001LoginCtrl" value="0">
  <body class="../common/images/login/">
</logic:notEqual>

<div class="txt_c mrl_auto h100">
  <div class="mrl_auto mb20 mt80">
    <a href="<bean:write name="cmn001Form" property="cmn001Url" />" target="_blank">
      <logic:equal name="cmn001Form" property="cmn001BinSid" value="0">
        <img src="../common/images/login/logo.png" alt="<gsmsg:write key="cmn.cmn001.2" />" border="0">
      </logic:equal>
      <logic:notEqual name="cmn001Form" property="cmn001BinSid" value="0">
        <img src="../common/cmn001.do?CMD=getImageFile&cmn001BinSid=<bean:write name="cmn001Form" property="cmn001BinSid" />" alt="<gsmsg:write key="cmn.cmn001.2" />" border="0">
      </logic:notEqual>
    </a>
  </div>
  <logic:equal name="cmn001Form" property="cmn001LoginCtrl" value="0">
    <html:form action="/common/cmn001">
      <input type="hidden" name="CMD" value="login">
      <input type="hidden" name="cmn001loginType" value="<%= String.valueOf(jp.groupsession.v2.cmn.cmn001.Cmn001Form.LOGIN_TYPE_SCREEN) %>">
      <html:hidden property="url" />
      <html:hidden property="cmn001initAccess" />
      <div class="login_box">
        <logic:messagesPresent message="false">
          <html:errors /><br>
        </logic:messagesPresent>
        <gsmsg:write key="cmn.cmn001.3" />
        <ul>
          <li>
          <div><p><gsmsg:write key="cmn.user.id" /></p></div>
          <div><html:text property="cmn001Userid" maxlength="256" styleClass="input" tabindex="1" /></div>
          </li>
        <li>
            <div><p><gsmsg:write key="user.117" /></p></div>
            <div><html:password property="cmn001Passwd" maxlength="256" styleClass="input" tabindex="2" /></div>
          </li>
        </ul>
        <input type="submit" value="<gsmsg:write key="mobile.17" />" class="login_btn" tabindex="3">
        <div class="txt_r">
          <a href="<%= gsurl %>" target="_blank"><img src="../common/images/login/gs_logo.png" alt="GroupSession"></a>
        </div>
      </div>
    </html:form>
  </logic:equal>
  <logic:notEqual name="cmn001Form" property="cmn001LoginCtrl" value="0">
    <div class="login_msg mt50">
      <span class="textError">
        <gsmsg:write key="cmn.cmn001.4" /><br>
        <gsmsg:write key="cmn.cmn001.5" />
      </span>
    </div>
  </logic:notEqual>

  <div class="menu_box">
    <ul class="menu">
      <li>
        <a href="https://groupsession.jp/support/tutorial.html" target="_blank"><i class="icon-paging_right mr5"></i><gsmsg:write key="cmn.cmn001.6" /></a>
      </li>
      <li>
        <a href="../crossride_dnf4/index.html" target="_blank"><i class="icon-paging_right mr5"></i><gsmsg:write key="cmn.cmn001.8" /></a>
      </li>
      <li>
        <a href="https://groupsession.jp/products/app.html" target="_blank"><i class="icon-paging_right mr5"></i><gsmsg:write key="cmn.cmn001.9" /></a>
      </li>
    </ul>

    <ul class="mob_menu">
      <li>
        <a href="https://groupsession.jp/products/app.html" target="_blank"><img src="../common/images/login/smp.png" alt="<gsmsg:write key="cmn.cmn001.11" />"><gsmsg:write key="cmn.cmn001.11" /></a>
      </li>
    </ul>
  </div>
</div>

<footer>
  <div class="login_copyright">
    <a href="http://www.sjts.co.jp/" target="_blank">&copy;<gsmsg:write key="cmn.cmn001.10" /></a>
  </div>
</footer>



</body>
</html:html>