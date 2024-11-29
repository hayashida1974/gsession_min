<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.usedsize.UsedSizeConst" %>
<%@ page import="jp.groupsession.v2.man.GSConstMain" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="man.personal.edit" />
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../main/js/man520.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../main/css/main.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<style>
.ui-widget-header {
    background: none!important;
    border: none!important;
}
</style>
</head>

<body>
<html:form action="/main/man520">

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
      <gsmsg:write key="main.man520.1" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man520Back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">

  <div class="display_flex mb20">
    <div class="txt_l cl_fontWarn mr40">
      <gsmsg:write key="main.man520.3" />
      <br>
      <gsmsg:write key="main.man520.4" />
    </div>

    <div class="display_flex ml_auto">
      <div class="hp60 wp60 bgC_header1 verAlignMid bor1">
        <span class="fw_bold side_headerTitle pl0 mrl_auto fs_base"><gsmsg:write key="cmn.sum" /></span>
      </div>
      <div class="hp60 verAlignMid bor1 border_left_none wp140 bgC_body">
        <div class="mrl_auto lh_normal">
          <span class="main_dataSum"><bean:write name="man520Form" property="sumValue" /></span><span class="fs_13"><bean:write name="man520Form" property="sumUnit" /></span>
        </div>
      </div>
    </div>
  </div>

  <div class="main_grid_pane w100">
  <logic:iterate id="dataMdl" name="man520Form" property="dataList">
    <bean:define id="diskSizeMdl" name="dataMdl" type="jp.groupsession.v2.man.man520.Man520DataModel" />
    <dl class="bor1 cursor_p">
      <dt class="side_header side_headerTitle pl0">
        <img class="btn_classicImg-display" src="<bean:write name="dataMdl" property="iconClassic" />" alt="<bean:write name="dataMdl" property="pluginName" />">
        <img class="btn_originalImg-display" src="<bean:write name="dataMdl" property="iconOriginal" />" alt="<bean:write name="dataMdl" property="pluginName" />">
        <span class="js_pluginName"><bean:write name="dataMdl" property="pluginName" /></span>
      </dt>
      <dd class="bgC_body m0"><div><%= diskSizeMdl.getStrTotalSize() %><span class="fs_13"><%= diskSizeMdl.getTotalSizeUnit() %></span></div></dd>
      <span class="display_none js_sizeTable">

      <logic:notEqual name="dataMdl" property="senyoMapFlg" value="true">
        <table class="table-left w100">
        <logic:notEmpty name="dataMdl" property="nameList">
        <logic:iterate id="detailName" name="dataMdl" property="nameList" type="java.lang.String">
          <tr>
            <th class="txt_l w70" name="5">
              <gsmsg:write key="<%= detailName %>" />
            </th>
            <td class="txt_r w30"><%= diskSizeMdl.getDetailSize(detailName) %><%= diskSizeMdl.getDetailSizeUnit(detailName) %></td>
          </tr>
        </logic:iterate>
        </logic:notEmpty>
          <tr>
            <th class="txt_l" name="5"><gsmsg:write key="ntp.172" /></th>
            <td class="txt_r"><%= diskSizeMdl.getStrTotalSize() %><%= diskSizeMdl.getTotalSizeUnit() %></td>
          </tr>
        </table>
      </logic:notEqual>
      <logic:equal name="dataMdl" property="senyoMapFlg" value="true">
        <bean:write name="dataMdl" property="senyoMapHtml" filter="false" />
      </logic:equal>
      </span>
    </dl>
  </logic:iterate>
  </div>
</div>

</html:form>

  <div class="display_none">
    <div id="detail_dialog" style="min-width: 300px">
    </div>
  </div>


<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>