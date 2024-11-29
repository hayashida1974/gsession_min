package jp.groupsession.v2.adr.adr010.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アドレス帳一覧画面の検索結果情報(CSV出力用)を保持するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr010CsvDetailModel extends AbstractModel {

    /** アドレス帳SID */
    private int adrSid__ = 0;
    /** ユーザSID */
    private int userSid__ = 0;
    /** 会社SID */
    private int acoSid__ = 0;
    /** 会社拠点SID */
    private int abaSid__ = 0;
    /** 氏名(姓) */
    private String userSei__ = null;
    /** 氏名(名) */
    private String userMei__ = null;
    /** 氏名カナ(姓) */
    private String userSeiKn__ = null;
    /** 氏名カナ(名) */
    private String userMeiKn__ = null;
    /** 氏名(姓名) */
    private String userName__ = null;
    /** 氏名カナ(姓名) */
    private String userNameKn__ = null;
    /** 企業コード */
    private String companyCode__ = null;
    /** 会社名 */
    private String companyName__ = null;
    /** 会社名カナ */
    private String companyNameKn__ = null;
    /** url */
    private String companyUrl__ = null;
    /** 会社情報備考 */
    private String companyRemarks__ = null;

    /** 会社郵便番号上３桁 */
    private String companyPostNo1__ = null;
    /** 会社郵便番号下４桁 */
    private String companyPostNo2__ = null;
    /** 会社都道府県 */
    private String companyTdfk__ = null;
    /** 会社住所１ */
    private String companyAddress1__ = null;
    /** 会社住所２ */
    private String companyAddress2__ = null;

    /** 支店・営業所種別 */
    private String companyBaseType__ = null;
    /** 支店・営業所名 */
    private String companyBaseName__ = null;
    /** 支店・営業所郵便番号上３桁 */
    private String companyBasePostNo1__ = null;
    /** 支店・営業所郵便番号下４桁 */
    private String companyBasePostNo2__ = null;
    /** 支店・営業所都道府県 */
    private String companyBaseTdfk__ = null;
    /** 支店・営業所住所１ */
    private String companyBaseAddress1__ = null;
    /** 支店・営業所住所２ */
    private String companyBaseAddress2__ = null;
    /** 拠点情報備考 */
    private String companyBaseRemarks__ = null;

    /** 所属 */
    private String syozoku__ = null;
    /** 役職名 */
    private String positionName__ = null;

    /** メールアドレス１ */
    private String mail1__ = null;
    /** メールアドレスコメント１ */
    private String mail1Comment__ = null;
    /** メールアドレス２ */
    private String mail2__ = null;
    /** メールアドレスコメント１ */
    private String mail2Comment__ = null;
    /** メールアドレス３ */
    private String mail3__ = null;
    /** メールアドレスコメント１ */
    private String mail3Comment__ = null;

    /** 郵便番号上３桁 */
    private String postNo1__ = null;
    /** 郵便番号下４桁 */
    private String postNo2__ = null;
    /** 都道府県 */
    private String tdfk__ = null;
    /** 住所１ */
    private String address1__ = null;
    /** 住所２ */
    private String address2__ = null;

    /** 電話番号１ */
    private String tel1__ = null;
    /** 内線１ */
    private String nai1__ = null;
    /** 電話番号コメント１ */
    private String tel1Comment__ = null;
    /** 内線２ */
    private String nai2__ = null;
    /** 電話番号２ */
    private String tel2__ = null;
    /** 電話番号コメント２ */
    private String tel2Comment__ = null;
    /** 電話番号３ */
    private String tel3__ = null;
    /** 内線３ */
    private String nai3__ = null;
    /** 電話番号コメント３ */
    private String tel3Comment__ = null;

    /** ＦＡＸ１ */
    private String fax1__ = null;
    /** ＦＡＸコメント１ */
    private String fax1Comment__ = null;
    /** ＦＡＸ２ */
    private String fax2__ = null;
    /** ＦＡＸコメント２ */
    private String fax2Comment__ = null;
    /** ＦＡＸ３ */
    private String fax3__ = null;
    /** ＦＡＸコメント３ */
    private String fax3Comment__ = null;

    /** 備考 */
    private String biko__ = null;

    /** コンタクト履歴 コンタクト履歴SID */
    private int contactSid__ = 0;
    /** コンタクト履歴 日時Form */
    private String contactDate__ = null;
    /** コンタクト履歴 日時Form */
    private String contactTime__ = null;
    /** コンタクト履歴 日時To */
    private String contactDateTo__ = null;
    /** コンタクト履歴 日時To */
    private String contactTimeTo__ = null;

    /** コンタクト履歴 種別 */
    private int contactType__ = 0;
    /** コンタクト履歴 タイトル */
    private String contactTitle__ = null;
    /** コンタクト履歴 登録者 */
    private String contactEntryUser__ = null;
    /** コンタクト履歴種別名 */
    private String typeName__ = null;
    /** コンタクト履歴 備考 */
    private String contactBiko__ = null;

    /**
     * <p>abaSid を取得します。
     * @return abaSid
     */
    public int getAbaSid() {
        return abaSid__;
    }

    /**
     * <p>abaSid をセットします。
     * @param abaSid abaSid
     */
    public void setAbaSid(int abaSid) {
        abaSid__ = abaSid;
    }

    /**
     * <p>acoSid を取得します。
     * @return acoSid
     */
    public int getAcoSid() {
        return acoSid__;
    }

    /**
     * <p>acoSid をセットします。
     * @param acoSid acoSid
     */
    public void setAcoSid(int acoSid) {
        acoSid__ = acoSid;
    }

    /**
     * <p>address1 を取得します。
     * @return address1
     */
    public String getAddress1() {
        return address1__;
    }

    /**
     * <p>address1 をセットします。
     * @param address1 address1
     */
    public void setAddress1(String address1) {
        address1__ = address1;
    }

    /**
     * <p>address2 を取得します。
     * @return address2
     */
    public String getAddress2() {
        return address2__;
    }

    /**
     * <p>address2 をセットします。
     * @param address2 address2
     */
    public void setAddress2(String address2) {
        address2__ = address2;
    }

    /**
     * <p>adrSid を取得します。
     * @return adrSid
     */
    public int getAdrSid() {
        return adrSid__;
    }

    /**
     * <p>adrSid をセットします。
     * @param adrSid adrSid
     */
    public void setAdrSid(int adrSid) {
        adrSid__ = adrSid;
    }

    /**
     * <p>biko を取得します。
     * @return biko
     */
    public String getBiko() {
        return biko__;
    }

    /**
     * <p>biko をセットします。
     * @param biko biko
     */
    public void setBiko(String biko) {
        biko__ = biko;
    }

    /**
     * <p>companyBaseName を取得します。
     * @return companyBaseName
     */
    public String getCompanyBaseName() {
        return companyBaseName__;
    }

    /**
     * <p>companyBaseName をセットします。
     * @param companyBaseName companyBaseName
     */
    public void setCompanyBaseName(String companyBaseName) {
        companyBaseName__ = companyBaseName;
    }

    /**
     * <p>companyBasePostNo1 を取得します。
     * @return companyBasePostNo1
     */
    public String getCompanyBasePostNo1() {
        return companyBasePostNo1__;
    }

    /**
     * <p>companyBasePostNo1 をセットします。
     * @param companyBasePostNo1 companyBasePostNo1
     */
    public void setCompanyBasePostNo1(String companyBasePostNo1) {
        companyBasePostNo1__ = companyBasePostNo1;
    }

    /**
     * <p>companyBasePostNo2 を取得します。
     * @return companyBasePostNo2
     */
    public String getCompanyBasePostNo2() {
        return companyBasePostNo2__;
    }

    /**
     * <p>companyBasePostNo2 をセットします。
     * @param companyBasePostNo2 companyBasePostNo2
     */
    public void setCompanyBasePostNo2(String companyBasePostNo2) {
        companyBasePostNo2__ = companyBasePostNo2;
    }

    /**
     * <p>companyBaseTdfk を取得します。
     * @return companyBaseTdfk
     */
    public String getCompanyBaseTdfk() {
        return companyBaseTdfk__;
    }

    /**
     * <p>companyBaseTdfk をセットします。
     * @param companyBaseTdfk companyBaseTdfk
     */
    public void setCompanyBaseTdfk(String companyBaseTdfk) {
        companyBaseTdfk__ = companyBaseTdfk;
    }

    /**
     * <p>companyBaseType を取得します。
     * @return companyBaseType
     */
    public String getCompanyBaseType() {
        return companyBaseType__;
    }

    /**
     * <p>companyBaseType をセットします。
     * @param companyBaseType companyBaseType
     */
    public void setCompanyBaseType(String companyBaseType) {
        companyBaseType__ = companyBaseType;
    }

    /**
     * <p>companyName を取得します。
     * @return companyName
     */
    public String getCompanyName() {
        return companyName__;
    }

    /**
     * <p>companyName をセットします。
     * @param companyName companyName
     */
    public void setCompanyName(String companyName) {
        companyName__ = companyName;
    }

    /**
     * <p>companyNameKn を取得します。
     * @return companyNameKn
     */
    public String getCompanyNameKn() {
        return companyNameKn__;
    }

    /**
     * <p>companyNameKn をセットします。
     * @param companyNameKn companyNameKn
     */
    public void setCompanyNameKn(String companyNameKn) {
        companyNameKn__ = companyNameKn;
    }

    /**
     * <p>companyUrl を取得します。
     * @return companyUrl
     */
    public String getCompanyUrl() {
        return companyUrl__;
    }

    /**
     * <p>companyUrl をセットします。
     * @param companyUrl companyUrl
     */
    public void setCompanyUrl(String companyUrl) {
        companyUrl__ = companyUrl;
    }

    /**
     * <p>companyRemarks を取得します。
     * @return companyRemarks
     */
    public String getCompanyRemarks() {
        return companyRemarks__;
    }

    /**
     * <p>companyRemarks をセットします。
     * @param companyRemarks companyRemarks
     */
    public void setCompanyRemarks(String companyRemarks) {
        companyRemarks__ = companyRemarks;
    }

    /**
     * <p>companyPostNo1 を取得します。
     * @return companyPostNo1
     * @see jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel#companyPostNo1__
     */
    public String getCompanyPostNo1() {
        return companyPostNo1__;
    }

    /**
     * <p>companyPostNo1 をセットします。
     * @param companyPostNo1 companyPostNo1
     * @see jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel#companyPostNo1__
     */
    public void setCompanyPostNo1(String companyPostNo1) {
        companyPostNo1__ = companyPostNo1;
    }

    /**
     * <p>companyPostNo2 を取得します。
     * @return companyPostNo2
     * @see jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel#companyPostNo2__
     */
    public String getCompanyPostNo2() {
        return companyPostNo2__;
    }

    /**
     * <p>companyPostNo2 をセットします。
     * @param companyPostNo2 companyPostNo2
     * @see jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel#companyPostNo2__
     */
    public void setCompanyPostNo2(String companyPostNo2) {
        companyPostNo2__ = companyPostNo2;
    }

    /**
     * <p>companyTdfk を取得します。
     * @return companyTdfk
     * @see jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel#companyTdfk__
     */
    public String getCompanyTdfk() {
        return companyTdfk__;
    }

    /**
     * <p>companyTdfk をセットします。
     * @param companyTdfk companyTdfk
     * @see jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel#companyTdfk__
     */
    public void setCompanyTdfk(String companyTdfk) {
        companyTdfk__ = companyTdfk;
    }

    /**
     * <p>companyAddress1 を取得します。
     * @return companyAddress1
     * @see jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel#companyAddress1__
     */
    public String getCompanyAddress1() {
        return companyAddress1__;
    }

    /**
     * <p>companyAddress1 をセットします。
     * @param companyAddress1 companyAddress1
     * @see jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel#companyAddress1__
     */
    public void setCompanyAddress1(String companyAddress1) {
        companyAddress1__ = companyAddress1;
    }

    /**
     * <p>companyAddress2 を取得します。
     * @return companyAddress2
     * @see jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel#companyAddress2__
     */
    public String getCompanyAddress2() {
        return companyAddress2__;
    }

    /**
     * <p>companyAddress2 をセットします。
     * @param companyAddress2 companyAddress2
     * @see jp.groupsession.v2.adr.adr010.model.Adr010CsvDetailModel#companyAddress2__
     */
    public void setCompanyAddress2(String companyAddress2) {
        companyAddress2__ = companyAddress2;
    }

    /**
     * <p>fax1 を取得します。
     * @return fax1
     */
    public String getFax1() {
        return fax1__;
    }

    /**
     * <p>fax1 をセットします。
     * @param fax1 fax1
     */
    public void setFax1(String fax1) {
        fax1__ = fax1;
    }

    /**
     * <p>fax1Comment を取得します。
     * @return fax1Comment
     */
    public String getFax1Comment() {
        return fax1Comment__;
    }

    /**
     * <p>fax1Comment をセットします。
     * @param fax1Comment fax1Comment
     */
    public void setFax1Comment(String fax1Comment) {
        fax1Comment__ = fax1Comment;
    }

    /**
     * <p>fax2 を取得します。
     * @return fax2
     */
    public String getFax2() {
        return fax2__;
    }

    /**
     * <p>fax2 をセットします。
     * @param fax2 fax2
     */
    public void setFax2(String fax2) {
        fax2__ = fax2;
    }

    /**
     * <p>fax2Comment を取得します。
     * @return fax2Comment
     */
    public String getFax2Comment() {
        return fax2Comment__;
    }

    /**
     * <p>fax2Comment をセットします。
     * @param fax2Comment fax2Comment
     */
    public void setFax2Comment(String fax2Comment) {
        fax2Comment__ = fax2Comment;
    }

    /**
     * <p>fax3 を取得します。
     * @return fax3
     */
    public String getFax3() {
        return fax3__;
    }

    /**
     * <p>fax3 をセットします。
     * @param fax3 fax3
     */
    public void setFax3(String fax3) {
        fax3__ = fax3;
    }

    /**
     * <p>fax3Comment を取得します。
     * @return fax3Comment
     */
    public String getFax3Comment() {
        return fax3Comment__;
    }

    /**
     * <p>fax3Comment をセットします。
     * @param fax3Comment fax3Comment
     */
    public void setFax3Comment(String fax3Comment) {
        fax3Comment__ = fax3Comment;
    }

    /**
     * <p>mail1 を取得します。
     * @return mail1
     */
    public String getMail1() {
        return mail1__;
    }

    /**
     * <p>mail1 をセットします。
     * @param mail1 mail1
     */
    public void setMail1(String mail1) {
        mail1__ = mail1;
    }

    /**
     * <p>mail1Comment を取得します。
     * @return mail1Comment
     */
    public String getMail1Comment() {
        return mail1Comment__;
    }

    /**
     * <p>mail1Comment をセットします。
     * @param mail1Comment mail1Comment
     */
    public void setMail1Comment(String mail1Comment) {
        mail1Comment__ = mail1Comment;
    }

    /**
     * <p>mail2 を取得します。
     * @return mail2
     */
    public String getMail2() {
        return mail2__;
    }

    /**
     * <p>mail2 をセットします。
     * @param mail2 mail2
     */
    public void setMail2(String mail2) {
        mail2__ = mail2;
    }

    /**
     * <p>mail2Comment を取得します。
     * @return mail2Comment
     */
    public String getMail2Comment() {
        return mail2Comment__;
    }

    /**
     * <p>mail2Comment をセットします。
     * @param mail2Comment mail2Comment
     */
    public void setMail2Comment(String mail2Comment) {
        mail2Comment__ = mail2Comment;
    }

    /**
     * <p>mail3 を取得します。
     * @return mail3
     */
    public String getMail3() {
        return mail3__;
    }

    /**
     * <p>mail3 をセットします。
     * @param mail3 mail3
     */
    public void setMail3(String mail3) {
        mail3__ = mail3;
    }

    /**
     * <p>mail3Comment を取得します。
     * @return mail3Comment
     */
    public String getMail3Comment() {
        return mail3Comment__;
    }

    /**
     * <p>mail3Comment をセットします。
     * @param mail3Comment mail3Comment
     */
    public void setMail3Comment(String mail3Comment) {
        mail3Comment__ = mail3Comment;
    }

    /**
     * <p>nai1 を取得します。
     * @return nai1
     */
    public String getNai1() {
        return nai1__;
    }

    /**
     * <p>nai1 をセットします。
     * @param nai1 nai1
     */
    public void setNai1(String nai1) {
        nai1__ = nai1;
    }

    /**
     * <p>nai2 を取得します。
     * @return nai2
     */
    public String getNai2() {
        return nai2__;
    }

    /**
     * <p>nai2 をセットします。
     * @param nai2 nai2
     */
    public void setNai2(String nai2) {
        nai2__ = nai2;
    }

    /**
     * <p>nai3 を取得します。
     * @return nai3
     */
    public String getNai3() {
        return nai3__;
    }

    /**
     * <p>nai3 をセットします。
     * @param nai3 nai3
     */
    public void setNai3(String nai3) {
        nai3__ = nai3;
    }

    /**
     * <p>positionName を取得します。
     * @return positionName
     */
    public String getPositionName() {
        return positionName__;
    }

    /**
     * <p>positionName をセットします。
     * @param positionName positionName
     */
    public void setPositionName(String positionName) {
        positionName__ = positionName;
    }

    /**
     * <p>postNo1 を取得します。
     * @return postNo1
     */
    public String getPostNo1() {
        return postNo1__;
    }

    /**
     * <p>postNo1 をセットします。
     * @param postNo1 postNo1
     */
    public void setPostNo1(String postNo1) {
        postNo1__ = postNo1;
    }

    /**
     * <p>postNo2 を取得します。
     * @return postNo2
     */
    public String getPostNo2() {
        return postNo2__;
    }

    /**
     * <p>postNo2 をセットします。
     * @param postNo2 postNo2
     */
    public void setPostNo2(String postNo2) {
        postNo2__ = postNo2;
    }

    /**
     * <p>syozoku を取得します。
     * @return syozoku
     */
    public String getSyozoku() {
        return syozoku__;
    }

    /**
     * <p>syozoku をセットします。
     * @param syozoku syozoku
     */
    public void setSyozoku(String syozoku) {
        syozoku__ = syozoku;
    }

    /**
     * <p>tdfk を取得します。
     * @return tdfk
     */
    public String getTdfk() {
        return tdfk__;
    }

    /**
     * <p>tdfk をセットします。
     * @param tdfk tdfk
     */
    public void setTdfk(String tdfk) {
        tdfk__ = tdfk;
    }

    /**
     * <p>tel1 を取得します。
     * @return tel1
     */
    public String getTel1() {
        return tel1__;
    }

    /**
     * <p>tel1 をセットします。
     * @param tel1 tel1
     */
    public void setTel1(String tel1) {
        tel1__ = tel1;
    }

    /**
     * <p>tel1Comment を取得します。
     * @return tel1Comment
     */
    public String getTel1Comment() {
        return tel1Comment__;
    }

    /**
     * <p>tel1Comment をセットします。
     * @param tel1Comment tel1Comment
     */
    public void setTel1Comment(String tel1Comment) {
        tel1Comment__ = tel1Comment;
    }

    /**
     * <p>tel2 を取得します。
     * @return tel2
     */
    public String getTel2() {
        return tel2__;
    }

    /**
     * <p>tel2 をセットします。
     * @param tel2 tel2
     */
    public void setTel2(String tel2) {
        tel2__ = tel2;
    }

    /**
     * <p>tel2Comment を取得します。
     * @return tel2Comment
     */
    public String getTel2Comment() {
        return tel2Comment__;
    }

    /**
     * <p>tel2Comment をセットします。
     * @param tel2Comment tel2Comment
     */
    public void setTel2Comment(String tel2Comment) {
        tel2Comment__ = tel2Comment;
    }

    /**
     * <p>tel3 を取得します。
     * @return tel3
     */
    public String getTel3() {
        return tel3__;
    }

    /**
     * <p>tel3 をセットします。
     * @param tel3 tel3
     */
    public void setTel3(String tel3) {
        tel3__ = tel3;
    }

    /**
     * <p>tel3Comment を取得します。
     * @return tel3Comment
     */
    public String getTel3Comment() {
        return tel3Comment__;
    }

    /**
     * <p>tel3Comment をセットします。
     * @param tel3Comment tel3Comment
     */
    public void setTel3Comment(String tel3Comment) {
        tel3Comment__ = tel3Comment;
    }

    /**
     * <p>userName を取得します。
     * @return userName
     */
    public String getUserName() {
        return userName__;
    }

    /**
     * <p>userName をセットします。
     * @param userName userName
     */
    public void setUserName(String userName) {
        userName__ = userName;
    }

    /**
     * <p>userNameKn を取得します。
     * @return userNameKn
     */
    public String getUserNameKn() {
        return userNameKn__;
    }

    /**
     * <p>userNameKn をセットします。
     * @param userNameKn userNameKn
     */
    public void setUserNameKn(String userNameKn) {
        userNameKn__ = userNameKn;
    }

    /**
     * <p>companyCode を取得します。
     * @return companyCode
     */
    public String getCompanyCode() {
        return companyCode__;
    }

    /**
     * <p>companyCode をセットします。
     * @param companyCode companyCode
     */
    public void setCompanyCode(String companyCode) {
        companyCode__ = companyCode;
    }

    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }

    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }

    /**
     * <p>companyBaseAddress1 を取得します。
     * @return companyBaseAddress1
     */
    public String getCompanyBaseAddress1() {
        return companyBaseAddress1__;
    }

    /**
     * <p>companyBaseAddress1 をセットします。
     * @param companyBaseAddress1 companyBaseAddress1
     */
    public void setCompanyBaseAddress1(String companyBaseAddress1) {
        companyBaseAddress1__ = companyBaseAddress1;
    }

    /**
     * <p>companyBaseAddress2 を取得します。
     * @return companyBaseAddress2
     */
    public String getCompanyBaseAddress2() {
        return companyBaseAddress2__;
    }

    /**
     * <p>companyBaseAddress2 をセットします。
     * @param companyBaseAddress2 companyBaseAddress2
     */
    public void setCompanyBaseAddress2(String companyBaseAddress2) {
        companyBaseAddress2__ = companyBaseAddress2;
    }

    /**
     * <p>companyBaseRemarks を取得します。
     * @return companyBaseRemarks
     */
    public String getCompanyBaseRemarks() {
        return companyBaseRemarks__;
    }

    /**
     * <p>companyBaseRemarks をセットします。
     * @param companyBaseRemarks companyBaseRemarks
     */
    public void setCompanyBaseRemarks(String companyBaseRemarks) {
        companyBaseRemarks__ = companyBaseRemarks;
    }

    /**
     * <p>contactDate を取得します。
     * @return contactDate
     */
    public String getContactDate() {
        return contactDate__;
    }

    /**
     * <p>contactDate をセットします。
     * @param contactDate contactDate
     */
    public void setContactDate(String contactDate) {
        contactDate__ = contactDate;
    }

    /**
     * <p>contactEntryUser を取得します。
     * @return contactEntryUser
     */
    public String getContactEntryUser() {
        return contactEntryUser__;
    }

    /**
     * <p>contactEntryUser をセットします。
     * @param contactEntryUser contactEntryUser
     */
    public void setContactEntryUser(String contactEntryUser) {
        contactEntryUser__ = contactEntryUser;
    }

    /**
     * <p>contactSid を取得します。
     * @return contactSid
     */
    public int getContactSid() {
        return contactSid__;
    }

    /**
     * <p>contactSid をセットします。
     * @param contactSid contactSid
     */
    public void setContactSid(int contactSid) {
        contactSid__ = contactSid;
    }

    /**
     * <p>contactTitle を取得します。
     * @return contactTitle
     */
    public String getContactTitle() {
        return contactTitle__;
    }

    /**
     * <p>contactTitle をセットします。
     * @param contactTitle contactTitle
     */
    public void setContactTitle(String contactTitle) {
        contactTitle__ = contactTitle;
    }

    /**
     * <p>contactType を取得します。
     * @return contactType
     */
    public int getContactType() {
        return contactType__;
    }

    /**
     * <p>contactType をセットします。
     * @param contactType contactType
     */
    public void setContactType(int contactType) {
        contactType__ = contactType;
    }

    /**
     * <p>typeName を取得します。
     * @return typeName
     */
    public String getTypeName() {
        return typeName__;
    }

    /**
     * <p>typeName をセットします。
     * @param typeName typeName
     */
    public void setTypeName(String typeName) {
        typeName__ = typeName;
    }

    /**
     * <p>contactDateTo を取得します。
     * @return contactDateTo
     */
    public String getContactDateTo() {
        return contactDateTo__;
    }

    /**
     * <p>contactDateTo をセットします。
     * @param contactDateTo contactDateTo
     */
    public void setContactDateTo(String contactDateTo) {
        contactDateTo__ = contactDateTo;
    }

    /**
     * <p>contactBiko を取得します。
     * @return contactBiko
     */
    public String getContactBiko() {
        return contactBiko__;
    }

    /**
     * <p>contactBiko をセットします。
     * @param contactBiko contactBiko
     */
    public void setContactBiko(String contactBiko) {
        contactBiko__ = contactBiko;
    }

    /**
     * <p>contactTime を取得します。
     * @return contactTime
     */
    public String getContactTime() {
        return contactTime__;
    }

    /**
     * <p>contactTime をセットします。
     * @param contactTime contactTime
     */
    public void setContactTime(String contactTime) {
        contactTime__ = contactTime;
    }

    /**
     * <p>contactTimeTo を取得します。
     * @return contactTimeTo
     */
    public String getContactTimeTo() {
        return contactTimeTo__;
    }

    /**
     * <p>contactTimeTo をセットします。
     * @param contactTimeTo contactTimeTo
     */
    public void setContactTimeTo(String contactTimeTo) {
        contactTimeTo__ = contactTimeTo;
    }

    /**
     * @return userSei
     */
    public String getUserSei() {
        return userSei__;
    }

    /**
     * @param userSei セットする userSei
     */
    public void setUserSei(String userSei) {
        userSei__ = userSei;
    }

    /**
     * @return userMei
     */
    public String getUserMei() {
        return userMei__;
    }

    /**
     * @param userMei セットする userMei
     */
    public void setUserMei(String userMei) {
        userMei__ = userMei;
    }

    /**
     * @return userSeiKn
     */
    public String getUserSeiKn() {
        return userSeiKn__;
    }

    /**
     * @param userSeiKn セットする userSeiKn
     */
    public void setUserSeiKn(String userSeiKn) {
        userSeiKn__ = userSeiKn;
    }

    /**
     * @return userMeiKn
     */
    public String getUserMeiKn() {
        return userMeiKn__;
    }

    /**
     * @param userMeiKn セットする userMeiKn
     */
    public void setUserMeiKn(String userMeiKn) {
        userMeiKn__ = userMeiKn;
    }

}