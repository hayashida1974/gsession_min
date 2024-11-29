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

<title>GROUPSESSION <gsmsg:write key="main.man002.66" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery.jcarousel.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man230.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/main/man230">

<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man002.66" />
    </li>
    <li>
      <div>
        <logic:equal name="man230Form" property="man230SupportLimitFlag" value="0">
          <button type="button" value="<gsmsg:write key="cmn.reports" />" class="baseBtn" onClick="sendSystemInfo();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei.png" alt="<gsmsg:write key="cmn.reports" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_sinsei.png" alt="<gsmsg:write key="cmn.reports" />">
            <gsmsg:write key="cmn.reports" />
          </button>
        </logic:equal>
        <button type="button" value="<gsmsg:write key="cmn.reload" />" class="baseBtn" onClick="buttonPush('reload');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
          <gsmsg:write key="cmn.reload" />
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
<logic:equal name="man230Form" property="man230SupportLimitFlag" value="0">
  <div class="txt_l">
    <gsmsg:write key="main.man230.8" />
  </div>
</logic:equal>
<table class="table-left">

<!-- Support License -->
 <tr>
   <td class="table_title-color" colspan="2"><gsmsg:write key="cmn.license.info" /></td>
 </tr>
 <tr>
   <th><gsmsg:write key="cmn.serial.number" /></th>
   <td>
     <logic:notEmpty name="man230Form" property="gsUid">
       <bean:write name="man230Form" property="gsUid" />
     </logic:notEmpty>
   </td>
 </tr>

 <logic:notEmpty name="man230Form" property="licenseId">
 <tr>
   <th><gsmsg:write key="main.man150.3" /></th>
   <td>
     <bean:write name="man230Form" property="licenseId" />
   </td>
 </tr>
 </logic:notEmpty>

 <logic:notEmpty name="man230Form" property="licenseCom">
 <tr>
   <th><gsmsg:write key="cmn.company.name" /></th>
   <td>
     <bean:write name="man230Form" property="licenseCom" />
   </td>
 </tr>
 </logic:notEmpty>

 <logic:iterate id="plugin" name="man230Form" property="pluginList" scope="request" indexId="cnt">
 <tr>
   <th><bean:write name="plugin" property="pluginName" /></th>
   <td><gsmsg:write key="cmn.period2" />&nbsp;<bean:write name="plugin" property="licenseLimit" /></td>
 </tr>
 </logic:iterate>

 <tr>
 <bean:define id="manCnt" name="man230Form" property="userCount" type="java.lang.String" />
   <th><gsmsg:write key="anp.anp110.02" /><gsmsg:write key="rss.25" /></th>
   <td><gsmsg:write key="bmk.23" arg0="<%= manCnt %>" /></td>
 </tr>
 <!-- サーバー情報  -->
 <tr>
   <td class="table_title-color" colspan="2"><gsmsg:write key="cmn.main.server.info" /></td>
 </tr>
 <tr>
   <th>Version</th>
   <td><%= jp.groupsession.v2.cmn.GSConst.VERSION %>&nbsp;(<bean:write name="man230Form" property="man230GSVersionDB" />)</td>
 </tr>

 <tr>
   <th>OS</th>
   <td><bean:write name="man230Form" property="man230Os" /></td>
 </tr>

 <tr>
   <th><gsmsg:write key="main.man230.12" /></th>
   <td><bean:write name="man230Form" property="man230CpuProcesser" /></td>
 </tr>

 <tr>
   <th><gsmsg:write key="main.man230.9" /></th>
   <td><bean:write name="man230Form" property="man230CpuCoreNum" />Core</td>
 </tr>

 <tr>
   <th><gsmsg:write key="main.man230.13" /></th>
   <td><bean:write name="man230Form" property="man230MemSize" /></td>
 </tr>

 <tr>
   <th><gsmsg:write key="main.man230.14" /></th>
   <td>
     <table>
       <logic:iterate id="lineList" name="man230Form" property="man230DiskInfo" indexId="idx">
         <% String diskTdClass = "bor1"; %>
         <logic:equal name="idx" value="0">
         <tr class="bgC_header2">
         <% diskTdClass += " no_w"; %>
         </logic:equal>
         <logic:notEqual name="idx" value="0">
         <tr>
         </logic:notEqual>
           <logic:iterate id="elementValue" name="lineList">
             <td class="<%= diskTdClass %>">
               <bean:write name="elementValue" />
             </td>
           </logic:iterate>
         </tr>
       </logic:iterate>
     </table>
   </td>
 </tr>
 
 <logic:equal name="man230Form" property="man230DbKbn" value="0">
 <tr>
   <th><gsmsg:write key="main.man230.15" /></th>
   <td>
     <table class="wp300">
       <tr class="bgC_header2">
         <td class="bor1 no_w w40"><gsmsg:write key="cmn.folder"/></td>
         <td class="bor1 no_w w60"><gsmsg:write key="cmn.size"/></td>
       </tr>
       <logic:iterate id="lineList" name="man230Form" property="man230dataFolderInfo">
         <tr>
           <% String sizeClass = ""; %>
           <logic:iterate id="elementValue" name="lineList" indexId="idx">
             <% if (idx%2 == 1) {sizeClass="txt_r";} %>
             <td class="bor1 <%= sizeClass %>">
               <bean:write name="elementValue" />
             </td>
           </logic:iterate>
         </tr>
       </logic:iterate>
     </table>
   </td>
 </tr>
 </logic:equal>

 <tr>
   <th><gsmsg:write key="main.man230.10" /></th>
   <td><bean:write name="man230Form" property="man230JvmBitMode" /></td>
 </tr>

 <tr>
   <th><gsmsg:write key="main.man230.2" /></th>
  <td><bean:write name="man230Form" property="man230J2ee" /></td>
 </tr>

 <tr>
   <th>Java</th>
   <td><bean:write name="man230Form" property="man230Java" /></td>
 </tr>

 <tr>
   <th><gsmsg:write key="main.man230.4" /></th>
   <td><bean:write name="man230Form" property="man230MemoryUse" />&nbsp;(<bean:write name="man230Form" property="man230MemoryUsePer" />%)</td>
 </tr>

 <tr>
   <th><gsmsg:write key="main.man230.5" /></th>
   <td><bean:write name="man230Form" property="man230MemoryMax" /></td>
 </tr>

 <logic:equal name="man230Form" property="man230DbKbn" value="0">
 <tr>
   <th><gsmsg:write key="main.man230.6" /></th>
   <td><bean:write name="man230Form" property="man230FreeDSpace" /></td>
 </tr>
 </logic:equal>

 <tr>
   <th><gsmsg:write key="main.man230.7" /></th>
   <td><bean:write name="man230Form" property="man230ConnectionCount" /></td>
 </tr>

 <logic:equal name="man230Form" property="man230DbKbn" value="0">

 <!-- ConnectionOption  -->
 <tr>
   <td class="table_title-color" colspan="2"><gsmsg:write key="cmn.main.h2connection" /></td>
 </tr>
 <bean:define name="man230Form" property="man230ConnectionOp" id="connectionOp"/>
 <tr>
   <th>LOCK_MODE</th>
   <td><bean:write name="connectionOp" property="lockMode"/></td>
 </tr>

 <tr>
   <th>LOCK_TIMEOUT</th>
   <td><bean:write name="connectionOp" property="lockTimeOut"/></td>
 </tr>

 <tr>
   <th>DEFAULT_LOCK_TIMEOUT</th>
   <td><bean:write name="connectionOp" property="defLockTimeOut"/></td>
 </tr>

 <tr>
   <th>MULTI_THREADED</th>
   <td><bean:write name="connectionOp" property="multiThreaded"/></td>
 </tr>

 <tr>
   <th>IFEXISTS</th>
   <td><bean:write name="connectionOp" property="ifExists"/></td>
 </tr>

 <tr>
   <th>AUTOCOMMIT</th>
   <td><bean:write name="connectionOp" property="autoCommit"/></td>
 </tr>

 <tr>
   <th>DB_CLOSE_ON_EXIT</th>
   <td><bean:write name="connectionOp" property="dbCloseOnExit"/></td>
 </tr>

 <tr>
   <th>CACHE_SIZE</th>
   <td><bean:write name="connectionOp" property="cacheSize"/></td>
 </tr>

 <tr>
   <th>PAGE_SIZE</th>
   <td><bean:write name="connectionOp" property="pageSize"/></td>
 </tr>

 <tr>
   <th>MAX_LENGTH_INPLACE_LOB</th>
   <td><bean:write name="connectionOp" property="maxLengthImplace"/></td>
 </tr>

 <tr>
   <th>CACHE_TYPE</th>
   <td><bean:write name="connectionOp" property="cacheType"/></td>
 </tr>

 <tr>
   <th>MVCC</th>
   <td><bean:write name="connectionOp" property="mvcc"/></td>
 </tr>

 <tr>
   <th>QUERY_TIMEOUT</th>
   <td><bean:write name="connectionOp" property="queryTimeOut"/></td>
 </tr>

 <tr>
   <th>QUERY_TIMEOUT_BATCH</th>
   <td><bean:write name="connectionOp" property="queryTimeOutBatch"/></td>
 </tr>

 <tr>
   <th>MAX_LOG_SIZE</th>
   <td><bean:write name="connectionOp" property="maxLogSize"/></td>
 </tr>

 </logic:equal>

 <logic:equal name="man230Form" property="man230DbKbn" value="0">

 <!-- gsdata.conf -->
 <tr>
   <td class="table_title-color" colspan="2"><gsmsg:write key="project.prj170.2"/></td>
 </tr>
 <bean:define name="man230Form" property="man230GsdataConf" id="gsdata"/>
 <tr>
   <th>GSDATA_DIR</th>
   <td><span class="txt_gray"><bean:write name="gsdata" property="gsDataDir" /></td>
 </tr>

 <tr>
   <th>BACKUP_DIR</th>
   <td><bean:write name="gsdata" property="backUpDir" /></td>
 </tr>

 <tr>
   <th>FILEKANRI_DIR</th>
   <td><bean:write name="gsdata" property="fileKanriDir" /></td>
 </tr>

 <tr>
   <th>WEBMAIL_DIR</th>
   <td><bean:write name="gsdata" property="webMailDir" /></td>
 </tr>

 </logic:equal>

 <!-- BackUpConf -->

 <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
 <% String[] dayOfWeekNameList = {gsMsg.getMessage(request, "cmn.sunday3"), gsMsg.getMessage(request, "cmn.monday3"),
 gsMsg.getMessage(request, "cmn.tuesday3"), gsMsg.getMessage(request, "cmn.wednesday3"),
 gsMsg.getMessage(request, "cmn.thursday3"), gsMsg.getMessage(request, "main.src.man080.7"),
 gsMsg.getMessage(request, "cmn.saturday3")}; %>
 <tr>
   <td class="table_title-color" colspan="2"><gsmsg:write key="cmn.autobackup.setting"/></td>
 </tr>

 <logic:notEmpty name="man230Form" property="man230BackUpConf">
   <bean:define name="man230Form" property="man230BackUpConf" id="backUp"/>
   <logic:notEqual name="backUp" property="bucInterval" value="0">
   <tr>

     <th><gsmsg:write key="cmn.main.backup.interval"/></th>

     <logic:equal name="backUp" property="bucInterval" value="1">
       <td><gsmsg:write key="cmn.everyday"/></td>
     </logic:equal>

     <logic:equal name="backUp" property="bucInterval" value="2">
       <bean:define name="backUp" property="bucDow" id="dayOfWeek"/>
       <td>
       <gsmsg:write key="cmn.weekly2"/>
       <%= dayOfWeekNameList[((Integer) dayOfWeek).intValue() - 1] %>
       </span>
       </td>
     </logic:equal>

     <logic:equal name="backUp" property="bucInterval" value="3">
       <bean:define name="backUp" property="bucWeekMonth" id="weekMonth"/>
       <bean:define name="backUp" property="bucDow" id="dayOfWeek"/>
       <td>

       <gsmsg:write key="cmn.monthly.2"/>
       <gsmsg:write key="main.src.man025.3"/><%= String.valueOf(weekMonth) %>
       <%= dayOfWeekNameList[((Integer) dayOfWeek).intValue() - 1] %>
       </td>
     </logic:equal>

   </tr>

   <logic:notEqual name="backUp" property="bucInterval" value="1">
   <bean:define name="backUp" property="bucDow" id="dayOfWeek"/>
   </logic:notEqual>

    <logic:notEqual name="backUp" property="bucInterval" value="0">
    <tr>
      <th><gsmsg:write key="man.number.generations"/></th>
      <td><bean:write name="backUp" property="bucGeneration"/><gsmsg:write key="fil.6"/></td>
    </tr>
    </logic:notEqual>

   <tr>
    <th><gsmsg:write key="main.output"/></th>
    <logic:equal name="backUp" property="bucZipout" value="1">
    <td><gsmsg:write key="main.zip.format.output"/></td>
    </logic:equal>
    <logic:equal name="backUp" property="bucZipout" value="0">
    <td><gsmsg:write key="cmn.not.compress"/></td>
    </logic:equal>
   </tr>

 </logic:notEqual>

 <logic:equal name="backUp" property="bucInterval" value="0">
  <tr>
    <th><gsmsg:write key="cmn.main.backup.interval"/></th>
    <td><gsmsg:write key="reserve.use.kbn.noset" /></td>
  </tr>
 </logic:equal>
 </logic:notEmpty>

 <logic:empty name="man230Form" property="man230BackUpConf">
 <tr>
   <th><gsmsg:write key="cmn.main.backup.interval"/></th>
   <td><gsmsg:write key="reserve.use.kbn.noset" /></td>
 </tr>
 </logic:empty>

 <!-- BatchInfo -->
 <tr>
   <td class="table_title-color" colspan="2"><gsmsg:write key="main.man040.1" /></td>
 </tr>
 <logic:notEmpty name="man230Form" property="man230CmnBatchJob">

   <bean:define name="man230Form" property="man230CmnBatchJob" id="batchJob"/>
   <tr>
     <th><gsmsg:write key="main.src.18" /></th>
     <td><bean:write name="batchJob" property="batFrDate"/><gsmsg:write key="tcd.running.time" /></td>
   </tr>

 </logic:notEmpty>
 <logic:empty name="man230Form" property="man230CmnBatchJob">
 <tr>
   <th><gsmsg:write key="main.man002.34" /></th>
   <td><gsmsg:write key="reserve.use.kbn.noset" /></td>
 </tr>
 </logic:empty>


 <!-- Plugin Info -->
 <tr>
   <td class="table_title-color" colspan="2"><gsmsg:write key="cmn.plugin.info" /></td>
 </tr>
 <logic:iterate name="man230Form" property="man230PluginList" id="pluginList" indexId="idx">

 <% int iterateNum = idx%2; %>
 <% if (iterateNum == 0) { %>

 <tr>
 <th><bean:write name="pluginList"/></th>

 <% } %>
 <% if (iterateNum == 1) { %>
 <logic:equal name="pluginList" value="1">
   <td><gsmsg:write key="cmn.unused" /></td>
 </logic:equal>
 <logic:equal name="pluginList" value="0">
   <td><gsmsg:write key="cmn.use" /></td>
 </logic:equal>
 </tr>

 <% } %>

 </logic:iterate>

 <!-- Temporary directory -->
 <tr>
   <td class="table_title-color" colspan="2"><gsmsg:write key="cmn.main.temp.path" /></td>
 </tr>
 <tr>
   <th><gsmsg:write key="cmn.main.temp.path" /></th>
   <td>
     <logic:notEmpty name="man230Form" property="gsUid">
       <bean:write name="man230Form" property="man230TempPath" />
     </logic:notEmpty>
   </td>
 </tr>

 <!-- sgsMobileSuiteVersion.conf  -->
 <tr>
   <td class="table_title-color" colspan="2">gsMobileSuiteVersion.conf</td>
 </tr>
 <bean:define name="man230Form" property="man230GsMobSuiteVer" id="gsMob"/>
  <tr>
    <th>COMPLIANT_VERSION</th>
    <td>
      <logic:notEmpty name="gsMob" property="compliantVer">
        <bean:write name="gsMob" property="compliantVer" />
      </logic:notEmpty>
    </td>
  </tr>

  <tr>
    <th>GS_MBA_IOS_VERSION</th>
    <td>
      <logic:notEmpty name="gsMob" property="gsMbaIosVer">
        <bean:write name="gsMob" property="gsMbaIosVer" />
      </logic:notEmpty>
    </td>
  </tr>

  <tr>
    <th>GS_CAL_IOS_VERSION</th>
    <td>
      <logic:notEmpty name="gsMob" property="gsCalIosVer">
        <bean:write name="gsMob" property="gsCalIosVer" />
      </logic:notEmpty>
    </td>
  </tr>

  <tr>
    <th>GS_ADR_IOS_VERSION</th>
    <td>
      <logic:notEmpty name="gsMob" property="gsAdrIosVer">
        <bean:write name="gsMob" property="gsAdrIosVer" />
      </logic:notEmpty>
    </td>
  </tr>

  <tr>
    <th>GS_SML_IOS_VERSION</th>
    <td>
      <logic:notEmpty name="gsMob" property="gsSmlIosVer">
        <bean:write name="gsMob" property="gsSmlIosVer" />
      </logic:notEmpty>
    </td>
  </tr>

  <tr>
    <th>GS_WML_IOS_VERSION</th>
    <td>
      <logic:notEmpty name="gsMob" property="gsWmlIosVer">
        <bean:write name="gsMob" property="gsWmlIosVer" />
      </logic:notEmpty>
    </td>
  </tr>

 <!-- filesearch.conf  -->
 <tr>
   <td class="table_title-color" colspan="2">filesearch.conf</td>
 </tr>
 <tr>
   <th>FIL_ALL_SEARCH_USE</th>
   <td>
     <logic:notEmpty name="man230Form" property="man230FileSearch">
       <bean:write name="man230Form" property="man230FileSearch" />
     </logic:notEmpty>
   </td>
 </tr>

 <!-- mailserverl.conf  -->
 <tr>
   <td class="table_title-color" colspan="2">mailserverl.conf</td>
 </tr>
 <tr>
   <th>MAIL_PORT_NUMBER</th>
   <td>
     <logic:notEmpty name="man230Form" property="man230MailServer">
       <bean:write name="man230Form" property="man230MailServer" />
     </logic:notEmpty>
   </td>
 </tr>

 <!-- portal.conf  -->
 <tr>
   <td class="table_title-color" colspan="2">portal.conf</td>
 </tr>
 <tr>
   <th>PORTLET_MAXLENGTH</th>
   <td>
     <logic:notEmpty name="man230Form" property="man230Portal">
       <bean:write name="man230Form" property="man230Portal" />
     </logic:notEmpty>
   </td>
 </tr>

 <!-- reserve.conf  -->
 <tr>
   <td class="table_title-color" colspan="2">reserve.conf</td>
 </tr>
  <tr>
    <th>RSV_PRINT_USE</th>
    <td>
      <logic:notEmpty name="man230Form" property="man230RsvPrintUse">
        <bean:write name="man230Form" property="man230RsvPrintUse" />
      </logic:notEmpty>
    </td>
  </tr>

 <!-- webmail.conf  -->
 <bean:define name="man230Form" property="man230WebMailModel" id="webmailInfo"/>
 <tr>
   <td class="table_title-color" colspan="2">webmail.conf</td>
 </tr>
  <tr>
    <th>MAILRECEIVE_THREAD_MAXCOUNT</th>
    <td>
     <logic:notEmpty name="webmailInfo" property="mailReceiveThreadMaxCount">
        <bean:write name="webmailInfo" property="mailReceiveThreadMaxCount" />
      </logic:notEmpty>
    </td>
  </tr>

  <tr>
    <th>MAILRECEIVE_MAXCOUNT</th>
    <td>
      <logic:notEmpty name="webmailInfo" property="mailReceiveMaxCount">
        <bean:write name="webmailInfo" property="mailReceiveMaxCount" />
      </logic:notEmpty>
    </td>
  </tr>

  <tr>
    <th>MAILRECEIVE_CONNECTTIMEOUT</th>
    <td>
      <logic:notEmpty name="webmailInfo" property="mailReceiveConnectTimeOut">
        <bean:write name="webmailInfo" property="mailReceiveConnectTimeOut" />
      </logic:notEmpty>
    </td>
  </tr>

  <tr>
    <th>MAILRECEIVE_TIMEOUT</th>
    <td>
      <logic:notEmpty name="webmailInfo" property="mailReceiveTimeOut">
        <bean:write name="webmailInfo" property="mailReceiveTimeOut" />
      </logic:notEmpty>
    </td>
  </tr>

  <tr>
    <th class="no_w wp20">MAILRECEIVE_RCVSVR_CHECKTIME</th>
    <td>
      <logic:notEmpty name="webmailInfo" property="mailReceiveRcvSvrChecktime">
        <bean:write name="webmailInfo" property="mailReceiveRcvSvrChecktime" />
      </logic:notEmpty>
    </td>
  </tr>

  <tr>
    <th>MAILBODY_LIMIT</th>
    <td>
      <logic:notEmpty name="webmailInfo" property="mailBodyLimit">
      <bean:write name="webmailInfo" property="mailBodyLimit" />
      </logic:notEmpty>
    </td>
  </tr>

</table>
  <div class="footerBtn_block">
    <logic:equal name="man230Form" property="man230SupportLimitFlag" value="0">
      <button type="button" value="<gsmsg:write key="cmn.reports" />" class="baseBtn" onClick="sendSystemInfo();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei.png" alt="<gsmsg:write key="cmn.reports" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_sinsei.png" alt="<gsmsg:write key="cmn.reports" />">
        <gsmsg:write key="cmn.reports" />
      </button>
    </logic:equal>
    <button type="button" value="<gsmsg:write key="cmn.reload" />" class="baseBtn" onClick="buttonPush('reload');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
      <gsmsg:write key="cmn.reload" />
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