package jp.groupsession.v2.rng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rng.model.RngFormdataModel;

/**
 * <p>RNG_FORMDATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngFormdataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngFormdataDao.class);

    /**
     * <p>Default Constructor
     */
    public RngFormdataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngFormdataDao(Connection con) {
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
            sql.addSql("drop table RNG_FORMDATA");

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
            sql.addSql(" create table RNG_FORMDATA (");
            sql.addSql("   RNG_SID NUMBER(10,0) not null,");
            sql.addSql("   RFD_SID integer not null,");
            sql.addSql("   RFD_ID varchar(100),");
            sql.addSql("   RFD_ROWNO NUMBER(10,0) not null,");
            sql.addSql("   RFD_VALUE Date,");
            sql.addSql("   RFD_AUID NUMBER(10,0) not null,");
            sql.addSql("   RFD_ADATE varchar(23) not null,");
            sql.addSql("   RFD_EUID NUMBER(10,0) not null,");
            sql.addSql("   RFD_EDATE varchar(23) not null,");
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
     * <p>Insert RNG_FORMDATA Data Bindding JavaBean
     * @param bean RNG_FORMDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngFormdataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_FORMDATA(");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RFD_SID,");
            sql.addSql("   RFD_ROWNO,");
            sql.addSql("   RFD_ID,");
            sql.addSql("   RFD_VALUE,");
            sql.addSql("   RFD_AUID,");
            sql.addSql("   RFD_ADATE,");
            sql.addSql("   RFD_EUID,");
            sql.addSql("   RFD_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRngSid());
            sql.addIntValue(bean.getRfdSid());
            sql.addIntValue(bean.getRfdRowno());
            sql.addStrValue(bean.getRfdId());
            sql.addStrValue(bean.getRfdValue());
            sql.addIntValue(bean.getRfdAuid());
            sql.addDateValue(bean.getRfdAdate());
            sql.addIntValue(bean.getRfdEuid());
            sql.addDateValue(bean.getRfdEdate());
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
     * <p>Insert RNG_FORMDATA Data Bindding JavaBean
     * @param list RNG_FORMDATA Data List Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(List<RngFormdataModel> list) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = null;
            sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_FORMDATA(");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RFD_SID,");
            sql.addSql("   RFD_ID,");
            sql.addSql("   RFD_ROWNO,");
            sql.addSql("   RFD_VALUE,");
            sql.addSql("   RFD_AUID,");
            sql.addSql("   RFD_ADATE,");
            sql.addSql("   RFD_EUID,");
            sql.addSql("   RFD_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (RngFormdataModel bean : list) {
                sql.addIntValue(bean.getRngSid());
                sql.addIntValue(bean.getRfdSid());
                sql.addStrValue(bean.getRfdId());
                sql.addIntValue(bean.getRfdRowno());
                sql.addStrValue(bean.getRfdValue());
                sql.addIntValue(bean.getRfdAuid());
                sql.addDateValue(bean.getRfdAdate());
                sql.addIntValue(bean.getRfdEuid());
                sql.addDateValue(bean.getRfdEdate());
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
     * <p>Select RNG_FORMDATA All Data
     * @return List in RNG_FORMDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RngFormdataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngFormdataModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RFD_SID,");
            sql.addSql("   RFD_ROWNO,");
            sql.addSql("   RFD_ID,");
            sql.addSql("   RFD_VALUE,");
            sql.addSql("   RFD_AUID,");
            sql.addSql("   RFD_ADATE,");
            sql.addSql("   RFD_EUID,");
            sql.addSql("   RFD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_FORMDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngFormdataFromRs(rs));
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
     * <p>Select RNG_FORMDATA All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in RNG_RNDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RngFormdataModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngFormdataModel> ret = new ArrayList<RngFormdataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RFD_SID,");
            sql.addSql("   RFD_ROWNO,");
            sql.addSql("   RFD_ID,");
            sql.addSql("   RFD_VALUE,");
            sql.addSql("   RFD_AUID,");
            sql.addSql("   RFD_ADATE,");
            sql.addSql("   RFD_EUID,");
            sql.addSql("   RFD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_FORMDATA");
            sql.addSql(" order by ");
            sql.addSql("   RNG_SID asc");
            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngFormdataFromRs(rs));
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
     * <p>count  All Data
     * @return count
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   RNG_FORMDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>Select RNG_FORMDATA All Data
     * @param rngSid RNG_SID
     * @return List in RNG_FORMDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RngFormdataModel> select(int rngSid) throws SQLException {
        return select(rngSid, false);
    }
    /**
     *
     * <br>[機  能] rngSidで入力データを取得する
     * <br>[解  説] 複写用の場合、RFD_IDが設定されたもののみデータを取得する
     * <br>[備  考]
     * @param rngSid RNG_SID
     * @param isCopy コピーフラグtrue:複写用
     * @return List in RNG_FORMDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RngFormdataModel> select(int rngSid, boolean isCopy) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngFormdataModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RFD_SID,");
            sql.addSql("   RFD_ROWNO,");
            sql.addSql("   RFD_ID,");
            sql.addSql("   RFD_VALUE,");
            sql.addSql("   RFD_AUID,");
            sql.addSql("   RFD_ADATE,");
            sql.addSql("   RFD_EUID,");
            sql.addSql("   RFD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_FORMDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            if (isCopy) {
                sql.addSql(" and RFD_ID is not null");
            }
            sql.addSql(" order by RFD_SID, RFD_ROWNO, RFD_VALUE");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngFormdataFromRs(rs));
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
     * <p>Select RNG_FORMDATA
     * @param rngSid RNG_SID
     * @param rfdSid RFD_SID
     * @param rfdRowno RFD_ROWNO
     * @return RNG_FORMDATAModel
     * @throws SQLException SQL実行例外
     */
    public RngFormdataModel select(int rngSid,
            int rfdSid,
            int rfdRowno
            ) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngFormdataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RFD_SID,");
            sql.addSql("   RFD_ROWNO,");
            sql.addSql("   RFD_ID,");
            sql.addSql("   RFD_VALUE,");
            sql.addSql("   RFD_AUID,");
            sql.addSql("   RFD_ADATE,");
            sql.addSql("   RFD_EUID,");
            sql.addSql("   RFD_EDATE");
            sql.addSql(" from");
            sql.addSql("   RNG_FORMDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            sql.addSql(" and");
            sql.addSql("   RFD_SID=?");
            sql.addSql(" and");
            sql.addSql("   RFD_ROWNO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);
            sql.addIntValue(rfdSid);
            sql.addIntValue(rfdRowno);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngFormdataFromRs(rs);
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
     * <p>Select RNG_FORMDATA
     * @param rngSidList 稟議SID一覧
     * @return List in RNG_FORMDATAModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, ArrayList<RngFormdataModel>> selectPdf(List<Integer> rngSidList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<Integer, ArrayList<RngFormdataModel>> ret =
                                        new HashMap<Integer, ArrayList<RngFormdataModel>>();
        con = getCon();
        if (rngSidList.size() <= 0) {
            return ret;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RFD_SID,");
            sql.addSql("   RFD_ROWNO,");
            sql.addSql("   RFD_ID,");
            sql.addSql("   RFD_VALUE,");
            sql.addSql("   RFD_AUID,");
            sql.addSql("   RFD_ADATE,");
            sql.addSql("   RFD_EUID,");
            sql.addSql("   RFD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_FORMDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID in (");
            StringBuilder strBui = new StringBuilder();
            for (Integer rngSid : rngSidList) {
                if (strBui.length() > 0) {
                    strBui.append(",");
                }
                strBui.append(rngSid);
            }
            sql.addSql("     " + strBui.toString());
            sql.addSql("   )");
            sql.addSql(" order by RFD_ROWNO, RNG_SID, RFD_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RngFormdataModel mdl = __getRngFormdataFromRs(rs);

                Integer key = Integer.valueOf(mdl.getRngSid());
                ArrayList<RngFormdataModel> list = ret.get(key);
                if (list == null) {
                    list = new ArrayList<RngFormdataModel>();
                }
                list.add(mdl);
                ret.put(key, list);
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
     * <p>Select RNG_FORMDATA
     * @param rngSidList 稟議SID一覧
     * @return List in RNG_FORMDATAModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, ArrayList<RngFormdataModel>> select(List<Integer> rngSidList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<Integer, ArrayList<RngFormdataModel>> ret =
                                        new HashMap<Integer, ArrayList<RngFormdataModel>>();
        con = getCon();
        if (rngSidList.size() <= 0) {
            return ret;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RFD_SID,");
            sql.addSql("   RFD_ROWNO,");
            sql.addSql("   RFD_ID,");
            sql.addSql("   RFD_VALUE,");
            sql.addSql("   RFD_AUID,");
            sql.addSql("   RFD_ADATE,");
            sql.addSql("   RFD_EUID,");
            sql.addSql("   RFD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_FORMDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID in (");
            StringBuffer strBuf = new StringBuffer();
            for (Integer rngSid : rngSidList) {
                if (strBuf.length() > 0) {
                    strBuf.append(",");
                }
                strBuf.append(rngSid);
            }
            sql.addSql("     " + strBuf.toString());
            sql.addSql("   )");
            sql.addSql(" order by RNG_SID, RFD_ROWNO, RFD_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RngFormdataModel mdl = __getRngFormdataFromRs(rs);

                Integer key = Integer.valueOf(mdl.getRngSid());
                ArrayList<RngFormdataModel> list = ret.get(key);
                if (list == null) {
                    list = new ArrayList<RngFormdataModel>();
                }
                list.add(mdl);
                ret.put(key, list);
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
     * <p>Delete RNG_FORMDATA
     * @param rngSid RNG_SID
     * @param rfdSid RFD_SID
     * @param rfdRowno RFD_ROWNO
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int rngSid,
            String rfdSid,
            int rfdRowno
            ) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_FORMDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");
            sql.addSql(" and");
            sql.addSql("   RFD_SID=?");
            sql.addSql(" and");
            sql.addSql("   RFD_ROWNO=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);
            sql.addStrValue(rfdSid);
            sql.addIntValue(rfdRowno);

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
     * <p>Delete RNG_FORMDATA
     * @param rngSid RNG_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteRngSid(int rngSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_FORMDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rngSid);

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
     * <br>[機  能] 指定した稟議情報のフォーム入力値のサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSidList 稟議SID
     * @return フォーム入力値のサイズ合計
     * @throws SQLException
     */
    public long getTotalInputDataSize(List<Integer> rngSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        long ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("  sum(octet_length(RFD_VALUE)) as VALUE_SIZE");
            sql.addSql(" from");
            sql.addSql("   RNG_FORMDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID in (");

            for (int idx = 0; idx < rngSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(rngSidList.get(idx));
            }

            sql.addSql("  )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("VALUE_SIZE");
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
     * <p>Create RNG_FORMDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngFormdataModel
     * @throws SQLException SQL実行例外
     */
    private RngFormdataModel __getRngFormdataFromRs(ResultSet rs) throws SQLException {
        RngFormdataModel bean = new RngFormdataModel();
        bean.setRngSid(rs.getInt("RNG_SID"));
        bean.setRfdSid(rs.getInt("RFD_SID"));
        bean.setRfdRowno(rs.getInt("RFD_ROWNO"));
        bean.setRfdId(rs.getString("RFD_ID"));
        bean.setRfdValue(rs.getString("RFD_VALUE"));
        bean.setRfdAuid(rs.getInt("RFD_AUID"));
        bean.setRfdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RFD_ADATE")));
        bean.setRfdEuid(rs.getInt("RFD_EUID"));
        bean.setRfdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RFD_EDATE")));
        return bean;
    }
}
