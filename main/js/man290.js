function buttonPush(cmd){
    document.forms[0].CMD.value=cmd;
    document.forms[0].submit();
    return false;
}

function buttonPush2(cmd, mod){
    document.forms[0].CMD.value=cmd;
    document.forms[0].cmd.value=mod;
    document.forms[0].submit();
    return false;
}
function more(){
  if($('#moreSettings').hasClass("display_n")){
    $('#moreSettings').removeClass("display_n");
    $('.js_eventTr').removeClass("border_top_none");
    //通常設定のみ表示
    $('#more')[0].innerHTML="＜＜" + msglist.usualDisp;
    document.forms[0].man290elementKbn.value = 1;
    setDisabled();
  } else {
        $('#moreSettings').addClass("display_n");
        $('.js_eventTr').addClass("border_top_none");
    //詳細設定を表示する
    $('#more')[0].innerHTML="＞＞" + msglist.detailDisp;
    document.forms[0].man290ExtKbn[0].checked=true;
    document.forms[0].man290elementKbn.value = 0;
    setDisabled();
  }
}

function moreRe(){
  if(($('#moreSettings').hasClass("display_n")) && document.forms[0].man290elementKbn.value == 1){
        $('#moreSettings').removeClass("display_n");
    //通常設定のみ表示
    $('#more')[0].innerHTML="＜＜" + msglist.usualDisp;;
  } else {
        $('#moreSettings').addClass("display_n");
    //詳細設定を表示する
    $('#more')[0].innerHTML="＞＞" + msglist.detailDisp;
  }
}




function selectGroup(cmd){
    document.forms[0].CMD.value=cmd;
    document.forms[0].submit();
    return false;
}

function setDisabled(value) {

    if (document.forms[0].man290ExtKbn[1].checked == true) {
        $(".js_dayCheck").removeClass("js_tableTopCheck");
        $(".js_dayCheck").addClass("js_tableTopCheck");
        document.forms[0].man290Dweek[0].disabled=false;
        document.forms[0].man290Dweek[1].disabled=false;
        document.forms[0].man290Dweek[2].disabled=false;
        document.forms[0].man290Dweek[3].disabled=false;
        document.forms[0].man290Dweek[4].disabled=false;
        document.forms[0].man290Dweek[5].disabled=false;
        document.forms[0].man290Dweek[6].disabled=false;
        document.forms[0].man290Week.disabled=true;
        document.forms[0].man290Day.disabled=true;
        document.forms[0].man290Week.value=0;
        document.forms[0].man290Day.value=0;
        document.forms[0].man290HolidayKbn2.disabled=false;
        document.forms[0].man290HolidayKbn3.disabled=false;

    } else if (document.forms[0].man290ExtKbn[2].checked == true) {
        $(".js_dayCheck").removeClass("js_tableTopCheck");
        document.forms[0].man290Dweek[0].disabled=false;
        document.forms[0].man290Dweek[1].disabled=false;
        document.forms[0].man290Dweek[2].disabled=false;
        document.forms[0].man290Dweek[3].disabled=false;
        document.forms[0].man290Dweek[4].disabled=false;
        document.forms[0].man290Dweek[5].disabled=false;
        document.forms[0].man290Dweek[6].disabled=false;
        document.forms[0].man290Week.disabled=false;
        document.forms[0].man290Day.disabled=false;
        document.forms[0].man290HolidayKbn2.disabled=false;
        document.forms[0].man290HolidayKbn3.disabled=false;

        if (document.forms[0].man290Week.value==0) {
            document.forms[0].man290Dweek[0].disabled=true;
            document.forms[0].man290Dweek[1].disabled=true;
            document.forms[0].man290Dweek[2].disabled=true;
            document.forms[0].man290Dweek[3].disabled=true;
            document.forms[0].man290Dweek[4].disabled=true;
            document.forms[0].man290Dweek[5].disabled=true;
            document.forms[0].man290Dweek[6].disabled=true;
            document.forms[0].man290Dweek[0].checked=false;
            document.forms[0].man290Dweek[1].checked=false;
            document.forms[0].man290Dweek[2].checked=false;
            document.forms[0].man290Dweek[3].checked=false;
            document.forms[0].man290Dweek[4].checked=false;
            document.forms[0].man290Dweek[5].checked=false;
            document.forms[0].man290Dweek[6].checked=false;
        } else {
            document.forms[0].man290Dweek[0].disabled=false;
            document.forms[0].man290Dweek[1].disabled=false;
            document.forms[0].man290Dweek[2].disabled=false;
            document.forms[0].man290Dweek[3].disabled=false;
            document.forms[0].man290Dweek[4].disabled=false;
            document.forms[0].man290Dweek[5].disabled=false;
            document.forms[0].man290Dweek[6].disabled=false;
        }
    } else {
        $(".js_dayCheck").removeClass("js_tableTopCheck");
        document.forms[0].man290Dweek[0].disabled=true;
        document.forms[0].man290Dweek[1].disabled=true;
        document.forms[0].man290Dweek[2].disabled=true;
        document.forms[0].man290Dweek[3].disabled=true;
        document.forms[0].man290Dweek[4].disabled=true;
        document.forms[0].man290Dweek[5].disabled=true;
        document.forms[0].man290Dweek[6].disabled=true;
        document.forms[0].man290Week.disabled=true;
        document.forms[0].man290Day.disabled=true;
        document.forms[0].man290Dweek[0].checked=false;
        document.forms[0].man290Dweek[1].checked=false;
        document.forms[0].man290Dweek[2].checked=false;
        document.forms[0].man290Dweek[3].checked=false;
        document.forms[0].man290Dweek[4].checked=false;
        document.forms[0].man290Dweek[5].checked=false;
        document.forms[0].man290Dweek[6].checked=false;
        document.forms[0].man290Week.value=0;
        document.forms[0].man290Day.value=0;
        document.forms[0].man290HolidayKbn2.disabled=true;
        document.forms[0].man290HolidayKbn3.disabled=true;
        if (document.forms[0].man290HolidayKbn2.checked == true || document.forms[0].man290HolidayKbn3.checked == true) {
            document.forms[0].man290HolidayKbn0.checked=true;
        }
    }

}

