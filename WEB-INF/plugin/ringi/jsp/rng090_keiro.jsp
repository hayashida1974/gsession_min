<%@page import="jp.groupsession.v2.rng.RngConst"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<bean:define id="rngTemplateMode" name="rng090Form" property="rngTemplateMode" />
<% int tmodeAll = RngConst.RNG_TEMPLATE_ALL; %>
<% int tmodeShare = RngConst.RNG_TEMPLATE_SHARE; %>
<% int tmodePrivate = RngConst.RNG_TEMPLATE_PRIVATE; %>
<% int rtMode = ((Integer) rngTemplateMode).intValue(); %>

<logic:equal name="rng090Form" property="rng090useKeiroTemplate" value="1">
<% if (rtMode == tmodePrivate) { %>
  <ringi:rng110_keiro name="rng090Form" property="rng090keiro"  keiroShareRange="<%=RngConst.RNG_TEMPLATE_PRIVATE %>" />
<%} %>
<% if (rtMode == tmodeShare) { %>
  <ringi:rng110_keiro name="rng090Form" property="rng090keiro"  keiroShareRange="<%=RngConst.RNG_TEMPLATE_SHARE %>" />
<%} %>
</logic:equal>
<logic:equal name="rng090Form" property="rng090useKeiroTemplate" value="0">
  <logic:notEqual name="rng090Form" property="rng090KeiroTemplateSid" value="0">
    <div class="keiro_template"><gsmsg:write key="rng.25"/>:<bean:write name="rng090Form" property="rng090KeiroTemplateName" /></div>
    <ringi:rng110_keiro name="rng090Form" property="rng090keiro"  kakuninMode="kakunin"/>
  </logic:notEqual>
</logic:equal>
