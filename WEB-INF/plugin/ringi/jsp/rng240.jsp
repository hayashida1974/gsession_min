<%@page import="jp.groupsession.v2.rng.RngConst"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../ringi/css/ringi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<script src="../common/js/glayer.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.ui.touch-punch0.2.3.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../ringi/js/rng110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/usrgrpselect.js?<%= GSConst.VERSION_PARAM %>"></script>

<title>GROUPSESSION <gsmsg:write key="rng.62" /> <gsmsg:write key="rng.rng110.01" /></title>
</head>
<body>
<html:form action="ringi/rng240">

<input type="hidden" name="helpPrm" value="<bean:write name="rng240Form" property="rngRctCmdMode" />">
<input type="hidden" name="CMD" value="entry">
<%@ include file="/WEB-INF/plugin/ringi/jsp/rng010_hiddenParams.jsp" %>
<html:hidden property="backScreen" />
<html:hidden property="rngTemplateMode" />
<html:hidden property="rctSid" />
<html:hidden property="rngRctCmdMode" />
<html:hidden property="rng110UserSid" />

<logic:notEmpty name="rng240Form" property="rng110apprUser">
  <logic:iterate id="apprUser" name="rng240Form" property="rng110apprUser">
    <input type="hidden" name="rng110apprUser" value="<bean:write name="apprUser" />">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="rng240Form" property="rng110confirmUser">
  <logic:iterate id="confirmUser" name="rng240Form" property="rng110confirmUser">
    <input type="hidden" name="rng110confirmUser" value="<bean:write name="confirmUser" />">
  </logic:iterate>
</logic:notEmpty>

<jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
<!-- body -->

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.admin.setting" />
    </li>
    <li class="pageTitle_subFont">
      <logic:equal name="rng240Form" property="rngRctCmdMode" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_ADD) %>">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.rng110.01" />
      </logic:equal>
      <logic:equal name="rng240Form" property="rngRctCmdMode" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_EDIT) %>">
        <span class="pageTitle_subFont-plugin"><gsmsg:write key="rng.62" /></span><gsmsg:write key="rng.rng110.41" />
      </logic:equal>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn js_btn_ok1" value="OK">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="rng240Form" property="rngRctCmdMode" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_EDIT) %>">
          <button type="button" class="baseBtn js_btn_dell_n1" value="<gsmsg:write key="cmn.delete" />">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="..//common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backList');">
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
    <tr class="w100">
      <th class="w20">
        <gsmsg:write key="rng.rng110.02" />
        <span class="cl_fontWarn">
          <gsmsg:write key="cmn.comments" />
        </span>
      </th>
      <td class="w80">
        <html:text name="rng240Form" property="rng110name" styleClass="wp300" maxlength="20" />
        <input type="text" name="dammy" class="display_n" maxlength="20" />
      </td>
    </tr>
    <tr  class="w100">
      <th class="w20">
        <gsmsg:write key="rng.24" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <ringi:rng110_keiro name="rng240Form" property="rng110keiro"  keiroShareRange="<%=RngConst.RNG_TEMPLATE_SHARE %>"/>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn js_btn_ok1" value="OK">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="rng240Form" property="rngRctCmdMode" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_EDIT) %>">
      <button type="button" class="baseBtn js_btn_dell_n1" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="..//common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backList');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />

</html:form>
</body>
</html:html>