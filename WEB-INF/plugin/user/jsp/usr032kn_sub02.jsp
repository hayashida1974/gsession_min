<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

  <!-- 取込みユーザ名 -->
    <tr>
      <th>
        <gsmsg:write key="user.usr032kn.4" />
      </th>
      <td>
        <logic:notEmpty name="usr032knForm" property="usr032knImpList" scope="request">
          <logic:iterate id="uinf" name="usr032knForm" property="usr032knImpList" scope="request">
          <logic:equal name="uinf" property="usiHenkou" value="1">
          <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </logic:equal>
        <logic:equal name="uinf" property="usiHenkou" value="0">
        </logic:equal>
        <bean:write name="uinf" property="usigpSid" />&nbsp;&nbsp;<bean:write name="uinf" property="usigpNm" />&nbsp;/<bean:write name="uinf" property="usiSyainNo" />&nbsp;&nbsp;<bean:write name="uinf" property="usiSei" />&nbsp;&nbsp;<bean:write name="uinf" property="usiMei" /></span><br>
        </logic:iterate>
        <logic:equal name="usr032knForm" property="usr032knGrpflg" value="1">
          <br>
          <span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span><gsmsg:write key="user.usr032kn.5" />
        </logic:equal>
        </logic:notEmpty>
      </td>
    </tr>

  <!-- 役職 -->
    <logic:notEmpty name="usr032knForm" property="posList" scope="request">
    <tr>
      <th>
        <gsmsg:write key="cmn.post" />
      </th>
      <td>
        <span class="cl_fontWarn">
          <gsmsg:write key="user.18" /><br>
          <gsmsg:write key="user.20" />
        </span>
        <br>
        <logic:iterate id="posName" name="usr032knForm" property="posList" scope="request">
          <bean:write name="posName" /><br>
        </logic:iterate>
      </td>
    </tr>
    </logic:notEmpty>

    <tr>
      <td colspan="2" class="bgC_lightGray fw_b">
        <gsmsg:write key="user.86" />
      </td>
    </tr>

    <logic:equal name="usr032knForm" property="changePassword" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.CHANGEPASSWORD_PARMIT) %>">
    <!-- パスワード変更 -->
    <tr>
      <th>
        <gsmsg:write key="cmn.change.password" />
      </th>
      <td>
      <logic:equal name="usr032knForm" property="usr032PswdKbn" value="1">
        <gsmsg:write key="user.2" />
      </logic:equal>
      </td>
    </tr>
    </logic:equal>

    <!-- 上書き -->
    <tr>
      <th>
        <gsmsg:write key="cmn.overwrite" />
      </th>
      <td>
        <logic:equal name="usr032knForm" property="usr032updateFlg" value="1">
          <gsmsg:write key="user.1" />
          <logic:equal name="usr032knForm" property="usr032updatePassFlg" value="1">
          <br><gsmsg:write key="user.no.pass.override" />
          </logic:equal>
        </logic:equal>
      </td>
    </tr>

    <!-- グループ作成 -->
    <tr>
      <th>
        <gsmsg:write key="user.usr032.1" />
      </th>
      <td>
        <logic:equal name="usr032knForm" property="usr032createFlg" value="1">
          <gsmsg:write key="user.usr032.2" />
        </logic:equal>
       </td>
    </tr>