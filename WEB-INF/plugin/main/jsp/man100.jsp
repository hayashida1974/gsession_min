<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%
  int add = jp.groupsession.v2.man.man100.Man100Biz.MODE_ADD;
  int edit = jp.groupsession.v2.man.man100.Man100Biz.MODE_EDIT;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man002.26" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script type="text/javascript" src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man100.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>
<body class="body_03">
<html:form action="/main/man100">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="man100ProcMode" value="">
<input type="hidden" name="man100EditPosSid" value="">
<logic:notEmpty name="man100Form" property="posList" scope="request">
  <logic:iterate id="sort" name="man100Form" property="posList" scope="request">
    <input type="hidden" name="man100KeyList" value="<bean:write name="sort" property="posValue" />">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--BODY -->

<div class="kanriPageTitle w90 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man002.26" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onclick="buttonPush('posImport');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonSubmit('add', '<%= add %>', '-1');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('admin_back');">
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
  <div class="txt_l">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('up');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('down');">
      <gsmsg:write key="cmn.down" />
    </button>
    <span class="ml10"><gsmsg:write key="main.man100.1" /></span>
  </div>
  <table class="table-top w100">
    <tr>
      <th class="table_title-color">
      </th>
      <th class="w10 table_title-color">
        <gsmsg:write key="user.src.50" />
      </th>
      <th class="w30 table_title-color">
        <gsmsg:write key="cmn.job.title" />
      </th>
      <th class="w60 table_title-color">
        <gsmsg:write key="cmn.memo" />
      </th>
    </tr>
    <logic:notEmpty name="man100Form" property="posList" scope="request">
      <logic:iterate id="posMdl" name="man100Form" property="posList" scope="request" indexId="idx">
        <tr class="js_listHover cursor_p" data-edit="<%= edit %>" data-sid="<bean:write name="posMdl" property="posSid" />">
          <td class="txt_c js_sord_radio">
            <bean:define id="posValue" name="posMdl" property="posValue" />
            <html:radio property="man100SortRadio" value="<%= String.valueOf(posValue) %>" />
          </td>
          <td class="txt_l js_listClick">
            <bean:write name="posMdl" property="posCode"/>
          </td>
          <td class="txt_l js_listClick">
            <span class="cl_linkDef"><bean:write name="posMdl" property="posName" /></span>
          </td>
          <td class="txt_l js_listClick">
            <bean:write name="posMdl" property="posBiko" filter="false" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>