<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.usr.GSConstUser"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="main.man002.24" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../user/js/usr030.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/calendar.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../user/css/user.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body onload="exportExecute();">

  <html:form action="/user/usr030">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="processMode" />
    <html:hidden property="csvOut" />
    <html:hidden property="usr030cmdMode" />
    <html:hidden property="usr030SearchFlg" />
    <html:hidden property="selectgsidSave" />
    <html:hidden property="usr030usrUkoFlgSave" />
    <html:hidden property="usr030userIdSave" />
    <html:hidden property="usr030shainnoSave" />
    <html:hidden property="usr030seiSave" />
    <html:hidden property="usr030meiSave" />
    <html:hidden property="usr030seiknSave" />
    <html:hidden property="usr030meiknSave" />
    <html:hidden property="usr030agefromSave" />
    <html:hidden property="usr030agetoSave" />
    <html:hidden property="usr030yakushokuSave" />
    <html:hidden property="usr030mailSave" />
    <html:hidden property="usr030tdfkCdSave" />
    <html:hidden property="usr030seibetuSave" />
    <html:hidden property="usr030entranceYearFrSave" />
    <html:hidden property="usr030entranceMonthFrSave" />
    <html:hidden property="usr030entranceDayFrSave" />
    <html:hidden property="usr030entranceYearToSave" />
    <html:hidden property="usr030entranceMonthToSave" />
    <html:hidden property="usr030entranceDayToSave" />

    <logic:equal name="usr030Form" property="usr030cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NAME)%>">
      <input type="hidden" name="helpPrm" value="0">
    </logic:equal>

    <logic:equal name="usr030Form" property="usr030cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_SHOUSAI)%>">
      <input type="hidden" name="helpPrm" value="1">
    </logic:equal>


    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w85 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shain.info" /></span><gsmsg:write key="main.man002.24" />
        </li>
        <li>
          <div>
            <button type="button" name="btn_prjadd" class="baseBtn btn_classicImg-display" value="<gsmsg:write key="user.44" />" onClick="buttonPush('groupEdit');">
              <img class="img-18" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="user.44" />">
              <gsmsg:write key="user.44" />
            </button>
            <button type="button" name="btn_prjadd" class="btn_originalImg-display baseBtn wp120" value="<gsmsg:write key="user.44" />" onClick="buttonPush('groupEdit');">
              <img src="../common/images/original/icon_group.png" alt="<gsmsg:write key="user.44" />">
              <gsmsg:write key="user.44" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w85 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <ul class="tabHeader w100">
        <logic:equal name="usr030Form" property="usr030cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NAME)%>">
          <li class="tabHeader_tab-on bgC_lightGray  mwp100 pl10 pr10 js_tab border_bottom_none bgI_none" onclick="changeTab('name');"><gsmsg:write key="cmn.name2" /></li>
          <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab" onclick="changeTab('shousai');"><gsmsg:write key="cmn.advanced.search" /></li>
          <li class="tabHeader_space"></li>
        </logic:equal>
        <logic:equal name="usr030Form" property="usr030cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_SHOUSAI)%>">
          <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab" onclick="changeTab('name');"><gsmsg:write key="cmn.name2" /></li>
          <li class="tabHeader_tab-on bgC_lightGray  mwp100 pl10 pr10 js_tab border_bottom_none bgI_none" onclick="changeTab('shousai');"><gsmsg:write key="cmn.advanced.search" /></li>
          <li class="tabHeader_space"></li>
        </logic:equal>
      </ul>

      <!-- ユーザ管理-->
      <table class="w100 txt_c bor_r1 bor_l1 bor_b1">
        <tr>
          <td class="w100 txt_t p0">
            <!-- 氏名 --> <logic:equal name="usr030Form" property="usr030cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NAME)%>">
              <%@ include file="/WEB-INF/plugin/user/jsp/usr030_sub01.jsp"%>
            </logic:equal> <!-- 詳細検索 --> <logic:equal name="usr030Form" property="usr030cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_SHOUSAI)%>">
              <%@ include file="/WEB-INF/plugin/user/jsp/usr030_sub02.jsp"%>
            </logic:equal>
        <tr>
          <td class="w100 bgC_header2 bor_t1">

          <div class="display_tbl table-fixed">
            <div class="display_tbl_c w100 p5">
              <logic:empty name="usr030Form" property="usr030users">
                   <html:select property="usr030selectusers" styleClass="w100 hp300" size="20" multiple="multiple">
                   </html:select>
                  </logic:empty>
                  <logic:notEmpty name="usr030Form" property="usr030users">
                    <html:select property="usr030selectusers" styleClass="w100 hp300" size="20" multiple="multiple">
                      <logic:iterate id="user" name="usr030Form" property="usr030users">
                        <logic:equal value="1" name="user" property="usrUkoFlg">
                          <option value='<bean:write name="user" property="usrSid"/>' class="mukoUserOption"><bean:write name="user" property="usiName" /></option>
                        </logic:equal>
                        <logic:equal value="0" name="user" property="usrUkoFlg">
                          <option value='<bean:write name="user" property="usrSid"/>'><bean:write name="user" property="usiName" /></option>
                        </logic:equal>
                      </logic:iterate>
                    </html:select>
                  </logic:notEmpty>
                </div>
                <div class="display_tbl_c txt_m txt_c">

                  <table>
                    <tr>
                      <td>
                        <button type="button" name="btn_prjadd" class="baseBtn wp150" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('add');">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                          <gsmsg:write key="cmn.add" />
                        </button>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td class="txt_c txt_t">
                        <button type="button" name="btn_prjadd" class="baseBtn wp150" value="<gsmsg:write key="cmn.fixed" />" onClick="buttonPush('edit');">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.fixed" />">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.fixed" />">
                          <gsmsg:write key="cmn.fixed" />
                        </button>
                      </td>
                      <td>
                        <button type="button" name="btn_prjadd" class="baseBtn wp150" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('del');">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                          <gsmsg:write key="cmn.delete" />
                        </button> <br> <span> <a href="javascript:buttonPush('csvDel');"><gsmsg:write key="cmn.comments" /><gsmsg:write key="user.usr030.1" /> </a>
                      </span>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <button type="button" name="btn_prjadd" class="baseBtn wp150" value="<gsmsg:write key="user.usr030.2" />" onClick="buttonPush('yuko');">
                          <img class="classic-display" src="../user/images/classic/icon_yuko_18.gif" alt="<gsmsg:write key="user.usr030.2" />">
                          <img class="original-display" src="../user/images/original/icon_yuko.png" alt="<gsmsg:write key="user.usr030.2" />">
                          <gsmsg:write key="user.usr030.2" />
                        </button>
                      </td>
                      <td>
                        <button type="button" name="btn_prjadd" class="baseBtn wp150" value="<gsmsg:write key="user.usr030.3" />" onClick="buttonPush('muko');">
                          <img class="classic-display" src="../user/images/classic/icon_muko_18.gif" alt="<gsmsg:write key="user.usr030.3" />">
                          <img class="original-display" src="../user/images/original/icon_muko.png" alt="<gsmsg:write key="user.usr030.3" />">
                          <gsmsg:write key="user.usr030.3" />
                        </button>
                      </td>
                    </tr>
                    <tr>
                      <td>
                        <button type="button" class="baseBtn wp150" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('userImp');">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
                          <gsmsg:write key="cmn.import" />
                        </button>
                      </td>
                      <td>
                        <button type="button" class="baseBtn wp150" value="<gsmsg:write key="cmn.export" />" onClick="exportPush('userExp');">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                          <gsmsg:write key="cmn.export" />
                        </button>
                      </td>
                    </tr>
                  </table>
                </div>
              </div>
            </div>
          </td>
        </tr>
      </table>
    </div>


  </html:form>
  <IFRAME type="hidden" src="../common/html/damy.html" class="display_n" name="navframe"></IFRAME>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
  <map name="imagemap">
    <area shape="rect" coords="0,3,97,26" href="javascript:changeTab('name');" alt="<gsmsg:write key="cmn.name" />">
    <area shape="rect" coords="104,3,197,26" href="javascript:changeTab('shousai');" alt="<gsmsg:write key="cmn.advanced.search" />">
  </map>
</body>
</html:html>