package jp.groupsession.v2.sml.dao;


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
import jp.groupsession.v2.sml.model.SmlAsakModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>SML_ASAK Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlAsakDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlAsakDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlAsakDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlAsakDao(Connection con) {
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
            sql.addSql("drop table SML_ASAK");

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
            sql.addSql(" create table SML_ASAK (");
            sql.addSql("   SAC_SID NUMBER(4,0) not null,");
            sql.addSql("   SMS_SID NUMBER(4,0) not null,");
            sql.addSql("   SMJ_SENDKBN NUMBER(4,0) not null,");
            sql.addSql("   SMS_AUID NUMBER(4,0) not null,");
            sql.addSql("   SMS_ADATE varchar(8) not null,");
            sql.addSql("   SMS_EUID NUMBER(4,0) not null,");
            sql.addSql("   SMS_EDATE varchar(8) not null,");
            sql.addSql("   primary key (USR_SID,SMS_SID)");
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
     * <p>Insert SML_ASAK Data Bindding JavaBean
     * @param bean SML_ASAK Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlAsakModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_ASAK(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMS_SID,");
            sql.addSql("   SMJ_SENDKBN,");
            sql.addSql("   SMS_AUID,");
            sql.addSql("   SMS_ADATE,");
            sql.addSql("   SMS_EUID,");
            sql.addSql("   SMS_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSmsSid());
            sql.addIntValue(bean.getSmjSendkbn());
            sql.addIntValue(bean.getSmsAuid());
            sql.addDateValue(bean.getSmsAdate());
            sql.addIntValue(bean.getSmsEuid());
            sql.addDateValue(bean.getSmsEdate());
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
     * <p>Update SML_ASAK Data Bindding JavaBean
     * @param bean SML_ASAK Data Bindding JavaBean
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlAsakModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ASAK");
            sql.addSql(" set ");
            sql.addSql("   SMS_AUID=?,");
            sql.addSql("   SMS_ADATE=?,");
            sql.addSql("   SMS_EUID=?,");
            sql.addSql("   SMS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmsAuid());
            sql.addDateValue(bean.getSmsAdate());
            sql.addIntValue(bean.getSmsEuid());
            sql.addDateValue(bean.getSmsEdate());
            //where
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSmsSid());

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
     * <p>Select SML_ASAK All Data
     * @return List in SML_ASAKModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlAsakModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlAsakModel> ret = new ArrayList<SmlAsakModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMS_SID,");
            sql.addSql("   SMJ_SENDKBN,");
            sql.addSql("   SMS_AUID,");
            sql.addSql("   SMS_ADATE,");
            sql.addSql("   SMS_EUID,");
            sql.addSql("   SMS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_ASAK");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlAsakFromRs(rs));
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
     * <p>Select SML_ASAK
     * @param bean SML_ASAK Model
     * @return SML_ASAKModel
     * @throws SQLException SQL実行例外
     */
    public SmlAsakModel select(SmlAsakModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlAsakModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMS_SID,");
            sql.addSql("   SMJ_SENDKBN,");
            sql.addSql("   SMS_AUID,");
            sql.addSql("   SMS_ADATE,");
            sql.addSql("   SMS_EUID,");
            sql.addSql("   SMS_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_ASAK");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSmsSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlAsakFromRs(rs);
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
     * <p>Delete SML_ASAK
     * @param bean SML_ASAK Model
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(SmlAsakModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_ASAK");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSmsSid());

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
     * <p>Delete SML_ASAK
     * @param bean SML_ASAK Model
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteFromMailSid(SmlAsakModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_ASAK");
            sql.addSql(" where ");
            sql.addSql("   SMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmsSid());

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
     * <br>[機  能] 指定されたメールSIDのメッセージを物理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param msgSid メールSID配列
     * @throws SQLException SQL実行例外
     */
    public void deleteMsgButuri(Integer[] msgSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_ASAK");
            sql.addSql(" where ");
            sql.addSql("   SMS_SID in (");

            for (int i = 0; i < msgSid.length; i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addIntValue(msgSid[i]);
            }
            sql.addSql(")");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定されたメールSIDのメッセージを物理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid セッションユーザSID
     * @param target メールSID配列
     * @throws SQLException SQL実行例外
     */
    public void deleteGomibakoMsgButuri(int sacSid, ArrayList<Integer> target)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_ASAK");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql(" and");
            sql.addSql("   SMS_SID in (");

            for (int idx = 0; idx < target.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addIntValue(target.get(idx));
                idx += 1;
            }
            sql.addSql(")");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定されたメールSIDのメッセージを物理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param msgSid メールSID配列
     * @throws SQLException SQL実行例外
     */
    public void deleteMsgButuri(int sacSid, String[] msgSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_ASAK");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql(" and");
            sql.addSql("   SMS_SID in (");

            for (int i = 0; i < msgSid.length; i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                String mailKey = msgSid[i].substring(1);
                sql.addIntValue(Integer.parseInt(mailKey));
            }
            sql.addSql(")");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定されたメールSIDのメッセージを物理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param mailSid メールSID
     * @throws SQLException SQL実行例外
     */
    public void deleteMsgButuri(int sacSid, int mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_ASAK");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMS_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(mailSid);
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
     * <p>Create SML_ASAK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlAsakModel
     * @throws SQLException SQL実行例外
     */
    private SmlAsakModel __getSmlAsakFromRs(ResultSet rs) throws SQLException {
        SmlAsakModel bean = new SmlAsakModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSmsSid(rs.getInt("SMS_SID"));
        bean.setSmjSendkbn(rs.getInt("SMJ_SENDKBN"));
        bean.setSmsAuid(rs.getInt("SMS_AUID"));
        bean.setSmsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMS_ADATE")));
        bean.setSmsEuid(rs.getInt("SMS_EUID"));
        bean.setSmsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMS_EDATE")));
        return bean;
    }
}
