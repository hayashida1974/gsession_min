<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/common/" prefix="common" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.wml.wml010.Wml010Const" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% String jusin = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.DIR_TYPE_RECEIVE); %>
<% String sosin = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.DIR_TYPE_SENDED); %>
<% String yoyakuSosin = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.DIR_TYPE_NOSEND); %>
<% String soko = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.DIR_TYPE_DRAFT); %>
<% String gomi = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.DIR_TYPE_DUST); %>
<% String hokan = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.DIR_TYPE_STORAGE); %>

<% String maxLengthTitle = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.MAXLEN_NAME); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clockpiker/clockpiker.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.contextmenu.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tinymce-5.10.3/tinymce.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<jsp:include page="/WEB-INF/plugin/webmail/jsp/wml010_message.jsp" />

<%
  jp.co.sjts.util.date.UDate nowDate = new jp.co.sjts.util.date.UDate();
  String timeStr = nowDate.getTimeStamp();
%>

<script src="../common/js/toastDisplay.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/autoSave.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../webmail/js/wml010/util.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../webmail/js/wml010.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../webmail/js/wml010/editorUtil.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../webmail/js/wml010/dialog.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/common.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>


<logic:notEmpty name="wml010Form" property="wml010theme" scope="request">
  <bean:define id="selectThemePath" name="wml010Form" property="wml010theme" type="String"/>
  <theme:css filename="theme.css" selectthemepath="<%= selectThemePath %>" />
</logic:notEmpty>
<logic:empty name="wml010Form" property="wml010theme" scope="request">
  <theme:css filename="theme.css"/>
</logic:empty>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<title>GROUPSESSION <gsmsg:write key="sml.19" /></title>
</head>

<body id="wml010body" onload="showLengthId($('#inputstr')[0], <bean:write name="wml010Form" property="wml010maxBodySize"/>, 'inputlength');" onunload="windowClose();">

<html:form action="/webmail/wml010" styleId="webmailForm" onsubmit="javascript:return false;">

<input type="hidden" name="CMD" value="">

<!-- ヘルプパラメータの設定 -->
<input type="hidden" name="helpPrm" value="0">

<input type="hidden" name="wmlAccountMode" value="0">
<input type="hidden" name="detailSearchFlg" value="1">
<input type="hidden" name="wml010downloadFileId" value="">
<input type="hidden" name="wml010svSendContent" value="">
<input type="hidden" name="wml010sendContentCursor" value="">
<input type="hidden" name="wml010sendContentCursorType" value="">
<input type="hidden" name="wml010sendMessageNum" value="">
<input type="hidden" name="wml010sendMailType" value="">
<input type="hidden" name="wml010tempDirId" value="">

<input type="hidden" name="wml010selectMessageNum" value="">
<input type="hidden" name="dirTypeEsc" value="">
<input type="hidden" name="selectMailNumEsc" value="">
<input type="hidden" name="editMailNumEsc" value="">


<html:hidden property="wml010pluginAddressUse" />
<html:hidden property="wml010pluginUserUse" />
<html:hidden property="wml010pluginCircularUse" />
<html:hidden property="wml010pluginSmailUse" />
<html:hidden property="wml010pluginFilekanriUse" />

<html:hidden property="wml010viewDirectory" />
<html:hidden property="wml010viewDirectoryType" />
<html:hidden property="wml010viewLabel" />
<input type="hidden" name="wml010viewDelMail" value="0">
<html:hidden property="wml010viewAccountAddress" />
<html:hidden property="wml010viewAccountAutoSave" />
<html:hidden property="wml010selectPage" />
<html:hidden property="wml010sortKey" />
<html:hidden property="wml010order" />
<html:hidden property="wml010searchType" />
<html:hidden property="wml010searchSortKey" />
<html:hidden property="wml010searchOrder" />
<html:hidden property="wml010checkAddress" />
<html:hidden property="wml010checkFile" />

<html:hidden property="wml010svSearchKeywordNml" />
<html:hidden property="wml010svSearchFrom" />
<html:hidden property="wml010svSearchTo" />
<html:hidden property="wml010svSearchToKbnTo" />
<html:hidden property="wml010svSearchToKbnCc" />
<html:hidden property="wml010svSearchToKbnBcc" />
<html:hidden property="wml010svSearchDateType" />
<html:hidden property="wml010svSearchDateYearFr" />
<html:hidden property="wml010svSearchDateMonthFr" />
<html:hidden property="wml010svSearchDateDayFr" />
<html:hidden property="wml010svSearchDateYearTo" />
<html:hidden property="wml010svSearchDateMonthTo" />
<html:hidden property="wml010svSearchDateDayTo" />
<html:hidden property="wml010svSearchTempFile" />
<html:hidden property="wml010svSearchKeywordKbn" />
<html:hidden property="wml010svSearchKeyword" />
<html:hidden property="wml010svSearchReadKbn" />
<html:hidden property="wml010downloadMessageNum" value=""/>

<input type="hidden" name="wml010sendSignOld" value="0">
<input type="hidden" name="wml010shareMailNum" value="">
<html:hidden property="wml010maxBodySize" styleId="wmlBodyLimit" />

<html:hidden property="wml010smlShareTitle"/>
<html:hidden property="wml010smlShareBody"/>
<html:hidden property="wml010smlShareTemp"/>
<html:hidden property="wml010smlShareFlg"/>
<html:hidden property="wml010smlShareCloseFlg"/>
<html:hidden property="wml010smlShareHtml"/>
<input type="hidden" name="wml010smlShareInit" value="0">

<input type="hidden" name="compressFileType" value="">
<input type="hidden" name="compressFileDef" value="">
<input type="hidden" name="compressFilePlan" value="">
<input type="hidden" name="wml010sendMailPlanDateYear" value="" />
<input type="hidden" name="wml010sendMailPlanDateMonth" value="" />
<input type="hidden" name="wml010sendMailPlanDateDay" value="" />
<input type="hidden" name="wml010sendMailPlanDateHour" value="" />
<input type="hidden" name="wml010sendMailPlanDateMinute" value="" />
<input type="hidden" name="hourDivision" value="5" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../webmail/images/classic/header_webmail.png" alt="<gsmsg:write key="wml.wml010.25" />">
      <img class="header_pluginImg" src="../webmail/images/original/header_webmail.png" alt="<gsmsg:write key="wml.wml010.25" />">
    </li>
    <li><gsmsg:write key="wml.wml010.25" /></li>
    <li>
      <div>
        <logic:equal name="wml010Form" property="wml010smlShareFlg" value="0">

          <button class="baseBtn" value="<gsmsg:write key="cmn.advanced.search" />" onClick="viewSearchDetail();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_advanced_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <gsmsg:write key="cmn.advanced.search" />
          </button>

          <button class="baseBtn" value="<gsmsg:write key="cmn.account" />" onClick="moveAccountConf();">
            <img class="btn_classicImg-display"  src="../common/images/classic/icon_account_setting.png" alt="<gsmsg:write key="cmn.account" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_account_setting.png" alt="<gsmsg:write key="cmn.account" />">
            <gsmsg:write key="cmn.account" />
          </button>
        </logic:equal>
        <logic:notEqual name="wml010Form" property="wml010smlShareFlg" value="0">
          <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" id="shareClose">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
            <gsmsg:write key="cmn.close" />
          </button>
        </logic:notEqual>
      </div>
    </li>
  </ul>
</div>

<common:toast toastId="autoSave">
  <gsmsg:write key="wml.305" />
</common:toast>

<div id="warnDiskArea" class="warn_disk_area bgC_body display_none">
  <img class="btn_classicImg-display" src="../main/images/classic/icon_log_warn.gif" alt="<gsmsg:write key="cmn.warning" />">
  <img class="btn_originalImg-display" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
  <gsmsg:write key="wml.251" /><span id="warnDiskRatio"></span><gsmsg:write key="wml.252" />
</div>


