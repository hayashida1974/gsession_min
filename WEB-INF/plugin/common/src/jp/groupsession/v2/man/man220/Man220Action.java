package jp.groupsession.v2.man.man220;

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
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 グループ・ユーザ並び順設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man220Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man220Action.class);

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
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;
        Man220Form schForm = (Man220Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("confirm")) {
            //OKボタンクリック
            forward = __doOK(map, schForm, req, res, con);

        } else if (cmd.equals("backKtool")) {
            //戻るボタンクリック
            forward = map.findForward("ktool");

        } else {
            //デフォルト
            forward = __doInit(map, schForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Man220Form form,
                                    HttpServletRequest req, HttpServletResponse res, Connection con)
    throws SQLException {

        log__.debug("初期表示");
        con.setAutoCommit(true);
        Man220ParamModel paramMdl = new Man220ParamModel();
        paramMdl.setParam(form);
        Man220Biz biz = new Man220Biz();
        biz.setInitData(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doOK(
        ActionMapping map,
        Man220Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("OKボタンクリック");

        RequestModel reqMdl = getRequestModel(req);

        ActionErrors errors = form.validateCheck(reqMdl);

        Man220ParamModel paramMdl = new Man220ParamModel();
        paramMdl.setParam(form);
        Man220Biz biz = new Man220Biz();
        if (!errors.isEmpty()) {
            addErrors(req, errors);

            biz.setInitData(paramMdl, reqMdl, con);
            paramMdl.setFormData(form);
            return map.getInputForward();
        }

        //登録処理
        boolean commit = false;
        try {
            biz.entryCmbSortConfig(paramMdl, con, getSessionUserSid(req));
            paramMdl.setFormData(form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("コンボボックスソート設定の登録に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }
        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String value = "";
        // ユーザコンボボックス表示順
        value += "[" + gsMsg.getMessage("main.man220.2") + "] ";
        value += gsMsg.getMessage("cmn.first.key") + ":"
                + __getSortNameUsr(gsMsg, paramMdl.getMan220UserSortKey1())
                + " " + __getOrderKeyName(
                        gsMsg, paramMdl.getMan220UserSortOrder1(), paramMdl.getMan220UserSortKey1())
                + "\r\n";
        value += gsMsg.getMessage("cmn.second.key") + ":"
                + __getSortNameUsr(gsMsg, paramMdl.getMan220UserSortKey2())
                + " " + __getOrderKeyName(
                        gsMsg, paramMdl.getMan220UserSortOrder2(), paramMdl.getMan220UserSortKey2())
                + "\r\n";
        // グループコンボボックス表示順
        value += "[" + gsMsg.getMessage("main.man220.5") + "] ";
        value += gsMsg.getMessage("cmn.first.key") + ":"
                + __getSortNameGrp(gsMsg, paramMdl.getMan220GroupSortKey1())
                + " " + __getOrderKeyName(gsMsg,
                        paramMdl.getMan220GroupSortOrder1(), paramMdl.getMan220GroupSortKey1())
                + "\r\n";
        value += gsMsg.getMessage("cmn.second.key") + ":"
                + __getSortNameGrp(gsMsg, paramMdl.getMan220GroupSortKey2())
                + " " + __getOrderKeyName(gsMsg,
                        paramMdl.getMan220GroupSortOrder2(), paramMdl.getMan220GroupSortKey2());
        if (paramMdl.getMan220GroupSortKbn() == GSConst.GROUPCMB_SKBN_SET) {
            value += "\r\n" + gsMsg.getMessage("man.reorder.hierarchy.group");
        }
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                getInterMessage(reqMdl, "cmn.change"), GSConstLog.LEVEL_INFO, value);

        //共通メッセージ画面を表示
        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <p> ユーザコンボボックス表示順のソート対象名を取得
     * @param gsMsg GSメッセージ取得クラス
     * @param sortKey ソート対象内部値
     * @return ソート対象名
     * */
    private String __getSortNameUsr(GsMessage gsMsg, int sortKey) {
        if (sortKey == GSConst.USERCMB_SKEY_NAME) {
            return gsMsg.getMessage("cmn.name4");
        } else if (sortKey == GSConst.USERCMB_SKEY_SNO) {
            return gsMsg.getMessage("cmn.employee.staff.number");
        } else if (sortKey == GSConst.USERCMB_SKEY_POSITION) {
            return gsMsg.getMessage("cmn.post");
        } else if (sortKey == GSConst.USERCMB_SKEY_BDATE) {
            return gsMsg.getMessage("cmn.birthday");
        } else if (sortKey == GSConst.USERCMB_SKEY_SORTKEY1) {
            return gsMsg.getMessage("cmn.sortkey") + "1";
        } else if (sortKey == GSConst.USERCMB_SKEY_SORTKEY2) {
            return gsMsg.getMessage("cmn.sortkey") + "2";
        } else {
            return gsMsg.getMessage("cmn.specified.no");
        }
    }

    /**
     * <p> グループコンボボックス表示順のソート対象名を取得
     * @param gsMsg GSメッセージ取得クラス
     * @param sortKey ソート対象内部値
     * @return ソート対象名
     * */
    private String __getSortNameGrp(GsMessage gsMsg, int sortKey) {
        if (sortKey == GSConst.GROUPCMB_SKEY_GRPID) {
            return gsMsg.getMessage("main.src.man220.6");
        } else if (sortKey == GSConst.GROUPCMB_SKEY_NAME) {
            return gsMsg.getMessage("cmn.name4");
        }
        return gsMsg.getMessage("cmn.specified.no");
    }

    /**
     * <p> 表示順名を取得します
     * @param  gsMsg GSメッセージ取得クラス
     * @param orderKey 表示順内部値
     * @param sortKey ソート対象内部値
     * @return 表示順名
     * */
    private String __getOrderKeyName(GsMessage gsMsg, int orderKey, int sortKey) {
        if (sortKey == GSConst.USERCMB_SKEY_NOSET || sortKey == GSConst.GROUPCMB_SKBN_NOSET) {
            return "";
        }
        if (orderKey == GSConst.ORDER_KEY_ASC) {
            return gsMsg.getMessage("cmn.order.asc");
        } else {
            return gsMsg.getMessage("cmn.order.desc");
        }
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
        Man220Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ktool");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage();
        String msgState = "touroku.kanryo.object";
        String key1 = gsMsg.getMessage(req, "main.src.man220.1");
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        req.setAttribute("cmn999Form", cmn999Form);

    }
}
