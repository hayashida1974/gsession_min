package jp.groupsession.v2.cmn.usedsize;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.dao.base.CmnDatausedDao;
import jp.groupsession.v2.cmn.model.base.CmnDatausedModel;

/**
 * <br>[機  能] 使用データサイズ集計に関する各種機能を適用するビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class UsedSizeBiz {

    /**
     * <br>[機  能] プラグイン別の使用データサイズ合計を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param pluginId プラグインID
     * @param usedSize 使用データサイズ合計
     * @throws SQLException SQL実行時例外
     */
    public void entryPluginUsedDisk(Connection con, String pluginId, long usedSize)
    throws SQLException {
        CmnDatausedModel dataUsedMdl = new CmnDatausedModel();
        dataUsedMdl.setCduPlugin(pluginId);
        dataUsedMdl.setCduSize(usedSize);

        CmnDatausedDao dataUsedDao = new CmnDatausedDao(con);
        if (dataUsedDao.update(dataUsedMdl) == 0) {
            dataUsedDao.insert(dataUsedMdl);
        }
    }
}
