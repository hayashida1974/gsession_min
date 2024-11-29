package jp.groupsession.v2.rng.dao;

import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rng.model.RingiIdModel;

/**
 * <p>RNG_ID Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RngIdDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngIdDao.class);

    /**
     * <p>Default Constructor
     */
    public RngIdDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngIdDao(Connection con) {
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
            sql.addSql("drop table RNG_ID");

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
            sql.addSql(" create table RNG_ID (");
            sql.addSql("   RID_SID integer not null,");
            sql.addSql("   RID_FORMAT varchar(120) not null,");
            sql.addSql("   RID_INIT integer not null,");
            sql.addSql("   RID_MANUAL integer not null,");
            sql.addSql("   RID_RESET integer not null,");
            sql.addSql("   RID_ZERO integer not null,");
            sql.addSql("   RID_TITLE varchar(50) not null,");
            sql.addSql("   RID_USE_DATE timestamp,");
            sql.addSql("   RID_AUID integer not null,");
            sql.addSql("   RID_ADATE timestamp not null,");
            sql.addSql("   RID_EUID integer not null,");
            sql.addSql("   RID_EDATE timestamp not null,");
            sql.addSql("   primary key (RID_SID)");
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
     * <p>Insert RNG_ID Data Bindding JavaBean
     * @param bean RNG_ID Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RingiIdModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_ID(");
            sql.addSql("   RID_SID,");
            sql.addSql("   RID_FORMAT,");
            sql.addSql("   RID_INIT,");
            sql.addSql("   RID_MANUAL,");
            sql.addSql("   RID_RESET,");
            sql.addSql("   RID_ZERO,");
            sql.addSql("   RID_TITLE,");
            sql.addSql("   RID_USE_DATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRngSid());
            sql.addStrValue(bean.getRngFormat());
            sql.addIntValue(bean.getRngInit());
            sql.addIntValue(bean.getRngManual());
            sql.addIntValue(bean.getRngReset());
            sql.addIntValue(bean.getRngZeroume());
            sql.addStrValue(bean.getRngTitle());
            sql.addDateValue(bean.getRidUseDate());
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
     * <p>Select RNG_ACONF All Data
     * @return List in RNG_ACONFModel
     * @throws SQLException SQL実行例外
     */
    public List<RingiIdModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RingiIdModel> ret = new ArrayList<RingiIdModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RID_SID,");
            sql.addSql("   RID_FORMAT,");
            sql.addSql("   RID_INIT,");
            sql.addSql("   RID_MANUAL,");
            sql.addSql("   RID_RESET,");
            sql.addSql("   RID_ZERO,");
            sql.addSql("   RID_TITLE,");
            sql.addSql("   RID_USE_DATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_ID");
            sql.addSql("  order by RID_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngIdFromRs(rs));
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
     * <p>編集処理
     * @param bean RNG_ID Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RingiIdModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_ID");
            sql.addSql(" set ");
            sql.addSql("   RID_FORMAT=?,");
            sql.addSql("   RID_INIT=?,");
            sql.addSql("   RID_MANUAL=?,");
            sql.addSql("   RID_RESET=?,");
            sql.addSql("   RID_ZERO=?,");
            sql.addSql("   RID_TITLE=?,");
            sql.addSql("   RID_USE_DATE=?");
            sql.addSql(" where");
            sql.addSql("   RID_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getRngFormat());
            sql.addIntValue(bean.getRngInit());
            sql.addIntValue(bean.getRngManual());
            sql.addIntValue(bean.getRngReset());
            sql.addIntValue(bean.getRngZeroume());
            sql.addStrValue(bean.getRngTitle());
            sql.addDateValue(bean.getRidUseDate());
            //where
            sql.addIntValue(bean.getRngSid());

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
     * <p>編集処理 申請ID登録画面用
     * @param bean RNG_ID Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateEntry(RingiIdModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_ID");
            sql.addSql(" set ");
            sql.addSql("   RID_FORMAT=?,");
            sql.addSql("   RID_INIT=?,");
            sql.addSql("   RID_MANUAL=?,");
            sql.addSql("   RID_RESET=?,");
            sql.addSql("   RID_ZERO=?,");
            sql.addSql("   RID_TITLE=?,");
            sql.addSql("   RID_USE_DATE=?");
            sql.addSql(" where");
            sql.addSql("   RID_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getRngFormat());
            sql.addIntValue(bean.getRngInit());
            sql.addIntValue(bean.getRngManual());
            sql.addIntValue(bean.getRngReset());
            sql.addIntValue(bean.getRngZeroume());
            sql.addStrValue(bean.getRngTitle());
            sql.addDateValue(bean.getRidUseDate());
            //where
            sql.addIntValue(bean.getRngSid());

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
     * <p>採番値更新処理
     * @param saiban 採番値
     * @param ridId RNG_ID Data Binding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int idUpdate(int saiban, int ridId) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        UDate now = new UDate();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_ID");
            sql.addSql(" set ");
            sql.addSql("   RID_INIT=?,");
            sql.addSql("   RID_USE_DATE=?");
            sql.addSql(" where");
            sql.addSql("   RID_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(saiban);
            sql.addDateValue(now);
            sql.addIntValue(ridId);
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
     * <p>検索条件でリストを取得する
     * @return List in RNG_ACONFModel
     * @throws SQLException SQL実行例外
     * @param searchWord 検索文字列
     */
    public List<RingiIdModel> serachSelect(String searchWord) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RingiIdModel> ret = new ArrayList<RingiIdModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RID_SID,");
            sql.addSql("   RID_FORMAT,");
            sql.addSql("   RID_INIT,");
            sql.addSql("   RID_MANUAL,");
            sql.addSql("   RID_RESET,");
            sql.addSql("   RID_ZERO,");
            sql.addSql("   RID_TITLE,");
            sql.addSql("   RID_USE_DATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_ID");
            sql.addSql(" where ");
            sql.addSql("   RID_FORMAT like '%"
                    + JDBCUtil.escapeForLikeSearch(searchWord)
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
            sql.addSql("  order by RID_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngIdFromRs(rs));
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
     * <p>申請SIDのフォーマットを取得する
     * @return String
     * @throws SQLException SQL実行例外
     * @param sid sid
     */
    public String select(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RID_FORMAT");
            sql.addSql(" from ");
            sql.addSql("   RNG_ID");
            sql.addSql(" where ");
            sql.addSql("   RID_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = rs.getString("RID_FORMAT");
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
     * <p>申請SIDから情報を取得する
     * @return String
     * @throws SQLException SQL実行例外
     * @param sid sid
     */
    public RingiIdModel selectData(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RingiIdModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RID_SID,");
            sql.addSql("   RID_FORMAT,");
            sql.addSql("   RID_INIT,");
            sql.addSql("   RID_MANUAL,");
            sql.addSql("   RID_RESET,");
            sql.addSql("   RID_ZERO,");
            sql.addSql("   RID_TITLE,");
            sql.addSql("   RID_USE_DATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_ID");
            sql.addSql(" where ");
            sql.addSql("   RID_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getRngIdFromRs(rs);
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
     * <p>申請SIDを削除する
     * @throws SQLException SQL実行例外
     * @param sid sid
     */
    public void delete(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql("   from ");
            sql.addSql(" RNG_ID ");
            sql.addSql(" where ");
            sql.addSql("   RID_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sid);
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
     * <br>[機  能] 稟議申請IDが重複しているかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param rngid チェックする稟議申請ID(null の場合は全稟議件数取得)
     * @param rngSid 除外する稟議SID
     * @return 重複している稟議件数
     * @throws SQLException SQL実行例外
     */
    public int getOverlapCount(String rngid, int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(RNG_SID) as RNG_COUNT");
            sql.addSql(" from");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" where");
            sql.addSql("   1 = 1"); // if分岐で And を使うため

            if (rngid != null) {
                sql.addSql(" and");
                sql.addSql("   RNG_RNDATA.RNG_ID = ?");
                sql.addStrValue(rngid);
            }
            if (rngSid >= 0) {
                sql.addSql(" and");
                sql.addSql("   RNG_RNDATA.RNG_SID <> ?");
                sql.addIntValue(rngSid);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("RNG_COUNT");
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
     * <p>Create RNG_ID Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngAconfModel
     * @throws SQLException SQL実行例外
     */
    private RingiIdModel __getRngIdFromRs(ResultSet rs) throws SQLException {
        RingiIdModel bean = new RingiIdModel();
        bean.setRngSid(rs.getInt("RID_SID"));
        bean.setRngFormat(rs.getString("RID_FORMAT"));
        bean.setRngInit(rs.getInt("RID_INIT"));
        bean.setRngManual(rs.getInt("RID_MANUAL"));
        bean.setRngReset(rs.getInt("RID_RESET"));
        bean.setRngZeroume(rs.getInt("RID_ZERO"));
        bean.setRngTitle(rs.getString("RID_TITLE"));
        bean.setRidUseDate(UDate.getInstanceTimestamp(rs.getTimestamp("RID_USE_DATE")));
        return bean;
    }
}
