<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>

<table class="bor_b1 bor_l1 bor_r1 w100 bgC_lightGray mt0 borC_light">
  <tr>
    <td class="p5 w100">
      <table class="table-left w100">
        <tr>
          <th class="w15 no_w" colspan="2">
            <gsmsg:write key="cmn.affiliation.group" />
          </th>
          <td class="w40">
            <html:select name="man050Form" property="man050grpSid" styleClass="wp300" onchange="changeGrp2()">
              <logic:notEmpty name="man050Form" property="man050GroupList">
                <html:optionsCollection name="man050Form" property="man050GroupList" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
            <button type="button" class="iconBtn-border ml5" value="" onclick="openGroupWindow(this.form.man050grpSid, 'man050grpSid', '0')" id="man050GroupBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
            </button>
          </td>
          <th class="w15 no_w">
            <gsmsg:write key="user.75" />
          </th>
          <td class="w30">
            <html:select name="man050Form" property="man050usrSid" styleClass="wp300" onchange="buttonPush('search')">
              <logic:notEmpty name="man050Form" property="man050BelongUserList">
                <html:optionsCollection name="man050Form" property="man050BelongUserList" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
          </td>
        </tr>
        <tr>
          <th class="w10 no_w" rowspan="2">
            <gsmsg:write key="main.man050.2" />
          </th>
          <th class="w5 no_w">
            <gsmsg:write key="cmn.start" />
          </th>
          <td class="w40">
            <span class="verAlignMid">
              <html:text name="man050Form" property="man050FrDate" maxlength="10" styleId="frDate" styleClass="txt_c wp95 datepicker js_frDatePicker" onchange="onSelectFromEvent();" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <button type="button" class="webIconBtn ml5" onClick="moveDay($('#frDate')[0], 1);">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left "></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="moveDay($('#frDate')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </button>
              <span>
                <a class="fw_b todayBtn original-display" onClick="moveDay($('#frDate')[0], 2);">
                  <gsmsg:write key="cmn.today" />
                </a>
              </span>
              <button type="button" class="webIconBtn" onClick="moveDay($('#frDate')[0], 3);">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right "></i>
              </button>
            </span>
          </td>
          <th class="w15 no_w">
            <gsmsg:write key="main.man050.3" />
          </th>
          <td class="w30">
            <span class="verAlignMid">
              <html:radio property="man050Terminal" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.TERMINAL_KBN_ALL) %>" styleId="terminal0" onclick="return tarminalChange(0)" /><label for="terminal0"><gsmsg:write key="cmn.all" /></label>
              <html:radio styleClass="ml10" property="man050Terminal" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.TERMINAL_KBN_PC) %>" styleId="terminal1" onclick="return tarminalChange(1)" /><label for="terminal1"><%= jp.groupsession.v2.cmn.GSConstCommon.TERMINAL_KBN_PC_TEXT %></label>
              <html:radio styleClass="ml10" property="man050Terminal" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.TERMINAL_KBN_MOBILE) %>" styleId="terminal2" onclick="return tarminalChange(2)" /><label for="terminal2"><gsmsg:write key="main.man120.4" /></label>
            </span>
          </td>
        </tr>
        <tr>
          <th class="w5 no_w">
            <gsmsg:write key="cmn.end" />
          </th>
          <td class="w40">
            <span class="verAlignMid">
              <html:text name="man050Form" property="man050ToDate" maxlength="10" styleId="toDate" styleClass="txt_c wp95 datepicker js_toDatePicker" onchange="onSelectToEvent();" />
              <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
              <button type="button" class="webIconBtn ml5" onClick="moveDay($('#toDate')[0], 1);">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
                <i class="icon-paging_left "></i>
              </button>
              <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="moveDay($('#toDate')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </button>
              <span>
                <a class="fw_b todayBtn original-display" onClick="moveDay($('#toDate')[0], 2);">
                  <gsmsg:write key="cmn.today" />
                </a>
              </span>
              <button type="button" class="webIconBtn" onClick="moveDay($('#toDate')[0], 3);">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
                <i class="icon-paging_right "></i>
              </button>
            </span>
          </td>
          <th class="w15 no_w">
            <gsmsg:write key="main.man050.4" />
          </th>
          <td class="w30">
            <span class="verAlignMid">
              <html:radio property="man050Car" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_PC_ALL) %>" styleId="car0"  onclick="return tarminalChange(3)" /><label for="car0"><gsmsg:write key="cmn.all" /></label>
              <html:radio styleClass="ml10" property="man050Car" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_DOCOMO) %>" styleId="car2"  onclick="return tarminalChange(3)" /><label for="car2"><%= jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_DOCOMO_TEXT %></label>
              <html:radio styleClass="ml10" property="man050Car" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_KDDI) %>" styleId="car3"  onclick="return tarminalChange(3)" /><label for="car3"><%= jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_KDDI_TEXT %></label>
              <html:radio styleClass="ml10" property="man050Car" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_SOFTBANK) %>" styleId="car4"  onclick="return tarminalChange(3)" /><label for="car4"><%= jp.groupsession.v2.cmn.GSConstCommon.CAR_KBN_SOFTBANK_TEXT %></label>
            </span>
          </td>
        </tr>
      </table>
      <div class="txt_c">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onclick="buttonPush('search');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="cmn.search" />
        </button>
      </div>
    </td>
  </tr>
