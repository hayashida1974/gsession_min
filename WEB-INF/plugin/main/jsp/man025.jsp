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
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="main.holiday.setting" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man025.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>

<html:form action="/main/man025">
<logic:equal name="man025Form" property="processMode" scope="request" value="addTemp">
  <input type="hidden" name="CMD" value="add">
</logic:equal>
<logic:equal name="man025Form" property="processMode" scope="request" value="editTemp">
  <input type="hidden" name="CMD" value="edit">
</logic:equal>

<html:hidden property="processMode" />
<html:hidden property="man020DspYear" />
<html:hidden property="editHltSid" />
<html:hidden property="man023CheckAll" />

<logic:notEmpty name="man025Form" property="man023hltSid" scope="request">
  <logic:iterate id="hltBean" name="man025Form" property="man023hltSid" scope="request">
    <input type="hidden" name="man023hltSid" value="<bean:write name="hltBean" />">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <logic:equal name="man025Form" property="processMode" scope="request" value="addTemp">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="main.holiday.setting" /></span><gsmsg:write key="main.man025.1" />
      </logic:equal>
      <logic:equal name="man025Form" property="processMode" scope="request" value="editTemp">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="main.holiday.setting" /></span><gsmsg:write key="main.man025.2" />
      </logic:equal>
    </li>
    <li>
      <div>
        <logic:equal name="man025Form" property="processMode" scope="request" value="addTemp">
          <button type="submit" class="baseBtn" value="addTemp">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
        </logic:equal>
        <logic:equal name="man025Form" property="processMode" scope="request" value="editTemp">
          <button type="button" class="baseBtn" value="editTemp" onclick="buttonPush('edit');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
            <gsmsg:write key="cmn.change" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backTemp');">
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
      <html:errors />
    </logic:messagesPresent>
  </div>
  <logic:equal name="man025Form" property="processMode" scope="request" value="addTemp">
    <div class="txt_l">
      <button type="button" class="baseBtn" value="<gsmsg:write key="main.man025.3" />" onClick="buttonPush('kakucho');">
        <gsmsg:write key="main.man025.3" />
      </button>
    </div>
  </logic:equal>
  <table class="table-left w100">
    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.date2" />
      </th>
      <td class="w70">
        <html:select name="man025Form" property="man025HltMonth">
          <html:optionsCollection name="man025Form" property="man025MonthLabel" value="value" label="label" />
        </html:select>
        <html:select name="man025Form" property="man025HltDay">
          <html:optionsCollection name="man025Form" property="man025DayLabel" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w30">
        <gsmsg:write key="cmn.holiday.name" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w70">
        <html:text property="man025HltName" styleClass="wp300" size="60" maxlength="20"  />
      </td>
    </tr>
    <tr>
      <th class="w30">
        <gsmsg:write key="main.src.man024kn.4" />
      </th>
      <td class="w70">
        <div class="verAlignMid">
          <html:multibox name="man025Form" property="man025FuriFlg" styleId="man025FuriFlg" value="0" />
          <label for="man025FuriFlg"><gsmsg:write key="main.man025.4" /></label>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <logic:equal name="man025Form" property="processMode" scope="request" value="addTemp">
      <button type="submit" class="baseBtn" value="addTemp">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <gsmsg:write key="cmn.add" />
      </button>
    </logic:equal>
    <logic:equal name="man025Form" property="processMode" scope="request" value="editTemp">
      <button type="button" class="baseBtn" value="editTemp" onclick="buttonPush('edit');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
        <gsmsg:write key="cmn.change" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backTemp');">
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