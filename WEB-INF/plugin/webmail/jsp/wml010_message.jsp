<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<script type="text/javascript">
<!--
var msglist_wml010 = (function () {
  //使用するメッセージキーの配列を作成
   var ret = new Array();
   ret['cmn.add'] = '<gsmsg:write key="cmn.add"/>';
   ret['cmn.attach.file'] = '<gsmsg:write key="cmn.attach.file"/>';
   ret['cmn.bcc'] = '<gsmsg:write key="cmn.bcc"/>';
   ret['cmn.cc'] = '<gsmsg:write key="cmn.cc"/>';
   ret['cmn.close'] = '<gsmsg:write key="cmn.close"/>';
   ret['cmn.cancel'] = '<gsmsg:write key="cmn.cancel"/>';
   ret['cmn.content'] = '<gsmsg:write key="cmn.content"/>';
   ret['cmn.create.new'] = '<gsmsg:write key="cmn.create.new"/>';
   ret['cmn.date'] = '<gsmsg:write key="cmn.date"/>';
   ret['cmn.delete'] = '<gsmsg:write key="cmn.delete"/>';
   ret['cmn.delete.all'] = '<gsmsg:write key="cmn.delete.all"/>';
   ret['cmn.draft'] = '<gsmsg:write key="cmn.draft"/>';
   ret['cmn.draft2'] = '<gsmsg:write key="cmn.draft2"/>';
   ret['cmn.forward'] = '<gsmsg:write key="cmn.forward"/>';
   ret['cmn.from'] = '<gsmsg:write key="cmn.from"/>';
   ret['cmn.group'] = '<gsmsg:write key="cmn.group"/>';
   ret['cmn.sel.all.group'] = '<gsmsg:write key="cmn.sel.all.group"/>';
   ret['cmn.label'] = '<gsmsg:write key="cmn.label"/>';
   ret['cmn.loading'] = '<gsmsg:write key="cmn.loading"/>';
   ret['cmn.next'] = '<gsmsg:write key="cmn.next"/>';
   ret['cmn.next.page'] = '<gsmsg:write key="cmn.next.page"/>';
   ret['cmn.ok'] = '<gsmsg:write key="cmn.ok"/>';
   ret['cmn.previous'] = '<gsmsg:write key="cmn.previous"/>';
   ret['cmn.previous.page'] = '<gsmsg:write key="cmn.previous.page"/>';
   ret['cmn.private'] = '<gsmsg:write key="cmn.private"/>';
   ret['cmn.receive'] = '<gsmsg:write key="cmn.receive"/>';
   ret['cmn.receive3'] = '<gsmsg:write key="cmn.receive3"/>';
   ret['cmn.register.copy.new'] = '<gsmsg:write key="cmn.register.copy.new"/>';
   ret['cmn.reply'] = '<gsmsg:write key="cmn.reply"/>';
   ret['cmn.search.result'] = '<gsmsg:write key="cmn.search.result"/>';
   ret['cmn.sent'] = '<gsmsg:write key="cmn.sent"/>';
   ret['cmn.sent3'] = '<gsmsg:write key="cmn.sent3"/>';
   ret['cmn.select'] = '<gsmsg:write key="cmn.select"/>';
   ret['cmn.sendfrom'] = '<gsmsg:write key="cmn.sendfrom"/>';
   ret['cmn.size'] = '<gsmsg:write key="cmn.size"/>';
   ret['cmn.subject'] = '<gsmsg:write key="cmn.subject"/>';
   ret['cmn.trash'] = '<gsmsg:write key="cmn.trash"/>';
   ret['cmn.undo'] = '<gsmsg:write key="cmn.undo"/>';
   ret['cmn.user'] = '<gsmsg:write key="cmn.subject"/>';
   ret['cmn.phone'] = '<gsmsg:write key="cmn.phone"/>';
   ret['sml.67'] = '<gsmsg:write key="sml.67"/>';
   ret['sml.190'] = '<gsmsg:write key="sml.190"/>';
   ret['sml.191'] = '<gsmsg:write key="sml.191"/>';
   ret['sml.192'] = '<gsmsg:write key="sml.192"/>';
   ret['sml.196'] = '<gsmsg:write key="sml.196"/>';
   ret['sml.198'] = '<gsmsg:write key="sml.198"/>';
   ret['sml.199'] = '<gsmsg:write key="sml.199"/>';
   ret['sml.201'] = '<gsmsg:write key="sml.201"/>';
   ret['sml.202'] = '<gsmsg:write key="sml.202"/>';
   ret['sml.203'] = '<gsmsg:write key="sml.203"/>';
   ret['sml.204'] = '<gsmsg:write key="sml.204"/>';
   ret['sml.205'] = '<gsmsg:write key="sml.205"/>';
   ret['sml.206'] = '<gsmsg:write key="sml.206"/>';
   ret['sml.207'] = '<gsmsg:write key="sml.207"/>';
   ret['sml.208'] = '<gsmsg:write key="sml.208"/>';
   ret['sml.209'] = '<gsmsg:write key="sml.209"/>';
   ret['sml.210'] = '<gsmsg:write key="sml.210"/>';
   ret['sml.211'] = '<gsmsg:write key="sml.211"/>';
   ret['sml.212'] = '<gsmsg:write key="sml.212"/>';
   ret['sml.213'] = '<gsmsg:write key="sml.213"/>';
   ret['sml.214'] = '<gsmsg:write key="sml.214"/>';
   ret['sml.215'] = '<gsmsg:write key="sml.215"/>';
   ret['sml.218'] = '<gsmsg:write key="sml.218"/>';
   ret['sml.219'] = '<gsmsg:write key="sml.219"/>';
   ret['sml.222'] = '<gsmsg:write key="sml.222"/>';
   ret['sml.223'] = '<gsmsg:write key="sml.223"/>';
   ret['sml.224'] = '<gsmsg:write key="sml.224"/>';
   ret['sml.225'] = '<gsmsg:write key="sml.225"/>';
   ret['sml.226'] = '<gsmsg:write key="sml.226"/>';
   ret['sml.227'] = '<gsmsg:write key="sml.227"/>';
   ret['sml.228'] = '<gsmsg:write key="sml.228"/>';
   ret['sml.229'] = '<gsmsg:write key="sml.229"/>';
   ret['sml.sml010.13'] = '<gsmsg:write key="sml.sml010.13"/>';
   ret['sml.sml010.14'] = '<gsmsg:write key="sml.sml010.14"/>';
   ret['sml.sml010.15'] = '<gsmsg:write key="sml.sml010.15"/>';
   ret['sml.sml010.16'] = '<gsmsg:write key="sml.sml010.16"/>';
   ret['sml.sml010.17'] = '<gsmsg:write key="sml.sml010.17"/>';
   ret['sml.sml010.18'] = '<gsmsg:write key="sml.sml010.18"/>';
   ret['sml.sml010.19'] = '<gsmsg:write key="sml.sml010.19"/>';
   ret['sml.sml010.20'] = '<gsmsg:write key="sml.sml010.20"/>';
   ret['sml.sml010.21'] = '<gsmsg:write key="sml.sml010.21"/>';
   ret['sml.js.1'] = '<gsmsg:write key="sml.js.1"/>';

   ret['cmn.attached'] = '<gsmsg:write key="cmn.attached"/>';
   ret['cmn.check'] = '<gsmsg:write key="cmn.check"/>';
   ret['cmn.move'] = '<gsmsg:write key="cmn.move"/>';
   ret['cmn.read.yet'] = '<gsmsg:write key="cmn.read.yet"/>';
   ret['cmn.save.draft'] = '<gsmsg:write key="cmn.save.draft"/>';
   ret['cmn.share'] = '<gsmsg:write key="cmn.share"/>';
   ret['cmn.strage'] = '<gsmsg:write key="cmn.strage"/>';

   ret['cir.26'] = '<gsmsg:write key="cir.26"/>';
   ret['cir.allTmep.download'] = '<gsmsg:write key="cir.allTmep.download"/>';

   ret['wml.js.40'] = '<gsmsg:write key="wml.js.40"/>';
   ret['wml.js.72'] = '<gsmsg:write key="wml.js.72"/>';
   ret['wml.js.84'] = '<gsmsg:write key="wml.js.84"/>';
   ret['wml.js.createmail'] = '<gsmsg:write key="wml.js.createmail"/>';

   ret['cmn.sendfrom'] = '<gsmsg:write key="cmn.sendfrom"/>';
   ret['wml.failed.addlabel'] = '<gsmsg:write key="wml.failed.addlabel"/>';
   ret['wml.failed.changemail'] = '<gsmsg:write key="wml.failed.changemail"/>';
   ret['wml.failed.deletemailfile'] = '<gsmsg:write key="wml.failed.deletemailfile"/>';
   ret['wml.failed.deletemail'] = '<gsmsg:write key="wml.failed.deletemail"/>';
   ret['wml.failed.movemail'] = '<gsmsg:write key="wml.failed.movemail"/>';
   ret['wml.failed.storemail'] = '<gsmsg:write key="wml.failed.storemail"/>';
   ret['wml.failed.treeinfo'] = '<gsmsg:write key="wml.failed.treeinfo"/>';
   ret['wml.plz.select.mail'] = '<gsmsg:write key="wml.plz.select.mail"/>';

   ret['wml.19'] = '<gsmsg:write key="wml.19"/>';
   ret['wml.150'] = '<gsmsg:write key="wml.150"/>';
   ret['wml.211'] = '<gsmsg:write key="wml.211"/>';
   ret['wml.241'] = '<gsmsg:write key="wml.241"/>';
   ret['wml.276'] = '<gsmsg:write key="wml.276"/>';

   ret['wml.wml010.16'] = '<gsmsg:write key="wml.wml010.16"/>';
   ret['wml.wml010.20'] = '<gsmsg:write key="wml.wml010.20"/>';
   ret['wml.wml010.34'] = '<gsmsg:write key="wml.wml010.34"/>';
   ret['wml.wml010.36'] = '<gsmsg:write key="wml.wml010.36"/>';
   ret['wml.wml010.37'] = '<gsmsg:write key="wml.wml010.37"/>';

   ret['anp.anp090.03'] = '<gsmsg:write key="anp.anp090.03"/>';
   ret['mobile.39'] = '<gsmsg:write key="mobile.39"/>';
   ret['zsk.10'] = '<gsmsg:write key="zsk.10"/>';

   return ret;
})();
-->
</script>