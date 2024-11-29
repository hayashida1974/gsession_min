package jp.groupsession.v2.usr.usr010;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;

/**
 * <br>[機  能] メイン 管理者設定 グループマネージャー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr010Action extends AdminAction {
    /** 画面ID */
    public static final String SCR_ID = "usr010";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr010Action.class);

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

        //ダウンロードフラグ
        String downLoadFlg = NullDefault.getString(req.getParameter("grpCsvOut"), "");
        downLoadFlg = downLoadFlg.trim();

        if (cmd.equals("exp_ok")) {
            if (downLoadFlg.equals("1")) {
                log__.debug("CSVファイルダウンロード");
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説] コントローラの役割を担います
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
        log__.debug("START");
        ActionForward forward = null;

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("back")) {
            //戻る
            forward = map.findForward("back");
        } else if (cmd.equals("userEdit")) {
            //ユーザーマネージャーへ
            forward = map.findForward("userEdit");
        } else if (cmd.equals("add")) {
            //グループ追加
            forward = map.findForward("add");
        } else if (cmd.equals("edit")) {
            //グループ編集
            forward = __doEdit(map, form, req, res, con);
        } else if (cmd.equals("uview")) {
            //所属ユーザ表示
            forward = __doUview(map, form, req, res, con);
        } else if (cmd.equals("groupImp")) {
            //グループインポート
            forward = map.findForward("import");
        } else if (cmd.equals("groupExp")) {
            //グループエクスポート確認
            forward = __doExport(map, form, req, res, con);
        } else if (cmd.equals("exp_ok")) {
            //グループエクスポート実行
            String downLoadFlg = NullDefault.getString(req.getParameter("grpCsvOut"), "");
            downLoadFlg = downLoadFlg.trim();
            if (downLoadFlg.equals("1")) {
                log__.debug("CSVファイルダウンロード");
                return __doExportOk(map, form, req, res, con);
            }
        } else {
            // デフォルト グループ一覧表示
            forward = map.getInputForward();
        }
        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] グループ編集処理
     * <br>[解  説] 編集ボタンが押下された時の処理を行います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doEdit(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        int usr010grpSid = NullDefault.getInt(req.getParameter("usr010grpSid"), -1);
        log__.debug("usr010grpSid=" + usr010grpSid);
        GsMessage gsMsg = new GsMessage();
        //グループ
        String textGroup = gsMsg.getMessage(req, "cmn.group");

        ActionForward forward = null;
        if (usr010grpSid > -1) {
            //編集時グループラジオ選択有り
            forward = map.findForward("edit");

        } else {
            //エラーメッセージ表示
            ActionErrors errors = new ActionErrors();
            String eprefix = "sltgp.";
            ActionMessage msg = new ActionMessage("error.select.required.text", textGroup);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
            addErrors(req, errors);
            forward = map.getInputForward();
        }
        return forward;
    }

    /**
     * <br>[機  能] 所属ユーザ一覧処理
     * <br>[解  説] 所属ユーザ一覧ボタンが押下された時の処理を行います
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    private ActionForward __doUview(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        int usr010grpSid = NullDefault.getInt(req.getParameter("usr010grpSid"), -1);
        log__.debug("usr010grpSid=" + usr010grpSid);
        GsMessage gsMsg = new GsMessage();
        //グループ
        String textGroup = gsMsg.getMessage(req, "cmn.group");

        ActionForward forward = map.getInputForward();
        if (usr010grpSid > -1) {
            //編集時グループラジオ選択有り
            forward = map.findForward("uview");
        } else {
            //エラーメッセージ表示
            ActionErrors errors = new ActionErrors();
            String eprefix = "sltgp.";
            ActionMessage msg = new ActionMessage("error.select.required.text", textGroup);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
            addErrors(req, errors);
            forward = map.getInputForward();
        }
        return forward;
    }

    /**
     * <p>エクスポートボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doExport(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        //確認画面へ
        log__.debug("エクスポート確認画面へ");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("expok");
        cmn999Form.setUrlOK(urlForward.getPath());
        urlForward = map.findForward("expcancel");
        cmn999Form.setUrlCancel(urlForward.getPath());

        cmn999Form.setMessage(msgRes.getMessage("export.kakunin.group"));


//        cmn999Form.addHiddenParam("usr030SearchFlg", form.getUsr030SearchFlg());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;

    }

    /**
     * エクスポート処理実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doExportOk(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
    throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //グループ情報
        String textGroupInfo = gsMsg.getMessage("user.src.59");

        log__.debug("エクスポート処理実行");

        //テンポラリディレクトリパスを取得
        UsrCommonBiz usrCmnBiz = new UsrCommonBiz(reqMdl);
        String tempDir = usrCmnBiz.getTempDir(SCR_ID);

        //CSVファイルを作成
        String fileName = GrpCsvWriter.FILE_NAME;
        GrpCsvWriter write = new GrpCsvWriter(reqMdl);
        write.outputCsv(con, tempDir);

        String fullPath = tempDir + fileName;
        //ダウンロード
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        usrCmnBiz.deleteTempDir(SCR_ID);

        /** メッセージ エクスポート **/
        String export = gsMsg.getMessage("cmn.export");

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                export, GSConstLog.LEVEL_INFO, textGroupInfo);

        return null;
    }
}
