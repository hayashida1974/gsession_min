<%@page import="jp.groupsession.v2.cmn.GSConstWebmail"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.wml.wml140.Wml140Form" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

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
  <script src="../webmail/js/wml070.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml140.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml140kn.06" /></title>

</head>

<body class="body_03">

<html:form action="/webmail/wml140kn">

<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="wmlFilterCmdMode" />
<html:hidden property="wmlEditFilterId" />
<html:hidden property="wml130accountSid" />
<html:hidden property="wmlViewAccount" />
<html:hidden property="dspCount" />
<html:hidden property="wml130SortRadio" />

<html:hidden property="wml140initFlg" />
<html:hidden property="wml140FilterName" />
<html:hidden property="wml140filterType" />
<html:hidden property="wml140condition1" />
<html:hidden property="wml140conditionType1" />
<html:hidden property="wml140conditionExs1" />
<html:hidden property="wml140conditionText1" />
<html:hidden property="wml140condition2" />
<html:hidden property="wml140conditionType2" />
<html:hidden property="wml140conditionExs2" />
<html:hidden property="wml140conditionText2" />
<html:hidden property="wml140condition3" />
<html:hidden property="wml140conditionType3" />
<html:hidden property="wml140conditionExs3" />
<html:hidden property="wml140conditionText3" />
<html:hidden property="wml140condition4" />
<html:hidden property="wml140conditionType4" />
<html:hidden property="wml140conditionExs4" />
<html:hidden property="wml140conditionText4" />
<html:hidden property="wml140condition5" />
<html:hidden property="wml140conditionType5" />
<html:hidden property="wml140conditionExs5" />
<html:hidden property="wml140conditionText5" />

<html:hidden property="wml140actionLabel" />
<html:hidden property="wml140actionLabelValue" />
<html:hidden property="wml140actionRead" />
<html:hidden property="wml140actionMove" />
<html:hidden property="wml140actionFolderValue" />
<html:hidden property="wml140actionSend" />

<logic:notEmpty name="wml140knForm" property="wml140actionSendValue">
<logic:iterate id="fwAddress" name="wml140knForm" property="wml140actionSendValue">
  <input type="hidden" name="wml140actionSendValue" value="<bean:write name="fwAddress" />">
</logic:iterate>
</logic:notEmpty>

<html:hidden property="wml140tempFile" />
<html:hidden property="wml140doFilter" />

