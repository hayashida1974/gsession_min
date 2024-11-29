<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<table class="w100" cellpadding="0" cellspacing="0">
  <tr class="txt_t">
    <td class="w100 txt_c">
      <table class="table-left">
        <tr>
          <!-- キーワード -->
          <th class="w10 txt_l no_w">
            <gsmsg:write key="cmn.keyword" />
          </th>
          <td class="w40 txt_l word_b-all">
            <html:text property="usr040Keyword" styleClass="wp250" maxlength="60" />
            <br>
            <span class="verAlignMid">
              <html:checkbox name="usr040Form" property="usr040KeyKbnShainno" value="1" styleId="KbnSNo" />
              <label for="KbnSNo" class="mr10 no_w"><gsmsg:write key="cmn.employee.staff.number" /></label>
              <html:checkbox name="usr040Form" property="usr040KeyKbnName" value="1" styleId="KbnName" />
              <label for="KbnName" class="mr10 no_w"><gsmsg:write key="cmn.name" /></label>
              <html:checkbox name="usr040Form" property="usr040KeyKbnNameKn" value="1" styleId="KbnNameKn" />
              <label for="KbnNameKn" class="mr10 no_w"><gsmsg:write key="cmn.name.kana" /></label>
            </span>
            <span class="verAlignMid">
              <html:checkbox name="usr040Form" property="usr040KeyKbnMail" value="1" styleId="KbnMail" />
              <label for="KbnMail" class="mr10 no_w">E-MAIL</label>
              <html:checkbox name="usr040Form" property="usr040KeyKbnTel" value="1" styleId="KbnTel" />
              <label for="KbnTel" class="no_w"><gsmsg:write key="cmn.tel" /></label>
            </span>
          </td>
          <!-- 年齢 -->
          <th class="w10 txt_l no_w">
            <gsmsg:write key="user.3" />
          </th>
          <td class="w40 txt_l">
            <html:text property="usr040agefrom" styleClass="wp50 mr5" maxlength="2" /><gsmsg:write key="user.98" />
            <span class="ml5 mr5">～</span>
            <html:text property="usr040ageto" styleClass="wp50 mr5" maxlength="2" /><gsmsg:write key="user.98" />
          </td>
        </tr>

        <tr>
          <!-- 所属グループ -->
          <th class="txt_l no_w">
            <gsmsg:write key="cmn.affiliation.group" />
          </th>
          <td class="txt_l">
            <html:select property="selectgsid" styleClass="wp220">
              <html:optionsCollection name="usr040Form" property="grpLabelList" value="value" label="label" />
            </html:select>
            <logic:notEqual name="usr040Form" property="usr040webmail" value="1">
              <button type="button" onclick="openGroupWindow(this.form.selectgsid, 'selectgsid', '0', '', 1)" class="iconBtn-border" value="&nbsp;&nbsp;" id="usr040GroupBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
              </button>
            </logic:notEqual>
          </td>
          <!-- 性別 -->
          <th class="txt_l no_w">
            <gsmsg:write key="user.123" />
          </th>
          <td class="txt_l no_w">
            <logic:notEmpty name="usr040Form" property="seibetuLabelList">
              <span class="verAlignMid">
                <bean:define id="seibetuVal" name="usr040Form" property="usr040seibetu" type="java.lang.String" />
                <% boolean seibetsuFlg = false; %>
                <logic:iterate id="seibetuMdl" name="usr040Form" property="seibetuLabelList" indexId="idxSeibetu">
                  <logic:equal name="seibetuMdl" property="value" value="<%= seibetuVal %>">
                    <input type="radio" name="usr040seibetu" id="usr040seibetu_<%= idxSeibetu %>" value="<bean:write name="seibetuMdl" property="value" />" checked <% if (seibetsuFlg) { %> class="ml10"<% } %> />
                    <label for="usr040seibetu_<%= idxSeibetu %>">
                      <bean:write name="seibetuMdl" property="label" />
                    </label>
                  </logic:equal>
                  <logic:notEqual name="seibetuMdl" property="value" value="<%= seibetuVal %>">
                    <input type="radio" name="usr040seibetu" id="usr040seibetu_<%= idxSeibetu %>" value="<bean:write name="seibetuMdl" property="value" />" <% if (seibetsuFlg) { %> class="ml10"<% } %> />
                    <label for="usr040seibetu_<%= idxSeibetu %>">
                      <bean:write name="seibetuMdl" property="label" />
                    </label>
                  </logic:notEqual>
                  <% seibetsuFlg = true; %>
                </logic:iterate>
              </span>
            </logic:notEmpty>
          </td>
        </tr>

        <tr>
          <!-- 役職 -->
          <th class="txt_l no_w">
            <gsmsg:write key="cmn.post" />
          </th>
          <td class="txt_l">
            <html:select property="usr040yakushoku" styleClass="wp220">
              <html:optionsCollection name="usr040Form" property="posLabelList" value="value" label="label" />
            </html:select>
          </td>
          <!-- 都道府県 -->
          <th class="txt_l no_w">
            <gsmsg:write key="cmn.prefectures" />
          </th>
          <td class="txt_l">
            <html:select property="usr040tdfkCd">
              <html:optionsCollection name="usr040Form" property="tdfkLabelList" value="value" label="label" />
            </html:select>
          </td>

        </tr>

        <tr>
          <!-- 入社年月日 -->
          <th class="txt_l no_w">
            <gsmsg:write key="user.156" />
          </th>
          <td colspan="3" class="user_search2_syosai_td_1">


            <span style="line-height: 30px;" class="text_base">From:</span>
            <html:select property="usr040entranceYearFr" styleId="selYearsf">
              <html:optionsCollection name="usr040Form" property="usr040entranceYearFrLabel" value="value" label="label" />
            </html:select>
            <html:select property="usr040entranceMonthFr" styleId="selMonthsf">
              <html:optionsCollection name="usr040Form" property="usr040entranceMonthFrLabel" value="value" label="label" />
            </html:select>
            <html:select property="usr040entranceDayFr" styleId="selDaysf">
              <html:optionsCollection name="usr040Form" property="usr040entranceDayFrLabel" value="value" label="label" />
            </html:select>
               <span class="verAlignMid">
              <button type="button" class="iconBtn-border" value="Cal" onclick="wrtCalendarByBtn(this.form.selDaysf, this.form.selMonthsf, this.form.selYearsf, 'usr040entranceFrCalBtn')" class="iconBtn-border" id="usr040entranceFrCalBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
                <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
              </button>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selYearsf')[0], $('#selMonthsf')[0], $('#selDaysf')[0], 1)">
                <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left"></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="return moveDay($('#selYearsf')[0], $('#selMonthsf')[0], $('#selDaysf')[0], 2)">
                <gsmsg:write key='cmn.today' />
              </button>
              <span>
                <a class="todayBtn original-display" onclick="return moveDay($('#selYearsf')[0], $('#selMonthsf')[0], $('#selDaysf')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </a>
              </span>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selYearsf')[0], $('#selMonthsf')[0], $('#selDaysf')[0], 3)">
                <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right"></i>
              </button>
              <button type="button" value="<gsmsg:write key="cmn.specified.no" />" class="baseBtn" onclick="return clearDate($('#selYearsf'), $('#selMonthsf'), $('#selDaysf'))">
                <gsmsg:write key="cmn.specified.no" />
              </button>
              </span>
              <br>

              <span class="mr20">To</span>:
              <html:select property="usr040entranceYearTo" styleId="selYearst">
                <html:optionsCollection name="usr040Form" property="usr040entranceYearFrLabel" value="value" label="label" />
              </html:select>
              <html:select property="usr040entranceMonthTo" styleId="selMonthst">
                <html:optionsCollection name="usr040Form" property="usr040entranceMonthFrLabel" value="value" label="label" />
              </html:select>
              <html:select property="usr040entranceDayTo" styleId="selDayst">
                <html:optionsCollection name="usr040Form" property="usr040entranceDayFrLabel" value="value" label="label" />
              </html:select>

              <span class="verAlignMid">
                <button type="button" class="iconBtn-border" value="Cal" onclick="wrtCalendarByBtn(this.form.selDayst, this.form.selMonthst, this.form.selYearst, 'usr040entranceToCalBtn')" class="iconBtn-border" id="usr040entranceToCalBtn">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
                </button>
                <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selYearst')[0], $('#selMonthst')[0], $('#selDayst')[0], 1)">
                  <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_l.png">
                  <i class="icon-paging_left"></i>
                </button>
                <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onClick="return moveDay($('#selYearst')[0], $('#selMonthst')[0], $('#selDayst')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </button>
                <a class="todayBtn original-display" onclick="return moveDay($('#selYearst')[0], $('#selMonthst')[0], $('#selDayst')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </a>
                <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selYearst')[0], $('#selMonthst')[0], $('#selDayst')[0], 3)">
                  <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_r.png">
                  <i class="icon-paging_right"></i>
                </button>
                <button type="button" value="<gsmsg:write key="cmn.specified.no" />" class="baseBtn" onclick="return clearDate($('#selYearst'), $('#selMonthst'), $('#selDayst'))">
                  <gsmsg:write key="cmn.specified.no" />
                </button>
              </span>
          </td>
        </tr>
      </table>
    </td>
    <td class="w20 txt_c"></td>
  </tr>
