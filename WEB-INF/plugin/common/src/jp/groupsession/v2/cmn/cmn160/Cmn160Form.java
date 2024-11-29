package jp.groupsession.v2.cmn.cmn160;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 企業情報画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn160Form extends AbstractGsForm {

    /** 初期フラグ */
    private int cmn160InitFlg__ = 0;

    /** 会社名 */
    private String cmn160ComName__ = null;
    /** 会社名(カナ) */
    private String cmn160ComNamekn__ = null;
    /** URL */
    private String cmn160Url__ = null;
    /** 期首月 */
    private int cmn160Kisyu__ = GSConstCommon.DEFAULT_KISYU;
    /** 備考 */
    private String cmn160Biko__ = null;

    /** 月コンボ */
    private List<LabelValueBean> cmn160MonthList__ = null;

    /** ログイン画面 ロゴ画像バイナリSID */
    private Long cmn160BinSid__ = new Long(0);
    /** ログイン画面 ロゴファイル名 */
    private String cmn160LogoName__ = null;
    /** ログイン画面 ロゴファイル保存名 */
    private String cmn160LogoSaveName__ = null;
    /** ログイン画面 テンポラリディレクトリセットフラグ 0:未セット 1:セット済 */
    private int cmn160TempSetFlg__ = 0;
    /** ログイン画面 画像表示区分 0:デフォルトロゴ 1:オリジナルロゴ */
    private int cmn160DspLogoKbn__ = 0;
    /** ログイン画面 画像表示区分(DBから取得) 0:デフォルトロゴ 1:オリジナルロゴ */
    private int cmn160DbLogoKbn__ = 0;

    /** メニュー ロゴ画像バイナリSID */
    private Long cmn160MenuBinSid__ = new Long(0);
    /** メニュー ロゴファイル名 */
    private String cmn160MenuLogoName__ = null;
    /** メニュー ロゴファイル保存名 */
    private String cmn160MenuLogoSaveName__ = null;
    /** メニュー テンポラリディレクトリセットフラグ 0:未セット 1:セット済 */
    private int cmn160MenuTempSetFlg__ = 0;
    /** メニュー 画像表示区分 0:デフォルトロゴ 1:オリジナルロゴ */
    private int cmn160MenuDspLogoKbn__ = 0;
    /** メニュー 画像表示区分(DBから取得) 0:デフォルトロゴ 1:オリジナルロゴ */
    private int cmn160MenuDbLogoKbn__ = 0;

    /** 登録区分 0:新規登録 1:編集 */
    private int cmn160TourokuKbn__ = GSConstCommon.MODE_ADD;

    /** ログイン画面 ロゴ削除実行フラグ */
    private boolean cmn160DelExeFlg__ = false;
    /** メニュー ロゴ削除実行フラグ */
    private boolean cmn160MenuDelExeFlg__ = false;

    /** テーマパス */
    private String cmn160ThemePath__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param tempDirLogo テンポラリディレクトリ ロゴ画像
     * @param tempDirMenuLogo テンポラリディレクトリ メニューロゴ画像
     * @return エラー
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 例外処理
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req,
            String tempDirLogo, String tempDirMenuLogo) throws SQLException, IOToolsException {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage();
        /** メッセージ 会社名 **/
        String ent = gsMsg.getMessage(req, "cmn.company.name");
        /** メッセージ 会社名カナ **/
        String entKane = gsMsg.getMessage(req, "cmn.cmn160.1");
        /** メッセージ 期首月 **/
        String kisyu = gsMsg.getMessage(req, "cmn.cmn160.3");
        /** メッセージ 会社名カナ **/
        String strMemo = gsMsg.getMessage(req, "cmn.memo");
        /** メッセージ オリジナルロゴ **/
        String strOriLogo = gsMsg.getMessage(req, "cmn.original.logo");

        //会社名
        GSValidateCommon.validateTextField(errors, cmn160ComName__, "cmn160ComName",
                ent, GSConstCommon.MAX_LENGTH_ENT_NAME, false);
        //会社名(カナ)
        GSValidateCommon.validateTextFieldKana(errors, cmn160ComNamekn__, "cmn160ComNamekn",
                entKane, GSConstCommon.MAX_LENGTH_ENT_NAMEKN, false);
        //URL
        GSValidateCommon.validateTextField(errors, cmn160Url__, "cmn160Url",
                GSConstCommon.TEXT_ENT_URL, GSConstCommon.MAX_LENGTH_ENT_URL, false);
        if (!ValidateUtil.isHttpUrlFormat(cmn160Url__)) {
            String url = gsMsg.getMessage("cmn.url");
            ActionMessage msg = new ActionMessage("error.input.format.text", url);
            StrutsUtil.addMessage(errors, msg, "error.input.format.url");
        }
        //期首月
        if (cmn160Kisyu__ < 1 || cmn160Kisyu__ > 12) {
            ActionMessage msg =
                new ActionMessage("error.select.required.text", kisyu);
            StrutsUtil.addMessage(errors, msg, "cmn160Kisyu");
        }
        //備考
        GSValidateCommon.validateTextAreaField(errors, cmn160Biko__, "cmn160Biko",
                strMemo, GSConstCommon.MAX_LENGTH_ENT_BIKO, false);

        CommonBiz commonBiz = new CommonBiz();
        //ログイン画面 オリジナルロゴ
        if (cmn160DspLogoKbn__ == 1) {
            //未設定チェック

            if (StringUtil.isNullZeroString(cmn160LogoName__)) {
                ActionMessage msg =
                    new ActionMessage("error.select.required.text", strOriLogo);
                StrutsUtil.addMessage(errors, msg, "cmn160Logo");
            } else if (!commonBiz.checkTempPhotoFile(tempDirLogo)) {
                //BMP,JPG,JPEG,GIF,PNG以外のファイルならばエラー
                ActionMessage msg = new ActionMessage("error.select2.required.extent2",
                        gsMsg.getMessage("cmn.login") + gsMsg.getMessage("cmn.display")
                        + " " + gsMsg.getMessage("cmn.logo"));
                StrutsUtil.addMessage(errors, msg, "cmn160SelectFiles");
            }
        }

        //メニュー オリジナルロゴ
        if (cmn160MenuDspLogoKbn__ == 1) {
            //未設定チェック

            if (StringUtil.isNullZeroString(cmn160MenuLogoName__)) {
                ActionMessage msg =
                    new ActionMessage("error.select.required.text", strOriLogo);
                StrutsUtil.addMessage(errors, msg, "cmn160Logo");
            } else if (!commonBiz.checkTempPhotoFile(tempDirMenuLogo)) {
                //BMP,JPG,JPEG,GIF,PNG以外のファイルならばエラー
                ActionMessage msg = new ActionMessage("error.select2.required.extent2",
                        gsMsg.getMessage("man.menu") + " " + gsMsg.getMessage("cmn.logo"));
                StrutsUtil.addMessage(errors, msg, "cmn160MenuLogo");
            }
        }

        return errors;
    }

    /**
     * @return cmn160Biko
     */
    public String getCmn160Biko() {
        return cmn160Biko__;
    }



    /**
     * @param cmn160Biko 設定する cmn160Biko
     */
    public void setCmn160Biko(String cmn160Biko) {
        cmn160Biko__ = cmn160Biko;
    }



    /**
     * @return cmn160ComName
     */
    public String getCmn160ComName() {
        return cmn160ComName__;
    }



    /**
     * @param cmn160ComName 設定する cmn160ComName
     */
    public void setCmn160ComName(String cmn160ComName) {
        cmn160ComName__ = cmn160ComName;
    }



    /**
     * @return cmn160ComNamekn
     */
    public String getCmn160ComNamekn() {
        return cmn160ComNamekn__;
    }



    /**
     * @param cmn160ComNamekn 設定する cmn160ComNamekn
     */
    public void setCmn160ComNamekn(String cmn160ComNamekn) {
        cmn160ComNamekn__ = cmn160ComNamekn;
    }



    /**
     * @return cmn160InitFlg
     */
    public int getCmn160InitFlg() {
        return cmn160InitFlg__;
    }



    /**
     * @param cmn160InitFlg 設定する cmn160InitFlg
     */
    public void setCmn160InitFlg(int cmn160InitFlg) {
        cmn160InitFlg__ = cmn160InitFlg;
    }


    /**
     * @return cmn160Kisyu
     */
    public int getCmn160Kisyu() {
        return cmn160Kisyu__;
    }



    /**
     * @param cmn160Kisyu 設定する cmn160Kisyu
     */
    public void setCmn160Kisyu(int cmn160Kisyu) {
        cmn160Kisyu__ = cmn160Kisyu;
    }


    /**
     * @return cmn160MonthList
     */
    public List<LabelValueBean> getCmn160MonthList() {
        return cmn160MonthList__;
    }



    /**
     * @param cmn160MonthList 設定する cmn160MonthList
     */
    public void setCmn160MonthList(List<LabelValueBean> cmn160MonthList) {
        cmn160MonthList__ = cmn160MonthList;
    }



    /**
     * @return cmn160Url
     */
    public String getCmn160Url() {
        return cmn160Url__;
    }



    /**
     * @param cmn160Url 設定する cmn160Url
     */
    public void setCmn160Url(String cmn160Url) {
        cmn160Url__ = cmn160Url;
    }



    /**
     * <p>cmn160BinSid を取得します。
     * @return cmn160BinSid
     */
    public Long getCmn160BinSid() {
        return cmn160BinSid__;
    }



    /**
     * <p>cmn160BinSid をセットします。
     * @param cmn160BinSid cmn160BinSid
     */
    public void setCmn160BinSid(Long cmn160BinSid) {
        cmn160BinSid__ = cmn160BinSid;
    }



    /**
     * <p>cmn160LogoName を取得します。
     * @return cmn160LogoName
     */
    public String getCmn160LogoName() {
        return cmn160LogoName__;
    }



    /**
     * <p>cmn160LogoName をセットします。
     * @param cmn160LogoName cmn160LogoName
     */
    public void setCmn160LogoName(String cmn160LogoName) {
        cmn160LogoName__ = cmn160LogoName;
    }




    /**
     * <p>cmn160LogoSaveName を取得します。
     * @return cmn160LogoSaveName
     */
    public String getCmn160LogoSaveName() {
        return cmn160LogoSaveName__;
    }



    /**
     * <p>cmn160LogoSaveName をセットします。
     * @param cmn160LogoSaveName cmn160LogoSaveName
     */
    public void setCmn160LogoSaveName(String cmn160LogoSaveName) {
        cmn160LogoSaveName__ = cmn160LogoSaveName;
    }



    /**
     * <p>cmn160DspLogoKbn をセットします。
     * @param cmn160DspLogoKbn cmn160DspLogoKbn
     */
    public void setCmn160DspLogoKbn(int cmn160DspLogoKbn) {
        cmn160DspLogoKbn__ = cmn160DspLogoKbn;
    }



    /**
     * <p>cmn160TempSetFlg をセットします。
     * @param cmn160TempSetFlg cmn160TempSetFlg
     */
    public void setCmn160TempSetFlg(int cmn160TempSetFlg) {
        cmn160TempSetFlg__ = cmn160TempSetFlg;
    }



    /**
     * <p>cmn160DspLogoKbn を取得します。
     * @return cmn160DspLogoKbn
     */
    public int getCmn160DspLogoKbn() {
        return cmn160DspLogoKbn__;
    }



    /**
     * <p>cmn160TempSetFlg を取得します。
     * @return cmn160TempSetFlg
     */
    public int getCmn160TempSetFlg() {
        return cmn160TempSetFlg__;
    }



    /**
     * <p>cmn160TourokuKbn を取得します。
     * @return cmn160TourokuKbn
     */
    public int getCmn160TourokuKbn() {
        return cmn160TourokuKbn__;
    }



    /**
     * <p>cmn160TourokuKbn をセットします。
     * @param cmn160TourokuKbn cmn160TourokuKbn
     */
    public void setCmn160TourokuKbn(int cmn160TourokuKbn) {
        cmn160TourokuKbn__ = cmn160TourokuKbn;
    }



    /**
     * <p>cmn160DelExeFlg を取得します。
     * @return cmn160DelExeFlg
     */
    public boolean isCmn160DelExeFlg() {
        return cmn160DelExeFlg__;
    }



    /**
     * <p>cmn160DelExeFlg をセットします。
     * @param cmn160DelExeFlg cmn160DelExeFlg
     */
    public void setCmn160DelExeFlg(boolean cmn160DelExeFlg) {
        cmn160DelExeFlg__ = cmn160DelExeFlg;
    }



    /**
     * <p>cmn160DbLogoKbn を取得します。
     * @return cmn160DbLogoKbn
     */
    public int getCmn160DbLogoKbn() {
        return cmn160DbLogoKbn__;
    }



    /**
     * <p>cmn160DbLogoKbn をセットします。
     * @param cmn160DbLogoKbn cmn160DbLogoKbn
     */
    public void setCmn160DbLogoKbn(int cmn160DbLogoKbn) {
        cmn160DbLogoKbn__ = cmn160DbLogoKbn;
    }



    /**
     * <p>cmn160MenuBinSid を取得します。
     * @return cmn160MenuBinSid
     */
    public Long getCmn160MenuBinSid() {
        return cmn160MenuBinSid__;
    }



    /**
     * <p>cmn160MenuBinSid をセットします。
     * @param cmn160MenuBinSid cmn160MenuBinSid
     */
    public void setCmn160MenuBinSid(Long cmn160MenuBinSid) {
        cmn160MenuBinSid__ = cmn160MenuBinSid;
    }



    /**
     * <p>cmn160MenuLogoName を取得します。
     * @return cmn160MenuLogoName
     */
    public String getCmn160MenuLogoName() {
        return cmn160MenuLogoName__;
    }



    /**
     * <p>cmn160MenuLogoName をセットします。
     * @param cmn160MenuLogoName cmn160MenuLogoName
     */
    public void setCmn160MenuLogoName(String cmn160MenuLogoName) {
        cmn160MenuLogoName__ = cmn160MenuLogoName;
    }



    /**
     * <p>cmn160MenuLogoSaveName を取得します。
     * @return cmn160MenuLogoSaveName
     */
    public String getCmn160MenuLogoSaveName() {
        return cmn160MenuLogoSaveName__;
    }



    /**
     * <p>cmn160MenuLogoSaveName をセットします。
     * @param cmn160MenuLogoSaveName cmn160MenuLogoSaveName
     */
    public void setCmn160MenuLogoSaveName(String cmn160MenuLogoSaveName) {
        cmn160MenuLogoSaveName__ = cmn160MenuLogoSaveName;
    }



    /**
     * <p>cmn160MenuTempSetFlg を取得します。
     * @return cmn160MenuTempSetFlg
     */
    public int getCmn160MenuTempSetFlg() {
        return cmn160MenuTempSetFlg__;
    }



    /**
     * <p>cmn160MenuTempSetFlg をセットします。
     * @param cmn160MenuTempSetFlg cmn160MenuTempSetFlg
     */
    public void setCmn160MenuTempSetFlg(int cmn160MenuTempSetFlg) {
        cmn160MenuTempSetFlg__ = cmn160MenuTempSetFlg;
    }



    /**
     * <p>cmn160MenuDspLogoKbn を取得します。
     * @return cmn160MenuDspLogoKbn
     */
    public int getCmn160MenuDspLogoKbn() {
        return cmn160MenuDspLogoKbn__;
    }



    /**
     * <p>cmn160MenuDspLogoKbn をセットします。
     * @param cmn160MenuDspLogoKbn cmn160MenuDspLogoKbn
     */
    public void setCmn160MenuDspLogoKbn(int cmn160MenuDspLogoKbn) {
        cmn160MenuDspLogoKbn__ = cmn160MenuDspLogoKbn;
    }



    /**
     * <p>cmn160MenuDbLogoKbn を取得します。
     * @return cmn160MenuDbLogoKbn
     */
    public int getCmn160MenuDbLogoKbn() {
        return cmn160MenuDbLogoKbn__;
    }



    /**
     * <p>cmn160MenuDbLogoKbn をセットします。
     * @param cmn160MenuDbLogoKbn cmn160MenuDbLogoKbn
     */
    public void setCmn160MenuDbLogoKbn(int cmn160MenuDbLogoKbn) {
        cmn160MenuDbLogoKbn__ = cmn160MenuDbLogoKbn;
    }



    /**
     * <p>cmn160MenuDelExeFlg を取得します。
     * @return cmn160MenuDelExeFlg
     */
    public boolean isCmn160MenuDelExeFlg() {
        return cmn160MenuDelExeFlg__;
    }



    /**
     * <p>cmn160MenuDelExeFlg をセットします。
     * @param cmn160MenuDelExeFlg cmn160MenuDelExeFlg
     */
    public void setCmn160MenuDelExeFlg(boolean cmn160MenuDelExeFlg) {
        cmn160MenuDelExeFlg__ = cmn160MenuDelExeFlg;
    }



    /**
     * <p>cmn160ThemePath を取得します。
     * @return cmn160ThemePath
     */
    public String getCmn160ThemePath() {
        return cmn160ThemePath__;
    }



    /**
     * <p>cmn160ThemePath をセットします。
     * @param cmn160ThemePath cmn160ThemePath
     */
    public void setCmn160ThemePath(String cmn160ThemePath) {
        cmn160ThemePath__ = cmn160ThemePath;
    }


}