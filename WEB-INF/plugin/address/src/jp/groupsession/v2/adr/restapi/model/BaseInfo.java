package jp.groupsession.v2.adr.restapi.model;

/**
 * <br>[機  能] アドレス帳 会社拠点情報 保持モデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class BaseInfo {

    /** 会社拠点SID */
    private Integer sid__ = 0;
    /** 会社拠点種別 */
    private Integer type__ = 0;
    /** 会社拠点名 */
    private String name__ = null;
    /** 会社拠点郵便番号1 */
    private String zip1Text__ = null;
    /** 会社拠点郵便番号2 */
    private String zip2Text__ = null;
    /** 会社拠点都道府県SID */
    private Integer todofukenSid__ = -1;
    /** 会社拠点都道府県名 */
    private String todofukenName__ = null;
    /** 会社拠点住所1 */
    private String address1Text__ = null;
    /** 会社拠点住所2 */
    private String address2Text__ = null;
    /** 会社拠点備考 */
    private String memoText__ = null;
    
    public Integer getSid() {
        return sid__;
    }
    public void setSid(Integer sid) {
        sid__ = sid;
    }
    public Integer getType() {
        return type__;
    }
    public void setType(Integer type) {
        type__ = type;
    }
    public String getName() {
        return name__;
    }
    public void setName(String name) {
        name__ = name;
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
    public String getMemoText() {
        return memoText__;
    }
    public void setMemoText(String memoText) {
        memoText__ = memoText;
    }
}
