package jp.groupsession.v2.wml.wml260kn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.wml.wml260.Wml260Dao;

/**
 * <br>[機  能] WEBメール 送信予定メール管理確認画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml260knDao extends Wml260Dao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml260knDao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Wml260knDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定したメールが属するアカウントの"草稿"ディレクトリを取得し、メッセージ番号とMappingする
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @return "草稿"ディレクトリSIDとメッセージ番号のMapping
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, List<Long>> getDraftDirectoryMap(long[] mailNumList)
    throws SQLException {

        Map<Integer, List<Long>> draftDirMap = new HashMap<Integer, List<Long>>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WML_DIRECTORY.WDR_SID as WDR_SID,");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM as WMD_MAILNUM");
            sql.addSql(" from ");
            sql.addSql("   WML_DIRECTORY,");
            sql.addSql("   WML_MAILDATA");

            sql.addSql(" where");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM in (");
            for (int mailIdx = 0; mailIdx < mailNumList.length; mailIdx++) {
                if (mailIdx > 0) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("     ?");
                }
                sql.addLongValue(mailNumList[mailIdx]);
            }
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   WML_DIRECTORY.WDR_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   WML_MAILDATA.WAC_SID = WML_DIRECTORY.WAC_SID");
            sql.addIntValue(GSConstWebmail.DIR_TYPE_DRAFT);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                int dirSid = rs.getInt("WDR_SID");
                if (!draftDirMap.containsKey(dirSid)) {
                    draftDirMap.put(dirSid, new ArrayList<Long>());
                }
                draftDirMap.get(dirSid).add(rs.getLong("WMD_MAILNUM"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return draftDirMap;
    }

    /**
     * <br>[機  能] 指定したメール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @return メール情報
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getLogList(long[] mailNumList) throws SQLException {

        ArrayList<String> logList = new ArrayList<String>();
        if (mailNumList == null || mailNumList.length == 0) {
            return logList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_TITLE,");
            sql.addSql("   WAC_NAME");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA,");
            sql.addSql("   WML_DIRECTORY,");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WML_MAILDATA.WDR_SID = WML_DIRECTORY.WDR_SID");
            sql.addSql("   and WML_DIRECTORY.WAC_SID = WML_ACCOUNT.WAC_SID");
            sql.addSql("   and WMD_MAILNUM in (");

            for (int idx = 0; idx < mailNumList.length; idx++) {
                if (idx > 0) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("     ?");
                }
                sql.addLongValue(mailNumList[idx]);
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String msg = "[アカウント名]" + rs.getString("WAC_NAME")
                                    + " [件名]" + rs.getString("WMD_TITLE");
                logList.add(msg);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return logList;
    }
}
