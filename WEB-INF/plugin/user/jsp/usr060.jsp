<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<%
  String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../user/css/user.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<logic:equal name="usr060Form" property="usr060AddMode" value="true">
  <title>GROUPSESSION <gsmsg:write key="user.usr060.3" /></title>
</logic:equal>
<logic:notEqual name="usr060Form" property="usr060AddMode" value="true">
  <title>GROUPSESSION <gsmsg:write key="user.usr060.1" /></title>
</logic:notEqual>
</head>

<body>
  <html:form action="/user/usr060">
    <input type="hidden" name="CMD" value="dsp">
    <html:hidden property="url" />
    <html:hidden name="usr060Form" property="usr060Token" />
    <html:hidden name="usr060Form" property="usr060otpSended" />
    <html:hidden name="usr060Form" property="usr060AddMode" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <!-- BODY -->

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.preferences2" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shain.info" /></span><logic:equal name="usr060Form" property="usr060AddMode" value="true"><gsmsg:write key="user.usr060.3" /></logic:equal><logic:notEqual name="usr060Form" property="usr060AddMode" value="true"><gsmsg:write key="user.usr060.1" /></logic:notEqual>
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush('usr060Ok');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('usr060Back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table cellpadding="5" cellspacing="0" border="0" class="table-left">
        <%-- ワンタイムパスワード通知先メールアドレス --%>
        <tr>
          <th class="w30 txt_m txt_l no_w">
            <span>
              <gsmsg:write key="user.usr060.4" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="w70 txt_m txt_l no_w" colspan="5">
            <logic:notEqual name="usr060Form" property="usr060otpSended" value="true">
              <html:text name="usr060Form" property="usr060SendToAddress" styleClass="wp350" maxlength="256" />
              <button type="submit" name="usr060SendOtp" value="usr060SendOtp" class="baseBtn">
                 <img class="classic-display" src="../common/images/classic/icon_mail.png">
                <img class="original-display" src="../common/images/original/icon_mail.png">
                <span>
                  <gsmsg:write key="user.usr060.7" />
                </span>
              </button>
              <button type="submit" name="usr060Reenter" value="usr060Reenter" class="baseBtn display_n">
                <gsmsg:write key="anp.reenter" />
              </button>
            </logic:notEqual>
            <logic:equal name="usr060Form" property="usr060otpSended" value="true">
              <html:text name="usr060Form" property="usr060SendToAddress" styleClass="wp350" maxlength="256" disabled="true" />
              <button type="submit" name="usr060SendOtp" value="usr060SendOtp" class="baseBtn display_n">
                <img class="classic-display" src="../common/images/classic/icon_mail.png">
                <img class="original-display" src="../common/images/original/icon_mail.png">
                <span>
                  <gsmsg:write key="user.usr060.7" />
                </span>
              </button>
              <button type="submit" name="usr060Reenter" value="usr060Reenter" class="baseBtn">
                <gsmsg:write key="anp.reenter" />
              </button>
            </logic:equal>
          </td>
        </tr>
        <%-- ワンタイムパスワード --%>
        <tr>
          <th class="txt_m txt_l no_w">
            <span>
              <gsmsg:write key="user.usr060.5" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_m txt_l" colspan="5">
            <logic:notEqual name="usr060Form" property="usr060otpSended" value="true">
              <html:password name="usr060Form" property="usr060KakuninPass" maxlength="4" disabled="true" />
            </logic:notEqual>
            <logic:equal name="usr060Form" property="usr060otpSended" value="true">
              <html:password name="usr060Form" property="usr060KakuninPass" maxlength="4" />
            </logic:equal>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush('usr060Ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('usr060Back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>


  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>