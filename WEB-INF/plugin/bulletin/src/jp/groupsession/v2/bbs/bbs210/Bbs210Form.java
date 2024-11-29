package jp.groupsession.v2.bbs.bbs210;

import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs010.Bbs010Form;

/**
 * <br>[機  能] 掲示板 個人設定 初期値設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs210Form extends Bbs010Form {
    /** 初期化フラグ*/
    private int bbs210Init__;

    /** 投稿タイプ初期値 */
    private int bbs210IniPostType__ = GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN;

    /**
     * <p>bbs210IniPostType を取得します。
     * @return bbs210IniPostType
     */
    public int getBbs210IniPostType() {
        return bbs210IniPostType__;
    }

    /**
     * <p>bbs210IniPostType をセットします。
     * @param bbs210IniPostType bbs210IniPostType
     */
    public void setBbs210IniPostType(int bbs210IniPostType) {
        bbs210IniPostType__ = bbs210IniPostType;
    }

    /**
     * <p>bbs210Init を取得します。
     * @return bbs210Init
     */
    public int getBbs210Init() {
        return bbs210Init__;
    }

    /**
     * <p>bbs210Init をセットします。
     * @param bbs210Init bbs210Init
     */
    public void setBbs210Init(int bbs210Init) {
        bbs210Init__ = bbs210Init;
    }
}