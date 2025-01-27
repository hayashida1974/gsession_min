package jp.groupsession.v2.adr.adr120;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.GSValidateAdr;
import jp.groupsession.v2.adr.GSValidateAdrCsv;
import jp.groupsession.v2.adr.adr100.Adr100Form;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.ui.parts.industry.AdrIndustrySelector;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 会社インポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr120Form extends Adr100Form implements ISelectorUseForm {

    /** 画面初期表示フラグ*/
    private int adr120init__ = 0;
    /** 取込ファイル */
    private String[] adr120file__ = null;
    /** 所属業種 */
    private String[] adr120atiSid__ = null;
    /** 既存の会社情報更新フラグ */
    private int adr120updateFlg__ = 0;

    /** 取込みファイルコンボ */
    private List<LabelValueBean> adr120fileCombo__ = null;

    /** 所属業種 UI */
    private AdrIndustrySelector adr120atiSidUI__ =
            AdrIndustrySelector.builder()
                .chainLabel(new GsMessageReq("address.103", null))
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("address.104", null))
                        .chainParameterName(
                                "adr120atiSid")
                    )
                .build();

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
        //戻り先
        msgForm.addHiddenParam("adr110BackId", getAdr110BackId());
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tempDir テンポラリディレクトリ
     * @param reqMdl RequestModel
     * @return エラー
     * @throws Exception 実行時例外
     */
    public ActionErrors validateCheck(Connection con, String tempDir, RequestModel reqMdl)
    throws Exception {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        String eprefix = "inputFile.";

        AddressBiz addressBiz = new AddressBiz(reqMdl);
        List<Cmn110FileModel> fileDataList = addressBiz.getFileData(tempDir);

        if (fileDataList == null || fileDataList.isEmpty()) {
            ActionMessage msg =
                new ActionMessage("error.select.required.text",
                        gsMsg.getMessage("cmn.capture.file"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
        } else {

            boolean csvError = false;
            String fileName = null;
            //複数選択エラー
            if (fileDataList.size() > 2) {
                ActionMessage msg =
                    new ActionMessage("error.input.notfound.file",
                            gsMsg.getMessage("cmn.capture.file"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.file");
                csvError = true;
            } else {
                //拡張子チェック
                fileName = fileDataList.get(0).getFileName();
                String strExt = StringUtil.getExtension(fileName);
                if (strExt == null || !strExt.toUpperCase().equals(".CSV")) {
                    ActionMessage msg =
                        new ActionMessage("error.select.required.text",
                                gsMsg.getMessage("cmn.csv.file.format"));
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
                    csvError = true;
                }
            }
            String fullPath = tempDir + fileDataList.get(0).getSaveFileName();
            CompanyCsvReader csvReader = new CompanyCsvReader(con);
            if (!csvError) {
                if (csvReader.isOverRowCount(fullPath)) {
                    ActionMessage msg =
                            new ActionMessage("error.over.row.csvdata",
                                    gsMsg.getMessage("cmn.capture.file"),
                                    String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT));
                        StrutsUtil.addMessage(errors, msg, eprefix + "error.over.row.csvdata");

                    csvError = true;
                }
            }
            if (!csvError) {
                csvReader.readCsvFile(fullPath);
                List<CompanyCsvModel> companyList = csvReader.getCompanyList();

                List<String> companyCodeList = new ArrayList<String>();
                List<String> checkCodeList = new ArrayList<String>();
                for (CompanyCsvModel companyData : companyList) {
                    String rowStr = gsMsg.getMessage(
                            "cmn.line2", new String[] {String.valueOf(companyData.getRowNum())});
                    String rowNum = String.valueOf(companyData.getRowNum());

                    //企業コード
                    String companyCode = companyData.getCompanyCode();
                    AdrValidateUtil.validateTextField(errors, companyCode,
                                                    "companyCode" + rowNum,
                                                    rowStr + gsMsg.getMessage("address.7"),
                                                    GSConstAddress.MAX_LENGTH_COMPANY_CODE,
                                                    true);

                    /***********************************************************/
                    /** 企業コード重複チェック 2011/11/18追加修正     開始 **/
                    /***********************************************************/
                    boolean addFlg = true;
                    //「同じ企業コードが存在した場合、既存の会社情報を上書きする」
                    // にチェックなし
                    if (adr120updateFlg__ == GSConstAddress.COMPANY_UPDATE_NO) {
                        AdrCompanyDao companyDao = new AdrCompanyDao(con);
                        if (companyDao.existCompany(-1, companyCode)) {
                            String msgKey = "error.duplication.companycode.import";
                            ActionMessage msg = new ActionMessage(msgKey, rowStr
                                          + gsMsg.getMessage("address.7"));
                            StrutsUtil.addMessage(
                                    errors, msg, "companyCode." + rowNum + msgKey);
                            addFlg = false;
                        } else {
                            addFlg = true;
                        }

                        //ファイル内の企業コードと一致するか
                        boolean fileFlg = checkCodeList.contains(companyCode);
                        if (fileFlg) {
                            ActionMessage msg = new ActionMessage("error.select.dup.list2",
                                    rowStr + gsMsg.getMessage("address.7"),
                                                     gsMsg.getMessage("address.149"));
                            StrutsUtil.addMessage(errors, msg, eprefix + rowNum
                                                  + "error.select.dup.list2");
                        }

                        if (!StringUtil.isNullZeroString(companyCode) && addFlg && !fileFlg) {
                            companyCodeList.add(companyCode);
                        }
                        checkCodeList.add(companyCode);

                    } else {
                        //「同じ企業コードが存在した場合、既存の会社情報を上書きする」にチェック有り
                        if (!StringUtil.isNullZeroString(companyCode)) {
                            companyCodeList.add(companyCode);
                        }
                    }


                    /***********************************************************/
                    /** 2011/11/18追加修正    終了 *****************************/
                    /***********************************************************/

                    //会社名
                    AdrValidateUtil.validateTextField(errors, companyData.getCompanyName(),
                                               "companyName" + rowNum,
                                               rowStr + gsMsg.getMessage("cmn.company.name"),
                                               GSConstAddress.MAX_LENGTH_COMPANY_NAME,
                                               false);
                    //会社名(カナ)
                    AdrValidateUtil.validateTextFieldKana(errors, companyData.getCompanyNameKn(),
                                                    "companyNameKn" + rowNum,
                                                    rowStr
                                                    + gsMsg.getMessage("cmn.company.name")
                                                    + "(" + gsMsg.getMessage("cmn.kana") + ")",
                                                    GSConstAddress.MAX_LENGTH_COMPANY_NAME_KN,
                                                    false);

                    GSValidateAdr gsValidate = new GSValidateAdr(reqMdl);
                    GSValidateAdrCsv gsValidateCvs = new GSValidateAdrCsv(reqMdl);

                    //会社郵便番号
                    gsValidate.validateCsvPostNum(errors, companyData.getCompanyPostNo(),
                                                        companyData.getRowNum(), reqMdl,
                                                        gsMsg.getMessage("address.139"));
                    //会社都道府県
                    gsValidateCvs.validateCsvTdfk(errors, companyData.getCompanyTdfk(),
                                                    companyData.getRowNum(), con,
                                                    gsMsg.getMessage("address.139"));
                    //会社住所１
                    AdrValidateUtil.validateTextField(errors, companyData.getCompanyAddress1(),
                                                    "companyAddress1" + rowNum,
                                                    rowStr
                                                    + gsMsg.getMessage("address.src.37"),
                                                    GSConstAddress.MAX_LENGTH_ADDRESS,
                                                    false);
                    //会社住所２
                    AdrValidateUtil.validateTextField(errors, companyData.getCompanyAddress2(),
                                                    "companyAddress2" + rowNum,
                                                    rowStr
                                                    + gsMsg.getMessage("address.src.38"),
                                                    GSConstAddress.MAX_LENGTH_ADDRESS,
                                                    false);
                    //URL
                    AdrValidateUtil.validateTextField(errors, companyData.getCompanyUrl(),
                                                    "companyUrl" + rowNum,
                                                    rowStr + "URL",
                                                    GSConstAddress.MAX_LENGTH_URL,
                                                    false);
                    //備考
                    AdrValidateUtil.validateTextAreaField(errors, companyData.getCompanyBiko(),
                                               "companyBiko" + rowNum,
                                               rowStr + gsMsg.getMessage("cmn.memo"),
                                               GSConstAddress.MAX_LENGTH_ADR_BIKO,
                                               false);
                    //企業拠点種別
                    boolean indispensabilityFlg = false;
                    String companyBaseType
                        = NullDefault.getString(companyData.getCompanyBaseType(), "");
                    if (companyBaseType.length() > 0) {
                        if (!companyBaseType.equals(
                                String.valueOf(GSConstAddress.ABATYPE_HEADOFFICE))
                        && !companyBaseType.equals(
                                String.valueOf(GSConstAddress.ABATYPE_BRANCH))
                        && !companyBaseType.equals(
                                String.valueOf(GSConstAddress.ABATYPE_BUSINESSOFFICE))) {
                            String msgKey = "error.input.notvalidate.data";

                            ActionMessage msg = new ActionMessage(
                                    msgKey, rowStr + gsMsg.getMessage("address.src.29"));

                            StrutsUtil.addMessage(
                                    errors, msg,
                                    "companyBaseType" + rowNum + "." + msgKey);
                        } else {
                            indispensabilityFlg = true;
                        }
                    } else if (!StringUtil.isNullZeroString(companyData.getCompanyBasePostNo())
                            || NullDefault.getInt(companyData.getCompanyBaseTdfk(), 0) > 0
                            || !StringUtil.isNullZeroString(companyData.getCompanyBaseAddress1())
                            || !StringUtil.isNullZeroString(companyData.getCompanyBaseAddress2())
                            || !StringUtil.isNullZeroString(companyData.getCompanyBaseBiko())) {

                        indispensabilityFlg = true;
                    }

                    //企業拠点名
                    AdrValidateUtil.validateTextField(errors, companyData.getCompanyBaseName(),
                                                  "companyBaseName" + rowNum,
                                                  rowStr + gsMsg.getMessage("address.src.30"),
                                                  GSConstAddress.MAX_LENGTH_COBASE_NAME,
                                                  indispensabilityFlg);
                    //企業拠点郵便番号
                    String companyBasePostNo = companyData.getCompanyBasePostNo();
                    gsValidate.validateCsvPostNum(errors, companyBasePostNo,
                                                        companyData.getRowNum(), reqMdl,
                                                        gsMsg.getMessage("address.150"));
                    //企業拠点都道府県
                    String companyBaseTdfk = companyData.getCompanyBaseTdfk();
                    gsValidateCvs.validateCsvTdfk(errors, companyBaseTdfk,
                                                    companyData.getRowNum(), con,
                                                    gsMsg.getMessage("address.150"));
                    //企業拠点住所１
                    AdrValidateUtil.validateTextField(errors, companyData.getCompanyBaseAddress1(),
                                                    "companyBaseAddress1" + rowNum,
                                                    rowStr + gsMsg.getMessage("address.src.47"),
                                                    GSConstAddress.MAX_LENGTH_ADDRESS,
                                                    false);
                    //企業拠点住所２
                    AdrValidateUtil.validateTextField(errors, companyData.getCompanyBaseAddress2(),
                                                    "companyBaseAddress2" + rowNum,
                                                    rowStr + gsMsg.getMessage("address.src.48"),
                                                    GSConstAddress.MAX_LENGTH_ADDRESS,
                                                    false);
                    //企業拠点備考
                    AdrValidateUtil.validateTextAreaField(errors, companyData.getCompanyBaseBiko(),
                                                  "companyBaseBiko" + rowNum,
                                                  rowStr + gsMsg.getMessage("address.src.31"),
                                                  GSConstAddress.MAX_LENGTH_ADR_BIKO,
                                                  false);
                }

                AdrCompanyDao companyDao = new AdrCompanyDao(con);
                for (String code : companyCodeList) {

                    boolean exist = companyDao.existCompany(0, code);
                    if (!exist || getAdr120updateFlg() == 1) {
                        CompanyCsvModel companyData = csvReader.getCompanyDataMap().get(code);
                        //会社名の未入力チェック
                        if (StringUtil.isNullZeroString(companyData.getCompanyName())) {
                            String msgKey = "error.not.input.companyname";
                            ActionMessage msg = new ActionMessage(msgKey,
                                    StringUtilHtml.transToHTmlPlusAmparsant(code),
                                    gsMsg.getMessage("cmn.company.name"));
                            StrutsUtil.addMessage(
                                    errors, msg, code + ".companyName" + msgKey);
                        }
                        //会社名カナの未入力チェック
                        if (StringUtil.isNullZeroString(companyData.getCompanyNameKn())) {
                            String msgKey = "error.not.input.companyname";
                            ActionMessage msg = new ActionMessage(msgKey, code,
                                    gsMsg.getMessage("address.9"));
                            StrutsUtil.addMessage(
                                    errors, msg, code + ".companyNameKn" + msgKey);
                        }
                    }
                }
            }
        }

        return errors;
    }



    /**
     * <p>adr120atiSid を取得します。
     * @return adr120atiSid
     */
    public String[] getAdr120atiSid() {
        return adr120atiSid__;
    }

    /**
     * <p>adr120atiSid をセットします。
     * @param adr120atiSid adr120atiSid
     */
    public void setAdr120atiSid(String[] adr120atiSid) {
        adr120atiSid__ = adr120atiSid;
    }

    /**
     * <p>adr120file を取得します。
     * @return adr120file
     */
    public String[] getAdr120file() {
        return adr120file__;
    }

    /**
     * <p>adr120file をセットします。
     * @param adr120file adr120file
     */
    public void setAdr120file(String[] adr120file) {
        adr120file__ = adr120file;
    }

    /**
     * <p>adr120updateFlg を取得します。
     * @return adr120updateFlg
     */
    public int getAdr120updateFlg() {
        return adr120updateFlg__;
    }

    /**
     * <p>adr120updateFlg をセットします。
     * @param adr120updateFlg adr120updateFlg
     */
    public void setAdr120updateFlg(int adr120updateFlg) {
        adr120updateFlg__ = adr120updateFlg;
    }

    /**
     * <p>adr120fileCombo を取得します。
     * @return adr120fileCombo
     */
    public List<LabelValueBean> getAdr120fileCombo() {
        return adr120fileCombo__;
    }

    /**
     * <p>adr120fileCombo をセットします。
     * @param adr120fileCombo adr120fileCombo
     */
    public void setAdr120fileCombo(List<LabelValueBean> adr120fileCombo) {
        adr120fileCombo__ = adr120fileCombo;
    }

    /**
     * <p>adr120init を取得します。
     * @return adr120init
     * @see jp.groupsession.v2.adr.adr120.Adr120Form#adr120init__
     */
    public int getAdr120init() {
        return adr120init__;
    }

    /**
     * <p>adr120init をセットします。
     * @param adr120init adr120init
     * @see jp.groupsession.v2.adr.adr120.Adr120Form#adr120init__
     */
    public void setAdr120init(int adr120init) {
        adr120init__ = adr120init;
    }

    /**
     * <p>adr120atiSidUI を取得します。
     * @return adr120atiSidUI
     */
    public AdrIndustrySelector getAdr120atiSidUI() {
        return adr120atiSidUI__;
    }

    /**
     * <p>adr120atiSidUI をセットします。
     * @param adr120atiSidUI adr120atiSidUI
     */
    public void setAdr120atiSidUI(AdrIndustrySelector adr120atiSidUI) {
        adr120atiSidUI__ = adr120atiSidUI;
    }

}
