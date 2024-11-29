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
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
  <link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
  <link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <gsjsmsg:js filename="gsjsmsg.js"/>
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>" language="JavaScript"></script>
  <script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml070.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml070.03" /></title>
</head>

<body class="body_03" onload="setSearchDateView(<bean:write name="wml070Form" property="wml070SendDateCondition" />);" onunload="windowClose();">

<html:form action="/webmail/wml070">

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden property="wml070searchFlg" />
<html:hidden property="wml070sortKey" />
<html:hidden property="wml070order" />
<html:hidden property="wmlViewAccount" />

<html:hidden property="wml070svTitle" />
<html:hidden property="wml070svAddress" />
<html:hidden property="wml070svAddressFrom" />
<html:hidden property="wml070svAddressTo" />
<html:hidden property="wml070svSendDateYear" />
<html:hidden property="wml070svSendDateMonth" />
<html:hidden property="wml070svSendDateDay" />
<html:hidden property="wml070svSendDateYearTo" />
<html:hidden property="wml070svSendDateMonthTo" />
<html:hidden property="wml070svSendDateDayTo" />
<html:hidden property="wml070svSendDateCondition" />
<html:hidden property="wml070svType" />
<input type="hidden" name="yearRangeMinFr" value="3" />
<input type="hidden" name="yearRangeMaxFr" value="0" />
<input type="hidden" name="yearRangeMinTo" value="3" />
<input type="hidden" name="yearRangeMaxTo" value="0" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w85 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.wml070.03" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('admTool');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w85 mrl_auto">

  <logic:messagesPresent message="false">
    <span class="mb10"><html:errors/></span>
  </logic:messagesPresent>

<logic:equal name="wml070Form" property="wmlEntryMailLogFlg" value="false">
  <div class="txt_l cl_fontWarn fw_b w100"><gsmsg:write key="wml.114" /></div>
