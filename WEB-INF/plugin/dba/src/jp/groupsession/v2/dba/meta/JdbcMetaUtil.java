/*
 * 作成日: 2004/07/30
 */
package jp.groupsession.v2.dba.meta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Types;

/**
 * <br>[機  能] JDBCのメタデータに関するユーティリティ
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class JdbcMetaUtil {

    /**
     * <p>ワイルドカード文字をエスケープするのに使用できる文字列を取得します。
     * @param con コネクション
     * @return エスケープ文字列
     * @throws SQLException SQL例外
     */
    public static String getSearchStringEscape(Connection con) throws SQLException {
        DatabaseMetaData meta = con.getMetaData();
        String esc = meta.getSearchStringEscape();
        return esc;
    }

    /**
     * <p>DBの製品名を返します
     * @param con コネクション
     * @return 製品名
     * @throws SQLException SQL例外
     */
    public static String getDbProductName(Connection con) throws SQLException {
        DatabaseMetaData meta = con.getMetaData();
        String name = meta.getDatabaseProductName();
        return name;
    }

    /**
     * <p>タイプ定数からJDBC標準のデータ型Stringを返します。
     * @param type タイプ
     * @return データ型
     */
    public static String getTypeNameJdbcStandard(int type) {
        String ret = "UNKNOWN";
        switch (type) {
            case Types.ARRAY :
                ret = "ARRAY";
                break;

            case Types.BIGINT :
                ret = "BIGINT";
                break;

            case Types.BINARY :
                ret = "BINARY";
                break;

            case Types.BIT :
                ret = "BIT";
                break;

            case Types.BLOB :
                ret = "BLOB";
                break;

            case Types.BOOLEAN :
                ret = "BOOLEAN";
                break;

            case Types.CHAR :
                ret = "CHAR";
                break;

            case Types.CLOB :
                ret = "CLOB";
                break;

            case Types.DATALINK :
                ret = "DATALINK";
                break;

            case Types.DATE :
                ret = "DATE";
                break;

            case Types.DECIMAL :
                ret = "DECIMAL";
                break;

            case Types.DISTINCT :
                ret = "DISTINCT";
                break;

            case Types.DOUBLE :
                ret = "DOUBLE";
                break;

            case Types.FLOAT :
                ret = "FLOAT";
                break;

            case Types.INTEGER :
                ret = "INTEGER";
                break;

            case Types.JAVA_OBJECT :
                ret = "JAVA_OBJECT";
                break;

            case Types.LONGVARBINARY :
                ret = "LONGVARBINARY";
                break;

            case Types.LONGVARCHAR :
                ret = "LONGVARCHAR";
                break;

            case Types.NULL :
                ret = "NULL";
                break;

            case Types.NUMERIC :
                ret = "NUMERIC";
                break;

            case Types.OTHER :
                ret = "OTHER";
                break;

            case Types.REAL :
                ret = "REAL";
                break;

            case Types.REF :
                ret = "REF";
                break;

            case Types.SMALLINT :
                ret = "SMALLINT";
                break;

            case Types.STRUCT :
                ret = "STRUCT";
                break;

            case Types.TIME :
                ret = "TIME";
                break;

            case Types.TIMESTAMP :
                ret = "TIMESTAMP";
                break;

            case Types.TINYINT :
                ret = "TINYINT";
                break;

            case Types.VARBINARY :
                ret = "VARBINARY";
                break;

            case Types.VARCHAR :
                ret = "VARCHAR";
                break;
            default:
                ret = "UNKNOWN";
        }
        return ret;
    }
}
