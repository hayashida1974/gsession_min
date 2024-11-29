package jp.co.sjts.util.jdbc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.IDbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] SQL文をこのオブジェクトに格納することによって、ログへの出力、
 * PreparedStatementへのセット、面倒なパラメータの変換等を自動化することができます。
 * <br>[解  説]
 * <br>[備  考] addSql(String) で指定する文字列内に「?」は1個までとする。
 *              現在対応しているパラメータの型は、int、long、double、BigDecimal、String、UDateです。
 */
public class SqlBuffer {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(SqlBuffer.class);

    /** SQL格納用リスト */
    private ArrayList < String > sqlList__ = null;
    /** パラメータ格納用リスト */
    private ArrayList < Object > quesionList__ = null;
    /** 改行コード*/
    private static String newLine__ = "\r\n";
    /** レコード取得限度数 */
    private long limit__ = 0;
    /** レコード取得開始位置 */
    private long offset__ = 0;

    /**
     * [機 能] コンストラクタ<br>
     * [解 説] <br>
     * [備 考] <br>
     */
    public SqlBuffer() {
        sqlList__ = new ArrayList <String>();
        quesionList__ = new ArrayList <Object>();
    }

    /**
     * [機 能] SQL文を追加します。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param sql SQL文
     */
    public void addSql(String sql) {
        sqlList__.add(sql);
    }

    /**
     * [機 能] SQL実行用の文字列を取得します。(PreparedStatement用の「?」は「?」のままです)<br>
     * [解 説] <br>
     * [備 考] <br>
     * @return SQL実行用の文字列
     */
    public String toSqlString() {
        if (sqlList__.size() < 0) {
            return null;
        }
        String sql = new String();
        for (int i = 0; i < sqlList__.size(); i++) {
            sql = sql.concat((String) sqlList__.get(i));
        }

        sql = __addPagingSQL(sql, false);
        return sql;
    }

    /**
     * [機 能] 改行コードを付加したログ出力用のString文字列を取得します。<br>
     * [解 説] PreparedStatement用の「?」はaddValueで設定した値に置き換えられます。<br>
     * [備 考] <br>
     * @return ログ用の文字列
     */
    public String toLogString() {
        if (sqlList__.size() <= 0) {
            return null;
        }

        String st = new String();
        st = st.concat(newLine__);
        String str = null;
        String que = null;
        int index = 0;
        for (int i = 0; i < sqlList__.size(); i++) {
            if (quesionList__ != null && quesionList__.size() > 0) {
                que = __quesionToString(quesionList__.get(index));
                str = __replace((String) sqlList__.get(i), "?", que);
                if (!str.equals((String) sqlList__.get(i)) && index < quesionList__.size() - 1) {
                    index++;
                }
            } else {
                str = (String) sqlList__.get(i);
            }
            st = st.concat(str);
            st = st.concat(newLine__);
        }

        st = __addPagingSQL(st, true);
        return st;
    }

    /**
     * [機 能] addValue(***)で追加されたパラメータをログ出力用にStringへ変換します。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param obj addValue(***)で追加されたパラメータ
     * @return 変換後のパラメータ
     */
    private String __quesionToString(Object obj) {
        @SuppressWarnings("all")
        Class cs = obj.getClass();

        //条件の型が必要な場合追加します。
        String ret = new String();
        if (cs.getName().equals("java.lang.Integer")) {
            ret = ((Integer) obj).toString();
        } else if (cs.getName().equals("java.lang.Long")) {
            ret = ((Long) obj).toString();
        } else if (cs.getName().equals("java.lang.Double")) {
            ret = ((Double) obj).toString();
        } else if (cs.getName().equals("java.math.BigDecimal")) {
            ret = "'" + ((BigDecimal) obj).toString() + "'";
        } else if (cs.getName().equals("java.lang.String")) {
            ret = "'" + (String) obj + "'";
        } else if (cs.getName().equals(UDate.class.getName())) {
            ret = "'" + ((UDate) obj).toSQLTimestamp().toString() + "'";
        } else if (cs.getName().equals(Time.class.getName())) {
            ret = "'" + ((Time) obj).toString().toString() + "'";
        } else if (cs.getName().equals(SqlFile.class.getName())) {
            SqlFile sfile = (SqlFile) obj;
            ret = "'" + sfile.getFile().getAbsolutePath() + "'";
        } else if (cs.getName().equals(NullObject.class.getName())) {
            //Null
            ret = "null";
        } else { //対応外の場合
            ret = "null";
        }
        return ret;
    }

