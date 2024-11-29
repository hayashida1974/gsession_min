package jp.groupsession.v2.adr.restapi.companies;

import java.util.List;

import jp.groupsession.v2.adr.restapi.model.BaseInfo;
import jp.groupsession.v2.adr.restapi.model.IndustryInfo;
import jp.groupsession.v2.restapi.response.annotation.ChildElementName;

/**
 * <br>[機  能] アドレス帳 会社情報取得API 実行結果モデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrCompaniesResultModel {

    /** 企業コード */
    private String id__ = null;
    /** 会社名 */
    private String name__ = null;
    /** 会社名(カナ) */
    private String kanaName__ = null;
    /** 業種 */
    @ChildElementName("industryInfo")
    private List<IndustryInfo> industryArray__;
    /** 郵便番号1 */
    private String zip1Text__ = null;
    /** 郵便番号2 */
    private String zip2Text__ = null;
    /** 都道府県SID */
    private Integer todofukenSid__ = -1;
    /** 都道府県名 */
    private String todofukenName__ = null;
    /** 住所1 */
    private String address1Text__ = null;
    /** 住所2 */
    private String address2Text__ = null;
    /** URL */
    private String urlText__ = null;
    /** 備考 */
    private String memoText__ = null;
    /** 会社拠点情報 */
    @ChildElementName("baseInfo")
    private List<BaseInfo> baseArray__;

    public String getId() {
        return id__;
    }

    public void setId(String id) {
        id__ = id;
    }

    public String getName() {
        return name__;
    }

    public void setName(String name) {
        name__ = name;
    }

    public List<IndustryInfo> getIndustryArray() {
        return industryArray__;
    }

    public void setIndustryArray(List<IndustryInfo> industryArray) {
        industryArray__ = industryArray;
    }

    public String getZip1Text() {
        return zip1Text__;
    }

    public void setZip1Text(String zip1Text) {
        zip1Text__ = zip1Text;
    }

    public String getZip2Text() {
        return zip2Text__;
    }

    public void setZip2Text(String zip2Text) {
        zip2Text__ = zip2Text;
    }

    public Integer getTodofukenSid() {
        return todofukenSid__;
    }

    public void setTodofukenSid(Integer todofukenSid) {
        todofukenSid__ = todofukenSid;
    }

    public String getTodofukenName() {
        return todofukenName__;
    }

    public void setTodofukenName(String todofukenName) {
        todofukenName__ = todofukenName;
    }

    public String getAddress1Text() {
        return address1Text__;
    }

    public void setAddress1Text(String address1Text) {
        address1Text__ = address1Text;
    }

    public String getAddress2Text() {
        return address2Text__;
    }

    public void setAddress2Text(String address2Text) {
        address2Text__ = address2Text;
    }

    public String getUrlText() {
        return urlText__;
    }

    public void setUrlText(String urlText) {
        urlText__ = urlText;
    }

    public String getMemoText() {
        return memoText__;
    }

    public void setMemoText(String memoText) {
        memoText__ = memoText;
    }

    public List<BaseInfo> getBaseArray() {
        return baseArray__;
    }

    public void setBaseArray(List<BaseInfo> baseArray) {
        baseArray__ = baseArray;
    }

    public String getKanaName() {
        return kanaName__;
    }

    public void setKanaName(String kanaName) {
        kanaName__ = kanaName;
    }
}