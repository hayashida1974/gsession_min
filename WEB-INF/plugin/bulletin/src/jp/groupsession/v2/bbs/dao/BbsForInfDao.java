package jp.groupsession.v2.bbs.dao;


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
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.model.BbsForInfModel;

/**
 * <p>BBS_FOR_INF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsForInfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsForInfDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsForInfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsForInfDao(Connection con) {
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
            sql.addSql("drop table BBS_FOR_INF");

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
            sql.addSql(" create table BBS_FOR_INF (");
            sql.addSql("   BFI_SID NUMBER(10,0) not null,");
            sql.addSql("   BFI_NAME varchar(150) not null,");
            sql.addSql("   BFI_CMT varchar(3000) not null,");
            sql.addSql("   BFI_SORT NUMBER(10,0) not null,");
            sql.addSql("   BFI_REPLY NUMBER(10,0) not null,");
            sql.addSql("   BFI_READ NUMBER(10,0) not null,");
            sql.addSql("   BIN_SID Date not null,");
            sql.addSql("   BFI_MREAD NUMBER(10,0) not null,");
            sql.addSql("   BFI_TEMPLATE_KBN NUMBER(10,0) not null,");
            sql.addSql("   BFI_TEMPLATE text,");
            sql.addSql("   BFI_TEMPLATE_TYPE NUMBER(10,0) not null,");
            sql.addSql("   BFI_TEMPLATE_WRITE NUMBER(10,0) not null,");
            sql.addSql("   BFI_AUID NUMBER(10,0) not null,");
            sql.addSql("   BFI_ADATE varchar(23) not null,");
            sql.addSql("   BFI_EUID NUMBER(10,0) not null,");
            sql.addSql("   BFI_EDATE varchar(23) not null,");
            sql.addSql("   BFI_DISK NUMBER(10,0) not null,");
            sql.addSql("   BFI_DISK_SIZE NUMBER(10,0),");
            sql.addSql("   BFI_WARN_DISK NUMBER(10,0) not null,");
            sql.addSql("   BFI_WARN_DISK_TH NUMBER(10,0),");
            sql.addSql("   BFI_LIMIT NUMBER(10,0) not null,");
            sql.addSql("   BFI_LIMIT_DATE NUMBER(10,0),");
            sql.addSql("   BFI_KEEP NUMBER(10,0) not null,");
            sql.addSql("   BFI_KEEP_DATE_Y NUMBER(10,0),");
            sql.addSql("   BFI_KEEP_DATE_M NUMBER(10,0),");
            sql.addSql("   BFI_LIMIT_ON NUMBER(10,0) not null,");
            sql.addSql("   BFI_PARENT_SID NUMBER(10,0) not null,");
            sql.addSql("   BFI_LEVEL NUMBER(10,0) not null,");
            sql.addSql("   BFI_FOLLOW_PARENT_MEM NUMBER(10,0) not null,");
            sql.addSql("   BFI_MIN_DIV NUMBER(10,0),");
            sql.addSql("   BFI_UNLIMITED NUMBER(10,0) not null");
            sql.addSql("   primary key (BFI_SID)");
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
     * <p>Insert BBS_FOR_INF Data Bindding JavaBean
     * @param bean BBS_FOR_INF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsForInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_FOR_INF(");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFI_NAME,");
            sql.addSql("   BFI_CMT,");
            sql.addSql("   BFI_SORT,");
            sql.addSql("   BFI_REPLY,");
            sql.addSql("   BFI_READ,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BFI_MREAD,");
            sql.addSql("   BFI_TEMPLATE_KBN,");
            sql.addSql("   BFI_TEMPLATE,");
            sql.addSql("   BFI_TEMPLATE_TYPE,");
            sql.addSql("   BFI_TEMPLATE_WRITE,");
            sql.addSql("   BFI_AUID,");
            sql.addSql("   BFI_ADATE,");
            sql.addSql("   BFI_EUID,");
            sql.addSql("   BFI_EDATE,");
            sql.addSql("   BFI_DISK,");
            sql.addSql("   BFI_DISK_SIZE,");
            sql.addSql("   BFI_WARN_DISK,");
            sql.addSql("   BFI_WARN_DISK_TH,");
            sql.addSql("   BFI_LIMIT,");
            sql.addSql("   BFI_LIMIT_DATE,");
            sql.addSql("   BFI_KEEP,");
            sql.addSql("   BFI_KEEP_DATE_Y,");
            sql.addSql("   BFI_KEEP_DATE_M,");
            sql.addSql("   BFI_LIMIT_ON,");
            sql.addSql("   BFI_PARENT_SID,");
            sql.addSql("   BFI_LEVEL,");
            sql.addSql("   BFI_FOLLOW_PARENT_MEM,");
            sql.addSql("   BFI_MIN_DIV,");
            sql.addSql("   BFI_UNLIMITED");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfiSid());
            sql.addStrValue(bean.getBfiName());
            sql.addStrValue(bean.getBfiCmt());
            sql.addIntValue(bean.getBfiSort());
            sql.addIntValue(bean.getBfiReply());
            sql.addIntValue(bean.getBfiRead());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getBfiMread());
            sql.addIntValue(bean.getBfiTemplateKbn());
            sql.addStrValue(bean.getBfiTemplate());
            sql.addIntValue(bean.getBfiTemplateType());
            sql.addIntValue(bean.getBfiTemplateWrite());
            sql.addIntValue(bean.getBfiAuid());
            sql.addDateValue(bean.getBfiAdate());
            sql.addIntValue(bean.getBfiEuid());
            sql.addDateValue(bean.getBfiEdate());
            sql.addIntValue(bean.getBfiDisk());
            sql.addIntValue(bean.getBfiDiskSize());
            sql.addIntValue(bean.getBfiWarnDisk());
            sql.addIntValue(bean.getBfiWarnDiskTh());
            sql.addIntValue(bean.getBfiLimit());
            sql.addIntValue(bean.getBfiLimitDate());
            sql.addIntValue(bean.getBfiKeep());
            sql.addIntValue(bean.getBfiKeepDateY());
            sql.addIntValue(bean.getBfiKeepDateM());
            sql.addIntValue(bean.getBfiLimitOn());
            sql.addIntValue(bean.getBfiParentSid());
            sql.addIntValue(bean.getBfiLevel());
            sql.addIntValue(bean.getBfiFollowParentMem());
            sql.addIntValue(bean.getBfiMinDiv());
            sql.addIntValue(bean.getBfiUnlimited());

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
     * <p>Insert BBS_FOR_INF Data Bindding JavaBean
     * @param beanList BBS_FOR_INF Data List
     * @throws SQLException SQL実行例外
     */
    public void insert(List<BbsForInfModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_FOR_INF(");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFI_NAME,");
            sql.addSql("   BFI_CMT,");
            sql.addSql("   BFI_SORT,");
            sql.addSql("   BFI_REPLY,");
            sql.addSql("   BFI_READ,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BFI_MREAD,");
            sql.addSql("   BFI_TEMPLATE_KBN,");
            sql.addSql("   BFI_TEMPLATE,");
            sql.addSql("   BFI_TEMPLATE_TYPE,");
            sql.addSql("   BFI_TEMPLATE_WRITE,");
            sql.addSql("   BFI_AUID,");
            sql.addSql("   BFI_ADATE,");
            sql.addSql("   BFI_EUID,");
            sql.addSql("   BFI_EDATE,");
            sql.addSql("   BFI_DISK,");
            sql.addSql("   BFI_DISK_SIZE,");
            sql.addSql("   BFI_WARN_DISK,");
            sql.addSql("   BFI_WARN_DISK_TH,");
            sql.addSql("   BFI_LIMIT,");
            sql.addSql("   BFI_LIMIT_DATE,");
            sql.addSql("   BFI_KEEP,");
            sql.addSql("   BFI_KEEP_DATE_Y,");
            sql.addSql("   BFI_KEEP_DATE_M,");
            sql.addSql("   BFI_LIMIT_ON,");
            sql.addSql("   BFI_PARENT_SID,");
            sql.addSql("   BFI_LEVEL,");
            sql.addSql("   BFI_FOLLOW_PARENT_MEM,");
            sql.addSql("   BFI_MIN_DIV,");
            sql.addSql("   BFI_UNLIMITED");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (BbsForInfModel bean : beanList) {
                sql.addIntValue(bean.getBfiSid());
                sql.addStrValue(bean.getBfiName());
                sql.addStrValue(bean.getBfiCmt());
                sql.addIntValue(bean.getBfiSort());
                sql.addIntValue(bean.getBfiReply());
                sql.addIntValue(bean.getBfiRead());
                sql.addLongValue(bean.getBinSid());
                sql.addIntValue(bean.getBfiMread());
                sql.addIntValue(bean.getBfiTemplateKbn());
                sql.addStrValue(bean.getBfiTemplate());
                sql.addIntValue(bean.getBfiTemplateType());
                sql.addIntValue(bean.getBfiTemplateWrite());
                sql.addIntValue(bean.getBfiAuid());
                sql.addDateValue(bean.getBfiAdate());
                sql.addIntValue(bean.getBfiEuid());
                sql.addDateValue(bean.getBfiEdate());
                sql.addIntValue(bean.getBfiDisk());
                sql.addIntValue(bean.getBfiDiskSize());
                sql.addIntValue(bean.getBfiWarnDisk());
                sql.addIntValue(bean.getBfiWarnDiskTh());
                sql.addIntValue(bean.getBfiLimit());
                sql.addIntValue(bean.getBfiLimitDate());
                sql.addIntValue(bean.getBfiKeep());
                sql.addIntValue(bean.getBfiKeepDateY());
                sql.addIntValue(bean.getBfiKeepDateM());
                sql.addIntValue(bean.getBfiLimitOn());
                sql.addIntValue(bean.getBfiParentSid());
                sql.addIntValue(bean.getBfiLevel());
                sql.addIntValue(bean.getBfiFollowParentMem());
                sql.addIntValue(bean.getBfiMinDiv());
                sql.addIntValue(bean.getBfiUnlimited());

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
     * フォーラム情報の更新を行う
     * @param bean 更新情報
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateBBSInf(BbsForInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" set ");
            sql.addSql("   BFI_NAME=?,");
            sql.addSql("   BFI_CMT=?,");
            sql.addSql("   BFI_SORT=?,");
            sql.addSql("   BFI_REPLY=?,");
            sql.addSql("   BFI_READ=?,");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   BFI_MREAD=?,");
            sql.addSql("   BFI_TEMPLATE_KBN=?,");
            sql.addSql("   BFI_TEMPLATE=?,");
            sql.addSql("   BFI_TEMPLATE_TYPE=?,");
            sql.addSql("   BFI_TEMPLATE_WRITE=?,");
            sql.addSql("   BFI_EUID=?,");
            sql.addSql("   BFI_EDATE=?,");
            sql.addSql("   BFI_DISK=?,");
            sql.addSql("   BFI_DISK_SIZE=?,");
            sql.addSql("   BFI_WARN_DISK=?,");
            sql.addSql("   BFI_WARN_DISK_TH=?,");
            sql.addSql("   BFI_LIMIT=?,");
            sql.addSql("   BFI_LIMIT_DATE=?,");
            sql.addSql("   BFI_KEEP=?,");
            sql.addSql("   BFI_KEEP_DATE_Y=?,");
            sql.addSql("   BFI_KEEP_DATE_M=?,");
            sql.addSql("   BFI_LIMIT_ON=?,");
            sql.addSql("   BFI_PARENT_SID=?,");
            sql.addSql("   BFI_LEVEL=?,");
            sql.addSql("   BFI_FOLLOW_PARENT_MEM=?,");
            sql.addSql("   BFI_MIN_DIV=?,");
            sql.addSql("   BFI_UNLIMITED=?");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getBfiName());
            sql.addStrValue(bean.getBfiCmt());
            sql.addIntValue(bean.getBfiSort());
            sql.addIntValue(bean.getBfiReply());
            sql.addIntValue(bean.getBfiRead());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getBfiMread());
            sql.addIntValue(bean.getBfiTemplateKbn());
            sql.addStrValue(bean.getBfiTemplate());
            sql.addIntValue(bean.getBfiTemplateType());
            sql.addIntValue(bean.getBfiTemplateWrite());
            sql.addIntValue(bean.getBfiEuid());
            sql.addDateValue(bean.getBfiEdate());
            sql.addIntValue(bean.getBfiDisk());
            sql.addIntValue(bean.getBfiDiskSize());
            sql.addIntValue(bean.getBfiWarnDisk());
            sql.addIntValue(bean.getBfiWarnDiskTh());
            sql.addIntValue(bean.getBfiLimit());
            sql.addIntValue(bean.getBfiLimitDate());
            sql.addIntValue(bean.getBfiKeep());
            sql.addIntValue(bean.getBfiKeepDateY());
            sql.addIntValue(bean.getBfiKeepDateM());
            sql.addIntValue(bean.getBfiLimitOn());
            sql.addIntValue(bean.getBfiParentSid());
            sql.addIntValue(bean.getBfiLevel());
            sql.addIntValue(bean.getBfiFollowParentMem());
            sql.addIntValue(bean.getBfiMinDiv());
            sql.addIntValue(bean.getBfiUnlimited());

            //where
            sql.addIntValue(bean.getBfiSid());

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
     * フォーラム情報の更新を行う
     * @param bean 更新情報
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateBBSSort(BbsForInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" set ");
            sql.addSql("   BFI_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfiSort());
            //where
            sql.addIntValue(bean.getBfiSid());

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
     * <p>Select BBS_FOR_INF All Data
     * @return List in BBS_FOR_INFModel
     * @throws SQLException SQL実行例外
     */
    public List< BbsForInfModel > select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList < BbsForInfModel > ret = new ArrayList < BbsForInfModel >();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFI_NAME,");
            sql.addSql("   BFI_CMT,");
            sql.addSql("   BFI_SORT,");
            sql.addSql("   BFI_REPLY,");
            sql.addSql("   BFI_READ,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BFI_MREAD,");
            sql.addSql("   BFI_TEMPLATE_KBN,");
            sql.addSql("   BFI_TEMPLATE,");
            sql.addSql("   BFI_TEMPLATE_TYPE,");
            sql.addSql("   BFI_TEMPLATE_WRITE,");
            sql.addSql("   BFI_AUID,");
            sql.addSql("   BFI_ADATE,");
            sql.addSql("   BFI_EUID,");
            sql.addSql("   BFI_EDATE,");
            sql.addSql("   BFI_DISK,");
            sql.addSql("   BFI_DISK_SIZE,");
            sql.addSql("   BFI_WARN_DISK,");
            sql.addSql("   BFI_WARN_DISK_TH,");
            sql.addSql("   BFI_LIMIT,");
            sql.addSql("   BFI_LIMIT_DATE,");
            sql.addSql("   BFI_KEEP,");
            sql.addSql("   BFI_KEEP_DATE_Y,");
            sql.addSql("   BFI_KEEP_DATE_M,");
            sql.addSql("   BFI_LIMIT_ON,");
            sql.addSql("   BFI_PARENT_SID,");
            sql.addSql("   BFI_LEVEL,");
            sql.addSql("   BFI_FOLLOW_PARENT_MEM,");
            sql.addSql("   BFI_MIN_DIV,");
            sql.addSql("   BFI_UNLIMITED");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" order by");
            sql.addSql("   BFI_SORT, BFI_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsForInfFromRs(rs));
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
     * <p>Select BBS_FOR_INF
     * @param bean BBS_FOR_INF Model
     * @return BBS_FOR_INFModel
     * @throws SQLException SQL実行例外
     */
    public BbsForInfModel select(BbsForInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsForInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFI_NAME,");
            sql.addSql("   BFI_CMT,");
            sql.addSql("   BFI_SORT,");
            sql.addSql("   BFI_REPLY,");
            sql.addSql("   BFI_READ,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BFI_MREAD,");
            sql.addSql("   BFI_TEMPLATE_KBN,");
            sql.addSql("   BFI_TEMPLATE,");
            sql.addSql("   BFI_TEMPLATE_TYPE,");
            sql.addSql("   BFI_TEMPLATE_WRITE,");
            sql.addSql("   BFI_AUID,");
            sql.addSql("   BFI_ADATE,");
            sql.addSql("   BFI_EUID,");
            sql.addSql("   BFI_EDATE,");
            sql.addSql("   BFI_DISK,");
            sql.addSql("   BFI_DISK_SIZE,");
            sql.addSql("   BFI_WARN_DISK,");
            sql.addSql("   BFI_WARN_DISK_TH,");
            sql.addSql("   BFI_LIMIT,");
            sql.addSql("   BFI_LIMIT_DATE,");
            sql.addSql("   BFI_KEEP,");
            sql.addSql("   BFI_KEEP_DATE_Y,");
            sql.addSql("   BFI_KEEP_DATE_M,");
            sql.addSql("   BFI_LIMIT_ON,");
            sql.addSql("   BFI_PARENT_SID,");
            sql.addSql("   BFI_LEVEL,");
            sql.addSql("   BFI_FOLLOW_PARENT_MEM,");
            sql.addSql("   BFI_MIN_DIV,");
            sql.addSql("   BFI_UNLIMITED");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfiSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsForInfFromRs(rs);
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
     * <p>Select BBS_FOR_INF
     * @param bfiSidList フォーラムSIDリスト
     * @return BBS_FOR_INFModelリスト
     * @throws SQLException SQL実行例外
     */
    public List<BbsForInfModel> select(List<Integer> bfiSidList) throws SQLException {

        List<BbsForInfModel> ret = new ArrayList<BbsForInfModel>();

        if (bfiSidList == null || bfiSidList.size() < 1) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFI_NAME,");
            sql.addSql("   BFI_CMT,");
            sql.addSql("   BFI_SORT,");
            sql.addSql("   BFI_REPLY,");
            sql.addSql("   BFI_READ,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BFI_MREAD,");
            sql.addSql("   BFI_TEMPLATE_KBN,");
            sql.addSql("   BFI_TEMPLATE,");
            sql.addSql("   BFI_TEMPLATE_TYPE,");
            sql.addSql("   BFI_TEMPLATE_WRITE,");
            sql.addSql("   BFI_AUID,");
            sql.addSql("   BFI_ADATE,");
            sql.addSql("   BFI_EUID,");
            sql.addSql("   BFI_EDATE,");
            sql.addSql("   BFI_DISK,");
            sql.addSql("   BFI_DISK_SIZE,");
            sql.addSql("   BFI_WARN_DISK,");
            sql.addSql("   BFI_WARN_DISK_TH,");
            sql.addSql("   BFI_LIMIT,");
            sql.addSql("   BFI_LIMIT_DATE,");
            sql.addSql("   BFI_KEEP,");
            sql.addSql("   BFI_KEEP_DATE_Y,");
            sql.addSql("   BFI_KEEP_DATE_M,");
            sql.addSql("   BFI_LIMIT_ON,");
            sql.addSql("   BFI_PARENT_SID,");
            sql.addSql("   BFI_LEVEL,");
            sql.addSql("   BFI_FOLLOW_PARENT_MEM,");
            sql.addSql("   BFI_MIN_DIV,");
            sql.addSql("   BFI_UNLIMITED");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID in (");

            for (int i = 0, n = bfiSidList.size(); i < n - 1; ++i) {
                sql.addSql("   ?,");
            }
            sql.addSql("   ?");
            sql.addSql("   )");

            for (int sid : bfiSidList) {
                sql.addIntValue(sid);
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsForInfFromRs(rs));
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
     * <p>指定したフォーラムが存在するかカウントする
     * @param bfiSid フォーラムSID
     * @return BBS_FOR_INFModel
     * @throws SQLException SQL実行例外
     */
    public int countBfi(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());
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
     * <p>指定されたフォーラムSIDからアイコンバイナリSIDを取得する
     * @param bfiSid フォーラムSID
     * @return アイコン画像バイナリSID
     * @throws SQLException SQL実行例外
     */
    public Long selectIcoBinSid(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Long ret = new Long(0);
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bfiSid);

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
     * <p>Select BBS_FOR_INF
     * @param sortNum ソート順
     * @return List
     * @throws SQLException SQL実行例外
     */
    public List < BbsForInfModel > select(int sortNum) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList < BbsForInfModel > ret = new ArrayList < BbsForInfModel >();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFI_NAME,");
            sql.addSql("   BFI_CMT,");
            sql.addSql("   BFI_SORT,");
            sql.addSql("   BFI_REPLY,");
            sql.addSql("   BFI_READ,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BFI_MREAD,");
            sql.addSql("   BFI_TEMPLATE_KBN,");
            sql.addSql("   BFI_TEMPLATE,");
            sql.addSql("   BFI_TEMPLATE_TYPE,");
            sql.addSql("   BFI_TEMPLATE_WRITE,");
            sql.addSql("   BFI_AUID,");
            sql.addSql("   BFI_ADATE,");
            sql.addSql("   BFI_EUID,");
            sql.addSql("   BFI_EDATE,");
            sql.addSql("   BFI_DISK,");
            sql.addSql("   BFI_DISK_SIZE,");
            sql.addSql("   BFI_WARN_DISK,");
            sql.addSql("   BFI_WARN_DISK_TH,");
            sql.addSql("   BFI_LIMIT,");
            sql.addSql("   BFI_LIMIT_DATE,");
            sql.addSql("   BFI_KEEP,");
            sql.addSql("   BFI_KEEP_DATE_Y,");
            sql.addSql("   BFI_KEEP_DATE_M,");
            sql.addSql("   BFI_LIMIT_ON,");
            sql.addSql("   BFI_PARENT_SID,");
            sql.addSql("   BFI_LEVEL,");
            sql.addSql("   BFI_FOLLOW_PARENT_MEM,");
            sql.addSql("   BFI_MIN_DIV,");
            sql.addSql("   BFI_UNLIMITED");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SORT > ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sortNum);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsForInfFromRs(rs));
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
     * <p>Select BBS_FOR_INF
     * @return int count
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(BFI_SORT) as CNT");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SORT=0");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
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
     * <p>Select BBS_FOR_INF All Data
     * @return int getMaxSort
     * @throws SQLException SQL実行例外
     */
    public int getMaxSort() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(BFI_SORT)");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_INF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            ret = rs.getInt("BFI_SORT");
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Delete BBS_FOR_INF
     * @param bean BBS_FOR_INF Model
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(BbsForInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfiSid());

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
     * <p>指定されたフォーラムSIDとアイコンバイナリSIDの組み合わせが存在するかを確認する
     * @param bfiSid フォーラムSID
     * @param icoBinSid アイコンバイナリSID
     * @return 結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existBbsForIco(int bfiSid, Long icoBinSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BFI_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bfiSid);
            sql.addLongValue(icoBinSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            ret = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }

    /**
     * <br>[機  能] 指定したフォーラムSIDからスレッド閲覧状態=「既読」のフォーラムSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSidList フォーラムSIDリスト
     * @return フォーラムSID
     * @throws SQLException SQL実行例外
     */
    public List <Integer> getInitForumSidList(
            List<Integer> bfiSidList)
                    throws SQLException {

        List <Integer> ret = new ArrayList <Integer>();

        if (bfiSidList == null || bfiSidList.isEmpty()) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   BFI_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_READ = ?");
            sql.addIntValue(GSConstBulletin.NEWUSER_THRE_VIEW_YES);

            sql.addSql(" and");
            sql.addSql("   BFI_SID in (");
            for (int idx = 0; idx < bfiSidList.size(); idx++) {
                if (idx > 0) {
                    sql.addSql("   ,?");
                } else {
                    sql.addSql("   ?");
                }
                sql.addIntValue(bfiSidList.get(idx));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("BFI_SID"));
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
     * <br>[機  能] フォーラムのうち、指定したグループ or ユーザがメンバーとして登録されているものを除外する
     * <br>[解  説]
     * <br>[備  考]
     * @param forumSidList 対象のフォーラムSID一覧
     * @param excForumSidList 除外するフォーラムSID一覧
     * @return フォーラムSID一覧
     * @throws SQLException SQL実行例外
     */
    public List<Integer> excludeForumSidList(
            List<Integer> forumSidList,
            List<Integer> excForumSidList)
                    throws SQLException {

        if (forumSidList == null || forumSidList.isEmpty()
        || excForumSidList == null || excForumSidList.isEmpty()) {
            return forumSidList;
        }

        List<Integer> bfiSidList = new ArrayList<Integer>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BFI_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where");
            sql.addSql("   BFI_SID in (");
            for (int idx = 0; idx < forumSidList.size(); idx++) {
                if (idx > 0) {
                    sql.addSql("   ,?");
                } else {
                    sql.addSql("   ?");
                }
                sql.addIntValue(forumSidList.get(idx));
            }
            sql.addSql("   )");

            sql.addSql(" and");
            sql.addSql("   BFI_SID not in (");
            for (int idx = 0; idx < excForumSidList.size(); idx++) {
                if (idx > 0) {
                    sql.addSql("   ,?");
                } else {
                    sql.addSql("   ?");
                }
                sql.addIntValue(excForumSidList.get(idx));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                bfiSidList.add(rs.getInt("BFI_SID"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return bfiSidList;
    }

    /**
     * <p>Create BBS_FOR_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsForInfModel
     * @throws SQLException SQL実行例外
     */
    private BbsForInfModel __getBbsForInfFromRs(ResultSet rs) throws SQLException {
        BbsForInfModel bean = new BbsForInfModel();
        bean.setBfiSid(rs.getInt("BFI_SID"));
        bean.setBfiName(rs.getString("BFI_NAME"));
        bean.setBfiCmt(rs.getString("BFI_CMT"));
        bean.setBfiSort(rs.getInt("BFI_SORT"));
        bean.setBfiReply(rs.getInt("BFI_REPLY"));
        bean.setBfiRead(rs.getInt("BFI_READ"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setBfiMread(rs.getInt("BFI_MREAD"));
        bean.setBfiTemplateKbn(rs.getInt("BFI_TEMPLATE_KBN"));
        bean.setBfiTemplate(rs.getString("BFI_TEMPLATE"));
        bean.setBfiTemplateType(rs.getInt("BFI_TEMPLATE_TYPE"));
        bean.setBfiTemplateWrite(rs.getInt("BFI_TEMPLATE_WRITE"));
        bean.setBfiAuid(rs.getInt("BFI_AUID"));
        bean.setBfiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BFI_ADATE")));
        bean.setBfiEuid(rs.getInt("BFI_EUID"));
        bean.setBfiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BFI_EDATE")));
        bean.setBfiDisk(rs.getInt("BFI_DISK"));
        bean.setBfiDiskSize(rs.getInt("BFI_DISK_SIZE"));
        bean.setBfiWarnDisk(rs.getInt("BFI_WARN_DISK"));
        bean.setBfiWarnDiskTh(rs.getInt("BFI_WARN_DISK_TH"));
        bean.setBfiLimit(rs.getInt("BFI_LIMIT"));
        bean.setBfiLimitDate(rs.getInt("BFI_LIMIT_DATE"));
        bean.setBfiKeep(rs.getInt("BFI_KEEP"));
        bean.setBfiKeepDateY(rs.getInt("BFI_KEEP_DATE_Y"));
        bean.setBfiKeepDateM(rs.getInt("BFI_KEEP_DATE_M"));
        bean.setBfiLimitOn(rs.getInt("BFI_LIMIT_ON"));
        bean.setBfiParentSid(rs.getInt("BFI_PARENT_SID"));
        bean.setBfiLevel(rs.getInt("BFI_LEVEL"));
        bean.setBfiFollowParentMem(rs.getInt("BFI_FOLLOW_PARENT_MEM"));
        bean.setBfiMinDiv(rs.getInt("BFI_MIN_DIV"));
        bean.setBfiUnlimited(rs.getInt("BFI_UNLIMITED"));

        return bean;
    }

    /**
     * <p>階層構造用にソートしたフォーラムを取得する
     * @return List<BbsForInfModel> ソート済みのフォーラム情報のリスト
     * @throws SQLException SQL実行例外
     */
    public List<BbsForInfModel> selectWithHierarchy() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<BbsForInfModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFI_NAME,");
            sql.addSql("   BFI_CMT,");
            sql.addSql("   BFI_SORT,");
            sql.addSql("   BFI_REPLY,");
            sql.addSql("   BFI_READ,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BFI_MREAD,");
            sql.addSql("   BFI_TEMPLATE_KBN,");
            sql.addSql("   BFI_TEMPLATE,");
            sql.addSql("   BFI_TEMPLATE_TYPE,");
            sql.addSql("   BFI_TEMPLATE_WRITE,");
            sql.addSql("   BFI_AUID,");
            sql.addSql("   BFI_ADATE,");
            sql.addSql("   BFI_EUID,");
            sql.addSql("   BFI_EDATE,");
            sql.addSql("   BFI_DISK,");
            sql.addSql("   BFI_DISK_SIZE,");
            sql.addSql("   BFI_WARN_DISK,");
            sql.addSql("   BFI_WARN_DISK_TH,");
            sql.addSql("   BFI_LIMIT,");
            sql.addSql("   BFI_LIMIT_DATE,");
            sql.addSql("   BFI_KEEP,");
            sql.addSql("   BFI_KEEP_DATE_Y,");
            sql.addSql("   BFI_KEEP_DATE_M,");
            sql.addSql("   BFI_LIMIT_ON,");
            sql.addSql("   BFI_PARENT_SID,");
            sql.addSql("   BFI_LEVEL,");
            sql.addSql("   BFI_FOLLOW_PARENT_MEM,");
            sql.addSql("   BFI_MIN_DIV,");
            sql.addSql("   BFI_UNLIMITED");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" order by");
            sql.addSql("   BFI_LEVEL desc,");
            sql.addSql("   BFI_PARENT_SID,");
            sql.addSql("   BFI_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            List<BbsForInfModel> bfiList = new ArrayList<>();
            HashMap<Integer, List<BbsForInfModel>> familyMap = new HashMap<>();
            BbsForInfModel resultModel;
            int savedParentSid = -1;
            while (rs.next()) {
                resultModel = __getBbsForInfFromRs(rs);

                if (savedParentSid != -1
                        && savedParentSid != resultModel.getBfiParentSid()) {
                    //親が同じフォーラムのフォーラム情報リストをマップにセット
                    familyMap.put(savedParentSid, bfiList);
                    bfiList = new ArrayList<>();
                }

                //同じ親を持つフォーラムのリストを作成
                bfiList.add(resultModel);

                savedParentSid = resultModel.getBfiParentSid();
            }
            //親が同じフォーラムのフォーラム情報リストをマップにセット
            familyMap.put(savedParentSid, bfiList);

            //階層を考慮してリストを並べ替える
            if (familyMap.size() > 0) {
                __getHisrarchyList(
                        ret, GSConstBulletin.BBS_DEFAULT_PFORUM_SID, familyMap);
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
     * <br>[機  能] 階層構造を考慮したリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ret フォーラム情報リスト
     * @param parentSid 親フォーラムSID
     * @param familyMap Key:親フォーラムSID Value:フォーラム情報 のHashMap
     */
    private void __getHisrarchyList(
            List<BbsForInfModel> ret,
            int parentSid,
            HashMap<Integer, List<BbsForInfModel>> familyMap) {
        //共通の親を持つフォーラムのリストを取得
        List<BbsForInfModel> childList = familyMap.get(parentSid);

        if (childList == null || childList.size() < 1) {
            return;
        }

        for (BbsForInfModel mdl : childList) {
            //リストからフォーラム1つを結果リストに追加
            ret.add(mdl);

            if (familyMap.containsKey(mdl.getBfiSid())) {
                //このフォーラムを親にしているフォーラムのリストを取得する(再帰処理)
                __getHisrarchyList(ret, mdl.getBfiSid(), familyMap);
            }
        }
    }

    /**
     * <br>[機  能] 指定したフォーラムを親に持つフォーラムから最大の並び順を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param parentSid 親フォーラムSID
     * @return count
     * @exception SQLException SQL実行時例外
     */
    public int getMaxSortOfChild(int parentSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(BFI_SORT) as MAX");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_PARENT_SID=?;");

            sql.addIntValue(parentSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * フォーラム情報の階層レベルの更新を行う
     * @param bean 更新情報
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateBbsLevel(BbsForInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" set ");
            sql.addSql("   BFI_LEVEL=?");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBfiLevel());
            //where
            sql.addIntValue(bean.getBfiSid());

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
     * <br>[機  能] 指定した階層以下のフォーラムSIDを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param level 階層レベル
     * @return フォーラムSIDのリスト
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getLowerLevelForumSid(int level)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFI_PARENT_SID,");
            sql.addSql("   BFI_LEVEL");
            sql.addSql(" from ");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where ");
            sql.addSql("   BFI_LEVEL>=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(level);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("BFI_SID"));
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
     * <br>[機  能] フォーラムメンバーを参照する際のフォーラムSIDを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @throws SQLException SQL実行時例外
     * @return フォーラムメンバーを参照する際のフォーラムSID
     */
    public int getBfiSidForMemberInfo(int bfiSid)
            throws SQLException {

        int ret = -1;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        int followParentMem = GSConstBulletin.FOLLOW_PARENT_MEMBER_YES;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BFI_PARENT_SID,");
            sql.addSql("   BFI_FOLLOW_PARENT_MEM");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_INF");
            sql.addSql(" where");
            sql.addSql("   BFI_SID = ?");

            int searchBfiSid = bfiSid;
            pstmt = con.prepareStatement(sql.toSqlString());

            int idx = 0;
            while (idx < GSConstBulletin.BBS_FORUM_MAX_LEVEL) {
                sql.addIntValue(searchBfiSid);

                log__.info(sql.toLogString());

                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    followParentMem = rs.getInt("BFI_FOLLOW_PARENT_MEM");
                    if (followParentMem == GSConstBulletin.FOLLOW_PARENT_MEMBER_NO) {
                        ret = searchBfiSid;
                        break;
                    }

                    searchBfiSid = rs.getInt("BFI_PARENT_SID");
                }

                pstmt.clearParameters();
                sql.clearValue();
                ++idx;
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
     * <br>[機  能] 指定されたフォーラムから下位のフォーラムを持つフォーラムを抽出する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSidList フォーラムSID
     * @return 下位のフォーラムを持つフォーラムのフォーラムSID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> extractParentForum(List<Integer> bfiSidList)
            throws SQLException {

        List<Integer> extractForumList = new ArrayList<Integer>();
        if (bfiSidList == null || bfiSidList.isEmpty()) {
            return extractForumList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select BFI_PARENT_SID from BBS_FOR_INF");
            sql.addSql(" where");
            sql.addSql("   BFI_PARENT_SID in (");

            for (int idx = 0; idx < bfiSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(bfiSidList.get(idx));
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                extractForumList.add(rs.getInt("BFI_PARENT_SID"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return extractForumList;
    }

    /**
     * <br>[機  能] 指定したフォーラムの親フォーラム名を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @throws SQLException SQL実行時例外
     * @return フォーラムメンバーを参照する際のフォーラムSID
     */
    public String getParentForum(int bfiSid)
            throws SQLException {

        String ret = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select ");
            sql.addSql("    BFI_NAME ");
            sql.addSql("  from ");
            sql.addSql("    BBS_FOR_INF ");
            sql.addSql("  where ");
            sql.addSql("    BFI_SID =  ");
            sql.addSql("    ( ");
            sql.addSql("      select  ");
            sql.addSql("        BFI_PARENT_SID  ");
            sql.addSql("      from  ");
            sql.addSql("        BBS_FOR_INF ");
            sql.addSql("      where ");
            sql.addSql("        BFI_SID = ? ");
            sql.addSql("    ) ");
            sql.addIntValue(bfiSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getString("BFI_NAME");
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
