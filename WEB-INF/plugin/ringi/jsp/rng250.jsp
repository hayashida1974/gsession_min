<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:form action="/ringi/rng250" styleClass="js_smailSetting">
<input type="hidden" name="plgId" value="rng">
<div class="wrapper w100 mrl_auto">
  <table class="table-left">
    <%-- 受信 --%>
    <tr>
      <th>
        <gsmsg:write key="rng.rng190.03" />
      </th>
      <td>
        <div class="fs_13">
          <gsmsg:write key="rng.rng250.03" />
        </div>
        <span class="verAlignMid mt5">
          <html:radio styleId="notinfoj" name="rng250Form" property="rng250smlNtfJyusin" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SMAIL_NOT_TSUUCHI) %>" /><label for="notinfoj"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio styleId="infoj" name="rng250Form" property="rng250smlNtfJyusin" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SMAIL_TSUUCHI) %>" /><label for="infoj"><gsmsg:write key="cmn.notify" /></label>
        </span>
      </td>
    </tr>
    <%-- 結果 --%>
    <tr>
      <th>
        <gsmsg:write key="rng.rng190.01" />
      </th>
      <td>
        <div class="fs_13">
          <gsmsg:write key="rng.rng250.01" />
        </div>
        <span class="verAlignMid mt5">
          <html:radio styleId="notinfo" name="rng250Form" property="rng250smlNtf" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SMAIL_NOT_TSUUCHI) %>" /><label for="notinfo"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio styleId="info" name="rng250Form" property="rng250smlNtf" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SMAIL_TSUUCHI) %>" /><label for="info"><gsmsg:write key="cmn.notify" /></label>
        </span>
      </td>
    </tr>
    <%-- 代理人 --%>
    <tr>
      <th class="no_w w20">
        <gsmsg:write key="rng.rng190.05" />
      </th>
      <td>
        <div class="fs_13">
          <gsmsg:write key="rng.rng250.05" />
        </div>
        <span class="verAlignMid mt5">
          <html:radio styleId="notinfod" name="rng250Form" property="rng250smlNtfDairi" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SMAIL_NOT_TSUUCHI) %>" /><label for="notinfod"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio styleId="infod" name="rng250Form" property="rng250smlNtfDairi" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RNG_SMAIL_TSUUCHI) %>" /><label for="infod"><gsmsg:write key="cmn.notify" /></label>
        </span>
      </td>
    </tr>
  </table>
</div>
</html:form>
