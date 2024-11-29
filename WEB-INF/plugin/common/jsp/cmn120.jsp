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
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmn120.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <bean:write name="cmn120Form" property="cmn120FunctionName" /><gsmsg:write key="cmn.select" /></title>
</head>

<body class="body_03" onload="cmn120Load();">

<html:form action="common/cmn120">

<input type="hidden" name="CMD">
<html:hidden property="cmn120BackUrl" />
<html:hidden property="cmn120FunctionName" />
<html:hidden property="cmn120FormKey" />
<html:hidden property="cmn120paramName" />
<html:hidden property="cmn120MovePage" />

<logic:notEmpty name="cmn120Form" property="cmn120userSid">
  <logic:iterate id="usid" name="cmn120Form" property="cmn120userSid">
    <input type="hidden" name="cmn120userSid" value="<bean:write name="usid" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="cmn120Form" property="cmn120userSidOld">
  <logic:iterate id="usid" name="cmn120Form" property="cmn120userSidOld">
    <input type="hidden" name="cmn120userSidOld" value="<bean:write name="usid" />">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="cmn120Form" property="cmn120paramName">
  <logic:notEmpty name="cmn120Form" property="cmn120userSid">
    <logic:iterate id="usid" name="cmn120Form" property="cmn120userSid">
      <input type="hidden" name="<bean:write name="cmn120Form" property="cmn120paramName" />" value="<bean:write name="usid" />">
    </logic:iterate>
  </logic:notEmpty>
</logic:notEmpty>

<logic:match name="cmn120Form" property="cmn120BackUrl" value="smail">
  <input type="hidden" name="helpPrm" value="0">
</logic:match>
<logic:match name="cmn120Form" property="cmn120BackUrl" value="circular">
  <input type="hidden" name="helpPrm" value="1">
</logic:match>
<logic:match name="cmn120Form" property="cmn120BackUrl" value="project">
  <input type="hidden" name="helpPrm" value="2">
</logic:match>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!-- BODY -->

