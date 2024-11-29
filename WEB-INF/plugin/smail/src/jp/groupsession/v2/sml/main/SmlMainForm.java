package jp.groupsession.v2.sml.main;

import jp.groupsession.v2.sml.sml010.Sml010Form;


/**
 * <br>[機  能] ショートメール(メイン画面表示用)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlMainForm extends Sml010Form {
    /** ショートメールトップ画面URL */
    private String  smlTopUrl__;
    /**
     * @return smlTopUrl__ を戻します。
     */
    public String getSmlTopUrl() {
        return smlTopUrl__;
    }
    /**
     * @param smlTopUrl 設定する smlTopUrl__。
     */
    public void setSmlTopUrl(String smlTopUrl) {
        smlTopUrl__ = smlTopUrl;
    }
}