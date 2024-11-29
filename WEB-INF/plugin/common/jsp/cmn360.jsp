<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<bean:define id="property" name="cmn360Form" property="targetSelector" type="String"/>

<logic:notEqual name="cmn360Form" property="selectorDispCmd" value="filter_post">
  <logic:notEqual name="cmn360Form" property="selectorDispCmd" value="filter_label">
    <ui:multiselector_draw  name="cmn360Form" property="<%=property %>" selector="selector" />
  </logic:notEqual>
</logic:notEqual>
<ui:multiselector_search  name="cmn360Form" property="<%=property %>" selector="selector" />
