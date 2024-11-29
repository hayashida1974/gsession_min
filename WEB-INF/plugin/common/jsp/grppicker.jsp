<%@page import="jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/gsform" prefix="gsform" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.formmodel.GroupComboModel"%>
<!-- 再描画用リクエストパラメータ -->
<bean:define id="grpsel" name="GroupSelDialogForm" property="grpsel" type="GroupComboModel" />
<gsform:grpsel_dialogparams bean="<%=grpsel %>" inDialog="in"></gsform:grpsel_dialogparams>
<div>
  <logic:notEqual value="<%=String.valueOf(UserGroupSelectModel.FLG_MULTI_OFF) %>"  name="grpsel" property="multiFlg">
    <gsform:grpselect name="GroupSelDialogForm" property="grpsel" onchange="$(this).parent().parent().grppicker();" size="15"></gsform:grpselect>
  </logic:notEqual>
</div>