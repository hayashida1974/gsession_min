package jp.groupsession.v2.wml.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.wml.model.base.WmlAccountUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WML_ACCOUNT_USER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlAccountUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlAccountUserDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlAccountUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlAccountUserDao(Connection con) {
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
            sql.addSql("drop table WML_ACCOUNT_USER");

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
            sql.addSql(" create table WML_ACCOUNT_USER (");
            sql.addSql("   WAC_SID NUMBER(10,0) not null,");
            sql.addSql("   GRP_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (WAC_SID)");
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
     * <p>Insert WML_ACCOUNT_USER Data Bindding JavaBean
     * @param bean WML_ACCOUNT_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlAccountUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_ACCOUNT_USER(");
            sql.addSql("   WAC_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWacSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
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
     * <p>Update WML_ACCOUNT_USER Data Bindding JavaBean
     * @param bean WML_ACCOUNT_USER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlAccountUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_ACCOUNT_USER");
            sql.addSql(" set ");
            sql.addSql("   GRP_SID=?,");
            sql.addSql("   USR_SID=?");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
            //where
            sql.addIntValue(bean.getWacSid());

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
     * <p>Select WML_ACCOUNT_USER All Data
     * @return List in WML_ACCOUNT_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountUserModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlAccountUserModel> ret = new ArrayList<WmlAccountUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT_USER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlAccountUserFromRs(rs));
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
     * <p>Select WML_ACCOUNT_USER
     * @param wacSid WAC_SID
     * @return List in WML_ACCOUNT_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlAccountUserModel> select(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlAccountUserModel> ret = new ArrayList<WmlAccountUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WAC_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getWmlAccountUserFromRs(rs));
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
     * <p>Delete WML_ACCOUNT_USER
     * @param wacSid WAC_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
     * <p>Delete WML_ACCOUNT_USER
     * @param wacSid WAC_SID
     * @param usrSids ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wacSid, String[] usrSids) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            if (usrSids != null && usrSids.length > 0) {
                sql.addSql(" and ");
                sql.addSql("   USR_SID in (");
                for (int i = 0; i < usrSids.length; i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(usrSids[i]);
                }
                sql.addSql(")");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
     * <br>[機  能] 指定したユーザに関連するアカウント使用者情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteOfUser(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

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
     * <br>[機  能] 指定したグループに関連するアカウント使用者情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSid グループSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteOfGroup(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);

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
     * <br>[機  能] 新規登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param wacType アカウント種別
     * @param sidList 団体SID or ユーザSIDの一覧
     * @throws SQLException SQL実行時例外
     */
    public void insert(int wacSid, int wacType, String[] sidList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            String valueString = "(" + wacSid + ", ";
            if (wacType == GSConstWebmail.WAC_TYPE_GROUP) {
                valueString += "?, null)";
            } else {
                valueString += "null, ?)";
            }

            SqlBuffer sql = null;
            for (int count = 0; count < sidList.length; count += 100) {
                sql = new SqlBuffer();
                sql.addSql(" insert into");
                sql.addSql("   WML_ACCOUNT_USER(WAC_SID, GRP_SID, USR_SID)");
                sql.addSql(" values");
                sql.addSql("   " + valueString);
                sql.addIntValue(Integer.parseInt(sidList[count]));

                for (int index = count + 1;
                    (index < sidList.length && index < count + 100); index++) {
                    sql.addSql("   ," + valueString);
                    sql.addIntValue(Integer.parseInt(sidList[index]));
                }

                pstmt = con.prepareStatement(sql.toSqlString());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定したアカウントの使用者(ユーザ)一覧を取得する
     * <br>[解  説]
     * <br>[備  考] 削除ユーザは除く
     * @param wacSid アカウントSID
     * @return 使用者(ユーザ)一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getUserList(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   WML_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID not in (");
            sql.addSql("     select USR_SID from CMN_USRM");
            sql.addSql("     where USR_JKBN = ?");
            sql.addSql("   )");

            log__.info(sql.toLogString());
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_DELETE);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <br>[機  能] 指定したアカウントの使用者(グループ)からユーザ一覧を取得する
     * <br>[解  説]
     * <br>[備  考] 削除ユーザは除く
     * @param wacSid アカウントSID
     * @return ユーザ一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getBelongUserList(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM,");
            sql.addSql("   WML_ACCOUNT_USER");
            sql.addSql(" where ");
            sql.addSql("   WML_ACCOUNT_USER.WAC_SID=?");
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.GRP_SID = WML_ACCOUNT_USER.GRP_SID");
            sql.addSql(" and ");
            sql.addSql("    CMN_BELONGM.USR_SID not in (");
            sql.addSql("     select USR_SID from CMN_USRM");
            sql.addSql("     where USR_JKBN = ?");
            sql.addSql("   )");

            log__.info(sql.toLogString());
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstUser.USER_JTKBN_DELETE);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <p>Create WML_ACCOUNT_USER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlAccountUserModel
     * @throws SQLException SQL実行例外
     */
    private WmlAccountUserModel __getWmlAccountUserFromRs(ResultSet rs) throws SQLException {
        WmlAccountUserModel bean = new WmlAccountUserModel();
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        return bean;
    }
}
