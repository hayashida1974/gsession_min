<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<%
  int[] sortKeyList = new int[] {
      jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_THRED,
      jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_TOUKOU,
      jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_ETSURAN,
      jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_USER,
      jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SAISHIN,
      jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SIZE
  };
  int order_desc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC;
  int order_asc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="bbs.9" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<gsjsmsg:js filename="gsjsmsg.js" />
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script language="JavaScript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/jquery.selection-min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmnPic.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/components/file-preview/index.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bulletin/js/bbs060.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bulletin/js/bbsMemPopUp.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/userpopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/selectionSearch.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/glayer.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jtooltip.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>

<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/common.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../bulletin/css/bulletin.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<logic:greaterThan name="bbs060Form" property="threadSid" value="0">
  <%-- 投稿一覧表示 --%>
  <body onload="getList(<bean:write name="bbs060Form" property="bbs010forumSid"/>,<bean:write name="bbs060Form" property="threadSid"/>);" onunload="windowClose();">
</logic:greaterThan>
<logic:lessEqual name="bbs060Form" property="threadSid" value="0">
  <%-- スレッド一覧表示 --%>
  <body onload="getList(<bean:write name="bbs060Form" property="bbs010forumSid"/>,0);" onunload="windowClose();">
</logic:lessEqual>

