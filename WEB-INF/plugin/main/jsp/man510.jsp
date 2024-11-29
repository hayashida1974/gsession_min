    <%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstApi"%>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man510.1" />
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='./js/man510.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmnOAuth.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body>
<html:form action="/main/man510">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="scrollY" value="">
<html:hidden name="man510Form" property="man510tokenSid"/>
<% String scrollYStr = request.getParameter("scrollY"); %>
<% if (scrollYStr != null) {%>
<script><!--

    $(function () {
        $(window).scrollTop(<%=scrollYStr%>);
    });

--></script>
<% } %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man510.1" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('man510Ok');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man510Back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
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
        <div class="verAlignMid">
          <html:radio name="man510Form" styleId="man510useOtp_use" property="man510useOtp" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USE) %>" />
          <label for="man510useOtp_use"><gsmsg:write key="main.man510.13" /></label>
          <html:radio name="man510Form" styleClass="ml10" styleId="man510useOtp_nouse" property="man510useOtp" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_NOUSE) %>" />
          <label for="man510useOtp_nouse"><gsmsg:write key="main.man510.14" /></label>
        </div>
      </td>
    </tr>
    <bean:define id="otpUseConfDsp" value="display_n"/>
    <logic:equal name="man510Form" property="man510useOtp" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USE) %>">
      <bean:define id="otpUseConfDsp" value=""/>
    </logic:equal>
    <%-- ワンタイムパスワード使用設定 --%>
    <tr class="<bean:write name="otpUseConfDsp"/>">
      <th class="w20 no_w">
        <gsmsg:write key="main.man510.9" />
      </th>
      <td class="w80">
        <div>
          <span class="verAlignMid">
            <gsmsg:write key="cmn.target"/>：
            <html:radio name="man510Form" styleId="man510otpUser_01" styleClass="ml5" property="man510otpUser" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_TARGET_ALL) %>" />
            <label for="man510otpUser_01"><gsmsg:write key="cmn.alluser" /></label>
            <html:radio name="man510Form" styleId="man510otpUser_02" styleClass="ml10" property="man510otpUser" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_TARGET_SELECT) %>" />
            <label for="man510otpUser_02"><gsmsg:write key="cmn.named.user" /></label>
          </span>
        </div>
        <bean:define id="otpUserSelDsp" value=""/>
        <logic:equal name="man510Form" property="man510otpUser" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_TARGET_ALL) %>">
          <bean:define id="otpUserSelDsp" value="display_n"/>
        </logic:equal>
        <span class="js_otpuser_sel <bean:write name="otpUserSelDsp"/>">
          <div class="verAlignMid ml5 mt5">
            <html:radio name="man510Form" styleId="man510otpUserKbn_01" property="man510otpUserKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USRTYPE_USE) %>" />
            <label for="man510otpUserKbn_01"><gsmsg:write key="main.man510.10" /></label>
            <html:radio name="man510Form" styleId="man510otpUserKbn_02" styleClass="ml10" property="man510otpUserKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_USRTYPE_NOUSE) %>" />
            <label for="man510otpUserKbn_02"><gsmsg:write key="main.man510.11" /></label>
          </div>
          <div class="pt5">
            <ui:usrgrpselector name="man510Form" property="man510targetUserUI" styleClass="hp160" />
          </div>
        </span>
        <div class="mt10">
          <span class="verAlignMid">
            <gsmsg:write key="cmn.conditions"/>：
            <html:radio name="man510Form" styleId="man510otpCond_01" property="man510otpCond" styleClass="ml5" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_IPCOND_ALL) %>" />
            <label for="man510otpCond_01"><gsmsg:write key="main.man510.15" /></label>
            <html:radio name="man510Form" styleId="man510otpCond_02" property="man510otpCond" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_IPCOND_OUTIP) %>" />
            <label for="man510otpCond_02"><gsmsg:write key="main.man510.16" /></label>
          </span>
        </div>
        <bean:define id="otpIpDsp" value=""/>
          <logic:equal name="man510Form" property="man510otpCond" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OTP_IPCOND_ALL) %>">
        <bean:define id="otpIpDsp" value="display_n"/>
        </logic:equal>
        <div class="js_otpip_sel <bean:write name="otpIpDsp"/>">
          <html:textarea name="man510Form" property="man510otpIpArea" styleClass="wp500 ml5 mr5" rows="6"/>
          <div class="cl_fontWarn">
            <gsmsg:write key="main.man430.7" /><br>
            <gsmsg:write key="cmn.cmn390.14" /><br>
            <gsmsg:write key="main.man430.8" /><br>
            <gsmsg:write key="cmn.cmn390.11" />
          <div class="ml20">
            <gsmsg:write key="cmn.cmn390.12" /><br>
            <gsmsg:write key="cmn.cmn390.13" />
          </div>
        </div>
      </td>
    </tr>
    <%-- SMTPサーバ --%>
    <tr class="<bean:write name="otpUseConfDsp"/>">
      <th class="w20 no_w">
        <gsmsg:write key="sml.sml110.06" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <div>
          <gsmsg:write key="main.man510.12" />
        </div>
        <table class="table-noBorder w100">
          <bean:size id="providerListSize" name="man510Form" property="man510providerList" />
          <logic:notEqual name="providerListSize" value="1">
          <tr>
            <td class="no_w fw_b txt_r">
              <gsmsg:write key="cmn.auth.method" />
            </td>
            <td class="w100">
              <span class="verAlignMid">
                <html:radio name="man510Form" property="man510authType" styleId="authMethod1" onclick="changeAuthMethod();"  value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAILSERVER_AUTH_TYPE_NORMAL) %>" />
                <label for="authMethod1"><gsmsg:write key="wml.309" /></label>
                <html:radio name="man510Form" property="man510authType" styleId="authMethod2" onclick="changeAuthMethod();" styleClass="ml10"  value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAILSERVER_AUTH_TYPE_OAUTH) %>" />
                <label for="authMethod2"><gsmsg:write key="wml.310" /></label>
              </span>
            </td>
          </tr>
          </logic:notEqual>
          <logic:equal name="providerListSize" value="1">
            <html:hidden property="man510authType" value="0" />
          </logic:equal>

          <tr class="js_BaseAuth">
            <td class="no_w fw_b txt_r">
              <gsmsg:write key="sml.sml110.07" />
            </td>
            <td class="w100">
              <html:text name="man510Form" maxlength="200" property="man510SmtpUrl" styleClass="wp350" />
            </td>
          </tr>
          <tr class="js_BaseAuth">
            <td class="w no_w fw_b txt_t txt_r">
              <gsmsg:write key="sml.sml110.08" />
            </td>
            <td class="w100">
              <div>
                <html:text name="man510Form"  maxlength="5" property="man510SmtpPort" styleClass="wp50 mr5" />
                <span class="cl_fontWarn fs_12"><gsmsg:write key="sml.sml110.05" /></span>
              </div>
              <div class="mt5">
                <gsmsg:write key="cmn.ango" />:
                <html:select name="man510Form" property="man510SmtpEncrypt">
                  <html:optionsCollection name="man510Form" property="man510AngoProtocolCombo" value="value" label="label" />
                </html:select>
                <span class="cl_fontWarn fs_12"><gsmsg:write key="cmn.check.portnumber" /></span>
              </div>
            </td>
          </tr>
          <tr>
            <td class="no_w fw_b txt_r">
              <gsmsg:write key="sml.sml110.17" />
            </td>
            <td class="w100">
              <html:text name="man510Form" maxlength="200" property="man510FromAddress" styleClass="wp350" />
            </td>
          </tr>
          <tr class="js_BaseAuth">
            <td class="">
            </td>
            <td class="w100">
              <div class="p10 bor1 bgC_other1">
                <div>
                  <gsmsg:write key="sml.sml110.12" />
                </div>
                <div class="cl_fontWarn fs_12">
                  <gsmsg:write key="sml.sml110.02" />
                </div>
                <table class="w100">
                  <tr>
                    <td class="no_w txt_r fw_b">
                      <gsmsg:write key="sml.sml110.22" />
                    </td>
                    <td class="w100 txt_l">
                      <html:text name="man510Form"  maxlength="100" property="man510SmtpUser" styleClass="wp200" />
                    </td>
                  </tr>
                  <tr>
                    <td class="no_w txt_r fw_b">
                      <gsmsg:write key="sml.sml110.21" />
                    </td>
                    <td class="w100 txt_l">
                      <html:password name="man510Form"  maxlength="140" property="man510SmtpPass" styleClass="wp200" />
                    </td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>

          <tr class="js_OAuth display_none">
            <td class="no_w fw_b txt_r">
              <gsmsg:write key="wml.308" />
            </td>
            <td class="w100">
              <logic:notEmpty name="man510Form" property="man510providerList">
              <html:select name="man510Form" property="man510provider">
                <html:optionsCollection name="man510Form" property="man510providerList" value="value" label="label" />
              </html:select>
              </logic:notEmpty>

              <span class="verAlignMid">
                <button type="button" class="baseBtn mr20" onclick="doOAuth('man510provider', 'man510FromAddress', 'man510tokenSid');"><gsmsg:write key="wml.313" /></button>
                <span class="ml10">
                  <bean:define id="oauthFlg" name="man510Form" property="man510oauthCompFlg" type="java.lang.Boolean" />
                  <gsmsg:write key="wml.316"/>:
                  <%  if (oauthFlg) { %><gsmsg:write key="wml.314"/>
                  <%  } else { %><span class="cl_fontWarn"><gsmsg:write key="wml.315"/></span><% } %>
                </span>
              </span>
            </td>
          </tr>

        </table>
      </td>
    </tr>
    <%-- ワンタイムパスワード通知先メールアドレス --%>
    <tr class="<bean:write name="otpUseConfDsp"/>">
      <th class="w20 no_w">
        <gsmsg:write key="user.usr060.4" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <div>
          <gsmsg:write key="main.man510.20"/>
        </div>
        <div class="mt5">
          <html:text name="man510Form" property="man510sendToAddress" styleClass="wp350" maxlength="256"/>
        </div>
      </td>
    </tr>
    <%-- トークン認証 --%>
    <tr class="<bean:write name="otpUseConfDsp"/>">
      <th class="w20 no_w">
        <gsmsg:write key="api.api020.3" />
      </th>
      <td class="w80">
        <div>
          <gsmsg:write key="api.api020.4"/>
        </div>
        <div>
          <gsmsg:write key="api.api020.5"/>
        </div>
        <div class="verAlignMid mt10 mb10">
          <html:radio name="man510Form" property="man510useToken" styleId="man510useToken_1" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USE) %>"  />
          <label for="man510useToken_1"><gsmsg:write key="api.api020.9" /></label>
          <html:radio name="man510Form" property="man510useToken" styleClass="ml10" styleId="man510useToken_2" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP) %>"  />
          <label for="man510useToken_2"><gsmsg:write key="api.api020.10" /></label>
          <span class="display_n">
            <html:radio name="man510Form" property="man510useToken" styleClass="ml10" styleId="man510useToken_0" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE) %>"  />
            <label for="man510useToken_0"><gsmsg:write key="api.api020.10" /></label>
          </span>
        </div>
        <bean:define id="nonDsp" value="" />
        <logic:notEqual name="man510Form" property="man510useToken" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP)%>">
          <bean:define id="nonDsp" value="display_n" />
        </logic:notEqual>
        <div class="<bean:write name="nonDsp"/>">
          <html:textarea name="man510Form" property="man510tokenIpArea" styleClass="w100" rows="6"/>
          <div class="cl_fontWarn">
            <gsmsg:write key="main.man430.7" /><br>
            <gsmsg:write key="cmn.cmn390.14" /><br>
            <gsmsg:write key="main.man430.8" /><br>
            <gsmsg:write key="cmn.cmn390.11" />
          </div>
          <div class="cl_fontWarn ml20 mb10">
            <gsmsg:write key="cmn.cmn390.12" /><br>
            <gsmsg:write key="cmn.cmn390.13" />
          </div>

        </div>
        <div class="mt5">
           <gsmsg:write key="api.api020.11"/>:
           <html:select name="man510Form" property="man510tokenLimit">
             <html:optionsCollection name="man510Form" property="man510tokenLimitOption" />
           </html:select>
           <div class="mt5 display_none js_limitFree">
             <gsmsg:write key="api.api020.17" />
           </div>

        </div>
        <div class="mt5">
          <gsmsg:write key="main.man510.21"/>
        </div>
      </td>
    </tr>
    <%-- ベーシック認証 --%>
    <tr class="<bean:write name="otpUseConfDsp"/>">
      <th class="w20 no_w">
        <gsmsg:write key="api.api020.12" />
      </th>
      <td class="w80">
        <div>
          <gsmsg:write key="main.man510.19"/>
        </div>
        <div class="verAlignMid mt10">
          <html:radio name="man510Form" property="man510useBasic" styleId="man510useBasic_0" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_NOUSE) %>" />
          <label for="man510useBasic_0"><gsmsg:write key="api.api020.8" /></label>
          <html:radio name="man510Form" property="man510useBasic" styleClass="ml10" styleId="man510useBasic_2" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP) %>" />
          <label for="man510useBasic_2"><gsmsg:write key="api.api020.10" /></label>
        </div>
        <span class="display_n">
          <html:radio name="man510Form" property="man510useBasic" styleClass="ml10" styleId="man510useBasic_1" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USE) %>"  />
          <label for="man510useBasic_1"><gsmsg:write key="api.api020.10" /></label>
        </span>
        <bean:define id="nonDsp" value="" />
        <logic:notEqual name="man510Form" property="man510useBasic" value="<%= String.valueOf(GSConstApi.USEKBN_AUTH_USEIP)%>">
          <bean:define id="nonDsp" value="display_n" />
        </logic:notEqual>
        <div class="mt10 <bean:write name="nonDsp"/>">
          <html:textarea name="man510Form" property="man510basicIpArea" styleClass="w100" rows="6"/>
          <div class="cl_fontWarn">
            <gsmsg:write key="main.man430.7" /><br>
            <gsmsg:write key="cmn.cmn390.14" /><br>
            <gsmsg:write key="main.man430.8" /><br>
            <gsmsg:write key="cmn.cmn390.11" />
          <div class="ml20">
            <gsmsg:write key="cmn.cmn390.12" /><br>
            <gsmsg:write key="cmn.cmn390.13" />
          </div>
        </div>
        <div><gsmsg:write key="main.man510.21"/></div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('man510Ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man510Back');">
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