<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>


<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="bbs.9" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bulletin/js/bbs170.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bulletin/js/bbsMemPopUp.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body class="body_03" onunload="windowClose();">

  <html:form action="/bulletin/bbs170">
    <input type="hidden" name="CMD" value="searchThre">
    <html:hidden name="bbs170Form" property="s_key" />
    <html:hidden name="bbs170Form" property="bbs010page1" />
    <html:hidden name="bbs170Form" property="bbs010forumSid" />
    <html:hidden name="bbs170Form" property="bbs170backForumSid" />
    <html:hidden name="bbs170Form" property="bbs170allForumFlg" />
    <html:hidden name="bbs170Form" property="threadSid" />
    <html:hidden name="bbs170Form" property="bbs170sortKey" />
    <html:hidden name="bbs170Form" property="bbs170orderKey" />


    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <!-- BODY -->

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../bulletin/images/classic/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
          <img class="header_pluginImg" src="../bulletin/images/original/header_bulletin.png" alt="<gsmsg:write key="cmn.bulletin" />">
        </li>
        <li>
          <gsmsg:write key="cmn.bulletin" />
        </li>
        <li class="pageTitle_subFont"> 掲示予定一覧 </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="backToThreadList();buttonPush('moveThreadList');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper">

      <div class="txt_l mt5 mb10">
        <logic:notEmpty name="bbs170Form" property="bbs170forumList">
          <html:select property="bbs010forumSid" onchange="changeForum();" styleClass="wp250">
            <html:optionsCollection name="bbs170Form" property="bbs170forumList" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </div>

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <%-- single forum START --%>
      <%-- scheduled post doesnot exist --%>
      <logic:empty name="bbs170Form" property="bbs170forumThreadMap">
        <table class="w100">

          <tr>

            <td class="w100">

              <table class="w100">
                <tr>

                  <%-- forum title START --%>
                  <td class="w100 txt_l">
                    <table class="w100">
                      <tr>

                        <%-- forum icon --%>
                        <td class="no_w">
                          <table>
                            <tr>
                              <logic:equal name="bbs170Form" property="bbs170BinSid" value="0">
                                <td>
                                  <img class="wp30hp30 classic-display" src="../bulletin/images/classic/icon_forum.gif" alt="<gsmsg:write key="bbs.3" />">
                                  <img class="wp30hp30 original-display" src="../bulletin/images/original/icon_forum_32.png" alt="<gsmsg:write key="bbs.3" />">
                                </td>
                              </logic:equal>
                              <logic:notEqual name="bbs170Form" property="bbs170BinSid" value="0">
                                <td>
                                  <img class="wp30hp30" src="../bulletin/bbs170.do?CMD=getImageFile&bbs010BinSid=<bean:write name="bbs170Form" property="bbs170BinSid" />&bbs010forumSid=<bean:write name="bbs170Form" property="bbs010forumSid" />" alt="<gsmsg:write key="bbs.3" />">
                                </td>
                              </logic:notEqual>
                            </tr>
                          </table>
                        </td>

                        <%-- forum name --%>
                        <td class="w100 txt_l no_w">
                          <bean:write name="bbs170Form" property="bbs170forumName" />
                        </td>
                      </tr>
                    </table>
                  </td>
                  <%-- forum title END --%>

                </tr>
              </table>

              <table class="table-top w100">

                <%-- list header START --%>
                <tr>
                  <th class="w35 no_w">
                    <logic:equal name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_THRED) %>">
                      <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_THRED) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>);">
                          <span>
                            <gsmsg:write key="bbs.2" />
                            <span class="classic-display">▲</span>
                            <span class="original-display">
                              <i class="icon-sort_up"></i>
                            </span>
                        </a>
                      </logic:equal>
                      <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_THRED) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                          <span>
                            <gsmsg:write key="bbs.2" />
                            <span class="classic-display">▼</span>
                            <span class="original-display">
                              <i class="icon-sort_down"></i>
                            </span>
                          </span>
                        </a>
                      </logic:equal>
                    </logic:equal>
                    <logic:notEqual name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_THRED) %>">
                      <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_THRED) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                        <span>
                          <gsmsg:write key="bbs.2" />
                        </span>
                      </a>
                    </logic:notEqual>
                  </th>

                  <th class="w15 no_w">
                    <logic:equal name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_USER) %>">
                      <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_USER) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>);">
                          <span>
                            <gsmsg:write key="cmn.contributor" />
                            <span class="classic-display">▲</span>
                            <span class="original-display">
                              <i class="icon-sort_up"></i>
                            </span>
                        </a>
                      </logic:equal>
                      <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_USER) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                          <span>
                            <gsmsg:write key="cmn.contributor" />
                            <span class="classic-display">▼</span>
                            <span class="original-display">
                              <i class="icon-sort_down"></i>
                            </span>
                          </span>
                        </a>
                      </logic:equal>
                    </logic:equal>
                    <logic:notEqual name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_USER) %>">
                      <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_USER) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                        <span>
                          <gsmsg:write key="cmn.contributor" />
                        </span>
                      </a>
                    </logic:notEqual>
                  </th>

                  <th class="w20 no_w">
                    <logic:equal name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_FR) %>">
                      <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_FR) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>);">
                          <span>
                            <gsmsg:write key="bbs.bbs070.5" />
                            <span class="classic-display">▼</span>
                            <span class="original-display">
                              <i class="icon-sort_down"></i>
                            </span>
                          </span>
                        </a>
                      </logic:equal>
                      <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_FR) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                          <span>
                            <gsmsg:write key="bbs.bbs070.5" />
                            <span class="classic-display">▲</span>
                            <span class="original-display">
                              <i class="icon-sort_up"></i>
                            </span>
                        </a>
                      </logic:equal>
                    </logic:equal>
                    <logic:notEqual name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_FR) %>">
                      <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_FR) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                        <span>
                          <gsmsg:write key="bbs.bbs070.5" />
                        </span>
                      </a>
                    </logic:notEqual>
                  </th>

                  <th class="w20 no_w">
                    <logic:equal name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_TO) %>">
                      <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_TO) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>);">
                          <span>
                            <gsmsg:write key="bbs.bbs070.6" />
                            <span class="classic-display">▼</span>
                            <span class="original-display">
                              <i class="icon-sort_down"></i>
                            </span>
                          </span>
                        </a>
                      </logic:equal>
                      <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_TO) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                          <span>
                            <gsmsg:write key="bbs.bbs070.6" />
                            <span class="classic-display">▲</span>
                            <span class="original-display">
                              <i class="icon-sort_up"></i>
                            </span>
                        </a>
                      </logic:equal>
                    </logic:equal>
                    <logic:notEqual name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_TO) %>">
                      <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_TO) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                        <span>
                          <gsmsg:write key="bbs.bbs070.6" />
                        </span>
                      </a>
                    </logic:notEqual>
                  </th>

                  <th class="w5 no_w">
                    <logic:equal name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SIZE) %>">
                      <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SIZE) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>);">
                          <span>
                            <gsmsg:write key="cmn.size" />
                            <span class="classic-display">▲</span>
                            <span class="original-display">
                              <i class="icon-sort_up"></i>
                            </span>
                        </a>
                      </logic:equal>
                      <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SIZE) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                          <span>
                            <gsmsg:write key="cmn.size" />
                            <span class="classic-display">▼</span>
                            <span class="original-display">
                              <i class="icon-sort_down"></i>
                            </span>
                          </span>
                        </a>
                      </logic:equal>
                    </logic:equal>
                    <logic:notEqual name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SIZE) %>">
                      <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SIZE) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                        <span>
                          <gsmsg:write key="cmn.size" />
                        </span>
                      </a>
                    </logic:notEqual>
                  </th>

                  <th class="w5 no_w">
                    <span>
                      <gsmsg:write key="bbs.bbs080.1" />
                    </span>
                  </th>
                </tr>
                <%-- list header END --%>
              </table>
            </td>
          </tr>
        </table>

      </logic:empty>

      <div class="mb10">
        <%-- forum warn START --%>
        <logic:notEqual name="bbs170Form" property="bbs010forumSid" value="-1">
          <logic:greaterThan name="bbs170Form" property="bbs170forumWarnDisk" value="0">
            <div class="bor1 p5 txt_c">
              <img class="classic-display wp20hp20" src="../common/images/classic/icon_warn_2.gif" alt="<gsmsg:write key="cmn.warning" />">
              <img class="original-display wp20hp20" src="../common/images/original/icon_warn_63.png" alt="<gsmsg:write key="cmn.warning" />">
              <span class="cl_fontWarn fw_b fs_14">
                <gsmsg:write key="wml.251" />
                <bean:write name="bbs170Form" property="bbs170forumWarnDisk" />
                <gsmsg:write key="wml.252" />
              </span>
            </div>
          </logic:greaterThan>
        </logic:notEqual>
        <%-- forum warn END --%>
      </div>

      <%-- scheduled post exists --%>
      <logic:notEmpty name="bbs170Form" property="bbs170forumThreadMap">
        <logic:iterate id="ftMap" name="bbs170Form" property="bbs170forumThreadMap" indexId="fidx">

          <table class="w100">
            <tr>

              <td class="w100 txt_t">

                <table class="w100">
                  <tr>

                    <%-- forum title START --%>
                    <td class="txt_l w100">
                      <table class="w100">
                        <tr>

                          <%-- forum icon --%>
                          <td class="no_w">
                            <table>
                              <tr>

                                <logic:equal name="bbs170Form" property="bbs010forumSid" value="-1">
                                  <logic:equal name="ftMap" property="value[0].imgBinSid" value="0">
                                    <td>
                                      <img class="wp30hp30 classic-display" src="../bulletin/images/classic/icon_forum.gif" alt="<gsmsg:write key="bbs.3" />">
                                      <img class="wp30hp30 original-display" src="../bulletin/images/original/icon_forum_32.png" alt="<gsmsg:write key="bbs.3" />">
                                    </td>
                                  </logic:equal>
                                  <logic:notEqual name="ftMap" property="value[0].imgBinSid" value="0">
                                    <td>
                                      <img class="wp30hp30" src="../bulletin/bbs170.do?CMD=getImageFile&bbs010BinSid=<bean:write name="ftMap" property="value[0].imgBinSid" />&bbs010forumSid=<bean:write name="ftMap" property="key" />" alt="<gsmsg:write key="bbs.3" />">
                                    </td>
                                  </logic:notEqual>
                                </logic:equal>

                                <logic:notEqual name="bbs170Form" property="bbs010forumSid" value="-1">
                                  <logic:equal name="ftMap" property="value[0].imgBinSid" value="0">
                                    <td>
                                      <img class="wp30hp30 classic-display" src="../bulletin/images/classic/icon_forum.gif" alt="<gsmsg:write key="bbs.3" />">
                                      <img class="wp30hp30 original-display" src="../bulletin/images/original/icon_forum_32.png" alt="<gsmsg:write key="bbs.3" />">
                                    </td>
                                  </logic:equal>
                                  <logic:notEqual name="ftMap" property="value[0].imgBinSid" value="0">
                                    <td>
                                      <img class="wp30hp30" src="../bulletin/bbs170.do?CMD=getImageFile&bbs010BinSid=<bean:write name="ftMap" property="value[0].imgBinSid" />&bbs010forumSid=<bean:write name="ftMap" property="key" />" alt="<gsmsg:write key="bbs.3" />">
                                    </td>
                                  </logic:notEqual>
                                </logic:notEqual>

                              </tr>
                            </table>
                          </td>

                          <%-- forum name --%>
                          <td class="txt_l w100 no_r">
                            <span class="fs_18 fw_b">
                              <bean:write name="ftMap" property="value[0].bfiName" />
                            </span>
                          </td>
                        </tr>
                      </table>
                    </td>
                    <%-- forum title END --%>

                    <!-- page combo1 START -->
                    <logic:notEqual name="bbs170Form" property="bbs010forumSid" value="-1">
                      <td class="txt_r w20">
                        <div class="paging">
                        <logic:notEqual name="bbs170Form" property="bbs010forumSid" value="-1">
                          <bean:size id="count1" name="bbs170Form" property="bbsPageLabel" scope="request" />
                          <logic:greaterThan name="count1" value="1">
                            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
                              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                              <i class="icon-paging_left"></i>
                            </button>
                            <html:select property="bbs170page1" onchange="changePage(0);" styleClass="paging_combo">
                              <html:optionsCollection name="bbs170Form" property="bbsPageLabel" value="value" label="label" />
                            </html:select>
                            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
                              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                              <i class="icon-paging_right"></i>
                            </button>
                          </logic:greaterThan>
                        </logic:notEqual>
                        </div>
                      </td>
                    </logic:notEqual>
                    <!-- page combo1 END -->

                  </tr>
                </table>

                <table class="table-top">

                  <%-- list header START --%>
                  <tr>
                    <th class="w35 no_w">
                      <logic:equal name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_THRED) %>">
                        <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>">
                          <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_THRED) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>);">
                            <span>
                              <gsmsg:write key="bbs.2" />
                              <span class="classic-display">▲</span>
                              <span class="original-display">
                                <i class="icon-sort_up"></i>
                              </span>
                            </span>
                          </a>
                        </logic:equal>
                        <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>">
                          <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_THRED) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                            <span>
                              <gsmsg:write key="bbs.2" />
                              <span class="classic-display">▼</span>
                              <span class="original-display">
                                <i class="icon-sort_down"></i>
                              </span>
                            </span>
                          </a>
                        </logic:equal>
                      </logic:equal>
                      <logic:notEqual name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_THRED) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_THRED) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                          <span>
                            <gsmsg:write key="bbs.2" />
                          </span>
                        </a>
                      </logic:notEqual>
                    </th>

                    <th class="w15 no_w">
                      <logic:equal name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_USER) %>">
                        <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>">
                          <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_USER) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>);">
                            <span>
                              <gsmsg:write key="cmn.contributor" />
                              <span class="classic-display">▲</span>
                              <span class="original-display">
                                <i class="icon-sort_up"></i>
                              </span>
                            </span>
                          </a>
                        </logic:equal>
                        <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>">
                          <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_USER) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                            <span>
                              <gsmsg:write key="cmn.contributor" />
                              <span class="classic-display">▼</span>
                              <span class="original-display">
                                <i class="icon-sort_down"></i>
                              </span>
                            </span>
                          </a>
                        </logic:equal>
                      </logic:equal>
                      <logic:notEqual name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_USER) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_USER) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                          <span>
                            <gsmsg:write key="cmn.contributor" />
                          </span>
                        </a>
                      </logic:notEqual>
                    </th>

                    <th class="w20 no_w">
                      <logic:equal name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_FR) %>">
                        <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>">
                          <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_FR) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>);">
                            <span>
                              <gsmsg:write key="bbs.bbs070.5" />
                              <span class="classic-display">▼</span>
                              <span class="original-display">
                                <i class="icon-sort_down"></i>
                              </span>
                            </span>
                          </a>
                        </logic:equal>
                        <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>">
                          <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_FR) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                            <span>
                              <gsmsg:write key="bbs.bbs070.5" />
                              <span class="classic-display">▲</span>
                              <span class="original-display">
                                <i class="icon-sort_up"></i>
                              </span>
                            </span>
                          </a>
                        </logic:equal>
                      </logic:equal>
                      <logic:notEqual name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_FR) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_FR) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                          <span>
                            <gsmsg:write key="bbs.bbs070.5" />
                          </span>
                        </a>
                      </logic:notEqual>
                    </th>

                    <th class="w20 no_w">
                      <logic:equal name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_TO) %>">
                        <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>">
                          <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_TO) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>);">
                            <span>
                              <gsmsg:write key="bbs.bbs070.6" />
                              <span class="classic-display">▼</span>
                              <span class="original-display">
                                <i class="icon-sort_down"></i>
                              </span>
                            </span>
                          </a>
                        </logic:equal>
                        <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>">
                          <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_TO) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                            <span>
                              <gsmsg:write key="bbs.bbs070.6" />
                              <span class="classic-display">▲</span>
                              <span class="original-display">
                                <i class="icon-sort_up"></i>
                              </span>
                            </span>
                          </a>
                        </logic:equal>
                      </logic:equal>
                      <logic:notEqual name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_TO) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_LIMIT_TO) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                          <span>
                            <gsmsg:write key="bbs.bbs070.6" />
                          </span>
                        </a>
                      </logic:notEqual>
                    </th>

                    <th class="w5 no_w">
                      <logic:equal name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SIZE) %>">
                        <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>">
                          <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SIZE) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>);">
                            <span>
                              <gsmsg:write key="cmn.size" />
                              <span class="classic-display">▲</span>
                              <span class="original-display">
                                <i class="icon-sort_up"></i>
                              </span>
                            </span>
                          </a>
                        </logic:equal>
                        <logic:equal name="bbs170Form" property="bbs170orderKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_DESC) %>">
                          <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SIZE) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                            <span>
                              <gsmsg:write key="cmn.size" />
                              <span class="classic-display">▼</span>
                              <span class="original-display">
                                <i class="icon-sort_down"></i>
                              </span>
                            </span>
                          </a>
                        </logic:equal>
                      </logic:equal>
                      <logic:notEqual name="bbs170Form" property="bbs170sortKey" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SIZE) %>">
                        <a href="#!" onClick="return onTitleLinkSubmit(<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.SORT_KEY_SIZE) %>,<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.ORDER_KEY_ASC) %>);">
                          <span>
                            <gsmsg:write key="cmn.size" />
                          </span>
                        </a>
                      </logic:notEqual>
                    </th>

                    <th class="w5 no_w">
                      <gsmsg:write key="bbs.bbs080.1" />
                    </th>
                  </tr>
                  <%-- list header END --%>

                  <%-- Key＝フォーラムSIDのリスト --%>
                  <logic:iterate id="threadMdl" name="ftMap" property="value" indexId="idx">

                    <%-- スレッド一覧 START --%>
                    <%-- <logic:notEmpty name="threadMdl" property="threadList"> --%>

                    <tr>
                      <td class="txt_l">
                        <div class="verAlignMid">
                          <img class="classic-display" src="../bulletin/images/classic/icon_thread.gif" alt="<gsmsg:write key="bbs.2" />">
                          <a href="javascript:buttnPushWrite('editThreOrPost',<bean:write name="threadMdl" property="bfiSid" />,<bean:write name="threadMdl" property="btiSid" />);">
                            <!-- 重要度 -->
                            <logic:equal name="threadMdl" property="bfiThreImportance" value="1">
                              <span>
                                <img class="classic-display" src="../common/images/classic/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                                <img class="original-display" src="../common/images/original/icon_zyuu.png" alt="<gsmsg:write key="sml.61" />">
                              </span>
                            </logic:equal>
                            <logic:equal name="threadMdl" property="btsTempflg" value="1">
                              <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attach.file" />">
                              <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attach.file" />">
                            </logic:equal>
                            <span class="cl_linkDef">
                              <bean:write name="threadMdl" property="btiTitle" />
                            </span>
                          </a>
                        </div>
                      </td>

                      <td class="txt_l fs_13">
                        <bean:define id="cbGrpSid" name="threadMdl" property="grpSid" type="java.lang.Integer" />
                        <% if (cbGrpSid.intValue() > 0) { %>
                        <logic:equal name="threadMdl" property="grpJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
                          <s><bean:write name="threadMdl" property="grpName" /></s>
                        </logic:equal>
                        <logic:notEqual name="threadMdl" property="grpJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
                          <bean:write name="threadMdl" property="grpName" />
                        </logic:notEqual>
                        <% } else { %>
                        <logic:equal name="threadMdl" property="userJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
                          <s><bean:write name="threadMdl" property="userName" /> </span></s>
                        </logic:equal>
                        <logic:notEqual name="threadMdl" property="userJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
                          <logic:equal value="1" name="threadMdl" property="userYukoKbn">
                            <span class="mukoUser">
                              <bean:write name="threadMdl" property="userName" />
                            </span>
                          </logic:equal>
                          <logic:notEqual value="1" name="threadMdl" property="userYukoKbn">
                            <bean:write name="threadMdl" property="userName" />
                          </logic:notEqual>
                        </logic:notEqual>
                        <% } %>
                      </td>
                      <td class="txt_c fs_13">
                        <bean:write name="threadMdl" property="strBtiLimitFrDate" />
                      </td>
                      <td class="txt_c fs_13">
                        <bean:write name="threadMdl" property="strBtiLimitDate" />
                      </td>
                      <td class="txt_r fs_13">
                        <bean:write name="threadMdl" property="viewBtsSize" />
                      </td>
                      <td class="txt_c fs_13">
                        <button type="button" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPushDelete('<bean:write name="threadMdl" property="btiSid" />');" class="baseBtn">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                          <i class="fas fa-times"></i>
                          <gsmsg:write key="cmn.delete" />
                        </button>
                      </td>
                    </tr>

                    <%-- </logic:notEmpty> --%>
                    <%-- スレッド一覧 END --%>

                  </logic:iterate>

                </table>

                <!-- page combo2 START -->
                <logic:notEqual name="bbs170Form" property="bbs010forumSid" value="-1">
                  <bean:size id="count1" name="bbs170Form" property="bbsPageLabel" scope="request" />
                  <logic:greaterThan name="count1" value="1">
                    <div class="txt_r w100 paging">
                      <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
                        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                        <i class="icon-paging_left"></i>
                      </button>
                      <html:select property="bbs170page2" onchange="changePage(1);" styleClass="paging_combo">
                        <html:optionsCollection name="bbs170Form" property="bbsPageLabel" value="value" label="label" />
                      </html:select>
                      <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
                        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                        <i class="icon-paging_right"></i>
                      </button>
                    </div>
                  </logic:greaterThan>
                </logic:notEqual>
                <!-- page combo2 END -->

                <!-- width set  -->

              </td>

            </tr>
          </table>

        </logic:iterate>
      </logic:notEmpty>
      <%-- single forum END --%>

    </div>

  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>
