<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<%-- <title>GROUPSESSION <bean:write name="cmn131knForm" property="cmn131dspTitle" /><gsmsg:write key="cmn.cmn131kn.1" /></title> --%>
<title>GROUPSESSION
<logic:equal name="cmn132Form" property="cmn132MyGroupKbn" value="<%= String.valueOf(1) %>">
  <gsmsg:write key="cmn.cmn130.2" /><gsmsg:write key="cmn.check" />
</logic:equal>
<logic:equal name="cmn132Form" property="cmn132MyGroupKbn" value="<%= String.valueOf(0) %>">
  <gsmsg:write key="cmn.mygroup" /><gsmsg:write key="cmn.check" />
</logic:equal>
</title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/submit.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body class="FreezePane">
<html:form action="/common/cmn132" onsubmit="return onControlSubmit();">

<input type="hidden" name="CMD" value="touroku">

<!-- BODY -->

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />">
      <img class="header_pluginImg" src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />">
    </li>
    <li>
      <gsmsg:write key="cmn.preferences2" />
    </li>
    <li class="pageTitle_subFont">
      <logic:equal name="cmn132Form" property="cmn132MyGroupKbn" value="<%= String.valueOf(1) %>">
        <gsmsg:write key="cmn.cmn130.2" /><gsmsg:write key="cmn.check" />
      </logic:equal>
      <logic:equal name="cmn132Form" property="cmn132MyGroupKbn" value="<%= String.valueOf(0) %>">
        <gsmsg:write key="cmn.mygroup" /><gsmsg:write key="cmn.check" />
      </logic:equal>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.cmn130.1" />
      </th>
      <td class="w75">
        <bean:write name="cmn132Form" property="cmn131name" />
      </td>
    </tr>
    <logic:equal name="cmn132Form" property="cmn132MyGroupKbn" value="<%= String.valueOf(1) %>">
      <tr>
        <th class="w25">
          <gsmsg:write key="cmn.registant" />
        </th>
        <td class="w75">
          <logic:equal name="cmn132Form" property="cmn132owner.usrUkoFlg" value="1">
            <div class="mukoUserOption">
              <bean:write name="cmn132Form" property="cmn132owner.usiSei" />&nbsp<bean:write name="cmn132Form" property="cmn132owner.usiMei" />
            </div>
          </logic:equal>
          <logic:notEqual name="cmn132Form" property="cmn132owner.usrUkoFlg" value="1">
            <bean:write name="cmn132Form" property="cmn132owner.usiSei" />&nbsp<bean:write name="cmn132Form" property="cmn132owner.usiMei" />
          </logic:notEqual>
        </td>
      </tr>
    </logic:equal>
    <tr>
      <th class="w25">
        <gsmsg:write key="cir.11" />
      </th>
      <td class="w75 word_b-all">
        <bean:write name="cmn132Form" property="cmn131memo" filter="false"/>
      </td>
    </tr>
  </table>
  <logic:notEmpty name="cmn132Form" property="cmn131knMemberList" scope="request">
    <div class="mt10 txt_l fw_b">
      <gsmsg:write key="cmn.member" />
    </div>
    <table class="table-top mt0">
      <tr>
        <th class="w30">
          <gsmsg:write key="cmn.name" />
        </th>
        <th class="w30">
          <gsmsg:write key="cmn.post" />
        </th>
        <th class="w40">
          <gsmsg:write key="cmn.affiliation.group" />
        </th>
      </tr>
      <logic:iterate id="memMdl" name="cmn132Form" property="cmn131knMemberList" scope="request">
        <tr>
          <td class="w30">
            <logic:equal name="memMdl" property="usrUkoFlg" value="1">
              <div class="mukoUserOption">
                <bean:write name="memMdl" property="usiSei" />&nbsp;<bean:write name="memMdl" property="usiMei" />
              </div>
            </logic:equal>
            <logic:notEqual name="memMdl" property="usrUkoFlg" value="1">
              <div>
                <bean:write name="memMdl" property="usiSei" />&nbsp;<bean:write name="memMdl" property="usiMei" />
              </div>
            </logic:notEqual>
          </td>
          <td class="w30">
            <logic:equal name="memMdl" property="usrUkoFlg" value="1">
              <div class="mukoUserOption">
                <bean:write name="memMdl" property="usiYakusyoku" />
              </div>
            </logic:equal>
            <logic:notEqual name="memMdl" property="usrUkoFlg" value="1">
              <div>
                <bean:write name="memMdl" property="usiYakusyoku" />
              </div>
            </logic:notEqual>
          </td>
          <td class="w40">
            <logic:notEmpty name="memMdl" property="belongGrpList">
              <logic:iterate id="grp" name="memMdl" property="belongGrpList">
                <logic:equal name="memMdl" property="usrUkoFlg" value="1">
                  <div class="mukoUserOption">
                    <bean:write name="grp" property="groupName"/>
                  </div>
                </logic:equal>
                <logic:notEqual name="memMdl" property="usrUkoFlg" value="1">
                  <div>
                    <bean:write name="grp" property="groupName"/>
                  </div>
                </logic:notEqual>
              </logic:iterate>
            </logic:notEmpty>
          </td>
        </tr>
      </logic:iterate>
    </table>
  </logic:notEmpty>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>
</div>

</html:form>
</body>
</html:html>