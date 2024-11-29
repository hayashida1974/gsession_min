<%@ page import="java.util.Calendar" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<% String close = String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE); %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
  <head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
    <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
    <script src="../common/js/cmn100.js?<%= GSConst.VERSION_PARAM %>"></script>

    <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
    <theme:css filename="theme.css"/>
    <link rel=stylesheet href='../common/css/common.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

    <title>GROUPSESSION <gsmsg:write key="cmn.shain.info" /></title>
  </head>

  <body class="body_03">
    <html:form action="/common/cmn100">
      <input type="hidden" name="CMD" value="">
      <html:hidden property="formUnload" />
      <html:hidden property="cmn100usid" />
      <html:hidden property="cmn100usiSyainNo" />
      <html:hidden property="cmn100usiPictKf" />
      <html:hidden property="cmn100usiSei" />
      <html:hidden property="cmn100usiMei" />
      <html:hidden property="cmn100usiSeiKn" />
      <html:hidden property="cmn100usiMeiKn" />
      <html:hidden property="cmn100usiSyozoku" />
      <html:hidden property="cmn100usiYakusyoku" />
      <html:hidden property="cmn100usiBdateKf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiBdateKf" value="<%= close %>">
        <html:hidden property="cmn100usiBdate" />
      </logic:notEqual>
      <html:hidden property="cmn100usiMail1Kf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiMail1Kf" value="<%= close %>">
        <html:hidden property="cmn100usiMail1" />
      </logic:notEqual>
      <html:hidden property="cmn100usiMail2Kf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiMail2Kf" value="<%= close %>">
        <html:hidden property="cmn100usiMail2" />
      </logic:notEqual>
      <html:hidden property="cmn100usiMail3Kf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiMail3Kf" value="<%= close %>">
        <html:hidden property="cmn100usiMail3" />
      </logic:notEqual>
      <html:hidden property="cmn100usiZipKf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiZipKf" value="<%= close %>">
        <html:hidden property="cmn100usiZip" />
      </logic:notEqual>
      <html:hidden property="cmn100usiTdfkKf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiTdfkKf" value="<%= close %>">
        <html:hidden property="cmn100TdfkName" />
      </logic:notEqual>
      <html:hidden property="cmn100usiAddr1Kf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiAddr1Kf" value="<%= close %>">
        <html:hidden property="cmn100usiAddr1" />
      </logic:notEqual>
      <html:hidden property="cmn100usiAddr2Kf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiAddr2Kf" value="<%= close %>">
        <html:hidden property="cmn100usiAddr2" />
      </logic:notEqual>
      <html:hidden property="cmn100usiTel1Kf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiTel1Kf" value="<%= close %>">
        <html:hidden property="cmn100usiTel1" />
      </logic:notEqual>
      <html:hidden property="cmn100usiTel2Kf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiTel2Kf" value="<%= close %>">
        <html:hidden property="cmn100usiTel2" />
      </logic:notEqual>
      <html:hidden property="cmn100usiTel3Kf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiTel3Kf" value="<%= close %>">
        <html:hidden property="cmn100usiTel3" />
      </logic:notEqual>
      <html:hidden property="cmn100usiFax1Kf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiFax1Kf" value="<%= close %>">
        <html:hidden property="cmn100usiFax1" />
      </logic:notEqual>
      <html:hidden property="cmn100usiFax2Kf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiFax2Kf" value="<%= close %>">
        <html:hidden property="cmn100usiFax2" />
      </logic:notEqual>
      <html:hidden property="cmn100usiFax3Kf" />
      <logic:notEqual name="cmn100Form" property="cmn100usiFax3Kf" value="<%= close %>">
        <html:hidden property="cmn100usiFax3" />
      </logic:notEqual>
      <html:hidden property="cmn100usiBiko" />
      <html:hidden property="cmn100binSid" />

      <div class="pageTitle w100 mrl_auto">
        <ul>
          <li>
            <img class="header_pluginImg-classic" src="../common/images/pluginImg/classic/menu_icon_user_25.gif" alt="<gsmsg:write key="cmn.shain.info" />">
            <img class="header_pluginImg" src="../common/images/pluginImg/original/menu_icon_user_50.png" alt="<gsmsg:write key="cmn.shain.info" />">
          </li>
          <li><gsmsg:write key="cmn.shain.info" />　</li>
          <li>
            <div>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
                <gsmsg:write key="cmn.close" />
              </button>
            </div>
          </li>
        </ul>
      </div>
      <div id="js_userInfoList" class="wrapper w100 mrl_auto">
        <!--削除ユーザ-->
        <logic:notEqual name="cmn100Form" property="cmn100usrJkbn" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_JTKBN_ACTIVE) %>">
          <div class="mrl_auto">
            <span id="js_loginStop" class="cl_fontWarn">
              <gsmsg:write key="cmn.cmn100.1" />
            </span>
          </div>
        </logic:notEqual>
        <logic:equal name="cmn100Form" property="cmn100usrJkbn" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_JTKBN_ACTIVE) %>">
          <!--ログイン停止ユーザ-->
          <logic:equal name="cmn100Form" property="cmn100usrUkoFlg" value="1">
            <div class="mrl_auto">
              <span id="js_loginStop" class="cl_fontWarn">
                <gsmsg:write key="cmn.cmn100.6" />
              </span>
            </div>
          </logic:equal>

          <table class="table-left">
            <!--付加情報-->
            <logic:iterate id="app" name="cmn100Form" property="appendInfoList" scope="request" indexId="idx">
              <logic:iterate id="msg" name="app" property="message" indexId="idy">
                <logic:equal name="idy" value="0">
                  <tr>
                    <th class="w40" rowspan="<bean:write name="app" property="titleRow" />">
                      <bean:write name="app" property="title" />
                      <logic:equal name="app" property="linkType" value="1">
                        <button type="button" class="iconBtn-border" value="" onClick="moveMonthSchedule('month', <bean:write name="cmn100Form" property="cmn100usid" />, 0);">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_cal_gekkan.png" alt="">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_gekkan.png" alt="">
                        </button>
                      </logic:equal>
                    </th>
                    <logic:equal name="app" property="filter" value="1">
                      <bean:write name="msg" filter="false"/>
                    </logic:equal>
                    <logic:notEqual name="app" property="filter" value="1">
                      <td class="borderBlock-white txt_l">
                        <bean:write name="msg" filter="true"/>
                      </td>
                    </logic:notEqual>
                  </tr>
                </logic:equal>
                <logic:notEqual name="idy" value="0">
                  <tr>
                    <logic:equal name="app" property="filter" value="1">
                      <bean:write name="msg" filter="false"/>
                    </logic:equal>
                    <logic:notEqual name="app" property="filter" value="1">
                      <td class="borderBlock-white txt_l">
                        <bean:write name="msg" filter="true"/>
                      </td>
                    </logic:notEqual>
                  </tr>
                </logic:notEqual>
              </logic:iterate>
            </logic:iterate>
          </table>

          <table class="table-left">
            <tr>
              <th colspan="2" class="no_w w40">
                <gsmsg:write key="cmn.employee.staff.number" />
              </th>
              <td class="borderBlock-white txt_l">
                <bean:write name="cmn100Form" property="cmn100usiSyainNo" />
              </td>
              <td rowspan="4" class="txt_c wp140 borderBlock-white" >
                <logic:equal name="cmn100Form" property="cmn100usiPictKf" value="<%= close %>">
                  <div class="userImg-hikokai hp150 mrl_auto">
                    <span class="cl_fontWarn fs_24 fw_b hikokai_lh">
                      <gsmsg:write key="cmn.private" />
                    </span>
                  </div>
                </logic:equal>
                <logic:notEqual name="cmn100Form" property="cmn100usiPictKf" value="<%= close %>">
                  <logic:lessThan name="cmn100Form" property="cmn100binSid" value="1">
                    <img src="../common/images/classic/icon_photo.gif" name="pitctImage" class="classic-display usrImage" alt="<gsmsg:write key="cmn.photo" />" >
                    <img src="../common/images/original/photo.png" name="pitctImage" class="original-display usrImage" alt="<gsmsg:write key="cmn.photo" />" >
                  </logic:lessThan>
                  <logic:greaterThan name="cmn100Form" property="cmn100binSid" value="0">
                    <logic:equal name="cmn100Form" property="formUnload" value="false">
                      <img src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name='cmn100Form' property='cmn100binSid' />" name="pictImage" class="usrImage" alt="<gsmsg:write key="cmn.photo" />">
                    </logic:equal>
                  </logic:greaterThan>
                </logic:notEqual>
              </td>
            </tr>
            <tr>
              <th colspan="2">
                <gsmsg:write key="cmn.name" />
              </th>
              <td class="txt_l borderBlock-white">
                <span class="mr5">
                  <bean:write name="cmn100Form" property="cmn100usiSei" />
                </span>
                <bean:write name="cmn100Form" property="cmn100usiMei" />
              </td>
            </tr>
            <tr>
              <th colspan="2">
                <gsmsg:write key="user.119" />
              </th>
              <td class="txt_l borderBlock-white">
                <span class="mr5">
                  <bean:write name="cmn100Form" property="cmn100usiSeiKn" />
                </span>
                <bean:write name="cmn100Form" property="cmn100usiMeiKn" />
              </td>
            </tr>
            <tr>
              <th colspan="2">
                <gsmsg:write key="cmn.affiliation" />
              </th>
              <td class="txt_l borderBlock-white">
                <bean:write name="cmn100Form" property="cmn100usiSyozoku" />
              </td>
            </tr>
            <tr>
              <th colspan="2">
                <gsmsg:write key="cmn.post" />
              </th>
              <td colspan="2" class="txt_l borderBlock-white">
                <bean:write name="cmn100Form" property="cmn100usiYakusyoku" />
              </td>
            </tr>
            <tr>
              <th colspan="2">
                <gsmsg:write key="user.123" />
              </th>
              <td colspan="2" class="txt_l borderBlock-white">
                <bean:write name="cmn100Form" property="cmn100usiSeibetu" />
              </td>
            </tr>
            <tr>
              <th colspan="2">
                <gsmsg:write key="user.156" />
              </th>
              <td colspan="2" class="txt_l borderBlock-white">
                <bean:write name="cmn100Form" property="cmn100entranceDate" />
              </td>
            </tr>
            <logic:notEqual name="cmn100Form" property="cmn100usiBdateKf" value="<%= close %>">
              <tr>
                <th colspan="2">
                  <gsmsg:write key="cmn.birthday" />
                </th>
                <td colspan="2" class="txt_l borderBlock-white">
                  <bean:write name="cmn100Form" property="cmn100usiBdate" />
                  <logic:notEqual name="cmn100Form" property="cmn100usiAge" value="-1">
                    (<bean:write name="cmn100Form" property="cmn100usiAge" /><gsmsg:write key="user.98" />)
                  </logic:notEqual>
                </td>
              </tr>
            </logic:notEqual>
            <logic:notEqual name="cmn100Form" property="cmn100usiMail1Kf" value="<%= close %>">
              <tr>
                <th class="no_w" colspan="2">
                  <gsmsg:write key="cmn.mailaddress1" />
                </th>
                <td colspan="2" class="txt_l borderBlock-white">
                  <logic:notEqual name="cmn100Form" property="cmn100usiMail1Kf" value="<%= close %>">
                    <logic:notEmpty name="cmn100Form" property="cmn100usiMail1">
                      <a href="mailto:<bean:write name="cmn100Form" property="cmn100usiMail1" />"><bean:write name="cmn100Form" property="cmn100usiMail1" /></a>
                    </logic:notEmpty>
                    <logic:notEmpty name="cmn100Form" property="cmn100usiMailCmt1">
                      <div>
                        <gsmsg:write key="cmn.comment" />：
                        <span class="ml5">
                          <bean:write name="cmn100Form" property="cmn100usiMailCmt1" />
                        </span>
                      </div>
                    </logic:notEmpty>
                  </logic:notEqual>
                </td>
              </tr>
            </logic:notEqual>
            <logic:notEqual name="cmn100Form" property="cmn100usiMail2Kf" value="<%= close %>">
              <tr>
                <th class="no_w" colspan="2">
                  <gsmsg:write key="cmn.mailaddress2" />
                </th>
                <td colspan="2" class="txt_l borderBlock-white">
                  <logic:notEqual name="cmn100Form" property="cmn100usiMail2Kf" value="<%= close %>">
                    <logic:notEmpty name="cmn100Form" property="cmn100usiMail2">
                      <a href="mailto:<bean:write name="cmn100Form" property="cmn100usiMail2" />"><bean:write name="cmn100Form" property="cmn100usiMail2" /></a>
                    </logic:notEmpty>
                    <logic:notEmpty name="cmn100Form" property="cmn100usiMailCmt2">
                      <div>
                        <gsmsg:write key="cmn.comment" />：
                        <span class="ml5">
                          <bean:write name="cmn100Form" property="cmn100usiMailCmt2" />
                        </span>
                      </div>
                    </logic:notEmpty>
                  </logic:notEqual>
                </td>
              </tr>
            </logic:notEqual>
            <logic:notEqual name="cmn100Form" property="cmn100usiMail3Kf" value="<%= close %>">
              <tr>
                <th class="no_w" colspan="2">
                  <gsmsg:write key="cmn.mailaddress3" />
                </th>
                <td colspan="2" class="txt_l borderBlock-white">
                  <logic:notEqual name="cmn100Form" property="cmn100usiMail3Kf" value="<%= close %>">
                    <logic:notEmpty name="cmn100Form" property="cmn100usiMail3">
                      <a href="mailto:<bean:write name="cmn100Form" property="cmn100usiMail3" />"><bean:write name="cmn100Form" property="cmn100usiMail3" /></a>
                    </logic:notEmpty>
                    <logic:notEmpty name="cmn100Form" property="cmn100usiMailCmt3">
                      <div>
                        <gsmsg:write key="cmn.comment" />：
                        <span class="ml5">
                          <bean:write name="cmn100Form" property="cmn100usiMailCmt3" />
                        </span>
                      </div>
                    </logic:notEmpty>
                  </logic:notEqual>
                </td>
              </tr>
            </logic:notEqual>
            <% int addressRow = 1; %>
            <logic:notEqual name="cmn100Form" property="cmn100usiZipKf" value="<%= close %>">
              <% addressRow = addressRow + 1; %>
            </logic:notEqual>
            <logic:notEqual name="cmn100Form" property="cmn100usiTdfkKf" value="<%= close %>">
              <% addressRow = addressRow + 1; %>
            </logic:notEqual>
            <logic:notEqual name="cmn100Form" property="cmn100usiAddr1Kf" value="<%= close %>">
              <% addressRow = addressRow + 1; %>
            </logic:notEqual>
            <logic:notEqual name="cmn100Form" property="cmn100usiAddr2Kf" value="<%= close %>">
              <% addressRow = addressRow + 1; %>
            </logic:notEqual>
            <% if (addressRow != 1) { %>
              <th rowspan="<%= addressRow %>">
                <gsmsg:write key="cmn.address" />
              </th>
              <logic:notEqual name="cmn100Form" property="cmn100usiZipKf" value="<%= close %>">
                <tr>
                  <th class="no_w bor1">
                    <gsmsg:write key="cmn.postalcode" />
                  </th>
                  <td colspan="2" class="txt_l borderBlock-white">
                    <logic:notEqual name="cmn100Form" property="cmn100usiZipKf" value="<%= close %>">
                      <bean:write name="cmn100Form" property="cmn100usiZip" />
                    </logic:notEqual>
                  </td>
                </tr>
              </logic:notEqual>
              <logic:notEqual name="cmn100Form" property="cmn100usiTdfkKf" value="<%= close %>">
                <tr>
                  <th class="no_w">
                    <gsmsg:write key="cmn.prefectures" />
                  </th>
                  <td colspan="2" class="txt_l borderBlock-white">
                    <logic:notEqual name="cmn100Form" property="cmn100usiTdfkKf" value="<%= close %>">
                      <bean:write name="cmn100Form" property="cmn100TdfkName" />
                    </logic:notEqual>
                  </td>
                </tr>
              </logic:notEqual>
              <logic:notEqual name="cmn100Form" property="cmn100usiAddr1Kf" value="<%= close %>">
                <tr>
                  <th class="no_w">
                    <gsmsg:write key="cmn.address1" />
                  </th>
                  <td colspan="2" class="txt_l borderBlock-white">
                    <logic:notEqual name="cmn100Form" property="cmn100usiAddr1Kf" value="<%= close %>">
                      <bean:write name="cmn100Form" property="cmn100usiAddr1" />
                    </logic:notEqual>
                  </td>
                </tr>
              </logic:notEqual>
              <logic:notEqual name="cmn100Form" property="cmn100usiAddr2Kf" value="<%= close %>">
                <tr>
                  <th class="no_w">
                    <gsmsg:write key="cmn.address2" />
                  </th>
                  <td colspan="2" class="txt_l borderBlock-white">
                    <logic:notEqual name="cmn100Form" property="cmn100usiAddr2Kf" value="<%= close %>">
                      <bean:write name="cmn100Form" property="cmn100usiAddr2" />
                    </logic:notEqual>
                  </td>
                </tr>
              </logic:notEqual>
            <% } %>
            <logic:notEqual name="cmn100Form" property="cmn100usiTel1Kf" value="<%= close %>">
              <tr>
                <th colspan="2">
                  <gsmsg:write key="cmn.tel1" />
                </th>
                <td colspan="2" class="txt_l borderBlock-white">
                  <bean:write name="cmn100Form" property="cmn100usiTel1" />
                  <div>
                    <logic:notEmpty name="cmn100Form" property="cmn100usiTelNai1">
                      <gsmsg:write key="user.136" />：
                      <span class="ml5 mr10">
                        <bean:write name="cmn100Form" property="cmn100usiTelNai1" />
                      </span>
                    </logic:notEmpty>
                    <logic:notEmpty name="cmn100Form" property="cmn100usiTelCmt1">
                      <gsmsg:write key="cmn.comment" />：
                      <span class="ml5">
                        <bean:write name="cmn100Form" property="cmn100usiTelCmt1" />
                      </span>
                    </logic:notEmpty>
                  </div>
                </td>
              </tr>
            </logic:notEqual>
            <logic:notEqual name="cmn100Form" property="cmn100usiTel2Kf" value="<%= close %>">
              <tr>
                <th colspan="2">
                  <gsmsg:write key="cmn.tel2" />
                </th>
                <td colspan="2" class="txt_l borderBlock-white">
                  <bean:write name="cmn100Form" property="cmn100usiTel2" />
                  <div>
                    <logic:notEmpty name="cmn100Form" property="cmn100usiTelNai2">
                      <gsmsg:write key="user.136" />：
                      <span class="mr10 ml5">
                        <bean:write name="cmn100Form" property="cmn100usiTelNai2" />
                      </span>
                    </logic:notEmpty>
                    <logic:notEmpty name="cmn100Form" property="cmn100usiTelCmt2">
                      <gsmsg:write key="cmn.comment" />：
                      <bean:write name="cmn100Form" property="cmn100usiTelCmt2" />
                    </logic:notEmpty>
                  </div>
                </td>
              </tr>
            </logic:notEqual>
            <logic:notEqual name="cmn100Form" property="cmn100usiTel3Kf" value="<%= close %>">
              <tr>
                <th colspan="2">
                  <gsmsg:write key="cmn.tel3" />
                </th>
                <td colspan="2" class="txt_l borderBlock-white">
                  <bean:write name="cmn100Form" property="cmn100usiTel3" />
                  <div>
                    <logic:notEmpty name="cmn100Form" property="cmn100usiTelNai3">
                      <gsmsg:write key="user.136" />：
                      <span class="mr10 ml5">
                        <bean:write name="cmn100Form" property="cmn100usiTelNai3" />
                      </span>
                    </logic:notEmpty>
                    <logic:notEmpty name="cmn100Form" property="cmn100usiTelCmt3">
                      <gsmsg:write key="cmn.comment" />：
                      <span class="ml5">
                        <bean:write name="cmn100Form" property="cmn100usiTelCmt3" />
                      </span>
                    </logic:notEmpty>
                  </div>
                </td>
              </tr>
            </logic:notEqual>
            <logic:notEqual name="cmn100Form" property="cmn100usiFax1Kf" value="<%= close %>">
              <tr>
                <th colspan="2">
                  <gsmsg:write key="user.143" />
                </th>
                <td colspan="2" class="txt_l borderBlock-white">
                  <bean:write name="cmn100Form" property="cmn100usiFax1" />
                  <logic:notEmpty name="cmn100Form" property="cmn100usiFaxCmt1">
                    <div>
                      <gsmsg:write key="cmn.comment" />：
                      <span class="ml5">
                        <bean:write name="cmn100Form" property="cmn100usiFaxCmt1" />
                      </span>
                    </div>
                  </logic:notEmpty>
                </td>
              </tr>
            </logic:notEqual>
            <logic:notEqual name="cmn100Form" property="cmn100usiFax2Kf" value="<%= close %>">
              <tr>
                <th colspan="2">
                  <gsmsg:write key="cmn.cmn100.2" />
                </th>
                <td colspan="2" class="txt_l borderBlock-white">
                  <bean:write name="cmn100Form" property="cmn100usiFax2" />
                  <logic:notEmpty name="cmn100Form" property="cmn100usiFaxCmt2">
                    <div>
                      <gsmsg:write key="cmn.comment" />：
                      <span class="ml5">
                        <bean:write name="cmn100Form" property="cmn100usiFaxCmt2" />
                      </span>
                    </div>
                  </logic:notEmpty>
                </td>
              </tr>
            </logic:notEqual>
            <logic:notEqual name="cmn100Form" property="cmn100usiFax3Kf" value="<%= close %>">
              <tr>
                <th colspan="2">
                  <gsmsg:write key="cmn.cmn100.3" />
                </th>
                <td colspan="2" class="txt_l borderBlock-white">
                  <bean:write name="cmn100Form" property="cmn100usiFax3" />
                  <logic:notEmpty name="cmn100Form" property="cmn100usiFaxCmt3">
                    <div>
                      <gsmsg:write key="cmn.comment" />：
                      <span class="ml5">
                        <bean:write name="cmn100Form" property="cmn100usiFaxCmt3" />
                      </span>
                    </div>
                  </logic:notEmpty>
                </td>
              </tr>
            </logic:notEqual>
            <tr>
              <th colspan="2">
                <gsmsg:write key="cmn.memo" />
              </th>
              <td colspan="2" class="txt_l borderBlock-white">
                <bean:write name="cmn100Form" property="cmn100usiBiko" filter="false" />
              </td>
            </tr>
            <tr>
              <th colspan="2">
                <gsmsg:write key="cmn.label" />
              </th>
              <td colspan="2" class="txt_l borderBlock-white">
                <logic:notEmpty name="cmn100Form" property="labelList">
                  <logic:iterate id="labelData" name="cmn100Form" property="labelList" indexId="idx">
                    <div>
                      <bean:write name="labelData" property="labName" />
                    </div>
                  </logic:iterate>
                </logic:notEmpty>
              </td>
            </tr>
          </table>

          <table class="table-top">
            <tr>
              <th class="w40">
                <gsmsg:write key="cmn.affiliation.group" />
              </th>
              <th class="w30">
                <gsmsg:write key="user.35" />
              </th>
              <th class="w30">
                <gsmsg:write key="cmn.group.admin" />
              </th>
            </tr>
            <logic:iterate id="group" name="cmn100Form" property="cmn100grpNmlist" indexId="idx" scope="request">
              <bean:define id="groupMdl" name="group" property="usrGroup"/>
              <tr>
                <td class="txt_c">
                  <bean:write name="groupMdl" property="groupName" />
                </td>
                <td class="txt_c">
                  <logic:equal name="group" property="defaultGroupSid" value="1">
                    <div>
                      <img src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="user.35" />" class="classic-display">
                      <img src="../common/images/original/icon_group.png" alt="<gsmsg:write key="user.35" />" class="original-display">
                    </div>
                  </logic:equal>
                </td>
                <td class="txt_c">
                  <logic:equal name="groupMdl" property="grpKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.USER_ADMIN) %>">
                    <div>
                      <img src="../common/images/classic/icon_key.gif" alt="<gsmsg:write key="cmn.admin" />" class="classic-display">
                      <img src="../common/images/original/icon_key.png" alt="<gsmsg:write key="cmn.admin" />" class="original-display">
                    </div>
                  </logic:equal>
                </td>
              </tr>
            </logic:iterate>
          </table>

          <logic:notEqual name="cmn100Form" property="cmn100QrDispKbn" value="0" >

            <table class="table-top">
              <tr>
                <th class="txt_l">
                  <gsmsg:write key="cmn.cmn100.4" />
                </th>
              </tr>
              <tr>
                <td class="txt_c">
                  <table class="mrl_auto">
                    <tr>
                      <th class="fw_b border_none cl_fontBody">
                        docomo
                      </th>
                      <td class="border_none">
                        <img class="wp130 hp130" src="../common/cmn100.do?CMD=getQrDocomo&userSid=<bean:write name="cmn100Form" property="cmn100usid" />">
                      </td>
                    </tr>
                    <tr>
                      <th class="fw_b border_none cl_fontBody">
                        au
                      </th>
                      <td class="border_none">
                        <img class="wp130 hp130" src="../common/cmn100.do?CMD=getQrAu&userSid=<bean:write name="cmn100Form" property="cmn100usid" />">
                      </td>
                    </tr>
                    <tr>
                      <th class="fw_b border_none cl_fontBody">
                        SoftBank
                      </th>
                      <td class="border_none">
                        <img class="wp130 hp130" src="../common/cmn100.do?CMD=getQrSoftBank&userSid=<bean:write name="cmn100Form" property="cmn100usid" />">
                      </td>
                    </tr>
                  </table>
                  <div class="txt_l fs_11 pt10">
                    <gsmsg:write key="cmn.cmn100.5" />
                  </div>
                </td>
              </tr>
            </table>
          </logic:notEqual>
        </logic:equal>

        <div class="footerBtn_block">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.close" />
          </button>
        </div>

      </div>
    </html:form>
  </body>
</html:html>
