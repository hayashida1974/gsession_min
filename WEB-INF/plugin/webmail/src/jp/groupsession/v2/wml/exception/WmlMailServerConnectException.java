package jp.groupsession.v2.wml.exception;

/**
 * <br>[機  能] メールの送信を試みた際に、メールサーバーへ接続できなかった場合の例外です
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlMailServerConnectException extends Exception {

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public WmlMailServerConnectException() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param e 例外
     */
    public WmlMailServerConnectException(Throwable e) {
        super(e);
    }
}