<div id="top_detailSearch" class="display_none">
  <table class="table-left w100">
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.sendfrom" />
      </th>
      <td class="w35">
        <html:text name="wml010Form" property="wml010searchFrom" styleClass="w80" />
      </td>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="wml.wml010.01" />
      </th>
      <td class="w35">
        <span class="verAlignMid">
          <html:radio name="wml010Form" property="wml010searchReadKbn" styleId="searchReadKbn0" value="<%= String.valueOf(Wml010Const.SEARCH_READKBN_NOSET) %>" />
          <label for="searchReadKbn0" class="mr10"><gsmsg:write key="cmn.not.specified" /></label>
          <html:radio name="wml010Form" property="wml010searchReadKbn" styleId="searchReadKbn1" value="<%= String.valueOf(Wml010Const.SEARCH_READKBN_NOREAD) %>" />
          <label for="searchReadKbn1" class="mr10"><gsmsg:write key="cmn.read.yet" /></label>
          <html:radio name="wml010Form" property="wml010searchReadKbn" styleId="searchReadKbn2" value="<%= String.valueOf(Wml010Const.SEARCH_READKBN_READED) %>" />
          <label for="searchReadKbn2"><gsmsg:write key="cmn.read.already" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="wml.send.dest" />
      </th>
      <td class="w35">
        <span class="verAlignMid">
          <html:checkbox property="wml010searchToKbnTo" styleId="wml010destTo" value="1" />
          <label for="wml010destTo" class="mr10"><gsmsg:write key="cmn.from" /></label>
          <html:checkbox property="wml010searchToKbnCc" styleId="wml010destCc" value="1" />
          <label for="wml010destCc" class="mr10"><gsmsg:write key="cmn.cc" /></label>
          <html:checkbox property="wml010searchToKbnBcc" styleId="wml010destBcc" value="1" />
          <label for="wml010destBcc"><gsmsg:write key="cmn.bcc" /></label>
        </span>
        <html:text name="wml010Form" property="wml010searchTo"  styleClass="w80" />
      </td>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.attach.file" />
      </th>
      <td class="w35">
        <span class="verAlignMid">
          <html:checkbox name="wml010Form" property="wml010searchTempFile" styleId="searchTempFile" value="1" />
          <label for="searchTempFile"><gsmsg:write key="wml.wml010.06" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.date2" />
      </th>
      <td class="w85" colspan="3">
        <span class="verAlignMid">
          <html:radio name="wml010Form" property="wml010searchDateType" styleId="searchDate0" value="<%= String.valueOf(Wml010Const.SEARCH_DATE_NOSET) %>" onclick="changeSearchDateType();" />
          <label for="searchDate0" class="mr10"><gsmsg:write key="cmn.not.specified" /></label>
          <html:radio name="wml010Form" property="wml010searchDateType" styleId="searchDate1" value="<%= String.valueOf(Wml010Const.SEARCH_DATE_SET) %>" onclick="changeSearchDateType();" />
          <label for="searchDate1"><gsmsg:write key="wml.wml010.12" /></label>
        </span>
        <div class="settingForm_separator" id="searchDateArea">
          <span class="verAlignMid">
            <span class="fw_b"><gsmsg:write key="wml.wml010.38" /><gsmsg:write key="wml.215" /></span>
            <html:text name="wml010Form" property="wml010searchDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatefr" />
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>

            <span class="ml20 fw_b"><gsmsg:write key="wml.wml010.39" /><gsmsg:write key="wml.215" /></span>
            <html:text name="wml010Form" property="wml010searchDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateto" />
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
          </span>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.keyword" />
      </th>
      <td class="w85" colspan="3">
        <html:select name="wml010Form" property="wml010searchKeywordKbn" styleClass="wp120" styleId="wmlSearchKeywordKbn">
          <html:optionsCollection name="wml010Form" property="keywordCombo" value="value" label="label" />
        </html:select>
        <html:text name="wml010Form" property="wml010searchKeyword" maxlength="100" styleClass="w60" />
      </td>
    </tr>
  </table>

  <div class="txt_c">
    <button type="button" class="baseBtn" id="head_menu_search_btn2" value="<gsmsg:write key="cmn.search" />" onClick="return searchResultLoadInit(<%= String.valueOf(Wml010Const.SEARCHTYPE_DETAIL) %>);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
      <gsmsg:write key="cmn.search" />
    </button>

    <button type="button" class="baseBtn" name="btn_prjadd" value="<gsmsg:write key="cmn.cancel" />" onClick="return viewSearchDetail();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.cancel" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.cancel" />">
      <gsmsg:write key="cmn.cancel" />
    </button>
  </div>
</div>

<div id="webmailErrMsg" class="w100"></div>

