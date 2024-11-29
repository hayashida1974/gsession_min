package jp.groupsession.v2.sml.sml260kn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール アカウントインポート確認画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml260knDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml260knDao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Sml260knDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 使用者ユーザ名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userIds ユーザID
     * @return 使用者ユーザ名
     * @throws SQLException SQL実行時例外
     */
    public List<CmnUsrmInfModel> getUseUserNameList(List<String> userIds)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CmnUsrmInfModel ret = null;
        List<CmnUsrmInfModel> usrNameList = new ArrayList<CmnUsrmInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI ");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM ");
            sql.addSql("  left join ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql("   on ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" where USR_LGID in ( ");
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
            sql.addSql(" ) and ");
            sql.addSql("    USR_JKBN = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());

            if (userIds.size() == 1) {
                sql.addStrValue(userIds.get(0));
            } else {
                for (int i = 0; i < userIds.size(); i++) {
                    sql.addStrValue(userIds.get(i));
                }
            }

            sql.addIntValue(0);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret = new CmnUsrmInfModel();
                ret.setUsrSid(rs.getInt("USR_SID"));
                ret.setUsiSei(rs.getString("USI_SEI"));
                ret.setUsiMei(rs.getString("USI_MEI"));
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
     * <br>[機  能] 使用者ユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userIds ユーザID
     * @return 使用者ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<String> getUseUserSidList(List<String> userIds)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_SID");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM ");
            sql.addSql("  left join ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql("   on ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" where USR_LGID in ( ");
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
            sql.addSql(" ) and ");
            sql.addSql("    USR_JKBN = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());

            if (userIds.size() == 1) {
                sql.addStrValue(userIds.get(0));
            } else {
                for (int i = 0; i < userIds.size(); i++) {
                    sql.addStrValue(userIds.get(i));
                }
            }

            sql.addIntValue(0);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("USR_SID"));
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