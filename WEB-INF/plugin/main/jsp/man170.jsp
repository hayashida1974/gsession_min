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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man170.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="main.man170.1" /></title>
</head>

<body onload="tarminalChange(0);">
<html:form action="/main/man170">
<input type="hidden" name="CMD" value="">
<html:hidden property="man170SortKey" />
<html:hidden property="man170OrderKey" />
<html:hidden property="man050SelectedUsrSid" />
<html:hidden property="man050Backurl" />
<html:hidden property="man050grpSid" />
<html:hidden property="man050SortKey" />
<html:hidden property="man050OrderKey" />
<html:hidden property="man050FrYear" />
<html:hidden property="man050FrMonth" />
<html:hidden property="man050FrDay" />
<html:hidden property="man050ToYear" />
<html:hidden property="man050ToMonth" />
<html:hidden property="man050ToDay" />
<html:hidden property="man050cmdMode" />
<html:hidden property="man050usrSid" />
<html:hidden property="man050Terminal" />
<html:hidden property="man050Car" />
<html:hidden property="man050PageTop" />
<html:hidden property="man050PageBottom" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../main/images/classic/header_main.png" alt="<gsmsg:write key="cmn.main" />">
      <img class="header_pluginImg" src="../main/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.main" />">
    </li>
    <li><gsmsg:write key="cmn.main" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man170.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('backToLoginList');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <table class="table-left wp300">
    <tr>
      <th class="w30 no_w">
        <gsmsg:write key="cmn.name" />
      </th>
      <td class="w70 no_w">
        <logic:equal name="man170Form" property="man170UserJtkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
          <s><bean:write name="man170Form" property="man170UserName" /></s>
        </logic:equal>
        <logic:notEqual name="man170Form" property="man170UserJtkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
          <bean:write name="man170Form" property="man170UserName" />
        </logic:notEqual>
      </td>
    </tr>
  </table>
  <div class="txt_l">
    <span class="verAlignMid">
      <span class="fw_b"><gsmsg:write key="main.man050.6" /></span>:
      <html:radio property="man170Terminal" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.TERMINAL_KBN_ALL) %>" styleId="terminal0" onclick="return tarminalChange(1)" />
      <label for="terminal0"><gsmsg:write key="cmn.all" /></label>
      <html:radio property="man170Terminal" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.TERMINAL_KBN_PC) %>" styleId="terminal1" onclick="return tarminalChange(1)" />
      <label for="terminal1"><%= jp.groupsession.v2.cmn.GSConstCommon.TERMINAL_KBN_PC_TEXT %></label>
      <html:radio property="man170Terminal" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.TERMINAL_KBN_MOBILE) %>" styleId="terminal2" onclick="return tarminalChange(1)" />
      <label for="terminal2"><gsmsg:write key="main.man120.4" /></label>
      <span class="ml20 fw_b"><gsmsg:write key="cmn.careers" /></span>:
      <html:radio property="man170Car" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_PC_ALL) %>" styleId="car0" onclick="return buttonPush('research');" />
      <label for="car0"><gsmsg:write key="cmn.all" /></label>
      <html:radio property="man170Car" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_DOCOMO) %>" styleId="car2" onclick="return buttonPush('research');" />
      <label for="car2"><%= jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_DOCOMO_TEXT %></label>
      <html:radio property="man170Car" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_KDDI) %>" styleId="car3" onclick="return buttonPush('research');" />
      <label for="car3"><%= jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_KDDI_TEXT %></label>
      <html:radio property="man170Car" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_SOFTBANK) %>" styleId="car4" onclick="return buttonPush('research');" />
      <label for="car4"><%= jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_SOFTBANK_TEXT %></label>
    </span>
  </div>
  <div class="paging">
    <button type="button" class="webIconBtn" onclick="buttonPush('pageleft');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
      <i class="icon-paging_left"></i>
    </button>
    <logic:notEmpty name="man170Form" property="man170PageList">
      <html:select property="man170PageTop" onchange="return changePage('0');" styleClass="paging_combo">
        <html:optionsCollection name="man170Form" property="man170PageList" value="value" label="label" />
      </html:select>
    </logic:notEmpty>
    <logic:empty name="man170Form" property="man170PageList">
      <html:select property="man170PageTop" styleClass="paging_combo">
        <option value="1" class="text_i">1 / 1</option>
      </html:select>
    </logic:empty>
    <button type="button" class="webIconBtn" onclick="buttonPush('pageright');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
      <i class="icon-paging_right"></i>
    </button>
  </div>
  <table class="table-top w100">
    <tr>
      <logic:equal name="man170Form" property="man170SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_DATE) %>">
        <logic:equal name="man170Form" property="man170OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
          <th class="w20 cursor_p no_w table_header-evt js_table_header-evt">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_DATE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
              <gsmsg:write key="main.man050.5" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
            </a>
          </th>
        </logic:equal>
        <logic:equal name="man170Form" property="man170OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
          <th class="w20 cursor_p no_w table_header-evt js_table_header-evt">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_DATE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
              <gsmsg:write key="main.man050.5" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
            </a>
          </th>
        </logic:equal>
      </logic:equal>
      <logic:notEqual name="man170Form" property="man170SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_DATE) %>">
        <th class="w20 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_DATE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="main.man050.5" />
          </a>
        </th>
      </logic:notEqual>
      <logic:equal name="man170Form" property="man170SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_TERMINAL) %>">
        <logic:equal name="man170Form" property="man170OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
          <th class="w10 cursor_p no_w table_header-evt js_table_header-evt">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_TERMINAL) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
              <gsmsg:write key="main.man050.6" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
            </a>
          </th>
        </logic:equal>
        <logic:equal name="man170Form" property="man170OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
          <th class="w10 cursor_p no_w table_header-evt js_table_header-evt">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_TERMINAL) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
              <gsmsg:write key="main.man050.6" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
            </a>
          </th>
        </logic:equal>
      </logic:equal>
      <logic:notEqual name="man170Form" property="man170SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_TERMINAL) %>">
        <th class="w10 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_TERMINAL) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="main.man050.6" />
          </a>
        </th>
      </logic:notEqual>
      <logic:equal name="man170Form" property="man170SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_IP) %>">
        <logic:equal name="man170Form" property="man170OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
          <th class="w20 cursor_p no_w table_header-evt js_table_header-evt">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_IP) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
              <gsmsg:write key="cmn.ipaddress" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
            </a>
          </th>
        </logic:equal>
        <logic:equal name="man170Form" property="man170OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
          <th class="w20 cursor_p no_w table_header-evt js_table_header-evt">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_IP) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
              <gsmsg:write key="cmn.ipaddress" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
            </a>
          </th>
        </logic:equal>
      </logic:equal>
      <logic:notEqual name="man170Form" property="man170SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_IP) %>">
        <th class="w20 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_IP) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="cmn.ipaddress" />
          </a>
        </th>
      </logic:notEqual>
      <logic:equal name="man170Form" property="man170SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_CAR) %>">
        <logic:equal name="man170Form" property="man170OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
          <th class="w25 cursor_p no_w table_header-evt js_table_header-evt">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_CAR) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
              <gsmsg:write key="cmn.careers" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
            </a>
          </th>
        </logic:equal>
        <logic:equal name="man170Form" property="man170OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
          <th class="w25 cursor_p no_w table_header-evt js_table_header-evt">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_CAR) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
              <gsmsg:write key="cmn.careers" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
            </a>
          </th>
        </logic:equal>
      </logic:equal>
      <logic:notEqual name="man170Form" property="man170SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_CAR) %>">
        <th class="w25 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_CAR) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="cmn.careers" />
          </a>
        </th>
      </logic:notEqual>
      <logic:equal name="man170Form" property="man170SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_UID) %>">
        <logic:equal name="man170Form" property="man170OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
          <th class="w25 cursor_p no_w table_header-evt js_table_header-evt">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_UID) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
              <gsmsg:write key="cmn.identification.number" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
            </a>
          </th>
        </logic:equal>
        <logic:equal name="man170Form" property="man170OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
          <th class="w25 cursor_p no_w table_header-evt js_table_header-evt">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_UID) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
              <gsmsg:write key="cmn.identification.number" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
            </a>
          </th>
        </logic:equal>
      </logic:equal>
      <logic:notEqual name="man170Form" property="man170SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_UID) %>">
        <th class="w25 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_UID) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="cmn.identification.number" />
          </a>
        </th>
      </logic:notEqual>
    </tr>
    <logic:notEmpty name="man170Form" property="man170LoginHistoryList">
      <logic:iterate id="history" name="man170Form" property="man170LoginHistoryList" scope="request" indexId="idx">
      <tr>
        <td class="txt_c">
          <bean:write name="history" property="loginTime" />
        </td>
        <td class="txt_l">
          <bean:write name="history" property="terminalName" />
        </td>
        <td class="txt_l">
          <bean:write name="history" property="clhIp" />
        </td>
        <td class="txt_l">
          <bean:write name="history" property="carName" />
        </td>
        <td class="txt_l">
          <bean:write name="history" property="clhUid" />
        </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <div class="paging">
    <button type="button" class="webIconBtn" onclick="buttonPush('pageleft');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
      <i class="icon-paging_left"></i>
    </button>
    <logic:notEmpty name="man170Form" property="man170PageList">
      <html:select property="man170PageBottom" onchange="return changePage('1');" styleClass="paging_combo">
        <html:optionsCollection name="man170Form" property="man170PageList" value="value" label="label" />
      </html:select>
    </logic:notEmpty>
    <logic:empty name="man170Form" property="man170PageList">
      <html:select property="man170PageTop" styleClass="paging_combo">
        <option value="1" class="text_i">1 / 1</option>
      </html:select>
    </logic:empty>
    <button type="button" class="webIconBtn" onclick="buttonPush('pageright');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
      <i class="icon-paging_right"></i>
    </button>
  </div>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>