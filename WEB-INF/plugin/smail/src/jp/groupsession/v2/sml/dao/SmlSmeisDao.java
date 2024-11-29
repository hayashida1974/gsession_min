package jp.groupsession.v2.sml.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.model.SmlSmeisModel;

/**
 * <p>SML_SMEIS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmlSmeisDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlSmeisDao.class);

    /**
     * <p>Default Constructor
     */
    public SmlSmeisDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SmlSmeisDao(Connection con) {
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
            sql.addSql("drop table SML_SMEIS");

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
            sql.addSql(" create table SML_SMEIS (");
            sql.addSql("   SAC_SID NUMBER(4,0) not null,");
            sql.addSql("   SMS_SID NUMBER(4,0) not null,");
            sql.addSql("   SMS_SDATE varchar(8),");
            sql.addSql("   SMS_TITLE varchar(50),");
            sql.addSql("   SMS_MARK NUMBER(4,0),");
            sql.addSql("   SMS_BODY text,");
            sql.addSql("   SMS_BODY_PLAIN text,");
            sql.addSql("   SMS_SIZE bigint not null,");
            sql.addSql("   SMS_TYPE NUMBER(4,0) not null,");
            sql.addSql("   SMS_AUID NUMBER(4,0) not null,");
            sql.addSql("   SMS_ADATE varchar(8) not null,");
            sql.addSql("   SMS_EUID NUMBER(4,0) not null,");
            sql.addSql("   SMS_EDATE varchar(8) not null,");
            sql.addSql("   primary key (SMS_SID)");
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
     * <p>Insert SML_SMEIS Data Bindding JavaBean
     * @param bean SML_SMEIS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SmlSmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SML_SMEIS(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMS_SID,");
            sql.addSql("   SMS_SDATE,");
            sql.addSql("   SMS_TITLE,");
            sql.addSql("   SMS_MARK,");
            sql.addSql("   SMS_BODY,");
            sql.addSql("   SMS_BODY_PLAIN,");
            sql.addSql("   SMS_JKBN,");
            sql.addSql("   SMS_SIZE,");
            sql.addSql("   SMS_TYPE,");
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
            sql.addIntValue(bean.getSmsSid());
            sql.addDateValue(bean.getSmsSdate());
            sql.addStrValue(bean.getSmsTitle());
            sql.addIntValue(bean.getSmsMark());
            sql.addStrValue(bean.getSmsBody());
            sql.addStrValue(NullDefault.getString(bean.getSmsBodyPlain(), ""));
            sql.addIntValue(bean.getSmsJkbn());
            sql.addLongValue(bean.getSmsSize());
            sql.addIntValue(bean.getSmsType());
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
     * <p>Insert SML_SMEIS Data Bindding JavaBean
     * @param beanList SML_SMEIS DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<SmlSmeisModel> beanList) throws SQLException {

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
            sql.addSql(" SML_SMEIS(");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMS_SID,");
            sql.addSql("   SMS_SDATE,");
            sql.addSql("   SMS_TITLE,");
            sql.addSql("   SMS_MARK,");
            sql.addSql("   SMS_BODY,");
            sql.addSql("   SMS_BODY_PLAIN,");
            sql.addSql("   SMS_JKBN,");
            sql.addSql("   SMS_SIZE,");
            sql.addSql("   SMS_TYPE,");
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
            for (SmlSmeisModel bean : beanList) {
                sql.addIntValue(bean.getSacSid());
                sql.addIntValue(bean.getSmsSid());
                sql.addDateValue(bean.getSmsSdate());
                sql.addStrValue(bean.getSmsTitle());
                sql.addIntValue(bean.getSmsMark());
                sql.addStrValue(bean.getSmsBody());
                sql.addStrValue(NullDefault.getString(bean.getSmsBodyPlain(), ""));
                sql.addIntValue(bean.getSmsJkbn());
                sql.addLongValue(bean.getSmsSize());
                sql.addIntValue(bean.getSmsType());
                sql.addIntValue(bean.getSmsAuid());
                sql.addDateValue(bean.getSmsAdate());
                sql.addIntValue(bean.getSmsEuid());
                sql.addDateValue(bean.getSmsEdate());
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
     * <p>Update SML_SMEIS Data Bindding JavaBean
     * @param bean SML_SMEIS Data Bindding JavaBean
     * @return count 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SmlSmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" set ");
            sql.addSql("   SAC_SID=?,");
            sql.addSql("   SMS_SDATE=?,");
            sql.addSql("   SMS_TITLE=?,");
            sql.addSql("   SMS_MARK=?,");
            sql.addSql("   SMS_BODY=?,");
            sql.addSql("   SMS_JKBN=?,");
            sql.addSql("   SMS_SIZE=?,");
            sql.addSql("   SMS_TYPE=?,");
            sql.addSql("   SMS_AUID=?,");
            sql.addSql("   SMS_ADATE=?,");
            sql.addSql("   SMS_EUID=?,");
            sql.addSql("   SMS_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSacSid());
            sql.addDateValue(bean.getSmsSdate());
            sql.addStrValue(bean.getSmsTitle());
            sql.addIntValue(bean.getSmsMark());
            sql.addStrValue(bean.getSmsBody());
            sql.addIntValue(bean.getSmsJkbn());
            sql.addLongValue(bean.getSmsSize());
            sql.addIntValue(bean.getSmsType());
            sql.addIntValue(bean.getSmsAuid());
            sql.addDateValue(bean.getSmsAdate());
            sql.addIntValue(bean.getSmsEuid());
            sql.addDateValue(bean.getSmsEdate());
            //where
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
     * <p>Select SML_SMEIS All Data
     * @return List in SML_SMEISModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlSmeisModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlSmeisModel> ret = new ArrayList<SmlSmeisModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMS_SID,");
            sql.addSql("   SMS_SDATE,");
            sql.addSql("   SMS_TITLE,");
            sql.addSql("   SMS_MARK,");
            sql.addSql("   SMS_BODY,");
            sql.addSql("   SMS_BODY_PLAIN,");
            sql.addSql("   SMS_JKBN,");
            sql.addSql("   SMS_SIZE,");
            sql.addSql("   SMS_TYPE,");
            sql.addSql("   SMS_AUID,");
            sql.addSql("   SMS_ADATE,");
            sql.addSql("   SMS_EUID,");
            sql.addSql("   SMS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_SMEIS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlSmeisFromRs(rs));
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
     * <p>Select SML_SMEIS All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in SML_SMEISModel
     * @throws SQLException SQL実行例外
     */
    public List<SmlSmeisModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SmlSmeisModel> ret = new ArrayList<SmlSmeisModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMS_SID,");
            sql.addSql("   SMS_SDATE,");
            sql.addSql("   SMS_TITLE,");
            sql.addSql("   SMS_MARK,");
            sql.addSql("   SMS_BODY,");
            sql.addSql("   SMS_BODY_PLAIN,");
            sql.addSql("   SMS_JKBN,");
            sql.addSql("   SMS_SIZE,");
            sql.addSql("   SMS_TYPE,");
            sql.addSql("   SMS_AUID,");
            sql.addSql("   SMS_ADATE,");
            sql.addSql("   SMS_EUID,");
            sql.addSql("   SMS_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" order by ");
            sql.addSql("   SAC_SID asc,");
            sql.addSql("   SMS_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSmlSmeisFromRs(rs));
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
     * <p>count SML_SMEIS All Data
     * @return List in SML_SMEISModel
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
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   SML_SMEIS");

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
     * <p>Select SML_SMEIS
     * @param bean SML_SMEIS Model
     * @return SML_SMEISModel
     * @throws SQLException SQL実行例外
     */
    public SmlSmeisModel select(SmlSmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SmlSmeisModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMS_SID,");
            sql.addSql("   SMS_SDATE,");
            sql.addSql("   SMS_TITLE,");
            sql.addSql("   SMS_MARK,");
            sql.addSql("   SMS_BODY,");
            sql.addSql("   SMS_BODY_PLAIN,");
            sql.addSql("   SMS_JKBN,");
            sql.addSql("   SMS_SIZE,");
            sql.addSql("   SMS_TYPE,");
            sql.addSql("   SMS_AUID,");
            sql.addSql("   SMS_ADATE,");
            sql.addSql("   SMS_EUID,");
            sql.addSql("   SMS_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where ");
            sql.addSql("   SMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmsSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSmlSmeisFromRs(rs);
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
     * <p>削除するメールSIDの件数を取得する
     * @param delMdl SmlAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @return 削除メールSIDの件数
     * @throws SQLException SQL実行例外
     */
    public int getDeleteMailCount(SmlAdelModel delMdl, int kbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int smailCount = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SMS_SID) as CNT");

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
     * <p>削除するメールSIDとメール容量を取得する。
     * @param delMdl SmlAdelModel
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @param limit 取得件数
     * @param offset 取得開始行数
     * @return 削除メールSIDリスト
     * @throws SQLException SQL実行例外
     */
    public Map<SmlSmeisModel, Long> getDeleteMail(
            SmlAdelModel delMdl, int kbn, int limit, int offset) throws SQLException {

        Map<SmlSmeisModel, Long> ret = new HashMap<SmlSmeisModel, Long>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMS_SID,");
            sql.addSql("   SMS_SIZE");

            //検索条件を設定
            __setDeleteMailWhere(sql, delMdl, kbn);

            //並び順、取得範囲を設定
            sql.addSql(" order by SMS_SID");
            sql.setPagingValue(offset, limit);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            SmlSmeisModel bean = null;
            while (rs.next()) {
                bean = new SmlSmeisModel();
                bean.setSacSid(rs.getInt("SAC_SID"));
                bean.setSmsSid(rs.getInt("SMS_SID"));
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
     * @param sidList 削除するメールSIDリスト
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @param limit 取得件数
     * @param offset 取得開始行数
     * @return 削除メールSIDリスト
     * @throws SQLException SQL実行例外
     */
    public Map<String, Long> getDeleteMail(
            Collection<Integer> sidList, int kbn, int limit, int offset) throws SQLException {

        log__.error("送信削除処理開始");
        Map<String, Long> ret = new HashMap<String, Long>();
        if (sidList == null || sidList.isEmpty()) {
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
            sb.append("   SMS_SID,");
            sb.append("   SMS_SIZE");
            sb.append(" from");
            sb.append("   SML_SMEIS");
            sb.append(" where");
            sb.append("   SMS_SID in (");
            final String sqlBaseTop = sb.toString();

            sb = new StringBuilder();
            sb.append("   )");
            sb.append(" order by SMS_SID");
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
                        ret.put(String.valueOf(rs.getInt("SMS_SID")), rs.getLong("SMS_SIZE"));
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

        int year = delMdl.getSadSdelYear();
        int month = delMdl.getSadSdelMonth();
        if (kbn == 2) {
            year = delMdl.getSadDdelYear();
            month = delMdl.getSadDdelMonth();
        }

        UDate delUd = new UDate();
        delUd.addYear((year * -1));
        delUd.addMonth((month * -1));
        delUd.setMaxHhMmSs();

        sql.addSql(" from");
        sql.addSql("   SML_SMEIS");
        sql.addSql(" where ");
        sql.addSql("   SMS_ADATE <= ?");
        sql.addSql(" and");
        sql.addSql("   SMS_JKBN = ?");
        sql.addDateValue(delUd);
        sql.addIntValue(jkbn);
        if (delMdl.getSacSid() > 0) {
            sql.addSql(" and");
            sql.addSql("   SAC_SID = ?");
            sql.addIntValue(delMdl.getSacSid());
        }
    }
    
    /**
     * <p>指定したアカウントのゴミ箱内の送信メールリストを取得する(本文を除く)
     * @param sacSid アカウントSID
     * @return ゴミ箱内の送信メールリスト
     * @throws SQLException SQL実行例外
     */
     public List<SmlSmeisModel> getGomibakoMailList(int sacSid) throws SQLException {
        
        List<SmlSmeisModel> ret = new ArrayList<SmlSmeisModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SMS_SID,");
            sql.addSql("   SMS_SDATE,");
            sql.addSql("   SMS_TITLE,");
            sql.addSql("   SMS_MARK,");
            sql.addSql("   SMS_JKBN,");
            sql.addSql("   SMS_SIZE,");
            sql.addSql("   SMS_TYPE,");
            sql.addSql("   SMS_AUID,");
            sql.addSql("   SMS_ADATE,");
            sql.addSql("   SMS_EUID,");
            sql.addSql("   SMS_EDATE");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMS_JKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_GOMIBAKO);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SmlSmeisModel bean = new SmlSmeisModel();
                bean.setSacSid(rs.getInt("SAC_SID"));
                bean.setSmsSid(rs.getInt("SMS_SID"));
                bean.setSmsSdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMS_SDATE")));
                bean.setSmsTitle(rs.getString("SMS_TITLE"));
                bean.setSmsMark(rs.getInt("SMS_MARK"));
                bean.setSmsJkbn(rs.getInt("SMS_JKBN"));
                bean.setSmsSize(rs.getLong("SMS_SIZE"));
                bean.setSmsType(rs.getInt("SMS_TYPE"));
                bean.setSmsAuid(rs.getInt("SMS_AUID"));
                bean.setSmsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMS_ADATE")));
                bean.setSmsEuid(rs.getInt("SMS_EUID"));
                bean.setSmsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMS_EDATE")));
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
     * <p>Delete SML_SMEIS
     * @param bean SML_SMEIS Model
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public  int delete(SmlSmeisModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
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
     * <p>指定されたメールSIDのメッセージを物理削除する
     * @param smsSid メールSID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int smsSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where ");
            sql.addSql("   SMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smsSid);

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
     * @param smsSidList メールSID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteSMail(List<String> smsSidList) throws SQLException {

        if (smsSidList == null || smsSidList.size() <= 0) {
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
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where ");
            if (smsSidList.size() == 1) {
                sql.addSql("   SMS_SID = ?");
                sql.addIntValue(Integer.parseInt(smsSidList.get(0)));
            } else {
                sql.addSql("   SMS_SID in (");
                for (int idx = 0; idx < smsSidList.size() - 1; idx++) {
                    sql.addSql("     ?,");
                    sql.addIntValue(Integer.parseInt(smsSidList.get(idx)));
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(smsSidList.get(smsSidList.size() - 1)));
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
     * <br>[機  能] 指定されたメールSIDの状態区分を変更する(送信)
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
    public void moveSmeis(int userSid, int sacSid, int jtkbn, UDate sysUd, String[] msgSid)
        throws SQLException {

        if (msgSid == null || msgSid.length < 1) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" set");
            sql.addSql("   SMS_JKBN = ?,");
            sql.addIntValue(jtkbn);
            sql.addSql("   SMS_EUID = ?,");
            sql.addIntValue(userSid);
            sql.addSql("   SMS_EDATE = ?");
            sql.addDateValue(sysUd);
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
     * <br>[機  能] ゴミ箱のデータを削除する(送信)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @param sacSid アカウントSID
     * @param sysUd システム日付
     * @throws SQLException SQL実行例外
     */
    public void moveGomibakoSmeis(int userSid, int sacSid, UDate sysUd)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" set");
            sql.addSql("   SMS_JKBN = ?,");
            sql.addSql("   SMS_EUID = ?,");
            sql.addSql("   SMS_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMS_JKBN = ?");

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
     * <br>[機  能] 指定されたメールSIDの状態区分を変更する(送信)
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
    public void moveSmeis(int userSid, int sacSid, int mailSid, int jtkbn, UDate sysUd)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" set");
            sql.addSql("   SMS_JKBN = ?,");
            sql.addSql("   SMS_EUID = ?,");
            sql.addSql("   SMS_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMS_SID = ?");

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
     * <br>[機  能] 指定されたメールSIDの状態区分を変更する(送信)
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
    public void moveSmeis(int userSid, int sacSid, int jtkbn, UDate sysUd, int mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" set");
            sql.addSql("   SMS_JKBN = ?,");
            sql.addSql("   SMS_EUID = ?,");
            sql.addSql("   SMS_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMS_SID = ?");

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
     * @param mailSid メールSID
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
            sql.addSql("   SML_SMEIS");
            sql.addSql(" set");
            sql.addSql("   SMS_JKBN = ?,");
            sql.addSql("   SMS_EUID = ?,");
            sql.addSql("   SMS_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SMS_SID = ?");

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
     * <br>[機  能] 指定されたメールSIDのメッセージを論理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid メールSID
     * @throws SQLException SQL実行例外
     */
    public void deleteMail(int mailSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" set");
            sql.addSql("   SMS_JKBN = ?");
            sql.addSql(" where ");
            sql.addSql("   SMS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_DELETE);
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
     * @param sacSid 削除対象のショートメールアカウントSID
     * @param eusid 更新者SID
     * @param sysUd システム日付
     * @throws SQLException SQL実行例外
     */
    public void deleteMsgRonri(int sacSid, int eusid, UDate sysUd)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" set");
            sql.addSql("   SMS_JKBN = ?,");
            sql.addSql("   SMS_EUID = ?,");
            sql.addSql("   SMS_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(eusid);
            sql.addDateValue(sysUd);
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
     * <br>[機  能] 指定されたメールSIDの送信メッセージを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sacSid アカウントSID
     * @param mailSid メールSID
     * @return ret 受信メッセージ詳細
     * @throws SQLException SQL実行例外
     */
    public ArrayList<SmailDetailModel> selectTargetSDetail(int sacSid,
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
            sql.addSql(GSConstSmail.TAB_DSP_MODE_SOSIN + " as mailKbn,");
            sql.addSql("   SML_SMEIS.SMS_TITLE as smsTitle");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where ");
            sql.addSql("   SML_SMEIS.SAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.addSql(" and");
            sql.addSql("   SML_SMEIS.SMS_SID in (");

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
            sql.addSql("   SML_SMEIS.SMS_JKBN = ?");
            sql.addIntValue(GSConstSmail.SML_JTKBN_TOROKU);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                SmailDetailModel mdl = new SmailDetailModel();
                mdl.setMailKbn(rs.getString("mailKbn"));
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
     * <br>[機  能] 指定されたメールが存在するか
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @param mailSid メールSID
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
            sql.addSql("   count(SML_SMEIS.SMS_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where ");
            sql.addSql("   SAC_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   SMS_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   SML_SMEIS.SMS_JKBN !=" + GSConstSmail.SML_JTKBN_DELETE);

            sql.addIntValue(sacSid);
            sql.addIntValue(mailSid);

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * @return ret 受信メッセージ詳細
     * @throws SQLException SQL実行例外
     */
    public boolean selectExsistMail(String[] mailSid)
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
            sql.addSql("   count(SML_SMEIS.SMS_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where ");
            sql.addSql("   SML_SMEIS.SMS_SID in (");

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
            sql.addSql("   SML_SMEIS.SMS_JKBN !=" + GSConstSmail.SML_JTKBN_DELETE);

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] 指定したショートメール情報(送信)のデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param smsSidList メールSID
     * @return データサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalDataSize(List<Integer> smsSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long dataSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(SMS_TITLE)) as TITLE_SIZE,");
            sql.addSql("   sum(octet_length(SMS_BODY)) as BODY_SIZE,");
            sql.addSql("   sum(octet_length(SMS_BODY_PLAIN)) as BODY_PLAIN_SIZE");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where");
            sql.addSql("   SMS_SID in (");

            for (int idx = 0; idx < smsSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(smsSidList.get(idx));
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
            sql.addSql("   SMS_SID");
            sql.addSql(" from");
            sql.addSql("   SML_SMEIS");
            sql.addSql(" where");
            sql.addSql("   SMS_SID in (");

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
                sidList.add(rs.getInt("SMS_SID"));
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
     * <br>[機  能] 送信タブ自動削除設定に従いデータを削除する
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
            sql.addSql("   SML_SMEIS");
            sql.addSql(" set");
            sql.addSql("   SMS_JKBN = ?,");
            sql.addSql("   SMS_EUID = ?,");
            sql.addSql("   SMS_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   SMS_ADATE <= ?");
            sql.addSql(" and");
            sql.addSql("   SMS_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            int year = delMdl.getSadSdelYear();
            int month = delMdl.getSadSdelMonth();
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
            sql.addSql("   SML_SMEIS");
            sql.addSql(" set");
            sql.addSql("   SMS_JKBN = ?,");
            sql.addSql("   SMS_EUID = ?,");
            sql.addSql("   SMS_EDATE = ?");
            sql.addSql(" where ");

            sql.addIntValue(GSConstSmail.SML_JTKBN_DELETE);
            sql.addIntValue(0);
            sql.addDateValue(now);

            int i = 0;
            for (String delSid : delList) {
                if (i > 0) {
                    sql.addSql("  or");
                }
                sql.addSql("   SMS_SID = ?");
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
     * <br>[機  能] 送信タブ自動削除設定に従いデータを削除する
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
                sql.addSql("   SML_SMEIS");
                sql.addSql(" set");
                sql.addSql("   SMS_JKBN = ?,");
                sql.addSql("   SMS_EUID = ?,");
                sql.addSql("   SMS_EDATE = ?");
                sql.addSql(" where ");
                sql.addSql("   SAC_SID = ?");
                sql.addSql(" and");
                sql.addSql("   SMS_ADATE <= ?");
                sql.addSql(" and");
                sql.addSql("   SMS_JKBN = ?");

                pstmt = con.prepareStatement(sql.toSqlString());

                int year = 0;
                int month = 0;
                if (kbn == 1) {
                    year = mdl.getSadSdelYear();
                    month = mdl.getSadSdelMonth();
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
            sql.addSql(" select 1 from SML_SMEIS");
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
            sql.addSql("   SMS_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   SMS_JKBN != ?");
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
     * <p>Create SML_SMEIS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SmlSmeisModel
     * @throws SQLException SQL実行例外
     */
    private SmlSmeisModel __getSmlSmeisFromRs(ResultSet rs) throws SQLException {
        SmlSmeisModel bean = new SmlSmeisModel();
        bean.setSacSid(rs.getInt("SAC_SID"));
        bean.setSmsSid(rs.getInt("SMS_SID"));
        bean.setSmsSdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMS_SDATE")));
        bean.setSmsTitle(rs.getString("SMS_TITLE"));
        bean.setSmsMark(rs.getInt("SMS_MARK"));
        bean.setSmsBody(rs.getString("SMS_BODY"));
        bean.setSmsBodyPlain(rs.getString("SMS_BODY_PLAIN"));
        bean.setSmsJkbn(rs.getInt("SMS_JKBN"));
        bean.setSmsSize(rs.getLong("SMS_SIZE"));
        bean.setSmsType(rs.getInt("SMS_TYPE"));
        bean.setSmsAuid(rs.getInt("SMS_AUID"));
        bean.setSmsAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMS_ADATE")));
        bean.setSmsEuid(rs.getInt("SMS_EUID"));
        bean.setSmsEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SMS_EDATE")));
        return bean;
    }
}