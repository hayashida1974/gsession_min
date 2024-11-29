package jp.groupsession.v2.adr.adr160;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.adr.adr010.Adr010ParamModel;
import jp.groupsession.v2.adr.adr160.model.Adr160ContactModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 コンタクト履歴一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr160ParamModel extends Adr010ParamModel {

    /** ページラベル */
    ArrayList < LabelValueBean > adr160PageLabel__;

    /** 氏名 */
    private String adr160simei__;
    /** 氏名(カナ) */
    private String adr160simeikana__;
    /** 会社 */
    private String adr160kaisya__;
    /** 会社拠点 */
    private String adr160kaisyakyoten__;
    /** 所属 */
    private String adr160syozoku__;
    /** 役職 */
    private String adr160yakusyoku__;

    /** エクスポート権限 */
    private int adr160exportPower__ = 0;
    /** コンタクト履歴有無 */
    private int adr160dataExist__ = 0;
    /** ラベル有無 */
    private int adr160labelExist__ = 0;

    /** コンタクト履歴 */
    private List<Adr160ContactModel> adr160contactList__;
    /** ラベル一覧 */
    private List<AdrLabelModel> adr160labelList__;

    /** コンタクト履歴SID(修正対象) */
    private int adr160EditSid__ = 0;
    /** 処理区分 */
    private int adr160ProcMode__;

    /** メールアドレス1 */
    private String adr160MailAddress1__;
    /** メールアドレス1 コメント */
    private String adr160MailComment1__;
    /** メールアドレス2 */
    private String adr160MailAddress2__;
    /** メールアドレス2 コメント */
    private String adr160MailComment2__;
    /** メールアドレス3 */
    private String adr160MailAddress3__;
    /** メールアドレス3 コメント */
    private String adr160MailComment3__;
    /** 電話番号1 */
    private String adr160Tel1__;
    /** 電話番号1 内線 */
    private String adr160TelNaisen1__;
    /** 電話番号1 コメント */
    private String adr160TelComment1__;
    /** 電話番号2 */
    private String adr160Tel2__;
    /** 電話番号2 内線 */
    private String adr160TelNaisen2__;
    /** 電話番号2 コメント */
    private String adr160TelComment2__;
    /** 電話番号3 */
    private String adr160Tel3__;
    /** 電話番号3 内線 */
    private String adr160TelNaisen3__;
    /** 電話番号3 コメント */
    private String adr160TelComment3__;
    /** FAX1 */
    private String adr160Fax1__;
    /** FAX1 コメント */
    private String adr160FaxComment1__;
    /** FAX2 */
    private String adr160Fax2__;
    /** FAX2 コメント */
    private String adr160FaxComment2__;
    /** FAX3 */
    private String adr160Fax3__;
    /** FAX3 コメント */
    private String adr160FaxComment3__;
    /** 郵便番号 */
    private String adr160PostNo__;
    /** 都道府県名 */
    private String adr160TdfName__;
    /** 住所1 */
    private String adr160Address1__;
    /** 住所2 */
    private String adr160Address2__;
    /** 備考 */
    private String adr160Biko__;
    /** 担当者名 */
    private ArrayList<UsrLabelValueBean> adr160TantoUserName__ = null;

    /**
     * <p>adr160contactList を取得します。
     * @return adr160contactList
     */
    public List<Adr160ContactModel> getAdr160contactList() {
        return adr160contactList__;
    }
    /**
     * <p>adr160contactList をセットします。
     * @param adr160contactList adr160contactList
     */
    public void setAdr160contactList(List<Adr160ContactModel> adr160contactList) {
        adr160contactList__ = adr160contactList;
    }
    /**
     * <p>adr160kaisya を取得します。
     * @return adr160kaisya
     */
    public String getAdr160kaisya() {
        return adr160kaisya__;
    }
    /**
     * <p>adr160kaisya をセットします。
     * @param adr160kaisya adr160kaisya
     */
    public void setAdr160kaisya(String adr160kaisya) {
        adr160kaisya__ = adr160kaisya;
    }
    /**
     * <p>adr160labelList を取得します。
     * @return adr160labelList
     */
    public List<AdrLabelModel> getAdr160labelList() {
        return adr160labelList__;
    }
    /**
     * <p>adr160labelList をセットします。
     * @param adr160labelList adr160labelList
     */
    public void setAdr160labelList(List<AdrLabelModel> adr160labelList) {
        adr160labelList__ = adr160labelList;
    }
    /**
     * <p>adr160PageLabel を取得します。
     * @return adr160PageLabel
     */
    public ArrayList<LabelValueBean> getAdr160PageLabel() {
        return adr160PageLabel__;
    }
    /**
     * <p>adr160PageLabel をセットします。
     * @param adr160PageLabel adr160PageLabel
     */
    public void setAdr160PageLabel(ArrayList<LabelValueBean> adr160PageLabel) {
        adr160PageLabel__ = adr160PageLabel;
    }
//    /**
//     * <p>adr160pageNum1 を取得します。
//     * @return adr160pageNum1
//     */
//    public int getAdr160pageNum1() {
//        return adr160pageNum1__;
//    }
//    /**
//     * <p>adr160pageNum1 をセットします。
//     * @param adr160pageNum1 adr160pageNum1
//     */
//    public void setAdr160pageNum1(int adr160pageNum1) {
//        adr160pageNum1__ = adr160pageNum1;
//    }
//    /**
//     * <p>adr160pageNum2 を取得します。
//     * @return adr160pageNum2
//     */
//    public int getAdr160pageNum2() {
//        return adr160pageNum2__;
//    }
//    /**
//     * <p>adr160pageNum2 をセットします。
//     * @param adr160pageNum2 adr160pageNum2
//     */
//    public void setAdr160pageNum2(int adr160pageNum2) {
//        adr160pageNum2__ = adr160pageNum2;
//    }
    /**
     * <p>adr160simei を取得します。
     * @return adr160simei
     */
    public String getAdr160simei() {
        return adr160simei__;
    }
    /**
     * <p>adr160simei をセットします。
     * @param adr160simei adr160simei
     */
    public void setAdr160simei(String adr160simei) {
        adr160simei__ = adr160simei;
    }
    /**
     * <p>adr160simeikana を取得します。
     * @return adr160simeikana
     */
    public String getAdr160simeikana() {
        return adr160simeikana__;
    }
    /**
     * <p>adr160simeikana をセットします。
     * @param adr160simeikana adr160simeikana
     */
    public void setAdr160simeikana(String adr160simeikana) {
        adr160simeikana__ = adr160simeikana;
    }
    /**
     * <p>adr160syozoku を取得します。
     * @return adr160syozoku
     */
    public String getAdr160syozoku() {
        return adr160syozoku__;
    }
    /**
     * <p>adr160syozoku をセットします。
     * @param adr160syozoku adr160syozoku
     */
    public void setAdr160syozoku(String adr160syozoku) {
        adr160syozoku__ = adr160syozoku;
    }
    /**
     * <p>adr160yakusyoku を取得します。
     * @return adr160yakusyoku
     */
    public String getAdr160yakusyoku() {
        return adr160yakusyoku__;
    }
    /**
     * <p>adr160yakusyoku をセットします。
     * @param adr160yakusyoku adr160yakusyoku
     */
    public void setAdr160yakusyoku(String adr160yakusyoku) {
        adr160yakusyoku__ = adr160yakusyoku;
    }
//    /**
//     * <p>orderKey を取得します。
//     * @return orderKey
//     */
//    public String getOrderKey() {
//        return orderKey__;
//    }
//    /**
//     * <p>orderKey をセットします。
//     * @param orderKey orderKey
//     */
//    public void setOrderKey(String orderKey) {
//        orderKey__ = orderKey;
//    }
//    /**
//     * <p>sortKey を取得します。
//     * @return sortKey
//     */
//    public String getSortKey() {
//        return sortKey__;
//    }
//    /**
//     * <p>sortKey をセットします。
//     * @param sortKey sortKey
//     */
//    public void setSortKey(String sortKey) {
//        sortKey__ = sortKey;
//    }
    /**
     * <p>adr160kaisyakyoten を取得します。
     * @return adr160kaisyakyoten
     */
    public String getAdr160kaisyakyoten() {
        return adr160kaisyakyoten__;
    }
    /**
     * <p>adr160kaisyakyoten をセットします。
     * @param adr160kaisyakyoten adr160kaisyakyoten
     */
    public void setAdr160kaisyakyoten(String adr160kaisyakyoten) {
        adr160kaisyakyoten__ = adr160kaisyakyoten;
    }
    /**
     * <p>adr160EditSid を取得します。
     * @return adr160EditSid
     */
    public int getAdr160EditSid() {
        return adr160EditSid__;
    }
    /**
     * <p>adr160EditSid をセットします。
     * @param adr160EditSid adr160EditSid
     */
    public void setAdr160EditSid(int adr160EditSid) {
        adr160EditSid__ = adr160EditSid;
    }
    /**
     * <p>adr160ProcMode を取得します。
     * @return adr160ProcMode
     */
    public int getAdr160ProcMode() {
        return adr160ProcMode__;
    }
    /**
     * <p>adr160ProcMode をセットします。
     * @param adr160ProcMode adr160ProcMode
     */
    public void setAdr160ProcMode(int adr160ProcMode) {
        adr160ProcMode__ = adr160ProcMode;
    }
    /**
     * <p>adr160exportPower を取得します。
     * @return adr160exportPower
     */
    public int getAdr160exportPower() {
        return adr160exportPower__;
    }
    /**
     * <p>adr160exportPower をセットします。
     * @param adr160exportPower adr160exportPower
     */
    public void setAdr160exportPower(int adr160exportPower) {
        adr160exportPower__ = adr160exportPower;
    }
    /**
     * <p>adr160dataExist を取得します。
     * @return adr160dataExist
     */
    public int getAdr160dataExist() {
        return adr160dataExist__;
    }
    /**
     * <p>adr160dataExist をセットします。
     * @param adr160dataExist adr160dataExist
     */
    public void setAdr160dataExist(int adr160dataExist) {
        adr160dataExist__ = adr160dataExist;
    }
    /**
     * <p>adr160labelExist を取得します。
     * @return adr160labelExist
     */
    public int getAdr160labelExist() {
        return adr160labelExist__;
    }
    /**
     * <p>adr160labelExist をセットします。
     * @param adr160labelExist adr160labelExist
     */
    public void setAdr160labelExist(int adr160labelExist) {
        adr160labelExist__ = adr160labelExist;
    }
    /**
     * <p>adr160MailAddress1 を取得します。
     * @return adr160MailAddress1
     */
    public String getAdr160MailAddress1() {
        return adr160MailAddress1__;
    }
    /**
     * <p>adr160MailAddress1 をセットします。
     * @param adr160MailAddress1 adr160MailAddress1
     */
    public void setAdr160MailAddress1(String adr160MailAddress1) {
        adr160MailAddress1__ = adr160MailAddress1;
    }
    /**
     * <p>adr160MailComment1 を取得します。
     * @return adr160MailComment1
     */
    public String getAdr160MailComment1() {
        return adr160MailComment1__;
    }
    /**
     * <p>adr160MailComment1 をセットします。
     * @param adr160MailComment1 adr160MailComment1
     */
    public void setAdr160MailComment1(String adr160MailComment1) {
        adr160MailComment1__ = adr160MailComment1;
    }
    /**
     * <p>adr160MailAddress2 を取得します。
     * @return adr160MailAddress2
     */
    public String getAdr160MailAddress2() {
        return adr160MailAddress2__;
    }
    /**
     * <p>adr160MailAddress2 をセットします。
     * @param adr160MailAddress2 adr160MailAddress2
     */
    public void setAdr160MailAddress2(String adr160MailAddress2) {
        adr160MailAddress2__ = adr160MailAddress2;
    }
    /**
     * <p>adr160MailComment2 を取得します。
     * @return adr160MailComment2
     */
    public String getAdr160MailComment2() {
        return adr160MailComment2__;
    }
    /**
     * <p>adr160MailComment2 をセットします。
     * @param adr160MailComment2 adr160MailComment2
     */
    public void setAdr160MailComment2(String adr160MailComment2) {
        adr160MailComment2__ = adr160MailComment2;
    }
    /**
     * <p>adr160MailAddress3 を取得します。
     * @return adr160MailAddress3
     */
    public String getAdr160MailAddress3() {
        return adr160MailAddress3__;
    }
    /**
     * <p>adr160MailAddress3 をセットします。
     * @param adr160MailAddress3 adr160MailAddress3
     */
    public void setAdr160MailAddress3(String adr160MailAddress3) {
        adr160MailAddress3__ = adr160MailAddress3;
    }
    /**
     * <p>adr160MailComment3 を取得します。
     * @return adr160MailComment3
     */
    public String getAdr160MailComment3() {
        return adr160MailComment3__;
    }
    /**
     * <p>adr160MailComment3 をセットします。
     * @param adr160MailComment3 adr160MailComment3
     */
    public void setAdr160MailComment3(String adr160MailComment3) {
        adr160MailComment3__ = adr160MailComment3;
    }
    /**
     * <p>adr160Tel1 を取得します。
     * @return adr160Tel1
     */
    public String getAdr160Tel1() {
        return adr160Tel1__;
    }
    /**
     * <p>adr160Tel1 をセットします。
     * @param adr160Tel1 adr160Tel1
     */
    public void setAdr160Tel1(String adr160Tel1) {
        adr160Tel1__ = adr160Tel1;
    }
    /**
     * <p>adr160TelNaisen1 を取得します。
     * @return adr160TelNaisen1
     */
    public String getAdr160TelNaisen1() {
        return adr160TelNaisen1__;
    }
    /**
     * <p>adr160TelNaisen1 をセットします。
     * @param adr160TelNaisen1 adr160TelNaisen1
     */
    public void setAdr160TelNaisen1(String adr160TelNaisen1) {
        adr160TelNaisen1__ = adr160TelNaisen1;
    }
    /**
     * <p>adr160TelComment1 を取得します。
     * @return adr160TelComment1
     */
    public String getAdr160TelComment1() {
        return adr160TelComment1__;
    }
    /**
     * <p>adr160TelComment1 をセットします。
     * @param adr160TelComment1 adr160TelComment1
     */
    public void setAdr160TelComment1(String adr160TelComment1) {
        adr160TelComment1__ = adr160TelComment1;
    }
    /**
     * <p>adr160Tel2 を取得します。
     * @return adr160Tel2
     */
    public String getAdr160Tel2() {
        return adr160Tel2__;
    }
    /**
     * <p>adr160Tel2 をセットします。
     * @param adr160Tel2 adr160Tel2
     */
    public void setAdr160Tel2(String adr160Tel2) {
        adr160Tel2__ = adr160Tel2;
    }
    /**
     * <p>adr160TelNaisen2 を取得します。
     * @return adr160TelNaisen2
     */
    public String getAdr160TelNaisen2() {
        return adr160TelNaisen2__;
    }
    /**
     * <p>adr160TelNaisen2 をセットします。
     * @param adr160TelNaisen2 adr160TelNaisen2
     */
    public void setAdr160TelNaisen2(String adr160TelNaisen2) {
        adr160TelNaisen2__ = adr160TelNaisen2;
    }
    /**
     * <p>adr160TelComment2 を取得します。
     * @return adr160TelComment2
     */
    public String getAdr160TelComment2() {
        return adr160TelComment2__;
    }
    /**
     * <p>adr160TelComment2 をセットします。
     * @param adr160TelComment2 adr160TelComment2
     */
    public void setAdr160TelComment2(String adr160TelComment2) {
        adr160TelComment2__ = adr160TelComment2;
    }
    /**
     * <p>adr160Tel3 を取得します。
     * @return adr160Tel3
     */
    public String getAdr160Tel3() {
        return adr160Tel3__;
    }
    /**
     * <p>adr160Tel3 をセットします。
     * @param adr160Tel3 adr160Tel3
     */
    public void setAdr160Tel3(String adr160Tel3) {
        adr160Tel3__ = adr160Tel3;
    }
    /**
     * <p>adr160TelNaisen3 を取得します。
     * @return adr160TelNaisen3
     */
    public String getAdr160TelNaisen3() {
        return adr160TelNaisen3__;
    }
    /**
     * <p>adr160TelNaisen3 をセットします。
     * @param adr160TelNaisen3 adr160TelNaisen3
     */
    public void setAdr160TelNaisen3(String adr160TelNaisen3) {
        adr160TelNaisen3__ = adr160TelNaisen3;
    }
    /**
     * <p>adr160TelComment3 を取得します。
     * @return adr160TelComment3
     */
    public String getAdr160TelComment3() {
        return adr160TelComment3__;
    }
    /**
     * <p>adr160TelComment3 をセットします。
     * @param adr160TelComment3 adr160TelComment3
     */
    public void setAdr160TelComment3(String adr160TelComment3) {
        adr160TelComment3__ = adr160TelComment3;
    }
    /**
     * <p>adr160Fax1 を取得します。
     * @return adr160Fax1
     */
    public String getAdr160Fax1() {
        return adr160Fax1__;
    }
    /**
     * <p>adr160Fax1 をセットします。
     * @param adr160Fax1 adr160Fax1
     */
    public void setAdr160Fax1(String adr160Fax1) {
        adr160Fax1__ = adr160Fax1;
    }
    /**
     * <p>adr160FaxComment1 を取得します。
     * @return adr160FaxComment1
     */
    public String getAdr160FaxComment1() {
        return adr160FaxComment1__;
    }
    /**
     * <p>adr160FaxComment1 をセットします。
     * @param adr160FaxComment1 adr160FaxComment1
     */
    public void setAdr160FaxComment1(String adr160FaxComment1) {
        adr160FaxComment1__ = adr160FaxComment1;
    }
    /**
     * <p>adr160Fax2 を取得します。
     * @return adr160Fax2
     */
    public String getAdr160Fax2() {
        return adr160Fax2__;
    }
    /**
     * <p>adr160Fax2 をセットします。
     * @param adr160Fax2 adr160Fax2
     */
    public void setAdr160Fax2(String adr160Fax2) {
        adr160Fax2__ = adr160Fax2;
    }
    /**
     * <p>adr160FaxComment2 を取得します。
     * @return adr160FaxComment2
     */
    public String getAdr160FaxComment2() {
        return adr160FaxComment2__;
    }
    /**
     * <p>adr160FaxComment2 をセットします。
     * @param adr160FaxComment2 adr160FaxComment2
     */
    public void setAdr160FaxComment2(String adr160FaxComment2) {
        adr160FaxComment2__ = adr160FaxComment2;
    }
    /**
     * <p>adr160Fax3 を取得します。
     * @return adr160Fax3
     */
    public String getAdr160Fax3() {
        return adr160Fax3__;
    }
    /**
     * <p>adr160Fax3 をセットします。
     * @param adr160Fax3 adr160Fax3
     */
    public void setAdr160Fax3(String adr160Fax3) {
        adr160Fax3__ = adr160Fax3;
    }
    /**
     * <p>adr160FaxComment3 を取得します。
     * @return adr160FaxComment3
     */
    public String getAdr160FaxComment3() {
        return adr160FaxComment3__;
    }
    /**
     * <p>adr160FaxComment3 をセットします。
     * @param adr160FaxComment3 adr160FaxComment3
     */
    public void setAdr160FaxComment3(String adr160FaxComment3) {
        adr160FaxComment3__ = adr160FaxComment3;
    }
    /**
     * <p>adr160PostNo を取得します。
     * @return adr160PostNo
     */
    public String getAdr160PostNo() {
        return adr160PostNo__;
    }
    /**
     * <p>adr160PostNo をセットします。
     * @param adr160PostNo adr160PostNo
     */
    public void setAdr160PostNo(String adr160PostNo) {
        adr160PostNo__ = adr160PostNo;
    }
    /**
     * <p>adr160TdfName を取得します。
     * @return adr160TdfName
     */
    public String getAdr160TdfName() {
        return adr160TdfName__;
    }
    /**
     * <p>adr160TdfName をセットします。
     * @param adr160TdfName adr160TdfName
     */
    public void setAdr160TdfName(String adr160TdfName) {
        adr160TdfName__ = adr160TdfName;
    }
    /**
     * <p>adr160Address1 を取得します。
     * @return adr160Address1
     */
    public String getAdr160Address1() {
        return adr160Address1__;
    }
    /**
     * <p>adr160Address1 をセットします。
     * @param adr160Address1 adr160Address1
     */
    public void setAdr160Address1(String adr160Address1) {
        adr160Address1__ = adr160Address1;
    }
    /**
     * <p>adr160Address2 を取得します。
     * @return adr160Address2
     */
    public String getAdr160Address2() {
        return adr160Address2__;
    }
    /**
     * <p>adr160Address2 をセットします。
     * @param adr160Address2 adr160Address2
     */
    public void setAdr160Address2(String adr160Address2) {
        adr160Address2__ = adr160Address2;
    }
    /**
     * <p>adr160Biko を取得します。
     * @return adr160Biko
     */
    public String getAdr160Biko() {
        return adr160Biko__;
    }
    /**
     * <p>adr160Biko をセットします。
     * @param adr160Biko adr160Biko
     */
    public void setAdr160Biko(String adr160Biko) {
        adr160Biko__ = adr160Biko;
    }
    /**
     * <p>adr160TantoUserName を取得します。
     * @return adr160TantoUserName
     */
    public ArrayList<UsrLabelValueBean> getAdr160TantoUserName() {
        return adr160TantoUserName__;
    }
    /**
     * <p>adr160TantoUserName をセットします。
     * @param adr160TantoUserName adr160TantoUserName
     */
    public void setAdr160TantoUserName(ArrayList<UsrLabelValueBean> adr160TantoUserName) {
        adr160TantoUserName__ = adr160TantoUserName;
    }

    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     */
    public void setHiddenParam160(Cmn999Form msgForm) {

        msgForm.addHiddenParam("sortKey", getSortKey());
        msgForm.addHiddenParam("orderKey", getOrderKey());
        msgForm.addHiddenParam("adr160pageNum1", getAdr160pageNum1());
        msgForm.addHiddenParam("adr160pageNum2", getAdr160pageNum2());
        msgForm.addHiddenParam("adr160ProcMode", adr160ProcMode__);
        msgForm.addHiddenParam("adr160EditSid", adr160EditSid__);
    }
}