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
<title>GROUPSESSION <gsmsg:write key="user.44" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../user/js/usr011kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/user/usr011kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="usr011gpId" />
<html:hidden property="usr011gpname" />
<html:hidden property="usr011gpnameKana" />
<html:hidden property="usr011com" />
<html:hidden property="usr011grpsid" />
<html:hidden property="usr011DelKbn" />
<html:hidden property="usr011DelButton" />
<html:hidden property="usr010grpmode" />
<html:hidden property="usr010grpSid" />
<html:hidden property="slt_group" />
<html:hidden property="selectgroup" />
<html:hidden property="disabledGroups"/>

<input type="hidden" name="helpPrm" value="<bean:write name="usr011knForm" property="usr011hrpPrm" />">

<logic:notEmpty name="usr011knForm" property="users_l" scope="request">
  <logic:iterate id="ulBean" name="usr011knForm" property="users_l" scope="request">
    <input type="hidden" name="users_l" value="<bean:write name="ulBean" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="usr011knForm" property="usersKr_l" scope="request">
  <logic:iterate id="ulkrBean" name="usr011knForm" property="usersKr_l" scope="request">
    <input type="hidden" name="usersKr_l" value="<bean:write name="ulkrBean" />">
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
    <logic:equal name="usr011knForm" property="usr010grpmode"  scope="request" value="add">
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="user.src.60" /></span><gsmsg:write key="user.usr011kn.1" />
      </li>
    </logic:equal>
    <logic:equal name="usr011knForm" property="usr010grpmode"  scope="request" value="edit">
      <logic:notEqual name="usr011knForm" property="usr011DelKbn"  scope="request" value="del">
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="user.src.60" /></span><gsmsg:write key="user.usr011kn.2" />
        </li>
      </logic:notEqual>
    </logic:equal>
    <logic:equal name="usr011knForm" property="usr011DelKbn"  scope="request" value="del">
      <li class="pageTitle_subFont">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="user.src.60" /></span><gsmsg:write key="user.usr011kn.3" />
      </li>
    </logic:equal>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('commit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="pushBack(),buttonPush('usr011_back');">
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
  <logic:notEqual name="usr011knForm" property="usr011DelKbn"  scope="request" value="del"><gsmsg:write key="user.usr011kn.4" /></logic:notEqual>
  <logic:equal name="usr011knForm" property="usr011DelKbn"  scope="request" value="del"><gsmsg:write key="user.usr011kn.5" /></logic:equal>
</div>

  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.group.id" />
      </th>
      <td class="w75">
        <logic:equal name="usr011knForm" property="usr011DelKbn"  scope="request" value="del">
          <bean:write name="usr011knForm" property="usr011gpIdDel" />
        </logic:equal>
        <logic:notEqual name="usr011knForm" property="usr011DelKbn"  scope="request" value="del">
          <bean:write name="usr011knForm" property="usr011gpId" />
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.group.name" />
      </th>
      <td>
        <logic:equal name="usr011knForm" property="usr011DelKbn"  scope="request" value="del">
          <bean:write name="usr011knForm" property="usr011gpnameDel" />
        </logic:equal>
        <logic:notEqual name="usr011knForm" property="usr011DelKbn"  scope="request" value="del">
          <bean:write name="usr011knForm" property="usr011gpname" />
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="user.14" />
      </th>
      <td>
        <logic:equal name="usr011knForm" property="usr011DelKbn"  scope="request" value="del">
          <bean:write name="usr011knForm" property="usr011gpnameKanaDel" />
        </logic:equal>
        <logic:notEqual name="usr011knForm" property="usr011DelKbn"  scope="request" value="del">
          <bean:write name="usr011knForm" property="usr011gpnameKana" />
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.comment" />
      </th>
      <td>
        <logic:equal name="usr011knForm" property="usr011DelKbn"  scope="request" value="del">
          <bean:write name="usr011knForm" property="usr011comDel" filter="false" />
        </logic:equal>
        <logic:notEqual name="usr011knForm" property="usr011DelKbn"  scope="request" value="del">
          <bean:write name="usr011knForm" property="usr011comHtml" filter="false" />
        </logic:notEqual>
      </td>
    </tr>
    <logic:notEqual name="usr011knForm" property="usr011DelKbn"  scope="request" value="del">
    <tr>
      <th>
        <gsmsg:write key="user.75" />
      </th>
      <td>
        <logic:notEmpty name="usr011knForm" property="usr011tarBelongingUser" scope="request">
        <logic:iterate id="ulBean" name="usr011knForm" property="usr011tarBelongingUser" scope="request">
          <span  class=" <logic:equal name="ulBean" property="usrUkoFlg" value="1" >mukoUser</logic:equal>"><bean:write name="ulBean" property="fullName" filter="false" /></span>
          <br>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.group.admin" />
      </th>
      <td>
        <logic:notEmpty name="usr011knForm" property="usr011BelongingUserKr" scope="request">
          <logic:iterate id="ulkrBean" name="usr011knForm" property="usr011BelongingUserKr" scope="request">
            <span class=" <logic:equal name="ulkrBean" property="usrUkoFlg" value="1" >mukoUser</logic:equal>"><bean:write name="ulkrBean" property="fullName" filter="false" /></span>
            <br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    </logic:notEqual>
    <tr>
      <th>
        <gsmsg:write key="user.21" />
      </th>
      <td>
        <bean:write name="usr011knForm" property="usr011knGroupClassName" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('commit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="pushBack(),buttonPush('usr011_back');">
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