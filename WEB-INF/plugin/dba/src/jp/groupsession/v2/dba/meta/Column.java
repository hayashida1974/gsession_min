/*
 * 作成日: 2004/07/30
 */
package jp.groupsession.v2.dba.meta;

import java.io.Serializable;
import java.sql.DatabaseMetaData;
import java.sql.Types;

import jp.co.sjts.util.sort.IListSortable;

/**
 * <br>[機  能] テーブルの列を表すクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Column implements Serializable, IListSortable {

    /** 列名 */
    private String name__ = null;
    /** データ型 */
    private int type__ = Types.NULL;
    /** サイズ */
    private int size__ = -1;
    /** 精度 */
    private int digits__ = -1;
    /**
     * Nullの許可
     * @see java.sql.DatabaseMetaData.columnNoNulls
     * @see java.sql.DatabaseMetaData.columnNullable
     * @see java.sql.DatabaseMetaData.columnNullableUnknown
     */
    int nullable__ = DatabaseMetaData.columnNullableUnknown;

    /** 主キーの連番 */
    private int seqKey__ = NOT_KEYSEQ;

    /** 主キーではない列のKeySeqの値 */
    public static final int NOT_KEYSEQ = -1;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Column() {
    }

    /**
     * <br>[機  能] 精度を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return 精度
     */
    public int getDigits() {
        return digits__;
    }

    /**
     * <br>[機  能] 列名を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return 列名
     */
    public String getName() {
        return name__.toUpperCase();
    }

    /**
     * <br>[機  能] null値を許可するか取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return null値を許可するかどうか
     */
    public int getNullable() {
        return nullable__;
    }

    /**
     * <br>[機  能] サイズを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return サイズ
     */
    public int getSize() {
        return size__;
    }

    /**
     * <br>[機  能] 列のタイプを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return 列のタイプ
     */
    public int getType() {
        return type__;
    }

    /**
     * <br>[機  能] 精度をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i 精度
     */
    public void setDigits(int i) {
        digits__ = i;
    }

    /**
     * <br>[機  能] 列名をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param string 列名
     */
    public void setName(String string) {
        name__ = string;
    }

    /**
     * <br>[機  能] null値を許可するかセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i null値をセットするか
     */
    public void setNullable(int i) {
        nullable__ = i;
    }

    /**
     * <br>[機  能] 列のサイズをセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i サイズ
     */
    public void setSize(int i) {
        size__ = i;
    }

    /**
     * <br>[機  能] 列のタイプをセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i 列のタイプ
     */
    public void setType(int i) {
        type__ = i;
    }

    /**
     * <br>[機  能] 主キーの連番を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return 主キーの連番
     */
    public int getSeqKey() {
        return seqKey__;
    }

    /**
     * <br>[機  能] 主キーの連番をセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param i 主キーの連番
     */
    public void setSeqKey(int i) {
        seqKey__ = i;
    }

    /**
     * <br>[機  能] このオブジェクトの文字列形式を返します。
     * <br>[解  説]
     * <br>[備  考]
     * @return このオブジェクトの文字列形式
     */
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("name = " + name__);
        buf.append(", type = " + JdbcMetaUtil.getTypeNameJdbcStandard(type__));
        buf.append(",  size = " + size__);
        buf.append(",  nullable = " + nullable__);
        buf.append(",  keySeq = " + seqKey__);
        return buf.toString();
    }

    /**
     * <br>[機  能] ソートキーを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return ソートキー
     * @see jp.co.sjts.util.sort.IListSortable#getSortKey()
     */
    public Object getSortKey() {
        //
        return String.valueOf(seqKey__);
    }
}
