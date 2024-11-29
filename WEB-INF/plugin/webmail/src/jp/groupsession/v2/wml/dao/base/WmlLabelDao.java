package jp.groupsession.v2.wml.dao.base;

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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.WmlLabelCountModel;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;

/**
 * <p>WML_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlLabelDao(Connection con) {
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
            sql.addSql("drop table WML_LABEL");

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
            sql.addSql(" create table WML_LABEL (");
            sql.addSql("   WLB_SID NUMBER(10,0) not null,");
            sql.addSql("   WLB_NAME varchar(300) not null,");
            sql.addSql("   WLB_ORDER NUMBER(10,0) not null,");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (WLB_SID)");
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
     * <p>Insert WML_LABEL Data Bindding JavaBean
     * @param bean WML_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_LABEL(");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WLB_NAME,");
            sql.addSql("   WLB_ORDER,");
            sql.addSql("   WAC_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWlbSid());
            sql.addStrValue(bean.getWlbName());
            sql.addIntValue(bean.getWlbOrder());
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
     * <p>Update WML_LABEL Data Bindding JavaBean
     * @param bean WML_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_LABEL");
            sql.addSql(" set ");
            sql.addSql("   WLB_NAME=?,");
            sql.addSql("   WLB_ORDER=?,");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWlbName());

            sql.addIntValue(bean.getWlbOrder());
            sql.addIntValue(bean.getWacSid());
            //where
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
     * <p>並び順を更新する
     * @param wlbSid ラベルSID
     * @param wlbOrder 並び順
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateOrder(int wlbSid, int wlbOrder) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_LABEL");
            sql.addSql(" set ");
            sql.addSql("   WLB_ORDER=?");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wlbOrder);
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
     * <p>Select WML_LABEL All Data
     * @return List in WML_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlLabelModel> ret = new ArrayList<WmlLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WLB_NAME,");
            sql.addSql("   WLB_ORDER,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlLabelFromRs(rs));
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
     * <p>Select WML_LABEL
     * @param wlbSid WLB_SID
     * @return WML_LABELModel
     * @throws SQLException SQL実行例外
     */
    public WmlLabelModel select(int wlbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WLB_NAME,");
            sql.addSql("   WLB_ORDER,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wlbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlLabelFromRs(rs);
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
     * <p>Delete WML_LABEL
     * @param wlbSid WLB_SID
     * @return delete count
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
            sql.addSql("   WML_LABEL");
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
     * <br>[機  能] ラベル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return ラベル情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<WmlLabelModel> getLabelList(int wacSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlLabelModel> ret = new ArrayList<WmlLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WLB_SID,");
            sql.addSql("   WLB_NAME,");
            sql.addSql("   WLB_ORDER,");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ?");

            sql.addSql(" order by");
            sql.addSql("   WLB_ORDER asc");

            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getWmlLabelFromRs(rs));
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
     * <br>[機  能] ラベルの並び順の現在最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return ラベル最大値
     * @throws SQLException SQL実行時例外
     */
    public int maxSortNumber(int wacSid)
    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxNumber = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(WLB_ORDER) as MAX_ORDER");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL");
            sql.addSql(" where");
            sql.addSql("   WAC_SID = ?");
            sql.addIntValue(wacSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxNumber = rs.getInt("MAX_ORDER");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return maxNumber;
    }

    /**
     * <br>[機  能] 指定したラベルが存在するかどうかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wlbSid ラベルSID
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int checkLabel(int wacSid, int wlbSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and ");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(wlbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 指定したラベル名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wlbSid ラベルSID
     * @return name
     * @throws SQLException SQL実行例外
     */
    public String getLabelName(int wlbSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String name = "";
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WLB_NAME ");
            sql.addSql(" from");
            sql.addSql("   WML_LABEL ");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wlbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                name = rs.getString("WLB_NAME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return name;
    }

    /**
     * <br>[機  能] 指定したアカウントが使用できるラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考] 未読件数も合わせて取得する
     * @param wacSid アカウントSID
     * @return ラベル情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<WmlLabelCountModel> getLabelListWithMidoku(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlLabelCountModel> ret = new ArrayList<WmlLabelCountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_LABEL.WLB_SID as WLB_SID,");
            sql.addSql("   WML_LABEL.WLB_NAME as WLB_NAME,");
            sql.addSql("   COALESCE(NOREAD_MSG.CNT, 0) as NOREAD_COUNT");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         WML_LABEL_RELATION.WLB_SID as WLB_SID,");
            sql.addSql("         count(*) as CNT");
            sql.addSql("       from");
            sql.addSql("         WML_DIRECTORY,");
            sql.addSql("         WML_LABEL_RELATION,");
            sql.addSql("         WML_MAILDATA");
            sql.addSql("       where");
            sql.addSql("         WML_DIRECTORY.WAC_SID = ?");
            sql.addSql("       and");
            sql.addSql("         WML_DIRECTORY.WDR_TYPE <> ?");
            sql.addSql("       and");
            sql.addSql("         WML_MAILDATA.WAC_SID = ?");
            sql.addSql("       and");
            sql.addSql("         WML_MAILDATA.WDR_SID = WML_DIRECTORY.WDR_SID");
            sql.addSql("       and");
            sql.addSql("         WML_MAILDATA.WMD_READED = ?");
            sql.addSql("       and");
            sql.addSql("         WML_LABEL_RELATION.WAC_SID = ?");
            sql.addSql("       and");
            sql.addSql("         WML_MAILDATA.WMD_MAILNUM = WML_LABEL_RELATION.WMD_MAILNUM");
            sql.addSql("       group by");
            sql.addSql("         WML_LABEL_RELATION.WLB_SID");
            sql.addSql("     ) NOREAD_MSG");
            sql.addSql("   on");
            sql.addSql("     WML_LABEL.WLB_SID = NOREAD_MSG.WLB_SID");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   WLB_ORDER asc");

            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.DIR_TYPE_DUST);
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.WMD_READED_NO);
            sql.addIntValue(wacSid);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                WmlLabelCountModel labelMdl = new WmlLabelCountModel();
                labelMdl.setId(rs.getInt("WLB_SID"));
                labelMdl.setName(rs.getString("WLB_NAME"));
                labelMdl.setNoReadCount(rs.getLong("NOREAD_COUNT"));
                ret.add(labelMdl);
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
     * <p>Create WML_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlLabelModel
     * @throws SQLException SQL実行例外
     */
    private WmlLabelModel __getWmlLabelFromRs(ResultSet rs) throws SQLException {
        WmlLabelModel bean = new WmlLabelModel();
        bean.setWlbSid(rs.getInt("WLB_SID"));
        bean.setWlbName(rs.getString("WLB_NAME"));
        bean.setWlbOrder(rs.getInt("WLB_ORDER"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        return bean;
    }
}
