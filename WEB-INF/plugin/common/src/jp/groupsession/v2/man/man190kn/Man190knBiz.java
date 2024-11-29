package jp.groupsession.v2.man.man190kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnLoginHistoryDao;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainUsedDataBiz;

/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴手動削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man190knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man190knBiz.class);
    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Man190knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] ログイン履歴を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteLoginHistory(Man190knParamModel paramMdl) throws SQLException {

        int adlYear = paramMdl.getMan190Year();
        int adlMonth = paramMdl.getMan190Month();

        //削除する境界の日付を設定する
        UDate delUd = new UDate();
        log__.debug("現在日 = " + delUd.getDateString());
        log__.debug("削除条件 経過年 = " + adlYear);
        log__.debug("削除条件 経過年 = " + adlMonth);

        delUd.addYear((adlYear * -1));
        delUd.addMonth((adlMonth * -1));
        delUd.setHour(GSConstMain.DAY_END_HOUR);
        delUd.setMinute(GSConstMain.DAY_END_MINUTES);
        delUd.setSecond(GSConstMain.DAY_END_SECOND);
        delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

        log__.debug("削除境界線(この日以前のデータを削除) = " + delUd.getTimeStamp());

        //ログイン履歴のデータ使用量を登録(削除対象のデータサイズを減算)
        MainUsedDataBiz usedDataBiz = new MainUsedDataBiz(con__);
        usedDataBiz.insertLoginHistoryDataSize(delUd, false);

        //ログイン履歴を削除する
        CmnLoginHistoryDao historyDao = new CmnLoginHistoryDao(con__);
        int count = historyDao.delete(delUd);

        log__.debug("ログイン履歴" + count + "件を削除");
    }
}