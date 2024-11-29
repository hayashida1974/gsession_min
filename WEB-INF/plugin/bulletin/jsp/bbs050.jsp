<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="bbs.bbs050.1" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bulletin/js/bbs050.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/bulletin/bbs050">
    <input type="hidden" name="CMD" value="">
    <html:hidden name="bbs050Form" property="backScreen" />
    <html:hidden name="bbs050Form" property="s_key" />
    <html:hidden name="bbs050Form" property="bbs010page1" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.preferences2" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.bulletin" /></span><gsmsg:write key="cmn.display.settings" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('bbsPsConf');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backBBSList');">
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
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left">
        <tr>
          <th class="w25">
            <gsmsg:write key="bbs.bbs050.2" />
          </th>
          <td class="w75">
            <gsmsg:write key="bbs.bbs050.3" />
            <div class="mt5">
              <html:select property="bbs050forumCnt">
                <html:optionsCollection name="bbs050Form" property="bbs050forumCntLabel" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.number" />
            </div>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="bbs.bbs050.4" />
          </th>
          <td>
            <gsmsg:write key="bbs.bbs050.5" />
            <div class="mt5">
              <html:select property="bbs050threCnt">
                <html:optionsCollection name="bbs050Form" property="bbs050threCntLabel" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.number" />
            </div>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="bbs.bbs050.6" />
          </th>
          <td>
            <gsmsg:write key="bbs.bbs050.7" />
            <div class="mt5">
              <html:select property="bbs050writeCnt">
                <html:optionsCollection name="bbs050Form" property="bbs050writeCntLabel" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.number" />
            </div>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="bbs.bbs050.8" />
          </th>
          <td>
            <div class="verAlignMid">
              <gsmsg:write key="bbs.bbs050.9" />
              <div>
              <img class="classic-display" src="../bulletin/images/classic/icon_new.gif" alt="<gsmsg:write key="bbs.bbsMain.6" />">
              <span class="labelNew original-display"><gsmsg:write key="bbs.bbsMain.6" /></span>
              </div>
              <gsmsg:write key="bbs.bbs050.10" />
            </div>
            <div class="mt5">
            <div>
              <gsmsg:write key="bbs.bbs050.11" />
            </div>
              <html:select property="bbs050newCnt">
                <html:optionsCollection name="bbs050Form" property="bbs050newCntLabel" value="value" label="label" />
              </html:select>
            </div>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="bbs.bbs050.17" />
          </th>
          <td>
            <gsmsg:write key="bbs.bbs050.18" />
            <div class="mt5">
              <span class="verAlignMid">
                <html:radio name="bbs050Form" styleId="bbs050wrtOrder_01" property="bbs050wrtOrder" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_WRTLIST_ORDER_ASC)%>" />
                <label for="bbs050wrtOrder_01">
                  <gsmsg:write key="cmn.order.asc" />
                </label>
                <html:radio styleClass="ml10" name="bbs050Form" styleId="bbs050wrtOrder_02" property="bbs050wrtOrder" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_WRTLIST_ORDER_DESC)%>" />
                <label for="bbs050wrtOrder_02">
                  <gsmsg:write key="cmn.order.desc" />
                </label>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="bbs.bbs050.19" />
          </th>
          <td>
            <gsmsg:write key="bbs.bbs050.20" />
            <div class="mt5">
              <span class="verAlignMid">
                <html:radio name="bbs050Form" styleId="bbs050threImage_01" property="bbs050threImage" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_IMAGE_DSP)%>" />
                <label for="bbs050threImage_01">
                  <gsmsg:write key="cmn.show" />
                </label>
                <html:radio styleClass="ml10" name="bbs050Form" styleId="bbs050threImage_02" property="bbs050threImage" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_IMAGE_NOT_DSP)%>" />
                <label for="bbs050threImage_02">
                  <gsmsg:write key="cmn.hide" />
                </label>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="bbs.bbs050.26" />
          </th>
          <td>
            <gsmsg:write key="bbs.bbs050.27" />
            <div class="mt5">
              <span class="verAlignMid">
                <html:radio name="bbs050Form" styleId="bbs050tempImage_01" property="bbs050tempImage" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_IMAGE_TEMP_DSP)%>" />
                <label for="bbs050tempImage_01">
                  <gsmsg:write key="cmn.show" />
                </label>
                <html:radio styleClass="ml10" name="bbs050Form" styleId="bbs050tempImage_02" property="bbs050tempImage" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_IMAGE_TEMP_NOT_DSP)%>" />
                <label for="bbs050tempImage_02">
                  <gsmsg:write key="cmn.hide" />
                </label>
              </span>
            </div>
          </td>
        </tr>
        <!-- [サブコンテンツ] 新着スレッド一覧表示設定 -->
        <tr>
          <th>
            <gsmsg:write key="bbs.bbs050.25" />
            <br>
            <gsmsg:write key="bbs.bbs010.2" />
          </th>
          <td>
            <gsmsg:write key="bbs.bbs050.22" />
            <div class="mt5">
              <span class="verAlignMid">
                <html:radio name="bbs050Form" styleId="bbs050threadFlg_01" property="bbs050threadFlg" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRED_DSP)%>" />
                <label for="bbs050threadFlg_01">
                  <gsmsg:write key="cmn.show" />
                </label>
                <html:radio styleClass="ml10" name="bbs050Form" styleId="bbs050threadFlg_02" property="bbs050threadFlg" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRED_NOT_DSP)%>" />
                <label for="bbs050threadFlg_02">
                  <gsmsg:write key="cmn.hide" />
                </label>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="bbs.bbs050.25" />
            <br>
            <gsmsg:write key="bbs.1" />
          </th>
          <td>
            <gsmsg:write key="bbs.bbs050.23" />
            <div class="mt5">
              <span class="verAlignMid">
                <html:radio name="bbs050Form" styleId="bbs050forumFlg_01" property="bbs050forumFlg" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_FORUM_DSP)%>" />
                <label for="bbs050forumFlg_01">
                  <gsmsg:write key="cmn.show" />
                </label>
                <html:radio styleClass="ml10" name="bbs050Form" styleId="bbs050forumFlg_02" property="bbs050forumFlg" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_FORUM_NOT_DSP)%>" />
                <label for="bbs050forumFlg_02">
                  <gsmsg:write key="cmn.hide" />
                </label>
              </span>
            </div>
          </td>
        </tr>
        <!-- [サブコンテンツ] 未読スレッド一覧表示設定 -->
        <tr>
          <th>
            <gsmsg:write key="bbs.bbs050.25" />
            <br>
            <gsmsg:write key="bbs.10" />
          </th>
          <td>
            <gsmsg:write key="bbs.bbs050.24" />
            <div class="mt5">
              <span class="verAlignMid">
                <html:radio name="bbs050Form" styleId="bbs050midokuTrdFlg_01" property="bbs050midokuTrdFlg" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_MIDOKU_TRD_DSP)%>" />
                <label for="bbs050midokuTrdFlg_01">
                  <gsmsg:write key="cmn.show" />
                </label>
                <html:radio styleClass="ml10" name="bbs050Form" styleId="bbs050midokuTrdFlg_02" property="bbs050midokuTrdFlg" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_MIDOKU_TRD_NOT_DSP)%>" />
                <label for="bbs050midokuTrdFlg_02">
                  <gsmsg:write key="cmn.hide" />
                </label>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <th class="w30">
            <gsmsg:write key="bbs.bbs050.13" />
          </th>
          <td class="w80">
            <gsmsg:write key="bbs.bbs050.14" />
            <div class="mt5">
              <html:select property="bbs050threMainCnt">
              <html:optionsCollection name="bbs050Form" property="bbs050threMainCntLabel" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.number" />
            </div>
          </td>
        </tr>
        <tr>
          <th class="w30">
            <gsmsg:write key="bbs.bbs050.15" />
          </th>
          <td class="w70">
            <gsmsg:write key="bbs.bbs050.16" /><br>
            <div class="mt5">
              <span class="verAlignMid">
                <html:radio name="bbs050Form" styleId="bbs050mainChkedDsp_01" property="bbs050mainChkedDsp" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_CHECKED_DSP) %>" /><label for="bbs050mainChkedDsp_01"><gsmsg:write key="cmn.show" /></label>
                <html:radio styleClass="ml10" name="bbs050Form" styleId="bbs050mainChkedDsp_02" property="bbs050mainChkedDsp" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_CHECKED_NOT_DSP) %>" /><label for="bbs050mainChkedDsp_02"><gsmsg:write key="cmn.hide" /></label>
              </span>
            </div>
          </td>
        </tr>
      </table>
      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('bbsPsConf');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backBBSList');">
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