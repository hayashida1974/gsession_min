package jp.groupsession.v2.adr.restapi.companies.query;

import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.MaxValue;
import jp.groupsession.v2.restapi.parameter.annotation.MinValue;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;

/**
 * <br>[機  能] アドレス帳 会社情報一覧取得API用モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ParamModel
public class AdrCompaniesQueryPostParamModel {

    /** 取得件数 */
    @MaxValue(100)
    @MinValue(1)
    private Integer limit__ = 50;

    /** 取得開始位置 */
    private Integer offset__ = 0;

    /** ソート昇順降順 */
    @Selectable({"0","1"})
    private Integer sortOrderFlg = 0;

    /** カナ順取得開始位置 */
    @TextField
    @MaxLength(1)
    private String kanaStartOffsetText__ = null;

    /** 検索キーワード */
    @TextField
    @MaxLength(50)
    private String keywordText__ = null;

    /** 会社名先頭カナ1文字 */
    @TextField
    @MaxLength(1)
    private String companyNameKanaStartText__ = null;

    /** 業種SID */
    private Integer industrySid__ = -1;

    /** 都道府県SID */
    private Integer todofukenSid__ = -1;
    
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

    /**
     * <br>[機  能] 入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RestApiコンテキスト
     */
    @Validator
    public void validate() {

        if (getCompanyNameKanaStartText() != null
                && !GSValidateUtil.isGsWideKana(getCompanyNameKanaStartText())) {
            throw new RestApiValidateException(
                    EnumError.PARAM_LETER,
                    "error.input.kana.text", "companyNameKanaStartText")
                .setParamName("companyNameKanaStartText");
        }

        if (getKanaStartOffsetText() != null
                && !GSValidateUtil.isGsWideKana(getKanaStartOffsetText())) {
            throw new RestApiValidateException(
                    EnumError.PARAM_LETER,
                    "error.input.kana.text", "kanaStartOffsetText")
                .setParamName("kanaStartOffsetText");
        }
    }
}
