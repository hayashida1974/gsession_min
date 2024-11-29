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
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="main.man030.8" /></title>
<script src="../common/js/cmn130.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body class="body_03">
<html:form action="/common/cmn130">

<input type="hidden" name="CMD" value="">
<input type="hidden" name="cmn130cmdMode" value="">
<input type="hidden" name="cmn130selectGroupSid" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

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
      <gsmsg:write key="main.man030.8" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('groupAdd', '');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('groupDelete', '');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToMenu', '');">
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

  <table class="table-top">
    <tr>
      <th class="w5">
      </th>
      <th class="w25 txt_l">
        <gsmsg:write key="cmn.cmn130.1" />
      </th>
      <th class="w10 txt_l">
        <gsmsg:write key="cmn.member" />
      </th>
      <th class="w60 txt_l">
        <gsmsg:write key="cir.11" />
      </th>
    </tr>

    <logic:notEmpty name="cmn130Form" property="cmn130GroupList" scope="request">
      <logic:iterate id="grpMdl" name="cmn130Form" property="cmn130GroupList" scope="request" indexId="idx">
        <tr>
          <td class="txt_c js_tableTopCheck js_contentHover_check cursor_p">
            <html:multibox name="cmn130Form" property="cmn130delGroupSid">
              <bean:write name="grpMdl" property="mgpSid" />
            </html:multibox>
          </td>
          <td class="js_listClick_myGroup js_contentHover_groupName cursor_p textLink" id="<bean:write name="grpMdl" property="mgpSid" />">
            <bean:write name="grpMdl" property="mgpName" />
          </td>
          <td class="txt_r js_listClick_myGroupCount js_contentHover_member cursor_p textLink" id="<bean:write name="grpMdl" property="mgpSid" />">
            <bean:define id="memSize" name="grpMdl" property="memberCnt" />
            <gsmsg:write key="bmk.23" arg0="<%= String.valueOf(memSize) %>"/>
          </td>
          <td class="js_listClick word_b-all">
            <bean:write name="grpMdl" property="mgpMemo" filter="false"/>
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>

  <div class="mt50 txt_l fw_b">
    <gsmsg:write key="cmn.cmn130.2" />
  </div>
  <table class="table-top">
    <tr>
      <th class="w25 txt_l">
        <gsmsg:write key="cmn.cmn130.1" />
      </th>
      <th class="w15 txt_l">
        <gsmsg:write key="cmn.registant" />
      </th>
      <th class="w10 txt_l">
        <gsmsg:write key="cmn.member" />
      </th>
      <th class="w55 txt_l">
        <gsmsg:write key="cir.11" />
      </th>
    </tr>

    <logic:notEmpty name="cmn130Form" property="cmn130SharedGroupList" scope="request">
      <logic:iterate id="grpMdl" name="cmn130Form" property="cmn130SharedGroupList" scope="request" indexId="idx">
        <tr class="js_listHover cursor_p" id="<bean:write name="grpMdl" property="mgpSid" />">
          <td class="js_listClick_shareGroup cl_linkDef">
            <bean:write name="grpMdl" property="mgpName" />
          </td>
          <td class="js_listClick_shareGroup">
              <bean:define id="linkClass" value="" />
            <logic:notEqual  name="grpMdl" property="owner.usrUkoFlg" value="0">
              <bean:define id="linkClass" value="mukoUserOption" />
            </logic:notEqual>
            <span class="<bean:write name="linkClass" />"><bean:write name="grpMdl" property="owner.usiSei" />&nbsp<bean:write name="grpMdl" property="owner.usiMei" /></span>
          </td>
          <td class="txt_r js_listClick_shareGroup">
            <bean:define id="memSize" name="grpMdl" property="memberCnt" />
            <gsmsg:write key="bmk.23" arg0="<%= String.valueOf(memSize) %>"/>
          </td>
          <td class="js_listClick_shareGroup word_b-all">
            <bean:write name="grpMdl" property="mgpMemo" filter="false"/>
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('groupAdd', '');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('groupDelete', '');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToMenu', '');">
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