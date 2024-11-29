package jp.groupsession.v2.adr.model;

/**
 * <br>[機  能] アドレス帳 会社情報一覧取得API 検索用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrCompanySearchModel {

    /** 取得件数 */
    private Integer limit__ = 50;
    /** 取得開始位置 */
    private Integer offset__ = 0;
    /** ソート昇順降順 */
    private Integer sortOrderFlg = 0;
    /** カナ順取得開始位置 */
    private String kanaStartOffsetText__ = null;
    /** 検索キーワード */
    private String keywordText__ = null;
    /** 会社名先頭カナ1文字 */
    private String companyNameKanaStartText__ = null;
    /** 業種SID */
    private Integer industrySid__ = -1;
    /** 都道府県SID */
    private Integer todofukenSid__ = -1;
    
    public Integer getSortOrderFlg() {
        return sortOrderFlg;
    }
    public void setSortOrderFlg(Integer sortOrderFlg) {
        this.sortOrderFlg = sortOrderFlg;
    }
    public String getKanaStartOffsetText() {
        return kanaStartOffsetText__;
    }
    public void setKanaStartOffsetText(String kanaStartOffsetText) {
        kanaStartOffsetText__ = kanaStartOffsetText;
    }
    public Integer getLimit() {
        return limit__;
    }
    public void setLimit(Integer limit) {
        limit__ = limit;
    }
    public Integer getOffset() {
        return offset__;
    }
    public void setOffset(Integer offset) {
        offset__ = offset;
    }
    public String getKeywordText() {
        return keywordText__;
    }
    public void setKeywordText(String keywordText) {
        keywordText__ = keywordText;
    }
    public String getCompanyNameKanaStartText() {
        return companyNameKanaStartText__;
    }
    public void setCompanyNameKanaStartText(String companyNameKanaStartText) {
        companyNameKanaStartText__ = companyNameKanaStartText;
    }
    public Integer getIndustrySid() {
        return industrySid__;
    }
    public void setIndustrySid(Integer industrySid) {
        industrySid__ = industrySid;
    }
    public Integer getTodofukenSid() {
        return todofukenSid__;
    }
    public void setTodofukenSid(Integer todofukenSid) {
        todofukenSid__ = todofukenSid;
    }
}
