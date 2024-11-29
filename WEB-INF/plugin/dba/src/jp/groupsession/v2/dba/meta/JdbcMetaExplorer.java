/*
 * 作成日: 2004/07/30
 */
package jp.groupsession.v2.dba.meta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.sjts.util.jdbc.JDBCUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] コネクションからDBのメタ情報を取得する
 * <br>[解  説] 使用するコネクションはクローズしないので、上位のクラスでクローズする。
 * <br>[備  考]
 * @author JTS
 */
public class JdbcMetaExplorer {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(JdbcMetaExplorer.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public JdbcMetaExplorer() {
    }

    /**
     * <br>[機  能] コネクションをセット
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public JdbcMetaExplorer(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] コネクションを取得します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return コネクション
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <br>[機  能] コネクションをセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param con
     *            コネクション
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] テーブルスキーマ文字列を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return スキーマ文字列
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getTableSchema() throws SQLException {
        ArrayList<String> list = new ArrayList<String>();
        DatabaseMetaData objMet = con__.getMetaData();
        ResultSet rs = null;
        rs = objMet.getSchemas();

        while (rs.next()) {
            list.add(rs.getString("TABLE_SCHEM"));
        }

        return list;
    }

    /**
     * <br>[機  能] コネクション内から取得できる全てのテーブル名を返します
     * <br>[解  説]
     * <br>[備  考]
     * @return 全てのテーブル名
     * @throws SQLException SQLエラー
     * @param getType 取得したいTABLE TYPE
     */
    @SuppressWarnings("unchecked")
    public ArrayList<String> getTableNames(String getType) throws SQLException {

        ArrayList<String> ret = new ArrayList<String>();
        ResultSet rs = null;

        ArrayList<String> schemas = getTableSchema();
        try {
            DatabaseMetaData meta = con__.getMetaData();
            String catalog = null;
            String schema = null;
            //TABLE LIST
            rs = meta.getTables(catalog, schema, "%", new String[]{});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME"); //TABLE_TYPE
                String tableType = rs.getString("TABLE_TYPE");

                log__.debug("TABLE_TYPE:" + rs.getString("TABLE_TYPE")
                          + " TABLE_NAME:" + rs.getString("TABLE_NAME"));
                //テーブルの場合のみ追加
                if (isTable(tableType, getType)) {

//                    String pref = TypeMappingResourceBundle
//                            .getString("table_prefix");
                    //if (tableName.startsWith(pref)) {
                        ret.add(tableName);
                    //}
                }
                //String tableCat = rs2.getString("TABLE_CAT");
                //String tableSchem = rs2.getString("TABLE_SCHEM");
                //String tableType = rs2.getString("TABLE_TYPE");
                //String remarks = rs2.getString("REMARKS");
                //String typeCat = rs.getString("TYPE_CAT");
                //String typeSchem = rs.getString("TYPE_SCHEM");
                //String typeName = rs.getString("TYPE_NAME");
                //String selfRefencingColName =
                // rs.getString("SELF_REFERENCING_COL_NAME");
                //String refGeneration = rs.getString("REF_GENERATION");
            }

        } catch (SQLException e) {
            log__.error("テーブル名の取得に失敗", e);
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
        }
        return ret;
    }

