<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.man.GSConstMain" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.holiday.setting" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man024kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/main/man024kn">
<html:hidden property="man020DspYear" />
<html:hidden property="editHltSid" />
<html:hidden property="man023CheckAll" />

<input type="hidden" name="CMD" value="">

<logic:notEmpty name="man024knForm" property="man023hltSid" scope="request">
<logic:iterate id="hltBean" name="man024knForm" property="man023hltSid" scope="request">
  <input type="hidden" name="man023hltSid" value="<bean:write name="hltBean" />">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man024kn.1" />
    </li>
    <li>
      <div>
        <bean:define id="dspYear" name="man024knForm" property="man020DspYear" type="java.lang.String" />
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('reflectHoliday')" >
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backTemp')">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
<div class="txt_l cl_fontWarn">
  <gsmsg:write key="main.man024kn.2" />
</div>
<table class="table-left">
  <tr>
    <th class="w30">
      <gsmsg:write key="project.lef.holiday" /><gsmsg:write key="cmn.year2" />
    </th>
    <td class="w70">
      <gsmsg:write key="cmn.year" arg0="<%= dspYear %>" />
    </td>
  </tr>
</table>
<table class="table-top">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.date3" />
      </th>
      <th class="w75">
        <gsmsg:write key="cmn.holiday.name" />
      </th>
    </tr>
    <logic:notEmpty name="man024knForm" property="man024knTemplateList" >
    <logic:iterate id="pageTemplateRecBean" name="man024knForm" property="man024knTemplateList" indexId="idx">
    <tr>
    <logic:equal name="pageTemplateRecBean" property="dspBorderFlg" value="true">
      <td class="fw_b txt_c">
        <strike><bean:write name="pageTemplateRecBean" property="viewDate" scope="page"/></strike>
      </td>
    </logic:equal>
    <logic:equal name="pageTemplateRecBean" property="dspBorderFlg" value="false">
      <td class="fw_b txt_c">
        <logic:equal name="pageTemplateRecBean" property="asterisk" value="<%=String.valueOf(GSConstMain.HOLIDAY_TEMPLATE_ASTERISK_FLG_YES) %>"><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></logic:equal><bean:write name="pageTemplateRecBean" property="viewDate" scope="page"/>
      </td>
    </logic:equal>
      <td>
        <bean:write name="pageTemplateRecBean" property="hltName" scope="page"/>
      </td>
    </tr>
    </logic:iterate>
    </logic:notEmpty>
  </table>
  <div class="footerBtn_block">
    <bean:define id="dspYear" name="man024knForm" property="man020DspYear" type="java.lang.String" />
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('reflectHoliday')">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backTemp')">
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