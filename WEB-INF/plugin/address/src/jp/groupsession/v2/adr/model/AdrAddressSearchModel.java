package jp.groupsession.v2.adr.model;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アドレス情報検索時の検索条件を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AdrAddressSearchModel extends AbstractModel {

    /** 会社検索タイプ 検索対象の制限なし */
    public static final int COMPANYSEACHTYPE_NO_LMIT = 0;
    /** 会社検索タイプ 会社未登録のアドレス情報のみ検索 */
    public static final int COMPANYSEACHTYPE_MITOROKU_ONLY = 1;

    /** 並び順 */
    private int orderKey__ = GSConst.ORDER_KEY_ASC;

    /** カナ順取得開始位置 */
    private String kanaStartOffset__ = null;

    /** 検索キーワード */
    private String keyword__ = null;
    /** ラベルSID */
    private int[] label__ = null;

    /** 氏名カナ 先頭1文字 */
    private String unameKnHead__ = null;
    /** 役職SID */
    private int position__ = -1;

    /** ユーザID */
    private String userId__ = null;
    /** グループID */
    private String groupId__ = null;

    /** 会社検索タイプ */
    private int companySearchType__ = AdrAddressSearchModel.COMPANYSEACHTYPE_NO_LMIT;

    /** 企業コード */
    private String coCode__ = null;
    /** 会社拠点SID */
    private Integer companyBaseSid__ = null;
    /** 会社名カナ 先頭1文字 */
    private String cnameKnHead__ = null;

    /** 業種SID */
    private int atiSid__ = -1;
    /** 都道府県SID */
    private int tdfk__ = -1;

    /** 検索開始位置 */
    private int offset__ = 0;
    /** 取得最大件数 */
    private int limit__ = 0;

    /**
     * @return the orderKey
     */
    public int getOrderKey() {
        return orderKey__;
    }
    /**
     * @param orderKey the orderKey to set
     */
    public void setOrderKey(int orderKey) {
        orderKey__ = orderKey;
    }
    /**
     * @return the kanaStartOffset
     */
    public String getKanaStartOffset() {
        return kanaStartOffset__;
    }
    /**
     * @param kanaStartOffset the kanaStartOffset to set
     */
    public void setKanaStartOffset(String kanaStartOffset) {
        kanaStartOffset__ = kanaStartOffset;
    }
    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword__;
    }
    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        keyword__ = keyword;
    }
    /**
     * @return the label
     */
    public int[] getLabel() {
        return label__;
    }
    /**
     * @param label the label to set
     */
    public void setLabel(int[] label) {
        label__ = label;
    }
    /**
     * @return the unameKnHead
     */
    public String getUnameKnHead() {
        return unameKnHead__;
    }
    /**
     * @param unameKnHead the unameKnHead to set
     */
    public void setUnameKnHead(String unameKnHead) {
        unameKnHead__ = unameKnHead;
    }
    /**
     * @return the position
     */
    public int getPosition() {
        return position__;
    }
    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        position__ = position;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId__;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }
    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId__;
    }
    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(String groupId) {
        groupId__ = groupId;
    }
    /**
     * @return the companySearchType
     */
    public int getCompanySearchType() {
        return companySearchType__;
    }
    /**
     * @param companySearchType the companySearchType to set
     */
    public void setCompanySearchType(int companySearchType) {
        companySearchType__ = companySearchType;
    }
    /**
     * @return the coCode
     */
    public String getCoCode() {
        return coCode__;
    }
    /**
     * @param coCode the coCode to set
     */
    public void setCoCode(String coCode) {
        coCode__ = coCode;
    }
    /**
     * @return the companyBaseSid
     */
    public Integer getCompanyBaseSid() {
        return companyBaseSid__;
    }
    /**
     * @param companyBaseSid the companyBaseSid to set
     */
    public void setCompanyBaseSid(Integer companyBaseSid) {
        companyBaseSid__ = companyBaseSid;
    }
    /**
     * @return the cnameKnHead
     */
    public String getCnameKnHead() {
        return cnameKnHead__;
    }
    /**
     * @param cnameKnHead the cnameKnHead to set
     */
    public void setCnameKnHead(String cnameKnHead) {
        cnameKnHead__ = cnameKnHead;
    }
    /**
     * @return the atiSid
     */
    public int getAtiSid() {
        return atiSid__;
    }
    /**
     * @param atiSid the atiSid to set
     */
    public void setAtiSid(int atiSid) {
        atiSid__ = atiSid;
    }
    /**
     * @return the tdfk
     */
    public int getTdfk() {
        return tdfk__;
    }
    /**
     * @param tdfk the tdfk to set
     */
    public void setTdfk(int tdfk) {
        tdfk__ = tdfk;
    }
    /**
     * @return the offset
     */
    public int getOffset() {
        return offset__;
    }
    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        offset__ = offset;
    }
    /**
     * @return the limit
     */
    public int getLimit() {
        return limit__;
    }
    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        limit__ = limit;
    }
}