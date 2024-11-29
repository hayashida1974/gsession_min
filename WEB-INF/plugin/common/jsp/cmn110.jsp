<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@page import="jp.groupsession.v2.cmn.GSConstCommon"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/freeze.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmn110_upload.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/common.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<title>GROUPSESSION <gsmsg:write key="cmn.cmn110.1" /></title>
</head>

<body onload="checkStatus();" class="bgI_none">

<div id="FreezePane" class="ofx_h">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <div id="InnerFreezePane"> </div>
  <html:form action="common/cmn110" enctype="multipart/form-data">
    <input type="hidden" name="CMD">
    <html:hidden property="cmn110parentListName" />
    <html:hidden property="cmn110pluginId" />
    <html:hidden property="cmn110Decision" />
    <html:hidden property="cmn110Mode" />
    <html:hidden property="cmn110TempDirPlus" />
    <html:hidden property="tempDirId" />
    <html:hidden property="splitStr" />
    <input type="hidden" name="cmn110uploadType" value="0">
    <input type="hidden" name="cmn110freezeText" value="<gsmsg:write key="cmn.cmn110.4" />">
    <span id="cmn110fileDataArea">
      <logic:notEmpty name="cmn110Form" property="cmn110tempName" scope="request">
        <logic:iterate id="fileName" name="cmn110Form" property="cmn110tempName" scope="request">
          <input type="hidden" name="cmn110tempName" value="<bean:write name="fileName"/>">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="cmn110Form" property="cmn110tempSaveName" scope="request">
        <logic:iterate id="fileName" name="cmn110Form" property="cmn110tempSaveName" scope="request">
          <input type="hidden" name="cmn110tempSaveName" value="<bean:write name="fileName"/>">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="cmn110Form" property="fileList" scope="request">
        <logic:iterate id="item" name="cmn110Form" property="fileList" scope="request">
          <input type="hidden" name="fileList" value="<bean:write name="item"/>">
        </logic:iterate>
      </logic:notEmpty>
    </span>
    <html:hidden property="cmn110PrjUseFlg" />
    <!-- プロジェクトプラグイン使用時 -->
    <logic:equal name="cmn110Form" property="cmn110PrjUseFlg" value="1">
      <html:hidden property="cmn110PrjCmdMode" />
      <html:hidden property="cmn110PrjSid" />
    </logic:equal>
    <!-- BODY -->

    <div>
      <div class="tempFileSelect_title bgC_header2">
        <logic:notEqual name="cmn110Form" property="strMaxSize" value="0">
          <gsmsg:write key="cmn.cmn110.2" />
        </logic:notEqual>
        <logic:equal name="cmn110Form" property="strMaxSize" value="0">
          <gsmsg:write key="cmn.cmn110.3" />
        </logic:equal>
      </div>
      <logic:notEqual name="cmn110Form" property="strMaxSize" value="0">
        <div class="mt5 w100 verAlignMid">
          <html:file property="cmn110file[0]" styleClass="w80" size="25" maxlength="50" styleId="selectFile"  />
          <span class="w20 txt_r">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.attached" />" onClick="confirmButtonPush();freezeScreenThis('<gsmsg:write key="cmn.cmn110.4" />');">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
              <img class="header_pluginImg" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
              <gsmsg:write key="cmn.attached" />
            </button>
          </span>
        </div>
        <div class="tempFileSelect_dropArea bgC_other1" id="uploadArea" draggable="true">
          <span class="tempFileSelect_dropArea-text fs_18">
            <gsmsg:write key="cmn.file.droparea.message" />
          </span>
        </div>
        <div class="textError fs_12">
          <bean:define id="filMaxSize" name="cmn110Form" property="strMaxSize" type="java.lang.String" />
          <gsmsg:write key="cmn.cmn110.5" arg0="<%= filMaxSize %>" />
        </div>
      </logic:notEqual>
      <div class="mt5 txt_c">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.cmn110.7" />" onClick="tempClose();">
          <img class="header_pluginImg-classic" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.cmn110.7" />">
          <img class="header_pluginImg" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.cmn110.7" />">
          <gsmsg:write key="cmn.cmn110.7" />
        </button>
      </div>
    </div>
  </html:form>
</div>
</body>
</html:html>