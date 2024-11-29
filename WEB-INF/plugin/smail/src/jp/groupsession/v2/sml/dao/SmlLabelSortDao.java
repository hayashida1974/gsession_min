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
import jp.groupsession.v2.sml.model.SmlLabelSortModel;

/**
 * <p>SML_LABEL_SORT Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlLabelSortDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlLabelSortDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlLabelSortDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlLabelSortDao(Connection con) {
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
            sql.addSql("drop table SML_LABEL_SORT");

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
     * <p>Insert SML_LABEL_SORT Data Bindding JavaBean
     * @param bean SML_LABEL_SORT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlLabelSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_LABEL_SORT(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SLB_SID,");
            sql.addSql("   SLS_SORTKEY,");
            sql.addSql("   SLS_ORDER");
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
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getSlbSid());
            sql.addIntValue(bean.getSlsSortkey());
            sql.addIntValue(bean.getSlsOrder());
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
     * <p>Update SML_LABEL_SORT Data Bindding JavaBean
     * @param bean SML_LABEL_SORT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlLabelSortModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_LABEL_SORT");
            sql.addSql(" set ");
            sql.addSql("   SLS_SORTKEY=?,");
            sql.addSql("   SLS_ORDER=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSlsSortkey());
            sql.addIntValue(bean.getSlsOrder());
            //where
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getUsrSid());
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
     * <p>Select WML_MAILDATA_SORT All Data
     * @return List in WML_MAILDATA_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlLabelSortModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlLabelSortModel> ret = new ArrayList<SmlLabelSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SLB_SID,");
            sql.addSql("   SLS_SORTKEY,");
            sql.addSql("   SLS_ORDER");
            sql.addSql(" from ");
            sql.addSql("   SML_LABEL_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlLabelSortFromRs(rs));
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
     * <p>アカウント毎のメールソート情報を取得する
     * @param sacSid アカウントSID
     * @return List in SML_LABEL_SORTModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlLabelSortModel> select(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlLabelSortModel> ret = new ArrayList<SmlLabelSortModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SLB_SID,");
            sql.addSql("   SLS_SORTKEY,");
            sql.addSql("   SLS_ORDER");
            sql.addSql(" from ");
            sql.addSql("   SML_LABEL_SORT");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addIntValue(sacSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlLabelSortFromRs(rs));
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
     * <p>Select SML_LABEL_SORT
     * @param sacSid SAC_SID
     * @param usrSid USR_SID
     * @param slbSid SLB_SID
     * @return SML_LABEL_SORTModel
     * @throws SQLException SQL実行例外
     */
    public SmlLabelSortModel select(int sacSid, int usrSid, int slbSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlLabelSortModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SLB_SID,");
            sql.addSql("   SLS_SORTKEY,");
            sql.addSql("   SLS_ORDER");
            sql.addSql(" from");
            sql.addSql("   SML_MAILDATA_SORT");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(slbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlLabelSortFromRs(rs);
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
     * <p>Delete SML_LABEL_SORT
     * @param sacSid SAC_SID
     * @param usrSid USR_SID
     * @param slbSid SLB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int sacSid, int usrSid, int slbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_LABEL_SORT");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   SLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(usrSid);
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
     * <br>[機  能] 指定したアカウントに関連するメール情報表示順を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteMailSortOfAccount(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_LABEL_SORT");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定したユーザに関連するメール情報表示順を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteMailSortOfUser(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_LABEL_SORT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

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
     * <br>[機  能] 指定したラベルに関連するメール情報表示順を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param slbSid ラベルSID
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteMailSortOfLabel(int slbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_LABEL_SORT");
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
     * <p>Create SML_LABEL_SORT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlLableSortModel
     * @throws SQLException SQL実行例外
     */
    private SmlLabelSortModel __getSmlLabelSortFromRs(ResultSet rs) throws SQLException {
        SmlLabelSortModel bean = new SmlLabelSortModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSlbSid(rs.getInt("SLB_SID"));
        bean.setSlsSortkey(rs.getInt("SLS_SORTKEY"));
        bean.setSlsOrder(rs.getInt("SLS_ORDER"));
        return bean;
    }
}
