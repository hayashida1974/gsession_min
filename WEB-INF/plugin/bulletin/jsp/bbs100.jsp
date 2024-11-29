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
<title>GROUPSESSION <gsmsg:write key="bbs.bbs100.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bulletin/js/bbs100.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/userpopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body onunload="windowClose();">

  <html:form action="/bulletin/bbs100">
    <input type="hidden" name="CMD" value="">
    <html:hidden name="bbs100Form" property="bbs010forumSid" />
    <html:hidden name="bbs100Form" property="s_key" />
    <html:hidden name="bbs100Form" property="bbs010page1" />
    <html:hidden name="bbs100Form" property="bbs100orderKey" />
    <html:hidden name="bbs100Form" property="bbs100sortKey" />

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
          <gsmsg:write key="bbs.bbs100.1" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backForumList');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>



    <div class="wrapper">

      <div class="w100 paging">

        <logic:notEmpty name="bbs100Form" property="bbs100users">
          <bean:size id="count1" name="bbs100Form" property="bbs100PageLabel" scope="request" />
          <logic:greaterThan name="count1" value="1">

            <table class="flo_r">
              <td>
                <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
              <td>
                <html:select property="bbs100pageNum1" onchange="changePage(1);" styleClass="paging_combo">
                  <html:optionsCollection name="bbs100Form" property="bbs100PageLabel" value="value" label="label" />
                </html:select>
              </td>
              <td>
                <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>

              </td>
              </tr>
            </table>

          </logic:greaterThan>
        </logic:notEmpty>
      </div>
    </div>


    <table class="w100">
      <tr>
        <td>

          <table class="table-top w100">
            <tr>
              <!-- 社員/職員番号 -->
              <th class="no_w cursor_p">
                <logic:equal name="bbs100Form" property="bbs100sortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO)%>">
                  <logic:equal name="bbs100Form" property="bbs100orderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>">
                    <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>');">
                      <gsmsg:write key="cmn.employee.staff.number" />
                      <span class="classic-display">▲</span>
                      <span class="original-display">
                        <i class="icon-sort_up"></i>
                      </span>
                    </a>
              </th>
              </logic:equal>
              <logic:equal name="bbs100Form" property="bbs100orderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>');">
                  <gsmsg:write key="cmn.employee.staff.number" />
                  <span class="classic-display">▼</span>
                  <span class="original-display">
                    <i class="icon-sort_down"></i>
                  </span>
                </a>
                </th>
              </logic:equal>
              </logic:equal>
              <logic:notEqual name="bbs100Form" property="bbs100sortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO)%>">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>');">
                  <gsmsg:write key="cmn.employee.staff.number" />
                </a>
                </th>
              </logic:notEqual>
              </th>

              <!-- 氏名 -->
              <th class="no_w w20 cursor_p">
                <logic:equal name="bbs100Form" property="bbs100sortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME)%>">
                  <logic:equal name="bbs100Form" property="bbs100orderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>">
                    <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>');">
                      <gsmsg:write key="cmn.name" />
                      <span class="classic-display">▲</span>
                      <span class="original-display">
                        <i class="icon-sort_up"></i>
                      </span>
                    </a>
              </th>
              </logic:equal>
              <logic:equal name="bbs100Form" property="bbs100orderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>');">
                  <gsmsg:write key="cmn.name" />
                  <span class="classic-display">▼</span>
                  <span class="original-display">
                    <i class="icon-sort_down"></i>
                  </span>
                </a>
                </th>
              </logic:equal>
              </logic:equal>
              <logic:notEqual name="bbs100Form" property="bbs100sortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME)%>">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>');">
                  <gsmsg:write key="cmn.name" />
                </a>
                </th>
              </logic:notEqual>
              </th>

              <!-- 役職 -->
              <th class="no_w cursor_p">
                <logic:equal name="bbs100Form" property="bbs100sortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK)%>">
                  <logic:equal name="bbs100Form" property="bbs100orderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>">
                    <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>');">
                      <gsmsg:write key="cmn.post" />
                      <span class="classic-display">▲</span>
                      <span class="original-display">
                        <i class="icon-sort_up"></i>
                      </span>
                    </a>
              </th>
              </logic:equal>
              <logic:equal name="bbs100Form" property="bbs100orderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>');">
                  <gsmsg:write key="cmn.post" />
                  <span class="classic-display">▼</span>
                  <span class="original-display">
                    <i class="icon-sort_down"></i>
                  </span>
                </a>
                </th>
              </logic:equal>
              </logic:equal>
              <logic:notEqual name="bbs100Form" property="bbs100sortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK)%>">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>');">
                  <gsmsg:write key="cmn.post" />
                </a>
                </th>
              </logic:notEqual>
              </th>

              <!-- E-MAIL -->
              <th class="no_w w50">E-MAIL</th>
            </tr>

            <logic:notEmpty name="bbs100Form" property="bbs100users">
              <logic:iterate id="user" name="bbs100Form" property="bbs100users" scope="request" indexId="idx">
                <tr>
                  <!-- 社員/職員番号 -->
                  <td class="no_w">
                    <bean:write name="user" property="usiSyainNo" />
                  </td>
                  <!-- 氏名 -->
                  <td>
                    <a href="#!" onClick="openUserInfoWindow(<bean:write name="user" property="usrSid" />);">
                      <logic:equal value="1" name="user" property="usrUkoFlg">
                        <span class="fw_n mukoUser">
                      </logic:equal>
                      <logic:notEqual value="1" name="user" property="usrUkoFlg">
                        <span class="fw_n">
                      </logic:notEqual>
                      <bean:write name="user" property="usiSei" />&nbsp;<bean:write name="user" property="usiMei" />
                      </span>
                    </a>
                  </td>
                  <!-- 役職 -->
                  <td class="no_w">
                    <bean:write name="user" property="usiYakusyoku" />
                  </td>
                  <!-- E-MAIL -->
                  <td>
                    <logic:notEmpty name="user" property="usiMail1">
                      <a href="mailto:<bean:write name="user" property="usiMail1" />">
                        <span class="fw_n">
                          <bean:write name="user" property="usiMail1" />
                        </span>
                      </a>
                    </logic:notEmpty>
                    <logic:notEmpty name="user" property="usiMail2">
                      <a href="mailto:<bean:write name="user" property="usiMail2" />">
                        <span class="fw_n">
                          <bean:write name="user" property="usiMail2" />
                        </span>
                      </a>
                    </logic:notEmpty>
                    <logic:notEmpty name="user" property="usiMail3">
                      <a href="mailto:<bean:write name="user" property="usiMail3" />">
                        <span class="fw_n">
                          <bean:write name="user" property="usiMail3" />
                        </span>
                      </a>
                    </logic:notEmpty>
                  </td>
                </tr>
              </logic:iterate>
            </logic:notEmpty>

          </table>

          <logic:notEmpty name="bbs100Form" property="bbs100PageLabel">
            <bean:size id="count2" name="bbs100Form" property="bbs100PageLabel" scope="request" />
            <logic:greaterThan name="count2" value="1">
              <div class="w100 paging">
                <table class="flo_r">
                  <td>
                    <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                      <i class="icon-paging_left"></i>
                    </button>
                  </td>
                  <td>
                    <html:select property="bbs100pageNum2" onchange="changePage(2);" styleClass="paging_combo">
                      <span>
                        <html:optionsCollection name="bbs100Form" property="bbs100PageLabel" value="value" label="label" />
                      </span>
                    </html:select>
                  </td>
                  <td>
                    <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
                      <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                      <i class="icon-paging_right"></i>
                    </button>
                  </td>
                  </tr>
                </table>
              </div>
            </logic:greaterThan>
          </logic:notEmpty>

        </td>
      </tr>
    </table>

  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>