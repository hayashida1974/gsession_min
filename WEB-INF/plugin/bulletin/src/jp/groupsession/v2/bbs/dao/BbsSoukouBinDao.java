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
import jp.groupsession.v2.bbs.model.BbsSoukouBinModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <p>BBS_SOUKOU_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BbsSoukouBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsSoukouBinDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsSoukouBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsSoukouBinDao(Connection con) {
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
            sql.addSql("drop table BBS_SOUKOU_BIN");

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
            sql.addSql(" create table BBS_SOUKOU_BIN (");
            sql.addSql("   BSK_SID integer not null,");
            sql.addSql("   BIN_SID bigint not null,");
            sql.addSql("   primary key (BSK_SID,BIN_SID)");
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
     * <p>Insert BBS_SOUKOU_BIN Data Bindding JavaBean
     * @param bean BBS_SOUKOU_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsSoukouBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_SOUKOU_BIN(");
            sql.addSql("   BSK_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBskSid());
            sql.addLongValue(bean.getBinSid());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update BBS_SOUKOU_BIN Data Bindding JavaBean
     * @param bean BBS_SOUKOU_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(BbsSoukouBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_SOUKOU_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   BSK_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getBskSid());
            sql.addLongValue(bean.getBinSid());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select BBS_SOUKOU_BIN All Data
     * @return List in BBS_SOUKOU_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsSoukouBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsSoukouBinModel> ret = new ArrayList<BbsSoukouBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BSK_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   BBS_SOUKOU_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsSoukouBinFromRs(rs));
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
     * <p>Select BBS_SOUKOU_BIN
     * @param bskSid BSK_SID
     * @param binSid BIN_SID
     * @return BBS_SOUKOU_BINModel
     * @throws SQLException SQL実行例外
     */
    public BbsSoukouBinModel select(int bskSid, long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsSoukouBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BSK_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU_BIN");
            sql.addSql(" where ");
            sql.addSql("   BSK_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
            sql.addLongValue(binSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsSoukouBinFromRs(rs);
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
     * <p>Delete BBS_SOUKOU_BIN
     * @param bskSid BSK_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int bskSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU_BIN");
            sql.addSql(" where ");
            sql.addSql("   BSK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }


    /**
     * <p>Create BBS_SOUKOU_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsSoukouBinModel
     * @throws SQLException SQL実行例外
     */
    private BbsSoukouBinModel __getBbsSoukouBinFromRs(ResultSet rs) throws SQLException {
        BbsSoukouBinModel bean = new BbsSoukouBinModel();
        bean.setBskSid(rs.getInt("BSK_SID"));
        bean.setBinSid(rs.getInt("BIN_SID"));
        return bean;
    }



    /**
     * <p>指定された草稿SIDと添付ファイルバイナリSIDの組み合わせが存在するかを確認する
     * @param bskSid 草稿SID
     * @param binSid 添付ファイルバイナリSID
     * @param usrSid ユーザSID
     * @return 結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existBbsSoukouTmp(int bskSid, Long binSid,
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
            sql.addSql("   BBS_SOUKOU_BIN.BSK_SID as BSK_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU_BIN");
            sql.addSql(" left join ");
            sql.addSql("   BBS_SOUKOU");
            sql.addSql(" on ");
            sql.addSql("   BBS_SOUKOU_BIN.BSK_SID = BBS_SOUKOU.BSK_SID");
            sql.addSql(" where ");
            sql.addSql("   BBS_SOUKOU_BIN.BSK_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BBS_SOUKOU_BIN.BIN_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BBS_SOUKOU.BSK_AUID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
            sql.addLongValue(binSid);
            sql.addIntValue(usrSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <p>指定された草稿SIDとユーザSIDの組み合わせが存在するかを確認する
     * @param bskSid 草稿SID
     * @param usrSid ユーザSID
     * @return 結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existBbsSoukouTmp(int bskSid,
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
            sql.addSql("   BBS_SOUKOU_BIN.BSK_SID as BSK_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU_BIN");
            sql.addSql(" left join ");
            sql.addSql("   BBS_SOUKOU");
            sql.addSql(" on ");
            sql.addSql("   BBS_SOUKOU_BIN.BSK_SID = BBS_SOUKOU.BSK_SID");
            sql.addSql(" where ");
            sql.addSql("   BBS_SOUKOU_BIN.BSK_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BBS_SOUKOU.BSK_AUID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
            sql.addIntValue(usrSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <br>[機  能] 指定した草稿のバイナリーファイル情報を論理削除する
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
            sql.addSql("        BBS_SOUKOU_BIN,");
            sql.addSql("        BBS_SOUKOU");
            sql.addSql("      where");
            sql.addSql("        BBS_SOUKOU.BSK_SID = ?");
            sql.addSql("      and");
            sql.addSql("        BBS_SOUKOU_BIN.BSK_SID = BBS_SOUKOU.BSK_SID");
            sql.addSql("    )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(bskSid);

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <br>[機  能] 指定した草稿のバイナリーファイル情報をユーザSIDから論理削除する
     * <br>[解  説] 状態区分を9:削除に更新する
     * <br>[備  考]
     * @param userSid 弓座SID
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
            sql.addSql("        BBS_SOUKOU_BIN,");
            sql.addSql("        BBS_SOUKOU");
            sql.addSql("      where");
            sql.addSql("        BBS_SOUKOU.BSK_AUID = ?");
            sql.addSql("      and");
            sql.addSql("        BBS_SOUKOU_BIN.BSK_SID = BBS_SOUKOU.BSK_SID");
            sql.addSql("    )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(userSid);

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return count;
    }

    /**
     * <p>Delete BBS_SOUKOU_BIN
     * @param bskSid BSK_SID
     * @throws SQLException SQL実行例外
     */
    public void deleteSoukouBin(int bskSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU_BIN");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select");
            sql.addSql("        BIN_SID");
            sql.addSql("      from");
            sql.addSql("        BBS_SOUKOU_BIN,");
            sql.addSql("        BBS_SOUKOU");
            sql.addSql("      where");
            sql.addSql("        BBS_SOUKOU.BSK_SID = ?");
            sql.addSql("      and");
            sql.addSql("        BBS_SOUKOU_BIN.BSK_SID = BBS_SOUKOU.BSK_SID");
            sql.addSql("    )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bskSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
    public void deleteSoukouBinUserSid(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_SOUKOU_BIN");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select");
            sql.addSql("        BIN_SID");
            sql.addSql("      from");
            sql.addSql("        BBS_SOUKOU_BIN,");
            sql.addSql("        BBS_SOUKOU");
            sql.addSql("      where");
            sql.addSql("        BBS_SOUKOU.BSK_AUID = ?");
            sql.addSql("      and");
            sql.addSql("        BBS_SOUKOU_BIN.BSK_SID = BBS_SOUKOU.BSK_SID");
            sql.addSql("    )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }


    /**
     * <br>[機  能] 指定された投稿の添付ファイル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bskSid 草稿SID
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> getSoukouTmpFileList(int bskSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List < CmnBinfModel > ret = new ArrayList < CmnBinfModel >();
        CommonBiz cmnBiz = new CommonBiz();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION as BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    BBS_SOUKOU_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    BBS_SOUKOU_BIN.BSK_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_SOUKOU_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addSql("  order by");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME");

            sql.addIntValue(bskSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel binMdl = new CmnBinfModel();
                binMdl.setBinSid(rs.getLong("BIN_SID"));
                binMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                binMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                binMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                long lngSize = rs.getInt("BIN_FILE_SIZE");
                binMdl.setBinFileSize(lngSize);
                String strSize = cmnBiz.getByteSizeString(lngSize);
                binMdl.setBinFileSizeDsp(strSize);
                ret.add(binMdl);
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
