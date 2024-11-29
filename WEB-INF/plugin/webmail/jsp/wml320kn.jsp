<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstWebmail" %>
<%@ page import="jp.groupsession.v2.wml.wml320.Wml320Form" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script type="text/javascript" src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml320.js?<%= GSConst.VERSION_PARAM %>"></script>
  <gsjsmsg:js filename="gsjsmsg.js"/>

  <title>GROUPSESSION</title>
</head>

<body>

<html:form action="/webmail/wml320kn">
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<input type="hidden" name="CMD" value="">

<html:hidden property="wmlCmdMode" />
<html:hidden property="wmlViewAccount" />
<html:hidden property="backScreen" />

<html:hidden property="wml320account" />
<html:hidden property="wml320keyword" />
<html:hidden property="wml320from" />
<html:hidden property="wml320readed" />
<html:hidden property="wml320dest" />
<html:hidden property="wml320destTypeTo" />
<html:hidden property="wml320destTypeBcc" />
<html:hidden property="wml320destTypeCc" />
<html:hidden property="wml320attach" />
<html:hidden property="wml320dateType" />
<html:hidden property="wml320dateYearFr" />
<html:hidden property="wml320dateMonthFr" />
<html:hidden property="wml320dateDayFr" />
<html:hidden property="wml320dateYearTo" />
<html:hidden property="wml320dateMonthTo" />
<html:hidden property="wml320dateDayTo" />
<html:hidden property="wml320label" />
<html:hidden property="wml320size" />
<html:hidden property="wml320sortKey" />
<html:hidden property="wml320order" />

<html:hidden styleId="accountSid" property="wml320svAccount" />
<html:hidden property="wml320svKeyword" />
<html:hidden property="wml320svFrom" />
<html:hidden property="wml320svReaded" />
<html:hidden property="wml320svDest" />
<html:hidden property="wml320svDestTypeTo" />
<html:hidden property="wml320svDestTypeBcc" />
<html:hidden property="wml320svDestTypeCc" />
<html:hidden property="wml320svAttach" />
<html:hidden property="wml320svDateType" />
<html:hidden property="wml320svDateYearFr" />
<html:hidden property="wml320svDateMonthFr" />
<html:hidden property="wml320svDateDayFr" />
<html:hidden property="wml320svDateYearTo" />
<html:hidden property="wml320svDateMonthTo" />
<html:hidden property="wml320svDateDayTo" />
<html:hidden property="wml320svLabel" />
<html:hidden property="wml320svSize" />
<html:hidden property="wml320svSortKey" />
<html:hidden property="wml320svOrder" />

<html:hidden property="wml320NowPage" />
<html:hidden property="wml320pageTop" />
<html:hidden property="wml320pageBottom" />

<html:hidden property="wml320searchFlg" />
<html:hidden property="wml320initFlg" />

<html:hidden styleId="delMailCount" property="deleteMailCount" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<% String btnHideClass = ""; %>
<logic:greaterThan name="wml320knForm" property="deleteMailCount" value="0">
  <% btnHideClass = " display_none"; %>
