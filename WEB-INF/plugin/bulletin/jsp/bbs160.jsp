<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<script src="../bulletin/js/bbs160.js?<%=GSConst.VERSION_PARAM%>"></script>
<html:form action="/bulletin/bbs160" styleClass="js_smailSetting">
<input type="hidden" name="plgId" value="bbs">
  <div class="wrapper w100">
    <table class="table-left">
      <tr>
        <th class="w20">
          <gsmsg:write key="shortmail.notification" />
        </th>
        <td class="w80">
          <span class="verAlignMid">
            <html:radio name="bbs160Form" styleId="js_bbsSmlNtf_01" property="bbs160smlNtf" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BAC_SML_NTF_ADMIN)%>" />
            <label for="js_bbsSmlNtf_01">
              <gsmsg:write key="cmn.set.the.admin" />
            </label>
            <html:radio styleClass="ml10" name="bbs160Form" styleId="js_bbsSmlNtf_02" property="bbs160smlNtf" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BAC_SML_NTF_USER)%>" />
            <label for="js_bbsSmlNtf_02">
              <gsmsg:write key="cmn.set.eachuser" />
            </label>
          </span>
        </td>
      </tr>
      
      <tr id="js_bbsSmlNoticeKbnArea">
        <th class="w20">
          <gsmsg:write key="bbs.bbs160.1" />
        </th>
        <td class="w80">
	      <div class="fs_13">
	        <gsmsg:write key="bbs.bbs050.12" />
	      </div>
	      <span class="verAlignMid mt5">
	        <html:radio name="bbs160Form" styleId="bbsSmlNtfKbn_02" property="bbs160smlNtfKbn" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BAC_SML_NTF_KBN_NO)%>" />
	        <label for="bbsSmlNtfKbn_02">
	          <gsmsg:write key="cmn.dont.notify" />
	        </label>
	        <html:radio styleClass="ml10" name="bbs160Form" styleId="bbsSmlNtfKbn_01" property="bbs160smlNtfKbn" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BAC_SML_NTF_KBN_YES)%>" />
	        <label for="bbsSmlNtfKbn_01">
	          <gsmsg:write key="cmn.notify" />
	        </label>
	      </span>
        </td>
      </tr>
    </table>
  </div>
</html:form>
