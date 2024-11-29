<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.co.sjts.util.StringUtilHtml" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/common/" prefix="common" %>
<%@ page import="jp.groupsession.v2.cmn.cmn999.Cmn999Form" %>
<% String help = jp.groupsession.v2.cmn.GSConst.HELPURL; %>

<!-- HEADER -->
<input type="hidden" name="" id="help_url" value="<bean:write name="<%= help %>" filter="false" />">

<% Cmn999Form cmn999Form = (Cmn999Form) request.getAttribute("cmn999Form"); %>

<common:toast toastId="cmn999InfoToast">
  <span class="js_toastMessage">
  </span>
</common:toast>

<common:toast toastId="toastNotice">
  <div class="display_flex">
    <div class="js_toastImage hp60">
      <img src="" class="userIcon_size-w52">
    </div>
    <div class="ml10">
      <span class="fw_bold js_toastTitle word_b-all"></span><br>
      <div class="mt10 js_toastContent of_h word_b-all mxhp60"></div>
    </div>
  </div>
</common:toast>

<script>
    <% if (cmn999Form != null && cmn999Form.isInfoToast()) { %>

        $(function() {
            <% String message = "";
               if (cmn999Form.getMessageList() != null) {
	               for (String msg : cmn999Form.getMessageList()) {
	                   message = message + StringUtilHtml.transToHTmlPlusAmparsant(msg) + "<br>";
	               }
               }
              %>
            <% if (message.length() > 0) {%>
	            $("#cmn999InfoToast .js_toastMessage").html("<%= message %>");
	            displayToast("cmn999InfoToast");
            <% } %>
        });
    <% } else { %>
            if (window.parent != null) {
              $(function() {
                    window.addEventListener("message", function(event) {
                        if (event.source !== window.parent) {
                            return false;
                        }
                        if (event.data.cmd !== 'cmn999InfoToast') {
                            return false;
                        }
                        $(".js_toastMessage").html(event.data.message);
                        displayToast(event.data.cmd);
                    });
                    window.parent.postMessage({
                        message: "isNeedToast",
                        targetOrigin:location.origin
                    });
                });
            }
    <% } %>

</script>