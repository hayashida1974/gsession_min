package jp.groupsession.v2.man.man100;

import jp.groupsession.v2.cmn.model.base.CmnPositionModel;

/**
 * <br>[機  能] 役職情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man100PositionModel extends CmnPositionModel {

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
