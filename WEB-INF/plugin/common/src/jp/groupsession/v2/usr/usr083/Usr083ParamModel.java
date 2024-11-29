package jp.groupsession.v2.usr.usr083;

import jp.groupsession.v2.usr.usr080.Usr080ParamModel;

/**
 * <br>[機  能] ユーザ情報 管理者設定 権限設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr083ParamModel extends Usr080ParamModel {

    /** 初期表示区分 */
    private int usr083initKbn__ = 0;
    /** デフォルト権限設定区分 */
    private int usr083DefoDspKbn__ = -1;

    /** 画面項目1(ラベル編集権限)権限有無 */
    private int usr083Pow1__ = 0;
    /** 画面項目2(ラベル付与権限)権限有無 */
    private int usr083Pow2__ = 0;

    /**
     * @return usr083DefoDspKbn
     */
    public int getUsr083DefoDspKbn() {
        return usr083DefoDspKbn__;
    }

    /**
     * @param usr083DefoDspKbn 設定する usr083DefoDspKbn
     */
    public void setUsr083DefoDspKbn(int usr083DefoDspKbn) {
        usr083DefoDspKbn__ = usr083DefoDspKbn;
    }

    /**
     * <p>usr083initKbn を取得します。
     * @return usr083initKbn
     */
    public int getUsr083initKbn() {
        return usr083initKbn__;
    }

    /**
     * <p>usr083initKbn をセットします。
     * @param usr083initKbn usr083initKbn
     */
    public void setUsr083initKbn(int usr083initKbn) {
        usr083initKbn__ = usr083initKbn;
    }

    /**
     * <p>usr083Pow1 を取得します。
     * @return usr083Pow1
     */
    public int getUsr083Pow1() {
        return usr083Pow1__;
    }

    /**
     * <p>usr083Pow1 をセットします。
     * @param usr083Pow1 usr083Pow1
     */
    public void setUsr083Pow1(int usr083Pow1) {
        usr083Pow1__ = usr083Pow1;
    }

    /**
     * <p>usr083Pow2 を取得します。
     * @return usr083Pow2
     */
    public int getUsr083Pow2() {
        return usr083Pow2__;
    }

    /**
     * <p>usr083Pow2 をセットします。
     * @param usr083Pow2 usr083Pow2
     */
    public void setUsr083Pow2(int usr083Pow2) {
        usr083Pow2__ = usr083Pow2;
    }

}