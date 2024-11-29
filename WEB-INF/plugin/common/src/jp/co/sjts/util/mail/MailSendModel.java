package jp.co.sjts.util.mail;

import java.util.Map;

/**
 * <br>[機 能] メール送信用の簡易情報を格納するModelクラス
 * <br>[解 説]
 * <br>[備 考]
 * @author JTS
 */
public class MailSendModel {

    /** メール種別 */
    private int mailKb__;
    /** From メールアドレス */
    private String fromAddress__;
    /** To メールアドレス */
    private String toAddress__;
    /** メールタイトル */
    private String subject__;
    /** メール本文 */
    @SuppressWarnings("all")
    private Map pattern__;

    /**
     * @return pattern を戻します。
     */
    @SuppressWarnings("all")
    public Map getPattern() {
        return pattern__;
    }
    /**
     * @param pattern pattern を設定。
     */
    @SuppressWarnings("all")
    public void setPattern(Map pattern) {
        pattern__ = pattern;
    }
    /**
     * @return fromAddress を戻します。
     */
    public String getFromAddress() {
        return fromAddress__;
    }
    /**
     * @param fromAddress fromAddress を設定。
     */
    public void setFromAddress(String fromAddress) {
        fromAddress__ = fromAddress;
    }
    /**
     * @return mailKb を戻します。
     */
    public int getMailKb() {
        return mailKb__;
    }
    /**
     * @param mailKb mailKb を設定。
     */
    public void setMailKb(int mailKb) {
        mailKb__ = mailKb;
    }
    /**
     * @return toAddress を戻します。
     */
    public String getToAddress() {
        return toAddress__;
    }
    /**
     * @param toAddress toAddress を設定。
     */
    public void setToAddress(String toAddress) {
        toAddress__ = toAddress;
    }

    /**
     * @return subject を戻します。
     */
    public String getSubject() {
        return subject__;
    }
    /**
     * @param subject subject を設定。
     */
    public void setSubject(String subject) {
        subject__ = subject;
    }
}