package jp.groupsession.v2.dba.gen;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.dba.core.AbstractSqlGenerator;
import jp.groupsession.v2.dba.core.SqlGeneratorFactory;
import jp.groupsession.v2.dba.core.TypeMappingResourceBundle;
import jp.groupsession.v2.dba.meta.JdbcMetaExplorer;
import jp.groupsession.v2.dba.meta.Table;

/**
 * <br>[機  能] CreateTable文のSQLを返します
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class CreateTableFileGenerator extends AbstractGenerator {

    /** ファイルの拡張子 */
    private static final String EXTENSION__ = ".sql";
    /** 製品名 */
    private String product__ = null;

    /**
     * <p>空のコンストラクタ
     */
    public CreateTableFileGenerator() {
    }

    /**
     * <p>空のコンストラクタ
     * @param product 製品名
     */
    public CreateTableFileGenerator(String product) {
        product__ = product;
    }

    /**
     * <p>CreateTable文のファイルを作成します。
     * @param tables テーブル情報
     * @throws GenerateException 生成に失敗した場合にスロー
     */
    public void generate(List tables) throws GenerateException {

        try {
            Iterator it = tables.iterator();
            while (it.hasNext()) {
                //テーブル
                Table tbl = (Table) it.next();

                //テーブル名
                String tblName = tbl.getName();

                //SQL生成オブジェクトの作成
                AbstractSqlGenerator sqlGen = SqlGeneratorFactory.getSqlGenerator(product__, tbl);

                //ファイルにライト
                IOTools.writeText(
                    TypeMappingResourceBundle.getString("destdir"),
                    tblName + EXTENSION__,
                    Encoding.WINDOWS_31J,
                    sqlGen.generateCreateTableSql());
            }
        } catch (IOException e) {
            throw new GenerateException(e);
        }
    }

    /**
     * <p>CreateTable文のファイルを作成します。
     * @param con コネクション
     * @throws GenerateException 生成に失敗した場合にスロー
     */
    public void generate(Connection con) throws GenerateException {
        try {
            //コネクションよりDB内容を取得・確認
            JdbcMetaExplorer jexp = new JdbcMetaExplorer(con);
            List tables = jexp.getTables("TABLE");
            generate(tables);
        } catch (SQLException e) {
            throw new GenerateException(e);
        }
    }
}
