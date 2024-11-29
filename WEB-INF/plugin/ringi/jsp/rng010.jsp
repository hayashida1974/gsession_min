<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.rng.RngConst" %>

<!DOCTYPE html>

<% int mode_jyusin = jp.groupsession.v2.rng.RngConst.RNG_MODE_JYUSIN; %>
<% int mode_sinsei = jp.groupsession.v2.rng.RngConst.RNG_MODE_SINSEI; %>
<% int mode_kanryo = jp.groupsession.v2.rng.RngConst.RNG_MODE_KANRYO; %>
<% int mode_soukou = jp.groupsession.v2.rng.RngConst.RNG_MODE_SOUKOU; %>
<% int mode_koetu = jp.groupsession.v2.rng.RngConst.RNG_MODE_KOETU; %>
<% String rngstatus_settlet = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_SETTLED); %>
<% String rngstatus_reject = String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_STATUS_REJECT); %>
<% int tmodeAll = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_ALL; %>
<% int tmodeShare = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_SHARE; %>

<% int sort_title = jp.groupsession.v2.rng.RngConst.RNG_SORT_TITLE; %>
<% int sort_name = jp.groupsession.v2.rng.RngConst.RNG_SORT_NAME; %>
<% int sort_appl = jp.groupsession.v2.rng.RngConst.RNG_SORT_DATE; %>
<% int sort_jyusin = jp.groupsession.v2.rng.RngConst.RNG_SORT_JYUSIN; %>
<% int sort_kakunin = jp.groupsession.v2.rng.RngConst.RNG_SORT_KAKUNIN; %>
<% int sort_touroku = jp.groupsession.v2.rng.RngConst.RNG_SORT_TOUROKU; %>
<% int order_asc = jp.groupsession.v2.rng.RngConst.RNG_ORDER_ASC; %>
<% int order_desc = jp.groupsession.v2.rng.RngConst.RNG_ORDER_DESC; %>

<bean:define id="rngDelAuth"  name="rng010Form" property="rng010delAuth" type="java.lang.Integer" />
<% boolean rngDelOk = false;%>
<% if (rngDelAuth.intValue() == jp.groupsession.v2.rng.RngConst.RAR_DEL_AUTH_UNRESTRICTED) { %>
<%     rngDelOk = true; %>
<% } %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">

<script type="text/javascript" src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../ringi/js/rng010.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../ringi/css/ringi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="rng.13" /></title>
</head>
<body>
<html:form action="ringi/rng010">
<input type="hidden" name="CMD" value="moveSearch">
<input type="hidden" name="rngCmdMode" value="0">
<input type="hidden" name="rngApprMode" value="0">
<input type="hidden" name="rng010userSid" value="0">
<input type="hidden" name="rngSelectTplSid" value="-1">
<input type="hidden" name="rng010TransitionFlg" value="0">

<html:hidden property="rngSid" value="-1" />
<html:hidden property="rngProcMode" />
<html:hidden property="rng010sortKey" />
<html:hidden property="rng010orderKey" />
<html:hidden property="rngTemplateMode" />
<html:hidden property="rng010adminFlg" />
<html:hidden property="rng010DairiFlg" />

<bean:define id="procMode" name="rng010Form" property="rngProcMode" />
<% int sMode = ((Integer) procMode).intValue(); %>
<input type="hidden" name="helpPrm" value="<%= sMode %>" >

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->
<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../ringi/images/classic/header_ringi.png" alt="<gsmsg:write key="rng.62" />">
      <img class="header_pluginImg" src="../ringi/images/original/header_ringi.png" alt="<gsmsg:write key="rng.62" />">
    </li>
    <li>
      <gsmsg:write key="rng.62" />
    </li>
    <li class="pageTitle_subFont">
    <% if (sMode == mode_jyusin) { %><!-- 受信モード -->
        <gsmsg:write key="cmn.receive" />
    <% } else if (sMode == mode_sinsei){ %><!-- 申請中モード -->
        <gsmsg:write key="rng.application.ongoing" />
    <% } else if (sMode == mode_kanryo){ %><!-- 完了モード -->
        <gsmsg:write key="cmn.complete" />
    <% } else if (sMode == mode_soukou){ %><!-- 草稿モード -->
        <gsmsg:write key="cmn.draft" />
    <% } else if (sMode == mode_koetu){ %><!-- 後閲モード -->
        <gsmsg:write key="rng.109" />
    <% } else { %>
        <gsmsg:write key="cmn.receive" />
    <% } %>
    </li>
    <li>
    </li>
  </ul>
