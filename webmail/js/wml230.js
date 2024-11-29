function selectPage(id){
    if (id == 1) {
        $('[name=wml230mailListPageTop]').val($('[name=wml230mailListPageBottom]').val());
    } else {
        $('[name=wml230mailListPageBottom]').val($('[name=wml230mailListPageTop]').val());
    }

    return buttonPush('init');
}

function changeFilterInput() {
    var index;
    for (index = 1; index <= 5; index++) {
        if (getElement('wml230condition' + index).checked) {
            getElement('wml230conditionType' + index).disabled = false;
            getElement('wml230conditionExs' + index).disabled = false;
            getElement('wml230conditionText' + index).disabled = false;
        } else {
            getElement('wml230conditionType' + index).disabled = true;
            getElement('wml230conditionExs' + index).disabled = true;
            getElement('wml230conditionText' + index).disabled = true;
        }
    }
}
function getElement(name) {
    return document.getElementsByName(name)[0];
}

function wml230Sort(sortKey, order) {
    document.getElementsByName('wml230mailListSortKey')[0].value = sortKey;
    document.getElementsByName('wml230mailListOrder')[0].value = order;
    document.forms[0].submit();
    return false;
}

function deleteFwAddress(rowIdx) {
    document.forms['wml230Form'].wml230actionSendValueDelIdx.value=rowIdx;
    return buttonPush('delFwAddress');
}

$(function(){

    /* hover */
    $('.js_mailListHover').live({
        mouseenter:function (e) {
            $(this).children().addClass("list_content-on");
            $(this).prev().children().addClass("list_content-topBorder");
        },
        mouseleave:function (e) {
            $(this).children().removeClass("list_content-on");
            $(this).prev().children().removeClass("list_content-topBorder");
        }
    });

    /* hover:click */
    $(".js_mailListClick").live("click", function(){
        var sid = $(this).parent().data("sid");
        openDetail(sid);
    });
});