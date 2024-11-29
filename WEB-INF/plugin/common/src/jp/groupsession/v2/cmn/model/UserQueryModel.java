package jp.groupsession.v2.cmn.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.EnumSeibetu;

public class UserQueryModel {
    /** 検索キーワード */
    private String keywordText__ = null;
    /** グループID                                       */
    private String groupId__;
    /** グループSID                                       */
    private Integer groupSid__;
    /** マイグループSID                                        */
    private Integer myGroupSid__;
    /** カナ開始検索                                       */
    private String seimeiKnStartText__;
    /** 社員番号検索フラグ                                        */
    private String shainNoText__;
    /** 氏名検索フラグ                                      */
    private String seimeiText__;
    /** 氏名カナ検索フラグ                                        */
    private String seimeiKnText__;
    /** 電話番号検索フラグ                                        */
    private String telText__;
    /** メール検索フラグ                                         */
    private String mailText__;
    /** 性別                                         */
    private EnumSeibetu seibetuType__ = EnumSeibetu.ALL;
    /** 年齢From                                       */
    private Integer ageFromNum__;
    /** 年齢To                                         */
    private Integer ageToNum__;
    /** 入社年月日From                                       */
    private UDate entranceFromDate__;
    /** 入社年月日To                                         */
    private UDate entranceToDate__;
    /** 役職Sid                                        */
    private Integer postsSid__;
    /** メール                                      */
    private String mailAddressText__;
    /** 都道府県SID                                      */
    private Integer todofukenSid__;
    /** ラベルSID                                       */
    private int[] labelSidArray__;

    /** 取得件数                                         */
    private Integer limit__;
    /** 取得開始位置                                       */
    private Integer offset__;
    /** ソート1キー                                       */
    private int sortKeyType__ = GSConstUser.USER_SORT_NAME;
    /** ソート1昇順降順                                         */
    private int sortOrderFlg__ = GSConst.ORDER_KEY_ASC;
    /** ソート2キー                                       */
    private int sortKey2Type__ = GSConstUser.USER_SORT_SNO;
    /** ソート2昇順降順                                         */
    private int sortOrder2Flg__ = GSConst.ORDER_KEY_ASC;
    /** カナ順取得開始位置 */
    private String kanaStartOffsetText__ = null;


