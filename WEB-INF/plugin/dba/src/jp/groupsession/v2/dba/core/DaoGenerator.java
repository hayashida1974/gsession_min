/*
 * create   : 2004/08/02
 * modified :
 */
package jp.groupsession.v2.dba.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.dba.meta.Column;
import jp.groupsession.v2.dba.meta.Table;

/**
 * <br>[機  能] Daoクラスを生成します。
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class DaoGenerator {

    /** テーブルオブジェクト */
    private Table table__ = null;
    /** SqlGenerator */
    private AbstractSqlGenerator sqlGen__ = null;
    /** パッケージ名 */
    private String packageName__ = null;
    /** モデルパッケージ名 */
    private String modelPackageName__ = null;
    /** Daoクラス名をテーブル名 + Dao でそのまま使用するフラグ true:そのまま使用, false:変換する */
    private boolean daoClassNameFlg__ = false;
    /** Modelクラス名をテーブル名 + Model でそのまま使用するフラグ true:そのまま使用, false:変換する */
    private boolean modelClassNameFlg__ = false;

    /**
     * <p>デフォルトコンストラクタ
     */
    public DaoGenerator() {
    }

    /**
     * <p>テーブル情報をセットします
     * @param table テーブル
     */
    public DaoGenerator(Table table) {
        table__ = table;
    }

    /**
     * <p>テーブル情報をセットします
     * @param table テーブル
     * @param sqlGen SqlGenerator
     */
    public DaoGenerator(Table table, AbstractSqlGenerator sqlGen) {
        table__ = table;
        sqlGen__ = sqlGen;
    }

    /**
     * <p>テーブル情報を取得します。
     * @return テーブル
     */
    public Table getTable() {
        return table__;
    }

    /**
     * <p>テーブル情報をセットします。
     * @param table テーブル
     */
    public void setTable(Table table) {
        table__ = table;
    }

    /**
     * <p>DaoのJavaソースを生成します。
     * @return Javaソース
     */
    public String generate() {
        //
        ArrayList<String> source = new ArrayList<String>();

        //パッケージ名を生成
        __createPackage(source);

        //import行を生成
        __createImport(source);

        //クラス名の行を生成
        __createClassName(source);

        //ログインスタンス行を生成
        __createLogger(source);

        //コンストラクタの生成
        __createConstructor(source);

        //Drop Table 文の作成
        __createDropSql(source);

        //Create Table 文の作成
        __createCreateSql(source);

        //Insert 文の作成
        __createInsertSql(source);

        //Update 文の作成
        __createUpdateSql(source);

        //Select 文の作成(全て)
        __createSelectAllSql(source);

        //Select 文の作成(主キーで検索)
        __createSelectSql(source);

        //Delete 文の作成(主キーで削除)
        __createDeleteSql(source);

        //レコードセットより、ビーンを作成するメソッドを生成
        //selectで使用
        __createSelectBeanFromRs(source);

        //フッター }のみ生成
        source.add("}");
        return StringUtil.toStringByList(source);
    }

    /**
     * <p>DaoのJavaソースを生成します。(任意のSelect文のみ)
     * @param sqlLines SQL文
     * @return Javaソース
     */
    public String generateSelect(List<String> sqlLines) {
        //
        ArrayList<String> source = new ArrayList<String>();

        //パッケージ名を生成
        __createPackage(source);

        //import行を生成
        __createImport(source);

        //クラス名の行を生成
        __createClassName(source);

        //ログインスタンス行を生成
        __createLogger(source);

        //コンストラクタの生成
        __createConstructor(source);

        //Select 文の作成(任意のSQL文)
        __createSelectAllSql(source, sqlLines);

        //レコードセットより、ビーンを作成するメソッドを生成
        //selectで使用
        __createSelectBeanFromRs(source);

        //フッター }のみ生成
        source.add("}");
        return StringUtil.toStringByList(source);
    }

    /**
     * <p>パッケージ宣言を生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createPackage(ArrayList<String> source) {
        String pkg = null;
        pkg = getPackageName();
        String line = null;
        if (pkg == null) {
            pkg = TypeMappingResourceBundle.getString("package");
            line = "package " + pkg + ";";
        } else if (pkg.trim().length() <= 0) {
            line = "";
        } else {
            line = "package " + pkg + ";";
        }

        //パッケージ宣言
        source.add(line);
        //空行
        source.add("");
        return source;
    }

    /**
     * <p>import宣言を生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createImport(ArrayList<String> source) {

        HashMap<String, String> hm = new HashMap<String, String>();
        ArrayList cols = table__.getColumns();
        Iterator it = cols.iterator();
        while (it.hasNext()) {
            Column col = (Column) it.next();
            int type = col.getType();
            String typeFqdn = GenUtil.getTypeNameJdbcFQDN(type);
            if (!StringUtil.isNullZeroString(typeFqdn)) {
                hm.put(typeFqdn, "");
                //source.add("import " + typeFqdn + ";");
            }
        }
        //
        Set kset = hm.keySet();
        Iterator it2 = kset.iterator();
        while (it2.hasNext()) {
            String imtgt = (String) it2.next();
            if (imtgt.endsWith("long")) {
                //longはインポートできないので除外
            } else if (imtgt.endsWith("int")) {
                //intはインポートできないので除外
            } else if (imtgt.endsWith("double")) {
                //doubleはインポートできないので除外
            } else {
                source.add("import " + imtgt + ";");
            }
        }

        //AbstractDaoは必須
        source.add("import java.sql.Connection;");
        source.add("import java.sql.PreparedStatement;");
        source.add("import java.sql.ResultSet;");
        source.add("import java.sql.SQLException;");
        source.add("import java.util.List;");
        source.add("import java.util.ArrayList;");
        source.add("import org.apache.commons.logging.Log;");
        source.add("import org.apache.commons.logging.LogFactory;");
        source.add("import " + TypeMappingResourceBundle.getString("JDBCUtil") + ";");
        source.add("import " + TypeMappingResourceBundle.getString("AbstractDao") + ";");
        source.add("import " + TypeMappingResourceBundle.getString("SqlBuffer") + ";");
//        source.add("import "
//                + TypeMappingResourceBundle.getString("DataNotFoundException")
//                + ";");

        //
        if (packageName__ != null && !packageName__.equals(modelPackageName__)) {
            //DaoとModelのパッケージが異なる場合
            String tblName = table__.getName();
            String modelName = __createModelClassName(tblName);
            source.add("import " + modelPackageName__ + "." + modelName + ";");
        }
        source.add("");
        return source;
    }

    /**
     * モデルクラス名を返す
     * @param tableName テーブル名
     * @return モデルクラス名
     */
    private String __createModelClassName(String tableName) {
        if (modelClassNameFlg__) {
            return tableName + "Model";
        } else {
            return GenUtil.createClassName(tableName, false,
                    TypeMappingResourceBundle.getString("table_prefix"));
        }
    }

    /**
     * <br>[機  能] Daoクラス名を返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param tableName テーブル名
     * @return Daoクラス名
     */
    private String __createDaoClassName(String tableName) {
        String clsName = null;
        if (daoClassNameFlg__) {
            clsName = tableName + "Dao";
        } else {
            clsName = GenUtil.createClassName(tableName, true,
                    TypeMappingResourceBundle.getString("table_prefix"));
        }
        return clsName;
    }

    /**
     * <p>クラス宣言を生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createClassName(ArrayList<String> source) {
        String tblName = table__.getName();
        String clsName = null;

        clsName = __createDaoClassName(tblName);

        //
        String line = "public class " + clsName + " extends AbstractDao {";

        //クラス宣言 空
        source.add("/**");
        source.add(" * <p>" + tblName + " Data Access Object");
        source.add(" *");
        source.add(" * @author " + GenConst.PRODUCT_NAME + " version " + GenConst.VERSION);
        source.add(" */");
        source.add(line);
        //空行
        source.add("");
        return source;
    }
    /**
     * <p>ログ出力の宣言ソースを生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createLogger(ArrayList<String> source) {
        String tblName = table__.getName();
        String clsName = __createDaoClassName(tblName);
        //
        source.add(GenConst.INDENT + "/** Logging インスタンス */");
        source.add(GenConst.INDENT
                + "private static Log log__ = LogFactory.getLog(" + clsName
                + ".class);");
        //空行
        source.add("");
        return source;
    }

    /**
     * <p>コンストラクタを生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createConstructor(ArrayList<String> source) {
        String tblName = table__.getName();
        String clsName = __createDaoClassName(tblName);

        //クラス宣言 空
        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>Default Constructor");
        source.add(GenConst.INDENT + " */");
        //
        source.add(GenConst.INDENT + "public " + clsName + "() {");
        source.add(GenConst.INDENT + "}");
        //空行
        source.add("");

        //クラス宣言 コネクション
        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>Set Connection");
        source.add(GenConst.INDENT + " * @param con Connection");
        source.add(GenConst.INDENT + " */");
        //
        source.add(GenConst.INDENT + "public " + clsName + "(Connection con) {");
        source.add(GenUtil.getIndent(2) + "super(con);");
        source.add(GenConst.INDENT + "}");
        //空行
        source.add("");
        return source;
    }

    /**
     * <p>Drop Table を生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createDropSql(ArrayList<String> source) {
        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>Drop Table");
        source.add(GenConst.INDENT + " * @throws SQLException SQL実行例外");
        source.add(GenConst.INDENT + " */");
        source.add(GenConst.INDENT + "public void dropTable() throws SQLException {");
        source.add("");
        source.add(GenUtil.getIndent(2) + "PreparedStatement pstmt = null;");
