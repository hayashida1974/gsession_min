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
<meta name="format-detection" content="telephone=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <gsjsmsg:js filename="gsjsmsg.js"/>
    <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script type="text/javascript" src="../ringi/js/rng180.js?<%= GSConst.VERSION_PARAM %>"></script>
    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
    <title>GROUPSESSION <gsmsg:write key="rng.62" /> <gsmsg:write key="cmn.preferences" /></title>
  </head>
  <body>
    <html:form action="/ringi/rng180">
      <input type="hidden" name="CMD" value="">
      <%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
      <html:hidden property="backScreen" />
      <html:hidden property="rngTemplateMode" />
      <html:hidden property="rng180initFlg" />
      <input type="hidden" name="rng180sinseiKbn" value="">
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
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.preferences" />
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="okClick('confirm');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                <gsmsg:write key="cmn.ok" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backMenu');">
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
          <tr>
            <th class="w25">
            <gsmsg:write key="cmn.delete" />
            </th>
            <td class="w75">
              <span class="verAlignMid">
                <html:radio name="rng180Form" styleId="rng180delKbn_01" property="rng180delKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_DEL_AUTH_ADM) %>" />
                <label for="rng180delKbn_01"><gsmsg:write key="cmn.delete.only.admin" /></label>
                <html:radio styleClass="ml10" name="rng180Form" styleId="rng180delKbn_02" property="rng180delKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_DEL_AUTH_UNRESTRICTED) %>" />
                <label for="rng180delKbn_02"><gsmsg:write key="man.no.limit" /></label>
              </span>
            </td>
          </tr>
          <!-- 汎用稟議 -->
          <tr>
            <th class="w25">
              <gsmsg:write key="rng.rng180.06" />
            </th>
            <td class="w75">
              <span class="verAlignMid">
                <html:radio name="rng180Form" styleId="rng180HanyoFlg_01" property="rng180HanyoFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_HANYO_FLG_YES) %>" onclick="dspPersonalTemplate()"/>
                <label for="rng180HanyoFlg_01"><gsmsg:write key="rng.rng180.05" /></label>
                <html:radio styleClass="ml10" name="rng180Form" styleId="rng180HanyoFlg_02" property="rng180HanyoFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_HANYO_FLG_NO) %>" onclick="dspPersonalTemplate()"/>
                <label for="rng180HanyoFlg_02"><gsmsg:write key="rng.rng180.03" /></label>
              </span>
            </td>
          </tr>
          <!-- 個人テンプレート -->
          <tr id="template">
            <th class="w25">
              <gsmsg:write key="cmn.personal.template" />
            </th>
            <td class="w75">
              <span class="verAlignMid">
                <html:radio name="rng180Form" styleId="rng180TemplateKbn_01" property="rng180TemplatePFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_TEMPLATE_PERSONAL_FLG_YES) %>" />
                <label for="rng180TemplateKbn_01"><gsmsg:write key="rng.rng180.05" /></label>
                <html:radio styleClass="ml10" name="rng180Form" styleId="rng180TemplateKbn_02" property="rng180TemplatePFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_TEMPLATE_PERSONAL_FLG_NO) %>" />
                <label for="rng180TemplateKbn_02"><gsmsg:write key="rng.rng180.03" /></label>
              </span>
            </td>
          </tr>
          <!-- 個人経路 -->
          <tr>
            <th class="w25">
              <gsmsg:write key="rng.rng180.07" />
            </th>
            <td class="w75">
              <span class="verAlignMid">
                <html:radio name="rng180Form" styleId="rng180KeiroKbn_01" property="rng180KeiroPFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_KEIRO_PERSONAL_FLG_YES) %>" />
                <label for="rng180KeiroKbn_01"><gsmsg:write key="rng.rng180.05" /></label>
                <html:radio styleClass="ml10" name="rng180Form" styleId="rng180KeiroKbn_02" property="rng180KeiroPFlg" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_KEIRO_PERSONAL_FLG_NO) %>" />
                <label for="rng180KeiroKbn_02"><gsmsg:write key="rng.rng180.03" /></label>
              </span>
            </td>
          </tr>
          <!-- 申請ID -->
          <tr>
            <th class="w25">
              <gsmsg:write key="rng.rng180.04" />
            </th>
            <td class="w75">
              <span class="verAlignMid">
                <logic:equal name="rng180Form" property="rng180sinseiKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_NONE) %>">
                  <input type="radio"  id="yes" name="use" value="yes">
                  <label for="yes"><gsmsg:write key="rng.rng180.05" /></label>
                  <input class="ml10" type="radio"  id="no" name="use" value="no" checked>
                  <label for="no"><gsmsg:write key="rng.rng180.03" /></label>
                </logic:equal>
                <logic:notEqual name="rng180Form" property="rng180sinseiKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_NONE) %>">
                  <input type="radio"  id="yes" name="use" value="yes" checked>
                  <label for="yes"><gsmsg:write key="rng.rng180.05" /></label>
                  <input class="ml10" type="radio"  id="no" name="use" value="no">
                  <label for="no"><gsmsg:write key="rng.rng180.03" /></label>
                </logic:notEqual>
              </span>
            </td>
          </tr>
          <!-- 申請ID選択 -->
          <tr id="sinseiArea">
            <th class="w25">
             <gsmsg:write key="rng.rng280.01" />
            </th>
            <td class="w75">
              <span class="verAlignMid">
                <logic:notEqual name="rng180Form" property="rng180sinseiKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_TOUITU) %>">
                  <input type="radio"  id="temp" name="select" checked value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_TEMP) %>">
                  <label for="temp"><gsmsg:write key="rng.rng180.02" /></label>
                  <input type="radio"  class="ml10"  id="all" name="select" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_TOUITU) %>">
                  <label for="all"><gsmsg:write key="rng.rng180.01" /></label>
                </logic:notEqual>
                <logic:equal name="rng180Form" property="rng180sinseiKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_TOUITU) %>">
                  <input type="radio"  id="temp" name="select" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_TEMP) %>">
                  <label for="temp"><gsmsg:write key="rng.rng180.02" /></label>
                  <input type="radio"  class="ml10" id="all" name="select" checked value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_TOUITU) %>">
                  <label for="all"><gsmsg:write key="rng.rng180.01" /></label>
                </logic:equal>
                <span id="selectTemp" class="ml10 verAlignMid">
                  <gsmsg:write key="rng.rng280.02" />
                  <html:select property="rng180defaultId" styleClass="ml5">
                    <html:optionsCollection property="rng180TempList" value="value" label="label" />
                  </html:select>
                </span>
              </span>
            </td>
          </tr>
          <!-- 重複ID -->
          <tr id="tyoufukuArea">
            <th class="w25">
               <gsmsg:write key="rng.rng210.05" />
            </th>
            <td class="w75">
              <span class="verAlignMid">
                <html:radio name="rng180Form" styleId="rng180OKbn_02" property="rng180Overlap" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_KYOKA) %>" />
                <label for="rng180OKbn_02"><gsmsg:write key="cmn.permit" /></label>
                <html:radio styleClass="ml10" name="rng180Form" styleId="rng180OKbn_03" property="rng180Overlap" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_NOT_KYOKA) %>" />
                <label for="rng180OKbn_03"><gsmsg:write key="cmn.not.permit" /></label>
              </span>
            </td>
          </tr>
        </table>
        <div class="footerBtn_block">
          <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="okClick('confirm');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backMenu');">
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