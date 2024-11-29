<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<logic:notEqual name="bbs090knForm" property="bbs090cmdMode" value="1">
<title>GROUPSESSION <gsmsg:write key="bbs.bbs090kn.1" /></title>
</logic:notEqual>
<logic:equal name="bbs090knForm" property="bbs090cmdMode" value="1">
<title>GROUPSESSION <gsmsg:write key="bbs.bbs090kn.2" /></title>
</logic:equal>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bulletin/js/bbs090kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/components/file-preview/index.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../bulletin/css/bulletin.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/bulletin/bbs090kn">

<input type="hidden" name="CMD" value="">
<html:hidden name="bbs090knForm" property="s_key" />
<html:hidden name="bbs090knForm" property="tempDirId" />
<html:hidden name="bbs090knForm" property="bbs010page1" />
<html:hidden name="bbs090knForm" property="bbs010forumSid" />
<html:hidden name="bbs090knForm" property="bbs060page1" />
<html:hidden name="bbs090knForm" property="searchDspID" />
<html:hidden name="bbs090knForm" property="bbs040forumSid" />
<html:hidden name="bbs090knForm" property="bbs040keyKbn" />
<html:hidden name="bbs090knForm" property="bbs040taisyouThread" />
<html:hidden name="bbs090knForm" property="bbs040taisyouNaiyou" />
<html:hidden name="bbs090knForm" property="bbs040userName" />
<html:hidden name="bbs090knForm" property="bbs040readKbn" />
<html:hidden name="bbs090knForm" property="bbs040publicStatusOngoing" />
<html:hidden name="bbs090knForm" property="bbs040publicStatusScheduled" />
<html:hidden name="bbs090knForm" property="bbs040publicStatusOver" />
<html:hidden name="bbs090knForm" property="bbs040dateNoKbn" />
<html:hidden name="bbs090knForm" property="bbs040fromYear" />
<html:hidden name="bbs090knForm" property="bbs040fromMonth" />
<html:hidden name="bbs090knForm" property="bbs040fromDay" />
<html:hidden name="bbs090knForm" property="bbs040toYear" />
<html:hidden name="bbs090knForm" property="bbs040toMonth" />
<html:hidden name="bbs090knForm" property="bbs040toDay" />
<html:hidden name="bbs090knForm" property="bbs041page1" />
<html:hidden name="bbs090knForm" property="threadSid" />
<html:hidden name="bbs090knForm" property="soukouSid" />
<html:hidden name="bbs090knForm" property="bbs060postPage1" />
<html:hidden name="bbs090knForm" property="bbs060postSid" />
<html:hidden name="bbs090knForm" property="bbs060postOrderKey" />
<html:hidden name="bbs090knForm" property="bbs090contributorEditKbn" />
<html:hidden name="bbs090knForm" property="bbs090cmdMode" />
<html:hidden name="bbs090knForm" property="bbs090contributor" />
<html:hidden name="bbs090knForm" property="bbs090value" />
<html:hidden name="bbs090knForm" property="bbs090valueHtml" />
<html:hidden name="bbs090knForm" property="bbs090valueType" />
<html:hidden name="bbs090knForm" property="bbs090knTmpFileId" />
<html:hidden name="bbs090knForm" property="bbs090threTitle" />
<html:hidden name="bbs090knForm" property="bbs090knviewContributor" />

<html:hidden name="bbs090knForm" property="bbsmainFlg" />
<html:hidden property="bbs220SortKey" />
<html:hidden property="bbs220OrderKey" />
<html:hidden property="bbs220BackDsp" />
<html:hidden property="bbs220BackThreadSid" />
<html:hidden property="bbs220BackForumSid" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../bulletin/images/classic/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
      <img class="header_pluginImg" src="../bulletin/images/original/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
    </li>
    <li><gsmsg:write key="cmn.bulletin" /></li>
    <li class="pageTitle_subFont">
      <logic:notEqual name="bbs090knForm" property="bbs090cmdMode" value="1">
        <gsmsg:write key="bbs.bbs090kn.1" />
      </logic:notEqual>
      <logic:equal name="bbs090knForm" property="bbs090cmdMode" value="1">
        <gsmsg:write key="bbs.bbs090kn.2" />
      </logic:equal>
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToInput');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
<table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.contributor" />
      </th>
      <td class="w75">
        <logic:equal name="bbs090knForm" property="bbs090contributorJKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>" >
          <del><bean:write name="bbs090knForm" property="bbs090knviewContributor" /></del>
        </logic:equal>
        <logic:notEqual name="bbs090knForm" property="bbs090contributorJKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>" >
          <bean:write name="bbs090knForm" property="bbs090knviewContributor" />
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="bbs.3" />
      </th>
      <td>
        <bean:write name="bbs090knForm" property="bbs090forumName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.title" />
      </th>
      <td>
        <bean:write name="bbs090knForm" property="bbs090threTitle" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.content" />
      </th>
      <td class="table_newLine">
        <logic:equal name="bbs090knForm" property="bbs090valueType" value="0">
          <bean:write name="bbs090knForm" property="bbs090knviewvalue" filter="false" />
        </logic:equal>
        <logic:equal name="bbs090knForm" property="bbs090valueType" value="1">
          <htmlframe:write attrClass="w100">
            <bean:write name="bbs090knForm" property="bbs090valueHtml" filter="false" />
          </htmlframe:write>
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.attached" />
      </th>
      <td>
        <logic:empty name="bbs090knForm" property="bbs090FileLabelList" scope="request">&nbsp;</logic:empty>

        <logic:notEmpty name="bbs090knForm" property="bbs090FileLabelList" scope="request">
        <logic:iterate id="fileMdl" name="bbs090knForm" property="bbs090FileLabelList" scope="request">
          <file-preview
            url="../bulletin/bbs090kn.do?CMD=fileDownload&bbs090knTmpFileId=<bean:write name="fileMdl" property="value"/>&bbs090cmdMode=<bean:write name="bbs090knForm" property="bbs090cmdMode"/>&bbs010forumSid=<bean:write name="bbs090knForm" property="bbs010forumSid"/>&bbs060postSid=<bean:write name="bbs090knForm" property="bbs060postSid"/>&tempDirId=<bean:write name="bbs090knForm" property="tempDirId"/>"
            filename="<bean:write name="fileMdl" property="label"/>">
            <div>
              <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.file" />">
              <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.file" />">
              <a href="#!" onClick="return fileLinkClick('<bean:write name="fileMdl" property="value" />');"><bean:write name="fileMdl" property="label" /></a>
            </div>
          </file-preview>
        </logic:iterate>
        </logic:notEmpty>
      </td>
      </tr>
    </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToInput');">
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
