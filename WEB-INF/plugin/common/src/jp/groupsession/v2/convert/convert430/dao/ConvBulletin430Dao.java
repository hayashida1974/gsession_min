package jp.groupsession.v2.convert.convert430.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.convert.convert430.model.CvtBbsForInfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.3.0へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvBulletin430Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvBulletin430Dao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvBulletin430Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] WEBメールに関するデータのコンバートを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param mtCon 採番コントローラ
     * @throws SQLException コンバートに失敗
     */
    public void convert(MlCountMtController mtCon) throws SQLException {

        //掲示板 使用ディスク量の設定
        try {
            __convertBbsSize();
        } catch (UnsupportedEncodingException e) {
            log__.error(e);
            throw new SQLException("掲示板 ディスク使用量の更新に失敗", e);
        }
    }

    /**
     * <br>[機  能] 掲示板 フォーラム集計情報、スレッド集計情報 ディスク使用量を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException ディスク使用量の更新に失敗
     * @throws UnsupportedEncodingException ディスク使用量の取得に失敗
     */
    private void __convertBbsSize() throws SQLException, UnsupportedEncodingException {
        //フォーラム一覧を取得する
        List<CvtBbsForInfModel> forumList = __getForumList();

        for (CvtBbsForInfModel forumData : forumList) {

            long forumDiskSize = 0;
            int bfiSid = forumData.getBfiSid();
            List<Integer> threadList = __getThreadList(bfiSid);
            if (!threadList.isEmpty()) {

                String logTitle = "[掲示板] " + forumData.getBfiName() + " : ";
                log__.info(logTitle + "更新開始");

                int threMaxCnt = threadList.size();
                log__.info(logTitle + "0" + " / " + threMaxCnt);

                int threCnt = 0;
                for (int btiSid : threadList) {

                    //スレッドフォーラムディスク使用量を更新
                    long threDiskSize = __getThreadDiskSize(btiSid);
                    __updateThreadDisk(btiSid, threDiskSize);
                    forumDiskSize += threDiskSize;

                    threCnt++;
                    if (threCnt % 50 == 0) {
                        log__.info(logTitle + threCnt + " / " + threMaxCnt);
                    }
                }

                //フォーラムディスク使用量を更新
                __updateForumDisk(bfiSid, forumDiskSize);

                log__.info(logTitle + "更新完了");
            }
        }
    }

    /**
     * <br>[機  能] 指定したスレッドのディスクサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return ディスクサイズ
     * @throws SQLException SQL実行時例外
     * @throws UnsupportedEncodingException スレッド情報のエンコードに失敗
     */
    private long __getThreadDiskSize(int btiSid)
    throws SQLException, UnsupportedEncodingException {
        long diskSize = 0;

        //スレッド情報
        diskSize = 20;
        String threTitle = NullDefault.getString(__getThreTitle(btiSid), "");
        diskSize += threTitle.getBytes(GSConst.ENCODING).length;
        diskSize += 24 + 4;
        diskSize += __getSumThreTempFileSize(btiSid);

        //投稿情報
        List<String> writeContentList = __getWriteContentList(btiSid);
        for (String writeContent : writeContentList) {
            writeContent = NullDefault.getString(writeContent, "");
            diskSize += 20 + writeContent.getBytes(GSConst.ENCODING).length + 24 + 4;
        }

        return diskSize;
    }

    /**
     * <br>[機  能] フォーラムの一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return フォーラム情報
     * @throws SQLException SQL実行時例外
     */
    private List<CvtBbsForInfModel> __getForumList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CvtBbsForInfModel> forumList = new ArrayList<CvtBbsForInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BFI_SID,");
            sql.addSql("   BFI_NAME");
            sql.addSql(" from");
            sql.addSql("   BBS_FOR_INF");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CvtBbsForInfModel forumData = new CvtBbsForInfModel();
                forumData.setBfiSid(rs.getInt("BFI_SID"));
                forumData.setBfiName(rs.getString("BFI_NAME"));
                forumList.add(forumData);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return forumList;
    }

    /**
     * <br>[機  能] スレッド一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @return スレッド一覧
     * @throws SQLException SQL実行時例外
     */
    private List<Integer> __getThreadList(int bfiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> threadList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_SID");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" where");
            sql.addSql("   BFI_SID = ?");

            sql.addIntValue(bfiSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                threadList.add(rs.getInt("BTI_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return threadList;
    }

    /**
     * <br>[機  能] 指定したスレッドのタイトルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return スレッドタイトル
     * @throws SQLException SQL実行時例外
     */
    private String __getThreTitle(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String title = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BTI_TITLE");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_INF");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(btiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                title = rs.getString("BTI_TITLE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return title;
    }

    /**
     * <br>[機  能] スレッド内投稿の添付ファイルサイズの合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return 添付ファイルサイズの合計
     * @throws SQLException SQL実行例外
     */
    private long __getSumThreTempFileSize(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long fileSize = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    sum(CMN_BINF.BIN_FILE_SIZE) as SUM_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    BBS_WRITE_INF,");
            sql.addSql("    BBS_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    BBS_WRITE_INF.BTI_SID = ?");
            sql.addSql("  and");
            sql.addSql("    BBS_WRITE_INF.BWI_SID = BBS_BIN.BWI_SID");
            sql.addSql("  and");
            sql.addSql("    BBS_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");

            sql.addIntValue(btiSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("SUM_FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return fileSize;
    }

    /**
     * <br>[機  能] スレッド内の投稿(内容)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @return 投稿(内容)
     * @throws SQLException SQL実行例外
     */
    private List<String> __getWriteContentList(int btiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> writeContentList = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BWI_VALUE");
            sql.addSql(" from");
            sql.addSql("   BBS_WRITE_INF");
            sql.addSql(" where");
            sql.addSql("   BTI_SID=?");

            sql.addIntValue(btiSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                writeContentList.add(rs.getString("BWI_VALUE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return writeContentList;
    }

    /**
     * <br>[機  能] スレッドディスク使用量を更新します。
     * <br>[解  説]
     * <br>[備  考]
     * @param btiSid スレッドSID
     * @param diskSize ディスク使用量
     * @throws SQLException SQL実行時例外
     */
    private void __updateThreadDisk(int btiSid, long diskSize) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_THRE_SUM");
            sql.addSql(" set ");
            sql.addSql("   BTS_SIZE=?");
            sql.addSql(" where ");
            sql.addSql("   BTI_SID=?");

            sql.addLongValue(diskSize);
            sql.addIntValue(btiSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] フォーラムディスク使用量を更新します。
     * <br>[解  説]
     * <br>[備  考]
     * @param bfiSid フォーラムSID
     * @param diskSize ディスク使用量
     * @throws SQLException SQL実行時例外
     */
    private void __updateForumDisk(int bfiSid, long diskSize) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   BBS_FOR_SUM");
            sql.addSql(" set ");
            sql.addSql("   BFS_SIZE=?");
            sql.addSql(" where ");
            sql.addSql("   BFI_SID=?");

            sql.addLongValue(diskSize);
            sql.addIntValue(bfiSid);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
}