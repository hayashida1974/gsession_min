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

  <script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
  <link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
  <link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/datepicker.js?<%=GSConst.VERSION_PARAM%>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml070.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml320.js?<%= GSConst.VERSION_PARAM %>"></script>
  <gsjsmsg:js filename="gsjsmsg.js"/>

  <title>GROUPSESSION</title>
</head>

<body>

<html:form action="/webmail/wml320">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="wml320dateYearFr" value="">
<input type="hidden" name="wml320dateMonthFr" value="">
<input type="hidden" name="wml320dateDayFr" value="">
<input type="hidden" name="wml320dateYearTo" value="">
<input type="hidden" name="wml320dateMonthTo" value="">
<input type="hidden" name="wml320dateDayTo" value="">

<html:hidden property="wmlCmdMode" />
<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlAccountMode" />
<html:hidden property="wmlAccountSid" />
<html:hidden property="backScreen" />

<html:hidden property="wml320dateYearFr" />
<html:hidden property="wml320dateMonthFr" />
<html:hidden property="wml320dateDayFr" />
<html:hidden property="wml320dateYearTo" />
<html:hidden property="wml320dateMonthTo" />
<html:hidden property="wml320dateDayTo" />

<html:hidden property="wml320svAccount" />
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
<html:hidden property="wml320searchFlg" />
<html:hidden property="wml320initFlg" />

<html:hidden styleId="delMailCount" property="deleteMailCount" />

