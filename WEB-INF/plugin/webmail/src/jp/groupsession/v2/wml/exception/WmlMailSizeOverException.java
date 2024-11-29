package jp.groupsession.v2.wml.exception;


/**
 * <br>[機  能] 送信を試みたメールのサイズが上限を超えていた場合の例外です
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlMailSizeOverException extends Exception {

    /** 最大メール容量 */
    private String maxSizeText__ = "";

    /**
     * <p>maxSizeText を取得します。
     * @return maxSizeText
     * @see jp.groupsession.v2.wml.exception.WmlMailSizeOverException#maxSizeText__
     */
    public String getMaxSizeText() {
        return maxSizeText__;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param maxSizeText 最大メール容量
     */    
    public WmlMailSizeOverException(String maxSizeText) {
        maxSizeText__ = maxSizeText;
    }

}
