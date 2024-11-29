package jp.groupsession.v2.wml.listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.IAutoManualDeleteListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.wml050.Wml050Biz;
import jp.groupsession.v2.wml.wml050.Wml050Form;
import jp.groupsession.v2.wml.wml050.Wml050ParamModel;
import jp.groupsession.v2.wml.wml050kn.Wml050knBiz;
import jp.groupsession.v2.wml.wml050kn.Wml050knForm;
import jp.groupsession.v2.wml.wml050kn.Wml050knParamModel;
import jp.groupsession.v2.wml.wml060.Wml060Biz;
import jp.groupsession.v2.wml.wml060.Wml060Form;
import jp.groupsession.v2.wml.wml060.Wml060ParamModel;
import jp.groupsession.v2.wml.wml060kn.Wml060knBiz;
import jp.groupsession.v2.wml.wml060kn.Wml060knForm;
import jp.groupsession.v2.wml.wml060kn.Wml060knParamModel;
import jp.groupsession.v2.wml.wml170.Wml170Biz;
import jp.groupsession.v2.wml.wml170.Wml170Form;
import jp.groupsession.v2.wml.wml170.Wml170ParamModel;
import jp.groupsession.v2.wml.wml170kn.Wml170knBiz;
import jp.groupsession.v2.wml.wml170kn.Wml170knForm;
import jp.groupsession.v2.wml.wml170kn.Wml170knParamModel;
import jp.groupsession.v2.wml.wml180.Wml180Biz;
import jp.groupsession.v2.wml.wml180.Wml180Form;
import jp.groupsession.v2.wml.wml180.Wml180ParamModel;
import jp.groupsession.v2.wml.wml180kn.Wml180knBiz;
import jp.groupsession.v2.wml.wml180kn.Wml180knForm;
import jp.groupsession.v2.wml.wml180kn.Wml180knParamModel;

