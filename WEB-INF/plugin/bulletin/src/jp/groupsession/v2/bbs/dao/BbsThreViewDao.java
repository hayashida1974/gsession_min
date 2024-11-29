package jp.groupsession.v2.bbs.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.model.BbsThreViewModel;
import jp.groupsession.v2.bbs.model.BbsUserModel;

/**
 * <p>BBS_THRE_VIEW Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsThreViewDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsThreViewDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsThreViewDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsThreViewDao(Connection con) {
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
            sql.addSql("drop table BBS_THRE_VIEW");

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
            sql.addSql(" create table BBS_THRE_VIEW (");
            sql.addSql("   BTI_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   BFI_SID NUMBER(10,0) not null,");
            sql.addSql("   BIV_VIEW_KBN NUMBER(10,0) not null,");
            sql.addSql("   BIV_AUID NUMBER(10,0) not null,");
            sql.addSql("   BIV_ADATE varchar(26) not null,");
            sql.addSql("   BIV_EUID NUMBER(10,0) not null,");
            sql.addSql("   BIV_EDATE varchar(26) not null,");
            sql.addSql("   primary key (BTI_SID,USR_SID)");
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
     * <p>Insert BBS_THRE_VIEW Data Bindding JavaBean
     * @param bean BBS_THRE_VIEW Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsThreViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_THRE_VIEW(");
            sql.addSql("   BTI_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BIV_VIEW_KBN,");
            sql.addSql("   BIV_AUID,");
            sql.addSql("   BIV_ADATE,");
            sql.addSql("   BIV_EUID,");
            sql.addSql("   BIV_EDATE");
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
            sql.addIntValue(bean.getBtiSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getBfiSid());
            sql.addIntValue(bean.getBivViewKbn());
            sql.addIntValue(bean.getBivAuid());
            sql.addDateValue(bean.getBivAdate());
            sql.addIntValue(bean.getBivEuid());
            sql.addDateValue(bean.getBivEdate());
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
     * <p>Insert BBS_THRE_VIEW Data Bindding JavaBean
     * @param beanList BBS_THRE_VIEW DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<BbsThreViewModel> beanList) throws SQLException {

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
            sql.addSql(" BBS_THRE_VIEW(");
            sql.addSql("   BTI_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BIV_VIEW_KBN,");
            sql.addSql("   BIV_AUID,");
            sql.addSql("   BIV_ADATE,");
            sql.addSql("   BIV_EUID,");
            sql.addSql("   BIV_EDATE");
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

            for (BbsThreViewModel bean : beanList) {
                sql.addIntValue(bean.getBtiSid());
                sql.addIntValue(bean.getUsrSid());
                sql.addIntValue(bean.getBfiSid());
                sql.addIntValue(bean.getBivViewKbn());
                sql.addIntValue(bean.getBivAuid());
                sql.addDateValue(bean.getBivAdate());
                sql.addIntValue(bean.getBivEuid());
                sql.addDateValue(bean.getBivEdate());
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
     * <br>[機  能] スレッド閲覧情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @param userSid ユーザSID
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getThreViewCount(int btiSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);
            sql.addIntValue(userSid);

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
     * <br>[機  能] 指定したフォーラム、ユーザの閲覧済みスレッド件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getReadedThreadCount(int bfiSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bfiSid);
            sql.addIntValue(userSid);

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
     * <p>Update BBS_THRE_VIEW Data Bindding JavaBean
     * @param bean BBS_THRE_VIEW Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(BbsThreViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" set ");
            sql.addSql("   BIV_VIEW_KBN=?,");
            sql.addSql("   BIV_EUID=?,");
            sql.addSql("   BIV_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBivViewKbn());
            sql.addIntValue(bean.getBivEuid());
            sql.addDateValue(bean.getBivEdate());
            //where
            sql.addIntValue(bean.getBtiSid());
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
     * <br>[機  能] スレッド閲覧情報の閲覧状態区分を変更する
     * <br>[解  説] 全てのユーザに対して更新を行う
     * <br>[備  考]
     * @param bean 更新情報
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAllUserViewKbn(BbsThreViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" set ");
            sql.addSql("   BIV_VIEW_KBN=?,");
            sql.addSql("   BIV_EUID=?,");
            sql.addSql("   BIV_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBivViewKbn());
            sql.addIntValue(bean.getBivEuid());
            sql.addDateValue(bean.getBivEdate());
            //where
            sql.addIntValue(bean.getBtiSid());

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
     * <p>Select BBS_THRE_VIEW
     * @return BBS_THRE_VIEWModel in List
     * @throws SQLException SQL実行例外
     */
    public List<BbsThreViewModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsThreViewModel> ret = new ArrayList<BbsThreViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BIV_VIEW_KBN,");
            sql.addSql("   BIV_AUID,");
            sql.addSql("   BIV_ADATE,");
            sql.addSql("   BIV_EUID,");
            sql.addSql("   BIV_EDATE");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_VIEW");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsThreViewFromRs(rs));
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
     * <p>Select BBS_THRE_VIEW
     * @param userSid ユーザSID
     * @return BBS_THRE_VIEWModel in List
     * @throws SQLException SQL実行例外
     */
    public List<BbsThreViewModel> select(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BbsThreViewModel> ret = new ArrayList<BbsThreViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BIV_VIEW_KBN,");
            sql.addSql("   BIV_AUID,");
            sql.addSql("   BIV_ADATE,");
            sql.addSql("   BIV_EUID,");
            sql.addSql("   BIV_EDATE");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" where");
            sql.addSql("   USR_SID=?");

            sql.addIntValue(userSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsThreViewFromRs(rs));
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
     * <p>Select BBS_THRE_VIEW
     * @param btiSid BTI_SID
     * @return BBS_THRE_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public List<BbsThreViewModel> getThreadView(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List <BbsThreViewModel> ret = new ArrayList<BbsThreViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BIV_VIEW_KBN,");
            sql.addSql("   BIV_AUID,");
            sql.addSql("   BIV_ADATE,");
            sql.addSql("   BIV_EUID,");
            sql.addSql("   BIV_EDATE");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsThreViewFromRs(rs));
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
     * <p>Select BBS_THRE_VIEW
     * @param bean BBS_THRE_VIEW Model
     * @return BBS_THRE_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public BbsThreViewModel select(BbsThreViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsThreViewModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BIV_VIEW_KBN,");
            sql.addSql("   BIV_AUID,");
            sql.addSql("   BIV_ADATE,");
            sql.addSql("   BIV_EUID,");
            sql.addSql("   BIV_EDATE");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBtiSid());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsThreViewFromRs(rs);
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
     * <p>Delete BBS_THRE_VIEW
     * @param btiSid スレッドSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);

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
     * <p>Delete BBS_THRE_VIEW
     * @param delList 削除する掲示板リスト
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(List<BbsThreViewModel> delList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" where ");

            int i = 0;
            for (BbsThreViewModel model : delList) {
                if (i > 0) {
                    sql.addSql("  or");
                }
                sql.addSql(" (");
                sql.addSql(" BTI_SID=?");
                sql.addSql(" and");
                sql.addSql(" USR_SID=?");
                sql.addSql(" )");

                sql.addIntValue(model.getBtiSid());
                sql.addIntValue(model.getUsrSid());
                i++;
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
     * <br>[機  能] 指定したフォーラム、ユーザに関するスレッド閲覧状態を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @param auid 登録/更新ユーザSID
     * @param adate 登録/更新日付
     * @throws SQLException SQL実行例外
     */
    public void insert(int bfiSid, int userSid, int auid, UDate adate) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_THRE_VIEW(");
            sql.addSql("   BTI_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BIV_VIEW_KBN,");
            sql.addSql("   BIV_AUID,");
            sql.addSql("   BIV_ADATE,");
            sql.addSql("   BIV_EUID,");
            sql.addSql("   BIV_EDATE");
            sql.addSql(" )");
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   ?,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" where");
            sql.addSql("   BFI_SID = ?");

            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("      BTI_LIMIT = ?");
            sql.addSql("    or");
            sql.addSql("      (");
            sql.addSql("         BTI_LIMIT = ?");
            sql.addSql("       and");
            sql.addSql("         BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("      )");
            sql.addSql("   )");

            sql.addIntValue(userSid);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_YES);
            sql.addIntValue(auid);
            sql.addDateValue(adate);
            sql.addIntValue(auid);
            sql.addDateValue(adate);
            sql.addIntValue(bfiSid);

            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            UDate limitFrDate = UDate.getInstanceStr(adate.getDateString());
            sql.addDateValue(limitFrDate);

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
     * <br>[機  能] 指定したフォーラム、ユーザのスレッド閲覧状態を全て「既読」として更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @param euid 更新ユーザSID
     * @param edate 更新日付
     * @throws SQLException SQL実行例外
     */
    public void updateAllReadThreadInForum(int bfiSid, int userSid, int euid, UDate edate)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" set");
            sql.addSql("   BIV_VIEW_KBN=?,");
            sql.addSql("   BIV_EUID=?,");
            sql.addSql("   BIV_EDATE=?");
            sql.addSql(" where");
            sql.addSql("   BBS_THRE_VIEW.BFI_SID = ?");
            sql.addSql(" and");
            sql.addSql("   BBS_THRE_VIEW.USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   BBS_THRE_VIEW.BTI_SID ");
            sql.addSql("     in ( ");
            sql.addSql("           select");
            sql.addSql("             BBS_THRE_INF.BTI_SID");
            sql.addSql("           from ");
            sql.addSql("             BBS_THRE_INF");
            sql.addSql("           where");
            sql.addSql("             BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("           or");
            sql.addSql("             (BBS_THRE_INF.BTI_LIMIT = ?");
            sql.addSql("               and");
            sql.addSql("              BBS_THRE_INF.BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("            )");
            sql.addSql("         )");

            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_YES);
            sql.addIntValue(euid);
            sql.addDateValue(edate);
            sql.addIntValue(bfiSid);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            UDate limitFrDate = UDate.getInstanceStr(edate.getDateString());
            sql.addDateValue(limitFrDate);

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
     * <br>[機  能] 指定したフォーラム、ユーザのスレッド閲覧状態を全て「既読」として登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @param auid 登録/更新ユーザSID
     * @param adate 登録/更新日付
     * @throws SQLException SQL実行例外
     */
    public void insertAllReadThreadInForum(int bfiSid, int userSid, int auid, UDate adate)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_THRE_VIEW(");
            sql.addSql("   BTI_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BIV_VIEW_KBN,");
            sql.addSql("   BIV_AUID,");
            sql.addSql("   BIV_ADATE,");
            sql.addSql("   BIV_EUID,");
            sql.addSql("   BIV_EDATE");
            sql.addSql(" )");
            sql.addSql(" select");
            sql.addSql("   BTI_SID,");
            sql.addSql("   ?,");
            sql.addSql("   BFI_SID,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" where");
            sql.addSql("   BFI_SID = ?");

            sql.addSql(" and");
            sql.addSql("   BTI_SID not in (");
            sql.addSql("     select BTI_SID from BBS_THRE_VIEW");
            sql.addSql("     where BBS_THRE_VIEW.BFI_SID = ?");
            sql.addSql("     and BBS_THRE_VIEW.USR_SID = ?");
            sql.addSql("   )");

            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("      BTI_LIMIT = ?");
            sql.addSql("    or");
            sql.addSql("      (");
            sql.addSql("         BTI_LIMIT = ?");
            sql.addSql("       and");
            sql.addSql("         BTI_LIMIT_FR_DATE <= ?");
            sql.addSql("      )");
            sql.addSql("   )");

            sql.addIntValue(userSid);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_YES);
            sql.addIntValue(auid);
            sql.addDateValue(adate);
            sql.addIntValue(auid);
            sql.addDateValue(adate);
            sql.addIntValue(bfiSid);

            sql.addIntValue(bfiSid);
            sql.addIntValue(userSid);

            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_NO);
            sql.addIntValue(GSConstBulletin.THREAD_LIMIT_YES);
            UDate limitFrDate = UDate.getInstanceStr(adate.getDateString());
            sql.addDateValue(limitFrDate);

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
     * <p>Create BBS_THRE_VIEW Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsThreViewModel
     * @throws SQLException SQL実行例外
     */
    private BbsThreViewModel __getBbsThreViewFromRs(ResultSet rs) throws SQLException {
        BbsThreViewModel bean = new BbsThreViewModel();
        bean.setBtiSid(rs.getInt("BTI_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setBfiSid(rs.getInt("BFI_SID"));
        bean.setBivViewKbn(rs.getInt("BIV_VIEW_KBN"));
        bean.setBivAuid(rs.getInt("BIV_AUID"));
        bean.setBivAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BIV_ADATE")));
        bean.setBivEuid(rs.getInt("BIV_EUID"));
        bean.setBivEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BIV_EDATE")));
        return bean;
    }

    /**
     * <br>[機  能] スレッド閲覧情報を削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @param bean BBS_USER Model
     * @return count delete
     * @throws SQLException SQL実行例外
     */
    public int delete(BbsUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定したフォーラム、ユーザのスレッド閲覧状態を未読に戻す
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param userSid ユーザSID
     * @param btiSid スレッドSID
     * @param euid 更新ユーザSID
     * @param edate 更新日付
     * @throws SQLException SQL実行例外
     */
    public void updateUnReadThreadInForum(
            int bfiSid,
            String[] btiSid,
            int userSid,
            int euid,
            UDate edate)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" set");
            sql.addSql("   BIV_VIEW_KBN=?,");
            sql.addSql("   BIV_EUID=?,");
            sql.addSql("   BIV_EDATE=?");
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_UNREAD);
            sql.addIntValue(euid);
            sql.addDateValue(edate);
            sql.addSql(" where");
            sql.addSql("   BBS_THRE_VIEW.BFI_SID = ?");
            sql.addSql(" and");
            sql.addSql("   BBS_THRE_VIEW.USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   BBS_THRE_VIEW.BIV_VIEW_KBN = ?");
            sql.addIntValue(bfiSid);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstBulletin.BBS_THRE_VIEW_YES);
            sql.addSql(" and");
            sql.addSql("   BBS_THRE_VIEW.BTI_SID ");
            sql.addSql("     in ( ");
            for (int i = 0; i < btiSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(btiSid[i], 0));

                if (i < btiSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("         )");
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
     * <br>[機  能] スレッドの所属フォーラムを更新する。
     * <br>[解  説] 全てのユーザに対して更新を行う。
     * <br>[備  考]
     * @param bfiSid 移動先フォーラムSID
     * @param threadSidList スレッドSIDリスト
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateBelongForum(int bfiSid, String[] threadSidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" set ");
            sql.addSql("   BFI_SID=?");
            sql.addIntValue(bfiSid);
            sql.addSql(" where ");
            sql.addSql("   BTI_SID in (");
            boolean sepFlg = false;
            for (String sid : threadSidList) {
                if (sepFlg) {
                    sql.addSql("    ,");
                } else {
                    sepFlg = true;
                }
                sql.addSql("    ?");
                sql.addIntValue(Integer.parseInt(sid));
            }
            sql.addSql("    )");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] フォーラムに所属していないユーザの閲覧情報を削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @param threSid スレッドSid一覧
     * @param usrList userSid一覧
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(Integer[] threSid, List<Integer> usrList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        if (threSid == null || threSid.length == 0
                || usrList == null || usrList.size() == 0) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID in (");
            for (int i = 0; i < threSid.length; i++) {
                sql.addSql("     ?");
                sql.addIntValue(threSid[i]);

                if (i < threSid.length - 1) {
                    sql.addSql("   , ");
                }
            }
            sql.addSql("         )");
            sql.addSql(" and");
            sql.addSql("   USR_SID not in (");
            for (int i = 0; i < usrList.size(); i++) {
                sql.addSql("     ?");
                sql.addIntValue(usrList.get(i));

                if (i < usrList.size() - 1) {
                    sql.addSql("   , ");
                }
            }
            sql.addSql("         )");

            pstmt = con.prepareStatement(sql.toSqlString());
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
}
