$(function() {
    /* メール 行  hover */
    $('.js_listHover').live({
        mouseenter:function (e) {
            $(this).children().addClass("list_content-on");
            $(this).prev().children().addClass("list_content-topBorder");
        },
        mouseleave:function (e) {
            $(this).children().removeClass("list_content-on");
            $(this).prev().children().removeClass("list_content-topBorder");
        }
    });
    $('.js_tableTopCheck-header').live("change",function() {
        changeChk();
    });
    /* hover:click */
    $(".js_listClick").live("click", function(){
        var sid = $(this).parent().data("sid");
        editMsg(sid);
    });
});

function changePage1() {
    document.forms[0].CMD.value='research';
    for (i = 0; i < document.forms[0].man320SltPage1.length; i++) {
        if (document.forms[0].man320SltPage1[i].selected) {
            document.forms[0].man320SltPage2.value=document.forms[0].man320SltPage1[i].value;
            document.forms[0].man320PageNum.value=document.forms[0].man320SltPage1[i].value;
        }
    }
    document.forms[0].submit();
    return false;
}

function changePage2() {
    document.forms[0].CMD.value='research';
    for (i = 0; i < document.forms[0].man320SltPage2.length; i++) {
        if (document.forms[0].man320SltPage2[i].selected) {
            document.forms[0].man320SltPage1.value=document.forms[0].man320SltPage2[i].value;
            document.forms[0].man320PageNum.value=document.forms[0].man320SltPage2[i].value;
        }
    }
    document.forms[0].submit();
    return false;
}

function onTitleLinkSubmit(fid, order) {
    document.forms[0].CMD.value='sortDetail';
    document.forms[0].man320SortKey.value = fid;
    document.forms[0].man320OrderKey.value = order;
    document.forms[0].submit();
    return false;
}

function addMsg() {
    document.forms[0].CMD.value='man320add';
    document.forms[0].cmd.value='add';
    document.forms[0].submit();
    return false;
}

function editMsg(sid) {
    document.forms[0].CMD.value='man320edit';
    document.forms[0].cmd.value='edit';
    document.forms[0].man320SelectedSid.value = sid;
    document.forms[0].submit();
    return false;
}

function changeChk(){
    var chkFlg;
    if (document.forms[0].allChk.checked) {
        checkAll('selectMsg');
    } else {
        nocheckAll('selectMsg');
    }
}

function checkAll(chkName){
   chkAry = document.getElementsByName(chkName);
   for(i = 0; i < chkAry.length; i++) {
       chkAry[i].checked = true;
   }
}

function nocheckAll(chkName){
   chkAry = document.getElementsByName(chkName);
   for(i = 0; i < chkAry.length; i++) {
       chkAry[i].checked = false;
   }
}

