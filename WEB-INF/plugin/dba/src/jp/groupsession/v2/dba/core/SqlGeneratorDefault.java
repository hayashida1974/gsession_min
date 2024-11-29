/*
 * create   : 2004/07/30
 * modified :
 */
package jp.groupsession.v2.dba.core;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.dba.meta.Column;
import jp.groupsession.v2.dba.meta.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] デフォルトSQL Generator
 * <br>[解  説] DB製品毎に作成する場合は、このクラスを継承しメソッドをオーバーライドして使用する。
 * <br>[備  考]
 * @author JTS
 */
public class SqlGeneratorDefault extends AbstractSqlGenerator {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SqlGeneratorDefault.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public SqlGeneratorDefault() {
    }

    /**
     * <p>テーブル情報をセットします
     * @param table テーブル
     */
    public SqlGeneratorDefault(Table table) {
        super(table);
    }

    /**
     * <p>テーブル情報からCreateTable文を作成します。(String)
     * @return Createtable文
     */
    public String generateCreateTableSql() {
        return StringUtil.toStringByList(generateCreateTableSqlList());
    }

    /**
     * <p>テーブル情報からCreateTable文を作成します。(List)
     * @return Createtable文
     */
    public List generateCreateTableSqlList() {
        ArrayList<String> sql = new ArrayList<String>();

        String tblName = table_.getName();

        log__.debug("-- table is " + tblName +  "--");

        sql.add(" create table " + tblName + " (");

        //PrimaryKey
        ArrayList keys = table_.getPkey();

        //
        ArrayList cols = table_.getColumns();
        Iterator it = cols.iterator();
        while (it.hasNext()) {
            Column col = (Column) it.next();
            //Java型
            int type = col.getType();
            String typeStr = _getTypeStr(col);

            //
            String line = " " + GenConst.INDENT_SQL + col.getName() + " " + typeStr;
            if (it.hasNext() || !keys.isEmpty()) {
                line += ",";
            }
            sql.add(line);
        }

        //主キーの設定
        if (!keys.isEmpty()) {
            String keystr = "";
            Iterator it2 = keys.iterator();
            while (it2.hasNext()) {
                Column col = (Column) it2.next();
                keystr += col.getName();
                if (it2.hasNext()) {
                    keystr += ",";
                }
            }
            sql.add(GenConst.INDENT_SQL + " primary key (" + keystr + ")");
        }
        sql.add(" )");
        return sql;
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
            case Types.BINARY :
                log__.debug("BINARY");
            case Types.BIT :
                log__.debug("BIT");
            case Types.BLOB :
                log__.debug("BLOB");
            case Types.BOOLEAN :
                log__.debug("BOOLEAN");
            case Types.CHAR :
                log__.debug("CHAR");
            case Types.CLOB :
                log__.debug("CLOB");
            case Types.DATALINK :
                log__.debug("DATALINK");
            case Types.DATE :
                ret = "Date";
                log__.debug("DATE");
                break;

            case Types.DECIMAL :
                log__.debug(Types.DECIMAL + "DECIMAL");
            case Types.DISTINCT :
                log__.debug(Types.DISTINCT + " DISTINCT");
            case Types.DOUBLE :
                log__.debug(Types.DOUBLE + " DOUBLE");
            case Types.FLOAT :
                log__.debug(Types.FLOAT + " FLOAT");
            case Types.INTEGER : //3
                log__.debug(Types.INTEGER + " INTEGER");
                ret = "NUMBER(" + col.getSize() + "," + col.getDigits() + ")";
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
            case Types.TIMESTAMP :
                log__.debug("TIMESTAMP");
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

    /**
     * <p>テーブル情報からCreateTable文を作成します。
     * @return Createtable文
     */
    public String generateDropTableSql() {
        return StringUtil.toStringByList(generateDropTableSqlList());
    }

    /**
     * <p>テーブル情報からCreateTable文を作成します。
     * @return Createtable文
     */
    public List generateDropTableSqlList() {
        ArrayList<String> sql = new ArrayList<String>();
        //テーブル名
        String tblName = table_.getName();
        sql.add("drop table " + tblName);
        return sql;
    }

    /**
     * <p>テーブル情報からselect文を作成します。
     * @return select文
     */
    public String generateSelectSql() {
        return StringUtil.toStringByList(generateSelectSqlList());
    }

    /**
     * <p>テーブル情報からselect文を作成します。
     * @return select文
     */
    public String generateSelectAllSql() {
        return StringUtil.toStringByList(generateSelectSqlAllList());
    }

    /**
     * <p>テーブル情報からselect文を作成します。
     * @return リスト形式のselect文
     */
    public List generateSelectSqlAllList() {
        ArrayList<String> sql = new ArrayList<String>();
        //テーブル名
        String tblName = table_.getName();
        sql.add(" select ");

        ArrayList cols = table_.getColumns();
        Iterator it = cols.iterator();
        while (it.hasNext()) {
            Column col = (Column) it.next();
            //
            String line = " " + GenConst.INDENT_SQL + col.getName();
            if (it.hasNext()) {
                line += ",";
            }
            sql.add(line);
        }
        sql.add(" from ");
        sql.add(" " + GenConst.INDENT_SQL + tblName);
        return sql;
    }

    /**
     * <p>テーブル情報からselect文を作成します。
     * @return select文
     */
    public List generateSelectSqlList() {
        ArrayList<String> sql = new ArrayList<String>();
        //テーブル名
        String tblName = table_.getName();
        sql.add(" select");

        ArrayList cols = table_.getColumns();
        Iterator it = cols.iterator();
        while (it.hasNext()) {
            Column col = (Column) it.next();
            //
            String line = " " + GenConst.INDENT_SQL + col.getName();
            if (it.hasNext()) {
                line += ",";
            }
            sql.add(line);
        }
        sql.add(" from");
        sql.add(" " + GenConst.INDENT_SQL + tblName);

        //主キーの条件追加
        //PrimaryKey
        ArrayList keys = table_.getPkey();
        if (!keys.isEmpty()) {
            sql.add(" where ");
            Iterator it2 = keys.iterator();
            while (it2.hasNext()) {
                Column col = (Column) it2.next();
                String colName = col.getName();
                sql.add(" " + GenConst.INDENT_SQL + colName + "=?");
                if (it2.hasNext()) {
                    sql.add(" " + "and");
                }
            }
        }

        return sql;
    }

    /**
     * <p>テーブル情報からInsert文を作成します。
     * @return insert文
     */
    public String generateInsertSql() {
        return StringUtil.toStringByList(generateInsertSqlList());
    }

    /**
     * <p>テーブル情報からInsert文を作成します。
     * @return リスト形式のinsert文
     */
    public List generateInsertSqlList() {
        ArrayList<String> sql = new ArrayList<String>();
        //テーブル名
        String tblName = table_.getName();
        sql.add(" insert ");
        sql.add(" into ");
        sql.add(" " + tblName + "(");

        ArrayList cols = table_.getColumns();
        Iterator it = cols.iterator();
        while (it.hasNext()) {
            Column col = (Column) it.next();
            //
            String line = " " + GenConst.INDENT_SQL + col.getName();
            if (it.hasNext()) {
                line += ",";
            }
            sql.add(line);
        }
        sql.add(" )");
        sql.add(" values");
        sql.add(" (");

        //値
        Iterator it2 = cols.iterator();
        while (it2.hasNext()) {
            it2.next();
            String line = " " + GenConst.INDENT_SQL;
            if (it2.hasNext()) {
                line += "?,";
            } else {
                line += "?";
            }
            sql.add(line);
        }
        sql.add(" )");
        return sql;
    }

    /**
     * <p>テーブル情報からUpdate文を作成します。
     * @return update文
     */
    public String generateUpdateSql() {
        return StringUtil.toStringByList(generateUpdateSqlList());
    }

    /**
     * <p>テーブル情報からUpdate文を作成します。
     * @return update文
     */
    public List generateUpdateSqlList() {
        ArrayList<String> sql = new ArrayList<String>();
        //テーブル名
        String tblName = table_.getName();
        sql.add(" update");
        sql.add(" " + GenConst.INDENT_SQL + tblName + "");
        sql.add(" set ");

        ArrayList cols = table_.getColumns();
        Iterator it = cols.iterator();
        while (it.hasNext()) {
            Column col = (Column) it.next();
            if (col.getSeqKey() > 0) {
                continue;
            }
            //
            String line = " " + GenConst.INDENT_SQL + col.getName() + "=?";
            if (it.hasNext()) {
                line += ",";
            }
            sql.add(line);
        }

        //PrimaryKey
        ArrayList keys = table_.getPkey();
        if (!keys.isEmpty()) {
            //主キーの条件追加
            sql.add(" where ");
            Iterator it2 = keys.iterator();
            while (it2.hasNext()) {
                Column col = (Column) it2.next();
                String colName = col.getName();
                sql.add(" " + GenConst.INDENT_SQL + colName + "=?");
                if (it2.hasNext()) {
                    sql.add(" and");
                }
            }
        }
        return sql;
    }

    /**
     * <p>テーブル情報からdelete文を作成します。
     * @return delete文
     */
    public String generateDeleteSql() {
        return StringUtil.toStringByList(generateDeleteSqlList());
    }

    /**
     * <p>テーブル情報からDelete文を作成します。
     * @return delete文
     */
    public List generateDeleteSqlList() {
        ArrayList<String> sql = new ArrayList<String>();
        //テーブル名
        String tblName = table_.getName();
        sql.add(" delete");
        sql.add(" from");
        sql.add(" " + GenConst.INDENT_SQL + tblName);

        //主キーの条件追加
        //PrimaryKey
        ArrayList keys = table_.getPkey();
        if (!keys.isEmpty()) {
            sql.add(" where ");
            Iterator it2 = keys.iterator();
            while (it2.hasNext()) {
                Column col = (Column) it2.next();
                String colName = col.getName();
                sql.add(" " + GenConst.INDENT_SQL + colName + "=?");
                if (it2.hasNext()) {
                    sql.add(" " + "and");
                }
            }
        }
        return sql;
    }
}
