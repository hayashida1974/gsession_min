package jp.groupsession.v2.dba.core;

import jp.groupsession.v2.dba.gen.DbProduct;
import jp.groupsession.v2.dba.meta.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] SqlGeneratorを生成・取得します。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SqlGeneratorFactory {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SqlGeneratorFactory.class);

    /**
     * <p>個々のDB製品に対応したSqlGeneratorを返します。
     * 取得できなかった場合ClassNotFoundExceptionをスローします
     * @param product 製品名
     * @param tbl テーブルオブジェクト
     * @return 個々のDB製品に対応したCreateTableFileGenerator
     */
    public static AbstractSqlGenerator getSqlGenerator(String product, Table tbl) {

        AbstractSqlGenerator sqlGen = null;
        product = product.toUpperCase();
        log__.debug("DB Products is " + product);

        if (DbProduct.ORACLE.equals(product)) {
            //Oracle
            sqlGen = new SqlGeneratorOracle(tbl);
        } else if (DbProduct.POSTGRESQL.equals(product)) {
            //PostgreSQL
            sqlGen = new SqlGeneratorPgsql(tbl);
        } else if (DbProduct.H2.equals(product)) {
            //H2
            sqlGen = new SqlGeneratorH2(tbl);
        } else {
            //Default
            sqlGen = new SqlGeneratorDefault(tbl);
        }
        return sqlGen;
    }
}