function changeWeekCombo(){
    if (document.forms[0].man290ExtKbn[2].checked == true) {
        if (document.forms[0].man290Week.value==0) {
            document.forms[0].man290Dweek[0].disabled=true;
            document.forms[0].man290Dweek[1].disabled=true;
            document.forms[0].man290Dweek[2].disabled=true;
            document.forms[0].man290Dweek[3].disabled=true;
            document.forms[0].man290Dweek[4].disabled=true;
            document.forms[0].man290Dweek[5].disabled=true;
            document.forms[0].man290Dweek[6].disabled=true;
            document.forms[0].man290Dweek[0].checked=false;
            document.forms[0].man290Dweek[1].checked=false;
            document.forms[0].man290Dweek[2].checked=false;
            document.forms[0].man290Dweek[3].checked=false;
            document.forms[0].man290Dweek[4].checked=false;
            document.forms[0].man290Dweek[5].checked=false;
            document.forms[0].man290Dweek[6].checked=false;
        } else {
            document.forms[0].man290Dweek[0].disabled=false;
            document.forms[0].man290Dweek[1].disabled=false;
            document.forms[0].man290Dweek[2].disabled=false;
            document.forms[0].man290Dweek[3].disabled=false;
            document.forms[0].man290Dweek[4].disabled=false;
            document.forms[0].man290Dweek[5].disabled=false;
            document.forms[0].man290Dweek[6].disabled=false;
        }
    }
}

function moveDay(elmDate, kbn) {

    systemDate = new Date();
    var year = convYear(systemDate.getFullYear());
    var month = ("0" + (systemDate.getMonth() + 1)).slice(-2);
    var day = ("0" + systemDate.getDate()).slice(-2);

    if (kbn == 2) {
        $(elmDate).val(year + "/" + month + "/" + day);
        return;
    }

    if (kbn == 1 || kbn == 3) {

        var ymdf = escape($(elmDate).val());
        re = new RegExp(/(\d{4})\/(\d{1,2})\/(\d{1,2})/);
        if (ymdf.match(re)) {
            newDate = new Date($(elmDate).val());

            if (kbn == 1) {
                newDate.setDate(newDate.getDate() - 1);
            } else if (kbn == 3) {
                newDate.setDate(newDate.getDate() + 1);
            }

            var newYear = convYear(newDate.getFullYear());
            var systemYear = convYear(systemDate.getFullYear());
            if (newYear < systemYear - 1 || newYear > systemYear + 3) {
                return;
            } else {
                year = newYear;
                month = ("0" + (newDate.getMonth() + 1)).slice(-2);
                day = ("0" + newDate.getDate()).slice(-2);
                $(elmDate).val(year + "/" + month + "/" + day);
            }

        } else {
            if ($(elmDate).val() == '') {
                $(elmDate).val(year + "/" + month + "/" + day);
            }
        }
    }
}

function convYear(yyyy) {
  if(yyyy<1900) {
    yyyy=1900+yyyy;
  }
  return yyyy;
}

function setParams() {

    setYmdParam($("input[name='man290FrDate']"),
        $("input[name='man290FrYear']"), 
        $("input[name='man290FrMonth']"),
        $("input[name='man290FrDay']"));

    setYmdParam($("input[name='man290ToDate']"),
        $("input[name='man290ToYear']"), 
        $("input[name='man290ToMonth']"),
        $("input[name='man290ToDay']"));

    setHmParam($("input[name='man290FrTime']"), $("input[name='man290FrHour']"), $("input[name='man290FrMin']"));
    
    setHmParam($("input[name='man290ToTime']"), $("input[name='man290ToHour']"), $("input[name='man290ToMin']"));

}

$(function() {
    //チェックボックス枠外押下判定
    if ($(window).live) {
        $('.js_tableTopCheck').live("click", function(e){
            if (e.target.type != 'checkbox') {
                var check = $(this).children().children('input[type=checkbox]');
                if (check.attr('checked')) {
                    check.attr('checked',false);
                } else {
                    check.attr('checked',true);
                }
            }
        });
    }
});