</table>

<table class="table-left" cellpadding="0" cellspacing="0">
  <tr>
    <th class="w10 no_w">
      <gsmsg:write key="cmn.sort.order" />
    </th>
    <td>
      <span>
        <gsmsg:write key="cmn.first.key" />
      </span>
      <span class="verAlignMid">
        <logic:notEqual name="usr040Form" property="sortKeyLabelList" value="">
          <html:select property="usr040sortKey" onchange="changeSortCombo();">
            <html:optionsCollection name="usr040Form" property="sortKeyLabelList" value="value" label="label" />
          </html:select>
        </logic:notEqual>
        <span class="verAlignMid">
          <html:radio name="usr040Form" property="usr040orderKey" styleClass="ml5" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>" styleId="sort1_up" />
          <label for="sort1_up">
            <gsmsg:write key="cmn.order.asc" />
          </label>
          <html:radio name="usr040Form" property="usr040orderKey" styleClass="ml10" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>" styleId="sort1_dw" />
          <label for="sort1_dw">
            <gsmsg:write key="cmn.order.desc" />
          </label>
        </span>
      </span>
      <span>
        <gsmsg:write key="cmn.second.key" />
      </span>
      <span class="verAlignMid">
        <logic:notEqual name="usr040Form" property="sortKeyLabelList" value="">
          <html:select property="usr040sortKey2" onchange="changeSortCombo();">
            <html:optionsCollection name="usr040Form" property="sortKeyLabelList" value="value" label="label" />
          </html:select>
        </logic:notEqual>
        <span class="verAlignMid">
          <html:radio name="usr040Form" property="usr040orderKey2" styleClass="ml5" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC)%>" styleId="sort2_up" />
          <label for="sort2_up">
            <gsmsg:write key="cmn.order.asc" />
          </label>
          <html:radio name="usr040Form" property="usr040orderKey2" styleClass="ml10" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC)%>" styleId="sort2_dw" />
          <label for="sort2_dw">
            <gsmsg:write key="cmn.order.desc" />
          </label>
        </span>
      </span>
    </td>
  </tr>
</table>