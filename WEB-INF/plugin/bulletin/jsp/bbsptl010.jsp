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
<title>GROUPSESSION <gsmsg:write key="bbs.9" /> <gsmsg:write key="portal.portal" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../bulletin/js/bbsptl010.js?<%= GSConst.VERSION_PARAM %>"></script>
<bean:define id="bbsBfiSid" name="bbsptl010Form" property="bbsPtlBfiSid" type="java.lang.Integer" />
<% String bbsFormId = "bbsptl010Form" + String.valueOf(bbsBfiSid.intValue()); %>
<bean:define id="bbsPageNum" name="bbsptl010Form" property="bbsPtl010page1" type="java.lang.Integer" />
<% String bbsPage = String.valueOf(bbsPageNum.intValue()); %>
</head>
<body>
<html:form action="/bulletin/bbsptl010" styleId="<%= bbsFormId %>">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="bbsptl010Form" proeprty="bbsPtl010page1">
<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>
<html:hidden property="bbsPtlBfiSid" />
<html:hidden property="bbsPtl010ItemId" />
<!--掲示板-->
<logic:notEmpty name="bbsptl010Form" property="threadList">

<table class="table-top table_col-even w100 mb0">
  <tr>
    <th class="table_title-color txt_l" colspan=3>
      <img class="mainPlugin_icon" src="../bulletin/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.bulletin" />">
      <a href="<bean:write name="bbsptl010Form" property="bbsTopUrl" />">
        <gsmsg:write key="cmn.bulletin" /><span class="pl5">[ <bean:write name="bbsptl010Form" property="bbsPtlBfiName" /> ]</span>
      </a>
      <bean:size id="count1" name="bbsptl010Form" property="bbsPageLabel" scope="request" />
      <logic:greaterThan name="count1" value="1">
        <div class="paging flo_r">
          <button type="button" class="webIconBtn" onClick="bbsPtl010buttonPush('prevPage', '<%= bbsFormId %>');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left ptl_moveIcon"></i>
          </button>
          <select size="1" name="bbsPtl010page1" onchange="bbsPtl010changePage(0, '<%= bbsFormId %>');" class="paging_combo">
            <logic:iterate id="labelBean" name="bbsptl010Form" property="bbsPageLabel" scope="request">
              <logic:equal name="labelBean" property="value" value="<%= bbsPage %>">
                <option value="<bean:write name="labelBean" property="value" />" selected="selected"><bean:write name="labelBean" property="label" /></option>
              </logic:equal>
              <logic:notEqual name="labelBean" property="value" value="<%= bbsPage %>">
                <option value="<bean:write name="labelBean" property="value" />"><bean:write name="labelBean" property="label" /></option>
              </logic:notEqual>
            </logic:iterate>
          </select>
          <button type="button" class="webIconBtn" onClick="bbsPtl010buttonPush('nextPage', '<%= bbsFormId %>');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right ptl_moveIcon"></i>
          </button>
        </div>
      </logic:greaterThan>
    </th>
  </tr>
  <tr>
    <th class="w50 bgC_header2 cl_fontBody no_w p0 pl5 pr5">
      <gsmsg:write key="bbs.bbsMain.4" />
    </th>
    <th class="w20 bgC_header2 cl_fontBody no_w p0 pl5 pr5">
      <gsmsg:write key="cmn.contributor" />
    </th>
    <th class="w30 bgC_header2 cl_fontBody no_w p0 pl5 pr5">
      <gsmsg:write key="bbs.bbs060.3" />
    </th>
  </tr>
  <logic:iterate id="thdMdl" name="bbsptl010Form" property="threadList" indexId="index">
    <bean:define id="userJtkbn" name="thdMdl" property="userJkbn" />
    <tr class="js_listHover" data-url="../bulletin/bbs060.do?bbs010forumSid=<bean:write name="bbsptl010Form" property="bbsPtlBfiSid" />&threadSid=<bean:write name="thdMdl" property="btiSid" />&bbsmainFlg=1">
      <td class="js_lisBbsPtlClick cursor_p">
        <logic:equal name="thdMdl" property="newFlg" value="1">
          <%if (originalTheme) { %>
            <span class="labelNew"><gsmsg:write key="bbs.bbsMain.6" /></span>
          <% } else { %>
            <img class="btn_classicImg-display" src="../file/images/classic/icon_new_folder.gif">
          <% } %>
        </logic:equal>
        <% String titleClass = "cl_linkDef"; %>
        <logic:equal name="thdMdl" property="readFlg" value="1">
          <% titleClass = "cl_linkVisit"; %>
        </logic:equal>
        <span class="<%= titleClass %>">
          <logic:equal name="thdMdl" property="bfiThreImportance" value="1">
            <!-- 重要度 -->
            <img class="btn_classicImg-display" src="../common/images/classic/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
          </logic:equal>
          <logic:equal name="thdMdl" property="btsTempflg" value="1">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file.gif" alt="<gsmsg:write key="cmn.attach.file" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attach.file" />">
          </logic:equal>
          <bean:write name="thdMdl" property="btiTitle" />
        </span>
      </td>
      <td class="js_lisBbsPtlClick cursor_p">
        <bean:define id="cbGrpSid" name="thdMdl" property="grpSid" type="java.lang.Integer" />
        <% if (cbGrpSid.intValue() > 0) { %>
          <logic:equal name="thdMdl" property="grpJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
            <s><bean:write name="thdMdl" property="grpName" /></s>
          </logic:equal>
          <logic:notEqual name="thdMdl" property="grpJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
            <bean:write name="thdMdl" property="grpName" />
          </logic:notEqual>
        <% } else { %>
          <logic:equal name="thdMdl" property="userJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
            <s><bean:write name="thdMdl" property="userName" /></s>
          </logic:equal>
          <logic:notEqual name="thdMdl" property="userJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
            <logic:equal value="1" name="thdMdl" property="userYukoKbn">
              <span class="mukoUser"><bean:write name="thdMdl" property="userName" /></span>
            </logic:equal>
            <logic:notEqual value="1" name="thdMdl" property="userYukoKbn">
              <bean:write name="thdMdl" property="userName" />
            </logic:notEqual>
          </logic:notEqual>
        <% } %>
      </td>
      <td class="js_lisBbsPtlClick cursor_p txt_c">
        <bean:write name="thdMdl" property="strWriteDate" />
      </td>
    </tr>
  </logic:iterate>
</table>
</logic:notEmpty>


</html:form>


</body>
</html:html>