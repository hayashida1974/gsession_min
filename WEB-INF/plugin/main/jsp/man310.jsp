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
<title>GROUPSESSION <gsmsg:write key="main.man310.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man310.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/components/file-preview/index.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body>
<html:form action="/main/man310">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="imssid" value="0">
<html:hidden name="man310Form" property="man310binSid" />

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../common/images/classic/icon_info_32.png" alt="<gsmsg:write key="cmn.information" />">
      <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="<gsmsg:write key="cmn.information" />">
    </li>
    <li><gsmsg:write key="cmn.information" /></li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <div class="flo_l fw_b ml5 mt10 mb10 verAlignMid">
    <img src="../main/images/classic/menu_icon_single_info.gif" class="btn_classicImg-display mr5" alt="インフォメーション">
    <img src="../common/images/original/icon_info_25.png" class="btn_originalImg-display mr5" alt="インフォメーション">
    <span class="fs_17 fw_b lh_normal">
      <bean:write name="man310Form" property="man310Msg"/>
    </span>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.content" />
      </th>
      <td class="w80">
        <bean:write name="man310Form" property="man310Value" filter="false" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.attached" />
      </th>
      <td class="w80">
        <logic:empty name="man310Form" property="tmpFileList">&nbsp;</logic:empty>

    <logic:notEmpty name="man310Form" property="tmpFileList">
      <logic:iterate id="fileMdl" name="man310Form" property="tmpFileList">
      <logic:notEmpty name="fileMdl" property="binFileExtension">
        <bean:define id="fext" name="fileMdl" property="binFileExtension"  type="java.lang.String" />
        <%
        String dext = ((String)pageContext.getAttribute("fext",PageContext.PAGE_SCOPE));
        if (dext != null) {
            dext = dext.toLowerCase();
            if (jp.groupsession.v2.cmn.biz.CommonBiz.isViewFile(dext)) {
        %>
        <div>
          <img src="../main/man310.do?CMD=tempview&imssid=<%= request.getParameter("imssid") %>&man310binSid=<bean:write name="fileMdl" property="binSid" />" name="pictImage<bean:write name="fileMdl" property="binSid" />" onload="initImageView('pictImage<bean:write name="fileMdl" property="binSid" />');">
        </div>
        <%
            }
        }
        %>
      </logic:notEmpty>
      <div>
        <file-preview url="../main/man310.do?CMD=fileDownload&imssid=<%= request.getParameter("imssid") %>&man310binSid=<bean:write name="fileMdl" property="binSid" />" filename="<bean:write name="fileMdl" property="binFileName" />">
          <a href="javascript:fileLinkClick(<%= request.getParameter("imssid") %>, <bean:write name="fileMdl" property="binSid" />);">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.file" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.file" />">
            <span class="textLink"><bean:write name="fileMdl" property="binFileName" /><bean:write name="fileMdl" property="binFileSizeDsp" /></span>
          </a>
        </file-preview>
      </div>
      </logic:iterate>
      </table>
    </logic:notEmpty>


      </td>
    </tr>
  </table>
  <table class="table-left w100 mt10">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.exposed" />
      </th>
      <td class="w80 bgC_other1">
        <logic:notEmpty name="man310Form" property="man310KoukaiList">
          <logic:iterate id="memName" name="man310Form" property="man310KoukaiList">
            <bean:write name="memName" property="label" /><br>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.registant" />
      </th>
      <td class="w80 bgC_other1">
        <logic:equal name="man310Form" property="man310UsrJkbn" value="9">
          <del>
            <bean:write name="man310Form" property="man310NameSei" />&nbsp;<bean:write name="man310Form" property="man310NameMei" />
          </del>
        </logic:equal>
        <logic:notEqual name="man310Form" property="man310UsrJkbn" value="9">
          <bean:write name="man310Form" property="man310NameSei" />&nbsp;<bean:write name="man310Form" property="man310NameMei" />
        </logic:notEqual>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>
</div>

<IFRAME type="hidden" src="../common/html/damy.html" style="display: none" name="navframe"></IFRAME>
</html:form>
</body>
</html:html>