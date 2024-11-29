package jp.groupsession.v2.rng.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rng.model.RngTemplatecategoryUseModel;

/**
 * <p>RNG_TEMPLATECATEGORY_USE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplatecategoryUseDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngTemplatecategoryUseDao.class);

    /**
     * <p>Default Constructor
     */
    public RngTemplatecategoryUseDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngTemplatecategoryUseDao(Connection con) {
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
            sql.addSql("drop table RNG_TEMPLATECATEGORY_USE");

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
            sql.addSql(" create table RNG_TEMPLATECATEGORY_USE (");
            sql.addSql("   RTC_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (RTC_SID,USR_SID,GRP_SID)");
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
     * <p>Insert RNG_TEMPLATECATEGORY_USE Data Bindding JavaBean
     * @param bean RNG_TEMPLATECATEGORY_USE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngTemplatecategoryUseModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_TEMPLATECATEGORY_USE(");
            sql.addSql("   RTC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRtcSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
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
     * <p>Update RNG_TEMPLATECATEGORY_USE Data Bindding JavaBean
     * @param bean RNG_TEMPLATECATEGORY_USE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngTemplatecategoryUseModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATECATEGORY_USE");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   RTC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getRtcSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());

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
     * <p>Select RNG_TEMPLATECATEGORY_USE All Data
     * @param rtcSid 稟議カテゴリSID
     * @return List in RNG_TEMPLATECATEGORY_USEModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RngTemplatecategoryUseModel> select(int rtcSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplatecategoryUseModel> ret = new ArrayList<RngTemplatecategoryUseModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATECATEGORY_USE");
            sql.addSql("  where");
            sql.addSql("   RTC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtcSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplatecategoryUseFromRs(rs));
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
     * <p>Select RNG_TEMPLATECATEGORY_USE
     * @return List in RNG_TEMPLATECATEGORY_USEModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RngTemplatecategoryUseModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplatecategoryUseModel> ret = new ArrayList<RngTemplatecategoryUseModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATECATEGORY_USE");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplatecategoryUseFromRs(rs));
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
     * <p>Select RNG_TEMPLATECATEGORY_USE
     * @param rtcSid RTC_SID
     * @param usrSid USR_SID
     * @param grpSid GRP_SID
     * @return RNG_TEMPLATECATEGORY_USEModel
     * @throws SQLException SQL実行例外
     */
    public RngTemplatecategoryUseModel select(
            int rtcSid, int usrSid, int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngTemplatecategoryUseModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATECATEGORY_USE");
            sql.addSql(" where ");
            sql.addSql("   RTC_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtcSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(grpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngTemplatecategoryUseFromRs(rs);
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
     * <p>稟議テンプレートカテゴリSIDの制限に含まれるか判定する
     * @param rtcSid テンプレートカテゴリSID
     * @param userSid USR_SID
     * @return 稟議テンプレートカテゴリSID一覧
     * @throws SQLException SQL実行例外
     */
    public boolean isSelectedSid(int rtcSid, int userSid)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATECATEGORY_USE");
            sql.addSql(" where");
            sql.addSql("   RTC_SID = ?");
            sql.addSql("   and (GRP_SID in (");
            sql.addSql("     select");
            sql.addSql("       GRP_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_BELONGM");
            sql.addSql("     where");
            sql.addSql("       USR_SID = ?");
            sql.addSql("   )");
            sql.addSql("   or USR_SID = ?)");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtcSid);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>稟議テンプレートカテゴリSID一覧を取得する
     * @param userSid USR_SID
     * @return 稟議テンプレートカテゴリSID一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getRngTemplatecategorySidList(int userSid)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATECATEGORY_USE");
            sql.addSql(" where");
            sql.addSql("   GRP_SID in (");
            sql.addSql("     select");
            sql.addSql("       GRP_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_BELONGM");
            sql.addSql("     where");
            sql.addSql("       USR_SID = ?");
            sql.addSql("   )");
            sql.addSql("   or USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer key = Integer.valueOf(rs.getInt("RTC_SID"));
                if (!ret.contains(key)) { // 重複チェック
                    ret.add(key);
                }
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
     * <p>Delete RNG_TEMPLATECATEGORY_USE
     * @param rtcSid RTC_SID
     * @return count 削除数
     * @throws SQLException SQL実行例外
     */
    public int delete(int rtcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATECATEGORY_USE");
            sql.addSql(" where ");
            sql.addSql("   RTC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtcSid);

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
     * <p>Create RNG_TEMPLATECATEGORY_USE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngTemplatecategoryUseModel
     * @throws SQLException SQL実行例外
     */
    private RngTemplatecategoryUseModel __getRngTemplatecategoryUseFromRs(
            ResultSet rs) throws SQLException {
        RngTemplatecategoryUseModel bean = new RngTemplatecategoryUseModel();
        bean.setRtcSid(rs.getInt("RTC_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        return bean;
    }
}
