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
<title>GROUPSESSION <bean:write name="cmn131knForm" property="cmn131dspTitle" /><gsmsg:write key="cmn.cmn131kn.1" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/submit.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<body class="body_03">
<html:form action="/common/cmn131kn" onsubmit="return onControlSubmit();">

<input type="hidden" name="CMD" value="touroku">
<html:hidden property="cmn130cmdMode" />
<html:hidden property="cmn130selectGroupSid" />
<html:hidden property="cmn131name" />
<html:hidden property="cmn131memo" />
<html:hidden property="cmn131initFlg" />
<input type="hidden" name="helpPrm" value="<bean:write name="cmn131Form" property="cmn130cmdMode" />">

<logic:notEmpty name="cmn131knForm" property="cmn131userSid" scope="request">
  <logic:iterate id="users" name="cmn131knForm" property="cmn131userSid" indexId="idx" scope="request">
    <bean:define id="userSid" name="users" type="java.lang.String" />
    <html:hidden property="cmn131userSid" value="<%= userSid %>" />
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="cmn131Form" property="cmn131refUserSid" scope="request">
  <logic:iterate id="refUsers" name="cmn131Form" property="cmn131refUserSid" indexId="idx" scope="request">
    <bean:define id="refUserSid" name="refUsers" type="java.lang.String" />
    <html:hidden property="cmn131refUserSid" value="<%= refUserSid %>" />
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--　BODY -->

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
      <bean:write name="cmn131knForm" property="cmn131dspTitle" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="submit" class="baseBtn" value="<gsmsg:write key="cmn.final" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToInput');">
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

  <div class="txt_l cl_fontWarn fw_b">
    <gsmsg:write key="main.man210kn.2" />
  </div>
  <table class="table-left mt0">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.cmn130.1" />
      </th>
      <td class="w75">
        <bean:write name="cmn131knForm" property="cmn131name" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.member" />
      </th>
      <td class="w75">
        <logic:iterate id="user" name="cmn131knForm" property="cmn131MbLabelList" >
          <logic:equal value="1" name="user" property="usrUkoFlg">
            <div class="mukoUserOption">
              <bean:write name="user" property="label"/>
            </div>
          </logic:equal>
          <logic:equal value="0" name="user" property="usrUkoFlg">
            <div>
              <bean:write name="user" property="label"/>
            </div>
          </logic:equal>
        </logic:iterate>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cir.11" />
      </th>
      <td class="w75 word_b-all">
        <bean:write name="cmn131knForm" property="cmn131knMemo" filter="false"/>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.share" />
      </th>
      <td class="w75">
        <logic:notEmpty name="cmn131knForm" property="cmn131refMbLabelList" scope="request">
          <logic:iterate id="refMemMdl" name="cmn131knForm" property="cmn131refMbLabelList" scope="request">
            <logic:equal value="1" name="refMemMdl" property="usrUkoFlg">
              <div class="mukoUserOption">
                <bean:write name="refMemMdl" property="label"/>
              </div>
            </logic:equal>
            <logic:equal value="0" name="refMemMdl" property="usrUkoFlg">
              <div>
                <bean:write name="refMemMdl" property="label"/>
              </div>
            </logic:equal>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="submit" class="baseBtn" value="<gsmsg:write key="cmn.final" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToInput');">
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