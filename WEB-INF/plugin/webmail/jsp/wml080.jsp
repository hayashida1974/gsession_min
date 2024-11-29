<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
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
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/components/file-preview/index.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml080.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml080.03" /></title>
</head>

<body>

<html:form action="/webmail/wml080">

<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden name="wml080Form" property="wmlViewAccount" />

<div class="wrapper">

  <table class="table-left ml0 mr0 mt5">
    <tr>
      <th class="txt_l">
        <gsmsg:write key="cmn.subject" />: <bean:write name="wml080Form" property="wml080Title" /><br>
        <gsmsg:write key="cmn.sendfrom" />: <bean:write name="wml080Form" property="wml080From" /><br>
        <gsmsg:write key="cmn.send.date" />: <bean:write name="wml080Form" property="wml080Sdate" /> <bean:write name="wml080Form" property="wml080Stime" /><br>
      </th>
    </tr>

  <logic:notEmpty name="wml080Form" property="tempFileList">
    <tr>
      <td>
      <logic:iterate id="fileData" name="wml080Form" property="tempFileList">
        <span class="virAlignMid">
          <file-preview url="../webmail/wml080.do?CMD=downloadFile&wml080mailNum=<bean:write name="wml080Form" property="wml080mailNum"/>&wml080downloadFileId=<bean:write name="fileData" property="binSid" />" filename="<bean:write name="fileData" property="fileName"/>">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file.gif" alt="<gsmsg:write key="cmn.file" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.file" />">
            <a href="#" onClick="fileDownload(<bean:write name="wml080Form" property="wml080mailNum" />,<bean:write name="fileData" property="binSid" />);"><bean:write name="fileData" property="fileName" /></a><br>
          </file-preview>
        </span>
      </logic:iterate>
      </td>
    </tr>
  </logic:notEmpty>

    <tr>
      <td>
      <logic:notEqual name="wml080Form" property="wml080BodyFlg" value="1">
        <span class="cl_fontWarn"><bean:write name="wml080Form" property="wml080Body" /></span>
      </logic:notEqual>

      <logic:equal name="wml080Form" property="wml080BodyFlg" value="1">
        <bean:write name="wml080Form" property="wml080Body" filter="false" />
      </logic:equal>
      </td>
    </tr>

  </table>

  <div class="w100 txt_c">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>

</html:form>
</body>
</html:html>