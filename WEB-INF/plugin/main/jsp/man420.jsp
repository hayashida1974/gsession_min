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
<title>GROUPSESSION <gsmsg:write key="main.man040.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man420.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-1.6.4.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onLoad="initLoad()">
<html:form action="/main/man420">
<input type="hidden" name="CMD" value="">
<html:hidden property="man420InitFlg"/>
<html:hidden property="man420ImportFolder"/>
<html:hidden property="man420ImpSuccessFolder"/>
<html:hidden property="man420ImpFailedFolder"/>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man420.2" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('setting');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('420_back');">
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
<table class="table-left">
    <tr>
      <th class="w15 no_w">
        <gsmsg:write key="main.man420.1" />
      </th>
      <td class="w85">
        <div class="verAlignMid">
          <html:radio styleId="usrNotImpRadio" name="man420Form" property="man420UsrImpFlg" value="0" onclick="hideUsrTimeSelect()"/>
          <label for="usrNotImpRadio">
            <gsmsg:write key="main.man420.7" />
          </label>
          <html:radio styleClass="ml10" styleId="usrImpRadio" name="man420Form" property="man420UsrImpFlg" value="1" onclick="hideUsrTimeSelect()"/>
          <label for="usrImpRadio" class="mr10">
            <gsmsg:write key="main.man420.8" />
          </label>
          <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
          &nbsp;<a href="../main/man420.do?CMD=man420_sample01&sample=1"><gsmsg:write key="user.91" /></a>
        </div>

        <div class="mt10">
          <b><gsmsg:write key="main.man420.10" /></b><br>
          <bean:write name="man420Form" property="man420ImportFolder" filter="true"/>
        </div>
        <div>
          <b><gsmsg:write key="main.man420.11" /></b><br>
          <bean:write name="man420Form" property="man420ImpSuccessFolder" filter="true"/>
        </div>
        <div>
          <b><gsmsg:write key="main.man420.12" /></b><br>
          <bean:write name="man420Form" property="man420ImpFailedFolder" filter="true"/>
        </div>
        <div class="settingForm_separator mt10 txt_l pt5">
          <button type="button" class="baseBtn" value="<gsmsg:write key="main.man040.2" />" onClick="buttonPush('usrExeConf');">
            <gsmsg:write key="main.man040.2" />
          </button>
        </div>
      </td>
    </tr>
    <tr id="usrStartTimeSelect">
      <th>
        <gsmsg:write key="cmn.import" /><gsmsg:write key="main.man080.1" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio styleId="usrImpFiveRadio" name="man420Form" property="man420UsrImpTimeSelect" value="0" onclick="hideUsrStartTime()"/>
          <label for="usrImpFiveRadio">
            <gsmsg:write key="main.man420.4" />
          </label>
          <html:radio styleClass="ml10" styleId="usrImpHourRadio" name="man420Form" property="man420UsrImpTimeSelect" value="1" onclick="hideUsrStartTime()"/>
          <label for="usrImpHourRadio">
            <gsmsg:write key="main.man420.5" />
          </label>
          <html:radio styleClass="ml10" styleId="usrImpPointRadio" name="man420Form" property="man420UsrImpTimeSelect" value="2" onclick="hideUsrStartTime()"/>
          <label for="usrImpPointRadio">
            <gsmsg:write key="main.man420.6" />
          </label>
        </div>
      </td>
    </tr>
    <tr id="usrStartTime">
      <th>
        <gsmsg:write key="cmn.starttime" />
      </th>
      <td>
        <html:select name="man420Form" property="man420UsrFrHour">
          <html:optionsCollection name="man420Form" property="man420UsrHourLabel" value="value" label="label" />
        </html:select>&nbsp;<gsmsg:write key="cmn.hour.input" /><gsmsg:write key="main.man040.3" />
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('setting');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('420_back');">
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