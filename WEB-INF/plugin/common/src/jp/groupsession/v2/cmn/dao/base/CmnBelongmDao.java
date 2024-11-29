package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>CMN_BELONGM Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnBelongmDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnBelongmDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnBelongmDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnBelongmDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_BELONGM Data Bindding JavaBean
     * @param bean CMN_BELONGM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnBelongmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_BELONGM(");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BEG_AUID,");
            sql.addSql("   BEG_ADATE,");
            sql.addSql("   BEG_EUID,");
            sql.addSql("   BEG_EDATE,");
            sql.addSql("   BEG_DEFGRP,");
            sql.addSql("   BEG_GRPKBN");
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
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getBegAuid());
            sql.addDateValue(bean.getBegAdate());
            sql.addIntValue(bean.getBegEuid());
            sql.addDateValue(bean.getBegEdate());
            sql.addIntValue(bean.getBegDefgrp());
            sql.addIntValue(bean.getBegGrpkbn());
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
     * <p>Insert CMN_BELONGM Data Bindding JavaBean
     * @param addLists ArrayList&lt;CMN_BELONGM&gt;
     * @throws SQLException SQL実行例外
     */
    public void insert(ArrayList < CmnBelongmModel > addLists) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            for (CmnBelongmModel bean : addLists) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" CMN_BELONGM(");
                sql.addSql("   GRP_SID,");
                sql.addSql("   USR_SID,");
                sql.addSql("   BEG_AUID,");
                sql.addSql("   BEG_ADATE,");
                sql.addSql("   BEG_EUID,");
                sql.addSql("   BEG_EDATE,");
                sql.addSql("   BEG_DEFGRP,");
                sql.addSql("   BEG_GRPKBN");
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
                sql.addIntValue(bean.getGrpSid());
                sql.addIntValue(bean.getUsrSid());
                sql.addIntValue(bean.getBegAuid());
                sql.addDateValue(bean.getBegAdate());
                sql.addIntValue(bean.getBegEuid());
                sql.addDateValue(bean.getBegEdate());
                sql.addIntValue(bean.getBegDefgrp());
                sql.addIntValue(bean.getBegGrpkbn());

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
     * <p>グループ所属ユーザーの追加・変更を行います。
     * @param bean CMN_BELONGM Data Bindding JavaBean
     * @param users 更新対象ユーザー配列
     * @throws SQLException SQL実行例外
     */
    public void insertBelongUser(CmnBelongmModel bean, String[] users) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        // ユーザーリスト作成
        StringBuilder bufUsers = new StringBuilder();
        //1件目
        bufUsers.append(users[0]);
        for (int i = 1; i < users.length; i++) {
            //2件目移行
            bufUsers.append(",");
            bufUsers.append(users[i]);
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_BELONGM(");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BEG_AUID,");
            sql.addSql("   BEG_ADATE,");
            sql.addSql("   BEG_EUID,");
            sql.addSql("   BEG_EDATE,");
            sql.addSql("   BEG_DEFGRP,");
            sql.addSql("   BEG_GRPKBN");
            sql.addSql(" )");
            sql.addSql(" select");
            sql.addSql("   ?,");
            sql.addSql("   USR_SID,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   USR_SID in (" + bufUsers.toString() + ")");
            sql.addSql(" and");
            sql.addSql("   not exists (");
            sql.addSql("     select 1 from CMN_BELONGM");
            sql.addSql("     where GRP_SID = ?");
            sql.addSql("     and");
            sql.addSql("        CMN_USRM.USR_SID = CMN_BELONGM.USR_SID");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getBegAuid());
            sql.addDateValue(bean.getBegAdate());
            sql.addIntValue(bean.getBegEuid());
            sql.addDateValue(bean.getBegEdate());
            sql.addIntValue(bean.getBegDefgrp());
            sql.addIntValue(GSConst.USER_NOT_ADMIN);
            sql.addIntValue(bean.getGrpSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

            pstmt = null;
            //SQL文
            sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID not in (" + bufUsers.toString() + ")");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGrpSid());

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
     * <p>グループ所属ユーザーのグループ区分を管理者にする
     * @param grpKbn グループ区分
     * @param gsid グループSID
     * @param users 更新対象ユーザー配列
     * @throws SQLException SQL実行例外
     */
    public void updataBelongUserGrpKbn(int grpKbn, int gsid, String[] users)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        // ユーザーリスト作成
        StringBuilder bufUsers = new StringBuilder();
        //1件目
        bufUsers.append(users[0]);
        for (int i = 1; i < users.length; i++) {
            //2件目移行
            bufUsers.append(",");
            bufUsers.append(users[i]);
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" set");
            sql.addSql("   BEG_GRPKBN = ?");
            sql.addSql(" where");
            sql.addSql("   GRP_SID = ?");
            sql.addSql(" and");
            sql.addSql("   USR_SID in (" + bufUsers.toString() + ")");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpKbn);
            sql.addIntValue(gsid);
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
     * <p>グループ所属者全員の管理者区分を変更する
     * @param gsid グループSID
     * @param grpKbn グループ区分
     * @throws SQLException SQL実行例外
     */
    public void updataAllBelongUserGrpKbn(int gsid, int grpKbn) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" set");
            sql.addSql("   BEG_GRPKBN = ?");
            sql.addSql(" where");
            sql.addSql("   GRP_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpKbn);
            sql.addIntValue(gsid);
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
     * <p>Update CMN_BELONGM Data Bindding JavaBean
     * @param bean CMN_BELONGM Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnBelongmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" set ");
            sql.addSql("   BEG_AUID=?,");
            sql.addSql("   BEG_ADATE=?,");
            sql.addSql("   BEG_EUID=?,");
            sql.addSql("   BEG_EDATE=?,");
            sql.addSql("   BEG_DEFGRP=?");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBegAuid());
            sql.addDateValue(bean.getBegAdate());
            sql.addIntValue(bean.getBegEuid());
            sql.addDateValue(bean.getBegEdate());
            sql.addIntValue(bean.getBegDefgrp());
            //where
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Select CMN_BELONGM All Data
     * @return List in CMN_BELONGMModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBelongmModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBelongmModel> ret = new ArrayList<CmnBelongmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BEG_AUID,");
            sql.addSql("   BEG_ADATE,");
            sql.addSql("   BEG_EUID,");
            sql.addSql("   BEG_EDATE,");
            sql.addSql("   BEG_DEFGRP,");
            sql.addSql("   BEG_GRPKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONGM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnBelongmFromRs(rs));
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
     * <p>ユーザーが所属するグループのモデルリストを返却します
     * @param usid ユーザーSID
     * @return List in CMN_BELONGMModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBelongmModel> selectUserBelongGroup(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBelongmModel> ret = new ArrayList<CmnBelongmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BEG_AUID,");
            sql.addSql("   BEG_ADATE,");
            sql.addSql("   BEG_EUID,");
            sql.addSql("   BEG_EDATE,");
            sql.addSql("   BEG_DEFGRP,");
            sql.addSql("   BEG_GRPKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnBelongmFromRs(rs));
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
     * <p>ユーザーが所属するグループのSIDリストを返却します
     * @param usid ユーザーSID
     * @return グループのSIDリスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectUserBelongGroupSid(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(new Integer(rs.getInt("GRP_SID")));
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
     * <br>[機  能] ユーザーが所属するグループのSIDリストを返却します
     * <br>[解  説]
     * <br>[備  考] ユーザSIDがない場合は空のグループSIDリストを返します
     * @param usidList ユーザーSIDリスト
     * @return グループのSIDリスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectUserBelongGroupSid(
            List<String> usidList)
            throws SQLException {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        int usidListSize = usidList.size();
        if (usidListSize < 1) {
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
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   USR_SID in");
            sql.addSql("     (");
            for (int i = 0; i < usidListSize - 1; ++i) {
                sql.addSql("     ?,");
            }
            sql.addSql("     ?");
            sql.addSql("     )");
            sql.addSql("  group by ");
            sql.addSql("    GRP_SID ");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (String usid : usidList) {
                sql.addIntValue(Integer.parseInt(usid));
            }

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(new Integer(rs.getInt("GRP_SID")));
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
     * <p>ユーザーが所属するグループで<br>
     * デフォルトグループのグループSIDを返します。
     * @param usid ユーザーSID
     * @return List in CMN_BELONGMModel
     * @throws SQLException SQL実行例外
     */
    public int selectUserBelongGroupDef(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BEG_AUID,");
            sql.addSql("   BEG_ADATE,");
            sql.addSql("   BEG_EUID,");
            sql.addSql("   BEG_EDATE,");
            sql.addSql("   BEG_DEFGRP,");
            sql.addSql("   BEG_GRPKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" AND ");
            sql.addSql("   BEG_DEFGRP=1");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                CmnBelongmModel model = __getCmnBelongmFromRs(rs);
                ret = model.getGrpSid();
                log__.debug("BEG_DEFGRP=1 :" + ret);
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
     * <p>指定グループに所属するユーザリストを返却します
     * @param gsid グループSID
     * @return List in CMN_BELONGMModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectBelongUserSid(int gsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(new Integer(rs.getInt("USR_SID")));
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
     * <p>指定グループをデフォルトグループに設定しているユーザリストを返却します
     * @param gsid グループSID
     * @return List in CMN_BELONGMModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectBelongDefaultUserSid(int gsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and ");
            sql.addSql("   BEG_DEFGRP=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gsid);
            sql.addIntValue(GSConstUser.BEG_DEFGRP_DEFAULT);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(new Integer(rs.getInt("USR_SID")));
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
     * <p>指定グループに所属するユーザリストを返却します(削除済みユーザ管理者ユーザのぞく)
     * @param gsid グループSID
     * @return List in CMN_BELONGMModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectBelongLiveUserSid(int gsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BELONGM.USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID=?");
            sql.addIntValue(gsid);
            sql.addSql("   and CMN_USRM.USR_SID > ?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addSql("   and CMN_USRM.USR_JKBN = ?");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql("   and CMN_BELONGM.USR_SID=CMN_USRM.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(new Integer(rs.getInt("USR_SID")));
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
     * <p>指定グループの管理者ユーザを取得します
     * @param grpSid グループSID
     * @return List in CMN_BELONGMModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnBelongmModel> selectBossModel(int grpSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBelongmModel> ret
                        = new ArrayList<CmnBelongmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BELONGM.GRP_SID,");
            sql.addSql("   CMN_BELONGM.USR_SID,");
            sql.addSql("   CMN_BELONGM.BEG_AUID,");
            sql.addSql("   CMN_BELONGM.BEG_ADATE,");
            sql.addSql("   CMN_BELONGM.BEG_EUID,");
            sql.addSql("   CMN_BELONGM.BEG_EDATE,");
            sql.addSql("   CMN_BELONGM.BEG_DEFGRP,");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql("   ,CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID=?");
            sql.addIntValue(grpSid);
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN=1");
            sql.addSql(" and CMN_USRM.USR_SID > ?");
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addSql(" and CMN_USRM.USR_JKBN = ?");
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addSql("   and CMN_BELONGM.USR_SID=CMN_USRM.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.USR_SID=CMN_USRM.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCmnBelongmFromRs(rs));
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
     * <p>Select CMN_BELONGM
     * @param usid ユーザSID
     * @param gsid グループSID
     * @return CMN_BELONGMModel
     * @throws SQLException SQL実行例外
     */
    public CmnBelongmModel select(int usid, int gsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnBelongmModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BEG_AUID,");
            sql.addSql("   BEG_ADATE,");
            sql.addSql("   BEG_EUID,");
            sql.addSql("   BEG_EDATE,");
            sql.addSql("   BEG_DEFGRP,");
            sql.addSql("   BEG_GRPKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gsid);
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCmnBelongmFromRs(rs);
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
     * <p>グループに所属するユーザのSIDを取得する
     * @param gsids グループSID
     * @return CMN_BELONGMModel
     * @throws SQLException SQL実行例外
     */
    public List<String> select(String[] gsids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   distinct USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            if (gsids != null && gsids.length > 0) {
                sql.addSql(" where ");
                sql.addSql("   GRP_SID in (");
                for (int i = 0; i < gsids.length; i++) {
                    if (i > 0) {
                        sql.addSql(" ,");
                    }
                    sql.addSql(gsids[i]);
                }
                sql.addSql(")");
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(String.valueOf(rs.getInt("USR_SID")));
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
     * <p>CMN_BELONGMの存在チェックを行います
     * @param usid ユーザSID
     * @param gsid グループSID
     * @return boolean true: 存在 false: 非存在
     * @throws SQLException SQL実行例外
     */
    public boolean isExist(int gsid, int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count (*) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gsid);
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("cnt");
            }

            if (count > 0) {
                ret = true;
            }

            log__.debug("===>getInt :" + rs.getInt("cnt"));
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Delete CMN_BELONGM
     * @param bean CMN_BELONGM Model
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(CmnBelongmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>指定グループに所属するユーザーを全削除します
     * @param gSid グループSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int gSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gSid);

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
     * <p>指定グループ以外の所属データを全削除します
     * @param gSid グループSID
     * @param usrSid ユーザSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delGrp(int gSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID<>?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(gSid);
            sql.addIntValue(usrSid);

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
     * <p>指定ユーザーが所属するグループを<br>
     * 全削除します
     * @param uSid ユーザーSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteUserBelongGroup(int uSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(uSid);

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
     * <p>Create CMN_BELONGM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnBelongmModel
     * @throws SQLException SQL実行例外
     */
    private CmnBelongmModel __getCmnBelongmFromRs(ResultSet rs) throws SQLException {
        CmnBelongmModel bean = new CmnBelongmModel();
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setBegAuid(rs.getInt("BEG_AUID"));
        bean.setBegAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BEG_ADATE")));
        bean.setBegEuid(rs.getInt("BEG_EUID"));
        bean.setBegEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BEG_EDATE")));
        bean.setBegDefgrp(rs.getInt("BEG_DEFGRP"));
        bean.setBegGrpkbn(rs.getInt("BEG_GRPKBN"));
        return bean;
    }
}