    /**
     * [機 能] 文字列内の任意の文字を指定した文字列に置き換えます。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param base 元となる文字列
     * @param target 対象文字
     * @param replace 置き換える文字
     * @return 置き換え後の文字列
     */
    private String __replace(String base, String target, String replace) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < base.length(); i++) {
            if (target.equals(base.substring(i, i + 1))) {
                buf.append(replace);
            } else {
                buf.append(base.substring(i, i + 1));
            }
        }
        return buf.toString();
    }

    /**
     * <br>[機  能] addValueメソットで設定されたパラメータをPreparedStatementへ反映します。
     * <br>[解  説]
     * <br>[備  考]
     * @param pstmt PreparedStatement
     * @return PreparedStatement
     * @throws SQLException SQL
     */
    public PreparedStatement setParameter(PreparedStatement pstmt) throws SQLException {
        if (sqlList__.size() <= 0 || pstmt == null) {
            return null;
        }
        try {
            int index = 0;
            for (int i = 0; i < quesionList__.size(); i++) {
                index++;
                Object obj = quesionList__.get(i);
                @SuppressWarnings("all")
                Class cs = obj.getClass();

                //条件の型が必要な場合追加します。
                if (cs.getName().equals("java.lang.Integer")) {
                     pstmt.setInt(index, ((Integer) obj).intValue());
                } else if (cs.getName().equals("java.lang.Long")) {
                    pstmt.setLong(index, ((Long) obj).longValue());
                } else if (cs.getName().equals("java.lang.Double")) {
                    pstmt.setDouble(index, ((Double) obj).doubleValue());
                } else if (cs.getName().equals("java.math.BigDecimal")) {
                    pstmt.setBigDecimal(index, (BigDecimal) obj);
                } else if (cs.getName().equals("java.lang.String")) {
                    pstmt.setString(index, (String) obj);
                } else if (cs.getName().equals(UDate.class.getName())) {
                    pstmt.setTimestamp(index, JDBCUtil.getTimestamp((UDate) obj));
                } else if (cs.getName().equals(Time.class.getName())) {
                    pstmt.setTime(index, JDBCUtil.getTime((Time) obj));
                } else if (cs.getName().equals(SqlFile.class.getName())) {
                    SqlFile sfile = (SqlFile) obj;
                    InputStream fis = sfile.toInputStream();
                    int length = (int) sfile.getFile().length();
                    pstmt.setBinaryStream(index, fis, (int) length);
                } else if (cs.getName().equals(NullObject.class.getName())) {
                    //NULL
                    NullObject nobj = (NullObject) obj;
                    if (nobj.getType().equals("java.math.BigDecimal")) {
                        pstmt.setNull(index, Types.BIGINT);
                    } else if (nobj.getType().equals("java.lang.String")) {
                        pstmt.setNull(index, Types.VARCHAR);
                    } else if (nobj.getType().equals(UDate.class.getName())) {
                        pstmt.setNull(index, Types.TIMESTAMP);
                    } else if (nobj.getType().equals(Time.class.getName())) {
                        pstmt.setNull(index, Types.TIME);
                    }
                } else {
                    pstmt.setString(index, "null");
                }
            }
        } catch (IOException e) {
            log__.error("IOエラー" + e.getMessage());
            throw new SQLException("IOエラー");
        } catch (SQLException e) {
            log__.error("setParameter.SQLException" + e.getMessage());
            throw e;
        }

        return pstmt;
    }

//条件の型が必要な場合追加します。
    /**
     * int型の条件を追加します。
     * @param value int型の条件
     */
    public void addIntValue(int value) {
        quesionList__.add(new Integer(value));
    }
    /**
     * long型の条件を追加します。
     * @param value long型の条件
     */
    public void addLongValue(long value) {
        quesionList__.add(new Long(value));
    }
    /**
     * double型の条件を追加します。
     * @param value double型の条件
     */
    public void addDoubleValue(double value) {
        quesionList__.add(new Double(value));
    }
    /**
     * BigDecimal型の条件を追加します。
     * @param value BigDecimal型の条件
     */
    public void addDecimalValue(BigDecimal value) {
        if (value == null) {
            quesionList__.add(new NullObject("java.math.BigDecimal"));
        } else {
            quesionList__.add(value);
        }
    }
    /**
     * String型の条件を追加します。
     * @param value String型の条件
     */
    public void addStrValue(String value) {
        if (value == null) {
            quesionList__.add(new NullObject("java.lang.String"));
        } else {
            quesionList__.add(__escapeValue(value));
        }
    }
    /**
     * UDate型の条件を追加します。
     * @param value UDate型の条件
     */
    public void addDateValue(UDate value) {
        if (value == null) {
            quesionList__.add(new NullObject(UDate.class.getName()));
        } else {
            quesionList__.add(value);
        }
    }
    /**
     * java.sql.Time型の条件を追加します。
     * @param value java.sql.Time型の条件
     */
    public void addTimeValue(Time value) {
        if (value == null) {
            quesionList__.add(new NullObject(Time.class.getName()));
        } else {
            quesionList__.add(value);
        }
    }

    /**
     * File型の条件を追加します。
     * @param value SqlFile型の条件
     */
    public void addFileValue(SqlFile value) {
        if (value == null) {
            quesionList__.add(new NullObject(File.class.getName()));
        } else {
            quesionList__.add(value);
        }
    }
