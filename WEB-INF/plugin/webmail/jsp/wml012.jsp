<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <bean:define id="selectThemePath" value="" type="String"/>
  <logic:notEmpty name="wml012Form" property="wml010theme" scope="request">
    <bean:define id="selectThemePath" name="wml012Form" property="wml010theme" type="String"/>
    <theme:css filename="theme.css" selectthemepath="<%= selectThemePath %>" />
  </logic:notEmpty>
  <logic:empty name="wml012Form" property="wml010theme" scope="request">
    <theme:css filename="theme.css"/>
  </logic:empty>

  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

  <script src="../webmail/js/wml012.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body class="w98">

<bean:define id="wml012checkAddress" name="wml012Form" property="wml010checkAddress" type="java.lang.Integer" />
<bean:define id="wml012checkFile" name="wml012Form" property="wml010checkFile" type="java.lang.Integer" />
<% boolean chkAdrFlg = (wml012checkAddress == 1); %>
<% boolean chkFileFlg = (wml012checkFile == 1); %>

<div class="wrapper mrl_auto">
  <div class="bgC_header1 bor1 w98 pos_fix component_bothEnd top0">
    <div class="display_inline-block ml5 pos_rel">
      <span class="table_title-color fs_14"><gsmsg:write key="wml.wml012.02" /></span>
    </div>
    <div class="display_inline-block mr5 pos_rel">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.sent" />" onClick="window.parent.doCheckAddressSubmit();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei.png" alt="<gsmsg:write key="cmn.sent" />">
        <img class="btn_originalImg-display" src="../webmail/images/original/icon_sent.png" alt="<gsmsg:write key="cmn.sent" />">
        <gsmsg:write key="cmn.sent" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="wml.js.84" />" onClick="window.parent.doHandleCancel();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="wml.js.84" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="wml.js.84" />">
        <gsmsg:write key="wml.js.84" />
      </button>
    </div>
  </div>

  <form method='POST' action='../webmail/wml012.do'>
    <table class="table-left w100 sendKn_table">

      <% String[] adrTypeArray = {"To", "Cc", "Bcc"}; %>
      <% String[] domainColorList = {"Blue","Red","Green","Yellow","Black","Navy","Wine","Cien","Gray","Marine"}; %>
      <% for (int adressTypeIdx = 0; adressTypeIdx < adrTypeArray.length; adressTypeIdx++) {%>
        <tr>
          <th class="w20">
            <% if (adressTypeIdx == 0) {%><gsmsg:write key="cmn.from" />
            <% } else if (adressTypeIdx == 1) {%>CC
            <% } else if (adressTypeIdx == 2) {%>BCC
            <% } %>
          </th>

          <% String adrType = adrTypeArray[adressTypeIdx];  %>
          <% String adrListName = "wml012" + adrType + "List"; %>
          <% String adrListSizeName = "wml012" + adrType + "ListSize"; %>

          <td class="w80" id="check<%= adrType %>Area">
            <logic:notEmpty name="wml012Form" property="<%= adrListName %>">

              <% if (chkAdrFlg) { %>
                <logic:greaterThan name="wml012Form" property="<%= adrListSizeName %>" value="0">
                  <div class="pb5">
                    <a href="#" onClick="return wml012checkAll('check<%= adrType %>');" class="fs_12"><gsmsg:write key="cmn.check.all" /></a>
                  </div>
                </logic:greaterThan>
              <% } %>

              <logic:iterate id="sendAddress" name="wml012Form" property="<%= adrListName %>" indexId="adrIdx">
                <% if (adrIdx.intValue() > 0 ) { %><br><% } %>
                <span class="verAlignMid">
                  <% String checkName = "check" + adrType + adrIdx.toString(); %>
                  <% if (chkAdrFlg) { %><input type="checkbox" name="check<%= adrType %>" id="<%= checkName %>" class="mr5"><% } %>
                  <label for="<%= checkName %>" id="<%= checkName %>_label">
                    <logic:empty name="sendAddress" property="domain"><bean:write name="sendAddress" property="address" /></logic:empty>
                    <logic:notEmpty name="sendAddress" property="domain">

                      <bean:define id="domainTypeStr" name="sendAddress" property="domainType" type="java.lang.String" />
                      <%
                        String domainColor = "Blue";
                        if (domainTypeStr != null && domainTypeStr.length() > 0) {
                          int domainTypeNum = Integer.parseInt(domainTypeStr);
                          if (domainTypeNum > 0 && domainTypeNum <= 10) {
                            domainColor = domainColorList[domainTypeNum - 1];
                          }
                        }
                      %>

                      <bean:write name="sendAddress" property="addressPrev" /><bean:write name="sendAddress" property="user" />@<span class="cl_fontWmlTitle<%= domainColor %>"><bean:write name="sendAddress" property="domain" /></span><bean:write name="sendAddress" property="addressAfter" />
                    </logic:notEmpty>
                  </label>
                </span>
              </logic:iterate>
            </logic:notEmpty>
          </td>
        </tr>
      <% } %>

      <tr>
        <th class="w20 pr5"><gsmsg:write key="cmn.subject" /></th>
        <td id="wmlConfirmTitle" class="w80 word_b-all">
          <bean:write name="wml012Form" property="wml010sendSubject" />
        </td>
      </tr>
      <tr>
        <th class="w20 pr5"><gsmsg:write key="wml.211" /></th>
        <td id="wmlSendPlanDate" class="w80 word_b-all">
          <bean:write name="wml012Form" property="wml012SendPlanDate" />
        </td>
      </tr>
      <tr>
        <th class="w20 pr5"><gsmsg:write key="cmn.attach.file" /></th>
        <td id="checkFileArea" class="w80 word_b-all">
          <logic:notEmpty name="wml012Form" property="wml012TempfileList">
            <% if (chkFileFlg) { %>
              <logic:greaterThan name="wml012Form" property="wml012TempfileList" value="0">
                <div class="pb5">
                  <a href="#" onClick="return wml012checkAll('checkFile');" class="fs_12"><gsmsg:write key="cmn.check.all" /></a>
                </div>
              </logic:greaterThan>
            <% } %>

            <logic:iterate id="fileData" name="wml012Form" property="wml012TempfileList" indexId="fileIdx">
              <% if (fileIdx.intValue() > 0 ) { %><br><% } %>
              <span class="verAlignMid">
                <% String checkName = "checkFile" + fileIdx.toString(); %>
                <% if (chkFileFlg) { %><input type="checkbox" name="checkFile" id="<%= checkName %>" class="mr5"><% } %>
                <label for="<%= checkName %>" id="<%= checkName %>_label">
                  <bean:write name="fileData" property="fileName"/>
                  <span class="ml5"><bean:write name="fileData" property="fileSizeDsp"/></span>
                </label>
              </span>
            </logic:iterate>

            <logic:equal name="wml012Form" property="wml012compressTempfile" value="1">
              <div class="cl_fontWarn fw_b mt10"><gsmsg:write key="wml.compress.automatically.attachments" /></div>
            </logic:equal>
          </logic:notEmpty>
        </td>
      </tr>
      <tr>
        <td colspan="2" class="p5">
          <div class="bgC_body">
            <div id="wmlConfirmBodyArea">
              <htmlframe:write attrClass="w100"  themePath="<%=selectThemePath %>">
                <bean:write name="wml012Form" property="wml012ViewBody" filter="false"/>
              </htmlframe:write>
            </div>
          </div>
        </td>
      </tr>
    </table>
  </form>
</div>

</body>
</html:html>
