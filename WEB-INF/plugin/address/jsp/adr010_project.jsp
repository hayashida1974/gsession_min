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
<html:hidden property="projectCmbsize" />
<table class="main_searchArea w100 bgC_lightGray mt0 borC_light">
  <tr>
    <td class="w50 p5 txt_t">
      <table class="table-left">
        <tr>
          <th class="w40">
           <gsmsg:write key="address.adr010.project.1" />
          </th>
          <td class="w60">
            <span class="verAlignMid">
              <html:radio name="adr010Form" property="projectKbn" value="0" styleId="prjAdd" onclick="buttonPush('chgPrjDspKbn');"/><label for="prjAdd"><gsmsg:write key="cmn.join.project" /></label>
              <html:radio styleClass="ml10" name="adr010Form" property="projectKbn" value="1" styleId="prjAll" onclick="buttonPush('chgPrjDspKbn');"/><label for="prjAll"><gsmsg:write key="cmn.all" /></label>
            </span>
          </td>
        </tr>
        <tr>
          <th class="w40">
            <gsmsg:write key="cmn.status" />
          </th>
          <td class="w60">
            <span class="verAlignMid">
              <html:radio name="adr010Form" property="statusKbn" value="0" styleId="prjNoStat" onclick="buttonPush('chgPrjDspKbn');"/><label for="prjNoStat"><span class="text_base2"><gsmsg:write key="address.adr010.project.4" /></span></label>&nbsp;
              <html:radio styleClass="ml10" name="adr010Form" property="statusKbn" value="1" styleId="prjCompStat" onclick="buttonPush('chgPrjDspKbn');"/><label for="prjCompStat"><span class="text_base2"><gsmsg:write key="cmn.complete" /></span></label>&nbsp;
              <html:radio styleClass="ml10" name="adr010Form" property="statusKbn" value="2" styleId="prjAllStat" onclick="buttonPush('chgPrjDspKbn');"/><label for="prjAllStat"><span class="text_base2"><gsmsg:write key="cmn.all" /></span></label>
            </span>
          </td>
        </tr>
      </table>
    </td>
    <td class="w50 p5 txt_t">
      <table class="table-left">
        <tr>
          <th class="w40">
           <gsmsg:write key="address.adr010.project.6" />
          </th>
          <td class="w60">
            <logic:notEmpty name="adr010Form" property="projectCmbList">
              <html:select property="selectingProject" styleClass="w100">
                <html:optionsCollection name="adr010Form" property="projectCmbList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td colspan="2" class="pb10 txt_c">
      <button type="button" value="<gsmsg:write key="cmn.search" />" onClick='buttonPush("search");' class="baseBtn">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
        <gsmsg:write key="cmn.search" />
      </button>
    </td>
  </tr>
</table>
