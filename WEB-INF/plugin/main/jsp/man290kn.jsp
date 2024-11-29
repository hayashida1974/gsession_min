<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% String maxLengthContent = String.valueOf(jp.groupsession.v2.man.GSConstMain.MAX_LENGTH_VALUE); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man290kn.1" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man290kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/components/file-preview/index.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body>
<html:form action="/main/man290kn">

<html:hidden property="cmd" />
<html:hidden property="man320OrderKey" />
<html:hidden property="man320SortKey" />
<html:hidden property="man320FormAdminConfBtn" />
<html:hidden property="man320SltPage1" />
<html:hidden property="man320SltPage2" />
<html:hidden property="man320PageNum" />
<html:hidden property="man320SelectedSid" />

<html:hidden property="man290ExtKbn" />
<html:hidden property="man290Week" />
<html:hidden property="man290Day" />
<html:hidden property="man290FrYear" />
<html:hidden property="man290FrMonth" />
<html:hidden property="man290FrDay" />
<html:hidden property="man290FrHour" />
<html:hidden property="man290FrMin" />
<html:hidden property="man290ToYear" />
<html:hidden property="man290ToMonth" />
<html:hidden property="man290ToDay" />
<html:hidden property="man290ToHour" />
<html:hidden property="man290ToMin" />
<html:hidden property="man290Msg" />
<html:hidden property="man290Value" />
<html:hidden property="man290Jtkbn" />
<html:hidden property="man290groupSid" />
<html:hidden property="man290elementKbn" />
<html:hidden property="man290HolKbn" />
<html:hidden name="man290knForm" property="man290knTmpFileId" />

<logic:notEmpty name="man290knForm" property="man290memberSid">
  <logic:iterate id="usid" name="man290knForm" property="man290memberSid">
    <input type="hidden" name="man290memberSid" value="<bean:write name="usid" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="man290knForm" property="man290Dweek" scope="request">
  <logic:iterate id="selectWeek" name="man290knForm" property="man290Dweek" scope="request">
    <input type="hidden" name="man290Dweek" value="<bean:write name="selectWeek" />">
  </logic:iterate>
</logic:notEmpty>


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<input type="hidden" name="helpPrm" value="<bean:write name="man290knForm" property="man290helpMode" />">
<!--BODY -->
<div class="pageTitle w80 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="<gsmsg:write key="cmn.information" />">
      <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="<gsmsg:write key="cmn.information" />">
    </li>
    <li><gsmsg:write key="cmn.information" /></li>
    <li class="pageTitle_subFont">
      <logic:equal name="man290knForm" property="cmd" value="add">
        <gsmsg:write key="cmn.entry" /><gsmsg:write key="cmn.check" />
      </logic:equal>
      <logic:equal name="man290knForm" property="cmd" value="edit">
        <gsmsg:write key="cmn.change" /><gsmsg:write key="cmn.check" />
      </logic:equal>
    </li>
    <li>
      <div>
        <logic:equal name="man290knForm" property="cmd" value="add">
          <input type="hidden" name="CMD" value="290kn_ok">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onclick="buttonPush2('290kn_commit', '<bean:write name="man290knForm" property="cmd" />')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <gsmsg:write key="cmn.final" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('290kn_back', '<bean:write name="man290knForm" property="cmd" />');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>
        <logic:equal name="man290knForm" property="cmd" value="edit">
          <input type="hidden" name="CMD" value="290kn_ok">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onclick="buttonPush2('290kn_commit', '<bean:write name="man290knForm" property="cmd" />')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
            <gsmsg:write key="cmn.final" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('290kn_back', '<bean:write name="man290knForm" property="cmd" />');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>
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
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w" colspan="2">
        <gsmsg:write key="main.man290.2" />
      </th>
      <td class="w80">
        <div>
          <bean:write name="man290knForm" property="man290knKoukaiString" />
        </div>
        <logic:notEmpty name="man290knForm" property="man290knDateList" scope="request">
          <logic:iterate id="aftDate" name="man290knForm" property="man290knDateList" scope="request">
            <div class="ml10">
              <bean:write name="aftDate" scope="page"/>
            </div>
          </logic:iterate>
        </logic:notEmpty>
        <div class="cl_fontWarn mt5">
          <bean:write name="man290knForm" property="man290knSyukuString" />
        </div>
      </td>
    </tr>
    <tr>
      <th class="w15 no_w" rowspan="2">
        <gsmsg:write key="cmn.period" />
      </th>
      <th class="w5 no_w">
        <gsmsg:write key="cmn.start" />
      </th>
      <td class="w80">
        <bean:write name="man290knForm" property="man290knFrDate" />
      </td>
    </tr>
    <tr>
      <th class="w5 no_w">
        <gsmsg:write key="cmn.end" />
      </th>
      <td class="w80">
        <bean:write name="man290knForm" property="man290knToDate" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w" colspan="2">
        <gsmsg:write key="cmn.message" />
      </th>
      <td class="w80">
        <bean:write name="man290knForm" property="man290knMsg" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w" colspan="2">
        <gsmsg:write key="cmn.content2" />
      </th>
      <td class="w80">
        <bean:write name="man290knForm" property="man290knValue" filter="false" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w" colspan="2">
        <gsmsg:write key="main.exposed" />
      </th>
      <td class="w80">
        <logic:notEmpty name="man290knForm" property="man290knKoukaiList">
          <logic:iterate id="memName" name="man290knForm" property="man290knKoukaiList" type="UsrLabelValueBean">
            <div>
              <span class="<%=memName.getCSSClassNameNormal()%>"><bean:write name="memName" property="label" /></span>
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w" colspan="2">
        <gsmsg:write key="cmn.attached" />
      </th>
      <td class="w80">
        <logic:empty name="man290knForm" property="man290FileLabelList" scope="request">&nbsp;</logic:empty>
        <logic:notEmpty name="man290knForm" property="man290FileLabelList" scope="request">
          <logic:iterate id="fileMdl" name="man290knForm" property="man290FileLabelList" scope="request">
            <div>
              <file-preview url="../main/man290kn.do?CMD=fileDownload&man290knTmpFileId=<bean:write name="fileMdl" property="value" />" filename="<bean:write name="fileMdl" property="label" />">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.file" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.file" />">
                <a href="#!" onClick="return fileLinkClick('<bean:write name="fileMdl" property="value" />');">
                  <span class="textLink"><bean:write name="fileMdl" property="label" /></span>
                </a>
              </file-preview>
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <logic:equal name="man290knForm" property="cmd" value="add">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onclick="buttonPush2('290kn_commit', '<bean:write name="man290knForm" property="cmd" />')">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
        <gsmsg:write key="cmn.final" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('290kn_back', '<bean:write name="man290knForm" property="cmd" />');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:equal>
    <logic:equal name="man290knForm" property="cmd" value="edit">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onclick="buttonPush2('290kn_commit', '<bean:write name="man290knForm" property="cmd" />')">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
        <gsmsg:write key="cmn.final" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('290kn_back', '<bean:write name="man290knForm" property="cmd" />');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </logic:equal>
  </div>
</div>

<IFRAME type="hidden" src="../common/html/damy.html" style="display: none" name="navframe"></IFRAME>
</html:form>

<span id="damy"></span>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>