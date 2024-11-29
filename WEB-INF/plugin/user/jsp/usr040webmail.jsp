<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

    <div class="scrDataTable" class="border_none">
    <table id="demoTable" class="table-top demoTable mt0" cellpadding="0" cellspacing="0">
    <tr>
      <th class="w3 txt_c no_w js_tableTopCheck js_tableTopCheck-header js_mailFlg cursor_p">
        <input type="checkbox" name="usr040webAllCheck" onclick="chgCheckAll('usr040webAllCheck', 'usr040selectUser');">
      </th>
      <th class="txt_c w22 no_w">
        <a href="#" class="cl_fontOutlineLink" onClick="clickSortTitle(<%= jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO %>);"><span class="cel_user_data">社員・職員番号</span></a>
        <span class="cel_user_data">/</span>
        <a href="#" class="cl_fontOutlineLink" onClick="clickSortTitle(<%= jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME %>);">
        <span class="cel_user_data">
        <gsmsg:write key="cmn.name" /></span></a>
        <span class="cel_user_data">/</span>
        <a href="#" class="cl_fontOutlineLink" onClick="clickSortTitle(<%= jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK %>);">
        <span class="cel_user_data">
        <gsmsg:write key="cmn.post" /></span></a>
    </th>
    <th class="w20 txt_c no_w">
      <span class="cel_user_data"><gsmsg:write key="cmn.affiliation.group" /></span>
    </th>
    <th class="w20 txt_c no_w">
      <span class="cel_user_data"><gsmsg:write key="user.8" /></span>
    </th>
    <th class="w20 txt_c no_w">
      <span class="cel_user_data"><gsmsg:write key="cmn.mailaddress" /></span>
    </th>
    <th class="w15 txt_c no_w">
      <span class="cel_user_data">ラベル</span>
    </th>
    </tr>


    <logic:notEmpty name="usr040Form" property="usr040users">
    <logic:iterate id="user" name="usr040Form" property="usr040users" scope="request" indexId="idx">
    <bean:define id="mukoclass" value="" />
    <logic:equal name="user" property="usrUkoFlg" value="1">
       <bean:define id="mukoclass" value="mukoUser" />
    </logic:equal>

    <% boolean usrMailFlg = false; %>
    <logic:notEmpty name="user" property="usiMail1"><% usrMailFlg = true; %></logic:notEmpty>
    <logic:notEmpty name="user" property="usiMail2"><% usrMailFlg = true; %></logic:notEmpty>
    <logic:notEmpty name="user" property="usiMail3"><% usrMailFlg = true; %></logic:notEmpty>

    <tr>
    <!-- チェックボックス -->
    <td class="txt_c js_tableTopCheck cursor_p"><% if (usrMailFlg) { %><html:multibox name="usr040Form" property="usr040selectUser"><bean:write name="user"  property="usrSid" /></html:multibox><% } %></td>
    <!-- 写真 -->
    <td>
      <table>
      <tr>
      <td class="border_none" rowspan="3">
      <div class="pl5 pr10">
        <logic:equal name="user" property="binSid" value="0">
          <img class="classic-display wp50" src="../common/images/classic/icon_photo.gif" name="userImage" alt="<gsmsg:write key="cmn.photo" />"/>
          <img class="original-display wp50" src="../common/images/classic/icon_photo.gif" name="userImage" alt="<gsmsg:write key="cmn.photo" />"/>
        </logic:equal>

        <logic:notEqual name="user" property="binSid" value="0">
        <logic:equal name="user" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
          <div class="hikokai_photo-m txt_c"><span class="hikokai_text"><gsmsg:write key="cmn.private" /></span></div>
        </logic:equal>
        <logic:notEqual name="user" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
          <img class="comment_Img wp50" src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="user" property="binSid" />" alt="<gsmsg:write key="cmn.photo" />"/>
        </logic:notEqual>
        </logic:notEqual>
      </div>
      </td>
      <!-- 社員/職員番号 -->
      <td class="no_w p0 border_none">
      <div>
       <span class="<%=mukoclass %> fs_12">
       <bean:write name="user" property="usiSyainNo" />
       </span>
       </div>
      </td>
      </tr>
    <!-- 名前 -->
    <tr>
      <td class="p0 border_none">
      <% if (usrMailFlg) { %>
        <a href="#!" onClick="addAddressMail('<bean:write name="user" property="usrSid" />', 'addUsrAtesaki', 0); return false;">
          <span class="<%=mukoclass %>"><bean:write name="user" property="usiSei" /><span class="pl5"><bean:write name="user" property="usiMei" /></span></span>
        </a>
       <% } else { %>
        <span class="<%=mukoclass %>"><bean:write name="user" property="usiSei" /><span class="pl5"><bean:write name="user" property="usiMei" /></span></span>
       <% } %>
      </td>
    </tr>
    <!-- 役職 -->
    <tr>
      <td class="no_w p0 border_none">
       <span class="cel_user_data fs_12 <%=mukoclass%>">
       <bean:write name="user" property="usiYakusyoku" />
       </span>
      </td>
      </tr>
      </table>
    </td>

    <!-- 所属グループ -->
    <td>
        <logic:notEmpty name="user" property="belongGrpList">
          <logic:iterate id="grp" name="user" property="belongGrpList">
            <div class="cel_user_data fs_12 <%=mukoclass %>"><bean:write name="grp" property="groupName"/></div>
          </logic:iterate>
        </logic:notEmpty>
    </td>
    <!-- 電話・内線 -->
    <%
      String strUsiTel1 = "";
      String strUsiTelNai1 = "";
      String strUsiTel2 = "";
      String strUsiTelNai2 = "";
      String strUsiTel3 = "";
      String strUsiTelNai3 = "";
    %>
    <logic:notEmpty name="user" property="usiTel1">
      <bean:define id="u_usiTel1" name="user" property="usiTel1" type="java.lang.String" />
      <% strUsiTel1 = jp.co.sjts.util.NullDefault.getString(((String)pageContext.getAttribute("u_usiTel1",PageContext.PAGE_SCOPE)), ""); %>
    </logic:notEmpty>
    <logic:notEmpty name="user" property="usiTelNai1">
      <bean:define id="u_usiTelNai1" name="user" property="usiTelNai1" type="java.lang.String" />
      <% strUsiTelNai1 = jp.co.sjts.util.NullDefault.getString(((String)pageContext.getAttribute("u_usiTelNai1",PageContext.PAGE_SCOPE)), ""); %>
    </logic:notEmpty>
    <logic:notEmpty name="user" property="usiTel2">
      <bean:define id="u_usiTel2" name="user" property="usiTel2" type="java.lang.String" />
      <% strUsiTel2 = jp.co.sjts.util.NullDefault.getString(((String)pageContext.getAttribute("u_usiTel2",PageContext.PAGE_SCOPE)), ""); %>
    </logic:notEmpty>
    <logic:notEmpty name="user" property="usiTelNai2">
      <bean:define id="u_usiTelNai2" name="user" property="usiTelNai2" type="java.lang.String" />
      <% strUsiTelNai2 = jp.co.sjts.util.NullDefault.getString(((String)pageContext.getAttribute("u_usiTelNai2",PageContext.PAGE_SCOPE)), ""); %>
    </logic:notEmpty>
    <logic:notEmpty name="user" property="usiTel3">
      <bean:define id="u_usiTel3" name="user" property="usiTel3" type="java.lang.String" />
      <% strUsiTel3 = jp.co.sjts.util.NullDefault.getString(((String)pageContext.getAttribute("u_usiTel3",PageContext.PAGE_SCOPE)), ""); %>
    </logic:notEmpty>
    <logic:notEmpty name="user" property="usiTelNai3">
      <bean:define id="u_usiTelNai3" name="user" property="usiTelNai3" type="java.lang.String" />
      <% strUsiTelNai3 = jp.co.sjts.util.NullDefault.getString(((String)pageContext.getAttribute("u_usiTelNai3",PageContext.PAGE_SCOPE)), ""); %>
    </logic:notEmpty>

    <td>
      <% if (strUsiTel1.length() > 0 || strUsiTelNai1.length() > 0) { %>
      <span class="cel_user_data no_w fs_12 <%=mukoclass %>">
      <logic:notEmpty name="user" property="usiTel1"><bean:write name="user" property="usiTel1" /></logic:notEmpty>
      <logic:notEmpty name="user" property="usiTelNai1"><wbr><bean:write name="user" property="usiTelNai1" /></logic:notEmpty>
      <logic:notEmpty name="user" property="usiTelCmt1">&nbsp;<span>(<bean:write name="user" property="usiTelCmt1" />)</span></logic:notEmpty>
      </span><br>
      <% } %>
      <% if (strUsiTel2.length() > 0 || strUsiTelNai2.length() > 0) { %>
      <span class="cel_user_data no_w fs_12 <%=mukoclass %>">
      <logic:notEmpty name="user" property="usiTel2"><bean:write name="user" property="usiTel2" /></logic:notEmpty>
      <logic:notEmpty name="user" property="usiTelNai2"><wbr><bean:write name="user" property="usiTelNai2" /></logic:notEmpty>
      <logic:notEmpty name="user" property="usiTelCmt2">&nbsp;<span>(<bean:write name="user" property="usiTelCmt2" />)</span></logic:notEmpty>
      </span><br>
      <% } %>
      <% if (strUsiTel3.length() > 0 || strUsiTelNai3.length() > 0) { %>
      <span class="cel_user_data no_w fs_12 <%=mukoclass %>">
      <logic:notEmpty name="user" property="usiTel3"><bean:write name="user" property="usiTel3" /></logic:notEmpty>
      <logic:notEmpty name="user" property="usiTelNai3"><wbr><bean:write name="user" property="usiTelNai3" /></logic:notEmpty>
      <logic:notEmpty name="user" property="usiTelCmt3">&nbsp;<span>(<bean:write name="user" property="usiTelCmt3" />)</span></logic:notEmpty>
      </span>
      <% } %>
    </td>

    <!-- E-MAIL -->
    <td style="border-right:0px">
      <logic:notEmpty name="user" property="usiMail1">
      <span class="cel_user_data fs_12 <%=mukoclass %>">
      <a href="#!" onClick="addAddressMail('<bean:write name="user" property="usrSid" />', 'addUsrAtesaki', 1); return false;"><bean:write name="user" property="mailAddress1" filter="false" /></a>
      <logic:notEmpty name="user" property="usiMailCmt1">&nbsp;<span>(<bean:write name="user" property="usiMailCmt1" />)</span></logic:notEmpty>
      </span>
      <br>
      </logic:notEmpty>
      <logic:notEmpty name="user" property="usiMail2">
      <span class="cel_user_data fs_12 <%=mukoclass %>">
      <a href="#!" onClick="addAddressMail('<bean:write name="user" property="usrSid" />', 'addUsrAtesaki', 2); return false;"><bean:write name="user" property="mailAddress2" filter="false" /></a>
      <logic:notEmpty name="user" property="usiMailCmt2">&nbsp;<span>(<bean:write name="user" property="usiMailCmt2" />)</span></logic:notEmpty>
      </span>
      <br>
      </logic:notEmpty>
      <logic:notEmpty name="user" property="usiMail3">
      <span class="cel_user_data fs_12 <%=mukoclass %>">
      <a href="#!" onClick="addAddressMail('<bean:write name="user" property="usrSid" />', 'addUsrAtesaki', 3); return false;"><bean:write name="user" property="mailAddress3" filter="false" /></a>
      <logic:notEmpty name="user" property="usiMailCmt3">&nbsp;<span>(<bean:write name="user" property="usiMailCmt3" />)</span></logic:notEmpty>
      </span>
      </logic:notEmpty>
    </td>

    <!-- ラベル -->
    <td>
      <logic:notEmpty name="user" property="viewLabelName">
      <span class="baseLabel"><bean:write name="user" property="viewLabelName"/></span>
      </logic:notEmpty>
    </td>
    </tr>

    </logic:iterate>
    </logic:notEmpty>

    </table>
    </div>