/**
 * <br>[機  能] 自動削除設定画面作成時に使用されるリスナー
 * <br>[解  説] WEBメールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlIAutoManualDeleteListenerImpl implements IAutoManualDeleteListener {

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の表示時に実行される
     * <br>[解  説] WEBメールについての処理を行う
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
        //Webメールは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_WML, pconfig)) {
            return false;
        }

        Wml050Biz biz = new Wml050Biz();
        Wml050Form wmlForm = new Wml050Form();
        Wml050ParamModel paramMdl = new Wml050ParamModel();
        paramMdl.setParam(wmlForm);
        biz.setInitData(paramMdl, reqMdl);
        biz.getAutoDelete(paramMdl, con);
        paramMdl.setFormData(wmlForm);
        req.setAttribute("wml050Form", wmlForm);
        
        Wml170Biz wml170biz = new Wml170Biz();
        Wml170Form wml170Form = new Wml170Form();
        Wml170ParamModel param170Mdl = new Wml170ParamModel();
        param170Mdl.setParam(wml170Form);
        wml170biz.setInitData(param170Mdl, con, reqMdl);
        wml170biz.getAutoDelete(param170Mdl, con);
        param170Mdl.setFormData(wml170Form);
        req.setAttribute("wml170Form", wml170Form);

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
        
        Wml050knForm wmlForm050 = new Wml050knForm();
        req.getParameterMap();
        Wml050knParamModel paramMdl050 = new Wml050knParamModel();
        paramMdl050.setParam(wmlForm050);
        paramMdl050.setWml050dustKbn(
                NullDefault.getString(req.getParameter("wml050dustKbn"), "0"));
        paramMdl050.setWml050dustYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050dustYear"), "0")));
        paramMdl050.setWml050dustMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050dustMonth"), "0")));
        paramMdl050.setWml050dustDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050dustDay"), "0")));
        paramMdl050.setWml050sendKbn(
                NullDefault.getString(req.getParameter("wml050sendKbn"), "0"));
        paramMdl050.setWml050sendYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050sendYear"), "0")));
        paramMdl050.setWml050sendMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050sendMonth"), "0")));
        paramMdl050.setWml050sendDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050sendDay"), "0")));
        paramMdl050.setWml050draftKbn(
                NullDefault.getString(req.getParameter("wml050draftKbn"), "0"));
        paramMdl050.setWml050draftYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050draftYear"), "0")));
        paramMdl050.setWml050draftMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050draftMonth"), "0")));
        paramMdl050.setWml050draftDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050draftDay"), "0")));
        paramMdl050.setWml050resvKbn(
                NullDefault.getString(req.getParameter("wml050resvKbn"), "0"));
        paramMdl050.setWml050resvYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050resvYear"), "0")));
        paramMdl050.setWml050resvMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050resvMonth"), "0")));
        paramMdl050.setWml050resvDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050resvDay"), "0")));
        paramMdl050.setWml050keepKbn(
                NullDefault.getString(req.getParameter("wml050keepKbn"), "0"));
        paramMdl050.setWml050keepYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050keepYear"), "0")));
        paramMdl050.setWml050keepMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050keepMonth"), "0")));
        paramMdl050.setWml050keepDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050keepDay"), "0")));
        paramMdl050.setFormData(wmlForm050);
        ActionErrors errors = wmlForm050.validateCheck(reqMdl);
        
        Wml170Form wmlForm = new Wml170Form();
        Wml170ParamModel paramMdl = new Wml170ParamModel();
        paramMdl.setWml170delKbn(NullDefault.getString(req.getParameter("wml170delKbn"), "0"));
        paramMdl.setWml170delYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml170delYear"), "0")));
        paramMdl.setWml170delMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml170delMonth"), "0")));
        paramMdl.setWml170delDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml170delDay"), "0")));
        paramMdl.setFormData(wmlForm);
        errors = wmlForm.validateCheck(reqMdl, errors);
        
        return errors;
    }

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の更新時に実行される
     * <br>[解  説] WEBメールについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAutoDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {
        
        Wml050knBiz biz050 = new Wml050knBiz(con);
        Wml050knForm wmlForm050 = new Wml050knForm();
        req.getParameterMap();
        Wml050knParamModel paramMdl050 = new Wml050knParamModel();
        paramMdl050.setParam(wmlForm050);
        paramMdl050.setWml050dustKbn(
                NullDefault.getString(req.getParameter("wml050dustKbn"), "0"));
        paramMdl050.setWml050dustYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050dustYear"), "0")));
        paramMdl050.setWml050dustMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050dustMonth"), "0")));
        paramMdl050.setWml050dustDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050dustDay"), "0")));
        paramMdl050.setWml050sendKbn(
                NullDefault.getString(req.getParameter("wml050sendKbn"), "0"));
        paramMdl050.setWml050sendYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050sendYear"), "0")));
        paramMdl050.setWml050sendMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050sendMonth"), "0")));
        paramMdl050.setWml050sendDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050sendDay"), "0")));
        paramMdl050.setWml050draftKbn(
                NullDefault.getString(req.getParameter("wml050draftKbn"), "0"));
        paramMdl050.setWml050draftYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050draftYear"), "0")));
        paramMdl050.setWml050draftMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050draftMonth"), "0")));
        paramMdl050.setWml050draftDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050draftDay"), "0")));
        paramMdl050.setWml050resvKbn(
                NullDefault.getString(req.getParameter("wml050resvKbn"), "0"));
        paramMdl050.setWml050resvYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050resvYear"), "0")));
        paramMdl050.setWml050resvMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050resvMonth"), "0")));
        paramMdl050.setWml050resvDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050resvDay"), "0")));
        paramMdl050.setWml050keepKbn(
                NullDefault.getString(req.getParameter("wml050keepKbn"), "0"));
        paramMdl050.setWml050keepYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050keepYear"), "0")));
        paramMdl050.setWml050keepMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050keepMonth"), "0")));
        paramMdl050.setWml050keepDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml050keepDay"), "0")));
        biz050.setData(paramMdl050, reqMdl.getSmodel().getUsrsid());
        paramMdl050.setFormData(wmlForm050);
        req.setAttribute("wml050knForm", wmlForm050);
        __setWml050AtDelOpLog(con, req, reqMdl, wmlForm050);
        Wml170knBiz biz = new Wml170knBiz(con);
        Wml170knForm wmlForm = new Wml170knForm();
        Wml170knParamModel paramMdl = new Wml170knParamModel();
        paramMdl.setWml170delKbn(NullDefault.getString(req.getParameter("wml170delKbn"), "0"));
        paramMdl.setWml170delYear(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml170delYear"), "0")));
        paramMdl.setWml170delMonth(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml170delMonth"), "0")));
        paramMdl.setWml170delDay(Integer.parseInt(
                NullDefault.getString(req.getParameter("wml170delDay"), "0")));
        biz.setData(paramMdl, reqMdl.getSmodel().getUsrsid());
        paramMdl.setFormData(wmlForm);
        req.setAttribute("wml170knForm", wmlForm);
        __setWml170AtDelOpLog(con, req, reqMdl, wmlForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param wmlForm アクションフォーム
     */
    private void __setWml050AtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Wml050knForm wmlForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String msg = "";
        msg += "[" +  gsMsg.getMessage("wml.wml010.25") + "] ";
        msg += "\r\n";
        msg += "[" +  gsMsg.getMessage("cmn.autodelete") + "] ";
        msg += "\r\n";
        msg += "[" + gsMsg.getMessage("cmn.trash") + "]";
        msg += __logAtDelSettingKbn(req, NullDefault.getString(wmlForm.getWml050dustKbn(), "0"),
                String.valueOf(wmlForm.getWml050dustYear()),
                String.valueOf(wmlForm.getWml050dustMonth()),
                String.valueOf(wmlForm.getWml050dustDay()));
        msg += "\r\n[" + gsMsg.getMessage("wml.19") + "]";
        msg += __logAtDelSettingKbn(req, NullDefault.getString(wmlForm.getWml050sendKbn(), "0"),
                String.valueOf(wmlForm.getWml050sendYear()),
                String.valueOf(wmlForm.getWml050sendMonth()),
                String.valueOf(wmlForm.getWml050sendDay()));
        msg += "\r\n[" + gsMsg.getMessage("cmn.draft") + "]";
        msg += __logAtDelSettingKbn(req, NullDefault.getString(wmlForm.getWml050draftKbn(), "0"),
                String.valueOf(wmlForm.getWml050draftYear()),
                String.valueOf(wmlForm.getWml050draftMonth()),
                String.valueOf(wmlForm.getWml050draftDay()));
        msg += "\r\n[" + gsMsg.getMessage("wml.37") + "]";
        msg += __logAtDelSettingKbn(req, NullDefault.getString(wmlForm.getWml050resvKbn(), "0"),
                String.valueOf(wmlForm.getWml050resvYear()),
                String.valueOf(wmlForm.getWml050resvMonth()),
                String.valueOf(wmlForm.getWml050resvDay()));
        msg += "\r\n[" + gsMsg.getMessage("cmn.strage") + "]";
        msg += __logAtDelSettingKbn(req, NullDefault.getString(wmlForm.getWml050keepKbn(), "0"),
                String.valueOf(wmlForm.getWml050keepYear()),
                String.valueOf(wmlForm.getWml050keepMonth()),
                String.valueOf(wmlForm.getWml050keepDay()));
        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.autodelete.setting");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, change, GSConstLog.LEVEL_INFO, msg);
    }

    /**
     * <br>[機  能] ログ メッセージ作成用
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param kbn 区分
     * @param year 年
     * @param month 月
     * @param day 日
     * @return メッセージ
     */
    private String __logAtDelSettingKbn(
        HttpServletRequest req, String kbn, String year, String month, String day) {

        String ret = "";
        GsMessage gsMsg = new GsMessage(req);

        if (Integer.parseInt(kbn) == (GSConstWebmail.WAD_DUST_NO)) {
            ret += gsMsg.getMessage("cmn.noset");
        } else if (Integer.parseInt(kbn) == (GSConstWebmail.WAD_DUST_LOGOUT)) {
            ret += gsMsg.getMessage("wml.wml050.02");
        } else if (Integer.parseInt(kbn) == (GSConstWebmail.WAD_DUST_AUTO)) {
            ret += gsMsg.getMessage("cmn.autodelete");
            ret += " - " + year + gsMsg.getMessage("cmn.year2") + " "
                     + month + gsMsg.getMessage("cmn.months2") + " "
                     + day + gsMsg.getMessage("cmn.day") + " " + gsMsg.getMessage("wml.73");
        }

        return ret;
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param wmlForm アクションフォーム
     */
    private void __setWml170AtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Wml170knForm wmlForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String msg = "";
        msg += "[" +  gsMsg.getMessage("wml.wml010.25") + "] ";
        msg += "\r\n";
        msg += "[" +  gsMsg.getMessage("cmn.autodelete") + "] ";
        msg += "\r\n";
        if (wmlForm.getWml170delKbn().equals("0")) {
            msg += gsMsg.getMessage("cmn.noset");
        } else if (wmlForm.getWml170delKbn().equals("1")) {
            String year = Integer.toString(wmlForm.getWml170delYear());
            String month = Integer.toString(wmlForm.getWml170delMonth());
            String day = Integer.toString(wmlForm.getWml170delDay());
            msg += gsMsg.getMessage(req, "cmn.automatically.delete");
            msg += "\r\n";
            msg += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            msg += gsMsg.getMessage(req, "cmn.year", year);
            msg += gsMsg.getMessage(req, "cmn.months", month);
            msg += day + gsMsg.getMessage(req, "cmn.day");
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
     * <br>[解  説] WEBメールについての処理を行う
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
        //Webメールは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_WML, pconfig)) {
            return false;
        }

        Wml060Biz biz = new Wml060Biz();
        Wml060Form wmlForm = new Wml060Form();
        Wml060ParamModel paramMdl = new Wml060ParamModel();
        paramMdl.setParam(wmlForm);
        biz.setInitData(reqMdl, paramMdl);
        paramMdl.setFormData(wmlForm);
        req.setAttribute("wml060Form", wmlForm);
        
        Wml180Biz wml180biz = new Wml180Biz();
        Wml180Form wml180Form = new Wml180Form();
        Wml180ParamModel wml180paramMdl = new Wml180ParamModel();
        wml180paramMdl.setParam(wml180Form);
        wml180biz.setInitData(con, reqMdl, wml180paramMdl);
        wml180paramMdl.setFormData(wml180Form);
        req.setAttribute("wml180Form", wml180Form);

        return true;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時前に入力チェックとして実行される
     * <br>[解  説] チャットについての処理を行う
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
        if (!pluginId.equals(GSConst.PLUGINID_WML)) {
            return null;
        }

        ActionErrors errors = new ActionErrors();
        String gamenId = req.getParameter("gamenId");
        if (gamenId.equals("wml060")) {
            Wml060knForm wmlForm = new Wml060knForm();
            req.getParameterMap();
            Wml060knParamModel paramMdl = new Wml060knParamModel();
            paramMdl.setParam(wmlForm);
            paramMdl.setWml060delKbn1(
                    NullDefault.getString(req.getParameter("wml060delKbn1"), "0"));
            paramMdl.setWml060delYear1(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delYear1"), "0")));
            paramMdl.setWml060delMonth1(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delMonth1"), "0")));
            paramMdl.setWml060delDay1(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delDay1"), "0")));
            paramMdl.setWml060delKbn2(
                    NullDefault.getString(req.getParameter("wml060delKbn2"), "0"));
            paramMdl.setWml060delYear2(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delYear2"), "0")));
            paramMdl.setWml060delMonth2(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delMonth2"), "0")));
            paramMdl.setWml060delDay2(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delDay2"), "0")));
            paramMdl.setWml060delKbn3(
                    NullDefault.getString(req.getParameter("wml060delKbn3"), "0"));
            paramMdl.setWml060delYear3(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delYear3"), "0")));
            paramMdl.setWml060delMonth3(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delMonth3"), "0")));
            paramMdl.setWml060delDay3(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delDay3"), "0")));
            paramMdl.setWml060delKbn4(
                    NullDefault.getString(req.getParameter("wml060delKbn4"), "0"));
            paramMdl.setWml060delYear4(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delYear4"), "0")));
            paramMdl.setWml060delMonth4(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delMonth4"), "0")));
            paramMdl.setWml060delDay4(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delDay4"), "0")));
            paramMdl.setWml060delKbn5(
                    NullDefault.getString(req.getParameter("wml060delKbn5"), "0"));
            paramMdl.setWml060delYear5(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delYear5"), "0")));
            paramMdl.setWml060delMonth5(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delMonth5"), "0")));
            paramMdl.setWml060delDay5(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delDay5"), "0")));
            paramMdl.setFormData(wmlForm);

            errors = wmlForm.validateCheck(reqMdl);

        } else if (gamenId.equals("wml180")) {
            Wml180knForm wmlForm = new Wml180knForm();
            Wml180knParamModel paramMdl = new Wml180knParamModel();
            paramMdl.setWml180delKbn(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delKbn"), "0")));
            paramMdl.setWml180delYear(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delYear"), "0")));
            paramMdl.setWml180delMonth(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delMonth"), "0")));
            paramMdl.setWml180delDay(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delDay"), "0")));
            paramMdl.setWml180delYearFr(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delYearFr"), "0")));
            paramMdl.setWml180delMonthFr(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delMonthFr"), "0")));
            paramMdl.setWml180delDayFr(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delDayFr"), "0")));
            paramMdl.setWml180delYearTo(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delYearTo"), "0")));
            paramMdl.setWml180delMonthTo(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delMonthTo"), "0")));
            paramMdl.setWml180delDayTo(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delDayTo"), "0")));
            paramMdl.setFormData(wmlForm);

            errors = wmlForm.validateCheck(reqMdl, errors);
        }
        return errors;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時に実行される
     * <br>[解  説] WEBメールについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteConf(Connection con, HttpServletRequest req, 
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGINID_WML)) {
            return;
        }
        String gamenId = req.getParameter("gamenId");
        if (gamenId.equals("wml060")) {
            Wml060knBiz biz = new Wml060knBiz(con);
            Wml060knForm wmlForm = new Wml060knForm();
            req.getParameterMap();
            Wml060knParamModel paramMdl = new Wml060knParamModel();
            paramMdl.setParam(wmlForm);
            paramMdl.setWml060delKbn1(
                    NullDefault.getString(req.getParameter("wml060delKbn1"), "0"));
            paramMdl.setWml060delYear1(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delYear1"), "0")));
            paramMdl.setWml060delMonth1(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delMonth1"), "0")));
            paramMdl.setWml060delDay1(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delDay1"), "0")));
            paramMdl.setWml060delKbn2(
                    NullDefault.getString(req.getParameter("wml060delKbn2"), "0"));
            paramMdl.setWml060delYear2(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delYear2"), "0")));
            paramMdl.setWml060delMonth2(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delMonth2"), "0")));
            paramMdl.setWml060delDay2(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delDay2"), "0")));
            paramMdl.setWml060delKbn3(
                    NullDefault.getString(req.getParameter("wml060delKbn3"), "0"));
            paramMdl.setWml060delYear3(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delYear3"), "0")));
            paramMdl.setWml060delMonth3(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delMonth3"), "0")));
            paramMdl.setWml060delDay3(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delDay3"), "0")));
            paramMdl.setWml060delKbn4(
                    NullDefault.getString(req.getParameter("wml060delKbn4"), "0"));
            paramMdl.setWml060delYear4(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delYear4"), "0")));
            paramMdl.setWml060delMonth4(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delMonth4"), "0")));
            paramMdl.setWml060delDay4(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delDay4"), "0")));
            paramMdl.setWml060delKbn5(
                    NullDefault.getString(req.getParameter("wml060delKbn5"), "0"));
            paramMdl.setWml060delYear5(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delYear5"), "0")));
            paramMdl.setWml060delMonth5(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delMonth5"), "0")));
            paramMdl.setWml060delDay5(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml060delDay5"), "0")));
            biz.setData(reqMdl, paramMdl, reqMdl.getSmodel().getUsrsid());
            paramMdl.setFormData(wmlForm);
            req.setAttribute("wml060knForm", wmlForm);
            __setWml060MtDelOpLog(con, req, reqMdl, wmlForm);
        } else if (gamenId.equals("wml180")) {
            Wml180knBiz biz = new Wml180knBiz(con);
            Wml180knForm wmlForm = new Wml180knForm();
            Wml180knParamModel paramMdl = new Wml180knParamModel();
            paramMdl.setWml180delKbn(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delKbn"), "0")));
            paramMdl.setWml180delYear(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delYear"), "0")));
            paramMdl.setWml180delMonth(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delMonth"), "0")));
            paramMdl.setWml180delDay(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delDay"), "0")));
            paramMdl.setWml180delYearFr(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delYearFr"), "0")));
            paramMdl.setWml180delMonthFr(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delMonthFr"), "0")));
            paramMdl.setWml180delDayFr(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delDayFr"), "0")));
            paramMdl.setWml180delYearTo(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delYearTo"), "0")));
            paramMdl.setWml180delMonthTo(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delMonthTo"), "0")));
            paramMdl.setWml180delDayTo(Integer.parseInt(
                    NullDefault.getString(req.getParameter("wml180delDayTo"), "0")));
            biz.setData(paramMdl, reqMdl.getSmodel().getUsrsid());
            paramMdl.setFormData(wmlForm);
            req.setAttribute("wml180knForm", wmlForm);
            __setWml180MtDelOpLog(con, req, reqMdl, wmlForm);
        }
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param wmlForm アクションフォーム
     */
    private void __setWml060MtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Wml060knForm wmlForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String delete = gsMsg.getMessage(req, "cmn.delete");

        String msg = "";
        msg += "[" +  gsMsg.getMessage("wml.wml010.25") + "] ";
        msg += "\r\n";
        msg += "[" +  gsMsg.getMessage("cmn.manual.delete2") + "] ";
        msg += "\r\n";
        msg += "[" + gsMsg.getMessage("cmn.trash") + "]";
        msg += __logMtDelSettingKbn(req, NullDefault.getString(wmlForm.getWml060delKbn1(), "0"),
                String.valueOf(wmlForm.getWml060delYear1()),
                String.valueOf(wmlForm.getWml060delMonth1()),
                String.valueOf(wmlForm.getWml060delDay1()));
        msg += "\r\n[" + gsMsg.getMessage("wml.19") + "]";
        msg += __logMtDelSettingKbn(req, NullDefault.getString(wmlForm.getWml060delKbn2(), "0"),
                String.valueOf(wmlForm.getWml060delYear2()),
                String.valueOf(wmlForm.getWml060delMonth2()),
                String.valueOf(wmlForm.getWml060delDay2()));
        msg += "\r\n[" + gsMsg.getMessage("cmn.draft") + "]";
        msg += __logMtDelSettingKbn(req, NullDefault.getString(wmlForm.getWml060delKbn3(), "0"),
                String.valueOf(wmlForm.getWml060delYear3()),
                String.valueOf(wmlForm.getWml060delMonth3()),
                String.valueOf(wmlForm.getWml060delDay3()));
        msg += "\r\n[" + gsMsg.getMessage("wml.37") + "]";
        msg += __logMtDelSettingKbn(req, NullDefault.getString(wmlForm.getWml060delKbn4(), "0"),
                String.valueOf(wmlForm.getWml060delYear4()),
                String.valueOf(wmlForm.getWml060delMonth4()),
                String.valueOf(wmlForm.getWml060delDay4()));
        msg += "\r\n[" + gsMsg.getMessage("cmn.strage") + "]";
        msg += __logMtDelSettingKbn(req, NullDefault.getString(wmlForm.getWml060delKbn5(), "0"),
                String.valueOf(wmlForm.getWml060delYear5()),
                String.valueOf(wmlForm.getWml060delMonth5()),
                String.valueOf(wmlForm.getWml060delDay5()));

        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.manual.delete2");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, delete, GSConstLog.LEVEL_INFO, msg);
    }

    /**
     * <br>[機  能] ログ メッセージ作成用
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param kbn 区分
     * @param year 年
     * @param month 月
     * @param day 日
     * @return メッセージ
     */
    private String __logMtDelSettingKbn(
        HttpServletRequest req, String kbn, String year, String month, String day) {

        String ret = "";
        GsMessage gsMsg = new GsMessage(req);

        if (kbn.equals(String.valueOf(GSConstWebmail.MANU_DEL_OK))) {
            ret += gsMsg.getMessage("wml.60");
            ret += " - " + year + gsMsg.getMessage("cmn.year2") + " "
                     + month + gsMsg.getMessage("cmn.months2") + " "
                     + day + gsMsg.getMessage("cmn.day") + " " + gsMsg.getMessage("wml.73");
        } else {
            ret += gsMsg.getMessage("cmn.dont.delete");
        }

        return ret;
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param wmlForm アクションフォーム
     */
    private void __setWml180MtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Wml180knForm wmlForm) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String delete = gsMsg.getMessage(req, "cmn.delete");
        String year = null;
        String month = null;
        String day = null;

        String msg = "";
        msg += "[" +  gsMsg.getMessage("wml.wml010.25") + "] ";
        msg += "\r\n";
        msg += "[" +  gsMsg.getMessage("cmn.manual.delete2") + "] ";
        
        if (wmlForm.getWml180delKbn() == 0) {
            msg += gsMsg.getMessage("wml.wml180.01");
            msg += "\r\n";
            year = Integer.toString(wmlForm.getWml180delYear());
            month = Integer.toString(wmlForm.getWml180delMonth());
            day = Integer.toString(wmlForm.getWml180delDay());
            msg += gsMsg.getMessage(req, "cmn.year", year);
            msg += gsMsg.getMessage(req, "cmn.months", month);
            msg += day + gsMsg.getMessage(req, "cmn.day");
            msg += gsMsg.getMessage("cmn.after.data");
        } else if (wmlForm.getWml180delKbn() == 1) {
            msg += gsMsg.getMessage("wml.05");
            msg += "\r\n" + wmlForm.getWml180delYearFr();
            msg += gsMsg.getMessage("cmn.year2");
            msg += " " + wmlForm.getWml180delMonthFr();
            msg += gsMsg.getMessage("cmn.month");
                    msg += " " + wmlForm.getWml180delDayFr();
            msg += gsMsg.getMessage("cmn.day");
            msg += " ~ ";
            msg += wmlForm.getWml180delYearTo();
            msg += gsMsg.getMessage("cmn.year2");
            msg += " " + wmlForm.getWml180delMonthTo();
            msg += gsMsg.getMessage("cmn.month");
            msg += " " + wmlForm.getWml180delDayTo();
            msg += gsMsg.getMessage("cmn.day");
        } else if (wmlForm.getWml180delKbn() == 2) {
            msg += gsMsg.getMessage("cmn.delete.all");
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
     * <br>[解  説] WEBメールについての処理を行う
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
        //WEBメールのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGINID_WML)
                && canPlgList.contains(GSConst.PLUGINID_WML)) {
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
        //WEBメールのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGINID_WML)
                && canPlgList.contains(GSConst.PLUGINID_WML)) {
            return true;
        }
        return false;
    }
}