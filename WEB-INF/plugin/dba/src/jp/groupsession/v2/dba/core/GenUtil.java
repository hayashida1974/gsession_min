/*
 * create   : 2004/07/30
 * modified :
 */
package jp.groupsession.v2.dba.core;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.text.CharacterIterator;
import java.text.MessageFormat;
import java.text.StringCharacterIterator;

/**
 * <br>[機  能] Generatorで使用するユーティリティ
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class GenUtil {
    /**
     * <p>引数で指定した文のインデントを返します。
     * @param cnt インデントの数
     * @return インデント
     */
    public static String getIndent(int cnt) {
        if (cnt < 0) {
            return "";
        }

        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < cnt; i++) {
            indent.append(GenConst.INDENT);
        }
        return indent.toString();
    }

    /**
     * <p>引数で指定した文のインデントを返します。
     * @param cnt インデントの数
     * @return インデント
     */
    public static String getIndentSql(int cnt) {
        if (cnt < 0) {
            return "";
        }

        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < cnt; i++) {
            indent.append(GenConst.INDENT_SQL);
        }
        return indent.toString();
    }

    /**
     * <p>テーブル名からクラス名を作成します。
     * @param tblName テーブル
     * @param type true:Dao, false:Bean
     * @param tblPrefix テーブル名の先頭から取り除く文字列
     * @return クラス名
     */
    public static String createClassName(
        final String tblName,
        final boolean type,
        final String tblPrefix) {

        if (tblName == null) {
            return null;
        }

        String target = tblName.toLowerCase();

        //prefixを取り除く
        if (target.startsWith(tblPrefix.toLowerCase())) {
            target = target.substring(tblPrefix.length());
        }

        StringBuilder retSB = new StringBuilder();
        StringCharacterIterator stit = new StringCharacterIterator(target);

        //大文字にするためのフラグ
        boolean uperFlg = true;
        for (char c = stit.first(); c != CharacterIterator.DONE; c = stit.next()) {

            switch (c) {
                case '_' :
                    uperFlg = true;
                    break;
                case '-' :
                    uperFlg = true;
                    break;
                default :
                    if (uperFlg) {
                        retSB.append(uperCase(c));
                        uperFlg = false;
                    } else {
                        retSB.append(c);
                        uperFlg = false;
                    }
                    break;
            }
        }

        //BEAN or DAO
        if (type) {
            retSB.append("Dao");
        } else {
            retSB.append("Model");
        }
        return retSB.toString();
    }

    /**
     * <p>結果セットからBeanを作成するメソッド名を作成します。
     * @param tblName テーブル
     * @param tblPrefix テーブル名の先頭から取り除く文字列
     * @return 作成したメソッド名
     */
    public static String createResultsetGetterName(final String tblName, final String tblPrefix) {

        if (tblName == null) {
            return null;
        }

        String target = tblName.toLowerCase();

        //prefixを取り除く
        if (target.startsWith(tblPrefix.toLowerCase())) {
            target = target.substring(tblPrefix.length());
        }

        StringBuilder retSB = new StringBuilder();
        retSB.append("__get");
        StringCharacterIterator stit = new StringCharacterIterator(target);

        //大文字にするためのフラグ
        boolean uperFlg = true;
        for (char c = stit.first(); c != CharacterIterator.DONE; c = stit.next()) {

            switch (c) {
                case '_' :
                    uperFlg = true;
                    break;
                case '-' :
                    uperFlg = true;
                    break;
                default :
                    if (uperFlg) {
                        retSB.append(uperCase(c));
                        uperFlg = false;
                    } else {
                        retSB.append(c);
                        uperFlg = false;
                    }
                    break;
            }
        }
        retSB.append("FromRs");
        return retSB.toString();
    }

    /**
     * <p>charを大文字に変換する
     * @param c 変換対象
     * @return 大文字に変換した文字列
     */
    private static String uperCase(char c) {
        String s = String.valueOf(c);
        return s.toUpperCase();
    }

    /**
     * <p>テーブルの列名からJavaソースのフィールド名を作成します。
     * @param colName テーブルの列名
     * @return 作成したJavaソースフィールド名
     */
    public static String createFieldName(final String colName) {
        String javaFldSuffix = TypeMappingResourceBundle.getString("java_field_suffix");
        return createFieldName2(colName) + javaFldSuffix;
    }

    /**
     * <p>テーブルの列名からJavaソースのフィールド名を作成します。(Suffixを付加しないで)
     * @param colName テーブルの列名
     * @return 作成したJavaソースフィールド名
     */
    public static String createFieldName2(final String colName) {

        //列名の先頭から取り除く文字列
        String colPrefix = TypeMappingResourceBundle.getString("column_prefix");
        if (colName == null) {
            return null;
        }

        String target = colName.toLowerCase();
        target.toLowerCase();

        //suffixを取り除く
        if (target.startsWith(colPrefix.toLowerCase())) {
            target = target.substring(colPrefix.length());
        }

        StringBuilder retSB = new StringBuilder();
        StringCharacterIterator stit = new StringCharacterIterator(target);

        //大文字にするためのフラグ
        boolean uperFlg = false;
        for (char c = stit.first(); c != CharacterIterator.DONE; c = stit.next()) {

            switch (c) {
                case '_' :
                    uperFlg = true;
                    break;
                case '-' :
                    uperFlg = true;
                    break;
                default :
                    if (uperFlg) {
                        retSB.append(uperCase(c));
                        uperFlg = false;
                    } else {
                        retSB.append(c);
                        uperFlg = false;
                    }
                    break;
            }
        }
        return retSB.toString();
    }

    /**
     * <p>テーブルの列名からJavaソースのパラメータ名(Select用引数の名称)を作成します。
     * @param colName テーブルの列名
     * @return 作成したJavaソースフィールド名
     */
    public static String createParamName(final String colName) {
        return createFieldName2(colName);
    }

    /**
     * <p>テーブルの列名からJavaソースのメソッド名を作成します。(Getter,Setter用)
     * @param colName テーブルの列名
     * @param gsFlg true:ゲッター,false:セッター
     * @return 作成したJavaソースフィールド名
     */
    public static String createMethodName(final boolean gsFlg, final String colName) {

        //列名の先頭から取り除く文字列
        String colPrefix = TypeMappingResourceBundle.getString("column_prefix");
        if (colName == null) {
            return null;
        }

        String target = colName.toLowerCase();
        target.toLowerCase();

        //suffixを取り除く
        if (target.startsWith(colPrefix.toLowerCase())) {
            target = target.substring(colPrefix.length());
        }

        StringBuilder retSB = new StringBuilder();
        if (gsFlg) {
            //ゲッター
            retSB.append("get");
        } else {
            //セッター
            retSB.append("set");
        }

        StringCharacterIterator stit = new StringCharacterIterator(target);

        //大文字にするためのフラグ
        boolean uperFlg = true;
        for (char c = stit.first(); c != CharacterIterator.DONE; c = stit.next()) {

            switch (c) {
                case '_' :
                    uperFlg = true;
                    break;
                case '-' :
                    uperFlg = true;
                    break;
                default :
                    if (uperFlg) {
                        retSB.append(uperCase(c));
                        uperFlg = false;
                    } else {
                        retSB.append(c);
                        uperFlg = false;
                    }
                    break;
            }
        }
        return retSB.toString();
    }

    /**
     * <p>タイプ定数からBeanにセットする際に型変換を行うメソッド名を取得します
     * @param type 列タイプ
     * @param rep 置き換える文字列
     * @return 型変換を行うメソッド名
     */
    public static String getGetTypeCastMethodName(int type, String rep) {
        String format = null;
        try {
            format = __getTypeValue(type, "_GCRAP");
        } catch (Exception e) {
        }
        if (format == null || format.length() <= 0) {
            return null;
        }

        Object[] argument = {rep};
        String result = MessageFormat.format(format, argument);
        return result;
    }

    /**
     * <p>BEANのタイプ定数からPrepareStatementにセットする際に型変換を行うメソッド名を取得します
     * @param type 列タイプ
     * @param rep 置き換える文字列
     * @return 型変換を行うメソッド名
     */
    public static String getSetTypeCastMethodName(int type, String rep) {
        String format = null;
        try {
            format = __getTypeValue(type, "_SCRAP");
        } catch (Exception e) {
        }
        if (format == null || format.length() <= 0) {
            return null;
        }

        Object[] argument = {rep};
        String result = MessageFormat.format(format, argument);
        return result;
    }

    /**
     * <p>タイプ定数からにマッチするBEAN用のデータ型をFQDNのStringを返します。
     * @param type タイプ
     * @return FQDNのデータ型名
     */
    public static String getTypeNameJdbcFQDN(int type) {
        return __getTypeValue(type, "_FQDN");
    }

    /**
     * <p>タイプ定数からプロパティファイルの値を取得します。
     * @param type Types定数
     * @param suffix キー名の末文字(先頭はTypes定数の名称と同じなので)
     * @return 取得した値
     */
    private static String __getTypeValue(int type, String suffix) {
        String ret = null;
        switch (type) {
            case Types.ARRAY :
                ret = TypeMappingResourceBundle.getString("ARRAY" + suffix);
                break;

            case Types.BIGINT :
                ret = TypeMappingResourceBundle.getString("BIGINT" + suffix);
                break;

            case Types.BINARY :
                ret = TypeMappingResourceBundle.getString("BINARY" + suffix);
                break;

            case Types.BIT :
                ret = TypeMappingResourceBundle.getString("BIT" + suffix);
                break;

            case Types.BLOB :
                ret = TypeMappingResourceBundle.getString("BLOB" + suffix);
                break;

            case Types.BOOLEAN :
                ret = TypeMappingResourceBundle.getString("BOOLEAN" + suffix);
                break;

            case Types.CHAR :
                ret = TypeMappingResourceBundle.getString("CHAR" + suffix);
                break;

            case Types.CLOB :
                ret = TypeMappingResourceBundle.getString("CLOB" + suffix);
                break;

            case Types.DATALINK :
                ret = TypeMappingResourceBundle.getString("DATALINK" + suffix);
                break;

            case Types.DATE :
                ret = TypeMappingResourceBundle.getString("DATE" + suffix);
                break;

            case Types.DECIMAL :
                ret = TypeMappingResourceBundle.getString("DECIMAL" + suffix);
                break;

            case Types.DISTINCT :
                ret = TypeMappingResourceBundle.getString("DISTINCT" + suffix);
                break;

            case Types.DOUBLE :
                ret = TypeMappingResourceBundle.getString("DOUBLE" + suffix);
                break;

            case Types.FLOAT :
                ret = TypeMappingResourceBundle.getString("FLOAT" + suffix);
                break;

            case Types.INTEGER :
                ret = TypeMappingResourceBundle.getString("INTEGER" + suffix);
                break;

            case Types.JAVA_OBJECT :
                ret = TypeMappingResourceBundle.getString("JAVA_OBJECT" + suffix);
                break;

            case Types.LONGVARBINARY :
                ret = TypeMappingResourceBundle.getString("LONGVARBINARY" + suffix);
                break;

            case Types.LONGVARCHAR :
                ret = TypeMappingResourceBundle.getString("LONGVARCHAR" + suffix);
                break;

            case Types.NULL :
                ret = TypeMappingResourceBundle.getString("NULL" + suffix);
                break;

            case Types.NUMERIC :
                ret = TypeMappingResourceBundle.getString("NUMERIC" + suffix);
                break;

            case Types.OTHER :
                ret = TypeMappingResourceBundle.getString("OTHER" + suffix);
                break;

            case Types.REAL :
                ret = TypeMappingResourceBundle.getString("REAL" + suffix);
                break;

            case Types.REF :
                ret = TypeMappingResourceBundle.getString("REF" + suffix);
                break;

            case Types.SMALLINT :
                ret = TypeMappingResourceBundle.getString("SMALLINT" + suffix);
                break;

            case Types.STRUCT :
                ret = TypeMappingResourceBundle.getString("STRUCT" + suffix);
                break;

            case Types.TIME :
                ret = TypeMappingResourceBundle.getString("TIME" + suffix);
                break;

            case Types.TIMESTAMP :
                ret = TypeMappingResourceBundle.getString("TIMESTAMP" + suffix);
                break;

            case Types.TINYINT :
                ret = TypeMappingResourceBundle.getString("TINYINT" + suffix);
                break;

            case Types.VARBINARY :
                ret = TypeMappingResourceBundle.getString("VARBINARY" + suffix);
                break;

            case Types.VARCHAR :
            default:
                ret = TypeMappingResourceBundle.getString("VARCHAR" + suffix);
                break;
        }
        return ret;
    }

    /**
     * <p>タイイプ定数からJDBC標準のデータ型Stringを返します。
     * @param type タイプ
     * @return JDBC標準のデータ型String
     */
    public static String getTypeNameJdbc(int type) {
        String ret = null;
        switch (type) {
            case Types.ARRAY :
                ret = TypeMappingResourceBundle.getString("ARRAY");
                break;

            case Types.BIGINT :
                ret = TypeMappingResourceBundle.getString("BIGINT");
                break;

            case Types.BINARY :
                ret = TypeMappingResourceBundle.getString("BINARY");
                break;

            case Types.BIT :
                ret = TypeMappingResourceBundle.getString("BIT");
                break;

            case Types.BLOB :
                ret = TypeMappingResourceBundle.getString("BLOB");
                break;

            case Types.BOOLEAN :
                ret = TypeMappingResourceBundle.getString("BOOLEAN");
                break;

            case Types.CHAR :
                ret = TypeMappingResourceBundle.getString("CHAR");
                break;

            case Types.CLOB :
                ret = TypeMappingResourceBundle.getString("CLOB");
                break;

            case Types.DATALINK :
                ret = TypeMappingResourceBundle.getString("DATALINK");
                break;

            case Types.DATE :
                ret = TypeMappingResourceBundle.getString("DATE");
                break;

            case Types.DECIMAL :
                ret = TypeMappingResourceBundle.getString("DECIMAL");
                break;

            case Types.DISTINCT :
                ret = TypeMappingResourceBundle.getString("DISTINCT");
                break;

            case Types.DOUBLE :
                ret = TypeMappingResourceBundle.getString("DOUBLE");
                break;

            case Types.FLOAT :
                ret = TypeMappingResourceBundle.getString("FLOAT");
                break;

            case Types.INTEGER :
                ret = TypeMappingResourceBundle.getString("INTEGER");
                break;

            case Types.JAVA_OBJECT :
                ret = TypeMappingResourceBundle.getString("JAVA_OBJECT");
                break;

            case Types.LONGVARBINARY :
                ret = TypeMappingResourceBundle.getString("LONGVARBINARY");
                break;

            case Types.LONGVARCHAR :
                ret = TypeMappingResourceBundle.getString("LONGVARCHAR");
                break;

            case Types.NULL :
                ret = TypeMappingResourceBundle.getString("NULL");
                break;

            case Types.NUMERIC :
                ret = TypeMappingResourceBundle.getString("NUMERIC");
                break;

            case Types.OTHER :
                ret = TypeMappingResourceBundle.getString("OTHER");
                break;

            case Types.REAL :
                ret = TypeMappingResourceBundle.getString("REAL");
                break;

            case Types.REF :
                ret = TypeMappingResourceBundle.getString("REF");
                break;

            case Types.SMALLINT :
                ret = TypeMappingResourceBundle.getString("SMALLINT");
                break;

            case Types.STRUCT :
                ret = TypeMappingResourceBundle.getString("STRUCT");
                break;

            case Types.TIME :
                ret = TypeMappingResourceBundle.getString("TIME");
                break;

            case Types.TIMESTAMP :
                ret = TypeMappingResourceBundle.getString("TIMESTAMP");
                break;

            case Types.TINYINT :
                ret = TypeMappingResourceBundle.getString("TINYINT");
                break;

            case Types.VARBINARY :
                ret = TypeMappingResourceBundle.getString("VARBINARY");
                break;

            case Types.VARCHAR :
                ret = TypeMappingResourceBundle.getString("VARCHAR");
                break;
            default:
                ret = "UNKNOWN";
        }
        return ret;
    }

    /**
     * <p>SQLタイプ定数からResultSetより値を取得するメソッド名を返します。
     * @param type SQL Types定数
     * @return メソッド名
     */
    public static String getGetMethodNameFromRs(int type) {
        String ret = null;
        //ResultSet rs = null;
        switch (type) {
            case Types.ARRAY :
                ret = "getArray";
                break;

            case Types.BIGINT :
                ret = "getInt";
                break;

            case Types.BINARY :
                ret = "getBinaryStream";
                break;

            case Types.BIT :
                ret = "getByte";
                break;

            case Types.BLOB :
                ret = "getBlob";
                break;

            case Types.BOOLEAN :
                ret = "getBoolean";
                break;

            case Types.CHAR :
                ret = "getString";
                break;

            case Types.CLOB :
                ret = "getClob";
                break;

            case Types.DATALINK :
                ret = "getString";
                break;

            case Types.DATE :
                ret = "getTimestamp";
                break;

            case Types.DECIMAL :
                ret = "getBigDecimal";
                break;

            case Types.DISTINCT :
                ret = "getString";
                break;

            case Types.DOUBLE :
                ret = "getDouble";
                break;

            case Types.FLOAT :
                ret = "getFloat";
                break;

            case Types.INTEGER :
                ret = "getInt";
                break;

            case Types.JAVA_OBJECT :
                ret = "getObject";
                break;

            case Types.LONGVARBINARY :
                ret = "getString";
                break;

            case Types.LONGVARCHAR :
                ret = "getString";
                break;

            case Types.NULL :
                ret = "";
                break;

            case Types.NUMERIC :
                ret = "getInt";
                break;

            case Types.OTHER :
                ret = "";
                break;

            case Types.REAL :
                ret = "";
                break;

            case Types.REF :
                ret = "getRef";
                break;

            case Types.SMALLINT :
                ret = "getInt";
                break;

            case Types.STRUCT :
                ret = "getObject";
                break;

            case Types.TIME :
                ret = "getTime";
                break;

            case Types.TIMESTAMP :
                ret = "getTimestamp";
                break;

            case Types.TINYINT :
                ret = "getInt";
                break;

            case Types.VARBINARY :
                ret = "getBinaryStream";
                break;

            case Types.VARCHAR :
                ret = "getString";
                break;
            default:
                ret = "UNKNOWN";
        }
        return ret;
    }

    /**
     * <p>SQLタイプ定数からPrepareStatementのセットメソッド名を指定します。
     * @param type SQL Types定数
     * @return メソッド名
     */
    public static String getSetMethodNameOfPstmt(int type) {
        String ret = null;
        PreparedStatement pstmt = null;
        switch (type) {
            case Types.ARRAY :
                ret = "setArray";
                break;

            case Types.BIGINT :
                ret = "setInt";
                break;

            case Types.BINARY :
                ret = "setBinaryStream";
                break;

            case Types.BIT :
                ret = "setByte";
                break;

            case Types.BLOB :
                ret = "setBlob";
                break;

            case Types.BOOLEAN :
                ret = "setBoolean";
                break;

            case Types.CHAR :
                ret = "setString";
                break;

            case Types.CLOB :
                ret = "setClob";
                break;

            case Types.DATALINK :
                ret = "setString";
                break;

            case Types.DATE :
                ret = "setTimestamp";
                break;

            case Types.DECIMAL :
                ret = "setBigDecimal";
                ret = "setLong"; //Oracle用
                break;

            case Types.DISTINCT :
                ret = "setString";
                break;

            case Types.DOUBLE :
                ret = "setDouble";
                break;

            case Types.FLOAT :
                ret = "setFloat";
                break;

            case Types.INTEGER :
                ret = "setInt";
                break;

            case Types.JAVA_OBJECT :
                ret = "setObject";
                break;

            case Types.LONGVARBINARY :
                ret = "setString";
                break;

            case Types.LONGVARCHAR :
                ret = "setString";
                break;

            case Types.NULL :
                ret = "";
                break;

            case Types.NUMERIC :
                ret = "setInt";
                break;

            case Types.OTHER :
                ret = "";
                break;

            case Types.REAL :
                ret = "";
                break;

            case Types.REF :
                ret = "setRef";
                break;

            case Types.SMALLINT :
                ret = "setInt";
                break;

            case Types.STRUCT :
                ret = "setObject";
                break;

            case Types.TIME :
                ret = "setTime";
                break;

            case Types.TIMESTAMP :
                ret = "setTimestamp";
                break;

            case Types.TINYINT :
                ret = "setInt";
                break;

            case Types.VARBINARY :
                ret = "setBinaryStream";
                break;

            case Types.VARCHAR :
                ret = "setString";
                break;
            default:
                ret = "UNKNOWN";
        }
        return ret;
    }

    /**
     * <p>SQLタイプ定数からSqlBuffer.addXXXValueのセットメソッド名を指定します。
     * @param type SQL Types定数
     * @return メソッド名
     */
    public static String getAddValueMethodName(int type) {
        String ret = null;
        PreparedStatement pstmt = null;
        switch (type) {
            case Types.NUMERIC :
            case Types.INTEGER :
            case Types.TINYINT :
            case Types.SMALLINT :
                ret = "addIntValue";
                break;

            case Types.BIGINT :
                ret = "addLongValue";
                break;

            case Types.DOUBLE :
                ret = "addDoubleValue";
                break;

            case Types.DECIMAL :
                ret = "addDecimalValue";
                break;

            case Types.VARCHAR :
                ret = "addStrValue";
                break;

            case Types.DATE :
            case Types.TIMESTAMP :
                ret = "addDateValue";
                break;

            case Types.TIME :
                ret = "addTimeValue";
                break;

            case Types.BINARY :
            case Types.BLOB :
                ret = "addFileValue";
                break;

            case Types.CLOB :
                ret = "setClob";
                break;

            case Types.ARRAY :
            case Types.BIT :
            case Types.BOOLEAN :
            case Types.CHAR :
            case Types.DATALINK :
            case Types.DISTINCT :
            case Types.FLOAT :
            case Types.JAVA_OBJECT :
            case Types.LONGVARBINARY :
            case Types.LONGVARCHAR :
            case Types.NULL :
            case Types.OTHER :
            case Types.REAL :
            case Types.REF :
            case Types.STRUCT :
            case Types.VARBINARY :
            default:
                ret = "UNKNOWN";
        }
        return ret;
    }

    /**
     * <p>文字列をJavaのソース用にエスケープする
     * @param buf 対象の文字列
     * @return エスケープした文字列
     */
    public static String escapeSrc(String buf) {
        char[] moji = buf.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int intCnt = 0; intCnt < buf.length(); intCnt++) {
            char ch = moji[intCnt];
            switch (ch) {
                case '"' :
                    sb.append("\\");
                    sb.append("\"");
                    break;
                case '\\' :
                    sb.append("\\\\");
                    break;
                default :
                    char[] c = {ch};
                    sb.append(ch);
            }
        }
        return new String(sb);
    }

}
