<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<%
  String strOk = jp.groupsession.v2.usr.GSConstUser.EXPORT_USE_OK;
  String labelSetOk = jp.groupsession.v2.usr.GSConstUser.LABEL_SET_OK;
  String labelEditOk = jp.groupsession.v2.usr.GSConstUser.LABEL_EDIT_OK;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="user.144" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/jquery-1.5.2.min.js?500"></script>
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script language="JavaScript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../user/js/usr040.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../user/js/usr040.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/calendar.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>">

</script>

<logic:equal name="usr040Form" property="usr040webmail" value="1">
  <link rel=stylesheet href='../webmail/css/webmail.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
  <script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
  <script src="../webmail/js/assets/webmailSubWindow.js?<%=GSConst.VERSION_PARAM%>"></script>

  <style type="text/css">

</style>

</logic:equal>

<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../user/css/user.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body onunload="windowClose();">

  <div id="FreezePane">


    <html:form action="/user/usr040">
      <input type="hidden" name="helpPrm" value="<bean:write name="usr040Form" property="usr040cmdMode" />">

      <input type="hidden" name="CMD" value="">
      <input type="hidden" name="pushSearch" value="">

      <html:hidden property="usr040cmdMode" />

      <html:hidden property="selectgsidSave" />

      <html:hidden property="usr040KeywordSave" />
      <html:hidden property="usr040KeyKbnShainnoSave" />
      <html:hidden property="usr040KeyKbnNameSave" />
      <html:hidden property="usr040KeyKbnNameKnSave" />
      <html:hidden property="usr040KeyKbnMailSave" />
      <html:hidden property="usr040KeyKbnTelSave" />
      <html:hidden property="usr040SearchKanaSave" />
      <html:hidden property="usr040agefromSave" />
      <html:hidden property="usr040agetoSave" />
      <html:hidden property="usr040yakushokuSave" />
      <html:hidden property="usr040tdfkCdSave" />
      <html:hidden property="usr040entranceYearFrSave" />
      <html:hidden property="usr040entranceMonthFrSave" />
      <html:hidden property="usr040entranceDayFrSave" />
      <html:hidden property="usr040entranceYearToSave" />
      <html:hidden property="usr040entranceMonthToSave" />
      <html:hidden property="usr040entranceDayToSave" />
      <html:hidden property="usr040seibetuSave" />
      <html:hidden property="usr040orderKeySave" />
      <html:hidden property="usr040sortKeySave" />
      <html:hidden property="usr040orderKey2Save" />
      <html:hidden property="usr040sortKey2Save" />

      <logic:notEmpty name="usr040Form" property="usr040labSidSave">
        <logic:iterate id="labSidArray" name="usr040Form" property="usr040labSidSave" indexId="idx">
          <input type="hidden" name="usr040labSidSave" value="<bean:write name="labSidArray" />">
        </logic:iterate>
      </logic:notEmpty>

      <html:hidden property="usr040CategorySetInitFlg" />

      <logic:notEmpty name="usr040Form" property="usr040CategoryOpenFlg">
        <logic:iterate id="openFlg" name="usr040Form" property="usr040CategoryOpenFlg">
          <bean:define id="flg" name="openFlg" type="java.lang.String" />
          <html:hidden property="usr040CategoryOpenFlg" value="<%=flg%>" />
        </logic:iterate>
      </logic:notEmpty>

      <html:hidden property="usr040SearchFlg" />
      <html:hidden property="usr040saveFlg" />
      <html:hidden property="usr040DetailExeFlg" />

      <html:hidden property="cmd" />
      <html:hidden property="sch010SelectUsrSid" />
      <html:hidden property="sch010SelectUsrKbn" />
      <html:hidden property="usr040webmail" />
      <html:hidden property="usr040webmailAddress" />
      <html:hidden property="usr040webmailType" />
      <html:hidden property="usr040SendMailMode" />
      <html:hidden property="usr040UsrSid" />
      <html:hidden property="usr040DelUsrSid" />
      <html:hidden property="usr040AddressKbn" />

      <%
        String[] atskListName = {"usr040AtskList", "usr040CcList", "usr040BccList"};
      %>
      <%
        String[] atskParamName = {"usr040Atsk", "usr040Cc", "usr040Bcc"};
      %>
      <%
        for (int atskIdx = 0; atskIdx < 3; atskIdx++) {
      %>
      <logic:notEmpty name="usr040Form" property="<%=atskListName[atskIdx]%>" scope="request">
        <%
          String usiMailName = "";
        %>
        <logic:iterate id="atskMdl" name="usr040Form" property="<%=atskListName[atskIdx]%>" scope="request">
          <%
            for (int mailNo = 1; mailNo <= 3; mailNo++) {
          %>
          <%
            usiMailName = "usiMail" + mailNo;
          %>
          <logic:notEmpty name="atskMdl" property="<%=usiMailName%>">
            <input type="hidden" name="<%=atskParamName[atskIdx]%>" value="<bean:write name="atskMdl" property="mailPersonal" /> &lt;<bean:write name="atskMdl" property="<%=usiMailName%>" />&gt;">
          </logic:notEmpty>
          <%
            usiMailName = "";
          %>
          <%
            }
          %>
        </logic:iterate>
      </logic:notEmpty>
      <%
        }
      %>

      <logic:notEmpty name="usr040Form" property="usr040SidsAtsk" scope="request">
        <logic:iterate id="sidsAtsk" name="usr040Form" property="usr040SidsAtsk" scope="request">
          <input type="hidden" name="usr040SidsAtsk" value="<bean:write name="sidsAtsk"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="usr040Form" property="usr040SidsCc" scope="request">
        <logic:iterate id="sidsCc" name="usr040Form" property="usr040SidsCc" scope="request">
          <input type="hidden" name="usr040SidsCc" value="<bean:write name="sidsCc"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="usr040Form" property="usr040SidsBcc" scope="request">
        <logic:iterate id="sidsBcc" name="usr040Form" property="usr040SidsBcc" scope="request">
          <input type="hidden" name="usr040SidsBcc" value="<bean:write name="sidsBcc"/>">
        </logic:iterate>
      </logic:notEmpty>

      <html:hidden property="usr040DspFlg" />

      <html:hidden property="usr040GrpSearchGIdSave" />
      <html:hidden property="usr040GrpSearchGNameSave" />
      <html:hidden property="usr040BaseUrl" />

      <span id="usr040labelArea"> </span>

      <logic:notEqual name="usr040Form" property="usr040webmail" value="1">
        <jsp:include page="/WEB-INF/plugin/common/jsp/header001.jsp" />
      </logic:notEqual>

      <div class="pageTitle">
        <ul>
          <li>
            <img class="header_pluginImg-classic" src="../user/images/classic/header_user.png" alt="<gsmsg:write key="cmn.shain.info" />">
            <img class="header_pluginImg" src="../user/images/original/header_user.png" alt="<gsmsg:write key="cmn.shain.info" />">
          </li>
          <li>
            <gsmsg:write key="cmn.shain.info" />
          </li>
          <li class="pageTitle_subFont">
            <logic:equal name="usr040Form" property="usr040cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NAME)%>">
              <gsmsg:write key="user.6" />
            </logic:equal>
            <logic:equal name="usr040Form" property="usr040cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP)%>">
              <gsmsg:write key="user.7" />
            </logic:equal>
            <logic:equal name="usr040Form" property="usr040cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_SHOUSAI)%>">
              <gsmsg:write key="cmn.advanced.search" />
            </logic:equal>
          </li>
          <li>
            <div>
              <logic:equal name="usr040Form" property="usr040webmail" value="1">
                <logic:equal name="usr040Form" property="usr040webmailType" value="1">
                  <button type="button" name="btn_apply" class="baseBtn" value="<gsmsg:write key="cmn.apply" />" onClick="setWebmailSendList(0, 'usr040SetMailMsg');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.apply" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_apply.png" alt="<gsmsg:write key="cmn.apply" />">
                    <gsmsg:write key="cmn.apply" />
                  </button>
                </logic:equal>
                <logic:notEqual name="usr040Form" property="usr040webmailType" value="1">
                  <button type="button" name="btn_apply" class="baseBtn" value="<gsmsg:write key="cmn.apply" />" onClick="setWebmailData(0, 'usr040SetMailMsg');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.apply" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_apply.png" alt="<gsmsg:write key="cmn.apply" />">
                    <gsmsg:write key="cmn.apply" />
                  </button>
                </logic:notEqual>
                <button type="button" name="btn_close" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
                  <gsmsg:write key="cmn.close" />
                </button>
              </logic:equal>
            </div>
          </li>
        </ul>
      </div>
      <div class="wrapperContent-2column">

        <div class="side_multi-left bgC_none">
          <logic:equal name="usr040Form" property="usr040webmail" value="1">
            <table class="w100 mb10 sideTop-fon">
              <tr>
                <td class="bor1 txt_c">
                  <div class="searchMenu_top searchMenu_title-select p5">
                    <gsmsg:write key="cmn.shain.info" />
                  </div>
                </td>
                <logic:equal name="usr040Form" property="usr040webmailCanUseAddress" value="true">
                  <td class="bor1 bgC_lightGray txt_c p5 cursor_p" onClick="changePopup();">
                    <div class="searchMenu_top">
                      <gsmsg:write key="cmn.addressbook" />
                    </div>
                  </td>
                </logic:equal>
              </tr>
            </table>
          </logic:equal>

          <table class="table-top mt0">
            <tr>
              <th class="txt_l no_w">
                <gsmsg:write key="cmn.search.menu" />
              </th>
            </tr>

            <tr>
              <td class="menuClick no_w cursor_p p0" id="name">
                <div class="searchMenu_title w100 p5" id="menu_name">
                  <!-- 氏名 -->
                  <gsmsg:write key="cmn.name" />
                </div>
              </td>
            </tr>

            <tr>
              <td class="menuClick no_w cursor_p p0" id="group">
                <div class="searchMenu_title w100 p5" id="menu_group">
                  <!-- グループ -->
                  <gsmsg:write key="cmn.group" />
                </div>
              </td>
            </tr>

            <tr>
              <td class="menuClick no_w cursor_p p0" id="shousai">
                <div class="searchMenu_title w100 p5" id="menu_shousai">
                  <!-- 詳細検索 -->
                  <gsmsg:write key="cmn.advanced.search" />
                </div>
              </td>
            </tr>

          </table>

          <jsp:include page="/WEB-INF/plugin/user/jsp/usr040_labelList.jsp">
            <jsp:param value="<%=labelEditOk%>" name="labelEditOk" />
          </jsp:include>

        </div>
        <div class="main">

          <table class="w100">
            <tr>
              <td class="txt_t pl5">

                <!-- エラーメッセージ -->
                <logic:messagesPresent message="false">
                  <html:errors />
                </logic:messagesPresent>

                <table class="w100" cellpadding="0" cellspacing="0">
                  <tr>
                    <td class="main_searchArea w100 bgC_lightGray mt0 borC_light p5 bor1">

                      <!-- 氏名 -->
                      <logic:equal name="usr040Form" property="usr040cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NAME)%>">
                        <jsp:include page="/WEB-INF/plugin/user/jsp/usr040_sub01.jsp" />
                      </logic:equal>

                      <!-- グループ -->
                      <logic:equal name="usr040Form" property="usr040cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP)%>">
                        <jsp:include page="/WEB-INF/plugin/user/jsp/usr040_sub02.jsp" />
                      </logic:equal>

                      <!-- 詳細検索 -->
                      <logic:equal name="usr040Form" property="usr040cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_SHOUSAI)%>">
                        <jsp:include page="/WEB-INF/plugin/user/jsp/usr040_sub03.jsp" />
                      </logic:equal>

                    </td>
                  </tr>

                  <logic:equal name="usr040Form" property="usr040cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_SHOUSAI)%>">
                    <tr>
                      <td class="pt10">
                        <table class="w100" cellpadding="0" cellspacing="0">
                          <tr>
                            <td colspan="2" class="txt_c">
                              <button type="button" name="btn_search" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('searchSyosai');">
                                <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                                <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                                <gsmsg:write key="cmn.search" />
                              </button>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </logic:equal>

                </table>

                <table class="w100">
                  <logic:equal name="usr040Form" property="usr040webmail" value="1">
                    <tr>
                      <td class="no_w">
                        <logic:equal name="usr040Form" property="usr040webmailType" value="1">
                          <button type="button" name="addUsrAtesaki" value="<gsmsg:write key="anp.send.dest" />" class="baseBtn" onClick="checkUsr(0, 0, 'addUsrAtesaki');">
                            <gsmsg:write key="anp.send.dest" />
                          </button>
                        </logic:equal>
                        <logic:notEqual name="usr040Form" property="usr040webmailType" value="1">
                          <button type="button" name="addUsrAtesaki" value="<gsmsg:write key="cmn.from" />" class="baseBtn" onClick="checkUsr(0, 0, 'addUsrAtesaki');">
                            <gsmsg:write key="cmn.from" />
                          </button>
                          <button type="button" name="addUsrCc" value="<gsmsg:write key="cmn.cc" />" class="baseBtn" onClick="checkUsr(1, 0, 'addUsrCc');">
                            <gsmsg:write key="cmn.cc" />
                          </button>
                          <button type="button" name="addUsrBcc" value="<gsmsg:write key="cmn.bcc" />" class="baseBtn" onClick="checkUsr(2, 0, 'addUsrBcc');">
                            <gsmsg:write key="cmn.bcc" />
                          </button>
                       </logic:notEqual>
                     </td>
                   </tr>
                 </logic:equal>

                  <tr>
                    <td class="txt_l" rowspan="2">
                      <logic:equal name="usr040Form" property="usr040SearchFlg" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.SEARCH_ZUMI)%>">
                        <span>
                          <gsmsg:write key="cmn.search.criteria" />
                        </span>
                        <br>
                        <%
                         boolean writedSearch = false;
                        %>
                        <%
                         jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
                        %>
                        <span class="text_search_key">
                          <logic:equal name="usr040Form" property="usr040cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_NAME)%>">
                            <logic:notEmpty name="usr040Form" property="usr040SearchKana">
                              <%
                                 writedSearch = true;
                               %>
                              <bean:define id="usr040SrhKana" name="usr040Form" property="usr040SearchKana" type="java.lang.String" />
                              <%
                                      String nameStr = gsMsg.getMessage(request, "cmn.name");
                               %>
                              <gsmsg:write key="user.usr040.31" arg0="<%=nameStr%>" arg1="<%=usr040SrhKana%>" />
                              <logic:notEmpty name="usr040Form" property="usr040DispKensakuJouken">(<bean:write name="usr040Form" property="usr040DispKensakuJouken" filter="false" />)</logic:notEmpty>
                            </logic:notEmpty>
                          </logic:equal>
                          <logic:equal name="usr040Form" property="usr040cmdMode" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.MODE_GROUP)%>">
                            <logic:notEmpty name="usr040Form" property="selectgpname">
                              <%
                                writedSearch = true;
                              %>
                              <bean:define id="selGpName" name="usr040Form" property="selectgpname" type="java.lang.String" />
                              <gsmsg:write key="user.usr040.32" arg0="<%=selGpName%>" />
                              <logic:notEmpty name="usr040Form" property="usr040DispKensakuJouken">(<bean:write name="usr040Form" property="usr040DispKensakuJouken" filter="false" />)</logic:notEmpty>
                            </logic:notEmpty>
                          </logic:equal>

                          <%
                            if (!writedSearch) {
                          %>
                          <logic:notEmpty name="usr040Form" property="usr040DispKensakuJouken">
                            <bean:define id="srhWord" name="usr040Form" property="usr040DispKensakuJouken" type="java.lang.String" />
                            <gsmsg:write key="address.38" arg0="<%=srhWord%>" />
                          </logic:notEmpty>
                          <%
                             }
                          %>
                        </span>
                      </logic:equal>
                    </td>

                    <logic:notEqual name="usr040Form" property="usr040webmail" value="1">
                      <td class="no_w txt_r">
                        <logic:notEmpty name="usr040Form" property="usr040users">
                          <logic:equal name="usr040Form" property="usrLabelSetKbn" value="<%=labelSetOk%>">
                            <button type="button" name="btn_usrlabel" class="baseBtn" value="<gsmsg:write key="cmn.add.label" />" onClick="openlabel();btnOnly('labelSet');">
                              <img class="btn_classicImg-display btnIcon-size" src="../common/images/classic/icon_tag.gif" alt="<gsmsg:write key="cmn.add.label" />">
                              <img class="btn_originalImg-display" src="../common/images/original/icon_label_add.png" alt="<gsmsg:write key="cmn.add.label" />">
                              <gsmsg:write key="cmn.add.label" />
                            </button>
                          </logic:equal>
                          <logic:equal name="usr040Form" property="usr040CsvExportKbn" value="<%=strOk%>">
                            <button type="button" name="btn_usrimp" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onClick="buttonPush('usr040export');">
                              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                              <gsmsg:write key="cmn.export" />
                            </button>

                          </logic:equal>
                        </logic:notEmpty>
                      </td>
                  </logic:notEqual>

                   <!-- ページング -->
                  <logic:notEmpty name="usr040Form" property="usr040users">
                    <bean:size id="count1" name="usr040Form" property="usr040PageLabel" scope="request" />
                    <logic:greaterThan name="count1" value="1">
                  <td class="w5">

                      <div class="paging txt_r">
                        <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
                          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                          <i class="icon-paging_left"></i>
                        </button>
                        <html:select styleClass="paging_combo" property="usr040pageNum1" onchange="changePage(1);">
                          <html:optionsCollection name="usr040Form" property="usr040PageLabel" value="value" label="label" />
                        </html:select>
                        <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
                          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                          <i class="icon-paging_right"></i>
                        </button>
                      </div>
                    </td>
                    </logic:greaterThan>
                  </logic:notEmpty>
                  </tr>
                </table>

       <logic:notEqual name="usr040Form" property="usr040webmail" value="1">
         <jsp:include page="/WEB-INF/plugin/user/jsp/usr040syain.jsp" />
       </logic:notEqual>

       <logic:equal name="usr040Form" property="usr040webmail" value="1">
         <jsp:include page="/WEB-INF/plugin/user/jsp/usr040webmail.jsp" />
       </logic:equal>

       <logic:notEmpty name="usr040Form" property="usr040PageLabel">
          <bean:size id="count2" name="usr040Form" property="usr040PageLabel" scope="request" />
       <logic:greaterThan name="count2" value="1">

        <div class="paging txt_r">
          <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="usr040pageNum2" onchange="changePage(2);">
            <html:optionsCollection name="usr040Form" property="usr040PageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
       </logic:greaterThan>
       </logic:notEmpty>


    </td>
    </tr>
    </table>
     <logic:equal name="usr040Form" property="usr040webmail" value="1">
       <jsp:include page="/WEB-INF/plugin/user/jsp/usr040webmailSend.jsp" />
     </logic:equal>
