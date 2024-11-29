<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.wml.wml280kn.Wml280knForm" %>
<%-- 定数値 --%>
<%
  String  acModeNormal    = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.ACCOUNTMODE_NORMAL);
  String  acModePsn       = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.ACCOUNTMODE_PSNLSETTING);
  String  acModeCommon    = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.ACCOUNTMODE_COMMON);
  String  cmdModeAdd      = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.CMDMODE_ADD);
  String  cmdModeEdit     = String.valueOf(jp.groupsession.v2.cmn.GSConstWebmail.CMDMODE_EDIT);
%>

<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml280.01" /></title>
</head>

<html:form action="/webmail/wml280kn">
<logic:equal name="wml280knForm" property="wmlCmdMode" value="<%= cmdModeAdd %>">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:equal name="wml280knForm" property="wmlCmdMode" value="<%= cmdModeEdit %>">
 <input type="hidden" name="helpPrm" value="2">
</logic:equal>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden property="wmlCmdMode" />

<html:hidden property="wmlViewAccount" />
<html:hidden property="wmlAccountMode" />
<html:hidden property="wmlAccountSid" />
<html:hidden property="wmlEditDestList" />
<html:hidden property="wml270keyword" />
<html:hidden property="wml270pageTop" />
<html:hidden property="wml270pageBottom" />
<html:hidden property="wml270pageDspFlg" />
<html:hidden property="wml270svKeyword" />
<html:hidden property="wml270sortKey" />
<html:hidden property="wml270order" />
<html:hidden property="wml270searchFlg" />
<html:hidden property="wml280initFlg" />
<html:hidden property="wml280name" />
<html:hidden property="wml280biko" />
<html:hidden property="wml280knMode" />

<logic:notEmpty name="wml280knForm" property="wml280destUser">
<logic:iterate id="destUser" name="wml280knForm" property="wml280destUser">
  <input type="hidden" name="wml280destUser" value="<bean:write name="destUser" />">
</logic:iterate>
</logic:notEmpty>
</span>

<logic:notEmpty name="wml280knForm" property="wml280destAddress">
<logic:iterate id="destAddress" name="wml280knForm" property="wml280destAddress">
  <input type="hidden" name="wml280destAddress" value="<bean:write name="destAddress" />">
</logic:iterate>
</logic:notEmpty>

<logic:iterate id="selectDestList" name="wml280knForm" property="wml280accessFull">
  <input type="hidden" name="wml280accessFull" value="<bean:write name="selectDestList" />">
</logic:iterate>

<logic:notEmpty name="wml280knForm" property="wml280accessRead">
<logic:iterate id="selectDestList" name="wml280knForm" property="wml280accessRead">
  <input type="hidden" name="wml280accessRead" value="<bean:write name="selectDestList" />">
</logic:iterate>
</logic:notEmpty>

<bean:define id="wmlCmdMode" name="wml280knForm" property="wmlCmdMode" type="java.lang.Integer" />
<bean:define id="acctMode" name="wml280knForm" property="wmlAccountMode" type="java.lang.Integer" />
<bean:define id="wml280knViewMode" name="wml280knForm" property="wml280knMode" type="java.lang.Integer" />
<% int cmdMode = wmlCmdMode.intValue(); %>
<% int accountMode = acctMode.intValue(); %>
<% int viewMode = wml280knViewMode.intValue(); %>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
  <% if (accountMode == 1) { %>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
  <% } else { %>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
  <% } %>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><% if (viewMode == 1) { %><gsmsg:write key="wml.wml280kn.03" /><% } else if (cmdMode == 0) { %><gsmsg:write key="wml.wml280kn.01" /><% } else if (cmdMode == 1) { %><gsmsg:write key="wml.wml280kn.02" /><% } %>
    </li>

    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backInput');">
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
    <html:errors/>
  </logic:messagesPresent>

  <table id="wml_settings" class="table-left w100 mt0">
    <tr>
      <th class="w20 no_w"><gsmsg:write key="wml.263" /></th>
      <td class="w80">
        <bean:write name="wml280knForm" property="wml280name" />
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="cmn.memo" /></th>
      <td>
        <bean:write name="wml280knForm" property="wml280knBiko" filter="false" />
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="anp.send.dest" /></th>
      <td>
        <table class="table-top">
          <tr>
            <th class="w45"><gsmsg:write key="project.68" /></th>
            <th class="w55"><gsmsg:write key="cmn.mailaddress" /></th>
          </tr>

          <logic:notEmpty name="wml280knForm" property="destUserList">
            <logic:iterate id="destUserData" name="wml280knForm" property="destUserList">
              <tr>
                <td class="bgC_tableCell">
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal name="destUserData" property="usrUkoFlg" value="1">
                    <bean:define id="mukoUserClass" value="mukoUser" />
                  </logic:equal>
                  <span class="<%=mukoUserClass%>"><bean:write name="destUserData" property="name" /></span>
                  <logic:notEmpty name="destUserData" property="acoName">
                    <div class="mt10">
                      <span class="ml5"><gsmsg:write key="address.139" />: <bean:write name="destUserData" property="acoName" /></span>
                      <logic:notEmpty name="destUserData" property="abaName">
                        <div class="ml5">
                          <gsmsg:write key="address.10" />: <bean:write name="destUserData" property="abaName" />
                        </div>
                      </logic:notEmpty>
                    </div>
                  </logic:notEmpty>
                </td>
                <td class="txt_m bgC_tableCell">
                  <bean:write name="destUserData" property="mailAddress" />
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </table>
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="fil.102" /></th>
      <td>
        <table class="table-left w60">
          <logic:notEmpty name="wml280knForm" property="wml280accessFullSelectCombo">
            <tr>
              <th class="w40"><gsmsg:write key="cmn.add.edit.delete" /></th>
            </tr>
            <tr>
              <td>
                <logic:iterate id="user" name="wml280knForm" property="wml280accessFullSelectCombo" scope="request">
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal name="user" property="usrUkoFlg" value="1">
                    <bean:define id="mukoUserClass" value="mukoUser" />
                  </logic:equal>
                  <span class="<%=mukoUserClass%>"><bean:write name="user" property="label" /></span><br>
                </logic:iterate>
              </td>
            </tr>
          </logic:notEmpty>

          <logic:notEmpty name="wml280knForm" property="wml280accessReadSelectCombo" scope="request">
            <tr>
              <th class="w40"><gsmsg:write key="cmn.reading" /></th>
            </tr>
            <tr>
              <td>
                <logic:iterate id="user" name="wml280knForm" property="wml280accessReadSelectCombo" scope="request">
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal name="user" property="usrUkoFlg" value="1">
                    <bean:define id="mukoUserClass" value="mukoUser" />
                  </logic:equal>
                  <span class="<%=mukoUserClass%>"><bean:write name="user" property="label" /></span><br>
                </logic:iterate>
              </td>
            </tr>
          </logic:notEmpty>

        </table>

      </td>
    </tr>

  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn js_btn_ok1" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backInput');">
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