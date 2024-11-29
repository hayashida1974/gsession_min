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
<title>GROUPSESSION <gsmsg:write key="main.man002.19" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body>
<html:form action="/main/man340kn">
<input type="hidden" name="CMD" value="">
<html:hidden property="man340initFlg" />
<html:hidden property="man340cmdMode" />
<html:hidden property="man340file" />
<html:hidden property="man340SaveFile" />
<html:hidden property="man340funcId" />
<html:hidden property="man340title" />
<html:hidden property="man340url" />
<html:hidden property="man340openKbn" />
<html:hidden property="man340paramKbn" />
<html:hidden property="man340sendKbn" />
<logic:notEmpty name="man340knForm" property="man340urlParamListToList">
  <logic:iterate id="paramData" name="man340knForm" property="man340urlParamListToList" indexId="idx">
    <input type="hidden" name="man340urlParamList[<%= String.valueOf(idx.intValue()) %>].paramName" value="<bean:write name="paramData" property="paramName" />">
    <input type="hidden" name="man340urlParamList[<%= String.valueOf(idx.intValue()) %>].paramValue" value="<bean:write name="paramData" property="paramValue" />">
  </logic:iterate>
</logic:notEmpty>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.plugin" /><gsmsg:write key="cmn.add" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('340kn_ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man340');">
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
      <html:errors />
    </logic:messagesPresent>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.plugin" /><gsmsg:write key="cmn.name3" />
      </th>
      <td class="w80">
        <bean:write name="man340knForm" property="man340title" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        URL
      </th>
      <td class="w80">
        <bean:write name="man340knForm" property="man340url" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man340.5" />
      </th>
      <td class="w80">
        <logic:equal name="man340knForm" property="man340paramKbn" value="0">
          <gsmsg:write key="cmn.noset" />
        </logic:equal>
        <logic:notEqual name="man340knForm" property="man340paramKbn" value="0">
          <gsmsg:write key="cmn.setting.do" />
        </logic:notEqual>
        <logic:notEqual name="man340knForm" property="man340paramKbn" value="0">
          <div class="settingForm_separator">
            <logic:equal name="man340knForm" property="man340sendKbn" value="0">
              <span class="mr10 fw_b"><gsmsg:write key="main.man340.6" />：</span><gsmsg:write key="main.man340.7" />
            </logic:equal>
            <logic:notEqual name="man340knForm" property="man340sendKbn" value="0">
              <span class="mr10 fw_b"><gsmsg:write key="main.man340.6" />：</span><gsmsg:write key="main.man340.8" />
            </logic:notEqual>
          </div>
          <table class="mt10">
            <tr class="border_none">
              <th class="wp200 bgC_header2 txt_c bor1">
                <gsmsg:write key="main.man340.9" />
              </th>
              <td class="wp100 border_none"></td>
              <th class="wp200 bgC_header2 txt_c bor1">
                <gsmsg:write key="main.man340.10" />
              </th>
            </tr>
            <logic:notEmpty name="man340knForm" property="man340urlParamListToList">
              <logic:iterate name="man340knForm" property="man340urlParamListToList" id="urlParamMdl">
                <tr class="border_none">
                  <td class="txt_l bor_b1 bor_l1 bor_r1">
                    <bean:write name="urlParamMdl" property="paramName" />
                  </td>
                  <td class="txt_c border_none">=</td>
                  <td class="txt_l bor_b1 bor_l1 bor_r1">
                    <bean:write name="urlParamMdl" property="paramValue" />
                  </td>
                </tr>
              </logic:iterate>
            </logic:notEmpty>
          </table>
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="main.man340.1" />
      </th>
      <td class="w80">
        <logic:equal name="man340knForm" property="man340openKbn" value="0">
          <gsmsg:write key="main.man340.2" />
        </logic:equal>
        <logic:notEqual name="man340knForm" property="man340openKbn" value="0">
          <gsmsg:write key="main.man340.3" />
        </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.icon" />
      </th>
      <td class="w80">
        <logic:equal name="man340knForm" property="man340file" value="">
          <img src="../common/images/pluginImg/classic/menu_icon_plugin_default_25.png" name="pitctImage" class="btn_classicImg-display wp25hp25" alt="<gsmsg:write key="cmn.icon" />">
          <img src="../common/images/pluginImg/original/menu_icon_plugin_default_50.png" name="pitctImage" class="btn_originalImg-display wp25hp25" alt="<gsmsg:write key="cmn.icon" />">
        </logic:equal>
        <logic:notEqual name="man340knForm" property="man340file" value="">
          <img src="../main/man340.do?CMD=getImageFile" name="pitctImage" class="wp25hp25" alt="<gsmsg:write key="cmn.icon" />" onload="initImageView('pitctImage');">
        </logic:notEqual>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('340kn_ok');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('man340');">
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