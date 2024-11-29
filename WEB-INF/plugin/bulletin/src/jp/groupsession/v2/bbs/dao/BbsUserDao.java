package jp.groupsession.v2.bbs.dao;


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
import jp.groupsession.v2.bbs.model.BbsUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BBS_USER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BbsUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsUserDao.class);

    /**
     * <p>Default Constructor
     */
    public BbsUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BbsUserDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert BBS_USER Data Bindding JavaBean
     * @param bean BBS_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(BbsUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" BBS_USER(");
            sql.addSql("   USR_SID,");
            sql.addSql("   BUR_FOR_CNT,");
            sql.addSql("   BUR_THRE_CNT,");
            sql.addSql("   BUR_WRT_CNT,");
            sql.addSql("   BUR_NEW_CNT,");
            sql.addSql("   BUR_SML_NTF,");
            sql.addSql("   BUR_THRE_MAIN_CNT,");
            sql.addSql("   BUR_WRTLIST_ORDER,");
            sql.addSql("   BUR_THRE_IMAGE,");
            sql.addSql("   BUR_MAIN_CHKED_DSP,");
            sql.addSql("   BUR_AUID,");
            sql.addSql("   BUR_ADATE,");
            sql.addSql("   BUR_EUID,");
            sql.addSql("   BUR_EDATE,");
            sql.addSql("   BUR_SUB_NEW_THRE,");
            sql.addSql("   BUR_SUB_FORUM,");
            sql.addSql("   BUR_SUB_UNCHK_THRE,");
            sql.addSql("   BUR_TEMP_IMAGE,");
            sql.addSql("   BUR_INI_POST_TYPE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getBurForCnt());
            sql.addIntValue(bean.getBurThreCnt());
            sql.addIntValue(bean.getBurWrtCnt());
            sql.addIntValue(bean.getBurNewCnt());
            sql.addIntValue(bean.getBurSmlNtf());
            sql.addIntValue(bean.getBurThreMainCnt());
            sql.addIntValue(bean.getBurWrtlistOrder());
            sql.addIntValue(bean.getBurThreImage());
            sql.addIntValue(bean.getBurMainChkedDsp());
            sql.addIntValue(bean.getBurAuid());
            sql.addDateValue(bean.getBurAdate());
            sql.addIntValue(bean.getBurEuid());
            sql.addDateValue(bean.getBurEdate());
            sql.addIntValue(bean.getBurSubNewThre());
            sql.addIntValue(bean.getBurSubForum());
            sql.addIntValue(bean.getBurSubUnchkThre());
            sql.addIntValue(bean.getBurTempImage());
            sql.addIntValue(bean.getBurIniPostType());
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
     * <p>Update BBS_USER Data Bindding JavaBean
     * @param bean BBS_USER Data Bindding JavaBean
     * @return update count
     * @throws SQLException SQL実行例外
     */
    public int update(BbsUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_USER");
            sql.addSql(" set ");
            sql.addSql("   BUR_FOR_CNT=?,");
            sql.addSql("   BUR_THRE_CNT=?,");
            sql.addSql("   BUR_WRT_CNT=?,");
            sql.addSql("   BUR_NEW_CNT=?,");
            sql.addSql("   BUR_SML_NTF=?,");
            sql.addSql("   BUR_THRE_MAIN_CNT=?,");
            sql.addSql("   BUR_WRTLIST_ORDER=?,");
            sql.addSql("   BUR_THRE_IMAGE=?,");
            sql.addSql("   BUR_MAIN_CHKED_DSP=?,");
            sql.addSql("   BUR_EUID=?,");
            sql.addSql("   BUR_EDATE=?,");
            sql.addSql("   BUR_SUB_NEW_THRE=?,");
            sql.addSql("   BUR_SUB_FORUM=?,");
            sql.addSql("   BUR_SUB_UNCHK_THRE=?,");
            sql.addSql("   BUR_TEMP_IMAGE=?,");
            sql.addSql("   BUR_INI_POST_TYPE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getBurForCnt());
            sql.addIntValue(bean.getBurThreCnt());
            sql.addIntValue(bean.getBurWrtCnt());
            sql.addIntValue(bean.getBurNewCnt());
            sql.addIntValue(bean.getBurSmlNtf());
            sql.addIntValue(bean.getBurThreMainCnt());
            sql.addIntValue(bean.getBurWrtlistOrder());
            sql.addIntValue(bean.getBurThreImage());
            sql.addIntValue(bean.getBurMainChkedDsp());
            sql.addIntValue(bean.getBurEuid());
            sql.addDateValue(bean.getBurEdate());
            sql.addIntValue(bean.getBurSubNewThre());
            sql.addIntValue(bean.getBurSubForum());
            sql.addIntValue(bean.getBurSubUnchkThre());
            sql.addIntValue(bean.getBurTempImage());
            sql.addIntValue(bean.getBurIniPostType());
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
     * <p>Select BBS_USER All Data
     * @return List in BBS_USERModel
     * @throws SQLException SQL実行例外
     */
    public List < BbsUserModel > select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List < BbsUserModel > ret = new ArrayList < BbsUserModel >();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   BUR_FOR_CNT,");
            sql.addSql("   BUR_THRE_CNT,");
            sql.addSql("   BUR_WRT_CNT,");
            sql.addSql("   BUR_NEW_CNT,");
            sql.addSql("   BUR_SML_NTF,");
            sql.addSql("   BUR_THRE_MAIN_CNT,");
            sql.addSql("   BUR_WRTLIST_ORDER,");
            sql.addSql("   BUR_THRE_IMAGE,");
            sql.addSql("   BUR_MAIN_CHKED_DSP,");
            sql.addSql("   BUR_AUID,");
            sql.addSql("   BUR_ADATE,");
            sql.addSql("   BUR_EUID,");
            sql.addSql("   BUR_EDATE,");
            sql.addSql("   BUR_SUB_NEW_THRE,");
            sql.addSql("   BUR_SUB_FORUM,");
            sql.addSql("   BUR_SUB_UNCHK_THRE,");
            sql.addSql("   BUR_TEMP_IMAGE,");
            sql.addSql("   BUR_INI_POST_TYPE");
            sql.addSql(" from ");
            sql.addSql("   BBS_USER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBbsUserFromRs(rs));
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
     * <p>Select BBS_USER
     * @param userId ユーザID
     * @return BBS_USERModel
     * @throws SQLException SQL実行例外
     */
    public BbsUserModel select(int userId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BbsUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   BUR_FOR_CNT,");
            sql.addSql("   BUR_THRE_CNT,");
            sql.addSql("   BUR_WRT_CNT,");
            sql.addSql("   BUR_NEW_CNT,");
            sql.addSql("   BUR_SML_NTF,");
            sql.addSql("   BUR_THRE_MAIN_CNT,");
            sql.addSql("   BUR_WRTLIST_ORDER,");
            sql.addSql("   BUR_THRE_IMAGE,");
            sql.addSql("   BUR_MAIN_CHKED_DSP,");
            sql.addSql("   BUR_AUID,");
            sql.addSql("   BUR_ADATE,");
            sql.addSql("   BUR_EUID,");
            sql.addSql("   BUR_EDATE,");
            sql.addSql("   BUR_SUB_NEW_THRE,");
            sql.addSql("   BUR_SUB_FORUM,");
            sql.addSql("   BUR_SUB_UNCHK_THRE,");
            sql.addSql("   BUR_TEMP_IMAGE,");
            sql.addSql("   BUR_INI_POST_TYPE");
            sql.addSql(" from");
            sql.addSql("   BBS_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userId);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getBbsUserFromRs(rs);
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
     * <p>Delete BBS_USER
     * @param bean BBS_USER Model
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(BbsUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_USER");
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
     * <p>Create BBS_USER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BbsUserModel
     * @throws SQLException SQL実行例外
     */
    private BbsUserModel __getBbsUserFromRs(ResultSet rs) throws SQLException {
        BbsUserModel bean = new BbsUserModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setBurForCnt(rs.getInt("BUR_FOR_CNT"));
        bean.setBurThreCnt(rs.getInt("BUR_THRE_CNT"));
        bean.setBurWrtCnt(rs.getInt("BUR_WRT_CNT"));
        bean.setBurNewCnt(rs.getInt("BUR_NEW_CNT"));
        bean.setBurSmlNtf(rs.getInt("BUR_SML_NTF"));
        bean.setBurThreMainCnt(rs.getInt("BUR_THRE_MAIN_CNT"));
        bean.setBurWrtlistOrder(rs.getInt("BUR_WRTLIST_ORDER"));
        bean.setBurThreImage(rs.getInt("BUR_THRE_IMAGE"));
        bean.setBurMainChkedDsp(rs.getInt("BUR_MAIN_CHKED_DSP"));
        bean.setBurAuid(rs.getInt("BUR_AUID"));
        bean.setBurAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BUR_ADATE")));
        bean.setBurEuid(rs.getInt("BUR_EUID"));
        bean.setBurEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BUR_EDATE")));
        bean.setBurSubNewThre(rs.getInt("BUR_SUB_NEW_THRE"));
        bean.setBurSubForum(rs.getInt("BUR_SUB_FORUM"));
        bean.setBurSubUnchkThre(rs.getInt("BUR_SUB_UNCHK_THRE"));
        bean.setBurTempImage(rs.getInt("BUR_TEMP_IMAGE"));
        bean.setBurIniPostType(rs.getInt("BUR_INI_POST_TYPE"));
        return bean;
    }

    /**
     * <p>ショートメール通知対象のユーザSIDを取得する。
     * @param fsid フォーラムSID
     * @param psetSmail 個人がショートメール通知設定可能か true:可能 false:不可
     * @return List in Model
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getSendSmailList(int fsid, boolean psetSmail)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SENDSID.USR_SID as USID,");
            sql.addSql("   FORUM_MEM.BFI_SID");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       CMN_USRM.USR_SID,");
            sql.addSql("       case");
            sql.addSql("       when");
            sql.addSql("         BBS_USER.BUR_SML_NTF = 1 then 1");
            sql.addSql("       else");
            sql.addSql("         0");
            sql.addSql("       end SMLFLG");
            sql.addSql("     from");
            sql.addSql("       CMN_USRM");
            sql.addSql("       left join");
            sql.addSql("         BBS_USER");
            sql.addSql("       on");
            sql.addSql("         CMN_USRM.USR_SID=BBS_USER.USR_SID");
            sql.addSql("     where");
            sql.addSql("       CMN_USRM.USR_JKBN = 0");
            sql.addSql("   ) SENDSID, ");
            sql.addSql("   (");
            sql.addSql("     select");
            sql.addSql("       BFI_SID,");
            sql.addSql("       USR_SID,");
            sql.addSql("       count(*)");
            sql.addSql("     from");
            sql.addSql("       (");
            sql.addSql("         select");
            sql.addSql("           BFI_SID,");
            sql.addSql("           USR_SID");
            sql.addSql("         from");
            sql.addSql("           BBS_FOR_MEM");
            sql.addSql("         where USR_SID > 0");
            sql.addSql("       union all");
            sql.addSql("         select");
            sql.addSql("           BBS_FOR_MEM.BFI_SID as BFI_SID,");
            sql.addSql("           CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql("         from");
            sql.addSql("           BBS_FOR_MEM,");
            sql.addSql("           CMN_BELONGM");
            sql.addSql("         where");
            sql.addSql("           BBS_FOR_MEM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("       ) BBS_MEM");
            sql.addSql("     group by BFI_SID, USR_SID");
            sql.addSql("   ) FORUM_MEM");
            sql.addSql(" where");
            sql.addSql("   FORUM_MEM.BFI_SID = ?");
            if (psetSmail) {
                sql.addSql(" and");
                sql.addSql("   SMLFLG=0");
            }
            sql.addSql(" and");
            sql.addSql("   FORUM_MEM.USR_SID=SENDSID.USR_SID");

            //フォーラムSID
            sql.addIntValue(fsid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer usid = new Integer(rs.getInt("USID"));
                if (ret.indexOf(usid) < 0) {
                    ret.add(usid);
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
}