    /**
     * <p>groupId を取得します。
     * @return groupId
     * @see UserQueryModel#groupId__
     */
    public String getGroupId() {
        return groupId__;
    }
    /**
     * <p>groupId をセットします。
     * @param groupId groupId
     * @see UserQueryModel#groupId__
     */
    public void setGroupId(String groupId) {
        groupId__ = groupId;
    }
    /**
     * <p>groupId を取得します。
     * @return groupId
     * @see UserQueryModel#groupId__
     */
    public Integer getGroupSid() {
        return groupSid__;
    }
    /**
     * <p>groupId をセットします。
     * @param groupId groupId
     * @see UserQueryModel#groupId__
     */
    public void setGroupSid(Integer groupId) {
        groupSid__ = groupId;
    }
    /**
     * <p>myGroupSid を取得します。
     * @return myGroupSid
     * @see UserQueryModel#myGroupSid__
     */
    public Integer getMyGroupSid() {
        return myGroupSid__;
    }
    /**
     * <p>myGroupSid をセットします。
     * @param myGroupSid myGroupSid
     * @see UserQueryModel#myGroupSid__
     */
    public void setMyGroupSid(Integer myGroupSid) {
        myGroupSid__ = myGroupSid;
    }
    /**
     * <p>seimeiKnStartText を取得します。
     * @return seimeiKnStartText
     * @see UserQueryModel#seimeiKnStartText__
     */
    public String getSeimeiKnStartText() {
        return seimeiKnStartText__;
    }
    /**
     * <p>seimeiKnStartText をセットします。
     * @param seimeiKnStartText seimeiKnStartText
     * @see UserQueryModel#seimeiKnStartText__
     */
    public void setSeimeiKnStartText(String seimeiKnStartText) {
        seimeiKnStartText__ = seimeiKnStartText;
    }
    /**
     * <p>shainNoText を取得します。
     * @return shainNoText
     * @see UserQueryModel#shainNoText__
     */
    public String getShainNoText() {
        return shainNoText__;
    }
    /**
     * <p>shainNoText をセットします。
     * @param shainNoText shainNoText
     * @see UserQueryModel#shainNoText__
     */
    public void setShainNoText(String shainNoText) {
        shainNoText__ = shainNoText;
    }
    /**
     * <p>seimeiText を取得します。
     * @return seimeiText
     * @see UserQueryModel#seimeiText__
     */
    public String getSeimeiText() {
        return seimeiText__;
    }
    /**
     * <p>seimeiText をセットします。
     * @param seimeiText seimeiText
     * @see UserQueryModel#seimeiText__
     */
    public void setSeimeiText(String seimeiText) {
        seimeiText__ = seimeiText;
    }
    /**
     * <p>seimeiKnText を取得します。
     * @return seimeiKnText
     * @see UserQueryModel#seimeiKnText__
     */
    public String getSeimeiKnText() {
        return seimeiKnText__;
    }
    /**
     * <p>seimeiKnText をセットします。
     * @param seimeiKnText seimeiKnText
     * @see UserQueryModel#seimeiKnText__
     */
    public void setSeimeiKnText(String seimeiKnText) {
        seimeiKnText__ = seimeiKnText;
    }
    /**
     * <p>telText を取得します。
     * @return telText
     * @see UserQueryModel#telText__
     */
    public String getTelText() {
        return telText__;
    }
    /**
     * <p>telText をセットします。
     * @param telText telText
     * @see UserQueryModel#telText__
     */
    public void setTelText(String telText) {
        telText__ = telText;
    }
    /**
     * <p>mailText を取得します。
     * @return mailText
     * @see UserQueryModel#mailText__
     */
    public String getMailText() {
        return mailText__;
    }
    /**
     * <p>mailText をセットします。
     * @param mailText mailText
     * @see UserQueryModel#mailText__
     */
    public void setMailText(String mailText) {
        mailText__ = mailText;
    }
    /**
     * <p>seibetuNum を取得します。
     * @return seibetuNum
     * @see UserQueryModel#seibetuType__
     */
    public EnumSeibetu getSeibetuType() {
        return seibetuType__;
    }
    /**
     * <p>seibetuNum をセットします。
     * @param seibetuNum seibetuNum
     * @see UserQueryModel#seibetuType__
     */
    public void setSeibetuType(EnumSeibetu seibetuNum) {
        seibetuType__ = seibetuNum;
    }
    /**
     * <p>ageFromNum を取得します。
     * @return ageFromNum
     * @see UserQueryModel#ageFromNum__
     */
    public Integer getAgeFromNum() {
        return ageFromNum__;
    }
    /**
     * <p>ageFromNum をセットします。
     * @param ageFromNum ageFromNum
     * @see UserQueryModel#ageFromNum__
     */
    public void setAgeFromNum(Integer ageFromNum) {
        ageFromNum__ = ageFromNum;
    }
    /**
     * <p>ageToNum を取得します。
     * @return ageToNum
     * @see UserQueryModel#ageToNum__
     */
    public Integer getAgeToNum() {
        return ageToNum__;
    }
    /**
     * <p>ageToNum をセットします。
     * @param ageToNum ageToNum
     * @see UserQueryModel#ageToNum__
     */
    public void setAgeToNum(Integer ageToNum) {
        ageToNum__ = ageToNum;
    }
    /**
     * <p>entranceFromDate を取得します。
     * @return entranceFromDate
     * @see UserQueryModel#entranceFromDate__
     */
    public UDate getEntranceFromDate() {
        return entranceFromDate__;
    }
    /**
     * <p>entranceFromDate をセットします。
     * @param entranceFromDate entranceFromDate
     * @see UserQueryModel#entranceFromDate__
     */
    public void setEntranceFromDate(UDate entranceFromDate) {
        entranceFromDate__ = entranceFromDate;
    }
    /**
     * <p>entranceToDate を取得します。
     * @return entranceToDate
     * @see UserQueryModel#entranceToDate__
     */
    public UDate getEntranceToDate() {
        return entranceToDate__;
    }
    /**
     * <p>entranceToDate をセットします。
     * @param entranceToDate entranceToDate
     * @see UserQueryModel#entranceToDate__
     */
    public void setEntranceToDate(UDate entranceToDate) {
        entranceToDate__ = entranceToDate;
    }
    /**
     * <p>postsSid を取得します。
     * @return postsSid
     * @see UserQueryModel#postsSid__
     */
    public Integer getPostsSid() {
        return postsSid__;
    }
    /**
     * <p>postsSid をセットします。
     * @param postsSid postsSid
     * @see UserQueryModel#postsSid__
     */
    public void setPostsSid(Integer postsSid) {
        postsSid__ = postsSid;
    }
    /**
     * <p>mailAddressText を取得します。
     * @return mailAddressText
     * @see UserQueryModel#mailAddressText__
     */
    public String getMailAddressText() {
        return mailAddressText__;
    }
    /**
     * <p>mailAddressText をセットします。
     * @param mailAddressText mailAddressText
     * @see UserQueryModel#mailAddressText__
     */
    public void setMailAddressText(String mailAddressText) {
        mailAddressText__ = mailAddressText;
    }
    /**
     * <p>todofukenSid を取得します。
     * @return todofukenSid
     * @see UserQueryModel#todofukenSid__
     */
    public Integer getTodofukenSid() {
        return todofukenSid__;
    }
    /**
     * <p>todofukenSid をセットします。
     * @param todofukenSid todofukenSid
     * @see UserQueryModel#todofukenSid__
     */
    public void setTodofukenSid(Integer todofukenSid) {
        todofukenSid__ = todofukenSid;
    }
    /**
     * <p>labelSidArray を取得します。
     * @return labelSidArray
     * @see UserQueryModel#labelSidArray__
     */
    public int[] getLabelSidArray() {
        return labelSidArray__;
    }
    /**
     * <p>labelSidArray をセットします。
     * @param labelSidArray labelSidArray
     * @see UserQueryModel#labelSidArray__
     */
    public void setLabelSidArray(int[] labelSidArray) {
        labelSidArray__ = labelSidArray;
    }
    /**
     * <p>limit を取得します。
     * @return limit
     * @see UserQueryModel#limit__
     */
    public Integer getLimit() {
        return limit__;
    }
    /**
     * <p>limit をセットします。
     * @param limit limit
     * @see UserQueryModel#limit__
     */
    public void setLimit(Integer limit) {
        limit__ = limit;
    }
    /**
     * <p>offset を取得します。
     * @return offset
     * @see UserQueryModel#offset__
     */
    public Integer getOffset() {
        return offset__;
    }
    /**
     * <p>offset をセットします。
     * @param offset offset
     * @see UserQueryModel#offset__
     */
    public void setOffset(Integer offset) {
        offset__ = offset;
    }
    /**
     * <p>sortKeyType を取得します。
     * @return sortKeyType
     * @see UserQueryModel#sortKeyType__
     */
    public int getSortKeyType() {
        return sortKeyType__;
    }
    /**
     * <p>sortKeyType をセットします。
     * @param sortKeyType sortKeyType
     * @see UserQueryModel#sortKeyType__
     */
    public void setSortKeyType(int sortKeyType) {
        sortKeyType__ = sortKeyType;
    }
    /**
     * <p>sortOrderFlg を取得します。
     * @return sortOrderFlg
     * @see UserQueryModel#sortOrderFlg__
     */
    public int getSortOrderFlg() {
        return sortOrderFlg__;
    }
    /**
     * <p>sortOrderFlg をセットします。
     * @param sortOrderFlg sortOrderFlg
     * @see UserQueryModel#sortOrderFlg__
     */
    public void setSortOrderFlg(int sortOrderFlg) {
        sortOrderFlg__ = sortOrderFlg;
    }
    /**
     * <p>sortKey2Type を取得します。
     * @return sortKey2Type
     * @see UserQueryModel#sortKey2Type__
     */
    public int getSortKey2Type() {
        return sortKey2Type__;
    }
    /**
     * <p>sortKey2Type をセットします。
     * @param sortKey2Type sortKey2Type
     * @see UserQueryModel#sortKey2Type__
     */
    public void setSortKey2Type(int sortKey2Type) {
        sortKey2Type__ = sortKey2Type;
    }
    /**
     * <p>sortOrder2Flg を取得します。
     * @return sortOrder2Flg
     * @see UserQueryModel#sortOrder2Flg__
     */
    public int getSortOrder2Flg() {
        return sortOrder2Flg__;
    }
    /**
     * <p>sortOrder2Flg をセットします。
     * @param sortOrder2Flg sortOrder2Flg
     * @see UserQueryModel#sortOrder2Flg__
     */
    public void setSortOrder2Flg(int sortOrder2Flg) {
        sortOrder2Flg__ = sortOrder2Flg;
    }
    public String getKeywordText() {
        return keywordText__;
    }
    public void setKeywordText(String keywordText) {
        keywordText__ = keywordText;
    }
    public String getKanaStartOffsetText() {
        return kanaStartOffsetText__;
    }
    public void setKanaStartOffsetText(String kanaStartOffsetText) {
        kanaStartOffsetText__ = kanaStartOffsetText;
    }

}
