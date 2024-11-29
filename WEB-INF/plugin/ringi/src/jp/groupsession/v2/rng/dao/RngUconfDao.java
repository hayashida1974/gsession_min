package jp.groupsession.v2.rng.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RngUconfModel;

/**
 * <p>RNG_UCONF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngUconfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngUconfDao.class);

    /**
     * <p>Default Constructor
     */
    public RngUconfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngUconfDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RNG_UCONF Data Bindding JavaBean
     * @param bean RNG_UCONF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_UCONF(");
            sql.addSql("   USR_SID,");
            sql.addSql("   RUR_SML_NTF,");
            sql.addSql("   RUR_VIEW_CNT,");
            sql.addSql("   RUR_SML_JUSIN,");
            sql.addSql("   RUR_SML_DAIRI");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRurSmlNtf());
            sql.addIntValue(bean.getRurViewCnt());
            sql.addIntValue(bean.getRurSmlJusin());
            sql.addIntValue(bean.getRurSmlDairi());
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
     * <p>Update RNG_UCONF Data Bindding JavaBean
     * @param bean RNG_UCONF Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(RngUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_UCONF");
            sql.addSql(" set ");
            sql.addSql("   RUR_SML_NTF=?,");
            sql.addSql("   RUR_VIEW_CNT=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRurSmlNtf());
            sql.addIntValue(bean.getRurViewCnt());
            //where
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
     * <p>Update RNG_UCONF Data Bindding JavaBean
     * @param bean RNG_UCONF Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int updateSml(RngUconfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_UCONF");
            sql.addSql(" set ");
            sql.addSql("   RUR_SML_NTF=?,");
            sql.addSql("   RUR_SML_JUSIN=?,");
            sql.addSql("   RUR_SML_DAIRI=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRurSmlNtf());
            sql.addIntValue(bean.getRurSmlJusin());
            sql.addIntValue(bean.getRurSmlDairi());
            //where
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
     * <p>Select RNG_UCONF All Data
     * @return List in RNG_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public List<RngUconfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RngUconfModel> ret = new ArrayList<RngUconfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   RUR_SML_NTF,");
            sql.addSql("   RUR_VIEW_CNT,");
            sql.addSql("   RUR_SML_JUSIN");
            sql.addSql("   RUR_SML_DAIRI");

            sql.addSql(" from ");
            sql.addSql("   RNG_UCONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngUconfFromRs(rs));
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
     * <p>Select RNG_UCONF
     * @param userSid ユーザSID
     * @return RNG_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public RngUconfModel select(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngUconfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   RUR_SML_NTF,");
            sql.addSql("   RUR_VIEW_CNT,");
            sql.addSql("   RUR_SML_JUSIN,");
            sql.addSql("   RUR_SML_DAIRI");
            sql.addSql(" from");
            sql.addSql("   RNG_UCONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngUconfFromRs(rs);
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
     * <p>個人設定 ショートメール通知 代理人通知設定を行っているユーザを取得
     * @param usrSid ユーザSID
     * @return RNG_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> selectDairi(List<Integer> usrSid) throws SQLException {

        List<Integer> ret = new ArrayList<Integer>();
        if (usrSid == null || usrSid.size() == 0) {
            return ret;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   USR_SID_DAIRI ");
            sql.addSql(" from ");
            sql.addSql("   RNG_DAIRI_USER ");
            sql.addSql(" where ");
            sql.addSql("   RNG_DAIRI_USER.USR_SID in ( ");
            for (int idx = 0; idx < usrSid.size(); idx++) {
                if (idx != 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ? ");
                sql.addIntValue(usrSid.get(idx));
            }
            sql.addSql("   ) ");
            sql.addSql(" and ");
            sql.addSql("   USR_SID_DAIRI not in (");
            sql.addSql("     select ");
            sql.addSql("       USR_SID ");
            sql.addSql("     from ");
            sql.addSql("       RNG_UCONF ");
            sql.addSql("     where ");
            sql.addSql("       RUR_SML_DAIRI = ? ");
            sql.addSql("   ) ");

            sql.addIntValue(RngConst.RNG_SMAIL_NOT_TSUUCHI);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("USR_SID_DAIRI"));
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
     * <p>Select RNG_UCONF
     * @param usrSid ユーザSID
     * @return RNG_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> selectSinsei(List<Integer> usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   SINSEI.USR_SID");
            sql.addSql(" from");
            sql.addSql("   (select");
            sql.addSql("      BASE.USR_SID,");
            sql.addSql("      RUR_SML_NTF");
            sql.addSql("    from");
            sql.addSql("      (select");
            sql.addSql("         USR_SID");
            sql.addSql("       from");
            sql.addSql("         CMN_USRM_INF");
            sql.addSql("       where");
            if (usrSid.size() > 0) {
                sql.addSql("         USR_SID IN (");
                for (int idx = 0; idx < usrSid.size(); idx++) {
                    if (usrSid.get(idx) != 0) {
                        sql.addSql(" ?");
                        sql.addIntValue(usrSid.get(idx));
                        if (idx + 1 != usrSid.size()) {
                            sql.addSql(" ,");
                        }
                    }
                }
                sql.addSql("                    ) ");
            } else {
                sql.addSql("         USR_SID = -1");
            }
            sql.addSql("      ) BASE");
            sql.addSql(" left join");
            sql.addSql("   (select");
            sql.addSql("      USR_SID,");
            sql.addSql("      RUR_SML_NTF");
            sql.addSql("    from");
            sql.addSql("      RNG_UCONF");
            sql.addSql("    where ");
            if (usrSid.size() > 0) {
                sql.addSql("      USR_SID IN (");
                for (int idx = 0; idx < usrSid.size(); idx++) {
                    if (usrSid.get(idx) != 0) {
                        sql.addSql(" ?");
                        sql.addIntValue(usrSid.get(idx));
                        if (idx + 1 != usrSid.size()) {
                            sql.addSql(" ,");
                        }
                    }
                }
                sql.addSql("                 )");
            } else {
                sql.addSql("      USR_SID = -1");
            }
            sql.addSql("   )RNG");
            sql.addSql(" on");
            sql.addSql("   BASE.USR_SID = RNG.USR_SID) SINSEI");
            sql.addSql(" where");
            sql.addSql("   SINSEI.RUR_SML_NTF = 0");
            sql.addSql(" or");
            sql.addSql("   SINSEI.RUR_SML_NTF IS NULL");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
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
     * <p>Select RNG_UCONF
     * @param usrSid ユーザSID
     * @return RNG_UCONFModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> selectZyusin(List<Integer> usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   JUSIN.USR_SID");
            sql.addSql(" from");
            sql.addSql("   (select");
            sql.addSql("      BASE.USR_SID,");
            sql.addSql("      RUR_SML_JUSIN");
            sql.addSql("    from");
            sql.addSql("      (select");
            sql.addSql("         USR_SID");
            sql.addSql("       from");
            sql.addSql("         CMN_USRM_INF");
            sql.addSql("       where");
            if (usrSid.size() > 0) {
                sql.addSql("         USR_SID IN (");
                for (int idx = 0; idx < usrSid.size(); idx++) {
                    if (usrSid.get(idx) != 0) {
                        sql.addSql(" ?");
                        sql.addIntValue(usrSid.get(idx));
                        if (idx + 1 != usrSid.size()) {
                            sql.addSql(" ,");
                        }
                    }
                }
                sql.addSql("                    ) ");
            } else {
                sql.addSql("         USR_SID = -1");
            }

            sql.addSql("      ) BASE");
            sql.addSql(" left join");
            sql.addSql("   (select");
            sql.addSql("      USR_SID,");
            sql.addSql("      RUR_SML_JUSIN");
            sql.addSql("    from");
            sql.addSql("      RNG_UCONF");
            sql.addSql("    where ");
            if (usrSid.size() > 0) {
                sql.addSql("      USR_SID IN (");
                for (int idx = 0; idx < usrSid.size(); idx++) {
                    if (usrSid.get(idx) != 0) {
                        sql.addSql(" ?");
                        sql.addIntValue(usrSid.get(idx));
                        if (idx + 1 != usrSid.size()) {
                            sql.addSql(" ,");
                        }
                    }
                }
                sql.addSql("                 )");
            } else {
                sql.addSql("      USR_SID = -1");
            }

            sql.addSql("   )RNG");
            sql.addSql(" on");
            sql.addSql("   BASE.USR_SID = RNG.USR_SID) JUSIN");
            sql.addSql(" where");
            sql.addSql("   JUSIN.RUR_SML_JUSIN = 0");
            sql.addSql(" or");
            sql.addSql("   JUSIN.RUR_SML_JUSIN IS NULL");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
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
     * <p>Delete RNG_UCONF
     * @param userSid ユーザSID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_UCONF");
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
     * <p>Create RNG_UCONF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngUconfModel
     * @throws SQLException SQL実行例外
     */
    private RngUconfModel __getRngUconfFromRs(ResultSet rs) throws SQLException {
        RngUconfModel bean = new RngUconfModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRurSmlNtf(rs.getInt("RUR_SML_NTF"));
        bean.setRurViewCnt(rs.getInt("RUR_VIEW_CNT"));
        bean.setRurSmlJusin(rs.getInt("RUR_SML_JUSIN"));
        bean.setRurSmlDairi(rs.getInt("RUR_SML_DAIRI"));
        return bean;
    }
}
