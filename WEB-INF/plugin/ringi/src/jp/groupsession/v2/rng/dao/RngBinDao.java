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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rng.model.RngBinModel;

/**
 * <p>RNG_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngBinDao.class);

    /**
     * <p>Default Constructor
     */
    public RngBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngBinDao(Connection con) {
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
            sql.addSql("drop table RNG_BIN");

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
            sql.addSql(" create table RNG_BIN (");
            sql.addSql("   RNG_SID NUMBER(10,0) not null,");
            sql.addSql("   BIN_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   RKS_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (RNG_SID,BIN_SID)");
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
     * <p>Insert RNG_BIN Data Bindding JavaBean
     * @param bean RNG_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_BIN(");
            sql.addSql("   RNG_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RKS_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRngSid());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRksSid());
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
     * <p>Update RNG_BIN Data Bindding JavaBean
     * @param bean RNG_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int update(RngBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_BIN");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            //where
            sql.addIntValue(bean.getRngSid());
            sql.addLongValue(bean.getBinSid());

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
     * <p>指定した稟議SIDに関連するバイナリSIDを返す
     * @param rngSid 稟議SID
     * @return List in RNG_BINModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> selectBinList(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SID,");
            sql.addSql("   BIN_SID as BIN,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_BIN");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("BIN"));
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
     * <p>Select RNG_BIN All Data
     * @return List in RNG_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<RngBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList <RngBinModel> ret = new ArrayList <RngBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RKS_SID");
            sql.addSql(" from ");
            sql.addSql("   RNG_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngBinFromRs(rs));
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
     * <p>Select RNG_BIN All Data
     * @param rksSid 経路情報
     * @return List in RNG_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<RngBinModel> getBinList(int rksSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList <RngBinModel> ret = new ArrayList <RngBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RKS_SID ");
            sql.addSql(" from ");
            sql.addSql("   RNG_BIN");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID = ? ");


            sql.addIntValue(rksSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngBinFromRs(rs));
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
     * <p>Select RNG_BIN
     * @param bean RNG_BIN Model
     * @return RNG_BINModel
     * @throws SQLException SQL実行例外
     */
    public RngBinModel select(RngBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RKS_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_BIN");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRngSid());
            sql.addLongValue(bean.getBinSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngBinFromRs(rs);
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
     * <br>[機  能] 指定されたバイナリSIDが稟議の添付ファイルのもかチェックする。
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param binSid バイナリSID
     * @return RNG_BINModel
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckRngTemp(int rngSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   RNG_BIN");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt > 0;
    }

    /**
     * <br>[機  能] 指定されたバイナリSID一覧の内で稟議で使用可能なものを抽出する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid  稟議SID
     * @param binSids バイナリSID一覧
     * @return 使用可能なバイナリSID一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Long> getCanRngTempList(int rngSid, List<Long> binSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<Long> ret = new ArrayList<Long>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_BIN");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");

            sql.addIntValue(rngSid);

            sql.addSql(" and");
            sql.addSql("   BIN_SID in (");

            StringBuffer strBuf = new StringBuffer();
            for (Long binSid : binSids) {
                if (strBuf.length() > 0) {
                    strBuf.append(",");
                }
                strBuf.append("?");
                sql.addLongValue(binSid);
            }
            sql.addSql("     " + strBuf.toString());
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret.add(rs.getLong("BIN_SID"));
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
     * <br>[機  能] 指定した稟議情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSidList 稟議SID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalFileSize(List<Integer> rngSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long fileSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   RNG_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   RNG_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   RNG_BIN.RNG_SID in (");

            for (int idx = 0; idx < rngSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(rngSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return fileSize;
    }

    /**
     * <br>[機  能] 稟議添付情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int delete(int rngSid) throws SQLException {

        return delete(rngSid, -1);
    }

    /**
     * <br>[機  能] 稟議添付情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userSid ユーザSID(-1が指定された場合は削除条件に含めない)
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int rngSid, int userSid) throws SQLException {
        return delete(rngSid, userSid, -1);
    }
    /**
     * <br>[機  能] 稟議添付情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param userSid ユーザSID(-1が指定された場合は削除条件に含めない)
     * @param rksSid 経路ステップSID(-1が指定された場合は削除条件に含めない)
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int rngSid, int userSid, int rksSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_BIN");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            sql.addIntValue(rngSid);
            if (userSid >= 0) {
                sql.addSql(" and");
                sql.addSql("   USR_SID=?");
                sql.addIntValue(userSid);
            }
            if (rksSid >= 0) {
                sql.addSql(" and");
                sql.addSql("   RKS_SID=?");
                sql.addIntValue(rksSid);
            }

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
     * <p>Create RNG_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngBinModel
     * @throws SQLException SQL実行例外
     */
    private RngBinModel __getRngBinFromRs(ResultSet rs) throws SQLException {
        RngBinModel bean = new RngBinModel();
        bean.setRngSid(rs.getInt("RNG_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRksSid(rs.getInt("RKS_SID"));
        return bean;
    }
}
