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
    <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
    <title>GROUPSESSION <gsmsg:write key="rng.62" /> <gsmsg:write key="cmn.preferences.kn" /></title>
  </head>
  <body>
    <html:form action="/ringi/rng180kn">
      <input type="hidden" name="CMD" value="">
      <%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
      <html:hidden property="backScreen" />
      <html:hidden property="rngTemplateMode" />
      <html:hidden property="rng180initFlg" />
      <html:hidden property="rng180delKbn" />
      <html:hidden property="rng180HanyoFlg" />
      <html:hidden property="rng180TemplatePFlg" />
      <html:hidden property="rng180KeiroPFlg" />

      <html:hidden property="rng180sinseiKbn" />
      <html:hidden property="rng180defaultId" />
      <html:hidden property="rng180Overlap" />
      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
      <!-- BODY -->
      <div class="kanriPageTitle w80 mrl_auto">
        <ul>
          <li>
            <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
          </li>
          <li>
            <gsmsg:write key="cmn.admin.setting" />
          </li>
          <li class="pageTitle_subFont">
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.preferences.kn" />
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
                <gsmsg:write key="cmn.final" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backInput');">
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
          <!-- 削除 -->
          <tr class="w100">
            <th class="w25">
              <gsmsg:write key="cmn.delete" />
            </th>
            <td class="w75">
              <logic:equal name="rng180knForm" property="rng180delKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_DEL_AUTH_ADM) %>">
                <gsmsg:write key="cmn.delete.only.admin" />
              </logic:equal>
              <logic:equal name="rng180knForm" property="rng180delKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_DEL_AUTH_UNRESTRICTED) %>">
                <gsmsg:write key="man.no.limit" />
              </logic:equal>
            </td>
          </tr>
          <!-- 汎用稟議テンプレート -->
          <tr class="w100">
            <th class="w25">
              <gsmsg:write key="rng.rng180.06" />
            </th>
            <td class="w75">
              <logic:equal name="rng180knForm" property="rng180HanyoFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_HANYO_FLG_YES) %>">
                <gsmsg:write key="rng.rng180.05" />
              </logic:equal>
              <logic:equal name="rng180knForm" property="rng180HanyoFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_HANYO_FLG_NO) %>">
                <gsmsg:write key="rng.rng180.03" />
              </logic:equal>
            </td>
          </tr>
          <!-- 個人テンプレート -->
          <logic:equal name="rng180knForm" property="rng180HanyoFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_HANYO_FLG_YES) %>">
          <tr class="w100">
            <th class="w25">
              <gsmsg:write key="cmn.personal.template" />
            </th>
            <td class="w75">
              <logic:equal name="rng180knForm" property="rng180TemplatePFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_TEMPLATE_PERSONAL_FLG_YES) %>">
                <gsmsg:write key="rng.rng180.05" />
              </logic:equal>
              <logic:equal name="rng180knForm" property="rng180TemplatePFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_TEMPLATE_PERSONAL_FLG_NO) %>">
                <gsmsg:write key="rng.rng180.03" />
              </logic:equal>
            </td>
          </tr>
          </logic:equal>
          <!-- 個人経路テンプレート -->
          <tr class="w100">
            <th class="w25">
              <gsmsg:write key="rng.rng180.07" />
            </th>
            <td class="w75">
              <logic:equal name="rng180knForm" property="rng180KeiroPFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_KEIRO_PERSONAL_FLG_YES) %>">
                <gsmsg:write key="rng.rng180.05" />
              </logic:equal>
              <logic:equal name="rng180knForm" property="rng180KeiroPFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_KEIRO_PERSONAL_FLG_NO) %>">
                <gsmsg:write key="rng.rng180.03" />
              </logic:equal>
            </td>
          </tr>
          <!-- 申請ID -->
          <tr class="w100">
            <th class="w25">
              <gsmsg:write key="rng.rng180.04" />
            </th>
            <td class="w75">
              <logic:equal name="rng180knForm" property="rng180sinseiKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_NONE) %>">
                <gsmsg:write key="rng.rng180.03" />
              </logic:equal>
              <logic:notEqual name="rng180knForm" property="rng180sinseiKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_NONE) %>">
                <gsmsg:write key="rng.rng180.05" />
              </logic:notEqual>
            </td>
          </tr>
          <logic:notEqual name="rng180knForm" property="rng180sinseiKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_NONE) %>">
          <!-- 申請ID選択 -->
          <tr class="w100">
            <th class="w25">
              <gsmsg:write key="rng.rng280.01" />
            </th>
            <td class="w75">
              <logic:notEqual name="rng180knForm" property="rng180sinseiKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_TOUITU) %>">
                <gsmsg:write key="rng.rng180.02" />
              </logic:notEqual>
              <logic:equal name="rng180knForm" property="rng180sinseiKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_TOUITU) %>">
                <gsmsg:write key="rng.rng180.01" />
                <div>
                  <gsmsg:write key="rng.rng280.02" />:<bean:write name="rng180knForm" property="rsv180knDefaultIdDsp"/>
                </div>
              </logic:equal>
            </td>
          </tr>
          <!-- 重複ID -->
          <tr class="w100">
            <th class="w25">
              <gsmsg:write key="rng.rng210.05" />
            </th>
            <td class="w75">
              <logic:equal name="rng180knForm" property="rng180Overlap" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_KYOKA) %>">
                <gsmsg:write key="cmn.permit" />
              </logic:equal>
              <logic:equal name="rng180knForm" property="rng180Overlap" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_NOT_KYOKA) %>">
                <gsmsg:write key="cmn.not.permit" />
              </logic:equal>
            </td>
          </tr>
          </logic:notEqual>
        </table>
        <div class="footerBtn_block">
          <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <gsmsg:write key="cmn.final" />
          </button>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backInput');">
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