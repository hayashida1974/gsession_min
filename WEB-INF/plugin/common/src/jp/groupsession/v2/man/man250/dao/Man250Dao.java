package jp.groupsession.v2.man.man250.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man250.LogCsvRecordListenerImpl;
import jp.groupsession.v2.man.man250.Man250Biz;
import jp.groupsession.v2.man.man250.model.Man250DspModel;
import jp.groupsession.v2.man.man250.model.Man250SearchModel;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ検索画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man250Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man250Dao.class);

    /**
     * <p>Default Constructor
     */
    public Man250Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Man250Dao(Connection con) {
        super(con);
    }

    /**
     * <p>
     * 検索条件にマッチするオペレーションログ情報の件数を取得する
     * @param searchMdl 検索用モデル
     * @return count 件数
     * @throws SQLException SQL実行例外
     */
    public int countLogList(Man250SearchModel searchMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count (*) as CNT");
            sql.addSql(" from");
            sql.addSql("   (CMN_LOG");
            sql.addSql("     left join CMN_USRM_INF on CMN_LOG.USR_SID = CMN_USRM_INF.USR_SID)");

            //SQLのwhere句を生成
            sql = __createSql(searchMdl, sql);

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>
     * オペレーションログ一覧を取得する
     * @param searchMdl 検索用モデル
     * @param reqMdl リクエスト情報
     * @return ArrayList in SchDataModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Man250DspModel> getLogList(Man250SearchModel searchMdl,
                                                                        RequestModel reqMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Man250DspModel> ret = new ArrayList<Man250DspModel>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_LOG.LOG_DATE,");
            sql.addSql("   CMN_LOG.USR_SID,");
            sql.addSql("   CMN_LOG.LOG_LEVEL,");
            sql.addSql("   CMN_LOG.LOG_PLUGIN,");
            sql.addSql("   CMN_LOG.LOG_PLUGIN_NAME,");
            sql.addSql("   CMN_LOG.LOG_PG_ID,");
            sql.addSql("   CMN_LOG.LOG_PG_NAME,");
            sql.addSql("   CMN_LOG.LOG_OP_CODE,");
            sql.addSql("   CMN_LOG.LOG_OP_VALUE,");
            sql.addSql("   CMN_LOG.LOG_IP,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG");
            sql.addSql(" from");
            sql.addSql("   (CMN_LOG");
            sql.addSql("     left join CMN_USRM on CMN_LOG.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("     left join CMN_USRM_INF on CMN_LOG.USR_SID = CMN_USRM_INF.USR_SID)");

            //SQLのwhere句を生成
            sql = __createSql(searchMdl, sql);

            //SQLのオーダー句を生成
            sql = __createSqlOrder(searchMdl,  sql);

            //limit, offset
            sql.setPagingValue(searchMdl.getOffset(), searchMdl.getLimit());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCmnLogFromRs(rs, reqMdl));
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
     * <p>
     * オペレーションログ一覧(CSV用)を取得する
     * @param searchMdl 検索用モデル
     * @param rl LogCsvRecordListenerImpl
     * @param reqMdl リクエスト情報
     * @return count 件数
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV実行例外
     */
    public int getLogListForCsv(Man250SearchModel searchMdl,
            LogCsvRecordListenerImpl rl, RequestModel reqMdl)
    throws SQLException, CSVException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_LOG.LOG_DATE,");
            sql.addSql("   CMN_LOG.USR_SID,");
            sql.addSql("   CMN_LOG.LOG_LEVEL,");
            sql.addSql("   CMN_LOG.LOG_PLUGIN,");
            sql.addSql("   CMN_LOG.LOG_PLUGIN_NAME,");
            sql.addSql("   CMN_LOG.LOG_PG_ID,");
            sql.addSql("   CMN_LOG.LOG_PG_NAME,");
            sql.addSql("   CMN_LOG.LOG_OP_CODE,");
            sql.addSql("   CMN_LOG.LOG_OP_VALUE,");
            sql.addSql("   CMN_LOG.LOG_IP,");
            sql.addSql("   CMN_LOG.VER_VERSION,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI");
            sql.addSql(" from");
            sql.addSql("   (CMN_LOG");
            sql.addSql("     left join CMN_USRM_INF on CMN_LOG.USR_SID = CMN_USRM_INF.USR_SID)");

            //SQLのwhere句を生成
            sql = __createSql(searchMdl, sql);

            //SQLのオーダー句を生成
            sql = __createSqlOrder(searchMdl,  sql);

            pstmt = con.prepareStatement(sql.toSqlString());
            pstmt.setFetchSize(5000);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                count++;
                __getCmnLogFromRsForCsv(rs, rl, reqMdl);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>
     * 条件にマッチするオペレーションログを一括削除する。
     * @param searchMdl 検索用モデル
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(Man250SearchModel searchMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from");
            sql.addSql("   CMN_LOG");

            //SQLのwhere句を生成
            sql = __createSql(searchMdl, sql);

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>検索条件のSQLを取得する。
     * @param searchMdl 検索用モデル
     * @param sql SqlBuffer
     * @return sql 検索条件のSQL
     * @throws SQLException SQL実行例外
     */
    public SqlBuffer __createSql(Man250SearchModel searchMdl, SqlBuffer sql) throws SQLException {


        // SQL文
        sql.addSql(" where ");

        //実行日時
        sql.addSql("   CMN_LOG.LOG_DATE");
        sql.addSql("   between cast(? as timestamp) ");
        sql.addSql("   and cast(? as timestamp)");
        String dateFr = searchMdl.getDateFr().getDateStringForSql() + " "
                + UDateUtil.getSeparateHMS(searchMdl.getDateFr());
        String dateTo = searchMdl.getDateTo().getDateStringForSql() + " "
                + UDateUtil.getSeparateHMS(searchMdl.getDateTo());

        sql.addStrValue(dateFr);
        sql.addStrValue(dateTo);

        //プラグイン
        if (searchMdl.getPluginId() != null && !searchMdl.getPluginId().equals("-1")) {
            sql.addSql(" and");
            sql.addSql("   CMN_LOG.LOG_PLUGIN = ?");
            sql.addStrValue(searchMdl.getPluginId());
        }

        //ログレベル
        List<String> logLevelList = new ArrayList <String>();
        if (searchMdl.getLogLevelError().equals("1")) {
            logLevelList.add(GSConstLog.LEVEL_ERROR);
        }
        if (searchMdl.getLogLevelWarn().equals("1")) {
            logLevelList.add(GSConstLog.LEVEL_WARN);
        }
        if (searchMdl.getLogLevelInfo().equals("1")) {
            logLevelList.add(GSConstLog.LEVEL_INFO);
        }
        if (searchMdl.getLogLevelTrace().equals("1")) {
            logLevelList.add(GSConstLog.LEVEL_TRACE);
        }

        if (logLevelList.size() < 4) {
            int i = 0;
            sql.addSql(" and");
            sql.addSql("   CMN_LOG.LOG_LEVEL in (");
            for (String logLevel : logLevelList) {
                if (i > 0) {
                    sql.addSql(",");
                }
                sql.addSql("?");
                sql.addStrValue(logLevel);
                i++;
            }
            sql.addSql(") ");
        }

        //実行ユーザ
        if (searchMdl.getUsrSid() >= 0) {
            sql.addSql(" and");
            sql.addSql("   CMN_LOG.USR_SID = ?");
            sql.addIntValue(searchMdl.getUsrSid());
        }

        // 検索キーワード
        List<String> keyWordList = searchMdl.getKeyWord();
        if (keyWordList != null && keyWordList.size() > 0) {
            __cleateKeyWord(searchMdl, sql, keyWordList);
        }

        return sql;
    }

    /**
     * <br>[機  能] キーワード入力時の検索条件を作成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索用モデル
     * @param sql SqlBuffer
     * @param keyWordList 検索キーワード
     */
    private void __cleateKeyWord(Man250SearchModel searchMdl, SqlBuffer sql,
            List<String> keyWordList) {

        //キーワード検索結合条件
        String keyWordJoin = " and";
        if (searchMdl.getKeyWordKbn() == GSConstMain.KEY_WORD_KBN_OR) {
            keyWordJoin = " or";
        }

        //検索対象 「画面・機能名」
        if (searchMdl.isTartgetFunc()) {
            sql.addSql(" and");
            if (searchMdl.isTargetOperation()
                    || searchMdl.isTargetContent()
                    || searchMdl.isTargetIpaddress()) {
                sql.addSql("  (");
            }
            sql.addSql("    (");
            for (int i = 0; i < keyWordList.size(); i++) {
                if (i > 0) {
                    sql.addSql(keyWordJoin);
                }
                sql.addSql("    CMN_LOG.LOG_PG_NAME like '%"
                        + JDBCUtil.escapeForLikeSearch(keyWordList.get(i))
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }
            sql.addSql("    )");
        }

        //検索対象 「操作」
        if (searchMdl.isTargetOperation()) {
            if (searchMdl.isTartgetFunc()) {
                sql.addSql("    or");
            } else {
                sql.addSql(" and");
                if (!searchMdl.isTartgetFunc()
                        && (searchMdl.isTargetContent()
                        || searchMdl.isTargetIpaddress())) {
                    sql.addSql("  (");
                }
            }
            sql.addSql("    (");
            for (int i = 0; i < keyWordList.size(); i++) {
                if (i > 0) {
                    sql.addSql(keyWordJoin);
                }
                sql.addSql("    CMN_LOG.LOG_OP_CODE like '%"
                        + JDBCUtil.escapeForLikeSearch(keyWordList.get(i))
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }
            sql.addSql("    )");
            if (searchMdl.isTartgetFunc()
                    && !searchMdl.isTargetContent()
                    && !searchMdl.isTargetIpaddress()) {
                sql.addSql("  )");
            }
        }

        //検索対象 「内容」
        if (searchMdl.isTargetContent()) {
            if (searchMdl.isTartgetFunc() || searchMdl.isTargetOperation()) {
                sql.addSql("    or");
            } else {
                sql.addSql(" and");
                if (!searchMdl.isTartgetFunc()
                        && !searchMdl.isTargetOperation()
                        && searchMdl.isTargetIpaddress()) {
                    sql.addSql("  (");
                }
            }
            sql.addSql("    (");
            for (int i = 0; i < keyWordList.size(); i++) {
                if (i > 0) {
                    sql.addSql(keyWordJoin);
                }
                sql.addSql("    CMN_LOG.LOG_OP_VALUE like '%"
                        + JDBCUtil.escapeForLikeSearch(keyWordList.get(i))
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }
            sql.addSql("    )");
            if ((searchMdl.isTartgetFunc()
                    || searchMdl.isTargetOperation())
                    && !searchMdl.isTargetIpaddress()) {
                sql.addSql("  )");
            }
        }

        //検索対象 「IPアドレス」
        if (searchMdl.isTargetIpaddress()) {
            if (searchMdl.isTartgetFunc()
                    || searchMdl.isTargetOperation()
                    || searchMdl.isTargetContent()) {
                sql.addSql("    or");
            } else {
                sql.addSql(" and");
            }
            sql.addSql("    (");
            for (int i = 0; i < keyWordList.size(); i++) {
                if (i > 0) {
                    sql.addSql(keyWordJoin);
                }
                sql.addSql("    CMN_LOG.LOG_IP like '%"
                        + JDBCUtil.escapeForLikeSearch(keyWordList.get(i))
                        + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
            }
            sql.addSql("    )");
            if (searchMdl.isTartgetFunc()
                    || searchMdl.isTargetOperation()
                    || searchMdl.isTargetContent()) {
                sql.addSql("  )");
            }
        }
    }

    /**
     * <p>検索条件のSQLを取得する。
     * @param searchMdl 検索用モデル
     * @param sql SqlBuffer
     * @return sql 検索条件のSQL
     * @throws SQLException SQL実行例外
     */
    public SqlBuffer __createSqlOrder(Man250SearchModel searchMdl,  SqlBuffer sql)
    throws SQLException {

        //ソート順
        String orderStr = "";
        // オーダー
        if (searchMdl.getOrder() == GSConst.ORDER_KEY_ASC) {
            orderStr = "  asc";
        } else if (searchMdl.getOrder() == GSConst.ORDER_KEY_DESC) {
            orderStr = "  desc";
        }

        sql.addSql(" order by ");
        switch (searchMdl.getSortKey()) {
        case Man250Biz.SORT_KEY_DATE:
            sql.addSql("   CMN_LOG.LOG_DATE " + orderStr + ",");
            break;
        case Man250Biz.SORT_KEY_PLUGIN:
            sql.addSql("   CMN_LOG.LOG_PLUGIN_NAME " + orderStr + ",");
            break;
        case Man250Biz.SORT_KEY_LOG_LEVEL:
            sql.addSql("   case");
            sql.addSql("   when CMN_LOG.LOG_LEVEL = 'error' then '4'");
            sql.addSql("   when CMN_LOG.LOG_LEVEL = 'warn' then '3'");
            sql.addSql("   when CMN_LOG.LOG_LEVEL = 'info' then '2'");
            sql.addSql("   when CMN_LOG.LOG_LEVEL = 'trace' then '1'");
            sql.addSql("   else '0'");
            sql.addSql("   end ");
            sql.addSql(orderStr + ",");
            break;
        case Man250Biz.SORT_KEY_USR_NAME:
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN || CMN_USRM_INF.USI_MEI_KN ");
            sql.addSql(orderStr + ",");
            sql.addSql("   CMN_USRM_INF.USI_SEI || CMN_USRM_INF.USI_MEI ");
            sql.addSql(orderStr + ",");

        case Man250Biz.SORT_KEY_PG_NAME:
            sql.addSql("   CMN_LOG.LOG_PG_NAME " + orderStr + ",");
            break;
        case Man250Biz.SORT_KEY_VALUE:
            sql.addSql("   CMN_LOG.LOG_OP_VALUE " + orderStr + ",");
            break;
        case Man250Biz.SORT_KEY_OP_CODE:
            sql.addSql("   CMN_LOG.LOG_OP_CODE " + orderStr + ",");
            break;
        case Man250Biz.SORT_KEY_LOG_IP:
            sql.addSql("   CMN_LOG.LOG_IP " + orderStr + ",");
            break;
        default:
            break;
        }

        String orderStr2 = "";
        // オーダー
        if (searchMdl.getOrder2() == GSConst.ORDER_KEY_ASC) {
            orderStr2 = "  asc";
        } else if (searchMdl.getOrder2() == GSConst.ORDER_KEY_DESC) {
            orderStr2 = "  desc";
        }
        switch (searchMdl.getSortKey2()) {
        case Man250Biz.SORT_KEY_DATE:
            sql.addSql("   CMN_LOG.LOG_DATE " + orderStr2);
            break;
        case Man250Biz.SORT_KEY_PLUGIN:
            sql.addSql("   CMN_LOG.LOG_PLUGIN_NAME " + orderStr2);
            break;
        case Man250Biz.SORT_KEY_LOG_LEVEL:
            sql.addSql("   case");
            sql.addSql("   when CMN_LOG.LOG_LEVEL = 'error' then '4'");
            sql.addSql("   when CMN_LOG.LOG_LEVEL = 'warn' then '3'");
            sql.addSql("   when CMN_LOG.LOG_LEVEL = 'info' then '2'");
            sql.addSql("   when CMN_LOG.LOG_LEVEL = 'trace' then '1'");
            sql.addSql("   else '0'");
            sql.addSql("   end ");

            sql.addSql(orderStr2);
            break;
        case Man250Biz.SORT_KEY_USR_NAME:
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN || CMN_USRM_INF.USI_MEI_KN ");
            sql.addSql(orderStr2 + " , ");
            sql.addSql("   CMN_USRM_INF.USI_SEI || CMN_USRM_INF.USI_MEI ");
            sql.addSql(orderStr2);
            break;
        case Man250Biz.SORT_KEY_PG_NAME:
            sql.addSql("   CMN_LOG.LOG_PG_NAME " + orderStr2);
            break;
        case Man250Biz.SORT_KEY_VALUE:
            sql.addSql("   CMN_LOG.LOG_OP_VALUE " + orderStr2);
            break;
        case Man250Biz.SORT_KEY_OP_CODE:
            sql.addSql("   CMN_LOG.LOG_OP_CODE " + orderStr2);
            break;
        case Man250Biz.SORT_KEY_LOG_IP:
            sql.addSql("   CMN_LOG.LOG_IP " + orderStr2);
            break;
        default:
            break;
        }

        return sql;
    }

    /**
     * <p>検索結果を表示用モデルに設定する。
     * @param rs ResultSet
     * @param reqMdl リクエスト情報
     * @return created CmnLogModel
     * @throws SQLException SQL実行例外
     */
    private Man250DspModel __getCmnLogFromRs(ResultSet rs, RequestModel reqMdl)
                                                                 throws SQLException {
        Man250DspModel bean = new Man250DspModel();
        UDate logDate = UDate.getInstanceTimestamp(rs.getTimestamp("LOG_DATE"));
        bean.setLogDate(UDateUtil.getSlashYYMD(logDate) + " " + UDateUtil.getSeparateHMS(logDate));
        bean.setLogLevel(Man250Biz.getLogLevelDsp(rs.getString("LOG_LEVEL"), reqMdl));
        bean.setPluginName(rs.getString("LOG_PLUGIN_NAME"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsrNameSei(rs.getString("USI_SEI"));
        bean.setUsrNameMei(rs.getString("USI_MEI"));
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        bean.setPgName(StringUtilHtml.transToHTmlWithWbr(rs.getString("LOG_PG_NAME"), 15));
        bean.setOpCode(rs.getString("LOG_OP_CODE"));
        bean.setLogIp(rs.getString("LOG_IP"));
        bean.setValue(StringUtilHtml.transToHTmlWithWbr(rs.getString("LOG_OP_VALUE"), 50));
        return bean;
    }

    /**
     * <p>検索結果を表示用モデルに設定する。
     * @param rs ResultSet
     * @param rl LogCsvRecordListenerImpl
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     * @throws CSVException CSV実行例外
     */
    private void __getCmnLogFromRsForCsv(ResultSet rs,
                                         LogCsvRecordListenerImpl rl, RequestModel reqMdl)
    throws SQLException, CSVException {
        Man250DspModel bean = new Man250DspModel();
        UDate logDate = UDate.getInstanceTimestamp(rs.getTimestamp("LOG_DATE"));
        bean.setLogDate(UDateUtil.getSlashYYMD(logDate) + " " + UDateUtil.getSeparateHMS(logDate));
        bean.setLogLevel(Man250Biz.getLogLevelDsp(rs.getString("LOG_LEVEL"), reqMdl));
        bean.setPluginName(rs.getString("LOG_PLUGIN_NAME"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsrNameSei(rs.getString("USI_SEI"));
        bean.setUsrNameMei(rs.getString("USI_MEI"));
        bean.setPgName(rs.getString("LOG_PG_NAME"));
        bean.setValue(rs.getString("LOG_OP_VALUE"));
        bean.setOpCode(rs.getString("LOG_OP_CODE"));
        bean.setPgId(rs.getString("LOG_PG_ID"));
        bean.setLogIp(rs.getString("LOG_IP"));
        bean.setVersion(rs.getString("VER_VERSION"));
        rl.setRecord(bean);
    }

}
