package jp.groupsession.v2.batch;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.background.BatchMasterThread;
import jp.groupsession.v2.cmn.background.IGsBatch;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.quartz.AbstractJob;
import jp.groupsession.v2.cmn.quartz.JobException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 1時間間隔で実行されるJOB
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class HourJob extends AbstractJob implements IGsBatch {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(HourJob.class);

    /**
     * <p>Jobの実行
     * @param pluginConfig プラグイン情報
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     */
    public void execute(PluginConfig pluginConfig)
                               throws JobException, SQLException {
        log__.debug("START HourJob");
        BatchMasterThread bachMaster = new BatchMasterThread(this, pluginConfig);
        bachMaster.run();
        log__.debug("END HourJob");
    }

    /**
     * <p>Jobの実行
     * @param dsKey ドメイン
     * @param pluginConfig プラグイン情報
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     */
    public void executeBatch(String dsKey, PluginConfig pluginConfig)
                               throws JobException, SQLException {

        String listenerName = "";
        Connection con = null;
        try {
            con = GroupSession.getConnection(dsKey);
            if (con != null) {
                //バッチを実行
                GSContext gscontext = getGscontext();
                CommonBiz cmnBiz = new CommonBiz();
                IBatchListener[] batchListeners = cmnBiz.getBatchListeners(pluginConfig, con);
                IBatchModel ibm = new IBatchModel();
                ibm.setGsContext(gscontext);
                ibm.setDomain(dsKey);
                for (IBatchListener listener : batchListeners) {
                    log__.debug("IBatchListener = " + listener.getClass().getCanonicalName());
                    listenerName = listener.getClass().getCanonicalName();
                    listener.doOneHourBatch(con, ibm);
                }
            }
        } catch (ClassNotFoundException e) {
            log__.error("", e);
        } catch (IllegalAccessException e) {
            log__.error("", e);
        } catch (InstantiationException e) {
            log__.error("", e);
        } catch (Exception e) {
            log__.error("1時間間隔バッチ処理の実行に失敗:" + listenerName, e);
            throw new JobException("1時間間隔バッチ処理の実行に失敗:" + listenerName, e);
        } catch (Throwable e) {
            log__.error("", e);
        } finally {
            JDBCUtil.closeConnection(con);
        }
    }
}
