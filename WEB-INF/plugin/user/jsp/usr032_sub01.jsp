<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

    <tr>
    <th class="txt_m txt_l no_w">
      <span><gsmsg:write key="cmn.affiliation.group" /></span><span class="cl_fontWarn">※</span>
    </th>
    <td class="txt_m txt_l">
      <table class="w100" cellpadding="0" cellspacing="0" border="0">
      <tr>
      <td class="txt_r border_none">
        <button type="button" name="all_off" class="baseBtn" onclick="javascript:onAllUnCheck();defaultGroup();" value="<gsmsg:write key="user.31" />">
          <gsmsg:write key="user.31" />
        </button>
      </td>
      </tr>
      </table>
    <div class="hp302 bor1">
      <iframe name="grpFrame" src="../user/usr021.do?parentName=usr032" class="w100 hp300" frameborder="0">
      <gsmsg:write key="user.32" />
      </iframe>
    </div>
    </td>
    </tr>

    <!-- デフォルトグループ -->
    <tr>
    <th class="txt_m txt_l no_w">
      <span><gsmsg:write key="user.35" /></span><span class="cl_fontWarn">※</span>
    </th>
    <td class="txt_m txt_l">
      <logic:empty name="usr032Form" property="groupList">
        <html:select property="usr031defgroup" styleClass="wp250">
          <option value="-1"><gsmsg:write key="cmn.select.plz" /></option>
        </html:select>
      </logic:empty>
      <logic:notEmpty name="usr032Form" property="groupList">
        <html:select property="usr031defgroup" styleClass="wp250">
          <option value="-1"><gsmsg:write key="cmn.select.plz" /></option>
          <html:optionsCollection name="usr032Form" property="groupList" value="groupSid" label="groupName" />
        </html:select>
      </logic:notEmpty>
    </td>
    </tr>
