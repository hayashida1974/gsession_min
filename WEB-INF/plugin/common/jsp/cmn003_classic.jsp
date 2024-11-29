<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<% pageContext.setAttribute("cmn003Form", request.getAttribute(request.getParameter("thisFormName"))); %>
<% String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY; %>
<% String gsurl = "../main/man001.do"; %>
<logic:equal name="cmn003Form" property="cmn003SysAdminFlg" value="true">
  <% gsurl = "../main/man002.do"; %>
</logic:equal>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>
<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <title>GROUPSESSION <gsmsg:write key="cmn.cmn003.1" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery.jcarousel.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmn003.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmn003_classic.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href="../common/css/theme_classic/skins/jts/skin.css?<%= GSConst.VERSION_PARAM %>" type='text/css'>
  <link rel="stylesheet" href="../common/css/common.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
  <theme:css filename="theme.css"/>
  <logic:notEqual name="cmn003Form" property="cmn003LogoBinSid" value="0">
    <style><!--
      .header_logo {
         background-image:url(../common/cmn003.do?CMD=getLogoImageFile&cmn003LogoBinSid=<bean:write name="cmn003Form" property="cmn003LogoBinSid" />);
      }
    --></style>
  </logic:notEqual>
  <script type="text/javascript">
  <!--
  var msglist_cmn003 = (function () {
    //使用するメッセージキーの配列を作成
     var ret = new Array();
     ret['cmn.cmn003.5'] = '<gsmsg:write key="cmn.cmn003.5"/>';

    return ret;
  })();

  -->
  </script>
</head>
<body class="body_bg classic m0 p0 bgC_menu hp90 ofy_h menu_overflow">
<logic:equal name="cmn003Form" property="cmn003LogoBinSid" value="0">
  <div>
    <a href="<%= gsurl %>" target="body" class="menu_evt gsLogo">GroupSession</a>
  </div>
  <div>
    <a href="<%= gsurl %>" target="body" class="menu_evt gsLogo2">GroupSession</a>
  </div>
</logic:equal>
<logic:notEqual name="cmn003Form" property="cmn003LogoBinSid" value="0">
  <div>
    <a href="<%= gsurl %>" target="body" border="0"  class="menu_evt header_logo" " >GroupSession</a>
  </div>
  <div>
    <a href="<%= gsurl %>" target="body" border="0"  class="menu_evt gsLogo2">GroupSession</a>
  </div>
