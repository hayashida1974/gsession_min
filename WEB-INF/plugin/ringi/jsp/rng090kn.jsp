<%@page import="java.util.Map.Entry"%>
<%@page import="jp.co.sjts.util.json.JSONObject"%>
<%@page import="jp.groupsession.v2.rng.RngConst"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>
<%@ taglib tagdir="/WEB-INF/tags/formbuilder" prefix="fb" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% int tmodeAll = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_ALL; %>
<% int tmodeShare = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_SHARE; %>
<% int tmodePrivate = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_PRIVATE; %>
<% int tCmdAdd = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_ADD; %>
<% int tCmdEdit = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_EDIT; %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../ringi/css/ringi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../ringi/js/rng090kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/glayer.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery.ui.touch-punch0.2.3.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/components/file-preview/index.js?<%= GSConst.VERSION_PARAM %>"></script>


<title>GROUPSESSION <gsmsg:write key="rng.58" /></title>
</head>
<body>

<html:form action="ringi/rng090kn">
<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>

<html:hidden property="rngTemplateMode" />

<html:hidden property="backScreen" />
<html:hidden property="rng010TransitionFlg" />

<html:hidden property="rngTplCmdMode" />
<html:hidden property="rngSelectTplSid" />

<html:hidden property="rng090title" />
<html:hidden property="rng090rngTitle" />
<html:hidden property="rng090content" />
<html:hidden property="rng090biko" />
<html:hidden name="rng090knForm" property="rng090knTmpFileId" />
<html:hidden name="rng090knForm" property="rng090useKeiroTemplate" />
<html:hidden name="rng090knForm" property="rng090KeiroTemplateSid" />
<html:hidden name="rng090knForm" property="rng090KeiroTemplateUsrSid" />
<html:hidden property="rng090idSid" />
<html:hidden property="flgFileChange" />
<html:hidden property="accessDateString" />
<html:hidden property="rng090rtpSpecVer" />

<%-- テンプレートフォーム設定（hidden） --%>
<html:hidden property="rng090templateJSON" />
<%-- 経路設定（hidden） --%>
<ringi:rng110_keiroHidden name="rng090knForm" property="rng090keiro"/>

<html:hidden property="rng090CatSid" />

<html:hidden property="rng060SelectCat" />
<html:hidden property="rng060SelectCatUsr" />

<input type="hidden" name="helpPrm" value="<bean:write name="rng090knForm" property="rngTemplateMode" />">
<input type="hidden" name="helpPrm" value="<bean:write name="rng090knForm" property="rngTplCmdMode" />">

