package jp.groupsession.v2.bbs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.bbs.bbs052.Bbs052Biz;
import jp.groupsession.v2.bbs.bbs052.Bbs052Form;
import jp.groupsession.v2.bbs.bbs052.Bbs052ParamModel;
import jp.groupsession.v2.bbs.bbs160.Bbs160Biz;
import jp.groupsession.v2.bbs.bbs160.Bbs160Form;
import jp.groupsession.v2.bbs.bbs160.Bbs160ParamModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.ISmlSendSettingListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール通知設定画面作成時に使用されるリスナー
 * <br>[解  説] 掲示板についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsISmlSendSettingListenerImpl implements ISmlSendSettingListener {

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
        //掲示板は利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_BULLETIN, pconfig)) {
            return false;
        }
        
        Bbs160Biz biz = new Bbs160Biz();
        Bbs160Form bbsForm = new Bbs160Form();
        Bbs160ParamModel paramMdl = new Bbs160ParamModel();
        paramMdl.setParam(bbsForm);
        biz.setInitData(paramMdl, con, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(bbsForm);
        req.setAttribute("bbs160Form", bbsForm);
        
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
        
        Bbs160Form bbsForm = new Bbs160Form();
        Bbs160ParamModel paramMdl = new Bbs160ParamModel();
        paramMdl.setBbs160smlNtf(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs160smlNtf"), "0")));
        paramMdl.setBbs160smlNtfKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs160smlNtfKbn"), "0")));
        paramMdl.setFormData(bbsForm);
        
        //入力値チェック
        ActionErrors errors = bbsForm.validateCheck(reqMdl);
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

        Bbs160Biz biz = new Bbs160Biz();
        Bbs160Form bbsForm = new Bbs160Form();
        Bbs160ParamModel paramMdl = new Bbs160ParamModel();
        paramMdl.setParam(bbsForm);
        paramMdl.setBbs160smlNtf(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs160smlNtf"), "0")));
        paramMdl.setBbs160smlNtfKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs160smlNtfKbn"), "0")));
        biz.updateBbsSmailSetting(paramMdl, reqMdl.getSmodel(),  con);
        paramMdl.setFormData(bbsForm);
        req.setAttribute("bbs160Form", bbsForm);
        __setAdmOpLog(con, req, reqMdl, bbsForm);
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
    private void __setAdmOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Bbs160Form bbsForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String textEdit = gsMsg.getMessage("cmn.change");

        String value = "";
        value += "[" +  gsMsg.getMessage("cmn.bulletin") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("shortmail.notification") + "] ";
        String[] setting = {
                gsMsg.getMessage("cmn.set.eachuser"),
                gsMsg.getMessage("cmn.set.the.admin")
        };
        value += setting[bbsForm.getBbs160smlNtf()];
        if (bbsForm.getBbs160smlNtf() == GSConstBulletin.BAC_SML_NTF_ADMIN) {
            String[] notify = {
                    gsMsg.getMessage("cmn.notify"),
                    gsMsg.getMessage("cmn.dont.notify")
            };
            value += "\r\n";
            value += notify[bbsForm.getBbs160smlNtfKbn()];
        }

        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.sml.notification.setting");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, textEdit, GSConstLog.LEVEL_INFO, value);
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
        
        BbsBiz biz = new BbsBiz();
        if (biz.canSetSmailConf(con, reqMdl.getSmodel().getUsrsid())) {
            CommonBiz cmnBiz = new CommonBiz();
            //ショートメールは利用可能か判定
            return cmnBiz.isCanUsePlugin(GSConst.PLUGIN_ID_BULLETIN, pconfig);
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

        Bbs052Biz biz = new Bbs052Biz();
        Bbs052Form bbsForm = new Bbs052Form();
        Bbs052ParamModel paramMdl = new Bbs052ParamModel();
        paramMdl.setParam(bbsForm);
        biz.setInitData(paramMdl, con, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(bbsForm);
        req.setAttribute("bbs052Form", bbsForm);
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
        
        Bbs052Form bbsForm = new Bbs052Form();
        Bbs052ParamModel paramMdl = new Bbs052ParamModel();
        paramMdl.setBbs052smlNtf(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs052smlNtf"), "0")));
        paramMdl.setFormData(bbsForm);
        
        //入力値チェック
        ActionErrors errors = bbsForm.validateCheck(reqMdl);
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

        Bbs052Biz biz = new Bbs052Biz();
        Bbs052Form bbsForm = new Bbs052Form();
        Bbs052ParamModel paramMdl = new Bbs052ParamModel();
        paramMdl.setParam(bbsForm);
        paramMdl.setBbs052smlNtf(Integer.parseInt(
                NullDefault.getString(req.getParameter("bbs052smlNtf"), "0")));
        biz.updateBbsUserConf(paramMdl, con, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(bbsForm);
        req.setAttribute("bbs052Form", bbsForm);
        __setPriOpLog(con, req, reqMdl, bbsForm);
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
    private void __setPriOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Bbs052Form bbsForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String textEdit = gsMsg.getMessage("cmn.change");

        String msg = "";
        msg += "[" +  gsMsg.getMessage("cmn.bulletin") + "] ";
        msg += "\r\n";
        msg += "[" + gsMsg.getMessage("shortmail.notification") + "]";
        if (bbsForm.getBbs052smlNtf() == GSConstBulletin.BAC_SML_NTF_KBN_YES) {
            msg += gsMsg.getMessage("cmn.notify");
        } else if (bbsForm.getBbs052smlNtf() == GSConstBulletin.BAC_SML_NTF_KBN_NO) {
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
        //掲示板のプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGIN_ID_BULLETIN)
                && canPlgList.contains(GSConst.PLUGIN_ID_BULLETIN)) {
            return true;
        }
        return false;
    }
}