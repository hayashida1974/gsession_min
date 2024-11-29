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
<title>GROUPSESSION <gsmsg:write key="user.44" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../user/js/usr010.js?<%=GSConst.VERSION_PARAM%>"></script>
<theme:css filename="theme.css" />

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../user/css/user.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
</head>

<body onload="exportExecute();">
  <html:form action="/user/usr010">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="usr010grpmode" value="">
    <input type="hidden" name="usr010grpSid" value="-1">
    <html:hidden property="grpCsvOut" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shain.info" /></span><gsmsg:write key="user.44" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn btn_classicImg-display" value="<gsmsg:write key="main.man002.24" />" onClick="return buttonPush('userEdit');">
            <img class="img-18" src="../common/images/classic/icon_user.png" alt="<gsmsg:write key="main.man002.24" />">
            <gsmsg:write key="main.man002.24" />
            </button>
             <button type="button" class="baseBtn wp110 btn_originalImg-display" value="<gsmsg:write key="main.man002.24" />" onClick="return buttonPush('userEdit');">
            <img src="../common/images/original/icon_user.png" alt="<gsmsg:write key="main.man002.24" />">
            <gsmsg:write key="main.man002.24" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <div class="mb5">
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
      </div>

      <table class="w100 bor1 bgC_body">
        <tr class="bgC_header1">
          <td class="w100" colspan="2">
            <div class="txt_l pt5 pb5">
              <span class="fs_18 table_title-color ml5">
                <gsmsg:write key="cmn.grouplist" />
              </span>
            </div>
          </td>
        </tr>
        <tr class="bgC_header2">
          <td class="w100 txt_t p5">
          <div class="bor1 hp302">
            <iframe src="../user/usr020.do" class="hp300 w100" name="ctgFrame" frameborder="0">
              <gsmsg:write key="user.32" />
            </iframe>
            </div>
          </td>
          <td class="txt_t p5">
            <button type="button" class="baseBtn wp150 mb5" value="<gsmsg:write key="user.37" />" onClick="return usr010ChahgeProcess(this.form, 'add'), getChgctg();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="user.37" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="user.37" />">
              <gsmsg:write key="user.37" />
            </button>
            <button type="button" class="baseBtn wp150 mb5" value="<gsmsg:write key="user.133" />" onClick="return  usr010ChahgeProcess(this.form, 'edit'), getChgctg();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="user.133" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="user.133" />">
              <gsmsg:write key="user.133" />
            </button>
            <button type="button" class="baseBtn wp150 mb5" value="<gsmsg:write key="user.43" />" onClick="return usr010ChahgeProcess(this.form, 'uview'), getChgctg();">
              <img class="classic-display img-18" src="../common/images/classic/icon_user.png" alt="<gsmsg:write key="user.43" />">
              <img class="original-display" src="../common/images/original/icon_user.png" alt="<gsmsg:write key="user.43" />">
              <gsmsg:write key="user.43" />
            </button>
            <button type="button" class="baseBtn wp150 mb5" value="<gsmsg:write key="cmn.import" />" onClick="return usr010ChahgeProcess(this.form, 'groupImp'), getChgctg();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <gsmsg:write key="cmn.import" />
            </button>
            <button type="button" class="baseBtn wp150" value="<gsmsg:write key="cmn.export" />" onClick="return usr010ChahgeProcess(this.form, 'groupExp'), getChgctg();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
              <gsmsg:write key="cmn.export" />
            </button>
          </td>
        </tr>
      </table>

    </div>
    </div>

  </html:form>
  <IFRAME type="hidden" src="../common/html/damy.html" class="display_n" name="navframe"></IFRAME>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>