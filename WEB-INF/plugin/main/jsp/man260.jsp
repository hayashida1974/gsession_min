<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>

<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">

<title>GROUPSESSION <gsmsg:write key="main.man260.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man260.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="changeEnableDisable();">
<html:form action="/main/man260">

<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man260.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('man260_opLogKn');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKtool');">
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
        <gsmsg:write key="cmn.autodelete" />
      </th>
      <td class="w75">
        <gsmsg:write key="main.man260.2" /><br>
        <bean:define id="man260BachTime" name="man260Form" property="man260BatchFrHour" type="java.lang.String" />
        <div class="cl_fontWarn mb10"><gsmsg:write key="cmn.automatic.performed.time" arg0="<%= man260BachTime %>" /></div>
        <div>
          <sppan class="verAlignMid">
            <html:radio name="man260Form" property="man260BatchKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OPE_LOG_NOTCONF) %>" styleId="man260JdelKbn_0" onclick="setDispState(this.form.man260BatchKbn, this.form.man260Year, this.form.man260Month)" /><label for="man260JdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
          </span>
        </div>
        <div>
          <span class="verAlignMid">
            <html:radio name="man260Form" property="man260BatchKbn" value="<%= String.valueOf(jp.groupsession.v2.man.GSConstMain.OPE_LOG_CONF) %>" styleId="man260JdelKbn_1" onclick="setDispState(this.form.man260BatchKbn, this.form.man260Year, this.form.man260Month)" /><label for="man260JdelKbn_1" class="mr20"><gsmsg:write key="cmn.automatically.delete" /></label>
    <gsmsg:write key="cmn.after.data.head" />

    <logic:notEmpty name="man260Form" property="yearLabel">
      <html:select property="man260Year" styleClass="wp100 mr5">
        <html:optionsCollection name="man260Form" property="yearLabel" value="value" label="label" />
      </html:select>
    </logic:notEmpty>

    <logic:notEmpty name="man260Form" property="monthLabel">
      <html:select property="man260Month" styleClass="wp100 mr5">
        <html:optionsCollection name="man260Form" property="monthLabel" value="value" label="label" />
      </html:select>
       <gsmsg:write key="cmn.after.data" />
    </logic:notEmpty>
          </span>
        </div>

      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('man260_opLogKn');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backKtool');">
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