    /**
     * <br>[機  能] メタ情報から取得したテーブルタイプから、テーブルかどうか判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param type タイプ
     * @param getType 取得したいTABLE TYPE
     * @return true:テーブルである, false:テーブルではない
     */
    private boolean isTable(String type, String getType) {
        if (type == null) {
            return false;
        }

        boolean ret = false;
        //大文字に変換してテーブルかどうか判定
        if (type.toUpperCase().equals(getType)) {
            ret = true;
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定したテーブル情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param tableName
     *            テーブル名
     * @return テーブル情報
     * @throws SQLException
     *             SQL例外
     */
    public Table getTable(String tableName) throws SQLException {

        Table table = new Table();
        //テーブル名をセット
        table.setName(tableName);

        //各行の情報を取得
        ArrayList<Column> columns = getColumns(tableName);
        table.setColumns(columns);
        return table;
    }

    /**
     * <br>[機  能] コネクションから取得できる全てのテーブル情報を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return テーブル情報のリスト
     * @param getType 取得したいTABLE TYPE
     * @throws SQLException
     *             SQLの実行に失敗字
     */
    @SuppressWarnings("unchecked")
    public List<Table> getTables(String getType) throws SQLException {

        ArrayList<Table> ret = new ArrayList<Table>();

        ArrayList names = getTableNames(getType);
        Iterator it = names.iterator();
        while (it.hasNext()) {
            //
            String name = (String) it.next();
            Table tbl = getTable(name);
            ret.add(tbl);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定したテーブルの列情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tableName テーブル名
     * @return Columnを格納したArrayList
     * @throws SQLException SQL例外
     */
    public ArrayList<Column> getColumns(String tableName) throws SQLException {

        ArrayList<Column> columns = new ArrayList<Column>();
        DatabaseMetaData meta = con__.getMetaData();
        ResultSet rs = null;

        //列の一覧を取得
        try {
            String catalog = null;
            String schema = null;
            rs = meta.getColumns(catalog, schema, tableName, null);
            while (rs.next()) {
                //列名
                String clmName = rs.getString("COLUMN_NAME");
                //データ型
                short sdataType = rs.getShort("DATA_TYPE");
                //サイズ
                int size = rs.getInt("COLUMN_SIZE");
                //精度
                int digits = rs.getInt("DECIMAL_DIGITS");
                //NULLを許可するか
                int nullAble = rs.getInt("NULLABLE");

                //Oracle用
                if (sdataType == 3 && digits <= 0) {
                    if (size <= 9) {
                        //INT
                        sdataType = 4;
                    } else {
                        //LONG
                        sdataType = 3;
                    }
                }

                Column col = new Column();
                col.setName(clmName);
                col.setType(sdataType);
                col.setSize(size);
                col.setDigits(digits);
                col.setNullable(nullAble);
                columns.add(col);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        //主キーを取得し、戻り値にセット
        try {
            rs = meta.getPrimaryKeys(null, null, tableName);
            while (rs.next()) {
                //列名
                String clmName = rs.getString("COLUMN_NAME");
                //主キー中の連番
                short keySeq = rs.getShort("KEY_SEQ");
                //主キー名
                String pkName = rs.getString("PK_NAME");
                __setPrimaryKey(columns, clmName, keySeq);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return columns;
    }

    /**
     * <br>[機  能] 主キーをセットする
     * <br>[解  説] 主キーがない場合はnullを返す
     * <br>[備  考]
     * @param cols 列情報
     * @param clmName クラス名
     * @param keySeq キーNO
     * @return 主キーをセットした列情報
     */
    private Column __setPrimaryKey(ArrayList cols, String clmName, short keySeq) {

        if (clmName == null) {
            return null;
        }

        //
        Iterator it = cols.iterator();
        Column col = null;
        while (it.hasNext()) {
            col = (Column) it.next();
            if (clmName.equals(col.getName())) {
                //PKEYの連番をセット
                col.setSeqKey(keySeq);
                break;
            } else {
                col = null;
            }
        }
        return col;
    }

    /**
     * <br>[機  能] コネクションから製品情報を返す
     * <br>[解  説] JDBCドライバの実装状況によって、取得できない項目が多いのであまり信用できない
     * <br>[備  考]
     * @return 製品情報
     * @throws SQLException SQL実行例外
     */
    public Product getProductInfo() throws SQLException {
        Product product = new Product();

        DatabaseMetaData dbMetaData = con__.getMetaData();
        product.setProductName(dbMetaData.getDatabaseProductName());
        product.setProductVersion(dbMetaData.getDatabaseProductVersion());

        product.setDriverName(dbMetaData.getDriverName());
        product.setDriverVersion(dbMetaData.getDriverVersion());
        product.setDriverMajorVersion(dbMetaData.getDriverMajorVersion());
        product.setDriverMinorVersion(dbMetaData.getDriverMinorVersion());
        //        dbMetaData.getExtraNameCharacters();
        //        dbMetaData.getIdentifierQuoteString();
        //        dbMetaData.getMaxCatalogNameLength();
        //        dbMetaData.getMaxCharLiteralLength();
        product.setMaxColumnNameLength(dbMetaData.getMaxColumnNameLength());
        product.setMaxColumnsInGroupBy(dbMetaData.getMaxColumnsInGroupBy());
        product.setMaxColumnsInIndex(dbMetaData.getMaxColumnsInIndex());
        product.setMaxColumnsInOrderBy(dbMetaData.getMaxColumnsInOrderBy());
        product.setMaxColumnsInSelect(dbMetaData.getMaxColumnsInSelect());
        product.setMaxColumnsInTable(dbMetaData.getMaxColumnsInTable());
        //        dbMetaData.getMaxConnections();
        product.setMaxCursorNameLength(dbMetaData.getMaxCursorNameLength());
        product.setMaxIndexLength(dbMetaData.getMaxIndexLength());
        product.setMaxProcedureNameLength(dbMetaData
                .getMaxProcedureNameLength());
        product.setMaxRowSize(dbMetaData.getMaxRowSize());
        product.setMaxSchemaNameLength(dbMetaData.getMaxSchemaNameLength());
        product.setMaxStatementLength(dbMetaData.getMaxStatementLength());
        //        dbMetaData.getMaxStatements();
        product.setMaxTableNameLength(dbMetaData.getMaxTableNameLength());
        product.setMaxTablesInSelect(dbMetaData.getMaxTablesInSelect());
        product.setMaxUserNameLength(dbMetaData.getMaxUserNameLength());
        return product;
    }

    /**
     * <br>[機  能] 結果セットより、そのヘッダ(列名)情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs 結果セット
     * @return 結果セットのヘッダ(列名)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getResultsetHeader(ResultSet rs) throws SQLException {
        ArrayList<String> ret = new ArrayList<String>();
        ResultSetMetaData rmeta = rs.getMetaData();
        int colCount = rmeta.getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            String colName = rmeta.getColumnName(i);
            if (colName == null) {
                colName = "[NULL]";
            }
            ret.add(colName);
        }
        return ret;
    }
}