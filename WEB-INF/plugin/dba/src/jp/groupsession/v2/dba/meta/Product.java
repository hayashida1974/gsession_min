/*
 * 作成日: 2005/01/18
 *
 * TODO この生成されたファイルのテンプレートを変更するには次へジャンプ:
 * ウィンドウ - 設定 - Java - コード・スタイル - コード・テンプレート
 */
package jp.groupsession.v2.dba.meta;

/**
 * <br>[機  能] DBの製品情報を格納するモデル
 * <br>[解  説]
 * <br>[備  考] JDBCドライバの実装状況によって、取得できない項目が多いのであまり信用できない
 * @author JTS
 */
public class Product {
    /** DB製品名 */
    private String productName__;
    /** メジャーバージョン */
    private int majorVersion__;
    /** マイナーバージョン */
    private int minorVersion__;
    /** 製品バージョン */
    private String productVersion__;
    /** ドライバ名 */
    private String driverName__;
    /** ドライババージョン */
    private String driverVersion__;
    /** ドライバメジャーバージョン */
    private int driverMajorVersion__;
    /** ドライバマイナーバージョン */
    private int driverMinorVersion__;
    /** 列名のMAXLENGTH */
    private int maxColumnNameLength__;

    /**
     * @return driverMajorVersion を戻します。
     */
    public int getDriverMajorVersion() {
        return driverMajorVersion__;
    }
    /**
     * @param driverMajorVersion driverMajorVersion を設定。
     */
    public void setDriverMajorVersion(int driverMajorVersion) {
        driverMajorVersion__ = driverMajorVersion;
    }
    /**
     * @return driverMinorVersion を戻します。
     */
    public int getDriverMinorVersion() {
        return driverMinorVersion__;
    }
    /**
     * @param driverMinorVersion driverMinorVersion を設定。
     */
    public void setDriverMinorVersion(int driverMinorVersion) {
        driverMinorVersion__ = driverMinorVersion;
    }
    /**
     * @return driverName を戻します。
     */
    public String getDriverName() {
        return driverName__;
    }
    /**
     * @param driverName driverName を設定。
     */
    public void setDriverName(String driverName) {
        driverName__ = driverName;
    }
    /**
     * @return driverVersion を戻します。
     */
    public String getDriverVersion() {
        return driverVersion__;
    }
    /**
     * @param driverVersion driverVersion を設定。
     */
    public void setDriverVersion(String driverVersion) {
        driverVersion__ = driverVersion;
    }
    /**
     * @return majorVersion を戻します。
     */
    public int getMajorVersion() {
        return majorVersion__;
    }
    /**
     * @param majorVersion majorVersion を設定。
     */
    public void setMajorVersion(int majorVersion) {
        majorVersion__ = majorVersion;
    }
    /**
     * @return maxColumnNameLength を戻します。
     */
    public int getMaxColumnNameLength() {
        return maxColumnNameLength__;
    }
    /**
     * @param maxColumnNameLength maxColumnNameLength を設定。
     */
    public void setMaxColumnNameLength(int maxColumnNameLength) {
        maxColumnNameLength__ = maxColumnNameLength;
    }
    /**
     * @return maxColumnsInGroupBy を戻します。
     */
    public int getMaxColumnsInGroupBy() {
        return maxColumnsInGroupBy__;
    }
    /**
     * @param maxColumnsInGroupBy maxColumnsInGroupBy を設定。
     */
    public void setMaxColumnsInGroupBy(int maxColumnsInGroupBy) {
        maxColumnsInGroupBy__ = maxColumnsInGroupBy;
    }
    /**
     * @return maxColumnsInIndex を戻します。
     */
    public int getMaxColumnsInIndex() {
        return maxColumnsInIndex__;
    }
    /**
     * @param maxColumnsInIndex maxColumnsInIndex を設定。
     */
    public void setMaxColumnsInIndex(int maxColumnsInIndex) {
        maxColumnsInIndex__ = maxColumnsInIndex;
    }
    /**
     * @return maxColumnsInOrderBy を戻します。
     */
    public int getMaxColumnsInOrderBy() {
        return maxColumnsInOrderBy__;
    }
    /**
     * @param maxColumnsInOrderBy maxColumnsInOrderBy を設定。
     */
    public void setMaxColumnsInOrderBy(int maxColumnsInOrderBy) {
        maxColumnsInOrderBy__ = maxColumnsInOrderBy;
    }
    /**
     * @return maxColumnsInSelect を戻します。
     */
    public int getMaxColumnsInSelect() {
        return maxColumnsInSelect__;
    }
    /**
     * @param maxColumnsInSelect maxColumnsInSelect を設定。
     */
    public void setMaxColumnsInSelect(int maxColumnsInSelect) {
        maxColumnsInSelect__ = maxColumnsInSelect;
    }
    /**
     * @return maxColumnsInTable を戻します。
     */
    public int getMaxColumnsInTable() {
        return maxColumnsInTable__;
    }
    /**
     * @param maxColumnsInTable maxColumnsInTable を設定。
     */
    public void setMaxColumnsInTable(int maxColumnsInTable) {
        maxColumnsInTable__ = maxColumnsInTable;
    }
    /**
     * @return maxCursorNameLength を戻します。
     */
    public int getMaxCursorNameLength() {
        return maxCursorNameLength__;
    }
    /**
     * @param maxCursorNameLength maxCursorNameLength を設定。
     */
    public void setMaxCursorNameLength(int maxCursorNameLength) {
        maxCursorNameLength__ = maxCursorNameLength;
    }
    /**
     * @return maxIndexLength を戻します。
     */
    public int getMaxIndexLength() {
        return maxIndexLength__;
    }
    /**
     * @param maxIndexLength maxIndexLength を設定。
     */
    public void setMaxIndexLength(int maxIndexLength) {
        maxIndexLength__ = maxIndexLength;
    }
    /**
     * @return maxProcedureNameLength を戻します。
     */
    public int getMaxProcedureNameLength() {
        return maxProcedureNameLength__;
    }
    /**
     * @param maxProcedureNameLength maxProcedureNameLength を設定。
     */
    public void setMaxProcedureNameLength(int maxProcedureNameLength) {
        maxProcedureNameLength__ = maxProcedureNameLength;
    }
    /**
     * @return maxRowSize を戻します。
     */
    public int getMaxRowSize() {
        return maxRowSize__;
    }
    /**
     * @param maxRowSize maxRowSize を設定。
     */
    public void setMaxRowSize(int maxRowSize) {
        maxRowSize__ = maxRowSize;
    }
    /**
     * @return maxSchemaNameLength を戻します。
     */
    public int getMaxSchemaNameLength() {
        return maxSchemaNameLength__;
    }
    /**
     * @param maxSchemaNameLength maxSchemaNameLength を設定。
     */
    public void setMaxSchemaNameLength(int maxSchemaNameLength) {
        maxSchemaNameLength__ = maxSchemaNameLength;
    }
    /**
     * @return maxStatementLength を戻します。
     */
    public int getMaxStatementLength() {
        return maxStatementLength__;
    }
    /**
     * @param maxStatementLength maxStatementLength を設定。
     */
    public void setMaxStatementLength(int maxStatementLength) {
        maxStatementLength__ = maxStatementLength;
    }
    /**
     * @return maxTableNameLength を戻します。
     */
    public int getMaxTableNameLength() {
        return maxTableNameLength__;
    }
    /**
     * @param maxTableNameLength maxTableNameLength を設定。
     */
    public void setMaxTableNameLength(int maxTableNameLength) {
        maxTableNameLength__ = maxTableNameLength;
    }
    /**
     * @return maxTablesInSelect を戻します。
     */
    public int getMaxTablesInSelect() {
        return maxTablesInSelect__;
    }
    /**
     * @param maxTablesInSelect maxTablesInSelect を設定。
     */
    public void setMaxTablesInSelect(int maxTablesInSelect) {
        maxTablesInSelect__ = maxTablesInSelect;
    }
    /**
     * @return maxUserNameLength を戻します。
     */
    public int getMaxUserNameLength() {
        return maxUserNameLength__;
    }
    /**
     * @param maxUserNameLength maxUserNameLength を設定。
     */
    public void setMaxUserNameLength(int maxUserNameLength) {
        maxUserNameLength__ = maxUserNameLength;
    }
    /**
     * @return minorVersion を戻します。
     */
    public int getMinorVersion() {
        return minorVersion__;
    }
    /**
     * @param minorVersion minorVersion を設定。
     */
    public void setMinorVersion(int minorVersion) {
        minorVersion__ = minorVersion;
    }
    /**
     * @return productName を戻します。
     */
    public String getProductName() {
        return productName__;
    }
    /**
     * @param productName productName を設定。
     */
    public void setProductName(String productName) {
        productName__ = productName;
    }
    /**
     * @return productVersion を戻します。
     */
    public String getProductVersion() {
        return productVersion__;
    }
    /**
     * @param productVersion productVersion を設定。
     */
    public void setProductVersion(String productVersion) {
        productVersion__ = productVersion;
    }
    /**
     * @return supportsAlterTableWithAddColumn を戻します。
     */
    public boolean isSupportsAlterTableWithAddColumn() {
        return supportsAlterTableWithAddColumn__;
    }
    /**
     * @param supportsAlterTableWithAddColumn supportsAlterTableWithAddColumn を設定。
     */
    public void setSupportsAlterTableWithAddColumn(
            boolean supportsAlterTableWithAddColumn) {
        supportsAlterTableWithAddColumn__ = supportsAlterTableWithAddColumn;
    }
    /**
     * @return supportsAlterTableWithDropColumn を戻します。
     */
    public boolean isSupportsAlterTableWithDropColumn() {
        return supportsAlterTableWithDropColumn__;
    }
    /**
     * @param supportsAlterTableWithDropColumn supportsAlterTableWithDropColumn を設定。
     */
    public void setSupportsAlterTableWithDropColumn(
            boolean supportsAlterTableWithDropColumn) {
        supportsAlterTableWithDropColumn__ = supportsAlterTableWithDropColumn;
    }
    /** */
    private int maxColumnsInGroupBy__;
    /** */
    private int maxColumnsInIndex__;
    /** */
    private int maxColumnsInOrderBy__;
    /** */
    private int maxColumnsInSelect__;
    /** */
    private int maxColumnsInTable__;
    /** */
    private int maxCursorNameLength__;
    /** */
    private int maxIndexLength__;
    /** */
    private int maxProcedureNameLength__;
    /** */
    private int maxRowSize__;
    /** */
    private int maxSchemaNameLength__;
    /** */
    private int maxStatementLength__;
    /** */
    private int maxTableNameLength__;
    /** */
    private int maxTablesInSelect__;
    /** */
    private int maxUserNameLength__;
    /** */
    private boolean supportsAlterTableWithAddColumn__;
    /** */
    private boolean supportsAlterTableWithDropColumn__;
}
