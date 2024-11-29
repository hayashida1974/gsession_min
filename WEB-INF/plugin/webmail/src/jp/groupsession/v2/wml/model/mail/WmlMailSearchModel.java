package jp.groupsession.v2.wml.model.mail;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.wml.wml010.Wml010Const;

/**
 * <br>[機  能] WEBメール メール一覧画面の検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlMailSearchModel extends AbstractModel {

    /** 読み込み開始位置 */
    private int start__ = 0;
    /** 1ページの最大表示件数 */
    private int maxCount__ = 0;
    /** ソートキー */
    private int sortKey__ = 0;
    /** 並び順 */
    private int order__ = GSConstWebmail.ORDER_ASC;

    /** メッセージ番号 */
    private long mailNum__ = 0;
    /** メッセージ番号(複数) */
    private long[] mailNumArray__ = null;

    /** アカウント */
    private int accountSid__ = 0;
    /** ディレクトリ */
    private long directorySid__ = 0;
    /** ディレクトリ種別 */
    private int directoryType__ = 0;
    /** ラベル */
    private int labelSid__ = 0;

    /** from */
    private String from__ = null;
    /** 送信先 */
    private String destination__ = null;
    /** 送信先 To */
    private boolean destinationTo__ = false;
    /** 送信先 Cc */
    private boolean destinationCc__ = false;
    /** 送信先 Bcc */
    private boolean destinationBcc__ = false;

    /** キーワード */
    private String keyword__ = null;

    /** 日付 受信日 From */
    private UDate resvDateFrom__ = null;
    /** 日付 受信日 To */
    private UDate resvDateTo__ = null;
    /** 日付 送信日 From */
    private UDate sendDateFrom__ = null;
    /** 日付 送信日 To */
    private UDate sendDateTo__ = null;
    /** 添付ファイル */
    private boolean tempFile__ = false;

    /** 未読/既読 */
    private int readKbn__ = Wml010Const.SEARCH_READKBN_NOSET;

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
     * <p>mailNumArray を取得します。
     * @return mailNumArray
     */
    public long[] getMailNumArray() {
        return mailNumArray__;
    }
    /**
     * <p>mailNumArray をセットします。
     * @param mailNumArray mailNumArray
     */
    public void setMailNumArray(long[] mailNumArray) {
        mailNumArray__ = mailNumArray;
    }
    /**
     * <p>accountSid を取得します。
     * @return accountSid
     */
    public int getAccountSid() {
        return accountSid__;
    }
    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     */
    public void setAccountSid(int accountSid) {
        accountSid__ = accountSid;
    }
    /**
     * <p>directorySid を取得します。
     * @return directorySid
     */
    public long getDirectorySid() {
        return directorySid__;
    }
    /**
     * <p>directorySid をセットします。
     * @param directorySid directorySid
     */
    public void setDirectorySid(long directorySid) {
        directorySid__ = directorySid;
    }
    /**
     * <p>directoryType を取得します。
     * @return directoryType
     */
    public int getDirectoryType() {
        return directoryType__;
    }
    /**
     * <p>directoryType をセットします。
     * @param directoryType directoryType
     */
    public void setDirectoryType(int directoryType) {
        directoryType__ = directoryType;
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
     * <p>keyword を取得します。
     * @return keyword
     */
    public String getKeyword() {
        return keyword__;
    }
    /**
     * <p>keyword をセットします。
     * @param keyword keyword
     */
    public void setKeyword(String keyword) {
        keyword__ = keyword;
    }
    /**
     * <p>order を取得します。
     * @return order
     */
    public int getOrder() {
        return order__;
    }
    /**
     * <p>order をセットします。
     * @param order order
     */
    public void setOrder(int order) {
        order__ = order;
    }
    /**
     * <p>maxCount を取得します。
     * @return maxCount
     */
    public int getMaxCount() {
        return maxCount__;
    }
    /**
     * <p>maxCount をセットします。
     * @param maxCount maxCount
     */
    public void setMaxCount(int maxCount) {
        maxCount__ = maxCount;
    }
    /**
     * <p>start を取得します。
     * @return start
     */
    public int getStart() {
        return start__;
    }
    /**
     * <p>start をセットします。
     * @param start start
     */
    public void setStart(int start) {
        start__ = start;
    }
    /**
     * <p>resvDateFrom を取得します。
     * @return resvDateFrom
     */
    public UDate getResvDateFrom() {
        return resvDateFrom__;
    }
    /**
     * <p>resvDateFrom をセットします。
     * @param resvDateFrom resvDateFrom
     */
    public void setResvDateFrom(UDate resvDateFrom) {
        resvDateFrom__ = resvDateFrom;
    }
    /**
     * <p>resvDateTo を取得します。
     * @return resvDateTo
     */
    public UDate getResvDateTo() {
        return resvDateTo__;
    }
    /**
     * <p>resvDateTo をセットします。
     * @param resvDateTo resvDateTo
     */
    public void setResvDateTo(UDate resvDateTo) {
        resvDateTo__ = resvDateTo;
    }
    /**
     * <p>sendDateFrom を取得します。
     * @return sendDateFrom
     */
    public UDate getSendDateFrom() {
        return sendDateFrom__;
    }
    /**
     * <p>sendDateFrom をセットします。
     * @param sendDateFrom sendDateFrom
     */
    public void setSendDateFrom(UDate sendDateFrom) {
        sendDateFrom__ = sendDateFrom;
    }
    /**
     * <p>sendDateTo を取得します。
     * @return sendDateTo
     */
    public UDate getSendDateTo() {
        return sendDateTo__;
    }
    /**
     * <p>sendDateTo をセットします。
     * @param sendDateTo sendDateTo
     */
    public void setSendDateTo(UDate sendDateTo) {
        sendDateTo__ = sendDateTo;
    }
    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public int getSortKey() {
        return sortKey__;
    }
    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
    }
    /**
     * <p>tempFile を取得します。
     * @return tempFile
     */
    public boolean isTempFile() {
        return tempFile__;
    }
    /**
     * <p>tempFile をセットします。
     * @param tempFile tempFile
     */
    public void setTempFile(boolean tempFile) {
        tempFile__ = tempFile;
    }
    /**
     * <p>destination を取得します。
     * @return destination
     */
    public String getDestination() {
        return destination__;
    }
    /**
     * <p>destination をセットします。
     * @param destination destination
     */
    public void setDestination(String destination) {
        destination__ = destination;
    }
    /**
     * <p>destinationTo を取得します。
     * @return destinationTo
     */
    public boolean getDestinationTo() {
        return destinationTo__;
    }
    /**
     * <p>destinationTo をセットします。
     * @param destinationTo destinationTo
     */
    public void setDestinationTo(boolean destinationTo) {
        destinationTo__ = destinationTo;
    }
    /**
     * <p>destinationCc を取得します。
     * @return destinationCc
     */
    public boolean getDestinationCc() {
        return destinationCc__;
    }
    /**
     * <p>destinationCc をセットします。
     * @param destinationCc destinationCc
     */
    public void setDestinationCc(boolean destinationCc) {
        destinationCc__ = destinationCc;
    }
    /**
     * <p>destinationBcc を取得します。
     * @return destinationBcc
     */
    public boolean getDestinationBcc() {
        return destinationBcc__;
    }
    /**
     * <p>destinationBcc をセットします。
     * @param destinationBcc destinationBcc
     */
    public void setDestinationBcc(boolean destinationBcc) {
        destinationBcc__ = destinationBcc;
    }
    /**
     * <p>labelSid を取得します。
     * @return labelSid
     */
    public int getLabelSid() {
        return labelSid__;
    }
    /**
     * <p>labelSid をセットします。
     * @param labelSid labelSid
     */
    public void setLabelSid(int labelSid) {
        labelSid__ = labelSid;
    }
    /**
     * <p>readKbn を取得します。
     * @return readKbn
     */
    public int getReadKbn() {
        return readKbn__;
    }
    /**
     * <p>readKbn をセットします。
     * @param readKbn readKbn
     */
    public void setReadKbn(int readKbn) {
        readKbn__ = readKbn;
    }
}
