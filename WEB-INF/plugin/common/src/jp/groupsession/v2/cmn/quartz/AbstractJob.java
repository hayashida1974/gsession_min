package jp.groupsession.v2.cmn.quartz;

import java.sql.SQLException;

import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.config.PluginConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * <br>[機  能] コンテナからQuartzによってバッチ処理を行う場合に継承する抽象クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractJob implements Job {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(AbstractJob.class);
    /** WAIT時間(5秒) */
    private static final long WAIT_MILLISECOND__ = 5000;
    /** タイムアウト時間(3分) */
    private static final long TIMEOUT_MILLISECOND__ = 180000;

    /** GS共通情報 */
    private GSContext gscontext__ = null;
    /** プラグイン情報 */
    private PluginConfig pluginConfig__ = null;

    /** バックアップ処理フラグ バックアップ中はtrueが設定される */
    private static boolean backup__ = false;

    static {
        backup__ = false;
    }

    /**
     * <p>コンストラクタ
     */
    public AbstractJob() {
        super();
        log__.info("コンストラクタ");
    }

    /**
     * <p>Jobの実行
     * <p>Exception発生時には次の実行までリトライしません
     * @param context Job context
     * @exception JobExecutionException エラー発生時にスロー
     */
    public final void execute(JobExecutionContext context)
            throws JobExecutionException {
        log__.info("ジョブ実行");

        try {
            long timeout = System.currentTimeMillis() + TIMEOUT_MILLISECOND__;
            while (isBackup()) {
                if (timeout < System.currentTimeMillis()) {
                    log__.info("ジョブ タイムアウト");
                    return;
                }

                Thread.sleep(WAIT_MILLISECOND__);
            }
        } catch (Exception e) {
            log__.error("ジョブ実行に失敗", e);
            return;
        }

        //GSCONTEXT
        Object tmpCt = context.get("GSCONTEXT");
        if (tmpCt instanceof jp.groupsession.v2.cmn.GSContext) {
            gscontext__ = (jp.groupsession.v2.cmn.GSContext) tmpCt;
        } else {
            log__.debug("tmpCt = " + tmpCt);
        }
        //PluginConfig
        Object tmpbtl = context.get("BTLISTENER");
        pluginConfig__ = (jp.groupsession.v2.cmn.config.PluginConfig) tmpbtl;

//        Connection con = null;
//        try {
//            log__.debug("クラス" + this.getClass().getName() + "がConnectionを取得します。");
//            DataSource ds = (DataSource) gscontext__.get(GSContext.GSDATASOURCE);
//            con = JDBCUtil.getConnection(ds);
//            log__.debug("コネクション取得完了");
//
//            execute(context, con, pluginConfig__);
//        } catch (SQLException e) {
//            throw new JobExecutionException("コネクションの取得に失敗", e, false);
//        } catch (Exception e) {
//            log__.error("バッチ処理で例外発生", e);
//        } catch (Throwable e) {
//            log__.error("バッチ処理でエラー発生", e);
//        } finally {
//            JDBCUtil.closeConnection(con);
//        }


        try {
            execute(pluginConfig__);
        } catch (SQLException e) {
            throw new JobExecutionException("コネクションの取得に失敗", e, false);
        } catch (Exception e) {
            log__.error("バッチ処理で例外発生", e);
        } catch (Throwable e) {
            log__.error("バッチ処理でエラー発生", e);
        }
    }

    /**
     * <p>Jobの実行
     * @param pluginConfig プラグイン情報
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     */
    public abstract void execute(PluginConfig pluginConfig)
    throws JobException, SQLException;

    /**
     * @return gscontext を戻します。
     */
    public GSContext getGscontext() {
        return gscontext__;
    }

    /**
     * @param gscontext 設定する gscontext。
     */
    public void setGscontext(GSContext gscontext) {
        gscontext__ = gscontext;
    }

    /**
     * @return pluginConfig を戻します。
     */
    public PluginConfig getPluginConfig() {
        return pluginConfig__;
    }

    /**
     * @param pluginConfig 設定する pluginConfig。
     */
    public void setPluginConfig(PluginConfig pluginConfig) {
        pluginConfig__ = pluginConfig;
    }

    /**
     * <p>backup を取得します。
     * @return backup
     */
    public static boolean isBackup() {
        return backup__;
    }

    /**
     * <p>backup をセットします。
     * @param backup backup
     */
    public static void setBackup(boolean backup) {
        backup__ = backup;
    }
}
