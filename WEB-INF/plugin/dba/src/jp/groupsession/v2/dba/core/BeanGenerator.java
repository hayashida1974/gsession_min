package jp.groupsession.v2.dba.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.dba.meta.Column;
import jp.groupsession.v2.dba.meta.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] テーブルの列情報からJavaBeanを作成するクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class BeanGenerator {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BeanGenerator.class);

    /** テーブルオブジェクト */
    private Table table__ = null;
    /** パッケージ名 */
    private String packageName__ = null;

    /** クラス名をテーブル名 + Model でそのまま使用するフラグ true:そのまま使用, false:変換する */
    private boolean classNameFlg__ = false;

    /**
     * <p>デフォルトコンストラクタ
     */
    public BeanGenerator() {
    }

    /**
     * <p>テーブル情報をセットします
     * @param table テーブル
     */
    public BeanGenerator(Table table) {
        table__ = table;
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
     * <p>BEANのJavaソースを生成します。
     * @return Javaソース
     */
    public String generate() {
        log__.debug("generate start");
        //
        ArrayList<String> source = new ArrayList<String>();

        //パッケージ名を生成
        __createPackage(source);

        //import行を生成
        __createImport(source);

        //クラス名の行を生成
        __createClassName(source);

        //フィールド行を生成
        __createField(source);

        //コンストラクタの生成
        __createConstructor(source);

        //Getter,Setterの生成
        __createGetSeter(source);

        //toCsvString
        __createCsvString(source);

        //フッター }のみ生成
        source.add("}");
        log__.debug("generate end");
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

        //シリアライズは必須
        source.add("import " + TypeMappingResourceBundle.getString("NullDefault") + ";");
        source.add("import java.io.Serializable;");
        source.add("");
        return source;
    }

    /**
     * モデルクラス名を返す
     * @param tableName テーブル名
     * @return モデルクラス名
     */
    private String __createModelClassName(String tableName) {
        if (classNameFlg__) {
            return tableName + "Model";
        } else {
            return GenUtil.createClassName(tableName, false,
                    TypeMappingResourceBundle.getString("table_prefix"));
        }
    }

    /**
     * <p>クラス宣言を生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createClassName(ArrayList<String> source) {
        String tblName = table__.getName();
        String clsName = __createModelClassName(tblName);

        //
        String line = "public class " + clsName + " implements Serializable {";

        //クラス宣言
        source.add("/**");
        source.add(" * <p>" + tblName + " Data Bindding JavaBean");
        source.add(" *");
        source.add(" * @author " + GenConst.PRODUCT_NAME + " version " + GenConst.VERSION);
        source.add(" */");
        source.add(line);
        //空行
        source.add("");
        return source;
    }

    /**
     * <p>フィールド行を生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createField(ArrayList<String> source) {
        ArrayList cols = table__.getColumns();
        Iterator it = cols.iterator();
        while (it.hasNext()) {

            Column col = (Column) it.next();

            String colName = col.getName();

            //フィールド名
            String fldName = GenUtil.createFieldName(colName);

            //型
            int type = col.getType();
            String typeStr = GenUtil.getTypeNameJdbc(type);

            //JavaDocコメント
            source.add(GenConst.INDENT + "/** " + colName + " mapping */");
            //フィールド宣言
            String line = GenConst.INDENT + "private " + typeStr + " " + fldName + ";";
            source.add(line);
        }

        //空行
        source.add("");
        return source;
    }

