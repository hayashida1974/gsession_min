package jp.groupsession.v2.rng.rng010;

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
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.AbstractRingiAction;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.rng130.Rng130Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng010Action extends AbstractRingiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng010Action.class);

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
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        Rng010Form thisForm = (Rng010Form) form;

        if (cmd.equals("prevPage")) {
            //前ページクリック
            thisForm.setRng010pageTop(thisForm.getRng010pageTop() - 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            thisForm.setRng010pageTop(thisForm.getRng010pageTop() + 1);
            forward = __doInit(map, thisForm, req, res, con);

        } else if (cmd.equals("rng040")) {
            log__.debug("*** 管理者設定ボタン押下。");
            forward = map.findForward("rng040");

        } else if (cmd.equals("rng080")) {
            log__.debug("*** 個人設定ボタン押下。");
            forward = map.findForward("rng080");

        } else if (cmd.equals("rngEdit")) {
            log__.debug("*** 稟議申請ボタン押下。");
            //forward = map.findForward("rng020");
            forward = map.findForward("rng060");

        } else if (cmd.equals("rng020")) {
            log__.debug("*** タイトル文字列押下。");
            forward = map.findForward("rng020");

        } else if (cmd.equals("rng030")) {
            log__.debug("*** タイトル文字列押下。");
            forward = map.findForward("rng030");

        } else if (cmd.equals("moveSearch")) {
            log__.debug("検索ボタンクリック");
            forward = __doSearch(map, thisForm, req, res, con);

        } else if (cmd.equals("detailedSearch")) {
            log__.debug("詳細検索ボタンクリック");
            forward = map.findForward("search");

        } else if (cmd.equals("delete")) {
            log__.debug("削除ボタンクリック");
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("moveSearch")) {
            log__.debug("検索ボタンクリック");
            forward = map.findForward("search");

        } else if (cmd.equals("deleteConfirm")) {
            log__.debug("削除確認画面からの遷移");
            forward = __doDeleteComplete(map, thisForm, req, res, con);

        } else if (cmd.equals("category")) {
            log__.debug("テンプレート選択");
            forward = map.findForward("rng020");

        } else if (cmd.equals("tempEdit")) {
            log__.debug("テンプレート選択");
            forward = map.findForward("rng060");

        } else if (cmd.equals("changeMode")) {
            log__.debug("フォルダ選択");
            forward = __dochangeMode(map, thisForm, req, res, con);

        } else {
            log__.debug("*** 初期表示を行います。");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doInit(ActionMapping map, Rng010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        con.setAutoCommit(true);

        //処理モード
        int procMode = RngConst.RNG_MODE_JYUSIN;
        if (form != null) {
            form.getRngProcMode();
        }
        log__.debug("procMode = " + procMode);
        Rng010Biz biz = new Rng010Biz();
        BaseUserModel userMdl = getSessionUserModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, getPluginId());

        Rng010ParamModel paramMdl = new Rng010ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, getRequestModel(req), con, adminUser);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 検索ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rng010Form
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doSearch(ActionMapping map, Rng010Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //キーワードチェック
        ActionErrors errors = form.validateCheckSearch(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Rng130Form form130 = new Rng130Form();
        form130.setRngSid(form.getRngSid());
        form130.setRngProcMode(form.getRngProcMode());
        form130.setSvRng130Category(form.getRng010SearchCategory());
        form130.setRngCmdMode(form.getRngCmdMode());
        form130.setRngTemplateMode(form.getRngTemplateMode());
        form130.setRng010orderKey(form.getRng010orderKey());
        form130.setRng010sortKey(form.getRng010sortKey());
        form130.setRng010pageTop(form.getRng010pageTop());
        form130.setRng010pageBottom(form.getRng010pageBottom());
        form130.setRng010adminFlg(form.getRng010adminFlg());
        form130.setRng130Type(form.getRngProcMode());
        form130.setRng130searchSubject1(1);
        form130.setRng130searchSubject2(1);
        form130.setRng130searchSubject3(1);
        form130.setSvRng130Type(form130.getRng130Type());

        switch (form130.getRng130Type()) {
            case RngConst.RNG_MODE_JYUSIN :
                form130.setSltSortKey1(RngConst.RNG_SORT_JYUSIN);
                form130.setRng130orderKey1(RngConst.RNG_ORDER_ASC);
                break;
            case RngConst.RNG_MODE_SINSEI :
                form130.setSltSortKey1(RngConst.RNG_SORT_KAKUNIN);
                form130.setRng130orderKey1(RngConst.RNG_ORDER_DESC);
                break;
            case RngConst.RNG_MODE_KANRYO :
                form130.setSltSortKey1(RngConst.RNG_SORT_KAKUNIN);
                form130.setRng130orderKey1(RngConst.RNG_ORDER_DESC);
                break;
            case RngConst.RNG_MODE_SOUKOU :
                form130.setSltSortKey1(RngConst.RNG_SORT_TOUROKU);
                form130.setRng130orderKey1(RngConst.RNG_ORDER_DESC);
                break;
            case RngConst.RNG_MODE_KOETU :
                form130.setSltSortKey1(RngConst.RNG_SORT_DATE);
                form130.setRng130orderKey1(RngConst.RNG_ORDER_DESC);
                break;
            default :
                form130.setSltSortKey1(RngConst.RNG_SORT_TITLE);
                form130.setRng130orderKey1(RngConst.RNG_ORDER_ASC);
        }
        form130.setSltSortKey2(RngConst.RNG_SORT_TITLE);
        form130.setRng130orderKey2(RngConst.RNG_ORDER_ASC);

        if (!StringUtil.isNullZeroString(form.getRngKeyword())) {
            form130.setSvRngViewAccount(form.getRng010ViewAccount());
            form130.setSvRngKeyword(form.getRngKeyword());
            form130.setSvRng130keyKbn(RngConst.RNG_SEARCHTYPE_AND);
            form130.setSvRng130searchSubject1(1);
            form130.setSvRng130searchSubject2(1);
            form130.setSvRng130searchSubject3(1);
            form130.setRng130searchFlg(1);

            form130.setSvSortKey1(form130.getSltSortKey1());
            form130.setSvRng130orderKey1(form130.getRng130orderKey1());
            form130.setSvSortKey2(form130.getSltSortKey2());
            form130.setSvRng130orderKey2(form130.getRng130orderKey2());
        }
        req.setAttribute("rng130Form", form130);

        return map.findForward("search");
    }

    /**
     * <br>[機  能] 各種確認画面の処理を行う
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
    private ActionForward __doDelete(ActionMapping map,
                                    Rng010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
    throws Exception {

        //稟議削除権限がない場合、不正なアクセスとする。
        if (form.getRngProcMode() != RngConst.RNG_MODE_SOUKOU) {
            RngBiz rngBiz = new RngBiz(con);
            BaseUserModel uModel = getRequestModel(req).getSmodel();
            if (!rngBiz.isDeleteRingi(uModel, con)) {
                return getAuthErrorPage(map, req);
            }
        }

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        Cmn999Form cmn999Form = new Cmn999Form();

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        String msgState = "delete.kakunin.ringi";
        cmn999Form.setMessage(msgRes.getMessage(msgState));
        cmn999Form.setUrlOK(map.findForward("mine").getPath() + "?CMD=deleteConfirm");
        cmn999Form.setUrlCancel(map.findForward("mine").getPath());
        cmn999Form = __setFormParam(cmn999Form, form);
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");

    }


    /**
     * <br>[機  能] 各種確認画面の処理を行う
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
    private ActionForward __dochangeMode(ActionMapping map,
                                    Rng010Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
    throws Exception {

        if (form.getRngProcMode() == RngConst.RNG_MODE_JYUSIN) {
            form.setRng010sortKey(RngConst.RNG_SORT_JYUSIN);
            form.setRng010orderKey(RngConst.RNG_ORDER_ASC);
        } else if (form.getRngProcMode() == RngConst.RNG_MODE_SINSEI) {
            form.setRng010sortKey(RngConst.RNG_SORT_KAKUNIN);
            form.setRng010orderKey(RngConst.RNG_ORDER_DESC);
        } else if (form.getRngProcMode() == RngConst.RNG_MODE_KANRYO) {
            form.setRng010sortKey(RngConst.RNG_SORT_KAKUNIN);
            form.setRng010orderKey(RngConst.RNG_ORDER_DESC);
        } else if (form.getRngProcMode() == RngConst.RNG_MODE_SOUKOU) {
            form.setRng010sortKey(RngConst.RNG_SORT_TOUROKU);
            form.setRng010orderKey(RngConst.RNG_ORDER_DESC);
        } else if (form.getRngProcMode() == RngConst.RNG_MODE_KOETU) {
            form.setRng010sortKey(RngConst.RNG_SORT_DATE);
            form.setRng010orderKey(RngConst.RNG_ORDER_DESC);
        }
        return __doInit(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外発生
     */
    private ActionForward __doDeleteComplete(ActionMapping map,
                                            Rng010Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con) throws Exception {

        RngBiz rngBiz = new RngBiz(con);

        //稟議削除権限がない場合、不正なアクセスとする。
        if (form.getRngProcMode() != RngConst.RNG_MODE_SOUKOU) {
            BaseUserModel uModel = getRequestModel(req).getSmodel();
            if (!rngBiz.isDeleteRingi(uModel, con)) {
                return getAuthErrorPage(map, req);
            }
        }

        Cmn999Form cmn999Form = new Cmn999Form();

        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        boolean commit = false;

        try {
            //稟議の削除を行う
            Rng010Biz biz = new Rng010Biz();
            BaseUserModel userMdl = getSessionUserModel(req);
            Rng010ParamModel paramMdl = new Rng010ParamModel();
            paramMdl.setParam(form);

            List<String> names = biz.getRingiNameList(paramMdl, con);

            biz.deleteRingi(paramMdl, con, userMdl.getUsrsid());
            paramMdl.setFormData(form);

            String msgState = "delete.kanryo.ringi";
            MessageResources msgRes = getResources(req);
            cmn999Form.setMessage(msgRes.getMessage(msgState));

            //戻り先画面を設定
            ActionForward urlForward = map.findForward("mine");
            cmn999Form.setUrlOK(urlForward.getPath());
            cmn999Form = __setFormParam(cmn999Form, form);
            req.setAttribute("cmn999Form", cmn999Form);

            RequestModel reqMdl = getRequestModel(req);
            GsMessage gsMsg = new GsMessage(reqMdl);
            String opCode = "";
            if (form.getRngProcMode() == RngConst.RNG_MODE_SOUKOU) {
                opCode = gsMsg.getMessage("cmn.draft") + gsMsg.getMessage("cmn.delete");
            } else {
                opCode = gsMsg.getMessage("rng.62") + gsMsg.getMessage("cmn.delete");
            }

            String msg = "";
            for (String name : names) {
                if (msg.length() > 0) {
                    msg += "\r\n";
                }
                msg += name;
            }

            //ログ出力処理
            rngBiz.outPutLog(
                    map, opCode,
                    GSConstLog.LEVEL_TRACE, msg,
                    reqMdl);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error(e);
            throw e;

        } finally {
            if (!commit) {
                con.rollback();
            }
        }

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
    private Cmn999Form __setFormParam(Cmn999Form cmn999Form, Rng010Form form) {

        cmn999Form.addHiddenParam("rngSid", form.getRngSid());
        cmn999Form.addHiddenParam("rngCmdMode", form.getRngCmdMode());
        cmn999Form.addHiddenParam("rngApprMode", form.getRngApprMode());
        form.setHiddenParam(cmn999Form, true);

        return cmn999Form;
    }
}
