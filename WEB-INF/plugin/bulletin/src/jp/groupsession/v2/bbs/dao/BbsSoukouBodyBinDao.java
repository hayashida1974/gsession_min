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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bbs.model.BbsSoukouBodyBinModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <p>BBS_SOUKOU_BODY_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BbsSoukouBodyBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsSoukouBodyBinDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsSoukouBodyBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsSoukouBodyBinDao(Connection con) {
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
            sql.addSql("drop table BBS_SOUKOU_BODY_BIN");

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
            sql.addSql(" create table BBS_SOUKOU_BODY_BIN (");
            sql.addSql("   BSK_SID integer not null,");
            sql.addSql("   BSB_FILE_SID integer not null,");
            sql.addSql("   BIN_SID bigint not null,");
            sql.addSql("   primary key (BSK_SID,BSB_FILE_SID)");
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
     * <p>Insert BBS_SOUKOU_BODY_BIN Data Bindding JavaBean
     * @param bean BBS_SOUKOU_BODY_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsSoukouBodyBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_SOUKOU_BODY_BIN(");
            sql.addSql("   BSK_SID,");
            sql.addSql("   BSB_FILE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBskSid());
            sql.addIntValue(bean.getBsbFileSid());
            sql.addLongValue(bean.getBinSid());
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
     * <p>Update BBS_SOUKOU_BODY_BIN Data Bindding JavaBean
     * @param bean BBS_SOUKOU_BODY_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BbsSoukouBodyBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_SOUKOU_BODY_BIN");
            sql.addSql(" set ");
            sql.addSql("   BIN_SID=?");
            sql.addSql(" where ");
            sql.addSql("   BSK_SID=?");
            sql.addSql(" and");
            sql.addSql("   BSB_FILE_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getBinSid());
            //where
            sql.addIntValue(bean.getBskSid());
            sql.addIntValue(bean.getBsbFileSid());

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
     * <p>Select BBS_SOUKOU_BODY_BIN All Data
     * @return List in BBS_SOUKOU_BODY_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsSoukouBodyBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BbsSoukouBodyBinModel> ret = new ArrayList<BbsSoukouBodyBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BSK_SID,");
            sql.addSql("   BSB_FILE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   BBS_SOUKOU_BODY_BIN");
            sql.addSql(" order by  ");
            sql.addSql("   BSB_FILE_SID asc");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsSoukouBodyBinFromRs(rs));
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
     * <p>Select BBS_SOUKOU_BODY_BIN All Data
     * @return List in BBS_SOUKOU_BODY_BINModel
     * @param bskSid 草稿SID
     * @throws SQLException SQL実行例外
     */
    public List<BbsSoukouBodyBinModel> select(int bskSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BbsSoukouBodyBinModel> ret = new ArrayList<BbsSoukouBodyBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BSK_SID,");
            sql.addSql("   BSB_FILE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   BBS_SOUKOU_BODY_BIN");
            sql.addSql(" where ");
            sql.addSql("   BSK_SID = ?");
            sql.addSql(" order by  ");
            sql.addSql("   BSB_FILE_SID asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsSoukouBodyBinFromRs(rs));
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
     * <p>Select BBS_SOUKOU_BODY_BIN
     * @param bskSid BSK_SID
     * @param bsbFileSid BSB_FILE_SID
     * @return BBS_SOUKOU_BODY_BINModel
     * @throws SQLException SQL実行例外
     */
    public BbsSoukouBodyBinModel select(int bskSid, int bsbFileSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsSoukouBodyBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BSK_SID,");
            sql.addSql("   BSB_FILE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU_BODY_BIN");
            sql.addSql(" where ");
            sql.addSql("   BSK_SID=?");
            sql.addSql(" and");
            sql.addSql("   BSB_FILE_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
            sql.addIntValue(bsbFileSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsSoukouBodyBinFromRs(rs);
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
     * <p>Delete BBS_SOUKOU_BODY_BIN
     * @param bskSid BSK_SID
     * @param bsbFileSid BSB_FILE_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int bskSid, int bsbFileSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU_BODY_BIN");
            sql.addSql(" where ");
            sql.addSql("   BSK_SID=?");
            sql.addSql(" and");
            sql.addSql("   BSB_FILE_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
            sql.addIntValue(bsbFileSid);

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
     * <p>Create BBS_SOUKOU_BODY_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsSoukouBodyBinModel
     * @throws SQLException SQL実行例外
     */
    private BbsSoukouBodyBinModel __getBbsSoukouBodyBinFromRs(ResultSet rs) throws SQLException {
        BbsSoukouBodyBinModel bean = new BbsSoukouBodyBinModel();
        bean.setBskSid(rs.getInt("BSK_SID"));
        bean.setBsbFileSid(rs.getInt("BSB_FILE_SID"));
        bean.setBinSid(rs.getInt("BIN_SID"));
        return bean;
    }

    /**
     * <p>指定された草稿SIDと草稿本文ファイルSIDの組み合わせが存在するかを確認する
     * @param bskSid 草稿SID
     * @param bsbFileSid ファイルSID
     * @param usrSid ユーザSID
     * @return 結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existBbsSoukouBodyTmp(
            int bskSid,
            int bsbFileSid,
            int usrSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BBS_SOUKOU_BODY_BIN.BSK_SID as BSK_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU_BODY_BIN");
            sql.addSql(" left join ");
            sql.addSql("   BBS_SOUKOU");
            sql.addSql(" on ");
            sql.addSql("   BBS_SOUKOU_BODY_BIN.BSK_SID = BBS_SOUKOU.BSK_SID");
            sql.addSql(" where ");
            sql.addSql("   BBS_SOUKOU.BSK_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BBS_SOUKOU.BSK_AUID=?");
            sql.addSql(" and ");
            sql.addSql("   BBS_SOUKOU_BODY_BIN.BSB_FILE_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(bsbFileSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            ret = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }


    /**
     * <br>[機  能] 指定した草稿本文のバイナリーファイル情報を論理削除する
     * <br>[解  説] 状態区分を9:削除に更新する
     * <br>[備  考]
     * @param bskSid 草稿SID
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int updateBinfForSoukou(int bskSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  update");
            sql.addSql("    CMN_BINF");
            sql.addSql("  set");
            sql.addSql("    BIN_JKBN = ?");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select");
            sql.addSql("        BIN_SID");
            sql.addSql("      from");
            sql.addSql("        BBS_SOUKOU_BODY_BIN,");
            sql.addSql("        BBS_SOUKOU");
            sql.addSql("      where");
            sql.addSql("        BBS_SOUKOU.BSK_SID = ?");
            sql.addSql("      and");
            sql.addSql("        BBS_SOUKOU_BODY_BIN.BSK_SID = BBS_SOUKOU.BSK_SID");
            sql.addSql("    )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(bskSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <br>[機  能] ユーザSIDから指定した草稿本文のバイナリーファイル情報を論理削除する
     * <br>[解  説] 状態区分を9:削除に更新する
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int updateBinfForSoukouUserSid(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  update");
            sql.addSql("    CMN_BINF");
            sql.addSql("  set");
            sql.addSql("    BIN_JKBN = ?");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select");
            sql.addSql("        BIN_SID");
            sql.addSql("      from");
            sql.addSql("        BBS_SOUKOU_BODY_BIN,");
            sql.addSql("        BBS_SOUKOU");
            sql.addSql("      where");
            sql.addSql("        BBS_SOUKOU.BSK_AUID = ?");
            sql.addSql("      and");
            sql.addSql("        BBS_SOUKOU_BODY_BIN.BSK_SID = BBS_SOUKOU.BSK_SID");
            sql.addSql("    )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <p>Delete BBS_SOUKOU_BODY_BIN
     * @param bskSid BSK_SID
     * @throws SQLException SQL実行例外
     */
    public void deleteSoukouBodyBin(int bskSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU_BODY_BIN");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select");
            sql.addSql("        BIN_SID");
            sql.addSql("      from");
            sql.addSql("        BBS_SOUKOU_BODY_BIN,");
            sql.addSql("        BBS_SOUKOU");
            sql.addSql("      where");
            sql.addSql("        BBS_SOUKOU.BSK_SID = ?");
            sql.addSql("      and");
            sql.addSql("        BBS_SOUKOU_BODY_BIN.BSK_SID = BBS_SOUKOU.BSK_SID");
            sql.addSql("    )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
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
     * <p>ユーザSIDから添付情報を削除する
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteSoukouBodyBinUserSid(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU_BODY_BIN");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select");
            sql.addSql("        BIN_SID");
            sql.addSql("      from");
            sql.addSql("        BBS_SOUKOU_BODY_BIN,");
            sql.addSql("        BBS_SOUKOU");
            sql.addSql("      where");
            sql.addSql("        BBS_SOUKOU.BSK_AUID = ?");
            sql.addSql("      and");
            sql.addSql("        BBS_SOUKOU_BODY_BIN.BSK_SID = BBS_SOUKOU.BSK_SID");
            sql.addSql("    )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
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
     * <p>Select BBS_SOUKOU_BODY_BIN All Data
     * @return List in BBS_SOUKOU_BODY_BINModel
     * @param bskSid 草稿SID
     * @throws SQLException SQL実行例外
     */
    public List<String> selectFileName(int bskSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BBS_SOUKOU_BODY_BIN.BSK_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME");
            sql.addSql(" from ");
            sql.addSql("   CMN_BINF");
            sql.addSql(" left join ");
            sql.addSql("   BBS_SOUKOU_BODY_BIN");
            sql.addSql(" on ");
            sql.addSql("   CMN_BINF.BIN_SID = BBS_SOUKOU_BODY_BIN.BIN_SID");
            sql.addSql(" where ");
            sql.addSql("   BBS_SOUKOU_BODY_BIN.BSK_SID = ?");
            sql.addSql(" order by  ");
            sql.addSql("   BSB_FILE_SID asc");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("BIN_FILE_NAME"));
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
     * <p>Select BBS_SOUKOU_BODY_BIN All Data
     * @return List in BBS_SOUKOU_BODY_BINModel
     * @param bskSid 草稿SID
     * @param fileId ファイル番号
     * @throws SQLException SQL実行例外
     */
    public String selectFileName(int bskSid, int fileId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = "";
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BBS_SOUKOU_BODY_BIN.BSK_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME");
            sql.addSql(" from ");
            sql.addSql("   CMN_BINF");
            sql.addSql(" left join ");
            sql.addSql("   BBS_SOUKOU_BODY_BIN");
            sql.addSql(" on ");
            sql.addSql("   CMN_BINF.BIN_SID = BBS_SOUKOU_BODY_BIN.BIN_SID");
            sql.addSql(" where ");
            sql.addSql("   BBS_SOUKOU_BODY_BIN.BSK_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   BBS_SOUKOU_BODY_BIN.BSB_FILE_SID = ?");
            sql.addSql(" order by  ");
            sql.addSql("   BSB_FILE_SID asc");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
            sql.addIntValue(fileId);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getString("BIN_FILE_NAME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }



}
