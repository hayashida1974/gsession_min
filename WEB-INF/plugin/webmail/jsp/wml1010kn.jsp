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
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../webmail/css/webmail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
  <script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <title>GROUPSESSION</title>
</head>

<body>
<html:form action="/webmail/wml1010kn">
<input type="hidden" name="CMD" value="">

<!-- 前画面で設定されたプロパティを「戻る」で前画面へ引継ぐ為の定義 -->
<html:hidden property="wml1010initFlg" />
<html:hidden property="wml1010accountSid" />
<html:hidden property="wml1010saveWdrSid" />
<html:hidden property="wml1010labelSid" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.preferences2" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="wml.wml010.25" /></span>メールインポート(確認)
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doImp');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>

  <table class="table-left mt0">
    <tr>
      <th class="w0 no_w">インポートするアカウント</th>
      <td class="w100 txt_l">
        <logic:notEmpty name="wml1010knForm" property="wml1010knAccountString">
          <bean:write name="wml1010knForm" property="wml1010knAccountString" />
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w0 no_w">インポートするフォルダ</th>
      <td class="w100 txt_l">
        <logic:notEmpty name="wml1010knForm" property="wml1010saveWdrSid">
          <bean:write name="wml1010knForm" property="wml1010saveWdrSid" />
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th class="w0 no_w">追加するラベル</th>
      <td class="w100 txt_l">
        <logic:notEmpty name="wml1010knForm" property="wml1010knLabelString">
          <bean:write name="wml1010knForm" property="wml1010knLabelString" />
        </logic:notEmpty>
      </td>
    </tr>
  </table>

  <%@ page import="jp.groupsession.v2.wml.syspro.wml1010kn.Wml1010knMailModel" %>

  <table class="js_mailSearchListArea table-top mt0 mb0 lh100" cellpadding="0" cellspacing="0">
    <tr class="js_mailListHeaderLine">
      <th class="table_title-color js_tableTopCheck js_tableTopCheck-header w3">
        <input class="" type="checkbox" name="allCheck" onclick="" >
      </th>
      <th class="table_title-color w3 no_w">
        <img class="btn_classicImg-display hp15" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
      </th>
      <th class="table_title-color w20 no_w">差出人</th>
      <th class="table_title-color w50 no_w">件名</th>
      <th class="table_title-color wp100 no_w">日時</th>
      <th class="table_title-color wp80 no_w">サイズ</th>
    </tr>
    <logic:notEmpty name="wml1010knForm" property="wml1010knMailList" scope="request">
      <logic:iterate id="wml1010knMailList" name="wml1010knForm" property="wml1010knMailList" type="Wml1010knMailModel">
        <tr class="js_listHover js_mailContent-line">
          <td class="js_tableTopCheck txt_c">
            <bean:define id="fileName" name="wml1010knMailList" property="fileName" type="java.lang.String" />
            <html:checkbox name="wml1010knMailList" property="import" value="<%= fileName %>" indexed="true" />
          </td>
          <td class="js_mailContent js_mailListElm-file pl5 pr5 txt_c"
		      <logic:equal name="wml1010knMailList" property="attach" value="true">
		        <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file.gif" alt="<gsmsg:write key="cmn.attached" />">
		        <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
		      </logic:equal>
          </td>
          <td class="js_mailContent js_mailListElm pl5 pr5 word_b-all">
            <bean:write name="wml1010knMailList" property="from" />
          </td>
          <td class="js_mailContent lh150 js_mailListElm pl5 pr5 txt_l word_b-all">
            <bean:write name="wml1010knMailList" property="subject" />
          </td>
          <td class="js_mailContent js_mailListElm txt_c">
            <bean:write name="wml1010knMailList" property="strDate" />
          </td>
          <td class="js_mailContent js_mailListElm txt_c">
            <bean:write name="wml1010knMailList" property="size" />
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('doImp');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backInput');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>