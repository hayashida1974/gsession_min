package jp.groupsession.v2.man.man002;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.IGsResourceManager;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.GSConfigConst;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginAdminDao;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.lic.GSConstLicese;
import jp.groupsession.v2.lic.LicenseModel;
import jp.groupsession.v2.lic.LicenseOperation;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.struts.AdminAction;

/**
 * <br>[機  能] メイン 管理者設定メニュー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man002Action extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man002Action.class);

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return true;
    }

    /**
     * <br>[機  能] アクションを実行する
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
        log__.debug("START");
        ActionForward forward = null;

        try {
            if (!_isSystemAdmin(req, con)) {
                CmnPluginAdminDao dao = new CmnPluginAdminDao(con);
                int count = dao.getCountPluginAdmin(getSessionUserSid(req));
                if (count == 0) {
                    forward = getNotAdminSeniPage(map, req);
                    return forward;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        Man002Form thisForm = (Man002Form) form;
        log__.debug("*****CMD = " + cmd);
        if (cmd.equals("man002back")) {
            forward = __doBack(map, thisForm, req, res, con);
        } else if (cmd.equals("enterpriseiinfo")) {
            forward = map.findForward("enterpriseiinfo");
        } else if (cmd.equals("grouplist")) {
            forward = map.findForward("grouplist");
        } else if (cmd.equals("userlist")) {
            forward = map.findForward("userlist");
        } else if (cmd.equals("pconfedit")) {
            forward = map.findForward("pconfedit");
        } else if (cmd.equals("holidaylist")) {
            forward = map.findForward("holidaylist");
        } else if (cmd.equals("jobschedule")) {
            forward = map.findForward("jobschedule");
        } else if (cmd.equals("lastlogin")) {
            forward = map.findForward("lastlogin");
        } else if (cmd.equals("diskconf")) {
            forward = map.findForward("diskconf");
        } else if (cmd.equals("pxyconf")) {
            forward = map.findForward("pxyconf");
        } else if (cmd.equals("backupconf")) {
            forward = map.findForward("backupconf");
        } else if (cmd.equals("manualbackup")) {
            forward = map.findForward("manualbackup");
        } else if (cmd.equals("loglist")) {
            forward = map.findForward("loglist");
        } else if (cmd.equals("poslist")) {
            forward = map.findForward("poslist");
        } else if (cmd.equals("pluginlist")) {
            forward = map.findForward("pluginlist");
        } else if (cmd.equals("fileConf")) {
            forward = map.findForward("fileConf");
        } else if (cmd.equals("sessiontime")) {
            forward = map.findForward("sessiontime");
        //GS管理者用 ワンタイムパスワード通知先メールアドレス変更
        } else if (cmd.equals("otp_sendto_address")) {
            forward = map.findForward("otp_sendto_address");
        //ライセンス登録・更新
        } else if (cmd.equals("licenseReg")) {
            forward = map.findForward("licenseReg");
        //ログイン履歴自動削除
        } else if (cmd.equals("lhisAutoDel")) {
            forward = map.findForward("lhisAutoDel");
        //ログイン履歴手動削除
        } else if (cmd.equals("lhisSyudoDel")) {
            forward = map.findForward("lhisSyudoDel");
        //GSファイアウォール設定
        } else if (cmd.equals("firewallConf")) {
            forward = map.findForward("firewallConf");
        //パスワードルール設定
        } else if (cmd.equals("pswdConf")) {
            forward = map.findForward("pswdConf");
        //モバイル使用一括設定
        } else if (cmd.equals("mblUse")) {
            forward = map.findForward("mblUse");
        //自動削除設定
        } else if (cmd.equals("autoDel")) {
            forward = map.findForward("autoDel");
        //手動削除
        } else if (cmd.equals("manualDel")) {
            forward = map.findForward("manualDel");
        //並び順設定
        } else if (cmd.equals("sortConfig")) {
            forward = map.findForward("sortConfig");
        //システム情報確認
        } else if (cmd.equals("system")) {
            forward = map.findForward("system");
        //オペレーションログ自動削除
        } else if (cmd.equals("oplogAutoDel")) {
            forward = map.findForward("oplogAutoDel");
        //オペレーションログ手動削除
        } else if (cmd.equals("oplogSyudoDel")) {
            forward = map.findForward("oplogSyudoDel");
        //オペレーションログ設定
        } else if (cmd.equals("oplogConf")) {
            forward = map.findForward("oplogConf");
        //オペレーションログ検索
        } else if (cmd.equals("oplogSearch")) {
            forward = map.findForward("oplogSearch");
        //ログイン設定
        } else if (cmd.equals("loginConf")) {
            forward = map.findForward("loginConf");
        //ワンタイムパスワード設定
        } else if (cmd.equals("otpConf")) {
            forward = map.findForward("otpConf");
        //所属情報一括設定
        } else if (cmd.equals("memshipconf")) {
            forward = map.findForward("memshipconf");
        //最終ログイン時間
        } else if (cmd.equals("beLogTime")) {
            forward = map.findForward("beLogTime");
        //メイン画面レイアウト設定
        } else if (cmd.equals("layoutConfig")) {
            forward = map.findForward("layoutConfig");
        //ログイン履歴統計情報
        } else if (cmd.equals("manLogCount")) {
            forward = map.findForward("manLogCount");
        //ユーザ連携自動インポート設定
        } else if (cmd.equals("linkTime")) {
            forward = map.findForward("linkTime");
        //オープンリダイレクト制限設定
        } else if (cmd.equals("externalPage")) {
            forward = map.findForward("externalPage");
        //サイボウズLiveデータ取り込み
        } else if (cmd.equals("cybozuImport")) {
            forward = map.findForward("cybozuImport");
        //OAuth認証情報管理
        } else if (cmd.equals("oauthManager")) {
            forward = map.findForward("oauthManager");
        //データ使用量一覧
        } else if (cmd.equals("useddata")) {
            forward = map.findForward("useddata");
        //ショートメール通知設定
        } else if (cmd.equals("smlConf")) {
            forward = map.findForward("smlConf");
        //表示設定
        } else if (cmd.equals("dspConf")) {
            forward = map.findForward("dspConf");
        } else if (cmd.equals("apiConnectManager")) {
            forward = map.findForward("apiConnectManager");
        } else {
            //デフォルト メイン画面表示
            forward = __doInit(map, (Man002Form) form, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
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
    private ActionForward __doInit(ActionMapping map,
                                    Man002Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        String licenseId = "";
        String licenseCount = "";
        String licenseCom = "";
        String licenseEntry = "";
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

                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseFreeSid())) {
                    licenseEntry = "登録済み";
                }

                if (!StringUtil.isNullZeroString(lmdl.getType())
                        && !lmdl.getType().substring(0, 1).equals(GSConstLicese.TYPE_INI_FREE)) {
                    form.setMan002FreeLinkFlg(GSConst.DSP_NOT);
                }

                LicenseModel pmdl = null;
                MainCommonBiz mainBiz = new MainCommonBiz();
                //プラグイン情報 サポート
                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitSupport())) {
                    pmdl = new LicenseModel();
                    pmdl.setPluginName(getInterMessage(req, "cmn.support"));
                    pmdl.setLicenseLimit(lmdl.getLicenseLimitSupport());
                    int licenselim = mainBiz.licenseCheck(lmdl.getLicenseLimitSupport());
                    pmdl.setLicenseOverFlag(licenselim);
                    pluginList.add(pmdl);
                }

                //プラグイン情報 モバイル
                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitMobile())) {
                    pmdl = new LicenseModel();
                    pmdl.setPluginName(getInterMessage(req, "mobile.4"));
                    pmdl.setLicenseLimit(lmdl.getLicenseLimitMobile());
                    int licenselim = mainBiz.licenseCheck(lmdl.getLicenseLimitMobile());
                    pmdl.setLicenseOverFlag(licenselim);
                    pluginList.add(pmdl);
                }

                //プラグイン情報 CrossRide
                if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitCrossRide())) {
                    pmdl = new LicenseModel();
                    pmdl.setPluginName(GSConstLicese.PLUGIN_NAME_CROSSRIDE);
                    pmdl.setLicenseLimit(lmdl.getLicenseLimitCrossRide());
                    int licenselim = mainBiz.licenseCheck(lmdl.getLicenseLimitCrossRide());
                    pmdl.setLicenseOverFlag(licenselim);
                    pluginList.add(pmdl);
                }

                //ライセンス種別によるシステム情報表示設定
                if (!StringUtil.isNullZeroString(lmdl.getType())
                        && lmdl.getType().substring(0, 1).equals(GSConstLicese.TYPE_INI_BYCLOUD)) {
                    form.setMan002SysInfDspFlg(GSConstMain.SYS_INF_DSP_NO);
                } else if (map.findForward("system") == null) {
                    form.setMan002SysInfDspFlg(GSConstMain.SYS_INF_DSP_NO);
                }
            } else {
                licenseEntry = "未登録";
            }
        }

        con.setAutoCommit(true);

        RequestModel reqMdl = getRequestModel(req);
        form.setLicenseId(licenseId);
        form.setLicenseCount(licenseCount);
        form.setLicenseCom(licenseCom);
        form.setLicenseEntry(licenseEntry);
        form.setPluginList(pluginList);
        UserBiz userBiz = new UserBiz();
        form.setUserCount(String.valueOf(userBiz.getActiveUserCount(con)));
        form.setLimitUserCount(
            String.valueOf(
                    GroupSession.getResourceManager().getUserCountLimit(reqMdl)));
        ITempFileUtil tempFileUtil
            = (ITempFileUtil) GroupSession.getContext().get(GSContext.TEMP_FILE_UTIL);
        form.setTempFileHozonKbn(String.valueOf(tempFileUtil.getTempFileHozonKbn()));
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        form.setDbKbn(String.valueOf(dbUtil.getDbType()));

        //無料ライセンス発行画面URLを取得
        String licenseFreeUrl = ConfigBundle.getValue("LICENSE_PAGE_FOR_FREE");
        form.setLicenseFreeUrl(licenseFreeUrl);

        //ライセンス購入画面URLを取得
        String licenseUrl = null;

        if (!StringUtil.isNullZeroStringSpace(GroupSession.getResourceManager().getDomain(req))
                && !GroupSession.getResourceManager().getDomain(req).equals(GSConst.GS_DOMAIN)) {
            if (ConfigBundle.getValue("LICENSE_PAGE_URL_CLOUD") != null) {
                //設定ファイル(servername.conf)の指定ディレクトリ
                form.setDomain(GroupSession.getResourceManager().getDomain(req));
                licenseUrl = ConfigBundle.getValue("LICENSE_PAGE_URL_CLOUD");
                form.setLicensePageUrl(licenseUrl);
            }
        } else {
            if (ConfigBundle.getValue("LICENSE_PAGE_URL") != null) {
                //設定ファイル(servername.conf)の指定ディレクトリ
                licenseUrl = ConfigBundle.getValue("LICENSE_PAGE_URL");
                form.setLicensePageUrl(licenseUrl);
            }
        }

        //GSUIDを取得
        CmnContmDao cntDao = new CmnContmDao(con);
        String gsUid = cntDao.getGsUid();
        if (!StringUtil.isNullZeroString(gsUid)) {
            form.setGsUid(LicenseOperation.getDecryString(gsUid));
        }

        Man002ParamModel paramMdl = new Man002ParamModel();
        paramMdl.setParam(form);
        Man002Biz biz = new Man002Biz(con);

        //プラグイン設定を取得する
        PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con, reqMdl);
        HashMap<String, String> smlMap = pconfig.getPluginIdForListeners(
                getPluginConfig(req).getPluginIdList(), GSConfigConst.NAME_SMAIL_SEND_SETTING);
        HashMap<String, String> delMap = pconfig.getPluginIdForListeners(
                getPluginConfig(req).getPluginIdList(), GSConfigConst.NAME_AUTO_MANUAL_DELETE);

        biz.setInitData(paramMdl, pconfig, reqMdl, smlMap, delMap,
                getPluginConfig(req).getUserPluginIdList());
        paramMdl.setFormData(form);

        //パスワード変更の有効・無効を設定
        if (canChangePassword(con, 0)) {
            form.setChangePassword(GSConst.CHANGEPASSWORD_PARMIT);
        } else {
            form.setChangePassword(GSConst.CHANGEPASSWORD_NOTPARMIT);
        }
        //ワンタイムパスワード設定の有効・無効を設定
        ILogin loginBiz = _getLoginInstance();
        if (loginBiz.canUseOnetimePassword()) {
            form.setUseableOtp(GSConstMain.OTP_USE);
        } else {
            form.setUseableOtp(GSConstMain.OTP_NOUSE);
        }

        //システム区分
        if (!GroupSession.getResourceManager().getDomain(req).equals(GSConst.GS_DOMAIN)) {
            form.setSysKbn(1);
            //DB使用量を取得
            form.setDbUse(
                  iGsManager.getDbUse(iGsManager.getDomain(req)));
            //DB使用可能容量
            form.setDbCanUse(iGsManager.getDbCanUse(iGsManager.getDomain(req)));
        }

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 戻るボタン押下時処理
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
    private ActionForward __doBack(ActionMapping map,
                                    Man002Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        RequestModel reqMdl = getRequestModel(req);
        PluginConfig pconfig = getPluginConfig(req);
        
        CommonBiz cmnBiz = new CommonBiz();
        ActionForward forward = cmnBiz.getBackUrl(map, reqMdl, pconfig);
        return forward;
    }
}