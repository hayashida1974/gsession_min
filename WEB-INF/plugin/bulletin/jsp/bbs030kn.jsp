<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstBBS" %>
<%@ page import="jp.groupsession.v2.bbs.GSConstBulletin" %>

<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="bbs.bbs030kn.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../bulletin/js/bbs030kn.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body>

<html:form action="/bulletin/bbs030kn">

<input type="hidden" name="CMD" value="">
<html:hidden name="bbs030knForm" property="backScreen" />
<html:hidden name="bbs030knForm" property="bbs020indexRadio" />
<html:hidden name="bbs030knForm" property="s_key" />
<html:hidden name="bbs030knForm" property="tempDirId" />

<html:hidden name="bbs030knForm" property="bbs010page1" />
<html:hidden name="bbs030knForm" property="bbs020page1" />
<html:hidden name="bbs030knForm" property="bbs020forumSid" />
<html:hidden name="bbs030knForm" property="bbs030cmdMode" />
<html:hidden name="bbs030knForm" property="bbs030forumName" />
<html:hidden name="bbs030knForm" property="bbs030comment" />
<html:hidden name="bbs030knForm" property="bbs030groupSid" />
<html:hidden name="bbs030knForm" property="bbs030reply" />
<html:hidden name="bbs030knForm" property="bbs030read" />
<html:hidden name="bbs030knForm" property="bbs030ImageName" />
<html:hidden name="bbs030knForm" property="bbs030ImageSaveName" />
<html:hidden name="bbs030knForm" property="bbs030mread" />
<html:hidden name="bbs030knForm" property="bbs030templateKbn" />
<html:hidden name="bbs030knForm" property="bbs030template" />
<html:hidden name="bbs030knForm" property="bbs030templateWriteKbn" />
<html:hidden name="bbs030knForm" property="bbs030templateType" />
<html:hidden name="bbs030knForm" property="bbs030templateHtml" />
<html:hidden name="bbs030knForm" property="bbs030diskSize" />
<html:hidden name="bbs030knForm" property="bbs030diskSizeLimit" />
<html:hidden name="bbs030knForm" property="bbs030warnDisk" />
<html:hidden name="bbs030knForm" property="bbs030warnDiskThreshold" />

<html:hidden name="bbs030knForm" property="bbs030LimitDisable" />
<html:hidden name="bbs030knForm" property="bbs030Limit" />
<html:hidden name="bbs030knForm" property="bbs030LimitDate" />
<html:hidden name="bbs030knForm" property="bbs030TimeUnit" />
<html:hidden name="bbs030knForm" property="bbs030Keep" />
<html:hidden name="bbs030knForm" property="bbs030KeepDateY" />
<html:hidden name="bbs030knForm" property="bbs030KeepDateM" />
<html:hidden name="bbs030knForm" property="bbs030ForumLevel" />
<html:hidden name="bbs030knForm" property="bbs030ParentForumSid" />
<html:hidden name="bbs030knForm" property="bbs030knForumName" />
<html:hidden name="bbs030knForm" property="bbs030AdaptChildMemFlg" />
<html:hidden name="bbs030knForm" property="bbs030HaveChildForumFlg" />
<html:hidden name="bbs030knForm" property="bbs030FollowParentMemFlg" />
<html:hidden name="bbs030knForm" property="bbs030UnlimitedFlg" />


<logic:notEmpty name="bbs030knForm" property="bbs030UserListWriteReg">
<logic:iterate id="usid" name="bbs030knForm" property="bbs030UserListWriteReg">
  <input type="hidden" name="bbs030UserListWriteReg" value="<bean:write name="usid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="bbs030knForm" property="bbs030UserListReadReg">
<logic:iterate id="usid" name="bbs030knForm" property="bbs030UserListReadReg">
  <input type="hidden" name="bbs030UserListReadReg" value="<bean:write name="usid" />">
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="bbs030knForm" property="bbs030UserListAdmReg">
<logic:iterate id="usid" name="bbs030knForm" property="bbs030UserListAdmReg">
  <input type="hidden" name="bbs030UserListAdmReg" value="<bean:write name="usid" />">
</logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->
<div class="kanriPageTitle w85 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.bulletin" /></span><gsmsg:write key="bbs.bbs030kn.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToInput');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w85 mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
  <table class="table-left">
    <tr>
      <th class="w25">
        <gsmsg:write key="bbs.4" />
      </th>
      <td class="w75">
        <bean:write name="bbs030knForm" property="bbs030forumName" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.comment" />
      </th>
      <td class="w75">
        <bean:write name="bbs030knForm" property="bbs030viewcomment" filter="false" />
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="bbs.bbs030.17" />
      </th>
      <td class="w75">
      <logic:equal name="bbs030knForm" property="bbs030ParentForumSid" value="0">
        <gsmsg:write key="cmn.no" />
      </logic:equal>

      <logic:notEqual name="bbs030knForm" property="bbs030ParentForumSid" value="0">
        <logic:notEqual name="bbs030knForm" property="bbs030knParentBinSid" value="0">
          <img class="wp20hp20" alt="<gsmsg:write key="bbs.3" />"
              src="../bulletin/bbs010.do?CMD=getImageFile&bbs010BinSid=<bean:write name="bbs030knForm" property="bbs030knParentBinSid" />&bbs010ForSid=<bean:write name="bbs030knForm" property="bbs030ParentForumSid" />">
        </logic:notEqual>
        <logic:equal name="bbs030knForm" property="bbs030knParentBinSid" value="0">
          <img class="classic-display" src="../bulletin/images/classic/icon_forum.gif" alt="<gsmsg:write key="bbs.3" />">
          <img class="original-display" src="../bulletin/images/original/menu_icon_single.png" alt="<gsmsg:write key="bbs.3" />">
        </logic:equal>

        <bean:write name="bbs030knForm" property="bbs030knForumName" filter="false" />
      </logic:notEqual>
      </td>
    </tr>
    <tr>
      <th class="w25">
        <gsmsg:write key="cmn.member" />
      </th>
      <td class="w75">
        <div id="selectFollowParentMemArea">
          <gsmsg:write key="bbs.bbs030.22" />
        </div>

        <div id="selectMemberArea">

          <table class="table-left w60">
            <tr>
              <th class="txt_c">
                <gsmsg:write key="cmn.add.edit.delete" />
              </th>
            </tr>
            <tr>
              <td class="w75">
                <logic:notEmpty name="bbs030knForm" property="bbs030knMemNameList">
                  <logic:iterate id="memName" name="bbs030knForm" property="bbs030knMemNameList" >
                    <logic:equal value="1" name="memName" property="usrUkoFlg">
                      <span class="mukoUser"><bean:write name="memName" property="label"/></span><br>
                    </logic:equal>
                    <logic:equal value="0" name="memName" property="usrUkoFlg">
                      <bean:write name="memName" property="label"/><br>
                    </logic:equal>
                  </logic:iterate>
                </logic:notEmpty>
                <logic:empty name="bbs030knForm" property="bbs030knMemNameList">&nbsp;</logic:empty>
              </td>
            </tr>
          </table>

          <table class="table-left w60">
            <tr>
              <th class="txt_c">
                <gsmsg:write key="cmn.reading" />
              </th>
            </tr>
            <tr>
              <td class="w75">
                <logic:notEmpty name="bbs030knForm" property="bbs030knMemNameListRead">
                  <logic:iterate id="memNameRead" name="bbs030knForm" property="bbs030knMemNameListRead" >
                    <logic:equal value="1" name="memNameRead" property="usrUkoFlg">
                      <span class="mukoUser"><bean:write name="memNameRead" property="label"/></span><br>
                    </logic:equal>
                    <logic:equal value="0" name="memNameRead" property="usrUkoFlg">
                      <bean:write name="memNameRead" property="label"/><br>
                    </logic:equal>
                  </logic:iterate>
                </logic:notEmpty>
                <logic:empty name="bbs030knForm" property="bbs030knMemNameListRead">&nbsp;</logic:empty>
              </td>
            </tr>
            </table>

             <logic:equal name="bbs030knForm" property="bbs030cmdMode" value="1">
             <logic:equal name="bbs030knForm" property="bbs030HaveChildForumFlg" value="1">
               <gsmsg:write key="bbs.bbs030.21" /><gsmsg:write key="cmn.colon" />
               <span class="fw_b">
                 <logic:equal name="bbs030knForm" property="bbs030AdaptChildMemFlg" value="1"><gsmsg:write key="fil.128" /></logic:equal>
                 <logic:notEqual name="bbs030knForm" property="bbs030AdaptChildMemFlg" value="1"><gsmsg:write key="fil.129" /></logic:notEqual>
               </span>
             </logic:equal>
           </logic:equal>
         </div>

       </td>
     </tr>
     <tr>
       <th class="w25">
         <gsmsg:write key="bbs.35" />
       </th>
       <td class="w75">
         <logic:notEmpty name="bbs030knForm" property="bbs030knMemNameListAdm">
           <logic:iterate id="memNameAdm" name="bbs030knForm" property="bbs030knMemNameListAdm" >
             <logic:equal value="1" name="memNameAdm" property="usrUkoFlg">
               <span class="mukoUser"><bean:write name="memNameAdm" property="label"/></span><br>
             </logic:equal>
             <logic:equal value="0" name="memNameAdm" property="usrUkoFlg">
               <bean:write name="memNameAdm" property="label"/><br>
             </logic:equal>
           </logic:iterate>
         </logic:notEmpty>
       </td>
     </tr>
     <tr>
       <th class="w25">
         <gsmsg:write key="cmn.icon" />
       </th>
       <td class="w75">

         <div>
         <logic:equal name="bbs030knForm" property="bbs030ImageName" value="">
           <img class="classic-display wp20hp20" src="../bulletin/images/classic/icon_forum.gif" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />">
           <img class="original-display" src="../bulletin/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.icon" />">
         </logic:equal>
         <logic:notEqual name="bbs030knForm" property="bbs030ImageName" value="">
           <img src="../bulletin/bbs030.do?CMD=getImageFile&tempDirId=<bean:write name="bbs030knForm" property="tempDirId" />" name="pitctImage" class="wp30" alt="<gsmsg:write key="cmn.icon" />" onload="initImageView('pitctImage');">
         </logic:notEqual>
         </div>

       </td>
     </tr>
     <tr>
       <th class="w25">
         <gsmsg:write key="bbs.6" />
       </th>
       <td class="w75">
         <logic:equal name="bbs030knForm" property="bbs030reply" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRE_REPLY_YES) %>"><gsmsg:write key="cmn.permit" /></logic:equal>
         <logic:equal name="bbs030knForm" property="bbs030reply" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_THRE_REPLY_NO) %>"><gsmsg:write key="cmn.not.permit" /></logic:equal>
       </td>
     </tr>
     <tr>
       <th class="w25">
         <gsmsg:write key="bbs.7" />
       </th>
       <td class="w75">
         <logic:equal name="bbs030knForm" property="bbs030read" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.NEWUSER_THRE_VIEW_NO) %>"><gsmsg:write key="cmn.read.yet" /></logic:equal>
         <logic:equal name="bbs030knForm" property="bbs030read" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.NEWUSER_THRE_VIEW_YES) %>"><gsmsg:write key="cmn.read.already" /></logic:equal>
       </td>
     </tr>
     <tr>
       <th class="w25">
         <gsmsg:write key="cmn.all.read" />
       </th>
       <td class="w75">
         <logic:equal name="bbs030knForm" property="bbs030mread" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_FORUM_MREAD_YES) %>"><gsmsg:write key="cmn.permit" /></logic:equal>
         <logic:equal name="bbs030knForm" property="bbs030mread" value="<%= String.valueOf(jp.groupsession.v2.bbs.GSConstBulletin.BBS_FORUM_MREAD_NO) %>"><gsmsg:write key="cmn.not.permit" /></logic:equal>
       </td>
     </tr>
     <tr>
       <th class="w25">
         <gsmsg:write key="wml.87" />
       </th>
       <td class="w75">
         <logic:equal name="bbs030knForm" property="bbs030diskSize" value="<%= String.valueOf(GSConstBulletin.BFI_DISK_NOLIMIT) %>">
           <gsmsg:write key="wml.31" />
         </logic:equal>
         <logic:equal name="bbs030knForm" property="bbs030diskSize" value="<%= String.valueOf(GSConstBulletin.BFI_DISK_LIMITED) %>">
           <gsmsg:write key="wml.32" />&nbsp;&nbsp;<bean:write name="bbs030knForm" property="bbs030diskSizeLimit" /><gsmsg:write key="cmn.mb" />
         </logic:equal>
       </td>
     </tr>
     <logic:equal name="bbs030knForm" property="bbs030diskSize" value="<%= String.valueOf(GSConstBulletin.BFI_DISK_LIMITED) %>">
     <tr>
       <th class="w25">
         <gsmsg:write key="wml.wml150.15" />
       </th>
       <td class="w75">
         <logic:equal name="bbs030knForm" property="bbs030warnDisk" value="<%= String.valueOf(GSConstBulletin.BFI_WARN_DISK_NO) %>">
           <gsmsg:write key="cmn.notset" />
         </logic:equal>
         <logic:equal name="bbs030knForm" property="bbs030warnDisk" value="<%= String.valueOf(GSConstBulletin.BFI_WARN_DISK_YES) %>">
           <gsmsg:write key="cmn.warning2" />&nbsp;
           <gsmsg:write key="cmn.threshold" />&nbsp;<bean:write name="bbs030knForm" property="bbs030warnDiskThreshold" />%
         </logic:equal>
       </td>
     </tr>
     </logic:equal>
     <tr>
       <th class="w25">
         <gsmsg:write key="bbs.bbs030.6" />
       </th>
       <td class="w75">
         <logic:equal name="bbs030knForm" property="bbs030templateKbn" value="0">
           <gsmsg:write key="cmn.noset" />
         </logic:equal>

         <logic:equal name="bbs030knForm" property="bbs030templateKbn" value="1">
           <gsmsg:write key="cmn.setting.do" />

           <logic:equal name="bbs030knForm" property="bbs030templateWriteKbn" value="1">
             &nbsp;&nbsp;<gsmsg:write key="bbs.bbs030.8" />
           </logic:equal>

           <table class="table-left w90">
             <tr>
               <th class="txt_c">
                 <gsmsg:write key="cmn.template" />
               </th>
             </tr>
             <tr>
               <td class="w75">
                 <logic:equal name="bbs030knForm" property="bbs030templateType" value="0"><bean:write name="bbs030knForm" property="bbs030viewTemplate" filter="false" /></logic:equal>
                 <logic:equal name="bbs030knForm" property="bbs030templateType" value="1"><bean:write name="bbs030knForm" property="bbs030templateHtml" filter="false" /></logic:equal>
               </td>
             </tr>
           </table>
         </logic:equal>
       </td>
     </tr>
     <tr>
       <th class="w25">
         <gsmsg:write key="bbs.12" />
       </th>
       <td class="w75">
         <logic:equal name="bbs030knForm" property="bbs030LimitDisable" value="<%= String.valueOf(GSConstBulletin.THREAD_DISABLE) %>">
           <gsmsg:write key="fil.107" />
         </logic:equal>
         <logic:equal name="bbs030knForm" property="bbs030LimitDisable" value="<%= String.valueOf(GSConstBulletin.THREAD_ENABLE) %>">
           <gsmsg:write key="fil.108" />
         </logic:equal>
       </td>
     </tr>
     <logic:equal name="bbs030knForm" property="bbs030LimitDisable" value="<%= String.valueOf(GSConstBulletin.THREAD_ENABLE) %>">
     <tr>
       <th class="w25">
         <gsmsg:write key="bbs.bbs030kn.3" />
       </th>
       <td class="w75">
         <logic:equal name="bbs030knForm" property="bbs030UnlimitedFlg" value="<%= String.valueOf(GSConstBulletin.BFI_UNLIMITED_YES) %>">
           <gsmsg:write key="cmn.permit" />
         </logic:equal>
         <logic:equal name="bbs030knForm" property="bbs030UnlimitedFlg" value="<%= String.valueOf(GSConstBulletin.BFI_UNLIMITED_NO) %>">
           <gsmsg:write key="cmn.not.permit" />
         </logic:equal>
       </td>
     </tr>
     <tr>
       <th class="w25">
         <gsmsg:write key="bbs.bbs030.10" />
       </th>
       <td class="w75">
         <logic:equal name="bbs030knForm" property="bbs030Limit" value="<%= String.valueOf(GSConstBulletin.THREAD_LIMIT_NO) %>">
           <gsmsg:write key="cmn.unlimited" />
         </logic:equal>
         <logic:equal name="bbs030knForm" property="bbs030Limit" value="<%= String.valueOf(GSConstBulletin.THREAD_LIMIT_YES) %>">
           <gsmsg:write key="bbs.bbs070.4" />&nbsp;&nbsp;<bean:write name="bbs030knForm" property="bbs030LimitDate" /><gsmsg:write key="cmn.days.after2" />
         </logic:equal>
       </td>
     </tr>
     <tr>
       <th class="w25">
         <gsmsg:write key="bbs.bbs030.24" />
       </th>
       <td class="w75">
         <logic:equal name="bbs030knForm" property="bbs030TimeUnit" value="<%= String.valueOf(GSConstBBS.MINUTE_DIVISION5) %>">
           <gsmsg:write key="bbs.bbs030.25"/>
         </logic:equal>
         <logic:equal name="bbs030knForm" property="bbs030TimeUnit" value="<%= String.valueOf(GSConstBBS.MINUTE_DIVISION10) %>">
           <gsmsg:write key="bbs.bbs030.26"/>
         </logic:equal>
         <logic:equal name="bbs030knForm" property="bbs030TimeUnit" value="<%= String.valueOf(GSConstBBS.MINUTE_DIVISION15) %>">
           <gsmsg:write key="bbs.bbs030.27"/>
         </logic:equal>
       </td>
     </tr>
     <tr>
       <th class="w25">
         <gsmsg:write key="bbs.bbs030.11" />
       </th>
       <td class="w75">
         <logic:equal name="bbs030knForm" property="bbs030Keep" value="<%= String.valueOf(GSConstBulletin.THREAD_KEEP_NO) %>">
           <gsmsg:write key="cmn.noset" />
         </logic:equal>
         <logic:equal name="bbs030knForm" property="bbs030Keep" value="<%= String.valueOf(GSConstBulletin.THREAD_KEEP_YES) %>">
           <bean:define id="keepMonth" name="bbs030knForm" property="bbs030KeepDateM" />
           <% String strKeepMonth = String.valueOf(keepMonth); %>
           <gsmsg:write key="cmn.setting.do" /><br>
           <gsmsg:write key="bbs.bbs030.14" />&nbsp;<bean:write name="bbs030knForm" property="bbs030KeepDateY" /><gsmsg:write key="cmn.year2" /><gsmsg:write key="cmn.months" arg0="<%= strKeepMonth %>"/>
         </logic:equal>
       </td>
     </tr>
     </logic:equal>
   </table>

  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('decision');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backToInput');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>

</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>
