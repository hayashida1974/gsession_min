<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.rng.RngConst" %>
<%@ page import="jp.groupsession.v2.rng.rng030.Rng030Form" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<div id="koetuPop" title="後閲指示範囲選択" class="display_n">
  <!-- 後閲ボタン -->
  <table class="w100">
    <tr>
      <td class="flo_r">
        <button value="<gsmsg:write key="rng.rng030.18"/>" class="baseBtn" onClick="buttonPush('koetu');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_syonin.png" alt="<gsmsg:write key="rng.rng030.18" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_syonin.png" alt="<gsmsg:write key="rng.rng030.18" />">
          <gsmsg:write key="rng.rng030.18"/>
        </button>
      </td>
    </tr>
  </table>

  <!-- リスト -->
  <table class="table-left w100">
    <% String[] keiroParam = {"keiroName","keiroCount","keiroMessage","keiroStatus","keiroSingi","keiroSort","keiroKoetu","keiroStepSid","groupDelFlg"}; %>
    <% String[] singiParam = {"singiName","singiDairi","singiComment","singiStatus","singiDate","singiDairiFlg","singiCheckFlg","singiCommentFlg","tmpFileList","userJkbn","userUkoFlg"}; %>
    <% int radioFlg = 1; %>
    <% int radioFirstFlg = 0; %>
    <logic:iterate id="keiroGroup" name="rng030Form" property="rng030keiroList" indexId="Idx">
      <% String lineNoKeiro = String.valueOf(Idx.intValue());%>
      <% String lineProNameKeiro = "rng030keiroList[" + lineNoKeiro + "].";%>
      <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="1">
        <%radioFlg = 0; %>
      </logic:equal>
      <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[6]%>" value="1">
        <%radioFlg = 1; %>
      </logic:equal>
      <bean:define id="rks" name="rng030Form" property="rng030RksSid"/>
      <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[7]%>" value="<%=rks.toString() %>">
        <%radioFlg = 1; %>
      </logic:equal>
      <!-- 申請者以外 -->
      <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[4]%>" value = "2">
        <!--直列経路 又は グループを選択時にユーザが1人のみ  -->
        <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[1]%>" value="1">
          <tr>
              <% if (radioFlg == 0) { %>
                <th class="w5 txt_c js_select_radio_koetu cursor_p">
                  <bean:define id="keiroStepSid" name="keiroGroup" property="keiroStepSid"/>
                  <% if(radioFirstFlg == 0) { %>
                    <input type="radio" name="rng030koetuNo" value="<%=keiroStepSid %>" checked>
                    <% radioFirstFlg = 1; %>
                  <%} else { %>
                    <input type="radio" name="rng030koetuNo" value="<%=keiroStepSid %>">
                  <%} %>
                    <input type="hidden" name="select_radio" value="<bean:write name="rng030Form"  property="<%= lineProNameKeiro + keiroParam[7]%>"/>">
                </th>
              <%} else { %>
                <th class="w5"></th>
              <%}%>
            <td class="w10 txt_c">
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="2">
                <img src="../ringi/images/classic/icon_stamp_ok_20.png" class="classic-display img_bottom" alt="<gsmsg:write key="rng.41" />">
                <img src="../ringi/images/original/icon_stamp_ok_20.png" class="original-display img_bottom" alt="<gsmsg:write key="rng.41" />" >
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="6">
                <img src="../ringi/images/classic/icon_stamp_ok_20.png" class="classic-display img_bottom" alt="<gsmsg:write key="rng.41" />">
                <img src="../ringi/images/original/icon_stamp_ok_20.png" class="original-display img_bottom" alt="<gsmsg:write key="rng.41" />" >
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="3">
                <img src="../ringi/images/classic/icon_stamp_ng_20.png" class="classic-display img_bottom" alt="<gsmsg:write key="rng.rng030.12" />">
                <img src="../ringi/images/original/icon_stamp_ng_20.png" class="original-display img_bottom" alt="<gsmsg:write key="rng.41" />">
              </logic:equal>
            </td>
            <td>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[8]%>" value="9">
                <del><bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/></del></span>
              </logic:equal>
              <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[8]%>" value="9">
                <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/></span>
              </logic:notEqual>
              <logic:iterate id="singi" name="keiroGroup" property="singiList" indexId="sIdx">
                <% String lineNoSingi = String.valueOf(sIdx.intValue()); %>
                <% String lineProNameSingi = "singiList[" + lineNoSingi + "].";%>
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[10]%>" value="0">
                  <div>
                </logic:equal>
                <logic:notEqual name="keiroGroup" property="<%= lineProNameSingi + singiParam[10]%>" value="0">
                  <div class="mukoUserOption">
                </logic:notEqual>
                    <logic:notEqual name="keiroGroup" property="<%= lineProNameSingi + singiParam[9]%>" value="0">
                      <del><bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[0]%>"/></del>
                    </logic:notEqual>
                    <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[9]%>" value="0">
                      <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[0]%>"/>
                    </logic:equal>
                  </span>
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
              <% if (radioFlg == 0) { %>
                <th id="koetuKeiroCount_<%= lineNoKeiro %>" class="w5 txt_c js_select_radio_koetu cursor_p">
                  <% if(radioFirstFlg == 0) { %>
                    <logic:equal name="rng030Form" property="rng030apprUsrJkbn" value="0">
                      <input type="radio" name="rng030koetuNo" value="Idx" checked>
                    </logic:equal>
                    <% radioFirstFlg = 1; %>
                  <%} else { %>
                    <logic:equal name="rng030Form" property="rng030apprUsrJkbn" value="0">
                      <input type="radio" name="rng030koetuNo" value="Idx">
                    </logic:equal>
                  <%} %>
                  <input type="hidden" name="select_radio" value="<bean:write name="rng030Form"  property="<%= lineProNameKeiro + keiroParam[7]%>"/>">
                </th>
              <%} else { %>
                <th class="w5"></th>
              <%}%>
            <td class="w10 txt_c">
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="2">
                <img src="../ringi/images/classic/icon_stamp_ok_20.png" class="classic-display img_bottom" alt="<gsmsg:write key="rng.41" />">
                <img src="../ringi/images/original/icon_stamp_ok_20.png" class="original-display img_bottom" alt="<gsmsg:write key="rng.41" />" >
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="6">
                <img src="../ringi/images/classic/icon_stamp_ok_20.png" class="classic-display img_bottom" alt="<gsmsg:write key="rng.41" />">
                <img src="../ringi/images/original/icon_stamp_ok_20.png" class="original-display img_bottom" alt="<gsmsg:write key="rng.41" />" >
              </logic:equal>
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[3]%>" value="3">
                <img src="../ringi/images/classic/icon_stamp_ng_20.png" class="classic-display img_bottom" alt="<gsmsg:write key="rng.rng030.12" />">
                <img src="../ringi/images/original/icon_stamp_ng_20.png" class="original-display img_bottom" alt="<gsmsg:write key="rng.41" />">
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
                <a href="#!" onclick="koetuPop('<%= lineNoKeiro %>','<bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[1]%>"/>');">
                  <span id="koetuLink_<%= lineNoKeiro %>" class="cl_linkDef"><gsmsg:write key="api.cmn.view"/></span>
                </a>
              </span>
              <div id="koetuDsp_<%= lineNoKeiro %>" class="display_n">
                <logic:iterate id="singi" name="keiroGroup" property="singiList" indexId="sIdx">
                  <div>
                    <% String lineNoSingi = String.valueOf(sIdx.intValue()); %>
                    <% String lineProNameSingi = "singiList[" + lineNoSingi + "].";%>
                    <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[10]%>" value="0">
                      <span>
                    </logic:equal>
                    <logic:notEqual name="keiroGroup" property="<%= lineProNameSingi + singiParam[10]%>" value="0">
                      <span class="mukoUserOption">
                    </logic:notEqual>
                        <logic:notEqual name="keiroGroup" property="<%= lineProNameSingi + singiParam[9]%>" value="0">
                          <del><bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[0]%>"/></del>
                        </logic:notEqual>
                        <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[9]%>" value="0">
                          <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[0]%>"/>
                        </logic:equal>
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

  <!-- 後閲ボタン -->
  <table class="w100">
    <tr>
      <td class="flo_r">
        <button value="<gsmsg:write key="rng.rng030.18"/>" class="baseBtn" onClick="buttonPush('koetu');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_sinsei_syonin.png" alt="<gsmsg:write key="rng.rng030.18" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_syonin.png" alt="<gsmsg:write key="rng.rng030.18" />">
          <gsmsg:write key="rng.rng030.18"/>
        </button>
      </td>
    </tr>
  </table>

</div>
