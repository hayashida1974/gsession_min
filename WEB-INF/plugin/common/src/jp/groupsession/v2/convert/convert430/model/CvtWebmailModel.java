package jp.groupsession.v2.convert.convert430.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメールのメール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CvtWebmailModel extends AbstractModel {
    /** メッセージ番号 */
    private long mailNum__ = 0;
    /** 件名 */
    private String subject__ = null;
    /** 本文 */
    private String body__ = null;
    /** From */
    private String from__ = null;
    /** 送信先 */
    private String sendAddress__ = null;
    /** charset */
    private String charset__ = null;
    /** 添付ファイルフラグ */
    private boolean tempFlg__ = false;

    /**
     * <p>subject を取得します。
     * @return subject
     */
    public String getSubject() {
        return subject__;
    }

    /**
     * <p>subject をセットします。
     * @param subject subject
     */
    public void setSubject(String subject) {
        subject__ = subject;
    }

    /**
     * <p>body を取得します。
     * @return body
     */
    public String getBody() {
        return body__;
    }

    /**
     * <p>body をセットします。
     * @param body body
     */
    public void setBody(String body) {
        body__ = body;
    }

    /**
     * <p>from を取得します。
     * @return from
     */
    public String getFrom() {
        return from__;
    }

    /**
     * <p>from をセットします。
     * @param from from
     */
    public void setFrom(String from) {
        from__ = from;
    }

    /**
     * <p>charset を取得します。
     * @return charset
     */
    public String getCharset() {
        return charset__;
    }

    /**
     * <p>charset をセットします。
     * @param charset charset
     */
    public void setCharset(String charset) {
        charset__ = charset;
    }

    /**
     * <p>mailNum を取得します。
     * @return mailNum
     */
    public long getMailNum() {
        return mailNum__;
    }

    /**
     * <p>mailNum をセットします。
     * @param mailNum mailNum
     */
    public void setMailNum(long mailNum) {
        mailNum__ = mailNum;
    }

    /**
     * <p>sendAddress を取得します。
     * @return sendAddress
     */
    public String getSendAddress() {
        return sendAddress__;
    }

    /**
     * <p>sendAddress をセットします。
     * @param sendAddress sendAddress
     */
    public void setSendAddress(String sendAddress) {
        sendAddress__ = sendAddress;
    }

    /**
     * <p>tempFlg を取得します。
     * @return tempFlg
     */
    public boolean isTempFlg() {
        return tempFlg__;
    }

    /**
     * <p>tempFlg をセットします。
     * @param tempFlg tempFlg
     */
    public void setTempFlg(boolean tempFlg) {
        tempFlg__ = tempFlg;
    }
}
