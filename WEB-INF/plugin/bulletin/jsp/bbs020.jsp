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
<title>GROUPSESSION <gsmsg:write key="bbs.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bulletin/js/bbs020.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jtooltip.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../bulletin/css/bulletin.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/bulletin/bbs020">
    <input type="hidden" name="CMD" value="">
    <html:hidden name="bbs020Form" property="backScreen" />
    <html:hidden name="bbs020Form" property="s_key" />
    <html:hidden name="bbs020Form" property="bbs010page1" />
    <html:hidden name="bbs020Form" property="bbs020forumSid" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="cmn.bulletin" /> <gsmsg:write key="bbs.14" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" name="new" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('addForum');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('confMenu');">
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

      <div class="txt_l">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('up');">
          <gsmsg:write key="cmn.up" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('down');">
          <gsmsg:write key="cmn.down" />
        </button>
      </div>

      <logic:notEmpty name="bbs020Form" property="forumList">
        <bean:size id="count1" name="bbs020Form" property="bbsPageLabel" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <!-- フォーラム一覧ページング -->
          <div class="flo_r paging verAlignMid">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select property="bbs020page1" onchange="changePage(0);" styleClass="paging_combo">
              <html:optionsCollection name="bbs020Form" property="bbsPageLabel" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <!-- フォーラム一覧ヘッダー -->
      <div>
      <table class="w100">
        <tr>
          <td>
            <ul class="p0 m0">
              <li class="main_bbsList-header">
                <div class="flo_l txt_l forum_li_forumname_manage">
                  <gsmsg:write key="bbs.4" />
                </div>
                <div class="forum_title-right forum_li_right_manage_header">
                  <div class="flo_l txt_l forum_li_postnumber_manage">
                    <gsmsg:write key="bbs.5" />
                  </div>
                  <div class="flo_l txt_l forum_li_reply_manage">
                    <gsmsg:write key="bbs.6" />
                  </div>
                  <div class="flo_l txt_l forum_li_size_manage">
                    <gsmsg:write key="cmn.size" />
                  </div>
                  <div class="forum_li_button"></div>
                </div>
              </li>

              <logic:notEmpty name="bbs020Form" property="forumList">

                <logic:iterate id="forumMdl" name="bbs020Form" property="forumList" indexId="idx">
                  <bean:define id="bfiSid" name="forumMdl" property="bfiSid" />

                  <bean:define id="fLevel" name="forumMdl" property="forumLevel" />
                  <%
                      int intLevel = ((Integer) fLevel).intValue();
                    %>
                  <%
                      int dep = 36 * intLevel - 36;
                    %>


                  <!-- 階層表現 -->
                  <li class="forumList_content pt0" style="margin-left:<%=dep%>px;">

                    <!-- フォーラム左 -->
                    <div id="imgClick" class="flo_l txt_l hp40 verAlignMid">
                      <!-- ラジオボタン -->
                      <html:radio property="bbs020indexRadio" value="<%=String.valueOf(bfiSid)%>" styleId="<%=String.valueOf(bfiSid)%>" styleClass="forum_li_radio" />
                      <label for="<%=String.valueOf(bfiSid)%>">
                        <%-- フォーラム画像default --%>
                        <div class="forum_icon-list display_inline">
                          <logic:equal name="forumMdl" property="imgBinSid" value="0">
                            <img class="classic-display wp30hp30" src="../bulletin/images/classic/icon_forum.gif" alt="<gsmsg:write key="bbs.3" />">
                            <img class="original-display wp30hp30" src="../bulletin/images/original/icon_forum_32.png" alt="<gsmsg:write key="bbs.3" />">
                          </logic:equal>
                          <%-- フォーラム画像original --%>
                          <logic:notEqual name="forumMdl" property="imgBinSid" value="0">
                            <img class="wp30hp30" src="../bulletin/bbs010.do?CMD=getImageFile&bbs010BinSid=<bean:write name="forumMdl" property="imgBinSid" />&bbs010ForSid=<bean:write name="forumMdl" property="bfiSid" />" alt="<gsmsg:write key="bbs.3" />">
                          </logic:notEqual>
                        </div>
                      </label>
                              <!-- フォーラムタイトル -->
                    <label for="<%=String.valueOf(bfiSid)%>">
                      <div class="txt_l">
                        <a>
                          <span class="tooltips">
                            <gsmsg:write key="bbs.2" />:
                            <bean:write name="forumMdl" property="bfsThreCnt" />
                            &nbsp;&nbsp;
                            <gsmsg:write key="bbs.16" />:
                            <bean:write name="forumMdl" property="writeCnt" />
                            <br>
                            <bean:write name="forumMdl" property="bfiCmtView" filter="false" />
                          </span>
                        </a>
                      </div>
                          <span class="cl_fontBody">
                            <bean:write name="forumMdl" property="bfiName" />
                          </span>
                    </label>
                    </div>

                    <%-- フォーラム右 --%>
                    <div class="forum_title-right forum_li_right_manage_content hp40 verAlignMid">
                      <!-- 投稿数 -->
                      <div class="flo_l txt_l forum_li_postnumber_manage forum_li_text">
                        <bean:write name="forumMdl" property="writeCnt" />
                      </div>
                      <!-- 返信許可 -->
                      <div class="flo_l txt_l forum_li_reply_manage forum_li_text">
                        <logic:equal name="forumMdl" property="bfiReply" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRE_REPLY_YES)%>">
                          <gsmsg:write key="cmn.accepted" />
                        </logic:equal>
                        <logic:equal name="forumMdl" property="bfiReply" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRE_REPLY_NO)%>">
                          <gsmsg:write key="cmn.not" />
                        </logic:equal>
                      </div>
                      <!-- サイズ -->
                      <div class="flo_l txt_l forum_li_size_manage forum_li_text">
                        <bean:write name="forumMdl" property="viewBfsSize" />
                      </div>

                      <div class="txt_l">
                        <!-- 編集ボタン -->
                        <button type="button" class="ml10 baseBtn" value="<gsmsg:write key="cmn.edit" />" onClick="buttonPushWithId('editForum', '<bean:write name="forumMdl" property="bfiSid" />');">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                          <gsmsg:write key="cmn.edit" />
                        </button>
                        <logic:equal name="forumMdl" property="numberOfChild" value="0">
                          <!-- 削除ボタン -->
                          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPushWithId('delForum', '<bean:write name="forumMdl" property="bfiSid" />');">
                            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                            <gsmsg:write key="cmn.delete" />
                          </button>
                        </logic:equal>
                      </div>
                    </div>
                  </li>

                </logic:iterate>
              </logic:notEmpty>
            </ul>
          </td>
        </tr>
      </table>
      </div>

      <!-- フォーラム一覧ページング -->
      <logic:notEmpty name="bbs020Form" property="forumList">
        <bean:size id="count1" name="bbs020Form" property="bbsPageLabel" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="flo_r paging verAlignMid">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select property="bbs020page2" onchange="changePage(1);" styleClass="paging_combo">
              <html:optionsCollection name="bbs020Form" property="bbsPageLabel" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>
    </div>


    </div>



  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>
