<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src="../ringi/js/rng190.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/ringi/rng190" styleClass="js_smailSetting">
<html:hidden property="backScreen" />
<input type="hidden" name="plgId" value="rng">
<!-- BODY -->
<div class="wrapper w100">
  <table class="table-left">
    <!-- ショートメール通知設定 -->
    <tr>
      <th class="w20">
        <gsmsg:write key="shortmail.notification" />
      </th>
      <td class="w80">
        <span class="verAlignMid">
          <html:radio name="rng190Form" styleId="js_rngSmlNtf_01" property="rng190SmlNtf" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SML_NTF_ADMIN) %>" />
          <label for="js_rngSmlNtf_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio styleClass="ml10"  name="rng190Form" styleId="js_rngSmlNtf_02" property="rng190SmlNtf" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SML_NTF_USER) %>" />
          <label for="js_rngSmlNtf_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </span>
      </td>
    </tr>
    <!-- 受信通知設定 -->
    <tr id="js_rngSmlNoticeJusKbnArea">
      <th class="w20">
        <gsmsg:write key="rng.rng190.03" />
      </th>
      <td class="w80">
        <div class="fs_13">
          <gsmsg:write key="rng.rng250.03" />
        </div>
        <span class="verAlignMid mt5">
          <html:radio name="rng190Form" styleId="rmgSmlJusKbn_02" property="rng190SmlJusin" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SML_NTF_KBN_NO) %>" />
          <label for="rmgSmlJusKbn_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="rng190Form" styleId="rmgSmlJusKbn_01" property="rng190SmlJusin" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SML_NTF_KBN_YES) %>" />
          <label for="rmgSmlJusKbn_01"><gsmsg:write key="cmn.notify" /></label>
        </span>
      </td>
    </tr>
    <!-- 結果通知設定 -->
    <tr id="js_rngSmlNoticeKbnArea">
      <th class="w20">
        <gsmsg:write key="rng.rng190.01" />
      </th>
      <td>
        <div class="fs_13">
          <gsmsg:write key="rng.rng250.01" />
        </div>
        <span class="verAlignMid mt5">
          <html:radio name="rng190Form" styleId="rngSmlNtfKbn_02" property="rng190SmlNtfKbn" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SML_NTF_KBN_NO) %>" />
          <label for="rngSmlNtfKbn_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="rng190Form" styleId="rngSmlNtfKbn_01" property="rng190SmlNtfKbn" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SML_NTF_KBN_YES) %>" />
          <label for="rngSmlNtfKbn_01"><gsmsg:write key="cmn.notify" /></label>
        </span>
      </td>
    </tr>
    <!-- 代理人通知設定 -->
    <tr id="js_rngSmlNoticeDairiKbnArea">
      <th class="w20">
        <gsmsg:write key="rng.rng190.05" />
      </th>
      <td>
        <div class="fs_13">
          <gsmsg:write key="rng.rng250.05" />
        </div>
        <span class="verAlignMid mt5">
          <html:radio name="rng190Form" styleId="rngSmlDairiKbn_02" property="rng190SmlDairi" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SML_NTF_KBN_NO) %>" />
          <label for="rngSmlDairiKbn_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="rng190Form" styleId="rngSmlDairiKbn_01" property="rng190SmlDairi" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.rng.RngConst.RAR_SML_NTF_KBN_YES) %>" />
          <label for="rngSmlDairiKbn_01"><gsmsg:write key="cmn.notify" /></label>
        </span>
      </td>
    </tr>
  </table>
</div>
</html:form>
