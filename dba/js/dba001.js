/* 左メニュー TABLE開閉フラグ */
var left_menu_table_opnkbn = true;
/* 左メニュー VIEW開閉フラグ */
var left_menu_view_opnkbn = true;
/* 左メニュー SEQUENCE開閉フラグ */
var left_menu_sequence_opnkbn = true;
/* 左メニュー INDEX開閉フラグ */
var left_menu_index_opnkbn = true;

/* テーブル一覧 実行結果のサイズ変更用変数 */
var headerHeight, tablelistHeight, tablelistHeaderHeight,
    tableSqlHeight, tableSqlHeaderHeight,
    resultDataHeaderHeight,
    tableSqlWidth;


$(function() {

    /* TABLE クリック */
    $("#table_top").live("click", function(){
        $(this).prev().prev().click();

        if (left_menu_table_opnkbn) {
            left_menu_table_opnkbn = false;
        } else {
            left_menu_table_opnkbn = true;
        }
    });

    /* VIEW クリック */
    $("#view_top").live("click", function(){
        $(this).prev().prev().click();

        if (left_menu_view_opnkbn) {
            left_menu_view_opnkbn = false;
        } else {
            left_menu_view_opnkbn = true;
        }
    });

    /* SEQUENCE クリック */
    $("#sequence_top").live("click", function(){
        $(this).prev().prev().click();

        if (left_menu_sequence_opnkbn) {
            left_menu_sequence_opnkbn = false;
        } else {
            left_menu_sequence_opnkbn = true;
        }
    });

    /* SEQUENCE クリック */
    $("#index_top").live("click", function(){
        $(this).prev().prev().click();

        if (left_menu_index_opnkbn) {
            left_menu_index_opnkbn = false;
        } else {
            left_menu_index_opnkbn = true;
        }
    });


    /* プラス、マイナス要素クリック */
    $(".js_lineToggle").live("click", function(){

        $(this).parent().next().toggle('fast');

        if ($(this).hasClass("side_folderImg-linePlusBottom2")) {
            $(this).removeClass("side_folderImg-linePlusBottom2");
            $(this).addClass("side_folderImg-lineMinusBottom2");
        } else if ($(this).hasClass("side_folderImg-lineMinusBottom2")) {
            $(this).removeClass("side_folderImg-lineMinusBottom2");
            $(this).addClass("side_folderImg-linePlusBottom2");
        } else if ($(this).hasClass("side_folderImg-linePlusBottom")) {
            $(this).removeClass("side_folderImg-linePlusBottom");
            $(this).addClass("side_folderImg-lineMinusBottom");
        } else if ($(this).hasClass("side_folderImg-lineMinusBottom")) {
            $(this).removeClass("side_folderImg-lineMinusBottom");
            $(this).addClass("side_folderImg-linePlusBottom");
        }

    });

    /* テーブル一覧エリア 折り畳み */
    $(".js_tableListBtn").live("click", function(){

        if (this.id == 'tableListHideBtn') {
            $("#dba_tablelist_child_area").animate( { height: 'toggle'}, 'middle' );

            $('#tableList').addClass("display_none");
            $('#tableList_hide').removeClass("display_none");

            $('#doSqlArea').removeClass("main");

            $('#dba_tablelist_child_area').removeAttr('style');

            $('#resizeArea').addClass("display_none");
            resizeWindowDBA();

        } else {
            openSideMenu();
        }

    });

    function openSideMenu() {
        $("#dba_tablelist_child_area").animate( { height: 'toggle'}, 'middle' );
        $('#tableList').removeClass("display_none");
        $('#tableList_hide').addClass("display_none");

        $('#doSqlArea').addClass("main");

        $('#dba_tablelist_child_area').removeAttr('style');

        $('#resizeArea').removeClass("display_none");

        resizeWindowDBA();
    }

    $(".js_tableListHeadArea").live("click", function(){
        $('#tableListHideBtn').click();
    });

    $(".js_openSideMenuArea").live("click", function(){
        openSideMenu();
    });

    /* SQL実行エリア クリック */
    $(".js_tableSqlHeadArea").live("click", function(){
        $("#tableSql_child_area").animate( { height: 'toggle'}, 'middle' );

        if ($('#tableSqlHideBtn').prop("class").indexOf('display_none') < 0) {
            $('#tableSqlHideBtn').addClass("display_none");
            $('#tableSqlOpenBtn').removeClass("display_none");
        } else {
            $('#tableSqlHideBtn').removeClass("display_none");
            $('#tableSqlOpenBtn').addClass("display_none");
        }
        resizeResultListHeight(null);
    });

    /* テーブル一覧(TABLE) 右クリック */
    $('.js_menu_tablelist').contextMenu('context_tablelist_menu',
        {
        bindings: {
            'content_select': function(t) {
                setContenxtSQL(t.id, "select");
            },
            'content_update': function(t) {
                setContenxtSQL(t.id, "update");
            },
            'content_insert': function(t) {
                setContenxtSQL(t.id, "insert");
            },
            'content_delete': function(t) {
                setContenxtSQL(t.id, "delete");
            },
            'content_dao': function(t) {
                downloadDaomodel(t.id);
            }
        }
    });

    /** 実行SQL テキストエリアへ指定SQLを設定 */
    function setContenxtSQL(id, sqlType) {
        var sqlstr = document.getElementById(id + "_" + sqlType).value;
        document.getElementById("dba001SqlStringTextArea").value=sqlstr;
    }

    headerHeight = $("#top1").outerHeight(true);
    tablelistHeight = $("#dba_tablelist_area").outerHeight(true);
    tablelistHeaderHeight = headerHeight + tablelistHeight + 10;
    tableSqlHeight = $("#tableSqlBlock").get(0).offsetHeight;
    resultDataHeaderHeight = $("#resultData_area").get(0).offsetHeight;
    tableSqlHeaderHeight = headerHeight + tableSqlHeight + resultDataHeaderHeight + 20;

    resizeChildArea(null, true);
    $(window).bind("resize", resizeWindowDBA);

    jQuery.resizable('resizeArea');

});


