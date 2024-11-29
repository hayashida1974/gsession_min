package jp.co.sjts.util.ical;

/**
 * <br>[機  能] iCal形式データの解析結果を格納する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IcalProperty {

    /** プロパティ名 */
    String name__ = null;
    /** 値 */
    String value__ = null;
    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>value を取得します。
     * @return value
     */
    public String getValue() {
        return value__;
    }
    /**
     * <p>value をセットします。
     * @param value value
     */
    public void setValue(String value) {
        value__ = value;
    }

}