</logic:notEqual>
<html:form action="/common/cmn002" target="_parent">
  <input type="hidden" id="windowCloseFlag" value="">
  <bean:define id="kusr" name="<%= key %>" scope="session" />
  <bean:define id="cmnMaxPage" name="cmn003Form" property="cmn003maxPage" />
  <% int menuMaxPage = ((Integer) cmnMaxPage).intValue(); %>
  <html:hidden name="cmn003Form" property="menuPage" />
  <input type="hidden" name="url" value="">
  <input type="hidden" name="backPlugin" value="">
  <div class="w100 txt_c">
    <div class="display_inline-block" >
      <ul id="mycarousel" class="jcarousel-skin-jts">
        <logic:notEqual name="kusr" property="usrsid" value="0">
          <bean:define id="menuInfoList" name="cmn003Form" property="menuInfoList" />
          <% java.util.List menuList = (java.util.List) menuInfoList; %>
          <% int menuSize = menuList.size(); %>
          <% jp.groupsession.v2.cmn.cmn003.MenuInfo menuInfo = null; %>
          <% int pageNum = 1; %>
          <% int sixTeenCnt = 1; %>
          <% int eightCnt = 1; %>
          <% for (int menuCount = 1; menuCount <= menuMaxPage * 16; menuCount++) { %>
          <%  if (menuCount == 1 || sixTeenCnt == 1) { %>
            <li class="m0 p0 js_menu_btn">
          <%  } %>
          <%  if (menuCount <= menuSize) { %>
          <%      menuInfo = (jp.groupsession.v2.cmn.cmn003.MenuInfo) menuList.get(menuCount - 1); %>
          <%      String pluginId = menuInfo.getPluginId(); %>
          <%      String name = menuInfo.getName(); %>
          <%      String menuUrl = menuInfo.getUrl(); %>
          <%      Integer menuTarget = menuInfo.getTarget(); %>
          <%      int pluginKbn = menuInfo.getPluginKbn(); %>
          <%      Long binSid = menuInfo.getBinSid(); %>
          <%      pageNum = (menuCount - 1) / 16 + 1; %>
          <%      int paramKbn = menuInfo.getParamKbn(); %>
          <%      int sendKbn = menuInfo.getSendKbn(); %>
          <%      int strLen = menuInfo.getName().length(); %>
          <%      if (strLen == 7) { %>
          <%         name = name.substring(0, 4) + "<br>" + name.substring(4); %>
          <%      } else if (strLen >= 8) { %>
          <%          strLen = 8; %>
          <%          name = name.substring(0, 5) + "<br>" + name.substring(5); %>
          <%      } %>
                    <div class="m0 p0 display_inline-block flo_l">
                      <% if (menuTarget == 0) { %>
                          <% if (pluginKbn != 0) { %>
                          <a id="<%= pluginId %>" class="user_plugin_link" href="../common/cmn002.do?menuPage=<%=pageNum%>&url=<%=menuUrl%>" target="_parent" onclick="return clickMenuTarFrame('<%= pluginId %>','<%= paramKbn %>','<%= sendKbn %>');">
                          <% } else { %>
                          <a class="menu_evt" href="../common/cmn002.do?menuPage=<%=pageNum%>&url=<%=menuUrl%>" target="_parent" onclick="return clickMenuGs('<%= menuUrl %>');">
                          <% } %>
                      <% } else { %>
                          <a id="<%= pluginId %>" class="user_plugin_link" href="<%=menuUrl%>" target="_blank" onclick="clickMenuTarWindow('<%= pluginId %>','<%= paramKbn %>','<%= sendKbn %>'); return false;">
                      <% } %>
                      <div class="menu_bg">
                        <table>
                          <tr>
                            <% if (pluginKbn != 0) { %>
                              <% if (binSid != 0) { %>
                              <td>
                                <img name="<%= pluginId %>MenuImg" alt="<%= name %>" class="menu_img pluginIcon_size"  src="../common/cmn003.do?CMD=getImageFile&cmn003PluginId=<%= pluginId %>&cmn003BinSid=<%= binSid %>" border="0" />
                              </td>
                              <% } else { %>
                              <td>
                                <img name="<%= pluginId %>MenuImg" alt="<%= name %>" class="menu_img pluginIcon_size" src="../common/images/pluginImg/classic/menu_icon_plugin_default_25.png" border="0" />
                              </td>
                              <% } %>
                            <% } else if (menuInfo.getIconClassic() != null && menuInfo.getIconClassic().length() > 0) { %>
                              <td>
                                <img name="<%= pluginId %>MenuImg" alt="<%= name %>" class="menu_img header_pluginImg-classic  pluginIcon_size"  src="<%= menuInfo.getIconClassic()%>" border="0" />
                              </td>
                            <% } else if (menuInfo.getIcon() != null && menuInfo.getIcon().length() > 0) { %>
                              <td>
                                <img name="<%= pluginId %>MenuImg" alt="<%= name %>" class="menu_img header_pluginImg-classic  pluginIcon_size"  src="<%= menuInfo.getIcon()%>" border="0" />
                              </td>
                            <% } else { %>
                              <td>
                                <img name="<%= pluginId %>MenuImg" alt="<%= name %>" class="menu_img header_pluginImg-classic  pluginIcon_size"  src="../common/images/pluginImg/classic/menu_icon_<%= pluginId %>_25.gif" border="0" />
                              </td>
                            <% } %>
                            <% if (strLen == 7) { %>
                            <td>
                              <table>
                                <tr>
                                  <td class="str_7width"></td>
                                  <td class="menu_text menu_text_position<%= strLen %>">
                                    <b><%= name %></b>
                                  </td>
                                </tr>
                              </table>
                            </td>
                            <% } else { %>
                            <td class="menu_text menu_text_position<%= strLen %>">
                              <b><%= name %></b>
                            </td>
                            <% } %>
                          </tr>
                        </table>
                      </div>
                      </a>
                    </div>
          <% }  else { %>
                    <div class="m0 p0 display_inline-block flo_l menu_no"></div>
          <% } %>
          <% if (sixTeenCnt == 16) { %>
          <%  sixTeenCnt = 0; %>
          <%  eightCnt = 0; %>
              </li>
          <% } %>
          <% if (eightCnt == 8 && sixTeenCnt != 0) { %>
          <%  eightCnt = 0; %>
                <br>
          <% } %>
          <% sixTeenCnt = sixTeenCnt + 1; %>
          <% eightCnt = eightCnt + 1; %>
          <% } %>
        </logic:notEqual>
      </ul>
      <% if (menuMaxPage > 1) { %>
      <span id="menu_countarea" class="menu_page menu_bun">
        <span id="pageCount"><bean:write name="cmn003Form" property="menuPage" /></span>/<%= String.valueOf(menuMaxPage) %>
      </span>
      <% } %>
    </div>
  </div>
