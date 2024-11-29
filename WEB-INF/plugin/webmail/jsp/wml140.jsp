<%@page import="jp.groupsession.v2.cmn.GSConstWebmail"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.wml.wml140.Wml140Form" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

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

  <title>GROUPSESSION <gsmsg:write key="wml.wml140.04" /></title>
</head>

<body class="body_03" onload="changeFilterInput();">

<html:form action="/webmail/wml140">

<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlFilterCmdMode" />
<html:hidden property="wmlEditFilterId" />
<html:hidden property="wml130accountSid" />
<html:hidden property="wml140initFlg" />
<html:hidden property="dspCount" />
<html:hidden property="wml130SortRadio" />

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
<html:hidden property="wml140svTempFile" />

<input type="hidden" name="wml140actionSendValueDelIdx" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.wml140.04" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn mwp100" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('filterSearch');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="wml.wml140.03" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="wml.wml140.03" />">
          <gsmsg:write key="wml.wml140.03" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('filterList');">
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
        <bean:write name="wml140Form" property="wml130account" />
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="wml.84" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <html:text name="wml140Form" property="wml140FilterName" maxlength="100" styleClass="wp550" />
      </td>
    </tr>
  </table>

  <div class="w100 mt20 txt_l">
    <span class="verAlignMid">
      <gsmsg:write key="wml.40" />
      <html:radio name="wml140Form" property="wml140filterType" value="0" styleId="filtype1" styleClass="ml20" /><label for="filtype1"><gsmsg:write key="wml.wml140.01" /></label>
      <html:radio name="wml140Form" property="wml140filterType" value="1" styleId="filtype2" styleClass="ml10" /><label for="filtype2"><gsmsg:write key="wml.containing.either" /></label>
    </span>
  </div>

  <table class="table-left mt0">

<%
  int condition = 1;
  String conditionCheck = null;
  String conditionType = null;
  String conditionExs = null;
  String conditionText = null;
  String number = null;
  try {
    for (int i = 1; i <= 5; i++) {
      conditionCheck = "wml140condition" + condition;
      conditionType = "wml140conditionType" + condition;
      conditionExs = "wml140conditionExs" + condition;
      conditionText = "wml140conditionText" + condition;
      number = String.valueOf(i);
%>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="wml.wml140.02" /><%=condition%>
      </th>
      <td class="w80 no_w">
        <span class="verAlignMid">
          <html:checkbox name="wml140Form" property="<%=conditionCheck%>" value="<%= number %>" styleClass="mr5" onclick="changeFilterInput();" />

        <logic:notEmpty name="wml140Form" property="conditionList1">
          <html:select property="<%=conditionType%>" size="1" styleClass="mr5">
            <html:optionsCollection name="wml140Form" property="conditionList1" value="value" label="label" />
          </html:select>
        </logic:notEmpty>

        <logic:notEmpty name="wml140Form" property="conditionList2">
          <html:select property="<%=conditionExs%>" size="1" styleClass="mr5">
            <html:optionsCollection name="wml140Form" property="conditionList2" value="value" label="label" />
          </html:select>
        </logic:notEmpty>

          <html:text property="<%=conditionText%>" maxlength="256" styleClass="wp400" />
        </span>
      </td>
    </tr>
<%
      condition++;
    }
  } catch (Exception e) {

  }
