package jp.groupsession.v2.sml.sml380;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlBanDestConfDao;

/**
*
* <br>[機  能] 送信先制限設定 一覧画面　検索DAOクラス
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class Sml380Dao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlBanDestConfDao.class);
    /** コネクション*/
    private Connection con__;
    /**
     * コンストラクタ
     * @param con コネクション
     */
    public Sml380Dao(Connection con) {
        setCon(con);
    }
    /**
    *
    * <br>[機  能] 指定SIDから送信制限設定名を取得
    * <br>[解  説]
    * <br>[備  考]
    * @param sbcSids 取得対象SID
    * @return 検索結果
    * @throws SQLException SQL実行時例外
    *
    */
    public List<String> getSbcNameList(String[] sbcSids) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();
        if (sbcSids == null || sbcSids.length == 0) {
            return ret;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SBC_SID,");
            sql.addSql("   SBC_NAME");
            sql.addSql(" from ");
            sql.addSql("   SML_BAN_DEST_CONF");
            sql.addSql(" where ");
            sql.addSql("   SBC_SID in (");
            for (int i = 0; i < sbcSids.length; i++) {
                String sid = sbcSids[i];
                if (i > 0) {
                    sql.addSql("     ,?");
                } else {
                    sql.addSql("      ?");
                }
                sql.addIntValue(Integer.parseInt(sid));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("SBC_NAME"));
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
    *
    * <br>[機  能] 検索結果の件数取得
    * <br>[解  説]
    * <br>[備  考]
    * @param searchMdl 検索パラメータ
    * @return 検索結果件数
    * @throws SQLException SQL実行時例外
    *
    */
    public int recordCount(Sml380SearchModel searchMdl) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(SBC_SID) as CNT");
            sql.addSql(" from ");
            sql.addSql("   SML_BAN_DEST_CONF");
            if (!StringUtil.isNullZeroString(searchMdl.getKeyword())) {
                sql.addSql(" where ");
                sql.addSql("   SBC_NAME ");
                String value = " like '%"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'";
                sql.addSql(value);
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     *
     * <br>[機  能] 一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索パラメータ
     * @return 検索結果
     * @throws SQLException SQL実行時例外
     *
     */
    public List<Sml380DataModel> searchSbc(Sml380SearchModel searchMdl) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Sml380DataModel> ret = new ArrayList<Sml380DataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SBC_SID,");
            sql.addSql("   SBC_NAME,");
            sql.addSql("   SBC_BIKO,");
            sql.addSql("   SBC_AUID,");
            sql.addSql("   SBC_ADATE,");
            sql.addSql("   SBC_EUID,");
            sql.addSql("   SBC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SML_BAN_DEST_CONF");
            if (!StringUtil.isNullZeroString(searchMdl.getKeyword())) {
                sql.addSql(" where ");
                sql.addSql("   SBC_NAME ");
                String value = " like '%"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'";
                sql.addSql(value);
            }
            sql.addSql(" order by ");
            sql.addSql("   SML_BAN_DEST_CONF.SBC_NAME ");
            if (searchMdl.getOrder() == GSConstSmail.ORDER_KEY_ASC) {
                sql.addSql(" asc ");
            } else {
                sql.addSql(" desc ");
            }
            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            int start = searchMdl.getStart();
            int maxCount = searchMdl.getLimit();
            if (start > 1) {
                rs.absolute(start - 1);
            }

            for (int i = 0; rs.next() && i < maxCount; i++) {

                Sml380DataModel bean = new Sml380DataModel();
                bean.setSbcSid(rs.getInt("SBC_SID"));
                bean.setDspSbcName(rs.getString("SBC_NAME"));
                bean.setDspSbcBiko(
                        StringUtilHtml.transToHTmlPlusAmparsant(
                                rs.getString("SBC_BIKO")));
                ret.add(bean);
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
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }
}
