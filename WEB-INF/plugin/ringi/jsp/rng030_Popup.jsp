<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.rng.RngConst" %>
<%@ page import="jp.groupsession.v2.rng.rng030.Rng030Form" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<div id="sasimodosiPop" title="差し戻し先選択" class="display_n">
  <!-- 差し戻しボタン -->
  <table class="w100">
    <tr>
      <td>
        <div class="flo_r">
          <button value="<gsmsg:write key="rng.rng030.08"/>" class="baseBtn" onClick="buttonPush('reflection');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_sashimodoshi.png" alt="<gsmsg:write key="rng.rng030.08" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_sashimodoshi.png" alt="<gsmsg:write key="rng.rng030.08" />">
            <gsmsg:write key="rng.rng030.08"/>
          </button>
        </div>
      </td>
    </tr>
  </table>

  <!-- リスト -->
  <table class="table-left w100">
    <% String[] keiroParam = {"keiroName","keiroCount","keiroMessage","keiroStatus","keiroSingi","keiroSort","keiroDelFlg","keiroStepSid","groupDelFlg"}; %>
    <% String[] singiParam = {"singiName","singiDairi","singiComment","singiStatus","singiDate","singiDairiFlg","singiCheckFlg","singiCommentFlg","tmpFileList","userJkbn","userUkoFlg"}; %>
    <% int radioFlg = 0; %>
    <!-- 申請者 -->
    <tr>
        <th class="w5 txt_c js_radio_select">
          <logic:equal name="rng030Form" property="rng030apprUsrJkbn" value="0">
            <html:radio name="rng030Form" property="rng030SasiNo" value="-1" />
            <input type="hidden" name="select_radio" value="-1">
            <input type="hidden" name="select_radio_none" value="none">
          </logic:equal>
        </th>
      <td class="w10 txt_c">
        <gsmsg:write key="rng.47" />
      </td>
      <td>
        <logic:notEqual name="rng030Form" property="rng030apprUsrUkoFlg" value="0">
          <span class="mukoUserOption">
            <logic:notEqual name="rng030Form" property="rng030apprUsrJkbn" value="0">
              <del><bean:write name="rng030Form" property="rng030apprUser" /></del>
            </logic:notEqual>
            <logic:equal name="rng030Form" property="rng030apprUsrJkbn" value="0">
              <bean:write name="rng030Form" property="rng030apprUser" />
            </logic:equal>
          </span>
        </logic:notEqual>
        <logic:equal name="rng030Form" property="rng030apprUsrUkoFlg" value="0">
          <logic:notEqual name="rng030Form" property="rng030apprUsrJkbn" value="0">
            <del><bean:write name="rng030Form" property="rng030apprUser" /></del>
          </logic:notEqual>
          <logic:equal name="rng030Form" property="rng030apprUsrJkbn" value="0">
            <bean:write name="rng030Form" property="rng030apprUser" />
          </logic:equal>
        </logic:equal>
      </td>
    </tr>
    <logic:iterate id="keiroGroup" name="rng030Form" property="rng030keiroList" indexId="Idx">
      <bean:define id="keiroStepSid" name="keiroGroup" property="keiroStepSid" />
      <% String lineNoKeiro = String.valueOf(Idx.intValue());%>
      <% String lineProNameKeiro = "rng030keiroList[" + lineNoKeiro + "].";%>
      <% String lineNextNoKeiro = String.valueOf(Idx.intValue()+1);%>
      <% String lineNextProNameKeiro = "rng030keiroList[" + lineNextNoKeiro + "].";%>
      <!-- 審議種別が申請者以外 -->
      <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[4]%>" value = "2">
        <!-- 直列経路 もしくは グループ選択にて経路内のユーザが1人の場合-->
        <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[1]%>" value="1">
          <tr>
            <th class="w5 txt_c js_radio_select">
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="1">
                <% radioFlg = 1; %>
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineNextProNameKeiro + keiroParam[3]%>"  value="1">
                <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[6]%>"  value="0">
                  <html:radio name="rng030Form" property="rng030SasiNo" value="<%=String.valueOf(keiroStepSid) %>"/>
                  <input type="hidden" name="select_radio" value="<%=String.valueOf(keiroStepSid) %>">
                  <input type="hidden" name="select_radio_none" value="none">
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="rng030Form" property="<%= lineNextProNameKeiro + keiroParam[3]%>"  value="1">
                <% if (radioFlg != 1 ) {%>
                  <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[6]%>"  value="0">
                    <html:radio name="rng030Form" property="rng030SasiNo" value="<%=String.valueOf(keiroStepSid) %>" />
                    <input type="hidden" name="select_radio" value="<%=String.valueOf(keiroStepSid) %>">
                    <input type="hidden" name="select_radio_none" value="none">
                  </logic:equal>
                <% } %>
              </logic:notEqual>
            </th>
            <td class="w10 txt_c">
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="2">
                <img src="../ringi/images/classic/icon_stamp_ok_20.png" class="classic-display img_bottom " alt="<gsmsg:write key="rng.41" />">
                <img src="../ringi/images/original/icon_stamp_ok_20.png" class="original-display img_bottom " alt="<gsmsg:write key="rng.41" />" >
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="6">
                <img src="../ringi/images/classic/icon_stamp_ok_20.png" class="classic-display img_bottom " alt="<gsmsg:write key="rng.41" />">
                <img src="../ringi/images/original/icon_stamp_ok_20.png" class="original-display img_bottom " alt="<gsmsg:write key="rng.41" />" >
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="3">
                <img src="../ringi/images/classic/icon_stamp_ng_20.png" class="classic-display img_bottom " alt="<gsmsg:write key="rng.rng030.12" />">
                <img src="../ringi/images/original/icon_stamp_ng_20.png" class="original-display img_bottom " alt="<gsmsg:write key="rng.41" />">
              </logic:equal>
            </td>
            <td>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[8]%>" value="9">
                <del><bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/></del>
              </logic:equal>
              <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[8]%>" value="9">
                <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/>
              </logic:notEqual>
              <logic:iterate id="singi" name="keiroGroup" property="singiList" indexId="sIdx">
                <% String lineNoSingi = String.valueOf(sIdx.intValue()); %>
                <% String lineProNameSingi = "singiList[" + lineNoSingi + "].";%>
                <bean:define id="mukoUser_class" value=""/>
                <logic:notEqual name="keiroGroup" property="<%= lineProNameSingi + singiParam[10]%>" value="0">
                  <bean:define id="mukoUser_class" value="mukoUserOption"/>
                </logic:notEqual>
                <div class="<%=mukoUser_class%>">
                  <logic:notEqual name="keiroGroup" property="<%= lineProNameSingi + singiParam[9]%>" value="0">
                    <del><bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[0]%>"/></del>
                  </logic:notEqual>
                  <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[9]%>" value="0">
                    <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[0]%>"/>
                  </logic:equal>
                </div>
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[7]%>" value="0">
                  <div class="ml10">
                    <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[2]%>"/>
                  </div>
                </logic:equal>
              </logic:iterate>
            </td>
          </tr>
        </logic:equal>

        <!-- 並列経路 -->
        <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[1]%>" value ="1">
          <tr>
            <th id="keiroCountPop_<%= lineNoKeiro %>" class="w5 txt_c js_radio_select">
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="1">
                <% radioFlg = 1; %>
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineNextProNameKeiro + keiroParam[3]%>"  value="1">
                <div id="group" class="display_n">1</div>
                <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[6]%>"  value="0">
                  <html:radio name="rng030Form" property="rng030SasiNo" value="<%=String.valueOf(keiroStepSid) %>" />
                  <input type="hidden" name="select_radio" value="<%=String.valueOf(keiroStepSid) %>">
                  <input type="hidden" name="select_radio_none" value="">
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="rng030Form" property="<%= lineNextProNameKeiro + keiroParam[3]%>"  value="1">
                <% if (radioFlg != 1 ) {%>
                  <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[6]%>"  value="0">
                    <html:radio name="rng030Form" property="rng030SasiNo" value="<%=String.valueOf(keiroStepSid) %>" />
                    <input type="hidden" name="select_radio" value="<%=String.valueOf(keiroStepSid) %>">
                    <input type="hidden" name="select_radio_none" value="">
                  </logic:equal>
                <%} %>
              </logic:notEqual>
            </th>
            <td class="w10 txt_c">
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="2">
                <img src="../ringi/images/classic/icon_stamp_ok_20.png" class="classic-display img_bottom " alt="<gsmsg:write key="rng.41" />">
                <img src="../ringi/images/original/icon_stamp_ok_20.png" class="original-display img_bottom " alt="<gsmsg:write key="rng.41" />" >
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="6">
                <img src="../ringi/images/classic/icon_stamp_ok_20.png" class="classic-display img_bottom " alt="<gsmsg:write key="rng.41" />">
                <img src="../ringi/images/original/icon_stamp_ok_20.png" class="original-display img_bottom " alt="<gsmsg:write key="rng.41" />" >
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="3">
                <img src="../ringi/images/classic/icon_stamp_ng_20.png" class="classic-display img_bottom " alt="<gsmsg:write key="rng.rng030.12" />">
                <img src="../ringi/images/original/icon_stamp_ng_20.png" class="original-display img_bottom " alt="<gsmsg:write key="rng.41" />">
              </logic:equal>
            </td>
            <td>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[8]%>" value="9">
                <del><bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/></del>
              </logic:equal>
              <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[8]%>" value="9">
                <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/>
              </logic:notEqual>
              <span id="link_area_<%= lineNoKeiro %>">
                <a href="#!" onclick="sasiPop('<%= lineNoKeiro %>','<bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[1]%>"/>');">
                  <span id="linkPop_<%= lineNoKeiro %>" class="cl_linkDef"><gsmsg:write key="api.cmn.view"/></span>
                </a>
              </span>
              <div id="dspPop_<%= lineNoKeiro %>" class="display_n">
                <logic:iterate id="singi" name="keiroGroup" property="singiList" indexId="sIdx">
                  <div>
                    <% String lineNoSingi = String.valueOf(sIdx.intValue()); %>
                    <% String lineProNameSingi = "singiList[" + lineNoSingi + "].";%>
                    <span>
                      <bean:define id="mukoUser_class_grp" value=""/>
                      <bean:define id="mukoUser_class" value=""/>
                      <logic:notEqual name="keiroGroup" property="<%= lineProNameSingi + singiParam[10]%>" value="0">
                        <bean:define id="mukoUser_class" value="mukoUserOption"/>
                      </logic:notEqual>
                      <span class="<%=mukoUser_class_grp%> <%=mukoUser_class%>">
                        <logic:notEqual name="keiroGroup" property="<%= lineProNameSingi + singiParam[9]%>" value="0">
                          <del><bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[0]%>"/></del>
                        </logic:notEqual>
                        <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[9]%>" value="0">
                          <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[0]%>"/>
                        </logic:equal>
                      </span>
                    </span>
                    <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[7]%>" value="0">
                      <div class="ml10">
                        <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[2]%>"/>
                      </div>
                    </logic:equal>
                  </div>
                </logic:iterate>
              </div>
            </td>
          </tr>
        </logic:notEqual>
      </logic:notEqual>
    </logic:iterate>
  </table>

  <table class="w100">
    <tr>
      <td>
        <div class="flo_r">
          <button value="<gsmsg:write key="rng.rng030.08"/>" class="baseBtn" onClick="buttonPush('reflection');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_sashimodoshi.png" alt="<gsmsg:write key="rng.rng030.08" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_sashimodoshi.png" alt="<gsmsg:write key="rng.rng030.08" />">
            <gsmsg:write key="rng.rng030.08"/>
          </button>
        </div>
      </td>
    </tr>
  </table>
</div>