</logic:greaterThan>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.wml320kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn js_wmlHideBtn <%= btnHideClass %>" onclick="buttonPush('decision');">
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
  <div class="txt_l textError">
    <gsmsg:write key="wml.wml320kn.2" />
    <div><gsmsg:write key="wml.wml320kn.3" /></div>
  </div>

  <logic:messagesPresent message="false">
    <div class="mt20"><html:errors/></div>
  </logic:messagesPresent>

  <logic:greaterThan name="wml320knForm" property="deleteMailCount" value="0">
    <div id="delProgressArea" class="txt_l textError mt20">
      <gsmsg:write key="wml.wml320.7" />
      <br><gsmsg:write key="wml.wml320.8" /><span id="mailCountArea" class="ml5"><bean:write name="wml320knForm" property="deleteMailCount" /></span>
    </div>
  </logic:greaterThan>

  <table class="table-left w100">
     <tr>
       <th class="w15 fw_b txt_l">
          <gsmsg:write key="wml.102" />
       </th>
       <td class="w85" colspan="3">
         <bean:write name="wml320knForm" property="wml320knAccount" />
       </td>
     </tr>
  </table>

  <table class="table-left w100">
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.folder" />
      </th>
      <td class="w85" colspan="3">
        <logic:notEmpty name="wml320knForm" property="wml320svDirectory" scope="request">
          <logic:iterate id="key" name="wml320knForm" property="wml320svDirectory" scope="request">
            <input type="hidden" name="wml320svDirectory" value="<bean:write name="key"/>">
          </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty name="wml320knForm" property="wml320svDirectory" scope="request">
          <logic:iterate id="key" name="wml320knForm" property="wml320svDirectory" scope="request">
            <logic:equal name="key" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_RECEIVE) %>">
              <div><gsmsg:write key="cmn.receive" /></div>
            </logic:equal>
            <logic:equal name="key" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_SENDED) %>">
              <div><gsmsg:write key="wml.19" /></div>
            </logic:equal>
            <logic:equal name="key" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_NOSEND) %>">
              <div><gsmsg:write key="wml.211" /></div>
            </logic:equal>
            <logic:equal name="key" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_DRAFT) %>">
              <div><gsmsg:write key="cmn.draft" /></div>
            </logic:equal>
            <logic:equal name="key" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_DUST) %>">
              <div><gsmsg:write key="cmn.trash" /></div>
            </logic:equal>
            <logic:equal name="key" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_STORAGE) %>">
              <div><gsmsg:write key="cmn.strage" /></div>
            </logic:equal>
          </logic:iterate>
        </logic:notEmpty>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.keyword" />
      </th>
      <td class="w85" colspan="3">
        <bean:write name="wml320knForm" property="wml320svKeyword" />
        <logic:empty  name="wml320knForm" property="wml320svKeyword">
          <gsmsg:write key="cmn.not.specified" />
        </logic:empty>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.sendfrom" />
      </th>
      <td class="w35">
        <bean:write name="wml320knForm" property="wml320svFrom" />
        <logic:empty  name="wml320knForm" property="wml320svFrom">
          <gsmsg:write key="cmn.not.specified" />
        </logic:empty>
      </td>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="wml.wml010.01" />
      </th>
      <td class="w35">
        <span class="verAlignMid">
          <logic:equal name="wml320knForm" property="wml320svReaded" value="0">
            <gsmsg:write key="cmn.not.specified" />
          </logic:equal>
          <logic:equal name="wml320knForm" property="wml320svReaded" value="1">
            <gsmsg:write key="cmn.read.yet" />
          </logic:equal>
          <logic:equal name="wml320knForm" property="wml320svReaded" value="2">
            <gsmsg:write key="cmn.read.already" />
          </logic:equal>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="wml.send.dest"/>
      </th>
      <td class="w35">
        <span class="verAlignMid">
          <logic:notEmpty  name="wml320knForm" property="wml320svDest">
            <gsmsg:write key="cmn.target" /><gsmsg:write key="wml.215" />
            <logic:equal name="wml320knForm" property="wml320svDestTypeTo" value="1">
              <gsmsg:write key="cmn.from" />
            </logic:equal>
            <logic:equal name="wml320knForm" property="wml320svDestTypeCc" value="1">
              <gsmsg:write key="cmn.cc" />
            </logic:equal>
            <logic:equal name="wml320knForm" property="wml320svDestTypeBcc" value="1">
              <gsmsg:write key="cmn.bcc" />
            </logic:equal>
          </span>
          <div>
            <bean:write name="wml320knForm" property="wml320svDest" />
          </div>
        </logic:notEmpty>
        <logic:empty  name="wml320knForm" property="wml320svDest">
          <gsmsg:write key="cmn.not.specified" />
        </logic:empty>
      </td>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.attach.file" />
      </th>
      <td class="w35">
        <span class="verAlignMid">
          <span class="verAlignMid">
          <logic:equal name="wml320knForm" property="wml320svAttach" value="0">
            <gsmsg:write key="cmn.not.specified" />
          </logic:equal>
          <logic:equal name="wml320knForm" property="wml320svAttach" value="1">
            <gsmsg:write key="cmn.exist" />
          </logic:equal>
          <logic:equal name="wml320knForm" property="wml320svAttach" value="2">
            <gsmsg:write key="cmn.no3" />
          </logic:equal>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.date2" />
      </th>
      <td class="w85" colspan="3">
        <span class="">
          <logic:equal name="wml320knForm" property="wml320svDateType" value="0">
            <gsmsg:write key="cmn.not.specified" />
          </logic:equal>
          <logic:equal name="wml320knForm" property="wml320svDateType" value="1">
            <span class="mr10">
              <bean:define id="frYear" name="wml320knForm" property="wml320svDateYearFr" type="java.lang.Integer" />
              <bean:define id="frMonth" name="wml320knForm" property="wml320svDateMonthFr" type="java.lang.Integer" />
              <bean:define id="frDay" name="wml320knForm" property="wml320svDateDayFr" type="java.lang.Integer" />
              <gsmsg:write key="wml.wml010.38" /><gsmsg:write key="wml.215" /><gsmsg:write key="cmn.date4" arg0="<%= String.valueOf(frYear) %>" arg1="<%= String.valueOf(frMonth) %>" arg2="<%= String.valueOf(frDay) %>"/>
            </span>
            <span class="ml10">
              <bean:define id="toYear" name="wml320knForm" property="wml320svDateYearTo" type="java.lang.Integer" />
              <bean:define id="toMonth" name="wml320knForm" property="wml320svDateMonthTo" type="java.lang.Integer" />
              <bean:define id="toDay" name="wml320knForm" property="wml320svDateDayTo" type="java.lang.Integer" />
              <gsmsg:write key="wml.wml010.39" /><gsmsg:write key="wml.215" /><gsmsg:write key="cmn.date4" arg0="<%= String.valueOf(toYear) %>" arg1="<%= String.valueOf(toMonth) %>" arg2="<%= String.valueOf(toDay) %>"/>
            </span>
          </logic:equal>
        </span>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.label" />
      </th>
      <td>
        <bean:write name="wml320knForm" property="wml320knLabel" />
        <logic:empty  name="wml320knForm" property="wml320knLabel">
          <gsmsg:write key="cmn.not.specified" />
        </logic:empty>
      </td>
      <th>
        <gsmsg:write key="cmn.size" />
      </th>
      <td>
        <logic:notEmpty  name="wml320knForm" property="wml320svSize">
          <bean:write name="wml320knForm" property="wml320svSize" /><gsmsg:write key="wml.wml320.1" /><gsmsg:write key="cmn.comp.oe" />
      </logic:notEmpty>
      <logic:empty  name="wml320knForm" property="wml320svSize">
          <gsmsg:write key="cmn.not.specified" />
        </logic:empty>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.sort.order" />
      </th>
      <td class="w85" colspan="3">
        <bean:define id="wmlSortKey" name="wml320knForm" property="wml320svSortKey" type="java.lang.Integer" />
        <% if (wmlSortKey.intValue() == Wml320Form.WML320_SKEY_FROM) { %>
          <gsmsg:write key="cmn.sendfrom" />
        <% } else if (wmlSortKey.intValue() == Wml320Form.WML320_SKEY_SUBJECT) { %>
          <gsmsg:write key="cmn.subject" />
        <% } else { %>
          <gsmsg:write key="cmn.date" />
        <% } %>

        <span class="ml5">
        <logic:equal name="wml320knForm" property="wml320svOrder" value="<%= String.valueOf(Wml320Form.WML320_ORDER_DESC) %>">
          <gsmsg:write key="cmn.order.desc" />
        </logic:equal>
        <logic:notEqual name="wml320knForm" property="wml320svOrder" value="<%= String.valueOf(Wml320Form.WML320_ORDER_DESC) %>">
          <gsmsg:write key="cmn.order.asc" />
        </logic:notEqual>
        </span>
        </div>
      </td>
    </tr>

  </table>

  <div class="footerBtn_block txt_r">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn js_wmlHideBtn <%= btnHideClass %>" onclick="buttonPush('decision');">
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

