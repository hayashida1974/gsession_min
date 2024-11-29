<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<!DOCTYPE html>
<% int tmodeAll = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_ALL; %>
<% int tmodeShare = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_SHARE; %>
<% int tmodePrivate = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_PRIVATE; %>
<% int tCmdAdd = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_ADD; %>
<% int tCmdEdit = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_EDIT; %>
<html:html>
  <head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Content-Script-Type" content="text/javascript">
    <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script type="text/javascript" src="../ringi/js/rng060.js?<%= GSConst.VERSION_PARAM %>"></script>
    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
    <title>GROUPSESSION <gsmsg:write key="rng.56" /></title>
  </head>
  <body>
    <html:form action="ringi/rng060">
      <input type="hidden" name="CMD" value="">
      <%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
      <html:hidden property="backScreen" />
      <html:hidden property="rngTemplateMode" />
      <html:hidden property="rngTplCmdMode" />
      <input type="hidden" name="rngSelectTplSid" value="-1">
      <html:hidden property="rng010TransitionFlg" />
      <input type="hidden" name="rng060TemplateMode" value="">
      <input type="hidden" name="rng090CatSid" value="">
      <input type="hidden" name="rng140ProcMode" value="">
      <input type="hidden" name="rng140CatSid" value="">
      <input type="hidden" name="helpPrm" value="<bean:write name="rng060Form" property="rngTemplateMode" />">
      <bean:define id="rngTemplateMode" name="rng060Form" property="rngTemplateMode" />
      <% int rtMode = ((Integer) rngTemplateMode).intValue(); %>
      <%
         String mode_style="pageTitle";
         String mode_kanri_style="kanriPageTitle";
         if (rtMode == tmodeShare || rtMode == tmodePrivate) {
             mode_style = mode_kanri_style;
         }
      %>
      <!-- BODY -->
      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
      <div class="<%= mode_style %> w80 mrl_auto">
        <ul>
            <!-- 内容テンプレート選択  -->
            <% if (rtMode == tmodeAll) { %>
              <li>
                <img class="header_pluginImg-classic" src="../ringi/images/classic/header_ringi.png" alt="<gsmsg:write key="rng.62" />">
                <img class="header_pluginImg" src="../ringi/images/original/header_ringi.png" alt="<gsmsg:write key="rng.62" />">
              </li>
              <li>
                <gsmsg:write key="rng.62" />
              </li>
              <li class="pageTitle_subFont">
                <gsmsg:write key="rng.56" />
              </li>
            <!-- 管理者設定、個人設定  -->
            <% } else { %>
                <!-- 管理者 -->
                <% if (rtMode == tmodeShare) { %>
                 <li>
                   <img class="header_pluginImg-classic" src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" >
                   <img class="header_pluginImg" src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" >
                 </li>
                 <li>
                  <gsmsg:write key="cmn.admin.setting" />
                 </li>
                 <li class="pageTitle_subFont">
                  <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.rng060.07" />
                 </li>
                <!-- 個人 -->
                <% } else { %>
                 <li>
                   <img class="header_pluginImg-classic" src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" >
                   <img class="header_pluginImg" src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" >
                 </li>
                 <li>
                  <gsmsg:write key="cmn.preferences2" />
                 </li>
                 <li class="pageTitle_subFont">
                  <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.personal.template" />
                 </li>
                <% }%>
            <% } %>
            <li>
              <div>
                <!-- テンプレート追加ボタン -->
                <logic:equal name="rng060Form" property="rtpAddable" value="1">
                  <% if (rtMode == tmodeShare) { %>
                    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add.template" />" onClick="selectTemplate('-1', <%= tCmdAdd %>, 'rng090', '<bean:write name="rng060Form" property="rng060SelectCat" />');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                      <gsmsg:write key="cmn.add" />
                    </button>
                  <% } else { %>
                    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add.template" />" onClick="selectTemplate('-1', <%= tCmdAdd %>, 'rng090', '<bean:write name="rng060Form" property="rng060SelectCatUsr" />');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add.template" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add.template" />">
                      <gsmsg:write key="cmn.add" />
                    </button>
                  <% } %>
                </logic:equal>
                <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('060back');">
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
          <!-- 内容テンプレート選択  -->
          <% if (rtMode == tmodeAll) { %>
            <gsmsg:write key="rng.rng060.04" />
          <!-- 管理者設定、個人設定  -->
          <% } else { %>
            <gsmsg:write key="rng.rng060.03" />
          <% } %>
        </div>
        <logic:messagesPresent message="false">
          <html:errors />
        </logic:messagesPresent>
        <!-- 共有テンプレート -->
        <% if (rtMode == tmodeAll || rtMode == tmodeShare) { %>
          <% if (rtMode == tmodeShare) { %>
            <logic:notEqual name="rng060Form" property="rng060SelectCat" value="-1">
              <div class="txt_l">
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="return sortUp(<%= String.valueOf(tmodeShare) %>);">
                  <gsmsg:write key="cmn.up" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="return sortDown(<%= String.valueOf(tmodeShare) %>);">
                  <gsmsg:write key="cmn.down" />
                </button>
              </div>
            </logic:notEqual>
          <% } %>
          <table class="table-left mt5">
            <tr>
              <th class="w15 txt_c">
                <gsmsg:write key="cmn.category.select" />
              </th>
              <td>
                <html:select property="rng060SelectCat" onchange="buttonPush('init');">
                  <html:optionsCollection property="rng060CategoryList" value="value" label="label" />
                </html:select>
              </td>
              <!-- 共有テンプレート選択 -->
              <logic:equal name="rng060Form" property="rtcEditable" value="1">
                <!-- カテゴリ編集可能 -->
                <logic:notEqual name="rng060Form" property="rng060SelectCat" value="0">
                  <logic:notEqual name="rng060Form" property="rng060SelectCat" value="-1">
                    <td class="wp150 txt_c">
                      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.04" />" onClick="addEditCategory('<bean:write name="rng060Form" property="rng060SelectCat" />', <%= tCmdEdit %>, 'addeditcategory');">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="rng.04" />">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="rng.04" />">
                        <gsmsg:write key="rng.04" />
                      </button>
                    </td>
                  </logic:notEqual>
                </logic:notEqual>
                <td class="wp150 txt_c">
                  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.categorylist" />" onClick="buttonPush('rng150');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_list.png" alt="<gsmsg:write key="cmn.categorylist" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.categorylist" />">
                    <gsmsg:write key="cmn.categorylist" />
                  </button>
                </td>
                <td class="wp150 txt_c">
                  <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng060.02" />" onClick="addEditCategory('-1', <%= tCmdAdd %>, 'addeditcategory');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_add_folder.png" alt="<gsmsg:write key="rng.rng060.02" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_add_folder.png" alt="<gsmsg:write key="rng.rng060.02" />">
                    <gsmsg:write key="rng.rng060.02" />
                  </button>
                </td>
              </logic:equal>
            </tr>
          </table>
          <table class="table-top">
            <tr>
              <% if (rtMode == tmodeShare) { %>
                    <th class="w5 table_title-color js_sort_radio_are"></th>
                    <th class="w20 table_title-color ">
                      <gsmsg:write key="cmn.category.belong" />
                    </th>
                    <th class="w75 table_title-color ">
                      <gsmsg:write key="cmn.shared.template" />
                    </th>
              <% } else { %>
                    <th class="w20 table_title-color">
                      <gsmsg:write key="cmn.category.belong" />
                    </th>
                    <th  class="w80 table_title-color">
                      <gsmsg:write key="cmn.shared.template" />
                    </th>
              <% } %>
            </tr>
            <logic:iterate id="template1" name="rng060Form" property="rng060tplListShare" indexId="idx">
              <bean:define id="rtpSid" name="template1" property="rtpSid" type="java.lang.Integer" />
              <% String strRtpSid = String.valueOf(rtpSid.intValue()); %>
              <tr class="js_listHover cursor_p" id="<%= strRtpSid %>">
                <% if (rtMode == tmodeShare) { %>
                  <td class="js_sort_radio_are w5 txt_c">
                    <html:radio property="rng060SortRadio" value="<%= strRtpSid %>" />
                  </td>
                  <td class="w20 js_listClick">
                    <bean:write name="template1" property="rtcName" />
                  </td>
                  <td class="w75 cl_linkDef js_listClick">
                    <bean:write name="template1" property="rtpTitle" />
                  </td>
                <% } else { %>
                  <td class="txt_l w20 js_listClick">
                    <bean:write name="template1" property="rtcName" />
                  </td>
                  <td class="txt_l w80 cl_linkDef js_listClick">
                    <bean:write name="template1" property="rtpTitle" />
                  </td>
                <% } %>
              </tr>
            </logic:iterate>
          </table>
        <% } %>
        <!-- 個人テンプレート -->
        <% if (rtMode == tmodeAll || rtMode == tmodePrivate) { %>
          <% if (rtMode == tmodePrivate) { %>
            <logic:notEqual name="rng060Form" property="rng060SelectCatUsr" value="-1">
              <div class="txt_l">
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="return sortUp(<%= String.valueOf(tmodePrivate) %>);">
                  <gsmsg:write key="cmn.up" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="return sortDown(<%= String.valueOf(tmodePrivate) %>);">
                  <gsmsg:write key="cmn.down" />
                </button>
              </div>
            </logic:notEqual>
            <% } %>
            <logic:equal name="rng060Form" property="rng060TemplatePersonalFlg" value="1">
              <table class="table-left">
                <tr>
                  <th class="w15 txt_c">
                    <gsmsg:write key="cmn.category.select" />
                  </th>
                  <td>
                    <html:select property="rng060SelectCatUsr" onchange="buttonPush('init');">
                      <html:optionsCollection property="rng060CategoryListPrivate" value="value" label="label" />
                    </html:select>
                  </td>
                  <!-- 個人テンプレート選択 -->
                  <logic:equal name="rng060Form" property="rtcEditable" value="1">
                    <!-- カテゴリ編集可能 -->
                    <logic:notEqual name="rng060Form" property="rng060SelectCatUsr" value="0">
                      <logic:notEqual name="rng060Form" property="rng060SelectCatUsr" value="-1">
                        <td class="wp150 txt_c">
                          <button type="button" class="baseBtn" value="<gsmsg:write key="rng.04" />" onClick="addEditCategory('<bean:write name="rng060Form" property="rng060SelectCatUsr" />', <%= tCmdEdit %>, 'addeditcategory');">
                            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="rng.04" />">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="rng.04" />">
                            <gsmsg:write key="rng.04" />
                          </button>
                        </td>
                      </logic:notEqual>
                    </logic:notEqual>
                    <td class="wp150 txt_c">
                      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.categorylist" />" onClick="buttonPush('rng150');">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_list.png" alt="<gsmsg:write key="cmn.categorylist" />">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.categorylist" />">
                        <gsmsg:write key="cmn.categorylist" />
                      </button>
                    </td>
                    <td class="wp150 txt_c">
                      <button type="button" class="baseBtn" value="<gsmsg:write key="rng.rng060.02" />" onClick="addEditCategory('-1', <%= tCmdAdd %>, 'addeditcategory');">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_add_folder.png" alt="<gsmsg:write key="rng.rng060.02" />">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_add_folder.png" alt="<gsmsg:write key="rng.rng060.02" />">
                        <gsmsg:write key="rng.rng060.02" />
                      </button>
                    </td>
                  </logic:equal>
                </tr>
              </table>
            <table class="table-top w100">
              <tr>
                <% if (rtMode != tmodeAll) { %>
                  <th class="w5 table_title-color js_sort_radio_are_usr"></th>
                  <th class="w20 table_title-color">
                    <gsmsg:write key="cmn.category.belong" />
                  </th>
                  <th class="w75 table_title-color">
                    <gsmsg:write key="cmn.personal.template" />
                  </th>
                <% } else { %>
                  <th class="w20 table_title-color">
                    <gsmsg:write key="cmn.category.belong" />
                  </th>
                  <th class="w80 table_title-color">
                    <gsmsg:write key="cmn.personal.template" />
                  </th>
                <% } %>
              </tr>
              <logic:iterate id="template2" name="rng060Form" property="rng060tplListPrivate" indexId="idx">
                <bean:define id="rtpSidPrivate" name="template2" property="rtpSid" type="java.lang.Integer" />
                <% String strRtpSidPrivate = String.valueOf(rtpSidPrivate.intValue()); %>
                <tr class="js_listHover cursor_p" id="<%= strRtpSidPrivate %>">
                  <% if (rtMode == tmodePrivate) { %>
                    <td class="w5 js_sort_radio_are_usr txt_c">
                      <html:radio property="rng060SortRadioPrivate" value="<%= strRtpSidPrivate %>" />
                    </td>
                    <td class="w20 js_listClick cursor_p">
                      <bean:write name="template2" property="rtcName" />
                    </td>
                    <td class="w75 cl_linkDef js_listClick cursor_p">
                      <bean:write name="template2" property="rtpTitle" />
                    </td>
                  <% } else { %>
                    <td class="txt_l w20 js_listClick cursor_p">
                      <bean:write name="template2" property="rtcName" />
                    </td>
                    <td class="txt_l w80 cl_linkDef js_listClick cursor_p">
                      <bean:write name="template2" property="rtpTitle" />
                    </td>
                  <% } %>
                </tr>
              </logic:iterate>
            </table>
          </logic:equal>
        <% } %>
        <div class="footerBtn_block">
          <!-- テンプレート追加ボタン -->
          <logic:equal name="rng060Form" property="rtpAddable" value="1">
            <% if (rtMode == tmodeShare) { %>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add.template" />" onClick="selectTemplate('-1', <%= tCmdAdd %>, 'rng090', '<bean:write name="rng060Form" property="rng060SelectCat" />');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <gsmsg:write key="cmn.add" />
              </button>
            <% } else { %>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add.template" />" onClick="selectTemplate('-1', <%= tCmdAdd %>, 'rng090', '<bean:write name="rng060Form" property="rng060SelectCatUsr" />');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add.template" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add.template" />">
                <gsmsg:write key="cmn.add" />
              </button>
            <% } %>
          </logic:equal>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('060back');">
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