</div>

<div class="wrapper_2column">
  <div class="side-left fs_13">
    <!-- アカウント -->
    <div id="rng_account_area" class="side_header side_header-folding cursor_p">
      <span class="side_headerTitle side_header-open fs_13">
        <span class="side_headerArrow"></span>
        <gsmsg:write key="wml.102" />
      </span>
    </div>
    <div id="rng_account_child_area" class="side_content">
      <html:select property="rng010ViewAccount" styleId="account_comb_box">
        <logic:notEmpty name="rng010Form" property="rng010AccountList">
          <logic:iterate id="accountMdl" name="rng010Form" property="rng010AccountList">
            <bean:define id="accoutVal" name="accountMdl" property="accountSid" type="java.lang.Integer" />
            <bean:define id="optClass" value="" />
            <logic:equal name="accountMdl" property="usrUkoFlg" value="1">
              <bean:define id="optClass" value="mukoUserOption" />
            </logic:equal>
            <option value="<%= String.valueOf(accoutVal) %>" class="<bean:write name="optClass" />"  <logic:equal name="rng010Form" property="rng010ViewAccount" value="<%= String.valueOf(accoutVal) %>">selected</logic:equal>><bean:write name="accountMdl" property="accountName" /></option>
          </logic:iterate>
        </logic:notEmpty>
      </html:select>
      <logic:notEmpty name="rng010Form" property="rng010AccountList">
        <bean:define id="viewAccoutVal" name="rng010Form" property="rng010ViewAccount" type="java.lang.Integer" />
        <logic:iterate id="accountMdl" name="rng010Form" property="rng010AccountList">
          <logic:notEqual name="accountMdl" property="accountSid" value="<%= String.valueOf(viewAccoutVal) %>">
            <div>
              <div class="js_side_accountName side_accountName verAlignMid w100 lh130 mb5 <logic:equal name="accountMdl" property="usrUkoFlg" value="1">mukoUser</logic:equal>" id="<bean:write name="accountMdl" property="accountSid" />">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_account_sel.png">
                <i class="btn_originalImg-display icon-account_sel"></i>
                <bean:write name="accountMdl" property="accountName" />
                <logic:notEqual name="accountMdl" property="accountCount" value="0">
                  (<bean:write name="accountMdl" property="accountCount" />)
                </logic:notEqual>
              </div>
            </div>
          </logic:notEqual>
        </logic:iterate>
      </logic:notEmpty>
    </div>

    <!-- フォルダ -->
    <div id="rng_folder_area" class="side_header cursor_p side_header-folding">
      <span class="side_headerTitle side_header-open fs_13">
        <span class="side_headerArrow"></span>
        <gsmsg:write key="cmn.folder" />
      </span>
    </div>

    <div id="rng_folder_child_area" class="side_content p0">
      <div class="side_folder-focus" onclick="changeMode(<%= mode_jyusin %>, <%= sort_jyusin %>, <%= order_asc %>);">
        <div class="side_folderImg side_folderImg-lineTop classic-display"></div>
        <div class="side_folderImg side_folderImg-jushin"></div>
        <div id="menu_jushin_txt" class="side-folderText">
          <gsmsg:write key="cmn.receive" />
          <logic:notEqual name="rng010Form" property="rng010JusinCnt" value="0">
            (<bean:write name="rng010Form" property="rng010JusinCnt"/>)
          </logic:notEqual>
        </div>
      </div>
      <div>
        <div class="side_folder-focus" onclick="changeMode(<%= mode_sinsei %>, <%= sort_kakunin %>, <%= order_desc %>);">
          <div class="side_folderImg side_folderImg-line classic-display"></div>
          <div class="side_folderImg side_folderImg-soshin"></div>
          <div class="side-folderText" id="1">
            <gsmsg:write key="rng.application.ongoing" />
          </div>
          <input type="hidden" name="left_menu_label_name" value="1">
        </div>
      </div>
      <div>
        <div class="side_folder-focus" onclick="changeMode(<%= mode_kanryo %>, <%= sort_kakunin %>, <%= order_desc %>);">
          <div class="side_folderImg side_folderImg-line classic-display"></div>
          <div class="side_folderImg side_folderImg-kanryo"></div>
          <div class="side-folderText" id="1">
            <gsmsg:write key="cmn.complete" />
          </div>
          <input type="hidden" name="left_menu_label_name" value="1">
        </div>
      </div>
      <logic:equal name="rng010Form" property="rng010DairiFlg" value="0">
        <div>
          <div class="side_folder-focus" onclick="changeMode(<%= mode_soukou %>, <%= sort_touroku %>, <%= order_desc %>);">
            <div class="side_folderImg side_folderImg-line classic-display"></div>
            <div class="side_folderImg side_folderImg-folder"></div>
            <div class="side-folderText" id="1">
              <gsmsg:write key="cmn.draft" />
              <logic:notEqual name="rng010Form" property="rng010SoukouCnt" value="0">
                (<bean:write name="rng010Form" property="rng010SoukouCnt"/>)
              </logic:notEqual>
            </div>
          </div>
          <input type="hidden" name="left_menu_label_name" value="1">
        </div>
      </logic:equal>
      <div>
        <input type="hidden" name="left_menu_label_name" value="1">
        <div class="side_folder-focus" onclick="changeMode(<%= mode_koetu %>, <%= sort_jyusin %>, <%= order_asc %>);">
          <div class="side_folderImg side_folderImg-lineBottom classic-display"></div>
          <div class="side_folderImg side_folderImg-koetu"></div>
          <div class="side-folderText" id="1">
            <gsmsg:write key="rng.109" />
          </div>
          <input type="hidden" name="left_menu_label_name" value="1">
        </div>
      </div>
    </div>

    <!-- 共有テンプレート -->
    <logic:equal name="rng010Form" property="rng010DairiFlg" value="0">

      <div id="rng_template_area" class="side_header cursor_p side_header-folding">
        <span class="side_headerTitle side_header-open fs_13 txt_m">
          <span class="side_headerArrow"></span>
          <gsmsg:write key="cmn.shared.template" />
          <span id="template_add" onclick="buttonPush('editTemplate');"></span>
          <span class="side_editBtn flo_r mr5 cl_fontMiddle classic-display side_confGear" id="template_edit" onclick="tempEdit(<%= tmodeShare %>,'tempEdit');">
            <gsmsg:write key="cmn.edit"/>
          </span>
          <span class="side_editBtn flo_r mr5 fs_18 original-display side_confGear" id="template_edit" onclick="tempEdit(<%= tmodeShare %>,'tempEdit');">
            <i class="icon-setting"></i>
          </span>
        </span>
      </div>

      <div id="rng_template_child_area" class="side_content">
        <table>
          <tbody>
            <gsmsg:write key="cmn.category.select" />
            <html:select property="rng010SelectCategory" onchange="buttonPush('init');">
              <html:optionsCollection property="rng010CategoryList" value="value" label="label" />
            </html:select>
            <logic:notEmpty name="rng010Form" property="rng010TemplateList">
              <logic:iterate id="template" name="rng010Form" property="rng010TemplateList">
                <a id='' href="#" onclick="changeCategory('category', <bean:write name="template" property="rtpSid" />);">
                  <div class="lh130 mb5">
                    <bean:write name="template" property="rtpTitle" />
                  </div>
                </a>
              </logic:iterate>
            </logic:notEmpty>
          </tbody>
        </table>
      </div>

      <!-- 個人テンプレート -->
      <logic:equal  name="rng010Form" property="rng010TemplatePersonalFlg" value="1">
        <div id="rng_template_user_area" class="side_header cursor_p side_header-folding">
          <span class="side_headerTitle side_header-open fs_13">
            <span class="side_headerArrow"></span>
            <gsmsg:write key="cmn.personal.template" />
            <span id="template_user_add" onclick="buttonPush('editTemplate');"></span>
          </span>
        </div>
        <div id="rng_template_user_child_area" class="side_content">
          <table>
            <tbody>
              <gsmsg:write key="cmn.category.select" />
              <br>
              <div>
                <html:select property="rng010SelectCategoryUser" onchange="buttonPush('init');">
                  <html:optionsCollection property="rng010CategoryListUser" value="value" label="label" />
                </html:select>
                <logic:notEmpty name="rng010Form" property="rng010TemplateListUser">
                  <logic:iterate id="template" name="rng010Form" property="rng010TemplateListUser">
                    <a href="#" onclick="changeCategory('category', <bean:write name="template" property="rtpSid" />);">
                      <div class="lh130 mb5">
                        <bean:write name="template" property="rtpTitle" />
                      </div>
                    </a>
                  </logic:iterate>
                </logic:notEmpty>
              </div>
            </tbody>
          </table>
        </div>
      </logic:equal>
    </logic:equal>
  </div>

  <!-- 右 -->
  <div class="main">
    <table class="pageTitle_sub">
      <tr>
        <td>
          <span class="sub_title">
            <logic:equal name="rng010Form" property="rngProcMode" value="<%= String.valueOf(RngConst.RNG_MODE_JYUSIN) %>">
              <gsmsg:write key="cmn.receive" />
            </logic:equal>
            <logic:equal name="rng010Form" property="rngProcMode" value="<%= String.valueOf(RngConst.RNG_MODE_SINSEI) %>">
              <gsmsg:write key="rng.application.ongoing" />
            </logic:equal>
            <logic:equal name="rng010Form" property="rngProcMode" value="<%= String.valueOf(RngConst.RNG_MODE_KANRYO) %>">
              <gsmsg:write key="cmn.complete" />
            </logic:equal>
            <logic:equal name="rng010Form" property="rngProcMode" value="<%= String.valueOf(RngConst.RNG_MODE_SOUKOU) %>">
              <gsmsg:write key="cmn.draft" />
            </logic:equal>
            <logic:equal name="rng010Form" property="rngProcMode" value="<%= String.valueOf(RngConst.RNG_MODE_KOETU) %>">
              <gsmsg:write key="rng.109" />
            </logic:equal>
          </span>
        </td>
        <td>
          <span id="simpleSearch">
            <html:text name="rng010Form" property="rngKeyword" styleClass="search_form" maxlength="100" />
            <button type="submit" value="<gsmsg:write key="cmn.advanced.search" />" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
              <gsmsg:write key="cmn.search" />
            </button>
          </span>
          <button type="button" value="<gsmsg:write key="cmn.advanced.search" />" onClick="buttonPush('detailedSearch');" class="baseBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_advanced_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <gsmsg:write key="cmn.advanced.search" />
          </button>
          <logic:equal name="rng010Form" property="rng010DairiFlg" value="0">
            <button type="button" class="baseBtn" name="btn_shinsei" value="<gsmsg:write key="rng.rng010.02" />" onClick="template(<%= tmodeAll %>, 'rngEdit')">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="rng.rng010.02" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="rng.rng010.02" />">
              <gsmsg:write key="rng.rng010.02" />
            </button>
            <% if ((sMode == mode_kanryo && rngDelOk) || sMode == mode_soukou) { %>
              <button type="button" name="btn_delete" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete')">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            <% } %>
          </logic:equal>
        </td>
      </tr>
    </table>
    <table class="w100">
      <tr>
        <td>
          <div>
            <gsmsg:write key="cmn.category.select" />
            <html:select property="rng010SearchCategory" onchange="categorySearch();">
              <html:optionsCollection property="rng010CategoryList" value="value" label="label" />
            </html:select>
          </div>
        </td>
        <td>
          <!-- ページング -->
          <logic:notEmpty name="rng010Form" property="pageList">
            <div class="paging">
              <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                <i class="icon-paging_left"></i>
              </button>
              <html:select styleClass="paging_combo"  property="rng010pageTop" onchange="selectPage(0);">
                <html:optionsCollection name="rng010Form" property="pageList" value="value" label="label" />
              </html:select>
              <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                <i class="icon-paging_right"></i>
              </button>
            </div>
          </logic:notEmpty>
        </td>
      </tr>
    </table>
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
    <% if (sMode == mode_jyusin) { %>
      <!-- 受信モード -->
      <jsp:include page="/WEB-INF/plugin/ringi/jsp/rng010_receive.jsp" />
    <% } else if (sMode == mode_sinsei) { %>
      <!-- 進行中 -->
      <jsp:include page="/WEB-INF/plugin/ringi/jsp/rng010_progress.jsp" />
    <% } else if (sMode == mode_kanryo) { %>
      <!-- 完了 -->
      <jsp:include page="/WEB-INF/plugin/ringi/jsp/rng010_done.jsp" />
    <% } else if (sMode == mode_soukou) { %>
      <!-- 草稿 -->
      <jsp:include page="/WEB-INF/plugin/ringi/jsp/rng010_draft.jsp" />
    <% } else if (sMode == mode_koetu) { %>
      <!-- 後閲 -->
      <jsp:include page="/WEB-INF/plugin/ringi/jsp/rng010_post.jsp" />
    <% } %>
  </div>
</div>
<span id="tooltip_area"></span>
</html:form>
<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
</body>
</html:html>