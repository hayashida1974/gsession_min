<%@page import="java.util.Map.Entry"%>
<%@page import="jp.co.sjts.util.json.JSONObject"%>
<%@page import="jp.groupsession.v2.cmn.GSConstCommon"%>
<%@page import="jp.groupsession.v2.rng.RngConst"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/formbuilder" prefix="fb" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>
<%@ taglib tagdir="/WEB-INF/tags/common" prefix="cmn" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% int tmodeAll = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_ALL; %>
<% int tmodeShare = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_SHARE; %>
<% int tmodePrivate = jp.groupsession.v2.rng.RngConst.RNG_TEMPLATE_PRIVATE; %>

<% int tCmdAdd = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_ADD; %>
<% int tCmdEdit = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_EDIT; %>

<% String maxLengthFormat = String.valueOf(jp.groupsession.v2.rng.RngConst.MAX_LENGTH_FORMAT); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">

<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

 <link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>" type="text/css">


<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../ringi/css/ringi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.ui.touch-punch0.2.3.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>

<script type="text/javascript" src="../ringi/js/pageutil.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/clonebtn.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../ringi/js/rng090.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
<cmn:loadscript src="../common/js/subform.js"/>

<title>GROUPSESSION <gsmsg:write key="rng.57" /></title>
</head>

<body onunload="windowClose();" onload="">

<html:form styleId="rng090Form" action="ringi/rng090"  >
<input type="hidden" name="CMD" value="">
<%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>

<html:hidden property="backScreen" />
<html:hidden property="rngTemplateMode" />

<html:hidden property="rng010TransitionFlg" />

<html:hidden property="rngTplCmdMode" />
<html:hidden property="rngSelectTplSid" />

<html:hidden property="rng060SelectCat" />
<html:hidden property="rng060SelectCatUsr" />

<html:hidden property="rng060SelectCat" />
<html:hidden property="rng060SelectCatUsr" />
<html:hidden property="rng090UserSid" />
<html:hidden property="rng090KeiroTemplateSid" />
<html:hidden property="rng090KeiroTemplateUsrSid" />
<html:hidden property="rng090idSid" />
<html:hidden property="rng090rtpSpecVer" />

<html:hidden property="flgFileChange" />
<html:hidden property="accessDateString" />


<input type="hidden" name="helpPrm" value="<bean:write name="rng090Form" property="rngTemplateMode" />">
<input type="hidden" name="helpPrm" value="<bean:write name="rng090Form" property="rngTplCmdMode" />">
<script>
 msglist_rng090 = new Array();
 msglist_rng090['cmn.ok'] = '<gsmsg:write key="cmn.ok"/>';
 msglist_rng090['cmn.cancel'] = '<gsmsg:write key="cmn.cancel"/>';
 msglist_rng090['cmn.close'] = '<gsmsg:write key="cmn.close"/>';
 msglist_rng090['cmn.back'] = '<gsmsg:write key="cmn.back"/>';
 msglist_rng090['mobile.13'] = '<gsmsg:write key="mobile.13"/>';
 msglist_rng090['mobile.14'] = '<gsmsg:write key="mobile.14"/>';
</script>

