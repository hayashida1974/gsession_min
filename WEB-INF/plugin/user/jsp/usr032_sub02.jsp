<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

    <!--  グループ作成 -->
    <tr>
    <th class="txt_m txt_l no_w">
      <span><gsmsg:write key="user.usr032.1" /></span>
    </th>
    <td class="txt_m txt_l">
    <span class="verAlignMid">
    <html:checkbox name="usr032Form" property="usr032createFlg" value="1" styleId="createFlg" />
    <label for="createFlg"><gsmsg:write key="user.usr032.2" /></label>
    </span>
    </td>
    </tr>