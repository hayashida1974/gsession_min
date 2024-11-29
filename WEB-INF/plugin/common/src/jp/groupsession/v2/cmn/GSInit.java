package jp.groupsession.v2.cmn;

import java.rmi.server.UID;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.Blowfish;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.background.BatchMasterThread;
import jp.groupsession.v2.cmn.background.IGsBatch;
import jp.groupsession.v2.cmn.config.LogInfo;
import jp.groupsession.v2.cmn.config.LoggingConfig;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.config.TopMenuInfo;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoSendDateDao;
import jp.groupsession.v2.cmn.dao.base.CmnLogConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrPluginDao;
import jp.groupsession.v2.cmn.model.base.CmnLogConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrPluginModel;
import jp.groupsession.v2.cmn.quartz.JobException;
import jp.groupsession.v2.convert.ConvertGsListenerImpl;
import jp.groupsession.v2.lic.GSConstLicese;
import jp.groupsession.v2.lic.LicenseOperation;

/**
 * <br>[機 能] GroupSession起動処理
 * <br>[解 説]
 * <br>[備 考]
 *
 * @author JTS
 */
public class GSInit implements IGsBatch {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(GSInit.class);

    /**
     * <p>Jobの実行
     * @param pluginConfig プラグイン情報
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     */
    public void execute(PluginConfig pluginConfig) throws JobException, SQLException {
        log__.debug("START");
        BatchMasterThread bachMaster = new BatchMasterThread(this, pluginConfig);
        bachMaster.run();
        log__.debug("END");
    }

    /**
     * <p>起動処理の実行
     * @param dsKey ドメイン
     * @param pConfig プラグイン情報(マスタ情報)
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     */
    public void executeBatch(String dsKey, PluginConfig pConfig)
                               throws JobException, SQLException {

        //ドメイン毎のプラグイン情報を作成
        PluginConfig pluginConfig = pConfig.clonePluginConfig(null);

        Connection con = null;
        try {

            //GSリスナー取得
            log__.info("起動リスナー実行開始 init()");

            //バージョンアップ時のデータコンバートを行う
            try {
                con = GroupSession.getConnection(dsKey);
                ConvertGsListenerImpl convertListener = new ConvertGsListenerImpl();
                convertListener.gsInit(GroupSession.getContext(), con, dsKey);
            } catch (Throwable e) {
                log__.error("バージョンアップ時のデータコンバートに失敗", e);
            } finally {
                JDBCUtil.closeConnection(con);
                con = null;
            }

            IGsListener[] lis = GsListenerUtil.getGsListeners(pluginConfig);
            log__.debug("起動するリスナー数 = " + lis.length);
            //各プラグインリスナーを呼び出し
            for (int i = 0; i < lis.length; i++) {
                try {
                    con = GroupSession.getConnection(dsKey);
                    lis[i].gsInit(GroupSession.getContext(), con, dsKey);
                } catch (Throwable e) {
                    log__.error("起動リスナーの実行に失敗", e);
                } finally {
                    JDBCUtil.closeConnection(con);
                }
            }
            log__.info("起動リスナー実行完了 init()");
        } catch (ClassNotFoundException e) {
            log__.error("リスナー実行に失敗しました。", e);
        } catch (IllegalAccessException e) {
            log__.error("リスナー実行に失敗しました。", e);
        } catch (InstantiationException e) {
            log__.error("リスナー実行に失敗しました。", e);
        } catch (Exception e) {
            log__.error("リスナー実行に失敗しました。", e);
        } finally {
            JDBCUtil.closeConnection(con);
        }

        try {
            con = GroupSession.getConnection(dsKey);
            con.setAutoCommit(false);
            //ログ設定を取得
            ArrayList<CmnLogConfModel> confList = __getLoggingConf(pluginConfig, con);
            LoggingConfig loggingConfig = new LoggingConfig();
            for (CmnLogConfModel conf : confList) {
                log__.info("loggingConfig__==>" + conf.getLgcPlugin());
                loggingConfig.addLogConf(conf);
            }
            //GSコンテキストへ設定
            GroupSession.getResourceManager().setLoggingConfig(dsKey, loggingConfig);
            con.commit();
        } catch (Exception e) {
            log__.error("起動リスナーの実行に失敗", e);
        } finally {
            JDBCUtil.closeConnection(con);
        }

        try {
            con = GroupSession.getConnection(dsKey);
            con.setAutoCommit(false);
            //GS UID設定
            __setGsUid(con);
            con.commit();
        } catch (Exception e) {
            log__.error("GSUIDの登録に失敗", e);
        } finally {
            JDBCUtil.closeConnection(con);
        }

        //ユーザが定義したプラグインを設定
        try {
            con = GroupSession.getConnection(dsKey);
            con.setAutoCommit(false);
            ArrayList<CmnUsrPluginModel> usrPlgList = new ArrayList<CmnUsrPluginModel>();
            CmnUsrPluginDao cmnUsrPlgDao = new CmnUsrPluginDao(con);
            try {
                usrPlgList = cmnUsrPlgDao.select();
                if (!usrPlgList.isEmpty()) {
                    for (CmnUsrPluginModel mdl : usrPlgList) {
                        pluginConfig.addPlugin(__setPlugin(mdl));
                    }
                }

                //ドメイン毎のプラグインコンフィグを設定
                GroupSession.getResourceManager().setPluginConfig(dsKey, pluginConfig);

            } catch (SQLException e1) {
                log__.error("ユーザプラグインの取得に失敗しました。");
            } catch (Exception e) {
                log__.error("ユーザプラグインの取得に失敗しました。");
            }
        } catch (Exception e) {
            log__.error("ユーザプラグインの取得に失敗しました。", e);
        } finally {
            JDBCUtil.closeConnection(con);
        }
        
        try {
            con = GroupSession.getConnection(dsKey);
            con.setAutoCommit(false);
            //GS UID設定
            CmnInfoSendDateDao csdDao = new CmnInfoSendDateDao(con);
            UDate sendDate = csdDao.getSendDate();
            if (sendDate == null) {
                CmnContmDao ccmDao = new CmnContmDao(con);
                String gsUid = LicenseOperation.getDecryString(ccmDao.getGsUid());
                
                String[] uidAry = gsUid.split(":");
                String uidFirst = uidAry[0].replace("SN", "");
                String uidSecond = uidAry[1];
                
                long uidFirstDec = Long.parseLong(uidFirst, 16);
                if (uidFirstDec < 0) {
                    uidFirstDec = -uidFirstDec;
                }
                long uidSecondDec = Long.parseLong(uidSecond, 16);
                if (uidSecondDec < 0) {
                    uidSecondDec = -uidSecondDec;
                }
                
                int addHour = (int) (uidFirstDec % 24);
                int addMinute = (int) (uidSecondDec % 60);
                
                UDate date = new UDate();
                date.addMonth(1);
                date.addHour(addHour);
                date.addMinute(addMinute);
                csdDao.insert(date);
            }
            con.commit();
        } catch (Exception e) {
            log__.error("GS利用状況送信日時の登録に失敗", e);
        } finally {
            JDBCUtil.closeConnection(con);
        }
    }

