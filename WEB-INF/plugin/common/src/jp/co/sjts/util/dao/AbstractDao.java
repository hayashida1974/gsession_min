package jp.co.sjts.util.dao;

import java.sql.Connection;

/**
 * <br>[機  能] テーブルアクセッサの抽象クラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class AbstractDao {

    /** DBコネクション */
    private Connection con__ = null;


    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public AbstractDao() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     */
    public AbstractDao(Connection con) {
        this.con__ = con;
    }

    /**
     * <br>[機  能] コネクションを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return コネクション
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <br>[機  能] コネクションをセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param connection コネクション
     */
    public void setCon(Connection connection) {
        con__ = connection;
    }
}
