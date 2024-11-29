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
<meta name="format-detection" content="telephone=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>GROUPSESSION <gsmsg:write key="cmn.cmn160kn.1" /></title>
    <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
  </head>

  <body class="body_03">

    <html:form action="/common/cmn160kn">

      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

      <input type="hidden" name="CMD" value="">
      <html:hidden property="cmn160InitFlg" />
      <html:hidden property="cmn160ComName" />
      <html:hidden property="cmn160ComNamekn" />
      <html:hidden property="cmn160Url" />
      <html:hidden property="cmn160Kisyu" />
      <html:hidden property="cmn160Biko" />
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


      <div class="kanriPageTitle w80 mrl_auto">
        <ul>
          <li>
            <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
          </li>
          <li><gsmsg:write key="cmn.admin.setting" /></li>
          <li class="pageTitle_subFont">
            <gsmsg:write key="cmn.cmn160kn.1" />
          </li>
          <li>
            <div>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('cmn160knDecision');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                <img class="btn_originalImg-display" src="../	common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                <gsmsg:write key="cmn.final" />
              </button>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cmn160knBack');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.back" />
              </button>
            </div>
          </li>
        </ul>
      </div>
      <div class="wrapper w80 mrl_auto">
        <table class="table-left">
          <!--  会社名 -->
          <tr>
            <th class="w25" >
              <gsmsg:write key="cmn.company.name" />
            </th>
            <td class="w75">
              <bean:write name="cmn160knForm" property="cmn160ComName" />
            </td>
          </tr>

          <!--  会社名かな -->
          <tr>
            <th class="w25" >
              <gsmsg:write key="cmn.cmn160.1" />
            </th>
            <td class="w75">
              <bean:write name="cmn160knForm" property="cmn160ComNamekn" />
            </td>
          </tr>

          <!-- ロゴ ログイン画面 -->
          <tr>
            <th class="w25" >
              <gsmsg:write key="cmn.login" /><gsmsg:write key="cmn.display" /><gsmsg:write key="cmn.logo" />
            </th>
            <td class="w75">
              <logic:empty name="cmn160knForm" property="cmn160LogoName">
                <img class="logo_background" src="../common/images/login/logo.png" name="pitctImage" alt="<gsmsg:write key="cmn.logo" />" border="1">
              </logic:empty>
              <logic:notEmpty name="cmn160knForm" property="cmn160LogoName">
                <logic:equal name="cmn160Form" property="cmn160DspLogoKbn" value="0">
                  <img class="logo_background" src="../common/images/login/logo.png" name="pitctImage" alt="<gsmsg:write key="cmn.logo" />" border="1">
                </logic:equal>
                <logic:equal name="cmn160Form" property="cmn160DspLogoKbn" value="1">
                  <img src="../common/cmn160.do?CMD=getImageFile" alt="<gsmsg:write key="cmn.original.logo" />" border="1">
                </logic:equal>
              </logic:notEmpty>
            </td>
          </tr>

          <!-- ロゴ メニュー -->
          <tr>
            <th class="w25" >
              <gsmsg:write key="man.menu" /> <gsmsg:write key="cmn.logo" />
            </th>
            <td class="w75">
              <logic:empty name="cmn160knForm" property="cmn160MenuLogoName">
                <img class="btn_classicImg-display bgC_menu" src="../common/images/classic/menu_logo1.png" name="pitctMenuImage" alt="<gsmsg:write key="cmn.logo" />">
                <img class="btn_originalImg-display menu_header" src="../common/images/original/menu_logo.png" name="pitctMenuImage" alt="<gsmsg:write key="cmn.logo" />">
              </logic:empty>
              <logic:notEmpty name="cmn160knForm" property="cmn160MenuLogoName">
                <logic:equal name="cmn160Form" property="cmn160MenuDspLogoKbn" value="0">
                  <img class="btn_classicImg-display bgC_menu" src="../common/images/classic/menu_logo1.png" name="pitctMenuImage" alt="<gsmsg:write key="cmn.logo" />">
                  <img class="btn_originalImg-display menu_header" src="../common/images/original/menu_logo.png" name="pitctMenuImage" alt="<gsmsg:write key="cmn.logo" />">
                </logic:equal>
                <logic:equal name="cmn160Form" property="cmn160MenuDspLogoKbn" value="1">
                  <img src="../common/cmn160.do?CMD=getMenuImageFile" alt="<gsmsg:write key="cmn.original.logo" />" border="1">
                </logic:equal>
              </logic:notEmpty>
            </td>
          </tr>

          <!-- URL -->
          <tr>
            <th class="w25" >
              URL
            </th>
            <td class="w75">
              <bean:write name="cmn160knForm" property="cmn160Url" />
            </td>
          </tr>

          <!-- 期首月 -->
          <tr>
            <th class="w25" >
              <gsmsg:write key="cmn.cmn160.3" />
            </th>
            <td class="w75">
              <bean:write name="cmn160knForm" property="cmn160Kisyu" /><gsmsg:write key="cmn.month" />
            </td>
          </tr>

          <!-- 備考 -->
          <tr>
            <th class="w25" >
              <gsmsg:write key="cmn.memo" />
            </th>
            <td class="w75">
              <bean:write name="cmn160knForm" property="cmn160knBiko" filter="false" />
            </td>
          </tr>
        </table>
        <div class="footerBtn_block">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('cmn160knDecision');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <img class="btn_originalImg-display" src="../	common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <gsmsg:write key="cmn.final" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cmn160knBack');">
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