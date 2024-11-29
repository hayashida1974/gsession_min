<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html:html>
<head>

<title>GROUPSESSION <gsmsg:write key="cmn.preferences2" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmnOAuth.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<logic:equal name="cmn270Form" property="cmn270AuthComplete" value="1">
  <body onload="cmn270Finally();">
</logic:equal>
<logic:notEqual name="cmn270Form" property="cmn270AuthComplete" value="1">
  <body onload="cmn270Resize();">
</logic:notEqual>

<html:form action="/common/cmn270">
<input type="hidden" name="CMD" value="">
<html:hidden property="cotSid" />
<html:hidden property="cmn270cotSidParamName" />

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
              <bean:write name="cmn270Form" property="cmn270Message" filter="false"/>
            </span>
          </td>
        </tr>
      </table>
    </div>
    <div class="information_middle bgC_body">
      <div class="txt_c">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="cmn270Close();">
          <img class="header_pluginImg-classic" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="header_pluginImg" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </div>
    <div class="information_bottom bgC_body"></div>
  </div>
</div>

</html:form>

</body>
</html:html>