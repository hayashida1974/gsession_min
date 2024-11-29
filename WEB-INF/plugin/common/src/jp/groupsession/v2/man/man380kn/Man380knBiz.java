package jp.groupsession.v2.man.man380kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnLogDao;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainUsedDataBiz;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ手動削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man380knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man380knBiz.class);

    /**
     * <br>[機  能] オペレーションログを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteOperationLogHistory(Connection con, Man380knParamModel paramMdl)
    throws SQLException {

        log__.debug("オペレーションログ手動削除処理開始");
        int adlYear = paramMdl.getMan380Year();
        int adlMonth = paramMdl.getMan380Month();
        CmnLogDao logDao = new CmnLogDao(con);


        //削除する境界の日付を設定する
        UDate delUdate = new UDate();
        log__.debug("現在日 = " + delUdate.getDateString());
        log__.debug("削除条件 経過年 = " + adlYear);
        log__.debug("削除条件 経過年 = " + adlMonth);

        delUdate.addYear((adlYear * -1));
        delUdate.addMonth((adlMonth * -1));
        delUdate.setHour(GSConstMain.DAY_END_HOUR);
        delUdate.setMinute(GSConstMain.DAY_END_MINUTES);
        delUdate.setSecond(GSConstMain.DAY_END_SECOND);
        delUdate.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

        log__.debug("削除境界線(この日以前のデータを削除) = " + delUdate.getTimeStamp());
        int count = logDao.delete(delUdate);

        //オペレーションログのデータ使用量を登録(削除対象のデータ使用量を減算)
        MainUsedDataBiz usedDataBiz = new MainUsedDataBiz(con);
        usedDataBiz.insertOpLogDataSize(count, false);

        log__.debug("オペレーションログ" + count + "件を削除");
    }

}