//    /**
//     * Null値をセットします。
//     */
//    public void addNullValue() {
//        quesionList__.add(new NullObject(""));
//    }

    /**
     * バリュー値のリストを初期化します。
     */
    public void clearValue() {
        if (quesionList__ != null) {
            quesionList__.clear();
            setPagingValue(0, 0);
        }
    }

    /**
     * <p>limit を取得します。
     * @return limit
     */
    public long getLimit() {
        return limit__;
    }
    /**
     * <p>offset を取得します。
     * @return offset
     */
    public long getOffset() {
        return offset__;
    }
    /**
     * <br>[機  能] limit と offset を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param offset レコード取得開始位置
     * @param limit レコード取得限度数
     */
    public void setPagingValue(int offset, int limit) {
        offset__ = (long) offset;
        limit__ = (long) limit;
    }
    /**
     * <br>[機  能] limit と offset を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param offset レコード取得開始位置
     * @param limit レコード取得限度数
     */
    public void setPagingValue(long offset, long limit) {
        offset__ = offset;
        limit__ = limit;
    }

    /**
     * <br>[機  能] ページング処理SQL文字列を指定した文字列へ追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql 文字列
     * @param newLine true:改行を行う false: 改行を行わない
     * @return SQL文字列
     */
    private String __addPagingSQL(String sql, boolean newLine) {

        if (!StringUtil.isNullZeroString(sql) && getLimit()  > 0) {

            IDbUtil dbUtil = DBUtilFactory.getInstance();
            if (dbUtil.getDbType() != GSConst.DBTYPE_ORACLE) {
                sql = sql.concat("  limit ").concat(String.valueOf(getLimit()))
                                .concat(" offset ").concat(String.valueOf(getOffset()));
            } else {
                String newLineStr = "";
                if (newLine) {
                    newLineStr = newLine__;
                }

                String headSql = " select ORA_DATA_NUM.* from (".concat(newLineStr);
                headSql = headSql.concat("   select").concat(newLineStr);
                headSql = headSql.concat("     ORA_DATA.*,").concat(newLineStr);
                headSql = headSql.concat("     ROWNUM as ORA_DATA_ROW_NUMBER").concat(newLineStr);
                headSql = headSql.concat("   from").concat(newLineStr);
                headSql = headSql.concat("    (").concat(newLineStr);

                sql = headSql.concat(sql);
                sql = sql.concat("    ) ORA_DATA").concat(newLineStr);
                sql = sql.concat(" ) ORA_DATA_NUM").concat(newLineStr);
                sql = sql.concat(" where ORA_DATA_NUM.ORA_DATA_ROW_NUMBER between ");
                sql = sql.concat(String.valueOf(getOffset() + 1));
                sql = sql.concat(" and ");
                sql = sql.concat(String.valueOf(getLimit() + getOffset())).concat(newLineStr);
            }
        }

        return sql;
    }

    /**
     * <br>[機  能] postgreSQL 更新用に、引数で指定した文字列中にあるASCII、UTF-8 以外の文字を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param item チェック文字列
     * @return 変換後文字列
     */
    private String __escapeValue(String item) {
        if (StringUtil.isNullZeroString(item)) {
            return item;
        }

        StringBuilder sb = new StringBuilder();
        char[] c = item.toCharArray();

        for (int i = 0; i < c.length; i++) {
            //ASCIIコード判定
            if (StringUtil.isAscii(c[i])) {
                sb.append(String.valueOf(c[i]));
            } else {
                //UTF-8判定
                if (Character.isHighSurrogate(c[i])) {
                    if (c.length < i + 1) {
                        //下位サロゲートがない不正な文字
                    } else {
                        //サロゲートペアを返す
                        char[] cs = new char[] {c[i], c[i + 1]};
                        String surrogatePair = new String(cs);
                        //surrogatePair += String.valueOf(c[i + 1]);
                        sb.append(surrogatePair);
                    }
                } else if (Character.isLowSurrogate(c[i])) {
                    //下位サロゲートは無視
                } else {
                    if (StringUtil.isUnicode(c[i])) {
                        if ((new Character(c[i])).equals(new Character((char) 0x0000))) {
                            log__.debug("character is 0x0000");
                            sb.append(" ");
                        } else {
                            sb.append(String.valueOf(c[i]));
                        }
                    }
                }
            }
        }

        return sb.toString();
    }
}