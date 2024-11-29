package jp.groupsession.v2.man.man290kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnInfoBinDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man320.Man320Biz;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン インフォメーション登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man290knAction extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man290knAction.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man290";


    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
            return true;

        }
        return false;
    }

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
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {


        ActionForward forward = null;
        //アクセス権限チェック　※管理者or登録許可ユーザ
        RequestModel reqMdl = getRequestModel(req);
        Man320Biz biz = new Man320Biz();
        if (!biz.isInfoAdminAuth(reqMdl, con)) {
            //権限エラー
            return getNotAdminSeniPage(map, req);
        }

        Man290knForm thisForm = (Man290knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        log__.debug("CMD==>" + cmd);
        if (cmd.equals("290kn_commit")) {
            //登録実行
            forward = __doCommit(map, thisForm, req, res, con);
        } else if (cmd.equals("290kn_back")) {
            //戻る
            forward = __doBack(map, thisForm, req, res, con);
        } else if (cmd.equals("fileDownload")) {
            log__.debug("添付ファイルダウンロード");
            forward = __doDownLoad(map, thisForm, req, res, con);
        } else {
            //初期表示
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイル処理例外
     */
    private void __doInit(ActionMapping map, Man290knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws SQLException, IOToolsException {

        con.setAutoCommit(true);
        Man290knParamModel paramMdl = new Man290knParamModel();
        paramMdl.setParam(form);
        Man290knBiz biz = new Man290knBiz(con, getRequestModel(req));
        biz.setInitData(paramMdl, getMainTempDir(req));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
    }

    /**
     * <br>[機  能] 確定ボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Man290knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
                    throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //セッション情報を取得
        int sessionUsrSid = getSessionUserSid(req);
        RequestModel reqMdl = getRequestModel(req);

        ActionForward forward = null;
        ActionErrors errors = form.validateCheck(reqMdl, con);
        if (errors.size() > 0) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            log__.debug("入力エラー");
            return map.getInputForward();
        }

        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {
            MlCountMtController cntCon = getCountMtController(req);
            int imsSid = -1;

            Man290knParamModel paramMdl = new Man290knParamModel();
            paramMdl.setParam(form);
            Man290knBiz biz = new Man290knBiz(con, reqMdl);
            CmnInfoBinDao biDao = new CmnInfoBinDao(con);

            if (form.getCmd().equals(GSConstMain.CMD_ADD)) {
                //新規登録
                imsSid = biz.insertMsg(paramMdl, sessionUsrSid, cntCon);
                biz.insertTag(paramMdl, imsSid, sessionUsrSid, cntCon);

            } else if (form.getCmd().equals(GSConstMain.CMD_EDIT)) {
                //編集登録
                imsSid = NullDefault.getInt(form.getMan320SelectedSid(), -1);
                int count = biz.updateMsg(paramMdl, sessionUsrSid);
                if (count > 0) {
                    biz.insertTag(paramMdl, imsSid, sessionUsrSid, cntCon);
                }
            }
            paramMdl.setFormData(form);

            //添付ファイルを削除
            biDao.deleteBinf(imsSid);
            biDao.delete(imsSid);

            //添付ファイルを登録
            CommonBiz cmnBiz = new CommonBiz();
            List < String > binSid = cmnBiz.insertBinInfo(con, getMainTempDir(req),
                    getAppRootPath(),
                    cntCon, sessionUsrSid, new UDate());
            //インフォメーション添付情報の登録
            biDao.insertInfoBinData(imsSid, binSid);

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("インフォメーションメッセージの登録に失敗しました", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }

            //テンポラリディレクトリを削除
            __deleteTempDir(req);
        }

        //ログ出力処理
        CommonBiz cmnBiz = new CommonBiz();
        String opCode = "";
        if (form.getCmd().equals("add")) {
            opCode = getInterMessage(reqMdl, "cmn.entry");
        } else if (form.getCmd().equals("edit")) {
            opCode = getInterMessage(reqMdl, "cmn.change");
        }
        GsMessage gsMsg = new GsMessage(reqMdl);
        cmnBiz.outPutCommonLog(
                map, reqMdl, gsMsg, con, opCode,
                GSConstLog.LEVEL_TRACE,
                "[" + getInterMessage(req, "cmn.information") + "]" + form.getMan290Msg());
        //テンポラリディレクトリ内のファイルを削除
        __deleteTempDir(req);

        forward = __doCompDsp(map, form, req, res, con);
        return forward;
    }

    /**
     * <br>[機  能] 添付ファイルダウンロードの処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     * @return ActionForward
     */
    private ActionForward __doDownLoad(
            ActionMapping map,
            Man290knForm form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con) throws SQLException, Exception {

        String fileId = form.getMan290knTmpFileId();
        //fileIdの半角数字チェック処理
        if (!ValidateUtil.isNumber(fileId)) {
            return getSubmitErrorPage(map, req);
        }

        String tempDir = getMainTempDir(req);
        String fileName = __downloadTempFile(req, res, tempDir, fileId);

        //ログ出力処理
        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        cmnBiz.outPutCommonLog(
                map, reqMdl, gsMsg, con, getInterMessage(req, "cmn.download"),
                GSConstLog.LEVEL_INFO,
                "[" + getInterMessage(reqMdl, "cmn.information") + "]" + fileName,
                fileId);

        return null;
    }

    /**
     * <br>[機  能] リクエストを解析し画面遷移先を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移
     */
    private ActionForward __doBack(ActionMapping map, Man290knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {

        ActionForward forward = null;

        forward = map.findForward("290kn_back");
        return forward;
    }

    /**
     * <br>[機  能] 登録・更新完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map, Man290knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //登録完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("290kn_comp");
        cmn999Form.setUrlOK(urlForward.getPath());
        if (form.getCmd().equals(GSConstMain.CMD_ADD)) {
            cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                    getInterMessage(req, GSConstMain.TEXT_INFO)));
        } else if (form.getCmd().equals(GSConstMain.CMD_EDIT)) {
            cmn999Form.setMessage(msgRes.getMessage("hensyu.kanryo.object",
                    getInterMessage(req, GSConstMain.TEXT_INFO)));
        }


        //hiddenパラメータ
        cmn999Form.addHiddenParam("cmd", form.getCmd());
        cmn999Form.addHiddenParam("man320SortKey", form.getMan320SortKey());
        cmn999Form.addHiddenParam("man320OrderKey", form.getMan320OrderKey());
        cmn999Form.addHiddenParam("man320PageNum", form.getMan320PageNum());
        cmn999Form.addHiddenParam("man320SelectedSid", form.getMan320SelectedSid());
        cmn999Form.addHiddenParam("selectMsg", form.getSelectMsg());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] テンポラリディレクトリのパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return String テンポラリディレクトリのパス
     */
    public String getMainTempDir(HttpServletRequest req) {
        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        return tempDir;
    }

    /**
     * <br>[機  能]テンポラリディレクトリパスを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    private void __deleteTempDir(HttpServletRequest req) {

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        temp.deleteTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
    }

    /**
     * <br>[機  能] 添付ファイルのダウンロードを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param tempDir テンポラリディレクトリ
     * @param fileId 添付ファイルID
     * @return fileName ファイル名
     * @throws Exception 添付ファイルのダウンロードに失敗
     */
    private String __downloadTempFile(HttpServletRequest req, HttpServletResponse res,
            String tempDir, String fileId)
                    throws Exception {

        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
        //添付ファイル保存用のパスを取得する(フルパス)
        String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);
        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);
        return fMdl.getFileName();
    }

}
