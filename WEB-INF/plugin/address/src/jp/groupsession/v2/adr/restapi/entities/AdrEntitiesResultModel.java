package jp.groupsession.v2.adr.restapi.entities;

import java.util.List;

import jp.groupsession.v2.restapi.response.annotation.ChildElementName;

/**
 * <br>[機  能] アドレス帳 アドレス情報取得API 実行結果モデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrEntitiesResultModel {

    /** アドレス情報SID */
    private Integer sid__ = 0;
    /** 氏名 姓 */
    private String seiText__ = null;
    /** 氏名 名 */
    private String meiText__ = null;
    /** 氏名 姓(カナ) */
    private String seiKanaText__ = null;
    /** 氏名 名(カナ) */
    private String meiKanaText__ = null;
    /** 所属 */
    private String syozokuText__ = null;
    /** 役職SID */
    private Integer yakusyokuSid__ = 0;
    /** 役職 */
    private String yakusyokuName__ = null;
    /** メールアドレス1 */
    private String mail1Text__ = null;
    /** メールアドレス1 コメント */
    private String mail1CommentText__= null;
    /** メールアドレス2 */
    private String mail2Text__ = null;
    /** メールアドレス2 コメント */
    private String mail2CommentText__= null;
    /** メールアドレス3 */
    private String mail3Text__ = null;
    /** メールアドレス3 コメント */
    private String mail3CommentText__= null;
    /** 電話番号1 */
    private String tel1Text__ = null;
    /** 電話番号1 内線 */
    private String tel1NaisenText__ = null;
    /** 電話番号1 コメント */
    private String tel1CommentText__ = null;
    /** 電話番号2 */
    private String tel2Text__ = null;
    /** 電話番号2 内線 */
    private String tel2NaisenText__ = null;
    /** 電話番号2 コメント */
    private String tel2CommentText__ = null;
    /** 電話番号3 */
    private String tel3Text__ = null;
    /** 電話番号3 内線 */
    private String tel3NaisenText__ = null;
    /** 電話番号3 コメント */
    private String tel3CommentText__ = null;
    /** FAX1 */
    private String fax1Text__ = null;
    /** FAX1 コメント */
    private String fax1CommentText__ = null;
    /** FAX2 */
    private String fax2Text__ = null;
    /** FAX2 コメント */
    private String fax2CommentText__ = null;
    /** FAX3 */
    private String fax3Text__ = null;
    /** FAX3 コメント */
    private String fax3CommentText__ = null;
    /** 郵便番号1 */
    private String zip1Text__= null;
    /** 郵便番号2 */
    private String zip2Text__= null;
    /** 都道府県SID */
    private Integer todofukenSid__ = 0;
    /** 都道府県 */
    private String todofukenName__ = null;
    /** 住所1 */
    private String address1Text__ = null;
    /** 住所2 */
    private String address2Text__ = null;
    /** 備考 */
    private String memoText__ = null;
    /** 企業コード */
    private String companyId__ = null;
    /** 会社拠点SID */
    private Integer baseSid__ = 0;
    /** 企業名 */
    private String companyName__ = null;
    /** 会社拠点名 */
    private String baseName__ = null;
    /** 会社拠点種別名 */
    private Integer baseType = 0;
    /** ラベル情報配列 */
    @ChildElementName("labelInfo")
    private List<LabelInfo> labelArray__;

    public static class LabelInfo {
        
        /** ラベルSID */
        private Integer sid__ = 0;
        /** ラベル名 */
        private String name__ = null;
        /** カテゴリSID */
        private Integer categorySid__ = 0;
        /** カテゴリ名 */
        private String categoryName__ = null;
        
        public Integer getSid() {
            return sid__;
        }
        public void setSid(Integer sid) {
            sid__ = sid;
        }
        public String getName() {
            return name__;
        }
        public void setName(String name) {
            name__ = name;
        }
        public Integer getCategorySid() {
            return categorySid__;
        }
        public void setCategorySid(Integer categorySid) {
            categorySid__ = categorySid;
        }
        public String getCategoryName() {
            return categoryName__;
        }
        public void setCategoryName(String categoryName) {
            categoryName__ = categoryName;
        }
    }

    /** 担当者情報配列 */
    @ChildElementName("tantoUserInfo")
    private List<TantoUserInfo> tantoUserArray__;

    public static class TantoUserInfo {

        /** 担当者ユーザID */
        private String id__ = null;
        /** 担当者 姓 */
        private String seiText__ = null;
        /** 担当者 名 */
        private String meiText__ = null;
        /** 担当者 姓(カナ) */
        private String seiKanaText__ = null;
        /** 担当者 名(カナ) */
        private String meiKanaText__ = null;
        /** 担当者 ログイン停止フラグ */
        private Integer loginStopFlg__ = 0;
        
        public String getId() {
            return id__;
        }
        public void setId(String id) {
            id__ = id;
        }
        public String getSeiText() {
            return seiText__;
        }
        public void setSeiText(String seiText) {
            seiText__ = seiText;
        }
        public String getMeiText() {
            return meiText__;
        }
        public void setMeiText(String meiText) {
            meiText__ = meiText;
        }
        public String getSeiKanaText() {
            return seiKanaText__;
        }
        public void setSeiKanaText(String seiKanaText) {
            seiKanaText__ = seiKanaText;
        }
        public Integer getLoginStopFlg() {
            return loginStopFlg__;
        }
        public void setLoginStopFlg(Integer loginStopFlg) {
            loginStopFlg__ = loginStopFlg;
        }
        public String getMeiKanaText() {
            return meiKanaText__;
        }
        public void setMeiKanaText(String meiKanaText) {
            meiKanaText__ = meiKanaText;
        }
    }

    public Integer getSid() {
        return sid__;
    }

    public void setSid(Integer sid) {
        sid__ = sid;
    }

    public String getSeiText() {
        return seiText__;
    }

    public void setSeiText(String seiText) {
        seiText__ = seiText;
    }

    public String getMeiText() {
        return meiText__;
    }

    public void setMeiText(String meiText) {
        meiText__ = meiText;
    }

    public String getSeiKanaText() {
        return seiKanaText__;
    }

    public void setSeiKanaText(String seiKanaText) {
        seiKanaText__ = seiKanaText;
    }

    public String getMeiKanaText() {
        return meiKanaText__;
    }

    public void setMeiKanaText(String meiKanaText) {
        meiKanaText__ = meiKanaText;
    }

    public String getSyozokuText() {
        return syozokuText__;
    }

    public void setSyozokuText(String syozokuText) {
        syozokuText__ = syozokuText;
    }

    public Integer getYakusyokuSid() {
        return yakusyokuSid__;
    }

    public void setYakusyokuSid(Integer yakusyokuSid) {
        yakusyokuSid__ = yakusyokuSid;
    }

    public String getYakusyokuName() {
        return yakusyokuName__;
    }

    public void setYakusyokuName(String yakusyokuName) {
        yakusyokuName__ = yakusyokuName;
    }

    public String getMail1Text() {
        return mail1Text__;
    }

    public void setMail1Text(String mail1Text) {
        mail1Text__ = mail1Text;
    }

    public String getMail1CommentText() {
        return mail1CommentText__;
    }

    public void setMail1CommentText(String mail1CommentText) {
        mail1CommentText__ = mail1CommentText;
    }

    public String getMail2Text() {
        return mail2Text__;
    }

    public void setMail2Text(String mail2Text) {
        mail2Text__ = mail2Text;
    }

    public String getMail2CommentText() {
        return mail2CommentText__;
    }

    public void setMail2CommentText(String mail2CommentText) {
        mail2CommentText__ = mail2CommentText;
    }

    public String getMail3Text() {
        return mail3Text__;
    }

    public void setMail3Text(String mail3Text) {
        mail3Text__ = mail3Text;
    }

    public String getMail3CommentText() {
        return mail3CommentText__;
    }

    public void setMail3CommentText(String mail3CommentText) {
        mail3CommentText__ = mail3CommentText;
    }

    public String getTel1Text() {
        return tel1Text__;
    }

    public void setTel1Text(String tel1Text) {
        tel1Text__ = tel1Text;
    }

    public String getTel1NaisenText() {
        return tel1NaisenText__;
    }

    public void setTel1NaisenText(String tel1NaisenText) {
        tel1NaisenText__ = tel1NaisenText;
    }

    public String getTel1CommentText() {
        return tel1CommentText__;
    }

    public void setTel1CommentText(String tel1CommentText) {
        tel1CommentText__ = tel1CommentText;
    }

    public String getTel2Text() {
        return tel2Text__;
    }

    public void setTel2Text(String tel2Text) {
        tel2Text__ = tel2Text;
    }

    public String getTel2NaisenText() {
        return tel2NaisenText__;
    }

    public void setTel2NaisenText(String tel2NaisenText) {
        tel2NaisenText__ = tel2NaisenText;
    }

    public String getTel2CommentText() {
        return tel2CommentText__;
    }

    public void setTel2CommentText(String tel2CommentText) {
        tel2CommentText__ = tel2CommentText;
    }

    public String getTel3Text() {
        return tel3Text__;
    }

    public void setTel3Text(String tel3Text) {
        tel3Text__ = tel3Text;
    }

    public String getTel3NaisenText() {
        return tel3NaisenText__;
    }

    public void setTel3NaisenText(String tel3NaisenText) {
        tel3NaisenText__ = tel3NaisenText;
    }

    public String getTel3CommentText() {
        return tel3CommentText__;
    }

    public void setTel3CommentText(String tel3CommentText) {
        tel3CommentText__ = tel3CommentText;
    }

    public String getFax1Text() {
        return fax1Text__;
    }

    public void setFax1Text(String fax1Text) {
        fax1Text__ = fax1Text;
    }

    public String getFax1CommentText() {
        return fax1CommentText__;
    }

    public void setFax1CommentText(String fax1CommentText) {
        fax1CommentText__ = fax1CommentText;
    }

    public String getFax2Text() {
        return fax2Text__;
    }

    public void setFax2Text(String fax2Text) {
        fax2Text__ = fax2Text;
    }

    public String getFax2CommentText() {
        return fax2CommentText__;
    }

    public void setFax2CommentText(String fax2CommentText) {
        fax2CommentText__ = fax2CommentText;
    }

    public String getFax3Text() {
        return fax3Text__;
    }

    public void setFax3Text(String fax3Text) {
        fax3Text__ = fax3Text;
    }

    public String getFax3CommentText() {
        return fax3CommentText__;
    }

    public void setFax3CommentText(String fax3CommentText) {
        fax3CommentText__ = fax3CommentText;
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

    public String getCompanyId() {
        return companyId__;
    }

    public void setCompanyId(String companyId) {
        companyId__ = companyId;
    }

    public Integer getBaseSid() {
        return baseSid__;
    }

    public void setBaseSid(Integer baseSid) {
        baseSid__ = baseSid;
    }

    public String getCompanyName() {
        return companyName__;
    }

    public void setCompanyName(String companyName) {
        companyName__ = companyName;
    }

    public String getBaseName() {
        return baseName__;
    }

    public void setBaseName(String baseName) {
        baseName__ = baseName;
    }

    public Integer getBaseType() {
        return baseType;
    }

    public void setBaseType(Integer baseType) {
        this.baseType = baseType;
    }

    public List<LabelInfo> getLabelArray() {
        return labelArray__;
    }

    public void setLabelArray(List<LabelInfo> labelArray) {
        labelArray__ = labelArray;
    }

    public List<TantoUserInfo> getTantoUserArray() {
        return tantoUserArray__;
    }

    public void setTantoUserArray(List<TantoUserInfo> tantoUserArray) {
        tantoUserArray__ = tantoUserArray;
    }
}
