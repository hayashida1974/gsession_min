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
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="man.mbl.use.mass.configuration.confirmation" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/submit.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>
<html:form action="/main/man210kn" onsubmit="return onControlSubmit();">

<input type="hidden" name="CMD" value="touroku">
<html:hidden property="man210ObjKbn" />
<html:hidden property="man210UseKbn" />
<html:hidden property="man210NumCont" />
<html:hidden property="man210NumAutAdd" />

<logic:notEmpty name="man210knForm" property="man210userSid" scope="request">
<logic:iterate id="users" name="man210knForm" property="man210userSid" indexId="idx" scope="request">
  <bean:define id="userSid" name="users" type="java.lang.String" />
  <html:hidden property="man210userSid" value="<%= userSid %>" />
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man210kn.1" />
    </li>
    <li>
      <div>
        <button type="submit" value="<gsmsg:write key="cmn.final" />" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_mblUseConf');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
<div class="txt_l cl_fontWarn">
  <gsmsg:write key="main.man210kn.2" />
</div>
<table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.target" />
      </th>
      <td class="w75">
        <logic:equal name="man210knForm" property="man210ObjKbn" value="0">
          <gsmsg:write key="cmn.alluser" />
        </logic:equal>

        <logic:notEqual name="man210knForm" property="man210ObjKbn" value="0">
          <logic:notEmpty name="man210knForm" property="man210knMemberList" scope="request">
            <logic:iterate id="memMdl" name="man210knForm" property="man210knMemberList" scope="request">
            <span class="<logic:equal name="memMdl" property="usrUkoFlg" value="1">mukoUser</logic:equal>">
              <bean:write name="memMdl" property="usiSei" />　<bean:write name="memMdl" property="usiMei" /><br>
            </span>
            </logic:iterate>
          </logic:notEmpty>
      </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.mobile.use" />
      </th>
      <td>
        <logic:equal name="man210knForm" property="man210UseKbn" value="0">
          <gsmsg:write key="main.man210kn.3" />

          <logic:equal name="man210knForm" property="man210NumCont" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.UID_CONTROL) %>">
            <br><gsmsg:write key="cmn.login.control.identification.number" />
          </logic:equal>

          <logic:equal name="man210knForm" property="man210NumAutAdd" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.UID_AUTO_REG_OK) %>">
            <br><gsmsg:write key="main.man210.3" />
          </logic:equal>

        </logic:equal>

        <logic:notEqual name="man210knForm" property="man210UseKbn" value="0">
          <gsmsg:write key="main.man210kn.4" />
        </logic:notEqual>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="submit" value="<gsmsg:write key="cmn.final" />" class="baseBtn">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_mblUseConf');">
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