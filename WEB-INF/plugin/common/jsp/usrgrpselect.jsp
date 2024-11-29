<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ page import="jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel"%>
<%@ page import="java.util.HashMap"%>

<script src="../common/js/usrgrpselect.js?<%= GSConst.VERSION_PARAM %>"></script>

<%
  String thisFormName = request.getParameter("thisFormName");
  pageContext.setAttribute("thisFormName", thisFormName);
  pageContext.setAttribute("thisForm", request.getAttribute(request.getParameter("thisFormName")));
  String id = request.getParameter("id");
%>
<bean:define id="thisSelect" name="thisForm" property="<%=id %>" type="UserGroupSelectModel"></bean:define>
<%
  String[] selectListKeys = thisSelect.getKeys();
  HashMap<String, String> dspListTitleMap = thisSelect.getSelectedListName();
  pageContext.setAttribute("selectListKeys", selectListKeys);
%>

<%-- usrgroupselect初期化処理 --%>
<script>
<!--
  $(function() {
    $('table[name=\"<%=id %>\"]').usrgrpselect({cmd:'init',key:[
        <logic:iterate id = "entry" name="thisSelect" property="keys">
          "<bean:write name="entry" />",
        </logic:iterate>
      ],
      titles:[
        <logic:iterate id = "entry" name="thisSelect" property="keys">
          "<%=dspListTitleMap.get(entry)%>",
        </logic:iterate>
      ],
      scroll:<bean:write name="thisSelect" property="scrollY"/>,
      banUsrSid:[
        <logic:iterate id = "banUsrSid" name="thisSelect" property="banUsrSid">
          "<bean:write name="banUsrSid" />",
        </logic:iterate>
      ],
    });
  });
-->
</script>

