package jp.groupsession.v2.rng.rng180kn;

import jp.groupsession.v2.rng.rng180.Rng180ParamModel;

/**
 * <br>[機  能] 稟議 管理者設定 基本設定確認画面のパラメータを保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng180knParamModel extends Rng180ParamModel {
    /** 表示用 申請ID設定 */
    private String rsv180knDefaultIdDsp__ = null;

    /**
     * <p>rsv180knDefaultIdDsp を取得します。
     * @return rsv180knDefaultIdDsp
     * @see jp.groupsession.v2.rng.rng180kn.Rng180knForm#rsv180knDefaultIdDsp__
     */
    public String getRsv180knDefaultIdDsp() {
        return rsv180knDefaultIdDsp__;
    }

    /**
     * <p>rsv180knDefaultIdDsp をセットします。
     * @param rsv180knDefaultIdDsp rsv180knDefaultIdDsp
     * @see jp.groupsession.v2.rng.rng180kn.Rng180knForm#rsv180knDefaultIdDsp__
     */
    public void setRsv180knDefaultIdDsp(String rsv180knDefaultIdDsp) {
        rsv180knDefaultIdDsp__ = rsv180knDefaultIdDsp;
    }
}