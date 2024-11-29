package jp.groupsession.v2.cmn.cmn250;

import jp.groupsession.v2.cmn.model.base.CmnOauthModel;

/**
 * <br>[機  能] OAuth認証情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn250DisplayModel extends CmnOauthModel {
    /** プロバイダ(表示用) */
    private String authValue__ = null;

    /**
     * <p>authValue を取得します。
     * @return authValue
     * @see jp.groupsession.v2.cmn.cmn250.Cmn250DisplayModel#authValue__
     */
    public String getAuthValue() {
        return authValue__;
    }

    /**
     * <p>authValue をセットします。
     * @param authValue authValue
     * @see jp.groupsession.v2.cmn.cmn250.Cmn250DisplayModel#authValue__
     */
    public void setAuthValue(String authValue) {
        authValue__ = authValue;
    }
}