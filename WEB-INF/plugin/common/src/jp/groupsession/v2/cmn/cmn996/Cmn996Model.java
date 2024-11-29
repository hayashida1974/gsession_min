package jp.groupsession.v2.cmn.cmn996;

import jp.groupsession.v2.cmn.cmn999.Cmn999Model;

/**
 * <br>[機  能] HTMLのhiddenタイプを表現するクラス。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn996Model extends Cmn999Model {

    /** キー名 */
    private String key__;
    /** バリュー */
    private String value__;

    /**
     * <p>key__を取得します。
     * @return key__を戻します。
     */
    public String getKey() {
        return key__;
    }
    /**
     * <p>key__をセットします。
     * @param key key__を設定。
     */
    public void setKey(String key) {
        key__ = key;
    }
    /**
     * <p>value__を取得します。
     * @return value__を戻します。
     */
    public String getValue() {
        return value__;
    }
    /**
     * <p>value__をセットします。
     * @param value value__を設定。
     */
    public void setValue(String value) {
        value__ = value;
    }
}