package jp.groupsession.v2.man.man510kn;

import jp.groupsession.v2.man.man510.Man510ParamModel;
/**
 *
 * <br>[機  能] ワンタイムパスワード設定確認 パラメータ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man510knParamModel extends Man510ParamModel {
    /** SMTP暗号プロトコル 表示用 */
    private String man510knSendEncrypt__ = null;

    /**
     * <p>man510knSendEncrypt を取得します。
     * @return man510knSendEncrypt
     * @see jp.groupsession.v2.man.man510kn.Man510knForm#man510knSendEncrypt__
     */
    public String getMan510knSendEncrypt() {
        return man510knSendEncrypt__;
    }

    /**
     * <p>man510knSendEncrypt をセットします。
     * @param man510knSendEncrypt man510knSendEncrypt
     * @see jp.groupsession.v2.man.man510kn.Man510knForm#man510knSendEncrypt__
     */
    public void setMan510knSendEncrypt(String man510knSendEncrypt) {
        man510knSendEncrypt__ = man510knSendEncrypt;
    }
}
