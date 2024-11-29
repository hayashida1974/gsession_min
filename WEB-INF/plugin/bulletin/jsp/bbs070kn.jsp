<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.bbs.GSConstBulletin" %>
<!DOCTYPE html>

<%
    String limitNo = String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_LIMIT_NO);
    String limitYes = String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_LIMIT_YES);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<logic:notEqual name="bbs070knForm" property="bbs070cmdMode" value="<%= String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT) %>">
<title>GROUPSESSION <gsmsg:write key="bbs.bbs070kn.1" /></title>
</logic:notEqual>
<logic:equal name="bbs070knForm" property="bbs070cmdMode" value="<%= String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT) %>">
<title>GROUPSESSION <gsmsg:write key="bbs.bbs070kn.2" /></title>
</logic:equal>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bulletin/js/bbs070kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/components/file-preview/index.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../bulletin/css/bulletin.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/bulletin/bbs070kn">

<input type="hidden" name="CMD" value="">
<html:hidden name="bbs070knForm" property="s_key" />
<html:hidden name="bbs070knForm" property="tempDirId" />
<html:hidden name="bbs070knForm" property="bbs010page1" />
<html:hidden name="bbs070knForm" property="bbs010forumSid" />
<html:hidden name="bbs070knForm" property="bbs060page1" />
<html:hidden name="bbs070knForm" property="searchDspID" />
<html:hidden name="bbs070knForm" property="bbs040forumSid" />
<html:hidden name="bbs070knForm" property="bbs040keyKbn" />
<html:hidden name="bbs070knForm" property="bbs040taisyouThread" />
<html:hidden name="bbs070knForm" property="bbs040taisyouNaiyou" />
<html:hidden name="bbs070knForm" property="bbs040userName" />
<html:hidden name="bbs070knForm" property="bbs040readKbn" />
<html:hidden name="bbs070knForm" property="bbs040publicStatusOngoing" />
<html:hidden name="bbs070knForm" property="bbs040publicStatusScheduled" />
<html:hidden name="bbs070knForm" property="bbs040publicStatusOver" />
<html:hidden name="bbs070knForm" property="bbs040dateNoKbn" />
<html:hidden name="bbs070knForm" property="bbs040fromYear" />
<html:hidden name="bbs070knForm" property="bbs040fromMonth" />
<html:hidden name="bbs070knForm" property="bbs040fromDay" />
<html:hidden name="bbs070knForm" property="bbs040toYear" />
<html:hidden name="bbs070knForm" property="bbs040toMonth" />
<html:hidden name="bbs070knForm" property="bbs040toDay" />
<html:hidden name="bbs070knForm" property="bbs041page1" />
<html:hidden name="bbs070knForm" property="bbs060postPage1" />
<html:hidden name="bbs070knForm" property="bbs060postSid" />
<html:hidden name="bbs070knForm" property="bbs060postOrderKey" />

<html:hidden name="bbs070knForm" property="threadSid" />
<html:hidden name="bbs070knForm" property="soukouSid" />
<html:hidden name="bbs070knForm" property="bbs070cmdMode" />
<html:hidden name="bbs070knForm" property="bbs070changeDateFlg" />
<html:hidden name="bbs070knForm" property="bbs070forumName" />
<html:hidden name="bbs070knForm" property="bbs070contributor" />
<html:hidden name="bbs070knForm" property="bbs070title" />
<html:hidden name="bbs070knForm" property="bbs070value" />
<html:hidden name="bbs070knForm" property="bbs070valueHtml" />
<html:hidden name="bbs070knForm" property="bbs070limit" />
<html:hidden name="bbs070knForm" property="bbs070limitFrYear" />
<html:hidden name="bbs070knForm" property="bbs070limitFrMonth" />
<html:hidden name="bbs070knForm" property="bbs070limitFrDay" />
<html:hidden name="bbs070knForm" property="bbs070limitFrHour" />
<html:hidden name="bbs070knForm" property="bbs070limitFrMinute" />
<html:hidden name="bbs070knForm" property="bbs070limitYear" />
<html:hidden name="bbs070knForm" property="bbs070limitMonth" />
<html:hidden name="bbs070knForm" property="bbs070limitDay" />
<html:hidden name="bbs070knForm" property="bbs070limitHour" />
<html:hidden name="bbs070knForm" property="bbs070limitMinute" />
<html:hidden name="bbs070knForm" property="bbs070limitDisable" />
<html:hidden name="bbs070knForm" property="bbs070limitException" />
<html:hidden name="bbs070knForm" property="bbs070Importance" />
<html:hidden name="bbs070knForm" property="bbs070contributorEditKbn" />
<html:hidden name="bbs070knForm" property="bbs070knTmpFileId" />
<html:hidden name="bbs070knForm" property="bbs070knviewContributor" />

