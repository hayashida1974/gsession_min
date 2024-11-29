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
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../user/js/usr012.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="user.usr012.1" /></title>
</head>

<body>
<html:form action="/user/usr012">
<input type="hidden" name="usr010grpSid" value="<bean:write name='usr012Form' property='usr010grpSid'/>">
<input type="hidden" name="CMD" value="">
<html:hidden property="usr012SortKey" />
<html:hidden property="usr012OrderKey" />
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="user.src.60" /></span><gsmsg:write key="user.usr012.2" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<table class="table-top">
    <tr>
    <!-- ログインID -->
    <logic:equal name="usr012Form" property="usr012SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_ID) %>">
      <logic:equal name="usr012Form" property="usr012OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="no_w"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_ID) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')"><span>ID<span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span></span></a></th>
      </logic:equal>
      <logic:equal name="usr012Form" property="usr012OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="no_w"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_ID) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span>ID<span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span></span></a></th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="usr012Form" property="usr012SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_ID) %>">
      <th class="no_w"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_ID) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span>ID</span></a></th>
    </logic:notEqual>

    <!-- 名前 -->
    <logic:equal name="usr012Form" property="usr012SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>">
      <logic:equal name="usr012Form" property="usr012OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w30"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')"><span><gsmsg:write key="cmn.name4" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span></span></a></th>
      </logic:equal>
      <logic:equal name="usr012Form" property="usr012OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w30 no_w"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span><gsmsg:write key="cmn.name4" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span></span></a></th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="usr012Form" property="usr012SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>">
      <th class="w30 no_w"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span><gsmsg:write key="cmn.name4" /></span></a></th>
    </logic:notEqual>

    <!-- 社員・職員番号 -->
    <logic:equal name="usr012Form" property="usr012SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>">
      <logic:equal name="usr012Form" property="usr012OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w20 no_w"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')"><span><gsmsg:write key="cmn.employee.staff.number" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span></span></a></th>
      </logic:equal>
      <logic:equal name="usr012Form" property="usr012OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w20 no_w"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span><gsmsg:write key="cmn.employee.staff.number" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span></span></a></th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="usr012Form" property="usr012SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>">
      <th class="w20 no_w"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span><gsmsg:write key="cmn.employee.staff.number" /></span></a></th>
    </logic:notEqual>

    <!-- 役職 -->
    <logic:equal name="usr012Form" property="usr012SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>">
      <logic:equal name="usr012Form" property="usr012OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w30 no_w"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')"><span><gsmsg:write key="cmn.post" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span></span></a></th>
      </logic:equal>
      <logic:equal name="usr012Form" property="usr012OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w30 no_w"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span><gsmsg:write key="cmn.post" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span></span></a></th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="usr012Form" property="usr012SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>">
      <th class="w30 no_w"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')"><span><gsmsg:write key="cmn.post" /></span></a></th>
    </logic:notEqual>
    </tr>

    <bean:define id="mod" value="0" />
    <logic:iterate id="group" name="usr012Form" property="usr012ModelList" indexId="idx" scope="request">
      <logic:notEqual name="group" property="usrUkoFlg" value="1">
        <bean:define id="ukoclass" value="" />
      </logic:notEqual>
      <logic:equal name="group" property="usrUkoFlg" value="1">
        <bean:define id="ukoclass" value="mukoUser" />
      </logic:equal>

      <tr>
        <td class="no_w">
        <span class="<bean:write name="ukoclass" />">
        <bean:write name="group" property="usrlgid" />
        </span>
        </td>
       <td class="no_w">
        <span class="<bean:write name="ukoclass" />">
        <bean:write name="group" property="namesei" />
        <bean:write name="group" property="namemei" />
        </span>
        </td>
        <td class="no_w">
        <span class="<bean:write name="ukoclass" />">
        <bean:write name="group" property="syainno" />
        </span>
        </td>
        <td class="no_w">
        <span class="<bean:write name="ukoclass" />">
        <bean:write name="group" property="yakusyoku" />
        </span>
        </td>
      </tr>
    </logic:iterate>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
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