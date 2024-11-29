<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<% jp.co.sjts.util.date.UDate now = new jp.co.sjts.util.date.UDate(); %>
<html:html>
<head></head>
<body onload="Realdate();">

<div id="clock" class="mt10">
  <div>

    <% boolean manStsFlg = true; %>
    <logic:notEqual name="man001Form" property="man001mainStatus" value="1">
      <% manStsFlg = false; %>
    </logic:notEqual>

    <% if (manStsFlg) { %><a href="http://biz.gs.sjts.co.jp/worldclock/clock.html" target="_blank"><% } %>
    <p class="clock_year"><span id="clock-year"></span></p>
    <p class="clock_time"><span id="clock-day"></span><span id="clock-time"></span></p>
    <% if (manStsFlg) { %></a><% } %>
  </div>
</div>

<bean:define id="stringMessage" value="こんばんは" />

</body>
</html:html>
<!-- Day and Time -->
<script language="JavaScript">
<!--

timerID = 0;

<% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
<% String sunday = gsMsg.getMessage(request, "cmn.sunday"); %>
<% String monday = gsMsg.getMessage(request, "cmn.Monday"); %>
<% String tuesday = gsMsg.getMessage(request, "cmn.tuesday"); %>
<% String wednesday = gsMsg.getMessage(request, "cmn.wednesday"); %>
<% String thursday = gsMsg.getMessage(request, "cmn.thursday"); %>
<% String friday = gsMsg.getMessage(request, "cmn.friday"); %>
<% String saturday = gsMsg.getMessage(request, "cmn.saturday"); %>

var dayarray=new Array('<%= sunday %>',
                       '<%= monday %>',
                       '<%= tuesday %>',
                       '<%= wednesday %>',
                       '<%= thursday %>',
                       '<%= friday %>',
                       '<%= saturday %>');

//サーバ時間
var serverdate = new Date(<%= now.getYear() %>, <%= now.getMonth() %>-1, <%= now.getIntDay() %>, <%= now.getIntHour() %>, <%= now.getIntMinute() %>, <%= now.getIntSecond() %>);
//サーバ時間(ミリ秒)
var servertime = serverdate.getTime();

//表示開始時間
var start = new Date();

function Realdate() {

    //ローカル現在時間
    var nowdate = new Date();
    //経過時間 (現在時間と表示開始時間の差)
    var keika = parseInt((nowdate.getTime() - start.getTime()));

    //表示時間 (サーバ時間 + 経過時間)
    var showdate = new Date();
    showdate.setTime(servertime + keika);

    //年
    var fyear=showdate.getFullYear();
    //月
    var month=showdate.getMonth()+1;
    //日
    var daym=showdate.getDate();
    //曜日
    var day=showdate.getDay();

    //表示文字列 月/日(曜)
    var rday = month+"/"+daym+"("+dayarray[day]+") ";
    //表示文字列 時:分
    (showdate.getHours()<10)?M="0"+showdate.getHours():M=showdate.getHours();
    (showdate.getMinutes()<10)?S="0"+showdate.getMinutes():S=showdate.getMinutes();
    var rtime = M+":"+S;

    //表示文字列セット
    document.getElementById("clock-year").innerHTML = fyear;
    document.getElementById("clock-day").innerHTML = rday;
    document.getElementById("clock-time").innerHTML = rtime;

    clearTimeout(timerID);
    timerID = setTimeout("Realdate()",1000*60);
}

//10秒毎に再セット
window.setInterval("Realdate()", 1000*10);
Realdate();
// -->
</script>

