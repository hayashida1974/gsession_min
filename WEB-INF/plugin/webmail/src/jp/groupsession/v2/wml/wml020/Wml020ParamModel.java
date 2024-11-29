package jp.groupsession.v2.wml.wml020;

import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.wml.wml010.Wml010ParamModel;

/**
 * <br>[機  能]  WEBメール 管理者設定メニュー画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml020ParamModel extends Wml010ParamModel {
    /** 遷移元画面 */
    private int backScreen__ = GSConstMain.BACK_PLUGIN;
    /** 送受信ログの登録 true:登録する false:登録しない */
    private boolean wmlEntryMailLogFlg__ = true;
    /** メールテンプレート区分 */
    private int wmlMailTemplateKbn__ = 0;

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
    /**
     * <p>wmlEntryMailLogFlg を取得します。
     * @return wmlEntryMailLogFlg
     */
    public boolean isWmlEntryMailLogFlg() {
        return wmlEntryMailLogFlg__;
    }
    /**
     * <p>wmlEntryMailLogFlg をセットします。
     * @param wmlEntryMailLogFlg wmlEntryMailLogFlg
     */
    public void setWmlEntryMailLogFlg(boolean wmlEntryMailLogFlg) {
        wmlEntryMailLogFlg__ = wmlEntryMailLogFlg;
    }
    /**
     * <p>wmlMailTemplateKbn を取得します。
     * @return wmlMailTemplateKbn
     */
    public int getWmlMailTemplateKbn() {
        return wmlMailTemplateKbn__;
    }
    /**
     * <p>wmlMailTemplateKbn をセットします。
     * @param wmlMailTemplateKbn wmlMailTemplateKbn
     */
    public void setWmlMailTemplateKbn(int wmlMailTemplateKbn) {
        wmlMailTemplateKbn__ = wmlMailTemplateKbn;
    }
}