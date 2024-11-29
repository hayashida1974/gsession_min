<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man070kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="main.man070kn.1" /></title>
</head>
<body class="body_03">
<html:form action="/main/man070kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="man070pxyUseKbn" />
<html:hidden property="man070address" />
<html:hidden property="man070portnum" />
<html:hidden property="man070Auth" />
<html:hidden property="man070AuthUser" />
<html:hidden property="man070AuthPassword" />
<html:hidden property="man070NoProxyAddress" />
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w90 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man070kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('settei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('input');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="main.man002.30" />
      </th>
      <td class="w75">
        <logic:equal name="man070knForm" property="man070pxyUseKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PROXY_SERVER_NOT_USE) %>">
          <gsmsg:write key="main.man070kn.2" />
        </logic:equal>
        <logic:equal name="man070knForm" property="man070pxyUseKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PROXY_SERVER_USE) %>">
          <div>
            <gsmsg:write key="main.man070kn.3" />
          </div>
          <table class="w100 mt10 table-noBorder">
            <tr>
              <td class="w15 txt_r no_w">
                <gsmsg:write key="cmn.address.2" />：
              </td>
              <td class="w85 txt_l no_w">
                <bean:write name="man070knForm" property="man070address" />
              </td>
            </tr>
            <tr>
              <td class="w15 txt_r no_w">
                <gsmsg:write key="cmn.port.number" />：
              </td>
              <td class="w85 txt_l no_w">
                <bean:write name="man070knForm" property="man070portnum" />
              </td>
            </tr>
          </table>
        </logic:equal>
      </td>
    </tr>
    <logic:equal name="man070knForm" property="man070pxyUseKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PROXY_SERVER_USE) %>">
      <tr>
        <th class="w25">
          <gsmsg:write key="main.man070.4" />
        </th>
        <td class="w75">
          <logic:notEqual name="man070Form" property="man070Auth" value="<%= String.valueOf(jp.groupsession.v2.man.man070.Man070Form.MAN070_AUTH_USE) %>">
            <gsmsg:write key="main.man070kn.4" />
          </logic:notEqual>
          <logic:equal name="man070Form" property="man070Auth" value="<%= String.valueOf(jp.groupsession.v2.man.man070.Man070Form.MAN070_AUTH_USE) %>">
            <div>
              <gsmsg:write key="main.man070.5" />
            </div>
            <table class="w100 mt10 table-noBorder">
              <tr>
                <td class="w15 txt_r no_w">
                  <gsmsg:write key="cmn.user" />：
                </td>
                <td class="w85 txt_l no_w">
                  <bean:write name="man070Form" property="man070AuthUser" />
                </td>
              </tr>
              <tr>
                <td class="w15 txt_r no_w">
                  <gsmsg:write key="user.117" />：
                </td>
                <td class="w85 txt_l no_w">
                  *****
                </td>
              </tr>
            </table>
          </logic:equal>
        </td>
      </tr>
      <tr>
        <th class="w25">
          <gsmsg:write key="main.man070.9" />
        </th>
        <td class="w75">
          <logic:iterate id="address" name="man070knForm" property="man070knViewNoProxyAddress">
            <div><bean:write name="address" /></div>
          </logic:iterate>
        </td>
      </tr>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('settei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('input');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>