package jp.groupsession.v2.cmn.cmn160;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 企業情報画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn160Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn160Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "cmn160";
    /** ログイン画面 ロゴ画像名 */
    public String imageFileName__ = "";
    /** ログイン画面 ロゴ画像保存名 */
    public String imageFileSaveName__ = "";
    /** ログイン画面 ロゴ画像保存用 添付ファイル フォルダ名 */
    public static final String TEMP_LOGO_CMN160 = "cmn160Logo";

    /** メニュー ロゴ画像名 */
    public String menuImageFileName__ = "";
    /** メニュー ロゴ画像保存名 */
    public String menuImageFileSaveName__ = "";
    /** メニュー ロゴ画像保存用 添付ファイル フォルダ名 */
    public static final String TEMP_MENU_LOGO_CMN160 = "cmn160MenuLogo";

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START");

        ActionForward forward = null;
        Cmn160Form thisForm = (Cmn160Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        log__.debug("*****CMD:" + cmd);

        if (cmd.equals("confirmEnterprise")) {
            //OKボタンクリック
            forward = __doConfirm(map, thisForm, req, res, con);

        } else if (cmd.equals("backAdmMenu")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm, req, res, con);

        } else if (cmd.equals("getImageFile")) {
            //ロゴ画像ダウンロード
            forward = __doGetImageFile(map, thisForm, req, res, con);

        } else if (cmd.equals("getMenuImageFile")) {
            //ロゴ画像ダウンロード
            forward = __doGetMenuImageFile(map, thisForm, req, res, con);

        } else if (cmd.equals("defaultLogo")) {
            //ログイン画面 ロゴ初期値セット
            thisForm.setCmn160DspLogoKbn(0);
            forward = __doTempDeleteMark(map, thisForm, req, res, con);

        } else if (cmd.equals("defaultMenuLogo")) {
            //メニュー ロゴ初期値セット
            thisForm.setCmn160MenuDspLogoKbn(0);
            forward = __doTempDeleteMenuMark(map, thisForm, req, res, con);

        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException バイナリファイル操作時例外
     * @return ActionForward
     */
    private ActionForward __doInit(
            ActionMapping map,
            Cmn160Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
                    throws SQLException, IOToolsException,
                    TempFileException, IOException {
        imageFileName__ = "";
        imageFileSaveName__ = "";
        menuImageFileName__ = "";
        menuImageFileSaveName__ = "";

        if (form.getCmn160InitFlg() == 0) {
            //テンポラリディレクトリの削除
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(getRequestModel(req), GSConst.PLUGINID_COMMON, TEMP_DIRECTORY_ID);
            //テンポラリディレクトリの生成
            temp.createTempDir(getRequestModel(req), GSConst.PLUGINID_COMMON,
                    TEMP_DIRECTORY_ID, TEMP_LOGO_CMN160);
            temp.createTempDir(getRequestModel(req), GSConst.PLUGINID_COMMON,
                TEMP_DIRECTORY_ID, TEMP_MENU_LOGO_CMN160);
        }

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 表示処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws IOException バイナリファイル操作時例外
     * @return ActionForward
     */
    private ActionForward __doDsp(
        ActionMapping map,
        Cmn160Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
    throws SQLException, IOToolsException, TempFileException, IOException {

        //初期表示情報を取得する
        con.setAutoCommit(true);
        Cmn160Biz biz = new Cmn160Biz(new GsMessage(req));

        //テーマパス
        String imgPath = GSConst.DEF_THEME_PATH;
        if (!StringUtil.isNullZeroStringSpace(getRequestModel(req).getSmodel().getCtmPath())) {
            imgPath = getRequestModel(req).getSmodel().getCtmPath();
        }

        form.setCmn160ThemePath(imgPath);

        //アプリケーションのルートパス
        String appRootPath = getAppRootPath();

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempPath = temp.getTempPath(getRequestModel(req), GSConst.PLUGINID_COMMON,
                TEMP_DIRECTORY_ID, TEMP_LOGO_CMN160);
        String tempMenuPath = temp.getTempPath(getRequestModel(req), GSConst.PLUGINID_COMMON,
                TEMP_DIRECTORY_ID, TEMP_MENU_LOGO_CMN160);
        Cmn160ParamModel paramModel = new Cmn160ParamModel();
        paramModel.setParam(form);
        biz.getInitData(con, paramModel, appRootPath, tempPath, tempMenuPath,
                GroupSession.getResourceManager().getDomain(req));
        paramModel.setFormData(form);

        //ログイン画面 画像ファイルを設定
        if (!NullDefault.getString(form.getCmn160LogoName(), "").equals("")) {
            imageFileName__ = form.getCmn160LogoName();
            imageFileSaveName__ = form.getCmn160LogoSaveName();
            form.setCmn160DspLogoKbn(1);
        }

        //メニュー 画像ファイルを設定
        if (!NullDefault.getString(form.getCmn160MenuLogoName(), "").equals("")) {
            menuImageFileName__ = form.getCmn160MenuLogoName();
            menuImageFileSaveName__ = form.getCmn160MenuLogoSaveName();
            form.setCmn160MenuDspLogoKbn(1);
        }

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
                                            Cmn160Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {
        
        //imageFileSaveNameの半角数字チェック処理
        if (!ValidateUtil.isNumber(imageFileSaveName__)) {
            return getSubmitErrorPage(map, req);
        }
        
        //画像ファイル読込
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String fullPath = temp.getTempPath(getRequestModel(req), GSConst.PLUGINID_COMMON,
                TEMP_DIRECTORY_ID, TEMP_LOGO_CMN160,
                imageFileSaveName__ + GSConstCommon.ENDSTR_SAVEFILE);

        TempFileUtil.downloadInline(req, res, fullPath, imageFileName__, Encoding.UTF_8);

        return null;
    }

    /**
     * <br>[機  能] tempディレクトリの画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetMenuImageFile(ActionMapping map,
                                            Cmn160Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {
        
        //menuImageFileSaveNameの半角数字チェック処理
        if (!ValidateUtil.isNumber(menuImageFileSaveName__)) {
            return getSubmitErrorPage(map, req);
        }

        //画像ファイル読込
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String fullPath = temp.getTempPath(getRequestModel(req), GSConst.PLUGINID_COMMON,
                TEMP_DIRECTORY_ID, TEMP_MENU_LOGO_CMN160,
                menuImageFileSaveName__ + GSConstCommon.ENDSTR_SAVEFILE);


        TempFileUtil.downloadInline(req, res, fullPath, menuImageFileName__, Encoding.UTF_8);

        return null;
    }

    /**
     * <br>[機  能] ロゴ削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ActionForward
     */
    private ActionForward __doTempDeleteMark(ActionMapping map,
                                      Cmn160Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws SQLException, IOToolsException, IOException, TempFileException {

        //添付ファイルを削除する
        //選択された添付ファイルを削除する
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.clearTempPath(getRequestModel(req), GSConst.PLUGINID_COMMON,
                TEMP_DIRECTORY_ID, TEMP_LOGO_CMN160);

        imageFileName__ = "";
        imageFileSaveName__ = "";
        form.setCmn160LogoName(imageFileName__);
        form.setCmn160LogoSaveName(imageFileSaveName__);
        form.setCmn160TempSetFlg(0);
        form.setCmn160DelExeFlg(true);

        return __doDsp(map, form, req, res, con);
    }


    /**
     * <br>[機  能] ロゴ削除ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException IOエラー
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return ActionForward
     */
    private ActionForward __doTempDeleteMenuMark(ActionMapping map,
                                      Cmn160Form form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws SQLException, IOToolsException, IOException, TempFileException {

        //添付ファイルを削除する
        //選択された添付ファイルを削除する
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.clearTempPath(getRequestModel(req), GSConst.PLUGINID_COMMON,
                TEMP_DIRECTORY_ID, TEMP_MENU_LOGO_CMN160);

        menuImageFileName__ = "";
        menuImageFileSaveName__ = "";
        form.setCmn160MenuLogoName(menuImageFileName__);
        form.setCmn160MenuLogoSaveName(menuImageFileSaveName__);
        form.setCmn160MenuTempSetFlg(0);
        form.setCmn160MenuDelExeFlg(true);

        return __doDsp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doConfirm(ActionMapping map,
                                    Cmn160Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDirLogo = temp.getTempPath(getRequestModel(req), GSConst.PLUGINID_COMMON,
                TEMP_DIRECTORY_ID, TEMP_LOGO_CMN160);
        String tempDirMenuLogo = temp.getTempPath(getRequestModel(req), GSConst.PLUGINID_COMMON,
                TEMP_DIRECTORY_ID, TEMP_MENU_LOGO_CMN160);

        //入力チェック
        ActionErrors errors = form.validateCheck(con, req, tempDirLogo, tempDirMenuLogo);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }

        saveToken(req);
        return map.findForward("confirmEnterprise");
    }

    /**
     * <br>[機  能] 戻るボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws IOToolsException ファイルアクセス時例外
     */
    private ActionForward __doBack(ActionMapping map,
                                    Cmn160Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws IOToolsException {

        //テンポラリディレクトリのファイル削除を行う
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req), GSConst.PLUGINID_COMMON, TEMP_DIRECTORY_ID);

        return map.findForward("mainAdmSetting");
    }
}