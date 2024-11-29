package jp.groupsession.v2.man.man029kn;

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
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 休日設定インポート確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man029knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man029knAction.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man029";

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     *
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("downLoad")) {
            log__.debug("取り込みCSVファイルダウンロード");
                return true;
        }
        return false;
    }
    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
     public ActionForward executeAction(ActionMapping map,
                                         ActionForm form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con) throws Exception {

         ActionForward forward = null;
         Man029knForm man029knForm = (Man029knForm) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //実行ボタン押下
         if (cmd.equals("doImport")) {
             log__.debug("実行ボタン押下");
             forward = __doImport(map, man029knForm, req, res, con);
         //戻るボタン押下
         } else if (cmd.equals("back_import")) {
             log__.debug("戻るボタン押下");
             forward = map.findForward("back_import");
         //添付ダウンロード
         } else if (cmd.equals("downLoad")) {
             forward = __doDownLoad(map, man029knForm, req, res, con);
         //初期表示処理
         } else {
             log__.debug("初期表示処理");
             forward = __doInit(map, man029knForm, req, res, con);
         }

         return forward;
     }

     /**
      * <br>[機  能] 初期表示を行う
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @return ActionForward
     * @throws Exception CSV情報取得時例外
      */
     private ActionForward __doInit(ActionMapping map,
                                     Man029knForm form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws Exception {

         RequestModel reqMdl = getRequestModel(req);

         //テンポラリディレクトリパスを取得
         String tempDir = __getTempDir(req);

         //再入力チェック
         ActionErrors errors = form.validateCheck(reqMdl, tempDir, con);
         if (errors.size() > 0) {
             addErrors(req, errors);
             return map.getInputForward();
         }

         //取込みファイル名称取得
         con.setAutoCommit(true);
         Man029knParamModel paramMdl = new Man029knParamModel();
         paramMdl.setParam(form);
         Man029knBiz biz = new Man029knBiz();
         biz.setImportFileName(paramMdl, tempDir);
         paramMdl.setFormData(form);
         con.setAutoCommit(false);
         return map.getInputForward();
     }

     /**
      * <br>[機  能] インポート実行
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws Exception 実行例外
      * @return アクションフォーワード
      */
     private ActionForward __doImport(ActionMapping map,
                                       Man029knForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
         throws Exception {

         if (!isTokenValid(req, true)) {
             log__.info("２重投稿");
             return getSubmitErrorPage(map, req);
         }

         RequestModel reqMdl = getRequestModel(req);

         //テンポラリディレクトリパスを取得
         CommonBiz cmnBiz = new CommonBiz();
         String tempDir = __getTempDir(req);

         //再入力チェック
         ActionErrors errors = form.validateCheck(reqMdl, tempDir, con);
         if (errors.size() > 0) {
             log__.debug("取込み前再チェックエラー");
             addErrors(req, errors);
             return map.getInputForward();
         }

         //取込み処理
         con.setAutoCommit(false);
         boolean commit = false;

         try {

             //セッションユーザSID取得
             BaseUserModel umodel = getSessionUserModel(req);
             int userSid = umodel.getUsrsid();

             //システム日付取得
             UDate now = new UDate();

             //採番用コネクション取得
             MlCountMtController cntCon = getCountMtController(req);

             //上書きフラグ
             int flg = form.getMan029updateFlg();

             //インポート
             Man029ImportCsv imp = new Man029ImportCsv(con, userSid, now, cntCon, flg);
             long num = imp.importCsv(tempDir);

             //ログ出力処理
             GsMessage gsMsg = new GsMessage(reqMdl);
             String dspName = gsMsg.getMessage("cmn.admin.setting")
                     + " " + gsMsg.getMessage("main.man029kn.1");
             String value = "";
             value = "[" + gsMsg.getMessage("cmn.capture.item.count") + "] ";
             value += num - 1;
             cmnBiz.outPutLogNoDspName(map, reqMdl, gsMsg, con,
                     getInterMessage(reqMdl, "cmn.import"),
                     GSConstLog.LEVEL_INFO, value, dspName);

             commit = true;

         } catch (Exception e) {
             log__.error("休日テンプレートCSVの取り込みに失敗しました。", e);
             throw e;
         } finally {

             //テンポラリディレクトリのファイル削除を行う
             GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
             temp.deleteTempPath(getRequestModel(req),
                     GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

             if (commit) {
                 con.commit();
             } else {
                 JDBCUtil.rollback(con);
             }
         }

         //完了画面遷移
         return __doImportComp(map, form, req, res, con);

     }

     /**
      * <br>[機  能] 休日テンプレートインポート完了後の画面遷移設定
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
     private ActionForward __doImportComp(ActionMapping map,
                                           Man029knForm form,
                                           HttpServletRequest req,
                                           HttpServletResponse res,
                                           Connection con) throws Exception {

         Cmn999Form cmn999Form = new Cmn999Form();
         cmn999Form.setType(Cmn999Form.TYPE_OK);
         cmn999Form.setIcon(Cmn999Form.ICON_INFO);
         cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

         //OKボタンクリック時遷移先
         ActionForward forwardOk = map.findForward("importComp");
         cmn999Form.setUrlOK(forwardOk.getPath());
         MessageResources msgRes = getResources(req);
         cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                 getInterMessage(req, "main.holiday.template")));

         //画面パラメータをセット
         cmn999Form.addHiddenParam("cmd", "ok");
         cmn999Form.addHiddenParam("man020DspYear", form.getMan020DspYear());

         //パラメータ
         req.setAttribute("cmn999Form", cmn999Form);

         return map.findForward("gf_msg");
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
     private ActionForward __doDownLoad(ActionMapping map,
                                         Man029knForm form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
         throws SQLException, Exception {

         RequestModel reqMdl = getRequestModel(req);

         //テンポラリディレクトリパスを取得
         String tempDir = __getTempDir(req);

         Man029knBiz biz = new Man029knBiz();
         //取込みファイル名称取得
         String fileId = biz.getImportFileName(tempDir);
         log__.debug("tempDir==>" + tempDir);
         log__.debug("fileId==>" + fileId);
         //オブジェクトファイルを取得
         ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
         Object fObj = objFile.load();
         Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
         //添付ファイル保存用のパスを取得する(フルパス)
         String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
         filePath = IOTools.replaceFileSep(filePath);

         //ログ出力処理
         CommonBiz commonBiz = new CommonBiz();
         GsMessage gsMsg = new GsMessage(reqMdl);
         String dspName = gsMsg.getMessage("cmn.admin.setting")
                 + " " + gsMsg.getMessage("main.man029kn.1");
         commonBiz.outPutLogNoDspName(
                 map, reqMdl, gsMsg, con,
                 getInterMessage(reqMdl, "cmn.download"),
                 GSConstLog.LEVEL_INFO, fMdl.getFileName(), dspName);

         //時間のかかる処理の前にコネクションを破棄
         JDBCUtil.closeConnectionAndNull(con);
         //ファイルをダウンロードする
         TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);

         return null;
     }

     /**
      * <br>[機  能]テンポラリディレクトリパスを取得する
      * <br>[解  説]
      * <br>[備  考]
      * @param req リクエスト
      * @return GSTemporaryPathModel
      */
     private String __getTempDir(HttpServletRequest req) {

         GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
         String tempDir = temp.getTempPath(getRequestModel(req),
                 GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
         return tempDir;
     }
}
