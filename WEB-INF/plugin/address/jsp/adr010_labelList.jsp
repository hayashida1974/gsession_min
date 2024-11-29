<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<table class="mt10 w100 table-top">
  <tr>
    <th class="txt_l pt10 pb10">
    <!-- ラベル -->
      <gsmsg:write key="cmn.select.label" />
      <!-- ラベル編集ボタン -->
      <logic:notEqual name="adr010Form" property="adr010webmail" value="1" >
        <logic:equal name="adr010Form" property="adr010viewLabelBtn" value="1">
          <div class="txt_r flo_r">
            <span class="side_editBtn flo_r mr5 cl_fontMiddle classic-display side_confGear cursor_p" id="template_edit" onClick="setParams();buttonPush('setupLabel');">
              <gsmsg:write key="cmn.edit"/>
            </span>
            <span class="side_editBtn flo_r mr5 fs_18 original-display side_confGear cursor_p" id="template_edit" onClick="setParams();buttonPush('setupLabel');">
              <i class="icon-setting"></i>
            </span>
          </div>
        </logic:equal>
      </logic:notEqual>
    </th>
  </tr>
  <tr>
    <td class="p0">
      <logic:empty name="adr010Form" property="adr010CaegoryLabelList">
        <div>
          <gsmsg:write key="cmn.label.no" />
        </div>
      </logic:empty>
      <logic:notEmpty name="adr010Form" property="adr010CaegoryLabelList">
        <logic:iterate id="categoryLabelData" name="adr010Form" property="adr010CaegoryLabelList" indexId="idx">
          <%
            String category_head_id = "category_head_" + idx.toString();
            String category_data_id = "category_data_" + idx.toString();
          %>
          <div class="labelSelect_category-close ml0 cursor_p side_folder-focus" id="<%= category_head_id.toString() %>" onclick="changeDspCategory('<%= idx.toString() %>');">
            <span class="labelSelect_category"></span>
            <span class="classic-display pl20"><bean:write name="categoryLabelData" property="categoryName" /></span>
            <span class="original-display"><bean:write name="categoryLabelData" property="categoryName" /></span>
          </div>
          <div class="ml20 wp200 word_b-all" id="<%= category_data_id.toString() %>">
            <logic:notEmpty name="categoryLabelData" property="labelList">
              <logic:iterate id="labelData" name="categoryLabelData" property="labelList" indexId="idx2">
                <%
                  String label_id = "label_" + idx.toString() + "_" + idx2.toString();
                %>
                  <!-- チェックボックス -->
                  <div>
                  <span class="verAlignMid">
                    <html:multibox name="adr010Form" property="adr010searchLabel" styleId="<%= label_id.toString() %>">
                      <bean:write name="labelData" property="albSid" />
                    </html:multibox>
                    <label for="<%= label_id.toString() %>" class="ml5"><a href="#" onClick='labelCheck(<bean:write name="labelData" property="albSid" />)'><bean:write name="labelData" property="albName" /></a></label>
                  </span>
                  </div>
              </logic:iterate>
            </logic:notEmpty>
          </div>
        </logic:iterate>
      </logic:notEmpty>
    </td>
  </tr>
</table>