<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>GROUPSESSION <gsmsg:write key="cmn.select.company" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../address/js/adr240.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../address/css/address.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<logic:notEqual name="adr240Form" property="adr240ProAddErrFlg" value="1">
  <body class="body_03">
</logic:notEqual>

<logic:equal name="adr240Form" property="adr240ProAddErrFlg" value="1">
  <body class="body_03" onload="selectCompany();">
</logic:equal>

<html:form action="/address/adr240">
<input type="hidden" name="CMD" value="">

<html:hidden property="adr240Index" />
<html:hidden property="adr240Str" />
<html:hidden property="adr240page" />
<html:hidden property="adr240parentPageId" />
<html:hidden property="adr240CompanySid" />
<html:hidden property="adr240CompanyBaseSid" />
<html:hidden property="adr240CompanyCode" />
<html:hidden property="adr240CompanyName" />
<html:hidden property="adr240mode" />
<html:hidden property="adr240ProAddFlg" />
<html:hidden property="adr240ProAddErrFlg" />
<html:hidden property="adr240RowNumber" />
<html:hidden property="adr240PrsMode" />
<html:hidden property="adr240SearchMode" />

<html:hidden property="adr240svCode" />
<html:hidden property="adr240svCoName" />
<html:hidden property="adr240svCoNameKn" />
<html:hidden property="adr240svCoBaseName" />
<html:hidden property="adr240svAtiSid" />
<html:hidden property="adr240svTdfk" />
<html:hidden property="adr240svBiko" />

<input type="hidden" name="adr240tanto" value="0">


