package jp.groupsession.v2.adr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.model.AdrUconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ADR_UCONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrUconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrUconfDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrUconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrUconfDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert ADR_UCONF Data Bindding JavaBean
     * @param bean ADR_UCONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_UCONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   AUC_ADRCOUNT,");
            sql.addSql("   AUC_COMCOUNT,");
            sql.addSql("   AUC_AUID,");
            sql.addSql("   AUC_ADATE,");
            sql.addSql("   AUC_EUID,");
            sql.addSql("   AUC_EDATE,");
            sql.addSql("   AUC_PERMIT_VIEW,");
            sql.addSql("   AUC_PERMIT_EDIT");
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
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getAucAdrcount());
            sql.addIntValue(bean.getAucComcount());
            sql.addIntValue(bean.getAucAuid());
            sql.addDateValue(bean.getAucAdate());
            sql.addIntValue(bean.getAucEuid());
            sql.addDateValue(bean.getAucEdate());
            sql.addIntValue(bean.getAucPermitView());
            sql.addIntValue(bean.getAucPermitEdit());
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
     * <p>Update ADR_UCONF Data Bindding JavaBean
     * @param bean ADR_UCONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_UCONF");
            sql.addSql(" set ");
            sql.addSql("   AUC_ADRCOUNT=?,");
            sql.addSql("   AUC_COMCOUNT=?,");
            sql.addSql("   AUC_EUID=?,");
            sql.addSql("   AUC_EDATE=?,");
            sql.addSql("   AUC_PERMIT_VIEW=?,");
            sql.addSql("   AUC_PERMIT_EDIT=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAucAdrcount());
            sql.addIntValue(bean.getAucComcount());
            sql.addIntValue(bean.getAucEuid());
            sql.addDateValue(bean.getAucEdate());
            sql.addIntValue(bean.getAucPermitView());
            sql.addIntValue(bean.getAucPermitEdit());
            //where
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Select ADR_UCONF All Data
     * @return List in ADR_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrUconfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrUconfModel> ret = new ArrayList<AdrUconfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   AUC_ADRCOUNT,");
            sql.addSql("   AUC_COMCOUNT,");
            sql.addSql("   AUC_AUID,");
            sql.addSql("   AUC_ADATE,");
            sql.addSql("   AUC_EUID,");
            sql.addSql("   AUC_EDATE,");
            sql.addSql("   AUC_PERMIT_VIEW,");
            sql.addSql("   AUC_PERMIT_EDIT");
            sql.addSql(" from ");
            sql.addSql("   ADR_UCONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrUconfFromRs(rs));
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
     * <p>Select ADR_UCONF
     * @param usrSid USR_SID
     * @return ADR_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public AdrUconfModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrUconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   AUC_ADRCOUNT,");
            sql.addSql("   AUC_COMCOUNT,");
            sql.addSql("   AUC_AUID,");
            sql.addSql("   AUC_ADATE,");
            sql.addSql("   AUC_EUID,");
            sql.addSql("   AUC_EDATE,");
            sql.addSql("   AUC_PERMIT_VIEW,");
            sql.addSql("   AUC_PERMIT_EDIT");
            sql.addSql(" from");
            sql.addSql("   ADR_UCONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrUconfFromRs(rs);
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
     * <p>Delete ADR_UCONF
     * @param usrSid USR_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_UCONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

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
     * <p>Create ADR_UCONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrUconfModel
     * @throws SQLException SQL実行例外
     */
    private AdrUconfModel __getAdrUconfFromRs(ResultSet rs) throws SQLException {
        AdrUconfModel bean = new AdrUconfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setAucAdrcount(rs.getInt("AUC_ADRCOUNT"));
        bean.setAucComcount(rs.getInt("AUC_COMCOUNT"));
        bean.setAucAuid(rs.getInt("AUC_AUID"));
        bean.setAucAdate(UDate.getInstanceTimestamp(rs.getTimestamp("AUC_ADATE")));
        bean.setAucEuid(rs.getInt("AUC_EUID"));
        bean.setAucEdate(UDate.getInstanceTimestamp(rs.getTimestamp("AUC_EDATE")));
        bean.setAucPermitView(rs.getInt("AUC_PERMIT_VIEW"));
        bean.setAucPermitEdit(rs.getInt("AUC_PERMIT_EDIT"));
        return bean;
    }
}