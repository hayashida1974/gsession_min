package jp.groupsession.v2.convert.convert490.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
/**
 *
 * <br>[機  能] 稟議申請時 存在しないテンプレートを参照して稟議の申請が行えていた不具合対応
 * <br>[解  説] 作成された不正な稟議の削除を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngRtpErrorDataConvertDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngRtpErrorDataConvertDao.class);
    /**
     *
     * <br>[機  能] コンバート処理
     * <br>[解  説] テンプレート参照が不正な稟議が登録可能の間に作成された不正な稟議を削除
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void convert(Connection con) throws SQLException {
        /*--------------No738 稟議申請時 存在しないテンプレートを参照して稟議の申請が行えていた不具合----*/
        //存在しないテンプレートを参照した稟議の取得
        List<Integer> rngSidList = __selectRtpErrorDataSid(con);
        for (int stIdx = 0; stIdx < rngSidList.size(); stIdx += 500) {
            log__.info("不正な稟議の削除 " + stIdx + "/" + rngSidList.size());
            int edIdx = stIdx + 500;
            if (edIdx >= rngSidList.size()) {
                edIdx = rngSidList.size();
            }
            List<Integer> sidList = rngSidList.subList(stIdx, edIdx);

            //複写用稟議経路ステップ情報の削除
            __deleteRCK(con, sidList);

            //複写用稟議経路ステップ選択情報の削除
            __deleteRCS(con, sidList);

            //稟議情報の削除
            __deleteRnd(con, sidList);

            // 稟議経路選択ユーザ情報の削除
            __deleteRKU(con, sidList);

            //稟議経路情報の削除
            __deleteRKS(con, sidList);

            // 稟議審議情報の削除
            __deleteRSS(con, sidList);

            // 稟議フォーム入力値情報の取得
            __deleteRFD(con, sidList);

            //バイナリー情報の論理削除
            //稟議添付情報の削除
            __deleteRBD(con, sidList);
        }
        log__.info("不正な稟議の削除 " + rngSidList.size() + "/" + rngSidList.size());
        /*-------------------------------*/

    }
    /**
     *
     * <br>[機  能] バイナリー情報の論理削除、稟議添付情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList 対象稟議SID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteRBD(Connection con, List<Integer> sidList) throws SQLException {
        //バイナリー情報の論理削除
        PreparedStatement pstmt = null;
        try {

            //バイナリー情報の論理削除

            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(0);
            sql.addDateValue(new UDate());
            sql.addIntValue(9);
            sql.addSql(" where");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select BIN_SID from RNG_BIN");
            sql.addSql("     where ");
            sql.addSql("       RNG_SID in (");
            for (int i = 0; i < sidList.size(); i++) {
                if (i > 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(sidList.get(i));

            }
            sql.addSql("       )");
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        //稟議添付情報の削除

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_BIN");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID in (");
            for (int i = 0; i < sidList.size(); i++) {
                if (i > 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(sidList.get(i));

            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     *
     * <br>[機  能] 稟議経路情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList 対象稟議SID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteRKS(Connection con, List<Integer> sidList) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID in (");
            for (int i = 0; i < sidList.size(); i++) {
                if (i > 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(sidList.get(i));

            }
            sql.addSql("       )");
            pstmt = con.prepareStatement(sql.toSqlString());

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
     *
     * <br>[機  能] 稟議フォーム入力値情報の取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList 対象稟議SID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteRFD(Connection con, List<Integer> sidList) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_FORMDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID in (");
            for (int i = 0; i < sidList.size(); i++) {
                if (i > 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(sidList.get(i));

            }
            sql.addSql("       )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     *
     * <br>[機  能] 稟議審議情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList 対象稟議SID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteRSS(Connection con, List<Integer> sidList) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID in (");
            for (int i = 0; i < sidList.size(); i++) {
                if (i > 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(sidList.get(i));

            }
            sql.addSql("       )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     *
     * <br>[機  能] 稟議経路選択ユーザ情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList 対象稟議SID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteRKU(Connection con, List<Integer> sidList) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_KEIROSTEP_SELECT");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID IN");
            sql.addSql("     (");
            sql.addSql("      select");
            sql.addSql("        RKS_SID");
            sql.addSql("      from");
            sql.addSql("        RNG_KEIRO_STEP");
            sql.addSql("      where");
            sql.addSql("        RNG_SID in (");
            for (int i = 0; i < sidList.size(); i++) {
                if (i > 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(sidList.get(i));

            }
            sql.addSql("       )");
            sql.addSql("     )");
            pstmt = con.prepareStatement(sql.toSqlString());
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
     *
     * <br>[機  能] 稟議情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList 対象稟議SID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteRnd(Connection con, List<Integer> sidList) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" where ");
            sql.addSql("   RNG_SID in (");
            for (int i = 0; i < sidList.size(); i++) {
                if (i > 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(sidList.get(i));

            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     *
     * <br>[機  能] 複写用稟議経路ステップ選択情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList 対象稟議SID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteRCS(Connection con, List<Integer> sidList) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_COPY_KEIROSTEP_SELECT");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID IN");
            sql.addSql("     (");
            sql.addSql("      select");
            sql.addSql("        RKS_SID");
            sql.addSql("      from");
            sql.addSql("        RNG_KEIRO_STEP");
            sql.addSql("      where");
            sql.addSql("        RNG_SID in (");
            for (int i = 0; i < sidList.size(); i++) {
                if (i > 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(sidList.get(i));

            }
            sql.addSql("       )");
            sql.addSql("     )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return;

    }
    /**
     *
     * <br>[機  能] 複写用稟議経路ステップ情報の削除
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sidList 対象稟議SID
     * @throws SQLException SQL実行時例外
     */
    private void __deleteRCK(Connection con, List<Integer> sidList) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_COPY_KEIRO_STEP");
            sql.addSql(" where ");
            sql.addSql("   RKS_SID IN");
            sql.addSql("     (");
            sql.addSql("      select");
            sql.addSql("        RKS_SID");
            sql.addSql("      from");
            sql.addSql("        RNG_KEIRO_STEP");
            sql.addSql("      where");
            sql.addSql("        RNG_SID in (");
            for (int i = 0; i < sidList.size(); i++) {
                if (i > 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addIntValue(sidList.get(i));

            }
            sql.addSql("       )");
            sql.addSql("     )");
            pstmt = con.prepareStatement(sql.toSqlString());
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
     *
     * <br>[機  能] 存在しないテンプレートを参照した稟議の取得
     * <br>[解  説]
     * <br>[備  考] 汎用稟議は除外
     * @param con コネクション
     * @return 削除対象SID
     * @throws SQLException SQL実行時例外
     */
    private List<Integer> __selectRtpErrorDataSid(Connection con) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List <Integer> ret = new ArrayList <Integer>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_SID");
            sql.addSql(" from");
            sql.addSql("   RNG_RNDATA left join");
            sql.addSql("   RNG_TEMPLATE ");
            sql.addSql(" on");
            sql.addSql("   RNG_RNDATA.RTP_SID=RNG_TEMPLATE.RTP_SID");
            sql.addSql(" and");
            sql.addSql("   RNG_RNDATA.RTP_VER=RNG_TEMPLATE.RTP_VER");
            sql.addSql(" where");
            sql.addSql("   RNG_RNDATA.RTP_SID > 0");
            sql.addSql(" and ");
            sql.addSql("   RNG_TEMPLATE.RTP_SID is null");
            sql.addSql(" ");
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("RNG_SID"));
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
