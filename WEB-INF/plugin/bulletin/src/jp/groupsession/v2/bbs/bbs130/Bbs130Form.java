package jp.groupsession.v2.bbs.bbs130;

import jp.groupsession.v2.bbs.bbs010.Bbs010Form;

/**
 * <br>[機  能] 掲示板 個人設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs130Form extends Bbs010Form {
    /** 掲示板初期値設定 表示判定 */
    private boolean bbs130IniPostTypeKbn__ = true;
    /** ショートメール通知設定制限 */
    private boolean bbs130smlKbn__ = true;

    /**
     * <p>bbs130IniPostTypeKbn を取得します。
     * @return bbs130IniPostTypeKbn
     */
    public boolean isBbs130IniPostTypeKbn() {
        return bbs130IniPostTypeKbn__;
    }
    /**
     * <p>bbs130IniPostTypeKbn をセットします。
     * @param bbs130IniPostTypeKbn bbs130IniPostTypeKbn
     */
    public void setBbs130IniPostTypeKbn(boolean bbs130IniPostTypeKbn) {
        bbs130IniPostTypeKbn__ = bbs130IniPostTypeKbn;
    }

    /**
     * <p>bbs130smlKbn を取得します。
     * @return bbs130smlKbn
     */
    public boolean isBbs130smlKbn() {
        return bbs130smlKbn__;
    }
    /**
     * <p>bbs130smlKbn をセットします。
     * @param bbs130smlKbn bbs130smlKbn
     */
    public void setBbs130smlKbn(boolean bbs130smlKbn) {
        bbs130smlKbn__ = bbs130smlKbn;
    }
}