    /**
     * オペレーションログ設定を取得する、存在しない場合は初期値で登録する
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException 暗号化に失敗時例外
     */
    private void __setGsUid(Connection con)
    throws SQLException, EncryptionException {

        CmnContmDao contDao = new CmnContmDao(con);
        String gsuid = contDao.getGsUid();
        if (gsuid == null) {
            UID uid = new UID();
            String gsId = GSConst.GSUID.concat(uid.toString());
            String enStr = null;
            try {
                byte[] enpt = Blowfish.encrypt(GSConstLicese.LICENSE_PHRASE, gsId);
                enStr = new String(Base64.encodeBase64(enpt));
                contDao.updateGsUid(enStr);
            } catch (SQLException e) {
                log__.error("SQL実行に失敗", e);
                throw new SQLException("UIDの登録に失敗", e);
            } catch (Exception e) {
                log__.error("暗号化に失敗", e);
                throw new EncryptionException("暗号化に失敗", e);
            }
        }
    }
    /**
     * <br>[機  能] プラグイン情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl CmnUsrPluginModel
     * @return plugin プラグインモデル
     * @throws Exception 実行例外
     */
    private Plugin __setPlugin(CmnUsrPluginModel mdl)
    throws Exception {
        Plugin pg = new Plugin();
        pg.setId(mdl.getCupPid());
        pg.setName(mdl.getCupName());
        pg.setPluginKbn(GSConst.PLUGIN_KBN_USER);
        TopMenuInfo topMenuInfo = new TopMenuInfo();
        if (mdl.getCupView() == 0) {
            topMenuInfo.setView("true");
        } else {
            topMenuInfo.setView("false");
        }
        if (mdl.getCupTarget() == 0) {
            topMenuInfo.setTarget("_blank");
        } else {
            topMenuInfo.setTarget("body");
        }
        topMenuInfo.setUrl(mdl.getCupUrl());
        topMenuInfo.setBinSid(mdl.getBinSid());

        topMenuInfo.setParamKbn(mdl.getCupParamKbn());
        topMenuInfo.setSendKbn(mdl.getCupSendKbn());

        pg.setTopMenuInfo(topMenuInfo);
        return pg;
    }
    /**
     * オペレーションログ設定を取得する、存在しない場合は初期値で登録する
     * @param pluginConfig プラグイン設定一式
     * @param con コネクション
     * @return オペレーションログ設定一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<CmnLogConfModel> __getLoggingConf(PluginConfig pluginConfig, Connection con)
    throws SQLException {

        CmnLogConfDao confDao = new CmnLogConfDao(con);
        HashMap<String, CmnLogConfModel> confMap = confDao.selectMap();
        CmnLogConfModel bean = null;

        //プラグインID一覧
        List<String> idList = pluginConfig.getPluginIdList();
        ArrayList<String> logList = new ArrayList<String>();
        Plugin plugin = null;
        LogInfo loginfo = null;
        for (String key : idList) {
            plugin = pluginConfig.getPlugin(key);
            loginfo = plugin.getLogInfo();
            //出力対象のプラグイン
            if (loginfo.isOut()) {
                logList.add(key);
            }
        }
        //Log出力対象以外の設定を削除
        confDao.delete(logList);

//      プラグイン一覧とオペレーションログ設定の整合性チェック
        for (String key : logList) {
            //設定が存在しないプラグインは初期値で登録
            if (!confMap.containsKey(key)) {
                bean = new CmnLogConfModel(key);
                confDao.insert(bean);
            }
        }

        return confDao.select();
    }
}
