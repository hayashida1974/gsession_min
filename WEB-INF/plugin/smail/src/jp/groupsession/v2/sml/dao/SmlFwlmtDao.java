package jp.groupsession.v2.sml.dao;

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
import jp.groupsession.v2.sml.model.SmlFwlmtModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>SML_FWLMT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlFwlmtDao extends AbstractDao {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlFwlmtDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlFwlmtDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlFwlmtDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert SML_FWLMT Data Bindding JavaBean
     * @param bean SML_FWLMT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlFwlmtModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_FWLMT(");
            sql.addSql("   SFL_TEXT,");
            sql.addSql("   SFL_AUID,");
            sql.addSql("   SFL_ADATE,");
            sql.addSql("   SFL_EUID,");
            sql.addSql("   SFL_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getSflText());
            sql.addIntValue(bean.getSflAuid());
            sql.addDateValue(bean.getSflAdate());
            sql.addIntValue(bean.getSflEuid());
            sql.addDateValue(bean.getSflEdate());
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
     * <p>Select SML_FWLMT Data
     * @return List in SML_FWLMTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlFwlmtModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlFwlmtModel> ret = new ArrayList<SmlFwlmtModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SFL_TEXT,");
            sql.addSql("   SFL_AUID,");
            sql.addSql("   SFL_ADATE,");
            sql.addSql("   SFL_EUID,");
            sql.addSql("   SFL_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_FWLMT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlAdminFromRs(rs));
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
     * <br>[機  能] 全て削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @throws SQLException SQL実行例外
     */
    public void delete() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_FWLMT");

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
     * <p>Create SML_FWLMT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlFwlmtModel
     * @throws SQLException SQL実行例外
     */
    private SmlFwlmtModel __getSmlAdminFromRs(ResultSet rs) throws SQLException {
        SmlFwlmtModel bean = new SmlFwlmtModel();
        bean.setSflText(rs.getString("SFL_TEXT"));
        bean.setSflAuid(rs.getInt("SFL_AUID"));
        bean.setSflAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SFL_ADATE")));
        bean.setSflEuid(rs.getInt("SFL_EUID"));
        bean.setSflEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SFL_EDATE")));
        return bean;
    }
}
