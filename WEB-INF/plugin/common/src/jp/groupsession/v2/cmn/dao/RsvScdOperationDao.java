package jp.groupsession.v2.cmn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.RsvScdOperationModel;

/**
 * <br>[機  能] 施設予約プラグインからスケジュールクラスを操作するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvScdOperationDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvScdOperationDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvScdOperationDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvScdOperationDao(Connection con) {
        super(con);
    }

    /**
     * <p>Select RSV_SIS_YRK
     * @param rsySid rsySid
     * @return RSV_SIS_YRKModel
     * @throws SQLException SQL実行例外
     */
    public RsvScdOperationModel select(int rsySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvScdOperationModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   RSR_RSID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new RsvScdOperationModel();
                ret.setScdRsSid(rs.getInt("SCD_RSSID"));
                ret.setRsrRsid(rs.getInt("RSR_RSID"));
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRssid スケジュールリレーションSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public ArrayList<RsvScdOperationModel> selectSchList(int scdRssid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvScdOperationModel> ret =
            new ArrayList<RsvScdOperationModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SCD_USR_SID,");
            sql.addSql("   SCD_GRP_SID,");
            sql.addSql("   SCD_USR_KBN,");
            sql.addSql("   SCD_FR_DATE,");
            sql.addSql("   SCD_TO_DATE,");
            sql.addSql("   SCD_DAILY,");
            sql.addSql("   SCD_BGCOLOR,");
            sql.addSql("   SCD_TITLE,");
            sql.addSql("   SCD_VALUE,");
            sql.addSql("   SCD_BIKO,");
            sql.addSql("   SCD_PUBLIC,");
            sql.addSql("   SCD_AUID,");
            sql.addSql("   SCD_ADATE,");
            sql.addSql("   SCD_EUID,");
            sql.addSql("   SCD_EDATE,");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SCD_EDIT,");
            sql.addSql("   SCD_RSSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRssid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvScdOperationModel mdl = __getSchDataFromRs(rs);
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRssid スケジュールリレーションSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public ArrayList<RsvScdOperationModel> selectSchListGrpSceSid(int scdRssid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvScdOperationModel> ret =
            new ArrayList<RsvScdOperationModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCH_DATA.SCD_SID,");
            sql.addSql("   SCH_DATA.SCD_USR_SID,");
            sql.addSql("   SCH_DATA.SCD_GRP_SID,");
            sql.addSql("   SCH_DATA.SCD_USR_KBN,");
            sql.addSql("   SCH_DATA.SCD_FR_DATE,");
            sql.addSql("   SCH_DATA.SCD_TO_DATE,");
            sql.addSql("   SCH_DATA.SCD_DAILY,");
            sql.addSql("   SCH_DATA.SCD_BGCOLOR,");
            sql.addSql("   SCH_DATA.SCD_TITLE,");
            sql.addSql("   SCH_DATA.SCD_VALUE,");
            sql.addSql("   SCH_DATA.SCD_BIKO,");
            sql.addSql("   SCH_DATA.SCD_PUBLIC,");
            sql.addSql("   SCH_DATA.SCD_AUID,");
            sql.addSql("   SCH_DATA.SCD_ADATE,");
            sql.addSql("   SCH_DATA.SCD_EUID,");
            sql.addSql("   SCH_DATA.SCD_EDATE,");
            sql.addSql("   SCH_DATA.SCE_SID,");
            sql.addSql("   SCH_DATA.SCD_EDIT,");
            sql.addSql("   SCH_DATA.SCD_RSSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA,");
            sql.addSql("   (select");
            sql.addSql("      max(SCH_DATA.SCE_SID) as SCE_SID");
            sql.addSql("    from");
            sql.addSql("      SCH_DATA");
            sql.addSql("    where ");
            sql.addSql("      SCH_DATA.SCD_RSSID = ?");
            sql.addSql("    group by");
            sql.addSql("      SCH_DATA.SCD_RSSID");
            sql.addSql("   ) relVw");
            sql.addSql(" where");
            sql.addSql("   SCH_DATA.SCE_SID = relVw.SCE_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRssid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvScdOperationModel mdl = __getSchDataFromRs(rs);
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
     * <br>[機  能] 指定されたスケジュールSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdSid スケジュールSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public RsvScdOperationModel selectSchMdl(int scdSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        RsvScdOperationModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SCD_USR_SID,");
            sql.addSql("   SCD_GRP_SID,");
            sql.addSql("   SCD_USR_KBN,");
            sql.addSql("   SCD_FR_DATE,");
            sql.addSql("   SCD_TO_DATE,");
            sql.addSql("   SCD_DAILY,");
            sql.addSql("   SCD_BGCOLOR,");
            sql.addSql("   SCD_TITLE,");
            sql.addSql("   SCD_VALUE,");
            sql.addSql("   SCD_BIKO,");
            sql.addSql("   SCD_PUBLIC,");
            sql.addSql("   SCD_AUID,");
            sql.addSql("   SCD_ADATE,");
            sql.addSql("   SCD_EUID,");
            sql.addSql("   SCD_EDATE,");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SCD_EDIT,");
            sql.addSql("   SCD_RSSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchDataFromRs(rs);
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考] 単一スケジュール編集の場合
     *
     * @param scdRsSid スケジュールリレーションSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public RsvScdOperationModel selectSchMdlGrpRssid(int scdRsSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        RsvScdOperationModel ret = null;
        HashMap<String, String> usrMap = new HashMap<String, String>();
        HashMap<Integer, Integer> rssidMap = new HashMap<Integer, Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_USR_SID,");
            sql.addSql("   SCD_RSSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRsSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            boolean exist = false;
            while (rs.next()) {
                int rsSid = rs.getInt("SCD_RSSID");
                int usrSid = rs.getInt("SCD_USR_SID");
                String key = rsSid + "-" + usrSid;
                usrMap.put(key, key);
                rssidMap.put(rsSid, rsSid);
                exist = true;
            }

            if (exist) {
                ret = new RsvScdOperationModel();
                ret.setUsrMap(usrMap);
                ret.setRssidMap(rssidMap);
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考] 単一スケジュール編集の場合
     *
     * @param sessionUsrSid セッションユーザSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public HashMap<String, String> selectRsvGrpAdmMap(int sessionUsrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        HashMap<String, String> grpAdmMap = new HashMap<String, String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_ADM");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sessionUsrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                int rsgSid = rs.getInt("RSG_SID");
                int usrSid = rs.getInt("USR_SID");
                String key = rsgSid + "-" + usrSid;
                grpAdmMap.put(key, key);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return grpAdmMap;
    }

    /**
     * <br>[機  能] 指定されたスケジュールSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考] 繰り返しスケジュール編集の場合
     *
     * @param scdSid スケジュールSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public RsvScdOperationModel selectSchMdlGrpSceSid(int scdSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        RsvScdOperationModel ret = null;
        HashMap<String, String> usrMap = new HashMap<String, String>();
        HashMap<Integer, Integer> rssidMap = new HashMap<Integer, Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCH_DATA.SCD_USR_SID,");
            sql.addSql("   SCH_DATA.SCD_RSSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA,");
            sql.addSql("   (select");
            sql.addSql("      SCH_DATA.SCE_SID");
            sql.addSql("    from");
            sql.addSql("      SCH_DATA");
            sql.addSql("    where");
            sql.addSql("      SCH_DATA.SCD_SID = ?");
            sql.addSql("   ) sceGrpVw");
            sql.addSql(" where");
            sql.addSql("   SCH_DATA.SCE_SID = sceGrpVw.SCE_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            boolean exist = false;
            while (rs.next()) {
                int rsSid = rs.getInt("SCD_RSSID");
                int usrSid = rs.getInt("SCD_USR_SID");
                String key = rsSid + "-" + usrSid;
                usrMap.put(key, key);
                rssidMap.put(rsSid, rsSid);
                exist = true;
            }

            if (exist) {
                ret = new RsvScdOperationModel();
                ret.setUsrMap(usrMap);
                ret.setRssidMap(rssidMap);
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
     * <br>[機  能] 指定されたユーザSID_Aのユーザが所属している
     * <br>         グループに、ユーザSID_Bのユーザが所属しているか
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSidA チェック元ユーザ
     * @param usrSidB チェック対象ユーザ
     * @return ret true:所属している false:所属していない
     * @throws SQLException 例外
     */
    public boolean isScdEditTi(int usrSidA, int usrSidB) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CMN_BELONGM.USR_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM,");
            sql.addSql("   (select");
            sql.addSql("      GRP_SID");
            sql.addSql("    from");
            sql.addSql("      CMN_BELONGM");
            sql.addSql("    where");
            sql.addSql("      USR_SID = ?");
            sql.addSql("   ) belongmVw");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.GRP_SID = belongmVw.GRP_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSidA);
            sql.addIntValue(usrSidB);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         施設情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rssidArray スケジュールリレーションSID
     * @return ret 施設情報
     * @throws SQLException 例外
     */
    public ArrayList<RsvScdOperationModel> selectRsvList(ArrayList<Integer> rssidArray)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvScdOperationModel> ret = new ArrayList<RsvScdOperationModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_DATA.RSG_SID,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA,");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID = RSV_SIS_YRK.RSD_SID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID in (");

            for (int i = 0; i < rssidArray.size(); i++) {
                int rssid = rssidArray.get(i);
                sql.addSql("?");
                sql.addIntValue(rssid);
                if (i != rssidArray.size() - 1) {
                    sql.addSql(", ");
                }
            }
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvScdOperationModel mdl = new RsvScdOperationModel();
                mdl.setRsgSid(rs.getInt("RSG_SID"));
                mdl.setScdRsSid(rs.getInt("SCD_RSSID"));
                mdl.setRsySid(rs.getInt("RSY_SID"));
                mdl.setRsyAuid(rs.getInt("RSY_AUID"));
                mdl.setRsyEdit(rs.getInt("RSY_EDIT"));
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
     * <br>[機  能] 指定された施設予約拡張SIDより施設情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsid 施設予約拡張SID
     * @return ret 施設情報
     * @throws SQLException 例外
     */
    public ArrayList<RsvScdOperationModel> selectRsvList(int rsrRsid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvScdOperationModel> ret = new ArrayList<RsvScdOperationModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_DATA.RSG_SID,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA,");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID = RSV_SIS_YRK.RSD_SID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvScdOperationModel mdl = new RsvScdOperationModel();
                mdl.setRsgSid(rs.getInt("RSG_SID"));
                mdl.setRsySid(rs.getInt("RSY_SID"));
                mdl.setScdRsSid(rs.getInt("SCD_RSSID"));
                mdl.setRsyAuid(rs.getInt("RSY_AUID"));
                mdl.setRsyEdit(rs.getInt("RSY_EDIT"));
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
     * <br>[機  能] 指定された施設予約SIDより施設情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsyRsid 施設予約SID
     * @return ret 施設情報
     * @throws SQLException 例外
     */
    public ArrayList<RsvScdOperationModel> selectRsvMdl(int rsyRsid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvScdOperationModel> ret = new ArrayList<RsvScdOperationModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_DATA.RSG_SID,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA,");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID = RSV_SIS_YRK.RSD_SID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSY_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsyRsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvScdOperationModel mdl = new RsvScdOperationModel();
                mdl.setRsgSid(rs.getInt("RSG_SID"));
                mdl.setScdRsSid(rs.getInt("SCD_RSSID"));
                mdl.setRsyAuid(rs.getInt("RSY_AUID"));
                mdl.setRsyEdit(rs.getInt("RSY_EDIT"));
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
     * <br>[機  能] リザルトセットの値をモデルにセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rs リザルトセット
     * @return bean
     * @throws SQLException SQL実行例外
     */
    private RsvScdOperationModel __getSchDataFromRs(ResultSet rs) throws SQLException {
        RsvScdOperationModel bean = new RsvScdOperationModel();
        bean.setScdSid(rs.getInt("SCD_SID"));
        bean.setScdUsrSid(rs.getInt("SCD_USR_SID"));
        bean.setScdGrpSid(rs.getInt("SCD_GRP_SID"));
        bean.setScdUsrKbn(rs.getInt("SCD_USR_KBN"));
        bean.setScdFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_FR_DATE")));
        bean.setScdToDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_TO_DATE")));
        bean.setScdDaily(rs.getInt("SCD_DAILY"));
        bean.setScdBgcolor(rs.getInt("SCD_BGCOLOR"));
        bean.setScdTitle(rs.getString("SCD_TITLE"));
        bean.setScdValue(rs.getString("SCD_VALUE"));
        bean.setScdBiko(rs.getString("SCD_BIKO"));
        bean.setScdPublic(rs.getInt("SCD_PUBLIC"));
        bean.setScdAuid(rs.getInt("SCD_AUID"));
        bean.setScdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_ADATE")));
        bean.setScdEuid(rs.getInt("SCE_SID"));
        bean.setScdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_EDATE")));
        bean.setScdEdit(rs.getInt("SCD_EDIT"));
        bean.setScdRsSid(rs.getInt("SCD_RSSID"));
        return bean;
    }
}