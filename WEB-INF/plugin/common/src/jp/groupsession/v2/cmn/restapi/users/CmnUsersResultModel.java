package jp.groupsession.v2.cmn.restapi.users;

import java.util.List;

import jp.groupsession.v2.restapi.response.annotation.ChildElementName;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;

/**
 *
 * <br>[機  能] レスポンス用モデルクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ResponceModel(targetField = {
            "userSid",
            "userId",
            "seiText",
            "meiText",
            "seiKanaText",
            "meiKanaText",
            "loginStopFlg",
            "syainNoText",
            "syozokuText",
            "yakusyokuSid",
            "yakusyokuName",
            "seibetuType",
            "entranceDate",
            "birthdayDate",
            "birthdayKoukaiFlg",
            "mail1Text",
            "mail1CommentText",
            "mail1KoukaiFlg",
            "mail2Text",
            "mail2CommentText",
            "mail2KoukaiFlg",
            "mail3Text",
            "mail3CommentText",
            "mail3KoukaiFlg",
            "zip1Text",
            "zip2Text",
            "zipKoukaiFlg",
            "todofukenSid",
            "todofukenName",
            "todofukenKoukaiFlg",
            "address1Text",
            "address1KoukaiFlg",
            "address2Text",
            "address2KoukaiFlg",
            "tel1Text",
            "tel1NaisenText",
            "tel1CommentText",
            "tel1KoukaiFlg",
            "tel2Text",
            "tel2NaisenText",
            "tel2CommentText",
            "tel2KoukaiFlg",
            "tel3Text",
            "tel3NaisenText",
            "tel3CommentText",
            "tel3KoukaiFlg",
            "fax1Text",
            "fax1CommentText",
            "fax1KoukaiFlg",
            "fax2Text",
            "fax2CommentText",
            "fax2KoukaiFlg",
            "fax3Text",
            "fax3CommentText",
            "fax3KoukaiFlg",
            "bikouText",
            "addDateTime",
            "editDateTime",
            "groupArray",
            "labelArray",
            "imageBinSid",
            "imageKoukaiFlg"
    })
public class CmnUsersResultModel {
    //所属グループ情報格納Model
    public static class GroupInfo {
        /** グループID */
        private String id__ = null;
        /** グループ名 */
        private String name__ = null;
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
    }

    //ラベル情報格納Model
    public static class LabelInfo {
        /** ラベルSID */
        private int sid__ = 0;
        /** ラベル名 */
        private String name__ = null;
        /** ラベルカテゴリSID */
        private int categorySid__ = 0;
        /** ラベルカテゴリ名 */
        private String categoryName__ = null;
        public int getSid() {
            return sid__;
        }
        public void setSid(int sid) {
            sid__ = sid;
        }
        public String getName() {
            return name__;
        }
        public void setName(String name) {
            name__ = name;
        }
        public int getCategorySid() {
            return categorySid__;
        }
        public void setCategorySid(int categorySid) {
            categorySid__ = categorySid;
        }
        public String getCategoryName() {
            return categoryName__;
        }
        public void setCategoryName(String categoryName) {
            categoryName__ = categoryName;
        }
    }

    /** ユーザSID                                       */
    private Integer userSid__;
    /** ログインID                                       */
    private String userId__;
    /** 姓                                        */
    private String seiText__;
    /** 名                                        */
    private String meiText__;
    /** 姓カナ                                      */
    private String seiKanaText__;
    /** 名カナ                                      */
    private String meiKanaText__;
    /** ユーザ無効フラグ                                         */
    private Integer loginStopFlg__;
    /** 社員/職員番号                                      */
    private String syainNoText__;
    /** 所属                                       */
    private String syozokuText__;
    /** 役職SID                                        */
    private Integer yakusyokuSid__;
    /** 役職名称                                         */
    private String yakusyokuName__;
    /** 性別                                         */
    private Integer seibetuType__;
    /** 入社年月日To                                         */
    private String entranceDate__;
    /** 生年月日(西暦)                                         */
    private String birthdayDate__;
    /** 生年月日(西暦)公開フラグ                                        */
    private Integer birthdayKoukaiFlg__;
    /** メールアドレス1                                         */
    private String mail1Text__;
    /** メールアドレスコメント1                                         */
    private String mail1CommentText__;
    /** メールアドレス1公開フラグ                                        */
    private Integer mail1KoukaiFlg__;
    /** メールアドレス2                                         */
    private String mail2Text__;
    /** メールアドレスコメント2                                         */
    private String mail2CommentText__;
    /** メールアドレス2公開フラグ                                        */
    private Integer mail2KoukaiFlg__;
    /** メールアドレス3                                         */
    private String mail3Text__;
    /** メールアドレスコメント3                                         */
    private String mail3CommentText__;
    /** メールアドレス3公開フラグ                                        */
    private Integer mail3KoukaiFlg__;
    /** 郵便番号1                                        */
    private String zip1Text__;
    /** 郵便番号2                                        */
    private String zip2Text__;
    /** 郵便番号公開フラグ                                        */
    private Integer zipKoukaiFlg__;
    /** 都道府県SID                                      */
    private Integer todofukenSid__;
    /** 都道府県名称                                       */
    private String todofukenName__;
    /** 都道府県公開フラグ                                        */
    private Integer todofukenKoukaiFlg__;
    /** 住所1                                      */
    private String address1Text__;
    /** 住所1公開フラグ                                         */
    private Integer address1KoukaiFlg__;
    /** 住所2                                      */
    private String address2Text__;
    /** 住所2公開フラグ                                         */
    private Integer address2KoukaiFlg__;
    /** 電話番号1                                        */
    private String tel1Text__;
    /** 電話番号1内線                                      */
    private String tel1NaisenText__;
    /** 電話番号1コメント                                        */
    private String tel1CommentText__;
    /** 電話番号1公開フラグ                                       */
    private Integer tel1KoukaiFlg__;
    /** 電話番号2                                        */
    private String tel2Text__;
    /** 電話番号2内線                                      */
    private String tel2NaisenText__;
    /** 電話番号2コメント                                        */
    private String tel2CommentText__;
    /** 電話番号2公開フラグ                                       */
    private Integer tel2KoukaiFlg__;
    /** 電話番号3                                        */
    private String tel3Text__;
    /** 電話番号3内線                                      */
    private String tel3NaisenText__;
    /** 電話番号3コメント                                        */
    private String tel3CommentText__;
    /** 電話番号3公開フラグ                                       */
    private Integer tel3KoukaiFlg__;
    /** FAX番号1                                       */
    private String fax1Text__;
    /** FAX番号1コメント                                       */
    private String fax1CommentText__;
    /** FAX番号1公開フラグ                                      */
    private Integer fax1KoukaiFlg__;
    /** FAX番号2                                       */
    private String fax2Text__;
    /** FAX番号2コメント                                       */
    private String fax2CommentText__;
    /** FAX番号2公開フラグ                                      */
    private Integer fax2KoukaiFlg__;
    /** FAX番号3                                       */
    private String fax3Text__;
    /** FAX番号3コメント                                       */
    private String fax3CommentText__;
    /** FAX番号3公開フラグ                                      */
    private Integer fax3KoukaiFlg__;
    /** 備考                                       */
    private String bikouText__;
    /** 登録日                                      */
    private String addDateTime__;
    /** 変更日                                      */
    private String editDateTime__;
    /** 所属グループ配列 */
    @ChildElementName("groupInfo")
    private List<GroupInfo> groupArray__ = null;
    /** ラベル配列 */
    @ChildElementName("labelInfo")
    private List<LabelInfo> labelArray__ = null;
    /** 写真 バイナリSID */
    private Long imageBinSid__;
    /** 写真 公開フラグ */
    private Integer imageKoukaiFlg__;
    /**
     * <p>userSid を取得します。
     * @return userSid
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#userSid__
     */
    public Integer getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#userSid__
     */
    public void setUserSid(Integer userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>userId を取得します。
     * @return userId
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#userId__
     */
    public String getUserId() {
        return userId__;
    }
    /**
     * <p>userId をセットします。
     * @param userId userId
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#userId__
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }
    /**
     * <p>seiText を取得します。
     * @return seiText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#seiText__
     */
    public String getSeiText() {
        return seiText__;
    }
    /**
     * <p>seiText をセットします。
     * @param seiText seiText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#seiText__
     */
    public void setSeiText(String seiText) {
        seiText__ = seiText;
    }
    /**
     * <p>meiText を取得します。
     * @return meiText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#meiText__
     */
    public String getMeiText() {
        return meiText__;
    }
    /**
     * <p>meiText をセットします。
     * @param meiText meiText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#meiText__
     */
    public void setMeiText(String meiText) {
        meiText__ = meiText;
    }
    /**
     * <p>seiKanaText を取得します。
     * @return seiKanaText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#seiKanaText__
     */
    public String getSeiKanaText() {
        return seiKanaText__;
    }
    /**
     * <p>seiKanaText をセットします。
     * @param seiKanaText seiKanaText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#seiKanaText__
     */
    public void setSeiKanaText(String seiKanaText) {
        seiKanaText__ = seiKanaText;
    }
    /**
     * <p>meiKanaText を取得します。
     * @return meiKanaText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#meiKanaText__
     */
    public String getMeiKanaText() {
        return meiKanaText__;
    }
    /**
     * <p>meiKanaText をセットします。
     * @param meiKanaText meiKanaText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#meiKanaText__
     */
    public void setMeiKanaText(String meiKanaText) {
        meiKanaText__ = meiKanaText;
    }
    /**
     * <p>loginStopFlg を取得します。
     * @return loginStopFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#LoginStopFlg__
     */
    public Integer getLoginStopFlg() {
        return loginStopFlg__;
    }
    /**
     * <p>loginStopFlg をセットします。
     * @param loginStopFlg loginStopFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#LoginStopFlg__
     */
    public void setLoginStopFlg(Integer loginStopFlg) {
        loginStopFlg__ = loginStopFlg;
    }
    /**
     * <p>syainNoText を取得します。
     * @return syainNoText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#syainNoText__
     */
    public String getSyainNoText() {
        return syainNoText__;
    }
    /**
     * <p>syainNoText をセットします。
     * @param syainNoText syainNoText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#syainNoText__
     */
    public void setSyainNoText(String syainNoText) {
        syainNoText__ = syainNoText;
    }
    /**
     * <p>syozokuText を取得します。
     * @return syozokuText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#syozokuText__
     */
    public String getSyozokuText() {
        return syozokuText__;
    }
    /**
     * <p>syozokuText をセットします。
     * @param syozokuText syozokuText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#syozokuText__
     */
    public void setSyozokuText(String syozokuText) {
        syozokuText__ = syozokuText;
    }
    /**
     * <p>yakusyokuSid を取得します。
     * @return yakusyokuSid
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#yakusyokuSid__
     */
    public Integer getYakusyokuSid() {
        return yakusyokuSid__;
    }
    /**
     * <p>yakusyokuSid をセットします。
     * @param yakusyokuSid yakusyokuSid
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#yakusyokuSid__
     */
    public void setYakusyokuSid(Integer yakusyokuSid) {
        yakusyokuSid__ = yakusyokuSid;
    }
    /**
     * <p>yakusyokuName を取得します。
     * @return yakusyokuName
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#yakusyokuName__
     */
    public String getYakusyokuName() {
        return yakusyokuName__;
    }
    /**
     * <p>yakusyokuName をセットします。
     * @param yakusyokuName yakusyokuName
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#yakusyokuName__
     */
    public void setYakusyokuName(String yakusyokuName) {
        yakusyokuName__ = yakusyokuName;
    }
    /**
     * <p>seibetuType を取得します。
     * @return seibetuType
     * @see CmnUsersResultModel#seibetuType__
     */
    public Integer getSeibetuType() {
        return seibetuType__;
    }
    /**
     * <p>seibetuType をセットします。
     * @param seibetuType seibetuType
     * @see CmnUsersResultModel#seibetuType__
     */
    public void setSeibetuType(Integer seibetuType) {
        seibetuType__ = seibetuType;
    }
    /**
     * <p>entranceDate を取得します。
     * @return entranceDate
     * @see CmnUsersResultModel#entranceDate__
     */
    public String getEntranceDate() {
        return entranceDate__;
    }
    /**
     * <p>entranceDate をセットします。
     * @param entranceDate entranceDate
     * @see CmnUsersResultModel#entranceDate__
     */
    public void setEntranceDate(String entranceDate) {
        entranceDate__ = entranceDate;
    }
    /**
     * <p>birthdayDate を取得します。
     * @return birthdayDate
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#birthdayDate__
     */
    public String getBirthdayDate() {
        return birthdayDate__;
    }
    /**
     * <p>birthdayDate をセットします。
     * @param birthdayDate birthdayDate
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#birthdayDate__
     */
    public void setBirthdayDate(String birthdayDate) {
        birthdayDate__ = birthdayDate;
    }
    /**
     * <p>birthdayKoukaiFlg を取得します。
     * @return birthdayKoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#birthdayKoukaiFlg__
     */
    public Integer getBirthdayKoukaiFlg() {
        return birthdayKoukaiFlg__;
    }
    /**
     * <p>birthdayKoukaiFlg をセットします。
     * @param birthdayKoukaiFlg birthdayKoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#birthdayKoukaiFlg__
     */
    public void setBirthdayKoukaiFlg(Integer birthdayKoukaiFlg) {
        birthdayKoukaiFlg__ = birthdayKoukaiFlg;
    }
    /**
     * <p>mail1Text を取得します。
     * @return mail1Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail1Text__
     */
    public String getMail1Text() {
        return mail1Text__;
    }
    /**
     * <p>mail1Text をセットします。
     * @param mail1Text mail1Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail1Text__
     */
    public void setMail1Text(String mail1Text) {
        mail1Text__ = mail1Text;
    }
    /**
     * <p>mail1CommentText を取得します。
     * @return mail1CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail1CommentText__
     */
    public String getMail1CommentText() {
        return mail1CommentText__;
    }
    /**
     * <p>mail1CommentText をセットします。
     * @param mail1CommentText mail1CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail1CommentText__
     */
    public void setMail1CommentText(String mail1CommentText) {
        mail1CommentText__ = mail1CommentText;
    }
    /**
     * <p>mail1KoukaiFlg を取得します。
     * @return mail1KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail1KoukaiFlg__
     */
    public Integer getMail1KoukaiFlg() {
        return mail1KoukaiFlg__;
    }
    /**
     * <p>mail1KoukaiFlg をセットします。
     * @param mail1KoukaiFlg mail1KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail1KoukaiFlg__
     */
    public void setMail1KoukaiFlg(Integer mail1KoukaiFlg) {
        mail1KoukaiFlg__ = mail1KoukaiFlg;
    }
    /**
     * <p>mail2Text を取得します。
     * @return mail2Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail2Text__
     */
    public String getMail2Text() {
        return mail2Text__;
    }
    /**
     * <p>mail2Text をセットします。
     * @param mail2Text mail2Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail2Text__
     */
    public void setMail2Text(String mail2Text) {
        mail2Text__ = mail2Text;
    }
    /**
     * <p>mail2CommentText を取得します。
     * @return mail2CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail2CommentText__
     */
    public String getMail2CommentText() {
        return mail2CommentText__;
    }
    /**
     * <p>mail2CommentText をセットします。
     * @param mail2CommentText mail2CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail2CommentText__
     */
    public void setMail2CommentText(String mail2CommentText) {
        mail2CommentText__ = mail2CommentText;
    }
    /**
     * <p>mail2KoukaiFlg を取得します。
     * @return mail2KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail2KoukaiFlg__
     */
    public Integer getMail2KoukaiFlg() {
        return mail2KoukaiFlg__;
    }
    /**
     * <p>mail2KoukaiFlg をセットします。
     * @param mail2KoukaiFlg mail2KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail2KoukaiFlg__
     */
    public void setMail2KoukaiFlg(Integer mail2KoukaiFlg) {
        mail2KoukaiFlg__ = mail2KoukaiFlg;
    }
    /**
     * <p>mail3Text を取得します。
     * @return mail3Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail3Text__
     */
    public String getMail3Text() {
        return mail3Text__;
    }
    /**
     * <p>mail3Text をセットします。
     * @param mail3Text mail3Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail3Text__
     */
    public void setMail3Text(String mail3Text) {
        mail3Text__ = mail3Text;
    }
    /**
     * <p>mail3CommentText を取得します。
     * @return mail3CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail3CommentText__
     */
    public String getMail3CommentText() {
        return mail3CommentText__;
    }
    /**
     * <p>mail3CommentText をセットします。
     * @param mail3CommentText mail3CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail3CommentText__
     */
    public void setMail3CommentText(String mail3CommentText) {
        mail3CommentText__ = mail3CommentText;
    }
    /**
     * <p>mail3KoukaiFlg を取得します。
     * @return mail3KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail3KoukaiFlg__
     */
    public Integer getMail3KoukaiFlg() {
        return mail3KoukaiFlg__;
    }
    /**
     * <p>mail3KoukaiFlg をセットします。
     * @param mail3KoukaiFlg mail3KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#mail3KoukaiFlg__
     */
    public void setMail3KoukaiFlg(Integer mail3KoukaiFlg) {
        mail3KoukaiFlg__ = mail3KoukaiFlg;
    }
    /**
     * <p>zip1Text を取得します。
     * @return zip1Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#zip1Text__
     */
    public String getZip1Text() {
        return zip1Text__;
    }
    /**
     * <p>zip1Text をセットします。
     * @param zip1Text zip1Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#zip1Text__
     */
    public void setZip1Text(String zip1Text) {
        zip1Text__ = zip1Text;
    }
    /**
     * <p>zip2Text を取得します。
     * @return zip2Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#zip2Text__
     */
    public String getZip2Text() {
        return zip2Text__;
    }
    /**
     * <p>zip2Text をセットします。
     * @param zip2Text zip2Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#zip2Text__
     */
    public void setZip2Text(String zip2Text) {
        zip2Text__ = zip2Text;
    }
    /**
     * <p>zipKoukaiFlg を取得します。
     * @return zipKoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#zipKoukaiFlg__
     */
    public Integer getZipKoukaiFlg() {
        return zipKoukaiFlg__;
    }
    /**
     * <p>zipKoukaiFlg をセットします。
     * @param zipKoukaiFlg zipKoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#zipKoukaiFlg__
     */
    public void setZipKoukaiFlg(Integer zipKoukaiFlg) {
        zipKoukaiFlg__ = zipKoukaiFlg;
    }
    /**
     * <p>todofukenSid を取得します。
     * @return todofukenSid
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#todofukenSid__
     */
    public Integer getTodofukenSid() {
        return todofukenSid__;
    }
    /**
     * <p>todofukenSid をセットします。
     * @param todofukenSid todofukenSid
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#todofukenSid__
     */
    public void setTodofukenSid(Integer todofukenSid) {
        todofukenSid__ = todofukenSid;
    }
    /**
     * <p>todofukenName を取得します。
     * @return todofukenName
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#todofukenName__
     */
    public String getTodofukenName() {
        return todofukenName__;
    }
    /**
     * <p>todofukenName をセットします。
     * @param todofukenName todofukenName
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#todofukenName__
     */
    public void setTodofukenName(String todofukenName) {
        todofukenName__ = todofukenName;
    }
    /**
     * <p>todofukenKoukaiFlg を取得します。
     * @return todofukenKoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#todofukenKoukaiFlg__
     */
    public Integer getTodofukenKoukaiFlg() {
        return todofukenKoukaiFlg__;
    }
    /**
     * <p>todofukenKoukaiFlg をセットします。
     * @param todofukenKoukaiFlg todofukenKoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#todofukenKoukaiFlg__
     */
    public void setTodofukenKoukaiFlg(Integer todofukenKoukaiFlg) {
        todofukenKoukaiFlg__ = todofukenKoukaiFlg;
    }
    /**
     * <p>address1Text を取得します。
     * @return address1Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#address1Text__
     */
    public String getAddress1Text() {
        return address1Text__;
    }
    /**
     * <p>address1Text をセットします。
     * @param address1Text address1Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#address1Text__
     */
    public void setAddress1Text(String address1Text) {
        address1Text__ = address1Text;
    }
    /**
     * <p>address1KoukaiFlg を取得します。
     * @return address1KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#address1KoukaiFlg__
     */
    public Integer getAddress1KoukaiFlg() {
        return address1KoukaiFlg__;
    }
    /**
     * <p>address1KoukaiFlg をセットします。
     * @param address1KoukaiFlg address1KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#address1KoukaiFlg__
     */
    public void setAddress1KoukaiFlg(Integer address1KoukaiFlg) {
        address1KoukaiFlg__ = address1KoukaiFlg;
    }
    /**
     * <p>address2Text を取得します。
     * @return address2Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#address2Text__
     */
    public String getAddress2Text() {
        return address2Text__;
    }
    /**
     * <p>address2Text をセットします。
     * @param address2Text address2Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#address2Text__
     */
    public void setAddress2Text(String address2Text) {
        address2Text__ = address2Text;
    }
    /**
     * <p>address2KoukaiFlg を取得します。
     * @return address2KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#address2KoukaiFlg__
     */
    public Integer getAddress2KoukaiFlg() {
        return address2KoukaiFlg__;
    }
    /**
     * <p>address2KoukaiFlg をセットします。
     * @param address2KoukaiFlg address2KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#address2KoukaiFlg__
     */
    public void setAddress2KoukaiFlg(Integer address2KoukaiFlg) {
        address2KoukaiFlg__ = address2KoukaiFlg;
    }
    /**
     * <p>tel1Text を取得します。
     * @return tel1Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel1Text__
     */
    public String getTel1Text() {
        return tel1Text__;
    }
    /**
     * <p>tel1Text をセットします。
     * @param tel1Text tel1Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel1Text__
     */
    public void setTel1Text(String tel1Text) {
        tel1Text__ = tel1Text;
    }
    /**
     * <p>tel1NaisenText を取得します。
     * @return tel1NaisenText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel1NaisenText__
     */
    public String getTel1NaisenText() {
        return tel1NaisenText__;
    }
    /**
     * <p>tel1NaisenText をセットします。
     * @param tel1NaisenText tel1NaisenText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel1NaisenText__
     */
    public void setTel1NaisenText(String tel1NaisenText) {
        tel1NaisenText__ = tel1NaisenText;
    }
    /**
     * <p>tel1CommentText を取得します。
     * @return tel1CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel1CommentText__
     */
    public String getTel1CommentText() {
        return tel1CommentText__;
    }
    /**
     * <p>tel1CommentText をセットします。
     * @param tel1CommentText tel1CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel1CommentText__
     */
    public void setTel1CommentText(String tel1CommentText) {
        tel1CommentText__ = tel1CommentText;
    }
    /**
     * <p>tel1KoukaiFlg を取得します。
     * @return tel1KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel1KoukaiFlg__
     */
    public Integer getTel1KoukaiFlg() {
        return tel1KoukaiFlg__;
    }
    /**
     * <p>tel1KoukaiFlg をセットします。
     * @param tel1KoukaiFlg tel1KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel1KoukaiFlg__
     */
    public void setTel1KoukaiFlg(Integer tel1KoukaiFlg) {
        tel1KoukaiFlg__ = tel1KoukaiFlg;
    }
    /**
     * <p>tel2Text を取得します。
     * @return tel2Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel2Text__
     */
    public String getTel2Text() {
        return tel2Text__;
    }
    /**
     * <p>tel2Text をセットします。
     * @param tel2Text tel2Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel2Text__
     */
    public void setTel2Text(String tel2Text) {
        tel2Text__ = tel2Text;
    }
    /**
     * <p>tel2NaisenText を取得します。
     * @return tel2NaisenText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel2NaisenText__
     */
    public String getTel2NaisenText() {
        return tel2NaisenText__;
    }
    /**
     * <p>tel2NaisenText をセットします。
     * @param tel2NaisenText tel2NaisenText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel2NaisenText__
     */
    public void setTel2NaisenText(String tel2NaisenText) {
        tel2NaisenText__ = tel2NaisenText;
    }
    /**
     * <p>tel2CommentText を取得します。
     * @return tel2CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel2CommentText__
     */
    public String getTel2CommentText() {
        return tel2CommentText__;
    }
    /**
     * <p>tel2CommentText をセットします。
     * @param tel2CommentText tel2CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel2CommentText__
     */
    public void setTel2CommentText(String tel2CommentText) {
        tel2CommentText__ = tel2CommentText;
    }
    /**
     * <p>tel2KoukaiFlg を取得します。
     * @return tel2KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel2KoukaiFlg__
     */
    public Integer getTel2KoukaiFlg() {
        return tel2KoukaiFlg__;
    }
    /**
     * <p>tel2KoukaiFlg をセットします。
     * @param tel2KoukaiFlg tel2KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel2KoukaiFlg__
     */
    public void setTel2KoukaiFlg(Integer tel2KoukaiFlg) {
        tel2KoukaiFlg__ = tel2KoukaiFlg;
    }
    /**
     * <p>tel3Text を取得します。
     * @return tel3Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel3Text__
     */
    public String getTel3Text() {
        return tel3Text__;
    }
    /**
     * <p>tel3Text をセットします。
     * @param tel3Text tel3Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel3Text__
     */
    public void setTel3Text(String tel3Text) {
        tel3Text__ = tel3Text;
    }
    /**
     * <p>tel3NaisenText を取得します。
     * @return tel3NaisenText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel3NaisenText__
     */
    public String getTel3NaisenText() {
        return tel3NaisenText__;
    }
    /**
     * <p>tel3NaisenText をセットします。
     * @param tel3NaisenText tel3NaisenText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel3NaisenText__
     */
    public void setTel3NaisenText(String tel3NaisenText) {
        tel3NaisenText__ = tel3NaisenText;
    }
    /**
     * <p>tel3CommentText を取得します。
     * @return tel3CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel3CommentText__
     */
    public String getTel3CommentText() {
        return tel3CommentText__;
    }
    /**
     * <p>tel3CommentText をセットします。
     * @param tel3CommentText tel3CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel3CommentText__
     */
    public void setTel3CommentText(String tel3CommentText) {
        tel3CommentText__ = tel3CommentText;
    }
    /**
     * <p>tel3KoukaiFlg を取得します。
     * @return tel3KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel3KoukaiFlg__
     */
    public Integer getTel3KoukaiFlg() {
        return tel3KoukaiFlg__;
    }
    /**
     * <p>tel3KoukaiFlg をセットします。
     * @param tel3KoukaiFlg tel3KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#tel3KoukaiFlg__
     */
    public void setTel3KoukaiFlg(Integer tel3KoukaiFlg) {
        tel3KoukaiFlg__ = tel3KoukaiFlg;
    }
    /**
     * <p>fax1Text を取得します。
     * @return fax1Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax1Text__
     */
    public String getFax1Text() {
        return fax1Text__;
    }
    /**
     * <p>fax1Text をセットします。
     * @param fax1Text fax1Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax1Text__
     */
    public void setFax1Text(String fax1Text) {
        fax1Text__ = fax1Text;
    }
    /**
     * <p>fax1CommentText を取得します。
     * @return fax1CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax1CommentText__
     */
    public String getFax1CommentText() {
        return fax1CommentText__;
    }
    /**
     * <p>fax1CommentText をセットします。
     * @param fax1CommentText fax1CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax1CommentText__
     */
    public void setFax1CommentText(String fax1CommentText) {
        fax1CommentText__ = fax1CommentText;
    }
    /**
     * <p>fax1KoukaiFlg を取得します。
     * @return fax1KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax1KoukaiFlg__
     */
    public Integer getFax1KoukaiFlg() {
        return fax1KoukaiFlg__;
    }
    /**
     * <p>fax1KoukaiFlg をセットします。
     * @param fax1KoukaiFlg fax1KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax1KoukaiFlg__
     */
    public void setFax1KoukaiFlg(Integer fax1KoukaiFlg) {
        fax1KoukaiFlg__ = fax1KoukaiFlg;
    }
    /**
     * <p>fax2Text を取得します。
     * @return fax2Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax2Text__
     */
    public String getFax2Text() {
        return fax2Text__;
    }
    /**
     * <p>fax2Text をセットします。
     * @param fax2Text fax2Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax2Text__
     */
    public void setFax2Text(String fax2Text) {
        fax2Text__ = fax2Text;
    }
    /**
     * <p>fax2Comment を取得します。
     * @return fax2Comment
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax2CommentText__
     */
    public String getFax2CommentText() {
        return fax2CommentText__;
    }
    /**
     * <p>fax2Comment をセットします。
     * @param fax2Comment fax2Comment
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax2CommentText__
     */
    public void setFax2CommentText(String fax2Comment) {
        fax2CommentText__ = fax2Comment;
    }
    /**
     * <p>fax2KoukaiFlg を取得します。
     * @return fax2KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax2KoukaiFlg__
     */
    public Integer getFax2KoukaiFlg() {
        return fax2KoukaiFlg__;
    }
    /**
     * <p>fax2KoukaiFlg をセットします。
     * @param fax2KoukaiFlg fax2KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax2KoukaiFlg__
     */
    public void setFax2KoukaiFlg(Integer fax2KoukaiFlg) {
        fax2KoukaiFlg__ = fax2KoukaiFlg;
    }
    /**
     * <p>fax3Text を取得します。
     * @return fax3Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax3Text__
     */
    public String getFax3Text() {
        return fax3Text__;
    }
    /**
     * <p>fax3Text をセットします。
     * @param fax3Text fax3Text
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax3Text__
     */
    public void setFax3Text(String fax3Text) {
        fax3Text__ = fax3Text;
    }
    /**
     * <p>fax3CommentText を取得します。
     * @return fax3CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax3CommentText__
     */
    public String getFax3CommentText() {
        return fax3CommentText__;
    }
    /**
     * <p>fax3CommentText をセットします。
     * @param fax3CommentText fax3CommentText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax3CommentText__
     */
    public void setFax3CommentText(String fax3CommentText) {
        fax3CommentText__ = fax3CommentText;
    }
    /**
     * <p>fax3KoukaiFlg を取得します。
     * @return fax3KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax3KoukaiFlg__
     */
    public Integer getFax3KoukaiFlg() {
        return fax3KoukaiFlg__;
    }
    /**
     * <p>fax3KoukaiFlg をセットします。
     * @param fax3KoukaiFlg fax3KoukaiFlg
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#fax3KoukaiFlg__
     */
    public void setFax3KoukaiFlg(Integer fax3KoukaiFlg) {
        fax3KoukaiFlg__ = fax3KoukaiFlg;
    }
    /**
     * <p>bikouText を取得します。
     * @return bikouText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#bikouText__
     */
    public String getBikouText() {
        return bikouText__;
    }
    /**
     * <p>bikouText をセットします。
     * @param bikouText bikouText
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#bikouText__
     */
    public void setBikouText(String bikouText) {
        bikouText__ = bikouText;
    }
    /**
     * <p>addDateTime を取得します。
     * @return addDateTime
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#addDateTime__
     */
    public String getAddDateTime() {
        return addDateTime__;
    }
    /**
     * <p>addDateTime をセットします。
     * @param addDateTime addDateTime
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#addDateTime__
     */
    public void setAddDateTime(String addDateTime) {
        addDateTime__ = addDateTime;
    }
    /**
     * <p>editDateTime を取得します。
     * @return editDateTime
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#editDateTime__
     */
    public String getEditDateTime() {
        return editDateTime__;
    }
    /**
     * <p>editDateTime をセットします。
     * @param editDateTime editDateTime
     * @see jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel#editDateTime__
     */
    public void setEditDateTime(String editDateTime) {
        editDateTime__ = editDateTime;
    }
    public List<GroupInfo> getGroupArray() {
        return groupArray__;
    }
    public void setGroupArray(List<GroupInfo> groupArray) {
        groupArray__ = groupArray;
    }
    public List<LabelInfo> getLabelArray() {
        return labelArray__;
    }
    public void setLabelArray(List<LabelInfo> labelArray) {
        labelArray__ = labelArray;
    }

    public Long getImageBinSid() {
        return imageBinSid__;
    }
    public void setImageBinSid(Long imageBinSid) {
        imageBinSid__ = imageBinSid;
    }
    public Integer getImageKoukaiFlg() {
        return imageKoukaiFlg__;
    }
    public void setImageKoukaiFlg(Integer imageKoukaiFlg) {
        imageKoukaiFlg__ = imageKoukaiFlg;
    }
}
