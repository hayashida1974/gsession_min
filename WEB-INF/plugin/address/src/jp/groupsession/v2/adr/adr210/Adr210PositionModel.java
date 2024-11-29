package jp.groupsession.v2.adr.adr210;

import jp.groupsession.v2.adr.model.AdrPositionModel;

/**
 * <br>[機  能] アドレス帳 役職一覧画面の役職情報(表示用)を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr210PositionModel extends AdrPositionModel {

    /** 表示順(画面用) */
    private String posValue__;

    /**
     * <p>posValue を取得します。
     * @return posValue
     */
    public String getPosValue() {
        return posValue__;
    }
    /**
     * <p>posValue をセットします。
     * @param posValue posValue
     */
    public void setPosValue(String posValue) {
        posValue__ = posValue;
    }
}