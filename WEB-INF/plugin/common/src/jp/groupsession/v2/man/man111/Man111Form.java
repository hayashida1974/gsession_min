package jp.groupsession.v2.man.man111;

import jp.groupsession.v2.man.man110kn.Man110knForm;

/**
 * <br>[機  能] メイン 役職登録ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man111Form extends Man110knForm {

    //フラグ
    /** 画面closeフラグ true=closeする、false=closeしない */
    private boolean closeFlg__ = false;
    /** 初期表示フラグ 0:初期表示  1:二回目以降*/
    private int man111InitFlg__ = 0;

    /**
     * <p>closeFlg を取得します。
     * @return closeFlg
     */
    public boolean isCloseFlg() {
        return closeFlg__;
    }

    /**
     * <p>closeFlg をセットします。
     * @param closeFlg closeFlg
     */
    public void setCloseFlg(boolean closeFlg) {
        closeFlg__ = closeFlg;
    }

    /**
     * <p>man111InitFlg を取得します。
     * @return man111InitFlg
     */
    public int getMan111InitFlg() {
        return man111InitFlg__;
    }

    /**
     * <p>man111InitFlg をセットします。
     * @param man111InitFlg man111InitFlg
     */
    public void setMan111InitFlg(int man111InitFlg) {
        man111InitFlg__ = man111InitFlg;
    }

}
