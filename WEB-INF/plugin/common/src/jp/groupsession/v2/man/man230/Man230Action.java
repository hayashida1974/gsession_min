package jp.groupsession.v2.man.man230;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.IGsResourceManager;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;
import jp.groupsession.v2.lic.GSConstLicese;
import jp.groupsession.v2.lic.LicenseModel;
import jp.groupsession.v2.lic.LicenseOperation;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AdminAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] メイン 管理者設定 システム情報画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man230Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man230Action.class);

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
        Man230Form thisForm = (Man230Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        //クラウド版の場合は不正なアクセス
        String domain = getRequestModel(req).getDomain();
        Object licMdl = GroupSession.getResourceManager().getLicenseMdl(domain);
        String type = "";
        if (licMdl != null) {
            type = NullDefault.getString(((LicenseModel) licMdl).getType(), "");
        }
        if (!GroupSession.getResourceManager().getDomain(req).equals(GSConst.GS_DOMAIN)
                || (!StringUtil.isNullZeroString(type)
                        && type.substring(0, 1).equals(GSConstLicese.TYPE_INI_BYCLOUD))) {
            forward = map.findForward("gf_submit");
            return forward;
        }

        log__.debug("cmd = " + cmd);

        if (cmd.equals("backKtool")) {
            //戻るボタンクリック
            forward = map.findForward("ktool");

        } else if (cmd.equals("sendSystemInfo")) {
            //システム情報を送信
            __outputLog(map, req, con);
            __doInit(map, thisForm, req, res, con);
            __getSendInfo(thisForm, req, res);
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
            Man230Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
    throws Exception {

        log__.debug("初期表示");

        ServletContext context = getServlet().getServletContext();
        String confDbDir = ConfigBundle.getValue("GSDATA_DIR");
        if (StringUtil.isNullZeroString(confDbDir)) {
            confDbDir = context.getRealPath("/");
        }

        //ユーザ数の取得
        UserBiz userBiz = new UserBiz();
        form.setUserCount(String.valueOf(userBiz.getActiveUserCount(con)));

        //アプリケーションルートパスの取得
        String appRootPath = getAppRootPath();
        Man230ParamModel paramMdl = new Man230ParamModel();
        //サポート・オプションライセンス情報の取得
        getSupportOptionInfo(map, form, req, res, con);

        //プラグイン情報の取得
        PluginConfig pconfig = getPluginConfig(req);
        getPluginConfig(pconfig, form, con, req);

        //テンポラリディレクトリパスの取得
        form.setMan230TempPath(getTempPath(req));

        paramMdl.setParam(form);
        Man230Biz biz = new Man230Biz();
        biz.setInitData(con, paramMdl, context, confDbDir,
                getRequestModel(req), appRootPath, pconfig);
        paramMdl.setFormData(form);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] サポート・オプションライセンス情報の取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward getSupportOptionInfo(ActionMapping map,
                                    Man230Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        String licenseId = "";
        String licenseCount = "";
        String licenseCom = "";
        ArrayList<LicenseModel> pluginList = new ArrayList<LicenseModel>();
        IGsResourceManager iGsManager = GroupSession.getResourceManager();

        GSContext gs = getGsContext();

        if (gs != null) {
            LicenseModel lmdl
              = (LicenseModel) iGsManager.getLicenseMdl(iGsManager.getDomain(req));

            if (lmdl != null) {

                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseId())) {
                    licenseId = String.valueOf(lmdl.getLicenseId());
                    licenseCount = String.valueOf(lmdl.getLicenseNumber());
                }

                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseCom())) {
                    licenseCom = String.valueOf(lmdl.getLicenseCom());
                }

                LicenseModel pmdl = null;

                //プラグイン情報 サポート
                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitSupport())) {
                    pmdl = new LicenseModel();
                    pmdl.setPluginName(getInterMessage(req, "cmn.support"));
                    pmdl.setLicenseLimit(lmdl.getLicenseLimitSupport());
                    pluginList.add(pmdl);

                    UDate now = new UDate();
                    UDate limit = UDate.getInstanceStr(lmdl.getLicenseLimitSupport());

                    //サポート期限が残っているか判定
                    if (now.compareDateYMD(limit) >= UDate.EQUAL) {
                        form.setMan230SupportLimitFlag(String.valueOf(UDate.EQUAL));
                    }
                }

                //プラグイン情報 モバイル
                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitMobile())) {
                    pmdl = new LicenseModel();
                    pmdl.setPluginName(getInterMessage(req, "mobile.4"));
                    pmdl.setLicenseLimit(lmdl.getLicenseLimitMobile());
                    pluginList.add(pmdl);
                }

                //プラグイン情報 CrossRide
                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitCrossRide())) {
                    pmdl = new LicenseModel();
                    pmdl.setPluginName(GSConstLicese.PLUGIN_NAME_CROSSRIDE);
                    pmdl.setLicenseLimit(lmdl.getLicenseLimitCrossRide());
                    pluginList.add(pmdl);
                }
            }
        }

        con.setAutoCommit(true);

        form.setLicenseId(licenseId);
        form.setLicenseCount(licenseCount);
        form.setLicenseCom(licenseCom);
        form.setPluginList(pluginList);
        UserBiz userBiz = new UserBiz();
        form.setUserCount(String.valueOf(userBiz.getActiveUserCount(con)));
        form.setLimitUserCount(
            String.valueOf(
                    GroupSession.getResourceManager().getUserCountLimit(getRequestModel(req))));
        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);
        form.setTempFileHozonKbn(String.valueOf(tempFileUtil.getTempFileHozonKbn()));
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        form.setDbKbn(String.valueOf(dbUtil.getDbType()));

        //GSUIDを取得
        CmnContmDao cntDao = new CmnContmDao(con);
        String gsUid = cntDao.getGsUid();
        if (!StringUtil.isNullZeroString(gsUid)) {
            form.setGsUid(LicenseOperation.getDecryString(gsUid));
        }

        //パスワード変更の有効・無効を設定
        if (canChangePassword(con, 0)) {
            form.setChangePassword(GSConst.CHANGEPASSWORD_PARMIT);
        } else {
            form.setChangePassword(GSConst.CHANGEPASSWORD_NOTPARMIT);
        }

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     *
     * <br>[機  能]プラグインのON/OFF状況を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param pconfig プラグイン設定情報
     * @param form フォーム
     * @param con コネクション
     * @param req リクエスト
     * @throws Exception 実行時例外
     */
    private void getPluginConfig(
            PluginConfig pconfig,
            Man230Form form,
            Connection con,
            HttpServletRequest req) throws Exception {

        //トップ表示設定を取得
        CmnTdispDao dispDao = new CmnTdispDao(con);
        //表示用プラグインリスト
        ArrayList<String> pluginList = new ArrayList<>();
        //管理者ユーザの登録値を取得(使用に設定されているプラグインの取得)
        List<CmnTdispModel> dispList = dispDao.getAdminTdispList();
        if (dispList != null || !dispList.isEmpty()) {
            for (CmnTdispModel dbDispMdl : dispList) {
                if (dbDispMdl.getTdpPid().equals("license")
                        || dbDispMdl.getTdpPid().equals(GSConst.PLUGINID_COMMON)
                        || dbDispMdl.getTdpPid().equals("help")
                        || dbDispMdl.getTdpPid().equals("wsrelay")) {
                    continue;
                }
                Plugin plugin = pconfig.getPlugin(dbDispMdl.getTdpPid());
                if (plugin != null) {
                pluginList.add(plugin.getName(getRequestModel(req)));
                pluginList.add(String.valueOf(GSConstMain.PLUGIN_USE));
                }
            }
        }
        //未使用に設定されているプラグインの取得
        for (Plugin plugin : pconfig.getPluginDataList()) {
            if (plugin != null
            && !plugin.getId().equals(GSConst.PLUGINID_COMMON)
            && !plugin.getId().equals("help")
            && !plugin.getId().equals("license")
            && !plugin.getId().equals("wsrelay")) {
                if (pluginList.indexOf(plugin.getName(getRequestModel(req))) == -1) {
                    pluginList.add(plugin.getName(getRequestModel(req)));
                    pluginList.add(String.valueOf(GSConstMain.PLUGIN_NOT_USE));
                }
            }
        }

        form.setMan230PluginList(pluginList);
    }

    /**
     * <br>[機  能]　「報告する」ボタンが押された時にパラメータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @throws Exception 実行例外
     */
    private void __getSendInfo(
        Man230Form form,
        HttpServletRequest req,
        HttpServletResponse res
        )
        throws Exception {

        JSONObject jsonData = null;
        Man230Biz biz = new Man230Biz();
        Man230ParamModel paramMdl = new Man230ParamModel();
        paramMdl.setParam(form);

        jsonData = biz.getSystemInfo(paramMdl, getRequestModel(req));

        PrintWriter out = null;

        try {
            res.setHeader("Cache-Control", "no-cache");
            res.setContentType("application/json;charset=UTF-8");
            out = res.getWriter();
            out.print(jsonData.toString());
            out.flush();
        } catch (Exception e) {
            log__.error("jsonデータ送信失敗(ユーザプラグインクリック時URLデータ取得)");
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }

    /**
     *
     * <br>[機  能]ログを出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     */
    private void __outputLog(
            ActionMapping map,
            HttpServletRequest req,
            Connection con) {
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsmsg = new GsMessage(req);
        String opCode = gsmsg.getMessage("main.man002.66") + gsmsg.getMessage("cmn.sent");
        String value = opCode;
        cmnBiz.outPutCommonLog(map, getRequestModel(req), gsmsg, con,
                opCode, GSConstLog.LEVEL_TRACE, value);
    }
}