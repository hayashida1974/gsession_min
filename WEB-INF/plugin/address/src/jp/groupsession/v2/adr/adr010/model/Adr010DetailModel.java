package jp.groupsession.v2.adr.adr010.model;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アドレス一覧帳画面の検索結果情報を保持するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr010DetailModel extends AbstractModel {

    /** アドレス帳SID */
    private int adrSid__ = 0;

    /** ユーザSID */
    private int userSid__ = 0;
    /** 氏名 */
    private String userName__ = null;
    /** 会社SID */
    private int acoSid__ = 0;
    /** 会社拠点SID */
    private int abaSid__ = 0;
    /** 企業コード */
    private String companyCode__ = null;
    /** 会社名 */
    private String companyName__ = null;
    /** 会社拠点名 */
    private String companyBaseName__ = null;
    /** 役職SID */
    private int apsSid__ = 0;
    /** 役職名 */
    private String positionName__ = null;
    /** E-MAIL1 */
    private String mail1__ = null;
    /** E-MAIL2 */
    private String mail2__ = null;
    /** E-MAIL3 */
    private String mail3__ = null;
    /** E-MAIL1 コメント */
    private String mailCmt1__ = null;
    /** E-MAIL2 コメント */
    private String mailCmt2__ = null;
    /** E-MAIL3 コメント */
    private String mailCmt3__ = null;
    /** 電話番号1 */
    private String tel1__ = null;
    /** 電話番号2 */
    private String tel2__ = null;
    /** 電話番号3 */
    private String tel3__ = null;
    /** 電話番号1 コメント */
    private String telCmt1__ = null;
    /** 電話番号2 コメント */
    private String telCmt2__ = null;
    /** 電話番号3 コメント */
    private String telCmt3__ = null;

    /** コンタクト履歴 コンタクト履歴SID */
    private int contactSid__ = 0;
    /** コンタクト履歴 日時 */
    private String contactDate__ = null;
    /** コンタクト履歴 種別 */
    private int contactType__ = 0;
    /** コンタクト履歴 タイトル */
    private String contactTitle__ = null;
    /** コンタクト履歴 登録者 */
    private String contactEntryUser__ = null;
    /** コンタクト履歴 登録者ログイン停止 */
    private int contactEntryUsrUkoFlg__ = 0;
    /** コンタクト履歴 登録者ログイン停止 */
    private int contactEntryUsrJkbn__ = 0;

    /** プロジェクトSID */
    private int prjSid__ = 0;

    /** ラベル名称 */
    private List<String> labelNameList__ = null;

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
     * <p>apsSid を取得します。
     * @return apsSid
     */
    public int getApsSid() {
        return apsSid__;
    }
    /**
     * <p>apsSid をセットします。
     * @param apsSid apsSid
     */
    public void setApsSid(int apsSid) {
        apsSid__ = apsSid;
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
     * @return prjSid
     */
    public int getPrjSid() {
        return prjSid__;
    }
    /**
     * @param prjSid 設定する prjSid
     */
    public void setPrjSid(int prjSid) {
        prjSid__ = prjSid;
    }
    /**
     * <p>mailCmt1 を取得します。
     * @return mailCmt1
     */
    public String getMailCmt1() {
        return mailCmt1__;
    }
    /**
     * <p>mailCmt1 をセットします。
     * @param mailCmt1 mailCmt1
     */
    public void setMailCmt1(String mailCmt1) {
        mailCmt1__ = mailCmt1;
    }
    /**
     * <p>mailCmt2 を取得します。
     * @return mailCmt2
     */
    public String getMailCmt2() {
        return mailCmt2__;
    }
    /**
     * <p>mailCmt2 をセットします。
     * @param mailCmt2 mailCmt2
     */
    public void setMailCmt2(String mailCmt2) {
        mailCmt2__ = mailCmt2;
    }
    /**
     * <p>mailCmt3 を取得します。
     * @return mailCmt3
     */
    public String getMailCmt3() {
        return mailCmt3__;
    }
    /**
     * <p>mailCmt3 をセットします。
     * @param mailCmt3 mailCmt3
     */
    public void setMailCmt3(String mailCmt3) {
        mailCmt3__ = mailCmt3;
    }
    /**
     * <p>telCmt1 を取得します。
     * @return telCmt1
     */
    public String getTelCmt1() {
        return telCmt1__;
    }
    /**
     * <p>telCmt1 をセットします。
     * @param telCmt1 telCmt1
     */
    public void setTelCmt1(String telCmt1) {
        telCmt1__ = telCmt1;
    }
    /**
     * <p>telCmt2 を取得します。
     * @return telCmt2
     */
    public String getTelCmt2() {
        return telCmt2__;
    }
    /**
     * <p>telCmt2 をセットします。
     * @param telCmt2 telCmt2
     */
    public void setTelCmt2(String telCmt2) {
        telCmt2__ = telCmt2;
    }
    /**
     * <p>telCmt3 を取得します。
     * @return telCmt3
     */
    public String getTelCmt3() {
        return telCmt3__;
    }
    /**
     * <p>telCmt3 をセットします。
     * @param telCmt3 telCmt3
     */
    public void setTelCmt3(String telCmt3) {
        telCmt3__ = telCmt3;
    }
    /**
     * <p>labelNameList を取得します。
     * @return labelNameList
     */
    public List<String> getLabelNameList() {
        return labelNameList__;
    }
    /**
     * <p>labelNameList をセットします。
     * @param labelNameList labelNameList
     */
    public void setLabelNameList(List<String> labelNameList) {
        labelNameList__ = labelNameList;
    }

    /**
     * <br>[機  能] 画面に表示するラベル名称を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return ラベル名称
     */
    public String getViewLabelName() {
        String labelName = null;
        if (labelNameList__ != null && !labelNameList__.isEmpty()) {
            labelName = labelNameList__.get(0);
            for (int idx = 1; idx < labelNameList__.size(); idx++) {
                labelName += ", " + labelNameList__.get(idx);
            }
        }
        return labelName;
    }
    /**
     * <p>contactEntryUsrUkoFlg を取得します。
     * @return contactEntryUsrUkoFlg
     */
    public int getContactEntryUsrUkoFlg() {
        return contactEntryUsrUkoFlg__;
    }
    /**
     * <p>contactEntryUsrUkoFlg をセットします。
     * @param contactEntryUsrUkoFlg contactEntryUsrUkoFlg
     */
    public void setContactEntryUsrUkoFlg(int contactEntryUsrUkoFlg) {
        contactEntryUsrUkoFlg__ = contactEntryUsrUkoFlg;
    }
    /**
     * <p>contactEntryUsrJkbn を取得します。
     * @return contactEntryUsrJkbn
     */
    public int getContactEntryUsrJkbn() {
        return contactEntryUsrJkbn__;
    }
    /**
     * <p>contactEntryUsrJkbn をセットします。
     * @param contactEntryUsrJkbn contactEntryUsrJkbn
     */
    public void setContactEntryUsrJkbn(int contactEntryUsrJkbn) {
        contactEntryUsrJkbn__ = contactEntryUsrJkbn;
    }
}