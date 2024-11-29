package jp.groupsession.v2.usr.usr080;

import jp.groupsession.v2.usr.usr040.Usr040ParamModel;

/**
 * <br>[機  能] ユーザ情報 管理者設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr080ParamModel extends Usr040ParamModel {

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Usr080ParamModel() {
    }

    /** 遷移元 メイン管理者メニュー画面から遷移した場合:1 その他:0*/
    private int backScreen__ = 0;

    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }

    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }

}
