package jp.groupsession.v2.sml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.model.SmlBinModel;
import jp.groupsession.v2.sml.model.SmlSmeisModel;

/**
 * <p>SmlBinDao Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlBinDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlBinDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert SML_BIN Data Bindding JavaBean
     * @param bean SML_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_BIN(");
            sql.addSql("   SML_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmlSid());
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
     * <br>[機  能] ショートメール添付情報を登録する
     * <br>[解  説] Listを渡し複数登録する
     * <br>[備  考]
     * @param bean SmlSmeisModel
     * @param binList List in String
     * @throws SQLException SQL実行例外
     */
    public void insertSmlBin(SmlSmeisModel bean, List<String> binList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (binList.size() < 1) {
            return;
        }

        try {

            for (int i = 0; i < binList.size(); i++) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" SML_BIN(");
                sql.addSql("   SML_SID,");
                sql.addSql("   BIN_SID");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getSmsSid());
                sql.addLongValue(NullDefault.getLong(binList.get(i), 0));

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update SML_BIN Data Bindding JavaBean
     * @param bean SML_BIN Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   SML_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getSmlSid());
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
     * <br>[機  能] 選択されたショートメール紐付いている添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smlSid ショートメールSID
     * @return List in SmlBinModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlBinModel> getBinList(int smlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlBinModel> ret = new ArrayList<SmlBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_BIN");
            sql.addSql(" where ");
            sql.addSql("   SML_SID = ?");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smlSid);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getSmlBinFromRs(rs));
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
    public List<SmlBinModel> getBinList(String[] smlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlBinModel> ret = new ArrayList<SmlBinModel>();
        con = getCon();

        if (smlSid == null) {
            return ret;
        }
        if (smlSid.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_BIN");
            sql.addSql(" where ");
            sql.addSql("   SML_SID in (");

            for (int i = 0; i < smlSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(smlSid[i], 0));

                if (i < smlSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getSmlBinFromRs(rs));
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
     * <br>[機  能] 添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return List in SmlBinModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlBinModel> ret = new ArrayList<SmlBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_BIN");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getSmlBinFromRs(rs));
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
     * <p>Select SML_BIN
     * @param bean SML_BIN Model
     * @return SML_BINModel
     * @throws SQLException SQL実行例外
     */
    public SmlBinModel select(SmlBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   SML_BIN");
            sql.addSql(" where ");
            sql.addSql("   SML_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmlSid());
            sql.addLongValue(bean.getBinSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlBinFromRs(rs);
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
     * <br>[機  能] ショートメールSIDから添付情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smsSid ショートメールSID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteSmlBin(int smsSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_BIN");
            sql.addSql(" where ");
            sql.addSql("   SML_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smsSid);
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
     * <br>[機  能] ショートメールSID(複数)から添付情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smsSidList ショートメールSID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteSmlBin(List<String> smsSidList) throws SQLException {

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
            sql.addSql("   SML_BIN");
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
            sql.addSql("   SML_BIN");
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
            sql.addSql("   SML_BIN");
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
     * <br>[機  能] ショートメールSIDから添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid ショートメールSID
     * @return ArrayList in CmnBinfModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnBinfModel> getFileList(int mailSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION as BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    SML_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    SML_BIN.SML_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    SML_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  order by");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME");

            sql.addIntValue(mailSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel retMdl = new CmnBinfModel();
                retMdl.setBinSid(rs.getLong("BIN_SID"));
                retMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                retMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                retMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                retMdl.setBinFileSize(size);
                retMdl.setBinFileSizeDsp(strSize);
                ret.add(retMdl);
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
            sql.addSql("   SML_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   SML_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   SML_BIN.SML_SID in (");

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
     * <br>[機  能] 選択されたショートメールに紐付いている添付ファイル名を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid ショートメールSID
     * @return ret 紐付いている添付ファイルの名称リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getBinFileNameList(int mailSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<String> ret = new ArrayList<String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME as fileName");
            sql.addSql(" from ");
            sql.addSql("   SML_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   SML_BIN.SML_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SML_BIN.BIN_SID = CMN_BINF.BIN_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(mailSid);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("fileName"));
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
     * <br>[機  能] ショートメール添付ファイルの存在の有無を判定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smlSid ショートメールSID
     * @param sacSid アカウントSID
     * @param binSid バイナリSID
     * @return count 0:存在しない 1以上:存在する
     * @throws SQLException SQL実行例外
     */
    public int getBinFileCnt(int smlSid, int sacSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) CNT ");
            sql.addSql(" from ");
            sql.addSql("   SML_BIN, ");
            sql.addSql("   CMN_BINF, ");
            sql.addSql("   (select count(*) as J_CNT from SML_JMEIS ");
            sql.addSql("      where SMJ_SID = ? ");
            sql.addSql("      and SAC_SID=?) as JCNT, ");
            sql.addSql("   (select count(*) as S_CNT from SML_SMEIS ");
            sql.addSql("      where SMS_SID = ? ");
            sql.addSql("      and SAC_SID=?) as SCNT, ");
            sql.addSql("   (select count(*) as W_CNT from SML_WMEIS ");
            sql.addSql("      where SMW_SID = ? ");
            sql.addSql("      and SAC_SID=?) as WCNT ");
            sql.addSql(" where ");
            sql.addSql("   SML_BIN.BIN_SID = CMN_BINF.BIN_SID ");
            sql.addSql(" and ");
            sql.addSql("   SML_BIN.BIN_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   SML_BIN.SML_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   ( ");
            sql.addSql("   JCNT.J_CNT > 0 ");
            sql.addSql("   or ");
            sql.addSql("   SCNT.S_CNT > 0 ");
            sql.addSql("   or ");
            sql.addSql("   WCNT.W_CNT > 0 ");
            sql.addSql("   ) ");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smlSid);
            sql.addIntValue(sacSid);
            sql.addIntValue(smlSid);
            sql.addIntValue(sacSid);
            sql.addIntValue(smlSid);
            sql.addIntValue(sacSid);
            sql.addLongValue(binSid);
            sql.addIntValue(smlSid);
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
     *
     * <br>[機  能] 指定したメールSIDの添付ファイル数を取得する
     * <br>[解  説] メールSIDによるマップ型を返す
     * <br>[備  考]
     * @param smlSids 指定したメールSID
     * @return メールSIDによる添付ファイル数マップ
     * @throws SQLException SQL実行時例外
     */
    public Map<Integer, Integer> getBinFileCnt(Collection<Integer> smlSids) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        Map<Integer, Integer> ret = new HashMap<>();

        List<Set<Integer>> roopSet = new ArrayList<>();
        Set<Integer> sids = new HashSet<>();
        roopSet.add(sids);
        int cnt = 0;
        for (int sid : smlSids) {
            sids.add(sid);
            cnt++;
            if (cnt % 500 == 0) {
                sids = new HashSet<>();
                roopSet.add(sids);
            }
        }
        for (Set<Integer> sels : roopSet) {
            if (sels.size() == 0) {
                continue;
            }

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql("        select ");
                sql.addSql("          SML_SID, ");
                sql.addSql("          count(*) as CT ");
                sql.addSql("        from ");
                sql.addSql("          SML_BIN ");
                sql.addSql("        where");
                sql.addSql("          SML_SID in ( ");
                sql.addSql(
                        sels.stream()
                            .map(sid -> sid.toString())
                            .collect(Collectors.joining(","))
                          );
                sql.addSql("          )");
                sql.addSql("        group by ");
                sql.addSql("          SML_SID ");
                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);

                log__.info(sql.toLogString());

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    ret.put(rs.getInt("SML_SID"),
                            rs.getInt("CT"));
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }
        }
        return ret;


    }

    /**
     * <br>[機  能] メールSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリSID
     * @return メールSID
     * @throws SQLException SQL実行例外
     */
    public int getSmlSid(long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int smlSid = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_SID");
            sql.addSql(" from");
            sql.addSql("   SML_BIN");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID=?");

            sql.addLongValue(binSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                smlSid = rs.getInt("SML_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return smlSid;
    }

    /**
     * <p>Create SML_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlBinModel
     * @throws SQLException SQL実行例外
     */
    private SmlBinModel __getSmlBinFromRs(ResultSet rs) throws SQLException {
        SmlBinModel bean = new SmlBinModel();
        bean.setSmlSid(rs.getInt("SML_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }
}