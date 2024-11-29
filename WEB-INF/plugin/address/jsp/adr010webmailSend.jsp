<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.adr.GSConstAddress" %>
<%@ page import="jp.groupsession.v2.adr.adr010.Adr010Const" %>

<table class="w100">
  <tr>
    <td class="w30 txt_l txt_t">
      <% String addressKbn = "1"; %>
      <table class="table-top w100">
        <logic:notEmpty name="adr010Form" property="adr010AtskList">
          <tr>
            <th class="w100">
              <gsmsg:write key="cmn.from" />
            </th>
          </tr>
          <logic:iterate id="atskMdl" name="adr010Form" property="adr010AtskList" indexId="idx">
            <tr>
              <td class="no_w">
                <bean:write name="atskMdl" property="adrSei" /><span class="ml5"><bean:write name="atskMdl" property="adrMei" /></span>
                <div>
                  &lt;
                  <logic:notEmpty name="atskMdl" property="adrMail1">
                    <bean:write name="atskMdl" property="adrMail1" />
                    <% addressKbn = "1"; %>
                  </logic:notEmpty>
                  <logic:notEmpty name="atskMdl" property="adrMail2">
                    <bean:write name="atskMdl" property="adrMail2" />
                    <% addressKbn = "2"; %>
                  </logic:notEmpty>
                  <logic:notEmpty name="atskMdl" property="adrMail3">
                    <bean:write name="atskMdl" property="adrMail3" />
                    <% addressKbn = "3"; %>
                  </logic:notEmpty>
                  &gt;
                  <span class="ml10">[<a href="#!" onClick="return deleteSend(0, <bean:write name="atskMdl" property="adrSid" />, <%= addressKbn %>);"><gsmsg:write key="cmn.delete" /></a>]</span>
                </div>
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>
    </td>
    <logic:notEmpty name="adr010Form" property="adr010CcList">
      <td class="w30 txt_l txt_t">
        <table class="w100 table-top">
          <tr>
            <th class="w100">
              <gsmsg:write key="cmn.cc" />
            </th>
          </tr>
          <logic:iterate id="ccMdl" name="adr010Form" property="adr010CcList" indexId="idx2">
            <tr>
              <td class="no_w">
                <bean:write name="ccMdl" property="adrSei" /><span class="ml5"><bean:write name="ccMdl" property="adrMei" /></span>
                <div>
                  &lt;
                  <logic:notEmpty name="ccMdl" property="adrMail1">
                    <bean:write name="ccMdl" property="adrMail1" />
                    <% addressKbn = "1"; %>
                  </logic:notEmpty>
                  <logic:notEmpty name="ccMdl" property="adrMail2">
                    <bean:write name="ccMdl" property="adrMail2" />
                    <% addressKbn = "2"; %>
                  </logic:notEmpty>
                  <logic:notEmpty name="ccMdl" property="adrMail3">
                    <bean:write name="ccMdl" property="adrMail3" />
                    <% addressKbn = "3"; %>
                  </logic:notEmpty>
                  &gt;
                  <span class="ml10">[<a href="#!" onClick="return deleteSend(1, <bean:write name="ccMdl" property="adrSid" />, <%= addressKbn %>);"><gsmsg:write key="cmn.delete" /></a>]</span>
                </div>
              </td>
            </tr>
          </logic:iterate>
        </table>
      </td>
    </logic:notEmpty>
    <logic:notEmpty name="adr010Form" property="adr010BccList">
      <td class="txt_l w30 txt_t">
        <table class="w100 table-top">
          <tr>
            <th class="w100">
              <gsmsg:write key="cmn.bcc" />
            </th>
          </tr>
          <logic:iterate id="bccMdl" name="adr010Form" property="adr010BccList" indexId="idx3">
            <tr>
              <td class="no_w">
                <bean:write name="bccMdl" property="adrSei" /><span class="ml5"><bean:write name="bccMdl" property="adrMei" /></span>
                <div>
                  &lt;
                  <logic:notEmpty name="bccMdl" property="adrMail1">
                    <bean:write name="bccMdl" property="adrMail1" />
                    <% addressKbn = "1"; %>
                  </logic:notEmpty>
                  <logic:notEmpty name="bccMdl" property="adrMail2">
                    <bean:write name="bccMdl" property="adrMail2" />
                    <% addressKbn = "2"; %>
                  </logic:notEmpty>
                  <logic:notEmpty name="bccMdl" property="adrMail3">
                    <bean:write name="bccMdl" property="adrMail3" />
                    <% addressKbn = "3"; %>
                  </logic:notEmpty>
                  &gt;
                  <span class="ml10">[<a href="#!" onClick="return deleteSend(2, <bean:write name="bccMdl" property="adrSid" />, <%= addressKbn %>);"><gsmsg:write key="cmn.delete" /></a>]</span>
                </div>
              </td>
            </tr>
          </logic:iterate>
        </table>
      </td>
    </logic:notEmpty>
  </tr>
</table>