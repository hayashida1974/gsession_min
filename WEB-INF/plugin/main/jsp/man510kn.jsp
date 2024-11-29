<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstApi"%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man510kn.1" />
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='./js/man510.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/main/man510kn">

<input type="hidden" name="CMD" value="">
<html:hidden name="man510knForm" property="man510tokenSid"/>

<logic:notEmpty name="man510knForm" property="man510targetUser">
  <logic:iterate id="targetUser" name="man510knForm" property="man510targetUser">
    <input type="hidden" name="man510targetUser" value="<bean:write name="targetUser"/>">
  </logic:iterate>
</logic:notEmpty>


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man510kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('man510knOk');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man510knBack');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
  <table class="table-left">
  <%-- ワンタイムパスワードログイン機能 --%>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man510.3"  />
      </th>
      <td class="w80">
        <html:hidden name="man510knForm" property="man510useOtp"/>
        <logic:equal name="man510knForm" property="man510useOtp" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USE) %>">
          <gsmsg:write key="main.man510.13" />
        </logic:equal>
        <logic:notEqual name="man510knForm" property="man510useOtp" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USE) %>">
          <gsmsg:write key="main.man510.14" />
        </logic:notEqual>
      </td>
    </tr>
    <bean:define id="otpUseConfDsp" value="display_n"/>
    <logic:equal name="man510knForm" property="man510useOtp" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USE) %>">
      <bean:define id="otpUseConfDsp" value=""/>
    </logic:equal>
    <%-- ワンタイムパスワード使用設定 --%>
    <tr class="<bean:write name="otpUseConfDsp"/>">
      <th class="w20 no_w">
        <gsmsg:write key="main.man510.9" />
      </th>
      <td>
         <div class="flo_l mr5"><gsmsg:write key="cmn.target"/>:</div>
         <div class="flo_l w90">
           <html:hidden name="man510knForm" property="man510otpUser"/>
           <html:hidden name="man510knForm" property="man510otpUserKbn"/>
           <logic:equal name="man510knForm" property="man510otpUser" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_TARGET_ALL) %>">
             <gsmsg:write key="cmn.alluser" />
           </logic:equal>
           <logic:notEqual name="man510knForm" property="man510otpUser" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_TARGET_ALL) %>">
             <gsmsg:write key="cmn.named.user" />
             <gsform:usrgrpselect name="man510knForm" property="man510targetUserList" onchange="buttonPush('dsp');" kakunin="kakunin" ></gsform:usrgrpselect>
           </logic:notEqual>
         </div>
         <br class="side_folder-focus">
         <div class="flo_l mr5 mt10"><gsmsg:write key="cmn.conditions"/>:</div>
         <div class="flo_l mt10">
           <html:hidden name="man510knForm" property="man510otpCond"/>
           <logic:equal name="man510knForm" property="man510otpCond" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_IPCOND_ALL) %>">
             <gsmsg:write key="main.man510.15" />
           </logic:equal>
           <logic:notEqual name="man510knForm" property="man510otpCond" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_IPCOND_ALL) %>">
             <gsmsg:write key="main.man510.16" />
           </logic:notEqual>
           <html:hidden name="man510knForm" property="man510otpIpArea"/>
           <logic:notEqual name="man510knForm" property="man510otpCond" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_IPCOND_ALL) %>">
             <div>
               <bean:write name="man510knForm" property="man510otpIpDsp" filter="false"/>
             </div>
           </logic:notEqual>
          </div>
      </td>
    </tr>
  <%-- SMTPサーバ --%>
    <tr class="<bean:write name="otpUseConfDsp"/>">
      <th class="w20 no_w">
        <gsmsg:write key="sml.sml110.06" />
      </th>
      <td>
        <table class="table-noBorder w100">
          <bean:size id="providerListSize" name="man510knForm" property="man510providerList" />
          <logic:notEqual name="providerListSize" value="1">
          <tr>
            <td class="no_w fw_b txt_r">
              <gsmsg:write key="cmn.auth.method" />
            </td>
            <td class="w100">
              <html:hidden name="man510knForm" property="man510authType" />
              <logic:equal name="man510knForm" property="man510authType" value="0">
                <gsmsg:write key="cmn.auth.basic" />
              </logic:equal>
              <logic:equal name="man510knForm" property="man510authType" value="1">
                <gsmsg:write key="cmn.auth.oauth" />
              </logic:equal>
            </td>
          </tr>
          </logic:notEqual>
          <logic:equal name="man510knForm" property="man510authType" value="0">
          <tr>
            <td class="no_w fw_b txt_r">
              <gsmsg:write key="sml.sml110.07" />
            </td>
            <td class="w100">
              <html:hidden name="man510knForm" property="man510SmtpUrl" />
              <bean:write name="man510knForm" property="man510SmtpUrl" />
            </td>
          </tr>
          <tr>
            <td class="no_w fw_b txt_r txt_t">
              <gsmsg:write key="sml.sml110.08" />
            </td>
            <td class="w100">
              <div>
                <html:hidden name="man510knForm"   property="man510SmtpPort"  />
                <bean:write name="man510knForm"   property="man510SmtpPort"  />
              </div>
              <div>
                <html:hidden name="man510knForm"   property="man510SmtpEncrypt"  />
                <gsmsg:write key="cmn.ango" />:
                <bean:write name="man510knForm" property="man510knSendEncrypt" />
              </div>
            </td>
          </tr>
          </logic:equal>
          <tr>
            <td class="no_w fw_b txt_r">
              <gsmsg:write key="sml.sml110.17" />
            </td>
            <td class="w100">
              <html:hidden name="man510knForm"   property="man510FromAddress"  />
              <bean:write name="man510knForm"   property="man510FromAddress"  />
            </td>
          </tr>
          <logic:equal name="man510knForm" property="man510authType" value="0">
          <tr>
            <td class="no_w fw_b txt_r">
              <gsmsg:write key="sml.sml110.22" />
            </td>
            <td class="w100">
              <html:hidden name="man510knForm" property="man510SmtpUser"  />
              <html:hidden name="man510knForm" property="man510SmtpPass"  />
              <bean:write name="man510knForm" property="man510SmtpUser"  />
            </td>
          </tr>
          </logic:equal>
          <logic:equal name="man510knForm" property="man510authType" value="1">
          <tr>
            <td class="no_w fw_b txt_r">
              <gsmsg:write key="cmn.auth.provider" />
            </td>
            <td class="w100">
              <html:hidden name="man510knForm" property="man510provider" />
              <html:hidden name="man510knForm" property="man510oauthCompFlg" />
              <html:hidden name="man510knForm" property="man510SendProvider" />
              <logic:equal name="man510knForm" property="man510SendProvider" value="1">
                <gsmsg:write key="cmn.cmn260.02" />
              </logic:equal>
              <logic:equal name="man510knForm" property="man510SendProvider" value="2">
                <gsmsg:write key="cmn.cmn260.03" />
              </logic:equal>
              <span class="ml10">
                <gsmsg:write key="wml.316" />:
                <bean:define id="oauthFlg" name="man510knForm" property="man510oauthCompFlg" type="java.lang.Boolean" />
                <%  if (oauthFlg) { %><gsmsg:write key="cmn.auth.already" />
                <%  } else { %><span class="cl_fontWarn"><gsmsg:write key="cmn.auth.yet" /></span><% } %>
              </span>
            </td>
          </tr>
          </logic:equal>
        </table>
      </td>
    </tr>
  <%-- ワンタイムパスワード通知先メールアドレス --%>
    <tr class="<bean:write name="otpUseConfDsp"/>">
      <th class="w20 no_w">
        <gsmsg:write key="user.usr060.4" />
      </th>
      <td>
        <html:hidden name="man510knForm" property="man510sendToAddress" />
        <bean:write name="man510knForm" property="man510sendToAddress" />
      </td>
    </tr>
  <%-- トークン認証 --%>
    <tr class="<bean:write name="otpUseConfDsp"/>">
      <th class="w20 no_w">
        <gsmsg:write key="api.api020.3" />
      </th>
      <td>
        <div>
          <html:hidden name="man510knForm" property="man510useToken"/>
          <logic:equal name="man510knForm" property="man510useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE) %>">
            <gsmsg:write key="api.api020.8" />
          </logic:equal>
          <logic:equal name="man510knForm" property="man510useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USE) %>">
            <gsmsg:write key="api.api020.9" />
          </logic:equal>
          <logic:equal name="man510knForm" property="man510useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP) %>">
            <gsmsg:write key="api.api020.10" />
          </logic:equal>
       </div>
        <bean:define id="nonDsp" value="" />
        <logic:notEqual name="man510knForm" property="man510useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP)%>">
          <bean:define id="nonDsp" value="display_n" />
        </logic:notEqual>
        <div class="ml10<bean:write name="nonDsp"/>">
          <html:hidden name="man510knForm" property="man510tokenIpArea"/>
          <bean:write name="man510knForm" property="man510tokenIpAreaDsp"  filter="false"/>
        </div>
        <bean:define id="nonDsp" value="" />
        <logic:equal name="man510knForm" property="man510useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE)%>">
          <bean:define id="nonDsp" value="display_n" />
        </logic:equal>
        <div class="mt10 <bean:write name="nonDsp"/>">
           <html:hidden name="man510knForm" property="man510tokenLimit"/>
           <gsmsg:write key="api.api020.11"/>:<bean:write name="man510knForm" property="man510tokenLimitDsp" />
           <html:hidden name="man510knForm" property="man510tokenAutoDel"/>
        </div>
      </td>
    </tr>
  <%-- ベーシック認証 --%>
    <tr class="<bean:write name="otpUseConfDsp"/>">
      <th class="w20 no_w">
        <gsmsg:write key="api.api020.12" />
      </th>
      <td>
        <div>
          <html:hidden name="man510knForm" property="man510useBasic"/>
          <logic:equal name="man510knForm" property="man510useBasic" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE) %>">
            <gsmsg:write key="api.api020.8" />
          </logic:equal>
          <logic:equal name="man510knForm" property="man510useBasic" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USE) %>">
            <gsmsg:write key="api.api020.9" />
          </logic:equal>
          <logic:equal name="man510knForm" property="man510useBasic" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP) %>">
            <gsmsg:write key="api.api020.10" />
          </logic:equal>
        </div>
        <bean:define id="nonDsp" value="" />
        <logic:notEqual name="man510knForm" property="man510useBasic" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP)%>">
          <bean:define id="nonDsp" value="display_n" />
        </logic:notEqual>
        <div class="<bean:write name="nonDsp"/>">
          <html:hidden name="man510knForm" property="man510basicIpArea" />
          <bean:write name="man510knForm" property="man510basicIpAreaDsp" filter="false"/>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('man510knOk');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man510knBack');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>
</body>
</html:html>