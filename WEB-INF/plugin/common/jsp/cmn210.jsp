<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-groupTree.tld" prefix="groupTree" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<!DOCTYPE html>
<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta name="format-detection" content="telephone=no">
  <title>GROUPSESSION <gsmsg:write key="cmn.grouplist" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmn210.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/treeview.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
</head>
<body>
<html:form action="/common/cmn210">
<bean:parameter id="cmn210tree" name="cmn210tree" value=""/>
<input type="hidden" name="cmn210tree" value="<bean:write name="cmn210tree" />">
<div class="pageTitle w90 mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../common/images/classic/header_group.png" alt="<gsmsg:write key="cmn.group" />">
      <img class="header_pluginImg" src="../common/images/original/icon_group_32.png" alt="<gsmsg:write key="cmn.group" />">
    </li>
    <li><gsmsg:write key="wml.wml040.04" /></li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div id="sidetree" class="mt0 bor1 w90 mrl_auto bgC_body">
  <div class="ml20">
    <div id="sidetreecontrol" >
      <a href="#!"><gsmsg:write key="cmn.all.close" /></a> | <a href="#!"><gsmsg:write key="cmn.all.open" /></a>
    </div>
    <ul id="tree">
      <!-- マイグループ -->
      <logic:notEmpty name="cmn210Form" property="myGroupList">
        <logic:iterate id="mgl" name="cmn210Form" property="myGroupList" indexId="idx">
            <li>
              <a href="<bean:write name="cmn210Form" property="scriptStr" />.value=('<bean:write name="mgl" property="value" />');<bean:write name="cmn210Form" property="scriptStr2" />">
                <bean:write name="mgl" property="label" />
              </a>
            </li>
        </logic:iterate>
      </logic:notEmpty>
      <!-- 一般ユーザ  -->
      <% int prevVal = 0; %>
      <% int nextVal = 0; %>
      <logic:iterate id="mdl" name="cmn210Form" property="groupList" indexId="idx">
        <logic:iterate id="disabled" name="cmn210Form" property="groupDisabledKbnList" offset="idx" length="1">
          <bean:define id="nowval" name="mdl" property="classLevel" />
          <%-- 階層チェック：前のグループ(prevVal)と次のグループ(nextVal)を比較 --%>
          <% nextVal = ((Integer) nowval).intValue(); %>
          <% if (idx > 0) { %>
          <%    if(prevVal < nextVal) {%>
          <%-- 次のグループ(nextVal)が子階層の場合 --%>
                  <ul>
          <%    } else if(prevVal == nextVal) {%>
          <%-- 次のグループ(nextVal)と同じ階層の場合 --%>
                  </li>
          <%    } else {%>
          <%-- 次のグループ(nextVal)が全く別のグループ階層になった場合（前のグループ(prevVal)までで階層が終了） --%>
                  </li>
          <%      for(int i = 0 ;i < (prevVal - nextVal) ;i++){%>
                      </ul>
                    </li>
          <%      }%>
          <%    }%>
            <li><!--
       --><% } else { %>
            <li><!--
       --><%    }%><!--
         --><logic:equal name="disabled" value="1"><!--
           --><bean:write name="mdl" property="groupName" /><!--
         --></logic:equal><!--
         --><logic:notEqual name="disabled" value="1"><!--
           --><a href="<bean:write name="cmn210Form" property="scriptStr" />.value=('<bean:write name="mdl" property="groupSid" />');<bean:write name="cmn210Form" property="scriptStr2" />"><!--
             --><bean:write name="mdl" property="groupName" /><!--
           --></a><!--
         --></logic:notEqual>
          <bean:define id="pastval" name="mdl" property="classLevel" />
          <% prevVal = ((Integer) pastval).intValue(); %>
        </logic:iterate>
      </logic:iterate>
    </li>
    <% if (prevVal > 1) { %>
      <% for(int i = 0 ;i < (prevVal - 1) ;i++){%>
            </ul>
          </li>
      <% }%>
    <% }%>
    </ul>
  </div>
</div>
</html:form>
</body>
</html:html>