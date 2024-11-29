<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%
String orderAsc  = String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC);
String orderDesc = String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC);
%>

<%
String sortMsg  = String.valueOf(jp.groupsession.v2.man.man320.Man320Biz.SORT_KEY_MSG);
String sortFrDate  = String.valueOf(jp.groupsession.v2.man.man320.Man320Biz.SORT_KEY_FRDATE);
String sortToDate  = String.valueOf(jp.groupsession.v2.man.man320.Man320Biz.SORT_KEY_TODATE);
String sortUsrName  = String.valueOf(jp.groupsession.v2.man.man320.Man320Biz.SORT_KEY_USR_NAME);
%>

<html:html>
<head>

<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">

<title>GROUPSESSION <gsmsg:write key="main.man320.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../main/js/man320.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body>
<html:form action="/main/man320">

<input type="hidden" name="CMD" value="">
<html:hidden property="cmd" />
<html:hidden property="man320SortKey" />
<html:hidden property="man320OrderKey" />
<html:hidden property="man320PageNum" />
<html:hidden property="man320SelectedSid" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<!--BODY -->
<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="<gsmsg:write key="cmn.information" />">
      <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="<gsmsg:write key="cmn.information" />">
    </li>
    <li><gsmsg:write key="cmn.information" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.list" />
    </li>
    <li>
      <logic:notEmpty name="man320Form" property="man320DspList">
        <button type="button" class="baseBtn" value="<gsmsg:write key="man.purge" />" onClick="buttonPush('man320delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="man.purge" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="man.purge" />">
          <gsmsg:write key="man.purge" />
        </button>
      </logic:notEmpty>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.create.new" />" onClick="addMsg();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.create.new" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.create.new" />">
        <gsmsg:write key="cmn.create.new" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('320_back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>
<div class="wrapper">
  <logic:empty name="man320Form" property="man320DspList">
    <div class="mt10 txt_l">
      <gsmsg:write key="man.no.info.registered" />
    </div>
  </logic:empty>
  <logic:notEmpty name="man320Form" property="man320DspList">
    <div class="txt_l">
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
    </div>
    <bean:size id="count1" name="man320Form" property="man320PageLabel" scope="request" />
    <logic:greaterThan name="count1" value="1">
      <div class="paging">
        <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_left');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <logic:notEmpty name="man320Form" property="man320PageLabel">
          <html:select property="man320SltPage1" onchange="changePage1();" styleClass="paging_combo">
            <html:optionsCollection name="man320Form" property="man320PageLabel" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
        <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_right');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:greaterThan>
    <table class="table-top w100">
      <tr>
        <th class="table_title-color js_tableTopCheck js_tableTopCheck-header cursor_p">
          <input type="checkbox" name="allChk" onClick="changeChk();">
        </th>
        <th class="w45 cursor_p no_w table_header-evt js_table_header-evt table_title-color">
          <logic:equal name="man320Form" property="man320SortKey" value="<%= sortMsg %>">
            <logic:equal name="man320Form" property="man320OrderKey" value="<%= orderAsc %>">
              <a href="#!" onclick="return onTitleLinkSubmit('<%= sortMsg %>', '<%= orderDesc %>')">
                <gsmsg:write key="cmn.message" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
              </a>
            </logic:equal>
            <logic:equal name="man320Form" property="man320OrderKey" value="<%= orderDesc %>">
              <a href="#!" onclick="return onTitleLinkSubmit('<%= sortMsg %>', '<%= orderAsc %>')">
                <gsmsg:write key="cmn.message" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
              </a>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="man320Form" property="man320SortKey" value="<%= sortMsg %>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%= sortMsg %>', '<%= orderAsc %>')">
              <gsmsg:write key="cmn.message" />
            </a>
          </logic:notEqual>
        </th>
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt table_title-color">
          <logic:equal name="man320Form" property="man320SortKey" value="<%= sortFrDate %>">
            <logic:equal name="man320Form" property="man320OrderKey" value="<%= orderAsc %>">
              <a href="#!" onclick="return onTitleLinkSubmit('<%= sortFrDate %>', '<%= orderDesc %>')">
                <gsmsg:write key="main.man321.1" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
              </a>
            </logic:equal>
            <logic:equal name="man320Form" property="man320OrderKey" value="<%= orderDesc %>">
              <a href="#!" onclick="return onTitleLinkSubmit('<%= sortFrDate %>', '<%= orderAsc %>')">
                <gsmsg:write key="main.man321.1" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
              </a>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="man320Form" property="man320SortKey" value="<%= sortFrDate %>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%= sortFrDate %>', '<%= orderAsc %>')">
              <gsmsg:write key="main.man321.1" /></span>
            </a>
          </logic:notEqual>
        </th>
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt table_title-color">
          <logic:equal name="man320Form" property="man320SortKey" value="<%= sortToDate %>">
            <logic:equal name="man320Form" property="man320OrderKey" value="<%= orderAsc %>">
              <a href="#!" onclick="return onTitleLinkSubmit('<%= sortToDate %>', '<%= orderDesc %>')">
                <gsmsg:write key="main.man322.1" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
              </a>
            </logic:equal>
            <logic:equal name="man320Form" property="man320OrderKey" value="<%= orderDesc %>">
              <a href="#!" onclick="return onTitleLinkSubmit('<%= sortToDate %>', '<%= orderAsc %>')">
                <gsmsg:write key="main.man322.1" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
              </a>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="man320Form" property="man320SortKey" value="<%= sortToDate %>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%= sortToDate %>', '<%= orderAsc %>')">
              <gsmsg:write key="main.man322.1" /></span>
            </a>
          </logic:notEqual>
        </th>
        <th class="w10 no_w table_title-color">
          <gsmsg:write key="cmn.advanced.settings" />
        </th>
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt table_title-color">
          <logic:equal name="man320Form" property="man320SortKey" value="<%= sortUsrName %>">
            <logic:equal name="man320Form" property="man320OrderKey" value="<%= orderAsc %>">
              <a href="#!" onclick="return onTitleLinkSubmit('<%= sortUsrName %>', '<%= orderDesc %>')">
                <gsmsg:write key="main.man323.1" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
              </a>
            </logic:equal>
            <logic:equal name="man320Form" property="man320OrderKey" value="<%= orderDesc %>">
              <a href="#!" onclick="return onTitleLinkSubmit('<%= sortUsrName %>', '<%= orderAsc %>')">
                <gsmsg:write key="main.man323.1" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
              </a>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="man320Form" property="man320SortKey" value="<%= sortUsrName %>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%= sortUsrName %>', '<%= orderAsc %>')">
              <gsmsg:write key="main.man323.1" /></span>
            </a>
          </logic:notEqual>
        </th>
      </tr>
      <logic:iterate id="dspMdl" name="man320Form" property="man320DspList" indexId="idx">
        <tr class="js_listHover cursor_p" data-sid="<bean:write name="dspMdl" property="imsSid" />">
          <td class="no_w js_tableTopCheck">
            <bean:define id="sid" name="dspMdl" property="imsSid" type="java.lang.Integer" />
            <html:multibox property="selectMsg" value="<%= Integer.toString(sid.intValue()) %>"/>
          </td>
          <td class="txt_l js_listClick">
            <logic:equal name="dspMdl" property="kigenChFlg" value="1">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
            </logic:equal>
            <span class="textLink"><bean:write name="dspMdl" property="imsMsg" /></span>
          </td>
          <td class="txt_c js_listClick no_w">
            <bean:write name="dspMdl" property="frDate" />
          </td>
          <td class="txt_c js_listClick no_w">
            <logic:equal name="dspMdl" property="kigenChFlg" value="1">
              <span class="cl_fontWarn"><bean:write name="dspMdl" property="toDate" /></span>
            </logic:equal>
            <logic:notEqual name="dspMdl" property="kigenChFlg" value="1">
              <bean:write name="dspMdl" property="toDate" />
            </logic:notEqual>
          </td>
          <td class="txt_c js_listClick no_w">
            <bean:write name="dspMdl" property="exString"  />
          </td>
          <td class="txt_l js_listClick no_w">
            <logic:equal name="dspMdl" property="usrJkbn" value="9">
              <del>
                <bean:write name="dspMdl" property="usrNameSei" />&nbsp;<bean:write name="dspMdl" property="usrNameMei" />
              </del>
            </logic:equal>
            <logic:notEqual name="dspMdl" property="usrJkbn" value="9">
              <bean:write name="dspMdl" property="usrNameSei" />&nbsp;<bean:write name="dspMdl" property="usrNameMei" />
            </logic:notEqual>
          </td>
        </tr>
      </logic:iterate>
    </table>
    <bean:size id="count2" name="man320Form" property="man320PageLabel" scope="request" />
    <logic:greaterThan name="count2" value="1">
      <div class="paging">
        <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_left');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <logic:notEmpty name="man320Form" property="man320PageLabel">
          <html:select property="man320SltPage2" onchange="changePage2();" styleClass="paging_combo">
            <html:optionsCollection name="man320Form" property="man320PageLabel" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
        <button type="button" class="webIconBtn" onclick="buttonPush('arrorw_right');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:greaterThan>
  </logic:notEmpty>
</div>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>