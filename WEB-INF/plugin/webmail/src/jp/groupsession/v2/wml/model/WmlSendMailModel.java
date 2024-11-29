package jp.groupsession.v2.wml.model;

/**
 * <br>[機  能] 送信メール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlSendMailModel extends WmlMailModel {

    /** HTMLメールフラグ */
    private boolean htmlMail__ = false;
    /** 返信/転送元メールのメッセージ番号 */
    private long originMailNum__ = 0;
    /** 返信メールフラグ */
    private boolean replyMail__ = false;
    /** 転送メールフラグ */
    private boolean forwardMail__ = false;

    /**
     * <p>htmlMail を取得します。
     * @return htmlMail
     */
    public boolean isHtmlMail() {
        return htmlMail__;
    }
    /**
     * <p>htmlMail をセットします。
     * @param htmlMail htmlMail
     */
    public void setHtmlMail(boolean htmlMail) {
        htmlMail__ = htmlMail;
    }
    /**
     * <p>forwardMail を取得します。
     * @return forwardMail
     */
    public boolean isForwardMail() {
        return forwardMail__;
    }
    /**
     * <p>forwardMail をセットします。
     * @param forwardMail forwardMail
     */
    public void setForwardMail(boolean forwardMail) {
        forwardMail__ = forwardMail;
    }
    /**
     * <p>originMailNum を取得します。
     * @return originMailNum
     */
    public long getOriginMailNum() {
        return originMailNum__;
    }
    /**
     * <p>originMailNum をセットします。
     * @param originMailNum originMailNum
     */
    public void setOriginMailNum(long originMailNum) {
        originMailNum__ = originMailNum;
    }
    /**
     * <p>replyMail を取得します。
     * @return replyMail
     */
    public boolean isReplyMail() {
        return replyMail__;
    }
    /**
     * <p>replyMail をセットします。
     * @param replyMail replyMail
     */
    public void setReplyMail(boolean replyMail) {
        replyMail__ = replyMail;
    }
}
