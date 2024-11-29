package jp.groupsession.v2.sml.dao;

import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sml.model.SmlBanDestConfModel;

/**
 * <p>SML_BAN_DEST_CONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlBanDestConfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlBanDestConfDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlBanDestConfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlBanDestConfDao(Connection con) {
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
            sql.addSql("drop table SML_BAN_DEST_CONF");

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
            sql.addSql(" create table SML_BAN_DEST_CONF (");
            sql.addSql("   SBC_SID NUMBER(10,0) not null,");
            sql.addSql("   SBC_NAME varchar(50) not null,");
            sql.addSql("   SBC_BIKO varchar(1000),");
            sql.addSql("   SBC_AUID NUMBER(10,0) not null,");
            sql.addSql("   SBC_ADATE varchar(23) not null,");
            sql.addSql("   SBC_EUID NUMBER(10,0) not null,");
            sql.addSql("   SBC_EDATE varchar(23) not null,");
            sql.addSql("   primary key (SBC_SID)");
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
     * <p>Insert SML_BAN_DEST_CONF Data Bindding JavaBean
     * @param bean SML_BAN_DEST_CONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlBanDestConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_BAN_DEST_CONF(");
            sql.addSql("   SBC_SID,");
            sql.addSql("   SBC_NAME,");
            sql.addSql("   SBC_BIKO,");
            sql.addSql("   SBC_AUID,");
            sql.addSql("   SBC_ADATE,");
            sql.addSql("   SBC_EUID,");
            sql.addSql("   SBC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSbcSid());
            sql.addStrValue(bean.getSbcName());
            sql.addStrValue(bean.getSbcBiko());
            sql.addIntValue(bean.getSbcAuid());
            sql.addDateValue(bean.getSbcAdate());
            sql.addIntValue(bean.getSbcEuid());
            sql.addDateValue(bean.getSbcEdate());
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
     * <p>Update SML_BAN_DEST_CONF Data Bindding JavaBean
     * @param bean SML_BAN_DEST_CONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlBanDestConfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_BAN_DEST_CONF");
            sql.addSql(" set ");
            sql.addSql("   SBC_NAME=?,");
            sql.addSql("   SBC_BIKO=?,");
            sql.addSql("   SBC_AUID=?,");
            sql.addSql("   SBC_ADATE=?,");
            sql.addSql("   SBC_EUID=?,");
            sql.addSql("   SBC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SBC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getSbcName());
            sql.addStrValue(bean.getSbcBiko());
            sql.addIntValue(bean.getSbcAuid());
            sql.addDateValue(bean.getSbcAdate());
            sql.addIntValue(bean.getSbcEuid());
            sql.addDateValue(bean.getSbcEdate());
            //where
            sql.addIntValue(bean.getSbcSid());

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
     * <p>Select SML_BAN_DEST_CONF All Data
     * @return List in SML_BAN_DEST_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlBanDestConfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlBanDestConfModel> ret = new ArrayList<SmlBanDestConfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SBC_SID,");
            sql.addSql("   SBC_NAME,");
            sql.addSql("   SBC_BIKO,");
            sql.addSql("   SBC_AUID,");
            sql.addSql("   SBC_ADATE,");
            sql.addSql("   SBC_EUID,");
            sql.addSql("   SBC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_BAN_DEST_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlBanDestConfFromRs(rs));
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
     * <p>Select SML_BAN_DEST_CONF
     * @param sbcSid SBC_SID
     * @return SML_BAN_DEST_CONFModel
     * @throws SQLException SQL実行例外
     */
    public SmlBanDestConfModel select(int sbcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlBanDestConfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SBC_SID,");
            sql.addSql("   SBC_NAME,");
            sql.addSql("   SBC_BIKO,");
            sql.addSql("   SBC_AUID,");
            sql.addSql("   SBC_ADATE,");
            sql.addSql("   SBC_EUID,");
            sql.addSql("   SBC_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_BAN_DEST_CONF");
            sql.addSql(" where ");
            sql.addSql("   SBC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sbcSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlBanDestConfFromRs(rs);
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
     * <p>Delete SML_BAN_DEST_CONF
     * @param sbcSid SBC_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int sbcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_BAN_DEST_CONF");
            sql.addSql(" where ");
            sql.addSql("   SBC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sbcSid);

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
     * <p>Create SML_BAN_DEST_CONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlBanDestConfModel
     * @throws SQLException SQL実行例外
     */
    private SmlBanDestConfModel __getSmlBanDestConfFromRs(ResultSet rs) throws SQLException {
        SmlBanDestConfModel bean = new SmlBanDestConfModel();
        bean.setSbcSid(rs.getInt("SBC_SID"));
        bean.setSbcName(rs.getString("SBC_NAME"));
        bean.setSbcBiko(rs.getString("SBC_BIKO"));
        bean.setSbcAuid(rs.getInt("SBC_AUID"));
        bean.setSbcAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SBC_ADATE")));
        bean.setSbcEuid(rs.getInt("SBC_EUID"));
        bean.setSbcEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SBC_EDATE")));
        return bean;
    }
}
