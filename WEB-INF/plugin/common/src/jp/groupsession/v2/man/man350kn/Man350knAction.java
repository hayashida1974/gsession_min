package jp.groupsession.v2.man.man350kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import jp.groupsession.v2.cmn.GSException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 メイン画面レイアウト設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man350knAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man350knAction.class);

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
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Man350knForm manForm = (Man350knForm) form;

        if (cmd.equals("man350knOk")) {
            //確定ボタンリック
            forward = __doDecision(map, manForm, req, res, con);

        } else if (cmd.equals("man350knBack")) {
            //戻るボタンクリック
            forward = map.findForward("backEditLayout");

        } else {
            //初期表示
            forward = map.getInputForward();
        }

        return forward;
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
     * @throws SQLException SQL実行例外
     * @throws GSException GS汎用実行例外
     */
    private ActionForward __doDecision(ActionMapping map,
        Man350knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws SQLException, GSException {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);

        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //ログインユーザSIDを取得
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }

        boolean commit = false;

        Man350knParamModel paramMdl = new Man350knParamModel();
        paramMdl.setParam(form);
        try {

            //更新処理
            Man350knBiz biz = new Man350knBiz();
            biz.updateData(paramMdl, con, buMdl.getUsrsid());
            paramMdl.setFormData(form);

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("レイアウト変更処理エラー", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String opCode = gsMsg.getMessage("cmn.change");

        //ログ出力処理
        CommonBiz cmnBiz = new CommonBiz();
        String value = "";
        String[] layOutKbn = {
                gsMsg.getMessage("cmn.set.the.admin"),
                gsMsg.getMessage("cmn.set.eachuser")
        };
        value += "[" + gsMsg.getMessage("main.man350.2") + "] ";
        value += layOutKbn[paramMdl.getMan350kbn()];
        // 管理者が設定する場合
        if (paramMdl.getMan350kbn() == GSConstMain.MANSCREEN_LAYOUTKBN_ADMIN) {
            String[] layOutType = {
                    gsMsg.getMessage("cmn.default"),
                    gsMsg.getMessage("cmn.customize")
            };
            value += "\r\n" + "[" + gsMsg.getMessage("main.man350.3") + "] ";
            value += layOutType[paramMdl.getMan350layout()] + "\r\n";
            if (paramMdl.getMan350layout() == GSConstMain.MANSCREEN_LAYOUT_CUSTOM) {
                List<String> layOutDsp = new ArrayList<String>();
                if (NullDefault.getInt(paramMdl.getMan350area1(), 0)
                        == GSConstMain.LAYOUT_LEFT_VIEW_ON) {
                    layOutDsp.add(gsMsg.getMessage("cmn.left"));
                }
                if (NullDefault.getInt(paramMdl.getMan350area2(), 0)
                        == GSConstMain.LAYOUT_RIGHT_VIEW_ON) {
                    layOutDsp.add(gsMsg.getMessage("cmn.right"));
                }
                if (NullDefault.getInt(paramMdl.getMan350area3(), 0)
                        == GSConstMain.LAYOUT_UP_VIEW_ON) {
                    layOutDsp.add(gsMsg.getMessage("main.man350.4"));
                }
                if (NullDefault.getInt(paramMdl.getMan350area4(), 0)
                        == GSConstMain.LAYOUT_CENTER_VIEW_ON) {
                    layOutDsp.add(gsMsg.getMessage("main.man350.6"));
                }
                if (NullDefault.getInt(paramMdl.getMan350area5(), 0)
                        == GSConstMain.LAYOUT_BOTTOM_VIEW_ON) {
                    layOutDsp.add(gsMsg.getMessage("main.man350.5"));
                }
                for (int i = 0; i < layOutDsp.size(); i++) {
                    if (i > 0) {
                        value += ", ";
                    }
                    value += layOutDsp.get(i);
                }
            }
        }

        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                opCode, GSConstLog.LEVEL_INFO, value);

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
        Man350knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ktool");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "hensyu.kanryo.object";

        GsMessage gsMsg = new GsMessage();
        String textLayout = gsMsg.getMessage(req, "ptl.5");

        cmn999Form.setMessage(msgRes.getMessage(msgState, textLayout));


        req.setAttribute("cmn999Form", cmn999Form);
    }
}

