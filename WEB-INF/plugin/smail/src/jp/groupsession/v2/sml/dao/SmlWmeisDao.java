package jp.groupsession.v2.sml.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.model.SmlWmeisModel;

/**
 * <p>SML_WMEIS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlWmeisDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlWmeisDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlWmeisDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlWmeisDao(Connection con) {
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
            sql.addSql("drop table SML_WMEIS");

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
            sql.addSql(" create table SML_WMEIS (");
            sql.addSql("   SAC_SID NUMBER(4,0) not null,");
            sql.addSql("   SMW_SID NUMBER(4,0) not null,");
            sql.addSql("   SMW_TITLE varchar(50),");
            sql.addSql("   SMW_MARK NUMBER(4,0),");
            sql.addSql("   SMW_BODY text,");
            sql.addSql("   SMW_BODY_PLAIN text,");
            sql.addSql("   SMW_SIZE bigint not null,");
            sql.addSql("   SMW_TYPE NUMBER(4,0),");
            sql.addSql("   SMW_JKBN NUMBER(4,0),");
            sql.addSql("   SMW_AUID NUMBER(4,0) not null,");
            sql.addSql("   SMW_ADATE varchar(8) not null,");
            sql.addSql("   SMW_EUID NUMBER(4,0) not null,");
            sql.addSql("   SMW_EDATE varchar(8) not null,");
            sql.addSql("   SMW_ORIGIN integer,");
            sql.addSql("   SMW_EDIT_KBN integer,");
            sql.addSql("   primary key (SMW_SID)");
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
     * <p>Insert SML_WMEIS Data Bindding JavaBean
     * @param bean SML_WMEIS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlWmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_WMEIS(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMW_SID,");
            sql.addSql("   SMW_TITLE,");
            sql.addSql("   SMW_MARK,");
            sql.addSql("   SMW_BODY,");
            sql.addSql("   SMW_BODY_PLAIN,");
            sql.addSql("   SMW_TYPE,");
            sql.addSql("   SMW_SIZE,");
            sql.addSql("   SMW_JKBN,");
            sql.addSql("   SMW_AUID,");
            sql.addSql("   SMW_ADATE,");
            sql.addSql("   SMW_EUID,");
            sql.addSql("   SMW_EDATE,");
            sql.addSql("   SMW_ORIGIN,");
            sql.addSql("   SMW_EDIT_KBN");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getSmwSid());
            sql.addStrValue(bean.getSmwTitle());
            sql.addIntValue(bean.getSmwMark());
            sql.addStrValue(bean.getSmwBody());
            sql.addStrValue(bean.getSmwBodyPlain());
            sql.addIntValue(bean.getSmwType());
            sql.addLongValue(bean.getSmwSize());
            sql.addIntValue(bean.getSmwJkbn());
            sql.addIntValue(bean.getSmwAuid());
            sql.addDateValue(bean.getSmwAdate());
            sql.addIntValue(bean.getSmwEuid());
            sql.addDateValue(bean.getSmwEdate());
            sql.addIntValue(bean.getSmwOrigin());
            sql.addIntValue(bean.getSmwEditKbn());
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
     * <p>Update SML_WMEIS Data Bindding JavaBean
     * @param bean SML_WMEIS Data Bindding JavaBean
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlWmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" set ");
            sql.addSql("   SAC_SID=?,");
            sql.addSql("   SMW_TITLE=?,");
            sql.addSql("   SMW_MARK=?,");
            sql.addSql("   SMW_BODY=?,");
            sql.addSql("   SMW_BODY_PLAIN=?,");
            sql.addSql("   SMW_TYPE=?,");
            sql.addSql("   SMW_SIZE=?,");
            sql.addSql("   SMW_JKBN=?,");
            sql.addSql("   SMW_AUID=?,");
            sql.addSql("   SMW_ADATE=?,");
            sql.addSql("   SMW_EUID=?,");
            sql.addSql("   SMW_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SMW_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addStrValue(bean.getSmwTitle());
            sql.addIntValue(bean.getSmwMark());
            sql.addStrValue(bean.getSmwBody());
            sql.addStrValue(bean.getSmwBodyPlain());
            sql.addIntValue(bean.getSmwType());
            sql.addLongValue(bean.getSmwSize());
            sql.addIntValue(bean.getSmwJkbn());
            sql.addIntValue(bean.getSmwAuid());
            sql.addDateValue(bean.getSmwAdate());
            sql.addIntValue(bean.getSmwEuid());
            sql.addDateValue(bean.getSmwEdate());
            //where
            sql.addIntValue(bean.getSmwSid());

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
     * <p>Select SML_WMEIS All Data
     * @return List in SML_WMEISModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlWmeisModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlWmeisModel> ret = new ArrayList<SmlWmeisModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMW_SID,");
            sql.addSql("   SMW_TITLE,");
            sql.addSql("   SMW_MARK,");
            sql.addSql("   SMW_BODY,");
            sql.addSql("   SMW_BODY_PLAIN,");
            sql.addSql("   SMW_TYPE,");
            sql.addSql("   SMW_SIZE,");
            sql.addSql("   SMW_JKBN,");
            sql.addSql("   SMW_AUID,");
            sql.addSql("   SMW_ADATE,");
            sql.addSql("   SMW_EUID,");
            sql.addSql("   SMW_EDATE,");
            sql.addSql("   SMW_ORIGIN,");
            sql.addSql("   SMW_EDIT_KBN");
            sql.addSql(" from ");
            sql.addSql("   SML_WMEIS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlWmeisFromRs(rs));
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
     * <p>Select SML_WMEIS
     * @param bean SML_WMEIS Model
     * @return SML_WMEISModel
     * @throws SQLException SQL実行例外
     */
    public SmlWmeisModel select(SmlWmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlWmeisModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMW_SID,");
            sql.addSql("   SMW_TITLE,");
            sql.addSql("   SMW_MARK,");
            sql.addSql("   SMW_BODY,");
            sql.addSql("   SMW_BODY_PLAIN,");
            sql.addSql("   SMW_TYPE,");
            sql.addSql("   SMW_SIZE,");
            sql.addSql("   SMW_JKBN,");
            sql.addSql("   SMW_AUID,");
            sql.addSql("   SMW_ADATE,");
            sql.addSql("   SMW_EUID,");
            sql.addSql("   SMW_EDATE,");
            sql.addSql("   SMW_ORIGIN,");
            sql.addSql("   SMW_EDIT_KBN");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SMW_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmwSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlWmeisFromRs(rs);
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
     * <br>[機  能] 草稿メールSIDからメール情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param smwSid 草稿メールSID
     * @return SML_WMEISModel
     * @throws SQLException SQL実行例外
     */
    public SmlWmeisModel select(int smwSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlWmeisModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMW_SID,");
            sql.addSql("   SMW_TITLE,");
            sql.addSql("   SMW_MARK,");
            sql.addSql("   SMW_BODY,");
            sql.addSql("   SMW_BODY_PLAIN,");
            sql.addSql("   SMW_TYPE,");
            sql.addSql("   SMW_SIZE,");
            sql.addSql("   SMW_JKBN,");
            sql.addSql("   SMW_AUID,");
            sql.addSql("   SMW_ADATE,");
            sql.addSql("   SMW_EUID,");
            sql.addSql("   SMW_EDATE,");
            sql.addSql("   SMW_ORIGIN,");
            sql.addSql("   SMW_EDIT_KBN");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SMW_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smwSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlWmeisFromRs(rs);
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
     * <p>Delete SML_WMEIS
     * @param bean SML_WMEIS Model
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public  int delete(SmlWmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SMW_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmwSid());

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
     * <br>[機  能] 指定されたメールSIDの状態区分を変更する(草稿)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @param sacSid アカウントSID
     * @param jtkbn 状態区分
     * @param sysUd システム日付
     * @param msgSid メールSID配列
     * @throws SQLException SQL実行例外
     */
    public void moveWmeis(int userSid, int sacSid, int jtkbn, UDate sysUd, String[] msgSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" set");
            sql.addSql("   SMW_JKBN = ?,");
            sql.addIntValue(jtkbn);
            sql.addSql("   SMW_EUID = ?,");
            sql.addIntValue(userSid);
            sql.addSql("   SMW_EDATE = ?");
            sql.addDateValue(sysUd);
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql(" and");
            sql.addSql("   SMW_SID in (");

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
     * <br>[機  能] 指定されたメールSIDの状態区分を変更する(草稿)
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
    public void moveWmeis(int userSid, int sacSid, int jtkbn, UDate sysUd, int mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" set");
            sql.addSql("   SMW_JKBN = ?,");
            sql.addSql("   SMW_EUID = ?,");
            sql.addSql("   SMW_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMW_SID = ?");

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
     * <br>[機  能] 指定されたメールSIDの添付情報を論理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param msgSid メールSID配列
     * @throws SQLException SQL実行例外
     */
    public void deleteSoukouBin(int sacSid, String[] msgSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where");
            sql.addSql("   CMN_BINF.BIN_SID in (");
            sql.addSql("     select");
            sql.addSql("       SML_BIN.BIN_SID");
            sql.addSql("     from");
            sql.addSql("       SML_BIN");
            sql.addSql("     where");
            sql.addSql("       SML_BIN.SML_SID in (");
            sql.addSql("         select");
            sql.addSql("           SMW_SID");
            sql.addSql("         from");
            sql.addSql("           SML_WMEIS");
            sql.addSql("         where");
            sql.addSql("           SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql("         and");
            sql.addSql("           SMW_SID in(");
            for (int i = 0; i < msgSid.length; i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                String mailKey = msgSid[i].substring(1);
                sql.addIntValue(Integer.parseInt(mailKey));
            }
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("   )");


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
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql(" and");
            sql.addSql("   SMW_SID in (");

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
     * @param smwSidList 削除対象メールSID一覧
     * @throws SQLException SQL実行例外
     */
    public void deleteMsgButuri(List<String> smwSidList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SMW_SID in (");

            for (int idx = 0; idx < smwSidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addIntValue(Integer.parseInt(smwSidList.get(idx)));
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
     * <br>[機  能] ゴミ箱から物理削除する対象のメールSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @return ret 削除対象
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getClearWmeisSid(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SMW_SID");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMW_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_GOMIBAKO);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("SMW_SID"));
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
     * <br>[機  能] 草稿のメッセージ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @return cnt 未開封のメッセージ件数
     * @throws SQLException SQL実行例外
     */
    public int getSokoMsgCnt(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SMW_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMW_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

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
     * <br>[機  能] ゴミ箱の草稿に紐づく添付情報を論理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param mailSid メールSID
     * @throws SQLException SQL実行例外
     */
    public void deleteGomibakoBin(int sacSid, ArrayList<Integer> mailSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where");
            sql.addSql("   CMN_BINF.BIN_SID in (");
            sql.addSql("     select");
            sql.addSql("       SML_BIN.BIN_SID");
            sql.addSql("     from");
            sql.addSql("       SML_BIN");
            sql.addSql("     where");
            sql.addSql("       SML_BIN.SML_SID in (");
            sql.addSql("         select");
            sql.addSql("           SMW_SID");
            sql.addSql("         from");
            sql.addSql("           SML_WMEIS");
            sql.addSql("         where");
            sql.addSql("           SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql("         and");
            sql.addSql("           SMW_SID in(");
            for (int i = 0; i < mailSid.size(); i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                int mailKey = mailSid.get(i);
                sql.addIntValue(mailKey);
            }
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("   )");

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
     * <br>[機  能] ゴミ箱の草稿に紐づく添付情報を論理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param mailSid メールSID
     * @throws SQLException SQL実行例外
     */
    public void deleteGomibakoBodyBin(int sacSid, ArrayList<Integer> mailSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where");
            sql.addSql("   CMN_BINF.BIN_SID in (");
            sql.addSql("     select");
            sql.addSql("       SML_BODY_BIN.BIN_SID");
            sql.addSql("     from");
            sql.addSql("       SML_BODY_BIN");
            sql.addSql("     where");
            sql.addSql("       SML_BODY_BIN.SML_SID in (");
            sql.addSql("         select");
            sql.addSql("           SMW_SID");
            sql.addSql("         from");
            sql.addSql("           SML_WMEIS");
            sql.addSql("         where");
            sql.addSql("           SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql("         and");
            sql.addSql("           SMW_SID in(");
            for (int i = 0; i < mailSid.size(); i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                int mailKey = mailSid.get(i);
                sql.addIntValue(mailKey);
            }
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("   )");

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
     * <br>[機  能] ゴミ箱のメッセージを物理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @throws SQLException SQL実行例外
     */
    public void deleteGomibakoMsgButuri(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMW_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定されたメールSIDに紐づく添付情報を論理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param mailSid メールSID
     * @throws SQLException SQL実行例外
     */
    public void deleteBin(int sacSid, int mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = ?");
            sql.addSql(" where");
            sql.addSql("   CMN_BINF.BIN_SID in (");
            sql.addSql("     select");
            sql.addSql("       SML_BIN.BIN_SID");
            sql.addSql("     from");
            sql.addSql("       SML_BIN");
            sql.addSql("     where");
            sql.addSql("       SML_BIN.SML_SID in (");
            sql.addSql("         select");
            sql.addSql("           SMW_SID");
            sql.addSql("         from");
            sql.addSql("           SML_WMEIS");
            sql.addSql("         where");
            sql.addSql("           SAC_SID = ?");
            sql.addSql("         and");
            sql.addSql("           SMW_SID = ?");
            sql.addSql("       )");
            sql.addSql("   )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(sacSid);
            sql.addIntValue(mailSid);

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
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMW_SID = ?");

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
     * <br>[機  能] 指定されたユーザSIDのメッセージを物理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @throws SQLException SQL実行例外
     */
    public void deleteMsgButuri(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);

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
     * <br>[機  能] 指定されたユーザの下書きメールSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacid アカウントSID
     * @return ret 全下書きメールSIDリスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectAllWSid(int sacid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SMW_SID");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("SMW_SID"));
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
     * <br>[機  能] 指定されたメールSIDの送信メッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param mailSid メールSID
     * @return ret 受信メッセージ詳細
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmailDetailModel> selectTargetWDetail(int sacSid,
                                                            String[] mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        ArrayList<SmailDetailModel> ret = new ArrayList<SmailDetailModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(GSConstSmail.TAB_DSP_MODE_SOKO + " as mailKbn,");
            sql.addSql("   SML_WMEIS.SMW_TITLE as smwTitle");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SML_WMEIS.SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql(" and");
            sql.addSql("   SML_WMEIS.SMW_SID in (");

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
            sql.addSql("   SML_WMEIS.SMW_JKBN = ?");
            sql.addIntValue(GSConstSmail.SML_JTKBN_TOROKU);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                SmailDetailModel mdl = new SmailDetailModel();
                mdl.setMailKbn(rs.getString("mailKbn"));
                mdl.setSmsTitle(rs.getString("smwTitle"));
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
     * <br>[機  能] 草稿タブ自動削除設定に従いデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delMdl 削除ユーザの設定データ
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @throws SQLException SQL実行例外
     */
    public void delete(SmlAdelModel delMdl, int kbn) throws SQLException {

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
            sql.addSql("   SML_WMEIS");
            sql.addSql(" set");
            sql.addSql("   SMW_JKBN = ?,");
            sql.addSql("   SMW_EUID = ?,");
            sql.addSql("   SMW_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SMW_EDATE <= ?");
            sql.addSql(" and");
            sql.addSql("   SMW_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            int year = delMdl.getSadWdelYear();
            int month = delMdl.getSadWdelMonth();
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
     * <p>削除するメールSIDリストを取得する。
     * @param delMdl SmlAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @param limit 取得件数
     * @param offset 取得開始行数
     * @return 削除メールSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<String> getDeleteMailList(
            SmlAdelModel delMdl, int kbn, int limit, int offset) throws SQLException {

        List<String> ret = new ArrayList<String>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

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
            sql.addSql(" select");
            sql.addSql("   SMW_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SMW_EDATE <= ?");
            sql.addSql(" and");
            sql.addSql("   SMW_JKBN = ?");
            sql.addSql("   order by SMW_SID");
            sql.addSql("   limit ?");
            sql.addSql("   offset ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            int year = delMdl.getSadWdelYear();
            int month = delMdl.getSadWdelMonth();
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

            sql.addDateValue(delUd);
            sql.addIntValue(jkbn);
            sql.addIntValue(limit);
            sql.addIntValue(offset);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(String.valueOf(rs.getInt("SMW_SID")));
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
     * <br>[機  能] 指定したショートメール情報(草稿)のデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param smwSidList メールSID
     * @return データサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalDataSize(List<Integer> smwSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long dataSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(SMW_TITLE)) as TITLE_SIZE,");
            sql.addSql("   sum(octet_length(SMW_BODY)) as BODY_SIZE,");
            sql.addSql("   sum(octet_length(SMW_BODY_PLAIN)) as BODY_PLAIN_SIZE");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where");
            sql.addSql("   SMW_SID in (");

            for (int idx = 0; idx < smwSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(smwSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataSize = rs.getLong("TITLE_SIZE");
                dataSize += rs.getLong("BODY_SIZE");
                dataSize += rs.getLong("BODY_PLAIN_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return dataSize;
    }

    /**
     * <br>[機  能] ショートメール情報のSID一覧を取得する
     * <br>[解  説] システムユーザのアカウントを除外する
     * <br>[備  考]
     * @param smwSidList メールSID
     * @return システムユーザのアカウントを除外したSID一覧
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getSidList(List<Integer> smwSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> sidList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SMW_SID");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where");
            sql.addSql("   SMW_SID in (");

            for (int idx = 0; idx < smwSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(smwSidList.get(idx));
            }

            sql.addSql("  )");

            sql.addSql(" and");
            sql.addSql("   SAC_SID not in (");
            sql.addSql("     ?,");
            sql.addSql("     ?");
            sql.addSql("   )");
            sql.addIntValue(GSConst.SYSTEM_USER_ADMIN);
            sql.addIntValue(GSConst.SYSTEM_USER_MAIL);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                sidList.add(rs.getInt("SMW_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return sidList;
    }

    /**
     * <br>[機  能] 草稿タブ自動削除設定に従いデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delList 削除ユーザの個人設定リスト
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @throws SQLException SQL実行例外
     */
    public void delete(ArrayList<SmlAdelModel> delList, int kbn) throws SQLException {

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
                sql.addSql("   SML_WMEIS");
                sql.addSql(" set");
                sql.addSql("   SMW_JKBN = ?,");
                sql.addSql("   SMW_EUID = ?,");
                sql.addSql("   SMW_EDATE = ?");
                sql.addSql(" where ");
                sql.addSql("   SAC_SID = ?");
                sql.addSql(" and");
                sql.addSql("   SMW_EDATE <= ?");
                sql.addSql(" and");
                sql.addSql("   SMW_JKBN = ?");

                pstmt = con.prepareStatement(sql.toSqlString());

                int year = 0;
                int month = 0;
                if (kbn == 1) {
                    year = mdl.getSadWdelYear();
                    month = mdl.getSadWdelMonth();
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
     * <br>[機  能] 自動削除対象の草稿ショートメールSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delList 削除ユーザの個人設定リスト
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @return 草稿ショートメールSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getMailSidForAutoDel(ArrayList<SmlAdelModel> delList, int kbn)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();

        List<Integer> sidList = new ArrayList<Integer>();

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
                sql.addSql(" select");
                sql.addSql("   SMW_SID");
                sql.addSql(" from");
                sql.addSql("   SML_WMEIS");
                sql.addSql(" where ");
                sql.addSql("   SAC_SID = ?");
                sql.addSql(" and");
                sql.addSql("   SMW_EDATE <= ?");
                sql.addSql(" and");
                sql.addSql("   SMW_JKBN = ?");

                pstmt = con.prepareStatement(sql.toSqlString());

                int year = 0;
                int month = 0;
                if (kbn == 1) {
                    year = mdl.getSadWdelYear();
                    month = mdl.getSadWdelMonth();
                } else if (kbn == 2) {
                    year = mdl.getSadDdelYear();
                    month = mdl.getSadDdelMonth();
                }

                //削除基準日時
                UDate delUd = now.cloneUDate();
                delUd.addYear((year * -1));
                delUd.addMonth((month * -1));
                delUd.setHour(GSConstMain.DAY_END_HOUR);
                delUd.setMinute(GSConstMain.DAY_END_MINUTES);
                delUd.setSecond(GSConstMain.DAY_END_SECOND);
                delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

                sql.addIntValue(mdl.getSacSid());
                sql.addDateValue(delUd);
                sql.addIntValue(jkbn);

                //ログ出力
                log__.info(sql.toLogString());

                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    sidList.add(rs.getInt("SMW_SID"));
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }

        return sidList;
    }

    /**
     * <br>[機  能] 指定したメールを削除区分に更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param delList 削除するショートメールSIDリスト
     * @throws SQLException SQL実行例外
     */
    public void delete(List<String> delList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        UDate now = new UDate();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" set");
            sql.addSql("   SMW_JKBN = ?,");
            sql.addSql("   SMW_EUID = ?,");
            sql.addSql("   SMW_EDATE = ?");
            sql.addSql(" where ");

            sql.addIntValue(GSConstSmail.SML_JTKBN_DELETE);
            sql.addIntValue(0);
            sql.addDateValue(now);

            int i = 0;
            for (String delSid : delList) {
                if (i > 0) {
                    sql.addSql("  or");
                }
                sql.addSql("   SMW_SID = ?");
                sql.addIntValue(Integer.parseInt(delSid));
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
     * <br>[機  能] 論理削除された草稿メールのメールSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return メールSID一覧
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getDeletedMailSidList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SMW_SID");
            sql.addSql(" from");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SMW_JKBN = ?");
            sql.addIntValue(GSConstSmail.SML_JTKBN_DELETE);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("SMW_SID"));
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
     * <p>Select SML_WMEIS All Data
     * @param sacSid アカウントSID
     * @param smwSid メールSID
     * @return List in SML_WMEISModel
     * @throws SQLException SQL実行例外
     */
    public SmlWmeisModel select(int sacSid, int smwSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlWmeisModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMW_SID,");
            sql.addSql("   SMW_TITLE,");
            sql.addSql("   SMW_MARK,");
            sql.addSql("   SMW_BODY,");
            sql.addSql("   SMW_BODY_PLAIN,");
            sql.addSql("   SMW_TYPE,");
            sql.addSql("   SMW_SIZE,");
            sql.addSql("   SMW_JKBN,");
            sql.addSql("   SMW_AUID,");
            sql.addSql("   SMW_ADATE,");
            sql.addSql("   SMW_EUID,");
            sql.addSql("   SMW_EDATE,");
            sql.addSql("   SMW_ORIGIN,");
            sql.addSql("   SMW_EDIT_KBN");
            sql.addSql(" from ");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   SMW_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   SMW_JKBN <> ?");
            sql.addIntValue(sacSid);
            sql.addIntValue(smwSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_DELETE);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlWmeisFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

//追加メソッド

    /**
     * <p>削除するメールSIDリストを取得する。
     * @param delMdl SmlAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @param limit 取得件数
     * @param offset 取得開始行数
     * @return 削除メールSIDリスト
     * @throws SQLException SQL実行例外
     */
    public Map<SmlWmeisModel, Long> getDeleteMail(
            SmlAdelModel delMdl, int kbn, int limit, int offset) throws SQLException {

        Map<SmlWmeisModel, Long> ret = new HashMap<SmlWmeisModel, Long>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

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
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMW_SID,");
            sql.addSql("   SMW_SIZE");
            sql.addSql(" from ");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where ");
            sql.addSql("   SMW_EDATE <= ?");
            sql.addSql(" and");
            sql.addSql("   SMW_JKBN = ?");
            sql.addSql("   order by SMW_SID");
            sql.addSql("   limit ?");
            sql.addSql("   offset ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            int year = delMdl.getSadWdelYear();
            int month = delMdl.getSadWdelMonth();
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

            sql.addDateValue(delUd);
            sql.addIntValue(jkbn);
            sql.addIntValue(limit);
            sql.addIntValue(offset);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            SmlWmeisModel bean = null;
            while (rs.next()) {
                bean = new SmlWmeisModel();
                bean.setSacSid(rs.getInt("SAC_SID"));
                bean.setSmwSid(rs.getInt("SMW_SID"));
                ret.put(bean, rs.getLong("SMW_SIZE"));
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
     * @param sidList 削除メールSIDリスト
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @param limit 取得件数
     * @param offset 取得開始行数
     * @return 削除メールSIDリスト
     * @throws SQLException SQL実行例外
     */
    public Map<SmlWmeisModel, Long> getDeleteMail(
            List<Integer> sidList, int kbn, int limit, int offset) throws SQLException {

        Map<SmlWmeisModel, Long> ret = new HashMap<SmlWmeisModel, Long>();
        if (sidList == null || sidList.size() == 0) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        int jkbn = -1;
        if (kbn == 1) {
            jkbn = GSConstSmail.SML_JTKBN_TOROKU;
        } else if (kbn == 2) {
            jkbn = GSConstSmail.SML_JTKBN_GOMIBAKO;
        }

        try {
            //SQL文
            StringBuilder sb = new StringBuilder();
            sb.append(" select");
            sb.append("   SAC_SID,");
            sb.append("   SMW_SID,");
            sb.append("   SMW_SIZE");
            sb.append(" from ");
            sb.append("   SML_WMEIS");
            sb.append(" where ");
            sb.append("   SMW_SID in (");
            final String sqlBaseTop = sb.toString();
            
            sb = new StringBuilder();
            sb.append("   )");
            sb.append(" and");
            sb.append("   SMW_JKBN = ?");
            sb.append("   order by SMW_SID");
            sb.append("   limit ?");
            sb.append("   offset ?");
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
                    log__.info(sql.toLogString());
                    pstmt = con.prepareStatement(sql.toSqlString());
                    sql.addIntValue(jkbn);
                    sql.addIntValue(limit);
                    sql.addIntValue(offset);
                    sql.setParameter(pstmt);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        SmlWmeisModel bean = new SmlWmeisModel();
                        bean.setSacSid(rs.getInt("SAC_SID"));
                        bean.setSmwSid(rs.getInt("SMW_SID"));
                        ret.put(bean, rs.getLong("SMW_SIZE"));
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
     * <br>[機  能] 指定したアカウントのゴミ箱内の草稿リストを取得する(本文を除く)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @return ゴミ箱内の草稿リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmlWmeisModel> getGomibakoMailList(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<SmlWmeisModel> ret = new ArrayList<SmlWmeisModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMW_SID,");
            sql.addSql("   SMW_TITLE,");
            sql.addSql("   SMW_MARK,");
            sql.addSql("   SMW_TYPE,");
            sql.addSql("   SMW_SIZE,");
            sql.addSql("   SMW_JKBN,");
            sql.addSql("   SMW_AUID,");
            sql.addSql("   SMW_ADATE,");
            sql.addSql("   SMW_EUID,");
            sql.addSql("   SMW_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_WMEIS");
            sql.addSql(" where");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMW_JKBN = ?");
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_GOMIBAKO);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SmlWmeisModel bean = new SmlWmeisModel();
                bean.setSacSid(rs.getInt("SAC_SID"));
                bean.setSmwSid(rs.getInt("SMW_SID"));
                bean.setSmwTitle(rs.getString("SMW_TITLE"));
                bean.setSmwMark(rs.getInt("SMW_MARK"));
                bean.setSmwType(rs.getInt("SMW_TYPE"));
                bean.setSmwSize(rs.getLong("SMW_SIZE"));
                bean.setSmwJkbn(rs.getInt("SMW_JKBN"));
                bean.setSmwAuid(rs.getInt("SMW_AUID"));
                bean.setSmwAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMW_ADATE")));
                bean.setSmwEuid(rs.getInt("SMW_EUID"));
                bean.setSmwEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMW_EDATE")));
                ret.add(bean);
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
     * <p>Create SML_WMEIS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlWmeisModel
     * @throws SQLException SQL実行例外
     */
    private SmlWmeisModel __getSmlWmeisFromRs(ResultSet rs) throws SQLException {
        SmlWmeisModel bean = new SmlWmeisModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSmwSid(rs.getInt("SMW_SID"));
        bean.setSmwTitle(rs.getString("SMW_TITLE"));
        bean.setSmwMark(rs.getInt("SMW_MARK"));
        bean.setSmwBody(rs.getString("SMW_BODY"));
        bean.setSmwBodyPlain(rs.getString("SMW_BODY_PLAIN"));
        bean.setSmwType(rs.getInt("SMW_TYPE"));
        bean.setSmwSize(rs.getLong("SMW_SIZE"));
        bean.setSmwJkbn(rs.getInt("SMW_JKBN"));
        bean.setSmwAuid(rs.getInt("SMW_AUID"));
        bean.setSmwAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMW_ADATE")));
        bean.setSmwEuid(rs.getInt("SMW_EUID"));
        bean.setSmwEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMW_EDATE")));
        bean.setSmwOrigin(rs.getInt("SMW_ORIGIN"));
        bean.setSmwEditKbn(rs.getInt("SMW_EDIT_KBN"));
        return bean;
    }
}