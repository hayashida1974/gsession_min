<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<% String labelEditOk = request.getParameter("labelEditOk");%>
<table class="table-top">
  <tr>
    <th class="txt_l no_w">

      <!-- ラベル -->
      <span>
        <gsmsg:write key="cmn.select.label" />
      </span>
      <!--ラベル編集ボタン -->
      <logic:notEqual name="usr040Form" property="usr040webmail" value="1">
        <logic:equal name="usr040Form" property="usrLabelEditKbn" value="<%= labelEditOk %>">
          <div class="txt_r flo_r">
            <span class="side_editBtn flo_r mr5 cl_fontMiddle classic-display side_confGear cursor_p" onClick="buttonPush('labelEdit');" value="<gsmsg:write key="cmn.label"/>">
              <gsmsg:write key="cmn.edit" />
            </span>
            <span class="side_editBtn flo_r mr5 fs_18 original-display side_confGear" onClick="buttonPush('labelEdit');" value="<gsmsg:write key="cmn.label"/>">
              <i class="icon-setting cursor_p"></i>
            </span>
          </div>
        </logic:equal>
      </logic:notEqual>
    </th>
  </tr>

  <tr>
    <td class="p0">
      <logic:empty name="usr040Form" property="usr040CaegoryLabelList">
        <div>
          <gsmsg:write key="cmn.label.no" />
        </div>
      </logic:empty>

      <logic:notEmpty name="usr040Form" property="usr040CaegoryLabelList">
        <logic:iterate id="categoryLabelData" name="usr040Form" property="usr040CaegoryLabelList" indexId="idx">

          <%
           String category_head_id = "category_head_" + idx.toString();
           String category_data_id = "category_data_" + idx.toString();
          %>

          <div class="ml0 cursor_p side_folder-focus labelSelect_category-close" id="<%= category_head_id.toString() %>" onclick="changeDspCategory('<%= idx.toString() %>');">
            <span class="labelSelect_category"></span>
            <span class="classic-display pl20">
              <bean:write name="categoryLabelData" property="categoryName" />
            </span>
            <span class="original-display">
              <bean:write name="categoryLabelData" property="categoryName" />
            </span>
          </div>

          <div id="<%= category_data_id.toString() %>">

            <logic:notEmpty name="categoryLabelData" property="labelList">
              <logic:iterate id="labelData" name="categoryLabelData" property="labelList" indexId="idx2">

                <%
                  String label_id = "label_" + idx.toString() + "_" + idx2.toString();
                %>

                <div class="ml20">
                  <span class="verAlignMid">
                    <!-- チェックボックス -->
                    <html:multibox name="usr040Form" property="usr040labSid" styleId="<%= label_id.toString() %>">
                      <bean:write name="labelData" property="labSid" />
                    </html:multibox>
                    <label for="<%= label_id.toString() %>">
                      <a href="#" onClick='labelCheck(<bean:write name="labelData" property="labSid" />)'>
                        <bean:write name="labelData" property="labName" />
                      </a>
                    </label>
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