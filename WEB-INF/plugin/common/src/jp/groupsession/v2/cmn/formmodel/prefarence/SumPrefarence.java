package jp.groupsession.v2.cmn.formmodel.prefarence;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.formmodel.Calc.Format;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
    *
    * <br>[機  能] 自動計算設定用モデル
    * <br>[解  説]
    * <br>[備  考]
    *
    * @author JTS
    */
   public class SumPrefarence extends jp.groupsession.v2.cmn.formmodel.Sum {
       /** フォーム選択用LabelValue*/
       private List<LabelValueBean> select__;
       /**
        * <p>select を取得します。
        * @return select
        */
       public List<LabelValueBean> getSelect() {
           return select__;
       }

       /**
        * <p>select をセットします。
        * @param select select
        */
       public void setSelect(List<LabelValueBean> select) {
           select__ = select;
       }
       @Override
       public void validateCheck(ActionErrors errors, RequestModel reqMdl,
               ValidateInfo info) {
           GsMessage gsMsg = new GsMessage(reqMdl);
           String teisuName = gsMsg.getMessage("cmn.teisu");
           int errorSize = errors.size();
           for (Format format : getTarget()) {
               if (format.getType() == 0) {
                   GSValidateCommon.validateNumberDecimal(errors,
                           format.getValue(),
                           teisuName, 20, true);
                   if (errorSize != errors.size()) {
                       format.setValue("");
                       errorSize = errors.size();
                   } else {
                     //半角チェック
                       if (!Pattern.matches("^-?[0-9]*.?[0-9]+$", format.getValue())) {
                           ActionMessage msg = null;
                           msg = new ActionMessage("error.input.number.hankaku",
                                   gsMsg.getMessage("cmn.form.pref.calc"));
                           StrutsUtil.addMessage(errors, msg, "siki");
                       }
                   }
               }
           }
           GSValidateCommon.validateTextField(errors,
                   getTanni(),
                   "tanni",
                   gsMsg.getMessage("ntp.102"),
                   100,
                   false);

           if (!(getRound() == 0 || getRound() == 1 || getRound() == 2)) {
               ActionMessage msg = null;
               msg = new ActionMessage("error.input.required.text",
                       gsMsg.getMessage("cmn.form.round"));
               StrutsUtil.addMessage(errors, msg, "round");
           }

           int errorsCount = errors.size();
           GSValidateCommon.validateNumberInt(errors,
                   String.valueOf(getKeta()),
                   gsMsg.getMessage("cmn.keta"),
                   1);

           if (errors.size() == errorsCount) {
               int nKeta = Integer.parseInt(getKeta());
               if (nKeta < 0 || nKeta > 5) {
                   ActionMessage msg = null;
                   msg = new ActionMessage("error.input.addhani.text",
                           gsMsg.getMessage("cmn.keta"), "0", "5");
                   StrutsUtil.addMessage(errors, msg, "keta");
               }
           }
       }

   }