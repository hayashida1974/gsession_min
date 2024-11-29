package jp.groupsession.v2.man.man110kn;

import jp.groupsession.v2.man.man110.Man110Form;

/**
 * <br>[機  能] メイン 管理者設定 役職登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man110knForm extends Man110Form {

    //表示項目
    /** 備考(表示用) */
    private String bikou__;

    /**
     * <p>bikou を取得します。
     * @return bikou
     */
    public String getBikou() {
        return bikou__;
    }

    /**
     * <p>bikou をセットします。
     * @param bikou bikou
     */
    public void setBikou(String bikou) {
        bikou__ = bikou;
    }

}
