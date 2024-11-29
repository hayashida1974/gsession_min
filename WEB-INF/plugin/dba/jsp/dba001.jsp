<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>[Group Session] Database Administrator</title>
<script type="text/javascript" src="../common/js/cmd.js"></script>

<script src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.contextmenu.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../dba/js/dba001.js" charset="utf-8"></script>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../dba/css/dba.css' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body>

<html:form action="/dba/dba001" styleId="dbaForm">
<input type="hidden" name="CMD" id="CMD" value="001_ok">
<input type="hidden" name="dba001SqlString" id="dba001SqlString">
<html:hidden name="dba001Form" property="dba001GenTableName" />

<logic:notEmpty name="dba001Form" property="dba001TableInfos" scope="request">
  <logic:iterate id="tableInfo" name="dba001Form" property="dba001TableInfos" scope="request">
    <input type="hidden" id="<bean:write name="tableInfo" property="name" />_select" value="<bean:write name="tableInfo" property="sqlSelectString" />">
    <input type="hidden" id="<bean:write name="tableInfo" property="name" />_update" value="<bean:write name="tableInfo" property="sqlUpdateString" />">
    <input type="hidden" id="<bean:write name="tableInfo" property="name" />_insert" value="<bean:write name="tableInfo" property="sqlInsertString" />">
    <input type="hidden" id="<bean:write name="tableInfo" property="name" />_delete" value="<bean:write name="tableInfo" property="sqlDeleteString" />">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="dba001Form" property="dba001ViewInfos" scope="request">
  <logic:iterate id="viewInfo" name="dba001Form" property="dba001ViewInfos" scope="request">
    <input type="hidden" id="<bean:write name="viewInfo" property="name" />_select" value="<bean:write name="viewInfo" property="sqlSelectString" />">
  </logic:iterate>
</logic:notEmpty>

<div id="top1" class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../dba/images/classic/header_dba.png" alt="DBA">
      <img class="header_pluginImg" src="../dba/images/original/header_dba.png" alt="DBA">
    </li>
    <li>DBA</li>
    <li>&nbsp;</li>
    <li></li>
  </ul>
</div>

