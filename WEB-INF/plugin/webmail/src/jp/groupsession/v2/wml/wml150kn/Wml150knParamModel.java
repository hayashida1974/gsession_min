package jp.groupsession.v2.wml.wml150kn;

import jp.groupsession.v2.wml.wml150.Wml150ParamModel;

/**
 * <br>[機  能] WEBメール アカウント基本設定確認画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml150knParamModel extends Wml150ParamModel {
    /** 転送先制限 許可する文字列 表示用 */
    private String[] wml150knFwLimitText__ = null;
    /** 受信暗号プロトコル 表示用 */
    private String wml150knReceiveEncrypt__ = null;
    /** 送信暗号プロトコル 表示用 */
    private String wml150knSendEncrypt__ = null;

    /**
     * <p>wml150knFwLimitText を取得します。
     * @return wml150knFwLimitText
     */
    public String[] getWml150knFwLimitText() {
        return wml150knFwLimitText__;
    }

    /**
     * <p>wml150knFwLimitText をセットします。
     * @param wml150knFwLimitText wml150knFwLimitText
     */
    public void setWml150knFwLimitText(String[] wml150knFwLimitText) {
        wml150knFwLimitText__ = wml150knFwLimitText;
    }


    /**
     * <p>wml150knSendEncrypt を取得します。
     * @return wml150knSendEncrypt
     * @see jp.groupsession.v2.wml.wml150kn.Wml150knParamModel#wml150knSendEncrypt__
     */
    public String getWml150knSendEncrypt() {
        return wml150knSendEncrypt__;
    }

    /**
     * <p>wml150knSendEncrypt をセットします。
     * @param wml150knSendEncrypt wml150knSendEncrypt
     * @see jp.groupsession.v2.wml.wml150kn.Wml150knParamModel#wml150knSendEncrypt__
     */
    public void setWml150knSendEncrypt(String wml150knSendEncrypt) {
        wml150knSendEncrypt__ = wml150knSendEncrypt;
    }

    /**
     * <p>wml150knReceiveEncrypt を取得します。
     * @return wml150knReceiveEncrypt
     * @see jp.groupsession.v2.wml.wml150kn.Wml150knParamModel#wml150knReceiveEncrypt__
     */
    public String getWml150knReceiveEncrypt() {
        return wml150knReceiveEncrypt__;
    }

    /**
     * <p>wml150knReceiveEncrypt をセットします。
     * @param wml150knReceiveEncrypt wml150knReceiveEncrypt
     * @see jp.groupsession.v2.wml.wml150kn.Wml150knParamModel#wml150knReceiveEncrypt__
     */
    public void setWml150knReceiveEncrypt(String wml150knReceiveEncrypt) {
        wml150knReceiveEncrypt__ = wml150knReceiveEncrypt;
    }
}