package jp.groupsession.v2.usr.usr061;

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

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.usr060.Usr060Biz;
import jp.groupsession.v2.usr.usr060.Usr060ParamModel;
/**
 *
 * <br>[機  能] ワンタイムパスワード通知先メールアドレス設定（GS管理者） アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr061Action extends AbstractGsAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr061Action.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        Usr061Form thisForm = (Usr061Form) form;
        //アクセス権限チェック
        forward = __doImmigration(map, thisForm, req, con);
        if (forward != null) {
            return forward;
        }

        //初回表示
        if (cmd.equals("otp_sendto_address")) {
            forward = __doInit(map, thisForm, req, res, con);
        //戻る
        } else if (cmd.equals("usr061Back")) {
            forward = map.findForward("back");
        //OK
        } else if (cmd.equals("usr061Ok")) {
            forward = __doOk(map, thisForm, req, res, con);
        //確定
        } else if (cmd.equals("usr061kakutei")) {
            forward = __doComplete(map, thisForm, req, res, con);
        //再描画
        } else {
            forward = __doDsp(map, thisForm, req, res, con);
        }
        return forward;
    }

    /**
    *
    * <br>[機  能] 描画設定処理を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map map マップ
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return 遷移先
    * @throws Exception 実行時例外
    */
    private ActionForward __doDsp(ActionMapping map, Usr061Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return map.getInputForward();
    }

    /**
    *
    * <br>[機  能] 登録確定処理を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map map マップ
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return 遷移先
    * @throws Exception 実行時例外
    */
    private ActionForward __doComplete(ActionMapping map, Usr061Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con) throws Exception {
        con.setAutoCommit(false);

        Usr061Biz biz = new Usr061Biz(getRequestModel(req), con);
        boolean commit = false;
        try {
            Usr060ParamModel param = new Usr060ParamModel();
            param.setParam(form);

            RequestModel reqMdl = getRequestModel(req);
            ActionErrors errors = form.validateAddress(reqMdl);
            if (errors.size() > 0) {
                addErrors(req, errors);
                return __doDsp(map, form, req, res, con);
            }

            biz.doUpdateAddress(param);

            param.setFormData(param);

            con.commit();
            GsMessage gsMsg = new GsMessage(req);
            /** メッセージ 変更 **/
            String change = gsMsg.getMessage("cmn.change");

            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.outPutCommonLog(map, getRequestModel(req), gsMsg, con,
                    change, GSConstLog.LEVEL_INFO,
                    gsMsg.getMessage("user.157") + ":" + param.getUsr060SendToAddress()
                    );
            con.commit();
            commit = true;

        } finally {
            if (!commit) {
                con.rollback();
            }
            con.setAutoCommit(true);
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);

        ActionForward urlForward = map.findForward("back");
        cmn999Form.setWtarget(Cmn999Form.WTARGET_TOP);
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();
        //パスワード
        String textPassWord = gsMsg.getMessage(req, "user.157");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("hensyu.henkou.kanryo.object", textPassWord));

        cmn999Form.addHiddenAll(form, form.getClass(), "");


        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");    }

    /**
    *
    * <br>[機  能] OKボタン処理
    * <br>[解  説]
    * <br>[備  考]
    * @param map map マップ
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return 遷移先
    * @throws Exception 実行時例外
    */
    private ActionForward __doOk(ActionMapping map, Usr061Form form,
            HttpServletRequest req, HttpServletResponse res,
            Connection con) throws Exception {
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateAddress(reqMdl);
        if (errors.size() > 0) {
            addErrors(req, errors);
            return __doDsp(map, form, req, res, con);
        }
        //共通確認画面遷移
        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);


        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("kakutei");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //キャンセルボタンクリック時遷移先
        ActionForward forwardCanc = map.findForward("dsp");
        cmn999Form.setUrlCancel(forwardCanc.getPath());
        GsMessage gsMsg = new GsMessage();
        //パスワード
        String textPassWord = gsMsg.getMessage(req, "user.157");
        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("setting.kakunin.data",
                     textPassWord, form.getUsr060SendToAddress()));

        cmn999Form.addHiddenAll(form, form.getClass(), "");

        req.setAttribute("cmn999Form", cmn999Form);
        saveToken(req);
        return map.findForward("gf_msg");
    }


    /**
    *
    * <br>[機  能] 初期化処理を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param map map マップ
    * @param form フォーム
    * @param req リクエスト
    * @param res レスポンス
    * @param con コネクション
    * @return 遷移先
    * @throws Exception 実行時例外
    */
    private ActionForward __doInit(ActionMapping map, Usr061Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {
        Usr060ParamModel param = new Usr060ParamModel();
        param.setParam(form);
        Usr060Biz biz = new Usr060Biz(getRequestModel(req), con);
        biz.doInit(param, true);
        param.setFormData(form);
        return __doDsp(map, form, req, res, con);
    }

    /**
     *
     * <br>[機  能] アクセス権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return エラー時遷移先
     */
    private ActionForward __doImmigration(ActionMapping map,
            Usr061Form form, HttpServletRequest req, Connection con) {
        boolean adminUser = false;
        try {
            CommonBiz cmnBiz = new CommonBiz();
            BaseUserModel smodel = getSessionUserModel(req);
            adminUser = cmnBiz.isPluginAdmin(con, smodel, getPluginId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (adminUser) {
            return null;
        } else {
            return getSubmitErrorPage(map, req);
        }
    }


    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }
}
