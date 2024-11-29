<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<table class="w100" cellpadding="0" cellspacing="0">
  <tr>
    <% String addressKbn = "1"; %>
    <logic:notEmpty name="usr040Form" property="usr040AtskList">
      <td class="w25 txt_t pr5">
        <table class="w100 table-top" cellpadding="0" cellspacing="0">
          <tr>
            <th>
              <logic:equal name="usr040Form" property="usr040webmailType" value="1">
                <div id="send_title">
                  <gsmsg:write key="anp.send.dest" />
                </div>
              </logic:equal>
              <logic:notEqual name="usr040Form" property="usr040webmailType" value="1">
                <div id="send_title">
                  <gsmsg:write key="cmn.from" />
                </div>
              </logic:notEqual>
            </th>
          </tr>

          <logic:iterate id="atskMdl" name="usr040Form" property="usr040AtskList">
            <bean:define id="mukoclass" value="" />
            <logic:equal name="atskMdl" property="usrUkoFlg" value="1">
              <bean:define id="mukoclass" value="mukoUser" />
            </logic:equal>

            <tr>
              <td class="no_w">
                <div class=" <%=mukoclass%>">
                  <bean:write name="atskMdl" property="usiSei" />
                  <span class="pl5">
                    <bean:write name="atskMdl" property="usiMei" />
                  </span>
                </div>
                <div>
                  <span>
                    &lt;
                    <logic:notEmpty name="atskMdl" property="usiMail1">
                      <bean:write name="atskMdl" property="usiMail1" />
                      <% addressKbn = "1"; %>
                    </logic:notEmpty>
                    <logic:notEmpty name="atskMdl" property="usiMail2">
                      <bean:write name="atskMdl" property="usiMail2" />
                      <% addressKbn = "2"; %>
                    </logic:notEmpty>
                    <logic:notEmpty name="atskMdl" property="usiMail3">
                      <bean:write name="atskMdl" property="usiMail3" />
                      <% addressKbn = "3"; %>
                    </logic:notEmpty>
                    &gt;
                  </span>
                  <span class="pl5">
                    [<a href="#!" onClick="return deleteSend(0, <bean:write name="atskMdl" property="usrSid" />, <%=addressKbn%>);">
                      <span class="cl_linkDef">
                        <gsmsg:write key="cmn.delete" />
                      </span>
                    </a>]
                  </span>
                 &nbsp;
                </div>
              </td>
            </tr>
          </logic:iterate>
        </table>
      </td>
    </logic:notEmpty>
    <logic:notEmpty name="usr040Form" property="usr040CcList">
      <td class="w25 txt_t pr5">

        <table class="table-top 100" cellpadding="0" cellspacing="0">

          <tr>
            <th>
              <div id="send_title">
                <gsmsg:write key="cmn.cc" />
              </div>

            </th>
          </tr>

          <logic:iterate id="ccMdl" name="usr040Form" property="usr040CcList">
            <bean:define id="mukoclass" value="" />
            <logic:equal name="ccMdl" property="usrUkoFlg" value="1">
              <bean:define id="mukoclass" value="mukoUser" />
            </logic:equal>

            <tr>
              <td class="no_w">
                <div class="<%=mukoclass%>">
                  <bean:write name="ccMdl" property="usiSei" />
                  <span class="pl5">
                    <bean:write name="ccMdl" property="usiMei" />
                  </span>
                </div>
                <div>
                  <span>
                    &lt;
                    <% int mailDspFlg = 0; %>
                    <logic:notEmpty name="ccMdl" property="usiMail1">
                      <logic:equal name="ccMdl" property="usiMail1Kf" value="0">
                        <bean:write name="ccMdl" property="usiMail1" />
                        <% mailDspFlg = 1; %>
                        <% addressKbn = "1"; %>
                      </logic:equal>
                    </logic:notEmpty>
                    <logic:notEmpty name="ccMdl" property="usiMail2">
                      <logic:equal name="ccMdl" property="usiMail2Kf" value="0">
                        <% if (mailDspFlg == 0) { %>
                        <bean:write name="ccMdl" property="usiMail2" />
                        <% mailDspFlg = 1; %>
                        <% addressKbn = "2"; %>
                        <% } %>
                      </logic:equal>
                    </logic:notEmpty>
                    <logic:notEmpty name="ccMdl" property="usiMail3">
                      <logic:equal name="ccMdl" property="usiMail3Kf" value="0">
                        <% if (mailDspFlg == 0) { %>
                        <bean:write name="ccMdl" property="usiMail3" />
                        <% addressKbn = "3"; %>
                        <% } %>
                      </logic:equal>
                    </logic:notEmpty>
                    &gt;
                  </span>
                  <span class="pl5">
                    [<a href="#!" onClick="return deleteSend(1, <bean:write name="ccMdl" property="usrSid" />, <%=addressKbn%>);">
                    <span class="cl_linkDef">
                      <gsmsg:write key="cmn.delete" />
                    </span>
                    </a>]
                  </span>
                </div>
              </td>
            </tr>
          </logic:iterate>
        </table>
      </td>
    </logic:notEmpty>

    <logic:notEmpty name="usr040Form" property="usr040BccList">
      <td class="w25 txt_t">

        <table class="w100 table-top" cellpadding="0" cellspacing="0">
          <tr>
            <th>
              <div id="send_title">
                <gsmsg:write key="cmn.bcc" />
              </div>
            </th>
          </tr>

          <logic:iterate id="bccMdl" name="usr040Form" property="usr040BccList">
            <bean:define id="mukoclass" value="" />
            <logic:equal name="bccMdl" property="usrUkoFlg" value="1">
              <bean:define id="mukoclass" value="mukoUser" />
            </logic:equal>

            <tr>
              <td class="no_w">
                <div class="<%=mukoclass%>">
                  <bean:write name="bccMdl" property="usiSei" />
                  <span class="pl5">
                    <bean:write name="bccMdl" property="usiMei" />
                  </span>
                </div>
                <div>
                  <span>
                    &lt;
                    <% int mailDspFlg = 0; %>
                    <logic:notEmpty name="bccMdl" property="usiMail1">
                      <logic:equal name="bccMdl" property="usiMail1Kf" value="0">
                        <bean:write name="bccMdl" property="usiMail1" />
                        <% mailDspFlg = 1; %>
                        <% addressKbn = "1"; %>
                      </logic:equal>
                    </logic:notEmpty>
                    <logic:notEmpty name="bccMdl" property="usiMail2">
                      <logic:equal name="bccMdl" property="usiMail2Kf" value="0">
                        <% if (mailDspFlg == 0) { %>
                        <bean:write name="bccMdl" property="usiMail2" />
                        <% mailDspFlg = 1; %>
                        <% addressKbn = "2"; %>
                        <% } %>
                      </logic:equal>
                    </logic:notEmpty>
                    <logic:notEmpty name="bccMdl" property="usiMail3">
                      <logic:equal name="bccMdl" property="usiMail3Kf" value="0">
                        <% if (mailDspFlg == 0) { %>
                        <bean:write name="bccMdl" property="usiMail3" />
                        <% addressKbn = "3"; %>
                        <% } %>
                      </logic:equal>
                    </logic:notEmpty>
                    &gt;
                  </span>
                  <span class="pl5">
                    [<a href="#!" onClick="return deleteSend(2, <bean:write name="bccMdl" property="usrSid" />, <%=addressKbn%>);">
                      <span class="cl_linkDef">
                        <gsmsg:write key="cmn.delete" />
                      </span>
                    </a>]
                  </span>
                </div>
              </td>
            </tr>
          </logic:iterate>
        </table>
      </td>
    </logic:notEmpty>
  </tr>
</table>
