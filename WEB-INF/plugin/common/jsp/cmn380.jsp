<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<bean:define id="fileName" name="cmn380Form" property="cmn380FileName"/>
<title>GROUPSESSION <gsmsg:write key="cmn.preview"/> 『<%= fileName %>』</title>

<bean:define id="url" name="cmn380Form" property="cmn380PreviewURL" type="java.lang.String" />
<bean:define id="fileExtension" name="cmn380Form" property="cmn380PreviewFileExtension" type="java.lang.String" />
<%
  boolean pdfFlg = false, imgFlg = false, textFlg = false;
  if (fileExtension != null) {
    fileExtension = fileExtension.toLowerCase();

    pdfFlg = fileExtension.equals("pdf");
    imgFlg = fileExtension.equals("jpg")
          || fileExtension.equals("jpeg")
          || fileExtension.equals("png")
          || fileExtension.equals("gif");
    textFlg = fileExtension.equals("txt")
           || fileExtension.equals("js")
           || fileExtension.equals("css")
           || fileExtension.equals("html");
  }
%>

<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.12.1/jquery-ui.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn380.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/common.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

<% if (imgFlg) { %>
<script src="../common/js/viewerjs/viewer.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/js/viewerjs/viewer.min.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<% } %>

<theme:css filename="theme.css"/>

</head>

<% if (pdfFlg) { %>
<body class="m0 p0">
  <iframe class="w100 h100 pos_abs" src="../common/js/pdfjs-4.2.67/web/viewer.html?file=<bean:write name="cmn380Form" property="cmn380PreviewPdfURL" />"></iframe>
<% } else if (imgFlg) { %>
<body onload="cmn380LoadFile();" class="bgC_none">
  <div style="width: 880px; height: 880px;">
    <img id="imgPreview" class="pt5 cursor_p display_none" src="<%= url %>" />
  </div>
<% } else if (textFlg) { %>
<body onload="convertFile()" class="m0 bgC_body bgI_none">
  <html:form action="/common/cmn380">
    <div>
      <input type="hidden" name="url" value="<%= url %>"/>
      <input type="hidden" name="extension" value="<%= fileExtension %>"/>
      <span id="textPreviewArea" class="word_b-all"></span>
    </div>
    <div class="encordingSelectArea bgC_header2 pr10 w100 hp40">
      <span class="mr5"><gsmsg:write key="cmn.encording"/></span>
      <html:select property="cmn380SelectEncording" styleClass="wp180" onchange="convertFile()">
        <html:optionsCollection name="cmn380Form" property="cmn380EncordingList" value="value" label="label"/>
      </html:select>
    </div>
    <% if (fileExtension.toLowerCase().equals("html")) {%>
      <div class="cmn380MessageArea pl10 w50 hp40"><gsmsg:write key="cmn.preview.message.html"/></div>
    <% } %>
  </html:form>
<% } %>
</body>
</html:html>