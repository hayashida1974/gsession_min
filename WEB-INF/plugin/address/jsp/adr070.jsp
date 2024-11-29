<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.adr.GSConstAddress" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="address.adr070.1" /></title>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr070.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body class="body_03" onload="viewchange();editchange();">

<html:form action="/address/adr070">

<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>
<html:hidden property="adr070init" name="adr070Form"/>
<logic:notEmpty name="adr070Form" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr070Form" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr070Form" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr070Form" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr070Form" property="adr010searchLabel">
<logic:iterate id="searchLabel" name="adr070Form" property="adr010searchLabel">
  <input type="hidden" name="adr010searchLabel" value="<bean:write name="searchLabel" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr070Form" property="adr010svSearchLabel">
<logic:iterate id="svSearchLabel" name="adr070Form" property="adr010svSearchLabel">
  <input type="hidden" name="adr010svSearchLabel" value="<bean:write name="svSearchLabel" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr070Form" property="projectCmbList">
<logic:iterate id="project" name="adr070Form" property="projectCmbList">
  <input type="hidden" name="projectCmbList" value="<bean:write name="project" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr070Form" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr070Form" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<input type="hidden" name="helpPrm" value="<bean:write name="adr070Form" property="adr070cmdMode" />">

<html:hidden property="adr070cmdMode" />

