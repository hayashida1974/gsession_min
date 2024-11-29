package jp.groupsession.v2.wml.wml160kn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.wml.wml160.WebmailCsvModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール アカウントインポート確認画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml160knDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml160knDao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml160knDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 使用者ユーザ/グループ名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userIds 使用者ユーザ/グループID
     * @param userKbn 使用者ユーザ/ユーザ区分
     * @return 使用者ユーザ/グループ名
     * @throws SQLException SQL実行時例外
     */
    public List<Wml160knUserDataModel> getUseUserNameList(List<String> userIds, int userKbn)
    throws SQLException {

        List<Wml160knUserDataModel> usrNameList = new ArrayList<Wml160knUserDataModel>();
        if (userIds == null || userIds.isEmpty()) {
            return usrNameList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            if (userKbn == WebmailCsvModel.USERKBN_GROUP) {
                sql.addSql(" select ");
                sql.addSql("   GRP_SID,");
                sql.addSql("   GRP_NAME");
                sql.addSql(" from ");
                sql.addSql("   CMN_GROUPM ");
                sql.addSql(" where");
                sql.addSql("    GRP_JKBN = ? ");
                sql.addIntValue(CmnGroupmDao.GRP_JKBN_LIVING);

                sql.addSql(" and");
                sql.addSql("   GRP_ID in ( ");
            } else {
                sql.addSql(" select ");
                sql.addSql("   CMN_USRM.USR_SID,");
                sql.addSql("   CMN_USRM_INF.USI_SEI, ");
                sql.addSql("   CMN_USRM_INF.USI_MEI ");
                sql.addSql(" from ");
                sql.addSql("   CMN_USRM ");
                sql.addSql("   left join ");
                sql.addSql("     CMN_USRM_INF ");
                sql.addSql("   on ");
                sql.addSql("     CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
                sql.addSql(" where");
                sql.addSql("    USR_JKBN = ? ");
                sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

                sql.addSql(" and");
                sql.addSql("   USR_LGID in ( ");
            }

            if (userIds.size() == 1) {
                sql.addSql(" ? ");
                sql.addStrValue(userIds.get(0));
            } else {
                for (int idx = 0; idx < userIds.size(); idx++) {
                    if (idx == userIds.size() - 1) {
                        sql.addSql(" ? ");
                    } else {
                        sql.addSql(" ?, ");
                    }
                    sql.addStrValue(userIds.get(idx));
                }
            }
            sql.addSql(" )");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Wml160knUserDataModel ret = new Wml160knUserDataModel();
                if (userKbn == WebmailCsvModel.USERKBN_GROUP) {
                    ret.setGrpSid(rs.getInt("GRP_SID"));
                    ret.setGrpName(rs.getString("GRP_NAME"));
                } else {
                    ret.setUsrSid(rs.getInt("USR_SID"));
                    ret.setUsiSei(rs.getString("USI_SEI"));
                    ret.setUsiMei(rs.getString("USI_MEI"));
                }
                usrNameList.add(ret);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return usrNameList;
    }

    /**
     * <br>[機  能] 使用者ユーザ/グループSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userIds 使用者ユーザ/グループID
     * @param userKbn 使用者ユーザ/ユーザ区分
     * @return 使用者ユーザ/グループSID
     * @throws SQLException SQL実行時例外
     */
    public List<String> getUseUserSidList(List<String> userIds, int userKbn)
    throws SQLException {
        List<String> ret = new ArrayList<String>();
        if (userIds == null || userIds.isEmpty()) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            String sidName = "";
            SqlBuffer sql = new SqlBuffer();
            if (userKbn == WebmailCsvModel.USERKBN_GROUP) {
                sidName = "GRP_SID";
                sql.addSql(" select ");
                sql.addSql("   GRP_SID");
                sql.addSql(" from ");
                sql.addSql("   CMN_GROUPM ");
                sql.addSql(" where");
                sql.addSql("    GRP_JKBN = ? ");
                sql.addIntValue(CmnGroupmDao.GRP_JKBN_LIVING);

                sql.addSql(" and");
                sql.addSql("   GRP_ID in ( ");
            } else {
                sidName = "USR_SID";
                sql.addSql(" select ");
                sql.addSql("   USR_SID");
                sql.addSql(" from ");
                sql.addSql("   CMN_USRM ");
                sql.addSql(" where");
                sql.addSql("    USR_JKBN = ? ");
                sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

                sql.addSql(" and");
                sql.addSql("   USR_LGID in ( ");
            }
            if (userIds.size() == 1) {
                sql.addSql(" ? ");
            } else {
                for (int i = 0; i < userIds.size(); i++) {
                    if (i == userIds.size() - 1) {
                        sql.addSql(" ? ");
                        break;
                    }
                    sql.addSql(" ?, ");
                }
            }
            sql.addSql(" )");
            pstmt = con.prepareStatement(sql.toSqlString());

            if (userIds.size() == 1) {
                sql.addStrValue(userIds.get(0));
            } else {
                for (int i = 0; i < userIds.size(); i++) {
                    sql.addStrValue(userIds.get(i));
                }
            }

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString(sidName));
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