//        source.add(GenUtil.getIndent(2) + "int count = 0;");
        source.add(GenUtil.getIndent(2) + "Connection con = null;");
        source.add(GenUtil.getIndent(2) + "con = getCon();");
        source.add("");
        source.add(GenUtil.getIndent(2) + "try {");
        source.add(GenUtil.getIndent(2) + "    //SQL文");
        source.add(GenUtil.getIndent(2) + "    SqlBuffer sql = new SqlBuffer();");

        //SQL文
        List sql = sqlGen__.generateDropTableSqlList();
        Iterator it = sql.iterator();
        while (it.hasNext()) {
            //
            String line = (String) it.next();
            line = GenUtil.escapeSrc(line);
            source.add(GenUtil.getIndent(3) + "sql.addSql(\"" + line + "\");");
        }

        source.add("");
        source.add(GenUtil.getIndent(2) + "    pstmt = con.prepareStatement(sql.toSqlString());");
        source.add(GenUtil.getIndent(2) + "    log__.info(sql.toLogString());");
//        source.add(GenUtil.getIndent(2) + "    count = pstmt.executeUpdate();");
        source.add(GenUtil.getIndent(2) + "    pstmt.executeUpdate();");
        source.add(GenUtil.getIndent(2) + "} catch (SQLException e) {");
        source.add(GenUtil.getIndent(2) + "    throw e;");
        source.add(GenUtil.getIndent(2) + "} finally {");
        source.add(GenUtil.getIndent(2) + "    JDBCUtil.closeStatement(pstmt);");
        source.add(GenUtil.getIndent(2) + "}");

        source.add(GenConst.INDENT + "}");
        source.add("");
        return source;
    }

    /**
     * <p>Create Tableを生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createCreateSql(ArrayList<String> source) {
        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>Create Table");
        source.add(GenConst.INDENT + " * @throws SQLException SQL実行例外");
        source.add(GenConst.INDENT + " */");
        source.add(GenConst.INDENT + "public void createTable() throws SQLException {");
        source.add("");
        source.add(GenUtil.getIndent(2) + "PreparedStatement pstmt = null;");
