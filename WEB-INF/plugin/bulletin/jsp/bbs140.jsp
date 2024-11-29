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
<title>GROUPSESSION <gsmsg:write key="bbs.bbs140.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bulletin/js/bbs140.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/bulletin/bbs140">
    <input type="hidden" name="CMD" value="">
    <html:hidden name="bbs140Form" property="bbs010forumSid" />
    <html:hidden name="bbs140Form" property="threadSid" />
    <html:hidden name="bbs140Form" property="bbs010page1" />
    <html:hidden name="bbs140Form" property="bbs140orderKey" />
    <html:hidden name="bbs140Form" property="bbs140sortKey" />

    <!-- BODY -->
    <div class="wrapper">

      <div class="w100">
        <logic:notEmpty name="bbs140Form" property="bbs140users">
          <bean:size id="count1" name="bbs140Form" property="bbs140PageLabel" scope="request" />
          <logic:greaterThan name="count1" value="1">
            <div class="flo_r paging mb5">
                <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select property="bbs140pageNum1" onchange="changePage(1);" styleClass="paging_combo">
                  <html:optionsCollection name="bbs140Form" property="bbs140PageLabel" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
                </div>
          </logic:greaterThan>
        </logic:notEmpty>
      </div>

      <table class="table-top w100">
        <tr>
          <!-- 氏名 -->
          <th class="w40 table_title-color">
            <logic:equal name="bbs140Form" property="bbs140sortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME)%>">
              <logic:equal name="bbs140Form" property="bbs140orderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>');">
                  <span>
                    <gsmsg:write key="cmn.name" />
                    <span class="classic-display">▲</span>
                    <span class="original-display">
                      <i class="icon-sort_up"></i>
                    </span>
                  </span>
                </a>
          </logic:equal>
          <logic:equal name="bbs140Form" property="bbs140orderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>');">
              <span>
                <gsmsg:write key="cmn.name" />
                <span class="classic-display">▼</span>
                <span class="original-display">
                  <i class="icon-sort_down"></i>
                </span>
              </span>
            </a>
          </logic:equal>
          </logic:equal>
          <logic:notEqual name="bbs140Form" property="bbs140sortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME)%>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>');">
              <span>
                <gsmsg:write key="cmn.name" />
              </span>
            </a>
          </logic:notEqual>
          </th>

          <!-- 閲覧状況 -->
          <th class="w60 table_title-color">
            <logic:equal name="bbs140Form" property="bbs140sortKey" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_WATCH)%>">
              <logic:equal name="bbs140Form" property="bbs140orderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_WATCH)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>');">
                  <span>
                    <gsmsg:write key="bbs.bbsMain.1" />
                    <span class="classic-display">▲</span>
                    <span class="original-display">
                      <i class="icon-sort_up"></i>
                    </span>
                  </span>
                </a>
          </logic:equal>
          <logic:equal name="bbs140Form" property="bbs140orderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_WATCH)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>');">
              <span>
                <gsmsg:write key="bbs.bbsMain.1" />
                <span class="classic-display">▼</span>
                <span class="original-display">
                  <i class="icon-sort_down"></i>
                </span>
              </span>
            </a>
          </logic:equal>
          </logic:equal>
          <logic:notEqual name="bbs140Form" property="bbs140sortKey" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_WATCH)%>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_WATCH)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>');">
              <span>
                <gsmsg:write key="bbs.bbsMain.1" />
              </span>
            </a>
          </logic:notEqual>
          </th>

          <logic:notEmpty name="bbs140Form" property="bbs140users">
            <logic:iterate id="user" name="bbs140Form" property="bbs140users" scope="request" indexId="idx">

              <tr>
                <!-- 氏名 -->
                <td>
                  <logic:equal value="1" name="user" property="usrYukoKbn">
                    <span class="normal_link mukoUser">
                  </logic:equal>
                  <logic:notEqual value="1" name="user" property="usrYukoKbn">
                    <span class="normal_link">
                  </logic:notEqual>
                  <bean:write name="user" property="usiSei" />&nbsp;<bean:write name="user" property="usiMei" />
                  </span>
                </td>
                <!-- 閲覧状況 -->
                <logic:equal name="user" property="userJkbn" value="0">
                  <td>
                    <span class="cl_linkDef">
                      <gsmsg:write key="cmn.read.yet" />
                    </span>
                  </td>
                </logic:equal>
                <logic:equal name="user" property="userJkbn" value="1">
                  <td>
                    <gsmsg:write key="cmn.read.already" />
                  </td>
                </logic:equal>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
      </table>

      <table class="w100">
        <tr>
          <td>
            <logic:notEmpty name="bbs140Form" property="bbs140PageLabel">
              <bean:size id="count2" name="bbs140Form" property="bbs140PageLabel" scope="request" />
              <logic:greaterThan name="count2" value="1">
                <div class="flo_r paging">
                    <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                      <i class="icon-paging_left"></i>
                    </button>
                    <html:select property="bbs140pageNum2" onchange="changePage(2);" styleClass="paging_combo">
                      <html:optionsCollection name="bbs140Form" property="bbs140PageLabel" value="value" label="label" />
                    </html:select>
                    <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                      <i class="icon-paging_right"></i>
                    </button>
                  </div>
              </logic:greaterThan>
            </logic:notEmpty>
          </td>
        </tr>
      </table>

      <div class="txt_c clr">
        <button type="button" class="baseBtn" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>

    </div>

  </html:form>

</body>
</html:html>