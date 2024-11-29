<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<table class="table-top w100" cellpadding="0" cellspacing="0">
  <tr>
    <th class="js_tableTopCheck no_w w3 txt_c js_tableTopCheck-header cursor_p">
      <input type="checkbox" name="allCheck" onclick="changeChk();">
    </th>

    <!--  社員・職員番号／氏名／役職 -->
    <th class="txt_c w22 no_w">
      <a href="#!" onClick="clickSortTitle(<%= jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO %>);">
        <logic:equal name="usr040Form" property="usr040sortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>">
          <logic:equal name="usr040Form" property="usr040orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
            <gsmsg:write key="cmn.employee.staff.number2" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span>
              </logic:equal>
          <logic:notEqual name="usr040Form" property="usr040orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
            <gsmsg:write key="cmn.employee.staff.number2" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span>
          </logic:notEqual>
        </logic:equal>
        <logic:notEqual name="usr040Form" property="usr040sortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_SNO) %>">
          <gsmsg:write key="cmn.employee.staff.number2" />
        </logic:notEqual>
      </a>
      ／
      <a href="#!" onClick="clickSortTitle(<%= jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME %>);">
        <logic:equal name="usr040Form" property="usr040sortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>">
          <logic:equal name="usr040Form" property="usr040orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
            <gsmsg:write key="cmn.name" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span>
          </logic:equal>
          <logic:notEqual name="usr040Form" property="usr040orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
            <gsmsg:write key="cmn.name" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span>
          </logic:notEqual>
        </logic:equal>
        <logic:notEqual name="usr040Form" property="usr040sortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_NAME) %>">
          <gsmsg:write key="cmn.name" />
        </logic:notEqual>
      </a>
      ／
      <a href="#!" onClick="clickSortTitle(<%= jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK %>);">
        <logic:equal name="usr040Form" property="usr040sortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>">
          <logic:equal name="usr040Form" property="usr040orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
            <gsmsg:write key="cmn.post" /><span class="classic-display">▲</span><span class="original-display"><i class="icon-sort_up"></i></span>
          </logic:equal>
          <logic:notEqual name="usr040Form" property="usr040orderKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC) %>">
            <gsmsg:write key="cmn.post" /><span class="classic-display">▼</span><span class="original-display"><i class="icon-sort_down"></i></span>
          </logic:notEqual>
        </logic:equal>
        <logic:notEqual name="usr040Form" property="usr040sortKey" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.USER_SORT_YKSK) %>">
          <gsmsg:write key="cmn.post" />
        </logic:notEqual>
      </a>
    </th>
    <th class="w20 txt_c no_w">
      <gsmsg:write key="cmn.affiliation.group" />
    </th>
    <th class="w20 txt_c no_w">
      <gsmsg:write key="user.8" />
    </th>
    <th class="w20 txt_c no_w">
      <gsmsg:write key="cmn.mailaddress" />
    </th>
    <th class="w15 txt_c no_w">
      <gsmsg:write key="cmn.label" />
    </th>
  </tr>

  <logic:notEmpty name="usr040Form" property="usr040users">
    <logic:iterate id="user" name="usr040Form" property="usr040users" scope="request" indexId="idx">
      <logic:notEqual name="user" property="usrUkoFlg" value="1">
        <bean:define id="ukoclass" value="" />
      </logic:notEqual>
      <logic:equal name="user" property="usrUkoFlg" value="1">
        <bean:define id="ukoclass" value="mukoUser" />
      </logic:equal>
      <tr>
        <!-- チェックボックス -->
        <td class="txt_c js_tableTopCheck cursor_p">
          <html:multibox name="usr040Form" property="usr040selectUser">
            <bean:write name="user" property="usrSid" />
          </html:multibox>
        </td>
        <!-- 写真 -->
        <td>
          <table class="table-noBorder">
            <tr>
              <td>
                <logic:equal name="user" property="binSid" value="0">
                  <img class="classic-display wp50" src="../common/images/classic/icon_photo.gif" name="userImage" alt="<gsmsg:write key="cmn.photo" />" />
                  <img class="original-display wp50" src="../common/images/original/photo.png" name="userImage" alt="<gsmsg:write key="cmn.photo" />" />
                </logic:equal>
                <logic:notEqual name="user" property="binSid" value="0">
                  <logic:equal name="user" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                    <div align="center" class="hikokai_photo-m">
                      <span class="hikokai_text">
                        <gsmsg:write key="cmn.private" />
                      </span>
                    </div>
                  </logic:equal>
                  <logic:notEqual name="user" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                    <img src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name='user' property='binSid' />" alt="<gsmsg:write key="cmn.photo" />" width="45px" />
                  </logic:notEqual>
                </logic:notEqual>
              </td>
              <td>
                <!-- 社員/職員番号 -->
                <div class="txt_l no_w">
                  <span class="fs_12 <bean:write name="ukoclass" />">
                    <bean:write name="user" property="usiSyainNo" />
                    &nbsp;
                  </span>
                </div>
                <!-- 名前 -->
                <div class="txt_l">
                  <a href="#!" onClick="openUserInfoWindow(<bean:write name="user" property="usrSid" />);">
                    <span class="<bean:write name="ukoclass" />">
                      <bean:write name="user" property="usiSei" /><span class="pl5"><bean:write name="user" property="usiMei" /></span>
                    </span>
                  </a>
                </div>
                <!-- 役職 -->
                <div class="txt_l no_w">
                  <span class="<bean:write name="ukoclass" />">
                    <bean:write name="user" property="usiYakusyoku" />
                    &nbsp;
                  </span>
                </div>
              </td>
            </tr>
          </table>
        </td>
        <!--  所属グループ -->
        <td>
          <div class="flo_l">
            <logic:notEmpty name="user" property="belongGrpList">
              <logic:iterate id="grp" name="user" property="belongGrpList">
                <div class="<bean:write name="ukoclass" />">
                  <bean:write name="grp" property="groupName" />
                </div>
              </logic:iterate>
            </logic:notEmpty>
          </div>
        </td>
        <!-- 電話・内線 -->
        <td>
          <div class="txt_l no_w">
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
            <% if (strUsiTel1.length() > 0 || strUsiTelNai1.length() > 0) { %>
            <div class="<bean:write name="ukoclass" />">
              <logic:notEmpty name="user" property="usiTel1">
                <bean:write name="user" property="usiTel1" />
                <logic:notEmpty name="user" property="usiTelCmt1">
                  <span class="fs_12">
                    (
                    <bean:write name="user" property="usiTelCmt1" />
                    )
                  </span>
                </logic:notEmpty>
              </logic:notEmpty>
              <logic:notEmpty name="user" property="usiTelNai1">
                <div>
                  <gsmsg:write key="user.136" />
                  :
                  <bean:write name="user" property="usiTelNai1" />
                  <logic:empty name="user" property="usiTel1">
                    <logic:notEmpty name="user" property="usiTelCmt1">
                      <span class="fs_12">
                        (
                        <bean:write name="user" property="usiTelCmt1" />
                        )
                      </span>
                    </logic:notEmpty>
                  </logic:empty>
                </div>
              </logic:notEmpty>
            </div>
            <% } %>
            <% if (strUsiTel2.length() > 0 || strUsiTelNai2.length() > 0) { %>
            <div class="mt10 <bean:write name="ukoclass" />">
              <logic:notEmpty name="user" property="usiTel2">
                <bean:write name="user" property="usiTel2" />
                <logic:notEmpty name="user" property="usiTelCmt2">
                  <span class="fs_12">
                    (
                    <bean:write name="user" property="usiTelCmt2" />
                    )
                  </span>
                </logic:notEmpty>
              </logic:notEmpty>
              <logic:notEmpty name="user" property="usiTelNai2">
                <div>
                  <gsmsg:write key="user.136" />
                  :
                  <bean:write name="user" property="usiTelNai2" />
                  <logic:empty name="user" property="usiTel2">
                    <logic:notEmpty name="user" property="usiTelCmt2">
                      <span class="fs_12">
                        (
                        <bean:write name="user" property="usiTelCmt2" />
                        )
                      </span>
                    </logic:notEmpty>
                  </logic:empty>
                </div>
              </logic:notEmpty>
            </div>
            <% } %>
            <% if (strUsiTel3.length() > 0 || strUsiTelNai3.length() > 0) { %>
            <div class="mt10 <bean:write name="ukoclass" />">
              <logic:notEmpty name="user" property="usiTel3">
                <bean:write name="user" property="usiTel3" />
                <logic:notEmpty name="user" property="usiTelCmt3">
                  <span class="fs_12">
                    (
                    <bean:write name="user" property="usiTelCmt3" />
                    )
                  </span>
                </logic:notEmpty>
              </logic:notEmpty>
              <logic:notEmpty name="user" property="usiTelNai3">
                <div>
                  <gsmsg:write key="user.136" />
                  :
                  <bean:write name="user" property="usiTelNai3" />
                  <logic:empty name="user" property="usiTel3">
                    <logic:notEmpty name="user" property="usiTelCmt3">
                      <span class="fs_12">
                        (
                        <bean:write name="user" property="usiTelCmt3" />
                        )
                      </span>
                    </logic:notEmpty>
                  </logic:empty>
                </div>
              </logic:notEmpty>
            </div>
            <% } %>
          </div>
        </td>
        <!-- メールアドレス -->
        <td>
          <div class="txt_l no_w">
            <logic:notEmpty name="user" property="usiMail1">
              <span class="<bean:write name="ukoclass" />">
                <a href="mailto:<bean:write name="user" property="usiMail1" />">
                  <bean:write name="user" property="mailAddress1" filter="false" />
                </a>
                <logic:notEmpty name="user" property="usiMailCmt1">
                      &nbsp;
                      <span class="fs_12">
                    (
                    <bean:write name="user" property="usiMailCmt1" />
                    )
                  </span>
                </logic:notEmpty>
              </span>
              <br>
            </logic:notEmpty>
            <logic:notEmpty name="user" property="usiMail2">
              <span class="<bean:write name="ukoclass" />">
                <a href="mailto:<bean:write name="user" property="usiMail2" />">
                  <bean:write name="user" property="mailAddress2" filter="false" />
                </a>
                <logic:notEmpty name="user" property="usiMailCmt2">
                    &nbsp;
                    <span class="fs_12">
                    (
                    <bean:write name="user" property="usiMailCmt2" />
                    )
                  </span>
                </logic:notEmpty>
              </span>
              <br>
            </logic:notEmpty>
            <logic:notEmpty name="user" property="usiMail3">
              <span class="<bean:write name="ukoclass" />">
                <a href="mailto:<bean:write name="user" property="usiMail3" />">
                  <bean:write name="user" property="mailAddress3" filter="false" />
                </a>
                <logic:notEmpty name="user" property="usiMailCmt3">
                      &nbsp;
                      <span class="fs_12">
                    (
                    <bean:write name="user" property="usiMailCmt3" />
                    )
                  </span>
                </logic:notEmpty>
              </span>
            </logic:notEmpty>
          </div>
        </td>
        <!-- ラベル -->
        <td>
          <div class="txt_l no_w">
            <logic:notEmpty name="user" property="viewLabelName">
              <span class="baseLabel">
                <bean:write name="user" property="viewLabelName" />
              </span>
            </logic:notEmpty>
          </div>
        </td>
      </tr>
    </logic:iterate>
  </logic:notEmpty>
</table>
