package jp.groupsession.v2.sml.model;

import java.util.List;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ショートメールの送信情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmailSendModel extends AbstractModel {

    /** 宛先アカウントSID */
    private List<Integer> accountSidList__;
    /** 送信メールSID */
    private int smjSid__;

    /** 送信アカウント */
    private SmlAccountModel sacMdl__;

    /**
     * <p>accountSidList を取得します。
     * @return accountSidList
     */
    public List<Integer> getAccountSidList() {
        return accountSidList__;
    }
    /**
     * <p>accountSidList をセットします。
     * @param accountSidList accountSidList
     */
    public void setAccountSidList(List<Integer> accountSidList) {
        accountSidList__ = accountSidList;
    }
    /**
     * <p>smjSid を取得します。
     * @return smjSid
     */
    public int getSmjSid() {
        return smjSid__;
    }
    /**
     * <p>smjSid をセットします。
     * @param smjSid smjSid
     */
    public void setSmjSid(int smjSid) {
        smjSid__ = smjSid;
    }
    /**
     * 送信アカウントを取得します
     * @return 送信アカウント
     */
    public SmlAccountModel getSacMdl() {
        return sacMdl__;
    }
    /**
     * 送信アカウントをセットします
     * @param sacMdl
     */
    public void setSacMdl(SmlAccountModel sacMdl) {
        sacMdl__ = sacMdl;
    }
    

}