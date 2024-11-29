package jp.groupsession.v2.man.man110kn;

import jp.groupsession.v2.man.man110.Man110ParamModel;

/**
 * <br>[機  能] メイン 管理者設定 役職登録確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man110knParamModel extends Man110ParamModel {

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