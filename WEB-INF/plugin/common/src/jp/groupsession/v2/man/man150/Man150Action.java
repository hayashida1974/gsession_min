package jp.groupsession.v2.man.man150;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;

/**
 * <br>[機  能] メイン 管理者設定 ライセンスファイル登録・更新画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man150Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man150Action.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man150";

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
         Man150Form manform = (Man150Form) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //インポートボタン押下
         if (cmd.equals("import")) {
             log__.debug("インポートボタン押下");
             forward = __doImportCheck(map, manform, req, res, con);
         //戻るボタン押下
         } else if (cmd.equals("backKanriMenu")) {
             log__.debug("戻るボタン押下");
             GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
             temp.deleteTempPath(getRequestModel(req), GSConstMain.PLUGIN_ID_MAIN,
                     TEMP_DIRECTORY_ID);
             forward = map.findForward("backKanriMenu");
         //確認で戻るボタン押下
         } else if (cmd.equals("backToImport")) {
             log__.debug("確認画面で戻るボタン押下");
             forward = __doDsp(map, manform, req, res, con);
         //初期表示処理
         } else {
             log__.debug("初期表示処理");
             forward = __doInit(map, manform, req, res, con);
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
      * @throws Exception 実行時例外
      * @return ActionForward
      */
     private ActionForward __doInit(ActionMapping map,
                                     Man150Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
         throws Exception {

         //テンポラリディレクトリのファイル削除を行う
         GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
         temp.deleteTempPath(getRequestModel(req),
                 GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
         temp.createTempDir(getRequestModel(req),
                 GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

         return __doDsp(map, form, req, res, con);
     }

     /**
      * <br>[機  能] 再表示を行う
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws Exception 実行時例外
      * @return ActionForward
      */
     private ActionForward __doDsp(ActionMapping map,
                                    Man150Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
         throws Exception {

         RequestModel reqMdl = getRequestModel(req);

         //テンポラリディレクトリパスを取得
         GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
         String tempDir = temp.getTempPath(getRequestModel(req),
                 GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

         //初期表示情報を画面にセットする
         Man150ParamModel paramMdl = new Man150ParamModel();
         paramMdl.setParam(form);
         Man150Biz biz = new Man150Biz();
         biz.setInitData(
                 paramMdl, reqMdl, tempDir, GroupSession.getResourceManager().getDomain(req));
         paramMdl.setFormData(form);

         //トランザクショントークン設定
         saveToken(req);

         return map.getInputForward();
     }

     /**
      * <br>[機  能] インポートボタン押下時処理
      * <br>[解  説]
      * <br>[備  考]
      *
      * @param map アクションマッピング
      * @param form アクションフォーム
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @return ActionForward
     * @throws Exception インポート処理時例外
      */
     private ActionForward __doImportCheck(ActionMapping map,
                                            Man150Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
         throws Exception {

         RequestModel reqMdl = getRequestModel(req);

         //テンポラリディレクトリパスを取得
         GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
         String tempDir = temp.getTempPath(getRequestModel(req),
                 GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

         GSContext gs = getGsContext();
         CmnContmDao cntDao = new CmnContmDao(con);
         String gsUid = cntDao.getGsUid();

         ActionErrors errors = form.validateCheck(reqMdl, tempDir, gs, gsUid);
         if (!errors.isEmpty()) {
             addErrors(req, errors);
             return __doDsp(map, form, req, res, con);
         }

         return map.findForward("doImport");
     }
}