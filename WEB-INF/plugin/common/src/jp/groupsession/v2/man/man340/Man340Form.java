package jp.groupsession.v2.man.man340;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man121.Man121Form;
import jp.groupsession.v2.man.man340.model.Man340UrlParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 プラグイン追加画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man340Form extends Man121Form {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man340Form.class);

    /** 初期表示フラグ */
    private int man340initFlg__ = 0;

    /** 処理モード */
    private int man340cmdMode__ = 0;
    /** プラグインID */
    private String man340pluginId__ = GSConst.PLUGINID_MAIN;
    /** 機能名 */
    private String man340funcId__ = null;
    /** タイトル */
    private String man340title__ = null;
    /** URL */
    private String man340url__ = null;
    /** ウィンドウ区分 0:インライン 1:別ウィンドウ */
    private int man340openKbn__ = GSConstMain.TARGET_BLANK;
    /** 添付ファイル */
    private String man340file__ = null;
    /** 添付ファイル 保存名 */
    private String man340SaveFile__ = null;

    /** パラメータ区分 */
    private int man340paramKbn__ = 0;
    /** 送信区分 */
    private int man340sendKbn__ = 0;
    /** パラメータリスト */
    private List<Man340UrlParamModel> man340urlParamList__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Man340Form() {
        man340urlParamList__ = new ArrayList <Man340UrlParamModel>();
    }

    /**
     * <p>man340openKbn を取得します。
     * @return man340openKbn
     */
    public int getMan340openKbn() {
        return man340openKbn__;
    }
    /**
     * <p>man340openKbn をセットします。
     * @param man340openKbn man340openKbn
     */
    public void setMan340openKbn(int man340openKbn) {
        man340openKbn__ = man340openKbn;
    }
    /**
     * <p>man340title を取得します。
     * @return man340title
     */
    public String getMan340title() {
        return man340title__;
    }
    /**
     * <p>man340title をセットします。
     * @param man340title man340title
     */
    public void setMan340title(String man340title) {
        man340title__ = man340title;
    }
    /**
     * <p>man340url を取得します。
     * @return man340url
     */
    public String getMan340url() {
        return man340url__;
    }
    /**
     * <p>man340url をセットします。
     * @param man340url man340url
     */
    public void setMan340url(String man340url) {
        man340url__ = man340url;
    }

    /**
     * <p>man340file を取得します。
     * @return man340file
     */
    public String getMan340file() {
        return man340file__;
    }
    /**
     * <p>man340file をセットします。
     * @param man340file man340file
     */
    public void setMan340file(String man340file) {
        man340file__ = man340file;
    }
    /**
     * <p>man340SaveFile を取得します。
     * @return man340SaveFile
     */
    public String getMan340SaveFile() {
        return man340SaveFile__;
    }

    /**
     * <p>man340SaveFile をセットします。
     * @param man340SaveFile man340SaveFile
     */
    public void setMan340SaveFile(String man340SaveFile) {
        man340SaveFile__ = man340SaveFile;
    }

    /**
     * <p>man340funcId を取得します。
     * @return man340funcId
     */
    public String getMan340funcId() {
        return man340funcId__;
    }
    /**
     * <p>man340funcId をセットします。
     * @param man340funcId man340funcId
     */
    public void setMan340funcId(String man340funcId) {
        man340funcId__ = man340funcId;
    }
    /**
     * <p>man340pluginId を取得します。
     * @return man340pluginId
     */
    public String getMan340pluginId() {
        return man340pluginId__;
    }
    /**
     * <p>man340pluginId をセットします。
     * @param man340pluginId man340pluginId
     */
    public void setMan340pluginId(String man340pluginId) {
        man340pluginId__ = man340pluginId;
    }
    /**
     * <p>man340cmdMode を取得します。
     * @return man340cmdMode
     */
    public int getMan340cmdMode() {
        return man340cmdMode__;
    }
    /**
     * <p>man340cmdMode をセットします。
     * @param man340cmdMode man340cmdMode
     */
    public void setMan340cmdMode(int man340cmdMode) {
        man340cmdMode__ = man340cmdMode;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param appPath アプリケーションルートパス
     * @return エラー
     * @throws SQLException sql実行例外
     * @throws IOToolsException 例外処理
     */
    public ActionErrors validateCheck(String tempDir,
                                      RequestModel reqMdl,
                                      Connection con,
                                      String appPath) throws SQLException, IOToolsException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String message2 = gsMsg.getMessage("cmn.title");
        String message3 = gsMsg.getMessage("cmn.icon") + gsMsg.getMessage("cmn.image");

        //タイトル
        errors = __validateCmnFieldText(errors,
                                        message2,
                                        man340title__,
                                        "man340title",
                                        GSConstMain.MAX_LENGTH_TITLE,
                                        true);
        //URL
        errors = __validateUrl(errors);

        //URLパラメータ
        //パラメータ設定「設定する」の場合
        if (man340paramKbn__ == GSConstMain.PARAM_KBN_YES) {
            errors = __validateUrlParam(errors, gsMsg);
        }

        //添付ファイル
        errors = __validateFile(errors,
                tempDir,
                message3);

        return errors;
    }

    /**
     * <br>[機  能] URL入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @return errors エラー
     */
    private ActionErrors __validateUrl(ActionErrors errors) {

        log__.debug("*****validateMan340");
        //URL文字チェック
        errors = __validateCmnFieldText(errors,
                                        "URL",
                                        man340url__,
                                        "man340url",
                                        GSConst.MAX_LENGTH_URL,
                                        true);
        if (!StringUtil.isNullZeroString(man340url__)) {
            //URL形式チェック
            boolean urlFlg = true;
            int pos = man340url__.indexOf("://");
            String scheme = null;
            String host = null;

            //スキーマの取得
            if (pos < 0) {
                urlFlg = false;
            } else {
                scheme = man340url__.substring(0, pos);
            }

            //「://」以降の文字列を取得
            if (urlFlg) {
                host = man340url__.substring(pos + 3);
            }

            log__.debug("scheme ===> " + scheme);
            log__.debug("host   ===> " + host);

            //「://」以降の文字列チェック
            if (StringUtil.isNullZeroString(host) || ValidateUtil.isSpace(host)) {
                urlFlg = false;
            }

            if (!urlFlg) {
                ActionMessage msg = null;
                String eprefix = "man340url";
                String fieldfix = "URL" + ".";
                msg = new ActionMessage("error.input.format.text", "URL");
                StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "man340url");
                return errors;
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] URL入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param gsMsg GSメッセージ
     * @return errors エラー
     */
    private ActionErrors __validateUrlParam(ActionErrors errors, GsMessage gsMsg) {

        log__.debug("*****validateMan340");

        ActionMessage msg = null;


        if (man340urlParamList__ != null && man340urlParamList__.size() > 0) {

            //パラメータ名の未入力チェック
            boolean allEmpFlg = true;
            for (Man340UrlParamModel paramMdl : man340urlParamList__) {
                if (!StringUtil.isNullZeroString(paramMdl.getParamName())) {
                    allEmpFlg = false;
                    break;
                }
            }

            if (allEmpFlg) {
                //全件未入力チェック
                msg = new ActionMessage("error.input.required.text", "パラメータ名");
                StrutsUtil.addMessage(errors, msg, "all" + "man340urlParamList");
                return errors;
            }

            //テキストフィールドの入力チェックを行う
            for (int index = 0; index < man340urlParamList__.size(); index++) {
                Man340UrlParamModel paramMdl = man340urlParamList__.get(index);

                //値のみの入力禁止チェック
                if (StringUtil.isNullZeroString(paramMdl.getParamName())
                        && !StringUtil.isNullZeroString(paramMdl.getParamValue())) {

                    String pName =
                            gsMsg.getMessageVal0("cmn.line2", String.valueOf(index + 1))
                            + "パラメータ名";

                    msg = new ActionMessage("error.input.required.text", pName);
                    StrutsUtil.addMessage(errors, msg, index + "man340urlParamList");
                }

                //パラメータ名、値
                if (!StringUtil.isNullZeroString(paramMdl.getParamName())
                        || !StringUtil.isNullZeroString(paramMdl.getParamValue())) {


                    if (!StringUtil.isNullZeroString(paramMdl.getParamName())) {
                        String pName =
                                gsMsg.getMessageVal0("cmn.line2", String.valueOf(index + 1))
                                + "パラメータ名";

                        errors = __validateCmnFieldText(
                                errors,
                                pName,
                                paramMdl.getParamName(),
                                "man340urlParamList." + index + ".paramName",
                                GSConstMain.MAX_LENGTH_PARAM_NAME, false);
                    }

                    if (!StringUtil.isNullZeroString(paramMdl.getParamValue())) {
                        String vName =
                                gsMsg.getMessageVal0("cmn.line2", String.valueOf(index + 1))
                                + "値";

                        errors = __validateCmnFieldTextForParamValue(
                                errors,
                                vName,
                                paramMdl.getParamValue(),
                                "man340urlParamList." + index + ".paramValue",
                                GSConstMain.MAX_LENGTH_PARAM_VALUE);
                    }

                }
            }
        }

        return errors;
    }




    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param maxLength 最大文字数
     * @param hisuFlg 入力が必須か true:必須 false:必須ではない
     * @return errors エラー
     */
    private ActionErrors __validateCmnFieldText(
            ActionErrors errors,
            String checkObject,
            String field,
            String strField,
            int maxLength,
            boolean hisuFlg) {
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;

        String eprefix = GSConst.PLUGINID_MAIN;
        String fieldfix = checkObject + ".";

        if (StringUtil.isNullZeroString(field)) {
            if (hisuFlg) {
                //未入力チェック
                msg = new ActionMessage("error.input.required.text", checkObject);
                StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
                return errors;
            }
        }

        if (ValidateUtil.isSpace(field)) {
            //スペースのみチェック
            msg = new ActionMessage("error.input.spase.only", checkObject);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isSpaceStart(field)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", checkObject);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (ValidateUtil.isTab(field)) {
            //タブチェック
            msg = new ActionMessage("error.input.tab.text", checkObject);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
            return errors;
        }


        if (field.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", checkObject, maxLength);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] テキストフィールドの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param checkObject エラーメッセージ表示テキスト(例："名前" 例："コメント"）
     * @param field チェックするフィールド
     * @param strField チェックするフィールドの文字列
     * @param maxLength 最大文字数
     * @return errors エラー
     */
    private ActionErrors __validateCmnFieldTextForParamValue(
            ActionErrors errors,
            String checkObject,
            String field,
            String strField,
            int maxLength) {
        log__.debug(strField + " のチェックを行います。");
        ActionMessage msg = null;

        String eprefix = GSConst.PLUGINID_MAIN;
        String fieldfix = checkObject + ".";

        if (ValidateUtil.isTab(field)) {
            //タブチェック
            msg = new ActionMessage("error.input.tab.text", checkObject);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
            return errors;
        }


        if (field.length() > maxLength) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", checkObject, maxLength);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
            return errors;
        }

        if (!GSValidateUtil.isGsJapaneaseStringTextArea(field)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(field);
            msg = new ActionMessage("error.input.njapan.text", checkObject, nstr);
            StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + strField);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] アイコンファイルの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param tempDir テンポラリディレクトリ
     * @param checkObject チェック対象
     * @return errors エラー
     * @throws IOToolsException 例外処理
     * @throws SQLException SQL例外処理
     */
    private ActionErrors __validateFile(
            ActionErrors errors,
            String tempDir,
            String checkObject) throws SQLException, IOToolsException {

        ActionMessage msg = null;
        CommonBiz commonBiz = new CommonBiz();
        if (!commonBiz.checkTempPhotoFile(tempDir)) {
            //BMP,JPG,JPEG,GIF,PNG以外のファイルならばエラー
            msg = new ActionMessage("error.select2.required.extent");
            StrutsUtil.addMessage(errors, msg, checkObject);
            return errors;
        }
        return errors;
    }
    /**
     * <p>man340paramKbn を取得します。
     * @return man340paramKbn
     */
    public int getMan340paramKbn() {
        return man340paramKbn__;
    }
    /**
     * <p>man340paramKbn をセットします。
     * @param man340paramKbn man340paramKbn
     */
    public void setMan340paramKbn(int man340paramKbn) {
        man340paramKbn__ = man340paramKbn;
    }
    /**
     * <p>man340sendKbn を取得します。
     * @return man340sendKbn
     */
    public int getMan340sendKbn() {
        return man340sendKbn__;
    }
    /**
     * <p>man340sendKbn をセットします。
     * @param man340sendKbn man340sendKbn
     */
    public void setMan340sendKbn(int man340sendKbn) {
        man340sendKbn__ = man340sendKbn;
    }
    /**
     * <p>man340urlParamList を取得します。
     * @return man340urlParamList
     */
    public List<Man340UrlParamModel> getMan340urlParamListToList() {
        return man340urlParamList__;
    }
    /**
     * <p>man340urlParamList をセットします。
     * @param man340urlParamList
     *
     * List man340urlParamList
     */
    public void setMan340urlParamListForm(List<Man340UrlParamModel> man340urlParamList) {
        man340urlParamList__ = man340urlParamList;
    }

    /**
     * <p>Man340UrlParamModel を取得します。
     * @param iIndex インデックス番号
     * @return Man340UrlParamModel を戻す
     */
    public Man340UrlParamModel getMan340urlParamList(int iIndex) {
        while (man340urlParamList__.size() <= iIndex) {
            man340urlParamList__.add(new Man340UrlParamModel());
        }
        return man340urlParamList__.get(iIndex);
    }

    /**
     * <p>man340urlParamList を取得します。
     * @return man340urlParamList
     */
    public Man340UrlParamModel[] getMan340urlParamList() {
        int size = 0;
        if (man340urlParamList__ != null) {
            size = man340urlParamList__.size();
        }
        return (Man340UrlParamModel[]) man340urlParamList__.toArray(new Man340UrlParamModel[size]);
    }
    /**
     * <br>[機  能] 表の行数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 表の行数
     */
    public int getMan340urlParamListFormSize() {
        return man340urlParamList__.size();
    }

    /**
     * <p>man340initFlg を取得します。
     * @return man340initFlg
     */
    public int getMan340initFlg() {
        return man340initFlg__;
    }

    /**
     * <p>man340initFlg をセットします。
     * @param man340initFlg man340initFlg
     */
    public void setMan340initFlg(int man340initFlg) {
        man340initFlg__ = man340initFlg;
    }
}