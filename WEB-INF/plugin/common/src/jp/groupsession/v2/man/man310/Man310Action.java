package jp.groupsession.v2.man.man310;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.man.man320.Man320Biz;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] メイン インフォメーション詳細画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man310Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man310Action.class);

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

        if (cmd.equals("fileDownload") || cmd.equals("getPhotoFile")) {
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

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        Man310Form thisForm = (Man310Form) form;
        //パラメータからユーザSIDをセット
        thisForm.setMan310SelectedSid(req.getParameter("imssid"));
        int imssid = NullDefault.getInt(req.getParameter("imssid"), -1);

        //閲覧可能か判定
        int sessionUsrSid = getSessionUserSid(req);
        Man320Biz biz = new Man320Biz();
        UDate date = new UDate();
        if (!biz.isDspAuthMsg(sessionUsrSid, date, imssid, con)) {
            //アクセスエラー
            log__.warn("閲覧できないインフォメーションです。");
            __setErrorPageParam(map, req, thisForm);
            return map.findForward("gf_msg");
        }

        ActionForward forward = null;
        if (cmd.equals("fileDownload")) {
            //添付ファイルリンククリッククリック
            forward = __doDownLoad(map, thisForm, req, res, con);
        } else if (cmd.equals("tempview")) {
            //添付ファイル表示
            forward = __doTempView(map, thisForm, req, res, con);
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
     * @return ActionForward 画面遷移先
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Man310Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        Man310ParamModel paramMdl = new Man310ParamModel();
        paramMdl.setParam(form);
        Man310Biz biz = new Man310Biz();
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
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
        Man310Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {


        int imsSid = NullDefault.getInt(form.getMan310SelectedSid(), -1);
        long binSid = form.getMan310binSid();

        Man310Biz biz = new Man310Biz();
        if (biz.existInfoBinData(con, imsSid, binSid)) {
            CommonBiz cmnBiz = new CommonBiz();
            CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, binSid,
                    GroupSession.getResourceManager().getDomain(req));

            if (cbMdl != null) {
                //ログ出力処理
                RequestModel reqMdl = getRequestModel(req);
                GsMessage gsMsg = new GsMessage(reqMdl);
                cmnBiz.outPutCommonLog(
                        map, reqMdl, gsMsg, con,
                        getInterMessage(reqMdl, "cmn.download"),
                        GSConstLog.LEVEL_INFO,
                        "[" + getInterMessage(reqMdl, "cmn.information") + "]"
                        + cbMdl.getBinFileName());
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadAtachment(req, res, cbMdl, getAppRootPath(),
                                            Encoding.UTF_8);
            }
        }

        return null;
    }

    /**
     * <br>[機  能] 添付ファイルをブラウザ内に表示処理
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
    private ActionForward __doTempView(
        ActionMapping map,
        Man310Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException, Exception {

        int imsSid = NullDefault.getInt(form.getMan310SelectedSid(), -1);
        long binSid = form.getMan310binSid();

        Man310Biz biz = new Man310Biz();
        if (biz.existInfoBinData(con, imsSid, binSid)) {

            CommonBiz cmnBiz = new CommonBiz();
            CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, form.getMan310binSid(),
                    GroupSession.getResourceManager().getDomain(req));

            if (cbMdl != null) {
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
                                            Encoding.UTF_8);
            }
        }

//        CommonBiz cmnBiz = new CommonBiz();
//        CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, form.getMan310binSid(),
//                GroupSession.getResourceManager().getDomain(req));
//
//        if (cbMdl != null) {
//            JDBCUtil.closeConnectionAndNull(con);
//
//            //ファイルをダウンロードする
//            TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(),
//                                        Encoding.UTF_8);
//        }
        return null;
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setErrorPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Man310Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);

        MessageResources msgRes = getResources(req);
        String msgState = "error.access.window.colse";
        cmn999Form.setMessage(msgRes.getMessage(msgState));
        req.setAttribute("cmn999Form", cmn999Form);
    }
}