<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../address/images/classic/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
      <img class="header_pluginImg" src="../address/images/original/header_address.png" alt="<gsmsg:write key="cmn.addressbook" />">
    </li>
    <li><gsmsg:write key="cmn.addressbook" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="cmn.select.company" />
    </li>
    <li>
      <div>
        <logic:equal name="adr240Form" property="adr240mode" value="0">
          <logic:equal name="adr240Form" property="adr240PrsMode" value="0">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.select" />" onClick="return parentReload();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.close" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.close" />">
              <gsmsg:write key="cmn.select" />
            </button>
          </logic:equal>
          <logic:notEqual name="adr240Form" property="adr240PrsMode" value="0">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.select" />" onClick="return setParent();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.close" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.close" />">
              <gsmsg:write key="cmn.select" />
            </button>
          </logic:notEqual>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />"  onClick="return window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w90 mrl_auto">
  <bean:define id="procMode" name="adr240Form" property="adr240SearchMode" />
  <% int sMode = ((Integer) procMode).intValue(); %>
  <% if (sMode == 0) { %>
    <ul class="tabHeader w100">
      <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none bgI_none bgC_lightGray" id="tab1" onclick="javascript:change240Tab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_50) %>');">
        <gsmsg:write key="address.adr100.1" />
      </li>
      <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab2" onclick="javascript:change240Tab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) %>');">
        <gsmsg:write key="cmn.advanced.search" />
      </li>
      <li class="tabHeader_space border_bottom_none"></li>
    </ul>
    <div id="tab1_block" class="bgC_lightGray bor1 p10">
      <table class="w100 bgC_body">
        <tr>
          <logic:equal name="adr240Form" property="adr240Index" value="-1">
            <td class="bor1 txt_c bgC_gray w10">
              <span class="cl_fontOutline"><gsmsg:write key="cmn.all" /></span>
            </td>
          </logic:equal>
          <logic:notEqual name="adr240Form" property="adr240Index" value="-1">
            <td class="bor1 txt_c w10 td-hoverChange cursor_p" onClick="return selectLine('-1');">
              <span class="cl_linkDef">
                <gsmsg:write key="cmn.all" />
              </span>
            </td>
          </logic:notEqual>
          <logic:notEmpty name="adr240Form" property="adr240IndexList">
            <logic:iterate id="lineLabel" name="adr240Form" property="adr240IndexList">
              <logic:equal name="lineLabel" property="value" value="0">
                <td class="bor1 txt_c w7 td-hoverChange cursor_p" onClick="return selectLine('<bean:write name="lineLabel" property="label" />');">
                  <span class="cl_linkDef">
                    <bean:write name="lineLabel" property="label" />
                  </span>
                </td>
              </logic:equal>
              <logic:equal name="lineLabel" property="value" value="1">
                <td class="bor1 txt_c w7">
                  <bean:write name="lineLabel" property="label" />
                </td>
              </logic:equal>
              <logic:equal name="lineLabel" property="value" value="2">
                <td class="bor1 txt_c bgC_gray w7">
                  <span class="cl_fontOutline"><bean:write name="lineLabel" property="label" /></span>
                </td>
              </logic:equal>
            </logic:iterate>
          </logic:notEmpty>
          <logic:equal name="adr240Form" property="adr240Index" value="99">
            <td class="no_w bor1 txt_c bgC_gray w20">
              <span class="cl_fontOutline"><gsmsg:write key="address.adr240.1" /></span>
            </td>
          </logic:equal>
          <logic:notEqual name="adr240Form" property="adr240Index" value="99">
            <td class="no_w bor1 txt_c w20 td-hoverChange cursor_p" onClick="return selectLine('99');">
              <span class="cl_linkDef">
                <gsmsg:write key="address.adr240.1" />
              </class>
            </td>
          </logic:notEqual>
        </tr>
      </table>
      <table class="w100 mt5 bgC_body">
        <tr>
          <logic:equal name="adr240Form" property="adr240Str" value="-1">
            <td class="bor1 txt_c bgC_gray w25">
              <span class="cl_fontOutline"><gsmsg:write key="cmn.all" /></span>
            </td>
          </logic:equal>
          <logic:notEqual name="adr240Form" property="adr240Str" value="-1">
            <td class="bor1 txt_c w25 td-hoverChange cursor_p" onClick="return selectStr('-1');">
              <span class="cl_linkDef">
                <gsmsg:write key="cmn.all" />
              </span>
            </td>
          </logic:notEqual>
          <logic:notEmpty name="adr240Form" property="adr240StrList">
            <logic:iterate id="strLabel" name="adr240Form" property="adr240StrList">
              <logic:equal name="strLabel" property="value" value="0">
                <td class="bor1 txt_c w15 td-hoverChange cursor_p" onClick="return selectStr('<bean:write name="strLabel" property="label" />');">
                  <span class="cl_linkDef">
                    <bean:write name="strLabel" property="label" />
                  </span>
                </td>
              </logic:equal>
              <logic:equal name="strLabel" property="value" value="1">
                <td class="bor1 txt_c w15">
                  <bean:write name="strLabel" property="label" />
                </td>
              </logic:equal>
              <logic:equal name="strLabel" property="value" value="2">
                <td class="bor1 txt_c bgC_gray w15">
                  <span class="cl_fontOutline"><bean:write name="strLabel" property="label" /></span>
                </td>
              </logic:equal>
            </logic:iterate>
          </logic:notEmpty>
        </tr>
      </table>
    </div>
  <% } else if (sMode == 1) { %>
    <ul class="tabHeader w100">
      <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab1" onclick="javascript:change240Tab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_50) %>');">
        <gsmsg:write key="address.adr100.1" />
      </li>
      <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none bgI_none bgC_lightGray" id="tab2" onclick="javascript:change240Tab('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.SEARCH_COMPANY_MODE_DETAIL) %>');">
        <gsmsg:write key="cmn.advanced.search" />
      </li>
      <li class="tabHeader_space border_bottom_none"></li>
    </ul>
    <div id="tab2_block" class="bgC_lightGray bor1 p10">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
      <table class="table-left mt0">
        <tr>
          <th class="w15">
            <gsmsg:write key="address.7" />
          </th>
          <td class="w35">
            <html:text property="adr240code" maxlength="20" styleClass="wp250" />
          </td>
          <th class="w15">
            <gsmsg:write key="address.11" />
          </th>
          <td class="w35">
            <logic:notEmpty name="adr240Form" property="atiCmbList">
              <html:select name="adr240Form" property="adr240atiSid">
                <html:optionsCollection name="adr240Form" property="atiCmbList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w15">
            <gsmsg:write key="cmn.company.name" />
          </th>
          <td class="w35">
            <html:text property="adr240coName" maxlength="50" styleClass="wp250" />
          </td>
          <th class="w15">
            <gsmsg:write key="cmn.prefectures" />
          </th>
          <td class="w35">
            <logic:notEmpty name="adr240Form" property="tdfkCmbList">
              <html:select name="adr240Form" property="adr240tdfk">
                <html:optionsCollection name="adr240Form" property="tdfkCmbList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w15">
            <gsmsg:write key="address.9" />
          </th>
          <td class="w35">
            <html:text property="adr240coNameKn" maxlength="100" styleClass="wp250" />
          </td>
          <th class="w15">
            <gsmsg:write key="cmn.memo" />
          </th>
          <td class="w30">
            <html:text property="adr240biko" maxlength="1000" styleClass="wp250" />
          </td>
        </tr>
        <tr>
          <th class="w15">
            <gsmsg:write key="address.10" />
          </th>
          <td colspan="3">
            <html:text property="adr240coBaseName" maxlength="50" styleClass="wp250" />
          </td>
        </tr>
      </table>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="cmn.search" />
        </button>
      </div>
    </div>
  <% } %>

  <logic:notEmpty name="adr240Form" property="adr240CompanyList">
    <logic:notEmpty  name="adr240Form" property="pageCmbList">
      <div class="paging mt5">
        <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  name="adr240Form" property="adr240pageTop" onchange="buttonPush('changePageTop');">
          <html:optionsCollection name="adr240Form" property="pageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:notEmpty>


    <logic:notEqual name="adr240Form" property="adr240mode" value="0">
      <table class="table-top">
        <tr>
          <th>
            <gsmsg:write key="address.118" />
          </th>
        </tr>
        <logic:iterate id="companyModel" name="adr240Form" property="adr240CompanyList" indexId="idx">
          <bean:define id="coData" name="companyModel" type="jp.groupsession.v2.adr.adr240.model.Adr240Model" />
          <% String companyId = String.valueOf(coData.getAcoSid()) + ":" + String.valueOf(coData.getAbaSid()); %>
          <% String companyId2 = String.valueOf(coData.getAcoSid()) + "_" + String.valueOf(coData.getAbaSid()); %>
          <% String companyName = coData.getAcoName();
             if (coData.getAbaSid() != 0) {
            	 companyName += " " + String.valueOf(coData.getAbaName()); 
             }
          %>
          
          <tr class="js_listHover cursor_p">
            <logic:equal name="adr240Form" property="adr240selMode" value="1">
              <td id="<%= companyId %>" onClick="return setParent('<%= companyId %>');">
                <input type="hidden" name="adr240selectCompanyCode<%= companyId2 %>" value="<bean:write name="companyModel" property="acoCode" />">
                <input type="hidden" name="adr240selectCompanyName<%= companyId %>" value="<%= companyName %>">
                <bean:write name="companyModel" property="acoName" /> <bean:write name="companyModel" property="abaName" />
              </td>
            </logic:equal>
            <logic:notEqual name="adr240Form" property="adr240selMode" value="1">
              <td  onClick="clickCompanyName2('<%= companyId %>');">
                <input type="hidden" name="adr240selectCompanyCode<%= companyId2 %>" value="<bean:write name="companyModel" property="acoCode" />">
                <input type="hidden" name="adr240selectCompanyName<%= companyId %>" value="<%= companyName %>">
                <bean:write name="companyModel" property="acoName" /> <bean:write name="companyModel" property="abaName" />
              </td>
            </logic:notEqual>
          </tr>
        </logic:iterate>
      </table>
    </logic:notEqual>
    <logic:equal name="adr240Form" property="adr240mode" value="0">
      <div class="wrapperContent-2column">
        <div class="w60">
          <table class="table-top">
            <tr>
              <th colspan="2">
                <gsmsg:write key="address.118" />
              </th>
            </tr>
            <logic:iterate id="companyModel" name="adr240Form" property="adr240CompanyList" indexId="idx">
              <bean:define id="coData" name="companyModel" type="jp.groupsession.v2.adr.adr240.model.Adr240Model" />
              <% String companyId3 = String.valueOf(coData.getAcoSid()) + ":" + String.valueOf(coData.getAbaSid()); %>
              <% String companyId4 = String.valueOf(coData.getAcoSid()) + "_" + String.valueOf(coData.getAbaSid()); %>
              <% String companyName2 = coData.getAcoName();
                 if (coData.getAbaSid() != 0) {
            	     companyName2 += " " + String.valueOf(coData.getAbaName()); 
                 }
              %>
              <tr class="js_listHover cursor_p">
                <td class="w5 txt_c" onClick="clickCompanyName(<%= String.valueOf(coData.getAcoSid()) %>, <%= String.valueOf(coData.getAbaSid()) %>);">
                  <logic:notEqual name="adr240Form" property="adr240ProAddFlg" value="1">
                    <input type="radio" name="adr240selectCompany" value="<%= companyId3 %>" onclick="selectCompany();">
                  </logic:notEqual>
                  <logic:equal name="adr240Form" property="adr240ProAddFlg" value="1">
                    <html:radio name="adr240Form" property="adr240selectCompany" value="<%= companyId3 %>" onclick="selectCompany();" />
                  </logic:equal>
                </td>
                <td class="w55 txt_l" onClick="clickCompanyName(<%= String.valueOf(coData.getAcoSid()) %>, <%= String.valueOf(coData.getAbaSid()) %>);">
                  <input type="hidden" name="adr240selectCompanyCode<%= companyId4 %>" value="<bean:write name="companyModel" property="acoCode" />">
                  <input type="hidden" name="adr240selectCompanyName<%= companyId3 %>" value="<%= companyName2 %>">
                  <bean:write name="companyModel" property="acoName" /> <bean:write name="companyModel" property="abaName" />
                </td>
              </tr>
            </logic:iterate>
          </table>
        </div>
        <div class="w40 ml5">
          <table class="table-top">
            <tbody id="tantoAll">
              <tr>
                <th colspan="2">
                  <gsmsg:write key="cmn.staff" />
                </th>
              </tr>
              <tr id="tantoArea">
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </logic:equal>

    <logic:notEmpty  name="adr240Form" property="pageCmbList">
      <div class="paging">
        <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  name="adr240Form" property="adr240pageBottom" onchange="buttonPush('changePageBottom');">
          <html:optionsCollection name="adr240Form" property="pageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:notEmpty>
  </logic:notEmpty>
  <div class="footerBtn_block mt10">
    <logic:equal name="adr240Form" property="adr240mode" value="0">
      <logic:equal name="adr240Form" property="adr240PrsMode" value="0">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.select" />" onClick="return parentReload();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.select" />
        </button>
      </logic:equal>
      <logic:notEqual name="adr240Form" property="adr240PrsMode" value="0">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.select" />" onClick="return setParent();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.select" />
        </button>
      </logic:notEqual>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />"  onClick="return window.close();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>
</div>
</html:form>
</body>
</html:html>