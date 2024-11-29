package jp.groupsession.v2.adr.adr100;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr020.Adr020ParamModel;
import jp.groupsession.v2.adr.adr100.model.Adr100DetailModel;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] アドレス帳 会社一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr100ParamModel extends Adr020ParamModel {
    /** 検索フラグ */
    private int adr100searchFlg__ = 0;
    /** ページ */
    private int adr100page__ = 0;
    /** ページ(画面上部) */
    private int adr100pageTop__ = 0;
    /** ページ(画面下部) */
    private int adr100pageBottom__ = 0;

    /** 企業コード */
    private String adr100code__ = null;
    /** 会社名 */
    private String adr100coName__ = null;
    /** 会社名カナ */
    private String adr100coNameKn__ = null;
    /** 支店・営業所名 */
    private String adr100coBaseName__ = null;
    /** 業種 */
    private int adr100atiSid__ = 0;
    /** 都道府県 */
    private int adr100tdfk__ = 0;
    /** 備考 */
    private String adr100biko__ = null;

    /** 企業コード(検索条件保持用) */
    private String adr100svCode__ = null;
    /** 会社名(検索条件保持用) */
    private String adr100svCoName__ = null;
    /** 会社名カナ(検索条件保持用) */
    private String adr100svCoNameKn__ = null;
    /** 支店・営業所名(検索条件保持用) */
    private String adr100svCoBaseName__ = null;
    /** 業種(検索条件保持用) */
    private int adr100svAtiSid__ = 0;
    /** 都道府県(検索条件保持用) */
    private int adr100svTdfk__ = 0;
    /** 備考(検索条件保持用) */
    private String adr100svBiko__ = null;

    /** 会社情報(検索結果) 一覧 */
    private List<Adr100DetailModel> companyList__ = null;

    /** ソートキー */
    private int adr100SortKey__ = GSConstAddress.COMPANY_SORT_CODE;
    /** オーダーキー */
    private int adr100OrderKey__ = GSConst.ORDER_KEY_ASC;

    /** 検索モード */
    private int adr100mode__ = GSConstAddress.SEARCH_COMPANY_MODE_50;
    /** 会社名カナ一覧 */
    private List<String> adr100comNameKanaList__ = null;

    /** クリックカナ */
    private String adr100SearchKana__ = null;

    /** 選択会社情報 SID */
    private String[] adr100SelectCom__ = null;
    /** 選択会社情報 会社名 */
    private String[] adr100SelectComName__ = null;

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
        AdrValidateUtil.validateTextField(errors, adr100code__, "adr100code",
                gsMsg.getMessage(req, "address.7"), GSConstAddress.MAX_LENGTH_COMPANY_CODE, false);

        //会社名
        AdrValidateUtil.validateTextField(errors, adr100coName__, "adr100coName",
                gsMsg.getMessage(req, "cmn.company.name"),
                                GSConstAddress.MAX_LENGTH_COMPANY_NAME, false);
        //会社名(カナ)
        AdrValidateUtil.validateTextFieldKana(errors,
                                        adr100coNameKn__, "adr100coNameKn",
                                        gsMsg.getMessage(req, "cmn.company.name")
                                        + "(" + gsMsg.getMessage(req, "cmn.kana") + ")",
                                        GSConstAddress.MAX_LENGTH_COMPANY_NAME_KN,
                                        false);
        //支店・営業所名
        AdrValidateUtil.validateTextField(errors, adr100coBaseName__, "adr100coBaseName",
                gsMsg.getMessage(req, "address.10"), GSConstAddress.MAX_LENGTH_COBASE_NAME, false);
        //備考
        AdrValidateUtil.validateTextField(errors, adr100biko__, "adr100biko__",
                gsMsg.getMessage(req, "cmn.memo"), GSConstAddress.MAX_LENGTH_ADR_BIKO, false);

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateSelectCheck100(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;
        //会社情報
        String msgComImfo = gsMsg.getMessage(req, "address.118");

        //未選択チェック
        if (adr100SelectCom__ == null) {
            msg = new ActionMessage(
                    "error.select.required.text", msgComImfo);
            StrutsUtil.addMessage(errors, msg, "comSid");
        } else {
            if (adr100SelectCom__.length < 1) {
                msg = new ActionMessage(
                        "error.select.required.text", msgComImfo);
                StrutsUtil.addMessage(errors, msg, "comSid");
            }
        }
        return errors;
    }

    /**
     * <p>adr100mode を取得します。
     * @return adr100mode
     */
    public int getAdr100mode() {
        return adr100mode__;
    }

    /**
     * <p>adr100mode をセットします。
     * @param adr100mode adr100mode
     */
    public void setAdr100mode(int adr100mode) {
        adr100mode__ = adr100mode;
    }

    /**
     * <p>adr100atiSid を取得します。
     * @return adr100atiSid
     */
    public int getAdr100atiSid() {
        return adr100atiSid__;
    }

    /**
     * <p>adr100atiSid をセットします。
     * @param adr100atiSid adr100atiSid
     */
    public void setAdr100atiSid(int adr100atiSid) {
        adr100atiSid__ = adr100atiSid;
    }

    /**
     * <p>adr100biko を取得します。
     * @return adr100biko
     */
    public String getAdr100biko() {
        return adr100biko__;
    }

    /**
     * <p>adr100biko をセットします。
     * @param adr100biko adr100biko
     */
    public void setAdr100biko(String adr100biko) {
        adr100biko__ = adr100biko;
    }

    /**
     * <p>adr100coBaseName を取得します。
     * @return adr100coBaseName
     */
    public String getAdr100coBaseName() {
        return adr100coBaseName__;
    }

    /**
     * <p>adr100coBaseName をセットします。
     * @param adr100coBaseName adr100coBaseName
     */
    public void setAdr100coBaseName(String adr100coBaseName) {
        adr100coBaseName__ = adr100coBaseName;
    }

    /**
     * <p>adr100code を取得します。
     * @return adr100code
     */
    public String getAdr100code() {
        return adr100code__;
    }

    /**
     * <p>adr100code をセットします。
     * @param adr100code adr100code
     */
    public void setAdr100code(String adr100code) {
        adr100code__ = adr100code;
    }

    /**
     * <p>adr100coName を取得します。
     * @return adr100coName
     */
    public String getAdr100coName() {
        return adr100coName__;
    }

    /**
     * <p>adr100coName をセットします。
     * @param adr100coName adr100coName
     */
    public void setAdr100coName(String adr100coName) {
        adr100coName__ = adr100coName;
    }

    /**
     * <p>adr100coNameKn を取得します。
     * @return adr100coNameKn
     */
    public String getAdr100coNameKn() {
        return adr100coNameKn__;
    }

    /**
     * <p>adr100coNameKn をセットします。
     * @param adr100coNameKn adr100coNameKn
     */
    public void setAdr100coNameKn(String adr100coNameKn) {
        adr100coNameKn__ = adr100coNameKn;
    }

    /**
     * <p>adr100page を取得します。
     * @return adr100page
     */
    public int getAdr100page() {
        return adr100page__;
    }

    /**
     * <p>adr100page をセットします。
     * @param adr100page adr100page
     */
    public void setAdr100page(int adr100page) {
        adr100page__ = adr100page;
    }

    /**
     * <p>adr100pageBottom を取得します。
     * @return adr100pageBottom
     */
    public int getAdr100pageBottom() {
        return adr100pageBottom__;
    }

    /**
     * <p>adr100pageBottom をセットします。
     * @param adr100pageBottom adr100pageBottom
     */
    public void setAdr100pageBottom(int adr100pageBottom) {
        adr100pageBottom__ = adr100pageBottom;
    }

    /**
     * <p>adr100pageTop を取得します。
     * @return adr100pageTop
     */
    public int getAdr100pageTop() {
        return adr100pageTop__;
    }

    /**
     * <p>adr100pageTop をセットします。
     * @param adr100pageTop adr100pageTop
     */
    public void setAdr100pageTop(int adr100pageTop) {
        adr100pageTop__ = adr100pageTop;
    }

    /**
     * <p>adr100searchFlg を取得します。
     * @return adr100searchFlg
     */
    public int getAdr100searchFlg() {
        return adr100searchFlg__;
    }

    /**
     * <p>adr100searchFlg をセットします。
     * @param adr100searchFlg adr100searchFlg
     */
    public void setAdr100searchFlg(int adr100searchFlg) {
        adr100searchFlg__ = adr100searchFlg;
    }

    /**
     * <p>adr100svAtiSid を取得します。
     * @return adr100svAtiSid
     */
    public int getAdr100svAtiSid() {
        return adr100svAtiSid__;
    }

    /**
     * <p>adr100svAtiSid をセットします。
     * @param adr100svAtiSid adr100svAtiSid
     */
    public void setAdr100svAtiSid(int adr100svAtiSid) {
        adr100svAtiSid__ = adr100svAtiSid;
    }

    /**
     * <p>adr100svBiko を取得します。
     * @return adr100svBiko
     */
    public String getAdr100svBiko() {
        return adr100svBiko__;
    }

    /**
     * <p>adr100svBiko をセットします。
     * @param adr100svBiko adr100svBiko
     */
    public void setAdr100svBiko(String adr100svBiko) {
        adr100svBiko__ = adr100svBiko;
    }

    /**
     * <p>adr100svCoBaseName を取得します。
     * @return adr100svCoBaseName
     */
    public String getAdr100svCoBaseName() {
        return adr100svCoBaseName__;
    }

    /**
     * <p>adr100svCoBaseName をセットします。
     * @param adr100svCoBaseName adr100svCoBaseName
     */
    public void setAdr100svCoBaseName(String adr100svCoBaseName) {
        adr100svCoBaseName__ = adr100svCoBaseName;
    }

    /**
     * <p>adr100svCode を取得します。
     * @return adr100svCode
     */
    public String getAdr100svCode() {
        return adr100svCode__;
    }

    /**
     * <p>adr100svCode をセットします。
     * @param adr100svCode adr100svCode
     */
    public void setAdr100svCode(String adr100svCode) {
        adr100svCode__ = adr100svCode;
    }

    /**
     * <p>adr100svCoName を取得します。
     * @return adr100svCoName
     */
    public String getAdr100svCoName() {
        return adr100svCoName__;
    }

    /**
     * <p>adr100svCoName をセットします。
     * @param adr100svCoName adr100svCoName
     */
    public void setAdr100svCoName(String adr100svCoName) {
        adr100svCoName__ = adr100svCoName;
    }

    /**
     * <p>adr100svCoNameKn を取得します。
     * @return adr100svCoNameKn
     */
    public String getAdr100svCoNameKn() {
        return adr100svCoNameKn__;
    }

    /**
     * <p>adr100svCoNameKn をセットします。
     * @param adr100svCoNameKn adr100svCoNameKn
     */
    public void setAdr100svCoNameKn(String adr100svCoNameKn) {
        adr100svCoNameKn__ = adr100svCoNameKn;
    }

    /**
     * <p>adr100svTdfk を取得します。
     * @return adr100svTdfk
     */
    public int getAdr100svTdfk() {
        return adr100svTdfk__;
    }

    /**
     * <p>adr100svTdfk をセットします。
     * @param adr100svTdfk adr100svTdfk
     */
    public void setAdr100svTdfk(int adr100svTdfk) {
        adr100svTdfk__ = adr100svTdfk;
    }

    /**
     * <p>adr100tdfk を取得します。
     * @return adr100tdfk
     */
    public int getAdr100tdfk() {
        return adr100tdfk__;
    }

    /**
     * <p>adr100tdfk をセットします。
     * @param adr100tdfk adr100tdfk
     */
    public void setAdr100tdfk(int adr100tdfk) {
        adr100tdfk__ = adr100tdfk;
    }

    /**
     * <p>companyList を取得します。
     * @return companyList
     */
    public List<Adr100DetailModel> getCompanyList() {
        return companyList__;
    }

    /**
     * <p>companyList をセットします。
     * @param companyList companyList
     */
    public void setCompanyList(List<Adr100DetailModel> companyList) {
        companyList__ = companyList;
    }

    /**
     * <p>adr100SortKey を取得します。
     * @return adr100SortKey
     */
    public int getAdr100SortKey() {
        return adr100SortKey__;
    }

    /**
     * <p>adr100SortKey をセットします。
     * @param adr100SortKey adr100SortKey
     */
    public void setAdr100SortKey(int adr100SortKey) {
        adr100SortKey__ = adr100SortKey;
    }

    /**
     * <p>adr100OrderKey を取得します。
     * @return adr100OrderKey
     */
    public int getAdr100OrderKey() {
        return adr100OrderKey__;
    }

    /**
     * <p>adr100OrderKey をセットします。
     * @param adr100OrderKey adr100OrderKey
     */
    public void setAdr100OrderKey(int adr100OrderKey) {
        adr100OrderKey__ = adr100OrderKey;
    }

    /**
     * <p>adr100comNameKanaList を取得します。
     * @return adr100comNameKanaList
     */
    public List<String> getAdr100comNameKanaList() {
        return adr100comNameKanaList__;
    }

    /**
     * <p>adr100comNameKanaList をセットします。
     * @param adr100comNameKanaList adr100comNameKanaList
     */
    public void setAdr100comNameKanaList(List<String> adr100comNameKanaList) {
        adr100comNameKanaList__ = adr100comNameKanaList;
    }

    /**
     * <p>adr100SearchKana を取得します。
     * @return adr100SearchKana
     */
    public String getAdr100SearchKana() {
        return adr100SearchKana__;
    }

    /**
     * <p>adr100SearchKana をセットします。
     * @param adr100SearchKana adr100SearchKana
     */
    public void setAdr100SearchKana(String adr100SearchKana) {
        adr100SearchKana__ = adr100SearchKana;
    }

    /**
     * @return adr100SelectCom
     */
    public String[] getAdr100SelectCom() {
        return adr100SelectCom__;
    }

    /**
     * @param adr100SelectCom セットする adr100SelectCom
     */
    public void setAdr100SelectCom(String[] adr100SelectCom) {
        adr100SelectCom__ = adr100SelectCom;
    }

    /**
     * @return adr100SelectComName
     */
    public String[] getAdr100SelectComName() {
        return adr100SelectComName__;
    }

    /**
     * @param adr100SelectComName セットする adr100SelectComName
     */
    public void setAdr100SelectComName(String[] adr100SelectComName) {
        adr100SelectComName__ = adr100SelectComName;
    }

}