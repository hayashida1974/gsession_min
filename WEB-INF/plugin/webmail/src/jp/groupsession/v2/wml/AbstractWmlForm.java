package jp.groupsession.v2.wml;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] WEBメールプラグインで共通使用するフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AbstractWmlForm extends AbstractGsForm {

    /** 表示アカウント */
    private String wmlViewAccount__ = null;

    /** 編集モード */
    private int wmlCmdMode__ = GSConstWebmail.CMDMODE_ADD;
    /** アカウント管理モード */
    private int wmlAccountMode__ = 0;
    /** アカウントSID */
    private int wmlAccountSid__ = 0;
    /** アカウント登録画面 チェックされている並び順 */
    private String wml100sortAccount__ = null;

    /**
     * <p>wmlViewAccount を取得します。
     * @return wmlViewAccount
     */
    public String getWmlViewAccount() {
        return wmlViewAccount__;
    }
    /**
     * <p>wmlViewAccount をセットします。
     * @param wmlViewAccount wmlViewAccount
     */
    public void setWmlViewAccount(String wmlViewAccount) {
        wmlViewAccount__ = wmlViewAccount;
    }
    /**
     * <p>wmlAccountMode を取得します。
     * @return wmlAccountMode
     */
    public int getWmlAccountMode() {
        return wmlAccountMode__;
    }
    /**
     * <p>wmlAccountMode をセットします。
     * @param wmlAccountMode wmlAccountMode
     */
    public void setWmlAccountMode(int wmlAccountMode) {
        wmlAccountMode__ = wmlAccountMode;
    }
    /**
     * <p>wmlCmdMode を取得します。
     * @return wmlCmdMode
     */
    public int getWmlCmdMode() {
        return wmlCmdMode__;
    }
    /**
     * <p>wmlCmdMode をセットします。
     * @param wmlCmdMode wmlCmdMode
     */
    public void setWmlCmdMode(int wmlCmdMode) {
        wmlCmdMode__ = wmlCmdMode;
    }
    /**
     * <p>wmlAccountSid を取得します。
     * @return wmlAccountSid
     */
    public int getWmlAccountSid() {
        return wmlAccountSid__;
    }
    /**
     * <p>wmlAccountSid をセットします。
     * @param wmlAccountSid wmlAccountSid
     */
    public void setWmlAccountSid(int wmlAccountSid) {
        wmlAccountSid__ = wmlAccountSid;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {

        msgForm.addHiddenParam("wmlViewAccount", getWmlViewAccount());
        msgForm.addHiddenParam("wmlCmdMode", getWmlCmdMode());
        msgForm.addHiddenParam("wmlAccountMode", getWmlAccountMode());
        msgForm.addHiddenParam("wmlAccountSid", getWmlAccountSid());

    }
    /**
     * <p>wml100sortAccount を取得します。
     * @return wml100sortAccount
     */
    public String getWml100sortAccount() {
        return wml100sortAccount__;
    }
    /**
     * <p>wml100sortAccount をセットします。
     * @param wml100sortAccount wml100sortAccount
     */
    public void setWml100sortAccount(String wml100sortAccount) {
        wml100sortAccount__ = wml100sortAccount;
    }
}
