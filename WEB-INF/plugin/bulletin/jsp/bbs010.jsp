<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.lang.Integer"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="bbs.bbs010.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bulletin/js/bbs010.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jtooltip.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../bulletin/css/bulletin.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form styleId="bbs010Form" action="/bulletin/bbs010">
    <input type="hidden" name="CMD" value="search">
    <html:hidden name="bbs010Form" property="bbs010forumSid" />

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
        <li class="pageTitle_subFont">
          <gsmsg:write key="bbs.1" />
        </li>
        <li>
        </li>
      </ul>
    </div>

    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>

    <div class="wrapper_2column w100">
      <logic:notEmpty name="bbs010Form" property="shinThredList">

        <div class="side_multi-left bgC_none">
          <table class="table-top m0">
            <tr>
              <th class="w100 side_header txt_l">
                <img class="classic-display" src="../bulletin/images/classic/icon_thread.gif" alt="<gsmsg:write key="bbs.2" />">
                <gsmsg:write key="bbs.bbs010.2" />
              </th>
            </tr>
            <logic:iterate id="thredMdl" name="bbs010Form" property="shinThredList" indexId="idy">
              <tr class="w100">
                <td class="w100">
                  <div class="w100 lh130">
                    <%-- 未読・既読のスタイル --%>
                    <% String fTitleClass = "cl_linkDef cl_linkHoverChange fw_bold"; %>
                    <% String tTitleClass = "cl_linkDef cl_linkHoverChange fw_bold"; %>
                    <logic:equal name="thredMdl" property="f_ReadFlg" value="1">
                      <% fTitleClass = "cl_linkVisit cl_linkHoverChange fw_b"; %>
                    </logic:equal>
                    <logic:equal name="thredMdl" property="t_ReadFlg" value="1">
                      <% tTitleClass = "cl_linkVisit cl_linkHoverChange fw_b"; %>
                    </logic:equal>
                    <div>
                      <%-- フォーラム画像default --%>
                      <logic:equal name="thredMdl" property="imgBinSid" value="0">
                        <img class="classic-display wp20hp20" src="../bulletin/images/classic/icon_forum.gif" alt="<gsmsg:write key="bbs.3" />">
                        <img class="original-display wp20hp20" src="../bulletin/images/original/icon_forum_32.png" alt="<gsmsg:write key="bbs.3" />">
                      </logic:equal>
                      <%-- フォーラム画像original --%>
                      <logic:notEqual name="thredMdl" property="imgBinSid" value="0">
                        <img width="20" height="20" src="../bulletin/bbs010.do?CMD=getImageFile&bbs010BinSid=<bean:write name="thredMdl" property="imgBinSid" />&bbs010ForSid=<bean:write name="thredMdl" property="bfiSid" />" alt="<gsmsg:write key="bbs.3" />">
                      </logic:notEqual>
                      <%-- フォーラム名 --%>
                      <a href="javascript:clickForum(<bean:write name="thredMdl" property="bfiSid" />);" class="ml5 fs_12 fw_n <%= fTitleClass %>">
                        <bean:write name="thredMdl" property="bfiName" filter="false" />
                      </a>
                    </div>
                  </div>
                  <div class="w100 mt5 lh130">
                    <a href="../bulletin/bbs060.do?bbs010forumSid=<bean:write name="thredMdl" property="bfiSid" />&threadSid=<bean:write name="thredMdl" property="btiSid" />"  class="fs_13 lh130 <%= tTitleClass %>">
                        <%-- 重要度 --%>
                        <logic:equal name="thredMdl" property="threImportance" value="1">
                          <img class="classic-display" src="../common/images/classic/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                          <img class="original-display" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                        </logic:equal>
                        <%-- 添付ファイルアイコン --%>
                        <logic:equal name="thredMdl" property="btsTempflg" value="1">
                          <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attach.file" />">
                          <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attach.file" />">
                        </logic:equal>
                        <%-- スレッド名 --%>
                        <bean:write name="thredMdl" property="btiTitle" filter="false" />
                    </a>
                  </div>
                  <div class="cl_fontMiddle side_userName mt5">
                    <div class="w50 flo_l">
                      <bean:define id="cbGrpSid" name="thredMdl" property="grpSid" type="java.lang.Integer" />
                      <% if (cbGrpSid.intValue() > 0) { %>
                      <logic:equal name="thredMdl" property="grpJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
                        <s><bean:write name="thredMdl" property="grpName" /></s>
                      </logic:equal>
                      <logic:notEqual name="thredMdl" property="grpJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
                        <bean:write name="thredMdl" property="grpName" />
                      </logic:notEqual>
                      <% } else { %>
                      <logic:equal name="thredMdl" property="userJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
                        <s><bean:write name="thredMdl" property="userName" /></s>
                      </logic:equal>
                      <logic:notEqual name="thredMdl" property="userJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
                        <bean:define id="yukoFlg" name="thredMdl" property="userYukoKbn" type="java.lang.Integer" />
                        <% if (yukoFlg.intValue() == 1) { %>
                        <span class="mukoUser">
                          <bean:write name="thredMdl" property="userName" />
                        </span>
                        <% } else { %>
                        <span class="txt_r">
                          <bean:write name="thredMdl" property="userName" />
                        </span>
                        <% } %>
                      </logic:notEqual>
                      <% } %>
                    </div>
                    <div class="txt_r w50 flo_l">
                      <bean:write name="thredMdl" property="strWriteDate" />
                    </div>
                  </div>
                </td>
              </tr>
            </logic:iterate>
          </table>
        </div>
      </logic:notEmpty>
      <logic:notEmpty name="bbs010Form" property="shinThredList">
        <div class="main">
      </logic:notEmpty>
      <logic:empty name="bbs010Form" property="shinThredList">
        <div class="w100">
      </logic:empty>

    <div class="txt_r">
      <html:text name="bbs010Form" property="s_key" maxlength="50" />
      <button type="submit" name="btn_prjadd" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');" class="baseBtn">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <gsmsg:write key="cmn.search" />
      </button>
      <button type="button" name="btn_prjadd" value="<gsmsg:write key="cmn.advanced.search" />" onClick="buttonPush('searchDtl');" class="baseBtn">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_advanced_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
        <gsmsg:write key="cmn.advanced.search" />
      </button>
      <button type="submit" name="btn_prjadd" value="<gsmsg:write key="bbs.bbs220.1" />" onClick="buttonPush('soukou');" class="baseBtn">
        <gsmsg:write key="bbs.bbs220.1" />
      </button>
    </div>
      <logic:notEmpty name="bbs010Form" property="forumList">
        <bean:size id="count1" name="bbs010Form" property="bbsPageLabel" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="mt20 paging ">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left "></i>
            </button>
            <html:select property="bbs010page1" onchange="changePage(0);" styleClass="paging_combo">
              <html:optionsCollection name="bbs010Form" property="bbsPageLabel" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right "></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <ul class="main_bbsList-none mt5 mb5">
        <li class="bgC_header1 table_title-color fw_bold fs_14 bor1">
          <table class="w100">
            <tr>
              <td class="pl5">
                <gsmsg:write key="bbs.3" />
              </td>
              <td class="flo_r wp250 txt_c">
                <table>
                  <tr>
                    <td class="wp180">
                      <gsmsg:write key="bbs.bbs010.3" />
                    </td>
                    <td class="wp70">
                      <gsmsg:write key="cmn.member" />
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>

        </li>

        <logic:notEmpty name="bbs010Form" property="forumList">
          <% int saveParentSid = 0; %>
          <% int saveLevel = 1; %>
          <% ArrayList<Integer> parentForumList = new ArrayList<Integer>(); %>
          <% boolean initFlg = true; %>
          <logic:iterate id="forumMdl" name="bbs010Form" property="forumList" indexId="idx">
            <% String titleClass = "cl_linkDef cl_linkHoverChange fw_bold fs_17"; %>
            <logic:equal name="forumMdl" property="readFlg" value="1">
              <% titleClass = "cl_linkVisit cl_linkHoverChange fw_b fs_17"; %>
            </logic:equal>
            <bean:define id="fLevel" name="forumMdl" property="forumLevel" />
            <% int intLevel = ((Integer) fLevel).intValue(); %>
            <bean:define id="pfsid" name="forumMdl" property="parentForumSid" type="java.lang.Integer" />

            <% if (initFlg) { %>
            <% initFlg = false; %>
            <% } else { %>
            <% if (saveLevel > intLevel) { %>
            <% int levelReminder = saveLevel - intLevel; %>
            <% for (int j=0;j<levelReminder;++j) { %>

      </ul>
      <% } %>
      <% } else if (saveLevel == intLevel && saveParentSid != pfsid) { %>
      </li>
      </ul>
      <% } %>
      <% } %>
      <logic:notEqual name="forumMdl" property="parentForumSid" value="0">
        <% if (!parentForumList.contains(pfsid)) { %>
        <li>
          <ul class="main_bbsList-none forum_childBlock <bean:write name="forumMdl" property="parentForumSid" />_child">
            <% } %>

      </logic:notEqual>


      <li class="forumList_content" id="<bean:write name="forumMdl" property="bfiSid"/>">

        <!-- フォーラム左 -->
        <div class="flo_l txt_l">

          <%-- 開閉ボタン --%>
          <logic:notEqual name="forumMdl" property="numberOfChild" value="0">
            <span href="#" class="forum_button forum_openIcon classic-display js_forumBtn_classic" id="forum_button_<bean:write name="forumMdl" property="bfiSid"/>"></span>
            <span href="#" class="forum_openIcon original-display cl_webIcon js_forumBtn_original" id="forum_button_<bean:write name="forumMdl" property="bfiSid"/>"></span>
          </logic:notEqual>
          <logic:equal name="forumMdl" property="numberOfChild" value="0">
            <span class="child_indent forum_indent">-</span>
          </logic:equal>

          <%-- フォーラム画像 --%>
          <div class="forum_icon-list">
            <logic:equal name="forumMdl" property="imgBinSid" value="0">
              <%-- フォーラム画像default --%>
              <a href="javascript:clickForum(<bean:write name="forumMdl" property="bfiSid" />);">
                 <img class="classic-display" src="../bulletin/images/classic/icon_forum.gif" alt="<gsmsg:write key="bbs.3" />">
                 <img class="original-display" src="../bulletin/images/original/icon_forum_32.png" alt="<gsmsg:write key="bbs.3" />">
              </a>
            </logic:equal>
            <logic:notEqual name="forumMdl" property="imgBinSid" value="0">
              <%-- フォーラム画像original --%>
              <a href="javascript:clickForum(<bean:write name="forumMdl" property="bfiSid" />);">
                <img width="30" height="30" src="../bulletin/bbs010.do?CMD=getImageFile&bbs010BinSid=<bean:write name="forumMdl" property="imgBinSid" />&bbs010ForSid=<bean:write name="forumMdl" property="bfiSid" />" alt="<gsmsg:write key="bbs.3" />">
              </a>
            </logic:notEqual>
          </div>

        </div>

        <%-- フォーラム右 --%>
        <div class="forum_title-right forum_list-right">
          <%-- 最終書き込み日時 --%>
          <span class="flo_l txt_l forumList_writedate">
            <bean:write name="forumMdl" property="strWriteDate" />
          </span>

          <%-- メンバー --%>
          <a href="#" class="forum_memberIcon-list" onclick="clickMemBtn(<bean:write name="forumMdl" property="bfiSid"/>);">
            <img class="eventImg classic-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
            <img class="eventImg original-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
          </a>
        </div>
        <%-- フォーラムタイトル --%>
        <div class="forumList_title word_b-all">
          <a href="javascript:clickForum(<bean:write name="forumMdl" property="bfiSid" />);">
            <span class="tooltips">
              <gsmsg:write key="bbs.2" />:
              <bean:write name="forumMdl" property="bfsThreCnt" />
              &nbsp;&nbsp;
              <gsmsg:write key="bbs.16" />:
              <bean:write name="forumMdl" property="writeCnt" />
              <br>
              <bean:write name="forumMdl" property="bfiCmtView" filter="false" />
            </span>
            <span class="<%= titleClass %>">
              <bean:write name="forumMdl" property="bfiName" />
            </span>
          </a>

          <%-- 掲示予定あり --%>
          <logic:greaterThan name="forumMdl" property="rsvThreCnt" value="0">
            <a>
              <span class="tooltips">
                <gsmsg:write key="bbs.bbs010.4" />:
                <bean:write name="forumMdl" property="rsvThreCnt" />
              </span>
              <span>
              <img class="classic-display txt_t" src="../bulletin/images/classic/icon_scheduled_post.png" class="wp20hp20" alt="<gsmsg:write key="bbs.bbs010.4" />">
              <i class="original-display icon-time"></i>
              </span>
            </a>
          </logic:greaterThan>

          <%-- フォーラムnew画像 --%>
          <logic:equal name="forumMdl" property="newFlg" value="1">
            <span class="forum_newIcon classic-display txt_t">
              <img class="classic-display" src="../bulletin/images/classic/icon_new.gif" alt="new<gsmsg:write key="cmn.icon" />">
            </span>
            <span class="labelNew original-display txt_t">
              <gsmsg:write key="bbs.bbsMain.6" />
            </span>
          </logic:equal>
        </div>
      </li>

      <% parentForumList.add(pfsid); %>
      <% saveParentSid = pfsid; %>
      <% saveLevel = intLevel; %>

      </logic:iterate>
      </logic:notEmpty>
      </li>
      </ul>
      </li>
      </ul>

      <logic:notEmpty name="bbs010Form" property="forumList">
        <bean:size id="count2" name="bbs010Form" property="bbsPageLabel" scope="request" />
        <logic:greaterThan name="count2" value="1">
          <div class="txt_r paging">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select property="bbs010page2" onchange="changePage(1);" styleClass="paging_combo">
              <html:optionsCollection name="bbs010Form" property="bbsPageLabel" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>
    </div>

  </html:form>
  </div>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>
