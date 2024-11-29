package jp.groupsession.v2.sml.model;


import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.sml.GSConstSmail;

/**
 * <br>[機  能] ショートメールプラグインで共通使用するパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlParamModel extends AbstractParamModel {
    /** 表示アカウント */
    private int smlViewAccount__ = 0;

    /** 編集モード */
    private int smlCmdMode__ = GSConstSmail.CMDMODE_ADD;
    /** アカウント管理モード */
    private int smlAccountMode__ = 0;
    /** アカウントSID */
    private int smlAccountSid__ = 0;
    /** アカウント登録画面 チェックされている並び順 */
    private String sml100sortAccount__ = null;

    /**
     * <p>smlCmdMode を取得します。
     * @return smlCmdMode
     */
    public int getSmlCmdMode() {
        return smlCmdMode__;
    }
    /**
     * <p>smlCmdMode をセットします。
     * @param smlCmdMode smlCmdMode
     */
    public void setSmlCmdMode(int smlCmdMode) {
        smlCmdMode__ = smlCmdMode;
    }
    /**
     * <p>smlAccountMode を取得します。
     * @return smlAccountMode
     */
    public int getSmlAccountMode() {
        return smlAccountMode__;
    }
    /**
     * <p>smlAccountMode をセットします。
     * @param smlAccountMode smlAccountMode
     */
    public void setSmlAccountMode(int smlAccountMode) {
        smlAccountMode__ = smlAccountMode;
    }
    /**
     * <p>smlAccountSid を取得します。
     * @return smlAccountSid
     */
    public int getSmlAccountSid() {
        return smlAccountSid__;
    }
    /**
     * <p>smlAccountSid をセットします。
     * @param smlAccountSid smlAccountSid
     */
    public void setSmlAccountSid(int smlAccountSid) {
        smlAccountSid__ = smlAccountSid;
    }
    /**
     * <p>sml100sortAccount を取得します。
     * @return sml100sortAccount
     */
    public String getSml100sortAccount() {
        return sml100sortAccount__;
    }
    /**
     * <p>sml100sortAccount をセットします。
     * @param sml100sortAccount sml100sortAccount
     */
    public void setSml100sortAccount(String sml100sortAccount) {
        sml100sortAccount__ = sml100sortAccount;
    }
    /**
     * <p>smlViewAccount を取得します。
     * @return smlViewAccount
     */
    public int getSmlViewAccount() {
        return smlViewAccount__;
    }
    /**
     * <p>smlViewAccount をセットします。
     * @param smlViewAccount smlViewAccount
     */
    public void setSmlViewAccount(int smlViewAccount) {
        smlViewAccount__ = smlViewAccount;
    }
}