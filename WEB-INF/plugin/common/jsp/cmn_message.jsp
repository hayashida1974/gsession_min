<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<script type="text/javascript">
<!--
var msglist_cmn = (function () {
  //使用するメッセージキーの配列を作成
   var ret = new Array();
   ret['cmn.ok'] = '<gsmsg:write key="cmn.ok"/>';
   ret['cmn.error'] = '<gsmsg:write key="cmn.cmn280.02"/>';
   ret['cmn.del.error'] = '<gsmsg:write key="cht.cht010.29"/>';
   ret['cmn.sml.ok'] = '<gsmsg:write key="cmn.cmn280.01"/>';
   ret['cmn.auto.del.ok']='<gsmsg:write key="cmn.cmn300.02"/>';
   ret['cmn.manual.del.ok']='<gsmsg:write key="cmn.cmn310.02"/>';
   ret['cmn.send.receive.log']='<gsmsg:write key="wml.wml170kn.02" />';
   ret['cmn.send.receive.all']='<gsmsg:write key="wml.wml170kn.03" />';
   ret['cmn.del.check']='<gsmsg:write key="cmn.cmn310.03"/>';
   ret['cmn.after']='<gsmsg:write key="cmn.cmn310.04"/>';
   ret['cmn.cancel']='<gsmsg:write key="cmn.cancel"/>'
   ret['cmn.sch'] = '<gsmsg:write key="schedule.108"/>';
   ret['cmn.bbs'] = '<gsmsg:write key="cmn.bulletin"/>';
   ret['cmn.sml'] = '<gsmsg:write key="cmn.shortmail"/>';
   ret['cmn.rsv'] = '<gsmsg:write key="cmn.reserve"/>';
   ret['cmn.ntp'] = '<gsmsg:write key="ntp.1"/>';
   ret['cmn.cir'] = '<gsmsg:write key="cir.5"/>';
   ret['cmn.wml'] = '<gsmsg:write key="wml.wml010.25"/>';
   ret['cmn.fil'] = '<gsmsg:write key="cmn.filekanri"/>';
   ret['cmn.rng'] = '<gsmsg:write key="rng.62"/>';
   ret['cmn.enq'] = '<gsmsg:write key="enq.plugin"/>';
   ret['cmn.cht'] = '<gsmsg:write key="cht.01"/>';
   ret['cmn.mem'] = '<gsmsg:write key="memo.01"/>';
   ret['cmn.all'] = '<gsmsg:write key="cmn.all"/>';
   ret['cmn.act'] = '<gsmsg:write key="cmn.pdf.account"/>';
   ret['cmn.year'] = '<gsmsg:write key="cmn.year2"/>';
   ret['cmn.month'] = '<gsmsg:write key="cmn.months2"/>';
   ret['cmn.day'] = '<gsmsg:write key="cmn.day"/>';
   ret['cmn.send'] = '<gsmsg:write key="cmn.sent"/>';
   ret['cmn.receive'] = '<gsmsg:write key="cmn.receive"/>';
   ret['cmn.draft'] = '<gsmsg:write key="cmn.draft"/>';
   ret['cmn.strage'] = '<gsmsg:write key="cmn.strage"/>';
   ret['cmn.trash'] = '<gsmsg:write key="cmn.trash"/>';
   ret['cmn.comp'] = '<gsmsg:write key="cmn.complete"/>';
   ret['cmn.enq.send'] = '<gsmsg:write key="enq.05"/>';
   ret['cmn.wml.send'] = '<gsmsg:write key="wml.19"/>';
   ret['cmn.wml.receive'] = '<gsmsg:write key="wml.37"/>';
   ret['cmn.rng.apply'] = '<gsmsg:write key="rng.48"/>';

   ret['cmn.search'] = '<gsmsg:write key="cmn.search"/>';
   ret['cmn.close'] = '<gsmsg:write key="cmn.close"/>';
   ret['cmn.delete'] = '<gsmsg:write key="cmn.delete"/>';
   ret['cmn.next'] = '<gsmsg:write key="cmn.next"/>';
   ret['cmn.next.page'] = '<gsmsg:write key="cmn.next.page"/>';
   ret['cmn.previous'] = '<gsmsg:write key="cmn.previous"/>';
   ret['cmn.previous.page'] = '<gsmsg:write key="cmn.previous.page"/>';
   ret['sml.sml240.01'] = '<gsmsg:write key="sml.sml240.01" />';
   ret['cmn.mailaddress'] = '<gsmsg:write key="cmn.mailaddress" />';
   ret['cmn.employer'] = '<gsmsg:write key="cmn.employer" />';
   ret['sml.217'] = '<gsmsg:write key="sml.217" />';
   ret['cmn.received.date'] = '<gsmsg:write key="cmn.received.date" />';
   ret['cmn.memo'] = '<gsmsg:write key="cmn.memo" />';
   ret['sml.230'] = '<gsmsg:write key="sml.230" />';

  return ret;
})();
-->
</script>