package jp.groupsession.v2.adr.adr020;

import static jp.groupsession.v2.adr.GSConstAddress.*;
import static jp.groupsession.v2.cmn.GSConst.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.Adr010Form;
import jp.groupsession.v2.adr.dao.AdrCompanyBaseDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] アドレス帳登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr020Form extends Adr010Form implements ISelectorUseForm {

    /** 初期フラグ */
    private int adr020init__ = 0;

    /** 氏名 姓 */
    private String adr020unameSei__ = null;
    /** 氏名 名 */
    private String adr020unameMei__ = null;
    /** 氏名カナ 姓 */
    private String adr020unameSeiKn__ = null;
    /** 氏名カナ 名 */
    private String adr020unameMeiKn__ = null;
    /** 会社 */
    private String adr020selectCompany__ = null;
    /** 会社拠点 */
    private String adr020selectCompanyBase__ = null;
    /** 所属 */
    private String adr020syozoku__ = null;
    /** 役職 */
    private int adr020position__ = 0;
    /** メールアドレス1 */
    private String adr020mail1__ = null;
    /** メールアドレス1 コメント */
    private String adr020mail1Comment__ = null;
    /** メールアドレス2 */
    private String adr020mail2__ = null;
    /** メールアドレス2 コメント */
    private String adr020mail2Comment__ = null;
    /** メールアドレス3 */
    private String adr020mail3__ = null;
    /** メールアドレス3 コメント */
    private String adr020mail3Comment__ = null;
    /** 電話番号1 */
    private String adr020tel1__ = null;
    /** 電話番号1 内線 */
    private String adr020tel1Nai__ = null;
    /** 電話番号1 コメント */
    private String adr020tel1Comment__ = null;
    /** 電話番号2 */
    private String adr020tel2__ = null;
    /** 電話番号2 内線 */
    private String adr020tel2Nai__ = null;
    /** 電話番号2 コメント */
    private String adr020tel2Comment__ = null;
    /** 電話番号3 */
    private String adr020tel3__ = null;
    /** 電話番号3 内線 */
    private String adr020tel3Nai__ = null;
    /** 電話番号3 コメント */
    private String adr020tel3Comment__ = null;
    /** ＦＡＸ1 */
    private String adr020fax1__ = null;
    /** ＦＡＸ1 コメント */
    private String adr020fax1Comment__ = null;
    /** ＦＡＸ2 */
    private String adr020fax2__ = null;
    /** ＦＡＸ2 コメント */
    private String adr020fax2Comment__ = null;
    /** ＦＡＸ3 */
    private String adr020fax3__ = null;
    /** ＦＡＸ3 コメント */
    private String adr020fax3Comment__ = null;
    /** 郵便番号上3桁 */
    private String adr020postno1__ = null;
    /** 郵便番号下4桁 */
    private String adr020postno2__ = null;
    /** 都道府県 */
    private int adr020tdfk__ = 0;
    /** 住所１ */
    private String adr020address1__ = null;
    /** 住所２ */
    private String adr020address2__ = null;
    /** 備考 */
    private String adr020biko__ = null;
    /** 担当者 */
    private String[] adr020tantoList__ = null;
    /** 担当者グループ */
    private int adr020tantoGroup__ = -2;
    /** ラベル */
    private String[] adr020label__ = null;
    /** 閲覧権限 */
    private int adr020permitView__ = GSConst.ADR_VIEWPERMIT_OWN;
    /** 閲覧グループ */
    private String[] adr020permitViewGroup__ = null;
    /** 閲覧ユーザ */
    private String[] adr020permitViewUser__ = null;
    /** 閲覧ユーザグループ */
    private int adr020permitViewUserGroup__ = -2;
    /** 編集権限 */
    private int adr020permitEdit__ = GSConstAddress.EDITPERMIT_OWN;
    /** 編集グループ */
    private String[] adr020permitEditGroup__ = null;
    /** 編集ユーザ */
    private String[] adr020permitEditUser__ = null;
    /** 編集ユーザグループ */
    private int adr020permitEditUserGroup__ = -2;

    /** 削除ラベルSID */
    private String adr020deleteLabel__ = null;

    /** 会社SID */
    private String[] adr020CompanySid__ = null;
    /** 会社拠点SID */
    private String[] adr020CompanyBaseSid__ = null;

    /** 企業コード */
    private String adr020companyCode__ = null;
    /** 会社名 */
    private String adr020companyName__ = null;
    /** 支店・営業所名 */
    private String adr020companyBaseName__ = null;

    /** 担当者一覧 */
    private List<UsrLabelValueBean> selectTantoCombo__ = null;
    /** 閲覧グループ一覧 */
    private List<LabelValueBean> selectPermitViewGroup__ = null;
    /** 閲覧ユーザ一覧 */
    private List<UsrLabelValueBean> selectPermitViewUser__ = null;
    /** 編集グループ一覧 */
    private List<LabelValueBean> selectPermitEditGroup__ = null;
    /** 編集ユーザ一覧 */
    private List<UsrLabelValueBean> selectPermitEditUser__ = null;
    /** 担当者 UI */
    private UserGroupSelector adr020tantoListUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cmn.staff", null))
                .chainType(EnumSelectType.USER)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cmn.staff", null))
                        .chainParameterName(
                                "adr020tantoList")
                    )
                .chainGroupSelectionParamName("adr020tantoGroup")
                .build();
    /** 閲覧グループ UI */
    private UserGroupSelector adr020permitViewGroupUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("address.66", null))
                .chainType(EnumSelectType.GROUP)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("address.66", null))
                        .chainParameterName(
                                "adr020permitViewGroup")
                    )
                .build();
    /** 閲覧ユーザ UI */
    private UserGroupSelector adr020permitViewUserUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("address.68", null))
                .chainType(EnumSelectType.USER)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("address.68", null))
                        .chainParameterName(
                                "adr020permitViewUser")
                    )
                .chainGroupSelectionParamName("adr020permitViewUserGroup")
                .build();
    /** 編集グループ UI */
    private UserGroupSelector adr020permitEditGroupUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cmn.editgroup", null))
                .chainType(EnumSelectType.GROUP)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cmn.editgroup", null))
                        .chainParameterName(
                                "adr020permitEditGroup")
                    )
                .build();
    /** 編集ユーザ UI */
    private UserGroupSelector adr020permitEditUserUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cmn.edituser", null))
                .chainType(EnumSelectType.USER)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cmn.edituser", null))
                        .chainParameterName(
                                "adr020permitEditUser")
                    )
                .chainGroupSelectionParamName("adr020permitEditUserGroup")
                .build();

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int adr020searchUse__ = GSConst.PLUGIN_USE;

    /** 役職追加ボタン表示フラグ */
    private int adr020addPositionBtnFlg__ = 0;
    /** 会社追加ボタン表示フラグ */
    private int adr020addCompanyBtnFlg__ = 0;
    /** ラベル追加ボタン表示フラグ */
    private int adr020addLabelBtnFlg__ = 0;

    /** 複写ボタンフラグ */
    private int adrCopyFlg__ = 0;
    /** WEBメール 連携判定 */
    private int adr020webmail__ = 0;
    /** WEBメール 対象メール */
    private long adr020webmailId__ = 0;

    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);

        //アドレス帳登録処理区分
        msgForm.addHiddenParam("adr020ProcMode", getAdr020ProcMode());
        //初期フラグ
        msgForm.addHiddenParam("adr020init", adr020init__);

        //氏名 姓
        msgForm.addHiddenParam("adr020unameSei", adr020unameSei__);
        //氏名 名
        msgForm.addHiddenParam("adr020unameMei", adr020unameMei__);
        //氏名カナ 姓
        msgForm.addHiddenParam("adr020unameSeiKn", adr020unameSeiKn__);
        //氏名カナ 名
        msgForm.addHiddenParam("adr020unameMeiKn", adr020unameMeiKn__);
        //会社
        msgForm.addHiddenParam("adr020selectCompany", adr020selectCompany__);
        //会社拠点
        msgForm.addHiddenParam("adr020selectCompanyBase", adr020selectCompanyBase__);
        //所属
        msgForm.addHiddenParam("adr020syozoku", adr020syozoku__);
        //役職
        msgForm.addHiddenParam("adr020position", adr020position__);
        //メールアドレス1
        msgForm.addHiddenParam("adr020mail1", adr020mail1__);
        //メールアドレス1 コメント
        msgForm.addHiddenParam("adr020mail1Comment", adr020mail1Comment__);
        //メールアドレス2
        msgForm.addHiddenParam("adr020mail2", adr020mail2__);
        //メールアドレス2 コメント
        msgForm.addHiddenParam("adr020mail2Comment", adr020mail2Comment__);
        //メールアドレス3
        msgForm.addHiddenParam("adr020mail3", adr020mail3__);
        //メールアドレス3 コメント
        msgForm.addHiddenParam("adr020mail3Comment", adr020mail3Comment__);
        //電話番号1
        msgForm.addHiddenParam("adr020tel1", adr020tel1__);
        //電話番号1 内線
        msgForm.addHiddenParam("adr020tel1Nai", adr020tel1Nai__);
        //電話番号1 コメント
        msgForm.addHiddenParam("adr020tel1Comment", adr020tel1Comment__);
        //電話番号2
        msgForm.addHiddenParam("adr020tel2", adr020tel2__);
        //電話番号2 内線
        msgForm.addHiddenParam("adr020tel2Nai", adr020tel2Nai__);
        //電話番号2 コメント
        msgForm.addHiddenParam("adr020tel2Comment", adr020tel2Comment__);
        //電話番号3
        msgForm.addHiddenParam("adr020tel3", adr020tel3__);
        //電話番号3 内線
        msgForm.addHiddenParam("adr020tel3Nai", adr020tel3Nai__);
        //電話番号3 コメント
        msgForm.addHiddenParam("adr020tel3Comment", adr020tel3Comment__);
        //ＦＡＸ1
        msgForm.addHiddenParam("adr020fax1", adr020fax1__);
        //ＦＡＸ1 コメント
        msgForm.addHiddenParam("adr020fax1Comment", adr020fax1Comment__);
        //ＦＡＸ2
        msgForm.addHiddenParam("adr020fax2", adr020fax2__);
        //ＦＡＸ2 コメント
        msgForm.addHiddenParam("adr020fax2Comment", adr020fax2Comment__);
        //ＦＡＸ3
        msgForm.addHiddenParam("adr020fax3", adr020fax3__);
        //ＦＡＸ3 コメント
        msgForm.addHiddenParam("adr020fax3Comment", adr020fax3Comment__);
        //郵便番号上3桁
        msgForm.addHiddenParam("adr020postno1", adr020postno1__);
        //郵便番号下4桁
        msgForm.addHiddenParam("adr020postno2", adr020postno2__);
        //都道府県
        msgForm.addHiddenParam("adr020tdfk", adr020tdfk__);
        //住所１
        msgForm.addHiddenParam("adr020address1", adr020address1__);
        //住所２
        msgForm.addHiddenParam("adr020address2", adr020address2__);
        //備考
        msgForm.addHiddenParam("adr020biko", adr020biko__);
        //担当者
        msgForm.addHiddenParam("adr020tantoList", adr020tantoList__);
        //担当者グループ
        msgForm.addHiddenParam("adr020tantoGroup", adr020tantoGroup__);
        //ラベル
        msgForm.addHiddenParam("adr020label", adr020label__);
        //閲覧権限
        msgForm.addHiddenParam("adr020permitView", adr020permitView__);
        //閲覧グループ
        msgForm.addHiddenParam("adr020permitViewGroup", adr020permitViewGroup__);
        //閲覧ユーザ
        msgForm.addHiddenParam("adr020permitViewUser", adr020permitViewUser__);
        //閲覧ユーザグループ
        msgForm.addHiddenParam("adr020permitViewUserGroup", adr020permitViewUserGroup__);
        //編集権限
        msgForm.addHiddenParam("adr020permitEdit", adr020permitEdit__);
        //編集グループ
        msgForm.addHiddenParam("adr020permitEditGroup", adr020permitEditGroup__);
        //編集ユーザ
        msgForm.addHiddenParam("adr020permitEditUser", adr020permitEditUser__);
        //編集ユーザグループ
        msgForm.addHiddenParam("adr020permitEditUserGroup", adr020permitEditUserGroup__);
        //戻り先
        msgForm.addHiddenParam("adr020BackId", getAdr020BackId());

        //WEBメール 連携判定
        msgForm.addHiddenParam("adr020webmail", getAdr020webmail());
        //WEBメール 対象メール
        msgForm.addHiddenParam("adr020webmailId", getAdr020webmailId());

        msgForm.addHiddenParam("adr160pageNum1", getAdr160pageNum1());
        msgForm.addHiddenParam("adr160pageNum2", getAdr160pageNum2());
        msgForm.addHiddenParam("sortKey", getSortKey());
        msgForm.addHiddenParam("orderKey", getOrderKey());

        msgForm.addHiddenParam("adrCopyFlg", getAdrCopyFlg());
        msgForm.addHiddenParam("adr010webmail", getAdr010webmail());
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
    public ActionErrors validateCheck(Connection con, HttpServletRequest req)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();

        //氏名 姓
        String nameSei = gsMsg.getMessage(req, "cmn.name") + " "
                         + gsMsg.getMessage(req, "cmn.lastname");
        AdrValidateUtil.validateTextField(errors, adr020unameSei__, "adr020unameSei",
                                          nameSei, MAX_LENGTH_NAME_SEI, true);
        //氏名 名
        String nameMei = gsMsg.getMessage(req, "cmn.name") + " "
                         + gsMsg.getMessage(req, "cmn.name3");
        AdrValidateUtil.validateTextField(errors, adr020unameMei__, "adr020unameMei",
                                          nameMei, MAX_LENGTH_NAME_MEI, true);
        //氏名カナ 姓
        String nameSeiKn = gsMsg.getMessage(req, "cmn.name.kana") + " "
                           + gsMsg.getMessage(req, "cmn.lastname");
        AdrValidateUtil.validateTextFieldKana(errors, adr020unameSeiKn__, "adr020unameSeiKn",
                                              nameSeiKn, MAX_LENGTH_NAME_SEI_KN, true);
        //氏名カナ 名
        String nameMeiKn = gsMsg.getMessage(req, "cmn.name.kana") + " "
                           + gsMsg.getMessage(req, "cmn.name3");
        AdrValidateUtil.validateTextFieldKana(errors, adr020unameMeiKn__, "adr020unameMeiKn",
                                              nameMeiKn, MAX_LENGTH_NAME_MEI_KN, true);
        //会社情報
        if (!StringUtil.isNullZeroString(getAdr020selectCompany())) {
            AdrCompanyDao companyDao = new AdrCompanyDao(con);
            AdrCompanyModel companyMdl
                = companyDao.select(Integer.parseInt(getAdr020selectCompany()));
            String msgKey = "error.none.edit.data";
            if (companyMdl == null) {
                ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("address.118")
                        , gsMsg.getMessage("cmn.setting"));
                StrutsUtil.addMessage(
                        errors, msg, "adr020selectCompany" + msgKey);
            } else if (!StringUtil.isNullZeroString(getAdr020selectCompanyBase())) {
                AdrCompanyBaseDao companyBaseDao = new AdrCompanyBaseDao(con);
                AdrCompanyBaseModel companyBaseMdl
                    = companyBaseDao.select(
                            Integer.parseInt(getAdr020selectCompanyBase()));
                if (companyBaseMdl == null) {
                    ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("address.src.28")
                            , gsMsg.getMessage("cmn.setting"));
                    StrutsUtil.addMessage(
                            errors, msg, "adr020selectCompanyBase" + msgKey);
                }
            }
        }

        //役職
        if (getAdr020position() > 0) {
            AdrPositionDao positionDao = new AdrPositionDao(con);
            AdrPositionModel positionModel = positionDao.select(getAdr020position());
            if (positionModel == null) {
                String msgKey = "error.none.edit.data";
                ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("address.adr010.1")
                        , gsMsg.getMessage("cmn.setting"));
                StrutsUtil.addMessage(
                        errors, msg, "adr020position" + msgKey);
            }
        }

        //所属
        String syozoku = gsMsg.getMessage(req, "cmn.affiliation");
        AdrValidateUtil.validateTextField(errors, adr020syozoku__, "adr020syozoku",
                                          syozoku,
                                          MAX_LENGTH_SYOZOKU, false);