</table>
<div class="txt_r pt10">
  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onclick="buttonPush('man050export');">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
    <gsmsg:write key="cmn.export" />
  </button>
</div>

<logic:notEmpty name="man050Form" property="man050PageList">
  <bean:size id="count2" name="man050Form" property="man050PageList" scope="request" />
  <logic:greaterThan name="count2" value="1">
    <div class="paging mt5">
      <button type="button" class="webIconBtn" onClick="buttonPush('pageleft');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select styleClass="paging_combo"  property="man050PageTop" onchange="changePage('0');">
        <html:optionsCollection name="man050Form" property="man050PageList" value="value" label="label" />
      </html:select>
      <button type="button" class="webIconBtn" onClick="buttonPush('pageright');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:greaterThan>
</logic:notEmpty>

<table class="table-top w100">
  <tr>
    <logic:equal name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>">
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
            <gsmsg:write key="cmn.employee.staff.number" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
          </a>
        </th>
      </logic:equal>
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="cmn.employee.staff.number" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
          </a>
        </th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>">
      <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
        <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
          <gsmsg:write key="cmn.employee.staff.number" />
        </a>
      </th>
    </logic:notEqual>

    <logic:equal name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>">
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
            <gsmsg:write key="cmn.name" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
          </a>
        </th>
      </logic:equal>
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="cmn.name" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
          </a>
        </th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>">
      <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
        <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
          <gsmsg:write key="cmn.name" />
        </a>
      </th>
    </logic:notEqual>

    <logic:equal name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>">
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w10 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
            <gsmsg:write key="cmn.post" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
          </a>
        </th>
      </logic:equal>
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w10 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="cmn.post" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
          </a>
        </th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>">
      <th class="w10 cursor_p no_w table_header-evt js_table_header-evt">
        <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
          <gsmsg:write key="cmn.post" />
        </a>
      </th>
    </logic:notEqual>

    <logic:equal name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_LALG) %>">
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_LALG) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
            <gsmsg:write key="main.man050.5" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
          </a>
        </th>
      </logic:equal>
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_LALG) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="main.man050.5" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
          </a>
        </th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_LALG) %>">
      <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
        <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_LALG) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
          <gsmsg:write key="main.man050.5" />
        </a>
      </th>
    </logic:notEqual>

    <logic:equal name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_TERMINAL) %>">
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_TERMINAL) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
            <gsmsg:write key="main.man050.6" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
          </a>
        </th>
      </logic:equal>
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_TERMINAL) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="main.man050.6" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
          </a>
        </th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_TERMINAL) %>">
      <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
        <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_TERMINAL) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
          <gsmsg:write key="main.man050.6" />
        </a>
      </th>
    </logic:notEqual>

    <logic:equal name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_CAR) %>">
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_CAR) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
            <gsmsg:write key="cmn.careers" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
          </a>
        </th>
      </logic:equal>
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_CAR) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="cmn.careers" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
          </a>
        </th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_CAR) %>">
      <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
        <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_CAR) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
          <gsmsg:write key="cmn.careers" />
        </a>
      </th>
    </logic:notEqual>

    <logic:equal name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_UID) %>">
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_UID) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
            <gsmsg:write key="cmn.identification.number" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
          </a>
        </th>
      </logic:equal>
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_UID) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="cmn.identification.number" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
          </a>
        </th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_UID) %>">
      <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
        <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_UID) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
          <gsmsg:write key="cmn.identification.number" />
        </a>
      </th>
    </logic:notEqual>
  </tr>
  <logic:notEmpty name="man050Form" property="man050UserList">
    <logic:iterate id="group" name="man050Form" property="man050UserList" scope="request" indexId="idx">
      <tr class="js_listHover cursor_p" data-sid="<bean:write name="group" property="usrsid" />">
        <td class="txt_l no_w js_listClick">
          <bean:write name="group" property="syainno" />
        </td>
        <td class="txt_l no_w js_listClick">
          <span class="cl_linkDef"><bean:write name="group" property="fullName" /></span>
        </td>
        <td class="txt_l no_w js_listClick">
          <bean:write name="group" property="yakusyoku" />
        </td>
        <td class="txt_c no_w js_listClick">
          <bean:write name="group" property="strLgintime" />
        </td>
        <td class="txt_l no_w js_listClick">
          <bean:write name="group" property="terminalName" />
        </td>
        <td class="txt_l no_w js_listClick">
          <bean:write name="group" property="carName" />
        </td>
        <td class="txt_l no_w js_listClick">
          <bean:write name="group" property="clhUid" />
        </td>
      </tr>
    </logic:iterate>
  </logic:notEmpty>
</table>

<logic:notEmpty name="man050Form" property="man050PageList">
  <bean:size id="count2" name="man050Form" property="man050PageList" scope="request" />
  <logic:greaterThan name="count2" value="1">
    <div class="paging">
      <button type="button" class="webIconBtn" onClick="buttonPush('pageleft');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select styleClass="paging_combo"  property="man050PageBottom" onchange="changePage('1');">
        <html:optionsCollection name="man050Form" property="man050PageList" value="value" label="label" />
      </html:select>
      <button type="button" class="webIconBtn" onClick="buttonPush('pageright');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:greaterThan>
</logic:notEmpty>