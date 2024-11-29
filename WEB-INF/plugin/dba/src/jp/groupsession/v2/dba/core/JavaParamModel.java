package jp.groupsession.v2.dba.core;

import jp.groupsession.v2.dba.meta.Column;

/**
 * <br>[機  能] javaメソッドの引数等の生成に使用するモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class JavaParamModel {
    /** Javaの型名 */
    private String typeName__ = null;

    /** 引数名称 */
    private String paramName__ = null;

    /** DBの列オブジェクト */
    private Column col__ = null;

    /**
     * デフォルトコンストラクタ
     */
    public JavaParamModel() {
    }

    /**
     * <p>col を取得します。
     * @return col
     */
    public Column getCol() {
        return col__;
    }

    /**
     * <p>col をセットします。
     * @param col col
     */
    public void setCol(Column col) {
        col__ = col;
    }

    /**
     * <p>paramName を取得します。
     * @return paramName
     */
    public String getParamName() {
        return paramName__;
    }

    /**
     * <p>paramName をセットします。
     * @param paramName paramName
     */
    public void setParamName(String paramName) {
        paramName__ = paramName;
    }

    /**
     * <p>typeName を取得します。
     * @return typeName
     */
    public String getTypeName() {
        return typeName__;
    }

    /**
     * <p>typeName をセットします。
     * @param typeName typeName
     */
    public void setTypeName(String typeName) {
        typeName__ = typeName;
    }


}
