package jp.groupsession.v2.sml.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.model.SmlJmeisModel;

/**
 * <p>SMAIL_JMEIS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlJmeisDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlJmeisDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlJmeisDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlJmeisDao(Connection con) {
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
            sql.addSql("drop table SML_JMEIS");

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
            sql.addSql(" create table SML_JMEIS (");
            sql.addSql("   SAC_SID NUMBER(4,0) not null,");
            sql.addSql("   SMJ_SID NUMBER(4,0) not null,");
            sql.addSql("   SMJ_OPKBN NUMBER(4,0),");
            sql.addSql("   SMJ_OPDATE varchar(8),");
            sql.addSql("   SMJ_JKBN NUMBER(4,0),");
            sql.addSql("   SMJ_FWKBN NUMBER(4,0),");
            sql.addSql("   SMJ_SENDKBN NUMBER(4,0),");
            sql.addSql("   SMJ_AUID NUMBER(4,0) not null,");
            sql.addSql("   SMJ_ADATE varchar(8) not null,");
            sql.addSql("   SMJ_EUID NUMBER(4,0) not null,");
            sql.addSql("   SMJ_EDATE varchar(8) not null,");
            sql.addSql("   SMJ_RTN_KBN integer not null,");
            sql.addSql("   SMJ_FW_KBN integer not null,");
            sql.addSql("   primary key (SAC_SID,SMJ_SID)");
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
     * <p>Insert SML_JMEIS Data Bindding JavaBean
     * @param bean SML_JMEIS Data Bindding JavaBean
     * @return count インサート件数
     * @throws SQLException SQL実行例外
     */
    public int insert(SmlJmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_JMEIS(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMJ_SID,");
            sql.addSql("   SMJ_OPKBN,");
            sql.addSql("   SMJ_OPDATE,");
            sql.addSql("   SMJ_JKBN,");
            sql.addSql("   SMJ_FWKBN,");
            sql.addSql("   SMJ_SENDKBN,");
            sql.addSql("   SMJ_AUID,");
            sql.addSql("   SMJ_ADATE,");
            sql.addSql("   SMJ_EUID,");
            sql.addSql("   SMJ_EDATE,");
            sql.addSql("   SMJ_RTN_KBN,");
            sql.addSql("   SMJ_FW_KBN");
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
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSmjSid());
            sql.addIntValue(bean.getSmjOpkbn());
            sql.addDateValue(bean.getSmjOpdate());
            sql.addIntValue(bean.getSmjJkbn());
            sql.addIntValue(bean.getSmjFwkbn());
            sql.addIntValue(bean.getSmjSendkbn());
            sql.addIntValue(bean.getSmjAuid());
            sql.addDateValue(bean.getSmjAdate());
            sql.addIntValue(bean.getSmjEuid());
            sql.addDateValue(bean.getSmjEdate());
            sql.addIntValue(bean.getSmjRtnKbn());
            sql.addIntValue(bean.getSmjFwKbn());
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
     * <p>Insert SML_JMEIS Data Bindding JavaBean
     * @param beanList SML_JMEIS DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<SmlJmeisModel> beanList) throws SQLException {

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
            sql.addSql(" SML_JMEIS(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMJ_SID,");
            sql.addSql("   SMJ_OPKBN,");
            sql.addSql("   SMJ_OPDATE,");
            sql.addSql("   SMJ_JKBN,");
            sql.addSql("   SMJ_FWKBN,");
            sql.addSql("   SMJ_SENDKBN,");
            sql.addSql("   SMJ_AUID,");
            sql.addSql("   SMJ_ADATE,");
            sql.addSql("   SMJ_EUID,");
            sql.addSql("   SMJ_EDATE,");
            sql.addSql("   SMJ_RTN_KBN,");
            sql.addSql("   SMJ_FW_KBN");
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
            for (SmlJmeisModel bean : beanList) {
                sql.addIntValue(bean.getSacSid());
                sql.addIntValue(bean.getSmjSid());
                sql.addIntValue(bean.getSmjOpkbn());
                sql.addDateValue(bean.getSmjOpdate());
                sql.addIntValue(bean.getSmjJkbn());
                sql.addIntValue(bean.getSmjFwkbn());
                sql.addIntValue(bean.getSmjSendkbn());
                sql.addIntValue(bean.getSmjAuid());
                sql.addDateValue(bean.getSmjAdate());
                sql.addIntValue(bean.getSmjEuid());
                sql.addDateValue(bean.getSmjEdate());
                sql.addIntValue(bean.getSmjRtnKbn());
                sql.addIntValue(bean.getSmjFwKbn());
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
     * <p>Update SML_JMEIS Data Bindding JavaBean
     * @param bean SML_JMEIS Data Bindding JavaBean
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlJmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set ");
            sql.addSql("   SMJ_OPKBN=?,");
            sql.addSql("   SMJ_OPDATE=?,");
            sql.addSql("   SMJ_JKBN=?,");
            sql.addSql("   SMJ_FWKBN=?,");
            sql.addSql("   SMJ_AUID=?,");
            sql.addSql("   SMJ_ADATE=?,");
            sql.addSql("   SMJ_EUID=?,");
            sql.addSql("   SMJ_EDATE=?,");
            sql.addSql("   SMJ_RTN_KBN=?,");
            sql.addSql("   SMJ_FW_KBN=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmjOpkbn());
            sql.addDateValue(bean.getSmjOpdate());
            sql.addIntValue(bean.getSmjJkbn());
            sql.addIntValue(bean.getSmjFwkbn());
            sql.addIntValue(bean.getSmjAuid());
            sql.addDateValue(bean.getSmjAdate());
            sql.addIntValue(bean.getSmjEuid());
            sql.addDateValue(bean.getSmjEdate());
            sql.addIntValue(bean.getSmjRtnKbn());
            sql.addIntValue(bean.getSmjFwKbn());
            //where
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSmjSid());

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
     * <p>受信テーブルの返信区分を変更
     * @param kbn 状態区分
     * @param sacSid アカウントSID
     * @param mailSid メールSID
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateHenshin(int kbn, int sacSid, int mailSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set ");
            sql.addSql("   SMJ_RTN_KBN=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(kbn);
            sql.addIntValue(sacSid);
            sql.addIntValue(mailSid);

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
     * <p>受信テーブルの転送区分を変更
     * @param kbn 状態区分
     * @param sacSid アカウントSID
     * @param mailSid メールSID
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateFw(int kbn, int sacSid, int mailSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set ");
            sql.addSql("   SMJ_FW_KBN=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(kbn);
            sql.addIntValue(sacSid);
            sql.addIntValue(mailSid);

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
     * <p>Select SML_JMEIS All Data
     * @return List in SML_JMEISModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlJmeisModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlJmeisModel> ret = new ArrayList<SmlJmeisModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMJ_SID,");
            sql.addSql("   SMJ_OPKBN,");
            sql.addSql("   SMJ_OPDATE,");
            sql.addSql("   SMJ_JKBN,");
            sql.addSql("   SMJ_FWKBN,");
            sql.addSql("   SMJ_SENDKBN,");
            sql.addSql("   SMJ_AUID,");
            sql.addSql("   SMJ_ADATE,");
            sql.addSql("   SMJ_EUID,");
            sql.addSql("   SMJ_EDATE,");
            sql.addSql("   SMJ_RTN_KBN,");
            sql.addSql("   SMJ_FW_KBN");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlJmeisFromRs(rs));
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
     * <p>Select SML_JMEIS All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in SML_JMEISModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlJmeisModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlJmeisModel> ret = new ArrayList<SmlJmeisModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMJ_SID,");
            sql.addSql("   SMJ_OPKBN,");
            sql.addSql("   SMJ_OPDATE,");
            sql.addSql("   SMJ_JKBN,");
            sql.addSql("   SMJ_FWKBN,");
            sql.addSql("   SMJ_SENDKBN,");
            sql.addSql("   SMJ_AUID,");
            sql.addSql("   SMJ_ADATE,");
            sql.addSql("   SMJ_EUID,");
            sql.addSql("   SMJ_EDATE,");
            sql.addSql("   SMJ_RTN_KBN,");
            sql.addSql("   SMJ_FW_KBN");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" order by ");
            sql.addSql("   SAC_SID asc,");
            sql.addSql("   SMJ_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlJmeisFromRs(rs));
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
     * <p>count SML_JMEIS All Data
     * @return List in SML_JMEISModel
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
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");

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
     * <p>Select SML_JMEIS
     * @param bean SML_JMEIS Model
     * @return SML_JMEISModel
     * @throws SQLException SQL実行例外
     */
    public SmlJmeisModel select(SmlJmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlJmeisModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMJ_SID,");
            sql.addSql("   SMJ_OPKBN,");
            sql.addSql("   SMJ_OPDATE,");
            sql.addSql("   SMJ_JKBN,");
            sql.addSql("   SMJ_FWKBN,");
            sql.addSql("   SMJ_SENDKBN,");
            sql.addSql("   SMJ_AUID,");
            sql.addSql("   SMJ_ADATE,");
            sql.addSql("   SMJ_EUID,");
            sql.addSql("   SMJ_EDATE,");
            sql.addSql("   SMJ_RTN_KBN,");
            sql.addSql("   SMJ_FW_KBN");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSmjSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlJmeisFromRs(rs);
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
     * <p>Delete SML_JMEIS
     * @param bean SML_JMEIS Model
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(SmlJmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSmjSid());

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
     * <p>ショートメールSIDを指定し受信データを削除します
     * @param smjSid メールSID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int smjSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" where ");
            sql.addSql("   SMJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smjSid);

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
     * @param smjSidList メールSID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteJMail(List<String> smjSidList) throws SQLException {

        if (smjSidList == null || smjSidList.size() <= 0) {
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
            sql.addSql("   SML_JMEIS");
            sql.addSql(" where ");
            if (smjSidList.size() == 1) {
                sql.addSql("   SMJ_SID = ?");
                sql.addIntValue(Integer.parseInt(smjSidList.get(0)));
            } else {
                sql.addSql("   SMJ_SID in (");
                for (int idx = 0; idx < smjSidList.size() - 1; idx++) {
                    sql.addSql("     ?,");
                    sql.addIntValue(Integer.parseInt(smjSidList.get(idx)));
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(smjSidList.get(smjSidList.size() - 1)));
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
     * <br>[機  能] 未開封のメッセージ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param jkbn メール区分
     * @return cnt 未開封のメッセージ件数
     * @throws SQLException SQL実行例外
     */
    public int getUnopenedMsgCnt(int sacSid, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SMJ_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_OPKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.OPKBN_UNOPENED);
            sql.addIntValue(jkbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt;
    }

    /**
     * <br>[機  能] 未開封のメッセージ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param accountList アカウントリスト
     * @param jkbn メール区分
     * @return cnt 未開封のメッセージ件数
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, Integer> getAccountUnopenedMsgCnt(
            List<SmlAccountModel> accountList, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, Integer> cntMap = new HashMap<>();
        if (accountList.size() == 0) {
            return cntMap;
        }
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   count(SMJ_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID in (");
            for (int i = 0; i < accountList.size(); i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("     ?");
                sql.addIntValue(accountList.get(i).getSacSid());
            }
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   SMJ_OPKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_JKBN = ?");
            sql.addSql(" group by");
            sql.addSql("   SAC_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstSmail.OPKBN_UNOPENED);
            sql.addIntValue(jkbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                cntMap.put(rs.getInt("SAC_SID"), rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cntMap;
    }

    /**
     * <br>[機  能] 未開封のメッセージを開封済に更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param param SmlJmeisModel
     * @throws SQLException SQL実行例外
     */
    public void updateOpKbn(int usrSid, SmlJmeisModel param) throws SQLException {

        PreparedStatement pstmt = null;

        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_OPKBN = ?,");
            sql.addSql("   SMJ_OPDATE = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstSmail.OPKBN_OPENED);
            sql.addDateValue(param.getSmjOpdate());
            sql.addIntValue(usrSid);
            sql.addDateValue(param.getSmjEdate());
            sql.addIntValue(param.getSacSid());
            sql.addIntValue(param.getSmjSid());

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
     * <br>[機  能] 未開封のメッセージを開封済に更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param param SmlJmeisModel
     * @throws SQLException SQL実行例外
     */
    public void updateOpKbnOnly(int usrSid, SmlJmeisModel param) throws SQLException {

        PreparedStatement pstmt = null;

        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_OPKBN = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(param.getSmjOpkbn());
            sql.addIntValue(usrSid);
            sql.addDateValue(param.getSmjEdate());
            sql.addIntValue(param.getSacSid());
            sql.addIntValue(param.getSmjSid());

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
     * <br>[機  能] 未開封のメッセージを開封済に更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param param SmlJmeisModel
     * @return opnKbn 既読区分
     * @throws SQLException SQL実行例外
     */
    public boolean selOpKbnDate(SmlJmeisModel param) throws SQLException {

        boolean opnKbn = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
             //SQL文
             SqlBuffer sql = new SqlBuffer();
             sql.addSql(" select");
             sql.addSql("   SMJ_OPDATE");
             sql.addSql(" from");
             sql.addSql("   SML_JMEIS");
              sql.addSql(" where ");
             sql.addSql("   SAC_SID=?");
             sql.addSql(" and");
             sql.addSql("   SMJ_SID=?");

             pstmt = con.prepareStatement(sql.toSqlString());
             sql.addIntValue(param.getSacSid());
             sql.addIntValue(param.getSmjSid());

             log__.info(sql.toLogString());
             sql.setParameter(pstmt);
             pstmt.executeQuery();

             rs = pstmt.executeQuery();

             if (rs.next()) {
                 if (rs.getTimestamp("SMJ_OPDATE") != null) {
                     opnKbn = true;
                 }
             }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return opnKbn;
    }

    /**
     * <br>[機  能] 指定したメールを削除区分に更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param delList 削除するショートメールSIDリスト
     * @throws SQLException SQL実行例外
     */
    public void delete(List<SmlJmeisModel> delList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        UDate now = new UDate();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_JKBN = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addSql(" where ");

            sql.addIntValue(GSConstSmail.SML_JTKBN_DELETE);
            sql.addIntValue(0);
            sql.addDateValue(now);

            int i = 0;
            for (SmlJmeisModel model : delList) {
                if (i > 0) {
                    sql.addSql("  or");
                }
                sql.addSql(" (");
                sql.addSql(" SAC_SID=?");
                sql.addSql(" and");
                sql.addSql(" SMJ_SID=?");
                sql.addSql(" )");

                sql.addIntValue(model.getSacSid());
                sql.addIntValue(model.getSmjSid());
                i++;
            }

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
     * <br>[機  能] 指定したメールを削除区分に更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param smjSid 削除するショートメールSID
     * @throws SQLException SQL実行例外
     */
    public void deleteMail(int smjSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_JKBN = ?");
            sql.addSql(" where ");
            sql.addSql("   SMJ_SID=?");

            sql.addIntValue(GSConstSmail.SML_JTKBN_DELETE);
            sql.addIntValue(smjSid);

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
     * <br>[機  能] 指定されたメールSID中に、未読メッセージが含まれているか
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param msgSid メールSID配列
     * @return ret true:含まれている false 含まれていない
     * @throws SQLException SQL実行例外
     */
    public boolean isMsgUnOpend(int sacSid, String[] msgSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        boolean openFlg = false;
        con = getCon();

        if (msgSid == null || msgSid.length <= 0) {
            return false;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SMJ_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql(" and");
            sql.addSql("   SMJ_SID in (");

            for (int i = 0; i < msgSid.length; i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addIntValue(NullDefault.getInt(msgSid[i], -1));
            }
            sql.addSql(")");
            sql.addSql(" and");
            sql.addSql("   SMJ_OPKBN = ?");
            sql.addIntValue(GSConstSmail.OPKBN_UNOPENED);
            sql.addSql(" and");
            sql.addSql("   SMJ_JKBN = ?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("cnt");
                if (cnt > 0) {
                    openFlg = true;
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return openFlg;
    }

    /**
     * <br>[機  能] 指定されたメールSIDの状態区分を変更する(受信)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param sacSid アカウントSID
     * @param jtkbn 状態区分
     * @param sysUd システム日付
     * @param msgSid メールSID配列
     * @throws SQLException SQL実行例外
     */
    public void moveJmeis(int usrSid, int sacSid, int jtkbn, UDate sysUd, String[] msgSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (msgSid == null || msgSid.length <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_JKBN = ?,");
            sql.addIntValue(jtkbn);
            sql.addSql("   SMJ_EUID = ?,");
            sql.addIntValue(usrSid);
            sql.addSql("   SMJ_EDATE = ?");
            sql.addDateValue(sysUd);
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql(" and");
            sql.addSql("   SMJ_SID in (");

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
     * <br>[機  能] ゴミ箱のデータを削除する(受信)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @param sacSid アカウントSID
     * @param sysUd システム日付
     * @throws SQLException SQL実行例外
     */
    public void moveGomibakoJmeis(int userSid, int sacSid, UDate sysUd)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_JKBN = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstSmail.SML_JTKBN_DELETE);
            sql.addIntValue(userSid);
            sql.addDateValue(sysUd);
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_GOMIBAKO);

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
     * <br>[機  能] 受信メールの状態区分を変更する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @param kbn 状態区分
     * @param sacSid アカウントSID
     * @param sysUd システム日付
     * @throws SQLException SQL実行例外
     */
    public void updateOpkbn(int userSid, int sacSid, int kbn, UDate sysUd)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_OPKBN = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addIntValue(kbn);
            sql.addIntValue(userSid);
            sql.addDateValue(sysUd);
            if (kbn == GSConstSmail.OPKBN_OPENED) {
                sql.addSql("   ,SMJ_OPDATE = ");
                sql.addSql("       case when SMJ_OPDATE is null ");
                sql.addSql("     then  ? ");
                sql.addSql("       else  SMJ_OPDATE ");
                sql.addSql("     end");
                sql.addDateValue(sysUd);
            }
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_JKBN = ?");
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_TOROKU);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 受信メールの状態区分を変更する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smlSids ショートメールSID
     * @param userSid ユーザSID
     * @param kbn 状態区分
     * @param sacSid アカウントSID
     * @param sysUd システム日付
     * @throws SQLException SQL実行例外
     */
    public void updateOpkbn(String[] smlSids, int userSid, int sacSid, int kbn, UDate sysUd)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (smlSids == null || smlSids.length <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_OPKBN = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addIntValue(kbn);
            sql.addIntValue(userSid);
            sql.addDateValue(sysUd);
            if (kbn == GSConstSmail.OPKBN_OPENED) {
                sql.addSql("   ,SMJ_OPDATE = ");
                sql.addSql("       case when SMJ_OPDATE is null ");
                sql.addSql("     then  ? ");
                sql.addSql("       else  SMJ_OPDATE ");
                sql.addSql("     end");
                sql.addDateValue(sysUd);
            }
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql(" and");
            sql.addSql("   SMJ_SID in (");
            for (int i = 0; i < smlSids.length; i++) {
                if (i > 0) {
                    sql.addSql(", ");
                }
                sql.addSql(smlSids[i]);
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 受信メールの状態区分を変更する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smlSid ショートメールSID
     * @param userSid ユーザSID
     * @param kbn 状態区分
     * @param sacSid アカウントSID
     * @param sysUd システム日付
     * @throws SQLException SQL実行例外
     */
    public void updateOpkbn(int smlSid, int userSid, int sacSid, int kbn, UDate sysUd)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_OPKBN = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addIntValue(kbn);
            sql.addIntValue(userSid);
            sql.addDateValue(sysUd);
            if (kbn == GSConstSmail.OPKBN_OPENED) {
                sql.addSql("   ,SMJ_OPDATE = ");
                sql.addSql("       case when SMJ_OPDATE is null ");
                sql.addSql("     then  ? ");
                sql.addSql("       else  SMJ_OPDATE ");
                sql.addSql("     end");
                sql.addDateValue(sysUd);
            }
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID = ?");
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_TOROKU);
            sql.addIntValue(smlSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定されたメールSIDの状態区分を変更する(受信)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @param sacSid アカウントSID
     * @param mailSid メールSID
     * @param jtkbn 状態区分
     * @param sysUd システム日付
     * @throws SQLException SQL実行例外
     */
    public void moveJmeis(int userSid, int sacSid, int mailSid, int jtkbn, UDate sysUd)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_JKBN = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(jtkbn);
            sql.addIntValue(userSid);
            sql.addDateValue(sysUd);
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
     * <br>[機  能] 指定されたメールSIDの状態区分を変更する(受信)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @param sacSid アカウントSID
     * @param jtkbn 状態区分
     * @param sysUd システム日付
     * @param mailSid メールSID
     * @throws SQLException SQL実行例外
     */
    public void moveJmeis(int userSid, int sacSid, int jtkbn, UDate sysUd, int mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_JKBN = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(jtkbn);
            sql.addIntValue(userSid);
            sql.addDateValue(sysUd);
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
     * <br>[機  能] 指定されたメールSIDのメッセージを論理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @param sacSid アカウントSID
     * @param sysUd システム日付
     * @param mailSid メールSID配列
     * @throws SQLException SQL実行例外
     */
    public void deleteMsgRonri(int userSid, int sacSid, UDate sysUd, int mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_JKBN = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(userSid);
            sql.addDateValue(sysUd);
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
     * <br>[機  能] 指定されたユーザののメッセージを論理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delSacid 削除対象アカウントSID
     * @param updUsid 更新者SID
     * @param sysUd システム日付
     * @throws SQLException SQL実行例外
     */
    public void deleteMsgRonri(int delSacid, int updUsid, UDate sysUd)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_JKBN = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(updUsid);
            sql.addDateValue(sysUd);
            sql.addIntValue(delSacid);

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
     * <br>[機  能] 指定されたメールSIDの受信メッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param mailSid メールSID
     * @return ret 受信メッセージ詳細
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmailDetailModel> selectTargetJDetail(int sacSid,
                                                            String[] mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<SmailDetailModel> ret = new ArrayList<SmailDetailModel>();
        con = getCon();

        if (mailSid == null || mailSid.length <= 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(GSConstSmail.TAB_DSP_MODE_JUSIN + " as mailKbn,");
            sql.addSql("   SML_JMEIS.SMJ_OPKBN as smjOpkbn,");
            sql.addSql("   SML_SMEIS.SMS_TITLE as smsTitle");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS,");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where ");
            sql.addSql("   SML_JMEIS.SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql(" and");
            sql.addSql("   SML_JMEIS.SMJ_SID in (");

            for (int i = 0; i < mailSid.length; i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                String mailKey = mailSid[i].substring(1);
                sql.addIntValue(Integer.parseInt(mailKey));
            }
            sql.addSql(")");

            sql.addSql(" and");
            sql.addSql("   SML_JMEIS.SMJ_JKBN = ?");
            sql.addIntValue(GSConstSmail.SML_JTKBN_TOROKU);
            sql.addSql(" and");
            sql.addSql("   SML_JMEIS.SMJ_SID = SML_SMEIS.SMS_SID");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                SmailDetailModel mdl = new SmailDetailModel();
                mdl.setMailKbn(rs.getString("mailKbn"));
                mdl.setSmjOpkbn(rs.getInt("smjOpkbn"));
                mdl.setSmsTitle(rs.getString("smsTitle"));
                ret.add(mdl);
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
     * <p>削除するメールの件数を取得する。
     * @param delMdl SmlAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @return 削除メールリスト
     * @throws SQLException SQL実行例外
     */
    public int getDeleteMailCount(
            SmlAdelModel delMdl, int kbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int smailCount = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SMJ_SID) as CNT");

            //検索条件を設定
            __setDeleteMailWhere(sql, delMdl, kbn);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                smailCount = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return smailCount;
    }

    /**
     * <p>削除するメールSIDリストを取得する。
     * @param delMdl SmlAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @param limit 取得件数
     * @param offset 取得開始行数
     * @return 削除メール，メール容量マップ
     * @throws SQLException SQL実行例外
     */
    public Map<SmlJmeisModel, Long> getDeleteMail(
            SmlAdelModel delMdl, int kbn, int limit, int offset) throws SQLException {

        Map<SmlJmeisModel, Long> ret = new HashMap<SmlJmeisModel, Long>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();


        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_JMEIS.SAC_SID,");
            sql.addSql("   SML_JMEIS.SMJ_SID,");
            sql.addSql("   SML_SMEIS.SMS_SIZE");

            //検索条件を設定
            __setDeleteMailWhere(sql, delMdl, kbn);

            sql.addSql(" order by SMJ_SID");
            sql.setPagingValue(offset, limit);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            SmlJmeisModel bean = null;
            while (rs.next()) {
                bean = new SmlJmeisModel();
                bean.setSacSid(rs.getInt("SAC_SID"));
                bean.setSmjSid(rs.getInt("SMJ_SID"));
                ret.put(bean, rs.getLong("SMS_SIZE"));
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
     * <p>削除するメールSIDとメール容量を取得する。
     * @param sidList 削除対象SIDリスト
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @param limit 取得件数
     * @param offset 取得開始行数
     * @return 削除メール，メール容量マップ
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, Long> getDeleteMail(
            List<Integer> sidList, int kbn, int limit, int offset) throws SQLException {

        Map<Integer, Long> ret = new HashMap<Integer, Long>();
        if (sidList == null || sidList.size() == 0) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            StringBuilder sb = new StringBuilder();
            sb.append(" select");
            sb.append("   SMJ_SID,");
            sb.append("   SML_SMEIS.SMS_SIZE");
            sb.append(" from");
            sb.append("   SML_JMEIS,");
            sb.append("   SML_SMEIS");
            sb.append(" where ");
            sb.append("   SMJ_SID in (");
            final String sqlBaseTop = sb.toString();
            
            sb = new StringBuilder();
            sb.append("   )");
            sb.append(" and");
            sb.append("   SML_JMEIS.SMJ_SID = SML_SMEIS.SMS_SID");
            sb.append(" order by SMJ_SID");
            final String sqlBaseBottom = sb.toString();
            
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sqlBaseTop);
            int count = 1;
            for (int sid : sidList) {
                sql.addSql("?");
                if (count % 500 != 0 && count != sidList.size()) {
                    sql.addSql(", ");
                }
                sql.addIntValue(sid);
                //500件ごとに実行
                if (count % 500 == 0 || count == sidList.size()) {
                    sql.addSql(sqlBaseBottom);
                    sql.setPagingValue(offset, limit);
                    log__.info(sql.toLogString());
                    pstmt = con.prepareStatement(sql.toSqlString());
                    sql.setParameter(pstmt);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        ret.put(rs.getInt("SMJ_SID"), rs.getLong("SMS_SIZE"));
                    }
                    //各種パラメータのリセット
                    sql = new SqlBuffer();
                    sql.addSql(sqlBaseTop);
                    pstmt.clearParameters();
                }
                count++;
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
     * <p>削除するメールを取得するための検索条件を取得する。
     * @param sql SqlBuffer
     * @param delMdl SmlAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     */
    private void __setDeleteMailWhere(SqlBuffer sql, SmlAdelModel delMdl, int kbn) {

        int jkbn = -1;
        if (kbn == 1) {
            jkbn = GSConstSmail.SML_JTKBN_TOROKU;
        } else if (kbn == 2) {
            jkbn = GSConstSmail.SML_JTKBN_GOMIBAKO;
        }

        int year = delMdl.getSadJdelYear();
        int month = delMdl.getSadJdelMonth();
        if (kbn == 2) {
            year = delMdl.getSadDdelYear();
            month = delMdl.getSadDdelMonth();
        }

        UDate delUd = new UDate();
        delUd.addYear((year * -1));
        delUd.addMonth((month * -1));
        delUd.setMaxHhMmSs();

        sql.addSql(" from");
        sql.addSql("   SML_JMEIS,");
        sql.addSql("   SML_SMEIS");
        sql.addSql(" where ");
        sql.addSql("   SMJ_ADATE <= ?");
        sql.addSql(" and");
        sql.addSql("   SMJ_JKBN = ?");
        sql.addSql(" and");
        sql.addSql("   SML_JMEIS.SMJ_SID = SML_SMEIS.SMS_SID");
        sql.addDateValue(delUd);
        sql.addIntValue(jkbn);
        if (delMdl.getSacSid() > 0) {
            sql.addSql(" and");
            sql.addSql("   SML_JMEIS.SAC_SID = ?");
            sql.addIntValue(delMdl.getSacSid());
        }
    }
    
    /**
     * <br>[機  能] 指定したアカウントのゴミ箱内の受信メールリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @return ゴミ箱内の受信メールリスト
     * @throws SQLException SQL実行例外
     */
    public List<SmlJmeisModel> getGomibakoMailList(int sacSid) throws SQLException {
        
        List<SmlJmeisModel> ret = new ArrayList<SmlJmeisModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMJ_SID,");
            sql.addSql("   SMJ_OPKBN,");
            sql.addSql("   SMJ_OPDATE,");
            sql.addSql("   SMJ_JKBN,");
            sql.addSql("   SMJ_FWKBN,");
            sql.addSql("   SMJ_SENDKBN,");
            sql.addSql("   SMJ_AUID,");
            sql.addSql("   SMJ_ADATE,");
            sql.addSql("   SMJ_EUID,");
            sql.addSql("   SMJ_EDATE,");
            sql.addSql("   SMJ_RTN_KBN,");
            sql.addSql("   SMJ_FW_KBN");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMJ_JKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_GOMIBAKO);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlJmeisFromRs(rs));
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
     * <br>[機  能] 受信タブ自動削除設定に従いデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delMdl 削除ユーザの設定データ
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @throws SQLException SQL実行例外
     */
    public void delete(SmlAdelModel delMdl, int kbn)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        UDate now = new UDate();
        int jkbn = -1;

        if (kbn == 1) {
            jkbn = GSConstSmail.SML_JTKBN_TOROKU;
        } else if (kbn == 2) {
            jkbn = GSConstSmail.SML_JTKBN_GOMIBAKO;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set");
            sql.addSql("   SMJ_JKBN = ?,");
            sql.addSql("   SMJ_EUID = ?,");
            sql.addSql("   SMJ_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SMJ_ADATE <= ?");
            sql.addSql(" and");
            sql.addSql("   SMJ_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            int year = delMdl.getSadJdelYear();
            int month = delMdl.getSadJdelMonth();
            if (kbn == 2) {
                year = delMdl.getSadDdelYear();
                month = delMdl.getSadDdelMonth();
            }
            UDate delUd = now.cloneUDate();

            delUd.addYear((year * -1));
            delUd.addMonth((month * -1));
            delUd.setHour(GSConstMain.DAY_END_HOUR);
            delUd.setMinute(GSConstMain.DAY_END_MINUTES);
            delUd.setSecond(GSConstMain.DAY_END_SECOND);
            delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

            sql.addIntValue(GSConstSmail.SML_JTKBN_DELETE);
            sql.addIntValue(0);
            sql.addDateValue(now);
            sql.addDateValue(delUd);
            sql.addIntValue(jkbn);

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
     * <br>[機  能] 受信タブ自動削除設定に従いデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delList 削除ユーザの個人設定リスト
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @throws SQLException SQL実行例外
     */
    public void delete(ArrayList<SmlAdelModel> delList, int kbn)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        UDate now = new UDate();
        int jkbn = -1;

        if (kbn == 1) {
            jkbn = GSConstSmail.SML_JTKBN_TOROKU;
        } else if (kbn == 2) {
            jkbn = GSConstSmail.SML_JTKBN_GOMIBAKO;
        }

        try {

            for (SmlAdelModel mdl : delList) {

                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   SML_JMEIS");
                sql.addSql(" set");
                sql.addSql("   SMJ_JKBN = ?,");
                sql.addSql("   SMJ_EUID = ?,");
                sql.addSql("   SMJ_EDATE = ?");
                sql.addSql(" where ");
                sql.addSql("   SAC_SID = ?");
                sql.addSql(" and");
                sql.addSql("   SMJ_ADATE <= ?");
                sql.addSql(" and");
                sql.addSql("   SMJ_JKBN = ?");

                pstmt = con.prepareStatement(sql.toSqlString());

                int year = 0;
                int month = 0;
                if (kbn == 1) {
                    year = mdl.getSadJdelYear();
                    month = mdl.getSadJdelMonth();
                } else if (kbn == 2) {
                    year = mdl.getSadDdelYear();
                    month = mdl.getSadDdelMonth();
                }

                UDate delUd = now.cloneUDate();

                delUd.addYear((year * -1));
                delUd.addMonth((month * -1));
                delUd.setHour(GSConstMain.DAY_END_HOUR);
                delUd.setMinute(GSConstMain.DAY_END_MINUTES);
                delUd.setSecond(GSConstMain.DAY_END_SECOND);
                delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

                pstmt.setInt(1, GSConstSmail.SML_JTKBN_DELETE);
                pstmt.setInt(2, 0);
                pstmt.setTimestamp(3, now.toSQLTimestamp());
                pstmt.setInt(4, mdl.getSacSid());
                pstmt.setTimestamp(5, delUd.toSQLTimestamp());
                pstmt.setInt(6, jkbn);

                //ログ出力
                sql.addIntValue(GSConstSmail.SML_JTKBN_DELETE);
                sql.addIntValue(0);
                sql.addDateValue(now);
                sql.addIntValue(mdl.getSacSid());
                sql.addDateValue(delUd);
                sql.addIntValue(jkbn);

                log__.info(sql.toLogString());
                sql.clearValue();

                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定されたメールが存在するか
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid メールSID
     * @param sacSid アカウントSID
     * @return ret 受信メッセージ詳細
     * @throws SQLException SQL実行例外
     */
    public boolean selectExsistMail(int sacSid, int mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(SML_JMEIS.SMJ_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and ");
            sql.addSql("   SMJ_SID=?");
            sql.addSql(" and ");
            sql.addSql("   SML_JMEIS.SMJ_JKBN !=" + GSConstSmail.SML_JTKBN_DELETE);

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(sacSid);
            sql.addIntValue(mailSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt("CNT") > 0) {
                    ret = true;
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
     * <br>[機  能] 指定されたメールが存在するか
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid メールSID
     * @param sacSid アカウントSID
     * @return ret 受信メッセージ詳細
     * @throws SQLException SQL実行例外
     */
    public boolean selectExsistMail(int sacSid, String[] mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        boolean ret = false;

        if (mailSid == null || mailSid.length <= 0) {
            return false;
        }

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(SML_JMEIS.SMJ_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql("  and ");
            sql.addSql("   SML_JMEIS.SMJ_SID in (");

            for (int i = 0; i < mailSid.length; i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                String mailKey = mailSid[i].substring(1);
                sql.addIntValue(Integer.parseInt(mailKey));
            }
            sql.addSql(")");
            sql.addSql(" and ");
            sql.addSql("   SML_JMEIS.SMJ_JKBN !=" + GSConstSmail.SML_JTKBN_DELETE);

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(sacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt("CNT") > 0) {
                    ret = true;
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
     * <br>[機  能] 転送区分を変更します
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean SmlJmeisModel
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public int updateFwkbn(SmlJmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" set ");
            sql.addSql("   SMJ_FWKBN=?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMJ_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmjFwkbn());

            //where
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSmjSid());

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
     * <br>[機  能] 指定されたショートメールが対象アカウント内に存在するかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSidList 対象アカウントSID一覧
     * @param smjSid メールSID
     * @return true: 存在する false: 存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existMailInAccount(List<Integer> sacSidList, int smjSid)
        throws SQLException {

        if (sacSidList == null || sacSidList.isEmpty() || smjSid <= 0) {
            return false;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        boolean ret = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select 1 from SML_JMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID in (");

            for (int idx = 0; idx < sacSidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addIntValue(sacSidList.get(idx));
            }
            sql.addSql(")");

            sql.addSql(" and ");
            sql.addSql("   SMJ_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   SMJ_JKBN != ?");
            sql.addIntValue(smjSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_DELETE);

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 対象アカウント内から指定されたショートメールが存在するアカウントSIDリストを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSidList 対象アカウントSID一覧
     * @param smjSid メールSID
     * @return true: 存在する false: 存在しない
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getMailSidInAccount(List<Integer> sacSidList, int smjSid)
        throws SQLException {
        List<Integer> ret = new ArrayList<>();
        if (sacSidList == null || sacSidList.isEmpty() || smjSid <= 0) {
            return ret;
        }
        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = sacSidList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select SAC_SID from SML_JMEIS");

        Connection con = null;
        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件分インサート
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());
            sql.addSql(" where ");
            sql.addSql("   SAC_SID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addLongValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");
            sql.addSql(" and ");
            sql.addSql("   SMJ_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   SMJ_JKBN = ?");
            sql.addIntValue(smjSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_TOROKU);

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                try (ResultSet rs = pstmt.executeQuery();) {

                    while (rs.next()) {
                        ret.add(rs.getInt("SAC_SID"));
                    }

                }

            }
            exeList.clear();
        }
        return ret;
    }
    /**
     * <p>Create SML_JMEIS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlJmeisModel
     * @throws SQLException SQL実行例外
     */
    private SmlJmeisModel __getSmlJmeisFromRs(ResultSet rs) throws SQLException {
        SmlJmeisModel bean = new SmlJmeisModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSmjSid(rs.getInt("SMJ_SID"));
        bean.setSmjOpkbn(rs.getInt("SMJ_OPKBN"));
        bean.setSmjOpdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMJ_OPDATE")));
        bean.setSmjJkbn(rs.getInt("SMJ_JKBN"));
        bean.setSmjFwkbn(rs.getInt("SMJ_FWKBN"));
        bean.setSmjSendkbn(rs.getInt("SMJ_SENDKBN"));
        bean.setSmjAuid(rs.getInt("SMJ_AUID"));
        bean.setSmjAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMJ_ADATE")));
        bean.setSmjEuid(rs.getInt("SMJ_EUID"));
        bean.setSmjEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMJ_EDATE")));
        bean.setSmjRtnKbn(rs.getInt("SMJ_RTN_KBN"));
        bean.setSmjFwKbn(rs.getInt("SMJ_FW_KBN"));
        return bean;
    }
}