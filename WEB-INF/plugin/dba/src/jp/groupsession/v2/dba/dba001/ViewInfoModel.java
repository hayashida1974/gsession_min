package jp.groupsession.v2.dba.dba001;

import jp.groupsession.v2.dba.meta.Table;

/**
 * <br>[機  能] 画面にセットするテーブル情報
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ViewInfoModel {

    /** テーブル情報 */
    private Table table__ = null;
    /** sql select文 */
    private String sqlSelectString__ = null;

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