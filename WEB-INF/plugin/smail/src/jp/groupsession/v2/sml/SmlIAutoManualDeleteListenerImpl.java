package jp.groupsession.v2.sml;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.IAutoManualDeleteListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.sml150.Sml150Biz;
import jp.groupsession.v2.sml.sml150.Sml150Form;
import jp.groupsession.v2.sml.sml150.Sml150ParamModel;
import jp.groupsession.v2.sml.sml150kn.Sml150knBiz;
import jp.groupsession.v2.sml.sml150kn.Sml150knForm;
import jp.groupsession.v2.sml.sml150kn.Sml150knParamModel;
import jp.groupsession.v2.sml.sml160.Sml160Biz;
import jp.groupsession.v2.sml.sml160.Sml160Form;
import jp.groupsession.v2.sml.sml160.Sml160ParamModel;
import jp.groupsession.v2.sml.sml160kn.Sml160knBiz;
import jp.groupsession.v2.sml.sml160kn.Sml160knForm;
import jp.groupsession.v2.sml.sml160kn.Sml160knParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 自動削除設定画面作成時に使用されるリスナー
 * <br>[解  説] ショートメールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlIAutoManualDeleteListenerImpl implements IAutoManualDeleteListener {

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の表示時に実行される
     * <br>[解  説] ショートメールについての処理を行う
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
        //ショートメールは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig)) {
            return false;
        }
        Sml150Biz biz = new Sml150Biz();
        Sml150Form smlForm = new Sml150Form();

        Sml150ParamModel paramMdl = new Sml150ParamModel();
        paramMdl.setParam(smlForm);
        biz.setInitData(reqMdl, paramMdl, con);
        paramMdl.setFormData(smlForm);
        req.setAttribute("sml150Form", smlForm);
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
        
        Sml150knForm smlForm = new Sml150knForm();
        req.getParameterMap();
        Sml150knParamModel paramMdl = new Sml150knParamModel();
        paramMdl.setParam(smlForm);
        paramMdl.setSml150JdelKbn(
                NullDefault.getString(req.getParameter("sml150JdelKbn"), "0"));
        paramMdl.setSml150JYear(
                NullDefault.getString(req.getParameter("sml150JYear"), "0"));
        paramMdl.setSml150JMonth(
                NullDefault.getString(req.getParameter("sml150JMonth"), "0"));
        paramMdl.setSml150SdelKbn(
                NullDefault.getString(req.getParameter("sml150SdelKbn"), "0"));
        paramMdl.setSml150SYear(
                NullDefault.getString(req.getParameter("sml150SYear"), "0"));
        paramMdl.setSml150SMonth(
                NullDefault.getString(req.getParameter("sml150SMonth"), "0"));
        paramMdl.setSml150WdelKbn(
                NullDefault.getString(req.getParameter("sml150WdelKbn"), "0"));
        paramMdl.setSml150WYear(
                NullDefault.getString(req.getParameter("sml150WYear"), "0"));
        paramMdl.setSml150WMonth(
                NullDefault.getString(req.getParameter("sml150WMonth"), "0"));
        paramMdl.setSml150DdelKbn(
                NullDefault.getString(req.getParameter("sml150DdelKbn"), "0"));
        paramMdl.setSml150DYear(
                NullDefault.getString(req.getParameter("sml150DYear"), "0"));
        paramMdl.setSml150DMonth(
                NullDefault.getString(req.getParameter("sml150DMonth"), "0"));
        paramMdl.setFormData(smlForm);
        return smlForm.validateCheck(reqMdl);
    }

    /**
     * <br>[機  能] 管理者設定 自動削除設定画面の更新時に実行される
     * <br>[解  説] ショートメールについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doAutoDeleteConf(Connection con, HttpServletRequest req,
            RequestModel reqMdl) throws SQLException {

        Sml150knBiz biz = new Sml150knBiz(con);
        Sml150knForm smlForm = new Sml150knForm();
        req.getParameterMap();
        Sml150knParamModel paramMdl = new Sml150knParamModel();
        paramMdl.setParam(smlForm);
        paramMdl.setSml150JdelKbn(
                NullDefault.getString(req.getParameter("sml150JdelKbn"), "0"));
        paramMdl.setSml150JYear(
                NullDefault.getString(req.getParameter("sml150JYear"), "0"));
        paramMdl.setSml150JMonth(
                NullDefault.getString(req.getParameter("sml150JMonth"), "0"));
        paramMdl.setSml150SdelKbn(
                NullDefault.getString(req.getParameter("sml150SdelKbn"), "0"));
        paramMdl.setSml150SYear(
                NullDefault.getString(req.getParameter("sml150SYear"), "0"));
        paramMdl.setSml150SMonth(
                NullDefault.getString(req.getParameter("sml150SMonth"), "0"));
        paramMdl.setSml150WdelKbn(
                NullDefault.getString(req.getParameter("sml150WdelKbn"), "0"));
        paramMdl.setSml150WYear(
                NullDefault.getString(req.getParameter("sml150WYear"), "0"));
        paramMdl.setSml150WMonth(
                NullDefault.getString(req.getParameter("sml150WMonth"), "0"));
        paramMdl.setSml150DdelKbn(
                NullDefault.getString(req.getParameter("sml150DdelKbn"), "0"));
        paramMdl.setSml150DYear(
                NullDefault.getString(req.getParameter("sml150DYear"), "0"));
        paramMdl.setSml150DMonth(
                NullDefault.getString(req.getParameter("sml150DMonth"), "0"));
        SmlAdelModel delMdl = biz.updateAuteDelSetting(reqMdl, paramMdl);
        paramMdl.setFormData(smlForm);
        req.setAttribute("sml150knForm", smlForm);
        __setAtDelOpLog(con, req, reqMdl, delMdl);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param delMdl 自動削除情報
     */
    private void __setAtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, SmlAdelModel delMdl) {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String change = gsMsg.getMessage(req, "cmn.change");

        String year = null;
        String month = null;
        String value = "";
        value += "[" +  gsMsg.getMessage("cmn.shortmail") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("sml.50") + "] ";
        if (delMdl.getSadJdelKbn() == GSConstSmail.AUTO_DEL_ACCOUNT) {
            year = Integer.toString(delMdl.getSadJdelYear());
            month = Integer.toString(delMdl.getSadJdelMonth());
            value += gsMsg.getMessage(req, "cmn.automatically.delete");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value +=  gsMsg.getMessage(req, "cmn.noset");
        }

        value += "\r\n";
        value += "[" +  gsMsg.getMessage("sml.52") + "] ";
        if (delMdl.getSadSdelKbn() == GSConstSmail.AUTO_DEL_ACCOUNT) {
            year = Integer.toString(delMdl.getSadSdelYear());
            month = Integer.toString(delMdl.getSadSdelMonth());
            value += gsMsg.getMessage(req, "cmn.automatically.delete");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value +=  gsMsg.getMessage(req, "cmn.noset");
        }

        value += "\r\n";
        value += "[" +  gsMsg.getMessage("sml.51") + "] ";
        if (delMdl.getSadWdelKbn() == GSConstSmail.AUTO_DEL_ACCOUNT) {
            year = Integer.toString(delMdl.getSadWdelYear());
            month = Integer.toString(delMdl.getSadWdelMonth());
            value += gsMsg.getMessage(req, "cmn.automatically.delete");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value +=  gsMsg.getMessage(req, "cmn.noset");
        }

        value += "\r\n";
        value += "[" +  gsMsg.getMessage("sml.49") + "] ";
        if (delMdl.getSadDdelKbn() == GSConstSmail.AUTO_DEL_ACCOUNT) {
            year = Integer.toString(delMdl.getSadDdelYear());
            month = Integer.toString(delMdl.getSadJdelMonth());
            value += gsMsg.getMessage(req, "cmn.automatically.delete");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value +=  gsMsg.getMessage(req, "cmn.noset");
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
     * <br>[解  説] ショートメールについての処理を行う
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
        //ショートメールは利用可能か判定
        if (!cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig)) {
            return false;
        }

        Sml160Biz biz = new Sml160Biz();
        Sml160Form smlForm = new Sml160Form();
        Sml160ParamModel paramMdl = new Sml160ParamModel();
        paramMdl.setParam(smlForm);
        biz.setInitData(reqMdl, paramMdl, con);
        paramMdl.setFormData(smlForm);
        req.setAttribute("sml160Form", smlForm);
        return true;
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時前に入力チェックとして実行される
     * <br>[解  説] ショートメールについての処理を行う
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
        if (!pluginId.equals(GSConst.PLUGINID_SML)) {
            return null;
        }

        Sml160knForm smlForm = new Sml160knForm();
        req.getParameterMap();
        Sml160knParamModel paramMdl = new Sml160knParamModel();
        paramMdl.setParam(smlForm);
        paramMdl.setSml160SelKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("sml160SelKbn"), "0")));
        paramMdl.setSml160AccountSid(Integer.parseInt(
                NullDefault.getString(req.getParameter("sml160AccountSid"), "0")));
        paramMdl.setSml160AccountName(
                NullDefault.getString(req.getParameter("sml160AccountName"), ""));
        paramMdl.setSml160JdelKbn(
                NullDefault.getString(req.getParameter("sml160JdelKbn"), "0"));
        paramMdl.setSml160JYear(
                NullDefault.getString(req.getParameter("sml160JYear"), "0"));
        paramMdl.setSml160JMonth(
                NullDefault.getString(req.getParameter("sml160JMonth"), "0"));
        paramMdl.setSml160SdelKbn(
                NullDefault.getString(req.getParameter("sml160SdelKbn"), "0"));
        paramMdl.setSml160SYear(
                NullDefault.getString(req.getParameter("sml160SYear"), "0"));
        paramMdl.setSml160SMonth(
                NullDefault.getString(req.getParameter("sml160SMonth"), "0"));
        paramMdl.setSml160WdelKbn(
                NullDefault.getString(req.getParameter("sml160WdelKbn"), "0"));
        paramMdl.setSml160WYear(
                NullDefault.getString(req.getParameter("sml160WYear"), "0"));
        paramMdl.setSml160WMonth(
                NullDefault.getString(req.getParameter("sml160WMonth"), "0"));
        paramMdl.setSml160DdelKbn(
                NullDefault.getString(req.getParameter("sml160DdelKbn"), "0"));
        paramMdl.setSml160DYear(
                NullDefault.getString(req.getParameter("sml160DYear"), "0"));
        paramMdl.setSml160DMonth(
                NullDefault.getString(req.getParameter("sml160DMonth"), "0"));
        paramMdl.setFormData(smlForm);

        return smlForm.validateSml160(con, req);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面の更新時に実行される
     * <br>[解  説] ショートメールについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteConf(Connection con, HttpServletRequest req,
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGINID_SML)) {
            return;
        }
        Sml160knBiz biz = new Sml160knBiz(con);
        Sml160knForm smlForm = new Sml160knForm();
        req.getParameterMap();
        Sml160knParamModel paramMdl = new Sml160knParamModel();
        paramMdl.setParam(smlForm);
        paramMdl.setSml160SelKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("sml160SelKbn"), "0")));
        paramMdl.setSml160AccountSid(Integer.parseInt(
                NullDefault.getString(req.getParameter("sml160AccountSid"), "0")));
        paramMdl.setSml160AccountName(
                NullDefault.getString(req.getParameter("sml160AccountName"), ""));
        paramMdl.setSml160JdelKbn(
                NullDefault.getString(req.getParameter("sml160JdelKbn"), "0"));
        paramMdl.setSml160JYear(
                NullDefault.getString(req.getParameter("sml160JYear"), "0"));
        paramMdl.setSml160JMonth(
                NullDefault.getString(req.getParameter("sml160JMonth"), "0"));
        paramMdl.setSml160SdelKbn(
                NullDefault.getString(req.getParameter("sml160SdelKbn"), "0"));
        paramMdl.setSml160SYear(
                NullDefault.getString(req.getParameter("sml160SYear"), "0"));
        paramMdl.setSml160SMonth(
                NullDefault.getString(req.getParameter("sml160SMonth"), "0"));
        paramMdl.setSml160WdelKbn(
                NullDefault.getString(req.getParameter("sml160WdelKbn"), "0"));
        paramMdl.setSml160WYear(
                NullDefault.getString(req.getParameter("sml160WYear"), "0"));
        paramMdl.setSml160WMonth(
                NullDefault.getString(req.getParameter("sml160WMonth"), "0"));
        paramMdl.setSml160DdelKbn(
                NullDefault.getString(req.getParameter("sml160DdelKbn"), "0"));
        paramMdl.setSml160DYear(
                NullDefault.getString(req.getParameter("sml160DYear"), "0"));
        paramMdl.setSml160DMonth(
                NullDefault.getString(req.getParameter("sml160DMonth"), "0"));
        paramMdl.setFormData(smlForm);

        biz.updateSyudoDelSetting(paramMdl, reqMdl);
        req.setAttribute("sml160knForm", smlForm);
        __setMtDelOpLog(con, req, reqMdl, smlForm);
    }

    /**
     * <br>[機  能] オペレーションログの内容セット
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param smlForm アクションフォーム
     * @throws SQLException 
     */
    private void __setMtDelOpLog(Connection con, HttpServletRequest req,
            RequestModel reqMdl, Sml160knForm smlForm) throws SQLException {

        CommonBiz biz = new CommonBiz();
        GsMessage gsMsg = new GsMessage();
        String delete = gsMsg.getMessage(req, "cmn.delete");
        String year = null;
        String month = null;

        String accountName;
        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = sacDao.select(smlForm.getSml160AccountSid());
        if (smlForm.getSml160SelKbn() != GSConstSmail.ACCOUNT_SEL) {
            accountName = gsMsg.getMessage("wml.wml120.01");
        } else {
            accountName = sacMdl.getSacName();
        }

        String value = "";
        value += "[" +  gsMsg.getMessage("cmn.shortmail") + "] ";
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("cmn.target")
                + gsMsg.getMessage("cmn.pdf.account") + "] ";
        value += accountName;
        value += "\r\n";
        value += "[" +  gsMsg.getMessage("sml.57") + "] ";
        if (Integer.parseInt(smlForm.getSml160JdelKbn())
                == GSConstSmail.AUTO_DEL_ACCOUNT) {
            year = smlForm.getSml160JYear();
            month = smlForm.getSml160JMonth();
            value += gsMsg.getMessage(req, "wml.60");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value += gsMsg.getMessage("cmn.dont.delete");
        }

        value += "\r\n";
        value += "[" +  gsMsg.getMessage("sml.59") + "] ";
        if (Integer.parseInt(smlForm.getSml160SdelKbn())
                == GSConstSmail.AUTO_DEL_ACCOUNT) {
            year = smlForm.getSml160SYear();
            month = smlForm.getSml160SMonth();
            value += gsMsg.getMessage(req, "wml.60");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value += gsMsg.getMessage("cmn.dont.delete");
        }

        value += "\r\n";
        value += "[" +  gsMsg.getMessage("sml.58") + "] ";
        if (Integer.parseInt(smlForm.getSml160WdelKbn())
                == GSConstSmail.AUTO_DEL_ACCOUNT) {
            year = smlForm.getSml160WYear();
            month = smlForm.getSml160WMonth();
            value += gsMsg.getMessage(req, "wml.60");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value += gsMsg.getMessage("cmn.dont.delete");
        }

        value += "\r\n";
        value += "[" +  gsMsg.getMessage("sml.56") + "] ";
        if (Integer.parseInt(smlForm.getSml160DdelKbn())
                == GSConstSmail.AUTO_DEL_ACCOUNT) {
            year = smlForm.getSml160DYear();
            month = smlForm.getSml160DMonth();
            value += gsMsg.getMessage(req, "wml.60");
            value += "\r\n";
            value += "[" + gsMsg.getMessage("cmn.passage.year.month") + "] ";
            value += gsMsg.getMessage(req, "cmn.year", year);
            value += gsMsg.getMessage(req, "cmn.months", month);
        } else {
            value += gsMsg.getMessage("cmn.dont.delete");
        }
        String dspName = gsMsg.getMessage("cmn.admin.setting") + " "
                + gsMsg.getMessage("cmn.manual.delete2");

        biz.outPutListenerLog(
                req, reqMdl, con, reqMdl.getSmodel().getUsrsid(),
                this.getClass().getCanonicalName(),
                dspName, delete, GSConstLog.LEVEL_INFO, value);
    }

    /**
     * <br>[機  能] 管理者設定 手動削除画面のアカウント変更時に実行される
     * <br>[解  説] ショートメールについての処理を行う
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @throws SQLException 実行例外
     */
    public void doManualDeleteChangeAccount(Connection con, HttpServletRequest req,
            RequestModel reqMdl) throws SQLException {

        String pluginId = NullDefault.getString(req.getParameter("pluginId"), "");
        if (!pluginId.equals(GSConst.PLUGINID_SML)) {
            return;
        }
        String accountSid = NullDefault.getString(req.getParameter("accountSid"), "0");
        String accountName = NullDefault.getString(req.getParameter("accountName"), "");
        Sml160Biz biz = new Sml160Biz();
        Sml160Form smlForm = new Sml160Form();
        try {
            BeanUtils.copyProperties(smlForm, req.getParameterMap());
        } catch (IllegalAccessException | InvocationTargetException e) {
        }

        Sml160ParamModel paramMdl = new Sml160ParamModel();

        smlForm.setSml160AccountSid(Integer.parseInt(accountSid));
        smlForm.setSml160AccountName(accountName);;
        paramMdl.setParam(smlForm);
        biz.setInitData(reqMdl, paramMdl, con);
        paramMdl.setFormData(smlForm);
        req.setAttribute("sml160Form", smlForm);
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
        //ショートメールのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGINID_SML)
                && canPlgList.contains(GSConst.PLUGINID_SML)) {
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
        //ショートメールのプラグイン管理者(システム管理者)且つ利用可能か判定
        if (cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(), GSConst.PLUGINID_SML)
                && canPlgList.contains(GSConst.PLUGINID_SML)) {
            return true;
        }
        return false;
    }
}