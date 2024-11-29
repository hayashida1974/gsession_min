package jp.groupsession.v2.adr.adr080;

import jp.groupsession.v2.adr.model.AdrTypeindustryModel;

/**
 * <br>[機  能] 業種情報表示用のModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr080TypeindustryModel extends AdrTypeindustryModel {

    /** 表示順(画面用) */
    private String atiValue__;

    /**
     * <p>atiValue を取得します。
     * @return atiValue
     */
    public String getAtiValue() {
        return atiValue__;
    }

    /**
     * <p>atiValue をセットします。
     * @param atiValue atiValue
     */
    public void setAtiValue(String atiValue) {
        atiValue__ = atiValue;
    }



}