<div class="pageTitle w80 mrl_auto">
  <ul>
    <logic:match name="cmn120Form" property="cmn120BackUrl" value="smail">
      <li>
        <img class="header_pluginImg-classic" src="../smail/images/classic/header_smail.png" alt="<gsmsg:write key="cmn.shortmail" />">
        <img class="header_pluginImg" src="../smail/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.shortmail" />">
        <img class="header_pluginImg-classic" src="../common/images/pluginImg/classic/menu_icon_smail_25.gif" alt="<gsmsg:write key="cmn.shortmail" />">
        <img class="header_pluginImg" src="../common/images/pluginImg/original/menu_icon_smail_50.png" alt="<gsmsg:write key="cmn.shortmail" />">
      </li>
      <li>
        <gsmsg:write key="cmn.shortmail" />
      </li>
      <li class="pageTitle_subFont">
        <bean:write name="cmn120Form" property="cmn120FunctionName" /><gsmsg:write key="cmn.select" />
      </li>
    </logic:match>
    <logic:match name="cmn120Form" property="cmn120BackUrl" value="circular">
      <li>
        <img class="header_pluginImg-classic" src="../common/images/pluginImg/classic/menu_icon_circular_25.gif" alt="<gsmsg:write key="cir.5" />">
        <img class="header_pluginImg" src="../common/images/pluginImg/original/menu_icon_circular_50.png" alt="<gsmsg:write key="cir.5" />">
      </li>
      <li>
        <gsmsg:write key="cir.5" />
      </li>
      <li class="pageTitle_subFont">
        <bean:write name="cmn120Form" property="cmn120FunctionName" /><gsmsg:write key="cmn.select" />
      </li>
    </logic:match>
    <logic:match name="cmn120Form" property="cmn120BackUrl" value="project">
      <li>
        <img class="header_pluginImg-classic" src="../common/images/pluginImg/classic/menu_icon_project_25.gif" alt="<gsmsg:write key="cmn.project" />">
        <img class="header_pluginImg" src="../common/images/pluginImg/original/menu_icon_project_50.png" alt="<gsmsg:write key="cmn.project" />">
      </li>
      <li>
        <gsmsg:write key="cmn.project" />
      </li>
      <li class="pageTitle_subFont">
        <bean:write name="cmn120Form" property="cmn120FunctionName" /><gsmsg:write key="cmn.select" />
      </li>
    </logic:match>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.select" />" onClick="cmn120ButtonPush(1);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.select" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.select" />">
          <gsmsg:write key="cmn.select" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="cmn120ButtonPush(2);">
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
      <th class="w30">
        <gsmsg:write key="cmn.mygroup" />
      </th>
      <td class="w70">
        <div>
          <html:select name="cmn120Form" property="cmn120MyGroupSid" styleClass="w50">
            <logic:notEmpty name="cmn120Form" property="cmn120MyGroupList">
              <html:optionsCollection name="cmn120Form" property="cmn120MyGroupList" value="value" label="label" />
            </logic:notEmpty>
          </html:select>
          <bean:define id="functionName" name="cmn120Form" property="cmn120FunctionName" type="java.lang.String" />
          <button type="button" class="baseBtn ml10" value="<gsmsg:write key="cmn.cmn120.1" arg0="<%= functionName %>" />" onClick="cmn120ButtonPush(3);">
            <gsmsg:write key="cmn.cmn120.1" arg0="<%= functionName %>" />
          </button>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <bean:write name="cmn120Form" property="cmn120FunctionName" />
      </th>
      <td>
        <table class="userSelectBlock">
          <tr>
            <td class="userSelect_fromTo">
              <span class="userGroup_title"><bean:write name="cmn120Form" property="cmn120FunctionName" /></span>
            </td>
            <td class="userSelect_center"></td>
            <td class="userSelect_fromTo lh_normal">
              <button class="baseBtn userSelect_btn mt0" type="button" value="<gsmsg:write key="cmn.sel.all.group" />" onClick="return openAllGroup(this.form.cmn120groupSid, 'cmn120groupSid', '-1', '0', 'changeGrp', 'cmn120userSid', '-1');">
                <gsmsg:write key="cmn.sel.all.group" />
              </button>
              <div class="verAlignMid">
                <html:select styleClass="wp200" name="cmn120Form" property="cmn120groupSid" onchange="cmn120ChangeGrp();">
                  <logic:notEmpty name="cmn120Form" property="cmn120GroupList">
                    <html:optionsCollection name="cmn120Form" property="cmn120GroupList" value="value" label="label" />
                  </logic:notEmpty>
                </html:select>
                <button class="iconBtn-border ml5" type="button" id="cmn120GroupBtn" value="" onClick="openGroupWindow(this.form.cmn120groupSid, 'cmn120groupSid', '0', 'changeGrp');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
                </button>
              </div>
           </td>
          </tr>
          <tr class="border_none">
            <td class="userSelect_fromTo">
              <html:select styleClass="w100 hp215" name="cmn120Form" property="cmn120SelectRightUser" size="15" multiple="true">
                <logic:notEmpty name="cmn120Form" property="cmn120RightUserList">
                  <logic:iterate id="user" name="cmn120Form" property="cmn120RightUserList" >
                    <logic:equal value="1" name="user" property="usrUkoFlg">
                      <option value='<bean:write name="user" property="value"/>' class="mukoUserOption"><bean:write name="user" property="label"/></option>
                    </logic:equal>
                    <logic:equal value="0" name="user" property="usrUkoFlg">
                      <option value='<bean:write name="user" property="value"/>'><bean:write name="user" property="label"/></option>
                    </logic:equal>
                  </logic:iterate>
                </logic:notEmpty>
                <option value="-1">&nbsp;</option>
              </html:select>
            </td>
            <td class="selectForm_moveArea userSelect_center">
              <div>
                <button class="baseBtn mb20" value="<gsmsg:write key="cmn.add" />" onClick="cmn120ButtonPush(5);">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                  <i class="icon-left"></i>
                  <gsmsg:write key="cmn.add" />
                </button>
              </div>
              <div>
                <button class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="cmn120ButtonPush(4);">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                  <i class="icon-right"></i>
                  <gsmsg:write key="cmn.delete" />
                </button>
              </div>
            </td>
            <td class="userSelect_fromTo">
              <html:select styleClass="w100 hp215" name="cmn120Form" property="cmn120SelectLeftUser" size="15" multiple="true">
                <logic:notEmpty name="cmn120Form" property="cmn120LeftUserList">
                  <logic:iterate id="user" name="cmn120Form" property="cmn120LeftUserList" >
                    <logic:equal value="1" name="user" property="usrUkoFlg">
                      <option value='<bean:write name="user" property="value"/>' class="mukoUserOption"><bean:write name="user" property="label"/></option>
                    </logic:equal>
                    <logic:equal value="0" name="user" property="usrUkoFlg">
                      <option value='<bean:write name="user" property="value"/>'><bean:write name="user" property="label"/></option>
                    </logic:equal>
                  </logic:iterate>
                </logic:notEmpty>
                <option value="-1">&nbsp;</option>
              </html:select>
            </td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.select" />" onClick="cmn120ButtonPush(1);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.select" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.select" />">
      <gsmsg:write key="cmn.select" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="cmn120ButtonPush(2);">
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