<input type="hidden" name="yearRangeMinFr" value="10" />
<input type="hidden" name="yearRangeMaxFr" value="0" />
<input type="hidden" name="yearRangeMinTo" value="10" />
<input type="hidden" name="yearRangeMaxTo" value="0" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<% String btnHideClass = ""; %>
<logic:greaterThan name="wml320Form" property="deleteMailCount" value="0">
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="wml.wml320.4" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn ml8 js_wmlHideBtn <%= btnHideClass %>" value="<gsmsg:write key="cmn.delete" />" onclick="setDateParam();buttonPush('delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('psnTool');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">

  <div class="txt_c mb20"><html:errors/></div>

  <logic:greaterThan name="wml320Form" property="deleteMailCount" value="0">
    <div id="delProgressArea" class="txt_l textError mb20">
      <gsmsg:write key="wml.wml320.7" />
      <br><gsmsg:write key="wml.wml320.8" /><span id="mailCountArea" class="ml5"><bean:write name="wml320Form" property="deleteMailCount" /></span>
    </div>
  </logic:greaterThan>

  <bean:define id="delLimit" name="wml320Form" property="wml320delLimit" type="java.lang.Integer" />
  <% if (delLimit.intValue() > 0) { %>
  <div class="txt_l textError">
    <gsmsg:write key="wml.wml320.6" arg0="<%= String.valueOf(delLimit) %>"/>
  </div>
  <% } %>

  <table class="table-left w100 mt0">
     <tr>
       <th class="w15 fw_b txt_l">
         <gsmsg:write key="wml.102" />
       </th>
       <td class="w85" colspan="3">
         <html:select name="wml320Form" property="wml320account" styleId="accountSid" styleClass="" onchange="changeAccount()">
           <html:optionsCollection property="accountCombo" value="value" label="label"/>
         </html:select>
       </td>
     </tr>
  </table>
  <table class="table-left w100">
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.folder" /><span class="ml5 cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w85" colspan="3">
        <logic:notEmpty name="wml320Form" property="wml320svDirectory" scope="request">
          <logic:iterate id="key" name="wml320Form" property="wml320svDirectory" scope="request">
            <input type="hidden" name="wml320svDirectory" value="<bean:write name="key"/>">
          </logic:iterate>
        </logic:notEmpty>
        <span class="verAlignMid">
          <html:multibox  name="wml320Form" property="wml320directory" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_RECEIVE) %>" styleId="wml320zyusin" />
          <label for="wml320zyusin" class="mr10"><gsmsg:write key="cmn.receive" /></label>
          <html:multibox  name="wml320Form" property="wml320directory" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_SENDED) %>" styleId="wml320sousin" />
          <label for="wml320sousin" class="mr10"><gsmsg:write key="wml.19" /></label>
          <html:multibox  name="wml320Form" property="wml320directory" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_NOSEND) %>" styleId="wml320yoyakuSousin"/>
          <label for="wml320yoyakuSousin" class="mr10"><gsmsg:write key="wml.211" /></label>
          <html:multibox  name="wml320Form" property="wml320directory" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_DRAFT) %>" styleId="wml320soukou"/>
          <label for="wml320soukou" class="mr10"><gsmsg:write key="cmn.draft" /></label>
          <html:multibox  name="wml320Form" property="wml320directory" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_DUST) %>" styleId="wml320gomi"/>
          <label for="wml320gomi" class="mr10"><gsmsg:write key="cmn.trash" /></label>
          <html:multibox  name="wml320Form" property="wml320directory" value="<%= String.valueOf(GSConstWebmail.DIR_TYPE_STORAGE) %>" styleId="wml320hokan"/>
          <label for="wml320hokan"><gsmsg:write key="cmn.strage" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.keyword" />
      </th>
      <td class="w85" colspan="3">
        <html:text name="wml320Form" property="wml320keyword" maxlength="100" styleClass="w60"/>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.sendfrom" />
      </th>
      <td class="w35">
        <html:text name="wml320Form" property="wml320from" maxlength="256" styleClass="w80"/>
      </td>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="wml.wml010.01" />
      </th>
      <td class="w35">
        <span class="verAlignMid">
          <html:radio name="wml320Form" property="wml320readed" value="0" styleId="searchReadKbn0"/>
          <label for="searchReadKbn0" class="mr10"><gsmsg:write key="cmn.not.specified" /></label>
          <html:radio name="wml320Form" property="wml320readed" value="1" styleId="searchReadKbn1"/>
          <label for="searchReadKbn1" class="mr10"><gsmsg:write key="cmn.read.yet" /></label>
          <html:radio name="wml320Form" property="wml320readed" value="2" styleId="searchReadKbn2"/>
          <label for="searchReadKbn2"><gsmsg:write key="cmn.read.already" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="wml.send.dest"/>
      </th>
      <td class="w35">
        <span class="verAlignMid">
          <html:checkbox name="wml320Form" property="wml320destTypeTo" value="1" styleId="wml010destTo"/>
          <label for="wml010destTo" class="mr10"><gsmsg:write key="cmn.from" /></label>
          <html:checkbox name="wml320Form" property="wml320destTypeCc" value="1" styleId="wml010destCc"/>
          <label for="wml010destCc" class="mr10"><gsmsg:write key="cmn.cc" /></label>
          <html:checkbox name="wml320Form" property="wml320destTypeBcc" value="1" styleId="wml010destBcc"/>
          <label for="wml010destBcc"><gsmsg:write key="cmn.bcc" /></label>
        </span>
        <html:text name="wml320Form" property="wml320dest" maxlength="256" styleClass="w80"/>
      </td>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.attach.file" />
      </th>
      <td class="w35">
        <span class="verAlignMid">
          <html:radio  name="wml320Form" property="wml320attach" value="0" styleId="wml320attachKbn0"/>
          <label for="wml320attachKbn0" class="mr10"><gsmsg:write key="cmn.not.specified" /></label>
          <html:radio  name="wml320Form" property="wml320attach"  value="1" styleId="wml320attachKbn1"/>
          <label for="wml320attachKbn1" class="mr10"><gsmsg:write key="cmn.exist" /></label>
          <html:radio  name="wml320Form" property="wml320attach"  value="2" styleId="wml320attachKbn2"/>
          <label for="wml320attachKbn2"><gsmsg:write key="cmn.no3" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w15 fw_b txt_l">
        <gsmsg:write key="cmn.date2" />
      </th>
      <td class="w85" colspan="3">
        <span class="verAlignMid">
          <html:radio  name="wml320Form" property="wml320dateType" value="0" onclick="changeDateType();" styleId="searchDate0"/>
          <label for="searchDate0" class="mr10"><gsmsg:write key="cmn.not.specified" /></label>
          <html:radio  name="wml320Form" property="wml320dateType" value="1" onclick="changeDateType();" styleId="searchDate1"/>
          <label for="searchDate1"><gsmsg:write key="wml.wml010.12" /></label>
        </span>
        <div class="settingForm_separator" id="searchDateArea" style="">
          <div class="verAlignMid">
            <span class="fw_b"><gsmsg:write key="wml.wml010.38" /><gsmsg:write key="wml.215" /></span>
            <html:text name="wml320Form" property="wml320DateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatefr" />
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          </div>
          <div class="verAlignMid">
            <span class="ml10 fw_b"><gsmsg:write key="wml.wml010.39" /><gsmsg:write key="wml.215" /></span>
            <html:text name="wml320Form" property="wml320DateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateto" />
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.label" />
      </th>
      <td>
        <html:select property="wml320label" styleId="" styleClass="wp190">
           <html:optionsCollection name="wml320Form" property="wml320labelCombo" value="value" label="label" />
        </html:select>
      </td>
      <th>
        <gsmsg:write key="cmn.size" />
      </th>
      <td>
        <html:text name="wml320Form" property="wml320size" maxlength="8" styleClass="w20 mr5"/><gsmsg:write key="wml.wml320.1" /><gsmsg:write key="cmn.comp.oe" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.sort.order" />
      </th>
      <td class="w90" colspan="3">
        <div class="display_inline">
          <html:select property="wml320sortKey" >
            <html:optionsCollection name="wml320Form" property="wml320sortKeyCombo" value="value" label="label" />
          </html:select>
        </div>
        <div class="display_inline txt_b">
         <span class="verAlignMid mr20">
          <html:radio name="wml320Form" property="wml320order" styleId="sort1_up" value="<%= String.valueOf(Wml320Form.WML320_ORDER_ASC) %>" />
          <label for="sort1_up"><gsmsg:write key="cmn.order.asc" /></label>
          <html:radio styleClass="ml10" name="wml320Form" property="wml320order" styleId="sort1_dw" value="<%= String.valueOf(Wml320Form.WML320_ORDER_DESC) %>" />
          <label for="sort1_dw"><gsmsg:write key="cmn.order.desc" /></label>
        </span>
        </div>
      </td>
    </tr>

  </table>

  <div class="txt_c">
    <button type="button" class="baseBtn js_wmlHideBtn <%= btnHideClass %>" id="head_menu_search_btn2" value="<gsmsg:write key="cmn.search" />" onclick="searchpush();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>

  <div class="txt_r mt10">
     <logic:notEmpty name="wml320Form" property="wml320pageCombo">
       <span class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
           <html:select property="wml320pageTop" onchange="setDateParam();selectPage(0);" styleClass="paging_combo">
             <html:optionsCollection name="wml320Form" property="wml320pageCombo" value="value" label="label" />
           </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
       </span>
     </logic:notEmpty>
  </div>

  <logic:notEmpty  name="wml320Form" property="wml320mailList">
  <table class="table-top mt5 mb0">
    <tr>
      <th class="w5 no_w">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file.gif" alt="<gsmsg:write key="cmn.attach.file" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attach.file" />">
      </th>
      <th class="w5 no_w">
        <img class="classic-display" src="../common/images/classic/icon_mail.png" alt="<gsmsg:write key="cmn.read.yet" />">
        <img class="original-display" src="../common/images/original/icon_midoku_15.png" alt="<gsmsg:write key="cmn.read.yet" />">
      </th>
      <th class="w30 no_w">
        <gsmsg:write key="wml.wml320.5" />
      </th>
      <th class="w50 no_w">
        <gsmsg:write key="cmn.subject" />
      </th>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.date" />
      </th>
    </tr>

    <logic:iterate id="mailData" name="wml320Form" property="wml320mailList">
      <tr class="js_mailListHover js_mailListClick cursor_p" data-sid="<bean:write name="mailData" property="mailNum" />">
        <td class="txt_c">
          <logic:equal name="mailData" property="tempFlg" value="<%= String.valueOf(GSConstWebmail.TEMPFLG_EXIST) %>">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file.gif" alt="<gsmsg:write key="cmn.attach.file" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attach.file" />">
          </logic:equal>
        </td>
        <td class="txt_c">
          <logic:equal name="mailData" property="readed" value="<%= String.valueOf(GSConstWebmail.READEDFLG_NOREAD) %>">
            <img class="classic-display" src="../common/images/classic/icon_mail.png" alt="">
            <img class="original-display" src="../common/images/original/icon_midoku_15.png" alt="">
          </logic:equal>
        </td>
        <td class="word_b-all">
          <gsmsg:write key="wml.send.dest" /><gsmsg:write key="cmn.colon" /><bean:write name="mailData" property="dest" /><br>
          <gsmsg:write key="cmn.sendfrom" /><gsmsg:write key="cmn.colon" /><bean:write name="mailData" property="from" />
        </td>
        <td class="word_b-all"><span class="cl_linkDef"><bean:write name="mailData" property="title" /></span></td>
        <td class="txt_c"><bean:write name="mailData" property="dspDate" /></td>
      </tr>
    </logic:iterate>
  </table>
  </logic:notEmpty>

  <div class="txt_r mt5">
     <logic:notEmpty name="wml320Form" property="wml320pageCombo">
       <span class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
           <html:select property="wml320pageBottom" onchange="setDateParam();selectPage(1);" styleClass="paging_combo">
             <html:optionsCollection name="wml320Form" property="wml320pageCombo" value="value" label="label" />
           </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
       </span>
     </logic:notEmpty>
  </div>

  <div class="footerBtn_block txt_r mt10">
    <button type="button" class="baseBtn ml8 js_wmlHideBtn <%= btnHideClass %>" value="<gsmsg:write key="cmn.delete" />" onclick="setDateParam();buttonPush('delete');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('psnTool');">
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

