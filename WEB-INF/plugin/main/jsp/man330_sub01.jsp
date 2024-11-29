<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>

<table class="bor1 w100 bgC_lightGray mt0 borC_light border_top_none">
  <tr>
    <td class="p10 w100">
      <div class="txt_l cl_fontWarn">
        <gsmsg:write key="main.sel.items.export.output" />
      </div>
      <table class="table-left w100 mt0">
        <tr>
          <th class="w15 no_w">
            <gsmsg:write key="reserve.output.item" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
          </th>
          <td class="w85">
            <span class="verAlignMid no_w">
              <html:multibox styleId="userId" name="man330Form" property="man330CsvOutField" value="1" />
              <label for="userId" class="text_base"><gsmsg:write key="cmn.user.id" /></label><wbr>
              <html:multibox styleId="userName" styleClass="ml10" name="man330Form" property="man330CsvOutField" value="2" />
              <label for="userName" class="text_base"><gsmsg:write key="cmn.name" /></label><wbr>
              <html:multibox styleId="userNameKn" styleClass="ml10" name="man330Form" property="man330CsvOutField" value="3" />
              <label for="userNameKn" class="text_base"><gsmsg:write key="cmn.name.kana" /></label><wbr>
              <html:multibox styleId="groupId" styleClass="ml10" name="man330Form" property="man330CsvOutField" value="4" />
              <label for="groupId" class="text_base"><gsmsg:write key="cmn.group.id" /></label><wbr>
              <html:multibox styleId="groupName" styleClass="ml10" name="man330Form" property="man330CsvOutField" value="5" />
              <label for="groupName" class="text_base"><gsmsg:write key="cmn.group.name" /></label><wbr>
              <html:multibox styleId="groupNameKn" styleClass="ml10" name="man330Form" property="man330CsvOutField" value="6" />
              <label for="groupNameKn" class="text_base"><gsmsg:write key="user.14" /></label>
            </span>
          </td>
        </tr>
      </table>
      <div class="txt_c">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onclick="buttonPush('export_exe');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
          <gsmsg:write key="cmn.export" />
        </button>
      </div>
    </td>
  </tr>
</table>
