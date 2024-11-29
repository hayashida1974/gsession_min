package jp.groupsession.v2.cmn.dao.base;

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
import jp.groupsession.v2.cmn.model.base.SaibanModel;

/**
 * <p>SAIBAN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SaibanDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SaibanDao.class);

    /**
     * <p>Default Constructor
     */
    public SaibanDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SaibanDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CMN_SAIBAN Data Bindding JavaBean
     * @param bean CMN_SAIBAN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SaibanModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CMN_SAIBAN(");
            sql.addSql("   sbn_sid,");
            sql.addSql("   sbn_sid_sub,");
            sql.addSql("   sbn_number,");
            sql.addSql("   sbn_string,");
            sql.addSql("   sbn_aid,");
            sql.addSql("   sbn_adate,");
            sql.addSql("   sbn_eid,");
            sql.addSql("   sbn_edate");
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
            sql.addStrValue(bean.getSbnSid());
            sql.addStrValue(bean.getSbnSidSub());
            sql.addLongValue(bean.getSbnNumber());
            sql.addStrValue(bean.getSbnString());
            sql.addIntValue(bean.getSbnAid());
            sql.addDateValue(bean.getSbnAdate());
            sql.addIntValue(bean.getSbnEid());
            sql.addDateValue(bean.getSbnEdate());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
        }
    }

    /**
     * <p>Update CMN_SAIBAN Data Bindding JavaBean
     * @param bean CMN_SAIBAN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void update(SaibanModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_SAIBAN");
            sql.addSql(" set ");
            sql.addSql("   sbn_number=?,");
            sql.addSql("   sbn_string=?,");
            sql.addSql("   sbn_aid=?,");
            sql.addSql("   sbn_adate=?,");
            sql.addSql("   sbn_eid=?,");
            sql.addSql("   sbn_edate=?");
            sql.addSql(" where ");
            sql.addSql("   sbn_sid=?,");
            sql.addSql("   sbn_sid_sub=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getSbnNumber());
            sql.addStrValue(bean.getSbnString());
            sql.addIntValue(bean.getSbnAid());
            sql.addDateValue(bean.getSbnAdate());
            sql.addIntValue(bean.getSbnEid());
            sql.addDateValue(bean.getSbnEdate());
            //where
            sql.addStrValue(bean.getSbnSid());
            sql.addStrValue(bean.getSbnSidSub());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
        }
    }

    /**
     * <p>Select CMN_SAIBAN All Data
     * @return List in CMN_SAIBANModel
     * @throws SQLException SQL実行例外
     */
    public List<SaibanModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SaibanModel> ret = new ArrayList<SaibanModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sbn_sid,");
            sql.addSql("   sbn_sid_sub,");
            sql.addSql("   sbn_number,");
            sql.addSql("   sbn_string,");
            sql.addSql("   sbn_aid,");
            sql.addSql("   sbn_adate,");
            sql.addSql("   sbn_eid,");
            sql.addSql("   sbn_edate");
            sql.addSql(" from ");
            sql.addSql("   CMN_SAIBAN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSaibanFromRs(rs));
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
     * <p>Select CMN_SAIBAN
     * @param bean CMN_SAIBAN Model
     * @return CMN_SAIBANModel
     * @throws SQLException SQL実行例外
     */
    public SaibanModel select(SaibanModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SaibanModel ret = new SaibanModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sbn_sid,");
            sql.addSql("   sbn_sid_sub,");
            sql.addSql("   sbn_number,");
            sql.addSql("   sbn_string,");
            sql.addSql("   sbn_aid,");
            sql.addSql("   sbn_adate,");
            sql.addSql("   sbn_eid,");
            sql.addSql("   sbn_edate");
            sql.addSql(" from");
            sql.addSql("   CMN_SAIBAN");
            sql.addSql(" where ");
            sql.addSql("   sbn_sid=?");
            sql.addSql(" and");
            sql.addSql("   sbn_sid_sub=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getSbnSid());
            sql.addStrValue(bean.getSbnSidSub());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSaibanFromRs(rs);
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
     * <p>Delete CMN_SAIBAN
     * @param bean CMN_SAIBAN Model
     * @throws SQLException SQL実行例外
     */
    public  void delete(SaibanModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CMN_SAIBAN");
            sql.addSql(" where ");
            sql.addSql("   sbn_sid=?");
            sql.addSql(" and");
            sql.addSql("   sbn_sid_sub=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getSbnSid());
            sql.addStrValue(bean.getSbnSidSub());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
        }
    }

    /**
     * <p>Create CMN_SAIBAN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SaibanModel
     * @throws SQLException SQL実行例外
     */
    private SaibanModel __getSaibanFromRs(ResultSet rs) throws SQLException {
        SaibanModel bean = new SaibanModel();
        bean.setSbnSid(rs.getString("sbn_sid"));
        bean.setSbnSidSub(rs.getString("sbn_sid_sub"));
        bean.setSbnNumber(rs.getLong("sbn_number"));
        bean.setSbnString(rs.getString("sbn_string"));
        bean.setSbnAid(rs.getInt("sbn_aid"));
        bean.setSbnAdate(UDate.getInstanceTimestamp(rs.getTimestamp("sbn_adate")));
        bean.setSbnEid(rs.getInt("sbn_eid"));
        bean.setSbnEdate(UDate.getInstanceTimestamp(rs.getTimestamp("sbn_edate")));
        return bean;
    }

    /**
     * <br>[機  能]指定された採番ID、採番IDサブのを採番No.を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 検索条件
     * @return ユーザ情報
     * @throws SQLException SQL実行例外
     */
    public int updateSaibanNo(SaibanModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   CMN_SAIBAN");
            sql.addSql(" set");
            sql.addSql("   SBN_NUMBER = ?,");
            sql.addSql("   SBN_STRING = ?,");
            sql.addSql("   SBN_EID = ?,");
            sql.addSql("   SBN_EDATE = ?");
            sql.addSql(" where");
            sql.addSql("   SBN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SBN_SID_SUB = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addLongValue(bean.getSbnNumber());
            sql.addStrValue(bean.getSbnString());
            sql.addIntValue(bean.getSbnEid());
            sql.addDateValue(bean.getSbnEdate());
            sql.addStrValue(bean.getSbnSid());
            sql.addStrValue(bean.getSbnSidSub());

            log__.debug(sql.toLogString());

            pstmt = sql.setParameter(pstmt);
            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能]採番ID、採番IDサブを元に採番情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 検索条件
     * @return ユーザ情報
     * @throws SQLException SQL実行例外
     */
    public SaibanModel getSaibanData(SaibanModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SaibanModel ret = null;

        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SBN_SID,");
            sql.addSql("   SBN_SID_SUB,");
            sql.addSql("   SBN_NUMBER,");
            sql.addSql("   SBN_STRING,");
            sql.addSql("   SBN_AID,");
            sql.addSql("   SBN_ADATE,");
            sql.addSql("   SBN_EID,");
            sql.addSql("   SBN_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_SAIBAN");
            sql.addSql(" where");
            sql.addSql("   SBN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SBN_SID_SUB = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getSbnSid());
            sql.addStrValue(bean.getSbnSidSub());

            log__.info(sql.toLogString());

            pstmt = sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getSaibanFromRs(rs);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return ret;
    }
    /**
     * <br>[機  能]採番ID、採番IDサブを元に採番情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 検索条件
     * @return ユーザ情報
     * @throws SQLException SQL実行例外
     */
    public SaibanModel getSaibanDataForLock(SaibanModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SaibanModel ret = null;

        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SBN_SID,");
            sql.addSql("   SBN_SID_SUB,");
            sql.addSql("   SBN_NUMBER,");
            sql.addSql("   SBN_STRING,");
            sql.addSql("   SBN_AID,");
            sql.addSql("   SBN_ADATE,");
            sql.addSql("   SBN_EID,");
            sql.addSql("   SBN_EDATE");
            sql.addSql(" from");
            sql.addSql("   CMN_SAIBAN");
            sql.addSql(" where");
            sql.addSql("   SBN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SBN_SID_SUB = ?");
            sql.addSql(" for update");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getSbnSid());
            sql.addStrValue(bean.getSbnSidSub());

            log__.info(sql.toLogString());

            pstmt = sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getSaibanFromRs(rs);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 採番ID、採番IDサブを元に採番を行う
     * <br>[解  説] 対象のレコードに対してロックを行い、採番Noの更新を行う
     * <br>[備  考]
     * @param model 採番情報
     * @return 採番No レコードが存在しない場合は0を返す
     * @throws SQLException SQL実行例外
     */
    public long updateSbnNumber(SaibanModel model) throws SQLException {

        PreparedStatement pstmt = null;

        ResultSet rs = null;
        Connection con = null;
        long number = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            //ロックを取得
            sql.addSql(" update CMN_SAIBAN");
            sql.addSql(" set SBN_EID = ?");
            sql.addSql(" where");
            sql.addSql("   SBN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SBN_SID_SUB = ?");
            pstmt = con.prepareStatement(sql.toSqlString(),
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);

            sql.addIntValue(model.getSbnEid());
            sql.addStrValue(model.getSbnSid());
            sql.addStrValue(model.getSbnSidSub());

            log__.info(sql.toLogString());
            pstmt = sql.setParameter(pstmt);

            pstmt.executeUpdate();
            JDBCUtil.closePreparedStatement(pstmt);

            //採番を行う
            sql = new SqlBuffer();
            sql.addSql(" select SBN_NUMBER from CMN_SAIBAN");
            sql.addSql(" where");
            sql.addSql("   SBN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SBN_SID_SUB = ?");

            pstmt = con.prepareStatement(sql.toSqlString(),
                                      ResultSet.TYPE_SCROLL_SENSITIVE,
                                      ResultSet.CONCUR_UPDATABLE);

            sql.addStrValue(model.getSbnSid());
            sql.addStrValue(model.getSbnSidSub());

            log__.info(sql.toLogString());
            pstmt = sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                number = rs.getLong("SBN_NUMBER");
                number++;
            }
            JDBCUtil.closePreparedStatement(pstmt);

            //採番テーブルを更新する
            sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_SAIBAN");
            sql.addSql(" set");
            sql.addSql("   SBN_NUMBER = ?,");
            sql.addSql("   SBN_EID = ?,");
            sql.addSql("   SBN_EDATE = ?");
            sql.addSql(" where");
            sql.addSql("   SBN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SBN_SID_SUB = ?");

            sql.addLongValue(number);
            sql.addIntValue(model.getSbnEid());
            sql.addDateValue(model.getSbnEdate());
            sql.addStrValue(model.getSbnSid());
            sql.addStrValue(model.getSbnSidSub());

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
            pstmt = sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            log__.error("採番に失敗", e);
            throw new SQLException(e);
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return number;
    }
}
