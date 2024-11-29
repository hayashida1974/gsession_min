package jp.groupsession.v2.wml.wml010.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;

/**
 * <br>[機  能] WEBメール 送信メール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml010SendMailModel extends AbstractModel {

    /** 宛先 */
    String to__ = "";
    /** CC */
    String cc__ = "";
    /** BCC */
    String bcc__ = "";
    /** 件名 */
    String subject__ = "";
    /** 本文 */
    String content__ = "";
    /** 添付ファイル */
    String fileList__ = "";

    /** アカウントSID */
    int wacSid__ = 0;
    /** 返信/編集元メッセージ番号 */
    long sendMessageNum__ = 0;
    /** 送信種別 */
    private int sendMailType__ = GSConstWebmail.SEND_TYPE_NORMAL;
    /** HTMLメールフラグ */
    private boolean htmlMail__ = false;

    /** 後で送信 */
    private boolean timeSent__ = false;
    /** 後で送信 送信日 */
    private UDate sendPlanDate__ = null;

    /** 送信メール情報 */
    private SmtpSendModel sendData__ = null;
    /** 添付ファイルの圧縮 */
    private boolean archiveMail__ = false;
    /** 添付ファイルの圧縮 ファイルパス */
    private String archiveFilePath__ = null;
    /** 添付ファイルの圧縮 パスワード */
    private String archivePassword__ = null;
    /** 添付ファイルの圧縮 画面指定 */
    private int compressFileType__ = 0;

    /** テンポラリディレクトリID */
    String tempDirId__ = null;

    /**
     * <p>bcc を取得します。
     * @return bcc
     */
    public String getBcc() {
        return bcc__;
    }
    /**
     * <p>bcc をセットします。
     * @param bcc bcc
     */
    public void setBcc(String bcc) {
        bcc__ = bcc;
    }
    /**
     * <p>cc を取得します。
     * @return cc
     */
    public String getCc() {
        return cc__;
    }
    /**
     * <p>cc をセットします。
     * @param cc cc
     */
    public void setCc(String cc) {
        cc__ = cc;
    }
    /**
     * <p>content を取得します。
     * @return content
     */
    public String getContent() {
        return content__;
    }
    /**
     * <p>content をセットします。
     * @param content content
     */
    public void setContent(String content) {
        content__ = content;
    }
    /**
     * <p>fileList を取得します。
     * @return fileList
     */
    public String getFileList() {
        return fileList__;
    }
    /**
     * <p>fileList をセットします。
     * @param fileList fileList
     */
    public void setFileList(String fileList) {
        fileList__ = fileList;
    }
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
     * <p>sendMailType を取得します。
     * @return sendMailType
     */
    public int getSendMailType() {
        return sendMailType__;
    }
    /**
     * <p>sendMailType をセットします。
     * @param sendMailType sendMailType
     */
    public void setSendMailType(int sendMailType) {
        sendMailType__ = sendMailType;
    }
    /**
     * <p>sendMessageNum を取得します。
     * @return sendMessageNum
     */
    public long getSendMessageNum() {
        return sendMessageNum__;
    }
    /**
     * <p>sendMessageNum をセットします。
     * @param sendMessageNum sendMessageNum
     */
    public void setSendMessageNum(long sendMessageNum) {
        sendMessageNum__ = sendMessageNum;
    }
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
     * <p>to を取得します。
     * @return to
     */
    public String getTo() {
        return to__;
    }
    /**
     * <p>to をセットします。
     * @param to to
     */
    public void setTo(String to) {
        to__ = to;
    }
    /**
     * <p>wacSid を取得します。
     * @return wacSid
     */
    public int getWacSid() {
        return wacSid__;
    }
    /**
     * <p>wacSid をセットします。
     * @param wacSid wacSid
     */
    public void setWacSid(int wacSid) {
        wacSid__ = wacSid;
    }
    /**
     * <p>timeSent を取得します。
     * @return timeSent
     */
    public boolean isTimeSent() {
        return timeSent__;
    }
    /**
     * <p>timeSent をセットします。
     * @param timeSent timeSent
     */
    public void setTimeSent(boolean timeSent) {
        timeSent__ = timeSent;
    }
    /**
     * <p>sendPlanDate を取得します。
     * @return sendPlanDate
     */
    public UDate getSendPlanDate() {
        return sendPlanDate__;
    }
    /**
     * <p>sendPlanDate をセットします。
     * @param sendPlanDate sendPlanDate
     */
    public void setSendPlanDate(UDate sendPlanDate) {
        sendPlanDate__ = sendPlanDate;
    }
    /**
     * <p>sendData を取得します。
     * @return sendData
     */
    public SmtpSendModel getSendData() {
        return sendData__;
    }
    /**
     * <p>sendData をセットします。
     * @param sendData sendData
     */
    public void setSendData(SmtpSendModel sendData) {
        sendData__ = sendData;
    }
    /**
     * <p>archiveFilePath を取得します。
     * @return archiveFilePath
     */
    public String getArchiveFilePath() {
        return archiveFilePath__;
    }
    /**
     * <p>archiveFilePath をセットします。
     * @param archiveFilePath archiveFilePath
     */
    public void setArchiveFilePath(String archiveFilePath) {
        archiveFilePath__ = archiveFilePath;
    }
    /**
     * <p>archiveMail を取得します。
     * @return archiveMail
     */
    public boolean isArchiveMail() {
        return archiveMail__;
    }
    /**
     * <p>archiveMail をセットします。
     * @param archiveMail archiveMail
     */
    public void setArchiveMail(boolean archiveMail) {
        archiveMail__ = archiveMail;
    }
    /**
     * <p>archivePassword を取得します。
     * @return archivePassword
     */
    public String getArchivePassword() {
        return archivePassword__;
    }
    /**
     * <p>archivePassword をセットします。
     * @param archivePassword archivePassword
     */
    public void setArchivePassword(String archivePassword) {
        archivePassword__ = archivePassword;
    }
    /**
     * <p>compressFileType を取得します。
     * @return compressFileType
     */
    public int getCompressFileType() {
        return compressFileType__;
    }
    /**
     * <p>compressFileType をセットします。
     * @param compressFileType compressFileType
     */
    public void setCompressFileType(int compressFileType) {
        compressFileType__ = compressFileType;
    }
    /**
     * <p>tempDirId を取得します。
     * @return tempDirId
     */
    public String getTempDirId() {
        return tempDirId__;
    }
    /**
     * <p>tempDirId をセットします。
     * @param tempDirId tempDirId
     */
    public void setTempDirId(String tempDirId) {
        tempDirId__ = tempDirId;
    }
}
