package jp.groupsession.v2.rng.rng270;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 稟議個人設定代理人画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng270Action extends AbstractRingiAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng270Action.class);

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
        Rng270Form thisForm = (Rng270Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("conf")) {
            //設定ボタンクリック
            forward = __doDecision(map, thisForm, req, res, con);
        } else if (cmd.equals("backConfMenu")) {
            //戻るボタンクリック
            forward = map.findForward("confmenu");
        } else if (cmd.equals("reload")) {
            //リロード
            forward = __reload(map, thisForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END");
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
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Rng270Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        con.setAutoCommit(true);
        Rng270ParamModel paramMdl = new Rng270ParamModel();
        paramMdl.setParam(form);
        Rng270Biz biz = new Rng270Biz();
        biz.setInitData(paramMdl, con, getSessionUserSid(req), getRequestModel(req));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] リロードを行う
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
    private ActionForward __reload(ActionMapping map,
        Rng270Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        con.setAutoCommit(true);
        Rng270ParamModel paramMdl = new Rng270ParamModel();
        paramMdl.setParam(form);
        Rng270Biz biz = new Rng270Biz();
        biz.reload(paramMdl, con, getRequestModel(req));
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 設定ボタンクリック時の処理
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
    private ActionForward __doDecision(ActionMapping map,
        Rng270Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        //入力チェック
        Rng270Biz biz = new Rng270Biz();
        Rng270ParamModel paramMdl = new Rng270ParamModel();
        paramMdl.setParam(form);

        ActionErrors errors = form.validateCheck(req);
        con.setAutoCommit(false);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            biz.reload(paramMdl, con, getRequestModel(req));
            return map.getInputForward();
        }
        
        //トークンチェック
        if (!isTokenValid(req, true)) {
            return map.findForward("gf_submit");
        }

        boolean commit = false;
        try {
            biz.update(paramMdl, userSid, con);
            paramMdl.setFormData(form);

            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);

            //ログ出力処理
            RngBiz rngBiz = new RngBiz(con);
            String opCode = gsMsg.getMessage("cmn.change");
            String msg = "";

            //[稟議代理人設定]
            form.getUsrgroupselect().init(con, reqMdl, new String[] {"target"},
                    new String[] {""}, "", null, null);
            msg += "[" + gsMsg.getMessage("rng.rng120.10") + "] ";
            List<UsrLabelValueBean> setUsrList = form.getUsrgroupselect().getSelectedList("target");
            if (setUsrList != null && setUsrList.size() != 0) {
                int setUsrListSize = setUsrList.size();
                for (int i = 0; i < setUsrListSize; i++) {
                    msg += setUsrList.get(i).getLabel();
                    if (i != setUsrListSize - 1) {
                        msg +=  ", ";
                    }
                }
                msg += "\r\n";

                //[稟議代理期間設定]
                msg += "[" + gsMsg.getMessage("rng.rng120.11") + "] ";
                msg += form.getRng270DairiStart() + " - ";
                if (form.getRng270DairiLast() == RngConst.RNG_DAIRI_KIKAN) {
                    msg += form.getRng270DairiFinish();
                }

            } else {
                msg += gsMsg.getMessage("cmn.notset");
            }

            rngBiz.outPutLog(
                    map, opCode, GSConstLog.LEVEL_INFO, msg,
                    reqMdl);

            commit = true;
        } catch (Exception e) {
            log__.error("稟議代理設定更新エラー", e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }
        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Rng270Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("confmenu");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        String msg = gsMsg.getMessage(req, "rng.rng120.10");

        //メッセージセット
        String msgState = "hensyu.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState, msg));

        cmn999Form.addHiddenParam("rngTemplateMode", form.getRngTemplateMode());
        
        form.setHiddenParam(cmn999Form, false);
        form.setConfHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

    }
}

