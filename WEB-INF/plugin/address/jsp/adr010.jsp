<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.adr.GSConstAddress" %>
<%@ page import="jp.groupsession.v2.adr.adr010.Adr010Const" %>

<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.addressbook" /></title>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../address/css/address.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../address/css/freeze.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
<script src="../address/js/adrcommon.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr010.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>

<logic:equal name="adr010Form" property="adr010webmail" value="1">
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel="stylesheet" href="../common/css/jquery.scrolltable.css?<%= GSConst.VERSION_PARAM %>" />
  <script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/assets/webmailSubWindow.js?<%= GSConst.VERSION_PARAM %>"></script>
<style type="text/css">
.fakeContainer {
    margin: 0px;
    width:  100%;
    height: 300px;
    overflow: hidden;
}
.sData{
  overflow-x:hidden!important;
  margin-top: 0px!important;
}
</style>
</logic:equal>
</head>
<bean:define id="cmdMode" name="adr010Form" property="adr010cmdMode" type="java.lang.Integer" />
<% if (cmdMode.intValue() == Adr010Const.CMDMODE_CONTACT) { %>
  <body class="body_03" onload="adr010DateKbn();">
<% } else { %>
  <body class="body_03">
<% } %>
<div id="FreezePane">
<html:form action="/address/adr010">
<html:hidden property="adr010webmail" />
<html:hidden property="adr010webmailAddress" />
<html:hidden property="adr010webmailType" />
<html:hidden property="adr010SendMailMode" />
<html:hidden property="adr010AdrSid" />
<html:hidden property="adr010DelAdrSid" />
<input type="hidden" name="adr010AdrType" value="0">
<% String[] atskListName = {"adr010AtskList", "adr010CcList", "adr010BccList"}; %>
<% String[] atskParamName = {"adr010Atsk", "adr010Cc", "adr010Bcc"}; %>
<% for (int atskIdx = 0; atskIdx < 3; atskIdx++) { %>
<logic:notEmpty name="adr010Form" property="<%= atskListName[atskIdx] %>" scope="request">
  <% String adrMailName = ""; %>
  <logic:iterate id="atskMdl" name="adr010Form" property="<%= atskListName[atskIdx] %>" scope="request">
    <% for (int mailNo = 1; mailNo <= 3; mailNo++) { %>
    <%     adrMailName = "adrMail" + mailNo; %>
    <logic:notEmpty name="atskMdl" property="<%= adrMailName %>">
    <logic:notEmpty name="atskMdl" property="adrSei">
      <input type="hidden" name="<%= atskParamName[atskIdx] %>" value="<bean:write name="atskMdl" property="adrMailPersonal" /> &lt;<bean:write name="atskMdl" property="<%= adrMailName %>" />&gt;">
    </logic:notEmpty>
    <logic:empty name="atskMdl" property="adrSei">
      <input type="hidden" name="<%= atskParamName[atskIdx] %>" value="<bean:write name="atskMdl" property="<%= adrMailName %>" />">
    </logic:empty>
    </logic:notEmpty>
    <%     adrMailName = ""; %>
    <% } %>
  </logic:iterate>
</logic:notEmpty>
<% } %>
<logic:notEmpty name="adr010Form" property="adr010SidsAtsk" scope="request">
  <logic:iterate id="sidsAtsk" name="adr010Form" property="adr010SidsAtsk" scope="request">
    <input type="hidden" name="adr010SidsAtsk" value="<bean:write name="sidsAtsk"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr010Form" property="adr010SidsCc" scope="request">
  <logic:iterate id="sidsCc" name="adr010Form" property="adr010SidsCc" scope="request">
    <input type="hidden" name="adr010SidsCc" value="<bean:write name="sidsCc"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr010Form" property="adr010SidsBcc" scope="request">
  <logic:iterate id="sidsBcc" name="adr010Form" property="adr010SidsBcc" scope="request">
    <input type="hidden" name="adr010SidsBcc" value="<bean:write name="sidsBcc"/>">
  </logic:iterate>
</logic:notEmpty>
<span id="adr010labelArea">
</span>
<input type="hidden" name="helpPrm" value="<bean:write name="adr010Form" property="adr010cmdMode" />">
<logic:notEqual name="adr010Form" property="adr010webmail" value="1">
  <jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