%>

    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.attach.file" />
      </th>
      <td class="w80 no_w">
        <span class="verAlignMid">
          <html:checkbox name="wml140Form" property="wml140tempFile" value="1" styleId="filTempFile" styleClass="mr5" /><label for="filTempFile"><gsmsg:write key="wml.14" /></label>
        </span>
      </td>
    </tr>

  </table>

  <div class="w100 mt20 txt_l">
    <gsmsg:write key="wml.56" />
  </div>

  <table class="table-left mt0">
    <tr>
      <th class="w0 txt_c txt_m js_tableTopCheck cursor_p">
        <html:checkbox name="wml140Form" property="wml140actionLabel" value="1" />
      </th>
      <td class="w100">
        <span class="verAlignMid">
          <gsmsg:write key="wml.75" />
        <logic:notEmpty name="wml140Form" property="conditionList2">
          <html:select property="wml140actionLabelValue" size="1" styleClass="ml5">
            <html:optionsCollection name="wml140Form" property="lbList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w0 txt_c txt_m js_tableTopCheck cursor_p">
        <html:checkbox name="wml140Form" property="wml140actionRead" value="1" />
      </th>
      <td class="w100">
        <gsmsg:write key="cmn.mark.read" />
      </td>
    </tr>
    <tr>
      <th class="w0 txt_c txt_m js_tableTopCheck cursor_p">
        <html:checkbox name="wml140Form" property="wml140actionMove" value="1" />
      </th>
      <td class="w100">
        <span class="verAlignMid">
          <gsmsg:write key="wml.92" />
          <html:select property="wml140actionFolderValue" size="1" styleClass="ml5">
            <html:option value="<%=String.valueOf(GSConstWebmail.FILTER_MOVE_DUST) %>"><gsmsg:write key="cmn.trash" /></html:option>
            <html:option value="<%=String.valueOf(GSConstWebmail.FILTER_MOVE_STRAGE) %>"><gsmsg:write key="cmn.strage" /></html:option>
          </html:select>
        </span>
      </td>
    </tr>

  <logic:notEqual name="wml140Form" property="wml140fwLimitFlg" value="1">

    <tr>
      <th class="w0 txt_c txt_m js_tableTopCheck cursor_p">
        <html:checkbox name="wml140Form" property="wml140actionSend" value="1" onclick="changeFilterInput();" />
      </th>
      <td class="w100">
        <table class="table-noBorder">
          <tr>
          <td class="w20 no_w">
            <gsmsg:write key="wml.57" />
          </td>
          <td class="w80 pl30 txt_t">
            <table id="wml140fwAddressArea" class="w99 txt_t">
          <logic:notEmpty name="wml140Form" property="wml140actionSendValue">
            <logic:iterate id="fwAddress" name="wml140Form" property="wml140actionSendValue" indexId="fwAdrIdx" type="java.lang.String">
              <tr>
                <td class="w5 txt_r">
                  <% if (fwAdrIdx.intValue() > 0) { %>
                  <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif" alt="<gsmsg:write key="cmn.delete" />" onClick="deleteFwAddress(<%= String.valueOf(fwAdrIdx.intValue()) %>);">
                  <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />" onClick="deleteFwAddress(<%= String.valueOf(fwAdrIdx.intValue()) %>);">
                  <% } else { %>&nbsp;<% } %>
                </td>
                <td class="w95">
                  <% if (fwAddress == null) { fwAddress = ""; }%>
                  <input type="text" name="wml140actionSendValue" value="<%= fwAddress %>"  maxlength="256" class="w60">
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
          <logic:empty name="wml140Form" property="wml140actionSendValue">
              <tr>
                <td class="w5">&nbsp;</td>
                <td class="w95"><input type="text" name="wml140actionSendValue" value=""  maxlength="256" class="w60"></td>
              </tr>
          </logic:empty>
              <tr>
                <td>&nbsp;</td>
                <td><a href="#" onClick="return buttonPush('addFwAddress');" class="fs_11"><gsmsg:write key="wml.wml140.05" /></td>
              </tr>
            </table>
          </td>
          </tr>
        </table>
      </td>
    </tr>
  </logic:notEqual>

  </table>

<logic:notEmpty name="wml140Form" property="wml140mailList">

  <div class="w100 mt20">
    <span class="flo_l verAlignMid">
      <html:checkbox styleId="wml140doFilter" styleClass="mr5" name="wml140Form" property="wml140doFilter" value="<%= String.valueOf(jp.groupsession.v2.wml.wml140.Wml140Form.WML140_DOFILTER_YES) %>" /><label for="wml140doFilter"><gsmsg:write key="wml.85" />&nbsp;<gsmsg:write key="wml.116" /></label>
    </span>
  <logic:notEmpty name="wml140Form" property="wml140mailListPageCombo">
    <span class="flo_r">
      <div class="paging">
        <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  property="wml140mailListPageTop" onchange="selectPage(0);">
          <html:optionsCollection name="wml140Form" property="wml140mailListPageCombo" value="value" label="label" />
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

      <bean:define id="wml140SortKey" name="wml140Form" property="wml140mailListSortKey" type="java.lang.Integer" />
      <bean:define id="wml140Order" name="wml140Form" property="wml140mailListOrder" type="java.lang.Integer" />
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
      <th class="<%= widthList[i] %> cursor_p">
        <a href="#" onClick="return wml140Sort(<%= String.valueOf(sortKeyList[i]) %>, <%= String.valueOf(order) %>)"><%= title %></a>
      </th>
      <% } %>

    </tr>

  <logic:iterate id="mailData" name="wml140Form" property="wml140mailList" indexId="idx">
    <tr class="js_mailListHover cursor_p" data-sid="<bean:write name="mailData" property="mailNum" />">
      <td class="js_mailListClick txt_c">
      <logic:equal name="mailData" property="attach" value="true">
        <img class="btn_classicImg-display" src="../webmail/images/classic/attach.gif" alt="<gsmsg:write key="cmn.attach.file" />">
        <img class="btn_originalImg-display" src="../webmail/images/original/icon_attach_12.png" alt="<gsmsg:write key="cmn.attach.file" />">
      </logic:equal>
      </td>
      <td class="js_mailListClick">
        <bean:write name="mailData" property="from" />
      </td>
      <td class="js_mailListClick">
        <span class="cl_linkDef"><bean:write name="mailData" property="subject" /></span>
      </td>
      <td class="js_mailListClick txt_c">
        <bean:write name="mailData" property="strDate" />
      </td>
    </tr>
  </logic:iterate>

  </table>

  <logic:notEmpty name="wml140Form" property="wml140mailListPageCombo">
  <div class="paging">
    <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
      <i class="icon-paging_left"></i>
    </button>
    <html:select styleClass="paging_combo"  property="wml140mailListPageBottom" onchange="selectPage(1);">
      <html:optionsCollection name="wml140Form" property="wml140mailListPageCombo" value="value" label="label" />
    </html:select>
    <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
      <i class="icon-paging_right"></i>
    </button>
  </div>
  </logic:notEmpty>


</logic:notEmpty>

  <div class="footerBtn_block mt20">
    <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn mwp100" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('filterSearch');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="wml.wml140.03" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="wml.wml140.03" />">
      <gsmsg:write key="wml.wml140.03" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('filterList');">
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