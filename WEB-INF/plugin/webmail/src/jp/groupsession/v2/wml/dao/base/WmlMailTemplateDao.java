package jp.groupsession.v2.wml.dao.base;

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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.model.base.WmlMailTemplateModel;

/**
 * <p>WML_MAIL_TEMPLATE Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WmlMailTemplateDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlMailTemplateDao.class);

    /**
     * <p>Default Constructor
     */
    public WmlMailTemplateDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WmlMailTemplateDao(Connection con) {
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
            sql.addSql("drop table WML_MAIL_TEMPLATE");

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
            sql.addSql(" create table WML_MAIL_TEMPLATE (");
            sql.addSql("   WTP_SID NUMBER(10,0) not null,");
            sql.addSql("   WTP_TYPE NUMBER(10,0) not null,");
            sql.addSql("   WAC_SID NUMBER(10,0),");
            sql.addSql("   WTP_NAME varchar(100) not null,");
            sql.addSql("   WTP_TITLE varchar(1000),");
            sql.addSql("   WTP_BODY Date,");
            sql.addSql("   WTP_ORDER NUMBER(10,0) not null,");
            sql.addSql("   WTP_AUID NUMBER(10,0) not null,");
            sql.addSql("   WTP_ADATE varchar(23) not null,");
            sql.addSql("   WTP_EUID NUMBER(10,0) not null,");
            sql.addSql("   WTP_EDATE varchar(23) not null,");
            sql.addSql("   primary key (WTP_SID)");
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
     * <p>Insert WML_MAIL_TEMPLATE Data Bindding JavaBean
     * @param bean WML_MAIL_TEMPLATE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WmlMailTemplateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_MAIL_TEMPLATE(");
            sql.addSql("   WTP_SID,");
            sql.addSql("   WTP_TYPE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WTP_NAME,");
            sql.addSql("   WTP_TITLE,");
            sql.addSql("   WTP_BODY,");
            sql.addSql("   WTP_ORDER,");
            sql.addSql("   WTP_AUID,");
            sql.addSql("   WTP_ADATE,");
            sql.addSql("   WTP_EUID,");
            sql.addSql("   WTP_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWtpSid());
            sql.addIntValue(bean.getWtpType());
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWtpName());
            sql.addStrValue(bean.getWtpTitle());
            sql.addStrValue(bean.getWtpBody());
            sql.addIntValue(bean.getWtpOrder());
            sql.addIntValue(bean.getWtpAuid());
            sql.addDateValue(bean.getWtpAdate());
            sql.addIntValue(bean.getWtpEuid());
            sql.addDateValue(bean.getWtpEdate());
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
     * <p>Update WML_MAIL_TEMPLATE Data Bindding JavaBean
     * @param bean WML_MAIL_TEMPLATE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(WmlMailTemplateModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" set ");
            sql.addSql("   WTP_TYPE=?,");
            sql.addSql("   WAC_SID=?,");
            sql.addSql("   WTP_NAME=?,");
            sql.addSql("   WTP_TITLE=?,");
            sql.addSql("   WTP_BODY=?,");
            sql.addSql("   WTP_EUID=?,");
            sql.addSql("   WTP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getWtpType());
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWtpName());
            sql.addStrValue(bean.getWtpTitle());
            sql.addStrValue(bean.getWtpBody());
            sql.addIntValue(bean.getWtpEuid());
            sql.addDateValue(bean.getWtpEdate());
            //where
            sql.addIntValue(bean.getWtpSid());

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
     * <br>[機  能] 並び順を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtpSid メールテンプレートSID
     * @param order 並び順
     * @param userSid 更新ユーザSID
     * @param date 更新日時
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateOrder(int wtpSid, int order, int userSid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" set ");
            sql.addSql("   WTP_ORDER=?,");
            sql.addSql("   WTP_EUID=?,");
            sql.addSql("   WTP_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(order);
            sql.addIntValue(userSid);
            sql.addDateValue(date);
            //where
            sql.addIntValue(wtpSid);

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
     * <p>Select WML_MAIL_TEMPLATE All Data
     * @return List in WML_MAIL_TEMPLATEModel
     * @throws SQLException SQL実行例外
     */
    public List<WmlMailTemplateModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailTemplateModel> ret = new ArrayList<WmlMailTemplateModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WTP_SID,");
            sql.addSql("   WTP_TYPE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WTP_NAME,");
            sql.addSql("   WTP_TITLE,");
            sql.addSql("   WTP_BODY,");
            sql.addSql("   WTP_ORDER,");
            sql.addSql("   WTP_AUID,");
            sql.addSql("   WTP_ADATE,");
            sql.addSql("   WTP_EUID,");
            sql.addSql("   WTP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_TEMPLATE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailTemplateFromRs(rs));
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
     * <br>[機  能] メールテンプレート情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtpType 種別
     * @param wacSid アカウントSID
     * @return メールテンプレート情報
     * @throws SQLException SQL実行時例外
     */
    public List<WmlMailTemplateModel> getTemplateList(int wtpType, int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WmlMailTemplateModel> ret = new ArrayList<WmlMailTemplateModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WTP_SID,");
            sql.addSql("   WTP_TYPE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WTP_NAME,");
            sql.addSql("   WTP_TITLE,");
            sql.addSql("   WTP_BODY,");
            sql.addSql("   WTP_ORDER,");
            sql.addSql("   WTP_AUID,");
            sql.addSql("   WTP_ADATE,");
            sql.addSql("   WTP_EUID,");
            sql.addSql("   WTP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   WTP_TYPE = ?");
            sql.addIntValue(wtpType);

            if (wtpType == GSConstWebmail.WTP_TYPE_ACCOUNT) {
                sql.addSql(" and ");
                sql.addSql("   WAC_SID = ?");
                sql.addIntValue(wacSid);
            }

            sql.addSql(" order by");
            sql.addSql("   WTP_ORDER asc");
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailTemplateFromRs(rs));
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
     * <br>[機  能] 並び順を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtpSid メールテンプレートSID
     * @return 並び順
     * @throws SQLException SQL実行時例外
     */
    public int getOrder(int wtpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int order = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WTP_ORDER");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID = ?");
            sql.addIntValue(wtpSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                order = rs.getInt("WTP_ORDER");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return order;
    }

    /**
     * <br>[機  能] 並び順を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtpType テンプレート区分
     * @param wacSid アカウントSID
     * @return テンプレートリスト
     * @throws SQLException SQL実行時例外
     */
    public List<WmlMailTemplateModel> getAllOrder(int wtpType, int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlMailTemplateModel> tmplateList = new ArrayList<WmlMailTemplateModel>();
        WmlMailTemplateModel tempMdl = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WTP_SID,");
            sql.addSql("   WTP_ORDER");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   WTP_TYPE = ?");
            sql.addSql(" and ");
            sql.addSql("   WAC_SID = ?");
            sql.addSql(" order by WTP_ORDER ASC");

            sql.addIntValue(wtpType);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                tempMdl = new WmlMailTemplateModel();
                tempMdl.setWtpSid(rs.getInt("WTP_SID"));
                tempMdl.setWtpOrder(rs.getInt("WTP_ORDER"));
                tmplateList.add(tempMdl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return tmplateList;
    }

    /**
     * <br>[機  能] メールテンプレート情報の並び順を取得する
     * <br>[解  説] 指定した条件内の最大値を取得する
     * <br>[備  考]
     * @param wtpType 種別
     * @param wacSid アカウントSID
     * @return 並び順
     * @throws SQLException SQL実行時例外
     */
    public int getMaxOrder(int wtpType, int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int order = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(WTP_ORDER) as MAX_ORDER");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   WTP_TYPE = ?");
            sql.addIntValue(wtpType);

            if (wtpType == GSConstWebmail.WTP_TYPE_ACCOUNT) {
                sql.addSql(" and ");
                sql.addSql("   WAC_SID = ?");
                sql.addIntValue(wacSid);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                order = rs.getInt("MAX_ORDER");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return order;
    }

    /**
     * <br>[機  能] 並び順からメールテンプレート情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtpType 種別
     * @param wacSid アカウントSID
     * @param order 並び順
     * @return メールテンプレート情報
     * @throws SQLException SQL実行時例外
     */
    public WmlMailTemplateModel selectByOrder(int wtpType, int wacSid, int order)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlMailTemplateModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WTP_SID,");
            sql.addSql("   WTP_TYPE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WTP_NAME,");
            sql.addSql("   WTP_TITLE,");
            sql.addSql("   WTP_BODY,");
            sql.addSql("   WTP_ORDER,");
            sql.addSql("   WTP_AUID,");
            sql.addSql("   WTP_ADATE,");
            sql.addSql("   WTP_EUID,");
            sql.addSql("   WTP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   WTP_TYPE = ?");
            sql.addSql(" and ");
            sql.addSql("   WTP_ORDER = ?");
            sql.addIntValue(wtpType);
            sql.addIntValue(order);

            if (wtpType == GSConstWebmail.WTP_TYPE_ACCOUNT) {
                sql.addSql(" and ");
                sql.addSql("   WAC_SID = ?");
                sql.addIntValue(wacSid);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlMailTemplateFromRs(rs);
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
     * <br>[機  能] 指定したアカウントが使用可能なメールテンプレート情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return メールテンプレート情報
     * @throws SQLException SQL実行時例外
     */
    public List<WmlMailTemplateModel> getMailTemplateList(int wacSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<WmlMailTemplateModel> ret = new ArrayList<WmlMailTemplateModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WTP_SID,");
            sql.addSql("   WTP_TYPE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WTP_NAME,");
            sql.addSql("   WTP_TITLE,");
            sql.addSql("   WTP_BODY,");
            sql.addSql("   WTP_ORDER,");
            sql.addSql("   WTP_AUID,");
            sql.addSql("   WTP_ADATE,");
            sql.addSql("   WTP_EUID,");
            sql.addSql("   WTP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   WTP_TYPE = ?");
            sql.addSql(" or ");
            sql.addSql("   (");
            sql.addSql("     WTP_TYPE = ?");
            sql.addSql("   and ");
            sql.addSql("     WAC_SID = ?");
            sql.addSql("   )");
            sql.addSql(" order by");
            sql.addSql("   WTP_TYPE desc,");
            sql.addSql("   WTP_ORDER");
            sql.addIntValue(GSConstWebmail.WTP_TYPE_COMMON);
            sql.addIntValue(GSConstWebmail.WTP_TYPE_ACCOUNT);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWmlMailTemplateFromRs(rs));
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
     * <p>Select WML_MAIL_TEMPLATE
     * @param wtpSid WTP_SID
     * @return WML_MAIL_TEMPLATEModel
     * @throws SQLException SQL実行例外
     */
    public WmlMailTemplateModel select(int wtpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        WmlMailTemplateModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WTP_SID,");
            sql.addSql("   WTP_TYPE,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WTP_NAME,");
            sql.addSql("   WTP_TITLE,");
            sql.addSql("   WTP_BODY,");
            sql.addSql("   WTP_ORDER,");
            sql.addSql("   WTP_AUID,");
            sql.addSql("   WTP_ADATE,");
            sql.addSql("   WTP_EUID,");
            sql.addSql("   WTP_EDATE");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wtpSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getWmlMailTemplateFromRs(rs);
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
     * <br>[機  能] 指定したメールテンプレート情報のデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wtpSidList メールテンプレートSID
     * @return データサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalDataSize(List<Integer> wtpSidList) throws SQLException {

        if (wtpSidList == null || wtpSidList.isEmpty()) {
            return 0;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long dataSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(WTP_TITLE)) as TITLE_SIZE,");
            sql.addSql("   sum(octet_length(WTP_BODY)) as BODY_SIZE");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" where");
            sql.addSql("   WTP_SID in (");

            for (int idx = 0; idx < wtpSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(wtpSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataSize += rs.getLong("TITLE_SIZE");
                dataSize += rs.getLong("BODY_SIZE");
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
     * <p>Delete WML_MAIL_TEMPLATE
     * @param wtpSid WTP_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int wtpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_TEMPLATE");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wtpSid);

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
     * <p>Create WML_MAIL_TEMPLATE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WmlMailTemplateModel
     * @throws SQLException SQL実行例外
     */
    private WmlMailTemplateModel __getWmlMailTemplateFromRs(ResultSet rs) throws SQLException {
        WmlMailTemplateModel bean = new WmlMailTemplateModel();
        bean.setWtpSid(rs.getInt("WTP_SID"));
        bean.setWtpType(rs.getInt("WTP_TYPE"));
        bean.setWacSid(rs.getInt("WAC_SID"));
        bean.setWtpName(rs.getString("WTP_NAME"));
        bean.setWtpTitle(rs.getString("WTP_TITLE"));
        bean.setWtpBody(rs.getString("WTP_BODY"));
        bean.setWtpOrder(rs.getInt("WTP_ORDER"));
        bean.setWtpAuid(rs.getInt("WTP_AUID"));
        bean.setWtpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("WTP_ADATE")));
        bean.setWtpEuid(rs.getInt("WTP_EUID"));
        bean.setWtpEdate(UDate.getInstanceTimestamp(rs.getTimestamp("WTP_EDATE")));
        return bean;
    }
}