</logic:notEqual>
<input type="hidden" name="CMD" value="">
<input type="hidden" name="selectLabel" value="">
<html:hidden property="adr010cmdMode" />
<html:hidden property="adr010searchFlg" />
<html:hidden property="adr010InitDspContactFlg" />
<html:hidden property="adr010EditAdrSid" />
<html:hidden property="adr010orderKey" />
<html:hidden property="adr010sortKey" />
<html:hidden property="adr010page" />
<input type="hidden" name="adr010SltYearFrContact" />
<input type="hidden" name="adr010SltMonthFrContact" />
<input type="hidden" name="adr010SltDayFrContact" />
<input type="hidden" name="adr010SltYearToContact" />
<input type="hidden" name="adr010SltMonthToContact" />
<input type="hidden" name="adr010SltDayToContact" />
<input type="hidden" name="adr020ProcMode" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_ADD) %>">
<% if (cmdMode.intValue() == Adr010Const.CMDMODE_COMPANY) { %>
<html:hidden property="adr010svCode" />
<html:hidden property="adr010svCoName" />
<html:hidden property="adr010svCoNameKn" />
<html:hidden property="adr010svCoBaseName" />
<html:hidden property="adr010svAtiSid" />
<html:hidden property="adr010svTdfk" />
<html:hidden property="adr010svBiko" />
<html:hidden property="adr010SearchComKana" />
<html:hidden property="adr010svSearchComKana" />
<% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_NAME) { %>
<html:hidden property="adr010SearchKana" />
<html:hidden property="adr010svSearchKana" />
<% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_TANTO) { %>
<html:hidden property="adr010svTantoGroup" />
<html:hidden property="adr010svTantoUser" />
<% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_PROJECT) { %>
<html:hidden property="selectingProjectSv" />
<html:hidden property="projectKbnSv" />
<html:hidden property="statusKbnSv" />
<% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_DETAILED) { %>
<html:hidden property="adr010svUnameSei" />
<html:hidden property="adr010svUnameMei" />
<html:hidden property="adr010svUnameSeiKn" />
<html:hidden property="adr010svUnameMeiKn" />
<html:hidden property="adr010svDetailCoName" />
<html:hidden property="adr010svSyozoku" />
<html:hidden property="adr010svPosition" />
<html:hidden property="adr010svMail" />
<html:hidden property="adr010svDetailTantoGroup" />
<html:hidden property="adr010svDetailTantoUser" />
<html:hidden property="adr010svDetailAtiSid" />
<% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_CONTACT) { %>
<html:hidden property="adr010svTantoGroupContact" />
<html:hidden property="adr010svTantoUserContact" />
<html:hidden property="adr010svUnameSeiContact" />
<html:hidden property="adr010svUnameMeiContact" />
<html:hidden property="adr010svCoNameContact" />
<html:hidden property="adr010svCoBaseNameContact" />
<html:hidden property="adr010svProjectContact" />
<html:hidden property="adr010SvTempFilekbnContact" />
<html:hidden property="adr010svSltYearFrContact" />
<html:hidden property="adr010svSltMonthFrContact" />
<html:hidden property="adr010svSltDayFrContact" />
<html:hidden property="adr010svSltYearToContact" />
<html:hidden property="adr010svSltMonthToContact" />
<html:hidden property="adr010svSltDayToContact" />
<html:hidden property="adr010svSyubetsuContact" />
<html:hidden property="adr010svSearchWordContact" />
<html:hidden property="adr010SvKeyWordkbnContact" />
<html:hidden property="adr010svdateNoKbn" />
<logic:notEmpty name="adr010Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr010Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<% } %>
<logic:notEmpty name="adr010Form" property="adr010svSearchLabel">
<logic:iterate id="labelId" name="adr010Form" property="adr010svSearchLabel">
<input type="hidden" name="adr010svSearchLabel" value="<bean:write name="labelId" />">
</logic:iterate>
</logic:notEmpty>
<html:hidden property="adr010CategorySetInitFlg" />
<logic:notEmpty name="adr010Form" property="adr010CategoryOpenFlg">
<logic:iterate id="openFlg" name="adr010Form" property="adr010CategoryOpenFlg">
  <bean:define id="flg" name="openFlg" type="java.lang.String" />
  <html:hidden property="adr010CategoryOpenFlg" value="<%= flg %>" />
</logic:iterate>
</logic:notEmpty>
<input type="hidden" name="adr100backFlg" value="">
<input type="hidden" name="adr110ProcMode" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT) %>">
<input type="hidden" name="adr110editAcoSid" value="">
<%
  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
