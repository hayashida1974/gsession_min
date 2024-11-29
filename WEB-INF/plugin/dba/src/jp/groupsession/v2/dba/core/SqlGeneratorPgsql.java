/*
 * create   : 2004/07/30
 * modified :
 */
package jp.groupsession.v2.dba.core;

import jp.groupsession.v2.dba.meta.Table;

/**
 * <br>[機  能] PostgreSQL用のSqlGenerator
 * <br>[解  説]
 * <br>[備  考] 現段階ではPostgreSQL固有の処理は実装されていない
 * @author JTS
 */
public class SqlGeneratorPgsql extends SqlGeneratorDefault {

    /**
     * <p>デフォルトコンストラクタ
     */
    public SqlGeneratorPgsql() {
    }

    /**
     * <p>テーブル情報をセットします
     * @param table テーブル
     */
    public SqlGeneratorPgsql(Table table) {
        super(table);
    }
}
