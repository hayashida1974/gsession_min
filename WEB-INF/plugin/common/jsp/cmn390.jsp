<%@page import="jp.groupsession.v2.cmn.cmn390.Cmn390Biz"%>
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
  <title>GROUPSESSION <gsmsg:write key="cmn.preferences2" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src="../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>"></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
  <script src="../common/js/cmn390.js?<%=GSConst.VERSION_PARAM%>"></script>

  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
  <theme:css filename="theme.css"/>
</head>
<body>

<html:form action="/common/cmn390">

<input type="hidden" name="CMD" value="">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.cmn390.01" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('cmn390Commit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('cmn390Back');">
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

  <table class="table-left mt0">
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.cmn390.03" />
      </th>
      <td class="w75">
        <label class="verAlignMid mr10">
          <html:radio styleClass="js_ipAddrSeigenUseFlg" name="cmn390Form" property="cmn390ipAddrSeigenUseFlg" value="<%=String.valueOf(Cmn390Biz.FLG_NEGATIVE)%>"></html:radio>
          <gsmsg:write key="cmn.cmn390.09"/>
        </label>
        <label class="verAlignMid mr10">
          <html:radio styleClass="js_ipAddrSeigenUseFlg" name="cmn390Form" property="cmn390ipAddrSeigenUseFlg" value="<%=String.valueOf(Cmn390Biz.FLG_POSITIVE)%>"></html:radio>
          <gsmsg:write key="cmn.cmn390.10"/>
        </label>
      </td>
    </tr>
    <bean:define id="dispUse" value="display_none" />
    <logic:equal name="cmn390Form" property="cmn390ipAddrSeigenUseFlg" value="<%=String.valueOf(Cmn390Biz.FLG_POSITIVE)%>">
      <bean:define id="dispUse" value="" />
    </logic:equal>
    <tr class="js_seigenSubParamRow <%=dispUse%>">
      <th class="w25 no_w">
        <gsmsg:write key="cmn.cmn390.04" />
      </th>
      <td class="w75">
        <html:textarea name="cmn390Form" property="cmn390arrowIpAddrText" styleClass="w100" rows="6" styleId="inputstr"  />
        <span class="display_none js_txtmaxlength"><%=String.valueOf(Cmn390Biz.MAXLENGTH_IPADDR)%></span>
        <br>
        <div class="cl_fontWarn  mb10">
          <gsmsg:write key="main.man430.7" /><br>
          <gsmsg:write key="cmn.cmn390.14" /><br>
          <gsmsg:write key="main.man430.8" /><br>
          <gsmsg:write key="cmn.cmn390.11" />
          <div class="ml20">
            <gsmsg:write key="cmn.cmn390.12" /><br>
            <gsmsg:write key="cmn.cmn390.13" />
          </div>
        </div>
      </td>
    </tr>
    <tr class="js_seigenSubParamRow <%=dispUse%>">
      <th class="w25 no_w">
        <gsmsg:write key="cmn.cmn390.05" />
      </th>
      <td class="w75">
        <div>
          <label class="verAlignMid">
            <html:checkbox name="cmn390Form" property="cmn390arrowAnpFlg" value="<%=String.valueOf(Cmn390Biz.FLG_POSITIVE)%>"></html:checkbox>
            <gsmsg:write key="cmn.cmn390.07"/>
          </label>
        </div>
        <div class="mt5 pt5 bor_t1 borC_light w70">
          <label class="verAlignMid">
            <html:checkbox name="cmn390Form" property="cmn390arrowMblFlg" value="<%=String.valueOf(Cmn390Biz.FLG_POSITIVE)%>" styleClass="js_mobilePermitFlg"></html:checkbox>
            <gsmsg:write key="cmn.cmn390.06"/>
          </label>
        </div>
        <div class="js_mobilePermissionArea ml20">
          <span class="verAlignMid">
            <gsmsg:write key="cmn.cmn390.15"/>
            <div class="mt5">
              <label class="verAlignMid ml5">
                <html:radio name="cmn390Form" property="cmn390mobilePermissionKbn" value="<%=String.valueOf(Cmn390Biz.FLG_NEGATIVE)%>" styleClass="js_mobilePermitPluginKbn"></html:radio>
                <gsmsg:write key="cmn.not.limit"/>
              </label>
              <label class="verAlignMid ml10">
                <html:radio name="cmn390Form" property="cmn390mobilePermissionKbn" value="<%=String.valueOf(Cmn390Biz.FLG_POSITIVE)%>" styleClass="js_mobilePermitPluginKbn"></html:radio>
                <gsmsg:write key="cmn.do.limit"/>
              </label>
            </div>
          </span>
          <div class="js_mobilePermissionPluginArea mt0">
            <ui:multiselector name="cmn390Form" property="cmn390mobilePermissionPluginUI" styleClass="hp200 mt0" onchange="" />
          </div>
      </td>
    </tr>
  </table>

  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('cmn390Commit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('cmn390Back');">
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