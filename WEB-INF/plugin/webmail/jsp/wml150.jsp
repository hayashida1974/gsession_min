<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstWebmail" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.wml.wml150.Wml150Form" %>
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

  <title>GROUPSESSION <gsmsg:write key="wml.wml020.07" /></title>
</head>

<body onload="wml150Init(<bean:write name="wml150Form" property="wml150acntMakeKbn" />, <bean:write name="wml150Form" property="wml150elementKbn" />, <bean:write name="wml150Form" property="wml150diskSize" />, <bean:write name="wml150Form" property="wml150autoResv" />, <bean:write name="wml150Form" property="wml150sendMaxSizeKbn" />, <bean:write name="wml150Form" property="wml150FwLimit" />, <bean:write name="wml150Form" property="wml150FwLimitCheckFlg" />, <bean:write name="wml150Form" property="wml150bcc" />, <bean:write name="wml150Form" property="wml150warnDisk" />, <bean:write name="wml150Form" property="wml150TldLimit" />);changeCompressFile(<bean:write name="wml150Form" property="wml150compressFile" />);changeTimesent(<bean:write name="wml150Form" property="wml150timeSent" />);">

<html:form action="/webmail/wml150">

<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />

<html:hidden property="wmlViewAccount" />
<html:hidden property="wml150initFlg" />
<html:hidden property="wml150elementKbn" />
<html:hidden property="wml150svFwLimitText" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>

    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><gsmsg:write key="cmn.preferences" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('admTool');">
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
      <th class="w25"><gsmsg:write key="wml.101" /></th>
      <td class="w75">
        <div class="mb20">
          <gsmsg:write key="wml.wml150.03" />
        </div>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150acntMakeKbn" value="<%= String.valueOf(GSConstWebmail.KANRI_USER_NO) %>" styleId="acntMake1" onclick="changeAcntMakeKbn(0);" /><label for="acntMake1"><gsmsg:write key="wml.31" /></label>
          <html:radio name="wml150Form" property="wml150acntMakeKbn" value="<%= String.valueOf(GSConstWebmail.KANRI_USER_ONLY) %>" styleId="acntMake2" styleClass="ml10" onclick="changeAcntMakeKbn(1);" /><label for="acntMake2"><gsmsg:write key="wml.70" /></label>
        </span>
      </td>
    </tr>

    <tr id="settingServerArea">
      <th class="w25"><gsmsg:write key="wml.253" /></th>
      <td class="w75">
        <div class="mb20">
          <gsmsg:write key="wml.wml150.17" />
        </div>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150settingServer" value="<%= String.valueOf(GSConstWebmail.WAD_SETTING_SERVER_NO) %>" styleId="settingServer1" /><label for="settingServer1"><gsmsg:write key="cmn.not.permit" /></label>
          <html:radio name="wml150Form" property="wml150settingServer" value="<%= String.valueOf(GSConstWebmail.WAD_SETTING_SERVER_YES) %>" styleId="settingServer2" styleClass="ml10" /><label for="settingServer2"><gsmsg:write key="cmn.permit" /></label>
        </span>
      </td>
    </tr>

    <tr>
      <th class="w25"><gsmsg:write key="cmn.format." /></th>
      <td>
        <div class="mb20">
          <gsmsg:write key="wml.wml150.01" />
        </div>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150acntSendFormat" value="<%= String.valueOf(GSConstWebmail.ACNT_SENDFORMAT_NOSET) %>" styleId="acntSendFormat1" /><label for="acntSendFormat1"><gsmsg:write key="wml.31" /></label>
          <html:radio name="wml150Form" property="wml150acntSendFormat" value="<%= String.valueOf(GSConstWebmail.ACNT_SENDFORMAT_TEXT) %>" styleId="acntSendFormat2" styleClass="ml10" /><label for="acntSendFormat2"><gsmsg:write key="wml.104" /></label>
          <html:radio name="wml150Form" property="wml150acntSendFormat" value="<%= String.valueOf(GSConstWebmail.ACNT_SENDFORMAT_HTML) %>" styleId="acntSendFormat3" styleClass="ml10" /><label for="acntSendFormat3"><gsmsg:write key="wml.109" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w25"><gsmsg:write key="wml.23" /></th>
      <td>
        <div class="mb20">
          <gsmsg:write key="wml.wml150.02" />
        </div>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150acntLogRegist" value="<%= String.valueOf(GSConstWebmail.ACNT_SENDFORMAT_NOSET) %>" styleId="acntLogRegist1" /><label for="acntLogRegist1"><gsmsg:write key="wml.12" /></label>
          <html:radio name="wml150Form" property="wml150acntLogRegist" value="<%= String.valueOf(GSConstWebmail.ACNT_SENDFORMAT_TEXT) %>" styleId="acntLogRegist2" styleClass="ml10" /><label for="acntLogRegist2"><gsmsg:write key="cmn.dont.entry" /></label>
        </span>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.154" /></th>
      <td>
        <html:text name="wml150Form" property="wml150receiveServer" styleClass="wp273" maxlength="100" />
        <gsmsg:write key="cmn.port.number" />
        <html:text name="wml150Form" property="wml150receiveServerPort" styleClass="wp70" maxlength="5" />
        <div class="mt5">
          <gsmsg:write key="cmn.ango" />:
          <html:select name="wml150Form" property="wml150receiveServerEncrypt">
            <html:optionsCollection name="wml150Form" property="wml150AngoProtocolCombo" value="value" label="label" />
          </html:select>
        </div>
        <span class="cl_fontWarn fs_12"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></span>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.80" /></th>
      <td>
        <html:text name="wml150Form" property="wml150sendServer" styleClass="wp273" maxlength="100" />
        <gsmsg:write key="cmn.port.number" />
        <html:text name="wml150Form" property="wml150sendServerPort" styleClass="wp70" maxlength="5" />
        <div class="mt5">
          <gsmsg:write key="cmn.ango" />:
          <html:select name="wml150Form" property="wml150sendServerEncrypt" styleClass="hp24">
            <html:optionsCollection name="wml150Form" property="wml150AngoProtocolCombo" value="value" label="label" />
          </html:select>
        </div>
        <span class="cl_fontWarn fs_12"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></span>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.246" /></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150sendMaxSizeKbn" styleId="sendMaxSize1" value="0" onclick="changeSendMaxSize(0);" /><label for="sendMaxSize1"><gsmsg:write key="wml.31" /></label>
          <html:radio name="wml150Form" property="wml150sendMaxSizeKbn" styleId="sendMaxSize2" styleClass="ml10" value="1" onclick="changeSendMaxSize(1);" /><label for="sendMaxSize2"><gsmsg:write key="wml.32" /></label>
          <span id="sendMaxSize" class="ml10"><html:text name="wml150Form" property="wml150sendMaxSize" styleClass="wp80 mr5" maxlength="6" />MB</span>
        </span>
        <div id="sendMaxSizeCmt" class="fs_12">
          <gsmsg:write key="cmn.comments" /> <gsmsg:write key="wml.wml150.05" />
          <br><gsmsg:write key="cmn.comments" /> <gsmsg:write key="wml.wml150.06" />
        </div>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.wml150.13" /></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150bcc" styleId="bcc1" value="0" onclick="wml150changeBcc(0);" /><label for="bcc1"><gsmsg:write key="man.no.limit" /></label>
          <html:radio name="wml150Form" property="wml150bcc" styleId="bcc2" styleClass="ml10" value="1" onclick="wml150changeBcc(1);" /><label for="bcc2" class="mr10"><gsmsg:write key="cmn.coerced" /></label>

          <span id="bccThresholdArea" class="verAlignMid">
            <gsmsg:write key="cmn.threshold" />:
            <html:select name="wml150Form" property="wml150bccThreshold" styleClass="ml10 mr5">
              <html:optionsCollection  name="wml150Form" property="bccThresholdList" value="value" label="label" />
            </html:select>
            <gsmsg:write key="cmn.number" />
          </span>
        </span>
        <div id="bccThresholdCmtArea" class="cl_fontWarn fs_12">
          <gsmsg:write key="cmn.comments" /><gsmsg:write key="wml.wml150.14" />
        </div>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="cmn.proxyuser" /></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150proxyUser" value="<%= String.valueOf(GSConstWebmail.WAD_PROXY_USER_NO) %>" styleId="proxyUser1" /><label for="proxyUser1"><gsmsg:write key="cmn.not.permit" /></label>
          <html:radio name="wml150Form" property="wml150proxyUser" value="<%= String.valueOf(GSConstWebmail.WAD_PROXY_USER_YES) %>" styleId="proxyUser2" styleClass="ml10" /><label for="proxyUser2"><gsmsg:write key="cmn.permit" /></label>
        </span>
      </td>
    </tr>

    <tr id="fwLimitLine">
      <th class="w25 no_w"><gsmsg:write key="wml.wml150.10" /></th>
      <td>
        <gsmsg:write key="wml.wml150.07" />
        <br><br>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150FwLimit" styleId="wml150FwLimit0" value="0" onclick="changeFwLimit(0);"/><label for="wml150FwLimit0"><gsmsg:write key="wml.transfer.limit.01" /></label>
          <html:radio name="wml150Form" property="wml150FwLimit" styleId="wml150FwLimit1" styleClass="ml10" value="1" onclick="changeFwLimit(1);"/><label for="wml150FwLimit1"><gsmsg:write key="wml.transfer.limit.02" /></label>
          <html:radio name="wml150Form" property="wml150FwLimit" styleId="wml150FwLimit2" styleClass="ml10" value="2" onclick="changeFwLimit(2);"/><label for="wml150FwLimit2"><gsmsg:write key="wml.transfer.limit.03" /></label>
        </span>

        <div id="fwLimitDeleteArea" class="mt5">
          <span class="verAlignMid">
            <html:checkbox styleId="fwLimitDelete" name="wml150Form" property="wml150FwLimitDelete" value="1" />
            <label for="fwLimitDelete"><gsmsg:write key="wml.transfer.limit.04" /></label>
          </span>
        </div>

        <div id="fwLimitArea" class="mt10">
          <gsmsg:write key="wml.wml150.08" />
          <br><span class="cl_fontWarn"><gsmsg:write key="sml.sml110.03" /></span>
          <br>
          <div class="w100 txt_c">
            <html:textarea name="wml150Form" property="wml150FwLimitText" styleClass="w99" rows="6"></html:textarea>
          </div>

          <div class="w100 txt_c mt5">
            <button class="baseBtn" name="check" onclick="buttonPush('filterCheck');" value="<gsmsg:write key="wml.wml150.11" />"><gsmsg:write key="wml.wml150.11" /></button>
          </div>

        <logic:notEmpty name="wml150Form" property="fwCheckList">
          <div class="mt10"><gsmsg:write key="wml.wml150.12" /></div>
          <table class="table-top w100 mt0">
            <tr>
              <th class="w30 txt_c"><gsmsg:write key="wml.96" /></th>
              <th class="w35 txt_c"><gsmsg:write key="wml.84" /></th>
              <th class="w20 txt_c"><gsmsg:write key="wml.201" /></th>
              <th class="w15 txt_c"><gsmsg:write key="cmn.user" /></th>
            </tr>

            <logic:iterate id="filterData" name="wml150Form" property="fwCheckList" indexId="idx">
              <tr>
              <td><bean:write name="filterData" property="wacName" /></td>
              <td><bean:write name="filterData" property="filterName" /></td>
              <td><bean:write name="filterData" property="fwAddress" /></td>
              <td><bean:write name="filterData" property="userNameSei" /><span class="ml5"><bean:write name="filterData" property="userNameMei" /></span></td>
              </tr>
            </logic:iterate>

          </table>
        </logic:notEmpty>

        </div>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.wml150.18" /></th>
      <td>
        <div class="mb20">
          <gsmsg:write key="wml.wml150.19" />
        </div>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150linkLimit" value="<%= String.valueOf(GSConstWebmail.WAD_LINK_UNLIMITED) %>" styleId="linkLimit1" /><label for="linkLimit1"><gsmsg:write key="wml.31" /></label>
          <html:radio name="wml150Form" property="wml150linkLimit" value="<%= String.valueOf(GSConstWebmail.WAD_LINK_LIMITED) %>" styleId="linkLimit2" styleClass="ml10" /><label for="linkLimit2"><gsmsg:write key="wml.32" /></label>
        </span>
      </td>
    </tr>

    <tr id="tldLimitLine">
      <th class="w25 no_w"><gsmsg:write key="wml.wml150.20" /></th>
      <td>
        <div class="mb20">
          <gsmsg:write key="wml.wml150.21" />
        </div>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150TldLimit" styleId="wml150TldLimit0" value="0" onclick="changeTldLimit(0);"/><label for="wml150TldLimit0"><gsmsg:write key="wml.31" /></label>
          <html:radio name="wml150Form" property="wml150TldLimit" styleId="wml150TldLimit1" styleClass="ml10" value="1" onclick="changeTldLimit(1);"/><label for="wml150TldLimit1"><gsmsg:write key="wml.32" /></label>
        </span>

        <div id="tldLimitArea" class="mt10">
          <gsmsg:write key="wml.wml150.22" />
          <div class="cl_fontWarn"><gsmsg:write key="sml.sml110.03" /></div>
          <html:textarea name="wml150Form" property="wml150TldLimitText" styleClass="wp600" rows="6"></html:textarea>
        </div>
      </td>
    </tr>

  </table>

  <table id="tab2_table" class="table-left display_none w100 mt0">
    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.permit.select" /></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150permitKbn" styleId="wml150permitKbn1" value="0" onclick="lmtEnableDisable();" /><label for="wml150permitKbn1"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="wml150Form" property="wml150permitKbn" styleId="wml150permitKbn2" styleClass="ml10" value="1" onclick="lmtEnableDisable();" /><label for="wml150permitKbn2"><gsmsg:write key="cmn.set.eachaccount" /></label>
        </span>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.87" /></span></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150diskSize" styleId="disk1" value="0" onclick="changeInputDiskSize(0);" /><label for="disk1"><gsmsg:write key="wml.31" /></label>
          <html:radio name="wml150Form" property="wml150diskSize" styleId="disk2" styleClass="ml10" value="1" onclick="changeInputDiskSize(1);" /><label for="disk2"><gsmsg:write key="wml.32" /></label>
          <span id="inputDiskSize"><html:text name="wml150Form" property="wml150diskSizeLimit" styleClass="wp80 ml10 mr5" maxlength="6" />MB</span>
          <span id="inputDiskSize2" class="ml10 verAlignMid"><html:checkbox name="wml150Form" property="wml150diskSizeComp" styleId="sizeComp" styleClass="ml10" onclick="changeDefLabel();"/><label for="sizeComp"><gsmsg:write key="cmn.force" /></label></span>
        </span>
        <div id="lmtinput1" class="fs_12 cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></div>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.wml150.15" /></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150warnDisk" styleId="warnDisk1" value="0" onclick="wml150changeWarnDisk(0);" /><label for="warnDisk1"><gsmsg:write key="cmn.notset" /></label>
          <html:radio name="wml150Form" property="wml150warnDisk" styleId="warnDisk2" styleClass="ml10" value="1" onclick="wml150changeWarnDisk(1);" /><label for="warnDisk2" class="mr10"><gsmsg:write key="cmn.warning2" /></label>

          <span id="warnDiskThresholdArea" class="verAlignMid">
            <gsmsg:write key="cmn.threshold" />
            <html:select name="wml150Form" property="wml150warnDiskThreshold" styleClass="ml10 mr5">
              <html:optionsCollection  name="wml150Form" property="warnDiskThresholdList" value="value" label="label" />
            </html:select>%
          </span>
        </span>
        <div id="warnDiskThresholdCmtArea" class="cl_fontWarn fs_12">
          <gsmsg:write key="cmn.comments" /><gsmsg:write key="wml.wml150.16" />
        </div>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.36" /></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150delReceive" styleId="delReceive1" value="1" /><label for="delReceive1"><gsmsg:write key="wml.60" /></label>
          <html:radio name="wml150Form" property="wml150delReceive" styleId="delReceive2" styleClass="ml10" value="0" /><label for="delReceive2"><gsmsg:write key="cmn.dont.delete" /></label>
        </span>
        <div id="lmtinput2" class="fs_12 cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></div>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.50" /></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150autoResv" styleId="autoResv1" value="1" onclick="changeAutoRsvTime(1);" /><label for="autoResv1"><gsmsg:write key="wml.48" /></label>
          <html:radio name="wml150Form" property="wml150autoResv" styleId="autoResv2" styleClass="ml10" value="0" onclick="changeAutoRsvTime(0);" /><label for="autoResv2"><gsmsg:write key="wml.49" /></label>
        </span>
        <div id="lmtinput3" class="fs_12 cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></div>
      </td>
    </tr>

    <tr id="autoRsvTime">
      <th class="w25 no_w"><gsmsg:write key="wml.auto.receive.time" /></th>
      <td>
        <html:select property="wml150AutoReceiveTime">
          <html:optionsCollection name="wml150Form" property="wml150AutoRsvKeyLabel" value="value" label="label" />
        </html:select>
        <div id="lmtinput4" class="fs_12 cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></div>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.238" /></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150checkAddress" styleId="checkAddress1" value="1" /><label for="checkAddress1"><gsmsg:write key="cmn.check.2" /></label>
          <html:radio name="wml150Form" property="wml150checkAddress" styleId="checkAddress2" value="0" styleClass="ml10" /><label for="checkAddress2"><gsmsg:write key="cmn.notcheck" /></label>
        </span>
        <div id="lmtinput5" class="fs_12 cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></div>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.239" /></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150checkFile" styleId="checkFile1" value="1" /><label for="checkFile1"><gsmsg:write key="cmn.check.2" /></label>
          <html:radio name="wml150Form" property="wml150checkFile" styleId="checkFile2" value="0" styleClass="ml10" /><label for="checkFile2"><gsmsg:write key="cmn.notcheck" /></label>
        </span>
        <div id="lmtinput6" class="fs_12 cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></div>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.240" /></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150compressFile" styleId="compressFile1" value="1" onclick="changeCompressFile(1);" /><label for="compressFile1"><gsmsg:write key="cmn.compress" /></label>
          <html:radio name="wml150Form" property="wml150compressFile" styleId="compressFile2" value="0" styleClass="ml10" onclick="changeCompressFile(0);" /><label for="compressFile2"><gsmsg:write key="cmn.not.compress" /></label>
          <html:radio name="wml150Form" property="wml150compressFile" styleId="compressFile3" value="2" styleClass="ml10" onclick="changeCompressFile(2);" /><label for="compressFile3"><gsmsg:write key="cmn.setting.from.screen" /></label>
        </span>
        <div id="lmtinput7" class="fs_12 cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></div>
      </td>
    </tr>

    <tr id="compressDefArea">
      <th class="w25 no_w"><gsmsg:write key="wml.240" /><span class="ml5"><gsmsg:write key="ntp.10"/></span></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150compressFileDef" styleId="compressFileDef1" value="1" /><label for="compressFileDef1" class="mr10"><gsmsg:write key="cmn.compress" /></label>
          <html:radio name="wml150Form" property="wml150compressFileDef" styleId="compressFileDef2" value="0" /><label for="compressFileDef2"><gsmsg:write key="cmn.not.compress" /></label>
        </span>
        <div id="lmtinput8" class="fs_12 cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></div>
      </td>
    </tr>

    <tr>
      <th class="w25 no_w"><gsmsg:write key="wml.241" /></tn>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150timeSent" styleId="timeSent1" value="1" onclick="changeTimesent(1);" /><label for="timeSent1"><gsmsg:write key="cmn.effective" /></label>
          <html:radio name="wml150Form" property="wml150timeSent" styleId="timeSent2" value="0" styleClass="ml10" onclick="changeTimesent(0);" /><label for="timeSent2"><gsmsg:write key="cmn.invalid" /></label>
          <html:radio name="wml150Form" property="wml150timeSent" styleId="timeSent3" value="2" styleClass="ml10" onclick="changeTimesent(2);" /><label for="timeSent3"><gsmsg:write key="cmn.setting.from.screen" /></label>
        </span>
        <div id="lmtinput9" class="fs_12 cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></div>
      </td>
    </tr>

    <tr id="timeSentDefArea">
      <th class="w25 no_w"><gsmsg:write key="wml.241" /><span class="ml5"><gsmsg:write key="ntp.10"/></span></th>
      <td>
        <span class="verAlignMid">
          <html:radio name="wml150Form" property="wml150timeSentDef" styleId="timeSentDef1" value="1" /><label for="timeSentDef1"><gsmsg:write key="wml.241" /></label>
          <html:radio name="wml150Form" property="wml150timeSentDef" styleId="timeSentDef2" value="0" styleClass="ml10" /><label for="timeSentDef2"><gsmsg:write key="wml.276" /></label>
        </span>
        <div id="lmtinput10" class="fs_12 cl_fontWarn"><gsmsg:write key="cmn.comments" /><gsmsg:write key="cmn.view.account.defaultset" /></div>
      </td>
    </tr>
  </table>


  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('admTool');">
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