</html:form>
<!-- 右下ユーザ、ログアウト情報 -->
<div id="menu_username" class="login_user">
  <div class="verAlignMid">
  <!-- メモ -->
  <logic:notEqual value="true" name="cmn003Form" property="cmn003SysAdminFlg">
    <logic:equal value="1" name="cmn003Form" property="cmn003MemoUse">
      <span>
        <span class="icon-memo"></span>
        <a class="cursor_p cl_fontOutlineLink menu_evt menu_headerText" href="#!" onClick="openMemoWindow()">
          <gsmsg:write key="memo.01" />
        </a>
      </span>
      <span class="ml5">|</span>
    </logic:equal>
  </logic:notEqual>
  <!-- ヘルプ -->
  <img src="../common/images/classic/icon_help.png" alt="<gsmsg:write key="cmn.help" />" styleClass="img_bottom" >
  <a class="menu_evt" href="#" onClick="help()"><gsmsg:write key="cmn.help" /></a>
  <span class="ml5">|</span>
  <%-- 設定 --%>
  <div class="js_setteiArea ml5 cursor_p cl_fontOutlineLink menu_evt menu_headerText cl_linkHoverChange topMenu_setting link" href="#">
    <span>
      <span class="icon-setting"></span>
      <a class=> <gsmsg:write key="cmn.setting" /></a>
    </span>
  </div>
  <span class="ml5">|</span>
  <!-- ユーザ名 -->
  <img src="../common/images/classic/icon_user.png" alt="<gsmsg:write key="cmn.user" />" styleClass="img_bottom" >
  <logic:notEmpty name="<%= key %>" scope="session">
    <bean:write name="<%= key %>" scope="session" property="usisei" />
    <span class="ml10"><bean:write name="<%= key %>" scope="session" property="usimei" /></span>
    <span class="ml5">|</span>
    <img src="../common/images/classic/icon_logout_s.gif" alt="<gsmsg:write key="cmn.user" />" styleClass="img_bottom hp12" >
    <a class="menu_evt" href="./cmn001.do?CMD=logout" target="_top"><gsmsg:write key="mobile.11" /></a>
  </logic:notEmpty>
  <logic:empty name="<%= key %>" scope="session"></logic:empty>
  </div>
</div>
</body>
<script type="text/javascript">
  var animate = false;
  $(function(){
    var maxCnt = <bean:write name="cmn003Form" property="cmn003maxPage" />;
    $("#mycarousel").jcarousel({
      start:<bean:write name="cmn003Form" property="menuPage" />,
      size :<bean:write name="cmn003Form" property="cmn003maxPage" />,
      scroll:1,
      <logic:equal name="cmn003Form" property="cmn003buttonFlg" value="1">
      buttonNextHTML:'',
      buttonPrevHTML:'',
      </logic:equal>
      initCallback: mycarousel_initCallback,
      itemVisibleInCallback: {
          onBeforeAnimation: callback1,
          onAfterAnimation: callback2
      }
    });

    $('.jcarousel-next-horizontal').hover(
       function(){
              var cnt = eval($("#pageCount").text());
              if (cnt < maxCnt) {
                $('.jcarousel-next-horizontal').removeClass("jcarousel-hover2");
                $('.jcarousel-next-horizontal').addClass("jcarousel-hover1");
              }
         },function(){
              var cnt = eval($("#pageCount").text());
              if (cnt < maxCnt) {
                $('.jcarousel-next-horizontal').removeClass("jcarousel-hover1");
                $('.jcarousel-next-horizontal').addClass("jcarousel-hover2");
              }
         });

      $('.jcarousel-prev-horizontal').hover(
        function(){
            var cnt = eval($("#pageCount").text());
            if ((cnt - 1) > 0) {
              $('.jcarousel-prev-horizontal').removeClass("jcarousel-hover2");
              $('.jcarousel-prev-horizontal').addClass("jcarousel-hover1");
            }
        },function(){
            var cnt = eval($("#pageCount").text());
            if ((cnt - 1) > 0) {
              $('.jcarousel-prev-horizontal').removeClass("jcarousel-hover1");
              $('.jcarousel-prev-horizontal').addClass("jcarousel-hover2");
            }
        });
      $('.menu_bg').hover(
        function(){
            $(this).addClass("menu_hover");
        },function(){
            $(this).removeClass("menu_hover");
        });
  });

  function callback1() {
     animate = true;
  }

  function callback2() {
       animate = false;
  }

  function mycarousel_initCallback(carousel) {
    var maxCnt = <bean:write name="cmn003Form" property="cmn003maxPage" />;

      carousel.buttonNext.bind('click', function() {
          if (!animate) {
            var cnt = eval($("#pageCount").text());
            if (cnt < maxCnt) {
               $("#pageCount").text(cnt + 1);
               $("input[name='menuPage']").attr("value",cnt + 1);
               if (cnt +1 == maxCnt) {
                 $('.jcarousel-next-horizontal').addClass("jcarousel-hover2");
               }
            }
          }
      });
      carousel.buttonPrev.bind('click', function() {
          if (!animate) {
            var cnt = eval($("#pageCount").text());
            if ((cnt - 1) > 0) {
                $("#pageCount").text(cnt - 1);
                $("input[name='menuPage']").attr("value",cnt - 1);
                if ((cnt - 1) == 1) {
                  $('.jcarousel-prev-horizontal').addClass("jcarousel-hover2");
                }
            }
          }
      });
  };
</script>
</html:html>