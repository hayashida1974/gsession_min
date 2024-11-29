package jp.groupsession.v2.adr.adr150;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr150.model.Adr150DetailModel;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] アドレス帳 会社選択のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr150ParamModel extends NtpParamModel {
    /** 遷移元 */
    private String adr150ReturnPage__ = null;
    /** 検索フラグ */
    private int adr150searchFlg__ = 0;
    /** ページ */
    private int adr150page__ = 0;
    /** ページ(画面上部) */
    private int adr150pageTop__ = 0;
    /** ページ(画面下部) */
    private int adr150pageBottom__ = 0;

    /** 企業コード */
    private String adr150code__ = null;
    /** 会社名 */
    private String adr150coName__ = null;
    /** 会社名カナ */
    private String adr150coNameKn__ = null;
    /** 支店・営業所名 */
    private String adr150coBaseName__ = null;
    /** 業種 */
    private int adr150atiSid__ = 0;
    /** 都道府県 */
    private int adr150tdfk__ = 0;
    /** 備考 */
    private String adr150biko__ = null;

    /** 企業コード(検索条件保持用) */
    private String adr150svCode__ = null;
    /** 会社名(検索条件保持用) */
    private String adr150svCoName__ = null;
    /** 会社名カナ(検索条件保持用) */
    private String adr150svCoNameKn__ = null;
    /** 支店・営業所名(検索条件保持用) */
    private String adr150svCoBaseName__ = null;
    /** 業種(検索条件保持用) */
    private int adr150svAtiSid__ = 0;
    /** 都道府県(検索条件保持用) */
    private int adr150svTdfk__ = 0;
    /** 備考(検索条件保持用) */
    private String adr150svBiko__ = null;

    /** 会社情報(検索結果) 一覧 */
    private List<Adr150DetailModel> companyList__ = null;

    /** ソートキー */
    private int adr150SortKey__ = GSConstAddress.COMPANY_SORT_NAME;
    /** オーダーキー */
    private int adr150OrderKey__ = GSConst.ORDER_KEY_ASC;

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
        AdrValidateUtil.validateTextField(errors, adr150code__, "adr150code",
                gsMsg.getMessage(req, "address.7"), GSConstAddress.MAX_LENGTH_COMPANY_CODE, false);

        //会社名
        AdrValidateUtil.validateTextField(errors, adr150coName__, "adr150coName",
                gsMsg.getMessage(req, "cmn.company.name"),
                GSConstAddress.MAX_LENGTH_COMPANY_NAME, false);
        //会社名(カナ)
        AdrValidateUtil.validateTextFieldKana(errors,
                                        adr150coNameKn__, "adr150coNameKn",
                                        gsMsg.getMessage(req, "cmn.company.name")
                                        + "(" + gsMsg.getMessage(req, "cmn.kana") + ")",
                                        GSConstAddress.MAX_LENGTH_COMPANY_NAME_KN,
                                        false);
        //支店・営業所名
        AdrValidateUtil.validateTextField(errors, adr150coBaseName__, "adr150coBaseName",
                gsMsg.getMessage(req, "address.10"), GSConstAddress.MAX_LENGTH_COBASE_NAME, false);
        //備考
        AdrValidateUtil.validateTextAreaField(errors, adr150biko__, "adr150biko__",
                gsMsg.getMessage(req, "cmn.memo"), GSConstAddress.MAX_LENGTH_ADR_BIKO, false);


        return errors;
    }

    /**
     * <p>adr150ReturnPage を取得します。
     * @return adr150ReturnPage
     */
    public String getAdr150ReturnPage() {
        return adr150ReturnPage__;
    }

    /**
     * <p>adr150ReturnPage をセットします。
     * @param adr150ReturnPage adr150ReturnPage
     */
    public void setAdr150ReturnPage(String adr150ReturnPage) {
        adr150ReturnPage__ = adr150ReturnPage;
    }

    /**
     * <p>adr150atiSid を取得します。
     * @return adr150atiSid
     */
    public int getAdr150atiSid() {
        return adr150atiSid__;
    }

    /**
     * <p>adr150atiSid をセットします。
     * @param adr150atiSid adr150atiSid
     */
    public void setAdr150atiSid(int adr150atiSid) {
        adr150atiSid__ = adr150atiSid;
    }

    /**
     * <p>adr150biko を取得します。
     * @return adr150biko
     */
    public String getAdr150biko() {
        return adr150biko__;
    }

    /**
     * <p>adr150biko をセットします。
     * @param adr150biko adr150biko
     */
    public void setAdr150biko(String adr150biko) {
        adr150biko__ = adr150biko;
    }

    /**
     * <p>adr150coBaseName を取得します。
     * @return adr150coBaseName
     */
    public String getAdr150coBaseName() {
        return adr150coBaseName__;
    }

    /**
     * <p>adr150coBaseName をセットします。
     * @param adr150coBaseName adr150coBaseName
     */
    public void setAdr150coBaseName(String adr150coBaseName) {
        adr150coBaseName__ = adr150coBaseName;
    }

    /**
     * <p>adr150code を取得します。
     * @return adr150code
     */
    public String getAdr150code() {
        return adr150code__;
    }

    /**
     * <p>adr150code をセットします。
     * @param adr150code adr150code
     */
    public void setAdr150code(String adr150code) {
        adr150code__ = adr150code;
    }

    /**
     * <p>adr150coName を取得します。
     * @return adr150coName
     */
    public String getAdr150coName() {
        return adr150coName__;
    }

    /**
     * <p>adr150coName をセットします。
     * @param adr150coName adr150coName
     */
    public void setAdr150coName(String adr150coName) {
        adr150coName__ = adr150coName;
    }

    /**
     * <p>adr150coNameKn を取得します。
     * @return adr150coNameKn
     */
    public String getAdr150coNameKn() {
        return adr150coNameKn__;
    }

    /**
     * <p>adr150coNameKn をセットします。
     * @param adr150coNameKn adr150coNameKn
     */
    public void setAdr150coNameKn(String adr150coNameKn) {
        adr150coNameKn__ = adr150coNameKn;
    }

    /**
     * <p>adr150page を取得します。
     * @return adr150page
     */
    public int getAdr150page() {
        return adr150page__;
    }

    /**
     * <p>adr150page をセットします。
     * @param adr150page adr150page
     */
    public void setAdr150page(int adr150page) {
        adr150page__ = adr150page;
    }

    /**
     * <p>adr150pageBottom を取得します。
     * @return adr150pageBottom
     */
    public int getAdr150pageBottom() {
        return adr150pageBottom__;
    }

    /**
     * <p>adr150pageBottom をセットします。
     * @param adr150pageBottom adr150pageBottom
     */
    public void setAdr150pageBottom(int adr150pageBottom) {
        adr150pageBottom__ = adr150pageBottom;
    }

    /**
     * <p>adr150pageTop を取得します。
     * @return adr150pageTop
     */
    public int getAdr150pageTop() {
        return adr150pageTop__;
    }

    /**
     * <p>adr150pageTop をセットします。
     * @param adr150pageTop adr150pageTop
     */
    public void setAdr150pageTop(int adr150pageTop) {
        adr150pageTop__ = adr150pageTop;
    }

    /**
     * <p>adr150searchFlg を取得します。
     * @return adr150searchFlg
     */
    public int getAdr150searchFlg() {
        return adr150searchFlg__;
    }

    /**
     * <p>adr150searchFlg をセットします。
     * @param adr150searchFlg adr150searchFlg
     */
    public void setAdr150searchFlg(int adr150searchFlg) {
        adr150searchFlg__ = adr150searchFlg;
    }

    /**
     * <p>adr150svAtiSid を取得します。
     * @return adr150svAtiSid
     */
    public int getAdr150svAtiSid() {
        return adr150svAtiSid__;
    }

    /**
     * <p>adr150svAtiSid をセットします。
     * @param adr150svAtiSid adr150svAtiSid
     */
    public void setAdr150svAtiSid(int adr150svAtiSid) {
        adr150svAtiSid__ = adr150svAtiSid;
    }

    /**
     * <p>adr150svBiko を取得します。
     * @return adr150svBiko
     */
    public String getAdr150svBiko() {
        return adr150svBiko__;
    }

    /**
     * <p>adr150svBiko をセットします。
     * @param adr150svBiko adr150svBiko
     */
    public void setAdr150svBiko(String adr150svBiko) {
        adr150svBiko__ = adr150svBiko;
    }

    /**
     * <p>adr150svCoBaseName を取得します。
     * @return adr150svCoBaseName
     */
    public String getAdr150svCoBaseName() {
        return adr150svCoBaseName__;
    }

    /**
     * <p>adr150svCoBaseName をセットします。
     * @param adr150svCoBaseName adr150svCoBaseName
     */
    public void setAdr150svCoBaseName(String adr150svCoBaseName) {
        adr150svCoBaseName__ = adr150svCoBaseName;
    }

    /**
     * <p>adr150svCode を取得します。
     * @return adr150svCode
     */
    public String getAdr150svCode() {
        return adr150svCode__;
    }

    /**
     * <p>adr150svCode をセットします。
     * @param adr150svCode adr150svCode
     */
    public void setAdr150svCode(String adr150svCode) {
        adr150svCode__ = adr150svCode;
    }

    /**
     * <p>adr150svCoName を取得します。
     * @return adr150svCoName
     */
    public String getAdr150svCoName() {
        return adr150svCoName__;
    }

    /**
     * <p>adr150svCoName をセットします。
     * @param adr150svCoName adr150svCoName
     */
    public void setAdr150svCoName(String adr150svCoName) {
        adr150svCoName__ = adr150svCoName;
    }

    /**
     * <p>adr150svCoNameKn を取得します。
     * @return adr150svCoNameKn
     */
    public String getAdr150svCoNameKn() {
        return adr150svCoNameKn__;
    }

    /**
     * <p>adr150svCoNameKn をセットします。
     * @param adr150svCoNameKn adr150svCoNameKn
     */
    public void setAdr150svCoNameKn(String adr150svCoNameKn) {
        adr150svCoNameKn__ = adr150svCoNameKn;
    }

    /**
     * <p>adr150svTdfk を取得します。
     * @return adr150svTdfk
     */
    public int getAdr150svTdfk() {
        return adr150svTdfk__;
    }

    /**
     * <p>adr150svTdfk をセットします。
     * @param adr150svTdfk adr150svTdfk
     */
    public void setAdr150svTdfk(int adr150svTdfk) {
        adr150svTdfk__ = adr150svTdfk;
    }

    /**
     * <p>adr150tdfk を取得します。
     * @return adr150tdfk
     */
    public int getAdr150tdfk() {
        return adr150tdfk__;
    }

    /**
     * <p>adr150tdfk をセットします。
     * @param adr150tdfk adr150tdfk
     */
    public void setAdr150tdfk(int adr150tdfk) {
        adr150tdfk__ = adr150tdfk;
    }

    /**
     * <p>companyList を取得します。
     * @return companyList
     */
    public List<Adr150DetailModel> getCompanyList() {
        return companyList__;
    }

    /**
     * <p>companyList をセットします。
     * @param companyList companyList
     */
    public void setCompanyList(List<Adr150DetailModel> companyList) {
        companyList__ = companyList;
    }

    /**
     * <p>adr150SortKey を取得します。
     * @return adr150SortKey
     */
    public int getAdr150SortKey() {
        return adr150SortKey__;
    }

    /**
     * <p>adr150SortKey をセットします。
     * @param adr150SortKey adr150SortKey
     */
    public void setAdr150SortKey(int adr150SortKey) {
        adr150SortKey__ = adr150SortKey;
    }

    /**
     * <p>adr150OrderKey を取得します。
     * @return adr150OrderKey
     */
    public int getAdr150OrderKey() {
        return adr150OrderKey__;
    }

    /**
     * <p>adr150OrderKey をセットします。
     * @param adr150OrderKey adr150OrderKey
     */
    public void setAdr150OrderKey(int adr150OrderKey) {
        adr150OrderKey__ = adr150OrderKey;
    }
}