<div class="wrapper_2column">

  <div>
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </div>

  <div class="side-left fs_13 border_none">

    <div class="m0 p0 txt-l border_none tabBody">
      <div class="ml10 pb5">
        <a href="../webmail/wml010.do" target="_blank" class="fw_b webmail_outwindow cl_linkDef"><gsmsg:write key="wml.wml010.24" /></a>
      </div>
    </div>

    <div id="wml_account_area" class="side_header cursor_p side_header-folding bor_t1 bor_l1">
      <span class="side_headerTitle side_header-open fs_13">
        <span class="side_headerArrow"></span>
        <gsmsg:write key="cmn.account" />
      </span>
    </div>
    <div id="wml_account_child_area" class="border_none">
      <div class="side_content bor_l1 bor_r1 p5">
        <input type="hidden" id="wmlViewAccountSv" value="<bean:write name="wml010Form" property="wmlViewAccount" />" />
        <html:select property="wmlViewAccount" styleId="account_comb_box" styleClass="wp190" onchange="changeAccount();">
          <html:optionsCollection name="wml010Form" property="accountCombo" value="value" label="label" />
        </html:select>
        <div>
          <logic:notEmpty name="wml010Form" property="accountLinkList">
            <logic:iterate id="accountData" name="wml010Form" property="accountLinkList">
              <div class="js_side_accountName side_accountName word_b-all" data-accountid="<bean:write name="accountData" property="wacSid" />">
                <div class="verAlignMid lh130">
                  <img class="classic-display" src="../common/images/classic/icon_account_sel.png" >
                  <i class="original-display icon-account_sel"  ></i>
                  <div class="ml5">
                    <a href="#" class="cl_linkDef fw_b"><bean:write name="accountData" property="wacName" /></a>
                    <logic:notEqual name="accountData" property="notReadCount" value="0">
                      <wbr><span class="ml5 fs_11 fw_b">(<bean:write name="accountData" property="notReadCount" />)</span>
                    </logic:notEqual>
                  </div>
                </div>
              </div>
            </logic:iterate>
          </logic:notEmpty>
        </div>
      </div>

      <%--新着メールを確認 --%>
      <div class="table_title-color p5 bor_l1 bor_r1 bor_b1">
        <button type="button" class="mailMenu_button" value="<gsmsg:write key="wml.196" />" onClick="getNewMail();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_send_mail.png" alt="<gsmsg:write key="wml.196" />">
          <img class="btn_originalImg-display" src="../webmail/images/original/icon_new_arrival.png" alt="<gsmsg:write key="wml.196" />">
          <gsmsg:write key="wml.196" />
        </button>
      </div>

      <%-- ディレクトリ・ラベル --%>
      <div class="side_content p0 bor_l1 bor_r1">
        <div id="wmllist_folder_1" class="side_folder-focus" onclick="javascript:changeDirectory(<%= String.valueOf(jusin) %>);">
          <div class="side_folderImg side_folderImg-lineTop classic-display"></div>
          <div class="side_folderImg side_folderImg-mail-jushin"></div>
          <div id="menu_folder_txt_1" class="side-folderText js_file_hover js_menu_folder">
            <gsmsg:write key="cmn.receive" />&nbsp;<span id="wmllist_folder_count_1" class="fs_11 fw_b"></span>
            <input type="hidden" name="menu_folder_txt_1_id" value="" />
          </div>
        </div>
        <div id="wmllist_folder_2" class="side_folder-focus" onclick="javascript:changeDirectory(<%= String.valueOf(sosin) %>);">
          <div class="side_folderImg side_folderImg-line classic-display"></div>
          <div class="side_folderImg side_folderImg-mail-soshin"></div>
          <div id="menu_folder_txt_2" class="side-folderText js_file_hover js_menu_folder" folderid="">
            <gsmsg:write key="wml.19" />&nbsp;<span id="wmllist_folder_count_2" class="fs_11 fw_b"></span>
            <input type="hidden" name="menu_folder_txt_2_id" value="" />
          </div>
        </div>
        <div id="wmllist_folder_3" class="side_folder-focus" onclick="javascript:changeDirectory(<%= String.valueOf(yoyakuSosin) %>);">
          <div class="side_folderImg side_folderImg-line classic-display"></div>
          <div class="side_folderImg side_folderImg-mail-koetu"></div>
          <div id="menu_folder_txt_3" class="side-folderText js_file_hover js_menu_folder" folderid="">
            <gsmsg:write key="wml.211" />&nbsp;<span id="wmllist_folder_count_3" class="fs_11 fw_b"></span>
            <input type="hidden" name="menu_folder_txt_3_id" value="" />
          </div>
        </div>
        <div id="wmllist_folder_4" class="side_folder-focus" onclick="javascript:changeDirectory(<%= String.valueOf(soko) %>);">
          <div class="side_folderImg side_folderImg-line classic-display"></div>
          <div class="side_folderImg side_folderImg-mail-folder"></div>
          <div id="menu_folder_txt_4" class="side-folderText js_file_hover js_menu_folder" folderid="">
          <gsmsg:write key="cmn.draft" />&nbsp;<span id="wmllist_folder_count_4" class="fs_11 fw_b"></span></div>
            <input type="hidden" name="menu_folder_txt_4_id" value="" />
        </div>

        <div id="wmllist_folder_5" class="side_folder-focus" onclick="javascript:changeDirectory(<%= String.valueOf(gomi) %>);">
          <div class="side_folderImg side_folderImg-line classic-display"></div>
          <div class="side_folderImg side_folderImg-mail-dust"></div>
          <div id="menu_folder_txt_5" class="side-folderText js_file_hover js_menu_folder" folderid="">
            <gsmsg:write key="cmn.trash" />&nbsp;<span id="wmllist_folder_count_5" class="fs_11 fw_b"></span>
            <input type="hidden" name="menu_folder_txt_5_id" value="" />
            <span>
              &nbsp;[<a href="#!" class="" id="head_menu_empty_trash_btn"><gsmsg:write key="sml.sml010.07" /></a>]
            </span>
          </div>
        </div>

        <% String leftLineClass = "side_folderImg-lineBottom classic-display";%>
        <logic:notEmpty name="wml010Form" property="labelCombo">
          <% leftLineClass = "side_folderImg-line";%>
        </logic:notEmpty>

        <div id="wmllist_folder_7" class="side_folder-focus" onclick="javascript:changeDirectory(<%= String.valueOf(hokan) %>);">
          <div id="hokan_bottom_div" class="side_folderImg side_folderImg-line classic-display <%= leftLineClass %>"></div>
          <div class="side_folderImg side_folderImg-mail-folder"></div>
          <div id="menu_folder_txt_7" class="side-folderText js_file_hover js_menu_folder" folderid="">
            <gsmsg:write key="cmn.strage" />&nbsp;&nbsp;<span id="wmllist_folder_count_7" class="fs_11 fw_b"></span>
            <input type="hidden" name="menu_folder_txt_7_id" value="" />
          </div>
        </div>

        <%-- ラベル  --%>
        <div id="labelArea" class="m0 p0 lh100"></div>

        <div class='contextMenu' id='context_menu1'>
          <ul>
            <li id='all_read' class="fs_12 pt0 pb0"><gsmsg:write key="cmn.all" /><gsmsg:write key="cmn.mark.read" /></li>
            <li id='all_no_read' class="fs_12 mt0 pt0 pb0"><gsmsg:write key="cmn.all" /><gsmsg:write key="wml.js.10" /></li>
          </ul>
        </div>
        <div class='contextMenu' id='context_menu2'>
          <ul>
            <li id='mail_line_reply' class="fs_12 pt0 pb0"><gsmsg:write key="cmn.reply" /></li>
            <li id='mail_line_allreply' class="fs_12 pt0 pb0"><gsmsg:write key="wml.reply.all" /></li>
            <li id='mail_line_forward' class="fs_12 pt0 pb0"><gsmsg:write key="cmn.forward" /></li>
            <li id='mail_line_delete' class="fs_12 pt0 pb0"><gsmsg:write key="cmn.delete" /></li>
            <li id='mail_read' class="fs_12 pt0 pb0"><gsmsg:write key="cmn.mark.read" /></li>
            <li id='mail_no_read' class="fs_12 pt0 pb0"><gsmsg:write key="wml.js.10" /></li>
          </ul>
        </div>

      </div>
    </div>

    <%--ユーザ情報 --%>
    <div id="wml_shain_area" class="side_header cursor_p side_header-folding bor_l1">
      <span class="side_headerTitle side_header-close fs_13">
        <span class="side_headerArrow"></span>
        <gsmsg:write key="cmn.shain.info" />
      </span>
    </div>

    <div id="wml_shain_child_area" class="side_content pl0 pr0 bor_l1" style="overflow: hidden; display: none;">
      <div class="bor_b1">
        <div class="verAlignMid w100 pl10 pr10 pb5">
          <html:select name="wml010Form" property="wml010shainGroup" styleId="wml010ChangeGrp" styleClass="wp130 m0">
            <logic:notEmpty name="wml010Form" property="shainGroupCombo">
              <logic:iterate id="gpBean" name="wml010Form" property="shainGroupCombo" scope="request">
                <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
              </logic:iterate>
            </logic:notEmpty>
          </html:select>
          <span class="ml5">
          <button class="iconBtn-border" type="button" id="wml010GroupBtn" value="&nbsp;&nbsp;" onClick="openGroupWindow(this.form.wml010shainGroup, 'wml010shainGroup', '0', 'changeGrp', '1', 'fakeGrpButton');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
            <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
          </button>
          <span class="mrl_auto"></span>
        </div>
        <input type="button" class="display_none" id="fakeGrpButton" name="fakeGrpButton" />
      </div>

      <div id="selGrpUsrArea" class="pl0 pr0 pt10 word_b-all">
      </div>

    </div>

    <%--アドレス帳 --%>
    <div id="wml_addressbook_area" class="side_header cursor_p side_header-folding bor_l1">
      <span class="side_headerTitle side_header-close fs_13">
        <span class="side_headerArrow"></span>
        <gsmsg:write key="addressbook" />
      </span>
    </div>

    <div id="wml_addressbook_child_area" class="side_content pl0 pr0 bor_l1" style="overflow: hidden; display: none;">
      <div class="bor_b1">
        <div class="w100 pl10 pr10">
          <div class="w100 txt_c verAlignMid">
            <input type="radio" name="wml010addressType" value="1" onclick="redrawAddressList();" id="adrType0">
            <label for="adrType0" class="mr10"><gsmsg:write key="cmn.all" /></label>
            <input type="radio" name="wml010addressType" value="0" checked="checked" onclick="redrawAddressList();" id="adrType1">
            <label for="adrType1"><gsmsg:write key="wml.wml010.07" /></label>
          </div>
        </div>
        <div class="pl10 pr10 pb5">
          <input type="text" class="wp100" name="wml010searchTextAddressList" />
          <button type="button" class="baseBtn" id="wml_addressbook_search_btn" value="<gsmsg:write key="cmn.search" />" onclick="searchAddressList();">
            <gsmsg:write key="cmn.search" />
          </button>
        </div>
      </div>

      <div id="selAddressbookArea" class="pl0 pr0 pt10 word_b-all">
      </div>

    </div>

    <%--送信先リスト --%>
    <div id="wml_destlist_area" class="side_header cursor_p side_header-folding bor_l1">
      <span class="side_headerTitle side_header-close fs_13">
        <span class="side_headerArrow"></span>
        <gsmsg:write key="wml.262" />
      </span>
    </div>

    <div id="wml_destlist_child_area" class="side_content pl0 pr0 bor_l1" style="overflow: hidden; display: none;">
      <div id="selDestlistArea" class="pl0 pr0 pt5 word_b-all">
      </div>
    </div>


  </div>
  <div class="main">

    <!-- タブヘッダー -->
    <ul class="tabHeader fs_13 w100">
      <li id="mail_list_tab" class="tabHeader_tab-on bgI_none bgC_header2 js_mailAreaHead2 pt5 pr10 pl10" >
        <bean:define id="tabDirType" name="wml010Form" property="wml010viewDirectoryType" />
        <% if (tabDirType == null) { tabDirType = ""; } %>
        <% if (tabDirType.equals(sosin)) { %>
          <gsmsg:write key="wml.19" />
        <% } else if (tabDirType.equals(yoyakuSosin)) { %>
          <gsmsg:write key="wml.211" />
        <% } else if (tabDirType.equals(soko)) { %>
          <gsmsg:write key="cmn.draft" />
        <% } else if (tabDirType.equals(gomi)) { %>
          <gsmsg:write key="cmn.trash" />
        <% } else if (tabDirType.equals(hokan)) { %>
          <gsmsg:write key="cmn.strage" />
        <% } else { %>
          <gsmsg:write key="cmn.receive" />
        <% } %>

      </li>
      <li class="classic-display bor_b1 wp5 m0"></li>
      <li class="tabHeader_space verAlignMid w100">
        <div class="mrl_auto"></div>
        <div class="display_inline">
          <%-- ディスク使用量  --%>
          <div class="smlDiskUse cl_fontMiddle txt_l no_w">
            <img class="classic-display" src="../common/images/classic/icon_disk_capacity.png"/>
            <img class="original-display"src="../common/images/original/icon_disk.png"/>
            <gsmsg:write key="wml.88" />：<span id="useDiskSize"></span></span><gsmsg:write key="cmn.mb" /><span id="limitDiskArea">/<span id="limitDiskSize"></span>MB&nbsp(<span id="useDiskRatio"></span>%)</span>
          </div>

          <%-- 検索(簡易検索)  --%>
          <div class="verAlignMid">
            <input id="search_text_val" name="wml010searchKeywordNml" class="wp120 js_tabHeader_serchSpace ml10" type="text" value="" maxlength="100">
            <button type="button" class="baseBtn js_tabHeader_serchSpace no_w" id="wml_search_btn" value="<gsmsg:write key="cmn.search" />">
             <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
             <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
             <gsmsg:write key="cmn.search" />
            </button>
          </div>
        </div>
       </li>
    </ul>

    <!-- 一覧 メニュー -->
    <div class="js_mailListArea w100 mailMenu bgC_header2">
      <bean:define id="showLabelButton" value =" " />
      <bean:define id="showKidokuButton" value =" " />
      <bean:define id="showDustButton" value ="" />
      <bean:define id="showDelButton" value ="display_none" />
      <bean:define id="showReturnButton" value ="display_none" />
      <logic:equal name="wml010Form" property="wml010viewDirectoryType" value="<%= gomi %>">
        <bean:define id="showLabelButton" value ="display_none" />
        <bean:define id="showKidokuButton" value ="display_none" />
        <bean:define id="showDustButton" value ="display_none" />
        <bean:define id="showDelButton" value ="" />
        <bean:define id="showReturnButton" value ="" />
      </logic:equal>

      <div class="mailMenu_buttonSet mr5">
        <button type="button" class="mailMenu_button js_headMenuAddBtn" value="<gsmsg:write key="wml.js.createmail" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="wml.js.createmail" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add_15.png" alt="<gsmsg:write key="wml.js.createmail" />">
          <gsmsg:write key="wml.js.createmail" />
        </button>
        <button type="button" id="head_menu_list_hokan_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.strage" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.strage" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_kanryo_box.png" alt="<gsmsg:write key="cmn.strage" />">
          <gsmsg:write key="cmn.strage" />
        </button>
        <button type="button" id="head_menu_list_idou_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.move" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.move" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_move_box.png" alt="<gsmsg:write key="cmn.move" />">
          <gsmsg:write key="cmn.move" />
        </button>
      </div>
      <button type="button" id="head_menu_list_dust_btn" class="mr5 mailMenu_button <%=showDustButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_trash.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_trash_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <button type="button" id="head_menu_list_del_btn" class="mr5 mailMenu_button <%=showDelButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <div class="mailMenu_buttonSet <%=showLabelButton%> mr5">
        <button type="button" id="head_menu_list_label_add_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_label_add_15.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <gsmsg:write key="cmn.add.label2" />
        </button>
        <button type="button" id="head_menu_list_label_del_btn" class="mailMenu_button" value="<gsmsg:write key="wml.js.108" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label_del.png" alt="<gsmsg:write key="wml.js.108" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_label_del_15.png" alt="<gsmsg:write key="wml.js.108" />">
          <gsmsg:write key="wml.js.108" />
        </button>
      </div>
      <div class="mailMenu_buttonSet <%=showKidokuButton%> mr5">
        <button type="button" id="head_menu_list_kidoku_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kidoku.png" alt="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_checked_15.png" alt="<gsmsg:write key="cmn.read.already" />">
          <gsmsg:write key="cmn.read.already" />
        </button>
        <button type="button" id="head_menu_list_midoku_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_midoku.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_midoku_15.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <gsmsg:write key="cmn.read.yet" />
        </button>
      </div>
      <div class="mailMenu_buttonSet mr5">
        <button type="button" id="head_menu_list_eml_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_mail.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_eml_15.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <gsmsg:write key="cmn.output.eml" />
        </button>
      </div>
      <div id="wml_page_top_area" class="paging js_paging ml_auto display_none js_paging-mail">
        <button type="button" class="webIconBtn js_paging_prevBtn">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous" />">
          <i class="icon-paging_left"></i>
        </button>
        <select name="wml010pageTop" class="paging_combo js_paging_combo ml0 mr0">
        </select>
        <button type="button" class="webIconBtn js_paging_nextBtn" >
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </div>

    <!-- リストBODY  -->
    <table id="mail_list_draw_table" class="w100 js_mailListArea table-top mt0 mb0 lh100"></table>
    <!-- 一覧 ページコンボ下部 -->
    <div class="verAlignMid w100 js_mailListArea mailMenu bgC_header2 bor_b1">

      <div class="mailMenu_buttonSet mr5">
        <button type="button" class="mailMenu_button js_headMenuAddBtn" value="<gsmsg:write key="wml.js.createmail" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="wml.js.createmail" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add_15.png" alt="<gsmsg:write key="wml.js.createmail" />">
          <gsmsg:write key="wml.js.createmail" />
        </button>
        <button type="button" id="head_menu_list_hokan_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.strage" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.strage" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_kanryo_box.png" alt="<gsmsg:write key="cmn.strage" />">
          <gsmsg:write key="cmn.strage" />
        </button>
        <button type="button" id="head_menu_list_idou_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.move" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.move" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_move_box.png" alt="<gsmsg:write key="cmn.move" />">
          <gsmsg:write key="cmn.move" />
        </button>
      </div>
      <button type="button" id="head_menu_list_dust_btn2" class="mr5 mailMenu_button <%=showDustButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_trash.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_trash_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <button type="button" id="head_menu_list_del_btn2" class="mr5 mailMenu_button <%=showDelButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <div class="mailMenu_buttonSet <%=showLabelButton%> mr5">
        <button type="button" id="head_menu_list_label_add_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_label_add_15.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <gsmsg:write key="cmn.add.label2" />
        </button>
        <button type="button" id="head_menu_list_label_del_btn2" class="mailMenu_button" value="<gsmsg:write key="wml.js.108" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label_del.png" alt="<gsmsg:write key="wml.js.108" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_label_del_15.png" alt="<gsmsg:write key="wml.js.108" />">
          <gsmsg:write key="wml.js.108" />
        </button>
      </div>
      <div class="mailMenu_buttonSet <%=showKidokuButton%> mr5">
        <button type="button" id="head_menu_list_kidoku_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kidoku.png" alt="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_checked_15.png" alt="<gsmsg:write key="cmn.read.already" />">
          <gsmsg:write key="cmn.read.already" />
        </button>
        <button type="button" id="head_menu_list_midoku_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_midoku.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_midoku_15.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <gsmsg:write key="cmn.read.yet" />
        </button>
      </div>
      <div class="mailMenu_buttonSet mr5">
        <button type="button" id="head_menu_list_eml_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_mail.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_eml_15.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <gsmsg:write key="cmn.output.eml" />
        </button>
      </div>

      <div class="paging js_paging ml_auto display_none js_paging-mail">
          <button type="button" class="webIconBtn js_paging_prevBtn" >
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous" />">
            <i class="icon-paging_left"></i>
          </button>
          <select  name="wml010pageBottom" class="paging_combo js_paging_combo ml0 mr0">
          </select>
          <button type="button" class="webIconBtn js_paging_nextBtn" >
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next" />">
            <i class="icon-paging_right"></i>
          </button>
      </div>
    </div>

    <!-- 検索結果 メニュー -->
    <div class="js_mailSearchListArea w100 mailMenu bgC_header2 display_none ">
      <bean:define id="showLabelButton" value =" " />
      <bean:define id="showKidokuButton" value =" " />
      <bean:define id="showDustButton" value ="" />
      <bean:define id="showDelButton" value ="display_none" />

      <div class="mailMenu_buttonSet mr5">
        <button type="button" class="mailMenu_button js_headMenuAddBtn" value="<gsmsg:write key="wml.js.createmail" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="wml.js.createmail" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add_15.png" alt="<gsmsg:write key="wml.js.createmail" />">
          <gsmsg:write key="wml.js.createmail" />
        </button>
        <button type="button" id="head_menu_search_list_strage_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.strage" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.strage" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_kanryo_box.png" alt="<gsmsg:write key="cmn.strage" />">
          <gsmsg:write key="cmn.strage" />
        </button>
        <button type="button" id="head_menu_search_list_move_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.move" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.move" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_move_box.png" alt="<gsmsg:write key="cmn.move" />">
          <gsmsg:write key="cmn.move" />
        </button>
      </div>

      <button type="button" id="head_menu_search_list_dust_btn" class="mr5 mailMenu_button <%=showDustButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_trash.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_trash_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>

      <button type="button" id="head_menu_search_list_del_btn" class="mr5 mailMenu_button <%=showDelButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>

      <div class="mr5 mailMenu_buttonSet <%=showLabelButton%>">
        <button type="button" id="head_menu_search_list_label_add_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label.png" alt="<gsmsg:write key="cmn.add.label2" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_label_add_15.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <gsmsg:write key="cmn.add.label2" />
        </button>
        <button type="button" id="head_menu_search_list_label_del_btn" class="mailMenu_button" value="<gsmsg:write key="wml.js.108" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label_del.png" alt="<gsmsg:write key="wml.js.108" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_label_del_15.png" alt="<gsmsg:write key="wml.js.108" />">
          <gsmsg:write key="wml.js.108" />
        </button>
      </div>
      <div class="mr5 mailMenu_buttonSet <%=showKidokuButton%>">
        <button type="button" id="head_menu_search_list_kidoku_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kidoku.png" alt="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_checked_15.png" alt="<gsmsg:write key="cmn.read.already" />">
          <gsmsg:write key="cmn.read.already" />
        </button>
        <button type="button" id="head_menu_search_list_midoku_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_midoku.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_midoku_15.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <gsmsg:write key="cmn.read.yet" />
        </button>
      </div>

      <div class="mailMenu_buttonSet mr5">
        <button type="button" id="head_menu_search_list_eml_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_mail.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_eml_15.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <gsmsg:write key="cmn.output.eml" />
        </button>
      </div>
      <div id="wml_search_page_top_area" class="paging js_paging ml_auto display_none js_paging-search">
        <button type="button" class="webIconBtn js_paging_prevBtn">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous" />">
          <i class="icon-paging_left"></i>
        </button>
        <select name="wml010searchPageTop" class="paging_combo js_paging_combo ml0 mr0">
        </select>
        <button type="button" class="webIconBtn js_paging_nextBtn">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </div>

    <!-- 検索結果リストBODY  -->
    <table id="mail_search_list_draw_table" class="js_mailSearchListArea table-top mt0 mb0 lh100" cellpadding="0" cellspacing="0"></table>
    <!-- 検索結果ページング 下部  -->
    <div class="verAlignMid w100 js_mailSearchListArea mailMenu bgC_header2 bor_b1 display_none">

      <div class="mailMenu_buttonSet mr5">
        <button type="button" class="mailMenu_button js_headMenuAddBtn" value="<gsmsg:write key="wml.js.createmail" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="wml.js.createmail" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add_15.png" alt="<gsmsg:write key="wml.js.createmail" />">
          <gsmsg:write key="wml.js.createmail" />
        </button>
        <button type="button" id="head_menu_search_list_strage_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.strage" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.strage" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_kanryo_box.png" alt="<gsmsg:write key="cmn.strage" />">
          <gsmsg:write key="cmn.strage" />
        </button>
        <button type="button" id="head_menu_search_list_move_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.move" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.move" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_move_box.png" alt="<gsmsg:write key="cmn.move" />">
          <gsmsg:write key="cmn.move" />
        </button>
      </div>

      <button type="button" id="head_menu_search_list_dust_btn2" class="mr5 mailMenu_button <%=showDustButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_trash_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>

      <button type="button" id="head_menu_search_list_del_btn2" class="mr5 mailMenu_button <%=showDelButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>

      <div class="mr5 mailMenu_buttonSet <%=showLabelButton%>">
        <button type="button" id="head_menu_search_list_label_add_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label.png" alt="<gsmsg:write key="cmn.add.label2" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_label_add_15.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <gsmsg:write key="cmn.add.label2" />
        </button>
        <button type="button" id="head_menu_search_list_label_del_btn2" class="mailMenu_button" value="<gsmsg:write key="wml.js.108" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label_del.png" alt="<gsmsg:write key="wml.js.108" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_label_del_15.png" alt="<gsmsg:write key="wml.js.108" />">
          <gsmsg:write key="wml.js.108" />
        </button>
      </div>
      <div class="mr5 mailMenu_buttonSet <%=showKidokuButton%>">
        <button type="button" id="head_menu_search_list_kidoku_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kidoku.png" alt="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_checked_15.png" alt="<gsmsg:write key="cmn.read.already" />">
          <gsmsg:write key="cmn.read.already" />
        </button>
        <button type="button" id="head_menu_search_list_midoku_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_midoku.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_midoku_15.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <gsmsg:write key="cmn.read.yet" />
        </button>
      </div>

      <div class="mailMenu_buttonSet mr5">
        <button type="button" id="head_menu_search_list_eml_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_mail.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_eml_15.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <gsmsg:write key="cmn.output.eml" />
        </button>
      </div>

      <div class="paging js_paging ml_auto display_none js_paging-search">
          <button type="button" class="webIconBtn js_paging_prevBtn" >
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous" />">
            <i class="icon-paging_left"></i>
          </button>
          <select name="wml010searchPageBottom" class="paging_combo js_paging_combo ml0 mr0">
          </select>
          <button type="button" class="webIconBtn  js_paging_nextBtn" >
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next" />">
            <i class="icon-paging_right"></i>
          </button>
      </div>
    </div>


    <!-- 新規作成 メニュー -->
    <div class="js_mailCreateArea w100 mailMenu bgC_header2 display_none">
      <button type="button" id="head_menu_send_btn" class="mailMenu_button mr5" value="<gsmsg:write key="cir.26" />">
        <img class="btn_classicImg-display hp15" src="../common/images/classic/icon_sinsei.png" alt="<gsmsg:write key="cir.26" />">
        <img class="btn_originalImg-display hp15" src="../common/images/original/icon_sent.png" alt="<gsmsg:write key="cir.26" />">
        <gsmsg:write key="cir.26" />
      </button>

      <div class="mailMenu_buttonSet">
        <button type="button" id="head_menu_sendtemp_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.attached" />">
          <img class="btn_classicImg-display hp15" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
          <img class="btn_originalImg-display hp15" src="../common/images/original/icon_attach_15.png" alt="<gsmsg:write key="cmn.attached" />">
          <gsmsg:write key="cmn.attached" />
        </button>
        <input type="file" id="attachmentAreaBtn1" class="display_none" onchange="attachFileSelect(this, 1);" multiple="">
        <button type="button" id="head_menu_soko_btn" class="mailMenu_button no_w" value="<gsmsg:write key="cmn.save.draft" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_save_soukou_15.png" alt="<gsmsg:write key="cmn.save.draft" />">
          <gsmsg:write key="cmn.save.draft" />
        </button>
        <button type="button" id="head_menu_template_btn" class="mailMenu_button no_w" value="<gsmsg:write key="cmn.template" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_template.png" alt="<gsmsg:write key="cmn.template" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_template_15.png" alt="<gsmsg:write key="cmn.template" />">
          <gsmsg:write key="cmn.template" />
        </button>
      </div>
    </div>
    <!-- 新規作成 Body -->
    <div class="bor1 w100  js_mailCreateArea bgC_body display_none">
      <!-- div class="w100" -->
      <div class="w100" id="attachment_FormArea1">
        <!-- 差出人 -->
        <div class="verAlignMid w100 bor_b1 borC_weak">
          <div class="wp70 txt_c txt_m no_w"><gsmsg:write key="cmn.sendfrom"/></div>
          <div class="m5">
            <html:select property="wml010sendAccount" styleId="send_account_comb_box" styleClass="wp180">
              <html:optionsCollection name="wml010Form" property="accountCombo" value="value" label="label" />
            </html:select>
          </div>
        </div>
        <!-- 宛先 -->
        <div class="verAlignMid w100 bor_b1 borC_weak">
          <div class="m5 ">
            <div class="verAlignMid">
              <a href="#" onClick="openAddress('wml010sendAddressTo');" id="sendAdrIconTo">
                <img class="btn_classicImg-display txt_l wp20 hp20"  src="../webmail/images/classic/icon_address.gif">
                <img class="btn_originalImg-display txt_l wp20 hp20" src="../webmail/images/original/icon_address.png">
              </a>
              <a href="#" onClick="openSyain('wml010sendAddressTo');" id="sendSyainIconTo">
                <img class="btn_classicImg-display txt_l wp20 hp20"  src="../common/images/classic/icon_user.png">
                <img class="btn_originalImg-display txt_l wp20 hp20" src="../common/images/original/icon_user.png">
              </a>
              <a href="#" onClick="openDestlistDialog('wml010sendAddressTo');" id="sendDestIconTo">
                <img class="btn_classicImg-display txt_l wp20 hp20"  src="../webmail/images/classic/menu_icon_single.gif">
                <img class="btn_originalImg-display txt_l wp20 hp20" src="../webmail/images/original/icon_destlist.png">
              </a>

              <div class="wp50 pl15 txt_m no_w"><gsmsg:write key="cmn.from"/></div>

              <span>
                <div id="autoCompleteTo" class="w80 pl20">
                  <input type="text" id="composeTo" name="wml010sendAddressTo" class="wp600 js_wml010AddressText" value="" onFocus="setEditorFocus('wml010sendAddressTo');">
                  <div id="autoTo"></div>
                </div>
              </span>

              <div>
                <div  class="mb5 pr20">
                  <div id="atesaki_to_area" class="js_selectAtesakiArea"></div>
                </div>
                <a href="#!" class="js_atesakiAllDisp fs_12"></a>
              </div>
            </div>
          </div>
        </div>
        <!-- CC -->
        <div class="verAlignMid w100 bor_b1 borC_weak">
          <div class="m5 ">
            <div class="verAlignMid">
              <a href="#" onClick="openAddress('wml010sendAddressCc');" id="sendAdrIconCc">
                <img class="btn_classicImg-display txt_l wp20 hp20"  src="../webmail/images/classic/icon_address.gif">
                <img class="btn_originalImg-display txt_l wp20 hp20" src="../webmail/images/original/icon_address.png">
              </a>
              <a href="#" onClick="openSyain('wml010sendAddressCc');" id="sendSyainIconCc">
                <img class="btn_classicImg-display txt_l wp20 hp20"  src="../common/images/classic/icon_user.png">
                <img class="btn_originalImg-display txt_l wp20 hp20" src="../common/images/original/icon_user.png">
              </a>
              <a href="#" onClick="openDestlistDialog('wml010sendAddressCc');" id="sendDestIconCc">
                <img class="btn_classicImg-display txt_l wp20 hp20"  src="../webmail/images/classic/menu_icon_single.gif">
                <img class="btn_originalImg-display txt_l wp20 hp20" src="../webmail/images/original/icon_destlist.png">
              </a>

              <div class="wp50 pl15 txt_m no_w"><gsmsg:write key="cmn.cc"/></div>

              <span>
                <div id="autoCompleteCc" class="w80 pl20">
                  <input type="text" id="composeCc" name="wml010sendAddressCc" class="wp600 js_wml010AddressText" value="" onFocus="setEditorFocus('wml010sendAddressCc');">
                  <div id="autoCc"></div>
                </div>
              </span>

              <div>
                <div class="mb5 pr20">
                  <div id="atesaki_cc_area" class="js_selectAtesakiArea"></div>
                </div>
                <a href="#!" class="js_atesakiAllDisp fs_12"></a>
              </div>
            </div>
          </div>
        </div>
        <!-- BCC -->
        <div class="verAlignMid w100 bor_b1 borC_weak">
          <div class="m5 ">
            <div class="verAlignMid" >
              <a href="#" onClick="openAddress('wml010sendAddressBcc');" id="sendAdrIconBcc">
                <img class="btn_classicImg-display txt_l wp20 hp20"  src="../webmail/images/classic/icon_address.gif">
                <img class="btn_originalImg-display txt_l wp20 hp20" src="../webmail/images/original/icon_address.png">
              </a>
              <a href="#" onClick="openSyain('wml010sendAddressBcc');" id="sendSyainIconBcc">
                <img class="btn_classicImg-display txt_l wp20 hp20"  src="../common/images/classic/icon_user.png">
                <img class="btn_originalImg-display txt_l wp20 hp20" src="../common/images/original/icon_user.png">
              </a>
              <a href="#" onClick="openDestlistDialog('wml010sendAddressBcc');" id="sendDestIconBcc">
                <img class="btn_classicImg-display txt_l wp20 hp20"  src="../webmail/images/classic/menu_icon_single.gif">
                <img class="btn_originalImg-display txt_l wp20 hp20" src="../webmail/images/original/icon_destlist.png">
              </a>

              <div class="wp50 pl15 txt_m no_w"><gsmsg:write key="cmn.bcc"/></div>

              <span>
                <div id="autoCompleteBcc" class="w100 pl20">
                  <input type="text" id="composeBcc" name="wml010sendAddressBcc" class="wp600 js_wml010AddressText" value="" onFocus="setEditorFocus('wml010sendAddressBcc');">
                  <div id="autoBcc"></div>
                </div>
              </span>
              <div>
                <div class="mb5 pr20">
                  <div id="atesaki_bcc_area" class="js_selectAtesakiArea"></div>
                </div>
                <a href="#!" class="js_atesakiAllDisp fs_12"></a>
              </div>
            </div>
          </div>
        </div>
        <!-- 件名 -->
        <div class="verAlignMid w100 bor_b1 borC_weak">
          <div class="wp60 pl15 txt_m no_w"><gsmsg:write key="cmn.subject" /></div>
          <div class="m5 ">
            <input type="text" id="subject" name="wml010sendSubject" maxlength="<%= maxLengthTitle %>" value="" class="wp600" onFocus="setEditorFocus('');">
          </div>
        </div>
        <!-- 署名 -->
        <div id="composeSendSign" class="verAlignMid w100 bor_b1 borC_weak">
          <div class="wp60 pl15 txt_m no_w"><gsmsg:write key="wml.34"/></div>
          <div class="m5">
            <select name="wml010sendSign" id="sendSignCombo" class="wp180 mr5" onchange="changeSendSign();">
            </select>
            <button id="sendSignSetting" type="button" class="baseBtn" value="<gsmsg:write key="cmn.setting" />" onclick="changeSendSign();"><gsmsg:write key="cmn.setting" /></button>
          </div>
        </div>

        <!-- 予約送信 -->
        <div class="verAlignMid borC_weak pt5 pl5 hp30">
          <input type="checkbox" id="sendPlanDateKbnCheck" name="sendMailPlanType" value="1" onClick="viewSendPlanDate();" class="ml10">
          <label for="sendPlanDateKbnCheck"><gsmsg:write key="wml.211" /></label>
          <input type="hidden" id="sendPlanDateKbn" name="sendMailPlanType" value="0">

          <span class="ml20 verAlignMid display_none" id="sendPlanImmArea">
            <input type="radio" id="sendPlanImm2" name="wml010sendMailPlanImm" value="0">
            <label for="sendPlanImm2" class="mr10"><gsmsg:write key="wml.241" /></label>
            <input type="radio" id="sendPlanImm1" name="wml010sendMailPlanImm" value="1">
            <label for="sendPlanImm1"><gsmsg:write key="wml.276" /></label>
          </span>
          <font class="m5 ml10" id="sendPlanDateValueArea">
          </font>
        </div>

        <%-- 添付ファイル --%>
        <div id="composeTempFile" class="w100 p5 pl15 mt5 bor_t1 bor_b1 display_none">
        </div>

        <span id="wmlAttachmentIdArea">
        </span>
      </div>
      <!-- テキスト入力切り替え -->
      <div class="w100 display_inline ">
        <span id="attachmentFileErrorArea2" class="m5 p0"></span>
        <div class="ml_auto mr5 mt5 mb5">
          <a id="text_html" href="#!" class="js_mailCreateBottomSelTextForm wp140 cursor_p fw_b cl_fontBlock txt_c"><gsmsg:write key="wml.109" /><gsmsg:write key="sml.sml010.08" /></a>
          <a id="text_text" href="#!" class="js_mailCreateBottomSelTextForm wp140 cursor_p fw_b cl_fontBlock txt_c display_none"><gsmsg:write key="wml.js.12" /><gsmsg:write key="sml.sml010.08" /></a>
         <input type="hidden" name="wml010sendMailHtml" value="0" />
        </div>
      </div>
      <div id="html_input_area" class="pl5 pr5 display_none">
        <div id="attachment_FormArea2" class="p0 m0">
        <textarea id="html_input"></textarea>
        <input type="hidden" name="wml010sendContentHtml" value="" />
        </div>
        <input type="file" id="attachmentAreaBtn2" class="display_none" onchange="attachFileSelect(this, '2');" multiple="">

        <div id="wmlComposeBodyContent" class="p0 m0">
        </div>
      </div>
      <div id="text_input_area" class="pl5 pr5">
        <textarea id="text_input" class="html_text_input w100 m0" name="wml010sendContent" rows="30" wrap="soft" onkeyup="showLengthStr(value, <bean:write name="wml010Form" property="wml010maxBodySize"/>, 'inputlength');" id="inputstr"></textarea>
      </div>
      <logic:notEqual name="wml010Form" property="wml010maxBodySize" value="0">
        <div id="text_count_area" class="fs_13 w100 pl15 pr10 display_inline">
          <div class="ml_auto">
          <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength">0</span><span class="ml5">/&nbsp;<bean:write name="wml010Form" property="wml010maxBodySize"/>&nbsp;<gsmsg:write key="cmn.character" />
          </div>
        </div>
      </logic:notEqual>
    </div>

    <!-- メール確認 メニュー -->
    <div class="js_mailKakuninArea js_mailMenu w100 mailMenu bgC_header2 display_none ">

      <div class="mr5 mailMenu_buttonSet">
        <!-- 戻る -->
        <button type="button" id="head_menu_back_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.back" />">
          <img class="btn_classicImg-display" src="../webmail/images/classic/icon_mail_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back_15.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
        <!-- 保管 -->
        <button type="button" id="head_menu_strage_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.strage" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.strage" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_kanryo_box.png" alt="<gsmsg:write key="cmn.strage" />">
          <gsmsg:write key="cmn.strage" />
        </button>
        <!-- 移動 -->
        <button type="button" id="head_menu_move_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.move" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.move" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_move_box.png" alt="<gsmsg:write key="cmn.move" />">
          <gsmsg:write key="cmn.move" />
        </button>
        <!-- 編集 -->
        <button type="button" id="head_menu_edit_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.edit" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_mail_edit_15.png" alt="<gsmsg:write key="cmn.edit" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_mail_edit_15.png" alt="<gsmsg:write key="cmn.edit" />">
          <gsmsg:write key="cmn.edit" />
        </button>
      </div>

      <div class="mr5 mailMenu_buttonSet">
        <!-- 削除 -->
        <button type="button" id="head_menu_dust_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.delete" />">
          <img class="btn_classicImg-display js_detailBtnIcon_Trash" src="../common/images/classic/icon_trash.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_detailBtnIcon_Trash" src="../common/images/original/icon_trash_15.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_classicImg-display js_detailBtnIcon_Delete display_none" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_detailBtnIcon_Delete display_none" src="../common/images/original/icon_delete_15.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <!-- 返信-->
        <button type="button" id="head_menu_replay_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.reply" />">
          <img class="btn_classicImg-display wp15" src="../common/images/classic/icon_replay.png" alt="<gsmsg:write key="cmn.reply" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_replay_15.png" alt="<gsmsg:write key="cmn.reply" />">
          <gsmsg:write key="cmn.reply" />
        </button>
        <!-- 全員に返信-->
        <button type="button" id="head_menu_all_replay_btn" class="mailMenu_button" value="<gsmsg:write key="wml.reply.all" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_all_replay.png" alt="<gsmsg:write key="wml.reply.all" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_all_replay_15.png" alt="<gsmsg:write key="wml.reply.all" />">
          <gsmsg:write key="wml.reply.all" />
        </button>
        <!-- 転送-->
        <button type="button" id="head_menu_forward_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.forward" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_forward.png" alt="<gsmsg:write key="cmn.forward" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_forward_15.png" alt="<gsmsg:write key="cmn.forward" />">
          <gsmsg:write key="cmn.forward" />
        </button>
      </div>

      <div class="mr10 mailMenu_buttonSet">
        <!-- ラベル追加 -->
        <button type="button" id="head_menu_addlabel_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_label_add_15.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <gsmsg:write key="cmn.add.label2" />
        </button>
        <!-- ラベル削除 -->
        <button type="button" id="head_menu_dellabel_btn" class="mr5 mailMenu_button" value="<gsmsg:write key="wml.js.108" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label_del.png" alt="<gsmsg:write key="wml.js.108" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_label_del_15.png" alt="<gsmsg:write key="wml.js.108" />">
          <gsmsg:write key="wml.js.108" />
        </button>
      </div>

      <!-- 共有 -->
      <button type="button" id="head_menu_share_btn" class="mailMenu_button mr5" value="<gsmsg:write key="cmn.share" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_send_mail.png" alt="<gsmsg:write key="cmn.share" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_send_mail_15.png" alt="<gsmsg:write key="cmn.share" />">
        <gsmsg:write key="cmn.share" />
      </button>

      <div class="mr5 mailMenu_buttonSet">
        <button type="button" id="head_menu_prev_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.previous"/>">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_l.png" alt="<gsmsg:write key="cmn.previous"/>">
          <i class="icon-arrow_left"></i>
          <gsmsg:write key="cmn.previous"/>
        </button>
        <button type="button" id="head_menu_next_btn"
          class="mailMenu_button" value="<gsmsg:write key="cmn.next"/>">
          <gsmsg:write key="cmn.next"/>
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_r.png" alt="<gsmsg:write key="cmn.next"/>">
          <i class="icon-arrow_right"></i>
        </button>
      </div>
    </div>
    <!-- メール確認 -->
    <div id="wml_kakunin_body" class="bor1 w100 js_mailKakuninArea display_none" >
    </div>
    <!-- メール確認フッター -->
    <div class="js_mailKakuninArea w100 mailMenu bor_b1 bgC_header2 display_none ">
      <div class="ml_auto"></div>
      <a href="#!" class="verAlignMid js_mailCheckBodyBottomPdf">
        <img class="btn_classicImg-display"
          src="../common/images/classic/icon_pdf.png"
          alt="<gsmsg:write key="wml.237" />">
        <img
          class="btn_originalImg-display  mr5"
          src="../common/images/original/icon_pdf.png"
          alt="<gsmsg:write key="wml.237" />">
        <gsmsg:write key="wml.237" />
      </a>
      <a href="#!" class="verAlignMid js_mailCheckBodyBottomEml">
        <img class="btn_classicImg-display ml5"
          src="../common/images/classic/icon_mail.png"
          alt="<gsmsg:write key="cmn.export.eml" />">
        <img
          class="btn_originalImg-display ml10 mr5"
          src="../common/images/original/icon_eml.png"
          alt="<gsmsg:write key="cmn.export.eml" />">
        <gsmsg:write key="cmn.export.eml" />
      </a>
    </div>

    <!-- メール確認 メニュー(下部) -->
    <div class="js_mailKakuninArea js_mailMenu w100 bor_b1 mailMenu bgC_header2 display_none ">

      <div class="mr5 mailMenu_buttonSet">
        <!-- 戻る -->
        <button type="button" id="head_menu_back_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.back" />">
          <img class="btn_classicImg-display" src="../webmail/images/classic/icon_mail_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back_15.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
        <!-- 保管 -->
        <button type="button" id="head_menu_strage_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.strage" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.strage" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_kanryo_box.png" alt="<gsmsg:write key="cmn.strage" />">
          <gsmsg:write key="cmn.strage" />
        </button>
        <!-- 移動 -->
        <button type="button" id="head_menu_move_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.move" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_folder_kanryo_box_15.png" alt="<gsmsg:write key="cmn.move" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_folder_move_box.png" alt="<gsmsg:write key="cmn.move" />">
          <gsmsg:write key="cmn.move" />
        </button>
        <!-- 編集 -->
        <button type="button" id="head_menu_edit_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.edit" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_mail_edit_15.png" alt="<gsmsg:write key="cmn.edit" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_mail_edit_15.png" alt="<gsmsg:write key="cmn.edit" />">
          <gsmsg:write key="cmn.edit" />
        </button>
      </div>

      <div class="mr5 mailMenu_buttonSet">
        <!-- 削除 -->
        <button type="button" id="head_menu_dust_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.delete" />">
          <img class="btn_classicImg-display js_detailBtnIcon_Trash" src="../common/images/classic/icon_trash.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_detailBtnIcon_Trash" src="../common/images/original/icon_trash_15.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_classicImg-display js_detailBtnIcon_Delete display_none" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display js_detailBtnIcon_Delete display_none" src="../common/images/original/icon_delete_15.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <!-- 返信-->
        <button type="button" id="head_menu_replay_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.reply" />">
          <img class="btn_classicImg-display wp15" src="../common/images/classic/icon_replay.png" alt="<gsmsg:write key="cmn.reply" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_replay_15.png" alt="<gsmsg:write key="cmn.reply" />">
          <gsmsg:write key="cmn.reply" />
        </button>
        <!-- 全員に返信-->
        <button type="button" id="head_menu_all_replay_btn2" class="mailMenu_button" value="<gsmsg:write key="wml.reply.all" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_all_replay.png" alt="<gsmsg:write key="wml.reply.all" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_all_replay_15.png" alt="<gsmsg:write key="wml.reply.all" />">
          <gsmsg:write key="wml.reply.all" />
        </button>
        <!-- 転送-->
        <button type="button" id="head_menu_forward_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.forward" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_forward.png" alt="<gsmsg:write key="cmn.forward" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_forward_15.png" alt="<gsmsg:write key="cmn.forward" />">
          <gsmsg:write key="cmn.forward" />
        </button>
      </div>

      <div class="mr10 mailMenu_buttonSet">
        <!-- ラベル追加 -->
        <button type="button" id="head_menu_addlabel_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_label_add_15.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <gsmsg:write key="cmn.add.label2" />
        </button>
        <!-- ラベル削除 -->
        <button type="button" id="head_menu_dellabel_btn2" class="mr5 mailMenu_button" value="<gsmsg:write key="wml.js.108" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label_del.png" alt="<gsmsg:write key="wml.js.108" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_label_del_15.png" alt="<gsmsg:write key="wml.js.108" />">
          <gsmsg:write key="wml.js.108" />
        </button>
      </div>

      <!-- 共有 -->
      <button type="button" id="head_menu_share_btn2" class="mailMenu_button mr5" value="<gsmsg:write key="cmn.share" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_send_mail.png" alt="<gsmsg:write key="cmn.share" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_send_mail_15.png" alt="<gsmsg:write key="cmn.share" />">
        <gsmsg:write key="cmn.share" />
      </button>

      <div class="mr5 mailMenu_buttonSet">
        <button type="button" id="head_menu_prev_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.previous"/>">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_l.png" alt="<gsmsg:write key="cmn.previous"/>">
          <i class="icon-arrow_left"></i>
          <gsmsg:write key="cmn.previous"/>
        </button>
        <button type="button" id="head_menu_next_btn2" class="mailMenu_button" value="<gsmsg:write key="cmn.next"/>">
          <gsmsg:write key="cmn.next"/>
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_r.png" alt="<gsmsg:write key="cmn.next"/>">
          <i class="icon-arrow_right"></i>
        </button>
      </div>
    </div>

    <!-- 受信確認テンプレート -->
    <div id="wml_kakunin_body-jusin" class="js_mailKakuninArea-jusin display_none" >
      <!-- メールヘッダ情報 -->
      <div class="p5 pl20 bgC_header3 display_inline w100">
        <div class="bgC_header3 w100">
          <table>
            <!-- 件名 -->
            <tr>
              <th class="txt_t fw_b no_w"><span id="wml_detail_subject" class="verAlignMid"><gsmsg:write key="cmn.subject"/></span></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 js_mailKakunin_title fw_b word_b-all" colspan="2">
              </td>
            </tr>
            <!-- 差出人 -->
            <tr>
              <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.sendfrom"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 js_mailKakunin_from fw_b word_b-all" colspan="2">
              </td>
            </tr>
            <!-- 日時 -->
            <tr>
              <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.date"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 js_mailKakunin_date fw_b word_b-all" colspan="2">
              </td>
            </tr>
            <!-- 宛先 -->
            <tr>
              <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.from"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 js_mailKakunin_to js_mailKakunin-expandable fw_b word_b-all" colspan="2">
              </td>
            </tr>
            <!-- CC -->
            <tr>
              <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.cc"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 js_mailKakunin_cc js_mailKakunin-expandable fw_b word_b-all" colspan="2">
              </td>
            </tr>
            <!-- BCC -->
            <tr>
              <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.bcc"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 js_mailKakunin_bcc js_mailKakunin-expandable fw_b word_b-all" colspan="2">
              </td>
            </tr>
            <!-- 予約送信 -->
            <tr>
              <th class="txt_t fw_b no_w"><gsmsg:write key="wml.211"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 js_mailKakunin_sendplan js_mailKakunin-expandable fw_b word_b-all cl_fontWarn" colspan="2">
              </td>
            </tr>
            <!-- ラベル(ヘッダ情報) -->
            <tr>
              <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.label"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 js_mailKakunin_label js_mailKakunin-expandable fw_b w90 word_b-all">
              </td>
              <td class="pl5 txt_r fs_13 w10 no_w"><a id="js_mailKakunin_headerInfo" href="#" onClick=""><gsmsg:write key="wml.header.info" /></a></td>
            </tr>
          </table>
        </div>
      </div>
      <!-- 添付ファイル -->
      <div id="wmlMailDetailTempArea" class="fs_16 pl20 pt5 fw_b js_mailKakunin_temp bor_t1 bgC_body word_b-all display_none"></div>
      <!-- 本文 -->
      <div class="p5 pl20 bor_t1 js_mailKakunin_body bgC_body word_b-all">
      </div>
    </div>
    <!-- 送信確認テンプレート -->
    <div id="wml_kakunin_body-sosin" class="js_mailKakuninArea-sosin display_none" >
      <!-- メールヘッダ情報 -->
      <div class="p5 pl20 bgC_header2">
        <table>
          <!-- 宛先 -->
          <tr >
            <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.from"/></th>
            <td class="txt_t fw_b">:</td>
            <td class="pl5 js_mailKakunin_to">
            </td>
          </tr>
          <!-- CC -->
          <tr >
            <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.cc"/></th>
            <td class="txt_t fw_b">:</td>
            <td class="pl5 js_mailKakunin_cc">
            </td>
          </tr>
          <!-- BCC -->
          <tr >
            <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.bcc"/></th>
            <td class="txt_t fw_b">:</td>
            <td class="pl5 js_mailKakunin_bcc">
            </td>
          </tr>
          <!-- 件名 -->
          <tr >
            <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.subject"/></th>
            <td class="txt_t fw_b">:</td>
            <td class="pl5 js_mailKakunin_title">
            </td>
          </tr>
          <!-- 日時 -->
          <tr >
            <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.date"/></th>
            <td class="txt_t fw_b">:</td>
            <td class="pl5 js_mailKakunin_date">
            </td>
          </tr>
        </table>
        <!-- 添付ファイル -->
        <div class="fs_16 fw_b js_mailKakunin_temp" ></div>
      </div>
      <!-- 本文 -->
      <div class="p5 pl20 bor_t1 js_mailKakunin_body">
      </div>

      <!-- 開封確認 -->
      <div class="p5 pl20 bor_t1 js_mailKakunin_opnChk">
      </div>

    </div>
    <div class="mt10 txt_r">
      <span class="textLink cursor_p" onclick="scrollBodyTop();"><gsmsg:write key="cmn.top.back" /></span>
    </div>
  </div>
