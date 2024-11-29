package jp.groupsession.v2.man.man040kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.man.man040.Man040ParamModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 バッチ処理起動時間設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man040knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man040knBiz.class);

    /**
     * <br>[機  能] バッチジョブ起動時間更新処理
     * <br>[解  説] バッチジョブ起動時間を更新します
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param sessionUserSid ユーザSID
     * @param con コネクション
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int executeBatchJobStartTime(Man040ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {
        log__.debug("更新処理開始");

        int cnt = 0;

        CmnBatchJobDao batDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batMdl = new CmnBatchJobModel();

        UDate now = new UDate();
        batMdl.setBatFrDate(Integer.parseInt(paramMdl.getMan040FrHour()));
        batMdl.setBatUpuser(sessionUserSid);
        batMdl.setBatUpdate(now);

        if (batDao.select() != null) {
            log__.debug("更新");
            cnt = batDao.update(batMdl);
        } else {
            log__.debug("新規");
            batMdl.setBatAduser(sessionUserSid);
            batMdl.setBatAddate(now);
            batDao.insert(batMdl);
        }

        log__.debug("更新処理終了");
        return cnt;
    }
}
