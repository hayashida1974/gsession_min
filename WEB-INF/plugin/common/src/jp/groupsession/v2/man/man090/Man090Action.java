package jp.groupsession.v2.man.man090;

import java.io.File;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 アプリケーションログ一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man090Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man090Action.class);
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

        if (cmd.equals("logdownload")) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con)
        throws Exception {

        ActionForward forward = null;

        if (!GSConst.GS_DOMAIN.equals(
                GroupSession.getResourceManager().getDomain(req)
                )) {
            //不正なドメインによるアクセス
            return getSubmitErrorPage(map, req);
        }


        Man090Form thisForm = (Man090Form) form;
        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("backMenu")) {
            //「戻る」処理
            forward = map.findForward("menu");
        } else if (cmd.equals("logdownload")) {
            //「ダウンロード」処理
            forward = __doExport(map, thisForm, req, res, con);
        } else {
            //デフォルト
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res);
        }

        return forward;
    }


    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward フォワード
     * @throws Exception 例外
     */
    private ActionForward __doInit(ActionMapping map,
            Man090Form form,
            HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        Man090ParamModel paramMdl = new Man090ParamModel();
        paramMdl.setParam(form);
        Man090Biz biz = new Man090Biz();
        biz.setInitData(paramMdl, getAppRootPath());
        paramMdl.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] エクスポート処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception ダウンロード失敗
     */
    private ActionForward __doExport(ActionMapping map,
            Man090Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
            throws Exception {

        ActionForward forward = null;
        //ログディレクトリの取得
        String logDir = Man090Biz.getLogFileDir(getAppRootPath());
        log__.debug("logディレクトリ = " + logDir);

        String selectLogName = form.getLogName();
        
        //ファイル一覧を設定
        Enumeration<File> fileList = IOTools.getFiles(logDir);
        
        //ファイル名のハッシュ値リストを取得
        Man090Biz biz = new Man090Biz();
        List<DspAppLogModel> dspFileList = biz.getFileDataList(fileList);
        
        //APPログの取得
        log__.debug("selectLogName = " + selectLogName);
        if (dspFileList != null) {
            boolean equalFlg = false;
            //抽出するログファイル名の取得
            String logFile = GSConst.LOGFILE_NAME + ".log";
            String errorLogFile = GSConst.LOGFILE_NAME_ERROR + ".log";
            log__.debug("logFile = " + logFile);
            
            RequestModel reqMdl = getRequestModel(req);
            CommonBiz cmnBiz = new CommonBiz();
            GsMessage gsMsg = new GsMessage(reqMdl);

            for (DspAppLogModel dspMdl : dspFileList) {
                if (dspMdl.getHashHttpLogName().equals(selectLogName)) {
                    
                    String fileName = dspMdl.getHttpLogName();
                    if (fileName.indexOf(logFile) != -1
                    || fileName.indexOf(errorLogFile) != -1) {
                        
                        //ダウンロードするファイルの作成
                        String fullPath = logDir + File.separator + fileName;
                        TempFileUtil.downloadAtachment(req, res, fullPath, fileName,
                                Encoding.UTF_8);
                        equalFlg = true;
                        
                        //ログ出力
                        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                                getInterMessage(reqMdl, "cmn.download"),
                                GSConstLog.LEVEL_INFO, fileName);
                        break;
                    }
                }
            }
            
            //エラー画面
            if (!equalFlg) {
                //ファイルが無ければエラー画面
                forward = _setNotFileDispParam(map, form, req);
            }
        }
        //ダウンロードのため遷移先なし
        return forward;
    }

    /**
     * [機  能] ファイルが存在しない場合の警告画面の設定処理を行う<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form フォーム
     * @param req リクエスト
     * @return 警告画面遷移
     */
    protected ActionForward _setNotFileDispParam(
        ActionMapping map,
        Man090Form form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        urlForward = map.findForward("mine");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "search.data.notfound";
        cmn999Form.setMessage(msgRes.getMessage(msgState, getInterMessage(req, "cmn.logfile")));
        cmn999Form = __setFormParam(cmn999Form, form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 共通メッセージフォームにフォームパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージフォーム
     * @param form アクションフォーム
     * @return 共通メッセージフォーム
     */
    private Cmn999Form __setFormParam(Cmn999Form cmn999Form, Man090Form form) {
        return cmn999Form;
    }
}
