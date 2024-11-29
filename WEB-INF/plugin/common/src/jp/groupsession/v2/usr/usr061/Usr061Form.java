package jp.groupsession.v2.usr.usr061;

import jp.groupsession.v2.usr.usr060.Usr060Form;

/**
 *
 * <br>[機  能] ワンタイムパスワード通知先メールアドレス設定（GS管理者） フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr061Form extends Usr060Form {
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
