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
import jp.groupsession.v2.sml.model.SmlBanDestPermitModel;

/**
 * <p>SML_BAN_DEST_PERMIT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlBanDestPermitDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlBanDestPermitDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlBanDestPermitDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlBanDestPermitDao(Connection con) {
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
            sql.addSql("drop table SML_BAN_DEST_PERMIT");

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
            sql.addSql(" create table SML_BAN_DEST_PERMIT (");
            sql.addSql("   SBC_SID NUMBER(10,0) not null,");
            sql.addSql("   SBP_TARGET_SID NUMBER(10,0) not null,");
            sql.addSql("   SBP_TARGET_KBN NUMBER(10,0) not null");
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
     * <p>Insert SML_BAN_DEST_PERMIT Data Bindding JavaBean
     * @param bean SML_BAN_DEST_PERMIT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlBanDestPermitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_BAN_DEST_PERMIT(");
            sql.addSql("   SBC_SID,");
            sql.addSql("   SBP_TARGET_SID,");
            sql.addSql("   SBP_TARGET_KBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSbcSid());
            sql.addIntValue(bean.getSbpTargetSid());
            sql.addIntValue(bean.getSbpTargetKbn());
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
     * <p>Update SML_BAN_DEST_PERMIT Data Bindding JavaBean
     * @param bean SML_BAN_DEST_PERMIT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlBanDestPermitModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_BAN_DEST_PERMIT");
            sql.addSql(" set ");
            sql.addSql("   SBC_SID=?,");
            sql.addSql("   SBP_TARGET_SID=?,");
            sql.addSql("   SBP_TARGET_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSbcSid());
            sql.addIntValue(bean.getSbpTargetSid());
            sql.addIntValue(bean.getSbpTargetKbn());

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
     * <p>Select SML_BAN_DEST_PERMIT All Data
     * @return List in SML_BAN_DEST_PERMITModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlBanDestPermitModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlBanDestPermitModel> ret = new ArrayList<SmlBanDestPermitModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SBC_SID,");
            sql.addSql("   SBP_TARGET_SID,");
            sql.addSql("   SBP_TARGET_KBN");
            sql.addSql(" from ");
            sql.addSql("   SML_BAN_DEST_PERMIT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlBanDestPermitFromRs(rs));
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
     * <p>Create SML_BAN_DEST_PERMIT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlBanDestPermitModel
     * @throws SQLException SQL実行例外
     */
    private SmlBanDestPermitModel __getSmlBanDestPermitFromRs(ResultSet rs) throws SQLException {
        SmlBanDestPermitModel bean = new SmlBanDestPermitModel();
        bean.setSbcSid(rs.getInt("SBC_SID"));
        bean.setSbpTargetSid(rs.getInt("SBP_TARGET_SID"));
        bean.setSbpTargetKbn(rs.getInt("SBP_TARGET_KBN"));
        return bean;
    }

    /**
     * <p>Select SML_BAN_DEST_PERMIT
     * @param sbcSid 送信制限管理SID
     * @return List in SML_BAN_DEST_PERMITModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlBanDestPermitModel> getSmlBanDestPermitList(int sbcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlBanDestPermitModel> ret = new ArrayList<SmlBanDestPermitModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SBC_SID,");
            sql.addSql("   SBP_TARGET_SID,");
            sql.addSql("   SBP_TARGET_KBN");
            sql.addSql(" from ");
            sql.addSql("   SML_BAN_DEST_PERMIT");
            sql.addSql(" where SBC_SID=?");
            sql.addIntValue(sbcSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlBanDestPermitFromRs(rs));
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
    *
    * <br>[機  能] 送信制限管理SIDで送信制限許可を削除
    * <br>[解  説]
    * <br>[備  考]
    * @param sbcSid 送信制限管理SID
    * @return 処理件数
    * @throws SQLException SQL実行時例外
    */
    public int delete(int sbcSid) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   SML_BAN_DEST_PERMIT");
            sql.addSql(" where SBC_SID=?");
            sql.addIntValue(sbcSid);

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

}
