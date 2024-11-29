package jp.groupsession.v2.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bbs.model.BbsBinModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BBS_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsBinDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsBinDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table BBS_BIN");

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
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table BBS_BIN (");
            sql.addSql("   BWI_SID NUMBER(10,0) not null,");
            sql.addSql("   BIN_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (BWI_SID,BIN_SID)");
            sql.addSql(" )");

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
     * <p>Insert BBS_BIN Data Bindding JavaBean
     * @param bean BBS_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_BIN(");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBwiSid());
            sql.addLongValue(bean.getBinSid());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>投稿添付情報の一括登録を行う
     * @param bwiSid 投稿SID
     * @param binSidList バイナリSIDの一覧
     * @throws SQLException SQL実行例外
     */
    public void insertBbsBinData(int bwiSid, List < String > binSidList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into ");
            sql.addSql(" BBS_BIN (");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values (");
            sql.addSql("   " + bwiSid + ",");
            sql.addSql("   ?");
            sql.addSql(" )");

            String logString = sql.toLogString();
            pstmt = con.prepareStatement(sql.toSqlString());

            for (String binSid : binSidList) {
                log__.info(StringUtil.substitute(logString, "?", binSid));
                pstmt.setLong(1, Long.parseLong(binSid));
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update BBS_BIN Data Bindding JavaBean
     * @param bean BBS_BIN Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(BbsBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getBwiSid());
            sql.addLongValue(bean.getBinSid());

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
     * <p>Select BBS_BIN All Data
     * @return List in BBS_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsBinModel> ret = new ArrayList<BbsBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   BBS_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsBinFromRs(rs));
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
     * <p>Select BBS_BIN
     * @param bean BBS_BIN Model
     * @return BBS_BINModel
     * @throws SQLException SQL実行例外
     */
    public BbsBinModel select(BbsBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BWI_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_BIN");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBwiSid());
            sql.addLongValue(bean.getBinSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsBinFromRs(rs);
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
     * <p>Delete BBS_BIN
     * @param bwiSid 投稿SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_BIN");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bwiSid);

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
     * <p>指定した投稿の投稿添付情報を全て削除する
     * @param bwiSid 投稿SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteWriteBin(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_BIN");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bwiSid);

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
     * <p>投稿添付情報の一括削除を行う
     * @param bwiSid 投稿SID
     * @throws SQLException SQL実行例外
     */
    public void deleteBbsBin(int bwiSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_BIN");
            sql.addSql(" where ");
            sql.addSql("   BWI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bwiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

    }

    /**
     * <br>[機  能] 指定したスレッドに添付ファイルが設定されているかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return true: 添付ファイルあり、 false: 添付ファイルなし
     * @throws SQLException SQL実行時例外
     */
    public boolean existTempFile(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select 1 from");
            sql.addSql("   BBS_BIN,");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where");
            sql.addSql("   BBS_WRITE_INF.BTI_SID = ?");
            sql.addSql(" and");
            sql.addSql("   BBS_WRITE_INF.BWI_SID = BBS_BIN.BWI_SID");
            sql.addIntValue(btiSid);
            sql.setPagingValue(0, 1);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <p>Create BBS_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsBinModel
     * @throws SQLException SQL実行例外
     */
    private BbsBinModel __getBbsBinFromRs(ResultSet rs) throws SQLException {
        BbsBinModel bean = new BbsBinModel();
        bean.setBwiSid(rs.getInt("BWI_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }
}
