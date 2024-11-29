package jp.groupsession.v2.adr.adr240;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.Adr010ParamModel;
import jp.groupsession.v2.adr.adr240.model.Adr240Model;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 会社選択画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr240ParamModel extends Adr010ParamModel {

    /** 選択行 */
    private String adr240Index__ = "-1";
    /** 選択文字 */
    private String adr240Str__ = "-1";
    /** 親画面ID */
    private String adr240parentPageId__ = null;

    /** 行リスト **/
    private List<LabelValueBean> adr240IndexList__ = null;
    /** 文字リスト **/
    private List<LabelValueBean> adr240StrList__ = null;
    /** 会社リスト **/
    private List<Adr240Model> adr240CompanyList__ = null;


    /** 会社SID */
    private String adr240CompanySid__ = null;
    /** 会社コード */
    private String adr240CompanyCode__ = null;
    /** 会社拠点SID */
    private String adr240CompanyBaseSid__ = null;
    /** 会社名 */
    private String adr240CompanyName__ = null;

    /** 選択された会社 */
    private String adr240selectCompany__ = null;

    /** ページ */
    private int adr240page__ = 0;
    /** ページ(画面上部) */
    private int adr240pageTop__ = 0;
    /** ページ(画面下部) */
    private int adr240pageBottom__ = 0;

    /** モード 0:会社・担当者選択 1：会社のみ選択 */
    private int adr240mode__ = 0;
    /** 選択モード 0:通常 1:クリックで選択 */
    private int adr240selMode__ = 0;

    /** 処理モード 0:通常 1：親画面リロードなし */
    private int adr240PrsMode__ = 0;
    /** 親画面行番号 */
    private String adr240RowNumber__ = "";

    /** プロジェクト選択フラグ */
    private int adr240ProAddFlg__ = 0;
    /** プロジェクト選択エラーフラグ */
    private int adr240ProAddErrFlg__ = 0;


    /** 企業コード */
    private String adr240code__ = null;
    /** 会社名 */
    private String adr240coName__ = null;
    /** 会社名カナ */
    private String adr240coNameKn__ = null;
    /** 支店・営業所名 */
    private String adr240coBaseName__ = null;
    /** 業種 */
    private int adr240atiSid__ = 0;
    /** 都道府県 */
    private int adr240tdfk__ = 0;
    /** 備考 */
    private String adr240biko__ = null;

    /** 企業コード(検索条件保持用) */
    private String adr240svCode__ = null;
    /** 会社名(検索条件保持用) */
    private String adr240svCoName__ = null;
    /** 会社名カナ(検索条件保持用) */
    private String adr240svCoNameKn__ = null;
    /** 支店・営業所名(検索条件保持用) */
    private String adr240svCoBaseName__ = null;
    /** 業種(検索条件保持用) */
    private int adr240svAtiSid__ = 0;
    /** 都道府県(検索条件保持用) */
    private int adr240svTdfk__ = 0;
    /** 備考(検索条件保持用) */
    private String adr240svBiko__ = null;

    /** ソートキー */
    private int adr240SortKey__ = GSConstAddress.COMPANY_SORT_CODE;
    /** オーダーキー */
    private int adr240OrderKey__ = GSConst.ORDER_KEY_ASC;

    /** 検索モード */
    private int adr240SearchMode__ = GSConstAddress.SEARCH_COMPANY_MODE_50;
    /** 検索フラグ */
    private int adr240searchFlg__ = 0;

//    /** ラベル選択 */
//    private String[] adr240searchLabel__ = null;
//    /** 選択ラベル一覧 */
//    private List<AdrLabelModel> selectLabelList__ = null;
//    /** 選択中ラベル一覧 */
//    private List<AdrLabelModel> selectedLabelList__ = null;

    /**
     * @return adr240ProAddFlg
     */
    public int getAdr240ProAddFlg() {
        return adr240ProAddFlg__;
    }
    /**
     * @param adr240ProAddFlg 設定する adr240ProAddFlg
     */
    public void setAdr240ProAddFlg(int adr240ProAddFlg) {
       adr240ProAddFlg__ = adr240ProAddFlg;
    }
    /**
     * @return adr240mode
     */
    public int getAdr240mode() {
        return adr240mode__;
    }
    /**
     * @param adr240mode 設定する adr240mode
     */
    public void setAdr240mode(int adr240mode) {
        adr240mode__ = adr240mode;
    }
    /**
     * @return adr240page
     */
    public int getAdr240page() {
        return adr240page__;
    }
    /**
     * @param adr240page 設定する adr240page
     */
    public void setAdr240page(int adr240page) {
        this.adr240page__ = adr240page;
    }
    /**
     * @return adr240pageBottom
     */
    public int getAdr240pageBottom() {
        return adr240pageBottom__;
    }
    /**
     * @param adr240pageBottom 設定する adr240pageBottom
     */
    public void setAdr240pageBottom(int adr240pageBottom) {
        this.adr240pageBottom__ = adr240pageBottom;
    }
    /**
     * @return adr240pageTop
     */
    public int getAdr240pageTop() {
        return adr240pageTop__;
    }
    /**
     * @param adr240pageTop 設定する adr240pageTop
     */
    public void setAdr240pageTop(int adr240pageTop) {
        this.adr240pageTop__ = adr240pageTop;
    }
    /**
     * @return adr240CompanyList
     */
    public List<Adr240Model> getAdr240CompanyList() {
        return adr240CompanyList__;
    }
    /**
     * @param adr240CompanyList 設定する adr240CompanyList
     */
    public void setAdr240CompanyList(List<Adr240Model> adr240CompanyList) {
        this.adr240CompanyList__ = adr240CompanyList;
    }
    /**
     * @return adr240Index
     */
    public String getAdr240Index() {
        return adr240Index__;
    }
    /**
     * @param adr240Index 設定する adr240Index
     */
    public void setAdr240Index(String adr240Index) {
        this.adr240Index__ = adr240Index;
    }
    /**
     * @return adr240IndexList
     */
    public List<LabelValueBean> getAdr240IndexList() {
        return adr240IndexList__;
    }
    /**
     * @param adr240IndexList 設定する adr240IndexList
     */
    public void setAdr240IndexList(List<LabelValueBean> adr240IndexList) {
        this.adr240IndexList__ = adr240IndexList;
    }
    /**
     * @return adr240StrList
     */
    public List<LabelValueBean> getAdr240StrList() {
        return adr240StrList__;
    }
    /**
     * @param adr240StrList 設定する adr240StrList
     */
    public void setAdr240StrList(List<LabelValueBean> adr240StrList) {
        this.adr240StrList__ = adr240StrList;
    }
    /**
     * @return adr240Str
     */
    public String getAdr240Str() {
        return adr240Str__;
    }
    /**
     * @param adr240Str 設定する adr240Str
     */
    public void setAdr240Str(String adr240Str) {
        this.adr240Str__ = adr240Str;
    }
    /**
     * <p>adr240parentPageId を取得します。
     * @return adr240parentPageId
     */
    public String getAdr240parentPageId() {
        return adr240parentPageId__;
    }
    /**
     * <p>adr240parentPageId をセットします。
     * @param adr240parentPageId adr240parentPageId
     */
    public void setAdr240parentPageId(String adr240parentPageId) {
        adr240parentPageId__ = adr240parentPageId;
    }
    /**
     * <p>adr240CompanyBaseSid を取得します。
     * @return adr240CompanyBaseSid
     */
    public String getAdr240CompanyBaseSid() {
        return adr240CompanyBaseSid__;
    }
    /**
     * <p>adr240CompanyBaseSid をセットします。
     * @param adr240CompanyBaseSid adr240CompanyBaseSid
     */
    public void setAdr240CompanyBaseSid(String adr240CompanyBaseSid) {
        adr240CompanyBaseSid__ = adr240CompanyBaseSid;
    }
    /**
     * <p>adr240CompanySid を取得します。
     * @return adr240CompanySid
     */
    public String getAdr240CompanySid() {
        return adr240CompanySid__;
    }
    /**
     * <p>adr240CompanySid をセットします。
     * @param adr240CompanySid adr240CompanySid
     */
    public void setAdr240CompanySid(String adr240CompanySid) {
        adr240CompanySid__ = adr240CompanySid;
    }
    /**
     * <p>adr240CompanyName を取得します。
     * @return adr240CompanyName
     */
    public String getAdr240CompanyName() {
        return adr240CompanyName__;
    }
    /**
     * <p>adr240CompanyName をセットします。
     * @param adr240CompanyName adr240CompanyName
     */
    public void setAdr240CompanyName(String adr240CompanyName) {
        adr240CompanyName__ = adr240CompanyName;
    }
    /**
     * <p>adr240selectCompany を取得します。
     * @return adr240selectCompany
     */
    public String getAdr240selectCompany() {
        return adr240selectCompany__;
    }
    /**
     * <p>adr240selectCompany をセットします。
     * @param adr240selectCompany adr240selectCompany
     */
    public void setAdr240selectCompany(String adr240selectCompany) {
        adr240selectCompany__ = adr240selectCompany;
    }
    /**
     * @return adr240ProAddErrFlg
     */
    public int getAdr240ProAddErrFlg() {
        return adr240ProAddErrFlg__;
    }
    /**
     * @param adr240ProAddErrFlg 設定する adr240ProAddErrFlg
     */
    public void setAdr240ProAddErrFlg(int adr240ProAddErrFlg) {
        adr240ProAddErrFlg__ = adr240ProAddErrFlg;
    }
    /**
     * <p>adr240PrsMode を取得します。
     * @return adr240PrsMode
     */
    public int getAdr240PrsMode() {
        return adr240PrsMode__;
    }
    /**
     * <p>adr240PrsMode をセットします。
     * @param adr240PrsMode adr240PrsMode
     */
    public void setAdr240PrsMode(int adr240PrsMode) {
        adr240PrsMode__ = adr240PrsMode;
    }
    /**
     * <p>adr240RowNumber を取得します。
     * @return adr240RowNumber
     */
    public String getAdr240RowNumber() {
        return adr240RowNumber__;
    }
    /**
     * <p>adr240RowNumber をセットします。
     * @param adr240RowNumber adr240RowNumber
     */
    public void setAdr240RowNumber(String adr240RowNumber) {
        adr240RowNumber__ = adr240RowNumber;
    }
    /**
     * <p>adr240CompanyCode を取得します。
     * @return adr240CompanyCode
     */
    public String getAdr240CompanyCode() {
        return adr240CompanyCode__;
    }
    /**
     * <p>adr240CompanyCode をセットします。
     * @param adr240CompanyCode adr240CompanyCode
     */
    public void setAdr240CompanyCode(String adr240CompanyCode) {
        adr240CompanyCode__ = adr240CompanyCode;
    }
    /**
     * <p>adr240atiSid を取得します。
     * @return adr240atiSid
     */
    public int getAdr240atiSid() {
        return adr240atiSid__;
    }
    /**
     * <p>adr240atiSid をセットします。
     * @param adr240atiSid adr240atiSid
     */
    public void setAdr240atiSid(int adr240atiSid) {
        adr240atiSid__ = adr240atiSid;
    }
    /**
     * <p>adr240code を取得します。
     * @return adr240code
     */
    public String getAdr240code() {
        return adr240code__;
    }
    /**
     * <p>adr240code をセットします。
     * @param adr240code adr240code
     */
    public void setAdr240code(String adr240code) {
        adr240code__ = adr240code;
    }
    /**
     * <p>adr240coName を取得します。
     * @return adr240coName
     */
    public String getAdr240coName() {
        return adr240coName__;
    }
    /**
     * <p>adr240coName をセットします。
     * @param adr240coName adr240coName
     */
    public void setAdr240coName(String adr240coName) {
        adr240coName__ = adr240coName;
    }
    /**
     * <p>adr240coNameKn を取得します。
     * @return adr240coNameKn
     */
    public String getAdr240coNameKn() {
        return adr240coNameKn__;
    }
    /**
     * <p>adr240coNameKn をセットします。
     * @param adr240coNameKn adr240coNameKn
     */
    public void setAdr240coNameKn(String adr240coNameKn) {
        adr240coNameKn__ = adr240coNameKn;
    }
    /**
     * <p>adr240coBaseName を取得します。
     * @return adr240coBaseName
     */
    public String getAdr240coBaseName() {
        return adr240coBaseName__;
    }
    /**
     * <p>adr240coBaseName をセットします。
     * @param adr240coBaseName adr240coBaseName
     */
    public void setAdr240coBaseName(String adr240coBaseName) {
        adr240coBaseName__ = adr240coBaseName;
    }
    /**
     * <p>adr240tdfk を取得します。
     * @return adr240tdfk
     */
    public int getAdr240tdfk() {
        return adr240tdfk__;
    }
    /**
     * <p>adr240tdfk をセットします。
     * @param adr240tdfk adr240tdfk
     */
    public void setAdr240tdfk(int adr240tdfk) {
        adr240tdfk__ = adr240tdfk;
    }
    /**
     * <p>adr240biko を取得します。
     * @return adr240biko
     */
    public String getAdr240biko() {
        return adr240biko__;
    }
    /**
     * <p>adr240biko をセットします。
     * @param adr240biko adr240biko
     */
    public void setAdr240biko(String adr240biko) {
        adr240biko__ = adr240biko;
    }
    /**
     * <p>adr240svCode を取得します。
     * @return adr240svCode
     */
    public String getAdr240svCode() {
        return adr240svCode__;
    }
    /**
     * <p>adr240svCode をセットします。
     * @param adr240svCode adr240svCode
     */
    public void setAdr240svCode(String adr240svCode) {
        adr240svCode__ = adr240svCode;
    }
    /**
     * <p>adr240svCoName を取得します。
     * @return adr240svCoName
     */
    public String getAdr240svCoName() {
        return adr240svCoName__;
    }
    /**
     * <p>adr240svCoName をセットします。
     * @param adr240svCoName adr240svCoName
     */
    public void setAdr240svCoName(String adr240svCoName) {
        adr240svCoName__ = adr240svCoName;
    }
    /**
     * <p>adr240svCoNameKn を取得します。
     * @return adr240svCoNameKn
     */
    public String getAdr240svCoNameKn() {
        return adr240svCoNameKn__;
    }
    /**
     * <p>adr240svCoNameKn をセットします。
     * @param adr240svCoNameKn adr240svCoNameKn
     */
    public void setAdr240svCoNameKn(String adr240svCoNameKn) {
        adr240svCoNameKn__ = adr240svCoNameKn;
    }
    /**
     * <p>adr240svCoBaseName を取得します。
     * @return adr240svCoBaseName
     */
    public String getAdr240svCoBaseName() {
        return adr240svCoBaseName__;
    }
    /**
     * <p>adr240svCoBaseName をセットします。
     * @param adr240svCoBaseName adr240svCoBaseName
     */
    public void setAdr240svCoBaseName(String adr240svCoBaseName) {
        adr240svCoBaseName__ = adr240svCoBaseName;
    }
    /**
     * <p>adr240svAtiSid を取得します。
     * @return adr240svAtiSid
     */
    public int getAdr240svAtiSid() {
        return adr240svAtiSid__;
    }
    /**
     * <p>adr240svAtiSid をセットします。
     * @param adr240svAtiSid adr240svAtiSid
     */
    public void setAdr240svAtiSid(int adr240svAtiSid) {
        adr240svAtiSid__ = adr240svAtiSid;
    }
    /**
     * <p>adr240svTdfk を取得します。
     * @return adr240svTdfk
     */
    public int getAdr240svTdfk() {
        return adr240svTdfk__;
    }
    /**
     * <p>adr240svTdfk をセットします。
     * @param adr240svTdfk adr240svTdfk
     */
    public void setAdr240svTdfk(int adr240svTdfk) {
        adr240svTdfk__ = adr240svTdfk;
    }
    /**
     * <p>adr240svBiko を取得します。
     * @return adr240svBiko
     */
    public String getAdr240svBiko() {
        return adr240svBiko__;
    }
    /**
     * <p>adr240svBiko をセットします。
     * @param adr240svBiko adr240svBiko
     */
    public void setAdr240svBiko(String adr240svBiko) {
        adr240svBiko__ = adr240svBiko;
    }
    /**
     * <p>adr240SearchMode を取得します。
     * @return adr240SearchMode
     */
    public int getAdr240SearchMode() {
        return adr240SearchMode__;
    }
    /**
     * <p>adr240SearchMode をセットします。
     * @param adr240SearchMode adr240SearchMode
     */
    public void setAdr240SearchMode(int adr240SearchMode) {
        adr240SearchMode__ = adr240SearchMode;
    }
    /**
     * <p>adr240searchFlg を取得します。
     * @return adr240searchFlg
     */
    public int getAdr240searchFlg() {
        return adr240searchFlg__;
    }
    /**
     * <p>adr240searchFlg をセットします。
     * @param adr240searchFlg adr240searchFlg
     */
    public void setAdr240searchFlg(int adr240searchFlg) {
        adr240searchFlg__ = adr240searchFlg;
    }
    /**
     * <p>adr240SortKey を取得します。
     * @return adr240SortKey
     */
    public int getAdr240SortKey() {
        return adr240SortKey__;
    }
    /**
     * <p>adr240SortKey をセットします。
     * @param adr240SortKey adr240SortKey
     */
    public void setAdr240SortKey(int adr240SortKey) {
        adr240SortKey__ = adr240SortKey;
    }
    /**
     * <p>adr240OrderKey を取得します。
     * @return adr240OrderKey
     */
    public int getAdr240OrderKey() {
        return adr240OrderKey__;
    }
    /**
     * <p>adr240OrderKey をセットします。
     * @param adr240OrderKey adr240OrderKey
     */
    public void setAdr240OrderKey(int adr240OrderKey) {
        adr240OrderKey__ = adr240OrderKey;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();

        //企業コード
        AdrValidateUtil.validateTextField(errors, adr240code__, "adr240code",
                gsMsg.getMessage(req, "address.7"), GSConstAddress.MAX_LENGTH_COMPANY_CODE, false);

        //会社名
        AdrValidateUtil.validateTextField(errors, adr240coName__, "adr240coName",
                                        gsMsg.getMessage(req, "cmn.company.name"),
                                        GSConstAddress.MAX_LENGTH_COMPANY_NAME, false);
        //会社名(カナ)
        AdrValidateUtil.validateTextFieldKana(errors,
                                        adr240coNameKn__, "adr240coNameKn",
                                        gsMsg.getMessage(req, "cmn.company.name")
                                        + "(" + gsMsg.getMessage(req, "cmn.kana") + ")",
                                        GSConstAddress.MAX_LENGTH_COMPANY_NAME_KN,
                                        false);
        //支店・営業所名
        AdrValidateUtil.validateTextField(errors, adr240coBaseName__, "adr240coBaseName",
                gsMsg.getMessage(req, "address.10"), GSConstAddress.MAX_LENGTH_COBASE_NAME, false);
        //備考
        AdrValidateUtil.validateTextField(errors, adr240biko__, "adr240biko__",
                gsMsg.getMessage(req, "cmn.memo"), GSConstAddress.MAX_LENGTH_ADR_BIKO, false);

        return errors;
    }
    /**
     * <p>adr240selMode を取得します。
     * @return adr240selMode
     */
    public int getAdr240selMode() {
        return adr240selMode__;
    }
    /**
     * <p>adr240selMode をセットします。
     * @param adr240selMode adr240selMode
     */
    public void setAdr240selMode(int adr240selMode) {
        adr240selMode__ = adr240selMode;
    }

}