package jp.groupsession.v2.adr.adr050;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.Adr010ParamModel;

/**
 * <br>[機  能] アドレス帳 個人設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr050ParamModel extends Adr010ParamModel {
    /** 初期値設定表示フラグ */
    private int adr050initFlg__ = GSConstAddress.MEM_DSP_ADM;

    /**
     * <p>adr050initFlg を取得します。
     * @return adr050initFlg
     */
    public int getAdr050initFlg() {
        return adr050initFlg__;
    }

    /**
     * <p>adr050initFlg をセットします。
     * @param adr050initFlg adr050initFlg
     */
    public void setAdr050initFlg(int adr050initFlg) {
        adr050initFlg__ = adr050initFlg;
    }
}