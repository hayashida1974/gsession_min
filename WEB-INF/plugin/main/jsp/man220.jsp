<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>

<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">

<title>GROUPSESSION <gsmsg:write key="main.grp.usr.sort.setting" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/main/man220">

<input type="hidden" name="CMD" value="">
<html:hidden property="man220initFlg" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.grp.usr.sort.setting" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKtool');">
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
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
<table class="table-left">
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="main.man220.2" />
      </th>
      <td class="w75">
      <span class="cl_fontWarn"><gsmsg:write key="main.man220.3" /></span>
      <div class="mb5">
        <span class="verAlignMid">
          <gsmsg:write key="cmn.first.key" />：
          <html:select property="man220UserSortKey1" styleClass="wp150">
            <html:optionsCollection name="man220Form" property="userSortKeyLabel" value="value" label="label" />
          </html:select>
          <html:radio name="man220Form" property="man220UserSortOrder1" styleClass="ml10" styleId="userOrder10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="userOrder10"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="man220Form" property="man220UserSortOrder1" styleClass="ml10" styleId="userOrder11" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="userOrder11"><gsmsg:write key="cmn.order.desc" /></label>
        </span>
      </div>
      <div>
        <span class="verAlignMid">
          <gsmsg:write key="cmn.second.key" />：
          <html:select property="man220UserSortKey2" styleClass="wp150">
            <option value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.USERCMB_SKEY_NOSET) %>">　</option>
            <html:optionsCollection name="man220Form" property="userSortKeyLabel" value="value" label="label" />
          </html:select>
          <html:radio name="man220Form" property="man220UserSortOrder2" styleClass="ml10" styleId="userOrder20" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="userOrder20"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="man220Form" property="man220UserSortOrder2" styleClass="ml10" styleId="userOrder21" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="userOrder21"><gsmsg:write key="cmn.order.desc" /></label>
        </span>
      </div>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="main.man220.5" />
      </th>
      <td>
      <span class="cl_fontWarn"><gsmsg:write key="main.man220.6" /></span>
      <div class="mb5">
        <span class="verAlignMid">
          <gsmsg:write key="cmn.first.key" />：
          <html:select property="man220GroupSortKey1" styleClass="wp150">
            <html:optionsCollection name="man220Form" property="groupSortKeyLabel" value="value" label="label" />
          </html:select>
          <html:radio name="man220Form" property="man220GroupSortOrder1" styleClass="ml10" styleId="groupOrder10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="groupOrder10"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="man220Form" property="man220GroupSortOrder1" styleClass="ml10" styleId="groupOrder11" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="groupOrder11"><gsmsg:write key="cmn.order.desc" /></label>
        </span>
      </div>
      <div>
        <span class="verAlignMid">
          <gsmsg:write key="cmn.second.key" />：
          <html:select property="man220GroupSortKey2" styleClass="wp150">
            <option value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.GROUPCMB_SKEY_NOSET) %>">　</option>
            <html:optionsCollection name="man220Form" property="groupSortKeyLabel" value="value" label="label" />
          </html:select>
          <html:radio name="man220Form" property="man220GroupSortOrder2" styleClass="ml10" styleId="groupOrder20" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>" /><label for="groupOrder20"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio name="man220Form" property="man220GroupSortOrder2" styleClass="ml10" styleId="groupOrder21" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>" /><label for="groupOrder21"><gsmsg:write key="cmn.order.desc" /></label>
        </span>
      </div>
      <div class="mt10">
        <span class="verAlignMid">
          <html:checkbox styleId="groupSortKbn" name="man220Form" property="man220GroupSortKbn" value="<%= String.valueOf(jp.groupsession.v2.man.man220.Man220Form.GRPSORTKBN_SELECT) %>" /><label for="groupSortKbn"><gsmsg:write key="man.reorder.hierarchy.group" /></label>
        </span>
      </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKtool');">
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