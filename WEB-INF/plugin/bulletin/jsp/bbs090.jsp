<%@page import="jp.groupsession.v2.cmn.GSConstCommon"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.bbs.GSConstBulletin"%>
<!DOCTYPE html>

<%
  String maxLengthValue = String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.MAX_LENGTH_WRITEVALUE);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<logic:notEqual name="bbs090Form" property="bbs090cmdMode" value="1">
  <title>GROUPSESSION <gsmsg:write key="bbs.bbs090.1" /></title>
</logic:notEqual>
<logic:equal name="bbs090Form" property="bbs090cmdMode" value="1">
  <title>GROUPSESSION <gsmsg:write key="bbs.bbs090.2" /></title>
</logic:equal>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tinymce-5.10.3/tinymce.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../bulletin/js/bbs_tinymce.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bulletin/js/bbs090.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../bulletin/css/bulletin.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body onunload="windowClose();" onload="showLengthId($('#input_area_plain')[0], <%=maxLengthValue%>, 'inputlength');">

  <html:form action="/bulletin/bbs090">

    <input type="hidden" name="CMD" value="">
    <html:hidden name="bbs090Form" property="s_key" />
    <html:hidden name="bbs090Form" property="tempDirId" />
    <html:hidden name="bbs090Form" property="bbs010page1" />
    <html:hidden name="bbs090Form" property="bbs010forumSid" />
    <html:hidden name="bbs090Form" property="bbs060page1" />
    <html:hidden name="bbs090Form" property="searchDspID" />
    <html:hidden name="bbs090Form" property="bbs040forumSid" />
    <html:hidden name="bbs090Form" property="bbs040keyKbn" />
    <html:hidden name="bbs090Form" property="bbs040taisyouThread" />
    <html:hidden name="bbs090Form" property="bbs040taisyouNaiyou" />
    <html:hidden name="bbs090Form" property="bbs040userName" />
    <html:hidden name="bbs090Form" property="bbs040readKbn" />
    <html:hidden name="bbs090Form" property="bbs040publicStatusOngoing" />
    <html:hidden name="bbs090Form" property="bbs040publicStatusScheduled" />
    <html:hidden name="bbs090Form" property="bbs040publicStatusOver" />
    <html:hidden name="bbs090Form" property="bbs040dateNoKbn" />
    <html:hidden name="bbs090Form" property="bbs040fromYear" />
    <html:hidden name="bbs090Form" property="bbs040fromMonth" />
    <html:hidden name="bbs090Form" property="bbs040fromDay" />
    <html:hidden name="bbs090Form" property="bbs040toYear" />
    <html:hidden name="bbs090Form" property="bbs040toMonth" />
    <html:hidden name="bbs090Form" property="bbs040toDay" />
    <html:hidden name="bbs090Form" property="bbs041page1" />
    <html:hidden name="bbs090Form" property="threadSid" />
    <html:hidden name="bbs090Form" property="soukouSid" />
    <html:hidden name="bbs090Form" property="bbs060postPage1" />
    <html:hidden name="bbs090Form" property="bbs060postSid" />
    <html:hidden name="bbs090Form" property="bbs060postOrderKey" />
    <html:hidden name="bbs090Form" property="bbs090contributorEditKbn" />
    <html:hidden name="bbs090Form" property="bbs090cmdMode" />

    <html:hidden name="bbs090Form" property="bbsmainFlg" />
    <html:hidden name="bbs090Form" property="bbs090valueType" />
    <html:hidden property="bbs220SortKey" />
    <html:hidden property="bbs220OrderKey" />
    <html:hidden property="bbs220BackDsp" />
    <html:hidden property="bbs220BackThreadSid" />
    <html:hidden property="bbs220BackForumSid" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <!-- BODY -->
    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../bulletin/images/classic/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
          <img class="header_pluginImg" src="../bulletin/images/original/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
        </li>
        <li>
          <gsmsg:write key="cmn.bulletin" />
        </li>
        <logic:notEqual name="bbs090Form" property="bbs090cmdMode" value="1">
          <li class="pageTitle_subFont">
            <gsmsg:write key="bbs.bbs090.1" />
          </li>
        </logic:notEqual>
        <logic:equal name="bbs090Form" property="bbs090cmdMode" value="1">
          <li class="pageTitle_subFont">
            <gsmsg:write key="bbs.bbs090.2" />
          </li>
        </logic:equal>
        <li>
          <div>
            <logic:notEqual name="bbs090Form" property="bbs090cmdMode" value="<%=String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT)%>">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.save.draft" />" onClick="buttonPush('draft');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
                <gsmsg:write key="cmn.save.draft" />
              </button>
            </logic:notEqual>
            <button type="button" class="baseBtn" value="ＯＫ" onClick="buttonPush('moveWriteConfirm');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backWriteList');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper">
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left w100">
        <tr>
          <th class="w25 txt_l">
            <gsmsg:write key="cmn.contributor" />
          </td>
          <td class="w75">
            <logic:equal name="bbs090Form" property="bbs090contributorEditKbn" value="0">
              <logic:iterate id="labelMdl" name="bbs090Form" property="bbs090contributorList">
                <html:hidden name="bbs090Form" property="bbs090contributor" />
                <logic:equal name="bbs090Form" property="bbs090contributorJKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                  <del>
                    <bean:write name="labelMdl" property="label" />
                  </del>
                </logic:equal>
                <logic:notEqual name="bbs090Form" property="bbs090contributorJKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                  <bean:write name="labelMdl" property="label" />
                </logic:notEqual>
              </logic:iterate>
            </logic:equal>
            <logic:equal name="bbs090Form" property="bbs090contributorEditKbn" value="1">
              <html:select property="bbs090contributor" styleClass="wp200">
                <html:optionsCollection name="bbs090Form" property="bbs090contributorList" value="value" label="label" />
              </html:select>
            </logic:equal>
          </td>
        </tr>
        <tr>
          <th class="txt_l">
            <gsmsg:write key="bbs.3" />
          </th>
          <td>
            <bean:write name="bbs090Form" property="bbs090forumName" />
          </td>
        </tr>
        <tr>
          <th class="txt_l">
            <gsmsg:write key="cmn.title" />
          </th>
          <td>
            <bean:write name="bbs090Form" property="bbs090threTitle" />
          </td>
        </tr>

        <!-- 本文 -->
        <bean:define id="tempDirId" name="bbs090Form" property="tempDirId" type="java.lang.String"/>
        <tr>
          <th class="txt_l">
            <gsmsg:write key="cmn.content" />
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="pr5">
            <div class="w100 txt_r">
              <span class="cl_linkDef cursor_p" id="value_content_type_switch"></span>
            </div>

            <div id="input_area_html" class="w100">
              <span id="bbsComposeBodyContent"></span>

              <span id="attachmentFileErrorArea2"></span>

              <div class="m0 p0" id="attachment_FormArea2">
                <textarea name="bbs090valueHtml" id="inputstr_tinymce"><bean:write name="bbs090Form" property="bbs090valueHtml" /></textarea>
              </div>
              <input type="file" id="attachmentAreaBtn2" class="display_none" onchange="attachFileSelect(this, '2');" multiple="">
            </div>
            <textarea name="bbs090value" class="w98" rows="10" id="input_area_plain" onkeyup="showLengthStr(value, <%=maxLengthValue%>, 'inputlength');"><bean:write name="bbs090Form" property="bbs090value" /></textarea>

            <div id="plain_text_count" class="fs_13">
              <gsmsg:write key="cmn.current.characters" />:<span id="inputlength">0</span>&nbsp;/&nbsp;<%=maxLengthValue%>
              <gsmsg:write key="cmn.character" />
            </div>
          </td>
        </tr>

        <tr>
          <th class="txt_l">
            <gsmsg:write key="cmn.attached" />
          </th>
          <td>
            <attachmentFile:filearea
            mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
            pluginId="<%= GSConstBulletin.PLUGIN_ID_BULLETIN %>"
            tempDirId="<%= tempDirId %>"
            formId="1" />

          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <logic:notEqual name="bbs090Form" property="bbs090cmdMode" value="<%=String.valueOf(GSConstBulletin.BBSCMDMODE_EDIT)%>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.save.draft" />" onClick="buttonPush('draft');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_save_soukou.png" alt="<gsmsg:write key="cmn.save.draft" />">
            <gsmsg:write key="cmn.save.draft" />
          </button>
        </logic:notEqual>
        <button type="button" class="baseBtn" value="ＯＫ" onClick="buttonPush('moveWriteConfirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backWriteList');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>