//        source.add(GenUtil.getIndent(2) + "int count = 0;");
        source.add(GenUtil.getIndent(2) + "Connection con = null;");
        source.add(GenUtil.getIndent(2) + "con = getCon();");
        source.add("");
        source.add(GenUtil.getIndent(2) + "try {");
        source.add(GenUtil.getIndent(2) + "    //SQL文");
        source.add(GenUtil.getIndent(2) + "    SqlBuffer sql = new SqlBuffer();");

        //SQL文
        List sql = sqlGen__.generateCreateTableSqlList();
        Iterator it = sql.iterator();
        while (it.hasNext()) {
            //
            String line = (String) it.next();
            line = GenUtil.escapeSrc(line);
            source.add(GenUtil.getIndent(3) + "sql.addSql(\"" + line + "\");");
        }

        source.add("");
        source.add(GenUtil.getIndent(2) + "    pstmt = con.prepareStatement(sql.toSqlString());");
        source.add(GenUtil.getIndent(2) + "    log__.info(sql.toLogString());");
        source.add(GenUtil.getIndent(2) + "    pstmt.executeUpdate();");
//        source.add(GenUtil.getIndent(2) + "    count = pstmt.executeUpdate();");
        source.add(GenUtil.getIndent(2) + "} catch (SQLException e) {");
        source.add(GenUtil.getIndent(2) + "    throw e;");
        source.add(GenUtil.getIndent(2) + "} finally {");
        source.add(GenUtil.getIndent(2) + "    JDBCUtil.closeStatement(pstmt);");
        source.add(GenUtil.getIndent(2) + "}");

        source.add(GenConst.INDENT + "}");
        source.add("");
        return source;
    }

    /**
     * <p>Insertを生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createInsertSql(ArrayList<String> source) {
        //テーブル名
        String tblName = table__.getName();
        //BEAN
        String bean = __createModelClassName(tblName);

        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>Insert " + tblName + " Data Bindding JavaBean");
        source.add(GenConst.INDENT + " * @param bean " + tblName + " Data Bindding JavaBean");
        source.add(GenConst.INDENT + " * @throws SQLException SQL実行例外");
        source.add(GenConst.INDENT + " */");
        source.add(GenConst.INDENT + "public void insert(" + bean + " bean) throws SQLException {");
        source.add("");
        source.add(GenUtil.getIndent(2) + "PreparedStatement pstmt = null;");
//        source.add(GenUtil.getIndent(2) + "int count = 0;");
        source.add(GenUtil.getIndent(2) + "Connection con = null;");
        source.add(GenUtil.getIndent(2) + "con = getCon();");
        source.add("");
        source.add(GenUtil.getIndent(2) + "try {");
        source.add(GenUtil.getIndent(2) + "    //SQL文");
        source.add(GenUtil.getIndent(2) + "    SqlBuffer sql = new SqlBuffer();");

        //SQL文
        List sql = sqlGen__.generateInsertSqlList();
        Iterator it = sql.iterator();
        while (it.hasNext()) {
            //
            String line = (String) it.next();
            line = GenUtil.escapeSrc(line);
            source.add(GenUtil.getIndent(3) + "sql.addSql(\"" + line + "\");");
        }
        source.add("");
        source.add(GenUtil.getIndent(2) + "    pstmt = con.prepareStatement(sql.toSqlString());");

        //値をセット
        ArrayList cols = table__.getColumns();
        Iterator it2 = cols.iterator();
        int cnt = 0;
        while (it2.hasNext()) {
            cnt++;
            Column col = (Column) it2.next();
            //beanのGETメソッドを取得
            String colName = col.getName();
            String gmethodName = GenUtil.createMethodName(true, colName);

            //PrepareStatementのSetメソッドを取得
            String smethodName = GenUtil.getSetMethodNameOfPstmt(col.getType());

            //型変換用メソッド名を取得
            String rep = "bean." + gmethodName + "()";
            String addValue = GenUtil.getAddValueMethodName(col.getType());

            source.add(
                    GenUtil.getIndent(2)
                        + "    sql." + addValue
                        + "("
                        + rep
                        + ");");
        }
        source.add(GenUtil.getIndent(2) + "    log__.info(sql.toLogString());");
        source.add(GenUtil.getIndent(2) + "    sql.setParameter(pstmt);");
        source.add(GenUtil.getIndent(2) + "    pstmt.executeUpdate();");
