<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%@ page import="jp.groupsession.v2.cmn.GSConstWebmail" %>
<%@ page import="jp.groupsession.v2.wml.wml030.Wml030AccountModel" %>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml030.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmnOAuth.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION<gsmsg:write key="wml.wml020.08" /></title>

  <script type="text/javascript">
  <!--
    var msg_loading = '<gsmsg:write key="cmn.loading"/>';
  -->
  </script>
</head>

<body>

<html:form action="/webmail/wml030" styleId="webmailForm">
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<input type="hidden" name="wmlCmdMode" value="0">
<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlAccountMode" />
<html:hidden property="wmlAccountSid" />

<html:hidden property="wml030svKeyword" />
<html:hidden property="wml030svGroup" />
<html:hidden property="wml030svUser" />
<html:hidden property="wml030svNinsyo" />
<html:hidden property="wml030sortKey" />
<html:hidden property="wml030order" />
<html:hidden property="wml030searchFlg" />

<input type="hidden" name="wmlMailTemplateKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.MAILTEMPLATE_NORMAL) %>">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.wml020.08" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('acntImport');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('accountDetail', 0);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('accountDelete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('admTool');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper mrl_auto">

  <bean:define id="pageDspFlg" name="wml030Form" property="wml030pageDspFlg" type="java.lang.Boolean" />

  <table class="table-top">
    <tr>
      <td class="txt_l w100 border_none">
        <div class="no_w display_inline-block">
          <html:text name="wml030Form" property="wml030keyword" maxlength="50" styleClass="wp180" />
          <button type="button" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');" class="baseBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
        </div>

        <div class="ml20 no_w display_inline-block">
          <gsmsg:write key="cmn.group" />
          <html:select name="wml030Form" property="wml030group" onchange="buttonPush('init');">
            <html:optionsCollection name="wml030Form" property="groupCombo" value="value" label="label" />
          </html:select>
          <button class="iconBtn-border ml5" id="rng130GroupBtn" value="&nbsp;&nbsp;" onClick="openGroupWindow(this.form.wml030group, 'wml030group', '0', 'init');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
            <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
          </button>
        </div>

        <div class="ml20 no_w display_inline-block">
          <gsmsg:write key="cmn.user" />
          <html:select name="wml030Form" property="wml030user">
            <logic:notEmpty name="wml030Form" property="userCombo">
              <logic:iterate id="user" name="wml030Form" property="userCombo" >
                <bean:define id="userValue" name="user" property="value" />
                <bean:define id="mukoUserClass" value="" />
                <logic:equal name="user" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUserOption" /></logic:equal>
                <html:option styleClass="<%=mukoUserClass %>" value="<%=String.valueOf(userValue) %>"><bean:write name="user" property="label" /></html:option>
              </logic:iterate>
            </logic:notEmpty>
          </html:select>
        </div>
        
        <div class="ml20 no_w display_inline-block">
          <gsmsg:write key="wml.313" />
          <html:select name="wml030Form" property="wml030ninsyo" styleClass="js_ninsyo-combo">
            <html:optionsCollection name="wml030Form" property="ninsyoCombo" value="value" label="label" />
          </html:select>
        </div>
        <div class="ml10 no_w display_inline-block">
          <span class="js_ninsyo-radio">
            <html:radio name="wml030Form" styleId="wml030ninsyouKbn_00" property="wml030ninsyoStatus" value="0" />
            <label for="wml030ninsyouKbn_00"><gsmsg:write key="cmn.all" /></label>
            <html:radio styleClass="ml10" name="wml030Form" styleId="wml030ninsyouKbn_01" property="wml030ninsyoStatus" value="1" />
            <label for="wml030ninsyouKbn_01"><gsmsg:write key="wml.314" /></label>
            <html:radio styleClass="ml10" name="wml030Form" styleId="wml030ninsyouKbn_02" property="wml030ninsyoStatus" value="2" />
            <label for="wml030ninsyouKbn_02"><gsmsg:write key="wml.315" /></label>
          </span>
        </div>
      </td>
    </tr>
  </table>

  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

