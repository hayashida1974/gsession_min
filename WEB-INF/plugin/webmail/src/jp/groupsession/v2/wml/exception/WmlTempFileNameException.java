package jp.groupsession.v2.wml.exception;

/**
 * <br>[機  能] 添付ファイルの名前が不正だった場合の例外です
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlTempFileNameException extends Exception {

    /** エラーメッセージ */
    private String message__ = "";

    /**
     * <p>message を取得します。
     * @return message
     * @see jp.groupsession.v2.wml.exception.WmlTempFileNameException#message__
     */
    public String getMessage() {
        return message__;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param maxSizeText 最大メール容量
     */    
    public WmlTempFileNameException(String message) {
        message__ = message;
    }
}