<html:hidden name="bbs070knForm" property="bbs070BackID" />
<html:hidden name="bbs070knForm" property="bbs070valueType" />
<html:hidden name="bbs070knForm" property="bbs170backForumSid" />
<html:hidden name="bbs070knForm" property="bbs170allForumFlg" />
<html:hidden property="bbs220SortKey" />
<html:hidden property="bbs220OrderKey" />
<html:hidden property="bbs220BackDsp" />
<html:hidden property="bbs220BackThreadSid" />
<html:hidden property="bbs220BackForumSid" />


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../bulletin/images/classic/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
      <img class="header_pluginImg" src="../bulletin/images/original/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
    </li>
    <li><gsmsg:write key="cmn.bulletin" /></li>
    <li class="pageTitle_subFont">
      <logic:notEqual name="bbs070knForm" property="bbs070cmdMode" value="<%= String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT) %>">
        <gsmsg:write key="bbs.bbs070kn.1" />
       </logic:notEqual>
        <logic:equal name="bbs070knForm" property="bbs070cmdMode" value="<%= String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT) %>">
        <gsmsg:write key="bbs.bbs070kn.2" />
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
<logic:equal name="bbs070knForm"  property="bbs070changeDateFlg" value="1">
  <table class="w100" id ="keikoku_table_color">
    <tr>
      <td><gsmsg:write key="bbs.bbs070kn.3" /></td>
    </tr>
    <tr>
      <td><span id ="keikoku_msg_color"><gsmsg:write key="bbs.bbs070kn.4" /></span></td>
    </tr>
  </table>
</logic:equal>

