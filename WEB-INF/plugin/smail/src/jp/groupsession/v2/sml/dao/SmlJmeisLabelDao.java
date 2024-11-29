package jp.groupsession.v2.sml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sml.model.SmlJmeisLabelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>SML_JMEIS_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlJmeisLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlJmeisLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlJmeisLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlJmeisLabelDao(Connection con) {
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
            sql.addSql("drop table SML_JMEIS_LABEL");

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
            sql.addSql(" create table SML_JMEIS_LABEL (");
            sql.addSql("   SMJ_SID NUMBER(10,0) not null,");
            sql.addSql("   SLB_SID NUMBER(10,0) not null,");
            sql.addSql("   SAC_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (SMJ_SID,SLB_SID)");
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
     * <p>Insert WML_LABEL_RELATION Data Bindding JavaBean
     * @param bean WML_LABEL_RELATION Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlJmeisLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_JMEIS_LABEL(");
            sql.addSql("   SMJ_SID,");
            sql.addSql("   SLB_SID,");
            sql.addSql("   SAC_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmjSid());
            sql.addIntValue(bean.getSlbSid());
            sql.addIntValue(bean.getSacSid());
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
     * <p>Update SML_JMEIS_LABEL Data Bindding JavaBean
     * @param bean SML_JMEIS_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlJmeisLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS_LABEL");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   SMJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getSmjSid());
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

    /**
     * <p>Select SML_JMEIS_LABEL All Data
     * @return List in SML_JMEIS_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlJmeisLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlJmeisLabelModel> ret = new ArrayList<SmlJmeisLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SMJ_SID,");
            sql.addSql("   SLB_SID,");
            sql.addSql("   SAC_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_JMEIS_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlLabelRelationFromRs(rs));
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
     * <p>Select SML_JMEIS_LABEL All Data
     * @param smjSid メールSID
     * @return List in SML_JMEIS_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlJmeisLabelModel> select(int smjSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlJmeisLabelModel> ret = new ArrayList<SmlJmeisLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SMJ_SID,");
            sql.addSql("   SLB_SID,");
            sql.addSql("   SAC_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_JMEIS_LABEL");
            sql.addSql(" where ");
            sql.addSql("   SMJ_SID=?");
            sql.addIntValue(smjSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlLabelRelationFromRs(rs));
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
     * <p>Select SML_JMEIS_LABEL
     * @param smjSid SMJ_SID
     * @param slbSid SLB_SID
     * @return SML_JMEIS_LABELModel
     * @throws SQLException SQL実行例外
     */
    public SmlJmeisLabelModel select(int smjSid, int slbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlJmeisLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SMJ_SID,");
            sql.addSql("   SLB_SID,");
            sql.addSql("   SAC_SID");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS_LABEL");
            sql.addSql(" where ");
            sql.addSql("   SMJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smjSid);
            sql.addIntValue(slbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlLabelRelationFromRs(rs);
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
     * <br>[機  能] 指定したラベルが存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param smjSid SMJ_SID
     * @param slbSid SLB_SID
     * @param sacSid SAC_SID
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existsJmeisLabel(int smjSid, int slbSid, int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS_LABEL");
            sql.addSql(" where ");
            sql.addSql("   SMJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   SLB_SID=?");
            sql.addSql(" and");
            sql.addSql("   SAC_SID=?");
            sql.setPagingValue(0, 1);
            sql.addIntValue(smjSid);
            sql.addIntValue(slbSid);
            sql.addIntValue(sacSid);
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
     * <p>Delete SML_JMEIS_LABEL
     * @param smjSid SMJ_SID
     * @param slbSid SLB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int smjSid, int slbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS_LABEL");
            sql.addSql(" where ");
            sql.addSql("   SMJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smjSid);
            sql.addIntValue(slbSid);

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
     * <p>Delete SML_JMEIS_LABEL
     * @param smjSid SMJ_SID
     * @param slbSid SLB_SID
     * @param sacSid SAC_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int smjSid, int slbSid, int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS_LABEL");
            sql.addSql(" where ");
            sql.addSql("   SMJ_SID=?");
            sql.addSql(" and");
            sql.addSql("   SLB_SID=?");
            sql.addSql(" and");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smjSid);
            sql.addIntValue(slbSid);
            sql.addIntValue(sacSid);

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
     * <br>[機  能] メールラベルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param slbSid ラベルSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int slbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS_LABEL");
            sql.addSql(" where ");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(slbSid);

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
     * <br>[機  能] メールラベルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param smjSid メールSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteToMailNum(int smjSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS_LABEL");
            sql.addSql(" where ");
            sql.addSql("   SMJ_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smjSid);

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
     * <p>Create SML_JMEIS_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlJmeisLabel
     * @throws SQLException SQL実行例外
     */
    private SmlJmeisLabelModel __getSmlLabelRelationFromRs(ResultSet rs) throws SQLException {
        SmlJmeisLabelModel bean = new SmlJmeisLabelModel();
        bean.setSmjSid(rs.getInt("SMJ_SID"));
        bean.setSlbSid(rs.getInt("SLB_SID"));
        bean.setSacSid(rs.getInt("SAC_SID"));
        return bean;
    }
}
