package jp.groupsession.v2.sml.restapi.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.GSConstSmail;

/**
 *
 * <br>[機  能] メール情報　ショートメール検索用モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlRestapiMailBodyModel {

    /** メール形式(0:テキスト形式 / 1:HTML形式)*/
    private int    mailType__ = GSConstSmail.SAC_SEND_MAILTYPE_NORMAL;

    /** メール本文*/
    private String mailBody__;

    /** 添付ファイル一覧*/
    private ArrayList<CmnBinfModel> binList__;

    /**
     * <p>mailType を取得します。
     * @return mailType
     * @see jp.groupsession.v2.sml.restapi.model.SmlAccountsMailboxesMailsBodyModel#mailType__
     */
    public int getMailType() {
        return mailType__;
    }

    /**
     * <p>mailType をセットします。
     * @param mailType mailType
     * @see jp.groupsession.v2.sml.restapi.model.SmlAccountsMailboxesMailsBodyModel#mailType__
     */
    public void setMailType(int mailType) {
        mailType__ = mailType;
    }

    /**
     * <p>mailBody を取得します。
     * @return mailBody
     * @see jp.groupsession.v2.sml.restapi.model.SmlAccountsMailboxesMailsBodyModel#mailBody__
     */
    public String getMailBody() {
        return mailBody__;
    }

    /**
     * <p>mailBody をセットします。
     * @param mailBody mailBody
     * @see jp.groupsession.v2.sml.restapi.model.SmlAccountsMailboxesMailsBodyModel#mailBody__
     */
    public void setMailBody(String mailBody) {
        mailBody__ = mailBody;
    }

    /**
     * <p>binList を取得します。
     * @return binList
     * @see jp.groupsession.v2.sml.restapi.model.SmlAccountsMailboxesMailsBodyModel#binList__
     */
    public ArrayList<CmnBinfModel> getBinList() {
        return binList__;
    }

    /**
     * <p>binList をセットします。
     * @param binList binList
     * @see jp.groupsession.v2.sml.restapi.model.SmlAccountsMailboxesMailsBodyModel#binList__
     */
    public void setBinList(ArrayList<CmnBinfModel> binList) {
        binList__ = binList;
    }

    /**
     * <p>binMdl を配列へ追加します。
     * @param binMdl binMdl
     */
    public void addBinModel(CmnBinfModel binMdl) {
        if (binMdl != null) {
            if (binList__ == null) {
                binList__ = new ArrayList<CmnBinfModel>();
            }
            binList__.add(binMdl);
        }
    }
}
