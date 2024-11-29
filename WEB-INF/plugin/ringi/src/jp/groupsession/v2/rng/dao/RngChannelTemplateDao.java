package jp.groupsession.v2.rng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rng.model.RngChannelTemplateModel;

/**
 * <p>RNG_CHANNEL_TEMPLATE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngChannelTemplateDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngChannelTemplateDao.class);

    /**
     * <p>Default Constructor
     */
    public RngChannelTemplateDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngChannelTemplateDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RNG_CHANNEL_TEMPLATE Data Bindding JavaBean
     * @param bean RNG_CHANNEL_TEMPLATE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngChannelTemplateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_CHANNEL_TEMPLATE(");
            sql.addSql("   RCT_SID,");
            sql.addSql("   RCT_VER,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RCT_NAME,");
            sql.addSql("   RCT_AUID,");
            sql.addSql("   RCT_ADATE,");
            sql.addSql("   RCT_EUID,");
            sql.addSql("   RCT_EDATE,");
            sql.addSql("   RCT_JKBN");
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
            sql.addIntValue(bean.getRctSid());
            sql.addIntValue(bean.getRctVer());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getRctName());
            sql.addIntValue(bean.getRctAuid());
            sql.addDateValue(bean.getRctAdate());
            sql.addIntValue(bean.getRctEuid());
            sql.addDateValue(bean.getRctEdate());
            sql.addIntValue(bean.getRctJkbn());
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
     * <p>Update RNG_CHANNEL_TEMPLATE Data Bindding JavaBean
     * @param bean RNG_CHANNEL_TEMPLATE Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(RngChannelTemplateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_CHANNEL_TEMPLATE");
            sql.addSql(" set ");
            sql.addSql("   RCT_VER=?,");
            sql.addSql("   RCT_NAME=?,");
            sql.addSql("   RCT_EUID=?,");
            sql.addSql("   RCT_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RCT_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRctVer());
            sql.addStrValue(bean.getRctName());
            sql.addIntValue(bean.getRctEuid());
            sql.addDateValue(bean.getRctEdate());
            //where
            sql.addIntValue(bean.getRctSid());
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
     * <p>Select RNG_CHANNEL_TEMPLATE All Data
     * @return List in RNG_CHANNEL_TEMPLATEModel
     * @throws SQLException SQL実行例外
     */
    public List<RngChannelTemplateModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RngChannelTemplateModel> ret = new ArrayList<RngChannelTemplateModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RCT_SID,");
            sql.addSql("   RCT_VER,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RCT_NAME,");
            sql.addSql("   RCT_AUID,");
            sql.addSql("   RCT_ADATE,");
            sql.addSql("   RCT_EUID,");
            sql.addSql("   RCT_EDATE,");
            sql.addSql("   RCT_JKBN");
            sql.addSql(" from ");
            sql.addSql("   RNG_CHANNEL_TEMPLATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngChannelTemplateFromRs(rs));
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
     * <p>Select RNG_CHANNEL_TEMPLATE
     * @param rctSid 経路テンプレートSID
     * @param userSid ユーザSID
     * @return RNG_CHANNEL_TEMPLATEModel
     * @throws SQLException SQL実行例外
     */
    public RngChannelTemplateModel select(int rctSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngChannelTemplateModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RCT_SID,");
            sql.addSql("   RCT_VER,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RCT_NAME,");
            sql.addSql("   RCT_AUID,");
            sql.addSql("   RCT_ADATE,");
            sql.addSql("   RCT_EUID,");
            sql.addSql("   RCT_EDATE,");
            sql.addSql("   RCT_JKBN");
            sql.addSql(" from");
            sql.addSql("   RNG_CHANNEL_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   RCT_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rctSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngChannelTemplateFromRs(rs);
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
     * <br>[機  能] 指定したユーザが登録した経路テンプレート情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 経路テンプレート情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<RngChannelTemplateModel> getChannelTemplateList(int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RngChannelTemplateModel> ret = new ArrayList<RngChannelTemplateModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RCT_SID,");
            sql.addSql("   RCT_VER,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RCT_NAME,");
            sql.addSql("   RCT_AUID,");
            sql.addSql("   RCT_ADATE,");
            sql.addSql("   RCT_EUID,");
            sql.addSql("   RCT_EDATE,");
            sql.addSql("   RCT_JKBN");
            sql.addSql(" from ");
            sql.addSql("   RNG_CHANNEL_TEMPLATE");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RCT_JKBN=0");
            sql.addIntValue(userSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getRngChannelTemplateFromRs(rs));
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
     * <p>Delete RNG_CHANNEL_TEMPLATE
     * @param rctSid 経路テンプレートSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int rctSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_CHANNEL_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   RCT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rctSid);

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
     * <p>Delete RNG_CHANNEL_TEMPLATE
     * @param rctList 経路テンプレートモデルリスト
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteNoUse(List<RngChannelTemplateModel> rctList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_CHANNEL_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   RCT_SID =? ");
            pstmt = con.prepareStatement(sql.toSqlString());

            for (RngChannelTemplateModel rctMdl : rctList) {
                sql.addIntValue(rctMdl.getRctSid());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count += pstmt.executeUpdate();
                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Update RNG_CHANNEL_TEMPLATE Data Bindding JavaBean
     * @param rctSid 経路テンプレートSID
     * @param jkbn 状態区分 9:削除
     * @param sessionUsrSid セッションユーザSID
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int updateJkbn(int rctSid, int jkbn, int sessionUsrSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_CHANNEL_TEMPLATE");
            sql.addSql(" set ");
            sql.addSql("   RCT_JKBN=?,");
            sql.addSql("   RCT_EUID=?,");
            sql.addSql("   RCT_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RCT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(jkbn);
            sql.addIntValue(sessionUsrSid);;
            sql.addDateValue(new UDate());
            //where
            sql.addIntValue(rctSid);

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
     * 未使用の論理削除済み経路テンプレートを取得します
     * @return 論理削除済みテンプレートモデルのリスト
     * @throws SQLException SQL実行例外
     * */
    public List<RngChannelTemplateModel> selectSakujoChannel() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<RngChannelTemplateModel> ret = new ArrayList<RngChannelTemplateModel>();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RCT_SID,");
            sql.addSql("   RCT_VER,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RCT_NAME,");
            sql.addSql("   RCT_AUID,");
            sql.addSql("   RCT_ADATE,");
            sql.addSql("   RCT_EUID,");
            sql.addSql("   RCT_EDATE,");
            sql.addSql("   RCT_JKBN");
            sql.addSql(" from ");
            sql.addSql("   RNG_CHANNEL_TEMPLATE");
            sql.addSql(" where");
            sql.addSql("   RCT_JKBN=9");
            sql.addSql(" and");
            sql.addSql("   RCT_SID not in(");
            sql.addSql("  select");
            sql.addSql("     RCT_SID");
            sql.addSql("  from");
            sql.addSql("     RNG_TEMPLATE)");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
               ret.add(__getRngChannelTemplateFromRs(rs));
            }
            return ret;
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create RNG_CHANNEL_TEMPLATE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngChannelTemplateModel
     * @throws SQLException SQL実行例外
     */
    private RngChannelTemplateModel __getRngChannelTemplateFromRs(ResultSet rs)
    throws SQLException {
        RngChannelTemplateModel bean = new RngChannelTemplateModel();
        bean.setRctSid(rs.getInt("RCT_SID"));
        bean.setRctVer(rs.getInt("RCT_VER"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRctName(rs.getString("RCT_NAME"));
        bean.setRctAuid(rs.getInt("RCT_AUID"));
        bean.setRctAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RCT_ADATE")));
        bean.setRctEuid(rs.getInt("RCT_EUID"));
        bean.setRctEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RCT_EDATE")));
        bean.setRctJkbn(rs.getInt("RCT_JKBN"));
        return bean;
    }
}
