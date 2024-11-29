<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%@ page import="jp.groupsession.v2.adr.GSConstAddress" %>
<%@ page import="jp.groupsession.v2.adr.adr010.Adr010Const" %>

<table class="table-top">
  <tr>
    <th class="w5 js_tableTopCheck js_tableTopCheck-header">
      <input type="checkbox" name="allCheck" onClick="changeChk();">
    </th>
    <th class="w25">
      <!-- 氏名 -->
      <a href="#" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>);">
        <logic:equal name="adr330Form" property="adr330searchSVBean.sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
          <logic:equal name="adr330Form" property="adr330searchSVBean.orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
            <span class="classic-display"><gsmsg:write key="cmn.name" />▲</span>
            <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_up"></i></span>
          </logic:equal>
          <logic:notEqual name="adr330Form" property="adr330searchSVBean.orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
            <span class="classic-display"><gsmsg:write key="cmn.name" />▼</span>
            <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_down"></i></span>
          </logic:notEqual>
        </logic:equal>
        <logic:notEqual name="adr330Form" property="adr330searchSVBean.sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
          <gsmsg:write key="cmn.name" />
        </logic:notEqual>
      </a>
      ／
      <!-- 役職 -->
      <a href="#" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_POSITION) %>);">
        <logic:equal name="adr330Form" property="adr330searchSVBean.sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_POSITION) %>">
          <logic:equal name="adr330Form" property="adr330searchSVBean.orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
            <span class="classic-display"><gsmsg:write key="cmn.post" />▲</span>
            <span class="original-display"><gsmsg:write key="cmn.post" /><i class="icon-sort_up"></i></span>
          </logic:equal>
          <logic:notEqual name="adr330Form" property="adr330searchSVBean.orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
            <span class="classic-display"><gsmsg:write key="cmn.post" />▼</span>
            <span class="original-display"><gsmsg:write key="cmn.post" /><i class="icon-sort_down"></i></span>
          </logic:notEqual>
        </logic:equal>
        <logic:notEqual name="adr330Form" property="adr330searchSVBean.sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_POSITION) %>">
          <gsmsg:write key="cmn.post" />
        </logic:notEqual>
      </a>
    </th>
    <th class="w25">
      <!-- 会社名 -->
      <a href="#" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>);">
        <logic:equal name="adr330Form" property="adr330searchSVBean.sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
          <logic:equal name="adr330Form" property="adr330searchSVBean.orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
            <span class="classic-display"><gsmsg:write key="cmn.company.name" />▲</span>
            <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_up"></i></span>
          </logic:equal>
          <logic:notEqual name="adr330Form" property="adr330searchSVBean.orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
            <span class="classic-display"><gsmsg:write key="cmn.company.name" />▼</span>
            <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_down"></i></span>
          </logic:notEqual>
        </logic:equal>
        <logic:notEqual name="adr330Form" property="adr330searchSVBean.sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
          <gsmsg:write key="cmn.company.name" />
        </logic:notEqual>
      </a>
      ／
      <!-- 拠点 -->
      <a href="#" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>);">
        <logic:equal name="adr330Form" property="adr330searchSVBean.sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>">
          <logic:equal name="adr330Form" property="adr330searchSVBean.orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
            <span class="classic-display"><gsmsg:write key="address.10" />▲</span>
            <span class="original-display"><gsmsg:write key="address.10" /><i class="icon-sort_up"></i></span>
          </logic:equal>
          <logic:notEqual name="adr330Form" property="adr330searchSVBean.orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
            <span class="classic-display"><gsmsg:write key="address.10" />▼</span>
            <span class="original-display"><gsmsg:write key="address.10" /><i class="icon-sort_down"></i></span>
          </logic:notEqual>
        </logic:equal>
        <logic:notEqual name="adr330Form" property="adr330searchSVBean.sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>">
          <gsmsg:write key="address.10" />
        </logic:notEqual>
      </a>
    </th>
    <!-- 電話番号 -->
    <th class="w20">
      <gsmsg:write key="cmn.tel" />
    </th>
    <!-- E-MAIL -->
    <th class="w25">
      <gsmsg:write key="address.96" />
    </th>
  </tr>
  <logic:iterate id="detailMdl" name="adr330Form" property="detailList">
    <tr>
      <td class="txt_c js_tableTopCheck">
        <html:multibox name="adr330Form" property="adr330selectSid"><bean:write name="detailMdl"  property="adrSid" /></html:multibox>
      </td>
      <td>
        <logic:notEmpty name="detailMdl" property="positionName" >
          <div>
            <bean:write name="detailMdl" property="positionName" />
          </div>
        </logic:notEmpty>
        <a href="#" onClick="return editAddress('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT) %>', '<bean:write name="detailMdl" property="adrSid" />')">
          <bean:write name="detailMdl" property="userName" />
        </a>
        <logic:notEmpty name="detailMdl" property="viewLabelName">
          <br>
          <span class="baseLabel ml0">
            <bean:write name="detailMdl" property="viewLabelName" />
          </span>
        </logic:notEmpty>
      </td>
      <td>
        <a href="#" onClick="return viewCompany('<bean:write name="detailMdl" property="acoSid" />');">
          <bean:write name="detailMdl" property="companyName" />
        </a>
        <logic:notEmpty name="detailMdl" property="companyBaseName">
          <div class="ml5">
            <bean:write name="detailMdl" property="companyBaseName" />
          </div>
        </logic:notEmpty>
      </td>
      <td>
        <logic:notEmpty name="detailMdl" property="tel1">
          <div>
            <bean:write name="detailMdl" property="tel1" />
            <logic:notEmpty name="detailMdl" property="telCmt1">
              <span class="ml5">(<bean:write name="detailMdl" property="telCmt1" />)</span>
            </logic:notEmpty>
          </div>
        </logic:notEmpty>
        <logic:notEmpty name="detailMdl" property="tel2">
          <div>
            <bean:write name="detailMdl" property="tel2" />
            <logic:notEmpty name="detailMdl" property="telCmt2">
              <span class="ml5">(<bean:write name="detailMdl" property="telCmt2" />)</span>
            </logic:notEmpty>
          </div>
        </logic:notEmpty>
        <logic:notEmpty name="detailMdl" property="tel3">
          <div>
            <bean:write name="detailMdl" property="tel3" />
            <logic:notEmpty name="detailMdl" property="telCmt3">
              <span class="ml5">(<bean:write name="detailMdl" property="telCmt3" />)</span>
            </logic:notEmpty>
          </div>
        </logic:notEmpty>
      </td>
      <td>
        <logic:notEmpty name="detailMdl" property="mail1">
          <div>
            <a href="mailto:<bean:write name="detailMdl" property="mail1" />"><bean:write name="detailMdl" property="mail1" /></a>
            <logic:notEmpty name="detailMdl" property="mailCmt1">
              <span class="ml5">(<bean:write name="detailMdl" property="mailCmt1" />)</span>
            </logic:notEmpty>
          </div>
        </logic:notEmpty>
        <logic:notEmpty name="detailMdl" property="mail2">
          <div>
            <a href="mailto:<bean:write name="detailMdl" property="mail2" />"><bean:write name="detailMdl" property="mail2" /></a>
            <logic:notEmpty name="detailMdl" property="mailCmt2">
              <span class="ml5">(<bean:write name="detailMdl" property="mailCmt2" />)</span>
            </logic:notEmpty>
          </div>
        </logic:notEmpty>
        <logic:notEmpty name="detailMdl" property="mail3">
          <div>
            <a href="mailto:<bean:write name="detailMdl" property="mail3" />"><bean:write name="detailMdl" property="mail3" /></a>
            <logic:notEmpty name="detailMdl" property="mailCmt3">
              <span class="ml5">(<bean:write name="detailMdl" property="mailCmt3" />)</span>
            </logic:notEmpty>
          </div>
        </logic:notEmpty>
      </td>
    </tr>
  </logic:iterate>
</table>
