<%@page import="jp.groupsession.v2.struts.msg.GsMessage"%>
<%@page import="java.util.HashMap"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

var msglist_rng110_keiro = (function () {
  //使用するメッセージキーの配列を作成
  var ret = new Array();
   ret['rng.rng110.03'] = '<gsmsg:write key="rng.rng110.03"/>';
   ret['rng.rng110.04'] = '<gsmsg:write key="rng.rng110.04"/>';
   ret['rng.rng110.05'] = '<gsmsg:write key="rng.rng110.05"/>';
   ret['rng.rng110.06'] = '<gsmsg:write key="rng.rng110.06"/>';
   ret['rng.rng110.07'] = '<gsmsg:write key="rng.rng110.07"/>';
   ret['rng.rng110.08'] = '<gsmsg:write key="rng.rng110.08"/>';
   ret['cmn.cancel'] = '<gsmsg:write key="cmn.cancel"/>';
   return ret;
})();