package jp.groupsession.v2.adr.adr130;

import jp.groupsession.v2.adr.model.AdrLabelModel;

/**
 * <br>[機  能] アドレス帳 ラベル情報(表示用)を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr130LabelModel extends AdrLabelModel {

    /** 表示順(画面用) */
    private String albValue__;

    /**
     * <p>albValue を取得します。
     * @return albValue
     */
    public String getAlbValue() {
        return albValue__;
    }

    /**
     * <p>albValue をセットします。
     * @param albValue albValue
     */
    public void setAlbValue(String albValue) {
        albValue__ = albValue;
    }



}
