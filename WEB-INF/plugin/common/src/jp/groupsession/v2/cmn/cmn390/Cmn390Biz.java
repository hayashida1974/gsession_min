package jp.groupsession.v2.cmn.cmn390;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.base.CmnFirewallConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginMobilePermissionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnFirewallConfModel;
import jp.groupsession.v2.cmn.model.base.CmnPluginMobilePermissionModel;

public class Cmn390Biz {
    /** 選択値 肯定*/
    public static final int FLG_POSITIVE = 1;
    /** 選択値 否定*/
    public static final int FLG_NEGATIVE = 0;
    /** 選択値 否定*/
    public static final int MAXLENGTH_IPADDR = 1000;

    /** リクエストモデル*/
    RequestModel reqMdl__;
    /** コネクション*/
    Connection con__;
    /**
     * コンストラクタ
     * @param reqMdl
     * @param con
     */
    public Cmn390Biz(RequestModel reqMdl, Connection con) {
        super();
        reqMdl__ = reqMdl;
        con__ = con;
    }
    /**
     *
     * <br>[機  能] 初回アクセス設定処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl
     * @throws SQLException
     */
    public void doInit(Cmn390ParamModel paramMdl) throws SQLException {
        CmnFirewallConfDao dao = new CmnFirewallConfDao(con__);
        CmnFirewallConfModel dbConf = dao.select();
        paramMdl.setCmn390ipAddrSeigenUseFlg(FLG_NEGATIVE);
        if (ValidateUtil.isEmpty(dbConf.getCfcAllowIp()) == false) {
            paramMdl.setCmn390ipAddrSeigenUseFlg(FLG_POSITIVE);
        }

        paramMdl.setCmn390arrowIpAddrText(dbConf.getCfcAllowIp());

        paramMdl.setCmn390arrowMblFlg(dbConf.getCfcAllowMbl());

        paramMdl.setCmn390arrowAnpFlg(dbConf.getCfcAllowAnp());

        paramMdl.setCmn390mobilePermissionKbn(dbConf.getCfcAllowMblPlugin());

        if (dbConf.getCfcAllowMblPlugin() == GSConstCommon.CFC_ALLOW_MBL_YES) {
            CmnPluginMobilePermissionDao pluginMobileDao = new CmnPluginMobilePermissionDao(con__);
            List<String> pluginIdList = pluginMobileDao.getAllowPluginIdList();
            paramMdl.setCmn390mobilePermissionPluginList(
                pluginIdList.toArray(new String[0])
            );
        }
    }

    /**
     *
     * <br>[機  能] 描画設定処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl
     * @throws SQLException
     */
    public void doDsp(Cmn390ParamModel paramMdl) {

    }
    /**
     *
     * <br>[機  能] 登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl
     * @throws SQLException
     */
    public void doCommit(Cmn390ParamModel paramMdl) throws SQLException {
        CmnFirewallConfDao dao = new CmnFirewallConfDao(con__);
        CmnFirewallConfModel dbConf = new CmnFirewallConfModel();
        dbConf.setCfcAllowIp("");
        if (paramMdl.getCmn390ipAddrSeigenUseFlg() == FLG_POSITIVE) {
            dbConf.setCfcAllowIp(paramMdl.getCmn390arrowIpAddrText());
            dbConf.setCfcAllowMbl(paramMdl.getCmn390arrowMblFlg());
            dbConf.setCfcAllowAnp(paramMdl.getCmn390arrowAnpFlg());
            if (dbConf.getCfcAllowMbl() == GSConstCommon.CFC_ALLOW_MBL_YES) {
                dbConf.setCfcAllowMblPlugin(paramMdl.getCmn390mobilePermissionKbn());
            } else {
                dbConf.setCfcAllowMblPlugin(GSConstCommon.CFC_ALLOW_MBL_PLUGIN_NO);
            }
        }
        boolean commitFlg = false;
        try {
            dao.update(dbConf);

            //モバイル外部アクセス許可プラグイン情報を登録する
            //※例外設定 = 使用可能なプラグインを選択する を指定した場合のみ、選択したプラグインを新規登録
            CmnPluginMobilePermissionDao pluginMobileDao = new CmnPluginMobilePermissionDao(con__);
            pluginMobileDao.deleteAll();
            if (dbConf.getCfcAllowMblPlugin() == GSConstCommon.CFC_ALLOW_MBL_PLUGIN_RESTRICT) {
                CmnPluginMobilePermissionModel pluginPermitMdl = new CmnPluginMobilePermissionModel();

                //画面で選択したプラグインを登録
                for (String pluginId : paramMdl.getCmn390mobilePermissionPluginList()) {
                    pluginPermitMdl.setPmpPlugin(pluginId);
                    pluginMobileDao.insert(pluginPermitMdl);
                }
            }

            commitFlg = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }

    }

}