<table class="userSelectBlock" name="<%=id %>" style="<bean:write name="thisSelect" property="styleDisplay" />">
  <tr>
    <td class="userSelect_fromTo">
    </td>
    <td class="userSelect_center"></td>
    <td class="userSelect_fromTo lh_normal" rowspan="<%=2 * selectListKeys.length + 1%>">
      <div>
        <button name="<%=id %>.group.all" class="baseBtn userSelect_btn mt0 js_btn_group_n1" type="button" value="<gsmsg:write key="cmn.sel.all.group" />">
          <gsmsg:write key="cmn.sel.all.group" />
        </button>
        <div class="verAlignMid">
          <html:select styleClass="wp200" property="<%=id + \".group.selectedSingle\" %>">
            <logic:notEmpty name="thisForm" property="<%=id + \".group.list\" %>" >
              <logic:iterate id="gpBean" name="thisForm" property="<%=id + \".group.list\" %>">
                <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                <% boolean mygrpFlg = gpValue.indexOf("M") == -1; %>
                <% if (mygrpFlg) { %>
                  <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                <% } else { %>
                  <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                <% } %>
              </logic:iterate>
            </logic:notEmpty>
          </html:select>
          <button name="<%=id %>.group.select" class="iconBtn-border ml5" type="button">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
            <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
          </button>
        </div>
      </div>
      <div>
        <html:select property="<%=id + \".targetNoSelected\" %>" size="10" styleClass="w100 hp220 mt5 noSelected pr0" multiple="true">
         <logic:iterate id="rightUser" name="thisForm" property="<%=id + \".noselectList\" %>" >
           <logic:equal value="1" name="rightUser" property="usrUkoFlg">
            <option value='<bean:write name="rightUser" property="value"/>' class="mukoUserOption"><bean:write name="rightUser" property="label"/></option>
           </logic:equal>
           <logic:equal value="0" name="rightUser" property="usrUkoFlg">
            <option value='<bean:write name="rightUser" property="value"/>'><bean:write name="rightUser" property="label"/></option>
           </logic:equal>
          </logic:iterate>
        </html:select>
      </div>
    </td>
  </tr>
  <%
    int useCnt = 0;
    for (String key : selectListKeys) {
        if (thisSelect.getSelectedListUse().get(key).equals("true")) {
            useCnt++;
        }
    }
    int selectSize = 3;
    if (useCnt == 1) {
        selectSize = 10;
    }
  %>
  <logic:equal name="thisSelect" property="<%=\"selectedListUse.\" + selectListKeys[0]%>" value="true">
    <tr name="<%=id%>.selected(<%=selectListKeys[0]%>).head" class="border_top_none border_bottom_none">
  </logic:equal>
  <logic:notEqual name="thisSelect" property="<%=\"selectedListUse.\" + selectListKeys[0]%>" value="true">
    <tr name="<%=id%>.selected(<%=selectListKeys[0]%>).head" class="display_n border_top_none border_bottom_none"">
  </logic:notEqual>
    <td class="userSelect_fromTo">
      <span class="userGroup_title">
        <%=dspListTitleMap.get(selectListKeys[0]) %>
      </span>
    </td>
    <td class="border_right_none border_bottom_none"></td>
  </tr>
  <logic:equal name="thisSelect" property="<%=\"selectedListUse.\" + selectListKeys[0]%>" value="true">
    <tr name="<%=id%>.selected(<%=selectListKeys[0]%>)" >
  </logic:equal>
  <logic:notEqual name="thisSelect" property="<%=\"selectedListUse.\" + selectListKeys[0]%>" value="true">
    <tr name="<%=id%>.selected(<%=selectListKeys[0]%>)" class="display_n">
  </logic:notEqual>
    <td class="userSelect_fromTo txt_m">
      <logic:iterate id="leftUser" name="thisForm" property="<%=id + \".selectedList(\" + selectListKeys[0] + \")\" %>" type="UsrLabelValueBean">
        <html:hidden property="<%=id + \".selected(\" + selectListKeys[0] + \")\" %>" value="<%=leftUser.getValue() %>"/>
      </logic:iterate>
      <html:select property="<%=id + \".targetSelected\" %>" size="<%= String.valueOf(selectSize) %>" styleClass="w100 hp75 selected pr0" multiple="true" >
        <logic:iterate id="leftUser" name="thisForm" property="<%=id + \".selectedList(\" + selectListKeys[0] + \")\" %>" >
          <logic:equal value="1" name="leftUser" property="usrUkoFlg">
            <option value='<bean:write name="leftUser" property="value"/>' class="mukoUserOption"><bean:write name="leftUser" property="label"/></option>
          </logic:equal>
          <logic:equal value="0" name="leftUser" property="usrUkoFlg">
            <option value='<bean:write name="leftUser" property="value"/>'><bean:write name="leftUser" property="label"/></option>
          </logic:equal>
        </logic:iterate>
      </html:select>
    </td>
    <td class="selectForm_moveArea userSelect_center">
      <div>
        <button type="button" class="baseBtn mb20 js_btn_base1add" value="<gsmsg:write key="cmn.add" />" name="adduserBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
          <i class="icon-left"></i>
          <gsmsg:write key="cmn.add" />
        </button>
      </div>
      <div>
        <button type="button" class="baseBtn js_btn_base1del" value="<gsmsg:write key="cmn.delete" />" name="deluserBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
          <i class="icon-right"></i>
          <gsmsg:write key="cmn.delete" />
        </button>
      </div>
    </td>
  </tr>
  <% if (selectListKeys.length > 1) {%>
    <logic:equal name="thisSelect" property="<%=\"selectedListUse.\" + selectListKeys[1]%>" value="true">
      <tr name="<%=id%>.selected(<%=selectListKeys[1]%>).head" class="border_top_none border_bottom_none">
    </logic:equal>
    <logic:notEqual name="thisSelect" property="<%=\"selectedListUse.\" + selectListKeys[1]%>" value="true">
      <tr name="<%=id%>.selected(<%=selectListKeys[1]%>).head" class="display_n border_top_none border_bottom_none">
    </logic:notEqual>
      <td class="userSelect_fromTo">
        <span class="userGroup_title hp35">
          <%=dspListTitleMap.get(selectListKeys[1]) %>
        </span>
      </td>
      <td class="border_right_none border_bottom_none"></td>
    </tr>
    <logic:equal name="thisSelect" property="<%=\"selectedListUse.\" + selectListKeys[1]%>" value="true">
      <tr name="<%=id%>.selected(<%=selectListKeys[1]%>)" >
    </logic:equal>
    <logic:notEqual name="thisSelect" property="<%=\"selectedListUse.\" + selectListKeys[1]%>" value="true">
      <tr name="<%=id%>.selected(<%=selectListKeys[1]%>)" class="display_n">
    </logic:notEqual>
      <td class="userSelect_fromTo txt_m">
        <logic:iterate id="leftUser" name="thisForm" property="<%=id + \".selectedList(\" + selectListKeys[1] + \")\" %>" type="UsrLabelValueBean">
          <html:hidden property="<%=id + \".selected(\" + selectListKeys[1] + \")\" %>" value="<%=leftUser.getValue() %>"/>
        </logic:iterate>
        <html:select property="<%=id + \".targetSelected\" %>" size="<%= String.valueOf(selectSize) %>" styleClass="w100 js_editArea selected pr0" multiple="true" >
          <logic:iterate id="user" name="thisForm" property="<%=id + \".selectedList(\" + selectListKeys[1] + \")\" %>" >
            <logic:equal value="1" name="user" property="usrUkoFlg">
              <option value='<bean:write name="user" property="value"/>' class="mukoUserOption"><bean:write name="user" property="label"/></option>
            </logic:equal>
            <logic:equal value="0" name="user" property="usrUkoFlg">
              <option value='<bean:write name="user" property="value"/>'><bean:write name="user" property="label"/></option>
            </logic:equal>
          </logic:iterate>
        </html:select>
      </td>
      <td class="selectForm_moveArea userSelect_center">
        <div>
          <button type="button" class="baseBtn mb20 js_btn_base1add" value="<gsmsg:write key="cmn.add" />" name="adduserBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
            <i class="icon-left"></i>
            <gsmsg:write key="cmn.add" />
          </button>
        </div>
        <div>
          <button type="button" class="baseBtn js_btn_base1del" value="<gsmsg:write key="cmn.delete" />" name="deluserBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
            <i class="icon-right"></i>
            <gsmsg:write key="cmn.delete" />
          </button>
        </div>
      </td>
    </tr>
  <% } %>
</table>
