package jp.groupsession.v2.adr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.adr.model.AdrCompanyBaseModel;

/**
 * <p>ADR_COMPANY_BASE Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AdrCompanyBaseDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrCompanyBaseDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrCompanyBaseDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrCompanyBaseDao(Connection con) {
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
            sql.addSql("drop table ADR_COMPANY_BASE");

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
            sql.addSql(" create table ADR_COMPANY_BASE (");
            sql.addSql("   ABA_SID NUMBER(10,0) not null,");
            sql.addSql("   ACO_SID NUMBER(10,0) not null,");
            sql.addSql("   ABA_TYPE NUMBER(10,0) not null,");
            sql.addSql("   ABA_NAME varchar(50) not null,");
            sql.addSql("   ABA_POSTNO1 varchar(3),");
            sql.addSql("   ABA_POSTNO2 varchar(4),");
            sql.addSql("   TDF_SID NUMBER(10,0),");
            sql.addSql("   ABA_ADDR1 varchar(100),");
            sql.addSql("   ABA_ADDR2 varchar(100),");
            sql.addSql("   ABA_BIKO varchar(1000),");
            sql.addSql("   ABA_AUID NUMBER(10,0) not null,");
            sql.addSql("   ABA_ADATE varchar(23) not null,");
            sql.addSql("   ABA_EUID NUMBER(10,0) not null,");
            sql.addSql("   ABA_EDATE varchar(23) not null,");
            sql.addSql("   primary key (ABA_SID)");
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
     * <p>Insert ADR_COMPANY_BASE Data Bindding JavaBean
     * @param bean ADR_COMPANY_BASE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrCompanyBaseModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_COMPANY_BASE(");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_TYPE,");
            sql.addSql("   ABA_NAME,");
            sql.addSql("   ABA_POSTNO1,");
            sql.addSql("   ABA_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ABA_ADDR1,");
            sql.addSql("   ABA_ADDR2,");
            sql.addSql("   ABA_BIKO,");
            sql.addSql("   ABA_AUID,");
            sql.addSql("   ABA_ADATE,");
            sql.addSql("   ABA_EUID,");
            sql.addSql("   ABA_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAbaSid());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaType());
            sql.addStrValue(bean.getAbaName());
            sql.addStrValue(bean.getAbaPostno1());
            sql.addStrValue(bean.getAbaPostno2());
            sql.addIntValue(bean.getTdfSid());
            sql.addStrValue(bean.getAbaAddr1());
            sql.addStrValue(bean.getAbaAddr2());
            sql.addStrValue(bean.getAbaBiko());
            sql.addIntValue(bean.getAbaAuid());
            sql.addDateValue(bean.getAbaAdate());
            sql.addIntValue(bean.getAbaEuid());
            sql.addDateValue(bean.getAbaEdate());
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
     * <p>Update ADR_COMPANY_BASE Data Bindding JavaBean
     * @param bean ADR_COMPANY_BASE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrCompanyBaseModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" set ");
            sql.addSql("   ACO_SID=?,");
            sql.addSql("   ABA_TYPE=?,");
            sql.addSql("   ABA_NAME=?,");
            sql.addSql("   ABA_POSTNO1=?,");
            sql.addSql("   ABA_POSTNO2=?,");
            sql.addSql("   TDF_SID=?,");
            sql.addSql("   ABA_ADDR1=?,");
            sql.addSql("   ABA_ADDR2=?,");
            sql.addSql("   ABA_BIKO=?,");
            sql.addSql("   ABA_EUID=?,");
            sql.addSql("   ABA_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaType());
            sql.addStrValue(bean.getAbaName());
            sql.addStrValue(bean.getAbaPostno1());
            sql.addStrValue(bean.getAbaPostno2());
            sql.addIntValue(bean.getTdfSid());
            sql.addStrValue(bean.getAbaAddr1());
            sql.addStrValue(bean.getAbaAddr2());
            sql.addStrValue(bean.getAbaBiko());
            sql.addIntValue(bean.getAbaEuid());
            sql.addDateValue(bean.getAbaEdate());
            //where
            sql.addIntValue(bean.getAbaSid());

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
     * <p>Select ADR_COMPANY_BASE All Data
     * @return List in ADR_COMPANY_BASEModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrCompanyBaseModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrCompanyBaseModel> ret = new ArrayList<AdrCompanyBaseModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_TYPE,");
            sql.addSql("   ABA_NAME,");
            sql.addSql("   ABA_POSTNO1,");
            sql.addSql("   ABA_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ABA_ADDR1,");
            sql.addSql("   ABA_ADDR2,");
            sql.addSql("   ABA_BIKO,");
            sql.addSql("   ABA_AUID,");
            sql.addSql("   ABA_ADATE,");
            sql.addSql("   ABA_EUID,");
            sql.addSql("   ABA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_COMPANY_BASE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrCompanyBaseFromRs(rs));
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
     * <br>[機  能] 指定した会社の会社拠点情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param acoSid 会社SID
     * @return List in ADR_COMPANY_BASEModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrCompanyBaseModel> getCompanyBaseList(int acoSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrCompanyBaseModel> ret = new ArrayList<AdrCompanyBaseModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_TYPE,");
            sql.addSql("   ABA_NAME,");
            sql.addSql("   ABA_POSTNO1,");
            sql.addSql("   ABA_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ABA_ADDR1,");
            sql.addSql("   ABA_ADDR2,");
            sql.addSql("   ABA_BIKO,");
            sql.addSql("   ABA_AUID,");
            sql.addSql("   ABA_ADATE,");
            sql.addSql("   ABA_EUID,");
            sql.addSql("   ABA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" where ");
            sql.addSql("   ACO_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   ABA_TYPE asc,");
            sql.addSql("   ABA_SID asc");

            sql.addIntValue(acoSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrCompanyBaseFromRs(rs));
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
     * <p>Select ADR_COMPANY_BASE
     * @param abaSid ABA_SID
     * @return ADR_COMPANY_BASEModel
     * @throws SQLException SQL実行例外
     */
    public AdrCompanyBaseModel select(int abaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrCompanyBaseModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_TYPE,");
            sql.addSql("   ABA_NAME,");
            sql.addSql("   ABA_POSTNO1,");
            sql.addSql("   ABA_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ABA_ADDR1,");
            sql.addSql("   ABA_ADDR2,");
            sql.addSql("   ABA_BIKO,");
            sql.addSql("   ABA_AUID,");
            sql.addSql("   ABA_ADATE,");
            sql.addSql("   ABA_EUID,");
            sql.addSql("   ABA_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" where ");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(abaSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrCompanyBaseFromRs(rs);
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
     * <p>Select ADR_COMPANY_BASE
     * @param abaSid ABA_SID
     * @param acoSid ACO_SID
     * @return ADR_COMPANY_BASEModel
     * @throws SQLException SQL実行例外
     */
    public AdrCompanyBaseModel select(int acoSid, int abaSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrCompanyBaseModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_TYPE,");
            sql.addSql("   ABA_NAME,");
            sql.addSql("   ABA_POSTNO1,");
            sql.addSql("   ABA_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ABA_ADDR1,");
            sql.addSql("   ABA_ADDR2,");
            sql.addSql("   ABA_BIKO,");
            sql.addSql("   ABA_AUID,");
            sql.addSql("   ABA_ADATE,");
            sql.addSql("   ABA_EUID,");
            sql.addSql("   ABA_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" where ");
            sql.addSql("   ABA_SID=?");
            sql.addSql(" and ");
            sql.addSql("   ACO_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(abaSid);
            sql.addIntValue(acoSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrCompanyBaseFromRs(rs);
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
     * <p>拠点名から拠点情報を取得する。
     * @param acoSid 会社SID
     * @param abaName ABA_NAME
     * @return ADR_COMPANY_BASEModel
     * @throws SQLException SQL実行例外
     */
    public AdrCompanyBaseModel getCompanyBaseModel(int acoSid, String abaName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrCompanyBaseModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_TYPE,");
            sql.addSql("   ABA_NAME,");
            sql.addSql("   ABA_POSTNO1,");
            sql.addSql("   ABA_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ABA_ADDR1,");
            sql.addSql("   ABA_ADDR2,");
            sql.addSql("   ABA_BIKO,");
            sql.addSql("   ABA_AUID,");
            sql.addSql("   ABA_ADATE,");
            sql.addSql("   ABA_EUID,");
            sql.addSql("   ABA_EDATE");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" where ");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and");
            sql.addSql("   ABA_NAME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(acoSid);
            sql.addStrValue(abaName);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrCompanyBaseFromRs(rs);
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
     * <br>[機  能] 拠点名一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param abaSids 拠点SID一覧
     * @return 拠点SIDをキーにした拠点名配列
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, String> getCompanyBaseNameMap(Connection con,
                                                      List<Integer> abaSids) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Map<Integer, String> ret = new HashMap<Integer, String>();

        try {
            if (abaSids != null && abaSids.size() > 0) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select ");
                sql.addSql("   ABA_SID,");
                sql.addSql("   ABA_NAME");
                sql.addSql(" from ");
                sql.addSql("   ADR_COMPANY_BASE");
                sql.addSql(" where ");
                sql.addSql("   ABA_SID in (");
                for (int i = 0; i < abaSids.size(); i++) {
                    sql.addSql((i > 0 ? "   ," : "   ") + String.valueOf(abaSids.get(i)));
                }
                sql.addSql("   )");

                pstmt = con.prepareStatement(sql.toSqlString());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    ret.put(rs.getInt("ABA_SID"), rs.getString("ABA_NAME"));
                }

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
     * <p>Delete ADR_COMPANY_BASE
     * @param abaSid ABA_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int abaSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" where ");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(abaSid);

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
     * <p>会社拠点を削除する。
     * @param acoSid ACO_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteCompany(int acoSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY_BASE");
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
     * <br>[機  能] 指定された会社拠点SIDを除く会社拠点情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param acoSid 会社SID
     * @param notDeleteList 削除対象から除外する会社拠点SID
     * @return delete count
     * @throws SQLException SQL実行時例外
     */
    public int delete(int acoSid, List<Integer> notDeleteList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" where ");
            sql.addSql("   ACO_SID = ?");
            sql.addIntValue(acoSid);

            if (notDeleteList != null && !notDeleteList.isEmpty()) {
                sql.addSql(" and");
                StringBuilder sb = new StringBuilder("");
                sb.append("   ABA_SID not in (");

                int index = 0;
                int lastIndex = notDeleteList.size() - 1;
                for (Integer abaSid : notDeleteList) {
                    sb.append(String.valueOf(abaSid.intValue()));
                    if (index < lastIndex) {
                        sb.append(", ");
                    }
                    index++;
                }

                sb.append(")");
                sql.addSql(sb.toString());
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] 指定した会社の会社拠点情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param acoSid 会社SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteForCompany(int acoSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ADR_COMPANY_BASE");
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
     * <p>Create ADR_COMPANY_BASE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrCompanyBaseModel
     * @throws SQLException SQL実行例外
     */
    private AdrCompanyBaseModel __getAdrCompanyBaseFromRs(ResultSet rs) throws SQLException {
        AdrCompanyBaseModel bean = new AdrCompanyBaseModel();
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaType(rs.getInt("ABA_TYPE"));
        bean.setAbaName(rs.getString("ABA_NAME"));
        bean.setAbaPostno1(rs.getString("ABA_POSTNO1"));
        bean.setAbaPostno2(rs.getString("ABA_POSTNO2"));
        bean.setTdfSid(rs.getInt("TDF_SID"));
        bean.setAbaAddr1(rs.getString("ABA_ADDR1"));
        bean.setAbaAddr2(rs.getString("ABA_ADDR2"));
        bean.setAbaBiko(rs.getString("ABA_BIKO"));
        bean.setAbaAuid(rs.getInt("ABA_AUID"));
        bean.setAbaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ABA_ADATE")));
        bean.setAbaEuid(rs.getInt("ABA_EUID"));
        bean.setAbaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ABA_EDATE")));
        return bean;
    }

    /**
     * <p>Select ADR_COMPANY_BASE All Data
     * @param sids aco_sid
     * @return List in ADR_COMPANY_BASEModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrCompanyBaseModel> select(Collection<Integer> sids) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrCompanyBaseModel> ret = new ArrayList<AdrCompanyBaseModel>();
        con = getCon();
        if (sids == null || sids.size() <= 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ABA_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_TYPE,");
            sql.addSql("   ABA_NAME,");
            sql.addSql("   ABA_POSTNO1,");
            sql.addSql("   ABA_POSTNO2,");
            sql.addSql("   TDF_SID,");
            sql.addSql("   ABA_ADDR1,");
            sql.addSql("   ABA_ADDR2,");
            sql.addSql("   ABA_BIKO,");
            sql.addSql("   ABA_AUID,");
            sql.addSql("   ABA_ADATE,");
            sql.addSql("   ABA_EUID,");
            sql.addSql("   ABA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ADR_COMPANY_BASE");
            sql.addSql(" where ");
            int i = 0;
            sql.addSql("   (ACO_SID in (");
            for (Integer sid : sids) {
                if (sid == null) {
                    continue;
                }
                if (i > 0 && i % 1000 == 0) {
                    sql.addSql("  ) or ACO_SID in (");
                }
                if (i % 1000 != 0) {
                    sql.addSql("  ,");
                }
                sql.addSql(String.valueOf(sid));
                i++;
            }
            sql.addSql("  ))");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrCompanyBaseFromRs(rs));
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
