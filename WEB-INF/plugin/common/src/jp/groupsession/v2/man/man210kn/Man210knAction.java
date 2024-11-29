package jp.groupsession.v2.man.man210kn;

import java.sql.Connection;
import java.sql.SQLException;
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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 管理者設定 モバイル使用一括設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man210knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man210knAction.class);

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
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("START_Man210kn");
        ActionForward forward = null;

        Man210knForm thisForm = (Man210knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("back_mblUseConf")) {
            log__.debug("戻る");
            forward = map.findForward("back_mblUseConf");

        } else if (cmd.equals("touroku")) {
            log__.debug("登録");
            forward = __doTouroku(map, thisForm, req, res, con);
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Man210kn");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Man210knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        //初期表示情報を画面にセットする
        con.setAutoCommit(true);
        Man210knParamModel paramMdl = new Man210knParamModel();
        paramMdl.setParam(form);
        Man210knBiz biz = new Man210knBiz();
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 登録ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doTouroku(
        ActionMapping map,
        Man210knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        ActionErrors errors = null;
        RequestModel reqMdl = getRequestModel(req);

        if (form.getMan210ObjKbn() == 1) {
            // 対象が指定ユーザ
            errors = form.validateCheck(reqMdl);

            if (errors.size() > 0) {

                addErrors(req, errors);

                log__.debug("入力エラー");
                return __doInit(map, form, req, res, con);
            }
        }

        //ログインユーザSIDを取得
        int userSid = getSessionUserSid(req);

        //更新処理を行う
        Man210knParamModel paramMdl = new Man210knParamModel();
        paramMdl.setParam(form);
        Man210knBiz biz = new Man210knBiz();
        biz.doUpdate(paramMdl, con, userSid);
        paramMdl.setFormData(form);

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);

        String value = "";
        // 対象
        value = "[" + gsMsg.getMessage("cmn.target") + "] ";
        if (paramMdl.getMan210ObjKbn() == GSConstMain.MOBILE_USER_ALL) {
            value += gsMsg.getMessage("cmn.alluser");
        } else if (paramMdl.getMan210ObjKbn() == GSConstMain.MOBILE_USER_SELECTED) {
            String[] memberList = paramMdl.getMan210userSid();
            CmnUsrmDao usrDao = new CmnUsrmDao(con);
            List<BaseUserModel> usrList = usrDao.getSelectedUserList(memberList);
            for (int i = 0; i < memberList.length; i++) {
                if (i > 0) {
                    value += ", ";
                }
                value += usrList.get(i).getUsiseimei();
            }
        }
        // モバイル使用
        value += "\r\n" + "[" + gsMsg.getMessage("cmn.mobile.use") + "] ";
        if (paramMdl.getMan210UseKbn() == GSConstMain.MOBILE_USE_KBN_YES) {
            value += gsMsg.getMessage("main.man210kn.3");
            if (paramMdl.getMan210NumCont().equals(String.valueOf(GSConstUser.UID_CONTROL))) {
                value += "\r\n" + gsMsg.getMessage("cmn.login.control.identification.number");
                if (paramMdl.getMan210NumAutAdd().equals(
                        String.valueOf(GSConstUser.UID_AUTO_REG_OK))) {
                    value += "\r\n" + gsMsg.getMessage("main.man210.3");
                }
            }
        } else if (paramMdl.getMan210UseKbn() == GSConstMain.MOBILE_USE_KBN_NO) {
            value += gsMsg.getMessage("main.man210kn.4");
        }

        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.change"), GSConstLog.LEVEL_INFO, value);

        //完了画面を表示
        return __setKanryoDsp(map, form, req);
    }

    /**
     * [機  能] 完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     */
    private ActionForward __setKanryoDsp(
        ActionMapping map,
        Man210knForm form,
        HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("updateMblUse");
        cmn999Form.setUrlOK(forwardOk.getPath());

        MessageResources msgRes = getResources(req);

        cmn999Form.setMessage(
        msgRes.getMessage("cmn.kanryo.object",
                getInterMessage(req, GSConstMain.TEXT_MBL_CONF)));

        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

}