</div>

<div class="display_none">
  <div id="atesakiSelPop" title="<gsmsg:write key="cmn.form.user" />" >
    <table class="w100 h100">
      <tr>
        <td id="atesakiSelArea" class="w100"></td>
      </tr>
    </table>
  </div>


  <div id="sendMailPop" title="">
    <ul class="verAlignMid mt20 p0">
     <li class="" >
       <img class="header_pluginImg-classic" src="../common/images/classic/icon_info_32.png" alt="cmn.warn">
       <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
     </li>
     <li class="txt_t pt20 pl10">
       <span id="sendMailPopMsg">
       </span>
     </li>
    </ul>
  </div>

  <div id="messagePop" title="" >
    <ul class="verAlignMid  mt20 p0">
     <li class="" >
       <img class="header_pluginImg-classic" src="../common/images/classic/icon_info_32.png" alt="cmn.warn">
       <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
     </li>
     <li class=" txt_t pt20 pl10">
       <span id="messageArea">
       </span>
     </li>
    </ul>
  </div>

  <div id="delKakuninPop" title="" >
    <ul class="verAlignMid mt20 p0">
     <li>
       <img class="header_pluginImg-classic" src="../common/images/classic/icon_info_32.png" alt="cmn.warn">
       <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
     </li>
     <li class="txt_t pl10">
       <gsmsg:write key="sml.sml010.09" />
     </li>
    </ul>
  </div>

  <div id="delMailMsgPop" title="" >
    <ul class="verAlignMid  mt20 p0">
      <li class=" hp200 verAlignMid" >
        <img class="header_pluginImg-classic" src="../common/images/classic/icon_info_32.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class=" txt_t pt20 pl10">
         <div id="delScrollArea" class="txt_m of_a wp500 hp200">
           <table>
             <tr>
               <td id="delMailMsgArea" class="txt_m wp500 hp200 fw_b fs_13"></td>
             </tr>
           </table>
         </div>
      </li>
    </ul>
  </div>

  <div id="warningPop" title="" >
    <ul class="verAlignMid  mt20 p0">
      <li class="" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class=" txt_t pt20 pl10">
         <gsmsg:write key="sml.sml010.09" />
      </li>
    </ul>

  </div>


