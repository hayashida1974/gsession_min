<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>

<%@ page import="jp.groupsession.v2.rng.RngConst" %>
<%@ page import="jp.groupsession.v2.rng.rng030.Rng030Form" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.rng.rng030.Rng030KeiroParam"%>
<div>
  <html:errors/>
  <!-- 経路追加ボタン -->
  <table class="w100">
    <tr>
      <td class="flo_r">
        <button value="<gsmsg:write key="rng.rng020.02"/>" class="baseBtn" onClick="commitAddKeiroPopup();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="rng.rng020.02" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="rng.rng020.02" />">
          <gsmsg:write key="rng.rng020.02"/>
        </button>
      </td>
    </tr>
  </table>
  <form>
  <!-- リスト -->
  <table class="table-left w100">
    <% String[] keiroParam = {"keiroName","keiroCount","keiroMessage","keiroStatus","keiroSingi","keiroSort","keiroKoetuHani","groupDelFlg"}; %>
    <% String[] singiParam = {"singiName","singiDairi","singiComment","singiStatus","singiDate","singiDairiFlg","singiCheckFlg","singiCommentFlg","tmpFileList","userJkbn","userUkoFlg"}; %>
    <% int radioFlg = 1; %>
    <% int radioFirstFlg = 0; %>
    <logic:iterate id="keiroGroup" name="rng030Form" property="rng030keiroList" indexId="Idx">
      <% String lineNoKeiro = String.valueOf(Idx.intValue());%>
      <% String lineProNameKeiro = "rng030keiroList[" + lineNoKeiro + "].";%>
      <!-- 申請者以外 -->
      <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[4]%>" value = "2">
        <!-- 直列経路 -->
        <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[1]%>" value="1">
          <tr>
            <th class="w5 txt_c">
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
            </th>
            <td class="tc_l">
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[7]%>" value="9">
                <del><bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/></del>
              </logic:equal>
              <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[7]%>" value="9">
                <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/>
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
                  </div>
                <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[5]%>" value="0">
                  <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[1]%>"/>
                </logic:equal>
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
            <th id="addKeiroCount_<%= lineNoKeiro %>" class="w5 txt_c">
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
            </th>
            <td class="tc_l">
              <logic:equal name="rng030Form" property="<%= lineProNameKeiro + keiroParam[7]%>" value="9">
                <del><bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/></del>
              </logic:equal>
              <logic:notEqual name="rng030Form" property="<%= lineProNameKeiro + keiroParam[7]%>" value="9">
                <bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[0]%>"/>
              </logic:notEqual>
              <span id="link_area_<%= lineNoKeiro %>">
                <a href="#!" onclick="keiroPop('<%= lineNoKeiro %>','<bean:write name="rng030Form" property="<%= lineProNameKeiro + keiroParam[1]%>"/>');">
                  <span id="keiroLink_<%= lineNoKeiro %>" class="cl_linkDef"><gsmsg:write key="api.cmn.view"/></span>
                </a>
              </span>
              <div class="display_n" id="keiroDsp_<%= lineNoKeiro %>">
                <logic:iterate id="singi" name="keiroGroup" property="singiList" indexId="sIdx">
                  <div>
                    <% String lineNoSingi = String.valueOf(sIdx.intValue()); %>
                    <% String lineProNameSingi = "singiList[" + lineNoSingi + "].";%>
                    <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[10]%>" value="0">
                      <span class="">
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
                    <logic:equal name="keiroGroup" property="<%= lineProNameSingi + singiParam[5]%>" value="0">
                      <bean:write name="keiroGroup" property="<%= lineProNameSingi + singiParam[1]%>"/>
                    </logic:equal>
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
      <logic:equal name="keiroGroup" property="keiroAddible" value="<%=String.valueOf(RngConst.RNG_ABLE_ADDKEIRO) %>">
        <tr>
          <th class="w5 txt_c"></th>
          <td class="txt_l p0">
            <ringi:rng030_keiroAddibleRow name="rng030Form"  property="<%=\"rng030addKeiro(\" + ((Rng030KeiroParam) keiroGroup).getKeiroStepSid() + \")\" %>" block="<%=((Rng030KeiroParam) keiroGroup).getAddBlock() %>"/>
          </td>
        </tr>
      </logic:equal>
    </logic:iterate>
  </table>
  </form>
  <table class="w100">
    <tr>
      <td class="flo_r">
        <button value="<gsmsg:write key="rng.rng020.02"/>" class="baseBtn" onClick="commitAddKeiroPopup();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="rng.rng020.02" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="rng.rng020.02" />">
          <gsmsg:write key="rng.rng020.02"/>
        </button>
      </td>
    </tr>
  </table>

</div>
