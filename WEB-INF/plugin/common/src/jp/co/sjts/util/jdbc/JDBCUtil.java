package jp.co.sjts.util.jdbc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;


/**
 * <br>[機  能] JDBCのアクセスに必要となる基本処理を実装
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class JDBCUtil {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(JDBCUtil.class);

    /** デフォルトのエスケープ文字 */
    public static String def_esc = "\\";
    /** like検索時のデフォルトエスケープ文字列(置換前) */
    public static String def_like_esc = "\\";
    /** like検索時のデフォルトエスケープ文字列(置換後) */
    public static String def_like_esc_after = "\\\\";

    /**
     * <br>[機  能] コネクションをクローズします。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
        }
    }

    /**
     * <br>[機  能] ステートメントをクローズします。
     * <br>[解  説]
     * <br>[備  考]
     * @param stmt ステートメント
     */
    public static void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
        }
    }

    /**
     * <br>[機  能] PreparedStatementをクローズします。
     * <br>[解  説]
     * <br>[備  考]
     * @param pstmt PreparedStatement
     */
    public static void closePreparedStatement(PreparedStatement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
        }
    }

    /**
     * <br>[機  能] 結果セットを閉じます
     * <br>[解  説]
     * <br>[備  考]
     * @param rs 結果セット
     */
    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
        }
    }

    /**
     * <br>[機  能] ロールバックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public static void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException e) {
        }
    }

//    /**
//     * <br>[機  能] コミットを行います
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param con コネクション
//     */
//    public static void commit(Connection con) {
//        try {
//            if (con != null) {
//                con.commit();
//            }
//        } catch (SQLException e) {
//        }
//    }

    /**
     * <br>[機  能] 引数で指定した単語をSQLで使用できる形式に加工します。
     * <br>[解  説]
     * <br>[備  考]
     * @param   strItem 処理したい文字列
     * @return  strItem を処理した文字列
     */
    public static String encString(String strItem) {

        //文字列が0の時はそのまま返す
        if (strItem.length() == 0) {
            return "";
        }
        // 文字列中に「'」がなければそのまま返す
        if (strItem.indexOf("\'") < 0) {
            return strItem;
        }

        //文字列変換部分
        StringBuilder strBuf = new StringBuilder();
        for (int intCnt = 0; intCnt < strItem.length(); intCnt++) {
            char ch = strItem.charAt(intCnt);
            if ('\'' == ch) {
                strBuf.append("''");
            } else {
                strBuf.append(ch);
            }
        }

        return new String(strBuf);
    }

    /**
     * <br>[機  能] SQL Like句に指定する検索文字列のエスケープを行う
     * <br>[解  説] 変更対象：検索文字列
     * <br>         変更内容：LIKE制御文字エスケープ
     * <br>         変更範囲：文字列中の「%」「'」「_」
     * <br>[備  考]
     * @param   strItem 処理したい文字列
     * @return  strItem を処理した文字列
     */
    public static String escapeForLikeSearch(String strItem) {
        return escapeForLikeSearch(strItem, def_like_esc);
    }

    /**
     * <br>[機  能] SQL Like句に指定する検索文字列のエスケープを行う
     * <br>[解  説] 変更対象：検索文字列
     * <br>         変更内容：LIKE制御文字エスケープ
     * <br>         変更範囲：文字列中の「%」「'」「_」
     * <br>[備  考]
     * @param strItem 処理したい文字列
     * @param esc エスケープする文字列
     * @return  strItem を処理した文字列
     */
    public static String escapeForLikeSearch(String strItem, String esc) {

        //文字列が0の時はそのまま返す
        if (strItem == null || strItem.length() == 0) {
            return strItem;
        }
        String encStr = strItem.toString();
        if (strItem.indexOf(esc) >= 0) {
            //文字列変換部分
            StringBuilder strBuf = new StringBuilder();
            for (int intCnt = 0; intCnt < strItem.length(); intCnt++) {
                char ch = strItem.charAt(intCnt);
                if (esc.charAt(0) == ch) {
                    strBuf.append(def_like_esc_after);
                } else {
                    strBuf.append(ch);
                }
            }
            encStr = strBuf.toString();
        }
        //文字列が0の時はそのまま返す
        if (encStr.length() == 0) {
            return "";
        }
        // 文字列中に「'」「%」がなければそのまま返す
        if (
            (encStr.indexOf("\'") < 0)
            &&
            (encStr.indexOf("%") < 0)
            &&
            (encStr.indexOf("_") < 0)
        ) {
            return encStr;
        }

        //文字列変換部分
        StringBuilder strBuf = new StringBuilder();
        for (int intCnt = 0; intCnt < encStr.length(); intCnt++) {
            char ch = encStr.charAt(intCnt);
            if ('\'' == ch) {
                strBuf.append("''");
            } else if ('%' == ch) {
                strBuf.append(esc);
                strBuf.append(ch);
            } else if ('_' == ch) {
                strBuf.append(esc);
                strBuf.append(ch);
            } else {
                strBuf.append(ch);
            }
        }
        return new String(strBuf);
    }

    /**
     * <br>[機  能] UDateからjava.sql.Dateを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param udate UDate型の日付
     * @return java.sql.Date型の日付
     */
    public static Date getDate(UDate udate) {

        Date date = null;
        if (udate != null) {
            date = udate.getSQLDate();
        }
        return date;
    }

    /**
     * <br>[機  能] SQL結果からUDateを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs SQL結果
     * @param itemName 項目名
     * @return UDate
     * @exception SQLException DB実行例外の場合にスローする
     */
    public static UDate getUDateRS(ResultSet rs, String itemName)
        throws SQLException {

        UDate udate = null;
        if (rs.getDate(itemName) != null) {
            udate = UDate.getInstanceSqlDate(rs.getDate(itemName));
        }
        return udate;
    }

    /**
     * <br>[機  能] UDateからjava.sql.Timestampを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param udate UDate型の日付
     * @return java.sql.Date型の日付
     */
    public static Timestamp getTimestamp(UDate udate) {
        Timestamp stamp = null;
        if (udate != null) {
            stamp = udate.toSQLTimestamp();
        }
        return stamp;
    }
    /**
     * <br>[機  能] Timeからjava.sql.Timestampを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param time Time型の時間
     * @return java.sql.Time型の時間
     */
    public static Time getTime(Time time) {
        Time stamp = null;
        if (time != null) {
            stamp = time;
        }
        return stamp;
    }
    /**
     * <br>[機  能] SQL結果からTimestampを返します。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs SQL結果
     * @param itemName 項目名
     * @return UDate
     * @exception SQLException DB実行例外の場合にスローする
     */
    public static UDate getTimestampRS(ResultSet rs, String itemName)
        throws SQLException {
        UDate udate = null;
        if (rs.getTimestamp(itemName) != null) {
            udate = UDate.getInstance(rs.getTimestamp(itemName).getTime());
        }
        return udate;
    }

    /**
     * <br>[機  能] Blobの内容を指定されたファイルに書き込みます。
     * <br>[解  説]
     * <br>[備  考]
     * @param blob Blob
     * @param filePath ファイル
     * @param bufSize ファイル書き込み時のバッファサイズ
     * @exception IOException ファイルの書き込みに失敗
     * @exception SQLException DB実行例外の場合にスローする
     */
    public static void writeBlob(Blob blob, File filePath, int bufSize)
        throws IOException, SQLException {

        // ディレクトリを作成
        File dirPath = new File(filePath.getParent());
        if (!dirPath.exists() && !dirPath.mkdirs()) {
            throw new IOException("directory " + dirPath.getPath() + " create failed");
        }

        BufferedInputStream bi = null;
        FileOutputStream fo = null;

        try {
            fo = new FileOutputStream(filePath);
            bi = new BufferedInputStream(blob.getBinaryStream());

            long mapLen = blob.length();
            if (bufSize > mapLen) {
                bufSize = new Long(mapLen).intValue();
            }

            byte[] buf = new byte[bufSize];

            int length = -1;
            while ((length = bi.read(buf)) != -1) {
                fo.write(buf, 0, length);
            }
            fo.flush();
        } finally {
            try {
                bi.close();
                fo.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * <p>データソースよりコネクションを取得する。
     * 3回リトライし、失敗した場合はSQLExceptionをスローする。
     * <p>
     * またリトライ時は3秒間処理を待ち、リトライする。
     * @param ds データソース
     * @return Connection
     * @exception SQLException コネクションの取得に失敗した場合にスロー
     */
    public static Connection getConnection(DataSource ds) throws SQLException {
        long milisec = 3000;
        return getConnection(ds, milisec);
    }

    /** コネクション 使用率上限 */
    private static final int CON_LIMITRATE__ = 50;

    /**
     * <br>[機  能] データソースよりコネクションを取得する。
     * <br>3回リトライし、失敗した場合はSQLExceptionをスローする。
     * <br>またリトライ時は指定ミリ秒間処理を待ち、リトライする。
     * <br>ただし、コネクションの使用率が規定を超えていた場合はリトライを行わずにConnectionExceptionをスローする。
     * <br>[解  説]
     * <br>[備  考]
     * @param ds データソース
     * @param milisec リトライまでの待ち時間(ミリ秒)
     * @return Connection
     * @exception SQLException コネクションの取得に失敗した場合にスロー
     * @exception DBConnectionException コネクションの取得に失敗した際、コネクションの使用率が規定を超えていた場合にスロー
     */
    public static Connection getConnection(DataSource ds, long milisec)
    throws SQLException, DBConnectionException {
        UDate startTime = new UDate();
        UDate endTime = null;
        Connection con = null;

        //コネクション数取得(1回目)
        int[] conCnts = __getConCnt(ds);
        int aconcnt = conCnts[0];
        int iconcnt = conCnts[1];

        // 1回目
        try {
            con = ds.getConnection();
            if (log__.isDebugEnabled()) {
                endTime = new UDate();
                long msecond = UDateUtil.diffMillis(startTime, endTime);
                log__.debug("コネクション取得時間(ミリ秒) " + msecond);
            }

            return con;
        } catch (SQLException e) {
            log__.error("コネクションの取得に失敗(1回目)", e);
            log__.error("現在のコネクション状況(1回目): ACTIVE=" + aconcnt + " IDLE=" + iconcnt);

            int maxtconcnt = conCnts[2];
            BigDecimal conRate = new BigDecimal(aconcnt);
            conRate = conRate.multiply(new BigDecimal(100));
            conRate = (conRate.divide(new BigDecimal(maxtconcnt), 0, BigDecimal.ROUND_DOWN));
            if (conRate.intValue() > CON_LIMITRATE__) {
                throw new DBConnectionException("コネクション使用率が上限を超えたため取得処理を終了:"
                                                        + " ACTIVE=" + aconcnt
                                                        + " MAXTOTAL=" + maxtconcnt
                                                        + " IDLE=" + iconcnt
                                                        + " 使用率(%)=" + conRate.toPlainString(), e);
            }
        }

        __sleep(milisec);
        //コネクション数取得(2回目)
        conCnts = __getConCnt(ds);
        aconcnt = conCnts[0];
        iconcnt = conCnts[1];
        try {
            // 2回目
            con = ds.getConnection();
            if (log__.isDebugEnabled()) {
                endTime = new UDate();
                long msecond = UDateUtil.diffMillis(startTime, endTime);
                log__.debug("コネクション取得時間(ミリ秒) " + msecond);
            }
            return con;
        } catch (SQLException e) {
            log__.error("コネクションの取得に失敗(2回目)", e);
            log__.error("現在のコネクション状況(2回目): ACTIVE=" + aconcnt + " IDLE=" + iconcnt);
        }

        __sleep(milisec);
        //コネクション数取得(3回目)
        conCnts = __getConCnt(ds);
        aconcnt = conCnts[0];
        iconcnt = conCnts[1];
        try {
            // 3回目
            con = ds.getConnection();
            if (log__.isDebugEnabled()) {
                endTime = new UDate();
                long msecond = UDateUtil.diffMillis(startTime, endTime);
                log__.debug("コネクション取得時間(ミリ秒) " + msecond);
            }
            return con;
        } catch (SQLException e) {
            log__.fatal("コネクションの取得に失敗(3回目)", e);
            log__.fatal("現在のコネクション状況(3回目): ACTIVE=" + aconcnt + " IDLE=" + iconcnt);
            throw e;
        }

    }
    /**
     * <br>[機  能] コネクション状況を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ds データソース
     * @return index[0]:ACTIVE数  index[1]:IDLE数
     */
    public static int[] getConCnt(DataSource ds) {
        return __getConCnt(ds);
    }

    /**
     * <br>[機  能] コネクション状況を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ds データソース
     * @return index[0]:ACTIVE数  index[1]:IDLE数
     */
    private static int[] __getConCnt(DataSource ds) {

        int aconcnt = -1;
        int iconcnt = -1;
        int maxtconcnt = -1;

        if (ds instanceof org.apache.commons.dbcp2.BasicDataSource) {
            //
            org.apache.commons.dbcp2.BasicDataSource bds
                = (org.apache.commons.dbcp2.BasicDataSource) ds;
            aconcnt = bds.getNumActive();
            iconcnt = bds.getNumIdle();
            maxtconcnt = bds.getMaxTotal();
            log__.info("現在のコネクション状況: ACTIVE=" + aconcnt + " IDLE=" + iconcnt);
        } else {
            log__.debug("DataSourceクラス" + ds.getClass().getCanonicalName());
        }

        int [] ret = {aconcnt, iconcnt, maxtconcnt};
        return ret;
    }

    /**
     * <p>処理を指定ミリ秒またせるメソッド。
     * milisecに3000を指定した場合3秒待ち
     * @param milisec ミリ秒
     */
    private static void __sleep(long milisec) {
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            log__.error(milisec + "ミリ秒の待ち処理に失敗", e);
        }
    }

    /**
     * <br>[機  能] コネクションをクローズし、nullを代入
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public static void closeConnectionAndNull(Connection con) {
        closeConnection(con);
        con = null;
    }

    /**
     * <br>[機  能] 指定された文字列をH2DatabaseのLucene全文検索で使用できる形式に加工します。
     * <br>[解  説]
     * <br>[備  考]
     * @param strItem 処理したい文字列
     * @return 加工後の文字列
     */
    public static String encFullStringH2Lucene(String strItem) {

        //文字列が0の時はそのまま返す
        if (strItem == null || strItem.length() == 0) {
            return strItem;
        }


        String encStr = strItem.toString();
        //文字列変換部分
        StringBuilder strBuf = new StringBuilder();
        for (int intCnt = 0; intCnt < strItem.length(); intCnt++) {
            char ch = strItem.charAt(intCnt);
            if (ch == '+'
            || ch == '-'
            || ch == '!'
            || ch == '('
            || ch == ')'
            || ch == '{'
            || ch == '}'
            || ch == '['
            || ch == ']'
            || ch == '^'
            || ch == '\"'
            || ch == '~'
            || ch == '*'
            || ch == '?'
            || ch == ':'
            || ch == '\\'
            || ch == '%'
            || ch == '_') {
                strBuf.append("\\");
                strBuf.append(ch);

            } else if (ch == '\'') {
                strBuf.append("\'");
                strBuf.append(ch);

            } else if (intCnt + 1 < strItem.length()
                    && ((ch == '&' && strItem.charAt(intCnt + 1) == '&')
                            || (ch == '|' && strItem.charAt(intCnt + 1) == '|'))) {
                    strBuf.append("\\");
                    strBuf.append(ch);
            } else {
                strBuf.append(ch);
            }
        }

        encStr = strBuf.toString();

        if (encStr.indexOf(" ") >= 0 || encStr.indexOf("　") >= 0
        || encStr.equals("AND") || encStr.endsWith("OR")) {
            encStr = "\"" + encStr + "\"";
        }

        return encStr;
    }

    /**
     * <br>[機  能] 指定された文字列を全文検索で使用できる形式に加工します。
     * <br>[解  説]
     * <br>[備  考]
     * @param strItem 処理したい文字列
     * @return 加工後の文字列
     */
    public static String encFullTextSearch(String strItem) {

        //文字列が0の時はそのまま返す
        if (strItem == null || strItem.length() == 0) {
            return strItem;
        }


        String encStr = strItem.toString();
        //文字列変換部分
        StringBuilder strBuf = new StringBuilder();
        for (int intCnt = 0; intCnt < strItem.length(); intCnt++) {
            char ch = strItem.charAt(intCnt);
            if (ch == '\\'
                || ch == '('
                || ch == ')'
                || ch == '\"'
                || ch == '\'') {
                strBuf.append("\\");
                strBuf.append(ch);
            } else {
                strBuf.append(ch);
            }
        }

        encStr = strBuf.toString();

        return encStr;
    }

    /**
     * <br>[機  能] 指定されたConnectionのautoCommitが無効(false)の場合、有効(true)に設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException autoCommitの設定時に例外発生
     */
    public static void autoCommitOn(Connection con) throws SQLException {
        if (con != null && !con.isClosed() && !con.getAutoCommit()) {
            con.setAutoCommit(true);
        }
    }

    /**
     * <br>[機  能] 指定されたConnectionのautoCommitが有効(true)の場合、無効(false)に設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException autoCommitの設定時に例外発生
     */
    public static void autoCommitOff(Connection con) throws SQLException {
        if (con != null && !con.isClosed() && con.getAutoCommit()) {
            con.setAutoCommit(false);
        }
    }
}
