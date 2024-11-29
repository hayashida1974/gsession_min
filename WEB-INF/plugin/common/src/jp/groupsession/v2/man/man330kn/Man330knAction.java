package jp.groupsession.v2.man.man330kn;

import java.sql.Connection;
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

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man330.Man330Biz;
import jp.groupsession.v2.man.man330kn.model.Man330knCsvModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.IUserGroupListener;
import jp.groupsession.v2.usr.UserUtil;

/**
 * <br>[機  能] メイン 所属情報一括設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man330knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man330knAction.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "man330";

    /** CMD:インポート実行ボタンクリック */
    public static final String CMD_EXE = "man330kn_commit";
    /** CMD:インポート実行ボタンクリック */
    public static final String CMD_BACK = "man330kn_back";

    /**
     * <br>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @throws GSException GS用汎実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception, GSException {

        log__.debug("START_MAN330kn");

        ActionForward forward = null;
        Man330knForm thisForm = (Man330knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        log__.debug("CMD==>" + cmd);
        if (cmd.equals(CMD_EXE)) {
            //登録実行
            forward = __doCommit(map, thisForm, req, res, con);
        } else if (cmd.equals(CMD_BACK)) {
            //戻る
            forward = map.findForward(CMD_BACK);
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("START_MAN330kn");

        return forward;
    }

    /**
     * <br>初期表示処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行時例外
     * @return アクションフォーワード
     */
    private ActionForward __doInit(ActionMapping map, Man330knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception {

        log__.debug("初期表示");

        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        //全グループIDを取得
        Man330Biz biz = new Man330Biz(con);
        List<String> allGpIdList = biz.getAllGrpIdList();

        //全ユーザIDを取得
        List<String> allUsrIdList = biz.getAllUsrIdList();

        //再入力チェック
        ActionErrors errors = form.validateCheck(reqMdl, tempDir, con, allGpIdList, allUsrIdList);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        String fileName = __getFileName(tempDir);
        form.setMan330knFileName(fileName);

        //取込み予定情報を取得
        Man330knCsvImport csvImport = new Man330knCsvImport(0, con);
        form.setMan330knImpList(csvImport.importCsv(tempDir));

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>確定ボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     * @throws GSException GS用汎実行例外
     */
    private ActionForward __doCommit(ActionMapping map, Man330knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws Exception, GSException {

        log__.debug("インポート実行");

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        RequestModel reqMdl = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);

        con.setAutoCommit(false);
        //全グループIDを取得
        Man330Biz biz = new Man330Biz(con);
        List<String> allGpIdList = biz.getAllGrpIdList();

        //全ユーザIDを取得
        List<String> allUsrIdList = biz.getAllUsrIdList();

        //再入力チェック
        ActionErrors errors = form.validateCheck(reqMdl, tempDir, con, allGpIdList, allUsrIdList);
        if (errors.size() > 0) {
            log__.debug("再チェックNG");
            addErrors(req, errors);
            return map.getInputForward();
        }
        CommonBiz cmnBiz = new CommonBiz();
         //取込み処理
        log__.debug("ユーザ取り込み開始");
        boolean commit = false;
        try {

            Man330knCsvImport csvImport = new Man330knCsvImport(1, userSid, con);

            //ユーザリスナー取得
            IUserGroupListener[] lis = UserUtil.getUserListeners(getPluginConfig(req));
            csvImport.setLis(lis);

            List<Man330knCsvModel> grpDatalist = csvImport.importCsv(tempDir);
            long num = 0;
            if (grpDatalist != null) {
                num = grpDatalist.size();
            }

            GsMessage gsMsg = new GsMessage(reqMdl);
            //メッセージ インポート
            String strImport = gsMsg.getMessage("cmn.import");

            //ログ出力
            cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                    strImport, GSConstLog.LEVEL_INFO,
                    "[count]" + num);

            commit = true;
        } catch (Exception e) {
            log__.error("ユーザCSVの取り込みに失敗しました。", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
            //TEMPディレクトリ削除
            temp.deleteTempPath(getRequestModel(req),
                    GSConstMain.PLUGIN_ID_MAIN, TEMP_DIRECTORY_ID);
        }

        //完了画面遷移
        __setKanryou(map, req, form, "touroku.kanryo.object");

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 添付ファイルの名称を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir 添付ディレクトリPATH
     * @return String ファイル名
     * @throws IOToolsException 添付ファイルへのアクセスに失敗
     */
    private String __getFileName(String tempDir) throws IOToolsException {
        String ret = null;
        List<String> fileList = IOTools.getFileNames(tempDir);
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }
                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                ret = fMdl.getFileName();
                if (ret != null) {
                    return ret;
                }
            }
        }
        return ret;
    }

    /**
     * [機  能] 登録完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @param form フォーム
     * @param msgState 完了画面に表示するメッセージのキー
     */
    private void __setKanryou(ActionMapping map,
                               HttpServletRequest req,
                               Man330knForm form,
                               String msgState) {

        GsMessage gsMsg = new GsMessage();

        //所属情報一括設定
        String textUser = gsMsg.getMessage(req, "main.memberships.conf");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("mainAdmSetting");
        cmn999Form.setUrlOK(urlForward.getPath());

        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textUser));

        req.setAttribute("cmn999Form", cmn999Form);
    }
}
