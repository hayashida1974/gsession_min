package jp.groupsession.v2.man.man002;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.AdminSettingInfo;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnPluginAdminDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.model.ManAdmSettingInfoModel;

/**
 * <br>[機  能] メイン 管理者設定メニュー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man002Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man002Biz.class);
    /** Connection */
    private Connection con__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     */
    public Man002Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示画面情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param pconfig プラグイン設定情報
     * @param reqMdl リクエスト情報
     * @param smlMap ショートメールリスナー使用pluginIdマップ
     * @param delMap 自動・手動削除リスナー使用pluginIdマップ
     * @param pidList ユーザプラグインIDリスト
     * @throws Exception SQL実行時例外
     */
    public void setInitData(Man002ParamModel paramMdl,
            PluginConfig pconfig, RequestModel reqMdl, HashMap<String, String> smlMap,
            HashMap<String, String> delMap, ArrayList<String> pidList) throws Exception {

        log__.debug("setInitData Start");

        BaseUserModel smodel = reqMdl.getSmodel();
        List <String> plgIdList = new ArrayList<String>();
        //表示用プラグインリスト
        ArrayList <ManAdmSettingInfoModel> dspList =
                new ArrayList<ManAdmSettingInfoModel>();
        ManAdmSettingInfoModel dspModel = null;

        if (smodel != null && smodel.isAdmin()) {
            //システム管理者
            paramMdl.setMan002AdminFlg(GSConstMain.ADMKBN_ADM);
        } else {
            CmnPluginAdminDao dao = new CmnPluginAdminDao(con__);
            int usrSid = smodel.getUsrsid();
            int count = dao.getCountPluginAdmin(usrSid);

            if (count > 0) {
                //プラグイン管理者
                paramMdl.setMan002AdminFlg(GSConstMain.ADMKBN_PLG_ADM);
                plgIdList = dao.getPluginAdmin(usrSid);
            }
        }
        //plugin.xmlに記述されたものは無条件に許可
        List < Plugin > plist = pconfig.getPluginDataList();
        AdminSettingInfo asinfo = new AdminSettingInfo();

        for (Plugin plugin : plist) {
            if (paramMdl.getMan002AdminFlg() == GSConstMain.ADMKBN_PLG_ADM) {
                if (plgIdList.contains(plugin.getId())) {
                    asinfo = plugin.getAdminSettingInfo();
                } else {
                    asinfo = null;
                }
            } else {
                asinfo = plugin.getAdminSettingInfo();
            }
            if (asinfo != null) {
                //表示/非表示チェック
                if (!NullDefault.getString(asinfo.getView(), "false").equals("true")) {
                    continue;
                }
                dspModel = new ManAdmSettingInfoModel();
                dspModel.setName(plugin.getName(reqMdl));
                dspModel.setId(plugin.getId());
                dspModel.setUrl(asinfo.getUrl()
                        + "?backScreen=" + GSConstMain.BACK_MAIN_ADM_SETTING);
                if (!pidList.contains(plugin.getId())) {
                    dspModel.setIcon(__getIconPath(
                            "../common/images/pluginImg/original/menu_icon_"
                            + plugin.getId() + "_50.png"));
                } else {
                    dspModel.setIcon(__getIconPath(asinfo.getIcon()));
                }
                dspModel.setIconClassic(__getIconPath(asinfo.getIconClassic()));
                dspList.add(dspModel);
            }
        }
        paramMdl.setPluginMenuList(dspList);

        //ポータルが使用可能かを判定
        CommonBiz cmnBiz = new CommonBiz();
        if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_PORTAL, pconfig)) {
            paramMdl.setPortalUseOk(GSConstMain.PLUGIN_USE);
        } else {
            paramMdl.setPortalUseOk(GSConstMain.PLUGIN_NOT_USE);
        }

        //ショートメールが使用可能かを判定
        if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
            paramMdl.setSmlUseOk(GSConstMain.PLUGIN_USE);
        } else {
            paramMdl.setSmlUseOk(GSConstMain.PLUGIN_NOT_USE);
        }

        boolean pluginSmlUseflg = false;
        for (String key : smlMap.keySet()) {
            if (cmnBiz.isPluginAdmin(con__, reqMdl.getSmodel(), key)
                    && cmnBiz.isCanUsePlugin(key, pconfig)) {
                pluginSmlUseflg = true;
                break;
            }
        }
        if (pluginSmlUseflg) {
            paramMdl.setSmlNoticeFlg(GSConstMain.PLUGIN_USE);
        } else {
            paramMdl.setSmlNoticeFlg(GSConstMain.PLUGIN_NOT_USE);
        }
        //自動削除が使用可能かを判定
        boolean pluginAutoDelflg = false;
        for (String key : delMap.keySet()) {
            if (cmnBiz.isPluginAdmin(con__, reqMdl.getSmodel(), key)
                    && cmnBiz.isCanUsePlugin(key, pconfig)) {
                pluginAutoDelflg = true;
                break;
            }
        }
        if (pluginAutoDelflg) {
            paramMdl.setAutoDelFlg(GSConstMain.PLUGIN_USE);
        } else {
            paramMdl.setAutoDelFlg(GSConstMain.PLUGIN_NOT_USE);
        }
        //手動削除が使用可能かを判定
        boolean pluginManualDelflg = false;
        for (String key : delMap.keySet()) {
            if (key.equals(GSConst.PLUGIN_ID_MEMO)) {
                continue;
            }
            if (cmnBiz.isPluginAdmin(con__, reqMdl.getSmodel(), key)
                    && cmnBiz.isCanUsePlugin(key, pconfig)) {
                pluginManualDelflg = true;
                break;
            }
        }
        if (pluginManualDelflg) {
            paramMdl.setManualDelFlg(GSConstMain.PLUGIN_USE);
        } else {
            paramMdl.setManualDelFlg(GSConstMain.PLUGIN_NOT_USE);
        }
    }

    /**
     * <br>[機  能] アイコンのパスが存在しない場合、デフォルトのアイコンパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param icon アイコンのパス
     * @return icon アイコンのパス
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
