package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.CmnBelongmHistoryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CMN_BELONGM_HISTORY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CmnBelongmHistoryDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnBelongmHistoryDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnBelongmHistoryDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnBelongmHistoryDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_BELONGM_HISTORY Data Bindding JavaBean
     * @param bean CMN_BELONGM_HISTORY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CmnBelongmHistoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_BELONGM_HISTORY(");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BEG_DATE,");
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
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addDateValue(bean.getBegDate());
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
     * <p>Insert CMN_BELONGM_HISTORY Data Bindding JavaBean
     * @param addLists ArrayList&lt;CMN_BELONGM_HISTORY&gt;
     * @throws SQLException SQL実行例外
     */
    public void insert(ArrayList < CmnBelongmHistoryModel > addLists) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            for (CmnBelongmHistoryModel bean : addLists) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" CMN_BELONGM_HISTORY(");
                sql.addSql("   GRP_SID,");
                sql.addSql("   USR_SID,");
                sql.addSql("   BEG_DATE,");
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
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getGrpSid());
                sql.addIntValue(bean.getUsrSid());
                sql.addDateValue(bean.getBegDate());
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
     * <p>グループ所属ユーザーのグループ区分を管理者にする
     * @param grpKbn グループ区分
     * @param gsid グループSID
     * @param date 日付
     * @param users 更新対象ユーザー配列
     * @throws SQLException SQL実行例外
     */
    public void updataBelongUserGrpKbn(int grpKbn, int gsid, UDate date, String[] users)
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

        UDate begDate = new UDate();
        begDate.setDate(date.getYear(), date.getMonth(), date.getIntDay());
        String begDateStr = begDate.getDateStringForSql();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   CMN_BELONGM_HISTORY");
            sql.addSql(" set");
            sql.addSql("   BEG_GRPKBN = ?");
            sql.addSql(" where");
            sql.addSql("   GRP_SID = ?");
            sql.addSql(" and");
            sql.addSql("   USR_SID in (" + bufUsers.toString() + ")");
            sql.addSql(" and");
            sql.addSql("   BEG_DATE = cast('" + begDateStr + "' as DATE)");

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
     * @param date 日付
     * @throws SQLException SQL実行例外
     */
    public void updataAllBelongUserGrpKbn(int gsid, int grpKbn, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        UDate begDate = new UDate();
        begDate.setDate(date.getYear(), date.getMonth(), date.getIntDay());
        String begDateStr = begDate.getDateStringForSql();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   CMN_BELONGM_HISTORY");
            sql.addSql(" set");
            sql.addSql("   BEG_GRPKBN = ?");
            sql.addSql(" where");
            sql.addSql("   GRP_SID = ?");
            sql.addSql(" and");
            sql.addSql("   BEG_DATE = cast('" + begDateStr + "' as DATE)");

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
     * <p>Update CMN_BELONGM_HISTORY Data Bindding JavaBean
     * @param bean CMN_BELONGM_HISTORY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CmnBelongmHistoryModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        UDate begDate = new UDate();
        begDate.setDate(bean.getBegDate().getYear(),
                        bean.getBegDate().getMonth(),
                        bean.getBegDate().getIntDay());
        String begDateStr = begDate.getDateStringForSql();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BELONGM_HISTORY");
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
            sql.addSql(" and");
            sql.addSql("   BEG_DATE = cast('" + begDateStr + "' as DATE)");

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
     * <p>Select CMN_BELONGM_HISTORY All Data
     * @return List in CMN_BELONGM_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBelongmHistoryModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBelongmHistoryModel> ret = new ArrayList<CmnBelongmHistoryModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BEG_DATE,");
            sql.addSql("   BEG_AUID,");
            sql.addSql("   BEG_ADATE,");
            sql.addSql("   BEG_EUID,");
            sql.addSql("   BEG_EDATE,");
            sql.addSql("   BEG_DEFGRP,");
            sql.addSql("   BEG_GRPKBN");
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONGM_HISTORY");

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
     * @param date 日付
     * @return List in CMN_BELONGM_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBelongmHistoryModel> selectUserBelongGroup(int usid,
            UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBelongmHistoryModel> ret = new ArrayList<CmnBelongmHistoryModel>();
        con = getCon();

        UDate begDate = new UDate();
        begDate.setDate(date.getYear(), date.getMonth(), date.getIntDay());
        String begDateStr = begDate.getDateStringForSql();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BEG_DATE,");
            sql.addSql("   BEG_AUID,");
            sql.addSql("   BEG_ADATE,");
            sql.addSql("   BEG_EUID,");
            sql.addSql("   BEG_EDATE,");
            sql.addSql("   BEG_DEFGRP,");
            sql.addSql("   BEG_GRPKBN");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   BEG_DATE = cast('" + begDateStr + "' as DATE)");

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
     * <p>ユーザーが所属するグループのモデルリストを返却します
     * @param usid ユーザーSID
     * @param date 日付
     * @return List in CMN_BELONGM_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectUserBelongGroupSid(int usid,
                                       UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        UDate begDate = new UDate();
        begDate.setDate(date.getYear(), date.getMonth(), date.getIntDay());
        String begDateStr = begDate.getDateStringForSql();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   BEG_DATE = cast('" + begDateStr + "' as DATE)");

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
//    /**
//     * <p>ユーザーが所属するグループで<br>
//     * デフォルトグループのグループSIDを返します。
//     * @param usid ユーザーSID
//     * @return List in CMN_BELONGM_HISTORYModel
//     * @throws SQLException SQL実行例外
//     */
//    public int selectUserBelongGroupDef(int usid) throws SQLException {
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        int ret = 0;
//        con = getCon();
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" select");
//            sql.addSql("   GRP_SID,");
//            sql.addSql("   USR_SID,");
//            sql.addSql("   BEG_AUID,");
//            sql.addSql("   BEG_ADATE,");
//            sql.addSql("   BEG_EUID,");
//            sql.addSql("   BEG_EDATE,");
//            sql.addSql("   BEG_DEFGRP,");
//            sql.addSql("   BEG_GRPKBN");
//            sql.addSql(" from");
//            sql.addSql("   CMN_BELONGM_HISTORY");
//            sql.addSql(" where ");
//            sql.addSql("   USR_SID=?");
//            sql.addSql(" AND ");
//            sql.addSql("   BEG_DEFGRP=1");
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//            sql.addIntValue(usid);
//
//            log__.info(sql.toLogString());
//            sql.setParameter(pstmt);
//            rs = pstmt.executeQuery();
//            if (rs.next()) {
//                CmnBelongmHistoryModel model = __getCmnBelongmFromRs(rs);
//                ret = model.getGrpSid();
//                log__.debug("BEG_DEFGRP=1 :" + ret);
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeResultSet(rs);
//            JDBCUtil.closeStatement(pstmt);
//        }
//        return ret;
//    }
    /**
     * <p>指定グループに所属するユーザリストを返却します
     * @param gsid グループSID
     * @param date 日付
     * @return List in CMN_BELONGM_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> selectBelongUserSid(int gsid,
                                         UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        UDate begDate = new UDate();
        begDate.setDate(date.getYear(), date.getMonth(), date.getIntDay());
        String begDateStr = begDate.getDateStringForSql();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BEG_DATE = cast('" + begDateStr + "' as DATE)");

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
     * <p>Select CMN_BELONGM_HISTORY
     * @param usid ユーザSID
     * @param gsid グループSID
     * @param date 日付
     * @return CMN_BELONGM_HISTORYModel
     * @throws SQLException SQL実行例外
     */
    public CmnBelongmHistoryModel select(int usid,
                        int gsid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnBelongmHistoryModel ret = null;
        con = getCon();

        UDate begDate = new UDate();
        begDate.setDate(date.getYear(), date.getMonth(), date.getIntDay());
        String begDateStr = begDate.getDateStringForSql();

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
            sql.addSql("   CMN_BELONGM_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   BEG_DATE = cast('" + begDateStr + "' as DATE)");

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
     * <p>CMN_BELONGM_HISTORYの存在チェックを行います
     * @param usid ユーザSID
     * @param gsid グループSID
     * @param date 日付
     * @return boolean true: 存在 false: 非存在
     * @throws SQLException SQL実行例外
     */
    public boolean isExist(int gsid, int usid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();

        UDate begDate = new UDate();
        begDate.setDate(date.getYear(), date.getMonth(), date.getIntDay());
        String begDateStr = begDate.getDateStringForSql();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count (*) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   BEG_DATE = cast('" + begDateStr + "' as DATE)");

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
     * <p>Delete CMN_BELONGM_HISTORY
     * @param bean CMN_BELONGM_HISTORY Model
     * @param date 日付
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(CmnBelongmHistoryModel bean, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        UDate begDate = new UDate();
        begDate.setDate(date.getYear(), date.getMonth(), date.getIntDay());
        String begDateStr = begDate.getDateStringForSql();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   BEG_DATE = cast('" + begDateStr + "' as DATE)");

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
            sql.addSql("   CMN_BELONGM_HISTORY");
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
            sql.addSql("   CMN_BELONGM_HISTORY");
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
     * @param date 日付
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteUserBelongGroup(int uSid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        UDate begDate = new UDate();
        begDate.setDate(date.getYear(), date.getMonth(), date.getIntDay());
        String begDateStr = begDate.getDateStringForSql();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM_HISTORY");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   BEG_DATE = cast('" + begDateStr + "' as DATE)");

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
     * <p>Create CMN_BELONGM_HISTORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnBelongmHistoryModel
     * @throws SQLException SQL実行例外
     */
    private CmnBelongmHistoryModel __getCmnBelongmFromRs(ResultSet rs) throws SQLException {
        CmnBelongmHistoryModel bean = new CmnBelongmHistoryModel();
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setBegDate(UDate.getInstanceTimestamp(rs.getTimestamp("BEG_DATE")));
        bean.setBegAuid(rs.getInt("BEG_AUID"));
        bean.setBegAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BEG_ADATE")));
        bean.setBegEuid(rs.getInt("BEG_EUID"));
        bean.setBegEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BEG_EDATE")));
        bean.setBegDefgrp(rs.getInt("BEG_DEFGRP"));
        bean.setBegGrpkbn(rs.getInt("BEG_GRPKBN"));
        return bean;
    }
}
