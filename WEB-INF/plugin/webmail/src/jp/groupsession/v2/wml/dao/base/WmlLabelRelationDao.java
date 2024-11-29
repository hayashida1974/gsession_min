package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.wml.model.base.WmlLabelRelationModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_LABEL_RELATION Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlLabelRelationDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlLabelRelationDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlLabelRelationDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlLabelRelationDao(Connection con) {
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
            sql.addSql("drop table WML_LABEL_RELATION");

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
            sql.addSql(" create table WML_LABEL_RELATION (");
            sql.addSql("   WMD_MAILNUM Date not null,");
            sql.addSql("   WLB_SID NUMBER(10,0) not null,");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (WMD_MAILNUM,WLB_SID)");
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
    public void insert(WmlLabelRelationModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_LABEL_RELATION(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WAC_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWmdMailnum());
            sql.addIntValue(bean.getWlbSid());
            sql.addIntValue(bean.getWacSid());
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
     * <p>Insert WML_LABEL_RELATION Data Bindding JavaBean
     * @param beanList WML_LABEL_RELATION DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<WmlLabelRelationModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_LABEL_RELATION(");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WAC_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (WmlLabelRelationModel bean : beanList) {
                sql.addLongValue(bean.getWmdMailnum());
                sql.addIntValue(bean.getWlbSid());
                sql.addIntValue(bean.getWacSid());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();

                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update WML_LABEL_RELATION Data Bindding JavaBean
     * @param bean WML_LABEL_RELATION Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlLabelRelationModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addLongValue(bean.getWmdMailnum());
            sql.addIntValue(bean.getWlbSid());

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
     * <p>Select WML_LABEL_RELATION All Data
     * @return List in WML_LABEL_RELATIONModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlLabelRelationModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlLabelRelationModel> ret = new ArrayList<WmlLabelRelationModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL_RELATION");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlLabelRelationFromRs(rs));
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
     * <p>Select WML_LABEL_RELATION All Data
     * @param wmdMailnum メール番号
     * @return List in WML_LABEL_RELATIONModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlLabelRelationModel> select(long wmdMailnum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlLabelRelationModel> ret = new ArrayList<WmlLabelRelationModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addLongValue(wmdMailnum);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlLabelRelationFromRs(rs));
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
     * <p>Select WML_LABEL_RELATION
     * @param wmdMailnum WMD_MAILNUM
     * @param wlbSid WLB_SID
     * @return WML_LABEL_RELATIONModel
     * @throws SQLException SQL実行例外
     */
    public WmlLabelRelationModel select(long wmdMailnum, int wlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlLabelRelationModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
            sql.addIntValue(wlbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlLabelRelationFromRs(rs);
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
     * <p>Delete WML_LABEL_RELATION
     * @param wmdMailnum WMD_MAILNUM
     * @param wlbSid WLB_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wmdMailnum, int wlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM=?");
            sql.addSql(" and");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wmdMailnum);
            sql.addIntValue(wlbSid);

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
     * @param wlbSid ラベルSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int wlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wlbSid);

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
     * @param messageNum メッセージ番号
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteToMailNum(long messageNum) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(messageNum);

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
     * <p>Delete WML_LABEL_RELATION
     * @param wlbSid WLB_SID
     * @param mailNumArray WMD_MAILNUMの一覧
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wlbSid, long[] mailNumArray) throws SQLException {

        if (mailNumArray == null || mailNumArray.length == 0) {
            return 0;
        }

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID=?");
            sql.addIntValue(wlbSid);

            sql.addSql(" and ");
            sql.addSql("   WMD_MAILNUM in (");
            for (int idx = 0; idx < mailNumArray.length; idx++) {
                if (idx != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addLongValue(mailNumArray[idx]);
            }
            sql.addSql("   )");

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

    /**
     * <p>Select WML_LABEL_RELATION All Data
     * @return List in WML_LABEL_RELATIONModel
     * @throws SQLException SQL実行例外
     */
    public long count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL_RELATION");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CNT");
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
     * <p>Select WML_LABEL_RELATION All Data
     * @param offset 取得するレコード位置
     * @param limit 取得する最大件数
     * @return List in WML_LABEL_RELATIONModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlLabelRelationModel> selectLimit(
            long offset, long limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlLabelRelationModel> ret = new ArrayList<WmlLabelRelationModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WMD_MAILNUM,");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" order by ");
            sql.addSql("   WMD_MAILNUM asc,");
            sql.addSql("   WLB_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlLabelRelationFromRs(rs));
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
     * <br>[機  能] 指定したラベルのメール件数を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return ラベルごとのメール件数
     * @throws SQLException SQL実行時例外
     */
    public Map<Integer, Long> getLabelMailCount(
        int wacSid, List<Integer> labelSidList) throws SQLException {

        Map<Integer, Long> ret = new HashMap<Integer, Long>();
        if (labelSidList == null || labelSidList.isEmpty()) {
            return ret;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_LABEL.WLB_SID as WLB_SID,");
            sql.addSql("   COALESCE(MSG.CNT, 0) as CNT");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         WML_LABEL_RELATION.WLB_SID as WLB_SID,");
            sql.addSql("         count(*) as CNT");
            sql.addSql("       from");
            sql.addSql("         WML_LABEL_RELATION,");
            sql.addSql("         WML_MAILDATA");
            sql.addSql("       where");
            sql.addSql("         WML_MAILDATA.WAC_SID = ?");
            sql.addSql("       and");
            sql.addSql("         WML_LABEL_RELATION.WAC_SID = ?");
            sql.addSql("       and");
            sql.addSql("         WML_MAILDATA.WMD_MAILNUM = WML_LABEL_RELATION.WMD_MAILNUM");
            sql.addSql("       group by");
            sql.addSql("         WML_LABEL_RELATION.WLB_SID");
            sql.addSql("     ) MSG");
            sql.addSql("   on");
            sql.addSql("     WML_LABEL.WLB_SID = MSG.WLB_SID");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   WLB_ORDER asc");

            sql.addIntValue(wacSid);
            sql.addIntValue(wacSid);
            sql.addIntValue(wacSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getInt("WLB_SID"), rs.getLong("CNT"));
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
     * <p>Create WML_LABEL_RELATION Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlLabelRelationModel
     * @throws SQLException SQL実行例外
     */
    private WmlLabelRelationModel __getWmlLabelRelationFromRs(ResultSet rs) throws SQLException {
        WmlLabelRelationModel bean = new WmlLabelRelationModel();
        bean.setWmdMailnum(rs.getInt("WMD_MAILNUM"));
        bean.setWlbSid(rs.getInt("WLB_SID"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        return bean;
    }
}
