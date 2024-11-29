package jp.groupsession.v2.wml.wml320;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] WEBメール一括削除リスト検索用のモデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class Wml320SearchModel implements Serializable {

    /** ユーザSID */
    private int usrSid__;
    /** アカウント */
    private int account__;
    /** ディレクトリ */
    private int[] directory__;
    /** 検索条件 キーワード */
    private String keyword__;
    /** 検索条件 From */
    private String from__;
    /** 検索条件 未読/既読 */
    private int readed__;
    /** 検索条件 送信先 */
    private String dest__;
    /** 検索条件 送信先 対象 To*/
    private int destTypeTo__;
    /** 検索条件 送信先 対象 Bcc*/
    private int destTypeBcc__;
    /** 検索条件 送信先 対象 Cc*/
    private int destTypeCc__;
    /** 検索条件 添付ファイル */
    private int attach__;
    /** 検索条件 日付選択 */
    private int dateType__;
    /** 検索条件 日付 From 年 */
    private UDate dateFr__;
    /** 検索条件 日付 To 年 */
    private UDate dateTo__;
    /** 検索条件 ラベル */
    private int label__;
    /** 検索条件 サイズ */
    private int size__;

    /** 検索条件 ソートキー */
    private int sortKey__ = 0;
    /** 検索条件 オーダーキー */
    private int order__ = 0;

    /** 削除件数上限 */
    private int delLimit__ = 0;

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#usrSid__
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#usrSid__
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>account を取得します。
     * @return account
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#account__
     */
    public int getAccount() {
        return account__;
    }
    /**
     * <p>account をセットします。
     * @param account account
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#account__
     */
    public void setAccount(int account) {
        account__ = account;
    }

    /**
     * <p>directory を取得します。
     * @return directory
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#directory__
     */
    public int[] getDirectory() {
        return directory__;
    }
    /**
     * <p>directory をセットします。
     * @param directory directory
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#directory__
     */
    public void setDirectory(int[] directory) {
        directory__ = directory;
    }
    /**
     * <p>keyword を取得します。
     * @return keyword
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#keyword__
     */
    public String getKeyword() {
        return keyword__;
    }
    /**
     * <p>keyword をセットします。
     * @param keyword keyword
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#keyword__
     */
    public void setKeyword(String keyword) {
        keyword__ = keyword;
    }
    /**
     * <p>from を取得します。
     * @return from
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#from__
     */
    public String getFrom() {
        return from__;
    }
    /**
     * <p>from をセットします。
     * @param from from
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#from__
     */
    public void setFrom(String from) {
        from__ = from;
    }
    /**
     * <p>readed を取得します。
     * @return readed
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#readed__
     */
    public int getReaded() {
        return readed__;
    }
    /**
     * <p>readed をセットします。
     * @param readed readed
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#readed__
     */
    public void setReaded(int readed) {
        readed__ = readed;
    }
    /**
     * <p>dest を取得します。
     * @return dest
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#dest__
     */
    public String getDest() {
        return dest__;
    }
    /**
     * <p>dest をセットします。
     * @param dest dest
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#dest__
     */
    public void setDest(String dest) {
        dest__ = dest;
    }
    /**
     * <p>destTypeTo を取得します。
     * @return destTypeTo
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#destTypeTo__
     */
    public int getDestTypeTo() {
        return destTypeTo__;
    }
    /**
     * <p>destTypeTo をセットします。
     * @param destTypeTo destTypeTo
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#destTypeTo__
     */
    public void setDestTypeTo(int destTypeTo) {
        destTypeTo__ = destTypeTo;
    }
    /**
     * <p>destTypeBcc を取得します。
     * @return destTypeBcc
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#destTypeBcc__
     */
    public int getDestTypeBcc() {
        return destTypeBcc__;
    }
    /**
     * <p>destTypeBcc をセットします。
     * @param destTypeBcc destTypeBcc
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#destTypeBcc__
     */
    public void setDestTypeBcc(int destTypeBcc) {
        destTypeBcc__ = destTypeBcc;
    }
    /**
     * <p>destTypeCc を取得します。
     * @return destTypeCc
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#destTypeCc__
     */
    public int getDestTypeCc() {
        return destTypeCc__;
    }
    /**
     * <p>destTypeCc をセットします。
     * @param destTypeCc destTypeCc
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#destTypeCc__
     */
    public void setDestTypeCc(int destTypeCc) {
        destTypeCc__ = destTypeCc;
    }
    /**
     * <p>attach を取得します。
     * @return attach
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#attach__
     */
    public int getAttach() {
        return attach__;
    }
    /**
     * <p>attach をセットします。
     * @param attach attach
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#attach__
     */
    public void setAttach(int attach) {
        attach__ = attach;
    }
    /**
     * <p>dateType を取得します。
     * @return dateType
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#dateType__
     */
    public int getDateType() {
        return dateType__;
    }
    /**
     * <p>dateType をセットします。
     * @param dateType dateType
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#dateType__
     */
    public void setDateType(int dateType) {
        dateType__ = dateType;
    }
    /**
     * <p>dateFr を取得します。
     * @return dateFr
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#dateFr__
     */
    public UDate getDateFr() {
        return dateFr__;
    }
    /**
     * <p>dateFr をセットします。
     * @param dateFr dateFr
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#dateFr__
     */
    public void setDateFr(UDate dateFr) {
        dateFr__ = dateFr;
    }
    /**
     * <p>dateTo を取得します。
     * @return dateTo
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#dateTo__
     */
    public UDate getDateTo() {
        return dateTo__;
    }
    /**
     * <p>dateTo をセットします。
     * @param dateTo dateTo
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#dateTo__
     */
    public void setDateTo(UDate dateTo) {
        dateTo__ = dateTo;
    }
    /**
     * <p>label を取得します。
     * @return label
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#label__
     */
    public int getLabel() {
        return label__;
    }
    /**
     * <p>label をセットします。
     * @param label label
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#label__
     */
    public void setLabel(int label) {
        label__ = label;
    }
    /**
     * <p>size を取得します。
     * @return size
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#size__
     */
    public int getSize() {
        return size__;
    }
    /**
     * <p>size をセットします。
     * @param size size
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#size__
     */
    public void setSize(int size) {
        size__ = size;
    }
    /**
     * <p>sortKey を取得します。
     * @return sortKey
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#sortKey__
     */
    public int getSortKey() {
        return sortKey__;
    }
    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#sortKey__
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
    }
    /**
     * <p>order を取得します。
     * @return order
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#order__
     */
    public int getOrder() {
        return order__;
    }
    /**
     * <p>order をセットします。
     * @param order order
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#order__
     */
    public void setOrder(int order) {
        order__ = order;
    }
    /**
     * <p>delLimit を取得します。
     * @return delLimit
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#delLimit__
     */
    public int getDelLimit() {
        return delLimit__;
    }
    /**
     * <p>delLimit をセットします。
     * @param delLimit delLimit
     * @see jp.groupsession.v2.wml.wml320.Wml320SearchModel#delLimit__
     */
    public void setDelLimit(int delLimit) {
        delLimit__ = delLimit;
    }
}