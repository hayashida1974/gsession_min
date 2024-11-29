package jp.groupsession.v2.bbs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.bbs120.Bbs120Biz;
import jp.groupsession.v2.bbs.bbs120.Bbs120Form;
import jp.groupsession.v2.bbs.bbs120.Bbs120ParamModel;
import jp.groupsession.v2.bbs.bbs150.Bbs150Biz;
import jp.groupsession.v2.bbs.bbs150.Bbs150Form;
import jp.groupsession.v2.bbs.bbs150kn.Bbs150knForm;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.IAutoManualDeleteListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 自動削除設定画面作成時に使用されるリスナー
 * <br>[解  説] 掲示板についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsIAutoManualDeleteListenerImpl implements IAutoManualDeleteListener {

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の表示時に実行される
     * <br>[解  説] 掲示板についての処理を行う
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
        //掲示板は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_BULLETIN, pconfig)) {
            return false;
        }

        Bbs120Biz biz = new Bbs120Biz();
        Bbs120Form bbsForm = new Bbs120Form();
        Bbs120ParamModel paramMdl = new Bbs120ParamModel();
        paramMdl.setParam(bbsForm);
        biz.setInitData(paramMdl, reqMdl, reqMdl.getSmodel(), con);
        paramMdl.setFormData(bbsForm);
        req.setAttribute("bbs120Form", bbsForm);
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
        
        Bbs120Form bbsForm = new Bbs120Form();
        req.getParameterMap();
        Bbs120ParamModel paramMdl = new Bbs120ParamModel();
        paramMdl.setParam(bbsForm);
        paramMdl.setBbs120AtdelFlg(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs120AtdelFlg"), "0")));
        paramMdl.setBbs120AtdelYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs120AtdelYear"), "0")));
        paramMdl.setBbs120AtdelMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs120AtdelMonth"), "0")));
        paramMdl.setFormData(bbsForm);

        //入力チェック
        return bbsForm.validateCheck(req);
    }


    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の更新時に実行される
     * <br>[解  説] 掲示板についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAutoDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        Bbs120Biz biz = new Bbs120Biz();
        Bbs120Form bbsForm = new Bbs120Form();
        req.getParameterMap();
        Bbs120ParamModel paramMdl = new Bbs120ParamModel();
        paramMdl.setParam(bbsForm);
        paramMdl.setBbs120AtdelFlg(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs120AtdelFlg"), "0")));
        paramMdl.setBbs120AtdelYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs120AtdelYear"), "0")));
        paramMdl.setBbs120AtdelMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs120AtdelMonth"), "0")));
        paramMdl.setFormData(bbsForm);

        biz.setAutoDeleteSetting(paramMdl, reqMdl, reqMdl.getSmodel(), con);
        req.setAttribute("bbs120Form", bbsForm);
        __setAtDelOpLog(con, req, reqMdl, bbsForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param bbsForm アクションフォーム
     */
    private void __setAtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Bbs120Form bbsForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String auto = null;
        String year = null;
        String month = null;
        String value = "";
        value += "[" +  gsMsg.getMessage("cmn.bulletin") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.autodelete") + "] ";
        if (bbsForm.getBbs120AtdelFlg() == GSConstBulletin.AUTO_DELETE_ON) {
            auto = gsMsg.getMessage(req, "cmn.automatically.delete");
            year = Integer.toString(bbsForm.getBbs120AtdelYear());
            month = Integer.toString(bbsForm.getBbs120AtdelMonth());
            value += auto;
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else if (bbsForm.getBbs120AtdelFlg() == GSConstBulletin.AUTO_DELETE_OFF) {
            auto = gsMsg.getMessage(req, "cmn.noset");
            value += auto;
        }
        

        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.autodelete.setting");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, change, GSConstLog.LEVEL_INFO, value);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の表示時に実行される
     * <br>[解  説] 掲示板についての処理を行う
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
        //掲示板は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_BULLETIN, pconfig)) {
            return false;
        }

        Bbs150Biz biz = new Bbs150Biz(reqMdl);
        Bbs150Form bbsForm = new Bbs150Form();
        biz.setInitData(bbsForm);
        biz.setDspData(bbsForm);
        req.setAttribute("bbs150Form", bbsForm);
        return true;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時前に入力チェックとして実行される
     * <br>[解  説] 掲示板についての処理を行う
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
        if (!pluginId.equals(GSConst.PLUGIN_ID_BULLETIN)) {
            return null;
        }

        Bbs150knForm bbsForm = new Bbs150knForm();
        req.getParameterMap();
        bbsForm.setBbs150Year(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs150Year"), "0")));
        bbsForm.setBbs150Month(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs150Month"), "0")));

        return bbsForm.validateCheck(req);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時に実行される
     * <br>[解  説] 掲示板についての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGIN_ID_BULLETIN)) {
            return;
        }
        BbsBiz biz = new BbsBiz();
        Bbs150knForm bbsForm = new Bbs150knForm();
        req.getParameterMap();

        //削除する境界の日付を設定する
        bbsForm.setBbs150Year(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs150Year"), "0")));
        bbsForm.setBbs150Month(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs150Month"), "0")));
        UDate delUdate = new UDate();

        delUdate.addYear((bbsForm.getBbs150Year() * -1));
        delUdate.addMonth((bbsForm.getBbs150Month() * -1));
        delUdate.setHour(GSConstBulletin.DAY_END_HOUR);
        delUdate.setMinute(GSConstBulletin.DAY_END_MINUTES);
        delUdate.setSecond(GSConstBulletin.DAY_END_SECOND);
        delUdate.setMilliSecond(GSConstBulletin.DAY_END_MILLISECOND);
        //削除実行
        biz.deleteOldBulletin(con, delUdate);
        //掲示期限を過ぎたスレッドを削除する
        biz.deleteOverLimitBulletin(con);
        req.setAttribute("bbs150knForm", bbsForm);
        __setMtDelOpLog(con, req, reqMdl, bbsForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param bbsForm アクションフォーム
     */
    private void __setMtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Bbs150knForm bbsForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String delete = gsMsg.getMessage(req, "cmn.delete");
        String year = Integer.toString(bbsForm.getBbs150Year());
        String month = Integer.toString(bbsForm.getBbs150Month());

        String value = "";
        value += "[" +  gsMsg.getMessage("cmn.bulletin") + "] ";
        value += "\r\n";
        value += "[" + gsMsg.getMessage("cmn.manual.delete2") + "] ";
        value += gsMsg.getMessage(req, "cmn.year", year);
        value += gsMsg.getMessage(req, "cmn.months", month);
        value += gsMsg.getMessage("cmn.after.data");

        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.manual.delete2");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, delete, GSConstLog.LEVEL_INFO, value);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面のアカウント変更時に実行される
     * <br>[解  説] 掲示板についての処理を行う
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
        //掲示板のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_BULLETIN)
                && canPlgList.contains(GSConst.PLUGIN_ID_BULLETIN)) {
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
        //掲示板のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_BULLETIN)
                && canPlgList.contains(GSConst.PLUGIN_ID_BULLETIN)) {
            return true;
        }
        return false;
    }
}