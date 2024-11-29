package jp.groupsession.v2.bbs.bbs160;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs110.Bbs110Form;
import jp.groupsession.v2.cmn.GSConstBBS;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 管理者設定 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs160Form extends Bbs110Form {
    /** ショートメール通知 */
    private int bbs160smlNtf__ = GSConstBulletin.BAC_SML_NTF_USER;
    /** ショートメール通知区分 */
    private int bbs160smlNtfKbn__ = GSConstBulletin.BAC_SML_NTF_KBN_YES;
    /**
     * <p>bbs160smlNtf を取得します。
     * @return bbs160smlNtf
     */
    public int getBbs160smlNtf() {
        return bbs160smlNtf__;
    }
    /**
     * <p>bbs160smlNtf をセットします。
     * @param bbs160smlNtf bbs160smlNtf
     */
    public void setBbs160smlNtf(int bbs160smlNtf) {
        bbs160smlNtf__ = bbs160smlNtf;
    }
    /**
     * <p>bbs160smlNtfKbn を取得します。
     * @return bbs160smlNtfKbn
     */
    public int getBbs160smlNtfKbn() {
        return bbs160smlNtfKbn__;
    }
    /**
     * <p>bbs160smlNtfKbn をセットします。
     * @param bbs160smlNtfKbn bbs160smlNtfKbn
     */
    public void setBbs160smlNtfKbn(int bbs160smlNtfKbn) {
        bbs160smlNtfKbn__ = bbs160smlNtfKbn;
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
        String eprefix = "bbs160.";
        
         // 数値範囲チェック
        if (bbs160smlNtf__ != GSConstBBS.SMAIL_SEND_KBN_ADMIN
                && bbs160smlNtf__ !=  GSConstBBS.SMAIL_SEND_KBN_USER) {
            // ショートメール通知
            String checkObj = gsMsg.getMessage("shortmail.notification");
            msg = new ActionMessage(msgKey, bulletin, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        if (bbs160smlNtfKbn__ != GSConstBBS.SML_NTF_KBN_YES
                && bbs160smlNtfKbn__ !=  GSConstBBS.SML_NTF_KBN_NO) {
            // 新規投稿時
            String checkObj = gsMsg.getMessage("bbs.bbs160.1");
            msg = new ActionMessage(msgKey, bulletin, checkObj);
            StrutsUtil.addMessage(errors, msg, eprefix + checkObj);
        }
        
        return errors;
    }
}