//        source.add(GenUtil.getIndent(2) + "    count = pstmt.executeUpdate();");
        source.add(GenUtil.getIndent(2) + "} catch (SQLException e) {");
        source.add(GenUtil.getIndent(2) + "    throw e;");
        source.add(GenUtil.getIndent(2) + "} finally {");
        source.add(GenUtil.getIndent(2) + "    JDBCUtil.closeStatement(pstmt);");
        source.add(GenUtil.getIndent(2) + "}");

        source.add(GenConst.INDENT + "}");
        source.add("");
        return source;
    }

    /**
     * <p>Updateを生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createUpdateSql(ArrayList<String> source) {
        //テーブル名
        String tblName = table__.getName();
        //BEAN
        String bean = __createModelClassName(tblName);

        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>Update " + tblName + " Data Bindding JavaBean");
        source.add(GenConst.INDENT + " * @param bean " + tblName + " Data Bindding JavaBean");
        source.add(GenConst.INDENT + " * @return 更新件数");
        source.add(GenConst.INDENT + " * @throws SQLException SQL実行例外");
        source.add(GenConst.INDENT + " */");
        source.add(GenConst.INDENT + "public int update(" + bean + " bean) throws SQLException {");
        source.add("");
        source.add(GenUtil.getIndent(2) + "PreparedStatement pstmt = null;");
        source.add(GenUtil.getIndent(2) + "int count = 0;");
        source.add(GenUtil.getIndent(2) + "Connection con = null;");
        source.add(GenUtil.getIndent(2) + "con = getCon();");
        source.add("");
        source.add(GenUtil.getIndent(2) + "try {");
        source.add(GenUtil.getIndent(2) + "    //SQL文");
        source.add(GenUtil.getIndent(2) + "    SqlBuffer sql = new SqlBuffer();");

        //SQL文
        List sql = sqlGen__.generateUpdateSqlList();
        Iterator it = sql.iterator();
        while (it.hasNext()) {
            //
            String line = (String) it.next();
            line = GenUtil.escapeSrc(line);
            source.add(GenUtil.getIndent(3) + "sql.addSql(\"" + line + "\");");
        }
        source.add("");
        source.add(GenUtil.getIndent(2) + "    pstmt = con.prepareStatement(sql.toSqlString());");

        //値をセット
        ArrayList cols = table__.getColumns();
        Iterator it2 = cols.iterator();
        int cnt = 0;
        while (it2.hasNext()) {
            Column col = (Column) it2.next();
            if (col.getSeqKey() > 0) {
                continue;
            }
            cnt++;
            //beanのGETメソッドを取得
            String colName = col.getName();
            String gmethodName = GenUtil.createMethodName(true, colName);

            //PrepareStatementのSetメソッドを取得
            String smethodName = GenUtil.getSetMethodNameOfPstmt(col.getType());

            //型変換用メソッド名を取得
            String rep = "bean." + gmethodName + "()";
            String addValue = GenUtil.getAddValueMethodName(col.getType());

            source.add(
                GenUtil.getIndent(2)
                    + "    sql." + addValue
                    + "("
                    + rep
                    + ");");
        }
        //PrimaryKey
        ArrayList keys = table__.getPkey();
        if (!keys.isEmpty()) {
            source.add(GenUtil.getIndent(3) + "//where");
            Iterator it3 = keys.iterator();
            while (it3.hasNext()) {
                cnt++;
                Column col = (Column) it3.next();
                String addValue = GenUtil.getAddValueMethodName(col.getType());
                String mnameSetter = "sql." + addValue;
                //レコードセットよりBEANを取得するメソッド名
                mnameSetter
                    += "("
                    +  "bean." + GenUtil.createMethodName(true, col.getName() + "()")
                    +  ");";
                source.add(GenUtil.getIndent(3) + mnameSetter);
            }
        }
        source.add("");

        source.add(GenUtil.getIndent(2) + "    log__.info(sql.toLogString());");
        source.add(GenUtil.getIndent(2) + "    sql.setParameter(pstmt);");
        source.add(GenUtil.getIndent(2) + "    count = pstmt.executeUpdate();");
        source.add(GenUtil.getIndent(2) + "} catch (SQLException e) {");
        source.add(GenUtil.getIndent(2) + "    throw e;");
        source.add(GenUtil.getIndent(2) + "} finally {");
        source.add(GenUtil.getIndent(2) + "    JDBCUtil.closeStatement(pstmt);");
        source.add(GenUtil.getIndent(2) + "}");
        source.add(GenUtil.getIndent(2) + "return count;");

        source.add(GenConst.INDENT + "}");
        source.add("");
        return source;
    }

    /**
     * <p>全てのレコードを取得するSelect文を生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createSelectAllSql(ArrayList<String> source) {
        //テーブル名
        String tblName = table__.getName();
        //BEAN
        String bean = __createModelClassName(tblName);

        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>Select " + tblName + " All Data");
        source.add(GenConst.INDENT + " * @return List in " + tblName + "Model");
        source.add(GenConst.INDENT + " * @throws SQLException SQL実行例外");
        source.add(GenConst.INDENT + " */");
        source.add(GenConst.INDENT + "public List select() throws SQLException {");
        source.add("");
        source.add(GenUtil.getIndent(2) + "PreparedStatement pstmt = null;");
        source.add(GenUtil.getIndent(2) + "ResultSet rs = null;");
        source.add(GenUtil.getIndent(2) + "Connection con = null;");
        source.add(GenUtil.getIndent(2) + "ArrayList ret = new ArrayList();");
        source.add(GenUtil.getIndent(2) + "con = getCon();");
        source.add("");
        source.add(GenUtil.getIndent(2) + "try {");
        source.add(GenUtil.getIndent(2) + "    //SQL文");
        source.add(GenUtil.getIndent(2) + "    SqlBuffer sql = new SqlBuffer();");

        //SQL文
        List sql = sqlGen__.generateSelectSqlAllList();
        Iterator it = sql.iterator();
        while (it.hasNext()) {
            //
            String line = (String) it.next();
            line = GenUtil.escapeSrc(line);
            source.add(GenUtil.getIndent(3) + "sql.addSql(\"" + line + "\");");
        }
        source.add("");
        source.add(GenUtil.getIndent(2) + "    pstmt = con.prepareStatement(sql.toSqlString());");
        source.add(GenUtil.getIndent(2) + "    log__.info(sql.toLogString());");
        source.add(GenUtil.getIndent(2) + "    rs = pstmt.executeQuery();");
        source.add(GenUtil.getIndent(2) + "    while (rs.next()) {");

        //レコードセットよりBEANを取得するメソッド名
        String mnameRs =
            GenUtil.createResultsetGetterName(
                tblName,
                TypeMappingResourceBundle.getString("table_prefix"));
        source.add(GenUtil.getIndent(2) + "        ret.add(" + mnameRs + "(rs));");
        source.add(GenUtil.getIndent(2) + "    }");
        source.add(GenUtil.getIndent(2) + "} catch (SQLException e) {");
        source.add(GenUtil.getIndent(2) + "    throw e;");
        source.add(GenUtil.getIndent(2) + "} finally {");
        source.add(GenUtil.getIndent(2) + "    JDBCUtil.closeResultSet(rs);");
        source.add(GenUtil.getIndent(2) + "    JDBCUtil.closeStatement(pstmt);");
        source.add(GenUtil.getIndent(2) + "}");
        source.add(GenUtil.getIndent(2) + "return ret;");
        source.add(GenConst.INDENT + "}");
        source.add("");
        return source;
    }

    /**
     * <p>全てのレコードを取得するSelect文を生成します。
     * @param source ソース
     * @param cSql 任意のSQL
     * @return 生成したソース
     */
    private ArrayList __createSelectAllSql(ArrayList<String> source, List cSql) {
        //テーブル名
        String tblName = table__.getName();
        //BEAN
        String bean = __createModelClassName(tblName);

        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>Select " + tblName + " All Data");
        source.add(GenConst.INDENT + " * @return List in " + tblName + "Model");
        source.add(GenConst.INDENT + " * @throws SQLException SQL実行例外");
        source.add(GenConst.INDENT + " */");
        source.add(GenConst.INDENT + "public List select() throws SQLException {");
        source.add("");
        source.add(GenUtil.getIndent(2) + "PreparedStatement pstmt = null;");
        source.add(GenUtil.getIndent(2) + "ResultSet rs = null;");
        source.add(GenUtil.getIndent(2) + "Connection con = null;");
        source.add(GenUtil.getIndent(2) + "ArrayList ret = new ArrayList();");
        source.add(GenUtil.getIndent(2) + "con = getCon();");
        source.add("");
        source.add(GenUtil.getIndent(2) + "try {");
        source.add(GenUtil.getIndent(2) + "    //SQL文");
        source.add(GenUtil.getIndent(2) + "    SqlBuffer sql = new SqlBuffer();");

        //SQL文
        List sql = cSql;
        Iterator it = sql.iterator();
        while (it.hasNext()) {
            //
            String line = (String) it.next();
            line = GenUtil.escapeSrc(line);
            source.add(GenUtil.getIndent(3) + "sql.addSql(\"" + line + "\");");
        }
        source.add("");
        source.add(GenUtil.getIndent(2) + "    log__.info(sql.toLogString());");
        source.add(GenUtil.getIndent(2) + "    pstmt = con.prepareStatement(sql.toSqlString());");
        source.add(GenUtil.getIndent(2) + "    sql.setParameter(pstmt);");
        source.add(GenUtil.getIndent(2) + "    rs = pstmt.executeQuery();");
        source.add(GenUtil.getIndent(2) + "    while (rs.next()) {");

        //レコードセットよりBEANを取得するメソッド名
        String mnameRs =
            GenUtil.createResultsetGetterName(
                tblName,
                TypeMappingResourceBundle.getString("table_prefix"));
        source.add(GenUtil.getIndent(2) + "        ret.add(" + mnameRs + "(rs));");
        source.add(GenUtil.getIndent(2) + "    }");
        source.add(GenUtil.getIndent(2) + "} catch (SQLException e) {");
        source.add(GenUtil.getIndent(2) + "    throw e;");
        source.add(GenUtil.getIndent(2) + "} finally {");
        source.add(GenUtil.getIndent(2) + "    JDBCUtil.closeResultSet(rs);");
        source.add(GenUtil.getIndent(2) + "    JDBCUtil.closeStatement(pstmt);");
        source.add(GenUtil.getIndent(2) + "}");
        source.add(GenUtil.getIndent(2) + "return ret;");
        source.add(GenConst.INDENT + "}");
        source.add("");
        return source;
    }

    /**
     * <p>プライマリーキーを検索条件にするSelect文を生成します。
     * @param source ソース
     * @return 生成したソース
     */
    @SuppressWarnings("unchecked")
    private ArrayList __createSelectSql(ArrayList<String> source) {
        //テーブル名
        String tblName = table__.getName();
        //BEAN
        String bean = __createModelClassName(tblName);

        //引数パラメータオブジェクトの作成
        ArrayList keys = table__.getPkey();
        ArrayList params = new ArrayList();
        if (keys.isEmpty()) {
            //キーなしの場合は空のリストを返す
            return new ArrayList();
        } else {
            //引数の型を取得する
            Iterator it2 = keys.iterator();
            while (it2.hasNext()) {
                Column col = (Column) it2.next();
                //データ型
                String type = GenUtil.getTypeNameJdbc(col.getType());
                //変数名
                String paramName = GenUtil.createParamName(col.getName());
                JavaParamModel model = new JavaParamModel();
                model.setTypeName(type);
                model.setParamName(paramName);
                model.setCol(col);
                params.add(model);
            }
        }

        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>Select " + tblName);

        //引数のJavaDocを生成
        StringBuilder paramBuf = new StringBuilder();
        Iterator paramIt = params.iterator();
        int pcnt = 0;
        while (paramIt.hasNext()) {
            pcnt++;
            JavaParamModel model = (JavaParamModel) paramIt.next();
            // JavaDocコメント
            source.add(GenConst.INDENT + " * @param " + model.getParamName()
                    + " " + model.getCol().getName());
            // 引数文字列も生成する
            if (pcnt > 1) {
                paramBuf.append(", ");
            }
            paramBuf.append(model.getTypeName() + " " + model.getParamName());
        }

        source.add(GenConst.INDENT + " * @return " + tblName + "Model");
        source.add(GenConst.INDENT + " * @throws SQLException SQL実行例外");
        source.add(GenConst.INDENT + " */");
        source.add(
            GenConst.INDENT
                + "public "
                + bean
                + " select("
                + paramBuf.toString()
                + ") throws SQLException {");
        source.add("");
        source.add(GenUtil.getIndent(2) + "PreparedStatement pstmt = null;");
        source.add(GenUtil.getIndent(2) + "ResultSet rs = null;");
        source.add(GenUtil.getIndent(2) + "Connection con = null;");
        source.add(GenUtil.getIndent(2) + bean + " ret = null;");
        source.add(GenUtil.getIndent(2) + "con = getCon();");
        source.add("");
        source.add(GenUtil.getIndent(2) + "try {");
        source.add(GenUtil.getIndent(2) + "    //SQL文");
        source.add(GenUtil.getIndent(2) + "    SqlBuffer sql = new SqlBuffer();");

        //SQL文
        List sql = sqlGen__.generateSelectSqlList();
        Iterator it = sql.iterator();
        while (it.hasNext()) {
            //
            String line = (String) it.next();
            line = GenUtil.escapeSrc(line);
            source.add(GenUtil.getIndent(3) + "sql.addSql(\"" + line + "\");");
        }
        source.add("");
        source.add(GenUtil.getIndent(3) + "pstmt = con.prepareStatement(sql.toSqlString());");

        //PrimaryKey
        paramIt = params.iterator();
        while (paramIt.hasNext()) {
            pcnt++;
            JavaParamModel model = (JavaParamModel) paramIt.next();
            Column col = model.getCol();
            String addValue = GenUtil.getAddValueMethodName(col.getType());
            String mnameSetter = "sql." + addValue;
            //レコードセットよりBEANを取得するメソッド名
            mnameSetter
                += "("
                + model.getParamName()
                +  ");";
            //
            source.add(GenUtil.getIndent(3) + mnameSetter);
        }
        source.add("");
        source.add(GenUtil.getIndent(2) + "    log__.info(sql.toLogString());");
        source.add(GenUtil.getIndent(2) + "    sql.setParameter(pstmt);");
        source.add(GenUtil.getIndent(2) + "    rs = pstmt.executeQuery();");
        source.add(GenUtil.getIndent(2) + "    if (rs.next()) {");

        //レコードセットよりBEANを取得するメソッド名
        String mnameRs =
            GenUtil.createResultsetGetterName(
                tblName,
                TypeMappingResourceBundle.getString("table_prefix"));
        source.add(GenUtil.getIndent(2) + "        ret = " + mnameRs + "(rs);");
        source.add(GenUtil.getIndent(2) + "    }");
        source.add(GenUtil.getIndent(2) + "} catch (SQLException e) {");
        source.add(GenUtil.getIndent(2) + "    throw e;");
        source.add(GenUtil.getIndent(2) + "} finally {");
        source.add(GenUtil.getIndent(2) + "    JDBCUtil.closeResultSet(rs);");
        source.add(GenUtil.getIndent(2) + "    JDBCUtil.closeStatement(pstmt);");
        source.add(GenUtil.getIndent(2) + "}");
        source.add(GenUtil.getIndent(2) + "return ret;");
        source.add(GenConst.INDENT + "}");
        source.add("");
        return source;
    }

    /**
     * <p>プライマリーキーを検索条件にするDelete文を生成します。
     * @param source ソース
     * @return 生成したソース
     */
    @SuppressWarnings("unchecked")
    private ArrayList __createDeleteSql(ArrayList<String> source) {
        //テーブル名
        String tblName = table__.getName();
        //BEAN
        String bean = __createModelClassName(tblName);

        //引数パラメータオブジェクトの作成
        ArrayList keys = table__.getPkey();
        ArrayList params = new ArrayList();
        if (keys.isEmpty()) {
            // キーなしの場合は空のリストを返す
            return new ArrayList();
        } else {
            //引数の型を取得する
            Iterator it2 = keys.iterator();
            while (it2.hasNext()) {
                Column col = (Column) it2.next();
                //データ型
                String type = GenUtil.getTypeNameJdbc(col.getType());
                //変数名
                String paramName = GenUtil.createParamName(col.getName());
                JavaParamModel model = new JavaParamModel();
                model.setTypeName(type);
                model.setParamName(paramName);
                model.setCol(col);
                params.add(model);
            }
        }

        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>Delete " + tblName);

        //引数のJavaDocを生成
        StringBuilder paramBuf = new StringBuilder();
        Iterator paramIt = params.iterator();
        int pcnt = 0;
        while (paramIt.hasNext()) {
            pcnt++;
            JavaParamModel model = (JavaParamModel) paramIt.next();
            // JavaDocコメント
            source.add(GenConst.INDENT + " * @param " + model.getParamName()
                    + " " + model.getCol().getName());
            // 引数文字列も生成する
            if (pcnt > 1) {
                paramBuf.append(", ");
            }
            paramBuf.append(model.getTypeName() + " " + model.getParamName());
        }


        source.add(GenConst.INDENT + " * @throws SQLException SQL実行例外");
        source.add(GenConst.INDENT + " */");
        source.add(
            GenConst.INDENT
                + "public"
                + " int"
                + " delete("
                + paramBuf.toString()
                + ") throws SQLException {");
        source.add("");
        source.add(GenUtil.getIndent(2) + "PreparedStatement pstmt = null;");
//        source.add(GenUtil.getIndent(2) + "ResultSet rs = null;");
        source.add(GenUtil.getIndent(2) + "int count = 0;");
        source.add(GenUtil.getIndent(2) + "Connection con = null;");
        source.add(GenUtil.getIndent(2) + "con = getCon();");
        source.add("");
        source.add(GenUtil.getIndent(2) + "try {");
        source.add(GenUtil.getIndent(2) + "    //SQL文");
        source.add(GenUtil.getIndent(2) + "    SqlBuffer sql = new SqlBuffer();");

        //SQL文
        List sql = sqlGen__.generateDeleteSqlList();
        Iterator it = sql.iterator();
        while (it.hasNext()) {
            //
            String line = (String) it.next();
            line = GenUtil.escapeSrc(line);
            source.add(GenUtil.getIndent(3) + "sql.addSql(\"" + line + "\");");
        }
        source.add("");
        source.add(GenUtil.getIndent(3) + "pstmt = con.prepareStatement(sql.toSqlString());");

        //PrimaryKey
        paramIt = params.iterator();
        while (paramIt.hasNext()) {
            pcnt++;
            JavaParamModel model = (JavaParamModel) paramIt.next();
            Column col = model.getCol();
            String addValue = GenUtil.getAddValueMethodName(col.getType());
            String mnameSetter = "sql." + addValue;
            // レコードセットよりBEANを取得するメソッド名
            mnameSetter += "(" + model.getParamName() + ");";
            //
            source.add(GenUtil.getIndent(3) + mnameSetter);
        }
        source.add("");
        source.add(GenUtil.getIndent(2) + "    log__.info(sql.toLogString());");
        source.add(GenUtil.getIndent(2) + "    sql.setParameter(pstmt);");
        source.add(GenUtil.getIndent(2) + "    count = pstmt.executeUpdate();");
        source.add(GenUtil.getIndent(2) + "} catch (SQLException e) {");
        source.add(GenUtil.getIndent(2) + "    throw e;");
        source.add(GenUtil.getIndent(2) + "} finally {");
        source.add(GenUtil.getIndent(2) + "    JDBCUtil.closeStatement(pstmt);");
        source.add(GenUtil.getIndent(2) + "}");
        source.add(GenUtil.getIndent(2) + "return count;");
        source.add(GenConst.INDENT + "}");
        source.add("");
        return source;
    }

    /**
     * <p>Select文で使用する結果セットよりBEANを生成するメソッドを生成します。
     * @param source ソース
     * @return 生成したソース
     */
    public ArrayList __createSelectBeanFromRs(ArrayList<String> source) {
        //テーブル名
        String tblName = table__.getName();
        //BEAN
        String bean = __createModelClassName(tblName);
        //レコードセットよりBEANを取得するメソッド名
        String mnameRs =
            GenUtil.createResultsetGetterName(
                tblName,
                TypeMappingResourceBundle.getString("table_prefix"));

        source.add(GenConst.INDENT + "/**");
        source.add(
            GenConst.INDENT + " * <p>Create " + tblName + " Data Bindding JavaBean From ResultSet");
        source.add(GenConst.INDENT + " * @param rs ResultSet");
        source.add(GenConst.INDENT + " * @return created " + bean);
        source.add(GenConst.INDENT + " * @throws SQLException SQL実行例外");
        source.add(GenConst.INDENT + " */");
        source.add(
            GenConst.INDENT
                + "private "
                + bean
                + " "
                + mnameRs
                + "(ResultSet rs) throws SQLException {");
        source.add(GenUtil.getIndent(2) + bean + " bean = new " + bean + "();");

        //値をセット
        ArrayList cols = table__.getColumns();
        Iterator it = cols.iterator();
        while (it.hasNext()) {
            Column col = (Column) it.next();
            //beanのSETメソッドを取得
            String colName = col.getName();
            String smethodName = GenUtil.createMethodName(false, colName);

            //ResultSetからの取得メソッド
            String gmethodName = GenUtil.getGetMethodNameFromRs(col.getType());
            String rep = "rs." + gmethodName + "(\"" + col.getName() + "\")";

            //型変換用メソッド名を取得
            String castMethod = GenUtil.getGetTypeCastMethodName(col.getType(), rep);
            if (castMethod == null) {
                source.add(GenUtil.getIndent(2) + "bean." + smethodName + "(" + rep + ");");
            } else {
                source.add(GenUtil.getIndent(2) + "bean." + smethodName + "(" + castMethod + ");");
            }
        }
        source.add(GenUtil.getIndent(2) + "return bean;");
        source.add(GenConst.INDENT + "}");
        return null;
    }
    /**
     * @return packageName を戻します。
     */
    public String getPackageName() {
        return packageName__;
    }
    /**
     * @param packageName packageName を設定。
     */
    public void setPackageName(String packageName) {
        packageName__ = packageName;
    }

    /**
     * <br>
     * [機 能] モデルパッケージ名を取得する。<br>
     * [解 説] <br>
     * [備 考]
     * @return パッケージ名
     */
    public String getModelPackageName() {
        return modelPackageName__;
    }

    /**
     * <br>
     * [機 能] モデルクラスのパッケージ名を返す<br>
     * [解 説] <br>
     * [備 考]
     * @param modelPackageName パッケージ名
     */
    public void setModelPackageName(String modelPackageName) {
        modelPackageName__ = modelPackageName;
    }

    /**
     * <br>
     * [機 能] daoClassNameFlgを返す。<br>
     * [解 説] <br>
     * [備 考]
     * @return daoClassNameFlg
     */
    public boolean isDaoClassNameFlg() {
        return daoClassNameFlg__;
    }

    /**
     * <br>
     * [機 能] daoClassNameFlgをセットする。<br>
     * [解 説] <br>
     * [備 考]
     * @param daoClassNameFlg daoClassNameFlg
     */
    public void setDaoClassNameFlg(boolean daoClassNameFlg) {
        this.daoClassNameFlg__ = daoClassNameFlg;
    }

    /**
     * <br>
     * [機 能] modelClassNameFlg__を返す<br>
     * [解 説] <br>
     * [備 考]
     * @return modelClassNameFlg
     */
    public boolean isModelClassNameFlg() {
        return modelClassNameFlg__;
    }

    /**
     * <br>
     * [機 能] modelClassNameFlgをセットする。<br>
     * [解 説] <br>
     * [備 考]
     * @param modelClassNameFlg modelClassNameFlg
     */
    public void setModelClassNameFlg(boolean modelClassNameFlg) {
        this.modelClassNameFlg__ = modelClassNameFlg;
    }
}
