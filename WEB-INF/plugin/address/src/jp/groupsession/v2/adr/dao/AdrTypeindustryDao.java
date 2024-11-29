package jp.groupsession.v2.adr.dao;

import jp.co.sjts.util.date.UDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;

/**
 * <p>ADR_TYPEINDUSTRY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrTypeindustryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrTypeindustryDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrTypeindustryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrTypeindustryDao(Connection con) {
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
            sql.addSql("drop table ADR_TYPEINDUSTRY");

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
            sql.addSql(" create table ADR_TYPEINDUSTRY (");
            sql.addSql("   ATI_SID NUMBER(10,0) not null,");
            sql.addSql("   ATI_NAME varchar(20) not null,");
            sql.addSql("   ATI_BIKO varchar(300),");
            sql.addSql("   ATI_SORT NUMBER(10,0) not null,");
            sql.addSql("   ATI_AUID NUMBER(10,0) not null,");
            sql.addSql("   ATI_ADATE varchar(23) not null,");
            sql.addSql("   ATI_EUID NUMBER(10,0) not null,");
            sql.addSql("   ATI_EDATE varchar(23) not null,");
            sql.addSql("   primary key (ATI_SID)");
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
     * <p>Insert ADR_TYPEINDUSTRY Data Bindding JavaBean
     * @param bean ADR_TYPEINDUSTRY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrTypeindustryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_TYPEINDUSTRY(");
            sql.addSql("   ATI_SID,");
            sql.addSql("   ATI_NAME,");
            sql.addSql("   ATI_BIKO,");
            sql.addSql("   ATI_SORT,");
            sql.addSql("   ATI_AUID,");
            sql.addSql("   ATI_ADATE,");
            sql.addSql("   ATI_EUID,");
            sql.addSql("   ATI_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAtiSid());
            sql.addStrValue(bean.getAtiName());
            sql.addStrValue(bean.getAtiBiko());
            sql.addIntValue(bean.getAtiSort());
            sql.addIntValue(bean.getAtiAuid());
            sql.addDateValue(bean.getAtiAdate());
            sql.addIntValue(bean.getAtiEuid());
            sql.addDateValue(bean.getAtiEdate());
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
     * <p>Update ADR_TYPEINDUSTRY Data Bindding JavaBean
     * @param bean ADR_TYPEINDUSTRY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrTypeindustryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_TYPEINDUSTRY");
            sql.addSql(" set ");
            sql.addSql("   ATI_NAME=?,");
            sql.addSql("   ATI_BIKO=?,");
            sql.addSql("   ATI_EUID=?,");
            sql.addSql("   ATI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ATI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getAtiName());
            sql.addStrValue(bean.getAtiBiko());
            sql.addIntValue(bean.getAtiEuid());
            sql.addDateValue(bean.getAtiEdate());
            //where
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
     * <p>Select ADR_TYPEINDUSTRY All Data
     * @return List in ADR_TYPEINDUSTRYModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrTypeindustryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrTypeindustryModel> ret = new ArrayList<AdrTypeindustryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ATI_SID,");
            sql.addSql("   ATI_NAME,");
            sql.addSql("   ATI_BIKO,");
            sql.addSql("   ATI_SORT,");
            sql.addSql("   ATI_AUID,");
            sql.addSql("   ATI_ADATE,");
            sql.addSql("   ATI_EUID,");
            sql.addSql("   ATI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_TYPEINDUSTRY");
            sql.addSql(" order by ");
            sql.addSql("   ATI_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrTypeindustryFromRs(rs));
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
     * <p>Select ADR_TYPEINDUSTRY
     * @param atiSid ATI_SID
     * @return ADR_TYPEINDUSTRYModel
     * @throws SQLException SQL実行例外
     */
    public AdrTypeindustryModel select(int atiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrTypeindustryModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ATI_SID,");
            sql.addSql("   ATI_NAME,");
            sql.addSql("   ATI_BIKO,");
            sql.addSql("   ATI_SORT,");
            sql.addSql("   ATI_AUID,");
            sql.addSql("   ATI_ADATE,");
            sql.addSql("   ATI_EUID,");
            sql.addSql("   ATI_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_TYPEINDUSTRY");
            sql.addSql(" where ");
            sql.addSql("   ATI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(atiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrTypeindustryFromRs(rs);
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
     * <br>[機  能] 指定したSIDの業種を取得する
     * <br>[解  説]
     * <br>[備  考] 表示順でソート
     * @param atiSids 業種SIDリスト
     * @return List<AdrTypeindustryModel> 業種情報モデル
     * @throws SQLException SQL実行例外
     */
    public List<AdrTypeindustryModel> getIndustryList(int[] atiSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<AdrTypeindustryModel> ret = new ArrayList<AdrTypeindustryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ATI_SID,");
            sql.addSql("   ATI_NAME,");
            sql.addSql("   ATI_BIKO,");
            sql.addSql("   ATI_SORT,");
            sql.addSql("   ATI_AUID,");
            sql.addSql("   ATI_ADATE,");
            sql.addSql("   ATI_EUID,");
            sql.addSql("   ATI_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_TYPEINDUSTRY");
            sql.addSql(" where ");
            sql.addSql("   ATI_SID in ");
            sql.addSql("   ( ?");
            for (int i = 0; i < atiSids.length - 1; i++) {
                sql.addSql("  , ?");
            }
            sql.addSql("   )");
            
            sql.addSql(" order by ");
            sql.addSql("   ATI_SORT");

            if (atiSids != null && atiSids.length > 0) {
                for (int n = 0; n < atiSids.length; n++) {
                    sql.addIntValue(atiSids[n]);
                }
            } else {
                sql.addIntValue(-1);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrTypeindustryFromRs(rs));
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
     * <br>[機  能] ソート順の最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return int ソート順の最大値
     * @throws SQLException SQL実行例外
     */
    public int getSortMax() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(ATI_SORT) as MAX");
            sql.addSql(" from");
            sql.addSql("   ADR_TYPEINDUSTRY");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("MAX");
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
     * <p>Delete ADR_TYPEINDUSTRY
     * @param atiSid ATI_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int atiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_TYPEINDUSTRY");
            sql.addSql(" where ");
            sql.addSql("   ATI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Create ADR_TYPEINDUSTRY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrTypeindustryModel
     * @throws SQLException SQL実行例外
     */
    private AdrTypeindustryModel __getAdrTypeindustryFromRs(ResultSet rs) throws SQLException {
        AdrTypeindustryModel bean = new AdrTypeindustryModel();
        bean.setAtiSid(rs.getInt("ATI_SID"));
        bean.setAtiName(rs.getString("ATI_NAME"));
        bean.setAtiBiko(rs.getString("ATI_BIKO"));
        bean.setAtiSort(rs.getInt("ATI_SORT"));
        bean.setAtiAuid(rs.getInt("ATI_AUID"));
        bean.setAtiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ATI_ADATE")));
        bean.setAtiEuid(rs.getInt("ATI_EUID"));
        bean.setAtiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ATI_EDATE")));
        return bean;
    }
}