<html:hidden property="wml140viewMailList" />
<html:hidden property="wml140mailListSortKey" />
<html:hidden property="wml140mailListOrder" />
<html:hidden property="wml140svFilterType" />
<html:hidden property="wml140svCondition1" />
<html:hidden property="wml140svConditionType1" />
<html:hidden property="wml140svConditionExs1" />
<html:hidden property="wml140svConditionText1" />
<html:hidden property="wml140svCondition2" />
<html:hidden property="wml140svConditionType2" />
<html:hidden property="wml140svConditionExs2" />
<html:hidden property="wml140svConditionText2" />
<html:hidden property="wml140svCondition3" />
<html:hidden property="wml140svConditionType3" />
<html:hidden property="wml140svConditionExs3" />
<html:hidden property="wml140svConditionText3" />
<html:hidden property="wml140svCondition4" />
<html:hidden property="wml140svConditionType4" />
<html:hidden property="wml140svConditionExs4" />
<html:hidden property="wml140svConditionText4" />
<html:hidden property="wml140svCondition5" />
<html:hidden property="wml140svConditionType5" />
<html:hidden property="wml140svConditionExs5" />
<html:hidden property="wml140svConditionText5" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.wml140kn.06" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backInput');">
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

  <table class="table-left mt0">
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="wml.102" />
      </th>
      <td class="w75">
        <bean:write name="wml140knForm" property="wml130account" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="wml.84" />
      </th>
      <td class="w75">
        <bean:write name="wml140knForm" property="wml140FilterName" />
      </td>
    </tr>
  </table>

  <div class="w100 mt20 mb10 txt_l">
    <gsmsg:write key="wml.40" />&nbsp;&nbsp;<bean:write name="wml140knForm" property="wml140filterTypeView" />
  </div>

  <table class="table-left">
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="wml.wml140kn.05" />
      </th>
      <td class="w75 no_w">
        <bean:write name="wml140knForm" property="wml140conditionType1View" />
        <span class="ml10"><bean:write name="wml140knForm" property="wml140conditionExs1View" /></span>
        <span class="ml20"><bean:write name="wml140knForm" property="wml140conditionText1" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="wml.wml140kn.04" />
      </th>
      <td class="w75 no_w">
        <bean:write name="wml140knForm" property="wml140conditionType2View" />
        <span class="ml10"><bean:write name="wml140knForm" property="wml140conditionExs2View" /></span>
        <span class="ml20"><bean:write name="wml140knForm" property="wml140conditionText2" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="wml.wml140kn.03" />
      </th>
      <td class="w75 no_w">
        <bean:write name="wml140knForm" property="wml140conditionType3View" />
        <span class="ml10"><bean:write name="wml140knForm" property="wml140conditionExs3View" /></span>
        <span class="ml20"><bean:write name="wml140knForm" property="wml140conditionText3" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="wml.wml140kn.02" />
      </th>
      <td class="w75 no_w">
        <bean:write name="wml140knForm" property="wml140conditionType4View" />
        <span class="ml10"><bean:write name="wml140knForm" property="wml140conditionExs4View" /></span>
        <span class="ml20"><bean:write name="wml140knForm" property="wml140conditionText4" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="wml.wml140kn.01" />
      </th>
      <td class="w75 no_w">
        <bean:write name="wml140knForm" property="wml140conditionType5View" />
        <span class="ml10"><bean:write name="wml140knForm" property="wml140conditionExs5View" /></span>
        <span class="ml20"><bean:write name="wml140knForm" property="wml140conditionText5" /></span>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.attach.file" />
      </th>
      <td class="w75 no_w">
      <logic:equal name="wml140knForm" property="wml140tempFile" value="1">
        <gsmsg:write key="wml.14" />
      </logic:equal>
      </td>
    </tr>
  </table>

  <div class="w100 mt20 txt_l">
    <gsmsg:write key="wml.56" />
  </div>

  <% int runCount = 0; boolean runLabel = false, runRead = false, runMove = false, runSend = false; %>
  <logic:equal name="wml140knForm" property="wml140actionLabel" value="1">
    <% runCount++; runLabel = true;%>
  </logic:equal>
  <logic:equal name="wml140knForm" property="wml140actionRead" value="1">
    <% runCount++; runRead = true; %>
  </logic:equal>
  <logic:equal name="wml140knForm" property="wml140actionMove" value="1">
    <% runCount++; runMove = true; %>
  </logic:equal>
  <logic:equal name="wml140knForm" property="wml140actionSend" value="1">
    <% runCount++; runSend = true; %>
  </logic:equal>

  <table class="table-left mt0">
    <tr>
      <th class="w25 no_w" rowspan="<%= String.valueOf(runCount) %>">
        <gsmsg:write key="cmn.run" />
      </th>
    <logic:equal name="wml140knForm" property="wml140actionLabel" value="1">
      <td class="w100">
        <span class="mr10"><gsmsg:write key="wml.75" />:</span><bean:write name="wml140knForm" property="wml140LabelView" />
      </td>
      </tr>
    </logic:equal>

    <logic:equal name="wml140knForm" property="wml140actionRead" value="1">
    <% if (runLabel) { %>
    <tr>
    <% } %>
      <td class="w75">
        <gsmsg:write key="cmn.mark.read" />
      </td>
    </tr>
    </logic:equal>

    <logic:equal name="wml140knForm" property="wml140actionMove" value="1">
    <% if (runLabel || runRead) { %>
    <tr>
    <% } %>
      <td class="w75">
        <span class="mr10"><gsmsg:write key="wml.92" />:</span>
      <logic:equal name="wml140knForm" property="wml140actionFolderValue" value="<%=String.valueOf(GSConstWebmail.FILTER_MOVE_DUST) %>">
        <gsmsg:write key="cmn.trash" />
      </logic:equal>
      <logic:equal name="wml140knForm" property="wml140actionFolderValue" value="<%=String.valueOf(GSConstWebmail.FILTER_MOVE_STRAGE) %>">
        <gsmsg:write key="cmn.strage" />
      </logic:equal>
      </td>
    </tr>
    </logic:equal>

    <logic:equal name="wml140knForm" property="wml140actionSend" value="1">
    <% if (runLabel || runRead || runMove) { %>
    <tr>
    <% }  %>
      <td class="w75">
        <table class="table-noBorder">
          <td>
            <gsmsg:write key="wml.57" />
          </td>
          <td>
        <logic:notEmpty name="wml140knForm" property="wml140actionSendValue">
          <logic:iterate id="fwAddress" name="wml140knForm" property="wml140actionSendValue" indexId="fwIdx">
            <div <% if (fwIdx.intValue() > 0) { %> class="mt5"<% } %>>&nbsp;&nbsp;<bean:write name="fwAddress" /></div>
          </logic:iterate>
        </logic:notEmpty>
          </td>
          </tr>
        </table>
      </td>
    </tr>
    </logic:equal>

  </table>

