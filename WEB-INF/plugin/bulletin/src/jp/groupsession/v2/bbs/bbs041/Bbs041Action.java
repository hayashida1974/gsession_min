package jp.groupsession.v2.bbs.bbs041;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.AbstractBulletinAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs060.Bbs060Form;
import jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel;
import jp.groupsession.v2.bbs.bbs070.Bbs070Form;
import jp.groupsession.v2.bbs.bbs170.Bbs170Biz;
import jp.groupsession.v2.bbs.dao.BbsForAdminDao;
import jp.groupsession.v2.bbs.dao.BbsThreInfDao;
import jp.groupsession.v2.bbs.model.BbsForAdminModel;
import jp.groupsession.v2.bbs.model.BbsThreInfModel;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板検索結果一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs041Action extends AbstractBulletinAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs041Action.class);

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
        Bbs041Form bbsForm = (Bbs041Form) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("prevPage")) {
            //前ページクリック
            bbsForm.setBbs041page1(bbsForm.getBbs041page1() - 1);
            forward = __doInit(map, bbsForm, req, res, con);
        } else if (cmd.equals("nextPage")) {
            //次ページクリック
            bbsForm.setBbs041page1(bbsForm.getBbs041page1() + 1);
            forward = __doInit(map, bbsForm, req, res, con);
        } else if (cmd.equals("moveResult")) {
            //検索結果リンククリック
            forward = __moveResult(map, bbsForm, req, con);
        } else if (cmd.equals("backPage")) {
            //戻るボタンクリック
            forward = __doBack(map, bbsForm, req, con);
        } else {
            //初期表示
            forward = __doInit(map, bbsForm, req, res, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 検索結果リンククリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param bbs041Form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __moveResult(
            ActionMapping map, Bbs041Form bbs041Form,
            HttpServletRequest req, Connection con)
                    throws SQLException {
        BbsThreInfDao dao = new BbsThreInfDao(con);
        BbsThreInfModel btiMdl = dao.select(bbs041Form.getThreadSid());
        BbsBiz bbsBiz = new BbsBiz();

        //掲示期間が未来の投稿の場合、投稿編集画面に遷移
        //削除されていないか確認
        if (btiMdl == null) {
            return __setAlreadyDelPost(map, req);
        }
        if (btiMdl.getBtiLimit() == GSConstBulletin.THREAD_LIMIT_YES) {
            if (bbsBiz.checkReserveDate(btiMdl.getBtiLimitFrDate(), new UDate())) {
                //未来投稿チェック
                BbsForAdminDao bfaDao = new BbsForAdminDao(con);
                RequestModel reqMdl = getRequestModel(req);
                BbsForAdminModel bfaMdl = bfaDao.select(bbs041Form.getBbs010forumSid(),
                        reqMdl.getSmodel().getUsrsid());


                if (bfaMdl == null && btiMdl.getBtiAuid() != reqMdl.getSmodel().getUsrsid()) {
                    return __setAlreadyDelPost(map, req);
                }
                __setBbs070Form(req, con, bbs041Form, GSConstBulletin.BBSCMDMODE_EDIT);
                return map.findForward("moveThreadDtl");
            } else if (bbsBiz.checkToDate(btiMdl.getBtiLimitFrDate(), new UDate())) {
                //過去投稿チェック
                return __setAlreadyDelPost(map, req);
            }
        }

        Bbs060ParamModel paramMdl060 = new Bbs060ParamModel();
        paramMdl060.setParam(bbs041Form);
        Bbs060Form form060 = new Bbs060Form();
        paramMdl060.setFormData(form060);
        req.setAttribute("bbs060Form", form060);
        return map.findForward("moveThreadList");
    }

    /**
     * <br>[機  能] スレッド作成画面へのフォームパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @param form アクションフォーム
     * @param cmdMode 処理モード
     * @throws SQLException SQL実行時例外
     */
    private void __setBbs070Form(
            HttpServletRequest req, Connection con, Bbs041Form form, int cmdMode)
                    throws SQLException {

        Bbs070Form form070 = new Bbs070Form();
        form070.setS_key(form.getS_key());
        form070.setBbs010page1(form.getBbs010page1());
        form070.setBbs010forumSid(form.getBbs010forumSid());
        form070.setBbs060page1(form.getBbs060page1());
        form070.setBbs070cmdMode(cmdMode);

        //フォーラムSID,スレッドSIDより最初の投稿SIDを取得する
        Bbs170Biz biz = new Bbs170Biz();
        int bwiSid = biz.getFirstBwiSid(con, form.getBbs010forumSid(), form.getThreadSid());

        form070.setThreadSid(form.getThreadSid());
        form070.setBbs060postSid(bwiSid);
        form070.setBbs070BackID(GSConstBulletin.DSP_ID_BBS041);

        req.setAttribute("bbs070Form", form070);
    }

    /**
     * <br>[機  能] 戻る処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param bbsForm フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doBack(
            ActionMapping map,
            Bbs041Form bbsForm,
            HttpServletRequest req,
            Connection con) {
        ActionForward forward = null;
        String searchDsp = NullDefault.getString(bbsForm.getSearchDspID(), "");

        if (searchDsp.equals(GSConstBulletin.SERCHDSPID_FORUMLIST)) {
            //戻り先：掲示板一覧/検索
            forward = map.findForward("backBBSList");

        } else if (searchDsp.equals(GSConstBulletin.SERCHDSPID_THREADLIST)) {
            //戻り先：掲示板一覧/検索
            forward = map.findForward("moveThreadList");

        } else {
            //戻り先：詳細検索
            forward = map.findForward("moveSearchDtl");
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
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
        Bbs041Form form,
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

        con.setAutoCommit(true);
        Bbs041ParamModel paramMdl = new Bbs041ParamModel();
        paramMdl.setParam(form);
        Bbs041Biz biz = new Bbs041Biz();
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, buMdl, GSConstBulletin.PLUGIN_ID_BULLETIN);

        RequestModel reqMdl = getRequestModel(req);
        biz.setInitData(paramMdl, con, userSid, adminUser, reqMdl);
        paramMdl.setFormData(form);

        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
        form.setBbs041searchUse(CommonBiz.getWebSearchUse(pconfig));
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] スレッド削除/変更時権限エラーメッセージ画面遷移時の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @return ActionForward フォワード
     * @throws SQLException SQL例外発生
     */
    private ActionForward __setAlreadyDelPost(
            ActionMapping map,
            HttpServletRequest req)
                    throws SQLException {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        urlForward = map.findForward("backBBSList");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage(getRequestModel(req));

        String  textDelWrite = gsMsg.getMessage("bbs.2");
        String textDel = gsMsg.getMessage("cmn.operations");

        //メッセージセット
        String msgState = "error.none.edit.data";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                textDelWrite,
                textDel));

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}

