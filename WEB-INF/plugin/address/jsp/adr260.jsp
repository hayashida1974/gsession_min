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
<title>GROUPSESSION <gsmsg:write key="cmn.select.label" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../address/js/adr260.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/readOnly.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/freeze.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
</head>

<body class="body_03" onload="freezeScreenParent('', false);parentReadOnly();init(<bean:write name="adr260Form" property="adr260initFlg" />);">
<html:form action="/address/adr260">

<input type="hidden" name="CMD" value="">
<html:hidden property="adr260initFlg" />

<html:hidden property="adr260parentLabelName" />

<logic:notEmpty name="adr260Form" property="adr260selectLabel">
<logic:iterate id="label" name="adr260Form" property="adr260selectLabel">
  <input type="hidden" name="adr260selectLabel" value="<bean:write name="label" />">
</logic:iterate>
</logic:notEmpty>

<div class="wrapper txt_r">
  <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="setParentLabel();">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
    <gsmsg:write key="cmn.ok" />
  </button>
  <button type="button" value="<gsmsg:write key="cmn.cancel" />" class="baseBtn" onClick="labelSelectClose();">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.ok" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.ok" />">
    <gsmsg:write key="cmn.cancel" />
  </button>
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.category" />
      </th>
      <td class="w75">
        <html:select property="selectCategory" onchange="buttonPush('init');" styleClass="w100">
         <html:optionsCollection name="adr260Form" property="categoryList" value="value" label="label"/>
        </html:select>
      </td>
    </tr>
  </table>

  <logic:notEmpty name="adr260Form" property="labelList">
    <table class="table-top">
      <logic:iterate id="labelData" name="adr260Form" property="labelList" indexId="idx">
        <tr class="js_listHover cursor_p">
          <td class="w5 js_tableTopCheck">
            <html:multibox name="adr260Form" property="adr260selectLabel" styleClass="check">
              <bean:write name="labelData" property="albSid" />
            </html:multibox>
          </td>
          <td class="w95 js_radio">
            <bean:write name="labelData" property="albName" />
          </td>
        </tr>
      </logic:iterate>
    </table>
  </logic:notEmpty>
 </div>

</html:form>

</body>
</html:html>