<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstWebmail" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.wml.wml150kn.Wml150knForm" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml150.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml150.04" /></title>
</head>

<body>

<html:form action="/webmail/wml150kn">

<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="wmlViewAccount" />
<html:hidden property="wml150acntMakeKbn" />
<html:hidden property="wml150acntSendFormat" />
<html:hidden property="wml150acntLogRegist" />
<html:hidden property="wml150initFlg" />
<html:hidden property="wml150elementKbn" />
<html:hidden property="wml150permitKbn" />
<html:hidden property="wml150diskSize" />
<html:hidden property="wml150diskSizeLimit" />
<html:hidden property="wml150diskSizeComp" />
<html:hidden property="wml150warnDisk" />
<html:hidden property="wml150warnDiskThreshold" />
<html:hidden property="wml150delReceive" />
<html:hidden property="wml150autoResv" />
<html:hidden property="wml150AutoReceiveTime" />
<html:hidden property="wml150receiveServer" />
<html:hidden property="wml150receiveServerPort" />
<html:hidden property="wml150receiveServerEncrypt" />
<html:hidden property="wml150sendServer" />
<html:hidden property="wml150sendServerPort" />
<html:hidden property="wml150sendServerEncrypt" />
<html:hidden property="wml150sendMaxSizeKbn" />
<html:hidden property="wml150sendMaxSize" />
<html:hidden property="wml150FwLimit" />
<html:hidden property="wml150FwLimitText" />
<html:hidden property="wml150svFwLimitText" />
<html:hidden property="wml150FwLimitDelete" />

<html:hidden property="wml150checkAddress" />
<html:hidden property="wml150checkFile" />
<html:hidden property="wml150compressFile" />
<html:hidden property="wml150compressFileDef" />
<html:hidden property="wml150timeSent" />
<html:hidden property="wml150timeSentDef" />
<html:hidden property="wml150bcc" />
<html:hidden property="wml150bccThreshold" />
<html:hidden property="wml150settingServer" />
<html:hidden property="wml150proxyUser" />
<html:hidden property="wml150linkLimit" />
<html:hidden property="wml150TldLimit" />
<html:hidden property="wml150TldLimitText" />


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>

    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="cmn.preferences.kn" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
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

  <ul class="tabHeader w100">
    <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none" id="tab1">
      <gsmsg:write key="cmn.preferences" />
    </li>
    <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab2">
      <gsmsg:write key="cmn.detail.setting" />
    </li>
    <li class="tabHeader_space border_bottom_none"></li>
  </ul>

  <table id="tab1_table" class="table-left w100 mt0">
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.101" /></span></td>
      <td class="w75">
        <logic:equal name="wml150knForm" property="wml150acntMakeKbn" value="<%= String.valueOf(GSConstWebmail.KANRI_USER_NO) %>">
          <gsmsg:write key="wml.31" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150acntMakeKbn" value="<%= String.valueOf(GSConstWebmail.KANRI_USER_ONLY) %>">
          <gsmsg:write key="wml.70" />
        </logic:equal>
      </td>
    </tr>

<logic:equal name="wml150knForm" property="wml150acntMakeKbn" value="<%= String.valueOf(GSConstWebmail.KANRI_USER_ONLY) %>">
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.253" /></th>
      <td class="w75 txt_l">
        <logic:equal name="wml150knForm" property="wml150settingServer" value="<%= String.valueOf(GSConstWebmail.WAD_SETTING_SERVER_YES) %>">
          <gsmsg:write key="cmn.permit" />
        </logic:equal>
        <logic:notEqual name="wml150knForm" property="wml150settingServer" value="<%= String.valueOf(GSConstWebmail.WAD_SETTING_SERVER_YES) %>">
          <gsmsg:write key="cmn.not.permit" />
        </logic:notEqual>
      </td>
    </tr>
