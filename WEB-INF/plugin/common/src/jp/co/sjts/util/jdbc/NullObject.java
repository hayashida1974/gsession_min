package jp.co.sjts.util.jdbc;

/**
 * <br>[機  能] SqlBuffer内でNullを表すObject
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class NullObject {

    /** NullObjectタイプ */
    private String type__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param type NullObjectタイプ
     */
    public NullObject(String type) {
        setType(type);
    }

    /**
     * @return type__ を戻します。
     */
    public String getType() {
        return type__;
    }

    /**
     * @param type type__を設定する。
     */
    public void setType(String type) {
        type__ = type;
    }
}