<div class="wrapper_2column">

  <div id="tableList_hide" class="fs_13 display_none">
    <table class="table-left mt0 h100">
    <tr>
      <th class="txt_t p0 cursor_p js_openSideMenuArea">
        <a class="side_headerTitle p0" href="#!" id="tableListOpenBtn">
          <i class="icon-right verAlignMid"></i>
        </a>
      </th>
    </tr>
    </table>
  </div>

  <div id="tableList" class="fs_13 bor_t1 bor_l1 bor_r1" style="width: 200px;">
    <div id="dba_tablelist_area" class="side_header side_header-folding">
      <table class="table-noBorder w99">
      <tr>
        <td class="no_w js_tableListHeadArea cursor_p">
          <span class="side_headerTitle fs_13 dba_header_tablelist">
            テーブル一覧
          </span>
        </td>
        <td class="txt_r">
          <a class="js_tableListBtn side_headerTitle dba_header_tablelist" href="#!" id="tableListHideBtn">
            <i class="icon-left verAlignMid"></i>
          </a>
        </td>
      </tr>
      </table>

    </div>

    <div id="dba_tablelist_child_area" class="side_content js_tablelist_child_area bgC_tableCell ofx_s">
    <div class="m0" id="js_treeListArea" style="width: 350px;">
      <div id="dba_tablelist_tree_area" class="mb0">
        <div class=" mb0 pb0">
          <div class="original-display"></div>
          <div class="side_folderImg cl_webIcon verAlignMid js_lineToggle side_folderImg-lineMinusBottom2"></div>
          <div class="side_folderImg side_folderImg-label ml0 "></div>
          <div class="side-folderText" id="table_top">TABLE</div>
        </div>
        <logic:notEmpty name="dba001Form" property="dba001TableInfos" scope="request">
          <bean:define id="idxList" name="dba001Form" property="dba001TableInfos" type="java.util.List" />

          <div id="tablelist_area">
            <logic:iterate id="tableInfo" name="dba001Form" property="dba001TableInfos" scope="request" indexId="tblIdx">
              <%
                String leftLineClass = "side_folderImg-line";
                if (idxList.size() - 1 == tblIdx) {
                    leftLineClass = "side_folderImg-lineBottom";
                }
              %>

              <div class="side_folder-focus">
                <div class="display_inline">
                  <div class="classic-display ml10 mr0">
                    <img src="../common/images/classic/icon_left_line_pertical.png">
                  </div>
                  <div class="js_menu_tablelist" id="<bean:write name="tableInfo" property="name" />">
                    <div class="original-display wp20 hp25 side_folderImg"></div>
                    <div class="<%= leftLineClass %> verAlignMid side_folderImg"></div>
                    <div class="side-folderText">
                      <a href="#" onClick="selectData('<bean:write name="tableInfo" property="name" />')"><bean:write name="tableInfo" property="name" /></a>
                    </div>
                  </div>

                </div>
                <br>
              </div>
            </logic:iterate>
          </div>
        </logic:notEmpty>
      </div>

      <div>
        <div class="mt0 mb0">
          <div class="original-display"></div>
          <div class="side_folderImg cl_webIcon verAlignMid js_lineToggle side_folderImg-lineMinusBottom2"></div>
          <div class="side_folderImg side_folderImg-label ml0 "></div>
          <div class="side-folderText" id="table_top">VIEW</div>
        </div>

        <logic:notEmpty name="dba001Form" property="dba001ViewInfos" scope="request">
          <bean:define id="idxList" name="dba001Form" property="dba001ViewInfos" type="java.util.List" />

          <div id="viewlist_area">
            <logic:iterate id="tableInfo" name="dba001Form" property="dba001ViewInfos" scope="request" indexId="viewIdx">
              <%
                String leftLineClass = "side_folderImg-line";
                if (idxList.size() - 1 == viewIdx) {
                    leftLineClass = "side_folderImg-lineBottom";
                }
              %>

              <div class="side_folder-focus">
                <div class="display_inline">
                  <div class="classic-display ml10 mr0">
                    <img src="../common/images/classic/icon_left_line_pertical.png">
                  </div>

                  <div class="original-display wp20 hp25 side_folderImg"></div>
                  <div class="<%= leftLineClass %> verAlignMid side_folderImg"></div>
                  <div class="side-folderText">
                    <a href="#" onClick="selectData('<bean:write name="tableInfo" property="name" />')"><bean:write name="tableInfo" property="name" /></a>
                  </div>
                </div>
              </div>
            </logic:iterate>
          </div>

        </logic:notEmpty>

      </div>

      <div>
        <div class="mb0 pb0">
          <div class="original-display"></div>
          <div class="side_folderImg cl_webIcon verAlignMid js_lineToggle side_folderImg-lineMinusBottom2"></div>
          <div class="side_folderImg side_folderImg-label ml0 "></div>
          <div class="side-folderText" id="sequence_top">SEQUENCE</div>
        </div>
        <div id="sequencelist_area">

          <div class="side_folder-focus">
            <div class="display_inline">
              <div class="classic-display ml10 mr0">
                <img src="../common/images/classic/icon_left_line_pertical.png">
              </div>

              <% String sequenceLeftLineClass = "side_folderImg-line"; %>
              <logic:empty name="dba001Form" property="dba001SeqInfos" scope="request">
                <% sequenceLeftLineClass = "side_folderImg-lineBottom"; %>
              </logic:empty>

              <div class="original-display wp20 hp25 side_folderImg"></div>
              <div class="<%= sequenceLeftLineClass %> verAlignMid side_folderImg"></div>
              <div class="side-folderText">
                <a href='#' onClick='selectSeq()'>全てのシーケンス</a>
              </div>
            </div>
            <br>
          </div>

          <logic:notEmpty name="dba001Form" property="dba001SeqInfos" scope="request">
            <bean:define id="idxList" name="dba001Form" property="dba001SeqInfos" type="java.util.List" />

            <logic:iterate id="tableInfo" name="dba001Form" property="dba001SeqInfos" scope="request" indexId="sequenceIdx">
              <%
                String leftLineClass = "side_folderImg-line";
                if (idxList.size() - 1 == sequenceIdx) {
                    leftLineClass = "side_folderImg-lineBottom";
                }
              %>

              <div class="side_folder-focus">
                <div class="display_inline">
                  <div class="classic-display ml10 mr0">
                    <img src="../common/images/classic/icon_left_line_pertical.png">
                  </div>
                  <div class="original-display wp20 hp25 side_folderImg"></div>
                  <div class="<%= leftLineClass %> verAlignMid side_folderImg"></div>
                  <div class="side-folderText">
                    <a href="#" onClick="selectSeq('<bean:write name="tableInfo" property="dbaName" />')"><bean:write name="tableInfo" property="dbaName" /></a>
                  </div>
                </div>
                <br>
              </div>
            </logic:iterate>
          </logic:notEmpty>
        </div>
      </div>

      <div>
        <div class="mb0 pb0">
          <div class="original-display"></div>
          <logic:notEmpty name="dba001Form" property="dba001IndexInfos" scope="request">
            <div class="side_folderImg cl_webIcon verAlignMid js_lineToggle side_folderImg-lineMinusBottom2"></div>
          </logic:notEmpty>
          <logic:empty name="dba001Form" property="dba001IndexInfos" scope="request">
            <div class="side_folderImg cl_webIcon verAlignMid"></div>
          </logic:empty>
          <div class="side_folderImg side_folderImg-label ml0 "></div>
          <div class="side-folderText" id="index_top">INDEX</div>
        </div>
        <div id="indexlist_area">
          <logic:notEmpty name="dba001Form" property="dba001IndexInfos" scope="request">
            <bean:define id="idxList" name="dba001Form" property="dba001IndexInfos" type="java.util.List" />
            <logic:iterate id="tableInfo" name="dba001Form" property="dba001IndexInfos" scope="request" indexId="indexIdx">
              <%
                String leftLineClass = "side_folderImg-line";
                if (idxList.size() - 1 == indexIdx) {
                    leftLineClass = "side_folderImg-lineBottom";
                }
              %>

              <div class="side_folder-focus">
                <div class="display_inline">
                  <div class="classic-display ml10 mr0">
                    <img src="../common/images/classic/icon_left_line_pertical.png">
                  </div>

                  <div>
                    <div class="original-display wp20 hp25 side_folderImg"></div>
                    <div class="<%= leftLineClass %> verAlignMid side_folderImg"></div>
                    <div class="side-folderText">
                      <a href="#" onClick="selectIndex('<bean:write name="tableInfo" property="indexName" />')"><bean:write name="tableInfo" property="indexName" /></a>
                    </div>
                  </div>

                </div>
                <br>
              </div>
            </logic:iterate>
          </logic:notEmpty>
        </div>
      </div>

      <div>
        <div class="verAlignMid">
          <div class="original-display"></div>
          <div class="side_folderImg cl_webIcon verAlignMid js_lineToggle side_folderImg-lineMinusBottom"></div>
          <div class="side_folderImg side_folderImg-label ml0 "></div>
          <div class="side-folderText" id="view_top">SETTING</div>
        </div>
        <div id="sequencelist_area">
          <div class="side_folder-focus js_file_hover">
            <div class="wp20 hp25 side_folderImg"></div>
            <div class="side_folderImg-lineBottom verAlignMid side_folderImg"></div>
            <div class="side-folderText">
              <a href='#' onClick='selectSetting();'>DB設定情報</a>
            </div>
          </div>
        </div>
      </div>

    </div>

    <div class="contextMenu" id="context_tablelist_menu">
      <ul class="wp130">
        <li id='content_select' class="fs_11 no_w">select文を生成</li>
        <li id='content_update' class="fs_11 no_w">update文を生成</li>
        <li id='content_insert' class="fs_11 no_w">insert文を生成</li>
        <li id='content_delete' class="fs_11 no_w">delete文を生成</li>
        <li id='content_dao' class="fs_11 no_w">daoModelを生成(zip)</li>
      </ul>
    </div>
  </div>
  </div>

  <div id="resizeArea" style="cursor: w-resize;">
    <table class="table-left mt0 h100 border_none" style="width: 2px;">
    <tr class="border_none">
      <td class="txt_c txt_m p0 border_none"></td>
    </tr>
    </table>
  </div>

  <div id="doSqlArea" style="width: calc(100% - 195px);" class="ml5">

    <div id="tableSqlBlock" class="dba_header_dosql">
      <div id="tableSql_area" class="side_header side_header-folding w100 bor_t1 bor_l1 bor_r1 js_tableSqlHeadArea cursor_p txt_m">
        <span class="side_headerTitle pl5 fs_13 m0">SQL実行</span>
        <div class="txt_r flo_r">
          <a class="side_headerTitle" href="#!" id="tableSqlHideBtn">
            <i class="icon-up verAlignMid"></i>
          </a>
          <a class="side_headerTitle display_none" href="#!" id="tableSqlOpenBtn">
            <i class="icon-down verAlignMid"></i>
          </a>
        </div>
      </div>
      <div id="tableSql_child_area">
        <textarea id="dba001SqlStringTextArea" class="w100 hp200 dba_textarea borC_light"></textarea>
        <button type="button" class="baseBtn" name="btn_execute" value="実 行" onClick="doSql();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png">
          実 行
        </button>
      </div>

    </div>

    <div id="resultData_area" class="table_title-color mt10 pl5 pt5 pb5 bor_t1 bor_l1 bor_r1 bor_b1">
      <span class="side_headerTitle pl5 fs_13 m0">
        実行結果
      </span>
    </div>
    <div id="resultData_child_area">
    </div>
  </div>

</div>


</html:form>
</body>
</html:html>