<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.bbs.GSConstBulletin"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.bulletin" /> <gsmsg:write key="bbs.bbs220.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script language="JavaScript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bulletin/js/bbs220.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<script src="../common/components/file-preview/index.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../bulletin/css/bulletin.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<jsp:include page="/WEB-INF/plugin/bulletin/jsp/bbs220_message.jsp" />

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body>
  <html:form action="/bulletin/bbs220">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="bbs220SortKey" />
    <html:hidden property="bbs220OrderKey" />
    <html:hidden property="soukouSid" />
    <html:hidden property="bbs220SelectType" />
    <html:hidden property="bbs220BackDsp" />
    <html:hidden property="bbs220BackThreadSid" />
    <html:hidden property="bbs220BackForumSid" />
    <html:hidden name="bbs220Form" property="bbs010page1" />
    <html:hidden name="bbs220Form" property="bbs010forumSid" />
    <html:hidden name="bbs220Form" property="threadSid" />
    <html:hidden name="bbs220Form" property="bbsmainFlg" />
    <html:hidden name="bbs220Form" property="bbs060sortKey" />
    <html:hidden name="bbs220Form" property="bbs060orderKey" />
    <html:hidden name="bbs220Form" property="bbs060page1" />
    <html:hidden name="bbs220Form" property="bbs060page2" />

    <html:hidden name="bbs220Form" property="searchDspID" />
    <html:hidden name="bbs220Form" property="bbs040forumSid" />
    <html:hidden name="bbs220Form" property="bbs040keyKbn" />
    <html:hidden name="bbs220Form" property="bbs040taisyouThread" />
    <html:hidden name="bbs220Form" property="bbs040taisyouNaiyou" />
    <html:hidden name="bbs220Form" property="bbs040userName" />
    <html:hidden name="bbs220Form" property="bbs040readKbn" />
    <html:hidden name="bbs220Form" property="bbs040dateNoKbn" />
    <html:hidden name="bbs220Form" property="bbs040fromYear" />
    <html:hidden name="bbs220Form" property="bbs040fromMonth" />
    <html:hidden name="bbs220Form" property="bbs040fromDay" />
    <html:hidden name="bbs220Form" property="bbs040toYear" />
    <html:hidden name="bbs220Form" property="bbs040toMonth" />
    <html:hidden name="bbs220Form" property="bbs040toDay" />
    <html:hidden name="bbs220Form" property="bbs041page1" />

    <html:hidden name="bbs220Form" property="bbs060postOrderKey" />
    <html:hidden name="bbs220Form" property="bbs060postPage1" />
    <html:hidden name="bbs220Form" property="bbs060reply" />
    <html:hidden name="bbs220Form" property="bbs060showThreBtn" />
    <html:hidden name="bbs220Form" property="bbs060postBinSid" />
    <html:hidden name="bbs220Form" property="bbs060postSid" />


    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

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
          <gsmsg:write key="bbs.bbs220.1" />
        </li>
        <li>
          <div>
            <button type="button" name="btn_delete" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('soukouDelete')">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backPage')">
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

      <!-- 草稿一覧 -->
      <table class="table-top">
        <!-- タイトル -->
        <tr>
          <bean:define id="bbsSortKey" name="bbs220Form" property="bbs220SortKey" type="java.lang.Integer" />
          <bean:define id="bbsOrder" name="bbs220Form" property="bbs220OrderKey" type="java.lang.Integer" />
          <%
             String sortSign = "";
             String nextOrder = "";
             jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
             String[] widthList = {
                     "3",
                     "20",
                     "20",
                     "10",
                     "25",
                     "15",
                     "5"
                     };
             String[] titleList = {
                     "<input class=\"cursor_p\" type=\"checkbox\" name=\"allChk\" onClick=\"changeChk();\">",
                     gsMsg.getMessage(request, "bbs.4"),
                     gsMsg.getMessage(request, "bbs.bbsMain.4"),
                     gsMsg.getMessage(request, "cmn.type2"),
                     gsMsg.getMessage(request, "cmn.content"),
                     gsMsg.getMessage(request, "main.man470.7"),
                     ""
                     };
             int[] sortKeyList = {
                     0,
                     0,
                     0,
                     GSConstBulletin.SOUKOU_SORT_KEY_TYPE,
                     0,
                     GSConstBulletin.SOUKOU_SORT_KEY_ADATE,
                     0};
             for (int titleIdx = 0; titleIdx < titleList.length; titleIdx++) {
                 if(sortKeyList[titleIdx]  == 0) {
                     %>
             <% if(titleIdx  == 0) { %>
             <th class="w<%=widthList[titleIdx]%> cursor_p js_tableTopCheck js_tableTopCheck-header">
             <% } else { %>
             <th class="w<%=widthList[titleIdx]%> no_w">
             <% } %>
             <%=titleList[titleIdx]%>
             </th>
             <%
                 } else {
                 if (bbsSortKey.intValue() == sortKeyList[titleIdx]) {
                     if (bbsOrder.intValue() == 1) {
                         sortSign = "<span class=\"classic-display\">▼</span><i class=\"original-display icon-sort_down\"></i>";
                         nextOrder = "0";
                     } else {
                         sortSign = "<span class=\"classic-display\">▲</span><i class=\"original-display icon-sort_up\"></i>";
                         nextOrder = "1";
                     }
                 } else {
                     nextOrder = "0";
                     sortSign = "";
                 }
            %>
          <th class="w<%=widthList[titleIdx]%> cursor_p">
            <a href="#" onClick="clickTitle(<%=String.valueOf(sortKeyList[titleIdx])%>, <%= nextOrder %>);">
              <span><%=titleList[titleIdx]%><%=sortSign%></span>
            </a>
          </th>
          <%
                 }
             }
          %>
        </tr>
        <!-- 草稿 -->
        <logic:notEmpty name="bbs220Form" property="bbs220SoukouList">
          <logic:iterate id="soukouData" name="bbs220Form" property="bbs220SoukouList" indexId="idx">
            <tr>
              <!-- チェックボックス -->
              <td class="txt_c cursor_p js_tableTopCheck">
                <html:multibox name="bbs220Form" property="bbs220delInfSid">
                  <bean:write name="soukouData" property="bskSid" />
                </html:multibox>
              </td>
              <!-- フォーラム名 -->
              <td class="txt_l">
                <div class="verAlignMid txt_m">
                <logic:equal name="soukouData" property="forumDelFlg" value="<%=String.valueOf(GSConstBulletin.FORUM_DELETE_NO)%>">
                  <!--フォーラム画像default -->
                  <logic:equal name="soukouData" property="imgBinSid" value="0">
                    <img class="classic-display" src="../bulletin/images/classic/icon_forum.gif" alt="<gsmsg:write key="bbs.3" />">
                    <img class="original-display" src="../bulletin/images/original/icon_forum_32.png" alt="<gsmsg:write key="bbs.3" />">
                  </logic:equal>
                  <!--フォーラム画像original -->
                  <logic:notEqual name="soukouData" property="imgBinSid" value="0">
                    <img class="wp30hp30" src="../bulletin/bbs010.do?CMD=getImageFile&bbs010BinSid=<bean:write name="soukouData" property="imgBinSid" />&bbs010ForSid=<bean:write name="soukouData" property="bfiSid" />" alt="<gsmsg:write key="bbs.3" />">
                  </logic:notEqual>
                </logic:equal>
                <logic:equal name="soukouData" property="forumDelFlg" value="<%=String.valueOf(GSConstBulletin.FORUM_DELETE_YES)%>">
                  <img class="wp30hp30 classic-display" src="../bulletin/images/classic/icon_forum.gif" alt="<gsmsg:write key="bbs.3" />">
                  <img class="wp30hp30 original-display" src="../bulletin/images/original/icon_forum_32.png" alt="<gsmsg:write key="bbs.3" />">
                </logic:equal>
                <logic:equal name="soukouData" property="forumDelFlg" value="<%=String.valueOf(GSConstBulletin.FORUM_DELETE_NO)%>">
                  <div class="fs_16 verAlignMid">
                    <bean:write name="soukouData" property="forumName" />
                  </div>
                </logic:equal>
                <logic:equal name="soukouData" property="forumDelFlg" value="<%=String.valueOf(GSConstBulletin.FORUM_DELETE_YES)%>">
                  <span class="opacity6 fs_16">
                    <gsmsg:write key="bbs.bbs220.2" />
                  </span>
                </logic:equal>
                </div>
              </td>
              <!-- スレッド名 -->
              <td class="txt_l">
                <div class="verAlignMid txt_m">
                  <img class="classic-display" src="../bulletin/images/classic/icon_thread.gif" alt="<gsmsg:write key="bbs.2" />">
                  <!-- スレッド草稿 -->
                  <logic:equal name="soukouData" property="bskSoukouType" value="<%=String.valueOf(GSConstBulletin.SOUKOU_TYPE_THREAD)%>">
                    <logic:equal name="soukouData" property="bskImportance" value="<%=String.valueOf(GSConstBulletin.BTI_IMPORTANCE_YES)%>">
                      <img class="classic-display" src="../common/images/classic/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                      <img class="original-display" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                    </logic:equal>
                    <logic:equal name="soukouData" property="fileFlg" value="<%=String.valueOf(GSConstBulletin.SOUKOU_TEMP_YES)%>">
                      <div class="ml5 mr5">
                       <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attach.file" />">
                       <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attach.file" />">
                      </div>
                    </logic:equal>
                    <span class="verAlignMid fs_16">
                      <bean:write name="soukouData" property="bskTitle" />
                    </span>
                  </logic:equal>

                  <!-- 投稿草稿 -->
                  <logic:equal name="soukouData" property="bskSoukouType" value="<%=String.valueOf(GSConstBulletin.SOUKOU_TYPE_TOUKOU)%>">
                    <logic:equal name="soukouData" property="thredDelFlg" value="<%=String.valueOf(GSConstBulletin.THREAD_DELETE_NO)%>">
                      <logic:equal name="soukouData" property="juyoKbn" value="<%=String.valueOf(GSConstBulletin.BTI_IMPORTANCE_YES)%>">
                        <img class="classic-display" src="../common/images/classic/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                        <img class="original-display" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                      </logic:equal>
                      <logic:equal name="soukouData" property="fileFlg" value="<%=String.valueOf(GSConstBulletin.SOUKOU_TEMP_YES)%>">
                        <div class="ml5 mr5">
                         <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attach.file" />">
                         <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attach.file" />">
                        </div>
                      </logic:equal>
                      <span class="verAlignMid fs_16">
                        <bean:write name="soukouData" property="threadName" />
                      </span>
                    </logic:equal>
                    <logic:equal name="soukouData" property="thredDelFlg" value="<%=String.valueOf(GSConstBulletin.THREAD_DELETE_YES)%>">
                      <span class="opacity6 fs_16">
                        <gsmsg:write key="bbs.bbs220.3" />
                      </span>
                    </logic:equal>
                  </logic:equal>
                </div>
              </td>
              <!-- 種類 -->
              <td class="txt_c">
                <!-- スレッド草稿 -->
                <logic:equal name="soukouData" property="bskSoukouType" value="<%=String.valueOf(GSConstBulletin.SOUKOU_TYPE_THREAD)%>">
                  <span>
                    <gsmsg:write key="bbs.2" />
                  </span>
                </logic:equal>
                <!-- 投稿草稿 -->
                <logic:equal name="soukouData" property="bskSoukouType" value="<%=String.valueOf(GSConstBulletin.SOUKOU_TYPE_TOUKOU)%>">
                  <span>
                    <gsmsg:write key="bbs.16" />
                  </span>
                </logic:equal>
              </td>
              <!-- 内容 -->
              <td class="txt_l table_newLine">
                <div>
                  <logic:equal name="soukouData" property="contentFileFlg" value="<%=String.valueOf(GSConstBulletin.SOUKOU_TEMP_YES)%>">
                   <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file.gif" alt="<gsmsg:write key="cmn.attach.file" />">
                   <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attach.file" />">
                  </logic:equal>
                  <span>
                    <bean:write name="soukouData" property="content" />
                  </span>
                  <span class="cursor_p flo_r" onClick="soukou_info(<bean:write name="soukouData" property="bskSid"/>);">
                    <img class="classic-display" src="../common/images/classic/icon_comment.png">
                    <i class="original-display icon-comment"></i>
                  </span>
                </div>
              </td>
              <!-- 作成日時 -->
              <td class="txt_c">
                <span>
                  <bean:write name="soukouData" property="strDate" />
                </span>
              </td>
              <!-- 編集ボタン -->
              <td class="txt_c">
                <logic:equal name="soukouData" property="editFlg" value="<%=String.valueOf(GSConstBulletin.SOUKOU_EDITBUTTON_YES)%>">
                  <logic:equal name="soukouData" property="bskSoukouType" value="<%=String.valueOf(GSConstBulletin.SOUKOU_TYPE_THREAD)%>">
                    <button type="button" name="btn_prjadd" class="baseBtn no_w" value="<gsmsg:write key="cmn.edit" />"
                      onClick="editButton(
                                <bean:write name="soukouData" property="bskSid"/>,
                                <bean:write name="soukouData" property="bfiSid"/>,
                                <bean:write name="bbs220Form" property="threadSid"/>,
                                <%=String.valueOf(GSConstBulletin.SOUKOU_TYPE_THREAD)%>
                                );">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                      <gsmsg:write key="cmn.edit" />
                    </button>
                  </logic:equal>
                  <logic:equal name="soukouData" property="bskSoukouType" value="<%=String.valueOf(GSConstBulletin.SOUKOU_TYPE_TOUKOU)%>">
                    <button type="button" name="btn_prjadd" class="baseBtn no_w" value="<gsmsg:write key="cmn.edit" />"
                      onClick="editButton(
                                <bean:write name="soukouData" property="bskSid"/>,
                                <bean:write name="soukouData" property="bfiSid"/>,
                                <bean:write name="soukouData" property="btiSid"/>,
                                <%=String.valueOf(GSConstBulletin.SOUKOU_TYPE_TOUKOU)%>
                                );">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                      <gsmsg:write key="cmn.edit" />
                    </button>
                  </logic:equal>
                </logic:equal>
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>
    </div>

    <!-- 草稿内容ダイアログ -->
    <div id="js_soukouInfo" class="display_n of_a">
      <table class="w100">
        <tr class="txt_c">
          <td>
            <span class="js_naiyo"></span>
          </td>
        </tr>
      </table>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>

<div class="display_none js_htmlframe_template">
  <htmlframe:write attrClass="w100  js_htmlframe "></htmlframe:write>
</div>

