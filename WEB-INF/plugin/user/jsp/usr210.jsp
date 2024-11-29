<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.select.label" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="../common/js/jquery-1.5.2.min.js?500"></script>
<script type="text/javascript" src="../user/js/usr210.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/readOnly.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/freeze.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body onload="freezeScreenParent('', false);parentReadOnly();init(<bean:write name="usr210Form" property="usr210initFlg" />);">
  <html:form action="/user/usr210">

    <input type="hidden" name="CMD" value="">
    <html:hidden property="usr210initFlg" />
    <html:hidden property="usr210parentLabelName" />

    <logic:notEmpty name="usr210Form" property="usr210selectLabel">
      <logic:iterate id="label" name="usr210Form" property="usr210selectLabel">
        <input type="hidden" name="usr210selectLabel" value="<bean:write name="label" />">
      </logic:iterate>
    </logic:notEmpty>

    <div class="txt_r">
      <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="setParentLabel();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <button type="button" value="<gsmsg:write key="cmn.cancel" />" class="baseBtn" onclick="labelSelectClose();">
         <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.cancel" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.cancel" />">
        <gsmsg:write key="cmn.cancel" />
      </button>
    </div>

    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>

    <tr>
      <table class="table-left" cellpadding="5" cellspacing="0" border="0" width="100%">
        <tr>
          <td class="w20 no_w">
            <span>
              <gsmsg:write key="user.47" />
            </span>
          </td>
          <td class="txt_l w80">
            <html:select name="usr210Form" property="categorySid" onchange="changeCategory('changeCategory');">
              <html:optionsCollection name="usr210Form" property="categoryList" value="value" label="label" />
            </html:select>
          </td>
        </tr>
      </table>
    </tr>

    <logic:notEmpty name="usr210Form" property="labelList">
      <tr>
        <td>
          <br>
          <table class="table-top" cellpadding="0" cellspacing="0">
            <%
              int count = 0;
            %>
            <%
              String labelNo = "labelNo";
            %>
            <logic:iterate id="labelData" name="usr210Form" property="labelList" indexId="idx">
              <tr class="js_listHover cursor_p">
                <td class="js_tableTopCheck">
                  <html:multibox name="usr210Form" property="usr210selectLabel" styleId="<%=labelNo + Integer.toString(count)%>" styleClass="check">
                    <bean:write name="labelData" property="labSid" />
                  </html:multibox>
                </td>
                <td class="w100 js_radio">
                  <bean:write name="labelData" property="labName" />
                </td>
              </tr>
              <% count++; %>
            </logic:iterate>
          </table>
        </td>
      </tr>
    </logic:notEmpty>
    </table>
  </html:form>
</body>
</html:html>