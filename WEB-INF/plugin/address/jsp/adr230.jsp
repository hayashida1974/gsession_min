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
<title>GROUPSESSION <gsmsg:write key="cmn.select.company" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../address/js/adr230.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body class="body_03">
<html:form action="/address/adr230">
<input type="hidden" name="CMD" value="">
<html:hidden property="adr230SortKey" />
<html:hidden property="adr230OrderKey" />

<div class="pageTitle mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.select.company" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />"  onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>


<div class="wrapper">
  <logic:notEmpty name="adr230Form" property="adr230PageList">
    <div class="paging">
      <button type="button" class="webIconBtn" onClick="buttonPush('pageleft');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select styleClass="paging_combo" name="adr230Form" property="adr230PageTop" onchange="return changePage('0');">
        <html:optionsCollection name="adr230Form" property="adr230PageList" value="value" label="label" />
      </html:select>
      <button type="button" class="webIconBtn" onClick="buttonPush('pageright');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:notEmpty>
  <table class="table-top">
    <tr>
      <th class="w30">
        <logic:equal name="adr230Form" property="adr230SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_CODE) %>">
          <logic:equal name="adr230Form" property="adr230OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_CODE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
              <span class="classic-display"><gsmsg:write key="address.7" />▲</span>
              <span class="original-display txt_m"><gsmsg:write key="address.7" /><i class="icon-sort_up"></i></span>
            </a>
          </logic:equal>
          <logic:equal name="adr230Form" property="adr230OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_CODE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
              <span class="classic-display"><gsmsg:write key="address.7" />▼</span>
              <span class="original-display txt_m"><gsmsg:write key="address.7" /><i class="icon-sort_down"></i></span>
            </a>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="adr230Form" property="adr230SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_CODE) %>">
          <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_CODE) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="address.7" />
          </a>
        </logic:notEqual>
      </th>
      <th class="w70">
        <logic:equal name="adr230Form" property="adr230SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>">
          <logic:equal name="adr230Form" property="adr230OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▲</span>
              <span class="original-display txt_m"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_up"></i></span>
            </a>
          </logic:equal>
          <logic:equal name="adr230Form" property="adr230OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
            <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▼</span>
              <span class="original-display txt_m"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_down"></i></span>
            </a>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="adr230Form" property="adr230SortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>">
          <a href="#" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.COMPANY_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="cmn.company.name" />
          </a>
        </logic:notEqual>
      </th>
    </tr>
    <logic:notEmpty name="adr230Form" property="adr230CompanyList">
      <logic:iterate id="comList" name="adr230Form" property="adr230CompanyList" indexId="idx">
        <tr class="js_listHover cursor_p" id="<bean:write name="comList" property="acoCode" />">
          <td class="cl_linkDef js_listClick">
            <bean:write name="comList" property="acoCode" />
          </td>
          <td class="js_listClick">
            <bean:write name="comList" property="acoName" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <logic:notEmpty name="adr230Form" property="adr230PageList">
    <div class="paging">
      <button type="button" class="webIconBtn" onClick="buttonPush('pageleft');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select styleClass="paging_combo" name="adr230Form" property="adr230PageBottom" onchange="return changePage('1');">
        <html:optionsCollection name="adr230Form" property="adr230PageList" value="value" label="label" />
      </html:select>
      <button type="button" class="webIconBtn" onClick="buttonPush('pageright');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:notEmpty>
</div>

</html:form>
</body>
</html:html>