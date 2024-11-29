package jp.groupsession.v2.cmn.cmn290;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.ISmlSendSettingListener;
import jp.groupsession.v2.cmn.SmlSendSettingListenerUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.GSConfigConst;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] スケジュール ショートメール通知設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn290Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn290Action.class);
    /** 回覧板遷移先URL*/
    private static final String CIR_URL = "/WEB-INF/plugin/circular/jsp/cir250.jsp";
    /** 日報遷移先URL*/
    private static final String NTP_URL = "/WEB-INF/plugin/nippou/jsp/ntp095.jsp";

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
        Cmn290Form cmnForm = (Cmn290Form) form;
        if (form == null) {
            log__.debug(" form is null ");
        }

        //ショートメール使用可能判定
        if (!__canUseSmlConf(cmnForm, req, con)) {
            return getSubmitErrorPage(map, req);
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("cmn290back")) {
            //戻る
            forward = map.findForward("cmn290back");
        } else if (cmd.equals("add") || cmd.equals("delete") || cmd.equals("group")) {
            //ユーザ追加・削除・グループ変更
            forward = __doUsrGrpComponent(map, cmnForm, req, res, con);
        } else if (cmd.equals("update")) {
            //更新
            forward = __doEdit(map, cmnForm, req, res, con);
        } else if (cmd.equals("accountChange")) {
            //アカウント変更
            forward = __doAccoutChange(map, cmnForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, cmnForm, req, res, con);
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
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     */
    private ActionForward __doInit(ActionMapping map, Cmn290Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, ClassNotFoundException, 
            IllegalAccessException, InstantiationException {


        ActionForward forward = null;
        con.setAutoCommit(true);
        Cmn290ParamModel paramMdl = new Cmn290ParamModel();
        paramMdl.setFormData(form);
        
        //プラグイン設定を取得する
        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconfig
        = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req), reqMdl);
        HashMap<String, String> lisMap = pconfig.getPluginIdForListeners(
                getPluginConfig(req).getPluginIdList(), GSConfigConst.NAME_SMAIL_SEND_SETTING);
        for (String key : lisMap.keySet()) {
            String className = lisMap.get(key);
            ISmlSendSettingListener lis = 
                    (ISmlSendSettingListener) Class.forName(className).newInstance();
            boolean disp = lis.doPconfSettingAvailable(con, req, reqMdl, pconfig);
            if (disp) {
                lis.doPconfSettingDisp(con, req, reqMdl);
                switch (key) {
                case GSConst.PLUGIN_ID_BULLETIN:
                    form.setCmn290bbsDsp(1);
                    break;
                case GSConst.PLUGIN_ID_FILE:
                    form.setCmn290filDsp(1);
                    break;
                case GSConst.PLUGIN_ID_NIPPOU:
                    form.setCmn290ntpDsp(1);
                    break;
                case GSConst.PLUGIN_ID_RINGI:
                    form.setCmn290rngDsp(1);
                    break;
                case GSConst.PLUGINID_CIR:
                    form.setCmn290cirDsp(1);
                    break;
                case GSConst.PLUGINID_SCH:
                    form.setCmn290schDsp(1);
                    break;
                case GSConst.PLUGIN_ID_RESERVE:
                    form.setCmn290rsvDsp(1);
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
     * <br>[機  能] 更新処理
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
    private ActionForward __doEdit(ActionMapping map, Cmn290Form form,
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
        con.setAutoCommit(false);
        ActionErrors errors = new ActionErrors();
        Cmn290ParamModel paramMdl = new Cmn290ParamModel();
        paramMdl.setFormData(form);
        ISmlSendSettingListener[] lis = SmlSendSettingListenerUtil
                .getSmlSendSettingListeners(getPluginConfig(req));
        for (int i = 0; i < lis.length; i++) {
            errors.add(lis[i].doPconfValidate(req, getRequestModel(req)));
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
            for (ISmlSendSettingListener listner : lis) {
                listner.doPconfSettingEdit(con, req, getRequestModel(req));
            }
            con.commit();
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
     * <br>[機  能] 通知対象のユーザ・グループの選択
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
    private ActionForward __doUsrGrpComponent(ActionMapping map, Cmn290Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, ClassNotFoundException, 
            IllegalAccessException, InstantiationException {

        Cmn290ParamModel paramMdl = new Cmn290ParamModel();
        paramMdl.setFormData(form);
        ISmlSendSettingListener[] lis = SmlSendSettingListenerUtil
                .getSmlSendSettingListeners(getPluginConfig(req));

        for (int i = 0; i < lis.length; i++) {
            lis[i].doPconfSettingUsrGrp(
                    con, req, getRequestModel(req));
        }
        //実装時は日報でしかユーザ選択コンボは存在しない
        return new ActionForward(NTP_URL);
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
    private ActionForward __doAccoutChange(ActionMapping map, Cmn290Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException, ClassNotFoundException, 
            IllegalAccessException, InstantiationException {
        
        con.setAutoCommit(true);
        Cmn290ParamModel paramMdl = new Cmn290ParamModel();
        paramMdl.setFormData(form);
        ISmlSendSettingListener[] lis = SmlSendSettingListenerUtil
                .getSmlSendSettingListeners(getPluginConfig(req));
        for (int i = 0; i < lis.length; i++) {
            lis[i].doPconfSettingChangeAccount(con, req, getRequestModel(req));
        }
        con.setAutoCommit(false);

        //実装時は回覧板でしかアカウント変更は存在しない
        return new ActionForward(CIR_URL);
    }

    /**
     * <br>[機  能] ショートメール設定が利用可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return true ショートメール使用可能
     *
     */
    private boolean __canUseSmlConf(Cmn290Form form, HttpServletRequest req, Connection con)
    throws SQLException {

        //プラグイン設定を取得する
        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        //ショートメールは利用可能か判定
        return cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig);
    }
}
