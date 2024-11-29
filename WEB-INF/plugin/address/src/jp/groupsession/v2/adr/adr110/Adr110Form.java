package jp.groupsession.v2.adr.adr110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr100.Adr100Form;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.adr.ui.parts.industry.AdrIndustrySelector;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳.会社登録画面のForm
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr110Form extends Adr100Form implements ISelectorUseForm {

    /** 初期フラグ */
    private int adr110initFlg__ = 0;

    /** 企業コード */
    private String adr110coCode__ = null;
    /** 会社名 */
    private String adr110coName__ = null;
    /** 会社名(カナ) */
    private String adr110coNameKn__ = null;
    /** 会社情報 郵便番号(上3桁) */
    private String adr110coPostno1__ = null;
    /** 会社情報 郵便番号(下4桁) */
    private String adr110coPostno2__ = null;
    /** 会社情報 都道府県 */
    private int adr110coTdfk__ = -1;
    /** 会社情報 住所1 */
    private String adr110coAddress1__ = null;
    /** 会社情報 住所2 */
    private String adr110coAddress2__ = null;
    /** 所属業種 */
    private String[] adr110atiList__ = null;
    /** URL */
    private String adr110url__ = null;
    /** 備考 */
    private String adr110biko__ = null;

    /** 会社拠点 種別 */
    private int adr110abaType__ = 0;
    /** 会社拠点 支店・営業所名 */
    private String adr110abaName__ = null;
    /** 会社拠点 郵便番号上3桁 */
    private String adr110abaPostno1__ = null;
    /** 会社拠点 郵便番号下4桁 */
    private String adr110abaPostno2__ = null;
    /** 会社拠点 都道府県 */
    private int adr110abaTdfk__ = 0;
    /** 会社拠点 住所１ */
    private String adr110abaAddress1__ = null;
    /** 会社拠点 住所２ */
    private String adr110abaAddress2__ = null;
    /** 会社拠点 備考 */
    private String adr110abaBiko__ = null;

    /** 所属業種 UI */
    private AdrIndustrySelector adr110atiListUI__ =
            AdrIndustrySelector.builder()
                .chainLabel(new GsMessageReq("address.103", null))
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("address.104", null))
                        .chainParameterName(
                                "adr110atiList")
                    )
                .build();

    /** 会社拠点情報一覧 */
    private List<Adr110BaseForm> abaList__ = null;

    /** 拠点種別 */
    private List<LabelValueBean> abaTypeList__ = null;

    /** 削除対象の会社拠点情報Index */
    private int adr110deleteCompanyBaseIndex__ = 0;
    /** 編集対象の会社拠点情報Index */
    private int adr111editCompanyBaseIndex__ = 0;

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int adr110searchUse__ = GSConst.PLUGIN_USE;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Adr110Form() {
        abaList__ = new ArrayList <Adr110BaseForm>();
    }

    /**
     * <br>[機  能] 拠点情報の入力値を初期化する。
     * <br>[解  説]
     * <br>[備  考]
     */
    public void resetAbaParam() {
        /** 会社拠点 種別 */
        adr110abaType__ = 0;
        /** 会社拠点 支店・営業所名 */
        adr110abaName__ = null;
        /** 会社拠点 郵便番号上3桁 */
        adr110abaPostno1__ = null;
        /** 会社拠点 郵便番号下4桁 */
        adr110abaPostno2__ = null;
        /** 会社拠点 都道府県 */
        adr110abaTdfk__ = 0;
        /** 会社拠点 住所１ */
        adr110abaAddress1__ = null;
        /** 会社拠点 住所２ */
        adr110abaAddress2__ = null;
        /** 会社拠点 備考 */
        adr110abaBiko__ = null;
    }


    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);

        //戻り先画面
        msgForm.addHiddenParam("adr100backFlg", getAdr100backFlg());

        //処理モード
        msgForm.addHiddenParam("adr110ProcMode", getAdr110ProcMode());
        //編集対象会社SID
        msgForm.addHiddenParam("adr110editAcoSid", getAdr110editAcoSid());
        //検索フラグ
        msgForm.addHiddenParam("adr100searchFlg", getAdr100searchFlg());
        //ページ
        msgForm.addHiddenParam("adr100page", getAdr100page());
        //ページ(画面上部)
        msgForm.addHiddenParam("adr100pageTop", getAdr100pageTop());
        //ページ(画面下部)
        msgForm.addHiddenParam("adr100pageBottom", getAdr100pageBottom());

        //企業コード
        msgForm.addHiddenParam("adr100code", getAdr100code());
        //会社名
        msgForm.addHiddenParam("adr100coName", getAdr100coName());
        //会社名カナ
        msgForm.addHiddenParam("adr100coNameKn", getAdr100coNameKn());
        //支店・営業所名
        msgForm.addHiddenParam("adr100coBaseName", getAdr100coBaseName());
        //業種
        msgForm.addHiddenParam("adr100atiSid", getAdr100atiSid());
        //都道府県
        msgForm.addHiddenParam("adr100tdfk", getAdr100tdfk());
        //備考
        msgForm.addHiddenParam("adr100biko", getAdr100biko());

        //企業コード(検索条件保持用)
        msgForm.addHiddenParam("adr100svCode", getAdr100svCode());
        //会社名(検索条件保持用)
        msgForm.addHiddenParam("adr100svCoName", getAdr100svCoName());
        //会社名カナ(検索条件保持用)
        msgForm.addHiddenParam("adr100svCoNameKn", getAdr100svCoNameKn());
        //支店・営業所名(検索条件保持用)
        msgForm.addHiddenParam("adr100svCoBaseName", getAdr100svCoBaseName());
        //業種(検索条件保持用)
        msgForm.addHiddenParam("adr100svAtiSid", getAdr100svAtiSid());
        //都道府県(検索条件保持用)
        msgForm.addHiddenParam("adr100svTdfk", getAdr100svTdfk());
        //備考(検索条件保持用)
        msgForm.addHiddenParam("adr100svBiko", getAdr100svBiko());
        //ソートキー
        msgForm.addHiddenParam("adr100SortKey", getAdr100SortKey());
        //オーダーキー
        msgForm.addHiddenParam("adr100OrderKey", getAdr100OrderKey());
        //検索モード
        msgForm.addHiddenParam("adr100mode", getAdr100mode());
        //クリックカナ
        msgForm.addHiddenParam("adr100SearchKana", getAdr100SearchKana());
        //初期フラグ
        msgForm.addHiddenParam("adr110initFlg", adr110initFlg__);
        //企業コード
        msgForm.addHiddenParam("adr110coCode", adr110coCode__);
        //会社名
        msgForm.addHiddenParam("adr110coName", adr110coName__);
        //会社名(カナ)
        msgForm.addHiddenParam("adr110coNameKn", adr110coNameKn__);

        //会社情報 郵便番号上3桁
        msgForm.addHiddenParam("adr110coPostno1", adr110coPostno1__);
        //会社情報 郵便番号下4桁
        msgForm.addHiddenParam("adr110coPostno2", adr110coPostno2__);
        //会社情報 addHiddenParam
        msgForm.addHiddenParam("adr110coTdfk", adr110coTdfk__);
        //会社情報 住所１
        msgForm.addHiddenParam("adr110coAddress1", adr110coAddress1__);
        //会社情報 住所２
        msgForm.addHiddenParam("adr110coAddress2", adr110coAddress2__);

        //所属業種
        msgForm.addHiddenParam("adr110atiList", adr110atiList__);
        //URL
        msgForm.addHiddenParam("adr110url", adr110url__);
        //備考
        msgForm.addHiddenParam("adr110biko", adr110biko__);

        //会社拠点 種別
        msgForm.addHiddenParam("adr110abaType", adr110abaType__);
        //会社拠点 支店・営業所名
        msgForm.addHiddenParam("adr110abaName", adr110abaName__);
        //会社拠点 郵便番号上3桁
        msgForm.addHiddenParam("adr110abaPostno1", adr110abaPostno1__);
        //会社拠点 郵便番号下4桁
        msgForm.addHiddenParam("adr110abaPostno2", adr110abaPostno2__);
        //会社拠点 都道府県
        msgForm.addHiddenParam("adr110abaTdfk", adr110abaTdfk__);
        //会社拠点 住所１
        msgForm.addHiddenParam("adr110abaAddress1", adr110abaAddress1__);
        //会社拠点 住所２
        msgForm.addHiddenParam("adr110abaAddress2", adr110abaAddress2__);
        //会社拠点 備考
        msgForm.addHiddenParam("adr110abaBiko", adr110abaBiko__);
        //戻り先
        msgForm.addHiddenParam("adr110BackId", getAdr110BackId());

        if (abaList__ != null) {

            int index = 0;
            for (Adr110BaseForm abaData : abaList__) {
                //会社拠点情報 index
                msgForm.addHiddenParam("abaList[" + index + "].adr110abaIndex",
                                    abaData.getAdr110abaIndex());
                //会社拠点 会社拠点SID(一覧)
                msgForm.addHiddenParam("abaList[" + index + "].adr110abaSidDetail",
                                    abaData.getAdr110abaSidDetail());
                //会社拠点 種別(一覧)
                msgForm.addHiddenParam("abaList[" + index + "].adr110abaTypeDetail",
                                    abaData.getAdr110abaTypeDetail());
                //会社拠点 支店・営業所名(一覧)
                msgForm.addHiddenParam("abaList[" + index + "].adr110abaName",
                                    abaData.getAdr110abaName());
                //会社拠点 郵便番号上3桁(一覧)
                msgForm.addHiddenParam("abaList[" + index + "].adr110abaPostno1",
                                    abaData.getAdr110abaPostno1());
                //会社拠点 郵便番号下4桁(一覧)
                msgForm.addHiddenParam("abaList[" + index + "].adr110abaPostno2",
                                    abaData.getAdr110abaPostno2());
                //会社拠点 都道府県(一覧)
                msgForm.addHiddenParam("abaList[" + index + "].adr110abaTdfk",
                                    abaData.getAdr110abaTdfk());
                //会社拠点 住所１(一覧)
                msgForm.addHiddenParam("abaList[" + index + "].adr110abaAddress1",
                                    abaData.getAdr110abaAddress1());
                //会社拠点 住所２(一覧)
                msgForm.addHiddenParam("abaList[" + index + "].adr110abaAddress2",
                                    abaData.getAdr110abaAddress2());
                //会社拠点 備考(一覧)
                msgForm.addHiddenParam("abaList[" + index + "].adr110abaBiko",
                                    abaData.getAdr110abaBiko());

                index++;
            }
        }

        //削除対象の会社拠点情報Index
        msgForm.addHiddenParam("adr110deleteCompanyBaseIndex",
                            adr110deleteCompanyBaseIndex__);
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        //企業コード
        AdrValidateUtil.validateTextField(errors, adr110coCode__, "adr110coCode",
                gsMsg.getMessage(req, "address.7"), GSConstAddress.MAX_LENGTH_COMPANY_CODE, true);
        if (errors.isEmpty()) {
            //企業コードの重複チェック
            int acoSid = getAdr110editAcoSid();
            if (getAdr110ProcMode() == GSConstAddress.PROCMODE_ADD) {
                acoSid = -1;
            }
            AdrCompanyDao companyDao = new AdrCompanyDao(con);
            if (companyDao.existCompany(acoSid, adr110coCode__)) {
                String msgKey = "error.duplication.companycode";
                ActionMessage msg = new ActionMessage(msgKey,
                        StringUtilHtml.transToHTmlPlusAmparsant(adr110coCode__));
                StrutsUtil.addMessage(
                        errors, msg, "adr110coCode" + msgKey);

            }
        }
        //業種
        if (adr110atiList__ != null) {
            //既に削除された業種を確認しエラーメッセージを表示する
            //削除された業種のフォーム値は除去する
            ArrayList<String> atiSidList = new ArrayList<String>();
            AdrTypeindustryDao industryDao = new AdrTypeindustryDao(con);
            boolean atiErr = false;
            for (int index = 0; index < adr110atiList__.length; index++) {
                int atiSid = Integer.parseInt(adr110atiList__[index]);
                if (atiSid > 0) {
                    AdrTypeindustryModel industryMdl
                        = industryDao.select(atiSid);
                    if (industryMdl != null) {
                        atiSidList.add(String.valueOf(industryMdl.getAtiSid()));
                    } else {
                        atiErr = true;
                    }
                }
            }
            if (atiErr) {
                String msgKey = "error.none.edit.data";
                ActionMessage msg = new ActionMessage(
                        msgKey,
                        gsMsg.getMessage("address.104"),
                        gsMsg.getMessage("cmn.setting"));
                StrutsUtil.addMessage(
                        errors, msg, "adr020permitViewUser" + msgKey);
            }
        }


        //会社名
        AdrValidateUtil.validateTextField(errors, adr110coName__, "adr110coName",
                                        gsMsg.getMessage(req, "cmn.company.name"),
                                        GSConstAddress.MAX_LENGTH_COMPANY_NAME, true);
        //会社名(カナ)
        AdrValidateUtil.validateTextFieldKana(errors,
                                        adr110coNameKn__, "adr110coNameKn",
                                        gsMsg.getMessage(req, "cmn.company.name")
                                        + "(" + gsMsg.getMessage(req, "cmn.kana") + ")",
                                        GSConstAddress.MAX_LENGTH_COMPANY_NAME_KN,
                                        true);
        //会社情報 郵便番号
        AdrValidateUtil.validatePostNum(errors, adr110coPostno1__, adr110coPostno2__, req);

        //会社情報 都道府県
        CommonBiz cmnBiz = new CommonBiz();
        ArrayList<LabelValueBean> tdfkList = cmnBiz.getTdfkLabelList(con, gsMsg);
        //LabelValueBean型のリストをvalueのみのリストに型変換
        List<String> tdfkValList = tdfkList.stream()
                .map(value -> value.getValue()).collect(Collectors.toList());

        if (!tdfkValList.contains(String.valueOf(adr110coTdfk__))) {
            ActionMessage msg = null;
            String strTdfk = gsMsg.getMessage("cmn.prefectures");
            msg = new ActionMessage("error.input.notvalidate.data", strTdfk);
            errors.add("error.input.notvalidate.data", msg);;
        }

        //会社情報 住所１
        AdrValidateUtil.validateTextField(errors, adr110coAddress1__, "adr110coAddress1",
                gsMsg.getMessage(req, "cmn.address") + "１",
                GSConstAddress.MAX_LENGTH_ADDRESS, false);
        //会社情報 住所２
        AdrValidateUtil.validateTextField(errors, adr110coAddress2__, "adr110coAddress2",
                gsMsg.getMessage(req, "cmn.address") + "２",
                GSConstAddress.MAX_LENGTH_ADDRESS, false);

        //URL
        AdrValidateUtil.validateTextField(errors, adr110url__, "adr110url",
                                        GSConstAddress.TEXT_URL,
                                        GSConstAddress.MAX_LENGTH_URL, false);
        
        if (!ValidateUtil.isHttpUrlFormat(adr110url__)) {
            String url = gsMsg.getMessage("cmn.url");
            ActionMessage msg =
                    new ActionMessage("error.input.format.text", url);
            StrutsUtil.addMessage(errors, msg, "error.input.format.url");
        }
        //備考
        AdrValidateUtil.validateTextAreaField(errors, adr110biko__, "adr110biko",
                                        gsMsg.getMessage(req, "cmn.memo"),
                                        GSConstAddress.MAX_LENGTH_ADR_BIKO, false);

        return errors;
    }

    /**
     * <br>[機  能] 拠点情報一覧の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheckBase(
            Connection con, HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        Adr110BaseForm baseForm = new Adr110BaseForm();

        if (abaList__ != null && abaList__.size() > 0) {
            for (int i = 0; i < abaList__.size(); i++) {
                baseForm.setAdr110abaTypeDetail(abaList__.get(i).getAdr110abaTypeDetail());
                baseForm.setAdr110abaName(abaList__.get(i).getAdr110abaName());
                baseForm.setAdr110abaPostno1(abaList__.get(i).getAdr110abaPostno1());
                baseForm.setAdr110abaPostno2(abaList__.get(i).getAdr110abaPostno2());
                baseForm.setAdr110abaTdfk(abaList__.get(i).getAdr110abaTdfk());
                baseForm.setAdr110abaAddress1(abaList__.get(i).getAdr110abaAddress1());
                baseForm.setAdr110abaAddress2(abaList__.get(i).getAdr110abaAddress2());
                baseForm.setAdr110abaBiko(abaList__.get(i).getAdr110abaBiko());
                errors = baseForm.validateCheck(con, req);
                if (!errors.isEmpty()) {
                    break;
                }
            }
        }
        return errors;
    }

    /**
     * <p>adr110abaAddress1 を取得します。
     * @return adr110abaAddress1
     */
    public String getAdr110abaAddress1() {
        return adr110abaAddress1__;
    }

    /**
     * <p>adr110abaAddress1 をセットします。
     * @param adr110abaAddress1 adr110abaAddress1
     */
    public void setAdr110abaAddress1(String adr110abaAddress1) {
        adr110abaAddress1__ = adr110abaAddress1;
    }

    /**
     * <p>adr110abaAddress2 を取得します。
     * @return adr110abaAddress2
     */
    public String getAdr110abaAddress2() {
        return adr110abaAddress2__;
    }

    /**
     * <p>adr110abaAddress2 をセットします。
     * @param adr110abaAddress2 adr110abaAddress2
     */
    public void setAdr110abaAddress2(String adr110abaAddress2) {
        adr110abaAddress2__ = adr110abaAddress2;
    }

    /**
     * <p>adr110abaBiko を取得します。
     * @return adr110abaBiko
     */
    public String getAdr110abaBiko() {
        return adr110abaBiko__;
    }

    /**
     * <p>adr110abaBiko をセットします。
     * @param adr110abaBiko adr110abaBiko
     */
    public void setAdr110abaBiko(String adr110abaBiko) {
        adr110abaBiko__ = adr110abaBiko;
    }

    /**
     * <p>adr110abaName を取得します。
     * @return adr110abaName
     */
    public String getAdr110abaName() {
        return adr110abaName__;
    }

    /**
     * <p>adr110abaName をセットします。
     * @param adr110abaName adr110abaName
     */
    public void setAdr110abaName(String adr110abaName) {
        adr110abaName__ = adr110abaName;
    }

    /**
     * <p>adr110abaPostno1 を取得します。
     * @return adr110abaPostno1
     */
    public String getAdr110abaPostno1() {
        return adr110abaPostno1__;
    }

    /**
     * <p>adr110abaPostno1 をセットします。
     * @param adr110abaPostno1 adr110abaPostno1
     */
    public void setAdr110abaPostno1(String adr110abaPostno1) {
        adr110abaPostno1__ = adr110abaPostno1;
    }

    /**
     * <p>adr110abaPostno2 を取得します。
     * @return adr110abaPostno2
     */
    public String getAdr110abaPostno2() {
        return adr110abaPostno2__;
    }

    /**
     * <p>adr110abaPostno2 をセットします。
     * @param adr110abaPostno2 adr110abaPostno2
     */
    public void setAdr110abaPostno2(String adr110abaPostno2) {
        adr110abaPostno2__ = adr110abaPostno2;
    }

    /**
     * <p>adr110abaTdfk を取得します。
     * @return adr110abaTdfk
     */
    public int getAdr110abaTdfk() {
        return adr110abaTdfk__;
    }

    /**
     * <p>adr110abaTdfk をセットします。
     * @param adr110abaTdfk adr110abaTdfk
     */
    public void setAdr110abaTdfk(int adr110abaTdfk) {
        adr110abaTdfk__ = adr110abaTdfk;
    }

    /**
     * <p>adr110abaType を取得します。
     * @return adr110abaType
     */
    public int getAdr110abaType() {
        return adr110abaType__;
    }

    /**
     * <p>adr110abaType をセットします。
     * @param adr110abaType adr110abaType
     */
    public void setAdr110abaType(int adr110abaType) {
        adr110abaType__ = adr110abaType;
    }

    /**
     * <p>adr110atiList を取得します。
     * @return adr110atiList
     */
    public String[] getAdr110atiList() {
        return adr110atiList__;
    }

    /**
     * <p>adr110atiList をセットします。
     * @param adr110atiList adr110atiList
     */
    public void setAdr110atiList(String[] adr110atiList) {
        adr110atiList__ = adr110atiList;
    }

    /**
     * <p>adr110biko を取得します。
     * @return adr110biko
     */
    public String getAdr110biko() {
        return adr110biko__;
    }

    /**
     * <p>adr110biko をセットします。
     * @param adr110biko adr110biko
     */
    public void setAdr110biko(String adr110biko) {
        adr110biko__ = adr110biko;
    }

    /**
     * <p>adr110coCode を取得します。
     * @return adr110coCode
     */
    public String getAdr110coCode() {
        return adr110coCode__;
    }

    /**
     * <p>adr110coCode をセットします。
     * @param adr110coCode adr110coCode
     */
    public void setAdr110coCode(String adr110coCode) {
        adr110coCode__ = adr110coCode;
    }

    /**
     * <p>adr110coName を取得します。
     * @return adr110coName
     */
    public String getAdr110coName() {
        return adr110coName__;
    }

    /**
     * <p>adr110coName をセットします。
     * @param adr110coName adr110coName
     */
    public void setAdr110coName(String adr110coName) {
        adr110coName__ = adr110coName;
    }

    /**
     * <p>adr110coNameKn を取得します。
     * @return adr110coNameKn
     */
    public String getAdr110coNameKn() {
        return adr110coNameKn__;
    }

    /**
     * <p>adr110coNameKn をセットします。
     * @param adr110coNameKn adr110coNameKn
     */
    public void setAdr110coNameKn(String adr110coNameKn) {
        adr110coNameKn__ = adr110coNameKn;
    }

    /**
     * <p>adr110coPostno1 を取得します。
     * @return adr110coPostno1
     * @see jp.groupsession.v2.adr.adr110.Adr110Form#adr110coPostno1__
     */
    public String getAdr110coPostno1() {
        return adr110coPostno1__;
    }

    /**
     * <p>adr110coPostno1 をセットします。
     * @param adr110coPostno1 adr110coPostno1
     * @see jp.groupsession.v2.adr.adr110.Adr110Form#adr110coPostno1__
     */
    public void setAdr110coPostno1(String adr110coPostno1) {
        adr110coPostno1__ = adr110coPostno1;
    }

    /**
     * <p>adr110coPostno2 を取得します。
     * @return adr110coPostno2
     * @see jp.groupsession.v2.adr.adr110.Adr110Form#adr110coPostno2__
     */
    public String getAdr110coPostno2() {
        return adr110coPostno2__;
    }

    /**
     * <p>adr110coPostno2 をセットします。
     * @param adr110coPostno2 adr110coPostno2
     * @see jp.groupsession.v2.adr.adr110.Adr110Form#adr110coPostno2__
     */
    public void setAdr110coPostno2(String adr110coPostno2) {
        adr110coPostno2__ = adr110coPostno2;
    }

    /**
     * <p>adr110coTdfk を取得します。
     * @return adr110coTdfk
     * @see jp.groupsession.v2.adr.adr110.Adr110ParamModel#adr110coTdfk__
     */
    public int getAdr110coTdfk() {
        return adr110coTdfk__;
    }

    /**
     * <p>adr110coTdfk をセットします。
     * @param adr110coTdfk adr110coTdfk
     * @see jp.groupsession.v2.adr.adr110.Adr110ParamModel#adr110coTdfk__
     */
    public void setAdr110coTdfk(int adr110coTdfk) {
        adr110coTdfk__ = adr110coTdfk;
    }

    /**
     * <p>adr110coAddress1 を取得します。
     * @return adr110coAddress1
     * @see jp.groupsession.v2.adr.adr110.Adr110Form#adr110coAddress1__
     */
    public String getAdr110coAddress1() {
        return adr110coAddress1__;
    }

    /**
     * <p>adr110coAddress1 をセットします。
     * @param adr110coAddress1 adr110coAddress1
     * @see jp.groupsession.v2.adr.adr110.Adr110Form#adr110coAddress1__
     */
    public void setAdr110coAddress1(String adr110coAddress1) {
        adr110coAddress1__ = adr110coAddress1;
    }

    /**
     * <p>adr110coAddress2 を取得します。
     * @return adr110coAddress2
     * @see jp.groupsession.v2.adr.adr110.Adr110Form#adr110coAddress2__
     */
    public String getAdr110coAddress2() {
        return adr110coAddress2__;
    }

    /**
     * <p>adr110coAddress2 をセットします。
     * @param adr110coAddress2 adr110coAddress2
     * @see jp.groupsession.v2.adr.adr110.Adr110Form#adr110coAddress2__
     */
    public void setAdr110coAddress2(String adr110coAddress2) {
        adr110coAddress2__ = adr110coAddress2;
    }

    /**
     * <p>adr110url を取得します。
     * @return adr110url
     */
    public String getAdr110url() {
        return adr110url__;
    }

    /**
     * <p>adr110url をセットします。
     * @param adr110url adr110url
     */
    public void setAdr110url(String adr110url) {
        adr110url__ = adr110url;
    }

    /**
     * <p>adr110atiListUI を取得します。
     * @return adr110atiListUI
     */
    public AdrIndustrySelector getAdr110atiListUI() {
        return adr110atiListUI__;
    }

    /**
     * <p>adr110atiListUI をセットします。
     * @param adr110atiListUI adr110atiListUI
     */
    public void setAdr110atiListUI(AdrIndustrySelector adr110atiListUI) {
        adr110atiListUI__ = adr110atiListUI;
    }

    /**
     * <p>adr110initFlg を取得します。
     * @return adr110initFlg
     */
    public int getAdr110initFlg() {
        return adr110initFlg__;
    }

    /**
     * <p>adr110initFlg をセットします。
     * @param adr110initFlg adr110initFlg
     */
    public void setAdr110initFlg(int adr110initFlg) {
        adr110initFlg__ = adr110initFlg;
    }

    /**
     * <p>abaList を取得します。
     * @return abaList
     */
    public List<Adr110BaseForm> getAbaListToList() {
        return abaList__;
    }

    /**
     * <p>abaList をセットします。
     * @param abaList abaList
     */
    public void setAbaListForm(List<Adr110BaseForm> abaList) {
        abaList__ = abaList;
    }

    /**
     * <p>abaTypeList を取得します。
     * @return abaTypeList
     */
    public List<LabelValueBean> getAbaTypeList() {
        return abaTypeList__;
    }

    /**
     * <p>abaTypeList をセットします。
     * @param abaTypeList abaTypeList
     */
    public void setAbaTypeList(List<LabelValueBean> abaTypeList) {
        abaTypeList__ = abaTypeList;
    }

    /**
     * <p>Adr110BaseForm を取得します。
     * @param iIndex インデックス番号
     * @return Adr110BaseForm を戻す
     */
    public Adr110BaseForm getAbaList(int iIndex) {
        while (abaList__.size() <= iIndex) {
            abaList__.add(new Adr110BaseForm());
        }
        return abaList__.get(iIndex);
    }
    /**
     * <p>Adr110BaseForm を取得します。
     * @return Adr110BaseForm[]
     */
    public Adr110BaseForm[] getAbaList() {
        int size = 0;
        if (abaList__ != null) {
            size = abaList__.size();
        }
        return (Adr110BaseForm[]) abaList__.toArray(new Adr110BaseForm[size]);
    }
    /**
     * <br>[機  能] 表の行数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 表の行数
     */
    public int getAbaListFormSize() {
        return abaList__.size();
    }

    /**
     * <p>adr111editCompanyBaseIndex を取得します。
     * @return adr111editCompanyBaseIndex
     */
    public int getAdr111editCompanyBaseIndex() {
        return adr111editCompanyBaseIndex__;
    }

    /**
     * <p>adr111editCompanyBaseIndex をセットします。
     * @param adr111editCompanyBaseIndex adr111editCompanyBaseIndex
     */
    public void setAdr111editCompanyBaseIndex(int adr111editCompanyBaseIndex) {
        adr111editCompanyBaseIndex__ = adr111editCompanyBaseIndex;
    }

    /**
     * <p>adr110deleteCompanyBaseIndex を取得します。
     * @return adr110deleteCompanyBaseIndex
     */
    public int getAdr110deleteCompanyBaseIndex() {
        return adr110deleteCompanyBaseIndex__;
    }

    /**
     * <p>adr110deleteCompanyBaseIndex をセットします。
     * @param adr110deleteCompanyBaseIndex adr110deleteCompanyBaseIndex
     */
    public void setAdr110deleteCompanyBaseIndex(int adr110deleteCompanyBaseIndex) {
        adr110deleteCompanyBaseIndex__ = adr110deleteCompanyBaseIndex;
    }

    /**
     * <p>adr110searchUse を取得します。
     * @return adr110searchUse
     */
    public int getAdr110searchUse() {
        return adr110searchUse__;
    }

    /**
     * <p>adr110searchUse をセットします。
     * @param adr110searchUse adr110searchUse
     */
    public void setAdr110searchUse(int adr110searchUse) {
        adr110searchUse__ = adr110searchUse;
    }

}
