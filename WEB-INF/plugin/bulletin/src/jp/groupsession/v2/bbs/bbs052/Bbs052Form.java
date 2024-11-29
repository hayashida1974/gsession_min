package jp.groupsession.v2.bbs.bbs052;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs010.Bbs010Form;
import jp.groupsession.v2.cmn.GSConstBBS;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 掲示板 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs052Form extends Bbs010Form {

    /** ショートメール通知 */
    private int bbs052smlNtf__ = GSConstBulletin.BBS_SMAIL_TSUUCHI;

    /**
     * <p>bbs052smlNtf を取得します。
     * @return bbs052smlNtf
     */
    public int getBbs052smlNtf() {
        return bbs052smlNtf__;
    }

    /**
     * <p>bbs052smlNtf をセットします。
     * @param bbs052smlNtf bbs052smlNtf
     */
    public void setBbs052smlNtf(int bbs052smlNtf) {
        bbs052smlNtf__ = bbs052smlNtf;
    }
    
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return アクションエラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        
        String bulletin = gsMsg.getMessage("cmn.bulletin");
        String msgKey = "error.input.sml.setting";
        String eprefix = "bbs052.";
        
         // 数値範囲チェック
        if (bbs052smlNtf__ != GSConstBBS.SML_NTF_KBN_YES
                && bbs052smlNtf__ !=  GSConstBBS.SML_NTF_KBN_NO) {
            // 新規投稿時
            String checkObj = gsMsg.getMessage("bbs.bbs052.2");
            msg = new ActionMessage(msgKey, bulletin, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        
        return errors;
    }

}