<%-- BODY --%>
<%-- タイトル --%>
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="address.adr070.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('importAddressConfirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAddressList');">
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
  <ul class="tabHeader w100">
    <logic:equal name="adr070Form" property="adr070cmdMode" value="1">
      <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none bgI_none" id="tab1" onClick="buttonPush('douji');">
        <gsmsg:write key="address.adr070.5" />
      </li>
      <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab2" onClick="buttonPush('tujou');">
        <gsmsg:write key="address.adr070.4" />
      </li>
    </logic:equal>
    <logic:notEqual name="adr070Form" property="adr070cmdMode" value="1">
      <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab1" onClick="buttonPush('douji');">
        <gsmsg:write key="address.adr070.5" />
      </li>
      <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none bgI_none" id="tab2" onClick="buttonPush('tujou');">
        <gsmsg:write key="address.adr070.4" />
      </li>
    </logic:notEqual>
    <li class="tabHeader_space border_bottom_none"></li>
  </ul>
  <logic:equal name="adr070Form" property="adr070cmdMode" value="1">
    <table id="tab1_table" class="table-left w100 mt0">
      <!-- アドレス・会社情報同時 -->
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.capture.file" />
          <span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
        </th>
        <td class="w75">
          <span class="fs_13">
            <% jp.groupsession.v2.struts.msg.GsMessage gsMsg1 = new jp.groupsession.v2.struts.msg.GsMessage(); %>
            <% String csvFileMsg1 = "<a href=\"../address/adr070.do?CMD=downloadCsv&adr070cmdMode=1\">" + gsMsg1.getMessage(request, "cmn.capture.csvfile") + "</a>"; %>
            *<gsmsg:write key="cmn.plz.specify2" arg0="<%= csvFileMsg1 %>" />
          </span>
          <attachmentFile:filearea
            mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
            pluginId="<%= GSConstAddress.PLUGIN_ID_ADDRESS %>"
            tempDirId="adr070" />
        </td>
      </tr>
      <%-- 上書き --%>
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.overwrite" />
        </th>
        <td class="w75">
          <span class="verAlignMid"><html:checkbox name="adr070Form" property="adr070updateFlg" value="1" styleId="updateFlg" /><label for="updateFlg"><gsmsg:write key="address.109" /></label></span>
        </td>
      </tr>
      <%-- 担当者選択 --%>
      <tr>
        <th class="w25">
          <gsmsg:write key="address.46" />
        </th>
        <td class="w75">
          <ui:usrgrpselector name="adr070Form" property="adr070tantoListUI" styleClass="hp215" />
        </td>
      </tr>
      <tr>
        <th class="25">
          <gsmsg:write key="address.61" />
          <span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
        </th>
        <td>
          <div class="verAlignMid">
            <html:radio property="adr070permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_OWN) %>" styleId="view0" onclick="viewchange();" /><label for="view0"><gsmsg:write key="address.62" /></label>
            <html:radio styleClass="ml10" property="adr070permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_GROUP) %>" styleId="view1" onclick="viewchange();" /><label for="view1"><gsmsg:write key="group.designation" /></label>
            <html:radio styleClass="ml10" property="adr070permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_USER) %>" styleId="view2" onclick="viewchange();" /><label for="view2"><gsmsg:write key="cmn.user.specified" /></label>
            <html:radio styleClass="ml10" property="adr070permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_NORESTRICTION) %>" styleId="view3" onclick="viewchange();" /><label for="view3"><gsmsg:write key="cmn.no.setting.permission" /></label>
          </div>
          <%-- グループ指定 --%>
          <div id="viewgroup">
            <ui:usrgrpselector name="adr070Form" property="adr070permitViewGroupUI" styleClass="hp215" />
          </div>
          <%--  ユーザ指定 --%>
          <div id="viewuser">
            <ui:usrgrpselector name="adr070Form" property="adr070permitViewUserUI" styleClass="hp215" />
          </div>
        </td>
      </tr>
      <%-- 編集権限 --%>
      <tr>
        <th>
          <gsmsg:write key="cmn.edit.permissions" />
          <span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
        </th>
        <td>
          <div id="editselect" class="verAlignMid">
            <html:radio property="adr070permitEdit" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_OWN) %>" styleId="edit0" onclick="editchange();" /><label for="edit0"><gsmsg:write key="address.62" /></label>
            <html:radio styleClass="ml10" property="adr070permitEdit" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_GROUP) %>" styleId="edit1" onclick="editchange();" /><label for="edit1"><gsmsg:write key="group.designation" /></label>
            <html:radio styleClass="ml10" property="adr070permitEdit" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_USER) %>" styleId="edit2" onclick="editchange();" /><label for="edit2"><gsmsg:write key="cmn.user.specified" /></label>
            <html:radio styleClass="ml10" property="adr070permitEdit" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_NORESTRICTION) %>" styleId="edit3" onclick="editchange();" /><label for="edit3"><gsmsg:write key="cmn.no.setting.permission" /></label>
          </div>
          <div id="editselectstr"></div>
          <%-- グループ指定 --%>
          <div id="editgroup">
            <ui:usrgrpselector name="adr070Form" property="adr070permitEditGroupUI" styleClass="hp215" />
          </div>
          <%-- ユーザ指定 --%>
          <div id="edituser">
            <ui:usrgrpselector name="adr070Form" property="adr070permitEditUserUI" styleClass="hp215" />
          </div>
        </td>
      </tr>
    </table>
  </logic:equal>
  <logic:notEqual name="adr070Form" property="adr070cmdMode" value="1">
    <table id="tab2_table" class="table-left w100 mt0">
      <!-- 会社指定 -->
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.capture.file" />
          <span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
        </th>
        <td class="w75">
          <span class="fs_13">
            <% jp.groupsession.v2.struts.msg.GsMessage gsMsg2 = new jp.groupsession.v2.struts.msg.GsMessage(); %>
            <% String csvFileMsg2 = "<a href=\"../address/adr070.do?CMD=downloadCsv&adr070cmdMode=0\">" + gsMsg2.getMessage(request, "cmn.capture.csvfile") + "</a>"; %>
            *<gsmsg:write key="cmn.plz.specify2" arg0="<%= csvFileMsg2 %>" />
          </span>
          <attachmentFile:filearea
            mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
            pluginId="<%= GSConstAddress.PLUGIN_ID_ADDRESS %>"
            tempDirId="adr070" />
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.select.company" />
        </th>
        <td class="w75">
          <div>
            <gsmsg:write key="address.139" /><span class="ml5">:</span>
            <html:select name="adr070Form" property="adr070selectCompany" styleClass="wp200 ml5" onchange="buttonPush('init');">
              <html:optionsCollection name="adr070Form" property="adr070CompanyCombo" value="value" label="label" />
            </html:select>
          </div>
          <div class="mt5">
            <gsmsg:write key="address.10" /><span class="ml5">:</span>
            <html:select name="adr070Form" property="adr070selectCompanyBase" styleClass="wp200 ml5" onchange="buttonPush('init');">
              <html:optionsCollection name="adr070Form" property="adr070CompanyBaseCombo" value="value" label="label" />
            </html:select>
          </div>
        </td>
      </tr>
      <%-- 担当者選択 --%>
      <tr>
        <th class="w25">
          <gsmsg:write key="address.46" />
        </th>
        <td class="w75">
          <ui:usrgrpselector name="adr070Form" property="adr070tantoListUI" styleClass="hp215" />
        </td>
      </tr>
      <tr>
        <th class="25">
          <gsmsg:write key="address.61" />
          <span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
        </th>
        <td>
          <div class="verAlignMid">
            <html:radio property="adr070permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_OWN) %>" styleId="view0" onclick="viewchange();" /><label for="view0"><gsmsg:write key="address.62" /></label>
            <html:radio styleClass="ml10" property="adr070permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_GROUP) %>" styleId="view1" onclick="viewchange();" /><label for="view1"><gsmsg:write key="group.designation" /></label>
            <html:radio styleClass="ml10" property="adr070permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_USER) %>" styleId="view2" onclick="viewchange();" /><label for="view2"><gsmsg:write key="cmn.user.specified" /></label>
            <html:radio styleClass="ml10" property="adr070permitView" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_NORESTRICTION) %>" styleId="view3" onclick="viewchange();" /><label for="view3"><gsmsg:write key="cmn.no.setting.permission" /></label>
          </div>
          <%-- グループ指定 --%>
          <div id="viewgroup">
            <ui:usrgrpselector name="adr070Form" property="adr070permitViewGroupUI" styleClass="hp215" />
          </div>
          <%--  ユーザ指定 --%>
          <div id="viewuser">
            <ui:usrgrpselector name="adr070Form" property="adr070permitViewUserUI" styleClass="hp215" />
          </div>
        </td>
      </tr>
      <%-- 編集権限 --%>
      <tr>
        <th>
          <gsmsg:write key="cmn.edit.permissions" />
          <span class="cl_fontWarn"><gsmsg:write key="cmn.asterisk" /></span>
        </th>
        <td>
          <div id="editselect" class="verAlignMid">
            <html:radio property="adr070permitEdit" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_OWN) %>" styleId="edit0" onclick="editchange();" /><label for="edit0"><gsmsg:write key="address.62" /></label>
            <html:radio styleClass="ml10" property="adr070permitEdit" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_GROUP) %>" styleId="edit1" onclick="editchange();" /><label for="edit1"><gsmsg:write key="group.designation" /></label>
            <html:radio styleClass="ml10" property="adr070permitEdit" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_USER) %>" styleId="edit2" onclick="editchange();" /><label for="edit2"><gsmsg:write key="cmn.user.specified" /></label>
            <html:radio styleClass="ml10" property="adr070permitEdit" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_NORESTRICTION) %>" styleId="edit3" onclick="editchange();" /><label for="edit3"><gsmsg:write key="cmn.no.setting.permission" /></label>
          </div>
          <div id="editselectstr"></div>
          <%-- グループ指定 --%>
          <div id="editgroup">
            <ui:usrgrpselector name="adr070Form" property="adr070permitEditGroupUI" styleClass="hp215" />
          </div>
          <%-- ユーザ指定 --%>
          <div id="edituser">
            <ui:usrgrpselector name="adr070Form" property="adr070permitEditUserUI" styleClass="hp215" />
          </div>
        </td>
      </tr>
    </table>
  </logic:notEqual>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('importAddressConfirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <gsmsg:write key="cmn.import" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backAddressList');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>


</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</div>
</body>
</html:html>
