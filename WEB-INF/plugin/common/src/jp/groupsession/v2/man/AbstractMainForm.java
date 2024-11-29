package jp.groupsession.v2.man;

import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] メインで共通的に使用するフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AbstractMainForm extends AbstractGsForm {
    /** パスワード編集区分 */
    private int manPasswordKbn__ = GSConstMain.PASSWORD_EDIT_USER;
    /** パスワード有効期限フラグ */
    private int manPasswordLimitFlg__ = GSConstMain.PWC_LIMITKBN_OFF;
    /**
     * <p>manPasswordKbn を取得します。
     * @return manPasswordKbn
     */
    public int getManPasswordKbn() {
        return manPasswordKbn__;
    }
    /**
     * <p>manPasswordKbn をセットします。
     * @param manPasswordKbn manPasswordKbn
     */
    public void setManPasswordKbn(int manPasswordKbn) {
        manPasswordKbn__ = manPasswordKbn;
    }
    /**
     * <p>manPasswordLimitFlg を取得します。
     * @return manPasswordLimitFlg
     */
    public int getManPasswordLimitFlg() {
        return manPasswordLimitFlg__;
    }
    /**
     * <p>manPasswordLimitFlg をセットします。
     * @param manPasswordLimitFlg manPasswordLimitFlg
     */
    public void setManPasswordLimitFlg(int manPasswordLimitFlg) {
        manPasswordLimitFlg__ = manPasswordLimitFlg;
    }
}