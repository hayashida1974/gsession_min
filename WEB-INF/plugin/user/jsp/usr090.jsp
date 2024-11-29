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
<title>GROUPSESSION <gsmsg:write key="user.usr090.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../user/js/usr090.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../user/css/user.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>
  <html:form action="/user/usr090">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="targetUsrSid" />
    <html:hidden property="targetUidNumber" />
    <html:hidden property="usr090SortKey" />
    <html:hidden property="usr090OrderKey" />

    <div>
      <div class="paging">
        <button type="button" class="webIconBtn" onClick="buttonPush('pageleft');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo" property="usr090PageTop" onchange="return changePage('0');">
          <html:optionsCollection name="usr090Form" property="usr090PageList" value="value" label="label" />
        </html:select>
        <logic:empty name="usr090Form" property="usr090PageList">
          <html:select styleClass="paging_combo" property="usr090PageTop">
            <option value="1">1 / 1</option>
          </html:select>
        </logic:empty>
        <button type="button" class="webIconBtn" onClick="buttonPush('pageright');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>


      <table summary="" class="table-top" cellpadding="0" cellspacing="0">
        <tr>

          <!-- 最終ログイン時間 -->
          <logic:equal name="usr090Form" property="usr090SortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_DATE)%>">

            <logic:equal name="usr090Form" property="usr090OrderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>">
              <th class="w25 no_w">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_DATE)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>')">
                  <span>
                    <gsmsg:write key="user.usr090.2" />
                    <span class="classic-display">▲</span>
                    <span class="original-display">
                      <i class="icon-sort_up"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>

            <logic:equal name="usr090Form" property="usr090OrderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>">
              <th class="w25 no_w">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_DATE)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>')">
                  <span>
                    <gsmsg:write key="user.usr090.2" />
                    <span class="classic-display">▼</span>
                    <span class="original-display">
                      <i class="icon-sort_down"></i>
                    </span>
                  </span>
                  </span>
                </a>
              </th>
            </logic:equal>

          </logic:equal>

          <logic:notEqual name="usr090Form" property="usr090SortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_DATE)%>">
            <th class="w25 no_w">
              <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_DATE)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>')">
                <span>
                  <gsmsg:write key="user.usr090.2" />
                </span>
              </a>
            </th>
          </logic:notEqual>

          <!-- キャリア -->
          <logic:equal name="usr090Form" property="usr090SortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_CAR)%>">

            <logic:equal name="usr090Form" property="usr090OrderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>">
              <th class="w25 no_w">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_CAR)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>')">
                  <span>
                    <gsmsg:write key="cmn.careers" />
                    <span class="classic-display">▲</span>
                    <span class="original-display">
                      <i class="icon-sort_up"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>

            <logic:equal name="usr090Form" property="usr090OrderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>">
              <th class="w25 no_w">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_CAR)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>')">
                  <span>
                    <gsmsg:write key="cmn.careers" />
                    <span class="classic-display">▼</span>
                    <span class="original-display">
                      <i class="icon-sort_down"></i>
                    </span>
                  </span>
                  </span>
                </a>
              </th>
            </logic:equal>

          </logic:equal>

          <logic:notEqual name="usr090Form" property="usr090SortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_CAR)%>">
            <th class="w25 no_w">
              <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_CAR)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>')">
                <span>
                  <gsmsg:write key="cmn.careers" />
                </span>
              </a>
            </th>
          </logic:notEqual>

          <!-- 個体識別番号 -->
          <logic:equal name="usr090Form" property="usr090SortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_UID)%>">

            <logic:equal name="usr090Form" property="usr090OrderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>">
              <th class="w50 no_w">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_UID)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>')">
                  <span>
                    <gsmsg:write key="cmn.identification.number" />
                    <span class="classic-display">▲</span>
                    <span class="original-display">
                      <i class="icon-sort_up"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>

            <logic:equal name="usr090Form" property="usr090OrderKey" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>">
              <th class="w50 no_w">
                <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_UID)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>')">
                  <span>
                    <gsmsg:write key="cmn.identification.number" />
                    <span class="classic-display">▼</span>
                    <span class="original-display">
                      <i class="icon-sort_down"></i>
                    </span>
                  </span>
                  </span>
                </a>
              </th>
            </logic:equal>

          </logic:equal>

          <logic:notEqual name="usr090Form" property="usr090SortKey" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_UID)%>">
            <th class="w50 no_w">
              <a href="#!" onclick="return onTitleLinkSubmit('<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SORT_UID)%>', '<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>')">
                <span>
                  <gsmsg:write key="cmn.identification.number" />
                </span>
              </a>
            </th>
          </logic:notEqual>

        </tr>

        <bean:define id="mod" value="0" />
        <logic:notEmpty name="usr090Form" property="usr090UidList">
          <logic:iterate id="uidList" name="usr090Form" property="usr090UidList" indexId="idx">

            <tr class="js_listHover cursor_p" id="<bean:write name="uidList" property="clhUid" />">
              <td class="txt_c no_w js_listClick">
                <span>
                  <bean:write name="uidList" property="loginTime" />
                </span>
              </td>
              <td class="txt_l no_w js_listClick">
                <span>
                  <bean:write name="uidList" property="carName" />
                </span>
              </td>
              <td class="txt_l js_listClick">
                <span class="cl_linkDef">
                    <bean:write name="uidList" property="clhUid" />
                </span>
              </td>
            </tr>

          </logic:iterate>
        </logic:notEmpty>

      </table>

      <div class="paging">
        <button type="button" class="webIconBtn" onClick="buttonPush('pageleft');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo" property="usr090PageBottom" onchange="return changePage('1');">
          <html:optionsCollection name="usr090Form" property="usr090PageList" value="value" label="label" />
        </html:select>
        <logic:empty name="usr090Form" property="usr090PageList">
          <html:select styleClass="paging_combo" property="usr090PageBottom">
            <option value="1">1 / 1</option>
          </html:select>
        </logic:empty>
        <button type="button" class="webIconBtn" onClick="buttonPush('pageright');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>

      <div class="footerBtn_block mt10 txt_r">
        <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>

    </div>

  </html:form>
</body>
</html:html>