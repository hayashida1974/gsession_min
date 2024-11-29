package jp.groupsession.v2.wml.batch.dao;

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
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;

/**
 * <br>[機  能] WEBメール バッチ処理で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlBatchDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlBatchDao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public WmlBatchDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 削除されたアカウントのアカウントSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アカウントSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getDeleteAccount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> accountSidList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   WAC_JKBN = ?");
            sql.addIntValue(GSConstWebmail.WAC_JKBN_DELETE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                accountSidList.add(rs.getInt("WAC_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return accountSidList;
    }

    /**
     * <br>[機  能] フィルター条件の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delFilterConditionType(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER_CONDITION ");
            sql.addSql(" where ");
            sql.addSql("    WFT_SID in ");
            sql.addSql("     (select WFT_SID from WML_FILTER ");
            sql.addSql("      where  ");
            sql.addSql("        WFT_TYPE = ? ");
            sql.addSql("      and ");
            sql.addSql("        WAC_SID = ?)  ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstWebmail.LABELTYPE_ONES);
            sql.addIntValue(wacSid);

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
     * <br>[機  能] フィルター適用順の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delFilterSortType(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER_SORT ");
            sql.addSql(" where ");
            sql.addSql("    WFT_SID in ");
            sql.addSql("     (select WFT_SID from WML_FILTER ");
            sql.addSql("      where  ");
            sql.addSql("        WFT_TYPE = ?");
            sql.addSql("      and ");
            sql.addSql("        WAC_SID  = ?) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstWebmail.LABELTYPE_ONES);
            sql.addIntValue(wacSid);

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
     * <br>[機  能] フィルターの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delFilterType(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER ");
            sql.addSql(" where ");
            sql.addSql("   WFT_TYPE  = ? ");
            sql.addSql(" and ");
            sql.addSql("   WAC_SID  = ? ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstWebmail.LABELTYPE_ONES);
            sql.addIntValue(wacSid);

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
     * <br>[機  能] フィルター_転送先アドレスの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delFilterFwaddressType(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   WML_FILTER_FWADDRESS ");
            sql.addSql(" where ");
            sql.addSql("    WFT_SID in ");
            sql.addSql("     (select WFT_SID from WML_FILTER ");
            sql.addSql("      where  ");
            sql.addSql("        WFT_TYPE = ?");
            sql.addSql("      and ");
            sql.addSql("        WAC_SID  = ?) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstWebmail.LABELTYPE_ONES);
            sql.addIntValue(wacSid);

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
     * <br>[機  能] メール・ラベルの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wdrSid ディレクトリSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delLabelRelation(long wdrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" where ");
            sql.addSql("   WML_LABEL_RELATION.WMD_MAILNUM in (");
            sql.addSql("     select WMD_MAILNUM from WML_MAILDATA");
            sql.addSql("     where WDR_SID = ?");
            sql.addSql("   )");
            sql.addLongValue(wdrSid);

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] ラベルの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delLabelType(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ? ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
     * <br>[機  能] メール-ラベルの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delLabelRelationType(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   WML_LABEL_RELATION");
            sql.addSql(" where ");
            sql.addSql("   WLB_SID in (");
            sql.addSql("     select WLB_SID from WML_LABEL ");
            sql.addSql("     where WAC_SID = ?");
            sql.addSql("   ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
     * <br>[機  能] 指定したアカウントのメッセージ番号を取得する
     * <br>[解  説] 最大100件まで
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return メッセージ番号
     * @throws SQLException SQL実行例外
     */
    public List<Long> getMailNumList(int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        List<Long> mailNumList = new ArrayList<Long>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WMD_MAILNUM");
            sql.addSql(" from");
            sql.addSql("   WML_MAILDATA");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ? ");
            sql.addSql(" order by");
            sql.addSql("   WMD_MAILNUM");
            sql.addIntValue(wacSid);

            //100件ずつ取得する
            sql.setPagingValue(0, GSConstWebmail.MAX_DEL_MAILDATA);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                mailNumList.add(rs.getLong("WMD_MAILNUM"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return mailNumList;
    }

    /**
     * <br>[機  能] メール添付ファイルの論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wtdMdl WmlTempfileModel
     * @param mailNumList メッセージ番号
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int updateTempFile(WmlTempfileModel wtdMdl, List<Long> mailNumList)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   WML_TEMPFILE ");
            sql.addSql(" set ");
            sql.addSql("   WTF_EUID = ?, ");
            sql.addSql("   WTF_EDATE = ?, ");
            sql.addSql("   WTF_JKBN = ? ");
            sql.addIntValue(wtdMdl.getWtfEuid());
            sql.addDateValue(wtdMdl.getWtfEdate());
            sql.addIntValue(wtdMdl.getWtfJkbn());

            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM in (");
            for (int i = 0; i < mailNumList.size(); i++) {
                if (i == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(mailNumList.get(i));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] メール情報に関連する情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mailNumList メッセージ番号
     * @param tableName テーブル名
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    public int delMailData(List<Long> mailNumList, String tableName) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   " + tableName);
            sql.addSql(" where ");
            sql.addSql("   WMD_MAILNUM in (");
            for (int i = 0; i < mailNumList.size(); i++) {
                if (i == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(mailNumList.get(i));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] ディレクトリの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delDirectory(int wacSid) throws SQLException {
        return __delAccountData(wacSid, "WML_DIRECTORY");
    }

    /**
     * <br>[機  能] 受信済みメールUIDLを取得する
     * <br>[解  説] 100件ずつ取得する
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return 受信済みメールUIDL
     * @throws SQLException SQL実行例外
     */
    public List<String> getUidlList(int wacSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<String> uidlList = new ArrayList<String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select WUD_UID from WML_UIDL");
            sql.addSql(" where WAC_SID = ? ");
            sql.setPagingValue(0, GSConstWebmail.MAX_DEL_MAILDATA);
            sql.addIntValue(wacSid);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                uidlList.add(rs.getString("WUD_UID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return uidlList;
    }

    /**
     * <br>[機  能] 受信済みメールUIDLの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param uidlList UIDL
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delUidl(int wacSid, List<String> uidlList) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   WML_UIDL");
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ? ");
            sql.addIntValue(wacSid);

            sql.addSql(" and");
            sql.addSql("   WUD_UID in (");
            for (int idx = 0; idx < uidlList.size(); idx++) {
                if (idx > 0) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("     ?");
                }
                sql.addStrValue(uidlList.get(idx));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] アカウント並び順の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delAccountSort(int wacSid) throws SQLException {
        return __delAccountData(wacSid, "WML_ACCOUNT_SORT");
    }

    /**
     * <br>[機  能] アカウント使用者の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delAccountUser(int wacSid) throws SQLException {
        return __delAccountData(wacSid, "WML_ACCOUNT_USER");
    }

    /**
     * <br>[機  能] アカウント署名の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delAccountSign(int wacSid) throws SQLException {
        return __delAccountData(wacSid, "WML_ACCOUNT_SIGN");
    }

    /**
     * <br>[機  能] アカウントディスク使用量の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delAccountDisk(int wacSid) throws SQLException {
        return __delAccountData(wacSid, "WML_ACCOUNT_DISK");
    }

    /**
     * <br>[機  能] アカウント_受信サーバ情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delAccountRcvSvr(int wacSid) throws SQLException {
        return __delAccountData(wacSid, "WML_ACCOUNT_RCVSVR");
    }

    /**
     * <br>[機  能] アカウント_受信情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delAccountRcvData(int wacSid) throws SQLException {
        return __delAccountData(wacSid, "WML_ACCOUNT_RCVDATA");
    }

    /**
     * <br>[機  能] アカウント情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delAccount(int wacSid) throws SQLException {
        return __delAccountData(wacSid, "WML_ACCOUNT");
    }

    /**
     * <br>[機  能] アカウントに関連する情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param tableName テーブル名
     * @return 削除件数
     * @throws SQLException SQL実行時例外
     */
    private int __delAccountData(int wacSid, String tableName) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   " + tableName);
            sql.addSql(" where ");
            sql.addSql("   WAC_SID = ? ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(wacSid);

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
}