//        //メールアドレス１
//        AdrValidateUtil.validateTextField(errors, adr020mail1__, "adr020mail1",
//                                        TEXT_MAIL + "１", MAX_LENGTH_MAIL, false);
        //メールアドレス１
        AdrValidateUtil.validateMail(errors, this.adr020mail1__, 1, req);

        //メールアドレス１コメント
        String msgMailAddress = gsMsg.getMessage(req, "cmn.mailaddress");
        String msgComment = gsMsg.getMessage(req, "cmn.comment");
        AdrValidateUtil.validateTextField(errors, adr020mail1Comment__, "adr020mail1Comment",
                                          msgMailAddress + "１ " + msgComment,
                                          MAX_LENGTH_MAIL_COMMENT, false);
//        //メールアドレス２
//        AdrValidateUtil.validateTextField(errors, adr020mail2__, "adr020mail2",
//                                        TEXT_MAIL + "２", MAX_LENGTH_MAIL, false);

        //メールアドレス２
        AdrValidateUtil.validateMail(errors, this.adr020mail2__, 2, req);

        //メールアドレス２コメント
        AdrValidateUtil.validateTextField(errors, adr020mail2Comment__, "adr020mail2Comment",
                                        msgMailAddress + "２ " + msgComment,
                                        MAX_LENGTH_MAIL_COMMENT, false);
