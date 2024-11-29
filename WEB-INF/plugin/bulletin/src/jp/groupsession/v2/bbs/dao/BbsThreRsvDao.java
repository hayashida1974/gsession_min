package jp.groupsession.v2.bbs.dao;


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
import jp.groupsession.v2.bbs.model.BbsThreRsvModel;

/**
 * <p>BBS_THRE_RSV Data Access Object
 * 予約投稿リストの操作を行う。
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsThreRsvDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsThreRsvDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsThreRsvDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsThreRsvDao(Connection con) {
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
            sql.addSql("drop table BBS_THRE_RSV");

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
            sql.addSql(" create table BBS_THRE_RSV (");
            sql.addSql("   BTI_SID integer not null,");
            sql.addSql("   BFI_SID integer not null,");
            sql.addSql("   BTR_URL varchar(3000) not null");
            sql.addSql("   primary key (BTI_SID)");
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
     * <p>全ての予約投稿リストを取得する
     * @return ArrayList in BBS_THRE_RSV Model
     * @throws SQLException SQL実行例外
     * */
    public ArrayList<BbsThreRsvModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<BbsThreRsvModel> ret = new ArrayList<BbsThreRsvModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTR_URL");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_RSV");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
                    while (rs.next()) {
                ret.add(__getBbsThreRsvFromRs(rs));
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
     * <p>指定したスレッドSIDを持つ予約投稿リストを取得する
     * @param btiSid スレッドSID
     * @return ArrayList in BBS_THRE_RSV Model
     * @throws SQLException SQL実行例外
     * */
    public BbsThreRsvModel selectByBti(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        BbsThreRsvModel ret = new BbsThreRsvModel();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTR_URL");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_RSV");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getBbsThreRsvFromRs(rs);
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
     * <p>指定したフォーラムSIDを持つ予約投稿リストを取得する
     * @param bfiSid フォーラムSID
     * @return ArrayList in BBS_THRE_RSV Model
     * @throws SQLException SQL実行例外
     * */
    public ArrayList<BbsThreRsvModel> selectByBfi(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<BbsThreRsvModel> ret = new ArrayList<BbsThreRsvModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTR_URL");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_RSV");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bfiSid);
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsThreRsvFromRs(rs));
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
     * <p>Select BBS_THRE_RSV
     * @param now 現在の日時(文字列)
     * @return List in BFI_SID
     * @throws SQLException SQL実行例外
     * */
    public List<Integer> selectBfiSid(UDate now) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> btiSidList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BBS_THRE_INF.BFI_SID ");
            sql.addSql(" from ");
            sql.addSql("   BBS_THRE_RSV ");
            sql.addSql(" inner join ");
            sql.addSql("   BBS_THRE_INF ");
            sql.addSql(" on ");
            sql.addSql("   BBS_THRE_RSV.BTI_SID=BBS_THRE_INF.BTI_SID ");
            sql.addSql(" where ");
            sql.addSql("   BTI_LIMIT_FR_DATE <= ?");
            sql.addSql(" and ");
            sql.addSql("   BTI_LIMIT_DATE >= ?");
            sql.addSql(" group by ");
            sql.addSql("   BBS_THRE_INF.BFI_SID ");
            sql.addSql(";");
            pstmt = con.prepareStatement(sql.toSqlString());
            UDate limitDate = UDate.getInstanceStr(now.getDateString());
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                btiSidList.add(__getBbsForInfSidFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return btiSidList;
    }

    /**
     * <p>Select BBS_THRE_RSV
     * @param now 現在の日時(文字列)
     * @return List in BbsThreRsvModel
     * @throws SQLException SQL実行例外
     * */
    public List<BbsThreRsvModel> select(UDate now) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsThreRsvModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BBS_THRE_RSV.BTI_SID,");
            sql.addSql("   BBS_THRE_RSV.BFI_SID,");
            sql.addSql("   BBS_THRE_RSV.BTR_URL");
            sql.addSql(" from ");
            sql.addSql("   BBS_THRE_RSV ");
            sql.addSql(" inner join ");
            sql.addSql("   BBS_THRE_INF ");
            sql.addSql(" on ");
            sql.addSql("   BBS_THRE_RSV.BTI_SID=BBS_THRE_INF.BTI_SID ");
            sql.addSql(" where ");
            sql.addSql("   BTI_LIMIT_FR_DATE <= ?");
            sql.addSql(" and ");
            sql.addSql("   BTI_LIMIT_DATE >= ?");
            sql.addSql(";");
            pstmt = con.prepareStatement(sql.toSqlString());
            UDate limitDate = UDate.getInstanceStr(now.getDateString());
            sql.addDateValue(limitDate);
            sql.addDateValue(limitDate);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsThreRsvFromRs(rs));
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
     * <p>Insert BBS_THRE_RSV Data Bindding JavaBean
     * @param bean BbsThreRsvModel
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsThreRsvModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_THRE_RSV(");
            sql.addSql("   BTI_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BTR_URL");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");
            sql.addSql(" ; ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBtiSid());
            sql.addIntValue(bean.getBfiSid());
            sql.addStrValue(bean.getBtrUrl());
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
     * <p>Delete BBS_THRE_RSV
     * @param now 現在の日時
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(UDate now) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   BBS_THRE_RSV ");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID ");
            sql.addSql(" in ");
            sql.addSql(" ( ");
            sql.addSql(" select ");
            sql.addSql("   BBS_THRE_RSV.BTI_SID ");
            sql.addSql(" from ");
            sql.addSql("   BBS_THRE_RSV ");
            sql.addSql(" left join ");
            sql.addSql("   BBS_THRE_INF ");
            sql.addSql(" on ");
            sql.addSql("   BBS_THRE_RSV.BTI_SID=BBS_THRE_INF.BTI_SID ");
            sql.addSql(" where ");
            sql.addSql(" ( ");
            sql.addSql("   BTI_LIMIT_FR_DATE <= ?");
            sql.addSql(" ) ");
            sql.addSql(" or ");
            sql.addSql(" ( ");
            sql.addSql("   BTI_LIMIT_FR_DATE is NULL");
            sql.addSql(" ) ");
            sql.addSql(" ); ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(now);

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
     * <p>Delete BBS_THRE_RSV
     * @param btiSid BTI_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   BBS_THRE_RSV ");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID = ?");
            sql.addSql(" ; ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);

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
     * <p>Create BBS_THRE_RSV Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsThreRsvModel
     * @throws SQLException SQL実行例外
     * */
    private BbsThreRsvModel __getBbsThreRsvFromRs(ResultSet rs) throws SQLException {
        BbsThreRsvModel bean = new BbsThreRsvModel();
        bean.setBfiSid(rs.getInt("BFI_SID"));
        bean.setBtiSid(rs.getInt("BTI_SID"));
        bean.setBtrUrl(rs.getString("BTR_URL"));

        return bean;
    }

    /**
     * <p>Create BFI_SID From ResultSet
     * @param rs ResultSet
     * @return created BTI_SID
     * @throws SQLException SQL実行例外
     * */
    private int __getBbsForInfSidFromRs(ResultSet rs) throws SQLException {
        int btfSid;
        btfSid = rs.getInt("BFI_SID");

        return btfSid;
    }

}
