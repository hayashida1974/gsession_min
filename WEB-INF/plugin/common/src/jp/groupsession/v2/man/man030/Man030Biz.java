package jp.groupsession.v2.man.man030;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.ISmlSendSettingListener;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.PrivateSettingInfo;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnDispAconfDao;
import jp.groupsession.v2.cmn.dao.base.CmnMainscreenLayoutAdminDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.login.ILogin;
import jp.groupsession.v2.cmn.login.otp.OnetimePasswordBiz;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnDispAconfModel;
import jp.groupsession.v2.cmn.model.base.CmnMainscreenLayoutAdminModel;
import jp.groupsession.v2.cmn.model.base.CmnOtpConfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.dao.base.PtlAdmConfDao;
import jp.groupsession.v2.man.man002.Man002Biz;
import jp.groupsession.v2.man.model.ManAdmSettingInfoModel;
import jp.groupsession.v2.man.model.base.CmnPconfEditModel;
import jp.groupsession.v2.man.model.base.PtlAdmConfModel;

/**
 * <br>[機  能] メイン 個人設定メニュー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man030Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man002Biz.class);

    /**
     * <br>[機  能] 初期表示画面情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param pconfig プラグイン設定情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param lisMap リスナーマップ
     * @param req リクエスト
     * @param pidList ユーザプラグインIDリスト
     * @throws Exception SQL実行時例外
     */
    public void setInitData(Man030ParamModel paramMdl, PluginConfig pconfig, Connection con,
                            RequestModel reqMdl, HashMap<String, String> lisMap,
                            HttpServletRequest req, ArrayList<String> pidList)
    throws Exception {

        log__.debug("setInitData Start");

        int userSid = reqMdl.getSmodel().getUsrsid();

        //表示用プラグインリスト
        ArrayList <ManAdmSettingInfoModel> dspList = new ArrayList<ManAdmSettingInfoModel>();
        ManAdmSettingInfoModel dspModel = null;

        //plugin.xmlに記述されたものは無条件に許可
        List < Plugin > plist = pconfig.getPluginDataList();

        CmnTdispDao tdispDao = new CmnTdispDao(con);
        List<String> pluginList = tdispDao.getMenuPluginIdList(userSid);

        CmnContmDao cntDao = new CmnContmDao(con);
        CmnContmModel cntmModel = cntDao.select();

        //管理者設定でメニュー固定にしているか、plugin.xmlに設定が無い場合表示する。
        boolean priConfExist = true;
        if (pluginList == null || pluginList.size() < 1 || cntmModel.getCntMenuStatic() == 1) {
            priConfExist = false;
        }
        boolean dspFlg;
        PrivateSettingInfo memoInfo = null;
        Plugin memoPlugin = null;
        for (Plugin plugin : plist) {
            dspFlg = false;
            PrivateSettingInfo psinfo = plugin.getPrivateSettingInfo();
            if (psinfo != null) {

                if (plugin.getId().equals(GSConstMain.PLUGIN_ID_MEMO)) {
                    memoPlugin = plugin;
                    memoInfo = psinfo;
                }

                //表示/非表示チェック
                if (!NullDefault.getString(psinfo.getView(), "false").equals("true")) {
                    continue;
                }

                if (priConfExist) {
                    for (String pid : pluginList) {
                        if (pid.equals(plugin.getId())) {
                            dspFlg = true;
                        }
                    }

                    if (!dspFlg) {
                        continue;
                    }
                }

                dspModel = new ManAdmSettingInfoModel();
                dspModel.setName(plugin.getName(reqMdl));
                dspModel.setId(plugin.getId());
                dspModel.setUrl(psinfo.getUrl()
                        + "?backScreen=" + GSConstMain.BACK_MAIN_PRI_SETTING);
                if (!pidList.contains(plugin.getId())) {
                    dspModel.setIcon(__getIconPath(
                            "../common/images/pluginImg/original/menu_icon_"
                            + plugin.getId() + "_50.png"));
                } else {
                    dspModel.setIcon(__getIconPath(psinfo.getIcon()));
                }
                dspModel.setIconClassic(__getIconPath(psinfo.getIconClassic()));
                dspList.add(dspModel);
            }
        }
        //メニュー表示を各ユーザが設定可能の場合、メモプラグインの追加
        if (priConfExist && memoInfo != null && memoPlugin != null) {
            dspModel = new ManAdmSettingInfoModel();
            dspModel.setName(memoPlugin.getName(reqMdl));
            dspModel.setId(memoPlugin.getId());
            dspModel.setUrl(memoInfo.getUrl() + "?backScreen="
                    + GSConstMain.BACK_MAIN_PRI_SETTING);
            dspModel.setIcon(memoInfo.getIcon());
            dspModel.setIconClassic(memoInfo.getIconClassic());
            dspList.add(dspModel);
        }

        paramMdl.setPluginMenuList(dspList);

        //ポータルを使用可能か判定
        paramMdl.setPortalUseOk(GSConstMain.PLUGIN_NOT_USE);
        CommonBiz cmnBiz = new CommonBiz();
        if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_PORTAL, pconfig)) {
            PtlAdmConfDao dao = new PtlAdmConfDao(con);
            PtlAdmConfModel model = dao.select();
            if (model != null
                    && (model.getPacPtlEditkbn() == GSConstPortal.EDIT_KBN_PUBLIC
                    || model.getPacDefKbn() == GSConstPortal.EDIT_KBN_PUBLIC)) {
                paramMdl.setPortalUseOk(GSConstMain.PLUGIN_USE);
                paramMdl.setPtlSortPow(model.getPacPtlEditkbn());
                paramMdl.setPtlDefPow(model.getPacDefKbn());
            }
        }

        //ショートメールが使用可能かを判定
        if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
            paramMdl.setSmlUseOk(GSConstMain.PLUGIN_USE);
        } else {
            paramMdl.setSmlUseOk(GSConstMain.PLUGIN_NOT_USE);
        }
        boolean pluginSmlUseflg = false;
        for (String key : lisMap.keySet()) {
            String className = lisMap.get(key);
            ISmlSendSettingListener lis =
                    (ISmlSendSettingListener) Class.forName(className).newInstance();
            if (lis.doPconfSettingAvailable(con, req, reqMdl, pconfig)) {
                pluginSmlUseflg = true;
                break;
            }
        }
        if (pluginSmlUseflg) {
            paramMdl.setSmlNoticeFlg(GSConstMain.PLUGIN_USE);
        } else {
            paramMdl.setSmlNoticeFlg(GSConstMain.PLUGIN_NOT_USE);
        }

        //メイン画面レイアウト設定区分を取得する。
        CmnMainscreenLayoutAdminDao layoutDao = new CmnMainscreenLayoutAdminDao(con);
        CmnMainscreenLayoutAdminModel layoutModel = layoutDao.select();
        if (layoutModel != null) {
            paramMdl.setMainLayoutKbn(layoutModel.getMlcAdmLayoutKbn());
        }

        //システム区分
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        paramMdl.setSysKbn(dbUtil.getDbType());

        // 個人情報修正区分、パスワード修正区分取得
        MainCommonBiz manCmnBiz = new MainCommonBiz();
        CmnPconfEditModel pconfEditMdl = new CmnPconfEditModel();
        pconfEditMdl = manCmnBiz.getCpeConf(0, con);
        paramMdl.setMainPconfEditKbn(pconfEditMdl.getCpePconfKbn());
        paramMdl.setManPasswordKbn(pconfEditMdl.getCpePasswordKbn());
        //ワンタイムパスワード設定の有効・無効を設定
        ILogin loginBiz = cmnBiz.getLoginInstance();
        OnetimePasswordBiz passBiz = new OnetimePasswordBiz();
        CmnOtpConfModel otpConf = passBiz.getOtpConf(con);
        //ワンタイムパスワードが使用可能なログイン方法か
        //ワンタイムパスワード通知先アドレス編集権限があるか
        //ワンタイムパスワードを使用するか
        if (loginBiz.canUseOnetimePassword()
                && pconfEditMdl.getCpeOtpsendKbn() == GSConstMain.PCONF_EDIT_USER
                && otpConf.getCocUseOtp() == GSConstMain.OTP_USE
                ) {
            paramMdl.setUseableOtp(GSConstMain.OTP_USE);
        } else {
            paramMdl.setUseableOtp(GSConstMain.OTP_NOUSE);
        }

        //表示設定区分
        CmnDispAconfDao dao = new CmnDispAconfDao(con);
        CmnDispAconfModel model = dao.select();
        if (model != null) {
            paramMdl.setDspSettingKbn(model.getCdaRokuyouKbn());
        }
    }

    /**
     * <br>[機  能] アイコンのパスが存在しない場合に、デフォルトのアイコンパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param icon アイコンのパス
     * @return アイコンのパス
     * @throws IOToolsException ファイル操作時の例外
     */
    private String __getIconPath(String icon) throws IOToolsException {

        String defaultIcon = "../main/images/icon_plugin.gif";
        if (StringUtil.isNullZeroStringSpace(icon)) {
            return defaultIcon;
        }

        return icon;
    }

}
