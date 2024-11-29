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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao.ISearchParam;
import jp.groupsession.v2.rng.model.RngTemplateKeiroModel;
import jp.groupsession.v2.rng.model.RngTemplateKeiroUserModel;

/**
 * <p>RNG_TEMPLATE_KEIRO_USER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplateKeiroUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngTemplateKeiroUserDao.class);

    /**
     * <p>Default Constructor
     */
    public RngTemplateKeiroUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngTemplateKeiroUserDao(Connection con) {
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
            sql.addSql("drop table RNG_TEMPLATE_KEIRO_USER");

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
            sql.addSql(" create table RNG_TEMPLATE_KEIRO_USER (");
            sql.addSql("   RTK_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   POS_SID NUMBER(10,0) not null,");
            sql.addSql("   RKU_TYPE NUMBER(10,0) not null,");
            sql.addSql("   RKU_AUID NUMBER(10,0) not null,");
            sql.addSql("   RKU_ADATE varchar(23) not null,");
            sql.addSql("   RKU_EUID NUMBER(10,0) not null,");
            sql.addSql("   RKU_EDATE varchar(23) not null,");
            sql.addSql("   primary key (RTK_SID,USR_SID,GRP_SID,POS_SID)");
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
     * <p>Insert RNG_TEMPLATE_KEIRO_USER Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_KEIRO_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngTemplateKeiroUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_TEMPLATE_KEIRO_USER(");
            sql.addSql("   RTK_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID,");
            sql.addSql("   RKU_TYPE,");
            sql.addSql("   RKU_AUID,");
            sql.addSql("   RKU_ADATE,");
            sql.addSql("   RKU_EUID,");
            sql.addSql("   RKU_EDATE");
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
            sql.addIntValue(bean.getRtkSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getPosSid());
            sql.addIntValue(bean.getRkuType());
            sql.addIntValue(bean.getRkuAuid());
            sql.addDateValue(bean.getRkuAdate());
            sql.addIntValue(bean.getRkuEuid());
            sql.addDateValue(bean.getRkuEdate());
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
     * <p>Update RNG_TEMPLATE_KEIRO_USER Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_KEIRO_USER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngTemplateKeiroUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATE_KEIRO_USER");
            sql.addSql(" set ");
            sql.addSql("   RKU_TYPE=?,");
            sql.addSql("   RKU_AUID=?,");
            sql.addSql("   RKU_ADATE=?,");
            sql.addSql("   RKU_EUID=?,");
            sql.addSql("   RKU_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   POS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRkuType());
            sql.addIntValue(bean.getRkuAuid());
            sql.addDateValue(bean.getRkuAdate());
            sql.addIntValue(bean.getRkuEuid());
            sql.addDateValue(bean.getRkuEdate());
            //where
            sql.addIntValue(bean.getRtkSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getPosSid());

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
     * <p>Select RNG_TEMPLATE_KEIRO_USER All Data
     * @return List in RNG_TEMPLATE_KEIRO_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateKeiroUserModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplateKeiroUserModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTK_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID,");
            sql.addSql("   RKU_TYPE,");
            sql.addSql("   RKU_AUID,");
            sql.addSql("   RKU_ADATE,");
            sql.addSql("   RKU_EUID,");
            sql.addSql("   RKU_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_KEIRO_USER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateKeiroUserFromRs(rs));
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
     * <p>Select RNG_TEMPLATE_KEIRO_USER All Data
     * @param param 検索オブジェクト
     * @param keiroDao 経路テンプレート経路ステップ情報DAO
     * @param jkbn 削除区分
     * @return List in RNG_TEMPLATE_KEIRO_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateKeiroUserModel> select(RngTemplateKeiroDao keiroDao,
            ISearchParam param, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplateKeiroUserModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RTK_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID,");
            sql.addSql("   RKU_TYPE,");
            sql.addSql("   RKU_AUID,");
            sql.addSql("   RKU_ADATE,");
            sql.addSql("   RKU_EUID,");
            sql.addSql("   RKU_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_KEIRO_USER");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID in ( ");
            keiroDao.writeSelect(sql, param, jkbn);
            sql.addSql("   )  ");
            sql.addSql(" order by  RTK_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateKeiroUserFromRs(rs));
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
     * <p>Select RNG_TEMPLATE_KEIRO_USER
     * @param rtkSid RTK_SID
     * @param usrSid USR_SID
     * @param grpSid GRP_SID
     * @param posSid POS_SID
     * @return RNG_TEMPLATE_KEIRO_USERModel
     * @throws SQLException SQL実行例外
     */
    public RngTemplateKeiroUserModel
    select(int rtkSid, int usrSid, int grpSid, int posSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngTemplateKeiroUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTK_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID,");
            sql.addSql("   RKU_TYPE,");
            sql.addSql("   RKU_AUID,");
            sql.addSql("   RKU_ADATE,");
            sql.addSql("   RKU_EUID,");
            sql.addSql("   RKU_EDATE");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_KEIRO_USER");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   POS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtkSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(grpSid);
            sql.addIntValue(posSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngTemplateKeiroUserFromRs(rs);
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
     * <p>Select RNG_TEMPLATE_KEIRO_USER
     * @param rtkSid RTK_SID
     * @return RNG_TEMPLATE_KEIRO_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateKeiroUserModel>
    select(int rtkSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        List<RngTemplateKeiroUserModel> ret = new ArrayList<RngTemplateKeiroUserModel>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTK_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   POS_SID,");
            sql.addSql("   RKU_TYPE,");
            sql.addSql("   RKU_AUID,");
            sql.addSql("   RKU_ADATE,");
            sql.addSql("   RKU_EUID,");
            sql.addSql("   RKU_EDATE");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_KEIRO_USER");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtkSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateKeiroUserFromRs(rs));
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
     * <p>Delete RNG_TEMPLATE_KEIRO_USER
     * @param rtkSid RTK_SID
     * @param usrSid USR_SID
     * @param grpSid GRP_SID
     * @param posSid POS_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int rtkSid, int usrSid, int grpSid, int posSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_KEIRO_USER");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   POS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtkSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(grpSid);
            sql.addIntValue(posSid);

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
     * <p>Delete RNG_TEMPLATE_KEIRO
     * @param rtkList  List in RTK_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteNoUseStep(List<RngTemplateKeiroModel> rtkList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_KEIRO_USER");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());

            for (RngTemplateKeiroModel rtkMdl : rtkList) {
                sql.addIntValue(rtkMdl.getRtkSid());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count += pstmt.executeUpdate();
                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create RNG_TEMPLATE_KEIRO_USER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngTemplateKeiroUserModel
     * @throws SQLException SQL実行例外
     */
    private RngTemplateKeiroUserModel
    __getRngTemplateKeiroUserFromRs(ResultSet rs) throws SQLException {
        RngTemplateKeiroUserModel bean = new RngTemplateKeiroUserModel();
        bean.setRtkSid(rs.getInt("RTK_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setPosSid(rs.getInt("POS_SID"));
        bean.setRkuType(rs.getInt("RKU_TYPE"));
        bean.setRkuAuid(rs.getInt("RKU_AUID"));
        bean.setRkuAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKU_ADATE")));
        bean.setRkuEuid(rs.getInt("RKU_EUID"));
        bean.setRkuEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKU_EDATE")));
        return bean;
    }
}
