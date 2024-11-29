<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%@ page import="jp.groupsession.v2.cmn.GSConstWebmail" %>
<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <gsjsmsg:js filename="gsjsmsg.js"/>
  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>" language="JavaScript"></script>
  <script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml260.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml260kn.01" /></title>
</head>

<body onload="setSearchDateView(<bean:write name="wml260knForm" property="wml260SendDateCondition" />);" onunload="windowClose();">

<html:form action="/webmail/wml260kn">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden property="wml260searchFlg" />
<html:hidden property="wml260sortKey" />
<html:hidden property="wml260order" />
<html:hidden property="wmlViewAccount" />

<html:hidden property="wml260AccountName" />
<html:hidden property="wml260Title" />
<html:hidden property="wml260Address" />
<html:hidden property="wml260AddressFrom" />
<html:hidden property="wml260AddressTo" />
<html:hidden property="wml260SendDateYear" />
<html:hidden property="wml260SendDateMonth" />
<html:hidden property="wml260SendDateDay" />
<html:hidden property="wml260SendDateYearTo" />
<html:hidden property="wml260SendDateMonthTo" />
<html:hidden property="wml260SendDateDayTo" />
<html:hidden property="wml260SendDateCondition" />

<html:hidden property="wml260svAccountName" />
<html:hidden property="wml260svTitle" />
<html:hidden property="wml260svAddress" />
<html:hidden property="wml260svAddressFrom" />
<html:hidden property="wml260svAddressTo" />
<html:hidden property="wml260svSendDateYear" />
<html:hidden property="wml260svSendDateMonth" />
<html:hidden property="wml260svSendDateDay" />
<html:hidden property="wml260svSendDateYearTo" />
<html:hidden property="wml260svSendDateMonthTo" />
<html:hidden property="wml260svSendDateDayTo" />
<html:hidden property="wml260svSendDateCondition" />

<logic:iterate id="selectMailNum" name="wml260knForm" property="wml260selectMailNum">
  <input type="hidden" name="wml260selectMailNum" value="<bean:write name="selectMailNum" />">
</logic:iterate>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.wml260kn.01" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="wml.js.84" />" onClick="buttonPush('wml260knSendCancel');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="wml.js.84" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="wml.js.84" />">
          <gsmsg:write key="wml.js.84" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backList');">
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

  <div class="w100 txt_l cl_fontWarn fw_b">
    <gsmsg:write key="wml.wml260kn.02" />
  </div>

  <div class="mt20">
    <span class="flo_l">
    <logic:equal name="wml260knForm" property="wml260pageDspFlg" value="true">
      <br>
    </logic:equal>
      <gsmsg:write key="wml.wml070.08" />
    </span>

    <logic:equal name="wml260knForm" property="wml260pageDspFlg" value="true">
    <span class="flo_r">
      <div class="paging">
        <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  property="wml260pageTop" onchange="changePage(1);">
          <html:optionsCollection name="wml260knForm" property="pageList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('next');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </span>
    </logic:equal>
  </div>


  <bean:define id="orderValue" name="wml260knForm" property="wml260order" type="java.lang.Integer" />
  <%  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
      String accountName = gsMsg.getMessage(request, "wml.96");
      String sender = gsMsg.getMessage(request, "cmn.sendfrom");
      String from = gsMsg.getMessage(request, "cmn.from");
      String subject = gsMsg.getMessage(request, "cmn.subject");
      String date = gsMsg.getMessage(request, "cmn.date");
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

  <bean:define id="sortValue" name="wml260knForm" property="wml260sortKey" type="java.lang.Integer" />
  <% String[] orderList = {String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC)}; %>
  <% String[] titleList = {accountName, subject, sender, from + "(To Cc Bcc)", date}; %>
  <% int titleIndex = 0; %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_TITLE) { titleIndex = 1; } %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_FROM) { titleIndex = 2; } %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_TO) { titleIndex = 3; } %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_DATE) { titleIndex = 4; } %>
  <% if (sortValue.intValue() == 4) { titleIndex = 0; } %>
  <% titleList[titleIndex] = titleList[titleIndex] + orderRight; %>
  <% orderList[titleIndex] = nextOrder; %>

  <table class="table-top w100">
    <tr>
      <th>&nbsp;</th>
      <th class="w20 cursor_p no_w">
        <a href="#" onClick="return sort(<%= "4" %>, <%= orderList[0] %>);"><%= titleList[0] %></a>
      </th>
      <th class="w35 cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_TITLE) %>, <%= orderList[1] %>);"><%= titleList[1] %></a>
      </th>
      <th class="w20 cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_FROM) %>, <%= orderList[2] %>);"><%= titleList[2] %></a>
      </th>
      <th class="w20 cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_TO) %>, <%= orderList[3] %>);"><%= titleList[3] %></a>
      </th>
      <th class="w10 cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_DATE) %>, <%= orderList[4] %>);"><%= titleList[4] %></a>
      </th>
    </tr>

<logic:notEmpty name="wml260knForm" property="wml260SendResvList">
  <logic:iterate id="sendToData" name="wml260knForm" property="wml260SendResvList">
    <tr class="js_listHover cursor_p" data-sid="<bean:write name="sendToData" property="wmdMailnum" />">
      <td class="txt_c js_listClick">
      <logic:equal name="sendToData" property="wlgTempFlg" value="1" >
        <img class="btn_classicImg-display" src="../webmail/images/classic/attach.gif" alt="<gsmsg:write key="cmn.attach.file" />">
        <img class="btn_originalImg-display" src="../webmail/images/original/icon_attach_12.png" alt="<gsmsg:write key="cmn.attach.file" />">
      </logic:equal>
      </td>
      <td class="txt_l js_listClick">
        <bean:write name="sendToData" property="accountName" />
      </td>
      <td class="txt_l js_listClick">
        <span class="cl_linkDef"><bean:write name="sendToData" property="wlgTitle" /></span>
      </td>
      <td class="txt_l js_listClick">
        <bean:write name="sendToData" property="wlgFrom" />
      </td>
      <td class="txt_l js_listClick">
        <bean:write name="sendToData" property="wlsAddress" />
      </td>
      <td class="txt_c js_listClick">
        <bean:write name="sendToData" property="wlgDate" />
        <br><bean:write name="sendToData" property="wlgTime" />
      </td>
    </tr>
  </logic:iterate>
</logic:notEmpty>

  </table>

  <logic:equal name="wml260knForm" property="wml260pageDspFlg" value="true">
  <div class="paging">
    <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select styleClass="paging_combo"  property="wml260pageBottom" onchange="changePage(2);">
      <html:optionsCollection name="wml260knForm" property="pageList" value="value" label="label" />
    </html:select>
    <button type="button" class="webIconBtn" onClick="buttonPush('next');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
      <i class="icon-paging_right"></i>
    </button>
  </div>
  </logic:equal>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>