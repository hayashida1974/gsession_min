package jp.groupsession.v2.man.man150kn;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IGsResourceManager;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CliPluginModel;
import jp.groupsession.v2.lic.LicenseModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 ライセンスファイル登録・更新確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man150knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man150knAction.class);

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
         Man150knForm manform = (Man150knForm) form;

         //コマンドパラメータ取得
         String cmd = NullDefault.getString(req.getParameter("CMD"), "");
         cmd = cmd.trim();

         //インポートボタン押下
         if (cmd.equals("import_ok")) {
             log__.debug("インポートボタン押下");
             forward = __doImport(map, manform, req, res, con);
         //戻るボタン押下
         } else if (cmd.equals("backToImport")) {
             log__.debug("戻るボタン押下");
             forward = map.findForward("backToImport");
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
      * @return ActionForward
     * @throws Exception CSV情報取得時例外
      */
     private ActionForward __doInit(ActionMapping map,
                                     Man150knForm form,
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

         //再入力チェック
         ActionErrors errors = form.validateCheck(reqMdl, tempDir, gs, gsUid);
         if (!errors.isEmpty()) {
             addErrors(req, errors);
             return map.getInputForward();
         }

         //取込情報取得
         Man150knParamModel paramMdl = new Man150knParamModel();
         paramMdl.setParam(form);
         Man150knBiz biz = new Man150knBiz();
         biz.setInitData(paramMdl, reqMdl, tempDir);
         paramMdl.setFormData(form);

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
                                       Man150knForm form,
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
         GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
         String tempDir = temp.getTempPath(getRequestModel(req),
                 GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

         GSContext gs = getGsContext();
         CmnContmDao cntDao = new CmnContmDao(con);
         String gsUid = cntDao.getGsUid();

         //再入力チェック
         ActionErrors errors = form.validateCheck(reqMdl, tempDir, gs, gsUid);
         if (!errors.isEmpty()) {
             addErrors(req, errors);
             return map.getInputForward();
         }

         //ライセンスファイル取込
         String appRootPath = getAppRootPath();
         String domain = GroupSession.getResourceManager().getDomain(req);
         Man150knBiz biz = new Man150knBiz();
         biz.importLicenseFile(appRootPath, tempDir, gs, domain, con);

         //テンポラリディレクトリのファイル削除
         temp.deleteTempPath(getRequestModel(req), GSConstMain.PLUGIN_ID_MAIN,
                 TEMP_DIRECTORY_ID);

         //システム情報を送信する
         __SendSystemInfo(req, res, con);

         //ログ出力
         GsMessage gsMsg = new GsMessage(reqMdl);
         CommonBiz cmnBiz = new CommonBiz();
         cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                 getInterMessage(reqMdl, "cmn.import"), GSConstLog.LEVEL_INFO,
                           getInterMessage(req, GSConstMain.TEXT_LICENSE_FILE));

         return __doImportComp(map, form, req, res, con);
     }

     /**
     * <br>[機  能] システム情報を送信する
     * <br>[解  説] システム情報の他に、初回起動の時間も送信
     * <br>[備  考]
     *
      * @param req リクエスト
      * @param res レスポンス
      * @param con コネクション
      * @throws Exception 実行時例外
     */
    private void __SendSystemInfo(
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            ) throws Exception {

        ServletContext context = getServlet().getServletContext();

        String confDbDir = ConfigBundle.getValue("GSDATA_DIR");
        if (StringUtil.isNullZeroString(confDbDir)) {
            confDbDir = context.getRealPath("/");
        }

        IGsResourceManager iGsManager = GroupSession.getResourceManager();
        GSContext gs = getGsContext();
        LicenseModel lmdl = null;

        if (gs != null) {
            lmdl = (LicenseModel) iGsManager.getLicenseMdl(iGsManager.getDomain(req));
        }

        log__.info("利用状況パラメータの取得開始");
        CommonBiz cmnBiz = new CommonBiz();
        //システム情報パラメータ
        String sysInfoParam =
                cmnBiz.getSystemInfoReqParam(con, getRequestModel(req).getDomain(), confDbDir,
                        getAppRootPath(), getTempPath(req), lmdl);
        //プラグイン利用状況リストパラメータ
        PluginConfig pconfig = getPluginConfig(req);
        List <CliPluginModel> pluginList = cmnBiz.getPluginInfo(con, pconfig);
        String pluginInfoParam = cmnBiz.getPluginInfoReqParam(pluginList);
        
        String dataUsedParam = cmnBiz.getDatausedParam(con, pconfig);
        
        String reqParam = sysInfoParam + pluginInfoParam + dataUsedParam;
        log__.info("利用状況パラメータの取得完了");
        log__.info("GS利用状況送信内容:" + reqParam);
        HttpURLConnection hpCon = null;
        StringBuffer result = new StringBuffer();
        String repoUrl = NullDefault.getString(ConfigBundle.getValue("SYSTEM_REPORT_URL"), "");
        try {

            URL url = new URL(repoUrl);
            hpCon = (HttpURLConnection) url.openConnection();
            hpCon.setDoOutput(true);
            hpCon.setRequestMethod("POST");
            hpCon.setUseCaches(false);
            hpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;");
            hpCon.setConnectTimeout(100000);
            OutputStreamWriter out = new OutputStreamWriter(hpCon.getOutputStream());
            out.write(reqParam);
            out.close();
            hpCon.connect();
            // HTTPレスポンスコード
            final int status = hpCon.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                log__.info("利用状況送信処理に成功");
                // 通信に成功した
                // テキストを取得する
                final InputStream in = hpCon.getInputStream();
                String encoding = hpCon.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                // 1行ずつテキストを読み込む
                while ((line = bufReader.readLine()) != null)  {
                    result.append(line);
                }
                bufReader.close();
                inReader.close();
                in.close();
            }

        } catch  (Exception e)  {
            //外部公開してない場合エラーが発生する
            log__.warn("利用状況送信処理で例外発生：" + e);
        } finally {
            if (hpCon != null) {
                // コネクションを切断
                hpCon.disconnect();
            }
        }

     }

     /**
      * <br>[機  能] ライセンスファイル取込完了後の画面遷移設定
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
                                           Man150knForm form,
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
         cmn999Form.setMessage(
                 msgRes.getMessage(
                         "comp.regorupd.data",
                         getInterMessage(req, GSConstMain.TEXT_LICENSE_FILE)));

         req.setAttribute("cmn999Form", cmn999Form);
         return map.findForward("gf_msg");
     }
}