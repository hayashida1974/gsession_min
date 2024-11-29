package jp.groupsession.v2.adr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.model.AdrBelongIndustryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ADR_BELONG_INDUSTRY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrBelongIndustryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrBelongIndustryDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrBelongIndustryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrBelongIndustryDao(Connection con) {
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
            sql.addSql("drop table ADR_BELONG_INDUSTRY");

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
            sql.addSql(" create table ADR_BELONG_INDUSTRY (");
            sql.addSql("   ACO_SID NUMBER(10,0) not null,");
            sql.addSql("   ATI_SID NUMBER(10,0) not null,");
            sql.addSql("   ABI_AUID NUMBER(10,0) not null,");
            sql.addSql("   ABI_ADATE varchar(23) not null,");
            sql.addSql("   ABI_EUID NUMBER(10,0) not null,");
            sql.addSql("   ABI_EDATE varchar(23) not null,");
            sql.addSql("   primary key (ACO_SID,ATI_SID)");
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
     * <p>Insert ADR_BELONG_INDUSTRY Data Bindding JavaBean
     * @param bean ADR_BELONG_INDUSTRY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrBelongIndustryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_BELONG_INDUSTRY(");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ATI_SID,");
            sql.addSql("   ABI_AUID,");
            sql.addSql("   ABI_ADATE,");
            sql.addSql("   ABI_EUID,");
            sql.addSql("   ABI_EDATE");
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
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAtiSid());
            sql.addIntValue(bean.getAbiAuid());
            sql.addDateValue(bean.getAbiAdate());
            sql.addIntValue(bean.getAbiEuid());
            sql.addDateValue(bean.getAbiEdate());
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
     * <p>Update ADR_BELONG_INDUSTRY Data Bindding JavaBean
     * @param bean ADR_BELONG_INDUSTRY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrBelongIndustryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_BELONG_INDUSTRY");
            sql.addSql(" set ");
            sql.addSql("   ABI_AUID=?,");
            sql.addSql("   ABI_ADATE=?,");
            sql.addSql("   ABI_EUID=?,");
            sql.addSql("   ABI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and");
            sql.addSql("   ATI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAbiAuid());
            sql.addDateValue(bean.getAbiAdate());
            sql.addIntValue(bean.getAbiEuid());
            sql.addDateValue(bean.getAbiEdate());
            //where
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAtiSid());

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
     * <p>Select ADR_BELONG_INDUSTRY All Data
     * @return List in ADR_BELONG_INDUSTRYModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrBelongIndustryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrBelongIndustryModel> ret = new ArrayList<AdrBelongIndustryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ATI_SID,");
            sql.addSql("   ABI_AUID,");
            sql.addSql("   ABI_ADATE,");
            sql.addSql("   ABI_EUID,");
            sql.addSql("   ABI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_BELONG_INDUSTRY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrBelongIndustryFromRs(rs));
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
     * <p>Select ADR_BELONG_INDUSTRY
     * @param acoSid ACO_SID
     * @param atiSid ATI_SID
     * @return ADR_BELONG_INDUSTRYModel
     * @throws SQLException SQL実行例外
     */
    public AdrBelongIndustryModel select(int acoSid, int atiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrBelongIndustryModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ATI_SID,");
            sql.addSql("   ABI_AUID,");
            sql.addSql("   ABI_ADATE,");
            sql.addSql("   ABI_EUID,");
            sql.addSql("   ABI_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_BELONG_INDUSTRY");
            sql.addSql(" where ");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and");
            sql.addSql("   ATI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(acoSid);
            sql.addIntValue(atiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrBelongIndustryFromRs(rs);
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
     * <br>[機  能] 指定した業種SIDに所属している会社の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param atiSid 業種SID
     * @return int 会社の件数
     * @throws SQLException SQL実行例外
     */
    public int getIndCount(int atiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(ACO_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   ADR_BELONG_INDUSTRY");
            sql.addSql(" where");
            sql.addSql("   ATI_SID = ?");

            sql.addIntValue(atiSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
     * <br>[機  能] 指定した会社の業種SID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param acoSid 会社SID
     * @return List in ADR_BELONG_INDUSTRYModel
     * @throws SQLException SQL実行例外
     */
    public String[] getAtiSidList(int acoSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> atiSidList = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ATI_SID");
            sql.addSql(" from ");
            sql.addSql("   ADR_BELONG_INDUSTRY");
            sql.addSql(" where ");
            sql.addSql("   ACO_SID = ?");

            sql.addIntValue(acoSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                atiSidList.add(rs.getString("ATI_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return atiSidList.toArray(new String[atiSidList.size()]);
    }

    /**
     * <p>Delete ADR_BELONG_INDUSTRY
     * @param acoSid ACO_SID
     * @param atiSid ATI_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int acoSid, int atiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_BELONG_INDUSTRY");
            sql.addSql(" where ");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and");
            sql.addSql("   ATI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(acoSid);
            sql.addIntValue(atiSid);

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
     * <br>[機  能] 指定した会社の所属業種情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param acoSid 会社SID
     * @return delete count
     * @throws SQLException SQL実行時例外
     */
    public int delete(int acoSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_BELONG_INDUSTRY");
            sql.addSql(" where ");
            sql.addSql("   ACO_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(acoSid);

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
     * <p>Create ADR_BELONG_INDUSTRY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrBelongIndustryModel
     * @throws SQLException SQL実行例外
     */
    private AdrBelongIndustryModel __getAdrBelongIndustryFromRs(ResultSet rs) throws SQLException {
        AdrBelongIndustryModel bean = new AdrBelongIndustryModel();
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAtiSid(rs.getInt("ATI_SID"));
        bean.setAbiAuid(rs.getInt("ABI_AUID"));
        bean.setAbiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ABI_ADATE")));
        bean.setAbiEuid(rs.getInt("ABI_EUID"));
        bean.setAbiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ABI_EDATE")));
        return bean;
    }
}
