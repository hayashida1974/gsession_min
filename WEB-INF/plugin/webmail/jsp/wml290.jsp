<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.wml.wml280kn.Wml280knForm" %>
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

  <gsjsmsg:js filename="gsjsmsg.js"/>

  <script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>

  <script src="../webmail/js/assets/webmailSubWindow.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../webmail/js/wml290.js?<%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="wml.wml010.35" /></title>
</head>

<logic:empty name="wml290Form" property="wml290destAddressList">
  <body class="body_03">
</logic:empty>
<logic:notEmpty name="wml290Form" property="wml290destAddressList">
  <body class="body_03" onload="setWml290WebmailData();">
</logic:notEmpty>

<html:form action="/webmail/wml290">

<input type="hidden" name="CMD" value="">

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

<html:hidden property="wml290initFlg" />
<html:hidden property="wml290webmailAddress" />

<logic:notEmpty name="wml290Form" property="wml280destUser">
<logic:iterate id="destUser" name="wml290Form" property="wml280destUser">
  <input type="hidden" name="wml280destUser" value="<bean:write name="destUser" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="wml290Form" property="wml280destAddress">
<logic:iterate id="destAddress" name="wml290Form" property="wml280destAddress">
  <input type="hidden" name="wml280destAddress" value="<bean:write name="destAddress" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="wml290Form" property="wml290destAddressList">
  <bean:define id="webmailAddressName" name="wml290Form" property="wml290webmailAddress" type="java.lang.String" />
  <%
    String destAddressName = "wml290Atsk";
    if (webmailAddressName.equals("wml010sendAddressCc")) {
        destAddressName = "wml290Cc";
    } else if (webmailAddressName.equals("wml010sendAddressBcc")) {
        destAddressName = "wml290Bcc";
    }
  %>

  <logic:iterate id="destAddress" name="wml290Form" property="wml290destAddressList">
    <input type="hidden" name="<%= destAddressName %>" value="<bean:write name="destAddress" />">
  </logic:iterate>
</logic:notEmpty>

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../webmail/images/classic/header_webmail.png" alt="<gsmsg:write key="wml.wml010.25" />">
      <img class="header_pluginImg" src="../webmail/images/original/header_webmail.png" alt="<gsmsg:write key="wml.wml010.25" />">
    </li>
    <li><gsmsg:write key="wml.wml010.25" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="wml.wml010.35" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.apply" />" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.apply" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_apply.png" alt="<gsmsg:write key="cmn.apply" />">
          <gsmsg:write key="cmn.apply" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">

  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <table class="table-left">
    <tr>
      <th class="wp250 txt_t pt5 pb5 pl5 pr5">
        <logic:notEmpty name="wml290Form" property="wml290destlistCombo">
          <table class="table-top mt0 mb0 ml0 mr0">
            <logic:iterate id="destlistData" name="wml290Form" property="wml290destlistCombo">
              <tr class="js_listHover cursor_p" data-sid="<bean:write name="destlistData" property="value" />">
                <td class="js_listClick bgC_tableCell">
                  <span class="cl_linkDef"><bean:write name="destlistData" property="label" /></span>
                </td>
              </tr>
            </logic:iterate>
          </table>
        </logic:notEmpty>
      </th>

      <td class="wp750 txt_t">
        <logic:notEmpty name="wml290Form" property="destUserList">
          <gsmsg:write key="wml.263" />: <bean:write name="wml290Form" property="wml280name" />

          <table class="table-top w99">
            <tr>
              <th class="txt_c js_tableTopCheck js_tableTopCheck-header cursor_p"><input type="checkbox" name="wml290AllCheck" value="1" onClick="chgCheckAll('wml290AllCheck', 'wml280destUserSelect');"></th>
              <th class="w45"><gsmsg:write key="cmn.name" />/<gsmsg:write key="cmn.post" /></th>
              <th class="w45"><gsmsg:write key="cmn.company.name" />/<gsmsg:write key="address.10" /></th>
              <th class="w54"><gsmsg:write key="cmn.mailaddress" /></th>
            </tr>

            <logic:iterate id="destUserData" name="wml290Form" property="destUserList">
              <bean:define id="destListId" name="destUserData" property="destId" type="java.lang.String" />
              <bean:define id="mukoclass" value="" />
              <logic:equal name="destUserData" property="usrUkoFlg" value="1">
                <bean:define id="mukoclass" value="mukoUser" />
              </logic:equal>

              <tr class="js_listHover cursor_p" data-sid="<%= destListId %>">
                <td class="txt_c bgC_tableCell js_tableTopCheck">
                  <html:multibox name="wml290Form" property="wml280destUserSelect" styleId="<%= destListId %>">
                    <%= destListId %>
                  </html:multibox>
                </td>
                <td class="bgC_tableCell js_listClick_Detail">
                  <span class="<%=mukoclass%>">
                    <logic:notEmpty name="destUserData" property="yakusyoku">
                      <bean:write name="destUserData" property="yakusyoku" /><br>
                    </logic:notEmpty>
                    <bean:write name="destUserData" property="name" />
                  </span>
                </td>

                <td class="bgC_tableCell js_listClick_Detail">
                  <span class="ml5"><bean:write name="destUserData" property="acoName" /></span>
                  <logic:notEmpty name="destUserData" property="abaName">
                    <div class="tl15"><bean:write name="destUserData" property="abaName" /></div>
                  </logic:notEmpty>
                </td>
                <td class="bgC_tableCell js_listClick_Detail">
                  <bean:write name="destUserData" property="mailAddress" />
                </td>
              </tr>
            </logic:iterate>
          </table>
        </logic:notEmpty>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.apply" />" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.apply" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_apply.png" alt="<gsmsg:write key="cmn.apply" />">
      <gsmsg:write key="cmn.apply" />
    </button>
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