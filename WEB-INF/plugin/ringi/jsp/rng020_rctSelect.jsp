<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<% int cmdmode_add = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_ADD; %>
<% int cmdmode_edit = jp.groupsession.v2.rng.RngConst.RNG_CMDMODE_EDIT; %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ringi" prefix="ringi" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

    <div class="keiroTemplateDialog_tab" >
      <div class="keiroTemplateDialog_space"></div>
      <div class="keiroTemplateDialog_share"><gsmsg:write key="cmn.share"/></div>
      <div class="keiroTemplateDialog_person"><gsmsg:write key="cmn.individual"/></div>
    </div>
    <div class="keiroTemplateDialog_tabbody">
      <div name="keirotemplate_share">
        <ringi:rng100_list name="rng230Form" property="rng100keiroTemplateList" />
      </div>
      <div name="keirotemplate_person">
        <ringi:rng100_list name="rng100Form" property="rng100keiroTemplateList" />
      </div>
    </div>

