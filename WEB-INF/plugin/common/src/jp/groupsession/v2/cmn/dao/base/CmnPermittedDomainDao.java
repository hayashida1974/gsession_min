package jp.groupsession.v2.cmn.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnPermittedDomainModel;

/**
 * <p>CMN_PERMITTED_DOMAIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnPermittedDomainDao extends AbstractDao {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(CmnPermittedDomainDao.class);

    /**
     * <p> デフォルトコンストラクタ
     * */
    public CmnPermittedDomainDao() {
    }

    /**
     * <p> コネクションをセットします。
     * @param con コネクション
     * */
    public CmnPermittedDomainDao(Connection con) {
        super(con);
    }

    /**
     * <p> テーブルを削除します。
     * @throws SQLException SQL実行例外
     * */
    public void dropTable() throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table CMN_PERMITTED_DOMAIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p> テーブルを作成します。
     * @throws SQLException SQL実行例外
     * */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table CMN_PERMITTED_DOMAIN ");
            sql.addSql(" ( ");
            sql.addSql("  CPD_EXT_DOMAIN varchar(150),  ");
            sql.addSql("  CPD_AUID NUMBER(4,0),  ");
            sql.addSql("  CPD_ADATE varchar(8),  ");
            sql.addSql("  CPD_EUID NUMBER(4,0),  ");
            sql.addSql("  CPD_EDATE varchar(8)  ");
            sql.addSql(" ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p> CMN_PERMITTED_DOMAINの全レコードを取得します。
     * @return CMN_PERMITTED_DOMAINモデルリスト
     * @throws SQLException SQL実行例外
     * */
    public ArrayList<CmnPermittedDomainModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CmnPermittedDomainModel> ret
                        = new ArrayList<CmnPermittedDomainModel>();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("    CPD_EXT_DOMAIN,    ");
            sql.addSql("    CPD_AUID,    ");
            sql.addSql("    CPD_ADATE,    ");
            sql.addSql("    CPD_EUID,    ");
            sql.addSql("    CPD_EDATE    ");
            sql.addSql(" from ");
            sql.addSql("    CMN_PERMITTED_DOMAIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnPremittedDomeinFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }

    /**
     * <p> CMN_PERMITTED_DOMAINテーブルにレコードを追加します。
     * @param bean CMN_PERMITTED_DOMAINモデル
     * @return カウント
     * @throws SQLException SQL実行例外
     * */
    public int insert(CmnPermittedDomainModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  insert  ");
            sql.addSql("    into   ");
            sql.addSql("      CMN_PERMITTED_DOMAIN");
            sql.addSql("        (    ");
            sql.addSql("          CPD_EXT_DOMAIN,");
            sql.addSql("          CPD_AUID,");
            sql.addSql("          CPD_ADATE,");
            sql.addSql("          CPD_EUID,");
            sql.addSql("          CPD_EDATE");
            sql.addSql("        )    ");
            sql.addSql("    values   ");
            sql.addSql("        (    ");
            sql.addSql("          ?,  ");
            sql.addSql("          ?,  ");
            sql.addSql("          ?,  ");
            sql.addSql("          ?, ");
            sql.addSql("          ?");
            sql.addSql("        )    ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCpdExtDomain());
            sql.addIntValue(bean.getCpdAuid());
            sql.addDateValue(bean.getCpdAdate());
            sql.addIntValue(bean.getCpdEuid());
            sql.addDateValue(bean.getCpdEdate());

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

    /**
     * <p> CMN_PERMITTED_DOMAINテーブルの全レコードを削除します。
     * @throws SQLException SQL実行例外
     * @return カウント
     * */
    public int delete() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_PERMITTED_DOMAIN");

            pstmt = con.prepareStatement(sql.toSqlString());

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

    /**
     * <p> リザルトセットからCMN_PERMITTED_DOMAINのモデルを作成します。
     * @param rs リザルトセット
     * @return CMN_PERMITTED_DOMAINモデル
     * @throws SQLException SQL実行例外
     * */
    private CmnPermittedDomainModel
             __getCmnPremittedDomeinFromRs(ResultSet rs) throws SQLException {
        CmnPermittedDomainModel bean = new CmnPermittedDomainModel();
        bean.setCpdExtDomain(rs.getString("CPD_EXT_DOMAIN"));
        bean.setCpdAuid(rs.getInt("CPD_AUID"));
        bean.setCpdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CPD_ADATE")));
        bean.setCpdEuid(rs.getInt("CPD_EUID"));
        bean.setCpdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CPD_EDATE")));

        return bean;
    }

}
