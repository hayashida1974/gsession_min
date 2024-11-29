package jp.groupsession.v2.wml.wml090;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.wml.wml010.Wml010Form;

/**
 * <br>[機  能] WEBメール 個人設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml090Form extends Wml010Form {
    /** 遷移元画面 */
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

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
