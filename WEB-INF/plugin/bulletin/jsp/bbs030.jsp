<%@page import="jp.groupsession.v2.struts.msg.GsMessage"%>
<%@page import="jp.groupsession.v2.cmn.GSConstCommon"%>
<%@page import="jp.groupsession.v2.bbs.GSConstBulletin"%>
<%@page import="jp.groupsession.v2.cmn.GSConstBBS"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>


<!DOCTYPE html>

<%
  String maxLengthComment = String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.MAX_LENGTH_FORUMCOMMENT);
  String maxLengthTemplate = String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.MAX_LENGTH_THREVALUE);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="bbs.bbs030.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tinymce-5.10.3/tinymce.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../bulletin/js/bbs_tinymce.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bulletin/js/bbs030.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/common.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../bulletin/css/bulletin.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body onload="showLengthId($('#inputstr')[0], <%=maxLengthComment%>, 'inputlengthComment'); showLengthId($('#input_area_plain')[0], <%=maxLengthTemplate%>, 'inputlengthTemplate'); changeInitArea(); changeInputDiskSize(<bean:write name="bbs030Form" property="bbs030diskSize" />); changeWarnDisk(<bean:write name="bbs030Form" property="bbs030warnDisk" />); doScroll();">

  <html:form action="/bulletin/bbs030">

    <input type="hidden" name="CMD" value="">
    <html:hidden name="bbs030Form" property="backScreen" />
    <html:hidden name="bbs030Form" property="s_key" />
    <html:hidden name="bbs030Form" property="tempDirId" />
    <html:hidden name="bbs030Form" property="bbs010page1" />
    <html:hidden name="bbs030Form" property="bbs020page1" />
    <html:hidden name="bbs030Form" property="bbs020forumSid" />
    <html:hidden name="bbs030Form" property="bbs020indexRadio" />
    <html:hidden name="bbs030Form" property="bbs030cmdMode" />
    <html:hidden name="bbs030Form" property="bbs030ImageName" />
    <html:hidden name="bbs030Form" property="bbs030ImageSaveName" />
    <html:hidden name="bbs030Form" property="bbs030ForumLevel" />
    <html:hidden name="bbs030Form" property="bbs030ParentForumSid" />
    <html:hidden name="bbs030Form" property="bbs030ScrollPosition" />
    <html:hidden name="bbs030Form" property="bbs190ScrollPosition" />
    <html:hidden name="bbs030Form" property="bbs030HaveChildForumFlg" />
    <html:hidden name="bbs030Form" property="bbs030templateType" />

    <logic:notEmpty name="bbs030Form" property="bbs030DisabledForumSid">
      <logic:iterate id="fsid" name="bbs030Form" property="bbs030DisabledForumSid">
        <input type="hidden" name="bbs030DisabledForumSid" value="<bean:write name="fsid" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="bbs030Form" property="bbs030BanUserSidList">
      <logic:iterate id="usid" name="bbs030Form" property="bbs030BanUserSidList">
        <input type="hidden" name="bbs030BanUserSid" value="<bean:write name="usid" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="bbs030Form" property="bbs030BanGroupSidList">
      <logic:iterate id="gsid" name="bbs030Form" property="bbs030BanGroupSidList">
        <input type="hidden" name="bbs030BanGroupSid" value="<bean:write name="gsid" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="bbs030Form" property="bbs030DisableGroupSidList">
      <logic:iterate id="gsid" name="bbs030Form" property="bbs030DisableGroupSidList">
        <input type="hidden" name="bbs030DisableGroupSid" value="<bean:write name="gsid" />">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <!-- BODY -->

    <div class="kanriPageTitle w85 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <logic:equal name="bbs030Form" property="bbs030cmdMode" scope="request" value="1"><span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.bulletin" /></span><gsmsg:write key="bbs.bbs030.16" /></logic:equal>
          <logic:notEqual name="bbs030Form" property="bbs030cmdMode" scope="request" value="1"><span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.bulletin" /></span><gsmsg:write key="bbs.bbs030.2" /></logic:notEqual>
        </li>
        <li>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('forumConfirm');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>

          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backForumList');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </li>
      </ul>
    </div>
    <div class="wrapper w85 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left w100">
        <tr>
          <th class="w25">
            <gsmsg:write key="bbs.4" />
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="w75">
            <html:text name="bbs030Form" property="bbs030forumName" maxlength="70" styleClass="wp500" />
          </td>
        </tr>

        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.comment" />
          </th>
          <td class="w75">
            <div>
            <textarea name="bbs030comment" class="wp500" rows="10" onkeyup="showLengthStr(value, <%=maxLengthComment%>, 'inputlengthComment');" id="inputstr"><bean:write name="bbs030Form" property="bbs030comment" /></textarea>
            </div>
            <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlengthComment" class="formCounter">0</span>&nbsp;/
            <span class="formCounter"><%=maxLengthComment%>
              <gsmsg:write key="cmn.character" />
            </span>
          </td>
        </tr>

        <!-- フォーラム設定 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="bbs.bbs030.17" />
          </th>
          <td class="w75">
            <div>
              <gsmsg:write key="bbs.bbs030.18" />
            </div>
            <div class="cl_fontWarn fw_b fs_13">※
              <gsmsg:write key="bbs.bbs030.19" />
            </div>
            <div class="cl_fontWarn fw_b fs_13">※
              <gsmsg:write key="bbs.bbs030.29" />
            </div>
            <div class="iframe_border_hp bor1">
            <iframe src="../bulletin/bbs190.do?selectLevel=<bean:write name="bbs030Form" property="bbs030ForumLevel" scope="request"/>&checkForum=<bean:write name="bbs030Form" property="bbs030ParentForumSid" scope="request"/>" class="w100 iframe_hp" frameborder="0" name="ctgFrame" root="1">
              <gsmsg:write key="user.32" />
            </iframe>
            </div>
          </td>
        </tr>
        <!-- メンバー -->
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.member" />
          </th>
          <td class="w75">
            <div class="verAlignMid" id="selectFollowParentMemArea">
              <html:radio name="bbs030Form" property="bbs030FollowParentMemFlg" value="1" styleId="followParentMem_01" />
              <label for="followParentMem_01">
                <gsmsg:write key="bbs.bbs030.22" />
              </label>

              <html:radio styleClass="ml10" name="bbs030Form" property="bbs030FollowParentMemFlg" value="0" styleId="followParentMem_00" />
              <label for="followParentMem_00">
                <gsmsg:write key="bbs.bbs030.23" />
              </label>
            </div>
            <bean:define id="selectLabel1"><gsmsg:write key="cmn.add.edit.delete"/></bean:define>
            <bean:define id="selectLabel2"><gsmsg:write key="cmn.reading"/></bean:define>
            <div id="selectMemberArea" class="pos_rel">
              <logic:equal name="bbs030Form" property="bbs030ParentForumSid" value="<%=String.valueOf(GSConstBulletin.BBS_DEFAULT_PFORUM_SID) %>">
                <ui:usrgrpselector name="bbs030Form" property="bbs030memberSelectForRoot" styleClass="hp400"
                 onchange=""
                 />
              </logic:equal>
              <logic:notEqual name="bbs030Form" property="bbs030ParentForumSid" value="<%=String.valueOf(GSConstBulletin.BBS_DEFAULT_PFORUM_SID) %>">
                <div class="pos_abs">
                  <div class="fs_13">※<gsmsg:write key="bbs.bbs030.20" />
                  </div>
                </div>
                <ui:multiselector name="bbs030Form" property="bbs030memberSelect" styleClass="hp400"
                 onchange=""
                 />
              </logic:notEqual>
              <logic:equal name="bbs030Form" property="bbs030cmdMode" value="1">
                <logic:equal name="bbs030Form" property="bbs030HaveChildForumFlg" value="1">
                  <div class="verAlignMid">
                    <html:checkbox name="bbs030Form" property="bbs030AdaptChildMemFlg" value="1" styleId="adaptChildMemFlg" />
                    <label for="adaptChildMemFlg">
                      <gsmsg:write key="bbs.bbs030.21" />
                    </label>
                  </div>
                </logic:equal>
              </logic:equal>
            </div>
          </td>
        </tr>
        <!-- フォーラム管理者 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="bbs.35" />
          </th>
          <td class="w75">
              <ui:multiselector name="bbs030Form" property="bbs030adminSelect" styleClass="hp200"
               onchange=""
               />
          </td>
        </tr>

        <!-- アイコン -->
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.icon" />
          </th>
          </td>
          <td class="w75">
            <div>
            <logic:equal name="bbs030Form" property="bbs030ImageName" value="">
              <img class="classic-display" src="../bulletin/images/classic/icon_forum.gif" name="pitctImage" alt="<gsmsg:write key="bbs.3" />">
              <img class="original-display" src="../bulletin/images/original/menu_icon_single.png" alt="<gsmsg:write key="bbs.3" />">
            </logic:equal>
            <logic:notEqual name="bbs030Form" property="bbs030ImageName" value="">
              <img src="../bulletin/bbs030.do?CMD=getImageFile&tempDirId=<bean:write name="bbs030Form" property="tempDirId" />" name="pitctImage" class="wp30hp30" alt="<gsmsg:write key="cmn.icon" />"  onload="initImageView('pitctImage');">
            </logic:notEqual>
            </div>
            <gsmsg:write key="cmn.icon.size" />

            <div>
              <bean:define id="tempDirId" name="bbs030Form" property="tempDirId" type="java.lang.String"/>
              <attachmentFile:filearea
              mode="<%= String.valueOf(GSConstCommon.CMN110MODE_GAZOU) %>"
              pluginId="<%= GSConstBulletin.PLUGIN_ID_BULLETIN %>"
              tempDirId="<%= tempDirId %>"
              delBtn="true"
              delBtnAction="buttonPush('bbs030tempdeleteMark');"
              fileList="false" />
            </div>
          </td>
        </tr>

        <tr>
          <th class="w25">
            <gsmsg:write key="bbs.6" />
          </th>
          <td class="w75">
            <div class="fs_13 mb10">
              <gsmsg:write key="bbs.bbs030.3" />
            </div>
            <span class="verAlignMid">
              <html:radio name="bbs030Form" styleId="bbs030reply_1" property="bbs030reply" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRE_REPLY_NO)%>" />
              <label for="bbs030reply_1">
                <span>
                  <gsmsg:write key="cmn.not.permit" />
                </span>
              </label>
              <html:radio styleClass="ml10" name="bbs030Form" styleId="bbs030reply_0" property="bbs030reply" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRE_REPLY_YES)%>" />
              <label for="bbs030reply_0">
                <span>
                  <gsmsg:write key="cmn.permit" />
                </span>
              </label>
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <gsmsg:write key="bbs.7" />
          </th>
          <td class="w75">
            <div class="fs_13 mb10">
              <gsmsg:write key="bbs.bbs030.4" />
            </div>
            <span class="verAlignMid">
              <html:radio name="bbs030Form" styleId="bbs030read_0" property="bbs030read" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.NEWUSER_THRE_VIEW_NO)%>" />
              <label for="bbs030read_0">
                <span>
                  <gsmsg:write key="cmn.read.yet" />
                </span>
              </label>
              <html:radio styleClass="ml10" name="bbs030Form" styleId="bbs030read_1" property="bbs030read" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.NEWUSER_THRE_VIEW_YES)%>" />
              <label for="bbs030read_1">
                <span>
                  <gsmsg:write key="cmn.read.already" />
                </span>
              </label>
            </span>
          </td>
        </tr>

        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.all.read" />
          </th>
          <td class="w75">
            <div class="fs_13 mb10">
              <gsmsg:write key="bbs.bbs030.5" />
            </div>
            <span class="verAlignMid">
              <html:radio name="bbs030Form" styleId="bbs030mread_1" property="bbs030mread" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRE_REPLY_NO)%>" />
              <label for="bbs030mread_1">
                <span>
                  <gsmsg:write key="cmn.not.permit" />
                </span>
              </label>
              <html:radio styleClass="ml10" name="bbs030Form" styleId="bbs030mread_0" property="bbs030mread" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRE_REPLY_YES)%>" />
              <label for="bbs030mread_0">
                <span>
                  <gsmsg:write key="cmn.permit" />
                </span>
              </label>
            </span>
          </td>
        </tr>

        <tr>
          <th class="w25">
            <gsmsg:write key="wml.87" />
          <td class="w75">
            <span class="verAlignMid">
              <html:radio name="bbs030Form" property="bbs030diskSize" styleId="disk1" value="0" onclick="changeInputDiskSize(0);" />
              <label for="disk1">
                <gsmsg:write key="wml.31" />
              </label>
              <html:radio styleClass="ml10" name="bbs030Form" property="bbs030diskSize" styleId="disk2" value="1" onclick="changeInputDiskSize(1);" />
              <label for="disk2">
                <gsmsg:write key="wml.32" />
              </label>
            <span id="inputDiskSize">
              <html:text name="bbs030Form" property="bbs030diskSizeLimit" styleClass="wp70 ml10" maxlength="6" />MB&nbsp;
            </span>
          </td>
        </tr>

        <tr id="warnDiskArea">
          <th class="w25">
            <gsmsg:write key="wml.wml150.15" />
          </th>
          <td class="w75">
            <span class="verAlignMid">
              <html:radio name="bbs030Form" property="bbs030warnDisk" styleId="warnDisk1" value="0" onclick="changeWarnDisk(0);" />
              <label for="warnDisk1">
                <gsmsg:write key="cmn.notset" />
              </label>
              <html:radio styleClass="ml10" name="bbs030Form" property="bbs030warnDisk" styleId="warnDisk2" value="1" onclick="changeWarnDisk(1);" />
              <label for="warnDisk2">
                <gsmsg:write key="cmn.warning2" />
              </label>
            <span class="ml10" id="warnDiskThresholdArea">
              <span>
                <gsmsg:write key="cmn.threshold" />:&nbsp;
                <html:select name="bbs030Form" property="bbs030warnDiskThreshold">
                  <html:optionsCollection name="bbs030Form" property="warnDiskThresholdList" value="value" label="label" />
                </html:select>%
              </span>
              </span>
              </span>
              <div class="fs_13">
                <gsmsg:write key="bbs.bbs030.9" />
              </div>
          </td>
        </tr>

        <tr>
          <th class="w25">
            <gsmsg:write key="bbs.bbs030.6" />
          </th>
          <td class="w75">
            <div class="fs_13 mb5">
              <gsmsg:write key="bbs.bbs030.7" />
            </div>
            <span class="verAlignMid mr5">
              <html:radio name="bbs030Form" onclick="changeInitArea();" styleId="bbs030templateKbn_1" property="bbs030templateKbn" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRE_TEMPLATE_NO)%>" />
              <label for="bbs030templateKbn_1">
                <span>
                  <gsmsg:write key="cmn.noset" />
                </span>
              </label>
              <html:radio styleClass="ml10" name="bbs030Form" onclick="changeInitArea();" styleId="bbs030templateKbn_0" property="bbs030templateKbn" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRE_TEMPLATE_YES)%>" />
              <label for="bbs030templateKbn_0">
                <span>
                  <gsmsg:write key="cmn.setting.do" />
                </span>
              </label>
            </span>
            <div id="templateArea" class="mt10">
              <div class="verAlignMid">
                <html:checkbox name="bbs030Form" property="bbs030templateWriteKbn" styleId="toukouCheck" value="1" />
                <label for="toukouCheck">
                  <gsmsg:write key="bbs.bbs030.8" />
                </label>
              </div>
              <div class="w100 txt_r">
                <span id="value_content_type_switch" class="cl_linkDef cursor_p"></span>
              </div>

              <div id="input_area_html">
                <textarea name="bbs030templateHtml" id="inputstr_tinymce"><bean:write name="bbs030Form" property="bbs030templateHtml" /></textarea>
              </div>

              <textarea name="bbs030template" class="w100" cols="50" rows="10" id="input_area_plain" onkeyup="showLengthStr(value, <%=maxLengthTemplate%>, 'inputlengthTemplate');"><bean:write name="bbs030Form" property="bbs030template" /></textarea>

              <div id="template_plain_text_count">
                <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlengthTemplate" class="formCounter">0</span> /
                <span class="formCounter"><%=maxLengthTemplate%><gsmsg:write key="cmn.character" />
                </span>
              </div>
            </div>
          </td>
        </tr>

        <tr>
          <th class="w25">
            <gsmsg:write key="bbs.12" />
          </th>
          <td class="w75">
            <span class="verAlignMid">
              <html:radio name="bbs030Form" property="bbs030LimitDisable" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_DISABLE)%>" styleId="limiton1" onclick="doInit();" />
              <label for="limiton1">
                <gsmsg:write key="fil.107" />
              </label>
              <html:radio styleClass="ml10" name="bbs030Form" property="bbs030LimitDisable" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_ENABLE)%>" styleId="limiton2" onclick="doInit();" />
              <label for="limiton2">
                <gsmsg:write key="fil.108" />
              </label>
            </span>
          </td>
        </tr>

        <tr id="inputUnlimitedLimit">
          <th class="w25">
            <gsmsg:write key="bbs.bbs030kn.3" />
          </th>
          <td class="w75">
            <div class="verAlignMid">
              <html:radio name="bbs030Form" property="bbs030UnlimitedFlg" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BFI_UNLIMITED_YES)%>" styleId="unlimited1" />
              <label for="unlimited1">
                <gsmsg:write key="cmn.permit" />
              </label>
              </span>
              <html:radio styleClass="ml10" name="bbs030Form" property="bbs030UnlimitedFlg" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BFI_UNLIMITED_NO)%>" styleId="unlimited2" />
              <label for="unlimited2">
                <gsmsg:write key="cmn.not.permit" />
              </label>
            </div>
          </td>
        </tr>

        <tr id="inputLimitEnableLimit">
          <th class="w25">
            <gsmsg:write key="bbs.bbs030.10" />
          </th>
          <td class="w75">
            <div class="verAlignMid">
              <span class="verAlignMid" id="unlimited">
                <html:radio name="bbs030Form" property="bbs030Limit" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_LIMIT_NO)%>" styleId="limit1" />
                <label class="mr10" for="limit1">
                  <gsmsg:write key="cmn.unlimited" />
                </label>
              </span>
              <html:radio name="bbs030Form" property="bbs030Limit" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_LIMIT_YES)%>" styleId="limit2" />
              <label class="mr10" for="limit2">
                <gsmsg:write key="bbs.bbs070.4" />
              </label>
              <span id="inputLimitDate">
                <html:text name="bbs030Form" property="bbs030LimitDate" styleClass="wp60" maxlength="3" style="width:59px;" />
                <gsmsg:write key="cmn.days.after2" />
              </span>
            </div>
          </td>
        </tr>

        <tr id="timeUnit">
          <th class="w25">
            <gsmsg:write key="bbs.bbs030.24" />
          </th>
          <td class="w75">
            <div class="verAlignMid">
              <html:radio name="bbs030Form" property="bbs030TimeUnit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstBBS.MINUTE_DIVISION5)%>" styleId="time1" />
              <label for="time1">
                <gsmsg:write key="bbs.bbs030.25" />
              </label>
              <html:radio styleClass="ml10" name="bbs030Form" property="bbs030TimeUnit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstBBS.MINUTE_DIVISION10)%>" styleId="time2" />
              <label for="time2">
                <gsmsg:write key="bbs.bbs030.26" />
              </label>
              <html:radio styleClass="ml10" name="bbs030Form" property="bbs030TimeUnit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstBBS.MINUTE_DIVISION15)%>" styleId="time3" />
              <label for="time3">
                <gsmsg:write key="bbs.bbs030.27" />
              </label>
          </td>

          <tr id="inputLimitEnableDate">
          <th class="w25">
            <gsmsg:write key="bbs.bbs030.11" />
          </th>
          <td class="w75">
            <div class="fs_13 mb10">
              <gsmsg:write key="bbs.bbs030.12" />
              <logic:equal name="bbs030Form" property="bbs030DspAtdelFlg" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.AUTO_DELETE_ON)%>">
                <gsmsg:write key="bbs.bbs030.13" />
                <bean:define id="dspAtdelYear" name="bbs030Form" property="bbs030DspAtdelYear" type="java.lang.Integer" />
                <bean:define id="dspAtdelMonth" name="bbs030Form" property="bbs030DspAtdelMonth" type="java.lang.Integer" />
                <div>
                  <gsmsg:write key="cmn.autodelete.setting" />&nbsp;:&nbsp;<gsmsg:write key="bbs.bbs030.15" arg0="<%=String.valueOf(dspAtdelYear)%>" arg1="<%=String.valueOf(dspAtdelMonth)%>" />
                </div>
              </logic:equal>
            </div>
            <span class="verAlignMid">
            <html:radio name="bbs030Form" property="bbs030Keep" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_KEEP_NO)%>" styleId="keep1" />
            <label for="keep1">
              <gsmsg:write key="cmn.noset" />
            </label>
            <html:radio styleClass="ml10" name="bbs030Form" property="bbs030Keep" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_KEEP_YES)%>" styleId="keep2" />
            <label for="keep2">
              <gsmsg:write key="cmn.setting.do" />
            </label>
            </span>

            <div class="mt20" id="inputKeepDate">
              <span class="mr5">
                <gsmsg:write key="bbs.bbs030.14" />
              </span>
              <html:select name="bbs030Form" property="bbs030KeepDateY">
                <html:optionsCollection name="bbs030Form" property="bbs030KeepDateYLabel" value="value" label="label" />
              </html:select>
              <html:select name="bbs030Form" property="bbs030KeepDateM">
                <html:optionsCollection name="bbs030Form" property="bbs030KeepDateMLabel" value="value" label="label" />
              </html:select>
            </div>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('forumConfirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>

        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backForumList');">
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
