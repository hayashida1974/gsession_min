package jp.groupsession.v2.rng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.IAutoManualDeleteListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.model.RngDeleteModel;
import jp.groupsession.v2.rng.rng160.Rng160Biz;
import jp.groupsession.v2.rng.rng160.Rng160Form;
import jp.groupsession.v2.rng.rng160.Rng160ParamModel;
import jp.groupsession.v2.rng.rng160kn.Rng160knBiz;
import jp.groupsession.v2.rng.rng160kn.Rng160knForm;
import jp.groupsession.v2.rng.rng160kn.Rng160knParamModel;
import jp.groupsession.v2.rng.rng170.Rng170Biz;
import jp.groupsession.v2.rng.rng170.Rng170Form;
import jp.groupsession.v2.rng.rng170.Rng170ParamModel;
import jp.groupsession.v2.rng.rng170kn.Rng170knBiz;
import jp.groupsession.v2.rng.rng170kn.Rng170knForm;
import jp.groupsession.v2.rng.rng170kn.Rng170knParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 自動削除設定画面作成時に使用されるリスナー
 * <br>[解  説] 稟議についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngIAutoManualDeleteListenerImpl implements IAutoManualDeleteListener {

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の表示時に実行される
     * <br>[解  説] 稟議についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @throws SQLException 実行例外
     * @return 使用権限
     */
    public boolean doAutoDeleteDisp(Connection con, HttpServletRequest req, 
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        //稟議は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_RINGI, pconfig)) {
            return false;
        }
        Rng160Biz biz = new Rng160Biz();
        Rng160Form rngForm = new Rng160Form();
        Rng160ParamModel paramMdl = new Rng160ParamModel();
        paramMdl.setParam(rngForm);
        biz.setInitData(con, paramMdl, reqMdl);
        paramMdl.setFormData(rngForm);
        req.setAttribute("rng160Form", rngForm);
        return true;
    }
    
    /**
     * <p>管理者設定 自動削除設定画面の更新時前に入力チェックとして実行される
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @return errors エラー内容
     * @throws SQLException 実行例外
     */
    public ActionErrors doAutoDeleteCheck(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {
        
        Rng160knForm rngForm = new Rng160knForm();
        req.getParameterMap();
        Rng160knParamModel paramMdl = new Rng160knParamModel();
        paramMdl.setParam(rngForm);
        paramMdl.setRng160pendingKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160pendingKbn"), "0")));
        paramMdl.setRng160pendingYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160pendingYear"), "0")));
        paramMdl.setRng160pendingMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160pendingMonth"), "0")));
        paramMdl.setRng160pendingDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160pendingDay"), "0")));
        paramMdl.setRng160completeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160completeKbn"), "0")));
        paramMdl.setRng160completeYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160completeYear"), "0")));
        paramMdl.setRng160completeMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160completeMonth"), "0")));
        paramMdl.setRng160completeDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160completeDay"), "0")));
        paramMdl.setRng160draftKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160draftKbn"), "0")));
        paramMdl.setRng160draftYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160draftYear"), "0")));
        paramMdl.setRng160draftMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160draftMonth"), "0")));
        paramMdl.setRng160draftDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160draftDay"), "0")));
        paramMdl.setFormData(rngForm);
        return rngForm.validateCheck(reqMdl);
    }

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の更新時に実行される
     * <br>[解  説] 稟議についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAutoDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        Rng160knBiz biz = new Rng160knBiz();
        Rng160knForm rngForm = new Rng160knForm();
        req.getParameterMap();
        Rng160knParamModel paramMdl = new Rng160knParamModel();
        paramMdl.setParam(rngForm);
        paramMdl.setRng160pendingKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160pendingKbn"), "0")));
        paramMdl.setRng160pendingYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160pendingYear"), "0")));
        paramMdl.setRng160pendingMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160pendingMonth"), "0")));
        paramMdl.setRng160pendingDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160pendingDay"), "0")));
        paramMdl.setRng160completeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160completeKbn"), "0")));
        paramMdl.setRng160completeYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160completeYear"), "0")));
        paramMdl.setRng160completeMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160completeMonth"), "0")));
        paramMdl.setRng160completeDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160completeDay"), "0")));
        paramMdl.setRng160draftKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160draftKbn"), "0")));
        paramMdl.setRng160draftYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160draftYear"), "0")));
        paramMdl.setRng160draftMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160draftMonth"), "0")));
        paramMdl.setRng160draftDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng160draftDay"), "0")));
        biz.entryAutoDeleteConf(con, reqMdl, paramMdl);
        paramMdl.setFormData(rngForm);
        req.setAttribute("rng160knForm", rngForm);
        __setAtDelOpLog(con, req, reqMdl, rngForm);
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
    private void __setAtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Rng160knForm rngForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String year = null;
        String month = null;
        String day = null;
        String msg = "";
        msg += "[" +  gsMsg.getMessage("rng.62") + "] ";
        msg += "\r\n";
        msg += "[" +  gsMsg.getMessage("rng.48") + "] ";
        if (rngForm.getRng160pendingKbn() == RngConst.RAD_KBN_DELETE) {
            year = Integer.toString(rngForm.getRng160pendingYear());
            month = Integer.toString(rngForm.getRng160pendingMonth());
            day = Integer.toString(rngForm.getRng160pendingDay());
            msg += gsMsg.getMessage(req, "cmn.automatically.delete");
            msg += "\r\n";
            msg += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            msg += gsMsg.getMessage(req, "cmn.year", year);
            msg += gsMsg.getMessage(req, "cmn.months", month);
            msg += day + gsMsg.getMessage(req, "cmn.day");
        } else {
            msg += gsMsg.getMessage("cmn.noset");
        }

        msg += "\r\n";
        msg += "[" + gsMsg.getMessage("cmn.complete") + "] ";
        if (rngForm.getRng160completeKbn() == RngConst.RAD_KBN_DELETE) {
            year = Integer.toString(rngForm.getRng160completeYear());
            month = Integer.toString(rngForm.getRng160completeMonth());
            day = Integer.toString(rngForm.getRng160completeDay());
            msg += gsMsg.getMessage(req, "cmn.automatically.delete");
            msg += "\r\n";
            msg += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            msg += gsMsg.getMessage(req, "cmn.year", year);
            msg += gsMsg.getMessage(req, "cmn.months", month);
            msg += day + gsMsg.getMessage(req, "cmn.day");
        } else {
            msg += gsMsg.getMessage("cmn.noset");
        }

        msg += "\r\n";
        msg += "[" + gsMsg.getMessage("cmn.draft") + "] ";
        if (rngForm.getRng160draftKbn() == RngConst.RAD_KBN_DELETE) {
            year = Integer.toString(rngForm.getRng160draftYear());
            month = Integer.toString(rngForm.getRng160draftMonth());
            day = Integer.toString(rngForm.getRng160draftDay());
            msg += gsMsg.getMessage(req, "cmn.automatically.delete");
            msg += "\r\n";
            msg += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            msg += gsMsg.getMessage(req, "cmn.year", year);
            msg += gsMsg.getMessage(req, "cmn.months", month);
            msg += day + gsMsg.getMessage(req, "cmn.day");
        } else {
            msg += gsMsg.getMessage("cmn.noset");
        }
        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.autodelete.setting");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, change, GSConstLog.LEVEL_INFO, msg);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の表示時に実行される
     * <br>[解  説] 稟議についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @throws SQLException 実行例外
     * @return 使用権限
     */
    public boolean doManualDeleteDisp(Connection con, HttpServletRequest req, 
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        //稟議は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_RINGI, pconfig)) {
            return false;
        }

        Rng170Biz biz = new Rng170Biz();
        Rng170Form rngForm = new Rng170Form();
        Rng170ParamModel paramMdl = new Rng170ParamModel();
        paramMdl.setParam(rngForm);
        biz.setInitData(reqMdl, paramMdl);
        paramMdl.setFormData(rngForm);
        req.setAttribute("rng170Form", rngForm);
        return true;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時前に入力チェックとして実行される
     * <br>[解  説] 稟議についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @return errors エラー内容
     * @throws SQLException 実行例外
     */
    public ActionErrors doManualDeleteCheck(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGIN_ID_RINGI)) {
            return null;
        }

        Rng170knForm rngForm = new Rng170knForm();
        req.getParameterMap();
        Rng170knParamModel paramMdl = new Rng170knParamModel();
        paramMdl.setParam(rngForm);
        paramMdl.setRng170pendingKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170pendingKbn"), "0")));
        paramMdl.setRng170pendingYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170pendingYear"), "0")));
        paramMdl.setRng170pendingMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170pendingMonth"), "0")));
        paramMdl.setRng170pendingDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170pendingDay"), "0")));
        paramMdl.setRng170completeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170completeKbn"), "0")));
        paramMdl.setRng170completeYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170completeYear"), "0")));
        paramMdl.setRng170completeMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170completeMonth"), "0")));
        paramMdl.setRng170completeDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170completeDay"), "0")));
        paramMdl.setRng170draftKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170draftKbn"), "0")));
        paramMdl.setRng170draftYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170draftYear"), "0")));
        paramMdl.setRng170draftMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170draftMonth"), "0")));
        paramMdl.setRng170draftDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170draftDay"), "0")));
        paramMdl.setFormData(rngForm);

        return rngForm.validateCheck(reqMdl);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時に実行される
     * <br>[解  説] 稟議についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGIN_ID_RINGI)) {
            return;
        }
        Rng170knBiz biz = new Rng170knBiz();
        RngBiz rngBiz = new RngBiz(con);
        Rng170knForm rngForm = new Rng170knForm();
        req.getParameterMap();
        Rng170knParamModel paramMdl = new Rng170knParamModel();
        paramMdl.setParam(rngForm);
        paramMdl.setRng170pendingKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170pendingKbn"), "0")));
        paramMdl.setRng170pendingYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170pendingYear"), "0")));
        paramMdl.setRng170pendingMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170pendingMonth"), "0")));
        paramMdl.setRng170pendingDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170pendingDay"), "0")));
        paramMdl.setRng170completeKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170completeKbn"), "0")));
        paramMdl.setRng170completeYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170completeYear"), "0")));
        paramMdl.setRng170completeMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170completeMonth"), "0")));
        paramMdl.setRng170completeDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170completeDay"), "0")));
        paramMdl.setRng170draftKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170draftKbn"), "0")));
        paramMdl.setRng170draftYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170draftYear"), "0")));
        paramMdl.setRng170draftMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170draftMonth"), "0")));
        paramMdl.setRng170draftDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("rng170draftDay"), "0")));
        List<RngDeleteModel> delList = biz.getDelDataList(reqMdl, paramMdl);
        rngBiz.deleteRngData(con, delList, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(rngForm);
        req.setAttribute("rng160knForm", rngForm);
        __setMtDelOpLog(con, req, reqMdl, rngForm);
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
    private void __setMtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Rng170knForm rngForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String delete = gsMsg.getMessage(req, "cmn.delete");
        String year = null;
        String month = null;
        String day = null;

        String msg = "";
        msg += "[" +  gsMsg.getMessage("rng.62") + "] ";
        msg += "\r\n";
        msg += "[" +  gsMsg.getMessage("rng.48") + "] ";
        if (rngForm.getRng170pendingKbn() == RngConst.MANU_DEL_OK) {
            year = Integer.toString(rngForm.getRng170pendingYear());
            month = Integer.toString(rngForm.getRng170pendingMonth());
            day = Integer.toString(rngForm.getRng170pendingDay());
            msg += gsMsg.getMessage(req, "wml.60");
            msg += "\r\n";
            msg += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            msg += gsMsg.getMessage(req, "cmn.year", year);
            msg += gsMsg.getMessage(req, "cmn.months", month);
            msg += day + gsMsg.getMessage(req, "cmn.day");
        } else {
            msg += gsMsg.getMessage("cmn.dont.delete");
        }

        msg += "\r\n";
        msg += "[" + gsMsg.getMessage("cmn.complete") + "] ";
        if (rngForm.getRng170completeKbn() == RngConst.RAD_KBN_DELETE) {
            year = Integer.toString(rngForm.getRng170completeYear());
            month = Integer.toString(rngForm.getRng170completeMonth());
            day = Integer.toString(rngForm.getRng170completeDay());
            msg += gsMsg.getMessage(req, "wml.60");
            msg += "\r\n";
            msg += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            msg += gsMsg.getMessage(req, "cmn.year", year);
            msg += gsMsg.getMessage(req, "cmn.months", month);
            msg += day + gsMsg.getMessage(req, "cmn.day");
        } else {
            msg += gsMsg.getMessage("cmn.dont.delete");
        }

        msg += "\r\n";
        msg += "[" + gsMsg.getMessage("cmn.draft") + "] ";
        if (rngForm.getRng170draftKbn() == RngConst.RAD_KBN_DELETE) {
            year = Integer.toString(rngForm.getRng170draftYear());
            month = Integer.toString(rngForm.getRng170draftMonth());
            day = Integer.toString(rngForm.getRng170draftDay());
            msg += gsMsg.getMessage(req, "wml.60");
            msg += "\r\n";
            msg += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            msg += gsMsg.getMessage(req, "cmn.year", year);
            msg += gsMsg.getMessage(req, "cmn.months", month);
            msg += day + gsMsg.getMessage(req, "cmn.day");
        } else {
            msg += gsMsg.getMessage("cmn.dont.delete");
        }

        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.manual.delete2");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, delete, GSConstLog.LEVEL_INFO, msg);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面のアカウント変更時に実行される
     * <br>[解  説] 稟議についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteChangeAccount(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {
    }

    /**
     * <br>[機  能] セッションユーザがシステム管理者、プラグイン管理者であるか
     * <br>[解  説] 指定されたプラグインが使用可能であるか
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @return true:使用可, false:使用不可
     * @throws SQLException
     */
    public boolean doAutoAdminSettingAvailable(Connection con, HttpServletRequest req,
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {
        CommonBiz cmnBiz = new CommonBiz();
        List<String> canPlgList = cmnBiz.getCanUsePluginIdList(con, reqMdl.getSmodel().getUsrsid());
        //稟議のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_RINGI)
                && canPlgList.contains(GSConst.PLUGIN_ID_RINGI)) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] セッションユーザがシステム管理者、プラグイン管理者であるか
     * <br>[解  説] 指定されたプラグインが使用可能であるか
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param pconfig プラグインコンフィグ
     * @return true:使用可, false:使用不可
     * @throws SQLException
     */
    public boolean doManualAdminSettingAvailable(Connection con, HttpServletRequest req,
            RequestModel reqMdl, PluginConfig pconfig) throws SQLException {
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