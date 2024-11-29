<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<!DOCTYPE html>

<%
    String maxLengthBiko = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAX_LENGTH_ENT_BIKO);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="main.man002.21" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn160.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body class="body_03" onload="showLengthId($('#inputstr')[0], <%= maxLengthBiko %>, 'inputlength');">

<html:form action="/common/cmn160">
<html:hidden property="cmn160InitFlg" />
<html:hidden property="cmn160LogoName" />
<html:hidden property="cmn160LogoSaveName" />
<html:hidden property="cmn160TempSetFlg" />
<html:hidden property="cmn160TourokuKbn" />
<html:hidden property="cmn160DspLogoKbn" />
<html:hidden property="cmn160DbLogoKbn" />
<html:hidden property="cmn160MenuLogoName" />
<html:hidden property="cmn160MenuLogoSaveName" />
<html:hidden property="cmn160MenuTempSetFlg" />
<html:hidden property="cmn160MenuDspLogoKbn" />
<html:hidden property="cmn160MenuDbLogoKbn" />
<html:hidden property="cmn160ThemePath" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<input type="hidden" name="CMD" value="">


<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.admin.setting" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man002.21" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirmEnterprise');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdmMenu');">
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
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.company.name" />
      </th>
      <td class="w75">
        <html:text property="cmn160ComName" styleClass="w100" maxlength="50" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.cmn160.1" />
      </th>
      <td class="w75">
        <html:text property="cmn160ComNamekn" styleClass="w100" maxlength="100" />
        <div>
          <gsmsg:write key="cmn.cmn160.2" />
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.login" /><gsmsg:write key="cmn.display" /> <gsmsg:write key="cmn.logo" />
      </th>
      <td class="w75">
        <div>
          <logic:empty name="cmn160Form" property="cmn160LogoName">
            <img class="logo_background" src="../common/images/login/logo.png" name="pitctImage" alt="<gsmsg:write key="cmn.logo" />">
          </logic:empty>
          <logic:notEmpty name="cmn160Form" property="cmn160LogoName">
            <logic:equal name="cmn160Form" property="cmn160DspLogoKbn" value="0">
              <img class="logo_background" src="../common/images/login/logo.png" name="pitctImage" alt="<gsmsg:write key="cmn.logo" />">
            </logic:equal>
            <logic:equal name="cmn160Form" property="cmn160DspLogoKbn" value="1">
              <img class="logo_background" src="../common/cmn160.do?CMD=getImageFile" alt="<gsmsg:write key="cmn.original.logo" />">
            </logic:equal>
          </logic:notEmpty>
        </div>
        <div>
          <gsmsg:write key="cmn.change.logo" />
        </div>
        <div>
          &nbsp;<gsmsg:write key="cmn.cmn160.4" />
        </div>
        <attachmentFile:filearea
          mode="<%= String.valueOf(GSConstCommon.CMN110MODE_GAZOU) %>"
          pluginId="<%= GSConst.PLUGINID_COMMON %>"
          tempDirId="cmn160"
          tempDirPlus="cmn160Logo"
          delBtn="true"
          delBtnAction="buttonPush('defaultLogo');"
          fileList="false"
          formId="1" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="man.menu" /> <gsmsg:write key="cmn.logo" />
      </th>
      <td class="w75">
        <div>
          <logic:empty name="cmn160Form" property="cmn160MenuLogoName">
            <img class="btn_classicImg-display bgC_menu" src="../common/images/classic/menu_logo1.png" name="pitctMenuImage" alt="<gsmsg:write key="cmn.logo" />">
            <img class="btn_originalImg-display menu_header" src="../common/images/original/menu_logo.png" name="pitctMenuImage" alt="<gsmsg:write key="cmn.logo" />">
          </logic:empty>

          <logic:notEmpty name="cmn160Form" property="cmn160MenuLogoName">
            <logic:equal name="cmn160Form" property="cmn160MenuDspLogoKbn" value="0">
              <img class="btn_classicImg-display bgC_menu" src="../common/images/classic/menu_logo1.png" name="pitctMenuImage" alt="<gsmsg:write key="cmn.logo" />">
              <img class="btn_originalImg-display menu_header" src="../common/images/original/menu_logo.png" name="pitctMenuImage" alt="<gsmsg:write key="cmn.logo" />">
            </logic:equal>
            <logic:equal name="cmn160Form" property="cmn160MenuDspLogoKbn" value="1">
              <img src="../common/cmn160.do?CMD=getMenuImageFile" class="bgC_menu p5alt="<gsmsg:write key="cmn.original.logo" />">
            </logic:equal>
          </logic:notEmpty>
        </div>
        <div>
          <gsmsg:write key="cmn.change.menu.logo" />
        </div>
        <div>
          &nbsp;<gsmsg:write key="cmn.cmn160.5" />
        </div>
        <attachmentFile:filearea
          mode="<%= String.valueOf(GSConstCommon.CMN110MODE_GAZOU) %>"
          pluginId="<%= GSConst.PLUGINID_COMMON %>"
          tempDirId="cmn160"
          tempDirPlus="cmn160MenuLogo"
          delBtn="true"
          delBtnAction="buttonPush('defaultMenuLogo');"
          fileList="false"
          formId="2" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.url" />
      </th>
      <td class="w75">
        <html:text property="cmn160Url" styleClass="w100" maxlength="100" />
        <div>
          <gsmsg:write key="cmn.change.logo.link" />
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.cmn160.3" />
      </th>
      <td class="w75">
        <logic:notEmpty name="cmn160Form" property="cmn160MonthList">
          <html:select name="cmn160Form" property="cmn160Kisyu">
            <html:optionsCollection name="cmn160Form" property="cmn160MonthList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w75">
        <textarea name="cmn160Biko" rows="5" class="w100" onkeyup="showLengthStr(value, <%= maxLengthBiko %>, 'inputlength');" id="inputstr" /><bean:write name="cmn160Form" property="cmn160Biko" /></textarea>
        <br>
        <span class="formCounter"><gsmsg:write key="wml.js.15" /></span><span id="inputlength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthBiko %>&nbsp;<gsmsg:write key="cmn.character" /></span>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirmEnterprise');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAdmMenu');">
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