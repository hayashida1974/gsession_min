package jp.groupsession.v2.sml.dao;

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
import jp.groupsession.v2.sml.model.SmlFilterModel;

/**
 * <p>SML_FILTER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlFilterDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlFilterDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlFilterDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlFilterDao(Connection con) {
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
            sql.addSql("drop table SML_FILTER");

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
            sql.addSql(" create table SML_FILTER (");
            sql.addSql("   SFT_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   SFT_NAME varchar(100) not null,");
            sql.addSql("   SFT_TYPE NUMBER(10,0) not null,");
            sql.addSql("   SAC_SID NUMBER(10,0),");
            sql.addSql("   SFT_TEMPFILE NUMBER(10,0) not null,");
            sql.addSql("   SFT_ACTION_LABEL NUMBER(10,0) not null,");
            sql.addSql("   SLB_SID NUMBER(10,0),");
            sql.addSql("   SFT_ACTION_READ NUMBER(10,0) not null,");
            sql.addSql("   SFT_ACTION_DUST NUMBER(10,0) not null,");
            sql.addSql("   SFT_CONDITION NUMBER(10,0) not null,");
            sql.addSql("   primary key (SFT_SID)");
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
     * <p>Insert SML_FILTER Data Bindding JavaBean
     * @param bean SML_FILTER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlFilterModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_FILTER(");
            sql.addSql("   SFT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SFT_NAME,");
            sql.addSql("   SFT_TYPE,");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SFT_TEMPFILE,");
            sql.addSql("   SFT_ACTION_LABEL,");
            sql.addSql("   SLB_SID,");
            sql.addSql("   SFT_ACTION_READ,");
            sql.addSql("   SFT_ACTION_DUST,");
            sql.addSql("   sFT_CONDITION");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSftSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getSftName());
            sql.addIntValue(bean.getSftType());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSftTempfile());
            sql.addIntValue(bean.getSftActionLabel());
            sql.addIntValue(bean.getSlbSid());
            sql.addIntValue(bean.getSftActionRead());
            sql.addIntValue(bean.getSftActionDust());
            sql.addIntValue(bean.getSftCondition());
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
     * <p>Update SML_FILTER Data Bindding JavaBean
     * @param bean SML_FILTER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlFilterModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_FILTER");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   SFT_NAME=?,");
            sql.addSql("   SFT_TYPE=?,");
            sql.addSql("   SAC_SID=?,");
            sql.addSql("   SFT_TEMPFILE=?,");
            sql.addSql("   SFT_ACTION_LABEL=?,");
            sql.addSql("   SLB_SID=?,");
            sql.addSql("   SFT_ACTION_READ=?,");
            sql.addSql("   SFT_ACTION_DUST=?,");
            sql.addSql("   SFT_CONDITION=?");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getSftName());
            sql.addIntValue(bean.getSftType());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSftTempfile());
            sql.addIntValue(bean.getSftActionLabel());
            sql.addIntValue(bean.getSlbSid());
            sql.addIntValue(bean.getSftActionRead());
            sql.addIntValue(bean.getSftActionDust());
            sql.addIntValue(bean.getSftCondition());
            //where
            sql.addIntValue(bean.getSftSid());

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
     * <p>Select SML_FILTER All Data
     * @return List in SML_FILTERModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlFilterModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlFilterModel> ret = new ArrayList<SmlFilterModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SFT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SFT_NAME,");
            sql.addSql("   SFT_TYPE,");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SFT_TEMPFILE,");
            sql.addSql("   SFT_ACTION_LABEL,");
            sql.addSql("   SLB_SID,");
            sql.addSql("   SFT_ACTION_READ,");
            sql.addSql("   SFT_ACTION_DUST,");
            sql.addSql("   SFT_CONDITION");
            sql.addSql(" from ");
            sql.addSql("   SML_FILTER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlFilterFromRs(rs));
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
     * <p>Select SML_FILTER
     * @param sftSid SFT_SID
     * @return SML_FILTERModel
     * @throws SQLException SQL実行例外
     */
    public SmlFilterModel select(int sftSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlFilterModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SFT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SFT_NAME,");
            sql.addSql("   SFT_TYPE,");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SFT_TEMPFILE,");
            sql.addSql("   SFT_ACTION_LABEL,");
            sql.addSql("   SLB_SID,");
            sql.addSql("   SFT_ACTION_READ,");
            sql.addSql("   SFT_ACTION_DUST,");
            sql.addSql("   SFT_CONDITION");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sftSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlFilterFromRs(rs);
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
     * <br>[機  能] 指定したユーザが作成したフィルターの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param sftType フィルター種別
     * @return フィルターの一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getFilterList(int usrSid, int sftType) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SFT_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_FILTER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   SFT_TYPE = ?");

            sql.addIntValue(usrSid);
            sql.addIntValue(sftType);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("SFT_SID"));
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
     * <p>Delete SML_FILTER
     * @param sftSid SFT_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int sftSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_FILTER");
            sql.addSql(" where ");
            sql.addSql("   SFT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sftSid);

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
     * <p>Create SML_FILTER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlFilterModel
     * @throws SQLException SQL実行例外
     */
    private SmlFilterModel __getSmlFilterFromRs(ResultSet rs) throws SQLException {
        SmlFilterModel bean = new SmlFilterModel();
        bean.setSftSid(rs.getInt("SFT_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSftName(rs.getString("SFT_NAME"));
        bean.setSftType(rs.getInt("SFT_TYPE"));
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSftTempfile(rs.getInt("SFT_TEMPFILE"));
        bean.setSftActionLabel(rs.getInt("SFT_ACTION_LABEL"));
        bean.setSlbSid(rs.getInt("SLB_SID"));
        bean.setSftActionRead(rs.getInt("SFT_ACTION_READ"));
        bean.setSftActionDust(rs.getInt("SFT_ACTION_DUST"));
        bean.setSftCondition(rs.getInt("SFT_CONDITION"));
        return bean;
    }
}