<logic:notEmpty name="wml030Form" property="accountList">

  <div class="mt20 txt_b">
    <span class="flo_r">
      <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.export" />" onClick="return buttonPush('acntExport');">
        <img src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_classicImg-display">
        <img src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />" class="btn_originalImg-display">
        <gsmsg:write key="cmn.export" />
      </button>
      <% if (pageDspFlg.booleanValue()) { %>
      <div class="paging">
        <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  property="wml030pageTop" onchange="changePage(0);">
          <html:optionsCollection name="wml030Form" property="pageCombo" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </span>
      <% } %>
  </div>

  <bean:define id="orderValue" name="wml030Form" property="wml030order" type="java.lang.Integer" />
  <%  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
      String account = gsMsg.getMessage(request, "wml.96");
      String mail = gsMsg.getMessage(request, "cmn.mailaddress");
      String user = gsMsg.getMessage(request, "cmn.employer");
      String disk = gsMsg.getMessage(request, "wml.88");
      String date = gsMsg.getMessage(request, "cmn.received.date");
      String authType = gsMsg.getMessage(request, "wml.313");
      String down = "<span class=\"classic-display\">"
                    + gsMsg.getMessage(request, "tcd.tcd040.23")
                    + "</span><span class=\"original-display txt_m\"><i class=\"icon-sort_down\"></i></span>";
      String up = "<span class=\"classic-display\">"
                    + gsMsg.getMessage(request, "tcd.tcd040.22")
                    + "</span><span class=\"original-display txt_m\"><i class=\"icon-sort_up\"></i></span>";
  %>
  <% String orderLeft = ""; %>
  <% String orderRight = up; %>
  <% String nextOrder = String.valueOf(GSConstWebmail.ORDER_DESC); %>
  <% if (orderValue.intValue() == GSConstWebmail.ORDER_DESC) { %>
  <%    orderLeft = ""; %>
  <%    orderRight = down; %>
  <%    nextOrder = String.valueOf(GSConstWebmail.ORDER_ASC); %>
  <% } %>

  <bean:define id="sortValue" name="wml030Form" property="wml030sortKey" type="java.lang.Integer" />
  <% String[] orderList = {String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC)}; %>
  <% String[] titleList = {account, mail, user, disk, date, authType}; %>
  <% int titleIndex = 0; %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_MAIL) { titleIndex = 1; } %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_USER) { titleIndex = 2; } %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_DISKSIZE) { titleIndex = 3; } %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_RECEIVEDATE) { titleIndex = 4; } %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_AUTHTYPE) { titleIndex = 5; } %>
  <% titleList[titleIndex] = orderLeft + titleList[titleIndex] + orderRight; %>
  <% orderList[titleIndex] = nextOrder; %>

  <table class="table-top w100">
    <tr>
      <th class="w3 table_title-color fs_13 js_tableTopCheck js_tableTopCheck-header cursor_p">
        <input type="checkbox" name="allChk" value="1" onchange="changeChk();">
      </th>
      <th class="w12 table_title-color fs_13 no_w">
        <gsmsg:write key="wml.281" />
      </th>
      <th class="w11 table_title-color fs_13 cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_ACCOUNTNAME) %>, <%= orderList[0] %>);"><span class="cl_fontOutline"><%= titleList[0] %></span></a>
      </th>
      <th class="w18 table_title-color fs_13 cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_MAIL) %>, <%= orderList[1] %>);"><span class="cl_fontOutline"><%= titleList[1] %></span></a>
      </th>
      <th class="w8 table_title-color fs_13 cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_USER) %>, <%= orderList[2] %>);"><span class="cl_fontOutline"><%= titleList[2] %></span></a>
      </th>
      <th class="w13 table_title-color fs_13 cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_DISKSIZE) %>, <%= orderList[3] %>);"><span class="cl_fontOutline"><%= titleList[3] %></span></a>
      </th>
      <th class="w10 table_title-color fs_13 cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_RECEIVEDATE) %>, <%= orderList[4] %>);"><span class="cl_fontOutline"><%= titleList[4] %></span></a>
      </th>
      <th class="w4 table_title-color no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_AUTHTYPE) %>, <%= orderList[5] %>);"><span class="cl_fontOutline"><%= titleList[5] %></span></a>
      </th>
      <th class="w8 table_title-color fs_13 no_w">
        <gsmsg:write key="cmn.memo" />
      </th>
      <th class="w3 table_title-color">
        &nbsp;
      </th>
      <th class="w10 table_title-color">
      </th>
    </tr>
  <logic:iterate id="accountData" name="wml030Form" property="accountList" indexId="idx">
    <bean:define id="backclass" value="td_line_color" />
    <bean:define id="backclass_no_edit" value="td_line_no_edit_color" />
    <bean:define id="backpat" value="<%= String.valueOf((idx.intValue() % 2) + 1) %>" />
    <bean:define id="back" value="<%= String.valueOf(backclass) + String.valueOf(backpat) %>" />
    <bean:define id="back_no_edit" value="<%= String.valueOf(backclass_no_edit) + String.valueOf(backpat) %>" />
    <tr class="<%= String.valueOf(back) %> fs_13" id="<bean:write name="accountData" property="accountSid" />">
      <td class="w3 txt_c js_tableTopCheck cursor_p">
        <% if (Integer.valueOf(backpat) == 1) { %>
        <html:multibox name="wml030Form" property="wml030selectAcount" onclick="backGroundSetting(this, '1');">
          <bean:write name="accountData" property="accountSid" />
        </html:multibox>
        <% } else { %>
        <html:multibox name="wml030Form" property="wml030selectAcount" onclick="backGroundSetting(this, '2');">
          <bean:write name="accountData" property="accountSid" />
        </html:multibox>
        <% } %>
      </td>
      <td class="w12">
        <bean:write name="accountData" property="accountUid" />
      </td>
      <td class="w11">
        <bean:write name="accountData" property="accountName" /></a>
      </td>
      <td class="w18">
        <bean:write name="accountData" property="mailAddress" />
      </td>
      <td class="w8">
        <bean:write name="accountData" property="accountUserCount" />&nbsp;<bean:write name="accountData" property="accountUserKbnString" />
      </td>
      <td class="w13 txt_r">
        <bean:write name="accountData" property="diskSizeUse" />MB
      </td>
      <td class="w8 txt_c">
        <bean:write name="accountData" property="viewReceiveDate" />
      </td>
      <td class="w4 txt_c">
        <logic:equal name="accountData" property="authType" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
          <gsmsg:write key="wml.310" />
          <br>
          <bean:define id="oauthFlg" name="accountData" property="oauthCertifiedFlg" type="java.lang.Boolean" />
          <%  if (oauthFlg) { %><gsmsg:write key="wml.314"/>
          <%  } else { %><span class="cl_fontWarn"><gsmsg:write key="wml.315"/></span><% } %>
          <input type="hidden" name="provider_<bean:write name="accountData" property="accountUid" />" value="<bean:write name="accountData" property="couSid" />">
          <input type="hidden" name="mailAddress_<bean:write name="accountData" property="accountUid" />" value="<bean:write name="accountData" property="mailAddress" />">
          <input type="hidden" name="cotSid_<bean:write name="accountData" property="accountUid" />" value="<bean:write name="accountData" property="cotSid" />">
          <br>
          <button type="button" class="baseBtn no_w" onclick="doOAuth('provider_<bean:write name="accountData" property="accountUid" />', 'mailAddress_<bean:write name="accountData" property="accountUid" />', 'cotSid_<bean:write name="accountData" property="accountUid" />');"><gsmsg:write key="wml.313" /></button>
        </logic:equal>
        <logic:notEqual name="accountData" property="authType" value="<%= String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH) %>">
          <span class="no_w">
            <gsmsg:write key="wml.309" />
          </span>
        </logic:notEqual>
      </td>
      <td class="w10">
        <bean:write name="accountData" property="viewBiko" filter="false" />
      </td>
      <% String strWacSid = Integer.toString(((Wml030AccountModel) accountData).getAccountSid()); %>
      <td class="w3">
        <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.edit" />" onClick="editAccount(<%= String.valueOf(GSConstWebmail.CMDMODE_EDIT) %>, <%= strWacSid %>);">
          <gsmsg:write key="cmn.edit" />
        </button>
      </td>
      <td class="w10 txt_c">
        <button class="baseBtn wp100 fs_13 hp22" value="<gsmsg:write key="cmn.label" />" onclick="confLabel(<%= strWacSid %>);">
          <gsmsg:write key="cmn.label" />
        </button>
        <br>
        <button class="baseBtn wp100 fs_13 hp22" value="<gsmsg:write key="wml.248" />" onclick="confFilter(<%= strWacSid %>);">
          <gsmsg:write key="wml.248" />
        </button>
        <br>
        <button class="baseBtn wp100 fs_13 hp22 no_w" value="<gsmsg:write key="cmn.template" />" onclick="confTemplate(<%= strWacSid %>);">
          <gsmsg:write key="cmn.template" />
        </button>
      </td>
    </tr>
  </logic:iterate>
  </table>

</logic:notEmpty>

  <% if (pageDspFlg.booleanValue()) { %>
  <div class="paging">
    <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select styleClass="paging_combo"  property="wml030pageBottom" onchange="changePage(1);">
      <html:optionsCollection name="wml030Form" property="pageCombo" value="value" label="label" />
    </html:select>
    <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
      <i class="icon-paging_right"></i>
    </button>
  </div>
  <% } %>
</div>

</html:form>

<div id="loading_pop" title="" style="display:none;">
  <table class="w100 h100">
    <tr>
      <td class="txt_m txt_c">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
        <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
      </td>
    </tr>
  </table>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>