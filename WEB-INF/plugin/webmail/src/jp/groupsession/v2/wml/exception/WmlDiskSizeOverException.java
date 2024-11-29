package jp.groupsession.v2.wml.exception;

/**
 * <br>[機  能] メールの送信を試みた際に、テンポラリディレクトリが存在しない場合の例外です
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlDiskSizeOverException extends Exception {
    
    /** エラーメッセージ */
    private String errorMsg__ = "";

    /**
     * <p>errorMsg を取得します。
     * @return errorMsg
     * @see jp.groupsession.v2.wml.exception.WmlDiskSizeOverException#errorMsg__
     */
    public String getErrorMsg() {
        return errorMsg__;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param msg エラーメッセージ
     */
    public WmlDiskSizeOverException(String errorMsg) {
        errorMsg__ = errorMsg;
    }
}
