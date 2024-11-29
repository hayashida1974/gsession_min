<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.cmn999.Cmn999Form" %>

<!DOCTYPE html>


<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript"  src="../common/js/push.min.js?<%= GSConst.VERSION_PARAM %>"></script><script src='../common/js/cmn400.js?<%= GSConst.VERSION_PARAM %>'></script>
<gsjsmsg:js filename="gsjsmsg.js"/>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<title>GROUPSESSION <gsmsg:write key="cmn.cmn400.01" /></title>
</head>

<!-- body -->
<body>
<html:form action="/common/cmn400">
<input type="hidden" name="CMD" value="cmn400Back">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div  class="kanriPageTitle w70 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />">
      <img class="header_pluginImg" src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />">
    </li>
    <li><gsmsg:write key="cmn.cmn400.01" /></li>
   
    <li>
      <div>
        <button type="button" class="baseBtn js_backBtn" value="<gsmsg:write key="cmn.back" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" />
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" />
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w70 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  
  <table class="table-left mt0">
    <tbody><tr>
      <th class="w25 no_w">
        <gsmsg:write key="cmn.cmn400.02" />
      </th>
      <td class="w75">
        <gsmsg:write key="cmn.cmn400.03" /><br>
        <button type="button" class="baseBtn js_noticeBtn" value="<gsmsg:write key="cmn.cmn400.04" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_beru.png" />
          <img class="btn_originalImg-display" src="../common/images/original/icon_bell.png" />
          <gsmsg:write key="cmn.cmn400.04" />
        </button>
      </td>
    </tr>
  </tbody>
  </table>
  
  <div class="txt_l mt20 bgC_other1 bor1">
    <h4 class="cl_fontWarn"><gsmsg:write key="cmn.cmn400.05" /></h4>
    <ul class="pl10">
      <li class="mt10">
        <span class="fw_bold"><gsmsg:write key="cmn.cmn400.06" /></span>
        <div class="pl10">
          <gsmsg:write key="cmn.cmn400.07" /><br>
          <gsmsg:write key="cmn.cmn400.08" />
        </div>
      </li>
      <li class="mt15">
        <span class="fw_bold"><gsmsg:write key="cmn.cmn400.09" /></span>
        <div class="pl10">
          <gsmsg:write key="cmn.cmn400.10" />
        </div>
      </li>
      <li class="mt15">
        <span class="fw_bold"><gsmsg:write key="cmn.cmn400.11" /></span>
        <div class="pl10">
          <gsmsg:write key="cmn.cmn400.12" /><br>
          <gsmsg:write key="cmn.cmn400.13" />
        </div>
      </li>
    </ul>
  </div>
      
  
  <div class="footerBtn_block mt10">
    <button type="button" class="baseBtn js_backBtn" value="<gsmsg:write key="cmn.back" />">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" />
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" />
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>