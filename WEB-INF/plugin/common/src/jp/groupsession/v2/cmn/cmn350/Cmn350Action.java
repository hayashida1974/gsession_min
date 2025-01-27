package jp.groupsession.v2.cmn.cmn350;

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
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.base.CmnDispAconfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnDispAconfModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 表示設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn350Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn350Action.class);

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
        Cmn350Form cmnForm = (Cmn350Form) form;

        //権限チェック
        forward = checkPow(map, req, con);
        if (forward != null) {
            return forward;
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("cmn350Back")) {
            //戻る
            forward = map.findForward("cmn350Back");
        } else if (cmd.equals("update")) {
            //登録・更新
            forward = __doEdit(map, cmnForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, cmnForm, req, res, con);
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
    private ActionForward __doInit(ActionMapping map, Cmn350Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        con.setAutoCommit(true);
        Cmn350Biz biz = new Cmn350Biz(con, getRequestModel(req));
        Cmn350ParamModel paramMdl = new Cmn350ParamModel();

        paramMdl.setParam(form);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        //トランザクショントークン設定
        this.saveToken(req);

        return map.getInputForward();
    }

    /**
     * <br>更新処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     */
    private ActionForward __doEdit(ActionMapping map, Cmn350Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, ClassNotFoundException,
            IllegalAccessException, InstantiationException {

        //入力チェック
        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        Cmn350Biz biz = new Cmn350Biz(con, getRequestModel(req));
        Cmn350ParamModel paramMdl = new Cmn350ParamModel();
        paramMdl.setParam(form);
        biz.setDspPriConfig(paramMdl);
        paramMdl.setFormData(form);
        //ログ出力
        outPutLog(map, form, req, con);

        __setToastMsg(map, req);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 個人設定 認証情報登録(更新)のログ出力処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form フォーム
     * @param req HttpServletRequest
     * @param con DB Connection
     */
    private void outPutLog(
            ActionMapping map,
            Cmn350Form form,
            HttpServletRequest req,
            Connection con) {

        //ログ出力処理
        CommonBiz cmnBiz = new CommonBiz();
        StringBuilder values = new StringBuilder();
        GsMessage gsMsg = new GsMessage();
        RequestModel reqMdl = getRequestModel(req);

        //操作
        String opCode = gsMsg.getMessage(req, "cmn.edit");

        String dspMode = null;
        if (form.getCmn350RokuyoDsp() == GSConst.DSP_OK) {
            dspMode = getInterMessage(req, "cmn.display.ok");
        } else if (form.getCmn350RokuyoDsp() == GSConst.DSP_NOT) {
            dspMode = getInterMessage(req, "cmn.dont.show");
        }

        //内容
        values.append("[" + gsMsg.getMessage("cmn.cmn320.04") + "]"
                + NullDefault.getString(dspMode, ""));

        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con, opCode,
                            GSConstLog.LEVEL_INFO, values.toString());
    }

    /**
     * <br>[機  能] トースト表示のメッセージをセット
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     */
    private void __setToastMsg(
            ActionMapping map,
            HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("cmn350Ok");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "hensyu.kanryo.object";
        cmn999Form.setMessage(msgRes.getMessage(msgState,
                gsMsg.getMessage("cmn.display.settings")));

        req.setAttribute("cmn999Form", cmn999Form);
    }
    /**
     * <br>[機  能] 権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param req HttpServletRequest
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward checkPow(ActionMapping map,
                                   HttpServletRequest req,
                                   Connection con) throws Exception {

        CmnDispAconfDao dao = new CmnDispAconfDao(con);
        CmnDispAconfModel model = dao.select();

        if (model != null && model.getCdaRokuyouKbn() != GSConst.SETTING_USR) {
            return map.findForward("gf_submit");
        }

        return null;
    }
}