<%@page import="jp.groupsession.v2.rng.rng020.Rng020KeiroBlock"%>
<%@page import="jp.groupsession.v2.rng.rng030.Rng030Action"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="jp.groupsession.v2.rng.rng030.Rng030ButtonDispParam"%>
<%@page import="jp.groupsession.v2.rng.rng030.Rng030KeiroParam"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform" %>
<%@ taglib tagdir="/WEB-INF/tags/formbuilder" prefix="fb" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>

<%@ page import="jp.groupsession.v2.rng.RngConst" %>
<%@ page import="jp.groupsession.v2.rng.rng030.Rng030Form" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%
   pageContext.setAttribute("rng030Form", request.getAttribute("rng030Form"));
   Integer Idx = Integer.valueOf(request.getParameter("Idx"));
   pageContext.setAttribute("rng030Form", request.getAttribute("rng030Form"));
%>

<% String lineNoKeiro = String.valueOf(Idx.intValue());%>
<% String lineProNameKeiro = "rng030keiroList[" + lineNoKeiro + "].";%>
<% int singiCount = 0; %>
<bean:define id="keiroGroup" name="rng030Form" property="<%=\"rng030keiroList[\" + lineNoKeiro + \"]\" %>" />

<bean:define id="selClass" value="" />
<logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroStatus\"%>"  value="1">
  <bean:define id="selClass" value="bgC_select" />
</logic:equal>
<bean:define id="btn" name="rng030Form" property="rng030BtnDisp" />

