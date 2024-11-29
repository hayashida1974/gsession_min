/*
 * create   : 2004/07/30
 * modified :
 */
package jp.groupsession.v2.dba.core;

import java.sql.Types;

import jp.groupsession.v2.dba.meta.Column;
import jp.groupsession.v2.dba.meta.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] H2用のSqlGenerator
 * <br>[解  説]
 * <br>[備  考] 現段階ではH2固有の処理は実装されていない
 * @author JTS
 */
public class SqlGeneratorH2 extends SqlGeneratorDefault {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SqlGeneratorH2.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public SqlGeneratorH2() {
    }

    /**
     * <p>テーブル情報をセットします
     * @param table テーブル
     */
    public SqlGeneratorH2(Table table) {
        super(table);
    }

    /**
     * <p>列情報からCreatetable用の列タイプ文字列を生成します。
     * @param col 列情報
     * @return 列タイプ文字列
     */
    protected String _getTypeStr(Column col) {
        String ret = null;
        int type = col.getType();
        int size = col.getSize();

        switch (type) {
            case Types.ARRAY :
                log__.debug("ARRAY");
            case Types.BIGINT :
                log__.debug("BIGINT");
                ret = "bigint";
                break;

            case Types.BINARY :
                log__.debug("BINARY");
            case Types.BIT :
                log__.debug("BIT");
            case Types.BLOB :
                log__.debug("BLOB");
                ret = "blob";
                break;

            case Types.BOOLEAN :
                log__.debug("BOOLEAN");
            case Types.CHAR :
                log__.debug("CHAR");
            case Types.CLOB :
                log__.debug("CLOB");
                ret = "clob";
                break;

            case Types.DATALINK :
                log__.debug("DATALINK");
            case Types.DATE :
                ret = "Date";
                log__.debug("DATE");
                break;

            case Types.DECIMAL :
                log__.debug(Types.DECIMAL + "DECIMAL");
                ret = "decimal";
                break;

            case Types.DISTINCT :
                log__.debug(Types.DISTINCT + " DISTINCT");
            case Types.DOUBLE :
                log__.debug(Types.DOUBLE + " DOUBLE");
                ret = "double";
                break;
            case Types.FLOAT :
                log__.debug(Types.FLOAT + " FLOAT");
            case Types.INTEGER : //3
                log__.debug(Types.INTEGER + " INTEGER");
                ret = "integer";
                break;

            case Types.JAVA_OBJECT :
                log__.debug("JAVA_OBJECT");
            case Types.LONGVARBINARY :
                log__.debug("LONGVARBINARY");
            case Types.LONGVARCHAR :
                log__.debug("LONGVARCHAR");
            case Types.NULL :
                log__.debug("NULL");
            case Types.NUMERIC :
                log__.debug("NUMERIC");
            case Types.OTHER :
                log__.debug("OTHER");
            case Types.REAL :
                log__.debug("REAL");
            case Types.REF :
                log__.debug("REF");
            case Types.SMALLINT :
                log__.debug("SMALLINT");
            case Types.STRUCT :
                log__.debug("STRUCT");
            case Types.TIME :
                log__.debug("TIME");
                ret = "time";
                break;

            case Types.TIMESTAMP :
                log__.debug("TIMESTAMP");
                ret = "timestamp";
                break;

            case Types.TINYINT :
                log__.debug("TINYINT");
            case Types.VARBINARY :
                log__.debug("VARBINARY");
            case Types.VARCHAR :
                log__.debug("VARCHAR");
                ret = "varchar(" + size + ")";
                break;
            default :
                log__.debug("UNKNOWN");
                ret = "UNKNOWN";
        }

        //Not Null
        String notNull = "";
        if (java.sql.DatabaseMetaData.columnNoNulls == col.getNullable()) {
            notNull = " not null";
        }
        ret += notNull;

        log__.debug(col.getName() + " type = " + ret + "| " + col.getType());
        log__.debug(col.getName() + " size = " + size);

        return ret;
    }
}