<logic:notEmpty name="wml140knForm" property="wml140mailList">

  <div class="w100 mt20">
    <span class="flo_l verAlignMid">
      <gsmsg:write key="wml.85" />&nbsp;<gsmsg:write key="wml.116" />
    </span>
  <logic:notEmpty name="wml140knForm" property="wml140mailListPageCombo">
    <span class="flo_r">
      <div class="paging">
        <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  property="wml140mailListPageTop" onchange="selectPage(0);">
          <html:optionsCollection name="wml140knForm" property="wml140mailListPageCombo" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </span>
  </logic:notEmpty>
  </div>

  <table class="table-top">
    <tr>
      <th width="2%">&nbsp;</th>

      <bean:define id="wml140SortKey" name="wml140knForm" property="wml140mailListSortKey" type="java.lang.Integer" />
      <bean:define id="wml140Order" name="wml140knForm" property="wml140mailListOrder" type="java.lang.Integer" />
      <% String[] widthList = new String[] {"w30", "w50", "w20"};
        jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
          String from = gsMsg.getMessage(request, "cmn.sendfrom");
          String subject = gsMsg.getMessage(request, "cmn.subject");
          String date = gsMsg.getMessage(request, "cmn.date");
          String down = "<span class=\"classic-display\">"
                  + gsMsg.getMessage(request, "tcd.tcd040.23")
                  + "</span><span class=\"original-display txt_m\"><i class=\"icon-sort_down\"></i></span>";
          String up = "<span class=\"classic-display\">"
                  + gsMsg.getMessage(request, "tcd.tcd040.22")
                  + "</span><span class=\"original-display txt_m\"><i class=\"icon-sort_up\"></i></span>";
      %>
      <% String[] titleList = new String[] {from, subject, date}; %>
      <% int[] sortKeyList = new int[] {Wml140Form.WML140_SKEY_FROM, Wml140Form.WML140_SKEY_SUBJECT, Wml140Form.WML140_SKEY_DATE}; %>
      <% for (int i = 0; i < widthList.length; i++) { %>
      <%   String title = titleList[i]; %>
      <%   int order = Wml140Form.WML140_ORDER_ASC; %>
      <%   if (sortKeyList[i] == wml140SortKey.intValue()) { %>
      <%     if (wml140Order.intValue() == Wml140Form.WML140_ORDER_DESC) { %>
      <%       title = title + " " + down; %>
      <%     } else { %>
      <%       title = title + " " + up; %>
      <%       order = Wml140Form.WML140_ORDER_DESC; %>
      <%     } %>
      <%   } %>
      <th class="<%= widthList[i] %>">
        <a href="#" onClick="return wml140Sort(<%= String.valueOf(sortKeyList[i]) %>, <%= String.valueOf(order) %>)"><%= title %></a>
      </th>
      <% } %>

    </tr>

  <logic:iterate id="mailData" name="wml140knForm" property="wml140mailList" indexId="idx">
    <tr>
      <td class="txt_c">
      <logic:equal name="mailData" property="attach" value="true">
        <img class="btn_classicImg-display" src="../webmail/images/classic/attach.gif" alt="<gsmsg:write key="cmn.attach.file" />">
        <img class="btn_originalImg-display" src="../webmail/images/original/icon_attach_12.png" alt="<gsmsg:write key="cmn.attach.file" />">
      </logic:equal>
      </td>
      <td>
        <bean:write name="mailData" property="from" />
      </td>
      <td>
        <a href="#" onClick="openDetail('<bean:write name="mailData" property="mailNum" />');"><bean:write name="mailData" property="subject" /></a>
      </td>
      <td>
        <bean:write name="mailData" property="strDate" />
      </td>
    </tr>
  </logic:iterate>

  </table>

  <logic:notEmpty name="wml140knForm" property="wml140mailListPageCombo">
  <div class="paging">
    <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select styleClass="paging_combo"  property="wml140mailListPageBottom" onchange="selectPage(1);">
      <html:optionsCollection name="wml140knForm" property="wml140mailListPageCombo" value="value" label="label" />
    </html:select>
    <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
      <i class="icon-paging_right"></i>
    </button>
  </div>
  </logic:notEmpty>


</logic:notEmpty>

  <div class="footerBtn_block mt20">
    <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backInput');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>