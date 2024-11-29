package jp.groupsession.v2.cmn.restapi.users.authentications;

import jp.groupsession.v2.cmn.login.otp.OtpSendResult;

public class OtpNeedResultModel {
    /** メッセージ                                        */
    private String messageText__;
    /** 理由区分コード                                      */
    private String reasonCodeText__;

    /** 一時トークン                                       */
    private String onteimePasswordTokenText__;
    /** 差出人アドレス                                      */
    private String sendMailFrom__;
    /** 送信先アドレス                                      */
    private String sendMailTo__;
    /** メールタイトル                                      */
    private String sendMailSubject__;
    /** 送信日時                                         */
    private String sendMailDateTime__;
    /**
     *
     * コンストラクタ
     * @param otpResult
     */
    public OtpNeedResultModel(OtpSendResult otpResult) {
        onteimePasswordTokenText__ = otpResult.getOtpToken();
        sendMailFrom__ = otpResult.getOtpSendMailFrom();
        sendMailTo__ = otpResult.getOtpSendMailTo();
        sendMailSubject__ = otpResult.getOtpSendMailSubject();
        sendMailDateTime__ = otpResult.getOtpSendDateStr();
    }
    /**
     * <p>messageText を取得します。
     * @return messageText
     * @see OtpNeedResultModel#messageText__
     */
    public String getMessageText() {
        return messageText__;
    }
    /**
     * <p>messageText をセットします。
     * @param messageText messageText
     * @see OtpNeedResultModel#messageText__
     */
    public void setMessageText(String messageText) {
        messageText__ = messageText;
    }
    /**
     * <p>reasonCodeText を取得します。
     * @return reasonCodeText
     * @see OtpNeedResultModel#reasonCodeText__
     */
    public String getReasonCodeText() {
        return reasonCodeText__;
    }
    /**
     * <p>reasonCodeText をセットします。
     * @param reasonCodeText reasonCodeText
     * @see OtpNeedResultModel#reasonCodeText__
     */
    public void setReasonCodeText(String reasonCodeText) {
        reasonCodeText__ = reasonCodeText;
    }
    /**
     * <p>onteimePasswordTokenText を取得します。
     * @return onteimePasswordTokenText
     * @see OtpNeedResultModel#onteimePasswordTokenText__
     */
    public String getOnteimePasswordTokenText() {
        return onteimePasswordTokenText__;
    }
    /**
     * <p>onteimePasswordTokenText をセットします。
     * @param onteimePasswordTokenText onteimePasswordTokenText
     * @see OtpNeedResultModel#onteimePasswordTokenText__
     */
    public void setOnteimePasswordTokenText(String onteimePasswordTokenText) {
        onteimePasswordTokenText__ = onteimePasswordTokenText;
    }
    /**
     * <p>sendMailFrom を取得します。
     * @return sendMailFrom
     * @see OtpNeedResultModel#sendMailFrom__
     */
    public String getSendMailFrom() {
        return sendMailFrom__;
    }
    /**
     * <p>sendMailFrom をセットします。
     * @param sendMailFrom sendMailFrom
     * @see OtpNeedResultModel#sendMailFrom__
     */
    public void setSendMailFrom(String sendMailFrom) {
        sendMailFrom__ = sendMailFrom;
    }
    /**
     * <p>sendMailTo を取得します。
     * @return sendMailTo
     * @see OtpNeedResultModel#sendMailTo__
     */
    public String getSendMailTo() {
        return sendMailTo__;
    }
    /**
     * <p>sendMailTo をセットします。
     * @param sendMailTo sendMailTo
     * @see OtpNeedResultModel#sendMailTo__
     */
    public void setSendMailTo(String sendMailTo) {
        sendMailTo__ = sendMailTo;
    }
    /**
     * <p>sendMailSubject を取得します。
     * @return sendMailSubject
     * @see OtpNeedResultModel#sendMailSubject__
     */
    public String getSendMailSubject() {
        return sendMailSubject__;
    }
    /**
     * <p>sendMailSubject をセットします。
     * @param sendMailSubject sendMailSubject
     * @see OtpNeedResultModel#sendMailSubject__
     */
    public void setSendMailSubject(String sendMailSubject) {
        sendMailSubject__ = sendMailSubject;
    }
    /**
     * <p>sendMailDateTime を取得します。
     * @return sendMailDateTime
     * @see OtpNeedResultModel#sendMailDateTime__
     */
    public String getSendMailDateTime() {
        return sendMailDateTime__;
    }
    /**
     * <p>sendMailDateTime をセットします。
     * @param sendMailDateTime sendMailDateTime
     * @see OtpNeedResultModel#sendMailDateTime__
     */
    public void setSendMailDateTime(String sendMailDateTime) {
        sendMailDateTime__ = sendMailDateTime;
    }


}
