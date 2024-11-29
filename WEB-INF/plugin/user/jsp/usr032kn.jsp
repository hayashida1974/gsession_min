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
<title>GROUPSESSION <gsmsg:write key="user.usr032.3" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/user/usr032kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="processMode" />

<html:hidden property="usr030SearchKana" />
<html:hidden property="usr030cmdMode" />
<html:hidden property="usr030SearchFlg" />
<html:hidden property="selectgsid" />
<html:hidden property="usr030userId" />
<html:hidden property="usr030usrUkoFlg" />
<html:hidden property="usr030shainno" />
<html:hidden property="usr030sei" />
<html:hidden property="usr030mei" />
<html:hidden property="usr030seikn" />
<html:hidden property="usr030meikn" />
<html:hidden property="usr030agefrom" />
<html:hidden property="usr030ageto" />
<html:hidden property="usr030yakushoku" />
<html:hidden property="usr030mail" />
<html:hidden property="usr030tdfkCd" />
<html:hidden property="usr030seibetu" />
<html:hidden property="usr030entranceYearFr" />
<html:hidden property="usr030entranceMonthFr" />
<html:hidden property="usr030entranceDayFr" />
<html:hidden property="usr030entranceYearTo" />
<html:hidden property="usr030entranceMonthTo" />
<html:hidden property="usr030entranceDayTo" />
<html:hidden property="selectgsidSave" />
<html:hidden property="usr030userIdSave" />
<html:hidden property="usr030usrUkoFlgSave" />
<html:hidden property="usr030shainnoSave" />
<html:hidden property="usr030seiSave" />
<html:hidden property="usr030meiSave" />
<html:hidden property="usr030seiknSave" />
<html:hidden property="usr030meiknSave" />
<html:hidden property="usr030agefromSave" />
<html:hidden property="usr030agetoSave" />
<html:hidden property="usr030yakushokuSave" />
<html:hidden property="usr030mailSave" />
<html:hidden property="usr030tdfkCdSave" />
<html:hidden property="usr030seibetuSave" />
<html:hidden property="usr030entranceYearFrSave" />
<html:hidden property="usr030entranceMonthFrSave" />
<html:hidden property="usr030entranceDayFrSave" />
<html:hidden property="usr030entranceYearToSave" />
<html:hidden property="usr030entranceMonthToSave" />
<html:hidden property="usr030entranceDayToSave" />

<html:hidden property="selectgroup" />
<html:hidden property="usr031defgroup" />
<html:hidden property="usr032initFlg" />
<html:hidden property="usr032updateFlg" />
<html:hidden property="usr032PswdKbn" />
<html:hidden property="usr032createFlg" />
<html:hidden property="usr032cmdMode" />
<html:hidden property="usr032updatePassFlg" />

<logic:notEmpty name="usr032knForm" property="usr030selectusers">
<logic:iterate id="usrSid" name="usr032knForm" property="usr030selectusers">
  <input type="hidden" name="usr030selectusers" value="<bean:write name="usrSid" />">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<logic:equal name="usr032knForm" property="usr032cmdMode" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NORMAL) %>">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>

<logic:equal name="usr032knForm" property="usr032cmdMode" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP_ALL) %>">
  <input type="hidden" name="helpPrm" value="1">
</logic:equal>

<logic:equal name="usr032knForm" property="usr032cmdMode" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_MULTIPLE_GROUP) %>">
  <input type="hidden" name="helpPrm" value="2">
</logic:equal>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="user.src.60" /></span><gsmsg:write key="user.usr032kn.8" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doImp');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('Usr032kn_Back');">
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

<div class="txt_l cl_fontWarn">
<gsmsg:write key="cmn.capture.file.sure" />
</div>
  <table class="table-left">
  <!-- 取込みファイル -->
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.capture.file" />
      </th>
      <td class="w75">
        <bean:write name="usr032knForm" property="usr032knFileName" />
      </td>
    </tr>
  <!-- 取込み件数 -->
    <tr>
      <th>
        <gsmsg:write key="cmn.capture.item.count" />
      </th>
      <td>
        <logic:notEmpty name="usr032knForm" property="usr032knImpList" scope="request">
          <bean:size id="count" name="usr032knForm" property="usr032knImpList" scope="request" />
          <bean:write name="count" /><gsmsg:write key="cmn.number" /><br>
        </logic:notEmpty>
      </td>
    </tr>
    <logic:equal name="usr032knForm" property="usr032cmdMode" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NORMAL) %>">
      <%@ include file="/WEB-INF/plugin/user/jsp/usr032kn_sub01.jsp" %>
    </logic:equal>
    <logic:equal name="usr032knForm" property="usr032cmdMode" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP_ALL) %>">
      <%@ include file="/WEB-INF/plugin/user/jsp/usr032kn_sub02.jsp" %>
    </logic:equal>
    <logic:equal name="usr032knForm" property="usr032cmdMode" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_MULTIPLE_GROUP) %>">
      <%@ include file="/WEB-INF/plugin/user/jsp/usr032kn_sub03.jsp" %>
    </logic:equal>
  </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doImp');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('Usr032kn_Back');">
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