<!-- body -->
<bean:define id="rngTemplateMode" name="rng090Form" property="rngTemplateMode" />
<% int rtMode = ((Integer) rngTemplateMode).intValue(); %>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <% if (rtMode == tmodeShare) { %>
      <li>
        <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
      </li>
      <li><gsmsg:write key="cmn.admin.setting" /></li>
      <li class="pageTitle_subFont">
        <logic:equal name="rng090Form" property="rngTplCmdMode" value="<%= String.valueOf(tCmdAdd) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.entry.shared.template" />
        </logic:equal>
        <logic:equal name="rng090Form" property="rngTplCmdMode" value="<%= String.valueOf(tCmdEdit) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.entry.shared.template2" />
        </logic:equal>
      </li>
    <% } else if (rtMode == tmodePrivate) { %>
      <li>
        <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
        <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
      </li>
      <li><gsmsg:write key="cmn.preferences2" /></li>
      <li class="pageTitle_subFont">
        <logic:equal name="rng090Form" property="rngTplCmdMode" value="<%= String.valueOf(tCmdAdd) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.entry.personal.template" />
        </logic:equal>
        <logic:equal name="rng090Form" property="rngTplCmdMode" value="<%= String.valueOf(tCmdEdit) %>">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="cmn.entry.personal.template2" />
        </logic:equal>
      </li>
    <% } %>
    <li>
      <div>
        <button type="button" class="baseBtn btn_preview_n1" value="<gsmsg:write key="cmn.preview" />" onClick="openRingiTemplatePreview();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
          <gsmsg:write key="cmn.preview" />
        </button>
        <button type="button" class="baseBtn btn_ok1" value="OK" onClick="buttonPush('ok090');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="rng090Form" property="rngTplCmdMode" value="<%= String.valueOf(tCmdEdit) %>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('cmn999del');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="..//common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rng060');">
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
    <logic:equal name="rng090Form" property="rngTplCmdMode" value="<%= String.valueOf(tCmdEdit) %>">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.register.copy.new" />" onClick="buttonPush('copy');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy.new" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy.new" />">
        <gsmsg:write key="cmn.register.copy2" />
      </button>
    </logic:equal>
  </div>

  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>

  <table class="table-left">
    <tr class="w100">
      <th class="w30">
        <gsmsg:write key="cmn.category" />
        <span class="cl_fontWarn">
          <gsmsg:write key="cmn.comments" />
        </span>
      </th>
      <td class="w70">
        <html:select name="rng090Form" property="rng090CatSid">
          <html:optionsCollection name="rng090Form" property="rng090CategoryList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr class="w100">
      <th class="w30">
        <gsmsg:write key="rng.10" />
        <span class="cl_fontWarn">
          <gsmsg:write key="cmn.comments" />
        </span>
      </th>
      <td class="w70">
        <html:text name="rng090Form" property="rng090title" styleClass="w100" maxlength="100" />
      </td>
    </tr>

    <% if (rtMode == tmodeShare) { %>
      <logic:notEqual value="2" name="rng090Form" property="idSelectable" >
        <tr class="w100">
          <th class="w30">
            <gsmsg:write key="rng.rng180.04" />
          </th>
          <td class="w70">
            <div>
              <bean:write name="rng090Form" property="rng090idTitle" />
                <logic:equal value="1" name="rng090Form" property="idSelectable" >
                 &nbsp;
                 <button type="button" name="rng090idSelect" class="baseBtn btn_base1s" value="<gsmsg:write key="cmn.select" />">
                   <gsmsg:write key="cmn.select" />
                 </button>
              </logic:equal>
            </div>
            <div id="idselect" class="display_n" ></div>
            <logic:equal value="0" name="rng090Form" property="idPrefManualEditable" >
              <div class="mt10">
                <gsmsg:write key="rng.rng210.04" />
                <br>
                <div class="verAlignMid">
                  <html:radio styleId="idPrefManual_1" name="rng090Form" property="rng090idPrefManual" value="1">
                    <label for="idPrefManual_1">
                      <gsmsg:write key="cmn.permit" />
                    </label>
                  </html:radio>
                  <html:radio styleId="idPrefManual_2" name="rng090Form" property="rng090idPrefManual" styleClass="ml10" value="2">
                    <label for="idPrefManual_2">
                      <gsmsg:write key="cmn.not.permit" />
                    </label>
                  </html:radio>
                </div>
              </div>
            </logic:equal>
          </td>
        </tr>
      </logic:notEqual>
    <%} %>
    <tr class="w100">
      <th class="w30">
        <gsmsg:write key="cmn.title" />
      </th>
      <td class="w70">
        <html:text name="rng090Form" property="rng090rngTitle" styleClass="w100" maxlength="100" />
      </td>
    </tr>
<!-- フォーマット 個人テンプレート限定 -->
    <logic:notEqual value="<%=String.valueOf(RngConst.RNG_RTP_SPEC_VER_A480)%>" name="rng090Form" property="rng090rtpSpecVer" >
      <script>
        $(function() {
          showLengthId($('textarea[name="rng090content"]')[0], <%= maxLengthFormat %>, 'inputlength');
        })
      </script>

      <tr class="w100 bor_b1 bor_t1">
        <th class="w30">
          <gsmsg:write key="rng.12" />
          <span class="cl_fontWarn">
            <gsmsg:write key="cmn.comments" />
          </span>
        </th>
        <td class="w70">
          <textarea  name="rng090content" class="w100" rows="20" onkeyup="showLengthStr(value, <%= maxLengthFormat %>, 'inputlength');" id="inputstr"><bean:write name="rng090Form" property="rng090content" /></textarea>
          <br>
          <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthFormat %>&nbsp;<gsmsg:write key="cmn.character" /></span>
        </td>
      </tr>

      <tr class="w100">
        <th class="w30">
          <gsmsg:write key="cmn.attached" />
        </th>
        <td class="w70">
          <attachmentFile:filearea
          mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
          pluginId="<%= RngConst.PLUGIN_ID_RINGI %>"
          tempDirId="rng090" />
        </td>
      </tr>
    </logic:notEqual>