//    /**
//     *
//     * @return JDBCに対応したJavaのデータ型
//     */
//    private String __getTypeStr(Column col) {
//        String ret = null;
//        int type = col.getType();
//        int size = col.getSize();
//
//        switch (type) {
//            case Types.ARRAY :
//                log__.debug("ARRAY");
//            case Types.BIGINT :
//                log__.debug("BIGINT");
//            case Types.BINARY :
//                log__.debug("BINARY");
//            case Types.BIT :
//                log__.debug("BIT");
//            case Types.BLOB :
//                log__.debug("BLOB");
//            case Types.BOOLEAN :
//                log__.debug("BOOLEAN");
//            case Types.CHAR :
//                log__.debug("CHAR");
//            case Types.CLOB :
//                log__.debug("CLOB");
//            case Types.DATALINK :
//                log__.debug("DATALINK");
//            case Types.DATE :
//                ret = "Date";
//                log__.debug("DATE");
//                break;
//
//            case Types.DECIMAL :
//                log__.debug(Types.DECIMAL + "DECIMAL");
//            case Types.DISTINCT :
//                log__.debug(Types.DISTINCT + " DISTINCT");
//            case Types.DOUBLE :
//                log__.debug(Types.DOUBLE + " DOUBLE");
//            case Types.FLOAT :
//                log__.debug(Types.FLOAT + " FLOAT");
//            case Types.INTEGER : //3
//                log__.debug(Types.INTEGER + " INTEGER");
//                ret = "NUMBER(" + col.getSize() + "," + col.getDigits() + ")";
//                break;
//
//            case Types.JAVA_OBJECT :
//                log__.debug("JAVA_OBJECT");
//            case Types.LONGVARBINARY :
//                log__.debug("LONGVARBINARY");
//            case Types.LONGVARCHAR :
//                log__.debug("LONGVARCHAR");
//            case Types.NULL :
//                log__.debug("NULL");
//            case Types.NUMERIC :
//                log__.debug("NUMERIC");
//            case Types.OTHER :
//                log__.debug("OTHER");
//            case Types.REAL :
//                log__.debug("REAL");
//            case Types.REF :
//                log__.debug("REF");
//            case Types.SMALLINT :
//                log__.debug("SMALLINT");
//            case Types.STRUCT :
//                log__.debug("STRUCT");
//            case Types.TIME :
//                log__.debug("TIME");
//            case Types.TIMESTAMP :
//                log__.debug("TIMESTAMP");
//            case Types.TINYINT :
//                log__.debug("TINYINT");
//            case Types.VARBINARY :
//                log__.debug("VARBINARY");
//            case Types.VARCHAR :
//                log__.debug("VARCHAR");
//                ret = "varchar(" + size + ")";
//                break;
//            default :
//                log__.debug("UNKNOWN");
//                ret = "UNKNOWN";
//        }
//
//        //Not Null
//        String notNull = "";
//        if (java.sql.DatabaseMetaData.columnNoNulls == col.getNullable()) {
//            notNull = " not null";
//        }
//        ret += notNull;
//
//        log__.debug(col.getName() + " type = " + ret + "| " + col.getType());
//        log__.debug(col.getName() + " size = " + size);
//
//        return ret;
//    }

    /**
     * <p>デフォルトコンストラクタを生成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createConstructor(ArrayList<String> source) {
        String tblName = table__.getName();
        String clsName = __createModelClassName(tblName);

        //クラス宣言
        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>Default Constructor");
        source.add(GenConst.INDENT + " */");
        //
        String line = GenConst.INDENT + "public " + clsName + "() {";
        source.add(line);
        source.add(GenConst.INDENT + "}");
        //空行
        source.add("");
        return source;
    }

    /**
     * <p>ゲッター、セッターを作成します。
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createGetSeter(ArrayList<String> source) {
        ArrayList cols = table__.getColumns();
        Iterator it = cols.iterator();
        while (it.hasNext()) {
            Column col = (Column) it.next();
            __createGetter(source, col);
            __createSetter(source, col);
        }
        return source;
    }

    /**
     * <p>ゲッターを作成します。
     * @param source ソース
     * @param col 列情報
     * @return 生成したソース
     */
    private ArrayList __createGetter(ArrayList<String> source, Column col) {
        //テーブル列名
        String colName = col.getName();

        //Javaフィールド名
        String fldName = GenUtil.createFieldName(colName);

        //Java型
        int type = col.getType();
        String typeStr = GenUtil.getTypeNameJdbc(type);

        //引数の名称
        String hName = GenUtil.createFieldName2(colName);

        //JavaDocコメント
        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>get " + colName + " value");
        source.add(GenConst.INDENT + " * @return " + colName + " value");
        source.add(GenConst.INDENT + " */");

        //メソッド名宣言
        String methodName = GenUtil.createMethodName(true, colName);
        source.add(GenConst.INDENT + "public " + typeStr + " " + methodName + "() {");

        //return
        source.add(GenConst.INDENT + GenConst.INDENT + "return " + fldName + ";");

        //閉じ括弧
        source.add(GenConst.INDENT + "}");
        source.add("");
        return source;
    }

    /**
     * <p>セッターを作成します。
     * @param source ソース
     * @param col 列情報
     * @return 生成したソース
     */
    private ArrayList __createSetter(ArrayList<String> source, Column col) {
        //テーブル列名
        String colName = col.getName();

        //Javaフィールド名
        String fldName = GenUtil.createFieldName(colName);

        //Java型
        int type = col.getType();
        String typeStr = GenUtil.getTypeNameJdbc(type);

        //引数の名称
        String hName = GenUtil.createFieldName2(colName);

        //JavaDocコメント
        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>set " + colName + " value");
        source.add(GenConst.INDENT + " * @param " + hName + " " + colName + " value");
        source.add(GenConst.INDENT + " */");

        //メソッド名宣言
        String methodName = GenUtil.createMethodName(false, colName);
        source.add(
            GenConst.INDENT + "public void " + methodName + "(" + typeStr + " " + hName + ") {");

        //return
        source.add(GenConst.INDENT + GenConst.INDENT + fldName + " = " + hName + ";");

        //閉じ括弧
        source.add(GenConst.INDENT + "}");
        source.add("");
        return source;
    }

    /**
     * <p>beanのCSV形式を返します
     * @param source ソース
     * @return 生成したソース
     */
    private ArrayList __createCsvString(ArrayList<String> source) {
        ArrayList cols = table__.getColumns();
        Iterator it = cols.iterator();

        //JavaDocコメント
        source.add(GenConst.INDENT + "/**");
        source.add(GenConst.INDENT + " * <p>to Csv String");
        source.add(GenConst.INDENT + " * @return Csv String");
        source.add(GenConst.INDENT + " */");

        //メソッド名宣言
        source.add(
            GenConst.INDENT + "public String toCsvString() {");
        //StringBuffer
        source.add(GenUtil.getIndent(2) + "StringBuffer buf = new StringBuffer();");
        while (it.hasNext()) {
            Column col = (Column) it.next();
            //テーブル列名
            String colName = col.getName();
            //Javaフィールド名
            String fldName = GenUtil.createFieldName(colName);
            //Java型
            int type = col.getType();
            String typeStr = GenUtil.getTypeNameJdbc(type);
            //
            if (typeStr.equals("int") || typeStr.equals("double") || typeStr.equals("long")) {
                //int,double,long
                source.add(GenUtil.getIndent(2) + "buf.append(" + fldName + ");");
            } else if (typeStr.equals("String")) {
                // String
                source.add(GenUtil.getIndent(2)
                        + "buf.append(NullDefault.getString(" + fldName
                        + ", \"\"));");
            } else if (typeStr.equals("UDate")) {
                // UDate
                source.add(GenUtil.getIndent(2)
                        + "buf.append(NullDefault.getStringFmObj(" + fldName
                        + ", \"\"));");
            } else {
                // その他
                source.add(GenUtil.getIndent(2)
                        + "buf.append(NullDefault.getStringFmObj(" + fldName
                        + ", \"\"));");
            }
            if (it.hasNext()) {
                source.add(GenUtil.getIndent(2) + "buf.append(\",\");");
            }
        }
        //戻り値
        source.add(GenUtil.getIndent(2) + "return buf.toString();");
        //閉じ括弧
        source.add(GenConst.INDENT + "}");
        source.add("");
        return source;
    }

    /**
     * <br>[機  能] classNameFlgを判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @return classNameFlg
     */
    public boolean isClassNameFlg() {
        return classNameFlg__;
    }

    /**
     * <br>[機  能] classNameFlgをセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param classNameFlg classNameFlg
     */
    public void setClassNameFlg(boolean classNameFlg) {
        this.classNameFlg__ = classNameFlg;
    }
}
