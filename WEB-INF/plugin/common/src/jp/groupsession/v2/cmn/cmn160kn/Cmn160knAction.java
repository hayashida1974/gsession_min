package jp.groupsession.v2.cmn.cmn160kn;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn160.Cmn160Action;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 企業情報確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn160knAction extends Cmn160Action {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn160knAction.class);

    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "cmn160";


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

        log__.debug("START");

        ActionForward forward = null;
        Cmn160knForm thisForm = (Cmn160knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        if (cmd.equals("cmn160knBack")) {
            log__.debug("*** 戻るボタンクリック");
            forward = map.findForward("cmn160knBack");

        } else if (cmd.equals("cmn160knDecision")) {
            log__.debug("確定ボタンクリック");
            forward = __doEntry(map, thisForm, req, res, con);

        } else {
            log__.debug("*** 初期表示を行います。");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END");
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
        Cmn160knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        Cmn160knBiz biz = new Cmn160knBiz(new GsMessage(req));

        //初期表示情報を設定する。
        Cmn160knParamModel paramModel = new Cmn160knParamModel();
        paramModel.setParam(form);
        biz.getInitData(con, paramModel);
        paramModel.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 企業情報登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    private ActionForward __doEntry(
                            ActionMapping map,
                            Cmn160knForm form,
                            HttpServletRequest req,
                            HttpServletResponse res,
                            Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        //テンポラリディレクトリパス
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConst.PLUGINID_COMMON, TEMP_DIRECTORY_ID);
        String tempPath = tempDir + TEMP_LOGO_CMN160;
        String tempMenuPath = tempDir + TEMP_MENU_LOGO_CMN160;

        //入力チェックを行う
        ActionErrors errors = null;
        errors = form.validateCheck(con, req, tempPath, tempMenuPath);
        if (errors != null && !errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        ActionForward forward = null;
        boolean commit = false;

        RequestModel reqMdl = getRequestModel(req);

        //アプリケーションのルートパス
        String appRoot = getAppRootPath();

        MlCountMtController cntCon = getCountMtController(req);

        try {
            GsMessage gsMsg = new GsMessage(reqMdl);
            Cmn160knBiz biz = new Cmn160knBiz(gsMsg);
            Cmn160knParamModel paramModel = new Cmn160knParamModel();
            paramModel.setParam(form);
            biz.entryEnterPriseData(con, paramModel,
                                    getSessionUserModel(req).getUsrsid(),
                                    appRoot, tempPath, tempMenuPath, cntCon);
            paramModel.setFormData(form);

            forward = __setCompPageParam(map, req, form);
            con.commit();
            commit = true;

            String textEdit = gsMsg.getMessage("cmn.change");

            //ログ出力
            CommonBiz cmnBiz = new CommonBiz();
            cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                    textEdit, GSConstLog.LEVEL_INFO,
                    "[name]" + NullDefault.getString(form.getCmn160ComName(), ""));
        } catch (Exception e) {
            log__.error("企業情報の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
        //テンポラリディレクトリ内のファイルを削除
        temp.deleteTempPath(getRequestModel(req), GSConst.PLUGINID_COMMON, TEMP_DIRECTORY_ID);
        return forward;
    }


    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Cmn160knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("mainAdmSetting");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "hensyu.kanryo.object";
        GsMessage gsMsg = new GsMessage();

        cmn999Form.setMessage(msgRes.getMessage(msgState, gsMsg.getMessage(req, "main.man002.21")));

        //パラメータ設定
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

}
