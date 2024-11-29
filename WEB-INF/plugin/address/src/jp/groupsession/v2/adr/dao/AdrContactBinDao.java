package jp.groupsession.v2.adr.dao;

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
import jp.groupsession.v2.adr.model.AdrContactBinModel;

/**
 * <p>ADR_CONTACT_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrContactBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrContactBinDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrContactBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrContactBinDao(Connection con) {
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
            sql.addSql("drop table ADR_CONTACT_BIN");

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
            sql.addSql(" create table ADR_CONTACT_BIN (");
            sql.addSql("   ADC_SID NUMBER(10,0) not null,");
            sql.addSql("   BIN_SID NUMBER(10,0) not null,");
            sql.addSql("   ACB_AUID NUMBER(10,0) not null,");
            sql.addSql("   ACB_ADATE varchar(23) not null,");
            sql.addSql("   ACB_EUID NUMBER(10,0) not null,");
            sql.addSql("   ACB_EDATE varchar(23) not null,");
            sql.addSql("   primary key (ADC_SID,BIN_SID)");
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
     * <p>Insert ADR_CONTACT_BIN Data Bindding JavaBean
     * @param bean ADR_CONTACT_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrContactBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_CONTACT_BIN(");
            sql.addSql("   ADC_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   ACB_AUID,");
            sql.addSql("   ACB_ADATE,");
            sql.addSql("   ACB_EUID,");
            sql.addSql("   ACB_EDATE");
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
            sql.addIntValue(bean.getAdcSid());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getAcbAuid());
            sql.addDateValue(bean.getAcbAdate());
            sql.addIntValue(bean.getAcbEuid());
            sql.addDateValue(bean.getAcbEdate());
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
     * <p>Update ADR_CONTACT_BIN Data Bindding JavaBean
     * @param bean ADR_CONTACT_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrContactBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_CONTACT_BIN");
            sql.addSql(" set ");
            sql.addSql("   ACB_AUID=?,");
            sql.addSql("   ACB_ADATE=?,");
            sql.addSql("   ACB_EUID=?,");
            sql.addSql("   ACB_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAcbAuid());
            sql.addDateValue(bean.getAcbAdate());
            sql.addIntValue(bean.getAcbEuid());
            sql.addDateValue(bean.getAcbEdate());
            //where
            sql.addIntValue(bean.getAdcSid());
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
     * <p>Select ADR_CONTACT_BIN All Data
     * @return List in ADR_CONTACT_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrContactBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrContactBinModel> ret = new ArrayList<AdrContactBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ADC_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   ACB_AUID,");
            sql.addSql("   ACB_ADATE,");
            sql.addSql("   ACB_EUID,");
            sql.addSql("   ACB_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_CONTACT_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrContactBinFromRs(rs));
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
     * <p>Select ADR_CONTACT_BIN
     * @param adcSid ADC_SID
     * @param binSid BIN_SID
     * @return ADR_CONTACT_BINModel
     * @throws SQLException SQL実行例外
     */
    public AdrContactBinModel select(int adcSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrContactBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADC_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   ACB_AUID,");
            sql.addSql("   ACB_ADATE,");
            sql.addSql("   ACB_EUID,");
            sql.addSql("   ACB_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT_BIN");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adcSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrContactBinFromRs(rs);
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
     * <p>指定されたバイナリSIDがコンタクト履歴の添付ファイルかチェックする
     * @param adcSid コンタクト履歴SID
     * @param binSid バイナリSID
     * @return ADR_CONTACT_BINModel
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckContactFile(int adcSid, Long binSid) throws SQLException {

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
            sql.addSql("   ADR_CONTACT_BIN");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adcSid);
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
     * <br>[機  能] 指定されたコンタクト履歴添付情報の添付ファイルSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adcSid コンタクト履歴SID
     * @param userSid ユーザSID
     * @return 添付ファイルSIDリスト
     * @throws SQLException SQL実行例外
     */
    public String[] getTmpFileList(int adcSid, int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List < String > binList = new ArrayList < String >();
        String[] ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_CONTACT_BIN.BIN_SID");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT_BIN");
            sql.addSql(" where");
            sql.addSql("   ADR_CONTACT_BIN.ADC_SID = ?");
            sql.addIntValue(adcSid);
            if (userSid != -1) {
                sql.addSql(" and");
                sql.addSql("   ADR_CONTACT_BIN.ACB_AUID = ?");
                sql.addIntValue(userSid);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                binList.add(String.valueOf(rs.getLong("BIN_SID")));
            }
            ret = binList.toArray(new String[binList.size()]);
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] ユーザSIDからコンタクト履歴添付情報のコンタクト履歴SIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return コンタクト履歴SIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getTmpFileAdcSidList(int userSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List <Integer> ret = new ArrayList <Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ADR_CONTACT_BIN.ADC_SID");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT_BIN");
            sql.addSql(" where");
            if (userSid != -1) {
                sql.addSql("   ADR_CONTACT_BIN.ACB_AUID = ?");
                sql.addIntValue(userSid);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("ADC_SID"));
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
     * <br>[機  能] 指定したコンタクト履歴情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param adcSidList コンタクト履歴SIDリスト
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getSumFileSize(List<Integer> adcSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long fileSize = 0;
        con = getCon();
        if (adcSidList == null || adcSidList.isEmpty()) {
            return fileSize;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   ADR_CONTACT_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   ADR_CONTACT_BIN.ADC_SID in (");

            for (int idx = 0; idx < adcSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(adcSidList.get(idx));
            }

            sql.addSql(" )");

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
     * <p>Delete ADR_CONTACT_BIN
     * @param adcSid ADC_SID
     * @param binSid BIN_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int adcSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_CONTACT_BIN");
            sql.addSql(" where ");
            sql.addSql("   ADC_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(adcSid);
            sql.addLongValue(binSid);

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
     * <p>Create ADR_CONTACT_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrContactBinModel
     * @throws SQLException SQL実行例外
     */
    private AdrContactBinModel __getAdrContactBinFromRs(ResultSet rs) throws SQLException {
        AdrContactBinModel bean = new AdrContactBinModel();
        bean.setAdcSid(rs.getInt("ADC_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setAcbAuid(rs.getInt("ACB_AUID"));
        bean.setAcbAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ACB_ADATE")));
        bean.setAcbEuid(rs.getInt("ACB_EUID"));
        bean.setAcbEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ACB_EDATE")));
        return bean;
    }
}
