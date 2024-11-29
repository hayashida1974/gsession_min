<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>

<table class="bor1 w100 bgC_lightGray mt0 borC_light border_top_none">
  <tr>
    <td class="p10 w100">
      <table class="table-left w100 mt0">
        <tr>
          <th class="w15 no_w">
            <gsmsg:write key="cmn.capture.file" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
          </th>
          <td class="w85">
            <span class="fs_13">
              <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
              <% String csvFileMsg = "<a href=\"../main/man330.do?CMD=man330_sample\">" + gsMsg.getMessage(request, "cmn.capture.csvfile") + "</a>"; %>
              *<gsmsg:write key="cmn.plz.specify2" arg0="<%= csvFileMsg %>" />
            </span>
            <attachmentFile:filearea
              mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
              pluginId="<%= GSConstMain.PLUGIN_ID_MAIN %>"
              tempDirId="man330" />
          </td>
        </tr>
      </table>
      <div class="txt_c">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onclick="buttonPush('import_exe');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
      </div>
    </td>
  </tr>
</table>