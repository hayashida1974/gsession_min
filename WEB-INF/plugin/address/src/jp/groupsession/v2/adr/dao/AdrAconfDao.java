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
import jp.groupsession.v2.adr.model.AdrAconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ADR_ACONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class AdrAconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AdrAconfDao.class);

    /**
     * <p>Default Constructor
     */
    public AdrAconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public AdrAconfDao(Connection con) {
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
            sql.addSql("drop table ADR_ACONF");

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
            sql.addSql(" create table ADR_ACONF (");
            sql.addSql("   AAC_ATI_EDIT NUMBER(10,0) not null,");
            sql.addSql("   AAC_ACO_EDIT NUMBER(10,0) not null,");
            sql.addSql("   AAC_ALB_EDIT NUMBER(10,0) not null,");
            sql.addSql("   AAC_EXPORT NUMBER(10,0) not null,");
            sql.addSql("   AAC_AUID NUMBER(10,0) not null,");
            sql.addSql("   AAC_ADATE varchar(23) not null,");
            sql.addSql("   AAC_EUID NUMBER(10,0) not null,");
            sql.addSql("   AAC_EDATE varchar(23) not null,");
            sql.addSql("   AAC_AREST_KBN NUMBER(10,0) not null");
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
     * <p>Insert ADR_ACONF Data Bindding JavaBean
     * @param bean ADR_ACONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(AdrAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ADR_ACONF(");
            sql.addSql("   AAC_ATI_EDIT,");
            sql.addSql("   AAC_ACO_EDIT,");
            sql.addSql("   AAC_ALB_EDIT,");
            sql.addSql("   AAC_EXPORT,");
            sql.addSql("   AAC_AUID,");
            sql.addSql("   AAC_ADATE,");
            sql.addSql("   AAC_EUID,");
            sql.addSql("   AAC_EDATE,");
            sql.addSql("   AAC_YKS_EDIT,");
            sql.addSql("   AAC_VRM_EDIT,");
            sql.addSql("   AAC_PVW_KBN,");
            sql.addSql("   AAC_PET_KBN,");
            sql.addSql("   AAC_AREST_KBN");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAacAtiEdit());
            sql.addIntValue(bean.getAacAcoEdit());
            sql.addIntValue(bean.getAacAlbEdit());
            sql.addIntValue(bean.getAacExport());
            sql.addIntValue(bean.getAacAuid());
            sql.addDateValue(bean.getAacAdate());
            sql.addIntValue(bean.getAacEuid());
            sql.addDateValue(bean.getAacEdate());
            sql.addIntValue(bean.getAacYksEdit());
            sql.addIntValue(bean.getAacVrmEdit());
            sql.addIntValue(bean.getAacPvwKbn());
            sql.addIntValue(bean.getAacPetKbn());
            sql.addIntValue(bean.getAacArestKbn());
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
     * <p>Update ADR_ACONF Data Bindding JavaBean
     * @param bean ADR_ACONF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(AdrAconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_ACONF");
            sql.addSql(" set ");
            sql.addSql("   AAC_ATI_EDIT=?,");
            sql.addSql("   AAC_ACO_EDIT=?,");
            sql.addSql("   AAC_ALB_EDIT=?,");
            sql.addSql("   AAC_EXPORT=?,");
            sql.addSql("   AAC_EUID=?,");
            sql.addSql("   AAC_EDATE=?,");
            sql.addSql("   AAC_YKS_EDIT=?,");
            sql.addSql("   AAC_VRM_EDIT=?,");
            sql.addSql("   AAC_PVW_KBN=?,");
            sql.addSql("   AAC_PET_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAacAtiEdit());
            sql.addIntValue(bean.getAacAcoEdit());
            sql.addIntValue(bean.getAacAlbEdit());
            sql.addIntValue(bean.getAacExport());
            sql.addIntValue(bean.getAacEuid());
            sql.addDateValue(bean.getAacEdate());
            sql.addIntValue(bean.getAacYksEdit());
            sql.addIntValue(bean.getAacVrmEdit());
            sql.addIntValue(bean.getAacPvwKbn());
            sql.addIntValue(bean.getAacPetKbn());

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
     * <p>Update ADR_ACONF Data Bindding JavaBean
     * @param arestKbn 登録者制限区分
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateArestKbn(int arestKbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_ACONF");
            sql.addSql(" set ");
            sql.addSql("   AAC_AREST_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(arestKbn);

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
     * <p>Select ADR_ACONF All Data
     * @return List in ADR_ACONFModel
     * @throws SQLException SQL実行例外
     */
    public List<AdrAconfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<AdrAconfModel> ret = new ArrayList<AdrAconfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   AAC_ATI_EDIT,");
            sql.addSql("   AAC_ACO_EDIT,");
            sql.addSql("   AAC_ALB_EDIT,");
            sql.addSql("   AAC_EXPORT,");
            sql.addSql("   AAC_AUID,");
            sql.addSql("   AAC_ADATE,");
            sql.addSql("   AAC_EUID,");
            sql.addSql("   AAC_EDATE,");
            sql.addSql("   AAC_YKS_EDIT,");
            sql.addSql("   AAC_VRM_EDIT,");
            sql.addSql("   AAC_PVW_KBN,");
            sql.addSql("   AAC_PET_KBN,");
            sql.addSql("   AAC_AREST_KBN");
            sql.addSql(" from ");
            sql.addSql("   ADR_ACONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getAdrAconfFromRs(rs));
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
     * <p>Select ADR_ACONF
     * @param returnInit 該当データがない場合に初期化したデータを返す
     * @return AdrAconfModel
     * @throws SQLException SQL実行例外
     *
     */
    public AdrAconfModel selectAconf(boolean returnInit) throws SQLException {
        AdrAconfModel ret = selectAconf();
        if (returnInit && ret == null) {
            ret = new AdrAconfModel();
            ret.init();
        }
        return ret;
    }
    /**
     * <p>Select ADR_ACONF
     * @return AdrAconfModel
     * @throws SQLException SQL実行例外
     *
     */
    public AdrAconfModel selectAconf() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        AdrAconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   AAC_ATI_EDIT,");
            sql.addSql("   AAC_ACO_EDIT,");
            sql.addSql("   AAC_ALB_EDIT,");
            sql.addSql("   AAC_EXPORT,");
            sql.addSql("   AAC_AUID,");
            sql.addSql("   AAC_ADATE,");
            sql.addSql("   AAC_EUID,");
            sql.addSql("   AAC_EDATE,");
            sql.addSql("   AAC_YKS_EDIT,");
            sql.addSql("   AAC_VRM_EDIT,");
            sql.addSql("   AAC_PVW_KBN,");
            sql.addSql("   AAC_PET_KBN,");
            sql.addSql("   AAC_AREST_KBN");
            sql.addSql(" from ");
            sql.addSql("   ADR_ACONF");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getAdrAconfFromRs(rs);
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
     * <p>Create ADR_ACONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created AdrAconfModel
     * @throws SQLException SQL実行例外
     */
    private AdrAconfModel __getAdrAconfFromRs(ResultSet rs) throws SQLException {
        AdrAconfModel bean = new AdrAconfModel();
        bean.setAacAtiEdit(rs.getInt("AAC_ATI_EDIT"));
        bean.setAacAcoEdit(rs.getInt("AAC_ACO_EDIT"));
        bean.setAacAlbEdit(rs.getInt("AAC_ALB_EDIT"));
        bean.setAacExport(rs.getInt("AAC_EXPORT"));
        bean.setAacAuid(rs.getInt("AAC_AUID"));
        bean.setAacAdate(UDate.getInstanceTimestamp(rs.getTimestamp("AAC_ADATE")));
        bean.setAacEuid(rs.getInt("AAC_EUID"));
        bean.setAacEdate(UDate.getInstanceTimestamp(rs.getTimestamp("AAC_EDATE")));
        bean.setAacYksEdit(rs.getInt("AAC_YKS_EDIT"));
        bean.setAacVrmEdit(rs.getInt("AAC_VRM_EDIT"));
        bean.setAacPvwKbn(rs.getInt("AAC_PVW_KBN"));
        bean.setAacPetKbn(rs.getInt("AAC_PET_KBN"));
        bean.setAacArestKbn(rs.getInt("AAC_AREST_KBN"));
        return bean;
    }
}