</logic:equal>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="cmn.format." /></th>
      <td class="w75">
        <logic:equal name="wml150knForm" property="wml150acntSendFormat" value="<%= String.valueOf(GSConstWebmail.ACNT_SENDFORMAT_NOSET) %>">
          <gsmsg:write key="wml.31" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150acntSendFormat" value="<%= String.valueOf(GSConstWebmail.ACNT_SENDFORMAT_TEXT) %>">
          <gsmsg:write key="wml.104" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150acntSendFormat" value="<%= String.valueOf(GSConstWebmail.ACNT_SENDFORMAT_HTML) %>">
          <gsmsg:write key="wml.109" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.23" /></th>
      <td class="w75">
        <logic:equal name="wml150knForm" property="wml150acntLogRegist" value="<%= String.valueOf(GSConstWebmail.ACNT_LOG_REGIST_REGIST) %>">
          <gsmsg:write key="wml.12" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150acntLogRegist" value="<%= String.valueOf(GSConstWebmail.ACNT_LOG_REGIST_NOTREGIST) %>">
          <gsmsg:write key="cmn.dont.entry" />
        </logic:equal>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.81" /></th>
      <td class="w75 txt_l">
        <logic:notEmpty name="wml150knForm" property="wml150receiveServer">
          <bean:write name="wml150knForm" property="wml150receiveServer" />&nbsp;
        </logic:notEmpty>
        <logic:notEmpty name="wml150knForm" property="wml150receiveServerPort">
          <gsmsg:write key="cmn.port.number" />:<bean:write name="wml150knForm" property="wml150receiveServerPort" />
        </logic:notEmpty>
        <br><gsmsg:write key="cmn.ango" />:
        <bean:write name="wml150knForm" property="wml150knReceiveEncrypt" />
    </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.80" /></th>
      <td class="w75 txt_l">
        <logic:notEmpty name="wml150knForm" property="wml150sendServer">
          <span class="mr5"><bean:write name="wml150knForm" property="wml150sendServer" /></span>
        </logic:notEmpty>
        <logic:notEmpty name="wml150knForm" property="wml150sendServerPort">
          <gsmsg:write key="cmn.port.number" />:<bean:write name="wml150knForm" property="wml150sendServerPort" />
        </logic:notEmpty>
        <br><gsmsg:write key="cmn.ango" />:
        <bean:write name="wml150knForm" property="wml150knSendEncrypt" />
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.246" /></th>
      <td class="w75 txt_l">
        <logic:equal name="wml150knForm" property="wml150sendMaxSizeKbn" value="1">
          <gsmsg:write key="wml.32" />&nbsp;<bean:write name="wml150knForm" property="wml150sendMaxSize" />MB
        </logic:equal>
        <logic:notEqual name="wml150knForm" property="wml150sendMaxSizeKbn" value="1">
          <gsmsg:write key="wml.31" />
        </logic:notEqual>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.wml150.13" /></th>
      <td class="w75 txt_l">
        <logic:equal name="wml150knForm" property="wml150bcc" value="1">
          <span class="mr10"><gsmsg:write key="cmn.coerced" /></span>
          <gsmsg:write key="cmn.threshold" />:&nbsp;<bean:write name="wml150Form" property="wml150bccThreshold" /><gsmsg:write key="cmn.number" />
        </logic:equal>
        <logic:notEqual name="wml150knForm" property="wml150bcc" value="1">
           <gsmsg:write key="man.no.limit" />
        </logic:notEqual>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="cmn.proxyuser" /></th>
      <td class="w75 txt_l">
        <logic:equal name="wml150knForm" property="wml150proxyUser" value="<%= String.valueOf(GSConstWebmail.WAD_PROXY_USER_NO) %>">
          <gsmsg:write key="cmn.not.permit" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150proxyUser" value="<%= String.valueOf(GSConstWebmail.WAD_PROXY_USER_YES) %>">
          <gsmsg:write key="cmn.permit" />
        </logic:equal>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.wml150.10" /></th>
      <td class="w75 txt_l">
        <bean:define id="fwLimitKbn" name="wml150knForm" property="wml150FwLimit" type="java.lang.Integer" />
        <% if (fwLimitKbn.intValue() == 1) { %>
          <gsmsg:write key="wml.transfer.limit.02" />
          <br>
          <logic:iterate id="fwLimitAddress" name="wml150knForm" property="wml150knFwLimitText">
            <br><span class="ml10"><bean:write name="fwLimitAddress" /></span>
          </logic:iterate>
        <% } else if (fwLimitKbn.intValue() == 2) { %>
          <gsmsg:write key="wml.transfer.limit.03" />
          <logic:equal name="wml150knForm" property="wml150FwLimitDelete" value="1">
            <div class="mt5">
             <span class="ml5"><gsmsg:write key="wml.transfer.limit.04" /></span>
            </div>
          </logic:equal>
        <% } else { %>
          <gsmsg:write key="wml.transfer.limit.01" />
        <% } %>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.wml150.18" /></th>
      <td class="w75 txt_l">
        <logic:equal name="wml150knForm" property="wml150linkLimit" value="<%= String.valueOf(GSConstWebmail.WAD_LINK_UNLIMITED) %>">
          <gsmsg:write key="wml.31" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150linkLimit" value="<%= String.valueOf(GSConstWebmail.WAD_LINK_LIMITED) %>">
          <gsmsg:write key="wml.32" />
        </logic:equal>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.wml150.20" /></th>
      <td class="w75 txt_l">
        <logic:equal name="wml150knForm" property="wml150TldLimit" value="<%= String.valueOf(GSConstWebmail.WAD_TLD_UNLIMITED) %>">
          <gsmsg:write key="wml.31" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150TldLimit" value="<%= String.valueOf(GSConstWebmail.WAD_TLD_LIMITED) %>">
          <gsmsg:write key="wml.32" />
        </logic:equal>
      </td>
    </tr>

  </table>

  <table id="tab2_table" class="table-left display_none w100 mt0">
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.permit.select" /></th>
      <td class="w75">
        <logic:equal name="wml150knForm" property="wml150permitKbn" value="<%= String.valueOf(GSConstWebmail.PERMIT_ADMIN) %>">
          <gsmsg:write key="cmn.set.the.admin" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150permitKbn" value="<%= String.valueOf(GSConstWebmail.PERMIT_ACCOUNT) %>">
          <gsmsg:write key="cmn.set.eachaccount" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.87" /></th>
      <td class="w75">
        <logic:equal name="wml150knForm" property="wml150diskSize" value="<%= String.valueOf(GSConstWebmail.WAC_DISK_UNLIMITED) %>">
          <gsmsg:write key="wml.31" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150diskSize" value="<%= String.valueOf(GSConstWebmail.WAC_DISK_LIMIT) %>">
          <gsmsg:write key="wml.32" />&nbsp;<bean:write name="wml150knForm" property="wml150diskSizeLimit" />MB
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150diskSizeComp" value="true">
          <span class="ml5"><gsmsg:write key="cmn.force" /></span>
        </logic:equal>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.wml150.15" /></th>
      <td class="w75">
        <logic:equal name="wml150knForm" property="wml150warnDisk" value="<%= String.valueOf(GSConstWebmail.WAD_WARN_DISK_NO) %>">
          <gsmsg:write key="cmn.notset" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150warnDisk" value="<%= String.valueOf(GSConstWebmail.WAD_WARN_DISK_YES) %>">
          <spa class="mr10"><gsmsg:write key="cmn.warning2" /></span>
          <spa class="mr5"><gsmsg:write key="cmn.threshold" /></span>
          <bean:write name="wml150knForm" property="wml150warnDiskThreshold" />%
        </logic:equal>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.36" /></th>
      <td class="w75">
        <logic:equal name="wml150knForm" property="wml150delReceive" value="<%= String.valueOf(GSConstWebmail.WAC_DELRECEIVE_YES) %>">
          <gsmsg:write key="wml.60" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150delReceive" value="<%= String.valueOf(GSConstWebmail.WAC_DELRECEIVE_NO) %>">
          <gsmsg:write key="cmn.dont.delete" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.50" /></th>
      <td class="w75">
        <logic:equal name="wml150knForm" property="wml150autoResv" value="<%= String.valueOf(GSConstWebmail.MAIL_AUTO_RSV_ON) %>">
          <gsmsg:write key="wml.48" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150autoResv" value="<%= String.valueOf(GSConstWebmail.MAIL_AUTO_RSV_OFF) %>">
          <gsmsg:write key="wml.49" />
        </logic:equal>
      </td>
    </tr>

    <logic:equal name="wml150knForm" property="wml150autoResv" value="<%= String.valueOf(GSConstWebmail.MAIL_AUTO_RSV_ON) %>">
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.auto.receive.time" /></th>
      <td class="w75">
        <bean:write name="wml150knForm" property="wml150AutoReceiveTime" /><gsmsg:write key="cmn.minute" />
      </td>
    </tr>
    </logic:equal>

    <tr id="checkAddress">
      <th class="w25 no_w"><gsmsg:write key="wml.238" /></th>
      <td class="w75 txt_l">
        <logic:equal name="wml150knForm" property="wml150checkAddress" value="1">
          <gsmsg:write key="cmn.check.2" />
        </logic:equal>
        <logic:notEqual name="wml150knForm" property="wml150checkAddress" value="1">
          <gsmsg:write key="cmn.notcheck" />
        </logic:notEqual>
      </td>
    </tr>

    <tr id="checkFile">
      <th class="w25 no_w"><gsmsg:write key="wml.239" /></th>
      <td class="w75 txt_l">
        <logic:equal name="wml150knForm" property="wml150checkFile" value="1">
          <gsmsg:write key="cmn.check.2" />
        </logic:equal>
        <logic:notEqual name="wml150knForm" property="wml150checkFile" value="1">
          <gsmsg:write key="cmn.notcheck" />
        </logic:notEqual>
      </td>
    </tr>

    <tr id="compressFile">
      <th class="w25 no_w"><gsmsg:write key="wml.240" /></th>
      <td class="w75 txt_l">
        <logic:equal name="wml150knForm" property="wml150compressFile" value="1">
          <gsmsg:write key="cmn.compress" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150compressFile" value="0">
           <gsmsg:write key="cmn.not.compress" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150compressFile" value="2">
           <gsmsg:write key="cmn.setting.from.screen" />
        </logic:equal>
      </td>
    </tr>

    <logic:equal name="wml150knForm" property="wml150compressFile" value="2">
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.240" />&nbsp;<gsmsg:write key="ntp.10"/></th>
      <td class="w75 txt_l">
          <logic:equal name="wml150knForm" property="wml150compressFileDef" value="1">
           <gsmsg:write key="cmn.compress" />
          </logic:equal>
          <logic:notEqual name="wml150knForm" property="wml150compressFileDef" value="1">
            <gsmsg:write key="cmn.not.compress" />
          </logic:notEqual>
      </td>
    </tr>
    </logic:equal>

    <tr id="timeSent">
      <th class="w25 no_w"><gsmsg:write key="wml.241" /></th>
      <td class="w75 txt_l">
        <logic:equal name="wml150knForm" property="wml150timeSent" value="1">
          <gsmsg:write key="cmn.effective" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150timeSent" value="0">
           <gsmsg:write key="cmn.invalid" />
        </logic:equal>
        <logic:equal name="wml150knForm" property="wml150timeSent" value="2">
           <gsmsg:write key="cmn.setting.from.screen" />
        </logic:equal>
      </td>
    </tr>

    <logic:equal name="wml150knForm" property="wml150timeSent" value="2">
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.241" />&nbsp;<gsmsg:write key="ntp.10"/></th>
      <td class="w75 txt_l">
        <logic:equal name="wml150knForm" property="wml150timeSentDef" value="1">
          <gsmsg:write key="wml.241" />
        </logic:equal>
        <logic:notEqual name="wml150knForm" property="wml150timeSentDef" value="1">
          <gsmsg:write key="wml.276" />
        </logic:notEqual>
      </td>
    </tr>
    </logic:equal>
  </table>


  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
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