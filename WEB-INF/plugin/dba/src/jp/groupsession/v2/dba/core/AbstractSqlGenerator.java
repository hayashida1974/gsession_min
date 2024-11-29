/*
 * create   : 2004/08/02
 * modified :
 */
package jp.groupsession.v2.dba.core;

import java.util.List;

import jp.groupsession.v2.dba.meta.Table;

/**
 * <br>[機  能] SqlGeneratorのAbstractクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractSqlGenerator {

    /** テーブルオブジェクト */
    protected Table table_ = null;

    /**
     * <p>デフォルトコンストラクタ
     */
    public AbstractSqlGenerator() {
    }

    /**
     * <p>テーブル情報をセット
     * @param table セットするテーブル情報
     */
    public AbstractSqlGenerator(Table table) {
        table_ = table;
    }

    /**
     * <p>テーブル情報を取得します。
     * @return テーブル
     */
    public Table getTable() {
        return table_;
    }

    /**
     * <p>テーブル情報をセットします。
     * @param table テーブル
     */
    public void setTable(Table table) {
        table_ = table;
    }

    /**
     * <p>CreateTable文のSQLを生成します。(String)
     * @return create table 文
     */
    public abstract String generateCreateTableSql();

    /**
     * <p>CreateTable文のSQLを生成します。(List)
     * @return リスト形式の create table文
     */
    public abstract List generateCreateTableSqlList();

    /**
     * <p>DropTable文のSQLを生成します。(String)
     * @return drop table文
     */
    public abstract String generateDropTableSql();

    /**
     * <p>DropTable文のSQLを生成します。(String)
     * @return リスト形式のdrop table文
     */
    public abstract List generateDropTableSqlList();

    /**
     * <p>Insert文のSQLを生成します。(String)
     * @return insert文
     */
    public abstract String generateInsertSql();

    /**
     * <p>Insert文のSQLを生成します。(List)
     * @return リスト形式のinsert文
     */
    public abstract List generateInsertSqlList();

    /**
     * <p>Insert文のSQLを生成します。(String)
     * @return update文
     */
    public abstract String generateUpdateSql();

    /**
     * <p>Update文のSQLを生成します。(List)
     * @return リスト形式のupdate文
     */
    public abstract List generateUpdateSqlList();

    /**
     * <p>Select文のSQLを生成します。(String)
     * @return select文
     */
    public abstract String generateSelectSql();

    /**
     * <p>Select文のSQLを生成します。(List)
     * @return リスト形式のselect文
     */
    public abstract List generateSelectSqlList();

    /**
     * <p>Select文のSQLを生成します。(String)
     * @return select文
     */
    public abstract String generateSelectAllSql();

    /**
     * <p>Select文のSQLを生成します。(List)
     * @return リスト形式のselect文
     */
    public abstract List generateSelectSqlAllList();

    /**
     * <p>Delete文のSQLを生成します。(String)
     * @return delete文
     */
    public abstract String generateDeleteSql();

    /**
     * <p>Delete文のSQLを生成します。(List)
     * @return リスト形式のdelete文
     */
    public abstract List generateDeleteSqlList();
}
