<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%@ page import="jp.groupsession.v2.cmn.GSConstWebmail" %>
<%@ page import="jp.groupsession.v2.wml.wml270.Wml270DestListModel" %>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml270.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION<gsmsg:write key="wml.wml020.14" /></title>
</head>

<body>

<html:form action="/webmail/wml270">
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<input type="hidden" name="wmlCmdMode" value="0">
<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlAccountMode" />
<html:hidden property="wmlAccountSid" />

<input type="hidden" name="wmlEditDestList" value="">
<html:hidden property="wml270svKeyword" />
<html:hidden property="wml270sortKey" />
<html:hidden property="wml270order" />
<html:hidden property="wml270searchFlg" />

<input type="hidden" name="wmlMailTemplateKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.MAILTEMPLATE_NORMAL) %>">

<bean:define id="acctMode" name="wml270Form" property="wmlAccountMode" type="java.lang.Integer" />
<% int accountMode = acctMode.intValue(); %>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w70 mrl_auto">
  <ul>
  <% if (accountMode == 1) { %>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
  <% } else { %>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
  <% } %>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.wml020.14" />
    </li>

    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('addDestList', 0);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('destListDelete');">
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

<div class="wrapper w70 mrl_auto">

<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>

  <table class="table-top">
    <tr>
      <td class="txt_l w100">
        <span class="verAlignMid">
          <html:text name="wml270Form" property="wml270keyword" maxlength="50" styleClass="wp180" />
          <button type="button" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');" class="baseBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
        </span>
      </td>
    </tr>
  </table>

<logic:notEmpty name="wml270Form" property="destListList">
  <div class="mt20 txt_b">

    <span class="flo_r">
    <logic:notEmpty name="wml270Form" property="pageCombo">
      <div class="paging">
        <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  property="wml270pageTop" onchange="changePage(0);">
          <html:optionsCollection name="wml270Form" property="pageCombo" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:notEmpty>
    </span>
  </div>

  <bean:define id="orderValue" name="wml270Form" property="wml270order" type="java.lang.Integer" />
  <%  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
      String destList = gsMsg.getMessage(request, "wml.263");
      String user = gsMsg.getMessage(request, "cmn.employer");
      String up = "<span class=\"classic-display\">"
              + gsMsg.getMessage(request, "tcd.tcd040.22")
              + "</span><span class=\"original-display txt_m\"><i class=\"icon-sort_up\"></i></span>";
      String down = "<span class=\"classic-display\">"
              + gsMsg.getMessage(request, "tcd.tcd040.23")
              + "</span><span class=\"original-display txt_m\"><i class=\"icon-sort_down\"></i></span>";
  %>
  <% String orderRight = up; %>
  <% String nextOrder = String.valueOf(GSConstWebmail.ORDER_DESC); %>
  <% if (orderValue.intValue() == GSConstWebmail.ORDER_DESC) { %>
  <%    orderRight = down; %>
  <%    nextOrder = String.valueOf(GSConstWebmail.ORDER_ASC); %>
  <% } %>

  <bean:define id="sortValue" name="wml270Form" property="wml270sortKey" type="java.lang.Integer" />
  <% String[] orderList = {String.valueOf(GSConstWebmail.ORDER_ASC)}; %>
  <% String[] titleList = {destList}; %>
  <% int titleIndex = 0; %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_USER) { titleIndex = 1; } %>
  <% titleList[titleIndex] = titleList[titleIndex] + orderRight; %>
  <% orderList[titleIndex] = nextOrder; %>
  <table class="table-top w100 mb0">
    <tr>
      <th class="w0 txt_c js_tableTopCheck js_tableTopCheck-header cursor_p">
        <input type="checkbox" name="allChk" onClick="changeChk();">
      </th>
      <th class="w50 no_w cursor_p">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_ACCOUNTNAME) %>, <%= orderList[0] %>);"><%= titleList[0] %>
      </th>
      <th class="w50 no_w">
        <gsmsg:write key="cmn.memo" />
      </th>
    </tr>
  <logic:iterate id="destData" name="wml270Form" property="destListList" indexId="idx" type="Wml270DestListModel">
    <bean:define id="backclass" value="td_line_color" />
    <bean:define id="backclass_no_edit" value="td_line_no_edit_color" />
    <bean:define id="backpat" value="<%= String.valueOf((idx.intValue() % 2) + 1) %>" />
    <bean:define id="back" value="<%= String.valueOf(backclass) + String.valueOf(backpat) %>" />
    <bean:define id="back_no_edit" value="<%= String.valueOf(backclass_no_edit) + String.valueOf(backpat) %>" />
    <tr class="<%= String.valueOf(back) %> js_listHover cursor_p" id="<bean:write name="destData" property="destListSid" />" data-sid="<bean:write name="destData" property="destListSid" />">
      <td class="js_tableTopCheck txt_c">
      <logic:equal name="destData" property="editFlg" value="true">
      <% if (Integer.valueOf(backpat) == 1) { %>
        <html:multibox name="wml270Form" property="wml270selectDestList">
          <bean:write name="destData" property="destListSid" />
        </html:multibox>
      <% } else { %>
        <html:multibox name="wml270Form" property="wml270selectDestList">
          <bean:write name="destData" property="destListSid" />
        </html:multibox>
      <% } %>
      </logic:equal>
      </td>
      <td class="js_listClick">
        <span class="cl_linkDef"><bean:write name="destData" property="destListName" /></span>
      </td>
      <td class="js_listClick">
        <bean:write name="destData" property="viewBiko" filter="false" />
      </td>

    </tr>
  </logic:iterate>

  </table>

  <logic:notEmpty name="wml270Form" property="pageCombo">
  <div class="txt_r paging">
    <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select styleClass="paging_combo"  property="wml270pageBottom" onchange="changePage(1);">
      <html:optionsCollection name="wml270Form" property="pageCombo" />
    </html:select>
    <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
      <i class="icon-paging_right"></i>
    </button>
  </div>
  </logic:notEmpty>

</logic:notEmpty>

</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>