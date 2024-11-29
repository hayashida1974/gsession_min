package jp.groupsession.v2.dba.dba001.model;

/**
 * <br>[機  能] INDEXの属性情報モデル
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Dba001IndexModel {
    /** TABLE_CATALOG */
    private String tableCatalog__ = null;
    /** TABLE_SCHEMA */
    private String tableSchema__ = null;
    /** TABLE_NAME */
    private String tableName__ = null;
    /** NON_UNIQUE */
    private String nonUnique__ = null;
    /** INDEX_NAME */
    private String indexName__ = null;
    /** ORDINAL_POSITION */
    private String ordinalPosition__ = null;
    /** COLUMN_NAME */
    private String columnName__ = null;
    /** CARDINALITY */
    private String cardInality__ = null;
    /** PRIMARY_KEY */
    private String primaryKey__ = null;
    /** INDEX_TYPE_NAME */
    private String indexTypeName__ = null;
    /** IS_GENERATED */
    private String isGenerated__ = null;
    /** INDEX_TYPE */
    private String indexType__ = null;
    /** ASC_OR_DESC */
    private String ascOrDesc__ = null;
    /** PAGES */
    private String pages__ = null;
    /** FILTER_CONDITION */
    private String filterCondition__ = null;
    /** REMARKS */
    private String remarks__ = null;
    /** SQL */
    private String sql__ = null;
    /** ID */
    private String id__ = null;
    /** SORT_TYPE */
    private String sortType__ = null;
    /**
     * <br>[機  能] ascOrDesc を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return ascOrDesc
     */
    public String getAscOrDesc() {
        return ascOrDesc__;
    }
    /**
     * <br>[機  能] ascOrDesc を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ascOrDesc 設定する ascOrDesc
     */
    public void setAscOrDesc(String ascOrDesc) {
        ascOrDesc__ = ascOrDesc;
    }
    /**
     * <br>[機  能] cardInality を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return cardInality
     */
    public String getCardInality() {
        return cardInality__;
    }
    /**
     * <br>[機  能] cardInality を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cardInality 設定する cardInality
     */
    public void setCardInality(String cardInality) {
        cardInality__ = cardInality;
    }
    /**
     * <br>[機  能] columnName を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return columnName
     */
    public String getColumnName() {
        return columnName__;
    }
    /**
     * <br>[機  能] columnName を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param columnName 設定する columnName
     */
    public void setColumnName(String columnName) {
        columnName__ = columnName;
    }
    /**
     * <br>[機  能] filterCondition を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return filterCondition
     */
    public String getFilterCondition() {
        return filterCondition__;
    }
    /**
     * <br>[機  能] filterCondition を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param filterCondition 設定する filterCondition
     */
    public void setFilterCondition(String filterCondition) {
        filterCondition__ = filterCondition;
    }
    /**
     * <br>[機  能] id を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return id
     */
    public String getId() {
        return id__;
    }
    /**
     * <br>[機  能] id を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param id 設定する id
     */
    public void setId(String id) {
        id__ = id;
    }
    /**
     * <br>[機  能] indexName を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return indexName
     */
    public String getIndexName() {
        return indexName__;
    }
    /**
     * <br>[機  能] indexName を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param indexName 設定する indexName
     */
    public void setIndexName(String indexName) {
        indexName__ = indexName;
    }
    /**
     * <br>[機  能] indexType を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return indexType
     */
    public String getIndexType() {
        return indexType__;
    }
    /**
     * <br>[機  能] indexType を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param indexType 設定する indexType
     */
    public void setIndexType(String indexType) {
        indexType__ = indexType;
    }
    /**
     * <br>[機  能] indexTypeName を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return indexTypeName
     */
    public String getIndexTypeName() {
        return indexTypeName__;
    }
    /**
     * <br>[機  能] indexTypeName を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param indexTypeName 設定する indexTypeName
     */
    public void setIndexTypeName(String indexTypeName) {
        indexTypeName__ = indexTypeName;
    }
    /**
     * <br>[機  能] isGenerated を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return isGenerated
     */
    public String getIsGenerated() {
        return isGenerated__;
    }
    /**
     * <br>[機  能] isGenerated を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param isGenerated 設定する isGenerated
     */
    public void setIsGenerated(String isGenerated) {
        isGenerated__ = isGenerated;
    }
    /**
     * <br>[機  能] nonUnique を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return nonUnique
     */
    public String getNonUnique() {
        return nonUnique__;
    }
    /**
     * <br>[機  能] nonUnique を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param nonUnique 設定する nonUnique
     */
    public void setNonUnique(String nonUnique) {
        nonUnique__ = nonUnique;
    }
    /**
     * <br>[機  能] ordinalPosition を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return ordinalPosition
     */
    public String getOrdinalPosition() {
        return ordinalPosition__;
    }
    /**
     * <br>[機  能] ordinalPosition を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ordinalPosition 設定する ordinalPosition
     */
    public void setOrdinalPosition(String ordinalPosition) {
        ordinalPosition__ = ordinalPosition;
    }
    /**
     * <br>[機  能] pages を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return pages
     */
    public String getPages() {
        return pages__;
    }
    /**
     * <br>[機  能] pages を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param pages 設定する pages
     */
    public void setPages(String pages) {
        pages__ = pages;
    }
    /**
     * <br>[機  能] primaryKey を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return primaryKey
     */
    public String getPrimaryKey() {
        return primaryKey__;
    }
    /**
     * <br>[機  能] primaryKey を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param primaryKey 設定する primaryKey
     */
    public void setPrimaryKey(String primaryKey) {
        primaryKey__ = primaryKey;
    }
    /**
     * <br>[機  能] remarks を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return remarks
     */
    public String getRemarks() {
        return remarks__;
    }
    /**
     * <br>[機  能] remarks を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param remarks 設定する remarks
     */
    public void setRemarks(String remarks) {
        remarks__ = remarks;
    }
    /**
     * <br>[機  能] sortType を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return sortType
     */
    public String getSortType() {
        return sortType__;
    }
    /**
     * <br>[機  能] sortType を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sortType 設定する sortType
     */
    public void setSortType(String sortType) {
        sortType__ = sortType;
    }
    /**
     * <br>[機  能] sql を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return sql
     */
    public String getSql() {
        return sql__;
    }
    /**
     * <br>[機  能] sql を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sql 設定する sql
     */
    public void setSql(String sql) {
        sql__ = sql;
    }
    /**
     * <br>[機  能] tableCatalog を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return tableCatalog
     */
    public String getTableCatalog() {
        return tableCatalog__;
    }
    /**
     * <br>[機  能] tableCatalog を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param tableCatalog 設定する tableCatalog
     */
    public void setTableCatalog(String tableCatalog) {
        tableCatalog__ = tableCatalog;
    }
    /**
     * <br>[機  能] tableName を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return tableName
     */
    public String getTableName() {
        return tableName__;
    }
    /**
     * <br>[機  能] tableName を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param tableName 設定する tableName
     */
    public void setTableName(String tableName) {
        tableName__ = tableName;
    }
    /**
     * <br>[機  能] tableSchema を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return tableSchema
     */
    public String getTableSchema() {
        return tableSchema__;
    }
    /**
     * <br>[機  能] tableSchema を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param tableSchema 設定する tableSchema
     */
    public void setTableSchema(String tableSchema) {
        tableSchema__ = tableSchema;
    }
}