//        //メールアドレス３
//        AdrValidateUtil.validateTextField(errors, adr020mail3__, "adr020mail3",
//                                        TEXT_MAIL + "３", MAX_LENGTH_MAIL, false);

        //メールアドレス３
        AdrValidateUtil.validateMail(errors, this.adr020mail3__, 3, req);

        //メールアドレス３コメント
        AdrValidateUtil.validateTextField(errors, adr020mail3Comment__, "adr020mail3Comment",
                                         msgMailAddress + "３ " + msgComment,
                                         MAX_LENGTH_MAIL_COMMENT, false);

        //電話番号１
        String msgTelNum = gsMsg.getMessage(req, "cmn.tel");
        String msgNai = gsMsg.getMessage(req, "address.58");
        AdrValidateUtil.validateTel(errors, adr020tel1__, "adr020tel1",
                                        msgTelNum + "１", MAX_LENGTH_TEL, false);
        //電話番号１ 内線
        AdrValidateUtil.validateTextField(errors, adr020tel1Nai__, "adr020tel1Nai",
                                        msgTelNum + "１ " + msgNai, MAX_LENGTH_TEL_NAI, false);
        //電話番号１ コメント
        AdrValidateUtil.validateTextField(errors, adr020tel1Comment__, "adr020tel1Comment",
                                        msgTelNum + "１ " + msgComment,
                                        MAX_LENGTH_TEL_COMMENT, false);
        //電話番号２
        AdrValidateUtil.validateTel(errors, adr020tel2__, "adr020tel2",
                                        msgTelNum + "２", MAX_LENGTH_TEL, false);
        //電話番号２ 内線
        AdrValidateUtil.validateTextField(errors, adr020tel2Nai__, "adr020tel2Nai",
                                        msgTelNum + "２ " + msgNai, MAX_LENGTH_TEL_NAI, false);
        //電話番号２ コメント
        AdrValidateUtil.validateTextField(errors, adr020tel2Comment__, "adr020tel2Comment",
                                        msgTelNum + "２ " + msgComment,
                                        MAX_LENGTH_TEL_COMMENT, false);
        //電話番号３
        AdrValidateUtil.validateTel(errors, adr020tel3__, "adr020tel3",
                                        msgTelNum + "３", MAX_LENGTH_TEL, false);
        //電話番号３ 内線
        AdrValidateUtil.validateTextField(errors, adr020tel3Nai__, "adr020tel3Nai",
                                        msgTelNum + "３ " + msgNai, MAX_LENGTH_TEL_NAI, false);
        //電話番号３ コメント
        AdrValidateUtil.validateTextField(errors, adr020tel3Comment__, "adr020tel3Comment",
                                        msgTelNum + "３ " + msgComment,
                                        MAX_LENGTH_TEL_COMMENT, false);

        //ＦＡＸ１
        AdrValidateUtil.validateTel(errors, adr020fax1__, "adr020fax1",
                                        TEXT_FAX + "１", MAX_LENGTH_FAX, false);
        //ＦＡＸ１コメント
        AdrValidateUtil.validateTextField(errors, adr020fax1Comment__, "adr020fax1Comment",
                                        TEXT_FAX + "１ " + msgComment,
                                        MAX_LENGTH_FAX_COMMENT, false);
        //ＦＡＸ２
        AdrValidateUtil.validateTel(errors, adr020fax2__, "adr020fax2",
                                        TEXT_FAX + "２", MAX_LENGTH_FAX, false);
        //ＦＡＸ２コメント
        AdrValidateUtil.validateTextField(errors, adr020fax2Comment__, "adr020fax2Comment",
                                        TEXT_FAX + "２ " + msgComment,
                                        MAX_LENGTH_FAX_COMMENT, false);
        //ＦＡＸ３
        AdrValidateUtil.validateTel(errors, adr020fax3__, "adr020fax3",
                                        TEXT_FAX + "３", MAX_LENGTH_FAX, false);
        //ＦＡＸ３コメント
        AdrValidateUtil.validateTextField(errors, adr020fax3Comment__, "adr020fax3Comment",
                                        TEXT_FAX + "３ " + msgComment,
                                        MAX_LENGTH_FAX_COMMENT, false);

        //郵便番号
        AdrValidateUtil.validatePostNum(errors, adr020postno1__, adr020postno2__, req);
        //住所１
        String msgJyusyo = gsMsg.getMessage(req, "cmn.address");
        AdrValidateUtil.validateTextField(errors, adr020address1__, "adr020address1",
                                          msgJyusyo + "１", MAX_LENGTH_ADDRESS, false);
        //住所２
        AdrValidateUtil.validateTextField(errors, adr020address2__, "adr020address2",
                                        msgJyusyo + "２", MAX_LENGTH_ADDRESS, false);
        //備考
        String msgBiko = gsMsg.getMessage(req, "cmn.memo");
        AdrValidateUtil.validateTextAreaField(errors, adr020biko__, "adr020biko",
                                              msgBiko, MAX_LENGTH_ADR_BIKO, false);

        //閲覧権限グループ
        if (adr020permitView__ == ADR_VIEWPERMIT_GROUP) {
            if (adr020permitViewGroup__ == null || adr020permitViewGroup__.length == 0) {
                String msgKey = "error.select.required.text";
                ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage(req, "address.66"));
                StrutsUtil.addMessage(
                        errors, msg, "adr020permitViewGroup." + msgKey);
            } else {
                GroupDao grpDao = new GroupDao(con);
                int[] intGrpSidList = new int[adr020permitViewGroup__.length];
                ArrayList<Integer> delSidList = new ArrayList<Integer>();
                for (int i = 0; i < adr020permitViewGroup__.length; i++) {
                    intGrpSidList[i] = NullDefault.getInt(adr020permitViewGroup__[i], -1);
                    if (intGrpSidList[i] > 0) {
                        delSidList.add(intGrpSidList[i]);
                    }
                }
                List <CmnGroupmModel> grpDataList = grpDao.getGroups(intGrpSidList);
                for (CmnGroupmModel cmnGroupmModel__ : grpDataList) {
                    if (delSidList.contains(Integer.valueOf(cmnGroupmModel__.getGrpSid()))) {
                        delSidList.remove(Integer.valueOf(cmnGroupmModel__.getGrpSid()));
                    }
                }
                if (delSidList.size() > 0) {
                    String msgKey = "error.none.edit.data";
                    ActionMessage msg = new ActionMessage(msgKey
                            , gsMsg.getMessage("address.66")
                            , gsMsg.getMessage("cmn.setting"));
                    StrutsUtil.addMessage(
                            errors, msg, "adr020permitViewGroup" + msgKey);
                }
            }
        }

        //閲覧権限ユーザ
        if (adr020permitView__ == ADR_VIEWPERMIT_USER) {
            if (adr020permitViewUser__ == null || adr020permitViewUser__.length == 0) {
                String msgKey = "error.select.required.text";
                ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage(req, "address.68"));
                StrutsUtil.addMessage(
                        errors, msg, "adr020permitViewUser." + msgKey);
            } else {
                UserBiz userBiz = new UserBiz();

                List<UsrLabelValueBean> userLabelList =
                        userBiz.getUserLabelList(con, adr020permitViewUser__);
                ArrayList<String> delSidList = new ArrayList<String>();
                for (String sidStr : Arrays.asList(adr020permitViewUser__)) {
                    int sid = NullDefault.getInt(sidStr, -1);
                    if (sid > 0) {
                        delSidList.add(sidStr);
                    }
                }
                for (LabelValueBean bean__ : userLabelList) {
                    if (delSidList.contains(bean__.getValue())) {
                        delSidList.remove(bean__.getValue());
                    }
                }
                if (delSidList.size() > 0) {
                    String msgKey = "error.none.edit.data";
                    ActionMessage msg = new ActionMessage(msgKey
                            , gsMsg.getMessage("address.68")
                            , gsMsg.getMessage("cmn.setting"));
                    StrutsUtil.addMessage(
                            errors, msg, "adr020permitViewUser" + msgKey);
                }

            }
        }

        //編集権限グループ
        if (adr020permitView__ == ADR_VIEWPERMIT_GROUP || adr020permitEdit__ == EDITPERMIT_GROUP) {
            if (adr020permitEditGroup__ == null || adr020permitEditGroup__.length == 0) {
                String msgKey = "error.select.required.text";
                ActionMessage msg = new ActionMessage(msgKey,
                        gsMsg.getMessage(req, "cmn.editgroup"));
                StrutsUtil.addMessage(
                        errors, msg, "adr020permitEditGroup." + msgKey);
            } else {
                GroupDao grpDao = new GroupDao(con);
                int[] intGrpSidList = new int[adr020permitEditGroup__.length];
                ArrayList<Integer> delSidList = new ArrayList<Integer>();

                for (int i = 0; i < adr020permitEditGroup__.length; i++) {
                    intGrpSidList[i] = NullDefault.getInt(adr020permitEditGroup__[i], -1);
                    if (intGrpSidList[i] > 0) {
                        delSidList.add(intGrpSidList[i]);
                    }
                }
                List <CmnGroupmModel> grpDataList = grpDao.getGroups(intGrpSidList);
                for (CmnGroupmModel cmnGroupmModel__ : grpDataList) {
                    if (delSidList.contains(Integer.valueOf(cmnGroupmModel__.getGrpSid()))) {
                        delSidList.remove(Integer.valueOf(cmnGroupmModel__.getGrpSid()));
                    }
                }

                if (delSidList.size() > 0) {
                    String msgKey = "error.none.edit.data";
                    ActionMessage msg = new ActionMessage(msgKey
                            , gsMsg.getMessage("cmn.editgroup")
                            , gsMsg.getMessage("cmn.setting"));
                    StrutsUtil.addMessage(
                            errors, msg, "adr020permitEditGroup" + msgKey);
                }
            }
        }

        //編集権限ユーザ
        if (adr020permitView__ == ADR_VIEWPERMIT_USER || adr020permitEdit__ == EDITPERMIT_USER) {
            if (adr020permitEditUser__ == null || adr020permitEditUser__.length == 0) {
                String msgKey = "error.select.required.text";
                ActionMessage msg = new ActionMessage(msgKey,
                        gsMsg.getMessage(req, "cmn.edituser"));
                StrutsUtil.addMessage(
                        errors, msg, "adr020permitEditUser." + msgKey);
            } else {
                UserBiz userBiz = new UserBiz();

                List<UsrLabelValueBean> userLabelList =
                        userBiz.getUserLabelList(con, adr020permitEditUser__);
                ArrayList<String> delSidList = new ArrayList<String>();
                for (String sidStr : Arrays.asList(adr020permitEditUser__)) {
                    int sid = NullDefault.getInt(sidStr, -1);
                    if (sid > 0) {
                        delSidList.add(sidStr);
                    }
                }
                for (LabelValueBean bean__ : userLabelList) {
                    if (delSidList.contains(bean__.getValue())) {
                        delSidList.remove(bean__.getValue());
                    }
                }
                if (delSidList.size() > 0) {
                    String msgKey = "error.none.edit.data";
                    ActionMessage msg = new ActionMessage(msgKey
                            , gsMsg.getMessage("cmn.edituser")
                            , gsMsg.getMessage("cmn.setting"));
                    StrutsUtil.addMessage(
                            errors, msg, "adr020permitEditUser" + msgKey);
                }

            }
        }
        //ラベル
        if (getAdr020label() != null) {
            AdrLabelDao labelDao = new AdrLabelDao(con);
            String[] selectLabel = getAdr020label();
            for (String sidStr : selectLabel) {
                AdrLabelModel mdl = labelDao.select(NullDefault.getInt(sidStr, 0));
                if (mdl == null) {
                    String msgKey = "error.none.edit.data";
                    ActionMessage msg = new ActionMessage(msgKey
                            , gsMsg.getMessage("cmn.label")
                            , gsMsg.getMessage("cmn.setting"));
                    StrutsUtil.addMessage(
                            errors, msg, "adr020permitEditUser" + msgKey);
                    break;
                }
            }
        }
        //担当者
        if (adr020tantoList__ != null && adr020tantoList__.length > 0) {
            UserBiz userBiz = new UserBiz();

            List<UsrLabelValueBean> userLabelList =
                    userBiz.getUserLabelList(con, adr020tantoList__);
            ArrayList<String> delSidList = new ArrayList<String>();
            for (String sidStr : Arrays.asList(adr020tantoList__)) {
                int sid = NullDefault.getInt(sidStr, -1);
                if (sid > 0) {
                    delSidList.add(sidStr);
                }
            }
            for (LabelValueBean bean__ : userLabelList) {
                if (delSidList.contains(bean__.getValue())) {
                    delSidList.remove(bean__.getValue());
                }
            }
            if (delSidList.size() > 0) {
                String msgKey = "error.none.edit.data";
                ActionMessage msg = new ActionMessage(msgKey
                        , gsMsg.getMessage("cmn.staff")
                        , gsMsg.getMessage("cmn.setting"));
                StrutsUtil.addMessage(
                        errors, msg, "adr020permitEditUser" + msgKey);
            }

        }

        return errors;
    }

    /**
     * <p>adr020address1 を取得します。
     * @return adr020address1
     */
    public String getAdr020address1() {
        return adr020address1__;
    }
    /**
     * <p>adr020address1 をセットします。
     * @param adr020address1 adr020address1
     */
    public void setAdr020address1(String adr020address1) {
        adr020address1__ = adr020address1;
    }
    /**
     * <p>adr020address2 を取得します。
     * @return adr020address2
     */
    public String getAdr020address2() {
        return adr020address2__;
    }
    /**
     * <p>adr020address2 をセットします。
     * @param adr020address2 adr020address2
     */
    public void setAdr020address2(String adr020address2) {
        adr020address2__ = adr020address2;
    }
    /**
     * <p>adr020biko を取得します。
     * @return adr020biko
     */
    public String getAdr020biko() {
        return adr020biko__;
    }
    /**
     * <p>adr020biko をセットします。
     * @param adr020biko adr020biko
     */
    public void setAdr020biko(String adr020biko) {
        adr020biko__ = adr020biko;
    }
    /**
     * <p>adr020fax1 を取得します。
     * @return adr020fax1
     */
    public String getAdr020fax1() {
        return adr020fax1__;
    }
    /**
     * <p>adr020fax1 をセットします。
     * @param adr020fax1 adr020fax1
     */
    public void setAdr020fax1(String adr020fax1) {
        adr020fax1__ = adr020fax1;
    }
    /**
     * <p>adr020fax1Comment を取得します。
     * @return adr020fax1Comment
     */
    public String getAdr020fax1Comment() {
        return adr020fax1Comment__;
    }
    /**
     * <p>adr020fax1Comment をセットします。
     * @param adr020fax1Comment adr020fax1Comment
     */
    public void setAdr020fax1Comment(String adr020fax1Comment) {
        adr020fax1Comment__ = adr020fax1Comment;
    }
    /**
     * <p>adr020fax2 を取得します。
     * @return adr020fax2
     */
    public String getAdr020fax2() {
        return adr020fax2__;
    }
    /**
     * <p>adr020fax2 をセットします。
     * @param adr020fax2 adr020fax2
     */
    public void setAdr020fax2(String adr020fax2) {
        adr020fax2__ = adr020fax2;
    }
    /**
     * <p>adr020fax2Comment を取得します。
     * @return adr020fax2Comment
     */
    public String getAdr020fax2Comment() {
        return adr020fax2Comment__;
    }
    /**
     * <p>adr020fax2Comment をセットします。
     * @param adr020fax2Comment adr020fax2Comment
     */
    public void setAdr020fax2Comment(String adr020fax2Comment) {
        adr020fax2Comment__ = adr020fax2Comment;
    }
    /**
     * <p>adr020fax3 を取得します。
     * @return adr020fax3
     */
    public String getAdr020fax3() {
        return adr020fax3__;
    }
    /**
     * <p>adr020fax3 をセットします。
     * @param adr020fax3 adr020fax3
     */
    public void setAdr020fax3(String adr020fax3) {
        adr020fax3__ = adr020fax3;
    }
    /**
     * <p>adr020fax3Comment を取得します。
     * @return adr020fax3Comment
     */
    public String getAdr020fax3Comment() {
        return adr020fax3Comment__;
    }
    /**
     * <p>adr020fax3Comment をセットします。
     * @param adr020fax3Comment adr020fax3Comment
     */
    public void setAdr020fax3Comment(String adr020fax3Comment) {
        adr020fax3Comment__ = adr020fax3Comment;
    }
    /**
     * <p>adr020label を取得します。
     * @return adr020label
     */
    public String[] getAdr020label() {
        return adr020label__;
    }
    /**
     * <p>adr020label をセットします。
     * @param adr020label adr020label
     */
    public void setAdr020label(String[] adr020label) {
        adr020label__ = adr020label;
    }
    /**
     * <p>adr020mail1 を取得します。
     * @return adr020mail1
     */
    public String getAdr020mail1() {
        return adr020mail1__;
    }
    /**
     * <p>adr020mail1 をセットします。
     * @param adr020mail1 adr020mail1
     */
    public void setAdr020mail1(String adr020mail1) {
        adr020mail1__ = adr020mail1;
    }
    /**
     * <p>adr020mail1Comment を取得します。
     * @return adr020mail1Comment
     */
    public String getAdr020mail1Comment() {
        return adr020mail1Comment__;
    }
    /**
     * <p>adr020mail1Comment をセットします。
     * @param adr020mail1Comment adr020mail1Comment
     */
    public void setAdr020mail1Comment(String adr020mail1Comment) {
        adr020mail1Comment__ = adr020mail1Comment;
    }
    /**
     * <p>adr020mail2 を取得します。
     * @return adr020mail2
     */
    public String getAdr020mail2() {
        return adr020mail2__;
    }
    /**
     * <p>adr020mail2 をセットします。
     * @param adr020mail2 adr020mail2
     */
    public void setAdr020mail2(String adr020mail2) {
        adr020mail2__ = adr020mail2;
    }
    /**
     * <p>adr020mail2Comment を取得します。
     * @return adr020mail2Comment
     */
    public String getAdr020mail2Comment() {
        return adr020mail2Comment__;
    }
    /**
     * <p>adr020mail2Comment をセットします。
     * @param adr020mail2Comment adr020mail2Comment
     */
    public void setAdr020mail2Comment(String adr020mail2Comment) {
        adr020mail2Comment__ = adr020mail2Comment;
    }
    /**
     * <p>adr020mail3 を取得します。
     * @return adr020mail3
     */
    public String getAdr020mail3() {
        return adr020mail3__;
    }
    /**
     * <p>adr020mail3 をセットします。
     * @param adr020mail3 adr020mail3
     */
    public void setAdr020mail3(String adr020mail3) {
        adr020mail3__ = adr020mail3;
    }
    /**
     * <p>adr020mail3Comment を取得します。
     * @return adr020mail3Comment
     */
    public String getAdr020mail3Comment() {
        return adr020mail3Comment__;
    }
    /**
     * <p>adr020mail3Comment をセットします。
     * @param adr020mail3Comment adr020mail3Comment
     */
    public void setAdr020mail3Comment(String adr020mail3Comment) {
        adr020mail3Comment__ = adr020mail3Comment;
    }
    /**
     * <p>adr020permitEdit を取得します。
     * @return adr020permitEdit
     */
    public int getAdr020permitEdit() {
        return adr020permitEdit__;
    }
    /**
     * <p>adr020permitEdit をセットします。
     * @param adr020permitEdit adr020permitEdit
     */
    public void setAdr020permitEdit(int adr020permitEdit) {
        adr020permitEdit__ = adr020permitEdit;
    }
    /**
     * <p>adr020permitEditGroup を取得します。
     * @return adr020permitEditGroup
     */
    public String[] getAdr020permitEditGroup() {
        return adr020permitEditGroup__;
    }
    /**
     * <p>adr020permitEditGroup をセットします。
     * @param adr020permitEditGroup adr020permitEditGroup
     */
    public void setAdr020permitEditGroup(String[] adr020permitEditGroup) {
        adr020permitEditGroup__ = adr020permitEditGroup;
    }
    /**
     * <p>adr020permitEditUser を取得します。
     * @return adr020permitEditUser
     */
    public String[] getAdr020permitEditUser() {
        return adr020permitEditUser__;
    }
    /**
     * <p>adr020permitEditUser をセットします。
     * @param adr020permitEditUser adr020permitEditUser
     */
    public void setAdr020permitEditUser(String[] adr020permitEditUser) {
        adr020permitEditUser__ = adr020permitEditUser;
    }
    /**
     * <p>adr020permitEditUserGroup を取得します。
     * @return adr020permitEditUserGroup
     */
    public int getAdr020permitEditUserGroup() {
        return adr020permitEditUserGroup__;
    }
    /**
     * <p>adr020permitEditUserGroup をセットします。
     * @param adr020permitEditUserGroup adr020permitEditUserGroup
     */
    public void setAdr020permitEditUserGroup(int adr020permitEditUserGroup) {
        adr020permitEditUserGroup__ = adr020permitEditUserGroup;
    }
    /**
     * <p>adr020permitView を取得します。
     * @return adr020permitView
     */
    public int getAdr020permitView() {
        return adr020permitView__;
    }
    /**
     * <p>adr020permitView をセットします。
     * @param adr020permitView adr020permitView
     */
    public void setAdr020permitView(int adr020permitView) {
        adr020permitView__ = adr020permitView;
    }
    /**
     * <p>adr020permitViewGroup を取得します。
     * @return adr020permitViewGroup
     */
    public String[] getAdr020permitViewGroup() {
        return adr020permitViewGroup__;
    }
    /**
     * <p>adr020permitViewGroup をセットします。
     * @param adr020permitViewGroup adr020permitViewGroup
     */
    public void setAdr020permitViewGroup(String[] adr020permitViewGroup) {
        adr020permitViewGroup__ = adr020permitViewGroup;
    }
    /**
     * <p>adr020permitViewUser を取得します。
     * @return adr020permitViewUser
     */
    public String[] getAdr020permitViewUser() {
        return adr020permitViewUser__;
    }
    /**
     * <p>adr020permitViewUser をセットします。
     * @param adr020permitViewUser adr020permitViewUser
     */
    public void setAdr020permitViewUser(String[] adr020permitViewUser) {
        adr020permitViewUser__ = adr020permitViewUser;
    }
    /**
     * <p>adr020permitViewUserGroup を取得します。
     * @return adr020permitViewUserGroup
     */
    public int getAdr020permitViewUserGroup() {
        return adr020permitViewUserGroup__;
    }
    /**
     * <p>adr020permitViewUserGroup をセットします。
     * @param adr020permitViewUserGroup adr020permitViewUserGroup
     */
    public void setAdr020permitViewUserGroup(int adr020permitViewUserGroup) {
        adr020permitViewUserGroup__ = adr020permitViewUserGroup;
    }
    /**
     * <p>adr020position を取得します。
     * @return adr020position
     */
    public int getAdr020position() {
        return adr020position__;
    }
    /**
     * <p>adr020position をセットします。
     * @param adr020position adr020position
     */
    public void setAdr020position(int adr020position) {
        adr020position__ = adr020position;
    }
    /**
     * <p>adr020postno1 を取得します。
     * @return adr020postno1
     */
    public String getAdr020postno1() {
        return adr020postno1__;
    }
    /**
     * <p>adr020postno1 をセットします。
     * @param adr020postno1 adr020postno1
     */
    public void setAdr020postno1(String adr020postno1) {
        adr020postno1__ = adr020postno1;
    }
    /**
     * <p>adr020postno2 を取得します。
     * @return adr020postno2
     */
    public String getAdr020postno2() {
        return adr020postno2__;
    }
    /**
     * <p>adr020postno2 をセットします。
     * @param adr020postno2 adr020postno2
     */
    public void setAdr020postno2(String adr020postno2) {
        adr020postno2__ = adr020postno2;
    }
    /**
     * <p>adr020selectCompany を取得します。
     * @return adr020selectCompany
     */
    public String getAdr020selectCompany() {
        return adr020selectCompany__;
    }
    /**
     * <p>adr020selectCompany をセットします。
     * @param adr020selectCompany adr020selectCompany
     */
    public void setAdr020selectCompany(String adr020selectCompany) {
        adr020selectCompany__ = adr020selectCompany;
    }
    /**
     * <p>adr020selectCompanyBase を取得します。
     * @return adr020selectCompanyBase
     */
    public String getAdr020selectCompanyBase() {
        return adr020selectCompanyBase__;
    }
    /**
     * <p>adr020selectCompanyBase をセットします。
     * @param adr020selectCompanyBase adr020selectCompanyBase
     */
    public void setAdr020selectCompanyBase(String adr020selectCompanyBase) {
        adr020selectCompanyBase__ = adr020selectCompanyBase;
    }
    /**
     * <p>adr020syozoku を取得します。
     * @return adr020syozoku
     */
    public String getAdr020syozoku() {
        return adr020syozoku__;
    }
    /**
     * <p>adr020syozoku をセットします。
     * @param adr020syozoku adr020syozoku
     */
    public void setAdr020syozoku(String adr020syozoku) {
        adr020syozoku__ = adr020syozoku;
    }
    /**
     * <p>adr020tantoGroup を取得します。
     * @return adr020tantoGroup
     */
    public int getAdr020tantoGroup() {
        return adr020tantoGroup__;
    }
    /**
     * <p>adr020tantoGroup をセットします。
     * @param adr020tantoGroup adr020tantoGroup
     */
    public void setAdr020tantoGroup(int adr020tantoGroup) {
        adr020tantoGroup__ = adr020tantoGroup;
    }
    /**
     * <p>adr020tantoList を取得します。
     * @return adr020tantoList
     */
    public String[] getAdr020tantoList() {
        return adr020tantoList__;
    }
    /**
     * <p>adr020tantoList をセットします。
     * @param adr020tantoList adr020tantoList
     */
    public void setAdr020tantoList(String[] adr020tantoList) {
        adr020tantoList__ = adr020tantoList;
    }
    /**
     * <p>adr020tel1 を取得します。
     * @return adr020tel1
     */
    public String getAdr020tel1() {
        return adr020tel1__;
    }
    /**
     * <p>adr020tel1 をセットします。
     * @param adr020tel1 adr020tel1
     */
    public void setAdr020tel1(String adr020tel1) {
        adr020tel1__ = adr020tel1;
    }
    /**
     * <p>adr020tel1Comment を取得します。
     * @return adr020tel1Comment
     */
    public String getAdr020tel1Comment() {
        return adr020tel1Comment__;
    }
    /**
     * <p>adr020tel1Comment をセットします。
     * @param adr020tel1Comment adr020tel1Comment
     */
    public void setAdr020tel1Comment(String adr020tel1Comment) {
        adr020tel1Comment__ = adr020tel1Comment;
    }
    /**
     * <p>adr020tel1Nai を取得します。
     * @return adr020tel1Nai
     */
    public String getAdr020tel1Nai() {
        return adr020tel1Nai__;
    }
    /**
     * <p>adr020tel1Nai をセットします。
     * @param adr020tel1Nai adr020tel1Nai
     */
    public void setAdr020tel1Nai(String adr020tel1Nai) {
        adr020tel1Nai__ = adr020tel1Nai;
    }
    /**
     * <p>adr020tel2 を取得します。
     * @return adr020tel2
     */
    public String getAdr020tel2() {
        return adr020tel2__;
    }
    /**
     * <p>adr020tel2 をセットします。
     * @param adr020tel2 adr020tel2
     */
    public void setAdr020tel2(String adr020tel2) {
        adr020tel2__ = adr020tel2;
    }
    /**
     * <p>adr020tel2Comment を取得します。
     * @return adr020tel2Comment
     */
    public String getAdr020tel2Comment() {
        return adr020tel2Comment__;
    }
    /**
     * <p>adr020tel2Comment をセットします。
     * @param adr020tel2Comment adr020tel2Comment
     */
    public void setAdr020tel2Comment(String adr020tel2Comment) {
        adr020tel2Comment__ = adr020tel2Comment;
    }
    /**
     * <p>adr020tel2Nai を取得します。
     * @return adr020tel2Nai
     */
    public String getAdr020tel2Nai() {
        return adr020tel2Nai__;
    }
    /**
     * <p>adr020tel2Nai をセットします。
     * @param adr020tel2Nai adr020tel2Nai
     */
    public void setAdr020tel2Nai(String adr020tel2Nai) {
        adr020tel2Nai__ = adr020tel2Nai;
    }
    /**
     * <p>adr020tel3 を取得します。
     * @return adr020tel3
     */
    public String getAdr020tel3() {
        return adr020tel3__;
    }
    /**
     * <p>adr020tel3 をセットします。
     * @param adr020tel3 adr020tel3
     */
    public void setAdr020tel3(String adr020tel3) {
        adr020tel3__ = adr020tel3;
    }
    /**
     * <p>adr020tel3Comment を取得します。
     * @return adr020tel3Comment
     */
    public String getAdr020tel3Comment() {
        return adr020tel3Comment__;
    }
    /**
     * <p>adr020tel3Comment をセットします。
     * @param adr020tel3Comment adr020tel3Comment
     */
    public void setAdr020tel3Comment(String adr020tel3Comment) {
        adr020tel3Comment__ = adr020tel3Comment;
    }
    /**
     * <p>adr020tel3Nai を取得します。
     * @return adr020tel3Nai
     */
    public String getAdr020tel3Nai() {
        return adr020tel3Nai__;
    }
    /**
     * <p>adr020tel3Nai をセットします。
     * @param adr020tel3Nai adr020tel3Nai
     */
    public void setAdr020tel3Nai(String adr020tel3Nai) {
        adr020tel3Nai__ = adr020tel3Nai;
    }
    /**
     * <p>adr020tdfk を取得します。
     * @return adr020tdfk
     */
    public int getAdr020tdfk() {
        return adr020tdfk__;
    }

    /**
     * <p>adr020tdfk をセットします。
     * @param adr020tdfk adr020tdfk
     */
    public void setAdr020tdfk(int adr020tdfk) {
        adr020tdfk__ = adr020tdfk;
    }

    /**
     * <p>adr020unameMei を取得します。
     * @return adr020unameMei
     */
    public String getAdr020unameMei() {
        return adr020unameMei__;
    }
    /**
     * <p>adr020unameMei をセットします。
     * @param adr020unameMei adr020unameMei
     */
    public void setAdr020unameMei(String adr020unameMei) {
        adr020unameMei__ = adr020unameMei;
    }
    /**
     * <p>adr020unameMeiKn を取得します。
     * @return adr020unameMeiKn
     */
    public String getAdr020unameMeiKn() {
        return adr020unameMeiKn__;
    }
    /**
     * <p>adr020unameMeiKn をセットします。
     * @param adr020unameMeiKn adr020unameMeiKn
     */
    public void setAdr020unameMeiKn(String adr020unameMeiKn) {
        adr020unameMeiKn__ = adr020unameMeiKn;
    }
    /**
     * <p>adr020unameSei を取得します。
     * @return adr020unameSei
     */
    public String getAdr020unameSei() {
        return adr020unameSei__;
    }
    /**
     * <p>adr020unameSei をセットします。
     * @param adr020unameSei adr020unameSei
     */
    public void setAdr020unameSei(String adr020unameSei) {
        adr020unameSei__ = adr020unameSei;
    }
    /**
     * <p>adr020unameSeiKn を取得します。
     * @return adr020unameSeiKn
     */
    public String getAdr020unameSeiKn() {
        return adr020unameSeiKn__;
    }
    /**
     * <p>adr020unameSeiKn をセットします。
     * @param adr020unameSeiKn adr020unameSeiKn
     */
    public void setAdr020unameSeiKn(String adr020unameSeiKn) {
        adr020unameSeiKn__ = adr020unameSeiKn;
    }

    /**
     * <p>adr020init を取得します。
     * @return adr020init
     */
    public int getAdr020init() {
        return adr020init__;
    }

    /**
     * <p>adr020init をセットします。
     * @param adr020init adr020init
     */
    public void setAdr020init(int adr020init) {
        adr020init__ = adr020init;
    }
    /**
     * <p>adr020tantoListUI を取得します。
     * @return adr020tantoListUI
     */
    public UserGroupSelector getAdr020tantoListUI() {
        return adr020tantoListUI__;
    }

    /**
     * <p>adr020tantoListUI をセットします。
     * @param adr020tantoListUI adr020tantoListUI
     */
    public void setAdr020tantoListUI(UserGroupSelector adr020tantoListUI) {
        adr020tantoListUI__ = adr020tantoListUI;
    }

    /**
     * <p>adr020permitViewGroupUI を取得します。
     * @return adr020permitViewGroupUI
     */
    public UserGroupSelector getAdr020permitViewGroupUI() {
        return adr020permitViewGroupUI__;
    }

    /**
     * <p>adr020permitViewGroupUI をセットします。
     * @param adr020permitViewGroupUI adr020permitViewGroupUI
     */
    public void setAdr020permitViewGroupUI(
            UserGroupSelector adr020permitViewGroupUI) {
        adr020permitViewGroupUI__ = adr020permitViewGroupUI;
    }

    /**
     * <p>adr020permitViewUserUI を取得します。
     * @return adr020permitViewUserUI
     */
    public UserGroupSelector getAdr020permitViewUserUI() {
        return adr020permitViewUserUI__;
    }

    /**
     * <p>adr020permitViewUserUI をセットします。
     * @param adr020permitViewUserUI adr020permitViewUserUI
     */
    public void setAdr020permitViewUserUI(
            UserGroupSelector adr020permitViewUserUI) {
        adr020permitViewUserUI__ = adr020permitViewUserUI;
    }

    /**
     * <p>adr020permitEditGroupUI を取得します。
     * @return adr020permitEditGroupUI
     */
    public UserGroupSelector getAdr020permitEditGroupUI() {
        return adr020permitEditGroupUI__;
    }

    /**
     * <p>adr020permitEditGroupUI をセットします。
     * @param adr020permitEditGroupUI adr020permitEditGroupUI
     */
    public void setAdr020permitEditGroupUI(
            UserGroupSelector adr020permitEditGroupUI) {
        adr020permitEditGroupUI__ = adr020permitEditGroupUI;
    }

    /**
     * <p>adr020permitEditUserUI を取得します。
     * @return adr020permitEditUserUI
     */
    public UserGroupSelector getAdr020permitEditUserUI() {
        return adr020permitEditUserUI__;
    }

    /**
     * <p>adr020permitEditUserUI をセットします。
     * @param adr020permitEditUserUI adr020permitEditUserUI
     */
    public void setAdr020permitEditUserUI(
            UserGroupSelector adr020permitEditUserUI) {
        adr020permitEditUserUI__ = adr020permitEditUserUI;
    }

    /**
     * <p>selectPermitEditGroup を取得します。
     * @return selectPermitEditGroup
     */
    public List<LabelValueBean> getSelectPermitEditGroup() {
        return selectPermitEditGroup__;
    }

    /**
     * <p>selectPermitEditGroup をセットします。
     * @param selectPermitEditGroup selectPermitEditGroup
     */
    public void setSelectPermitEditGroup(List<LabelValueBean> selectPermitEditGroup) {
        selectPermitEditGroup__ = selectPermitEditGroup;
    }

    /**
     * <p>selectPermitEditUser を取得します。
     * @return selectPermitEditUser
     */
    public List<UsrLabelValueBean> getSelectPermitEditUser() {
        return selectPermitEditUser__;
    }

    /**
     * <p>selectPermitEditUser をセットします。
     * @param selectPermitEditUser selectPermitEditUser
     */
    public void setSelectPermitEditUser(List<UsrLabelValueBean> selectPermitEditUser) {
        selectPermitEditUser__ = selectPermitEditUser;
    }

    /**
     * <p>selectPermitViewGroup を取得します。
     * @return selectPermitViewGroup
     */
    public List<LabelValueBean> getSelectPermitViewGroup() {
        return selectPermitViewGroup__;
    }

    /**
     * <p>selectPermitViewGroup をセットします。
     * @param selectPermitViewGroup selectPermitViewGroup
     */
    public void setSelectPermitViewGroup(List<LabelValueBean> selectPermitViewGroup) {
        selectPermitViewGroup__ = selectPermitViewGroup;
    }

    /**
     * <p>selectPermitViewUser を取得します。
     * @return selectPermitViewUser
     */
    public List<UsrLabelValueBean> getSelectPermitViewUser() {
        return selectPermitViewUser__;
    }

    /**
     * <p>selectPermitViewUser をセットします。
     * @param selectPermitViewUser selectPermitViewUser
     */
    public void setSelectPermitViewUser(List<UsrLabelValueBean> selectPermitViewUser) {
        selectPermitViewUser__ = selectPermitViewUser;
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
     * <p>adr020companyName を取得します。
     * @return adr020companyName
     */
    public String getAdr020companyName() {
        return adr020companyName__;
    }

    /**
     * <p>adr020companyName をセットします。
     * @param adr020companyName adr020companyName
     */
    public void setAdr020companyName(String adr020companyName) {
        adr020companyName__ = adr020companyName;
    }

    /**
     * <p>adr020companyBaseName を取得します。
     * @return adr020companyBaseName
     */
    public String getAdr020companyBaseName() {
        return adr020companyBaseName__;
    }

    /**
     * <p>adr020companyBaseName をセットします。
     * @param adr020companyBaseName adr020companyBaseName
     */
    public void setAdr020companyBaseName(String adr020companyBaseName) {
        adr020companyBaseName__ = adr020companyBaseName;
    }

    /**
     * <p>adr020companyCode を取得します。
     * @return adr020companyCode
     */
    public String getAdr020companyCode() {
        return adr020companyCode__;
    }

    /**
     * <p>adr020companyCode をセットします。
     * @param adr020companyCode adr020companyCode
     */
    public void setAdr020companyCode(String adr020companyCode) {
        adr020companyCode__ = adr020companyCode;
    }

    /**
     * <p>adr020deleteLabel を取得します。
     * @return adr020deleteLabel
     */
    public String getAdr020deleteLabel() {
        return adr020deleteLabel__;
    }

    /**
     * <p>adr020deleteLabel をセットします。
     * @param adr020deleteLabel adr020deleteLabel
     */
    public void setAdr020deleteLabel(String adr020deleteLabel) {
        adr020deleteLabel__ = adr020deleteLabel;
    }

    /**
     * adr020addPositionBtnFlgを取得します。
     * @return adr020addPositionBtnFlg
     */
    public int getAdr020addPositionBtnFlg() {
        return adr020addPositionBtnFlg__;
    }

    /**
     * adr020addPositionBtnFlgをセットします。
     * @param adr020addPositionBtnFlg adr020addPositionBtnFlg
     * */
    public void setAdr020addPositionBtnFlg(int adr020addPositionBtnFlg) {
        adr020addPositionBtnFlg__ = adr020addPositionBtnFlg;
    }

    /**
     * <p>adr020addCompanyBtnFlg を取得します。
     * @return adr020addCompanyBtnFlg
     */
    public int getAdr020addCompanyBtnFlg() {
        return adr020addCompanyBtnFlg__;
    }

    /**
     * <p>adr020addCompanyBtnFlg をセットします。
     * @param adr020addCompanyBtnFlg adr020addCompanyBtnFlg
     */
    public void setAdr020addCompanyBtnFlg(int adr020addCompanyBtnFlg) {
        adr020addCompanyBtnFlg__ = adr020addCompanyBtnFlg;
    }

    /**
     * <p>adr020addLabelBtnFlg を取得します。
     * @return adr020addLabelBtnFlg
     */
    public int getAdr020addLabelBtnFlg() {
        return adr020addLabelBtnFlg__;
    }

    /**
     * <p>adr020addLabelBtnFlg をセットします。
     * @param adr020addLabelBtnFlg adr020addLabelBtnFlg
     */
    public void setAdr020addLabelBtnFlg(int adr020addLabelBtnFlg) {
        adr020addLabelBtnFlg__ = adr020addLabelBtnFlg;
    }

    /**
     * <p>adr020searchUse を取得します。
     * @return adr020searchUse
     */
    public int getAdr020searchUse() {
        return adr020searchUse__;
    }

    /**
     * <p>adr020searchUse をセットします。
     * @param adr020searchUse adr020searchUse
     */
    public void setAdr020searchUse(int adr020searchUse) {
        adr020searchUse__ = adr020searchUse;
    }

    /**
     * @return adr020CompanySid
     */
    public String[] getAdr020CompanySid() {
        return adr020CompanySid__;
    }

    /**
     * @param adr020CompanySid 設定する adr020CompanySid
     */
    public void setAdr020CompanySid(String[] adr020CompanySid) {
        adr020CompanySid__ = adr020CompanySid;
    }

    /**
     * @return adr020CompanyBaseSid
     */
    public String[] getAdr020CompanyBaseSid() {
        return adr020CompanyBaseSid__;
    }

    /**
     * @param adr020CompanyBaseSid 設定する adr020CompanyBaseSid
     */
    public void setAdr020CompanyBaseSid(String[] adr020CompanyBaseSid) {
        adr020CompanyBaseSid__ = adr020CompanyBaseSid;
    }

    /**
     * <p>adrCopyFlg を取得します。
     * @return adrCopyFlg
     */
    public int getAdrCopyFlg() {
        return adrCopyFlg__;
    }

    /**
     * <p>adrCopyFlg をセットします。
     * @param adrCopyFlg adrCopyFlg
     */
    public void setAdrCopyFlg(int adrCopyFlg) {
        adrCopyFlg__ = adrCopyFlg;
    }

    /**
     * <p>adr020webmail を取得します。
     * @return adr020webmail
     */
    public int getAdr020webmail() {
        return adr020webmail__;
    }

    /**
     * <p>adr020webmail をセットします。
     * @param adr020webmail adr020webmail
     */
    public void setAdr020webmail(int adr020webmail) {
        adr020webmail__ = adr020webmail;
    }

    /**
     * <p>adr020webmailId を取得します。
     * @return adr020webmailId
     */
    public long getAdr020webmailId() {
        return adr020webmailId__;
    }

    /**
     * <p>adr020webmailId をセットします。
     * @param adr020webmailId adr020webmailId
     */
    public void setAdr020webmailId(long adr020webmailId) {
        adr020webmailId__ = adr020webmailId;
    }
}