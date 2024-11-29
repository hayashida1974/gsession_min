package jp.groupsession.v2.adr.adr040;

import jp.groupsession.v2.adr.adr030.Adr030ParamModel;

/**
 * <br>[機  能] アドレス帳 管理者設定 権限設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr040ParamModel extends Adr030ParamModel {

    /** 画面項目1(業種編集権限)権限有無 */
    private int adr040Pow1__ = 0;
    /** 画面項目2(会社編集権限)権限有無 */
    private int adr040Pow2__ = 0;
    /** 画面項目3(ラベル編集権限)権限有無 */
    private int adr040Pow3__ = 0;
    /** 画面項目4(エクスポート権限)権限有無 */
    private int adr040Pow4__ = 1;
    /** 画面項目5(役職編集権限)権限有無 */
    private int adr040Pow5__ = 0;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Adr040ParamModel() {

    }

    /**
     * <p>adr040Pow1 を取得します。
     * @return adr040Pow1
     */
    public int getAdr040Pow1() {
        return adr040Pow1__;
    }

    /**
     * <p>adr040Pow1 をセットします。
     * @param adr040Pow1 adr040Pow1
     */
    public void setAdr040Pow1(int adr040Pow1) {
        adr040Pow1__ = adr040Pow1;
    }

    /**
     * <p>adr040Pow2 を取得します。
     * @return adr040Pow2
     */
    public int getAdr040Pow2() {
        return adr040Pow2__;
    }

    /**
     * <p>adr040Pow2 をセットします。
     * @param adr040Pow2 adr040Pow2
     */
    public void setAdr040Pow2(int adr040Pow2) {
        adr040Pow2__ = adr040Pow2;
    }

    /**
     * <p>adr040Pow3 を取得します。
     * @return adr040Pow3
     */
    public int getAdr040Pow3() {
        return adr040Pow3__;
    }

    /**
     * <p>adr040Pow3 をセットします。
     * @param adr040Pow3 adr040Pow3
     */
    public void setAdr040Pow3(int adr040Pow3) {
        adr040Pow3__ = adr040Pow3;
    }

    /**
     * <p>adr040Pow4 を取得します。
     * @return adr040Pow4
     */
    public int getAdr040Pow4() {
        return adr040Pow4__;
    }

    /**
     * <p>adr040Pow4 をセットします。
     * @param adr040Pow4 adr040Pow4
     */
    public void setAdr040Pow4(int adr040Pow4) {
        adr040Pow4__ = adr040Pow4;
    }

    /**
     * <p>adr040Pow5 を取得します。
     * @return adr040Pow5
     */
    public int getAdr040Pow5() {
        return adr040Pow5__;
    }

    /**
     * <p>adr040Pow5 をセットします。
     * @param adr040Pow5 adr040Pow5
     */
    public void setAdr040Pow5(int adr040Pow5) {
        adr040Pow5__ = adr040Pow5;
    }
}