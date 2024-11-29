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

<% Integer cmdMode = Integer.parseInt(request.getParameter("cmdMode"));%>
<%
  String keyWordAnd    = String.valueOf(jp.groupsession.v2.adr.GSConstAddress.KEY_WORD_KBN_AND);
  String keyWordOr     = String.valueOf(jp.groupsession.v2.adr.GSConstAddress.KEY_WORD_KBN_OR);
  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
  String markOther    = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_OTHER);
  String markTel      = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_TEL);
  String markMail     = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_MAIL);
  String markWeb      = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_WEB);
  String markMeeting  = String.valueOf(jp.groupsession.v2.cmn.GSConst.CONTYP_MEETING);
  java.util.HashMap imgMapClassic = new java.util.HashMap();
  String msgTel = gsMsg.getMessage(request, "cmn.phone");
  String msgMail = gsMsg.getMessage(request, "cmn.mail");
  String msgMeeting = gsMsg.getMessage(request, "address.28");
  String msgOther = gsMsg.getMessage(request, "cmn.other");

  imgMapClassic.put(markTel, "<img src=\"../common/images/classic/icon_call.png\" alt=" + "\"" + msgTel + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put(markMail, "<img src=\"../address/images/classic/icon_mail.gif\" alt=" + "\"" + msgMail + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put(markWeb, "<img src=\"../address/images/classic/icon_web.gif\" alt=\"Web\" border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put(markMeeting, "<img src=\"../common/images/classic/icon_syorui.gif\" alt=" + "\"" + msgMeeting + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapClassic.put("none", "");

  java.util.HashMap imgMapOriginal = new java.util.HashMap();
  imgMapOriginal.put(markTel, "<img src=\"../common/images/original/icon_call.png\" alt=" + "\"" + msgTel + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put(markMail, "<img src=\"../common/images/original/icon_mail.png\" alt=" + "\"" + msgMail + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put(markWeb, "<img src=\"../address/images/original/icon_web.png\" alt=\"Web\" border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put(markMeeting, "<img src=\"../common/images/original/icon_siryo.png\" alt=" + "\"" + msgMeeting + "\"" + " border=\"0\" class=\"img_bottom\">");
  imgMapOriginal.put("none", "");

  java.util.HashMap imgTextMap = new java.util.HashMap();
  imgTextMap.put(markTel, msgTel);
  imgTextMap.put(markMail, msgMail);
  imgTextMap.put(markWeb, "Web");
  imgTextMap.put(markMeeting, msgMeeting);
  imgTextMap.put(markOther, msgOther);
  imgTextMap.put("none", "&nbsp;");

%>


<table class="table-top">
  <tr>
    <th class="js_tableTopCheck js_tableTopCheck-header">
      <logic:equal name="adr010Form" property="adr010AbleEdit" value="1">
        <input type="checkbox" name="allCheck" onClick="changeChk();">
      </logic:equal>
    </th>
    <!-- 検索メニュー　コンタクト履歴 -->
    <% if (cmdMode.intValue() == Adr010Const.CMDMODE_CONTACT) { %>
      <%-- 日時 --%>
      <th class="w15">
        <a href="#" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_DATE) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_DATE) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.date" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.date" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.date" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.date" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_DATE) %>">
            <gsmsg:write key="cmn.date" />
          </logic:notEqual>
        </a>
      </th>
      <%-- 氏名 --%>
      <th class="w15">
        <a href="#" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.name" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.name" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
            <gsmsg:write key="cmn.name" />
          </logic:notEqual>
        </a>
      </th>
      <%-- 会社名／拠点 --%>
      <th class="w15">
        <a href="#" class="cl_fontOutlineLink" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
            <gsmsg:write key="cmn.company.name" />
          </logic:notEqual>
        </a>
        ／
        <a href="#" class="cl_fontOutlineLink" onClick="sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="address.10" />▲</span>
              <span class="original-display"><gsmsg:write key="address.10" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="address.10" />▼</span>
              <span class="original-display"><gsmsg:write key="address.10" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>">
            <gsmsg:write key="address.10" />
          </logic:notEqual>
        </a>
      </th>
      <%-- 種別 --%>
      <th class="w15">
        <a href="#" onClick="sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_TYPE) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_TYPE) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.type" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.type" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.type" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.type" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_TYPE) %>">
            <gsmsg:write key="cmn.type" />
          </logic:notEqual>
        </a>
      </th>
      <%-- コンタクト履歴 タイトル --%>
      <th class="w25">
        <a href="#" onClick="sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_TITLE) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_TITLE) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="address.6" /><span class="ml5"><gsmsg:write key="cmn.title" />▲</span></span>
              <span class="original-display"><gsmsg:write key="address.6" /><span class="ml5"><gsmsg:write key="cmn.title" /><i class="icon-sort_up"></i></span></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="address.6" /><span class="ml5"><gsmsg:write key="cmn.title" />▼</span></span>
              <span class="original-display"><gsmsg:write key="address.6" /><span class="ml5"><gsmsg:write key="cmn.title" /><i class="icon-sort_down"></i></span></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_TITLE) %>">
            <gsmsg:write key="address.6" /><span class="ml5"><gsmsg:write key="cmn.title" /></span>
          </logic:notEqual>
        </a>
      </th>
      <%-- 登録者 --%>
      <th class="w15">
        <a href="#" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_ENTRYUSER) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_ENTRYUSER) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.registant" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.registant" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.registant" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.registant" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONTACT_ENTRYUSER) %>">
            <gsmsg:write key="cmn.registant" />
          </logic:notEqual>
        </a>
      </th>
      <!-- 検索メニュー プロジェクト-->
    <% } else if (cmdMode.intValue() == Adr010Const.CMDMODE_PROJECT) {%>
      <th class="w25">
        <a href="#" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.name" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.name" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
            <gsmsg:write key="cmn.name" />
          </logic:notEqual>
        </a>
      </th>
      <th class="w25">
        <a href="#" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
            <gsmsg:write key="cmn.company.name" />
          </logic:notEqual>
        </a>
      </th>
      <th class="w25">
        <gsmsg:write key="cmn.tel" />
      </th>
      <th class="w25">
        <gsmsg:write key="address.96" />
      </th>
    <!-- 検索メニュー 会社、氏名、担当者、詳細検索-->
    <% } else { %>
      <th class="w20">
        <a href="#" class="cl_fontOutlineLink" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.name" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.name" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.name" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_UNAME) %>">
            <gsmsg:write key="cmn.name" />
          </logic:notEqual>
        </a>
        ／
        <a href="#" class="cl_fontOutlineLink" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_POSITION) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_POSITION) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.post" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.post" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.post" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.post" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_POSITION) %>">
            <gsmsg:write key="cmn.post" />
          </logic:notEqual>
        </a>
      </th>
      <th class="w25">
        <a href="#" class="cl_fontOutlineLink" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▲</span>
              <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="cmn.company.name" />▼</span>
              <span class="original-display"><gsmsg:write key="cmn.company.name" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_CONAME) %>">
            <gsmsg:write key="cmn.company.name" />
          </logic:notEqual>
        </a>
        ／
        <%-- 拠点 --%>
        <a href="#" class="cl_fontOutlineLink" onClick="return sort(<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>);">
          <logic:equal name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>">
            <logic:equal name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="address.10" />▲</span>
              <span class="original-display"><gsmsg:write key="address.10" /><i class="icon-sort_up"></i></span>
            </logic:equal>
            <logic:notEqual name="adr010Form" property="adr010orderKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.ORDERKEY_ASC) %>" >
              <span class="classic-display"><gsmsg:write key="address.10" />▼</span>
              <span class="original-display"><gsmsg:write key="address.10" /><i class="icon-sort_down"></i></span>
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="adr010Form" property="adr010sortKey" value="<%= String.valueOf(jp.groupsession.v2.adr.adr010.Adr010Const.SORTKEY_COBASENAME) %>">
            <gsmsg:write key="address.10" />
          </logic:notEqual>
        </a>
      </th>
      <%-- 電話番号 --%>
      <th class="w22">
        <gsmsg:write key="cmn.tel" />
      </th>
      <%-- E-MAIL --%>
      <th class="w23">
        <gsmsg:write key="address.96" />
      </th>
      <%-- コンタクト履歴 --%>
      <th>
        <gsmsg:write key="address.6" />
      </th>
    <% }%>
  </tr>
  <logic:iterate id="detailMdl" name="adr010Form" property="detailList">
    <tr class="borderBlock-white">
      <td class="js_tableTopCheck cursor_p">
        <logic:equal name="adr010Form" property="adr010AbleEdit" value="1">
          <html:multibox name="adr010Form" property="adr010selectSid"><bean:write name="detailMdl"  property="adrSid" /></html:multibox>
        </logic:equal>
      </td>
      <!-- 検索メニュー　コンタクト履歴 -->
      <% if (cmdMode.intValue() == Adr010Const.CMDMODE_CONTACT) { %>
        <td class="txt_c">
          <bean:write name="detailMdl" property="contactDate" />
        </td>
        <td>
          <a href="#" onClick="setParams();return editAddress('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT) %>', '<bean:write name="detailMdl" property="adrSid" />')"><bean:write name="detailMdl" property="userName" /></a>
          <logic:notEmpty name="detailMdl" property="viewLabelName">
            <br><span class="baseLabel ml0"><bean:write name="detailMdl" property="viewLabelName" /></span>
          </logic:notEmpty>
        </td>
        <td>
          <a href="#" onClick="setParams();return viewCompany('<bean:write name="detailMdl" property="acoSid" />');"><bean:write name="detailMdl" property="companyName" /></a>
          <logic:notEmpty name="detailMdl" property="companyBaseName" >
            <div>
              <bean:write name="detailMdl" property="companyBaseName" />
            </div>
          </logic:notEmpty>
        </td>
        <td class="txt_c">
          <bean:define id="intImgMark" name="detailMdl" property="contactType" type="java.lang.Integer" />
          <% String imgMark = String.valueOf(intImgMark.intValue()); %>
          <% String key = "none";  if (imgMapClassic.containsKey(imgMark)) { key = imgMark; } %>
          <span class="classic-display"><%= (String) imgMapClassic.get(key) %></span>
          <span class="original-display"><%= (String) imgMapOriginal.get(key) %></span>
          <% String txtkey = "none";  if (imgTextMap.containsKey(imgMark)) { txtkey = imgMark; } %>
          <%= (String) imgTextMap.get(txtkey) %>
        </td>
        <td>
          <a href="#" onClick="setParams();return viewContact('<bean:write name="detailMdl" property="adrSid" />');"><bean:write name="detailMdl" property="contactTitle" /></a>
        </td>
        <td>
          <logic:notEqual name="detailMdl" property="contactEntryUsrJkbn" value="0">
            <del><bean:write name="detailMdl" property="contactEntryUser" /></del>
          </logic:notEqual>
          <logic:equal name="detailMdl" property="contactEntryUsrJkbn" value="0">
            <bean:define id="mukoUserClass" value="" />
            <logic:equal name="detailMdl" property="contactEntryUsrUkoFlg" value="1">
              <bean:define id="mukoUserClass" value="mukoUser" />
            </logic:equal>
            <span class="<%=mukoUserClass%>"><bean:write name="detailMdl" property="contactEntryUser" /></span>
          </logic:equal>
        </td>
      <% } else { %>
        <%-- 氏名／役職 --%>
        <td>
          <% if (cmdMode.intValue() != Adr010Const.CMDMODE_PROJECT) { %>
            <logic:notEmpty name="detailMdl" property="positionName" >
              <bean:write name="detailMdl" property="positionName" />
            </logic:notEmpty>
          <% } %>
          <a href="#" onClick="return editAddress('<%= String.valueOf(jp.groupsession.v2.adr.GSConstAddress.PROCMODE_EDIT) %>', '<bean:write name="detailMdl" property="adrSid" />')"><bean:write name="detailMdl" property="userName" /></a>
          <logic:notEmpty name="detailMdl" property="viewLabelName">
            <br><span class="baseLabel ml0"><bean:write name="detailMdl" property="viewLabelName" /></span>
          </logic:notEmpty>
        </td>
        <%-- 会社名／拠点 --%>
        <td>
          <a href="#" onClick="return viewCompany('<bean:write name="detailMdl" property="acoSid" />');"><bean:write name="detailMdl" property="companyName" /></a>
          <% if (cmdMode.intValue() != Adr010Const.CMDMODE_PROJECT) { %>
            <logic:notEmpty name="detailMdl" property="companyBaseName">
              <div>
                <bean:write name="detailMdl" property="companyBaseName" />
              </div>
            </logic:notEmpty>
          <% } %>
        </td>
        <%-- 電話番号 --%>
        <td>
          <logic:notEmpty name="detailMdl" property="tel1">
            <div>
              <bean:write name="detailMdl" property="tel1" />
              <logic:notEmpty name="detailMdl" property="telCmt1">
                <span class="ml5">(<bean:write name="detailMdl" property="telCmt1" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
          <logic:notEmpty name="detailMdl" property="tel2">
            <div>
              <bean:write name="detailMdl" property="tel2" />
              <logic:notEmpty name="detailMdl" property="telCmt2">
                <span class="ml5">(<bean:write name="detailMdl" property="telCmt2" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
          <logic:notEmpty name="detailMdl" property="tel3">
            <div>
              <bean:write name="detailMdl" property="tel3" />
              <logic:notEmpty name="detailMdl" property="telCmt3">
                <span class="ml5">(<bean:write name="detailMdl" property="telCmt3" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
        </td>
        <%-- E-MAIL --%>
        <td>
          <logic:notEmpty name="detailMdl" property="mail1">
            <div>
              <a href="mailto:<bean:write name="detailMdl" property="mail1" />"><bean:write name="detailMdl" property="mail1" /></a>
              <logic:notEmpty name="detailMdl" property="mailCmt1">
                <span class="ml5">(<bean:write name="detailMdl" property="mailCmt1" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
          <logic:notEmpty name="detailMdl" property="mail2">
            <div>
              <a href="mailto:<bean:write name="detailMdl" property="mail2" />"><bean:write name="detailMdl" property="mail2" /></a>
              <logic:notEmpty name="detailMdl" property="mailCmt2">
                <span class="ml5">(<bean:write name="detailMdl" property="mailCmt2" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
          <logic:notEmpty name="detailMdl" property="mail3">
            <div>
              <a href="mailto:<bean:write name="detailMdl" property="mail3" />"><bean:write name="detailMdl" property="mail3" /></a>
              <logic:notEmpty name="detailMdl" property="mailCmt3">
                <span class="ml5">(<bean:write name="detailMdl" property="mailCmt3" />)</span>
              </logic:notEmpty>
            </div>
          </logic:notEmpty>
        </td>
     <% if (cmdMode.intValue() != Adr010Const.CMDMODE_PROJECT) { %>
       <%-- コンタクト履歴 --%>
       <td class="txt_c no_w">
         <button type="button" class="baseBtn" onClick="viewContact('<bean:write name="detailMdl" property="adrSid" />')">
           <gsmsg:write key="address.6" />
         </button>
       </td>
     <% } %>
  <% } %>
  </tr>
  </logic:iterate>
  </table>