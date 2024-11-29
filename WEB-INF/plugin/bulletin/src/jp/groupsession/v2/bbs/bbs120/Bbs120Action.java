package jp.groupsession.v2.bbs.bbs120;

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
import jp.groupsession.v2.bbs.AbstractBulletinAdminAction;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 自動データ削除設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs120Action extends AbstractBulletinAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs120Action.class);

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

        Bbs120Form bbsForm = (Bbs120Form) form;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("bbs120kakunin")) {
            //確認
            forward = __doKakunin(map, bbsForm, req, res, con);
        } else if (cmd.equals("bbs120commit")) {
            //登録
            forward = __doCommit(map, bbsForm, req, res, con);
        } else if (cmd.equals("confMenu")) {
            //戻るボタンクリック
            forward = map.findForward("confMenu");
        } else if (cmd.equals("changeCancel")) {
            forward = __doSetShowData(map, bbsForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, bbsForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Bbs120Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("登録処理");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        RequestModel reqMdl = getRequestModel(req);
        Bbs120ParamModel paramMdl = new Bbs120ParamModel();
        paramMdl.setParam(form);
        BaseUserModel umodel = getSessionUserModel(req);
        Bbs120Biz biz = new Bbs120Biz();
        biz.setAutoDeleteSetting(paramMdl, reqMdl, umodel, con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textEdit = gsMsg.getMessage("cmn.change");

        //ログ出力処理
        BbsBiz bbsBiz = new BbsBiz(con);
        String auto = null;
        String year = null;
        String month = null;
        String value = null;

        if (form.getBbs120AtdelFlg() == GSConstBulletin.AUTO_DELETE_ON) {
            auto = "自動で削除する";
            year = Integer.toString(form.getBbs120AtdelYear());
            month = Integer.toString(form.getBbs120AtdelMonth());
            value = "自動削除：" + auto + "\n" + year + "年" + month + "ヶ月経過したデータ";
        } else if (form.getBbs120AtdelFlg() == GSConstBulletin.AUTO_DELETE_OFF) {
            auto = "設定しない";
            value = auto;
        }

        bbsBiz.outPutLog(map, reqMdl,
                textEdit, GSConstLog.LEVEL_INFO, value);

        //共通メッセージ画面(OK)を表示
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
        Bbs120Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("confMenu");
        cmn999Form.setUrlOK(urlForward.getPath());

        GsMessage gsMsg = new GsMessage();

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        String textAutoDelSetting = gsMsg.getMessage(req, "cmn.automatic.delete.setting");
        cmn999Form.setMessage(msgRes.getMessage(msgState, textAutoDelSetting));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("s_key", form.getS_key());
        cmn999Form.addHiddenParam("bbs010page1", form.getBbs010page1());

        req.setAttribute("cmn999Form", cmn999Form);

    }

    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Bbs120Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("確認処理");

        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doSetShowData(map, form, req, res, con);
        }

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面(OK キャンセル)を表示
        __setKakuninPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Bbs120Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
//        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        GsMessage gsMsg = new GsMessage();
        String textAutoDelData = gsMsg.getMessage(req, "cmn.automatic.delete");
        String textBbs = gsMsg.getMessage(req, "cmn.bulletin");
        String textNoSet = gsMsg.getMessage(req, "cmn.noset");
        String textAutoDelOn = gsMsg.getMessage(req, "cmn.automatically.delete");
        String textYear = gsMsg.getMessage(
                req, "cmn.year", String.valueOf(form.getBbs120AtdelYear()));
        String textMonths = gsMsg.getMessage(
                req, "cmn.months", String.valueOf(form.getBbs120AtdelMonth()));


        //メッセージセット
        String msgState = "setting.kakunin.data";
        String mkey1 = textBbs + " " + textAutoDelData;
        String mkey2 = null;
        if (form.getBbs120AtdelFlg() ==  GSConstBulletin.AUTO_DELETE_OFF) {
            mkey2 = textNoSet;
        } else {
            mkey2 = textAutoDelOn + "("
                    + textYear + textMonths + ")";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1, mkey2));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("s_key", form.getS_key());
        cmn999Form.addHiddenParam("bbs010page1", form.getBbs010page1());
        cmn999Form.addHiddenParam("cmd", "ok");
        cmn999Form.addHiddenParam("bbs120AtdelFlg", form.getBbs120AtdelFlg());
        cmn999Form.addHiddenParam("bbs120AtdelYear", form.getBbs120AtdelYear());
        cmn999Form.addHiddenParam("bbs120AtdelMonth", form.getBbs120AtdelMonth());

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Bbs120Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");

        //初期値をセット
        Bbs120ParamModel paramMdl = new Bbs120ParamModel();
        paramMdl.setParam(form);
        BaseUserModel umodel = getSessionUserModel(req);
        Bbs120Biz biz = new Bbs120Biz();
        con.setAutoCommit(true);
        biz.setInitData(paramMdl,  getRequestModel(req), umodel, con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return __doSetShowData(map, form, req, res, con);
    }

    /**
     * <br>表示用データを取得、表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doSetShowData(ActionMapping map, Bbs120Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        //表示用データを取得する
        Bbs120ParamModel paramMdl = new Bbs120ParamModel();
        paramMdl.setParam(form);
        Bbs120Biz biz = new Bbs120Biz();
        con.setAutoCommit(true);
        biz.setShowData(paramMdl, con,  getRequestModel(req));
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

}
