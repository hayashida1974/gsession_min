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
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="addressbook" /> <gsmsg:write key="cmn.setting.permissions.kn" /></title>
</head>

<body>
<html:form action="/address/adr040kn">
<input type="hidden" name="CMD" value="">

<html:hidden name="adr040knForm" property="backScreen" />
<html:hidden name="adr040knForm" property="adr040Pow1" />
<html:hidden name="adr040knForm" property="adr040Pow2" />
<html:hidden name="adr040knForm" property="adr040Pow3" />
<html:hidden name="adr040knForm" property="adr040Pow4" />
<html:hidden name="adr040knForm" property="adr040Pow5" />

<logic:notEmpty name="adr040knForm" property="adr010SearchTargetContact" scope="request">
  <logic:iterate id="target" name="adr040knForm" property="adr010SearchTargetContact" scope="request">
    <input type="hidden" name="adr010SearchTargetContact" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="adr040knForm" property="adr010svSearchTargetContact" scope="request">
  <logic:iterate id="svTarget" name="adr040knForm" property="adr010svSearchTargetContact" scope="request">
    <input type="hidden" name="adr010svSearchTargetContact" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="adr040knForm" property="adr010selectSid">
<logic:iterate id="adrSid" name="adr040knForm" property="adr010selectSid">
  <input type="hidden" name="adr010selectSid" value="<bean:write name="adrSid" />">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" %>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="addressbook" /></span><gsmsg:write key="cmn.setting.permissions.kn" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('adr040knkakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('adr040knback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="address.85" />
      </th>
      <td class="w75">
        <logic:equal name="adr040knForm" property="adr040Pow5" value="1"><gsmsg:write key="cmn.only.admin.editable" /></logic:equal>
        <logic:equal name="adr040knForm" property="adr040Pow5" value="0"><gsmsg:write key="address.87" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="address.88" />
      </th>
      <td>
        <logic:equal name="adr040knForm" property="adr040Pow1" value="1"><gsmsg:write key="cmn.only.admin.editable" /></logic:equal>
        <logic:equal name="adr040knForm" property="adr040Pow1" value="0"><gsmsg:write key="address.89" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="address.90" />
      </th>
      <td>
        <logic:equal name="adr040knForm" property="adr040Pow2" value="1"><gsmsg:write key="cmn.only.admin.editable" /></logic:equal>
        <logic:equal name="adr040knForm" property="adr040Pow2" value="0"><gsmsg:write key="address.91" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.edit.permissions.label" />
      </th>
      <td>
        <logic:equal name="adr040knForm" property="adr040Pow3" value="1"><gsmsg:write key="cmn.only.admin.editable" /></logic:equal>
        <logic:equal name="adr040knForm" property="adr040Pow3" value="0"><gsmsg:write key="cmn.noset.edit.permissions.label" /></logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="address.94" />
      </th>
      <td>
        <logic:equal name="adr040knForm" property="adr040Pow4" value="1"><gsmsg:write key="address.140" /></logic:equal>
        <logic:equal name="adr040knForm" property="adr040Pow4" value="0"><gsmsg:write key="address.95" /></logic:equal>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="return buttonPush('adr040knkakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('adr040knback');">
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