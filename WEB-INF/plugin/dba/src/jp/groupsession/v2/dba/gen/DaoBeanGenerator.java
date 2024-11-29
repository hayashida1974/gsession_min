package jp.groupsession.v2.dba.gen;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.dba.core.AbstractSqlGenerator;
import jp.groupsession.v2.dba.core.BeanGenerator;
import jp.groupsession.v2.dba.core.DaoGenerator;
import jp.groupsession.v2.dba.core.GenUtil;
import jp.groupsession.v2.dba.core.SqlGeneratorFactory;
import jp.groupsession.v2.dba.core.TypeMappingResourceBundle;
import jp.groupsession.v2.dba.meta.JdbcMetaExplorer;
import jp.groupsession.v2.dba.meta.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>DaoとBeanを生成するクラス
 * @author JTS
 */
public class DaoBeanGenerator extends AbstractGenerator {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(DaoBeanGenerator.class);

    /** ファイルの拡張子 */
    private static final String EXTENSION__ = ".java";
    /** 製品名 */
    private String product__ = null;

    /**
     * <p>空のコンストラクタ
     */
    public DaoBeanGenerator() {
    }

    /**
     * <p>空のコンストラクタ
     * @param product 製品名
     */
    public DaoBeanGenerator(String product) {
        product__ = product;
    }

    /**
     * <p>テーブルデータのリストを元にCreateTable文のSQLを生成します。
     * @param tables テーブル情報
     * @throws GenerateException 生成時のエラー
     */
    public void generate(List tables) throws GenerateException {

        try {
            Iterator it = tables.iterator();
            while (it.hasNext()) {
                //
                Table tbl = (Table) it.next();
                generate(tbl, TypeMappingResourceBundle.getString("destdir"), Encoding.WINDOWS_31J);
            }
        } catch (Exception e) {
            log__.error(e);
            throw new GenerateException();
        }
    }

    /**
     * <p>テーブルデータを元にCreateTable文のSQLを生成します。
     * @param tbl テーブル情報
     * @param destDir 出力先
     * @param encode 出力文字コード
     * @throws GenerateException 生成時のエラー
     */
    public void generate(Table tbl, String destDir, String encode) throws GenerateException {
        String tblName = tbl.getName();

        try {
            // Bean Class名
            String beanClsName = GenUtil.createClassName(tblName, false,
                    TypeMappingResourceBundle.getString("table_prefix"));

            // Dao Class名
            String daoClsName = GenUtil.createClassName(tblName, true,
                    TypeMappingResourceBundle.getString("table_prefix"));

            // SQL生成オブジェクトの作成
            AbstractSqlGenerator sqlGen = getSqlGenerator(product__, tbl);

            // JavaBeanを生成
            BeanGenerator bg = new BeanGenerator(tbl);
            IOTools.writeText(destDir, beanClsName + EXTENSION__, encode, bg
                    .generate());
            // Daoを生成
            DaoGenerator dg = new DaoGenerator(tbl, sqlGen);
            IOTools.writeText(destDir, daoClsName + EXTENSION__, encode, dg
                    .generate());
        } catch (Exception e) {
            log__.error(e);
            throw new GenerateException();
        }
    }

    /**
     * <p>
     * コネクションを元にCreateTable文のSQLファイルを生成します。(String)
     *
     * @param con
     *            コネクション
     * @throws GenerateException
     *             生成エラー
     */
    public void generate(Connection con) throws GenerateException {
        try {
            //コネクションよりDB内容を取得・確認
            JdbcMetaExplorer jexp = new JdbcMetaExplorer(con);
            List tables = jexp.getTables("TABLE");
            generate(tables);
        } catch (SQLException e) {
            log__.error(e);
            throw new GenerateException(e);
        }
    }

    /**
     * <p>個々のDB製品に対応したCreateTableFileGeneratorを返します。
     * 取得できなかった場合ClassNotFoundExceptionをスローします
     * @param product 製品名
     * @param tbl テーブルオブジェクト
     * @return 個々のDB製品に対応したCreateTableFileGenerator
     * @exception ClassNotFoundException クラスが見つからなかった場合
     */
    public static AbstractSqlGenerator getSqlGenerator(String product, Table tbl)
        throws ClassNotFoundException {
        return SqlGeneratorFactory.getSqlGenerator(product, tbl);
    }
}
