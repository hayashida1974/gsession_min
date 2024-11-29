<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.adr.GSConstAddress" %>
<%@ page import="jp.groupsession.v2.adr.adr010.Adr010Const" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.addressbook" /></title>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../address/css/address.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<gsjsmsg:js filename="gsjsmsg.js"/>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adrcommon.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr330.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>



</head>


<body class="body_03" onunload="calWindowClose();">
<div id="FreezePane">

<html:form action="/address/adr330">
<html:hidden property="backScreen" />

<jsp:include page="/WEB-INF/plugin/address/jsp/adr010_roopHiddenParams.jsp" >
<jsp:param value="adr330Form" name="thisFormName"/>
</jsp:include>
<jsp:include page="/WEB-INF/plugin/address/jsp/adr010_hiddenParams.jsp" />




<span id="adr010labelArea">
</span>

<jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp"  />

<input type="hidden" name="CMD" value="">

<html:hidden property="adr330searchFlg" />
<html:hidden property="adr330back" value="1" />

<%--前回検索パラメータ --%>
<jsp:include page="/WEB-INF/plugin/address/jsp/adr330_searchHiddenParams.jsp" >
<jsp:param value="adr330Form" name="thisFormName"/>
<jsp:param value="adr330searchSVBean" name="searchParamName"/>
</jsp:include>

<input type="hidden" name="adr020ProcMode" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_ADD) %>">


<input type="hidden" name="adr100backFlg" value="">
<input type="hidden" name="adr110ProcMode" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT) %>">
<input type="hidden" name="adr110editAcoSid" value="">

<%
  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(request);
%>




<div class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.addressbook" /></span><gsmsg:write key="address.adr330.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="return buttonPush('adr330back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <jsp:include page="/WEB-INF/plugin/address/jsp/adr330_detailed.jsp" >
    <jsp:param value="adr330searchBean" name="searchParamName"/>
  </jsp:include>
  <bean:define id="beanName" value="adr330searchBean" />
    <%-- ソート項目 --%>
  <html:hidden property="<%=beanName + \".sortKey\" %>" />
    <%-- 並び順 --%>
  <html:hidden property="<%=beanName + \".orderKey\" %>" />
    <%-- ページ --%>
  <html:hidden property="<%=beanName + \".page\" %>" />

  <logic:notEmpty name="adr330Form" property="detailList">
    <div class="flo_l w70 txt_l mt5">
      <div class="pageTitle_sub">
        <span class="sub_title"><gsmsg:write key="cmn.search.criteria" /></span>
      </div>
      <logic:empty name="adr330Form" property="adr010searchParamString">
        <% String allSearchOth = gsMsg.getMessage(request, "address.adr010.7"); %>
        <gsmsg:write key="address.38" arg0="<%= allSearchOth %>" />
      </logic:empty>
      <logic:notEmpty name="adr330Form" property="adr010searchParamString">
        <bean:define id="searchPrmOth" name="adr330Form" property="adr010searchParamString" type="java.lang.String" />
        <gsmsg:write key="address.38" arg0="<%= searchPrmOth %>" />
      </logic:notEmpty>
    </div>
    <div class="flo_l w30 txt_r mt5">
      <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('adrDelete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <button type="button" value="<gsmsg:write key="cmn.export" />" class="baseBtn" onClick="buttonPush('export');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
        <gsmsg:write key="cmn.export" />
      </button>
    </div>
    <logic:notEmpty name="adr330Form" property="pageCmbList">
      <div class="paging mt35">
        <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  name="adr330Form" property="adr330searchSVBean.page" onchange="changePage(this.value);">
          <html:optionsCollection name="adr330Form" property="pageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:notEmpty>

    <jsp:include page="/WEB-INF/plugin/address/jsp/adr330address.jsp" />

    <logic:notEmpty name="adr330Form" property="pageCmbList">
      <div class="paging">
        <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  name="adr330Form" property="adr330searchSVBean.page" onchange="changePage(this.value);">
          <html:optionsCollection name="adr330Form" property="pageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:notEmpty>
  </logic:notEmpty>
</div>
</html:form>
<jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp"/>
</div>
</body>
</html:html>