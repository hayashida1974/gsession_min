package jp.groupsession.v2.wml.wml010.model;

import java.io.File;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール メールエクスポートファイル情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml010ExportFileModel extends AbstractModel {

    /** メッセージ番号 */
    private long messageNum__ = 0;
    /** アカウント名 */
    private String accountName__  = null;
    /** 件名 */
    private String subject__ = null;
    /** From */
    private String from__ = null;
    /** To */
    private List<String> toList__ = null;
    /** Cc */
    private List<String> ccList__ = null;
    /** Bcc */
    private List<String> bccList__ = null;
    /** 送信日時 */
    private UDate sdate__ = null;
    /** ファイルパス */
    private File filePath__ = null;

    /** メッセージ番号(複数エクスポート) */
    private long[] messageNumList__ = null;

    /**
     * <p>messageNum を取得します。
     * @return messageNum
     */
    public long getMessageNum() {
        return messageNum__;
    }
    /**
     * <p>messageNum をセットします。
     * @param messageNum messageNum
     */
    public void setMessageNum(long messageNum) {
        messageNum__ = messageNum;
    }
    /**
     * <p>filePath を取得します。
     * @return filePath
     */
    public File getFilePath() {
        return filePath__;
    }
    /**
     * <p>sdate を取得します。
     * @return sdate
     */
    public UDate getSdate() {
        return sdate__;
    }
    /**
     * <p>sdate をセットします。
     * @param sdate sdate
     */
    public void setSdate(UDate sdate) {
        sdate__ = sdate;
    }
    /**
     * <p>filePath をセットします。
     * @param filePath filePath
     */
    public void setFilePath(File filePath) {
        filePath__ = filePath;
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
     * <p>accountName を取得します。
     * @return accountName
     */
    public String getAccountName() {
        return accountName__;
    }
    /**
     * <p>accountName をセットします。
     * @param accountName accountName
     */
    public void setAccountName(String accountName) {
        accountName__ = accountName;
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
     * <p>toList を取得します。
     * @return toList
     */
    public List<String> getToList() {
        return toList__;
    }
    /**
     * <p>toList をセットします。
     * @param toList toList
     */
    public void setToList(List<String> toList) {
        toList__ = toList;
    }
    /**
     * <p>ccList を取得します。
     * @return ccList
     */
    public List<String> getCcList() {
        return ccList__;
    }
    /**
     * <p>ccList をセットします。
     * @param ccList ccList
     */
    public void setCcList(List<String> ccList) {
        ccList__ = ccList;
    }
    /**
     * <p>bccList を取得します。
     * @return bccList
     */
    public List<String> getBccList() {
        return bccList__;
    }
    /**
     * <p>bccList をセットします。
     * @param bccList bccList
     */
    public void setBccList(List<String> bccList) {
        bccList__ = bccList;
    }
    /**
     * <p>messageNumList を取得します。
     * @return messageNumList
     */
    public long[] getMessageNumList() {
        return messageNumList__;
    }
    /**
     * <p>messageNumList をセットします。
     * @param messageNumList messageNumList
     */
    public void setMessageNumList(long[] messageNumList) {
        messageNumList__ = messageNumList;
    }

}