</html:form>

  <div id="labelAddPop" title="<gsmsg:write key="wml.wml010.16" />" >
    <div class='bd txt_l'>
      <form method='POST' action='../webmail/wml010.do' name="addLabelForm" id="addLabelForm">
        <table class="mt10" summary="">
          <tr>
            <td>
              <span class="verAlignMid">
                <input type="radio" name="wml010addLabelType" value="0" id="addLabelType0" onClick="changeAddLabelType();" checked>
                <label for="addLabelType0"><gsmsg:write key="wml.76" /></label>
              </span>
            </td>
            <td class="pl5">
              <div class="p0 bgC_body">
                <select id="label_dialog_sel" name="wml010addLabel" class="wp230">
                  <logic:notEmpty name="wml010Form" property="labelCombo">
                    <logic:iterate id="labelData" name="wml010Form" property="labelCombo">
                      <option value="<bean:write name="labelData" property="value" />"><bean:write name="labelData" property="label" /></option>
                    </logic:iterate>
                  </logic:notEmpty>
                </select>
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <span class="verAlignMid">
                <input type="radio" name="wml010addLabelType" id="addLabelType1" value="1" onClick="changeAddLabelType();">
                <label for="addLabelType1"><gsmsg:write key="wml.wml010.09" /></label>
              </span>
            </td>
            <td class="pl5">
              <div class="p0 bgC_body">
                <input type="text" name="wml010addLabelName" id="label_dialog_new" maxlength="100" disabled="true" class="wp230">
              </div>
              <span id="addLabelParam"></span>
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>

  <div id="labelDelPop" title="<gsmsg:write key="wml.js.108" />" >
    <div class='bd txt_l'>
      <form method='POST' action='../webmail/wml010.do' name="delLabelForm">
        <table class="hp75 txt_m" summary="">
          <tr>
            <td><gsmsg:write key="wml.76" /></td>
            <td>
              <select id="label_dialog_del" name="wml010delLabel" class="ml5 wp230">
                <logic:notEmpty name="wml010Form" property="labelCombo">
                  <logic:iterate id="labelData" name="wml010Form" property="labelCombo">
                    <option value="<bean:write name="labelData" property="value" />"><bean:write name="labelData" property="label" /></option>
                  </logic:iterate>
                </logic:notEmpty>
              </select>
              <div class="display_n js_delLabelArea">
                <logic:notEmpty name="wml010Form" property="labelCombo">
                  <logic:iterate id="labelData" name="wml010Form" property="labelCombo">
                    <span id="delLabel_<bean:write name="labelData" property="value" />"><bean:write name="labelData" property="label" /></span>
                  </logic:iterate>
                </logic:notEmpty>
              </div>
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>

  <div id='moveMailPop'>
    <div class='bd txt_l pt5'>
      <form method='POST' action='../webmail/wml010.do' name="moveMailForm">
        <logic:notEmpty name="wml010Form" property="folderCombo">
          <logic:iterate id="folderData" name="wml010Form" property="folderCombo" indexId="idx">
            <% String folderId = "moveFolder_" + String.valueOf(idx.intValue()); %>
            <div>
              <span class="verAlignMid">
                <input type="radio" name="wml010moveFolder" value="<bean:write name="folderData" property="value" />" id="<%= folderId %>">
                <label for="<%= folderId %>"><bean:write name="folderData" property="label" /></label>
                <% if (idx.intValue() == 0)  { %>
                  <input type="hidden" name="moveFolderDefaultId" value="<%= folderId %>">
                <% } %>
              </span>
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </form>
    </div>
  </div>

  <div id="shareMailPop">
    <div class="bd txt_l">
      <table summary="" id="wmlShereList">
        <tbody>
        <tr id="entrySmailArea">
          <td class="mailList_data">
            <img class="btn_classicImg-display" src="../webmail/images/classic/smail_icon_single.gif" />
            <img class="btn_originalImg-display" src="../webmail/images/original/smail_icon_single.png">
          </td>
          <td class="mailList_data1 cursor_p pl5" onClick="openEntrySmail();"><span class="cl_linkDef"><gsmsg:write key="wml.wml010.31" /></span></td>
        </tr>
        <tr id="entryCircularArea">
          <td class="mailList_data1">
            <img class="btn_classicImg-display" src="../webmail/images/classic/circular_icon_single.gif" />
            <img class="btn_originalImg-display" src="../webmail/images/original/circular_icon_single.png">
          </td>
          <td class="mailList_data1 cursor_p pl5"><span class="cl_linkDef" onClick="return openEntryCircular();"><gsmsg:write key="wml.wml010.30" /></span></td>
        </tr>
        <tr id="entryFilekanriArea">
          <td class="mailList_data1">
            <img class="btn_classicImg-display" src="../webmail/images/classic/file_icon_single.gif" />
            <img class="btn_originalImg-display" src="../webmail/images/original/file_icon_single.png">
          </td>
          <td class="mailList_data1 cursor_p pl5" onClick="openEntryFilekanri();"><span class="cl_linkDef"><gsmsg:write key="wml.wml010.32" /></span></td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>

  <div id='wmlSubWindowPop'>
    <div class='bd txt_l bgC_tableCell'>
        <iframe id="wml010EntryMailArea" hspace="0" vspace="0" class="m0 p0 w100 hp700" style="display:inline!important;" frameborder="no" src=""></iframe>
    </div>
  </div>


  <div id="mailTemplatePop" class="ofy_h">
    <div id="mailTemplate_scroll" class="w100 hp400 of_a p0">
      <table id="sendTemplateList" class="table-top bor_t1 m0">
        <tbody></tbody>
      </table>
      <div id="tooltip_area" align="left"></div>
    </div>
  </div>

  <div id="loading_pop" title="">
    <table class="w100 h100">
      <tr>
        <td class="txt_m txt_c">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
          <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
        </td>
      </tr>
    </table>
  </div>

  <div id='sendMailTempPop'>
    <div>
      <form method='POST' action='../webmail/wml010.do' enctype="multipart/form-data" id="sendMailTempForm" name="sendMailTempForm">
        <input type="hidden" id="sendMailTempFormCMD" name="CMD" value="sendFileUpload">
        <div class="mt5 w100 verAlignMid">
          <input type="file" id="wml010sendMailFile" name="wml010sendMailFile[0]" size="30" maxlength="50">
        </div>

        <div id="wml010DragArea">
          <div class="tempFileSelect_dropArea bgC_other1" id="uploadArea" draggable="true">
            <span class="tempFileSelect_dropArea-text fs_18">
              <gsmsg:write key="cmn.file.droparea.message" />
            </span>
          </div>
        </div>
      </form>
    </div>
  </div>


  <div id='deleteAttachForDetailPop'>
    <div class="txt_c txt_m pt10">
      [<span id="msg_dialog11"></span>] を削除します。<br>
      よろしいですか？<br>
    </div>
  </div>


  <div id="sendMailConfirmPop" title="">
    <div class='bd txt_l'>
        <iframe id="wml010SendConfirmArea" class="w100" hspace="0" vspace="0" frameborder="no" src=""></iframe>
    </div>
  </div>


</div>

<span id="tooltip_area"></span>
<span id="mailTooltip_area"></span>

<logic:equal name="wml010Form" property="wml010smlShareFlg" value="0">
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</logic:equal>

<div id='shareDialog' title="<gsmsg:write key="cmn.share" />" class="display_none">
  <iframe id="shareFrame" name=shareFrameName class="w100 h100"></iframe>
</div>

<form id="tempSendForm">
</form>

<iframe id="wml010Export" src="" class="display_n" />
</body>
</html:html>