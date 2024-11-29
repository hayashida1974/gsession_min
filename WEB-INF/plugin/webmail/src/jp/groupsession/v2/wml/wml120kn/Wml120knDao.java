package jp.groupsession.v2.wml.wml120kn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール ラベル登録確認画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml120knDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml120knDao.class);
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml120knDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ラベルを更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean WmlLabelModel
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public int update(WmlLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_LABEL");
            sql.addSql(" set ");
            sql.addSql("   WLB_NAME=?,");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWlbName());
            sql.addIntValue(bean.getWacSid());
            //where
            sql.addIntValue(bean.getWlbSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
}
