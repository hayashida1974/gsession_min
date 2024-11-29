<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<table class="w100 bgC_lightGray borC_light border_top_none">
  <tr>
    <td class="w100 txt_t p5">
      <table class="table-left m0 table-fixed">
        <tr>
          <th class="txt_l w15">
            <gsmsg:write key="cmn.user.id" />
          </th>
          <td class="txt_l w40 no_w">
          <div>
            <html:text property="usr030userId" styleClass="wp200" maxlength="20" />
            </div>
            <div class="verAlignMid">
              <gsmsg:write key="cmn.status" />:
              <html:radio property="usr030usrUkoFlg" styleId="usr030usrUkoFlg_0" value="-1" />
              <label for="usr030usrUkoFlg_0">
                <gsmsg:write key="ntp.166" />
              </label>
              <html:radio property="usr030usrUkoFlg" styleClass="ml10" styleId="usr030usrUkoFlg_1" value="0" />
              <label for="usr030usrUkoFlg_1">
                <gsmsg:write key="user.usr030.4" />
              </label>
               <html:radio property="usr030usrUkoFlg" styleClass="ml10" styleId="usr030usrUkoFlg_2" value="1" />
              <label for="usr030usrUkoFlg_2">
                <gsmsg:write key="user.usr030.5" />
              </label>
              </div>
          </td>
          <th class="txt_l w15">
            <gsmsg:write key="cmn.affiliation.group" />
          </th>
          <td class="txt_l w30">
            <html:select property="selectgsid" styleClass="wp200">
              <html:optionsCollection name="usr030Form" property="grpLabelList" value="value" label="label" />
            </html:select>
            <button type="button" onclick="openGroupWindow(this.form.selectgsid, 'selectgsid', '0', '', 1)" class="iconBtn-border" value="&nbsp;&nbsp;" id="usr030GroupBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
            </button>
          </td>
        </tr>
        <tr>
          <th class="txt_l w30">
            <gsmsg:write key="cmn.employee.staff.number" />
          </th>
          <td class="txt_l w30">
            <html:text property="usr030shainno" styleClass="wp200" maxlength="20" />
          </td>

          <th class="txt_l no_w">
            <gsmsg:write key="cmn.post" />
          </th>
          <td class="txt_l">
            <html:select property="usr030yakushoku" styleClass="wp200">
              <html:optionsCollection name="usr030Form" property="posLabelList" value="value" label="label" />
            </html:select>
          </td>

        </tr>

        <tr>
          <th class="txt_l">
            <gsmsg:write key="cmn.name" />
          </th>

          <td>
            <div class="verAlignMid">
            <div class="wp35">
              <gsmsg:write key="cmn.lastname" />
            </div>
            <html:text property="usr030sei" styleClass="wp100" maxlength="<%= String.valueOf(GSConstUser.MAX_LENGTH_USER_NAME_SEI) %>" />
            <div class="wp35 ml5">
              <gsmsg:write key="cmn.name3" />
            </div>
            <html:text property="usr030mei" styleClass="wp100" maxlength="<%= String.valueOf(GSConstUser.MAX_LENGTH_USER_NAME_MEI) %>" />
            </div>
          </td>

          <th class="txt_l">
            <gsmsg:write key="user.123" />
          </th>
          <td class="txt_l">
            <logic:notEmpty name="usr030Form" property="seibetuLabelList">
              <span class="verAlignMid">
                <bean:define id="seibetuVal" name="usr030Form" property="usr030seibetu" type="java.lang.String" />
                <logic:iterate id="seibetuMdl" name="usr030Form" property="seibetuLabelList" indexId="idxSeibetu">
                  <logic:equal name="seibetuMdl" property="value" value="<%= seibetuVal %>">
                    <input type="radio" name="usr030seibetu" id="usr030seibetu_<%= idxSeibetu %>" value="<bean:write name="seibetuMdl" property="value" />" checked />
                    <label class="mr10" for="usr030seibetu_<%= idxSeibetu %>">
                      <bean:write name="seibetuMdl" property="label" />
                    </label>
                  </logic:equal>
                  <logic:notEqual name="seibetuMdl" property="value" value="<%= seibetuVal %>">
                    <input type="radio" name="usr030seibetu" id="usr030seibetu_<%= idxSeibetu %>" value="<bean:write name="seibetuMdl" property="value" />" />
                    <label class="mr10" for="usr030seibetu_<%= idxSeibetu %>">
                      <bean:write name="seibetuMdl" property="label" />
                    </label>
                  </logic:notEqual>
                </logic:iterate>
              </span>
            </logic:notEmpty>
          </td>

        </tr>

        <tr>
          <th class="txt_l">
            <gsmsg:write key="cmn.name.kana" />
          </th>
          <td>
          <div class="verAlignMid">
          <div class="wp35">
            <gsmsg:write key="user.149" />
          </div>
            <html:text property="usr030seikn" styleClass="wp100" maxlength="<%= String.valueOf(GSConstUser.MAX_LENGTH_USER_NAME_SEI_KN) %>" />
          <div class="wp35 ml5">
            <gsmsg:write key="user.150" />
          </div>
            <html:text property="usr030meikn" styleClass="wp100" maxlength="<%= String.valueOf(GSConstUser.MAX_LENGTH_USER_NAME_MEI_KN) %>" />
          </div>
          </td>

          <th class="txt_l">E-MAIL</th>
          <td class="txt_l">
            <html:text property="usr030mail" styleClass="w100" maxlength="<%= String.valueOf(GSConstUser.MAX_LENGTH_MAIL) %>" />
          </td>

        </tr>

        <tr>
          <th class="txt_l">
            <gsmsg:write key="user.3" />
          </th>
          <td class="txt_l">
            <html:text property="usr030agefrom" styleClass="wp50" maxlength="2" />
            &nbsp;
            <gsmsg:write key="user.98" />
            &nbsp;～&nbsp;
            <html:text property="usr030ageto" styleClass="wp50" maxlength="2" />
            &nbsp;
            <gsmsg:write key="user.98" />
          </td>

          <th class="txt_l">
            <gsmsg:write key="cmn.prefectures" />
          </th>
          <td class="txt_l">
            <html:select property="usr030tdfkCd">
              <html:optionsCollection name="usr030Form" property="tdfkLabelList" value="value" label="label" />
            </html:select>
          </td>

        </tr>
        <tr>
          <th class="txt_l">
            <gsmsg:write key="user.122" />
          </th>
          <td colspan="3">
            <div class="wp40 display_inline-block">From:</div>
            <html:select property="usr030entranceYearFr" styleId="selYearsf">
              <html:optionsCollection name="usr030Form" property="usr030entranceYearFrLabel" value="value" label="label" />
            </html:select>
            <html:select property="usr030entranceMonthFr" styleId="selMonthsf">
              <html:optionsCollection name="usr030Form" property="usr030entranceMonthFrLabel" value="value" label="label" />
            </html:select>
            <html:select property="usr030entranceDayFr" styleId="selDaysf">
              <html:optionsCollection name="usr030Form" property="usr030entranceDayFrLabel" value="value" label="label" />
            </html:select>
            <span class="verAlignMid">
              <button type="button" class="iconBtn-border" value="Cal" onclick="wrtCalendarByBtn(this.form.selDaysf, this.form.selMonthsf, this.form.selYearsf, 'usr030entranceFrCalBtn')" class="iconBtn-border" id="usr030entranceToCalBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_cal.png" alt="Cal">
                <img class="btn_originalImg-display" src="../common/images/original/icon_calendar.png" alt="Cal">
              </button>
              <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveDay($('#selYearsf')[0], $('#selMonthsf')[0], $('#selDaysf')[0], 1)">
                <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left"></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onclick="return moveDay($('#selYearsf')[0], $('#selMonthsf')[0], $('#selDaysf')[0], 2)">
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
            <div class="wp40 display_inline-block">To:</div>
            <html:select property="usr030entranceYearTo" styleId="selYearst">
              <html:optionsCollection name="usr030Form" property="usr030entranceYearFrLabel" value="value" label="label" />
            </html:select>
            <html:select property="usr030entranceMonthTo" styleId="selMonthst">
              <html:optionsCollection name="usr030Form" property="usr030entranceMonthFrLabel" value="value" label="label" />
            </html:select>
            <html:select property="usr030entranceDayTo" styleId="selDayst">
              <html:optionsCollection name="usr030Form" property="usr030entranceDayFrLabel" value="value" label="label" />
            </html:select>

            <span class="verAlignMid">
              <button type="button" class="iconBtn-border" value="Cal" onclick="wrtCalendarByBtn(this.form.selDayst, this.form.selMonthst, this.form.selYearst, 'usr030entranceToCalBtn')" class="iconBtn-border" id="usr030entranceToCalBtn">
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
              <span>
                <a class="todayBtn original-display" onclick="return moveDay($('#selYearst')[0], $('#selMonthst')[0], $('#selDayst')[0], 2)">
                  <gsmsg:write key='cmn.today' />
                </a>
              </span>
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
  </tr>
  <tr>
    <td class="txt_c pb5">
      <button type="button" name="btn_search" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('searchSyosai');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <gsmsg:write key="cmn.search" />
      </button>
    </td>
  </tr>
</table>
