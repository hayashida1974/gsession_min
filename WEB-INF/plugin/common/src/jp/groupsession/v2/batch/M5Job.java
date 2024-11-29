package jp.groupsession.v2.batch;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSDBConCntLogger;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.background.BatchMasterThread;
import jp.groupsession.v2.cmn.background.IGsBatch;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.quartz.AbstractJob;
import jp.groupsession.v2.cmn.quartz.JobException;

/**
 * <br>[機  能] 5分間隔で実行されるJOB
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class M5Job extends AbstractJob implements IGsBatch {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(M5Job.class);

    /**
     * <p>Jobの実行
     * @param pluginConfig プラグイン情報
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     */
    public void execute(PluginConfig pluginConfig)
                               throws JobException, SQLException {
        log__.debug("START M5Job");
        BatchMasterThread bachMaster = new BatchMasterThread(this, pluginConfig);
        bachMaster.run();
        log__.debug("END M5Job");
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

        Connection con = null;
        String listenerName = "";

        //バッチを実行
        GSContext gscontext = getGscontext();
        try {
            con = GroupSession.getConnection(dsKey);
            CommonBiz cmnBiz = new CommonBiz();
            IBatchListener[] batchListeners = cmnBiz.getBatchListeners(pluginConfig, con);
            for (IBatchListener listener : batchListeners) {
                log__.debug("IBatchListener = " + listener.getClass().getCanonicalName());
                listenerName = listener.getClass().getCanonicalName();
                IBatchModel ibm = new IBatchModel();
                ibm.setGsContext(gscontext);
                ibm.setDomain(dsKey);
                listener.do5mBatch(con, ibm);
            }
        } catch (ClassNotFoundException e) {
            log__.error("", e);
        } catch (IllegalAccessException e) {
            log__.error("", e);
        } catch (InstantiationException e) {
            log__.error("", e);
        } catch (Exception e) {
            log__.error("5分間隔バッチ処理の実行に失敗:" + listenerName, e);
            throw new JobException("5分間隔バッチ処理の実行に失敗:" + listenerName, e);
        } catch (Throwable e) {
            log__.error("", e);
        } finally {
            JDBCUtil.closeConnection(con);
            //5分バッチの終了後、DBコネクション状態をログに残す
            GSDBConCntLogger.outputCntCon(dsKey);
        }
    }
}
