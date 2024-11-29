package jp.groupsession.v2.wml.wml020;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.wml.wml010.Wml010Form;


/**
 * <br>[機  能] WEBメール 管理者設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml020Form extends Wml010Form {

    /** 遷移元画面 */
    private int backScreen__ = GSConstMain.BACK_PLUGIN;
    /** 送受信ログの登録 true:登録する false:登録しない */
    private boolean wmlEntryMailLogFlg__ = true;
    /** メールテンプレート区分 */
    private int wmlMailTemplateKbn__ = 0;
    /** メイン管理者権限フラグ */
    private boolean wml020GsAdminFlg__ = false;

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
    /**
     * <p>wml020GsAdminFlg を取得します。
     * @return wml020GsAdminFlg
     */
    public boolean isWml020GsAdminFlg() {
        return wml020GsAdminFlg__;
    }
    /**
     * <p>wml020GsAdminFlg をセットします。
     * @param wml020GsAdminFlg wml020GsAdminFlg
     */
    public void setWml020GsAdminFlg(boolean wml020GsAdminFlg) {
        wml020GsAdminFlg__ = wml020GsAdminFlg;
    }
    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("backScreen", backScreen__);
    }
}
