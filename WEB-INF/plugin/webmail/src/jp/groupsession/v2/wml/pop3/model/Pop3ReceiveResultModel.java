package jp.groupsession.v2.wml.pop3.model;

import java.io.Serializable;

/**
 * <br>[機  能] メール受信処理の結果に関する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Pop3ReceiveResultModel implements Serializable {
    /** コミットを実行したか */
    private boolean commitFlg__ = false;
    /** 受信または登録に失敗したメールが存在するか */
    private boolean errMailFlg__ = false;
    /** 添付ファイルをダミーファイルで登録したメールが存在するか */
    private boolean dammyAttachMail__ = false;
    /**
     * <p>commitFlg を取得します。
     * @return commitFlg
     */
    public boolean isCommitFlg() {
        return commitFlg__;
    }
    /**
     * <p>commitFlg をセットします。
     * @param commitFlg commitFlg
     */
    public void setCommitFlg(boolean commitFlg) {
        commitFlg__ = commitFlg;
    }
    /**
     * <p>errMailFlg を取得します。
     * @return errMailFlg
     */
    public boolean isErrMailFlg() {
        return errMailFlg__;
    }
    /**
     * <p>errMailFlg をセットします。
     * @param errMailFlg errMailFlg
     */
    public void setErrMailFlg(boolean errMailFlg) {
        errMailFlg__ = errMailFlg;
    }
    /**
     * <p>dammyAttachMail を取得します。
     * @return dammyAttachMail
     * @see jp.groupsession.v2.wml.pop3.model.Pop3ReceiveResultModel#dammyAttachMail__
     */
    public boolean isDammyAttachMail() {
        return dammyAttachMail__;
    }
    /**
     * <p>dammyAttachMail をセットします。
     * @param dammyAttachMail dammyAttachMail
     * @see jp.groupsession.v2.wml.pop3.model.Pop3ReceiveResultModel#dammyAttachMail__
     */
    public void setDammyAttachMail(boolean dammyAttachMail) {
        dammyAttachMail__ = dammyAttachMail;
    }
}
