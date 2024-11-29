<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleReadOnly.tld" prefix="dailySchedule"%>
<%@ taglib uri="/WEB-INF/ctag-dailyReserveReadOnly.tld" prefix="dailyReserve"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="schedule.sch120.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<script type="text/javascript" src="../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../bulletin/js/bbs061.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript">

</script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../bulletin/css/bulletin.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>
  <html:form styleId="bbs060Form" action="/bulletin/bbs061">
    <input type="hidden" name="CMD" value="">

    <html:hidden name="bbs061Form" property="bbs061Kanryo" />
    <html:hidden name="bbs061Form" property="checkForum" />
    <html:hidden name="bbs061Form" property="bbs010forumSid" />
    <logic:notEmpty name="bbs061Form" property="bbs060ChkInfSid">
      <logic:iterate id="chkInfSid" name="bbs061Form" property="bbs060ChkInfSid">
        <input type="hidden" name="bbs060ChkInfSid" value="<bean:write name="chkInfSid"/>">
      </logic:iterate>
    </logic:notEmpty>

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
          <gsmsg:write key="bbs.bbs061.6" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.final" />" class="js_move_thread baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
              <gsmsg:write key="cmn.final" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper txt_l">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <logic:notEmpty name="bbs061Form" property="moveForumList">
        <%
          boolean checkedFlg = true;
        %>
        <logic:iterate id="fList" name="bbs061Form" property="moveForumList">

        <bean:define id="cLevel" name="fList" property="classLevel" />
        <%
          int intLevel = ((Integer) cLevel).intValue();
        %>
        <%
          int dep = 27 * intLevel - 17;
        %>
        <div class="no_w" style="padding-left:<%=dep%>px;">

        <%
          if (checkedFlg) {
        %>
        <input checked id="parentForumSid<bean:write name="fList" property="forumSid"/>" type="radio" name="checkForum" value="<bean:write name="fList" property="forumSid"/>">
        <%
          checkedFlg = false;
        %>
        <%
          } else {
        %>
        <input id="parentForumSid<bean:write name="fList" property="forumSid"/>" type="radio" name="checkForum" value="<bean:write name="fList" property="forumSid"/>">
        <%
          }
        %>

            <label for="parentForumSid<bean:write name="fList" property="forumSid"/>">

              <%-- フォーラム画像default --%>
              <logic:equal name="fList" property="binSid" value="0">
                <img class="wp20hp20 classic-display" src="../bulletin/images/classic/icon_forum.gif" alt="<gsmsg:write key="bbs.3" />">
                <img class="wp20hp20 original-display" src="../bulletin/images/original/icon_forum_32.png" alt="<gsmsg:write key="bbs.3" />">
              </logic:equal>

              <%-- フォーラム画像original --%>
              <logic:notEqual name="fList" property="binSid" value="0">
                <img class="wp20hp20 ml4 mb5" alt="<gsmsg:write key="bbs.3" />" src="../bulletin/bbs010.do?CMD=getImageFile&bbs010BinSid=<bean:write name="fList" property="binSid" />&bbs010ForSid=<bean:write name="fList" property="forumSid" />">
              </logic:notEqual>

              <span id="parentForumSid<bean:write name="fList" property="forumSid"/>"> <bean:write name="fList" property="forumName" /></span>

            </label>
          </div>
        </logic:iterate>
      </logic:notEmpty>
    </div>

    <!--確認ダイアログ -->
    <div id="moveThreadCheck" class="display_n">
      <table class="w100 h100">
        <tr>
          <td>
            <img class="classic-display" src="../main/images/classic/header_info.png" alt="cmn.info">
            <img class="original-display" src="../common/images/original/icon_info_32.png" alt="cmn.info">
          </td>
          <td class="w100 pl10">
            <gsmsg:write key="bbs.bbs061.7" />
          </td>
        </tr>
      </table>
    </div>

    <!--エラーダイアログ -->

    <div id="errorDialog" title="" class="display_n">
      <table class="w100 h100">
        <tr>
          <td>
            <img class="classic-display" src="../main/images/classic/header_info.png" alt="cmn.info">
            <img class="original-display" src="../common/images/original/icon_info_32.png" alt="cmn.info">
          </td>
          <td class="w100 pl10">
            <span class="js_error_message"></span>
          </td>
        </tr>
      </table>
    </div>
  </html:form>

</body>
</html:html>
