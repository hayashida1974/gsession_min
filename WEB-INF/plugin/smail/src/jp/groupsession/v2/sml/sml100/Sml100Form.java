package jp.groupsession.v2.sml.sml100;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml010.Sml010Form;

/**
 * <br>[機  能] ショートメール 管理者設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml100Form extends Sml010Form {

    /** 自動削除区分 */
    private int sml100autoDelKbn__ = GSConstSmail.AUTO_DEL_ADM;
    /** メイン管理者権限フラグ */
    private boolean sml100GsAdminFlg__ = false;

    /**
     * <p>sml100autoDelKbn を取得します。
     * @return sml100autoDelKbn
     */
    public int getSml100autoDelKbn() {
        return sml100autoDelKbn__;
    }

    /**
     * <p>sml100autoDelKbn をセットします。
     * @param sml100autoDelKbn sml100autoDelKbn
     */
    public void setSml100autoDelKbn(int sml100autoDelKbn) {
        sml100autoDelKbn__ = sml100autoDelKbn;
    }

    /**
     * <p>sml100GsAdminFlg を取得します。
     * @return sml100GsAdminFlg
     */
    public boolean isSml100GsAdminFlg() {
        return sml100GsAdminFlg__;
    }

    /**
     * <p>sml100GsAdminFlg をセットします。
     * @param sml100GsAdminFlg sml100GsAdminFlg
     */
    public void setSml100GsAdminFlg(boolean sml100GsAdminFlg) {
        sml100GsAdminFlg__ = sml100GsAdminFlg;
    }

}
