package jp.groupsession.v2.sml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sml.model.SmlBodyBinModel;

/**
 * <p>SML_BODY_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SmlBodyBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlBodyBinDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlBodyBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlBodyBinDao(Connection con) {
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
            sql.addSql("drop table SML_BODY_BIN");

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
            sql.addSql(" create table SML_BODY_BIN (");
            sql.addSql("   SML_SID integer not null,");
            sql.addSql("   SBB_SID integer not null,");
            sql.addSql("   BIN_SID bigint not null,");
            sql.addSql("   primary key (SML_SID, SBB_SID)");
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
     * <p>Insert SML_BODY_BIN Data Bindding JavaBean
     * @param bean SML_BODY_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlBodyBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_BODY_BIN(");
            sql.addSql("   SML_SID,");
            sql.addSql("   SBB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmlSid());
            sql.addIntValue(bean.getSbbSid());
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
     * <p>Update SML_BODY_BIN Data Bindding JavaBean
     * @param bean SML_BODY_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlBodyBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_BODY_BIN");
            sql.addSql(" set ");
            sql.addSql("   BIN_SID=?");
            sql.addSql(" where ");
            sql.addSql("   SML_SID=?");
            sql.addSql(" and");
            sql.addSql("   SBB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getBinSid());
            //where
            sql.addIntValue(bean.getSmlSid());
            sql.addLongValue(bean.getSbbSid());

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
     * <p>Select SML_BODY_BIN
     * @return SML_BODY_BINModel List
     * @throws SQLException SQL実行例外
     */
    public List<SmlBodyBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlBodyBinModel> ret = new ArrayList<SmlBodyBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_SID,");
            sql.addSql("   SBB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   SML_BODY_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlBodyBinFromRs(rs));
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
     * <p>Select SML_BODY_BIN
     * @param smlSid SML_SID
     * @param sbbSid SBB_SID
     * @return SML_BODY_BINModel
     * @throws SQLException SQL実行例外
     */
    public SmlBodyBinModel select(int smlSid, int sbbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlBodyBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_SID,");
            sql.addSql("   SBB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   SML_BODY_BIN");
            sql.addSql(" where ");
            sql.addSql("   SML_SID=?");
            sql.addSql(" and");
            sql.addSql("   SBB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smlSid);
            sql.addIntValue(sbbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlBodyBinFromRs(rs);
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
     * <p>ファイルDLにて閲覧権限があるユーザのみがBIN_SIDを取得する
     * @param smlSid SML_SID
     * @param sbbSid SBB_SID
     * @param usrSid ユーザSID
     * @return SML_BODY_BINModel
     * @throws SQLException SQL実行例外
     */
    public Long select(int smlSid, int sbbSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Long ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    BIN_SID");
            sql.addSql("  from");
            sql.addSql("    SML_BODY_BIN");
            sql.addSql("  where");
            sql.addSql("    SML_SID = (select");
            sql.addSql("                 SMS_SID");
            sql.addSql("               from");
            sql.addSql("                 SML_SMEIS");
            sql.addSql("               where");
            sql.addSql("                 SMS_SID = ?");
            sql.addSql("               and");
            sql.addSql("                 SAC_SID in (select");
            sql.addSql("                               SAC_SID");
            sql.addSql("                             from");
            sql.addSql("                               SML_ACCOUNT_USER");
            sql.addSql("                             where");
            sql.addSql("                               USR_SID = ?");
            sql.addSql("                             or");
            sql.addSql("                               GRP_SID in (select");
            sql.addSql("                                             GRP_SID");
            sql.addSql("                                           from");
            sql.addSql("                                             CMN_BELONGM");
            sql.addSql("                                           where");
            sql.addSql("                                             USR_SID = ?))");
            sql.addSql("               union");
            sql.addSql("               select");
            sql.addSql("                 SMJ_SID");
            sql.addSql("               from");
            sql.addSql("                 SML_JMEIS");
            sql.addSql("               where");
            sql.addSql("                 SMJ_SID = ?");
            sql.addSql("               and");
            sql.addSql("                 SAC_SID in (select");
            sql.addSql("                               SAC_SID");
            sql.addSql("                             from");
            sql.addSql("                               SML_ACCOUNT_USER");
            sql.addSql("                             where");
            sql.addSql("                               USR_SID = ?");
            sql.addSql("                             or");
            sql.addSql("                               GRP_SID in (select");
            sql.addSql("                                             GRP_SID");
            sql.addSql("                                           from");
            sql.addSql("                                             CMN_BELONGM");
            sql.addSql("                                           where");
            sql.addSql("                                             USR_SID = ?))");
            sql.addSql("               union");
            sql.addSql("               select");
            sql.addSql("                 SMW_SID");
            sql.addSql("               from");
            sql.addSql("                 SML_WMEIS");
            sql.addSql("               where");
            sql.addSql("                 SMW_SID = ?");
            sql.addSql("               and");
            sql.addSql("                 SAC_SID in (select");
            sql.addSql("                               SAC_SID");
            sql.addSql("                             from");
            sql.addSql("                               SML_ACCOUNT_USER");
            sql.addSql("                             where");
            sql.addSql("                               USR_SID = ?");
            sql.addSql("                             or");
            sql.addSql("                               GRP_SID in (select");
            sql.addSql("                                             GRP_SID");
            sql.addSql("                                           from");
            sql.addSql("                                             CMN_BELONGM");
            sql.addSql("                                           where");
            sql.addSql("                                             USR_SID = ?)))");
            sql.addSql("  and");
            sql.addSql("    SBB_SID = ?");
            

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smlSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(smlSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(smlSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(sbbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("BIN_SID");
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
     * <br>[機  能] 選択されたショートメール紐付いている添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smlSid ショートメールSID
     * @return List in SmlBinModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlBodyBinModel> getBinList(int smlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlBodyBinModel> ret = new ArrayList<SmlBodyBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_SID,");
            sql.addSql("   SBB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_BODY_BIN");
            sql.addSql(" where ");
            sql.addSql("   SML_SID = ?");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smlSid);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getSmlBodyBinFromRs(rs));
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
     * <p>Delete SML_BODY_BIN
     * @param smlSid SML_SID
     * @param sbbSid SBB_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int smlSid, int sbbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_BODY_BIN");
            sql.addSql(" where ");
            sql.addSql("   SML_SID=?");
            sql.addSql(" and");
            sql.addSql("   SBB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smlSid);
            sql.addLongValue(sbbSid);

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
     * <p>Delete SML_BODY_BIN
     * @param smlSid SML_SID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int smlSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_BODY_BIN");
            sql.addSql(" where ");
            sql.addSql("   SML_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smlSid);

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
     * <br>[機  能] ショートメールSID(複数)から添付情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smsSidList ショートメールSID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(List<String> smsSidList) throws SQLException {

        if (smsSidList == null || smsSidList.size() <= 0) {
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
            sql.addSql("   SML_BODY_BIN");
            sql.addSql(" where ");
            if (smsSidList.size() == 1) {
                sql.addSql("   SML_SID = ?");
                sql.addIntValue(Integer.parseInt(smsSidList.get(0)));
            } else {
                sql.addSql("   SML_SID in (");
                for (int idx = 0; idx < smsSidList.size() - 1; idx++) {
                    sql.addSql("     ?,");
                    sql.addIntValue(Integer.parseInt(smsSidList.get(idx)));
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(smsSidList.get(smsSidList.size() - 1)));
                sql.addSql("   )");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>投稿本文添付情報の一括登録を行う
     * @param smlSid 投稿SID
     * @param sbbList ファイルSIDリスト
     * @param bodyBinSidList バイナリSIDの一覧
     * @throws SQLException SQL実行例外
     */
    public void insertSmlBodyBinData(int smlSid, List<String> sbbList, List<String> bodyBinSidList)
                    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into ");
            sql.addSql(" SML_BODY_BIN (");
            sql.addSql("   SML_SID,");
            sql.addSql("   SBB_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values (");
            sql.addSql("   " + smlSid + ",");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            String logString = sql.toLogString();
            pstmt = con.prepareStatement(sql.toSqlString());

            for (int i = 0; i < bodyBinSidList.size(); ++i) {
                pstmt.setInt(1, Integer.parseInt(sbbList.get(i)));
                pstmt.setLong(2, Long.parseLong(bodyBinSidList.get(i)));
                logString = StringUtil.substitute(logString, "?", sbbList.get(i));
                log__.info(logString);
                logString = StringUtil.substitute(logString, "?", bodyBinSidList.get(i));
                log__.info(logString);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
    
    /**
     * <br>[機  能] ショートメールSID(複数)からバイナリSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smsSidList ショートメールSID
     * @return バイナリSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Long> selectBinSidList(List<String> smsSidList) throws SQLException {

        if (smsSidList == null || smsSidList.size() <= 0) {
            return null;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        List<Long> ret = new ArrayList<Long>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   SML_BODY_BIN");
            sql.addSql(" where ");
            if (smsSidList.size() == 1) {
                sql.addSql("   SML_SID = ?");
                sql.addIntValue(Integer.parseInt(smsSidList.get(0)));
            } else {
                sql.addSql("   SML_SID in (");
                for (int idx = 0; idx < smsSidList.size() - 1; idx++) {
                    sql.addSql("     ?,");
                    sql.addIntValue(Integer.parseInt(smsSidList.get(idx)));
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(smsSidList.get(smsSidList.size() - 1)));
                sql.addSql("   )");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
     * <br>[機  能] ショートメールSID(複数)からバイナリSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smsSidList ショートメールSID
     * @return バイナリSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Long> selectBinSidList(String[] smsSidList) throws SQLException {

        if (smsSidList == null || smsSidList.length <= 0) {
            return null;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        List<Long> ret = new ArrayList<Long>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   SML_BODY_BIN");
            sql.addSql(" where ");
            if (smsSidList.length == 1) {
                sql.addSql("   SML_SID = ?");
                sql.addIntValue(Integer.parseInt(smsSidList[0]));
            } else {
                sql.addSql("   SML_SID in (");
                for (int idx = 0; idx < smsSidList.length - 1; idx++) {
                    sql.addSql("     ?,");
                    sql.addIntValue(Integer.parseInt(smsSidList[idx]));
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(smsSidList[smsSidList.length - 1]));
                sql.addSql("   )");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
     * <br>[機  能] 指定したショートメール情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param smlSidList メールSID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalFileSize(List<Integer> smlSidList) throws SQLException {

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
            sql.addSql("   SML_BODY_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   SML_BODY_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   SML_BODY_BIN.SML_SID in (");

            for (int idx = 0; idx < smlSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(smlSidList.get(idx));
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
     * <p>Create SML_BODY_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlBodyBinModel
     * @throws SQLException SQL実行例外
     */
    private SmlBodyBinModel __getSmlBodyBinFromRs(ResultSet rs) throws SQLException {
        SmlBodyBinModel bean = new SmlBodyBinModel();
        bean.setSmlSid(rs.getInt("SML_SID"));
        bean.setSbbSid(rs.getInt("SBB_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }
}