<!-- 備考 -->
    <tr class="w100">
      <th class="w30">
        <gsmsg:write key="cmn.memo" />
      </th>
      <td class="w70">
        <script>
          $(function() {
            showLengthId($('textarea[name="rng090biko"]')[0], <%= maxLengthFormat %>, 'bikolength');
          });
        </script>
        <textarea name="rng090biko" class="w100" rows="5" onkeyup="showLengthStr(value, <%= maxLengthFormat %>, 'bikolength');" id="inputstr"><bean:write name="rng090Form" property="rng090biko" /></textarea>
        <br>
        <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="bikolength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthFormat %>&nbsp;<gsmsg:write key="cmn.character" /></span>
      </td>
    </tr>
  </table>

  <logic:equal value="<%=String.valueOf(RngConst.RNG_RTP_SPEC_VER_A480)%>" name="rng090Form" property="rng090rtpSpecVer" >
<!-- 入力フォーム -->
    <table class="table-top">
      <tr>
        <th>
          <gsmsg:write key="rng.rng090.01" />
        </th>
      </tr >
      <tr>
        <td colspan="5" valign="middle" class="bgC_tableCell txt_m">
          <fb:main name="rng090Form" property="rng090template"/>
        </td>
      </tr>
    </table>
  </logic:equal>

<!-- 経路 -->
  <table class="table-top">
    <tr>
      <th>
        <gsmsg:write key="rng.24" />
      </th>
    </tr>
    <tr>
      <td colspan="5"  class="bgC_tableCell txt_m">
        <logic:equal value="<%=String.valueOf(RngConst.RNG_RTP_SPEC_VER_A480)%>" name="rng090Form" property="rng090rtpSpecVer" >
          <div class="verAlignMid">
            <html:radio styleId="useKeiroTemp_1" name="rng090Form" property="rng090useKeiroTemplate" value="1">
              <label for="useKeiroTemp_1"><gsmsg:write key="rng.rng090.03"/></label>
            </html:radio>
            <html:radio styleId="useKeiroTemp_0" name="rng090Form" property="rng090useKeiroTemplate" value="0" styleClass="ml5">
              <label for="useKeiroTemp_0"><gsmsg:write key="rng.rng090.02"/></label>
            </html:radio>
            <button type="button" name="rng090KeiroTemplateSelect" class="baseBtn btn_base1s" value="<gsmsg:write key="cmn.select" />">
              <gsmsg:write key="cmn.select" />
            </button>
          </div>
        </logic:equal>
        <div name="rng090keiro">
          <logic:equal name="rng090Form" property="rng090useKeiroTemplate" value="1">
            <% if (rtMode == tmodePrivate) { %>
              <ringi:rng110_keiro name="rng090Form" property="rng090keiro"  keiroShareRange="<%=RngConst.RNG_TEMPLATE_PRIVATE %>" />
            <%} %>
            <% if (rtMode == tmodeShare) { %>
              <ringi:rng110_keiro name="rng090Form" property="rng090keiro"  keiroShareRange="<%=RngConst.RNG_TEMPLATE_SHARE %>" />
            <%} %>
          </logic:equal>
          <logic:equal name="rng090Form" property="rng090useKeiroTemplate" value="0">
            <logic:notEqual name="rng090Form" property="rng090KeiroTemplateSid" value="0">
              <div class="keiro_template">
                <gsmsg:write key="rng.25"/>:<bean:write name="rng090Form" property="rng090KeiroTemplateName" />
              </div>
              <ringi:rng110_keiro name="rng090Form" property="rng090keiro"  kakuninMode="kakunin"/>
            </logic:notEqual>
            <logic:equal name="rng090Form" property="rng090KeiroTemplateSid" value="0">
              <ringi:rng110_keiro name="rng090Form" property="rng090keiro"  hidden="hidden"/>
            </logic:equal>
          </logic:equal>
        </div>
        <% if (rtMode == tmodePrivate) { %>
          <ringi:ring020_keirotemplateDialog/>
        <%} %>
        <% if (rtMode == tmodeShare) { %>
          <ringi:ring020_keirotemplateDialog hiddenPersonal="hidden"/>
        <%} %>
      </td>
    </tr>
  </table>


  <div class="footerBtn_block">
    <button type="button" class="baseBtn btn_preview_n1" value="<gsmsg:write key="cmn.preview" />" onClick="openRingiTemplatePreview();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
      <gsmsg:write key="cmn.preview" />
    </button>
    <button type="button" class="baseBtn btn_ok1" value="OK" onClick="buttonPush('ok090');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="rng090Form" property="rngTplCmdMode" value="<%= String.valueOf(tCmdEdit) %>">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('cmn999del');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="..//common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('rng060');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>
<div class="display_none">
  <div class="js_apiconnectAddDialog apiconnectAddDialog" />
</div>
<div id="paramEditClose" class="display_n">
  <table class="w100 h100">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="rng.rng090.72" />
      </td>
    </tr>
  </table>
</div>
</body>
</html:html>