</logic:equal>

  <table class="table-left mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.subject" />
      </th>
      <td class="w75">
        <html:text name="wml070Form" property="wml070Title" maxlength="100" styleClass="wp350" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.mailaddress" />
      </th>
      <td class="w75">
        <html:text name="wml070Form" property="wml070Address" maxlength="256" styleClass="wp350" /><br>
        <span class="verAlignMid">
          <html:checkbox name="wml070Form" property="wml070AddressFrom" styleId="searchMail0" value="1" /><label for="searchMail0"><gsmsg:write key="cmn.sendfrom" /></label>
          <html:checkbox name="wml070Form" property="wml070AddressTo" styleId="searchMail1" styleClass="ml10" value="1" /><label for="searchMail1"><gsmsg:write key="cmn.from" />(To Cc Bcc)</label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.date2" />
      </th>
      <td class="w75">
        <table class="table-noBorder">
          <tr>
          <td id="wml070FromDate">From:</td>
          <td class="no_w">
            <span class="verAlignMid">
              <html:text name="wml070Form" property="wml070sendDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatefr" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            </span>
          </td>

          <td class="no_w">
            <span class="verAlignMid">
              <button type="button" class="webIconBtn" onClick="moveDay($('#selDatefr')[0], 1);">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.changes.previous.day" />">
                <i class="icon-paging_left"></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.thismonth3" />" onClick="moveDay($('#selDatefr')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </button>
              <span>
                <a href="#" class="fw_b todayBtn original-display" onClick="moveDay($('#selDatefr')[0], 2);">
                <gsmsg:write key="cmn.today" />
                </a>
              </span>
              <button type="button" class="webIconBtn" onClick="moveDay($('#selDatefr')[0], 3);">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.change.nextday" />">
                <i class="icon-paging_right"></i>
              </button>
            </span>
          </td>
          <td class="txt_m pl20 no_w">
            <span class="verAlignMid">
              <html:radio name="wml070Form" property="wml070SendDateCondition" styleId="sdkbn1" value="1" onclick="setSearchDateView(1);" /><label for="sdkbn1"><gsmsg:write key="wml.wml070.09" /></label>
              <html:radio name="wml070Form" property="wml070SendDateCondition" styleId="sdkbn2" styleClass="ml10" value="2" onclick="setSearchDateView(2);" /><label for="sdkbn2"><gsmsg:write key="wml.wml070.10" /></label>
              <html:radio name="wml070Form" property="wml070SendDateCondition" styleId="sdkbn3" styleClass="ml10" value="3" onclick="setSearchDateView(3);" /><label for="sdkbn3"><gsmsg:write key="wml.wml070.11" /></label>
            </span>
            <br>
            <span class="verAlignMid">
              <html:radio name="wml070Form" property="wml070SendDateCondition" styleId="sdkbn4" value="4" onclick="setSearchDateView(4);" /><label for="sdkbn4"><gsmsg:write key="wml.05" /></label>
            </span>
          </td>
          </tr>

          <tr id="wml070ToDate">
          <td class="txt_r">To:</td>
          <td class="no_w">
            <span class="verAlignMid">
              <html:text name="wml070Form" property="wml070sendDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateto" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
            </span>
          </td>

          <td class="no_w">
            <span class="verAlignMid">
              <button type="button" class="webIconBtn" onClick="moveDay($('#selDateto')[0], 1);">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.changes.previous.day" />">
                <i class="icon-paging_left"></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.thismonth3" />" onClick="moveDay($('#selDateto')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </button>
              <span>
                <a href="#" class="fw_b todayBtn original-display" onClick="moveDay($('#selDateto')[0], 2);">
                <gsmsg:write key="cmn.today" />
                </a>
              </span>
              <button type="button" class="webIconBtn" onClick="moveDay($('#selDateto')[0], 3);">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.change.nextday" />">
                <i class="icon-paging_right"></i>
              </button>
            </span>
          </td>
          <td>&nbsp;</td>
          </tr>
        </table>

      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.type" />
      </th>
      <td class="w75">
        <span class="verAlignMid">
          <html:radio name="wml070Form" property="wml070Type" styleId="sbkbn1" value="2" /><label for="sbkbn1"><gsmsg:write key="cmn.without.specifying" /></label>
          <html:radio name="wml070Form" property="wml070Type" styleId="sbkbn2" styleClass="ml10" value="1" /><label for="sbkbn2"><gsmsg:write key="cmn.sent" /></label>
          <html:radio name="wml070Form" property="wml070Type" styleId="sbkbn3" styleClass="ml10" value="0" /><label for="sbkbn3"><gsmsg:write key="cmn.receive" /></label>
        </span>
      </td>
    </tr>
  </table>

  <div class="w100 mt20 mb20">
    <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('searchResult');">
      <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>

  <div class="w100 txt_l txt_b">

  <logic:equal name="wml070Form" property="wml070pageDspFlg" value="true">
    <div class="paging">
      <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select styleClass="paging_combo"  property="wml070pageTop" onchange="changePage(1);">
        <html:optionsCollection name="wml070Form" property="pageList" value="value" label="label" />
      </html:select>
      <button type="button" class="webIconBtn" onClick="buttonPush('next');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:equal>
  </div>

  <bean:define id="orderValue" name="wml070Form" property="wml070order" type="java.lang.Integer" />
  <%  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
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

  <bean:define id="sortValue" name="wml070Form" property="wml070sortKey" type="java.lang.Integer" />
  <% String[] orderList = {String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC), String.valueOf(GSConstWebmail.ORDER_ASC)}; %>
  <% String[] titleList = {subject, sender, from + "(To Cc Bcc)", date}; %>
  <% int titleIndex = 0; %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_TITLE) { titleIndex = 0; } %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_FROM) { titleIndex = 1; } %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_TO) { titleIndex = 2; } %>
  <% if (sortValue.intValue() == GSConstWebmail.SKEY_DATE) { titleIndex = 3; } %>
  <% titleList[titleIndex] = titleList[titleIndex] + orderRight; %>
  <% orderList[titleIndex] = nextOrder; %>

  <table class="table-top mt0 mb0 w100">
    <tr>
      <th class="w1 no_w">&nbsp;</th>
      <th class="w35 txt_c cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_TITLE) %>, <%= orderList[0] %>);"><span class="text_tlw"><%= titleList[0] %></span></a>
      </th>
      <th class="w20 txt_c cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_FROM) %>, <%= orderList[1] %>);"><span class="text_tlw"><%= titleList[1] %></span></a>
      </th>
      <th class="w20 txt_c cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_TO) %>, <%= orderList[2] %>);"><span class="text_tlw"><%= titleList[2] %></span></a>
      </th>
      <th class="w20 txt_c cursor_p no_w">
        <a href="#" onClick="return sort(<%= String.valueOf(GSConstWebmail.SKEY_DATE) %>, <%= orderList[3] %>);"><span class="text_tlw"><%= titleList[3] %></span></a>
      </th>
    </tr>

  <logic:notEmpty name="wml070Form" property="wml070SendResvList">
    <logic:iterate id="sendToData" name="wml070Form" property="wml070SendResvList">
    <tr class="js_listHover cursor_p" data-sid="<bean:write name="sendToData" property="wmdMailnum" />">
      <td class="txt_c no_w js_listClick">
      <logic:equal name="sendToData" property="wlgTempFlg" value="1">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file.gif" alt="<gsmsg:write key="cmn.file" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.file" />">
      </logic:equal>
      </td>
      <td class="txt_l js_listClick">
        <span class="cl_linkDef"><bean:write name="sendToData" property="wlgTitle" /></span>
      </td>
      <td class="txt_l js_listClick"><bean:write name="sendToData" property="wlgFrom" /></td>
      <td class="txt_l js_listClick"><bean:write name="sendToData" property="wlsAddress" /></td>
      <td class="txt_c js_listClick">
        <bean:write name="sendToData" property="wlgDate" /><br>
        <bean:write name="sendToData" property="wlgTime" />
      </td>
    </tr>
    </logic:iterate>
  </logic:notEmpty>

  </table>

<logic:equal name="wml070Form" property="wml070pageDspFlg" value="true">
  <div class="paging">
    <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select styleClass="paging_combo"  property="wml070pageBottom" onchange="changePage(0);">
      <html:optionsCollection name="wml070Form" property="pageList" value="value" label="label" />
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