</div>

</div>

 <logic:equal name="usr040Form" property="usr040webmail" value="1">
      <div class="footerBtn_block txt_r mt5">
        <logic:equal name="usr040Form" property="usr040webmail" value="1">
          <logic:equal name="usr040Form" property="usr040webmailType" value="1">
            <button type="button" name="btn_apply" class="baseBtn" value="<gsmsg:write key="cmn.apply" />" onClick="setWebmailSendList(0, 'usr040SetMailMsg');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.apply" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_apply.png" alt="<gsmsg:write key="cmn.apply" />">
              <gsmsg:write key="cmn.apply" />
            </button>
          </logic:equal>
          <logic:notEqual name="usr040Form" property="usr040webmailType" value="1">
            <button type="button" name="btn_apply" class="baseBtn" value="<gsmsg:write key="cmn.apply" />" onClick="setWebmailData(0, 'usr040SetMailMsg');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.apply" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_apply.png" alt="<gsmsg:write key="cmn.apply" />">
              <gsmsg:write key="cmn.apply" />
            </button>
          </logic:notEqual>
          <button type="button" name="btn_close" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
            <gsmsg:write key="cmn.close" />
          </button>
        </logic:equal>
      </div>
    </logic:equal>

</html:form>

    <logic:notEqual name="usr040Form" property="usr040webmail" value="1">
  <jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />
</logic:notEqual>
</div>

  <div id="labelPanel" class="display_n txt_c p0">
    <div class="bd">
      <iframe src="../common/html/damy.html" name="lab" class="wp350 hp300 border_none mt10"></iframe>
    </div>
  </div>

</body>
</html:html>
