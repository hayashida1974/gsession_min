<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.wml.wml280.Wml280Form" %>
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
  <link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml280.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml280.01" /></title>
</head>

<html:form action="/webmail/wml280">
<logic:equal name="wml280Form" property="wmlCmdMode" value="<%= cmdModeAdd %>">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>
<logic:equal name="wml280Form" property="wmlCmdMode" value="<%= cmdModeEdit %>">
 <input type="hidden" name="helpPrm" value="2">
</logic:equal>

<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<%@ include file="/WEB-INF/plugin/webmail/jsp/wml010_hiddenParams.jsp" %>
<html:hidden property="wmlCmdMode" />
<bean:define id="wmlCmdMode" name="wml280Form" property="wmlCmdMode" type="java.lang.Integer" />
<bean:define id="acctMode" name="wml280Form" property="wmlAccountMode" type="java.lang.Integer" />
<% int cmdMode = wmlCmdMode.intValue(); %>
<% int accountMode = acctMode.intValue(); %>

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

<span id="wml280destUserArea">
<logic:notEmpty name="wml280Form" property="wml280destUser">
<logic:iterate id="destUser" name="wml280Form" property="wml280destUser">
  <input type="hidden" name="wml280destUser" value="<bean:write name="destUser" />">
</logic:iterate>
</logic:notEmpty>
</span>

<span id="wml280destAddressArea">
<logic:notEmpty name="wml280Form" property="wml280destAddress">
<logic:iterate id="destAddress" name="wml280Form" property="wml280destAddress">
  <input type="hidden" name="wml280destAddress" value="<bean:write name="destAddress" />">
</logic:iterate>
</logic:notEmpty>
</span>

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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span><% if (cmdMode == 0) { %><gsmsg:write key="wml.wml280.01" /><% } else if (cmdMode == 1) { %><gsmsg:write key="wml.wml280.02" /><% } %>
    </li>

    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="wml280Form" property="wmlCmdMode" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteDestList');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
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
      <th class="w20 no_w"><gsmsg:write key="wml.263" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
      <td class="w80">
        <html:text name="wml280Form" property="wml280name" maxlength="100" styleClass="wp550" />
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="cmn.memo" /></th>
      <td>
        <html:textarea name="wml280Form" property="wml280biko" rows="10" styleClass="wp550" />
      </td>
    </tr>

    <tr>
      <th class="no_w"><gsmsg:write key="anp.send.dest" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
      <td>

        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="openSyainPlus('wml280user');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteDestUser');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>

        <table class="table-top">
          <tr>
            <th></th>
            <th class="w45"><gsmsg:write key="cmn.name" /></th>
            <th class="w55"><gsmsg:write key="cmn.mailaddress" /></th>
          </tr>

          <logic:notEmpty name="wml280Form" property="destUserList">
            <logic:iterate id="destUserData" name="wml280Form" property="destUserList">
              <bean:define id="destListId" name="destUserData" property="destId" type="java.lang.String" />
              <tr class="js_listHover cursor_p" data-sid="<%= destListId %>">
                <td class="txt_c js_tableTopCheck bgC_tableCell">
                  <html:multibox name="wml280Form" property="wml280destUserSelect" styleId="<%= destListId %>">
                    <bean:write name="destUserData" property="destId" />
                  </html:multibox>
                </td>
                <td class="js_listClick bgC_tableCell">
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal name="destUserData" property="usrUkoFlg" value="1">
                    <bean:define id="mukoUserClass" value="mukoUser" />
                  </logic:equal>
                  <span class="<%=mukoUserClass%>"><bean:write name="destUserData" property="name" /></span>
                  <logic:notEmpty name="destUserData" property="acoName">
                    <div class="mt10">
                      <span class="ml5"><gsmsg:write key="address.139" />: <bean:write name="destUserData" property="acoName" /></span>
                      <logic:notEmpty name="destUserData" property="abaName">
                        <div class="ml5"><gsmsg:write key="address.10" />: <bean:write name="destUserData" property="abaName" /></div>
                      </logic:notEmpty>
                    </div>
                  </logic:notEmpty>
                </td>
                <td class="js_listClick bgC_tableCell">
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
        <ui:usrgrpselector name="wml280Form" property="wml280accessUI" styleClass="hp300" />
      </td>
    </tr>

  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="wml280Form" property="wmlCmdMode" value="1">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteDestList');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
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