windowWidth = $(window).width() - 25;
jQuery.resizable = function (resizerID) {
    jQuery('#' + resizerID).bind("mousedown", function (e) {
        jQuery('body').bind("mouseup", function () {
            jQuery('body').unbind("mousemove");
            jQuery('body').unbind("mouseup");

        });
        jQuery('body').bind("mousemove", function (e) {
            var resizePointX = e.pageX;

            if (resizePointX <= 160 || (windowWidth - resizePointX) < 200) {
                return;
            }
            jQuery('#' + resizerID).prev().width(resizePointX);


            jQuery('#' + resizerID).next().width(windowWidth - resizePointX);

            if (resizePointX > 380) {
                $("#js_treeListArea").css("width", (resizePointX - 30));
            } else {
                $("#js_treeListArea").css("width", '350');
            }

            var tableSqlWidth = $("#tableSqlBlock").outerWidth(true);
            $("#resultData_child_area").css("width", tableSqlWidth);
        });
    });
}

function resizeWindowDBA() {
    windowWidth = $(window).width() - 8;
    if (window.innerHeight < 533) {
        windowWidth -= 17;
    }

    var resizePointX = jQuery('#tableList').width();

    if ($('#tableList').prop('class').indexOf("display_none") >= 0) {
        resizePointX = 10;
    }
    jQuery('#doSqlArea').width(windowWidth - resizePointX);

    resizeChildArea(null, true);
}


function resizeChildArea(e, resultFlg) {
    var windowHeight = $(window).height();
    var tablelistChildHeight = windowHeight - tablelistHeaderHeight;
    if (tablelistChildHeight < 400) {
        tablelistChildHeight = 400;
    }
    $("#dba_tablelist_child_area").css("height", tablelistChildHeight);

    if (resultFlg) {
        resizeResultListHeight(e);
    }

    resizeResultDataChildArea(e);
}

function resizeResultDataChildArea(e) {
    var tableSqlWidth = $("#tableSqlBlock").outerWidth(true);

    var agent = window.navigator.userAgent.toLowerCase();
    $("#resultData_child_area").css("width", tableSqlWidth);

}

