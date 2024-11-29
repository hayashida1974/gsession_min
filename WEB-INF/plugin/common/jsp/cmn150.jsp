<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

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
<title>GROUPSESSION <gsmsg:write key="cmn.setting.main.view2" /></title>
</head>

<body>
<html:form action="/common/cmn150">
<input type="hidden" name="CMD" value="">

<html:hidden property="cmn150backPage" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.preferences2" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.setting.main.view2" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush('cmn150kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('cmn150back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto mt20">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.cmn150.1" />
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio name="cmn150Form" styleId="cmn150Dsp1_01" property="cmn150Dsp1" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_DSP) %>" /><label for="cmn150Dsp1_01"><gsmsg:write key="cmn.show" /></label>
          <html:radio name="cmn150Form" styleId="cmn150Dsp1_02" styleClass="ml10" property="cmn150Dsp1" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_NOT_DSP) %>" /><label for="cmn150Dsp1_02"><gsmsg:write key="cmn.hide" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="user.usr090.2" />
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio name="cmn150Form" styleId="cmn150Dsp2_01" property="cmn150Dsp2" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_DSP) %>" /><label for="cmn150Dsp2_01"><gsmsg:write key="cmn.show" /></label>
          <html:radio name="cmn150Form" styleId="cmn150Dsp2_02" styleClass="ml10" property="cmn150Dsp2" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_NOT_DSP) %>" /><label for="cmn150Dsp2_02"><gsmsg:write key="cmn.hide" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.auto.reload.time" />
      </th>
      <td class="w75">
        <html:select name="cmn150Form" property="cmn150Dsp3">
          <html:optionsCollection name="cmn150Form" property="cmn150DspLabelList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="main.src.man001.1" />
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio name="cmn150Form" styleId="cmn150Dsp4_01" property="cmn150Dsp4" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_DSP) %>" /><label for="cmn150Dsp4_01"><gsmsg:write key="cmn.show" /></label>
          <html:radio name="cmn150Form" styleId="cmn150Dsp4_02" styleClass="ml10" property="cmn150Dsp4" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_NOT_DSP) %>" /><label for="cmn150Dsp4_02"><gsmsg:write key="cmn.hide" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.cmn150.2" />
      </th>
      <td class="w75">
        <ui:sortingselector name="cmn150Form" property="cmn150WeatherUI" styleClass="hp200"
        onchange="" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.news" />
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio name="cmn150Form" styleId="cmn150Dsp6_01" property="cmn150Dsp6" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_DSP) %>" /><label for="cmn150Dsp6_01"><gsmsg:write key="cmn.show" /></label>
          <html:radio name="cmn150Form" styleId="cmn150Dsp6_02" styleClass="ml10" property="cmn150Dsp6" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_NOT_DSP) %>" /><label for="cmn150Dsp6_02"><gsmsg:write key="cmn.hide" /></label>
        </div>
      </td>
    </tr>
  </table>
  <logic:notEqual name="cmn150Form" property="cmn150MainStatus" value="0">
    <html:hidden name="cmn150Form" property="cmn150Dsp4" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_DSP) %>" />
    <html:hidden name="cmn150Form" property="cmn150Dsp6" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAIN_DSP) %>" />
  </logic:notEqual>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush('cmn150kakunin');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('cmn150back');">
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