<dl class="<%=selClass%> keiroKakuninList_keiroStep">
  <% String[] keiroParam = {"keiroName","keiroCount","keiroMessage","keiroStatus","keiroSingi","keiroSort","singiCount","keiroKoetuMei","keiroStepSid","keiroLimit", "groupDelFlg"}; %>
  <% String[] singiParam = {"singiName","singiDairi","singiComment","singiStatus","singiDate","singiDairiFlg","singiCheckFlg","singiCommentFlg",
        "tmpFileList","singiKoetu","singiTime","singiPosition","userSid","dairiSid"}; %>

  <!-- スタンプ -->
  <li class="wp50 txt_c">
    <logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroStatus\"%>" value="2">
      <img src="../ringi/images/classic/icon_stamp_ok.png" class="classic-display img_bottom " alt="<gsmsg:write key="rng.41" />">
      <img src="../ringi/images/original/icon_stamp_ok.png" class="original-display img_bottom" alt="<gsmsg:write key="rng.41" />" >
    </logic:equal>
    <logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroStatus\"%>" value="4">
      <img src="../ringi/images/classic/icon_stamp_ok.png" class="classic-display img_bottom " alt="<gsmsg:write key="rng.41" />" >
      <img src="../ringi/images/original/icon_stamp_ok.png" class="original-display img_bottom" alt="<gsmsg:write key="rng.41" />" >
    </logic:equal>
    <logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroStatus\"%>" value="3">
      <img src="../ringi/images/classic/icon_stamp_ng.png" class="classic-display img_bottom " alt="<gsmsg:write key="rng.rng030.12" />">
      <img src="../ringi/images/original/icon_stamp_ng.png" class="original-display img_bottom" alt="<gsmsg:write key="rng.41" />">
    </logic:equal>
  </li>

  <!-- グループ表記か個人表記か -->
  <bean:define id="multipleUser" value="0"/>
  <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + \"keiroCount\"%>" value="1">
    <bean:define id="multipleUser" value="1"/>
  </logic:notEqual>
  <logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroCount\"%>" value="1">
    <logic:notEmpty name="rng030Form" property="<%= lineProNameKeiro + \"keiroName\"%>">
      <bean:define id="multipleUser" value="1"/>
    </logic:notEmpty>
  </logic:equal>
  <li>
    <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + \"keiroSingi\"%>" value = "2">
      <!-- 個人承認start -->
      <logic:equal name="multipleUser" value="0">
        <% String lineNoSingi = String.valueOf(0); %>
        <% String lineProNameSingi = "singiList[" + lineNoSingi + "].";%>
        <logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroStatus\"%>" value="6">
          &nbsp;<span class="cl_fontWarn"><gsmsg:write key="rng.rng030.19" /> <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[9]%>"/> <gsmsg:write key="rng.rng030.20" /></span>
          <br>
        </logic:equal>
        <logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroStatus\"%>" value="7">
          &nbsp;<span class="cl_fontWarn"><gsmsg:write key="rng.rng030.19" /> <gsmsg:write key="rng.rng030.26" /></span>
          <br>
        </logic:equal>

        <logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroMessage\"%>" value="0">
          <logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroSingi\"%>" value="0">
            <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + \"keiroStatus\"%>" value="6">
              <div class="pt10"><span class="flo_r"><gsmsg:write key="rng.rng030.22" />:<gsmsg:write key="rng.rng110.20" /></span></div>
            </logic:notEqual>
            <logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroStatus\"%>" value="6">
              <div class="pt5 flo_r"><gsmsg:write key="rng.rng030.22" />:<gsmsg:write key="rng.rng110.20" /></div>
            </logic:equal>
          </logic:equal>
        </logic:equal>

        <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[5]%>" value="0">
          <logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroSingi\"%>" value="0">
            &nbsp;<span class="cl_fontWarn"><gsmsg:write key="rng.rng030.19" /> <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[1]%>"/> <gsmsg:write key="rng.rng030.21" /></span>
            <br>
          </logic:equal>
          <logic:equal name="rng030Form" property="<%= lineProNameKeiro + \"keiroSingi\"%>" value="1">
            &nbsp;<span class="cl_fontWarn"><gsmsg:write key="rng.rng030.19" /> <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[1]%>"/> <gsmsg:write key="rng.rng030.28" /></span>
            <br>
          </logic:equal>
        </logic:equal>
        <dl>
          <%-- 審議者名 --%>
          <li class="w20 keiroList_userName-minWidth">
            <a href="#!" class="atesakiRngLink cl_fontBody" onClick="openUserInfoWindow(<bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[12]%>"/>);">
              <ringi:rng030_keiroUser bean="<%=(Rng030KeiroParam) keiroGroup %>" lineProNameSingi="<%= lineProNameSingi%>" singiName="<%= singiParam[0]%>" />
            </a>
            <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[5]%>" value="0">
              (<a href="#!" class="atesakiRngLink cl_fontBody" onClick="openUserInfoWindow(<bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[13]%>"/>);">
                <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[1]%>"/>
              </a>)
            </logic:equal>
            <div>
              <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[11]%>"/>
            </div>
          </li>
          <li class="ml5"></li>
          <bean:define id="viewUser" name="rng030Form" property="rng010ViewAccount"/>
          <li class="fs_13">
            <!-- SID取得用div -->
            <div class="display_n" id="rksSid__<%= lineNoKeiro %><%= lineNoSingi %>"><bean:write name="rng030Form" property="<%= lineProNameKeiro + \"keiroStepSid\"%>"/></div>
            <div class="display_n" id="usrSid__<%= lineNoKeiro %><%= lineNoSingi %>"><bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[12]%>"/></div>
            <div class="display_n" id="rngSid__<%= lineNoKeiro %><%= lineNoSingi %>"><bean:write name="rng030Form" property="rngSid"/></div>
            <bean:define id="editOkFlg" value="0"/>
            <logic:equal name="btn" property="btnCommentDispFlg" value="1">
              <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[12] %>" value="<%= viewUser.toString() %>">
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3] %>" value="2">
                  <bean:define id="editOkFlg" value="1"/>
                </logic:equal>
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3] %>" value="3">
                  <bean:define id="editOkFlg" value="1"/>
                </logic:equal>
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3] %>" value="6">
                  <bean:define id="editOkFlg" value="1"/>
                </logic:equal>
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3] %>" value="4">
                  <bean:define id="editOkFlg" value="1"/>
                </logic:equal>
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3] %>" value="7">
                  <bean:define id="editOkFlg" value="1"/>
                </logic:equal>
              </logic:equal>
            </logic:equal>

            <!-- ボタン -->
            <logic:equal name="editOkFlg" value="1">
              <div id="<%= lineNoKeiro %><%= lineNoSingi %>">
                <button id="edit__<%= lineNoKeiro %><%= lineNoSingi %>" type="button" class="js_commentBtn baseBtn" value="<gsmsg:write key="cmn.edit" />"><gsmsg:write key="cmn.edit" /></button>
                <button id="kakutei__<%= lineNoKeiro %><%= lineNoSingi %>" type="button" class="js_kakuteiBtn baseBtn" value="<gsmsg:write key="cmn.final" />"><gsmsg:write key="cmn.final" /></button>
                <button id="cansel__<%= lineNoKeiro %><%= lineNoSingi %>" type="button" class="js_canselBtn baseBtn" value="<gsmsg:write key="cmn.cancel" />"><gsmsg:write key="cmn.cancel" /></button>
              </div>
            </logic:equal>

            <!-- 表示用コメント -->
            <div id="comment__<%= lineNoKeiro %><%= lineNoSingi %>">
              <span id="comspan__<%= lineNoKeiro %><%= lineNoSingi %>"><bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[2]%>"/></span>
            </div>

            <!-- 編集用コメント -->
            <div id="text__<%= lineNoKeiro %><%= lineNoSingi %>" class="display_n">
              <textarea id="cmt__<%= lineNoKeiro %><%= lineNoSingi %>" class="wp400" rows="4">
              </textarea>
            </div>

            <!-- 詳細表示リンク -->
            <div id="linkTxt__<%= lineNoKeiro %><%= lineNoSingi %>">
              <div id="disp__<%= lineNoKeiro %><%= lineNoSingi %>" class="display_n">
                <a href="#!" onclick="showDisp.call(this,'<%= lineNoKeiro %>','<%= lineNoSingi %>');">
                  <span id="linkDsp__<%= lineNoKeiro %><%= lineNoKeiro %>"><gsmsg:write key="bmk.bmk010.03"/></span>
                </a>
              </div>
            </div>

            <!-- 文字数警告 -->
            <div id="alert__<%= lineNoKeiro %><%= lineNoSingi %>" class="display_n">
              <span class="cl_fontWarn"><gsmsg:write key="rng.rng030.27"/></span>
            </div>

            <!-- 表示用添付ファイル -->
            <div id="temp__<%= lineNoKeiro %><%= lineNoSingi %>" class="pt5">
              <logic:iterate id="tmpFile" name="keiroGroup" property="<%= lineProNameSingi + singiParam[8]%>">
                <file-preview url="../ringi/rng030.do?CMD=fileDownload&rng030fileId=<bean:write name="tmpFile" property="binSid" />&rngSid=<bean:write name="rng030Form" property="rngSid"/>&rng010ViewAccount=<bean:write name="rng030Form" property="rng010ViewAccount"/>" filename="<bean:write name="tmpFile" property="binFileName" />"}>
                  <img class="classic-display" src="../common/images/classic/icon_temp_file.gif">
                  <img class="original-display" src="../common/images/original/icon_attach.png">
                  <a href="#!" onClick="return fileNameClick(<bean:write name="tmpFile" property="binSid" />);"><span class="small_link"><u><bean:write name="tmpFile" property="binFileName" />
                    <bean:write name="tmpFile" property="binFileSizeDsp" /></u></span>
                  </a>
                </file-preview>
                <br>
              </logic:iterate>
            </div>

            <!-- 編集用添付ファイル -->
            <div id="tempName__<%= lineNoKeiro %><%= lineNoSingi %>" class="formRow display_n pt5">
              <bean:define id="rowName" type="String"><%=Rng030Action.SUBDIRNAME_COMMENTEDIT_ROW%><bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[8]%>"/><bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[12]%>"/></bean:define>
              <gsform:file_opnTempButton selectListName="<%=\"rng030selectFiles\"+ lineNoKeiro+ lineNoSingi %>" pluginId="<%=RngConst.PLUGIN_ID_RINGI %>" tempDirId="<%=Rng030Action.TEMPDIRID %>" subDirList="<%=Arrays.asList(new String[] {Rng030Action.SUBDIRNAME_COMMENTEDIT, rowName}) %>"></gsform:file_opnTempButton>
            </div>
          </li>
          <li class="mrl_auto" >
            <dl>
            </dl>
          </li>
          <!-- 確認日時  -->
          <li class="wp130 txt_c keiroList_confDate-minWidth">
            <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + \"keiroStatus\"%>" value="6">
              <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[6]%>" value="0">
                <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[4]%>"/><br>
                <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[10]%>"/>
              </logic:equal>
            </logic:notEqual>
          </li>
        </dl>
        <%singiCount+=1; %>
      </logic:equal>
      <!-- 個人承認end -->
      <!-- グループ承認start -->
      <logic:equal name="multipleUser" value="1">
        <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="6">
          <span class="cl_fontWarn"><gsmsg:write key="rng.rng030.19" /> <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[7]%>"/> <gsmsg:write key="rng.rng030.20" /></span><br>
        </logic:equal>
        <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="7">
          <span class="cl_fontWarn"><gsmsg:write key="rng.rng030.19" /> <gsmsg:write key="rng.rng030.26" /></span><br>
        </logic:equal>
        <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[4]%>" value="0">
          <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="6">
            <div class="flo_r">
              <gsmsg:write key="rng.rng030.22" />:
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[2]%>" value="0">
                <gsmsg:write key="rng.rng110.20" />
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[2]%>" value="1">
                <gsmsg:write key="rng.rng110.21" />
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[2]%>" value="2">
                <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[9]%>"/><gsmsg:write key="rng.rng030.30"/>
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[2]%>" value="3">
                <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[9]%>"/><gsmsg:write key="rng.rng030.29"/>
              </logic:equal>
            </div>
          </logic:notEqual>
          <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="6">
            <div class="flo_r">
              <gsmsg:write key="rng.rng030.22" />:
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[2]%>" value="0">
                <gsmsg:write key="rng.rng110.20" />
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[2]%>" value="1">
                <gsmsg:write key="rng.rng110.21" />
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[2]%>" value="2">
                <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[9]%>"/><gsmsg:write key="rng.rng030.30"/>
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[2]%>" value="3">
                <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[9]%>"/><gsmsg:write key="rng.rng030.29"/>
              </logic:equal>
            </div>
          </logic:equal>
        </logic:equal>

        <logic:iterate id="singi" name="keiroGroup" property="singiList" indexId="sIdx">
          <% String lineNoSingi = String.valueOf(sIdx.intValue()); %>
          <% String lineProNameSingi = "singiList[" + lineNoSingi + "].";%>
          <% singiCount+=1; %>
          <%if(Integer.parseInt(lineNoSingi) == 0) { %>
            <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>"  value="1">
              <span class="boxTitleRng bgC_select">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png"  align="center">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png"  align="center">
                <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[10]%>" value="9">
                  <del><bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/></del>
                </logic:equal>
                <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[10]%>" value="9">
                  <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/>
                </logic:notEqual>
              </span>
            </logic:equal>
            <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>"  value="1">
              <span class="boxTitleRng bgC_body">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png"  align="center">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png"  align="center">
                <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[10]%>" value="9">
                  <del><bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/></del>
                </logic:equal>
                <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[10]%>" value="9">
                  <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/>
                </logic:notEqual>
              </span>
            </logic:notEqual>
          <%}%>
          <div class="keiroKakuninList_singi">
            <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[5]%>" value="0">
              <div class="w100 bor_l1 bor_r1">
                <div class="mt10">
                  <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[4]%>" value="0">
                    <span class="cl_fontWarn"><gsmsg:write key="rng.rng030.19" /> <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[1]%>"/><gsmsg:write key="rng.rng030.21" /></span>
                  </logic:equal>
                  <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[4]%>" value="1">
                    <span class="cl_fontWarn"><gsmsg:write key="rng.rng030.19" /> <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[1]%>"/><gsmsg:write key="rng.rng030.28" /></span>
                  </logic:equal>
                </div>
              </div>
            </logic:equal>
            <dl class="w100">
              <li class="ml5"></li>
              <li class="w20 pl5 keiroList_userName-minWidth">
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3]%>" value="2">
                  <img src="../ringi/images/classic/icon_stamp_ok_20.png" class="classic-display" alt="<gsmsg:write key="rng.41" />" >
                  <img src="../ringi/images/original/icon_stamp_ok_20.png" class="original-display" alt="<gsmsg:write key="rng.41" />" >
                </logic:equal>
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3]%>" value="4">
                  <img src="../ringi/images/classic/icon_stamp_ok_20.png" class="classic-display" alt="<gsmsg:write key="rng.41" />" >
                  <img src="../ringi/images/original/icon_stamp_ok_20.png" class="original-display" alt="<gsmsg:write key="rng.41" />" >
                </logic:equal>
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3]%>" value="3">
                  <img src="../ringi/images/classic/icon_stamp_ng_20.png" class="classic-display" alt="<gsmsg:write key="rng.rng030.12" />" >
                  <img src="../ringi/images/original/icon_stamp_ng_20.png" class="original-display" alt="<gsmsg:write key="rng.41" />">
                </logic:equal>
                <a href="#!" class="atesakiRngLink cl_fontBody" onClick="openUserInfoWindow(<bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[12]%>"/>);">
                  <ringi:rng030_keiroUser bean="<%=(Rng030KeiroParam) keiroGroup %>" lineProNameSingi="<%= lineProNameSingi%>" singiName="<%= singiParam[0]%>" />
                </a>
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[5]%>" value="0">
                  (<a href="#!" class="atesakiRngLink cl_fontBody" onClick="openUserInfoWindow(<bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[13]%>"/>);">
                    <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[1]%>"/>
                  </a>)
                </logic:equal>
                <div>
                  <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[11]%>"/>
                </div>
              </li>
              <li class="fs_13">
                <bean:define id="viewUser" name="rng030Form" property="rng010ViewAccount"/>
                <!-- SID取得用div -->
                <div class="display_n" id="rksSid__<%= lineNoKeiro %><%= lineNoSingi %>"><bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[8]%>"/></div>
                <div class="display_n" id="usrSid__<%= lineNoKeiro %><%= lineNoSingi %>"><bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[12]%>"/></div>
                <div class="display_n" id="rngSid__<%= lineNoKeiro %><%= lineNoSingi %>"><bean:write name="rng030Form" property="rngSid"/></div>
                <!-- ボタン表示 -->
                <bean:define id="editOkFlg" value="0"/>

                <logic:equal name="btn" property="btnCommentDispFlg" value="1">
                  <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[12] %>" value="<%= viewUser.toString() %>">
                    <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3] %>" value="2">
                      <bean:define id="editOkFlg" value="1"/>
                    </logic:equal>
                    <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3] %>" value="3">
                      <bean:define id="editOkFlg" value="1"/>
                    </logic:equal>
                    <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3] %>" value="6">
                      <bean:define id="editOkFlg" value="1"/>
                    </logic:equal>
                    <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3] %>" value="4">
                      <bean:define id="editOkFlg" value="1"/>
                    </logic:equal>
                    <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[3] %>" value="7">
                      <bean:define id="editOkFlg" value="1"/>
                    </logic:equal>
                  </logic:equal>
                </logic:equal>

                <logic:equal name="editOkFlg" value="1">
                  <div id="<%= lineNoKeiro %><%= lineNoSingi %>">
                    <button id="edit__<%= lineNoKeiro %><%= lineNoSingi %>" type="button" class="js_commentBtn baseBtn" value="<gsmsg:write key="cmn.edit" />"><gsmsg:write key="cmn.edit" /></button>
                    <button id="kakutei__<%= lineNoKeiro %><%= lineNoSingi %>" type="button" class="js_kakuteiBtn baseBtn" value="<gsmsg:write key="cmn.final" />"><gsmsg:write key="cmn.final" /></button>
                    <button id="cansel__<%= lineNoKeiro %><%= lineNoSingi %>" type="button" class="js_canselBtn baseBtn" value="<gsmsg:write key="cmn.cancel" />"><gsmsg:write key="cmn.cancel" /></button>
                  </div>
                </logic:equal>
                <!-- 表示用コメント -->
                <div id="comment__<%= lineNoKeiro %><%= lineNoSingi %>">
                  <span id="comspan__<%= lineNoKeiro %><%= lineNoSingi %>"><bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[2]%>"/></span>
                </div>
                <!-- 編集用コメント -->
                <div id="text__<%= lineNoKeiro %><%= lineNoSingi %>" class="display_n">
                  <textarea id="cmt__<%= lineNoKeiro %><%= lineNoSingi %>" class="wp400" rows="4">
                  </textarea>
                </div>
                <!-- 詳細表示リンク -->
                <div id="linkTxt__<%= lineNoKeiro %><%= lineNoSingi %>">
                  <div id="disp__<%= lineNoKeiro %><%= lineNoSingi %>" class="display_n">
                    <a href="#!" onclick="showDisp.call(this,'<%= lineNoKeiro %>','<%= lineNoSingi %>');">
                      <span id="linkDsp__<%= lineNoKeiro %><%= lineNoKeiro %>"><gsmsg:write key="bmk.bmk010.03"/></span>
                    </a>
                  </div>
                </div>
                <!-- 文字数警告 -->
                <div id="alert__<%= lineNoKeiro %><%= lineNoSingi %>" class="display_n">
                  <span class="cl_fontWarn"><gsmsg:write key="rng.rng030.27"/></span>
                </div>
                <!-- 表示用添付 -->
                <div id="temp__<%= lineNoKeiro %><%= lineNoSingi %>" class="mt5">
                  <logic:iterate id="tmpFile" name="keiroGroup" property="<%= lineProNameSingi + singiParam[8]%>">
                    <file-preview url="../ringi/rng030.do?CMD=fileDownload&rng030fileId=<bean:write name="tmpFile" property="binSid" />&rngSid=<bean:write name="rng030Form" property="rngSid"/>&rng010ViewAccount=<bean:write name="rng030Form" property="rng010ViewAccount"/>" filename="<bean:write name="tmpFile" property="binFileName" />"}>
                      <img class="classic-display" src="../common/images/classic/icon_temp_file.gif">
                      <img class="original-display" src="../common/images/original/icon_attach.png">
                      <a href="#!" onClick="return fileNameClick(<bean:write name="tmpFile" property="binSid" />);"><span class="small_link"><u>
                        <bean:write name="tmpFile" property="binFileName" /><bean:write name="tmpFile" property="binFileSizeDsp" /></u></span>
                      </a>
                    </file-preview>
                    <br>
                  </logic:iterate>
                </div>
                <!-- 編集用添付 -->
                <div id="tempName__<%= lineNoKeiro %><%= lineNoSingi %>" class="mt5 display_n">
                  <bean:define id="rowName" type="String"><%=Rng030Action.SUBDIRNAME_COMMENTEDIT_ROW%><bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[8]%>"/><bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[12]%>"/></bean:define>
                  <gsform:file_opnTempButton selectListName="<%=\"rng030selectFiles\"+ lineNoKeiro+ lineNoSingi %>" pluginId="<%=RngConst.PLUGIN_ID_RINGI %>" tempDirId="<%=Rng030Action.TEMPDIRID %>" subDirList="<%=Arrays.asList(new String[] {Rng030Action.SUBDIRNAME_COMMENTEDIT, rowName}) %>"></gsform:file_opnTempButton>
                </div>
              </li>
              <li class="wp130 txt_c keiroList_confDate-minWidth">
                <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="6">
                  <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[6]%>" value="0">
                    <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[4]%>"/><br>
                    <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[10]%>"/>
                  </logic:equal>
                </logic:notEqual>
              </li>
            </dl>
          </div>
        </logic:iterate>
      </logic:equal>
      <!-- グループ承認end -->
    </logic:notEqual>
  </li>
</dl>
<div id="singiCount<%=lineNoKeiro %>" class="display_n"><%= singiCount%></div>
