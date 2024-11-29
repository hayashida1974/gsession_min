package jp.groupsession.v2.bbs.bbs110;

import jp.groupsession.v2.bbs.bbs010.Bbs010Form;

/**
 * <br>[機  能] 掲示板 管理者設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs110Form extends Bbs010Form {
    /**ショートメール利用可否フラグ*/
    int smailUseOk__;
    /** メイン管理者権限フラグ */
    private boolean bbs110GsAdminFlg__ = false;

    /**
     * <p>smailUseOk を取得します。
     * @return smailUseOk
     */
    public int getSmailUseOk() {
        return smailUseOk__;
    }

    /**
     * <p>smailUseOk をセットします。
     * @param smailUseOk smailUseOk
     */
    public void setSmailUseOk(int smailUseOk) {
        smailUseOk__ = smailUseOk;
    }

    /**
     * <p>bbs110GsAdminFlg を取得します。
     * @return bbs110GsAdminFlg
     */
    public boolean isBbs110GsAdminFlg() {
        return bbs110GsAdminFlg__;
    }

    /**
     * <p>bbs110GsAdminFlg をセットします。
     * @param bbs110GsAdminFlg bbs110GsAdminFlg
     */
    public void setBbs110GsAdminFlg(boolean bbs110GsAdminFlg) {
        bbs110GsAdminFlg__ = bbs110GsAdminFlg;
    }
}
