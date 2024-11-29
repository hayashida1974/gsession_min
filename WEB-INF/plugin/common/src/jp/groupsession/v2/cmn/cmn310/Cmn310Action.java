package jp.groupsession.v2.cmn.cmn310;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.AutoManualDeleteListenerUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.IAutoManualDeleteListener;
import jp.groupsession.v2.cmn.config.GSConfigConst;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AdminAction;

/**
 * <br>[機  能] 手動削除画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn310Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn310Action.class);
    /** 回覧板遷移先URL*/
    private static final String CIR_URL = "/WEB-INF/plugin/circular/jsp/cir120.jsp";
    /** ショートメール遷移先URL*/
    private static final String SML_URL = "/WEB-INF/plugin/smail/jsp/sml160.jsp";

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }

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

        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
        HashMap<String, String> lisMap = pconfig.getPluginIdForListeners(
                getPluginConfig(req).getPluginIdList(), GSConfigConst.NAME_AUTO_MANUAL_DELETE);
        boolean disp = false;
        for (String key : lisMap.keySet()) {
            String className = lisMap.get(key);
            IAutoManualDeleteListener lis = 
                    (IAutoManualDeleteListener) Class.forName(className).newInstance();
            disp = lis.doManualAdminSettingAvailable(con, req, reqMdl, pconfig);
            if (disp) {
                break;
            }
        }
        if (!disp) {
            forward = getNotAdminSeniPage(map, req);
            return forward;
        }

        Cmn310Form cmnForm = (Cmn310Form) form;
        if (form == null) {
            log__.debug(" form is null ");
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("cmn310back")) {
            //戻る
            forward = map.findForward("cmn310back");
        } else if (cmd.equals("validateCheck")) {
            //入力チェック
            forward = __doValidate(map, cmnForm, req, res, con);
        } else if (cmd.equals("update")) {
            //登録・更新
            forward = __doEdit(map, cmnForm, req, res, con);
        } else if (cmd.equals("accountChange")) {
            //アカウント変更
            forward = __doAccoutChange(map, cmnForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, cmnForm, req, res, con, pconfig, lisMap);
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
     * @param pconfig プラグインコンフィグ
     * @param lisMap ハッシュマップ
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     */
    private ActionForward __doInit(ActionMapping map, Cmn310Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con,
            PluginConfig pconfig, HashMap<String, String> lisMap)
            throws SQLException, ClassNotFoundException, 
            IllegalAccessException, InstantiationException {

        ActionForward forward = null;
        con.setAutoCommit(true);

        //プラグイン設定を取得する
        for (String key : lisMap.keySet()) {
            String className = lisMap.get(key);
            IAutoManualDeleteListener lis = 
                    (IAutoManualDeleteListener) Class.forName(className).newInstance();
            boolean disp = lis.doManualAdminSettingAvailable(
                    con, req, getRequestModel(req), pconfig);
            if (disp) {
                lis.doManualDeleteDisp(con, req, getRequestModel(req), pconfig);
                switch (key) {
                case GSConst.PLUGIN_ID_BULLETIN:
                    form.setCmn310bbsDsp(1);
                    break;
                case GSConst.PLUGIN_ID_CHAT:
                    form.setCmn310chtDsp(1);
                    break;
                case GSConst.PLUGINID_CIR:
                    form.setCmn310cirDsp(1);
                    break;
                case GSConst.PLUGIN_ID_ENQUETE:
                    form.setCmn310enqDsp(1);
                    break;
                case GSConst.PLUGIN_ID_NIPPOU:
                    form.setCmn310ntpDsp(1);
                    break;
                case GSConst.PLUGIN_ID_RESERVE:
                    form.setCmn310rsvDsp(1);
                    break;
                case GSConst.PLUGIN_ID_RINGI:
                    form.setCmn310rngDsp(1);
                    break;
                case GSConst.PLUGINID_SCH:
                    form.setCmn310schDsp(1);
                    break;
                case GSConst.PLUGINID_SML:
                    form.setCmn310smlDsp(1);
                    break;
                case GSConst.PLUGINID_WML:
                    form.setCmn310wmlDsp(1);
                    break;
                default:
                    break;
                }
            }
        }
        //トランザクショントークン設定
        this.saveToken(req);
        
        forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
    }

    /**
     * <br>入力チェック
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
    private ActionForward __doValidate(ActionMapping map, Cmn310Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, ClassNotFoundException, 
            IllegalAccessException, InstantiationException {

        ActionForward forward = null;
        JSONObject jsonData = new JSONObject();

        con.setAutoCommit(true);
        ActionErrors errors = new ActionErrors();
        Cmn310ParamModel paramMdl = new Cmn310ParamModel();
        paramMdl.setFormData(form);
        IAutoManualDeleteListener[] lis = AutoManualDeleteListenerUtil
                .getAutoManualDeleteListeners(getPluginConfig(req));
        for (int i = 0; i < lis.length; i++) {
            errors = lis[i].doManualDeleteCheck(con, req, getRequestModel(req));
            if (errors != null && errors.size() > 0) {
                break;
            }
        }
        //入力エラー時
        if (errors != null && errors.size() > 0) {
            List<String> errMsgList = new ArrayList<String>();
            MessageResources mres = getResources(req);
            Iterator<ActionMessage> iterator = errors.get();

            while (iterator.hasNext()) {
                ActionMessage errorMsg = (ActionMessage) iterator.next();
                errMsgList.add(mres.getMessage(
                        errorMsg.getKey(), errorMsg.getValues()));
            }
            jsonData.element("success", false);
            jsonData.element("errors", true);
            jsonData.element("size", errors.size());
            for (int i = 0; i < errMsgList.size(); i++) {
                jsonData.element("errorMsg_" + i, errMsgList.get(i));
            }

        } else {
            jsonData.element("success", true);
        }
        forward = map.getInputForward();
        //レスポンスの出力
        __writeResp(res, jsonData);

        return forward;
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
    private ActionForward __doEdit(ActionMapping map, Cmn310Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, ClassNotFoundException, 
            IllegalAccessException, InstantiationException {

        ActionForward forward = null;
        JSONObject jsonData = new JSONObject();
        //2重投稿チェック
        if (!isTokenValid(req, false)) {
            log__.info("２重投稿");
            jsonData.element("success", false);
            //レスポンスの出力
            __writeResp(res, jsonData);

            return forward;
        }
        con.setAutoCommit(true);
        ActionErrors errors = new ActionErrors();
        Cmn310ParamModel paramMdl = new Cmn310ParamModel();
        paramMdl.setFormData(form);
        IAutoManualDeleteListener[] lis = AutoManualDeleteListenerUtil
                .getAutoManualDeleteListeners(getPluginConfig(req));
        for (int i = 0; i < lis.length; i++) {
            errors = lis[i].doManualDeleteCheck(con, req, getRequestModel(req));
            if (errors != null && errors.size() > 0) {
                break;
            }
        }
        //入力エラー時
        if (errors != null && errors.size() > 0) {
            List<String> errMsgList = new ArrayList<String>();
            MessageResources mres = getResources(req);
            Iterator<ActionMessage> iterator = errors.get();

            while (iterator.hasNext()) {
                ActionMessage errorMsg = (ActionMessage) iterator.next();
                errMsgList.add(mres.getMessage(
                        errorMsg.getKey(), errorMsg.getValues()));
            }
            jsonData.element("success", false);
            jsonData.element("errors", true);
            jsonData.element("size", errors.size());
            for (int i = 0; i < errMsgList.size(); i++) {
                jsonData.element("errorMsg_" + i, errMsgList.get(i));
            }

        } else {
            for (int i = 0; i < lis.length; i++) {
                lis[i].doManualDeleteConf(con, req, getRequestModel(req));
            }
            con.setAutoCommit(false);
            jsonData.element("success", true);
        }
        forward = map.getInputForward();
        //レスポンスの出力
        __writeResp(res, jsonData);

        return forward;
    }

    /**
     * <br>[機  能] jsonレスポンスの書き込み処理
     * <br>[解  説]
     * <br>[備  考]
     * @param res レスポンス
     * @param json jsonオブジェクト
     */
    private void __writeResp(HttpServletResponse res, JSONObject json) {
        PrintWriter out = null;
        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(json);
            out.flush();
        } catch (IOException e) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * <br>[機  能] アカウント変更
     * <br>[解  説]
     * <br>[備  考]
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
    private ActionForward __doAccoutChange(ActionMapping map, Cmn310Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, ClassNotFoundException, 
            IllegalAccessException, InstantiationException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGINID_CIR)
                && !pluginId.equals(GSConst.PLUGINID_SML)) {
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(true);
        Cmn310ParamModel paramMdl = new Cmn310ParamModel();
        paramMdl.setFormData(form);
        IAutoManualDeleteListener[] lis = AutoManualDeleteListenerUtil
                .getAutoManualDeleteListeners(getPluginConfig(req));
        for (int i = 0; i < lis.length; i++) {
            lis[i].doManualDeleteChangeAccount(con, req, getRequestModel(req));
        }
        con.setAutoCommit(false);

        if (pluginId.equals(GSConst.PLUGINID_CIR)) {
            return new ActionForward(CIR_URL);
        }
        return new ActionForward(SML_URL);
    }
}