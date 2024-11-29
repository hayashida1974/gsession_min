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
<% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(request);%>
<table class="main_searchArea w100 bgC_lightGray mt0 borC_light">
  <tr>
    <td class="txt_c w50 p5">
      <% java.util.List rowList = new java.util.ArrayList(); %>
      <% rowList.add(new String[]{gsMsg.getMessage(request, "cmn.kana.a"), gsMsg.getMessage(request, "cmn.kana.ka"), gsMsg.getMessage(request, "cmn.kana.sa"), gsMsg.getMessage(request, "cmn.kana.ta"), gsMsg.getMessage(request, "cmn.kana.na"), gsMsg.getMessage(request, "cmn.kana.ha"), gsMsg.getMessage(request, "cmn.kana.ma"), gsMsg.getMessage(request, "cmn.kana.ya"), gsMsg.getMessage(request, "cmn.kana.ra"), gsMsg.getMessage(request, "cmn.kana.wa")}); %>
      <% rowList.add(new String[]{gsMsg.getMessage(request, "cmn.kana.i"), gsMsg.getMessage(request, "cmn.kana.ki"), gsMsg.getMessage(request, "cmn.kana.shi"), gsMsg.getMessage(request, "cmn.kana.chi"), gsMsg.getMessage(request, "cmn.kana.ni"), gsMsg.getMessage(request, "cmn.kana.hi"), gsMsg.getMessage(request, "cmn.kana.mi"), "", gsMsg.getMessage(request, "cmn.kana.ri"), gsMsg.getMessage(request, "cmn.kana.wo")}); %>
      <% rowList.add(new String[]{gsMsg.getMessage(request, "cmn.kana.u"), gsMsg.getMessage(request, "cmn.kana.ku"), gsMsg.getMessage(request, "cmn.kana.su"), gsMsg.getMessage(request, "cmn.kana.tsu"), gsMsg.getMessage(request, "cmn.kana.nu"), gsMsg.getMessage(request, "cmn.kana.fu"), gsMsg.getMessage(request, "cmn.kana.mu"), gsMsg.getMessage(request, "cmn.kana.yu"), gsMsg.getMessage(request, "cmn.kana.ru"), gsMsg.getMessage(request, "cmn.kana.n")}); %>
      <% rowList.add(new String[]{gsMsg.getMessage(request, "cmn.kana.e"), gsMsg.getMessage(request, "cmn.kana.ke"), gsMsg.getMessage(request, "cmn.kana.se"), gsMsg.getMessage(request, "cmn.kana.te"), gsMsg.getMessage(request, "cmn.kana.ne"), gsMsg.getMessage(request, "cmn.kana.he"), gsMsg.getMessage(request, "cmn.kana.me"), "", gsMsg.getMessage(request, "cmn.kana.re"), ""}); %>
      <% rowList.add(new String[]{gsMsg.getMessage(request, "cmn.kana.o"), gsMsg.getMessage(request, "cmn.kana.ko"), gsMsg.getMessage(request, "cmn.kana.so"), gsMsg.getMessage(request, "cmn.kana.to"), gsMsg.getMessage(request, "cmn.kana.no"), gsMsg.getMessage(request, "cmn.kana.ho"), gsMsg.getMessage(request, "cmn.kana.mo"), gsMsg.getMessage(request, "cmn.kana.yo"), gsMsg.getMessage(request, "cmn.kana.ro"), ""}); %>
      <bean:define id="extKanaList" name="adr010Form" property="adr010cnameKanaList" type="java.util.List" />
      <table class="bgC_tableCell mrl_auto">
        <% for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) { %>
          <%     String[] kanaArray = (String[]) rowList.get(rowIndex); %>
          <tr class="txt_c">
            <%     for (int kanaIndex = 0; kanaIndex < kanaArray.length; kanaIndex++) { %>
            <%         String kana = kanaArray[kanaIndex]; %>
            <%         if (extKanaList.contains(kana)) { %>
            <td class="bor1 wp50 hp50 cursor_p" onClick="return searchToComKana('<%= kana %>');">
              <div class="cl_linkDef wp50 hp50 td-hoverChange font-bold display_tbl_c txt_c txt_m"><%= kana %></div>
            </td>
            <%         } else { %>
            <td class="bor1 wp50 hp50"><%= kana %></td>
            <%         } %>
            <%     } %>
          </tr>
        <% } %>
      </table>
    </td>
    <td class="w50 p5">
      <table class="table-left bgC_body mt0">
        <tr>
          <th class="w25">
            <gsmsg:write key="address.7" />
          </th>
          <td class="w75">
            <html:text property="adr010code" maxlength="20" styleClass="w100"/>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.company.name" />
          </th>
          <td class="w75">
            <html:text property="adr010coName" maxlength="50" styleClass="w100"/>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="address.9" />
          </th>
          <td class="w75">
            <html:text property="adr010coNameKn" maxlength="100" styleClass="w100"/>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="address.10" />
          </th>
          <td class="w75">
            <html:text property="adr010coBaseName" maxlength="50" styleClass="w100" />
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="address.11" />
          </th>
          <td class="w75">
            <html:select name="adr010Form" property="adr010atiSid" styleClass="w100">
              <html:optionsCollection name="adr010Form" property="atiCmbList" value="value" label="label" />
            </html:select>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.prefectures" />
          </th>
          <td class="w75">
            <html:select name="adr010Form" property="adr010tdfk">
              <html:optionsCollection name="adr010Form" property="tdfkCmbList" value="value" label="label" />
            </html:select>
          </td>
        </tr>
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.memo" />
          </th>
          <td class="w75">
            <html:text property="adr010biko" maxlength="1000" styleClass="w100"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td colspan="2" class="txt_c">
      <button type="button" value="<gsmsg:write key="cmn.advanced.search" />" onClick="buttonPush('search');" class="baseBtn">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
        <gsmsg:write key="cmn.search" />
      </button>
    </td>
  </tr>
</table>