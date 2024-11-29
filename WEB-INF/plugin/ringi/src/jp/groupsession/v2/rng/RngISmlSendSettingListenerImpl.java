package jp.groupsession.v2.rng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.ISmlSendSettingListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.rng190.Rng190Biz;
import jp.groupsession.v2.rng.rng190.Rng190Form;
import jp.groupsession.v2.rng.rng190.Rng190ParamModel;
import jp.groupsession.v2.rng.rng250.Rng250Biz;
import jp.groupsession.v2.rng.rng250.Rng250Form;
import jp.groupsession.v2.rng.rng250.Rng250ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール通知設定画面作成時に使用されるリスナー
 * <br>[解  説] スケジュールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngISmlSendSettingListenerImpl implements ISmlSendSettingListener {

    /**
     * <p>管理者設定ショートメール通知設定画面表示時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @throws SQLException 実行例外
     * @return 使用権限
     */
    public boolean doAdminSettingDisp(Connection con,  HttpServletRequest req, 
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_RINGI, pconfig)) {
            return false;
        }

        Rng190Biz biz = new Rng190Biz(con, reqMdl);
        Rng190Form rngForm = new Rng190Form();
        Rng190ParamModel paramMdl = new Rng190ParamModel();
        paramMdl.setParam(rngForm);
        biz.setInitData(paramMdl);
        paramMdl.setFormData(rngForm);
        req.setAttribute("rng190Form", rngForm);
        return true;
    }
    
    /**
     * <p>管理者設定ショートメール通知設定画面更新時に実行される
     * <p>入力値チェックを行う
     * @param req
     * @param reqMdl リクエストモデル
     * @return errors エラー内容
     * @throws SQLException 実行例外
     */
    public ActionErrors doAconfValidate(HttpServletRequest req, RequestModel reqMdl) {
        
        Rng190Form rngForm = new Rng190Form();
        Rng190ParamModel paramMdl = new Rng190ParamModel();

        paramMdl.setRng190SmlNtf(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng190SmlNtf"), "0")));
        paramMdl.setRng190SmlJusin(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng190SmlJusin"), "0")));
        paramMdl.setRng190SmlNtfKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng190SmlNtfKbn"), "0")));
        paramMdl.setRng190SmlDairi(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng190SmlDairi"), "0")));
        paramMdl.setFormData(rngForm);
        
        //入力チェック
        ActionErrors errors = rngForm.validateCheck(reqMdl);
        return errors;
    }

    /**
     * <p>管理者設定ショートメール通知設定画面更新時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAdminSettingEdit(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        Rng190Biz biz = new Rng190Biz(con, reqMdl);
        Rng190Form rngForm = new Rng190Form();
        Rng190ParamModel paramMdl = new Rng190ParamModel();

        paramMdl.setParam(rngForm);
        paramMdl.setRng190SmlNtf(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng190SmlNtf"), "0")));
        paramMdl.setRng190SmlJusin(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng190SmlJusin"), "0")));
        paramMdl.setRng190SmlNtfKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng190SmlNtfKbn"), "0")));
        paramMdl.setRng190SmlDairi(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng190SmlDairi"), "0")));
        biz.updateSmailSetting(paramMdl, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(rngForm);
        req.setAttribute("rng190Form", rngForm);
        __setAdmOpLog(con, req, reqMdl, rngForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param rngForm アクションフォーム
     */
    private void __setAdmOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Rng190Form rngForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String msg = "";
        msg += "[" +  gsMsg.getMessage("rng.62") + "] ";
        msg += "\r\n";
        msg += "[" + gsMsg.getMessage("shortmail.notification") + "] ";
        if (rngForm.getRng190SmlNtf() == RngConst.RAR_SML_NTF_ADMIN) { // 管理者が設定
            msg += gsMsg.getMessage("cmn.set.the.admin") + "\r\n";

            msg += "[" + gsMsg.getMessage("rng.rng190.01") + "] ";
            if (rngForm.getRng190SmlNtfKbn() == RngConst.RAR_SML_NTF_KBN_YES) {
                msg += gsMsg.getMessage("cmn.notify");
            } else {
                msg += gsMsg.getMessage("cmn.dont.notify");
            }
            msg += "\r\n";

            msg += "[" + gsMsg.getMessage("rng.rng190.03") + "] ";
            if (rngForm.getRng190SmlJusin() == RngConst.RAR_SML_NTF_KBN_YES) {
                msg += gsMsg.getMessage("cmn.notify");
            } else {
                msg += gsMsg.getMessage("cmn.dont.notify");
            }
            msg += "\r\n";

            msg += "[" + gsMsg.getMessage("rng.rng190.05") + "] ";
            if (rngForm.getRng190SmlDairi() == RngConst.RAR_SML_NTF_KBN_YES) {
                msg += gsMsg.getMessage("cmn.notify");
            } else {
                msg += gsMsg.getMessage("cmn.dont.notify");
            }
        } else if (rngForm.getRng190SmlNtf() == RngConst.RAR_SML_NTF_KBN_YES) { // 各ユーザが設定
            msg += gsMsg.getMessage("cmn.set.eachuser");
        }

        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.sml.notification.setting");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, change, GSConstLog.LEVEL_INFO, msg);
    }

    /**
     * <p>個人設定ショートメール通知設定使用判定時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @throws SQLException 実行例外
     * @return 表示・非表示判定
     */
    public boolean doPconfSettingAvailable(Connection con, HttpServletRequest req,
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {
        
        RngBiz rngBiz = new RngBiz(con);
        RngAconfModel admMdl = rngBiz.getRngAconf(con);
        if (admMdl != null && admMdl.getRarSmlNtf() == 0) {
            CommonBiz cmnBiz = new CommonBiz();
            //ショートメールは利用可能か判定
            return cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_RINGI, pconfig);
        } 
        return false;
    }

    /**
     * <p>個人設定ショートメール通知設定画面表示時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doPconfSettingDisp(Connection con, HttpServletRequest req,
            RequestModel reqMdl) throws SQLException {

        Rng250Biz biz = new Rng250Biz();
        Rng250Form rngForm = new Rng250Form();
        Rng250ParamModel paramMdl = new Rng250ParamModel();
        paramMdl.setParam(rngForm);
        biz.setInitData(paramMdl, con, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(rngForm);
        req.setAttribute("rng250Form", rngForm);
    }
    
    /**
     * <p>個人設定ショートメール通知設定画面更新時に実行される
     * <p>入力値チェックを行う
     * @param req
     * @param reqMdl リクエストモデル
     * @return errors エラー内容
     * @throws SQLException 実行例外
     */
    public ActionErrors doPconfValidate(HttpServletRequest req, RequestModel reqMdl) {
        
        Rng250Form rngForm = new Rng250Form();
        Rng250ParamModel paramMdl = new Rng250ParamModel();

        paramMdl.setRng250smlNtf(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng250smlNtf"), "0")));
        paramMdl.setRng250smlNtfJyusin(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng250smlNtfJyusin"), "0")));
        paramMdl.setRng250smlNtfDairi(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng250smlNtfDairi"), "0")));
        paramMdl.setFormData(rngForm);

        //入力チェック
        ActionErrors errors = rngForm.validateCheck(reqMdl);
        return errors;
    }

    /**
     * <p>個人設定ショートメール通知設定画面更新時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doPconfSettingEdit(Connection con, HttpServletRequest req,
            RequestModel reqMdl) throws SQLException {

        Rng250Biz biz = new Rng250Biz();
        Rng250Form rngForm = new Rng250Form();
        Rng250ParamModel paramMdl = new Rng250ParamModel();

        paramMdl.setParam(rngForm);
        paramMdl.setRng250smlNtf(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng250smlNtf"), "0")));
        paramMdl.setRng250smlNtfJyusin(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng250smlNtfJyusin"), "0")));
        paramMdl.setRng250smlNtfDairi(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng250smlNtfDairi"), "0")));
        biz.updateUserConf(paramMdl, con, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(rngForm);
        req.setAttribute("rng250Form", rngForm);
        __setPriOpLog(con, req, reqMdl, rngForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param rngForm アクションフォーム
     */
    private void __setPriOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Rng250Form rngForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String textEdit = gsMsg.getMessage("cmn.change");
        String msg = "";

        msg += "";
        msg += "[" +  gsMsg.getMessage("rng.62") + "] ";
        msg += "\r\n";
        msg += "[" + gsMsg.getMessage("rng.rng190.01") + "] ";
        if (rngForm.getRng250smlNtf() == RngConst.RNG_SMAIL_TSUUCHI) {
            msg += gsMsg.getMessage("cmn.notify");
        } else {
            msg += gsMsg.getMessage("cmn.dont.notify");
        }
        msg += "\r\n";

        msg += "[" + gsMsg.getMessage("rng.rng190.03") + "] ";
        if (rngForm.getRng250smlNtfJyusin() == RngConst.RNG_SMAIL_TSUUCHI) {
            msg += gsMsg.getMessage("cmn.notify");
        } else {
            msg += gsMsg.getMessage("cmn.dont.notify");
        }
        msg += "\r\n";

        msg += "[" + gsMsg.getMessage("rng.rng190.05") + "] ";
        if (rngForm.getRng250smlNtfDairi() == RngConst.RNG_SMAIL_TSUUCHI) {
            msg += gsMsg.getMessage("cmn.notify");
        } else {
            msg += gsMsg.getMessage("cmn.dont.notify");
        }

        String dspName = gsMsg.getMessage("cmn.preferences2") + " "
                + gsMsg.getMessage("cmn.sml.notification.setting");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, textEdit, GSConstLog.LEVEL_INFO, msg);
    }

    /**
     * <p>個人設定ショートメール通知設定画面アカウント変更時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doPconfSettingChangeAccount(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {
        
    }

    /**
     * <p>個人設定ショートメール通知設定画面ユーザ・グループ追加時に実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @return errors エラー内容
     * @throws SQLException 実行例外
     */
    public ActionErrors doPconfSettingUsrGrp(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {
        return null;
    }

    /**
     * <br>[機  能] セッションユーザがシステム管理者又はプラグイン管理者であるか
     * <br>[解  説] 指定されたプラグインが使用可能であるか
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @return true:使用可, false:使用不可
     * @throws SQLException 
     */
    public boolean doAdminSettingAvailable(Connection con,
            HttpServletRequest req, RequestModel reqMdl, PluginConfig pconfig) throws SQLException {
        CommonBiz cmnBiz = new CommonBiz();
        List<String> canPlgList = cmnBiz.getCanUsePluginIdList(con, reqMdl.getSmodel().getUsrsid());
        //稟議のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_RINGI)
                && canPlgList.contains(GSConst.PLUGIN_ID_RINGI)) {
            return true;
        }
        return false;
    }
}