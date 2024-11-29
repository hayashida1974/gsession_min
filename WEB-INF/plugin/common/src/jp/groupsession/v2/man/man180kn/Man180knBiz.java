package jp.groupsession.v2.man.man180kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnLhisBatchConfDao;
import jp.groupsession.v2.cmn.model.base.CmnLhisBatchConfModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴自動削除設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man180knBiz {

    /**
     * <br>[機  能] ログイン履歴削除設定を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void updateLhisBatchConf(Connection con, Man180knParamModel paramMdl, int userSid)
        throws SQLException {

        CmnLhisBatchConfModel param = new CmnLhisBatchConfModel();
        param.setCbcAdlKbn(paramMdl.getMan180BatchKbn());

        int year = 0;
        int month = 0;
        if (paramMdl.getMan180BatchKbn() == GSConstMain.LHIS_DELKBN_ON) {
            year = paramMdl.getMan180Year();
            month = paramMdl.getMan180Month();
        }
        param.setCbcAdlYear(year);
        param.setCbcAdlMonth(month);

        UDate now = new UDate();
        param.setCbcAuid(userSid);
        param.setCbcAdate(now);
        param.setCbcEuid(userSid);
        param.setCbcEdate(now);

        CmnLhisBatchConfDao batchDao = new CmnLhisBatchConfDao(con);
        if (batchDao.update(param) < 1) {
            batchDao.insert(param);
        }
    }
}