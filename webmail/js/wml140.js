function selectPage(id){
    if (id == 1) {
        $('[name=wml140mailListPageTop]').val($('[name=wml140mailListPageBottom]').val());
    } else {
        $('[name=wml140mailListPageBottom]').val($('[name=wml140mailListPageTop]').val());
    }

    return buttonPush('init');
}

function changeFilterInput() {
    var index;
    for (index = 1; index <= 5; index++) {
        if (getElement('wml140condition' + index).checked) {
            getElement('wml140conditionType' + index).disabled = false;
            getElement('wml140conditionExs' + index).disabled = false;
            getElement('wml140conditionText' + index).disabled = false;
        } else {
            getElement('wml140conditionType' + index).disabled = true;
            getElement('wml140conditionExs' + index).disabled = true;
            getElement('wml140conditionText' + index).disabled = true;
        }
    }
}
function getElement(name) {
    return document.getElementsByName(name)[0];
}

function wml140Sort(sortKey, order) {
    document.getElementsByName('wml140mailListSortKey')[0].value = sortKey;
    document.getElementsByName('wml140mailListOrder')[0].value = order;
    document.forms[0].submit();
    return false;
}

function deleteFwAddress(rowIdx) {
    document.forms['wml140Form'].wml140actionSendValueDelIdx.value=rowIdx;
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