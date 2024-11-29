package jp.groupsession.v2.bbs.bbs030kn;

import java.sql.Connection;

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
import jp.groupsession.v2.bbs.AbstractBulletinAdminAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs020.Bbs020Biz;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 フォーラム登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs030knAction extends AbstractBulletinAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs030knAction.class);

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
        Bbs030knForm bbsForm = (Bbs030knForm) form;

        //コマンド
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD= " + cmd);

        if (cmd.equals("decision")) {
            //追加ボタンクリック
            forward = __doDecision(map, bbsForm, req, res, con);
        } else if (cmd.equals("backToInput")) {
            //戻るボタンクリック
            forward = map.findForward("backToInput");
        } else {
            //初期表示
            forward = __doInit(map, bbsForm, req, res, con);
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
        Bbs030knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        con.setAutoCommit(true);
        Bbs030knParamModel paramMdl = new Bbs030knParamModel();
        paramMdl.setParam(form);
        Bbs030knBiz biz = new Bbs030knBiz();


        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時の処理
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
        Bbs030knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //テンポラリディレクトリパス
        String tempPath = _getBulletinTempDir(req, form);

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(req, con, tempPath);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        Bbs020Biz bbs020Biz = new Bbs020Biz();
        Bbs030knBiz bbs030knBiz = new Bbs030knBiz();
        boolean commit = false;

        //アプリケーションのルートパス
        String appRoot = getAppRootPath();

        //テンポラリディレクトリパス
        String tempDir = _getBulletinTempDir(req, form);
        BbsForInfDao dao = new BbsForInfDao(con);
        String befName = "";
        try {
            MlCountMtController cntCon = getCountMtController(req);

            Bbs030knParamModel paramMdl = new Bbs030knParamModel();
            paramMdl.setParam(form);
            if (form.getBbs030cmdMode() == GSConstBulletin.BBSCMDMODE_ADD) {
                //登録処理
                bbs030knBiz.insertForumData(
                        paramMdl, con, cntCon, userSid, appRoot, tempDir);

            } else if (form.getBbs030cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
                BbsForInfModel mdl = new BbsForInfModel();
                mdl.setBfiSid(form.getBbs020forumSid());
                BbsForInfModel gMdl = dao.select(mdl);
                befName = gMdl.getBfiName();
                if (gMdl != null && gMdl.getBfiName() != null && !gMdl.getBfiName().equals("")) {
                    befName = gMdl.getBfiName();
                }
                //更新処理
                bbs030knBiz.updateForumData(
                        paramMdl, con, cntCon, userSid, appRoot, tempDir);
            }

            //移動先の表示順の連番を修正する
            bbs020Biz.fixForumSort(con, paramMdl.getBbs030ParentForumSid());

            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("フォーラム登録処理エラー", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        RequestModel reqMdl = getRequestModel(req);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //ログ出力処理
        BbsBiz bbsBiz = new BbsBiz(con);
        String opCode = "";
        BbsForInfModel sMdl = new BbsForInfModel();
        sMdl.setBfiSid(form.getBbs030ParentForumSid());
        BbsForInfModel getMdl = dao.select(sMdl);
        String parentForumName = "";
        if (getMdl != null && getMdl.getBfiName() != null && !getMdl.getBfiName().equals("")) {
            parentForumName = getMdl.getBfiName();
        } else {
            parentForumName = "なし";
        }

        if (form.getBbs030cmdMode() == GSConstBulletin.BBSCMDMODE_ADD) {
            String textEntry = gsMsg.getMessage("cmn.entry");
            opCode = textEntry;
        } else if (form.getBbs030cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            String textEdit = gsMsg.getMessage("cmn.change");
            opCode = textEdit;
        }
        StringBuilder sb = new StringBuilder();
        if (!form.getBbs030forumName().equals(befName)
                && form.getBbs030cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            sb.append("[");
            sb.append(gsMsg.getMessage("bbs.41"));
            sb.append("]");
            sb.append(befName);
            sb.append("\n");
        }
        sb.append("[");
        sb.append(gsMsg.getMessage("bbs.4"));
        sb.append("]");
        sb.append(form.getBbs030forumName());
        sb.append("\n");
        sb.append("[");
        sb.append(gsMsg.getMessage("bbs.40"));
        sb.append("]");
        sb.append(parentForumName);
        bbsBiz.outPutLog(
                map, reqMdl, opCode, GSConstLog.LEVEL_INFO, sb.toString());

        //テンポラリディレクトリ内のファイルを削除
        _deleteBulletinTempDir(req, form);

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
        Bbs030knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("backForumList");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = null;
        if (form.getBbs030cmdMode() == GSConstBulletin.BBSCMDMODE_ADD) {
            msgState = "touroku.kanryo.object";
        } else if (form.getBbs030cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            msgState = "hensyu.kanryo.object";
        }

        GsMessage gsMsg = new GsMessage();
        String textForum = gsMsg.getMessage(req, "bbs.3");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textForum));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("s_key", form.getS_key());
        cmn999Form.addHiddenParam("bbs010page1", form.getBbs010page1());
        cmn999Form.addHiddenParam("bbs020page1", form.getBbs020page1());
        cmn999Form.addHiddenParam("bbs020indexRadio", form.getBbs020indexRadio());
        req.setAttribute("cmn999Form", cmn999Form);

    }

}
