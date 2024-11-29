<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>

<logic:equal name="man050Form" property="man050adminFlg" value="true">
  <table class="bor_b1 bor_l1 bor_r1 w100 bgC_lightGray mt0 borC_light">
    <tr>
      <td class="p5 w100">
        <table class="table-left w100">
          <tr>
            <th class="w25">
              <gsmsg:write key="cmn.affiliation.group" />
            </th>
            <td class="w75">
              <html:select name="man050Form" property="man050grpSid" styleClass="wp300" onchange="changeGrp();">
                <logic:notEmpty name="man050Form" property="man050GroupList">
                  <html:optionsCollection name="man050Form" property="man050GroupList" value="value" label="label" />
                </logic:notEmpty>
              </html:select>
              <button type="button" class="iconBtn-border ml5" value="" onclick="openGroupWindow(this.form.man050grpSid, 'man050grpSid', '0')" id="man050GroupBtn">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
              </button>
            </td>
          </tr>
        </table>
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
</logic:equal>
<logic:notEqual name="man050Form" property="man050adminFlg" value="true">
  <div class="txt_l">
    <span class="fw_b"><gsmsg:write key="cmn.affiliation.group" /></span>
    <html:select name="man050Form" property="man050grpSid" styleClass="wp300" onchange="changeGrp();">
      <logic:notEmpty name="man050Form" property="man050GroupList">
        <html:optionsCollection name="man050Form" property="man050GroupList" value="value" label="label" />
      </logic:notEmpty>
    </html:select>
  </div>
</logic:notEqual>
<table class="table-left table-fixed w100 fw_b">
  <tr>
    <td class="bgC_lastLoginToday txt_c" nowrap><gsmsg:write key="cmn.today" /></td>
    <td class="bgC_lastLoginYesterday txt_c" nowrap><gsmsg:write key="man.yesterday" /></td>
    <td class="bgC_lastLogin2 txt_c" nowrap><gsmsg:write key="man.days.ago" arg0="2" /></td>
    <td class="bgC_lastLogin3 txt_c" nowrap><gsmsg:write key="man.days.ago" arg0="3" /></td>
    <td class="bgC_lastLogin4 txt_c" nowrap><gsmsg:write key="man.days.ago" arg0="4" /></td>
    <td class="bgC_lastLogin5 txt_c" nowrap><gsmsg:write key="man.days.ago" arg0="5" /></td>
    <td class="bgC_lastLogin10 txt_c" nowrap><gsmsg:write key="man.days.ago" arg0="10" /></td>
    <td class="bgC_lastLogin20 txt_c" nowrap><gsmsg:write key="main.man050.1" /></td>
  </tr>
</table>
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
        <th class="w30 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
            <gsmsg:write key="cmn.name" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
          </a>
        </th>
      </logic:equal>
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w30 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="cmn.name" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
          </a>
        </th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>">
      <th class="w30 cursor_p no_w table_header-evt js_table_header-evt">
        <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
          <gsmsg:write key="cmn.name" />
        </a>
      </th>
    </logic:notEqual>
    <logic:equal name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>">
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
            <gsmsg:write key="cmn.post" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
          </a>
        </th>
      </logic:equal>
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="cmn.post" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
          </a>
        </th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>">
      <th class="w15 cursor_p no_w table_header-evt js_table_header-evt">
        <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
          <gsmsg:write key="cmn.post" />
        </a>
      </th>
    </logic:notEqual>
    <logic:equal name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_LALG) %>">
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
        <th class="w40 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_LALG) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>')">
            <gsmsg:write key="user.usr090.2" /><span class="classic-display">▲</span><span class="original-display txt_m"><i class="icon-sort_up"></i></span>
          </a>
        </th>
      </logic:equal>
      <logic:equal name="man050Form" property="man050OrderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC) %>">
        <th class="w40 cursor_p no_w table_header-evt js_table_header-evt">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_LALG) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
            <gsmsg:write key="user.usr090.2" /><span class="classic-display">▼</span><span class="original-display txt_m"><i class="icon-sort_down"></i></span>
          </a>
        </th>
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="man050Form" property="man050SortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_LALG) %>">
      <th class="w40 cursor_p no_w table_header-evt js_table_header-evt">
        <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_LALG) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>')">
          <gsmsg:write key="user.usr090.2" />
        </a>
      </th>
    </logic:notEqual>
    <logic:equal name="man050Form" property="man050adminFlg" value="true">
      <th class="no_w">&nbsp;</th>
    </logic:equal>
  </tr>
  <logic:iterate id="group" name="man050Form" property="man050UserList" scope="request" indexId="idx">
    <bean:define id="tblColor" value="bgC_lastLoginToday" />
    <bean:define id="mod" name="group" property="lgintimeFg" />
    <logic:equal name="mod" value="1">
      <% tblColor = "bgC_lastLoginToday"; %>
    </logic:equal>
    <logic:equal name="mod" value="2">
      <% tblColor = "bgC_lastLoginYesterday"; %>
    </logic:equal>
    <logic:equal name="mod" value="3">
      <% tblColor = "bgC_lastLogin2"; %>
    </logic:equal>
    <logic:equal name="mod" value="4">
      <% tblColor = "bgC_lastLogin3"; %>
    </logic:equal>
    <logic:equal name="mod" value="5">
      <% tblColor = "bgC_lastLogin4"; %>
    </logic:equal>
    <logic:equal name="mod" value="6">
      <% tblColor = "bgC_lastLogin5"; %>
    </logic:equal>
    <logic:equal name="mod" value="7">
      <% tblColor = "bgC_lastLogin10"; %>
    </logic:equal>
    <logic:equal name="mod" value="8">
      <% tblColor = "bgC_lastLogin20"; %>
    </logic:equal>
    <tr class="<%= tblColor %>">
      <td class="txt_l no_w">
        <bean:write name="group" property="syainno" />
      </td>
      <td class="txt_l no_w">
        <a href="#!" onClick="openUserInfoWindow(<bean:write name="group" property="usrsid" />);"><span class="normal_link"><bean:write name="group" property="fullName" /></span></a>
      </td>
      <td class="txt_l no_w">
        <bean:write name="group" property="yakusyoku" />
      </td>
      <td class="txt_c no_w">
        <bean:write name="group" property="strLgintime" />
      </td>
      <logic:equal name="man050Form" property="man050adminFlg" value="true">
        <td class="txt_c no_w">
          <button type="button" class="baseBtn" value="<gsmsg:write key="user.usr031.18" />" onclick="return moveDetail(<bean:write name="group" property="usrsid" />);">
            <gsmsg:write key="user.usr031.18" />
          </button>
        </td>
      </logic:equal>
    </tr>
  </logic:iterate>
</table>
