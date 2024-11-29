package jp.groupsession.v2.rng.rng140kn;

import jp.groupsession.v2.rng.rng140.Rng140ParamModel;

/**
 * <br>[機  能]稟議 テンプレートカテゴリ登録確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng140knParamModel extends Rng140ParamModel {
    /** 備考 */
    private String biko__ = "";

    /**
     * @return biko
     */
    public String getBiko() {
        return biko__;
    }
    /**
     * @param biko セットする biko
     */
    public void setBiko(String biko) {
        biko__ = biko;
    }
}