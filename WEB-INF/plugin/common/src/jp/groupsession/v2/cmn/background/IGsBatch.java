package jp.groupsession.v2.cmn.background;

import java.sql.SQLException;

import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.quartz.JobException;

/**
 * <br>[機  能] バッチ実行に関するインターフェース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IGsBatch {

    /**
     * <p>バッチ実行
     * @param pluginConfig プラグイン情報
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     */
    public void execute(PluginConfig pluginConfig) throws JobException, SQLException;

    /**
     * <p>Jobの実行
     * @param dsKey ドメイン
     * @param pluginConfig プラグイン情報
     * @throws JobException 例外
     * @throws SQLException SQL実行時例外
     */
    public void executeBatch(String dsKey, PluginConfig pluginConfig)
                                                   throws JobException, SQLException;
}
