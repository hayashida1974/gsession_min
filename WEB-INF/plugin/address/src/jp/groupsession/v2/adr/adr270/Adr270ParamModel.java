package jp.groupsession.v2.adr.adr270;

import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr270ParamModel extends AbstractParamModel {

    /** アドレス帳SID */
    private int adrSid__ = 0;

    /** 氏名 姓 */
    private String adr270unameSei__ = null;
    /** 氏名 名 */
    private String adr270unameMei__ = null;
    /** 氏名カナ 姓 */
    private String adr270unameSeiKn__ = null;
    /** 氏名カナ 名 */
    private String adr270unameMeiKn__ = null;
    /** 会社 */
    private String adr270selectCompany__ = null;
    /** 会社拠点 */
    private String adr270selectCompanyBase__ = null;
    /** 所属 */
    private String adr270syozoku__ = null;
    /** 役職 */
    private int adr270position__ = 0;
    /** 役職名称 */
    private String adr270positionName__ = null;
    /** メールアドレス1 */
    private String adr270mail1__ = null;
    /** メールアドレス1 コメント */
    private String adr270mail1Comment__ = null;
    /** メールアドレス2 */
    private String adr270mail2__ = null;
    /** メールアドレス2 コメント */
    private String adr270mail2Comment__ = null;
    /** メールアドレス3 */
    private String adr270mail3__ = null;
    /** メールアドレス3 コメント */
    private String adr270mail3Comment__ = null;
    /** 電話番号1 */
    private String adr270tel1__ = null;
    /** 電話番号1 内線 */
    private String adr270tel1Nai__ = null;
    /** 電話番号1 コメント */
    private String adr270tel1Comment__ = null;
    /** 電話番号2 */
    private String adr270tel2__ = null;
    /** 電話番号2 内線 */
    private String adr270tel2Nai__ = null;
    /** 電話番号2 コメント */
    private String adr270tel2Comment__ = null;
    /** 電話番号3 */
    private String adr270tel3__ = null;
    /** 電話番号3 内線 */
    private String adr270tel3Nai__ = null;
    /** 電話番号3 コメント */
    private String adr270tel3Comment__ = null;
    /** ＦＡＸ1 */
    private String adr270fax1__ = null;
    /** ＦＡＸ1 コメント */
    private String adr270fax1Comment__ = null;
    /** ＦＡＸ2 */
    private String adr270fax2__ = null;
    /** ＦＡＸ2 コメント */
    private String adr270fax2Comment__ = null;
    /** ＦＡＸ3 */
    private String adr270fax3__ = null;
    /** ＦＡＸ3 コメント */
    private String adr270fax3Comment__ = null;
    /** 郵便番号上3桁 */
    private String adr270postno1__ = null;
    /** 郵便番号下4桁 */
    private String adr270postno2__ = null;
    /** 都道府県 */
    private int adr270tdfk__ = 0;
    /** 都道府県名称 */
    private String adr270tdfkName__ = null;
    /** 住所１ */
    private String adr270address1__ = null;
    /** 住所２ */
    private String adr270address2__ = null;
    /** 備考 */
    private String adr270biko__ = null;
    /** 担当者 */
    private String[] adr270tantoList__ = null;
    /** ラベル */
    private String[] adr270label__ = null;

    /** 企業コード */
    private String adr270companyCode__ = null;
    /** 会社名 */
    private String adr270companyName__ = null;
    /** 支店・営業所名 */
    private String adr270companyBaseName__ = null;
    /** 担当者(選択用) */
    private String[] adr270selectTantoList__ = null;
    /** 担当者(未選択 選択用) */
    private String[] adr270NoSelectTantoList__ = null;
    /** 担当者コンボ */
    private List<UsrLabelValueBean> selectTantoCombo__ = null;
    /** 担当者(未選択)コンボ */
    private List<UsrLabelValueBean> noSelectTantoCombo__ = null;

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int adr270searchUse__ = GSConst.PLUGIN_USE;

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
     * <p>adr270address1 を取得します。
     * @return adr270address1
     */
    public String getAdr270address1() {
        return adr270address1__;
    }
    /**
     * <p>adr270address1 をセットします。
     * @param adr270address1 adr270address1
     */
    public void setAdr270address1(String adr270address1) {
        adr270address1__ = adr270address1;
    }
    /**
     * <p>adr270address2 を取得します。
     * @return adr270address2
     */
    public String getAdr270address2() {
        return adr270address2__;
    }
    /**
     * <p>adr270address2 をセットします。
     * @param adr270address2 adr270address2
     */
    public void setAdr270address2(String adr270address2) {
        adr270address2__ = adr270address2;
    }
    /**
     * <p>adr270biko を取得します。
     * @return adr270biko
     */
    public String getAdr270biko() {
        return adr270biko__;
    }
    /**
     * <p>adr270biko をセットします。
     * @param adr270biko adr270biko
     */
    public void setAdr270biko(String adr270biko) {
        adr270biko__ = adr270biko;
    }
    /**
     * <p>adr270fax1 を取得します。
     * @return adr270fax1
     */
    public String getAdr270fax1() {
        return adr270fax1__;
    }
    /**
     * <p>adr270fax1 をセットします。
     * @param adr270fax1 adr270fax1
     */
    public void setAdr270fax1(String adr270fax1) {
        adr270fax1__ = adr270fax1;
    }
    /**
     * <p>adr270fax1Comment を取得します。
     * @return adr270fax1Comment
     */
    public String getAdr270fax1Comment() {
        return adr270fax1Comment__;
    }
    /**
     * <p>adr270fax1Comment をセットします。
     * @param adr270fax1Comment adr270fax1Comment
     */
    public void setAdr270fax1Comment(String adr270fax1Comment) {
        adr270fax1Comment__ = adr270fax1Comment;
    }
    /**
     * <p>adr270fax2 を取得します。
     * @return adr270fax2
     */
    public String getAdr270fax2() {
        return adr270fax2__;
    }
    /**
     * <p>adr270fax2 をセットします。
     * @param adr270fax2 adr270fax2
     */
    public void setAdr270fax2(String adr270fax2) {
        adr270fax2__ = adr270fax2;
    }
    /**
     * <p>adr270fax2Comment を取得します。
     * @return adr270fax2Comment
     */
    public String getAdr270fax2Comment() {
        return adr270fax2Comment__;
    }
    /**
     * <p>adr270fax2Comment をセットします。
     * @param adr270fax2Comment adr270fax2Comment
     */
    public void setAdr270fax2Comment(String adr270fax2Comment) {
        adr270fax2Comment__ = adr270fax2Comment;
    }
    /**
     * <p>adr270fax3 を取得します。
     * @return adr270fax3
     */
    public String getAdr270fax3() {
        return adr270fax3__;
    }
    /**
     * <p>adr270fax3 をセットします。
     * @param adr270fax3 adr270fax3
     */
    public void setAdr270fax3(String adr270fax3) {
        adr270fax3__ = adr270fax3;
    }
    /**
     * <p>adr270fax3Comment を取得します。
     * @return adr270fax3Comment
     */
    public String getAdr270fax3Comment() {
        return adr270fax3Comment__;
    }
    /**
     * <p>adr270fax3Comment をセットします。
     * @param adr270fax3Comment adr270fax3Comment
     */
    public void setAdr270fax3Comment(String adr270fax3Comment) {
        adr270fax3Comment__ = adr270fax3Comment;
    }
    /**
     * <p>adr270mail1 を取得します。
     * @return adr270mail1
     */
    public String getAdr270mail1() {
        return adr270mail1__;
    }
    /**
     * <p>adr270mail1 をセットします。
     * @param adr270mail1 adr270mail1
     */
    public void setAdr270mail1(String adr270mail1) {
        adr270mail1__ = adr270mail1;
    }
    /**
     * <p>adr270mail1Comment を取得します。
     * @return adr270mail1Comment
     */
    public String getAdr270mail1Comment() {
        return adr270mail1Comment__;
    }
    /**
     * <p>adr270mail1Comment をセットします。
     * @param adr270mail1Comment adr270mail1Comment
     */
    public void setAdr270mail1Comment(String adr270mail1Comment) {
        adr270mail1Comment__ = adr270mail1Comment;
    }
    /**
     * <p>adr270mail2 を取得します。
     * @return adr270mail2
     */
    public String getAdr270mail2() {
        return adr270mail2__;
    }
    /**
     * <p>adr270mail2 をセットします。
     * @param adr270mail2 adr270mail2
     */
    public void setAdr270mail2(String adr270mail2) {
        adr270mail2__ = adr270mail2;
    }
    /**
     * <p>adr270mail2Comment を取得します。
     * @return adr270mail2Comment
     */
    public String getAdr270mail2Comment() {
        return adr270mail2Comment__;
    }
    /**
     * <p>adr270mail2Comment をセットします。
     * @param adr270mail2Comment adr270mail2Comment
     */
    public void setAdr270mail2Comment(String adr270mail2Comment) {
        adr270mail2Comment__ = adr270mail2Comment;
    }
    /**
     * <p>adr270mail3 を取得します。
     * @return adr270mail3
     */
    public String getAdr270mail3() {
        return adr270mail3__;
    }
    /**
     * <p>adr270mail3 をセットします。
     * @param adr270mail3 adr270mail3
     */
    public void setAdr270mail3(String adr270mail3) {
        adr270mail3__ = adr270mail3;
    }
    /**
     * <p>adr270mail3Comment を取得します。
     * @return adr270mail3Comment
     */
    public String getAdr270mail3Comment() {
        return adr270mail3Comment__;
    }
    /**
     * <p>adr270mail3Comment をセットします。
     * @param adr270mail3Comment adr270mail3Comment
     */
    public void setAdr270mail3Comment(String adr270mail3Comment) {
        adr270mail3Comment__ = adr270mail3Comment;
    }
    /**
     * <p>adr270position を取得します。
     * @return adr270position
     */
    public int getAdr270position() {
        return adr270position__;
    }
    /**
     * <p>adr270position をセットします。
     * @param adr270position adr270position
     */
    public void setAdr270position(int adr270position) {
        adr270position__ = adr270position;
    }
    /**
     * <p>adr270positionName を取得します。
     * @return adr270positionName
     */
    public String getAdr270positionName() {
        return adr270positionName__;
    }
    /**
     * <p>adr270positionName をセットします。
     * @param adr270positionName adr270positionName
     */
    public void setAdr270positionName(String adr270positionName) {
        adr270positionName__ = adr270positionName;
    }
    /**
     * <p>adr270postno1 を取得します。
     * @return adr270postno1
     */
    public String getAdr270postno1() {
        return adr270postno1__;
    }
    /**
     * <p>adr270postno1 をセットします。
     * @param adr270postno1 adr270postno1
     */
    public void setAdr270postno1(String adr270postno1) {
        adr270postno1__ = adr270postno1;
    }
    /**
     * <p>adr270postno2 を取得します。
     * @return adr270postno2
     */
    public String getAdr270postno2() {
        return adr270postno2__;
    }
    /**
     * <p>adr270postno2 をセットします。
     * @param adr270postno2 adr270postno2
     */
    public void setAdr270postno2(String adr270postno2) {
        adr270postno2__ = adr270postno2;
    }

    /**
     * <p>adr270selectCompany を取得します。
     * @return adr270selectCompany
     */
    public String getAdr270selectCompany() {
        return adr270selectCompany__;
    }
    /**
     * <p>adr270selectCompany をセットします。
     * @param adr270selectCompany adr270selectCompany
     */
    public void setAdr270selectCompany(String adr270selectCompany) {
        adr270selectCompany__ = adr270selectCompany;
    }
    /**
     * <p>adr270selectCompanyBase を取得します。
     * @return adr270selectCompanyBase
     */
    public String getAdr270selectCompanyBase() {
        return adr270selectCompanyBase__;
    }
    /**
     * <p>adr270selectCompanyBase をセットします。
     * @param adr270selectCompanyBase adr270selectCompanyBase
     */
    public void setAdr270selectCompanyBase(String adr270selectCompanyBase) {
        adr270selectCompanyBase__ = adr270selectCompanyBase;
    }
    /**
     * <p>adr270syozoku を取得します。
     * @return adr270syozoku
     */
    public String getAdr270syozoku() {
        return adr270syozoku__;
    }
    /**
     * <p>adr270syozoku をセットします。
     * @param adr270syozoku adr270syozoku
     */
    public void setAdr270syozoku(String adr270syozoku) {
        adr270syozoku__ = adr270syozoku;
    }
    /**
     * <p>adr270label を取得します。
     * @return adr270label
     */
    public String[] getAdr270label() {
        return adr270label__;
    }
    /**
     * <p>adr270label をセットします。
     * @param adr270label adr270label
     */
    public void setAdr270label(String[] adr270label) {
        adr270label__ = adr270label;
    }
    /**
     * <p>adr270tantoList を取得します。
     * @return adr270tantoList
     */
    public String[] getAdr270tantoList() {
        return adr270tantoList__;
    }
    /**
     * <p>adr270tantoList をセットします。
     * @param adr270tantoList adr270tantoList
     */
    public void setAdr270tantoList(String[] adr270tantoList) {
        adr270tantoList__ = adr270tantoList;
    }
    /**
     * <p>adr270tel1 を取得します。
     * @return adr270tel1
     */
    public String getAdr270tel1() {
        return adr270tel1__;
    }
    /**
     * <p>adr270tel1 をセットします。
     * @param adr270tel1 adr270tel1
     */
    public void setAdr270tel1(String adr270tel1) {
        adr270tel1__ = adr270tel1;
    }
    /**
     * <p>adr270tel1Comment を取得します。
     * @return adr270tel1Comment
     */
    public String getAdr270tel1Comment() {
        return adr270tel1Comment__;
    }
    /**
     * <p>adr270tel1Comment をセットします。
     * @param adr270tel1Comment adr270tel1Comment
     */
    public void setAdr270tel1Comment(String adr270tel1Comment) {
        adr270tel1Comment__ = adr270tel1Comment;
    }
    /**
     * <p>adr270tel1Nai を取得します。
     * @return adr270tel1Nai
     */
    public String getAdr270tel1Nai() {
        return adr270tel1Nai__;
    }
    /**
     * <p>adr270tel1Nai をセットします。
     * @param adr270tel1Nai adr270tel1Nai
     */
    public void setAdr270tel1Nai(String adr270tel1Nai) {
        adr270tel1Nai__ = adr270tel1Nai;
    }
    /**
     * <p>adr270tel2 を取得します。
     * @return adr270tel2
     */
    public String getAdr270tel2() {
        return adr270tel2__;
    }
    /**
     * <p>adr270tel2 をセットします。
     * @param adr270tel2 adr270tel2
     */
    public void setAdr270tel2(String adr270tel2) {
        adr270tel2__ = adr270tel2;
    }
    /**
     * <p>adr270tel2Comment を取得します。
     * @return adr270tel2Comment
     */
    public String getAdr270tel2Comment() {
        return adr270tel2Comment__;
    }
    /**
     * <p>adr270tel2Comment をセットします。
     * @param adr270tel2Comment adr270tel2Comment
     */
    public void setAdr270tel2Comment(String adr270tel2Comment) {
        adr270tel2Comment__ = adr270tel2Comment;
    }
    /**
     * <p>adr270tel2Nai を取得します。
     * @return adr270tel2Nai
     */
    public String getAdr270tel2Nai() {
        return adr270tel2Nai__;
    }
    /**
     * <p>adr270tel2Nai をセットします。
     * @param adr270tel2Nai adr270tel2Nai
     */
    public void setAdr270tel2Nai(String adr270tel2Nai) {
        adr270tel2Nai__ = adr270tel2Nai;
    }
    /**
     * <p>adr270tel3 を取得します。
     * @return adr270tel3
     */
    public String getAdr270tel3() {
        return adr270tel3__;
    }
    /**
     * <p>adr270tel3 をセットします。
     * @param adr270tel3 adr270tel3
     */
    public void setAdr270tel3(String adr270tel3) {
        adr270tel3__ = adr270tel3;
    }
    /**
     * <p>adr270tel3Comment を取得します。
     * @return adr270tel3Comment
     */
    public String getAdr270tel3Comment() {
        return adr270tel3Comment__;
    }
    /**
     * <p>adr270tel3Comment をセットします。
     * @param adr270tel3Comment adr270tel3Comment
     */
    public void setAdr270tel3Comment(String adr270tel3Comment) {
        adr270tel3Comment__ = adr270tel3Comment;
    }
    /**
     * <p>adr270tel3Nai を取得します。
     * @return adr270tel3Nai
     */
    public String getAdr270tel3Nai() {
        return adr270tel3Nai__;
    }
    /**
     * <p>adr270tel3Nai をセットします。
     * @param adr270tel3Nai adr270tel3Nai
     */
    public void setAdr270tel3Nai(String adr270tel3Nai) {
        adr270tel3Nai__ = adr270tel3Nai;
    }
    /**
     * <p>adr270tdfk を取得します。
     * @return adr270tdfk
     */
    public int getAdr270tdfk() {
        return adr270tdfk__;
    }

    /**
     * <p>adr270tdfk をセットします。
     * @param adr270tdfk adr270tdfk
     */
    public void setAdr270tdfk(int adr270tdfk) {
        adr270tdfk__ = adr270tdfk;
    }

    /**
     * <p>adr270tdfkName を取得します。
     * @return adr270tdfkName
     */
    public String getAdr270tdfkName() {
        return adr270tdfkName__;
    }
    /**
     * <p>adr270tdfkName をセットします。
     * @param adr270tdfkName adr270tdfkName
     */
    public void setAdr270tdfkName(String adr270tdfkName) {
        adr270tdfkName__ = adr270tdfkName;
    }
    /**
     * <p>adr270unameMei を取得します。
     * @return adr270unameMei
     */
    public String getAdr270unameMei() {
        return adr270unameMei__;
    }
    /**
     * <p>adr270unameMei をセットします。
     * @param adr270unameMei adr270unameMei
     */
    public void setAdr270unameMei(String adr270unameMei) {
        adr270unameMei__ = adr270unameMei;
    }
    /**
     * <p>adr270unameMeiKn を取得します。
     * @return adr270unameMeiKn
     */
    public String getAdr270unameMeiKn() {
        return adr270unameMeiKn__;
    }
    /**
     * <p>adr270unameMeiKn をセットします。
     * @param adr270unameMeiKn adr270unameMeiKn
     */
    public void setAdr270unameMeiKn(String adr270unameMeiKn) {
        adr270unameMeiKn__ = adr270unameMeiKn;
    }
    /**
     * <p>adr270unameSei を取得します。
     * @return adr270unameSei
     */
    public String getAdr270unameSei() {
        return adr270unameSei__;
    }
    /**
     * <p>adr270unameSei をセットします。
     * @param adr270unameSei adr270unameSei
     */
    public void setAdr270unameSei(String adr270unameSei) {
        adr270unameSei__ = adr270unameSei;
    }
    /**
     * <p>adr270unameSeiKn を取得します。
     * @return adr270unameSeiKn
     */
    public String getAdr270unameSeiKn() {
        return adr270unameSeiKn__;
    }
    /**
     * <p>adr270unameSeiKn をセットします。
     * @param adr270unameSeiKn adr270unameSeiKn
     */
    public void setAdr270unameSeiKn(String adr270unameSeiKn) {
        adr270unameSeiKn__ = adr270unameSeiKn;
    }

    /**
     * <p>adr270NoSelectTantoList を取得します。
     * @return adr270NoSelectTantoList
     */
    public String[] getAdr270NoSelectTantoList() {
        return adr270NoSelectTantoList__;
    }

    /**
     * <p>adr270NoSelectTantoList をセットします。
     * @param adr270NoSelectTantoList adr270NoSelectTantoList
     */
    public void setAdr270NoSelectTantoList(String[] adr270NoSelectTantoList) {
        adr270NoSelectTantoList__ = adr270NoSelectTantoList;
    }

    /**
     * <p>adr270selectTantoList を取得します。
     * @return adr270selectTantoList
     */
    public String[] getAdr270selectTantoList() {
        return adr270selectTantoList__;
    }

    /**
     * <p>adr270selectTantoList をセットします。
     * @param adr270selectTantoList adr270selectTantoList
     */
    public void setAdr270selectTantoList(String[] adr270selectTantoList) {
        adr270selectTantoList__ = adr270selectTantoList;
    }

    /**
     * <p>noSelectTantoCombo を取得します。
     * @return noSelectTantoCombo
     */
    public List<UsrLabelValueBean> getNoSelectTantoCombo() {
        return noSelectTantoCombo__;
    }

    /**
     * <p>selectTantoCombo を取得します。
     * @return selectTantoCombo
     */
    public List<UsrLabelValueBean> getSelectTantoCombo() {
        return selectTantoCombo__;
    }

    /**
     * <p>selectTantoCombo をセットします。
     * @param selectTantoCombo selectTantoCombo
     */
    public void setSelectTantoCombo(List<UsrLabelValueBean> selectTantoCombo) {
        selectTantoCombo__ = selectTantoCombo;
    }

    /**
     * <p>adr270companyName を取得します。
     * @return adr270companyName
     */
    public String getAdr270companyName() {
        return adr270companyName__;
    }

    /**
     * <p>adr270companyName をセットします。
     * @param adr270companyName adr270companyName
     */
    public void setAdr270companyName(String adr270companyName) {
        adr270companyName__ = adr270companyName;
    }

    /**
     * <p>adr270companyBaseName を取得します。
     * @return adr270companyBaseName
     */
    public String getAdr270companyBaseName() {
        return adr270companyBaseName__;
    }

    /**
     * <p>adr270companyBaseName をセットします。
     * @param adr270companyBaseName adr270companyBaseName
     */
    public void setAdr270companyBaseName(String adr270companyBaseName) {
        adr270companyBaseName__ = adr270companyBaseName;
    }

    /**
     * <p>adr270companyCode を取得します。
     * @return adr270companyCode
     */
    public String getAdr270companyCode() {
        return adr270companyCode__;
    }

    /**
     * <p>adr270companyCode をセットします。
     * @param adr270companyCode adr270companyCode
     */
    public void setAdr270companyCode(String adr270companyCode) {
        adr270companyCode__ = adr270companyCode;
    }
    /**
     * <p>adr270searchUse を取得します。
     * @return adr270searchUse
     */
    public int getAdr270searchUse() {
        return adr270searchUse__;
    }

    /**
     * <p>adr270searchUse をセットします。
     * @param adr270searchUse adr270searchUse
     */
    public void setAdr270searchUse(int adr270searchUse) {
        adr270searchUse__ = adr270searchUse;
    }

    /** 業種コンボ */
    private List<LabelValueBean> atiCmbList__ = null;
    /** 役職コンボ */
    private List<LabelValueBean> positionCmbList__ = null;
    /** 選択ラベル一覧 */
    private List<AdrLabelModel> selectLabelList__ = null;

    /**
     * <p>atiCmbList を取得します。
     * @return atiCmbList
     */
    public List<LabelValueBean> getAtiCmbList() {
        return atiCmbList__;
    }
    /**
     * <p>atiCmbList をセットします。
     * @param atiCmbList atiCmbList
     */
    public void setAtiCmbList(List<LabelValueBean> atiCmbList) {
        atiCmbList__ = atiCmbList;
    }
    /**
     * <p>positionCmbList を取得します。
     * @return positionCmbList
     */
    public List<LabelValueBean> getPositionCmbList() {
        return positionCmbList__;
    }
    /**
     * <p>positionCmbList をセットします。
     * @param positionCmbList positionCmbList
     */
    public void setPositionCmbList(List<LabelValueBean> positionCmbList) {
        positionCmbList__ = positionCmbList;
    }
    /**
     * <p>selectLabelList を取得します。
     * @return selectLabelList
     */
    public List<AdrLabelModel> getSelectLabelList() {
        return selectLabelList__;
    }
    /**
     * <p>selectLabelList をセットします。
     * @param selectLabelList selectLabelList
     */
    public void setSelectLabelList(List<AdrLabelModel> selectLabelList) {
        selectLabelList__ = selectLabelList;
    }

    /**
     * <br>[機  能] 郵便番号(画面表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 郵便番号(画面表示用)
     */
    public String getAdr270ViewPostno() {
        if (StringUtil.isNullZeroString(adr270postno1__)
        || StringUtil.isNullZeroString(adr270postno2__)) {
            return null;
        }

        return adr270postno1__ + "-" + adr270postno2__;
    }


}