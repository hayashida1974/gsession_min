<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<script src="../bulletin/js/bbs120.js?<%=GSConst.VERSION_PARAM%>"></script>
<html:form action="/bulletin/bbs120" styleClass="js_delForm">
<input type="hidden" name="plgId" value="bbs">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_bbsErrorArea"></div>
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.autodelete" />
      </th>
      <td class="w80">
        <div><gsmsg:write key="bbs.bbs120.1" /></div>
        <div class="mt5">
          <div class="verAlignMid">
            <html:radio name="bbs120Form" styleId="bbs120AtdelFlg_01" property="bbs120AtdelFlg" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.AUTO_DELETE_OFF)%>" onclick="bbsChangeDisable();" />
            <label for="bbs120AtdelFlg_01"><gsmsg:write key="cmn.noset" /></label>
            <html:radio styleClass="ml10" name="bbs120Form" styleId="bbs120AtdelFlg_02" property="bbs120AtdelFlg" value="<%=String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.AUTO_DELETE_ON)%>" onclick="bbsChangeEnable();" />
            <label for="bbs120AtdelFlg_02"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <!-- 年 -->
            <logic:notEmpty name="bbs120Form" property="bbs120AtdelYearLabel" scope="request">
              <html:select styleClass="bbs120AtdelYear" property="bbs120AtdelYear">
                <html:optionsCollection name="bbs120Form" property="bbs120AtdelYearLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <!-- 月 -->
            <logic:notEmpty name="bbs120Form" property="bbs120AtdelMonthLabel" scope="request">
              <html:select styleClass="bbs120AtdelMonth ml5 mr5" property="bbs120AtdelMonth">
                <html:optionsCollection name="bbs120Form" property="bbs120AtdelMonthLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <gsmsg:write key="cmn.after.data" />
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>