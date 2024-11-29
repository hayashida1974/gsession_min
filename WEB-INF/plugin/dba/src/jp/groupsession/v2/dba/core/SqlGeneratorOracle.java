/*
 * create   : 2004/07/30
 * modified :
 */
package jp.groupsession.v2.dba.core;

import jp.groupsession.v2.dba.meta.Table;

/**
 * <br>[機  能] Oracle用のSqlGenerator
 * <br>[解  説]
 * <br>[備  考] 現段階ではOracle固有の処理は実装されていない
 * @author JTS
 */
public class SqlGeneratorOracle extends SqlGeneratorDefault {

    /**
     * <p>デフォルトコンストラクタ
     */
    public SqlGeneratorOracle() {
    }

    /**
     * <p>テーブル情報をセットします
     * @param table テーブル
     */
    public SqlGeneratorOracle(Table table) {
        super(table);
    }
}
