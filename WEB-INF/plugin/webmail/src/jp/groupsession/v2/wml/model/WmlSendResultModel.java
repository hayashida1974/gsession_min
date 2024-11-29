package jp.groupsession.v2.wml.model;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;

/**
 * <br>[機  能] メール送信処理の結果を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlSendResultModel extends AbstractModel {
    /** 処理結果 成功 */
    public static final int RESULT_SUCCESS = 0;
    /** 処理結果 サイズ上限エラー */
    public static final int RESULT_SIZEOVER = 1;
    /** 処理結果 サーバ接続エラー */
    public static final int RESULT_SERVERERROR = 2;

    /** 処理結果 */
    private int result__ = RESULT_SUCCESS;
    /** 送信メール情報 */
    private SmtpSendModel sendMailData__ = null;
    /** 送信メールサイズ */
    private long mailSize__ = 0;
    /** 送信メール最大サイズ */
    private long mailMaxSize__ = 0;
    /** 送信メールのメールSID */
    private long mailNum__ = 0;

    /**
     * <p>result を取得します。
     * @return result
     */
    public int getResult() {
        return result__;
    }
    /**
     * <p>result をセットします。
     * @param result result
     */
    public void setResult(int result) {
        result__ = result;
    }
    /**
     * <p>sendMailData を取得します。
     * @return sendMailData
     */
    public SmtpSendModel getSendMailData() {
        return sendMailData__;
    }
    /**
     * <p>sendMailData をセットします。
     * @param sendMailData sendMailData
     */
    public void setSendMailData(SmtpSendModel sendMailData) {
        sendMailData__ = sendMailData;
    }
    /**
     * <p>mailSize を取得します。
     * @return mailSize
     */
    public long getMailSize() {
        return mailSize__;
    }
    /**
     * <p>mailSize をセットします。
     * @param mailSize mailSize
     */
    public void setMailSize(long mailSize) {
        mailSize__ = mailSize;
    }
    /**
     * <p>mailMaxSize を取得します。
     * @return mailMaxSize
     */
    public long getMailMaxSize() {
        return mailMaxSize__;
    }
    /**
     * <p>mailMaxSize をセットします。
     * @param mailMaxSize mailMaxSize
     */
    public void setMailMaxSize(long mailMaxSize) {
        mailMaxSize__ = mailMaxSize;
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
}