<html:form styleId="bbs060Form" action="/bulletin/bbs060">
  <input type="hidden" name="CMD" value="searchThre" />
  <html:hidden name="bbs060Form" property="bbs010page1" />
  <html:hidden name="bbs060Form" property="bbs010forumSid" />
  <html:hidden name="bbs060Form" property="threadSid" />
  <html:hidden name="bbs060Form" property="bbsmainFlg" />
  <html:hidden name="bbs060Form" property="bbs060sortKey" />
  <html:hidden name="bbs060Form" property="bbs060orderKey" />
  <html:hidden name="bbs060Form" property="bbs060page1" />
  <html:hidden name="bbs060Form" property="bbs060page2" />

  <html:hidden name="bbs060Form" property="searchDspID" />
  <html:hidden name="bbs060Form" property="bbs040forumSid" />
  <html:hidden name="bbs060Form" property="bbs040keyKbn" />
  <html:hidden name="bbs060Form" property="bbs040taisyouThread" />
  <html:hidden name="bbs060Form" property="bbs040taisyouNaiyou" />
  <html:hidden name="bbs060Form" property="bbs040userName" />
  <html:hidden name="bbs060Form" property="bbs040readKbn" />
  <html:hidden name="bbs060Form" property="bbs040dateNoKbn" />
  <html:hidden name="bbs060Form" property="bbs040fromYear" />
  <html:hidden name="bbs060Form" property="bbs040fromMonth" />
  <html:hidden name="bbs060Form" property="bbs040fromDay" />
  <html:hidden name="bbs060Form" property="bbs040toYear" />
  <html:hidden name="bbs060Form" property="bbs040toMonth" />
  <html:hidden name="bbs060Form" property="bbs040toDay" />
  <html:hidden name="bbs060Form" property="bbs041page1" />

  <html:hidden name="bbs060Form" property="bbs060postOrderKey" />
  <html:hidden name="bbs060Form" property="bbs060postPage1" />
  <html:hidden name="bbs060Form" property="bbs060reply" />
  <html:hidden name="bbs060Form" property="bbs060showThreBtn" />
  <html:hidden name="bbs060Form" property="bbs060postBinSid" />
  <html:hidden name="bbs060Form" property="bbs060postSid" />

  <input type="hidden" name="helpPrm" />

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
      <li class="fs_16" id="bbs060screenName"></li>
      <li>
        <div id="list_menu_post-header"></div>
        <div id="list_menu_thread-header">
         <button type="button" name="btn_prjadd" value="<gsmsg:write key="bbs.bbs060.1" />" id="add_thread_button" onClick="buttonPush('addThread');" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="bbs.bbs060.1" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="bbs.bbs060.1" />">
          <gsmsg:write key="bbs.bbs060.1" />
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

  <div class="w100 bor1 mt5 mb5 cl_fontWarn bgC_body display_n" id="display_warn"></div>

  <span class="gs_errspace">
    <jsp:include page="/WEB-INF/plugin/bulletin/jsp/bbs060_errspace.jsp"></jsp:include>
  </span>

  <div id="loading_pop" class="display_n">
    <table class="w100 h100">
      <tr>
        <td class="p0 txt_c txt_m w100">
          <img class="classic-display" src="../common/images/classic/icon_loader.gif" />
          <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
        </td>
      </tr>
    </table>
  </div>

  <div class="wrapper_2column" id="main_content_block">

    <div class="side_multi-left bgC_none">
      <!-- フォーラム一覧 -->
      <table class="table-top m0 mb10 display_n bgC_body wp250" id="subForumList">
        <tr>
          <th class="table_title-color p0">
            <div class="verAlignMid ml5 hp40"><gsmsg:write key="bbs.1" /></div>
          </th>
        </tr>
        <tr>
          <td class="p0">
            <%-- フォーラム一覧階層部 START --%>
            <div class="ofy_h" id="side_forum_list"></div>
            <%-- フォーラム一覧階層部 END --%>
          </td>
        </tr>

      </table>

      <!-- 未読スレッド一覧 START-->
      <table class="table-left wp250 mb15 display_n" id="notReadThreadListTable"></table>
      <!-- 未読スレッド一覧 END-->

      <%-- 全て既読にするボタン START --%>
      <div class="wp250 display_n" id="markAllReadBlock">
      </div>
      <%-- 全て既読にするボタン END --%>

    </div>

    <div class="main">
      <table class="w100 txt_r">
        <tr>
          <%-- スレッド一覧のメニュー --%>
          <td class="txt_r no_w display_n" id="list_menu_thread">
            <input type="text" class="wp180" name="s_key" value="<bean:write name="bbs060Form" property="s_key" />" maxlength="50" />
            <button type="submit" name="btn_search" value="<gsmsg:write key="cmn.search" />" onClick="buttonPushSearch();" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
              <gsmsg:write key="cmn.search" />
            </button>
            <button type="button" name="btn_detailSearch" value="<gsmsg:write key="cmn.advanced.search" />" onClick="buttonPushSearchDtl();" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_advanced_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
              <gsmsg:write key="cmn.advanced.search" />
            </button>
            <button type="button" name="btn_mobe" value="<gsmsg:write key="cmn.move" />" onClick="clickMoveBtn();" class="baseBtn">
              <img class="classic-display" src="../bulletin/images/classic/icon_move_thread.png" alt="<gsmsg:write key="cmn.read.yet" />">
              <img class="original-display" src="../bulletin/images/original/icon_thread_move.png" alt="<gsmsg:write key="cmn.read.yet" />">
              <gsmsg:write key="cmn.move" />
            </button>
            <button type="button" name="btn_yet" value="<gsmsg:write key="cmn.read.yet" />" onClick="clickReadYet();" class="baseBtn">
              <img class="classic-display" src="../common/images/classic/icon_midoku.png" alt="<gsmsg:write key="cmn.read.yet" />">
              <img class="original-display" src="../bulletin/images/original/icon_bbs_midoku.png" alt="<gsmsg:write key="cmn.read.yet" />">
              <gsmsg:write key="cmn.read.yet" />
            </button>
            <button type="button" name="btn_soukou" value="<gsmsg:write key="bbs.bbs220.1" />" onClick="buttonPush('soukou');" class="baseBtn">
              <gsmsg:write key="bbs.bbs220.1" />
            </button>
            <span id="damy_add_thread_button" class="display_n"></span>
          </td>
          <%-- 投稿一覧のメニュー --%>
          <td class="display_n no_w" id="list_menu_post"></td>
        </tr>
      </table>
      <table class="w100">
        <tr>
          <td></td>
          <!-- スレッド一覧 表示部 START-->
          <td class="w100" id="thread_list_block"></td>
          <!-- スレッド一覧 表示部 END-->
          <!-- 投稿一覧 表示部 START-->
          <td class="w100 txt_l txt_t" id="post_list_block"></td>
          <!-- 投稿一覧 表示部 END-->
        </tr>
      </table>

      <div class="footerBtn_block display_n txt_r mt10" id="list_menu_thread_footer">
        <button type="button" name="btn_prjadd" value="<gsmsg:write key="bbs.bbs060.1" />" id="add_thread_footer_button" onClick="buttonPush('addThread');" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="bbs.bbs060.1" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="bbs.bbs060.1" />">
          <gsmsg:write key="bbs.bbs060.1" />
        </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backBBSList');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
      </div>

    </div>
  </div>
</html:form>
<div class="display_none js_htmlframe_template">
  <htmlframe:write attrClass="w100  js_htmlframe "></htmlframe:write>
</div>

<div name="moveDlg" class="display_n">
  <iframe name="moveDlg" src="about:blank"> </iframe>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>