%>

<%-- タイトル --%>
<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="addressbook" />">
    </li>
    <li><gsmsg:write key="addressbook" /></li>
    <li class="pageTitle_subFont">
      <logic:notEqual name="adr010Form" property="adr010webmail" value="1">
        <gsmsg:write key="cmn.addressbook" />
      </logic:notEqual>
      <logic:equal name="adr010Form" property="adr010webmail" value="1">
        <gsmsg:write key="address.31" />
      </logic:equal>
    </li>
    <li>
      <div>
        <%-- WEBメール作成からダイアログで開いた場合 --%>
        <logic:equal name="adr010Form" property="adr010webmail" value="1">
          <logic:equal name="adr010Form" property="adr010webmailType" value="1">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.apply" />" onClick="setWebmailSendList(1, 'adr010SetMailMsg');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.apply" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_apply.png" alt="<gsmsg:write key="cmn.apply" />">
              <gsmsg:write key="cmn.apply" />
            </button>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010webmailType" value="1">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.apply" />" onClick="setWebmailData(1, 'adr010SetMailMsg');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.apply" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_apply.png" alt="<gsmsg:write key="cmn.apply" />">
              <gsmsg:write key="cmn.apply" />
            </button>
          </logic:notEqual>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
            <gsmsg:write key="cmn.close" />
          </button>
        </logic:equal>
      </div>
    </li>
  </ul>
</div>

