package jp.groupsession.v2.rng.rng090;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.formbuilder.FormBuilder;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngCategoriCantAccessException;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RngValidate;
import jp.groupsession.v2.rng.rng060.Rng060Form;
import jp.groupsession.v2.rng.rng110keiro.Rng110Keiro;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 テンプレート登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng090Form extends Rng060Form {

    /** テンプレート名 */
    private String rng090title__ = null;
    /** 稟議タイトル */
    private String rng090rngTitle__ = null;
    /** 内容 */
    private String rng090content__ = null;
    /** 備考 */
    private String rng090biko__ = null;
    /** カテゴリSID */
    private int rng090CatSid__ = 0;
    /** ユーザSID */
    private int rng090UserSid__ = 0;

    /** 添付ファイル */
    private String[] rng090files__ = null;

    /** ファイルコンボ */
    private List < LabelValueBean > rng090FileLabelList__ = null;

    /** 経路テンプレートの使用*/
    private int rng090useKeiroTemplate__ = 1;

    /** 経路テンプレート名*/
    private String rng090KeiroTemplateName__ = "";
    /** 経路テンプレートSID*/
    private int rng090KeiroTemplateSid__ = 0;
    /** 経路テンプレートユーザSID*/
    private int rng090KeiroTemplateUsrSid__ = -1;

    /** ID設定区分 0: 全稟議で共通 1:テンプレートごとに設定 2:使用しない*/
    private int idSelectable__;
    /** ID手動入力設定  1:テンプレートごとに設定　0: 全稟議で共通*/
    private int idPrefManualEditable__;
    /** 申請IDSID*/
    private int rng090idSid__ = 0;
    /** 申請IDタイトル*/
    private String rng090idTitle__;
    /** 申請ID 手動入力設定*/
    private int rng090idPrefManual__ = 1;
    /** 申請ID 重複登録設定*/
    private int rng090idPrefOverrap__ = 1;
    /** 添付ファイル変更フラグ*/
    private int flgFileChange__ = 0;
    /** アクセス日時*/
    private String accessDateString__;
    /** 稟議テンプレート 仕様バージョン*/
    private int rng090rtpSpecVer__ = RngConst.RNG_RTP_SPEC_VER_A480;
    /** 経路バージョン*/
    private int rng090KeiroVer__ = 0;

    //表示項目
    /** カテゴリ一覧 */
    private List<LabelValueBean> rng090CategoryList__ = null;
    /** 経路設定*/
    private Rng110Keiro rng090keiro__ = new Rng110Keiro();

    /** テンプレート設定*/
    private FormBuilder rng090template__ = new FormBuilder();

    /** テンプレート設定結果JSON文字列*/
    private String rng090templateJSON__;

    /**
     * <p>rng090idSid を取得します。
     * @return rng090idSid
     */
    public int getRng090idSid() {
        return rng090idSid__;
    }
    /**
     * <p>rng090idSid をセットします。
     * @param rng090idSid rng090idSid
     */
    public void setRng090idSid(int rng090idSid) {
        rng090idSid__ = rng090idSid;
    }
    /**
     * <p>rng090idTitle を取得します。
     * @return rng090idTitle
     */
    public String getRng090idTitle() {
        return rng090idTitle__;
    }
    /**
     * <p>rng090idTitle をセットします。
     * @param rng090idTitle rng090idTitle
     */
    public void setRng090idTitle(String rng090idTitle) {
        rng090idTitle__ = rng090idTitle;
    }
    /**
     * <p>rng090idPrefManual を取得します。
     * @return rng090idPrefManual
     */
    public int getRng090idPrefManual() {
        return rng090idPrefManual__;
    }
    /**
     * <p>rng090idPrefManual をセットします。
     * @param rng090idPrefManual rng090idPrefManual
     */
    public void setRng090idPrefManual(int rng090idPrefManual) {
        rng090idPrefManual__ = rng090idPrefManual;
    }
    /**
     * <p>rng090idPrefOverrap を取得します。
     * @return rng090idPrefOverrap
     */
    public int getRng090idPrefOverrap() {
        return rng090idPrefOverrap__;
    }
    /**
     * <p>rng090idPrefOverrap をセットします。
     * @param rng090idPrefOverrap rng090idPrefOverrap
     */
    public void setRng090idPrefOverrap(int rng090idPrefOverrap) {
        rng090idPrefOverrap__ = rng090idPrefOverrap;
    }


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("rng.10");
        String title = gsMsg.getMessage("cmn.title");
        String format = gsMsg.getMessage("rng.12");
        //カテゴリアクセス権限例外用のエラー場面設定を用意
        RngCategoriCantAccessException rcaException = new RngCategoriCantAccessException();
        rcaException.setSeigenKbn(RngCategoriCantAccessException.SEIGEN_KBN_AUTH);
        rcaException.setCantActionStr(gsMsg.getMessage("cmn.edit"));
        //カテゴリアクセス権限
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), RngConst.PLUGIN_ID_RINGI);
        Rng090Biz rng090Biz = new Rng090Biz(con, reqMdl);
        int tFlg = getRngTemplateMode();
        if (!rng090Biz.categoriAuthChk(tFlg, getRng090CatSid(), adminUser)) {
            ActionMessage acmsg = new ActionMessage("error.edit.power.user",
                    gsMsg.getMessage("rng.113"),
                    gsMsg.getMessage("cmn.entry"));
            errors.add("error.edit.power.user", acmsg);
            return errors;
        }

        //テンプレート名
        errors = RngValidate.validateCmnFieldText(
                errors,             //errors
                msg,         //エラーメッセージ表示テキスト
                rng090title__,      //チェックするフィールド
                "rng090title__",    //チェックするフィールドの文字列
                100,                //最大桁数
                true);             //入力必須か

        //-- タイトルチェック --
        errors = RngValidate.validateCmnFieldText(
                                                errors,
                                                title,
                                                rng090rngTitle__,
                                                "rng090rngTitle",
                                                RngConst.MAX_LENGTH_TITLE,
                                                false);
        if (getRng090rtpSpecVer() == RngConst.RNG_RTP_SPEC_VER_INIT) {
            //フォーマット
            errors = RngValidate.validateFieldTextArea(
                    errors,
                    format,
                    rng090content__,
                    "rng090content__",
                    1000,
                    true);
        } else {
            FormBuilder fb = getRng090template();
            ValidateInfo vi = new ValidateInfo();
            vi.setTitle(gsMsg.getMessage("rng.rng090.01"));
            vi.setRequire(1);
            fb.validateCheck(errors, reqMdl, vi);


        }

        if (getRng090useKeiroTemplate() == 0 && getRng090KeiroTemplateSid() == 0) {
            ActionMessage acmsg = new ActionMessage("error.select.required.text",
                    gsMsg.getMessage("rng.25"));
            errors.add("error.select.required.text", acmsg);
        }
        if (getRng090useKeiroTemplate() == 1) {
            getRng090keiro().validateCheck(reqMdl, errors, rng090rtpSpecVer__, false);
        }

        //備考
        errors = RngValidate.validateFieldTextArea(
                errors,
                gsMsg.getMessage("cmn.memo"),
                rng090biko__,
                "rng090biko__",
                1000,
                false);

        return errors;
    }
    /**
     * <p>rng090content を取得します。
     * @return rng090content
     */
    public String getRng090content() {
        return rng090content__;
    }

    /**
     * <p>rng090content をセットします。
     * @param rng090content rng090content
     */
    public void setRng090content(String rng090content) {
        rng090content__ = rng090content;
    }

    /**
     * <p>rng090FileLabelList を取得します。
     * @return rng090FileLabelList
     */
    public List<LabelValueBean> getRng090FileLabelList() {
        return rng090FileLabelList__;
    }

    /**
     * <p>rng090FileLabelList をセットします。
     * @param rng090FileLabelList rng090FileLabelList
     */
    public void setRng090FileLabelList(List<LabelValueBean> rng090FileLabelList) {
        rng090FileLabelList__ = rng090FileLabelList;
    }

    /**
     * <p>rng090files を取得します。
     * @return rng090files
     */
    public String[] getRng090files() {
        return rng090files__;
    }

    /**
     * <p>rng090files をセットします。
     * @param rng090files rng090files
     */
    public void setRng090files(String[] rng090files) {
        rng090files__ = rng090files;
    }

    /**
     * <p>rng090title を取得します。
     * @return rng090title
     */
    public String getRng090title() {
        return rng090title__;
    }

    /**
     * <p>rng090title をセットします。
     * @param rng090title rng090title
     */
    public void setRng090title(String rng090title) {
        rng090title__ = rng090title;
    }
    /**
     * <p>rng090rngTitle を取得します。
     * @return rng090rngTitle
     */
    public String getRng090rngTitle() {
        return rng090rngTitle__;
    }
    /**
     * <p>rng090rngTitle をセットします。
     * @param rng090rngTitle rng090rngTitle
     */
    public void setRng090rngTitle(String rng090rngTitle) {
        rng090rngTitle__ = rng090rngTitle;
    }
    /**
     * @return rng090CategoryList
     */
    public List<LabelValueBean> getRng090CategoryList() {
        return rng090CategoryList__;
    }
    /**
     * @param rng090CategoryList 設定する rng090CategoryList
     */
    public void setRng090CategoryList(List<LabelValueBean> rng090CategoryList) {
        rng090CategoryList__ = rng090CategoryList;
    }
    /**
     * @return rng090CatSid
     */
    public int getRng090CatSid() {
        return rng090CatSid__;
    }
    /**
     * @param rng090CatSid 設定する rng090CatSid
     */
    public void setRng090CatSid(int rng090CatSid) {
        rng090CatSid__ = rng090CatSid;
    }
    /**
     * <p>rng090UserSid を取得します。
     * @return rng090UserSid
     */
    public int getRng090UserSid() {
        return rng090UserSid__;
    }
    /**
     * <p>rng090UserSid をセットします。
     * @param rng090UserSid rng090UserSid
     */
    public void setRng090UserSid(int rng090UserSid) {
        rng090UserSid__ = rng090UserSid;
    }
    /**
     * <p>rng090keiro を取得します。
     * @return rng090keiro
     */
    public Rng110Keiro getRng090keiro() {
        return rng090keiro__;
    }
    /**
     * <p>rng090keiro をセットします。
     * @param rng090keiro rng090keiro
     */
    public void setRng090keiro(Rng110Keiro rng090keiro) {
        rng090keiro__ = rng090keiro;
    }
    /**
     * <p>rng090template を取得します。
     * @return rng090template
     */
    public FormBuilder getRng090template() {
        return rng090template__;
    }
    /**
     * <p>rng090template をセットします。
     * @param rng090template rng090template
     */
    public void setRng090template(FormBuilder rng090template) {
        rng090template__ = rng090template;
    }
    /**
     * <p>rng090templateJSON を取得します。
     * @return rng090templateJSON
     */
    public String getRng090templateJSON() {
        return rng090templateJSON__;
    }
    /**
     * <p>rng090templateJSON をセットします。
     * @param rng090templateJSON rng090templateJSON
     */
    public void setRng090templateJSON(String rng090templateJSON) {
        rng090templateJSON__ = rng090templateJSON;
    }

    /**
     * <p>rng090useKeiroTemplate を取得します。
     * @return rng090useKeiroTemplate
     */
    public int getRng090useKeiroTemplate() {
        return rng090useKeiroTemplate__;
    }
    /**
     * <p>rng090useKeiroTemplate をセットします。
     * @param rng090useKeiroTemplate rng090useKeiroTemplate
     */
    public void setRng090useKeiroTemplate(int rng090useKeiroTemplate) {
        rng090useKeiroTemplate__ = rng090useKeiroTemplate;
    }
    /**
     * <p>rng090KeiroTemplateSid を取得します。
     * @return rng090KeiroTemplateSid
     */
    public int getRng090KeiroTemplateSid() {
        return rng090KeiroTemplateSid__;
    }
    /**
     * <p>rng090KeiroTemplateSid をセットします。
     * @param rng090KeiroTemplateSid rng090KeiroTemplateSid
     */
    public void setRng090KeiroTemplateSid(int rng090KeiroTemplateSid) {
        rng090KeiroTemplateSid__ = rng090KeiroTemplateSid;
    }
    /**
     * <p>rng090KeiroTemplateUsrSid を取得します。
     * @return rng090KeiroTemplateUsrSid
     */
    public int getRng090KeiroTemplateUsrSid() {
        return rng090KeiroTemplateUsrSid__;
    }
    /**
     * <p>rng090KeiroTemplateUsrSid をセットします。
     * @param rng090KeiroTemplateUsrSid rng090KeiroTemplateUsrSid
     */
    public void setRng090KeiroTemplateUsrSid(int rng090KeiroTemplateUsrSid) {
        rng090KeiroTemplateUsrSid__ = rng090KeiroTemplateUsrSid;
    }
    /**
     * <p>rng090KeiroTemplateName を取得します。
     * @return rng090KeiroTemplateName
     */
    public String getRng090KeiroTemplateName() {
        return rng090KeiroTemplateName__;
    }
    /**
     * <p>rng090KeiroTemplateName をセットします。
     * @param rng090KeiroTemplateName rng090KeiroTemplateName
     */
    public void setRng090KeiroTemplateName(String rng090KeiroTemplateName) {
        rng090KeiroTemplateName__ = rng090KeiroTemplateName;
    }
    /**
     * <p>rng090biko を取得します。
     * @return rng090biko
     */
    public String getRng090biko() {
        return rng090biko__;
    }
    /**
     * <p>rng090biko をセットします。
     * @param rng090biko rng090biko
     */
    public void setRng090biko(String rng090biko) {
        rng090biko__ = rng090biko;
    }
    /**
     * <p>idSelectable を取得します。
     * @return idSelectable
     */
    public int getIdSelectable() {
        return idSelectable__;
    }
    /**
     * <p>idSelectable をセットします。
     * @param idSelectable idSelectable
     */
    public void setIdSelectable(int idSelectable) {
        idSelectable__ = idSelectable;
    }
    /**
     * <p>idPrefManualEditable を取得します。
     * @return idPrefManualEditable
     */
    public int getIdPrefManualEditable() {
        return idPrefManualEditable__;
    }
    /**
     * <p>idPrefManualEditable をセットします。
     * @param idPrefManualEditable idPrefManualEditable
     */
    public void setIdPrefManualEditable(int idPrefManualEditable) {
        idPrefManualEditable__ = idPrefManualEditable;
    }
    /**
     * <p>flgFileChange を取得します。
     * @return flgFileChange
     */
    public int getFlgFileChange() {
        return flgFileChange__;
    }
    /**
     * <p>flgFileChange をセットします。
     * @param flgFileChange flgFileChange
     */
    public void setFlgFileChange(int flgFileChange) {
        flgFileChange__ = flgFileChange;
    }
    /**
     * <p>accessDateString を取得します。
     * @return accessDateString
     */
    public String getAccessDateString() {
        return accessDateString__;
    }
    /**
     * <p>accessDateString をセットします。
     * @param accessDateString accessDateString
     */
    public void setAccessDateString(String accessDateString) {
        accessDateString__ = accessDateString;
    }
    /**
     * <p>rng090rtpSpecVer を取得します。
     * @return rng090rtpSpecVer
     */
    public int getRng090rtpSpecVer() {
        return rng090rtpSpecVer__;
    }
    /**
     * <p>rng090rtpSpecVer をセットします。
     * @param rng090rtpSpecVer rng090rtpSpecVer
     */
    public void setRng090rtpSpecVer(int rng090rtpSpecVer) {
        this.rng090rtpSpecVer__ = rng090rtpSpecVer;
    }

    /**
     * <p>rng090KeiroVer を取得します。
     * @return rng090KeiroVer
     */
    public int getRng090KeiroVer() {
        return rng090KeiroVer__;
    }

    /**
     * <p>rng090KeiroVer をセットします。
     * @param rng090KeiroVer rng090KeiroVer
     */
    public void setRng090KeiroVer(int rng090KeiroVer) {
        this.rng090KeiroVer__ = rng090KeiroVer;
    }
}
