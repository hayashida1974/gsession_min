package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.model.WmlDirectoryCountModel;
import jp.groupsession.v2.wml.model.base.WmlDirectoryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_DIRECTORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class WmlDirectoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlDirectoryDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlDirectoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlDirectoryDao(Connection con) {
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
            sql.addSql("drop table WML_DIRECTORY");

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
            sql.addSql(" create table WML_DIRECTORY (");
            sql.addSql("   WDR_SID Date not null,");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   WDR_NAME varchar(100) not null,");
            sql.addSql("   WDR_TYPE NUMBER(10,0) not null,");
            sql.addSql("   WDR_VIEW NUMBER(10,0) not null,");
            sql.addSql("   WDR_DEFAULT NUMBER(10,0) not null,");
            sql.addSql("   primary key (WDR_SID)");
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
     * <p>Insert WML_DIRECTORY Data Bindding JavaBean
     * @param bean WML_DIRECTORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlDirectoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_DIRECTORY(");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDR_NAME,");
            sql.addSql("   WDR_TYPE,");
            sql.addSql("   WDR_VIEW,");
            sql.addSql("   WDR_DEFAULT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWdrSid());
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWdrName());
            sql.addIntValue(bean.getWdrType());
            sql.addIntValue(bean.getWdrView());
            sql.addIntValue(bean.getWdrDefault());
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
     * <p>Update WML_DIRECTORY Data Bindding JavaBean
     * @param bean WML_DIRECTORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlDirectoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   WAC_SID=?,");
            sql.addSql("   WDR_NAME=?,");
            sql.addSql("   WDR_TYPE=?,");
            sql.addSql("   WDR_VIEW=?,");
            sql.addSql("   WDR_DEFAULT=?");
            sql.addSql(" where ");
            sql.addSql("   WDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWdrName());
            sql.addIntValue(bean.getWdrType());
            sql.addIntValue(bean.getWdrView());
            sql.addIntValue(bean.getWdrDefault());
            //where
            sql.addLongValue(bean.getWdrSid());

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
     * <p>Select WML_DIRECTORY All Data
     * @return List in WML_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlDirectoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlDirectoryModel> ret = new ArrayList<WmlDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDR_NAME,");
            sql.addSql("   WDR_TYPE,");
            sql.addSql("   WDR_VIEW,");
            sql.addSql("   WDR_DEFAULT");
            sql.addSql(" from ");
            sql.addSql("   WML_DIRECTORY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlDirectoryFromRs(rs));
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
     * <p>Select WML_DIRECTORY
     * @param wdrSid WDR_SID
     * @return WML_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public WmlDirectoryModel select(long wdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlDirectoryModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDR_NAME,");
            sql.addSql("   WDR_TYPE,");
            sql.addSql("   WDR_VIEW,");
            sql.addSql("   WDR_DEFAULT");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   WDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wdrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlDirectoryFromRs(rs);
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
     * <br>[機  能] 指定したディレクトリのアカウントSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wdrSid ディレクトリSID
     * @return アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public int getAccountSid(long wdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int wacSid = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   WDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wdrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                wacSid = rs.getInt("WAC_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return wacSid;
    }

    /**
     * <br>[機  能] 指定したアカウントの受信箱ディレクトリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return 受信箱ディレクトリのディレクトリSID
     * @throws SQLException SQL実行時例外
     */
    public long getReceiveDirSid(int wacSid) throws SQLException {

        return getDirSid(wacSid, GSConstWebmail.DIR_TYPE_RECEIVE);
    }

    /**
     * <br>[機  能] 指定したアカウントのディレクトリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wdrType ディレクトリ種別
     * @return ディレクトリSID
     * @throws SQLException SQL実行時例外
     */
    public long getDirSid(int wacSid, int wdrType) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long wdrSid = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WDR_SID");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and ");
            sql.addSql("   WDR_TYPE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);
            sql.addIntValue(wdrType);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                wdrSid = rs.getLong("WDR_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return wdrSid;
    }

    /**
     * <br>[機  能] 指定したアカウントのディレクトリ情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return ディレクトリ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<WmlDirectoryModel> getDirectoryList(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlDirectoryModel> ret = new ArrayList<WmlDirectoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDR_NAME,");
            sql.addSql("   WDR_TYPE,");
            sql.addSql("   WDR_DEFAULT,");
            sql.addSql("   WDR_VIEW");
            sql.addSql(" from ");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where");
            sql.addSql("   WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WDR_VIEW = ?");
            sql.addSql(" order by");
            sql.addSql("   WDR_DEFAULT,");
            sql.addSql("   WDR_TYPE");
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.DSP_VIEW_OK);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getWmlDirectoryFromRs(rs));
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
     * <br>[機  能] 指定したアカウント一覧のディレクトリ情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSidList アカウントSID一覧
     * @return ディレクトリ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<WmlDirectoryModel> getDirectoryList(
            int[] wacSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WmlDirectoryModel> ret = new ArrayList<WmlDirectoryModel>();
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDR_NAME,");
            sql.addSql("   WDR_TYPE,");
            sql.addSql("   WDR_DEFAULT,");
            sql.addSql("   WDR_VIEW");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where");
            sql.addSql("   WAC_SID in (");
            for (int i = 0; i < wacSidList.length; i++) {
                sql.addSql((i == 0 ? "?" : ",?"));
                sql.addIntValue(wacSidList[i]);
            }
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   WDR_VIEW = ?");
            sql.addSql(" order by");
            sql.addSql("   WDR_DEFAULT,");
            sql.addSql("   WDR_TYPE");

            sql.addIntValue(GSConstWebmail.DSP_VIEW_OK);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getWmlDirectoryFromRs(rs));
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
     * <br>[機  能] 指定したアカウントのディレクトリ情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wdrSid ディレクトリSID
     * @return ディレクトリ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public boolean isViewDirecty(int wacSid, long wdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDR_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where");
            sql.addSql("   WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WDR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WDR_VIEW = ?");
            sql.addIntValue(wacSid);
            sql.addLongValue(wdrSid);
            sql.addIntValue(GSConstWebmail.DSP_VIEW_OK);
            sql.setPagingValue(0, 1);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            result = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return result;
    }

    /**
     * <br>[機  能] 指定したアカウントのディレクトリSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return ディレクトリSID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Long> getDirectorySidList(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Long> ret = new ArrayList<Long>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WDR_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where");
            sql.addSql("   WAC_SID = ?");
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getLong("WDR_SID"));
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
     * <br>[機  能] 指定したアカウント、ディレクトリ種別に該当するディレクトリSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID | 指定しない場合は負の値
     * @param wdrTypeList ディレクトリ種別 | null または 空のリストを指定した場合、
     *                                        検索を行わない
     * @return ディレクトリSID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Long> getDirSidList(int wacSid, List<Integer> wdrTypeList)
    throws SQLException {

        List<Long> ret = new ArrayList<Long>();
        if (wdrTypeList == null || wdrTypeList.isEmpty()) {
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
            sql.addSql("   WDR_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where");

            if (wacSid > 0) {
                sql.addSql("   WML_DIRECTORY.WAC_SID = ? ");
                sql.addSql(" and ");
                sql.addIntValue(wacSid);
            }

            sql.addSql("   WML_DIRECTORY.WDR_TYPE in (");
            sql.addSql("     ?");
            sql.addIntValue(wdrTypeList.get(0));

            for (int idx = 1; idx < wdrTypeList.size(); idx++) {
                sql.addSql("     ,?");
                sql.addIntValue(wdrTypeList.get(idx).intValue());
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getLong("WDR_SID"));
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
     * <br>[機  能] ディレクトリ名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wdrSid ディレクトリSID
     * @return ディレクトリ名
     * @throws SQLException SQL実行時例外
     */
    public String getDirName(long wdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String wdrName = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WDR_NAME");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   WDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wdrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                wdrName = rs.getString("WDR_NAME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return wdrName;
    }

    /**
     * <br>[機  能] ディレクトリ種別を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wdrSid ディレクトリSID
     * @return ディレクトリ種別
     * @throws SQLException SQL実行時例外
     */
    public int getDirType(long wdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int wdrType = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WDR_TYPE");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   WDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wdrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                wdrType = rs.getInt("WDR_TYPE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return wdrType;
    }

    /**
     * <br>[機  能] 指定したアカウントのディレクトリ情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param wacSid アカウントSID
     * @return ディレクトリ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<WmlDirectoryCountModel> getDirectoryList(RequestModel reqMdl,
                                              int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlDirectoryCountModel> ret = new ArrayList<WmlDirectoryCountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_DIRECTORY.WDR_SID as WDR_SID,");
            sql.addSql("   WML_DIRECTORY.WDR_NAME as WDR_NAME,");
            sql.addSql("   WML_DIRECTORY.WDR_TYPE as WDR_TYPE,");
            sql.addSql("   COALESCE(NOREAD_MSG.CNT, 0) as NOREAD_COUNT");
            sql.addSql(" from ");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         WDR_SID,");
            sql.addSql("         count(*) as CNT");
            sql.addSql("       from");
            sql.addSql("         WML_MAILDATA");
            sql.addSql("       where");
            sql.addSql("         WAC_SID = ?");
            sql.addSql("       and");
            sql.addSql("         WMD_READED = ?");
            sql.addSql("       group by");
            sql.addSql("         WDR_SID");
            sql.addSql("     ) NOREAD_MSG");
            sql.addSql("   on");
            sql.addSql("     WML_DIRECTORY.WDR_SID = NOREAD_MSG.WDR_SID");
            sql.addSql(" where");
            sql.addSql("   WML_DIRECTORY.WAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   WML_DIRECTORY.WDR_VIEW = ?");
            sql.addSql(" order by");
            sql.addSql("   WML_DIRECTORY.WDR_DEFAULT,");
            sql.addSql("   WML_DIRECTORY.WDR_TYPE");

            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.WMD_READED_NO);
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.DSP_VIEW_OK);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            WmlDirectoryModel baseMdl = null;
            WmlDirectoryCountModel countMdl = null;
            while (rs.next()) {
                baseMdl = new WmlDirectoryModel();
                baseMdl.setWdrSid(rs.getLong("WDR_SID"));
                baseMdl.setWdrName(__getDirName(
                    reqMdl, rs.getLong("WDR_SID"), rs.getString("WDR_NAME")));
                baseMdl.setWdrType(rs.getInt("WDR_TYPE"));
                
                countMdl = new WmlDirectoryCountModel(baseMdl);
                countMdl.setNoReadCount(rs.getLong("NOREAD_COUNT"));

                ret.add(countMdl);
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
     * <p>Delete WML_DIRECTORY
     * @param wdrSid WDR_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(long wdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_DIRECTORY");
            sql.addSql(" where ");
            sql.addSql("   WDR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(wdrSid);

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
     * <br>[機  能] ディレクトリ名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param wdrType ディレクトリタイプ
     * @param wdrName ディレクトリ名
     * @return String
     */
    private String __getDirName(RequestModel reqMdl, long wdrType, String wdrName) {

        GsMessage gsMsg = new GsMessage(reqMdl);

        if (wdrType == GSConstWebmail.DIR_TYPE_RECEIVE) {
            wdrName = gsMsg.getMessage("cmn.receive");
        } else if (wdrType == GSConstWebmail.DIR_TYPE_SENDED) {
            wdrName = gsMsg.getMessage("wml.19");
        } else if (wdrType == GSConstWebmail.DIR_TYPE_NOSEND) {
            wdrName = gsMsg.getMessage("wml.211");
        } else if (wdrType == GSConstWebmail.DIR_TYPE_DRAFT) {
            wdrName = gsMsg.getMessage("cmn.draft");
        } else if (wdrType == GSConstWebmail.DIR_TYPE_DUST) {
            wdrName = gsMsg.getMessage("cmn.trash");
        } else if (wdrType == GSConstWebmail.DIR_TYPE_SPAM) {
            wdrName = gsMsg.getMessage("wml.212");
        } else if (wdrType == GSConstWebmail.DIR_TYPE_STORAGE) {
            wdrName = gsMsg.getMessage("cmn.strage");
        }
        return wdrName;
    }

    /**
     * <p>Create WML_DIRECTORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlDirectoryModel
     * @throws SQLException SQL実行例外
     */
    private WmlDirectoryModel __getWmlDirectoryFromRs(ResultSet rs) throws SQLException {
        WmlDirectoryModel bean = new WmlDirectoryModel();
        bean.setWdrSid(rs.getInt("WDR_SID"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWdrName(rs.getString("WDR_NAME"));
        bean.setWdrType(rs.getInt("WDR_TYPE"));
        bean.setWdrDefault(rs.getInt("WDR_DEFAULT"));
        bean.setWdrView(rs.getInt("WDR_VIEW"));
        return bean;
    }
}