function resizeResultListHeight(e) {
    var windowHeight = $(window).height();
    if (windowHeight < 400) {
        windowHeight = 400;
    }

    var resultDataChildHeight = windowHeight - tableSqlHeaderHeight;

    if (resultDataChildHeight < 110) {
        resultDataChildHeight = 110;
    }

    if ($('#tableSqlHideBtn').prop('class').indexOf("display_none") >= 0) {
        resultDataChildHeight += $("#tableSql_child_area").get(0).offsetHeight;
    }

    $("#resultData_child_area").css("height", resultDataChildHeight);
}


function downloadDaomodel(tname){
    document.getElementsByName('dba001Form')[0].CMD.value='001_gen';
    document.getElementsByName('dba001Form')[0].dba001GenTableName.value=tname;
    document.getElementsByName('dba001Form')[0].submit();
}


function doSql() {
  var param = document.getElementById("dba001SqlStringTextArea").value;
  document.getElementById("dba001SqlString").value = param;

  var formObject = document.getElementsByName('dba001Form')[0];
  document.getElementsByName('dba001Form')[0].CMD.value = '001_ok';
  document.getElementsByName('dba001Form')[0].dba001SqlString.value = param;

  var formData = $('#dbaForm').serialize();

  viewLoader();

  //データ取得
  $.ajax({
      async: true,
      url:"../dba/dba002.do",
      type: "post",
      data:formData
  }).done(function( data ) {

      document.getElementById("resultData_child_area").innerHTML = data;

  }).fail(function(data){
  });
}

function selectData(tname) {
  var sqlstr = document.getElementById(tname + "_select").value;
  var array = sqlstr.split("where");
  document.getElementById("dba001SqlStringTextArea").value=array[0];

  var param = document.getElementById("dba001SqlStringTextArea").value;
  document.getElementById("dba001SqlString").value = param;

  displayTable('001_ok',param);
}

function selectSeq(tname) {
  var sqlstr = "select * from INFORMATION_SCHEMA.SEQUENCES where SEQUENCE_NAME = " + "'" + tname + "'";

  if (tname == null) {
    var array = sqlstr.split(" where ");
    document.getElementById("dba001SqlStringTextArea").value=array[0];
  } else {
    document.getElementById("dba001SqlStringTextArea").value=sqlstr;
  }

  var param = document.getElementById("dba001SqlStringTextArea").value;
  document.getElementById("dba001SqlString").value = param;

  displayTable('001_ok',param);
}

function selectSetting() {
  var sqlstr = "select * from INFORMATION_SCHEMA.SETTINGS";

  document.getElementById("dba001SqlStringTextArea").value=sqlstr;

  var param = document.getElementById("dba001SqlStringTextArea").value;
  document.getElementById("dba001SqlString").value = param;

  displayTable('001_ok',param);
}

function selectIndex(tname) {
  var sqlstr = "select * from INFORMATION_SCHEMA.INDEXES where INDEX_NAME = " + "'" + tname + "'";

  if (tname == null) {
  var array = sqlstr.split(" where ");
  document.getElementById("dba001SqlStringTextArea").value=array[0];
  } else {
  document.getElementById("dba001SqlStringTextArea").value=sqlstr;
  }


  var param = document.getElementById("dba001SqlStringTextArea").value;
  document.getElementById("dba001SqlString").value = param;

  displayTable('001_ok',param);
}

function displayTable(CMD, SQL){

  document.getElementsByName('dba001Form')[0].dba001GenTableName.value='';

  viewLoader();

  var paramStr = 'CMD=' + CMD
      + '&dba001SqlString=' + SQL;

  //データ取得
  $.ajax({
      async: true,
      url:"../dba/dba002.do",
      type: "post",
      data: paramStr
  }).done(function( data ) {

      document.getElementById("resultData_child_area").innerHTML = data;

  }).fail(function(data){
  });

}

function viewLoader() {
    document.getElementById("resultData_child_area").innerHTML =
        '<div class="w100 mt20 txt_c">'
        + '<img class="header_pluginImg-classic" src="../common/images/classic/icon_loader.gif" alt="読み込み中">'
        + '<img class="header_pluginImg" src="../common/images/original/ajax-loader.gif" alt="読み込み中">'
        + '</div>';
}
