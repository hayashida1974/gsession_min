<%@page import="jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel"%>
<%@page import="jp.groupsession.v2.rng.RngConst"%>
<%@page import="jp.co.sjts.util.NullDefault"%>
<%@page import="jp.groupsession.v2.rng.rng110keiro.KeiroInCondition"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script>
  $(function() {
    //  複数選択区分でcssが表示判定できるようルート要素のデータ属性に保管
    $('input[name="multisel"]').change(function() {
      $('#keiro_dialog').attr('data-multisel', $(this).val());
    });
    <logic:equal parameter="CMD" value="dialogSubmit">
      //ダイアログOKクリック時バリデートエラーがない場合ダイアログを閉じる
      <logic:messagesNotPresent message="false">
        $('#keiro_pref_dialog').data('submitClose')();
      </logic:messagesNotPresent>
    </logic:equal>
  });
</script>

<html:errors />
<form id="keiro_dialog" class="keiro_dialog" data-multisel="<bean:write name="rng110keiroDialogForm" property="multisel" />">
  <html:hidden name="rng110keiroDialogForm" property="keiroRootType" />
  <html:hidden name="rng110keiroDialogForm" property="keiroShareRange" />
  <html:radio name="rng110keiroDialogForm" property="keiroKbn" styleId="keiro_kbn_0" styleClass="keiro_kbn-0" value="0" />
  <label for="keiro_kbn_0">
    <gsmsg:write key="rng.rng110.08"/>
  </label>
  <html:radio name="rng110keiroDialogForm" property="keiroKbn" styleId="keiro_kbn_1" styleClass="keiro_kbn-1" value="1" />
  <label for="keiro_kbn_1">
    <gsmsg:write key="rng.rng110.03"/>
  </label>
  <html:radio name="rng110keiroDialogForm" property="keiroKbn" styleId="keiro_kbn_2" styleClass="keiro_kbn-2" value="2" />
  <label for="keiro_kbn_2">
    <gsmsg:write key="rng.rng110.04"/>
  </label>
  <html:radio name="rng110keiroDialogForm" property="keiroKbn" styleId="keiro_kbn_3" styleClass="keiro_kbn-3" value="3" />
  <label for="keiro_kbn_3">
    <gsmsg:write key="rng.rng110.06"/>
  </label>
  <html:radio name="rng110keiroDialogForm" property="keiroKbn" styleId="keiro_kbn_4" styleClass="keiro_kbn-4" value="4" />
  <label for="keiro_kbn_4">
    <gsmsg:write key="rng.rng110.07"/>
  </label>
  <html:radio name="rng110keiroDialogForm" property="keiroKbn" styleId="keiro_kbn_5" styleClass="keiro_kbn-5" value="5" />
  <label for="keiro_kbn_5">
    <gsmsg:write key="rng.rng110.05"/>
  </label>
  <div>
    <div class="row keiro_kbn">
      <gsmsg:write key="rng.rng110.09"/>
      <div class="content">
        <span class="select_free">
          <gsmsg:write key="rng.rng110.08"/>
        </span>
        <span class="target_user">
          <gsmsg:write key="rng.rng110.03"/>
        </span>
        <span class="target_pos">
          <gsmsg:write key="rng.rng110.04"/>
        </span>
        <span class="select_user">
          <gsmsg:write key="rng.rng110.06"/>
        </span>
        <span class="select_group">
          <gsmsg:write key="rng.rng110.07"/>
        </span>
        <span class="auto_boss">
          <gsmsg:write key="rng.rng110.05"/>
        </span>
      </div>
    </div>
    <logic:equal name="rng110keiroDialogForm" property="keiroShareRange" value="1">
      <!--経路利用条件 -->
        <div class="row keiro_incondition">
          <gsmsg:write key="rng.rng110.10"/>
          <div class="content">
            <logic:iterate id="inCondKV" name="rng110keiroDialogForm" property="inCondMap" type="Entry">
              <div name="<bean:write name="inCondKV" property="key" />" class="inCond borC_light">
                <span class="selectval" <logic:notEmpty name="inCondKV" property="value.condKbn.selected">value="<bean:write name="inCondKV" property="value.condKbn.selected" />"</logic:notEmpty> />
                <html:select name="rng110keiroDialogForm" property="<%=\"inCond(\" + inCondKV.getKey() +\").condKbn.selected\" %>" styleClass="inCondKbn ml0 txt_t" >
                  <html:optionsCollection name="inCondKV" property="value.condKbn.allList"/>
                </html:select>
                <logic:notEqual name="inCondKV" property="key" value="template">
                  <div class="inCondGroup pos_rel">
                    <input type="hidden" name="<%="inCond(" + inCondKV.getKey() +").selGrp.paramName" %>" value="<%="inCond(" + inCondKV.getKey() +").selGrp"%>"/>
                    <ui:usrgrpselector name="rng110keiroDialogForm" property="<%=\"inCond(\" + inCondKV.getKey() +\").selGrp\" %>"  styleClass="hp160 inCondGroup_selector" />
                  </div>
                  <div class="inCondPos pos_rel">
                    <input type="hidden" name="<%="inCond(" + inCondKV.getKey() +").selPos.paramName" %>" value="<%="inCond(" + inCondKV.getKey() +").selPos"%>"/>
                    <ui:multiselector name="rng110keiroDialogForm" property="<%=\"inCond(\" + inCondKV.getKey() +\").selPos\" %>"  styleClass="hp160 inCondPos_selector" />
                  </div>
                </logic:notEqual>
                <span class="inCondForm mt5">
                  <gsmsg:write key="rng.rng110.31"/><html:text name="rng110keiroDialogForm" styleClass="mr5 ml5" property="<%=\"inCond(\" + inCondKV.getKey() +\").formId\" %>" maxlength="<%=String.valueOf(KeiroInCondition.MAXTEXTSIZE_INCOND_FORMID) %>" size="10"/>
                  <gsmsg:write key="rng.rng110.32"/><html:text name="rng110keiroDialogForm" styleClass="mr5 ml5" property="<%=\"inCond(\" + inCondKV.getKey() +\").formValue\" %>" maxlength="<%=String.valueOf(KeiroInCondition.MAXTEXTSIZE_INCOND_VALUE) %>" size="10" />
                  <html:select name="rng110keiroDialogForm" property="<%=\"inCond(\" + inCondKV.getKey() +\").comp.selected\" %>">
                    <html:optionsCollection name="inCondKV" property="value.comp.allList"/>
                  </html:select>
                </span>
                <img class="btn_classicImg-display delButton ml5 cursor_p mt5" src="../common/images/classic/icon_delete_2.gif">
                <img class="btn_originalImg-display delButton ml5 cursor_p mt5" src="../common/images/original/icon_delete.png">
              </div>
            </logic:iterate>
          <a href="#!" class="js_addLink notBlock cl_linkDef"><gsmsg:write key="rng.rng110.37"/></a>
        </div>
      </div>
      <!--経路コメント -->
      <div class="row">
        <gsmsg:write key="rng.rng110.43"/>
        <div class="content">
          <html:text name="rng110keiroDialogForm" property="keiroComment" maxlength="200" styleClass="w90" />
        </div>
      </div>
    </logic:equal>
    <!--経路選択 ユーザグループ指定 -->
    <div class="row target_user" >
      <gsmsg:write key="rng.rng110.12"/>
        <div class="content" >
        <bean:define id="usrgrpsel" name="rng110keiroDialogForm" property="usrgrouptarget" type="UserGroupSelectModel"/>
        <ui:usrgrpselector name="rng110keiroDialogForm" property="<%=\"usrgrouptargetSelector\" %>" styleClass="hp160" />
      </div>
    </div>
    <!--経路選択 役職指定 -->
    <div name="target_pos" class="row target_pos " >
      <gsmsg:write key="rng.rng110.12"/>
      <div class="content">
        <% boolean delBtnFlg = false; %>
        <logic:iterate id="pos" name="rng110keiroDialogForm" property="targetposMap" type="Entry">
          <div name="<bean:write name="pos" property="key" />" class="target">
            <div class="verAlignMid">
              <html:select name="rng110keiroDialogForm" property="<%=\"targetpos(\" + pos.getKey() + \").grpSel.selected\" %>" styleClass="wp150">
                <html:optionsCollection name="pos" property="value.grpSel.allList" />
              </html:select>
              <html:select name="rng110keiroDialogForm" styleClass="ml5" property="<%=\"targetpos(\" + pos.getKey() + \").posSel.selected\" %>" >
                <html:optionsCollection name="pos" property="value.posSel.allList" />
              </html:select>
              <% if (delBtnFlg) { %>
                <img class="btn_classicImg-display delButton ml5 cursor_p" src="../common/images/classic/icon_delete_2.gif">
                <img class="btn_originalImg-display delButton ml5 cursor_p" src="../common/images/original/icon_delete.png">
              <% } else { %>
              <%   delBtnFlg = true; %>
              <% } %>
            </div>
          </div>
        </logic:iterate>
        <a href="#!" class="js_addLink notBlock cl_linkDef">
          <gsmsg:write key="rng.rng110.38"/>
        </a>
      </div>
    </div>
    <!--複数選択 -->
    <div class="row keiro_multisel select_user select_group ">
      <gsmsg:write key="enq.enq210.03"/>・<gsmsg:write key="enq.enq210.04"/>
      <br>
      <div class="content verAlignMid">
        <html:radio name="rng110keiroDialogForm" property="multisel" styleId="keiro_multisel_0"  value="0" />
        <label for="keiro_multisel_0">
          <gsmsg:write key="enq.enq210.03"/>
        </label>
        <html:radio name="rng110keiroDialogForm" property="multisel" styleId="keiro_multisel_1" styleClass="ml10" value="1" />
        <label for="keiro_multisel_1">
          <gsmsg:write key="enq.enq210.04"/>
        </label>
      </div>
    </div>
    <!--範囲選択 ユーザ選択 -->
    <div class="row select_user select_free">
      <span class="select_user">
        <gsmsg:write key="rng.rng110.26"/>
      </span>
      <span class="select_free">
        <gsmsg:write key="rng.rng110.39"/>
      </span>
      <div class="content">
        <bean:define id="usrgrpsel" name="rng110keiroDialogForm" property="usrgroupselect" type="UserGroupSelectModel"/>
        <ui:usrgrpselector name="rng110keiroDialogForm" property="<%=\"usrgroupselectSelector\" %>" styleClass="hp160" />
      </div>
    </div>
    <!--範囲選択 グループ選択 -->
    <div class="row select_group">
      <gsmsg:write key="rng.rng110.26"/>
      <div class="content">
        <ui:usrgrpselector name="rng110keiroDialogForm" property="<%=\"groupSelSelector\" %>" styleClass="hp160" />
      </div>
    </div>
    <logic:equal name="rng110keiroDialogForm" property="keiroShareRange" value="1">
      <logic:equal name="rng110keiroDialogForm" property="keiroRootType" value="<%=String.valueOf(RngConst.RNG_RNCTYPE_APPR) %>">
        <!--経路承認条件 -->
        <div class="outcondition row target_user target_pos select_user select_group auto_boss keiro_outcondition">
          <gsmsg:write key="rng.rng110.11"/>
          <br>
          <div class="content verAlignMid">
            <html:radio name="rng110keiroDialogForm" property="outcondition" styleId="keiro_outcondition_0" styleClass="keiro_outcondition-0" value="0" />
            <label for="keiro_outcondition_0">
              <gsmsg:write key="rng.rng110.20"/>
            </label>
            <html:radio name="rng110keiroDialogForm" property="outcondition" styleId="keiro_outcondition_1" styleClass="ml10 keiro_outcondition-1" value="1" />
            <label for="keiro_outcondition_1">
              <gsmsg:write key="rng.rng110.21"/>
            </label>
            <html:radio name="rng110keiroDialogForm" property="outcondition" styleId="keiro_outcondition_2" styleClass="ml10 keiro_outcondition-2" value="2" />
            <label for="keiro_outcondition_2">
              <gsmsg:write key="rng.rng110.22"/>
            </label>
            <html:radio name="rng110keiroDialogForm" property="outcondition" styleId="keiro_outcondition_3" styleClass="ml10 keiro_outcondition-3" value="3" />
            <label for="keiro_outcondition_3">
              <gsmsg:write key="rng.rng110.23"/>
            </label>
            <div name="keiro_outcondition_threshold">
              &nbsp;&nbsp;<gsmsg:write key="cmn.threshold"/>:<html:text name="rng110keiroDialogForm" property="outcond_threshold" styleClass="wp30" maxlength="3" />
              <span class="tanni_2">
                <gsmsg:write key="cmn.persons"/>
              </span>
              <span class="tanni_3">%</span>
            </div>
          </div>
        </div>
      </logic:equal>
    </logic:equal>
    <logic:equal name="rng110keiroDialogForm" property="keiroShareRange" value="1">
      <logic:equal name="rng110keiroDialogForm" property="keiroRootType" value="<%=String.valueOf(RngConst.RNG_RNCTYPE_APPR) %>">
        <!--自己審議の許可 -->
        <div class="row select_user">
          <gsmsg:write key="rng.rng110.18"/>
          <br>
          <div class="content verAlignMid">
            <html:radio name="rng110keiroDialogForm" property="own" styleId="keiro_own_0" value="0" />
            <label for="keiro_own_0">
              <gsmsg:write key="cmn.not.permit"/>
            </label>
            <html:radio name="rng110keiroDialogForm" property="own" styleId="keiro_own_1" styleClass="ml10" value="1" />
            <label for="keiro_own_1">
              <gsmsg:write key="cmn.permit"/>
            </label>
          </div>
        </div>
      </logic:equal>
    </logic:equal>
    <logic:equal name="rng110keiroDialogForm" property="keiroShareRange" value="1">
      <logic:equal name="rng110keiroDialogForm" property="keiroRootType" value="<%=String.valueOf(RngConst.RNG_RNCTYPE_APPR) %>">
        <!-- この経路の審議者が存在しない場合 -->
        <div class="row keiro_nonuser">
          <gsmsg:write key="rng.rng110.13"/>
          <br>
          <div class="content verAlignMid">
            <html:radio name="rng110keiroDialogForm" property="nonuser" styleId="keiro_nonuser_1" value="1" />
            <label for="keiro_nonuser_1">
              <gsmsg:write key="rng.rng110.25"/>
            </label>
            <html:radio name="rng110keiroDialogForm" property="nonuser" styleId="keiro_nonuser_0" styleClass="ml10" value="0" />
            <label for="keiro_nonuser_0">
              <gsmsg:write key="rng.rng110.24"/>
            </label>
          </div>
        </div>
      </logic:equal>
    </logic:equal>
    <logic:equal name="rng110keiroDialogForm" property="keiroShareRange" value="1">
      <logic:equal name="rng110keiroDialogForm" property="keiroRootType" value="<%=String.valueOf(RngConst.RNG_RNCTYPE_APPR) %>">
        <!-- スキップの許可 -->
        <div class="row keiro_kouetu select_free target_user target_pos select_user select_group auto_boss rnctype_appr">
          <gsmsg:write key="rng.rng110.15"/>
          <br>
          <div class="content verAlignMid">
            <html:radio name="rng110keiroDialogForm" property="kouetu" styleId="keiro_kouetu_not" value="1" />
            <label for="keiro_kouetu_not">
              <gsmsg:write key="cmn.not.permit"/>
            </label>
            <html:radio name="rng110keiroDialogForm" property="kouetu" styleId="keiro_kouetu_ok" styleClass="ml10" value="0" />
            <label for="keiro_kouetu_ok">
              <gsmsg:write key="cmn.permit"/>
            </label>
          </div>
        </div>
      </logic:equal>
    </logic:equal>
    <logic:equal name="rng110keiroDialogForm" property="keiroShareRange" value="1">
      <logic:equal name="rng110keiroDialogForm" property="keiroRootType" value="<%=String.valueOf(RngConst.RNG_RNCTYPE_APPR) %>">
        <!--審議者が申請者の場合 -->
        <div class="row target_user target_pos">
          <gsmsg:write key="rng.rng110.16"/>
          <br>
          <div class="content verAlignMid">
            <html:radio name="rng110keiroDialogForm" property="skip" styleId="keiro_skip_not" value="0" />
            <label for="keiro_skip_not">
              <gsmsg:write key="rng.rng110.25"/>
            </label>
            <html:radio name="rng110keiroDialogForm" property="skip" styleId="keiro_skip_ok" styleClass="ml10" value="1" />
            <label for="keiro_skip_ok">
              <gsmsg:write key="rng.rng110.17"/>
            </label>
          </div>
        </div>
      </logic:equal>
    </logic:equal>
    <logic:equal name="rng110keiroDialogForm" property="keiroShareRange" value="1">
      <logic:equal name="rng110keiroDialogForm" property="keiroRootType" value="<%=String.valueOf(RngConst.RNG_RNCTYPE_APPR) %>">
        <!-- 後閲指示の許可 -->
        <div class="row keiro_kouetu target_user target_pos select_user select_group auto_boss rnctype_appr">
          <gsmsg:write key="rng.rng110.40"/>
          <br>
          <div class="content verAlignMid">
            <html:radio name="rng110keiroDialogForm" property="kouetuSiji" styleId="keiro_kouetu_siji_not" value="1" />
            <label for="keiro_kouetu_siji_not">
              <gsmsg:write key="cmn.not.permit"/>
            </label>
            <html:radio name="rng110keiroDialogForm" property="kouetuSiji" styleId="keiro_kouetu_siji_ok" styleClass="ml10" value="0" />
            <label for="keiro_kouetu_siji_ok">
              <gsmsg:write key="cmn.permit"/>
            </label>
          </div>
        </div>
      </logic:equal>
    </logic:equal>
    <logic:equal name="rng110keiroDialogForm" property="keiroShareRange" value="1">
      <logic:equal name="rng110keiroDialogForm" property="keiroRootType" value="<%=String.valueOf(RngConst.RNG_RNCTYPE_APPR) %>">
        <!-- 進行中の経路追加の許可 -->
        <div class="row keiro_tuika select_free ">
          <gsmsg:write key="rng.rng110.14"/>
          <br>
          <div class="content verAlignMid">
            <html:radio name="rng110keiroDialogForm" property="addkeiro" styleId="keiro_add_keiro_0" value="0" />
            <label for="keiro_add_keiro_0">
              <gsmsg:write key="cmn.not.permit"/>
            </label>
            <html:radio name="rng110keiroDialogForm" property="addkeiro" styleId="keiro_add_keiro_1" styleClass="ml10" value="1" />
            <label for="keiro_add_keiro_1">
              <gsmsg:write key="cmn.permit"/>
            </label>
          </div>
        </div>
      </logic:equal>
    </logic:equal>
    <!-- 自動設定上長階層数 -->
    <div class="row keiro_joutyou  auto_boss">
      <gsmsg:write key="rng.rng110.19"/>
      <div class="content">
        <html:text name="rng110keiroDialogForm" property="bossStepCnt"  size="2" maxlength="2" />
      </div>
    </div>
    <!-- 必須上長階層数 -->
    <div class="row keiro_joutyoumin  auto_boss">
      <gsmsg:write key="rng.rng110.30"/>
      <div class="content">
        <html:text name="rng110keiroDialogForm" property="bossStepCntMin"  size="2" maxlength="2" />
      </div>
    </div>
  </div>
</form>