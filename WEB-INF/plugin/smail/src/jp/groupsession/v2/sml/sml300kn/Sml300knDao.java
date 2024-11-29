package jp.groupsession.v2.sml.sml300kn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sml.model.SmlLabelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール 管理者設定 ラベル登録確認画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml300knDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml300knDao.class);
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Sml300knDao(Connection con) {
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
    public int update(SmlLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_LABEL");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   SLB_NAME=?,");
            sql.addSql("   SLB_TYPE=?,");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getSlbName());
            sql.addIntValue(bean.getSlbType());
            sql.addIntValue(bean.getSacSid());
            //where
            sql.addIntValue(bean.getSlbSid());

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
