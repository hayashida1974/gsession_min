package jp.groupsession.v2.sml;


import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] ショートメールプラグインで共通使用するフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AbstractSmlForm extends AbstractGsForm {

    /** 表示ユーザ */
    private int smlViewUser__ = 0;
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
     * <p>smlAccountMode をセットします。
     * @param smlAccountMode smlAccountMode
     */
    public void setSmlAccountMode(int smlAccountMode) {
        smlAccountMode__ = smlAccountMode;
    }
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
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {

        msgForm.addHiddenParam("smlViewAccount", getSmlViewAccount());
        msgForm.addHiddenParam("smlCmdMode", getSmlCmdMode());
        msgForm.addHiddenParam("smlAccountMode", getSmlAccountMode());
        msgForm.addHiddenParam("smlAccountSid", getSmlAccountSid());

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
    /**
     * <p>smlAccountMode を取得します。
     * @return smlAccountMode
     */
    public int getSmlAccountMode() {
        return smlAccountMode__;
    }
    /**
     * <p>smlViewUser を取得します。
     * @return smlViewUser
     */
    public int getSmlViewUser() {
        return smlViewUser__;
    }
    /**
     * <p>smlViewUser をセットします。
     * @param smlViewUser smlViewUser
     */
    public void setSmlViewUser(int smlViewUser) {
        smlViewUser__ = smlViewUser;
    }
}
