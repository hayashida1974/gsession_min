package jp.groupsession.v2.dba.gen;

import java.sql.Connection;
import java.util.List;

/**
 * <br>[機  能] Generatorの抽象クラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public abstract class AbstractGenerator {

    /**
     * <p>テーブルデータのリストを元に生成を行います
     * @param tables テーブル情報
     * @throws GenerateException 生成に失敗　した場合にスロー
     */
    public abstract void generate(List tables) throws GenerateException;

    /**
     * <p>コネクションを元に生成を行います
     * @param con コネクション
     * @exception GenerateException 生成に失敗した場合にスロー
     */
    public abstract void generate(Connection con) throws GenerateException;

}