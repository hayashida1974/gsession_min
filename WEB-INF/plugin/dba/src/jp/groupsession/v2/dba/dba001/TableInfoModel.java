package jp.groupsession.v2.dba.dba001;

import jp.groupsession.v2.dba.meta.Table;

/**
 * <br>[機  能] 画面にセットするテーブル情報
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TableInfoModel {
    /** テーブル情報 */
    private Table table__ = null;
    /** sql select文 */
    private String sqlSelectString__ = null;
    /** sql update文 */
    private String sqlUpdateString__ = null;
    /** sql insert文 */
    private String sqlInsertString__ = null;
    /** sql delete文 */
    private String sqlDeleteString__ = null;

    /**
     * <p>sqlDeleteString を取得します。
     * @return sqlDeleteString
     */
    public String getSqlDeleteString() {
        return sqlDeleteString__;
    }
    /**
     * <p>sqlDeleteString をセットします。
     * @param sqlDeleteString sqlDeleteString
     */
    public void setSqlDeleteString(String sqlDeleteString) {
        sqlDeleteString__ = sqlDeleteString;
    }
    /**
     * <p>sqlInsertString を取得します。
     * @return sqlInsertString
     */
    public String getSqlInsertString() {
        return sqlInsertString__;
    }
    /**
     * <p>sqlInsertString をセットします。
     * @param sqlInsertString sqlInsertString
     */
    public void setSqlInsertString(String sqlInsertString) {
        sqlInsertString__ = sqlInsertString;
    }
    /**
     * <p>sqlSelectString を取得します。
     * @return sqlSelectString
     */
    public String getSqlSelectString() {
        return sqlSelectString__;
    }
    /**
     * <p>sqlSelectString をセットします。
     * @param sqlSelectString sqlSelectString
     */
    public void setSqlSelectString(String sqlSelectString) {
        sqlSelectString__ = sqlSelectString;
    }
    /**
     * <p>sqlUpdateString を取得します。
     * @return sqlUpdateString
     */
    public String getSqlUpdateString() {
        return sqlUpdateString__;
    }
    /**
     * <p>sqlUpdateString をセットします。
     * @param sqlUpdateString sqlUpdateString
     */
    public void setSqlUpdateString(String sqlUpdateString) {
        sqlUpdateString__ = sqlUpdateString;
    }
    /**
     * <p>table を取得します。
     * @return table
     */
    public Table getTable() {
        return table__;
    }
    /**
     * <p>table をセットします。
     * @param table table
     */
    public void setTable(Table table) {
        table__ = table;
    }

    /**
     * <br>[機  能] テーブル名を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return テーブル名
     */
    public String getName() {
        return table__.getName();
    }
}