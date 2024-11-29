package jp.groupsession.v2.man.man320;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnInfoBinDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoMsgDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoTagDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] メイン インフォメーション一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man320Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man320Action.class);

    /**
     * <br>アクション実行
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
        ActionForward forward = null;
        Man320Form thisForm = (Man320Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("cmd = " + cmd);

        //アクセス権限チェック　※管理者or登録許可ユーザ
        RequestModel reqMdl = getRequestModel(req);
        Man320Biz biz = new Man320Biz();
        if (!biz.isInfoAdminAuth(reqMdl, con)) {
            //権限エラー
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("320_back")) {
            //戻るボタンクリック
            forward = __doBack(map, thisForm, req, res, con);

        } else if (cmd.equals("changeMode")) {
            //ページコンボ変更
            forward = __doChangeMode(map, thisForm, req, res, con);

        } else if (cmd.equals("arrorw_left")) {
            //左矢印押下
            forward = __doPageMinus(map, thisForm, req, res, con);

        } else if (cmd.equals("arrorw_right")) {
            //右矢印押下
            forward = __doPagePlus(map, thisForm, req, res, con);

        } else if (cmd.equals("man320add")) {
            //新規登録
            forward = map.findForward("add");
        } else if (cmd.equals("man320edit")) {
            //編集
            forward = map.findForward("add");
        } else if (cmd.equals("man320delete")) {
            //一括削除ボタン押下
            forward = __doDeleteKn(map, thisForm, req, res, con);

        } else if (cmd.equals("deleteOk")) {
            //一括削除実行
            forward = __doDelete(map, thisForm, req, res, con);

        } else if (cmd.equals("admconf")) {
            //管理者設定
            forward = map.findForward("admconf");

        } else {
            //初期表示
            __doInit(map, thisForm, req, res, con);
            forward = map.getInputForward();
        }

        return forward;
    }

    /**
     * <br>初期表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
            Man320Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
    throws SQLException {
        log__.debug("初期表示");

        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        con.setAutoCommit(true);
        //初期表示設定
        Man320ParamModel paramMdl = new Man320ParamModel();
        paramMdl.setParam(form);
        Man320Biz biz = new Man320Biz();
        biz.setInitData(paramMdl, con, getPluginConfig(req), getRequestModel(req));
        paramMdl.setFormData(form);

        //管理者設定
        if (usModel.getAdminFlg()) {
            form.setMan320FormAdminConfBtn(GSConst.USER_ADMIN);
        } else {
            form.setMan320FormAdminConfBtn(GSConst.USER_NOT_ADMIN);
        }
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>戻るボタン処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map,
            Man320Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
    throws SQLException {

        ActionForward forward = null;

//        forward = map.findForward("ktool");
        forward = map.findForward("main");

        return forward;
    }

    /**
     * <br>[機  能] ページコンボ変更処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doChangeMode(ActionMapping map,
                                          Man320Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con)
        throws SQLException {

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 左矢印押下処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doPageMinus(ActionMapping map,
                                         Man320Form form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws SQLException {

        //ページ数取得
        int page = form.getMan320PageNum();
        page -= 1;
        if (page < 1) {
            page = 1;
        }

        //調整後ページ数セット
        form.setMan320PageNum(page);
        __doInit(map, form, req, res, con);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 右矢印押下処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doPagePlus(ActionMapping map,
                                        Man320Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws SQLException {

        //ページ数取得
        int page = form.getMan320PageNum();
        page += 1;

        //調整後ページ数セット
        form.setMan320PageNum(page);
        __doInit(map, form, req, res, con);

        return map.getInputForward();
    }

    /**
     * <br>一括削除処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doDelete(ActionMapping map, Man320Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("一括削除");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        boolean commit = false;
        try {

            //インフォメーションを一括削除
            if (form.getSelectMsg() != null) {
                CmnInfoMsgDao msgDao = new CmnInfoMsgDao(con);
                CmnInfoTagDao tagDao = new CmnInfoTagDao(con);
                CmnInfoBinDao binDao = new CmnInfoBinDao(con);
                msgDao.delete(form.getSelectMsg());
                tagDao.delete(form.getSelectMsg());
                binDao.deleteBinf(form.getSelectMsg());
                binDao.delete(form.getSelectMsg());
            }
            commit = true;

        } catch (SQLException e) {
            log__.error("オペレーションログの一括削除に失敗", e);
            throw e;

        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
        }

        //ログ出力
        RequestModel reqMdl = getRequestModel(req);
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.delete"), GSConstLog.LEVEL_INFO,
                getInterMessage(reqMdl, "main.src.man320.1"));

        //共通メッセージ画面(OK)を表示
        __setCompPageParam(map, req, form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @exception SQLException SQL実行時例外
     */
    private ActionForward __doDeleteKn(
        ActionMapping map,
        Man320Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateMan320Check(reqMdl);
        if (errors.size() > 0) {
            addErrors(req, errors);
            __doInit(map, form, req, res, con);
            log__.debug("入力エラー");
            return map.getInputForward();
        }

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("deleteOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("research").getPath());

        //hiddenパラメータ
        cmn999Form.addHiddenParam("cmd", form.getCmd());
        cmn999Form.addHiddenParam("man320SortKey", form.getMan320SortKey());
        cmn999Form.addHiddenParam("man320OrderKey", form.getMan320OrderKey());
        cmn999Form.addHiddenParam("man320PageNum", form.getMan320PageNum());
        cmn999Form.addHiddenParam("man320SelectedSid", form.getMan320SelectedSid());
        cmn999Form.addHiddenParam("selectMsg", form.getSelectMsg());

        //メッセージセット
        Man320Biz biz = new Man320Biz();
        String infoName = biz.getMsgInfoTitle(con, form.getSelectMsg());
        String msgState = "sakujo.kakunin.list";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(reqMdl, GSConstMain.TEXT_INFO), infoName));

        req.setAttribute("cmn999Form", cmn999Form);

        //トランザクショントークン設定
        saveToken(req);

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
        Man320Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("research");
        cmn999Form.setUrlOK(urlForward.getPath());

        //hiddenパラメータ
        cmn999Form.addHiddenParam("cmd", form.getCmd());
        cmn999Form.addHiddenParam("man320SortKey", form.getMan320SortKey());
        cmn999Form.addHiddenParam("man320OrderKey", form.getMan320OrderKey());
        cmn999Form.addHiddenParam("man320PageNum", form.getMan320PageNum());

        //メッセージセット
        String msgState = "sakujo.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                getInterMessage(req, GSConstMain.TEXT_INFO)));

        req.setAttribute("cmn999Form", cmn999Form);

    }


}
