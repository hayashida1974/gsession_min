package jp.groupsession.v2.rng.rng040;

import jp.groupsession.v2.rng.rng010.Rng010ParamModel;

/**
 * <br>[機  能] 稟議 管理者設定メニュー画面のパラメータを保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng040ParamModel extends Rng010ParamModel {
    /** ショートメール使用可否*/
    int canUseSml__;

    /** 申請ID使用可否*/
    int canUseSinsei__;

    /**
     * <p>canUseSml を取得します。
     * @return canUseSml
     */
    public int getCanUseSml() {
        return canUseSml__;
    }

    /**
     * <p>canUseSml をセットします。
     * @param canUseSml canUseSml
     */
    public void setCanUseSml(int canUseSml) {
        canUseSml__ = canUseSml;
    }

    /**
     * <p>canUsesinsei を取得します。
     * @return canUsesinsei
     */
    public int getCanUseSinsei() {
        return canUseSinsei__;
    }

    /**
     * <p>canUsesinsei をセットします。
     * @param canUseSinsei canUseSinsei
     */
    public void setCanUseSinsei(int canUseSinsei) {
        canUseSinsei__ = canUseSinsei;
    }

}