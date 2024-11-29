<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<html:form action="/bulletin/bbs052" styleClass="js_smailSetting">
<input type="hidden" name="plgId" value="bbs">
  <div class="wrapper w100 mrl_auto">
    <table class="table-left">
      <tr>
        <th class="w20">
          <gsmsg:write key="bbs.bbs052.2" />
        </th>
        <td class="w80">
          <div class="fs_13">
            <gsmsg:write key="bbs.bbs050.12" />
          </div>
          <div class="verAlignMid mt5">
            <html:radio name="bbs052Form" styleId="bbs052smlNtf_02" property="bbs052smlNtf" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_SMAIL_NOT_TSUUCHI)%>" />
            <label for="bbs052smlNtf_02">
              <gsmsg:write key="cmn.dont.notify" />
            </label>
            <html:radio name="bbs052Form" styleId="bbs052smlNtf_01" property="bbs052smlNtf" styleClass="ml10" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_SMAIL_TSUUCHI)%>" />
            <label for="bbs052smlNtf_01">
              <gsmsg:write key="cmn.notify" />
            </label>
          </div>
        </td>
      </tr>
    </table>
  </div>
</html:form>