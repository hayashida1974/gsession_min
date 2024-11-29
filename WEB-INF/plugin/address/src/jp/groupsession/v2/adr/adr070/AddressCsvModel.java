package jp.groupsession.v2.adr.adr070;

import java.util.Comparator;

import jp.groupsession.v2.adr.adr120.CompanyCsvModel;

/**
 * <br>[機  能] アドレス帳 アドレスインポート インポートファイル(CSV)の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
@SuppressWarnings("all")
public class AddressCsvModel extends CompanyCsvModel implements Comparator {

    /** 行番号 */
    private long rowNum__ = 0;
    /** 項目数 */
    private int elementCount__ = 0;
    /** 氏名 姓 */
    private String nameSei__ = null;
    /** 氏名 名 */
    private String nameMei__ = null;
    /** 氏名カナ 姓 */
    private String nameSeiKn__ = null;
    /** 氏名カナ 名 */
    private String nameMeiKn__ = null;
    /** 所属 */
    private String syozoku__ = null;
    /** 役職 */
    private String yakusyoku__ = null;
    /** メールアドレス１ */
    private String mail1__ = null;
    /** メールアドレスコメント１ */
    private String mail1Comment__ = null;
    /** メールアドレス２ */
    private String mail2__ = null;
    /** メールアドレスコメント２ */
    private String mail2Comment__ = null;
    /** メールアドレス３ */
    private String mail3__ = null;
    /** メールアドレスコメント３ */
    private String mail3Comment__ = null;
    /** 郵便番号 */
    private String postNo__ = null;
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
    /** 電話番号２ */
    private String tel2__ = null;
    /** 内線２ */
    private String nai2__ = null;
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
     * <p>elementCount を取得します。
     * @return elementCount
     */
    public int getElementCount() {
        return elementCount__;
    }

    /**
     * <p>elementCount をセットします。
     * @param elementCount elementCount
     */
    public void setElementCount(int elementCount) {
        elementCount__ = elementCount;
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
     * <p>nameMei を取得します。
     * @return nameMei
     */
    public String getNameMei() {
        return nameMei__;
    }

    /**
     * <p>nameMei をセットします。
     * @param nameMei nameMei
     */
    public void setNameMei(String nameMei) {
        nameMei__ = nameMei;
    }

    /**
     * <p>nameMeiKn を取得します。
     * @return nameMeiKn
     */
    public String getNameMeiKn() {
        return nameMeiKn__;
    }

    /**
     * <p>nameMeiKn をセットします。
     * @param nameMeiKn nameMeiKn
     */
    public void setNameMeiKn(String nameMeiKn) {
        nameMeiKn__ = nameMeiKn;
    }

    /**
     * <p>nameSei を取得します。
     * @return nameSei
     */
    public String getNameSei() {
        return nameSei__;
    }

    /**
     * <p>nameSei をセットします。
     * @param nameSei nameSei
     */
    public void setNameSei(String nameSei) {
        nameSei__ = nameSei;
    }

    /**
     * <p>nameSeiKn を取得します。
     * @return nameSeiKn
     */
    public String getNameSeiKn() {
        return nameSeiKn__;
    }

    /**
     * <p>nameSeiKn をセットします。
     * @param nameSeiKn nameSeiKn
     */
    public void setNameSeiKn(String nameSeiKn) {
        nameSeiKn__ = nameSeiKn;
    }

    /**
     * <p>postNo を取得します。
     * @return postNo
     */
    public String getPostNo() {
        return postNo__;
    }

    /**
     * <p>postNo をセットします。
     * @param postNo postNo
     */
    public void setPostNo(String postNo) {
        postNo__ = postNo;
    }

    /**
     * <p>rowNum を取得します。
     * @return rowNum
     */
    public long getRowNum() {
        return rowNum__;
    }

    /**
     * <p>rowNum をセットします。
     * @param rowNum rowNum
     */
    public void setRowNum(long rowNum) {
        rowNum__ = rowNum;
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
     * <p>yakusyoku を取得します。
     * @return yakusyoku
     */
    public String getYakusyoku() {
        return yakusyoku__;
    }

    /**
     * <p>yakusyoku をセットします。
     * @param yakusyoku yakusyoku
     */
    public void setYakusyoku(String yakusyoku) {
        yakusyoku__ = yakusyoku;
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
     * <br>[機  能] 順序付けのために 2 つの引数を比較する。
     * <br>[解  説]
     * <br>[備  考]
     * @param obj1 比較対象の最初のオブジェクト
     * @param obj2 比較対象の2番目のオブジェクト
     * @return 最初の引数が 2 番目の引数より小さい場合は負の整数、両方が等しい場合は 0、
     *          最初の引数が 2 番目の引数より大きい場合は正の整数
     */
    public int compare(Object obj1, Object obj2) {
        return (int) (((AddressCsvModel) obj1).getRowNum() - ((AddressCsvModel) obj2).getRowNum());
    }
}