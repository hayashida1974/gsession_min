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
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../main/js/man070.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="main.man002.30" /></title>
</head>
<body onload="man070load();">
<html:form action="/main/man070">
<input type="hidden" name="CMD" value="kakunin">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w90 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man002.30" />
    </li>
    <li>
      <div>
        <button type="submit" value="cmn.ok" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="cmn.ok">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="cmn.ok">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_kanri_menu');">
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
        <gsmsg:write key="main.man002.30" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio styleId="proxyuse" name="man070Form" property="man070pxyUseKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PROXY_SERVER_NOT_USE) %>" onclick="radioChkChange(0)" />
          <label for="proxyuse"><gsmsg:write key="main.man070kn.2" /></label>
          <html:radio styleClass="ml10" styleId="proxynotuse" name="man070Form" property="man070pxyUseKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.PROXY_SERVER_USE) %>" onclick="radioChkChange(1)" />
          <label for="proxynotuse"><gsmsg:write key="main.man070kn.3" /></label>
        </div>
        <table class="w100 mt10 table-noBorder">
          <tr>
            <td class="w15 txt_r no_w">
              <gsmsg:write key="cmn.address.2" />：
            </td>
            <td class="w35 txt_l">
              <html:text name="man070Form" property="man070address" styleClass="wp200" maxlength="200" />
            </td>
            <td class="w50 txt_l cl_fontWarn no_w fs_13">
              <gsmsg:write key="main.man070.1" />
            </td>
          </tr>
          <tr>
            <td class="w15 txt_r no_w">
              <gsmsg:write key="cmn.port.number" />：
            </td>
            <td class="w35 txt_l">
              <html:text name="man070Form" property="man070portnum" styleClass="wp60" maxlength="5" />
            </td>
            <td class="w50 txt_l cl_fontWarn no_w fs_13">
              <gsmsg:write key="main.man070.3" />
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr  id="proxyAuth">
      <th class="w25 no_w">
        <gsmsg:write key="main.man070.4" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <div>
          <span class="verAlignMid">
            <html:checkbox styleId="userAuth" name="man070Form" property="man070Auth" value="<%= String.valueOf(jp.groupsession.v2.man.man070.Man070Form.MAN070_AUTH_USE) %>" onclick="chkUserAuth();" />
            <label for="userAuth"><gsmsg:write key="main.man070.5" /></label>
          </span>
        </div>
        <span id="userAuthElement">
          <div class="cl_fontWarn mt10">
            <gsmsg:write key="main.man070.10" />
          </div>
          <table class="w100 table-noBorder">
            <tr>
              <td class="w15 txt_r no_w">
                <gsmsg:write key="cmn.user" />：
              </td>
              <td class="w35 txt_l">
                <html:text name="man070Form" property="man070AuthUser" styleClass="wp200" maxlength="256" />
              </td>
              <td class="w50 txt_l cl_fontWarn no_w fs_13">
                <gsmsg:write key="cmn.comments" /><gsmsg:write key="main.man070.6" />
              </td>
            </tr>
            <tr>
              <td class="w15 txt_r no_w">
                <gsmsg:write key="user.117" />：
              </td>
              <td class="w35 txt_l">
                <html:password name="man070Form" property="man070AuthPassword" styleClass="wp200" maxlength="256" />
              </td>
              <td class="w50 txt_l cl_fontWarn no_w fs_13">
                <gsmsg:write key="cmn.comments" /><gsmsg:write key="main.man070.7" />
              </td>
            </tr>
          </table>
        </span>
      </td>
    </tr>
    <tr id="proxyAuthElement">
      <th>
        <gsmsg:write key="main.man070.9" />
      </th>
      <td>
        <div class="cl_fontWarn fs_13">
          <gsmsg:write key="sml.sml110.03" />
        </div>
        <div class="cl_fontWarn fs_13">
          <gsmsg:write key="main.man070.11" />
        </div>
        <html:textarea name="man070Form" property="man070NoProxyAddress" styleClass="w100" rows="6"></html:textarea>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="submit" value="cmn.ok" class="baseBtn">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="cmn.ok">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="cmn.ok">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_kanri_menu');">
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