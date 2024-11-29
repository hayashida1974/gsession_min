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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sml.model.SmlAccountForwardModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>SML_ACCOUNT_FORWARD Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlAccountForwardDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlAccountForwardDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlAccountForwardDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlAccountForwardDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ショートメール転送設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean SML_ACCOUNT_FORWARD Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlAccountForwardModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_ACCOUNT_FORWARD(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAF_MAILFW,");
            sql.addSql("   SAF_MAIL_DF,");
            sql.addSql("   SAF_SMAIL_OP,");
            sql.addSql("   SAF_HURIWAKE,");
            sql.addSql("   SAF_ZMAIL1,");
            sql.addSql("   SAF_ZMAIL2,");
            sql.addSql("   SAF_ZMAIL3,");
            sql.addSql("   SAF_AUID,");
            sql.addSql("   SAF_ADATE,");
            sql.addSql("   SAF_EUID,");
            sql.addSql("   SAF_EDATE");
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

            sql.addIntValue(bean.getSacSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getSafMailfw());
            sql.addStrValue(bean.getSafMailDf());
            sql.addIntValue(bean.getSafSmailOp());
            sql.addIntValue(bean.getSafHuriwake());
            sql.addStrValue(bean.getSafZmail1());
            sql.addStrValue(bean.getSafZmail2());
            sql.addStrValue(bean.getSafZmail3());
            sql.addIntValue(bean.getSafAuid());
            sql.addDateValue(bean.getSafAdate());
            sql.addIntValue(bean.getSafEuid());
            sql.addDateValue(bean.getSafEdate());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update SML_ACCOUNT_FORWARD Data Bindding JavaBean
     * @param bean SML_ACCOUNT_FORWARD Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(SmlAccountForwardModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ACCOUNT_FORWARD");
            sql.addSql(" set ");
            sql.addSql("   SAF_MAILFW=?,");
            sql.addSql("   SAF_MAIL_DF=?,");
            sql.addSql("   SAF_SMAIL_OP=?,");
            sql.addSql("   SAF_HURIWAKE=?,");
            sql.addSql("   SAF_ZMAIL1=?,");
            sql.addSql("   SAF_ZMAIL2=?,");
            sql.addSql("   SAF_ZMAIL3=?,");
            sql.addSql("   SAF_EUID=?,");
            sql.addSql("   SAF_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSafMailfw());
            sql.addStrValue(bean.getSafMailDf());
            sql.addIntValue(bean.getSafSmailOp());
            sql.addIntValue(bean.getSafHuriwake());
            sql.addStrValue(bean.getSafZmail1());
            sql.addStrValue(bean.getSafZmail2());
            sql.addStrValue(bean.getSafZmail3());
            sql.addIntValue(bean.getSafEuid());
            sql.addDateValue(bean.getSafEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getSacSid());

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
     * メール転送設定の更新を行う
     * @param bean SML_ACCOUNT_FORWARD Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSmlForward(SmlAccountForwardModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_ACCOUNT_FORWARD");
            sql.addSql(" set ");
            sql.addSql("   SAF_MAILFW=?,");
            sql.addSql("   SAF_MAIL_DF=?,");
            sql.addSql("   SAF_SMAIL_OP=?,");
            sql.addSql("   SAF_HURIWAKE=?,");
            sql.addSql("   SAF_ZMAIL1=?,");
            sql.addSql("   SAF_ZMAIL2=?,");
            sql.addSql("   SAF_ZMAIL3=?,");
            sql.addSql("   SAF_EUID=?,");
            sql.addSql("   SAF_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   SAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSafMailfw());
            sql.addStrValue(bean.getSafMailDf());
            sql.addIntValue(bean.getSafSmailOp());
            sql.addIntValue(bean.getSafHuriwake());
            sql.addStrValue(bean.getSafZmail1());
            sql.addStrValue(bean.getSafZmail2());
            sql.addStrValue(bean.getSafZmail3());
            sql.addIntValue(bean.getSafEuid());
            sql.addDateValue(bean.getSafEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getSacSid());

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
     * <p>Select SML_ACCOUNT_FORWARD All Data
     * @return List in SML_ACCOUNT_FORWARDModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlAccountForwardModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlAccountForwardModel> ret = new ArrayList<SmlAccountForwardModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAF_MAILFW,");
            sql.addSql("   SAF_MAIL_DF,");
            sql.addSql("   SAF_SMAIL_OP,");
            sql.addSql("   SAF_HURIWAKE,");
            sql.addSql("   SAF_ZMAIL1,");
            sql.addSql("   SAF_ZMAIL2,");
            sql.addSql("   SAF_ZMAIL3,");
            sql.addSql("   SAF_AUID,");
            sql.addSql("   SAF_ADATE,");
            sql.addSql("   SAF_EUID,");
            sql.addSql("   SAF_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT_FORWARD");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSafUserFromRs(rs));
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
     * <br>[機  能] ユーザSIDからショートメール転送設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param sacSid アカウントSID
     * @return SML_ACCOUNT_FORWARDModel
     * @throws SQLException SQL実行例外
     */
    public SmlAccountForwardModel getSafUserInfo(int userSid, int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlAccountForwardModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   SAF_MAILFW,");
            sql.addSql("   SAF_MAIL_DF,");
            sql.addSql("   SAF_SMAIL_OP,");
            sql.addSql("   SAF_HURIWAKE,");
            sql.addSql("   SAF_ZMAIL1,");
            sql.addSql("   SAF_ZMAIL2,");
            sql.addSql("   SAF_ZMAIL3,");
            sql.addSql("   SAF_AUID,");
            sql.addSql("   SAF_ADATE,");
            sql.addSql("   SAF_EUID,");
            sql.addSql("   SAF_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT_FORWARD");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   SAC_SID = ?");

            sql.addIntValue(userSid);
            sql.addIntValue(sacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getSafUserFromRs(rs);
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
     * <br>[機  能] ユーザSIDからショートメール転送設定を取得する
     * <br>[解  説]
     * <br>[備  考] ログイン停止ユーザ、削除ユーザは除外
     * @param sacSid アカウントSID
     * @return SML_ACCOUNT_FORWARDModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlAccountForwardModel> getSafUserInfoList(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SmlAccountForwardModel> ret = new ArrayList<SmlAccountForwardModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAC_SID,");
            sql.addSql("   SML_ACCOUNT_FORWARD.USR_SID,");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAF_MAILFW,");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAF_MAIL_DF,");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAF_SMAIL_OP,");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAF_HURIWAKE,");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAF_ZMAIL1,");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAF_ZMAIL2,");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAF_ZMAIL3,");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAF_AUID,");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAF_ADATE,");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAF_EUID,");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAF_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT_FORWARD,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   SML_ACCOUNT_FORWARD.SAC_SID = ?");
            sql.addSql("   and SML_ACCOUNT_FORWARD.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("   and CMN_USRM.USR_JKBN = ?");
            sql.addSql("   and CMN_USRM.USR_UKO_FLG = ?");

            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(GSConst.YUKOMUKO_YUKO);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getSafUserFromRs(rs));
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
     * <br>[機  能] ショートメール転送設定を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param sacSid アカウントSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteSmlForward(int userSid, int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
//        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT_FORWARD");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   SAC_SID=?");

            sql.addIntValue(userSid);
            sql.addIntValue(sacSid);

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
     * <br>[機  能] ショートメール転送設定を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSids ユーザSID
     * @param sacSid アカウントSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteCantUseUser(int sacSid, String[] userSids) throws SQLException {

        PreparedStatement pstmt = null;
//        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT_FORWARD");
            sql.addSql(" where ");
            sql.addSql("   USR_SID not in (");
            for (int i = 0; i < userSids.length; i++) {
                if (i > 0) {
                    sql.addSql(" , ");
                }
                sql.addSql(userSids[i]);
            }
            sql.addSql(" )");
            sql.addSql(" and ");
            sql.addSql("   SAC_SID=?");


            sql.addIntValue(sacSid);

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
     * <br>[機  能] ショートメール転送設定を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteAllAccountForward(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
//        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT_FORWARD");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");

            sql.addIntValue(sacSid);

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
     * <p>Create SML_ACCOUNT_FORWARD Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlAccountForwardModel
     * @throws SQLException SQL実行例外
     */
    private SmlAccountForwardModel __getSafUserFromRs(ResultSet rs) throws SQLException {
        SmlAccountForwardModel bean = new SmlAccountForwardModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setSafMailfw(rs.getInt("SAF_MAILFW"));
        bean.setSafMailDf(rs.getString("SAF_MAIL_DF"));
        bean.setSafSmailOp(rs.getInt("SAF_SMAIL_OP"));
        bean.setSafHuriwake(rs.getInt("SAF_HURIWAKE"));
        bean.setSafZmail1(rs.getString("SAF_ZMAIL1"));
        bean.setSafZmail2(rs.getString("SAF_ZMAIL2"));
        bean.setSafZmail3(rs.getString("SAF_ZMAIL3"));

        bean.setSafAuid(rs.getInt("SAF_AUID"));
        bean.setSafAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SAF_ADATE")));
        bean.setSafEuid(rs.getInt("SAF_EUID"));
        bean.setSafEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SAF_EDATE")));
        return bean;
    }
}