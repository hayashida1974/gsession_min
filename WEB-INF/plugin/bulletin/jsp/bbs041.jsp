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
<title>GROUPSESSION <gsmsg:write key="bbs.bbs041.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/search.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bulletin/js/bbs041.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/bulletin/bbs041">
    <input type="hidden" name="CMD" value="">
    <html:hidden name="bbs041Form" property="s_key" />
    <html:hidden name="bbs041Form" property="bbs010page1" />
    <html:hidden name="bbs041Form" property="bbs010forumSid" />
    <html:hidden name="bbs041Form" property="bbs060page1" />
    <html:hidden name="bbs041Form" property="searchDspID" />
    <html:hidden name="bbs041Form" property="threadSid" />
    <html:hidden name="bbs041Form" property="bbs040forumSid" />
    <html:hidden name="bbs041Form" property="bbs040keyKbn" />
    <html:hidden name="bbs041Form" property="bbs040taisyouThread" />
    <html:hidden name="bbs041Form" property="bbs040taisyouNaiyou" />
    <html:hidden name="bbs041Form" property="bbs040userName" />
    <html:hidden name="bbs041Form" property="bbs040readKbn" />
    <html:hidden name="bbs041Form" property="bbs040publicStatusOngoing" />
    <html:hidden name="bbs041Form" property="bbs040publicStatusScheduled" />
    <html:hidden name="bbs041Form" property="bbs040publicStatusOver" />
    <html:hidden name="bbs041Form" property="bbs040dateNoKbn" />
    <html:hidden name="bbs041Form" property="bbs040fromYear" />
    <html:hidden name="bbs041Form" property="bbs040fromMonth" />
    <html:hidden name="bbs041Form" property="bbs040fromDay" />
    <html:hidden name="bbs041Form" property="bbs040toYear" />
    <html:hidden name="bbs041Form" property="bbs040toMonth" />
    <html:hidden name="bbs041Form" property="bbs040toDay" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <!-- BODY -->

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../bulletin/images/classic/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
          <img class="header_pluginImg" src="../bulletin/images/original/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
        </li>
        <li>
          <gsmsg:write key="cmn.bulletin" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="bbs.bbs041.1" />
        </li>
        <li>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backPage');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </li>
      </ul>
    </div>

    <logic:notEmpty name="bbs041Form" property="resultList">
      <bean:size id="count1" name="bbs041Form" property="bbsPageLabel" scope="request" />
      <logic:greaterThan name="count1" value="1">
        <div class="txt_r paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="bbs041page1" onchange="changePage(0);">
            <html:optionsCollection name="bbs041Form" property="bbsPageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>
    </logic:notEmpty>

    <table class="table-top">
      <tr>
        <th>
          <gsmsg:write key="cmn.search.result" />
        </th>
      </tr>
      <logic:notEmpty name="bbs041Form" property="resultList">
        <logic:iterate id="resultMdl" name="bbs041Form" property="resultList" indexId="idx">
          <tr>
            <td>
            <div class="display_inline w100">
              <div>
                <img class="classic-display" src="../bulletin/images/classic/icon_thread.gif" alt="<gsmsg:write key="bbs.2" />">
              </div>
              <div class="ml5 w100 pt5">
                <a class="cl_linkDef fw_bold" onclick= "clickResult(<bean:write name="resultMdl" property="bfiSid" />, <bean:write name="resultMdl" property="btiSid" />);">
                  <!-- 重要度 -->
                  <logic:equal name="resultMdl" property="bfiThreImportance" value="1">
                    <img class="classic-display" src="../common/images/classic/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                    <img class="original-display" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                  </logic:equal>
                  <logic:equal name="resultMdl" property="btsTempflg" value="1">
                    <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attach.file" />">
                    <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attach.file" />">
                  </logic:equal>
                  <bean:write name="resultMdl" property="btiTitle" />
                </a>
                <logic:equal name="resultMdl" property="btiLimit" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.THREAD_LIMIT_YES)%>">
                  <logic:equal name="resultMdl" property="threadFinFlg" value="1">
                    <span class="flo_r cl_fontWarn">
                      <gsmsg:write key="bbs.12" />
                      ：<bean:write name="resultMdl" property="strBtiLimitDate" />迄
                    </span>
                  </logic:equal>
                  <logic:equal name="resultMdl" property="threadFinFlg" value="0">
                    <span class="flo_r">
                      <gsmsg:write key="bbs.12" />：<bean:write name="resultMdl" property="strBtiLimitDate" />迄
                    </span>
                  </logic:equal>
                </logic:equal>
                <div class="mt5 mb5">
                  <logic:equal name="resultMdl" property="bwiType" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN)%>">
                    <bean:write name="resultMdl" property="bwiValueView" />
                  </logic:equal>
                  <logic:equal name="resultMdl" property="bwiType" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.CONTENT_TYPE_TEXT_HTML)%>">
                    <bean:write name="resultMdl" property="bwiValuePlainView" />
                  </logic:equal>
                </div>
                <bean:define id="yukoFlg" name="resultMdl" property="userYukoKbn" type="java.lang.Integer" />
                <div class="component_bothEnd">
                <%
                  if (yukoFlg.intValue() == 1) {
                %>
                <span class="mukoUser">
                  <bean:write name="resultMdl" property="userName" />
                </span>
                <%
                  } else {
                %>
                <div class="fs_13">
                  <bean:write name="resultMdl" property="userName" />
                </div>
                <%
                  }
                %>
                <div class="txt_r fs_13">
                  <gsmsg:write key="bbs.bbs041.4" />
                  ：
                  <bean:write name="resultMdl" property="strWriteDate" />
                </div>
                </div>
              </div>
              </div>
            </td>
          </tr>
        </logic:iterate>
      </logic:notEmpty>
    </table>

    <logic:notEmpty name="bbs041Form" property="resultList">
      <bean:size id="count2" name="bbs041Form" property="bbsPageLabel" scope="request" />
      <table class="w100">
        <logic:notEqual name="bbs041Form" property="bbs041searchUse" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_NOT_USE)%>">
          <logic:notEmpty name="bbs041Form" property="s_key">
            <tr>
              <td>
                <bean:define id="searchKeyword" name="bbs041Form" property="s_key" type="java.lang.String" />
                <a href="#!" onClick="webSearch('<bean:write name="bbs041Form" property="bbs041WebSearchWord" />');">
                  <span class="cl_linkDef">
                    <gsmsg:write key="cmn.websearch" arg0="<%=searchKeyword%>" />
                  </span>
                </a>
              </td>
            </tr>
          </logic:notEmpty>
        </logic:notEqual>
      </table>
    </logic:notEmpty>

    <div class="w100 txt_r">
      <logic:greaterThan name="count2" value="1">
        <div class="txt_r paging mb5">
          <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="bbs041page2" onchange="changePage(1);">
            <html:optionsCollection name="bbs041Form" property="bbsPageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>
      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backPage');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>
