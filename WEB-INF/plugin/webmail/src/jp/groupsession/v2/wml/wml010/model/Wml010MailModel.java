package jp.groupsession.v2.wml.wml010.model;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.wml.model.MailTempFileModel;

/**
 * <br>[機  能] WEBメール メール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml010MailModel extends AbstractModel {

    /** メッセージ番号 */
    private long mailNum__ = 0;
    /** ディレクトリSID */
    private long dirSid__ = 0;
    /** ディレクトリ種別 */
    private int dirType__ = 0;
    /** 本文 */
    private String body__ = null;
    /** 日時 */
    private UDate date__ = null;
    /** 既読 */
    private boolean readed__ = false;
    /** 返信 */
    private boolean reply__ = false;
    /** 転送 */
    private boolean forward__ = false;
    /** from */
    private String from__ = null;
    /** 件名 */
    private String subject__ = null;
    /** 添付ファイル */
    private boolean attach__ = false;
    /** 編集可能メール */
    private boolean canEditMail__ = false;
    /** サイズ */
    private long mailSize__ = 0;
    /** ラベル情報一覧 */
    private List<Wml010LabelModel> labelList__ = null;
    /** 送信先 */
    private Wml010SendAddrModel sendAddress__ = null;
    /** 添付ファイル */
    private List<MailTempFileModel> tempFileList__ = null;

    /** 送信待ちメール */
    private boolean sendWaitMail__ = false;
    /** 送信予約 送信区分 */
    private int sendPlanKbn__ = 0;
    /** 送信予約 送信予定日時 */
    private UDate sendPlanDate__ = null;
    /** 送信予約 添付ファイルの圧縮 */
    private int sendPlanCompressFile__ = 0;

    /**
     * <p>attach を取得します。
     * @return attach
     */
    public boolean isAttach() {
        return attach__;
    }
    /**
     * <p>attach をセットします。
     * @param attach attach
     */
    public void setAttach(boolean attach) {
        attach__ = attach;
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
     * <p>date を取得します。
     * @return date
     */
    public UDate getDate() {
        return date__;
    }
    /**
     * <p>date をセットします。
     * @param date date
     */
    public void setDate(UDate date) {
        date__ = date;
    }
    /**
     * <p>dirSid を取得します。
     * @return dirSid
     */
    public long getDirSid() {
        return dirSid__;
    }
    /**
     * <p>dirSid をセットします。
     * @param dirSid dirSid
     */
    public void setDirSid(long dirSid) {
        dirSid__ = dirSid;
    }
    /**
     * <p>dirType を取得します。
     * @return dirType
     * @see jp.groupsession.v2.wml.wml010.model.Wml010MailModel#dirType__
     */
    public int getDirType() {
        return dirType__;
    }
    /**
     * <p>dirType をセットします。
     * @param dirType dirType
     * @see jp.groupsession.v2.wml.wml010.model.Wml010MailModel#dirType__
     */
    public void setDirType(int dirType) {
        dirType__ = dirType;
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
     * <p>labelList を取得します。
     * @return labelList
     */
    public List<Wml010LabelModel> getLabelList() {
        return labelList__;
    }
    /**
     * <p>labelList をセットします。
     * @param labelList labelList
     */
    public void setLabelList(List<Wml010LabelModel> labelList) {
        labelList__ = labelList;
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
     * <p>readed を取得します。
     * @return readed
     */
    public boolean isReaded() {
        return readed__;
    }
    /**
     * <p>readed をセットします。
     * @param readed readed
     */
    public void setReaded(boolean readed) {
        readed__ = readed;
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
     * <p>sendAddress を取得します。
     * @return sendAddress
     */
    public Wml010SendAddrModel getSendAddress() {
        return sendAddress__;
    }
    /**
     * <p>sendAddress をセットします。
     * @param sendAddress sendAddress
     */
    public void setSendAddress(Wml010SendAddrModel sendAddress) {
        sendAddress__ = sendAddress;
    }
    /**
     * <p>forward を取得します。
     * @return forward
     */
    public boolean isForward() {
        return forward__;
    }
    /**
     * <p>forward をセットします。
     * @param forward forward
     */
    public void setForward(boolean forward) {
        forward__ = forward;
    }
    /**
     * <p>reply を取得します。
     * @return reply
     */
    public boolean isReply() {
        return reply__;
    }
    /**
     * <p>canEditMail を取得します。
     * @return canEditMail
     */
    public boolean isCanEditMail() {
        return canEditMail__;
    }
    /**
     * <p>canEditMail をセットします。
     * @param canEditMail canEditMail
     */
    public void setCanEditMail(boolean canEditMail) {
        canEditMail__ = canEditMail;
    }
    /**
     * <p>reply をセットします。
     * @param reply reply
     */
    public void setReply(boolean reply) {
        reply__ = reply;
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
     * <p>sendWaitMail を取得します。
     * @return sendWaitMail
     */
    public boolean isSendWaitMail() {
        return sendWaitMail__;
    }
    /**
     * <p>sendWaitMail をセットします。
     * @param sendWaitMail sendWaitMail
     */
    public void setSendWaitMail(boolean sendWaitMail) {
        sendWaitMail__ = sendWaitMail;
    }
    /**
     * <p>sendPlanKbn を取得します。
     * @return sendPlanKbn
     */
    public int getSendPlanKbn() {
        return sendPlanKbn__;
    }
    /**
     * <p>sendPlanKbn をセットします。
     * @param sendPlanKbn sendPlanKbn
     */
    public void setSendPlanKbn(int sendPlanKbn) {
        sendPlanKbn__ = sendPlanKbn;
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
     * <p>sendPlanCompressFile を取得します。
     * @return sendPlanCompressFile
     */
    public int getSendPlanCompressFile() {
        return sendPlanCompressFile__;
    }
    /**
     * <p>sendPlanCompressFile をセットします。
     * @param sendPlanCompressFile sendPlanCompressFile
     */
    public void setSendPlanCompressFile(int sendPlanCompressFile) {
        sendPlanCompressFile__ = sendPlanCompressFile;
    }
    /**
     * <p>tempFileList を取得します。
     * @return tempFileList
     */
    public List<MailTempFileModel> getTempFileList() {
        return tempFileList__;
    }
    /**
     * <p>tempFileList をセットします。
     * @param tempFileList tempFileList
     */
    public void setTempFileList(List<MailTempFileModel> tempFileList) {
        tempFileList__ = tempFileList;
    }
    /**
     * <p>日付(文字列)を取得します。
     * @return date
     */
    public String getDateString() {
        if (date__ == null) {
            return null;
        }

        return UDateUtil.getSlashYYMD(date__) + " " + UDateUtil.getSeparateHMS(date__);
    }
}
