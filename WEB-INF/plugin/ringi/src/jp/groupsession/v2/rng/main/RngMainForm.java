package jp.groupsession.v2.rng.main;

import jp.groupsession.v2.rng.rng010.Rng010Form;

/**
 * <br>[機  能] 稟議一覧(メイン画面表示用)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngMainForm extends Rng010Form {
    /** 稟議トップ画面URL */
    private String  rngTopUrl__;

    /**
     * @return rngTopUrl__ を戻します。
     */
    public String getRngTopUrl() {
        return rngTopUrl__;
    }
    /**
     * @param rngTopUrl 設定する rngTopUrl__。
     */
    public void setRngTopUrl(String rngTopUrl) {
        rngTopUrl__ = rngTopUrl;
    }
}
