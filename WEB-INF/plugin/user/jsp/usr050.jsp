<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY; %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.change.password" /></title>
</head>

<body>
<html:form action="/user/usr050">
<input type="hidden" name="CMD" value="ok">
<html:hidden property="usr050CoeKbn" />
<html:hidden property="usr050UppercaseKbn" />
<html:hidden property="usr050Digit" />
<html:hidden property="usr050UidPswdKbn" />
<html:hidden property="usr050OldPswdKbn" />
<html:hidden property="usr050Mode" />
<html:hidden property="backScreen" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->

<div class="kanriPageTitle w80 mrl_auto">
  <bean:define id="kusr" name="<%= key %>" scope="session" />
  <ul>
    <logic:notEqual name="usr050Form" property="backScreen" value="1">
      <li>
        <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
      </li>
      <li><gsmsg:write key="cmn.preferences2" /></li>
    </logic:notEqual>
    <logic:equal name="usr050Form" property="backScreen" value="1">
      <li>
        <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
      </li>
      <gsmsg:write key="cmn.admin.setting" />
    </logic:equal>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shain.info" /></span><gsmsg:write key="cmn.change.password" />
    </li>
    <li>
      <div>
      <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backToMenu');">
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
    <div class="txt_l mb10">
      <html:errors/>
    </div>
  </logic:messagesPresent>

  <logic:equal name="usr050Form" property="usr050Mode" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.PSWD_MODE_UPDATE)  %>">
    <div class="txt_l fw_b"><gsmsg:write key="user.17" /></div>
  </logic:equal>
  <logic:equal name="usr050Form" property="usr050Mode" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.PSWD_MODE_LIMIT)  %>">
    <div class="txt_l fw_b"><gsmsg:write key="user.usr050.2" /><br><gsmsg:write key="user.17" /></div>
  </logic:equal>

  <table class="table-left mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="user.src.28" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <html:password name="usr050Form" property="usr050OldPassWord" styleClass="wp320" maxlength="256" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="user.src.26" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td>
        <html:password name="usr050Form" property="usr050NewPassWord" styleClass="wp320 mb5" maxlength="256" /><br>
        <html:password name="usr050Form" property="usr050NewPassWordKn" styleClass="wp320 mb5" maxlength="256" />&nbsp;<gsmsg:write key="user.19" /><br>
        <bean:define id="digitStr" name="usr050Form" property="usr050Digit" type="java.lang.Integer" />
        *<gsmsg:write key="user.usr031.10" arg0="<%= String.valueOf(digitStr.intValue()) %>" /><br>
        <logic:equal name="usr050Form" property="usr050CoeKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_COEKBN_ON_EN)  %>">*<gsmsg:write key="user.usr031.12" /><br></logic:equal>
        <logic:equal name="usr050Form" property="usr050CoeKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_COEKBN_ON_ENS)  %>">*<gsmsg:write key="user.usr031.19" /><br></logic:equal>
        <logic:equal name="usr050Form" property="usr050UppercaseKbn" value="<%=String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UPPERCASE_ON)%>">*<gsmsg:write key="user.usr031.25" /><br></logic:equal>
        *<gsmsg:write key="user.usr031.11" /><br>
        <logic:equal name="usr050Form" property="usr050UidPswdKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_UIDPSWDKBN_ON)  %>"><br>*<gsmsg:write key="user.usr031.13" /></logic:equal>
        <logic:equal name="usr050Form" property="usr050OldPswdKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PWC_OLDPSWDKBN_ON)  %>"><br>*<gsmsg:write key="user.usr050.11" /></logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backToMenu');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>