<%-- 内容 --%>
<div class="wrapper_2column">
    <%-- 検索メニュー、ラベル選択 --%>
    <div class="side_multi-left bgC_none wp190">
      <logic:equal name="adr010Form" property="adr010webmail" value="1">
        <table class="w100 mb10 sideTop-font">
          <tr>
            <td class="bor1 txt_c cursor_p p0" onClick="changePopup();">
              <div class="searchMenu_top searchMenu_title">
                <gsmsg:write key="cmn.shain.info" />
              </div>
            </td>
            <td class="bor1 txt_c p0 cursor_p">
              <div class="searchMenu_top searchMenu_title-select">
                <gsmsg:write key="cmn.addressbook" />
              </div>
            </td>
          </tr>
        </table>
      </logic:equal>
      <table class="table-top mt0">
        <tr>
          <th class="w100 txt_l">
            <gsmsg:write key="cmn.search.menu" />
          </th>
        </tr>
        <tr>
          <td class="p0">
            <div class="js_search_menu_title searchMenu_title w100" id="0">
              <gsmsg:write key="address.139" />
            </div>
          </td>
        </tr>
        <tr>
          <td class="p0">
            <div class="js_search_menu_title searchMenu_title w100" id="1">
              <gsmsg:write key="cmn.name" />
            </div>
          </td>
        </tr>
        <tr>
          <td class="p0">
            <div class="js_search_menu_title searchMenu_title w100" id="2">
              <gsmsg:write key="cmn.staff" />
            </div>
          </td>
        </tr>
        <tr>
          <td class="p0">
            <div class="js_search_menu_title searchMenu_title w100" id="3">
              <gsmsg:write key="cmn.project" />
            </div>
          </td>
        </tr>
        <tr>
          <td class="p0">
            <div class="js_search_menu_title searchMenu_title w100" id="4">
              <gsmsg:write key="cmn.advanced.search" />
            </div>
          </td>
        </tr>
        <tr>
          <td class="p0">
            <div class="js_search_menu_title searchMenu_title w100" id="5">
              <gsmsg:write key="address.6" />
            </div>
          </td>
        </tr>
      </table>
      <jsp:include page="/WEB-INF/plugin/address/jsp/adr010_labelList.jsp" />
    </div>
    <%-- アドレス一覧 --%>
    <div class="main">
      <logic:notEqual name="adr010Form" property="adr010webmail" value="1">
        <div class="w100 mb5 txt_r">
          <logic:equal name="adr010Form" property="adr010AbleEdit" value="1">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.new.registration" />" onClick="setParams();buttonPush('addAdrData');">
              <img class="btn_classicImg-display" src="../address/images/classic/icon_add_adress.png" alt="<gsmsg:write key="cmn.new.registration" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.new.registration" />">
              <gsmsg:write key="cmn.new.registration" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="setParams();buttonPush('import');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <gsmsg:write key="cmn.import" />
            </button>
          </logic:equal>
          <logic:equal name="adr010Form" property="adr010viewYksBtn" value="1">
            <button type="button" class="baseBtn" value="<gsmsg:write key="address.adr010.1" />" onClick="setParams();buttonPush('setupYakusyoku');">
              <img class="btn_classicImg-display btnIcon-size" src="../common/images/classic/icon_user.png" alt="<gsmsg:write key="address.adr010.1" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_user.png" alt="<gsmsg:write key="address.adr010.1" />">
              <gsmsg:write key="address.adr010.1" />
            </button>
          </logic:equal>
          <logic:equal name="adr010Form" property="adr010viewGyosyuBtn" value="1">
            <button type="button" class="baseBtn" value="<gsmsg:write key="address.adr010.2" />" onClick="setParams();buttonPush('setupIndustry');">
              <img class="btn_classicImg-display" src="../address/images/classic/icon_gyousyu.png" alt="<gsmsg:write key="address.adr010.2" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_gyousyu.png" alt="<gsmsg:write key="address.adr010.2" />">
              <gsmsg:write key="address.adr010.2" />
            </button>
          </logic:equal>
          <logic:equal name="adr010Form" property="adr010viewCompanyBtn" value="1">
            <button type="button" class="baseBtn" value="<gsmsg:write key="address.118" />" onClick="setParams();buttonPush('setupCompany');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_company.png" alt="<gsmsg:write key="address.adr010.2" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_company.png" alt="<gsmsg:write key="address.adr010.2" />">
              <gsmsg:write key="address.118" />
            </button>
          </logic:equal>
        </div>
      </logic:notEqual>
      <logic:messagesPresent message="false">
        <html:errors/>
      </logic:messagesPresent>
      <% if (cmdMode.intValue() == Adr010Const.CMDMODE_COMPANY) { %>
        <jsp:include page="/WEB-INF/plugin/address/jsp/adr010_company.jsp" />
      <% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_NAME) { %>
        <jsp:include page="/WEB-INF/plugin/address/jsp/adr010_name.jsp" />
      <% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_TANTO) { %>
        <jsp:include page="/WEB-INF/plugin/address/jsp/adr010_tanto.jsp" />
      <% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_PROJECT) { %>
        <jsp:include page="/WEB-INF/plugin/address/jsp/adr010_project.jsp" />
      <% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_DETAILED) { %>
        <jsp:include page="/WEB-INF/plugin/address/jsp/adr010_detailed.jsp" />
      <% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_CONTACT) { %>
        <jsp:include page="/WEB-INF/plugin/address/jsp/adr010_contact.jsp" />
      <% } %>
      <logic:notEmpty name="adr010Form" property="detailList">
        <%-- WEBメール作成からダイアログで開いた場合のみ表示 --%>
        <logic:equal name="adr010Form" property="adr010webmail" value="1">
          <div class="mb10">
            <logic:equal name="adr010Form" property="adr010webmailType" value="1">
              <button type="button" class="baseBtn" value="<gsmsg:write key="anp.send.dest" />" onClick="return addAddress(0, 0, 'addUsrAtesaki');">
                <gsmsg:write key="anp.send.dest" />
              </button>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010webmailType" value="1">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.from" />" onClick="return addAddress(0, 0, 'addUsrAtesaki');">
                <gsmsg:write key="cmn.from" />
              </button>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.cc" />" onClick="return addAddress(1, 0, 'addUsrCc');">
                <gsmsg:write key="cmn.cc" />
              </button>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.bcc" />" onClick="return addAddress(2, 0, 'addUsrBcc');">
                <gsmsg:write key="cmn.bcc" />
              </button>
            </logic:notEqual>
          </div>
        </logic:equal>

        <table class="w100">
          <tr>
            <td class="pageTitle_sub txt_l w50">
              <span class="sub_title">
                <gsmsg:write key="cmn.search.criteria" />
              </span>
            </td>
            <logic:notEqual name="adr010Form" property="adr010webmail" value="1">
              <td class="txt_r w60">
                <logic:equal name="adr010Form" property="adr010AbleEdit" value="1">
                  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="setParams();buttonPush('adrDelete');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <gsmsg:write key="cmn.delete" />
                  </button>
                  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add.label" />" onClick="openlabel();">
                    <img class="btn_classicImg-display btnIcon-size" src="../	common/images/classic/icon_tag.gif" alt="<gsmsg:write key="cmn.add.label" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_label_add.png" alt="<gsmsg:write key="cmn.add.label" />">
                    <gsmsg:write key="cmn.add.label" />
                  </button>
                </logic:equal>
                <% if (cmdMode.intValue() != Adr010Const.CMDMODE_CONTACT) { %>
                  <logic:equal name="adr010Form" property="adr010viewExportBtn" value="1">
                    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onClick="buttonPush('export');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                      <gsmsg:write key="cmn.export" />
                    </button>
                  </logic:equal>
                <% } else {%>
                  <logic:equal name="adr010Form" property="adr010viewExportBtn" value="1">
                    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onClick="buttonPush('exportContact');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                      <gsmsg:write key="cmn.export" />
                    </button>
                  </logic:equal>
                <% }%>
              </td>
            </logic:notEqual>
          </tr>
          <tr>
            <logic:notEmpty name="adr010Form" property="pageCmbList">
              <td>
            </logic:notEmpty>
            <logic:empty name="adr010Form" property="pageCmbList">
              <td colspan="2">
            </logic:empty>
              <%-- 検索対象：氏名、会社 --%>
              <% if (cmdMode.intValue() == Adr010Const.CMDMODE_NAME || cmdMode.intValue() == Adr010Const.CMDMODE_COMPANY) { %>
                <% boolean writedSearch = false; %>
                <% if (cmdMode.intValue() == Adr010Const.CMDMODE_COMPANY) { %>
                  <logic:notEmpty name="adr010Form" property="adr010svSearchComKana">
                    <bean:define id="comKana" name="adr010Form" property="adr010svSearchComKana" type="java.lang.String" />
                    <% String comName = gsMsg.getMessage(request, "cmn.company.name"); %>
                    <gsmsg:write key="user.usr040.31" arg0="<%= comName %>" arg1="<%= comKana %>" />
                    <logic:notEmpty name="adr010Form" property="adr010searchLabelString">
                      (<bean:write name="adr010Form" property="adr010searchLabelString" filter="false"/>)
                    </logic:notEmpty>
                    <% writedSearch = true; %>
                  </logic:notEmpty>
                  <logic:notEmpty name="adr010Form" property="adr010searchParamString">
                    <bean:define id="searchPrmOth" name="adr010Form" property="adr010searchParamString" type="java.lang.String" />
                    <gsmsg:write key="address.38" arg0="<%= searchPrmOth %>" />
                    <logic:notEmpty name="adr010Form" property="adr010searchLabelString">
                      (<bean:write name="adr010Form" property="adr010searchLabelString" filter="false"/>)
                    </logic:notEmpty>
                    <% writedSearch = true; %>
                  </logic:notEmpty>
                  <% if (!writedSearch) { %>
                    <logic:empty  name="adr010Form" property="adr010searchLabelString">
                      <% String allSearch = gsMsg.getMessage(request, "address.adr010.7"); %>
                      <gsmsg:write key="address.38" arg0="<%= allSearch %>" />
                    </logic:empty>
                    <logic:notEmpty  name="adr010Form" property="adr010searchLabelString">
                      <bean:define id="srhLabel" name="adr010Form" property="adr010searchLabelString" type="java.lang.String" />
                      <gsmsg:write key="address.38" arg0="<%= srhLabel %>" />
                    </logic:notEmpty>
                  <% } %>
                <% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_NAME) { %>
                  <logic:notEmpty name="adr010Form" property="adr010svSearchKana">
                    <bean:define id="adr010SrhKana" name="adr010Form" property="adr010svSearchKana" type="java.lang.String" />
                    <% String nameStr = gsMsg.getMessage(request, "cmn.name"); %>
                    <gsmsg:write key="user.usr040.31" arg0="<%= nameStr %>" arg1="<%= adr010SrhKana %>" />
                    <logic:notEmpty name="adr010Form" property="adr010searchLabelString">
                      (<bean:write name="adr010Form" property="adr010searchLabelString" filter="false"/>)
                    </logic:notEmpty>
                    <% writedSearch = true; %>
                  </logic:notEmpty>
                <% if (!writedSearch) { %>
                  <logic:notEmpty  name="adr010Form" property="adr010searchLabelString">
                    <bean:define id="adr010NameSrhLabel" name="adr010Form" property="adr010searchLabelString" type="java.lang.String" />
                    <gsmsg:write key="address.38" arg0="<%= adr010NameSrhLabel %>" />
                  </logic:notEmpty>
                <% } %>
              <% } %>
              <%-- 検索対象：プロジェクト --%>
              <% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_PROJECT) { %>
                <logic:empty name="adr010Form" property="adr010searchParamString">
                  <% String allProjects = gsMsg.getMessage(request, "cmn.allprojects"); %>
                  <gsmsg:write key="address.38" arg0="<%= allProjects %>" />
                </logic:empty>
                <logic:notEmpty name="adr010Form" property="adr010searchParamString">
                  <bean:define id="adr010PrjString" name="adr010Form" property="adr010searchParamString" type="java.lang.String" />
                  <gsmsg:write key="address.38" arg0="<%= adr010PrjString %>" />
                </logic:notEmpty>
              <%-- 検索対象：担当者、詳細検索、コンタクト履歴 --%>
              <% } else { %>
                <logic:empty name="adr010Form" property="adr010searchParamString">
                  <% String allSearchOth = gsMsg.getMessage(request, "address.adr010.7"); %>
                  <gsmsg:write key="address.38" arg0="<%= allSearchOth %>" />
                </logic:empty>
                <logic:notEmpty name="adr010Form" property="adr010searchParamString">
                  <bean:define id="searchPrmOth" name="adr010Form" property="adr010searchParamString" type="java.lang.String" />
                  <gsmsg:write key="address.38" arg0="<%= searchPrmOth %>" />
                </logic:notEmpty>
              <% } %>
            </td>
           <%-- 上部ページ切り替えコンボ --%>
            <logic:notEmpty name="adr010Form" property="pageCmbList">
              <td class="no_w">
                <div class="paging">
                  <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
                    <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                    <i class="icon-paging_left"></i>
                  </button>
                  <html:select name="adr010Form" styleClass="paging_combo"  property="adr010pageTop" onchange="changePage('adr010pageTop');">
                    <html:optionsCollection name="adr010Form" property="pageCmbList" value="value" label="label" />
                  </html:select>
                  <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
                    <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                    <i class="icon-paging_right"></i>
                  </button>
                </div>
              </td>
            </logic:notEmpty>
          </tr>
        </table>

        <%-- 検索結果 --%>
        <logic:notEqual name="adr010Form" property="adr010webmail" value="1">
          <jsp:include page="/WEB-INF/plugin/address/jsp/adr010address.jsp" >
            <jsp:param name="cmdMode" value="<%=cmdMode%>"/>
          </jsp:include>
        </logic:notEqual>
        <logic:equal name="adr010Form" property="adr010webmail" value="1">
          <jsp:include page="/WEB-INF/plugin/address/jsp/adr010webmail.jsp" >
            <jsp:param value="<%=cmdMode %>" name="cmdMode"/>
          </jsp:include>
        </logic:equal>
        <%-- 下部ページ切り替えコンボ --%>
        <logic:notEmpty name="adr010Form" property="pageCmbList">
          <div class="paging">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select name="adr010Form" styleClass="paging_combo"  property="adr010pageBottom" onchange="changePage('adr010pageBottom');">
              <html:optionsCollection name="adr010Form" property="pageCmbList" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:notEmpty>
      </logic:notEmpty>
      <logic:equal name="adr010Form" property="adr010webmail" value="1">
        <jsp:include page="/WEB-INF/plugin/address/jsp/adr010webmailSend.jsp" />
      </logic:equal>
  </div>
</html:form>
</div>

<logic:notEqual name="adr010Form" property="adr010webmail" value="1">
  <jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
</logic:notEqual>

<logic:equal name="adr010Form" property="adr010webmail" value="1">
  <div class="footerBtn_block txt_r">
    <logic:equal name="adr010Form" property="adr010webmailType" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.apply" />" onClick="setWebmailSendList(1, 'adr010SetMailMsg');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.apply" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_apply.png" alt="<gsmsg:write key="cmn.apply" />">
        <gsmsg:write key="cmn.apply" />
      </button>
    </logic:equal>
    <logic:notEqual name="adr010Form" property="adr010webmailType" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.apply" />" onClick="setWebmailData(1, 'adr010SetMailMsg');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.apply" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_apply.png" alt="<gsmsg:write key="cmn.apply" />">
        <gsmsg:write key="cmn.apply" />
      </button>
    </logic:notEqual>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>
</logic:equal>

<div id="labelPanel" class="display_n txt_c p0">
  <div><iframe src="../common/html/damy.html" name="lab" id="labelAdd" class="wp350 hp300 border_none mt10"></iframe></div>
</div>
</body>
</html:html>
