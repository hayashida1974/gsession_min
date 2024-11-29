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
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr190.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>" </script>
<script type="text/javascript" src="../common/js/readOnly.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/freeze.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body class="body_03" onload="freezeScreenParent('', false);init(<bean:write name="adr190Form" property="adr190initFlg" />);">
<html:form action="/address/adr190">

<input type="hidden" name="CMD" value="">
<html:hidden property="adr190initFlg" />
<html:hidden property="adr190parentLabelName" />
<input type="hidden" name="hiddenLabel" value="">

<logic:notEmpty name="adr190Form" property="adr190selectLabel">
<logic:iterate id="label" name="adr190Form" property="adr190selectLabel">
  <input type="hidden" name="adr190selectLabel" value="<bean:write name="label" />">
</logic:iterate>
</logic:notEmpty>


<div class="wrapper txt_r mt10">
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
      <th class="w25 no_w">
        <gsmsg:write key="cmn.category" />
      </th>
      <td class="w75">
        <html:select property="adr190selectCategory" onchange="buttonPush('init');">
          <html:optionsCollection name="adr190Form" property="adr190category" value="value" label="label" />
        </html:select>
      </td>
    </tr>
  </table>
  <logic:notEmpty name="adr190Form" property="labelList">
    <table class="table-top">
      <logic:iterate id="labelData" name="adr190Form" property="labelList" indexId="idx">
        <tr class="js_listHover cursor_p">
          <td class="w5 txt_c js_tableTopCheck">
            <html:multibox name="adr190Form" property="adr190selectLabel" styleClass="check">
              <bean:write name="labelData" property="albSid" />
            </html:multibox>
          </td>
          <td class="js_chk">
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