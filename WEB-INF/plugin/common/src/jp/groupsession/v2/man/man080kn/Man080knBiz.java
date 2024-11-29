package jp.groupsession.v2.man.man080kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnBackupConfDao;
import jp.groupsession.v2.cmn.model.base.CmnBackupConfModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] メイン 管理者設定 自動バックアップ設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man080knBiz {

    /**
     * <br>[機  能] バックアップ設定の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void insertBackupConf(Man080knParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        CmnBackupConfDao backupConfDao = new CmnBackupConfDao(con);
        backupConfDao.delete();

        UDate now = new UDate();
        CmnBackupConfModel backupMdl = new CmnBackupConfModel();

        int interval = paramMdl.getMan080Interval();
        backupMdl.setBucInterval(paramMdl.getMan080Interval());
        if (interval == GSConstMain.BUCCONF_INTERVAL_WEEKLY) {
            backupMdl.setBucDow(paramMdl.getMan080dow());
        } else if (interval == GSConstMain.BUCCONF_INTERVAL_MONTHLY) {
            backupMdl.setBucDow(paramMdl.getMan080monthdow());
            backupMdl.setBucWeekMonth(paramMdl.getMan080weekmonth());
        }

        backupMdl.setBucGeneration(paramMdl.getMan080generation());
        backupMdl.setBucZipout(paramMdl.getMan080zipOutputKbn());
        backupMdl.setBucAuid(userSid);
        backupMdl.setBucAdate(now);
        backupMdl.setBucEuid(userSid);
        backupMdl.setBucEdate(now);

        backupConfDao.insert(backupMdl);
    }

}
