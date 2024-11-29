<%@page import="jp.groupsession.v2.prj.GSConstProject"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.man.GSConstMain" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man480.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man480.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body onunload="windowClose();">
<bean:parameter id="scrollY" name="man480scrollY" value="0"/>
<logic:greaterThan name="scrollY" value="0">
<script>
   $(function () {
       $(window).scrollTop(<bean:write name="scrollY" />);
   })
</script>
</logic:greaterThan>
<html:form action="/main/man480">
<input type="hidden" name="CMD" value="">
<html:hidden property="man440GrpSid"/>
<html:hidden property="man480scrollY" value=""/>
<!-- BODY -->
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="main.man480.1" />
    </li>
    <li>
      <button type="button" value="<gsmsg:write key="cmn.import" />" class="baseBtn" onClick="buttonPush('Man480_Import');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
        <gsmsg:write key="cmn.import" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('Man480_Back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </div>
  <div class="p10 bor1 bgC_cybozuAlert">
    <table class="w100">
      <tr>
        <td class="w100 fw_b pb20 fs_17" colspan="2">
          <gsmsg:write key="main.man440.1" />
        </td>
      </tr>
      <tr>
        <td class="txt_t">●</td>
        <td class="w100 txt_l"><gsmsg:write key="main.man480.9" /></td>
      </tr>
      <tr>
        <td class="txt_t">●</td>
        <td class="w100 txt_l"><gsmsg:write key="main.man480.10" /></td>
      </tr>
      <tr>
        <td class="txt_t">●</td>
        <td class="w100 txt_l"><gsmsg:write key="main.man480.11" /></td>
      </tr>
      <tr>
        <td class="txt_t">●</td>
        <td class="w100 txt_l"><gsmsg:write key="main.man480.12" /></td>
      </tr>
      <tr>
        <td class="txt_t">●</td>
        <td class="w100 txt_l"><gsmsg:write key="main.man480.13" /></td>
      </tr>
    </table>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="main.man450.2" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <div>
          <button type="button" class="baseBtn ml0" value="<gsmsg:write key="cmn.attached" />" onClick="opnTemp('man480selectFiles', '<%=GSConstCommon.CMN110MODE_FILE_TANITU %>', '<%=GSConstMain.PLUGIN_ID_MAIN%>', 'man480');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
            <gsmsg:write key="cmn.attached" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_trash.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_trash.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </div>
        <html:select name="man480Form" property="man480selectFiles" styleClass="wp200 ml0" size="1">
          <logic:notEmpty name="man480Form" property="man480FileLabelList">
             <html:optionsCollection name="man480Form" property="man480FileLabelList" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w25 no_w">
        <gsmsg:write key="main.man480.2" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w75">
        <div class="verAlignMid">
          <html:radio name="man480Form" property="man480mode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_ADD) %>" styleId="mode1" onchange="document.man480Form.man480scrollY.value = $(window).scrollTop();buttonPush('dsp');" />
          <label for="mode1"><gsmsg:write key="main.man480.3" /></label>
        </div>
        <div class="mt5 fw_b">
          <gsmsg:write key="project.31" />
        </div>
        <div>
          <html:text property="man480projectID" styleClass="wp300" maxlength="<%=String.valueOf(GSConstProject.MAX_LENGTH_PRJ_ID) %>" />
        </div>
        <div class="mt5 fw_b">
          <gsmsg:write key="project.40" />
        </div>
        <div>
          <html:text property="man480projectName" styleClass="wp500" maxlength="<%=String.valueOf(GSConstProject.MAX_LENGTH_PRJ_NAME) %>" />
        </div>
        <div class="mt5 fw_b">
          <gsmsg:write key="project.41" />
        </div>
        <div>
          <html:text property="man480projectShortName" styleClass="wp500" maxlength="<%=String.valueOf(GSConstProject.MAX_LENGTH_PRJ_SHORT_NAME) %>" />
        </div>
        <div class="mt10 fw_b">
          <gsmsg:write key="main.man480.5" />
        </div>
        <div>
          ・<bean:write name="man480Form" property="man480grpName" />
        </div>
        <div class="mt20 verAlignMid">
          <html:radio name="man480Form" property="man480mode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CMDMODE_EDIT) %>" styleId="mode2" onchange="document.man480Form.man480scrollY.value = $(window).scrollTop();buttonPush('dsp');" />
          <label for="mode2"><gsmsg:write key="main.man480.4" /></label>
        </div>
        <div class="mt5">
          <html:select property="man480selectProject" styleClass="wp200 ml0" onchange="document.man480Form.man480scrollY.value = $(window).scrollTop();buttonPush('dsp');">
            <logic:notEmpty name="man480Form" property="man480projectLabelList">
              <html:optionsCollection name="man480Form" property="man480projectLabelList" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
        </div>
        <div>
          <gsmsg:write key="cmn.comments" /><gsmsg:write key="main.man480.7" />
        </div>
      </td>
    </tr>
    <logic:equal name="man480Form" property="man480fileOk" value="true">
      <tr>
        <th class="w25 no_w">
          <gsmsg:write key="main.man480.6" />
        </th>
        <td class="w75">
          <div>
            <gsmsg:write key="main.man480.8" />
          </div>
          <table class="w100 mt10 table-noBorder">
            <tr>
              <td class="w10 pt0 no_w txt_r">
                未着手
              </td>
              <td class="w5 pt0 txt_r">
                0%
              </td>
              <td class="w85">
              </td>
            </tr>
            <logic:notEmpty name="man480Form" property="man480svStatusLabelList">
              <logic:iterate name="man480Form" property="man480svStatusLabelList" id="statusLabel" indexId="idx">
                <tr>
                  <td class="w10 pt0 no_w txt_r">
                    <bean:write name="statusLabel" property="label"/>
                  </td>
                  <td class="w90 pt0 txt_l">
                   <bean:write name="statusLabel" property="value"/>%
                  </td>
                </tr>
              </logic:iterate>
            </logic:notEmpty>
            <logic:notEmpty name="man480Form" property="man480statusNames">
              <logic:iterate name="man480Form" property="man480statusNames" id="statusName" indexId="idx">
                <tr>
                  <td class="w10 pt0 no_w txt_r">
                    <bean:write name="statusName" />
                  </td>
                  <td class="w5 pt0 txt_r">
                   <html:text styleClass="wp50" maxlength="<%=String.valueOf(GSConstProject.MAX_LENGTH_RATE) %>" name="man480Form" property="<%=\"man480statusValue(\" + idx + \")\" %>" />%
                  </td>
                  <td class="w85">
                  </td>
                </tr>
              </logic:iterate>
            </logic:notEmpty>
            <tr>
              <td class="w10 pt0 no_w txt_r">
                完了
              </td>
              <td class="w5 pt0 txt_r">
                100%
              </td>
              <td class="w85">
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </logic:equal>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.import" />" class="baseBtn" onClick="buttonPush('Man480_Import');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <gsmsg:write key="cmn.import" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('Man480_Back');">
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