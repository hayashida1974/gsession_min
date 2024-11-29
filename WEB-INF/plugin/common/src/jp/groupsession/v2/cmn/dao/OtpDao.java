package jp.groupsession.v2.cmn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.base.CmnOtpConfModel;
import jp.groupsession.v2.man.GSConstMain;
/**
 *
 * <br>[機  能] ワンタイムパスワード関連DBアクセスDao
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class OtpDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(OtpDao.class);
    /**
     *
     * コンストラクタ
     * @param con コネクション
     */
    public OtpDao(Connection con) {
        super(con);
    }
    /**
     *
     * <br>[機  能] ワンタイムパスワード使用ユーザか返す
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid 対象ユーザSID
     * @param otpConf ワンタイムパスワード基本設定
     * @return ワンタイムパスワード使用ユーザ判定
     * @throws SQLException SQL実行例外
     */
    public boolean isNeedOtpUser(int usrSid, CmnOtpConfModel otpConf) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_OTP_USER");
            sql.addSql(" where");
            sql.addSql("   CMN_OTP_USER.USR_SID=?");
            sql.addSql("   or CMN_OTP_USER.GRP_SID in ");
            sql.addSql("   (select ");
            sql.addSql("      GRP_SID");
            sql.addSql("    from");
            sql.addSql("      CMN_BELONGM");
            sql.addSql("    where");
            sql.addSql("      USR_SID = ?");
            sql.addSql("   )");
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            boolean belong = rs.next();
            if (otpConf.getCocOtpUserType() == GSConstMain.OTP_USRTYPE_NOUSE
                    && !belong) {
                return true;
            }
            if (otpConf.getCocOtpUserType() == GSConstMain.OTP_USRTYPE_USE
                    && belong) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }
    /**
     *
     * <br>[機  能] API設定のベーシック認証が使用するかを確認
     * <br>[解  説] ベーシッk認証使用する場合にワンタイムパスワード機能を使用させないため
     * <br>[備  考]
     * @return 判定結果
     * @throws SQLException SQL実行時例外
     */
    public boolean checkCompOtpUseCondApiConf() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APC_BASIC_USE");
            sql.addSql(" from ");
            sql.addSql("   API_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return (rs.getInt("APC_BASIC_USE") != 1);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return true;
    }
}
