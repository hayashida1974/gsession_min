package jp.groupsession.v2.man.man130kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 添メイン 管理者設定 添付ファイル設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man130knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man130knBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Man130knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 添付ファイル設定の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void update(Man130knParamModel paramMdl, int usid) throws SQLException {

        UDate now = new UDate();
        boolean commitFlg = false;

        //添付ファイル設定の更新を行う
        CmnFileConfModel cfcMdl = new CmnFileConfModel();
        cfcMdl.setFicMaxSize(paramMdl.getMan130maxSize());
        cfcMdl.setFicEuid(usid);
        cfcMdl.setFicEdate(now);
        cfcMdl.setFicPhotoSize(paramMdl.getMan130PhotoSize());

        try {
            con__.setAutoCommit(false);

            CmnFileConfDao cfcDao = new CmnFileConfDao(con__);
            cfcDao.update(cfcMdl);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

}