<!-- BODY -->
<bean:define id="rngTemplateMode" name="rng090knForm" property="rngTemplateMode" />
<% int rtMode = ((Integer) rngTemplateMode).intValue(); %>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <% if (rtMode == tmodeShare) { %>
      <li>
        <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
      </li>
      <li>
        <gsmsg:write key="cmn.admin.setting" />
      </li>
      <li class="pageTitle_subFont">
        <logic:equal name="rng090knForm" property="rngTplCmdMode" value="<%= String.valueOf(tCmdAdd) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.entry.shared.template.kn" />
        </logic:equal>
        <logic:equal name="rng090knForm" property="rngTplCmdMode" value="<%= String.valueOf(tCmdEdit) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.entry.shared.template2.kn" />
        </logic:equal>
      </li>
    <% } else if (rtMode == tmodePrivate) { %>
      <li>
        <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
      </li>
      <li>
        <gsmsg:write key="cmn.preferences2" />
      </li>
      <li class="pageTitle_subFont">
        <logic:equal name="rng090knForm" property="rngTplCmdMode" value="<%= String.valueOf(tCmdAdd) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.entry.personal.template.kn" />
        </logic:equal>
        <logic:equal name="rng090knForm" property="rngTplCmdMode" value="<%= String.valueOf(tCmdEdit) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.entry.personal.template2.kn" />
        </logic:equal>
      </li>
    <% } %>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('cmn999kakutei');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rng090back');">
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
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.category" />
      </th>
      <td class="td_wt w80">
        <bean:write name="rng090knForm" property="rng090knCatName" />
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="rng.10" />
      </th>
      <td class="td_wt w80">
        <bean:write name="rng090knForm" property="rng090title" />
      </td>
    </tr>
    <% if (rtMode == tmodeShare) { %>
      <logic:notEqual value="2" name="rng090knForm" property="idSelectable" >
        <tr>
          <th class="w20">
            <gsmsg:write key="rng.rng180.04" />
          </th>
          <td class="td_wt  w100">
            <div>
              <bean:write name="rng090knForm" property="rng090idTitle" />
            </div>
            <logic:equal value="0" name="rng090knForm" property="idPrefManualEditable" >
              <p />
              <div>
                <span class="fw_b">
                  <gsmsg:write key="rng.rng210.04" />
                </span>
                <div>
                  <html:hidden name="rng090knForm" property="rng090idPrefManual" />
                  <logic:equal name="rng090knForm" property="rng090idPrefManual" value="1">
                     <gsmsg:write key="cmn.permit" />
                  </logic:equal>
                  <logic:equal name="rng090knForm" property="rng090idPrefManual" value="2">
                    <gsmsg:write key="cmn.not.permit" />
                  </logic:equal>
                </div>
              </div>
            </logic:equal>
          </td>
        </tr>
      </logic:notEqual>
    <%} %>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.title" />
      </th>
      <td class="td_wt w100">
        <bean:write name="rng090knForm" property="rng090rngTitle" />
      </td>
    </tr>
    <logic:notEqual value="<%=String.valueOf(RngConst.RNG_RTP_SPEC_VER_A480)%>" name="rng090knForm" property="rng090rtpSpecVer" >
      <tr>
        <th class="w20">
          <gsmsg:write key="rng.12" />
        </th>
        <td class="td_wt w100">
          <bean:write name="rng090knForm" property="rng090knViewContent" filter="false" />
        </td>
      </tr>
      <tr>
        <th class="w20">
          <gsmsg:write key="cmn.attached" />
        </th>
        <td class="td_wt w80">
          <logic:empty name="rng090knForm" property="rng090FileLabelList" scope="request">
            &nbsp;
          </logic:empty>
          <logic:notEmpty name="rng090knForm" property="rng090FileLabelList" scope="request">
            <logic:iterate id="fileMdl" name="rng090knForm" property="rng090FileLabelList" scope="request">
              <file-preview url="../ringi/rng090kn.do?CMD=fileDownload&rng090knTmpFileId=<bean:write name="fileMdl" property="value" />" filename="<bean:write name="fileMdl" property="label" />">
                <div>
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                  <a href="#!" onClick="return fileLinkClick('<bean:write name="fileMdl" property="value" />');">
                    <bean:write name="fileMdl" property="label" />
                  </a>
                </div>
              </file-preview>
            </logic:iterate>
          </logic:notEmpty>
        </td>
      </tr>
    </logic:notEqual>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="td_wt w100">
        <bean:write name="rng090knForm" property="rng090knViewBiko" filter="false" />
      </td>
    </tr>

  </table>
  <logic:equal value="<%=String.valueOf(RngConst.RNG_RTP_SPEC_VER_A480)%>" name="rng090knForm" property="rng090rtpSpecVer" >
    <table class="table-top">
      <tr>
        <th class="w100">
          <gsmsg:write key="rng.rng090.01" />
        </th>
      </tr>
      <tr class="bgC_tableCell">
        <td class="td_wt w100">
          <fb:main name="rng090knForm" property="rng090template" kakuninMode="kakunin"/>
        </td>
      </tr>
    </table>
  </logic:equal>
  <table class="table-top">
    <tr>
      <th class="w100">
        <gsmsg:write key="rng.24" />
      </th>
    </tr>
    <tr>
      <td class="w100">
        <% if (rtMode == tmodeShare) { %>
          <logic:equal name="rng090knForm" property="rng090useKeiroTemplate" value="0">
            <gsmsg:write key="rng.rng090.02"/>
            <br />
            <gsmsg:write key="rng.25"/>:<bean:write name="rng090knForm" property="rng090KeiroTemplateName" />
          </logic:equal>
          <logic:equal name="rng090knForm" property="rng090useKeiroTemplate" value="1">
            <gsmsg:write key="rng.rng090.03"/>
          </logic:equal>
        <% } %>
        <span name="rng090keiro" class="kakunin">
          <logic:equal name="rng090knForm" property="rng090useKeiroTemplate" value="0">
            <logic:notEqual name="rng090knForm" property="rng090KeiroTemplateSid" value="0">
              <ringi:rng110_keiro name="rng090knForm" property="rng090keiro" kakuninMode="kakunin"/>
            </logic:notEqual>
          </logic:equal>
          <logic:equal name="rng090knForm" property="rng090useKeiroTemplate" value="1">
            <ringi:rng110_keiro name="rng090knForm" property="rng090keiro" kakuninMode="kakunin"/>
          </logic:equal>
        </span>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('cmn999kakutei');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rng090back');">
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