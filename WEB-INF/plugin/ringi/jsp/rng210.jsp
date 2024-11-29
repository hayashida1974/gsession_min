<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.rng.rng210.Rng210ListModel"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>
<!DOCTYPE html>
<html:html>
  <head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <gsjsmsg:js filename="gsjsmsg.js"/>
    <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../ringi/js/rng210.js?<%= GSConst.VERSION_PARAM %>"></script>
    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
    <title>GROUPSESSION <gsmsg:write key="rng.62" /> <gsmsg:write key="cmn.preferences" /></title>
  </head>
  <body>
    <html:form action="/ringi/rng210">
      <input type="hidden" name="CMD" value="">
      <%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
      <html:hidden property="backScreen"/>
      <input type="hidden" name="rng210Format" value="">
      <input type="hidden" name="rng210DispFormat" value="">
      <html:hidden property="rng210Cmd"/>
      <html:hidden property="rng200Sid"/>
      <input type="hidden" name="helpPrm" value="<bean:write name="rng210Form" property="rng210Cmd" />">
      <!-- BODY -->
      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
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
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><!--
            --><logic:equal name="rng210Form" property="rng210Cmd" value="0"><gsmsg:write key="rng.rng210.06" /></logic:equal><!--
            --><logic:equal name="rng210Form" property="rng210Cmd" value="1"><gsmsg:write key="rng.rng210.17" /></logic:equal>
          </li>
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="OkClick('ok');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                <gsmsg:write key="cmn.ok" />
              </button>
              <logic:equal name="rng210Form" property="rng210Cmd" value="1">
                <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
              </logic:equal>
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('rngIdMenu');">
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
          <html:errors />
        </logic:messagesPresent>
        <table class="table-left w100">
          <!-- タイトル -->
          <tr>
            <th class="w25">
              <gsmsg:write key="cmn.title" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
            </th>
            <td class="w75">
              <html:text styleId="titleId" name="rng210Form" property="rng210Title" maxlength="50" styleClass="wp550"/>
            </td>
          </tr>
          <!-- フォーマット -->
          <tr>
            <th class="w25">
              <gsmsg:write key="rng.12" />
            </th>
            <td class="w75">
              <div name="template" class="display_n" >
                <ringi:rng210_formatRow />
              </div>
              <div id="formatTable" class="cl_fontWarn fw_b">
                <gsmsg:write key="rng.rng210.12" />
                  <% String[] addParam = {"rng210SelectFormat", "rng210FormatWord"}; %>
                <logic:iterate id="rng210Format" name="rng210Form" property="rng210FormatList" indexId="lineIdx" length="10" type="Rng210ListModel">
                  <%-- 行番号 --%>
                  <% String lineNo = String.valueOf(lineIdx.intValue()); %>
                  <ringi:rng210_formatRow bean="<%=rng210Format %>" lineNo="<%=lineNo %>" />
                </logic:iterate>
              </div>
              <div>
                <div>
                  <span class="wp60 display_inline"><gsmsg:write key="rng.rng210.13" /></span>
                  <gsmsg:write key="cmn.colon" />
                  <span id="pattern"><bean:write name="rng210Form" property="rng210Pattern" /></span>
                </div>
                <span class="wp60 display_inline"><gsmsg:write key="cmn.example" /></span>
                <gsmsg:write key="cmn.colon" />
                <span id="dsp"><bean:write name="rng210Form" property="rng210DispFormat" /></span>
              </div>
            </td>
          </tr>
          <!-- 連番設定 -->
          <tr>
            <th rowspan="4" class="w25">
             <gsmsg:write key="rng.rng210.09" />
            </th>
          </tr>
          <tr>
            <td>
              <div id="ketaInfo" class="cl_fontWarn fw_b display_n">
                <gsmsg:write key="rng.rng210.14" />
              </div>
              <gsmsg:write key="rng.rng210.10" /><gsmsg:write key="cmn.colon" />
              <html:text styleId="ketaId" onblur="focusOut();" name="rng210Form" property="rng210Zeroume" maxlength="2" styleClass="wp50" />
              <gsmsg:write key="cmn.keta" />
            </td>
          </tr>
          <tr>
            <td>
              <div id="label">
                <gsmsg:write key="rng.rng210.01" /><gsmsg:write key="cmn.colon" />
                <span class="mr10 display_inline"><bean:write name="rng210Form" property="rng210Init" /></span>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="editClick();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit"/>">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                  <gsmsg:write key="cmn.edit" />
                </button>
              </div>
              <div id="text" class="display_n">
                <gsmsg:write key="rng.rng210.01" /><gsmsg:write key="cmn.colon" />
                <html:text styleId="sinseiId" onblur="focusOut();" name="rng210Form" property="rng210Init" maxlength="10" styleClass="wp100" />
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <span class="verAlignMid">
                <gsmsg:write key="rng.rng210.07" /><gsmsg:write key="cmn.colon" />
                <span class="mr5"></span>
                <html:radio name="rng210Form" styleId="rng210RKbn_01" property="rng210Reset" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_RESET_NONE) %>" />
                <label for="rng210RKbn_01"><gsmsg:write key="rng.rng210.08" /></label>
                <html:radio styleClass="ml10" name="rng210Form" styleId="rng210RKbn_02" property="rng210Reset" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_RESET_YEAR) %>" />
                <label for="rng210RKbn_02"><gsmsg:write key="cmn.year2" /></label>
                <html:radio styleClass="ml10" name="rng210Form" styleId="rng210RKbn_03" property="rng210Reset" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_RESET_MONTH) %>" />
                <label for="rng210RKbn_03"><gsmsg:write key="cmn.month" /></label>
                <html:radio styleClass="ml10" name="rng210Form" styleId="rng210RKbn_04" property="rng210Reset" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_RESET_DAY) %>" />
                <label for="rng210RKbn_04"><gsmsg:write key="cmn.day" /></label>
              </span>
            </td>
          </tr>
          <!-- 手入力変更 -->
          <tr>
            <th>
             <gsmsg:write key="rng.rng210.04" />
            </th>
            <td>
              <div class="fs_13">
                <gsmsg:write key="rng.rng210.21" />
              </div>
              <span class="verAlignMid">
                <html:radio name="rng210Form" styleId="rng210MKbn_01" property="rng210Manual" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_MANUAL_TEMPLATE) %>" />
                <label for="rng210MKbn_01"><gsmsg:write key="rng.rng210.03" /></label>
                <html:radio styleClass="ml10" name="rng210Form" styleId="rng210MKbn_02" property="rng210Manual" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_MANUAL_KYOKA) %>" />
                <label for="rng210MKbn_02"><gsmsg:write key="cmn.permit" /></label>
                <html:radio styleClass="ml10" name="rng210Form" styleId="rng210MKbn_03" property="rng210Manual" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SINSEI_MANUAL_NOT_KYOKA) %>" />
                <label for="rng210MKbn_03"><gsmsg:write key="cmn.not.permit" /></label>
            </span>
            </td>
          </tr>
        </table>
        <div class="footerBtn_block">
          <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="OkClick('ok');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>
          <logic:equal name="rng210Form" property="rng210Cmd" value="1">
            <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:equal>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('rngIdMenu');">
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