</div>
<table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.contributor" />
      </th>
      <td class="w75">
        <logic:equal name="bbs070knForm" property="bbs070contributorJKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>" >
          <del><bean:write name="bbs070knForm" property="bbs070knviewContributor" /></del>
        </logic:equal>
        <logic:notEqual name="bbs070knForm" property="bbs070contributorJKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>" >
          <logic:equal value="1" name="bbs070knForm" property="bbs070UserYukoKbn">
            <span class="mukoUser"><bean:write name="bbs070knForm" property="bbs070knviewContributor" /></span>
          </logic:equal>
          <logic:notEqual value="1" name="bbs070knForm" property="bbs070UserYukoKbn">
            <bean:write name="bbs070knForm" property="bbs070knviewContributor" />
          </logic:notEqual>
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="bbs.3" />
      </th>
      <td>
        <bean:write name="bbs070knForm" property="bbs070forumName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.title" />
      </th>
      <td>
        <bean:write name="bbs070knForm" property="bbs070title" />
      </td>
    </tr>
    <!-- 重要度 -->
    <tr>
      <th>
        <gsmsg:write key="project.prj050.4" />
      </th>
      <td>
        <logic:equal name="bbs070knForm" property="bbs070Importance" value="1">
          <img class="classic-display" src="../common/images/classic/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
          <img class="original-display" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.content" />
      </th>
      <logic:equal name="bbs070knForm" property="bbs070valueType" value="0">
      <td class="table_newLine">
        <bean:write name="bbs070knForm" property="bbs070knviewvalue" filter="false" />
      </td>
      </logic:equal>
      <logic:equal name="bbs070knForm" property="bbs070valueType" value="1">
      <td class="table_newLine">
        <htmlframe:write attrClass="w100">
          <bean:write name="bbs070knForm" property="bbs070valueHtml" filter="false" />
        </htmlframe:write>
      </td>
      </logic:equal>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.attached" />
      </th>
      <td>
        <logic:empty name="bbs070knForm" property="bbs070FileLabelList" scope="request">&nbsp;</logic:empty>

        <logic:notEmpty name="bbs070knForm" property="bbs070FileLabelList" scope="request">
          <logic:iterate id="fileMdl" name="bbs070knForm" property="bbs070FileLabelList" scope="request">
            <file-preview
              url="../bulletin/bbs070kn.do?CMD=fileDownload&bbs070knTmpFileId=<bean:write name="fileMdl" property="value"/>&bbs070cmdMode=<bean:write name="bbs070knForm" property="bbs070cmdMode"/>&bbs010forumSid=<bean:write name="bbs070knForm" property="bbs010forumSid"/>&bbs060postSid=<bean:write name="bbs070knForm" property="bbs060postSid"/>&tempDirId=<bean:write name="bbs070knForm" property="tempDirId"/>"
              filename="<bean:write name="fileMdl" property="label"/>">
              <div>
                <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.file" />">
                <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.file" />">
                <a href="#!" onClick="return fileLinkClick2('<bean:write name="fileMdl" property="value" />');">
                  <bean:write name="fileMdl" property="label" />
                </a>
              </div>
            </file-preview>
        </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <logic:equal name="bbs070knForm" property="bbs070limitDisable" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_ENABLE) %>">
    <tr>
      <th><gsmsg:write key="bbs.12" /></th>
      <td>
        <logic:equal name="bbs070knForm" property="bbs070limit" value="<%= limitYes %>">
        <bean:define id="frYr" name="bbs070knForm" property="bbs070limitFrYear" type="java.lang.Integer" />
          <bean:define id="yr" name="bbs070knForm" property="bbs070limitYear" type="java.lang.Integer" />

          <div>
          <gsmsg:write key="bbs.bbs070.5" />&nbsp;<gsmsg:write key="cmn.year" arg0="<%= String.valueOf(frYr) %>" /><bean:write name="bbs070knForm" property="bbs070limitFrMonth" /><gsmsg:write key="cmn.month" /><bean:write name="bbs070knForm" property="bbs070limitFrDay" /><gsmsg:write key="cmn.day" /><bean:write name="bbs070knForm" property="bbs070limitFrHour"/><gsmsg:write key="cmn.hour"/><bean:write name="bbs070knForm" property="bbs070limitFrMinute"/><gsmsg:write key="cmn.minute"/>
          </div>
          <div>
          <gsmsg:write key="bbs.bbs070.6" />&nbsp;<gsmsg:write key="cmn.year" arg0="<%= String.valueOf(yr) %>" /><bean:write name="bbs070knForm" property="bbs070limitMonth" /><gsmsg:write key="cmn.month" /><bean:write name="bbs070knForm" property="bbs070limitDay" /><gsmsg:write key="cmn.day" /><bean:write name="bbs070knForm" property="bbs070limitHour"/><gsmsg:write key="cmn.hour"/><bean:write name="bbs070knForm" property="bbs070limitMinute"/><gsmsg:write key="cmn.minute"/>
          </div>
        </logic:equal>
        <logic:notEqual name="bbs070knForm" property="bbs070limit" value="<%= limitYes %>">
          <gsmsg:write key="cmn.unlimited" />
        </logic:notEqual>
      </td>
      </tr>
      </logic:equal>
      <logic:notEqual name="bbs070knForm" property="bbs070limitDisable" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_ENABLE) %>">
      <logic:equal name="bbs070knForm" property="bbs070limitException" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_EXCEPTION) %>">
      <tr>
      <th class="w20"><gsmsg:write key="bbs.12" /></th>
      <td>
        <logic:equal name="bbs070knForm" property="bbs070limit" value="<%= limitYes %>">
        <bean:define id="frYr" name="bbs070knForm" property="bbs070limitFrYear" type="java.lang.Integer" />
          <bean:define id="yr" name="bbs070knForm" property="bbs070limitYear" type="java.lang.Integer" />

          <div>
          <gsmsg:write key="bbs.bbs070.5" />&nbsp;<gsmsg:write key="cmn.year" arg0="<%= String.valueOf(frYr) %>" /><bean:write name="bbs070knForm" property="bbs070limitFrMonth" /><gsmsg:write key="cmn.month" /><bean:write name="bbs070knForm" property="bbs070limitFrDay" /><gsmsg:write key="cmn.day" />
          </div>
          <div>
          <gsmsg:write key="bbs.bbs070.6" />&nbsp;<gsmsg:write key="cmn.year" arg0="<%= String.valueOf(yr) %>" /><bean:write name="bbs070knForm" property="bbs070limitMonth" /><gsmsg:write key="cmn.month" /><bean:write name="bbs070knForm" property="bbs070limitDay" /><gsmsg:write key="cmn.day" />
          </div>
        </logic:equal>
        <logic:notEqual name="bbs070knForm" property="bbs070limit" value="<%= limitYes %>">
          <gsmsg:write key="cmn.unlimited" />
        </logic:notEqual>
      </td>
      </tr>
      </logic:equal>
      </logic:notEqual>
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
