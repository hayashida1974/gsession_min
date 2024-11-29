package jp.groupsession.v2.sml.sml280;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml100.Sml100Form;

/**
 * <br>[機  能] ショートメール アカウント基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml280Form extends Sml100Form {

    /** 初期表示区分 */
    private int sml280initFlg__ = GSConstSmail.DSP_FIRST;
    /** アカウントの作成区分 */
    private int sml280acntMakeKbn__ = GSConstSmail.KANRI_USER_NO;
    /** 自動削除区分 */
    private int sml280autoDelKbn__ = GSConstSmail.AUTO_DEL_ADM;
    /** 使用者 */
    private int sml280acntUser__ = GSConstSmail.KANRI_ACCOUNT_USER_NO;
    /**
     * <p>sml280initFlg を取得します。
     * @return sml280initFlg
     */
    public int getSml280initFlg() {
        return sml280initFlg__;
    }
    /**
     * <p>sml280initFlg をセットします。
     * @param sml280initFlg sml280initFlg
     */
    public void setSml280initFlg(int sml280initFlg) {
        sml280initFlg__ = sml280initFlg;
    }
    /**
     * <p>sml280acntMakeKbn を取得します。
     * @return sml280acntMakeKbn
     */
    public int getSml280acntMakeKbn() {
        return sml280acntMakeKbn__;
    }
    /**
     * <p>sml280acntMakeKbn をセットします。
     * @param sml280acntMakeKbn sml280acntMakeKbn
     */
    public void setSml280acntMakeKbn(int sml280acntMakeKbn) {
        sml280acntMakeKbn__ = sml280acntMakeKbn;
    }
    /**
     * <p>sml280autoDelKbn を取得します。
     * @return sml280autoDelKbn
     */
    public int getSml280autoDelKbn() {
        return sml280autoDelKbn__;
    }
    /**
     * <p>sml280autoDelKbn をセットします。
     * @param sml280autoDelKbn sml280autoDelKbn
     */
    public void setSml280autoDelKbn(int sml280autoDelKbn) {
        sml280autoDelKbn__ = sml280autoDelKbn;
    }
    /**
     * <p>sml280acntUser を取得します。
     * @return sml280acntUser
     */
    public int getSml280acntUser() {
        return sml280acntUser__;
    }
    /**
     * <p>sml280acntUser をセットします。
     * @param sml280acntUser sml280acntUser
     */
    public void setSml280acntUser(int sml280acntUser) {
        sml280acntUser__ = sml280acntUser;
    }
}
