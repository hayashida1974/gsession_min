package jp.groupsession.v2.man.dao.base;


import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.man.model.CmnImpTimeModel;

/**
 * <p>CMN_IMP_TIME Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnImpTimeDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnImpTimeDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnImpTimeDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnImpTimeDao(Connection con) {
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
            sql.addSql("drop table CMN_IMP_TIME");

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
            sql.addSql(" create table CMN_IMP_TIME (");
            sql.addSql("   CIT_USR_FLG NUMBER(10,0) not null,");
            sql.addSql("   CIT_USR_TIME_FLG NUMBER(10,0) not null,");
            sql.addSql("   CIT_USR_TIME varchar(23) not null,");
            sql.addSql("   CIT_GRP_FLG NUMBER(10,0) not null,");
            sql.addSql("   CIT_GRP_TIME_FLG NUMBER(10,0) not null,");
            sql.addSql("   CIT_GRP_TIME varchar(23) not null,");
            sql.addSql("   CIT_BEG_FLG NUMBER(10,0) not null,");
            sql.addSql("   CIT_BEG_TIME_FLG NUMBER(10,0) not null,");
            sql.addSql("   CIT_BEG_TIME varchar(23) not null");
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
     * <p>Insert CMN_IMP_TIME Data Bindding JavaBean
     * @param bean CMN_IMP_TIME Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnImpTimeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_IMP_TIME(");
            sql.addSql("   CIT_USR_FLG,");
            sql.addSql("   CIT_USR_TIME_FLG,");
            sql.addSql("   CIT_USR_TIME,");
            sql.addSql("   CIT_GRP_FLG,");
            sql.addSql("   CIT_GRP_TIME_FLG,");
            sql.addSql("   CIT_GRP_TIME,");
            sql.addSql("   CIT_BEG_FLG,");
            sql.addSql("   CIT_BEG_TIME_FLG,");
            sql.addSql("   CIT_BEG_TIME");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCitUsrFlg());
            sql.addIntValue(bean.getCitUsrTimeFlg());
            sql.addDateValue(bean.getCitUsrTime());
            sql.addIntValue(bean.getCitGrpFlg());
            sql.addIntValue(bean.getCitGrpTimeFlg());
            sql.addDateValue(bean.getCitGrpTime());
            sql.addIntValue(bean.getCitBegFlg());
            sql.addIntValue(bean.getCitBegTimeFlg());
            sql.addDateValue(bean.getCitBegTime());
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
     * <p>Update CMN_IMP_TIME Data Bindding JavaBean
     * @param bean CMN_IMP_TIME Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnImpTimeModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_IMP_TIME");
            sql.addSql(" set ");
            sql.addSql("   CIT_USR_FLG=?,");
            sql.addSql("   CIT_USR_TIME_FLG=?,");
            sql.addSql("   CIT_USR_TIME=?,");
            sql.addSql("   CIT_GRP_FLG=?,");
            sql.addSql("   CIT_GRP_TIME_FLG=?,");
            sql.addSql("   CIT_GRP_TIME=?,");
            sql.addSql("   CIT_BEG_FLG=?,");
            sql.addSql("   CIT_BEG_TIME_FLG=?,");
            sql.addSql("   CIT_BEG_TIME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCitUsrFlg());
            sql.addIntValue(bean.getCitUsrTimeFlg());
            sql.addDateValue(bean.getCitUsrTime());
            sql.addIntValue(bean.getCitGrpFlg());
            sql.addIntValue(bean.getCitGrpTimeFlg());
            sql.addDateValue(bean.getCitGrpTime());
            sql.addIntValue(bean.getCitBegFlg());
            sql.addIntValue(bean.getCitBegTimeFlg());
            sql.addDateValue(bean.getCitBegTime());

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
     * <p>Select CMN_IMP_TIME All Data
     * @return List in CMN_IMP_TIMEModel
     * @throws SQLException SQL実行例外
     */
    public CmnImpTimeModel select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        CmnImpTimeModel citMdl = null;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIT_USR_FLG,");
            sql.addSql("   CIT_USR_TIME_FLG,");
            sql.addSql("   CIT_USR_TIME,");
            sql.addSql("   CIT_GRP_FLG,");
            sql.addSql("   CIT_GRP_TIME_FLG,");
            sql.addSql("   CIT_GRP_TIME,");
            sql.addSql("   CIT_BEG_FLG,");
            sql.addSql("   CIT_BEG_TIME_FLG,");
            sql.addSql("   CIT_BEG_TIME");
            sql.addSql(" from ");
            sql.addSql("   CMN_IMP_TIME");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                citMdl = __getCmnImpTimeFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return citMdl;
    }

    /**
     * <p>Create CMN_IMP_TIME Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnImpTimeModel
     * @throws SQLException SQL実行例外
     */
    private CmnImpTimeModel __getCmnImpTimeFromRs(ResultSet rs) throws SQLException {
        CmnImpTimeModel bean = new CmnImpTimeModel();
        bean.setCitUsrFlg(rs.getInt("CIT_USR_FLG"));
        bean.setCitUsrTimeFlg(rs.getInt("CIT_USR_TIME_FLG"));
        bean.setCitUsrTime(UDate.getInstanceTimestamp(rs.getTimestamp("CIT_USR_TIME")));
        bean.setCitGrpFlg(rs.getInt("CIT_GRP_FLG"));
        bean.setCitGrpTimeFlg(rs.getInt("CIT_GRP_TIME_FLG"));
        bean.setCitGrpTime(UDate.getInstanceTimestamp(rs.getTimestamp("CIT_GRP_TIME")));
        bean.setCitBegFlg(rs.getInt("CIT_BEG_FLG"));
        bean.setCitBegTimeFlg(rs.getInt("CIT_BEG_TIME_FLG"));
        bean.setCitBegTime(UDate.getInstanceTimestamp(rs.getTimestamp("CIT_BEG_TIME")));
        return bean;
    }
}
