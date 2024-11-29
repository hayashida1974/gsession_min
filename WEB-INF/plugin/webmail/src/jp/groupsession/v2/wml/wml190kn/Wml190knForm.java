package jp.groupsession.v2.wml.wml190kn;

import jp.groupsession.v2.wml.wml190.Wml190Form;

/**
 * <br>[機  能] WEBメール 個人設定 アカウント編集確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml190knForm extends Wml190Form {
    /** テーマ 表示用 */
    private String wml190knTheme__ = null;
    /** 引用符 表示用 */
    private String wml190knQuotes__ = null;
    /** 受信暗号プロトコル 表示用 */
    private String wml190knReciveEncrypt__ = null;
    /** 送信暗号プロトコル 表示用 */
    private String wml190knSendEncrypt__ = null;
    /**
     * <p>wml190knTheme を取得します。
     * @return wml190knTheme
     */
    public String getWml190knTheme() {
        return wml190knTheme__;
    }
    /**
     * <p>wml190knTheme をセットします。
     * @param wml190knTheme wml190knTheme
     */
    public void setWml190knTheme(String wml190knTheme) {
        wml190knTheme__ = wml190knTheme;
    }
    /**
     * <p>wml190knQuotes を取得します。
     * @return wml190knQuotes
     */
    public String getWml190knQuotes() {
        return wml190knQuotes__;
    }
    /**
     * <p>wml190knQuotes をセットします。
     * @param wml190knQuotes wml190knQuotes
     */
    public void setWml190knQuotes(String wml190knQuotes) {
        wml190knQuotes__ = wml190knQuotes;
    }
    /**
     * <p>wml190knReciveEncrypt を取得します。
     * @return wml190knReciveEncrypt
     * @see jp.groupsession.v2.wml.wml190kn.Wml190knForm#wml190knReciveEncrypt__
     */
    public String getWml190knReciveEncrypt() {
        return wml190knReciveEncrypt__;
    }
    /**
     * <p>wml190knReciveEncrypt をセットします。
     * @param wml190knReciveEncrypt wml190knReciveEncrypt
     * @see jp.groupsession.v2.wml.wml190kn.Wml190knForm#wml190knReciveEncrypt__
     */
    public void setWml190knReciveEncrypt(String wml190knReciveEncrypt) {
        wml190knReciveEncrypt__ = wml190knReciveEncrypt;
    }
    /**
     * <p>wml190knSendEncrypt を取得します。
     * @return wml190knSendEncrypt
     * @see jp.groupsession.v2.wml.wml190kn.Wml190knForm#wml190knSendEncrypt__
     */
    public String getWml190knSendEncrypt() {
        return wml190knSendEncrypt__;
    }
    /**
     * <p>wml190knSendEncrypt をセットします。
     * @param wml190knSendEncrypt wml190knSendEncrypt
     * @see jp.groupsession.v2.wml.wml190kn.Wml190knForm#wml190knSendEncrypt__
     */
    public void setWml190knSendEncrypt(String wml190knSendEncrypt) {
        wml190knSendEncrypt__ = wml190knSendEncrypt;
    }
}
